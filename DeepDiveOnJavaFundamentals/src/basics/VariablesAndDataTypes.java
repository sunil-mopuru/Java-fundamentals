/**
 * Variables and Data Types - Practical Examples
 * This class demonstrates various data types, variable declarations,
 * type conversions, and best practices in Java.
 */
public class VariablesAndDataTypes {
    
    // Class-level constants
    public static final double PI = 3.14159;
    public static final int MAX_STUDENTS = 100;
    
    public static void main(String[] args) {
        System.out.println("=== Variables and Data Types Examples ===\n");
        
        // 1. Primitive Data Types
        demonstratePrimitiveTypes();
        
        // 2. Reference Data Types
        demonstrateReferenceTypes();
        
        // 3. Type Conversion (Casting)
        demonstrateTypeCasting();
        
        // 4. Variable Scope and Lifecycle
        demonstrateVariableScope();
        
        // 5. Best Practices
        demonstrateBestPractices();
    }
    
    /**
     * Demonstrates all primitive data types with examples
     */
    public static void demonstratePrimitiveTypes() {
        System.out.println("1. PRIMITIVE DATA TYPES:");
        System.out.println("------------------------");
        
        // Integer types
        byte byteValue = 127;                    // 8-bit: -128 to 127
        short shortValue = 32767;                // 16-bit: -32,768 to 32,767
        int intValue = 2147483647;               // 32-bit: -2^31 to 2^31-1
        long longValue = 9223372036854775807L;   // 64-bit: -2^63 to 2^63-1
        
        System.out.println("Integer Types:");
        System.out.println("  byte: " + byteValue + " (8-bit)");
        System.out.println("  short: " + shortValue + " (16-bit)");
        System.out.println("  int: " + intValue + " (32-bit)");
        System.out.println("  long: " + longValue + " (64-bit)");
        
        // Floating-point types
        float floatValue = 3.14159f;             // 32-bit single precision
        double doubleValue = 3.14159265359;      // 64-bit double precision
        
        System.out.println("\nFloating-Point Types:");
        System.out.println("  float: " + floatValue + " (32-bit)");
        System.out.println("  double: " + doubleValue + " (64-bit)");
        
        // Character type
        char charValue = 'A';                    // 16-bit Unicode
        char unicodeChar = '\u0041';             // Unicode for 'A'
        
        System.out.println("\nCharacter Type:");
        System.out.println("  char: " + charValue + " (16-bit Unicode)");
        System.out.println("  Unicode char: " + unicodeChar);
        
        // Boolean type
        boolean booleanValue = true;             // true or false
        
        System.out.println("\nBoolean Type:");
        System.out.println("  boolean: " + booleanValue);
        
        System.out.println();
    }
    
    /**
     * Demonstrates reference data types
     */
    public static void demonstrateReferenceTypes() {
        System.out.println("2. REFERENCE DATA TYPES:");
        System.out.println("-------------------------");
        
        // String (most commonly used reference type)
        String message = "Hello, Java!";
        String emptyString = "";
        String nullString = null;
        
        System.out.println("String Examples:");
        System.out.println("  message: \"" + message + "\"");
        System.out.println("  emptyString: \"" + emptyString + "\"");
        System.out.println("  nullString: " + nullString);
        
        // Wrapper classes for primitive types
        Integer integerObject = 42;
        Double doubleObject = 3.14;
        Boolean booleanObject = true;
        Character characterObject = 'X';
        
        System.out.println("\nWrapper Classes:");
        System.out.println("  Integer: " + integerObject);
        System.out.println("  Double: " + doubleObject);
        System.out.println("  Boolean: " + booleanObject);
        System.out.println("  Character: " + characterObject);
        
        // Arrays (reference types)
        int[] intArray = {1, 2, 3, 4, 5};
        String[] stringArray = {"Apple", "Banana", "Orange"};
        
        System.out.println("\nArrays:");
        System.out.println("  intArray length: " + intArray.length);
        System.out.println("  stringArray length: " + stringArray.length);
        
        System.out.println();
    }
    
    /**
     * Demonstrates type conversion (casting)
     */
    public static void demonstrateTypeCasting() {
        System.out.println("3. TYPE CONVERSION (CASTING):");
        System.out.println("-----------------------------");
        
        // Implicit casting (widening - automatic)
        System.out.println("Implicit Casting (Widening):");
        int intValue = 100;
        long longValue = intValue;           // int -> long (automatic)
        float floatValue = longValue;        // long -> float (automatic)
        double doubleValue = floatValue;     // float -> double (automatic)
        
        System.out.println("  int -> long: " + intValue + " -> " + longValue);
        System.out.println("  long -> float: " + longValue + " -> " + floatValue);
        System.out.println("  float -> double: " + floatValue + " -> " + doubleValue);
        
        // Explicit casting (narrowing - manual)
        System.out.println("\nExplicit Casting (Narrowing):");
        double largeDouble = 123.456;
        int intFromDouble = (int) largeDouble;    // double -> int (manual)
        long largeLong = 1234567890L;
        int intFromLong = (int) largeLong;        // long -> int (manual)
        
        System.out.println("  double -> int: " + largeDouble + " -> " + intFromDouble);
        System.out.println("  long -> int: " + largeLong + " -> " + intFromLong);
        
        // Character and number conversions
        System.out.println("\nCharacter Conversions:");
        char charA = 'A';
        int charToInt = charA;                    // char -> int (automatic)
        char intToChar = (char) 66;               // int -> char (manual)
        
        System.out.println("  char 'A' -> int: " + charToInt);
        System.out.println("  int 66 -> char: " + intToChar);
        
        // Potential data loss examples
        System.out.println("\nData Loss Examples:");
        double preciseDouble = 3.14159;
        int truncatedInt = (int) preciseDouble;
        System.out.println("  " + preciseDouble + " -> " + truncatedInt + " (decimal part lost)");
        
        long veryLargeLong = 2147483648L;  // Larger than int max value
        int overflowedInt = (int) veryLargeLong;
        System.out.println("  " + veryLargeLong + " -> " + overflowedInt + " (overflow occurred)");
        
        System.out.println();
    }
    
    /**
     * Demonstrates variable scope and lifecycle
     */
    public static void demonstrateVariableScope() {
        System.out.println("4. VARIABLE SCOPE AND LIFECYCLE:");
        System.out.println("--------------------------------");
        
        // Local variables (method scope)
        int localVariable = 10;
        System.out.println("Local variable: " + localVariable);
        
        // Block scope
        {
            int blockVariable = 20;
            System.out.println("Block variable: " + blockVariable);
            System.out.println("Local variable accessible in block: " + localVariable);
        }
        // System.out.println(blockVariable); // This would cause compilation error
        
        // Demonstrating variable shadowing
        int shadowedVariable = 30;
        System.out.println("Outer shadowedVariable: " + shadowedVariable);
        
        {
            int shadowedVariable = 40;  // Shadows the outer variable
            System.out.println("Inner shadowedVariable: " + shadowedVariable);
        }
        
        System.out.println("Outer shadowedVariable after block: " + shadowedVariable);
        
        System.out.println();
    }
    
    /**
     * Demonstrates best practices for variables
     */
    public static void demonstrateBestPractices() {
        System.out.println("5. BEST PRACTICES:");
        System.out.println("------------------");
        
        // 1. Meaningful variable names
        int numberOfStudents = 25;           // Good
        int n = 25;                          // Bad
        
        String firstName = "John";           // Good
        String fn = "John";                  // Bad
        
        boolean isUserActive = true;         // Good
        boolean flag = true;                 // Bad
        
        System.out.println("Good naming: numberOfStudents = " + numberOfStudents);
        System.out.println("Bad naming: n = " + n);
        
        // 2. Use appropriate data types
        byte smallNumber = 100;              // Use byte for small numbers
        long largeNumber = 1234567890L;      // Use long for large numbers
        float price = 29.99f;                // Use float for simple decimals
        double preciseValue = 3.14159265359; // Use double for precision
        
        System.out.println("\nAppropriate data types:");
        System.out.println("  byte for small numbers: " + smallNumber);
        System.out.println("  long for large numbers: " + largeNumber);
        System.out.println("  float for simple decimals: " + price);
        System.out.println("  double for precise values: " + preciseValue);
        
        // 3. Initialize variables
        int initializedVariable = 0;         // Good
        String initializedString = "";       // Good
        
        System.out.println("\nInitialized variables:");
        System.out.println("  initializedVariable: " + initializedVariable);
        System.out.println("  initializedString: \"" + initializedString + "\"");
        
        // 4. Use constants for magic numbers
        final int MAX_RETRY_ATTEMPTS = 3;
        final double TAX_RATE = 0.08;
        
        System.out.println("\nConstants:");
        System.out.println("  MAX_RETRY_ATTEMPTS: " + MAX_RETRY_ATTEMPTS);
        System.out.println("  TAX_RATE: " + TAX_RATE);
        
        // 5. Group related variables
        // Coordinates
        int x = 10, y = 20, z = 30;
        
        // User information
        String firstName2 = "Jane", lastName = "Doe", email = "jane@example.com";
        
        System.out.println("\nGrouped variables:");
        System.out.println("  Coordinates: (" + x + ", " + y + ", " + z + ")");
        System.out.println("  User: " + firstName2 + " " + lastName + " (" + email + ")");
        
        System.out.println("\n=== End of Variables and Data Types Examples ===");
    }
} 