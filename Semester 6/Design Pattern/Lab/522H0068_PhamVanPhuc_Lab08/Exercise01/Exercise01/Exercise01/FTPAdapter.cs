using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise01
{
    public class FTPAdapter : ISimpleFTP
    {
        private IComplexFTP complexFTP;

        public FTPAdapter(IComplexFTP complexFTP)
        {
            this.complexFTP = complexFTP;
        }

        public void SendAMsg(string message)
        {
            // Chuyển single message thành array để thích ứng với ComplexFTP
            complexFTP.SendMsgs(new string[] { message });
        }

        public void ConnectServer()
        {
            // Map phương thức ConnectServer sang Connect
            complexFTP.Connect();
        }
    }
}
