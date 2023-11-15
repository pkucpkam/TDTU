import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

public class Stack2 {
    public static boolean check(int n) {
        Stack<Integer> stack = new Stack<>();
        Queue<Integer> queue = new LinkedList<>();
        while (n != 0) {
            stack.push(n % 10);
            queue.offer(n % 10);
            n = n / 10;
        }

        while (!stack.isEmpty()) {
            if (stack.pop() != queue.poll()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String args[]) {
        System.out.println(check(1231));
    }
}
