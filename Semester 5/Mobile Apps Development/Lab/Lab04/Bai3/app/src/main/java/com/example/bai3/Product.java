package com.example.bai3;

public class Product {
    private String name;
    private boolean isSelected;

    public Product(String name) {
        this.name = name;
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
