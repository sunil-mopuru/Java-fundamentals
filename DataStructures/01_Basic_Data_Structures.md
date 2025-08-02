# Basic Data Structures in Java

## 1. Arrays

### What are Arrays?

An array is a fixed-size collection of elements of the same data type, stored in contiguous memory locations. Each element can be accessed directly using an index.

### Key Characteristics

- **Fixed Size**: Once created, size cannot be changed
- **Contiguous Memory**: Elements are stored in adjacent memory locations
- **Index-based Access**: O(1) time complexity for accessing elements
- **Zero-based Indexing**: First element is at index 0

### Types of Arrays

#### 1.1 Single-Dimensional Arrays

```java
// Declaration and initialization
int[] numbers = {1, 2, 3, 4, 5};
String[] names = new String[3];
names[0] = "Alice";
names[1] = "Bob";
names[2] = "Charlie";

// Accessing elements
System.out.println(numbers[0]); // Output: 1
System.out.println(names[1]);   // Output: Bob

// Array length
System.out.println(numbers.length); // Output: 5
```

#### 1.2 Multi-Dimensional Arrays

```java
// 2D Array (Matrix)
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

// 3D Array
int[][][] cube = new int[3][3][3];

// Accessing elements
System.out.println(matrix[1][2]); // Output: 6
```

### Common Array Operations

#### Traversal

```java
// Using for loop
for (int i = 0; i < numbers.length; i++) {
    System.out.print(numbers[i] + " ");
}

// Using enhanced for loop (for-each)
for (int num : numbers) {
    System.out.print(num + " ");
}
```

#### Searching

```java
// Linear Search - O(n)
public static int linearSearch(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target) {
            return i;
        }
    }
    return -1; // Not found
}

// Binary Search - O(log n) (requires sorted array)
public static int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;

        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    return -1;
}
```

#### Sorting

```java
// Bubble Sort - O(n²)
public static void bubbleSort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                // Swap
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}

// Using built-in sort
Arrays.sort(numbers);
```

### Time Complexity Analysis

- **Access**: O(1)
- **Search**: O(n) for linear search, O(log n) for binary search
- **Insertion**: O(n) (shifting required)
- **Deletion**: O(n) (shifting required)
- **Sorting**: O(n log n) for efficient algorithms

## 2. Strings

### What are Strings?

A String in Java is an immutable sequence of characters. Once created, a String object cannot be modified.

### Key Characteristics

- **Immutable**: Cannot be changed after creation
- **String Pool**: JVM maintains a pool of unique strings
- **Unicode Support**: Can store any Unicode character
- **Rich API**: Many built-in methods for manipulation

### String Creation and Manipulation

```java
// Different ways to create strings
String str1 = "Hello World";
String str2 = new String("Hello World");
char[] chars = {'H', 'e', 'l', 'l', 'o'};
String str3 = new String(chars);

// String concatenation
String firstName = "John";
String lastName = "Doe";
String fullName = firstName + " " + lastName; // "John Doe"

// Using StringBuilder for efficient concatenation
StringBuilder sb = new StringBuilder();
sb.append("Hello");
sb.append(" ");
sb.append("World");
String result = sb.toString();
```

### Common String Operations

#### Length and Access

```java
String text = "Hello World";
System.out.println(text.length());     // 11
System.out.println(text.charAt(0));    // 'H'
System.out.println(text.charAt(6));    // 'W'
```

#### Substring Operations

```java
String text = "Hello World";
String sub1 = text.substring(0, 5);    // "Hello"
String sub2 = text.substring(6);       // "World"
```

#### Searching and Replacing

```java
String text = "Hello World";
System.out.println(text.indexOf("o"));     // 4
System.out.println(text.lastIndexOf("o")); // 7
System.out.println(text.contains("World")); // true
System.out.println(text.startsWith("Hello")); // true
System.out.println(text.endsWith("World"));   // true

// Replacing
String newText = text.replace("World", "Java"); // "Hello Java"
```

#### Case Conversion

```java
String text = "Hello World";
System.out.println(text.toUpperCase()); // "HELLO WORLD"
System.out.println(text.toLowerCase()); // "hello world"
```

#### Splitting and Joining

```java
String text = "apple,banana,orange";
String[] fruits = text.split(",");
// fruits = ["apple", "banana", "orange"]

String joined = String.join(" - ", fruits);
// joined = "apple - banana - orange"
```

### String Comparison

```java
String s1 = "Hello";
String s2 = "Hello";
String s3 = new String("Hello");

// == compares references
System.out.println(s1 == s2);      // true (same reference in string pool)
System.out.println(s1 == s3);      // false (different references)

// equals() compares content
System.out.println(s1.equals(s2)); // true
System.out.println(s1.equals(s3)); // true

// compareTo() for lexicographical comparison
System.out.println("apple".compareTo("banana")); // negative
System.out.println("banana".compareTo("apple")); // positive
System.out.println("apple".compareTo("apple"));  // zero
```

### StringBuilder and StringBuffer

```java
// StringBuilder (not thread-safe, faster)
StringBuilder sb = new StringBuilder();
sb.append("Hello");
sb.append(" ");
sb.append("World");
String result1 = sb.toString();

// StringBuffer (thread-safe, slower)
StringBuffer buffer = new StringBuffer();
buffer.append("Hello");
buffer.append(" ");
buffer.append("World");
String result2 = buffer.toString();
```

## 3. Primitive Data Types

### Overview

Java has 8 primitive data types that serve as the building blocks for all data structures.

### Data Type Details

| Type      | Size    | Range                  | Default Value | Example                |
| --------- | ------- | ---------------------- | ------------- | ---------------------- |
| `byte`    | 8 bits  | -128 to 127            | 0             | `byte b = 100;`        |
| `short`   | 16 bits | -32,768 to 32,767      | 0             | `short s = 1000;`      |
| `int`     | 32 bits | -2³¹ to 2³¹-1          | 0             | `int i = 100000;`      |
| `long`    | 64 bits | -2⁶³ to 2⁶³-1          | 0L            | `long l = 100000L;`    |
| `float`   | 32 bits | ±3.4E-38 to ±3.4E+38   | 0.0f          | `float f = 3.14f;`     |
| `double`  | 64 bits | ±1.7E-308 to ±1.7E+308 | 0.0d          | `double d = 3.14;`     |
| `char`    | 16 bits | '\u0000' to '\uffff'   | '\u0000'      | `char c = 'A';`        |
| `boolean` | 1 bit   | true/false             | false         | `boolean flag = true;` |

### Type Conversion (Casting)

```java
// Implicit casting (widening)
int i = 100;
long l = i;        // int to long
float f = l;       // long to float
double d = f;      // float to double

// Explicit casting (narrowing)
double d = 100.04;
int i = (int) d;   // double to int (truncates decimal)
long l = (long) d; // double to long

// Character to integer
char c = 'A';
int ascii = c;     // 65 (ASCII value)
```

### Wrapper Classes

```java
// Autoboxing (primitive to wrapper)
Integer intObj = 100;        // int to Integer
Double doubleObj = 3.14;     // double to Double
Boolean boolObj = true;      // boolean to Boolean

// Unboxing (wrapper to primitive)
int i = intObj;              // Integer to int
double d = doubleObj;        // Double to double
boolean b = boolObj;         // Boolean to boolean

// Useful methods
String numStr = "123";
int num = Integer.parseInt(numStr);    // String to int
String str = Integer.toString(num);    // int to String
```

## 4. Practical Examples

### Example 1: Array Operations

```java
public class ArrayOperations {
    public static void main(String[] args) {
        // Create and initialize array
        int[] numbers = {64, 34, 25, 12, 22, 11, 90};

        // Print original array
        System.out.println("Original array:");
        printArray(numbers);

        // Sort array
        bubbleSort(numbers);
        System.out.println("Sorted array:");
        printArray(numbers);

        // Search for element
        int target = 25;
        int index = binarySearch(numbers, target);
        if (index != -1) {
            System.out.println(target + " found at index " + index);
        } else {
            System.out.println(target + " not found");
        }
    }

    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    // Bubble sort implementation
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Binary search implementation
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
```

### Example 2: String Manipulation

```java
public class StringManipulation {
    public static void main(String[] args) {
        String text = "  Hello World Java Programming  ";

        // Basic operations
        System.out.println("Original: '" + text + "'");
        System.out.println("Length: " + text.length());
        System.out.println("Trimmed: '" + text.trim() + "'");
        System.out.println("Uppercase: " + text.toUpperCase());
        System.out.println("Lowercase: " + text.toLowerCase());

        // Word count
        String[] words = text.trim().split("\\s+");
        System.out.println("Word count: " + words.length);

        // Character frequency
        countCharacters(text);

        // Palindrome check
        String palindrome = "racecar";
        System.out.println("Is '" + palindrome + "' palindrome? " + isPalindrome(palindrome));
    }

    public static void countCharacters(String text) {
        int[] charCount = new int[256];

        for (char c : text.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) {
                charCount[c]++;
            }
        }

        System.out.println("Character frequency:");
        for (int i = 0; i < charCount.length; i++) {
            if (charCount[i] > 0) {
                System.out.println((char) i + ": " + charCount[i]);
            }
        }
    }

    public static boolean isPalindrome(String str) {
        str = str.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
        int left = 0, right = str.length() - 1;

        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
```

## 5. Best Practices

### Arrays

1. **Use appropriate size**: Don't create unnecessarily large arrays
2. **Bounds checking**: Always check array bounds before access
3. **Use enhanced for loop**: When you don't need the index
4. **Consider ArrayList**: For dynamic sizing needs

### Strings

1. **Use StringBuilder**: For multiple string concatenations
2. **String pool awareness**: Understand how string literals work
3. **Immutable nature**: Remember strings cannot be modified
4. **Use appropriate methods**: Choose the right method for your needs

### Primitive Types

1. **Choose appropriate types**: Use the smallest type that fits your needs
2. **Be aware of precision**: Understand floating-point precision issues
3. **Use wrapper classes**: When you need object behavior
4. **Consider memory usage**: Primitive types are more memory efficient

## 6. Common Pitfalls

1. **Array Index Out of Bounds**: Always check array bounds
2. **String Immutability**: Remember strings cannot be changed
3. **Type Conversion Loss**: Be careful with narrowing conversions
4. **Memory Leaks**: Be mindful of object creation in loops
5. **Performance Issues**: Choose appropriate data structures for your use case

## 7. Practice Problems

1. **Array Problems**:

   - Find the maximum and minimum elements in an array
   - Reverse an array in-place
   - Find duplicate elements in an array
   - Rotate an array by k positions

2. **String Problems**:

   - Check if two strings are anagrams
   - Find the longest common substring
   - Count vowels and consonants
   - Remove duplicate characters

3. **Combined Problems**:
   - Convert string to character array and back
   - Find the most frequent character in a string
   - Check if a string contains only digits

These basic data structures form the foundation for understanding more complex data structures and algorithms in Java.
