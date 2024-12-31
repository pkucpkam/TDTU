package com.example;

import org.apache.commons.io.FilenameUtils;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "UploadServlet", value = "/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        Part part = request.getPart("document");
        String pathUploadsFolder = request.getServletContext().getRealPath("/uploads");
        String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        String fileExtension = FilenameUtils.getExtension(filename);
        List<String> acceptedExtensions = Arrays.asList("txt", "doc", "docx", "img", "pdf", "rar", "zip");
        if (acceptedExtensions.stream().noneMatch(extension -> extension.equals(fileExtension))) {
            response.getWriter().write("Unsupported file extension");
            return;
        }
        if (!Files.exists(Paths.get(pathUploadsFolder))) {
            Files.createDirectory(Paths.get(pathUploadsFolder));
        }
        if (Files.exists(Paths.get(pathUploadsFolder + "\\" + filename))) {
            if (request.getParameter("override") == null) {
                response.getWriter().write("File already exists");
            } else {
                try {
                    InputStream inputStream = part.getInputStream();
                    OutputStream outputStream = new FileOutputStream(pathUploadsFolder + "\\" + filename);
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    inputStream.close();
                    outputStream.close();
                    response.getWriter().write("File has been override");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } else {
            part.write(pathUploadsFolder + "\\" + filename);
            response.getWriter().write("Click <a href='download?file=" + filename + "'>here</a> to visit file");
        }
    }
}