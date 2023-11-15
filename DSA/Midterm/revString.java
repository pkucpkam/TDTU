import java.util.Stack;
public class revString {

    public static String rev1String(String s) {
        String s2 = "";
        Stack<Character> stack = new Stack<>();
        char chArr[] = s.toCharArray();
        for (char ch : chArr) {
            stack.push(ch);
        }
        while (!stack.isEmpty()) {
            s2 += stack.pop() ;
        }
        return s2;
    }

    public static String revString2(String s) {
        String s2 = "";
        String strArr[] = s.split(" ");
        for (String string : strArr) {
            s2 += rev1String(string) + " ";
        }
        return s2;
    }

    public static String rev3(String s) {
        String s2 = "";
        String[] strArr = s.split(" ");
        Stack<String> stack = new Stack<>();
        for (String string : strArr) {
            stack.push(string);
        }
        while (!stack.isEmpty()) {
            s2 += stack.pop() + " ";
        }
        return s2;
    }
    public static void main(String args[]) {
        System.out.println(rev1String("PhucPham"));
        System.out.println(rev3("I like apple"));
    }
}
