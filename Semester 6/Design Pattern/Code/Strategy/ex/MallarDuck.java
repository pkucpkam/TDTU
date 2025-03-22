package ex;
public class MallarDuck extends Duck {
    public MallarDuck() {
        flyBehavior = new FlyWithWing();
    }

    public void display() {
        System.out.println("Tôi là vịt Mallar.");
    }
}
