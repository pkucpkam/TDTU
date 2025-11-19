
# **Mô hình hóa UML (Unified Modeling Language)**

UML là **ngôn ngữ mô hình hóa chuẩn** dùng để mô tả cấu trúc và hành vi của hệ thống phần mềm. Nó giúp **truyền đạt ý tưởng thiết kế giữa các thành viên nhóm phát triển**.

---

## **1️⃣ Use Case Diagram (Biểu đồ ca sử dụng)**

### **Mục đích**

* Mô tả **chức năng của hệ thống** từ góc nhìn người dùng (Actor).
* Giúp hiểu **yêu cầu hệ thống**, xác định các chức năng quan trọng.

### **Thành phần**

1. **Actor (Tác nhân)**: Người dùng hoặc hệ thống ngoài tương tác với hệ thống.

   * Hình: người que stickman.
   * Ví dụ: `User`, `Admin`.
2. **Use Case (Chức năng)**: Hành động hoặc dịch vụ hệ thống cung cấp cho Actor.

   * Hình: hình oval.
   * Ví dụ: `Đăng nhập`, `Tạo báo cáo`.
3. **System (Hệ thống)**: Ranh giới hệ thống, chứa các use case.

   * Hình: hình chữ nhật bao quanh các use case.

### **Quan hệ giữa các Use Case**

* **<<include>>**: Một use case **bắt buộc** thực hiện một use case khác.

  * Ví dụ: `Đăng nhập` <<include>> `Xác thực tài khoản`.
* **<<extend>>**: Một use case **tùy chọn mở rộng** use case khác.

  * Ví dụ: `Đăng nhập` <<extend>> `Gửi cảnh báo khi sai mật khẩu`.
* **Generalization (Kế thừa)**: Use case con **kế thừa** hành vi use case cha.

  * Ví dụ: `Đăng nhập bằng Google` là generalization của `Đăng nhập`.

### **Ví dụ**

```
Actors: User
Use Cases: Đăng nhập, Tạo báo cáo, Xem báo cáo
Quan hệ: 
- Đăng nhập <<include>> Xác thực tài khoản
- Xem báo cáo <<extend>> Tải báo cáo PDF
```

---

## **2️⃣ Class Diagram (Biểu đồ lớp)**

### **Mục đích**

* Mô tả **cấu trúc tĩnh của hệ thống**: các lớp, thuộc tính, phương thức, và mối quan hệ.

### **Thành phần**

1. **Class (Lớp)**:

   * **Tên lớp**: ví dụ `Student`.
   * **Attributes (Thuộc tính)**: ví dụ `Name`, `Age`.
   * **Methods (Phương thức)**: ví dụ `Enroll()`, `GetScore()`.

2. **Quan hệ giữa các lớp**

* **Association (Liên kết)**:

  * Quan hệ bình thường giữa 2 lớp.
  * Ví dụ: `Student` --- `Course` (Sinh viên học Môn học).
* **Aggregation (Tập hợp)** – quan hệ “has-a” **yếu**:

  * Một lớp chứa lớp khác nhưng lớp con vẫn tồn tại độc lập.
  * Ví dụ: `Classroom` chứa nhiều `Student`, nhưng `Student` vẫn tồn tại nếu `Classroom` bị xóa.
* **Composition (Hợp thành)** – quan hệ “has-a” **mạnh**:

  * Lớp con không thể tồn tại nếu lớp cha bị xóa.
  * Ví dụ: `Building` chứa nhiều `Room`, nếu `Building` bị xóa → `Room` cũng bị xóa.
* **Inheritance (Kế thừa)** – quan hệ “is-a”:

  * Lớp con kế thừa thuộc tính/phương thức của lớp cha.
  * Ví dụ: `Cat` kế thừa `Animal`.

3. **Multiplicity (Bản số)**:

* Xác định số lượng đối tượng tham gia quan hệ:

  * `1`, `*`, `0..1`, `1..*`

### **Ví dụ**

```
Class: Student
Attributes: Name, Age
Methods: Enroll(), GetScore()

Class: Course
Attributes: CourseName
Methods: AddStudent()

Association: Student --- Course
Aggregation: Classroom o--- Student
Composition: Building ◼--- Room
Inheritance: Cat --|> Animal
```

---

## **3️⃣ Sequence Diagram (Biểu đồ tuần tự)**

### **Mục đích**

* Mô tả **tương tác giữa các đối tượng theo thứ tự thời gian**.
* Thường dùng để mô tả **một Use Case cụ thể**, ví dụ “Đăng nhập”.

### **Thành phần**

1. **Lifeline (Đường đời đối tượng)**: Dọc thể hiện sự tồn tại của một đối tượng trong quá trình thực hiện.
2. **Actor (Tác nhân)**: Người dùng hoặc hệ thống ngoài tương tác với đối tượng.
3. **Activation bar (Thanh kích hoạt)**: Chỉ ra thời gian đối tượng đang thực hiện một phương thức.
4. **Message (Thông điệp)**: Mũi tên thể hiện lời gọi phương thức hoặc phản hồi giữa các đối tượng.

### **Ví dụ: “Đăng nhập”**

```
Actor: User
Objects: LoginController, AuthService, Database

Flow:
1. User → LoginController: nhập username/password
2. LoginController → AuthService: xác thực thông tin
3. AuthService → Database: truy vấn dữ liệu người dùng
4. Database → AuthService: trả về thông tin
5. AuthService → LoginController: xác thực thành công/ thất bại
6. LoginController → User: thông báo kết quả
```

---

✅ **Tóm tắt**

* **Use Case Diagram** → tập trung **chức năng hệ thống từ người dùng**.
* **Class Diagram** → tập trung **cấu trúc tĩnh các lớp và mối quan hệ**.
* **Sequence Diagram** → tập trung **trình tự tương tác giữa các đối tượng** cho từng Use Case.
