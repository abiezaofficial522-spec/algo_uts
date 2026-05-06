
/**
 SortingDemo.java
 * Implementasi Bubble Sort dan Binary Search tanpa library built-in (manual array handling).
 Penjelasan, contoh penggunaan, dan optimasi disertakan.
 */

public class SortingDemo {

    // 1. BUBBLE SORT - Basic Implementation
    /**
     Bubble Sort dasar: Bandingkan adjacent elements, swap jika salah urut.
     Complexity: O(n^2) worst/average, O(n) best (already sorted).
     Penjelasan: Setiap pass, elemen terbesar "menggelembung" ke akhir.
     Contoh: Input [5,3,8,4,2] -> setelah pass1: [3,5,4,2,8], dst.
     */
    public static void bubbleSortBasic(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // 2. OPTIMASI BUBBLE SORT (Early Termination)
    /**
     * Optimasi: Gunakan flag swapped. Jika no swap di pass, array sudah sorted -> break early.
     * Improvement: Best case O(n) jika sudah sorted (vs O(n^2) basic).
     * Contoh penggunaan sama, tapi lebih efisien.
     */
    public static void bubbleSortOptimized(int[] arr) {
        int n = arr.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            // Optimasi: Jika tidak ada swap, sudah sorted
            if (!swapped) {
                break;
            }
        }
    }

    // 3. BINARY SEARCH (pada array yang sudah di-sort)
    /**
    Binary Search: Cari target di sorted array dengan divide-conquer.
    Complexity: O(log n), jauh lebih cepat dari linear O(n).
    Prekondisi: Array HARUS sudah di-sort.
    Penjelasan: Hitung mid, bandingkan target:
    - target < mid: cari kiri (high = mid-1)
    - target > mid: cari kanan (low = mid+1)
    - == : found
    Contoh: Sorted [1,3,5,7,9], target=5 -> mid=7 (index2), 5<7 -> low=0 high=1, mid=2 index1=3, 5>3 -> low=2 high=1 stop not found? Wait example 5: indices 0:1,1:3,2:5 -> mid=1 (3>5? No, adjust.
    Return index jika ditemukan, -1 jika tidak.
     */
    public static int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2; // Hindari overflow
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1; // Tidak ditemukan
    }

    // OPTIMASI BINARY SEARCH: Sudah optimal O(log n). Tambahan: Untuk multiple occurrences, bisa adjust cari leftmost/rightmost.

    public static void main(String[] args) {
        // Contoh Penggunaan
        int[] arr = {64, 34, 25, 12, 22, 11, 90};

        System.out.println("Array asli: ");
        printArray(arr);

        // 1. Basic Bubble Sort
        bubbleSortBasic(arr.clone()); // Clone agar tidak ubah original untuk demo
        System.out.println("\nSetelah Bubble Sort Basic: ");
        printArray(arr.clone());

        // Reset
        arr = new int[]{64, 34, 25, 12, 22, 11, 90};

        // 2. Optimized Bubble Sort
        bubbleSortOptimized(arr.clone());
        System.out.println("\nSetelah Bubble Sort Optimized (same result): ");
        printArray(arr.clone());

        // 3. Sort dulu, lalu Binary Search
        int[] sortedArr = arr.clone();
        bubbleSortOptimized(sortedArr);
        System.out.println("\nArray sorted untuk Binary Search: ");
        printArray(sortedArr);

        int target1 = 22;
        int target2 = 99;
        System.out.println("\nBinary Search untuk " + target1 + ": index " + binarySearch(sortedArr, target1));
        System.out.println("Binary Search untuk " + target2 + ": index " + binarySearch(sortedArr, target2));

        // Output contoh:
        // Array asli: [64, 34, 25, 12, 22, 11, 90]
        // Sorted: [11, 12, 22, 25, 34, 64, 90]
        // Binary 22: index 2
        // Binary 99: -1
    }

    // Helper print (manual, no lib dependency for sort/search)
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
