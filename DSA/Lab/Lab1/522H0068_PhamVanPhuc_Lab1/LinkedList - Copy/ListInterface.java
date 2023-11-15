import java.util.*;

public interface ListInterface <E> {

	public boolean isEmpty();
	public int     size();
	public E       getFirst() throws NoSuchElementException; 
	public boolean contains(E item);
	public void    addFirst(E item);
	public E       removeFirst() throws NoSuchElementException;  
	public void    print();
	public void    printHead();
	public void    addAfter(Node <E> curr,E item);
	public void    addLast(E item);
	public Node <E> getHead();
	public E       removeLast();
	public E       removeAfter(Node <E> curr);
	public E 	   removeCurr(Node <E> curr);
}
