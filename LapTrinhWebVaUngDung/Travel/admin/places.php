<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "travel1";

$conn = new mysqli($servername, $username, $password, $dbname);

if (!$conn) {
    die("Kết nối không thành công: " . mysqli_connect_error());
}

$sql = "SELECT * FROM place";
$result = mysqli_query($conn, $sql);
?>

<h2>Places List</h2>
<table border="1">
    <tr>
        <th>Place ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Initial Price</th>
        <th>Location</th>
    </tr>
    <?php
    while ($row = mysqli_fetch_assoc($result)) {
        echo "<tr>";
        echo "<td>".$row['placeID']."</td>";
        echo "<td>".$row['name']."</td>";
        echo "<td>".$row['price']."</td>";
        echo "<td>".$row['initPrice']."</td>";
        echo "<td>".$row['locationName']."</td>";
        echo "</tr>";
    }
    ?>
</table>

<h2>Add Place</h2>
<form id="addPlaceForm" action="add_place.php" method="POST" enctype="multipart/form-data">
    <label for="placeName">Name:</label>
    <input type="text" id="placeName" name="placeName"><br><br>
    <label for="placePrice">Price:</label>
    <input type="text" id="placePrice" name="placePrice"><br><br>
    <label for="placeInitPrice">Initial Price:</label>
    <input type="text" id="placeInitPrice" name="placeInitPrice"><br><br>
    <label for="placeLocation">Location:</label>
    <input type="text" id="placeLocation" name="placeLocation"><br><br>
    <label for="placeImage">Image:</label>
    <input type="file" id="placeImage" name="placeImage"><br><br>
    <button type="submit">Add Place</button>
</form>

<h2>Delete Place</h2>
<form id="deletePlaceForm" action="delete_place.php" method="POST"> 
    <label for="deletePlaceID">Place ID:</label>
    <input type="text" id="deletePlaceID" name="deletePlaceID"><br><br>
    <button type="submit">Delete Place</button>
</form>


<div id="myModalPlaces" class="modal">
  <!-- Nội dung modal -->
  <div class="modal-content">
    <span class="close">&times;</span>
    <h2>Edit Place</h2>
    <form action="edit_place.php" method="POST">
        <input type="hidden" id="edit-placeID" name="edit-placeID">
        <label for="edit-name">Name:</label>
        <input type="text" id="edit-name" name="edit-name"><br><br>
        <label for="edit-price">Price:</label>
        <input type="text" id="edit-price" name="edit-price"><br><br>
        <label for="edit-initPrice">Initial Price:</label>
        <input type="text" id="edit-initPrice" name="edit-initPrice"><br><br>
        <label for="edit-location">Location:</label>
        <input type="text" id="edit-location" name="edit-location"><br><br>
        <input type="submit" value="Save">
    </form>
  </div>
</div>

<script>
// Lấy modal
var modalPlaces = document.getElementById("myModalPlaces");

// Lấy nút mở modal
var btnsPlaces = document.getElementsByTagName("td");

// Lấy phần span đóng modal
var spanPlaces = document.getElementsByClassName("close")[0];

// Khi người dùng nhấn vào một ô trong bảng
for (var i = 0; i < btnsPlaces.length; i++) {
    btnsPlaces[i].onclick = function() {
        var row = this.parentNode.getElementsByTagName("td");
        var placeID = row[0].innerText; // Lấy ID của địa điểm
        var name = row[1].innerText;
        var price = row[2].innerText;
        var initPrice = row[3].innerText;
        var location = row[4].innerText;

        // Điền thông tin vào form modal
        document.getElementById("edit-placeID").value = placeID;
        document.getElementById("edit-name").value = name;
        document.getElementById("edit-price").value = price;
        document.getElementById("edit-initPrice").value = initPrice;
        document.getElementById("edit-location").value = location;

        modalPlaces.style.display = "block";
    }
}

// Khi người dùng nhấn vào nút đóng (x), đóng modal
spanPlaces.onclick = function() {
    modalPlaces.style.display = "none";
}

// Khi người dùng nhấn ra ngoài modal, đóng modal
window.onclick = function(event) {
    if (event.target == modalPlaces) {
        modalPlaces.style.display = "none";
    }
}
</script>
<link rel="stylesheet" href="style.css">