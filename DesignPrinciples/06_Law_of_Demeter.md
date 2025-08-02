# Law of Demeter Deep Dive

## Overview

**Law of Demeter (LoD)**, also known as the **Principle of Least Knowledge**, is a design principle that promotes loose coupling between software components. It states that an object should have limited knowledge of other objects and should only interact with its immediate "friends."

## What is the Law of Demeter?

The Law of Demeter can be summarized as:

- **Don't talk to strangers**: Only talk to your immediate friends
- **Principle of least knowledge**: An object should have minimal knowledge of other objects
- **Loose coupling**: Reduce dependencies between objects

## The Law in Detail

An object should only call methods on:

1. **Itself** (this)
2. **Objects passed as parameters** to its methods
3. **Objects it creates** directly
4. **Objects stored in its instance variables**
5. **Objects in global variables** (rarely applicable in modern Java)

## Benefits of Following the Law of Demeter

1. **Loose Coupling**: Objects are less dependent on each other
2. **Easier Testing**: Fewer dependencies to mock
3. **Better Encapsulation**: Internal structure is hidden
4. **Easier Maintenance**: Changes in one object don't affect others
5. **Improved Readability**: Code is more self-documenting

## Common Violations

### 1. Train Wreck (Method Chaining)

#### ❌ Violation Example

```java
// Bad: Violates Law of Demeter - accessing objects through multiple levels
public class Customer {
    private String name;
    private Address address;

    public Customer(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }
}

public class Address {
    private String street;
    private City city;

    public Address(String street, City city) {
        this.street = street;
        this.city = city;
    }

    public City getCity() {
        return city;
    }
}

public class City {
    private String name;
    private Country country;

    public City(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }
}

public class Country {
    private String name;

    public Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// Violation: Accessing objects through multiple levels
public class CustomerService {
    public void processCustomer(Customer customer) {
        // Train wreck - accessing through multiple objects
        String countryName = customer.getAddress().getCity().getCountry().getName();
        System.out.println("Customer is from: " + countryName);
    }
}
```

#### ✅ Correct Implementation

```java
// Good: Following Law of Demeter - encapsulating the logic
public class Customer {
    private String name;
    private Address address;

    public Customer(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    // Encapsulate the logic within the Customer class
    public String getCountryName() {
        return address.getCountryName();
    }
}

public class Address {
    private String street;
    private City city;

    public Address(String street, City city) {
        this.street = street;
        this.city = city;
    }

    // Encapsulate the logic within the Address class
    public String getCountryName() {
        return city.getCountryName();
    }
}

public class City {
    private String name;
    private Country country;

    public City(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    // Encapsulate the logic within the City class
    public String getCountryName() {
        return country.getName();
    }
}

public class Country {
    private String name;

    public Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// Good: Following Law of Demeter
public class CustomerService {
    public void processCustomer(Customer customer) {
        // Only talk to immediate friend
        String countryName = customer.getCountryName();
        System.out.println("Customer is from: " + countryName);
    }
}
```

### 2. Exposing Internal Structure

#### ❌ Violation Example

```java
// Bad: Exposing internal structure
public class Order {
    private List<OrderItem> items;
    private Customer customer;

    public Order(List<OrderItem> items, Customer customer) {
        this.items = items;
        this.customer = customer;
    }

    // Exposing internal structure
    public List<OrderItem> getItems() {
        return items;
    }

    public Customer getCustomer() {
        return customer;
    }
}

public class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}

public class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

// Violation: Client code knows too much about internal structure
public class OrderProcessor {
    public void processOrder(Order order) {
        // Client knows too much about Order's internal structure
        List<OrderItem> items = order.getItems();
        for (OrderItem item : items) {
            Product product = item.getProduct();
            String productName = product.getName();
            double productPrice = product.getPrice();
            int quantity = item.getQuantity();

            System.out.println("Processing: " + quantity + " x " + productName + " @ $" + productPrice);
        }
    }
}
```

#### ✅ Correct Implementation

```java
// Good: Encapsulating internal structure
public class Order {
    private List<OrderItem> items;
    private Customer customer;

    public Order(List<OrderItem> items, Customer customer) {
        this.items = items;
        this.customer = customer;
    }

    // Encapsulate the processing logic
    public void processItems() {
        for (OrderItem item : items) {
            item.process();
        }
    }

    // Provide high-level information without exposing structure
    public double getTotalAmount() {
        return items.stream()
                   .mapToDouble(OrderItem::getTotalPrice)
                   .sum();
    }

    public int getTotalItems() {
        return items.stream()
                   .mapToInt(OrderItem::getQuantity)
                   .sum();
    }
}

public class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Encapsulate the processing logic
    public void process() {
        System.out.println("Processing: " + quantity + " x " + product.getName() + " @ $" + product.getPrice());
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}

// Good: Client only knows about Order's public interface
public class OrderProcessor {
    public void processOrder(Order order) {
        // Client only talks to Order directly
        order.processItems();
        System.out.println("Total amount: $" + order.getTotalAmount());
        System.out.println("Total items: " + order.getTotalItems());
    }
}
```

### 3. Passing Objects Through Multiple Layers

#### ❌ Violation Example

```java
// Bad: Passing objects through multiple layers
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void createUser(String username, String email) {
        // Passing User object through multiple layers
        User user = new User(username, email);
        userService.createUser(user);
    }
}

public class UserService {
    private UserRepository userRepository;
    private EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public void createUser(User user) {
        // Passing User object to repository
        userRepository.save(user);

        // Passing User object to email service
        emailService.sendWelcomeEmail(user.getEmail());
    }
}

public class UserRepository {
    public void save(User user) {
        // Database operations
        System.out.println("Saving user: " + user.getUsername());
    }
}

public class EmailService {
    public void sendWelcomeEmail(String email) {
        System.out.println("Sending welcome email to: " + email);
    }
}
```

#### ✅ Correct Implementation

```java
// Good: Passing only necessary data
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void createUser(String username, String email) {
        // Pass only the necessary data, not the entire object
        userService.createUser(username, email);
    }
}

public class UserService {
    private UserRepository userRepository;
    private EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public void createUser(String username, String email) {
        // Create User object internally
        User user = new User(username, email);
        userRepository.save(user);

        // Pass only the email, not the entire user object
        emailService.sendWelcomeEmail(email);
    }
}

public class UserRepository {
    public void save(User user) {
        // Database operations
        System.out.println("Saving user: " + user.getUsername());
    }
}

public class EmailService {
    public void sendWelcomeEmail(String email) {
        System.out.println("Sending welcome email to: " + email);
    }
}
```

## Real-World Examples

### 1. Builder Pattern

```java
// Good: Builder pattern following Law of Demeter
public class EmailBuilder {
    private String to;
    private String subject;
    private String body;

    public EmailBuilder to(String to) {
        this.to = to;
        return this;
    }

    public EmailBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailBuilder body(String body) {
        this.body = body;
        return this;
    }

    public Email build() {
        return new Email(to, subject, body);
    }
}

public class EmailService {
    public void sendEmail(Email email) {
        // Send email logic
        System.out.println("Sending email to: " + email.getTo());
    }
}

// Usage - fluent interface, each method returns the builder
public class EmailClient {
    private EmailService emailService;

    public EmailClient(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendWelcomeEmail(String userEmail) {
        Email email = new EmailBuilder()
            .to(userEmail)
            .subject("Welcome!")
            .body("Welcome to our service!")
            .build();

        emailService.sendEmail(email);
    }
}
```

### 2. Facade Pattern

```java
// Good: Facade pattern following Law of Demeter
public class OrderFacade {
    private InventoryService inventoryService;
    private PaymentService paymentService;
    private ShippingService shippingService;
    private NotificationService notificationService;

    public OrderFacade(InventoryService inventoryService,
                      PaymentService paymentService,
                      ShippingService shippingService,
                      NotificationService notificationService) {
        this.inventoryService = inventoryService;
        this.paymentService = paymentService;
        this.shippingService = shippingService;
        this.notificationService = notificationService;
    }

    // Single method that orchestrates the entire process
    public OrderResult processOrder(OrderRequest request) {
        // Check inventory
        if (!inventoryService.checkAvailability(request.getProductId(), request.getQuantity())) {
            return OrderResult.failure("Product not available");
        }

        // Process payment
        PaymentResult paymentResult = paymentService.processPayment(request.getPaymentInfo());
        if (!paymentResult.isSuccess()) {
            return OrderResult.failure("Payment failed: " + paymentResult.getMessage());
        }

        // Create shipping label
        String trackingNumber = shippingService.createShippingLabel(request.getShippingAddress());

        // Send notification
        notificationService.sendOrderConfirmation(request.getCustomerEmail(), trackingNumber);

        return OrderResult.success(trackingNumber);
    }
}

// Client only needs to know about the facade
public class OrderController {
    private OrderFacade orderFacade;

    public OrderController(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    public void placeOrder(OrderRequest request) {
        OrderResult result = orderFacade.processOrder(request);
        if (result.isSuccess()) {
            System.out.println("Order placed successfully. Tracking: " + result.getTrackingNumber());
        } else {
            System.out.println("Order failed: " + result.getMessage());
        }
    }
}
```

### 3. Command Pattern

```java
// Good: Command pattern following Law of Demeter
public interface Command {
    void execute();
}

public class CreateUserCommand implements Command {
    private String username;
    private String email;
    private UserService userService;

    public CreateUserCommand(String username, String email, UserService userService) {
        this.username = username;
        this.email = email;
        this.userService = userService;
    }

    @Override
    public void execute() {
        userService.createUser(username, email);
    }
}

public class CommandProcessor {
    private List<Command> commands = new ArrayList<>();

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void processCommands() {
        for (Command command : commands) {
            command.execute();
        }
        commands.clear();
    }
}

// Client only knows about Command interface
public class Application {
    public static void main(String[] args) {
        UserService userService = new UserService();
        CommandProcessor processor = new CommandProcessor();

        // Client doesn't need to know about UserService internals
        Command createUserCommand = new CreateUserCommand("john", "john@example.com", userService);
        processor.addCommand(createUserCommand);
        processor.processCommands();
    }
}
```

## Best Practices

### 1. Use Tell, Don't Ask

```java
// Bad: Asking for data and then making decisions
public class OrderProcessor {
    public void processOrder(Order order) {
        if (order.getTotalAmount() > 1000) {
            order.setDiscount(0.1);
        }
        order.calculateFinalAmount();
    }
}

// Good: Telling objects what to do
public class OrderProcessor {
    public void processOrder(Order order) {
        order.applyDiscountIfEligible();
    }
}

public class Order {
    public void applyDiscountIfEligible() {
        if (getTotalAmount() > 1000) {
            setDiscount(0.1);
        }
        calculateFinalAmount();
    }
}
```

### 2. Encapsulate Collections

```java
// Bad: Exposing internal collection
public class UserManager {
    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users; // Exposing internal structure
    }
}

// Good: Encapsulating collection operations
public class UserManager {
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public List<User> findUsersByName(String name) {
        return users.stream()
                   .filter(user -> user.getName().equals(name))
                   .collect(Collectors.toList());
    }

    public int getUserCount() {
        return users.size();
    }
}
```

### 3. Use Data Transfer Objects (DTOs)

```java
// Good: Using DTOs to transfer only necessary data
public class UserDTO {
    private String username;
    private String email;

    public UserDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // Getters only
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}

public class UserService {
    public UserDTO getUserInfo(String userId) {
        User user = userRepository.findById(userId);
        return new UserDTO(user.getUsername(), user.getEmail());
    }
}

// Client only gets the data it needs
public class UserController {
    public void displayUserInfo(String userId) {
        UserDTO userInfo = userService.getUserInfo(userId);
        System.out.println("Username: " + userInfo.getUsername());
        System.out.println("Email: " + userInfo.getEmail());
    }
}
```

## Common Pitfalls

1. **Over-Encapsulation**: Making objects too rigid and inflexible
2. **Performance Concerns**: Multiple method calls instead of direct access
3. **Over-Abstraction**: Creating unnecessary abstractions
4. **Ignoring Context**: Applying the law too strictly in all situations

## When to Violate the Law of Demeter

1. **Framework Code**: When building frameworks that need to expose internal structure
2. **Performance Critical Code**: When performance is more important than loose coupling
3. **Simple Data Objects**: When objects are primarily data carriers
4. **Legacy Integration**: When integrating with legacy systems that require specific access patterns

## Summary

The Law of Demeter is a powerful principle that promotes loose coupling and better encapsulation. By following this law, we create more maintainable, testable, and flexible code. However, it's important to apply it judiciously and consider the context of your application.

Key takeaways:

- **Don't talk to strangers**: Only interact with immediate friends
- **Encapsulate behavior**: Keep related logic together
- **Use Tell, Don't Ask**: Tell objects what to do rather than asking for data
- **Consider context**: Don't apply the law too strictly in all situations
- **Balance trade-offs**: Consider performance and complexity when applying the law

Remember: **"The Law of Demeter is about reducing coupling, not eliminating it entirely."**
