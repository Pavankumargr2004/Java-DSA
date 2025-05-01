public class linearsearch {
    public static void main(String[] args) {
        
    int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // Array to search in
    int index=linear_search(arr, 5);
    System.out.println("Element found at index: " + index); 
     // Output the index of the found element

    
}
private static int linear_search(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target) {
            return i; // Return the index of the target element
            
        }
   
    }
    return -1; // Return -1 if the target element is not found
}
}
