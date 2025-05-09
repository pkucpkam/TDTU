using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Exercise02
{
    public class ThanhNienServiceAdapter : INewsService
    {
        private readonly ThanhNienService service;

        public ThanhNienServiceAdapter(ThanhNienService service)
        {
            this.service = service;
        }

        public List<Category> GetCategories()
        {
            ArrayList tnCategories = service.GetCategories();
            List<Category> categories = new List<Category>();

            foreach (TNCat tnCat in tnCategories)
            {
                categories.Add(new Category
                {
                    Id = tnCat.Id,
                    Title = tnCat.Title
                });
            }

            return categories;
        }

        public List<News> GetNewsList(int categoryId)
        {
            ArrayList tnNewsList = service.GetNewsList(categoryId);
            List<News> newsList = new List<News>();

            foreach (TNews tnNews in tnNewsList)
            {
                newsList.Add(new News
                {
                    Id = tnNews.Id,
                    Title = tnNews.Title,
                    Content = tnNews.Content,
                    CategoryId = categoryId
                });
            }

            return newsList;
        }

        public News GetNewsDetail(int newsId)
        {
            // Giả lập lấy chi tiết tin
            ArrayList newsList = service.GetNewsList(0); // 0 để lấy tất cả
            foreach (TNews tnNews in newsList)
            {
                if (tnNews.Id == newsId)
                {
                    return new News
                    {
                        Id = tnNews.Id,
                        Title = tnNews.Title,
                        Content = tnNews.Content
                    };
                }
            }
            return null;
        }
    }
}
