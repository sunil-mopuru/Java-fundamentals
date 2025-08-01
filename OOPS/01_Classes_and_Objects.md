# Java Classes and Objects - Master Level Guide

## Table of Contents

1. [Introduction to Classes and Objects](#introduction)
2. [Class Declaration and Structure](#class-declaration)
3. [Object Creation and Instantiation](#object-creation)
4. [Advanced Class Features](#advanced-features)
5. [Best Practices and Design Patterns](#best-practices)
6. [Real-World Examples](#real-world-examples)

## Introduction

Classes and Objects are the fundamental building blocks of Object-Oriented Programming in Java. A class is a blueprint or template that defines the structure and behavior of objects, while an object is an instance of a class.

### Key Concepts

- **Class**: A template that defines attributes (fields) and behaviors (methods)
- **Object**: An instance of a class with specific values for its attributes
- **Encapsulation**: Bundling data and methods that operate on that data within a single unit
- **State**: The current values of an object's attributes
- **Behavior**: The actions an object can perform through its methods

## Class Declaration and Structure

### Basic Class Structure

```java
// Access modifier + class keyword + class name
public class BankAccount {
    // Instance variables (fields) - define object state
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private static int totalAccounts = 0; // Class variable (shared across all instances)

    // Constants
    public static final double MINIMUM_BALANCE = 100.0;
    public static final double TRANSACTION_FEE = 2.50;

    // Constructor - special method for object initialization
    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.accountNumber = generateAccountNumber();
        totalAccounts++;
    }

    // Instance methods - define object behavior
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount + TRANSACTION_FEE) {
            balance -= (amount + TRANSACTION_FEE);
            System.out.println("Withdrawn: $" + amount + " (Fee: $" + TRANSACTION_FEE + ")");
            return true;
        }
        return false;
    }

    // Getter methods (accessors)
    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    // Setter methods (mutators) with validation
    public void setAccountHolder(String accountHolder) {
        if (accountHolder != null && !accountHolder.trim().isEmpty()) {
            this.accountHolder = accountHolder;
        } else {
            throw new IllegalArgumentException("Account holder name cannot be null or empty");
        }
    }

    // Static method - belongs to class, not instance
    public static int getTotalAccounts() {
        return totalAccounts;
    }

    // Private helper method
    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis() % 1000000;
    }

    // Override toString method for meaningful string representation
    @Override
    public String toString() {
        return String.format("BankAccount{accountNumber='%s', holder='%s', balance=%.2f}",
                           accountNumber, accountHolder, balance);
    }

    // Override equals method for proper object comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BankAccount that = (BankAccount) obj;
        return Objects.equals(accountNumber, that.accountNumber);
    }

    // Override hashCode method (should be consistent with equals)
    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}
```

## Object Creation and Instantiation

### Creating Objects

```java
public class ObjectCreationDemo {
    public static void main(String[] args) {
        // Creating objects using constructors
        BankAccount account1 = new BankAccount("John Doe", 1000.0);
        BankAccount account2 = new BankAccount("Jane Smith", 2500.0);

        // Using object methods
        account1.deposit(500.0);
        account2.withdraw(200.0);

        // Accessing static members
        System.out.println("Total accounts created: " + BankAccount.getTotalAccounts());

        // Object comparison
        System.out.println("Accounts equal: " + account1.equals(account2));

        // String representation
        System.out.println(account1.toString());
    }
}
```

### Object Lifecycle

```java
public class ObjectLifecycleDemo {
    public static void main(String[] args) {
        // 1. Object Creation
        BankAccount account = new BankAccount("Alice", 1000.0);

        // 2. Object Usage
        account.deposit(500.0);
        account.withdraw(200.0);

        // 3. Object Reference Manipulation
        BankAccount accountRef = account; // Reference assignment
        accountRef.deposit(100.0); // Same object, different reference

        // 4. Object becomes eligible for garbage collection
        account = null; // Original reference set to null
        accountRef = null; // All references removed

        // Garbage collector will eventually reclaim the memory
    }
}
```

## Advanced Class Features

### Nested Classes

```java
public class OuterClass {
    private int outerField = 10;

    // Static nested class
    public static class StaticNestedClass {
        private int nestedField;

        public StaticNestedClass(int value) {
            this.nestedField = value;
        }

        public void display() {
            System.out.println("Static nested field: " + nestedField);
            // Cannot access outerField directly (no outer instance)
        }
    }

    // Inner class (non-static)
    public class InnerClass {
        private int innerField;

        public InnerClass(int value) {
            this.innerField = value;
        }

        public void display() {
            System.out.println("Inner field: " + innerField);
            System.out.println("Outer field: " + outerField); // Can access outer fields
        }
    }

    // Local class (defined inside a method)
    public void methodWithLocalClass() {
        class LocalClass {
            private String localField = "Local";

            public void display() {
                System.out.println("Local field: " + localField);
                System.out.println("Outer field: " + outerField);
            }
        }

        LocalClass local = new LocalClass();
        local.display();
    }

    // Anonymous class
    public void methodWithAnonymousClass() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous class executing");
                System.out.println("Outer field: " + outerField);
            }
        };

        runnable.run();
    }
}
```

### Abstract Classes

```java
// Abstract class - cannot be instantiated directly
public abstract class Shape {
    protected String color;
    protected boolean filled;

    // Abstract method - must be implemented by subclasses
    public abstract double getArea();
    public abstract double getPerimeter();

    // Concrete method - has implementation
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    // Constructor
    public Shape(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }

    // Override toString
    @Override
    public String toString() {
        return String.format("Shape[color=%s, filled=%s]", color, filled);
    }
}

// Concrete class extending abstract class
public class Circle extends Shape {
    private double radius;

    public Circle(String color, boolean filled, double radius) {
        super(color, filled);
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        if (radius > 0) {
            this.radius = radius;
        } else {
            throw new IllegalArgumentException("Radius must be positive");
        }
    }

    @Override
    public String toString() {
        return String.format("Circle[%s, radius=%.2f]", super.toString(), radius);
    }
}
```

## Best Practices and Design Patterns

### Builder Pattern

```java
public class Person {
    private final String firstName;
    private final String lastName;
    private final int age;
    private final String email;
    private final String phone;
    private final String address;

    // Private constructor - only Builder can create instances
    private Person(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.email = builder.email;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    // Static Builder class
    public static class Builder {
        // Required parameters
        private final String firstName;
        private final String lastName;

        // Optional parameters - initialized to default values
        private int age = 0;
        private String email = "";
        private String phone = "";
        private String address = "";

        public Builder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    @Override
    public String toString() {
        return String.format("Person{firstName='%s', lastName='%s', age=%d, email='%s', phone='%s', address='%s'}",
                           firstName, lastName, age, email, phone, address);
    }
}

// Usage of Builder Pattern
public class BuilderPatternDemo {
    public static void main(String[] args) {
        Person person = new Person.Builder("John", "Doe")
            .age(30)
            .email("john.doe@email.com")
            .phone("123-456-7890")
            .address("123 Main St, City, State")
            .build();

        System.out.println(person);
    }
}
```

### Singleton Pattern

```java
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private String connectionString;
    private boolean isConnected;

    // Private constructor - prevents external instantiation
    private DatabaseConnection() {
        this.connectionString = "jdbc:mysql://localhost:3306/mydb";
        this.isConnected = false;
    }

    // Thread-safe singleton with double-checked locking
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public void connect() {
        if (!isConnected) {
            System.out.println("Connecting to database: " + connectionString);
            isConnected = true;
        }
    }

    public void disconnect() {
        if (isConnected) {
            System.out.println("Disconnecting from database");
            isConnected = false;
        }
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void executeQuery(String query) {
        if (isConnected) {
            System.out.println("Executing query: " + query);
        } else {
            throw new IllegalStateException("Database not connected");
        }
    }
}
```

## Real-World Examples

### E-commerce Product System

```java
public class Product {
    private final String productId;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private ProductCategory category;
    private List<String> tags;
    private Map<String, String> attributes;

    public Product(String productId, String name, double price, ProductCategory category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.tags = new ArrayList<>();
        this.attributes = new HashMap<>();
        this.stockQuantity = 0;
    }

    // Business logic methods
    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public boolean isOnSale() {
        return price < getOriginalPrice();
    }

    public double getOriginalPrice() {
        // In a real system, this would be stored separately
        return price * 1.2; // Assuming 20% markup
    }

    public void addToStock(int quantity) {
        if (quantity > 0) {
            stockQuantity += quantity;
        }
    }

    public boolean removeFromStock(int quantity) {
        if (quantity > 0 && stockQuantity >= quantity) {
            stockQuantity -= quantity;
            return true;
        }
        return false;
    }

    public void addTag(String tag) {
        if (tag != null && !tag.trim().isEmpty()) {
            tags.add(tag.toLowerCase());
        }
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag.toLowerCase());
    }

    public void setAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }

    // Getters and setters
    public String getProductId() { return productId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStockQuantity() { return stockQuantity; }
    public ProductCategory getCategory() { return category; }
    public void setCategory(ProductCategory category) { this.category = category; }
    public List<String> getTags() { return new ArrayList<>(tags); } // Defensive copy
    public Map<String, String> getAttributes() { return new HashMap<>(attributes); } // Defensive copy

    @Override
    public String toString() {
        return String.format("Product{id='%s', name='%s', price=%.2f, stock=%d, category=%s}",
                           productId, name, price, stockQuantity, category);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}

public enum ProductCategory {
    ELECTRONICS, CLOTHING, BOOKS, HOME_AND_GARDEN, SPORTS, FOOD, AUTOMOTIVE, HEALTH
}
```

### Usage Example

```java
public class EcommerceDemo {
    public static void main(String[] args) {
        // Create products
        Product laptop = new Product("LAP001", "Gaming Laptop", 1299.99, ProductCategory.ELECTRONICS);
        laptop.setDescription("High-performance gaming laptop with RTX graphics");
        laptop.addToStock(10);
        laptop.addTag("gaming");
        laptop.addTag("laptop");
        laptop.addTag("rtx");
        laptop.setAttribute("RAM", "16GB");
        laptop.setAttribute("Storage", "512GB SSD");

        Product book = new Product("BK001", "Java Programming", 49.99, ProductCategory.BOOKS);
        book.setDescription("Comprehensive guide to Java programming");
        book.addToStock(25);
        book.addTag("programming");
        book.addTag("java");
        book.addTag("education");

        // Demonstrate functionality
        System.out.println("Laptop in stock: " + laptop.isInStock());
        System.out.println("Laptop has 'gaming' tag: " + laptop.hasTag("gaming"));
        System.out.println("Laptop RAM: " + laptop.getAttribute("RAM"));

        // Remove from stock
        laptop.removeFromStock(2);
        System.out.println("Laptop stock after purchase: " + laptop.getStockQuantity());

        // Product comparison
        System.out.println("Products equal: " + laptop.equals(book));
    }
}
```

## Summary

This comprehensive guide covers:

1. **Class Structure**: Fields, methods, constructors, and access modifiers
2. **Object Creation**: Instantiation, lifecycle, and memory management
3. **Advanced Features**: Nested classes, abstract classes, and inheritance
4. **Design Patterns**: Builder and Singleton patterns for better code organization
5. **Best Practices**: Encapsulation, validation, defensive copying, and proper equals/hashCode implementation
6. **Real-World Applications**: Practical examples from e-commerce and banking domains

Key takeaways for mastering level:

- Always implement proper encapsulation with private fields and public methods
- Use constructors for object initialization
- Implement equals() and hashCode() consistently
- Consider design patterns for complex object creation
- Use defensive copying for mutable collections
- Override toString() for meaningful object representation
- Follow the principle of least privilege with access modifiers
