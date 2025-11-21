package OOP;

abstract class Drink {
    protected String name;
    protected double basePrice;
    protected String size; // S, M, L

    public Drink(String name, double basePrice, String size) {
        this.name = name;
        this.basePrice = basePrice;
        this.size = size;
    }

    // Phương thức đa hình: tính giá
    public abstract double calculatePrice();

    public String getName() {
        return name;
    }
}
