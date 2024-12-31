public class Student {
   
    public String name;
    public int age;
    public double math;
    public double physic;
    public double english;

    
    public Student(String name, int age, double math, double physic, double english) {
        this.name = name;
        this.age = age;
        this.math = math;
        this.physic = physic;
        this.english = english;
    }

    public double average()
    {
        double v = (math + physic + english) / 3.0;
        return (int)(v * 100) / 100.0;
    }

    public boolean isGoodStudent() {
        return average() > 8.0 && math >= 7 && physic >= 7 && english >= 7;
    }

    @Override
    public String toString() {
        return "Student (name=" + name + ", age=" + age + ", en=" + english + ", math=" + math + ", physic="
                + physic + ", avg= " + average() + ")";
    }

    
}
