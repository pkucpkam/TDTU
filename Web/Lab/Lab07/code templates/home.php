<?php
session_start();

if (!isset($_SESSION['username'])) {
   header("Location: login.php");
   exit;
}

$username = $_SESSION['username'];

?>

<!DOCTYPE html>
<html lang="en">

<head>
   <title>Bootstrap Example</title>
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1">
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
   <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
      integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
   <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

   <style>
      .fa,
      .fas {
         color: #858585;
      }

      .fa-folder {
         color: rgb(74, 158, 255);
      }

      i.fa,
      table i.fas {
         font-size: 16px;
         margin-right: 6px;
      }

      i.action {
         cursor: pointer;
      }

      a {
         color: black;
      }
   </style>
</head>

<body>
   <div class="container">
      <div class="row align-items-center py-5">
         <div class="col-6">
            <h3>File Manager</h3>
         </div>
         <div class="col-6">
            <h5 class="text-right">Xin chào <?php echo $username ?>, <a class="text-primary"
                  href="logout.php">Logout</a></h5>
         </div>
      </div>
      <ol class="breadcrumb">
         <li class="breadcrumb-item"><a href="#">xampp</a></li>
         <li class="breadcrumb-item"><a href="#">htpdocs</a></li>
         <li class="breadcrumb-item active">Lab07</li>
      </ol>

      <div class="input-group mb-3">
         <div class="input-group-prepend">
            <span class="input-group-text">
               <!-- <span class="fa fa-search"></span>          -->
            </span>
         </div>
         <input type="text" class="form-control" placeholder="Search">
      </div>
      <div class="btn-group my-3">
         <button type="button" class="btn btn-light border" onclick="showNewFolderModal()">
            <i class="fas fa-folder-plus"></i> New folder
         </button>
         <button type="button" class="btn btn-light border" onclick="showNewFileModal()">
            <i class="fas fa-file"></i> Create text file
         </button>
      </div>

      <table class="table table-hover border">
         <thead>
            <tr>
               <th>Name</th>
               <th>Type</th>
               <th>Size</th>
               <th>Last modified</th>
               <th>Actions</th>
            </tr>
         </thead>
         <tbody>

         </tbody>
      </table>
      <div class="border rounded mb-3 mt-5 p-3">
         <h4>File upload</h4>
         <form id="uploadForm" enctype="multipart/form-data">
            <div class="form-group">
               <div class="custom-file">
                  <input type="file" class="custom-file-input" id="fileToUpload" name="fileToUpload">
                  <label class="custom-file-label" for="customFile">Choose file</label>
               </div>
            </div>
            <p>Người dùng chỉ được upload tập tin có kích thước tối đa là 20 MB.</p>
            <p>Các tập tin thực thi (*.exe, *.msi, *.sh) không được phép upload.</p>
            <p><strong>Yêu cầu nâng cao</strong>: hiển thị progress bar trong quá trình upload.</p>
            <button class="btn btn-success px-5">Upload</button>
         </form>
      </div>

      <div class="modal-example my-5">
         <h4>Một số dialog mẫu</h4>
         <p>Nhấn vào để xem kết quả</p>
         <ul>
            <li><a href="#" data-toggle="modal" data-target="#confirm-delete">Confirm delete</a></li>
            <li><a href="#" data-toggle="modal" data-target="#confirm-rename">Confirm rename</a></li>
            <li><a href="#" data-toggle="modal" data-target="#new-file-dialog">New file dialog</a></li>
            <li><a href="#" data-toggle="modal" data-target="#message-dialog">Message Dialog</a></li>
         </ul>
      </div>

   </div>


   <!-- Delete dialog -->
   <div class="modal fade" id="confirm-delete">
      <div class="modal-dialog">
         <div class="modal-content">

            <div class="modal-header">
               <h4 class="modal-title">Xóa tập tin</h4>
               <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <div class="modal-body">
               Bạn có chắc rằng muốn xóa tập tin này
            </div>

            <div class="modal-footer">
               <button type="button" class="btn btn-danger" id="confirmDeleteBtn" data-dismiss="modal">Xóa</button>
               <button type="button" class="btn btn-secondary" data-dismiss="modal">Không</button>
            </div>
         </div>
      </div>
   </div>


   <!-- Rename dialog -->
   <div class="modal fade" id="confirm-rename">
      <div class="modal-dialog">
         <div class="modal-content">
            <div class="modal-header">
               <h4 class="modal-title">Đổi tên</h4>
               <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <form method="POST" action="" id="renameForm">
               <div class="modal-body">
                  <p>Nhập tên mới cho tập tin <strong id="old-file-name"></strong></p>
                  <input type="hidden" name="old_name" id="old-name-input">
                  <input type="text" name="new_name" id="new-name-input" placeholder="Nhập tên mới"
                     class="form-control" />
               </div>
               <div class="modal-footer">
                  <button type="submit" name="rename" class="btn btn-primary">Lưu</button>
                  <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
               </div>
            </form>
         </div>
      </div>
   </div>

   <!-- New file dialog -->
   <div class="modal fade" id="new-file-dialog">
      <div class="modal-dialog">
         <div class="modal-content">

            <div class="modal-header">
               <h4 class="modal-title">Tạo tập tin mới</h4>
               <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <div class="modal-body">
               <div class="form-group">
                  <label for="name">File Name</label>
                  <input type="text" placeholder="File name" class="form-control" id="name" />
               </div>
               <div class="form-group">
                  <label for="content">Nội dung</label>
                  <textarea rows="10" id="content" class="form-control" placeholder="Nội dung"></textarea>

               </div>
            </div>

            <div class="modal-footer">
               <button type="button" class="btn btn-success" id="createFileBtn" data-dismiss="modal">Lưu</button>
            </div>
         </div>
      </div>
   </div>

   <!--download-->
   <div class="modal fade" id="confirm-download">
      <div class="modal-dialog">
         <div class="modal-content">
            <div class="modal-header">
               <h4 class="modal-title">Tải xuống tập tin</h4>
               <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
               <p id="download-file-name"></p>
               <input type="hidden" name="download_name" id="download-name-input">
            </div>
            <div class="modal-footer">
               <button type="button" id="download-button" class="btn btn-primary">Tải xuống</button>
               <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
            </div>
         </div>
      </div>
   </div>

   <!-- message dialog -->
   <div class="modal fade" id="message-dialog">
      <div class="modal-dialog">
         <div class="modal-content">

            <div class="modal-header">
               <h4 class="modal-title">Xóa file</h4>
               <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <div class="modal-body">
               <p>Bạn không được cấp quyền để xóa tập tin/thư mục này</p>
            </div>

            <div class="modal-footer">
               <button type="button" class="btn btn-info" data-dismiss="modal">Đóng</button>
            </div>
         </div>
      </div>
   </div>

   <!-- modal create folder -->
   <div class="modal fade" id="new-folder-dialog" tabindex="-1" role="dialog" aria-labelledby="newFolderModalLabel"
      aria-hidden="true">
      <div class="modal-dialog" role="document">
         <div class="modal-content">
            <div class="modal-header">
               <h5 class="modal-title" id="newFolderModalLabel">New Folder</h5>
               <button type="button" class="close" data-dismiss="modal" arialabel="Close">
                  <span aria-hidden="true">&times;</span>
               </button>
            </div>
            <div class="modal-body">
               <div class="form-group">
                  <label for="folderName">Folder Name:</label>
                  <input type="text" class="form-control" id="folderName" placeholder="Enter folder name">
               </div>
            </div>
            <div class="modal-footer">
               <button type="button" class="btn btn-secondary" datadismiss="modal">Cancel</button>
               <button type="button" class="btn btn-primary" id="createFolderBtn">OK</button>
            </div>
         </div>
      </div>
   </div>

   <script>
      //Load_ files
      function loadFiles() {
         fetch('load_files.php')
            .then(response => {
               if (!response.ok) {
                  throw new Error('Error loading files');
               }
               return response.json();
            })
            .then(data => {
               const tableBody = document.querySelector('tbody')
               tableBody.innerHTML = '';
               data.forEach(file => {
                  const row = document.createElement('tr');
                  row.innerHTML = `
                  
                     <td>
                        <i class="fas fa-file"></i>
                        ${file.name}
                     </td>
                     <td>${file.type}</td>
                     <td>${file.size}</td>
                     <td>${file.last_modified}</td>
                     <td>
                     <i class="fa fa-download action" onclick="confirmDownload('${file.name}')"></i>
                     <i class="fa fa-edit action" onclick="showRenameModal('${file.name}')"></i>
                     <i class="fa fa-trash action"
                     onclick="deleteFile('${file.name}')"</i>
                     </td>
                     `;
                  tableBody.appendChild(row);
               });
            })
            .catch(error => console.error('Error:', error));
      }
      window.addEventListener('DOMContentLoaded', (event) => {
         loadFiles();
      });

      //Crete folder 
      function showNewFolderModal() {
         $(document).ready(function () {
            $('#new-folder-dialog').modal('show');
         });
      }

      $(document).ready(function () {
         $("#createFolderBtn").click(function () {
            var foldername = $("#folderName").val();
            createFolder(foldername);
         });
      });

      function createFolder(foldername) {
         fetch('create_folder.php', {
            method: 'POST',
            body: JSON.stringify({ foldername: foldername }),
            headers: {
               'Content-Type': 'application/json'
            }
         })
            .then(response => {
               if (!response.ok) {
                  throw new Error('Error creating folder');
               }
               $('#new-folder-dialog').modal('hide');
               loadFiles();

            })
            .catch(error => console.error('Error:', error));
      }

      // Create files 
      function showNewFileModal() {
         $(document).ready(function () {
            $('#new-file-dialog').modal('show');
         });
      }

      $(document).ready(function () {
         $("#createFileBtn").click(function () {

            var filename = $("#name").val();
            var content = $("#content").val();
            createTextFile(filename, content);
         });
      });


      function createTextFile(filename, content) {
         fetch('create_text_file.php', {
            method: 'POST',
            body: JSON.stringify({ filename: filename, content: content }),
            headers: {
               'Content-Type': 'application/json'
            }
         })
            .then(response => {
               if (!response.ok) {

                  throw new Error('Error creating text file');
               }
               loadFiles();
            })
            .catch(error => console.error('Error:', error));
      }

      // Upload file
      $('#uploadForm').submit(function (e) {
         e.preventDefault();
         var formData = new FormData(this);
         $.ajax({
            url: 'upload_file.php',
            type: 'POST',
            data: formData,
            success: function (response) {
               alert(response);
               $('#uploadForm')[0].reset();
               loadFiles();
            },
            error: function (error) {
               console.error('Error:', error);
            },
            cache: false,
            contentType: false,
            processData: false
         });
      });


      //Delete file
      function confirmDelete(fileName) {
         fetch('delete_file.php', {
            method: 'POST',
            body: JSON.stringify({ fileName: fileName }),
            headers: {
               'Content-Type': 'application/json'
            }
         })
            .then(response => {
               if (!response.ok) {
                  throw new Error('Error deleting file');
               }
               console.log(fileName);
               loadFiles();
            })
            .catch(error => console.error('Error:', error));
      }

      function deleteFile(fileName) {
         $('#confirm-delete').modal('show');
         $("#confirmDeleteBtn").click(function () {
            confirmDelete(fileName);
         });
      }

      // Dowload 
      function confirmDownload(fileName) {
         $('#confirm-download').modal('show');
         $("#download-file-name").text(fileName);
         $("#download-name-input").val(fileName);
      }

      $("#download-button").click(function () {
         var fileName = $("#download-name-input").val();
         window.location.href = 'download_file.php?fileName=' + fileName;
         $('#confirm-download').modal('hide');
      });

      // Rename
      function showRenameModal(oldFileName) {
         $('#confirm-rename').modal('show');

         $('#old-file-name').text(oldFileName);

         $('#old-name-input').val(oldFileName);
      }

      function handleRenameSubmit(event) {
         event.preventDefault(); 

         var oldFileName = $('#old-name-input').val();
         var newFileName = $('#new-name-input').val();

         console.log(oldFileName)
         $.ajax({
            url: 'rename_file.php', 
            type: 'POST',
            data: JSON.stringify({ oldFileName: oldFileName, newFileName: newFileName }), // Dữ liệu gửi đi dưới dạng JSON
            contentType: 'application/json',
            success: function (response) {
               alert(response); 
               $('#confirm-rename').modal('hide'); 
               loadFiles(); 
            },
            error: function (error) {
               console.error('Error:', error); 
            }
         });
      }

      $('#renameForm').submit(handleRenameSubmit);

   </script>
</body>

</html>