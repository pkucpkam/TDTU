using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise01
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            // Test với MyFTP gốc
            Console.WriteLine("Testing with original MyFTP:");
            MyApp app1 = new MyApp();
            ISimpleFTP myFtp = new MyFTP();
            app1.SetFTP(myFtp);
            app1.DoSomething();

            Console.WriteLine("\nTesting with ComplexFTP through Adapter:");
            // Test với ComplexFTP qua Adapter
            MyApp app2 = new MyApp();
            IComplexFTP complexFtp = new ComplexFTP();
            ISimpleFTP adaptedFtp = new FTPAdapter(complexFtp);
            app2.SetFTP(adaptedFtp);
            app2.DoSomething();
        }
    }
}
