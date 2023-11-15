public class Recur2d {

    public static double recur(int n) {
        if ( n == 1) {
            return 0;
        }
        else {
            return n*(n-1) + recur(n-1);
        }
    }

    public static double inte(int n) {
        double sum =  0;
        for (int i = 1; i<= n; i++) {
            sum += i*(i-1);
        }
        return sum;
    }
    public static void main (String args[]) {
        System.out.println(recur(5));
        System.out.println(inte(5));
    }
}
