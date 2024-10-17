<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "travel1";

$conn = new mysqli($servername, $username, $password, $dbname);

if (!$conn) {
    die("Kết nối không thành công: " . mysqli_connect_error());
}

// Lấy danh sách các địa điểm không nằm trong hot deals
$sql_available_places = "SELECT * FROM place WHERE placeID NOT IN (SELECT placeID FROM hotdeals)";
$result_available_places = mysqli_query($conn, $sql_available_places);

// Lấy danh sách các địa điểm đã có trong hot deals
$sql_hot_deals = "SELECT * FROM hotdeals";
$result_hot_deals = mysqli_query($conn, $sql_hot_deals);
?>

<h2>Hot Deals</h2>
<table border="1">
    <tr>
        <th>Place ID</th>
    </tr>
    <?php
    while ($row = mysqli_fetch_assoc($result_hot_deals)) {
        echo "<tr>";
        echo "<td>".$row['placeID']."</td>";
        echo "</tr>";
    }
    ?>
</table>

<h2>Add Hot Deal</h2>
<form id="addHotDealForm" action="add_hotdeal.php" method="POST">
    <label for="hotdealPlaceID">Place ID:</label>
    <select id="hotdealPlaceID" name="hotdealPlaceID">
        <?php
        while ($row = mysqli_fetch_assoc($result_available_places)) {
            echo "<option value='".$row['placeID']."'>".$row['placeID']."</option>";
        }
        ?>
    </select>
    <button type="submit">Add Hot Deal</button>
</form>

<h2>Delete Hot Deal</h2>
<form id="deleteHotDealForm" action="delete_hotdeal.php" method="POST">
    <label for="deleteHotDealID">Place ID:</label>
    <select id="deleteHotDealID" name="deleteHotDealID">
        <?php
        mysqli_data_seek($result_hot_deals, 0); // Đặt con trỏ của kết quả truy vấn lại về đầu
        while ($row = mysqli_fetch_assoc($result_hot_deals)) {
            echo "<option value='".$row['placeID']."'>".$row['placeID']."</option>";
        }
        ?>
    </select>
    <button type="submit">Delete Hot Deal</button>
</form>

<link rel="stylesheet" href="style.css">
