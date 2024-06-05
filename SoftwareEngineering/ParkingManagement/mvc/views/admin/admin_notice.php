<?php
require_once '../../controllers/noticeController.php';
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


</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="admin.html">
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
                <a class="nav-link " href="admin_service.php">
                    <i class="fas fa-list"></i>
                    <span>Service Management</span>
                </a>
            </li>

            <li class="nav-item ">
                <a class="nav-link" href="admin_users.php">
                    <i class="fas fa-users"></i>
                    <span>Customer Management</span>
                </a>
            </li>

            <li class="nav-item ">
                <a class="nav-link" href="admin_equipment.php">
                    <i class="fas fa-wrench"></i>
                    <span>Equipment Management</span>
                </a>
            </li>

            <li class="nav-item active">
                <a class="nav-link" href="admin_notice.php">
                    <i class="fas fa-comments"></i>
                    <span>Notification Management</span>
                </a>
            </li>

            <li class="nav-item">
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
                    <h1 class="h3 mb-2 text-gray-800">Notification</h1>
                    <!-- DataTales Example -->

                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Notification list</h6>
                            <a href="#" class="btn btn-success btn-icon-split mt-3" data-toggle="modal"
                                data-target="#addNotificationModal">
                                <span class="text">Add Notification</span>
                            </a>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>Notice ID</th>
                                            <th>Type Notice</th>
                                            <th>Receiver</th>
                                            <th>Content</th>
                                            <th>Function</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <?php foreach ($listNotice as $notice) { ?>
                                            <tr>
                                                <td><?php echo $notice['noticeID']; ?></td>
                                                <td><?php echo $notice['typeNotice']; ?></td>
                                                <td><?php echo $notice['target']; ?></td>
                                                <td><?php echo $notice['content']; ?></td>
                                                <td>
                                                    <button class="btn btn-danger btn-icon-split deleteNoticeBtn"
                                                        data-toggle="modal" data-target="#deleteNoticeModal"
                                                        data-notice-id="<?php echo $notice['noticeID']; ?>">
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

    <!-- Add Notification Modal -->
    <div class="modal fade" id="addNotificationModal" tabindex="-1" role="dialog"
        aria-labelledby="addNotificationModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Add Notification</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <!-- Dropdown menu để chọn loại thông báo -->
                    <div class="form-group">
                        <label for="typeNotice">Notification type:</label>
                        <select class="form-control" id="typeNotice">
                            <option value="general">General</option>
                            <option value="personal">Separate</option>
                        </select>
                    </div>
                    <div id="notificationContent">
                        <div id="generalNotification" style="display:none;">
                            <div class="form-group">
                                <label for="target">Group of People:</label>
                                <select class="form-control" id="target">
                                    <option>-- Select Group of People --</option>
                                    <option>Customer</option>
                                    <option>Attendant</option>
                                    <option>Accountant</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="message">Message</label>
                                <textarea class="form-control" id="message" rows="3"
                                    placeholder="Enter message"></textarea>
                            </div>
                        </div>
                        <div id="personalNotification" style="display:none;">
                            <div class="form-group">
                                <label for="target1">Username:</label>
                                <input type="text" class="form-control" id="target1" placeholder="Enter username">
                            </div>
                            <div class="form-group">
                                <label for="message1">Message:</label>
                                <textarea class="form-control" id="message1" rows="3"
                                    placeholder="Enter message"></textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" id="submitNotification">Submit</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Xóa Thông Báo -->
    <div class="modal fade" id="deleteNoticeModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Delete Notification</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="deleteNoticeForm">
                        <p>Are you sure you want to delete this notification?</p>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-danger" id="confirmDeleteNotice">Delete</button>
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
            // Ẩn thông báo riêng và hiển thị thông báo chung khi mở modal popup
            $("#personalNotification").hide();
            $("#generalNotification").show();

            // Xử lý khi người dùng chuyển đổi giữa thông báo chung và thông báo riêng
            $("#typeNotice").change(function () {
                var type = $(this).val();
                if (type === "personal") {
                    $("#generalNotification").hide();
                    $("#personalNotification").show();
                } else {
                    $("#personalNotification").hide();
                    $("#generalNotification").show();
                }
            });

        });

        $(document).ready(function () {
            // Function to handle adding a new notice
            $(document).ready(function () {
                // Function to handle adding a new notice
                $("#submitNotification").click(function () {
                    var typeNotice = $("#typeNotice").val();
                    var target;
                    var message;
                    if (typeNotice === "general") {
                        target = $("#target").val();
                        message = $("#message").val(); // Giữ nguyên tên biến là 'message'
                    } else {
                        target = $("#target1").val();
                        message = $("#message1").val(); // Giữ nguyên tên biến là 'message'
                    }

                    $.ajax({
                        url: "../../controllers/noticeController1.php",
                        type: "POST",
                        data: {
                            typeNotice: typeNotice,
                            target: target,
                            message: message // Giữ nguyên tên biến là 'message'
                        },
                        success: function (response) {
                            // Refresh the page after successful addition
                            alert("Notification sent successfully");
                            location.reload();
                            console.log(response);
                        },
                        error: function (xhr, status, error) {
                            // Handle errors here
                            console.error(error);
                        }
                    });
                });
            });



            // Function to handle deleting a notice
            $(".deleteNoticeBtn").click(function () {
                var noticeID = $(this).data("notice-id");

                $("#confirmDeleteNotice").click(function () {
                    $.ajax({
                        url: "../../controllers/noticeController1.php",
                        type: "POST",
                        data: {
                            deleteNotice: noticeID
                        },
                        success: function (response) {
                            // Refresh the page after successful deletion
                            location.reload();
                        },
                        error: function (xhr, status, error) {
                            // Handle errors here
                            console.error(error);
                        }
                    });
                });
            });
        });
    </script>



</body>

</html>