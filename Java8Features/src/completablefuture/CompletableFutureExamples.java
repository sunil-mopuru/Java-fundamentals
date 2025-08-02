package completablefuture;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Comprehensive examples demonstrating Java 8 CompletableFuture
 * This class showcases various aspects of asynchronous programming including
 * creation, chaining, combining, error handling, and practical applications.
 */
public class CompletableFutureExamples {
    
    public static void main(String[] args) {
        System.out.println("=== Java 8 CompletableFuture Deep Dive ===\n");
        
        basicExamples();
        chainingExamples();
        combiningExamples();
        errorHandlingExamples();
        asyncOperationsExamples();
        practicalExamples();
        exerciseSolutions();
        
        // Keep main thread alive for async operations
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    // ==================== BASIC EXAMPLES ====================
    
    private static void basicExamples() {
        System.out.println("1. Basic CompletableFuture Examples:");
        
        // Static factory methods
        CompletableFuture<String> completed = CompletableFuture.completedFuture("Hello");
        System.out.println("Completed future: " + completed.join());
        
        // Async execution with default executor
        CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
                return "Async result";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        
        // Async execution with custom executor
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CompletableFuture<String> customAsync = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
                return "Custom async result";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, executor);
        
        // Manual creation
        CompletableFuture<String> manual = new CompletableFuture<>();
        new Thread(() -> {
            try {
                Thread.sleep(200);
                manual.complete("Manual result");
            } catch (InterruptedException e) {
                manual.completeExceptionally(e);
            }
        }).start();
        
        // Non-blocking completion handlers
        async.thenAccept(result -> System.out.println("Async result: " + result));
        customAsync.thenAccept(result -> System.out.println("Custom async result: " + result));
        manual.thenAccept(result -> System.out.println("Manual result: " + result));
        
        // Blocking get (for demonstration)
        try {
            String result = async.get(1, TimeUnit.SECONDS);
            System.out.println("Blocking get result: " + result);
        } catch (Exception e) {
            System.out.println("Error getting result: " + e.getMessage());
        }
        
        executor.shutdown();
        System.out.println();
    }
    
    // ==================== CHAINING EXAMPLES ====================
    
    private static void chainingExamples() {
        System.out.println("2. Chaining Operations Examples:");
        
        // thenApply - transform results
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
        
        CompletableFuture<String> upperCase = future.thenApply(String::toUpperCase);
        CompletableFuture<Integer> length = future.thenApply(String::length);
        
        upperCase.thenAccept(result -> System.out.println("Uppercase: " + result));
        length.thenAccept(result -> System.out.println("Length: " + result));
        
        // Chain multiple transformations
        CompletableFuture<String> processed = future
            .thenApply(String::toUpperCase)
            .thenApply(s -> s + " World")
            .thenApply(s -> "Processed: " + s);
        
        processed.thenAccept(result -> System.out.println("Processed: " + result));
        
        // thenAccept - consume results
        future.thenAccept(result -> System.out.println("Consumed: " + result));
        
        // thenRun - execute actions
        future.thenRun(() -> System.out.println("Future completed"));
        
        // Chain with thenRun
        future.thenAccept(result -> System.out.println("Processing: " + result))
              .thenRun(() -> System.out.println("Step 1 done"))
              .thenRun(() -> System.out.println("Step 2 done"))
              .thenRun(() -> System.out.println("All done"));
        
        // thenCompose - chain futures
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
        
        CompletableFuture<String> future2 = future1.thenCompose(result -> 
            CompletableFuture.supplyAsync(() -> result + " World"));
        
        future2.thenAccept(result -> System.out.println("Composed: " + result));
        
        // Multiple compositions
        CompletableFuture<String> complex = future1
            .thenCompose(result -> CompletableFuture.supplyAsync(() -> result + " "))
            .thenCompose(result -> CompletableFuture.supplyAsync(() -> result + "World"))
            .thenCompose(result -> CompletableFuture.supplyAsync(() -> result + "!"));
        
        complex.thenAccept(result -> System.out.println("Complex composition: " + result));
        
        System.out.println();
    }
    
    // ==================== COMBINING EXAMPLES ====================
    
    private static void combiningExamples() {
        System.out.println("3. Combining Futures Examples:");
        
        // thenCombine - combine two futures
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "World");
        
        CompletableFuture<String> combined = future1.thenCombine(future2, (result1, result2) -> 
            result1 + " " + result2);
        
        combined.thenAccept(result -> System.out.println("Combined: " + result));
        
        // Async combination
        CompletableFuture<String> asyncCombined = future1.thenCombineAsync(future2, (result1, result2) -> {
            try {
                Thread.sleep(100);
                return result1 + " " + result2 + " (async)";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        
        asyncCombined.thenAccept(result -> System.out.println("Async combined: " + result));
        
        // allOf - wait for all futures
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "Result 3");
        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> "Result 4");
        CompletableFuture<String> future5 = CompletableFuture.supplyAsync(() -> "Result 5");
        
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future2, future3, future4, future5);
        
        allFutures.thenRun(() -> {
            System.out.println("All futures completed");
            System.out.println("Result 1: " + future1.join());
            System.out.println("Result 2: " + future2.join());
            System.out.println("Result 3: " + future3.join());
            System.out.println("Result 4: " + future4.join());
            System.out.println("Result 5: " + future5.join());
        });
        
        // anyOf - wait for any future
        CompletableFuture<String> slowFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                return "Slow result";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        
        CompletableFuture<String> fastFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
                return "Fast result";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        
        CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(slowFuture, fastFuture);
        
        anyFuture.thenAccept(result -> System.out.println("First completed: " + result));
        
        // Complex combinations
        CompletableFuture<Integer> num1 = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> num2 = CompletableFuture.supplyAsync(() -> 20);
        CompletableFuture<Integer> num3 = CompletableFuture.supplyAsync(() -> 30);
        
        CompletableFuture<Integer> sum = num1
            .thenCombine(num2, Integer::sum)
            .thenCombine(num3, Integer::sum);
        
        sum.thenAccept(total -> System.out.println("Sum: " + total));
        
        System.out.println();
    }
    
    // ==================== ERROR HANDLING EXAMPLES ====================
    
    private static void errorHandlingExamples() {
        System.out.println("4. Error Handling Examples:");
        
        // exceptionally - handle exceptions
        CompletableFuture<String> riskyFuture = CompletableFuture.supplyAsync(() -> {
            if (Math.random() > 0.5) {
                throw new RuntimeException("Random error");
            }
            return "Success";
        });
        
        CompletableFuture<String> handled = riskyFuture.exceptionally(throwable -> {
            System.out.println("Error occurred: " + throwable.getMessage());
            return "Default value";
        });
        
        handled.thenAccept(result -> System.out.println("Handled result: " + result));
        
        // handle - handle both success and error
        CompletableFuture<String> handledBoth = riskyFuture.handle((result, throwable) -> {
            if (throwable != null) {
                System.out.println("Error: " + throwable.getMessage());
                return "Error fallback";
            } else {
                System.out.println("Success: " + result);
                return result.toUpperCase();
            }
        });
        
        handledBoth.thenAccept(result -> System.out.println("Handled both: " + result));
        
        // whenComplete - execute regardless of outcome
        riskyFuture.whenComplete((result, throwable) -> {
            if (throwable != null) {
                System.out.println("Failed with: " + throwable.getMessage());
            } else {
                System.out.println("Succeeded with: " + result);
            }
        });
        
        // Error propagation
        CompletableFuture<String> errorFuture = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Initial error");
        });
        
        errorFuture.thenApply(String::toUpperCase)
                  .thenApply(s -> "Processed: " + s)
                  .exceptionally(throwable -> {
                      System.out.println("Caught error: " + throwable.getMessage());
                      return "Error fallback";
                  });
        
        System.out.println();
    }
    
    // ==================== ASYNC OPERATIONS EXAMPLES ====================
    
    private static void asyncOperationsExamples() {
        System.out.println("5. Async Operations Examples:");
        
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
        
        // Async variants execute in separate threads
        CompletableFuture<String> async1 = future.thenApplyAsync(String::toUpperCase);
        CompletableFuture<String> async2 = future.thenApplyAsync(s -> s + " World");
        CompletableFuture<Void> async3 = future.thenAcceptAsync(System.out::println);
        CompletableFuture<Void> async4 = future.thenRunAsync(() -> System.out.println("Done"));
        
        async1.thenAccept(result -> System.out.println("Async 1: " + result));
        async2.thenAccept(result -> System.out.println("Async 2: " + result));
        
        // Custom executor
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        CompletableFuture<String> customAsync = future.thenApplyAsync(String::toUpperCase, executor);
        CompletableFuture<Void> customAccept = future.thenAcceptAsync(System.out::println, executor);
        
        customAsync.thenAccept(result -> System.out.println("Custom async: " + result));
        
        // Timeout handling
        CompletableFuture<String> longFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000); // Long operation
                return "Long result";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        
        CompletableFuture<String> withTimeout = longFuture.orTimeout(2, TimeUnit.SECONDS)
            .exceptionally(throwable -> {
                if (throwable instanceof TimeoutException) {
                    return "Timeout fallback";
                }
                return "Error fallback";
            });
        
        withTimeout.thenAccept(result -> System.out.println("With timeout: " + result));
        
        executor.shutdown();
        System.out.println();
    }
    
    // ==================== PRACTICAL EXAMPLES ====================
    
    private static void practicalExamples() {
        System.out.println("6. Practical Examples:");
        
        // Web Service Client
        WebServiceClient webClient = new WebServiceClient();
        
        CompletableFuture<String> userData = webClient.fetchUserData("user123");
        CompletableFuture<String> userProfile = webClient.fetchUserProfile("user123");
        
        userData.thenAccept(data -> System.out.println("User data: " + data));
        userProfile.thenAccept(profile -> System.out.println("User profile: " + profile));
        
        // Database Service
        DatabaseService dbService = new DatabaseService();
        
        CompletableFuture<User> user = dbService.findUserById("user123");
        CompletableFuture<UserProfile> profile = dbService.getUserProfile("user123");
        
        user.thenAccept(u -> System.out.println("User: " + u));
        profile.thenAccept(p -> System.out.println("Profile: " + p));
        
        // File Processor
        FileProcessor fileProcessor = new FileProcessor();
        
        CompletableFuture<Void> processing = fileProcessor.processFile("input.txt", "output.txt");
        processing.thenRun(() -> System.out.println("File processing completed"));
        
        // Async Cache
        AsyncCache<String, String> cache = new AsyncCache<>();
        
        CompletableFuture<String> cachedResult = cache.get("key1", () -> 
            CompletableFuture.supplyAsync(() -> "Expensive computation result"));
        
        cachedResult.thenAccept(result -> System.out.println("Cached result: " + result));
        
        System.out.println();
    }
    
    // ==================== EXERCISE SOLUTIONS ====================
    
    private static void exerciseSolutions() {
        System.out.println("7. Exercise Solutions:");
        
        // Exercise 1: Async Calculator
        AsyncCalculator calculator = new AsyncCalculator();
        
        CompletableFuture<Double> addResult = calculator.add(10.0, 5.0);
        CompletableFuture<Double> multiplyResult = calculator.multiply(10.0, 5.0);
        CompletableFuture<Double> divideResult = calculator.divide(10.0, 2.0);
        
        addResult.thenAccept(result -> System.out.println("Add result: " + result));
        multiplyResult.thenAccept(result -> System.out.println("Multiply result: " + result));
        divideResult.thenAccept(result -> System.out.println("Divide result: " + result));
        
        // Chain operations
        calculator.add(5.0, 3.0)
                 .thenCompose(result -> calculator.multiply(result, 2.0))
                 .thenCompose(result -> calculator.power(result, 2.0))
                 .thenAccept(result -> System.out.println("Chained result: " + result));
        
        // Exercise 2: Web Scraper
        WebScraper scraper = new WebScraper();
        
        List<String> urls = Arrays.asList("http://example1.com", "http://example2.com", "http://example3.com");
        CompletableFuture<List<String>> scrapedData = scraper.scrapeUrls(urls);
        
        scrapedData.thenAccept(data -> System.out.println("Scraped data count: " + data.size()));
        
        // Exercise 3: Data Pipeline
        DataPipeline pipeline = new DataPipeline();
        
        CompletableFuture<Void> pipelineResult = pipeline.processData(
            Arrays.asList("source1", "source2", "source3"), "output.txt");
        
        pipelineResult.thenRun(() -> System.out.println("Data pipeline completed"));
        
        // Exercise 4: Task Scheduler
        TaskScheduler scheduler = new TaskScheduler();
        
        Task task1 = new Task("task1", () -> "Task 1 result");
        Task task2 = new Task("task2", () -> "Task 2 result");
        Task task3 = new Task("task3", () -> "Task 3 result");
        
        // task3 depends on task1 and task2
        task3.addDependency(task1);
        task3.addDependency(task2);
        
        scheduler.addTask(task1);
        scheduler.addTask(task2);
        scheduler.addTask(task3);
        
        CompletableFuture<Void> schedulerResult = scheduler.executeAll();
        schedulerResult.thenRun(() -> System.out.println("All tasks completed"));
        
        System.out.println();
    }
    
    // ==================== PRACTICAL EXAMPLE CLASSES ====================
    
    static class WebServiceClient {
        
        public CompletableFuture<String> fetchUserData(String userId) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000);
                    return "User data for " + userId;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        
        public CompletableFuture<String> fetchUserPreferences(String userId) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(800);
                    return "Preferences for " + userId;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        
        public CompletableFuture<String> fetchUserProfile(String userId) {
            CompletableFuture<String> userData = fetchUserData(userId);
            CompletableFuture<String> preferences = fetchUserPreferences(userId);
            
            return userData.thenCombine(preferences, (data, prefs) -> 
                "Profile: " + data + " | " + prefs);
        }
    }
    
    static class DatabaseService {
        
        public CompletableFuture<User> findUserById(String id) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(500);
                    return new User(id, "John Doe", "john@example.com");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        
        public CompletableFuture<List<Order>> findOrdersByUserId(String userId) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(300);
                    return Arrays.asList(
                        new Order("1", userId, 100.0),
                        new Order("2", userId, 200.0)
                    );
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        
        public CompletableFuture<UserProfile> getUserProfile(String userId) {
            CompletableFuture<User> user = findUserById(userId);
            CompletableFuture<List<Order>> orders = findOrdersByUserId(userId);
            
            return user.thenCombine(orders, UserProfile::new);
        }
    }
    
    static class FileProcessor {
        
        public CompletableFuture<List<String>> readFile(String filename) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000);
                    return Arrays.asList("line1", "line2", "line3");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        
        public CompletableFuture<List<String>> processLines(List<String> lines) {
            return CompletableFuture.supplyAsync(() -> {
                return lines.stream()
                    .map(String::toUpperCase)
                    .map(line -> "Processed: " + line)
                    .collect(Collectors.toList());
            });
        }
        
        public CompletableFuture<Void> writeFile(String filename, List<String> content) {
            return CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(500);
                    System.out.println("Written to " + filename + ": " + content);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        
        public CompletableFuture<Void> processFile(String inputFile, String outputFile) {
            return readFile(inputFile)
                .thenCompose(this::processLines)
                .thenCompose(lines -> writeFile(outputFile, lines))
                .thenRun(() -> System.out.println("File processing completed"));
        }
    }
    
    static class AsyncCache<K, V> {
        private final Map<K, CompletableFuture<V>> cache = new ConcurrentHashMap<>();
        
        public CompletableFuture<V> get(K key, Supplier<CompletableFuture<V>> supplier) {
            return cache.computeIfAbsent(key, k -> {
                CompletableFuture<V> future = supplier.get();
                // Remove from cache when completed with error
                future.whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        cache.remove(key);
                    }
                });
                return future;
            });
        }
        
        public void clear() {
            cache.clear();
        }
    }
    
    // ==================== EXERCISE SOLUTION CLASSES ====================
    
    static class AsyncCalculator {
        
        public CompletableFuture<Double> add(double a, double b) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(100);
                    return a + b;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        
        public CompletableFuture<Double> subtract(double a, double b) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(100);
                    return a - b;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        
        public CompletableFuture<Double> multiply(double a, double b) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(100);
                    return a * b;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        
        public CompletableFuture<Double> divide(double a, double b) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(100);
                    if (b == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    return a / b;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).exceptionally(throwable -> {
                System.out.println("Division error: " + throwable.getMessage());
                return 0.0;
            });
        }
        
        public CompletableFuture<Double> power(double base, double exponent) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(100);
                    return Math.pow(base, exponent);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
    
    static class WebScraper {
        
        public CompletableFuture<List<String>> scrapeUrls(List<String> urls) {
            List<CompletableFuture<String>> futures = urls.stream()
                .map(this::scrapeUrl)
                .collect(Collectors.toList());
            
            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList()));
        }
        
        private CompletableFuture<String> scrapeUrl(String url) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(500 + (long)(Math.random() * 1000));
                    return "Content from " + url;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).orTimeout(3, TimeUnit.SECONDS)
              .exceptionally(throwable -> {
                  if (throwable instanceof TimeoutException) {
                      return "Timeout for " + url;
                  }
                  return "Error for " + url;
              });
        }
    }
    
    static class DataPipeline {
        
        public CompletableFuture<Void> processData(List<String> sources, String outputFile) {
            List<CompletableFuture<List<String>>> readFutures = sources.stream()
                .map(this::readFromSource)
                .collect(Collectors.toList());
            
            return CompletableFuture.allOf(readFutures.toArray(new CompletableFuture[0]))
                .thenCompose(v -> {
                    List<String> allData = readFutures.stream()
                        .flatMap(future -> future.join().stream())
                        .collect(Collectors.toList());
                    return processData(allData);
                })
                .thenCompose(this::writeToOutput)
                .thenRun(() -> System.out.println("Data pipeline completed"));
        }
        
        private CompletableFuture<List<String>> readFromSource(String source) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(300);
                    return Arrays.asList("Data from " + source + " 1", "Data from " + source + " 2");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        
        private CompletableFuture<List<String>> processData(List<String> data) {
            return CompletableFuture.supplyAsync(() -> {
                return data.stream()
                    .map(String::toUpperCase)
                    .map(s -> "Processed: " + s)
                    .collect(Collectors.toList());
            });
        }
        
        private CompletableFuture<Void> writeToOutput(List<String> data) {
            return CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(500);
                    System.out.println("Written to output: " + data);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
    
    static class TaskScheduler {
        private final Map<String, Task> tasks = new HashMap<>();
        private final ExecutorService executor = Executors.newFixedThreadPool(5);
        
        public void addTask(Task task) {
            tasks.put(task.getName(), task);
        }
        
        public CompletableFuture<Void> executeAll() {
            List<CompletableFuture<Void>> taskFutures = tasks.values().stream()
                .map(this::executeTask)
                .collect(Collectors.toList());
            
            return CompletableFuture.allOf(taskFutures.toArray(new CompletableFuture[0]))
                .thenRun(() -> executor.shutdown());
        }
        
        private CompletableFuture<Void> executeTask(Task task) {
            // Wait for dependencies
            List<CompletableFuture<Void>> dependencies = task.getDependencies().stream()
                .map(depName -> tasks.get(depName).getFuture())
                .collect(Collectors.toList());
            
            return CompletableFuture.allOf(dependencies.toArray(new CompletableFuture[0]))
                .thenCompose(v -> task.execute(executor))
                .thenAccept(result -> System.out.println("Task " + task.getName() + " completed: " + result));
        }
    }
    
    static class Task {
        private final String name;
        private final Supplier<String> supplier;
        private final Set<String> dependencies = new HashSet<>();
        private CompletableFuture<String> future;
        
        public Task(String name, Supplier<String> supplier) {
            this.name = name;
            this.supplier = supplier;
        }
        
        public void addDependency(Task task) {
            dependencies.add(task.getName());
        }
        
        public CompletableFuture<String> execute(ExecutorService executor) {
            if (future == null) {
                future = CompletableFuture.supplyAsync(supplier, executor);
            }
            return future;
        }
        
        public String getName() { return name; }
        public Set<String> getDependencies() { return dependencies; }
        public CompletableFuture<String> getFuture() { return future; }
    }
    
    // ==================== SUPPORTING CLASSES ====================
    
    static class User {
        private final String id;
        private final String name;
        private final String email;
        
        public User(String id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
        
        public String getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        
        @Override
        public String toString() {
            return "User{id='" + id + "', name='" + name + "', email='" + email + "'}";
        }
    }
    
    static class Order {
        private final String id;
        private final String userId;
        private final double amount;
        
        public Order(String id, String userId, double amount) {
            this.id = id;
            this.userId = userId;
            this.amount = amount;
        }
        
        public String getId() { return id; }
        public String getUserId() { return userId; }
        public double getAmount() { return amount; }
        
        @Override
        public String toString() {
            return "Order{id='" + id + "', userId='" + userId + "', amount=" + amount + "}";
        }
    }
    
    static class UserProfile {
        private final User user;
        private final List<Order> orders;
        
        public UserProfile(User user, List<Order> orders) {
            this.user = user;
            this.orders = orders;
        }
        
        public User getUser() { return user; }
        public List<Order> getOrders() { return orders; }
        
        @Override
        public String toString() {
            return "UserProfile{user=" + user + ", orders=" + orders + "}";
        }
    }
} 