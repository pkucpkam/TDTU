<?php
session_start();
require_once '../models/paymentModel.php';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $functionName = $_POST['functionName'];
    if ($functionName == "pay") {
        // Lấy số tiền từ form
        $amount = $_POST['amount'];

        // Thực hiện nạp tiền cho tài khoản hiện tại
        $username = $_SESSION['username'];
        $result = addPay($username, $amount);

        // Kiểm tra kết quả và trả về thông báo
        if ($result) {
            echo "Nạp tiền thành công!";
        } else {
            echo "Đã xảy ra lỗi. Vui lòng thử lại sau.";
        }
    }

    if ($functionName == "changeStatus") {
        // Lấy số tiền từ form
        $paymentID = $_POST['paymentID'];

        // Update the status to 'Completed'
        $result = updatePaymentStatus($paymentID, 'Completed');

        // Return 'success' if the update was successful
        if ($result) {
            echo 'success';
        } else {
            echo 'error';
        }
    }

    if ($functionName == 'rejectStatus') {
        $paymentID = $_POST['paymentID'];
        $result = updatePaymentStatus($paymentID, 'Rejected');
        if ($result) {
            echo "success";
        }
    }
    
}



?>