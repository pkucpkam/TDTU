<?php
require_once ('../models/parkingModel.php');
require_once ('../models/usersModel.php');

session_start();

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $functionName = $_POST['functionName'];
    if ($functionName = 'deleteReserve') {
        $username = $_POST['username'];
        
        // Gọi hàm xóa đối tượng tương ứng ở đây
        $result = deleteFromReserveByUsername($username);
        
        // Trả về kết quả cho client
        if ($result) {
            echo 'success';
        } else {
            echo 'error';
        }
    }
}




?>