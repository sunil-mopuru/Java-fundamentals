import java.util.*;
import java.util.stream.Collectors;

/**
 * Comprehensive Collections Framework Demo
 * This program demonstrates various collection types, their usage,
 * and best practices in Java.
 */

// Student class for demonstrating collections with custom objects
class Student implements Comparable<Student> {
    private int id;
    private String name;
    private double gpa;
    
    public Student(int id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public double getGpa() { return gpa; }
    
    @Override
    public int compareTo(Student other) {
        return Double.compare(this.gpa, other.gpa);  // Sort by GPA
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return id == student.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s', gpa=%.2f}", id, name, gpa);
    }
}

// Task class for PriorityQueue demonstration
class Task implements Comparable<Task> {
    private String description;
    private int priority;  // Lower number = higher priority
    private Date dueDate;
    
    public Task(String description, int priority, Date dueDate) {
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
    }
    
    public String getDescription() { return description; }
    public int getPriority() { return priority; }
    public Date getDueDate() { return dueDate; }
    
    @Override
    public int compareTo(Task other) {
        // First compare by priority, then by due date
        int priorityCompare = Integer.compare(this.priority, other.priority);
        if (priorityCompare != 0) {
            return priorityCompare;
        }
        return this.dueDate.compareTo(other.dueDate);
    }
    
    @Override
    public String toString() {
        return String.format("Task{description='%s', priority=%d, dueDate=%s}", 
                           description, priority, dueDate);
    }
}

// Main demonstration class
public class CollectionsDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Java Collections Framework Demo ===\n");
        
        // 1. List Interface Demonstrations
        demonstrateArrayList();
        demonstrateLinkedList();
        
        // 2. Set Interface Demonstrations
        demonstrateHashSet();
        demonstrateTreeSet();
        
        // 3. Map Interface Demonstrations
        demonstrateHashMap();
        demonstrateTreeMap();
        
        // 4. Queue Interface Demonstrations
        demonstratePriorityQueue();
        
        // 5. Collections Utility Demonstrations
        demonstrateCollectionsUtility();
        
        // 6. Streams Demonstrations
        demonstrateStreams();
        
        // 7. Best Practices Demonstrations
        demonstrateBestPractices();
        
        System.out.println("\n=== End of Collections Framework Demo ===");
    }
    
    /**
     * Demonstrates ArrayList usage
     */
    public static void demonstrateArrayList() {
        System.out.println("1. ARRAYLIST DEMONSTRATION:");
        System.out.println("===========================");
        
        ArrayList<String> fruits = new ArrayList<>();
        
        // Adding elements
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Orange");
        fruits.add("Grape");
        fruits.add("Mango");
        
        System.out.println("Original list: " + fruits);
        System.out.println("Size: " + fruits.size());
        
        // Accessing elements
        System.out.println("First fruit: " + fruits.get(0));
        System.out.println("Last fruit: " + fruits.get(fruits.size() - 1));
        
        // Checking elements
        System.out.println("Contains Apple: " + fruits.contains("Apple"));
        System.out.println("Index of Orange: " + fruits.indexOf("Orange"));
        
        // Modifying elements
        fruits.set(1, "Blueberry");
        System.out.println("After replacing Banana: " + fruits);
        
        // Adding at specific position
        fruits.add(2, "Strawberry");
        System.out.println("After inserting Strawberry: " + fruits);
        
        // Removing elements
        fruits.remove("Grape");
        fruits.remove(0);
        System.out.println("After removals: " + fruits);
        
        // Iterating
        System.out.println("Iterating with for-each:");
        for (String fruit : fruits) {
            System.out.println("  " + fruit);
        }
        
        // Using forEach with lambda
        System.out.println("Using forEach:");
        fruits.forEach(fruit -> System.out.println("  " + fruit.toUpperCase()));
        
        System.out.println();
    }
    
    /**
     * Demonstrates LinkedList usage
     */
    public static void demonstrateLinkedList() {
        System.out.println("2. LINKEDLIST DEMONSTRATION:");
        System.out.println("============================");
        
        LinkedList<String> linkedList = new LinkedList<>();
        
        // Adding elements
        linkedList.add("First");
        linkedList.add("Second");
        linkedList.add("Third");
        
        // Adding at ends
        linkedList.addFirst("Zero");
        linkedList.addLast("Fourth");
        
        System.out.println("LinkedList: " + linkedList);
        
        // Accessing elements
        System.out.println("First: " + linkedList.getFirst());
        System.out.println("Last: " + linkedList.getLast());
        System.out.println("Element at index 2: " + linkedList.get(2));
        
        // Using as Queue
        System.out.println("\nUsing as Queue:");
        LinkedList<Integer> queue = new LinkedList<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        
        System.out.println("Queue: " + queue);
        System.out.println("Peek: " + queue.peek());
        System.out.println("Poll: " + queue.poll());
        System.out.println("Queue after poll: " + queue);
        
        // Using as Stack
        System.out.println("\nUsing as Stack:");
        LinkedList<String> stack = new LinkedList<>();
        stack.push("Bottom");
        stack.push("Middle");
        stack.push("Top");
        
        System.out.println("Stack: " + stack);
        System.out.println("Peek: " + stack.peek());
        System.out.println("Pop: " + stack.pop());
        System.out.println("Stack after pop: " + stack);
        
        System.out.println();
    }
    
    /**
     * Demonstrates HashSet usage
     */
    public static void demonstrateHashSet() {
        System.out.println("3. HASHSET DEMONSTRATION:");
        System.out.println("=========================");
        
        HashSet<String> hashSet = new HashSet<>();
        
        // Adding elements
        hashSet.add("Apple");
        hashSet.add("Banana");
        hashSet.add("Orange");
        hashSet.add("Apple");  // Duplicate - ignored
        
        System.out.println("HashSet: " + hashSet);
        System.out.println("Size: " + hashSet.size());
        
        // Checking elements
        System.out.println("Contains Apple: " + hashSet.contains("Apple"));
        System.out.println("Contains Grape: " + hashSet.contains("Grape"));
        
        // Removing elements
        hashSet.remove("Banana");
        System.out.println("After removing Banana: " + hashSet);
        
        // Set operations
        HashSet<String> set1 = new HashSet<>(Arrays.asList("A", "B", "C"));
        HashSet<String> set2 = new HashSet<>(Arrays.asList("B", "C", "D"));
        
        System.out.println("Set1: " + set1);
        System.out.println("Set2: " + set2);
        
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
        
        System.out.println();
    }
    
    /**
     * Demonstrates TreeSet usage
     */
    public static void demonstrateTreeSet() {
        System.out.println("4. TREESET DEMONSTRATION:");
        System.out.println("=========================");
        
        TreeSet<Integer> treeSet = new TreeSet<>();
        
        // Adding elements
        treeSet.add(30);
        treeSet.add(10);
        treeSet.add(50);
        treeSet.add(20);
        treeSet.add(40);
        
        System.out.println("TreeSet: " + treeSet);  // Naturally sorted
        
        // First and last
        System.out.println("First: " + treeSet.first());
        System.out.println("Last: " + treeSet.last());
        
        // Subset operations
        System.out.println("HeadSet (less than 30): " + treeSet.headSet(30));
        System.out.println("TailSet (greater than or equal to 30): " + treeSet.tailSet(30));
        System.out.println("SubSet (20 to 40): " + treeSet.subSet(20, 40));
        
        // Higher and lower
        System.out.println("Higher than 25: " + treeSet.higher(25));
        System.out.println("Lower than 25: " + treeSet.lower(25));
        
        // With custom objects
        TreeSet<Student> students = new TreeSet<>();
        students.add(new Student(1, "Alice", 3.8));
        students.add(new Student(2, "Bob", 3.5));
        students.add(new Student(3, "Charlie", 4.0));
        students.add(new Student(4, "Diana", 3.9));
        
        System.out.println("\nStudents sorted by GPA:");
        students.forEach(System.out::println);
        
        System.out.println();
    }
    
    /**
     * Demonstrates HashMap usage
     */
    public static void demonstrateHashMap() {
        System.out.println("5. HASHMAP DEMONSTRATION:");
        System.out.println("=========================");
        
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
        
        // Iterating
        System.out.println("\nKey-Value pairs:");
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }
        
        // Using forEach
        System.out.println("\nUsing forEach:");
        hashMap.forEach((key, value) -> 
            System.out.println("  " + key + " = " + value));
        
        // With custom objects
        HashMap<Integer, Student> students = new HashMap<>();
        students.put(1, new Student(1, "Alice", 3.8));
        students.put(2, new Student(2, "Bob", 3.5));
        students.put(3, new Student(3, "Charlie", 4.0));
        
        System.out.println("\nStudents map:");
        students.forEach((id, student) -> 
            System.out.println("  ID " + id + ": " + student));
        
        System.out.println();
    }
    
    /**
     * Demonstrates TreeMap usage
     */
    public static void demonstrateTreeMap() {
        System.out.println("6. TREEMAP DEMONSTRATION:");
        System.out.println("========================");
        
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
        
        // Higher and lower keys
        System.out.println("Higher key than Banana: " + treeMap.higherKey("Banana"));
        System.out.println("Lower key than Banana: " + treeMap.lowerKey("Banana"));
        
        System.out.println();
    }
    
    /**
     * Demonstrates PriorityQueue usage
     */
    public static void demonstratePriorityQueue() {
        System.out.println("7. PRIORITYQUEUE DEMONSTRATION:");
        System.out.println("==============================");
        
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
            System.out.println("  Removed: " + minQueue.poll());
        }
        
        // Custom ordering (max-heap)
        PriorityQueue<Integer> maxQueue = new PriorityQueue<>(Comparator.reverseOrder());
        maxQueue.offer(30);
        maxQueue.offer(10);
        maxQueue.offer(50);
        maxQueue.offer(20);
        maxQueue.offer(40);
        
        System.out.println("\nMax PriorityQueue: " + maxQueue);
        System.out.println("Peek (largest): " + maxQueue.peek());
        
        // With custom objects
        PriorityQueue<Task> taskQueue = new PriorityQueue<>();
        taskQueue.offer(new Task("Low priority task", 3, new Date()));
        taskQueue.offer(new Task("High priority task", 1, new Date()));
        taskQueue.offer(new Task("Medium priority task", 2, new Date()));
        
        System.out.println("\nTasks in priority order:");
        while (!taskQueue.isEmpty()) {
            System.out.println("  " + taskQueue.poll());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates Collections utility class
     */
    public static void demonstrateCollectionsUtility() {
        System.out.println("8. COLLECTIONS UTILITY DEMONSTRATION:");
        System.out.println("====================================");
        
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
        
        System.out.println();
    }
    
    /**
     * Demonstrates Streams usage
     */
    public static void demonstrateStreams() {
        System.out.println("9. STREAMS DEMONSTRATION:");
        System.out.println("========================");
        
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
        
        // Finding
        Optional<String> first = fruits.stream()
            .filter(fruit -> fruit.length() > 5)
            .findFirst();
        System.out.println("First fruit with length > 5: " + first.orElse("None"));
        
        // Any match
        boolean anyMatch = fruits.stream()
            .anyMatch(fruit -> fruit.contains("a"));
        System.out.println("Any fruit contains 'a': " + anyMatch);
        
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
        
        System.out.println();
    }
    
    /**
     * Demonstrates best practices
     */
    public static void demonstrateBestPractices() {
        System.out.println("10. BEST PRACTICES DEMONSTRATION:");
        System.out.println("================================");
        
        // 1. Choose the right collection
        System.out.println("1. Choosing the right collection:");
        
        // Use ArrayList for random access
        List<String> randomAccess = new ArrayList<>();
        randomAccess.add("Item1");
        randomAccess.add("Item2");
        System.out.println("  ArrayList for random access: " + randomAccess.get(1));
        
        // Use LinkedList for frequent modifications
        List<String> frequentModifications = new LinkedList<>();
        frequentModifications.add("First");
        frequentModifications.add("Second");
        frequentModifications.add(1, "Inserted");
        System.out.println("  LinkedList after insertion: " + frequentModifications);
        
        // Use HashSet for unique elements
        Set<String> uniqueElements = new HashSet<>();
        uniqueElements.add("A");
        uniqueElements.add("B");
        uniqueElements.add("A");  // Duplicate ignored
        System.out.println("  HashSet with duplicates: " + uniqueElements);
        
        // Use TreeSet for sorted elements
        Set<String> sortedElements = new TreeSet<>();
        sortedElements.add("Zebra");
        sortedElements.add("Apple");
        sortedElements.add("Banana");
        System.out.println("  TreeSet sorted: " + sortedElements);
        
        // 2. Initialize collections properly
        System.out.println("\n2. Proper initialization:");
        
        // Specify initial capacity for large collections
        ArrayList<String> largeList = new ArrayList<>(1000);
        System.out.println("  Pre-sized ArrayList capacity: " + largeList.size());
        
        // Use Arrays.asList for small fixed lists
        List<String> smallList = Arrays.asList("A", "B", "C");
        System.out.println("  Fixed list: " + smallList);
        
        // Use Collections.singletonList for single element
        List<String> singleElement = Collections.singletonList("Single");
        System.out.println("  Single element list: " + singleElement);
        
        // 3. Safe iteration
        System.out.println("\n3. Safe iteration:");
        
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));
        
        // Use for-each loop
        System.out.println("  For-each iteration:");
        for (String item : list) {
            System.out.println("    " + item);
        }
        
        // Use iterator for removal
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (item.equals("B")) {
                iterator.remove();  // Safe removal
            }
        }
        System.out.println("  After safe removal: " + list);
        
        // Use removeIf (Java 8+)
        list.removeIf(item -> item.equals("A"));
        System.out.println("  After removeIf: " + list);
        
        System.out.println();
    }
} 