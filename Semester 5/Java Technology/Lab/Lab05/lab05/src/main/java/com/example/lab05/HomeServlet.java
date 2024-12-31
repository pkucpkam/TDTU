package com.example.lab05;

import com.example.lab05.product.Product;
import com.example.lab05.product.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "/")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HomeServlet is called!");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
        } else {
            List<Product> products = ProductDao.getAllProducts();

            // Đưa danh sách sản phẩm vào request để gửi sang JSP
            request.setAttribute("products", products);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
