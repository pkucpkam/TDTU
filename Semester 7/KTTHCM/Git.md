
# **Git – Tổng quan**

---

## **1️⃣ Khái niệm cốt lõi**

* **Repository (Repo):**

  * **Local repo:** repo trên máy của bạn.
  * **Remote repo:** repo trên server, ví dụ GitHub, GitLab.

* **Luồng làm việc cơ bản:**

  ```
  Working Directory -> Staging Area (Index) -> Local Repo
  ```

* **Commit:** “Ảnh chụp” của dự án tại một thời điểm, lưu lịch sử thay đổi.

* **Branch:** Nhánh phát triển độc lập.

  * `main` / `master` là nhánh chính.

* **HEAD:** Con trỏ trỏ đến commit hiện tại.

---

## **2️⃣ Các lệnh cơ bản**

### **Thiết lập:**

* `git config` → cấu hình tên, email, editor…
* `git init` → khởi tạo repo mới.
* `git clone <url>` → sao chép repo từ remote.

### **Làm việc cơ bản:**

* `git add <file>` → thêm thay đổi vào staging.
* `git commit -m "message"` → ghi nhận các thay đổi.
* `git status` → kiểm tra trạng thái working directory và staging.
* `git log` → xem lịch sử commit.

### **Làm việc với Remote:**

* `git push` → đẩy commit lên remote.
* `git pull` → kéo commit từ remote và merge.
* `git fetch` → chỉ kéo về, chưa merge.

---

## **3️⃣ Phân nhánh (Branching)**

* `git branch` → tạo / liệt kê / xóa nhánh.
* `git checkout <branch>` → chuyển nhánh.
* `git merge <branch>` → gộp nhánh vào nhánh hiện tại.

  * **Fast-forward merge:** không có commit mới giữa 2 nhánh → chỉ di chuyển HEAD.
  * **3-way merge:** 2 nhánh có commit khác nhau → tạo commit merge.

---

## **4️⃣ Xử lý vấn đề**

* **Merge Conflict:**

  * Xảy ra khi 2 nhánh sửa cùng 1 chỗ trong file.
  * Giải quyết bằng cách chỉnh sửa file, `git add`, rồi `git commit`.

* **git reset:**

  * `--soft`: giữ thay đổi trong staging.
  * `--mixed` (mặc định): giữ thay đổi trong working directory, xóa khỏi staging.
  * `--hard`: xóa tất cả thay đổi chưa commit.

* **git stash:**

  * Tạm cất các thay đổi chưa commit để chuyển nhánh hoặc làm việc khác.

* **.gitignore:**

  * File liệt kê các file/folder không muốn đưa vào repo.

---