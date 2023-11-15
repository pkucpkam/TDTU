public class TestExercise3 extends Exercise3 {
    public static void main (String args[]) {
        
        MyLinkedList<Integer> list = new MyLinkedList<Integer>();
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        list.addFirst(4);
        list.addFirst(5);
        list.addFirst(6);
        list.addFirst(7);
        list.print();
        System.out.println("Count the number of even item in the list : " + countEven(list));
        System.out.println("Count the number of prime item in the list : " + countPrime(list));
        System.out.println("Add item 100 before the first even element in the list : ");
        addAfterFirtsEven(100, list);
        list.print();
        System.out.println("Find the maximum number in the list : " + findMax(list));
        System.out.println("Check reverse list ");
        list.revList();
        list.print();
        System.out.println("Check sort");
        sortAc(list);
        list.print();
    }

}
