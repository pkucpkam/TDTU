<?php
// Bắt đầu phiên làm việc
session_start();

// Kiểm tra xem người dùng đã đăng nhập hay chưa bằng cách kiểm tra session 'username'
if (isset($_SESSION['username'])) {
  // Nếu đã đăng nhập, ẩn nút "Login" và hiển thị nút "Logout"
  $loginButtonStyle = "none";
  $logoutButtonStyle = "block";
} else {
  // Nếu chưa đăng nhập, hiển thị nút "Login" và ẩn nút "Logout"
  $loginButtonStyle = "block";
  $logoutButtonStyle = "none";
}

// Lấy hotdeal 
$servername = "localhost";
$db_username = "root";
$db_password = "";
$dbname = "travel1";

$conn = new mysqli($servername, $db_username, $db_password, $dbname);

if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$hotDealList = "SELECT * FROM travel1.place INNER JOIN travel1.hotdeals ON place.placeID = hotdeals.placeID";
$result1 = $conn->query($hotDealList);
if ($result1->num_rows > 0) {
  // Hiển thị kết quả
  while ($row = $result1->fetch_assoc()) {
  }
} else {
}

?>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Home</title>
  <link rel="stylesheet" href="assets\css\style.css">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link
    href="https://fonts.googleapis.com/css2?family=Montserrat:wght@500;600;700;800&family=Poppins:wght@400;500;600;700&display=swap"
    rel="stylesheet">

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

            <p class="helpline-number">088888888</p>
          </div>

        </a>

        <a href="#" class="logo">
          <img src="assets\images\logo.svg" alt="logo">
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
            <a href="https://www.facebook.com/phucpham1803/" class="social-link">
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
              <img src="assets/images/logo-blue.svg" alt="Tourly logo">
            </a>

            <button class="nav-close-btn" aria-label="Close Menu" data-nav-close-btn>
              <ion-icon name="close-outline"></ion-icon>
            </button>

          </div>

          <ul class="navbar-list">

            <li>
              <a href="#home" class="navbar-link" data-nav-link>home</a>
            </li>

            <li>
              <a href="booking\booked.php" class="navbar-link" data-nav-link>Booked</a>
            </li>

            <li>
              <a href="\Final\plan\showplan.php" class="navbar-link" data-nav-link>Planning</a>
            </li>

            <li>
              <a href="#package" class="navbar-link" data-nav-link>About Us</a>
            </li>

          </ul>

        </nav>

        <button style="display: <?php echo $loginButtonStyle; ?>" class="btn btn-primary"><a
            href="/Final/login/login.php">LOGIN</a></button>

        <button style="display: <?php echo $logoutButtonStyle; ?>" class="btn btn-primary"><a
            href="/Final/logout.php">SIGN OUT</a></button>


      </div>
    </div>
    <section class="hero" id="home">
      <div class="container">
        <h2 class="h1 hero-title">Planning and booking services.</h2>

        <p class="hero-text">
          We help you plan your trip and book hotels in the locations you will visit.
        </p>
    </section>

  </header>

  <main>
    <article>

      <div class="video">
        <video autoplay muted loop id="myVideo">
          <source src="assets\video\back.mp4" type="video/mp4">
        </video>
      </div>

      </div>


      <!-- </section> -->
      <!-- SEARCH -->
      <section class="tour-search" id="search-box">
        <div class="container">
          <form action="search\search.php" method="post" class="tour-search-form">
            <div class="input-wrapper">
              <label for="destination" class="input-label">Search Destination</label>
              <input type="text" name="destination" id="destination" placeholder="Enter Destination"
                class="input-field">
            </div>

            <div class="input-wrapper">
              <label for="budget" class="input-label">Your Budget</label>
              <select name="budget" id="budget" class="form-select">
                <option hidden value="0">Your budget</option>
                <option value="1000">1000 $</option>
                <option value="2000">2000 $</option>
                <option value="5000">5000 $</option>
                <option value="10000">10000 $</option>
                <option value="200000">20000 $</option>
                <option value="0">NO PROBLEM</option>
              </select>
            </div>

            <button type="submit" class="btn btn-secondary">SEARCH</button>

          </form>
        </div>
      </section>

      <!-- 
        - #POPULAR
      -->

      <section class="popular" id="destination">
        <div class="container">

          <p class="section-subtitle">Uncover place</p>

          <h2 class="h2 section-title">Suggested destination</h2>

          <p class="section-text">
            Introducing new services and experiences to enrich the allure of this destination is essential
            in captivating travelers' imaginations and fostering unforgettable journeys. From guided eco-tours
            and adventurous pursuits to immersive cultural experiences and innovative technology integration.
          </p>

          <ul class="popular-list">

            <li>
              <div class="popular-card">

                <figure class="card-img">
                  <img src="./assets/images/popular-1.jpg" alt="San miguel, italy" loading="lazy">
                </figure>

                <div class="card-content">
                  <h3 class="h3 card-title">
                    <a href="detail\hanoi.php">Ha Noi</a>
                  </h3>

                  <p class="card-text">
                    Hanoi, the capital city of Vietnam, is a vibrant metropolis where ancient traditions blend
                    seamlessly with modernity.
                  </p>
                </div>

              </div>
            </li>

            <li>
              <div class="popular-card">

                <figure class="card-img">
                  <img src="./assets/images/popular-2.jpg" alt="Burj khalifa, dubai" loading="lazy">
                </figure>

                <div class="card-content">
                  <h3 class="h3 card-title">
                    <a href="\Final\detail\danang.php">Da Nang</a>
                  </h3>

                  <p class="card-text">
                    Đà Nẵng, a coastal city in central Vietnam, is renowned for its stunning beaches, picturesque
                    landscapes, and vibrant culinary scene.
                  </p>
                </div>

              </div>
            </li>

            <li>
              <div class="popular-card">

                <figure class="card-img">
                  <img src="./assets/images/popular-3.jpg" alt="Kyoto temple, japan" loading="lazy">
                </figure>

                <div class="card-content">
                  <h3 class="h3 card-title">
                    <a href="\Final\detail\hochiminh.php">Ho Chi Minh City</a>
                  </h3>

                  <p class="card-text">
                    Ho Chi Minh City, formerly known as Saigon, is a bustling metropolis in southern Vietnam that
                    pulsates with energy and vitality.
                  </p>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </section>

      <!-- Popular -->

      <!-- HOT DEALS -->
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
                      <button type="button" class="btn btn-success booking-button" data-mdb-ripple-init>Book</button>
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

      <!-- 
        - #GALLERY
      -->

      <section class="gallery" id="gallery">
        <div class="container">

          <p class="section-subtitle">Photo Gallery</p>

          <h2 class="h2 section-title">Photo's From Travellers</h2>

          <p class="section-text">
            It seems to me that some people want to make this announcement, but only the first ones, and no one else.
            The appearance of the praisers.
            Let it be ornamented with elasticity, fit.
          </p>

          <ul class="gallery-list">

            <li class="gallery-item">
              <figure class="gallery-image">
                <img src="./assets/images/gallery-1.jpg" alt="Gallery image">
              </figure>
            </li>

            <li class="gallery-item">
              <figure class="gallery-image">
                <img src="./assets/images/gallery-2.jpg" alt="Gallery image">
              </figure>
            </li>

            <li class="gallery-item">
              <figure class="gallery-image">
                <img src="./assets/images/gallery-3.jpg" alt="Gallery image">
              </figure>
            </li>

            <li class="gallery-item">
              <figure class="gallery-image">
                <img src="./assets/images/gallery-4.jpg" alt="Gallery image">
              </figure>
            </li>

            <li class="gallery-item">
              <figure class="gallery-image">
                <img src="./assets/images/gallery-5.jpg" alt="Gallery image">
              </figure>
            </li>

          </ul>

        </div>
      </section>





      <!-- 
        - #CTA
      -->

      <section class="cta" id="contact">
        <div class="container">

          <div class="cta-content">
            <p class="section-subtitle">Call To Action</p>

            <h2 class="h2 section-title">Ready For Unforgatable Travel. Remember Us!</h2>

            <p class="section-text">
              It seems to me that some people want to make this announcement, but only the first ones, and no one else.
              The appearance of the praisers.
              Let it be ornamented with elasticity, fit.
            </p>
          </div>

          <button class="btn btn-secondary">Contact Us !</button>

        </div>
      </section>
    </article>
  </main>

  <!-- 
    - #FOOTER
  -->

  <footer class="footer">

    <div class="footer-top">
      <div class="container">

        <div class="footer-brand">

          <a href="#" class="logo">
            <img src="./assets/images/logo.svg" alt="Tourly logo">
          </a>

          <p class="footer-text">
            From the moment you embark on your travel adventure with us, you'll notice the meticulous attention to
            detail that sets us apart.
            Our team of experienced travel professionals works tirelessly to tailor every aspect of your trip to your
            unique preferences.
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
          &copy; 2022 <a href="">Travel</a>. All rights reserved
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


  <!-- 
    - #GO TO TOP
  -->

  <a href="#top" class="go-top" data-go-top>
    <ion-icon name="chevron-up-outline"></ion-icon>
  </a>


  <!-- 
    - custom js link
  -->
  <script src="\Final\js\script.js"></script>

  <!-- 
    - ionicon link
  -->
  <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
  <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>


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
        xhttp.open("POST", "comment/get_comments.php", true);
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
      xhttp.open("POST", "comment/add_comment.php", true);
      xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
      // Gửi dữ liệu form qua yêu cầu AJAX
      xhttp.send("&comment=" + comment + "&placeID=" + placeID);
    });

  </script>




</body>

<style>
  /* CSS cho phần tìm kiếm */
  .tour-search {
    background-color: #f9f9f9;
    padding: 50px 0;
  }

  .tour-search .container {
    max-width: 800px;
    margin: 0 auto;
  }

  .tour-search-form {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
  }

  .input-wrapper {
    width: calc(50% - 10px);
    margin-bottom: 20px;
  }

  .input-label {
    font-size: 18px;
    font-weight: bold;
    color: #333;
  }

  .input-field,
  .form-select {
    width: 100%;
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 5px;
  }

  .btn {
    padding: 10px 20px;
    font-size: 16px;
    font-weight: bold;
    color: #fff;
    background-color: #007bff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }

  .btn:hover {
    background-color: #0056b3;
  }
</style>

</html>