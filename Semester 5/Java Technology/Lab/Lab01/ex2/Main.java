import vn.edu.tdtu.*;

public class Main {
    public static void main (String args[]) {
        int a[] = {1,2,3,4,5};
        int b[] = {7,8,9,10};

        System.out.println("Print 2 arrays");
        ArrayOutput.print(a);
        ArrayOutput.print(b);

        int c[] = ArrayHandler.merge(a,b);

        ArrayHandler.sort(c);

        System.out.println("Print array c");
        ArrayOutput.print(c);

        System.out.println("Write file");
        ArrayOutput.write(c,"array.txt");

    }
}
