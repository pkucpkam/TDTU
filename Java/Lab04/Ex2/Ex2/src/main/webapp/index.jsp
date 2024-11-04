<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Image and File Download Interface</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container">
    <h2 class="text-center mt-5">Image and File Download Interface</h2>

    <!-- Phần xem và tải ảnh -->
    <div class="row justify-content-center mt-4">
        <div class="col-md-6 col-lg-5">
            <h4>Image Operations:</h4>
            <div class="mt-3">
                <!-- Nút xem ảnh từ ImageServlet1 -->
                <a href="image1" target="_blank" class="btn btn-info btn-block">View Image 1 (Display in Browser)</a>

                <!-- Nút tải ảnh từ ImageServlet2 -->
                <a href="image2" class="btn btn-success btn-block mt-2">Download Image 2 (Force Download)</a>
            </div>
        </div>
    </div>

    <!-- Phần tải xuống file -->
    <div class="row justify-content-center mt-4">
        <div class="col-md-6 col-lg-5">
            <h4>File Download:</h4>
            <form id="downloadForm" action="download" method="get" class="border rounded p-4 bg-light mt-3">
                <div class="form-group">
                    <label for="file">File Name:</label>
                    <input type="text" id="file" name="file" class="form-control" placeholder="Enter file name (e.g., data.zip)" required>
                </div>
                <div class="form-group">
                    <label for="speed">Speed Limit (KB/s):</label>
                    <input type="number" id="speed" name="speed" class="form-control" placeholder="Optional">
                </div>
                <button type="submit" class="btn btn-primary btn-block">Download File</button>
            </form>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
