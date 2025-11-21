package OOP;

class Tea extends Drink {
    public Tea(String size) {
        super("Tea", 25000, size);
    }

    @Override
    public double calculatePrice() {
        // giảm giá 10% nếu size nhỏ
        if (size.equals("S")) return basePrice * 0.9;
        return basePrice;
    }
}