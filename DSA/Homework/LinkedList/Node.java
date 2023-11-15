class Node {
    int element;
    Node next;

    public Node() {
    }

    public Node(int element, Node next) {
        this.element = element;
        this.next = next;
    }

    public Node(int element) {
        this.element = element;
    }

    public int getElement() {
        return element;
    }

    public void setElement(int element) {
        this.element = element;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}