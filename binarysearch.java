import java.util.*;
public class binarysearch {
    public static void main(String[] args) {
        int[] arr = new int[100];
        int target = 50; 
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1; // Fill the array with numbers from 1 to 100
        }
        int index = binarysearch(arr, target); 
        // Call the binary search method
        if(index==-1){
            System.out.println("Element not found in the array"); // Output

        }
        else{
            System.out.println("Element found at index: " + index); // Output the index of the found element
        }
    }
    private static int binarysearch(int[] arr, int target) {
        int left = 0; // Left pointer
        int right = arr.length - 1; // Right pointer

        while (left <= right) {
            int mid = left + (right - left) / 2; // Calculate the middle index

            if (arr[mid] == target) {
                return mid; // Return the index of the target element
            } else if (arr[mid] < target) {
                left = mid + 1; // Move the left pointer to the right
            } else {
                right = mid - 1; // Move the right pointer to the left
            }
        }
        return -1; // Return -1 if the target element is not found
    }

    
}
