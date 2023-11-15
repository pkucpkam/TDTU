import java.util.Scanner;

public class Main {
    public class Node {
        int value;
        Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Node(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    public class MyLinkedList {
        Node head;

        public void addLast(int n) {
            Node newNode = new Node(n);
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

        public void print() {
            for (Node n1 = head; n1 != null; n1 = n1.getNext()) {
                System.out.print(n1.getValue() + " ");
            }
        }

        public void delEle(int k) {
            if (k == 0) {
                head = head.next;
                return;
            }
            Node curren = head;
            for (int i = 0; i < k - 1; i ++) {
                curren = curren.next;
            }

            Node nodedel = curren.next;
            curren.next = nodedel.next;
        }
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        Main mainInstance = new Main(); // Tạo thể hiện của Main
        MyLinkedList list = mainInstance.new MyLinkedList(); // Tạo thể hiện của MyLinkedList
        int n, tmp, k;
        n = sc.nextInt();
        for (int i = 1; i <= n; i++) {
            tmp = sc.nextInt();
            list.addLast(tmp);
        }
        //k = sc.nextInt();
        //list.delEle(k);
        list.print();
        sc.close();
    }
}
