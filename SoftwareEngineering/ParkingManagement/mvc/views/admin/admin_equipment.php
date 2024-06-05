<?php
require_once '../../controllers/equipmentController.php';
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

            <li class="nav-item active">
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

            <li class="nav-item">
                <a class="nav-link" href="admin_report.php">
                    <i class="fas fa-exclamation-triangle"></i>
                    <span>Reporting Management</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="admin6.php">
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
                    <h1 class="h3 mb-2 text-gray-800">Equipment Management</h1>
                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Equipment list</h6>
                            <a href="#" class="btn btn-success btn-icon-split float-left" data-toggle="modal"
                                data-target="#addEquipmentModal">
                                <span class="text">Add Equipment</span>
                            </a>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>EqipmentID</th>
                                            <th>EquipmentName</th>
                                            <th>Status</th>
                                            <th>Function</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php foreach ($listEquipment as $equipment): ?>
                                            <tr>
                                                <td><?php echo $equipment['equipmentID']; ?></td>
                                                <td><?php echo $equipment['equipmentName']; ?></td>
                                                <td><?php echo $equipment['status']; ?></td>
                                                <td>
                                                    <button class="btn btn-danger btn-icon-split deleteEquipmentBtn"
                                                        data-toggle="modal" data-target="#deleteEquipmentModal"
                                                        data-equipment-id="<?php echo $equipment['equipmentID']; ?>">
                                                        <span class="text">Delete</span>
                                                    </button>
                                                    <button class="btn btn-primary btn-icon-split editEquipmenteBtn"
                                                        data-toggle="modal" data-target="#editEquipmentModal"
                                                        data-equipment-id="<?php echo $equipment['equipmentID']; ?>">
                                                        <span class="text">Edit</span>
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

    <!-- Modal Thêm Trang Thiết Bị -->
    <div class="modal fade" id="addEquipmentModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Add Equipment</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="addEquipmentForm" action="admin_equipment.php" method="POST">
                        <div class="form-group">
                            <label for="equipmentName">Equipment name:</label>
                            <input type="text" class="form-control" id="equipmentName" name="equipmentName"
                                placeholder="Enter the device name">
                        </div>
                        <div class="form-group">
                            <label for="status">Status:</label>
                            <input type="text" class="form-control" id="status" name="status">
                        </div>

                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary" name="saveEquipment" id="saveEquipment">Save</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Xóa Trang Thiết Bị -->
    <div class="modal fade" id="deleteEquipmentModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Delete Equipment</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="deleteEquipmentForm">
                        <div class="form-group">
                            <label for="equipmentId">EquipmentID:</label>
                            <input type="text" class="form-control" id="equipmentId"
                                placeholder="Nhập mã trang thiết bị" readonly>
                        </div>
                        <p>Are you sure you want to delete the device <strong id="equipmentName"></strong>?</p>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-danger" id="confirmDelete">Delete</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Sửa Trang Thiết Bị -->
    <div class="modal fade" id="editEquipmentModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Edit Equipment Information</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="editEquipmentForm" action="admin_equipment.php" method="POST">
                        <div class="form-group">
                            <label for="editEquipmentName">Equipment name:</label>
                            <input type="text" class="form-control" id="editEquipmentName" name="equipmentName">
                        </div>
                        <div class="form-group">
                            <label for="editStatus">Status:</label>
                            <input type="text" class="form-control" id="editStatus" name="status">
                        </div>

                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary" name="updateEquipment" id="updateEquipment">Save</button>
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
            // Xử lý khi nhấn nút "Lưu" trong modal thêm trang thiết bị
            $('#saveEquipment').click(function (e) {
                e.preventDefault(); // Ngăn chặn hành động mặc định của nút Submit

                // Lấy dữ liệu từ form
                var formData = {
                    equipmentName: $('#equipmentName').val(),
                    status: $('#status').val(),
                    saveEquipment: true // Thêm biến này để xác định yêu cầu là thêm trang thiết bị
                };

                // Gửi dữ liệu lên server bằng Ajax
                $.ajax({
                    type: 'POST',
                    url: '../../controllers/equipmentController1.php',
                    data: formData,
                    success: function (response) {
                        // Xử lý kết quả trả về từ server (nếu cần)
                        console.log(response);
                        // Đóng modal sau khi thêm thành công
                        $('#addEquipmentModal').modal('hide');
                        // Tải lại trang để cập nhật danh sách trang thiết bị
                        alert("Equipment added successfully");
                        location.reload();
                    },
                    error: function (xhr, status, error) {
                        // Xử lý lỗi (nếu có)
                        console.error(xhr.responseText);
                    }
                });
            });

            // Khi modal xóa trang thiết bị được mở
            $('#deleteEquipmentModal').on('show.bs.modal', function (event) {
                // Lấy button đã kích hoạt mở modal
                var button = $(event.relatedTarget);
                // Lấy dữ liệu từ thuộc tính data-equipment-id của button
                var equipmentId = button.data('equipment-id');
                // Hiển thị ID trang thiết bị trong modal
                $(this).find('.modal-body #equipmentId').val(equipmentId);
                // Đặt lại nội dung thông báo xóa trang thiết bị
                var equipmentName = button.closest('tr').find('td:eq(1)').text();
                $(this).find('.modal-body #equipmentName').text(equipmentName);
            });

            // Khi nút "Sửa" trong bảng được nhấn
            $('.editEquipmenteBtn').on('click', function () {
                // Lấy ID của trang thiết bị từ thuộc tính data-equipment-id của nút
                var equipmentId = $(this).data('equipment-id');

                // Lấy thông tin của trang thiết bị từ hàng bảng tương ứng
                var equipmentName = $(this).closest('tr').find('td:eq(1)').text();
                var status = $(this).closest('tr').find('td:eq(2)').text();

                // Điền thông tin của trang thiết bị vào form sửa
                $('#editEquipmentName').val(equipmentName);
                $('#editStatus').val(status);

                // Đặt giá trị của ID trang thiết bị vào một trường ẩn trong form
                $('#equipmentId').val(equipmentId);
            });

            // Xác nhận xóa trang thiết bị
            $('#confirmDelete').click(function () {
                var equipmentId = $('#equipmentId').val();
                // Gửi yêu cầu xóa trang thiết bị bằng Ajax
                $.ajax({
                    type: 'POST',
                    url: '../../controllers/equipmentController1.php',
                    data: { deleteEquipment: equipmentId }, // Dữ liệu gửi đi (có thể cần chỉnh sửa)
                    success: function (response) {
                        // Xử lý kết quả trả về từ server (nếu cần)
                        console.log(response);
                        // Đóng modal sau khi xóa thành công
                        $('#deleteEquipmentModal').modal('hide');
                        // Tải lại trang để cập nhật danh sách trang thiết bị
                        alert("Device deletion successful");
                         location.reload();
                    },
                    error: function (xhr, status, error) {
                        // Xử lý lỗi (nếu có)
                        console.error(xhr.responseText);
                    }
                });
            });

            // Gửi dữ liệu khi sửa trang thiết bị
            $('#updateEquipment').click(function () {
                var formData = {
                    updateEquipment: true, // Thêm trường này để xác định yêu cầu là cập nhật trang thiết bị
                    equipmentId: $('#equipmentId').val(),
                    equipmentName: $('#editEquipmentName').val(),
                    status: $('#editStatus').val()
                };

                // Gửi dữ liệu cập nhật trang thiết bị bằng Ajax
                $.ajax({
                    type: 'POST',
                    url: '../../controllers/equipmentController1.php',
                    data: formData,
                    success: function (response) {
                        // Xử lý kết quả trả về từ server (nếu cần)
                        console.log(response);
                        // Đóng modal sau khi cập nhật thành công
                        $('#editEquipmentModal').modal('hide');
                        // Tải lại trang để cập nhật danh sách trang thiết bị
                        alert("Updated equipment information successfully");
                        location.reload();
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