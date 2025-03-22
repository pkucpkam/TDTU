package strategy;

public class MuteQuack implements QuackBehavior {
    public void quack() {
        System.out.println("<< Can't quack >>");
    }
    
}
