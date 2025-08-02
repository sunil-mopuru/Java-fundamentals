# Optional Class in Java 8

The Optional class was introduced in Java 8 to provide a better way to handle null values. It's a container object that may or may not contain a non-null value, helping to eliminate null pointer exceptions and making code more readable and maintainable.

## Table of Contents

1. [What is Optional?](#what-is-optional)
2. [Why Use Optional?](#why-use-optional)
3. [Creating Optional Objects](#creating-optional-objects)
4. [Optional Methods](#optional-methods)
5. [Working with Optional](#working-with-optional)
6. [Optional with Streams](#optional-with-streams)
7. [Best Practices](#best-practices)
8. [Common Patterns](#common-patterns)
9. [Practical Examples](#practical-examples)
10. [Common Pitfalls](#common-pitfalls)
11. [Exercises](#exercises)

## What is Optional?

Optional is a container class that represents an optional value. It can either contain a value or be empty (null). The main purpose is to provide a more explicit way to handle the absence of a value, rather than using null.

### Key Characteristics

- **Immutable**: Once created, an Optional cannot be modified
- **Type-safe**: Provides compile-time type safety
- **Functional**: Supports functional programming patterns
- **Explicit**: Makes the presence or absence of a value explicit

## Why Use Optional?

### Problems with Null

```java
// Traditional approach - prone to NullPointerException
public String getUserName(User user) {
    if (user != null) {
        if (user.getName() != null) {
            return user.getName().toUpperCase();
        }
    }
    return "Unknown";
}

// With Optional - safer and more readable
public String getUserName(User user) {
    return Optional.ofNullable(user)
        .map(User::getName)
        .map(String::toUpperCase)
        .orElse("Unknown");
}
```

### Benefits of Optional

1. **Explicit Intent**: Makes it clear when a value might be absent
2. **No NullPointerException**: Eliminates null pointer exceptions
3. **Functional Style**: Enables functional programming patterns
4. **Better API Design**: Forces developers to handle the absence case
5. **Composability**: Can be chained with other operations

## Creating Optional Objects

### Optional.empty()

```java
// Create an empty Optional
Optional<String> emptyOptional = Optional.empty();
```

### Optional.of()

```java
// Create Optional with a non-null value
Optional<String> optional = Optional.of("Hello");
// Optional.of(null); // This will throw NullPointerException
```

### Optional.ofNullable()

```java
// Create Optional that may be null
Optional<String> optional1 = Optional.ofNullable("Hello");
Optional<String> optional2 = Optional.ofNullable(null); // Creates empty Optional
```

### Optional.ofNullable() with Method Calls

```java
public class User {
    private String name;

    public String getName() {
        return name;
    }
}

User user = new User();
Optional<String> nameOptional = Optional.ofNullable(user.getName());
```

## Optional Methods

### Checking Presence

```java
Optional<String> optional = Optional.of("Hello");

// Check if value is present
if (optional.isPresent()) {
    System.out.println("Value is present: " + optional.get());
}

// Check if value is absent
if (optional.isEmpty()) {
    System.out.println("Value is absent");
}
```

### Getting Values

```java
Optional<String> optional = Optional.of("Hello");

// Get value (throws exception if empty)
String value = optional.get(); // Use with caution

// Get value with default
String valueOrDefault = optional.orElse("Default Value");

// Get value with supplier
String valueOrSupplied = optional.orElseGet(() -> "Supplied Value");

// Get value or throw exception
String valueOrThrow = optional.orElseThrow(() -> new RuntimeException("No value"));
```

### Conditional Actions

```java
Optional<String> optional = Optional.of("Hello");

// Execute action if present
optional.ifPresent(value -> System.out.println("Value: " + value));

// Execute action if present, otherwise do something else
optional.ifPresentOrElse(
    value -> System.out.println("Value: " + value),
    () -> System.out.println("No value present")
);
```

### Transforming Values

```java
Optional<String> optional = Optional.of("hello");

// Transform value if present
Optional<String> upperCase = optional.map(String::toUpperCase);

// Transform with function that returns Optional
Optional<String> transformed = optional.flatMap(value ->
    Optional.of(value.toUpperCase())
);

// Filter values
Optional<String> filtered = optional.filter(value -> value.length() > 3);
```

## Working with Optional

### Basic Usage Patterns

```java
public class OptionalExamples {

    // Traditional null checking
    public String getTraditional(String input) {
        if (input != null) {
            return input.toUpperCase();
        }
        return "DEFAULT";
    }

    // With Optional
    public String getWithOptional(String input) {
        return Optional.ofNullable(input)
            .map(String::toUpperCase)
            .orElse("DEFAULT");
    }

    // Handling multiple levels of null
    public String getNestedTraditional(User user) {
        if (user != null) {
            Address address = user.getAddress();
            if (address != null) {
                String city = address.getCity();
                if (city != null) {
                    return city.toUpperCase();
                }
            }
        }
        return "UNKNOWN";
    }

    // With Optional - much cleaner
    public String getNestedOptional(User user) {
        return Optional.ofNullable(user)
            .map(User::getAddress)
            .map(Address::getCity)
            .map(String::toUpperCase)
            .orElse("UNKNOWN");
    }
}
```

### Optional with Collections

```java
public class OptionalWithCollections {

    // Find first element matching condition
    public Optional<String> findFirstMatch(List<String> list, String prefix) {
        return list.stream()
            .filter(s -> s.startsWith(prefix))
            .findFirst();
    }

    // Find maximum value
    public Optional<Integer> findMax(List<Integer> numbers) {
        return numbers.stream()
            .max(Integer::compareTo);
    }

    // Safe list access
    public Optional<String> getElementAt(List<String> list, int index) {
        return (index >= 0 && index < list.size())
            ? Optional.of(list.get(index))
            : Optional.empty();
    }
}
```

### Optional with Exceptions

```java
public class OptionalWithExceptions {

    // Convert exception-throwing method to Optional
    public Optional<Integer> parseInteger(String input) {
        try {
            return Optional.of(Integer.parseInt(input));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    // Using orElseThrow for custom exceptions
    public String getRequiredValue(Optional<String> optional) {
        return optional.orElseThrow(() ->
            new IllegalArgumentException("Value is required but not present")
        );
    }
}
```

## Optional with Streams

### Stream Operations with Optional

```java
public class OptionalWithStreams {

    // Filter out empty optionals
    public List<String> filterPresent(List<Optional<String>> optionals) {
        return optionals.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    // Map to optional and filter
    public List<String> processWithOptional(List<String> strings) {
        return strings.stream()
            .map(this::processString) // Returns Optional<String>
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    private Optional<String> processString(String input) {
        return input.length() > 3
            ? Optional.of(input.toUpperCase())
            : Optional.empty();
    }

    // Find first with transformation
    public Optional<String> findAndTransform(List<String> strings, String prefix) {
        return strings.stream()
            .filter(s -> s.startsWith(prefix))
            .findFirst()
            .map(String::toUpperCase);
    }
}
```

### Optional Stream

```java
public class OptionalStream {

    // Convert Optional to Stream
    public Stream<String> optionalToStream(Optional<String> optional) {
        return optional.stream();
    }

    // Process multiple optionals
    public List<String> processOptionals(List<Optional<String>> optionals) {
        return optionals.stream()
            .flatMap(Optional::stream) // Convert Optional to Stream
            .collect(Collectors.toList());
    }

    // Chain optional operations
    public Optional<String> chainOperations(Optional<String> optional) {
        return optional.stream()
            .map(String::toUpperCase)
            .filter(s -> s.length() > 3)
            .findFirst();
    }
}
```

## Best Practices

### 1. Don't Use Optional for Everything

```java
// Good - Optional for return values that might be absent
public Optional<User> findUserById(String id) {
    // Implementation
}

// Bad - Don't use Optional for required parameters
public void processUser(Optional<User> user) { // Avoid this
    // Implementation
}

// Good - Use regular parameters for required values
public void processUser(User user) {
    // Implementation
}
```

### 2. Avoid Optional.get() Without Checking

```java
// Bad - May throw exception
Optional<String> optional = Optional.of("Hello");
String value = optional.get(); // Dangerous

// Good - Use safe methods
String value = optional.orElse("Default");
String value = optional.orElseGet(() -> "Default");
optional.ifPresent(System.out::println);
```

### 3. Use Method References When Possible

```java
// Good
Optional<String> optional = Optional.of("hello");
Optional<String> upperCase = optional.map(String::toUpperCase);

// Also good
optional.ifPresent(System.out::println);
```

### 4. Prefer orElseGet() for Expensive Operations

```java
// Bad - Expensive operation always executed
String value = optional.orElse(expensiveOperation());

// Good - Expensive operation only executed if needed
String value = optional.orElseGet(() -> expensiveOperation());
```

### 5. Use Optional for Return Types

```java
// Good - Clear that method might not return a value
public Optional<String> findUserEmail(String userId) {
    // Implementation
}

// Bad - Ambiguous about null possibility
public String findUserEmail(String userId) {
    // Implementation
}
```

## Common Patterns

### Null-Safe Navigation

```java
public class NullSafeNavigation {

    // Traditional approach
    public String getCityTraditional(Person person) {
        if (person != null) {
            Address address = person.getAddress();
            if (address != null) {
                return address.getCity();
            }
        }
        return null;
    }

    // With Optional
    public Optional<String> getCityOptional(Person person) {
        return Optional.ofNullable(person)
            .map(Person::getAddress)
            .map(Address::getCity);
    }
}
```

### Default Values

```java
public class DefaultValues {

    // Multiple fallback values
    public String getValueWithFallbacks(Optional<String> primary,
                                       Optional<String> secondary,
                                       Optional<String> tertiary) {
        return primary
            .or(() -> secondary)
            .or(() -> tertiary)
            .orElse("Default");
    }

    // Conditional defaults
    public String getConditionalDefault(Optional<String> value, boolean useDefault) {
        return value.orElseGet(() -> useDefault ? "Default" : "Empty");
    }
}
```

### Exception Handling

```java
public class ExceptionHandling {

    // Convert exceptions to Optional
    public Optional<Integer> safeParse(String input) {
        try {
            return Optional.of(Integer.parseInt(input));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    // Custom exception with Optional
    public String getOrThrow(Optional<String> optional, String errorMessage) {
        return optional.orElseThrow(() ->
            new IllegalArgumentException(errorMessage)
        );
    }
}
```

### Collection Processing

```java
public class CollectionProcessing {

    // Find first matching element
    public Optional<String> findFirstMatch(List<String> list, String prefix) {
        return list.stream()
            .filter(s -> s.startsWith(prefix))
            .findFirst();
    }

    // Safe list access
    public Optional<String> safeGet(List<String> list, int index) {
        return (index >= 0 && index < list.size())
            ? Optional.of(list.get(index))
            : Optional.empty();
    }

    // Process only present values
    public List<String> processPresent(List<Optional<String>> optionals) {
        return optionals.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }
}
```

## Practical Examples

### Example 1: User Management System

```java
import java.util.*;
import java.util.Optional;

class User {
    private String id;
    private String name;
    private String email;
    private Address address;

    // Constructors, getters, setters
    public User(String id, String name, String email, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Address getAddress() { return address; }
}

class Address {
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

public class UserManagementExample {
    private Map<String, User> users = new HashMap<>();

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    // Find user by ID
    public Optional<User> findUserById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    // Get user's city safely
    public Optional<String> getUserCity(String userId) {
        return findUserById(userId)
            .map(User::getAddress)
            .map(Address::getCity);
    }

    // Get user's email or default
    public String getUserEmailOrDefault(String userId) {
        return findUserById(userId)
            .map(User::getEmail)
            .orElse("No email available");
    }

    // Get user's full address or throw exception
    public String getUserFullAddress(String userId) {
        return findUserById(userId)
            .map(User::getAddress)
            .map(addr -> String.format("%s, %s, %s",
                addr.getStreet(), addr.getCity(), addr.getCountry()))
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    }

    // Find users by city
    public List<User> findUsersByCity(String city) {
        return users.values().stream()
            .filter(user -> getUserCity(user.getId())
                .map(c -> c.equals(city))
                .orElse(false))
            .collect(Collectors.toList());
    }

    public static void main(String[] args) {
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
}
```

### Example 2: Configuration Management

```java
import java.util.*;
import java.util.Optional;

class Configuration {
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

public class ConfigurationExample {
    public static void main(String[] args) {
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
}
```

### Example 3: Data Processing Pipeline

```java
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

class DataProcessor {

    // Process data with multiple validation steps
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

    // Batch processing with Optional
    public List<String> processBatch(List<String> inputs) {
        return inputs.stream()
            .map(this::processData)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    // Find first valid result
    public Optional<String> findFirstValid(List<String> inputs) {
        return inputs.stream()
            .map(this::processData)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .findFirst();
    }

    // Aggregate results
    public String aggregateResults(List<String> inputs) {
        return inputs.stream()
            .map(this::processData)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.joining(", "));
    }
}

public class DataProcessingExample {
    public static void main(String[] args) {
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
}
```

## Common Pitfalls

### 1. Using Optional.get() Without Checking

```java
// Bad - May throw exception
Optional<String> optional = Optional.of("Hello");
String value = optional.get(); // Dangerous

// Good - Use safe methods
String value = optional.orElse("Default");
optional.ifPresent(System.out::println);
```

### 2. Creating Optional of Null

```java
// Bad - Will throw NullPointerException
Optional<String> optional = Optional.of(null);

// Good - Use ofNullable for potentially null values
Optional<String> optional = Optional.ofNullable(null);
```

### 3. Overusing Optional

```java
// Bad - Don't use Optional for everything
public void processUser(Optional<User> user) {
    user.ifPresent(this::doSomething);
}

// Good - Use regular parameters for required values
public void processUser(User user) {
    doSomething(user);
}
```

### 4. Ignoring Empty Optionals

```java
// Bad - Ignores the case when value is absent
Optional<String> optional = getOptionalValue();
optional.ifPresent(System.out::println);
// What if the value is absent? No handling!

// Good - Handle both cases
Optional<String> optional = getOptionalValue();
optional.ifPresentOrElse(
    System.out::println,
    () -> System.out.println("No value present")
);
```

### 5. Not Using Optional for Return Types

```java
// Bad - Ambiguous about null possibility
public String findUser(String id) {
    // Might return null
    return userRepository.findById(id);
}

// Good - Clear intent
public Optional<String> findUser(String id) {
    return Optional.ofNullable(userRepository.findById(id));
}
```

## Exercises

### Exercise 1: Basic Optional Operations

Create methods that demonstrate basic Optional operations:

1. Create an Optional from a potentially null string
2. Get the value or return a default
3. Transform the value if present
4. Execute an action only if the value is present
5. Chain multiple Optional operations

### Exercise 2: User Profile System

Create a UserProfile class with nested objects and use Optional to:

1. Safely access nested properties
2. Provide default values for missing properties
3. Transform user data safely
4. Handle missing user profiles gracefully

### Exercise 3: Configuration Reader

Create a configuration reader that:

1. Reads properties from a map
2. Converts string values to different types (int, boolean, etc.)
3. Provides default values for missing properties
4. Validates configuration values

### Exercise 4: Data Validation Pipeline

Create a data validation system that:

1. Validates input data using Optional
2. Transforms valid data
3. Collects validation errors
4. Returns either valid data or error messages

### Exercise 5: Optional with Streams

Create methods that:

1. Filter a list of Optionals to get only present values
2. Find the first Optional that matches a condition
3. Transform a list using Optional operations
4. Aggregate results from multiple Optionals

## Solutions

Solutions to these exercises can be found in the corresponding Java files in the `src/optional/` directory.

## Next Steps

After mastering the Optional class, proceed to:

- [Method References](05_Method_References.md) - Shorthand for lambda expressions
- [Default Methods](04_Default_Methods.md) - Interface evolution
- [Date/Time API](06_Date_Time_API.md) - Modern date and time handling
