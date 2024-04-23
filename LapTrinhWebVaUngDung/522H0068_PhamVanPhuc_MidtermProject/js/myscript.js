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


// Lưu trạng thái của menu chính khi ẩn
var isMainMenuHidden = false;

function toggleMainMenu() {
    var navMenu = document.getElementById("nav-menu");
    var subNav = document.getElementById("has-submenu");

    if (window.innerWidth < 310) {
        navMenu.style.display = "none";
        subNav.style.display = "none";
        isMainMenuHidden = true; // Lưu trạng thái
    } else if (window.innerWidth < 900) {
        navMenu.style.display = "none";
        subNav.style.display = "block";
    } else {
        subNav.style.display = "none";
        navMenu.style.display = "block";
    }
}

window.addEventListener('load', function() {
    toggleMainMenu(); // Gọi hàm để thiết lập trạng thái ban đầu

    // Nếu menu chính đang ẩn, tiếp tục ẩn khi tải lại trang
    if (isMainMenuHidden) {
        var navMenu = document.getElementById("nav-menu");
        var subNav = document.getElementById("has-submenu");
        navMenu.style.display = "none";
        subNav.style.display = "none";
    }
});


