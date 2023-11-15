public class Exercise5 {
    public static int convert2Bin(int n) {
        if (n == 0) {
            return 0;
        }
        else {
            return n%2 + 10*convert2Bin(n/2);
        }
    }
    public static void main(String args[]) {
        System.out.println("Test with n = 8, result = " + convert2Bin(8));
    }
}
