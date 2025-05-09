using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise02
{
    public class VnExpressService
    {
        public VECat[] RetrieveCats()
        {
            // Giả lập dữ liệu
            return new VECat[]
            {
            new VECat { CatId = 1, Title = "Kinh tế", Content = "Mô tả kinh tế" },
            new VECat { CatId = 2, Title = "Giáo dục", Content = "Mô tả giáo dục" }
            };
        }

        public VENews[] RetrieveNews(int catId)
        {
            // Giả lập dữ liệu
            return new VENews[]
            {
            new VENews { Id = 1, Headline = "Tin 3", Content = "Nội dung tin 3" },
            new VENews { Id = 2, Headline = "Tin 4", Content = "Nội dung tin 4" }
            };
        }
    }
}
