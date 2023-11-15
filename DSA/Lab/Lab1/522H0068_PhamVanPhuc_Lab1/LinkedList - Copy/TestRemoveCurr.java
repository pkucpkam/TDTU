public class TestRemoveCurr {
    public static void main (String args[]) {
        MyLinkedList<Integer> list = new MyLinkedList<Integer>();
        list.addFirst(18);
        list.addFirst(3);
        list.addFirst(2004);
        list.addLast(2023);
        list.print();

        //Remove the node after head
        Node<Integer> afterHeadNode = list.getHead();
        afterHeadNode = afterHeadNode.getNext();
        System.out.println("Check remove current node");
        list.removeCurr(afterHeadNode);
        list.print();
        //Remove node head
        System.out.println("Check remove current node is head");
        list.removeCurr(list.getHead());
        list.print();
    }
}
