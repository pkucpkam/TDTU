using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise02
{
    public interface INewsService
    {
        List<Category> GetCategories();
        List<News> GetNewsList(int categoryId);
        News GetNewsDetail(int newsId);
    }
}
