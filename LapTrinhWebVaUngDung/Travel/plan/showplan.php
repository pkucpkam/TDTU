<?php
session_start();

if (!isset($_SESSION['username'])) {
  header("Location: \Final\login\login.php");
  exit;
}

$username = $_SESSION['username'];

$servername = "localhost";
$db_username = "root"; // Thay thế bằng username của bạn
$db_password = ""; // Thay thế bằng mật khẩu của bạn
$database = "travel1";

$conn = new mysqli($servername, $db_username, $db_password, $database);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
  if (isset($_POST['delete_plan']) && isset($_POST['delete_id'])) {
    $planToDelete = $_POST['delete_plan'];
    $idToDelete = $_POST['delete_id'];
    // Xóa dữ liệu kế hoạch du lịch từ cơ sở dữ liệu
    $deleteSql = "DELETE FROM travel1.plan WHERE planName = '$planToDelete' AND id = $idToDelete AND username = '$username'";
    if ($conn->query($deleteSql) === TRUE) {
      // Kết nối đến cơ sở dữ liệu khác (ví dụ: travel2)
      $conn_other_db = new mysqli($servername, $db_username, $db_password, "userplan");

      // Kiểm tra kết nối
      if ($conn_other_db->connect_error) {
        die("Connection failed: " . $conn_other_db->connect_error);
      }

      $tableToDelete = (string) $idToDelete . "_" . $planToDelete . "_detail";
      $tableToDelete = str_replace(" ", "_", $tableToDelete);
      $tableToDelete = preg_replace("/[^A-Za-z0-9_]/", "", $tableToDelete);
      // Thực hiện câu lệnh xóa trong cơ sở dữ liệu khác
      $tableCheckSql = "SHOW TABLES LIKE '$tableToDelete'";
      $result = $conn_other_db->query($tableCheckSql);

      if ($result->num_rows > 0) {
        // Nếu bảng tồn tại, thực hiện xóa
        $deleteOtherSql = "DROP TABLE $tableToDelete";
        if ($conn_other_db->query($deleteOtherSql) === TRUE) {
          // Xóa bảng thành công từ cơ sở dữ liệu khác
          header("Refresh:0");
        } else {
          echo "Lỗi khi xóa bảng từ cơ sở dữ liệu khác: " . $conn_other_db->error;
        }
      } else {

      }
      // Đóng kết nối với cơ sở dữ liệu khác
      $conn_other_db->close();
    } else {
      echo "Lỗi khi xóa kế hoạch du lịch: " . $conn->error;
    }
  }
}



//Lấy thông tin
$_SESSION['placeNameNow'] = array(); // Khởi tạo một mảng để lưu trữ các planName và planID

$sql = "SELECT id, planName FROM plan WHERE username = '$username'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
  while ($row = $result->fetch_assoc()) {
    // Lưu planID và planName vào mảng
    $_SESSION['placeNameNow'][] = array(
      'planID' => $row["id"],
      'planName' => $row["planName"]
    );
  }
}

?>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Booked Trips</title>
  <link rel="stylesheet" href="\Final\assets\css\style.css">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link
    href="https://fonts.googleapis.com/css2?family=Montserrat:wght@500;600;700;800&family=Poppins:wght@400;500;600;700&display=swap"
    rel="stylesheet">

  <style>
    body {
      min-height: 100vh;
      align-items: center;
      padding: 30px 0px 0px 0px;
      background: linear-gradient(135deg, #71b7e6, #9b59b6);
    }

    /* CSS cho phần container của danh sách các chuyến đi đã đặt */
    .container {
      max-width: 1200px;
      /* Đặt chiều rộng tối đa của container */
      margin: 0 auto;
      /* Canh giữa container */
      padding: 0 15px;
      /* Thêm khoảng cách padding bên trái và bên phải */
    }

    /* CSS cho phần chuyến đi đã đặt */
    .trip {
      background-color: #f9f9f9;
      /* Màu nền của mỗi chuyến đi */
      padding: 20px;
      /* Thêm khoảng cách padding */
      margin-bottom: 50px;
      /* Thêm khoảng cách dưới giữa các chuyến đi */
      border-radius: 8px;
      /* Bo góc */
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      /* Đổ bóng */
    }

    .trip h2 {
      font-size: 24px;
      /* Kích thước font cho tiêu đề */
      margin-bottom: 10px;
      /* Thêm khoảng cách dưới cho tiêu đề */
    }

    .trip p {
      margin-bottom: 5px;
      /* Thêm khoảng cách dưới cho các đoạn văn bản */
    }

    .content-center {
      text-align: center;
      margin-top: 10%;
      margin-bottom: 5%;
      font-size: 50px;

    }

    .wrapper {
      max-width: 1200px;
      margin: 0 auto;
      padding: 20px;
    }

    .nav {
      display: flex;
      justify-content: space-between;
      align-items: center;
      background-color: #fff;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      padding: 10px 20px;
    }

    .nav-logo {
      font-size: 24px;
      font-weight: bold;
    }

    .nav-menu {
      display: flex;
    }

    .nav-menu ul {
      display: flex;
      list-style: none;
      margin: 0;
      padding: 0;
    }

    .nav-menu ul li {
      margin-right: 20px;
    }

    .nav-menu ul li a {
      text-decoration: none;
      color: #333;
      font-weight: bold;
    }

    .nav-button {
      display: flex;
      align-items: center;
    }

    .btn {
      background-color: #007bff;
      color: #fff;
      border: none;
      border-radius: 4px;
      padding: 10px 20px;
      cursor: pointer;
      margin-right: 10px;
    }

    .btn:hover {
      background-color: blue;
      color: white;
    }

    .txt {
      color: white;
    }

    .white-btn {
      background-color: #fff;
      color: #007bff;
    }

    .slider {
      position: absolute;
      bottom: 0;
      left: 0;
      height: 4px;
      width: 50%;
      background: #007bff;
      border-radius: 10px;
      transition: 0.4s;
    }

    .nav-menu-btn {
      display: none;
    }

    input[type="radio"] {
      display: none;
    }

    input[type="radio"]:checked~.slider {
      left: 50%;
    }

    input[type="radio"]:checked~.home {
      color: #007bff;
    }

    .home,
    .blog {
      cursor: pointer;
      padding: 10px 20px;
    }

    .content {
      display: none;
    }

    .content-1 {
      display: block;
    }

    .content-trip {
      display: flex;
      justify-content: flex-start;
      margin-top: 20px;
    }

    .text {
      width: 70%;
    }

    .img {
      width: 30%;
      text-align: right;
    }

    .icon {
      margin-right: 5px;
    }

    article {
      height: 80vh;
    }

    .text {
      width: 70%;
      /* Adjust width as needed */
    }

    .delete {
      background-color: #dc3545;
      color: #fff;
      border: none;
      border-radius: 4px;
      padding: 5px 10px;
      cursor: pointer;
      width: 100%;
    }

    .delete:hover {
      background-color: #c82333;
    }

    .planname {
      color: #333;
      text-decoration: none;
      width: 100%;
      text-align: center;
      margin-bottom: 10px;
    }

    .planname:hover {
      color: #007bff;
    }

    .box {
      width: 15%;
      background-color: #fff;
      border: 1px solid #ccc;
      padding: 10px;
      margin-bottom: 10px;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      display: flex;
      /* Use flexbox */
      justify-content: center;
      /* Center content horizontally */
      align-items: center;
      /* Center content vertically */
      margin-right: 30px;
    }
  </style>
</head>

<body id="top">
  <header class="header" data-header>

    <div class="overlay" data-overlay></div>

    <div class="header-top">
      <div class="container">

        <a href="tel:+01123456790" class="helpline-box">

          <div class="icon-box">
            <ion-icon name="call-outline"></ion-icon>
          </div>

          <div class="wrapper">
            <p class="helpline-title">For Further Inquires :</p>

            <p class="helpline-number">+01 (123) 4567 90</p>
          </div>

        </a>

        <a href="#" class="logo">
          <img src="\Final\assets\images\logo.svg" alt="logo">
        </a>

        <div class="header-btn-group">


          <button class="nav-open-btn" aria-label="Open Menu" data-nav-open-btn>
            <ion-icon name="menu-outline"></ion-icon>
          </button>

        </div>

      </div>
    </div>

    <div class="header-bottom">
      <div class="container">

        <ul class="social-list">

          <li>
            <a href="#" class="social-link">
              <ion-icon name="logo-facebook"></ion-icon>
            </a>
          </li>

          <li>
            <a href="#" class="social-link">
              <ion-icon name="logo-twitter"></ion-icon>
            </a>
          </li>

          <li>
            <a href="#" class="social-link">
              <ion-icon name="logo-youtube"></ion-icon>
            </a>
          </li>

        </ul>

        <nav class="navbar" data-navbar>

          <div class="navbar-top">

            <a href="#" class="logo">
              <img src="\Final\assets\images\logo.svg" alt="Tourly logo">
            </a>

            <button class="nav-close-btn" aria-label="Close Menu" data-nav-close-btn>
              <ion-icon name="close-outline"></ion-icon>
            </button>

          </div>

          <ul class="navbar-list">

            <li>
              <a href="\Final\index.php" class="navbar-link" data-nav-link>home</a>
            </li>

            <li>
              <a href="\Final\booking\booked.php" class="navbar-link" data-nav-link>Booked</a>
            </li>

            <li>
              <a href="#" class="navbar-link" data-nav-link>Planning</a>
            </li>

            <li>
              <a href="#package" class="navbar-link" data-nav-link>About Us</a>
            </li>

          </ul>

        </nav>

        <button class="btn btn-primary"><a href="/Final/logout.php">SIGN OUT</a></button>

      </div>
    </div>

  </header>


  <main>
    <article>
      <div class="container ">
        <h1 class="content-center">Plan your trip</h1>
        <div class="container">
          <input type="radio" name="slider" id="home">
          <input type="radio" name="slider" id="blog">

          <section>
            <button type="button" class="btn btn-outline-primary click"> <a class="txt" href="/Final/plan/plan.php">Add
                a Trip</a></button> <br>
            <div class="content content-1 container-fluid">
              <div class="container-fluid content-trip">
                <!-- Hiển thị planName từ cơ sở dữ liệu vào phần tử HTML tương ứng -->
                <?php
                foreach ($_SESSION['placeNameNow'] as $planName) {
                  echo "<div class='box'>";
                  echo "<div class='text'>";
                  // Sử dụng $planName['planName'] để hiển thị tên kế hoạch du lịch
                  echo "<a href='editplan.php?edit_plan=" . $planName['planName'] . "&edit_id=" . $planName['planID'] . "' class='planname'>" . $planName['planName'] . "</a>";
                  // Thêm nút xóa bằng cách sử dụng mảng $planName để lấy tên kế hoạch du lịch và id của kế hoạch
                  echo "<form method='post' action=''>";
                  echo "<input type='hidden' name='delete_id' value='" . $planName['planID'] . "'>";
                  echo "<input type='hidden' name='delete_plan' value='" . $planName['planName'] . "'>";
                  echo "<button type='submit' class='delete' onclick='return confirmDelete();' >Delete</button>";
                  echo "</form>";
                  echo "</div>";
                  echo "</div>";
                }
                ?>
              </div>
            </div>
          </section>
        </div>
      </div>
      </div>


    </article>
  </main>

  <footer class="footer">

    <div class="footer-top">
      <div class="container">

        <div class="footer-brand">

          <a href="#" class="logo">
            <img src="\Final\assets\images\logo.svg" alt="Tourly logo">
          </a>

          <p class="footer-text">
            Urna ratione ante harum provident, eleifend, vulputate molestiae proin fringilla, praesentium magna conubia
            at
            perferendis, pretium, aenean aut ultrices.
          </p>

        </div>

        <div class="footer-contact">

          <h4 class="contact-title">Contact Us</h4>

          <p class="contact-text">
            Feel free to contact and reach us !!
          </p>

          <ul>

            <li class="contact-item">
              <ion-icon name="call-outline"></ion-icon>

              <a href="tel:+01123456790" class="contact-link">+01 (123) 4567 90</a>
            </li>

            <li class="contact-item">
              <ion-icon name="mail-outline"></ion-icon>

              <a href="mailto:info.tourly.com" class="contact-link">info.tourly.com</a>
            </li>

            <li class="contact-item">
              <ion-icon name="location-outline"></ion-icon>

              <address>3146 Koontz, California</address>
            </li>

          </ul>

        </div>

        <div class="footer-form">

          <p class="form-text">
            Subscribe our newsletter for more update & news !!
          </p>

          <form action="" class="form-wrapper">
            <input type="email" name="email" class="input-field" placeholder="Enter Your Email" required>

            <button type="submit" class="btn btn-secondary">Subscribe</button>
          </form>

        </div>

      </div>
    </div>

    <div class="footer-bottom">
      <div class="container">

        <p class="copyright">
          &copy; 2022 <a href="">codewithsadee</a>. All rights reserved
        </p>

        <ul class="footer-bottom-list">

          <li>
            <a href="#" class="footer-bottom-link">Privacy Policy</a>
          </li>

          <li>
            <a href="#" class="footer-bottom-link">Term & Condition</a>
          </li>

          <li>
            <a href="#" class="footer-bottom-link">FAQ</a>
          </li>

        </ul>

      </div>
    </div>

  </footer>


  <a href="#top" class="go-top" data-go-top>
    <ion-icon name="chevron-up-outline"></ion-icon>
  </a>


  <script src="\Final\js\script.js"></script>
  <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
  <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

  <script>
    // Thêm mã JavaScript để xác nhận trước khi xóa kế hoạch
    function confirmDelete() {
      return confirm("Are you sure you want to delete this travel plan?"); // Hộp thoại xác nhận
    }
  </script>

</body>

</html>