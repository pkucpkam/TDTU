public class MergeSort {
    public static void mergeSort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    public static void mergeSort(int[] arr, int head, int tail) {
        if (head < tail) {
            int mid = (head + tail) / 2;
            mergeSort(arr, head, mid);
            mergeSort(arr, mid + 1, tail);
            merge(arr, head, mid, tail);
        }
    }

    public static void merge(int[] arr, int head, int mid, int tail) {
        int[] temp = new int[tail - head + 1];
        int i = 0;
        int left = head, right = mid + 1;
        while (left <= mid && right <= tail) {
            if (arr[left] < arr[right]) {
                temp[i] = arr[left];
                left++;
                i++;
            } else {
                temp[i] = arr[right];
                right++;
                i++;
            }
        }

        while (left <= mid) {
            temp[i] = arr[left];
            left++;
            i++;
        }

        while (right <= tail) {
            temp[i] = arr[right];
            right++;
            i++;
        }

        for (i = 0; i < temp.length; i++) {
            arr[head + i] = temp[i];
        }
    }

    public static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String args[]) {
        int[] arr = { 2, 8, 3, 4576, 127, 1, 12 };
        System.out.print("Array : ");
        printArr(arr);
        System.out.println("Sorting array by mergsort :");
        mergeSort(arr);
        printArr(arr);
    }
}
