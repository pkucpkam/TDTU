function moreButton() {
    var moreText = document.getElementById("infor");
    var moreButtonInfo = document.getElementById("more-button");

    // Lấy giá trị của thuộc tính "display" của phần tử "infor"
    var computedStyle = window.getComputedStyle(moreText);
    var displayValue = computedStyle.getPropertyValue("display");

    // Kiểm tra nếu phần tử đang ẩn
    if (displayValue === "none") {
        moreText.style.display = "block";
        moreButtonInfo.innerHTML = "See less";
    }
    else {
        moreText.style.display = "none";
        moreButtonInfo.innerHTML = "See More";
    }
}


//nav-menu
// script.js
document.addEventListener("DOMContentLoaded", function () {
    var navbar = document.getElementById("main-header");
    var navElements = document.querySelectorAll(".nav-ele");
    var nav_border = document.getElementById("now")
    window.addEventListener("scroll", function () {
        if (window.scrollY > 50) {
            navbar.classList.remove("unscrolled-header");
            navbar.classList.add("scrolled-header");
            nav_border.style.borderColor = "white"
            navElements.forEach(function (element) {
                element.style.color = "white"; // Đổi màu chữ thành trắng khi cuộn
                element.style.setProperty('--after-bg-color', 'white');
            });
        } else {
            navbar.classList.remove("scrolled-header");
            navbar.classList.add("unscrolled-header");
            nav_border.style.borderColor = "black"
            navElements.forEach(function (element) {
                element.style.color = "black"; // Đổi màu chữ thành đen khi lăn lên
                element.style.setProperty('--after-bg-color', 'black');
            });
        }
    });
});


window.addEventListener('resize', function() {
    var navMenu = this.document.getElementById("nav-menu")
    var subNav = this.document.getElementById("has-submenu")
    
    if (window.innerWidth < 310){
        navMenu.style.display = "none"
        subNav.style.display = "none";
    }
    else if (window.innerWidth < 900) {
        navMenu.style.display = "none";
        subNav.style.display = "block";
    }
    else {
        subNav.style.display = "none";
        navMenu.style.display = "block";
    } 
});



// Chuyển hướng trang sang booking
document.addEventListener("DOMContentLoaded", function() {
    var submitButtons = document.querySelectorAll('.booking-button');
    submitButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            // Lấy dữ liệu từ các phần tử HTML tương ứng
            var hotelName = this.parentElement.querySelector('.hotel-name').innerText;
            var locationName = this.parentElement.querySelector('.location-name').innerText;
            var placeID = this.parentElement.querySelector('.placeID').innerText;
            var price = this.parentElement.querySelector('.price').innerText;

            // Lưu dữ liệu vào localStorage
            localStorage.setItem("hotelName", hotelName);
            localStorage.setItem("locationName", locationName);    
            localStorage.setItem("placeID", placeID);
            localStorage.setItem("price", price);
            window.location.href = "booking.php"; // Thay đổi đường dẫn đến trang A nếu cần
        });
    });
});




document.addEventListener('DOMContentLoaded', function() {
    // Lấy các phần tử đích để thêm sự kiện click
    const quangninh = document.getElementById('quangninh');
    const danang = document.getElementById('danang');
    const dalat = document.getElementById('dalat');

    // Thêm sự kiện click cho phần tử "Quang Ninh"
    quangninh.addEventListener('click', function() {
        window.location.href = "detail/quangninh.html"; // Chuyển hướng đến trang quangninh.html
    });

    // Thêm sự kiện click cho phần tử "Da Nang"
    danang.addEventListener('click', function() {
        window.location.href = "detail/danang.html"; // Chuyển hướng đến trang danang.html
    });

    // Thêm sự kiện click cho phần tử "Da Lat"
    dalat.addEventListener('click', function() {
        window.location.href = "detail/dalat.html"; // Chuyển hướng đến trang dalat.html
    });
});