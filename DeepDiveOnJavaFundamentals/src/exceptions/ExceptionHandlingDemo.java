import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Comprehensive Exception Handling Demo
 * This program demonstrates various aspects of exception handling in Java
 * including try-catch blocks, custom exceptions, and best practices.
 */

// Custom checked exception
class InsufficientFundsException extends Exception {
    private double amount;
    private double balance;
    
    public InsufficientFundsException(double amount, double balance) {
        super("Insufficient funds. Required: $" + amount + ", Available: $" + balance);
        this.amount = amount;
        this.balance = balance;
    }
    
    public double getAmount() { return amount; }
    public double getBalance() { return balance; }
}

// Custom unchecked exception
class InvalidInputException extends RuntimeException {
    private String input;
    
    public InvalidInputException(String input) {
        super("Invalid input: " + input);
        this.input = input;
    }
    
    public String getInput() { return input; }
}

// Bank account class with exception handling
class BankAccount {
    private String accountNumber;
    private double balance;
    
    public BankAccount(String accountNumber, double initialBalance) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new InvalidInputException(accountNumber);
        }
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new InsufficientFundsException(amount, balance);
        }
        balance -= amount;
        System.out.println("Withdrawn: $" + amount + ", New balance: $" + balance);
    }
    
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
        System.out.println("Deposited: $" + amount + ", New balance: $" + balance);
    }
    
    public double getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
}

// Main demonstration class
public class ExceptionHandlingDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Exception Handling Demo ===\n");
        
        // 1. Basic exception handling
        demonstrateBasicExceptionHandling();
        
        // 2. Multiple catch blocks
        demonstrateMultipleCatchBlocks();
        
        // 3. Try-catch with finally
        demonstrateTryCatchFinally();
        
        // 4. Try-with-resources
        demonstrateTryWithResources();
        
        // 5. Custom exceptions
        demonstrateCustomExceptions();
        
        // 6. Exception propagation
        demonstrateExceptionPropagation();
        
        // 7. Best practices
        demonstrateBestPractices();
        
        // 8. Interactive exception handling
        demonstrateInteractiveExceptionHandling();
        
        System.out.println("\n=== End of Exception Handling Demo ===");
    }
    
    /**
     * Demonstrates basic try-catch exception handling
     */
    public static void demonstrateBasicExceptionHandling() {
        System.out.println("1. BASIC EXCEPTION HANDLING:");
        System.out.println("============================");
        
        // Division by zero
        try {
            int result = 10 / 0;
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Error: Division by zero");
            System.out.println("Exception message: " + e.getMessage());
        }
        
        // Array index out of bounds
        try {
            int[] numbers = {1, 2, 3};
            System.out.println("Number at index 5: " + numbers[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Array index out of bounds");
            System.out.println("Exception message: " + e.getMessage());
        }
        
        // Null pointer exception
        try {
            String str = null;
            System.out.println("Length: " + str.length());
        } catch (NullPointerException e) {
            System.out.println("Error: Null pointer exception");
            System.out.println("Exception message: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates multiple catch blocks
     */
    public static void demonstrateMultipleCatchBlocks() {
        System.out.println("2. MULTIPLE CATCH BLOCKS:");
        System.out.println("=========================");
        
        int[] numbers = {1, 0, 3, 4, 5};
        
        // Test different scenarios
        processArrayElement(numbers, 10);  // ArrayIndexOutOfBoundsException
        processArrayElement(numbers, 1);   // ArithmeticException
        processArrayElement(numbers, 2);   // No exception
        
        System.out.println();
    }
    
    private static void processArrayElement(int[] array, int index) {
        try {
            int value = array[index];
            int result = 100 / value;
            System.out.println("Result for index " + index + ": " + result);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array index out of bounds for index " + index);
            System.out.println("Array length: " + array.length);
        } catch (ArithmeticException e) {
            System.out.println("Division by zero at index " + index);
            System.out.println("Value at index " + index + " is zero");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates try-catch with finally block
     */
    public static void demonstrateTryCatchFinally() {
        System.out.println("3. TRY-CATCH WITH FINALLY:");
        System.out.println("==========================");
        
        FileInputStream file = null;
        try {
            file = new FileInputStream("nonexistent.txt");
            int data = file.read();
            System.out.println("Read data: " + data);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } finally {
            // This block always executes
            if (file != null) {
                try {
                    file.close();
                    System.out.println("File closed successfully");
                } catch (IOException e) {
                    System.out.println("Error closing file: " + e.getMessage());
                }
            }
            System.out.println("Finally block executed");
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates try-with-resources (Java 7+)
     */
    public static void demonstrateTryWithResources() {
        System.out.println("4. TRY-WITH-RESOURCES:");
        System.out.println("======================");
        
        try (FileInputStream file = new FileInputStream("nonexistent.txt")) {
            int data = file.read();
            System.out.println("Read data: " + data);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        // File is automatically closed
        
        System.out.println();
    }
    
    /**
     * Demonstrates custom exceptions
     */
    public static void demonstrateCustomExceptions() {
        System.out.println("5. CUSTOM EXCEPTIONS:");
        System.out.println("=====================");
        
        // Test invalid account creation
        try {
            BankAccount invalidAccount = new BankAccount("", 1000);
        } catch (InvalidInputException e) {
            System.out.println("Account creation error: " + e.getMessage());
            System.out.println("Invalid input: " + e.getInput());
        }
        
        // Test bank operations
        try {
            BankAccount account = new BankAccount("12345", 500);
            
            // Test valid operations
            account.deposit(200);
            account.withdraw(100);
            
            // Test insufficient funds
            account.withdraw(1000);
        } catch (InsufficientFundsException e) {
            System.out.println("Withdrawal error: " + e.getMessage());
            System.out.println("Required: $" + e.getAmount());
            System.out.println("Available: $" + e.getBalance());
        } catch (InvalidInputException e) {
            System.out.println("Account error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid argument: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates exception propagation
     */
    public static void demonstrateExceptionPropagation() {
        System.out.println("6. EXCEPTION PROPAGATION:");
        System.out.println("=========================");
        
        try {
            method1();
        } catch (Exception e) {
            System.out.println("Exception caught in main: " + e.getMessage());
            System.out.println("Stack trace:");
            e.printStackTrace();
        }
        
        System.out.println();
    }
    
    public static void method1() throws Exception {
        System.out.println("Method 1 called");
        method2();
    }
    
    public static void method2() throws Exception {
        System.out.println("Method 2 called");
        method3();
    }
    
    public static void method3() throws Exception {
        System.out.println("Method 3 called");
        throw new Exception("Exception from method 3");
    }
    
    /**
     * Demonstrates exception handling best practices
     */
    public static void demonstrateBestPractices() {
        System.out.println("7. BEST PRACTICES:");
        System.out.println("==================");
        
        // Good practice: Specific exception handling
        try {
            int[] array = {1, 2, 3};
            System.out.println("Array element: " + array[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Specific exception handling: " + e.getMessage());
        }
        
        // Good practice: Meaningful error messages
        try {
            validateAge(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
        
        // Good practice: Proper resource cleanup
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Resource management with try-with-resources");
        }
        
        System.out.println();
    }
    
    private static void validateAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative: " + age);
        }
        if (age > 150) {
            throw new IllegalArgumentException("Age seems unrealistic: " + age);
        }
        System.out.println("Valid age: " + age);
    }
    
    /**
     * Demonstrates interactive exception handling
     */
    public static void demonstrateInteractiveExceptionHandling() {
        System.out.println("8. INTERACTIVE EXCEPTION HANDLING:");
        System.out.println("=================================");
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("Enter a number: ");
            String input = scanner.nextLine();
            
            try {
                int number = Integer.parseInt(input);
                System.out.println("You entered: " + number);
                
                if (number < 0) {
                    throw new IllegalArgumentException("Number must be positive");
                }
                
                System.out.println("Square root: " + Math.sqrt(number));
                
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number");
                System.out.println("Input was: " + input);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        } finally {
            scanner.close();
        }
        
        System.out.println();
    }
} 