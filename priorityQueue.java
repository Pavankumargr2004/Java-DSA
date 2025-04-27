import java.util.*;

public class priorityQueue{
    public static void main(String[] args) {
        //proirityqueue= a fifo data structure that orders elements based on their priority.
        // The element with the highest priority is dequeued first.
        Queue<Double>queue= new PriorityQueue<>(Collections.reverseOrder()); // Create a priority queue with reverse order (max heap)
        queue.offer(3.0);
        queue.offer(2.5);
        queue.offer(4.0);
        queue.offer(1.0);
        queue.offer(1.5);
        while(!queue.isEmpty()){
            System.out.println(queue.poll()); // Dequeue 
        }
    
    }       
   
}
//Strings also can be used in a priority queue, but they are compared lexicographically (dictionary order).