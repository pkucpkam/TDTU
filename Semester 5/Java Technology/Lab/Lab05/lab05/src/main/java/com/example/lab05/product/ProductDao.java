package com.example.lab05.product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/productmanagement";
    private static final String JDBC_USER = "root";  // Đổi với username MySQL của bạn
    private static final String JDBC_PASSWORD = "";  // Đổi với password của bạn

    // Phương thức thêm sản phẩm vào cơ sở dữ liệu
    public void addProduct(Product product) {
        String sql = "INSERT INTO product (name, price) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";

        // Kết nối đến cơ sở dữ liệu và lấy dữ liệu
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Duyệt qua kết quả trả về từ database
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}
