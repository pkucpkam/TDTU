public class StdTriangle extends Triangle {
    public StdTriangle(Point a, Point b) {
        super(a, b);
    }

    @Override
    protected void draw2ndLine() {
        double midX = (a.getX() + b.getX()) / 2;
        double midY = (a.getY() + b.getY()) / 2 + 5;
        c.setX(midX);
        c.setY(midY);
    }
}
