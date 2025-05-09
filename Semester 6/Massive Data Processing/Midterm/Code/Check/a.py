import pandas as pd

# Đường dẫn tới file baskets.csv (Đổi lại đường dẫn nếu cần)
file_path = 'baskets.csv'

# Đọc file CSV bằng pandas (sử dụng đúng dấu phân cách là dấu phẩy `,`)
df = pd.read_csv(file_path, delimiter=',')  # Sửa từ '\t' thành ','

# Kiểm tra tên cột để đảm bảo đọc đúng
print("Tên cột trong file:", df.columns)

# Lọc ra các bản ghi của khách hàng mã 1249
customer_1249 = df[df['Member_number'] == 4698]

# Lấy ra các cột cần thiết (ở đây là ngày)
purchase_dates = customer_1249[['Date']]

# In ra các ngày mua hàng của khách hàng 1249
print("Những ngày mua hàng của khách hàng 4698:")
print(purchase_dates)
