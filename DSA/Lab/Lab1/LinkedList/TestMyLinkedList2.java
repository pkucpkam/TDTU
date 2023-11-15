import java.util.*;

public class TestMyLinkedList2 {
	public static void main(String [] args) 
		                      throws NoSuchElementException {
		MyLinkedList <Integer> list = new MyLinkedList <Integer>();

		list.addFirst(34);
		list.addFirst(12);
		list.addFirst(9);
		list.print();

		System.out.println("Testing removal");
		list.removeFirst();
		list.print();
		
		System.out.println("Testing AddLast");
		list.addLast(18);
		list.print();

		System.out.println("Testing contain");
		System.out.println(list.contains(12));
	}
}
