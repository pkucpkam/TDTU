<!-- noticeModel.php -->
<?php
require_once 'db.php';

function getNotices() {
    global $conn; 

    try {
        if (!$conn) {
            return false;
        }

        $sql = "SELECT * FROM notice"; 

        $stmt = $conn->prepare($sql);
        $stmt->execute();

        $listNotices = $stmt->fetchAll(PDO::FETCH_ASSOC);

        return $listNotices;
    } catch (PDOException $e) {
        echo "Lỗi khi truy vấn danh sách thông báo: " . $e->getMessage();
        return false;
    }
}

function getNoticesByRole($role, $username) {
    global $conn; 

    try {
        if (!$conn) {
            return false;
        }

        // Sử dụng các tham số và gán giá trị cho chúng
        $sql = "SELECT * FROM notice WHERE target = :role OR target = :username"; 

        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':role', $role);
        $stmt->bindParam(':username', $username);
        $stmt->execute();

        $listNotices = $stmt->fetchAll(PDO::FETCH_ASSOC);

        return $listNotices;
    } catch (PDOException $e) {
        echo "Lỗi khi truy vấn danh sách thông báo: " . $e->getMessage();
        return false;
    }
}


function addNotice($typeNotice, $target, $content) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        $sql = "INSERT INTO notice (typeNotice, target, content) 
                VALUES (:typeNotice, :target, :content)";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':typeNotice', $typeNotice);
        $stmt->bindParam(':target', $target);
        $stmt->bindParam(':content', $content);
        $stmt->execute();

        return true;
    } catch (PDOException $e) {
        echo "Lỗi khi thêm thông báo mới: " . $e->getMessage();
        return false;
    }
}

function updateNotice($noticeID, $typeNotice, $target, $content) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        $sql = "UPDATE notice SET typeNotice = :typeNotice, target = :target, 
                content = :content WHERE noticeID = :noticeID";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':typeNotice', $typeNotice);
        $stmt->bindParam(':target', $target);
        $stmt->bindParam(':content', $content);
        $stmt->bindParam(':noticeID', $noticeID);
        $stmt->execute();

        return true;
    } catch (PDOException $e) {
        echo "Lỗi khi cập nhật thông tin thông báo: " . $e->getMessage();
        return false;
    }
}

function deleteNotice($noticeID) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        $sql = "DELETE FROM notice WHERE noticeID = :noticeID";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':noticeID', $noticeID);
        $stmt->execute();

        return true;
    } catch (PDOException $e) {
        echo "Lỗi khi xóa thông báo: " . $e->getMessage();
        return false;
    }
}
?>
