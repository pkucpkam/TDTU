public class BubbleSort {
    public static void bubbleSortAsc(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
                printArr(arr);
            }
        }
    }

    public static void bubbleSortDesc(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] < arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
                printArr(arr);
            }
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
        System.out.println("Sorting ascending array : ");
        bubbleSortAsc(arr);
        System.out.println("Sorting descending array :");
        bubbleSortDesc(arr);

    }
}
