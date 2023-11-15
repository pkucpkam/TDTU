import java.util.*;

public class TestBasicLinkedList2 {
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
		
		//ListNode <Integer> current	=	list.getFirst();
		//System.out.println("Remove the current node: " + current.getElement());
	
	}
}
