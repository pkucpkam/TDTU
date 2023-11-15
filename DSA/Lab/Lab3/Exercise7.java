public class Exercise7 {
    public static int findMin(int[] a, int n) {
        if (n == 1) {
            return a[0]; 
        }
        else {
            return Math.min(a[n-1],findMin(a, n-1));
        }
    }

    public static int findMax(int[] a, int n) {
        if (n == 1) {
            return a[0]; 
        }
        else {
            return Math.max(a[n-1],findMax(a, n-1));
        }
    }

    public static int countEven(int[] a, int n) {
        if (n == 1) {
            if (a[0] % 2 == 0) {
                return 1;
            }
            else return 0;
        }
        else {
            if (a[n-1] % 2 == 0) {
                return 1 + countEven(a, n-1);
            }
            else {
                return 0 + countEven(a, n-1);
            }
        }
    }

    public static void main(String args[]) {
        int[] a = {1,234,6,12,6,2,3};
        System.out.println("Test find minimum : " + findMin(a, 7));
        System.out.println("Test find maximum : " + findMax(a, 7));
        System.out.println("Test count even element : " + countEven(a, 7));
    }
}
