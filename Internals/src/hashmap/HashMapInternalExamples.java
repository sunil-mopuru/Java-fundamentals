package hashmap;

import java.util.*;

/**
 * Practical examples demonstrating HashMap internal working concepts
 */
public class HashMapInternalExamples {
    
    public static void main(String[] args) {
        System.out.println("=== HashMap Internal Working Examples ===\n");
        
        // Example 1: Basic HashMap operations
        basicHashMapOperations();
        
        // Example 2: Hash collision demonstration
        hashCollisionExample();
        
        // Example 3: Load factor and rehashing
        loadFactorAndRehashing();
        
        // Example 4: Null key handling
        nullKeyHandling();
        
        // Example 5: Mutable key issues
        mutableKeyIssues();
        
        // Example 6: Performance comparison
        performanceComparison();
        
        // Example 7: Word frequency counter
        wordFrequencyCounter();
        
        // Example 8: Custom cache implementation
        customCacheExample();
    }
    
    /**
     * Example 1: Basic HashMap operations
     */
    public static void basicHashMapOperations() {
        System.out.println("1. Basic HashMap Operations:");
        System.out.println("---------------------------");
        
        HashMap<String, Integer> map = new HashMap<>();
        
        // Put operations
        map.put("Alice", 25);
        map.put("Bob", 30);
        map.put("Charlie", 35);
        
        System.out.println("After adding elements: " + map);
        System.out.println("Map size: " + map.size());
        
        // Get operations
        System.out.println("Alice's age: " + map.get("Alice"));
        System.out.println("Bob's age: " + map.get("Bob"));
        System.out.println("Non-existent key: " + map.get("David"));
        
        // Update existing key
        map.put("Alice", 26);
        System.out.println("After updating Alice: " + map.get("Alice"));
        
        // Remove operation
        map.remove("Bob");
        System.out.println("After removing Bob: " + map);
        System.out.println();
    }
    
    /**
     * Example 2: Hash collision demonstration
     */
    public static void hashCollisionExample() {
        System.out.println("2. Hash Collision Example:");
        System.out.println("--------------------------");
        
        // Create keys that will collide (same hash code)
        HashMap<CollisionKey, String> map = new HashMap<>();
        
        CollisionKey key1 = new CollisionKey("Key1");
        CollisionKey key2 = new CollisionKey("Key2");
        CollisionKey key3 = new CollisionKey("Key3");
        CollisionKey key4 = new CollisionKey("Key4");
        
        // All these keys have the same hash code (1)
        System.out.println("Hash code of Key1: " + key1.hashCode());
        System.out.println("Hash code of Key2: " + key2.hashCode());
        System.out.println("Hash code of Key3: " + key3.hashCode());
        System.out.println("Hash code of Key4: " + key4.hashCode());
        
        // Add elements (they will all go to the same bucket)
        map.put(key1, "Value1");
        map.put(key2, "Value2");
        map.put(key3, "Value3");
        map.put(key4, "Value4");
        
        System.out.println("Map size: " + map.size());
        System.out.println("All keys are in the same bucket due to collision");
        
        // Retrieve values
        System.out.println("Value for Key1: " + map.get(key1));
        System.out.println("Value for Key3: " + map.get(key3));
        
        // Try with new key object (should still work due to equals method)
        CollisionKey newKey1 = new CollisionKey("Key1");
        System.out.println("Value with new Key1 object: " + map.get(newKey1));
        System.out.println();
    }
    
    /**
     * Example 3: Load factor and rehashing
     */
    public static void loadFactorAndRehashing() {
        System.out.println("3. Load Factor and Rehashing:");
        System.out.println("-----------------------------");
        
        // Create HashMap with small capacity and high load factor to see rehashing
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
        
        // Continue adding to see next rehash
        for (int i = 5; i <= 10; i++) {
            map.put("Key" + i, i);
        }
        System.out.println("After adding more elements: size=" + map.size());
        System.out.println();
    }
    
    /**
     * Example 4: Null key handling
     */
    public static void nullKeyHandling() {
        System.out.println("4. Null Key Handling:");
        System.out.println("---------------------");
        
        HashMap<String, String> map = new HashMap<>();
        
        // Null key is handled specially
        map.put(null, "Null Value");
        System.out.println("Value for null key: " + map.get(null));
        
        // Only one null key is allowed (updates existing)
        map.put(null, "New Null Value");
        System.out.println("Updated value for null key: " + map.get(null));
        
        // Multiple null values are allowed
        map.put("Key1", null);
        map.put("Key2", null);
        System.out.println("Map with null values: " + map);
        
        // Check if null key exists
        System.out.println("Contains null key: " + map.containsKey(null));
        System.out.println("Contains null value: " + map.containsValue(null));
        System.out.println();
    }
    
    /**
     * Example 5: Mutable key issues
     */
    public static void mutableKeyIssues() {
        System.out.println("5. Mutable Key Issues:");
        System.out.println("----------------------");
        
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
        
        // This demonstrates why mutable keys are problematic
        System.out.println("The key is lost because its hash code changed!");
        System.out.println();
    }
    
    /**
     * Example 6: Performance comparison
     */
    public static void performanceComparison() {
        System.out.println("6. Performance Comparison:");
        System.out.println("--------------------------");
        
        int size = 100000;
        
        // HashMap performance
        HashMap<Integer, String> hashMap = new HashMap<>();
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < size; i++) {
            hashMap.put(i, "Value" + i);
        }
        
        long putTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            hashMap.get(i);
        }
        long getTime = System.currentTimeMillis() - startTime;
        
        System.out.println("HashMap Performance:");
        System.out.println("Put " + size + " elements: " + putTime + "ms");
        System.out.println("Get " + size + " elements: " + getTime + "ms");
        
        // TreeMap performance (for comparison)
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        startTime = System.currentTimeMillis();
        
        for (int i = 0; i < size; i++) {
            treeMap.put(i, "Value" + i);
        }
        
        long treePutTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            treeMap.get(i);
        }
        long treeGetTime = System.currentTimeMillis() - startTime;
        
        System.out.println("\nTreeMap Performance:");
        System.out.println("Put " + size + " elements: " + treePutTime + "ms");
        System.out.println("Get " + size + " elements: " + treeGetTime + "ms");
        
        System.out.println("\nHashMap is generally faster for random access!");
        System.out.println();
    }
    
    /**
     * Example 7: Word frequency counter
     */
    public static void wordFrequencyCounter() {
        System.out.println("7. Word Frequency Counter:");
        System.out.println("--------------------------");
        
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
        System.out.println();
    }
    
    /**
     * Example 8: Custom cache implementation
     */
    public static void customCacheExample() {
        System.out.println("8. Custom Cache Implementation:");
        System.out.println("-------------------------------");
        
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
        System.out.println("Value for key4: " + cache.get("key4"));
        System.out.println();
    }
}

/**
 * Custom key class that always returns the same hash code (for collision demonstration)
 */
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

/**
 * Mutable key class to demonstrate issues with mutable keys
 */
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

/**
 * Simple cache implementation using HashMap
 */
class SimpleCache<K, V> {
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
    
    @Override
    public String toString() {
        return cache.toString();
    }
} 