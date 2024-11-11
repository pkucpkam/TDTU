package com.example.bai2.model;

import android.net.Uri;

public class Video {
    private String id;
    private Uri uri;
    private String name;
    private int duration;
    private int size;
    private boolean isSelected;
    private String path;
    public Video() {
    }


    public Video(String id, Uri uri, String name, int duration, int size, String path) {
        this.id = id;
        this.uri = uri;
        this.name = name;
        this.duration = duration;
        this.size = size;
        this.path = path;
        this.isSelected = false;
    }

    public Video(String id, Uri uri, String name, int duration, int size, boolean isSelected, String path) {
        this.id = id;
        this.uri = uri;
        this.name = name;
        this.duration = duration;
        this.size = size;
        this.isSelected = isSelected;
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
