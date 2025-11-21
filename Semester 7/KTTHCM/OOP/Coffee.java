package OOP;

class Coffee extends Drink {
    public Coffee(String size) {
        super("Coffee", 30000, size);
    }

    @Override
    public double calculatePrice() {
        double price = basePrice;
        if (size.equals("M")) price += 2000;
        else if (size.equals("L")) price += 5000;
        return price;
    }
}