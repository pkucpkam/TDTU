
# **Sắp xếp (Sorting)**

## **1️⃣ Bubble Sort**

* **Nguyên lý:** Lặp nhiều lần qua mảng, so sánh cặp phần tử liên tiếp, đổi chỗ nếu sai thứ tự. Phần tử lớn “nổi” lên cuối sau mỗi lượt.
* **Độ phức tạp:** O(n²)
* **Ổn định:** Có

### Java Code

```java
public class BubbleSort {
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 1, 5};
        bubbleSort(arr);
        for(int num : arr) System.out.print(num + " "); // 1 2 5 5 9
    }
}
```

---

## **2️⃣ Insertion Sort**

* **Nguyên lý:** Chèn từng phần tử vào vị trí đúng trong phần mảng đã sắp xếp.
* **Độ phức tạp:** O(n²)
* **Ổn định:** Có

### Java Code

```java
public class InsertionSort {
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 1, 5};
        insertionSort(arr);
        for (int num : arr) System.out.print(num + " "); // 1 2 5 5 9
    }
}
```

---

## **3️⃣ Selection Sort**

* **Nguyên lý:** Tìm phần tử nhỏ nhất trong phần chưa sắp xếp, đưa nó lên đầu.
* **Độ phức tạp:** O(n²)
* **Ổn định:** Không

### Java Code

```java
public class SelectionSort {
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) minIdx = j;
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 1, 5};
        selectionSort(arr);
        for (int num : arr) System.out.print(num + " "); // 1 2 5 5 9
    }
}
```

---

## **4️⃣ Merge Sort (Chia để trị)**

* **Nguyên lý:** Chia mảng thành 2 nửa, sắp xếp từng nửa, gộp lại.
* **Độ phức tạp:** O(n log n)
* **Ổn định:** Có

### Java Code

```java
public class MergeSort {
    public static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) return;

        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) temp[k++] = arr[i++];
            else temp[k++] = arr[j++];
        }
        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];

        System.arraycopy(temp, 0, arr, left, temp.length);
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 1, 5};
        mergeSort(arr, 0, arr.length - 1);
        for (int num : arr) System.out.print(num + " "); // 1 2 5 5 9
    }
}
```

---

## **5️⃣ Quick Sort (Chia để trị, Pivot)**

* **Nguyên lý:** Chọn pivot, chia mảng thành 2 phần (nhỏ hơn pivot – lớn hơn pivot), đệ quy sắp xếp từng phần.
* **Độ phức tạp:** O(n log n) trung bình, O(n²) worst-case
* **Ổn định:** Không

### Java Code

```java
public class QuickSort {
    public static void quickSort(int[] arr, int left, int right) {
        if (left >= right) return;

        int pivot = arr[right];
        int i = left, j = right - 1;

        while (i <= j) {
            while (i <= j && arr[i] <= pivot) i++;
            while (i <= j && arr[j] >= pivot) j--;
            if (i < j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        arr[right] = arr[i];
        arr[i] = pivot;

        quickSort(arr, left, i - 1);
        quickSort(arr, i + 1, right);
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 1, 5};
        quickSort(arr, 0, arr.length - 1);
        for (int num : arr) System.out.print(num + " "); // 1 2 5 5 9
    }
}
```

---

✅ **Tóm tắt:**

| Thuật toán     | Độ phức tạp | Ổn định | Ghi chú                         |
| -------------- | ----------- | ------- | ------------------------------- |
| Bubble Sort    | O(n²)       | Có      | Dễ hiểu, chậm                   |
| Insertion Sort | O(n²)       | Có      | Tốt với mảng gần như sắp xếp    |
| Selection Sort | O(n²)       | Không   | Ít swap hơn Bubble              |
| Merge Sort     | O(n log n)  | Có      | Ổn định, dùng thêm bộ nhớ       |
| Quick Sort     | O(n log n)  | Không   | Nhanh, nguy cơ worst-case O(n²) |

---
