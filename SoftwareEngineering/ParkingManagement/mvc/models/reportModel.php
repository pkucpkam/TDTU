<?php
require_once 'db.php';

function getListReports()
{
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        $sql = "SELECT * FROM report WHERE status = 'Pending'";

        $stmt = $conn->prepare($sql);
        $stmt->execute();

        $listReports = $stmt->fetchAll(PDO::FETCH_ASSOC);

        return $listReports;
    } catch (PDOException $e) {
        echo "Lỗi khi truy vấn danh sách báo cáo: " . $e->getMessage();
        return false;
    }
}
function getListReportsSolved()
{
    global $conn;

    try {
        if (!$conn) {
            return false;
        }

        $sql = "SELECT * FROM report WHERE status = 'Solved'";

        $stmt = $conn->prepare($sql);
        $stmt->execute();

        $listReportsSolved = $stmt->fetchAll(PDO::FETCH_ASSOC);

        return $listReportsSolved;
    } catch (PDOException $e) {
        echo "Lỗi khi truy vấn danh sách báo cáo: " . $e->getMessage();
        return false;
    }
}


function changeStatus($reportID)
{
    global $conn;
    try {
        if (!$conn) {
            return json_encode(array('success' => false, 'message' => 'Database connection failed.'));
        }
        $sql = "UPDATE report SET status = 'Solved' WHERE reportID = :reportID";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':reportID', $reportID);
        $stmt->execute();
        return json_encode(array('success' => true, 'message' => 'Report status updated successfully.'));
    } catch (PDOException $e) {
        return json_encode(array('success' => false, 'message' => 'Error updating report status: ' . $e->getMessage()));
    }
}

function saveReport($sender, $typeReport, $content) {
    global $conn;
    try {
        if (!$conn) {
            return json_encode(array('success' => false, 'message' => 'Database connection failed.'));
        }

        
        
        // Prepare the SQL query to insert a new report
        $sql = "INSERT INTO report (typeReport,sender , content, status) VALUES (:sender, :typeReport, :content, 'Pending')";
        
        // Prepare the statement
        $stmt = $conn->prepare($sql);
        
        // Bind parameters
        $stmt->bindParam(':sender', $sender);
        $stmt->bindParam(':typeReport', $typeReport);
        $stmt->bindParam(':content', $content);
        
        // Execute the statement
        $stmt->execute();
        
        return json_encode(array('success' => true, 'message' => 'Report saved successfully.'));
    } catch (PDOException $e) {
        return json_encode(array('success' => false, 'message' => 'Error saving report: ' . $e->getMessage()));
    }
}


?>
