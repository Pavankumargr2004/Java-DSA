public class interpolationsearch {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // Array to search in
        int target = 5; // Element to search for
        int index = interpolation_search(arr, target); // Call the interpolation search method
        if (index == -1) {
            System.out.println("Element not found in the array"); // Output if not found
        } else {
            System.out.println("Element found at index: " + index); // Output the index of the found element
        }
    }

    private static int interpolation_search(int[] arr, int target) {
        int low = 0; // Left pointer
        int high = arr.length - 1; // Right pointer

        while (low <= high && target >= arr[low] && target <= arr[high]) {
            if (low == high) {
                if (arr[low] == target) {
                    return low; // Return the index of the target element
                }
                return -1; // Return -1 if the target element is not found
            }

            // Calculate the position using interpolation formula
            int pos = low + ((target - arr[low]) * (high - low)) / (arr[high] - arr[low]);

            if (arr[pos] == target) {
                return pos; // Return the index of the target element
            } else if (arr[pos] < target) {
                low = pos + 1; // Move the left pointer to the right
            } else {
                high = pos - 1; // Move the right pointer to the left
            }
        }
        return -1; // Return -1 if the target element is not found
    }
    
}
