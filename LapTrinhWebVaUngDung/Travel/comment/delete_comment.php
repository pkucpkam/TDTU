<?php
// Kiểm tra xem yêu cầu POST có chứa commentID không
if(isset($_POST['commentID'])) {
    $commentID = $_POST['commentID'];

    // Thực hiện kết nối đến cơ sở dữ liệu
    $servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "travel1";

    $conn = new mysqli($servername, $username, $password, $dbname);

    // Kiểm tra kết nối
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    // Xóa bình luận từ cơ sở dữ liệu dựa trên commentID
    $sql = "DELETE FROM comments WHERE commentID = '$commentID'";
    if ($conn->query($sql) === TRUE) {
        // Trả về phản hồi JSON với trạng thái "success"
        echo json_encode(array("status" => "success"));
    } else {
        // Trả về phản hồi JSON với trạng thái "error"
        echo json_encode(array("status" => "error"));
    }

    // Đóng kết nối
    $conn->close();
} else {
    // Trả về phản hồi JSON với trạng thái "error" nếu không có commentID được gửi
    echo json_encode(array("status" => "error"));
}
?>
