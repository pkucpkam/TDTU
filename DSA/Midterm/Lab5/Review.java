import java.util.Stack;
public class Review {

    public static boolean isNumber(String s) {
        return s.matches("0|([1-9][1-9]*)");
    }

    public static int compute(String s) {
        Stack<Integer> stack = new Stack<>();
        String[] strArr = s.split(" ");
        for (String string : strArr) {
            if (isNumber(string)) {
                stack.push(Integer.parseInt(string));
            }
            else {
                int num2 = stack.pop();
                int num1 = stack.pop();
                switch (string) {
                    case "+":
                        stack.push(num1 + num2);
                        break;
                    case "-":
                        stack.push(num1 - num2);
                        break;
                    case "*":
                        stack.push(num1*num2);
                        break;
                    case "/":
                        if (num2 == 0) {
                            
                        }
                        else {
                            stack.push(num1/num2);
                        }

                }
            }
        }
        return stack.pop();
    }

    public static int dec2bin(int n) {
        if (n == 0 ) { 
            return 0;
        }
        else {
            return (n%2) + 10*(dec2bin(n/2));
        }
    }

    public static int bin2dec(int n, int k) {
        if ( n ==0 ) {
            return 0;
        }
        else {
            return (n%10)*((int)Math.pow(2,k)) + bin2dec(n/10,k+1);
        }
    }
    public static void main (String args[]) {
        System.out.println(dec2bin(8));
        System.out.println(bin2dec(11111, 0));
    }
}
