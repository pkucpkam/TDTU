import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.neighbors import KNeighborsRegressor
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_squared_error, r2_score

# Đọc dữ liệu từ file Housing.csv
df = pd.read_csv('Housing.csv')

# Chuyển đổi các biến phân loại thành số
binary_columns = ['mainroad', 'guestroom', 'basement', 'hotwaterheating', 'airconditioning', 'prefarea']
for col in binary_columns:
    df[col] = df[col].map({'yes': 1, 'no': 0})

# Chuyển furnishingstatus thành dummy variables
df = pd.get_dummies(df, columns=['furnishingstatus'], drop_first=True)

# Tách features và target
X = df.drop('price', axis=1)
y = df['price']

# Chia dữ liệu thành tập train và test
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Chuẩn hóa dữ liệu
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# KNN Model
knn = KNeighborsRegressor(n_neighbors=3)
knn.fit(X_train_scaled, y_train)
knn_pred = knn.predict(X_test_scaled)

# Linear Regression Model
lr = LinearRegression()
lr.fit(X_train_scaled, y_train)
lr_pred = lr.predict(X_test_scaled)

# Đánh giá mô hình
print("KNN Results:")
print(f"Mean Squared Error: {mean_squared_error(y_test, knn_pred):.2f}")
print(f"R2 Score: {r2_score(y_test, knn_pred):.2f}")

print("\nLinear Regression Results:")
print(f"Mean Squared Error: {mean_squared_error(y_test, lr_pred):.2f}")
print(f"R2 Score: {r2_score(y_test, lr_pred):.2f}")

# Tạo sample_house khớp với các cột của X
sample_house = pd.DataFrame(columns=X.columns)
sample_house.loc[0] = 0  # Khởi tạo tất cả giá trị là 0
sample_house.loc[0, 'area'] = 8000
sample_house.loc[0, 'bedrooms'] = 4
sample_house.loc[0, 'bathrooms'] = 2
sample_house.loc[0, 'stories'] = 2
sample_house.loc[0, 'mainroad'] = 1
sample_house.loc[0, 'guestroom'] = 0
sample_house.loc[0, 'basement'] = 1
sample_house.loc[0, 'hotwaterheating'] = 0
sample_house.loc[0, 'airconditioning'] = 1
sample_house.loc[0, 'parking'] = 2
sample_house.loc[0, 'prefarea'] = 1
# Giả sử nhà được furnished (nếu trong dataset của bạn có unfurnished thì set là 0)
if 'furnishingstatus_unfurnished' in X.columns:
    sample_house.loc[0, 'furnishingstatus_unfurnished'] = 0

sample_scaled = scaler.transform(sample_house)
knn_prediction = knn.predict(sample_scaled)
lr_prediction = lr.predict(sample_scaled)

print("\nPredictions for sample house:")
print(f"KNN Prediction: {knn_prediction[0]:.2f}")
print(f"Linear Regression Prediction: {lr_prediction[0]:.2f}")

print("\nDataset Info:")
print(f"Number of samples: {len(df)}")
print(f"Features used: {list(X.columns)}")