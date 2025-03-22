import numpy as np
from sklearn.neighbors import KNeighborsClassifier

# Dữ liệu mẫu: height (feet), age (tuổi), size (S, M, L)
data = [
    [5, 45, 'H'],
    [5.11, 26, 'L'],
    [5.6, 30, 'M'],
    [5.9, 34, 'M'],
    [4.8, 40, 'H'],
    [5.8, 36, 'M'],
    [5.3, 19, 'L'],
    [5.8, 18, 'M'],
    [5.5, 23, 'L'],
    [5.6, 32, 'M'],
]

# Chuyển đổi dữ liệu thành mảng numpy
X = np.array([item[:2] for item in data])  # Các cột height và age
y = np.array([item[2] for item in data])   # Cột size

# Khởi tạo KNeighborsClassifier với k=3
knn = KNeighborsClassifier(n_neighbors=3)

# Huấn luyện mô hình
knn.fit(X, y)

# Dự đoán cho dữ liệu mới
new_height = 5.5
new_age = 38
predicted_size = knn.predict([[new_height, new_age]])[0]

# In kết quả
print(f"Với height = {new_height} feet và age = {new_age}:")
print(f"- Size quần áo dự đoán là: {predicted_size}")
