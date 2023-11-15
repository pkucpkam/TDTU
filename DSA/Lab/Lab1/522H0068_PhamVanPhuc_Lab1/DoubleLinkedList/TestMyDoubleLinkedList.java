//Test voi Integer
public class TestMyDoubleLinkedList {
    public static void main(String args[]) { 
        MyDoubleLinkedList <Integer> list = new MyDoubleLinkedList<Integer>();
        list.add(15);
        list.add(3);
        list.add(4);
        list.add(35);
        list.add(45);
        list.add(12);
        list.print();
        System.out.println("Test remove first:");
        list.removeFirst();
        list.print();
        System.out.println("Test contain : 14 " +list.contain(14));
        System.out.println("Test contain : 35 " + list.contain(35) );
    }
}
