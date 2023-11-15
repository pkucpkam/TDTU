import java.util.Stack;

public class Exercise5 {
    public static boolean checkDelimiters(String s) {
        Stack<Character> stack = new Stack<>();
        char ch;
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            }
            else if (ch == ')' || ch == '}' || ch == ']') {
                if ((ch == ')' && stack.peek() == '(') || (ch == '}' && stack.peek() == '{') || (ch == ']' && stack.peek() == '[')) {
                    stack.pop(); 
                }
                else {
                    return false;
                }               
            }
        }
        return true;
    }
    public static void main(String args[]) {
        String s1 = "({[{()}]})";
        String s2 = "(Phuc)";
        String s3 = "(({{[]}))";
        System.out.println("Test");
        System.out.println(checkDelimiters(s1));
        System.out.println(checkDelimiters(s2));
        System.out.println(checkDelimiters(s3));
    }
}
