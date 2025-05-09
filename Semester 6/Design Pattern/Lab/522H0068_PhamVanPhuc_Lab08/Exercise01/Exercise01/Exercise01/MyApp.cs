using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise01
{
    public class MyApp
    {
        private ISimpleFTP ftp;

        public void SetFTP(ISimpleFTP ftp)
        {
            this.ftp = ftp;
        }

        public void DoSomething()
        {
            ftp.ConnectServer();
            ftp.SendAMsg("abc");
        }
    }
}
