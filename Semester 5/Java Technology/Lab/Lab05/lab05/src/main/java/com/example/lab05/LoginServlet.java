package com.example.lab05;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // false: không tạo session mới nếu chưa có
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect("/lab05/home");
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    // Xử lý POST (Xử lý đăng nhập)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDao userDao = new UserDao();
        User user = userDao.validateUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("/lab05/home");
        } else {
            // Nếu đăng nhập thất bại, hiển thị thông báo lỗi
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);  // Quay lại trang đăng nhập
        }
    }
}
