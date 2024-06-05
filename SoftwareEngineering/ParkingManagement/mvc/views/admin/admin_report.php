<?php
require_once '../../controllers/reportController.php';
session_start();

?>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Management</title>

    <!-- Custom fonts for this template -->
    <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../css/sb-admin-2.min.css" rel="stylesheet">

    <!-- Custom styles for this page -->
    <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

    <style>
        .hide {
            display: none;
        }
    </style>

</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="admin.php">
                <div class="sidebar-brand-icon ">
                    <i class="fas fa-car"></i>
                </div>
                <div class="sidebar-brand-text mx-3">Manager</div>
            </a>

            <!-- Divider -->
            <hr class="sidebar-divider my-0">

            <!-- Nav Item - Dashboard -->
            <li class="nav-item">
                <a class="nav-link" href="admin.php">
                    <i class="fas fa-fw fa-tachometer-alt"></i>
                    <span>General Information</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider">

            <!-- Heading -->
            <div class="sidebar-heading">
                Function
            </div>

            <!-- Nav Item - Employee Management -->
            <li class="nav-item">
                <a class="nav-link employee-manager" href="admin_staff.php">
                    <i class="fas fa-users"></i>
                    <span>Employee Management</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="admin_service.php">
                    <i class="fas fa-list"></i>
                    <span>Service Management</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="admin_users.php">
                    <i class="fas fa-users"></i>
                    <span>Customer Management</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="admin_equipment.php">
                    <i class="fas fa-wrench"></i>
                    <span>Equipment Management</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="admin_notice.php">
                    <i class="fas fa-comments"></i>
                    <span>Notification Management</span>
                </a>
            </li>

            <li class="nav-item active">
                <a class="nav-link" href="admin_report.php">
                    <i class="fas fa-exclamation-triangle"></i>
                    <span>Reporting Management</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="admin_shift.php">
                    <i class="fas fa-calendar"></i>
                    <span>Devide Shiff</span>
                </a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider">

            <!-- Sidebar Toggler (Sidebar) -->
            <div class="text-center d-none d-md-inline">
                <button class="rounded-circle border-0" id="sidebarToggle"></button>
            </div>

        </ul>
        <!-- End of Sidebar -->
        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <!-- Topbar -->
                <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                    <!-- Sidebar Toggle (Topbar) -->
                    <form class="form-inline">
                        <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                            <i class="fa fa-bars"></i>
                        </button>
                    </form>

                    <!-- Topbar Navbar -->
                    <ul class="navbar-nav ml-auto">
                        <div class="topbar-divider d-none d-sm-block"></div>

                        <!-- Nav Item - User Information -->
                        <li class="nav-item dropdown no-arrow">
                            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="mr-2 d-none d-lg-inline text-gray-600 small"><?php echo $_SESSION['username']; ?></span>
                            </a>
                            <!-- Dropdown - User Information -->
                            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                                aria-labelledby="userDropdown">
                                <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Logout
                                </a>
                            </div>
                        </li>

                    </ul>

                </nav>
                <!-- End of Topbar -->

                <!-- Begin Page Content -->
                <div class="container-fluid">
                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">Reporting Management</h1>
                    <center class="mb-3">
                        <a href="#" class="ml-5 btn btn-danger btn-icon-split" id="IssueButton">
                            <span class="text">Problem needs to be resolved</span>
                        </a>

                        <a href="#" class="ml-5 btn btn-success btn-icon-split" id="resolvedIssueButton">
                            <span class="text">Problem resolved</span>
                        </a>

                    </center>
                    <!-- DataTales Example -->
                    <div class="card shadow mb-4 hide" id="dataTableContainer">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">List of resolved issues</h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>Report ID</th>
                                            <th>Type Report</th>
                                            <th>Sender</th>
                                            <th>Content</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php foreach ($listReportSolved as $report): ?>
                                            <tr>
                                                <td><?php echo $report['reportID']; ?></td>
                                                <td><?php echo $report['typeReport']; ?></td>
                                                <td><?php echo $report['sender']; ?></td>
                                                <td><?php echo $report['content']; ?></td>
                                                <td><?php echo $report['status']; ?></td>
                                            </tr>
                                        <?php endforeach; ?>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4 hide" id="dataTableContainer1">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">List of problems that need to be resolved</h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable1" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>Report ID</th>
                                            <th>Type Report</th>
                                            <th>Sender</th>
                                            <th>Content</th>
                                            <th>Status</th>
                                            <th>Function</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php foreach ($listReport as $report): ?>
                                            <tr>
                                                <td><?php echo $report['reportID']; ?></td>
                                                <td><?php echo $report['typeReport']; ?></td>
                                                <td><?php echo $report['sender']; ?></td>
                                                <td><?php echo $report['content']; ?></td>
                                                <td><?php echo $report['status']; ?></td>
                                                <td>
                                                    <button class="btn btn-success btn-icon-split solveReportBtn"
                                                        data-report-id="<?php echo $report['reportID']; ?>">
                                                        <span class="text">Solved</span>
                                                    </button>
                                                </td>
                                            </tr>
                                        <?php endforeach; ?>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>


                </div>
                <!-- /.container-fluid -->




            </div>
            <!-- End of Main Content -->

            <!-- Footer -->
            <footer class="sticky-footer bg-white">
                <div class="container my-auto">
                    <div class="copyright text-center my-auto">
                        <span>Copyright &copy; Parking</span>
                    </div>
                </div>
            </footer>
            <!-- End of Footer -->

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <!-- Logout Modal-->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-primary" href="../login.php">Logout</a>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap core JavaScript-->
    <script src="../vendor/jquery/jquery.min.js"></script>
    <script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="../vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="../js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="../js/demo/datatables-demo.js"></script>

    <script>
        $(document).ready(function () {
            $("#resolvedIssueButton").click(function () {
                $("#dataTableContainer1").addClass("hide");
                $("#dataTableContainer").removeClass("hide");
            });

        });

        $(document).ready(function () {
            $("#IssueButton").click(function () {
                $("#dataTableContainer").addClass("hide");
                $("#dataTableContainer1").removeClass("hide");
            });
        });

        $(document).ready(function () {
    // Event listener for the "Solved" button
    $(".solveReportBtn").click(function () {
        // Retrieve the report ID from the data attribute
        var reportID = $(this).data('report-id');

        // Send an AJAX request to update the status
        $.ajax({
            url: '../../controllers/reportController1.php',
            type: 'POST',
            data: { action: 'solveReport', reportID: reportID },
            dataType: 'json', // Chỉ định kiểu dữ liệu trả về là JSON
            success: function (response) {
                // Kiểm tra xem cập nhật trạng thái đã thành công hay không
                if (response.success) {
                    alert("Success"); // Hiển thị thông báo thành công
                    location.reload();
                } else {
                    alert(response.message); // Hiển thị thông báo lỗi
                }
            },
            error: function (xhr, status, error) {
                // Xử lý lỗi (nếu có)
                console.error(xhr.responseText);
            }
        });
    });
});


    </script>

</body>

</html>