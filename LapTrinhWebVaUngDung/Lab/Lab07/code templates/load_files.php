<?php 
$dir = "../uploads/";
$files = scandir($dir);
$fileList = array();
foreach ($files as $file) {
    if ($file != "." && $file != "..") {
        $filepath = $dir . $file;
        $fileInfo = array(
        "name" => $file,
        "type" => is_dir($filepath) ? "Folder" : "File",
        "size" => is_dir($filepath) ? "-" : filesize($filepath),
        "last_modified" => date("d-m-Y H:i:s", filemtime($filepath))
        );
        array_push($fileList, $fileInfo);
        }
    }
header('Content-Type: application/json');
echo json_encode($fileList);
?>