using Exercise2;
using System.Xml.Linq;
using System;

public class Workstation : Node
{
    public Workstation(string name) : base(name) { }

    public void Originate(Packet packet)
    {
        Console.WriteLine($"[Workstation] {Name} send packets: {packet}");
        Accept(packet);
    }

    public override void Accept(Packet packet)
    {
        NextNode?.Accept(packet);
    }
}