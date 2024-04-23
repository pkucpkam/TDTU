<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<div class="container mt-5 text-center">         
  <table class="table table-bordered">
    <thead>
      <tr>
        <th class="bg-secondary" colspan="10">BANG CUU CHUONG</th>
      </tr>
    </thead>
    <tbody>
        <?php
            for ($i = 1; $i <= 10; $i++) { ?>
                <tr>    
                    <?php for ($j = 1; $j <= 10; $j++) { ?>
                        <td>
                            <?php echo "$j * $i = "  . ($i *$j) ?>
                        </td>
                    <?php } ?>
                </tr>
        <?php } ?>
    </tbody>
  </table>
</div>

</body>
</html>
