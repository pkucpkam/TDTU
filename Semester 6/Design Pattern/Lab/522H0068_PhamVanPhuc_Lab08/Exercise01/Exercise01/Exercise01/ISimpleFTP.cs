using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise01
{
    public interface ISimpleFTP
    {
        void SendAMsg(string message);
        void ConnectServer();
    }
}
