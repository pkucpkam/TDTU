import java.lang.*;
public  class TestExer1{
	public static void main(String[] args){
		System.out.println("Test 5!=" + Fact(5));
	}
	// Compute n! (n>=0)
	public static int Fact(int n){
		if(n==0)
			return 1;
		else
			return n*Fact(n-1);
	}
}K