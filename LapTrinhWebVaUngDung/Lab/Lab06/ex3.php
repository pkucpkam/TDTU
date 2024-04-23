<?php
// Define the path to the comments file at the top of your script
$commentsFilePath = 'comments.txt';

// Check if form was submitted
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Extract submitted data
    $name = strip_tags($_POST['name']);
    $comment = strip_tags($_POST['comment']);
    $type = $_POST['type'];
    $timestamp = date('H:i - d/m/Y');
    
    // Prepare comment string
    $commentData = json_encode([
        'name' => $name,
        'comment' => $comment,
        'type' => $type,
        'timestamp' => $timestamp
    ]) . "\n";
    
    // Save the comment data to a file
    file_put_contents($commentsFilePath, $commentData, FILE_APPEND);
}

// Function to load and display comments
function displayComments($filePath) {
    if (file_exists($filePath)) {
        $comments = array_reverse(explode("\n", file_get_contents($filePath)));
        foreach ($comments as $commentLine) {
            if (!empty($commentLine)) {
                $commentObj = json_decode($commentLine, true);
                echo "<div class='alert {$commentObj['type']} alert-dismissible'>";
                echo "<button type='button' class='close' data-id='unique_id'>&times;</button>";
                echo "<strong>{$commentObj['name']}:</strong> {$commentObj['comment']}";
                echo "<div class='text-right small'>{$commentObj['timestamp']}</div>";
                echo "</div>";
            }
        }
    }
}
?>

<!doctype html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
        integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
    <title>PHP Exercises</title>
</head>

<body>
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-sm-8 my-3 mx-auto p-3">
                <div class="border rounded p-3">
                    <h4 class="text-center mb-3">Nhập bình luận của bạn</h4>
                    <form method="POST">
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label for="name">Họ và tên</label>
                                <input id="name" name="name" type="text" class="form-control">
                            </div>
                            <div class="form-group col-md-6">
                                <label for="type">Chọn màu nền</label>
                                <select id="type" name="type" class="custom-select">
                                    <option value="alert-secondary" selected>Gray</option>
                                    <option value="alert-success">Green</option>
                                    <option value="alert-primary">Blue</option>
                                    <option value="alert-danger">Red</option>
                                    <option value="alert-warning">Yellow</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="comment">Bình luận</label>
                            <textarea id="comment" name="comment" class="form-control" placeholder="Nhập nội dung"
                                style="height: 80px"></textarea>
                        </div>
                        <button class="btn btn-primary">Gửi bình luận</button>
                    </form>
                </div>
                <div class="mt-3" style="max-height: 300px; overflow: scroll">
                    <?php displayComments($commentsFilePath); ?>
                </div>
            </div>
        </div>
    </div>
</body>

</html>