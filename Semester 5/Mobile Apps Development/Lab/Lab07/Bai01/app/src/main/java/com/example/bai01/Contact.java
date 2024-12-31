package com.example.bai01;

public class Contact {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String company;

    // Constructor
    public Contact(String firstName, String lastName, String phone, String email, String company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.company = company;
    }

    // Getter and Setter methods
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + "\nPhone: " + phone + "\nEmail: " + email + "\nCompany: " + company; // Changed relationship to company
    }
}
