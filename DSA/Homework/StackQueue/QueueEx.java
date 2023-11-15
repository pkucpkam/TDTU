import java.util.*;

public class QueueEx {
    // reverse an positive integer using queue
    public static int reverseInt(int a) {
        Queue<Integer> queue = new LinkedList<>();
        int d;

        while (a > 0) {
            d = a % 10; // 123 % 10 = 3
            queue.offer(d);
            a = a / 10; // 123 / 10 = 12
        }

        while (! queue.isEmpty()) {
            d = queue.poll();
            a = a * 10 + d; // 12 * 10 + 3 = 123
        }

        return a;
    }

    public static String extractLow(String s) {
        Queue<Character> queue = new LinkedList<>();
        char ch;
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            if (Character.isLowerCase(ch)) {
                queue.offer(ch);
            }
        }
        String sn = "";
        while (!queue.isEmpty()) {
            ch = queue.poll();
            sn = sn + ch;
        }
        return sn;
    }

    public static void main(String[] args) {
        System.out.println(extractLow("PhucPhaM"));
    }
}
