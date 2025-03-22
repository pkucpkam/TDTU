public class CheesePizza extends Pizza {
    PizzaIngredientFactory ingredentFactory;

    public CheesePizza(PizzaIngredientFactory ingredentFactory) {
        this.ingredentFactory = ingredentFactory;
    }

    void prepare() {
        System.out.println("Preparing " + name);
        dough = ingredentFactory.createDough();
        sauce = ingredentFactory.createSauce();
        cheese = ingredentFactory.createCheese();
    }

}
