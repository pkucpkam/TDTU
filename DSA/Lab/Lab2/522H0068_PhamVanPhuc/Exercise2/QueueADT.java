
public interface QueueADT <E> {

	// return true if queue has no elements
	public boolean  isEmpty();

	// return the front of the queue 
	public E        peek();

	// remove and return the front of the queue
	public E        poll(); // also commonly known as dequeue

	// add item to the back of the queue
	public boolean offer(E item); // also commonly known as enqueue
}
