using Exercise2;
using System;

public class Printer2 : Printer
{
    public Printer2() : base("Printer2") { }

    protected override void Print(Packet packet)
    {
        Console.WriteLine($"[Printer2] In tài liệu: {packet.Contents} từ {packet.Originator}");
    }
}