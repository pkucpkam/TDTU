import java.util.*;

class MyLinkedList<T> implements ListInterface<Integer>  {

	// Data attributes
	private Node<Integer> head = null;
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
	public Integer getFirst() throws NoSuchElementException {
		if (head == null)
			throw new NoSuchElementException("can't get from an empty list");
		else
			return head.getData();
	}

	// Return true if list contains item, otherwise return false.
	public boolean contains(Integer item) {
		for (Node<Integer> n = head; n != null; n = n.getNext())
			if (n.getData().equals(item))
				return true;

		return false;
	}

	// Add item to front of list.
	public void addFirst(Integer item) {
		head = new Node<Integer>(item, head);
		numNode++;
	}

	// Remove first node of list.
	public Integer removeFirst() throws NoSuchElementException {
		Node<Integer> ln;
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

		Node<Integer> ln = head;
		System.out.print("List is: " + ln.getData());
		for (int i = 1; i < numNode; i++) {
			ln = ln.getNext();
			System.out.print(", " + ln.getData());
		}
		System.out.println(".");
	}

	// Print values of nodes in list.
	public void printHead() throws NoSuchElementException {
		if (head == null)
			throw new NoSuchElementException("Nothing to print...");

		// Node <E> ln = head;
		System.out.print("List is: " + head.getData());
		for (int i = 1; i < numNode; i++) {
			head = head.getNext();
			System.out.print(", " + head.getData());
		}
		System.out.println(".");
	}

	@Override
	public void addAfter(Node<Integer> curr, Integer item) {
		if (curr == null) {
			addFirst(item);
		} else {
			Node<Integer> newNode = new Node<Integer>(item, curr.getNext());
			curr.setNext(newNode);
		}
		numNode++;
	}

	@Override
	public void addLast(Integer item) {
		if (head == null) {
			addFirst(item);
		} else {
			Node<Integer> tmp = head;
			while (tmp.getNext() != null) {
				tmp = tmp.getNext();
			}
			Node<Integer> newNode = new Node<Integer>(item, null);
			tmp.setNext(newNode);
			numNode++;
		}

	}

	@Override
	public Node<Integer> getHead() {
		return head;
	}

	@Override
	public Integer removeAfter(Node<Integer> curr) {
		if (curr == null) {
			throw new NoSuchElementException("Can't remove element from an empty list");
		} else {
			Node<Integer> delNode = curr.getNext();
			if (delNode != null) {
				curr.setNext(delNode.getNext());
				numNode--;
				return delNode.getData();
			} else {
				throw new NoSuchElementException("No next node to remove");
			}
		}
	}

	@Override
	public Integer removeLast() throws NoSuchElementException {
		if (head == null) {
			throw new NoSuchElementException("Can't remove element from an empty list");
		} else {
			Node<Integer> preNode = null;
			Node<Integer> delNode = head;
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

	
	public void addSortedList(Integer item) {
		if (head == null || item.compareTo(head.getData()) <= 0) {
			addFirst(item);
		} else {
			addSortedList(head, item);
		}
	}
	
	public void addSortedList(Node<Integer> curr, Integer item) {
		if (curr.getNext() == null || item.compareTo(curr.getNext().getData()) <= 0) {
			// add after node curr
			addAfter(curr, item);
		} else {
			addSortedList(curr.getNext(), item);
		}
	}
	

    public int countEven() {
		return countEven(head);
	}
	public int countEven(Node <Integer> curr) {
		if (curr.getNext() == null) {
			if (curr.getData() % 2 == 0) {
				return 1;
			}
			else {
				return 0;
			}
		}
		else {
			if (curr.getData() % 2 == 0) {
				return 1 + countEven(curr.getNext());
			}
			else {
				return countEven(curr.getNext());
			}
		}
	} 

	public int sumNum(){
		return sumNum(head);
	}
	public int sumNum(Node <Integer> curr) {
		if (curr.getNext() == null) {
			return curr.getData();
		}
		else {
			return curr.getData() + sumNum(curr.getNext());
		}
	}

}
