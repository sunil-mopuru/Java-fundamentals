# Method References in Java 8

Method references are a shorthand notation for lambda expressions that call existing methods. They provide a more concise and readable way to express lambda expressions when you want to call an existing method.

## Table of Contents

1. [What are Method References?](#what-are-method-references)
2. [Types of Method References](#types-of-method-references)
3. [Static Method References](#static-method-references)
4. [Instance Method References](#instance-method-references)
5. [Constructor References](#constructor-references)
6. [Method References with Streams](#method-references-with-streams)
7. [Best Practices](#best-practices)
8. [Common Patterns](#common-patterns)
9. [Practical Examples](#practical-examples)
10. [Common Pitfalls](#common-pitfalls)
11. [Exercises](#exercises)

## What are Method References?

Method references are a shorthand notation for lambda expressions that call existing methods. They use the `::` operator to reference methods.

### Lambda Expression vs Method Reference

```java
// Lambda expression
Function<String, Integer> lengthFunction = s -> s.length();

// Method reference (equivalent)
Function<String, Integer> lengthFunction = String::length;
```

### Benefits of Method References

1. **Conciseness**: Shorter and more readable code
2. **Clarity**: Makes the intent more explicit
3. **Reusability**: References existing methods
4. **Performance**: Same performance as lambda expressions

## Types of Method References

There are four types of method references:

1. **Static Method References**: `ClassName::staticMethod`
2. **Instance Method References on Specific Objects**: `object::instanceMethod`
3. **Instance Method References on Arbitrary Objects**: `ClassName::instanceMethod`
4. **Constructor References**: `ClassName::new`

## Static Method References

Static method references refer to static methods of a class.

### Basic Syntax

```java
// Lambda expression
Function<String, Integer> parser = s -> Integer.parseInt(s);

// Method reference
Function<String, Integer> parser = Integer::parseInt;
```

### Examples

```java
import java.util.function.Function;
import java.util.function.Supplier;

public class StaticMethodReferences {

    // Static method examples
    public static void main(String[] args) {
        // Integer.parseInt
        Function<String, Integer> parseInt = Integer::parseInt;
        System.out.println(parseInt.apply("123")); // 123

        // Math.abs
        Function<Integer, Integer> abs = Math::abs;
        System.out.println(abs.apply(-5)); // 5

        // String.valueOf
        Function<Object, String> valueOf = String::valueOf;
        System.out.println(valueOf.apply(42)); // "42"

        // System.currentTimeMillis
        Supplier<Long> currentTime = System::currentTimeMillis;
        System.out.println(currentTime.get());

        // Custom static method
        Function<String, String> reverse = StringUtils::reverse;
        System.out.println(reverse.apply("hello")); // "olleh"
    }
}

class StringUtils {
    public static String reverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }
}
```

### Multiple Parameters

```java
import java.util.function.BiFunction;

public class StaticMethodReferencesMultiParam {

    public static void main(String[] args) {
        // Math.max
        BiFunction<Integer, Integer, Integer> max = Math::max;
        System.out.println(max.apply(5, 10)); // 10

        // String.compareTo
        BiFunction<String, String, Integer> compareTo = String::compareTo;
        System.out.println(compareTo.apply("hello", "world")); // negative value

        // Custom static method with multiple parameters
        BiFunction<Integer, Integer, Integer> add = MathUtils::add;
        System.out.println(add.apply(5, 3)); // 8
    }
}

class MathUtils {
    public static int add(int a, int b) {
        return a + b;
    }
}
```

## Instance Method References on Specific Objects

These refer to instance methods on a specific object.

### Basic Syntax

```java
// Lambda expression
Supplier<String> getter = () -> object.toString();

// Method reference
Supplier<String> getter = object::toString;
```

### Examples

```java
import java.util.function.Supplier;
import java.util.function.Consumer;

public class InstanceMethodReferences {

    public static void main(String[] args) {
        String str = "Hello World";

        // Instance method on specific object
        Supplier<Integer> length = str::length;
        System.out.println(length.get()); // 11

        // Instance method with parameters
        Consumer<String> printer = System.out::println;
        printer.accept("Hello from method reference");

        // Custom object
        Person person = new Person("Alice", 25);
        Supplier<String> getName = person::getName;
        System.out.println(getName.get()); // "Alice"

        // Instance method with parameters
        BiConsumer<Person, String> setName = Person::setName;
        setName.accept(person, "Bob");
        System.out.println(person.getName()); // "Bob"
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
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
}
```

## Instance Method References on Arbitrary Objects

These refer to instance methods on arbitrary objects of a specific type.

### Basic Syntax

```java
// Lambda expression
Function<String, Integer> length = s -> s.length();

// Method reference
Function<String, Integer> length = String::length;
```

### Examples

```java
import java.util.function.Function;
import java.util.function.BiFunction;

public class ArbitraryInstanceMethodReferences {

    public static void main(String[] args) {
        // String.length()
        Function<String, Integer> length = String::length;
        System.out.println(length.apply("hello")); // 5

        // String.toUpperCase()
        Function<String, String> upperCase = String::toUpperCase;
        System.out.println(upperCase.apply("hello")); // "HELLO"

        // String.startsWith()
        BiFunction<String, String, Boolean> startsWith = String::startsWith;
        System.out.println(startsWith.apply("hello", "he")); // true

        // String.contains()
        BiFunction<String, String, Boolean> contains = String::contains;
        System.out.println(contains.apply("hello world", "world")); // true

        // Custom class
        Function<Person, String> getName = Person::getName;
        Person person = new Person("Alice", 25);
        System.out.println(getName.apply(person)); // "Alice"

        // Method with multiple parameters
        BiFunction<Person, String, Void> setName = Person::setName;
        setName.apply(person, "Bob");
        System.out.println(person.getName()); // "Bob"
    }
}
```

### Collection Processing

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionMethodReferences {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

        // Map with method reference
        List<Integer> lengths = names.stream()
            .map(String::length)
            .collect(Collectors.toList());
        System.out.println(lengths); // [5, 3, 7]

        // Filter with method reference
        List<String> longNames = names.stream()
            .filter(name -> name.length() > 4)
            .collect(Collectors.toList());
        System.out.println(longNames); // [Alice, Charlie]

        // ForEach with method reference
        names.forEach(System.out::println);

        // Sort with method reference
        List<String> sorted = names.stream()
            .sorted(String::compareTo)
            .collect(Collectors.toList());
        System.out.println(sorted); // [Alice, Bob, Charlie]
    }
}
```

## Constructor References

Constructor references allow you to reference constructors.

### Basic Syntax

```java
// Lambda expression
Supplier<Person> creator = () -> new Person();

// Constructor reference
Supplier<Person> creator = Person::new;
```

### Examples

```java
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiFunction;

public class ConstructorReferences {

    public static void main(String[] args) {
        // Default constructor
        Supplier<Person> personCreator = Person::new;
        Person person1 = personCreator.get();
        System.out.println(person1); // Person{name='null', age=0}

        // Constructor with one parameter
        Function<String, Person> personWithName = Person::new;
        Person person2 = personWithName.apply("Alice");
        System.out.println(person2); // Person{name='Alice', age=0}

        // Constructor with two parameters
        BiFunction<String, Integer, Person> personWithNameAndAge = Person::new;
        Person person3 = personWithNameAndAge.apply("Bob", 25);
        System.out.println(person3); // Person{name='Bob', age=25}

        // Array constructor
        Function<Integer, String[]> arrayCreator = String[]::new;
        String[] array = arrayCreator.apply(5);
        System.out.println(array.length); // 5
    }
}

class Person {
    private String name;
    private int age;

    public Person() {
        this.name = null;
        this.age = 0;
    }

    public Person(String name) {
        this.name = name;
        this.age = 0;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}
```

## Method References with Streams

Method references are particularly useful with streams.

### Stream Operations

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamMethodReferences {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        // Map operations
        List<Integer> lengths = names.stream()
            .map(String::length)
            .collect(Collectors.toList());

        List<String> upperCase = names.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());

        // Filter operations
        List<String> longNames = names.stream()
            .filter(name -> name.length() > 4)
            .collect(Collectors.toList());

        // ForEach operations
        names.forEach(System.out::println);

        // Sort operations
        List<String> sorted = names.stream()
            .sorted(String::compareTo)
            .collect(Collectors.toList());

        // Collect operations
        String joined = names.stream()
            .collect(Collectors.joining(", "));

        // Reduce operations
        int totalLength = names.stream()
            .mapToInt(String::length)
            .sum();

        System.out.println("Lengths: " + lengths);
        System.out.println("Upper case: " + upperCase);
        System.out.println("Long names: " + longNames);
        System.out.println("Sorted: " + sorted);
        System.out.println("Joined: " + joined);
        System.out.println("Total length: " + totalLength);
    }
}
```

### Complex Stream Operations

```java
import java.util.*;
import java.util.stream.Collectors;

public class ComplexStreamMethodReferences {

    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
            new Person("Alice", 25),
            new Person("Bob", 30),
            new Person("Charlie", 35),
            new Person("David", 28)
        );

        // Extract names
        List<String> names = people.stream()
            .map(Person::getName)
            .collect(Collectors.toList());

        // Extract ages
        List<Integer> ages = people.stream()
            .map(Person::getAge)
            .collect(Collectors.toList());

        // Filter by age
        List<Person> youngPeople = people.stream()
            .filter(person -> person.getAge() < 30)
            .collect(Collectors.toList());

        // Sort by name
        List<Person> sortedByName = people.stream()
            .sorted(Comparator.comparing(Person::getName))
            .collect(Collectors.toList());

        // Sort by age
        List<Person> sortedByAge = people.stream()
            .sorted(Comparator.comparing(Person::getAge))
            .collect(Collectors.toList());

        // Group by age range
        Map<String, List<Person>> groupedByAge = people.stream()
            .collect(Collectors.groupingBy(person -> {
                if (person.getAge() < 30) return "Young";
                else return "Adult";
            }));

        // Find oldest person
        Optional<Person> oldest = people.stream()
            .max(Comparator.comparing(Person::getAge));

        // Find youngest person
        Optional<Person> youngest = people.stream()
            .min(Comparator.comparing(Person::getAge));

        System.out.println("Names: " + names);
        System.out.println("Ages: " + ages);
        System.out.println("Young people: " + youngPeople);
        System.out.println("Sorted by name: " + sortedByName);
        System.out.println("Sorted by age: " + sortedByAge);
        System.out.println("Grouped by age: " + groupedByAge);
        System.out.println("Oldest: " + oldest.orElse(null));
        System.out.println("Youngest: " + youngest.orElse(null));
    }
}
```

## Best Practices

### 1. Use Method References When Appropriate

```java
// Good - method reference is clearer
names.forEach(System.out::println);

// Good - lambda is clearer for complex logic
names.forEach(name -> {
    if (name.length() > 4) {
        System.out.println("Long name: " + name);
    }
});
```

### 2. Prefer Method References for Simple Operations

```java
// Good - method reference
Function<String, Integer> length = String::length;

// Avoid - unnecessary lambda
Function<String, Integer> length = s -> s.length();
```

### 3. Use Constructor References for Object Creation

```java
// Good - constructor reference
Supplier<Person> creator = Person::new;

// Avoid - lambda for simple constructor
Supplier<Person> creator = () -> new Person();
```

### 4. Consider Readability

```java
// Good - clear intent
List<String> upperCase = names.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());

// Good - clear intent with custom method
List<String> processed = names.stream()
    .map(StringProcessor::process)
    .collect(Collectors.toList());
```

## Common Patterns

### 1. Collection Processing

```java
public class CollectionPatterns {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

        // Transform elements
        List<Integer> lengths = names.stream()
            .map(String::length)
            .collect(Collectors.toList());

        // Filter elements
        List<String> longNames = names.stream()
            .filter(name -> name.length() > 4)
            .collect(Collectors.toList());

        // Sort elements
        List<String> sorted = names.stream()
            .sorted(String::compareTo)
            .collect(Collectors.toList());

        // ForEach operation
        names.forEach(System.out::println);

        // Reduce operation
        int totalLength = names.stream()
            .mapToInt(String::length)
            .sum();
    }
}
```

### 2. Object Creation

```java
public class ObjectCreationPatterns {

    public static void main(String[] args) {
        // Create objects with default constructor
        Supplier<Person> personCreator = Person::new;
        Person person = personCreator.get();

        // Create objects with parameters
        Function<String, Person> personWithName = Person::new;
        Person alice = personWithName.apply("Alice");

        BiFunction<String, Integer, Person> personWithNameAndAge = Person::new;
        Person bob = personWithNameAndAge.apply("Bob", 25);

        // Create arrays
        Function<Integer, String[]> arrayCreator = String[]::new;
        String[] array = arrayCreator.apply(10);
    }
}
```

### 3. Utility Methods

```java
public class UtilityPatterns {

    public static void main(String[] args) {
        // String utilities
        Function<String, String> upperCase = String::toUpperCase;
        Function<String, String> lowerCase = String::toLowerCase;
        Function<String, Integer> length = String::length;

        // Math utilities
        Function<Integer, Integer> abs = Math::abs;
        BiFunction<Integer, Integer, Integer> max = Math::max;
        BiFunction<Integer, Integer, Integer> min = Math::min;

        // System utilities
        Supplier<Long> currentTime = System::currentTimeMillis;
        Consumer<String> printer = System.out::println;

        // Custom utilities
        Function<String, String> reverse = StringUtils::reverse;
        Function<String, String> capitalize = StringUtils::capitalize;
    }
}

class StringUtils {
    public static String reverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
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

        // Extract employee names
        List<String> names = employees.stream()
            .map(Employee::getName)
            .collect(Collectors.toList());

        // Extract departments
        List<String> departments = employees.stream()
            .map(Employee::getDepartment)
            .distinct()
            .collect(Collectors.toList());

        // Sort by salary
        List<Employee> sortedBySalary = employees.stream()
            .sorted(Comparator.comparing(Employee::getSalary))
            .collect(Collectors.toList());

        // Sort by age (descending)
        List<Employee> sortedByAge = employees.stream()
            .sorted(Comparator.comparing(Employee::getAge).reversed())
            .collect(Collectors.toList());

        // Group by department
        Map<String, List<Employee>> byDepartment = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartment));

        // Find highest paid employee
        Optional<Employee> highestPaid = employees.stream()
            .max(Comparator.comparing(Employee::getSalary));

        // Calculate average salary
        double avgSalary = employees.stream()
            .mapToDouble(Employee::getSalary)
            .average()
            .orElse(0.0);

        // Print results
        System.out.println("Names: " + names);
        System.out.println("Departments: " + departments);
        System.out.println("Sorted by salary: " + sortedBySalary);
        System.out.println("Sorted by age: " + sortedByAge);
        System.out.println("By department: " + byDepartment);
        System.out.println("Highest paid: " + highestPaid.orElse(null));
        System.out.println("Average salary: " + avgSalary);
    }
}
```

### Example 2: Data Processing Pipeline

```java
import java.util.*;
import java.util.stream.Collectors;

public class DataProcessingExample {

    public static void main(String[] args) {
        List<String> data = Arrays.asList(
            "hello world",
            "java programming",
            "method references",
            "stream api",
            "lambda expressions"
        );

        // Process data using method references
        List<String> processed = data.stream()
            .map(String::trim)                    // Remove whitespace
            .map(String::toLowerCase)             // Convert to lowercase
            .filter(s -> s.length() > 5)          // Filter long strings
            .map(DataProcessor::capitalize)       // Custom processing
            .sorted(String::compareTo)            // Sort alphabetically
            .collect(Collectors.toList());

        // Print each processed item
        processed.forEach(System.out::println);

        // Calculate statistics
        IntSummaryStatistics stats = data.stream()
            .mapToInt(String::length)
            .summaryStatistics();

        System.out.println("Statistics: " + stats);

        // Group by length
        Map<Integer, List<String>> groupedByLength = data.stream()
            .collect(Collectors.groupingBy(String::length));

        System.out.println("Grouped by length: " + groupedByLength);
    }
}

class DataProcessor {
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
```

### Example 3: Configuration Management

```java
import java.util.*;
import java.util.function.Function;

public class ConfigurationExample {

    public static void main(String[] args) {
        Map<String, String> config = new HashMap<>();
        config.put("server.port", "8080");
        config.put("server.host", "localhost");
        config.put("debug.enabled", "true");
        config.put("log.level", "INFO");

        // Create configuration objects using method references
        List<ConfigItem> items = config.entrySet().stream()
            .map(entry -> new ConfigItem(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());

        // Extract keys
        List<String> keys = items.stream()
            .map(ConfigItem::getKey)
            .collect(Collectors.toList());

        // Extract values
        List<String> values = items.stream()
            .map(ConfigItem::getValue)
            .collect(Collectors.toList());

        // Filter by key prefix
        List<ConfigItem> serverConfig = items.stream()
            .filter(item -> item.getKey().startsWith("server."))
            .collect(Collectors.toList());

        // Sort by key
        List<ConfigItem> sorted = items.stream()
            .sorted(Comparator.comparing(ConfigItem::getKey))
            .collect(Collectors.toList());

        // Print results
        System.out.println("Keys: " + keys);
        System.out.println("Values: " + values);
        System.out.println("Server config: " + serverConfig);
        System.out.println("Sorted: " + sorted);
    }
}

class ConfigItem {
    private String key;
    private String value;

    public ConfigItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() { return key; }
    public String getValue() { return value; }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
```

## Common Pitfalls

### 1. Overusing Method References

```java
// Good - method reference is clear
names.forEach(System.out::println);

// Bad - method reference makes it less clear
names.forEach(name -> {
    if (name.length() > 4) {
        System.out.println("Long name: " + name);
    }
});

// Good - lambda is clearer for complex logic
names.forEach(name -> {
    if (name.length() > 4) {
        System.out.println("Long name: " + name);
    }
});
```

### 2. Incorrect Method Reference Types

```java
// This won't compile - wrong functional interface
Function<String, Integer> length = String::length; // Correct

// This won't compile - wrong parameter types
BiFunction<String, String, Boolean> contains = String::contains; // Correct

// This won't compile - wrong return type
Function<String, String> length = String::length; // Wrong - returns Integer
```

### 3. Ambiguous Method References

```java
// This might be ambiguous if multiple overloaded methods exist
// BiFunction<String, String, Boolean> method = String::startsWith; // Ambiguous

// Be explicit about which overload you want
BiFunction<String, String, Boolean> startsWith = String::startsWith;
```

### 4. Not Understanding Context

```java
// Method reference context matters
List<String> names = Arrays.asList("Alice", "Bob");

// This works - String::length is a Function<String, Integer>
List<Integer> lengths = names.stream()
    .map(String::length)
    .collect(Collectors.toList());

// This also works - String::length is a ToIntFunction<String>
int totalLength = names.stream()
    .mapToInt(String::length)
    .sum();
```

## Exercises

### Exercise 1: Basic Method References

Create method references for the following operations:

1. Convert a string to uppercase
2. Get the length of a string
3. Parse an integer from a string
4. Print a string to console
5. Create a new ArrayList

### Exercise 2: Stream Processing with Method References

Given a list of strings, use method references to:

1. Convert all strings to uppercase
2. Filter strings longer than 5 characters
3. Sort strings alphabetically
4. Print each string
5. Calculate the total length of all strings

### Exercise 3: Object Processing

Create a Person class and use method references to:

1. Extract all names from a list of Person objects
2. Sort Person objects by age
3. Filter Person objects older than 25
4. Create a map of name to age
5. Find the oldest Person

### Exercise 4: Custom Method References

Create custom classes and methods, then use method references to:

1. Process data through custom utility methods
2. Create objects using custom constructors
3. Filter objects using custom predicates
4. Transform objects using custom functions
5. Aggregate results using custom collectors

### Exercise 5: Advanced Method References

Create complex method reference scenarios:

1. Chain multiple method references
2. Use method references with Optional
3. Use method references with CompletableFuture
4. Use method references with custom functional interfaces
5. Use method references in parallel streams

## Solutions

Solutions to these exercises can be found in the corresponding Java files in the `src/methodreferences/` directory.

## Next Steps

After mastering method references, proceed to:

- [Default Methods](04_Default_Methods.md) - Interface evolution
- [Date/Time API](06_Date_Time_API.md) - Modern date and time handling
- [CompletableFuture](07_CompletableFuture.md) - Asynchronous programming
