import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Advanced Java Concepts Demo
 * This program demonstrates generics, annotations, reflection, 
 * multithreading, lambda expressions, and streams.
 */

// Custom annotation for demonstration
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@interface DemoAnnotation {
    String value() default "";
    int priority() default 0;
}

// Generic class for demonstration
class GenericContainer<T> {
    private T content;
    
    public GenericContainer(T content) {
        this.content = content;
    }
    
    public T getContent() {
        return content;
    }
    
    public void setContent(T content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return "GenericContainer{" + content + "}";
    }
}

// Bounded generic class
class NumberContainer<T extends Number> {
    private T number;
    
    public NumberContainer(T number) {
        this.number = number;
    }
    
    public T getNumber() {
        return number;
    }
    
    public double getDoubleValue() {
        return number.doubleValue();
    }
}

// Class with annotations for reflection demo
@DemoAnnotation(value = "Main demo class", priority = 1)
class DemoClass {
    @DemoAnnotation(value = "Name field", priority = 2)
    private String name;
    
    @DemoAnnotation(value = "Age field", priority = 2)
    private int age;
    
    public DemoClass(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    @DemoAnnotation(value = "Get name method", priority = 3)
    public String getName() {
        return name;
    }
    
    @DemoAnnotation(value = "Get age method", priority = 3)
    public int getAge() {
        return age;
    }
    
    @Override
    public String toString() {
        return "DemoClass{name='" + name + "', age=" + age + "}";
    }
}

// Thread-safe counter for multithreading demo
class ThreadSafeCounter {
    private int count = 0;
    private final Object lock = new Object();
    
    public synchronized void increment() {
        count++;
        System.out.println(Thread.currentThread().getName() + " incremented to: " + count);
    }
    
    public void decrement() {
        synchronized (lock) {
            count--;
            System.out.println(Thread.currentThread().getName() + " decremented to: " + count);
        }
    }
    
    public int getCount() {
        return count;
    }
}

// Task classes for multithreading
class IncrementTask implements Runnable {
    private ThreadSafeCounter counter;
    private int iterations;
    
    public IncrementTask(ThreadSafeCounter counter, int iterations) {
        this.counter = counter;
        this.iterations = iterations;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
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
    private ThreadSafeCounter counter;
    private int iterations;
    
    public DecrementTask(ThreadSafeCounter counter, int iterations) {
        this.counter = counter;
        this.iterations = iterations;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            counter.decrement();
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

// Person class for streams demo
class Person {
    private String name;
    private int age;
    private String city;
    
    public Person(String name, int age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }
    
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCity() { return city; }
    
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", city='" + city + "'}";
    }
}

// Main demonstration class
public class AdvancedConceptsDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Advanced Java Concepts Demo ===\n");
        
        // 1. Generics demonstration
        demonstrateGenerics();
        
        // 2. Annotations demonstration
        demonstrateAnnotations();
        
        // 3. Reflection demonstration
        demonstrateReflection();
        
        // 4. Multithreading demonstration
        demonstrateMultithreading();
        
        // 5. Lambda expressions demonstration
        demonstrateLambdaExpressions();
        
        // 6. Streams demonstration
        demonstrateStreams();
        
        System.out.println("\n=== End of Advanced Java Concepts Demo ===");
    }
    
    /**
     * Demonstrates generics usage
     */
    public static void demonstrateGenerics() {
        System.out.println("1. GENERICS DEMONSTRATION:");
        System.out.println("==========================");
        
        // Generic container usage
        GenericContainer<String> stringContainer = new GenericContainer<>("Hello, Generics!");
        GenericContainer<Integer> intContainer = new GenericContainer<>(42);
        GenericContainer<Double> doubleContainer = new GenericContainer<>(3.14159);
        
        System.out.println("String container: " + stringContainer);
        System.out.println("Integer container: " + intContainer);
        System.out.println("Double container: " + doubleContainer);
        
        // Bounded generics
        NumberContainer<Integer> intNumber = new NumberContainer<>(100);
        NumberContainer<Double> doubleNumber = new NumberContainer<>(99.9);
        
        System.out.println("Integer number: " + intNumber.getNumber() + " (double: " + intNumber.getDoubleValue() + ")");
        System.out.println("Double number: " + doubleNumber.getNumber() + " (double: " + doubleNumber.getDoubleValue() + ")");
        
        // Generic methods
        List<String> stringList = Arrays.asList("Apple", "Banana", "Cherry");
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5);
        
        System.out.println("First string: " + getFirstElement(stringList));
        System.out.println("First integer: " + getFirstElement(intList));
        
        // Wildcards
        List<Number> numbers = Arrays.asList(1, 2.5, 3L, 4.0f);
        System.out.println("Sum of numbers: " + sumNumbers(numbers));
        
        System.out.println();
    }
    
    // Generic method
    public static <T> T getFirstElement(List<T> list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    // Method with wildcard
    public static double sumNumbers(List<? extends Number> numbers) {
        return numbers.stream()
                     .mapToDouble(Number::doubleValue)
                     .sum();
    }
    
    /**
     * Demonstrates annotations usage
     */
    public static void demonstrateAnnotations() {
        System.out.println("2. ANNOTATIONS DEMONSTRATION:");
        System.out.println("=============================");
        
        // Using built-in annotations
        @SuppressWarnings("unused")
        String unusedVariable = "This variable is intentionally unused";
        
        // Functional interface annotation
        @FunctionalInterface
        interface StringProcessor {
            String process(String input);
        }
        
        // Using lambda with functional interface
        StringProcessor upperCase = String::toUpperCase;
        StringProcessor reverse = str -> new StringBuilder(str).reverse().toString();
        
        System.out.println("Uppercase: " + upperCase.process("hello"));
        System.out.println("Reverse: " + reverse.process("hello"));
        
        // Custom annotation usage
        DemoClass demoObject = new DemoClass("John Doe", 30);
        System.out.println("Demo object: " + demoObject);
        
        System.out.println();
    }
    
    /**
     * Demonstrates reflection usage
     */
    public static void demonstrateReflection() {
        System.out.println("3. REFLECTION DEMONSTRATION:");
        System.out.println("============================");
        
        try {
            // Get class information
            Class<?> demoClass = DemoClass.class;
            System.out.println("Class name: " + demoClass.getName());
            System.out.println("Simple name: " + demoClass.getSimpleName());
            
            // Check for annotations
            if (demoClass.isAnnotationPresent(DemoAnnotation.class)) {
                DemoAnnotation annotation = demoClass.getAnnotation(DemoAnnotation.class);
                System.out.println("Class annotation: " + annotation.value() + " (priority: " + annotation.priority() + ")");
            }
            
            // Get constructors
            Constructor<?>[] constructors = demoClass.getConstructors();
            System.out.println("Constructors:");
            for (Constructor<?> constructor : constructors) {
                System.out.println("  " + constructor);
            }
            
            // Get methods
            Method[] methods = demoClass.getMethods();
            System.out.println("Methods:");
            for (Method method : methods) {
                if (method.isAnnotationPresent(DemoAnnotation.class)) {
                    DemoAnnotation annotation = method.getAnnotation(DemoAnnotation.class);
                    System.out.println("  " + method.getName() + " -> " + annotation.value());
                }
            }
            
            // Get fields
            Field[] fields = demoClass.getDeclaredFields();
            System.out.println("Fields:");
            for (Field field : fields) {
                if (field.isAnnotationPresent(DemoAnnotation.class)) {
                    DemoAnnotation annotation = field.getAnnotation(DemoAnnotation.class);
                    System.out.println("  " + field.getType().getSimpleName() + " " + field.getName() + " -> " + annotation.value());
                }
            }
            
            // Create object dynamically
            Constructor<?> constructor = demoClass.getConstructor(String.class, int.class);
            Object obj = constructor.newInstance("Jane Doe", 25);
            System.out.println("Dynamically created object: " + obj);
            
            // Invoke method dynamically
            Method getNameMethod = demoClass.getMethod("getName");
            String name = (String) getNameMethod.invoke(obj);
            System.out.println("Dynamically retrieved name: " + name);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates multithreading usage
     */
    public static void demonstrateMultithreading() {
        System.out.println("4. MULTITHREADING DEMONSTRATION:");
        System.out.println("================================");
        
        ThreadSafeCounter counter = new ThreadSafeCounter();
        
        // Create threads
        Thread incrementThread1 = new Thread(new IncrementTask(counter, 5), "Increment-1");
        Thread incrementThread2 = new Thread(new IncrementTask(counter, 3), "Increment-2");
        Thread decrementThread = new Thread(new DecrementTask(counter, 4), "Decrement-1");
        
        // Start threads
        incrementThread1.start();
        incrementThread2.start();
        decrementThread.start();
        
        // Wait for threads to complete
        try {
            incrementThread1.join();
            incrementThread2.join();
            decrementThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Final counter value: " + counter.getCount());
        
        // Lambda-based thread creation
        Thread lambdaThread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("Lambda thread: " + i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Lambda-Thread");
        
        lambdaThread.start();
        
        try {
            lambdaThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates lambda expressions usage
     */
    public static void demonstrateLambdaExpressions() {
        System.out.println("5. LAMBDA EXPRESSIONS DEMONSTRATION:");
        System.out.println("====================================");
        
        // Functional interfaces with lambdas
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        
        // Using lambda with forEach
        System.out.println("All names:");
        names.forEach(name -> System.out.println("  " + name));
        
        // Using method reference
        System.out.println("All names (method reference):");
        names.forEach(System.out::println);
        
        // Custom functional interface
        @FunctionalInterface
        interface StringFilter {
            boolean test(String str);
        }
        
        StringFilter startsWithA = str -> str.startsWith("A");
        StringFilter longerThan4 = str -> str.length() > 4;
        
        System.out.println("Names starting with 'A':");
        names.stream()
             .filter(startsWithA)
             .forEach(System.out::println);
        
        System.out.println("Names longer than 4 characters:");
        names.stream()
             .filter(longerThan4)
             .forEach(System.out::println);
        
        // Lambda with multiple parameters
        @FunctionalInterface
        interface BinaryOperation<T> {
            T operate(T a, T b);
        }
        
        BinaryOperation<Integer> add = (a, b) -> a + b;
        BinaryOperation<Integer> multiply = (a, b) -> a * b;
        BinaryOperation<String> concatenate = (a, b) -> a + " " + b;
        
        System.out.println("Add: " + add.operate(5, 3));
        System.out.println("Multiply: " + multiply.operate(5, 3));
        System.out.println("Concatenate: " + concatenate.operate("Hello", "World"));
        
        System.out.println();
    }
    
    /**
     * Demonstrates streams usage
     */
    public static void demonstrateStreams() {
        System.out.println("6. STREAMS DEMONSTRATION:");
        System.out.println("=========================");
        
        // Create sample data
        List<Person> people = Arrays.asList(
            new Person("Alice", 25, "New York"),
            new Person("Bob", 30, "Los Angeles"),
            new Person("Charlie", 35, "Chicago"),
            new Person("David", 28, "New York"),
            new Person("Eve", 32, "Los Angeles"),
            new Person("Frank", 27, "Chicago"),
            new Person("Grace", 29, "New York"),
            new Person("Henry", 33, "Los Angeles")
        );
        
        System.out.println("Original people list:");
        people.forEach(System.out::println);
        
        // Basic stream operations
        System.out.println("\n=== Basic Operations ===");
        
        // Filter
        List<Person> youngPeople = people.stream()
                                        .filter(p -> p.getAge() < 30)
                                        .collect(Collectors.toList());
        System.out.println("Young people (< 30): " + youngPeople);
        
        // Map
        List<String> names = people.stream()
                                  .map(Person::getName)
                                  .collect(Collectors.toList());
        System.out.println("Names: " + names);
        
        // Sort
        List<Person> sortedByAge = people.stream()
                                        .sorted(Comparator.comparing(Person::getAge))
                                        .collect(Collectors.toList());
        System.out.println("Sorted by age: " + sortedByAge);
        
        // Terminal operations
        System.out.println("\n=== Terminal Operations ===");
        
        // Count
        long count = people.stream().count();
        System.out.println("Total people: " + count);
        
        // Average age
        double avgAge = people.stream()
                             .mapToInt(Person::getAge)
                             .average()
                             .orElse(0.0);
        System.out.println("Average age: " + avgAge);
        
        // Max age
        int maxAge = people.stream()
                          .mapToInt(Person::getAge)
                          .max()
                          .orElse(0);
        System.out.println("Max age: " + maxAge);
        
        // Any match, all match, none match
        boolean anyFromNY = people.stream().anyMatch(p -> "New York".equals(p.getCity()));
        boolean allOver20 = people.stream().allMatch(p -> p.getAge() > 20);
        boolean noneOver50 = people.stream().noneMatch(p -> p.getAge() > 50);
        
        System.out.println("Any from NY: " + anyFromNY);
        System.out.println("All over 20: " + allOver20);
        System.out.println("None over 50: " + noneOver50);
        
        // Collecting to different collections
        System.out.println("\n=== Collecting to Different Collections ===");
        
        // To Set
        Set<String> cities = people.stream()
                                  .map(Person::getCity)
                                  .collect(Collectors.toSet());
        System.out.println("Unique cities: " + cities);
        
        // To Map
        Map<String, Integer> nameToAge = people.stream()
                                              .collect(Collectors.toMap(
                                                  Person::getName,
                                                  Person::getAge
                                              ));
        System.out.println("Name to age map: " + nameToAge);
        
        // Grouping
        Map<String, List<Person>> peopleByCity = people.stream()
                                                      .collect(Collectors.groupingBy(Person::getCity));
        System.out.println("People grouped by city: " + peopleByCity);
        
        // Partitioning
        Map<Boolean, List<Person>> partitionedByAge = people.stream()
                                                           .collect(Collectors.partitioningBy(p -> p.getAge() >= 30));
        System.out.println("Partitioned by age >= 30: " + partitionedByAge);
        
        // Advanced operations
        System.out.println("\n=== Advanced Operations ===");
        
        // Average age by city
        Map<String, Double> avgAgeByCity = people.stream()
                                                .collect(Collectors.groupingBy(
                                                    Person::getCity,
                                                    Collectors.averagingInt(Person::getAge)
                                                ));
        System.out.println("Average age by city: " + avgAgeByCity);
        
        // Count by city
        Map<String, Long> countByCity = people.stream()
                                             .collect(Collectors.groupingBy(
                                                 Person::getCity,
                                                 Collectors.counting()
                                             ));
        System.out.println("Count by city: " + countByCity);
        
        // Reduce example
        String allNames = people.stream()
                               .map(Person::getName)
                               .reduce("", (a, b) -> a + (a.isEmpty() ? "" : ", ") + b);
        System.out.println("All names concatenated: " + allNames);
        
        System.out.println();
    }
} 