<?php 
require_once 'db.php';

function getService() {
    global $conn; // Sử dụng biến $conn được khai báo ở file db.php

    try {
        // Kiểm tra xem kết nối đã được thiết lập thành công chưa
        if (!$conn) {
            return false;
        }

        // Chuẩn bị câu truy vấn SQL
        $sql = "SELECT * FROM service1";

        // Chuẩn bị và thực thi câu truy vấn
        $stmt = $conn->prepare($sql);
        $stmt->execute();

        // Lấy danh sách dich vu từ kết quả truy vấn
        $listService = $stmt->fetchAll(PDO::FETCH_ASSOC);

        // Đóng kết nối (không bắt buộc với PDO)
        // $conn = null;

        // Trả về danh sách nhân viên
        return $listService;
    } catch (PDOException $e) {
        // Xử lý lỗi nếu có
        echo "Lỗi khi truy vấn danh sách nhân viên: " . $e->getMessage();
        return false;
    }
}

function addService($serviceName, $serviceDescription) {
    global $conn;

    try {
        // Chuẩn bị câu truy vấn SQL
        $sql = "INSERT INTO service1 (nameService, description) VALUES (:serviceName, :serviceDescription)";

        // Chuẩn bị và thực thi câu truy vấn
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':serviceName', $serviceName);
        $stmt->bindParam(':serviceDescription', $serviceDescription);
        $stmt->execute();

        // Trả về true nếu thêm dịch vụ thành công
        return true;
    } catch (PDOException $e) {
        // Xử lý lỗi nếu có
        echo "Lỗi khi thêm dịch vụ: " . $e->getMessage();
        return false;
    }
}


function deleteService($serviceId) {
    global $conn;

    try {
        // Chuẩn bị câu truy vấn SQL
        $sql = "DELETE FROM service1 WHERE serviceID = :serviceId";

        // Chuẩn bị và thực thi câu truy vấn
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':serviceId', $serviceId);
        $stmt->execute();

        // Trả về true nếu xóa dịch vụ thành công
        return true;
    } catch (PDOException $e) {
        // Xử lý lỗi nếu có
        echo "Lỗi khi xóa dịch vụ: " . $e->getMessage();
        return false;
    }
}


function updateService($serviceId, $serviceName, $serviceDescription) {
    global $conn;

    try {
        // Chuẩn bị câu truy vấn SQL với tên bảng là service1
        $sql = "UPDATE service1 SET nameService = :serviceName, description = :serviceDescription WHERE serviceID = :serviceId";

        // Chuẩn bị và thực thi câu truy vấn
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':serviceId', $serviceId);
        $stmt->bindParam(':serviceName', $serviceName);
        $stmt->bindParam(':serviceDescription', $serviceDescription);
        $stmt->execute();

        // Trả về true nếu cập nhật dịch vụ thành công
        return true;
    } catch (PDOException $e) {
        // Xử lý lỗi nếu có
        echo "Lỗi khi cập nhật dịch vụ: " . $e->getMessage();
        return false;
    }
}



?>

