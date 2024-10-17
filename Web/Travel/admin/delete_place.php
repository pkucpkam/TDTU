<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "travel1";

// Kết nối đến cơ sở dữ liệu
$conn = new mysqli($servername, $username, $password, $dbname);

if (!$conn) {
    die("Kết nối không thành công: " . mysqli_connect_error());
}

// Xử lý khi nhận được yêu cầu xóa địa điểm
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Lấy ID của địa điểm cần xóa
    $placeID_to_delete = $_POST["deletePlaceID"];

    // Kiểm tra xem có bất kỳ bảng nào liên quan đến địa điểm này không
    $tables_to_check = array("comments", "bookdetail", "hotdeals");

    foreach ($tables_to_check as $table) {
        // Kiểm tra xem địa điểm có tồn tại trong bảng không
        $check_query = "SELECT * FROM $table WHERE placeID='$placeID_to_delete'";
        $check_result = mysqli_query($conn, $check_query);

        if (mysqli_num_rows($check_result) > 0) {
            // Nếu có, xóa các bản ghi liên quan trong bảng này
            $delete_query = "DELETE FROM $table WHERE placeID='$placeID_to_delete'";
            if (!mysqli_query($conn, $delete_query)) {
                echo "Lỗi khi xóa bản ghi từ bảng $table: " . mysqli_error($conn);
            }
        }
    }

    // Sau khi xóa dữ liệu từ các bảng liên quan, có thể xóa địa điểm từ bảng place
    $delete_place_query = "DELETE FROM place WHERE placeID='$placeID_to_delete'";
    if (mysqli_query($conn, $delete_place_query)) {
    } else {
        echo "Lỗi khi xóa địa điểm: " . mysqli_error($conn);
    }
}

// Đóng kết nối
mysqli_close($conn);
?>