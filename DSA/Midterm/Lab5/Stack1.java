import java.util.Stack;

public class Stack1 {

    public static int compute(String s) {
        Stack<String> stack = new Stack<>();
        String[] str = s.split(" ");
        for (int i = 0; i < str.length; i++) {
            if (isNumber(str[i])) {
                stack.push(str[i]);
            }
            else {
                if (stack.peek() != null) {
                     int num1 = Integer.parseInt(stack.pop());
                int num2 = Integer.parseInt(stack.pop());
                switch (str[i]) {
                    case "+":
                        stack.push(String.valueOf(num1 + num2));
                        break;
                    case "-":
                        stack.push(String.valueOf(num2 - num1));
                        break;
                    case "*":
                        stack.push(String.valueOf(num1 * num2));
                        break;
                }
                }
            }
        }
        return Integer.parseInt(stack.pop());
    }

    public static boolean isNumber(String str) {
        return str.matches("0| ([1-9] [ 0 - 9]*)");
    }

    public static void main(String args[]) {
    System.out.println(compute("5 6 +"));
    }
}
