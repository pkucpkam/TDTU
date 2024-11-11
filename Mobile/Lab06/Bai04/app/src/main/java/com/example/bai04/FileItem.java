package com.example.bai04;

public class FileItem {
    private String name;
    private boolean isDirectory;

    public FileItem(String name, boolean isDirectory) {
        this.name = name;
        this.isDirectory = isDirectory;
    }

    public String getName() {
        return name;
    }

    public boolean isDirectory() {
        return isDirectory;
    }
}

