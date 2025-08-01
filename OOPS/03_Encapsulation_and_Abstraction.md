# Java Encapsulation and Abstraction - Master Level Guide

## Table of Contents

1. [Introduction](#introduction)
2. [Encapsulation](#encapsulation)
3. [Abstraction](#abstraction)
4. [Access Modifiers](#access-modifiers)
5. [Data Hiding](#data-hiding)
6. [Getter and Setter Methods](#getter-setter)
7. [Advanced Techniques](#advanced-techniques)
8. [Design Patterns](#design-patterns)

## Introduction

Encapsulation bundles data and methods that operate on that data within a single unit, while Abstraction hides complex implementation details and shows only necessary features.

### Key Concepts

- **Encapsulation**: Bundling data and methods within a single unit (class)
- **Abstraction**: Hiding complex implementation details
- **Data Hiding**: Restricting direct access to object's data
- **Information Hiding**: Concealing internal implementation details

## Encapsulation

### Basic Encapsulation Example

```java
public class BankAccount {
    // Private fields - data hiding
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private String password;

    public BankAccount(String accountHolder, String password, double initialBalance) {
        this.accountHolder = accountHolder;
        this.password = hashPassword(password);
        this.balance = initialBalance;
        this.accountNumber = generateAccountNumber();
    }

    // Public methods - controlled access to data
    public boolean authenticate(String password) {
        return this.password.equals(hashPassword(password));
    }

    public boolean deposit(double amount, String password) {
        if (!authenticate(password)) {
            throw new SecurityException("Invalid password");
        }

        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount, String password) {
        if (!authenticate(password)) {
            throw new SecurityException("Invalid password");
        }

        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // Read-only access to balance
    public double getBalance(String password) {
        if (!authenticate(password)) {
            throw new SecurityException("Invalid password");
        }
        return balance;
    }

    // Read-only access to account number
    public String getAccountNumber() {
        return accountNumber;
    }

    // Private helper methods
    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis() % 1000000;
    }

    private String hashPassword(String password) {
        return String.valueOf(password.hashCode());
    }
}
```

## Abstraction

### Abstract Class Example

```java
public abstract class DatabaseConnection {
    protected String connectionString;
    protected boolean isConnected;

    // Abstract methods - implementation details hidden
    protected abstract Connection createConnection();
    protected abstract void validateConnection();
    protected abstract void closeConnection();

    // Template method - defines algorithm structure
    public final void connect() {
        if (!isConnected) {
            createConnection();
            validateConnection();
            isConnected = true;
            System.out.println("Connected to database");
        }
    }

    public final void disconnect() {
        if (isConnected) {
            closeConnection();
            isConnected = false;
            System.out.println("Disconnected from database");
        }
    }

    public final void executeQuery(String query) {
        if (!isConnected) {
            throw new IllegalStateException("Database not connected");
        }
        System.out.println("Executing query: " + query);
    }
}

public class MySQLConnection extends DatabaseConnection {
    public MySQLConnection(String host, String database, String username, String password) {
        this.connectionString = String.format("jdbc:mysql://%s/%s?user=%s&password=%s",
                                            host, database, username, password);
    }

    @Override
    protected Connection createConnection() {
        System.out.println("Creating MySQL connection to: " + connectionString);
        return new MockConnection();
    }

    @Override
    protected void validateConnection() {
        System.out.println("Validating MySQL connection");
    }

    @Override
    protected void closeConnection() {
        System.out.println("Closing MySQL connection");
    }
}

// Mock class for demonstration
class MockConnection implements Connection {
    // Implementation details hidden
}
```

### Interface Abstraction

```java
public interface PaymentProcessor {
    PaymentResult processPayment(PaymentRequest request);
    boolean validatePayment(PaymentRequest request);
}

public interface PaymentRequest {
    double getAmount();
    String getCurrency();
    String getCardNumber();
}

public interface PaymentResult {
    boolean isSuccess();
    String getTransactionId();
    String getErrorMessage();
}

// Concrete implementation - details hidden from client
public class StripePaymentProcessor implements PaymentProcessor {
    private final String apiKey;

    public StripePaymentProcessor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public PaymentResult processPayment(PaymentRequest request) {
        if (!validatePayment(request)) {
            return new PaymentResultImpl(false, null, "Invalid payment request");
        }

        try {
            // Complex Stripe API integration hidden from client
            String response = sendToStripeAPI(request);
            return parseStripeResponse(response);
        } catch (Exception e) {
            return new PaymentResultImpl(false, null, "Payment processing failed");
        }
    }

    @Override
    public boolean validatePayment(PaymentRequest request) {
        return request.getAmount() > 0 &&
               request.getCardNumber() != null &&
               request.getCardNumber().length() >= 13;
    }

    // Private implementation details
    private String sendToStripeAPI(PaymentRequest request) {
        System.out.println("Sending payment request to Stripe API");
        return "{\"success\": true, \"transaction_id\": \"txn_123456\"}";
    }

    private PaymentResult parseStripeResponse(String response) {
        return new PaymentResultImpl(true, "txn_123456", null);
    }
}
```

## Access Modifiers

### Access Modifier Examples

```java
public class AccessModifierDemo {
    // Public - accessible from anywhere
    public String publicField = "Public";

    // Protected - accessible within same package and subclasses
    protected String protectedField = "Protected";

    // Package-private (default) - accessible within same package
    String packagePrivateField = "Package Private";

    // Private - accessible only within this class
    private String privateField = "Private";

    // Public method
    public void publicMethod() {
        System.out.println("Public method called");
        privateMethod(); // Can call private method within same class
    }

    // Protected method
    protected void protectedMethod() {
        System.out.println("Protected method called");
    }

    // Package-private method
    void packagePrivateMethod() {
        System.out.println("Package private method called");
    }

    // Private method
    private void privateMethod() {
        System.out.println("Private method called");
    }
}
```

## Data Hiding

### Immutable Objects

```java
public final class ImmutablePerson {
    // Final fields - cannot be changed after construction
    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;
    private final List<String> hobbies;

    public ImmutablePerson(String firstName, String lastName, LocalDate birthDate, List<String> hobbies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        // Defensive copy for mutable collections
        this.hobbies = new ArrayList<>(hobbies);
    }

    // Read-only accessors
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getBirthDate() { return birthDate; }

    // Defensive copy for mutable collections
    public List<String> getHobbies() {
        return new ArrayList<>(hobbies);
    }

    // Method to create new instance with changes
    public ImmutablePerson withHobby(String hobby) {
        List<String> newHobbies = new ArrayList<>(hobbies);
        newHobbies.add(hobby);
        return new ImmutablePerson(firstName, lastName, birthDate, newHobbies);
    }
}
```

### Builder Pattern

```java
public class Configuration {
    private final String databaseUrl;
    private final String username;
    private final String password;
    private final int maxConnections;
    private final int timeout;

    // Private constructor - only Builder can create instances
    private Configuration(Builder builder) {
        this.databaseUrl = builder.databaseUrl;
        this.username = builder.username;
        this.password = builder.password;
        this.maxConnections = builder.maxConnections;
        this.timeout = builder.timeout;
    }

    // Read-only accessors
    public String getDatabaseUrl() { return databaseUrl; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getMaxConnections() { return maxConnections; }
    public int getTimeout() { return timeout; }

    // Builder class
    public static class Builder {
        // Required parameters
        private final String databaseUrl;
        private final String username;
        private final String password;

        // Optional parameters with defaults
        private int maxConnections = 10;
        private int timeout = 30;

        public Builder(String databaseUrl, String username, String password) {
            this.databaseUrl = databaseUrl;
            this.username = username;
            this.password = password;
        }

        public Builder maxConnections(int maxConnections) {
            this.maxConnections = maxConnections;
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Configuration build() {
            // Validation
            if (maxConnections <= 0) {
                throw new IllegalArgumentException("Max connections must be positive");
            }
            return new Configuration(this);
        }
    }
}
```

## Getter and Setter Methods

### Smart Getters and Setters

```java
public class SmartBankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private LocalDateTime lastTransactionTime;

    public SmartBankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.accountNumber = generateAccountNumber();
        this.lastTransactionTime = LocalDateTime.now();
    }

    // Smart getter with validation
    public double getBalance() {
        if (balance < 0) {
            throw new IllegalStateException("Account has negative balance");
        }
        return balance;
    }

    // Smart setter with validation and logging
    public void setAccountHolder(String accountHolder) {
        if (accountHolder == null || accountHolder.trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name cannot be null or empty");
        }

        String oldHolder = this.accountHolder;
        this.accountHolder = accountHolder.trim();

        // Log the change
        System.out.println("Account holder changed from '" + oldHolder + "' to '" + this.accountHolder + "'");
    }

    // Computed getter
    public boolean isActive() {
        return LocalDateTime.now().minusDays(30).isBefore(lastTransactionTime);
    }

    // Read-only getters
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolder() { return accountHolder; }
    public LocalDateTime getLastTransactionTime() { return lastTransactionTime; }

    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis() % 1000000;
    }
}
```

## Advanced Techniques

### Proxy Pattern

```java
public interface Image {
    void display();
}

public class RealImage implements Image {
    private String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
        loadFromDisk();
    }

    @Override
    public void display() {
        System.out.println("Displaying image: " + fileName);
    }

    private void loadFromDisk() {
        System.out.println("Loading image from disk: " + fileName);
        // Simulate loading time
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class ProxyImage implements Image {
    private RealImage realImage;
    private String fileName;

    public ProxyImage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(fileName);
        }
        realImage.display();
    }
}
```

### Facade Pattern

```java
public class HomeTheaterFacade {
    private Amplifier amplifier;
    private DvdPlayer dvdPlayer;
    private Projector projector;
    private TheaterLights lights;
    private Screen screen;
    private PopcornPopper popper;

    public HomeTheaterFacade(Amplifier amplifier, DvdPlayer dvdPlayer,
                           Projector projector, TheaterLights lights,
                           Screen screen, PopcornPopper popper) {
        this.amplifier = amplifier;
        this.dvdPlayer = dvdPlayer;
        this.projector = projector;
        this.lights = lights;
        this.screen = screen;
        this.popper = popper;
    }

    // Simplified interface for complex subsystem
    public void watchMovie(String movie) {
        System.out.println("Get ready to watch a movie...");
        popper.on();
        popper.pop();
        lights.dim(10);
        screen.down();
        projector.on();
        amplifier.on();
        amplifier.setDvd(dvdPlayer);
        amplifier.setSurroundSound();
        amplifier.setVolume(5);
        dvdPlayer.on();
        dvdPlayer.play(movie);
    }

    public void endMovie() {
        System.out.println("Shutting movie theater down...");
        popper.off();
        lights.on();
        screen.up();
        projector.off();
        amplifier.off();
        dvdPlayer.stop();
        dvdPlayer.eject();
        dvdPlayer.off();
    }
}

// Simplified component classes
class Amplifier {
    public void on() { System.out.println("Amplifier on"); }
    public void off() { System.out.println("Amplifier off"); }
    public void setDvd(DvdPlayer dvd) { System.out.println("Amplifier setting DVD player"); }
    public void setSurroundSound() { System.out.println("Amplifier surround sound on"); }
    public void setVolume(int level) { System.out.println("Amplifier setting volume to " + level); }
}

class DvdPlayer {
    public void on() { System.out.println("DVD player on"); }
    public void off() { System.out.println("DVD player off"); }
    public void play(String movie) { System.out.println("DVD player playing " + movie); }
    public void stop() { System.out.println("DVD player stopped"); }
    public void eject() { System.out.println("DVD player eject"); }
}

class Projector {
    public void on() { System.out.println("Projector on"); }
    public void off() { System.out.println("Projector off"); }
}

class TheaterLights {
    public void on() { System.out.println("Theater lights on"); }
    public void dim(int level) { System.out.println("Theater lights dimming to " + level + "%"); }
}

class Screen {
    public void down() { System.out.println("Screen going down"); }
    public void up() { System.out.println("Screen going up"); }
}

class PopcornPopper {
    public void on() { System.out.println("Popcorn popper on"); }
    public void off() { System.out.println("Popcorn popper off"); }
    public void pop() { System.out.println("Popcorn popper popping popcorn"); }
}
```

## Design Patterns

### Singleton Pattern

```java
public class DatabaseConnection {
    private static volatile DatabaseConnection instance;
    private static final Object lock = new Object();

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
            synchronized (lock) {
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

## Real-World Example: E-commerce Product

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(String productId, String name, double price, ProductCategory category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.tags = new ArrayList<>();
        this.attributes = new HashMap<>();
        this.stockQuantity = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Business logic methods
    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public boolean isOnSale() {
        return price < getOriginalPrice();
    }

    public double getOriginalPrice() {
        return price * 1.2; // Assuming 20% markup
    }

    public void addToStock(int quantity) {
        if (quantity > 0) {
            stockQuantity += quantity;
            updatedAt = LocalDateTime.now();
        }
    }

    public boolean removeFromStock(int quantity) {
        if (quantity > 0 && stockQuantity >= quantity) {
            stockQuantity -= quantity;
            updatedAt = LocalDateTime.now();
            return true;
        }
        return false;
    }

    public void addTag(String tag) {
        if (tag != null && !tag.trim().isEmpty()) {
            tags.add(tag.toLowerCase());
            updatedAt = LocalDateTime.now();
        }
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag.toLowerCase());
    }

    public void setAttribute(String key, String value) {
        attributes.put(key, value);
        updatedAt = LocalDateTime.now();
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }

    // Getters with validation
    public String getProductId() { return productId; }

    public String getName() { return name; }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
            updatedAt = LocalDateTime.now();
        }
    }

    public double getPrice() { return price; }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
            updatedAt = LocalDateTime.now();
        }
    }

    public int getStockQuantity() { return stockQuantity; }
    public ProductCategory getCategory() { return category; }
    public List<String> getTags() { return new ArrayList<>(tags); } // Defensive copy
    public Map<String, String> getAttributes() { return new HashMap<>(attributes); } // Defensive copy
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}

public enum ProductCategory {
    ELECTRONICS, CLOTHING, BOOKS, HOME_AND_GARDEN, SPORTS, FOOD, AUTOMOTIVE, HEALTH
}
```

## Summary

This guide covers:

1. **Encapsulation**: Bundling data and methods, data hiding, and controlled access
2. **Abstraction**: Hiding implementation details through abstract classes and interfaces
3. **Access Modifiers**: Public, protected, package-private, and private access levels
4. **Data Hiding**: Immutable objects, defensive copying, and validation
5. **Getter/Setter Methods**: Smart accessors with validation and caching
6. **Advanced Techniques**: Proxy pattern, facade pattern, and builder pattern
7. **Design Patterns**: Singleton pattern with thread safety
8. **Real-World Applications**: E-commerce system with proper encapsulation

Key takeaways:

- Always use private fields and public methods for proper encapsulation
- Implement defensive copying for mutable collections
- Use validation in setters and business logic methods
- Prefer immutable objects when possible
- Apply design patterns to hide complexity
- Use interfaces for abstraction and loose coupling
- Follow the principle of least privilege with access modifiers
