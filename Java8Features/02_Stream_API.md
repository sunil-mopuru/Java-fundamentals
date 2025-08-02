# Stream API in Java 8

The Stream API is one of the most powerful features introduced in Java 8. It provides a modern, functional approach to processing collections of data, enabling parallel processing and making code more readable and maintainable.

## Table of Contents

1. [What is the Stream API?](#what-is-the-stream-api)
2. [Stream vs Collection](#stream-vs-collection)
3. [Stream Operations](#stream-operations)
4. [Creating Streams](#creating-streams)
5. [Intermediate Operations](#intermediate-operations)
6. [Terminal Operations](#terminal-operations)
7. [Parallel Streams](#parallel-streams)
8. [Collectors](#collectors)
9. [Practical Examples](#practical-examples)
10. [Best Practices](#best-practices)
11. [Common Pitfalls](#common-pitfalls)
12. [Exercises](#exercises)

## What is the Stream API?

A Stream is a sequence of elements that supports sequential and parallel aggregate operations. Streams are designed to work with lambda expressions and provide a functional approach to processing data.

### Key Characteristics

- **Non-mutating**: Streams don't modify the source collection
- **Lazy evaluation**: Operations are only executed when needed
- **Functional**: Operations are stateless and don't have side effects
- **Parallelizable**: Can be processed in parallel for better performance

## Stream vs Collection

| Aspect | Collection | Stream |
|--------|------------|--------|
| **Storage** | Stores data | Doesn't store data |
| **Iteration** | External iteration | Internal iteration |
| **Traversal** | Can be traversed multiple times | Can be traversed only once |
| **Modification** | Can be modified | Immutable |
| **Purpose** | Data structure | Data processing |

## Stream Operations

Stream operations are divided into two categories:

1. **Intermediate Operations**: Return a new stream (lazy)
2. **Terminal Operations**: Produce a result or side effect (eager)

### Operation Pipeline

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

// Stream pipeline
List<String> result = names.stream()           // Source
    .filter(name -> name.length() > 4)         // Intermediate operation
    .map(String::toUpperCase)                  // Intermediate operation
    .collect(Collectors.toList());             // Terminal operation
```

## Creating Streams

### From Collections

```java
List<String> list = Arrays.asList("a", "b", "c");
Stream<String> stream = list.stream();
```

### From Arrays

```java
String[] array = {"a", "b", "c"};
Stream<String> stream = Arrays.stream(array);
```

### Using Stream.of()

```java
Stream<String> stream = Stream.of("a", "b", "c");
Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5);
```

### Using Stream.builder()

```java
Stream<String> stream = Stream.<String>builder()
    .add("a")
    .add("b")
    .add("c")
    .build();
```

### Using Stream.generate()

```java
// Infinite stream of random numbers
Stream<Double> randomNumbers = Stream.generate(Math::random);

// Infinite stream of "Hello"
Stream<String> hellos = Stream.generate(() -> "Hello");
```

### Using Stream.iterate()

```java
// Infinite stream starting from 0, incrementing by 1
Stream<Integer> numbers = Stream.iterate(0, n -> n + 1);

// Finite stream (first 10 numbers)
Stream<Integer> firstTen = Stream.iterate(0, n -> n + 1).limit(10);
```

### From Files

```java
try (Stream<String> lines = Files.lines(Paths.get("file.txt"))) {
    lines.forEach(System.out::println);
}
```

### From Primitives

```java
IntStream intStream = IntStream.range(1, 10);        // 1 to 9
IntStream intStreamClosed = IntStream.rangeClosed(1, 10); // 1 to 10
LongStream longStream = LongStream.range(1, 100);
DoubleStream doubleStream = DoubleStream.of(1.1, 2.2, 3.3);
```

## Intermediate Operations

Intermediate operations return a new stream and are lazy (not executed until a terminal operation is called).

### filter()

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

// Filter names longer than 4 characters
List<String> longNames = names.stream()
    .filter(name -> name.length() > 4)
    .collect(Collectors.toList());

// Multiple filters
List<String> filteredNames = names.stream()
    .filter(name -> name.length() > 3)
    .filter(name -> name.startsWith("A"))
    .collect(Collectors.toList());
```

### map()

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

// Transform to uppercase
List<String> upperNames = names.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());

// Transform to lengths
List<Integer> lengths = names.stream()
    .map(String::length)
    .collect(Collectors.toList());

// Complex transformation
List<String> transformed = names.stream()
    .map(name -> "Hello " + name + "!")
    .collect(Collectors.toList());
```

### flatMap()

```java
List<List<String>> lists = Arrays.asList(
    Arrays.asList("a", "b"),
    Arrays.asList("c", "d"),
    Arrays.asList("e", "f")
);

// Flatten nested lists
List<String> flattened = lists.stream()
    .flatMap(List::stream)
    .collect(Collectors.toList());

// Example with words and characters
List<String> words = Arrays.asList("Hello", "World");
List<String> characters = words.stream()
    .flatMap(word -> Arrays.stream(word.split("")))
    .collect(Collectors.toList());
```

### distinct()

```java
List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 4, 5);

// Remove duplicates
List<Integer> uniqueNumbers = numbers.stream()
    .distinct()
    .collect(Collectors.toList());
```

### sorted()

```java
List<String> names = Arrays.asList("Charlie", "Alice", "Bob", "David");

// Natural sorting
List<String> sortedNames = names.stream()
    .sorted()
    .collect(Collectors.toList());

// Custom sorting
List<String> sortedByLength = names.stream()
    .sorted((a, b) -> a.length() - b.length())
    .collect(Collectors.toList());

// Using Comparator
List<String> sortedByLengthThenAlphabetically = names.stream()
    .sorted(Comparator.comparing(String::length).thenComparing(String::compareTo))
    .collect(Collectors.toList());
```

### limit() and skip()

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Get first 5 elements
List<Integer> firstFive = numbers.stream()
    .limit(5)
    .collect(Collectors.toList());

// Skip first 3 elements
List<Integer> skipFirstThree = numbers.stream()
    .skip(3)
    .collect(Collectors.toList());

// Pagination example
List<Integer> page2 = numbers.stream()
    .skip(5)  // Skip first 5 elements
    .limit(5) // Take next 5 elements
    .collect(Collectors.toList());
```

### peek()

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

// Debug stream processing
List<String> result = names.stream()
    .peek(name -> System.out.println("Processing: " + name))
    .filter(name -> name.length() > 4)
    .peek(name -> System.out.println("Filtered: " + name))
    .map(String::toUpperCase)
    .peek(name -> System.out.println("Mapped: " + name))
    .collect(Collectors.toList());
```

## Terminal Operations

Terminal operations produce a result or side effect and trigger the execution of the stream pipeline.

### forEach()

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

// Print each name
names.stream().forEach(System.out::println);

// Custom action
names.stream().forEach(name -> {
    System.out.println("Hello " + name + "!");
    System.out.println("Length: " + name.length());
});
```

### collect()

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

// Collect to List
List<String> list = names.stream()
    .filter(name -> name.length() > 4)
    .collect(Collectors.toList());

// Collect to Set
Set<String> set = names.stream()
    .collect(Collectors.toSet());

// Collect to Map
Map<String, Integer> nameLengthMap = names.stream()
    .collect(Collectors.toMap(
        name -> name,
        String::length
    ));

// Collect to String
String concatenated = names.stream()
    .collect(Collectors.joining(", "));
```

### reduce()

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Sum all numbers
int sum = numbers.stream()
    .reduce(0, Integer::sum);

// Product of all numbers
int product = numbers.stream()
    .reduce(1, (a, b) -> a * b);

// Find maximum
int max = numbers.stream()
    .reduce(Integer.MIN_VALUE, Integer::max);

// Concatenate strings
List<String> words = Arrays.asList("Hello", "World", "Java");
String result = words.stream()
    .reduce("", String::concat);
```

### findFirst() and findAny()

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

// Find first name longer than 4 characters
Optional<String> firstLongName = names.stream()
    .filter(name -> name.length() > 4)
    .findFirst();

// Find any name starting with 'A'
Optional<String> anyNameStartingWithA = names.stream()
    .filter(name -> name.startsWith("A"))
    .findAny();
```

### anyMatch(), allMatch(), noneMatch()

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

// Check if any name is longer than 5 characters
boolean anyLongName = names.stream()
    .anyMatch(name -> name.length() > 5);

// Check if all names are longer than 2 characters
boolean allLongNames = names.stream()
    .allMatch(name -> name.length() > 2);

// Check if no name starts with 'Z'
boolean noneStartsWithZ = names.stream()
    .noneMatch(name -> name.startsWith("Z"));
```

### count()

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

// Count names longer than 4 characters
long count = names.stream()
    .filter(name -> name.length() > 4)
    .count();
```

### min() and max()

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

// Find shortest name
Optional<String> shortest = names.stream()
    .min(Comparator.comparing(String::length));

// Find longest name
Optional<String> longest = names.stream()
    .max(Comparator.comparing(String::length));
```

## Parallel Streams

Parallel streams enable automatic parallel processing of data.

### Creating Parallel Streams

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

// From collection
Stream<String> parallelStream = names.parallelStream();

// From existing stream
Stream<String> parallel = names.stream().parallel();
```

### Parallel Processing Example

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Sequential processing
long startTime = System.currentTimeMillis();
int sequentialSum = numbers.stream()
    .mapToInt(Integer::intValue)
    .sum();
long sequentialTime = System.currentTimeMillis() - startTime;

// Parallel processing
startTime = System.currentTimeMillis();
int parallelSum = numbers.parallelStream()
    .mapToInt(Integer::intValue)
    .sum();
long parallelTime = System.currentTimeMillis() - startTime;

System.out.println("Sequential sum: " + sequentialSum + " in " + sequentialTime + "ms");
System.out.println("Parallel sum: " + parallelSum + " in " + parallelTime + "ms");
```

### When to Use Parallel Streams

**Use parallel streams when:**
- Large datasets
- CPU-intensive operations
- Operations are independent
- Order doesn't matter

**Avoid parallel streams when:**
- Small datasets
- I/O bound operations
- Operations depend on order
- Shared mutable state

## Collectors

Collectors provide various ways to collect stream elements into different data structures.

### Basic Collectors

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

// To List
List<String> list = names.stream()
    .collect(Collectors.toList());

// To Set
Set<String> set = names.stream()
    .collect(Collectors.toSet());

// To Collection
Collection<String> collection = names.stream()
    .collect(Collectors.toCollection(ArrayList::new));
```

### Collecting to Map

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

// Simple mapping
Map<String, Integer> nameLengthMap = names.stream()
    .collect(Collectors.toMap(
        name -> name,
        String::length
    ));

// With duplicate key handling
Map<Integer, String> lengthNameMap = names.stream()
    .collect(Collectors.toMap(
        String::length,
        name -> name,
        (existing, replacement) -> existing + ", " + replacement
    ));

// Grouping by length
Map<Integer, List<String>> groupedByLength = names.stream()
    .collect(Collectors.groupingBy(String::length));
```

### Grouping and Partitioning

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");

// Group by first letter
Map<Character, List<String>> groupedByFirstLetter = names.stream()
    .collect(Collectors.groupingBy(name -> name.charAt(0)));

// Partition by length > 4
Map<Boolean, List<String>> partitioned = names.stream()
    .collect(Collectors.partitioningBy(name -> name.length() > 4));

// Group by length with counting
Map<Integer, Long> countByLength = names.stream()
    .collect(Collectors.groupingBy(
        String::length,
        Collectors.counting()
    ));
```

### Joining

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

// Simple joining
String result = names.stream()
    .collect(Collectors.joining());

// Joining with delimiter
String withComma = names.stream()
    .collect(Collectors.joining(", "));

// Joining with prefix and suffix
String withBrackets = names.stream()
    .collect(Collectors.joining(", ", "[", "]"));
```

### Summarizing

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// IntSummaryStatistics
IntSummaryStatistics stats = numbers.stream()
    .mapToInt(Integer::intValue)
    .summaryStatistics();

System.out.println("Count: " + stats.getCount());
System.out.println("Sum: " + stats.getSum());
System.out.println("Average: " + stats.getAverage());
System.out.println("Min: " + stats.getMin());
System.out.println("Max: " + stats.getMax());
```

## Practical Examples

### Example 1: Employee Management System

```java
import java.util.*;
import java.util.stream.Collectors;

class Employee {
    private String name;
    private int age;
    private String department;
    private double salary;
    
    public Employee(String name, int age, String department, double salary) {
        this.name = name;
        this.age = age;
        this.department = department;
        this.salary = salary;
    }
    
    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    
    @Override
    public String toString() {
        return name + " (" + department + ")";
    }
}

public class EmployeeManagementExample {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 25, "IT", 50000),
            new Employee("Bob", 30, "HR", 45000),
            new Employee("Charlie", 35, "IT", 60000),
            new Employee("David", 28, "Finance", 55000),
            new Employee("Eve", 32, "IT", 58000)
        );
        
        // Find all IT employees
        List<Employee> itEmployees = employees.stream()
            .filter(emp -> "IT".equals(emp.getDepartment()))
            .collect(Collectors.toList());
        
        // Calculate average salary by department
        Map<String, Double> avgSalaryByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.averagingDouble(Employee::getSalary)
            ));
        
        // Find highest paid employee
        Optional<Employee> highestPaid = employees.stream()
            .max(Comparator.comparing(Employee::getSalary));
        
        // Group employees by age range
        Map<String, List<Employee>> byAgeRange = employees.stream()
            .collect(Collectors.groupingBy(emp -> {
                if (emp.getAge() < 30) return "Young";
                else if (emp.getAge() < 35) return "Middle";
                else return "Senior";
            }));
        
        // Print results
        System.out.println("IT Employees: " + itEmployees);
        System.out.println("Average Salary by Department: " + avgSalaryByDept);
        System.out.println("Highest Paid: " + highestPaid.orElse(null));
        System.out.println("By Age Range: " + byAgeRange);
    }
}
```

### Example 2: Data Analysis

```java
import java.util.*;
import java.util.stream.Collectors;

public class DataAnalysisExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        
        // Find all prime numbers
        List<Integer> primes = numbers.stream()
            .filter(DataAnalysisExample::isPrime)
            .collect(Collectors.toList());
        
        // Find perfect squares
        List<Integer> perfectSquares = numbers.stream()
            .filter(n -> Math.sqrt(n) == (int) Math.sqrt(n))
            .collect(Collectors.toList());
        
        // Calculate sum of even numbers
        int sumOfEvens = numbers.stream()
            .filter(n -> n % 2 == 0)
            .mapToInt(Integer::intValue)
            .sum();
        
        // Find numbers divisible by both 3 and 5
        List<Integer> divisibleBy3And5 = numbers.stream()
            .filter(n -> n % 3 == 0 && n % 5 == 0)
            .collect(Collectors.toList());
        
        // Group by remainder when divided by 3
        Map<Integer, List<Integer>> groupedByRemainder = numbers.stream()
            .collect(Collectors.groupingBy(n -> n % 3));
        
        System.out.println("Primes: " + primes);
        System.out.println("Perfect squares: " + perfectSquares);
        System.out.println("Sum of evens: " + sumOfEvens);
        System.out.println("Divisible by 3 and 5: " + divisibleBy3And5);
        System.out.println("Grouped by remainder (mod 3): " + groupedByRemainder);
    }
    
    private static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
```

### Example 3: File Processing

```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FileProcessingExample {
    public static void main(String[] args) {
        try {
            // Read lines from file
            List<String> lines = Files.readAllLines(Paths.get("sample.txt"));
            
            // Count lines
            long lineCount = lines.stream().count();
            
            // Count words
            long wordCount = lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .filter(word -> !word.isEmpty())
                .count();
            
            // Find longest line
            Optional<String> longestLine = lines.stream()
                .max(Comparator.comparing(String::length));
            
            // Find lines containing specific word
            List<String> linesWithJava = lines.stream()
                .filter(line -> line.toLowerCase().contains("java"))
                .collect(Collectors.toList());
            
            // Count word frequency
            Map<String, Long> wordFrequency = lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .map(String::toLowerCase)
                .filter(word -> !word.isEmpty())
                .collect(Collectors.groupingBy(
                    word -> word,
                    Collectors.counting()
                ));
            
            // Find most common word
            Optional<Map.Entry<String, Long>> mostCommon = wordFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue());
            
            System.out.println("Line count: " + lineCount);
            System.out.println("Word count: " + wordCount);
            System.out.println("Longest line: " + longestLine.orElse(""));
            System.out.println("Lines with 'Java': " + linesWithJava);
            System.out.println("Most common word: " + mostCommon.orElse(null));
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
```

## Best Practices

### 1. Use Method References When Possible

```java
// Instead of
names.stream().map(name -> name.toUpperCase())

// Use
names.stream().map(String::toUpperCase)
```

### 2. Avoid Side Effects in Streams

```java
// Bad - side effect in stream
List<String> result = new ArrayList<>();
names.stream()
    .filter(name -> name.length() > 4)
    .forEach(name -> result.add(name.toUpperCase()));

// Good - use collect
List<String> result = names.stream()
    .filter(name -> name.length() > 4)
    .map(String::toUpperCase)
    .collect(Collectors.toList());
```

### 3. Use Appropriate Collectors

```java
// For simple collections
List<String> list = stream.collect(Collectors.toList());
Set<String> set = stream.collect(Collectors.toSet());

// For custom collections
TreeSet<String> treeSet = stream.collect(Collectors.toCollection(TreeSet::new));
```

### 4. Consider Performance

```java
// Use parallel streams for large datasets
List<String> result = largeList.parallelStream()
    .filter(condition)
    .collect(Collectors.toList());

// Use primitive streams for numbers
int sum = numbers.stream()
    .mapToInt(Integer::intValue)
    .sum();
```

### 5. Handle Optional Results

```java
// Always check Optional before using
Optional<String> result = names.stream()
    .filter(name -> name.length() > 10)
    .findFirst();

result.ifPresent(System.out::println);
String name = result.orElse("No name found");
```

## Common Pitfalls

### 1. Stream Can Only Be Traversed Once

```java
Stream<String> stream = names.stream();
stream.forEach(System.out::println);
// stream.forEach(System.out::println); // This will throw exception
```

### 2. Lazy Evaluation

```java
// This won't print anything
names.stream()
    .filter(name -> {
        System.out.println("Filtering: " + name);
        return name.length() > 4;
    });

// This will print
names.stream()
    .filter(name -> {
        System.out.println("Filtering: " + name);
        return name.length() > 4;
    })
    .collect(Collectors.toList());
```

### 3. Order Matters in Parallel Streams

```java
// Order is not guaranteed in parallel streams
names.parallelStream()
    .forEach(System.out::println);

// Use forEachOrdered for ordered processing
names.parallelStream()
    .forEachOrdered(System.out::println);
```

### 4. Boxing/Unboxing Overhead

```java
// Avoid boxing/unboxing
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Bad
int sum = numbers.stream()
    .reduce(0, Integer::sum);

// Good
int sum = numbers.stream()
    .mapToInt(Integer::intValue)
    .sum();
```

## Exercises

### Exercise 1: Basic Stream Operations

Given a list of integers from 1 to 20, use streams to:
1. Find all even numbers
2. Find all numbers divisible by 3
3. Find the sum of all numbers
4. Find the product of all numbers
5. Find the average of all numbers

### Exercise 2: String Processing

Given a list of strings, use streams to:
1. Find all strings longer than 5 characters
2. Convert all strings to uppercase
3. Find all strings that start with 'A'
4. Count the total number of characters
5. Find the longest string

### Exercise 3: Employee Analysis

Create an Employee class with name, age, salary, and department. Create a list of employees and use streams to:
1. Find all employees in a specific department
2. Calculate average salary by department
3. Find the highest paid employee
4. Group employees by age range
5. Find employees with salary above average

### Exercise 4: Data Transformation

Given a list of numbers, use streams to:
1. Remove duplicates
2. Sort in descending order
3. Take only the first 5 elements
4. Transform each number to its square
5. Find the sum of squares

### Exercise 5: File Analysis

Create a text file and use streams to:
1. Count the number of lines
2. Count the number of words
3. Find the longest line
4. Count word frequency
5. Find the most common word

## Solutions

Solutions to these exercises can be found in the corresponding Java files in the `src/streams/` directory.

## Next Steps

After mastering the Stream API, proceed to:
- [Optional Class](03_Optional_Class.md) - Handle null values safely
- [Collectors Deep Dive](04_Collectors_Deep_Dive.md) - Advanced collection techniques
- [Parallel Streams](05_Parallel_Streams.md) - Performance optimization 