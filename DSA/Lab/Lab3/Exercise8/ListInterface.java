import java.util.*;

public interface ListInterface <T> {

	public boolean isEmpty();
	public int     size();
	public Integer       getFirst() throws NoSuchElementException; 
	public void    addFirst(Integer item);
	public Integer       removeFirst() throws NoSuchElementException;  
	public void    print();
	public void    printHead();
	public void    addAfter(Node <Integer> curr,Integer item);
	public void    addLast(Integer item);
	public Node <Integer> getHead();
	public Integer       removeLast();
	public Integer       removeAfter(Node <Integer> curr);
	public void    addSortedList(Integer item);
}
