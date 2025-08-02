# Linear Data Structures in Java

## Overview
Linear data structures are collections of elements arranged in a sequential manner. Each element has a unique predecessor and successor (except the first and last elements). This guide covers the most important linear data structures: Linked Lists, Stacks, Queues, and Deques.

## 1. Linked Lists

### What are Linked Lists?
A linked list is a linear data structure where elements are stored in nodes, and each node contains data and a reference (link) to the next node in the sequence.

### Key Characteristics
- **Dynamic Size**: Can grow and shrink at runtime
- **Non-contiguous Memory**: Elements are not stored in adjacent memory locations
- **Sequential Access**: Must traverse from the beginning to access elements
- **Flexible Insertion/Deletion**: Can insert/delete at any position efficiently

### Types of Linked Lists

#### 1.1 Singly Linked List
Each node contains data and a reference to the next node.

```java
class Node {
    int data;
    Node next;
    
    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class SinglyLinkedList {
    Node head;
    
    // Insert at the end
    public void insertAtEnd(int data) {
        Node newNode = new Node(data);
        
        if (head == null) {
            head = newNode;
            return;
        }
        
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }
    
    // Insert at the beginning
    public void insertAtBeginning(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }
    
    // Delete a node with given data
    public void deleteNode(int data) {
        if (head == null) return;
        
        if (head.data == data) {
            head = head.next;
            return;
        }
        
        Node current = head;
        while (current.next != null && current.next.data != data) {
            current = current.next;
        }
        
        if (current.next != null) {
            current.next = current.next.next;
        }
    }
    
    // Print the list
    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}
```

#### 1.2 Doubly Linked List
Each node contains data and references to both the next and previous nodes.

```java
class DoublyNode {
    int data;
    DoublyNode next;
    DoublyNode prev;
    
    DoublyNode(int data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

class DoublyLinkedList {
    DoublyNode head;
    DoublyNode tail;
    
    // Insert at the end
    public void insertAtEnd(int data) {
        DoublyNode newNode = new DoublyNode(data);
        
        if (head == null) {
            head = tail = newNode;
            return;
        }
        
        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
    }
    
    // Insert at the beginning
    public void insertAtBeginning(int data) {
        DoublyNode newNode = new DoublyNode(data);
        
        if (head == null) {
            head = tail = newNode;
            return;
        }
        
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
    }
    
    // Delete a node with given data
    public void deleteNode(int data) {
        if (head == null) return;
        
        DoublyNode current = head;
        while (current != null && current.data != data) {
            current = current.next;
        }
        
        if (current == null) return;
        
        if (current == head) {
            head = head.next;
            if (head != null) head.prev = null;
            else tail = null;
        } else if (current == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
    }
    
    // Print forward
    public void printForward() {
        DoublyNode current = head;
        while (current != null) {
            System.out.print(current.data + " <-> ");
            current = current.next;
        }
        System.out.println("null");
    }
    
    // Print backward
    public void printBackward() {
        DoublyNode current = tail;
        while (current != null) {
            System.out.print(current.data + " <-> ");
            current = current.prev;
        }
        System.out.println("null");
    }
}
```

#### 1.3 Circular Linked List
The last node points back to the first node, creating a circular structure.

```java
class CircularLinkedList {
    Node head;
    
    // Insert at the end
    public void insertAtEnd(int data) {
        Node newNode = new Node(data);
        
        if (head == null) {
            head = newNode;
            newNode.next = head;
            return;
        }
        
        Node current = head;
        while (current.next != head) {
            current = current.next;
        }
        
        current.next = newNode;
        newNode.next = head;
    }
    
    // Print the circular list
    public void printList() {
        if (head == null) return;
        
        Node current = head;
        do {
            System.out.print(current.data + " -> ");
            current = current.next;
        } while (current != head);
        System.out.println("(back to head)");
    }
}
```

### Common Linked List Operations

#### Finding Length
```java
public int getLength() {
    int count = 0;
    Node current = head;
    while (current != null) {
        count++;
        current = current.next;
    }
    return count;
}
```

#### Finding Middle Element
```java
public Node findMiddle() {
    if (head == null) return null;
    
    Node slow = head;
    Node fast = head;
    
    while (fast.next != null && fast.next.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    
    return slow;
}
```

#### Reversing a Linked List
```java
public void reverse() {
    Node prev = null;
    Node current = head;
    Node next = null;
    
    while (current != null) {
        next = current.next;
        current.next = prev;
        prev = current;
        current = next;
    }
    
    head = prev;
}
```

#### Detecting Cycle
```java
public boolean hasCycle() {
    if (head == null) return false;
    
    Node slow = head;
    Node fast = head;
    
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        
        if (slow == fast) {
            return true;
        }
    }
    
    return false;
}
```

## 2. Stacks

### What are Stacks?
A stack is a linear data structure that follows the LIFO (Last In, First Out) principle. Elements can only be added or removed from the top of the stack.

### Key Characteristics
- **LIFO Principle**: Last element added is the first one removed
- **Single Access Point**: Only the top element is accessible
- **Dynamic Size**: Can grow and shrink as needed
- **Simple Operations**: Push, pop, peek, and isEmpty

### Stack Implementation

#### Using Arrays
```java
class ArrayStack {
    private int[] stack;
    private int top;
    private int capacity;
    
    public ArrayStack(int capacity) {
        this.capacity = capacity;
        this.stack = new int[capacity];
        this.top = -1;
    }
    
    // Push operation
    public void push(int data) {
        if (isFull()) {
            System.out.println("Stack is full!");
            return;
        }
        stack[++top] = data;
    }
    
    // Pop operation
    public int pop() {
        if (isEmpty()) {
            System.out.println("Stack is empty!");
            return -1;
        }
        return stack[top--];
    }
    
    // Peek operation
    public int peek() {
        if (isEmpty()) {
            System.out.println("Stack is empty!");
            return -1;
        }
        return stack[top];
    }
    
    // Check if stack is empty
    public boolean isEmpty() {
        return top == -1;
    }
    
    // Check if stack is full
    public boolean isFull() {
        return top == capacity - 1;
    }
    
    // Get size
    public int size() {
        return top + 1;
    }
    
    // Print stack
    public void printStack() {
        if (isEmpty()) {
            System.out.println("Stack is empty!");
            return;
        }
        
        System.out.print("Stack: ");
        for (int i = top; i >= 0; i--) {
            System.out.print(stack[i] + " ");
        }
        System.out.println();
    }
}
```

#### Using Linked Lists
```java
class LinkedListStack {
    private Node top;
    
    public LinkedListStack() {
        this.top = null;
    }
    
    // Push operation
    public void push(int data) {
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
    }
    
    // Pop operation
    public int pop() {
        if (isEmpty()) {
            System.out.println("Stack is empty!");
            return -1;
        }
        
        int data = top.data;
        top = top.next;
        return data;
    }
    
    // Peek operation
    public int peek() {
        if (isEmpty()) {
            System.out.println("Stack is empty!");
            return -1;
        }
        return top.data;
    }
    
    // Check if stack is empty
    public boolean isEmpty() {
        return top == null;
    }
    
    // Print stack
    public void printStack() {
        if (isEmpty()) {
            System.out.println("Stack is empty!");
            return;
        }
        
        System.out.print("Stack: ");
        Node current = top;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
}
```

### Stack Applications

#### 1. Expression Evaluation
```java
public class ExpressionEvaluator {
    public static int evaluatePostfix(String expression) {
        Stack<Integer> stack = new Stack<>();
        
        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c)) {
                stack.push(c - '0');
            } else {
                int b = stack.pop();
                int a = stack.pop();
                
                switch (c) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/': stack.push(a / b); break;
                }
            }
        }
        
        return stack.pop();
    }
    
    public static String infixToPostfix(String expression) {
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();
        
        for (char c : expression.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                result.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                }
            } else {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    result.append(stack.pop());
                }
                stack.push(c);
            }
        }
        
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        
        return result.toString();
    }
    
    private static int precedence(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }
}
```

#### 2. Balanced Parentheses
```java
public static boolean isBalanced(String expression) {
    Stack<Character> stack = new Stack<>();
    
    for (char c : expression.toCharArray()) {
        if (c == '(' || c == '{' || c == '[') {
            stack.push(c);
        } else if (c == ')' || c == '}' || c == ']') {
            if (stack.isEmpty()) return false;
            
            char top = stack.pop();
            if ((c == ')' && top != '(') ||
                (c == '}' && top != '{') ||
                (c == ']' && top != '[')) {
                return false;
            }
        }
    }
    
    return stack.isEmpty();
}
```

## 3. Queues

### What are Queues?
A queue is a linear data structure that follows the FIFO (First In, First Out) principle. Elements are added at the rear and removed from the front.

### Key Characteristics
- **FIFO Principle**: First element added is the first one removed
- **Two Access Points**: Front for removal, rear for addition
- **Dynamic Size**: Can grow and shrink as needed
- **Simple Operations**: Enqueue, dequeue, peek, and isEmpty

### Queue Implementation

#### Using Arrays
```java
class ArrayQueue {
    private int[] queue;
    private int front;
    private int rear;
    private int size;
    private int capacity;
    
    public ArrayQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new int[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }
    
    // Enqueue operation
    public void enqueue(int data) {
        if (isFull()) {
            System.out.println("Queue is full!");
            return;
        }
        
        rear = (rear + 1) % capacity;
        queue[rear] = data;
        size++;
    }
    
    // Dequeue operation
    public int dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty!");
            return -1;
        }
        
        int data = queue[front];
        front = (front + 1) % capacity;
        size--;
        return data;
    }
    
    // Peek operation
    public int peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty!");
            return -1;
        }
        return queue[front];
    }
    
    // Check if queue is empty
    public boolean isEmpty() {
        return size == 0;
    }
    
    // Check if queue is full
    public boolean isFull() {
        return size == capacity;
    }
    
    // Get size
    public int size() {
        return size;
    }
    
    // Print queue
    public void printQueue() {
        if (isEmpty()) {
            System.out.println("Queue is empty!");
            return;
        }
        
        System.out.print("Queue: ");
        int count = 0;
        int index = front;
        
        while (count < size) {
            System.out.print(queue[index] + " ");
            index = (index + 1) % capacity;
            count++;
        }
        System.out.println();
    }
}
```

#### Using Linked Lists
```java
class LinkedListQueue {
    private Node front;
    private Node rear;
    
    public LinkedListQueue() {
        this.front = null;
        this.rear = null;
    }
    
    // Enqueue operation
    public void enqueue(int data) {
        Node newNode = new Node(data);
        
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }
    
    // Dequeue operation
    public int dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty!");
            return -1;
        }
        
        int data = front.data;
        front = front.next;
        
        if (front == null) {
            rear = null;
        }
        
        return data;
    }
    
    // Peek operation
    public int peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty!");
            return -1;
        }
        return front.data;
    }
    
    // Check if queue is empty
    public boolean isEmpty() {
        return front == null;
    }
    
    // Print queue
    public void printQueue() {
        if (isEmpty()) {
            System.out.println("Queue is empty!");
            return;
        }
        
        System.out.print("Queue: ");
        Node current = front;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
}
```

### Queue Applications

#### 1. Breadth-First Search (BFS)
```java
public static void bfs(Graph graph, int start) {
    boolean[] visited = new boolean[graph.getVertices()];
    Queue<Integer> queue = new LinkedList<>();
    
    visited[start] = true;
    queue.add(start);
    
    while (!queue.isEmpty()) {
        int vertex = queue.poll();
        System.out.print(vertex + " ");
        
        for (int neighbor : graph.getAdjacencyList(vertex)) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                queue.add(neighbor);
            }
        }
    }
}
```

#### 2. Level Order Traversal (Binary Tree)
```java
public static void levelOrderTraversal(TreeNode root) {
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

## 4. Deques (Double-Ended Queues)

### What are Deques?
A deque is a linear data structure that allows insertion and deletion at both ends. It combines the features of both stacks and queues.

### Key Characteristics
- **Double-Ended**: Can add/remove from both front and rear
- **Flexible Operations**: Can be used as both stack and queue
- **Dynamic Size**: Can grow and shrink as needed
- **Multiple Operations**: Add/remove from front or rear

### Deque Implementation

#### Using Linked Lists
```java
class Deque {
    private DoublyNode front;
    private DoublyNode rear;
    
    public Deque() {
        this.front = null;
        this.rear = null;
    }
    
    // Add at front
    public void addFront(int data) {
        DoublyNode newNode = new DoublyNode(data);
        
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            newNode.next = front;
            front.prev = newNode;
            front = newNode;
        }
    }
    
    // Add at rear
    public void addRear(int data) {
        DoublyNode newNode = new DoublyNode(data);
        
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            newNode.prev = rear;
            rear.next = newNode;
            rear = newNode;
        }
    }
    
    // Remove from front
    public int removeFront() {
        if (isEmpty()) {
            System.out.println("Deque is empty!");
            return -1;
        }
        
        int data = front.data;
        front = front.next;
        
        if (front == null) {
            rear = null;
        } else {
            front.prev = null;
        }
        
        return data;
    }
    
    // Remove from rear
    public int removeRear() {
        if (isEmpty()) {
            System.out.println("Deque is empty!");
            return -1;
        }
        
        int data = rear.data;
        rear = rear.prev;
        
        if (rear == null) {
            front = null;
        } else {
            rear.next = null;
        }
        
        return data;
    }
    
    // Peek front
    public int peekFront() {
        if (isEmpty()) {
            System.out.println("Deque is empty!");
            return -1;
        }
        return front.data;
    }
    
    // Peek rear
    public int peekRear() {
        if (isEmpty()) {
            System.out.println("Deque is empty!");
            return -1;
        }
        return rear.data;
    }
    
    // Check if deque is empty
    public boolean isEmpty() {
        return front == null;
    }
    
    // Print deque
    public void printDeque() {
        if (isEmpty()) {
            System.out.println("Deque is empty!");
            return;
        }
        
        System.out.print("Deque: ");
        DoublyNode current = front;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
}
```

### Deque Applications

#### 1. Sliding Window Maximum
```java
public static int[] maxSlidingWindow(int[] nums, int k) {
    if (nums == null || nums.length == 0) return new int[0];
    
    int n = nums.length;
    int[] result = new int[n - k + 1];
    Deque<Integer> deque = new LinkedList<>();
    
    for (int i = 0; i < n; i++) {
        // Remove elements outside the window
        while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
            deque.pollFirst();
        }
        
        // Remove smaller elements from the back
        while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
            deque.pollLast();
        }
        
        deque.addLast(i);
        
        // Add maximum to result
        if (i >= k - 1) {
            result[i - k + 1] = nums[deque.peekFirst()];
        }
    }
    
    return result;
}
```

## 5. Time Complexity Analysis

### Linked Lists
- **Access**: O(n)
- **Search**: O(n)
- **Insertion**: O(1) at beginning, O(n) at end
- **Deletion**: O(1) at beginning, O(n) at end
- **Space**: O(n)

### Stacks
- **Push**: O(1)
- **Pop**: O(1)
- **Peek**: O(1)
- **Search**: O(n)
- **Space**: O(n)

### Queues
- **Enqueue**: O(1)
- **Dequeue**: O(1)
- **Peek**: O(1)
- **Search**: O(n)
- **Space**: O(n)

### Deques
- **Add/Remove at Front**: O(1)
- **Add/Remove at Rear**: O(1)
- **Peek**: O(1)
- **Search**: O(n)
- **Space**: O(n)

## 6. Best Practices

1. **Choose the Right Structure**: Use stacks for LIFO operations, queues for FIFO operations
2. **Memory Management**: Be aware of memory usage, especially with linked lists
3. **Error Handling**: Always check for empty/full conditions
4. **Performance**: Consider time complexity for your specific use case
5. **Built-in Classes**: Use Java's built-in `Stack`, `Queue`, and `Deque` implementations when possible

## 7. Practice Problems

1. **Linked List Problems**:
   - Reverse a linked list in groups of k
   - Detect and remove loop in linked list
   - Merge two sorted linked lists
   - Find intersection point of two linked lists

2. **Stack Problems**:
   - Evaluate postfix expressions
   - Convert infix to postfix
   - Check balanced parentheses
   - Next greater element

3. **Queue Problems**:
   - Implement stack using queues
   - Implement queue using stacks
   - First non-repeating character in stream
   - Sliding window maximum

4. **Deque Problems**:
   - Maximum of all subarrays of size k
   - Palindrome checker
   - Implement stack and queue using deque

These linear data structures form the foundation for many algorithms and are essential for understanding more complex data structures. 