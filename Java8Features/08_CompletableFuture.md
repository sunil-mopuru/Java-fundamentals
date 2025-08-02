# Java 8 CompletableFuture Deep Dive

## Table of Contents

1. [Introduction](#introduction)
2. [Why CompletableFuture?](#why-completablefuture)
3. [Basic Concepts](#basic-concepts)
4. [Creating CompletableFuture](#creating-completablefuture)
5. [Completing Futures](#completing-futures)
6. [Chaining Operations](#chaining-operations)
7. [Combining Futures](#combining-futures)
8. [Error Handling](#error-handling)
9. [Async Operations](#async-operations)
10. [Practical Examples](#practical-examples)
11. [Best Practices](#best-practices)
12. [Exercises](#exercises)
13. [Summary](#summary)

## Introduction

`CompletableFuture` was introduced in Java 8 to provide a more powerful and flexible way to handle asynchronous programming. It extends the `Future` interface and provides a rich API for composing asynchronous operations, handling errors, and managing complex workflows.

### Key Features:

- **Asynchronous Execution**: Execute tasks in background threads
- **Composition**: Chain multiple operations together
- **Error Handling**: Comprehensive error handling mechanisms
- **Combination**: Combine multiple futures
- **Non-blocking**: Avoid blocking threads while waiting for results
- **Functional Style**: Use lambda expressions and method references

## Why CompletableFuture?

### Problems with Traditional Future:

```java
// Traditional Future - limited and blocking
ExecutorService executor = Executors.newFixedThreadPool(10);
Future<String> future = executor.submit(() -> {
    // Simulate long-running task
    Thread.sleep(1000);
    return "Result";
});

// Blocking call - thread waits
String result = future.get(); // Blocks until completion

// No easy way to chain operations
// No built-in error handling
// No composition capabilities
```

### Benefits of CompletableFuture:

```java
// CompletableFuture - non-blocking and composable
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    // Simulate long-running task
    Thread.sleep(1000);
    return "Result";
});

// Non-blocking - register callbacks
future.thenAccept(result -> System.out.println("Got: " + result))
      .thenRun(() -> System.out.println("Completed"));

// Chain operations
future.thenApply(String::toUpperCase)
      .thenApply(s -> "Processed: " + s)
      .thenAccept(System.out::println);
```

## Basic Concepts

### Core Components:

1. **CompletableFuture<T>**: Represents a future result of type T
2. **CompletionStage<T>**: Interface that CompletableFuture implements
3. **Executor**: Thread pool for executing tasks
4. **Completion Handlers**: Callbacks for handling results

### Execution Models:

- **Async**: Execute in separate thread
- **Sync**: Execute in calling thread
- **Custom Executor**: Execute in specified thread pool

## Creating CompletableFuture

### 1. **Static Factory Methods**

```java
// Completed future with value
CompletableFuture<String> completed = CompletableFuture.completedFuture("Hello");

// Completed future with exception
CompletableFuture<String> failed = CompletableFuture.failedFuture(new RuntimeException("Error"));

// Async execution with default executor
CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
    // Simulate work
    Thread.sleep(1000);
    return "Async result";
});

// Async execution with custom executor
ExecutorService executor = Executors.newFixedThreadPool(10);
CompletableFuture<String> customAsync = CompletableFuture.supplyAsync(() -> {
    return "Custom async result";
}, executor);
```

### 2. **Manual Creation**

```java
// Create incomplete future
CompletableFuture<String> future = new CompletableFuture<>();

// Complete it later
new Thread(() -> {
    try {
        Thread.sleep(1000);
        future.complete("Manual result");
    } catch (InterruptedException e) {
        future.completeExceptionally(e);
    }
}).start();
```

### 3. **From Existing Future**

```java
ExecutorService executor = Executors.newFixedThreadPool(10);
Future<String> oldFuture = executor.submit(() -> "Old future result");

// Convert to CompletableFuture
CompletableFuture<String> newFuture = CompletableFuture.supplyAsync(() -> {
    try {
        return oldFuture.get();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}, executor);
```

## Completing Futures

### Manual Completion

```java
CompletableFuture<String> future = new CompletableFuture<>();

// Complete with value
future.complete("Success");

// Complete with exception
future.completeExceptionally(new RuntimeException("Error"));

// Check if already completed
if (!future.isDone()) {
    future.complete("Value");
}

// Get result (blocking)
try {
    String result = future.get();
    System.out.println("Result: " + result);
} catch (Exception e) {
    System.out.println("Error: " + e.getMessage());
}
```

### Non-blocking Completion

```java
CompletableFuture<String> future = new CompletableFuture<>();

// Register completion handlers
future.thenAccept(result -> System.out.println("Success: " + result))
      .exceptionally(throwable -> {
          System.out.println("Error: " + throwable.getMessage());
          return null;
      });

// Complete later
future.complete("Async result");
```

## Chaining Operations

### 1. **thenApply() - Transform Results**

```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");

// Transform result
CompletableFuture<String> upperCase = future.thenApply(String::toUpperCase);
CompletableFuture<Integer> length = future.thenApply(String::length);

// Chain multiple transformations
CompletableFuture<String> processed = future
    .thenApply(String::toUpperCase)
    .thenApply(s -> s + " World")
    .thenApply(s -> "Processed: " + s);

// Async transformation
CompletableFuture<String> asyncTransformed = future.thenApplyAsync(s -> {
    // Simulate async work
    Thread.sleep(100);
    return s.toUpperCase();
});
```

### 2. **thenAccept() - Consume Results**

```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");

// Consume result without returning value
future.thenAccept(result -> System.out.println("Received: " + result));

// Chain with thenRun
future.thenAccept(result -> System.out.println("Processing: " + result))
      .thenRun(() -> System.out.println("Completed"));
```

### 3. **thenRun() - Execute Actions**

```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");

// Execute action after completion
future.thenRun(() -> System.out.println("Future completed"));

// Chain multiple actions
future.thenAccept(System.out::println)
      .thenRun(() -> System.out.println("Step 1 done"))
      .thenRun(() -> System.out.println("Step 2 done"))
      .thenRun(() -> System.out.println("All done"));
```

### 4. **thenCompose() - Chain Futures**

```java
CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");

// Chain with another future
CompletableFuture<String> future2 = future1.thenCompose(result ->
    CompletableFuture.supplyAsync(() -> result + " World"));

// Multiple compositions
CompletableFuture<String> complex = future1
    .thenCompose(result -> CompletableFuture.supplyAsync(() -> result + " "))
    .thenCompose(result -> CompletableFuture.supplyAsync(() -> result + "World"))
    .thenCompose(result -> CompletableFuture.supplyAsync(() -> result + "!"));
```

## Combining Futures

### 1. **thenCombine() - Combine Two Futures**

```java
CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "World");

// Combine results
CompletableFuture<String> combined = future1.thenCombine(future2, (result1, result2) ->
    result1 + " " + result2);

// Async combination
CompletableFuture<String> asyncCombined = future1.thenCombineAsync(future2, (result1, result2) -> {
    // Simulate async work
    Thread.sleep(100);
    return result1 + " " + result2;
});
```

### 2. **allOf() - Wait for All Futures**

```java
CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Result 1");
CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Result 2");
CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "Result 3");

// Wait for all to complete
CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future2, future3);

allFutures.thenRun(() -> {
    System.out.println("All futures completed");
    System.out.println("Result 1: " + future1.join());
    System.out.println("Result 2: " + future2.join());
    System.out.println("Result 3: " + future3.join());
});
```

### 3. **anyOf() - Wait for Any Future**

```java
CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
    Thread.sleep(2000);
    return "Slow result";
});

CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
    Thread.sleep(500);
    return "Fast result";
});

// Wait for first to complete
CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(future1, future2);

anyFuture.thenAccept(result -> System.out.println("First completed: " + result));
```

### 4. **Complex Combinations**

```java
CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 10);
CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 20);
CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> 30);

// Combine all three
CompletableFuture<Integer> sum = future1
    .thenCombine(future2, Integer::sum)
    .thenCombine(future3, Integer::sum);

sum.thenAccept(total -> System.out.println("Total: " + total));
```

## Error Handling

### 1. **exceptionally() - Handle Exceptions**

```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    if (Math.random() > 0.5) {
        throw new RuntimeException("Random error");
    }
    return "Success";
});

// Handle exceptions
CompletableFuture<String> handled = future.exceptionally(throwable -> {
    System.out.println("Error occurred: " + throwable.getMessage());
    return "Default value";
});
```

### 2. **handle() - Handle Both Success and Error**

```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    if (Math.random() > 0.5) {
        throw new RuntimeException("Random error");
    }
    return "Success";
});

// Handle both success and error
CompletableFuture<String> handled = future.handle((result, throwable) -> {
    if (throwable != null) {
        System.out.println("Error: " + throwable.getMessage());
        return "Error fallback";
    } else {
        System.out.println("Success: " + result);
        return result.toUpperCase();
    }
});
```

### 3. **whenComplete() - Execute Regardless of Outcome**

```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    if (Math.random() > 0.5) {
        throw new RuntimeException("Random error");
    }
    return "Success";
});

// Execute regardless of success or failure
future.whenComplete((result, throwable) -> {
    if (throwable != null) {
        System.out.println("Failed with: " + throwable.getMessage());
    } else {
        System.out.println("Succeeded with: " + result);
    }
});
```

### 4. **Error Propagation**

```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    throw new RuntimeException("Initial error");
});

// Error propagates through chain
future.thenApply(String::toUpperCase)
      .thenApply(s -> "Processed: " + s)
      .exceptionally(throwable -> {
          System.out.println("Caught error: " + throwable.getMessage());
          return "Error fallback";
      });
```

## Async Operations

### 1. **Async Variants**

```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");

// Async variants execute in separate threads
CompletableFuture<String> async1 = future.thenApplyAsync(String::toUpperCase);
CompletableFuture<String> async2 = future.thenApplyAsync(s -> s + " World");
CompletableFuture<Void> async3 = future.thenAcceptAsync(System.out::println);
CompletableFuture<Void> async4 = future.thenRunAsync(() -> System.out.println("Done"));
```

### 2. **Custom Executor**

```java
ExecutorService executor = Executors.newFixedThreadPool(5);

CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello", executor);

// Use custom executor for async operations
CompletableFuture<String> async = future.thenApplyAsync(String::toUpperCase, executor);
CompletableFuture<Void> asyncAccept = future.thenAcceptAsync(System.out::println, executor);
```

### 3. **Timeout Handling**

```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    try {
        Thread.sleep(5000); // Long operation
        return "Result";
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
});

// Add timeout
CompletableFuture<String> withTimeout = future.orTimeout(2, TimeUnit.SECONDS)
    .exceptionally(throwable -> {
        if (throwable instanceof TimeoutException) {
            return "Timeout fallback";
        }
        return "Error fallback";
    });
```

## Practical Examples

### 1. **Web Service Calls**

```java
public class WebServiceClient {

    public CompletableFuture<String> fetchUserData(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            // Simulate HTTP call
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
            // Simulate HTTP call
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
```

### 2. **Database Operations**

```java
public class DatabaseService {

    public CompletableFuture<User> findUserById(String id) {
        return CompletableFuture.supplyAsync(() -> {
            // Simulate database query
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
            // Simulate database query
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

        return user.thenCombine(orders, (u, o) ->
            new UserProfile(u, o));
    }
}
```

### 3. **File Processing Pipeline**

```java
public class FileProcessor {

    public CompletableFuture<List<String>> readFile(String filename) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Simulate file reading
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
                // Simulate file writing
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
```

### 4. **Caching with CompletableFuture**

```java
public class AsyncCache<K, V> {
    private final Map<K, CompletableFuture<V>> cache = new ConcurrentHashMap<>();

    public CompletableFuture<V> get(K key, Supplier<CompletableFuture<V>> supplier) {
        return cache.computeIfAbsent(key, k -> {
            CompletableFuture<V> future = supplier.get();
            // Remove from cache when completed
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

// Usage
AsyncCache<String, String> cache = new AsyncCache<>();
CompletableFuture<String> result = cache.get("key", () ->
    CompletableFuture.supplyAsync(() -> "Expensive computation result"));
```

## Best Practices

### 1. **Use Appropriate Executors**

```java
// Good: Use custom executor for CPU-intensive tasks
ExecutorService cpuExecutor = Executors.newFixedThreadPool(
    Runtime.getRuntime().availableProcessors());

// Good: Use cached executor for I/O operations
ExecutorService ioExecutor = Executors.newCachedThreadPool();

CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    // CPU-intensive work
    return "Result";
}, cpuExecutor);
```

### 2. **Handle Exceptions Properly**

```java
// Good: Always handle exceptions
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    // Risky operation
    return "Result";
});

future.exceptionally(throwable -> {
    // Log error
    System.err.println("Error: " + throwable.getMessage());
    // Return fallback
    return "Fallback";
});
```

### 3. **Avoid Blocking Operations**

```java
// Bad: Blocking in async context
CompletableFuture<String> bad = CompletableFuture.supplyAsync(() -> {
    try {
        return someBlockingOperation(); // Blocks thread
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
});

// Good: Use async operations
CompletableFuture<String> good = CompletableFuture.supplyAsync(() -> {
    return someAsyncOperation(); // Non-blocking
});
```

### 4. **Use Timeouts**

```java
// Good: Always add timeouts
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    // Long-running operation
    return "Result";
});

CompletableFuture<String> withTimeout = future
    .orTimeout(5, TimeUnit.SECONDS)
    .exceptionally(throwable -> {
        if (throwable instanceof TimeoutException) {
            return "Timeout fallback";
        }
        return "Error fallback";
    });
```

### 5. **Compose Operations Properly**

```java
// Good: Chain operations logically
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
    .thenApply(String::toUpperCase)
    .thenApply(s -> s + " World")
    .thenApply(s -> "Processed: " + s)
    .thenAccept(System.out::println)
    .thenRun(() -> System.out.println("Completed"));
```

## Exercises

### Exercise 1: Async Calculator

Create a calculator that performs operations asynchronously:

- Add, subtract, multiply, divide operations
- Chain multiple operations
- Handle division by zero errors
- Add timeout for long operations

### Exercise 2: Web Scraper

Create a web scraper that:

- Fetches multiple URLs concurrently
- Processes the content asynchronously
- Combines results from multiple sources
- Handles network errors and timeouts

### Exercise 3: Data Pipeline

Create a data processing pipeline that:

- Reads data from multiple sources
- Processes data in parallel
- Combines results
- Writes output asynchronously
- Handles errors at each stage

### Exercise 4: Task Scheduler

Create a task scheduler that:

- Executes tasks with dependencies
- Handles task failures gracefully
- Provides progress updates
- Supports task cancellation
- Manages resource limits

## Summary

CompletableFuture provides a powerful and flexible way to handle asynchronous programming in Java 8:

### Key Benefits:

- **Non-blocking**: Execute operations without blocking threads
- **Composable**: Chain and combine operations easily
- **Error Handling**: Comprehensive error handling mechanisms
- **Flexible**: Support for both sync and async execution
- **Functional**: Use lambda expressions and method references

### Main Operations:

1. **Creation**: `supplyAsync()`, `runAsync()`, `completedFuture()`
2. **Transformation**: `thenApply()`, `thenCompose()`
3. **Consumption**: `thenAccept()`, `thenRun()`
4. **Combination**: `thenCombine()`, `allOf()`, `anyOf()`
5. **Error Handling**: `exceptionally()`, `handle()`, `whenComplete()`

### Best Practices:

1. Use appropriate executors for different types of work
2. Always handle exceptions properly
3. Add timeouts to prevent indefinite waiting
4. Avoid blocking operations in async context
5. Compose operations logically and efficiently

CompletableFuture has revolutionized asynchronous programming in Java, making it easier to write concurrent, non-blocking applications with better performance and maintainability.
