using Exercise2;
using System.Reflection;
using System;

public class Server : Node
{
    private static Server _instance;
    private static readonly object _lock = new object();
    private Printer1 printer1;
    private Printer2 printer2;

    private Server(string name) : base(name) { }

    public static Server GetInstance()
    {
        if (_instance == null)
        {
            lock (_lock)
            {
                if (_instance == null)
                {
                    _instance = new Server("Server");
                }
            }
        }
        return _instance;
    }

    public void SetPrinters(Printer1 p1, Printer2 p2)
    {
        printer1 = p1;
        printer2 = p2;
    }

    public override void Accept(Packet packet)
    {
        if (packet.IsDestination(Name))
        {
            Console.WriteLine($"[Server] Nhận gói tin: {packet}");
            DispatchPrint(packet);
        }
        else
        {
            NextNode?.Accept(packet);
        }
    }

    private void DispatchPrint(Packet packet)
    {
        Printer selectedPrinter = new Random().Next(2) == 0 ? printer1 : printer2;
        selectedPrinter.Accept(packet);
    }
}
