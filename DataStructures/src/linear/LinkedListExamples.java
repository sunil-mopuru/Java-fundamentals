package linear;

/**
 * Comprehensive examples of linked list operations in Java
 * Demonstrates singly, doubly, and circular linked lists with various operations
 */
public class LinkedListExamples {
    
    // Node class for singly linked list
    static class Node {
        int data;
        Node next;
        
        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
    
    // Node class for doubly linked list
    static class DoublyNode {
        int data;
        DoublyNode next;
        DoublyNode prev;
        
        DoublyNode(int data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Linked List Examples ===\n");
        
        // Singly linked list examples
        demonstrateSinglyLinkedList();
        
        // Doubly linked list examples
        demonstrateDoublyLinkedList();
        
        // Circular linked list examples
        demonstrateCircularLinkedList();
        
        // Advanced linked list operations
        demonstrateAdvancedOperations();
    }
    
    /**
     * Demonstrates singly linked list operations
     */
    public static void demonstrateSinglyLinkedList() {
        System.out.println("1. Singly Linked List Operations:");
        
        SinglyLinkedList list = new SinglyLinkedList();
        
        // Insert elements
        list.insertAtEnd(10);
        list.insertAtEnd(20);
        list.insertAtEnd(30);
        list.insertAtEnd(40);
        list.insertAtEnd(50);
        
        System.out.println("Original list:");
        list.printList();
        
        // Insert at beginning
        list.insertAtBeginning(5);
        System.out.println("After inserting 5 at beginning:");
        list.printList();
        
        // Insert at specific position
        list.insertAtPosition(25, 3);
        System.out.println("After inserting 25 at position 3:");
        list.printList();
        
        // Delete operations
        list.deleteNode(25);
        System.out.println("After deleting 25:");
        list.printList();
        
        list.deleteAtPosition(2);
        System.out.println("After deleting at position 2:");
        list.printList();
        
        // Find operations
        System.out.println("Length of list: " + list.getLength());
        System.out.println("Middle element: " + list.findMiddle().data);
        System.out.println("Element 30 found at position: " + list.search(30));
        
        // Reverse the list
        list.reverse();
        System.out.println("After reversing:");
        list.printList();
        
        System.out.println();
    }
    
    /**
     * Demonstrates doubly linked list operations
     */
    public static void demonstrateDoublyLinkedList() {
        System.out.println("2. Doubly Linked List Operations:");
        
        DoublyLinkedList list = new DoublyLinkedList();
        
        // Insert elements
        list.insertAtEnd(10);
        list.insertAtEnd(20);
        list.insertAtEnd(30);
        list.insertAtEnd(40);
        
        System.out.println("Original list (forward):");
        list.printForward();
        
        System.out.println("Original list (backward):");
        list.printBackward();
        
        // Insert at beginning
        list.insertAtBeginning(5);
        System.out.println("After inserting 5 at beginning:");
        list.printForward();
        
        // Delete operations
        list.deleteNode(20);
        System.out.println("After deleting 20:");
        list.printForward();
        
        // Find operations
        System.out.println("Length of list: " + list.getLength());
        System.out.println("Element 30 found: " + list.search(30));
        
        System.out.println();
    }
    
    /**
     * Demonstrates circular linked list operations
     */
    public static void demonstrateCircularLinkedList() {
        System.out.println("3. Circular Linked List Operations:");
        
        CircularLinkedList list = new CircularLinkedList();
        
        // Insert elements
        list.insertAtEnd(10);
        list.insertAtEnd(20);
        list.insertAtEnd(30);
        list.insertAtEnd(40);
        
        System.out.println("Original circular list:");
        list.printList();
        
        // Insert at beginning
        list.insertAtBeginning(5);
        System.out.println("After inserting 5 at beginning:");
        list.printList();
        
        // Delete operations
        list.deleteNode(20);
        System.out.println("After deleting 20:");
        list.printList();
        
        // Find operations
        System.out.println("Length of list: " + list.getLength());
        System.out.println("Element 30 found: " + list.search(30));
        
        System.out.println();
    }
    
    /**
     * Demonstrates advanced linked list operations
     */
    public static void demonstrateAdvancedOperations() {
        System.out.println("4. Advanced Linked List Operations:");
        
        // Detect cycle
        SinglyLinkedList list1 = new SinglyLinkedList();
        list1.insertAtEnd(1);
        list1.insertAtEnd(2);
        list1.insertAtEnd(3);
        list1.insertAtEnd(4);
        
        // Create a cycle
        Node head = list1.head;
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = head.next; // Create cycle
        
        System.out.println("List with cycle - Has cycle: " + list1.hasCycle());
        
        // Remove cycle
        list1.removeCycle();
        System.out.println("After removing cycle - Has cycle: " + list1.hasCycle());
        
        // Merge sorted lists
        SinglyLinkedList list2 = new SinglyLinkedList();
        list2.insertAtEnd(1);
        list2.insertAtEnd(3);
        list2.insertAtEnd(5);
        
        SinglyLinkedList list3 = new SinglyLinkedList();
        list3.insertAtEnd(2);
        list3.insertAtEnd(4);
        list3.insertAtEnd(6);
        
        System.out.println("List 1:");
        list2.printList();
        System.out.println("List 2:");
        list3.printList();
        
        SinglyLinkedList merged = mergeSortedLists(list2, list3);
        System.out.println("Merged sorted list:");
        merged.printList();
        
        // Reverse in groups
        SinglyLinkedList list4 = new SinglyLinkedList();
        list4.insertAtEnd(1);
        list4.insertAtEnd(2);
        list4.insertAtEnd(3);
        list4.insertAtEnd(4);
        list4.insertAtEnd(5);
        list4.insertAtEnd(6);
        
        System.out.println("Original list:");
        list4.printList();
        
        list4.reverseInGroups(3);
        System.out.println("After reversing in groups of 3:");
        list4.printList();
        
        System.out.println();
    }
    
    // ========== SINGLY LINKED LIST IMPLEMENTATION ==========
    
    static class SinglyLinkedList {
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
        
        // Insert at specific position
        public void insertAtPosition(int data, int position) {
            if (position <= 0) {
                insertAtBeginning(data);
                return;
            }
            
            Node newNode = new Node(data);
            
            if (head == null) {
                head = newNode;
                return;
            }
            
            Node current = head;
            for (int i = 0; i < position - 1 && current.next != null; i++) {
                current = current.next;
            }
            
            newNode.next = current.next;
            current.next = newNode;
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
        
        // Delete at specific position
        public void deleteAtPosition(int position) {
            if (head == null || position < 0) return;
            
            if (position == 0) {
                head = head.next;
                return;
            }
            
            Node current = head;
            for (int i = 0; i < position - 1 && current.next != null; i++) {
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
        
        // Get length
        public int getLength() {
            int count = 0;
            Node current = head;
            while (current != null) {
                count++;
                current = current.next;
            }
            return count;
        }
        
        // Find middle element
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
        
        // Search for an element
        public int search(int data) {
            Node current = head;
            int position = 0;
            
            while (current != null) {
                if (current.data == data) {
                    return position;
                }
                current = current.next;
                position++;
            }
            
            return -1; // Not found
        }
        
        // Reverse the list
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
        
        // Detect cycle
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
        
        // Remove cycle
        public void removeCycle() {
            if (head == null) return;
            
            Node slow = head;
            Node fast = head;
            boolean hasCycle = false;
            
            // Detect cycle
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                
                if (slow == fast) {
                    hasCycle = true;
                    break;
                }
            }
            
            if (!hasCycle) return;
            
            // Find the start of cycle
            slow = head;
            while (slow != fast) {
                slow = slow.next;
                fast = fast.next;
            }
            
            // Remove cycle
            Node current = slow;
            while (current.next != slow) {
                current = current.next;
            }
            current.next = null;
        }
        
        // Reverse in groups
        public void reverseInGroups(int k) {
            head = reverseInGroupsUtil(head, k);
        }
        
        private Node reverseInGroupsUtil(Node head, int k) {
            if (head == null) return null;
            
            Node current = head;
            Node next = null;
            Node prev = null;
            int count = 0;
            
            // Reverse first k nodes
            while (count < k && current != null) {
                next = current.next;
                current.next = prev;
                prev = current;
                current = next;
                count++;
            }
            
            // Recursively reverse remaining nodes
            if (next != null) {
                head.next = reverseInGroupsUtil(next, k);
            }
            
            return prev;
        }
    }
    
    // ========== DOUBLY LINKED LIST IMPLEMENTATION ==========
    
    static class DoublyLinkedList {
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
        
        // Get length
        public int getLength() {
            int count = 0;
            DoublyNode current = head;
            while (current != null) {
                count++;
                current = current.next;
            }
            return count;
        }
        
        // Search for an element
        public boolean search(int data) {
            DoublyNode current = head;
            while (current != null) {
                if (current.data == data) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }
    }
    
    // ========== CIRCULAR LINKED LIST IMPLEMENTATION ==========
    
    static class CircularLinkedList {
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
        
        // Insert at the beginning
        public void insertAtBeginning(int data) {
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
            
            newNode.next = head;
            current.next = newNode;
            head = newNode;
        }
        
        // Delete a node with given data
        public void deleteNode(int data) {
            if (head == null) return;
            
            if (head.data == data) {
                if (head.next == head) {
                    head = null;
                } else {
                    Node current = head;
                    while (current.next != head) {
                        current = current.next;
                    }
                    current.next = head.next;
                    head = head.next;
                }
                return;
            }
            
            Node current = head;
            while (current.next != head && current.next.data != data) {
                current = current.next;
            }
            
            if (current.next != head) {
                current.next = current.next.next;
            }
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
        
        // Get length
        public int getLength() {
            if (head == null) return 0;
            
            int count = 0;
            Node current = head;
            do {
                count++;
                current = current.next;
            } while (current != head);
            
            return count;
        }
        
        // Search for an element
        public boolean search(int data) {
            if (head == null) return false;
            
            Node current = head;
            do {
                if (current.data == data) {
                    return true;
                }
                current = current.next;
            } while (current != head);
            
            return false;
        }
    }
    
    // ========== UTILITY METHODS ==========
    
    /**
     * Merge two sorted linked lists
     */
    public static SinglyLinkedList mergeSortedLists(SinglyLinkedList list1, SinglyLinkedList list2) {
        SinglyLinkedList merged = new SinglyLinkedList();
        
        Node current1 = list1.head;
        Node current2 = list2.head;
        
        while (current1 != null && current2 != null) {
            if (current1.data <= current2.data) {
                merged.insertAtEnd(current1.data);
                current1 = current1.next;
            } else {
                merged.insertAtEnd(current2.data);
                current2 = current2.next;
            }
        }
        
        // Add remaining elements
        while (current1 != null) {
            merged.insertAtEnd(current1.data);
            current1 = current1.next;
        }
        
        while (current2 != null) {
            merged.insertAtEnd(current2.data);
            current2 = current2.next;
        }
        
        return merged;
    }
} 