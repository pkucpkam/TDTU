
# **Stack (Ngăn xếp)**

* **Nguyên lý:** LIFO (**Last-In, First-Out**) → phần tử được thêm vào cuối cùng sẽ được lấy ra trước.

* **Các phép toán cơ bản:**

  1. `push` → thêm phần tử vào đỉnh stack.
  2. `pop` → lấy phần tử từ đỉnh stack.
  3. `peek` → xem phần tử trên cùng mà không xóa.
  4. `isEmpty` → kiểm tra stack có rỗng hay không.

* **Ứng dụng:**

  * Kiểm tra dấu ngoặc hợp lệ trong biểu thức.
  * Khử đệ quy (ví dụ: đảo ngược chuỗi).
  * Chức năng “Undo” trong phần mềm.
  * Tính toán biểu thức hậu tố (postfix).

---

## 1️⃣ Kiểm tra dấu ngoặc hợp lệ

```java
import java.util.Stack;

public class BracketChecker {
    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if ((c == ')' && top != '(') ||
                    (c == '}' && top != '{') ||
                    (c == ']' && top != '[')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String expr = "{[()]}";
        System.out.println(isValid(expr)); // true
    }
}
```

---

## 2️⃣ Đảo ngược chuỗi (khử đệ quy)

```java
import java.util.Stack;

public class ReverseString {
    public static String reverse(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) stack.push(c);

        StringBuilder reversed = new StringBuilder();
        while (!stack.isEmpty()) reversed.append(stack.pop());

        return reversed.toString();
    }

    public static void main(String[] args) {
        String str = "Hello World";
        System.out.println(reverse(str)); // "dlroW olleH"
    }
}
```

---

## 3️⃣ Chức năng "Undo"

```java
import java.util.Stack;

public class UndoExample {
    public static void main(String[] args) {
        Stack<String> history = new Stack<>();

        String text = "";
        // Gõ chữ
        text += "Hello";
        history.push(text);

        text += " World";
        history.push(text);

        text += " !!!";
        history.push(text);

        System.out.println("Current text: " + text);

        // Undo thao tác
        history.pop(); // xóa thao tác hiện tại
        text = history.peek(); // trở lại trạng thái trước
        System.out.println("After undo: " + text);
    }
}
```

---

## 4️⃣ Tính toán biểu thức hậu tố (Postfix)

```java
import java.util.Stack;

public class PostfixEvaluation {
    public static int evaluate(String expr) {
        Stack<Integer> stack = new Stack<>();
        String[] tokens = expr.split("\\s+");

        for (String token : tokens) {
            if (token.matches("-?\\d+")) { // nếu là số
                stack.push(Integer.parseInt(token));
            } else { // nếu là toán tử
                int b = stack.pop();
                int a = stack.pop();
                switch (token) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "*": stack.push(a * b); break;
                    case "/": stack.push(a / b); break;
                }
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        String expr = "5 1 2 + 4 * + 3 -"; // tương đương 5 + ((1+2)*4) - 3
        System.out.println(evaluate(expr)); // 14
    }
}
```

---
