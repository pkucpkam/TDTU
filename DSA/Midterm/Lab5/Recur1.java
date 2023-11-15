public class Recur1 {
    public static int product_Re(int a, int b) {
        if (b== 1) {
            return a;
        }
        else {
            return a*product_Re(a, b-1);
        }
    }

    public static int bin2dec(int n, int exp) {
        if (n == 0) {
            return 0;
        } else {
            int lastDigit = n % 10;
            return lastDigit * (int) Math.pow(2, exp) + bin2dec(n / 10, exp + 1);
        }
    }
    
    public static int maxDigit(int n) {
        if (n / 10 == 0) {
            return n;
        }
        else {
            return (int)Math.max(n%10, maxDigit(n/10));
        }
    }

    public static int maxElement(int arr[], int n) {
        if (n == 0) {
            return arr[0];
        }
        else {
            return Math.max(arr[n], maxElement(arr, n - 1));
        }
    }

    public static int search(int a[], int n, int key) {
        if (n == -1) {
            return -1;
        }
        if (a[n] == key) {
            return n;
        }
        else {
            return search(a, n - 1, key);
        }
    }
    public static void main(String args[]) {
        int[] arr = {1,6,2,8,3,567,235,4};
        System.out.println(search(arr, 7, 5));

    }
}
