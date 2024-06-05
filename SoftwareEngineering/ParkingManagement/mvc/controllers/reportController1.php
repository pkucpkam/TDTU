<?php 
session_start();
require_once('../models/reportModel.php');

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['action']) && $_POST['action'] === 'solveReport') {
    if (isset($_POST['reportID']) && is_numeric($_POST['reportID'])) {
        $reportID = $_POST['reportID'];
        // Gọi hàm để cập nhật trạng thái
        $result = changeStatus($reportID);
        // Trả kết quả dưới dạng JSON
        echo $result;
    }
}

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['submitReport'])) {
    // Lấy dữ liệu POST và chuyển thành chuỗi JSON
    // Check if complaint and typeReport are set
    if (isset($_POST['complaint'], $_POST['typeReport'])) {
        $complaint = $_POST['complaint'];
        $typeReport = $_POST['typeReport'];
        $username = $_SESSION['username'];

        // Call the function to save the report
        $result = saveReport($username , $typeReport, $complaint);

        // Return success or failure response
        if ($result) {
            echo json_encode(array('success' => true));
        } else {
            echo json_encode(array('success' => false, 'message' => 'Failed to save report.'));
        }
    } else {
        // Return error response if complaint or typeReport is not set
        echo json_encode(array('success' => false, 'message' => 'Complaint or type of report is missing.'));
    }
}
?>