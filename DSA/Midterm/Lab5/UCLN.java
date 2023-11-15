public class UCLN {
    public static int GDC(int a, int b) {
        if (b == 0) {
            return a;
        }
        else {
            return GDC(b, a % b);
        }
    }

    public static void main (String args[]) {
        System.out.println(GDC(5, 25));
    }
}
