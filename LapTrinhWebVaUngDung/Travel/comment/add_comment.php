<?php
// Kết nối đến cơ sở dữ liệu (sử dụng các thông số kết nối của bạn)
session_start();

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "travel1";

// Tạo kết nối
$conn = new mysqli($servername, $username, $password, $dbname);

// Kiểm tra kết nối
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Kiểm tra xem session có biến 'username' hay không
if (isset($_SESSION['username'])) {
    // Nếu có biến 'username', xử lý yêu cầu POST từ form
    if (isset($_POST['comment']) && isset($_POST['placeID'])) {
        $username = $_SESSION['username']; // Lấy username từ session, không cần lấy từ form
        $comment = $_POST['comment'];
        $placeID = $_POST['placeID'];

        // Kiểm tra xem người dùng đã đặt địa điểm này hay chưa
        $booking_check_query = "SELECT * FROM bookdetail WHERE username = '$username' AND placeID = '$placeID'";
        $booking_result = $conn->query($booking_check_query);
        
        if ($booking_result->num_rows > 0) {
            // Truy vấn cơ sở dữ liệu để chèn comment mới
            $sql = "INSERT INTO comments (username, content, placeID) VALUES ('$username', '$comment', '$placeID')";
            if ($conn->query($sql) === TRUE) {
                // Nếu chèn thành công, hiển thị lại comment mới và các comment khác của địa điểm tương ứng
                $sql_select_comments = "SELECT comments.content, users.name FROM comments JOIN users ON comments.username = users.username WHERE comments.placeID = '$placeID'";
                $result = $conn->query($sql_select_comments);
                if ($result->num_rows > 0) {
                    // Hiển thị các comment của địa điểm tương ứng
                    while($row = $result->fetch_assoc()) {
                        echo '<div class="comment">';
                        echo '<div class="user-info">';
                        echo '<p class="name-comment" ><strong>Name: </strong><span class="username-comments">' . $row["name"] . '</span></p>';
                        echo '</div>';
                        echo '<div class="comment-content">';
                        echo '<p><strong>Comment: </strong>' . $row["content"] . '</p>';
                        echo '</div>';
                        echo '</div>';
                    }
                } else {
                    echo "No comments for this place.";
                }
            } else {
                echo "Error: " . $sql . "<br>" . $conn->error;
            }
        } else {
            $sql_select_comments = "SELECT comments.content, users.name FROM comments JOIN users ON comments.username = users.username WHERE comments.placeID = '$placeID'";
                $result = $conn->query($sql_select_comments);
                if ($result->num_rows > 0) {
                    // Hiển thị các comment của địa điểm tương ứng
                    while($row = $result->fetch_assoc()) {
                        echo '<div class="comment">';
                        echo '<div class="user-info">';
                        echo '<p class="name-comment" ><strong>Name: </strong><span class="username-comments">' . $row["name"] . '</span></p>';
                        echo '</div>';
                        echo '<div class="comment-content">';
                        echo '<p><strong>Comment: </strong>' . $row["content"] . '</p>';
                        echo '</div>';
                        echo '</div>';
                    }
                }
            echo '<p class="warning">You need to book this place before commenting.</p>';
        }
    }
} else {
    if (isset($_POST['placeID'])) {
        $placeID = $_POST['placeID'];
        $sql_select_comments = "SELECT comments.content, users.name FROM comments JOIN users ON comments.username = users.username WHERE comments.placeID = '$placeID'";
        $result = $conn->query($sql_select_comments);
        if ($result->num_rows > 0) {
            // Hiển thị các comment của địa điểm tương ứng
            while($row = $result->fetch_assoc()) {
                echo '<div class="comment">';
                echo '<div class="user-info">';
                echo '<p class="name-comment" ><strong>Name: </strong><span class="username-comments">' . $row["name"] . '</span></p>';
                echo '</div>';
                echo '<div class="comment-content">';
                echo '<p><strong>Comment: </strong>' . $row["content"] . '</p>';
                echo '</div>';
                echo '</div>';
            }
        } else {
            echo '<p class="no-comments">No comments available for this place.</p>';
        }
        echo '<p class="warning">Log in to comment.</p>';
    } else {
        
    }
}

// Đóng kết nối
$conn->close();
?>