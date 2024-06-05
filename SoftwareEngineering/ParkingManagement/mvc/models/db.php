<?php
$servername = "localhost"; // Tên máy chủ MySQL
$username = "root"; // Tên người dùng MySQL
$password = ""; // Mật khẩu MySQL
$database = "parking"; // Tên cơ sở dữ liệu MySQL

try {
    // Kết nối đến cơ sở dữ liệu MySQL
    $conn = new PDO("mysql:host=$servername;dbname=$database", $username, $password);

    // Thiết lập chế độ lỗi cho PDO thành ném ngoại lệ
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Thiết lập bộ ký tự kết nối
    $conn->exec("SET NAMES utf8");
} catch(PDOException $e) {
    // Xử lý lỗi nếu kết nối thất bại
    echo "Kết nối đến cơ sở dữ liệu thất bại: " . $e->getMessage();
}
?>
