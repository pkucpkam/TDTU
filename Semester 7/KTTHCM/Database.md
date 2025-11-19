
# **Cơ sở dữ liệu – Tổng quan**

## **1️⃣ Thiết kế cơ sở dữ liệu (Design)**

* **Mô hình ERD (Entity-Relationship Diagram):**

  * **Thực thể (Entity / Table):** Ví dụ `Students`, `Courses`.
  * **Thuộc tính (Attribute / Column):** Ví dụ `StudentID`, `Name`, `Email`.
  * **Mối quan hệ (Relationship):**

    * Một-một (1:1)
    * Một-nhiều (1:N)
    * Nhiều-nhiều (M:N) → dùng bảng trung gian
  * ERD giúp **tổ chức dữ liệu hợp lý** trước khi tạo CSDL.

---

## **2️⃣ Truy vấn dữ liệu cơ bản (SELECT)**

```sql
-- Lấy tất cả dữ liệu từ bảng Students
SELECT * FROM Students;

-- Lấy cột cụ thể
SELECT StudentID, Name, Email FROM Students;

-- Lọc dữ liệu
SELECT * FROM Students WHERE Age >= 18;

-- Nhóm dữ liệu
SELECT DepartmentID, COUNT(*) AS StudentCount
FROM Students
GROUP BY DepartmentID;

-- Lọc sau khi nhóm
SELECT DepartmentID, COUNT(*) AS StudentCount
FROM Students
GROUP BY DepartmentID
HAVING COUNT(*) > 10;

-- Sắp xếp dữ liệu
SELECT * FROM Students ORDER BY Name ASC;
```

---

## **3️⃣ JOIN – Kết nối bảng (Rất quan trọng)**

```sql
-- INNER JOIN: chỉ lấy bản ghi có kết nối ở cả hai bảng
SELECT s.Name, d.DepartmentName
FROM Students s
INNER JOIN Departments d ON s.DepartmentID = d.DepartmentID;

-- LEFT JOIN: lấy tất cả bản ghi bên trái, bên phải nếu có
SELECT s.Name, d.DepartmentName
FROM Students s
LEFT JOIN Departments d ON s.DepartmentID = d.DepartmentID;

-- RIGHT JOIN: lấy tất cả bản ghi bên phải, bên trái nếu có
SELECT s.Name, d.DepartmentName
FROM Students s
RIGHT JOIN Departments d ON s.DepartmentID = d.DepartmentID;

-- FULL OUTER JOIN: lấy tất cả bản ghi từ cả hai bảng
SELECT s.Name, d.DepartmentName
FROM Students s
FULL OUTER JOIN Departments d ON s.DepartmentID = d.DepartmentID;
```

---

## **4️⃣ Subquery (Truy vấn con)**

```sql
-- Lấy sinh viên có điểm cao hơn trung bình
SELECT Name, Score
FROM Students
WHERE Score > (SELECT AVG(Score) FROM Students);

-- Sử dụng subquery trong FROM
SELECT DepartmentID, AVG(Score) AS AvgScore
FROM (SELECT * FROM Students WHERE Age >= 18) AS AdultStudents
GROUP BY DepartmentID;
```

---

## **5️⃣ Thao tác dữ liệu (DML – Data Manipulation Language)**

```sql
-- Thêm dữ liệu
INSERT INTO Students (StudentID, Name, Age, DepartmentID)
VALUES (1, 'Alice', 20, 101);

-- Cập nhật dữ liệu
UPDATE Students
SET Age = 21
WHERE StudentID = 1;

-- Xóa dữ liệu
DELETE FROM Students
WHERE StudentID = 1;
```

---

## **6️⃣ Định nghĩa dữ liệu (DDL – Data Definition Language)**

```sql
-- Tạo bảng
CREATE TABLE Students (
    StudentID INT PRIMARY KEY,
    Name VARCHAR(100),
    Age INT,
    DepartmentID INT
);

-- Thêm cột mới
ALTER TABLE Students
ADD Email VARCHAR(100);

-- Xóa cột
ALTER TABLE Students
DROP COLUMN Email;

-- Thêm khóa ngoại
ALTER TABLE Students
ADD CONSTRAINT FK_Department
FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentID);
```

---

## **7️⃣ Các lệnh nâng cao / hay dùng**

* **UNION / UNION ALL:** Gộp kết quả nhiều SELECT.
* **CASE WHEN:** Tạo điều kiện trong SELECT.
* **Views:** Tạo bảng ảo từ truy vấn.
* **Indexes:** Tăng tốc truy vấn.
* **Stored Procedures / Functions:** Lưu và tái sử dụng logic trong DB.

---
