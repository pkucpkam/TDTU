package com.example.lab05;

import java.sql.*;

public class UserDao {
    private Connection connection;

    // Kết nối cơ sở dữ liệu
    public UserDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Tải driver MySQL
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab05", "root", "");

            if (connection != null) {
                System.out.println("Kết nối cơ sở dữ liệu thành công!");
            } else {
                System.out.println("Không thể kết nối đến cơ sở dữ liệu.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }


    // Kiểm tra thông tin đăng nhập
    public User validateUser(String username, String password) {
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("fullName"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isUsernameExists(String username) {
        try {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Đăng ký người dùng mới
    public void registerUser(String username, String password, String fullName) {
        try {
            String query = "INSERT INTO users (username, password, fullName) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, fullName);
            ps.executeUpdate(); // Thực thi câu lệnh SQL để thêm người dùng mới
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
