package optional;

import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Comprehensive examples demonstrating Optional Class in Java 8
 */
public class OptionalExamples {
    
    public static void main(String[] args) {
        System.out.println("=== Optional Class Examples ===\n");
        
        // Creating Optional objects
        creatingOptionalObjects();
        
        // Optional methods
        optionalMethods();
        
        // Working with Optional
        workingWithOptional();
        
        // Optional with streams
        optionalWithStreams();
        
        // Best practices
        bestPracticesExamples();
        
        // Common patterns
        commonPatternsExamples();
        
        // Practical examples
        practicalExamples();
        
        // Common pitfalls
        commonPitfallsExamples();
        
        // Exercises
        exerciseSolutions();
    }
    
    // Creating Optional objects
    private static void creatingOptionalObjects() {
        System.out.println("1. Creating Optional Objects:");
        
        // Optional.empty()
        Optional<String> emptyOptional = Optional.empty();
        System.out.println("Empty optional: " + emptyOptional);
        
        // Optional.of()
        Optional<String> optional = Optional.of("Hello");
        System.out.println("Optional with value: " + optional);
        
        // Optional.ofNullable()
        Optional<String> optional1 = Optional.ofNullable("Hello");
        Optional<String> optional2 = Optional.ofNullable(null);
        System.out.println("Optional with nullable (present): " + optional1);
        System.out.println("Optional with nullable (empty): " + optional2);
        
        // Optional.ofNullable() with method calls
        User user = new User();
        Optional<String> nameOptional = Optional.ofNullable(user.getName());
        System.out.println("Optional from method call: " + nameOptional);
        
        System.out.println();
    }
    
    // Optional methods
    private static void optionalMethods() {
        System.out.println("2. Optional Methods:");
        
        Optional<String> optional = Optional.of("Hello");
        
        // Checking presence
        System.out.println("Is present: " + optional.isPresent());
        System.out.println("Is empty: " + optional.isEmpty());
        
        // Getting values
        String value = optional.get();
        System.out.println("Get value: " + value);
        
        String valueOrDefault = optional.orElse("Default Value");
        System.out.println("Or else: " + valueOrDefault);
        
        String valueOrSupplied = optional.orElseGet(() -> "Supplied Value");
        System.out.println("Or else get: " + valueOrSupplied);
        
        // Conditional actions
        optional.ifPresent(val -> System.out.println("If present: " + val));
        
        optional.ifPresentOrElse(
            val -> System.out.println("If present or else: " + val),
            () -> System.out.println("No value present")
        );
        
        // Transforming values
        Optional<String> upperCase = optional.map(String::toUpperCase);
        System.out.println("Mapped to uppercase: " + upperCase);
        
        Optional<String> transformed = optional.flatMap(value -> 
            Optional.of(value.toUpperCase())
        );
        System.out.println("Flat mapped: " + transformed);
        
        Optional<String> filtered = optional.filter(value -> value.length() > 3);
        System.out.println("Filtered: " + filtered);
        
        System.out.println();
    }
    
    // Working with Optional
    private static void workingWithOptional() {
        System.out.println("3. Working with Optional:");
        
        // Basic usage patterns
        String input = "hello";
        String result = getWithOptional(input);
        System.out.println("With Optional: " + result);
        
        // Nested null handling
        User user = new User("Alice", new Address("123 Main St", "New York", "USA"));
        String city = getNestedOptional(user);
        System.out.println("Nested Optional: " + city);
        
        // Optional with collections
        List<String> list = Arrays.asList("Alice", "Bob", "Charlie");
        Optional<String> firstMatch = findFirstMatch(list, "A");
        System.out.println("First match: " + firstMatch);
        
        Optional<Integer> max = findMax(Arrays.asList(1, 3, 2, 5, 4));
        System.out.println("Max value: " + max);
        
        Optional<String> element = getElementAt(list, 1);
        System.out.println("Element at index 1: " + element);
        
        // Optional with exceptions
        Optional<Integer> parsed = parseInteger("123");
        System.out.println("Parsed integer: " + parsed);
        
        Optional<Integer> parsedInvalid = parseInteger("abc");
        System.out.println("Parsed invalid: " + parsedInvalid);
        
        System.out.println();
    }
    
    // Optional with streams
    private static void optionalWithStreams() {
        System.out.println("4. Optional with Streams:");
        
        List<Optional<String>> optionals = Arrays.asList(
            Optional.of("Alice"),
            Optional.empty(),
            Optional.of("Bob"),
            Optional.empty(),
            Optional.of("Charlie")
        );
        
        // Filter out empty optionals
        List<String> present = filterPresent(optionals);
        System.out.println("Present values: " + present);
        
        // Process with optional
        List<String> strings = Arrays.asList("hello", "world", "java", "programming");
        List<String> processed = processWithOptional(strings);
        System.out.println("Processed: " + processed);
        
        // Find and transform
        Optional<String> found = findAndTransform(strings, "j");
        System.out.println("Found and transformed: " + found);
        
        // Convert Optional to Stream
        Optional<String> optional = Optional.of("Hello");
        Stream<String> stream = optionalToStream(optional);
        System.out.println("Optional to stream: " + stream.collect(Collectors.toList()));
        
        // Process multiple optionals
        List<String> processedOptionals = processOptionals(optionals);
        System.out.println("Processed optionals: " + processedOptionals);
        
        System.out.println();
    }
    
    // Best practices examples
    private static void bestPracticesExamples() {
        System.out.println("5. Best Practices Examples:");
        
        // Don't use Optional for everything
        User user = new User("Alice", new Address("123 Main St", "New York", "USA"));
        processUser(user); // Good - regular parameter
        
        // Avoid Optional.get() without checking
        Optional<String> optional = Optional.of("Hello");
        String value = optional.orElse("Default"); // Good - safe method
        System.out.println("Safe value: " + value);
        
        // Use method references when possible
        Optional<String> upperCase = optional.map(String::toUpperCase);
        System.out.println("Method reference: " + upperCase);
        
        // Prefer orElseGet() for expensive operations
        String expensiveValue = optional.orElseGet(() -> expensiveOperation());
        System.out.println("Expensive operation: " + expensiveValue);
        
        // Use Optional for return types
        Optional<String> found = findUserEmail("user123");
        System.out.println("Found email: " + found);
        
        System.out.println();
    }
    
    // Common patterns examples
    private static void commonPatternsExamples() {
        System.out.println("6. Common Patterns Examples:");
        
        // Null-safe navigation
        Person person = new Person("Alice", new Address("123 Main St", "New York", "USA"));
        Optional<String> city = getCityOptional(person);
        System.out.println("City: " + city);
        
        // Default values
        Optional<String> primary = Optional.empty();
        Optional<String> secondary = Optional.of("Secondary");
        Optional<String> tertiary = Optional.of("Tertiary");
        
        String result = getValueWithFallbacks(primary, secondary, tertiary);
        System.out.println("Value with fallbacks: " + result);
        
        // Exception handling
        Optional<String> value = Optional.empty();
        try {
            String required = getOrThrow(value, "Value is required");
        } catch (IllegalArgumentException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        
        // Collection processing
        List<String> list = Arrays.asList("Alice", "Bob", "Charlie");
        Optional<String> firstMatch = findFirstMatch(list, "A");
        System.out.println("First match: " + firstMatch);
        
        Optional<String> safeGet = safeGet(list, 1);
        System.out.println("Safe get: " + safeGet);
        
        List<Optional<String>> optionals = Arrays.asList(
            Optional.of("Alice"),
            Optional.empty(),
            Optional.of("Bob")
        );
        List<String> present = processPresent(optionals);
        System.out.println("Present values: " + present);
        
        System.out.println();
    }
    
    // Practical examples
    private static void practicalExamples() {
        System.out.println("7. Practical Examples:");
        
        // User management example
        userManagementExample();
        
        // Configuration example
        configurationExample();
        
        // Data processing example
        dataProcessingExample();
        
        System.out.println();
    }
    
    private static void userManagementExample() {
        System.out.println("User Management Example:");
        
        UserManagementExample manager = new UserManagementExample();
        
        // Add some users
        Address address1 = new Address("123 Main St", "New York", "USA");
        Address address2 = new Address("456 Oak Ave", "London", "UK");
        
        User user1 = new User("1", "Alice", "alice@example.com", address1);
        User user2 = new User("2", "Bob", null, address2);
        User user3 = new User("3", "Charlie", "charlie@example.com", null);
        
        manager.addUser(user1);
        manager.addUser(user2);
        manager.addUser(user3);
        
        // Test the methods
        System.out.println("User 1 city: " + manager.getUserCity("1"));
        System.out.println("User 2 email: " + manager.getUserEmailOrDefault("2"));
        System.out.println("User 1 address: " + manager.getUserFullAddress("1"));
        
        try {
            manager.getUserFullAddress("999"); // Non-existent user
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("Users in New York: " + manager.findUsersByCity("New York"));
    }
    
    private static void configurationExample() {
        System.out.println("\nConfiguration Example:");
        
        Configuration config = new Configuration();
        
        // Set some properties
        config.setProperty("server.port", "8080");
        config.setProperty("server.host", "localhost");
        config.setProperty("debug.enabled", "true");
        config.setProperty("allowed.users", "admin,user,guest");
        
        // Get properties with defaults
        System.out.println("Server port: " + config.getIntProperty("server.port", 3000));
        System.out.println("Server host: " + config.getPropertyOrDefault("server.host", "127.0.0.1"));
        System.out.println("Debug enabled: " + config.getBooleanProperty("debug.enabled", false));
        System.out.println("Allowed users: " + config.getListProperty("allowed.users", ","));
        
        // Non-existent properties
        System.out.println("Non-existent port: " + config.getIntProperty("non.existent.port", 3000));
        System.out.println("Non-existent host: " + config.getPropertyOrDefault("non.existent.host", "default"));
    }
    
    private static void dataProcessingExample() {
        System.out.println("\nData Processing Example:");
        
        DataProcessor processor = new DataProcessor();
        
        List<String> inputs = Arrays.asList(
            "hello",
            "  world  ",
            "",
            null,
            "very long string that exceeds the maximum allowed length and should be filtered out",
            "java",
            "programming"
        );
        
        System.out.println("Processed batch: " + processor.processBatch(inputs));
        System.out.println("First valid: " + processor.findFirstValid(inputs));
        System.out.println("Aggregated: " + processor.aggregateResults(inputs));
    }
    
    // Common pitfalls examples
    private static void commonPitfallsExamples() {
        System.out.println("8. Common Pitfalls Examples:");
        
        // Using Optional.get() without checking
        Optional<String> optional = Optional.of("Hello");
        String value = optional.orElse("Default"); // Good - safe method
        System.out.println("Safe value: " + value);
        
        // Creating Optional of null
        Optional<String> nullable = Optional.ofNullable(null); // Good
        System.out.println("Nullable optional: " + nullable);
        
        // Overusing Optional
        User user = new User("Alice", new Address("123 Main St", "New York", "USA"));
        processUser(user); // Good - regular parameter
        
        // Ignoring empty optionals
        Optional<String> empty = Optional.empty();
        empty.ifPresentOrElse(
            System.out::println,
            () -> System.out.println("No value present")
        );
        
        // Not using Optional for return types
        Optional<String> found = findUser("user123");
        System.out.println("Found user: " + found);
        
        System.out.println();
    }
    
    // Exercise solutions
    private static void exerciseSolutions() {
        System.out.println("9. Exercise Solutions:");
        
        // Exercise 1: Basic Optional Operations
        System.out.println("Exercise 1 - Basic Optional Operations:");
        Optional<String> optional = Optional.ofNullable("hello");
        System.out.println("Optional: " + optional);
        
        String value = optional.orElse("default");
        System.out.println("Value or default: " + value);
        
        Optional<String> transformed = optional.map(String::toUpperCase);
        System.out.println("Transformed: " + transformed);
        
        optional.ifPresent(val -> System.out.println("Present: " + val));
        
        Optional<String> chained = optional
            .map(String::toUpperCase)
            .filter(s -> s.length() > 3)
            .map(s -> "PREFIX_" + s);
        System.out.println("Chained: " + chained);
        
        // Exercise 2: User Profile System
        System.out.println("\nExercise 2 - User Profile System:");
        UserProfile profile = new UserProfile("Alice", "alice@example.com", 
            new ProfileAddress("123 Main St", "New York", "USA"));
        
        String name = profile.getName().orElse("Unknown");
        String email = profile.getEmail().orElse("No email");
        String city = profile.getCity().orElse("Unknown city");
        String transformedName = profile.getName()
            .map(String::toUpperCase)
            .orElse("UNKNOWN");
        
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("City: " + city);
        System.out.println("Transformed name: " + transformedName);
        
        // Exercise 3: Configuration Reader
        System.out.println("\nExercise 3 - Configuration Reader:");
        ConfigurationReader reader = new ConfigurationReader();
        reader.setProperty("port", "8080");
        reader.setProperty("host", "localhost");
        reader.setProperty("debug", "true");
        
        int port = reader.getIntProperty("port", 3000);
        String host = reader.getStringProperty("host", "127.0.0.1");
        boolean debug = reader.getBooleanProperty("debug", false);
        int nonExistent = reader.getIntProperty("non.existent", 3000);
        
        System.out.println("Port: " + port);
        System.out.println("Host: " + host);
        System.out.println("Debug: " + debug);
        System.out.println("Non-existent: " + nonExistent);
        
        // Exercise 4: Data Validation Pipeline
        System.out.println("\nExercise 4 - Data Validation Pipeline:");
        DataValidator validator = new DataValidator();
        
        List<String> inputs = Arrays.asList("hello", "", null, "very long string", "java");
        List<ValidationResult> results = validator.validateBatch(inputs);
        
        results.forEach(result -> {
            if (result.isValid()) {
                System.out.println("Valid: " + result.getValue());
            } else {
                System.out.println("Invalid: " + result.getError());
            }
        });
        
        // Exercise 5: Optional with Streams
        System.out.println("\nExercise 5 - Optional with Streams:");
        List<Optional<String>> optionals = Arrays.asList(
            Optional.of("Alice"),
            Optional.empty(),
            Optional.of("Bob"),
            Optional.empty(),
            Optional.of("Charlie")
        );
        
        List<String> present = optionals.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        System.out.println("Present values: " + present);
        
        Optional<String> firstMatch = optionals.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(s -> s.startsWith("A"))
            .findFirst();
        System.out.println("First match: " + firstMatch);
        
        List<String> transformed = optionals.stream()
            .flatMap(Optional::stream)
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("Transformed: " + transformed);
        
        String aggregated = optionals.stream()
            .flatMap(Optional::stream)
            .collect(Collectors.joining(", "));
        System.out.println("Aggregated: " + aggregated);
    }
    
    // Helper methods
    private static String getWithOptional(String input) {
        return Optional.ofNullable(input)
            .map(String::toUpperCase)
            .orElse("DEFAULT");
    }
    
    private static String getNestedOptional(User user) {
        return Optional.ofNullable(user)
            .map(User::getAddress)
            .map(Address::getCity)
            .map(String::toUpperCase)
            .orElse("UNKNOWN");
    }
    
    private static Optional<String> findFirstMatch(List<String> list, String prefix) {
        return list.stream()
            .filter(s -> s.startsWith(prefix))
            .findFirst();
    }
    
    private static Optional<Integer> findMax(List<Integer> numbers) {
        return numbers.stream()
            .max(Integer::compareTo);
    }
    
    private static Optional<String> getElementAt(List<String> list, int index) {
        return (index >= 0 && index < list.size()) 
            ? Optional.of(list.get(index)) 
            : Optional.empty();
    }
    
    private static Optional<Integer> parseInteger(String input) {
        try {
            return Optional.of(Integer.parseInt(input));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
    
    private static List<String> filterPresent(List<Optional<String>> optionals) {
        return optionals.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }
    
    private static List<String> processWithOptional(List<String> strings) {
        return strings.stream()
            .map(OptionalExamples::processString)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }
    
    private static Optional<String> processString(String input) {
        return input.length() > 3 
            ? Optional.of(input.toUpperCase())
            : Optional.empty();
    }
    
    private static Optional<String> findAndTransform(List<String> strings, String prefix) {
        return strings.stream()
            .filter(s -> s.startsWith(prefix))
            .findFirst()
            .map(String::toUpperCase);
    }
    
    private static Stream<String> optionalToStream(Optional<String> optional) {
        return optional.stream();
    }
    
    private static List<String> processOptionals(List<Optional<String>> optionals) {
        return optionals.stream()
            .flatMap(Optional::stream)
            .collect(Collectors.toList());
    }
    
    private static String expensiveOperation() {
        return "Expensive result";
    }
    
    private static void processUser(User user) {
        System.out.println("Processing user: " + user.getName());
    }
    
    private static Optional<String> findUserEmail(String userId) {
        return Optional.of("user@example.com");
    }
    
    private static Optional<String> getCityOptional(Person person) {
        return Optional.ofNullable(person)
            .map(Person::getAddress)
            .map(Address::getCity);
    }
    
    private static String getValueWithFallbacks(Optional<String> primary, 
                                               Optional<String> secondary, 
                                               Optional<String> tertiary) {
        return primary
            .or(() -> secondary)
            .or(() -> tertiary)
            .orElse("Default");
    }
    
    private static String getOrThrow(Optional<String> optional, String errorMessage) {
        return optional.orElseThrow(() -> 
            new IllegalArgumentException(errorMessage)
        );
    }
    
    private static Optional<String> safeGet(List<String> list, int index) {
        return (index >= 0 && index < list.size()) 
            ? Optional.of(list.get(index)) 
            : Optional.empty();
    }
    
    private static List<String> processPresent(List<Optional<String>> optionals) {
        return optionals.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }
    
    private static Optional<String> findUser(String id) {
        return Optional.of("User " + id);
    }
    
    // Supporting classes
    static class User {
        private String id;
        private String name;
        private String email;
        private Address address;
        
        public User(String id, String name, String email, Address address) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.address = address;
        }
        
        public User() {
            this.name = null;
        }
        
        public User(String name, Address address) {
            this.name = name;
            this.address = address;
        }
        
        public String getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public Address getAddress() { return address; }
    }
    
    static class Address {
        private String street;
        private String city;
        private String country;
        
        public Address(String street, String city, String country) {
            this.street = street;
            this.city = city;
            this.country = country;
        }
        
        public String getStreet() { return street; }
        public String getCity() { return city; }
        public String getCountry() { return country; }
    }
    
    static class Person {
        private String name;
        private Address address;
        
        public Person(String name, Address address) {
            this.name = name;
            this.address = address;
        }
        
        public String getName() { return name; }
        public Address getAddress() { return address; }
    }
    
    static class UserProfile {
        private String name;
        private String email;
        private ProfileAddress address;
        
        public UserProfile(String name, String email, ProfileAddress address) {
            this.name = name;
            this.email = email;
            this.address = address;
        }
        
        public Optional<String> getName() { return Optional.ofNullable(name); }
        public Optional<String> getEmail() { return Optional.ofNullable(email); }
        public Optional<String> getCity() { 
            return Optional.ofNullable(address).map(ProfileAddress::getCity); 
        }
    }
    
    static class ProfileAddress {
        private String street;
        private String city;
        private String country;
        
        public ProfileAddress(String street, String city, String country) {
            this.street = street;
            this.city = city;
            this.country = country;
        }
        
        public String getCity() { return city; }
    }
    
    static class ConfigurationReader {
        private Map<String, String> properties = new HashMap<>();
        
        public void setProperty(String key, String value) {
            properties.put(key, value);
        }
        
        public int getIntProperty(String key, int defaultValue) {
            return Optional.ofNullable(properties.get(key))
                .flatMap(this::parseInt)
                .orElse(defaultValue);
        }
        
        public String getStringProperty(String key, String defaultValue) {
            return Optional.ofNullable(properties.get(key))
                .orElse(defaultValue);
        }
        
        public boolean getBooleanProperty(String key, boolean defaultValue) {
            return Optional.ofNullable(properties.get(key))
                .map(Boolean::parseBoolean)
                .orElse(defaultValue);
        }
        
        private Optional<Integer> parseInt(String value) {
            try {
                return Optional.of(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }
    }
    
    static class DataValidator {
        public List<ValidationResult> validateBatch(List<String> inputs) {
            return inputs.stream()
                .map(this::validate)
                .collect(Collectors.toList());
        }
        
        private ValidationResult validate(String input) {
            if (input == null || input.trim().isEmpty()) {
                return new ValidationResult(false, "Input is null or empty", null);
            }
            if (input.length() > 10) {
                return new ValidationResult(false, "Input too long", null);
            }
            return new ValidationResult(true, null, input.toUpperCase());
        }
    }
    
    static class ValidationResult {
        private boolean valid;
        private String error;
        private String value;
        
        public ValidationResult(boolean valid, String error, String value) {
            this.valid = valid;
            this.error = error;
            this.value = value;
        }
        
        public boolean isValid() { return valid; }
        public String getError() { return error; }
        public String getValue() { return value; }
    }
    
    // User management example class
    static class UserManagementExample {
        private Map<String, User> users = new HashMap<>();
        
        public void addUser(User user) {
            users.put(user.getId(), user);
        }
        
        public Optional<User> findUserById(String id) {
            return Optional.ofNullable(users.get(id));
        }
        
        public Optional<String> getUserCity(String userId) {
            return findUserById(userId)
                .map(User::getAddress)
                .map(Address::getCity);
        }
        
        public String getUserEmailOrDefault(String userId) {
            return findUserById(userId)
                .map(User::getEmail)
                .orElse("No email available");
        }
        
        public String getUserFullAddress(String userId) {
            return findUserById(userId)
                .map(User::getAddress)
                .map(addr -> String.format("%s, %s, %s", 
                    addr.getStreet(), addr.getCity(), addr.getCountry()))
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        }
        
        public List<User> findUsersByCity(String city) {
            return users.values().stream()
                .filter(user -> getUserCity(user.getId())
                    .map(c -> c.equals(city))
                    .orElse(false))
                .collect(Collectors.toList());
        }
    }
    
    // Configuration class
    static class Configuration {
        private Map<String, String> properties = new HashMap<>();
        
        public void setProperty(String key, String value) {
            properties.put(key, value);
        }
        
        public Optional<String> getProperty(String key) {
            return Optional.ofNullable(properties.get(key));
        }
        
        public String getPropertyOrDefault(String key, String defaultValue) {
            return getProperty(key).orElse(defaultValue);
        }
        
        public int getIntProperty(String key, int defaultValue) {
            return getProperty(key)
                .flatMap(this::parseInt)
                .orElse(defaultValue);
        }
        
        public boolean getBooleanProperty(String key, boolean defaultValue) {
            return getProperty(key)
                .map(Boolean::parseBoolean)
                .orElse(defaultValue);
        }
        
        private Optional<Integer> parseInt(String value) {
            try {
                return Optional.of(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }
        
        public List<String> getListProperty(String key, String delimiter) {
            return getProperty(key)
                .map(value -> Arrays.asList(value.split(delimiter)))
                .orElse(Collections.emptyList());
        }
    }
    
    // Data processor class
    static class DataProcessor {
        public Optional<String> processData(String input) {
            return Optional.ofNullable(input)
                .filter(this::isValidInput)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(this::transformData)
                .filter(this::isValidOutput);
        }
        
        private boolean isValidInput(String input) {
            return input != null && input.length() > 0;
        }
        
        private String transformData(String input) {
            return input.toUpperCase();
        }
        
        private boolean isValidOutput(String output) {
            return output.length() <= 100;
        }
        
        public List<String> processBatch(List<String> inputs) {
            return inputs.stream()
                .map(this::processData)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        }
        
        public Optional<String> findFirstValid(List<String> inputs) {
            return inputs.stream()
                .map(this::processData)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
        }
        
        public String aggregateResults(List<String> inputs) {
            return inputs.stream()
                .map(this::processData)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.joining(", "));
        }
    }
} 