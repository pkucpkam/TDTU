function deleteComment(placeID, commentContent) {
    // Tạo một yêu cầu AJAX để gửi thông tin đến tệp PHP xử lý
    $.ajax({
        url: '/Final/comment/delete_comment.php',
        type: 'POST',
        data: { placeID: placeID, commentContent: commentContent },
        dataType: 'json',
        success: function(response) {
            if (response.status === "success") {
                // Nếu xóa thành công, tải lại trang để cập nhật danh sách bình luận
                location.reload();
            } else {
                // Nếu có lỗi xảy ra, hiển thị thông báo lỗi
                alert('Error deleting comment. Please try again later.');
            }
        },
        error: function(xhr, status, error) {
            // Xử lý lỗi nếu có
            console.error(xhr.responseText);
        }
    });
}