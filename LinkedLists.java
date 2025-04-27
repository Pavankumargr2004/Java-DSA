import java.util.*;
public class LinkedLists {
    public static void main(String[] args) {
        // Create a linked list
        LinkedList<String> linkedList = new LinkedList<>();

        // Add elements to the linked list
        linkedList.add("A");//we can use push() method to add elements in the linked list
        linkedList.add("B");//we can use offer() method to add elements in the linked list
        linkedList.add("C");
        linkedList.add("D");//we can use remove() or poll() method to remove        // Print the linked list
        linkedList.add( "E"); 
        linkedList.add(3, "G"); //to add an element at a specific index
        System.out.println("Linked List: " + linkedList);
        System.out.println(linkedList.indexOf("D"));
        // Add an element at the beginning
        System.out.println(linkedList.peekFirst());
        System.out.println(linkedList.peekLast());
        
        linkedList.addFirst("X");
        System.out.println("After adding X at the beginning: " + linkedList);
        
        // Add an element at the end
        linkedList.addLast("Y");
        System.out.println("After adding Y at the end: " + linkedList);

        // Remove the first element
        String removedFirst = linkedList.removeFirst();
        System.out.println("Removed first element: " + removedFirst);
        System.out.println("After removing first element: " + linkedList);

        // Remove the last element
        String removedLast = linkedList.removeLast();
        System.out.println("Removed last element: " + removedLast);
        System.out.println("After removing last element: " + linkedList);
    }
    
}
