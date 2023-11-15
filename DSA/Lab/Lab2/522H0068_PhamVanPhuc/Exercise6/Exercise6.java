public class Exercise6 {
    public static void main(String args[]) {
        QueueFrom2Stack<Integer> queue1 = new QueueFrom2Stack<Integer>();
        System.out.println("Test isEmpty : " + queue1.isEmpty());
        queue1.enQueue(15);
        queue1.enQueue(19);
        queue1.enQueue(12);
        System.out.println("front now is : " + queue1.peek());
        queue1.deQueue();
        System.out.println("front now is : " + queue1.peek());
        System.out.println("Test isEmpty : " + queue1.isEmpty());

    }
}
