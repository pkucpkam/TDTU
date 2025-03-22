import strategy.MallardDuck;
import strategy.RedheadDuck;
import strategy.RubberDuck;
import strategy.DecoyDuck;
import strategy.Duck;

public class MiniGameDuck {
    public static void main(String[] args) {
        Duck mallard = new MallardDuck();
        Duck redhead = new RedheadDuck();
        Duck rubber = new RubberDuck();
        Duck decoy = new DecoyDuck();

        mallard.display();
        mallard.performQuack();
        mallard.performFly();
        System.out.println();

        redhead.display();
        redhead.performQuack();
        redhead.performFly();
        System.out.println();

        rubber.display();
        rubber.performQuack();
        rubber.performFly();
        System.out.println();

        decoy.display();
        decoy.performQuack();
        decoy.performFly();
    }
}
