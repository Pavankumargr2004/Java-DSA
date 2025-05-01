import java.util.*;
public class linkedlist_vs_arraylist {
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<Integer>();// Create a linked list
        ArrayList<Integer>arraylist=new ArrayList<Integer>();
        long starttime,endtime ,elapsedtime;
        for(int i=0;i<100000;i++){
            linkedList.add(i);
            arraylist.add(i);
        }
        linkedList.remove(50000);
        starttime=System.nanoTime();
        linkedList.get(0);
        endtime=System.nanoTime();
        elapsedtime=endtime-starttime;
       System.out.println("linkedlist:\t"+elapsedtime+"ns");
       
       arraylist.remove(50000);
       starttime=System.nanoTime();
       arraylist.get(0);
        endtime=System.nanoTime();
        elapsedtime=endtime-starttime;
       System.out.println("arraylist:\t"+elapsedtime+"ns");
        
    

    }
}