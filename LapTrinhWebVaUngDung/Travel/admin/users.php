<?php
// Kết nối đến cơ sở dữ liệu
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "travel1";

$conn = new mysqli($servername, $username, $password, $dbname);

if (!$conn) {
    die("Kết nối không thành công: " . mysqli_connect_error());
}

$sql = "SELECT * FROM users";
$result = mysqli_query($conn, $sql);

?>

<h2>Users List</h2>
<table border="1">
    <tr>
        <th>Username</th>
        <th>Name</th>
        <th>Phone Number</th>
        <th>Email</th>
    </tr>
    <?php
    while ($row = mysqli_fetch_assoc($result)) {
        echo "<tr>";
        echo "<td data-username='" . $row['username'] . "'>" . $row['username'] . "</td>";
        echo "<td data-name='" . $row['name'] . "'>" . $row['name'] . "</td>";
        echo "<td data-phone='" . $row['phoneNumber'] . "'>" . $row['phoneNumber'] . "</td>";
        echo "<td data-email='" . $row['email'] . "'>" . $row['email'] . "</td>";
        echo "</tr>";
    }
    ?>
</table>


<h2>Add User</h2>
<form action="add_user.php" method="POST">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username"><br><br> 
    <label for="password">Password:</label>
    <input type="text" id="password" name="password"><br><br>
    <label for="name">Name:</label>
    <input type="text" id="name" name="name"><br><br>
    <label for="phone">Phone Number:</label>
    <input type="text" id="phone" name="phone"><br><br>
    <label for="email">Email:</label>
    <input type="text" id="email" name="email"><br><br>
    <input type="submit" value="Add User">
</form>

<h2>Delete User</h2>
<form action="delete_user.php" method="POST">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username"><br><br>
    <input type="submit" value="Delete User">
</form>


<!-- Modal -->
<div id="myModal" class="modal">
    <!-- Nội dung modal -->
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2>Edit User</h2>
        <form action="edit_user.php" method="POST">
            <input type="hidden" id="edit-username" name="edit-username">
            <label for="edit-name">Name:</label>
            <input type="text" id="edit-name" name="edit-name"><br><br>
            <label for="edit-phone">Phone Number:</label>
            <input type="text" id="edit-phone" name="edit-phone"><br><br>
            <label for="edit-email">Email:</label>
            <input type="text" id="edit-email" name="edit-email"><br><br>
            <input type="submit" value="Save">
        </form>
    </div>
</div>

<script>
    // Lấy modal
    var modal = document.getElementById("myModal");

    // Lấy nút mở modal
    var btns = document.getElementsByTagName("td");

    // Lấy phần span đóng modal
    var span = document.getElementsByClassName("close")[0];

    

    // Khi người dùng nhấn vào một ô trong bảng
for (var i = 0; i < btns.length; i++) {
    btns[i].onclick = function () {
        var row = this.parentNode.getElementsByTagName("td");
        var username = row[0].getAttribute("data-username");
        var name = row[1].getAttribute("data-name");
        var phone = row[2].getAttribute("data-phone");
        var email = row[3].getAttribute("data-email");

        document.getElementById("edit-username").value = username;
        document.getElementById("edit-name").value = name;
        document.getElementById("edit-phone").value = phone;
        document.getElementById("edit-email").value = email;
        modal.style.display = "block";
    }
}



    // Khi người dùng nhấn vào nút đóng (x), đóng modal
    span.onclick = function () {
        modal.style.display = "none";
    }

    // Khi người dùng nhấn ra ngoài modal, đóng modal
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    //Add user 
    document.addEventListener("DOMContentLoaded", function () {
        document.getElementById("addUserForm").addEventListener("submit", function (event) {
            // Ngăn chặn hành vi mặc định của biểu mẫu
            event.preventDefault();

            // Kiểm tra dữ liệu đã nhập
            var username = document.getElementById("username").value;
            var password = document.getElementById("password").value;
            var name = document.getElementById("name").value;
            var phone = document.getElementById("phone").value;
            var email = document.getElementById("email").value;

            // Kiểm tra dữ liệu hợp lệ
            if (username.trim() === '' || password.trim() === '' || name.trim() === '' || phone.trim() === '' || email.trim() === '') {
                alert("Vui lòng điền đầy đủ thông tin.");
                return;
            }

            // Gửi biểu mẫu
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "add_user.php", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        alert(xhr.responseText); // Hiển thị thông báo từ server
                    } else {
                        alert("Đã xảy ra lỗi: " + xhr.statusText);
                    }
                }
            };
            var formData = "username=" + encodeURIComponent(username) + "&password=" + encodeURIComponent(password) + "&name=" + encodeURIComponent(name) + "&phone=" + encodeURIComponent(phone) + "&email=" + encodeURIComponent(email);
            xhr.send(formData);
        });
    });


</script>

<link rel="stylesheet" href="style.css">