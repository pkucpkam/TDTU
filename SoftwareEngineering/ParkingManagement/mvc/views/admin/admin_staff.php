<?php
require_once '../../controllers/adminController.php';
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
            <li class="nav-item active">
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

            <li class="nav-item">
                <a class="nav-link" href="admin_report.php">
                    <i class="fas fa-exclamation-triangle"></i>
                    <span>Reporting Management</span>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="admin_shift.php">
                    <i class="fas fa-calendar"></i>
                    <span>Devide Shift</span>
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
                    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                        <i class="fa fa-bars"></i>
                    </button>

                    <!-- Topbar Navbar -->
                    <ul class="navbar-nav ml-auto">

                        <!-- Nav Item - Search Dropdown (Visible Only XS) -->
                        <li class="nav-item dropdown no-arrow d-sm-none">
                            <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="fas fa-search fa-fw"></i>
                            </a>
                            <!-- Dropdown - Messages -->
                            <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
                                aria-labelledby="searchDropdown">
                                <form class="form-inline mr-auto w-100 navbar-search">
                                    <div class="input-group">
                                        <input type="text" class="form-control bg-light border-0 small"
                                            placeholder="Search for..." aria-label="Search"
                                            aria-describedby="basic-addon2">
                                        <div class="input-group-append">
                                            <button class="btn btn-primary" type="button">
                                                <i class="fas fa-search fa-sm"></i>
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </li>

                        <!-- Nav Item - User Information -->
                        <li class="nav-item dropdown no-arrow">
                            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="mr-2 d-none d-lg-inline text-gray-600 small">
                                    <?php echo $_SESSION['username']; ?>
                                </span>
                            </a>
                            <!-- Dropdown - User Information -->
                            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                                aria-labelledby="userDropdown">
                                <a class="dropdown-item" href="#">
                                    <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Change password
                                </a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Log Out
                                </a>
                            </div>
                        </li>
                    </ul>

                </nav>
                <!-- End of Topbar -->

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">Employee Management</h1>
                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-2 font-weight-bold text-primary">Manage Employee</h6>
                            <center>
                                <a href="#" class="btn btn-success btn-icon-split float-left" data-toggle="modal"
                                    data-target="#addEmployeeModal">
                                    <span class="text">Add Employee</span>
                                </a>
                            </center>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>Username</th>
                                            <th>Full Name</th>
                                            <th>Salary</th>
                                            <th>Phone Number</th>
                                            <th>Address</th>
                                            <th>Email</th>
                                            <th>Function</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php foreach ($listStaff as $staff): ?>
                                            <tr>
                                                <td><?php echo $staff['username']; ?></td>
                                                <td><?php echo $staff['full_name']; ?></td>
                                                <td><?php echo $staff['salary']; ?></td>
                                                <td><?php echo $staff['phone_number']; ?></td>
                                                <td><?php echo $staff['address']; ?></td>
                                                <td><?php echo $staff['email']; ?></td>
                                                <td>
                                                    <button class="btn btn-danger btn-icon-split deleteEmployeeBtn"
                                                        data-toggle="modal" data-target="#deleteEmployeeModal"
                                                        data-employee-id="<?php echo $staff['staffID']; ?>">
                                                        <span class="text">Delete</span>
                                                    </button>
                                                    <button class="btn btn-primary btn-icon-split editEmployeeBtn"
                                                        data-toggle="modal" data-target="#editEmployeeModal"
                                                        data-employee-id="<?php echo $staff['staffID']; ?>">
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

    <!-- Modal Them Nhan Vien-->
    <div class="modal fade" id="addEmployeeModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Add Employee</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="addEmployeeForm" action="admin_staff.php" method="POST">
                        <div class="form-group">
                            <label for="username">Username:</label>
                            <input type="username" class="form-control" id="username" name="username"
                                placeholder="Enter username" required>
                        </div>
                        <div class="form-group">
                            <label for="fullname">Full Name:</label>
                            <input type="fullname" class="form-control" id="fullname" placeholder="Enter full name"
                                required>
                        </div>
                        <div class="form-group">
                            <label for="phoneNumber">Phone Number</label>:</label>
                            <input type="text" class="form-control" id="phoneNumber" placeholder="Enter phone number"
                                required>
                        </div>
                        <div class="form-group">
                            <label for="address">Address:</label>
                            <input type="text" class="form-control" id="address" placeholder="Enter address" required>
                        </div>
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" class="form-control" id="email" placeholder="Enter email" required>
                        </div>
                        <div class="form-group">
                            <label for="role">Role:</label>
                            <select class="form-control" id="role" name="role">
                                <option value="">Choose role</option>
                                <option value="accountant">Accountant</option>
                                <option value="attendant">Attendant</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="salary">Salary:</label>
                            <input type="text" class="form-control" id="salary" name="salary"
                                placeholder="Enter salary">
                        </div>

                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary" name="saveEmployee" id="saveEmployee">Save</button>
                </div>
            </div>
        </div>
    </div>


    <!-- Modal Xóa Nhân Viên -->
    <div class="modal fade" id="deleteEmployeeModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Delete Employee</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="deleteEmployeeForm">
                        <div class="form-group">
                            <label for="employeeId">Employee ID:</label>
                            <input type="text" class="form-control" id="employeeId" placeholder="Nhập mã nhân viên"
                                readonly>
                        </div>
                        <p>Are you sure you want to delete the employee <strong id="employeeName"></strong>?</p>
                    </form>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-danger" id="confirmDelete">Delete</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Sửa Nhân Viên -->
    <div class="modal fade" id="editEmployeeModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Edit Employee</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="editEmployeeForm" action="admin_staff.php" method="POST">
                        <div class="form-group">
                            <label for="editFullname">Fullname:</label>
                            <input type="text" class="form-control" id="editFullname" name="fullname" required>
                        </div>
                        <div class="form-group">
                            <label for="editPhoneNumber">Phone Number:</label>
                            <input type="text" class="form-control" id="editPhoneNumber" name="phoneNumber" required>
                        </div>
                        <div class="form-group">
                            <label for="editAddress">Address:</label>
                            <input type="text" class="form-control" id="editAddress" name="address" required>
                        </div>
                        <div class="form-group">
                            <label for="editEmail">Email:</label>
                            <input type="email" class="form-control" id="editEmail" name="email" required>
                        </div>
                        <div class="form-group">
                            <label for="editSalary">Salary:</label>
                            <input type="text" class="form-control" id="editSalary" name="salary" required>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary" name="updateEmployee"
                        id="updateEmployee">Save</button>
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

    <!-- admin.js -->
    <script src="../js/admin.js"></script>

    <script>
        $(document).ready(function () {
            $('#saveEmployee').click(function (e) {
                e.preventDefault(); // Ngăn chặn hành động mặc định của nút Submit

                // Lấy dữ liệu từ form
                var formData = {
                    username: $('#username').val(),
                    fullname: $('#fullname').val(),
                    phoneNumber: $('#phoneNumber').val(),
                    address: $('#address').val(),
                    email: $('#email').val(),
                    role: $('#role').val(),
                    salary: $('#salary').val(),
                    saveEmployee: true // Thêm biến này để xác định yêu cầu là thêm nhân viên
                };

                // Gửi dữ liệu lên server bằng Ajax
                $.ajax({
                    type: 'POST',
                    url: '../../controllers/adminController1.php',
                    data: formData,
                    success: function (response) {
                        // Xử lý kết quả trả về từ server (nếu cần)
                        if (response == 'Username already exists.') {
                            alert('Failed to add');
                        }       
                        else {
                            alert('Add success')
                        }
                        // Đóng modal sau khi thêm thành công
                        $('#addEmployeeModal').modal('hide');
                        // Tải lại trang để cập nhật danh sách nhân viên

                        location.reload();
                    },
                    error: function (xhr, status, error) {
                        // Xử lý lỗi (nếu có)
                    }
                });
            });
        });


        $(document).ready(function () {
            // Khi modal xóa nhân viên được mở
            $('#deleteEmployeeModal').on('show.bs.modal', function (event) {
                // Lấy button đã kích hoạt mở modal
                var button = $(event.relatedTarget);
                // Lấy dữ liệu từ thuộc tính data-employee-id của button
                var employeeId = button.data('employee-id');
                // Hiển thị tên nhân viên trong modal
                $(this).find('.modal-body #employeeId').val(employeeId);
                // Đặt lại nội dung thông báo xóa nhân viên
                var employeeName = button.closest('tr').find('td:eq(1)').text();
                $(this).find('.modal-body #employeeName').text(employeeName);
            });
        });


        $(document).ready(function () {
            // Khi nút "Sửa" trong bảng được nhấn
            $('.editEmployeeBtn').on('click', function () {
                // Lấy ID của nhân viên từ thuộc tính data-employee-id của nút
                var employeeId = $(this).data('employee-id');

                // Lấy thông tin của nhân viên từ hàng bảng tương ứng
                var fullname = $(this).closest('tr').find('td:eq(1)').text();
                var phoneNumber = $(this).closest('tr').find('td:eq(3)').text();
                var address = $(this).closest('tr').find('td:eq(4)').text();
                var email = $(this).closest('tr').find('td:eq(5)').text();
                var salary = $(this).closest('tr').find('td:eq(2)').text();

                // Điền thông tin của nhân viên vào form sửa
                $('#editFullname').val(fullname);
                $('#editPhoneNumber').val(phoneNumber);
                $('#editAddress').val(address);
                $('#editEmail').val(email);
                $('#editSalary').val(salary);

                // Đặt giá trị của ID nhân viên vào một trường ẩn trong form
                $('#employeeId').val(employeeId);
            });
        });

        // Xác nhận xóa nhân viên
        $('#confirmDelete').click(function () {
            var employeeId = $('#employeeId').val();
            // Gửi yêu cầu xóa nhân viên bằng Ajax
            $.ajax({
                type: 'POST',
                url: '../../controllers/adminController1.php',
                data: { deleteEmployee: employeeId }, // Dữ liệu gửi đi (có thể cần chỉnh sửa)
                success: function (response) {
                    // Xử lý kết quả trả về từ server (nếu cần)
                    console.log(response);
                    // Đóng modal sau khi xóa thành công
                    $('#deleteEmployeeModal').modal('hide');
                    // Tải lại trang để cập nhật danh sách nhân viên
                    alert("Success");
                    location.reload();
                },
                error: function (xhr, status, error) {
                    // Xử lý lỗi (nếu có)
                    location.reload();
                }
            });
        });

        // Gửi dữ liệu khi sửa nhân viên
        $('#updateEmployee').click(function () {
            var formData = {
                updateEmployee: true, // Thêm trường này để xác định yêu cầu là cập nhật nhân viên
                employeeId: $('#employeeId').val(),
                fullname: $('#editFullname').val(),
                phoneNumber: $('#editPhoneNumber').val(),
                address: $('#editAddress').val(),
                email: $('#editEmail').val(),
                salary: $('#editSalary').val()
            };

            // Gửi dữ liệu cập nhật nhân viên bằng Ajax
            $.ajax({
                type: 'POST',
                url: '../../controllers/adminController1.php',
                data: formData,
                success: function (response) {
                    // Xử lý kết quả trả về từ server (nếu cần)
                    console.log(response);
                    // Đóng modal sau khi cập nhật thành công
                    $('#editEmployeeModal').modal('hide');
                    // Tải lại trang để cập nhật danh sách nhân viên
                    alert("Edit success");
                    location.reload();
                },
                error: function (xhr, status, error) {
                    // Xử lý lỗi (nếu có)
                    console.error(xhr.responseText);
                }
            });
        });


    </script>

</body>

</html>