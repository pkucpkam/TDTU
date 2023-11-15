public class recurSumEven {
    public static int sumEvenDigit(int a) {
    if (a == 0) {
        return 0;
    }
    else if ((a%10) % 2 == 0) {
        return (a%10) + sumEvenDigit(a/10);
    }
    else {
        return sumEvenDigit(a/10);
        }
    }  
    public static void main(String args[]) {
        System.out.println(sumEvenDigit(12345));
    }
}