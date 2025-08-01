# Advanced Java Concepts - Deep Dive

## 1. Introduction to Advanced Java

Advanced Java concepts build upon the fundamentals to provide powerful features for building complex, enterprise-level applications.

### Key Advanced Concepts

- **Generics**: Type-safe collections and methods
- **Annotations**: Metadata for classes, methods, and fields
- **Reflection**: Runtime inspection and manipulation of classes
- **Multithreading**: Concurrent programming
- **Lambda Expressions**: Functional programming features
- **Streams**: Data processing pipelines

## 2. Generics

Generics provide type safety and eliminate the need for casting when working with collections.

### Basic Generics

```java
public class GenericsExample {
    public static void main(String[] args) {
        // Generic class usage
        Box<String> stringBox = new Box<>("Hello");
        Box<Integer> intBox = new Box<>(42);

        // Generic method usage
        String[] stringArray = {"Apple", "Banana", "Orange"};
        Integer[] intArray = {1, 2, 3, 4, 5};

        System.out.println("First string: " + getFirstElement(stringArray));
        System.out.println("First integer: " + getFirstElement(intArray));
    }

    // Generic method
    public static <T> T getFirstElement(T[] array) {
        if (array.length > 0) {
            return array[0];
        }
        return null;
    }
}

// Generic class
class Box<T> {
    private T content;

    public Box(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }
}
```

### Bounded Generics

```java
public class BoundedGenericsExample {
    public static void main(String[] args) {
        // Using bounded generics
        NumberBox<Integer> intBox = new NumberBox<>(42);
        NumberBox<Double> doubleBox = new NumberBox<>(3.14);

        // Wildcards
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5);
        List<Double> doubleList = Arrays.asList(1.1, 2.2, 3.3);

        System.out.println("Sum of integers: " + sumOfNumbers(intList));
        System.out.println("Sum of doubles: " + sumOfNumbers(doubleList));
    }

    // Upper bounded wildcard
    public static double sumOfNumbers(List<? extends Number> numbers) {
        double sum = 0.0;
        for (Number number : numbers) {
            sum += number.doubleValue();
        }
        return sum;
    }
}

// Bounded generic class
class NumberBox<T extends Number> {
    private T number;

    public NumberBox(T number) {
        this.number = number;
    }

    public T getNumber() {
        return number;
    }
}
```

## 3. Annotations

Annotations provide metadata about program elements.

### Built-in Annotations

```java
public class AnnotationsExample {
    public static void main(String[] args) {
        // Using annotations
        @SuppressWarnings("unused")
        String unusedVariable = "This variable is intentionally unused";

        // Override annotation
        ChildClass child = new ChildClass();
        child.display();

        // Functional interface
        Calculator calculator = (a, b) -> a + b;
        System.out.println("Result: " + calculator.calculate(5, 3));
    }

    @Deprecated
    public static void oldMethod() {
        System.out.println("This method is deprecated");
    }
}

// Override annotation
class ParentClass {
    public void display() {
        System.out.println("Parent display");
    }
}

class ChildClass extends ParentClass {
    @Override
    public void display() {
        System.out.println("Child display");
    }
}

// Functional interface annotation
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}
```

### Custom Annotations

```java
public class CustomAnnotationsExample {
    public static void main(String[] args) {
        // Using custom annotations
        @Author(name = "John Doe", date = "2024-01-15")
        class MyClass {
            @Version(major = 1, minor = 0)
            public void method() {
                System.out.println("Method with version annotation");
            }
        }

        // Processing annotations at runtime
        processAnnotations(MyClass.class);
    }

    public static void processAnnotations(Class<?> clazz) {
        // Check for class-level annotations
        if (clazz.isAnnotationPresent(Author.class)) {
            Author author = clazz.getAnnotation(Author.class);
            System.out.println("Author: " + author.name());
            System.out.println("Date: " + author.date());
        }
    }
}

// Custom annotation for author information
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Author {
    String name();
    String date();
}

// Custom annotation for version information
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Version {
    int major();
    int minor() default 0;
}
```

## 4. Reflection

Reflection allows you to inspect and manipulate classes, methods, and fields at runtime.

### Basic Reflection

```java
public class ReflectionExample {
    public static void main(String[] args) {
        // Get class information
        Class<?> stringClass = String.class;
        System.out.println("Class name: " + stringClass.getName());
        System.out.println("Simple name: " + stringClass.getSimpleName());

        // Get class modifiers
        int modifiers = stringClass.getModifiers();
        System.out.println("Is public: " + Modifier.isPublic(modifiers));

        // Inspect a custom class
        inspectClass(Person.class);
    }

    public static void inspectClass(Class<?> clazz) {
        System.out.println("\n=== Inspecting " + clazz.getSimpleName() + " ===");

        // Get constructors
        Constructor<?>[] constructors = clazz.getConstructors();
        System.out.println("Constructors:");
        for (Constructor<?> constructor : constructors) {
            System.out.println("  " + constructor);
        }

        // Get methods
        Method[] methods = clazz.getMethods();
        System.out.println("Methods:");
        for (Method method : methods) {
            System.out.println("  " + method.getName() + " -> " + method.getReturnType());
        }

        // Get fields
        Field[] fields = clazz.getDeclaredFields();
        System.out.println("Fields:");
        for (Field field : fields) {
            System.out.println("  " + field.getType() + " " + field.getName());
        }
    }
}

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
}
```

### Dynamic Object Creation

```java
public class DynamicReflectionExample {
    public static void main(String[] args) {
        try {
            // Create object dynamically
            Class<?> personClass = Class.forName("Person");
            Constructor<?> constructor = personClass.getConstructor(String.class, int.class);
            Object person = constructor.newInstance("John Doe", 30);

            System.out.println("Created person: " + person);

            // Invoke methods dynamically
            Method getNameMethod = personClass.getMethod("getName");
            String name = (String) getNameMethod.invoke(person);
            System.out.println("Name: " + name);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## 5. Multithreading Basics

Multithreading allows programs to execute multiple threads concurrently.

### Thread Creation

```java
public class MultithreadingExample {
    public static void main(String[] args) {
        // Method 1: Extending Thread class
        MyThread thread1 = new MyThread("Thread-1");
        thread1.start();

        // Method 2: Implementing Runnable interface
        MyRunnable runnable = new MyRunnable();
        Thread thread2 = new Thread(runnable, "Thread-2");
        thread2.start();

        // Method 3: Using lambda expressions
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Lambda thread: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Thread-3");
        thread3.start();

        // Wait for threads to complete
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("All threads completed");
    }
}

// Extending Thread class
class MyThread extends Thread {
    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(getName() + ": " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

// Implementing Runnable interface
class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
```

### Thread Synchronization

```java
public class ThreadSynchronizationExample {
    public static void main(String[] args) {
        // Shared resource
        Counter counter = new Counter();

        // Create multiple threads
        Thread thread1 = new Thread(new IncrementTask(counter), "Increment-1");
        Thread thread2 = new Thread(new IncrementTask(counter), "Increment-2");
        Thread thread3 = new Thread(new DecrementTask(counter), "Decrement-1");

        // Start threads
        thread1.start();
        thread2.start();
        thread3.start();

        // Wait for completion
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Final count: " + counter.getCount());
    }
}

// Shared resource
class Counter {
    private int count = 0;

    // Synchronized method
    public synchronized void increment() {
        count++;
        System.out.println(Thread.currentThread().getName() + " incremented to: " + count);
    }

    // Synchronized block
    public void decrement() {
        synchronized (this) {
            count--;
            System.out.println(Thread.currentThread().getName() + " decremented to: " + count);
        }
    }

    public int getCount() {
        return count;
    }
}

// Task classes
class IncrementTask implements Runnable {
    private Counter counter;

    public IncrementTask(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            counter.increment();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

class DecrementTask implements Runnable {
    private Counter counter;

    public DecrementTask(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            counter.decrement();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
```

## 6. Lambda Expressions

Lambda expressions provide a concise way to implement functional interfaces.

### Basic Lambda Expressions

```java
public class LambdaExpressionsExample {
    public static void main(String[] args) {
        // Functional interface with lambda
        Calculator add = (a, b) -> a + b;
        Calculator subtract = (a, b) -> a - b;
        Calculator multiply = (a, b) -> a * b;
        Calculator divide = (a, b) -> b != 0 ? a / b : 0;

        System.out.println("Addition: " + add.calculate(10, 5));
        System.out.println("Subtraction: " + subtract.calculate(10, 5));
        System.out.println("Multiplication: " + multiply.calculate(10, 5));
        System.out.println("Division: " + divide.calculate(10, 5));

        // Lambda with multiple statements
        Processor processor = (str) -> {
            String result = str.toUpperCase();
            System.out.println("Processing: " + result);
            return result;
        };

        String result = processor.process("hello world");
        System.out.println("Result: " + result);

        // Lambda with no parameters
        Runnable task = () -> System.out.println("Executing task");
        task.run();
    }
}

// Functional interfaces
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}

@FunctionalInterface
interface Processor {
    String process(String input);
}
```

### Lambda with Collections

```java
public class LambdaWithCollectionsExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");

        // Using lambda with forEach
        System.out.println("All names:");
        names.forEach(name -> System.out.println(name));

        // Using method reference
        System.out.println("\nAll names (method reference):");
        names.forEach(System.out::println);

        // Filtering with lambda
        System.out.println("\nNames starting with 'A':");
        names.stream()
             .filter(name -> name.startsWith("A"))
             .forEach(System.out::println);

        // Mapping with lambda
        System.out.println("\nName lengths:");
        names.stream()
             .map(name -> name.length())
             .forEach(length -> System.out.println(length));

        // Custom functional interface usage
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Filter even numbers
        List<Integer> evenNumbers = filterList(numbers, n -> n % 2 == 0);
        System.out.println("Even numbers: " + evenNumbers);

        // Filter numbers greater than 5
        List<Integer> greaterThan5 = filterList(numbers, n -> n > 5);
        System.out.println("Numbers > 5: " + greaterThan5);
    }

    // Generic method using functional interface
    public static <T> List<T> filterList(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }
}

// Custom functional interface
@FunctionalInterface
interface Predicate<T> {
    boolean test(T t);
}
```

## 7. Streams API

Streams provide a powerful way to process collections of data.

### Basic Stream Operations

```java
public class StreamsExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve", "Frank");

        // Basic stream operations
        System.out.println("=== Basic Stream Operations ===");

        // Filter
        List<String> longNames = names.stream()
                                     .filter(name -> name.length() > 4)
                                     .collect(Collectors.toList());
        System.out.println("Long names: " + longNames);

        // Map
        List<Integer> nameLengths = names.stream()
                                        .map(String::length)
                                        .collect(Collectors.toList());
        System.out.println("Name lengths: " + nameLengths);

        // Sort
        List<String> sortedNames = names.stream()
                                       .sorted()
                                       .collect(Collectors.toList());
        System.out.println("Sorted names: " + sortedNames);

        // Terminal operations
        System.out.println("\n=== Terminal Operations ===");

        // Count
        long count = names.stream().count();
        System.out.println("Total names: " + count);

        // AnyMatch, AllMatch, NoneMatch
        boolean anyStartsWithA = names.stream().anyMatch(name -> name.startsWith("A"));
        boolean allLongerThan3 = names.stream().allMatch(name -> name.length() > 3);
        boolean noneStartsWithZ = names.stream().noneMatch(name -> name.startsWith("Z"));

        System.out.println("Any starts with 'A': " + anyStartsWithA);
        System.out.println("All longer than 3: " + allLongerThan3);
        System.out.println("None starts with 'Z': " + noneStartsWithZ);

        // Reduce
        Optional<String> longest = names.stream()
                                       .reduce((a, b) -> a.length() > b.length() ? a : b);
        System.out.println("Longest name: " + longest.orElse("None"));

        // Collect to different collections
        System.out.println("\n=== Collecting to Different Collections ===");

        // To Set
        Set<String> nameSet = names.stream().collect(Collectors.toSet());
        System.out.println("Name set: " + nameSet);

        // To Map
        Map<String, Integer> nameLengthMap = names.stream()
                                                 .collect(Collectors.toMap(
                                                     name -> name,
                                                     String::length
                                                 ));
        System.out.println("Name length map: " + nameLengthMap);

        // Grouping
        Map<Integer, List<String>> groupedByLength = names.stream()
                                                         .collect(Collectors.groupingBy(String::length));
        System.out.println("Grouped by length: " + groupedByLength);
    }
}
```

## Summary

This section covered advanced Java concepts:

- **Generics**: Type-safe programming with parameterized types
- **Annotations**: Metadata for program elements
- **Reflection**: Runtime inspection and manipulation
- **Multithreading**: Concurrent programming
- **Lambda Expressions**: Functional programming features
- **Streams**: Data processing pipelines

These advanced concepts provide powerful tools for building sophisticated, enterprise-level Java applications with better type safety, performance, and maintainability.
