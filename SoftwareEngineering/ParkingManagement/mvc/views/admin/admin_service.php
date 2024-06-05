<?php
require_once '../../controllers/serviceController.php';
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

            <li class="nav-item active">
                <a class="nav-link " href="admin_service.php">
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
                    <h1 class="h3 mb-2 text-gray-800">Service Management</h1>
                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">List of services</h6>
                            <center>
                                <a href="#" class="btn btn-success btn-icon-split float-left mt-3" data-toggle="modal"
                                    data-target="#addServiceModal">
                                    <span class="text">Add Service</span>
                                </a>
                            </center>
                        </div>

                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>Service ID</th>
                                            <th>Name Service</th>
                                            <th>Description</th>
                                            <th>Function</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php foreach ($listService as $service): ?>
                                            <tr>
                                                <td><?php echo $service['serviceID']; ?></td>
                                                <td><?php echo $service['nameService']; ?></td>
                                                <td><?php echo $service['description']; ?></td>
                                                <td>
                                                    <button class="btn btn-danger btn-icon-split deleteEmployeeBtn"
                                                        data-toggle="modal" data-target="#deleteServiceModal"
                                                        data-service-id="<?php echo $service['serviceID']; ?>">
                                                        <span class="text">Delete</span>
                                                    </button>
                                                    <button class="btn btn-primary btn-icon-split editEmployeeBtn"
                                                        data-toggle="modal" data-target="#editServiceModal"
                                                        data-service-id="<?php echo $service['serviceID']; ?>">
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

    <!-- Modal Them Dich Vu-->
    <div class="modal fade" id="addServiceModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Add Service</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="addServiceForm" action="admin_service.php" method="POST">
                        <div class="form-group">
                            <label for="serviceName">Service's Name:</label>
                            <input type="text" class="form-control" id="serviceName" name="serviceName"
                                placeholder="Enter the service name">
                        </div>
                        <div class="form-group">
                            <label for="serviceDescription">Description:</label>
                            <input type="text" class="form-control" id="serviceDescription" name="serviceDescription"
                                placeholder="Enter a service description">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary" form="addServiceForm" id="saveService" >Save</button>
                </div>
            </div>
        </div>
    </div>



    <!-- Modal Sửa Dịch Vụ -->
    <div class="modal fade" id="editServiceModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Edit Service Information</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="editServiceForm" action="admin_service.php" method="POST">
                        <div class="form-group">
                            <label for="editServiceName">Service's Name:</label>
                            <input type="text" class="form-control" id="editServiceName" name="editServiceName">
                        </div>
                        <div class="form-group">
                            <label for="editServiceDescription">Description:</label>
                            <input type="text" class="form-control" id="editServiceDescription"
                                name="editServiceDescription">
                        </div>
                        <input type="hidden" id="editServiceID" name="editServiceID">
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary" form="editServiceForm" id="updateService" >Save</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Xóa Dịch Vụ -->
    <div class="modal fade" id="deleteServiceModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Delete Service</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="deleteServiceForm">
                        <div class="form-group">
                            <label for="deleteServiceID">ServiceID:</label>
                            <input type="text" class="form-control" id="deleteServiceID" readonly>
                        </div>
                        <p>Are you sure you want to delete this service?</p>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-danger" id="confirmDeleteService">Delete</button>
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
            // Submit form data to add a new service
            $('#saveService').click(function (e) {
                e.preventDefault(); // Prevent the default form submission

                // Get data from the form
                var formData = {
                    serviceName: $('#serviceName').val(),
                    serviceDescription: $('#serviceDescription').val(),
                    saveService: true // Add this variable to identify the request to add a service
                };

                console.log(formData)

                // Send data to the server using Ajax
                $.ajax({
                    type: 'POST',
                    url: '../../controllers/serviceController1.php',
                    data: formData,
                    success: function (response) {
                        // Handle the response from the server
                        console.log(response);
                        // Close the modal after successfully adding the service
                        $('#addServiceModal').modal('hide');
                        // Reload the page to update the service list
                        alert("Thêm dịch vụ thành công");
                        location.reload();
                    },
                    error: function (xhr, status, error) {
                        // Handle errors (if any)
                        console.error(xhr.responseText);
                    }
                });
            });

            // When the delete service modal is shown
            $('#deleteServiceModal').on('show.bs.modal', function (event) {
                // Get the button that triggered the modal
                var button = $(event.relatedTarget);
                // Get the service ID from the data-employee-id attribute of the button
                var serviceId = button.data('service-id');
                // Display the service ID in the modal
                $(this).find('.modal-body #deleteServiceID').val(serviceId);
            });

            // When the edit service button in the table is clicked
            $('.editEmployeeBtn').on('click', function () {
                // Get the service ID from the data-service-id attribute of the button
                var serviceId = $(this).data('service-id');

                // Get the service information from the corresponding table row
                var serviceName = $(this).closest('tr').find('td:eq(1)').text();
                var serviceDescription = $(this).closest('tr').find('td:eq(2)').text();

                // Set the service information in the edit service form
                $('#editServiceName').val(serviceName);
                $('#editServiceDescription').val(serviceDescription);

                // Set the service ID in a hidden field in the form
                $('#editServiceID').val(serviceId);
            });



            // Confirm delete service
            $('#confirmDeleteService').click(function () {
                var serviceId = $('#deleteServiceID').val();
                // Send a request to delete the service via Ajax
                $.ajax({
                    type: 'POST',
                    url: '../../controllers/serviceController1.php',
                    data: { deleteService: serviceId },
                    success: function (response) {
                        // Handle the response from the server
                        console.log(response);
                        // Close the modal after successfully deleting the service
                        $('#deleteServiceModal').modal('hide');
                        // Reload the page to update the service list
                        alert("Xóa dịch vụ thành công");
                        location.reload();
                    },
                    error: function (xhr, status, error) {
                        // Handle errors (if any)
                        console.error(xhr.responseText);
                    }
                });
            });

            // Submit edited service data
            $('#updateService').click(function () {
                var formData = {
                    updateService: true, // Add this variable to identify the request to update a service
                    serviceId: $('#editServiceID').val(),
                    serviceName: $('#editServiceName').val(),
                    serviceDescription: $('#editServiceDescription').val()
                };

                // Send data to update the service via Ajax
                $.ajax({
                    type: 'POST',
                    url: '../../controllers/serviceController1.php',
                    data: formData,
                    success: function (response) {
                        // Handle the response from the server
                        console.log(response);
                        // Close the modal after successfully updating the service
                        $('#editServiceModal').modal('hide');
                        // Reload the page to update the service list
                        alert("Cập nhật thông tin dịch vụ thành công");
                         location.reload();
                    },
                    error: function (xhr, status, error) {
                        // Handle errors (if any)
                        console.error(xhr.responseText);
                    }
                });
            });
        });

    </script>

</body>

</html>