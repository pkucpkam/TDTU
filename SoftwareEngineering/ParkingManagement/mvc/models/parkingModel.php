<?php 
require_once 'db.php';

function addParkingArea($areaID, $cardID, $typeVehicle, $licensePlate) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Thực hiện trừ tiền từ tài khoản
        $deductionSuccess = deductionBalance($cardID, $typeVehicle);

        // Nếu trừ tiền thành công, thêm thông tin mới vào bảng parkingArea
        if ($deductionSuccess) {
            $sql = "INSERT INTO parkingarea (areaID, IDCard, typeVehicle, status, licensePlate) 
                    VALUES (:areaID, :cardID, :typeVehicle, 'on', :licensePlate)";
            $stmt = $conn->prepare($sql);
            $stmt->bindParam(':areaID', $areaID);
            $stmt->bindParam(':cardID', $cardID);
            $stmt->bindParam(':typeVehicle', $typeVehicle);
            $stmt->bindParam(':licensePlate', $licensePlate);
            $stmt->execute();

            return true; // Trả về true nếu thêm dữ liệu thành công
        } else {
            // Nếu trừ tiền không thành công, trả về false
            return false;
        }
    } catch (PDOException $e) {
        echo "Lỗi khi thêm dữ liệu vào bảng parkingArea: " . $e->getMessage();
        return false;
    }
}


function addParkingAreaNormal($areaID, $cardID, $typeVehicle, $licensePlate) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Thêm thông tin mới vào bảng parkingArea
        $sql = "INSERT INTO parkingarea (areaID, IDCard, typeVehicle, status, licensePlate) 
                VALUES (:areaID, :cardID, :typeVehicle, 'on', :licensePlate)";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':areaID', $areaID);
        $stmt->bindParam(':cardID', $cardID);
        $stmt->bindParam(':typeVehicle', $typeVehicle);
        $stmt->bindParam(':licensePlate', $licensePlate);
        $stmt->execute();


        return true; // Trả về true nếu thêm dữ liệu thành công
    } catch (PDOException $e) {
        echo "Lỗi khi thêm dữ liệu vào bảng parkingArea: " . $e->getMessage();
        return false;
    }
}


function deductionBalance($IDCard, $typeVehicle) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Kiểm tra xem IDCard có tồn tại trong bảng users không
        $check_sql = "SELECT * FROM users WHERE IDCard = :IDCard";
        $check_stmt = $conn->prepare($check_sql);
        $check_stmt->bindParam(':IDCard', $IDCard);
        $check_stmt->execute();
        $user = $check_stmt->fetch(PDO::FETCH_ASSOC);

        if (!$user) {
            // Nếu không tìm thấy người dùng, trả về false
            return false;
        }

        $vehicle_type = $typeVehicle;

        // Xác định số tiền sẽ trừ tùy thuộc vào loại phương tiện
        $amount_to_deduct = ($vehicle_type === 'car') ? 2000 : 1000;

        if ($user['accountBalance'] >= $amount_to_deduct) {
            // Nếu đủ tiền để trừ, thực hiện trừ tiền
            $update_sql = "UPDATE users SET accountBalance = accountBalance - :amount WHERE IDCard = :IDCard";
            $update_stmt = $conn->prepare($update_sql);
            $update_stmt->bindParam(':amount', $amount_to_deduct);
            $update_stmt->bindParam(':IDCard', $IDCard);
            $update_stmt->execute();

            return true; // Trả về true nếu trừ tiền thành công
        } else {
            // Nếu không đủ tiền để trừ, trả về false
            return false;
        }
    } catch (PDOException $e) {
        echo "Lỗi khi trừ tiền từ tài khoản: " . $e->getMessage();
        return false;
    }
}


function getParkInfor() {
    global $conn; 

    try {
        if (!$conn) {
            return false;
        }

        $sql = "SELECT * FROM parkingarea where status = 'on'"; 

        $stmt = $conn->prepare($sql);
        $stmt->execute();

        $listVehicle = $stmt->fetchAll(PDO::FETCH_ASSOC);

        return $listVehicle;
    } catch (PDOException $e) {
        echo "Lỗi khi truy vấn danh sách thông báo: " . $e->getMessage();
        return false;
    }
}

function checkIDCardExist($IDCard) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Thực hiện câu truy vấn SQL để kiểm tra sự tồn tại của IDCard trong bảng parkingarea
        $sql = "SELECT COUNT(*) AS count FROM parkingarea WHERE licensePlate = :IDCard";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':IDCard', $IDCard);
        $stmt->execute();
        $result = $stmt->fetch(PDO::FETCH_ASSOC);

        // Trả về true nếu IDCard tồn tại, ngược lại trả về false
        return ($result['count'] > 0);
    } catch (PDOException $e) {
        echo "Lỗi khi kiểm tra sự tồn tại của IDCard: " . $e->getMessage();
        return false;
    }
}

function changeStatus($IDCard) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Kiểm tra sự tồn tại của IDCard trước khi cập nhật trạng thái của xe
        if (!checkIDCardExist($IDCard)) {
            echo "IDCard không tồn tại";
            return false;
        }

        // Thực hiện câu truy vấn SQL để cập nhật trạng thái của xe về "off"
        $sql = "UPDATE parkingarea SET status = 'off' WHERE IDCard = :IDCard AND status ='on'";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':IDCard', $IDCard);
        $stmt->execute();

        return true; // Trả về true nếu cập nhật thành công
    } catch (PDOException $e) {
        echo "Lỗi khi cập nhật trạng thái của xe: " . $e->getMessage();
        return false;
    }
}


function getParkingAvailability($areaID, $typeVehicle) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Truy vấn cơ sở dữ liệu để đếm số lượng chỗ đỗ xe trống trong khu vực và loại phương tiện cụ thể
        $sql = "SELECT COUNT(*) AS total FROM parkingarea WHERE areaID = :areaID AND typeVehicle = :typeVehicle AND status = 'on'";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':areaID', $areaID);
        $stmt->bindParam(':typeVehicle', $typeVehicle);
        $stmt->execute();

        $result = $stmt->fetch(PDO::FETCH_ASSOC);

        // Trả về số lượng chỗ đỗ xe trống
        return getQuanlity($typeVehicle) - $result['total'] - getBooked($typeVehicle, $areaID);
    } catch (PDOException $e) {
        echo "Lỗi khi lấy số lượng chỗ đỗ xe trống: " . $e->getMessage();
        return false;
    }
}

function getQuanlity($typeVehicle) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Khai báo biến để lưu trữ tên cột cần lấy dựa trên loại phương tiện
        $quantityColumn = ($typeVehicle === 'bike') ? 'bikeQuanlity' : 'carQuanlity';

        // Truy vấn cơ sở dữ liệu để lấy số lượng chỗ đỗ xe trống dựa trên loại phương tiện
        $sql = "SELECT $quantityColumn FROM parkarea";
        $stmt = $conn->prepare($sql);
        $stmt->execute();

        $result = $stmt->fetch(PDO::FETCH_ASSOC);

        // Trả về số lượng chỗ đỗ xe trống
        return $result[$quantityColumn];
    } catch (PDOException $e) {
        echo "Lỗi khi lấy số lượng chỗ đỗ xe trống: " . $e->getMessage();
        return false;
    }
}

function getBooked($typeVehicle, $areaID) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Truy vấn thông tin cấu trúc của bảng
        $stmt = $conn->prepare("SELECT * FROM getplaceinfor WHERE areaID = :areaID AND typeVehicle = :typeVehicle");
        
        // Gắn các tham số định danh
        $stmt->bindParam(':areaID', $areaID);
        $stmt->bindParam(':typeVehicle', $typeVehicle);
        
        // Thực thi câu truy vấn
        $stmt->execute();

        // Đếm số cột trong kết quả trả về
        $columnCount = $stmt->columnCount();

        // Trả về số lượng cột
        return $columnCount;
    
    } catch (PDOException $e) {
        echo "Lỗi khi lấy số lượng chỗ đỗ xe trống: " . $e->getMessage();
        return false;
    }
}

function reserveParkingPlace($username, $cardID, $typeVehicle, $areaID) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Thực hiện trừ tiền từ tài khoản
        $deductionSuccess = deductionBalanceByUsername($username, $typeVehicle);

        // Nếu trừ tiền thành công, thêm thông tin đặt chỗ vào bảng getplaceinfor
        if ($deductionSuccess) {
            $sql = "INSERT INTO getplaceinfor (username, cardID, typeVehicle, areaID) VALUES (:username, :cardID, :typeVehicle, :areaID)";
            $stmt = $conn->prepare($sql);
            $stmt->bindParam(':username', $username);
            $stmt->bindParam(':cardID', $cardID);
            $stmt->bindParam(':typeVehicle', $typeVehicle);
            $stmt->bindParam(':areaID', $areaID);
            $stmt->execute();

            return true; // Trả về true nếu thêm dữ liệu thành công
        } else {
            // Nếu trừ tiền không thành công, trả về false
            return false;
        }
    } catch (PDOException $e) {
        echo "Lỗi khi thêm dữ liệu vào bảng getplaceinfor: " . $e->getMessage();
        return false;
    }
}

function deductionBalanceByUsername($username, $typeVehicle) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Kiểm tra xem username có tồn tại trong bảng users không
        $check_sql = "SELECT * FROM users WHERE username = :username";
        $check_stmt = $conn->prepare($check_sql);
        $check_stmt->bindParam(':username', $username);
        $check_stmt->execute();
        $user = $check_stmt->fetch(PDO::FETCH_ASSOC);

        if (!$user) {
            // Nếu không tìm thấy người dùng, trả về false
            return false;
        }

        $vehicle_type = $typeVehicle;

        // Xác định số tiền sẽ trừ tùy thuộc vào loại phương tiện
        $amount_to_deduct = ($vehicle_type === 'car') ? 2000 : 1000;

        if ($user['accountBalance'] >= $amount_to_deduct) {
            // Nếu đủ tiền để trừ, thực hiện trừ tiền
            $update_sql = "UPDATE users SET accountBalance = accountBalance - :amount WHERE username = :username";
            $update_stmt = $conn->prepare($update_sql);
            $update_stmt->bindParam(':amount', $amount_to_deduct);
            $update_stmt->bindParam(':username', $username);
            $update_stmt->execute();

            return true; // Trả về true nếu trừ tiền thành công
        } else {
            // Nếu không đủ tiền để trừ, trả về false
            return false;
        }
    } catch (PDOException $e) {
        echo "Lỗi khi trừ tiền từ tài khoản: " . $e->getMessage();
        return false;
    }
}

function checkReserve($username) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Chuẩn bị câu truy vấn SQL để kiểm tra sự tồn tại của username trong bảng getplaceinfor
        $check_sql = "SELECT * FROM getplaceinfor WHERE username = :username";
        $check_stmt = $conn->prepare($check_sql);
        $check_stmt->bindParam(':username', $username);
        $check_stmt->execute();
        $result = $check_stmt->fetch(PDO::FETCH_ASSOC);

        // Nếu có bản ghi tồn tại, trả về true, ngược lại trả về false
        return $result ? true : false;
    } catch (PDOException $e) {
        // Xử lý lỗi nếu có
        echo "Lỗi khi kiểm tra sự tồn tại của username: " . $e->getMessage();
        return false;
    }
}


function deleteReserve($username) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Chuẩn bị câu truy vấn SQL để xóa các bản ghi có cardID tương ứng
        $delete_sql = "DELETE FROM getplaceinfor WHERE cardID = :cardID";
        $delete_stmt = $conn->prepare($delete_sql);
        $delete_stmt->bindParam(':cardID', $cardID);
        $delete_stmt->execute();

        // Trả về true nếu xóa thành công
        return true;
    } catch (PDOException $e) {
        // Xử lý lỗi nếu có
        echo "Lỗi khi xóa bản ghi trong bảng getplaceinfor: " . $e->getMessage();
        return false;
    }
}


function deleteFromReserve($cardID) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Lấy username dựa trên cardID
        $check_sql = "SELECT username FROM users WHERE IDCard = :cardID";
        $check_stmt = $conn->prepare($check_sql);
        $check_stmt->bindParam(':cardID', $cardID);
        $check_stmt->execute();
        $user = $check_stmt->fetch(PDO::FETCH_ASSOC);

        if (!$user) {
            // Nếu không tìm thấy người dùng, trả về false
            return false;
        }

        $username = $user['username'];

        // Xóa các bản ghi có username tương ứng trong bảng getplaceinfor
        $delete_sql = "DELETE FROM getplaceinfor WHERE username = :username";
        $delete_stmt = $conn->prepare($delete_sql);
        $delete_stmt->bindParam(':username', $username);
        $delete_stmt->execute();

        return true; // Trả về true nếu xóa thành công
    } catch (PDOException $e) {
        echo "Lỗi khi xóa bản ghi trong bảng getplaceinfor: " . $e->getMessage();
        return false;
    }
}

function getListReserve() {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Truy vấn để lấy tất cả các bản ghi từ bảng getplaceinfor
        $sql = "SELECT * FROM getplaceinfor";
        $stmt = $conn->prepare($sql);
        $stmt->execute();

        // Lấy tất cả các bản ghi dưới dạng mảng kết hợp
        $listReserve = $stmt->fetchAll(PDO::FETCH_ASSOC);

        return $listReserve; // Trả về danh sách các bản ghi
    } catch (PDOException $e) {
        echo "Lỗi khi lấy danh sách các bản ghi từ bảng getplaceinfor: " . $e->getMessage();
        return false;
    }
}

function deleteFromReserveByUsername($username) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Chuẩn bị câu truy vấn SQL để xóa các bản ghi có username tương ứng
        $delete_sql = "DELETE FROM getplaceinfor WHERE username = :username";
        $delete_stmt = $conn->prepare($delete_sql);
        $delete_stmt->bindParam(':username', $username);
        $delete_stmt->execute();

        // Trả về true nếu xóa thành công
        return true;
    } catch (PDOException $e) {
        // Xử lý lỗi nếu có
        echo "Lỗi khi xóa bản ghi trong bảng getplaceinfor: " . $e->getMessage();
        return false;
    }
}


function deleteFromGetPlace($cardID) {
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        // Chuẩn bị câu truy vấn SQL để xóa các bản ghi có cardID tương ứng
        $delete_sql = "DELETE FROM getplaceinfor WHERE cardID = :cardID";
        $delete_stmt = $conn->prepare($delete_sql);
        $delete_stmt->bindParam(':cardID', $cardID);
        $delete_stmt->execute();

        // Trả về true nếu xóa thành công
        return true;
    } catch (PDOException $e) {
        // Xử lý lỗi nếu có
        echo "Lỗi khi xóa bản ghi trong bảng getplaceinfor: " . $e->getMessage();
        return false;
    }
}

?>