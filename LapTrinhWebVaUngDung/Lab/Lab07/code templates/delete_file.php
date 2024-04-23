<?php

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $requestData = json_decode(file_get_contents('php://input'), true);
    $fileName = $requestData['fileName'];


    $directory = '../uploads/';

    $filePath = $directory . $fileName;

    if (file_exists($filePath)) {
        if (is_file($filePath)) {
            if (unlink($filePath)) {
                echo json_encode(['success' => true]);
            } else {
                echo json_encode(['success' => false, 'message' => 'Xóa tệp không thành công']);
            }
        } elseif (is_dir($filePath)) {
            if (rmdir($filePath)) {
                echo json_encode(['success' => true]);
            } else {
                echo json_encode(['success' => false, 'message' => 'Xóa thư mục không thành công']);
            }
        } else {
            echo json_encode(['success' => false, 'message' => 'Đường dẫn không phải là tệp hoặc thư mục']);
        }
    } else {
        echo json_encode(['success' => false, 'message' => 'Đường dẫn không tồn tại']);
    }
} else {
    echo json_encode(['success' => false, 'message' => 'Phương thức yêu cầu không hợp lệ']);
}
?>
