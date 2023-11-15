import java.util.NoSuchElementException;

class MyDoubleLinkedList<E> implements InterfaceDoubleLinkedList<E> {
    private DoubleNode<E> head = null;
    private DoubleNode<E> tail = null;
    private int num_node = 0;

    @Override
    public void add(E item) {
        DoubleNode<E> newNode = new DoubleNode<>(item);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        num_node++;
    }

    @Override
    public E removeFirst() throws NoSuchElementException {
        if (size() == 0) {
            throw new NoSuchElementException("List empty");
        }
        E removedItem = head.getData();
        head = head.getNext();
        if (head != null) {
            head.setPrev(null);
        } else {
            tail = null;
        }
        num_node--;
        return removedItem;
    }

    @Override
    public int size() {
        return num_node;
    }

    @Override
    public boolean contain(E item) {
        DoubleNode<E> curr = head;
        while (curr != null) {
            if (curr.getData() == item) {
                return true;
            }
            curr = curr.getNext();
        }
        return false;
    }

    @Override
    public void print() {
        DoubleNode<E> curr = head;
        System.out.print("List is : ");
        while (curr != null) {
            System.out.print(curr.getData() + " ,");
            curr = curr.getNext();
        }
        System.out.println();
    }
}