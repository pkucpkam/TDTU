import java.util.Arrays;

public abstract class Pizza {
    String name;

    Dough dough;
    Sauce sauce;
    Veggies veggies[];
    Cheese cheese;
    Pepperoni pepperoni;
    Clams clam;

    abstract void prepare();

    void bake() {
        System.out.println("Bake 25 minutes at 350");
    }

    void cut() {
        System.out.println("Cutting the pizza into diagonal slices");
    }

    void box() {
        System.out.println("Place Pizza in official PizzaStore box.");
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "Pizza{" +
                "name='" + name + '\'' +
                ", dough=" + dough +
                ", sauce=" + sauce +
                ", cheese=" + cheese +
                ", clams=" + clam +
                ", pepperoni=" + pepperoni +
                ", veggies=" + Arrays.toString(veggies) +
                '}';
    }
}
