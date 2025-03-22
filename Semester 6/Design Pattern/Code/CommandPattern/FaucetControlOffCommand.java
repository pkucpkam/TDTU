public class FaucetControlOffCommand implements Command {
    FaucetControl faucetControl;

    public FaucetControlOffCommand(FaucetControl faucetControl) {
        this.faucetControl = faucetControl;
    }

    @Override
    public void execute() {
        faucetControl.closeValue();
    }

    @Override
    public void undo() {
        faucetControl.openValue();
    }
}