import java.util.Stack;

public class QueueFrom2Stack <E> {
    Stack<E> Stack1 = new Stack<>();
    Stack<E> Stack2 = new Stack<>();

    //add element to queue
    public void enQueue(E item) {
        Stack1.push(item);
    }

    public boolean isEmpty() {
        if (Stack1.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }

    public E deQueue() {
        while (!Stack1.isEmpty()) {
            Stack2.push(Stack1.pop());
        }
        E tmp = Stack2.pop();
        while (!Stack2.isEmpty()) {
            Stack1.push(Stack2.pop());
        }
        return tmp;
    }

    public E peek() {
        while (!Stack1.isEmpty()) {
            Stack2.push(Stack1.pop());
        }
        E tmp = Stack2.peek();
        while (!Stack2.isEmpty()) {
            Stack1.push(Stack2.pop());
        }
        return tmp;
    }
    
}
