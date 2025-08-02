# HashMap Internal Working in Java

HashMap is one of the most commonly used data structures in Java. Understanding its internal working is crucial for writing efficient code and acing technical interviews.

## What is HashMap?

HashMap is a hash table-based implementation of the Map interface that provides constant-time performance for basic operations (get and put) under ideal conditions.

### Key Features

- **Key-Value Pairs**: Stores data as key-value pairs
- **No Order**: Does not maintain insertion order
- **Null Support**: Allows one null key and multiple null values
- **Not Thread-Safe**: Not synchronized by default
- **Fast Access**: O(1) average time complexity for get/put operations

## Internal Structure

HashMap internally uses an array of `Node<K,V>` objects called **table** (or buckets). Each Node represents a key-value pair.

### Basic Structure

```java
public class HashMap<K,V> extends AbstractMap<K,V> implements Map<K,V> {
    // The table, initialized on first use, and resized as necessary
    transient Node<K,V>[] table;

    // The number of key-value mappings contained in this map
    transient int size;

    // The next size value at which to resize (capacity * load factor)
    int threshold;

    // The load factor for the hash table
    final float loadFactor;

    // The number of times this HashMap has been structurally modified
    transient int modCount;
}
```

### Node Structure

```java
static class Node<K,V> implements Map.Entry<K,V> {
    final int hash;        // Hash code of the key
    final K key;           // Key
    V value;               // Value
    Node<K,V> next;        // Reference to next node (for collision resolution)

    Node(int hash, K key, V value, Node<K,V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }
}
```

## Hash Function and Bucket Index

### Hash Function Implementation

```java
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```

### Index Calculation

```java
// Calculate bucket index
int index = (n - 1) & hash;
// where n is the capacity (always a power of 2)
```

### Example: Hash Code Calculation

```java
public class HashCodeExample {
    public static void main(String[] args) {
        String key = "Hello";

        // Step 1: Get hashCode
        int hashCode = key.hashCode();
        System.out.println("Original hashCode: " + hashCode);

        // Step 2: Apply hash function
        int h = hashCode;
        int hash = h ^ (h >>> 16);
        System.out.println("After hash function: " + hash);

        // Step 3: Calculate index (assuming capacity = 16)
        int capacity = 16;
        int index = (capacity - 1) & hash;
        System.out.println("Bucket index: " + index);
    }
}
```

## Put Operation

The `put()` operation involves several steps:

### Step-by-Step Process

1. **Calculate Hash**: Get hash code of the key
2. **Find Bucket**: Calculate bucket index
3. **Check Existing**: Look for existing key in the bucket
4. **Update or Add**: Update value if key exists, otherwise add new node
5. **Resize if Needed**: Check if resize is required

### Put Operation Example

```java
public class PutOperationExample {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();

        // Step 1: Put first element
        map.put("Alice", 25);
        System.out.println("After putting Alice: " + map);

        // Step 2: Put second element
        map.put("Bob", 30);
        System.out.println("After putting Bob: " + map);

        // Step 3: Update existing key
        map.put("Alice", 26);
        System.out.println("After updating Alice: " + map);

        // Step 4: Put null key
        map.put(null, 40);
        System.out.println("After putting null key: " + map);
    }
}
```

## Get Operation

The `get()` operation retrieves a value by key:

### Get Operation Example

```java
public class GetOperationExample {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Alice", 25);
        map.put("Bob", 30);
        map.put("Charlie", 35);
        map.put(null, 40);

        // Get existing key
        Integer aliceAge = map.get("Alice");
        System.out.println("Alice's age: " + aliceAge);

        // Get non-existing key
        Integer davidAge = map.get("David");
        System.out.println("David's age: " + davidAge);

        // Get null key
        Integer nullValue = map.get(null);
        System.out.println("Null key value: " + nullValue);
    }
}
```

## Hash Collision Resolution

### What is Hash Collision?

A hash collision occurs when two different keys produce the same hash code and map to the same bucket.

### Collision Resolution Strategies

HashMap uses **Separate Chaining** with linked lists to resolve collisions:

1. **Linked List**: Multiple nodes in the same bucket form a linked list
2. **Tree Conversion**: When list length exceeds threshold, convert to Red-Black Tree
3. **Tree to List**: When tree size becomes small, convert back to linked list

### Collision Example

```java
class CollisionKey {
    private String value;

    public CollisionKey(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        // Force all keys to have the same hash code (for demonstration)
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CollisionKey that = (CollisionKey) obj;
        return Objects.equals(value, that.value);
    }

    @Override
    public String toString() {
        return value;
    }
}

public class ControlledCollisionExample {
    public static void main(String[] args) {
        HashMap<CollisionKey, String> map = new HashMap<>();

        // All these keys will collide (same hash code)
        map.put(new CollisionKey("Key1"), "Value1");
        map.put(new CollisionKey("Key2"), "Value2");
        map.put(new CollisionKey("Key3"), "Value3");
        map.put(new CollisionKey("Key4"), "Value4");

        System.out.println("Map size: " + map.size());

        // Retrieve values
        System.out.println("Value for Key1: " + map.get(new CollisionKey("Key1")));
        System.out.println("Value for Key3: " + map.get(new CollisionKey("Key3")));
    }
}
```

## Load Factor and Rehashing

### Load Factor

The load factor is a measure of how full the HashMap is allowed to get before its capacity is automatically increased.

- **Default Load Factor**: 0.75 (75%)
- **Threshold**: capacity × load factor
- **When to Resize**: When size > threshold

### Rehashing Process

1. **Create New Table**: Create a new table with doubled capacity
2. **Rehash All Elements**: Recalculate hash and index for all existing elements
3. **Redistribute**: Place elements in new buckets
4. **Update References**: Update table reference and threshold

### Rehashing Example

```java
public class RehashingExample {
    public static void main(String[] args) {
        // Create HashMap with initial capacity 4 and load factor 0.75
        HashMap<String, Integer> map = new HashMap<>(4, 0.75f);

        System.out.println("Initial capacity: 4");
        System.out.println("Load factor: 0.75");
        System.out.println("Threshold: 4 * 0.75 = 3");

        // Add elements one by one
        map.put("A", 1);
        System.out.println("After adding A: size=" + map.size());

        map.put("B", 2);
        System.out.println("After adding B: size=" + map.size());

        map.put("C", 3);
        System.out.println("After adding C: size=" + map.size());
        System.out.println("Threshold reached! Rehashing will occur on next put.");

        map.put("D", 4);
        System.out.println("After adding D: size=" + map.size());
        System.out.println("Rehashing completed! New capacity: 8");
    }
}
```

## Treeify Threshold

### When Does Treeification Occur?

- **TREEIFY_THRESHOLD**: 8 (minimum number of nodes before treeifying)
- **UNTREEIFY_THRESHOLD**: 6 (maximum number of nodes before untreeifying)
- **MIN_TREEIFY_CAPACITY**: 64 (minimum table capacity for treeifying)

### Tree Structure

When a bucket has too many collisions, it converts from a linked list to a Red-Black Tree for better performance.

```java
public class TreeifyExample {
    public static void main(String[] args) {
        // Create HashMap with small capacity to force collisions
        HashMap<CollisionKey, String> map = new HashMap<>(16, 1.0f);

        // Add more than 8 elements with same hash code
        for (int i = 1; i <= 10; i++) {
            map.put(new CollisionKey("Key" + i), "Value" + i);
            System.out.println("Added Key" + i + ", size: " + map.size());
        }

        // The bucket should now be treeified
        System.out.println("Map contents: " + map);

        // Retrieve elements (should be faster due to tree structure)
        for (int i = 1; i <= 10; i++) {
            String value = map.get(new CollisionKey("Key" + i));
            System.out.println("Retrieved Key" + i + ": " + value);
        }
    }
}
```

## Performance Characteristics

### Time Complexity

| Operation         | Average Case | Worst Case |
| ----------------- | ------------ | ---------- |
| **get()**         | O(1)         | O(log n)   |
| **put()**         | O(1)         | O(log n)   |
| **remove()**      | O(1)         | O(log n)   |
| **containsKey()** | O(1)         | O(log n)   |

### Space Complexity

- **Space**: O(n) where n is the number of key-value pairs
- **Overhead**: Additional space for hash table structure

## Common Interview Questions

### 1. How does HashMap handle null keys?

```java
public class NullKeyExample {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();

        // Null key is handled specially
        map.put(null, "Null Value");
        System.out.println("Value for null key: " + map.get(null));

        // Only one null key is allowed
        map.put(null, "New Null Value");
        System.out.println("Updated value for null key: " + map.get(null));

        // Multiple null values are allowed
        map.put("Key1", null);
        map.put("Key2", null);
        System.out.println("Map with null values: " + map);
    }
}
```

### 2. What happens if we use mutable objects as keys?

```java
class MutableKey {
    private String value;

    public MutableKey(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MutableKey that = (MutableKey) obj;
        return Objects.equals(value, that.value);
    }

    @Override
    public String toString() {
        return value;
    }
}

public class MutableKeyExample {
    public static void main(String[] args) {
        HashMap<MutableKey, String> map = new HashMap<>();

        MutableKey key = new MutableKey("Original");
        map.put(key, "Value");

        System.out.println("Before mutation: " + map.get(key));

        // Mutate the key
        key.setValue("Modified");

        System.out.println("After mutation: " + map.get(key));
        System.out.println("Map contents: " + map);

        // Try to get with new key object
        MutableKey newKey = new MutableKey("Modified");
        System.out.println("With new key object: " + map.get(newKey));
    }
}
```

## Best Practices

### 1. Choose Appropriate Initial Capacity

```java
// Good: Specify initial capacity if you know the size
HashMap<String, String> map = new HashMap<>(1000);

// Bad: Let it grow dynamically (causes multiple rehashes)
HashMap<String, String> map = new HashMap<>();
```

### 2. Use Immutable Keys

```java
// Good: Immutable key
public final class ImmutableKey {
    private final String value;

    public ImmutableKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ImmutableKey that = (ImmutableKey) obj;
        return Objects.equals(value, that.value);
    }
}
```

### 3. Override hashCode() and equals() Properly

```java
class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return age == person.age && Objects.equals(name, person.name);
    }
}
```

### 4. Use ConcurrentHashMap for Thread Safety

```java
// For thread-safe operations
ConcurrentHashMap<String, String> threadSafeMap = new ConcurrentHashMap<>();
```

## Practical Examples

### Example 1: Word Frequency Counter

```java
public class WordFrequencyCounter {
    public static void main(String[] args) {
        String text = "the quick brown fox jumps over the lazy dog the fox is quick";
        String[] words = text.split("\\s+");

        HashMap<String, Integer> frequencyMap = new HashMap<>();

        for (String word : words) {
            frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
        }

        System.out.println("Word frequencies:");
        frequencyMap.forEach((word, count) ->
            System.out.println(word + ": " + count));

        // Find most frequent word
        String mostFrequent = frequencyMap.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("None");

        System.out.println("Most frequent word: " + mostFrequent);
    }
}
```

### Example 2: Cache Implementation

```java
public class SimpleCache<K, V> {
    private final HashMap<K, V> cache;
    private final int maxSize;

    public SimpleCache(int maxSize) {
        this.cache = new HashMap<>();
        this.maxSize = maxSize;
    }

    public V get(K key) {
        return cache.get(key);
    }

    public void put(K key, V value) {
        if (cache.size() >= maxSize && !cache.containsKey(key)) {
            // Simple eviction: remove first entry
            K firstKey = cache.keySet().iterator().next();
            cache.remove(firstKey);
        }
        cache.put(key, value);
    }

    public void remove(K key) {
        cache.remove(key);
    }

    public int size() {
        return cache.size();
    }

    public void clear() {
        cache.clear();
    }
}

public class CacheExample {
    public static void main(String[] args) {
        SimpleCache<String, String> cache = new SimpleCache<>(3);

        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        System.out.println("Cache size: " + cache.size());
        System.out.println("Value for key1: " + cache.get("key1"));

        // This will trigger eviction
        cache.put("key4", "value4");
        System.out.println("Cache size after adding key4: " + cache.size());
        System.out.println("Value for key1 after eviction: " + cache.get("key1"));
    }
}
```

## Summary

HashMap is a powerful and efficient data structure that provides constant-time performance for basic operations. Understanding its internal working helps in:

1. **Writing Better Code**: Choose appropriate initial capacity and load factor
2. **Debugging**: Understand why certain operations might be slow
3. **Interview Preparation**: Answer technical questions confidently
4. **Performance Optimization**: Avoid common pitfalls and optimize usage

Key takeaways:

- HashMap uses hash table with separate chaining for collision resolution
- Load factor determines when rehashing occurs
- Treeification improves performance for buckets with many collisions
- Proper implementation of hashCode() and equals() is crucial
- Use immutable objects as keys to avoid issues
- Consider thread safety requirements when choosing between HashMap and ConcurrentHashMap
