public class Recur2a {
    public static int recur(int n) {
        if ( n == 1) {
            return 2;
        }
        else {
            return (int) Math.pow(2,n) + recur(n-1);
        }
    }

    public static int inte(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += (int) Math.pow(2, i);
        }
        return sum;
    }

    public static void main(String args[]) {
        System.out.println(recur(5));
        System.out.println(inte(5));
    }
}
