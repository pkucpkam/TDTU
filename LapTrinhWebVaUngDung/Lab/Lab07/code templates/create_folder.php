<?php 

session_start();

if (!isset($_SESSION['username'])) {
   header("Location: login.php");
   exit;
}

$username = $_SESSION['username'];


if ($_SERVER["REQUEST_METHOD"] == "POST") {
      $data = json_decode(file_get_contents("php://input"), true);
      
      $foldername = $data['foldername'];
      $dir = "../uploads/";
      mkdir($dir . $foldername);
   }
?>