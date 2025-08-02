import java.util.*;

/**
 * Comprehensive Sorting Algorithms Implementation in Java
 * This class provides implementations of all major sorting algorithms
 * with step-by-step visualization and performance measurement capabilities.
 */
public class SortingAlgorithms {
    
    public static void main(String[] args) {
        SortingAlgorithms sorter = new SortingAlgorithms();
        
        System.out.println("=== Sorting Algorithms Demo ===\n");
        
        // Test arrays
        int[] arr1 = {64, 34, 25, 12, 22, 11, 90};
        int[] arr2 = {64, 34, 25, 12, 22, 11, 90};
        int[] arr3 = {64, 34, 25, 12, 22, 11, 90};
        int[] arr4 = {64, 34, 25, 12, 22, 11, 90};
        int[] arr5 = {64, 34, 25, 12, 22, 11, 90};
        int[] arr6 = {64, 34, 25, 12, 22, 11, 90};
        int[] arr7 = {64, 34, 25, 12, 22, 11, 90};
        int[] arr8 = {64, 34, 25, 12, 22, 11, 90};
        
        System.out.println("Original Array: " + Arrays.toString(arr1));
        
        // Demonstrate each sorting algorithm
        System.out.println("\n1. Bubble Sort:");
        sorter.bubbleSortWithSteps(arr1.clone());
        
        System.out.println("\n2. Selection Sort:");
        sorter.selectionSortWithSteps(arr2.clone());
        
        System.out.println("\n3. Insertion Sort:");
        sorter.insertionSortWithSteps(arr3.clone());
        
        System.out.println("\n4. Merge Sort:");
        sorter.mergeSortWithSteps(arr4.clone());
        
        System.out.println("\n5. Quick Sort:");
        sorter.quickSortWithSteps(arr5.clone());
        
        System.out.println("\n6. Heap Sort:");
        sorter.heapSortWithSteps(arr6.clone());
        
        System.out.println("\n7. Counting Sort:");
        sorter.countingSortWithSteps(arr7.clone());
        
        System.out.println("\n8. Radix Sort:");
        sorter.radixSortWithSteps(arr8.clone());
        
        // Performance comparison
        System.out.println("\n=== Performance Comparison ===");
        sorter.compareSortingAlgorithms();
    }
    
    // ==================== BUBBLE SORT ====================
    
    public void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            
            if (!swapped) break;
        }
    }
    
    public void bubbleSortWithSteps(int[] arr) {
        int n = arr.length;
        boolean swapped;
        
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            System.out.println("Pass " + (i + 1) + ": " + Arrays.toString(arr));
            
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    System.out.println("  Swapping " + arr[j] + " and " + arr[j + 1]);
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            
            if (!swapped) {
                System.out.println("  No swaps needed - array is sorted!");
                break;
            }
        }
        System.out.println("Final: " + Arrays.toString(arr));
    }
    
    // ==================== SELECTION SORT ====================
    
    public void selectionSort(int[] arr) {
        int n = arr.length;
        
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
    
    public void selectionSortWithSteps(int[] arr) {
        int n = arr.length;
        
        for (int i = 0; i < n - 1; i++) {
            System.out.println("Pass " + (i + 1) + ": " + Arrays.toString(arr));
            
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            
            if (minIndex != i) {
                System.out.println("  Minimum found: " + arr[minIndex] + " at index " + minIndex);
                System.out.println("  Swapping " + arr[i] + " and " + arr[minIndex]);
                int temp = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = temp;
            }
        }
        System.out.println("Final: " + Arrays.toString(arr));
    }
    
    // ==================== INSERTION SORT ====================
    
    public void insertionSort(int[] arr) {
        int n = arr.length;
        
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }
    
    public void insertionSortWithSteps(int[] arr) {
        int n = arr.length;
        
        for (int i = 1; i < n; i++) {
            System.out.println("Pass " + i + ": " + Arrays.toString(arr));
            
            int key = arr[i];
            System.out.println("  Inserting " + key + " into sorted portion");
            
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
            
            System.out.println("  After insertion: " + Arrays.toString(arr));
        }
        System.out.println("Final: " + Arrays.toString(arr));
    }
    
    // ==================== MERGE SORT ====================
    
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
        
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        
        for (int i = 0; i < n1; i++) {
            leftArray[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = arr[mid + 1 + j];
        }
        
        int i = 0, j = 0, k = left;
        
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }
        
        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }
        
        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }
    
    public void mergeSortWithSteps(int[] arr) {
        System.out.println("Starting Merge Sort: " + Arrays.toString(arr));
        mergeSortWithStepsHelper(arr, 0, arr.length - 1, 0);
        System.out.println("Final: " + Arrays.toString(arr));
    }
    
    private void mergeSortWithStepsHelper(int[] arr, int left, int right, int depth) {
        String indent = "  ".repeat(depth);
        
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            System.out.println(indent + "Dividing: " + Arrays.toString(Arrays.copyOfRange(arr, left, right + 1)));
            
            mergeSortWithStepsHelper(arr, left, mid, depth + 1);
            mergeSortWithStepsHelper(arr, mid + 1, right, depth + 1);
            
            System.out.println(indent + "Merging: " + Arrays.toString(Arrays.copyOfRange(arr, left, right + 1)));
            merge(arr, left, mid, right);
            System.out.println(indent + "After merge: " + Arrays.toString(Arrays.copyOfRange(arr, left, right + 1)));
        }
    }
    
    // ==================== QUICK SORT ====================
    
    public void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }
    
    public void quickSortWithSteps(int[] arr) {
        System.out.println("Starting Quick Sort: " + Arrays.toString(arr));
        quickSortWithStepsHelper(arr, 0, arr.length - 1, 0);
        System.out.println("Final: " + Arrays.toString(arr));
    }
    
    private void quickSortWithStepsHelper(int[] arr, int low, int high, int depth) {
        String indent = "  ".repeat(depth);
        
        if (low < high) {
            System.out.println(indent + "Partitioning: " + Arrays.toString(Arrays.copyOfRange(arr, low, high + 1)));
            
            int pi = partitionWithSteps(arr, low, high, indent);
            
            System.out.println(indent + "Pivot position: " + pi + ", value: " + arr[pi]);
            
            quickSortWithStepsHelper(arr, low, pi - 1, depth + 1);
            quickSortWithStepsHelper(arr, pi + 1, high, depth + 1);
        }
    }
    
    private int partitionWithSteps(int[] arr, int low, int high, String indent) {
        int pivot = arr[high];
        System.out.println(indent + "  Pivot: " + pivot);
        
        int i = (low - 1);
        
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                
                if (i != j) {
                    System.out.println(indent + "  Swapping " + arr[i] + " and " + arr[j]);
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        System.out.println(indent + "  Final pivot position: " + (i + 1));
        return i + 1;
    }
    
    // ==================== HEAP SORT ====================
    
    public void heapSort(int[] arr) {
        int n = arr.length;
        
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        
        // One by one extract an element from heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            
            // Call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }
    
    private void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }
        
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            
            heapify(arr, n, largest);
        }
    }
    
    public void heapSortWithSteps(int[] arr) {
        int n = arr.length;
        System.out.println("Starting Heap Sort: " + Arrays.toString(arr));
        
        // Build heap
        System.out.println("Building Max Heap:");
        for (int i = n / 2 - 1; i >= 0; i--) {
            System.out.println("  Heapifying at index " + i);
            heapify(arr, n, i);
            System.out.println("  After heapify: " + Arrays.toString(arr));
        }
        
        // Extract elements
        System.out.println("Extracting elements from heap:");
        for (int i = n - 1; i > 0; i--) {
            System.out.println("  Moving root " + arr[0] + " to position " + i);
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            
            heapify(arr, i, 0);
            System.out.println("  After extraction: " + Arrays.toString(arr));
        }
        
        System.out.println("Final: " + Arrays.toString(arr));
    }
    
    // ==================== COUNTING SORT ====================
    
    public void countingSort(int[] arr) {
        int n = arr.length;
        
        // Find the maximum element
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        
        // Create count array
        int[] count = new int[max + 1];
        
        // Store count of each element
        for (int i = 0; i < n; i++) {
            count[arr[i]]++;
        }
        
        // Change count[i] so that count[i] now contains actual
        // position of this element in output array
        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }
        
        // Build the output array
        int[] output = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            output[count[arr[i]] - 1] = arr[i];
            count[arr[i]]--;
        }
        
        // Copy the output array to arr
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }
    
    public void countingSortWithSteps(int[] arr) {
        int n = arr.length;
        System.out.println("Starting Counting Sort: " + Arrays.toString(arr));
        
        // Find max
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        System.out.println("Maximum value: " + max);
        
        // Count array
        int[] count = new int[max + 1];
        for (int i = 0; i < n; i++) {
            count[arr[i]]++;
        }
        System.out.println("Count array: " + Arrays.toString(count));
        
        // Cumulative count
        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }
        System.out.println("Cumulative count: " + Arrays.toString(count));
        
        // Build output
        int[] output = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            output[count[arr[i]] - 1] = arr[i];
            count[arr[i]]--;
        }
        
        // Copy back
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
        
        System.out.println("Final: " + Arrays.toString(arr));
    }
    
    // ==================== RADIX SORT ====================
    
    public void radixSort(int[] arr) {
        int n = arr.length;
        
        // Find the maximum number to know number of digits
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        
        // Do counting sort for every digit
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, n, exp);
        }
    }
    
    private void countingSortByDigit(int[] arr, int n, int exp) {
        int[] output = new int[n];
        int[] count = new int[10];
        
        // Store count of occurrences in count[]
        for (int i = 0; i < n; i++) {
            count[(arr[i] / exp) % 10]++;
        }
        
        // Change count[i] so that count[i] now contains actual
        // position of this digit in output[]
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        // Build the output array
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        
        // Copy the output array to arr[]
        for (int i = 0; i < n; i++) {
            arr[i] = output[i];
        }
    }
    
    public void radixSortWithSteps(int[] arr) {
        int n = arr.length;
        System.out.println("Starting Radix Sort: " + Arrays.toString(arr));
        
        // Find max
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        System.out.println("Maximum value: " + max);
        
        // Sort by each digit
        for (int exp = 1; max / exp > 0; exp *= 10) {
            System.out.println("Sorting by digit at position " + exp + ":");
            countingSortByDigit(arr, n, exp);
            System.out.println("After sorting: " + Arrays.toString(arr));
        }
        
        System.out.println("Final: " + Arrays.toString(arr));
    }
    
    // ==================== PERFORMANCE COMPARISON ====================
    
    public void compareSortingAlgorithms() {
        int[] sizes = {100, 1000, 10000};
        
        for (int size : sizes) {
            System.out.println("\nArray size: " + size);
            
            // Generate test data
            int[] arr1 = generateRandomArray(size);
            int[] arr2 = arr1.clone();
            int[] arr3 = arr1.clone();
            int[] arr4 = arr1.clone();
            int[] arr5 = arr1.clone();
            int[] arr6 = arr1.clone();
            
            // Test different algorithms
            long startTime = System.nanoTime();
            Arrays.sort(arr1); // Java's built-in sort
            long javaTime = System.nanoTime() - startTime;
            
            startTime = System.nanoTime();
            quickSort(arr2, 0, arr2.length - 1);
            long quickTime = System.nanoTime() - startTime;
            
            startTime = System.nanoTime();
            mergeSort(arr3, 0, arr3.length - 1);
            long mergeTime = System.nanoTime() - startTime;
            
            startTime = System.nanoTime();
            heapSort(arr4);
            long heapTime = System.nanoTime() - startTime;
            
            startTime = System.nanoTime();
            insertionSort(arr5);
            long insertionTime = System.nanoTime() - startTime;
            
            startTime = System.nanoTime();
            bubbleSort(arr6);
            long bubbleTime = System.nanoTime() - startTime;
            
            System.out.printf("Java Sort: %d ns\n", javaTime);
            System.out.printf("Quick Sort: %d ns\n", quickTime);
            System.out.printf("Merge Sort: %d ns\n", mergeTime);
            System.out.printf("Heap Sort: %d ns\n", heapTime);
            System.out.printf("Insertion Sort: %d ns\n", insertionTime);
            System.out.printf("Bubble Sort: %d ns\n", bubbleTime);
        }
    }
    
    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(10000);
        }
        return arr;
    }
} 