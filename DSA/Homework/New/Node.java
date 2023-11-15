public class Node {
    int element;
    Node next;

    public Node (int element, Node next) {
        this.element = element;
        this.next = next;
    }

    public Node (int element) {
        this.element = element;
        this.next = null;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public int getEle() {
        return element;
    }

    
}
