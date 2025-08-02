# Tree Data Structures in Java

## Overview
Tree data structures are hierarchical data structures that consist of nodes connected by edges. Each node contains data and references to its child nodes. Trees are fundamental data structures used in many applications like file systems, databases, and compilers.

## 1. Binary Trees

### What are Binary Trees?
A binary tree is a tree data structure where each node has at most two children, referred to as the left child and right child.

### Key Characteristics
- **Each node has at most 2 children**
- **Left and right child references**
- **Hierarchical structure**
- **Recursive nature**

### Binary Tree Implementation

```java
class TreeNode {
    int data;
    TreeNode left;
    TreeNode right;
    
    TreeNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

class BinaryTree {
    TreeNode root;
    
    public BinaryTree() {
        this.root = null;
    }
    
    // Insert a node
    public void insert(int data) {
        root = insertRec(root, data);
    }
    
    private TreeNode insertRec(TreeNode root, int data) {
        if (root == null) {
            root = new TreeNode(data);
            return root;
        }
        
        if (data < root.data) {
            root.left = insertRec(root.left, data);
        } else if (data > root.data) {
            root.right = insertRec(root.right, data);
        }
        
        return root;
    }
    
    // Inorder traversal
    public void inorderTraversal() {
        inorderRec(root);
        System.out.println();
    }
    
    private void inorderRec(TreeNode root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.data + " ");
            inorderRec(root.right);
        }
    }
    
    // Preorder traversal
    public void preorderTraversal() {
        preorderRec(root);
        System.out.println();
    }
    
    private void preorderRec(TreeNode root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preorderRec(root.left);
            preorderRec(root.right);
        }
    }
    
    // Postorder traversal
    public void postorderTraversal() {
        postorderRec(root);
        System.out.println();
    }
    
    private void postorderRec(TreeNode root) {
        if (root != null) {
            postorderRec(root.left);
            postorderRec(root.right);
            System.out.print(root.data + " ");
        }
    }
    
    // Level order traversal (BFS)
    public void levelOrderTraversal() {
        if (root == null) return;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.data + " ");
            
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        System.out.println();
    }
    
    // Find height of tree
    public int getHeight() {
        return getHeightRec(root);
    }
    
    private int getHeightRec(TreeNode root) {
        if (root == null) return 0;
        
        int leftHeight = getHeightRec(root.left);
        int rightHeight = getHeightRec(root.right);
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    // Count nodes
    public int countNodes() {
        return countNodesRec(root);
    }
    
    private int countNodesRec(TreeNode root) {
        if (root == null) return 0;
        
        return 1 + countNodesRec(root.left) + countNodesRec(root.right);
    }
    
    // Search for a node
    public boolean search(int data) {
        return searchRec(root, data);
    }
    
    private boolean searchRec(TreeNode root, int data) {
        if (root == null || root.data == data) {
            return root != null;
        }
        
        if (data < root.data) {
            return searchRec(root.left, data);
        }
        
        return searchRec(root.right, data);
    }
}
```

### Tree Traversal Algorithms

#### 1. Depth-First Search (DFS)
```java
// Inorder: Left -> Root -> Right
public void inorderTraversal(TreeNode root) {
    if (root != null) {
        inorderTraversal(root.left);
        System.out.print(root.data + " ");
        inorderTraversal(root.right);
    }
}

// Preorder: Root -> Left -> Right
public void preorderTraversal(TreeNode root) {
    if (root != null) {
        System.out.print(root.data + " ");
        preorderTraversal(root.left);
        preorderTraversal(root.right);
    }
}

// Postorder: Left -> Right -> Root
public void postorderTraversal(TreeNode root) {
    if (root != null) {
        postorderTraversal(root.left);
        postorderTraversal(root.right);
        System.out.print(root.data + " ");
    }
}
```

#### 2. Breadth-First Search (BFS)
```java
public void levelOrderTraversal(TreeNode root) {
    if (root == null) return;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    
    while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        System.out.print(node.data + " ");
        
        if (node.left != null) {
            queue.add(node.left);
        }
        if (node.right != null) {
            queue.add(node.right);
        }
    }
}
```

## 2. Binary Search Trees (BST)

### What are Binary Search Trees?
A Binary Search Tree is a binary tree where for each node, all elements in the left subtree are less than the node's value, and all elements in the right subtree are greater than the node's value.

### Key Characteristics
- **Ordered structure**: Left subtree < Root < Right subtree
- **Efficient search**: O(log n) average case
- **Sorted traversal**: Inorder traversal gives sorted order
- **Unique values**: No duplicate values (typically)

### BST Implementation

```java
class BinarySearchTree {
    TreeNode root;
    
    public BinarySearchTree() {
        this.root = null;
    }
    
    // Insert a node
    public void insert(int data) {
        root = insertRec(root, data);
    }
    
    private TreeNode insertRec(TreeNode root, int data) {
        if (root == null) {
            root = new TreeNode(data);
            return root;
        }
        
        if (data < root.data) {
            root.left = insertRec(root.left, data);
        } else if (data > root.data) {
            root.right = insertRec(root.right, data);
        }
        
        return root;
    }
    
    // Search for a node
    public boolean search(int data) {
        return searchRec(root, data);
    }
    
    private boolean searchRec(TreeNode root, int data) {
        if (root == null || root.data == data) {
            return root != null;
        }
        
        if (data < root.data) {
            return searchRec(root.left, data);
        }
        
        return searchRec(root.right, data);
    }
    
    // Delete a node
    public void delete(int data) {
        root = deleteRec(root, data);
    }
    
    private TreeNode deleteRec(TreeNode root, int data) {
        if (root == null) return root;
        
        if (data < root.data) {
            root.left = deleteRec(root.left, data);
        } else if (data > root.data) {
            root.right = deleteRec(root.right, data);
        } else {
            // Node to be deleted found
            
            // Case 1: Node with only one child or no child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            
            // Case 2: Node with two children
            // Get the inorder successor (smallest in right subtree)
            root.data = minValue(root.right);
            
            // Delete the inorder successor
            root.right = deleteRec(root.right, root.data);
        }
        
        return root;
    }
    
    private int minValue(TreeNode root) {
        int minv = root.data;
        while (root.left != null) {
            minv = root.left.data;
            root = root.left;
        }
        return minv;
    }
    
    // Find minimum value
    public int findMin() {
        if (root == null) return Integer.MIN_VALUE;
        
        TreeNode current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.data;
    }
    
    // Find maximum value
    public int findMax() {
        if (root == null) return Integer.MAX_VALUE;
        
        TreeNode current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.data;
    }
    
    // Check if tree is BST
    public boolean isBST() {
        return isBSTUtil(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private boolean isBSTUtil(TreeNode root, int min, int max) {
        if (root == null) return true;
        
        if (root.data <= min || root.data >= max) {
            return false;
        }
        
        return isBSTUtil(root.left, min, root.data) &&
               isBSTUtil(root.right, root.data, max);
    }
    
    // Find kth smallest element
    public int kthSmallest(int k) {
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(root, inorder);
        return inorder.get(k - 1);
    }
    
    private void inorderTraversal(TreeNode root, List<Integer> result) {
        if (root != null) {
            inorderTraversal(root.left, result);
            result.add(root.data);
            inorderTraversal(root.right, result);
        }
    }
}
```

## 3. AVL Trees

### What are AVL Trees?
An AVL tree is a self-balancing binary search tree where the heights of the left and right subtrees of every node differ by at most one.

### Key Characteristics
- **Self-balancing**: Automatically maintains balance
- **Height-balanced**: Height difference ≤ 1 for all nodes
- **Efficient operations**: O(log n) for all operations
- **Complex implementation**: Requires rotation operations

### AVL Tree Implementation

```java
class AVLNode {
    int data;
    AVLNode left;
    AVLNode right;
    int height;
    
    AVLNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}

class AVLTree {
    AVLNode root;
    
    public AVLTree() {
        this.root = null;
    }
    
    // Get height of node
    private int height(AVLNode node) {
        if (node == null) return 0;
        return node.height;
    }
    
    // Get balance factor
    private int getBalance(AVLNode node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }
    
    // Right rotate
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        
        x.right = y;
        y.left = T2;
        
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        
        return x;
    }
    
    // Left rotate
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        
        y.left = x;
        x.right = T2;
        
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        
        return y;
    }
    
    // Insert a node
    public void insert(int data) {
        root = insertRec(root, data);
    }
    
    private AVLNode insertRec(AVLNode node, int data) {
        // Normal BST insertion
        if (node == null) {
            return new AVLNode(data);
        }
        
        if (data < node.data) {
            node.left = insertRec(node.left, data);
        } else if (data > node.data) {
            node.right = insertRec(node.right, data);
        } else {
            return node; // Duplicate keys not allowed
        }
        
        // Update height
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        
        // Get balance factor
        int balance = getBalance(node);
        
        // Left Left Case
        if (balance > 1 && data < node.left.data) {
            return rightRotate(node);
        }
        
        // Right Right Case
        if (balance < -1 && data > node.right.data) {
            return leftRotate(node);
        }
        
        // Left Right Case
        if (balance > 1 && data > node.left.data) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        
        // Right Left Case
        if (balance < -1 && data < node.right.data) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        
        return node;
    }
    
    // Delete a node
    public void delete(int data) {
        root = deleteRec(root, data);
    }
    
    private AVLNode deleteRec(AVLNode root, int data) {
        if (root == null) return root;
        
        if (data < root.data) {
            root.left = deleteRec(root.left, data);
        } else if (data > root.data) {
            root.right = deleteRec(root.right, data);
        } else {
            // Node to be deleted found
            
            // Node with only one child or no child
            if (root.left == null || root.right == null) {
                AVLNode temp = null;
                if (temp == root.left) {
                    temp = root.right;
                } else {
                    temp = root.left;
                }
                
                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                // Node with two children
                AVLNode temp = minValueNode(root.right);
                root.data = temp.data;
                root.right = deleteRec(root.right, temp.data);
            }
        }
        
        if (root == null) return root;
        
        // Update height
        root.height = Math.max(height(root.left), height(root.right)) + 1;
        
        // Get balance factor
        int balance = getBalance(root);
        
        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }
        
        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        
        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }
        
        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }
        
        return root;
    }
    
    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    
    // Inorder traversal
    public void inorderTraversal() {
        inorderRec(root);
        System.out.println();
    }
    
    private void inorderRec(AVLNode root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.data + " ");
            inorderRec(root.right);
        }
    }
}
```

## 4. Heaps

### What are Heaps?
A heap is a specialized tree-based data structure that satisfies the heap property. In a max heap, for any given node, the value of the node is greater than or equal to the values of its children.

### Key Characteristics
- **Complete binary tree**: All levels are filled except possibly the last
- **Heap property**: Parent is greater than children (max heap) or less than children (min heap)
- **Efficient operations**: O(log n) for insert and delete
- **Priority queue implementation**: Used for priority-based operations

### Heap Implementation

```java
class MaxHeap {
    private int[] heap;
    private int size;
    private int capacity;
    
    public MaxHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new int[capacity];
    }
    
    // Get parent index
    private int parent(int i) {
        return (i - 1) / 2;
    }
    
    // Get left child index
    private int leftChild(int i) {
        return 2 * i + 1;
    }
    
    // Get right child index
    private int rightChild(int i) {
        return 2 * i + 2;
    }
    
    // Check if node is leaf
    private boolean isLeaf(int i) {
        return i >= size / 2 && i < size;
    }
    
    // Swap two elements
    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    // Heapify down
    private void heapifyDown(int i) {
        if (!isLeaf(i)) {
            int left = leftChild(i);
            int right = rightChild(i);
            int largest = i;
            
            if (left < size && heap[left] > heap[largest]) {
                largest = left;
            }
            
            if (right < size && heap[right] > heap[largest]) {
                largest = right;
            }
            
            if (largest != i) {
                swap(i, largest);
                heapifyDown(largest);
            }
        }
    }
    
    // Heapify up
    private void heapifyUp(int i) {
        while (i > 0 && heap[parent(i)] < heap[i]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }
    
    // Insert element
    public void insert(int data) {
        if (size >= capacity) {
            System.out.println("Heap is full!");
            return;
        }
        
        heap[size] = data;
        heapifyUp(size);
        size++;
    }
    
    // Extract maximum element
    public int extractMax() {
        if (size <= 0) {
            System.out.println("Heap is empty!");
            return -1;
        }
        
        int max = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        
        return max;
    }
    
    // Get maximum element
    public int getMax() {
        if (size <= 0) {
            System.out.println("Heap is empty!");
            return -1;
        }
        return heap[0];
    }
    
    // Build heap from array
    public void buildHeap(int[] arr) {
        if (arr.length > capacity) {
            System.out.println("Array too large for heap!");
            return;
        }
        
        size = arr.length;
        System.arraycopy(arr, 0, heap, 0, size);
        
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }
    
    // Heap sort
    public void heapSort(int[] arr) {
        buildHeap(arr);
        
        for (int i = size - 1; i > 0; i--) {
            swap(0, i);
            size--;
            heapifyDown(0);
        }
        
        // Copy back to original array
        System.arraycopy(heap, 0, arr, 0, arr.length);
    }
    
    // Print heap
    public void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }
    
    // Get size
    public int getSize() {
        return size;
    }
    
    // Check if heap is empty
    public boolean isEmpty() {
        return size == 0;
    }
}
```

### Min Heap Implementation

```java
class MinHeap {
    private int[] heap;
    private int size;
    private int capacity;
    
    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new int[capacity];
    }
    
    // Get parent index
    private int parent(int i) {
        return (i - 1) / 2;
    }
    
    // Get left child index
    private int leftChild(int i) {
        return 2 * i + 1;
    }
    
    // Get right child index
    private int rightChild(int i) {
        return 2 * i + 2;
    }
    
    // Check if node is leaf
    private boolean isLeaf(int i) {
        return i >= size / 2 && i < size;
    }
    
    // Swap two elements
    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    // Heapify down
    private void heapifyDown(int i) {
        if (!isLeaf(i)) {
            int left = leftChild(i);
            int right = rightChild(i);
            int smallest = i;
            
            if (left < size && heap[left] < heap[smallest]) {
                smallest = left;
            }
            
            if (right < size && heap[right] < heap[smallest]) {
                smallest = right;
            }
            
            if (smallest != i) {
                swap(i, smallest);
                heapifyDown(smallest);
            }
        }
    }
    
    // Heapify up
    private void heapifyUp(int i) {
        while (i > 0 && heap[parent(i)] > heap[i]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }
    
    // Insert element
    public void insert(int data) {
        if (size >= capacity) {
            System.out.println("Heap is full!");
            return;
        }
        
        heap[size] = data;
        heapifyUp(size);
        size++;
    }
    
    // Extract minimum element
    public int extractMin() {
        if (size <= 0) {
            System.out.println("Heap is empty!");
            return -1;
        }
        
        int min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        
        return min;
    }
    
    // Get minimum element
    public int getMin() {
        if (size <= 0) {
            System.out.println("Heap is empty!");
            return -1;
        }
        return heap[0];
    }
    
    // Print heap
    public void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }
}
```

## 5. Tree Applications

### 1. Expression Trees
```java
class ExpressionTree {
    static class Node {
        char data;
        Node left, right;
        
        Node(char data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
    
    public static Node buildExpressionTree(String postfix) {
        Stack<Node> stack = new Stack<>();
        
        for (char c : postfix.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                stack.push(new Node(c));
            } else {
                Node right = stack.pop();
                Node left = stack.pop();
                Node operator = new Node(c);
                operator.left = left;
                operator.right = right;
                stack.push(operator);
            }
        }
        
        return stack.pop();
    }
    
    public static int evaluateExpressionTree(Node root) {
        if (root == null) return 0;
        
        if (root.left == null && root.right == null) {
            return Character.getNumericValue(root.data);
        }
        
        int leftVal = evaluateExpressionTree(root.left);
        int rightVal = evaluateExpressionTree(root.right);
        
        switch (root.data) {
            case '+': return leftVal + rightVal;
            case '-': return leftVal - rightVal;
            case '*': return leftVal * rightVal;
            case '/': return leftVal / rightVal;
            default: return 0;
        }
    }
}
```

### 2. Binary Tree Serialization
```java
public class TreeSerialization {
    public static String serialize(TreeNode root) {
        if (root == null) return "null";
        
        return root.data + "," + serialize(root.left) + "," + serialize(root.right);
    }
    
    public static TreeNode deserialize(String data) {
        Queue<String> queue = new LinkedList<>(Arrays.asList(data.split(",")));
        return deserializeHelper(queue);
    }
    
    private static TreeNode deserializeHelper(Queue<String> queue) {
        String val = queue.poll();
        if ("null".equals(val)) return null;
        
        TreeNode root = new TreeNode(Integer.parseInt(val));
        root.left = deserializeHelper(queue);
        root.right = deserializeHelper(queue);
        return root;
    }
}
```

## 6. Time Complexity Analysis

### Binary Trees
- **Search**: O(n) - worst case
- **Insert**: O(n) - worst case
- **Delete**: O(n) - worst case
- **Traversal**: O(n)

### Binary Search Trees
- **Search**: O(log n) - average, O(n) - worst case
- **Insert**: O(log n) - average, O(n) - worst case
- **Delete**: O(log n) - average, O(n) - worst case
- **Traversal**: O(n)

### AVL Trees
- **Search**: O(log n)
- **Insert**: O(log n)
- **Delete**: O(log n)
- **Traversal**: O(n)

### Heaps
- **Insert**: O(log n)
- **Delete**: O(log n)
- **Get Max/Min**: O(1)
- **Build Heap**: O(n)
- **Heap Sort**: O(n log n)

## 7. Best Practices

1. **Choose the Right Tree**: Use BST for ordered data, AVL for balanced operations, Heap for priority queues
2. **Memory Management**: Be aware of memory usage for large trees
3. **Recursion**: Use recursion carefully to avoid stack overflow
4. **Balancing**: Consider self-balancing trees for dynamic data
5. **Traversal**: Choose appropriate traversal based on your needs

## 8. Practice Problems

1. **Binary Tree Problems**:
   - Find the maximum path sum
   - Check if tree is balanced
   - Find the lowest common ancestor
   - Serialize and deserialize binary tree

2. **BST Problems**:
   - Validate BST
   - Find kth smallest element
   - Convert sorted array to BST
   - Find successor and predecessor

3. **Heap Problems**:
   - Find k largest elements
   - Merge k sorted arrays
   - Find median in data stream
   - Top k frequent elements

4. **Advanced Problems**:
   - Red-Black tree implementation
   - B-tree operations
   - Trie data structure
   - Segment tree for range queries

Tree data structures are fundamental to computer science and are used extensively in databases, file systems, and many algorithms. 