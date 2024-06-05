<?php
require_once 'db.php';

function getEquipments() {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Chuẩn bị câu truy vấn SQL
        $sql = "SELECT * FROM equipment";

        // Chuẩn bị và thực thi câu truy vấn
        $stmt = $conn->prepare($sql);
        $stmt->execute();

        // Lấy danh sách trang thiết bị từ kết quả truy vấn
        $listEquipments = $stmt->fetchAll(PDO::FETCH_ASSOC);

        return $listEquipments;
    } catch (PDOException $e) {
        echo "Lỗi khi truy vấn danh sách trang thiết bị: " . $e->getMessage();
        return false;
    }
}

function addEquipment($equipmentName, $status) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Thêm trang thiết bị mới vào cơ sở dữ liệu
        $sql = "INSERT INTO equipment (equipmentID, equipmentName, status) 
                VALUES (:equipmentID, :equipmentName, :status)";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':equipmentID', $equipmentID);
        $stmt->bindParam(':equipmentName', $equipmentName);
        $stmt->bindParam(':status', $status);
        $stmt->execute();

        return true; // Trả về true nếu thêm trang thiết bị thành công
    } catch (PDOException $e) {
        echo "Lỗi khi thêm trang thiết bị mới: " . $e->getMessage();
        return false;
    }
}

function updateEquipment($equipmentID, $equipmentName, $status) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Cập nhật thông tin của trang thiết bị trong cơ sở dữ liệu
        $sql = "UPDATE equipment SET equipmentName = :equipmentName, status = :status 
                WHERE equipmentID = :equipmentID";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':equipmentName', $equipmentName);
        $stmt->bindParam(':status', $status);
        $stmt->bindParam(':equipmentID', $equipmentID);
        $stmt->execute();

        return true; // Trả về true nếu cập nhật trang thiết bị thành công
    } catch (PDOException $e) {
        echo "Lỗi khi cập nhật thông tin trang thiết bị: " . $e->getMessage();
        return false;
    }
}

function deleteEquipment($equipmentID) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Xóa trang thiết bị khỏi cơ sở dữ liệu
        $sql = "DELETE FROM equipment WHERE equipmentID = :equipmentID";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':equipmentID', $equipmentID);
        $stmt->execute();

        return true; // Trả về true nếu xóa trang thiết bị thành công
    } catch (PDOException $e) {
        echo "Lỗi khi xóa trang thiết bị: " . $e->getMessage();
        return false;
    }
}
?>
