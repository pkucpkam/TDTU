import java.util.Stack;

public class revNumber {
    public static int revNum(int n) {
        String s = String.valueOf(n);
        String s2 = "";
        Stack<Character> stack = new Stack<>();
        char chArr[] = s.toCharArray();
        for (char ch : chArr) {
            stack.push(ch);
        }
        while (!stack.isEmpty()) {
            s2 += stack.pop() ;
        }
        return Integer.parseInt(s2);
    }

    public static void main(String args[]) {
        System.out.println(revNum(1232456));
    }   
}
