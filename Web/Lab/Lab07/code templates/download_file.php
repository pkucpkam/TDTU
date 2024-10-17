<?php
// Kiểm tra xem có tham số fileName được truyền từ yêu cầu không
if(isset($_GET['fileName'])) {
    $fileName = $_GET['fileName'];

    $filePath = '../uploads/' . $fileName;

    if(file_exists($filePath)) {
        header('Content-Description: File Transfer');
        header('Content-Type: application/octet-stream');
        header('Content-Disposition: attachment; filename="' . basename($filePath) . '"');
        header('Expires: 0');
        header('Cache-Control: must-revalidate');
        header('Pragma: public');
        header('Content-Length: ' . filesize($filePath));

        readfile($filePath);
        exit;
    } else {
        echo 'Tập tin không tồn tại.';
    }
} else {
    echo 'Thiếu tham số fileName.';
}
?>
