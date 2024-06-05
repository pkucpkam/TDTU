<?php
require_once 'db.php';

function getListStaffs()
{
    global $conn; // Sử dụng biến $conn được khai báo ở file db.php

    try {
        // Kiểm tra xem kết nối đã được thiết lập thành công chưa
        if (!$conn) {
            return false;
        }

        // Chuẩn bị câu truy vấn SQL
        $sql = "SELECT * FROM staff";

        // Chuẩn bị và thực thi câu truy vấn
        $stmt = $conn->prepare($sql);
        $stmt->execute();

        // Lấy danh sách nhân viên từ kết quả truy vấn
        $listStaffs = $stmt->fetchAll(PDO::FETCH_ASSOC);

        // Đóng kết nối (không bắt buộc với PDO)
        // $conn = null;

        // Trả về danh sách nhân viên
        return $listStaffs;
    } catch (PDOException $e) {
        // Xử lý lỗi nếu có
        echo "Lỗi khi truy vấn danh sách nhân viên: " . $e->getMessage();
        return false;
    }
}

function getListAttendant()
{
    global $conn; // Sử dụng biến $conn được khai báo ở file db.php

    try {
        // Kiểm tra xem kết nối đã được thiết lập thành công chưa
        if (!$conn) {
            return false;
        }

        // Chuẩn bị câu truy vấn SQL
        $sql = "SELECT * FROM staff INNER JOIN account ON staff.username = account.username WHERE account.role = 'attendant'";

        // Chuẩn bị và thực thi câu truy vấn
        $stmt = $conn->prepare($sql);
        $stmt->execute();

        // Lấy danh sách nhân viên từ kết quả truy vấn
        $listStaffs = $stmt->fetchAll(PDO::FETCH_ASSOC);

        // Đóng kết nối (không bắt buộc với PDO)
        // $conn = null;

        // Trả về danh sách nhân viên
        return $listStaffs;
    } catch (PDOException $e) {
        // Xử lý lỗi nếu có
        echo "Lỗi khi truy vấn danh sách nhân viên: " . $e->getMessage();
        return false;
    }
}

function addStaff($username, $full_name, $phone_number, $address, $email, $role, $salary) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Check if the username already exists
        if (checkUsername($username)) {
            echo "Username already exists.";
            return false;
        }

        // Prepare the SQL statement to insert data into the staff table
        $sql_staff = "INSERT INTO staff (username, salary, full_name, phone_number, address, email) VALUES (?, ?, ?, ?, ?, ?)";
        $stmt_staff = $conn->prepare($sql_staff);
        $stmt_staff->execute([$username, $salary, $full_name, $phone_number, $address, $email]);

        // Prepare the SQL statement to retrieve the auto-generated staffID
        $staffID = $conn->lastInsertId();

        // Prepare the SQL statement to insert data into the account table
        $password = "1"; // Default password is "1"
        $role = "Nhân viên"; // Set default role
        $sql_account = "INSERT INTO account (username, password, role) VALUES (?, ?, ?)";
        $stmt_account = $conn->prepare($sql_account);
        $stmt_account->execute([$username, $password, $role]);

        // Return true if insertion is successful
        return true;
    } catch (PDOException $e) {
        // Handle any errors
        echo "Error adding new staff: " . $e->getMessage();
        return false;
    }
}


function checkUsername($username) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Prepare the SQL statement to check if the username already exists
        $sql_check = "SELECT COUNT(*) as count FROM staff WHERE username = ?";
        $stmt_check = $conn->prepare($sql_check);
        $stmt_check->execute([$username]);
        $result = $stmt_check->fetch(PDO::FETCH_ASSOC);

        // If the count is greater than 0, it means the username already exists
        if ($result['count'] > 0) {
            return true;
        } else {
            return false;
        }
    } catch (PDOException $e) {
        // Handle any errors
        echo "Error checking username: " . $e->getMessage();
        return false;
    }
}


function deleteStaff($staffID) {
    global $conn;

    try {
        // Start a transaction
        $conn->beginTransaction();

        // Delete staff record
        $sqlStaff = "DELETE FROM staff WHERE staffID = ?";
        $stmtStaff = $conn->prepare($sqlStaff);
        $stmtStaff->execute([$staffID]);

        // Commit the transaction
        $conn->commit();

        // Return true if deletion is successful
        return true;
    } catch (PDOException $e) {
        // Rollback the transaction if an error occurs
        $conn->rollBack();

        // Handle any errors
        echo "Error deleting staff: " . $e->getMessage();
        return false;
    }
}


function updateStaff($staffID, $fullname, $phoneNumber, $address, $email, $salary) {
    global $conn;

    try {
        // Prepare the SQL statement to update staff information
        $sql = "UPDATE staff SET full_name = ?, phone_number = ?, address = ?, email = ?, salary = ? WHERE staffID = ?";
        $stmt = $conn->prepare($sql);
        $stmt->execute([$fullname, $phoneNumber, $address, $email, $salary, $staffID]);

        // Return true if update is successful
        return true;
    } catch (PDOException $e) {
        // Handle any errors
        echo "Error updating staff: " . $e->getMessage();
        return false;
    }
}

?>
