using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise01
{
    public interface IComplexFTP
    {
        void SendMsgs(string[] messages);
        void Connect();
        string[] GetDirs();
    }
}
