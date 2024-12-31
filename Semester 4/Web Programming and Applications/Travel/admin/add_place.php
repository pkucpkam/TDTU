<?php
// Kết nối đến cơ sở dữ liệu
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "travel1";
$conn = new mysqli($servername, $username, $password, $dbname);

// Kiểm tra kết nối
if ($conn->connect_error) {
    die("Kết nối không thành công: " . $conn->connect_error);
}

// Xử lý khi nhận được dữ liệu từ form thêm địa điểm
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Lấy dữ liệu từ form
    $name = $_POST["placeName"];
    $price = $_POST["placePrice"];
    $initPrice = $_POST["placeInitPrice"];
    $location = $_POST["placeLocation"];

    // Truy vấn cơ sở dữ liệu để lấy placeID cuối cùng
    $sql_max_id = "SELECT MAX(CAST(SUBSTRING(placeID, 6) AS UNSIGNED)) AS max_id FROM place";
    $result_max_id = $conn->query($sql_max_id);
    $row = $result_max_id->fetch_assoc();
    $max_id = $row['max_id'];

    // Tăng giá trị số lên 1 và tạo placeID mới
    $new_placeID = "place" . ($max_id + 1);

    // Thêm dữ liệu vào cơ sở dữ liệu
    $sql = "INSERT INTO place (placeID, name, price, initPrice, imgLink, locationName) 
            VALUES ('$new_placeID', '$name', '$price', '$initPrice', '', '$location')";
    
    if ($conn->query($sql) === TRUE) {
        
        // Xử lý tải lên ảnh
        $target_dir = "/Final/assets/images/";
        $target_file = $target_dir . $new_placeID . ".jpg";
        $uploadOk = 1;
        $imageFileType = strtolower(pathinfo($_FILES["placeImage"]["name"], PATHINFO_EXTENSION));
        
        // Kiểm tra xem tệp có phải là hình ảnh thực sự hay không
        $check = getimagesize($_FILES["placeImage"]["tmp_name"]);
        if($check !== false) {
            $uploadOk = 1;
        } else {
            echo "<script>alert('File không phải là hình ảnh.');</script>";
            $uploadOk = 0;
        }

        // Kiểm tra kích thước tệp
        if ($_FILES["placeImage"]["size"] > 500000) {
            echo "<script>alert('Tệp của bạn quá lớn.');</script>";
            $uploadOk = 0;
        }

        // Cho phép các loại tệp cụ thể
        if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg"
        && $imageFileType != "gif" ) {
            echo "<script>alert('Xin lỗi, chỉ các tệp JPG, JPEG, PNG & GIF được phép.');</script>";
            $uploadOk = 0;
        }

        // Kiểm tra xem $uploadOk có bị lỗi không
        if ($uploadOk == 0) {
            echo "<script>alert('Xin lỗi, tệp của bạn không được tải lên.');</script>";
        // Nếu mọi thứ đều ổn, cố gắng tải lên tệp
        } else {
            if (move_uploaded_file($_FILES["placeImage"]["tmp_name"], $_SERVER['DOCUMENT_ROOT'] . $target_file)) {
                // Lấy đường dẫn đầy đủ của ảnh
                $image_path = $target_file;

                // Cập nhật cột imgLink trong cơ sở dữ liệu
                $sql_update = "UPDATE place SET imgLink='$image_path' WHERE placeID='$new_placeID'";
                if ($conn->query($sql_update) === TRUE) {
                    echo "<script>alert('Thêm địa điểm thành công!');</script>";
                } else {
                    echo "<script>alert('Lỗi khi cập nhật đường dẫn ảnh: " . $conn->error . "');</script>";
                }
            } else {
                echo "<script>alert('Xảy ra lỗi khi tải lên tệp của bạn.');</script>";
            }
        }
        
    } else {
        echo "<script>alert('Lỗi: " . $sql . "<br>" . $conn->error . "');</script>";
    }
}

// Đóng kết nối
$conn->close();
?>
