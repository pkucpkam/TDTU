public class TestQueue {
	public static void main (String[] args) {

		QueueLL <Fraction> queue= new QueueLL <Fraction> (); 
		System.out.println("queue is empty? " + queue.isEmpty());
		queue.offer(new Fraction(1, 2));
		System.out.println("operation: queue.offer(\"1\")");
		System.out.println("queue is empty? " + queue.isEmpty());
		System.out.println("front now is: " + queue.peek());
		queue.offer(new Fraction(2, 3));
		System.out.println("operation: queue.offer(\"2\")");
		System.out.println("front now is: " + queue.peek());
		queue.offer(new Fraction(4, 5));
		System.out.println("operation: queue.offer(\"3\")");
		System.out.println("front now is: " + queue.peek());
		queue.poll();
		System.out.println("operation: queue.poll()");
		System.out.println("front now is: " + queue.peek());
		System.out.print("checking whether queue.peek().equals(\"4/6\"): ");
		System.out.println(queue.peek().equals(new Fraction(4, 6)));
		System.out.print("checking whether queue.peek().equals(\"1/3\"): ");
		System.out.println(queue.peek().equals(new Fraction(1, 3)));
		queue.poll();
		System.out.println("operation: queue.poll()");
		System.out.println("front now is: " + queue.peek());
		queue.poll();
		System.out.println("operation: queue.poll()");
		System.out.println("front now is: " + queue.peek());
	}
}
