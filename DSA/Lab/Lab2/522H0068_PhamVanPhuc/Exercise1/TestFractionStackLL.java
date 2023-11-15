public class TestFractionStackLL {
	public static void main (String[] args) {
		StackLL <Fraction> stack = new StackLL <Fraction>();

		System.out.println("stack is empty? " + stack.empty());
		stack.push( new Fraction(3, 4));
		stack.push(new Fraction(7, 8));
		System.out.println("top of stack is " + stack.peek());
		stack.push(new Fraction(1, 3) );
		System.out.println("top of stack is " + stack.pop());
		stack.push(new Fraction(2, 6));
		stack.pop();
		stack.pop();
		System.out.println("top of stack is " + stack.peek());
        System.out.println("stack is empty? " + stack.empty());
	}
}