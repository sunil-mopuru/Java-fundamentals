package trees;

import java.util.*;

/**
 * Comprehensive examples of tree data structures in Java
 * Demonstrates binary trees, BST, AVL trees, and heaps with various operations
 */
public class TreeExamples {
    
    // TreeNode class for binary trees
    static class TreeNode {
        int data;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
    
    // AVLNode class for AVL trees
    static class AVLNode {
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
    
    public static void main(String[] args) {
        System.out.println("=== Tree Data Structures Examples ===\n");
        
        // Binary tree examples
        demonstrateBinaryTree();
        
        // Binary Search Tree examples
        demonstrateBinarySearchTree();
        
        // AVL tree examples
        demonstrateAVLTree();
        
        // Heap examples
        demonstrateHeaps();
        
        // Advanced tree operations
        demonstrateAdvancedOperations();
    }
    
    /**
     * Demonstrates binary tree operations
     */
    public static void demonstrateBinaryTree() {
        System.out.println("1. Binary Tree Operations:");
        
        BinaryTree tree = new BinaryTree();
        
        // Create a sample binary tree
        tree.root = new TreeNode(1);
        tree.root.left = new TreeNode(2);
        tree.root.right = new TreeNode(3);
        tree.root.left.left = new TreeNode(4);
        tree.root.left.right = new TreeNode(5);
        tree.root.right.left = new TreeNode(6);
        tree.root.right.right = new TreeNode(7);
        
        System.out.println("Binary Tree Structure:");
        System.out.println("       1");
        System.out.println("      / \\");
        System.out.println("     2   3");
        System.out.println("    / \\ / \\");
        System.out.println("   4  5 6  7");
        System.out.println();
        
        // Tree traversals
        System.out.println("Inorder Traversal (Left -> Root -> Right):");
        tree.inorderTraversal();
        
        System.out.println("Preorder Traversal (Root -> Left -> Right):");
        tree.preorderTraversal();
        
        System.out.println("Postorder Traversal (Left -> Right -> Root):");
        tree.postorderTraversal();
        
        System.out.println("Level Order Traversal (BFS):");
        tree.levelOrderTraversal();
        
        // Tree properties
        System.out.println("Tree Height: " + tree.getHeight());
        System.out.println("Number of Nodes: " + tree.countNodes());
        System.out.println("Is tree balanced: " + tree.isBalanced());
        
        // Search operations
        System.out.println("Search for 5: " + tree.search(5));
        System.out.println("Search for 10: " + tree.search(10));
        
        System.out.println();
    }
    
    /**
     * Demonstrates Binary Search Tree operations
     */
    public static void demonstrateBinarySearchTree() {
        System.out.println("2. Binary Search Tree Operations:");
        
        BinarySearchTree bst = new BinarySearchTree();
        
        // Insert elements
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        bst.insert(60);
        bst.insert(80);
        
        System.out.println("BST after insertion:");
        bst.inorderTraversal();
        
        // Search operations
        System.out.println("Search for 40: " + bst.search(40));
        System.out.println("Search for 90: " + bst.search(90));
        
        // Find min and max
        System.out.println("Minimum value: " + bst.findMin());
        System.out.println("Maximum value: " + bst.findMax());
        
        // Check if it's a valid BST
        System.out.println("Is valid BST: " + bst.isBST());
        
        // Find kth smallest element
        System.out.println("3rd smallest element: " + bst.kthSmallest(3));
        
        // Delete operations
        System.out.println("Deleting 30:");
        bst.delete(30);
        bst.inorderTraversal();
        
        System.out.println("Deleting 50 (root):");
        bst.delete(50);
        bst.inorderTraversal();
        
        System.out.println();
    }
    
    /**
     * Demonstrates AVL tree operations
     */
    public static void demonstrateAVLTree() {
        System.out.println("3. AVL Tree Operations:");
        
        AVLTree avl = new AVLTree();
        
        // Insert elements (this will trigger rotations)
        System.out.println("Inserting elements into AVL tree:");
        avl.insert(10);
        System.out.println("After inserting 10:");
        avl.inorderTraversal();
        
        avl.insert(20);
        System.out.println("After inserting 20:");
        avl.inorderTraversal();
        
        avl.insert(30);
        System.out.println("After inserting 30 (triggers rotation):");
        avl.inorderTraversal();
        
        avl.insert(40);
        System.out.println("After inserting 40:");
        avl.inorderTraversal();
        
        avl.insert(50);
        System.out.println("After inserting 50:");
        avl.inorderTraversal();
        
        avl.insert(25);
        System.out.println("After inserting 25:");
        avl.inorderTraversal();
        
        // Delete operations
        System.out.println("Deleting 30:");
        avl.delete(30);
        avl.inorderTraversal();
        
        System.out.println();
    }
    
    /**
     * Demonstrates heap operations
     */
    public static void demonstrateHeaps() {
        System.out.println("4. Heap Operations:");
        
        // Max Heap
        System.out.println("Max Heap:");
        MaxHeap maxHeap = new MaxHeap(10);
        
        maxHeap.insert(10);
        maxHeap.insert(20);
        maxHeap.insert(15);
        maxHeap.insert(30);
        maxHeap.insert(25);
        
        System.out.println("Max Heap after insertion:");
        maxHeap.printHeap();
        
        System.out.println("Maximum element: " + maxHeap.getMax());
        
        System.out.println("Extracting maximum elements:");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.extractMax() + " ");
        }
        System.out.println();
        
        // Min Heap
        System.out.println("\nMin Heap:");
        MinHeap minHeap = new MinHeap(10);
        
        minHeap.insert(30);
        minHeap.insert(20);
        minHeap.insert(25);
        minHeap.insert(10);
        minHeap.insert(15);
        
        System.out.println("Min Heap after insertion:");
        minHeap.printHeap();
        
        System.out.println("Minimum element: " + minHeap.getMin());
        
        System.out.println("Extracting minimum elements:");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.extractMin() + " ");
        }
        System.out.println();
        
        // Heap Sort
        System.out.println("\nHeap Sort:");
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original array: " + Arrays.toString(arr));
        
        MaxHeap heapSort = new MaxHeap(arr.length);
        heapSort.heapSort(arr);
        System.out.println("Sorted array: " + Arrays.toString(arr));
        
        System.out.println();
    }
    
    /**
     * Demonstrates advanced tree operations
     */
    public static void demonstrateAdvancedOperations() {
        System.out.println("5. Advanced Tree Operations:");
        
        // Expression Tree
        System.out.println("Expression Tree:");
        String postfix = "34+2*";
        ExpressionTree.Node exprRoot = ExpressionTree.buildExpressionTree(postfix);
        int result = ExpressionTree.evaluateExpressionTree(exprRoot);
        System.out.println("Postfix expression: " + postfix);
        System.out.println("Result: " + result);
        
        // Tree Serialization
        System.out.println("\nTree Serialization:");
        BinaryTree tree = new BinaryTree();
        tree.root = new TreeNode(1);
        tree.root.left = new TreeNode(2);
        tree.root.right = new TreeNode(3);
        tree.root.left.left = new TreeNode(4);
        tree.root.left.right = new TreeNode(5);
        
        String serialized = TreeSerialization.serialize(tree.root);
        System.out.println("Serialized tree: " + serialized);
        
        TreeNode deserialized = TreeSerialization.deserialize(serialized);
        System.out.println("Deserialized tree (inorder):");
        TreeSerialization.inorderTraversal(deserialized);
        System.out.println();
        
        // Find Lowest Common Ancestor
        System.out.println("Lowest Common Ancestor:");
        TreeNode lca = findLCA(tree.root, 4, 5);
        System.out.println("LCA of 4 and 5: " + (lca != null ? lca.data : "null"));
        
        lca = findLCA(tree.root, 4, 3);
        System.out.println("LCA of 4 and 3: " + (lca != null ? lca.data : "null"));
        
        System.out.println();
    }
    
    // ========== BINARY TREE IMPLEMENTATION ==========
    
    static class BinaryTree {
        TreeNode root;
        
        public BinaryTree() {
            this.root = null;
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
            if (root == null) return false;
            
            if (root.data == data) return true;
            
            return searchRec(root.left, data) || searchRec(root.right, data);
        }
        
        // Check if tree is balanced
        public boolean isBalanced() {
            return isBalancedRec(root) != -1;
        }
        
        private int isBalancedRec(TreeNode root) {
            if (root == null) return 0;
            
            int leftHeight = isBalancedRec(root.left);
            if (leftHeight == -1) return -1;
            
            int rightHeight = isBalancedRec(root.right);
            if (rightHeight == -1) return -1;
            
            if (Math.abs(leftHeight - rightHeight) > 1) return -1;
            
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }
    
    // ========== BINARY SEARCH TREE IMPLEMENTATION ==========
    
    static class BinarySearchTree {
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
    }
    
    // ========== AVL TREE IMPLEMENTATION ==========
    
    static class AVLTree {
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
    
    // ========== HEAP IMPLEMENTATIONS ==========
    
    static class MaxHeap {
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
    
    static class MinHeap {
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
        
        // Check if heap is empty
        public boolean isEmpty() {
            return size == 0;
        }
    }
    
    // ========== EXPRESSION TREE ==========
    
    static class ExpressionTree {
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
    
    // ========== TREE SERIALIZATION ==========
    
    static class TreeSerialization {
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
        
        public static void inorderTraversal(TreeNode root) {
            if (root != null) {
                inorderTraversal(root.left);
                System.out.print(root.data + " ");
                inorderTraversal(root.right);
            }
        }
    }
    
    // ========== UTILITY METHODS ==========
    
    /**
     * Find Lowest Common Ancestor (LCA) of two nodes
     */
    public static TreeNode findLCA(TreeNode root, int n1, int n2) {
        if (root == null) return null;
        
        if (root.data == n1 || root.data == n2) {
            return root;
        }
        
        TreeNode leftLCA = findLCA(root.left, n1, n2);
        TreeNode rightLCA = findLCA(root.right, n1, n2);
        
        if (leftLCA != null && rightLCA != null) {
            return root;
        }
        
        return (leftLCA != null) ? leftLCA : rightLCA;
    }
} 