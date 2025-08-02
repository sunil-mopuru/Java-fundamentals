import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Comprehensive examples demonstrating Time and Space Complexity in Java
 * This class provides practical implementations and performance measurements
 * for various complexity classes and optimization techniques.
 */
public class ComplexityExamples {
    
    public static void main(String[] args) {
        ComplexityExamples examples = new ComplexityExamples();
        
        System.out.println("=== Time and Space Complexity Examples ===\n");
        
        // Demonstrate different complexity classes
        examples.demonstrateConstantTime();
        examples.demonstrateLogarithmicTime();
        examples.demonstrateLinearTime();
        examples.demonstrateLinearithmicTime();
        examples.demonstrateQuadraticTime();
        examples.demonstrateExponentialTime();
        
        // Demonstrate space complexity
        examples.demonstrateSpaceComplexity();
        
        // Performance comparison examples
        examples.compareAlgorithms();
        
        // Java-specific optimizations
        examples.javaSpecificOptimizations();
    }
    
    // ==================== TIME COMPLEXITY EXAMPLES ====================
    
    /**
     * O(1) - Constant Time Examples
     */
    public void demonstrateConstantTime() {
        System.out.println("1. O(1) - Constant Time Examples:");
        
        // Array access
        int[] arr = {1, 2, 3, 4, 5};
        long startTime = System.nanoTime();
        int element = arr[2]; // O(1)
        long endTime = System.nanoTime();
        System.out.println("   Array access: " + (endTime - startTime) + " ns");
        
        // HashMap operations
        Map<String, Integer> map = new HashMap<>();
        map.put("key", 42);
        
        startTime = System.nanoTime();
        Integer value = map.get("key"); // O(1) average case
        endTime = System.nanoTime();
        System.out.println("   HashMap get: " + (endTime - startTime) + " ns");
        
        // Stack operations
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        
        startTime = System.nanoTime();
        int top = stack.peek(); // O(1)
        endTime = System.nanoTime();
        System.out.println("   Stack peek: " + (endTime - startTime) + " ns");
        
        System.out.println();
    }
    
    /**
     * O(log n) - Logarithmic Time Examples
     */
    public void demonstrateLogarithmicTime() {
        System.out.println("2. O(log n) - Logarithmic Time Examples:");
        
        // Binary Search
        int[] sortedArr = new int[1000000];
        for (int i = 0; i < sortedArr.length; i++) {
            sortedArr[i] = i;
        }
        
        long startTime = System.nanoTime();
        int index = binarySearch(sortedArr, 500000);
        long endTime = System.nanoTime();
        System.out.println("   Binary Search (1M elements): " + (endTime - startTime) + " ns");
        
        // TreeMap operations
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        for (int i = 0; i < 100000; i++) {
            treeMap.put(i, "value" + i);
        }
        
        startTime = System.nanoTime();
        String treeValue = treeMap.get(50000); // O(log n)
        endTime = System.nanoTime();
        System.out.println("   TreeMap get (100K elements): " + (endTime - startTime) + " ns");
        
        // Power calculation
        startTime = System.nanoTime();
        double power = powerOptimized(2.0, 1000);
        endTime = System.nanoTime();
        System.out.println("   Power calculation (2^1000): " + (endTime - startTime) + " ns");
        
        System.out.println();
    }
    
    /**
     * O(n) - Linear Time Examples
     */
    public void demonstrateLinearTime() {
        System.out.println("3. O(n) - Linear Time Examples:");
        
        // Array traversal
        int[] arr = new int[1000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        
        long startTime = System.nanoTime();
        int max = findMax(arr);
        long endTime = System.nanoTime();
        System.out.println("   Find max in array (1M elements): " + (endTime - startTime) + " ns");
        
        // String palindrome check
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            sb.append("a");
        }
        String palindrome = sb.toString() + sb.reverse().toString();
        
        startTime = System.nanoTime();
        boolean isPalindrome = isPalindrome(palindrome);
        endTime = System.nanoTime();
        System.out.println("   Palindrome check (200K chars): " + (endTime - startTime) + " ns");
        
        // LinkedList traversal
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 100000; i++) {
            linkedList.add(i);
        }
        
        startTime = System.nanoTime();
        int length = getLinkedListLength(linkedList);
        endTime = System.nanoTime();
        System.out.println("   LinkedList length (100K elements): " + (endTime - startTime) + " ns");
        
        System.out.println();
    }
    
    /**
     * O(n log n) - Linearithmic Time Examples
     */
    public void demonstrateLinearithmicTime() {
        System.out.println("4. O(n log n) - Linearithmic Time Examples:");
        
        // Merge Sort
        int[] arr = new int[100000];
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(1000000);
        }
        
        long startTime = System.nanoTime();
        mergeSort(arr, 0, arr.length - 1);
        long endTime = System.nanoTime();
        System.out.println("   Merge Sort (100K elements): " + (endTime - startTime) + " ns");
        
        // Arrays.sort() (uses TimSort - hybrid of merge sort and insertion sort)
        int[] arr2 = new int[100000];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = random.nextInt(1000000);
        }
        
        startTime = System.nanoTime();
        Arrays.sort(arr2);
        endTime = System.nanoTime();
        System.out.println("   Arrays.sort() (100K elements): " + (endTime - startTime) + " ns");
        
        System.out.println();
    }
    
    /**
     * O(n²) - Quadratic Time Examples
     */
    public void demonstrateQuadraticTime() {
        System.out.println("5. O(n²) - Quadratic Time Examples:");
        
        // Bubble Sort
        int[] arr = new int[10000]; // Smaller size due to O(n²) complexity
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100000);
        }
        
        long startTime = System.nanoTime();
        bubbleSort(arr.clone());
        long endTime = System.nanoTime();
        System.out.println("   Bubble Sort (10K elements): " + (endTime - startTime) + " ns");
        
        // Selection Sort
        startTime = System.nanoTime();
        selectionSort(arr.clone());
        endTime = System.nanoTime();
        System.out.println("   Selection Sort (10K elements): " + (endTime - startTime) + " ns");
        
        // Find all pairs (demonstration with smaller array)
        int[] smallArr = new int[1000];
        for (int i = 0; i < smallArr.length; i++) {
            smallArr[i] = random.nextInt(1000);
        }
        
        startTime = System.nanoTime();
        int pairCount = countAllPairs(smallArr);
        endTime = System.nanoTime();
        System.out.println("   Count all pairs (1K elements): " + (endTime - startTime) + " ns");
        System.out.println("   Pairs found: " + pairCount);
        
        System.out.println();
    }
    
    /**
     * O(2ⁿ) - Exponential Time Examples
     */
    public void demonstrateExponentialTime() {
        System.out.println("6. O(2ⁿ) - Exponential Time Examples:");
        
        // Fibonacci without memoization (demonstration with small values)
        for (int n = 30; n <= 40; n += 5) {
            long startTime = System.nanoTime();
            long result = fibonacciExponential(n);
            long endTime = System.nanoTime();
            System.out.println("   Fibonacci(" + n + ") without memoization: " + 
                             (endTime - startTime) + " ns, result: " + result);
        }
        
        // Fibonacci with memoization
        for (int n = 40; n <= 45; n += 5) {
            long startTime = System.nanoTime();
            long result = fibonacciMemoization(n);
            long endTime = System.nanoTime();
            System.out.println("   Fibonacci(" + n + ") with memoization: " + 
                             (endTime - startTime) + " ns, result: " + result);
        }
        
        System.out.println();
    }
    
    // ==================== SPACE COMPLEXITY EXAMPLES ====================
    
    /**
     * Space Complexity Examples
     */
    public void demonstrateSpaceComplexity() {
        System.out.println("7. Space Complexity Examples:");
        
        // O(1) - Constant Space
        int[] arr = new int[1000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        
        long startTime = System.nanoTime();
        reverseArrayInPlace(arr);
        long endTime = System.nanoTime();
        System.out.println("   In-place array reversal (1M elements): " + (endTime - startTime) + " ns");
        
        // O(n) - Linear Space
        startTime = System.nanoTime();
        int[] copy = copyArray(arr);
        endTime = System.nanoTime();
        System.out.println("   Array copy (1M elements): " + (endTime - startTime) + " ns");
        
        // O(n²) - Quadratic Space (demonstration with smaller array)
        String str = "abcdefghijklmnopqrstuvwxyz"; // 26 characters
        startTime = System.nanoTime();
        List<String> substrings = getAllSubstrings(str);
        endTime = System.nanoTime();
        System.out.println("   Generate all substrings (26 chars): " + (endTime - startTime) + " ns");
        System.out.println("   Number of substrings: " + substrings.size());
        
        System.out.println();
    }
    
    // ==================== ALGORITHM COMPARISONS ====================
    
    /**
     * Compare different algorithms for the same problem
     */
    public void compareAlgorithms() {
        System.out.println("8. Algorithm Comparison Examples:");
        
        // Two Sum Problem
        int[] nums = new int[10000];
        Random random = new Random();
        for (int i = 0; i < nums.length; i++) {
            nums[i] = random.nextInt(10000);
        }
        int target = 5000;
        
        // Brute Force O(n²)
        long startTime = System.nanoTime();
        int[] result1 = twoSumBruteForce(nums, target);
        long endTime = System.nanoTime();
        System.out.println("   Two Sum - Brute Force: " + (endTime - startTime) + " ns");
        
        // Optimized O(n)
        startTime = System.nanoTime();
        int[] result2 = twoSumOptimized(nums, target);
        endTime = System.nanoTime();
        System.out.println("   Two Sum - Optimized: " + (endTime - startTime) + " ns");
        
        // String Reversal
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append("a");
        }
        String longString = sb.toString();
        
        // Inefficient O(n²)
        startTime = System.nanoTime();
        String reversed1 = reverseStringInefficient(longString);
        endTime = System.nanoTime();
        System.out.println("   String reversal - Inefficient: " + (endTime - startTime) + " ns");
        
        // Efficient O(n)
        startTime = System.nanoTime();
        String reversed2 = reverseStringEfficient(longString);
        endTime = System.nanoTime();
        System.out.println("   String reversal - Efficient: " + (endTime - startTime) + " ns");
        
        System.out.println();
    }
    
    // ==================== JAVA-SPECIFIC OPTIMIZATIONS ====================
    
    /**
     * Java-specific optimization examples
     */
    public void javaSpecificOptimizations() {
        System.out.println("9. Java-Specific Optimizations:");
        
        // String concatenation comparison
        int iterations = 10000;
        
        // Inefficient string concatenation
        long startTime = System.nanoTime();
        String result1 = "";
        for (int i = 0; i < iterations; i++) {
            result1 += i; // Creates new String object each time
        }
        long endTime = System.nanoTime();
        System.out.println("   String concatenation with +: " + (endTime - startTime) + " ns");
        
        // Efficient string concatenation
        startTime = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append(i);
        }
        String result2 = sb.toString();
        endTime = System.nanoTime();
        System.out.println("   String concatenation with StringBuilder: " + (endTime - startTime) + " ns");
        
        // ArrayList vs LinkedList performance
        int listSize = 100000;
        
        // ArrayList performance
        ArrayList<Integer> arrayList = new ArrayList<>();
        startTime = System.nanoTime();
        for (int i = 0; i < listSize; i++) {
            arrayList.add(i);
        }
        endTime = System.nanoTime();
        System.out.println("   ArrayList add operations: " + (endTime - startTime) + " ns");
        
        // LinkedList performance
        LinkedList<Integer> linkedList = new LinkedList<>();
        startTime = System.nanoTime();
        for (int i = 0; i < listSize; i++) {
            linkedList.add(i);
        }
        endTime = System.nanoTime();
        System.out.println("   LinkedList add operations: " + (endTime - startTime) + " ns");
        
        // HashMap vs TreeMap performance
        int mapSize = 100000;
        
        // HashMap performance
        HashMap<Integer, String> hashMap = new HashMap<>();
        startTime = System.nanoTime();
        for (int i = 0; i < mapSize; i++) {
            hashMap.put(i, "value" + i);
        }
        endTime = System.nanoTime();
        System.out.println("   HashMap put operations: " + (endTime - startTime) + " ns");
        
        // TreeMap performance
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        startTime = System.nanoTime();
        for (int i = 0; i < mapSize; i++) {
            treeMap.put(i, "value" + i);
        }
        endTime = System.nanoTime();
        System.out.println("   TreeMap put operations: " + (endTime - startTime) + " ns");
        
        System.out.println();
    }
    
    // ==================== UTILITY METHODS ====================
    
    // Binary Search - O(log n)
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
    
    // Power calculation - O(log n)
    public double powerOptimized(double x, int n) {
        if (n == 0) return 1;
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }
        
        double half = powerOptimized(x, n / 2);
        if (n % 2 == 0) return half * half;
        return half * half * x;
    }
    
    // Find max - O(n)
    public int findMax(int[] arr) {
        if (arr.length == 0) throw new IllegalArgumentException("Array is empty");
        
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) max = arr[i];
        }
        return max;
    }
    
    // Palindrome check - O(n)
    public boolean isPalindrome(String str) {
        int left = 0, right = str.length() - 1;
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }
    
    // LinkedList length - O(n)
    public int getLinkedListLength(LinkedList<Integer> list) {
        int count = 0;
        for (Integer item : list) {
            count++;
        }
        return count;
    }
    
    // Merge Sort - O(n log n)
    public void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }
    
    private void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];
        
        for (int i = 0; i < n1; i++) {
            leftArr[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArr[j] = arr[mid + 1 + j];
        }
        
        int i = 0, j = 0, k = left;
        
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }
        
        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }
        
        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }
    
    // Bubble Sort - O(n²)
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
    
    // Selection Sort - O(n²)
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
    
    // Count all pairs - O(n²)
    public int countAllPairs(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                count++;
            }
        }
        return count;
    }
    
    // Fibonacci exponential - O(2ⁿ)
    public long fibonacciExponential(int n) {
        if (n <= 1) return n;
        return fibonacciExponential(n - 1) + fibonacciExponential(n - 2);
    }
    
    // Fibonacci with memoization - O(n)
    public long fibonacciMemoization(int n) {
        long[] memo = new long[n + 1];
        return fibonacciMemoizationHelper(n, memo);
    }
    
    private long fibonacciMemoizationHelper(int n, long[] memo) {
        if (n <= 1) return n;
        if (memo[n] != 0) return memo[n];
        
        memo[n] = fibonacciMemoizationHelper(n - 1, memo) + 
                  fibonacciMemoizationHelper(n - 2, memo);
        return memo[n];
    }
    
    // In-place array reversal - O(1) space
    public void reverseArrayInPlace(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
    
    // Array copy - O(n) space
    public int[] copyArray(int[] arr) {
        int[] copy = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i];
        }
        return copy;
    }
    
    // Generate all substrings - O(n²) space
    public List<String> getAllSubstrings(String str) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j <= str.length(); j++) {
                result.add(str.substring(i, j));
            }
        }
        return result;
    }
    
    // Two Sum - Brute Force O(n²)
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
    
    // Two Sum - Optimized O(n)
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
    
    // String reversal - Inefficient O(n²)
    public String reverseStringInefficient(String str) {
        String result = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            result += str.charAt(i);
        }
        return result;
    }
    
    // String reversal - Efficient O(n)
    public String reverseStringEfficient(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }
} 