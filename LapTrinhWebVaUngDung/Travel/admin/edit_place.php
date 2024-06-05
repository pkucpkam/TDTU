<?php
// Kết nối đến cơ sở dữ liệu
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "travel1";
$conn = new mysqli($servername, $username, $password, $dbname);

// Kiểm tra kết nối
if ($conn->connect_error) {
    die("Kết nối không thành công: " . $conn->connect_error);
}

// Xử lý khi nhận được dữ liệu từ form chỉnh sửa
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Lấy dữ liệu từ form
    $placeID = $_POST["edit-placeID"];
    $name = $_POST["edit-name"];
    $price = $_POST["edit-price"];
    $initPrice = $_POST["edit-initPrice"];
    $location = $_POST["edit-location"];

    // Cập nhật thông tin địa điểm vào cơ sở dữ liệu
    $sql = "UPDATE place SET name='$name', price='$price', initPrice='$initPrice', locationName='$location' WHERE placeID='$placeID'";

    if ($conn->query($sql) === TRUE) {
        echo "<script>alert('Cập nhật thông tin địa điểm thành công.'); window.location.href = 'admin.html';</script>";
    } else {
        echo "Lỗi: " . $sql . "<br>" . $conn->error;
    }
}

// Đóng kết nối
$conn->close();
?>