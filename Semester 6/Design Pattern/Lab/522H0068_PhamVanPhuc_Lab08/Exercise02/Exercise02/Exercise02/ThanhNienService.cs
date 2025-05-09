using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise02
{
    public class ThanhNienService
    {
        public ArrayList GetCategories()
        {
            // Giả lập dữ liệu
            ArrayList categories = new ArrayList
        {
            new TNCat { Id = 1, Title = "Thể thao" },
            new TNCat { Id = 2, Title = "Công nghệ" }
        };
            return categories;
        }

        public ArrayList GetNewsList(int catId)
        {
            // Giả lập dữ liệu
            ArrayList newsList = new ArrayList
        {
            new TNews { Id = 1, Title = "Tin 1", Content = "Nội dung tin 1" },
            new TNews { Id = 2, Title = "Tin 2", Content = "Nội dung tin 2" }
        };
            return newsList;
        }
    }
}
