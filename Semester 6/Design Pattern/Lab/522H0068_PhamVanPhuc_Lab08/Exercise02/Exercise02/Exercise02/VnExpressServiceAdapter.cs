using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise02
{
    public class VnExpressServiceAdapter : INewsService
    {
        private readonly VnExpressService service;

        public VnExpressServiceAdapter(VnExpressService service)
        {
            this.service = service;
        }

        public List<Category> GetCategories()
        {
            VECat[] veCategories = service.RetrieveCats();
            List<Category> categories = new List<Category>();

            foreach (VECat veCat in veCategories)
            {
                categories.Add(new Category
                {
                    Id = veCat.CatId,
                    Title = veCat.Title
                });
            }

            return categories;
        }

        public List<News> GetNewsList(int categoryId)
        {
            VENews[] veNewsList = service.RetrieveNews(categoryId);
            List<News> newsList = new List<News>();

            foreach (VENews veNews in veNewsList)
            {
                newsList.Add(new News
                {
                    Id = veNews.Id,
                    Title = veNews.Headline,
                    Content = veNews.Content,
                    CategoryId = categoryId
                });
            }

            return newsList;
        }

        public News GetNewsDetail(int newsId)
        {
            // Giả lập lấy chi tiết tin
            VENews[] newsList = service.RetrieveNews(0); // 0 để lấy tất cả
            foreach (VENews veNews in newsList)
            {
                if (veNews.Id == newsId)
                {
                    return new News
                    {
                        Id = veNews.Id,
                        Title = veNews.Headline,
                        Content = veNews.Content
                    };
                }
            }
            return null;
        }
    }
}
