public class IntLinkedList {
    private Node head;

    public IntLinkedList() {
        this.head = null;
    }

    public void addFirst(int data) {
        head = new Node(data, head);
    }

    public boolean addLast(int data) {
        Node currNode = head;
        if (head == null) {
            head = new Node(data, head);
            return true;
        } else {
            while (currNode.getNext() != null) {
                if (currNode.getData() == data) {
                    return false;
                }
                currNode = currNode.getNext();
            }
            currNode.setNext(new Node(data, currNode.getNext()));
            return true;
        }
    }

    public boolean removeAt(int position) {
        if (position == 1) {
            head.setNext(head.getNext());
            return true;
        } else {
            int currPosition = 2;
            Node currNode = head.getNext();
            while (currNode.getNext() != null && currPosition + 1 != position) {
                if (currPosition + 1 == position) {
                    Node tmp = currNode;
                    tmp.setNext(currNode.getNext());
                    return true;
                }
                currNode = currNode.getNext();
                currPosition++;
            }
            return false;
        }
    }

    public int countOdd() {
        int count = 0;
        Node currNode = head;
        while (currNode != null) {
            if (currNode.getData() % 2 == 1) {
                count++;
            }
            currNode = currNode.getNext();
        }
        return count;
    }

    public int searchKey(int key) {
        int currPosition = 1;
        Node currNode = head;
        while (currNode != null) {
            if (currNode.getData() == key) {
                return currPosition;
            }
            currPosition++;
            currNode = currNode.getNext();
        }
        return -1;
    }

    public boolean checkSorted() {
        Node currNode = head;
        int asc = 0, count = 0, desc = 0;
        // check asc
        while (currNode.getNext() != null) {
            if (currNode.getData() >= currNode.getNext().getData()) {
                asc++;
            }
            count++;
            currNode = currNode.getNext();

        }
        currNode = head;
        while (currNode.getNext() != null) {
            if (currNode.getData() <= currNode.getNext().getData()) {
                desc++;
            }
            currNode = currNode.getNext();
        }
        if (asc == count || desc == count) {
            return true;
        } else {
            return false;
        }
    }

    public void print() {
        Node currNode = head;
        while (currNode != null) {
            System.out.print(currNode.getData() + " ");
            currNode = currNode.getNext();
        }
        System.out.println();
    }

    public void addAfter(Node a, int data) { 
        if (a == null) {
            head = new Node(data, head);
        }
        else {
            Node currNode = head;
            while (currNode != null) {
                if (currNode == a) {
                    currNode.setNext(new Node(data, currNode.getNext()));
                }
                currNode = currNode.getNext();
            }
        }
    }

    public void removeLast() {
        Node currNode = head;
        Node prevNode = null;
        while (currNode.getNext() != null) {
            prevNode = currNode;
            currNode = currNode.getNext();
        }
        prevNode.setNext(currNode.getNext());
    }
}
