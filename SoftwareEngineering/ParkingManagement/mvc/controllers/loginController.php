<!-- loginController.php -->
<?php
session_start();

require_once '../models/loginModel.php';

function checkLogin($username, $password) {
    if (authenticate($username, $password) == "admin") {
        $_SESSION['username'] = $username;
        $_SESSION['role'] = 'admin';
        header("Location: ../views/admin/admin.php"); 
        exit();
    }
    else if (authenticate($username, $password) == "attendant") { 
        $_SESSION['username'] = $username;
        $_SESSION['role'] = 'attendant';
        header("Location: ../views/attendant/attendant.php"); 
        exit();
    }
    else if ((authenticate($username, $password) == "accountant")) {
        $_SESSION['username'] = $username;
        $_SESSION['role'] = 'accountant';
        header("Location: ../views/accountant/accountant.php"); 
        exit();
    }
    else if ((authenticate($username, $password) == "user")) {
        $_SESSION['username'] = $username;
        $_SESSION['role'] = 'users';
        header("Location: ../views/users/users.php"); 
        exit();
    }
    else {
        return "Tên đăng nhập hoặc mật khẩu không chính xác";
    }
}
?>
