<?php
$is_post_request = $_SERVER["REQUEST_METHOD"] == "POST";
$name = $email = $gender = $os = "";
$browsers = [];

if ($is_post_request) {
    $name = htmlspecialchars($_POST['name']);
    $email = htmlspecialchars($_POST['email']);
    $gender = htmlspecialchars($_POST['gender']);
    $browsers = $_POST['browsers'] ?? [];
    $os = htmlspecialchars($_POST['os']);
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
    <title><?php echo $is_post_request ? 'Confirm Information' : 'User Information'; ?></title>
    <style>
    body {
        background-color: #f7f7f7;
        font-family: 'Arial', sans-serif;
    }

    .confirmation-box {
        background-color: #fff;
        border-radius: 8px;
        padding: 30px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        margin-top: 50px;
    }

    .confirmation-box h2 {
        font-size: 24px;
        color: #333;
        margin-bottom: 20px;
    }

    .confirmation-box p {
        font-size: 18px;
        color: #666;
        line-height: 1.6;
    }

    .confirmation-box ul {
        padding-left: 20px;
    }

    .confirmation-box ul li {
        list-style-type: disc;
        margin-bottom: 10px;
        font-size: 18px;
        color: #666;
    }

    .btn-custom {
        color: #fff;
        background-color: #28a745;
        border-color: #28a745;
        padding: 10px 24px;
        border-radius: 20px;
        font-size: 18px;
    }

    .btn-custom-outline {
        background-color: transparent;
        color: #28a745;
        border-color: #28a745;
        padding: 10px 24px;
        border-radius: 20px;
        font-size: 18px;
    }

    .btn-custom-outline:hover {
        background-color: #28a745;
        color: #fff;
    }

    .btn-container {
        margin-top: 30px;
        display: flex;
        justify-content: space-between;
    }
    </style>
</head>

<body>
    <div class="container">
        <?php if ($is_post_request): ?>
        <!-- Confirmation section -->
        <div class="confirmation-box">
            <h2>Confirm Information</h2>
            <p><strong>Your name:</strong> <?php echo $name; ?></p>
            <p><strong>Your email:</strong> <?php echo $email; ?></p>
            <p><strong>Gender:</strong> <?php echo $gender; ?></p>
            <p><strong>Favorite web browser:</strong></p>
            <ul>
                <?php foreach ($browsers as $browser): ?>
                <li><?php echo htmlspecialchars($browser); ?></li>
                <?php endforeach; ?>
            </ul>
            <p><strong>Preferred Operating System:</strong> <?php echo $os; ?></p>
            <div class="btn-container">
                <button class="btn btn-custom" onclick="alert('Data saved!');">Save</button>
                <button class="btn btn-custom-outline" onclick="history.back();">Back</button>
            </div>
        </div>
    </div>
    <?php else: ?>
    <!-- User Information Form -->
    <div class="row">
        <div class="col-md-8 col-lg-5 my-5 mx-auto">
            <h5 class="text-center mb-3">User Information</h5>
            <form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?>" method="POST">
                <div class="form-group">
                    <label for="name">Your name</label>
                    <input type="text" id="name" name="name" class="form-control" placeholder="Your name" required>
                </div>
                <div class="form-group">
                    <label for="email">Your email</label>
                    <input type="email" id="email" name="email" class="form-control" placeholder="Your email" required>
                </div>
                <div class="form-group">
                    <legend class="col-form-label">Gender</legend>
                    <div class="custom-control custom-radio">
                        <input type="radio" id="male" name="gender" class="custom-control-input" value="Male" required>
                        <label class="custom-control-label" for="male">Male</label>
                    </div>
                    <div class="custom-control custom-radio">
                        <input type="radio" id="female" name="gender" class="custom-control-input" value="Female"
                            required>
                        <label class="custom-control-label" for="female">Female</label>
                    </div>
                </div>
                <div class="form-group">
                    <legend class="col-form-label">Favorite web browsers</legend>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="chrome" name="browsers[]"
                            value="Google Chrome">
                        <label class="custom-control-label" for="chrome">Google Chrome</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="firefox" name="browsers[]"
                            value="Firefox">
                        <label class="custom-control-label" for="firefox">Firefox</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="safari" name="browsers[]"
                            value="Safari">
                        <label class="custom-control-label" for="safari">Safari</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="edge" name="browsers[]" value="Edge">
                        <label class="custom-control-label" for="edge">Edge</label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="os">Preferred Operating System</label>
                    <select id="os" name="os" class="custom-select">
                        <option value="Windows 10">Windows 10</option>
                        <option value="macOS">macOS</option>
                        <option value="Linux">Linux</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary px-5 mr-2">Send</button>
            </form>
        </div>
    </div>
    <?php endif; ?>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>