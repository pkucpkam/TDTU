public class Student {
    private String name;
    private double mathematics;
    private double programming;
    private double DSA1;

    public Student() {
        this.name = "";
        this.mathematics = 0;
        this.programming = 0;
        this.DSA1 = 0;
    }

    public Student(String name, double mathematics, double programming, double DSA1) {
        this.name = name;
        this.mathematics = mathematics;
        this.programming = programming;
        this.DSA1 = DSA1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMathematics() {
        return mathematics;
    }

    public void setMathematics(double mathematics) {
        this.mathematics = mathematics;
    }

    public double getProgramming() {
        return programming;
    }

    public void setProgramming(double programming) {
        this.programming = programming;
    }

    public double getDSA1() {
        return DSA1;
    }

    public void setDSA1(double dSA1) {
        DSA1 = dSA1;
    }

    public double aveGrade() {
        return (mathematics + programming + DSA1) / 3;
    }

    public String toString() {
        return name +" : " + aveGrade();
    }
}
