public class Main {
    public static void main(String args[]) {
        MyLinkedList list = new MyLinkedList();
        MyLinkedList list1 = new MyLinkedList();
        for (int i = 1; i < 10; i++) {
            list.addFirst(i);
        }
        list.print();
        list.removeFirst();
        System.out.println();
        list.print();
    }
}
