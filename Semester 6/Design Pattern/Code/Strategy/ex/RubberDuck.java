package ex;
public class RubberDuck extends Duck {
    public RubberDuck() {
        flyBehavior = new FlyNoWay();
    }

    public void display() {
        System.out.println("Tôi là vịt cao su.");
    }
    
}
