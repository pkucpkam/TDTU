import java.util.*;

public class Main {
    // verify a positive integer is palindrome
    public static boolean isPalindrome(int a) {
        Stack<Integer> stack = new Stack<>();
        Queue<Integer> queue = new LinkedList<>();
        int d;

        while (a > 0) {
            d = a % 10;
            stack.push(d);
            queue.offer(d);
            a /= 10;
        }

        while (! stack.empty()) {
            if (stack.pop() != queue.poll()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(1234321));
        System.out.println(isPalindrome(123432));
    }
}
