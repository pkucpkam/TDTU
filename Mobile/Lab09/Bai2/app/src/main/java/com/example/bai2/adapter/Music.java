package com.example.bai2.adapter;

import java.util.Objects;

public class Music {
    private String name;
    private int resourceId;

    public Music() {
    }

    public Music(String name, int resourceId) {
        this.name = name;
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Music music = (Music) o;
        return resourceId == music.resourceId && Objects.equals(name, music.name);
    }
}