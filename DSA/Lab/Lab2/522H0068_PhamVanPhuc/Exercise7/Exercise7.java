import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;
public class Exercise7 {
    public static boolean checkPali(String s) {
        Stack<Character> stack = new Stack<>();
        Queue<Character> queue = new LinkedList<>();
        char ch;
        for (int i = 0 ; i < s.length(); i++) {
            ch = s.charAt(i);
            if ((ch != ' ') && (Character.isLetterOrDigit(ch)))  {
                stack.push(ch);
                queue.offer(ch);
            }
        }
        while (!stack.isEmpty()) {
            char ch1 = stack.pop();
            char ch2 = queue.poll();
            if (ch1 != ch2) {
                return false;
            }
        }
        return true;
    }
    public static void main (String args[]) {
        String s1 = "abc cba";
        String s2 = "gangz ganngz";
        String s3 = "Phuc cuhP";
        System.out.println("Test with 'abccba' : " + checkPali(s1));
        System.out.println("Test with 'gangz gangz : '" + checkPali(s2));
        System.out.println("Test with 'Phuc cuhP : '" + checkPali(s3));
    }
}
