public class Ex3 {
    public static double a(int n) {
        if (n == 0) {
            return 2;
        }
        else {
            return 2 - 0.5*a(n-1);
        }
    }

    public static double b(int n) {
        if (n < 10) {
            return 1;
        }
        else {
            return 1 + a(n/10);
        }
    }

    public static int c(int n, int k) {
        if (k==1) {
            return n;
        }
        else {
            return n + c(n, k-1);
        }
    }
    public static void main (String args[]) {

    }
}
