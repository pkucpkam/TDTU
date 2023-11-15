
class QueueLL <E> implements QueueADT <E> {
	private MyLinkedList <E> list;
	public QueueLL() { list = new MyLinkedList <E> (); }
	public boolean isEmpty() { return list.isEmpty(); }

	public boolean offer(E o) { 
		list.addLast(o); 
		return true;
	}
	public E peek() {
		if (isEmpty()) return null;
		return list.getFirst();
	}
	public E poll() {
		E obj = peek();
		if (!isEmpty()) list.removeFirst();
		return obj;
	}
}
