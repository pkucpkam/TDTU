<?php
require_once 'db.php';

function getPayByID($username)
{
    global $conn; // Sử dụng biến $conn được khai báo ở file db.php

    try {
        // Kiểm tra xem kết nối đã được thiết lập thành công chưa
        if (!$conn) {
            return false;
        }

        // Chuẩn bị câu truy vấn SQL
        $sql = "SELECT * FROM payment WHERE username = :username";

        // Chuẩn bị và thực thi câu truy vấn
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':username', $username); // Gán giá trị cho tham số :username
        $stmt->execute();

        // Lấy danh sách dịch vụ từ kết quả truy vấn
        $listService = $stmt->fetchAll(PDO::FETCH_ASSOC);

        // Trả về danh sách dịch vụ
        return $listService;
    } catch (PDOException $e) {
        // Xử lý lỗi nếu có
        echo "Lỗi khi truy vấn danh sách dịch vụ: " . $e->getMessage();
        return false;
    }
}

function getPay()
{
    global $conn; // Sử dụng biến $conn được khai báo ở file db.php

    try {
        // Kiểm tra xem kết nối đã được thiết lập thành công chưa
        if (!$conn) {
            return false;
        }

        // Chuẩn bị câu truy vấn SQL
        $sql = "SELECT * FROM payment where status = 'Pending'";

        // Chuẩn bị và thực thi câu truy vấn
        $stmt = $conn->prepare($sql);
        $stmt->execute();

        // Lấy danh sách dịch vụ từ kết quả truy vấn
        $listService = $stmt->fetchAll(PDO::FETCH_ASSOC);

        // Trả về danh sách dịch vụ
        return $listService;
    } catch (PDOException $e) {
        // Xử lý lỗi nếu có
        echo "Lỗi khi truy vấn danh sách dịch vụ: " . $e->getMessage();
        return false;
    }
}

function addPay($username, $amount)
{
    global $conn;

    try {
        // Kiểm tra xem kết nối đã được thiết lập thành công chưa
        if (!$conn) {
            return false;
        }

        // Chuẩn bị câu truy vấn SQL
        $sql = "INSERT INTO payment (username, amount, time, status) VALUES (:username, :amount, NOW(), 'Pending')";

        // Chuẩn bị và thực thi câu truy vấn
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':username', $username);
        $stmt->bindParam(':amount', $amount);
        $stmt->execute();

        // Trả về true nếu thêm thành công
        return true;
    } catch (PDOException $e) {
        // Xử lý lỗi nếu có
        echo "Lỗi khi thêm giao dịch nạp tiền: " . $e->getMessage();
        return false;
    }
}


function updatePaymentStatus($paymentID, $status)
{
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        $sql = "UPDATE payment SET status = :status WHERE paymentID = :paymentID";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':status', $status);
        $stmt->bindParam(':paymentID', $paymentID);
        $stmt->execute();

        addMoney($paymentID);

        return true;
    } catch (PDOException $e) {
        // Log error or handle it appropriately
        return false;
    }
}


function addMoney($paymentID)
{
    global $conn; // Use the global connection variable declared in db.php

    try {
        // Begin a transaction
        $conn->beginTransaction();

        // Get username and amount from the payment table
        $stmt = $conn->prepare("SELECT username, amount FROM payment WHERE paymentID = :paymentID");
        $stmt->bindParam(':paymentID', $paymentID);
        $stmt->execute();
        $payment = $stmt->fetch(PDO::FETCH_ASSOC);

        // Check if payment exists
        if (!$payment) {
            throw new Exception("Payment not found");
        }

        $username = $payment['username'];
        $amount = $payment['amount'];

        // Update user's account balance
        $stmt = $conn->prepare("UPDATE users SET accountBalance = accountBalance + :amount WHERE username = :username");
        $stmt->bindParam(':amount', $amount);
        $stmt->bindParam(':username', $username);
        $stmt->execute();

        // Commit the transaction
        $conn->commit();

        return true; // Return true if successful
    } catch (Exception $e) {
        // Rollback the transaction if an error occurred
        $conn->rollBack();
        // You can log or handle the error here
        return false; // Return false to indicate failure
    }
}

?>