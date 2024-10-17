<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <link rel="stylesheet" href="login.css">
    <title>VivuTravel | Login & Registration</title>
</head>
<body>
 <div class="wrapper">
    <nav class="nav">
        <div class="nav-logo">
            <p id="return-home">Tourly</p>
        </div>
        <div class="nav-menu" id="navMenu">
            <ul>
                <li><a href="/Final/index.php" class="link active">Home</a></li>
                <li><a href="#" class="link">Booking</a></li>
                <li><a href="#" class="link">Planning</a></li>
                <li><a href="#" class="link">About</a></li>
            </ul>
        </div>
        <div class="nav-button">
            <button class="btn white-btn" id="loginBtn" onclick="login()">Sign In</button>
            <button class="btn" id="registerBtn" onclick="register()">Sign Up</button>
        </div>
        <div class="nav-menu-btn">
            <i class="bx bx-menu" onclick="myMenuFunction()"></i>
        </div>
    </nav>
<!----------------------------- Form box ----------------------------------->    
    <div class="form-box">
        
        <!------------------- login form -------------------------->
        <form action="" method="post" id="loginForm">
            <div class="login-container" id="login">
                <div class="top">
                    <span>Don't have an account? <a href="#" onclick="register()">Sign Up</a></span>
                    <header>Login</header>
                </div>
                <div class="input-box">
                    <input type="text" name="login_username" class="input-field" placeholder="Username" required>
                    <i class="bx bx-user"></i>
                </div>
                <div class="input-box">
                    <input type="password" name="login_password" class="input-field" placeholder="Password" required>
                    <i class="bx bx-lock-alt"></i>
                </div>
                <div class="input-box">
                    <input type="submit" name="login_submit" class="submit" value="Sign In">
                </div>
                <div class="two-col">
                    <div class="one">
                        <input type="checkbox" id="login-check">
                        <label for="login-check"> Remember Me</label>
                    </div>
                    <div class="two">
                        <label><a href="#">Forgot password?</a></label>
                    </div>
                </div>
            </div>
        </form>
        <?php
        if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['login_submit'])) {
            // Xử lý đăng nhập ở đây
            // Khởi động phiên
            session_start();

            // Kết nối tới cơ sở dữ liệu MySQL
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

            // Lấy dữ liệu từ form
            $username = $_POST['login_username'];
            $password = $_POST['login_password'];

            // Bảo vệ dữ liệu trước khi truy vấn
            $username = mysqli_real_escape_string($conn, $username);
            $password = mysqli_real_escape_string($conn, $password);

            //Kiểm tra admin 
            $sql = "SELECT * FROM admin WHERE account ='$username' AND password='$password'";
            $result = $conn->query($sql);

            // Kiểm tra số hàng trả về từ cơ sở dữ liệu
            if ($result->num_rows > 0) {
                // Đăng nhập thành công, lưu tên người dùng vào session
                header("Location: /Final/admin/admin.html");
                exit;
            } 

            // SQL query để kiểm tra thông tin đăng nhập
            $sql = "SELECT * FROM users WHERE username='$username' AND password='$password'";
            $result = $conn->query($sql);

            // Kiểm tra số hàng trả về từ cơ sở dữ liệu
            if ($result->num_rows > 0) {
                // Đăng nhập thành công, lưu tên người dùng vào session
                $_SESSION['username'] = $username;
                header("Location: /Final/index.php");
                exit;
            } else {
                // Đăng nhập không thành công
                echo "<script>alert('Tên đăng nhập hoặc mật khẩu không đúng.');</script>";
            }

            // Đóng kết nối
            $conn->close();
        }
        ?>
        <!------------------- registration form -------------------------->
        <div class="register-container" id="register">
            <div class="top">
                <span>Have an account? <a href="#" onclick="login()">Login</a></span>
                <header>Sign Up</header>
            </div>
            <form action="" method="post">
                <div class="">
                    <div class="input-box">
                        <input type="text" name="name" class="input-field" placeholder="Your Name" required>
                        <i class="bx bx-user"></i>
                    </div>
                </div>
                <div class="input-box">
                    <input type="email" name="email" class="input-field" placeholder="Email" required>
                    <i class="bx bx-envelope"></i>
                </div>
                <div class="input-box">
                    <input type="text" name="phone" class="input-field" placeholder="Number Phone" required>
                    <i class="bx bx-phone"></i>
                </div>
                <div class="input-box">
                    <input type="text" name="username" class="input-field" placeholder="Username" required>
                    <i class="bx bx-user"></i>
                </div>
                <div class="input-box">
                    <input type="password" name="password" class="input-field" placeholder="Password" required>
                    <i class="bx bx-lock-alt"></i>
                </div>
                <div class="input-box">
                    <input type="submit" name="register_submit" class="submit" value="Register">
                </div>
                <div class="two-col">
                    <div class="one">
                        <input type="checkbox" id="register-check">
                        <label for="register-check"> Remember Me</label>
                    </div>
                    <div class="two">
                        <label><a href="#">Terms & conditions</a></label>
                    </div>
                </div>
            </form>
        </div>
        <?php
        if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['register_submit'])) {
            // Xử lý đăng ký ở đây
            // Khởi động phiên

            // Kết nối tới cơ sở dữ liệu MySQL
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

            // Lấy dữ liệu từ form
            $name = $_POST['name'];
            $email = $_POST['email'];
            $phone = $_POST['phone'];
            $username = $_POST['username'];
            $password = $_POST['password'];

            // Bảo vệ dữ liệu trước khi truy vấn
            $name = mysqli_real_escape_string($conn, $name);
            $email = mysqli_real_escape_string($conn, $email);
            $phone = mysqli_real_escape_string($conn, $phone);
            $username = mysqli_real_escape_string($conn, $username);
            $password = mysqli_real_escape_string($conn, $password);

            // SQL query để kiểm tra xem tên người dùng đã tồn tại chưa
            $check_username_sql = "SELECT * FROM users WHERE username='$username'";
            $check_username_result = $conn->query($check_username_sql);

            if ($check_username_result->num_rows > 0) {
                echo "<script>alert('Tên người dùng đã tồn tại. Vui lòng chọn tên người dùng khác.');</script>";
            } else {
                // SQL query để thêm người dùng mới vào cơ sở dữ liệu
                $insert_user_sql = "INSERT INTO users (name, email, phonenumber, username, password) VALUES ('$name', '$email', '$phone', '$username', '$password')";
                if ($conn->query($insert_user_sql) === TRUE) {
                    echo "<script>alert('Đăng kí thành công. Vui lòng đăng nhập lại!');</script>";
                } else {
                    echo "<script>alert('Đã xảy ra lỗi trong quá trình đăng ký: " . $conn->error . "');</script>";
                }
            }
            // Đóng kết nối
            $conn->close();
        }
        ?>
    </div>
</div>   
<script>
   
   function myMenuFunction() {
    var i = document.getElementById("navMenu");
    if(i.className === "nav-menu") {
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

<script>
    var logo = document.getElementById("return-home")
    logo.addEventListener('click', function() {
        window.location.href = "/Final/index.php"
    })
</script>
</body>
</html>
