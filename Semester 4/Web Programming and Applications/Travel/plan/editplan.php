<?php
session_start();

// Kiểm tra người dùng đã đăng nhập chưa
if (!isset($_SESSION['username'])) {
    // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
    header("Location: /Final/login/login.php");
    exit();
}

// Kết nối đến cơ sở dữ liệu MySQL
$servername = "localhost";
$username = "root";
$password = "";
$database_travel = "travel1";
$database_userplan = "userplan";

$conn = new mysqli($servername, $username, $password);

// Kiểm tra kết nối
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}


//Lấy dữ liệu từ biến session
if ($_SERVER["REQUEST_METHOD"] == "GET") {
    $placeName = $_GET['edit_plan'];
    $planID = $_GET['edit_id'];
} else {
    $placeName = $_POST['edit_plan'];
    $planID = $_POST['edit_id'];
}




//Lay du lieu tu userplan
$conn->select_db($database_userplan);

// Tên bảng chi tiết kế hoạch
$table_name = $planID . "_" . $placeName . "_detail";
$table_name = str_replace(" ", "_", $table_name);
$table_name = preg_replace("/[^A-Za-z0-9_]/", "", $table_name);
$table_name = strtolower($table_name);

// Truy vấn SQL để lấy dữ liệu từ bảng chi tiết kế hoạch
$table_check_sql = "SHOW TABLES LIKE '$table_name'";
$table_check_result = $conn->query($table_check_sql);

// Mảng để lưu dữ liệu hoạt động
$activities = array();

// Kiểm tra xem bảng tồn tại hay không
if ($table_check_result->num_rows > 0) {
    $select_data_sql = "SELECT title, description FROM $table_name";
    $result = $conn->query($select_data_sql);

    // Kiểm tra và lưu trữ dữ liệu vào mảng
    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            $activities[] = $row;
        }
    } else {
        echo "Không có dữ liệu hoạt động trong bảng.";
    }
} else {

}


//Lay du lieu tu travel
// Chọn cơ sở dữ liệu
$conn->select_db($database_travel);

$sql = "SELECT * FROM plan WHERE id = '$planID'";


$result = $conn->query($sql);


// Kiểm tra xem có dữ liệu trả về không
if ($result->num_rows > 0) {
    // Lấy dữ liệu từ kết quả truy vấn
    $row = $result->fetch_assoc();
    $trip_name = $row['planName'];
    $locationName = $row['location'];
    $check_in_date = $row['checkInDate'];
    $check_out_date = $row['checkOutDate'];
    $flight = $row['flight'];
    $hotel_name = $row['hotelName'];
} else {
    echo "Không tìm thấy thông tin kế hoạch du lịch.";
}

// Xử lý khi form được gửi đi
// Xử lý khi form được gửi đi
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Lấy thông tin từ form
    $trip_name = $_POST['trip_name'];
    $location = $_POST['location'];
    $check_in_date = $_POST['check_in_date'];
    $check_out_date = $_POST['check_out_date'];
    $flight = $_POST['flight'];
    $hotel_name = $_POST['hotel_name'];

    // Kiểm tra xem kế hoạch đã tồn tại trong cơ sở dữ liệu Travel chưa
    $check_existing_plan_sql = "SELECT * FROM plan WHERE id = '$planID'";
    $result = $conn->query($check_existing_plan_sql);

    if ($result->num_rows > 0) {
        // Nếu kế hoạch đã tồn tại, thực hiện cập nhật thông tin
        $update_plan_sql = "UPDATE $database_travel.plan 
                            SET checkInDate = '$check_in_date', 
                                checkOutDate = '$check_out_date', 
                                flight = '$flight', 
                                hotelName = '$hotel_name', 
                                location = '$location' 
                            WHERE id = '$planID'";

        if ($conn->query($update_plan_sql) === TRUE) {
            //echo "Kế hoạch du lịch đã được cập nhật thành công!";
        } else {
            echo "Lỗi khi cập nhật kế hoạch du lịch: " . $conn->error;
        }
    } else {
        echo "Không tìm thấy kế hoạch du lịch.";
    }

    // Tạo hoặc cập nhật bảng chi tiết kế hoạch du lịch
    // chuyển qua database useplan 
    if (isset($_POST['title']) && isset($_POST['description'])) {
        $titles = $_POST['title'];
        $descriptions = $_POST['description'];

        $conn->select_db($database_userplan);
        // Tên bảng chi tiết kế hoạch
        $table_name = $planID . "_" . $placeName . "_detail";
        $table_name = str_replace(" ", "_", $table_name);
        $table_name = preg_replace("/[^A-Za-z0-9_]/", "", $table_name);

        // Kiểm tra xem bảng chi tiết kế hoạch đã tồn tại chưa
        $table_check_sql = "SHOW TABLES LIKE '$table_name'";
        $table_check_result = $conn->query($table_check_sql);

        if ($table_check_result->num_rows > 0) {
            // Nếu bảng đã tồn tại, xóa bảng cũ và tạo bảng mới
            $drop_table_sql = "DROP TABLE $table_name";
            if ($conn->query($drop_table_sql) === TRUE) {
                //echo "Bảng chi tiết kế hoạch đã tồn tại và đã được xóa.";
                // Tạo bảng mới
                $create_table_sql = "CREATE TABLE $table_name (
                                id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                title VARCHAR(255) NOT NULL,
                                description TEXT NOT NULL
                            )";
                if ($conn->query($create_table_sql) === TRUE) {
                    //echo "Bảng chi tiết kế hoạch đã được tạo mới thành công!";
                    // Thêm dữ liệu vào bảng chi tiết kế hoạch mới
                    $insert_data_sql = "INSERT INTO $table_name (title, description) VALUES ";
                    for ($i = 0; $i < count($titles); $i++) {
                        $title = $conn->real_escape_string($titles[$i]);
                        $description = $conn->real_escape_string($descriptions[$i]);
                        $insert_data_sql .= "('$title', '$description'), ";
                    }
                    $insert_data_sql = rtrim($insert_data_sql, ", "); // Loại bỏ dấu phẩy cuối cùng
                    if ($conn->query($insert_data_sql) === TRUE) {
                        //echo "Dữ liệu đã được thêm vào bảng chi tiết kế hoạch mới.";
                    } else {
                        echo "Lỗi khi thêm dữ liệu vào bảng chi tiết kế hoạch mới: " . $conn->error;
                    }
                } else {
                    echo "Lỗi khi tạo bảng chi tiết kế hoạch mới: " . $conn->error;
                }
            } else {
                echo "Lỗi khi xóa bảng chi tiết kế hoạch cũ: " . $conn->error;
            }
        } else {
            // Nếu bảng chưa tồn tại, tạo bảng mới và thêm dữ liệu
            $create_table_sql = "CREATE TABLE $table_name (
                            id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                            title VARCHAR(255) NOT NULL,
                            description TEXT NOT NULL
                        )";
            if ($conn->query($create_table_sql) === TRUE) {
                //echo "Bảng chi tiết kế hoạch đã được tạo mới thành công!";
                // Thêm dữ liệu vào bảng chi tiết kế hoạch mới
                $insert_data_sql = "INSERT INTO $table_name (title, description) VALUES ";
                for ($i = 0; $i < count($titles); $i++) {
                    $title = $conn->real_escape_string($titles[$i]);
                    $description = $conn->real_escape_string($descriptions[$i]);
                    $insert_data_sql .= "('$title', '$description'), ";
                }
                $insert_data_sql = rtrim($insert_data_sql, ", "); // Loại bỏ dấu phẩy cuối cùng
                if ($conn->query($insert_data_sql) === TRUE) {
                    //echo "Dữ liệu đã được thêm vào bảng chi tiết kế hoạch mới.";
                } else {
                    echo "Lỗi khi thêm dữ liệu vào bảng chi tiết kế hoạch mới: " . $conn->error;
                }
            } else {
                echo "Lỗi khi tạo bảng chi tiết kế hoạch mới: " . $conn->error;
            }
            echo "<script>alert('Successful plan update.'); window.location.href = 'showplan.php';</script>";
        }
    } else {
        $conn->select_db($database_userplan);
        // Tên bảng chi tiết kế hoạch
        $table_name = $planID . "_" . $placeName . "_detail";
        $table_name = str_replace(" ", "_", $table_name);
        $table_name = preg_replace("/[^A-Za-z0-9_]/", "", $table_name);

        // Kiểm tra xem bảng chi tiết kế hoạch đã tồn tại chưa
        $table_check_sql = "SHOW TABLES LIKE '$table_name'";
        $table_check_result = $conn->query($table_check_sql);

        if ($table_check_result->num_rows > 0) {
            $drop_table_sql = "DROP TABLE $table_name";
            if ($conn->query($drop_table_sql) === TRUE) {
                //echo "Bảng chi tiết kế hoạch đã tồn tại và đã được xóa.";
                echo "<script>alert('Successful plan update.'); window.location.href = 'showplan.php';</script>";
            } else {
                echo "Lỗi khi xóa bảng chi tiết kế hoạch: " . $conn->error;
            }
        }
    }
    echo "<script>alert('Successful plan update.'); window.location.href = 'showplan.php';</script>";

}

$conn->close();
?>


<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Travel Planning</title>

    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />

    <script src="https://unpkg.com/boxicons@2.1.4/dist/boxicons.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/remixicon@3.4.0/fonts/remixicon.css" rel="stylesheet" />

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css">

    <style>
        body {
            background: linear-gradient(135deg, #71b7e6, #9b59b6);
        }

        /* Reset CSS */
        /* CSS cho phần lên kế hoạch du lịch */
        .booking__container {
            max-width: 800px;
            /* Đặt chiều rộng tối đa của container */
            margin: 0 auto;
            /* Canh giữa container */
            padding: 20px;
            /* Thêm khoảng cách padding */
            background-color: #f9f9f9;
            /* Màu nền */
            border-radius: 8px;
            /* Bo góc */
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            /* Đổ bóng */
        }

        .booking__container h1 {
            font-size: 24px;
            /* Kích thước font cho tiêu đề */
            margin-bottom: 20px;
            /* Thêm khoảng cách dưới cho tiêu đề */
        }

        .form_content {
            margin-bottom: 10px;
            /* Thêm khoảng cách dưới cho phần nội dung của form */
        }

        .form__group {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
            margin-top: -10px;
            /* Thêm khoảng cách dưới cho mỗi nhóm input */
        }

        .form__group span {
            margin-right: 10px;
            /* Khoảng cách bên phải của biểu tượng */
        }

        .input__content {
            flex: 1;
            /* Định cỡ tự động của phần input */
        }

        .input__group {
            position: relative;
            /* Đặt vị trí tương đối để chứa input */
        }

        .input__group input[type="text"],
        .input__group input[type="date"],
        .input__group textarea {
            width: calc(100% - 30px);
            /* Đặt chiều rộng của input */
            padding: 10px;
            /* Thêm padding */
            border-radius: 5px;
            /* Bo góc */
            border: 1px solid #ccc;
            /* Đặt border */
            transition: border-color 0.3s ease;
            /* Hiệu ứng chuyển đổi */
        }

        .input__group textarea {
            height: 100px;
            /* Độ cao của textarea */
        }

        .input__content p {
            margin: 5px 0;
            /* Thêm khoảng cách trên và dưới cho phần mô tả */
        }

        .add_field_button {
            background-color: #007bff;
            /* Màu nền của nút Add */
            color: #fff;
            /* Màu chữ của nút Add */
            border: none;
            /* Loại bỏ border */
            padding: 10px 20px;
            /* Thêm padding */
            border-radius: 5px;
            /* Bo góc */
            cursor: pointer;
            /* Biến con trỏ thành pointer khi rê chuột */
        }

        .add_field_button:hover {
            background-color: #0056b3;
            /* Màu nền của nút Add khi hover */
        }

        .input_fields_wrap {
            margin-bottom: 20px;
            /* Thêm khoảng cách dưới cho phần Detailed Plan */
        }

        .add_field_button {
            background-color: #007bff;
            /* Màu nền của nút Add Activity */
            color: #fff;
            /* Màu chữ của nút Add Activity */
            border: none;
            /* Loại bỏ border */
            padding: 10px 20px;
            /* Thêm padding */
            border-radius: 5px;
            /* Bo góc */
            cursor: pointer;
            /* Biến con trỏ thành pointer khi rê chuột */
        }

        .add_field_button:hover {
            background-color: #0056b3;
            /* Màu nền của nút Add Activity khi hover */
        }

        .input_fields_wrap input[type="text"],
        .input_fields_wrap textarea {
            width: calc(100% - 10px);
            /* Đặt chiều rộng của input và textarea */
            padding: 10px;
            /* Thêm padding */
            border: 1px solid #ccc;
            /* Đặt border */
            border-radius: 5px;
            /* Bo góc */
            transition: border-color 0.3s ease;
            /* Hiệu ứng chuyển đổi */
        }

        .input_fields_wrap textarea {
            height: 100px;
            /* Đặt chiều cao của textarea */
        }

        .input_fields_wrap input[type="text"]:focus,
        .input_fields_wrap textarea:focus {
            border-color: #007bff;
            /* Màu border khi input được focus */
        }

        .input_fields_wrap a.remove_field {
            color: #dc3545;
            /* Màu chữ của nút Remove */
            margin-left: 10px;
            /* Khoảng cách bên trái của nút Remove */
            cursor: pointer;
            /* Biến con trỏ thành pointer khi rê chuột */
        }

        .input_fields_wrap a.remove_field:hover {
            text-decoration: underline;
            /* Gạch chân nút Remove khi hover */
        }

        #submit-button {
            width: 100%;
        }

        .content-center {
            text-align: center;
        }

        .content1 {
            padding: 2%;
        }
    </style>
</head>

<body>
    <!-- Form lập kế hoạch du lịch -->
    <section>
        <div class="content1">
            <h1 class="content-center">TRAVEL PLAN</h1>
            <section class="section__container booking__container">

                <form id="flight" method="POST" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?>">
                    <div class="form_content">
                        <p>Name of the trip</p>
                        <div class="form__group">

                            <div class="input__content">
                                <div class="input__group">
                                    <input type="text" name="trip_name" value="<?php echo $trip_name; ?>" required />
                                </div>
                            </div>
                        </div>
                        <p>Location</p>
                        <div class="form__group">
                            <div class="input__content">
                                <div class="input__group">
                                    <input type="text" name="location" value="<?php echo "$locationName"; ?>"
                                        required />
                                </div>
                            </div>
                        </div>

                        <p>Check in</p>
                        <div class="form__group">
                            <div class="input__content">
                                <div class="input__group">
                                    <input type="date" name="check_in_date" value="<?php echo $check_in_date; ?>"
                                        required />
                                </div>
                            </div>
                        </div>

                        <p>Check Out</p>
                        <div class="form__group">
                            <div class="input__content">
                                <div class="input__group">
                                    <input type="date" name="check_out_date" value="<?php echo $check_out_date; ?>"
                                        required />
                                </div>
                            </div>
                        </div>

                        <p>Flight</p>
                        <div class="form__group">
                            <div class="input__content">
                                <div class="input__group">
                                    <input type="text" name="flight" value="<?php echo $flight; ?>" required />
                                </div>
                            </div>
                        </div>

                        <p>Hotel</p>
                        <div class="form__group">
                            <div class="input__content">
                                <div class="input__group">
                                    <input type="text" name="hotel_name" value="<?php echo $hotel_name; ?>" required />
                                </div>
                            </div>
                        </div>

                        <div>
                            <h1 class="content-center">DETAIL PLAN</h1>
                            <div class="input_fields_wrap">
                                <button class="add_field_button">Add</button>
                                <?php foreach ($activities as $activity): ?>
                                    <div>
                                        <label>Title:</label>
                                        <input type="text" name="title[]" value="<?php echo $activity['title']; ?>" />
                                        <label>Description:</label>
                                        <textarea name="description[]"><?php echo $activity['description']; ?></textarea>
                                        <a href="#" class="remove_field">Remove</a>
                                    </div>
                                <?php endforeach; ?>

                            </div>
                        </div>
                        <input type="hidden" name="edit_id" value="<?php echo $planID; ?>">
                        <input type="hidden" name="edit_plan" value="<?php echo $trip_name; ?>">

                        <input id="submit-button" type="submit" value="Save" class="btn btn-primary">
                </form>
            </section>

            <!-- Form thêm chi tiết chuyến đi -->

            <script>
                function myMenuFunction() {
                    var i = document.getElementById("navMenu");
                    if (i.className === "nav-menu") {
                        i.className += " responsive";
                    } else {
                        i.className = "nav-menu";
                    }
    }
            </script>

            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
            <script>
                $(document).ready(function () {
                    var max_fields = 10; // Số lượng hoạt động tối đa có thể thêm
                    var wrapper = $(".input_fields_wrap"); // Containing div
                    var add_button = $(".add_field_button"); // Add button ID

                    var x = 0; // Đếm số lượng hoạt động đã thêm
                    $(add_button).click(function (e) {
                        e.preventDefault();
                        if (x < max_fields || x == 0) { // Kiểm tra nếu không có trường nhập liệu nào hoặc chưa đạt giới hạn
                            x++;
                            $(wrapper).append('<div><label>Title:</label><input type="text" name="title[]"/><label>Description:</label><textarea name="description[]"></textarea><a href="#" class="remove_field">Remove</a></div>'); // Add field HTML
                        }
                    });

                    $(wrapper).on("click", ".remove_field", function (e) { // Bấm vào xóa link
                        e.preventDefault(); $(this).parent('div').remove(); x--;
                    })
    });
            </script>

        </div>
    </section>
</body>

</html>