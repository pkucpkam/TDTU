using Exercise2;

public abstract class Printer : Node
{
    protected Printer(string name) : base(name) { }

    public override void Accept(Packet packet)
    {
        Print(packet);
    }

    protected abstract void Print(Packet packet);
}
