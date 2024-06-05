<?php

require_once('../models/noticeModel.php');

//  Handle adding a new notice
if(isset($_POST['typeNotice']) && isset($_POST['target']) && isset($_POST['message'])) {
    $typeNotice = $_POST['typeNotice'];
    $target = $_POST['target'];
    $message = $_POST['message'];

    $result = addNotice($typeNotice, $target, $message);

    // Return the result to the client (can be success message or error)
    echo $result ? "Thêm thông báo thành công!" : "Đã xảy ra lỗi khi thêm thông báo!";
}


// Handle deleting a notice
if(isset($_POST['deleteNotice'])) {
    $noticeID = $_POST['deleteNotice'];

    $result = deleteNotice($noticeID);

    // Return the result to the client (can be success message or error)
    echo $result ? "Xóa thông báo thành công!" : "Đã xảy ra lỗi khi xóa thông báo!";
}

?>
