using System;
using System.Threading;
using System.Threading.Tasks;

class Program
{
    static void Main(string[] args)
    {
        Console.WriteLine("Select running mode:");
        Console.WriteLine("1 - Single Thread");
        Console.WriteLine("2 - Multithreads");
        Console.Write("Enter selection: ");

        string choice = Console.ReadLine();

        if (choice == "1")
        {
            RunSingleThread();
        }
        else if (choice == "2")
        {
            RunMultiThread();
        }
        else
        {
            Console.WriteLine("Invalid selection!");
        }

        Console.WriteLine("Logging complete.");
        Console.ReadKey();
    }

    static void RunSingleThread()
    {
        Console.WriteLine("Run the program in single thread ...\n");

        Log logger1 = Log.GetInstance();
        Log logger2 = Log.GetInstance();

        logger1.WriteLog("This is single thread log 1");
        logger2.WriteLog("This is single thread log 2");

        Console.WriteLine($"\nLogger1 & Logger2 same instance? {ReferenceEquals(logger1, logger2)}");
    }

    static void RunMultiThread()
    {
        Console.WriteLine("Run the program in multithreaded...\n");

        Parallel.For(0, 5, i =>
        {
            Log logger = Log.GetInstance();
            logger.WriteLog($"Thread {Thread.CurrentThread.ManagedThreadId} ghi log");
        });
    }
}
