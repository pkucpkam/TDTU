using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise2
{
    internal class Program
    {
        static void Main()
        {
            // Tạo các thành phần
            Workstation workstation = new Workstation("Workstation1");
            Server server = Server.GetInstance();
            Printer printer1 = new Printer("Printer1");
            Printer printer2 = new Printer("Printer2");

            // Kết nối hệ thống
            workstation.InsertNode(server);
            server.SetPrinters(printer1, printer2);

            // Gửi lệnh in
            Packet packet1 = new Packet("Workstation1", "Server", "Print document A");
            Packet packet2 = new Packet("Workstation1", "Server", "Print document B");

            workstation.Originate(packet1);
            workstation.Originate(packet2);

            Console.ReadKey();
        }
    }
}
