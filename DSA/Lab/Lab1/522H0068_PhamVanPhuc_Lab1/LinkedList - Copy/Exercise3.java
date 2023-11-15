public class Exercise3 {
    // exercise 3
    public static int countEven(MyLinkedList<Integer> list) {
        int count = 0;
        Node<Integer> curr = list.getHead();
        while (curr.getNext() != null) {
            if (curr.getData() % 2 == 0) {
                count++;
            }
            curr = curr.getNext();
        }
        return count;
    }

    public static boolean checkPrime(int n) {
        int dem = 0;
        for (int i = 2; i <= n; i++) {
            if (n % i == 0) {
                dem++;
            }
        }
        return (dem == 1);
    }

    public static int countPrime(MyLinkedList<Integer> list) {
        int count = 0;
        Node<Integer> curr = list.getHead();
        while (curr.getNext() != null) {
            if (checkPrime(curr.getData())) {
                count++;
            }
            curr = curr.getNext();
        }
        return count;
    }

    public static void addAfterFirtsEven(int x, MyLinkedList<Integer> list) {
        Node<Integer> tmp = list.getHead();
        Node<Integer> curr = tmp.getNext();
        while (curr.getData() % 2 != 0 && curr != null) {
            curr = curr.getNext();
            tmp = tmp.getNext();
        }
        list.addAfter(tmp, x);
    }

    public static int findMax(MyLinkedList<Integer> list) {
        Node<Integer> curr = list.getHead();
        int max = curr.getData();
        while (curr != null) {
            if (max < curr.getData()) {
                max = curr.getData();
            }
            curr = curr.getNext();
        }
        return max;
    }

    public static void sortAc(MyLinkedList<Integer> list) {
        Node<Integer> sortedList = null;

        Node<Integer> curr = list.getHead();
        while (curr != null) {
            Node<Integer> next = curr.getNext();

            if (sortedList == null || curr.getData() <= sortedList.getData()) {
                curr.setNext(sortedList);
                sortedList = curr;
            } else {
                Node<Integer> prevSorted = sortedList;
                while (prevSorted.getNext() != null && curr.getData() > prevSorted.getNext().getData()) {
                    prevSorted = prevSorted.getNext();
                }
                curr.setNext(prevSorted.getNext());
                prevSorted.setNext(curr);
            }

            curr = next;
        }
        list.setHead(sortedList);
    }

}
