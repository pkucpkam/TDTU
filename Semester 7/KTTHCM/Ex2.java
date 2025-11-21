import java.util.Scanner;

public class Ex2 {
    public static void main (String args[]) {
        Scanner sc = new Scanner(System.in);
        int n;
        do {
            System.out.println("Moi nhap so phan tu cua mang");
            while (!sc.hasNextInt()) {
                System.out.println("Vui long nhap lai");
                sc.next();
            }
            n = sc.nextInt();
            if (n <= 0) {
                System.out.println("Vui lòng nhập số nguyên dương!");
            }
        } while (n <= 0);
        sc.close();
    }
}
