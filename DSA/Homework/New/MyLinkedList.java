public class MyLinkedList implements ListInterface{
    Node tail;
    Node head;
    int num_nodes = 0;
    public void addFirst(int item) {
        head = new Node(item, head);
        num_nodes++;
    }

    public void addAfter(int item) {
        Node newNode = new Node(item);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        }
        else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public void removeFirst() {
        Node newNode = head;
        head = head.getNext();
    }

    public boolean isEmpty() {
        return num_nodes == 0;
    }

    public void print() {
        Node h = head;
        while (h != null) {
            System.out.print(h.getEle() + " ");
            h = h.getNext();
        }
    }
}
