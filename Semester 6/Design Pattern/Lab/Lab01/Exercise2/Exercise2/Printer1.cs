using Exercise2;
using System;

public class Printer1 : Printer
{
    public Printer1() : base("Printer1") { }

    protected override void Print(Packet packet)
    {
        Console.WriteLine($"[Printer1] In tài liệu: {packet.Contents} từ {packet.Originator}");
    }
}


