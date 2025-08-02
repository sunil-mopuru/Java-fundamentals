package lambda;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;

/**
 * Comprehensive examples demonstrating Lambda Expressions in Java 8
 */
public class LambdaExamples {
    
    public static void main(String[] args) {
        System.out.println("=== Lambda Expressions Examples ===\n");
        
        // Basic examples
        basicLambdaExamples();
        
        // Functional interfaces
        functionalInterfaceExamples();
        
        // Variable capture
        variableCaptureExamples();
        
        // Built-in functional interfaces
        builtInFunctionalInterfaces();
        
        // Practical examples
        practicalExamples();
        
        // Best practices
        bestPracticesExamples();
        
        // Common pitfalls
        commonPitfallsExamples();
        
        // Exercises
        exerciseSolutions();
    }
    
    // Custom functional interfaces
    @FunctionalInterface
    interface MathOperation {
        int operate(int a, int b);
    }
    
    @FunctionalInterface
    interface StringProcessor {
        String process(String input);
    }
    
    @FunctionalInterface
    interface StringTransformer {
        String transform(String input);
    }
    
    @FunctionalInterface
    interface EventHandler {
        void handle(String event);
    }
    
    // Basic lambda examples
    private static void basicLambdaExamples() {
        System.out.println("1. Basic Lambda Examples:");
        
        // Runnable with lambda
        Runnable runnable = () -> System.out.println("Hello from lambda!");
        runnable.run();
        
        // Math operations
        MathOperation add = (a, b) -> a + b;
        MathOperation multiply = (a, b) -> a * b;
        
        System.out.println("5 + 3 = " + add.operate(5, 3));
        System.out.println("5 * 3 = " + multiply.operate(5, 3));
        
        // String processing
        StringProcessor upperCase = String::toUpperCase;
        StringProcessor reverse = s -> new StringBuilder(s).reverse().toString();
        
        System.out.println("Uppercase: " + upperCase.process("hello"));
        System.out.println("Reverse: " + reverse.process("hello"));
        System.out.println();
    }
    
    // Functional interface examples
    private static void functionalInterfaceExamples() {
        System.out.println("2. Functional Interface Examples:");
        
        // Using built-in functional interfaces
        Predicate<String> isLong = s -> s.length() > 5;
        Function<String, Integer> length = String::length;
        Consumer<String> printer = System.out::println;
        Supplier<Double> random = Math::random;
        
        System.out.println("Is 'Hello' long? " + isLong.test("Hello"));
        System.out.println("Length of 'Hello': " + length.apply("Hello"));
        printer.accept("Printing with consumer");
        System.out.println("Random number: " + random.get());
        System.out.println();
    }
    
    // Variable capture examples
    private static void variableCaptureExamples() {
        System.out.println("3. Variable Capture Examples:");
        
        String prefix = "Hello, ";
        int multiplier = 2;
        
        // Capturing local variables
        StringProcessor processor = name -> prefix + name;
        MathOperation operation = (a, b) -> a * b * multiplier;
        
        System.out.println(processor.process("World"));
        System.out.println("3 * 4 * " + multiplier + " = " + operation.operate(3, 4));
        
        // Effectively final demonstration
        int counter = 0;
        Runnable incrementer = () -> System.out.println("Counter: " + counter);
        incrementer.run();
        
        // Uncommenting the next line would cause compilation error
        // counter = 1; // Error: Variable used in lambda should be final or effectively final
        System.out.println();
    }
    
    // Built-in functional interfaces
    private static void builtInFunctionalInterfaces() {
        System.out.println("4. Built-in Functional Interfaces:");
        
        // Predicate examples
        Predicate<String> isLong = s -> s.length() > 5;
        Predicate<String> startsWithA = s -> s.startsWith("A");
        Predicate<String> isLongAndStartsWithA = isLong.and(startsWithA);
        
        System.out.println("Is 'Alice' long and starts with A? " + 
                          isLongAndStartsWithA.test("Alice"));
        
        // Function examples
        Function<String, Integer> length = String::length;
        Function<Integer, String> toString = Object::toString;
        Function<String, String> upperCase = String::toUpperCase;
        
        // Function composition
        Function<String, String> lengthThenUpper = length.andThen(toString).andThen(upperCase);
        System.out.println("Length as string in upper case: " + lengthThenUpper.apply("Hello"));
        
        // BiFunction examples
        BiFunction<String, String, String> concat = String::concat;
        BiFunction<Integer, Integer, Integer> add = Integer::sum;
        
        System.out.println("Concatenation: " + concat.apply("Hello ", "World"));
        System.out.println("Sum: " + add.apply(5, 3));
        System.out.println();
    }
    
    // Practical examples
    private static void practicalExamples() {
        System.out.println("5. Practical Examples:");
        
        // Sorting with lambda
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        System.out.println("Original list: " + names);
        
        // Sort by length
        names.sort((a, b) -> a.length() - b.length());
        System.out.println("Sorted by length: " + names);
        
        // Sort alphabetically
        names.sort(String::compareTo);
        System.out.println("Sorted alphabetically: " + names);
        
        // Event handling
        EventHandler logHandler = event -> System.out.println("Logging: " + event);
        EventHandler emailHandler = event -> System.out.println("Email: " + event);
        EventHandler smsHandler = event -> System.out.println("SMS: " + event);
        
        String event = "User login";
        logHandler.handle(event);
        emailHandler.handle(event);
        smsHandler.handle(event);
        
        // Mathematical operations
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b;
        BiFunction<Integer, Integer, Integer> power = (a, b) -> (int) Math.pow(a, b);
        
        System.out.println("5 + 3 = " + add.apply(5, 3));
        System.out.println("5 * 3 = " + multiply.apply(5, 3));
        System.out.println("2^3 = " + power.apply(2, 3));
        System.out.println();
    }
    
    // Best practices examples
    private static void bestPracticesExamples() {
        System.out.println("6. Best Practices Examples:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        
        // Use method references when possible
        names.forEach(System.out::println);
        
        // Extract complex logic
        names.forEach(LambdaExamples::processLongName);
        
        // Use descriptive parameter names
        BiFunction<String, String, String> concat = (first, second) -> first + second;
        System.out.println("Concatenation: " + concat.apply("Hello", "World"));
        System.out.println();
    }
    
    // Common pitfalls examples
    private static void commonPitfallsExamples() {
        System.out.println("7. Common Pitfalls Examples:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        
        // Use AtomicInteger for counters in lambda
        AtomicInteger counter = new AtomicInteger(0);
        names.forEach(name -> counter.incrementAndGet());
        System.out.println("Counter: " + counter.get());
        
        // Avoid object creation in lambda
        StringBuilder sb = new StringBuilder();
        names.forEach(sb::append);
        System.out.println("Concatenated: " + sb.toString());
        System.out.println();
    }
    
    // Helper method for best practices
    private static void processLongName(String name) {
        if (name.length() > 4) {
            String upperName = name.toUpperCase();
            System.out.println("Long name: " + upperName);
        }
    }
    
    // Exercise solutions
    private static void exerciseSolutions() {
        System.out.println("8. Exercise Solutions:");
        
        // Exercise 1: Basic Lambda Expressions
        System.out.println("Exercise 1 - Basic Lambda Expressions:");
        BiFunction<Integer, Integer, Integer> sum = (a, b) -> a + b;
        Predicate<String> isEmpty = String::isEmpty;
        Consumer<String> upperPrinter = s -> System.out.println(s.toUpperCase());
        Supplier<Integer> random100 = () -> (int) (Math.random() * 100) + 1;
        
        System.out.println("Sum: " + sum.apply(5, 3));
        System.out.println("Is empty: " + isEmpty.test(""));
        upperPrinter.accept("hello world");
        System.out.println("Random 1-100: " + random100.get());
        
        // Exercise 2: Custom Functional Interface
        System.out.println("\nExercise 2 - String Transformer:");
        StringTransformer toUpper = String::toUpperCase;
        StringTransformer reverse = s -> new StringBuilder(s).reverse().toString();
        StringTransformer addPrefix = s -> "PREFIX_" + s;
        StringTransformer removeVowels = s -> s.replaceAll("[aeiouAEIOU]", "");
        
        String test = "hello";
        System.out.println("Original: " + test);
        System.out.println("Uppercase: " + toUpper.transform(test));
        System.out.println("Reverse: " + reverse.transform(test));
        System.out.println("With prefix: " + addPrefix.transform(test));
        System.out.println("No vowels: " + removeVowels.transform(test));
        
        // Exercise 3: Collection Processing
        System.out.println("\nExercise 3 - Collection Processing:");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        Predicate<Integer> isOdd = n -> n % 2 != 0;
        Function<Integer, Integer> square = n -> n * n;
        
        int sumOfSquaredOdds = numbers.stream()
            .filter(isOdd)
            .peek(n -> System.out.println("Odd number: " + n))
            .map(square)
            .peek(n -> System.out.println("Squared: " + n))
            .reduce(0, Integer::sum);
        
        System.out.println("Sum of squared odd numbers: " + sumOfSquaredOdds);
        
        // Exercise 4: Event System
        System.out.println("\nExercise 4 - Event System:");
        EventDispatcher dispatcher = new EventDispatcher();
        
        dispatcher.registerHandler("log", event -> System.out.println("Log: " + event));
        dispatcher.registerHandler("email", event -> System.out.println("Email: " + event));
        dispatcher.registerHandler("database", event -> System.out.println("Database: " + event));
        
        dispatcher.triggerEvent("User login");
        dispatcher.triggerEvent("User logout");
        
        // Exercise 5: Calculator
        System.out.println("\nExercise 5 - Calculator:");
        Calculator calculator = new Calculator();
        
        System.out.println("5 + 3 = " + calculator.calculate(5, 3, (a, b) -> a + b));
        System.out.println("5 - 3 = " + calculator.calculate(5, 3, (a, b) -> a - b));
        System.out.println("5 * 3 = " + calculator.calculate(5, 3, (a, b) -> a * b));
        System.out.println("6 / 2 = " + calculator.calculate(6, 2, (a, b) -> a / b));
        System.out.println("5 / 0 = " + calculator.calculate(5, 0, (a, b) -> a / b));
    }
    
    // Event dispatcher for exercise 4
    static class EventDispatcher {
        private final java.util.Map<String, EventHandler> handlers = new java.util.HashMap<>();
        
        public void registerHandler(String type, EventHandler handler) {
            handlers.put(type, handler);
        }
        
        public void triggerEvent(String event) {
            System.out.println("Triggering event: " + event);
            handlers.values().forEach(handler -> handler.handle(event));
        }
    }
    
    // Calculator for exercise 5
    static class Calculator {
        public String calculate(int a, int b, BiFunction<Integer, Integer, Integer> operation) {
            try {
                int result = operation.apply(a, b);
                return String.valueOf(result);
            } catch (ArithmeticException e) {
                return "Error: " + e.getMessage();
            }
        }
    }
} 