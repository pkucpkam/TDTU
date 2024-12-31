package com.example;

import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet(name = "DownloadServlet", value = "/download")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = request.getParameter("file");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        String pathUploadsFolder = request.getServletContext().getRealPath("/uploads");
        File file = new File(pathUploadsFolder + "\\" + filename);
        if (!Files.exists(Paths.get(pathUploadsFolder + "\\" + filename))) {
            response.getWriter().write("File does not exist");
        } else {
            InputStream inputStream = new FileInputStream(file);
            byte[] bytes = inputStream.readAllBytes();
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            inputStream.close();
            outputStream.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}