# Java Memory Management - Deep Dive

## 1. Introduction to Java Memory Management

Java's memory management is automatic and handled by the Java Virtual Machine (JVM). Understanding how memory is managed is crucial for writing efficient Java applications and avoiding memory-related issues.

### Key Concepts

- **Heap Memory**: Where objects are allocated
- **Stack Memory**: Where method calls and local variables are stored
- **Garbage Collection**: Automatic memory cleanup
- **Memory Leaks**: Objects that can't be garbage collected
- **Performance Tuning**: Optimizing memory usage

## 2. Memory Areas in JVM

### Heap Memory

The heap is the runtime data area from which memory for all class instances and arrays is allocated.

```java
public class HeapMemoryExample {
    public static void main(String[] args) {
        // Objects are allocated in heap memory
        String largeString = new String("This is a large string that will be stored in heap");

        // Arrays are also stored in heap
        int[] largeArray = new int[1000000];

        // Custom objects
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add("String " + i);
        }

        // These objects will be garbage collected when no longer referenced
        largeString = null;
        largeArray = null;
        list = null;
    }
}
```

### Stack Memory

The stack is used for storing method calls, local variables, and partial results.

```java
public class StackMemoryExample {
    public static void main(String[] args) {
        // Local variables are stored in stack
        int localVar = 42;
        String localString = "Hello";

        // Method calls use stack memory
        recursiveMethod(5);
    }

    public static void recursiveMethod(int n) {
        // Each recursive call uses stack memory
        if (n > 0) {
            System.out.println("Recursive call: " + n);
            recursiveMethod(n - 1);  // Stack overflow if too deep
        }
    }
}
```

### Method Area

The method area stores class-level data including:

- Class definitions
- Method definitions
- Static variables
- Runtime constant pool

```java
public class MethodAreaExample {
    // Static variables are stored in method area
    public static final String CONSTANT = "This is a constant";
    public static int staticCounter = 0;

    // Class definition and method definitions are also stored here
    public static void incrementCounter() {
        staticCounter++;
    }
}
```

## 3. Object Lifecycle

### Object Creation

```java
public class ObjectLifecycleExample {
    public static void main(String[] args) {
        // 1. Object creation
        MyObject obj = new MyObject("Test");

        // 2. Object usage
        obj.doSomething();

        // 3. Object becomes eligible for garbage collection
        obj = null;

        // 4. Garbage collection (automatic)
        System.gc();  // Suggests garbage collection (not guaranteed)
    }
}

class MyObject {
    private String name;

    public MyObject(String name) {
        this.name = name;
        System.out.println("Object created: " + name);
    }

    public void doSomething() {
        System.out.println("Object " + name + " is doing something");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Object " + name + " is being garbage collected");
        super.finalize();
    }
}
```

### Object References

```java
public class ReferenceTypesExample {
    public static void main(String[] args) {
        // Strong reference
        String strongRef = new String("Strong Reference");

        // Weak reference
        WeakReference<String> weakRef = new WeakReference<>(new String("Weak Reference"));

        // Soft reference
        SoftReference<String> softRef = new SoftReference<>(new String("Soft Reference"));

        // Phantom reference
        ReferenceQueue<String> queue = new ReferenceQueue<>();
        PhantomReference<String> phantomRef = new PhantomReference<>(new String("Phantom Reference"), queue);

        // Accessing references
        System.out.println("Strong: " + strongRef);
        System.out.println("Weak: " + weakRef.get());
        System.out.println("Soft: " + softRef.get());
        System.out.println("Phantom: " + phantomRef.get());  // Always null

        // Making objects eligible for GC
        strongRef = null;

        // Force garbage collection for demonstration
        System.gc();

        // Check if weak reference is still available
        System.out.println("Weak after GC: " + weakRef.get());
    }
}
```

## 4. Garbage Collection

### Garbage Collection Process

```java
public class GarbageCollectionExample {
    public static void main(String[] args) {
        // Create objects that will be garbage collected
        for (int i = 0; i < 1000; i++) {
            createTemporaryObject(i);
        }

        // Get memory information
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;

        System.out.println("Total Memory: " + totalMemory / 1024 / 1024 + " MB");
        System.out.println("Free Memory: " + freeMemory / 1024 / 1024 + " MB");
        System.out.println("Used Memory: " + usedMemory / 1024 / 1024 + " MB");

        // Suggest garbage collection
        System.gc();

        // Check memory after GC
        freeMemory = runtime.freeMemory();
        usedMemory = totalMemory - freeMemory;
        System.out.println("After GC - Used Memory: " + usedMemory / 1024 / 1024 + " MB");
    }

    private static void createTemporaryObject(int id) {
        // This object becomes eligible for GC when method ends
        String temp = "Temporary object " + id;
        // Do something with temp
    }
}
```

### Garbage Collection Types

```java
public class GCTypesExample {
    public static void main(String[] args) {
        // Young Generation (Eden, Survivor spaces)
        createYoungGenObjects();

        // Old Generation (Tenured space)
        createOldGenObjects();

        // Permanent Generation (Metaspace in Java 8+)
        createPermanentGenObjects();
    }

    private static void createYoungGenObjects() {
        // These objects typically go to young generation
        for (int i = 0; i < 1000; i++) {
            String obj = new String("Young object " + i);
        }
    }

    private static void createOldGenObjects() {
        // Objects that survive multiple GC cycles move to old generation
        List<String> longLivedObjects = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            longLivedObjects.add("Long-lived object " + i);
        }

        // Force multiple GC cycles to promote objects
        for (int i = 0; i < 10; i++) {
            System.gc();
        }
    }

    private static void createPermanentGenObjects() {
        // Class definitions and static data
        // This is handled automatically by JVM
    }
}
```

## 5. Memory Leaks and Prevention

### Common Memory Leak Patterns

```java
public class MemoryLeakExample {
    // Static collections can cause memory leaks
    private static final List<Object> staticList = new ArrayList<>();

    // Event listeners not properly removed
    private static final Map<String, List<EventListener>> listeners = new HashMap<>();

    public static void main(String[] args) {
        // 1. Static collection memory leak
        for (int i = 0; i < 10000; i++) {
            staticList.add(new LargeObject("Object " + i));
        }

        // 2. Event listener memory leak
        addEventListener("event1", new EventListener() {
            @Override
            public void onEvent() {
                System.out.println("Event occurred");
            }
        });

        // 3. Thread local memory leak
        ThreadLocal<byte[]> threadLocal = new ThreadLocal<>();
        threadLocal.set(new byte[1024 * 1024]); // 1MB

        // 4. Database connection leak
        // Connection conn = DriverManager.getConnection(url);
        // Missing: conn.close();
    }

    public static void addEventListener(String event, EventListener listener) {
        listeners.computeIfAbsent(event, k -> new ArrayList<>()).add(listener);
    }

    // Proper cleanup method
    public static void cleanup() {
        staticList.clear();
        listeners.clear();
    }
}

class LargeObject {
    private String name;
    private byte[] data = new byte[1024 * 1024]; // 1MB

    public LargeObject(String name) {
        this.name = name;
    }
}

interface EventListener {
    void onEvent();
}
```

### Memory Leak Prevention

```java
public class MemoryLeakPrevention {
    public static void main(String[] args) {
        // 1. Use WeakHashMap for caches
        WeakHashMap<String, Object> cache = new WeakHashMap<>();
        cache.put("key1", new Object());

        // 2. Proper resource management with try-with-resources
        try (FileInputStream fis = new FileInputStream("test.txt")) {
            // Use the resource
            int data = fis.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3. Remove event listeners
        EventSource source = new EventSource();
        EventListener listener = new EventListener() {
            @Override
            public void onEvent() {
                System.out.println("Event");
            }
        };

        source.addEventListener(listener);
        // ... use the listener
        source.removeEventListener(listener); // Important!

        // 4. Use soft references for caches
        Map<String, SoftReference<Object>> softCache = new HashMap<>();
        softCache.put("key", new SoftReference<>(new Object()));
    }
}

class EventSource {
    private List<EventListener> listeners = new ArrayList<>();

    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }

    public void removeEventListener(EventListener listener) {
        listeners.remove(listener);
    }
}
```

## 6. Memory Monitoring and Profiling

### Runtime Memory Information

```java
public class MemoryMonitoringExample {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();

        // Get memory information
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;

        System.out.println("=== Memory Information ===");
        System.out.println("Max Memory: " + formatBytes(maxMemory));
        System.out.println("Total Memory: " + formatBytes(totalMemory));
        System.out.println("Free Memory: " + formatBytes(freeMemory));
        System.out.println("Used Memory: " + formatBytes(usedMemory));

        // Memory usage percentage
        double usagePercentage = (double) usedMemory / totalMemory * 100;
        System.out.println("Memory Usage: " + String.format("%.2f", usagePercentage) + "%");

        // Available processors
        int processors = runtime.availableProcessors();
        System.out.println("Available Processors: " + processors);

        // Monitor memory over time
        monitorMemoryUsage();
    }

    private static String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return bytes / 1024 + " KB";
        if (bytes < 1024 * 1024 * 1024) return bytes / (1024 * 1024) + " MB";
        return bytes / (1024 * 1024 * 1024) + " GB";
    }

    private static void monitorMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();

        System.out.println("\n=== Memory Monitoring ===");
        for (int i = 0; i < 5; i++) {
            long usedMemory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Iteration " + (i + 1) + ": " + formatBytes(usedMemory));

            // Create some objects
            List<String> tempList = new ArrayList<>();
            for (int j = 0; j < 1000; j++) {
                tempList.add("String " + j);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
```

### Memory Profiling Tools

```java
public class MemoryProfilingExample {
    public static void main(String[] args) {
        // Enable verbose garbage collection
        // Add JVM argument: -verbose:gc

        // Enable heap dump on OutOfMemoryError
        // Add JVM argument: -XX:+HeapDumpOnOutOfMemoryError

        // Set heap dump path
        // Add JVM argument: -XX:HeapDumpPath=/path/to/dumps

        // Enable GC logging
        // Add JVM arguments: -Xloggc:gc.log -XX:+PrintGCDetails -XX:+PrintGCTimeStamps

        // Create objects for profiling
        createObjectsForProfiling();

        // Force garbage collection
        System.gc();

        // Get memory usage
        printMemoryUsage();
    }

    private static void createObjectsForProfiling() {
        List<Object> objects = new ArrayList<>();

        // Create different types of objects
        for (int i = 0; i < 1000; i++) {
            if (i % 3 == 0) {
                objects.add(new String("String object " + i));
            } else if (i % 3 == 1) {
                objects.add(new Integer(i));
            } else {
                objects.add(new ArrayList<String>());
            }
        }

        System.out.println("Created " + objects.size() + " objects for profiling");
    }

    private static void printMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory used: " + usedMemory / 1024 / 1024 + " MB");
    }
}
```

## 7. Performance Tuning

### JVM Memory Tuning

```java
public class MemoryTuningExample {
    public static void main(String[] args) {
        // Common JVM tuning parameters:

        // 1. Set initial heap size
        // -Xms512m

        // 2. Set maximum heap size
        // -Xmx2g

        // 3. Set young generation size
        // -Xmn256m

        // 4. Set survivor ratio
        // -XX:SurvivorRatio=8

        // 5. Set garbage collector
        // -XX:+UseG1GC (G1 Garbage Collector)
        // -XX:+UseParallelGC (Parallel Garbage Collector)
        // -XX:+UseConcMarkSweepGC (CMS Garbage Collector)

        // 6. Set GC pause time target
        // -XX:MaxGCPauseMillis=200

        // Demonstrate memory-efficient programming
        demonstrateMemoryEfficientProgramming();
    }

    private static void demonstrateMemoryEfficientProgramming() {
        // 1. Use primitive types when possible
        int[] primitiveArray = new int[1000];  // More efficient
        Integer[] objectArray = new Integer[1000];  // Less efficient

        // 2. Reuse objects
        StringBuilder reusableBuilder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            reusableBuilder.setLength(0);  // Clear and reuse
            reusableBuilder.append("String ").append(i);
        }

        // 3. Use appropriate collection sizes
        ArrayList<String> sizedList = new ArrayList<>(1000);  // Pre-size

        // 4. Use object pooling for expensive objects
        ObjectPool<ExpensiveObject> pool = new ObjectPool<>(10);
        ExpensiveObject obj = pool.borrow();
        // Use the object
        pool.returnObject(obj);
    }
}

class ExpensiveObject {
    private byte[] data = new byte[1024 * 1024]; // 1MB

    public void reset() {
        // Reset object state for reuse
    }
}

class ObjectPool<T> {
    private final Queue<T> pool;
    private final int maxSize;

    public ObjectPool(int maxSize) {
        this.maxSize = maxSize;
        this.pool = new LinkedList<>();
    }

    public T borrow() {
        return pool.poll();
    }

    public void returnObject(T obj) {
        if (pool.size() < maxSize) {
            pool.offer(obj);
        }
    }
}
```

### Memory-Efficient Data Structures

```java
public class MemoryEfficientDataStructures {
    public static void main(String[] args) {
        // 1. Use primitive arrays instead of object arrays
        int[] numbers = new int[1000000];  // 4MB
        Integer[] objectNumbers = new Integer[1000000];  // Much more memory

        // 2. Use appropriate data structures
        // For small sets, use array-based structures
        List<String> smallList = new ArrayList<>();

        // For large sets with frequent lookups, use HashSet
        Set<String> largeSet = new HashSet<>();

        // For sorted data, use TreeSet
        Set<String> sortedSet = new TreeSet<>();

        // 3. Use weak references for caches
        Map<String, WeakReference<Object>> weakCache = new HashMap<>();

        // 4. Use soft references for memory-sensitive caches
        Map<String, SoftReference<Object>> softCache = new HashMap<>();

        // 5. Use flyweight pattern for shared objects
        FlyweightFactory factory = new FlyweightFactory();
        Flyweight flyweight1 = factory.getFlyweight("shared");
        Flyweight flyweight2 = factory.getFlyweight("shared");
        // flyweight1 and flyweight2 reference the same object
    }
}

interface Flyweight {
    void operation();
}

class ConcreteFlyweight implements Flyweight {
    private String intrinsicState;

    public ConcreteFlyweight(String state) {
        this.intrinsicState = state;
    }

    @Override
    public void operation() {
        System.out.println("Operation with state: " + intrinsicState);
    }
}

class FlyweightFactory {
    private Map<String, Flyweight> flyweights = new HashMap<>();

    public Flyweight getFlyweight(String key) {
        return flyweights.computeIfAbsent(key, ConcreteFlyweight::new);
    }
}
```

## 8. Best Practices

### Memory Management Best Practices

```java
public class MemoryBestPractices {

    // 1. Avoid memory leaks
    public static void avoidMemoryLeaks() {
        // Don't store references in static collections
        // static List<Object> staticList = new ArrayList<>(); // BAD

        // Use weak references for caches
        WeakHashMap<String, Object> cache = new WeakHashMap<>();

        // Remove event listeners
        EventSource source = new EventSource();
        EventListener listener = event -> System.out.println("Event");
        source.addEventListener(listener);
        // ... use listener
        source.removeEventListener(listener); // Important!
    }

    // 2. Use appropriate data types
    public static void useAppropriateDataTypes() {
        // Use primitives when possible
        int primitive = 42;  // 4 bytes
        Integer object = 42;  // 16+ bytes

        // Use appropriate collections
        List<String> smallList = new ArrayList<>();  // For small lists
        Set<String> uniqueSet = new HashSet<>();     // For unique elements
        Map<String, Integer> lookupMap = new HashMap<>(); // For key-value lookups
    }

    // 3. Manage resources properly
    public static void manageResources() {
        // Use try-with-resources
        try (FileInputStream fis = new FileInputStream("file.txt")) {
            // Use the resource
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close database connections
        // Connection conn = getConnection();
        // try {
        //     // Use connection
        // } finally {
        //     conn.close();
        // }
    }

    // 4. Optimize object creation
    public static void optimizeObjectCreation() {
        // Reuse objects when possible
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            builder.setLength(0);  // Clear and reuse
            builder.append("String ").append(i);
        }

        // Use object pooling for expensive objects
        // ObjectPool<ExpensiveObject> pool = new ObjectPool<>();
        // ExpensiveObject obj = pool.borrow();
        // // Use object
        // pool.returnObject(obj);
    }

    // 5. Monitor memory usage
    public static void monitorMemory() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();

        // Log memory usage
        System.out.println("Memory used: " + usedMemory / 1024 / 1024 + " MB");

        // Set up memory monitoring
        // -XX:+PrintGCDetails
        // -XX:+PrintGCTimeStamps
        // -Xloggc:gc.log
    }
}
```

## Summary

This section covered comprehensive Java memory management:

- **Memory Areas**: Heap, Stack, Method Area
- **Object Lifecycle**: Creation, usage, garbage collection
- **Garbage Collection**: Types and processes
- **Memory Leaks**: Common patterns and prevention
- **Monitoring**: Runtime information and profiling
- **Performance Tuning**: JVM parameters and optimization
- **Best Practices**: Efficient memory usage

Understanding Java memory management is essential for building high-performance, scalable applications and avoiding memory-related issues.
