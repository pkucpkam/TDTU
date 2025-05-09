using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise02
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            // Thiết lập encoding của console thành UTF-8 để hiển thị tiếng Việt
            Console.OutputEncoding = System.Text.Encoding.UTF8;
            Console.InputEncoding = System.Text.Encoding.UTF8;

            NewsManager manager = new NewsManager();

            // Hiển thị danh mục
            Console.WriteLine("Danh sách danh mục:");
            var categories = manager.GetAllCategories();
            foreach (var category in categories)
            {
                Console.WriteLine($"ID: {category.Id}, Tiêu đề: {category.Title}");
            }

            // Hiển thị danh sách tin tức trong danh mục
            Console.WriteLine("\nDanh sách tin tức trong danh mục ID 1:");
            var newsList = manager.GetNewsByCategory(1);
            foreach (var news in newsList)
            {
                Console.WriteLine($"ID: {news.Id}, Tiêu đề: {news.Title}");
            }

            // Hiển thị chi tiết tin tức
            Console.WriteLine("\nChi tiết tin tức ID 1 từ ThanhNienService:");
            var newsDetail = manager.GetNewsDetail(0, 1); // 0 là index của ThanhNienService
            if (newsDetail != null)
            {
                Console.WriteLine($"ID: {newsDetail.Id}, Tiêu đề: {newsDetail.Title}, Nội dung: {newsDetail.Content}");
            }

            // Giữ console để xem kết quả
            Console.WriteLine("\nNhấn phím bất kỳ để thoát...");
            Console.ReadKey();
        }
    }
}