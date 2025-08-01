# Java Variables Deep Dive: Local, Static, Instance Variables, Static Blocks, Constructor Blocks, and this & super Keywords

## Table of Contents
1. [Variable Types in Java](#variable-types-in-java)
2. [Local Variables](#local-variables)
3. [Instance Variables](#instance-variables)
4. [Static Variables](#static-variables)
5. [Static Blocks](#static-blocks)
6. [Instance Initialization Blocks (Constructor Blocks)](#instance-initialization-blocks-constructor-blocks)
7. [The `this` Keyword](#the-this-keyword)
8. [The `super` Keyword](#the-super-keyword)
9. [Variable Shadowing and Hiding](#variable-shadowing-and-hiding)
10. [Best Practices](#best-practices)
11. [Memory Management Considerations](#memory-management-considerations)

## Variable Types in Java

Java has three main types of variables based on their scope and lifecycle:

1. **Local Variables**: Declared inside methods, constructors, or blocks
2. **Instance Variables**: Declared inside a class but outside any method (non-static)
3. **Static Variables**: Declared inside a class but outside any method with `static` keyword

## Local Variables

### Characteristics
- Declared inside methods, constructors, or blocks
- Have method/block scope
- Must be initialized before use
- Are stored in the stack memory
- Cannot have access modifiers
- Cannot be declared as `static`

### Examples

```java
public class LocalVariableExample {
    
    public void demonstrateLocalVariables() {
        // Local variable declaration and initialization
        int localVar = 10;
        String message = "Hello World";
        
        // Local variable in a block
        {
            int blockVar = 20;
            System.out.println("Block variable: " + blockVar);
            System.out.println("Accessing outer local variable: " + localVar);
        }
        // blockVar is not accessible here - compilation error
        
        // Local variable in for loop
        for (int i = 0; i < 5; i++) {
            int loopVar = i * 2;
            System.out.println("Loop variable: " + loopVar);
        }
        // i and loopVar are not accessible here
        
        // Local variable shadowing
        int shadowedVar = 100;
        {
            int shadowedVar = 200; // Shadows the outer variable
            System.out.println("Inner shadowedVar: " + shadowedVar);
        }
        System.out.println("Outer shadowedVar: " + shadowedVar);
    }
    
    public void demonstrateLocalVariableRules() {
        // Rule 1: Must be initialized before use
        int uninitializedVar;
        // System.out.println(uninitializedVar); // Compilation error
        
        // Rule 2: Cannot have access modifiers
        // public int publicLocalVar = 10; // Compilation error
        // private int privateLocalVar = 10; // Compilation error
        
        // Rule 3: Cannot be declared as static
        // static int staticLocalVar = 10; // Compilation error
        
        // Rule 4: Can be final
        final int finalLocalVar = 50;
        // finalLocalVar = 60; // Compilation error - cannot reassign final variable
    }
}
```

## Instance Variables

### Characteristics
- Declared inside a class but outside any method
- Belong to individual objects (instances)
- Have default values if not initialized
- Are stored in the heap memory
- Can have access modifiers
- Cannot be declared as `static`

### Examples

```java
public class InstanceVariableExample {
    
    // Instance variables with different access modifiers
    public String publicVar = "Public Instance Variable";
    private int privateVar = 100;
    protected double protectedVar = 3.14;
    String defaultVar = "Default Access Instance Variable";
    
    // Instance variables with default values
    private int uninitializedInt;        // Default: 0
    private double uninitializedDouble;  // Default: 0.0
    private boolean uninitializedBoolean; // Default: false
    private String uninitializedString;  // Default: null
    private Object uninitializedObject;  // Default: null
    
    // Final instance variable
    private final String finalInstanceVar = "Cannot be changed";
    
    // Instance variable with complex initialization
    private List<String> stringList = new ArrayList<>();
    
    public void demonstrateInstanceVariables() {
        System.out.println("=== Instance Variables ===");
        System.out.println("Public variable: " + publicVar);
        System.out.println("Private variable: " + privateVar);
        System.out.println("Protected variable: " + protectedVar);
        System.out.println("Default variable: " + defaultVar);
        
        System.out.println("\nDefault values:");
        System.out.println("uninitializedInt: " + uninitializedInt);
        System.out.println("uninitializedDouble: " + uninitializedDouble);
        System.out.println("uninitializedBoolean: " + uninitializedBoolean);
        System.out.println("uninitializedString: " + uninitializedString);
        System.out.println("uninitializedObject: " + uninitializedObject);
        
        System.out.println("\nFinal instance variable: " + finalInstanceVar);
        // finalInstanceVar = "New value"; // Compilation error
        
        // Modifying instance variables
        privateVar = 200;
        protectedVar = 2.718;
        stringList.add("First item");
        
        System.out.println("\nAfter modification:");
        System.out.println("privateVar: " + privateVar);
        System.out.println("protectedVar: " + protectedVar);
        System.out.println("stringList: " + stringList);
    }
    
    public void demonstrateInstanceVariableScope() {
        // Local variable with same name as instance variable
        String publicVar = "Local Variable"; // Shadows instance variable
        System.out.println("Local publicVar: " + publicVar);
        System.out.println("Instance publicVar: " + this.publicVar); // Using 'this' to access instance variable
    }
}
```

## Static Variables

### Characteristics
- Declared inside a class but outside any method with `static` keyword
- Belong to the class, not individual objects
- Shared among all instances of the class
- Have default values if not initialized
- Are stored in the method area (part of heap)
- Can have access modifiers
- Can be accessed without creating an object

### Examples

```java
public class StaticVariableExample {
    
    // Static variables with different access modifiers
    public static String publicStaticVar = "Public Static Variable";
    private static int privateStaticVar = 1000;
    protected static double protectedStaticVar = 2.718;
    static String defaultStaticVar = "Default Access Static Variable";
    
    // Static variables with default values
    private static int uninitializedStaticInt;        // Default: 0
    private static double uninitializedStaticDouble;  // Default: 0.0
    private static boolean uninitializedStaticBoolean; // Default: false
    private static String uninitializedStaticString;  // Default: null
    
    // Final static variables (constants)
    public static final double PI = 3.14159;
    public static final int MAX_SIZE = 1000;
    
    // Static variable for counting instances
    private static int instanceCount = 0;
    
    // Instance variable for comparison
    private int instanceVar = 500;
    
    public StaticVariableExample() {
        instanceCount++; // Increment static variable in constructor
    }
    
    public void demonstrateStaticVariables() {
        System.out.println("=== Static Variables ===");
        System.out.println("Public static variable: " + publicStaticVar);
        System.out.println("Private static variable: " + privateStaticVar);
        System.out.println("Protected static variable: " + protectedStaticVar);
        System.out.println("Default static variable: " + defaultStaticVar);
        
        System.out.println("\nDefault values for static variables:");
        System.out.println("uninitializedStaticInt: " + uninitializedStaticInt);
        System.out.println("uninitializedStaticDouble: " + uninitializedStaticDouble);
        System.out.println("uninitializedStaticBoolean: " + uninitializedStaticBoolean);
        System.out.println("uninitializedStaticString: " + uninitializedStaticString);
        
        System.out.println("\nConstants:");
        System.out.println("PI: " + PI);
        System.out.println("MAX_SIZE: " + MAX_SIZE);
        
        System.out.println("\nInstance count: " + instanceCount);
        System.out.println("Instance variable: " + instanceVar);
    }
    
    public static void demonstrateStaticVariableAccess() {
        System.out.println("=== Static Variable Access ===");
        
        // Accessing static variables from static method
        System.out.println("PI: " + PI);
        System.out.println("publicStaticVar: " + publicStaticVar);
        
        // Cannot access instance variables from static method
        // System.out.println(instanceVar); // Compilation error
        
        // Modifying static variables
        privateStaticVar = 2000;
        protectedStaticVar = 1.618;
        
        System.out.println("\nAfter modification:");
        System.out.println("privateStaticVar: " + privateStaticVar);
        System.out.println("protectedStaticVar: " + protectedStaticVar);
    }
    
    public void demonstrateStaticVsInstance() {
        System.out.println("=== Static vs Instance Variables ===");
        
        // Creating multiple instances
        StaticVariableExample obj1 = new StaticVariableExample();
        StaticVariableExample obj2 = new StaticVariableExample();
        StaticVariableExample obj3 = new StaticVariableExample();
        
        System.out.println("Instance count: " + instanceCount); // Same for all objects
        
        // Modifying instance variable
        obj1.instanceVar = 1000;
        obj2.instanceVar = 2000;
        obj3.instanceVar = 3000;
        
        System.out.println("obj1.instanceVar: " + obj1.instanceVar);
        System.out.println("obj2.instanceVar: " + obj2.instanceVar);
        System.out.println("obj3.instanceVar: " + obj3.instanceVar);
        
        // Modifying static variable affects all instances
        privateStaticVar = 9999;
        System.out.println("\nAfter modifying static variable:");
        System.out.println("obj1.privateStaticVar: " + obj1.privateStaticVar);
        System.out.println("obj2.privateStaticVar: " + obj2.privateStaticVar);
        System.out.println("obj3.privateStaticVar: " + obj3.privateStaticVar);
    }
}
```

## Static Blocks

### Characteristics
- Executed when the class is loaded into memory
- Used for static initialization
- Can only access static members
- Executed only once per class
- Cannot access instance members
- Can have multiple static blocks

### Examples

```java
public class StaticBlockExample {
    
    // Static variables
    private static int staticVar1;
    private static int staticVar2;
    private static String staticString;
    private static List<String> staticList;
    
    // Static block 1
    static {
        System.out.println("Static block 1 executed");
        staticVar1 = 100;
        staticString = "Initialized in static block";
    }
    
    // Static block 2
    static {
        System.out.println("Static block 2 executed");
        staticVar2 = 200;
        staticList = new ArrayList<>();
        staticList.add("Item 1");
        staticList.add("Item 2");
    }
    
    // Static block with complex initialization
    static {
        System.out.println("Static block 3 executed");
        try {
            // Simulating some initialization that might throw exception
            if (staticVar1 > 50) {
                staticString += " - Enhanced";
            }
        } catch (Exception e) {
            System.err.println("Error in static block: " + e.getMessage());
        }
    }
    
    // Instance variable for comparison
    private int instanceVar = 500;
    
    public StaticBlockExample() {
        System.out.println("Constructor executed");
    }
    
    public static void demonstrateStaticBlocks() {
        System.out.println("=== Static Blocks Demonstration ===");
        System.out.println("staticVar1: " + staticVar1);
        System.out.println("staticVar2: " + staticVar2);
        System.out.println("staticString: " + staticString);
        System.out.println("staticList: " + staticList);
    }
    
    public void demonstrateStaticBlockLimitations() {
        System.out.println("=== Static Block Limitations ===");
        
        // Static blocks can access static members
        System.out.println("Accessing static members: " + staticVar1);
        
        // Static blocks cannot access instance members
        // System.out.println(instanceVar); // This would be an error in static block
    }
    
    public static void main(String[] args) {
        System.out.println("=== Static Block Execution Order ===");
        
        // Static blocks execute when class is loaded
        System.out.println("About to access static variable...");
        int value = staticVar1; // This triggers class loading and static block execution
        
        System.out.println("\nCreating instances...");
        StaticBlockExample obj1 = new StaticBlockExample();
        StaticBlockExample obj2 = new StaticBlockExample();
        
        // Static blocks are executed only once
        demonstrateStaticBlocks();
    }
}
```

## Instance Initialization Blocks (Constructor Blocks)

### Characteristics
- Executed every time an object is created
- Executed before the constructor
- Can access both static and instance members
- Can have multiple instance blocks
- Useful for common initialization code

### Examples

```java
public class InstanceBlockExample {
    
    // Instance variables
    private int instanceVar1;
    private int instanceVar2;
    private String instanceString;
    private List<String> instanceList;
    
    // Static variable for comparison
    private static int staticVar = 1000;
    
    // Instance initialization block 1
    {
        System.out.println("Instance block 1 executed");
        instanceVar1 = 10;
        instanceString = "Initialized in instance block";
    }
    
    // Instance initialization block 2
    {
        System.out.println("Instance block 2 executed");
        instanceVar2 = 20;
        instanceList = new ArrayList<>();
        instanceList.add("Instance Item 1");
        instanceList.add("Instance Item 2");
    }
    
    // Instance block with complex initialization
    {
        System.out.println("Instance block 3 executed");
        if (instanceVar1 > 5) {
            instanceString += " - Enhanced";
        }
        // Can access static members
        staticVar++;
    }
    
    // Default constructor
    public InstanceBlockExample() {
        System.out.println("Default constructor executed");
        instanceVar1 += 5; // Can modify instance variables in constructor
    }
    
    // Parameterized constructor
    public InstanceBlockExample(int value) {
        System.out.println("Parameterized constructor executed with value: " + value);
        this.instanceVar1 = value;
    }
    
    public void demonstrateInstanceBlocks() {
        System.out.println("=== Instance Blocks Demonstration ===");
        System.out.println("instanceVar1: " + instanceVar1);
        System.out.println("instanceVar2: " + instanceVar2);
        System.out.println("instanceString: " + instanceString);
        System.out.println("instanceList: " + instanceList);
        System.out.println("staticVar: " + staticVar);
    }
    
    public static void demonstrateExecutionOrder() {
        System.out.println("=== Instance Block Execution Order ===");
        
        System.out.println("Creating object 1:");
        InstanceBlockExample obj1 = new InstanceBlockExample();
        obj1.demonstrateInstanceBlocks();
        
        System.out.println("\nCreating object 2:");
        InstanceBlockExample obj2 = new InstanceBlockExample(100);
        obj2.demonstrateInstanceBlocks();
    }
}
```

## The `this` Keyword

### Characteristics
- Refers to the current object instance
- Used to distinguish between instance variables and parameters
- Used to call other constructors (constructor chaining)
- Used to pass current object reference to other methods
- Cannot be used in static methods

### Examples

```java
public class ThisKeywordExample {
    
    // Instance variables
    private String name;
    private int age;
    private double salary;
    private String department;
    
    // Constructor using 'this' to distinguish parameters from instance variables
    public ThisKeywordExample(String name, int age, double salary) {
        this.name = name;      // 'this.name' refers to instance variable
        this.age = age;        // 'age' refers to parameter
        this.salary = salary;
        this.department = "Unknown"; // Default value
    }
    
    // Constructor chaining using 'this()'
    public ThisKeywordExample(String name, int age, double salary, String department) {
        this(name, age, salary); // Calls the 3-parameter constructor
        this.department = department;
    }
    
    // Method using 'this' to call another method
    public void displayInfo() {
        System.out.println("=== Employee Information ===");
        this.displayBasicInfo(); // Using 'this' to call another method
        this.displayDepartmentInfo();
    }
    
    private void displayBasicInfo() {
        System.out.println("Name: " + this.name);
        System.out.println("Age: " + this.age);
        System.out.println("Salary: " + this.salary);
    }
    
    private void displayDepartmentInfo() {
        System.out.println("Department: " + this.department);
    }
    
    // Method that returns current object reference
    public ThisKeywordExample updateSalary(double newSalary) {
        this.salary = newSalary;
        return this; // Returns current object for method chaining
    }
    
    public ThisKeywordExample updateDepartment(String newDepartment) {
        this.department = newDepartment;
        return this; // Returns current object for method chaining
    }
    
    // Method demonstrating 'this' in nested classes
    public void demonstrateNestedThis() {
        System.out.println("Outer class name: " + this.name);
        
        // Local class
        class LocalClass {
            private String localName = "Local";
            
            public void displayNames() {
                System.out.println("Local name: " + this.localName);
                System.out.println("Outer name: " + ThisKeywordExample.this.name); // Accessing outer 'this'
            }
        }
        
        LocalClass local = new LocalClass();
        local.displayNames();
    }
    
    // Static method - cannot use 'this'
    public static void staticMethod() {
        // this.name = "Test"; // Compilation error - 'this' not available in static context
        System.out.println("Static method - no 'this' available");
    }
    
    public void demonstrateThisUsage() {
        System.out.println("=== 'this' Keyword Demonstration ===");
        
        // Method chaining using 'this'
        this.updateSalary(75000.0)
            .updateDepartment("Engineering")
            .displayInfo();
        
        System.out.println("\nDemonstrating nested 'this':");
        this.demonstrateNestedThis();
    }
}
```

## The `super` Keyword

### Characteristics
- Refers to the parent class object
- Used to call parent class constructors
- Used to access parent class methods and variables
- Cannot be used in static methods
- Used to distinguish between parent and child class members

### Examples

```java
// Parent class
class ParentClass {
    
    // Parent instance variables
    protected String name;
    protected int age;
    private String privateField = "Parent Private Field";
    
    // Parent constructor
    public ParentClass(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("Parent constructor called with: " + name + ", " + age);
    }
    
    // Parent method
    public void displayInfo() {
        System.out.println("Parent - Name: " + name + ", Age: " + age);
    }
    
    // Parent method that can be overridden
    public void showDetails() {
        System.out.println("Parent showDetails method");
    }
    
    // Private method - cannot be accessed by child
    private void privateMethod() {
        System.out.println("Parent private method");
    }
    
    // Protected method - can be accessed by child
    protected void protectedMethod() {
        System.out.println("Parent protected method");
    }
}

// Child class
class ChildClass extends ParentClass {
    
    // Child instance variables
    private String childField = "Child Field";
    private int childAge;
    
    // Child constructor using 'super()'
    public ChildClass(String name, int age, int childAge) {
        super(name, age); // Calls parent constructor
        this.childAge = childAge;
        System.out.println("Child constructor called with childAge: " + childAge);
    }
    
    // Child constructor with default parent values
    public ChildClass(String name) {
        super(name, 0); // Calls parent constructor with default age
        this.childAge = 0;
        System.out.println("Child constructor with default values");
    }
    
    // Overriding parent method
    @Override
    public void displayInfo() {
        super.displayInfo(); // Calls parent method
        System.out.println("Child - Child Age: " + childAge);
        System.out.println("Child Field: " + childField);
    }
    
    // Overriding parent method with different behavior
    @Override
    public void showDetails() {
        System.out.println("Child showDetails method");
        super.showDetails(); // Calls parent method
    }
    
    // Method demonstrating 'super' usage
    public void demonstrateSuper() {
        System.out.println("=== 'super' Keyword Demonstration ===");
        
        // Accessing parent instance variables
        System.out.println("Parent name (via super): " + super.name);
        System.out.println("Parent age (via super): " + super.age);
        
        // Accessing child instance variables
        System.out.println("Child field: " + this.childField);
        System.out.println("Child age: " + this.childAge);
        
        // Calling parent methods
        System.out.println("\nCalling parent methods:");
        super.displayInfo();
        super.showDetails();
        super.protectedMethod();
        
        // Cannot access parent private members
        // super.privateField; // Compilation error
        // super.privateMethod(); // Compilation error
        
        // Calling child methods
        System.out.println("\nCalling child methods:");
        this.displayInfo();
        this.showDetails();
    }
}

// Grandchild class demonstrating multi-level inheritance
class GrandchildClass extends ChildClass {
    
    private String grandchildField = "Grandchild Field";
    
    public GrandchildClass(String name, int age, int childAge) {
        super(name, age, childAge); // Calls child constructor
        System.out.println("Grandchild constructor called");
    }
    
    public void demonstrateMultiLevelSuper() {
        System.out.println("=== Multi-level 'super' Demonstration ===");
        
        // Accessing grandparent (ParentClass) members
        System.out.println("Grandparent name: " + super.name);
        System.out.println("Grandparent age: " + super.age);
        
        // Accessing parent (ChildClass) members
        // Note: We can't directly access childField as it's private
        // But we can access it through methods
        
        // Accessing current class members
        System.out.println("Grandchild field: " + this.grandchildField);
        
        // Calling methods from different levels
        System.out.println("\nCalling grandparent method:");
        super.displayInfo(); // Calls ParentClass.displayInfo()
        
        System.out.println("\nCalling parent method:");
        super.showDetails(); // Calls ChildClass.showDetails()
    }
}

public class SuperKeywordExample {
    
    public static void demonstrateSuperKeyword() {
        System.out.println("=== 'super' Keyword Examples ===");
        
        // Creating child object
        ChildClass child = new ChildClass("John", 30, 5);
        child.demonstrateSuper();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Creating grandchild object
        GrandchildClass grandchild = new GrandchildClass("Alice", 25, 3);
        grandchild.demonstrateMultiLevelSuper();
    }
}
```

## Variable Shadowing and Hiding

### Variable Shadowing (Local Variables)
- Local variables can shadow instance variables
- Use `this` keyword to access the instance variable

### Variable Hiding (Static Variables)
- Static variables in child class hide static variables in parent class
- Use class name or `super` to access parent static variables

### Examples

```java
class ShadowingExample {
    
    // Instance variables
    private String name = "Instance Name";
    private int value = 100;
    
    // Static variables
    public static String staticName = "Parent Static Name";
    public static int staticValue = 200;
    
    public void demonstrateShadowing() {
        System.out.println("=== Variable Shadowing ===");
        
        // Local variables shadowing instance variables
        String name = "Local Name";
        int value = 50;
        
        System.out.println("Local name: " + name);
        System.out.println("Local value: " + value);
        
        System.out.println("Instance name (via this): " + this.name);
        System.out.println("Instance value (via this): " + this.value);
    }
}

class HidingExample extends ShadowingExample {
    
    // Static variables hiding parent static variables
    public static String staticName = "Child Static Name";
    public static int staticValue = 300;
    
    public void demonstrateHiding() {
        System.out.println("=== Variable Hiding ===");
        
        // Accessing child static variables
        System.out.println("Child staticName: " + staticName);
        System.out.println("Child staticValue: " + staticValue);
        
        // Accessing parent static variables
        System.out.println("Parent staticName (via super): " + super.staticName);
        System.out.println("Parent staticValue (via super): " + super.staticValue);
        
        // Accessing parent static variables via class name
        System.out.println("Parent staticName (via class): " + ShadowingExample.staticName);
        System.out.println("Parent staticValue (via class): " + ShadowingExample.staticValue);
    }
}
```

## Best Practices

### 1. Variable Naming and Organization
```java
public class BestPracticesExample {
    
    // Constants at the top
    public static final double PI = 3.14159;
    public static final int MAX_SIZE = 1000;
    
    // Static variables
    private static int instanceCount = 0;
    
    // Instance variables grouped by purpose
    // Personal information
    private String firstName;
    private String lastName;
    private int age;
    
    // Contact information
    private String email;
    private String phone;
    
    // Business information
    private String department;
    private double salary;
    
    // Constructor
    public BestPracticesExample(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        instanceCount++;
    }
    
    // Methods
    public void updateContactInfo(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }
    
    public void updateBusinessInfo(String department, double salary) {
        this.department = department;
        this.salary = salary;
    }
}
```

### 2. Initialization Best Practices
```java
public class InitializationBestPractices {
    
    // Initialize at declaration when possible
    private String name = "Default Name";
    private List<String> items = new ArrayList<>();
    
    // Use static blocks for complex static initialization
    private static Map<String, Integer> configuration;
    
    static {
        configuration = new HashMap<>();
        configuration.put("timeout", 30);
        configuration.put("retries", 3);
        configuration.put("maxConnections", 100);
    }
    
    // Use instance blocks for common instance initialization
    private int counter;
    private boolean initialized;
    
    {
        counter = 0;
        initialized = false;
    }
    
    // Use constructors for parameter-based initialization
    public InitializationBestPractices(String name) {
        this.name = name;
        this.initialized = true;
    }
}
```

### 3. Access Modifier Best Practices
```java
public class AccessModifierBestPractices {
    
    // Public constants
    public static final String DEFAULT_NAME = "Unknown";
    
    // Private instance variables (encapsulation)
    private String name;
    private int age;
    
    // Protected variables for inheritance
    protected String department;
    
    // Package-private for internal use
    String internalId;
    
    // Public getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
```

## Memory Management Considerations

### 1. Variable Lifecycle
```java
public class MemoryManagementExample {
    
    // Static variables - live for entire application lifecycle
    private static List<String> globalCache = new ArrayList<>();
    
    // Instance variables - live as long as the object
    private String instanceData;
    
    public void demonstrateLifecycle() {
        // Local variables - live only during method execution
        String localData = "Temporary data";
        
        // After method ends, localData is eligible for garbage collection
        // instanceData continues to live with the object
        // globalCache continues to live for entire application
    }
}
```

### 2. Memory Leaks Prevention
```java
public class MemoryLeakPrevention {
    
    // Potential memory leak - static reference to large objects
    private static List<byte[]> largeDataCache = new ArrayList<>();
    
    // Better approach - use weak references or clear references
    private static WeakHashMap<String, byte[]> weakCache = new WeakHashMap<>();
    
    public void addData(byte[] data) {
        // Potential memory leak
        // largeDataCache.add(data);
        
        // Better approach
        weakCache.put("key", data);
    }
    
    public void clearCache() {
        // Clear static cache when no longer needed
        largeDataCache.clear();
        weakCache.clear();
    }
}
```

## Summary

This deep dive covered:

1. **Variable Types**: Local, instance, and static variables with their characteristics and usage
2. **Static Blocks**: For static initialization when class is loaded
3. **Instance Blocks**: For common initialization code executed before constructors
4. **`this` Keyword**: For referring to current object and constructor chaining
5. **`super` Keyword**: For accessing parent class members and constructor chaining
6. **Variable Shadowing and Hiding**: Understanding scope and visibility
7. **Best Practices**: Naming, organization, and initialization guidelines
8. **Memory Management**: Understanding variable lifecycle and preventing memory leaks

These concepts are fundamental to Java programming and essential for writing clean, maintainable, and efficient code. 