<!-- equipmentController1.php -->
<?php
require_once('../models/equipmentModel.php');

// Xử lý thêm trang thiết bị
if(isset($_POST['saveEquipment'])) {
    $equipmentName = $_POST['equipmentName'];
    $status = $_POST['status'];

    // Gọi hàm từ equipmentModel để thêm trang thiết bị
    $result = addEquipment($equipmentName, $status);

    // Trả về kết quả cho client (có thể là thông báo thành công hoặc thất bại)
    echo $result;
}

// Xử lý xóa trang thiết bị
if(isset($_POST['deleteEquipment'])) {
    $equipmentID = $_POST['deleteEquipment'];

    // Gọi hàm từ equipmentModel để xóa trang thiết bị
    $result = deleteEquipment($equipmentID);

    // Trả về kết quả cho client (có thể là thông báo thành công hoặc thất bại)
    echo $result;
}

// Xử lý cập nhật thông tin trang thiết bị
if(isset($_POST['updateEquipment'])) {
    $equipmentID = $_POST['equipmentId'];
    $equipmentName = $_POST['equipmentName'];
    $status = $_POST['status'];

    // Gọi hàm từ equipmentModel để cập nhật thông tin trang thiết bị
    $result = updateEquipment($equipmentID, $equipmentName, $status);

    // Trả về kết quả cho client (có thể là thông báo thành công hoặc thất bại)
    echo $result;
}
?>
