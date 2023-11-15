import java.util.*;

class StackLL <E> implements StackADT <E> {
	private MyLinkedList <E> list;

	public StackLL() {
		list = new MyLinkedList <E> ();
	}

	public boolean empty() { return list.isEmpty(); }

	public E peek() throws EmptyStackException {
		try {
			return list.getFirst();
		} catch (NoSuchElementException e) {
			throw new EmptyStackException();
		}
	}

	public E pop() throws EmptyStackException {
		E obj = peek();
		list.removeFirst();
		return obj;
	}

	public void push(E o) {
		list.addFirst(o);
	}
}
