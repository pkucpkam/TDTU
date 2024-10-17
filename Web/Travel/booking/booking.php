<?php
// Khởi động phiên
session_start();

// Kiểm tra xem người dùng đã đăng nhập hay chưa
if (!isset($_SESSION['username'])) {
  header("Location: \Final\login\login.php");
  exit;
}

// Kết nối tới cơ sở dữ liệu MySQL
$servername = "localhost";
$username = "root"; // Thay username và password phù hợp với cấu hình MySQL của bạn
$password = "";
$dbname = "travel1";

// Tạo kết nối
$conn = new mysqli($servername, $username, $password, $dbname);

// Kiểm tra kết nối
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

// Lấy tên người dùng từ session
$username = $_SESSION['username'];

// SQL query để lấy thông tin name và email từ cơ sở dữ liệu dựa trên tên người dùng
$sql = "SELECT name, email, phonenumber FROM users WHERE username='$username'";
$result = $conn->query($sql);

// Kiểm tra số hàng trả về từ cơ sở dữ liệu
if ($result->num_rows > 0) {
  // Lưu thông tin name và email vào biến
  $row = $result->fetch_assoc();
  $name = $row['name'];
  $email = $row['email'];
  $phone = $row['phonenumber'];
} else {
  // Trường hợp không tìm thấy thông tin name và email
  $name = "";
  $email = "";
  $phone = "";
}

// Đóng kết nối
$conn->close();
?>


<!DOCTYPE html>
<!---Coding By CodingLab | www.codinglabweb.com--->
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="X-UA-Compatible" content="ie=edge" />
  <title>Registration Form in HTML CSS</title>
  <!---Custom CSS File--->
  <link rel="stylesheet" href="\Final\assets\css\booking_style.css" />
  <style>
    body {
      padding: 5% 0px;
    }
  </style>
</head>

<body>
  
  <section class="container">
    <header>Travel Booking Form</header>
    <form action="/Final/booking/submit_booking.php" method="post" class="form">
      <div class="input-box">
        <label>Name User</label>
        <input type="text" placeholder="<?php echo $name; ?>" required readonly />
      </div>

      <div class="input-box">
        <label>Email User</label>
        <input type="text" placeholder="<?php echo $email; ?>" required readonly />
      </div>

      <div class="column">
        <div class="input-box">
          <label>Phone Number</label>
          <input type="number" placeholder="<?php echo $phone; ?>" required readonly />
        </div>
      </div>
      <br>
      <h2>Airline Tickets:</h2>
      <div class="column">
        <div class="select-box">
          <select name="airline">
            <option hidden>Airline Brands</option>
            <option>Vietnam Airlines (VNA)</option>
            <option>Vietjet Air (VJ)</option>
            <option>Bamboo Airways (QH)</option>
            <option>Jetstar Pacific Airlines</option>
          </select>
        </div>
      </div>

      <div class="gender-box">
        <h3>Ticket Type</h3>
        <div class="gender-option">
          <div class="gender">
            <input type="radio" name="ticket-type" value="Regular" checked />
            <label for="check-male">Regular</label>
          </div>
          <div class="gender">
            <input type="radio" name="ticket-type" value="Business" />
            <label for="check-female">Business</label>
          </div>
          <div class="gender">
            <input type="radio" name="ticket-type" value="VIP" />
            <label for="check-other">VIP</label>
          </div>
        </div>
      </div>
      <div class="input-box address">
        <p style="font-weight: bold; font-size: 1.5rem;">Hotel:
        <p>
        <div class="input-box">
          <label>Name Hotel</label>
          <input id="hotel-name" name="hotel-name" type="text" placeholder="Hotel's name" required readonly />
        </div>
        <div class="column">
          <div class="input-box">
            <label>Location Hotel</label>
            <input id="locationName" name="locationName" type="text" placeholder="Address" required readonly />
          </div>
        </div>

        <div class="column">
          <label for="">Number People</label>
          <div class="select-box">
            <select name="number-people" required>
              <option hidden>Number People</option>
              <option>2</option>
              <option>4</option>
              <option>8</option>
              <option>than 10</option>
            </select>
          </div>
        </div>

        <div class="column">
          <label for="check-in">Check-in Date:</label>
          <input id="check-in" name="check-in" type="date" placeholder="Check in" required />
          <label for="check-out">Check-out Date:</label>
          <input id="check-out" name="check-out" type="date" placeholder="Check out" required />
        </div>

        <div class="column">
          <div class="input-box">
            <label style="font-weight: bold;">Total Price ($):</label>
            <input id="price" name="price" type="text" placeholder="" required readonly />
          </div>
        </div>

        <input id="placeID" name="placeID" type="text" placeholder="" required hidden />
        <button type="submit">Submit</button>
    </form>
  </section>

  <!-- Srcipt -->
  <script>

    // Lấy thông tin từ session và điền vào form
    //lấy thông tin về địa danh và giá 
    var hotelName = localStorage.getItem("hotelName");
    var locationName = localStorage.getItem("locationName");
    var placeID = localStorage.getItem("placeID");
    var price = localStorage.getItem("price");

    placeID = placeID.trim()

    document.getElementById("hotel-name").value = hotelName;
    document.getElementById("locationName").value = locationName;
    document.getElementById("price").value = price;
    document.getElementById("placeID").value += placeID;



    console.log(placeID);

  </script>
</body>

</html>