package com.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = request.getParameter("file");
        String speedStr = request.getParameter("speed");

        if (fileName == null || fileName.isEmpty()) {
            response.getWriter().write("File not found");
            return;
        }

        File file = new File(getServletContext().getRealPath("/files/" + fileName));
        if (!file.exists()) {
            response.getWriter().write("File not found");
            return;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        int speedLimit = (speedStr != null) ? Integer.parseInt(speedStr) * 1024 : Integer.MAX_VALUE;
        try (InputStream fileStream = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            long bytesTransferred = 0;
            long startTime = System.nanoTime();

            while ((bytesRead = fileStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                bytesTransferred += bytesRead;

                if (speedLimit < Integer.MAX_VALUE) {
                    long timeElapsed = System.nanoTime() - startTime;
                    long expectedTime = (bytesTransferred * 1_000_000_000L) / speedLimit;

                    if (expectedTime > timeElapsed) {
                        try {
                            Thread.sleep((expectedTime - timeElapsed) / 1_000_000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }
}
