using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise01
{
    public class ComplexFTP : IComplexFTP
    {
        public void SendMsgs(string[] messages)
        {
            Console.WriteLine("Sending multiple messages: " + string.Join(", ", messages));
        }

        public void Connect()
        {
            Console.WriteLine("Connecting to complex FTP server");
        }

        public string[] GetDirs()
        {
            return new string[] { "dir1", "dir2", "dir3" };
        }
    }
}
