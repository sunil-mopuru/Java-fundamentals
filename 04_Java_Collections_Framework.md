# Java Collections Framework - Deep Dive

## 1. Introduction to Collections

The Java Collections Framework is a unified architecture for representing and manipulating collections. It provides a set of interfaces, implementations, and algorithms for storing and processing groups of objects.

### What are Collections?

Collections are objects that group multiple elements into a single unit. They are used to store, retrieve, manipulate, and communicate aggregate data.

### Collection Hierarchy

```
Collection (Interface)
├── List (Interface)
│   ├── ArrayList
│   ├── LinkedList
│   └── Vector
├── Set (Interface)
│   ├── HashSet
│   ├── LinkedHashSet
│   └── TreeSet
└── Queue (Interface)
    ├── PriorityQueue
    └── LinkedList

Map (Interface)
├── HashMap
├── LinkedHashMap
├── TreeMap
└── Hashtable
```

## 2. List Interface

Lists maintain the order of elements and allow duplicate elements.

### ArrayList

ArrayList is a resizable array implementation of the List interface.

```java
import java.util.ArrayList;
import java.util.List;

public class ArrayListExample {
    public static void main(String[] args) {
        // Creating ArrayList
        ArrayList<String> fruits = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        // Adding elements
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Orange");
        fruits.add("Apple");  // Duplicates allowed

        numbers.add(10);
        numbers.add(20);
        numbers.add(30);

        // Accessing elements
        System.out.println("First fruit: " + fruits.get(0));
        System.out.println("Size: " + fruits.size());

        // Iterating through ArrayList
        System.out.println("Fruits:");
        for (String fruit : fruits) {
            System.out.println(fruit);
        }

        // Using forEach with lambda
        System.out.println("Numbers:");
        numbers.forEach(System.out::println);

        // Checking if element exists
        System.out.println("Contains Apple: " + fruits.contains("Apple"));

        // Removing elements
        fruits.remove("Banana");
        fruits.remove(0);  // Remove by index

        // Clearing the list
        numbers.clear();
        System.out.println("Numbers size after clear: " + numbers.size());
    }
}
```

### LinkedList

LinkedList is a doubly-linked list implementation that provides efficient insertion and deletion.

```java
import java.util.LinkedList;

public class LinkedListExample {
    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<>();

        // Adding elements
        linkedList.add("First");
        linkedList.add("Second");
        linkedList.add("Third");

        // Adding at specific positions
        linkedList.addFirst("Zero");
        linkedList.addLast("Fourth");
        linkedList.add(2, "Inserted");

        System.out.println("LinkedList: " + linkedList);

        // Accessing elements
        System.out.println("First: " + linkedList.getFirst());
        System.out.println("Last: " + linkedList.getLast());
        System.out.println("Element at index 2: " + linkedList.get(2));

        // Removing elements
        linkedList.removeFirst();
        linkedList.removeLast();
        linkedList.remove("Second");

        System.out.println("After removals: " + linkedList);

        // LinkedList as Queue
        LinkedList<Integer> queue = new LinkedList<>();
        queue.offer(1);  // Add to end
        queue.offer(2);
        queue.offer(3);

        System.out.println("Queue: " + queue);
        System.out.println("Peek: " + queue.peek());  // View first element
        System.out.println("Poll: " + queue.poll());  // Remove and return first
        System.out.println("Queue after poll: " + queue);

        // LinkedList as Stack
        LinkedList<String> stack = new LinkedList<>();
        stack.push("First");   // Push to top
        stack.push("Second");
        stack.push("Third");

        System.out.println("Stack: " + stack);
        System.out.println("Peek: " + stack.peek());  // View top element
        System.out.println("Pop: " + stack.pop());    // Remove and return top
        System.out.println("Stack after pop: " + stack);
    }
}
```

## 3. Set Interface

Sets do not allow duplicate elements and do not maintain insertion order (except LinkedHashSet).

### HashSet

HashSet stores elements using a hash table for fast access.

```java
import java.util.HashSet;
import java.util.Set;

public class HashSetExample {
    public static void main(String[] args) {
        HashSet<String> hashSet = new HashSet<>();

        // Adding elements
        hashSet.add("Apple");
        hashSet.add("Banana");
        hashSet.add("Orange");
        hashSet.add("Apple");  // Duplicate - will be ignored

        System.out.println("HashSet: " + hashSet);
        System.out.println("Size: " + hashSet.size());

        // Checking elements
        System.out.println("Contains Apple: " + hashSet.contains("Apple"));
        System.out.println("Contains Grape: " + hashSet.contains("Grape"));

        // Removing elements
        hashSet.remove("Banana");
        System.out.println("After removing Banana: " + hashSet);

        // Iterating
        System.out.println("Elements:");
        for (String fruit : hashSet) {
            System.out.println(fruit);
        }

        // Set operations
        HashSet<String> set1 = new HashSet<>();
        set1.add("A");
        set1.add("B");
        set1.add("C");

        HashSet<String> set2 = new HashSet<>();
        set2.add("B");
        set2.add("C");
        set2.add("D");

        // Union
        HashSet<String> union = new HashSet<>(set1);
        union.addAll(set2);
        System.out.println("Union: " + union);

        // Intersection
        HashSet<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        System.out.println("Intersection: " + intersection);

        // Difference
        HashSet<String> difference = new HashSet<>(set1);
        difference.removeAll(set2);
        System.out.println("Difference: " + difference);
    }
}
```

### TreeSet

TreeSet stores elements in a sorted order using a Red-Black tree.

```java
import java.util.TreeSet;

public class TreeSetExample {
    public static void main(String[] args) {
        TreeSet<Integer> treeSet = new TreeSet<>();

        // Adding elements
        treeSet.add(30);
        treeSet.add(10);
        treeSet.add(50);
        treeSet.add(20);
        treeSet.add(40);

        System.out.println("TreeSet: " + treeSet);  // Naturally sorted

        // First and last elements
        System.out.println("First: " + treeSet.first());
        System.out.println("Last: " + treeSet.last());

        // Subset operations
        System.out.println("HeadSet (less than 30): " + treeSet.headSet(30));
        System.out.println("TailSet (greater than or equal to 30): " + treeSet.tailSet(30));
        System.out.println("SubSet (20 to 40): " + treeSet.subSet(20, 40));

        // Higher and lower
        System.out.println("Higher than 25: " + treeSet.higher(25));
        System.out.println("Lower than 25: " + treeSet.lower(25));

        // Ceiling and floor
        System.out.println("Ceiling of 25: " + treeSet.ceiling(25));
        System.out.println("Floor of 25: " + treeSet.floor(25));

        // Polling (removing first/last)
        System.out.println("Poll first: " + treeSet.pollFirst());
        System.out.println("Poll last: " + treeSet.pollLast());
        System.out.println("After polling: " + treeSet);

        // TreeSet with custom objects
        TreeSet<Person> people = new TreeSet<>();
        people.add(new Person("Alice", 25));
        people.add(new Person("Bob", 30));
        people.add(new Person("Charlie", 20));

        System.out.println("People sorted by age:");
        people.forEach(System.out::println);
    }
}

class Person implements Comparable<Person> {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Person other) {
        return Integer.compare(this.age, other.age);
    }

    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}
```

## 4. Map Interface

Maps store key-value pairs and do not allow duplicate keys.

### HashMap

HashMap stores key-value pairs using a hash table for fast access.

```java
import java.util.HashMap;
import java.util.Map;

public class HashMapExample {
    public static void main(String[] args) {
        HashMap<String, Integer> hashMap = new HashMap<>();

        // Adding key-value pairs
        hashMap.put("Apple", 1);
        hashMap.put("Banana", 2);
        hashMap.put("Orange", 3);
        hashMap.put("Apple", 4);  // Overwrites previous value

        System.out.println("HashMap: " + hashMap);

        // Accessing values
        System.out.println("Value for Apple: " + hashMap.get("Apple"));
        System.out.println("Value for Grape: " + hashMap.get("Grape"));  // null

        // Checking keys and values
        System.out.println("Contains key Apple: " + hashMap.containsKey("Apple"));
        System.out.println("Contains value 2: " + hashMap.containsValue(2));

        // Removing entries
        hashMap.remove("Banana");
        System.out.println("After removing Banana: " + hashMap);

        // Iterating through HashMap
        System.out.println("Key-Value pairs:");
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // Iterating keys only
        System.out.println("Keys:");
        for (String key : hashMap.keySet()) {
            System.out.println(key);
        }

        // Iterating values only
        System.out.println("Values:");
        for (Integer value : hashMap.values()) {
            System.out.println(value);
        }

        // Using forEach with lambda
        hashMap.forEach((key, value) ->
            System.out.println(key + " = " + value));

        // HashMap with custom objects
        HashMap<Integer, Student> students = new HashMap<>();
        students.put(1, new Student("Alice", 85));
        students.put(2, new Student("Bob", 92));
        students.put(3, new Student("Charlie", 78));

        System.out.println("Students:");
        students.forEach((id, student) ->
            System.out.println("ID " + id + ": " + student));
    }
}

class Student {
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return name + " (Score: " + score + ")";
    }
}
```

### TreeMap

TreeMap stores key-value pairs in sorted order based on keys.

```java
import java.util.TreeMap;

public class TreeMapExample {
    public static void main(String[] args) {
        TreeMap<String, Integer> treeMap = new TreeMap<>();

        // Adding elements
        treeMap.put("Zebra", 26);
        treeMap.put("Apple", 1);
        treeMap.put("Banana", 2);
        treeMap.put("Orange", 15);

        System.out.println("TreeMap: " + treeMap);  // Sorted by keys

        // First and last entries
        System.out.println("First entry: " + treeMap.firstEntry());
        System.out.println("Last entry: " + treeMap.lastEntry());

        // Submap operations
        System.out.println("HeadMap (before Orange): " + treeMap.headMap("Orange"));
        System.out.println("TailMap (from Orange): " + treeMap.tailMap("Orange"));
        System.out.println("SubMap (Apple to Orange): " + treeMap.subMap("Apple", "Orange"));

        // Higher and lower keys
        System.out.println("Higher key than Banana: " + treeMap.higherKey("Banana"));
        System.out.println("Lower key than Banana: " + treeMap.lowerKey("Banana"));

        // Ceiling and floor entries
        System.out.println("Ceiling entry for Cat: " + treeMap.ceilingEntry("Cat"));
        System.out.println("Floor entry for Cat: " + treeMap.floorEntry("Cat"));

        // Polling
        System.out.println("Poll first: " + treeMap.pollFirstEntry());
        System.out.println("Poll last: " + treeMap.pollLastEntry());
        System.out.println("After polling: " + treeMap);
    }
}
```

## 5. Queue Interface

Queues are designed for holding elements prior to processing.

### PriorityQueue

PriorityQueue orders elements according to their natural ordering or a custom comparator.

```java
import java.util.PriorityQueue;
import java.util.Comparator;

public class PriorityQueueExample {
    public static void main(String[] args) {
        // Natural ordering (min-heap)
        PriorityQueue<Integer> minQueue = new PriorityQueue<>();
        minQueue.offer(30);
        minQueue.offer(10);
        minQueue.offer(50);
        minQueue.offer(20);
        minQueue.offer(40);

        System.out.println("Min PriorityQueue: " + minQueue);
        System.out.println("Peek (smallest): " + minQueue.peek());

        System.out.println("Removing elements:");
        while (!minQueue.isEmpty()) {
            System.out.println("Removed: " + minQueue.poll());
        }

        // Custom ordering (max-heap)
        PriorityQueue<Integer> maxQueue = new PriorityQueue<>(Comparator.reverseOrder());
        maxQueue.offer(30);
        maxQueue.offer(10);
        maxQueue.offer(50);
        maxQueue.offer(20);
        maxQueue.offer(40);

        System.out.println("Max PriorityQueue: " + maxQueue);
        System.out.println("Peek (largest): " + maxQueue.peek());

        // PriorityQueue with custom objects
        PriorityQueue<Task> taskQueue = new PriorityQueue<>();
        taskQueue.offer(new Task("Low priority task", 3));
        taskQueue.offer(new Task("High priority task", 1));
        taskQueue.offer(new Task("Medium priority task", 2));

        System.out.println("Tasks in priority order:");
        while (!taskQueue.isEmpty()) {
            System.out.println(taskQueue.poll());
        }
    }
}

class Task implements Comparable<Task> {
    private String description;
    private int priority;  // Lower number = higher priority

    public Task(String description, int priority) {
        this.description = description;
        this.priority = priority;
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public String toString() {
        return description + " (Priority: " + priority + ")";
    }
}
```

## 6. Collections Utility Class

The Collections class provides static methods for manipulating collections.

```java
import java.util.*;

public class CollectionsUtilityExample {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Zebra");
        list.add("Apple");
        list.add("Banana");
        list.add("Orange");

        System.out.println("Original list: " + list);

        // Sorting
        Collections.sort(list);
        System.out.println("Sorted list: " + list);

        // Reverse sorting
        Collections.sort(list, Collections.reverseOrder());
        System.out.println("Reverse sorted: " + list);

        // Shuffling
        Collections.shuffle(list);
        System.out.println("Shuffled list: " + list);

        // Binary search (list must be sorted)
        Collections.sort(list);
        int index = Collections.binarySearch(list, "Banana");
        System.out.println("Index of Banana: " + index);

        // Finding min and max
        System.out.println("Min: " + Collections.min(list));
        System.out.println("Max: " + Collections.max(list));

        // Frequency
        list.add("Apple");
        System.out.println("Frequency of Apple: " + Collections.frequency(list, "Apple"));

        // Replacing elements
        Collections.replaceAll(list, "Apple", "Pineapple");
        System.out.println("After replacement: " + list);

        // Filling
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Collections.fill(numbers, 0);
        System.out.println("Filled with zeros: " + numbers);

        // Copying
        List<String> source = Arrays.asList("A", "B", "C");
        List<String> destination = new ArrayList<>(Arrays.asList("X", "Y", "Z"));
        Collections.copy(destination, source);
        System.out.println("Copied list: " + destination);

        // Rotating
        List<Integer> rotateList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Collections.rotate(rotateList, 2);
        System.out.println("Rotated by 2: " + rotateList);

        // Creating synchronized collections
        List<String> syncList = Collections.synchronizedList(new ArrayList<>());
        Set<String> syncSet = Collections.synchronizedSet(new HashSet<>());
        Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
    }
}
```

## 7. Streams and Lambda Expressions

Java 8 introduced streams for functional-style operations on collections.

```java
import java.util.*;
import java.util.stream.Collectors;

public class StreamsExample {
    public static void main(String[] args) {
        List<String> fruits = Arrays.asList("Apple", "Banana", "Orange", "Grape", "Mango");

        // Filtering
        List<String> filtered = fruits.stream()
            .filter(fruit -> fruit.startsWith("A"))
            .collect(Collectors.toList());
        System.out.println("Fruits starting with A: " + filtered);

        // Mapping
        List<Integer> lengths = fruits.stream()
            .map(String::length)
            .collect(Collectors.toList());
        System.out.println("Lengths: " + lengths);

        // Sorting
        List<String> sorted = fruits.stream()
            .sorted()
            .collect(Collectors.toList());
        System.out.println("Sorted: " + sorted);

        // Limiting
        List<String> limited = fruits.stream()
            .limit(3)
            .collect(Collectors.toList());
        System.out.println("First 3: " + limited);

        // Skipping
        List<String> skipped = fruits.stream()
            .skip(2)
            .collect(Collectors.toList());
        System.out.println("After skipping 2: " + skipped);

        // Finding
        Optional<String> first = fruits.stream()
            .filter(fruit -> fruit.length() > 5)
            .findFirst();
        System.out.println("First fruit with length > 5: " + first.orElse("None"));

        // Any match
        boolean anyMatch = fruits.stream()
            .anyMatch(fruit -> fruit.contains("a"));
        System.out.println("Any fruit contains 'a': " + anyMatch);

        // All match
        boolean allMatch = fruits.stream()
            .allMatch(fruit -> fruit.length() > 3);
        System.out.println("All fruits have length > 3: " + allMatch);

        // Reducing
        Optional<String> longest = fruits.stream()
            .reduce((a, b) -> a.length() > b.length() ? a : b);
        System.out.println("Longest fruit: " + longest.orElse("None"));

        // Collecting to different collections
        Set<String> fruitSet = fruits.stream()
            .collect(Collectors.toSet());
        System.out.println("As Set: " + fruitSet);

        Map<String, Integer> fruitMap = fruits.stream()
            .collect(Collectors.toMap(
                fruit -> fruit,
                String::length
            ));
        System.out.println("As Map: " + fruitMap);

        // Grouping
        Map<Integer, List<String>> groupedByLength = fruits.stream()
            .collect(Collectors.groupingBy(String::length));
        System.out.println("Grouped by length: " + groupedByLength);

        // Partitioning
        Map<Boolean, List<String>> partitioned = fruits.stream()
            .collect(Collectors.partitioningBy(fruit -> fruit.length() > 5));
        System.out.println("Partitioned by length > 5: " + partitioned);
    }
}
```

## 8. Best Practices

### Collection Best Practices

```java
import java.util.*;

public class CollectionBestPractices {

    // 1. Choose the right collection
    public static void chooseRightCollection() {
        // Use ArrayList for random access
        List<String> randomAccess = new ArrayList<>();

        // Use LinkedList for frequent insertions/deletions
        List<String> frequentModifications = new LinkedList<>();

        // Use HashSet for unique elements, no order
        Set<String> uniqueElements = new HashSet<>();

        // Use TreeSet for sorted unique elements
        Set<String> sortedUnique = new TreeSet<>();

        // Use HashMap for key-value pairs
        Map<String, Integer> keyValuePairs = new HashMap<>();

        // Use TreeMap for sorted key-value pairs
        Map<String, Integer> sortedKeyValue = new TreeMap<>();
    }

    // 2. Initialize collections properly
    public static void properInitialization() {
        // Good: Specify initial capacity for large collections
        ArrayList<String> largeList = new ArrayList<>(1000);

        // Good: Use Arrays.asList for small fixed lists
        List<String> smallList = Arrays.asList("A", "B", "C");

        // Good: Use Collections.singletonList for single element
        List<String> singleElement = Collections.singletonList("Single");

        // Good: Use Collections.emptyList() for empty lists
        List<String> emptyList = Collections.emptyList();
    }

    // 3. Iterate safely
    public static void safeIteration() {
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));

        // Good: Use for-each loop
        for (String item : list) {
            System.out.println(item);
        }

        // Good: Use iterator for removal
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (item.equals("B")) {
                iterator.remove();  // Safe removal
            }
        }

        // Good: Use removeIf (Java 8+)
        list.removeIf(item -> item.equals("A"));
    }

    // 4. Handle null values
    public static void handleNullValues() {
        // Good: Check for null before adding
        List<String> list = new ArrayList<>();
        String value = getValueFromSomewhere();
        if (value != null) {
            list.add(value);
        }

        // Good: Use Optional for nullable values
        Optional<String> optional = Optional.ofNullable(getValueFromSomewhere());
        optional.ifPresent(list::add);
    }

    // 5. Use generics properly
    public static void useGenerics() {
        // Good: Use raw types only when necessary
        List<String> stringList = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();

        // Good: Use bounded wildcards
        List<? extends Number> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<? super Integer> integers = new ArrayList<>();
    }

    // 6. Performance considerations
    public static void performanceConsiderations() {
        // Good: Pre-size collections when size is known
        int knownSize = 1000;
        ArrayList<String> preSized = new ArrayList<>(knownSize);

        // Good: Use appropriate data structures
        // For frequent lookups: HashSet, HashMap
        // For sorted data: TreeSet, TreeMap
        // For order preservation: LinkedHashSet, LinkedHashMap
    }

    private static String getValueFromSomewhere() {
        return Math.random() > 0.5 ? "Value" : null;
    }
}
```

## Summary

This section covered the comprehensive Java Collections Framework:

- **List Interface**: ArrayList, LinkedList for ordered collections
- **Set Interface**: HashSet, TreeSet for unique elements
- **Map Interface**: HashMap, TreeMap for key-value pairs
- **Queue Interface**: PriorityQueue for priority-based processing
- **Collections Utility**: Static methods for collection manipulation
- **Streams**: Functional programming with collections
- **Best Practices**: Proper usage and performance considerations

The Collections Framework provides powerful, flexible, and efficient data structures for storing and manipulating groups of objects in Java applications.
