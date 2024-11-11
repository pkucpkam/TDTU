package com.example.lab05;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy session hiện tại
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Hủy session
            session.invalidate();
        }

        // Chuyển hướng người dùng về trang đăng nhập
        response.sendRedirect("login.jsp");
    }
}
