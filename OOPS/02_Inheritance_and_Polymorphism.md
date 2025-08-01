# Java Inheritance and Polymorphism - Master Level Guide

## Table of Contents

1. [Introduction](#introduction)
2. [Types of Inheritance](#types-of-inheritance)
3. [Method Overriding](#method-overriding)
4. [Polymorphism](#polymorphism)
5. [Abstract Classes](#abstract-classes)
6. [Interfaces](#interfaces)
7. [Advanced Concepts](#advanced-concepts)
8. [Design Patterns](#design-patterns)

## Introduction

Inheritance allows a class to inherit properties and behaviors from another class, promoting code reuse and establishing hierarchical relationships. Polymorphism enables objects of different classes to respond to the same method call in different ways.

### Key Concepts

- **Inheritance**: Mechanism for one class to acquire properties of another
- **Polymorphism**: Ability of different objects to respond to same method call differently
- **Method Overriding**: Redefining a method in subclass with same signature as superclass
- **Dynamic Binding**: Runtime determination of which method to call

## Types of Inheritance

### Single Inheritance

```java
public class Animal {
    protected String name;
    protected int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void makeSound() {
        System.out.println(name + " makes a sound.");
    }
}

public class Dog extends Animal {
    private String breed;

    public Dog(String name, int age, String breed) {
        super(name, age);
        this.breed = breed;
    }

    @Override
    public void makeSound() {
        System.out.println(name + " barks: Woof!");
    }

    public void fetch() {
        System.out.println(name + " fetches the ball.");
    }
}
```

### Multilevel Inheritance

```java
public class Mammal extends Animal {
    protected boolean hasFur;

    public Mammal(String name, int age, boolean hasFur) {
        super(name, age);
        this.hasFur = hasFur;
    }

    public void giveBirth() {
        System.out.println(name + " gives birth to live young.");
    }
}

public class Cat extends Mammal {
    private boolean isIndoor;

    public Cat(String name, int age, boolean isIndoor) {
        super(name, age, true); // Cats have fur
        this.isIndoor = isIndoor;
    }

    @Override
    public void makeSound() {
        System.out.println(name + " meows: Meow!");
    }
}
```

## Method Overriding

### Rules for Method Overriding

```java
public class MethodOverridingDemo {
    public static void main(String[] args) {
        Animal animal = new Animal("Generic", 5);
        Dog dog = new Dog("Buddy", 3, "Golden Retriever");
        Cat cat = new Cat("Whiskers", 2, true);

        // Polymorphic behavior
        animal.makeSound(); // Generic sound
        dog.makeSound();    // Barking
        cat.makeSound();    // Meowing

        // Using superclass reference
        Animal[] animals = {animal, dog, cat};
        for (Animal a : animals) {
            a.makeSound(); // Dynamic method dispatch
        }
    }
}
```

### Covariant Return Types

```java
public class Shape {
    public Shape clone() {
        return new Shape();
    }
}

public class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public Circle clone() { // More specific return type
        return new Circle(this.radius);
    }
}
```

## Polymorphism

### Compile-time Polymorphism (Method Overloading)

```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public double add(double a, double b) {
        return a + b;
    }

    public int add(int a, int b, int c) {
        return a + b + c;
    }

    public String add(String a, String b) {
        return a + b;
    }
}
```

### Runtime Polymorphism (Method Overriding)

```java
public class PolymorphismDemo {
    public static void main(String[] args) {
        Animal animal1 = new Dog("Max", 4, "German Shepherd");
        Animal animal2 = new Cat("Luna", 2, true);

        // Same method call, different behaviors
        animal1.makeSound(); // Dog barks
        animal2.makeSound(); // Cat meows

        performAction(animal1);
        performAction(animal2);
    }

    public static void performAction(Animal animal) {
        animal.makeSound();

        // Type checking and casting
        if (animal instanceof Dog) {
            Dog dog = (Dog) animal;
            dog.fetch();
        } else if (animal instanceof Cat) {
            Cat cat = (Cat) animal;
            cat.giveBirth();
        }
    }
}
```

## Abstract Classes

```java
public abstract class Vehicle {
    protected String brand;
    protected String model;
    protected double price;

    public Vehicle(String brand, String model, double price) {
        this.brand = brand;
        this.model = model;
        this.price = price;
    }

    // Abstract method - must be implemented by subclasses
    public abstract void startEngine();
    public abstract void stopEngine();

    // Concrete method - has implementation
    public void displayInfo() {
        System.out.printf("Vehicle: %s %s - $%.2f%n", brand, model, price);
    }

    // Template method pattern
    public final void performMaintenance() {
        System.out.println("Starting maintenance for " + brand + " " + model);
        checkEngine();
        checkTires();
        System.out.println("Maintenance completed");
    }

    protected void checkEngine() {
        System.out.println("Checking engine...");
    }

    protected void checkTires() {
        System.out.println("Checking tires...");
    }
}

public class Car extends Vehicle {
    private String fuelType;

    public Car(String brand, String model, double price, String fuelType) {
        super(brand, model, price);
        this.fuelType = fuelType;
    }

    @Override
    public void startEngine() {
        System.out.println(brand + " " + model + " engine started with " + fuelType);
    }

    @Override
    public void stopEngine() {
        System.out.println(brand + " " + model + " engine stopped");
    }

    @Override
    protected void checkEngine() {
        System.out.println("Checking car engine with " + fuelType + " fuel system");
    }
}
```

## Interfaces

### Basic Interface Example

```java
public interface Payable {
    double calculatePay();
    void processPayment();
}

public interface Taxable {
    double calculateTax();
}

// Multiple interface implementation
public class Employee implements Payable, Taxable {
    private String name;
    private double hourlyRate;
    private int hoursWorked;

    public Employee(String name, double hourlyRate) {
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.hoursWorked = 0;
    }

    public void work(int hours) {
        this.hoursWorked += hours;
    }

    @Override
    public double calculatePay() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public void processPayment() {
        double pay = calculatePay();
        System.out.println("Processing payment of $" + pay + " for " + name);
        hoursWorked = 0;
    }

    @Override
    public double calculateTax() {
        double pay = calculatePay();
        return pay * 0.15; // 15% tax
    }
}
```

### Default Methods in Interfaces

```java
public interface Logger {
    void log(String message);

    // Default method - provides default implementation
    default void logInfo(String message) {
        log("INFO: " + message);
    }

    default void logError(String message) {
        log("ERROR: " + message);
    }

    // Static method in interface
    static Logger getDefaultLogger() {
        return new ConsoleLogger();
    }
}

public class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("[Console] " + message);
    }
}
```

## Advanced Concepts

### Final Classes and Methods

```java
// Final class - cannot be extended
public final class StringUtils {
    private StringUtils() {} // Private constructor

    public static String reverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }
}

public class Parent {
    // Final method - cannot be overridden
    public final void finalMethod() {
        System.out.println("Cannot be overridden");
    }

    public void overridableMethod() {
        System.out.println("Can be overridden");
    }
}
```

## Design Patterns

### Template Method Pattern

```java
public abstract class DataProcessor {
    // Template method - defines algorithm structure
    public final void processData() {
        readData();
        validateData();
        transformData();
        saveData();
    }

    // Abstract methods - must be implemented
    protected abstract void readData();
    protected abstract void validateData();
    protected abstract void transformData();
    protected abstract void saveData();
}

public class CSVProcessor extends DataProcessor {
    private String filename;

    public CSVProcessor(String filename) {
        this.filename = filename;
    }

    @Override
    protected void readData() {
        System.out.println("Reading CSV from " + filename);
    }

    @Override
    protected void validateData() {
        System.out.println("Validating CSV format");
    }

    @Override
    protected void transformData() {
        System.out.println("Transforming CSV data");
    }

    @Override
    protected void saveData() {
        System.out.println("Saving to database");
    }
}
```

### Strategy Pattern

```java
public interface PaymentStrategy {
    void pay(double amount);
}

public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " with credit card ending in " +
                          cardNumber.substring(cardNumber.length() - 4));
    }
}

public class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " via PayPal: " + email);
    }
}

public class ShoppingCart {
    private List<Item> items = new ArrayList<>();
    private PaymentStrategy paymentStrategy;

    public void addItem(Item item) {
        items.add(item);
    }

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }

    public void checkout() {
        double total = items.stream()
                           .mapToDouble(Item::getPrice)
                           .sum();
        paymentStrategy.pay(total);
        items.clear();
    }
}
```

## Real-World Example: Banking System

```java
public abstract class BankAccount {
    protected String accountNumber;
    protected String accountHolder;
    protected double balance;

    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.accountNumber = generateAccountNumber();
    }

    public abstract void calculateInterest();
    public abstract double getInterestRate();
    public abstract String getAccountType();

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    protected abstract String generateAccountNumber();

    public double getBalance() { return balance; }
}

public class SavingsAccount extends BankAccount {
    private static final double INTEREST_RATE = 0.025;
    private static final double MINIMUM_BALANCE = 100.0;

    public SavingsAccount(String accountHolder, double initialBalance) {
        super(accountHolder, initialBalance);
    }

    @Override
    public void calculateInterest() {
        if (balance >= MINIMUM_BALANCE) {
            double interest = balance * INTEREST_RATE;
            balance += interest;
        }
    }

    @Override
    public double getInterestRate() {
        return INTEREST_RATE;
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    @Override
    protected String generateAccountNumber() {
        return "SAV" + System.currentTimeMillis() % 1000000;
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance - amount >= MINIMUM_BALANCE) {
            return super.withdraw(amount);
        }
        return false;
    }
}

public class CheckingAccount extends BankAccount {
    private static final double INTEREST_RATE = 0.001;
    private int monthlyTransactions;
    private static final int FREE_TRANSACTIONS = 10;
    private static final double TRANSACTION_FEE = 2.0;

    public CheckingAccount(String accountHolder, double initialBalance) {
        super(accountHolder, initialBalance);
    }

    @Override
    public void calculateInterest() {
        double interest = balance * INTEREST_RATE;
        balance += interest;
    }

    @Override
    public double getInterestRate() {
        return INTEREST_RATE;
    }

    @Override
    public String getAccountType() {
        return "Checking";
    }

    @Override
    protected String generateAccountNumber() {
        return "CHK" + System.currentTimeMillis() % 1000000;
    }

    @Override
    public boolean withdraw(double amount) {
        if (super.withdraw(amount)) {
            monthlyTransactions++;
            if (monthlyTransactions > FREE_TRANSACTIONS) {
                balance -= TRANSACTION_FEE;
            }
            return true;
        }
        return false;
    }
}
```

## Summary

This guide covers:

1. **Inheritance Types**: Single, multilevel inheritance with practical examples
2. **Method Overriding**: Rules, covariant return types, and dynamic binding
3. **Polymorphism**: Compile-time (overloading) and runtime (overriding) polymorphism
4. **Abstract Classes**: Abstract methods, concrete methods, and template patterns
5. **Interfaces**: Multiple implementation, default methods, and static methods
6. **Advanced Concepts**: Final classes/methods and access modifiers
7. **Design Patterns**: Template method and Strategy patterns
8. **Real-World Applications**: Banking system with inheritance hierarchy

Key takeaways:

- Use inheritance for "is-a" relationships
- Prefer composition over inheritance when possible
- Implement proper method overriding with @Override
- Use abstract classes for shared implementation, interfaces for contracts
- Leverage polymorphism for flexible, extensible code
- Apply design patterns to solve common inheritance problems
