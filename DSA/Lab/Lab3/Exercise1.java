public class Exercise1 {
    public static int factorial(int n) {
        int s = 0;
        for (int i = 1; i <= n;i++) {
            s *= i;
        }
        return s;
    }
    public static int compute(int n,int x) {
        int s = 0;
        for (int i = 1; i <= x; i++) {
            s *= n;
        }
        return s;
    }
    public static int countNum(int n) {
        int count = 0;
        while (n != 0) {
            n = n/10;
            count += 1;
        }
        return count;
    }
    public static int GCD(int n1, int n2) {
        int temp;
        while(n1!= 0) {
            temp = n2 % n1;
            n2= n1;
            n1= temp;
        }
        return n2;
    }
    public static void main(String args[]) {
        System.out.println("Test compute factorial with n = 5, result = " + factorial(5));
        System.out.println("Test compute x^n with x = 5, n = 2, result = " + compute(5, 2));
        System.out.println("Test Count the number of digits of a positive integer number : " + countNum(12345));
        System.out.println("Test find GCD with 4 and 16, result = " + GCD(5, 15));
    }
}
