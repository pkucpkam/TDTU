public class RightTriangle extends Triangle {
    public RightTriangle(Point a, Point b) {
        super(a, b);
    }

    @Override
    protected void draw2ndLine() {
        // Giả sử góc vuông nằm tại điểm a
        c.setX(a.getX());
        c.setY(b.getY());
    }

}
