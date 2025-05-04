import java.util.*;

public class selection_sort {
    public static void main(String[] args) {
        int[] arr = {64, 25, 12, 22, 11}; // Array to be sorted
        selectionSort(arr); // Call the selection sort method
        System.out.println("Sorted array:"); // Output message
        for (int i : arr) { // Print the sorted array
            System.out.print(i + " "); // Print each element followed by a space
        }
    }

    public static void selectionSort(int[] arr) {
        int n = arr.length; // Get the length of the array
        for (int i = 0; i < n - 1; i++) { // Loop through each element in the array
            int minIndex = i; // Assume the minimum is the first element of the unsorted part
            for (int j = i + 1; j < n; j++) { // Loop through the unsorted part of the array
                if (arr[j] < arr[minIndex]) { // If a smaller element is found
                    minIndex = j; // Update the index of the minimum element
                }
            }
            // Swap the found minimum element with the first element of the unsorted part
            int temp = arr[minIndex]; // Store the minimum element in a temporary variable
            arr[minIndex] = arr[i]; // Place the minimum element at its correct position
            arr[i] = temp; // Place the stored minimum element in its new position
        }
    }
}
