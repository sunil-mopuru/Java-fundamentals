package defaultmethods;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Comprehensive examples demonstrating Java 8 Default Methods
 * This class showcases various aspects of default methods including
 * basic usage, inheritance resolution, practical patterns, and real-world applications.
 */
public class DefaultMethodExamples {
    
    public static void main(String[] args) {
        System.out.println("=== Java 8 Default Methods Deep Dive ===\n");
        
        basicDefaultMethodExamples();
        inheritanceAndResolution();
        practicalExamples();
        designPatterns();
        exerciseSolutions();
    }
    
    // ==================== BASIC EXAMPLES ====================
    
    private static void basicDefaultMethodExamples() {
        System.out.println("1. Basic Default Method Examples:");
        
        // Example 1: Simple interface with default method
        SimpleInterface simple = new SimpleImplementation();
        simple.abstractMethod();
        simple.defaultMethod();
        SimpleInterface.staticMethod();
        
        // Example 2: Multiple inheritance of behavior
        Duck duck = new Duck();
        duck.fly();
        duck.swim();
        
        // Example 3: Functional interface with default methods
        Predicate<String> longName = s -> s.length() > 5;
        Predicate<String> startsWithA = s -> s.startsWith("A");
        
        Predicate<String> combined = longName.and(startsWithA);
        System.out.println("Combined predicate: " + combined.test("Alice"));
        System.out.println("Negated predicate: " + longName.negate().test("Bob"));
        
        System.out.println();
    }
    
    // ==================== INHERITANCE AND RESOLUTION ====================
    
    private static void inheritanceAndResolution() {
        System.out.println("2. Inheritance and Method Resolution:");
        
        // Example 1: Method resolution with multiple interfaces
        MultipleInterfaceClass multi = new MultipleInterfaceClass();
        multi.conflictingMethod();
        
        // Example 2: Calling specific interface methods
        InterfaceResolver resolver = new InterfaceResolver();
        resolver.resolveMethod();
        
        // Example 3: Interface hierarchy resolution
        HierarchyExample hierarchy = new HierarchyExample();
        hierarchy.method();
        
        System.out.println();
    }
    
    // ==================== PRACTICAL EXAMPLES ====================
    
    private static void practicalExamples() {
        System.out.println("3. Practical Examples:");
        
        // Example 1: Calculator with default methods
        Calculator calculator = new BasicCalculator();
        System.out.println("Add: " + calculator.add(10, 5));
        System.out.println("Multiply: " + calculator.multiply(10, 5));
        System.out.println("Power: " + calculator.power(2, 3));
        System.out.println("Chain: " + calculator.add(5, 3).multiply(2).power(2));
        
        // Example 2: Logger with default methods
        Logger logger = new ConsoleLogger();
        logger.log("Basic message");
        logger.info("Info message");
        logger.error("Error message");
        logger.debug("Debug message");
        
        // Example 3: Cache with default methods
        Cache<String, Integer> cache = new SimpleCache<>();
        cache.put("key1", 100);
        cache.put("key2", 200);
        
        System.out.println("Get with default: " + cache.getOrDefault("key3", 0));
        System.out.println("Put if absent: " + cache.putIfAbsent("key1", 300));
        System.out.println("Remove if present: " + cache.removeIfPresent("key2", 200));
        
        System.out.println();
    }
    
    // ==================== DESIGN PATTERNS ====================
    
    private static void designPatterns() {
        System.out.println("4. Design Patterns with Default Methods:");
        
        // Example 1: Template Method Pattern
        DataProcessor<String> processor = new StringProcessor();
        String result = processor.processWithLogging("Hello World");
        System.out.println("Processed result: " + result);
        
        // Example 2: Decorator Pattern
        Component component = new SimpleComponent();
        Component decorated = component.withLogging();
        decorated.operation();
        
        // Example 3: Chain of Responsibility
        Handler handler1 = new Handler1();
        Handler handler2 = new Handler2();
        Handler handler3 = new Handler3();
        
        Handler chain = handler1.chain(handler2).chain(handler3);
        System.out.println("Chain result: " + chain.handle("request3"));
        
        // Example 4: Observer Pattern
        Observable observable = new SimpleObservable();
        Observer observer1 = new SimpleObserver("Observer1");
        Observer observer2 = new SimpleObserver("Observer2");
        
        observable.addObserverAndNotify(observer1);
        observable.addObserver(observer2);
        observable.notifyObservers();
        
        System.out.println();
    }
    
    // ==================== EXERCISE SOLUTIONS ====================
    
    private static void exerciseSolutions() {
        System.out.println("5. Exercise Solutions:");
        
        // Exercise 1: Enhanced Calculator
        EnhancedCalculator calc = new EnhancedCalculator();
        System.out.println("Enhanced calculator:");
        System.out.println("Add: " + calc.add(10, 5));
        System.out.println("Subtract: " + calc.subtract(10, 5));
        System.out.println("Multiply: " + calc.multiply(10, 5));
        System.out.println("Divide: " + calc.divide(10, 5));
        System.out.println("Power: " + calc.power(2, 3));
        System.out.println("Chain: " + calc.add(5, 3).multiply(2).power(2));
        
        // Exercise 2: Enhanced Logger
        EnhancedLogger enhancedLogger = new EnhancedLogger();
        enhancedLogger.log("Basic log");
        enhancedLogger.info("Info message");
        enhancedLogger.error("Error message");
        enhancedLogger.debug("Debug message");
        enhancedLogger.format("Formatted message: %s", "Hello");
        
        // Exercise 3: Enhanced Cache
        EnhancedCache<String, String> enhancedCache = new EnhancedCache<>();
        enhancedCache.put("name", "John");
        enhancedCache.put("age", "30");
        
        System.out.println("Get with default: " + enhancedCache.getOrDefault("city", "Unknown"));
        System.out.println("Put if absent: " + enhancedCache.putIfAbsent("name", "Jane"));
        System.out.println("Remove if present: " + enhancedCache.removeIfPresent("age", "30"));
        enhancedCache.clearAll();
        System.out.println("After clear: " + enhancedCache.get("name"));
        
        // Exercise 4: Multiple Interface Resolution
        MultipleInterfaceResolver resolver = new MultipleInterfaceResolver();
        resolver.method();
        
        System.out.println();
    }
    
    // ==================== INTERFACE DEFINITIONS ====================
    
    // Basic interface with default method
    interface SimpleInterface {
        void abstractMethod();
        
        default void defaultMethod() {
            System.out.println("Default implementation from SimpleInterface");
        }
        
        static void staticMethod() {
            System.out.println("Static method from SimpleInterface");
        }
    }
    
    // Multiple inheritance example
    interface Flyable {
        default void fly() {
            System.out.println("Flying...");
        }
    }
    
    interface Swimmable {
        default void swim() {
            System.out.println("Swimming...");
        }
    }
    
    // Calculator interface with default methods
    interface Calculator {
        double add(double a, double b);
        double subtract(double a, double b);
        
        default double multiply(double a, double b) {
            return a * b;
        }
        
        default double divide(double a, double b) {
            if (b == 0) {
                throw new ArithmeticException("Division by zero");
            }
            return a / b;
        }
        
        default double power(double base, double exponent) {
            return Math.pow(base, exponent);
        }
        
        default Calculator add(double a, double b) {
            return new Calculator() {
                @Override
                public double add(double x, double y) {
                    return Calculator.this.add(x, y);
                }
                
                @Override
                public double subtract(double x, double y) {
                    return Calculator.this.subtract(x, y);
                }
            };
        }
        
        default Calculator multiply(double a, double b) {
            return new Calculator() {
                @Override
                public double add(double x, double y) {
                    return Calculator.this.add(x, y);
                }
                
                @Override
                public double subtract(double x, double y) {
                    return Calculator.this.subtract(x, y);
                }
            };
        }
        
        default Calculator power(double base, double exponent) {
            return new Calculator() {
                @Override
                public double add(double x, double y) {
                    return Calculator.this.add(x, y);
                }
                
                @Override
                public double subtract(double x, double y) {
                    return Calculator.this.subtract(x, y);
                }
            };
        }
    }
    
    // Logger interface with default methods
    interface Logger {
        void log(String message);
        
        default void info(String message) {
            log("[INFO] " + message);
        }
        
        default void error(String message) {
            log("[ERROR] " + message);
        }
        
        default void debug(String message) {
            log("[DEBUG] " + message);
        }
    }
    
    // Cache interface with default methods
    interface Cache<K, V> {
        V get(K key);
        void put(K key, V value);
        
        default V getOrDefault(K key, V defaultValue) {
            V value = get(key);
            return value != null ? value : defaultValue;
        }
        
        default V putIfAbsent(K key, V value) {
            V existing = get(key);
            if (existing == null) {
                put(key, value);
                return value;
            }
            return existing;
        }
        
        default boolean removeIfPresent(K key, V value) {
            V existing = get(key);
            if (Objects.equals(existing, value)) {
                // Assuming we have a remove method
                return true;
            }
            return false;
        }
        
        default void clearAll() {
            // Implementation would depend on the specific cache implementation
        }
    }
    
    // Design pattern interfaces
    interface DataProcessor<T> {
        T process(T data);
        
        default T processWithLogging(T data) {
            System.out.println("Processing: " + data);
            T result = process(data);
            System.out.println("Result: " + result);
            return result;
        }
    }
    
    interface Component {
        void operation();
        
        default Component withLogging() {
            return () -> {
                System.out.println("Before operation");
                operation();
                System.out.println("After operation");
            };
        }
    }
    
    interface Handler {
        boolean handle(String request);
        
        default Handler chain(Handler next) {
            return request -> handle(request) || next.handle(request);
        }
    }
    
    interface Observable {
        void addObserver(Observer observer);
        void removeObserver(Observer observer);
        void notifyObservers();
        
        default void addObserverAndNotify(Observer observer) {
            addObserver(observer);
            notifyObservers();
        }
    }
    
    interface Observer {
        void update(String message);
    }
    
    // Multiple interface resolution interfaces
    interface InterfaceA {
        default void conflictingMethod() {
            System.out.println("InterfaceA's conflicting method");
        }
    }
    
    interface InterfaceB {
        default void conflictingMethod() {
            System.out.println("InterfaceB's conflicting method");
        }
    }
    
    interface InterfaceC extends InterfaceA {
        default void conflictingMethod() {
            System.out.println("InterfaceC's conflicting method");
        }
    }
    
    // ==================== IMPLEMENTATION CLASSES ====================
    
    static class SimpleImplementation implements SimpleInterface {
        @Override
        public void abstractMethod() {
            System.out.println("Abstract method implementation");
        }
    }
    
    static class Duck implements Flyable, Swimmable {
        // Inherits both fly() and swim() methods
    }
    
    static class MultipleInterfaceClass implements InterfaceA, InterfaceB {
        @Override
        public void conflictingMethod() {
            System.out.println("Resolved conflicting method in class");
            // Call specific interface method
            InterfaceA.super.conflictingMethod();
        }
    }
    
    static class InterfaceResolver implements InterfaceA, InterfaceB {
        @Override
        public void conflictingMethod() {
            InterfaceA.super.conflictingMethod();
            InterfaceB.super.conflictingMethod();
            System.out.println("Additional logic in resolver");
        }
        
        public void resolveMethod() {
            conflictingMethod();
        }
    }
    
    static class HierarchyExample implements InterfaceA, InterfaceB, InterfaceC {
        @Override
        public void conflictingMethod() {
            InterfaceC.super.conflictingMethod(); // Most specific wins
        }
        
        public void method() {
            conflictingMethod();
        }
    }
    
    static class BasicCalculator implements Calculator {
        @Override
        public double add(double a, double b) {
            return a + b;
        }
        
        @Override
        public double subtract(double a, double b) {
            return a - b;
        }
    }
    
    static class ConsoleLogger implements Logger {
        @Override
        public void log(String message) {
            System.out.println(message);
        }
    }
    
    static class SimpleCache<K, V> implements Cache<K, V> {
        private final Map<K, V> map = new HashMap<>();
        
        @Override
        public V get(K key) {
            return map.get(key);
        }
        
        @Override
        public void put(K key, V value) {
            map.put(key, value);
        }
        
        @Override
        public boolean removeIfPresent(K key, V value) {
            V existing = map.get(key);
            if (Objects.equals(existing, value)) {
                map.remove(key);
                return true;
            }
            return false;
        }
        
        @Override
        public void clearAll() {
            map.clear();
        }
    }
    
    static class StringProcessor implements DataProcessor<String> {
        @Override
        public String process(String data) {
            return data.toUpperCase();
        }
    }
    
    static class SimpleComponent implements Component {
        @Override
        public void operation() {
            System.out.println("Performing simple operation");
        }
    }
    
    static class Handler1 implements Handler {
        @Override
        public boolean handle(String request) {
            if (request.equals("request1")) {
                System.out.println("Handler1 handled: " + request);
                return true;
            }
            return false;
        }
    }
    
    static class Handler2 implements Handler {
        @Override
        public boolean handle(String request) {
            if (request.equals("request2")) {
                System.out.println("Handler2 handled: " + request);
                return true;
            }
            return false;
        }
    }
    
    static class Handler3 implements Handler {
        @Override
        public boolean handle(String request) {
            if (request.equals("request3")) {
                System.out.println("Handler3 handled: " + request);
                return true;
            }
            return false;
        }
    }
    
    static class SimpleObservable implements Observable {
        private final List<Observer> observers = new ArrayList<>();
        
        @Override
        public void addObserver(Observer observer) {
            observers.add(observer);
        }
        
        @Override
        public void removeObserver(Observer observer) {
            observers.remove(observer);
        }
        
        @Override
        public void notifyObservers() {
            for (Observer observer : observers) {
                observer.update("Notification from SimpleObservable");
            }
        }
    }
    
    static class SimpleObserver implements Observer {
        private final String name;
        
        public SimpleObserver(String name) {
            this.name = name;
        }
        
        @Override
        public void update(String message) {
            System.out.println(name + " received: " + message);
        }
    }
    
    // ==================== EXERCISE SOLUTION CLASSES ====================
    
    static class EnhancedCalculator implements Calculator {
        @Override
        public double add(double a, double b) {
            return a + b;
        }
        
        @Override
        public double subtract(double a, double b) {
            return a - b;
        }
    }
    
    static class EnhancedLogger implements Logger {
        @Override
        public void log(String message) {
            System.out.println(message);
        }
        
        public void format(String format, Object... args) {
            log(String.format(format, args));
        }
    }
    
    static class EnhancedCache<K, V> implements Cache<K, V> {
        private final Map<K, V> map = new HashMap<>();
        
        @Override
        public V get(K key) {
            return map.get(key);
        }
        
        @Override
        public void put(K key, V value) {
            map.put(key, value);
        }
        
        @Override
        public boolean removeIfPresent(K key, V value) {
            V existing = map.get(key);
            if (Objects.equals(existing, value)) {
                map.remove(key);
                return true;
            }
            return false;
        }
        
        @Override
        public void clearAll() {
            map.clear();
        }
    }
    
    static class MultipleInterfaceResolver implements InterfaceA, InterfaceB, InterfaceC {
        @Override
        public void conflictingMethod() {
            // Resolve by calling the most specific interface
            InterfaceC.super.conflictingMethod();
            System.out.println("Additional resolution logic");
        }
        
        public void method() {
            conflictingMethod();
        }
    }
} 