public class QuickSort {
	static int partition(int[] arr, int low, int high)
	{
		int pivot = arr[high];
		int i = (low - 1);

		for (int j = low; j <= high - 1; j++) {
			if (arr[j] < pivot) {
				i++;
				int temp = arr[i];
		        arr[i] = arr[j];
		        arr[j] = temp;
			}
		}
		int temp = arr[i+1];
		arr[i+1] = arr[high];
		arr[high] = temp;
        printArr(arr);
		return (i + 1);
	}

    static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length- 1);
    }
	static void quickSort(int[] arr, int low, int high)
	{
		if (low < high) {
			int pi = partition(arr, low, high);
			quickSort(arr, low, pi - 1);
			quickSort(arr, pi + 1, high);
		}
	}

    
    public static void printArr(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
    public static void main (String args[]) {
        int[] arr = { 2, 8, 3, 4576, 127,   1, 12 };
        System.out.print("Array : ");
        printArr(arr);
        System.out.println("Sorting array ascending by insertion sort :");
        quickSort(arr);
    }
}
