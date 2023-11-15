public class Exercise6 {
    public static int findMin(int[] a, int n) {
        int min = a[0];
        for (int i = 1; i < n; i++) {
            if (min > a[i]) {
                min = a[i];
            }
        }
        return min;
    }

    public static int findMax(int[] a, int n) {
        int max = a[0];
        for (int i = 1; i < n; i++) {
            if (max < a[i]) {
                max = a[i];
            }
        }
        return max;
    }

    public static int countEven(int[] a, int n) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] % 2 == 0) {
                count++;
            }
        }
        return count;
    }

    public static void main(String args[]) {
        int[] a = {1,234,6,12,6,2,3};
        System.out.println("Test find minimum : " + findMin(a, 7));
        System.out.println("Test find maximum : " + findMax(a, 7));
        System.out.println("Test count even element : " + countEven(a, 7));
    }
}
