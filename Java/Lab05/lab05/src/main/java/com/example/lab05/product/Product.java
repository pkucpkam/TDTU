package com.example.lab05.product;

public class Product {
    private int id;  // Thêm trường id
    private String name;
    private double price;

    // Constructor
    public Product(int id, String name, double price) {
        this.id = id;  // Khởi tạo id
        this.name = name;
        this.price = price;
    }

    public Product() {
    }

    // Getter và Setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter và Setter cho price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
