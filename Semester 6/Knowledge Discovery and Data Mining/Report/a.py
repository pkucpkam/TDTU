import cv2

# Đọc ảnh gốc
image = cv2.imread('images/camera_1743470411548.jpg')

# Làm mờ ảnh với Gaussian Blur (tham số càng lớn thì ảnh càng mờ)
blurred_image = cv2.GaussianBlur(image, (5, 5), 0)

# Lưu ảnh đã làm mờ
cv2.imwrite('blurred_output.jpg', blurred_image)
