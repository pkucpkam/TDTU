<?php
require_once '../../controllers/usersController.php';
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
                    <span>Generall Information</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider">

            <!-- Heading -->
            <div class="sidebar-heading">
                Function
            </div>

            <!-- Nav Item - Employee Management -->
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

            <li class="nav-item active">
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
                                <span
                                    class="mr-2 d-none d-lg-inline text-gray-600 small"><?php echo $_SESSION['username']; ?></span>
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
                    <h1 class="h3 mb-2 text-gray-800">Employee Management</h1>
                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">List of customers</h6>
                            <a href="#" class="btn btn-success btn-icon-split float-left mt-3" data-toggle="modal"
                                data-target="#addUserModal">
                                <span class="text">Add Customer</span>
                            </a>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>Username</th>
                                            <th>Name</th>
                                            <th>Phone</th>
                                            <th>Email</th>
                                            <th>ID Card</th>
                                            <th>Balance</th>
                                            <th>Function</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php foreach ($listUsers as $user): ?>
                                            <tr>
                                                <td><?php echo $user['username']; ?></td>
                                                <td><?php echo $user['fullname']; ?></td>
                                                <td><?php echo $user['phonenumber']; ?></td>
                                                <td><?php echo $user['email']; ?></td>
                                                <td><?php echo $user['IDCard']; ?></td>
                                                <td><?php echo $user['accountBalance']; ?></td>
                                                <td>
                                                    <button class="btn btn-danger btn-icon-split deleteUserBtn"
                                                        data-toggle="modal" data-target="#deleteUserModal"
                                                        data-user-id="<?php echo $user['username']; ?>">
                                                        <span class="text">Delete</span>
                                                    </button>
                                                    <!-- Edit Button -->
                                                    <button class="btn btn-primary btn-icon-split editUserBtn"
                                                        data-toggle="modal" data-target="#editUserModal"
                                                        data-user-id="<?php echo $user['username']; ?>">
                                                        <span class="text">Edit</span>
                                                    </button>
                                                </td>
                                            </tr>
                                        <?php endforeach; ?>
                                    </tbody>


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

    <!-- Modal Thêm Khách hàng -->
    <div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Add Customer</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="addUserForm" action="admin_users.php" method="POST">
                        <div class="form-group">
                            <label for="username">Username:</label>
                            <input type="text" class="form-control" id="username" name="username"
                                placeholder="Enter your login name">
                        </div>
                        <div class="form-group">
                            <label for="fullname">Full name:</label>
                            <input type="text" class="form-control" id="fullname" name="fullname"
                                placeholder="Enter first and last name">
                        </div>
                        <div class="form-group">
                            <label for="phoneNumber">Phone number:</label>
                            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                                placeholder="Enter your phone number">
                        </div>
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" class="form-control" id="email" name="email" placeholder="Nhập email">
                        </div>
                        <div class="form-group">
                            <label for="IDCard">ID Card:</label>
                            <input type="text" class="form-control" id="IDCard" name="IDCard" placeholder="Nhập CMND">
                        </div>
                        <div class="form-group">
                            <label for="accountBalance">Account balance:</label>
                            <input type="text" class="form-control" id="accountBalance" name="accountBalance" value="0"
                                readonly>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary" name="saveUser" id="saveUser">Save</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Xóa Khách hàng -->
    <div class="modal fade" id="deleteUserModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Delete Customer</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="deleteUserForm">
                        <div class="form-group">
                            <label for="usernameToDelete">Username:</label>
                            <input type="text" class="form-control" id="usernameToDelete"
                                placeholder="Nhập tên đăng nhập" readonly>
                        </div>
                        <p>Are you sure you want to delete the customer <strong id="usernameToDeleteText"></strong>?</p>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-danger" id="confirmDeleteUser">Delete</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Sửa Khách hàng -->
    <div class="modal fade" id="editUserModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Edit Customer Information</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="editUserForm" action="admin_users.php" method="POST">
                        <div class="form-group">
                            <label for="editUsername">Username:</label>
                            <input type="text" class="form-control" id="editUsername" name="editUsername" readonly>
                        </div>
                        <div class="form-group">
                            <label for="editFullname">Fullname:</label>
                            <input type="text" class="form-control" id="editFullname" name="editFullname">
                        </div>
                        <div class="form-group">
                            <label for="editPhoneNumber">Phone Number:</label>
                            <input type="text" class="form-control" id="editPhoneNumber" name="editPhoneNumber">
                        </div>
                        <div class="form-group">
                            <label for="editEmail">Email:</label>
                            <input type="email" class="form-control" id="editEmail" name="editEmail">
                        </div>
                        <div class="form-group">
                            <label for="editIDCard">ID Card:</label>
                            <input type="text" class="form-control" id="editIDCard" name="editIDCard">
                        </div>
                        <div class="form-group">
                            <label for="editAccountBalance">Account balance:</label>
                            <input type="text" class="form-control" id="editAccountBalance" name="editAccountBalance">
                        </div>


                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary" name="updateUser" id="updateUser">Save</button>
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
            // Khi modal xóa khách hàng được mở
            $('#deleteUserModal').on('show.bs.modal', function (event) {
                // Lấy button đã kích hoạt mở modal
                var button = $(event.relatedTarget);
                // Lấy dữ liệu từ thuộc tính data-user-id của button
                var userId = button.data('user-id');
                // Hiển thị tên khách hàng trong modal
                $(this).find('.modal-body #usernameToDelete').val(userId);
                // Đặt lại nội dung thông báo xóa khách hàng
                var userName = button.closest('tr').find('td:eq(1)').text();
                $(this).find('.modal-body #usernameToDeleteText').text(userName);
            });
        });

        $(document).ready(function () {
            // Khi modal sửa khách hàng được mở
            $('#editUserModal').on('show.bs.modal', function (event) {
                // Lấy button đã kích hoạt mở modal
                var button = $(event.relatedTarget);
                // Lấy thông tin của khách hàng từ dòng được chọn trong bảng
                var row = button.closest('tr');
                var username = row.find('td:eq(0)').text(); // Lấy username từ cột đầu tiên
                var fullname = row.find('td:eq(1)').text(); // Lấy fullname từ cột thứ hai
                var phoneNumber = row.find('td:eq(2)').text(); // Lấy phoneNumber từ cột thứ ba
                var email = row.find('td:eq(3)').text(); // Lấy email từ cột thứ tư
                var IDCard = row.find('td:eq(4)').text(); // Lấy IDCard từ cột thứ năm
                var accountBalance = row.find('td:eq(5)').text(); // Lấy accountBalance từ cột thứ sáu

                console.log(username)
                // Điền thông tin của khách hàng vào các trường nhập liệu trong modal sửa
                $('#editUsername').val(username);
                $('#editFullname').val(fullname);
                $('#editPhoneNumber').val(phoneNumber);
                $('#editEmail').val(email);
                $('#editIDCard').val(IDCard);
                $('#editAccountBalance').val(accountBalance);
            });
        });


        $(document).ready(function () {
            // Xử lý sự kiện khi nhấn nút Lưu trong modal Thêm Khách hàng
            $("#saveUser").click(function () {
                // Lấy dữ liệu từ form
                var username = $("#username").val();
                var fullname = $("#fullname").val();
                var phoneNumber = $("#phoneNumber").val();
                var email = $("#email").val();
                var IDCard = $("#IDCard").val();
                var accountBalance = $("#accountBalance").val();

                if (!validateEmail(email)) {
                    alert("Vui lòng nhập một địa chỉ email hợp lệ.");
                    return;
                }

                // Gửi yêu cầu AJAX
                $.ajax({
                    type: "POST",
                    url: "../../controllers/usersController1.php", // Đường dẫn đến file xử lý
                    data: {
                        saveUser: 1,
                        username: username,
                        fullname: fullname,
                        phoneNumber: phoneNumber,
                        email: email,
                        IDCard: IDCard,
                        accountBalance: accountBalance
                    },
                    success: function (response) {
                        if (response == 'success') {
                            alert('Success')
                        }
                        else {
                            alert("Failed")
                        }

                        location.reload();
                    }
                });
            });
        });

        function validateEmail(email) {
            var re = /\S+@\S+\.\S+/;
            return re.test(email);
        }

        $(document).ready(function () {
            // Xử lý sự kiện khi người dùng xác nhận xóa khách hàng
            $("#confirmDeleteUser").click(function () {
                // Lấy tên đăng nhập của khách hàng cần xóa
                var usernameToDelete = $("#usernameToDelete").val();

                // Gửi yêu cầu AJAX để xóa khách hàng
                $.ajax({
                    type: "POST",
                    url: "../../controllers/usersController1.php",
                    data: {
                        deleteUser: 1,
                        usernameToDelete: usernameToDelete
                    },
                    success: function (response) {
                        // Kiểm tra phản hồi từ máy chủ
                        if (response.trim() == '1') {
                            // Nếu xóa thành công, đóng modal và làm mới trang để cập nhật danh sách khách hàng
                            $("#deleteUserModal").modal("hide");
                            alert("Delete customer success.");
                            location.reload();
                        } else {
                            // Nếu xóa không thành công, hiển thị thông báo lỗi
                            alert("Cannot delete customer. Please try again later.");
                        }
                    },
                    error: function () {
                        // Nếu có lỗi xảy ra trong quá trình gửi yêu cầu AJAX, hiển thị thông báo lỗi
                        alert("Đã xảy ra lỗi khi gửi yêu cầu AJAX!");
                    }
                });
            });
        });

        $("#updateUser").click(function () {
            // Lấy dữ liệu từ các trường nhập trong modal sửa khách hàng
            var username = $("#editUsername").val();
            var fullname = $("#editFullname").val();
            var phoneNumber = $("#editPhoneNumber").val();
            var email = $("#editEmail").val();
            var IDCard = $("#editIDCard").val();
            var accountBalance = $("#editAccountBalance").val();

            if (!validateEmail(email)) {
                alert("Vui lòng nhập một địa chỉ email hợp lệ.");
                return;
            }

            console.log(username)
            // Gửi yêu cầu AJAX đến máy chủ
            $.ajax({
                type: "POST",
                url: "../../controllers/usersController1.php", // Đường dẫn đến tập lệnh xử lý
                data: {
                    updateUser: 1,
                    username: username,
                    fullname: fullname,
                    phoneNumber: phoneNumber,
                    email: email,
                    IDCard: IDCard,
                    accountBalance: accountBalance
                },
                success: function (response) {
                    if (response == 'ok') {
                        alert('Successfully updated customer information!');
                        location.reload();
                    }
                    else {
                        alert('Edit success')
                        location.reload();
                    }
                },
                error: function () {
                    // Xử lý khi có lỗi xảy ra trong quá trình gửi yêu cầu AJAX
                    alert("Đã xảy ra lỗi khi gửi yêu cầu AJAX!");
                }
            });
        });


    </script>

</body>

</html>