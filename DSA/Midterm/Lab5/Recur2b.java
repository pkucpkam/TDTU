public class Recur2b {
    public static double recur(int n) {
        if (n == 0) {
            return 1/2;
        }
        else {
            return ((n + 1)/2) + recur(n-1);
        }
    }

    public static double inte(int n) {
        double sum = 0;
        for (int i =0 ;i <= n; i++) {
            sum += ((i+1)/2);
        }
        return sum;
    }

    public static void main (String args[]) {
        System.out.println(recur(5));
        System.out.println(inte(5));
    }
}
