public class TestFraction {
    public static void main(String args[]) {
        MyLinkedList <Fraction> list = new MyLinkedList<Fraction> ();
        Fraction n = new Fraction(1,2);
        Fraction n1 = new Fraction(3,4); 
        Fraction n2 = new Fraction(2,4);
        System.out.println("Check empty list : " + list.isEmpty());
        list.addFirst(n);
        list.addFirst(n1);
        list.addLast(n2);
        list.print();
        System.out.println("Check remove last : ");
        list.removeLast();
        list.print();
        System.out.println("Check contain " + n2 + " : " + list.contains(n2));
        System.out.println("Check equals " + n + " with " + n1);
        System.out.println(n.equals(n1));
    }
}
