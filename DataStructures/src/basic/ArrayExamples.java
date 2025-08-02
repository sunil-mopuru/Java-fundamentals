package basic;

import java.util.Arrays;

/**
 * Comprehensive examples of array operations in Java
 * Demonstrates various array manipulations, searching, and sorting algorithms
 */
public class ArrayExamples {
    
    public static void main(String[] args) {
        System.out.println("=== Array Examples ===\n");
        
        // Basic array operations
        demonstrateBasicOperations();
        
        // Searching algorithms
        demonstrateSearching();
        
        // Sorting algorithms
        demonstrateSorting();
        
        // Multi-dimensional arrays
        demonstrateMultiDimensionalArrays();
        
        // Common array problems
        demonstrateCommonProblems();
    }
    
    /**
     * Demonstrates basic array operations
     */
    public static void demonstrateBasicOperations() {
        System.out.println("1. Basic Array Operations:");
        
        // Array declaration and initialization
        int[] numbers = {64, 34, 25, 12, 22, 11, 90};
        String[] names = {"Alice", "Bob", "Charlie", "David", "Eve"};
        
        System.out.println("Original numbers array: " + Arrays.toString(numbers));
        System.out.println("Original names array: " + Arrays.toString(names));
        
        // Accessing elements
        System.out.println("First element: " + numbers[0]);
        System.out.println("Last element: " + numbers[numbers.length - 1]);
        System.out.println("Array length: " + numbers.length);
        
        // Traversal methods
        System.out.print("Traversal using for loop: ");
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }
        System.out.println();
        
        System.out.print("Traversal using enhanced for loop: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        // Finding max and min
        System.out.println("Maximum element: " + findMax(numbers));
        System.out.println("Minimum element: " + findMin(numbers));
        
        // Sum and average
        System.out.println("Sum of elements: " + findSum(numbers));
        System.out.println("Average of elements: " + findAverage(numbers));
        
        System.out.println();
    }
    
    /**
     * Demonstrates various searching algorithms
     */
    public static void demonstrateSearching() {
        System.out.println("2. Searching Algorithms:");
        
        int[] sortedArray = {11, 12, 22, 25, 34, 64, 90};
        int target = 25;
        
        System.out.println("Array: " + Arrays.toString(sortedArray));
        System.out.println("Searching for: " + target);
        
        // Linear search
        int linearResult = linearSearch(sortedArray, target);
        System.out.println("Linear search result: " + linearResult);
        
        // Binary search
        int binaryResult = binarySearch(sortedArray, target);
        System.out.println("Binary search result: " + binaryResult);
        
        // Search for non-existent element
        int notFound = linearSearch(sortedArray, 100);
        System.out.println("Searching for 100: " + notFound);
        
        System.out.println();
    }
    
    /**
     * Demonstrates various sorting algorithms
     */
    public static void demonstrateSorting() {
        System.out.println("3. Sorting Algorithms:");
        
        int[] originalArray = {64, 34, 25, 12, 22, 11, 90};
        
        // Bubble sort
        int[] bubbleArray = originalArray.clone();
        bubbleSort(bubbleArray);
        System.out.println("Bubble sort: " + Arrays.toString(bubbleArray));
        
        // Selection sort
        int[] selectionArray = originalArray.clone();
        selectionSort(selectionArray);
        System.out.println("Selection sort: " + Arrays.toString(selectionArray));
        
        // Insertion sort
        int[] insertionArray = originalArray.clone();
        insertionSort(insertionArray);
        System.out.println("Insertion sort: " + Arrays.toString(insertionArray));
        
        // Built-in sort
        int[] builtinArray = originalArray.clone();
        Arrays.sort(builtinArray);
        System.out.println("Built-in sort: " + Arrays.toString(builtinArray));
        
        System.out.println();
    }
    
    /**
     * Demonstrates multi-dimensional arrays
     */
    public static void demonstrateMultiDimensionalArrays() {
        System.out.println("4. Multi-Dimensional Arrays:");
        
        // 2D Array (Matrix)
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        System.out.println("2D Matrix:");
        printMatrix(matrix);
        
        // Transpose matrix
        System.out.println("Transposed Matrix:");
        int[][] transposed = transposeMatrix(matrix);
        printMatrix(transposed);
        
        // Matrix multiplication
        int[][] matrix1 = {{1, 2}, {3, 4}};
        int[][] matrix2 = {{5, 6}, {7, 8}};
        
        System.out.println("Matrix 1:");
        printMatrix(matrix1);
        System.out.println("Matrix 2:");
        printMatrix(matrix2);
        
        int[][] multiplied = multiplyMatrices(matrix1, matrix2);
        System.out.println("Matrix Multiplication Result:");
        printMatrix(multiplied);
        
        System.out.println();
    }
    
    /**
     * Demonstrates common array problems and solutions
     */
    public static void demonstrateCommonProblems() {
        System.out.println("5. Common Array Problems:");
        
        // Reverse array
        int[] array = {1, 2, 3, 4, 5};
        System.out.println("Original array: " + Arrays.toString(array));
        reverseArray(array);
        System.out.println("Reversed array: " + Arrays.toString(array));
        
        // Find duplicates
        int[] duplicateArray = {1, 2, 3, 4, 2, 5, 6, 3};
        System.out.println("Array with duplicates: " + Arrays.toString(duplicateArray));
        findDuplicates(duplicateArray);
        
        // Rotate array
        int[] rotateArray = {1, 2, 3, 4, 5};
        System.out.println("Original array: " + Arrays.toString(rotateArray));
        rotateArrayByK(rotateArray, 2);
        System.out.println("Rotated by 2: " + Arrays.toString(rotateArray));
        
        // Find missing number
        int[] missingArray = {1, 2, 4, 5, 6};
        System.out.println("Array with missing number: " + Arrays.toString(missingArray));
        System.out.println("Missing number: " + findMissingNumber(missingArray));
        
        System.out.println();
    }
    
    // ========== HELPER METHODS ==========
    
    /**
     * Finds the maximum element in an array
     */
    public static int findMax(int[] arr) {
        if (arr.length == 0) return Integer.MIN_VALUE;
        
        int max = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }
    
    /**
     * Finds the minimum element in an array
     */
    public static int findMin(int[] arr) {
        if (arr.length == 0) return Integer.MAX_VALUE;
        
        int min = arr[0];
        for (int num : arr) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }
    
    /**
     * Calculates the sum of all elements in an array
     */
    public static int findSum(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }
    
    /**
     * Calculates the average of all elements in an array
     */
    public static double findAverage(int[] arr) {
        if (arr.length == 0) return 0.0;
        return (double) findSum(arr) / arr.length;
    }
    
    /**
     * Linear search implementation - O(n)
     */
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1; // Not found
    }
    
    /**
     * Binary search implementation - O(log n)
     * Requires sorted array
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1; // Not found
    }
    
    /**
     * Bubble sort implementation - O(n²)
     */
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    
    /**
     * Selection sort implementation - O(n²)
     */
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // Swap
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
    
    /**
     * Insertion sort implementation - O(n²)
     */
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    /**
     * Prints a 2D matrix
     */
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
    
    /**
     * Transposes a matrix
     */
    public static int[][] transposeMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] transposed = new int[cols][rows];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }
    
    /**
     * Multiplies two matrices
     */
    public static int[][] multiplyMatrices(int[][] a, int[][] b) {
        int rowsA = a.length;
        int colsA = a[0].length;
        int colsB = b[0].length;
        
        int[][] result = new int[rowsA][colsB];
        
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }
    
    /**
     * Reverses an array in-place
     */
    public static void reverseArray(int[] arr) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
    
    /**
     * Finds duplicate elements in an array
     */
    public static void findDuplicates(int[] arr) {
        System.out.print("Duplicate elements: ");
        boolean found = false;
        
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    System.out.print(arr[i] + " ");
                    found = true;
                    break;
                }
            }
        }
        
        if (!found) {
            System.out.print("None");
        }
        System.out.println();
    }
    
    /**
     * Rotates an array by k positions
     */
    public static void rotateArrayByK(int[] arr, int k) {
        int n = arr.length;
        k = k % n; // Handle cases where k > n
        
        // Reverse the entire array
        reverseArray(arr);
        
        // Reverse first k elements
        reverseArrayRange(arr, 0, k - 1);
        
        // Reverse remaining elements
        reverseArrayRange(arr, k, n - 1);
    }
    
    /**
     * Reverses a range of elements in an array
     */
    public static void reverseArrayRange(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
    
    /**
     * Finds the missing number in a sequence
     */
    public static int findMissingNumber(int[] arr) {
        int n = arr.length + 1; // Original size including missing number
        int expectedSum = n * (n + 1) / 2;
        int actualSum = findSum(arr);
        return expectedSum - actualSum;
    }
} 