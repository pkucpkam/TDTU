<?php
// Khởi động phiên
session_start();

// Kiểm tra xem người dùng đã đăng nhập hay chưa
if (!isset($_SESSION['username'])) {
    header("Location: login.html");
    exit;
}

// Kiểm tra dữ liệu đã được gửi từ form chưa
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Lấy dữ liệu từ form
    $username = $_SESSION['username'];
    $placeID = isset($_POST['placeID']) ? $_POST['placeID'] : '';
    $price = isset($_POST['price']) ? $_POST['price'] : '';
    $checkInDate = isset($_POST['check-in']) ? $_POST['check-in'] : '';
    $checkOutDate = isset($_POST['check-out']) ? $_POST['check-out'] : '';
    $airline = isset($_POST['airline']) ? $_POST['airline'] : '';
    $ticketType = isset($_POST['ticket-type']) ? $_POST['ticket-type'] : '';
    $hotelName = isset($_POST['hotel-name']) ? $_POST['hotel-name'] : '';
    $locationName = isset($_POST['locationName']) ? $_POST['locationName'] : '';
    $numberOfPeople = isset($_POST['number-people']) ? $_POST['number-people'] : '';
    ;
    // Kiểm tra kết nối tới cơ sở dữ liệu MySQL
    $servername = "localhost";
    $db_username = "root"; // Thay username và password phù hợp với cấu hình MySQL của bạn
    $db_password = "";
    $dbname = "travel1";

    // Tạo kết nối
    $conn = new mysqli($servername, $db_username, $db_password, $dbname);

    // Kiểm tra kết nối
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    // Kiểm tra xem username có tồn tại trong bảng users không
    $user_check_query = "SELECT * FROM users WHERE username='$username' LIMIT 1";
    $result = $conn->query($user_check_query);
    if ($result->num_rows == 1) {
        // Username hợp lệ, thực hiện thêm dữ liệu vào bảng bookDetail
        $sql = "INSERT INTO bookdetail (username, placeID, price, checkInDate, checkOutDate, flightBrand, typeTicket, hotelName, location, numberPeople)
                VALUES ('$username', '$placeID', '$price', '$checkInDate', '$checkOutDate', '$airline', '$ticketType', '$hotelName', '$locationName', '$numberOfPeople')";
        if ($conn->query($sql) === TRUE) {
            $conn->close();
            // Hiển thị thông báo thành công và chuyển về trang index.html
            echo "<script>alert('Booking submitted successfully.'); window.location.href = 'booked.php';</script>";
            exit;
        } else {
            echo "Error: " . $sql . "<br>" . $conn->error;
        }
    } else {
        // Username không hợp lệ, in ra thông báo hoặc xử lý lỗi
        echo "Invalid username.";
    }

    // Đóng kết nối
    $conn->close();
}
?>
