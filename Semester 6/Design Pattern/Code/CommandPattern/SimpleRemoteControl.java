public class SimpleRemoteControl {
    Command slot;

    public SimpleRemoteControl(){}

    public void setComand(Command command){
        slot = command;
    }

    public  void buttonWasPressed(){
        slot.execute();
    }
    
}
