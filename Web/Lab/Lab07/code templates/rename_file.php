<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $data = json_decode(file_get_contents("php://input"), true);

    if (isset($data['oldFileName']) && isset($data['newFileName'])) {
        $oldFileName = $data['oldFileName'];
        $newFileName = $data['newFileName'];

        $uploadDirectory = '../uploads/';

        if (file_exists($uploadDirectory . $oldFileName) && !file_exists($uploadDirectory . $newFileName)) {
            if (rename($uploadDirectory . $oldFileName, $uploadDirectory . $newFileName)) {
                echo 'Đổi tên tập tin thành công';
            } else {
            }
        } else {
        }
    } else {
    }
} else {
}
?>