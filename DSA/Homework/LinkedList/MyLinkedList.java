import java.util.NoSuchElementException;

public class MyLinkedList implements ListInterface {
    Node head;

    @Override
    public void addFirst(int item) {
        head = new Node(item, head);
    }

    @Override
    public int count() {
        int size = 0;

        for (Node n = head; n != null; n = n.getNext()) {
            size++;
        }
        return size;
    }

    @Override
    public void print() {
        System.out.print("[");
        for (Node n = head; n != null; n = n.getNext()) {
            System.out.print(n.getElement() + " ");
        }
        System.out.println("]");
    }

    @Override
    public int removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        int value = head.getElement();
        head = head.getNext();
        return value;
    }

    @Override
    public int getMax() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        Node n = head.getNext();
        int max = head.getElement();
        while (n != null) {
            if (n.getElement() > max) {
                max = n.getElement();
            }
            n = n.getNext();
        }
        return max;
    }

    @Override
    public int sumEven() {
        int sum = 0;
        Node n = head;
        while (n != null) {
            if (n.getElement() % 2 == 0) {
                sum += n.getElement();
            }
            n = n.getNext();
        }
        return sum;
    }

    @Override
    public int countKey(int x) {
        int count = 0;
        Node n = head;
        while (n != null) {
            if (x == n.getElement()) {
                count++;
            }
            n = n.getNext();
        }
        return count;
    }

    public void addEnd(int item) {
        Node newNode = new Node(item);
        if (head == null) {
            head = newNode;
            return;
        }

        Node last = head;
        while (last.getNext() != null) {
            last = last.getNext();
        }
        last.setNext(newNode);
    }
}