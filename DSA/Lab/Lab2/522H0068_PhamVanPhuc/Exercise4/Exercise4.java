import java.util.Stack;
public class Exercise4 {
    
    public static void rev(String s){
        Stack<Character> stack = new Stack<>();
        char ch;
        //them vao stack
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            stack.push(ch);
        }
        //lay ra
        while (!stack.empty()) {
            ch = stack.pop();
            System.out.print(ch);
        }
    }
    public static void main(String args[]) {
        System.out.println("Test with s = PhamVanPhuc");
        rev("PhamVanPhuc");
        System.out.println("Test with s = CTDL & GT");
        rev("CTDL & GT");
    }
}
