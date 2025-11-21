import java.util.Scanner;

public class Ex1 {

    public static int sum(int[] arr, int n) {
        if (n == 0) {
            return 0;
        }
        else {
            return sum(arr, n - 1) + arr[n - 1];
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n;
        do {
            System.out.println("Enter number ");
            while (!sc.hasNextInt()) {
                System.out.println("Enter again");
                sc.next();
            }
            n = sc.nextInt();
            if (n <= 0) {
                System.out.println("Enter again");
            }

        } while (n <= 0);

        int[] S = new int[n];
        for (int i = 0; i < n; i++) {
            S[i] = i + 1;
        }

        int sum1 = 0;
        for (int i = 0; i < n; i++) {
            sum1 += S[i];
        }

        System.out.println("Sum = " + sum1);

        System.out.println("Sum = " + sum(S, n));

        sc.close();
    }

    
}
