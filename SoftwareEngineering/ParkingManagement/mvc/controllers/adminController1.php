<?php
require_once('../models/adminModel.php');

// Xử lý thêm nhân viên
if(isset($_POST['username'])) {
    $username = $_POST['username'];
    $fullname = $_POST['fullname'];
    $phoneNumber = $_POST['phoneNumber'];
    $address = $_POST['address'];
    $email = $_POST['email'];
    $role = $_POST['role'];
    $salary = $_POST['salary'];

    // Gọi hàm từ adminModel để thêm nhân viên
    $result = addStaff($username, $fullname, $phoneNumber, $address, $email, $role, $salary);

    return $result;
    
}


// Xử lý xóa nhân viên
if(isset($_POST['deleteEmployee'])) {
    $employeeId = $_POST['deleteEmployee'];

    // Gọi hàm từ adminModel để xóa nhân viên
    $result = deleteStaff($employeeId);

    // Trả về kết quả cho client (có thể là thông báo thành công hoặc thất bại)
    echo $result;
}

// Xử lý cập nhật thông tin nhân viên
if(isset($_POST['updateEmployee'])) {
    $employeeId = $_POST['employeeId'];
    $fullname = $_POST['fullname'];
    $phoneNumber = $_POST['phoneNumber'];
    $address = $_POST['address'];
    $email = $_POST['email'];
    $salary = $_POST['salary'];

    // Gọi hàm từ adminModel để cập nhật thông tin nhân viên
    $result = updateStaff($employeeId, $fullname, $phoneNumber, $address, $email, $salary);

    // Trả về kết quả cho client (có thể là thông báo thành công hoặc thất bại)
    echo $result;
}

