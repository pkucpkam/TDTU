public class Exercise3 {
    static boolean isPrime(int n, int i)
    {
        if (n <= 2)
            return (n == 2);
        if (n % i == 0)
            return false;
        if (i * i > n)
            return true;
        return isPrime(n, i + 1);
    }
    public static void main(String args[]) {
        System.out.println("check with n = 2 : " + isPrime(2, 3));
        System.out.println("check with n = 15 : " + isPrime(15, 5));
    }
}
