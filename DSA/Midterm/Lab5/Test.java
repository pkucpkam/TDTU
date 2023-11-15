public class Test {
    public static void main(String args[]) {
        IntLinkedList list = new IntLinkedList();
        list.addLast(15);
        list.addLast(15);
        list.addLast(35);
        list.print();
        list.checkSorted();
        list.removeLast();
        list.print();
    }
}
