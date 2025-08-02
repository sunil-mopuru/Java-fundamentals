# Hash-Based Data Structures in Java

## Overview

Hash-based data structures use hash functions to map data to specific locations in memory, providing efficient insertion, deletion, and search operations. These structures are fundamental to many applications and are widely used in Java collections.

## 1. Hash Functions

### What are Hash Functions?

A hash function is a function that takes an input (or 'key') and returns a fixed-size string of bytes, typically a hash code. The output is used to index a hash table.

### Key Characteristics

- **Deterministic**: Same input always produces same output
- **Uniform Distribution**: Should distribute keys evenly
- **Fast Computation**: Should be computationally efficient
- **Minimal Collisions**: Should minimize hash collisions

### Common Hash Functions

#### 1.1 Division Method

```java
public static int hashDivision(int key, int tableSize) {
    return key % tableSize;
}
```

#### 1.2 Multiplication Method

```java
public static int hashMultiplication(int key, int tableSize) {
    double A = 0.6180339887; // Golden ratio
    double product = key * A;
    double fractionalPart = product - Math.floor(product);
    return (int) (tableSize * fractionalPart);
}
```

#### 1.3 Java's hashCode() Method

```java
// String hash function
public static int hashString(String str) {
    int hash = 0;
    for (char c : str.toCharArray()) {
        hash = 31 * hash + c;
    }
    return hash;
}

// Custom object hash function
public static int hashObject(Object obj) {
    return obj.hashCode();
}
```

## 2. Hash Tables

### What are Hash Tables?

A hash table is a data structure that implements an associative array abstract data type, a structure that can map keys to values. It uses a hash function to compute an index into an array of buckets or slots.

### Key Characteristics

- **Key-Value Storage**: Stores data in key-value pairs
- **Fast Access**: O(1) average case for search, insert, delete
- **Collision Handling**: Must handle hash collisions
- **Dynamic Resizing**: Can grow and shrink as needed

### Hash Table Implementation

#### 2.1 Basic Hash Table

```java
class HashTable<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.table = new Entry[capacity];
    }

    // Hash function
    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    // Put key-value pair
    public void put(K key, V value) {
        if (key == null) return;

        int index = hash(key);
        Entry<K, V> entry = table[index];

        // Check if key already exists
        while (entry != null) {
            if (entry.key.equals(key)) {
                entry.value = value; // Update value
                return;
            }
            entry = entry.next;
        }

        // Add new entry
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = table[index];
        table[index] = newEntry;
        size++;

        // Check if resizing is needed
        if ((double) size / capacity >= LOAD_FACTOR) {
            resize();
        }
    }

    // Get value by key
    public V get(K key) {
        if (key == null) return null;

        int index = hash(key);
        Entry<K, V> entry = table[index];

        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }

        return null; // Key not found
    }

    // Remove key-value pair
    public V remove(K key) {
        if (key == null) return null;

        int index = hash(key);
        Entry<K, V> entry = table[index];
        Entry<K, V> prev = null;

        while (entry != null) {
            if (entry.key.equals(key)) {
                if (prev == null) {
                    table[index] = entry.next;
                } else {
                    prev.next = entry.next;
                }
                size--;
                return entry.value;
            }
            prev = entry;
            entry = entry.next;
        }

        return null; // Key not found
    }

    // Check if key exists
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    // Get size
    public int size() {
        return size;
    }

    // Check if empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Clear all entries
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }
        size = 0;
    }

    // Resize hash table
    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCapacity = capacity;
        Entry<K, V>[] oldTable = table;

        capacity *= 2;
        table = new Entry[capacity];
        size = 0;

        // Rehash all entries
        for (int i = 0; i < oldCapacity; i++) {
            Entry<K, V> entry = oldTable[i];
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    // Print hash table
    public void printTable() {
        for (int i = 0; i < capacity; i++) {
            System.out.print("Bucket " + i + ": ");
            Entry<K, V> entry = table[i];
            while (entry != null) {
                System.out.print("(" + entry.key + ", " + entry.value + ") ");
                entry = entry.next;
            }
            System.out.println();
        }
    }
}
```

#### 2.2 Open Addressing Hash Table

```java
class OpenAddressingHashTable<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;
        boolean isDeleted;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.isDeleted = false;
        }
    }

    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR = 0.7;

    @SuppressWarnings("unchecked")
    public OpenAddressingHashTable(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.table = new Entry[capacity];
    }

    // Hash function
    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    // Linear probing
    private int probe(int index, int i) {
        return (index + i) % capacity;
    }

    // Put key-value pair
    public void put(K key, V value) {
        if (key == null) return;

        if ((double) size / capacity >= LOAD_FACTOR) {
            resize();
        }

        int index = hash(key);

        for (int i = 0; i < capacity; i++) {
            int probeIndex = probe(index, i);
            Entry<K, V> entry = table[probeIndex];

            if (entry == null || entry.isDeleted) {
                table[probeIndex] = new Entry<>(key, value);
                size++;
                return;
            } else if (entry.key.equals(key)) {
                entry.value = value; // Update value
                return;
            }
        }

        // Table is full
        resize();
        put(key, value);
    }

    // Get value by key
    public V get(K key) {
        if (key == null) return null;

        int index = hash(key);

        for (int i = 0; i < capacity; i++) {
            int probeIndex = probe(index, i);
            Entry<K, V> entry = table[probeIndex];

            if (entry == null) {
                return null; // Key not found
            } else if (!entry.isDeleted && entry.key.equals(key)) {
                return entry.value;
            }
        }

        return null; // Key not found
    }

    // Remove key-value pair
    public V remove(K key) {
        if (key == null) return null;

        int index = hash(key);

        for (int i = 0; i < capacity; i++) {
            int probeIndex = probe(index, i);
            Entry<K, V> entry = table[probeIndex];

            if (entry == null) {
                return null; // Key not found
            } else if (!entry.isDeleted && entry.key.equals(key)) {
                entry.isDeleted = true;
                size--;
                return entry.value;
            }
        }

        return null; // Key not found
    }

    // Check if key exists
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    // Get size
    public int size() {
        return size;
    }

    // Check if empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Clear all entries
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }
        size = 0;
    }

    // Resize hash table
    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCapacity = capacity;
        Entry<K, V>[] oldTable = table;

        capacity *= 2;
        table = new Entry[capacity];
        size = 0;

        // Rehash all entries
        for (int i = 0; i < oldCapacity; i++) {
            Entry<K, V> entry = oldTable[i];
            if (entry != null && !entry.isDeleted) {
                put(entry.key, entry.value);
            }
        }
    }

    // Print hash table
    public void printTable() {
        for (int i = 0; i < capacity; i++) {
            Entry<K, V> entry = table[i];
            if (entry != null && !entry.isDeleted) {
                System.out.println("Index " + i + ": (" + entry.key + ", " + entry.value + ")");
            } else {
                System.out.println("Index " + i + ": null");
            }
        }
    }
}
```

## 3. Hash Sets

### What are Hash Sets?

A HashSet is a collection that contains no duplicate elements. It uses a hash table for storage and provides constant-time performance for basic operations.

### Key Characteristics

- **No Duplicates**: Cannot contain duplicate elements
- **Unordered**: Elements are not stored in any particular order
- **Fast Operations**: O(1) average case for add, remove, contains
- **Null Support**: Can contain null elements (depending on implementation)

### HashSet Implementation

```java
class HashSet<E> {
    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<E>[] table;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    public HashSet(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.table = new Node[capacity];
    }

    // Hash function
    private int hash(E element) {
        return Math.abs(element.hashCode()) % capacity;
    }

    // Add element
    public boolean add(E element) {
        if (element == null) return false;

        int index = hash(element);
        Node<E> current = table[index];

        // Check if element already exists
        while (current != null) {
            if (current.data.equals(element)) {
                return false; // Element already exists
            }
            current = current.next;
        }

        // Add new element
        Node<E> newNode = new Node<>(element);
        newNode.next = table[index];
        table[index] = newNode;
        size++;

        // Check if resizing is needed
        if ((double) size / capacity >= LOAD_FACTOR) {
            resize();
        }

        return true;
    }

    // Remove element
    public boolean remove(E element) {
        if (element == null) return false;

        int index = hash(element);
        Node<E> current = table[index];
        Node<E> prev = null;

        while (current != null) {
            if (current.data.equals(element)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }

        return false; // Element not found
    }

    // Check if element exists
    public boolean contains(E element) {
        if (element == null) return false;

        int index = hash(element);
        Node<E> current = table[index];

        while (current != null) {
            if (current.data.equals(element)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    // Get size
    public int size() {
        return size;
    }

    // Check if empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Clear all elements
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }
        size = 0;
    }

    // Resize hash set
    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCapacity = capacity;
        Node<E>[] oldTable = table;

        capacity *= 2;
        table = new Node[capacity];
        size = 0;

        // Rehash all elements
        for (int i = 0; i < oldCapacity; i++) {
            Node<E> current = oldTable[i];
            while (current != null) {
                add(current.data);
                current = current.next;
            }
        }
    }

    // Get all elements
    public List<E> toList() {
        List<E> result = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            Node<E> current = table[i];
            while (current != null) {
                result.add(current.data);
                current = current.next;
            }
        }
        return result;
    }

    // Print hash set
    public void printSet() {
        System.out.print("HashSet: ");
        for (int i = 0; i < capacity; i++) {
            Node<E> current = table[i];
            while (current != null) {
                System.out.print(current.data + " ");
                current = current.next;
            }
        }
        System.out.println();
    }
}
```

## 4. Hash Maps

### What are Hash Maps?

A HashMap is a hash table-based implementation of the Map interface. It provides constant-time performance for basic operations and allows null keys and values.

### Key Characteristics

- **Key-Value Pairs**: Stores data as key-value pairs
- **No Duplicate Keys**: Each key can appear only once
- **Fast Operations**: O(1) average case for get, put, remove
- **Null Support**: Can contain null keys and values

### HashMap Implementation

```java
class HashMap<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    public HashMap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.table = new Entry[capacity];
    }

    // Hash function
    private int hash(K key) {
        return key == null ? 0 : Math.abs(key.hashCode()) % capacity;
    }

    // Put key-value pair
    public V put(K key, V value) {
        int index = hash(key);
        Entry<K, V> entry = table[index];

        // Check if key already exists
        while (entry != null) {
            if ((key == null && entry.key == null) ||
                (key != null && key.equals(entry.key))) {
                V oldValue = entry.value;
                entry.value = value; // Update value
                return oldValue;
            }
            entry = entry.next;
        }

        // Add new entry
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = table[index];
        table[index] = newEntry;
        size++;

        // Check if resizing is needed
        if ((double) size / capacity >= LOAD_FACTOR) {
            resize();
        }

        return null;
    }

    // Get value by key
    public V get(K key) {
        int index = hash(key);
        Entry<K, V> entry = table[index];

        while (entry != null) {
            if ((key == null && entry.key == null) ||
                (key != null && key.equals(entry.key))) {
                return entry.value;
            }
            entry = entry.next;
        }

        return null; // Key not found
    }

    // Remove key-value pair
    public V remove(K key) {
        int index = hash(key);
        Entry<K, V> entry = table[index];
        Entry<K, V> prev = null;

        while (entry != null) {
            if ((key == null && entry.key == null) ||
                (key != null && key.equals(entry.key))) {
                if (prev == null) {
                    table[index] = entry.next;
                } else {
                    prev.next = entry.next;
                }
                size--;
                return entry.value;
            }
            prev = entry;
            entry = entry.next;
        }

        return null; // Key not found
    }

    // Check if key exists
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    // Check if value exists
    public boolean containsValue(V value) {
        for (int i = 0; i < capacity; i++) {
            Entry<K, V> entry = table[i];
            while (entry != null) {
                if ((value == null && entry.value == null) ||
                    (value != null && value.equals(entry.value))) {
                    return true;
                }
                entry = entry.next;
            }
        }
        return false;
    }

    // Get all keys
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (int i = 0; i < capacity; i++) {
            Entry<K, V> entry = table[i];
            while (entry != null) {
                keys.add(entry.key);
                entry = entry.next;
            }
        }
        return keys;
    }

    // Get all values
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            Entry<K, V> entry = table[i];
            while (entry != null) {
                values.add(entry.value);
                entry = entry.next;
            }
        }
        return values;
    }

    // Get all entries
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entries = new HashSet<>();
        for (int i = 0; i < capacity; i++) {
            Entry<K, V> entry = table[i];
            while (entry != null) {
                entries.add(new AbstractMap.SimpleEntry<>(entry.key, entry.value));
                entry = entry.next;
            }
        }
        return entries;
    }

    // Get size
    public int size() {
        return size;
    }

    // Check if empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Clear all entries
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }
        size = 0;
    }

    // Resize hash map
    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCapacity = capacity;
        Entry<K, V>[] oldTable = table;

        capacity *= 2;
        table = new Entry[capacity];
        size = 0;

        // Rehash all entries
        for (int i = 0; i < oldCapacity; i++) {
            Entry<K, V> entry = oldTable[i];
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    // Print hash map
    public void printMap() {
        for (int i = 0; i < capacity; i++) {
            System.out.print("Bucket " + i + ": ");
            Entry<K, V> entry = table[i];
            while (entry != null) {
                System.out.print("(" + entry.key + ", " + entry.value + ") ");
                entry = entry.next;
            }
            System.out.println();
        }
    }
}
```

## 5. Collision Resolution Strategies

### 1. Separate Chaining

- **Description**: Each bucket contains a linked list of entries
- **Advantages**: Simple implementation, handles collisions well
- **Disadvantages**: Extra memory for pointers, cache unfriendly
- **Use Case**: When memory is not a constraint

### 2. Open Addressing

- **Description**: All entries are stored in the hash table itself
- **Types**:
  - **Linear Probing**: Check next slot sequentially
  - **Quadratic Probing**: Check slots with quadratic increments
  - **Double Hashing**: Use second hash function for probing

### 3. Robin Hood Hashing

- **Description**: Give priority to entries that have traveled farther
- **Advantages**: Reduces variance in probe lengths
- **Disadvantages**: More complex implementation

## 6. Performance Analysis

### Time Complexity

- **Average Case**: O(1) for insert, delete, search
- **Worst Case**: O(n) when all elements hash to same bucket
- **Best Case**: O(1) when no collisions occur

### Space Complexity

- **Separate Chaining**: O(n + m) where n is elements, m is buckets
- **Open Addressing**: O(m) where m is table size

### Load Factor Impact

- **High Load Factor**: More collisions, slower performance
- **Low Load Factor**: Fewer collisions, more memory usage
- **Optimal Range**: 0.6 - 0.8 for most applications

## 7. Applications

### 1. Database Indexing

```java
// Simple database index using HashMap
class DatabaseIndex {
    private HashMap<String, List<Integer>> index;

    public DatabaseIndex() {
        this.index = new HashMap<>();
    }

    public void addRecord(String key, int recordId) {
        if (!index.containsKey(key)) {
            index.put(key, new ArrayList<>());
        }
        index.get(key).add(recordId);
    }

    public List<Integer> search(String key) {
        return index.getOrDefault(key, new ArrayList<>());
    }
}
```

### 2. Caching

```java
// LRU Cache using HashMap and LinkedList
class LRUCache<K, V> {
    private HashMap<K, Node<K, V>> cache;
    private Node<K, V> head, tail;
    private int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head = new Node<>();
        this.tail = new Node<>();
        head.next = tail;
        tail.prev = head;
    }

    public V get(K key) {
        Node<K, V> node = cache.get(key);
        if (node == null) return null;

        moveToHead(node);
        return node.value;
    }

    public void put(K key, V value) {
        Node<K, V> node = cache.get(key);
        if (node == null) {
            node = new Node<>(key, value);
            cache.put(key, node);
            addNode(node);

            if (cache.size() > capacity) {
                Node<K, V> removed = removeTail();
                cache.remove(removed.key);
            }
        } else {
            node.value = value;
            moveToHead(node);
        }
    }

    private void addNode(Node<K, V> node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(Node<K, V> node) {
        removeNode(node);
        addNode(node);
    }

    private Node<K, V> removeTail() {
        Node<K, V> removed = tail.prev;
        removeNode(removed);
        return removed;
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev, next;

        Node() {}
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
```

### 3. Word Frequency Counter

```java
class WordFrequencyCounter {
    private HashMap<String, Integer> frequency;

    public WordFrequencyCounter() {
        this.frequency = new HashMap<>();
    }

    public void countWords(String text) {
        String[] words = text.toLowerCase().split("\\s+");
        for (String word : words) {
            word = word.replaceAll("[^a-zA-Z]", "");
            if (!word.isEmpty()) {
                frequency.put(word, frequency.getOrDefault(word, 0) + 1);
            }
        }
    }

    public int getFrequency(String word) {
        return frequency.getOrDefault(word.toLowerCase(), 0);
    }

    public List<String> getMostFrequent(int n) {
        return frequency.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(n)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
}
```

## 8. Best Practices

1. **Choose Good Hash Functions**: Use well-distributed hash functions
2. **Handle Collisions**: Implement appropriate collision resolution
3. **Monitor Load Factor**: Resize when load factor gets too high
4. **Override hashCode() and equals()**: For custom objects
5. **Consider Thread Safety**: Use ConcurrentHashMap for multi-threaded applications
6. **Memory Management**: Be aware of memory usage patterns

## 9. Practice Problems

1. **Hash Table Problems**:

   - Implement a phone directory
   - Find first non-repeating character
   - Group anagrams
   - Two sum problem

2. **Hash Set Problems**:

   - Remove duplicates from array
   - Check if array contains duplicates
   - Find intersection of two arrays
   - Happy number problem

3. **Hash Map Problems**:

   - Word pattern matching
   - Longest substring without repeating characters
   - Top k frequent elements
   - Subarray sum equals k

4. **Advanced Problems**:
   - Design a data structure with O(1) operations
   - Implement a distributed hash table
   - Consistent hashing
   - Bloom filter implementation

Hash-based data structures are fundamental to efficient programming and are used extensively in real-world applications.
