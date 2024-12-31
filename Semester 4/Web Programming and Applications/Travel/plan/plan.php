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

// Chọn cơ sở dữ liệu
$conn->select_db($database_travel);

// Xử lý khi form được gửi đi 
if ($_SERVER["REQUEST_METHOD"] == "POST") {
  // Lấy thông tin từ form
  $trip_name = $_POST['trip_name'];
  $location = $_POST['location'];
  $check_in_date = $_POST['check_in_date'];
  $check_out_date = $_POST['check_out_date'];
  $flight = $_POST['flight'];
  $hotel_name = $_POST['hotel_name'];
  // Kiểm tra xem các mảng title và description đã tồn tại và không rỗng
  if (isset($_POST['title']) && isset($_POST['description']) && !empty($_POST['title']) && !empty($_POST['description'])) {
    $titles = $_POST['title'];
    $descriptions = $_POST['description'];
  } else {
    $titles = [];
    $descriptions = [];
  }


  // Lấy tên người dùng từ session
  $username = $_SESSION['username'];

  // Kiểm tra xem kế hoạch cũ đã tồn tại không
  $check_existing_plan_sql = "SELECT * FROM plan WHERE id = '$username' AND planName = '$trip_name'";
  $result = $conn->query($check_existing_plan_sql);

  if ($result->num_rows > 0) {
    // Nếu kế hoạch đã tồn tại, thực hiện xóa
    $delete_existing_plan_sql = "DELETE FROM plan WHERE username = '$username' AND planName = '$trip_name'";
    if ($conn->query($delete_existing_plan_sql) === TRUE) {
      echo "Kế hoạch du lịch cũ đã được xóa.";
    } else {
      echo "Lỗi khi xóa kế hoạch cũ: " . $conn->error;
    }
  }

  // Thêm kế hoạch mới vào bảng 'plan' trong cơ sở dữ liệu Travel
  $insert_plan_sql = "INSERT INTO $database_travel.plan (username, planName, checkInDate, checkOutDate, flight, hotelName, location) 
                        VALUES ('$username', '$trip_name', '$check_in_date', '$check_out_date', '$flight', '$hotel_name', '$location')";
  if ($conn->query($insert_plan_sql) === TRUE) {
    // Lấy planID 
    $planID = $conn->insert_id;

    // Chọn cơ sở dữ liệu UserPlan
    $conn->select_db($database_userplan);

    // Kiểm tra xem có hoạt động nào được thêm không
    if (!empty($titles) && !empty($descriptions)) {
      // Tạo bảng mới cho chi tiết kế hoạch du lịch trong cơ sở dữ liệu UserPlan
      $table_name = "$planID" . "_" . $trip_name . "_Detail"; // Tên bảng chi tiết kế hoạch
      $table_name = str_replace(" ", "_", $table_name); // Loại bỏ khoảng trắng
      $table_name = preg_replace("/[^A-Za-z0-9_]/", "", $table_name); // Loại bỏ các ký tự không hợp lệ

      // Kiểm tra xem đã tồn tại database đó chưa 
      $table_check_sql = "SHOW TABLES LIKE '$table_name'";
      $table_check_result = $conn->query($table_check_sql);

      if ($table_check_result->num_rows > 0) {
        // Nếu bảng đã tồn tại, thực hiện truy vấn để xóa bảng
        $drop_table_sql = "DROP TABLE $table_name";
        if ($conn->query($drop_table_sql) === TRUE) {
          echo "Bảng chi tiết kế hoạch đã tồn tại và đã được xóa.<br>";
        } else {
          echo "Lỗi khi xóa bảng: " . $conn->error . "<br>";
        }
      }

      $create_table_sql = "CREATE TABLE $table_name (
                                    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                    title VARCHAR(255) NOT NULL,
                                    description TEXT NOT NULL
                                )";

      if ($conn->query($create_table_sql) === TRUE) {
        
        // Thêm các hoạt động vào bảng chi tiết kế hoạch du lịch trong cơ sở dữ liệu UserPlan
        for ($i = 0; $i < count($titles); $i++) {
          // Lấy tiêu đề và mô tả từ mảng theo chỉ mục $i
          $title = $conn->real_escape_string($titles[$i]);
          $description = $conn->real_escape_string($descriptions[$i]);

          // Tạo truy vấn SQL để thêm hoạt động vào bảng chi tiết kế hoạch
          $insert_activity_sql = "INSERT INTO $table_name (title, description) 
                                            VALUES ('$title', '$description')";

          // Thực thi truy vấn
          if ($conn->query($insert_activity_sql) !== TRUE) {
            echo "Lỗi khi thêm hoạt động: " . $conn->error;
          }
        }
      } else {
        echo "Lỗi khi tạo bảng chi tiết kế hoạch: " . $conn->error;
      }
    } else {

    }
  } else {
    echo "Lỗi khi thêm kế hoạch du lịch: " . $conn->error;
  }
  echo "<script>alert('PLanning submitted successfully.'); window.location.href = 'showplan.php';</script>";
  exit;
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

  <link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Bungee+Spice&display=swap" rel="stylesheet">
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
      font-family: ;
    }

    .content1 {
      padding: 2%;
    }

  </style>
</head>

<body>
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
                  <input type="text" name="trip_name" required />
                </div>

              </div>
            </div>

            <p>Location</p>
            <div class="form__group">
              <div class="input__content">
                <div class="input__group">
                  <input type="text" name="location" required />
                </div>

              </div>
            </div>

            <p>Check in</p>
            <div class="form__group">
              <div class="input__content">
                <div class="input__group">
                  <input type="date" name="check_in_date" required />
                </div>
              </div>
            </div>

            <p>Check Out</p>
            <div class="form__group">
              <div class="input__content">
                <div class="input__group">
                  <input type="date" name="check_out_date" required />
                </div>
              </div>
            </div>


            <p>Flight</p>
            <div class="form__group">
              <div class="input__content">
                <div class="input__group">
                  <input type="text" name="flight" required />
                </div>
              </div>
            </div>

            <p>Hotel</p>
            <div class="form__group">
              <div class="input__content">
                <div class="input__group">
                  <input type="text" name="hotel_name" required />
                </div>

              </div>
            </div>


            <h1 class="content-center">DETAIL PLAN</h1>
            <div class="input_fields_wrap">
              <button class="add_field_button">Add</button>
            </div>
            <br>
            <input id="submit-button" type="submit" value="Save" class="btn btn-primary">
        </form>
      </section>


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
      <script>
        var a = document.getElementById("loginBtn");
        var b = document.getElementById("registerBtn");
        var x = document.getElementById("login");
        var y = document.getElementById("register");
        function login() {
          x.style.left = "4px";
          y.style.right = "-520px";
          a.className += " white-btn";
          b.className = "btn";
          x.style.opacity = 1;
          y.style.opacity = 0;
        }
        function register() {
          x.style.left = "-510px";
          y.style.right = "5px";
          a.className = "btn";
          b.className += " white-btn";
          x.style.opacity = 0;
          y.style.opacity = 1;
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



</body>

</html>