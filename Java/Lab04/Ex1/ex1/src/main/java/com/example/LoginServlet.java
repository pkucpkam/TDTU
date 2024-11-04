package com.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    private HashMap<String, String> accounts;

    @Override
    public void init() throws ServletException {
        accounts = new HashMap<>();
        accounts.put("user1", "password1");
        accounts.put("user2", "password2");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        if (accounts.containsKey(username) && accounts.get(username).equals(password)) {
            response.getWriter().println("Name/Password match");
        } else {
            response.getWriter().println("Name/Password does not match");
        }
    }
}
