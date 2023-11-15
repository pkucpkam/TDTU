import java.util.*;

public class TestMyLinkedList1 {
	public static void main(String [] args){
		MyLinkedList <String> list = new MyLinkedList <String>();
		list.addFirst("aaa");
		list.addFirst("bbb");
		list.addFirst("ccc");
		list.print();

		System.out.println("Testing removal");
		list.removeFirst();
		list.print();

		if (list.contains("aaa")) 
			list.addFirst("xxxx");
		list.print();
		System.out.println("Testing list 1");
		MyLinkedList <String> list1 = new MyLinkedList <String>();
		list1.addFirst("aaa");
		list1.addFirst("bbb");
		list1.addFirst("ccc");
		list1.printHead();

		System.out.println("Testing removal 1");
		list1.removeFirst();
		list1.printHead();

		if (list1.contains("aaa")) 
			list1.addFirst("xxxx");
		list1.printHead();	
		
	}
}
