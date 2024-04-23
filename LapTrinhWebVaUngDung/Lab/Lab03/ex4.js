var currentIndex = 0;
var images = [
    "../images/pic_001.jpg",
    "./images/pic_002.jpg",
    "./images/pic_003.jpg",
    "./images/pic_004.jpg",
    "./images/pic_005.jpg",
    "./images/pic_006.jpg",
    "./images/pic_007.jpg",
    "./images/pic_008.jpg",
    "./images/pic_009.jpg",
    "./images/pic_010.jpg"
];

var des = [
    "pic_001.jpg (10/55)",
    "pic_002.jpg (11/55)",
    "pic_003.jpg (12/55)",
    "pic_004.jpg (13/55)",
    "pic_005.jpg (14/55)",
    "pic_006.jpg (15/55)",
    "pic_007.jpg (16/55)",
    "pic_008.jpg (17/55)",
    "pic_009.jpg (18/55)",
    "pic_010.jpg (19/55)"
];


var ind = 0;
var a; 

function slideShow() {
    var butName = document.getElementById("slideButton")
    if (butName.textContent == "Start slideshow") {
        a = setInterval(nextImg,2000) 
        butName.innerText = "Stop slideshow"
    }
    else {
        butName.innerText = "Start slideshow"
        clearInterval(a)
    }
}

function nextImg() {
     
    var sImg = document.getElementsByTagName("img")[0]
    var text = document.getElementsByTagName("p")[0]
    if (ind == 9) {
        sImg.src = images[0]
        ind = 0;
        text.innerText = des[0]
    }
    else {
        ind = ind + 1
        sImg.src = images[ind]
        text.innerText = des[ind]
    }
}

function preImg() {
    var sImg = document.getElementsByTagName("img")[0]
    var text = document.getElementsByTagName("p")[0]
    if (ind == 0) {
        sImg.src = images[9]
        ind = 9;
        text.innerText = des[9]
    }
    else {
        ind = ind - 1
        sImg.src = images[ind]
        text.innerText = des[ind]
    }
}
