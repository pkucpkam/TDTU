public class Test {
    public static void main(String args[]) {
        Integer y = new Integer (20);
        Integer w;
        w = new Integer (20);
        if (w == y)
            System.out.println("1. w == y");
        w = y;
        if (w == y)
        System.out.println("2. w == y");
    }
}
