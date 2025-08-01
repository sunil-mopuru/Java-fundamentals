/**
 * Inheritance and Polymorphism Demo
 * This program demonstrates inheritance, method overriding, and polymorphism
 * using a hierarchy of geometric shapes.
 */

// Abstract base class for all shapes
abstract class Shape {
    protected String color;
    protected double area;
    protected double perimeter;
    
    public Shape(String color) {
        this.color = color;
        this.area = 0.0;
        this.perimeter = 0.0;
    }
    
    // Abstract methods that must be implemented by subclasses
    public abstract void calculateArea();
    public abstract void calculatePerimeter();
    
    // Concrete method that can be used by all subclasses
    public void displayInfo() {
        System.out.println("Shape: " + getClass().getSimpleName());
        System.out.println("Color: " + color);
        System.out.println("Area: " + String.format("%.2f", area));
        System.out.println("Perimeter: " + String.format("%.2f", perimeter));
    }
    
    // Getter methods
    public String getColor() { return color; }
    public double getArea() { return area; }
    public double getPerimeter() { return perimeter; }
    
    // Setter method
    public void setColor(String color) { this.color = color; }
}

// Concrete class for Circle
class Circle extends Shape {
    private double radius;
    
    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
        calculateArea();
        calculatePerimeter();
    }
    
    @Override
    public void calculateArea() {
        area = Math.PI * radius * radius;
    }
    
    @Override
    public void calculatePerimeter() {
        perimeter = 2 * Math.PI * radius;
    }
    
    public double getRadius() { return radius; }
    
    public void setRadius(double radius) {
        this.radius = radius;
        calculateArea();
        calculatePerimeter();
    }
    
    @Override
    public void displayInfo() {
        System.out.println("\n=== Circle Information ===");
        super.displayInfo();
        System.out.println("Radius: " + radius);
        System.out.println("Diameter: " + (2 * radius));
    }
}

// Concrete class for Rectangle
class Rectangle extends Shape {
    private double length;
    private double width;
    
    public Rectangle(String color, double length, double width) {
        super(color);
        this.length = length;
        this.width = width;
        calculateArea();
        calculatePerimeter();
    }
    
    @Override
    public void calculateArea() {
        area = length * width;
    }
    
    @Override
    public void calculatePerimeter() {
        perimeter = 2 * (length + width);
    }
    
    public double getLength() { return length; }
    public double getWidth() { return width; }
    
    public void setDimensions(double length, double width) {
        this.length = length;
        this.width = width;
        calculateArea();
        calculatePerimeter();
    }
    
    @Override
    public void displayInfo() {
        System.out.println("\n=== Rectangle Information ===");
        super.displayInfo();
        System.out.println("Length: " + length);
        System.out.println("Width: " + width);
        System.out.println("Is Square: " + (length == width));
    }
}

// Concrete class for Triangle
class Triangle extends Shape {
    private double sideA;
    private double sideB;
    private double sideC;
    
    public Triangle(String color, double sideA, double sideB, double sideC) {
        super(color);
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
        calculateArea();
        calculatePerimeter();
    }
    
    @Override
    public void calculateArea() {
        // Using Heron's formula
        double s = (sideA + sideB + sideC) / 2;
        area = Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }
    
    @Override
    public void calculatePerimeter() {
        perimeter = sideA + sideB + sideC;
    }
    
    public boolean isEquilateral() {
        return sideA == sideB && sideB == sideC;
    }
    
    public boolean isIsosceles() {
        return sideA == sideB || sideB == sideC || sideA == sideC;
    }
    
    public boolean isScalene() {
        return sideA != sideB && sideB != sideC && sideA != sideC;
    }
    
    public double getSideA() { return sideA; }
    public double getSideB() { return sideB; }
    public double getSideC() { return sideC; }
    
    @Override
    public void displayInfo() {
        System.out.println("\n=== Triangle Information ===");
        super.displayInfo();
        System.out.println("Side A: " + sideA);
        System.out.println("Side B: " + sideB);
        System.out.println("Side C: " + sideC);
        System.out.println("Type: " + getTriangleType());
    }
    
    private String getTriangleType() {
        if (isEquilateral()) return "Equilateral";
        if (isIsosceles()) return "Isosceles";
        return "Scalene";
    }
}

// Interface for shapes that can be resized
interface Resizable {
    void resize(double factor);
}

// Interface for shapes that can be moved
interface Movable {
    void move(double deltaX, double deltaY);
}

// Advanced shape class implementing interfaces
class AdvancedRectangle extends Rectangle implements Resizable, Movable {
    private double x, y; // Position coordinates
    
    public AdvancedRectangle(String color, double length, double width, double x, double y) {
        super(color, length, width);
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void resize(double factor) {
        if (factor > 0) {
            setDimensions(getLength() * factor, getWidth() * factor);
            System.out.println("Rectangle resized by factor: " + factor);
        } else {
            System.out.println("Error: Resize factor must be positive");
        }
    }
    
    @Override
    public void move(double deltaX, double deltaY) {
        x += deltaX;
        y += deltaY;
        System.out.println("Rectangle moved to position: (" + x + ", " + y + ")");
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
    
    @Override
    public void displayInfo() {
        System.out.println("\n=== Advanced Rectangle Information ===");
        super.displayInfo();
        System.out.println("Position: (" + x + ", " + y + ")");
    }
}

// Main demonstration class
public class InheritanceDemo {
    public static void main(String[] args) {
        System.out.println("=== Inheritance and Polymorphism Demo ===\n");
        
        // 1. Creating different shapes
        System.out.println("1. CREATING SHAPES:");
        System.out.println("===================");
        
        Circle circle = new Circle("Red", 5.0);
        Rectangle rectangle = new Rectangle("Blue", 4.0, 6.0);
        Triangle triangle = new Triangle("Green", 3.0, 4.0, 5.0);
        AdvancedRectangle advancedRect = new AdvancedRectangle("Purple", 2.0, 3.0, 10.0, 20.0);
        
        // 2. Demonstrating inheritance
        System.out.println("\n2. INHERITANCE DEMONSTRATION:");
        System.out.println("=============================");
        
        circle.displayInfo();
        rectangle.displayInfo();
        triangle.displayInfo();
        advancedRect.displayInfo();
        
        // 3. Demonstrating polymorphism
        System.out.println("\n3. POLYMORPHISM DEMONSTRATION:");
        System.out.println("==============================");
        
        Shape[] shapes = {circle, rectangle, triangle, advancedRect};
        
        System.out.println("Processing all shapes polymorphically:");
        for (Shape shape : shapes) {
            System.out.println("\nProcessing " + shape.getClass().getSimpleName() + ":");
            shape.calculateArea();  // Calls appropriate method for each shape
            shape.calculatePerimeter();
            System.out.println("Updated area: " + String.format("%.2f", shape.getArea()));
            System.out.println("Updated perimeter: " + String.format("%.2f", shape.getPerimeter()));
        }
        
        // 4. Demonstrating interface usage
        System.out.println("\n4. INTERFACE DEMONSTRATION:");
        System.out.println("===========================");
        
        Resizable resizable = advancedRect;
        Movable movable = advancedRect;
        
        System.out.println("Before operations:");
        advancedRect.displayInfo();
        
        resizable.resize(2.0);  // Double the size
        movable.move(5.0, 10.0);  // Move the rectangle
        
        System.out.println("\nAfter operations:");
        advancedRect.displayInfo();
        
        // 5. Demonstrating method overloading
        System.out.println("\n5. METHOD OVERLOADING DEMONSTRATION:");
        System.out.println("====================================");
        
        processShape(circle);
        processShape(rectangle);
        processShape(triangle);
        processShape(advancedRect);
        
        // 6. Demonstrating instanceof operator
        System.out.println("\n6. INSTANCEOF DEMONSTRATION:");
        System.out.println("============================");
        
        for (Shape shape : shapes) {
            System.out.println(shape.getClass().getSimpleName() + ":");
            System.out.println("  Is Shape: " + (shape instanceof Shape));
            System.out.println("  Is Circle: " + (shape instanceof Circle));
            System.out.println("  Is Rectangle: " + (shape instanceof Rectangle));
            System.out.println("  Is Triangle: " + (shape instanceof Triangle));
            System.out.println("  Is Resizable: " + (shape instanceof Resizable));
            System.out.println("  Is Movable: " + (shape instanceof Movable));
        }
        
        // 7. Demonstrating dynamic method dispatch
        System.out.println("\n7. DYNAMIC METHOD DISPATCH:");
        System.out.println("===========================");
        
        System.out.println("Calling calculateArea() on different shapes:");
        for (Shape shape : shapes) {
            System.out.print(shape.getClass().getSimpleName() + " area calculation: ");
            shape.calculateArea();
            System.out.println("Result: " + String.format("%.2f", shape.getArea()));
        }
        
        System.out.println("\n=== End of Inheritance and Polymorphism Demo ===");
    }
    
    // Method overloading demonstration
    public static void processShape(Circle circle) {
        System.out.println("Processing Circle with radius: " + circle.getRadius());
    }
    
    public static void processShape(Rectangle rectangle) {
        System.out.println("Processing Rectangle with dimensions: " + 
                          rectangle.getLength() + " x " + rectangle.getWidth());
    }
    
    public static void processShape(Triangle triangle) {
        System.out.println("Processing Triangle with sides: " + 
                          triangle.getSideA() + ", " + triangle.getSideB() + ", " + triangle.getSideC());
    }
    
    public static void processShape(AdvancedRectangle advancedRect) {
        System.out.println("Processing AdvancedRectangle at position: (" + 
                          advancedRect.getX() + ", " + advancedRect.getY() + ")");
    }
} 