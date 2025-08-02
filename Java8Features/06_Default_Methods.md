# Java 8 Default Methods Deep Dive

## Table of Contents
1. [Introduction](#introduction)
2. [What are Default Methods?](#what-are-default-methods)
3. [Why Default Methods?](#why-default-methods)
4. [Syntax and Rules](#syntax-and-rules)
5. [Inheritance and Resolution](#inheritance-and-resolution)
6. [Practical Examples](#practical-examples)
7. [Best Practices](#best-practices)
8. [Common Patterns](#common-patterns)
9. [Exercises](#exercises)
10. [Summary](#summary)

## Introduction

Default methods were introduced in Java 8 to allow interfaces to have method implementations. This was primarily done to support the evolution of the Java Collections Framework and other core libraries without breaking existing code.

## What are Default Methods?

Default methods are methods in interfaces that have an implementation. They are declared using the `default` keyword and provide a default implementation that implementing classes can use or override.

### Key Characteristics:
- **Interface Methods with Implementation**: Unlike traditional interface methods, default methods have a body
- **Backward Compatibility**: Allow adding new methods to interfaces without breaking existing implementations
- **Optional Override**: Implementing classes can use the default implementation or provide their own
- **Multiple Inheritance**: Enable a form of multiple inheritance of behavior

## Why Default Methods?

### 1. **Interface Evolution**
Before Java 8, adding a method to an interface would break all existing implementations. Default methods solve this problem.

```java
// Before Java 8 - adding a method would break existing code
public interface List<E> {
    boolean add(E e);
    // Adding a new method here would break all implementations
}

// With default methods - safe to add new methods
public interface List<E> {
    boolean add(E e);
    
    default void forEach(Consumer<? super E> action) {
        // Default implementation
        for (E e : this) {
            action.accept(e);
        }
    }
}
```

### 2. **Multiple Inheritance of Behavior**
Classes can inherit behavior from multiple interfaces through default methods.

```java
interface Flyable {
    default void fly() {
        System.out.println("Flying...");
    }
}

interface Swimmable {
    default void swim() {
        System.out.println("Swimming...");
    }
}

class Duck implements Flyable, Swimmable {
    // Inherits both fly() and swim() methods
}
```

## Syntax and Rules

### Basic Syntax
```java
public interface MyInterface {
    // Abstract method (traditional)
    void abstractMethod();
    
    // Default method
    default void defaultMethod() {
        System.out.println("Default implementation");
    }
    
    // Static method (also introduced in Java 8)
    static void staticMethod() {
        System.out.println("Static method");
    }
}
```

### Rules for Default Methods

1. **Default Keyword**: Must use the `default` keyword
2. **Public Access**: Default methods are implicitly public
3. **Implementation Required**: Must provide a method body
4. **Cannot be Static**: Default methods cannot be static (use static methods for that)
5. **Cannot be Final**: Default methods cannot be final
6. **Cannot be Abstract**: Default methods cannot be abstract

## Inheritance and Resolution

### Method Resolution Rules

When a class implements multiple interfaces with conflicting default methods, Java uses specific resolution rules:

1. **Class Wins**: If a class overrides a default method, the class implementation wins
2. **Interface Wins**: If a class doesn't override, the most specific interface wins
3. **Explicit Override**: If there's ambiguity, the class must explicitly override the method

### Example: Method Resolution

```java
interface A {
    default void method() {
        System.out.println("A's method");
    }
}

interface B {
    default void method() {
        System.out.println("B's method");
    }
}

// This will cause a compilation error
class C implements A, B {
    // Must override method() to resolve conflict
    @Override
    public void method() {
        System.out.println("C's method");
        // Or call specific interface method
        A.super.method();
    }
}
```

### Calling Specific Interface Methods

```java
interface A {
    default void method() {
        System.out.println("A's method");
    }
}

interface B {
    default void method() {
        System.out.println("B's method");
    }
}

class C implements A, B {
    @Override
    public void method() {
        // Call A's default method
        A.super.method();
        // Call B's default method
        B.super.method();
        System.out.println("C's additional logic");
    }
}
```

## Practical Examples

### 1. **Collection Framework Evolution**

```java
public interface List<E> extends Collection<E> {
    // Existing methods...
    
    default void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        for (E e : this) {
            action.accept(e);
        }
    }
    
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, Spliterator.ORDERED);
    }
}
```

### 2. **Functional Interface Enhancement**

```java
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
    
    default Predicate<T> and(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) && other.test(t);
    }
    
    default Predicate<T> negate() {
        return (t) -> !test(t);
    }
    
    default Predicate<T> or(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) || other.test(t);
    }
}
```

### 3. **Builder Pattern with Default Methods**

```java
public interface Builder<T> {
    T build();
    
    default Builder<T> andThen(Consumer<T> consumer) {
        return () -> {
            T result = build();
            consumer.accept(result);
            return result;
        };
    }
}
```

### 4. **Validation Framework**

```java
public interface Validator<T> {
    boolean isValid(T value);
    
    default Validator<T> and(Validator<T> other) {
        return value -> isValid(value) && other.isValid(value);
    }
    
    default Validator<T> or(Validator<T> other) {
        return value -> isValid(value) || other.isValid(value);
    }
    
    default Validator<T> not() {
        return value -> !isValid(value);
    }
}
```

## Best Practices

### 1. **Keep Default Methods Simple**
```java
// Good: Simple, focused default method
default void log(String message) {
    System.out.println(getClass().getSimpleName() + ": " + message);
}

// Avoid: Complex logic in default methods
default void complexBusinessLogic() {
    // Complex implementation - better in abstract class
}
```

### 2. **Document Default Methods**
```java
/**
 * Provides a default implementation for common use cases.
 * Override this method for custom behavior.
 * 
 * @param input the input to process
 * @return the processed result
 */
default String process(String input) {
    return input != null ? input.trim() : "";
}
```

### 3. **Use Default Methods for Convenience**
```java
public interface Repository<T> {
    T findById(String id);
    
    // Convenience method with default implementation
    default Optional<T> findByIdOptional(String id) {
        try {
            return Optional.ofNullable(findById(id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
```

### 4. **Avoid Breaking Changes**
```java
// Good: Adding a new default method
default void newFeature() {
    // Safe default implementation
}

// Avoid: Changing existing method signatures
// This would break existing implementations
// default void existingMethod(String newParam) { }
```

## Common Patterns

### 1. **Template Method Pattern**
```java
public interface DataProcessor<T> {
    T process(T data);
    
    default T processWithLogging(T data) {
        System.out.println("Processing: " + data);
        T result = process(data);
        System.out.println("Result: " + result);
        return result;
    }
}
```

### 2. **Decorator Pattern**
```java
public interface Component {
    void operation();
    
    default Component withLogging() {
        return () -> {
            System.out.println("Before operation");
            operation();
            System.out.println("After operation");
        };
    }
}
```

### 3. **Chain of Responsibility**
```java
public interface Handler {
    boolean handle(String request);
    
    default Handler chain(Handler next) {
        return request -> handle(request) || next.handle(request);
    }
}
```

### 4. **Observer Pattern**
```java
public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
    
    default void addObserverAndNotify(Observer observer) {
        addObserver(observer);
        notifyObservers();
    }
}
```

## Exercises

### Exercise 1: Calculator Interface
Create a `Calculator` interface with default methods for common operations:

```java
public interface Calculator {
    double add(double a, double b);
    double subtract(double a, double b);
    
    // Add default methods for:
    // - multiply
    // - divide (with null check)
    // - power
    // - chain operations
}
```

### Exercise 2: Logger Interface
Create a `Logger` interface with default methods for different log levels:

```java
public interface Logger {
    void log(String message);
    
    // Add default methods for:
    // - info logging
    // - error logging
    // - debug logging
    // - formatted logging
}
```

### Exercise 3: Cache Interface
Create a `Cache` interface with default methods for common cache operations:

```java
public interface Cache<K, V> {
    V get(K key);
    void put(K key, V value);
    
    // Add default methods for:
    // - get with default value
    // - put if absent
    // - remove if present
    // - clear all
}
```

### Exercise 4: Multiple Interface Resolution
Create interfaces with conflicting default methods and resolve them:

```java
interface A {
    default void method() { System.out.println("A"); }
}

interface B {
    default void method() { System.out.println("B"); }
}

interface C extends A {
    default void method() { System.out.println("C"); }
}

// Create a class that implements A, B, and C
// Resolve the method conflicts appropriately
```

## Summary

Default methods are a powerful feature introduced in Java 8 that:

- **Enable Interface Evolution**: Allow adding new methods to interfaces without breaking existing code
- **Support Multiple Inheritance**: Provide a form of multiple inheritance of behavior
- **Improve API Design**: Enable more flexible and extensible API design
- **Maintain Backward Compatibility**: Ensure existing code continues to work

### Key Takeaways:
1. Use `default` keyword to define interface methods with implementation
2. Default methods are implicitly public and cannot be static or final
3. Method resolution follows specific rules when conflicts arise
4. Default methods are best used for convenience and backward compatibility
5. Keep default method implementations simple and well-documented

### Next Steps:
- Practice implementing default methods in your own interfaces
- Explore how default methods are used in the Java Collections Framework
- Learn about static methods in interfaces (also introduced in Java 8)
- Understand the relationship between default methods and abstract classes

Default methods have fundamentally changed how we design and evolve interfaces in Java, making the language more flexible and powerful while maintaining backward compatibility. 