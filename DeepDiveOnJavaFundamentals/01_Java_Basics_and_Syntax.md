# Java Basics and Syntax - Deep Dive

## 1. Variables and Data Types

### Primitive Data Types

Java has 8 primitive data types that store simple values directly in memory:

#### Integer Types
- **byte**: 8-bit signed integer (-128 to 127)
- **short**: 16-bit signed integer (-32,768 to 32,767)
- **int**: 32-bit signed integer (-2^31 to 2^31-1)
- **long**: 64-bit signed integer (-2^63 to 2^63-1)

#### Floating-Point Types
- **float**: 32-bit single-precision floating point
- **double**: 64-bit double-precision floating point

#### Character Type
- **char**: 16-bit Unicode character

#### Boolean Type
- **boolean**: true or false

### Reference Data Types

Reference types store references to objects in memory:
- **Classes**: String, Integer, Double, etc.
- **Arrays**: Collections of elements
- **Interfaces**: Abstract contracts

### Variable Declaration and Initialization

```java
// Primitive types
int age = 25;
double salary = 75000.50;
char grade = 'A';
boolean isActive = true;

// Reference types
String name = "John Doe";
Integer count = 100;
Double price = 29.99;
```

### Type Conversion (Casting)

#### Implicit Casting (Widening)
```java
int intValue = 100;
long longValue = intValue;  // Automatic conversion
double doubleValue = longValue;  // Automatic conversion
```

#### Explicit Casting (Narrowing)
```java
double doubleValue = 100.04;
int intValue = (int) doubleValue;  // Explicit casting, loses decimal part
long longValue = 1234567890L;
int intValue2 = (int) longValue;  // May lose precision
```

## 2. Operators and Expressions

### Arithmetic Operators
```java
int a = 10, b = 3;
int sum = a + b;        // 13
int difference = a - b; // 7
int product = a * b;    // 30
int quotient = a / b;   // 3 (integer division)
int remainder = a % b;  // 1
```

### Increment/Decrement Operators
```java
int x = 5;
int y = ++x;  // Pre-increment: x becomes 6, y gets 6
int z = x++;  // Post-increment: z gets 6, x becomes 7
```

### Comparison Operators
```java
boolean isEqual = (a == b);      // false
boolean isNotEqual = (a != b);   // true
boolean isGreater = (a > b);     // true
boolean isLess = (a < b);        // false
boolean isGreaterEqual = (a >= b); // true
boolean isLessEqual = (a <= b);    // false
```

### Logical Operators
```java
boolean condition1 = true;
boolean condition2 = false;

boolean andResult = condition1 && condition2;  // false
boolean orResult = condition1 || condition2;   // true
boolean notResult = !condition1;               // false
```

### Assignment Operators
```java
int value = 10;
value += 5;   // value = value + 5 (15)
value -= 3;   // value = value - 3 (12)
value *= 2;   // value = value * 2 (24)
value /= 4;   // value = value / 4 (6)
value %= 4;   // value = value % 4 (2)
```

## 3. Control Flow Statements

### Conditional Statements

#### if-else Statement
```java
int score = 85;

if (score >= 90) {
    System.out.println("Grade: A");
} else if (score >= 80) {
    System.out.println("Grade: B");
} else if (score >= 70) {
    System.out.println("Grade: C");
} else {
    System.out.println("Grade: F");
}
```

#### switch Statement
```java
int dayOfWeek = 3;
String dayName;

switch (dayOfWeek) {
    case 1:
        dayName = "Monday";
        break;
    case 2:
        dayName = "Tuesday";
        break;
    case 3:
        dayName = "Wednesday";
        break;
    case 4:
        dayName = "Thursday";
        break;
    case 5:
        dayName = "Friday";
        break;
    case 6:
    case 7:
        dayName = "Weekend";
        break;
    default:
        dayName = "Invalid day";
}
```

### Looping Statements

#### for Loop
```java
// Traditional for loop
for (int i = 0; i < 5; i++) {
    System.out.println("Count: " + i);
}

// Enhanced for loop (for-each)
int[] numbers = {1, 2, 3, 4, 5};
for (int number : numbers) {
    System.out.println("Number: " + number);
}
```

#### while Loop
```java
int count = 0;
while (count < 5) {
    System.out.println("Count: " + count);
    count++;
}
```

#### do-while Loop
```java
int number = 1;
do {
    System.out.println("Number: " + number);
    number++;
} while (number <= 5);
```

### Break and Continue Statements
```java
// Break example
for (int i = 0; i < 10; i++) {
    if (i == 5) {
        break;  // Exit loop when i equals 5
    }
    System.out.println("i: " + i);
}

// Continue example
for (int i = 0; i < 10; i++) {
    if (i % 2 == 0) {
        continue;  // Skip even numbers
    }
    System.out.println("Odd number: " + i);
}
```

## 4. Arrays and Collections

### Arrays

#### Array Declaration and Initialization
```java
// Declaration
int[] numbers;
String[] names;

// Initialization
numbers = new int[5];
names = new String[3];

// Declaration and initialization in one line
int[] scores = {85, 92, 78, 96, 88};
String[] colors = {"Red", "Green", "Blue"};

// Multi-dimensional arrays
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};
```

#### Array Operations
```java
int[] numbers = {10, 20, 30, 40, 50};

// Accessing elements
int firstElement = numbers[0];  // 10
int lastElement = numbers[numbers.length - 1];  // 50

// Iterating through array
for (int i = 0; i < numbers.length; i++) {
    System.out.println("Element " + i + ": " + numbers[i]);
}

// Enhanced for loop
for (int number : numbers) {
    System.out.println("Number: " + number);
}
```

### Basic Collections

#### ArrayList
```java
import java.util.ArrayList;

ArrayList<String> fruits = new ArrayList<>();
fruits.add("Apple");
fruits.add("Banana");
fruits.add("Orange");

// Accessing elements
String firstFruit = fruits.get(0);

// Iterating
for (String fruit : fruits) {
    System.out.println(fruit);
}

// Size and checking
int size = fruits.size();
boolean containsApple = fruits.contains("Apple");
```

## 5. String Manipulation

### String Creation and Methods
```java
// String creation
String str1 = "Hello";
String str2 = new String("World");

// Concatenation
String result = str1 + " " + str2;  // "Hello World"
String result2 = str1.concat(" ").concat(str2);

// String methods
String text = "  Hello World  ";
int length = text.length();                    // 15
String trimmed = text.trim();                  // "Hello World"
String upperCase = text.toUpperCase();         // "  HELLO WORLD  "
String lowerCase = text.toLowerCase();         // "  hello world  "
String substring = text.substring(2, 7);       // "Hello"
boolean startsWith = text.startsWith("  He");  // true
boolean endsWith = text.endsWith("ld  ");      // true
```

### String Comparison
```java
String str1 = "Hello";
String str2 = "Hello";
String str3 = new String("Hello");

// Using == (compares references)
boolean refEqual1 = (str1 == str2);    // true (string literal)
boolean refEqual2 = (str1 == str3);    // false (different objects)

// Using equals() (compares content)
boolean contentEqual1 = str1.equals(str2);  // true
boolean contentEqual2 = str1.equals(str3);  // true

// Using compareTo()
int comparison = str1.compareTo("World");  // negative (str1 comes before "World")
```

## 6. Input and Output

### Console Input/Output
```java
import java.util.Scanner;

// Output
System.out.println("Hello, World!");
System.out.print("This is on the same line");
System.out.printf("Formatted: %s, %d, %.2f", "String", 42, 3.14159);

// Input
Scanner scanner = new Scanner(System.in);
System.out.print("Enter your name: ");
String name = scanner.nextLine();

System.out.print("Enter your age: ");
int age = scanner.nextInt();

System.out.print("Enter your salary: ");
double salary = scanner.nextDouble();

scanner.close();
```

## 7. Best Practices

### Variable Naming Conventions
```java
// Good naming
int userAge = 25;
String firstName = "John";
boolean isActive = true;
double hourlyRate = 15.50;

// Constants
final double PI = 3.14159;
final int MAX_SIZE = 100;
```

### Code Organization
```java
// Group related variables
int x, y, z;  // Coordinates
String firstName, lastName, email;  // User information

// Use meaningful names
int numberOfStudents = 25;  // Better than int n = 25;
boolean isValidInput = true;  // Better than boolean flag = true;
```

### Memory Management
```java
// Use appropriate data types
byte smallNumber = 100;  // Use byte for small numbers
long largeNumber = 1234567890L;  // Use long for large numbers

// Avoid unnecessary object creation
String message = "Hello";  // Use string literal
// Instead of: String message = new String("Hello");
```

## Summary

This section covered the fundamental building blocks of Java programming:
- **Variables and Data Types**: Understanding primitive and reference types
- **Operators**: Arithmetic, comparison, logical, and assignment operators
- **Control Flow**: Making decisions and repeating code
- **Arrays and Collections**: Storing and manipulating data
- **String Manipulation**: Working with text data
- **Input/Output**: Interacting with users
- **Best Practices**: Writing clean, maintainable code

These concepts form the foundation for more advanced Java programming topics. 