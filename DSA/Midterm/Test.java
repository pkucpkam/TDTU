public class Test {
    public static void main (String args[]) {
        IntLinkedList list = new IntLinkedList();
        list.addFirst(25);
        list.addFirst(15);
        list.addFirst(15);
        list.addLast(35);
        System.out.println(list.checkSorted());
    }
}
