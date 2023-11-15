public class Test {

    
    public static void main (String args[]) {
        MyLinkedList<Integer> listInt = new MyLinkedList<>();
        listInt.addSortedList(2);
        listInt.addSortedList(10);
        listInt.addSortedList(4);
        listInt.addSortedList(9);
        listInt.addSortedList(1);
        listInt.print();
        System.out.println("Count even number : " + listInt.countEven());
        System.out.println("Sum elements of the list : " + listInt.sumNum());
    }
}
