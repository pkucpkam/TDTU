public class Exercise4 {
    public static int computeA(int n) {
        if (n == 1) {
            return 1;
        }
        else {
            return (2*n + 1) + computeA(n-1);
        }
    }

    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        }
        else {
            return factorial(n-1)*n;
        }
    }
    public static int computeB(int n) {
        if (n == 1) {
            return 1;
        }
        else {
            return factorial(n) + factorial(n-1);
        }
    }

    public static int computeC(int n ){
        if (n == 1) {
            return 1;
        }
        else {
            return factorial(n)*factorial(n-1);
        }
    }

    public static int computeD(int n,int k) {
        if ( k <= n && k > 0) {
            return n*computeD(n-1, k);
        }
        else {
            return 1;
        }
    }

    public static int compute(int x,int n) {
        if (n == 1) {
            return x;
        }
        else {
            return x*compute(x,n-1);
        }
    }

    public static int computeE(int n) {
        if (n == 1) {
            return 3;
        }
        else {
            return (int)Math.pow(2,n) + (int)Math.pow(n, 2) + computeE(n-1);
        }
    }
    public static void main(String args[]) {
        System.out.println("Test a with n = 10, result = " + computeA(10));
        System.out.println("Test b with n = 5, result = " + computeB(5));
        System.out.println("Test c with n = 5, result = " + computeC(5));
        System.out.println("Test d with n = 5, result = " + computeD(5,3));
        System.out.println("Test e with n = 5, result = " + computeE(5));
    }
}
