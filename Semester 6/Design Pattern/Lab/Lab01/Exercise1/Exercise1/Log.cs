using System;
using System.IO;
using System.Threading;

public class Log
{
    private static Log instance;
    private static readonly object lockObject = new object();

    private Log() { }

    public static Log GetInstance()
    {
        if (instance == null)
        {
            lock (lockObject)
            {
                if (instance == null)
                {
                    instance = new Log();
                }
            }
        }
        return instance;
    }

    public void WriteLog(string message)
    {
        lock (lockObject) // Đảm bảo thread-safe khi ghi log
        {
            string logEntry = $"[LOG] {DateTime.Now:yyyy-MM-dd HH:mm:ss} - {message}";
            Console.WriteLine(logEntry);
        }
    }
}