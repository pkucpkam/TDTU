
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

---

# 🟦 **PHẦN A — CƠ BẢN (15 câu)**

### **1. Git là gì?**

A. Hệ thống quản lý cơ sở dữ liệu
B. Hệ thống quản lý mã nguồn phân tán
C. Công cụ CI/CD
D. Trình biên dịch

### **2. Lệnh `git init` dùng để làm gì?**

A. Tải repo từ remote
B. Tạo repository mới
C. Xóa repository
D. Xem log

### **3. Để kiểm tra trạng thái file, ta dùng:**

A. `git status`
B. `git log`
C. `git diff`
D. `git remote`

### **4. Lệnh đưa file vào staging area:**

A. `git push`
B. `git add`
C. `git clone`
D. `git branch`

### **5. Lệnh tạo commit:**

A. `git commit -m "msg"`
B. `git save`
C. `git write`
D. `git history`

### **6. Lệnh xem lịch sử commit:**

A. `git show`
B. `git log`
C. `git version`
D. `git diff`

### **7. Lệnh kết nối repo local với repo remote:**

A. `git remote add origin <url>`
B. `git connect <url>`
C. `git push origin`
D. `git link`

### **8. Lệnh đẩy commit lên remote:**

A. `git upload`
B. `git push`
C. `git send`
D. `git log -p`

### **9. Clone repository dùng lệnh:**

A. `git get`
B. `git clone <url>`
C. `git copy`
D. `git fetch`

### **10. File nào Git tự bỏ qua?**

A. `.env`
B. `.gitignore`
C. `.git`
D. `README.md`

### **11. File `.gitignore` dùng để:**

A. Lưu các commit
B. Bỏ qua file không muốn track
C. Tự động đẩy lên remote
D. Ghi log lỗi Git

### **12. Lệnh chuyển branch:**

A. `git checkout <branch>`
B. `git swap`
C. `git change`
D. `git move`

### **13. Lệnh tạo branch:**

A. `git new branch`
B. `git create branch`
C. `git branch <name>`
D. `git make branch`

### **14. Lệnh xem danh sách branch:**

A. `git branch`
B. `git ls branch`
C. `git branch -a`
D. Cả A và C

### **15. Để kéo code mới nhất từ remote:**

A. `git download`
B. `git pull`
C. `git fetch`
D. `git sync`

---

# 🟦 **PHẦN B — TRUNG CẤP (20 câu)**

### **16. `git pull` thực chất = ?**

A. fetch + merge
B. clone + merge
C. commit + push
D. status + add

### **17. Lệnh xem sự khác nhau giữa file hiện tại và staging:**

A. `git diff --cached`
B. `git compare`
C. `git diff origin`
D. `git diff all`

### **18. Muốn xóa file khỏi staging nhưng không xóa file thật:**

A. `git delete`
B. `git restore --staged <file>`
C. `git remove <file>`
D. `git stash`

### **19. Lệnh hoàn tác commit cuối cùng nhưng giữ lại code:**

A. `git reset --hard HEAD~1`
B. `git revert HEAD`
C. `git reset --soft HEAD~1`
D. `git checkout HEAD`

### **20. `git reset --hard` làm gì?**

A. Xóa toàn bộ repo
B. Về commit cũ và mất hết thay đổi
C. Merge 2 nhánh
D. Chỉ reset staging

### **21. Lệnh tạo tag:**

A. `git new tag`
B. `git tag <name>`
C. `git branch tag`
D. `git push tag`

### **22. Lệnh xem remote repository:**

A. `git show remote`
B. `git branch -r`
C. `git remote -v`
D. `git remote list`

### **23. Muốn merge branch B vào A:**

A. checkout A → merge B
B. checkout B → merge A
C. push A → pull B
D. pull A → push B

### **24. Merge xảy ra conflict khi:**

A. 2 branch xóa cùng 1 file
B. 2 branch thay đổi cùng 1 dòng
C. 1 branch đổi tên file
D. Không bao giờ xảy ra

### **25. Giải quyết merge conflict bằng cách:**

A. Xóa project, clone lại
B. Chỉnh code trong file conflict
C. Reset toàn bộ
D. Push mạnh lên overriding code cũ

### **26. `git stash` dùng để:**

A. Lưu tạm thay đổi chưa commit
B. Xóa branch
C. Push code
D. Tạo commit tự động

### **27. Lệnh lấy lại code đã stash:**

A. `git stash show`
B. `git stash pop`
C. `git stash reset`
D. `git stash merge`

### **28. Rebase là gì?**

A. Tạo repo mới
B. Di chuyển commit của branch lên trên commit mới nhất của branch khác
C. Xóa commit
D. Tổng hợp commit

### **29. Lệnh rebase:**

A. `git rebase <branch>`
B. `git rebranch <branch>`
C. `git apply rebase`
D. `git merge rebase`

### **30. Muốn sửa lại message của commit cuối cùng:**

A. `git commit --edit`
B. `git commit --amend`
C. `git fix commit`
D. `git log --edit`

### **31. Lệnh xoá branch local:**

A. `git branch -d <name>`
B. `git delete <branch>`
C. `git reset --hard`
D. `git rm branch`

### **32. Lệnh xoá branch trên remote:**

A. `git push origin --delete <branch>`
B. `git delete remote`
C. `git branch -r -d`
D. `git rm --remote`

### **33. Khi clone repo, Git tạo thư mục nào?**

A. `.gitignore`
B. `.git/`
C. `.remote`
D. `.log`

### **34. HEAD trong Git là gì?**

A. File chứa mật khẩu
B. Con trỏ trỏ tới commit hiện tại
C. Tên remote
D. Nhánh chính

### **35. Để xem file nào thay đổi trước khi commit:**

A. `git logs`
B. `git status`
C. `git diff`
D. Cả B và C

---

# 🟦 **PHẦN C — NÂNG CAO (15 câu)**

### **36. Tính năng nào giúp “gom nhiều commit thành một”?**

A. Merge
B. Rebase interactive (`rebase -i`)
C. Stash
D. Branching

### **37. Lệnh để interactive rebase 5 commit gần nhất:**

A. `git rebase -i HEAD~5`
B. `git rebase 5`
C. `git commit -i 5`
D. `git squash 5`

### **38. Fast-forward merge xảy ra khi:**

A. 2 nhánh bị conflict
B. Nhánh cần merge không có commit phân nhánh
C. Remote bị lỗi
D. Nhánh tự động reset

### **39. Cherry-pick dùng để:**

A. Copy 1 commit sang branch khác
B. Xóa commit
C. Tạo nhánh
D. Gộp commit

### **40. Lệnh cherry-pick:**

A. `git pick <commit>`
B. `git cherry <branch>`
C. `git cherry-pick <commit>`
D. `git pick-commit <hash>`

### **41. Lệnh sửa tên branch local:**

A. `git branch -m <newname>`
B. `git rename <branch>`
C. `git rename branch`
D. `git mv branch`

### **42. Git hook là gì?**

A. Cách kéo code nhanh
B. Script chạy tự động khi có event (commit, push…)
C. File quản lý branch
D. Một dạng stash

### **43. Lệnh xem hash commit ngắn:**

A. `git short-hash`
B. `git log --oneline`
C. `git log -short`
D. `git hash list`

### **44. Lệnh xem ai sửa dòng code nào:**

A. `git blame <file>`
B. `git history <file>`
C. `git who <file>`
D. `git log -p`

### **45. Để reset 1 file về commit gần nhất:**

A. `git restore <file>`
B. `git revert`
C. `git reset file`
D. `git undo`

### **46. Submodule dùng để làm gì?**

A. Gộp 2 repo thành 1
B. Nhúng repo con trong repo cha
C. Chia nhỏ repo
D. Tạo tag

### **47. Lệnh cập nhật submodule:**

A. `git submodule update --init --recursive`
B. `git update submodule`
C. `git sync module`
D. `git merge module`

### **48. Nếu muốn commit code nhưng không muốn ai thấy nội dung file:**

A. Git không hỗ trợ
B. Dùng “[Git LFS](https://git-lfs.com/)”
C. Dùng “git lock”
D. Dùng “git ignore”

### **49. Reflog dùng để:**

A. Xem lịch sử HEAD
B. Lưu lại stash
C. Sửa log commit
D. Xóa merge conflict

### **50. Có thể phục hồi commit đã `reset --hard` bằng:**

A. Không thể
B. `git reflog`
C. `git stash pop`
D. `git pull`

---

# 🟩 **ĐÁP ÁN**

Nếu bạn muốn đáp án được ẩn → mình có thể chuyển qua dạng Google Form trắc nghiệm.

### **Phần A:**

1B, 2B, 3A, 4B, 5A, 6B, 7A, 8B, 9B, 10C, 11B, 12A, 13C, 14D, 15B

### **Phần B:**

16A, 17A, 18B, 19C, 20B, 21B, 22C, 23A, 24B, 25B, 26A, 27B, 28B, 29A, 30B, 31A, 32A, 33B, 34B, 35D

### **Phần C:**

36B, 37A, 38B, 39A, 40C, 41A, 42B, 43B, 44A, 45A, 46B, 47A, 48B, 49A, 50B

---
