public class IntLinkedList {
    private Node head;

    public IntLinkedList() {

    }

    public void addFirst(int data) {
        head = new Node(data, head);
    }

    public boolean addLast(int data) {
        Node currNode = head;
        while (currNode.getNext() != null) {
            if (currNode.getData() == data) {
                return false;
            }
            currNode = currNode.getNext();
        }
        currNode.setNext(new Node(data, currNode.getNext()));
        return true;
    }

    public boolean removeAt(int position) {

        if (position == 1) {
            head = head.getNext();
            return true;
        } else {
            int currPosition = 2;
            Node currNode = head.getNext();
            Node prevNode = head;
            while (currNode.getNext() != null) {
                if (currPosition == position) {
                    prevNode.setNext(currNode);
                    return true;
                }
                currNode = currNode.getNext();
                prevNode = prevNode.getNext();
                currPosition++;
            }
            return false;
        }
    }

    //  public boolean removeAt(int position) {
    //     if (position == 1) {
    //         head = head.getNext();
    //         return true;
    //     } else {
    //         int currPosition = 1;
    //         Node currNode = head;
    //         while (currNode.getNext() != null) {
    //             if (currPosition + 1 == position) {
    //                 Node tmp = currNode.getNext();
    //                 currNode.setNext(tmp.getNext());
    //                 return true;
    //             }
    //             currNode = currNode.getNext();
    //             currPosition++;
    //         }
    //         return false;
    //     }
    // }

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
        int currPosition = 0;
        Node currNode = head;
        while (currNode != null) {
            if (currNode.getData() == key) {
                return currPosition;
            }
            currNode = currNode.getNext();
            currPosition++;
        }
        return -1;
    }

    public boolean checkSorted() {
        Node currNode = head;
        if (head.getData() >= currNode.getNext().getData()) {
            while (currNode.getNext() != null) {
                if (currNode.getData() < currNode.getNext().getData()) {
                    return false;
                }
                currNode = currNode.getNext();
            }
            return true;
        } else {
            while (currNode.getNext() != null) {
                if (currNode.getData() > currNode.getNext().getData()) {
                    return false;
                }
                currNode = currNode.getNext();
            }
            return true;
        }
    }

    // print de test kq thui
    public void print() {
        Node currNode = head;
        while (currNode != null) {
            System.out.print(currNode.getData() + " ");
            currNode = currNode.getNext();
        }
        System.out.println();
    }
}
