package hashmap;

import java.util.Objects;

/**
 * Custom HashMap implementation to demonstrate internal working concepts
 * This is a simplified version for educational purposes
 */
public class CustomHashMap<K, V> {
    
    /**
     * Node class representing a key-value pair
     */
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;
        
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
    
    // Internal storage
    private Node<K, V>[] table;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private int threshold;
    
    @SuppressWarnings("unchecked")
    public CustomHashMap() {
        table = new Node[DEFAULT_CAPACITY];
        threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR);
    }
    
    @SuppressWarnings("unchecked")
    public CustomHashMap(int initialCapacity) {
        table = new Node[initialCapacity];
        threshold = (int) (initialCapacity * LOAD_FACTOR);
    }
    
    /**
     * Put a key-value pair into the map
     */
    public V put(K key, V value) {
        if (key == null) {
            return putForNullKey(value);
        }
        
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        
        // Check if key already exists
        for (Node<K, V> node = table[index]; node != null; node = node.next) {
            if (node.key.equals(key)) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }
        
        // Add new node
        addEntry(hash, key, value, index);
        return null;
    }
    
    /**
     * Get value by key
     */
    public V get(K key) {
        if (key == null) {
            return getForNullKey();
        }
        
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        
        for (Node<K, V> node = table[index]; node != null; node = node.next) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        
        return null;
    }
    
    /**
     * Remove a key-value pair
     */
    public V remove(K key) {
        if (key == null) {
            return removeForNullKey();
        }
        
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        
        Node<K, V> prev = null;
        for (Node<K, V> node = table[index]; node != null; node = node.next) {
            if (node.key.equals(key)) {
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return node.value;
            }
            prev = node;
        }
        
        return null;
    }
    
    /**
     * Check if key exists
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }
    
    /**
     * Get map size
     */
    public int size() {
        return size;
    }
    
    /**
     * Check if map is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Clear all entries
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        table = new Node[table.length];
        size = 0;
    }
    
    /**
     * Handle null key put operation
     */
    private V putForNullKey(V value) {
        for (Node<K, V> node = table[0]; node != null; node = node.next) {
            if (node.key == null) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }
        addEntry(0, null, value, 0);
        return null;
    }
    
    /**
     * Handle null key get operation
     */
    private V getForNullKey() {
        for (Node<K, V> node = table[0]; node != null; node = node.next) {
            if (node.key == null) {
                return node.value;
            }
        }
        return null;
    }
    
    /**
     * Handle null key remove operation
     */
    private V removeForNullKey() {
        Node<K, V> prev = null;
        for (Node<K, V> node = table[0]; node != null; node = node.next) {
            if (node.key == null) {
                if (prev == null) {
                    table[0] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return node.value;
            }
            prev = node;
        }
        return null;
    }
    
    /**
     * Add new entry to the map
     */
    private void addEntry(int hash, K key, V value, int index) {
        Node<K, V> node = new Node<>(key, value);
        node.next = table[index];
        table[index] = node;
        size++;
        
        if (size > threshold) {
            resize();
        }
    }
    
    /**
     * Calculate hash code
     */
    private int hash(K key) {
        return key.hashCode();
    }
    
    /**
     * Calculate bucket index
     */
    private int indexFor(int hash, int length) {
        return hash & (length - 1);
    }
    
    /**
     * Resize the table when threshold is reached
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldTable = table;
        int oldCapacity = oldTable.length;
        int newCapacity = oldCapacity * 2;
        
        table = new Node[newCapacity];
        threshold = (int) (newCapacity * LOAD_FACTOR);
        size = 0;
        
        // Rehash all existing entries
        for (Node<K, V> node : oldTable) {
            while (node != null) {
                put(node.key, node.value);
                node = node.next;
            }
        }
        
        System.out.println("Resized from " + oldCapacity + " to " + newCapacity);
    }
    
    /**
     * Get current capacity
     */
    public int getCapacity() {
        return table.length;
    }
    
    /**
     * Get current threshold
     */
    public int getThreshold() {
        return threshold;
    }
    
    /**
     * Print internal structure for debugging
     */
    public void printInternalStructure() {
        System.out.println("=== CustomHashMap Internal Structure ===");
        System.out.println("Capacity: " + table.length);
        System.out.println("Size: " + size);
        System.out.println("Threshold: " + threshold);
        System.out.println("Load Factor: " + LOAD_FACTOR);
        System.out.println();
        
        for (int i = 0; i < table.length; i++) {
            System.out.print("Bucket[" + i + "]: ");
            Node<K, V> node = table[i];
            if (node == null) {
                System.out.println("null");
            } else {
                while (node != null) {
                    System.out.print(node);
                    if (node.next != null) {
                        System.out.print(" -> ");
                    }
                    node = node.next;
                }
                System.out.println();
            }
        }
        System.out.println();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        
        boolean first = true;
        for (Node<K, V> node : table) {
            while (node != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(node);
                first = false;
                node = node.next;
            }
        }
        
        sb.append("}");
        return sb.toString();
    }
    
    /**
     * Demo method to show how the custom HashMap works
     */
    public static void main(String[] args) {
        System.out.println("=== Custom HashMap Demo ===\n");
        
        CustomHashMap<String, Integer> map = new CustomHashMap<>(4);
        
        System.out.println("Initial state:");
        map.printInternalStructure();
        
        // Add some elements
        System.out.println("Adding elements...");
        map.put("Alice", 25);
        map.put("Bob", 30);
        map.put("Charlie", 35);
        
        System.out.println("After adding 3 elements:");
        map.printInternalStructure();
        
        // This will trigger resize
        System.out.println("Adding one more element (will trigger resize)...");
        map.put("David", 40);
        
        System.out.println("After resize:");
        map.printInternalStructure();
        
        // Test get operations
        System.out.println("Get operations:");
        System.out.println("Alice's age: " + map.get("Alice"));
        System.out.println("Bob's age: " + map.get("Bob"));
        System.out.println("Non-existent: " + map.get("Eve"));
        
        // Test null key
        System.out.println("\nTesting null key:");
        map.put(null, 50);
        System.out.println("Null key value: " + map.get(null));
        
        // Test remove
        System.out.println("\nTesting remove:");
        System.out.println("Removing Bob: " + map.remove("Bob"));
        System.out.println("After removing Bob:");
        map.printInternalStructure();
        
        // Test toString
        System.out.println("Map contents: " + map);
        
        // Test collision
        System.out.println("\n=== Testing Hash Collision ===");
        CustomHashMap<CollisionKey, String> collisionMap = new CustomHashMap<>(4);
        
        CollisionKey key1 = new CollisionKey("Key1");
        CollisionKey key2 = new CollisionKey("Key2");
        CollisionKey key3 = new CollisionKey("Key3");
        
        collisionMap.put(key1, "Value1");
        collisionMap.put(key2, "Value2");
        collisionMap.put(key3, "Value3");
        
        System.out.println("Collision map structure:");
        collisionMap.printInternalStructure();
        
        System.out.println("Retrieving values:");
        System.out.println("Key1: " + collisionMap.get(key1));
        System.out.println("Key2: " + collisionMap.get(key2));
        System.out.println("Key3: " + collisionMap.get(key3));
    }
} 