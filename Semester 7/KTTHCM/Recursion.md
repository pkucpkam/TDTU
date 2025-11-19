# **Đệ quy (Recursion)**

* **Khái niệm:**
  Hàm tự gọi chính nó để giải quyết bài toán, gồm:

  1. **Điểm dừng (base case):** điều kiện để hàm ngừng gọi.
  2. **Bước đệ quy (recursive step):** gọi hàm với phiên bản nhỏ hơn.

* **Ưu/Nhược điểm:**

  * **Ưu:** Code ngắn gọn, logic rõ ràng.
  * **Nhược:** Dễ gây **Stack Overflow** nếu không có base case; tốn bộ nhớ khi đệ quy sâu.

* **Ứng dụng / Luyện tập:**

  1. Giai thừa
  2. Fibonacci
  3. Tháp Hà Nội
  4. Tìm kiếm nhị phân

---

## 1️⃣ Giai thừa (Factorial)

```java
public class Factorial {
    public static int factorial(int n) {
        if (n == 0 || n == 1) return 1; // base case
        return n * factorial(n - 1);    // recursive step
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.println(n + "! = " + factorial(n)); // 5! = 120
    }
}
```

---

## 2️⃣ Dãy Fibonacci

```java
public class Fibonacci {
    public static int fibonacci(int n) {
        if (n == 0) return 0;    // base case
        if (n == 1) return 1;    // base case
        return fibonacci(n - 1) + fibonacci(n - 2); // recursive step
    }

    public static void main(String[] args) {
        int n = 10;
        for (int i = 0; i < n; i++) {
            System.out.print(fibonacci(i) + " ");
        }
        // Output: 0 1 1 2 3 5 8 13 21 34
    }
}
```

> ⚠️ Lưu ý: Cách này **chậm** cho `n` lớn vì tính toán nhiều lần. Có thể tối ưu bằng memoization hoặc dùng vòng lặp.

---

## 3️⃣ Tháp Hà Nội (Tower of Hanoi)

```java
public class TowerOfHanoi {
    public static void solve(int n, char from, char to, char aux) {
        if (n == 1) {
            System.out.println("Move disk 1 from " + from + " to " + to);
            return;
        }
        solve(n - 1, from, aux, to); // di chuyển n-1 đĩa sang cọc phụ
        System.out.println("Move disk " + n + " from " + from + " to " + to);
        solve(n - 1, aux, to, from); // di chuyển n-1 đĩa từ cọc phụ sang cọc đích
    }

    public static void main(String[] args) {
        int n = 3;
        solve(n, 'A', 'C', 'B');
    }
}
```

**Output ví dụ (n=3):**

```
Move disk 1 from A to C
Move disk 2 from A to B
Move disk 1 from C to B
Move disk 3 from A to C
Move disk 1 from B to A
Move disk 2 from B to C
Move disk 1 from A to C
```

---

## 4️⃣ Tìm kiếm nhị phân (Binary Search, đệ quy)

```java
public class BinarySearch {
    public static int binarySearch(int[] arr, int target, int left, int right) {
        if (left > right) return -1; // base case: không tìm thấy

        int mid = left + (right - left) / 2;
        if (arr[mid] == target) return mid;
        if (arr[mid] > target) return binarySearch(arr, target, left, mid - 1);
        else return binarySearch(arr, target, mid + 1, right);
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 11};
        int target = 7;
        int index = binarySearch(arr, target, 0, arr.length - 1);
        System.out.println("Index of " + target + " is " + index); // 3
    }
}
```

---
