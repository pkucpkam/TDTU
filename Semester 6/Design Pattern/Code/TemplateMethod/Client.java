public class Client {
    public static void main(String[] args) {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(10, 0);

        System.out.println("=== Standard Triangle ===");
        Triangle stdTriangle = new StdTriangle(p1, p2);
        stdTriangle.draw();

        System.out.println("\n=== Isosceles Triangle ===");
        Triangle isoTriangle = new IsoscelesTriangle(p1, p2);
        isoTriangle.draw();

        System.out.println("\n=== Right Triangle ===");
        Triangle rightTriangle = new RightTriangle(p1, p2);
        rightTriangle.draw();
    }
}
