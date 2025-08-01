# JVM Performance Tuning and Monitoring

## Table of Contents

1. [Introduction to JVM Performance](#introduction-to-jvm-performance)
2. [Performance Metrics](#performance-metrics)
3. [JVM Tuning Parameters](#jvm-tuning-parameters)
4. [Memory Tuning](#memory-tuning)
5. [GC Tuning](#gc-tuning)
6. [JIT Compilation Tuning](#jit-compilation-tuning)
7. [Thread Tuning](#thread-tuning)
8. [Monitoring Tools](#monitoring-tools)
9. [Performance Profiling](#performance-profiling)
10. [Troubleshooting Performance Issues](#troubleshooting-performance-issues)

## Introduction to JVM Performance

JVM performance tuning is the process of optimizing the Java Virtual Machine to achieve better application performance, lower latency, and higher throughput.

### Performance Goals

- **Throughput**: Maximum application work per unit time
- **Latency**: Minimum response time for individual requests
- **Memory Efficiency**: Optimal memory usage
- **CPU Efficiency**: Optimal CPU utilization

### Performance Tuning Approach

1. **Measure**: Establish baseline performance metrics
2. **Analyze**: Identify bottlenecks and performance issues
3. **Tune**: Apply optimizations
4. **Validate**: Measure improvements
5. **Iterate**: Repeat the process

## Performance Metrics

### Key Performance Indicators (KPIs)

```java
public class PerformanceMetricsExample {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        // Memory metrics
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;

        // CPU metrics
        int availableProcessors = runtime.availableProcessors();

        // Thread metrics
        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
        while (rootGroup.getParent() != null) {
            rootGroup = rootGroup.getParent();
        }
        int threadCount = rootGroup.activeCount();

        System.out.println("=== Performance Metrics ===");
        System.out.println("Memory:");
        System.out.println("  Max: " + maxMemory / 1024 / 1024 + " MB");
        System.out.println("  Total: " + totalMemory / 1024 / 1024 + " MB");
        System.out.println("  Used: " + usedMemory / 1024 / 1024 + " MB");
        System.out.println("  Free: " + freeMemory / 1024 / 1024 + " MB");
        System.out.println("  Usage: " + (usedMemory * 100 / totalMemory) + "%");

        System.out.println("CPU:");
        System.out.println("  Available Processors: " + availableProcessors);

        System.out.println("Threads:");
        System.out.println("  Active Threads: " + threadCount);
    }
}
```

### Application Performance Metrics

```java
public class ApplicationMetrics {
    private static final long startTime = System.currentTimeMillis();
    private static long requestCount = 0;
    private static long totalResponseTime = 0;

    public static void recordRequest(long responseTime) {
        requestCount++;
        totalResponseTime += responseTime;
    }

    public static void printMetrics() {
        long uptime = System.currentTimeMillis() - startTime;
        double avgResponseTime = requestCount > 0 ? (double) totalResponseTime / requestCount : 0;
        double requestsPerSecond = uptime > 0 ? (requestCount * 1000.0) / uptime : 0;

        System.out.println("=== Application Metrics ===");
        System.out.println("Uptime: " + uptime / 1000 + " seconds");
        System.out.println("Total Requests: " + requestCount);
        System.out.println("Average Response Time: " + avgResponseTime + " ms");
        System.out.println("Requests per Second: " + requestsPerSecond);
    }

    public static void main(String[] args) {
        // Simulate some requests
        for (int i = 0; i < 1000; i++) {
            long start = System.currentTimeMillis();
            // Simulate work
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long responseTime = System.currentTimeMillis() - start;
            recordRequest(responseTime);
        }

        printMetrics();
    }
}
```

## JVM Tuning Parameters

### Basic JVM Parameters

```bash
# Memory settings
-Xms512m                    # Initial heap size
-Xmx2g                      # Maximum heap size
-Xss256k                    # Thread stack size
-XX:MetaspaceSize=64m       # Initial metaspace size
-XX:MaxMetaspaceSize=256m   # Maximum metaspace size

# GC settings
-XX:+UseG1GC                # Use G1 garbage collector
-XX:MaxGCPauseMillis=200    # Target maximum GC pause time
-XX:G1HeapRegionSize=16m    # G1 region size

# JIT settings
-XX:+TieredCompilation      # Enable tiered compilation
-XX:ReservedCodeCacheSize=256m  # Code cache size

# Logging
-verbose:gc                 # Enable GC logging
-XX:+PrintGCDetails         # Print detailed GC information
-Xloggc:gc.log              # GC log file
```

### Advanced Tuning Parameters

```bash
# Memory allocation
-XX:NewRatio=3              # Ratio of old to young generation
-XX:SurvivorRatio=8         # Ratio of Eden to Survivor spaces
-XX:MaxTenuringThreshold=15 # Maximum tenuring threshold

# GC tuning
-XX:ParallelGCThreads=4     # Number of parallel GC threads
-XX:ConcGCThreads=2         # Number of concurrent GC threads
-XX:InitiatingHeapOccupancyPercent=45  # G1 IHOP

# JIT tuning
-XX:CompileThreshold=10000  # Method invocation threshold
-XX:OnStackReplacePercentage=140  # OSR threshold
-XX:MaxInlineSize=35        # Maximum inline size
-XX:FreqInlineSize=325      # Frequent inline size
```

## Memory Tuning

### Heap Size Optimization

```java
public class HeapTuningExample {
    public static void main(String[] args) {
        // Monitor heap usage patterns
        Runtime runtime = Runtime.getRuntime();

        // Simulate application workload
        List<Object> objects = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            // Allocate memory
            for (int j = 0; j < 1000; j++) {
                objects.add(new byte[1024]); // 1KB objects
            }

            // Monitor memory usage
            long usedMemory = runtime.totalMemory() - runtime.freeMemory();
            long maxMemory = runtime.maxMemory();

            System.out.println("Iteration " + i + ":");
            System.out.println("  Used Memory: " + usedMemory / 1024 / 1024 + " MB");
            System.out.println("  Max Memory: " + maxMemory / 1024 / 1024 + " MB");
            System.out.println("  Usage: " + (usedMemory * 100 / maxMemory) + "%");

            // Force GC to see memory patterns
            if (i % 10 == 0) {
                System.gc();
            }
        }
    }
}
```

### Memory Allocation Patterns

```java
public class MemoryAllocationPatterns {
    public static void main(String[] args) {
        // Pattern 1: Object pooling for frequently created objects
        ObjectPool<StringBuilder> pool = new ObjectPool<>(StringBuilder::new);

        long startTime = System.currentTimeMillis();

        // Use pooled objects
        for (int i = 0; i < 100000; i++) {
            StringBuilder sb = pool.acquire();
            sb.append("Hello World ").append(i);
            pool.release(sb);
        }

        long pooledTime = System.currentTimeMillis() - startTime;

        // Pattern 2: Regular object creation
        startTime = System.currentTimeMillis();

        for (int i = 0; i < 100000; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("Hello World ").append(i);
        }

        long regularTime = System.currentTimeMillis() - startTime;

        System.out.println("Pooled objects time: " + pooledTime + " ms");
        System.out.println("Regular objects time: " + regularTime + " ms");
        System.out.println("Improvement: " + ((regularTime - pooledTime) * 100.0 / regularTime) + "%");
    }
}

// Simple object pool implementation
class ObjectPool<T> {
    private final Queue<T> pool;
    private final Supplier<T> factory;

    public ObjectPool(Supplier<T> factory) {
        this.pool = new ConcurrentLinkedQueue<>();
        this.factory = factory;
    }

    public T acquire() {
        T obj = pool.poll();
        return obj != null ? obj : factory.get();
    }

    public void release(T obj) {
        pool.offer(obj);
    }
}
```

## GC Tuning

### GC Algorithm Selection

```java
public class GCAlgorithmSelection {
    public static void main(String[] args) {
        // Test different GC algorithms
        String[] algorithms = {"Serial", "Parallel", "CMS", "G1", "ZGC"};

        for (String algorithm : algorithms) {
            System.out.println("Testing " + algorithm + " GC:");
            testGCAlgorithm(algorithm);
            System.out.println();
        }
    }

    private static void testGCAlgorithm(String algorithm) {
        long startTime = System.currentTimeMillis();
        long gcStartTime = System.currentTimeMillis();

        // Create objects to trigger GC
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            objects.add(new byte[1024]); // 1KB objects
        }

        // Force GC
        System.gc();

        long gcEndTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();

        long gcTime = gcEndTime - gcStartTime;
        long totalTime = endTime - startTime;

        System.out.println("  Total time: " + totalTime + " ms");
        System.out.println("  GC time: " + gcTime + " ms");
        System.out.println("  GC overhead: " + (gcTime * 100.0 / totalTime) + "%");
    }
}
```

### GC Tuning for Different Workloads

```java
public class GCTuningWorkloads {
    public static void main(String[] args) {
        // Low latency workload
        System.out.println("=== Low Latency Workload ===");
        testLowLatencyWorkload();

        // High throughput workload
        System.out.println("\n=== High Throughput Workload ===");
        testHighThroughputWorkload();

        // Memory-intensive workload
        System.out.println("\n=== Memory-Intensive Workload ===");
        testMemoryIntensiveWorkload();
    }

    private static void testLowLatencyWorkload() {
        // Simulate low latency requirements
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            // Quick operations
            String result = "Result " + i;

            // Check latency
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime > 1000) { // 1 second limit
                System.out.println("Latency exceeded 1 second at iteration " + i);
                break;
            }
        }

        System.out.println("Low latency test completed");
    }

    private static void testHighThroughputWorkload() {
        long startTime = System.currentTimeMillis();
        int operations = 0;

        // Simulate high throughput requirements
        while (System.currentTimeMillis() - startTime < 5000) { // 5 seconds
            // Perform operations
            for (int i = 0; i < 1000; i++) {
                new Object();
                operations++;
            }
        }

        long endTime = System.currentTimeMillis();
        double throughput = operations * 1000.0 / (endTime - startTime);

        System.out.println("Operations: " + operations);
        System.out.println("Throughput: " + throughput + " ops/sec");
    }

    private static void testMemoryIntensiveWorkload() {
        List<byte[]> memoryObjects = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        try {
            while (System.currentTimeMillis() - startTime < 3000) { // 3 seconds
                // Allocate large objects
                memoryObjects.add(new byte[1024 * 1024]); // 1MB

                // Monitor memory usage
                Runtime runtime = Runtime.getRuntime();
                long usedMemory = runtime.totalMemory() - runtime.freeMemory();

                if (memoryObjects.size() % 100 == 0) {
                    System.out.println("Memory used: " + usedMemory / 1024 / 1024 + " MB");
                }
            }
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError after " + memoryObjects.size() + " MB");
        }

        System.out.println("Memory-intensive test completed");
    }
}
```

## JIT Compilation Tuning

### JIT Compilation Monitoring

```java
public class JITCompilationExample {
    public static void main(String[] args) {
        // Enable JIT compilation monitoring
        System.setProperty("java.compiler", "NONE"); // Disable JIT for testing

        // Test method that will be JIT compiled
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 100000; i++) {
            performCalculation(i);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");

        // Re-enable JIT
        System.setProperty("java.compiler", "");

        // Test with JIT
        startTime = System.currentTimeMillis();

        for (int i = 0; i < 100000; i++) {
            performCalculation(i);
        }

        endTime = System.currentTimeMillis();
        System.out.println("Execution time with JIT: " + (endTime - startTime) + " ms");
    }

    public static int performCalculation(int value) {
        // Hot method that will be JIT compiled
        int result = 0;
        for (int i = 0; i < 100; i++) {
            result += value * i;
        }
        return result;
    }
}
```

### Method Inlining Optimization

```java
public class MethodInliningExample {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // Call small methods that can be inlined
        int result = 0;
        for (int i = 0; i < 1000000; i++) {
            result += add(i, i + 1);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Result: " + result);
        System.out.println("Time: " + (endTime - startTime) + " ms");
    }

    // Small method that can be inlined
    private static int add(int a, int b) {
        return a + b;
    }
}
```

## Thread Tuning

### Thread Pool Optimization

```java
public class ThreadTuningExample {
    public static void main(String[] args) {
        // Test different thread pool sizes
        int[] poolSizes = {1, 2, 4, 8, 16};

        for (int poolSize : poolSizes) {
            System.out.println("Testing thread pool size: " + poolSize);
            testThreadPool(poolSize);
            System.out.println();
        }
    }

    private static void testThreadPool(int poolSize) {
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        long startTime = System.currentTimeMillis();

        // Submit tasks
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            final int taskId = i;
            futures.add(executor.submit(() -> {
                // Simulate work
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return taskId;
            }));
        }

        // Wait for completion
        for (Future<Integer> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        executor.shutdown();

        System.out.println("  Execution time: " + (endTime - startTime) + " ms");
        System.out.println("  Throughput: " + (1000 * 1000.0 / (endTime - startTime)) + " tasks/sec");
    }
}
```

### Thread Stack Size Tuning

```java
public class ThreadStackTuningExample {
    public static void main(String[] args) {
        // Test different stack sizes
        int[] stackSizes = {64, 128, 256, 512}; // KB

        for (int stackSize : stackSizes) {
            System.out.println("Testing stack size: " + stackSize + " KB");
            testStackSize(stackSize);
            System.out.println();
        }
    }

    private static void testStackSize(int stackSizeKB) {
        long startTime = System.currentTimeMillis();

        // Create threads with specified stack size
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            final int threadId = i;
            threads[i] = new Thread(null, () -> {
                recursiveMethod(threadId, 0, stackSizeKB);
            }, "Thread-" + i, stackSizeKB * 1024);
        }

        // Start threads
        for (Thread thread : threads) {
            thread.start();
        }

        // Wait for completion
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("  Execution time: " + (endTime - startTime) + " ms");
    }

    private static void recursiveMethod(int threadId, int depth, int maxDepth) {
        if (depth < maxDepth / 10) { // Adjust recursion depth based on stack size
            recursiveMethod(threadId, depth + 1, maxDepth);
        }
    }
}
```

## Monitoring Tools

### Built-in Monitoring

```java
public class BuiltInMonitoring {
    public static void main(String[] args) {
        // Memory monitoring
        Runtime runtime = Runtime.getRuntime();

        // Thread monitoring
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();

        // GC monitoring
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();

        // Class loading monitoring
        ClassLoadingMXBean classBean = ManagementFactory.getClassLoadingMXBean();

        // Print monitoring information
        System.out.println("=== JVM Monitoring ===");

        System.out.println("Memory:");
        System.out.println("  Used: " + (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024 + " MB");
        System.out.println("  Max: " + runtime.maxMemory() / 1024 / 1024 + " MB");

        System.out.println("Threads:");
        System.out.println("  Active: " + threadBean.getThreadCount());
        System.out.println("  Peak: " + threadBean.getPeakThreadCount());

        System.out.println("GC:");
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            System.out.println("  " + gcBean.getName() + ": " + gcBean.getCollectionCount() + " collections");
        }

        System.out.println("Classes:");
        System.out.println("  Loaded: " + classBean.getLoadedClassCount());
        System.out.println("  Total Loaded: " + classBean.getTotalLoadedClassCount());
        System.out.println("  Unloaded: " + classBean.getUnloadedClassCount());
    }
}
```

### Custom Monitoring

```java
public class CustomMonitoring {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void startMonitoring() {
        scheduler.scheduleAtFixedRate(() -> {
            printSystemMetrics();
        }, 0, 5, TimeUnit.SECONDS);
    }

    private static void printSystemMetrics() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long maxMemory = runtime.maxMemory();

        System.out.println("[" + new java.util.Date() + "] " +
                          "Memory: " + usedMemory / 1024 / 1024 + "/" + maxMemory / 1024 / 1024 + " MB " +
                          "(" + (usedMemory * 100 / maxMemory) + "%)");
    }

    public static void stopMonitoring() {
        scheduler.shutdown();
    }

    public static void main(String[] args) {
        startMonitoring();

        // Simulate application workload
        try {
            Thread.sleep(30000); // 30 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stopMonitoring();
    }
}
```

## Performance Profiling

### CPU Profiling

```java
public class CPUProfilingExample {
    public static void main(String[] args) {
        // CPU-intensive operations
        long startTime = System.currentTimeMillis();

        // Operation 1: Mathematical calculations
        double result1 = performMathCalculations();

        // Operation 2: String operations
        String result2 = performStringOperations();

        // Operation 3: Array operations
        int[] result3 = performArrayOperations();

        long endTime = System.currentTimeMillis();

        System.out.println("Total execution time: " + (endTime - startTime) + " ms");
        System.out.println("Math result: " + result1);
        System.out.println("String result length: " + result2.length());
        System.out.println("Array result length: " + result3.length);
    }

    private static double performMathCalculations() {
        double result = 0;
        for (int i = 0; i < 1000000; i++) {
            result += Math.sin(i) * Math.cos(i);
        }
        return result;
    }

    private static String performStringOperations() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            sb.append("String ").append(i).append(" ");
        }
        return sb.toString();
    }

    private static int[] performArrayOperations() {
        int[] array = new int[100000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i * i;
        }
        return array;
    }
}
```

### Memory Profiling

```java
public class MemoryProfilingExample {
    public static void main(String[] args) {
        // Monitor memory usage during different operations

        // Operation 1: Object creation
        System.out.println("=== Object Creation ===");
        monitorMemoryUsage(() -> {
            List<Object> objects = new ArrayList<>();
            for (int i = 0; i < 100000; i++) {
                objects.add(new Object());
            }
        });

        // Operation 2: String operations
        System.out.println("\n=== String Operations ===");
        monitorMemoryUsage(() -> {
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                strings.add("String " + i + " with some additional content");
            }
        });

        // Operation 3: Array operations
        System.out.println("\n=== Array Operations ===");
        monitorMemoryUsage(() -> {
            List<int[]> arrays = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                arrays.add(new int[1000]);
            }
        });
    }

    private static void monitorMemoryUsage(Runnable operation) {
        Runtime runtime = Runtime.getRuntime();

        // Force GC to get baseline
        System.gc();

        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();

        // Perform operation
        operation.run();

        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = afterMemory - beforeMemory;

        System.out.println("Memory used: " + memoryUsed / 1024 / 1024 + " MB");
    }
}
```

## Troubleshooting Performance Issues

### Common Performance Problems

```java
public class PerformanceTroubleshooting {
    public static void main(String[] args) {
        // Problem 1: Memory leaks
        System.out.println("=== Memory Leak Detection ===");
        detectMemoryLeak();

        // Problem 2: High CPU usage
        System.out.println("\n=== CPU Usage Analysis ===");
        analyzeCPUUsage();

        // Problem 3: Thread deadlocks
        System.out.println("\n=== Deadlock Detection ===");
        detectDeadlocks();

        // Problem 4: GC issues
        System.out.println("\n=== GC Issue Analysis ===");
        analyzeGCIssues();
    }

    private static void detectMemoryLeak() {
        Runtime runtime = Runtime.getRuntime();
        List<Object> leakList = new ArrayList<>();

        long initialMemory = runtime.totalMemory() - runtime.freeMemory();

        // Simulate memory leak
        for (int i = 0; i < 1000; i++) {
            leakList.add(new byte[1024]); // 1KB objects
        }

        System.gc();

        long finalMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryIncrease = finalMemory - initialMemory;

        System.out.println("Memory increase: " + memoryIncrease / 1024 + " KB");
        System.out.println("Potential memory leak detected: " + (memoryIncrease > 0));
    }

    private static void analyzeCPUUsage() {
        long startTime = System.currentTimeMillis();

        // Simulate CPU-intensive operation
        double result = 0;
        for (int i = 0; i < 1000000; i++) {
            result += Math.sqrt(i);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("CPU-intensive operation took: " + duration + " ms");
        System.out.println("Result: " + result);
    }

    private static void detectDeadlocks() {
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();

        // Check for deadlocks
        long[] deadlockedThreads = threadBean.findDeadlockedThreads();

        if (deadlockedThreads != null) {
            System.out.println("Deadlock detected! Thread IDs: " + Arrays.toString(deadlockedThreads));
        } else {
            System.out.println("No deadlocks detected");
        }
    }

    private static void analyzeGCIssues() {
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean gcBean : gcBeans) {
            long collectionCount = gcBean.getCollectionCount();
            long collectionTime = gcBean.getCollectionTime();

            System.out.println("GC: " + gcBean.getName());
            System.out.println("  Collections: " + collectionCount);
            System.out.println("  Total time: " + collectionTime + " ms");

            if (collectionCount > 0) {
                double avgTime = (double) collectionTime / collectionCount;
                System.out.println("  Average time: " + avgTime + " ms");
            }
        }
    }
}
```

### Performance Optimization Techniques

```java
public class PerformanceOptimization {
    public static void main(String[] args) {
        // Technique 1: Object pooling
        System.out.println("=== Object Pooling ===");
        testObjectPooling();

        // Technique 2: Caching
        System.out.println("\n=== Caching ===");
        testCaching();

        // Technique 3: Lazy loading
        System.out.println("\n=== Lazy Loading ===");
        testLazyLoading();

        // Technique 4: Batch processing
        System.out.println("\n=== Batch Processing ===");
        testBatchProcessing();
    }

    private static void testObjectPooling() {
        // Without pooling
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("Hello ").append(i);
        }
        long withoutPooling = System.currentTimeMillis() - startTime;

        // With pooling
        ObjectPool<StringBuilder> pool = new ObjectPool<>(StringBuilder::new);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            StringBuilder sb = pool.acquire();
            sb.setLength(0); // Reset
            sb.append("Hello ").append(i);
            pool.release(sb);
        }
        long withPooling = System.currentTimeMillis() - startTime;

        System.out.println("Without pooling: " + withoutPooling + " ms");
        System.out.println("With pooling: " + withPooling + " ms");
        System.out.println("Improvement: " + ((withoutPooling - withPooling) * 100.0 / withoutPooling) + "%");
    }

    private static void testCaching() {
        Map<Integer, String> cache = new HashMap<>();

        long startTime = System.currentTimeMillis();

        // Simulate expensive computation with caching
        for (int i = 0; i < 10000; i++) {
            int key = i % 100; // Repeat keys to test cache
            String value = cache.computeIfAbsent(key, k -> {
                // Simulate expensive computation
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Computed value for " + k;
            });
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Cached computation time: " + (endTime - startTime) + " ms");
        System.out.println("Cache size: " + cache.size());
    }

    private static void testLazyLoading() {
        // Lazy loading example
        LazyLoadedResource resource = new LazyLoadedResource();

        System.out.println("Resource created (not loaded yet)");

        // Access resource (triggers loading)
        String data = resource.getData();

        System.out.println("Resource loaded, data length: " + data.length());
    }

    private static void testBatchProcessing() {
        List<Integer> items = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            items.add(i);
        }

        // Process in batches
        int batchSize = 1000;
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < items.size(); i += batchSize) {
            int end = Math.min(i + batchSize, items.size());
            List<Integer> batch = items.subList(i, end);

            // Process batch
            processBatch(batch);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Batch processing time: " + (endTime - startTime) + " ms");
    }

    private static void processBatch(List<Integer> batch) {
        // Simulate batch processing
        int sum = batch.stream().mapToInt(Integer::intValue).sum();
    }
}

// Lazy loading implementation
class LazyLoadedResource {
    private String data = null;

    public String getData() {
        if (data == null) {
            // Simulate expensive loading
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            data = "Loaded data with " + System.currentTimeMillis() + " characters";
        }
        return data;
    }
}
```

## Conclusion

JVM performance tuning is a complex but essential aspect of Java application development. By understanding the various tuning parameters, monitoring tools, and optimization techniques, developers can significantly improve application performance.

### Key Takeaways:

- Performance tuning requires systematic measurement and analysis
- Different workloads require different optimization strategies
- Monitoring and profiling are essential for identifying bottlenecks
- Memory, GC, and thread tuning are the most critical areas
- Regular performance testing and optimization should be part of the development process

This comprehensive guide provides the foundation for effective JVM performance tuning and monitoring.
