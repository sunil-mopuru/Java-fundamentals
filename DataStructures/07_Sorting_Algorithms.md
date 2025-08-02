# Sorting Algorithms in Java - Complete Guide

## Overview

This comprehensive guide covers all major sorting algorithms with step-by-step explanations, visualizations, and Java implementations. Understanding sorting algorithms is fundamental to computer science and essential for optimizing data processing.

## Table of Contents

1. [Introduction to Sorting](#introduction-to-sorting)
2. [Bubble Sort](#bubble-sort)
3. [Selection Sort](#selection-sort)
4. [Insertion Sort](#insertion-sort)
5. [Merge Sort](#merge-sort)
6. [Quick Sort](#quick-sort)
7. [Heap Sort](#heap-sort)
8. [Counting Sort](#counting-sort)
9. [Radix Sort](#radix-sort)
10. [Comparison and Analysis](#comparison-and-analysis)

## Introduction to Sorting

### What is Sorting?

Sorting is the process of arranging elements in a specific order (ascending or descending) based on a comparison criterion.

### Types of Sorting Algorithms

- **Comparison-based**: Compare elements to determine order
- **Non-comparison-based**: Use properties of data to sort
- **In-place**: Sort without extra space
- **Stable**: Maintain relative order of equal elements

### Complexity Overview

| Algorithm      | Time (Best) | Time (Average) | Time (Worst) | Space    | Stable |
| -------------- | ----------- | -------------- | ------------ | -------- | ------ |
| Bubble Sort    | O(n)        | O(n²)          | O(n²)        | O(1)     | Yes    |
| Selection Sort | O(n²)       | O(n²)          | O(n²)        | O(1)     | No     |
| Insertion Sort | O(n)        | O(n²)          | O(n²)        | O(1)     | Yes    |
| Merge Sort     | O(n log n)  | O(n log n)     | O(n log n)   | O(n)     | Yes    |
| Quick Sort     | O(n log n)  | O(n log n)     | O(n²)        | O(log n) | No     |
| Heap Sort      | O(n log n)  | O(n log n)     | O(n log n)   | O(1)     | No     |
| Counting Sort  | O(n+k)      | O(n+k)         | O(n+k)       | O(k)     | Yes    |
| Radix Sort     | O(nk)       | O(nk)          | O(nk)        | O(n+k)   | Yes    |

## Bubble Sort

### How it Works

Bubble Sort repeatedly steps through the list, compares adjacent elements, and swaps them if they are in the wrong order.

### Step-by-Step Visualization

```
Initial: [64, 34, 25, 12, 22, 11, 90]

Pass 1:
[64, 34, 25, 12, 22, 11, 90] → [34, 64, 25, 12, 22, 11, 90]
[34, 64, 25, 12, 22, 11, 90] → [34, 25, 64, 12, 22, 11, 90]
[34, 25, 64, 12, 22, 11, 90] → [34, 25, 12, 64, 22, 11, 90]
[34, 25, 12, 64, 22, 11, 90] → [34, 25, 12, 22, 64, 11, 90]
[34, 25, 12, 22, 64, 11, 90] → [34, 25, 12, 22, 11, 64, 90]
[34, 25, 12, 22, 11, 64, 90] → [34, 25, 12, 22, 11, 64, 90] ✓

Pass 2:
[34, 25, 12, 22, 11, 64, 90] → [25, 34, 12, 22, 11, 64, 90]
[25, 34, 12, 22, 11, 64, 90] → [25, 12, 34, 22, 11, 64, 90]
[25, 12, 34, 22, 11, 64, 90] → [25, 12, 22, 34, 11, 64, 90]
[25, 12, 22, 34, 11, 64, 90] → [25, 12, 22, 11, 34, 64, 90]
[25, 12, 22, 11, 34, 64, 90] → [25, 12, 22, 11, 34, 64, 90] ✓

... continues until sorted ...
```

### Java Implementation

```java
public class BubbleSort {
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            // Last i elements are already in place
            for (int j = 0; j < n - i - 1; j++) {
                // Compare adjacent elements
                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j] and arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            // If no swapping occurred, array is sorted
            if (!swapped) break;
        }
    }

    // Optimized version with early termination
    public static void bubbleSortOptimized(int[] arr) {
        int n = arr.length;
        boolean swapped;
        int lastUnsorted = n - 1;

        do {
            swapped = false;
            for (int i = 0; i < lastUnsorted; i++) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                }
            }
            lastUnsorted--;
        } while (swapped);
    }
}
```

### Characteristics

- **Time Complexity**: O(n²) worst and average, O(n) best
- **Space Complexity**: O(1)
- **Stable**: Yes
- **In-place**: Yes
- **Use Case**: Educational purposes, small datasets

## Selection Sort

### How it Works

Selection Sort divides the array into a sorted and unsorted region, repeatedly selecting the smallest element from the unsorted region and placing it at the end of the sorted region.

### Step-by-Step Visualization

```
Initial: [64, 34, 25, 12, 22, 11, 90]

Pass 1: Find minimum in [64, 34, 25, 12, 22, 11, 90]
Minimum: 11 at index 5
Swap 64 and 11: [11, 34, 25, 12, 22, 64, 90]

Pass 2: Find minimum in [34, 25, 12, 22, 64, 90]
Minimum: 12 at index 3
Swap 34 and 12: [11, 12, 25, 34, 22, 64, 90]

Pass 3: Find minimum in [25, 34, 22, 64, 90]
Minimum: 22 at index 4
Swap 25 and 22: [11, 12, 22, 34, 25, 64, 90]

... continues until sorted ...
```

### Java Implementation

```java
public class SelectionSort {
    public static void selectionSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in unsorted array
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // Swap the found minimum element with the first element
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
}
```

### Characteristics

- **Time Complexity**: O(n²) for all cases
- **Space Complexity**: O(1)
- **Stable**: No
- **In-place**: Yes
- **Use Case**: Small datasets, minimizing swaps

## Insertion Sort

### How it Works

Insertion Sort builds the final sorted array one item at a time, taking each element and inserting it into its correct position in the sorted portion.

### Step-by-Step Visualization

```
Initial: [64, 34, 25, 12, 22, 11, 90]

Pass 1: Insert 34 into sorted portion [64]
[64, 34, 25, 12, 22, 11, 90] → [34, 64, 25, 12, 22, 11, 90]

Pass 2: Insert 25 into sorted portion [34, 64]
[34, 64, 25, 12, 22, 11, 90] → [25, 34, 64, 12, 22, 11, 90]

Pass 3: Insert 12 into sorted portion [25, 34, 64]
[25, 34, 64, 12, 22, 11, 90] → [12, 25, 34, 64, 22, 11, 90]

Pass 4: Insert 22 into sorted portion [12, 25, 34, 64]
[12, 25, 34, 64, 22, 11, 90] → [12, 22, 25, 34, 64, 11, 90]

... continues until sorted ...
```

### Java Implementation

```java
public class InsertionSort {
    public static void insertionSort(int[] arr) {
        int n = arr.length;

        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            // Move elements of arr[0..i-1] that are greater than key
            // to one position ahead of their current position
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }
}
```

### Characteristics

- **Time Complexity**: O(n²) worst and average, O(n) best
- **Space Complexity**: O(1)
- **Stable**: Yes
- **In-place**: Yes
- **Use Case**: Small datasets, nearly sorted data

## Merge Sort

### How it Works

Merge Sort is a divide-and-conquer algorithm that recursively divides the array into two halves, sorts them, and then merges the sorted halves.

### Step-by-Step Visualization

```
Initial: [64, 34, 25, 12, 22, 11, 90]

Divide:
[64, 34, 25, 12, 22, 11, 90]
[64, 34, 25] [12, 22, 11, 90]
[64] [34, 25] [12, 22] [11, 90]
[64] [34] [25] [12] [22] [11] [90]

Merge:
[34, 64] [25] [12, 22] [11, 90]
[25, 34, 64] [11, 12, 22, 90]
[11, 12, 22, 25, 34, 64, 90]
```

### Java Implementation

```java
public class MergeSort {
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            // Find the middle point
            int mid = left + (right - left) / 2;

            // Sort first and second halves
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            // Merge the sorted halves
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        // Find sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Create temporary arrays
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = arr[mid + 1 + j];
        }

        // Merge the temporary arrays
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

        // Copy remaining elements
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
}
```

### Characteristics

- **Time Complexity**: O(n log n) for all cases
- **Space Complexity**: O(n)
- **Stable**: Yes
- **In-place**: No
- **Use Case**: Large datasets, external sorting

## Quick Sort

### How it Works

Quick Sort picks a 'pivot' element and partitions the array around it, placing smaller elements to the left and larger elements to the right, then recursively sorts the subarrays.

### Step-by-Step Visualization

```
Initial: [64, 34, 25, 12, 22, 11, 90]

Pivot: 64 (last element)
Partition: [34, 25, 12, 22, 11] [64] [90]
Left: [34, 25, 12, 22, 11] Right: [90]

Pivot: 11 (last element of left)
Partition: [] [11] [34, 25, 12, 22]
Left: [] Right: [34, 25, 12, 22]

Pivot: 22 (last element of right)
Partition: [12] [22] [34, 25]
Left: [12] Right: [34, 25]

... continues until sorted ...
```

### Java Implementation

```java
public class QuickSort {
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // pi is partitioning index, arr[pi] is now at right place
            int pi = partition(arr, low, high);

            // Recursively sort elements before and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        // Choose the rightmost element as pivot
        int pivot = arr[high];

        // Index of smaller element
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (arr[j] <= pivot) {
                i++;

                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }
}
```

### Characteristics

- **Time Complexity**: O(n log n) average, O(n²) worst
- **Space Complexity**: O(log n) average
- **Stable**: No
- **In-place**: Yes
- **Use Case**: General-purpose sorting, large datasets

## Heap Sort

### How it Works

Heap Sort uses a binary heap data structure to sort elements. It first builds a max heap, then repeatedly extracts the maximum element and places it at the end.

### Step-by-Step Visualization

```
Initial: [64, 34, 25, 12, 22, 11, 90]

Build Max Heap:
[64, 34, 25, 12, 22, 11, 90] → [90, 34, 64, 12, 22, 11, 25]
[90, 34, 64, 12, 22, 11, 25] → [90, 34, 64, 12, 22, 11, 25]

Extract Max and Heapify:
[90, 34, 64, 12, 22, 11, 25] → [64, 34, 25, 12, 22, 11] [90]
[64, 34, 25, 12, 22, 11] → [64, 34, 25, 12, 22, 11] [90]
[64, 34, 25, 12, 22, 11] → [34, 22, 25, 12, 11] [64, 90]

... continues until sorted ...
```

### Java Implementation

```java
public class HeapSort {
    public static void heapSort(int[] arr) {
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

    // To heapify a subtree rooted with node i which is an index in arr[]
    private static void heapify(int[] arr, int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // left = 2*i + 1
        int right = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }
}
```

### Characteristics

- **Time Complexity**: O(n log n) for all cases
- **Space Complexity**: O(1)
- **Stable**: No
- **In-place**: Yes
- **Use Case**: Priority queues, in-place sorting

## Counting Sort

### How it Works

Counting Sort counts the number of occurrences of each element and uses this information to place elements in their correct sorted positions.

### Step-by-Step Visualization

```
Input: [4, 2, 1, 4, 1, 0, 3, 2]

Step 1: Count occurrences
Count: [1, 2, 2, 1, 2] (for values 0,1,2,3,4)

Step 2: Cumulative count
Cumulative: [1, 3, 5, 6, 8]

Step 3: Place elements in output array
Output: [0, 1, 1, 2, 2, 3, 4, 4]
```

### Java Implementation

```java
public class CountingSort {
    public static void countingSort(int[] arr) {
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
}
```

### Characteristics

- **Time Complexity**: O(n+k) where k is the range
- **Space Complexity**: O(k)
- **Stable**: Yes
- **In-place**: No
- **Use Case**: Integer sorting with known range

## Radix Sort

### How it Works

Radix Sort sorts numbers by processing individual digits, starting from the least significant digit to the most significant digit.

### Step-by-Step Visualization

```
Input: [170, 45, 75, 90, 802, 24, 2, 66]

Step 1: Sort by least significant digit (1s place)
[170, 90, 802, 2, 24, 45, 75, 66]

Step 2: Sort by tens place
[802, 2, 24, 45, 66, 170, 75, 90]

Step 3: Sort by hundreds place
[2, 24, 45, 66, 75, 90, 170, 802]
```

### Java Implementation

```java
public class RadixSort {
    public static void radixSort(int[] arr) {
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

    private static void countingSortByDigit(int[] arr, int n, int exp) {
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
}
```

### Characteristics

- **Time Complexity**: O(nk) where k is the number of digits
- **Space Complexity**: O(n+k)
- **Stable**: Yes
- **In-place**: No
- **Use Case**: Integer and string sorting

## Comparison and Analysis

### When to Use Each Algorithm

1. **Bubble Sort**: Educational purposes, very small datasets
2. **Selection Sort**: Small datasets, minimizing swaps
3. **Insertion Sort**: Small datasets, nearly sorted data
4. **Merge Sort**: Large datasets, stable sorting required
5. **Quick Sort**: General-purpose, large datasets
6. **Heap Sort**: In-place sorting, priority queues
7. **Counting Sort**: Integer sorting with known range
8. **Radix Sort**: Integer/string sorting with fixed digits

### Best Practices

1. **Use built-in sort** for most applications
2. **Choose based on data characteristics**
3. **Consider stability requirements**
4. **Profile performance** for critical applications
5. **Optimize for specific use cases**

## Summary

This guide provides comprehensive coverage of sorting algorithms with:

- Step-by-step visualizations
- Complete Java implementations
- Performance analysis
- Use case recommendations
- Best practices

Understanding these algorithms is crucial for:

- Algorithm design and analysis
- Performance optimization
- Technical interviews
- System design decisions

Choose the right algorithm based on your specific requirements and data characteristics!
