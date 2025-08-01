# Object-Oriented Programming in Java - Deep Dive

## 1. Introduction to OOP

Object-Oriented Programming (OOP) is a programming paradigm that organizes code into objects that contain both data and behavior. Java is a pure object-oriented language that supports four main principles:

- **Encapsulation**: Bundling data and methods that operate on that data within a single unit
- **Inheritance**: Creating new classes based on existing ones
- **Polymorphism**: The ability to present the same interface for different underlying forms
- **Abstraction**: Hiding complex implementation details and showing only necessary features

## 2. Classes and Objects

### Class Definition

A class is a blueprint or template for creating objects. It defines the structure and behavior that objects of that class will have.

```java
public class Car {
    // Instance variables (attributes)
    private String brand;
    private String model;
    private int year;
    private double price;
    
    // Constructor
    public Car(String brand, String model, int year, double price) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
    }
    
    // Instance methods (behaviors)
    public void start() {
        System.out.println(brand + " " + model + " is starting...");
    }
    
    public void stop() {
        System.out.println(brand + " " + model + " is stopping...");
    }
    
    public void displayInfo() {
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Price: $" + price);
    }
    
    // Getter and setter methods
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
```

### Object Creation and Usage

```java
public class CarDemo {
    public static void main(String[] args) {
        // Creating objects (instances) of the Car class
        Car car1 = new Car("Toyota", "Camry", 2020, 25000.0);
        Car car2 = new Car("Honda", "Civic", 2019, 22000.0);
        
        // Using object methods
        car1.start();
        car1.displayInfo();
        car1.stop();
        
        System.out.println();
        
        car2.start();
        car2.displayInfo();
        car2.stop();
        
        // Modifying object state
        car1.setPrice(26000.0);
        System.out.println("Updated price: $" + car1.getPrice());
    }
}
```

### Class Components

#### Instance Variables
- Also called fields or attributes
- Store the state of an object
- Each object has its own copy of instance variables

#### Methods
- Define the behavior of objects
- Can access and modify instance variables
- Can be public, private, protected, or package-private

#### Constructors
- Special methods used to initialize objects
- Have the same name as the class
- Can be overloaded (multiple constructors with different parameters)

```java
public class Student {
    private String name;
    private int age;
    private String major;
    
    // Default constructor
    public Student() {
        this.name = "Unknown";
        this.age = 18;
        this.major = "Undeclared";
    }
    
    // Parameterized constructor
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
        this.major = "Undeclared";
    }
    
    // Full parameterized constructor
    public Student(String name, int age, String major) {
        this.name = name;
        this.age = age;
        this.major = major;
    }
    
    // Copy constructor
    public Student(Student other) {
        this.name = other.name;
        this.age = other.age;
        this.major = other.major;
    }
}
```

## 3. Encapsulation

Encapsulation is the bundling of data and methods that operate on that data within a single unit, while hiding the internal state and requiring all interactions to be performed through an object's methods.

### Access Modifiers

```java
public class BankAccount {
    // Private instance variables (data hiding)
    private String accountNumber;
    private double balance;
    private String accountHolder;
    
    // Public constructor
    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }
    
    // Public methods to access and modify private data
    public double getBalance() {
        return balance;
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }
    
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
            return true;
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds");
            return false;
        }
    }
    
    public String getAccountInfo() {
        return "Account: " + accountNumber + ", Holder: " + accountHolder + ", Balance: $" + balance;
    }
}
```

### Benefits of Encapsulation

1. **Data Hiding**: Internal state is protected from external access
2. **Controlled Access**: Data can only be modified through defined methods
3. **Flexibility**: Internal implementation can be changed without affecting external code
4. **Maintainability**: Easier to maintain and debug code

## 4. Inheritance

Inheritance allows a class to inherit properties and methods from another class, promoting code reuse and establishing a hierarchical relationship.

### Basic Inheritance

```java
// Parent class (superclass)
public class Animal {
    protected String name;
    protected int age;
    
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public void eat() {
        System.out.println(name + " is eating");
    }
    
    public void sleep() {
        System.out.println(name + " is sleeping");
    }
    
    public void makeSound() {
        System.out.println(name + " makes a sound");
    }
}

// Child class (subclass)
public class Dog extends Animal {
    private String breed;
    
    public Dog(String name, int age, String breed) {
        super(name, age);  // Call parent constructor
        this.breed = breed;
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " barks");
    }
    
    public void fetch() {
        System.out.println(name + " is fetching the ball");
    }
    
    public String getBreed() {
        return breed;
    }
}

// Another child class
public class Cat extends Animal {
    private boolean isIndoor;
    
    public Cat(String name, int age, boolean isIndoor) {
        super(name, age);
        this.isIndoor = isIndoor;
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " meows");
    }
    
    public void climb() {
        System.out.println(name + " is climbing");
    }
    
    public boolean isIndoor() {
        return isIndoor;
    }
}
```

### Inheritance Hierarchy

```java
public class InheritanceDemo {
    public static void main(String[] args) {
        Animal animal = new Animal("Generic Animal", 5);
        Dog dog = new Dog("Buddy", 3, "Golden Retriever");
        Cat cat = new Cat("Whiskers", 2, true);
        
        // Using inherited methods
        animal.eat();
        animal.makeSound();
        
        dog.eat();        // Inherited from Animal
        dog.makeSound();  // Overridden method
        dog.fetch();      // Dog-specific method
        
        cat.eat();        // Inherited from Animal
        cat.makeSound();  // Overridden method
        cat.climb();      // Cat-specific method
        
        // Polymorphism
        Animal[] animals = {animal, dog, cat};
        for (Animal a : animals) {
            a.makeSound();  // Each calls its own version
        }
    }
}
```

### Method Overriding

Method overriding allows a subclass to provide a specific implementation of a method that is already defined in its parent class.

```java
public class Shape {
    protected double area;
    
    public void calculateArea() {
        System.out.println("Calculating area of generic shape");
    }
    
    public void displayInfo() {
        System.out.println("Area: " + area);
    }
}

public class Circle extends Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public void calculateArea() {
        area = Math.PI * radius * radius;
        System.out.println("Calculating area of circle");
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Circle with radius " + radius);
        super.displayInfo();  // Call parent method
    }
}

public class Rectangle extends Shape {
    private double length;
    private double width;
    
    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    
    @Override
    public void calculateArea() {
        area = length * width;
        System.out.println("Calculating area of rectangle");
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Rectangle with length " + length + " and width " + width);
        super.displayInfo();
    }
}
```

## 5. Polymorphism

Polymorphism allows objects to be treated as instances of their parent class while maintaining their own unique implementations.

### Method Overloading (Compile-time Polymorphism)

```java
public class Calculator {
    // Method overloading - same name, different parameters
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

### Method Overriding (Runtime Polymorphism)

```java
public class PolymorphismDemo {
    public static void main(String[] args) {
        // Runtime polymorphism
        Shape[] shapes = {
            new Circle(5.0),
            new Rectangle(4.0, 6.0),
            new Circle(3.0)
        };
        
        for (Shape shape : shapes) {
            shape.calculateArea();  // Calls appropriate method at runtime
            shape.displayInfo();
            System.out.println();
        }
        
        // Method overloading
        Calculator calc = new Calculator();
        System.out.println("add(5, 3): " + calc.add(5, 3));
        System.out.println("add(5.5, 3.2): " + calc.add(5.5, 3.2));
        System.out.println("add(1, 2, 3): " + calc.add(1, 2, 3));
        System.out.println("add(\"Hello, \", \"World!\"): " + calc.add("Hello, ", "World!"));
    }
}
```

## 6. Abstraction

Abstraction is the process of hiding complex implementation details and showing only the necessary features of an object.

### Abstract Classes

```java
// Abstract class - cannot be instantiated
public abstract class Vehicle {
    protected String brand;
    protected String model;
    protected int year;
    
    public Vehicle(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract void startEngine();
    
    // Concrete method - has implementation
    public void displayInfo() {
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
    }
    
    // Abstract method
    public abstract void stopEngine();
}

// Concrete class implementing abstract methods
public class Car extends Vehicle {
    private int numberOfDoors;
    
    public Car(String brand, String model, int year, int numberOfDoors) {
        super(brand, model, year);
        this.numberOfDoors = numberOfDoors;
    }
    
    @Override
    public void startEngine() {
        System.out.println(brand + " " + model + " car engine is starting");
    }
    
    @Override
    public void stopEngine() {
        System.out.println(brand + " " + model + " car engine is stopping");
    }
    
    public void openDoors() {
        System.out.println("Opening " + numberOfDoors + " doors");
    }
}

// Another concrete class
public class Motorcycle extends Vehicle {
    private boolean hasSidecar;
    
    public Motorcycle(String brand, String model, int year, boolean hasSidecar) {
        super(brand, model, year);
        this.hasSidecar = hasSidecar;
    }
    
    @Override
    public void startEngine() {
        System.out.println(brand + " " + model + " motorcycle engine is starting");
    }
    
    @Override
    public void stopEngine() {
        System.out.println(brand + " " + model + " motorcycle engine is stopping");
    }
    
    public void wheelie() {
        System.out.println("Performing a wheelie!");
    }
}
```

### Interfaces

Interfaces define a contract that implementing classes must follow. They provide a way to achieve multiple inheritance and define common behavior.

```java
// Interface defining common behavior
public interface Movable {
    void move();
    void stop();
    double getSpeed();
}

public interface Flyable {
    void takeOff();
    void land();
    void fly();
}

// Class implementing multiple interfaces
public class Airplane extends Vehicle implements Movable, Flyable {
    private double altitude;
    private double speed;
    
    public Airplane(String brand, String model, int year) {
        super(brand, model, year);
        this.altitude = 0;
        this.speed = 0;
    }
    
    @Override
    public void startEngine() {
        System.out.println(brand + " " + model + " airplane engines are starting");
    }
    
    @Override
    public void stopEngine() {
        System.out.println(brand + " " + model + " airplane engines are stopping");
    }
    
    // Movable interface methods
    @Override
    public void move() {
        System.out.println("Airplane is moving on runway");
        speed = 50;
    }
    
    @Override
    public void stop() {
        System.out.println("Airplane is stopping");
        speed = 0;
    }
    
    @Override
    public double getSpeed() {
        return speed;
    }
    
    // Flyable interface methods
    @Override
    public void takeOff() {
        System.out.println("Airplane is taking off");
        altitude = 1000;
        speed = 200;
    }
    
    @Override
    public void land() {
        System.out.println("Airplane is landing");
        altitude = 0;
        speed = 0;
    }
    
    @Override
    public void fly() {
        System.out.println("Airplane is flying at altitude " + altitude + " feet");
    }
}
```

## 7. Advanced OOP Concepts

### Final Keyword

```java
public class FinalDemo {
    // Final variable - cannot be changed after initialization
    public static final double PI = 3.14159;
    
    // Final method - cannot be overridden
    public final void importantMethod() {
        System.out.println("This method cannot be overridden");
    }
}

// Final class - cannot be inherited
public final class UtilityClass {
    public static void helperMethod() {
        System.out.println("Utility method");
    }
}
```

### Static Members

```java
public class Student {
    private String name;
    private int id;
    
    // Static variable - shared by all instances
    private static int totalStudents = 0;
    private static int nextId = 1000;
    
    public Student(String name) {
        this.name = name;
        this.id = nextId++;
        totalStudents++;
    }
    
    // Static method - can be called without creating an instance
    public static int getTotalStudents() {
        return totalStudents;
    }
    
    public static void resetCounter() {
        totalStudents = 0;
        nextId = 1000;
    }
    
    // Instance method
    public void displayInfo() {
        System.out.println("Student ID: " + id + ", Name: " + name);
    }
}
```

### Inner Classes

```java
public class OuterClass {
    private String outerField = "Outer field";
    
    // Non-static inner class
    public class InnerClass {
        private String innerField = "Inner field";
        
        public void display() {
            System.out.println("Outer field: " + outerField);
            System.out.println("Inner field: " + innerField);
        }
    }
    
    // Static inner class
    public static class StaticInnerClass {
        public void display() {
            System.out.println("Static inner class method");
        }
    }
    
    // Local inner class
    public void methodWithLocalClass() {
        class LocalClass {
            public void display() {
                System.out.println("Local class method");
            }
        }
        
        LocalClass local = new LocalClass();
        local.display();
    }
    
    // Anonymous inner class
    public void methodWithAnonymousClass() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous class running");
            }
        };
        
        runnable.run();
    }
}
```

## 8. Best Practices

### Design Principles

1. **Single Responsibility Principle**: A class should have only one reason to change
2. **Open/Closed Principle**: Open for extension, closed for modification
3. **Liskov Substitution Principle**: Subtypes must be substitutable for their base types
4. **Interface Segregation Principle**: Clients should not be forced to depend on interfaces they don't use
5. **Dependency Inversion Principle**: Depend on abstractions, not concretions

### Code Organization

```java
// Good class design
public class BankAccount {
    // 1. Constants first
    private static final double MINIMUM_BALANCE = 100.0;
    private static final double OVERDRAFT_FEE = 25.0;
    
    // 2. Instance variables
    private String accountNumber;
    private double balance;
    private String accountHolder;
    
    // 3. Constructors
    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        validateInitialBalance(initialBalance);
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }
    
    // 4. Public methods
    public void deposit(double amount) {
        validateAmount(amount);
        balance += amount;
        logTransaction("DEPOSIT", amount);
    }
    
    public boolean withdraw(double amount) {
        validateAmount(amount);
        if (canWithdraw(amount)) {
            balance -= amount;
            logTransaction("WITHDRAWAL", amount);
            return true;
        }
        return false;
    }
    
    // 5. Private helper methods
    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
    
    private void validateInitialBalance(double balance) {
        if (balance < MINIMUM_BALANCE) {
            throw new IllegalArgumentException("Initial balance must be at least $" + MINIMUM_BALANCE);
        }
    }
    
    private boolean canWithdraw(double amount) {
        return balance - amount >= MINIMUM_BALANCE;
    }
    
    private void logTransaction(String type, double amount) {
        System.out.println(type + ": $" + amount + " - New balance: $" + balance);
    }
    
    // 6. Getter methods
    public double getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolder() { return accountHolder; }
}
```

## Summary

This section covered the fundamental principles of Object-Oriented Programming in Java:

- **Classes and Objects**: Blueprints and instances
- **Encapsulation**: Data hiding and controlled access
- **Inheritance**: Code reuse and hierarchical relationships
- **Polymorphism**: Multiple forms and behaviors
- **Abstraction**: Hiding complexity and showing essential features
- **Advanced Concepts**: Final keyword, static members, inner classes
- **Best Practices**: Design principles and code organization

These concepts form the foundation for building robust, maintainable, and scalable Java applications. 