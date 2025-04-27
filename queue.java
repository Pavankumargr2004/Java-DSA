import java.util. *;
public class queue {
    public static void main(String[] args) {
        // Create a queue=FIFO ENQUEUE() AND DEQUEUE()METHOD
        Queue<Integer> queue = new LinkedList<>();

        // Enqueue elements into the queue 
        // for enqueue in java we use  offer()
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.offer(4);
        System.out.println(queue); 
        System.out.println(queue.poll());//foe dqqueue in java we use poll() method

        // Peek at the front element
        System.out.println("Front element: " + queue.peek());

        // Dequeue an element from the queue or poll()
        System.out.println("Dequeued element: " + queue.poll());

        // Check if the queue is empty
        System.out.println("Is the queue empty? " + queue.isEmpty());
    
}
}