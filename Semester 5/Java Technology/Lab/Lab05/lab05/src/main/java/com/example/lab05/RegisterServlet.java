package com.example.lab05;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển hướng đến trang đăng ký nếu là GET request
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");

        UserDao userDao = new UserDao();

        // Kiểm tra tên đăng nhập đã tồn tại chưa
        if (userDao.isUsernameExists(username)) {
            request.setAttribute("error", "Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            // Lưu người dùng mới vào cơ sở dữ liệu
            userDao.registerUser(username, password, fullName);

            // Sau khi đăng ký thành công, chuyển hướng về trang đăng nhập
            response.sendRedirect("login.jsp");
        }
    }
}

