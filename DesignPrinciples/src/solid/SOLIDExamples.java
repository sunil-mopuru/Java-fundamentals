package solid;

import java.util.*;

/**
 * Practical examples demonstrating SOLID principles
 */
public class SOLIDExamples {
    
    public static void main(String[] args) {
        System.out.println("=== SOLID Principles Examples ===\n");
        
        // Single Responsibility Principle
        demonstrateSRP();
        
        // Open/Closed Principle
        demonstrateOCP();
        
        // Liskov Substitution Principle
        demonstrateLSP();
        
        // Interface Segregation Principle
        demonstrateISP();
        
        // Dependency Inversion Principle
        demonstrateDIP();
    }
    
    // ===== SINGLE RESPONSIBILITY PRINCIPLE =====
    
    private static void demonstrateSRP() {
        System.out.println("1. Single Responsibility Principle (SRP)");
        System.out.println("=========================================");
        
        // Good: Each class has a single responsibility
        UserValidator validator = new UserValidator();
        UserRepository repository = new UserRepository();
        EmailService emailService = new EmailService();
        
        UserService userService = new UserService(validator, repository, emailService);
        userService.createUser("john", "john@example.com");
        
        System.out.println();
    }
    
    // ===== OPEN/CLOSED PRINCIPLE =====
    
    private static void demonstrateOCP() {
        System.out.println("2. Open/Closed Principle (OCP)");
        System.out.println("===============================");
        
        List<Shape> shapes = Arrays.asList(
            new Rectangle(5, 4),
            new Circle(3),
            new Triangle(6, 8) // New shape without modifying existing code
        );
        
        AreaCalculator calculator = new AreaCalculator();
        for (Shape shape : shapes) {
            System.out.println("Area of " + shape.getClass().getSimpleName() + ": " + calculator.calculateArea(shape));
        }
        
        System.out.println();
    }
    
    // ===== LISKOV SUBSTITUTION PRINCIPLE =====
    
    private static void demonstrateLSP() {
        System.out.println("3. Liskov Substitution Principle (LSP)");
        System.out.println("=======================================");
        
        // Both Rectangle and Square can be used interchangeably
        List<Shape> shapes = Arrays.asList(
            new Rectangle(5, 4),
            new Square(5)
        );
        
        AreaCalculator calculator = new AreaCalculator();
        for (Shape shape : shapes) {
            System.out.println("Area of " + shape.getClass().getSimpleName() + ": " + calculator.calculateArea(shape));
        }
        
        System.out.println();
    }
    
    // ===== INTERFACE SEGREGATION PRINCIPLE =====
    
    private static void demonstrateISP() {
        System.out.println("4. Interface Segregation Principle (ISP)");
        System.out.println("=========================================");
        
        // Human can work, eat, and sleep
        Human human = new Human();
        human.work();
        human.eat();
        human.sleep();
        
        // Robot can only work
        Robot robot = new Robot();
        robot.work();
        
        System.out.println();
    }
    
    // ===== DEPENDENCY INVERSION PRINCIPLE =====
    
    private static void demonstrateDIP() {
        System.out.println("5. Dependency Inversion Principle (DIP)");
        System.out.println("========================================");
        
        // Can easily switch between different databases
        Database mysqlDb = new MySQLDatabase();
        Database postgresDb = new PostgreSQLDatabase();
        
        ReportGenerator mysqlReport = new ReportGenerator(mysqlDb);
        ReportGenerator postgresReport = new ReportGenerator(postgresDb);
        
        mysqlReport.generateReport();
        postgresReport.generateReport();
        
        System.out.println();
    }
}

// ===== SRP IMPLEMENTATIONS =====

class User {
    private String username;
    private String email;
    
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
    
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}

class UserValidator {
    public boolean isValidUsername(String username) {
        return username != null && username.length() >= 3;
    }
    
    public boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }
}

class UserRepository {
    private List<User> users = new ArrayList<>();
    
    public void save(User user) {
        users.add(user);
        System.out.println("User saved: " + user.getUsername());
    }
}

class EmailService {
    public void sendWelcomeEmail(String email) {
        System.out.println("Welcome email sent to: " + email);
    }
}

class UserService {
    private UserValidator validator;
    private UserRepository repository;
    private EmailService emailService;
    
    public UserService(UserValidator validator, UserRepository repository, EmailService emailService) {
        this.validator = validator;
        this.repository = repository;
        this.emailService = emailService;
    }
    
    public void createUser(String username, String email) {
        if (validator.isValidUsername(username) && validator.isValidEmail(email)) {
            User user = new User(username, email);
            repository.save(user);
            emailService.sendWelcomeEmail(email);
        } else {
            System.out.println("Invalid user data");
        }
    }
}

// ===== OCP IMPLEMENTATIONS =====

interface Shape {
    double calculateArea();
}

class Rectangle implements Shape {
    private double width;
    private double height;
    
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return width * height;
    }
}

class Circle implements Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class Triangle implements Shape {
    private double base;
    private double height;
    
    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }
}

class AreaCalculator {
    public double calculateArea(Shape shape) {
        return shape.calculateArea();
    }
}

// ===== LSP IMPLEMENTATIONS =====

class Square implements Shape {
    private double side;
    
    public Square(double side) {
        this.side = side;
    }
    
    @Override
    public double calculateArea() {
        return side * side;
    }
}

// ===== ISP IMPLEMENTATIONS =====

interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

interface Sleepable {
    void sleep();
}

class Human implements Workable, Eatable, Sleepable {
    @Override
    public void work() {
        System.out.println("Human is working");
    }
    
    @Override
    public void eat() {
        System.out.println("Human is eating");
    }
    
    @Override
    public void sleep() {
        System.out.println("Human is sleeping");
    }
}

class Robot implements Workable {
    @Override
    public void work() {
        System.out.println("Robot is working");
    }
}

// ===== DIP IMPLEMENTATIONS =====

interface Database {
    String getData();
}

class MySQLDatabase implements Database {
    @Override
    public String getData() {
        return "Data from MySQL database";
    }
}

class PostgreSQLDatabase implements Database {
    @Override
    public String getData() {
        return "Data from PostgreSQL database";
    }
}

class ReportGenerator {
    private Database database;
    
    public ReportGenerator(Database database) {
        this.database = database;
    }
    
    public void generateReport() {
        String data = database.getData();
        System.out.println("Generating report with: " + data);
    }
} 