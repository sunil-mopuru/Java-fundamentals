# Lambda Expressions in Java 8

Lambda expressions are one of the most significant features introduced in Java 8. They enable functional programming paradigms and provide a concise way to express instances of single-method interfaces (functional interfaces).

## Table of Contents

1. [What are Lambda Expressions?](#what-are-lambda-expressions)
2. [Functional Interfaces](#functional-interfaces)
3. [Lambda Expression Syntax](#lambda-expression-syntax)
4. [Variable Capture](#variable-capture)
5. [Built-in Functional Interfaces](#built-in-functional-interfaces)
6. [Practical Examples](#practical-examples)
7. [Best Practices](#best-practices)
8. [Common Pitfalls](#common-pitfalls)
9. [Exercises](#exercises)

## What are Lambda Expressions?

Lambda expressions are anonymous functions that can be treated as values. They allow you to pass behavior as parameters to methods, making your code more flexible and readable.

### Before Java 8 (Anonymous Classes)

```java
// Old way with anonymous class
Runnable runnable = new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello from anonymous class!");
    }
};
```

### With Lambda Expressions

```java
// New way with lambda expression
Runnable runnable = () -> System.out.println("Hello from lambda!");
```

## Functional Interfaces

A functional interface is an interface that contains exactly one abstract method. Lambda expressions can only be used with functional interfaces.

### Examples of Functional Interfaces

```java
// Built-in functional interfaces
@FunctionalInterface
public interface Runnable {
    void run();
}

@FunctionalInterface
public interface Callable<V> {
    V call() throws Exception;
}

@FunctionalInterface
public interface Comparator<T> {
    int compare(T o1, T o2);
}
```

### Creating Custom Functional Interfaces

```java
@FunctionalInterface
public interface MathOperation {
    int operate(int a, int b);
}

@FunctionalInterface
public interface StringProcessor {
    String process(String input);
}
```

## Lambda Expression Syntax

### Basic Syntax

```java
(parameters) -> { statements }
```

### Syntax Variations

```java
// No parameters
() -> System.out.println("Hello");

// Single parameter (parentheses optional)
name -> System.out.println("Hello " + name);
(name) -> System.out.println("Hello " + name);

// Multiple parameters
(a, b) -> a + b;
(a, b) -> {
    int result = a + b;
    return result;
};

// Explicit return type (rarely needed)
(int a, int b) -> a + b;
```

## Variable Capture

Lambda expressions can capture variables from their enclosing scope.

### Local Variable Capture

```java
public void demonstrateVariableCapture() {
    String prefix = "Hello, ";
    int multiplier = 2;
    
    // Capturing local variables
    StringProcessor processor = name -> prefix + name;
    MathOperation operation = (a, b) -> a * b * multiplier;
    
    System.out.println(processor.process("World")); // Output: Hello, World
    System.out.println(operation.operate(3, 4));    // Output: 24
}
```

### Effectively Final Variables

Variables captured by lambda expressions must be effectively final (either final or never reassigned after initialization).

```java
public void demonstrateEffectivelyFinal() {
    int counter = 0;
    
    // This works - counter is effectively final
    Runnable incrementer = () -> System.out.println("Counter: " + counter);
    
    // This would cause compilation error
    // counter = 1; // Error: Variable used in lambda expression should be final or effectively final
}
```

## Built-in Functional Interfaces

Java 8 provides several built-in functional interfaces in the `java.util.function` package.

### Predicate<T>

```java
import java.util.function.Predicate;

Predicate<String> isLong = s -> s.length() > 5;
Predicate<String> startsWithA = s -> s.startsWith("A");

System.out.println(isLong.test("Hello"));      // false
System.out.println(startsWithA.test("Alice")); // true
```

### Function<T, R>

```java
import java.util.function.Function;

Function<String, Integer> lengthFunction = String::length;
Function<String, String> upperCaseFunction = String::toUpperCase;

System.out.println(lengthFunction.apply("Hello"));     // 5
System.out.println(upperCaseFunction.apply("hello")); // HELLO
```

### Consumer<T>

```java
import java.util.function.Consumer;

Consumer<String> printer = System.out::println;
Consumer<String> prefixPrinter = s -> System.out.println("Message: " + s);

printer.accept("Hello World");
prefixPrinter.accept("Hello World");
```

### Supplier<T>

```java
import java.util.function.Supplier;

Supplier<Double> randomSupplier = Math::random;
Supplier<String> greetingSupplier = () -> "Hello World";

System.out.println(randomSupplier.get());
System.out.println(greetingSupplier.get());
```

### BiFunction<T, U, R>

```java
import java.util.function.BiFunction;

BiFunction<String, String, String> concat = String::concat;
BiFunction<Integer, Integer, Integer> add = Integer::sum;

System.out.println(concat.apply("Hello ", "World")); // Hello World
System.out.println(add.apply(5, 3));                 // 8
```

## Practical Examples

### Example 1: Sorting with Lambda

```java
import java.util.Arrays;
import java.util.List;

public class LambdaSortingExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        
        // Sort by length
        names.sort((a, b) -> a.length() - b.length());
        System.out.println("Sorted by length: " + names);
        
        // Sort alphabetically
        names.sort(String::compareTo);
        System.out.println("Sorted alphabetically: " + names);
        
        // Sort by length, then alphabetically
        names.sort((a, b) -> {
            int lengthCompare = a.length() - b.length();
            return lengthCompare != 0 ? lengthCompare : a.compareTo(b);
        });
        System.out.println("Sorted by length then alphabetically: " + names);
    }
}
```

### Example 2: Event Handling

```java
import java.util.function.Consumer;

public class EventHandlerExample {
    public static void main(String[] args) {
        // Define event handlers using lambda expressions
        Consumer<String> logHandler = event -> 
            System.out.println("Logging event: " + event);
        
        Consumer<String> emailHandler = event -> 
            System.out.println("Sending email for event: " + event);
        
        Consumer<String> smsHandler = event -> 
            System.out.println("Sending SMS for event: " + event);
        
        // Simulate event processing
        String event = "User login";
        processEvent(event, logHandler, emailHandler, smsHandler);
    }
    
    public static void processEvent(String event, Consumer<String>... handlers) {
        for (Consumer<String> handler : handlers) {
            handler.accept(event);
        }
    }
}
```

### Example 3: Mathematical Operations

```java
import java.util.function.BiFunction;
import java.util.function.Function;

public class MathOperationsExample {
    public static void main(String[] args) {
        // Define mathematical operations
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b;
        BiFunction<Integer, Integer, Integer> power = (a, b) -> (int) Math.pow(a, b);
        
        // Function composition
        Function<Integer, Integer> square = x -> x * x;
        Function<Integer, Integer> addOne = x -> x + 1;
        Function<Integer, Integer> squareThenAddOne = square.andThen(addOne);
        
        // Test operations
        System.out.println("5 + 3 = " + add.apply(5, 3));
        System.out.println("5 * 3 = " + multiply.apply(5, 3));
        System.out.println("2^3 = " + power.apply(2, 3));
        System.out.println("(4^2) + 1 = " + squareThenAddOne.apply(4));
    }
}
```

### Example 4: Collection Processing

```java
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Function;

public class CollectionProcessingExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Define predicates
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<Integer> isGreaterThan5 = n -> n > 5;
        
        // Define functions
        Function<Integer, Integer> square = n -> n * n;
        Function<Integer, String> toString = Object::toString;
        
        // Process collection
        numbers.stream()
            .filter(isEven.and(isGreaterThan5))
            .map(square)
            .map(toString)
            .forEach(System.out::println);
    }
}
```

## Best Practices

### 1. Keep Lambda Expressions Short

```java
// Good
names.forEach(name -> System.out.println(name));

// Better - use method reference
names.forEach(System.out::println);
```

### 2. Use Method References When Possible

```java
// Instead of lambda
Function<String, Integer> length = s -> s.length();

// Use method reference
Function<String, Integer> length = String::length;
```

### 3. Extract Complex Logic

```java
// Avoid complex lambda expressions
names.forEach(name -> {
    if (name.length() > 5) {
        String upperName = name.toUpperCase();
        System.out.println("Long name: " + upperName);
    }
});

// Extract to method
names.forEach(LambdaExamples::processLongName);

private static void processLongName(String name) {
    if (name.length() > 5) {
        String upperName = name.toUpperCase();
        System.out.println("Long name: " + upperName);
    }
}
```

### 4. Use Descriptive Parameter Names

```java
// Good
BiFunction<String, String, String> concat = (first, second) -> first + second;

// Avoid
BiFunction<String, String, String> concat = (a, b) -> a + b;
```

## Common Pitfalls

### 1. Variable Capture Issues

```java
// This won't compile
int[] counter = {0};
names.forEach(name -> counter[0]++); // Error: Variable used in lambda should be final

// Use AtomicInteger instead
AtomicInteger counter = new AtomicInteger(0);
names.forEach(name -> counter.incrementAndGet());
```

### 2. Exception Handling

```java
// Lambda expressions can't throw checked exceptions
// This won't compile
Function<String, Integer> parser = s -> Integer.parseInt(s); // OK
Function<String, Integer> fileReader = s -> {
    // This would cause compilation error if FileReader throws IOException
    return new FileReader(s).read();
};
```

### 3. Performance Considerations

```java
// Be careful with object creation in lambda expressions
// This creates a new object for each element
names.forEach(name -> new StringBuilder().append(name));

// Better approach
StringBuilder sb = new StringBuilder();
names.forEach(sb::append);
```

## Exercises

### Exercise 1: Basic Lambda Expressions

Create lambda expressions for the following operations:

1. A function that takes two integers and returns their sum
2. A predicate that checks if a string is empty
3. A consumer that prints a string in uppercase
4. A supplier that returns a random number between 1 and 100

### Exercise 2: Custom Functional Interface

Create a custom functional interface `StringTransformer` that takes a string and returns a transformed string. Implement it using lambda expressions for:
- Converting to uppercase
- Reversing the string
- Adding a prefix
- Removing vowels

### Exercise 3: Collection Processing

Given a list of integers, use lambda expressions to:
1. Filter out even numbers
2. Square the remaining numbers
3. Find the sum of all squared numbers
4. Print each step of the process

### Exercise 4: Event System

Create a simple event system using lambda expressions:
1. Define an `EventHandler` functional interface
2. Create different types of event handlers (log, email, database)
3. Implement an event dispatcher that can register and trigger handlers
4. Test with different events

### Exercise 5: Calculator

Create a calculator using lambda expressions:
1. Define operations for add, subtract, multiply, divide
2. Use `BiFunction` for arithmetic operations
3. Create a method that takes two numbers and an operation
4. Handle division by zero using Optional

## Solutions

Solutions to these exercises can be found in the corresponding Java files in the `src/lambda/` directory.

## Next Steps

After mastering lambda expressions, proceed to:
- [Stream API](02_Stream_API.md) - Process collections functionally
- [Method References](05_Method_References.md) - Shorthand for lambda expressions
- [Optional Class](03_Optional_Class.md) - Handle null values safely 