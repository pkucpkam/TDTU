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

// Lấy dữ liệu từ form chỉnh sửa
$edit_username = $_POST['edit-username'];
$edit_name = $_POST['edit-name'];
$edit_phone = $_POST['edit-phone'];
$edit_email = $_POST['edit-email'];

// Câu lệnh SQL để cập nhật thông tin người dùng
$sql = "UPDATE users SET name='$edit_name', phoneNumber='$edit_phone', email='$edit_email' WHERE username='$edit_username'";

// Thực thi câu lệnh SQL
if ($conn->query($sql) === TRUE) {
    echo "<script>alert('Thông tin người dùng đã được cập nhật thành công.'); window.location.href = 'admin.html';</script>";

} else {
    echo "Lỗi: " . $sql . "<br>" . $conn->error;
}

// Đóng kết nối
$conn->close();
?>
