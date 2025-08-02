# Composition Over Inheritance Deep Dive

## Overview

**Composition Over Inheritance** is a design principle that suggests using composition (has-a relationship) instead of inheritance (is-a relationship) to achieve code reuse and flexibility. This principle is often stated as "Favor composition over inheritance."

## What is Composition Over Inheritance?

The principle advocates:

- **Composition**: Building complex objects by combining simpler ones
- **Delegation**: Using other objects to handle specific responsibilities
- **Flexibility**: Easier to change behavior at runtime
- **Avoiding inheritance issues**: Problems with deep inheritance hierarchies

## Benefits of Composition

1. **Flexibility**: Can change behavior at runtime
2. **Loose Coupling**: Components are less dependent on each other
3. **Easier Testing**: Can mock individual components
4. **Avoids Inheritance Issues**: No problems with fragile base classes
5. **Multiple Behaviors**: Can combine multiple behaviors easily

## Problems with Inheritance

### 1. Fragile Base Class Problem

#### ❌ Inheritance Example

```java
// Bad: Inheritance can lead to fragile base class problem
public class Animal {
    public void makeSound() {
        System.out.println("Some sound");
    }

    public void move() {
        System.out.println("Moving");
    }
}

public class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }
}

public class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Meow!");
    }
}

// Problem: If we change the base class, it might break subclasses
public class Animal {
    public void makeSound() {
        System.out.println("Some sound");
        // Adding new behavior that might break subclasses
        this.eat(); // This might not be appropriate for all animals
    }

    public void eat() {
        System.out.println("Eating");
    }
}
```

#### ✅ Composition Example

```java
// Good: Using composition avoids fragile base class problem
public interface SoundBehavior {
    void makeSound();
}

public class BarkBehavior implements SoundBehavior {
    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }
}

public class MeowBehavior implements SoundBehavior {
    @Override
    public void makeSound() {
        System.out.println("Meow!");
    }
}

public class Animal {
    private SoundBehavior soundBehavior;

    public Animal(SoundBehavior soundBehavior) {
        this.soundBehavior = soundBehavior;
    }

    public void makeSound() {
        soundBehavior.makeSound();
    }

    public void setSoundBehavior(SoundBehavior soundBehavior) {
        this.soundBehavior = soundBehavior;
    }
}

// Usage
Animal dog = new Animal(new BarkBehavior());
Animal cat = new Animal(new MeowBehavior());

// Can change behavior at runtime
dog.setSoundBehavior(new MeowBehavior()); // Dog now meows!
```

### 2. Multiple Inheritance Problem

#### ❌ Inheritance Problem

```java
// Bad: Java doesn't support multiple inheritance
public class FlyingAnimal {
    public void fly() {
        System.out.println("Flying");
    }
}

public class SwimmingAnimal {
    public void swim() {
        System.out.println("Swimming");
    }
}

// Can't do this in Java:
// public class Duck extends FlyingAnimal, SwimmingAnimal { }

// Workaround with inheritance - becomes complex
public class FlyingAnimal {
    public void fly() {
        System.out.println("Flying");
    }
}

public class SwimmingFlyingAnimal extends FlyingAnimal {
    public void swim() {
        System.out.println("Swimming");
    }
}

public class Duck extends SwimmingFlyingAnimal {
    // Duck can fly and swim, but what if we need different combinations?
}
```

#### ✅ Composition Solution

```java
// Good: Composition handles multiple behaviors easily
public interface FlyBehavior {
    void fly();
}

public interface SwimBehavior {
    void swim();
}

public class CanFly implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("Flying");
    }
}

public class CannotFly implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("Cannot fly");
    }
}

public class CanSwim implements SwimBehavior {
    @Override
    public void swim() {
        System.out.println("Swimming");
    }
}

public class CannotSwim implements SwimBehavior {
    @Override
    public void swim() {
        System.out.println("Cannot swim");
    }
}

public class Animal {
    private FlyBehavior flyBehavior;
    private SwimBehavior swimBehavior;

    public Animal(FlyBehavior flyBehavior, SwimBehavior swimBehavior) {
        this.flyBehavior = flyBehavior;
        this.swimBehavior = swimBehavior;
    }

    public void fly() {
        flyBehavior.fly();
    }

    public void swim() {
        swimBehavior.swim();
    }

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public void setSwimBehavior(SwimBehavior swimBehavior) {
        this.swimBehavior = swimBehavior;
    }
}

// Easy to create different combinations
Animal duck = new Animal(new CanFly(), new CanSwim());
Animal fish = new Animal(new CannotFly(), new CanSwim());
Animal bird = new Animal(new CanFly(), new CannotSwim());
```

### 3. Tight Coupling Problem

#### ❌ Inheritance Example

```java
// Bad: Tight coupling with inheritance
public class DatabaseConnection {
    public void connect() {
        System.out.println("Connecting to database");
    }

    public void disconnect() {
        System.out.println("Disconnecting from database");
    }
}

public class MySQLConnection extends DatabaseConnection {
    @Override
    public void connect() {
        System.out.println("Connecting to MySQL database");
    }
}

public class UserService extends MySQLConnection {
    public void createUser(String username) {
        connect();
        // Create user logic
        disconnect();
    }
}

// Problem: UserService is tightly coupled to MySQL
// What if we want to use PostgreSQL instead?
```

#### ✅ Composition Example

```java
// Good: Loose coupling with composition
public interface DatabaseConnection {
    void connect();
    void disconnect();
}

public class MySQLConnection implements DatabaseConnection {
    @Override
    public void connect() {
        System.out.println("Connecting to MySQL database");
    }

    @Override
    public void disconnect() {
        System.out.println("Disconnecting from MySQL database");
    }
}

public class PostgreSQLConnection implements DatabaseConnection {
    @Override
    public void connect() {
        System.out.println("Connecting to PostgreSQL database");
    }

    @Override
    public void disconnect() {
        System.out.println("Disconnecting from PostgreSQL database");
    }
}

public class UserService {
    private DatabaseConnection databaseConnection;

    public UserService(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void createUser(String username) {
        databaseConnection.connect();
        // Create user logic
        databaseConnection.disconnect();
    }

    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
}

// Easy to switch databases
UserService userService = new UserService(new MySQLConnection());
userService.createUser("john");

// Switch to PostgreSQL
userService.setDatabaseConnection(new PostgreSQLConnection());
userService.createUser("jane");
```

## Real-World Examples

### 1. Strategy Pattern

```java
// Strategy pattern using composition
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
        System.out.println("Paid $" + amount + " using credit card: " + cardNumber);
    }
}

public class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using PayPal: " + email);
    }
}

public class ShoppingCart {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout(double total) {
        paymentStrategy.pay(total);
    }
}

// Usage
ShoppingCart cart = new ShoppingCart();
cart.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456"));
cart.checkout(100.0);

cart.setPaymentStrategy(new PayPalPayment("user@example.com"));
cart.checkout(50.0);
```

### 2. Decorator Pattern

```java
// Decorator pattern using composition
public interface Coffee {
    double getCost();
    String getDescription();
}

public class SimpleCoffee implements Coffee {
    @Override
    public double getCost() {
        return 2.0;
    }

    @Override
    public String getDescription() {
        return "Simple coffee";
    }
}

public abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public double getCost() {
        return coffee.getCost();
    }

    @Override
    public String getDescription() {
        return coffee.getDescription();
    }
}

public class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.5;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", milk";
    }
}

public class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.2;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", sugar";
    }
}

// Usage - flexible combinations
Coffee coffee = new SimpleCoffee();
System.out.println(coffee.getDescription() + ": $" + coffee.getCost());

coffee = new MilkDecorator(coffee);
System.out.println(coffee.getDescription() + ": $" + coffee.getCost());

coffee = new SugarDecorator(coffee);
System.out.println(coffee.getDescription() + ": $" + coffee.getCost());
```

### 3. Adapter Pattern

```java
// Adapter pattern using composition
public interface ModernInterface {
    void modernMethod();
}

public class LegacyClass {
    public void legacyMethod() {
        System.out.println("Legacy method called");
    }
}

public class LegacyAdapter implements ModernInterface {
    private LegacyClass legacyClass;

    public LegacyAdapter(LegacyClass legacyClass) {
        this.legacyClass = legacyClass;
    }

    @Override
    public void modernMethod() {
        legacyClass.legacyMethod();
    }
}

public class ModernClient {
    private ModernInterface modernInterface;

    public ModernClient(ModernInterface modernInterface) {
        this.modernInterface = modernInterface;
    }

    public void doSomething() {
        modernInterface.modernMethod();
    }
}

// Usage
LegacyClass legacy = new LegacyClass();
ModernInterface adapter = new LegacyAdapter(legacy);
ModernClient client = new ModernClient(adapter);
client.doSomething();
```

## When to Use Inheritance

### 1. True "Is-A" Relationships

```java
// Good: True inheritance relationship
public abstract class Shape {
    protected double area;

    public abstract double calculateArea();

    public double getArea() {
        return area;
    }
}

public class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        area = Math.PI * radius * radius;
        return area;
    }
}

public class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        area = width * height;
        return area;
    }
}
```

### 2. Framework Extensions

```java
// Good: Framework extension
public abstract class AbstractController {
    protected ModelAndView handleRequest(HttpServletRequest request) {
        // Common request handling logic
        return new ModelAndView();
    }

    protected abstract ModelAndView processRequest(HttpServletRequest request);
}

public class UserController extends AbstractController {
    @Override
    protected ModelAndView processRequest(HttpServletRequest request) {
        // User-specific request processing
        return new ModelAndView("user");
    }
}
```

## Best Practices

### 1. Prefer Composition

```java
// Good: Use composition for behavior
public class Logger {
    public void log(String message) {
        System.out.println("Logging: " + message);
    }
}

public class UserService {
    private Logger logger;

    public UserService(Logger logger) {
        this.logger = logger;
    }

    public void createUser(String username) {
        // Create user logic
        logger.log("User created: " + username);
    }
}
```

### 2. Use Interfaces

```java
// Good: Program to interfaces
public interface Logger {
    void log(String message);
}

public class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("Console: " + message);
    }
}

public class FileLogger implements Logger {
    @Override
    public void log(String message) {
        // Write to file
    }
}

public class UserService {
    private Logger logger;

    public UserService(Logger logger) {
        this.logger = logger;
    }

    public void createUser(String username) {
        logger.log("User created: " + username);
    }
}
```

### 3. Dependency Injection

```java
// Good: Use dependency injection
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final Logger logger;

    public UserService(UserRepository userRepository,
                      EmailService emailService,
                      Logger logger) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.logger = logger;
    }

    public void createUser(String username, String email) {
        User user = new User(username, email);
        userRepository.save(user);
        emailService.sendWelcomeEmail(email);
        logger.log("User created: " + username);
    }
}
```

## Common Pitfalls

1. **Overusing Inheritance**: Using inheritance for code reuse instead of behavior
2. **Deep Inheritance Hierarchies**: Creating complex inheritance trees
3. **Ignoring Composition**: Not considering composition as an alternative
4. **Tight Coupling**: Creating tightly coupled inheritance relationships

## Summary

Composition over inheritance is a powerful principle that promotes flexible, maintainable, and testable code. It helps avoid many of the problems associated with inheritance while providing greater flexibility and loose coupling.

Key takeaways:

- **Use composition for behavior**: Combine objects to achieve functionality
- **Use inheritance for true "is-a" relationships**: Only when there's a genuine inheritance relationship
- **Program to interfaces**: Use interfaces to define contracts
- **Favor delegation**: Let other objects handle specific responsibilities
- **Keep it flexible**: Design for change and extension

Remember: **"Composition is about what objects can do, inheritance is about what objects are."**
