public class Exercise2 {

    public static int factorial(int n) {
        if (n == 0) {
            return 1;
        }
        else {
            return n*factorial(n-1);
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

    public static int countNum(int n) {
        if (n == 0) {
            return 0;
        }
        else {
            return 1 + countNum(n/10);
        }
    }
    public static int GCD(int n1, int n2) {
        if (n2 == 0) {
            return n1;
        }
        else {
            return GCD(n2, n1%n2);
        }
    }
    public static void main(String args[]) {
        System.out.println("Test compute factorial with n = 5, result = " + factorial(5));
        System.out.println("Test compute x^n with x = 5, n = 2, result = " + compute(5, 2));
        System.out.println("Test Count the number of digits of a positive integer number : " + countNum(12345));
        System.out.println("Test find GCD with 4 and 16, result = " + GCD(5, 15));
    }
}