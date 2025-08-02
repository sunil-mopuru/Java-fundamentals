# Time and Space Complexity in Java

## Overview
Time and Space Complexity are fundamental concepts in computer science that help us analyze and compare the efficiency of algorithms. In Java, understanding these concepts is crucial for writing performant code and making informed decisions about which data structures and algorithms to use.

## Table of Contents
1. [What is Complexity Analysis?](#what-is-complexity-analysis)
2. [Big O Notation](#big-o-notation)
3. [Time Complexity](#time-complexity)
4. [Space Complexity](#space-complexity)
5. [Java-Specific Considerations](#java-specific-considerations)
6. [Common Complexity Classes](#common-complexity-classes)
7. [Practical Examples](#practical-examples)
8. [Best Practices](#best-practices)
9. [Practice Problems](#practice-problems)

## What is Complexity Analysis?

Complexity analysis is a method to describe the performance of an algorithm in terms of:
- **Time Complexity**: How the runtime grows with input size
- **Space Complexity**: How much memory the algorithm uses

### Why is it Important in Java?
- Helps choose the right data structures and algorithms
- Predicts performance bottlenecks
- Essential for system design and optimization
- Critical for interviews and competitive programming

## Big O Notation

Big O notation describes the upper bound of an algorithm's growth rate.

### Mathematical Definition
```
O(f(n)) = { g(n) | ∃ c > 0, n₀ > 0 : 0 ≤ g(n) ≤ c·f(n) ∀ n ≥ n₀ }
```

### Common Growth Rates (from best to worst)
1. **O(1)** - Constant time
2. **O(log n)** - Logarithmic time
3. **O(n)** - Linear time
4. **O(n log n)** - Linearithmic time
5. **O(n²)** - Quadratic time
6. **O(2ⁿ)** - Exponential time
7. **O(n!)** - Factorial time

## Time Complexity

### 1. O(1) - Constant Time
Operations that take the same time regardless of input size.

```java
// Examples of O(1) operations
public class ConstantTimeExamples {
    
    // Array access
    public int getElement(int[] arr, int index) {
        return arr[index]; // O(1)
    }
    
    // HashMap get operation (average case)
    public String getValue(Map<String, String> map, String key) {
        return map.get(key); // O(1) average case
    }
    
    // Basic arithmetic
    public int add(int a, int b) {
        return a + b; // O(1)
    }
    
    // Stack/Queue operations
    public void stackOperations(Stack<Integer> stack) {
        stack.push(5);    // O(1)
        stack.pop();      // O(1)
        stack.peek();     // O(1)
    }
}
```

### 2. O(log n) - Logarithmic Time
Operations that reduce the problem size by half in each step.

```java
public class LogarithmicTimeExamples {
    
    // Binary Search
    public int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) return mid;
            else if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
    
    // Tree operations (balanced trees)
    public class TreeNode {
        int val;
        TreeNode left, right;
        
        public TreeNode search(TreeNode root, int target) {
            if (root == null || root.val == target) return root;
            
            if (target < root.val) return search(root.left, target);
            return search(root.right, target);
        }
    }
    
    // Power calculation using divide and conquer
    public double power(double x, int n) {
        if (n == 0) return 1;
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }
        
        double half = power(x, n / 2);
        if (n % 2 == 0) return half * half;
        return half * half * x;
    }
}
```

### 3. O(n) - Linear Time
Operations that process each element once.

```java
public class LinearTimeExamples {
    
    // Array traversal
    public int findMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) max = arr[i];
        }
        return max;
    }
    
    // String operations
    public boolean isPalindrome(String str) {
        int left = 0, right = str.length() - 1;
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }
    
    // LinkedList traversal
    public class ListNode {
        int val;
        ListNode next;
        
        public int getLength(ListNode head) {
            int count = 0;
            ListNode current = head;
            while (current != null) {
                count++;
                current = current.next;
            }
            return count;
        }
    }
    
    // HashMap iteration
    public void printMap(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
```

### 4. O(n log n) - Linearithmic Time
Common in divide-and-conquer algorithms and efficient sorting.

```java
public class LinearithmicTimeExamples {
    
    // Merge Sort
    public void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }
    
    private void merge(int[] arr, int left, int mid, int right) {
        // Merge implementation
    }
    
    // Quick Sort (average case)
    public void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    
    // TreeMap operations
    public void treeMapOperations() {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "one");     // O(log n)
        treeMap.get(1);            // O(log n)
        treeMap.remove(1);         // O(log n)
    }
}
```

### 5. O(n²) - Quadratic Time
Nested loops and inefficient algorithms.

```java
public class QuadraticTimeExamples {
    
    // Bubble Sort
    public void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    
    // Selection Sort
    public void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) minIdx = j;
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }
    
    // Find all pairs
    public void findPairs(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                System.out.println(arr[i] + ", " + arr[j]);
            }
        }
    }
    
    // ArrayList contains() in a loop
    public boolean containsDuplicates(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).equals(list.get(j))) return true;
            }
        }
        return false;
    }
}
```

### 6. O(2ⁿ) - Exponential Time
Recursive algorithms without memoization.

```java
public class ExponentialTimeExamples {
    
    // Fibonacci without memoization
    public int fibonacci(int n) {
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
    
    // Subset generation
    public List<List<Integer>> generateSubsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        generateSubsetsHelper(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void generateSubsetsHelper(int[] nums, int index, 
                                     List<Integer> current, 
                                     List<List<Integer>> result) {
        if (index == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        // Include current element
        current.add(nums[index]);
        generateSubsetsHelper(nums, index + 1, current, result);
        current.remove(current.size() - 1);
        
        // Exclude current element
        generateSubsetsHelper(nums, index + 1, current, result);
    }
}
```

## Space Complexity

Space complexity measures the amount of memory an algorithm uses relative to input size.

### Types of Space Complexity

#### 1. O(1) - Constant Space
```java
public class ConstantSpaceExamples {
    
    // In-place array reversal
    public void reverseArray(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
    
    // Finding max and min
    public int[] findMinMax(int[] arr) {
        if (arr.length == 0) return new int[]{};
        
        int min = arr[0], max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) min = arr[i];
            if (arr[i] > max) max = arr[i];
        }
        return new int[]{min, max};
    }
}
```

#### 2. O(n) - Linear Space
```java
public class LinearSpaceExamples {
    
    // Creating a copy of array
    public int[] copyArray(int[] arr) {
        int[] copy = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i];
        }
        return copy;
    }
    
    // Recursive factorial
    public int factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1); // O(n) call stack space
    }
    
    // Building a new list
    public List<Integer> filterEvenNumbers(int[] arr) {
        List<Integer> result = new ArrayList<>();
        for (int num : arr) {
            if (num % 2 == 0) {
                result.add(num);
            }
        }
        return result;
    }
}
```

#### 3. O(n²) - Quadratic Space
```java
public class QuadraticSpaceExamples {
    
    // Creating all substrings
    public List<String> getAllSubstrings(String str) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j <= str.length(); j++) {
                result.add(str.substring(i, j));
            }
        }
        return result;
    }
    
    // 2D array creation
    public int[][] createMatrix(int n) {
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = i + j;
            }
        }
        return matrix;
    }
}
```

## Java-Specific Considerations

### 1. Object Overhead
Java objects have memory overhead:
```java
public class ObjectOverheadExample {
    // Each Integer object has overhead
    List<Integer> list = new ArrayList<>();
    // vs
    int[] array = new int[1000]; // More memory efficient for primitives
}
```

### 2. String Immutability
```java
public class StringMemoryExample {
    public void stringConcatenation() {
        String result = "";
        for (int i = 0; i < 1000; i++) {
            result += i; // Creates new String object each time - O(n²)
        }
        
        // Better approach
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append(i); // O(n)
        }
        String result2 = sb.toString();
    }
}
```

### 3. Collection Framework Overhead
```java
public class CollectionOverheadExample {
    // HashMap overhead
    Map<String, Integer> map = new HashMap<>();
    // Initial capacity: 16, load factor: 0.75
    // Each entry: key + value + hash + next pointer
    
    // ArrayList vs LinkedList
    List<Integer> arrayList = new ArrayList<>(); // Contiguous memory
    List<Integer> linkedList = new LinkedList<>(); // Node overhead
}
```

### 4. Garbage Collection Impact
```java
public class GarbageCollectionExample {
    public void memoryIntensive() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add("String " + i);
        }
        // GC will clean up when list goes out of scope
    }
}
```

## Common Complexity Classes

### Data Structure Operations

| Data Structure | Access | Search | Insertion | Deletion |
|----------------|--------|--------|-----------|----------|
| Array | O(1) | O(n) | O(n) | O(n) |
| LinkedList | O(n) | O(n) | O(1) | O(1) |
| HashMap | O(1) | O(1) | O(1) | O(1) |
| TreeMap | O(log n) | O(log n) | O(log n) | O(log n) |
| HashSet | O(1) | O(1) | O(1) | O(1) |
| TreeSet | O(log n) | O(log n) | O(log n) | O(log n) |

### Sorting Algorithms

| Algorithm | Time Complexity | Space Complexity | Stable |
|-----------|----------------|------------------|--------|
| Bubble Sort | O(n²) | O(1) | Yes |
| Selection Sort | O(n²) | O(1) | No |
| Insertion Sort | O(n²) | O(1) | Yes |
| Merge Sort | O(n log n) | O(n) | Yes |
| Quick Sort | O(n log n) | O(log n) | No |
| Heap Sort | O(n log n) | O(1) | No |

## Practical Examples

### Example 1: Optimizing Array Operations
```java
public class ArrayOptimizationExample {
    
    // Inefficient: O(n²)
    public boolean hasDuplicateInefficient(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] == arr[j]) return true;
            }
        }
        return false;
    }
    
    // Efficient: O(n log n)
    public boolean hasDuplicateEfficient(int[] arr) {
        Arrays.sort(arr); // O(n log n)
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1]) return true;
        }
        return false;
    }
    
    // Most Efficient: O(n)
    public boolean hasDuplicateOptimal(int[] arr) {
        Set<Integer> set = new HashSet<>();
        for (int num : arr) {
            if (!set.add(num)) return true;
        }
        return false;
    }
}
```

### Example 2: String Processing
```java
public class StringProcessingExample {
    
    // Inefficient: O(n²)
    public String reverseStringInefficient(String str) {
        String result = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            result += str.charAt(i);
        }
        return result;
    }
    
    // Efficient: O(n)
    public String reverseStringEfficient(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }
    
    // Most Efficient: O(n)
    public String reverseStringOptimal(String str) {
        return new StringBuilder(str).reverse().toString();
    }
}
```

### Example 3: Dynamic Programming
```java
public class DynamicProgrammingExample {
    
    // Exponential: O(2ⁿ)
    public int fibonacciExponential(int n) {
        if (n <= 1) return n;
        return fibonacciExponential(n - 1) + fibonacciExponential(n - 2);
    }
    
    // Linear: O(n)
    public int fibonacciLinear(int n) {
        if (n <= 1) return n;
        
        int prev = 0, curr = 1;
        for (int i = 2; i <= n; i++) {
            int next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }
    
    // With memoization: O(n)
    public int fibonacciMemoization(int n) {
        int[] memo = new int[n + 1];
        return fibonacciMemoizationHelper(n, memo);
    }
    
    private int fibonacciMemoizationHelper(int n, int[] memo) {
        if (n <= 1) return n;
        if (memo[n] != 0) return memo[n];
        
        memo[n] = fibonacciMemoizationHelper(n - 1, memo) + 
                  fibonacciMemoizationHelper(n - 2, memo);
        return memo[n];
    }
}
```

## Best Practices

### 1. Choose the Right Data Structure
```java
public class DataStructureChoice {
    
    // Use HashMap for O(1) lookups
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num)) return true;
        }
        return false;
    }
    
    // Use TreeMap for sorted operations
    public void sortedOperations() {
        TreeMap<Integer, String> sortedMap = new TreeMap<>();
        sortedMap.put(3, "three");
        sortedMap.put(1, "one");
        sortedMap.put(2, "two");
        
        // Automatically sorted
        for (Map.Entry<Integer, String> entry : sortedMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
```

### 2. Avoid Common Pitfalls
```java
public class CommonPitfalls {
    
    // Pitfall 1: Using ArrayList.contains() in loops
    public boolean badContainsCheck(List<String> list, String target) {
        for (String item : list) {
            if (list.contains(target)) return true; // O(n) in O(n) loop = O(n²)
        }
        return false;
    }
    
    // Better approach
    public boolean goodContainsCheck(List<String> list, String target) {
        return list.contains(target); // O(n) once
    }
    
    // Pitfall 2: String concatenation in loops
    public String badStringConcatenation(int n) {
        String result = "";
        for (int i = 0; i < n; i++) {
            result += i; // O(n²)
        }
        return result;
    }
    
    // Better approach
    public String goodStringConcatenation(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(i); // O(n)
        }
        return sb.toString();
    }
}
```

### 3. Profile and Measure
```java
public class PerformanceMeasurement {
    
    public void measurePerformance() {
        int[] arr = new int[100000];
        // Fill array with random data
        
        long startTime = System.nanoTime();
        bubbleSort(arr.clone());
        long bubbleTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        Arrays.sort(arr.clone());
        long quickTime = System.nanoTime() - startTime;
        
        System.out.println("Bubble Sort: " + bubbleTime + " ns");
        System.out.println("Quick Sort: " + quickTime + " ns");
    }
    
    private void bubbleSort(int[] arr) {
        // Implementation
    }
}
```

## Practice Problems

### Problem 1: Two Sum
```java
public class TwoSum {
    
    // Brute Force: O(n²)
    public int[] twoSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }
    
    // Optimized: O(n)
    public int[] twoSumOptimized(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{};
    }
}
```

### Problem 2: Valid Anagram
```java
public class ValidAnagram {
    
    // Sorting approach: O(n log n)
    public boolean isAnagramSorting(String s, String t) {
        if (s.length() != t.length()) return false;
        
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        
        Arrays.sort(sChars);
        Arrays.sort(tChars);
        
        return Arrays.equals(sChars, tChars);
    }
    
    // Counting approach: O(n)
    public boolean isAnagramCounting(String s, String t) {
        if (s.length() != t.length()) return false;
        
        int[] count = new int[26];
        
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        
        for (char c : t.toCharArray()) {
            count[c - 'a']--;
            if (count[c - 'a'] < 0) return false;
        }
        
        return true;
    }
}
```

### Problem 3: Maximum Subarray
```java
public class MaximumSubarray {
    
    // Brute Force: O(n²)
    public int maxSubArrayBruteForce(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        
        for (int i = 0; i < nums.length; i++) {
            int currentSum = 0;
            for (int j = i; j < nums.length; j++) {
                currentSum += nums[j];
                maxSum = Math.max(maxSum, currentSum);
            }
        }
        
        return maxSum;
    }
    
    // Kadane's Algorithm: O(n)
    public int maxSubArrayKadane(int[] nums) {
        int maxSoFar = nums[0];
        int maxEndingHere = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            maxEndingHere = Math.max(nums[i], maxEndingHere + nums[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        
        return maxSoFar;
    }
}
```

## Summary

Understanding time and space complexity is essential for writing efficient Java code. Key takeaways:

1. **Big O notation** helps compare algorithm efficiency
2. **Time complexity** measures runtime growth with input size
3. **Space complexity** measures memory usage growth
4. **Java-specific considerations** include object overhead, garbage collection, and collection framework characteristics
5. **Choose appropriate data structures** based on your use case
6. **Profile your code** to identify bottlenecks
7. **Practice regularly** to develop intuition for complexity analysis

Remember: The best algorithm is not always the one with the lowest theoretical complexity. Consider factors like:
- Input size and constraints
- Memory availability
- Code readability and maintainability
- Hardware characteristics
- Real-world performance requirements

## Additional Resources

- Java Collections Framework documentation
- Algorithm visualization tools
- Competitive programming platforms
- Performance profiling tools (JProfiler, VisualVM)
- Big O cheat sheet references 