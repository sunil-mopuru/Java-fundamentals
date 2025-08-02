# Advanced Data Structures in Java

## Overview

Advanced data structures are specialized structures designed for specific use cases and complex problems. This guide covers graphs, tries, segment trees, and disjoint sets, which are essential for solving advanced algorithmic problems.

## 1. Graph Data Structures

### What are Graphs?

A graph is a non-linear data structure consisting of vertices (nodes) and edges that connect these vertices. Graphs are used to represent relationships between objects.

### Key Characteristics

- **Vertices (Nodes)**: Points in the graph
- **Edges**: Connections between vertices
- **Directed/Undirected**: Edges can have direction or not
- **Weighted/Unweighted**: Edges can have weights or not

### Graph Representations

#### 1.1 Adjacency Matrix

```java
class GraphMatrix {
    private int[][] adjacencyMatrix;
    private int vertices;

    public GraphMatrix(int vertices) {
        this.vertices = vertices;
        this.adjacencyMatrix = new int[vertices][vertices];
    }

    // Add edge
    public void addEdge(int source, int destination) {
        adjacencyMatrix[source][destination] = 1;
        // For undirected graph, also add reverse edge
        adjacencyMatrix[destination][source] = 1;
    }

    // Add weighted edge
    public void addWeightedEdge(int source, int destination, int weight) {
        adjacencyMatrix[source][destination] = weight;
        adjacencyMatrix[destination][source] = weight;
    }

    // Remove edge
    public void removeEdge(int source, int destination) {
        adjacencyMatrix[source][destination] = 0;
        adjacencyMatrix[destination][source] = 0;
    }

    // Check if edge exists
    public boolean hasEdge(int source, int destination) {
        return adjacencyMatrix[source][destination] != 0;
    }

    // Get edge weight
    public int getEdgeWeight(int source, int destination) {
        return adjacencyMatrix[source][destination];
    }

    // Print graph
    public void printGraph() {
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
```

#### 1.2 Adjacency List

```java
class GraphList {
    private Map<Integer, List<Integer>> adjacencyList;
    private int vertices;

    public GraphList(int vertices) {
        this.vertices = vertices;
        this.adjacencyList = new HashMap<>();
        for (int i = 0; i < vertices; i++) {
            adjacencyList.put(i, new ArrayList<>());
        }
    }

    // Add edge
    public void addEdge(int source, int destination) {
        adjacencyList.get(source).add(destination);
        // For undirected graph, also add reverse edge
        adjacencyList.get(destination).add(source);
    }

    // Remove edge
    public void removeEdge(int source, int destination) {
        adjacencyList.get(source).remove(Integer.valueOf(destination));
        adjacencyList.get(destination).remove(Integer.valueOf(source));
    }

    // Check if edge exists
    public boolean hasEdge(int source, int destination) {
        return adjacencyList.get(source).contains(destination);
    }

    // Get neighbors
    public List<Integer> getNeighbors(int vertex) {
        return adjacencyList.get(vertex);
    }

    // Print graph
    public void printGraph() {
        for (int vertex : adjacencyList.keySet()) {
            System.out.print("Vertex " + vertex + " -> ");
            for (int neighbor : adjacencyList.get(vertex)) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }
}
```

### Graph Traversal Algorithms

#### 1.1 Depth-First Search (DFS)

```java
class GraphTraversal {
    private boolean[] visited;
    private GraphList graph;

    public GraphTraversal(GraphList graph) {
        this.graph = graph;
        this.visited = new boolean[graph.getVertices()];
    }

    // DFS using recursion
    public void dfsRecursive(int start) {
        visited[start] = true;
        System.out.print(start + " ");

        for (int neighbor : graph.getNeighbors(start)) {
            if (!visited[neighbor]) {
                dfsRecursive(neighbor);
            }
        }
    }

    // DFS using stack (iterative)
    public void dfsIterative(int start) {
        Stack<Integer> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (!visited[current]) {
                visited[current] = true;
                System.out.print(current + " ");

                for (int neighbor : graph.getNeighbors(current)) {
                    if (!visited[neighbor]) {
                        stack.push(neighbor);
                    }
                }
            }
        }
    }

    // Reset visited array
    public void resetVisited() {
        Arrays.fill(visited, false);
    }
}
```

#### 1.2 Breadth-First Search (BFS)

```java
public void bfs(int start) {
    Queue<Integer> queue = new LinkedList<>();
    queue.add(start);
    visited[start] = true;

    while (!queue.isEmpty()) {
        int current = queue.poll();
        System.out.print(current + " ");

        for (int neighbor : graph.getNeighbors(current)) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                queue.add(neighbor);
            }
        }
    }
}
```

### Shortest Path Algorithms

#### 1.1 Dijkstra's Algorithm

```java
class Dijkstra {
    private GraphMatrix graph;
    private int vertices;

    public Dijkstra(GraphMatrix graph) {
        this.graph = graph;
        this.vertices = graph.getVertices();
    }

    public int[] shortestPath(int source) {
        int[] distances = new int[vertices];
        boolean[] visited = new boolean[vertices];

        // Initialize distances
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;

        for (int count = 0; count < vertices - 1; count++) {
            // Find vertex with minimum distance
            int minVertex = findMinDistance(distances, visited);
            visited[minVertex] = true;

            // Update distances of adjacent vertices
            for (int v = 0; v < vertices; v++) {
                if (!visited[v] &&
                    graph.hasEdge(minVertex, v) &&
                    distances[minVertex] != Integer.MAX_VALUE &&
                    distances[minVertex] + graph.getEdgeWeight(minVertex, v) < distances[v]) {

                    distances[v] = distances[minVertex] + graph.getEdgeWeight(minVertex, v);
                }
            }
        }

        return distances;
    }

    private int findMinDistance(int[] distances, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < vertices; v++) {
            if (!visited[v] && distances[v] <= min) {
                min = distances[v];
                minIndex = v;
            }
        }

        return minIndex;
    }
}
```

## 2. Trie (Prefix Tree)

### What is a Trie?

A trie is a tree-like data structure used to store a dynamic set of strings, where the keys are usually strings. It is particularly useful for applications involving string operations like autocomplete and spell checking.

### Key Characteristics

- **Prefix-based**: Efficient for prefix searches
- **Space efficient**: Shares common prefixes
- **Fast search**: O(m) where m is string length
- **String operations**: Great for string-related problems

### Trie Implementation

```java
class Trie {
    private static class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;

        TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
        }
    }

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Insert a word
    public void insert(String word) {
        TrieNode current = root;

        for (char c : word.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }

        current.isEndOfWord = true;
    }

    // Search for a word
    public boolean search(String word) {
        TrieNode current = root;

        for (char c : word.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return false;
            }
            current = current.children.get(c);
        }

        return current.isEndOfWord;
    }

    // Check if any word starts with the given prefix
    public boolean startsWith(String prefix) {
        TrieNode current = root;

        for (char c : prefix.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return false;
            }
            current = current.children.get(c);
        }

        return true;
    }

    // Delete a word
    public boolean delete(String word) {
        return deleteHelper(root, word, 0);
    }

    private boolean deleteHelper(TrieNode current, String word, int index) {
        if (index == word.length()) {
            if (!current.isEndOfWord) {
                return false;
            }
            current.isEndOfWord = false;
            return current.children.isEmpty();
        }

        char c = word.charAt(index);
        TrieNode child = current.children.get(c);

        if (child == null) {
            return false;
        }

        boolean shouldDeleteChild = deleteHelper(child, word, index + 1);

        if (shouldDeleteChild) {
            current.children.remove(c);
            return current.children.isEmpty() && !current.isEndOfWord;
        }

        return false;
    }

    // Get all words with given prefix
    public List<String> getWordsWithPrefix(String prefix) {
        List<String> result = new ArrayList<>();
        TrieNode current = root;

        // Navigate to the prefix
        for (char c : prefix.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return result;
            }
            current = current.children.get(c);
        }

        // Collect all words from this node
        collectWords(current, prefix, result);
        return result;
    }

    private void collectWords(TrieNode node, String prefix, List<String> result) {
        if (node.isEndOfWord) {
            result.add(prefix);
        }

        for (char c : node.children.keySet()) {
            collectWords(node.children.get(c), prefix + c, result);
        }
    }

    // Count words with given prefix
    public int countWordsWithPrefix(String prefix) {
        TrieNode current = root;

        for (char c : prefix.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return 0;
            }
            current = current.children.get(c);
        }

        return countWords(current);
    }

    private int countWords(TrieNode node) {
        int count = node.isEndOfWord ? 1 : 0;

        for (TrieNode child : node.children.values()) {
            count += countWords(child);
        }

        return count;
    }
}
```

## 3. Segment Tree

### What is a Segment Tree?

A segment tree is a tree data structure used for storing information about intervals, or segments. It allows querying which of the stored segments contain a given point.

### Key Characteristics

- **Range queries**: Efficient for range-based operations
- **Point updates**: Can update individual elements
- **Range updates**: Can update ranges of elements
- **Flexible operations**: Supports sum, min, max, etc.

### Segment Tree Implementation

```java
class SegmentTree {
    private int[] tree;
    private int[] data;
    private int n;

    public SegmentTree(int[] arr) {
        this.data = arr;
        this.n = arr.length;
        this.tree = new int[4 * n];
        build(1, 0, n - 1);
    }

    // Build segment tree
    private void build(int node, int start, int end) {
        if (start == end) {
            tree[node] = data[start];
            return;
        }

        int mid = (start + end) / 2;
        build(2 * node, start, mid);
        build(2 * node + 1, mid + 1, end);

        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }

    // Range sum query
    public int rangeSum(int left, int right) {
        return rangeSumQuery(1, 0, n - 1, left, right);
    }

    private int rangeSumQuery(int node, int start, int end, int left, int right) {
        if (right < start || left > end) {
            return 0;
        }

        if (left <= start && right >= end) {
            return tree[node];
        }

        int mid = (start + end) / 2;
        int leftSum = rangeSumQuery(2 * node, start, mid, left, right);
        int rightSum = rangeSumQuery(2 * node + 1, mid + 1, end, left, right);

        return leftSum + rightSum;
    }

    // Point update
    public void update(int index, int value) {
        updatePoint(1, 0, n - 1, index, value);
    }

    private void updatePoint(int node, int start, int end, int index, int value) {
        if (start == end) {
            data[index] = value;
            tree[node] = value;
            return;
        }

        int mid = (start + end) / 2;
        if (index <= mid) {
            updatePoint(2 * node, start, mid, index, value);
        } else {
            updatePoint(2 * node + 1, mid + 1, end, index, value);
        }

        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }

    // Range update with lazy propagation
    private int[] lazy;

    public SegmentTree(int[] arr, boolean useLazy) {
        this.data = arr;
        this.n = arr.length;
        this.tree = new int[4 * n];
        this.lazy = new int[4 * n];
        build(1, 0, n - 1);
    }

    public void rangeUpdate(int left, int right, int value) {
        rangeUpdateLazy(1, 0, n - 1, left, right, value);
    }

    private void rangeUpdateLazy(int node, int start, int end, int left, int right, int value) {
        // Update lazy value
        if (lazy[node] != 0) {
            tree[node] += lazy[node] * (end - start + 1);
            if (start != end) {
                lazy[2 * node] += lazy[node];
                lazy[2 * node + 1] += lazy[node];
            }
            lazy[node] = 0;
        }

        if (right < start || left > end) {
            return;
        }

        if (left <= start && right >= end) {
            tree[node] += value * (end - start + 1);
            if (start != end) {
                lazy[2 * node] += value;
                lazy[2 * node + 1] += value;
            }
            return;
        }

        int mid = (start + end) / 2;
        rangeUpdateLazy(2 * node, start, mid, left, right, value);
        rangeUpdateLazy(2 * node + 1, mid + 1, end, left, right, value);

        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }

    public int rangeSumLazy(int left, int right) {
        return rangeSumQueryLazy(1, 0, n - 1, left, right);
    }

    private int rangeSumQueryLazy(int node, int start, int end, int left, int right) {
        if (lazy[node] != 0) {
            tree[node] += lazy[node] * (end - start + 1);
            if (start != end) {
                lazy[2 * node] += lazy[node];
                lazy[2 * node + 1] += lazy[node];
            }
            lazy[node] = 0;
        }

        if (right < start || left > end) {
            return 0;
        }

        if (left <= start && right >= end) {
            return tree[node];
        }

        int mid = (start + end) / 2;
        int leftSum = rangeSumQueryLazy(2 * node, start, mid, left, right);
        int rightSum = rangeSumQueryLazy(2 * node + 1, mid + 1, end, left, right);

        return leftSum + rightSum;
    }
}
```

## 4. Disjoint Set (Union-Find)

### What is a Disjoint Set?

A disjoint set is a data structure that tracks a set of elements partitioned into a number of disjoint (non-overlapping) subsets. It provides near-constant-time operations for merging sets and finding which set an element belongs to.

### Key Characteristics

- **Union operation**: Merge two sets
- **Find operation**: Find which set an element belongs to
- **Path compression**: Optimizes find operations
- **Union by rank**: Optimizes union operations

### Disjoint Set Implementation

```java
class DisjointSet {
    private int[] parent;
    private int[] rank;
    private int count;

    public DisjointSet(int size) {
        parent = new int[size];
        rank = new int[size];
        count = size;

        // Initialize each element as its own set
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    // Find the root of the set containing element x
    public int find(int x) {
        if (parent[x] != x) {
            // Path compression
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // Union two sets
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) {
            return; // Already in the same set
        }

        // Union by rank
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }

        count--; // Decrease number of sets
    }

    // Check if two elements are in the same set
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    // Get the number of sets
    public int getCount() {
        return count;
    }

    // Get the size of the set containing element x
    public int getSize(int x) {
        int root = find(x);
        int size = 0;
        for (int i = 0; i < parent.length; i++) {
            if (find(i) == root) {
                size++;
            }
        }
        return size;
    }

    // Get all elements in the same set as x
    public List<Integer> getSet(int x) {
        int root = find(x);
        List<Integer> set = new ArrayList<>();
        for (int i = 0; i < parent.length; i++) {
            if (find(i) == root) {
                set.add(i);
            }
        }
        return set;
    }

    // Print all sets
    public void printSets() {
        Map<Integer, List<Integer>> sets = new HashMap<>();

        for (int i = 0; i < parent.length; i++) {
            int root = find(i);
            sets.computeIfAbsent(root, k -> new ArrayList<>()).add(i);
        }

        for (List<Integer> set : sets.values()) {
            System.out.println("Set: " + set);
        }
    }
}
```

## 5. Applications and Use Cases

### 1. Graph Applications

- **Social Networks**: Friend connections and recommendations
- **Network Routing**: Finding shortest paths
- **Game Development**: AI pathfinding
- **Web Crawling**: Link analysis

### 2. Trie Applications

- **Autocomplete**: Text editors and search engines
- **Spell Checker**: Dictionary lookups
- **IP Routing**: Longest prefix matching
- **DNA Sequence Analysis**: Pattern matching

### 3. Segment Tree Applications

- **Range Queries**: Database operations
- **Game Development**: Collision detection
- **Image Processing**: Histogram operations
- **Financial Data**: Stock price analysis

### 4. Disjoint Set Applications

- **Kruskal's Algorithm**: Minimum spanning tree
- **Connected Components**: Graph analysis
- **Image Segmentation**: Computer vision
- **Network Connectivity**: Network analysis

## 6. Performance Analysis

### Time Complexity

- **Graph Operations**: Varies by algorithm
- **Trie Operations**: O(m) where m is string length
- **Segment Tree**: O(log n) for queries and updates
- **Disjoint Set**: Near O(1) with optimizations

### Space Complexity

- **Graph**: O(V²) for matrix, O(V + E) for list
- **Trie**: O(ALPHABET_SIZE _ N _ M) where N is number of strings, M is average length
- **Segment Tree**: O(4n) for full implementation
- **Disjoint Set**: O(n) where n is number of elements

## 7. Best Practices

1. **Choose the Right Structure**: Match the data structure to your problem
2. **Optimize for Your Use Case**: Consider space vs time trade-offs
3. **Handle Edge Cases**: Consider empty inputs and boundary conditions
4. **Use Built-in Libraries**: Leverage Java's Collections framework when possible
5. **Profile Performance**: Measure actual performance in your specific context

## 8. Practice Problems

1. **Graph Problems**:

   - Number of islands
   - Course schedule
   - Word ladder
   - Network delay time

2. **Trie Problems**:

   - Word search II
   - Design add and search words
   - Longest word in dictionary
   - Replace words

3. **Segment Tree Problems**:

   - Range sum query
   - Count of smaller numbers after self
   - My calendar I
   - Range sum query 2D

4. **Disjoint Set Problems**:
   - Number of provinces
   - Redundant connection
   - Accounts merge
   - Satisfiability of equality equations

Advanced data structures are essential for solving complex algorithmic problems and are widely used in real-world applications.
