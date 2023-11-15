public class recurFindMax {
    public static int findMax(int[] a, int n) {
        if (n == 0) {
            return a[0];
        }
        else {
            if (findMax(a, n- 1) > a[n -1]) {
                return findMax(a, n-1);
            }
            else {
                return a[n - 1];
            }
        }
    }

    public static void main (String args[]) {
        int[] a = {1,2,3,4,5,1,23,64,1,2,3};
        System.out.println(findMax(a, a.length - 1));
    }
}
