import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int n = 5;
        int[] a = new int[n];

        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            a[i] = rand.nextInt() % 100 + 100;
        }
        // int[] a = {5, 1, 3, 2, 4};

        System.out.println(Arrays.toString(a));
        Sort.insertionSort(a,true);
        System.out.println(Arrays.toString(a));
        Sort.insertionSort(a, false);
        System.out.println(Arrays.toString(a));
    }
}
