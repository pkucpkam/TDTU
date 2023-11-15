class Node <E> {
	/* data attributes */
	private E element;
	private Node <E> next;
	/* constructors */
	public Node(E item) { 
		this(item, null); 
	}
	public Node(E item, Node <E> n) { 
		element = item; 
		next = n; 
	}
	/* get the next ListNode */
	public Node <E> getNext() {
		return next;
	}
	/* get the element of the ListNode */
	public E getData() {
		return element;
	}
	/* set the next reference */
	public void setNext(Node <E> n) {
		next = n;
	}
}
