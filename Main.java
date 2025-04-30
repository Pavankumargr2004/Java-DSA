import java.util.*;
public class Main {
    public static void main(String[] args) {
        // Create an instance of the dynamic array class
        DynamicArray dynamicArray = new DynamicArray();
        dynamicArray.add("A");
        dynamicArray.add("B");
        dynamicArray.add("G");

        dynamicArray.insert(1, "C"); 
        dynamicArray.delete("B"); 
        System.out.println(dynamicArray.search("A"));
        System.out.println(dynamicArray);
        System.out.println("size:" + dynamicArray.size());
        System.out.println("capacity:" + dynamicArray.capacity());
        System.out.println("isEmpty:" + dynamicArray.isEmpty());

        
        // Add some elements to the dynamic array
    }
}

class DynamicArray {
    private ArrayList<String> array;

    public DynamicArray() {
        array = new ArrayList<>();
    }

    public void add(String element) {
        array.add(element);
    }

    public int size() {
        return array.size();
    }

    public int capacity() {
        return array.size() + 10; // Example capacity logic
    }

    public boolean isEmpty() {
        return array.isEmpty();
    }
    public int insert(int index, String element) {
        if (index < 0 || index > array.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        array.add(index, element);
        return index;
    }
    public void delete(String element) {
        array.remove(element);
    }
    public int search(String element) {
        return array.indexOf(element);
    }

    @Override
    public String toString() {
        return array.toString();
    }
}
