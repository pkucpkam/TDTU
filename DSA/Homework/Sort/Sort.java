import java.util.*;

public class Sort {
    // selection sort
    public static void selectionSort(int[] a) {
        int n = a.length;

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;

            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[minIdx]) {
                    minIdx = j;
                }
            }

            int temp = a[minIdx];
            a[minIdx] = a[i];
            a[i] = temp;

            System.out.println(Arrays.toString(a));
        }
    }

    public static void selectionSort(int[] a, boolean reversed) {
        for (int i = a.length -1;  i >= 1;i-- ) {
            int index = i;
            for (int j = 0; j <= i; j++ ) {
                if (reversed) {
                    if (a[index] < a[j]) {
                        index = j;
                    }
                }
                else {
                    if (a[index] > a[j]) {
                        index = j;
                    }
                }
                
            }
            int temp = a[index];
            a[index] = a[i];
            a[i] = temp;
        }
    } 

    // bubble Sort
    public static void bubbleSort(int[] a) {
        int n = a.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j] < a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
            // System.out.println(Arrays.toString(a));
        }
    }

    public static void bubbleSort(int[] a, boolean reversed) {
        for (int i = 0 ; i < a.length ; i++) {
            for (int j = 0; j < a.length - i -1  ; j++) {
                if (reversed && (a[j] > a[j + 1])) {
                    int temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                }
                else if (reversed == false && a[j] < a[j+1]) {
                    int temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                }
            }
        }
    }

    // insertion Sort
    public static void insertionSort(int[] a) {
        int n = a.length;

        for (int i = 1; i < n; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
            System.out.println(Arrays.toString(a));
        }
    }

    public static void insertionSort(int[] a, boolean reversed) {
        for (int i = 1; i < a.length; i++) {
            int next = a[i];
            int j;
            if (reversed) {
                for ( j = i -1; j >= 0 && a[j] > next ; j--) {
                    a[j+1] = a[j];
                }
                a[j+1] = next;
            }
            else {
                for ( j = i -1; j >= 0 && a[j] < next ; j--) {
                    a[j+1] = a[j];
                }   
                a[j+1] = next;
            }
            
        }
    }
}