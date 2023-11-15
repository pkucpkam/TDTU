import java.util.Arrays;
import java.util.Comparator;
public class Sorting {

    public static void sortingAcs(Student[] arrStudents) {
        Arrays.sort(arrStudents, new Comparator<Student>() {
            @Override
            public int compare(Student student1, Student student2) {
                if (student1.aveGrade() < student2.aveGrade()) {
                    return -1;
                } else if (student1.aveGrade() > student2.aveGrade()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

    public static void sortingDesc(Student[] arrStudents) {
        Arrays.sort(arrStudents, new Comparator<Student>() {
            @Override
            public int compare(Student student1, Student student2) {
                if (student1.aveGrade() > student2.aveGrade()) {
                    return -1;
                } else if (student1.aveGrade() < student2.aveGrade()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

    public static void printArr(Student[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        System.out.println();
    }
    public static void main (String args[]) { 
        //create array student
        Student arrStudent[] = new Student[5];
        arrStudent[0] = new Student("Phuc", 10,9.6,10);
        arrStudent[1] = new Student("Nam", 6,8.6,7);
        arrStudent[2] = new Student("Long", 4,8.8,4.5);
        arrStudent[3] = new Student("Nhan", 6,7.6,7.9);
        arrStudent[4] = new Student("Hao", 7,5.6,8.5);
        System.out.println("Initial array");;
        printArr(arrStudent);
        System.out.println("Sorting ascending");
        sortingAcs(arrStudent);
        printArr(arrStudent);
        System.out.println("Sorting descending");
        sortingDesc(arrStudent);
        printArr(arrStudent);
        
    }
}
