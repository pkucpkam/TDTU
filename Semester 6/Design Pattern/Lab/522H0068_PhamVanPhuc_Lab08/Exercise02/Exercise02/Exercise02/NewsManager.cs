using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise02
{
    public class NewsManager
    {
        private readonly List<INewsService> services;

        public NewsManager()
        {
            services = new List<INewsService>
        {
            new ThanhNienServiceAdapter(new ThanhNienService()),
            new VnExpressServiceAdapter(new VnExpressService())
        };
        }

        public List<Category> GetAllCategories()
        {
            List<Category> allCategories = new List<Category>();
            foreach (var service in services)
            {
                allCategories.AddRange(service.GetCategories());
            }
            return allCategories;
        }

        public List<News> GetNewsByCategory(int categoryId)
        {
            List<News> allNews = new List<News>();
            foreach (var service in services)
            {
                allNews.AddRange(service.GetNewsList(categoryId));
            }
            return allNews;
        }

        public News GetNewsDetail(int serviceIndex, int newsId)
        {
            if (serviceIndex >= 0 && serviceIndex < services.Count)
            {
                return services[serviceIndex].GetNewsDetail(newsId);
            }
            return null;
        }
    }
}
