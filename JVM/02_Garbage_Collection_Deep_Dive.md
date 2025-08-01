# Garbage Collection Deep Dive

## Table of Contents

1. [Introduction to Garbage Collection](#introduction-to-garbage-collection)
2. [GC Algorithms](#gc-algorithms)
3. [Generational Garbage Collection](#generational-garbage-collection)
4. [GC Tuning Parameters](#gc-tuning-parameters)
5. [GC Monitoring and Analysis](#gc-monitoring-and-analysis)
6. [Practical Examples](#practical-examples)

## Introduction to Garbage Collection

Garbage Collection (GC) is an automatic memory management feature of the JVM that reclaims memory occupied by objects that are no longer in use.

### Why Garbage Collection?

- **Automatic Memory Management**: Developers don't need to manually free memory
- **Prevents Memory Leaks**: Automatically removes unreferenced objects
- **Improves Application Stability**: Reduces crashes due to memory issues

### GC Terminology

- **Live Objects**: Objects that are still reachable from root references
- **Dead Objects**: Objects that are no longer reachable
- **Root References**: Starting points for object graph traversal (static variables, local variables, etc.)

## GC Algorithms

### 1. Mark and Sweep

The most basic garbage collection algorithm.

```java
// Example demonstrating mark and sweep concept
public class MarkAndSweepExample {
    public static void main(String[] args) {
        // Create objects
        Object obj1 = new Object();
        Object obj2 = new Object();

        // obj1 is still referenced (live)
        // obj2 becomes unreferenced (dead)
        obj2 = null;

        // During GC:
        // 1. Mark phase: Mark all reachable objects from roots
        // 2. Sweep phase: Remove unmarked objects

        System.out.println("obj1: " + obj1);
        System.out.println("obj2: " + obj2);
    }
}
```

### 2. Copying Collector

Divides memory into two equal halves and copies live objects between them.

```java
// Example demonstrating copying collector concept
public class CopyingCollectorExample {
    public static void main(String[] args) {
        // Simulate copying collector behavior
        List<Object> fromSpace = new ArrayList<>();
        List<Object> toSpace = new ArrayList<>();

        // Add objects to from space
        for (int i = 0; i < 5; i++) {
            fromSpace.add(new Object());
        }

        // Copy live objects to to space
        for (Object obj : fromSpace) {
            if (obj != null) { // Simulate live object check
                toSpace.add(obj);
            }
        }

        // Clear from space
        fromSpace.clear();

        System.out.println("From space size: " + fromSpace.size());
        System.out.println("To space size: " + toSpace.size());
    }
}
```

### 3. Mark and Compact

Marks live objects and then compacts them to eliminate fragmentation.

```java
// Example demonstrating mark and compact concept
public class MarkAndCompactExample {
    public static void main(String[] args) {
        // Simulate fragmented memory
        Object[] memory = new Object[10];
        memory[0] = new Object(); // Live object
        memory[2] = new Object(); // Live object
        memory[5] = new Object(); // Live object
        memory[8] = new Object(); // Live object

        // Compact live objects to beginning
        int compactIndex = 0;
        for (int i = 0; i < memory.length; i++) {
            if (memory[i] != null) {
                memory[compactIndex] = memory[i];
                if (compactIndex != i) {
                    memory[i] = null;
                }
                compactIndex++;
            }
        }

        System.out.println("Compacted objects: " + compactIndex);
    }
}
```

## Generational Garbage Collection

Most modern JVMs use generational garbage collection, which divides the heap into different generations.

### Heap Structure

```
┌─────────────────────────────────────────────────────────┐
│                    Heap Memory                          │
├─────────────────────────────────────────────────────────┤
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────┐ │
│  │   Young Gen     │  │   Old Gen       │  │  Meta   │ │
│  │                 │  │                 │  │  Space  │ │
│  │ ┌─────────────┐ │  │                 │  │         │ │
│  │ │    Eden     │ │  │                 │  │         │ │
│  │ └─────────────┘ │  │                 │  │         │ │
│  │ ┌─────────────┐ │  │                 │  │         │ │
│  │ │ Survivor S0 │ │  │                 │  │         │ │
│  │ └─────────────┘ │  │                 │  │         │ │
│  │ ┌─────────────┐ │  │                 │  │         │ │
│  │ │ Survivor S1 │ │  │                 │  │         │ │
│  │ └─────────────┘ │  │                 │  │         │ │
│  └─────────────────┘  └─────────────────┘  └─────────┘ │
└─────────────────────────────────────────────────────────┘
```

### Generational GC Example

```java
public class GenerationalGCExample {
    public static void main(String[] args) {
        // Young generation objects (short-lived)
        for (int i = 0; i < 1000; i++) {
            new Object(); // Most will be collected quickly
        }

        // Old generation objects (long-lived)
        List<Object> longLivedObjects = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            longLivedObjects.add(new Object());
        }

        // Force minor GC
        System.gc();

        System.out.println("Long-lived objects: " + longLivedObjects.size());
    }
}
```

### Object Lifecycle

```java
public class ObjectLifecycleExample {
    public static void main(String[] args) {
        // Phase 1: Object creation in Eden
        Object obj1 = new Object();
        System.out.println("Object created in Eden");

        // Phase 2: Survive minor GC, move to Survivor
        for (int i = 0; i < 1000; i++) {
            new Object(); // Fill Eden to trigger minor GC
        }
        System.gc(); // Minor GC

        // Phase 3: Survive multiple minor GCs, move to Old Gen
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 1000; j++) {
                new Object();
            }
            System.gc(); // Multiple minor GCs
        }

        System.out.println("Object lifecycle demonstration completed");
    }
}
```

## GC Tuning Parameters

### Common GC Parameters

```bash
# Basic heap settings
-Xms512m                    # Initial heap size
-Xmx2g                      # Maximum heap size
-XX:NewRatio=3              # Ratio of old to young generation (1:3)

# Young generation settings
-XX:NewSize=256m            # Initial young generation size
-XX:MaxNewSize=512m         # Maximum young generation size
-XX:SurvivorRatio=8         # Ratio of Eden to Survivor (8:1:1)

# GC algorithm selection
-XX:+UseSerialGC            # Serial garbage collector
-XX:+UseParallelGC          # Parallel garbage collector
-XX:+UseConcMarkSweepGC     # CMS garbage collector
-XX:+UseG1GC                # G1 garbage collector
-XX:+UseZGC                 # Z garbage collector (Java 11+)

# GC logging
-verbose:gc                 # Enable GC logging
-XX:+PrintGCDetails         # Print detailed GC information
-XX:+PrintGCTimeStamps      # Print GC timestamps
-XX:+PrintGCDateStamps      # Print GC date stamps
-Xloggc:gc.log              # Log GC to file

# Performance tuning
-XX:MaxGCPauseMillis=200    # Target maximum GC pause time
-XX:GCTimeRatio=99          # Target GC time ratio (1% GC time)
-XX:ParallelGCThreads=4     # Number of parallel GC threads
```

### GC Algorithm Comparison

| Algorithm   | Pause Time | Throughput | Memory Overhead | Use Case           |
| ----------- | ---------- | ---------- | --------------- | ------------------ |
| Serial GC   | High       | Low        | Low             | Small applications |
| Parallel GC | Medium     | High       | Low             | Multi-core systems |
| CMS         | Low        | Medium     | High            | Low latency apps   |
| G1 GC       | Low        | High       | Medium          | General purpose    |
| ZGC         | Very Low   | High       | High            | Ultra-low latency  |

## GC Monitoring and Analysis

### GC Log Analysis

```java
public class GCMonitoringExample {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        // Monitor memory before GC
        long beforeTotal = runtime.totalMemory();
        long beforeFree = runtime.freeMemory();
        long beforeUsed = beforeTotal - beforeFree;

        System.out.println("Before GC:");
        System.out.println("  Total: " + beforeTotal / 1024 / 1024 + " MB");
        System.out.println("  Used: " + beforeUsed / 1024 / 1024 + " MB");
        System.out.println("  Free: " + beforeFree / 1024 / 1024 + " MB");

        // Perform GC
        System.gc();

        // Monitor memory after GC
        long afterTotal = runtime.totalMemory();
        long afterFree = runtime.freeMemory();
        long afterUsed = afterTotal - afterFree;

        System.out.println("\nAfter GC:");
        System.out.println("  Total: " + afterTotal / 1024 / 1024 + " MB");
        System.out.println("  Used: " + afterUsed / 1024 / 1024 + " MB");
        System.out.println("  Free: " + afterFree / 1024 / 1024 + " MB");

        // Calculate memory reclaimed
        long reclaimed = beforeUsed - afterUsed;
        System.out.println("\nMemory reclaimed: " + reclaimed / 1024 / 1024 + " MB");
    }
}
```

### GC Statistics Collection

```java
public class GCStatisticsExample {
    private static long totalGCTime = 0;
    private static int gcCount = 0;

    public static void main(String[] args) {
        // Set up GC monitoring
        setupGCMonitoring();

        // Simulate application workload
        for (int i = 0; i < 10; i++) {
            createObjects();
            System.gc();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Print GC statistics
        printGCStatistics();
    }

    private static void setupGCMonitoring() {
        // This would typically be done with JMX or GC logging
        System.out.println("GC monitoring setup completed");
    }

    private static void createObjects() {
        for (int i = 0; i < 10000; i++) {
            new byte[1024]; // Create 1KB objects
        }
    }

    private static void printGCStatistics() {
        System.out.println("GC Statistics:");
        System.out.println("  Total GC count: " + gcCount);
        System.out.println("  Total GC time: " + totalGCTime + " ms");
        if (gcCount > 0) {
            System.out.println("  Average GC time: " + (totalGCTime / gcCount) + " ms");
        }
    }
}
```

## Practical Examples

### Example 1: Memory Leak Detection

```java
public class MemoryLeakDetectionExample {
    private static final List<Object> LEAK_LIST = new ArrayList<>();

    public static void main(String[] args) {
        // Simulate memory leak
        for (int i = 0; i < 100000; i++) {
            LEAK_LIST.add(new byte[1024]); // 1KB objects
        }

        // Monitor memory usage
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();

        System.out.println("Memory usage: " + usedMemory / 1024 / 1024 + " MB");
        System.out.println("Objects in leak list: " + LEAK_LIST.size());

        // This would be a memory leak in a real application
        // as objects are never removed from the list
    }
}
```

### Example 2: GC Performance Testing

```java
public class GCPerformanceTest {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // Create objects rapidly
        for (int i = 0; i < 1000000; i++) {
            new Object();

            // Force GC every 100,000 objects
            if (i % 100000 == 0) {
                System.gc();
            }
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Total execution time: " + totalTime + " ms");
        System.out.println("Objects created: 1,000,000");
        System.out.println("GC calls: 10");
    }
}
```

### Example 3: GC Algorithm Comparison

```java
public class GCAlgorithmComparison {
    public static void main(String[] args) {
        // Test different GC algorithms
        String[] gcAlgorithms = {"Serial", "Parallel", "CMS", "G1"};

        for (String algorithm : gcAlgorithms) {
            System.out.println("Testing " + algorithm + " GC:");
            testGCAlgorithm(algorithm);
            System.out.println();
        }
    }

    private static void testGCAlgorithm(String algorithm) {
        long startTime = System.currentTimeMillis();

        // Create objects
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            objects.add(new byte[1024]); // 1KB objects
        }

        // Perform GC
        System.gc();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("  Duration: " + duration + " ms");
        System.out.println("  Objects created: " + objects.size());
    }
}
```

### Example 4: Memory Pressure Testing

```java
public class MemoryPressureTest {
    public static void main(String[] args) {
        // Simulate memory pressure
        List<byte[]> memoryPressure = new ArrayList<>();

        try {
            while (true) {
                // Allocate 1MB chunks
                memoryPressure.add(new byte[1024 * 1024]);

                // Print memory usage every 100MB
                if (memoryPressure.size() % 100 == 0) {
                    Runtime runtime = Runtime.getRuntime();
                    long usedMemory = runtime.totalMemory() - runtime.freeMemory();
                    System.out.println("Memory used: " + usedMemory / 1024 / 1024 + " MB");
                }
            }
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError occurred after " +
                             memoryPressure.size() + " MB allocated");
        }
    }
}
```

## Best Practices for GC Optimization

### 1. Object Lifecycle Management

- Minimize object creation in hot paths
- Use object pooling for frequently created objects
- Avoid creating unnecessary temporary objects

### 2. Memory Allocation Patterns

- Allocate objects with similar lifetimes together
- Use appropriate data structures
- Consider memory locality

### 3. GC Tuning

- Monitor GC performance in production
- Tune GC parameters based on application characteristics
- Use appropriate GC algorithm for your use case

### 4. Monitoring and Profiling

- Use GC logging to analyze performance
- Monitor GC pause times and frequency
- Profile memory usage patterns

## Conclusion

Understanding garbage collection is essential for Java performance optimization. By choosing the right GC algorithm and tuning parameters appropriately, you can significantly improve application performance and reduce pause times.

### Key Takeaways:

- Different GC algorithms have different trade-offs
- Generational GC is most common in modern JVMs
- GC tuning requires understanding of application characteristics
- Monitoring and profiling are essential for optimization
- Memory pressure can significantly impact GC performance

This deep dive provides practical knowledge for optimizing garbage collection in Java applications.
