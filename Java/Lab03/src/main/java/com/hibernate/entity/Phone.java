package com.hibernate.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "MobilePhone")
public class Phone {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @NotNull
    @Size(min = 3, max = 128)
    @Column(name = "Name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "Price", nullable = false)
    private int price;

    @NotNull
    @Size(min = 3, max = 128)
    @Column(name = "Color", nullable = false)
    private String color;

    @Column(name = "Country")
    private String country;

    @Column(name = "Quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "ManufactureID", nullable = false)
    private Manufacture manufacture;

    // Constructors
    public Phone() {}

    public Phone(String id, String name, int price, String color, String country, int quantity, Manufacture manufacture) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.color = color;
        this.country = country;
        this.quantity = quantity;
        this.manufacture = manufacture;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Manufacture getManufacture() {
        return manufacture;
    }

    public void setManufacture(Manufacture manufacture) {
        this.manufacture = manufacture;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "ID='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", country='" + country + '\'' +
                ", quantity=" + quantity +
                ", manufacture=" + manufacture.getName() +
                '}';
    }
}
