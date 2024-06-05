<?php
require_once '../models/usersModel.php';

// Xử lý thêm khách hàng
if (isset($_POST['saveUser'])) {
    $username = $_POST['username'];
    $fullname = $_POST['fullname'];
    $phoneNumber = $_POST['phoneNumber'];
    $email = $_POST['email'];
    $IDCard = $_POST['IDCard'];
    $accountBalance = $_POST['accountBalance'];

    $result = addUser($username, $fullname, $phoneNumber, $email, $IDCard, $accountBalance);
    
    if ($result) {
        echo "success";
    }
    else {
        echo "error";
    }
}

// Xử lý cập nhật thông tin khách hàng
if (isset($_POST['updateUser'])) {
    // Trích xuất dữ liệu từ yêu cầu POST
    $username = $_POST['username'];
    $fullname = $_POST['fullname'];
    $phoneNumber = $_POST['phoneNumber'];
    $email = $_POST['email'];
    $IDCard = $_POST['IDCard'];
    $accountBalance = $_POST['accountBalance'];

    // Gọi hàm cập nhật thông tin khách hàng từ model
    $result = updateUser($username, $fullname, $phoneNumber, $email, $IDCard, $accountBalance);
    
    // Kiểm tra kết quả và gửi phản hồi về máy khách
    if ($result) {
        // Phản hồi thành công
        echo "ok";
    } else {
        // Phản hồi lỗi
        echo "Không thể cập nhật thông tin khách hàng. Vui lòng thử lại sau.";
    }
}



// Xử lý xóa khách hàng
if (isset($_POST['deleteUser'])) {
    $username = $_POST['usernameToDelete'];

    $result = deleteUser($username);
    echo $result;
    // Kiểm tra kết quả và thực hiện các hành động phù hợp, ví dụ: hiển thị thông báo thành công hoặc lỗi
}


if (isset($_POST['editUser'])) {
    $username = $_POST['username'];
    $fullname = $_POST['fullname'];
    $phoneNumber = $_POST['phoneNumber'];
    $email = $_POST['email'];

    $result = editInformation($username, $fullname, $phoneNumber, $email);

    if ($result) {
        echo "ok";
    }
    else {
        return false;
    }
}
?>
