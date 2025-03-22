public class FaucetControlOnCommand implements Command {
    FaucetControl faucetControl;

    public FaucetControlOnCommand(FaucetControl faucetControl) {
        this.faucetControl = faucetControl;
    }

    @Override
    public void execute() {
        faucetControl.openValue();
    }

    @Override
    public void undo() {
        faucetControl.closeValue();
    }
}