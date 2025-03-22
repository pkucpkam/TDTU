public abstract class Triangle {
    protected Point a, b, c;

    public Triangle(Point a, Point b) {
        this.a = a;
        this.b = b;
        this.c = new Point(0, 0);
    }

    public void draw() {
        System.out.println("Drawing Triangle...");
        draw2ndLine();
        drawLines();
    }

    void drawLines() {
        System.out.println("Drawing line from (" + a + ") to (" + b + ")");
        System.out.println("Drawing line from (" + b + ") to (" + c + ")");
        System.out.println("Drawing line from (" + c + ") to (" + a + ")");
    }

    protected abstract void draw2ndLine();
}
