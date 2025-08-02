# DRY Principle Deep Dive

## Overview

**DRY (Don't Repeat Yourself)** is a software development principle aimed at reducing repetition of software patterns, replacing it with abstractions or using data normalization to avoid redundancy.

## What is DRY?

The DRY principle states that "Every piece of knowledge or logic must have a single, unambiguous, authoritative representation within a system." This means:

- **No code duplication**: The same code should not be written multiple times
- **Single source of truth**: Each piece of knowledge should exist in only one place
- **Maintainability**: Changes need to be made in only one location

## Benefits of Following DRY

1. **Reduced Maintenance**: Changes only need to be made in one place
2. **Consistency**: Ensures consistent behavior across the application
3. **Reduced Bugs**: Fewer places where bugs can occur
4. **Improved Readability**: Code is cleaner and easier to understand
5. **Faster Development**: Reusable components speed up development

## Common Areas Where DRY Violations Occur

### 1. Code Duplication

#### ❌ Violation Example

```java
// Bad: Repeated validation logic
public class UserService {
    public void createUser(String username, String email) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        // Create user logic
    }

    public void updateUser(String username, String email) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        // Update user logic
    }

    public void deleteUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        // Delete user logic
    }
}
```

#### ✅ Correct Implementation

```java
// Good: Centralized validation
public class ValidationUtils {
    public static void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}

public class UserService {
    public void createUser(String username, String email) {
        ValidationUtils.validateUsername(username);
        ValidationUtils.validateEmail(email);
        // Create user logic
    }

    public void updateUser(String username, String email) {
        ValidationUtils.validateUsername(username);
        ValidationUtils.validateEmail(email);
        // Update user logic
    }

    public void deleteUser(String username) {
        ValidationUtils.validateUsername(username);
        // Delete user logic
    }
}
```

### 2. Configuration Duplication

#### ❌ Violation Example

```java
// Bad: Database configuration repeated in multiple classes
public class UserRepository {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}

public class ProductRepository {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
```

#### ✅ Correct Implementation

```java
// Good: Centralized configuration
public class DatabaseConfig {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}

public class UserRepository {
    public Connection getConnection() throws SQLException {
        return DatabaseConfig.getConnection();
    }
}

public class ProductRepository {
    public Connection getConnection() throws SQLException {
        return DatabaseConfig.getConnection();
    }
}
```

### 3. Business Logic Duplication

#### ❌ Violation Example

```java
// Bad: Same business logic repeated in multiple places
public class OrderService {
    public double calculateDiscount(Order order) {
        double total = order.getTotal();
        if (total > 1000) {
            return total * 0.1; // 10% discount
        } else if (total > 500) {
            return total * 0.05; // 5% discount
        }
        return 0;
    }
}

public class InvoiceService {
    public double calculateDiscount(Invoice invoice) {
        double total = invoice.getTotal();
        if (total > 1000) {
            return total * 0.1; // 10% discount
        } else if (total > 500) {
            return total * 0.05; // 5% discount
        }
        return 0;
    }
}
```

#### ✅ Correct Implementation

```java
// Good: Centralized business logic
public class DiscountCalculator {
    public static double calculateDiscount(double total) {
        if (total > 1000) {
            return total * 0.1; // 10% discount
        } else if (total > 500) {
            return total * 0.05; // 5% discount
        }
        return 0;
    }
}

public class OrderService {
    public double calculateDiscount(Order order) {
        return DiscountCalculator.calculateDiscount(order.getTotal());
    }
}

public class InvoiceService {
    public double calculateDiscount(Invoice invoice) {
        return DiscountCalculator.calculateDiscount(invoice.getTotal());
    }
}
```

### 4. Data Structure Duplication

#### ❌ Violation Example

```java
// Bad: Same data structure defined multiple times
public class User {
    private String name;
    private String email;
    private String phone;

    // Constructor, getters, setters
}

public class Customer {
    private String name;
    private String email;
    private String phone;

    // Constructor, getters, setters
}

public class Employee {
    private String name;
    private String email;
    private String phone;

    // Constructor, getters, setters
}
```

#### ✅ Correct Implementation

```java
// Good: Shared base class
public abstract class Person {
    protected String name;
    protected String email;
    protected String phone;

    public Person(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters and setters
}

public class User extends Person {
    public User(String name, String email, String phone) {
        super(name, email, phone);
    }
    // User-specific methods
}

public class Customer extends Person {
    public Customer(String name, String email, String phone) {
        super(name, email, phone);
    }
    // Customer-specific methods
}

public class Employee extends Person {
    public Employee(String name, String email, String phone) {
        super(name, email, phone);
    }
    // Employee-specific methods
}
```

## Advanced DRY Techniques

### 1. Template Method Pattern

```java
// Abstract base class with common algorithm structure
public abstract class ReportGenerator {
    public final void generateReport() {
        String data = fetchData();
        String processedData = processData(data);
        String formattedReport = formatReport(processedData);
        saveReport(formattedReport);
    }

    protected abstract String fetchData();
    protected abstract String processData(String data);
    protected abstract String formatReport(String data);
    protected abstract void saveReport(String report);
}

public class PDFReportGenerator extends ReportGenerator {
    @Override
    protected String fetchData() {
        return "PDF data";
    }

    @Override
    protected String processData(String data) {
        return "Processed " + data;
    }

    @Override
    protected String formatReport(String data) {
        return "PDF formatted " + data;
    }

    @Override
    protected void saveReport(String report) {
        // Save as PDF
    }
}
```

### 2. Strategy Pattern

```java
// Strategy interface
public interface PaymentStrategy {
    void pay(double amount);
}

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        // Credit card payment logic
    }
}

public class PayPalPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        // PayPal payment logic
    }
}

public class PaymentProcessor {
    private PaymentStrategy strategy;

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void processPayment(double amount) {
        strategy.pay(amount);
    }
}
```

### 3. Utility Classes

```java
// Centralized utility methods
public class StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static String reverse(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return new StringBuilder(str).reverse().toString();
    }
}
```

## When NOT to Apply DRY

### 1. Premature Abstraction

```java
// Bad: Over-abstraction for simple cases
public class StringProcessor {
    public static String process(String input, StringOperation operation) {
        return operation.apply(input);
    }
}

public interface StringOperation {
    String apply(String input);
}

// Usage becomes complex for simple operations
String result = StringProcessor.process("hello", input -> input.toUpperCase());
```

### 2. Accidental Similarity

```java
// Bad: Forcing abstraction where none is needed
public class ShapeCalculator {
    public static double calculateArea(double width, double height) {
        return width * height; // Rectangle area
    }

    public static double calculateArea(double radius) {
        return Math.PI * radius * radius; // Circle area
    }
}
```

## Best Practices for Applying DRY

1. **Identify Patterns**: Look for repeated code patterns
2. **Extract Common Logic**: Create shared methods or classes
3. **Use Inheritance**: Share common behavior through base classes
4. **Apply Design Patterns**: Use patterns like Template Method, Strategy, etc.
5. **Create Utility Classes**: Centralize common operations
6. **Use Configuration**: Externalize configuration values
7. **Regular Refactoring**: Continuously identify and eliminate duplication

## Common Pitfalls

1. **Over-abstraction**: Creating abstractions that are too complex
2. **Premature Optimization**: Applying DRY before understanding the domain
3. **Tight Coupling**: Creating dependencies that are hard to change
4. **Ignoring Context**: Forcing abstraction where context differs significantly

## Summary

The DRY principle is fundamental to writing maintainable code. By eliminating duplication, we reduce the risk of inconsistencies and make our code easier to maintain. However, it's important to apply DRY judiciously and avoid over-abstraction. The goal is to find the right balance between eliminating duplication and maintaining code clarity.
