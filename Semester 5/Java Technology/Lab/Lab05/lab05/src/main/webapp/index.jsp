<%@ page import="com.example.lab05.User" %>
<%@ page import="com.example.lab05.product.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Danh sách sản phẩm</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body style="background-color: #f8f8f8">

<div class="container-fluid p-5">
    <div class="row mb-5">
        <div class="col-md-6">
            <h3>Product Management</h3>
        </div>
        <div class="col-md-6 text-right">
            <!-- Sử dụng session đã có sẵn -->
            <%
                // Lấy đối tượng User từ session
                User user = (User) session.getAttribute("user");
                if (user != null) {
                    // Lấy tên người dùng từ đối tượng User
                    String username = user.getFullName();
            %>
            <!-- Hiển thị tên người dùng -->
            Xin chào <span class="text-danger"><%= username %></span> | <a href="logout">Logout</a>
            <%
            } else {
            %>
            <!-- Nếu không có user, hiển thị "Guest" -->
            Xin chào khách | <a href="login.jsp">Login</a>
            <%
                }
            %>
        </div>
    </div>
    <div class="row rounded border p-3">
        <div class="col-md-4">
            <h4 class="text-success">Thêm sản phẩm mới</h4>
            <form class="mt-3" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="product-name">Tên sản phẩm</label>
                    <input class="form-control" type="text" placeholder="Nhập tên sản phẩm" id="product-name" name="name">
                </div>
                <div class="form-group">
                    <label for="price">Giá sản phẩm</label>
                    <input class="form-control" type="number" placeholder="Nhập giá bán" id="price" name="price">
                </div>
                <div class="form-group">
                    <button class="btn btn-success mr-2">Thêm sản phẩm</button>
                </div>

                <div class="alert alert-danger">
                    Vui lòng nhập tên sản phẩm
                </div>
            </form>
        </div>
        <div class="col-md-8">
            <h4 class="text-success">Danh sách sản phẩm</h4>
            <p>Chọn một sản phẩm cụ thể để xem chi tiết</p>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>STT</th>
                    <th>Tên sản phẩm</th>
                    <th>Giá</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <%
                    // Lấy danh sách sản phẩm từ request
                    List<Product> products = (List<Product>) request.getAttribute("products");
                    if (products != null) {
                        int index = 1;
                        for (Product product : products) {
                %>
                <tr>
                    <td><%= index++ %></td>
                    <td><a href="#"> <%= product.getName() %> </a></td>
                    <td><%= product.getPrice() %> USD</td>
                    <td>
                        <a href="#">Chỉnh sửa</a> |
                        <a href="#">Xóa</a>
                    </td>
                </tr>
                <%      }
                } else {
                %>
                <tr>
                    <td colspan="4" class="text-center">Không có sản phẩm nào</td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
    // Add the following code if you want the name of the file appear on select
    $(".custom-file-input").on("change", function() {
        var fileName = $(this).val().split("\\").pop();
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    });
</script>

</body>
</html>
