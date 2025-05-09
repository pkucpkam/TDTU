using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise01
{
    public class MyFTP : ISimpleFTP
    {
        public void SendAMsg(string message)
        {
            Console.WriteLine("Sending message via MyFTP: " + message);
        }

        public void ConnectServer()
        {
            Console.WriteLine("Connecting via MyFTP");
        }
    }
}
