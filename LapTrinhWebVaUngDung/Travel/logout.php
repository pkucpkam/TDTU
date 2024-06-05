<?php
// Khởi động phiên
session_start();

// Hủy bỏ phiên làm việc
session_destroy();

// Chuyển hướng đến trang login
header("Location: login/login.php");
exit;
?>
