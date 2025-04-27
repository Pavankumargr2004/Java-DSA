import java.util.Stack;
public class stacks{
    public static void main(String[] args) {
        // Create a stack=LIFO PUSH() AND POP()METHOD
        Stack<Integer> stack = new Stack<>();

        // Push elements onto the stack
        stack.push(1);
        stack.push(2);
        stack.push(3);
System.out.println(stack);      // Peek at the top element
        System.out.println("Top element: " + stack.peek());
        System.out.println(stack.search(1)); 
        // Pop an element from the stack
        System.out.println("Popped element: " + stack.pop());

        // Check if the stack is empty
        System.out.println("Is the stack empty? " + stack.isEmpty());
    }

}