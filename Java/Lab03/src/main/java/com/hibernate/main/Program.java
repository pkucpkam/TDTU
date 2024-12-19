package com.hibernate.main;

import com.hibernate.dao.ManufactureDAO;
import com.hibernate.dao.PhoneDAO;
import com.hibernate.entity.Manufacture;
import com.hibernate.entity.Phone;

public class Program {
    public static void main(String[] args) {
        ManufactureDAO manufactureDAO = new ManufactureDAO();
        PhoneDAO phoneDAO = new PhoneDAO();

        System.out.println("------------- BEGIN ADD MANUFACTURE -------------");
        // Thêm các nhà sản xuất
        Manufacture m1 = new Manufacture("M001", "Apple", "USA", 100000);
        Manufacture m2 = new Manufacture("M002", "Samsung", "Korea", 200000);
        Manufacture m3 = new Manufacture("M003", "Xiaomi", "China", 300000);
        Manufacture m4 = new Manufacture("M004", "Huawei", "China", 400000);
        manufactureDAO.add(m1);
        manufactureDAO.add(m2);
        manufactureDAO.add(m3);
        manufactureDAO.add(m4);
        System.out.println("------------- END ADD MANUFACTURE -------------");

        System.out.println("------------- BEGIN ADD PHONE -------------");
        // Thêm các điện thoại
        Phone p1 = new Phone("P001", "iPhone 12", 1500, "Black", "USA", 100, m1);
        Phone p2 = new Phone("P002", "iPhone 12 Pro", 20000, "Black", "USA", 100, m1);
        Phone p3 = new Phone("P003", "iPhone 12 Pro Max", 25000, "Black", "USA", 100, m1);
        Phone p4 = new Phone("P004", "Samsung Galaxy S21", 1500, "Black", "Korea", 100, m2);
        Phone p5 = new Phone("P005", "Samsung Galaxy S21 Plus", 20000, "Black", "Korea", 100, m2);
        Phone p6 = new Phone("P006", "Samsung Galaxy S21 Ultra", 2500, "Black", "Korea", 100, m2);
        Phone p7 = new Phone("P007", "Xiaomi Mi 11", 15000, "Black", "China", 100, m3);
        Phone p8 = new Phone("P008", "Xiaomi Mi 11 Pro", 2000, "Black", "China", 100, m3);
        Phone p9 = new Phone("P009", "Xiaomi Mi 11 Ultra", 25000, "Black", "China", 100, m3);
        phoneDAO.add(p1);
        phoneDAO.add(p2);
        phoneDAO.add(p3);
        phoneDAO.add(p4);
        phoneDAO.add(p5);
        phoneDAO.add(p6);
        phoneDAO.add(p7);
        phoneDAO.add(p8);
        phoneDAO.add(p9);
        System.out.println("------------- END ADD PHONE -------------");

        // Test CRUD operations
        System.out.println("All Phones: " + phoneDAO.getAll());
        System.out.println("Get Phone P001: " + phoneDAO.get("P001"));

        // Update phone price
        p1.setPrice(32000);
        phoneDAO.update(p1);
        System.out.println("Updated Phone P001: " + phoneDAO.get("P001"));

        // Remove a phone
        phoneDAO.remove("P002");
        System.out.println("All Phones after removal: " + phoneDAO.getAll());

        // Get phone with highest price
        System.out.println("Phone with highest price: " + phoneDAO.getPhoneWithHighestPrice());

        // Check if there are phones above 50 million
        System.out.println("Is there a phone above 50 million? " + phoneDAO.hasPhoneAbove50Million());

        // Get all manufacturers
        System.out.println("All Manufacturers: " + manufactureDAO.getAll());

        // Check if all manufacturers have more than 100 employees
        System.out.println("All manufacturers have more than 100 employees: " + manufactureDAO.allManufacturersHaveMoreThan100Employees());

        // Sum of all employees
        System.out.println("Total employees: " + manufactureDAO.sumAllEmployees());

        // Get last manufacturer based in US
        try {
            System.out.println("Last manufacturer based in US: " + manufactureDAO.getLastManufacturerBasedInUS());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
