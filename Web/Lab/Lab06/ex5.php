<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Exercise 5</title>

    <style>
        *{
            box-sizing: border-box;
        }
        #panel{
            width:200px;
            height:200px;
            margin:auto;
            margin-top: 250px;

        }
        .cell{
            width :20px;
            height: 20px;
            background-color:#0b97c6;
            border : 1px solid black;
            float : left;
        }
    </style>
</head>
<body>
    <div id="panel">
        <?php
            for($i = 1; $i <= 100; $i++) {
                $r = random_int(0, 255);
                $g = random_int(0, 255);
                $b = random_int(0, 255);
                echo "<div class=\"cell\" style=\"background-color: rgb($r, $g, $b)\"></div>";
            }
        ?>
    </div>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        var cells = document.querySelectorAll(".cell");

        cells.forEach(function(cell) {
            cell.addEventListener("mouseover", function() {
                var color = this.style.backgroundColor;
                document.body.style.backgroundColor = color;
            });

            cell.addEventListener("mouseout", function() {
                document.body.style.backgroundColor = "white";
            });
        });
    });
</script>

</body>
</html>
