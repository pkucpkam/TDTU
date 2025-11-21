package OOP;

class MilkTea extends Drink {
    private boolean hasTopping;

    public MilkTea(String size, boolean hasTopping) {
        super("Milk Tea", 35000, size);
        this.hasTopping = hasTopping;
    }

    @Override
    public double calculatePrice() {
        double price = basePrice;
        if (hasTopping) price += 5000;
        return price;
    }
}