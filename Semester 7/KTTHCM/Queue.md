
# **Queue (Hàng đợi)**

### 1️⃣ Lý thuyết cơ bản

* **Nguyên lý:** FIFO (First-In, First-Out) → phần tử được thêm vào trước sẽ được lấy ra trước.
* **Các phép toán cơ bản:**

  * `enqueue` → thêm phần tử vào cuối queue.
  * `dequeue` → lấy phần tử ở đầu queue.
  * `peek` → xem phần tử ở đầu queue mà không lấy ra.
  * `isEmpty` → kiểm tra queue có rỗng hay không.
* **Ứng dụng:**

  * Duyệt đồ thị theo chiều rộng (BFS).
  * Hàng đợi xử lý tác vụ (task queue).
  * Hệ thống in ấn (print queue).
  * Mô phỏng các hệ thống thực tế như ngân hàng, bến xe.

---

# **2️⃣ Queue sử dụng Array (Mảng cố định)**

```java
class QueueArray {
    private int[] arr;
    private int front, rear, size, capacity;

    public QueueArray(int capacity) {
        this.capacity = capacity;
        arr = new int[capacity];
        front = 0;
        size = 0;
        rear = -1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public void enqueue(int value) {
        if (isFull()) {
            System.out.println("Queue Overflow!");
            return;
        }
        rear = (rear + 1) % capacity;
        arr[rear] = value;
        size++;
    }

    public int dequeue() {
        if (isEmpty()) {
            System.out.println("Queue Underflow!");
            return -1;
        }
        int value = arr[front];
        front = (front + 1) % capacity;
        size--;
        return value;
    }

    public int peek() {
        if (isEmpty()) {
            System.out.println("Queue rỗng!");
            return -1;
        }
        return arr[front];
    }

    public static void main(String[] args) {
        QueueArray q = new QueueArray(5);
        q.enqueue(10);
        q.enqueue(20);
        q.enqueue(30);
        System.out.println(q.dequeue()); // 10
        System.out.println(q.peek());    // 20
    }
}
```

---

# **3️⃣ Queue sử dụng Linked List**

```java
class QueueLinkedList {
    private Node front, rear;

    private static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    public QueueLinkedList() {
        front = rear = null;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public void enqueue(int value) {
        Node node = new Node(value);
        if (rear != null) rear.next = node;
        rear = node;
        if (front == null) front = rear;
    }

    public int dequeue() {
        if (isEmpty()) {
            System.out.println("Queue Underflow!");
            return -1;
        }
        int value = front.data;
        front = front.next;
        if (front == null) rear = null;
        return value;
    }

    public int peek() {
        if (isEmpty()) {
            System.out.println("Queue rỗng!");
            return -1;
        }
        return front.data;
    }

    public static void main(String[] args) {
        QueueLinkedList q = new QueueLinkedList();
        q.enqueue(10);
        q.enqueue(20);
        q.enqueue(30);
        System.out.println(q.dequeue()); // 10
        System.out.println(q.peek());    // 20
    }
}
```

---

# **4️⃣ Duyệt đồ thị theo chiều rộng (BFS) với Queue**

```java
import java.util.*;

public class BFSExample {
    public static void BFS(int start, List<List<Integer>> adj) {
        boolean[] visited = new boolean[adj.size()];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            System.out.print(node + " ");

            for (int neighbor : adj.get(node)) {
                if (!visited[neighbor]) {
                    queue.add(neighbor);
                    visited[neighbor] = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        int n = 5; // số đỉnh
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        // Tạo đồ thị
        adj.get(0).add(1);
        adj.get(0).add(2);
        adj.get(1).add(3);
        adj.get(2).add(3);
        adj.get(3).add(4);

        System.out.print("BFS từ đỉnh 0: ");
        BFS(0, adj); // 0 1 2 3 4
    }
}
```

---

# **5️⃣ Queue để mô phỏng Undo/Redo hoặc Task Queue**

```java
import java.util.*;

public class TaskQueue {
    public static void main(String[] args) {
        Queue<String> tasks = new LinkedList<>();

        // Thêm công việc vào hàng đợi
        tasks.add("Task1");
        tasks.add("Task2");
        tasks.add("Task3");

        System.out.println("Xử lý công việc:");
        while (!tasks.isEmpty()) {
            System.out.println("Processing " + tasks.poll());
        }
    }
}
```

---
