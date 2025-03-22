public class IsoscelesTriangle extends Triangle {
    public IsoscelesTriangle(Point a, Point b) {
        super(a, b);
    }

    @Override
    protected void draw2ndLine() {
        double baseMidX = (a.getX() + b.getX()) / 2;
        double height = Math.abs(a.getX() - b.getX());  // Đặt chiều cao bằng độ dài đáy

        c.setX(baseMidX);
        c.setY(a.getY() + height);
    }

}
