<!-- loginModel.php -->
<?php
require_once 'db.php';

function authenticate($username, $password) {
    global $conn; // Sử dụng biến kết nối được khai báo trong db.php

    try {
        // Sử dụng Prepared Statements để tránh SQL Injection
        $query = "SELECT * FROM account WHERE username = :username AND password = :password";
        $statement = $conn->prepare($query);
        $statement->bindParam(':username', $username);
        $statement->bindParam(':password', $password);
        $statement->execute();
        $user = $statement->fetch(PDO::FETCH_ASSOC);

        // Kiểm tra xem câu truy vấn đã trả về dữ liệu hay không
        if ($user) {
            return $user['role']; // Trả về vai trò của người dùng
        } else {
            return ""; // Trả về chuỗi rỗng nếu không tìm thấy người dùng
        }
    } catch(PDOException $e) {
        return "Error: " . $e->getMessage(); // Trả về thông báo lỗi nếu có
    }
}
?>
