import java.util.*;

class MyLinkedList <E> implements ListInterface <E> {

	// Data attributes
	private Node <E> head = null;
	private int numNode = 0;

	// Return true if list is empty; otherwise return false.
	public boolean isEmpty() { 
		return (numNode == 0); 
	}

	// Return number of nodes in list.
	public int size() { 
		return numNode; 
	}

	// Return value in the first node.
	public E getFirst() throws NoSuchElementException {
		if (head == null) 
			throw new NoSuchElementException("can't get from an empty list");
		else return head.getData();
	}

	// Return true if list contains item, otherwise return false.
	public boolean contains(E item) {
		for (Node <E> n = head; n != null; n = n.getNext())
			if (n.getData().equals(item)) return true;

		return false;
	}

	// Add item to front of list.
	public void addFirst(E item) {
		head = new Node <E> (item, head);
		numNode++;
	}

	// Remove first node of list.
	public E removeFirst() throws NoSuchElementException {
		Node <E> ln;
		if (head == null) 
			throw new NoSuchElementException("can't remove from an empty list");
		else { 
			ln = head;
			head = head.getNext();
			numNode--;
			return ln.getData();
		}
	}

	// Print values of nodes in list.
	public void print() throws NoSuchElementException {
		if (head == null)
			throw new NoSuchElementException("Nothing to print...");

		Node <E> ln = head;
		System.out.print("List is: " + ln.getData());
		for (int i=1; i < numNode; i++) {
			ln = ln.getNext();
			System.out.print(", " + ln.getData());
		}
		System.out.println(".");
	}
		// Print values of nodes in list.
	public void printHead() throws NoSuchElementException {
		if (head == null)
			throw new NoSuchElementException("Nothing to print...");

		//Node <E> ln = head;
		System.out.print("List is: " + head.getData());
		for (int i=1; i < numNode; i++) {
			head = head.getNext();
			System.out.print(", " + head.getData());
		}
		System.out.println(".");
	}

	@Override
	public void addAfter(Node <E> curr, E item) {
		if (curr == null) {
			addFirst(item);
		}
		else {
			Node <E> newNode = new Node<E>(item,curr.getNext());
			curr.setNext(newNode);
		}
	}

	@Override
	public void addLast(E item) {
		if (head == null) {
			addFirst(item);
		}
		else {
			Node <E> tmp = head;
			while (tmp.getNext() != null) {
				tmp = tmp.getNext();
			}
			Node <E> newNode = new Node<E>(item,null);
			tmp.setNext(newNode);
			numNode++;
		}

	}

	@Override
	public Node<E> getHead() {
		return head;
	}

	@Override
	public E removeAfter(Node<E> curr) {
		if (curr == null) {
			throw new NoSuchElementException("Can't remove element from an empty list");
		}
		else {
			Node <E> delNode = curr.getNext();
			if (delNode != null) {
				curr.setNext(delNode.getNext());
				numNode--;
				return delNode.getData();
			}
			else {
				throw new NoSuchElementException("No next node to remove");
			}
		}
	}

	@Override
	public E removeLast() throws NoSuchElementException {
		if (head == null) {
			throw new NoSuchElementException("Can't remove element from an empty list");
		}
		else {
			Node <E> preNode = null;
			Node <E> delNode = head;
			while (delNode.getNext() != null) {
				preNode = delNode;
				delNode = delNode.getNext();
			}
			preNode.setNext(delNode.getNext());
			delNode.setNext(null);
			numNode--;
			return delNode.getData();
		}
	}

	
}
