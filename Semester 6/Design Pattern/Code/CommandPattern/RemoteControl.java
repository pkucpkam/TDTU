public class RemoteControl {
    Command[] onCommands;
    Command[] offCommands;
    Command undoCommand;

    public RemoteControl(){
        onCommands = new Command[7];
        offCommands = new Command[7];

        Command noCommand = new NoCommand();
        for (int i = 0; i < offCommands.length; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
        undoCommand = noCommand;
    }

    public void setComand(int slot, Command onCommand, Command offCommand){
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    public void onButtonWasPushed(int slot){
        onCommands[slot].execute();
        undoCommand = onCommands[slot];
    }

    public void offButtonWasPushed(int slot){
        offCommands[slot].execute();
        undoCommand = offCommands[slot];
    }

    public void undoButtonWasPushed(int slot){
        undoCommand.undo();
    }

    public String toString(){
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("\n----- Remote Control -----\n");
        for (int i = 0; i < offCommands.length; i++) {
            strBuff.append("[slot " + i + "]" + onCommands[i].getClass().getName()
                + " "+offCommands[i].getClass().getName()+ "\n");
        }
        return strBuff.toString();
    }
}
