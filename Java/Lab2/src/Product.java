
public class Product {
    private int id;
    private String name;
    private double price;
    private String color;

    public Product() {
        super();
    }

    public Product(int id, String name, double price, String color) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.color = color;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-20s %-10s %-10s",
                "ID", "Name", "Price", "Color") + "\n" +
                String.format("%-10d %-20s %-10.2f %-10s",
                        id, name, price, color);
    }

}