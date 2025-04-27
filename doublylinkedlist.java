import java.util.*;
public class doublylinkedlist {
    public static void main(String[] args) {
        // Create a doubly linked list
        LinkedList<String> doublyLinkedList = new LinkedList<>();

        // Add elements to the doubly linked list
        doublyLinkedList.add("A");
        doublyLinkedList.add("B");
        doublyLinkedList.add("C");
        doublyLinkedList.add("D");

        // Print the doubly linked list
        System.out.println("Doubly Linked List: " + doublyLinkedList);

        // Add an element at the beginning
        doublyLinkedList.addFirst("X");
        System.out.println("After adding X at the beginning: " + doublyLinkedList);

        // Add an element at the end
        doublyLinkedList.addLast("Y");
        System.out.println("After adding Y at the end: " + doublyLinkedList);

        // Remove the first element
        String removedFirst = doublyLinkedList.removeFirst();
        System.out.println("Removed first element: " + removedFirst);
        System.out.println("After removing first element: " + doublyLinkedList);

        // Remove the last element
        String removedLast = doublyLinkedList.removeLast();
        System.out.println("Removed last element: " + removedLast);
        System.out.println("After removing last element: " + doublyLinkedList);
        
    }
}
