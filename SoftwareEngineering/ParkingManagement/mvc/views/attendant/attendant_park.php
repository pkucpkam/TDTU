<?php
require_once '../../controllers/parkingController.php';

$listReserve = getListReserve();
?>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Quản lý</title>

    <!-- Custom fonts for this template -->
    <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../css/sb-admin-2.min.css" rel="stylesheet">

    <!-- Custom styles for this page -->
    <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="attendant.php">
                <div class="sidebar-brand-icon ">
                    <i class="fas fa-user-shield"></i>
                </div>
                <div class="sidebar-brand-text mx-3">Attendant</div>
            </a>

            <!-- Divider -->
            <hr class="sidebar-divider my-0">

            <!-- Nav Item - Dashboard -->
            <li class="nav-item">
                <a class="nav-link" href="attendant.php">
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
                <a class="nav-link" href="attendant_card.php">
                    <i class="fas fa-ticket-alt"></i>
                    <span>Check membership card</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="attendant_ticket.php">
                    <i class="fas fa-ticket-alt"></i>
                    <span>Print bus tickets</span>
                </a>
            </li>

            <li class="nav-item active">
                <a class="nav-link" href="attendant_park.php">
                    <i class="fas fa-list"></i>
                    <span>Parking lot management</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="attendant_finish.php">
                    <i class="fas fa-check"></i>
                    <span>Return the vehicle</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="attendant_notice.php">
                    <i class="fas fa-comments"></i>
                    <span>Notification</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="attendant_shift.php">
                    <i class="fas fa-calendar"></i>
                    <span>View Shiff</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="attendant_report.php">
                    <i class="fas fa-exclamation-circle"></i>
                    <span>Report</span>
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
                                <span class="mr-2 d-none d-lg-inline text-gray-600 small">Douglas McGee</span>
                            </a>
                            <!-- Dropdown - User Information -->
                            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                                aria-labelledby="userDropdown">
                                <a class="dropdown-item" href="#">
                                    <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Profile
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Settings
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Activity Log
                                </a>
                                <div class="dropdown-divider"></div>
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
                    <h1 class="h3 mb-2 text-gray-800">Manage sending vehicles</h1>
                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">List of vehicles being sent</h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>Area</th>
                                            <th>ID Card</th>
                                            <th>Type Vehicle</th>
                                            <th>License Plate</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <?php foreach ($listVehicle as $vehicle) { ?>
                                            <tr>
                                                <td><?php echo $vehicle['areaID']; ?></td>
                                                <td><?php echo $vehicle['IDCard']; ?></td>
                                                <td><?php echo $vehicle['typeVehicle']; ?></td>
                                                <td><?php echo $vehicle['licensePlate']; ?></td>
                                            </tr>
                                        <?php } ?>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!-- List Reverse -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Reservation list</h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>Area</th>
                                            <th>License plate</th>
                                            <th>Type Vehicle</th>
                                            <th>Time Reserve</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <?php foreach ($listReserve as $vehicle) { ?>
                                            <tr>
                                                <td><?php echo $vehicle['areaID']; ?></td>
                                                <td><?php echo $vehicle['cardID']; ?></td>
                                                <td><?php echo $vehicle['typeVehicle']; ?></td>
                                                <td><?php echo $vehicle['time']; ?></td>
                                                <td>
                                                    <button class="btn btn-danger btn-icon-split deleteBtn"
                                                        data-payment-id="<?php echo $vehicle['username']; ?>">
                                                        <span class="text">Delete</span>
                                                    </button>
                                                </td>
                                            </tr>
                                        <?php } ?>
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
                        <span>Copyright &copy; Parking/span>
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
    $('.deleteBtn').click(function (e) {
        e.preventDefault(); // Ngăn chặn việc chuyển hướng sang trang mới mặc định khi nhấn nút

        // Lấy giá trị của thuộc tính data-payment-id của nút đã được nhấn
        var username = $(this).data('payment-id');
        alert('Xác nhận xóa')

        // Gửi yêu cầu đi bằng AJAX để xóa thông tin đặt chỗ dựa trên username
        $.ajax({
            type: "POST",
            url: "../../controllers/parkingController2.php", // Đường dẫn tới file xử lý việc xóa thông tin đặt chỗ
            data: {
                username: username,
                functionName: 'deleteReserve' // Chỉ định tên của hàm cần gọi trong controller
            },
            success: function (response) {
                if (response == 'success') {
                    alert('Reservation deleted successfully!');
                    location.reload(); // Tải lại trang sau khi xóa thành công
                } else {
                    alert('Failed to delete reservation. Please try again later.');
                }
            }
        });
    });
});

    </script>

</body>

</html>