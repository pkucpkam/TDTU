'use strict';

/**
 * navbar toggle
 */

const overlay = document.querySelector("[data-overlay]");
const navOpenBtn = document.querySelector("[data-nav-open-btn]");
const navbar = document.querySelector("[data-navbar]");
const navCloseBtn = document.querySelector("[data-nav-close-btn]");
const navLinks = document.querySelectorAll("[data-nav-link]");

const navElemArr = [navOpenBtn, navCloseBtn, overlay];

const navToggleEvent = function (elem) {
  for (let i = 0; i < elem.length; i++) {
    elem[i].addEventListener("click", function () {
      navbar.classList.toggle("active");
      overlay.classList.toggle("active");
    });
  }
}

navToggleEvent(navElemArr);
navToggleEvent(navLinks);



/**
 * header sticky & go to top
 */

const header = document.querySelector("[data-header]");
const goTopBtn = document.querySelector("[data-go-top]");

window.addEventListener("scroll", function () {

  if (window.scrollY >= 200) {
    header.classList.add("active");
    goTopBtn.classList.add("active");
  } else {
    header.classList.remove("active");
    goTopBtn.classList.remove("active");
  }

});

document.addEventListener("DOMContentLoaded", function () {
  var submitButtons = document.querySelectorAll('.booking-button');
  submitButtons.forEach(function (button) {
    button.addEventListener('click', function () {
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
      window.location.href = "/Final/booking/booking.php";
    });
  });
});