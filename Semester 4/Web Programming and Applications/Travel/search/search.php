<?php
session_start();

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "travel1";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $destination = $_POST['destination'];
$destination = strtolower(trim($destination));
$destination = str_replace(' ', '', $destination);

    $budget = $_POST['budget'];

    if ($destination == "" && $budget == 0) {
        // Truy vấn cơ sở dữ liệu để lấy tất cả các dữ liệu về các điểm đến
        $sql = "SELECT * FROM place";
    } else if ($destination == "") {
        $sql = "SELECT * FROM place WHERE price <= $budget";
    } else if ($budget == 0) {
        // Truy vấn cơ sở dữ liệu để lấy các điểm đến dựa trên tên
        $destination = str_replace(' ', '', $destination); // Loại bỏ khoảng trắng
        $sql = "SELECT * FROM place WHERE REPLACE(LOWER(name), ' ', '') LIKE '%$destination%' OR REPLACE(LOWER(locationName), ' ', '') LIKE '%$destination%'";

    } else {
        // Truy vấn cơ sở dữ liệu để lấy các điểm đến dựa trên cả tên và giá
        $sql = "SELECT * FROM place WHERE REPLACE(LOWER(name), ' ', '') LIKE '%$destination%' OR REPLACE(LOWER(locationName), ' ', '') LIKE '%$destination%' AND price <= $budget";
    }

    $result = $conn->query($sql);


    if ($result->num_rows > 0) {
        // Hiển thị kết quả
        while ($row = $result->fetch_assoc()) {
        }
    } else {
    }
}

$hotDealList = "SELECT * FROM travel1.place INNER JOIN travel1.hotdeals ON place.placeID = hotdeals.placeID";
$result1 = $conn->query($hotDealList);
if ($result1->num_rows > 0) {
    // Hiển thị kết quả
    while ($row = $result1->fetch_assoc()) {
    }
} else {
}

header("Cache-Control: public, max-age=3600");

// Đóng kết nối
$conn->close();
?>


<!-- HTML -->
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search</title>
    <link rel="shortcut icon" href="./favicon.svg" type="image/svg+xml">

    <link rel="stylesheet" href="\Final\assets\css\style.css">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
        href="https://fonts.googleapis.com/css2?family=Montserrat:wght@500;600;700;800&family=Poppins:wght@400;500;600;700&display=swap"
        rel="stylesheet">

    <style>
        #nors {
            font-size: 40px;
            text-align: center;
            font-family: var(--ff-poppins);
            font-weight: bold;
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
                    <img src="/Final/assets/images/logo.svg" alt=" logo">
                </a>

                <div class="header-btn-group">

                    <button class="search-btn" aria-label="Search">
                        <ion-icon name="search"></ion-icon>
                    </button>

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
                            <img src="/Final/assets/images/logo-blue.svg" alt="Tourly logo">
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
                            <a href="\Final\plan\plan.php" class="navbar-link" data-nav-link>Planning</a>
                        </li>

                        <li>
                            <a href="#package" class="navbar-link" data-nav-link>About Us</a>
                        </li>

                    </ul>

                </nav>

                <button class="btn btn-primary">SING OUT</button>

            </div>
        </div>
        <section class="hero" id="home">
            <div class="container">
                <h2 class="h1 hero-title">Search Result</h2>

                <p class="hero-text">
                    Ac mi duis mollis. Sapiente? Scelerisque quae, penatibus? Suscipit class corporis nostra rem quos
                    voluptatibus habitant?
                    Fames, vivamus minim nemo enim, gravida lobortis quasi, eum.
                </p>
        </section>
    </header>


    <main>
        <article>

            <div class="video">
                <video autoplay muted loop id="myVideo">
                    <source src="\Final\assets\video\14385-256955049_small.mp4" type="video/mp4">
                </video>
            </div>

            </div>

            <section class="package" id="package">
                <div class="container">

                    <p class="section-subtitle">Hotel</p>

                    <h2 class="h2 section-title">Search Result</h2>

                    <p class="section-text">
                        Fusce hic augue velit wisi quibusdam pariatur, iusto primis, nec nemo, rutrum. Vestibulum cumque
                        laudantium.
                        Sit ornare
                        mollitia tenetur, aptent.
                    </p>

                    <ul class="package-list">
                        <?php
                        // Kiểm tra xem có kết quả từ truy vấn không
                        if ($result->num_rows > 0) {
                            // Lặp qua từng dòng dữ liệu
                            foreach ($result as $row) {
                                ?>
                                <li>
                                    <div class="package-card">
                                        <figure class="card-banner">
                                            <img src="<?php echo $row["imgLink"]; ?>" alt="Hotel Image" loading="lazy">
                                        </figure>
                                        <div class="card-body">
                                            <h3 class="h3 card-title hotel-name">
                                                <?php echo $row["name"]; ?>
                                            </h3>
                                            <p class="card-text location-name">
                                                <?php echo $row["locationName"]; ?>
                                            </p>
                                            <p style="display: inline;" class="card-text mr-2 price">
                                                <?php echo $row["price"]; ?>
                                            </p>
                                            <p class="init-price">15.000.000 vnd</p>
                                            <p hidden class="placeID">
                                                <?php echo $row["placeID"]; ?>
                                            </p>
                                            <input type="hidden" class="placeID" value="<?php echo $row["placeID"]; ?>">
                                            <button id="myBtn" class="btn btn-info"
                                                data-placeid="<?php echo $row["placeID"]; ?>">Comments</button>
                                            <button type="button" class="btn btn-success booking-button"
                                                data-mdb-ripple-init>Book</button>
                                        </div>
                                    </div>
                                </li>
                                <?php
                            }
                        } else {
                            // Nếu không có kết quả, hiển thị thông báo không tìm thấy kết quả
                            echo "<li id='nors'>No results were found.</li>";
                        }
                        ?>
                    </ul>
                </div>
            </section>

            <section style="  background: linear-gradient(135deg,#71b7e6,#9b59b6);" class="popular" id="destination">
                <div class="container">

                    <p class="section-subtitle">Check Now</p>

                    <h2 class="h2 section-title">Hot deals today</h2>

                    <p class="section-text">
                        Don't miss this opportunity to elevate your travel experience without compromising on quality.
                        Book your stay now and treat yourself to the indulgence you deserve at a fraction of the cost.
                    </p>

                    <ul class="package-list">
                        <?php
                        // Kiểm tra xem có kết quả từ truy vấn không
                        if ($result1->num_rows > 0) {
                            // Lặp qua từng dòng dữ liệu
                            foreach ($result1 as $row) {
                                ?>
                                <li>
                                    <div class="package-card">
                                        <figure class="card-banner">
                                            <img src="<?php echo $row["imgLink"]; ?>" alt="Hotel Image" loading="lazy">
                                        </figure>
                                        <div class="card-body">
                                            <h3 class="h3 card-title hotel-name">
                                                <?php echo $row["name"]; ?>
                                            </h3>
                                            <p class="card-text location-name">
                                                <?php echo $row["locationName"]; ?>
                                            </p>
                                            <p style="display: inline;" class="card-text mr-2 price">
                                                <?php echo $row["price"]; ?>
                                            </p>
                                            <p class="init-price">
                                                <?php echo $row["initPrice"]; ?>
                                            </p>
                                            <p hidden class="placeID">
                                                <?php echo $row["placeID"]; ?>
                                            </p>
                                            <input type="hidden" class="placeID" value="<?php echo $row["placeID"]; ?>">
                                            <button id="myBtn" class="btn btn-info"
                                                data-placeid="<?php echo $row["placeID"]; ?>">Comments</button>
                                            <button type="button" class="btn btn-success booking-button"
                                                data-mdb-ripple-init>Book</button>
                                        </div>
                                    </div>
                                </li>
                                <?php
                            }
                        } else {
                            // Nếu không có kết quả, hiển thị thông báo không tìm thấy kết quả
                            echo "<li id='nors'>No results were found.</li>";
                        }
                        ?>
                    </ul>
                </div>
            </section>
        </article>
    </main>

    <footer class="footer">

        <div class="footer-top">
            <div class="container">

                <div class="footer-brand">

                    <a href="#" class="logo">
                        <img src="/Final/assets/images/logo.svg" alt="Tourly logo">
                    </a>

                    <p class="footer-text">
                        Urna ratione ante harum provident, eleifend, vulputate molestiae proin fringilla, praesentium
                        magna conubia
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

    <div id="myModal" class="modal">
        <!-- Modal content -->
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>Reviews about this place</h2>
            <div class="comments">
                <!-- Hiển thị thông tin đánh giá -->
            </div>
            <h2 id="comment-title">Add your comment</h2>
            <form id="comment-form" class="comment-form">
                <div class="form-group">
                    <label for="comment">Your Comment:</label>
                    <textarea id="comment" class="comment-box" name="comment" rows="4" required></textarea>
                </div>
                <input type="hidden" id="placeID" name="placeID">
                <button type="submit" class="btn btn-primary comment-button">Comment</button>
            </form>
        </div>
    </div>

    <script src="\Final\js\script.js"></script>

    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>


    <script>
        var btns = document.querySelectorAll(".btn-info");

        // Lấy phần span đóng modal
        var span = document.getElementsByClassName("close")[0];

        // Lặp qua mỗi nút "Comments" để gán sự kiện khi click
        btns.forEach(function (btn) {
            btn.onclick = function () {
                var modal = document.getElementById("myModal");
                modal.style.display = "block";
                var placeID = this.dataset.placeid;
                document.getElementById("placeID").value = placeID;
                // Lấy ID của địa điểm từ thuộc tính data-placeid
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {
                        document.querySelector(".comments").innerHTML = this.responseText;
                    }
                };
                xhttp.open("POST", "/Final/comment/get_comments.php", true);
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("placeID=" + placeID); // Gửi ID của địa điểm trong yêu cầu AJAX
            }
        });

        // Khi người dùng click vào nút đóng (span), đóng modal
        span.onclick = function () {
            var modal = document.getElementById("myModal");
            modal.style.display = "none";
        }

        // Khi người dùng click bất kỳ đâu ngoài modal, đóng modal
        window.onclick = function (event) {
            var modal = document.getElementById("myModal");
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }


        //Them comment
        document.getElementById("comment-form").addEventListener("submit", function (event) {
            event.preventDefault(); // Ngăn chặn gửi form mặc định

            // Lấy dữ liệu từ form
            var comment = document.getElementById("comment").value;
            var placeID = document.getElementById("placeID").value; // Lấy giá trị placeID từ trường input ẩn

            // Tạo yêu cầu AJAX 
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    // Sau khi gửi thành công, làm mới phần hiển thị comment
                    document.querySelector(".comments").innerHTML = this.responseText;
                    // Xóa dữ liệu trong form
                    document.getElementById("comment").value = "";
                }
            };
            xhttp.open("POST", "/Final/comment/add_comment.php", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            // Gửi dữ liệu form qua yêu cầu AJAX
            xhttp.send("&comment=" + comment + "&placeID=" + placeID);
        });
    </script>
</body>

</html>