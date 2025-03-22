
public class NYPizzaStore extends PizzaStore {

    @Override
    protected Pizza createPizza(String item) {
        Pizza pizza = null;
        PizzaIngredientFactory ingredentFactory = new NYPizzaIngredientFactory();

        if (item.equals("cheese")) {
            pizza = new CheesePizza(ingredentFactory);
            pizza.setName("New York Cheese Pizza");
        } else if (item.equals("veggie")) {
            pizza = new VeggiePizza(ingredentFactory);
            pizza.setName("New York Veggies Pizza");
        } else if (item.equals("clam")) {
            pizza = new ClamPizza(ingredentFactory);
            pizza.setName("New York Clam Pizza");
        } else if (item.equals("pepperoni")) {
            pizza = new PepperoniPizza(ingredentFactory);
            pizza.setName("New York Pepperoni Pizza");
        }

        return pizza;
    }

}