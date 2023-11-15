import java.util.Stack;

public class Exercise3 {
    public static int compute(int n) {
        if (n == 1) {
            return 3;
        }
        else if (n == 0) {
            return 0;
        }
        else {
            return (int)Math.pow(2, n) + (int)Math.pow(n, 2) + compute(n - 2);
        }
    }

    public static int computeByStack(int n) {
        Stack<Integer> stack = new Stack<>();
        //add to stack
        while (n > 1) {
            stack.push((int)Math.pow(2, n) + (int)Math.pow(n, 2));
            n-= 2;
        }
        if (n == 1) {
            stack.push(3);
        }
        //compute
        int result = 0;
        while (!stack.empty()) {
            result = result + stack.pop();
        }
        return result;
    }
    public static void main(String args[]) {
        System.out.println("using recursion");
        System.out.println("Test with n = 5, result is : " + compute(5));
        System.out.println("Test with n = 5, result is : " + compute(10));
        System.out.println("Test with n = 5, result is : " + compute(15));

        System.out.println("using stack");
        System.out.println("Test with n = 5, result is : " + computeByStack(5));
        System.out.println("Test with n = 5, result is : " + computeByStack(10));
        System.out.println("Test with n = 5, result is : " + computeByStack(15));
    }
}