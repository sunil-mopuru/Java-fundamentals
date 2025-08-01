# Exception Handling in Java - Deep Dive

## 1. Introduction to Exceptions

An exception is an event that occurs during the execution of a program that disrupts the normal flow of instructions. Java provides a robust exception handling mechanism to deal with these unexpected situations.

### What are Exceptions?

Exceptions are objects that represent errors or exceptional conditions that occur during program execution. They can be:
- **Checked Exceptions**: Must be handled at compile time
- **Unchecked Exceptions**: Runtime exceptions that don't require explicit handling

### Exception Hierarchy

```
Throwable
├── Error (Unchecked)
│   ├── OutOfMemoryError
│   ├── StackOverflowError
│   └── ...
└── Exception (Checked)
    ├── IOException
    ├── SQLException
    ├── RuntimeException (Unchecked)
    │   ├── NullPointerException
    │   ├── ArrayIndexOutOfBoundsException
    │   ├── IllegalArgumentException
    │   └── ...
    └── ...
```

## 2. Types of Exceptions

### Checked Exceptions (Compile-time)

Checked exceptions are exceptions that must be handled at compile time. They represent recoverable conditions.

```java
import java.io.FileInputStream;
import java.io.IOException;

public class CheckedExceptionExample {
    public static void readFile(String filename) throws IOException {
        FileInputStream file = new FileInputStream(filename);
        // File operations
        file.close();
    }
    
    public static void main(String[] args) {
        try {
            readFile("nonexistent.txt");
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
```

### Unchecked Exceptions (Runtime)

Unchecked exceptions are runtime exceptions that don't require explicit handling.

```java
public class UncheckedExceptionExample {
    public static void divideNumbers(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        int result = a / b;
        System.out.println("Result: " + result);
    }
    
    public static void main(String[] args) {
        try {
            divideNumbers(10, 0);
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

## 3. Try-Catch Blocks

### Basic Try-Catch

```java
public class BasicTryCatch {
    public static void main(String[] args) {
        try {
            // Code that might throw an exception
            int result = 10 / 0;
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            // Handle the exception
            System.out.println("Error: Division by zero");
            System.out.println("Exception details: " + e.getMessage());
        }
        
        System.out.println("Program continues after exception handling");
    }
}
```

### Multiple Catch Blocks

```java
public class MultipleCatchBlocks {
    public static void processArray(int[] array, int index) {
        try {
            int value = array[index];
            int result = 100 / value;
            System.out.println("Result: " + result);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Array index out of bounds");
            System.out.println("Index: " + index + ", Array length: " + array.length);
        } catch (ArithmeticException e) {
            System.out.println("Error: Division by zero");
            System.out.println("Value at index " + index + " is zero");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        int[] numbers = {1, 0, 3, 4, 5};
        
        // Test different scenarios
        processArray(numbers, 10);  // ArrayIndexOutOfBoundsException
        processArray(numbers, 1);   // ArithmeticException
        processArray(numbers, 2);   // No exception
    }
}
```

### Try-Catch with Finally

```java
import java.io.FileInputStream;
import java.io.IOException;

public class TryCatchFinally {
    public static void readFileWithFinally(String filename) {
        FileInputStream file = null;
        try {
            file = new FileInputStream(filename);
            int data = file.read();
            System.out.println("Read data: " + data);
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
        }
    }
    
    public static void main(String[] args) {
        readFileWithFinally("test.txt");
    }
}
```

### Try-With-Resources (Java 7+)

```java
import java.io.FileInputStream;
import java.io.IOException;

public class TryWithResources {
    public static void readFileWithResources(String filename) {
        try (FileInputStream file = new FileInputStream(filename)) {
            int data = file.read();
            System.out.println("Read data: " + data);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        // File is automatically closed
    }
    
    public static void main(String[] args) {
        readFileWithResources("test.txt");
    }
}
```

## 4. Throwing Exceptions

### Throw Statement

```java
public class ThrowExample {
    public static void validateAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative: " + age);
        }
        if (age > 150) {
            throw new IllegalArgumentException("Age seems unrealistic: " + age);
        }
        System.out.println("Valid age: " + age);
    }
    
    public static void main(String[] args) {
        try {
            validateAge(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
        
        try {
            validateAge(200);
        } catch (IllegalArgumentException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
        
        validateAge(25);  // Valid age
    }
}
```

### Throws Clause

```java
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ThrowsExample {
    public static void readFile(String filename) throws IOException {
        FileReader reader = new FileReader(filename);
        int data = reader.read();
        reader.close();
        System.out.println("Read data: " + data);
    }
    
    public static void processFile(String filename) throws IOException {
        readFile(filename);
        // Additional processing
    }
    
    public static void main(String[] args) {
        try {
            processFile("test.txt");
        } catch (IOException e) {
            System.out.println("Error processing file: " + e.getMessage());
        }
    }
}
```

## 5. Custom Exceptions

### Creating Custom Exception Classes

```java
// Custom checked exception
public class InsufficientFundsException extends Exception {
    private double amount;
    private double balance;
    
    public InsufficientFundsException(double amount, double balance) {
        super("Insufficient funds. Required: $" + amount + ", Available: $" + balance);
        this.amount = amount;
        this.balance = balance;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public double getBalance() {
        return balance;
    }
}

// Custom unchecked exception
public class InvalidAccountException extends RuntimeException {
    private String accountNumber;
    
    public InvalidAccountException(String accountNumber) {
        super("Invalid account number: " + accountNumber);
        this.accountNumber = accountNumber;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
}
```

### Using Custom Exceptions

```java
public class BankAccount {
    private String accountNumber;
    private double balance;
    
    public BankAccount(String accountNumber, double initialBalance) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new InvalidAccountException(accountNumber);
        }
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException(amount, balance);
        }
        balance -= amount;
        System.out.println("Withdrawn: $" + amount + ", New balance: $" + balance);
    }
    
    public double getBalance() {
        return balance;
    }
}

public class CustomExceptionDemo {
    public static void main(String[] args) {
        try {
            // Test invalid account
            BankAccount invalidAccount = new BankAccount("", 1000);
        } catch (InvalidAccountException e) {
            System.out.println("Account error: " + e.getMessage());
        }
        
        try {
            BankAccount account = new BankAccount("12345", 500);
            
            // Test insufficient funds
            account.withdraw(1000);
        } catch (InsufficientFundsException e) {
            System.out.println("Withdrawal error: " + e.getMessage());
            System.out.println("Required: $" + e.getAmount());
            System.out.println("Available: $" + e.getBalance());
        } catch (InvalidAccountException e) {
            System.out.println("Account error: " + e.getMessage());
        }
    }
}
```

## 6. Exception Propagation

### How Exceptions Propagate

```java
public class ExceptionPropagation {
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
    
    public static void main(String[] args) {
        try {
            method1();
        } catch (Exception e) {
            System.out.println("Exception caught in main: " + e.getMessage());
            e.printStackTrace();  // Print stack trace
        }
    }
}
```

### Exception Chaining

```java
public class ExceptionChaining {
    public static void processData(String data) throws Exception {
        try {
            int number = Integer.parseInt(data);
            System.out.println("Processed number: " + number);
        } catch (NumberFormatException e) {
            // Chain the exception with additional context
            throw new Exception("Failed to process data: " + data, e);
        }
    }
    
    public static void main(String[] args) {
        try {
            processData("abc");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Original cause: " + e.getCause().getMessage());
        }
    }
}
```

## 7. Best Practices

### Exception Handling Best Practices

```java
public class ExceptionBestPractices {
    
    // 1. Be specific with exception types
    public static void goodPractice1() {
        try {
            int[] array = {1, 2, 3};
            System.out.println(array[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Specific exception handling
            System.out.println("Array index out of bounds: " + e.getMessage());
        }
    }
    
    // 2. Don't catch and ignore exceptions
    public static void goodPractice2() {
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            // Log the exception or handle it properly
            System.err.println("Division by zero occurred: " + e.getMessage());
            // Don't just ignore it
        }
    }
    
    // 3. Use appropriate exception types
    public static void validateInput(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }
    }
    
    // 4. Clean up resources properly
    public static void processWithResources() {
        try (FileInputStream file = new FileInputStream("test.txt")) {
            // Process file
            int data = file.read();
            System.out.println("Data: " + data);
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
        // File is automatically closed
    }
    
    // 5. Provide meaningful error messages
    public static void divideNumbers(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException(
                String.format("Cannot divide %d by zero", a));
        }
        int result = a / b;
        System.out.println("Result: " + result);
    }
    
    // 6. Use exception hierarchy appropriately
    public static void processData(String[] data) {
        try {
            for (int i = 0; i < data.length; i++) {
                int value = Integer.parseInt(data[i]);
                System.out.println("Value: " + value);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Array access error: " + e.getMessage());
        } catch (Exception e) {
            // Catch-all for unexpected exceptions
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}
```

### Common Anti-patterns

```java
public class ExceptionAntiPatterns {
    
    // Anti-pattern 1: Catching generic Exception
    public static void badPractice1() {
        try {
            // Some code
            int result = 10 / 0;
        } catch (Exception e) {
            // Too generic - catches everything
            System.out.println("Error occurred");
        }
    }
    
    // Anti-pattern 2: Empty catch block
    public static void badPractice2() {
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            // Empty catch block - exception is ignored
        }
    }
    
    // Anti-pattern 3: Catching and re-throwing without context
    public static void badPractice3() throws Exception {
        try {
            // Some code
            throw new IOException("File not found");
        } catch (IOException e) {
            // Loses original context
            throw new Exception("Error occurred");
        }
    }
    
    // Anti-pattern 4: Using exceptions for control flow
    public static void badPractice4() {
        try {
            for (int i = 0; i < 10; i++) {
                if (i == 5) {
                    throw new RuntimeException("Found 5");
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Found target number");
        }
    }
}
```

## 8. Advanced Exception Handling

### Exception Handling with Lambda Expressions

```java
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class LambdaExceptionHandling {
    
    public static void processList(List<String> numbers) {
        numbers.forEach(handleException(
            number -> {
                int value = Integer.parseInt(number);
                System.out.println("Processed: " + value);
            },
            NumberFormatException.class,
            e -> System.err.println("Invalid number: " + e.getMessage())
        ));
    }
    
    // Generic exception handler for lambdas
    public static <T, E extends Exception> Consumer<T> handleException(
            Consumer<T> consumer, Class<E> exceptionClass, Consumer<E> handler) {
        return item -> {
            try {
                consumer.accept(item);
            } catch (Exception e) {
                if (exceptionClass.isInstance(e)) {
                    handler.accept(exceptionClass.cast(e));
                } else {
                    throw new RuntimeException(e);
                }
            }
        };
    }
    
    public static void main(String[] args) {
        List<String> numbers = Arrays.asList("1", "2", "abc", "4", "5");
        processList(numbers);
    }
}
```

### Exception Handling in Multi-threaded Applications

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadExceptionHandling {
    
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        // Submit task and handle exception
        Future<?> future = executor.submit(() -> {
            try {
                Thread.sleep(1000);
                throw new RuntimeException("Task failed");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Task interrupted", e);
            }
        });
        
        try {
            future.get();  // This will throw the exception
        } catch (Exception e) {
            System.err.println("Task exception: " + e.getCause().getMessage());
        }
        
        executor.shutdown();
    }
}
```

## Summary

This section covered comprehensive exception handling in Java:

- **Exception Types**: Checked vs unchecked exceptions
- **Try-Catch Blocks**: Basic, multiple catch, finally, try-with-resources
- **Throwing Exceptions**: throw statement and throws clause
- **Custom Exceptions**: Creating and using custom exception classes
- **Exception Propagation**: How exceptions flow through the call stack
- **Best Practices**: Proper exception handling techniques
- **Advanced Topics**: Lambda expressions and multi-threading

Proper exception handling is crucial for building robust, maintainable Java applications that can gracefully handle errors and provide meaningful feedback to users. 