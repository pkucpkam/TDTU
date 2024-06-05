<?php
require_once 'db.php';

function getInformation() {
    global $conn; // Sử dụng biến kết nối đã được định nghĩa trong db.php

    try {
        // Lấy số lượng sự cố
        $stmt = $conn->prepare("SELECT COUNT(*) as count FROM report where status = 'Pending'");
        $stmt->execute();
        $reportCount = $stmt->fetch(PDO::FETCH_ASSOC)['count'];

        // Lấy số lượng khách hàng
        $stmt = $conn->prepare("SELECT COUNT(*) as count FROM users");
        $stmt->execute();
        $userCount = $stmt->fetch(PDO::FETCH_ASSOC)['count'];

        // Lấy số lượng nhân viên
        $stmt = $conn->prepare("SELECT COUNT(*) as count FROM staff");
        $stmt->execute();
        $staffCount = $stmt->fetch(PDO::FETCH_ASSOC)['count'];

        // Lấy số lượng dịch vụ
        $stmt = $conn->prepare("SELECT COUNT(*) as count FROM service1");
        $stmt->execute();
        $serviceCount = $stmt->fetch(PDO::FETCH_ASSOC)['count'];

        // Trả về kết quả
        return array(
            'report' => $reportCount,
            'users' => $userCount,
            'service' => $serviceCount,
            'staff' => $staffCount
        );

    } catch(PDOException $e) {
        // Xử lý lỗi cụ thể (nếu cần)
        return "Error: " . $e->getMessage();
    }
}

function getQuanlity() {
    global $conn;

    try {
        $sql = "SELECT 
                    SUM(bikeQuanlity) AS total_bike_quantity,
                    SUM(carQuanlity) AS total_car_quantity
                FROM 
                    parkarea";

        $stmt = $conn->prepare($sql);
        $stmt->execute();

        $result = $stmt->fetch(PDO::FETCH_ASSOC);
        
        return array(
            'total_bike_quantity' => $result['total_bike_quantity'],
            'total_car_quantity' => $result['total_car_quantity']
        );

    } catch(PDOException $e) {
        return "Error: " . $e->getMessage();
    }
}

function getQuanlityUsed() {
    global $conn;

    try {
        // Chuẩn bị truy vấn SQL để lấy số lượng dòng của bảng `parkarea`
        $sql = "SELECT COUNT(*) AS row_count FROM parkarea where status = ";

        // Thực thi truy vấn
        $stmt = $conn->prepare($sql);
        $stmt->execute();

        // Lấy kết quả
        $result = $stmt->fetch(PDO::FETCH_ASSOC);
        
        // Lấy số lượng dòng từ kết quả
        $rowCount = $result['row_count'];

        // Trả về số lượng dòng
        return $rowCount;

    } catch(PDOException $e) {
        // Xử lý lỗi nếu có
        return "Error: " . $e->getMessage();
    }
}

function check() {
    return "OK";
}

?>
