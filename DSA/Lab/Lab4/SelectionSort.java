public class SelectionSort {
	public static void selectionSortAsc(int[] arr) {
		for (int i = arr.length - 1; i > 0; i--) {
			int index = i;
			for (int j = 0; j < i; j++) {
				if (arr[j] > arr[index]) {
					index = j;
				}
			}
			int tmp = arr[index];
			arr[index] = arr[i];
			arr[i] = tmp;
			printArr(arr);
		}
	}

	public static void selectionSortDesc(int arr[]) {
		for (int i = arr.length - 1; i > 0; i--) {
			int index = i;
			for (int j = 0; j < i; j++) {
				if (arr[j] < arr[index]) {
					index = j;
				}
			}
			int tmp = arr[i];
			arr[i] = arr[index];
			arr[index] = tmp;
			printArr(arr);
		}
	}

	public static void printArr(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String args[]) {
		int[] arr = { 2, 8, 3, 4576, 127, 0, 12 };
		System.out.print("Array 1 : ");
		printArr(arr);
		System.out.println("Selection sort ascending array 1:");
		selectionSortAsc(arr);

		System.out.println();
		;
		int[] arr2 = { 34, 23, 57, 23, 89, 1 };
		System.out.print("Array 2 : ");
		printArr(arr2);
		System.out.println("Selection sort descending array 2:");
		selectionSortDesc(arr2);

	}
}
