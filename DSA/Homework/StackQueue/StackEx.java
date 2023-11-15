import java.util.*;

public class StackEx {
    // reverse a string using Stack
    // public static String reverseString(String s) {
    //     Stack<Character> stack = new Stack<>();
    //     for (int i = 0; i < s.length(); i++) {
    //         stack.push(s.charAt(i));
    //     }
    //     String rs = "";
    //     while (! stack.empty()) {
    //         rs += stack.pop();
    //     }
    //     return rs;
    // }

    //hau to 
    public static int evaluate(String exp) {
        Stack<Integer> stack = new Stack<>();
        char ch;
        int num,num1,num2,result = 0;
        for (int i = 0; i < exp.length() ; i++) {
            ch = exp.charAt(i);
            if (Character.isDigit(ch)) {
                num = Character.getNumericValue(ch);
                stack.push(num);
            }
            else if (ch == ' '){
                continue;
            }
            else {
                num1 = stack.pop();
                num2 = stack.pop();
                switch (ch) {
                    case '+' : 
                        result = num1 + num2;
                        break;
                    case '-' : 
                        result = num1 - num2;
                        break;
                    case '*' : 
                        result = num1*num2;
                        break;
                    case '/' : 
                        if (num2 == 0) {
                            throw new ArithmeticException("Division by 0");
                        }
                        result = num1/num2;
                        break;
                    case '%' : 
                        result = num1 % num2;
                    default : 
                        throw new IllegalAccessError("wrong operator");
                }
                stack.push(result);
            }
        }
        if (stack.size() != 1) {
            throw new IllegalAccessError("wrong expression");
        }
        return stack.pop();
    }
    public static void main(String[] args) {
        System.out.println(evaluate("4 5 + 5 *"));
    }
}