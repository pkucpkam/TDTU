<?php
$servername = "pkuc.site";
$username = "iovq1r3ujzst_root";
$password = "Phucpham1803*";
$dbname = "iovq1r3ujzst_travel1";

$conn = new mysqli($servername, $username, $password, $dbname);

if (!$conn) {
    die("Kết nối không thành công: " . mysqli_connect_error());
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $placeID = $_POST['hotdealPlaceID'];

    $sql = "INSERT INTO hotdeals (placeID) VALUES ('$placeID')";

    if (mysqli_query($conn, $sql)) {
        echo "<script>alert('Thêm hotdeals thành công.'); window.location.href = 'admin.html'; </script>";
    } else {
        echo "Lỗi: " . $sql . "<br>" . mysqli_error($conn);
    }
}

mysqli_close($conn);
?>