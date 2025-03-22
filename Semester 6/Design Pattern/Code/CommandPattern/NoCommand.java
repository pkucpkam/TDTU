public class NoCommand implements Command {

    @Override
    public void execute() {
    }

    @Override
    public void undo() {
        throw new UnsupportedOperationException("Unimplemented method 'undo'");
    }

}
