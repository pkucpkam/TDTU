import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder, StandardScaler
from sklearn.neighbors import KNeighborsRegressor
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_absolute_error, mean_squared_error

# Đọc dữ liệu
file_path = 'Housing.csv'
data = pd.read_csv(file_path)

# Chuyển đổi categorical thành số
label_encoders = {}
categorical_columns = ['mainroad', 'guestroom', 'basement', 'hotwaterheating', 'airconditioning', 'prefarea', 'furnishingstatus']
for col in categorical_columns:
    le = LabelEncoder()
    data[col] = le.fit_transform(data[col])
    label_encoders[col] = le

# Tách features và target
X = data.drop(columns=['price'])
y = data['price']

# Chuẩn hóa dữ liệu
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# Chia tập train/test
X_train, X_test, y_train, y_test = train_test_split(X_scaled, y, test_size=0.2, random_state=42)

# Mô hình KNN
knn = KNeighborsRegressor(n_neighbors=5)
knn.fit(X_train, y_train)
y_pred_knn = knn.predict(X_test)

# Mô hình Linear Regression
lr = LinearRegression()
lr.fit(X_train, y_train)
y_pred_lr = lr.predict(X_test)

# Hồi quy tuyến tính với Gradient Descent
def gradient_descent(X, y, alpha=0.01, iterations=1000):
    m, n = X.shape
    X = np.c_[np.ones((m, 1)), X]  # Thêm cột bias (hệ số tự do)
    theta = np.zeros(n + 1)
    
    for _ in range(iterations):
        gradients = (2/m) * X.T.dot(X.dot(theta) - y)
        theta -= alpha * gradients
    
    return theta

# Huấn luyện mô hình Gradient Descent
theta_gd = gradient_descent(X_train, y_train.values)

# Dự đoán giá trị trên tập test
X_test_bias = np.c_[np.ones((X_test.shape[0], 1)), X_test]
y_pred_gd = X_test_bias.dot(theta_gd)

# Đánh giá kết quả
def evaluate_model(y_true, y_pred, model_name):
    mae = mean_absolute_error(y_true, y_pred)
    mse = mean_squared_error(y_true, y_pred)
    rmse = np.sqrt(mse)
    print(f"{model_name} - MAE: {mae:.2f}, MSE: {mse:.2f}, RMSE: {rmse:.2f}")

evaluate_model(y_test, y_pred_knn, "KNN")
evaluate_model(y_test, y_pred_lr, "Linear Regression")
evaluate_model(y_test, y_pred_gd, "Linear Regression (Gradient Descent)")

# Dự đoán giá cho một sample mới
new_sample = pd.DataFrame([{
    "area": 8000,
    "bedrooms": 3,
    "bathrooms": 2,
    "stories": 2,
    "mainroad": "yes",
    "guestroom": "yes",
    "basement": "no",
    "hotwaterheating": "no",
    "airconditioning": "yes",
    "parking": 2,
    "prefarea": "yes",
    "furnishingstatus": "semi-furnished"
}])

# Chuyển đổi categorical thành số
for col in categorical_columns:
    new_sample[col] = label_encoders[col].transform(new_sample[col])

# Chuẩn hóa sample mới
new_sample_scaled = scaler.transform(new_sample)

# Dự đoán giá với các mô hình
knn_pred = knn.predict(new_sample_scaled)
lr_pred = lr.predict(new_sample_scaled)
new_sample_bias = np.c_[np.ones((1, 1)), new_sample_scaled]
gd_pred = new_sample_bias.dot(theta_gd)

# In kết quả
print(f"Dự đoán giá nhà với sample mới:")
print(f"KNN: {knn_pred[0]:,.2f}")
print(f"Linear Regression: {lr_pred[0]:,.2f}")
print(f"Gradient Descent: {gd_pred[0]:,.2f}")
