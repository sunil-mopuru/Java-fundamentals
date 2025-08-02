import java.util.*;
import java.util.stream.Collectors;

/**
 * Comprehensive demonstration of all design principles working together
 * This example shows how multiple design principles can be applied
 * to create a well-designed e-commerce system.
 */
public class DesignPrinciplesDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Design Principles in Practice ===\n");
        
        // Create the e-commerce system
        ECommerceSystem ecommerce = createECommerceSystem();
        
        // Demonstrate the system in action
        demonstrateSystem(ecommerce);
    }
    
    private static ECommerceSystem createECommerceSystem() {
        // Create services following SRP
        ProductRepository productRepo = new ProductRepository();
        OrderRepository orderRepo = new OrderRepository();
        PaymentProcessor paymentProcessor = new PaymentProcessor();
        EmailService emailService = new EmailService();
        Logger logger = new ConsoleLogger();
        
        // Create validators
        OrderValidator orderValidator = new OrderValidator();
        PaymentValidator paymentValidator = new PaymentValidator();
        
        // Create the main service following DIP
        OrderService orderService = new OrderService(
            orderRepo, 
            productRepo, 
            paymentProcessor, 
            emailService, 
            logger,
            orderValidator,
            paymentValidator
        );
        
        return new ECommerceSystem(orderService, productRepo);
    }
    
    private static void demonstrateSystem(ECommerceSystem ecommerce) {
        // Add some products
        ecommerce.addProduct("Laptop", 999.99);
        ecommerce.addProduct("Mouse", 29.99);
        ecommerce.addProduct("Keyboard", 79.99);
        
        // Create an order
        OrderRequest request = new OrderRequest();
        request.addItem("Laptop", 1);
        request.addItem("Mouse", 2);
        request.setCustomerEmail("customer@example.com");
        request.setPaymentMethod("CREDIT_CARD");
        
        // Process the order
        OrderResult result = ecommerce.processOrder(request);
        
        if (result.isSuccess()) {
            System.out.println("Order processed successfully!");
            System.out.println("Order ID: " + result.getOrderId());
            System.out.println("Total: $" + result.getTotal());
        } else {
            System.out.println("Order failed: " + result.getErrorMessage());
        }
    }
}

// ===== MAIN SYSTEM CLASSES =====

/**
 * Main e-commerce system - follows KISS principle
 */
class ECommerceSystem {
    private final OrderService orderService;
    private final ProductRepository productRepository;
    
    public ECommerceSystem(OrderService orderService, ProductRepository productRepository) {
        this.orderService = orderService;
        this.productRepository = productRepository;
    }
    
    public void addProduct(String name, double price) {
        productRepository.addProduct(new Product(name, price));
    }
    
    public OrderResult processOrder(OrderRequest request) {
        return orderService.processOrder(request);
    }
}

// ===== ORDER PROCESSING (SRP + DIP) =====

/**
 * Order service - single responsibility for order processing
 * Uses dependency injection (DIP)
 */
class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PaymentProcessor paymentProcessor;
    private final EmailService emailService;
    private final Logger logger;
    private final OrderValidator orderValidator;
    private final PaymentValidator paymentValidator;
    
    public OrderService(OrderRepository orderRepository,
                       ProductRepository productRepository,
                       PaymentProcessor paymentProcessor,
                       EmailService emailService,
                       Logger logger,
                       OrderValidator orderValidator,
                       PaymentValidator paymentValidator) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.paymentProcessor = paymentProcessor;
        this.emailService = emailService;
        this.logger = logger;
        this.orderValidator = orderValidator;
        this.paymentValidator = paymentValidator;
    }
    
    public OrderResult processOrder(OrderRequest request) {
        try {
            // Validate order
            if (!orderValidator.isValid(request)) {
                return OrderResult.failure("Invalid order");
            }
            
            // Create order
            Order order = createOrder(request);
            
            // Process payment
            PaymentResult paymentResult = paymentProcessor.processPayment(order.getTotal(), request.getPaymentMethod());
            if (!paymentResult.isSuccess()) {
                return OrderResult.failure("Payment failed: " + paymentResult.getErrorMessage());
            }
            
            // Save order
            orderRepository.save(order);
            
            // Send confirmation
            emailService.sendOrderConfirmation(request.getCustomerEmail(), order.getId());
            
            // Log success
            logger.log("Order processed successfully: " + order.getId());
            
            return OrderResult.success(order.getId(), order.getTotal());
            
        } catch (Exception e) {
            logger.log("Error processing order: " + e.getMessage());
            return OrderResult.failure("System error: " + e.getMessage());
        }
    }
    
    private Order createOrder(OrderRequest request) {
        Order order = new Order();
        order.setId(generateOrderId());
        order.setCustomerEmail(request.getCustomerEmail());
        
        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findByName(itemRequest.getProductName());
            if (product != null) {
                OrderItem item = new OrderItem(product, itemRequest.getQuantity());
                order.addItem(item);
            }
        }
        
        return order;
    }
    
    private String generateOrderId() {
        return "ORD-" + System.currentTimeMillis();
    }
}

// ===== VALIDATION (SRP) =====

/**
 * Order validator - single responsibility for order validation
 */
class OrderValidator {
    public boolean isValid(OrderRequest request) {
        return request.getCustomerEmail() != null && 
               !request.getCustomerEmail().isEmpty() &&
               !request.getItems().isEmpty();
    }
}

/**
 * Payment validator - single responsibility for payment validation
 */
class PaymentValidator {
    public boolean isValid(String paymentMethod) {
        return Arrays.asList("CREDIT_CARD", "PAYPAL", "BANK_TRANSFER").contains(paymentMethod);
    }
}

// ===== REPOSITORIES (SRP + DIP) =====

/**
 * Product repository - single responsibility for product storage
 */
class ProductRepository {
    private final Map<String, Product> products = new HashMap<>();
    
    public void addProduct(Product product) {
        products.put(product.getName(), product);
    }
    
    public Product findByName(String name) {
        return products.get(name);
    }
    
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }
}

/**
 * Order repository - single responsibility for order storage
 */
class OrderRepository {
    private final Map<String, Order> orders = new HashMap<>();
    
    public void save(Order order) {
        orders.put(order.getId(), order);
    }
    
    public Order findById(String id) {
        return orders.get(id);
    }
}

// ===== PAYMENT PROCESSING (SRP + Strategy Pattern) =====

/**
 * Payment processor - uses strategy pattern for different payment methods
 */
class PaymentProcessor {
    private final Map<String, PaymentStrategy> strategies;
    
    public PaymentProcessor() {
        this.strategies = new HashMap<>();
        strategies.put("CREDIT_CARD", new CreditCardPaymentStrategy());
        strategies.put("PAYPAL", new PayPalPaymentStrategy());
        strategies.put("BANK_TRANSFER", new BankTransferPaymentStrategy());
    }
    
    public PaymentResult processPayment(double amount, String paymentMethod) {
        PaymentStrategy strategy = strategies.get(paymentMethod);
        if (strategy == null) {
            return PaymentResult.failure("Unsupported payment method");
        }
        
        return strategy.processPayment(amount);
    }
}

// ===== PAYMENT STRATEGIES (Strategy Pattern) =====

interface PaymentStrategy {
    PaymentResult processPayment(double amount);
}

class CreditCardPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentResult processPayment(double amount) {
        // Simulate credit card processing
        return PaymentResult.success("Credit card payment processed");
    }
}

class PayPalPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentResult processPayment(double amount) {
        // Simulate PayPal processing
        return PaymentResult.success("PayPal payment processed");
    }
}

class BankTransferPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentResult processPayment(double amount) {
        // Simulate bank transfer processing
        return PaymentResult.success("Bank transfer initiated");
    }
}

// ===== SERVICES (SRP) =====

/**
 * Email service - single responsibility for email operations
 */
class EmailService {
    public void sendOrderConfirmation(String email, String orderId) {
        System.out.println("Sending order confirmation to: " + email + " for order: " + orderId);
    }
}

/**
 * Logger interface - following ISP
 */
interface Logger {
    void log(String message);
}

/**
 * Console logger implementation
 */
class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}

// ===== DATA MODELS =====

class Product {
    private String name;
    private double price;
    
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    
    public String getName() { return name; }
    public double getPrice() { return price; }
}

class Order {
    private String id;
    private String customerEmail;
    private List<OrderItem> items = new ArrayList<>();
    
    public void setId(String id) { this.id = id; }
    public String getId() { return id; }
    
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public String getCustomerEmail() { return customerEmail; }
    
    public void addItem(OrderItem item) { items.add(item); }
    public List<OrderItem> getItems() { return items; }
    
    public double getTotal() {
        return items.stream()
                   .mapToDouble(OrderItem::getTotal)
                   .sum();
    }
}

class OrderItem {
    private Product product;
    private int quantity;
    
    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    
    public double getTotal() {
        return product.getPrice() * quantity;
    }
}

// ===== REQUEST/RESPONSE OBJECTS =====

class OrderRequest {
    private List<OrderItemRequest> items = new ArrayList<>();
    private String customerEmail;
    private String paymentMethod;
    
    public void addItem(String productName, int quantity) {
        items.add(new OrderItemRequest(productName, quantity));
    }
    
    public List<OrderItemRequest> getItems() { return items; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}

class OrderItemRequest {
    private String productName;
    private int quantity;
    
    public OrderItemRequest(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }
    
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
}

class OrderResult {
    private boolean success;
    private String orderId;
    private double total;
    private String errorMessage;
    
    public static OrderResult success(String orderId, double total) {
        OrderResult result = new OrderResult();
        result.success = true;
        result.orderId = orderId;
        result.total = total;
        return result;
    }
    
    public static OrderResult failure(String errorMessage) {
        OrderResult result = new OrderResult();
        result.success = false;
        result.errorMessage = errorMessage;
        return result;
    }
    
    public boolean isSuccess() { return success; }
    public String getOrderId() { return orderId; }
    public double getTotal() { return total; }
    public String getErrorMessage() { return errorMessage; }
}

class PaymentResult {
    private boolean success;
    private String message;
    private String errorMessage;
    
    public static PaymentResult success(String message) {
        PaymentResult result = new PaymentResult();
        result.success = true;
        result.message = message;
        return result;
    }
    
    public static PaymentResult failure(String errorMessage) {
        PaymentResult result = new PaymentResult();
        result.success = false;
        result.errorMessage = errorMessage;
        return result;
    }
    
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getErrorMessage() { return errorMessage; }
} 