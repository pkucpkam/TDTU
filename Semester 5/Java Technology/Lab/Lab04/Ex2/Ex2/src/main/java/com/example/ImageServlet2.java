package com.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet("/image2")
public class ImageServlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        response.setHeader("Content-Disposition", "attachment; filename=\"image2.jpg\"");

        try (InputStream imageStream = getServletContext().getResourceAsStream("/images/image2.jpg")) {
            if (imageStream == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            OutputStream out = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = imageStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
