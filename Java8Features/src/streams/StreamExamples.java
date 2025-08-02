package streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Comprehensive examples demonstrating Stream API in Java 8
 */
public class StreamExamples {
    
    public static void main(String[] args) {
        System.out.println("=== Stream API Examples ===\n");
        
        // Creating streams
        creatingStreams();
        
        // Intermediate operations
        intermediateOperations();
        
        // Terminal operations
        terminalOperations();
        
        // Parallel streams
        parallelStreams();
        
        // Collectors
        collectorsExamples();
        
        // Practical examples
        practicalExamples();
        
        // Best practices
        bestPracticesExamples();
        
        // Common pitfalls
        commonPitfallsExamples();
        
        // Exercises
        exerciseSolutions();
    }
    
    // Creating streams
    private static void creatingStreams() {
        System.out.println("1. Creating Streams:");
        
        // From collections
        List<String> list = Arrays.asList("a", "b", "c");
        Stream<String> stream = list.stream();
        System.out.println("From collection: " + stream.collect(Collectors.toList()));
        
        // From arrays
        String[] array = {"d", "e", "f"};
        Stream<String> arrayStream = Arrays.stream(array);
        System.out.println("From array: " + arrayStream.collect(Collectors.toList()));
        
        // Using Stream.of()
        Stream<String> ofStream = Stream.of("g", "h", "i");
        System.out.println("Using Stream.of(): " + ofStream.collect(Collectors.toList()));
        
        // Using Stream.builder()
        Stream<String> builderStream = Stream.<String>builder()
            .add("j")
            .add("k")
            .add("l")
            .build();
        System.out.println("Using Stream.builder(): " + builderStream.collect(Collectors.toList()));
        
        // Using Stream.generate()
        Stream<String> generateStream = Stream.generate(() -> "Hello").limit(3);
        System.out.println("Using Stream.generate(): " + generateStream.collect(Collectors.toList()));
        
        // Using Stream.iterate()
        Stream<Integer> iterateStream = Stream.iterate(0, n -> n + 1).limit(5);
        System.out.println("Using Stream.iterate(): " + iterateStream.collect(Collectors.toList()));
        
        // From primitives
        IntStream intStream = IntStream.range(1, 6);
        System.out.println("IntStream range: " + intStream.boxed().collect(Collectors.toList()));
        
        System.out.println();
    }
    
    // Intermediate operations
    private static void intermediateOperations() {
        System.out.println("2. Intermediate Operations:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        
        // filter()
        List<String> longNames = names.stream()
            .filter(name -> name.length() > 4)
            .collect(Collectors.toList());
        System.out.println("Filtered long names: " + longNames);
        
        // map()
        List<String> upperNames = names.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("Uppercase names: " + upperNames);
        
        List<Integer> lengths = names.stream()
            .map(String::length)
            .collect(Collectors.toList());
        System.out.println("Name lengths: " + lengths);
        
        // flatMap()
        List<List<String>> lists = Arrays.asList(
            Arrays.asList("a", "b"),
            Arrays.asList("c", "d"),
            Arrays.asList("e", "f")
        );
        List<String> flattened = lists.stream()
            .flatMap(List::stream)
            .collect(Collectors.toList());
        System.out.println("Flattened lists: " + flattened);
        
        // distinct()
        List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 4, 5);
        List<Integer> uniqueNumbers = numbers.stream()
            .distinct()
            .collect(Collectors.toList());
        System.out.println("Unique numbers: " + uniqueNumbers);
        
        // sorted()
        List<String> sortedNames = names.stream()
            .sorted()
            .collect(Collectors.toList());
        System.out.println("Sorted names: " + sortedNames);
        
        List<String> sortedByLength = names.stream()
            .sorted(Comparator.comparing(String::length))
            .collect(Collectors.toList());
        System.out.println("Sorted by length: " + sortedByLength);
        
        // limit() and skip()
        List<Integer> numbers2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> firstFive = numbers2.stream()
            .limit(5)
            .collect(Collectors.toList());
        System.out.println("First five: " + firstFive);
        
        List<Integer> skipFirstThree = numbers2.stream()
            .skip(3)
            .collect(Collectors.toList());
        System.out.println("Skip first three: " + skipFirstThree);
        
        // peek()
        List<String> result = names.stream()
            .peek(name -> System.out.println("Processing: " + name))
            .filter(name -> name.length() > 4)
            .peek(name -> System.out.println("Filtered: " + name))
            .collect(Collectors.toList());
        System.out.println("Final result: " + result);
        System.out.println();
    }
    
    // Terminal operations
    private static void terminalOperations() {
        System.out.println("3. Terminal Operations:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        
        // forEach()
        System.out.println("forEach example:");
        names.stream().forEach(System.out::println);
        
        // collect()
        List<String> longNames = names.stream()
            .filter(name -> name.length() > 4)
            .collect(Collectors.toList());
        System.out.println("Collected long names: " + longNames);
        
        Set<String> nameSet = names.stream()
            .collect(Collectors.toSet());
        System.out.println("Collected to set: " + nameSet);
        
        Map<String, Integer> nameLengthMap = names.stream()
            .collect(Collectors.toMap(
                name -> name,
                String::length
            ));
        System.out.println("Collected to map: " + nameLengthMap);
        
        String concatenated = names.stream()
            .collect(Collectors.joining(", "));
        System.out.println("Joined: " + concatenated);
        
        // reduce()
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbers.stream()
            .reduce(0, Integer::sum);
        System.out.println("Sum: " + sum);
        
        int product = numbers.stream()
            .reduce(1, (a, b) -> a * b);
        System.out.println("Product: " + product);
        
        // findFirst() and findAny()
        Optional<String> firstLongName = names.stream()
            .filter(name -> name.length() > 4)
            .findFirst();
        System.out.println("First long name: " + firstLongName.orElse("None"));
        
        Optional<String> anyNameStartingWithA = names.stream()
            .filter(name -> name.startsWith("A"))
            .findAny();
        System.out.println("Any name starting with A: " + anyNameStartingWithA.orElse("None"));
        
        // anyMatch(), allMatch(), noneMatch()
        boolean anyLongName = names.stream()
            .anyMatch(name -> name.length() > 5);
        System.out.println("Any name longer than 5: " + anyLongName);
        
        boolean allLongNames = names.stream()
            .allMatch(name -> name.length() > 2);
        System.out.println("All names longer than 2: " + allLongNames);
        
        boolean noneStartsWithZ = names.stream()
            .noneMatch(name -> name.startsWith("Z"));
        System.out.println("No name starts with Z: " + noneStartsWithZ);
        
        // count()
        long count = names.stream()
            .filter(name -> name.length() > 4)
            .count();
        System.out.println("Count of long names: " + count);
        
        // min() and max()
        Optional<String> shortest = names.stream()
            .min(Comparator.comparing(String::length));
        System.out.println("Shortest name: " + shortest.orElse("None"));
        
        Optional<String> longest = names.stream()
            .max(Comparator.comparing(String::length));
        System.out.println("Longest name: " + longest.orElse("None"));
        
        System.out.println();
    }
    
    // Parallel streams
    private static void parallelStreams() {
        System.out.println("4. Parallel Streams:");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Sequential processing
        long startTime = System.currentTimeMillis();
        int sequentialSum = numbers.stream()
            .mapToInt(Integer::intValue)
            .sum();
        long sequentialTime = System.currentTimeMillis() - startTime;
        
        // Parallel processing
        startTime = System.currentTimeMillis();
        int parallelSum = numbers.parallelStream()
            .mapToInt(Integer::intValue)
            .sum();
        long parallelTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Sequential sum: " + sequentialSum + " in " + sequentialTime + "ms");
        System.out.println("Parallel sum: " + parallelSum + " in " + parallelTime + "ms");
        
        // Creating parallel streams
        Stream<String> parallelStream = Arrays.asList("a", "b", "c").parallelStream();
        Stream<String> parallel = Arrays.asList("d", "e", "f").stream().parallel();
        
        System.out.println();
    }
    
    // Collectors examples
    private static void collectorsExamples() {
        System.out.println("5. Collectors Examples:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
        
        // Basic collectors
        List<String> list = names.stream()
            .collect(Collectors.toList());
        System.out.println("To list: " + list);
        
        Set<String> set = names.stream()
            .collect(Collectors.toSet());
        System.out.println("To set: " + set);
        
        Collection<String> collection = names.stream()
            .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("To collection: " + collection);
        
        // Collecting to Map
        Map<String, Integer> nameLengthMap = names.stream()
            .collect(Collectors.toMap(
                name -> name,
                String::length
            ));
        System.out.println("To map: " + nameLengthMap);
        
        // Grouping and partitioning
        Map<Integer, List<String>> groupedByLength = names.stream()
            .collect(Collectors.groupingBy(String::length));
        System.out.println("Grouped by length: " + groupedByLength);
        
        Map<Boolean, List<String>> partitioned = names.stream()
            .collect(Collectors.partitioningBy(name -> name.length() > 4));
        System.out.println("Partitioned by length > 4: " + partitioned);
        
        Map<Integer, Long> countByLength = names.stream()
            .collect(Collectors.groupingBy(
                String::length,
                Collectors.counting()
            ));
        System.out.println("Count by length: " + countByLength);
        
        // Joining
        String joined = names.stream()
            .collect(Collectors.joining(", "));
        System.out.println("Joined: " + joined);
        
        String withBrackets = names.stream()
            .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("With brackets: " + withBrackets);
        
        // Summarizing
        IntSummaryStatistics stats = names.stream()
            .mapToInt(String::length)
            .summaryStatistics();
        System.out.println("Statistics: " + stats);
        
        System.out.println();
    }
    
    // Practical examples
    private static void practicalExamples() {
        System.out.println("6. Practical Examples:");
        
        // Employee management example
        employeeManagementExample();
        
        // Data analysis example
        dataAnalysisExample();
        
        // File processing example
        fileProcessingExample();
        
        System.out.println();
    }
    
    private static void employeeManagementExample() {
        System.out.println("Employee Management Example:");
        
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 25, "IT", 50000),
            new Employee("Bob", 30, "HR", 45000),
            new Employee("Charlie", 35, "IT", 60000),
            new Employee("David", 28, "Finance", 55000),
            new Employee("Eve", 32, "IT", 58000)
        );
        
        // Find all IT employees
        List<Employee> itEmployees = employees.stream()
            .filter(emp -> "IT".equals(emp.getDepartment()))
            .collect(Collectors.toList());
        System.out.println("IT Employees: " + itEmployees);
        
        // Calculate average salary by department
        Map<String, Double> avgSalaryByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.averagingDouble(Employee::getSalary)
            ));
        System.out.println("Average Salary by Department: " + avgSalaryByDept);
        
        // Find highest paid employee
        Optional<Employee> highestPaid = employees.stream()
            .max(Comparator.comparing(Employee::getSalary));
        System.out.println("Highest Paid: " + highestPaid.orElse(null));
        
        // Group employees by age range
        Map<String, List<Employee>> byAgeRange = employees.stream()
            .collect(Collectors.groupingBy(emp -> {
                if (emp.getAge() < 30) return "Young";
                else if (emp.getAge() < 35) return "Middle";
                else return "Senior";
            }));
        System.out.println("By Age Range: " + byAgeRange);
    }
    
    private static void dataAnalysisExample() {
        System.out.println("\nData Analysis Example:");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        
        // Find all prime numbers
        List<Integer> primes = numbers.stream()
            .filter(StreamExamples::isPrime)
            .collect(Collectors.toList());
        System.out.println("Primes: " + primes);
        
        // Find perfect squares
        List<Integer> perfectSquares = numbers.stream()
            .filter(n -> Math.sqrt(n) == (int) Math.sqrt(n))
            .collect(Collectors.toList());
        System.out.println("Perfect squares: " + perfectSquares);
        
        // Calculate sum of even numbers
        int sumOfEvens = numbers.stream()
            .filter(n -> n % 2 == 0)
            .mapToInt(Integer::intValue)
            .sum();
        System.out.println("Sum of evens: " + sumOfEvens);
        
        // Find numbers divisible by both 3 and 5
        List<Integer> divisibleBy3And5 = numbers.stream()
            .filter(n -> n % 3 == 0 && n % 5 == 0)
            .collect(Collectors.toList());
        System.out.println("Divisible by 3 and 5: " + divisibleBy3And5);
        
        // Group by remainder when divided by 3
        Map<Integer, List<Integer>> groupedByRemainder = numbers.stream()
            .collect(Collectors.groupingBy(n -> n % 3));
        System.out.println("Grouped by remainder (mod 3): " + groupedByRemainder);
    }
    
    private static void fileProcessingExample() {
        System.out.println("\nFile Processing Example:");
        
        // Create a sample file for demonstration
        try {
            Files.write(Paths.get("sample.txt"), 
                Arrays.asList("Hello World", "Java is awesome", "Stream API rocks", "Hello Java"));
            
            // Read lines from file
            List<String> lines = Files.readAllLines(Paths.get("sample.txt"));
            
            // Count lines
            long lineCount = lines.stream().count();
            System.out.println("Line count: " + lineCount);
            
            // Count words
            long wordCount = lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .filter(word -> !word.isEmpty())
                .count();
            System.out.println("Word count: " + wordCount);
            
            // Find longest line
            Optional<String> longestLine = lines.stream()
                .max(Comparator.comparing(String::length));
            System.out.println("Longest line: " + longestLine.orElse(""));
            
            // Find lines containing specific word
            List<String> linesWithJava = lines.stream()
                .filter(line -> line.toLowerCase().contains("java"))
                .collect(Collectors.toList());
            System.out.println("Lines with 'Java': " + linesWithJava);
            
            // Count word frequency
            Map<String, Long> wordFrequency = lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .map(String::toLowerCase)
                .filter(word -> !word.isEmpty())
                .collect(Collectors.groupingBy(
                    word -> word,
                    Collectors.counting()
                ));
            System.out.println("Word frequency: " + wordFrequency);
            
            // Find most common word
            Optional<Map.Entry<String, Long>> mostCommon = wordFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue());
            System.out.println("Most common word: " + mostCommon.orElse(null));
            
            // Clean up
            Files.deleteIfExists(Paths.get("sample.txt"));
            
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }
    
    // Best practices examples
    private static void bestPracticesExamples() {
        System.out.println("7. Best Practices Examples:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        
        // Use method references when possible
        names.stream().map(String::toUpperCase).forEach(System.out::println);
        
        // Avoid side effects in streams
        List<String> result = names.stream()
            .filter(name -> name.length() > 4)
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("Result without side effects: " + result);
        
        // Use appropriate collectors
        TreeSet<String> treeSet = names.stream()
            .collect(Collectors.toCollection(TreeSet::new));
        System.out.println("TreeSet: " + treeSet);
        
        // Handle Optional results
        Optional<String> longName = names.stream()
            .filter(name -> name.length() > 10)
            .findFirst();
        
        longName.ifPresent(System.out::println);
        String name = longName.orElse("No long name found");
        System.out.println("Name: " + name);
        
        System.out.println();
    }
    
    // Common pitfalls examples
    private static void commonPitfallsExamples() {
        System.out.println("8. Common Pitfalls Examples:");
        
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        
        // Stream can only be traversed once
        Stream<String> stream = names.stream();
        stream.forEach(System.out::println);
        // stream.forEach(System.out::println); // This would throw exception
        
        // Lazy evaluation
        System.out.println("Lazy evaluation example:");
        names.stream()
            .filter(name -> {
                System.out.println("Filtering: " + name);
                return name.length() > 4;
            })
            .collect(Collectors.toList());
        
        // Order matters in parallel streams
        System.out.println("Parallel stream (order not guaranteed):");
        names.parallelStream().forEach(System.out::println);
        
        System.out.println("Parallel stream (ordered):");
        names.parallelStream().forEachOrdered(System.out::println);
        
        // Boxing/unboxing overhead
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // Good - use primitive stream
        int sum = numbers.stream()
            .mapToInt(Integer::intValue)
            .sum();
        System.out.println("Sum using primitive stream: " + sum);
        
        System.out.println();
    }
    
    // Exercise solutions
    private static void exerciseSolutions() {
        System.out.println("9. Exercise Solutions:");
        
        // Exercise 1: Basic Stream Operations
        System.out.println("Exercise 1 - Basic Stream Operations:");
        List<Integer> numbers = IntStream.rangeClosed(1, 20).boxed().collect(Collectors.toList());
        
        List<Integer> evens = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        System.out.println("Even numbers: " + evens);
        
        List<Integer> divisibleBy3 = numbers.stream()
            .filter(n -> n % 3 == 0)
            .collect(Collectors.toList());
        System.out.println("Divisible by 3: " + divisibleBy3);
        
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum: " + sum);
        
        int product = numbers.stream().reduce(1, (a, b) -> a * b);
        System.out.println("Product: " + product);
        
        double average = numbers.stream().mapToInt(Integer::intValue).average().orElse(0);
        System.out.println("Average: " + average);
        
        // Exercise 2: String Processing
        System.out.println("\nExercise 2 - String Processing:");
        List<String> strings = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve", "Frank");
        
        List<String> longStrings = strings.stream()
            .filter(s -> s.length() > 5)
            .collect(Collectors.toList());
        System.out.println("Strings longer than 5: " + longStrings);
        
        List<String> upperStrings = strings.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("Uppercase: " + upperStrings);
        
        List<String> startingWithA = strings.stream()
            .filter(s -> s.startsWith("A"))
            .collect(Collectors.toList());
        System.out.println("Starting with A: " + startingWithA);
        
        int totalChars = strings.stream()
            .mapToInt(String::length)
            .sum();
        System.out.println("Total characters: " + totalChars);
        
        Optional<String> longest = strings.stream()
            .max(Comparator.comparing(String::length));
        System.out.println("Longest string: " + longest.orElse("None"));
        
        // Exercise 3: Employee Analysis
        System.out.println("\nExercise 3 - Employee Analysis:");
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", 25, "IT", 50000),
            new Employee("Bob", 30, "HR", 45000),
            new Employee("Charlie", 35, "IT", 60000),
            new Employee("David", 28, "Finance", 55000),
            new Employee("Eve", 32, "IT", 58000)
        );
        
        List<Employee> itEmployees = employees.stream()
            .filter(emp -> "IT".equals(emp.getDepartment()))
            .collect(Collectors.toList());
        System.out.println("IT employees: " + itEmployees);
        
        Map<String, Double> avgSalaryByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.averagingDouble(Employee::getSalary)
            ));
        System.out.println("Average salary by department: " + avgSalaryByDept);
        
        Optional<Employee> highestPaid = employees.stream()
            .max(Comparator.comparing(Employee::getSalary));
        System.out.println("Highest paid: " + highestPaid.orElse(null));
        
        Map<String, List<Employee>> byAgeRange = employees.stream()
            .collect(Collectors.groupingBy(emp -> {
                if (emp.getAge() < 30) return "Young";
                else if (emp.getAge() < 35) return "Middle";
                else return "Senior";
            }));
        System.out.println("By age range: " + byAgeRange);
        
        double avgSalary = employees.stream()
            .mapToDouble(Employee::getSalary)
            .average()
            .orElse(0);
        
        List<Employee> aboveAverage = employees.stream()
            .filter(emp -> emp.getSalary() > avgSalary)
            .collect(Collectors.toList());
        System.out.println("Above average salary: " + aboveAverage);
        
        // Exercise 4: Data Transformation
        System.out.println("\nExercise 4 - Data Transformation:");
        List<Integer> numbers2 = Arrays.asList(1, 2, 2, 3, 3, 4, 5, 6, 7, 8, 9, 10);
        
        List<Integer> unique = numbers2.stream()
            .distinct()
            .sorted(Comparator.reverseOrder())
            .limit(5)
            .map(n -> n * n)
            .collect(Collectors.toList());
        System.out.println("Transformed: " + unique);
        
        int sumOfSquares = numbers2.stream()
            .distinct()
            .sorted(Comparator.reverseOrder())
            .limit(5)
            .mapToInt(n -> n * n)
            .sum();
        System.out.println("Sum of squares: " + sumOfSquares);
        
        // Exercise 5: File Analysis
        System.out.println("\nExercise 5 - File Analysis:");
        try {
            // Create sample file
            Files.write(Paths.get("sample.txt"), 
                Arrays.asList("Hello World", "Java is awesome", "Stream API rocks", "Hello Java"));
            
            List<String> lines = Files.readAllLines(Paths.get("sample.txt"));
            
            long lineCount = lines.stream().count();
            System.out.println("Line count: " + lineCount);
            
            long wordCount = lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .filter(word -> !word.isEmpty())
                .count();
            System.out.println("Word count: " + wordCount);
            
            Optional<String> longestLine = lines.stream()
                .max(Comparator.comparing(String::length));
            System.out.println("Longest line: " + longestLine.orElse(""));
            
            Map<String, Long> wordFreq = lines.stream()
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .map(String::toLowerCase)
                .filter(word -> !word.isEmpty())
                .collect(Collectors.groupingBy(
                    word -> word,
                    Collectors.counting()
                ));
            System.out.println("Word frequency: " + wordFreq);
            
            Optional<Map.Entry<String, Long>> mostCommon = wordFreq.entrySet().stream()
                .max(Map.Entry.comparingByValue());
            System.out.println("Most common word: " + mostCommon.orElse(null));
            
            // Clean up
            Files.deleteIfExists(Paths.get("sample.txt"));
            
        } catch (IOException e) {
            System.err.println("Error in file analysis: " + e.getMessage());
        }
    }
    
    // Helper method for prime checking
    private static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
    
    // Employee class for examples
    static class Employee {
        private String name;
        private int age;
        private String department;
        private double salary;
        
        public Employee(String name, int age, String department, double salary) {
            this.name = name;
            this.age = age;
            this.department = department;
            this.salary = salary;
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getDepartment() { return department; }
        public double getSalary() { return salary; }
        
        @Override
        public String toString() {
            return name + " (" + department + ")";
        }
    }
} 