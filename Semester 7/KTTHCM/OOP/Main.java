
import java.util.*;

abstract class Drink {
    protected String name;
    protected double basePrice;
    protected String size;

    public Drink(String name, double basePrice, String size) {
        this.name = name;
        this.basePrice = basePrice;
        this.size = size;
    }

    public abstract double calculatePrice();

    public String getName() {
        return name;
    }
}

class Coffee extends Drink {
    public Coffee(String size) {
        super("Coffee", 30000, size);
    }

    @Override
    public double calculatePrice() {
        if (size.equals("M")) return basePrice + 2000;
        else if (size.equals("L")) return basePrice + 5000;
        return basePrice;
    }
}

class Tea extends Drink {
    public Tea(String size) {
        super("Tea", 25000, size);
    }

    @Override
    public double calculatePrice() {
        if (size.equals("S")) return basePrice * 0.9;
        return basePrice;
    }
}

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

public class Main {
    public static void main(String[] args) {
        List<Drink> orders = new ArrayList<>();
        orders.add(new Coffee("L"));
        orders.add(new Tea("S"));
        orders.add(new MilkTea("M", true));
        orders.add(new Coffee("S"));
        orders.add(new MilkTea("L", false));

        double totalRevenue = 0;
        for (Drink d : orders) {
            totalRevenue += d.calculatePrice();
        }
        System.out.println("Tổng doanh thu: " + totalRevenue);

        Map<String, Double> revenueByType = new HashMap<>();
        for (Drink d : orders) {
            revenueByType.put(d.getName(),
                    revenueByType.getOrDefault(d.getName(), 0.0) + d.calculatePrice());
        }

        System.out.println("Doanh thu theo loại đồ uống:");
        for (String key : revenueByType.keySet()) {
            System.out.println(key + ": " + revenueByType.get(key));
        }
    }
}
