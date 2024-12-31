package com.example.bai1.network;

import com.example.bai1.recycler.DownloadFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {

    public interface Callback {
        void onFileInfoChanged(DownloadFile file);
        void onComplete(DownloadFile file);
        void onError(Throwable t);
    }

    public static void fetchInfo(DownloadFile file, Callback callback) {
        try {
            URL url = new URL(file.getDownloadUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.connect();

            String fileName = getFileName(file.getDownloadUrl(), conn);
            int fileSize = conn.getContentLength();

            file.setName(fileName);
            file.setSize(fileSize);

            if (callback != null) {
                callback.onFileInfoChanged(file);
            }

            conn.disconnect();
        }catch (Exception ex) {
            ex.printStackTrace();
            if (callback != null) {
                callback.onError(ex);
            }
        }
    }

    public static void download(DownloadFile file, File parent,
                                Callback callback){
        try {
            URL url = new URL(file.getDownloadUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.connect();

            String fileName = getFileName(file.getDownloadUrl(), conn);
            int fileSize = conn.getContentLength();

            file.setSize(fileSize);

            InputStream is = conn.getInputStream();
            byte[] buffer = new byte[10 * 1024];
            int downloaded = 0;
            int size;

            FileOutputStream stream = new FileOutputStream(
                    new File(parent, fileName));

            while ((size = is.read(buffer)) > 0) {
                stream.write(buffer, 0, size);
                downloaded += size;

                int percentage = (int) (downloaded * 100.0 / fileSize);
                file.setProgress(percentage);

                if (callback != null) {
                    callback.onFileInfoChanged(file);
                }
            }

            if (callback != null) {
                callback.onComplete(file);
            }

            stream.close();
            is.close();
            conn.disconnect();

        }catch (Exception e) {
            if (callback != null) {
                callback.onError(e);
            }
            e.printStackTrace();
        }
    }

    private static String getFileName(String fileURL, HttpURLConnection httpConn) {
        String fileName = "";
        String disposition = httpConn.getHeaderField("Content-Disposition");

        if (disposition != null) {
            // extracts file name from header field
            int index = disposition.indexOf("filename=");
            if (index > 0) {
                fileName = disposition.substring(index + 10,
                        disposition.length() - 1);
            }
        } else {
            // extracts file name from URL
            fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                    fileURL.length());
        }

        return fileName;
    }

}