public class InsertionSort {
    public static void insertionSortAsc(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];
                j = j - 1;
            }
            a[j + 1] = key;
            printArray(a);
        }
    }

    public static void insertionSortDesc(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] < key) {
                a[j + 1] = a[j];
                j = j - 1;
            }
            a[j + 1] = key;
            printArray(a);
        }
    }

    public static void printArray(int[] a) {
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + " ");
		System.out.println();
	}

	public static void main(String[] args) {
		int[] arr = { 5,1,2,6,4,3};

		System.out.print("Sort arr : ");
		printArray(arr);
		System.out.println("Sorting array ascending by insertion sort :");
		insertionSortAsc(arr);
		System.out.println("Sorting array descending by insertion sort :");
		insertionSortDesc(arr);
	}   
}
