# Java Advanced OOPs Concepts - Master Level Guide

## Table of Contents

1. [Introduction](#introduction)
2. [Generics](#generics)
3. [Collections Framework](#collections)
4. [Exception Handling](#exceptions)
5. [Lambda Expressions](#lambdas)
6. [Streams API](#streams)
7. [Reflection](#reflection)
8. [Annotations](#annotations)
9. [Design Patterns](#design-patterns)

## Introduction

Advanced OOPs concepts in Java extend beyond the basic four pillars (Encapsulation, Inheritance, Polymorphism, Abstraction) to include powerful features that make Java a robust and flexible language for enterprise development.

### Key Concepts

- **Generics**: Type-safe collections and methods
- **Collections Framework**: Data structures and algorithms
- **Exception Handling**: Robust error management
- **Lambda Expressions**: Functional programming features
- **Streams API**: Data processing pipelines
- **Reflection**: Runtime class inspection and manipulation

## Generics

### Basic Generics

```java
// Generic class
public class Box<T> {
    private T content;

    public Box(T content) {
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
        return "Box containing: " + content;
    }
}

// Generic method
public class GenericUtils {
    public static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static <T extends Comparable<T>> T findMax(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        T max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i].compareTo(max) > 0) {
                max = array[i];
            }
        }
        return max;
    }

    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
}
```

### Bounded Generics

```java
// Bounded type parameter
public class NumberBox<T extends Number> {
    private T number;

    public NumberBox(T number) {
        this.number = number;
    }

    public T getNumber() {
        return number;
    }

    public double getDoubleValue() {
        return number.doubleValue();
    }

    public boolean isPositive() {
        return number.doubleValue() > 0;
    }
}

// Multiple bounds
public class MultiBoundBox<T extends Number & Comparable<T>> {
    private T value;

    public MultiBoundBox(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public boolean isGreaterThan(T other) {
        return value.compareTo(other) > 0;
    }

    public double getDoubleValue() {
        return value.doubleValue();
    }
}

// Wildcards
public class WildcardExample {
    // Unbounded wildcard
    public static void printList(List<?> list) {
        for (Object item : list) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    // Upper bounded wildcard
    public static double sumOfNumbers(List<? extends Number> numbers) {
        double sum = 0.0;
        for (Number number : numbers) {
            sum += number.doubleValue();
        }
        return sum;
    }

    // Lower bounded wildcard
    public static void addNumbers(List<? super Integer> list) {
        list.add(1);
        list.add(2);
        list.add(3);
    }
}
```

### Generic Collections

```java
public class GenericCollectionsDemo {
    public static void main(String[] args) {
        // Generic ArrayList
        List<String> stringList = new ArrayList<>();
        stringList.add("Java");
        stringList.add("Python");
        stringList.add("C++");

        // Generic HashMap
        Map<String, Integer> wordCount = new HashMap<>();
        wordCount.put("Java", 1);
        wordCount.put("Python", 2);
        wordCount.put("C++", 3);

        // Generic Set
        Set<Integer> numberSet = new HashSet<>();
        numberSet.add(1);
        numberSet.add(2);
        numberSet.add(3);

        // Type-safe operations
        for (String word : stringList) {
            System.out.println(word.toUpperCase());
        }

        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
```

## Collections Framework

### List Implementations

```java
public class ListExamples {
    public static void main(String[] args) {
        // ArrayList - dynamic array
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Apple");
        arrayList.add("Banana");
        arrayList.add("Cherry");
        arrayList.add(1, "Blueberry"); // Insert at index 1

        // LinkedList - doubly linked list
        List<String> linkedList = new LinkedList<>();
        linkedList.add("First");
        linkedList.add("Second");
        linkedList.add("Third");

        // Vector - thread-safe dynamic array
        List<String> vector = new Vector<>();
        vector.add("Thread-safe");
        vector.add("Synchronized");

        // Stack - LIFO data structure
        Stack<String> stack = new Stack<>();
        stack.push("First");
        stack.push("Second");
        stack.push("Third");

        while (!stack.isEmpty()) {
            System.out.println("Popped: " + stack.pop());
        }
    }
}
```

### Set Implementations

```java
public class SetExamples {
    public static void main(String[] args) {
        // HashSet - unordered, no duplicates
        Set<String> hashSet = new HashSet<>();
        hashSet.add("Apple");
        hashSet.add("Banana");
        hashSet.add("Apple"); // Duplicate ignored
        hashSet.add("Cherry");

        // LinkedHashSet - ordered, no duplicates
        Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("First");
        linkedHashSet.add("Second");
        linkedHashSet.add("Third");

        // TreeSet - sorted, no duplicates
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("Zebra");
        treeSet.add("Apple");
        treeSet.add("Banana");
        // Will be sorted: Apple, Banana, Zebra

        // TreeSet with custom comparator
        Set<String> customTreeSet = new TreeSet<>((s1, s2) -> s2.compareTo(s1)); // Reverse order
        customTreeSet.add("Apple");
        customTreeSet.add("Banana");
        customTreeSet.add("Cherry");
    }
}
```

### Map Implementations

```java
public class MapExamples {
    public static void main(String[] args) {
        // HashMap - unordered key-value pairs
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Apple", 1);
        hashMap.put("Banana", 2);
        hashMap.put("Cherry", 3);

        // LinkedHashMap - ordered key-value pairs
        Map<String, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("First", 1);
        linkedHashMap.put("Second", 2);
        linkedHashMap.put("Third", 3);

        // TreeMap - sorted key-value pairs
        Map<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("Zebra", 3);
        treeMap.put("Apple", 1);
        treeMap.put("Banana", 2);

        // ConcurrentHashMap - thread-safe HashMap
        Map<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("Thread-safe", 1);
        concurrentHashMap.put("Concurrent", 2);

        // Map operations
        System.out.println("Contains key 'Apple': " + hashMap.containsKey("Apple"));
        System.out.println("Contains value 2: " + hashMap.containsValue(2));
        System.out.println("Value for 'Banana': " + hashMap.get("Banana"));

        // Iterating over map
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // Using forEach (Java 8+)
        hashMap.forEach((key, value) -> System.out.println(key + " -> " + value));
    }
}
```

### Custom Collections

```java
public class CustomCollection<T> implements Collection<T> {
    private List<T> elements = new ArrayList<>();

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return elements.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

    @Override
    public Object[] toArray() {
        return elements.toArray();
    }

    @Override
    public <E> E[] toArray(E[] a) {
        return elements.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return elements.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return elements.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return elements.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return elements.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return elements.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return elements.retainAll(c);
    }

    @Override
    public void clear() {
        elements.clear();
    }

    // Custom methods
    public T getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Collection is empty");
        }
        return elements.get(0);
    }

    public T getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Collection is empty");
        }
        return elements.get(elements.size() - 1);
    }
}
```

## Exception Handling

### Custom Exceptions

```java
// Custom checked exception
public class InsufficientFundsException extends Exception {
    private double requestedAmount;
    private double availableBalance;

    public InsufficientFundsException(double requestedAmount, double availableBalance) {
        super(String.format("Insufficient funds. Requested: $%.2f, Available: $%.2f",
                           requestedAmount, availableBalance));
        this.requestedAmount = requestedAmount;
        this.availableBalance = availableBalance;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }
}

// Custom unchecked exception
public class InvalidAccountException extends RuntimeException {
    private String accountNumber;

    public InvalidAccountException(String accountNumber) {
        super("Invalid account number: " + accountNumber);
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

// Exception handling example
public class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new InvalidAccountException(accountNumber);
        }
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        if (amount > balance) {
            throw new InsufficientFundsException(amount, balance);
        }

        balance -= amount;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
    }

    public double getBalance() {
        return balance;
    }
}
```

### Exception Handling Patterns

```java
public class ExceptionHandlingPatterns {

    // Try-with-resources pattern
    public static String readFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filename, e);
        }
    }

    // Multiple exception handling
    public static void processData(String data) {
        try {
            int number = Integer.parseInt(data);
            double result = Math.sqrt(number);
            System.out.println("Square root: " + result);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + data);
        } catch (IllegalArgumentException e) {
            System.err.println("Cannot calculate square root of negative number");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    // Exception chaining
    public static void performComplexOperation() {
        try {
            // Some complex operation
            throw new IOException("Database connection failed");
        } catch (IOException e) {
            throw new RuntimeException("Failed to perform operation", e);
        }
    }

    // Custom exception handling
    public static void handleBankOperations() {
        BankAccount account = new BankAccount("12345", 1000.0);

        try {
            account.withdraw(1500.0); // This will throw InsufficientFundsException
        } catch (InsufficientFundsException e) {
            System.err.println("Withdrawal failed: " + e.getMessage());
            System.err.println("Requested: $" + e.getRequestedAmount());
            System.err.println("Available: $" + e.getAvailableBalance());
        } catch (InvalidAccountException e) {
            System.err.println("Invalid account: " + e.getAccountNumber());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid argument: " + e.getMessage());
        }
    }
}
```

## Lambda Expressions

### Basic Lambda Expressions

```java
public class LambdaExamples {

    // Functional interfaces
    @FunctionalInterface
    public interface MathOperation {
        int operate(int a, int b);
    }

    @FunctionalInterface
    public interface StringProcessor {
        String process(String input);
    }

    @FunctionalInterface
    public interface Condition<T> {
        boolean test(T t);
    }

    public static void main(String[] args) {
        // Lambda expressions for math operations
        MathOperation addition = (a, b) -> a + b;
        MathOperation subtraction = (a, b) -> a - b;
        MathOperation multiplication = (a, b) -> a * b;
        MathOperation division = (a, b) -> b != 0 ? a / b : 0;

        System.out.println("10 + 5 = " + addition.operate(10, 5));
        System.out.println("10 - 5 = " + subtraction.operate(10, 5));
        System.out.println("10 * 5 = " + multiplication.operate(10, 5));
        System.out.println("10 / 5 = " + division.operate(10, 5));

        // Lambda expressions for string processing
        StringProcessor toUpperCase = str -> str.toUpperCase();
        StringProcessor reverse = str -> new StringBuilder(str).reverse().toString();
        StringProcessor addPrefix = str -> "Processed: " + str;

        System.out.println(toUpperCase.process("hello"));
        System.out.println(reverse.process("hello"));
        System.out.println(addPrefix.process("hello"));

        // Lambda expressions for conditions
        Condition<Integer> isEven = n -> n % 2 == 0;
        Condition<Integer> isPositive = n -> n > 0;
        Condition<String> isEmpty = str -> str == null || str.trim().isEmpty();

        System.out.println("Is 10 even? " + isEven.test(10));
        System.out.println("Is -5 positive? " + isPositive.test(-5));
        System.out.println("Is empty? " + isEmpty.test(""));
    }
}
```

### Lambda with Collections

```java
public class LambdaCollections {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Using lambda with forEach
        System.out.println("Names:");
        names.forEach(name -> System.out.println("Hello, " + name));

        // Using lambda with removeIf
        List<String> mutableNames = new ArrayList<>(names);
        mutableNames.removeIf(name -> name.length() > 4);
        System.out.println("Short names: " + mutableNames);

        // Using lambda with replaceAll
        List<String> upperCaseNames = new ArrayList<>(names);
        upperCaseNames.replaceAll(String::toUpperCase);
        System.out.println("Uppercase names: " + upperCaseNames);

        // Using lambda with sort
        List<String> sortedNames = new ArrayList<>(names);
        sortedNames.sort((s1, s2) -> s1.compareTo(s2));
        System.out.println("Sorted names: " + sortedNames);

        // Using lambda with filter and map
        List<Integer> evenSquares = numbers.stream()
            .filter(n -> n % 2 == 0)
            .map(n -> n * n)
            .collect(Collectors.toList());
        System.out.println("Even squares: " + evenSquares);
    }
}
```

## Streams API

### Basic Stream Operations

```java
public class StreamExamples {

    public static void main(String[] args) {
        List<String> words = Arrays.asList("Java", "Python", "C++", "JavaScript", "Ruby", "Go");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Filtering
        List<String> longWords = words.stream()
            .filter(word -> word.length() > 3)
            .collect(Collectors.toList());
        System.out.println("Long words: " + longWords);

        // Mapping
        List<Integer> wordLengths = words.stream()
            .map(String::length)
            .collect(Collectors.toList());
        System.out.println("Word lengths: " + wordLengths);

        // Sorting
        List<String> sortedWords = words.stream()
            .sorted()
            .collect(Collectors.toList());
        System.out.println("Sorted words: " + sortedWords);

        // Reducing
        int sum = numbers.stream()
            .reduce(0, Integer::sum);
        System.out.println("Sum: " + sum);

        // Finding
        Optional<String> firstLongWord = words.stream()
            .filter(word -> word.length() > 5)
            .findFirst();
        firstLongWord.ifPresent(word -> System.out.println("First long word: " + word));

        // Collecting to different collections
        Set<String> wordSet = words.stream()
            .collect(Collectors.toSet());
        System.out.println("Word set: " + wordSet);

        Map<String, Integer> wordLengthMap = words.stream()
            .collect(Collectors.toMap(
                word -> word,
                String::length
            ));
        System.out.println("Word length map: " + wordLengthMap);
    }
}
```

### Advanced Stream Operations

```java
public class AdvancedStreamExamples {

    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
            new Person("Alice", 25, "Engineer"),
            new Person("Bob", 30, "Manager"),
            new Person("Charlie", 35, "Engineer"),
            new Person("David", 28, "Designer"),
            new Person("Eve", 32, "Manager")
        );

        // Grouping by
        Map<String, List<Person>> peopleByJob = people.stream()
            .collect(Collectors.groupingBy(Person::getJob));
        System.out.println("People by job: " + peopleByJob);

        // Partitioning by
        Map<Boolean, List<Person>> peopleByAge = people.stream()
            .collect(Collectors.partitioningBy(person -> person.getAge() > 30));
        System.out.println("People by age (>30): " + peopleByAge);

        // Joining
        String allNames = people.stream()
            .map(Person::getName)
            .collect(Collectors.joining(", "));
        System.out.println("All names: " + allNames);

        // Averaging
        double averageAge = people.stream()
            .mapToInt(Person::getAge)
            .average()
            .orElse(0.0);
        System.out.println("Average age: " + averageAge);

        // FlatMap
        List<String> allCharacters = words.stream()
            .flatMap(word -> word.chars().mapToObj(ch -> String.valueOf((char) ch)))
            .collect(Collectors.toList());
        System.out.println("All characters: " + allCharacters);
    }
}

class Person {
    private String name;
    private int age;
    private String job;

    public Person(String name, int age, String job) {
        this.name = name;
        this.age = age;
        this.job = job;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getJob() { return job; }

    @Override
    public String toString() {
        return name + "(" + age + ")";
    }
}
```

## Reflection

### Basic Reflection

```java
public class ReflectionExamples {

    public static void main(String[] args) {
        // Getting class information
        Class<?> stringClass = String.class;
        System.out.println("Class name: " + stringClass.getName());
        System.out.println("Simple name: " + stringClass.getSimpleName());
        System.out.println("Package: " + stringClass.getPackage());
        System.out.println("Superclass: " + stringClass.getSuperclass());

        // Getting constructors
        Constructor<?>[] constructors = stringClass.getConstructors();
        System.out.println("Constructors:");
        for (Constructor<?> constructor : constructors) {
            System.out.println("  " + constructor);
        }

        // Getting methods
        Method[] methods = stringClass.getMethods();
        System.out.println("Methods:");
        for (Method method : methods) {
            System.out.println("  " + method.getName() + " -> " + method.getReturnType());
        }

        // Getting fields
        Field[] fields = stringClass.getDeclaredFields();
        System.out.println("Fields:");
        for (Field field : fields) {
            System.out.println("  " + field.getName() + " -> " + field.getType());
        }
    }

    // Creating objects using reflection
    public static Object createInstance(String className) throws Exception {
        Class<?> clazz = Class.forName(className);
        return clazz.getDeclaredConstructor().newInstance();
    }

    // Invoking methods using reflection
    public static Object invokeMethod(Object obj, String methodName, Object... args) throws Exception {
        Class<?>[] paramTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            paramTypes[i] = args[i].getClass();
        }

        Method method = obj.getClass().getMethod(methodName, paramTypes);
        return method.invoke(obj, args);
    }

    // Accessing private fields using reflection
    public static Object getPrivateField(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    public static void setPrivateField(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }
}
```

## Annotations

### Custom Annotations

```java
// Custom annotation
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Test {
    String description() default "";
    boolean enabled() default true;
    int priority() default 1;
}

// Annotation with multiple values
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Validation {
    String[] required() default {};
    int minLength() default 0;
    int maxLength() default Integer.MAX_VALUE;
    String pattern() default "";
}

// Using annotations
@Test(description = "Test user creation", priority = 1)
public class UserService {

    @Validation(required = {"name", "email"}, minLength = 3, maxLength = 50)
    public void createUser(String name, String email) {
        // Implementation
    }

    @Test(description = "Test user validation", enabled = false)
    public void validateUser() {
        // Implementation
    }
}

// Annotation processor
public class AnnotationProcessor {

    public static void processAnnotations(Class<?> clazz) {
        // Process class annotations
        if (clazz.isAnnotationPresent(Test.class)) {
            Test test = clazz.getAnnotation(Test.class);
            System.out.println("Class: " + clazz.getSimpleName());
            System.out.println("Description: " + test.description());
            System.out.println("Enabled: " + test.enabled());
            System.out.println("Priority: " + test.priority());
        }

        // Process method annotations
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                Test test = method.getAnnotation(Test.class);
                System.out.println("Method: " + method.getName());
                System.out.println("Description: " + test.description());
                System.out.println("Enabled: " + test.enabled());
            }

            if (method.isAnnotationPresent(Validation.class)) {
                Validation validation = method.getAnnotation(Validation.class);
                System.out.println("Method: " + method.getName());
                System.out.println("Required fields: " + Arrays.toString(validation.required()));
                System.out.println("Min length: " + validation.minLength());
                System.out.println("Max length: " + validation.maxLength());
            }
        }
    }
}
```

## Design Patterns

### Factory Pattern with Generics

```java
public interface Animal {
    void makeSound();
}

public class Dog implements Animal {
    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }
}

public class Cat implements Animal {
    @Override
    public void makeSound() {
        System.out.println("Meow!");
    }
}

public class Bird implements Animal {
    @Override
    public void makeSound() {
        System.out.println("Tweet!");
    }
}

// Generic factory
public class AnimalFactory {
    private Map<String, Class<? extends Animal>> animalTypes = new HashMap<>();

    public AnimalFactory() {
        animalTypes.put("dog", Dog.class);
        animalTypes.put("cat", Cat.class);
        animalTypes.put("bird", Bird.class);
    }

    public Animal createAnimal(String type) {
        Class<? extends Animal> animalClass = animalTypes.get(type.toLowerCase());
        if (animalClass == null) {
            throw new IllegalArgumentException("Unknown animal type: " + type);
        }

        try {
            return animalClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error creating animal: " + type, e);
        }
    }

    public <T extends Animal> T createAnimal(Class<T> animalClass) {
        try {
            return animalClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error creating animal: " + animalClass.getSimpleName(), e);
        }
    }
}
```

### Observer Pattern with Lambda

```java
public interface Observer<T> {
    void update(T data);
}

public class Subject<T> {
    private List<Observer<T>> observers = new ArrayList<>();
    private T data;

    public void addObserver(Observer<T> observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer<T> observer) {
        observers.remove(observer);
    }

    public void setData(T data) {
        this.data = data;
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer<T> observer : observers) {
            observer.update(data);
        }
    }
}

// Using lambda expressions with observer pattern
public class ObserverExample {
    public static void main(String[] args) {
        Subject<String> subject = new Subject<>();

        // Adding observers using lambda expressions
        subject.addObserver(data -> System.out.println("Observer 1: " + data));
        subject.addObserver(data -> System.out.println("Observer 2: " + data.toUpperCase()));
        subject.addObserver(data -> System.out.println("Observer 3: Length = " + data.length()));

        // Notifying observers
        subject.setData("Hello, World!");
    }
}
```

## Summary

This comprehensive guide covers:

1. **Generics**: Type-safe collections, bounded types, and wildcards
2. **Collections Framework**: Lists, Sets, Maps, and custom collections
3. **Exception Handling**: Custom exceptions and handling patterns
4. **Lambda Expressions**: Functional programming features
5. **Streams API**: Data processing and functional operations
6. **Reflection**: Runtime class inspection and manipulation
7. **Annotations**: Custom annotations and processors
8. **Design Patterns**: Factory and Observer patterns with modern Java features

Key takeaways:

- Use generics for type-safe collections and methods
- Leverage the Collections Framework for efficient data structures
- Implement proper exception handling for robust applications
- Use lambda expressions and streams for functional programming
- Apply reflection carefully for dynamic behavior
- Create custom annotations for metadata and processing
- Combine design patterns with modern Java features
