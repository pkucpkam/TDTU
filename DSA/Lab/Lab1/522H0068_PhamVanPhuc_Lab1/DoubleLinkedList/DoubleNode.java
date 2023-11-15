public class DoubleNode <E> {
    private E data;
    private DoubleNode <E> next;
    private DoubleNode <E> prev;

    public DoubleNode(E item) {
        this.data = item;
        this.next = null;
        this.prev = null;
    }
    public DoubleNode(E item, DoubleNode <E> p, DoubleNode <E> n) {
        this.data = item;
        this.next = n;
        this.prev = p;
    }

    public DoubleNode <E> getNext() {
        return this.next;
    }
    public void setNext(DoubleNode <E> n) {               
        this.next = n;
    }
    public DoubleNode <E> getPrev() {
        return this.prev;
    }
    public void setPrev(DoubleNode <E> p) {               
        this.prev = p;
    }
    public void setData(E item) {
        this.data = item;
    }
    public E getData() {
        return this.data;
    }
}