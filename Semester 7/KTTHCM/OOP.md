
# **Lập trình hướng đối tượng (OOP)**

OOP là **phương pháp lập trình dựa trên đối tượng**, giúp mô tả hệ thống như các **thực thể thực tế**, mỗi đối tượng **có trạng thái và hành vi**.

Trong UML, **lớp (Class)** trong Class Diagram phản ánh trực tiếp các nguyên tắc OOP.

---

## **1️⃣ Tính đóng gói (Encapsulation)**

### **Khái niệm**

* Che giấu dữ liệu bên trong đối tượng (**private attributes**).
* Cung cấp **phương thức công khai (public methods)** để thao tác dữ liệu.
* Mục đích: **bảo vệ dữ liệu, giảm sự phụ thuộc bên ngoài**.

### **Ví dụ Java**

```java
public class Student {
    private String name; // dữ liệu private
    private int age;

    // Constructor
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getter và Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { 
        if(age > 0) this.age = age; // Kiểm tra giá trị hợp lệ
    }
}
```

**Liên hệ UML:**

* Thuộc tính `name` và `age` → private (-)
* Phương thức `getName()`, `setName()` → public (+)

---

## **2️⃣ Tính kế thừa (Inheritance)**

### **Khái niệm**

* Lớp con (**subclass**) thừa hưởng **thuộc tính và phương thức của lớp cha (superclass)**.
* Giúp **tái sử dụng mã nguồn và mở rộng chức năng**.

### **Ví dụ Java**

```java
// Lớp cha
public class Animal {
    public void eat() { System.out.println("Animal ăn"); }
}

// Lớp con
public class Cat extends Animal {
    public void meow() { System.out.println("Mèo kêu meow"); }
}

public class Main {
    public static void main(String[] args) {
        Cat cat = new Cat();
        cat.eat(); // kế thừa từ Animal
        cat.meow(); // phương thức riêng
    }
}
```

**Liên hệ UML:**

* Ký hiệu kế thừa: `Cat --|> Animal`

---

## **3️⃣ Tính đa hình (Polymorphism)**

### **Khái niệm**

* Khả năng **nhiều hình thái** của cùng một phương thức hoặc đối tượng.
* Có 2 loại chính:

  1. **Overloading (Nạp chồng)**: cùng tên phương thức, khác tham số.
  2. **Overriding (Ghi đè)**: lớp con **thay đổi hành vi** của phương thức lớp cha.

### **Ví dụ Overloading**

```java
public class Calculator {
    public int add(int a, int b) { return a + b; }
    public double add(double a, double b) { return a + b; } // cùng tên, khác kiểu
}
```

### **Ví dụ Overriding**

```java
public class Animal {
    public void sound() { System.out.println("Animal phát ra âm thanh"); }
}

public class Dog extends Animal {
    @Override
    public void sound() { System.out.println("Dog sủa"); }
}

public class Main {
    public static void main(String[] args) {
        Animal a = new Dog();
        a.sound(); // Dog sủa → đa hình động
    }
}
```

**Liên hệ UML:**

* Phương thức ghi đè → lớp con kế thừa, có thể đổi hành vi.
* Dùng mũi tên kế thừa và chú thích `{override}`.

---

## **4️⃣ Tính trừu tượng (Abstraction)**

### **Khái niệm**

* Tách **giao diện (what)** khỏi **cài đặt (how)**.
* Chỉ định **những gì lớp phải làm**, không chỉ cách làm.
* Thường dùng: **abstract class** hoặc **interface**.

### **Ví dụ abstract class**

```java
abstract class Shape {
    abstract double area(); // phương thức trừu tượng
}

class Circle extends Shape {
    private double radius;
    Circle(double radius) { this.radius = radius; }

    @Override
    double area() { return Math.PI * radius * radius; }
}
```

### **Ví dụ interface**

```java
interface Flyable {
    void fly();
}

class Bird implements Flyable {
    @Override
    public void fly() { System.out.println("Chim bay"); }
}
```

**Liên hệ UML:**

* Abstract class → tên in nghiêng, phương thức trừu tượng in nghiêng.
* Interface → chữ `<<interface>>` và phương thức trừu tượng.

---

## **5️⃣ Tổng hợp OOP và UML**

| Tính chất     | Java ví dụ                 | UML representation                          |           |
| ------------- | -------------------------- | ------------------------------------------- | --------- |
| Encapsulation | private + getter/setter    | - thuộc tính private, + phương thức public  |           |
| Inheritance   | `extends`                  | Lớp con --                                  | > Lớp cha |
| Polymorphism  | Overloading/Overriding     | Ghi đè `{override}`, tên phương thức trùng  |           |
| Abstraction   | abstract class / interface | Lớp trừu tượng in nghiêng / `<<interface>>` |           |

---

✅ **Tóm lại:**

* **Encapsulation:** bảo vệ dữ liệu.
* **Inheritance:** tái sử dụng và mở rộng lớp.
* **Polymorphism:** cùng phương thức, nhiều hành vi.
* **Abstraction:** tập trung vào “làm gì”, không phải “làm thế nào”.
