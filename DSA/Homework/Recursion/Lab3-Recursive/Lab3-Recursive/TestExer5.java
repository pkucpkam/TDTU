//Recursive Function to Convert a Decimal to Binary 
import java.lang.*;
public  class TestExer5{
	public static void main(String[] args){
		System.out.println("Test Dec2Binary 8=" + Dec2Binary(8));
	}
	// Dec: 8 -> Binary:1000 , 8/2= 4 du(%) 0, 4/2= 2 du(%) 0, 2/2=1 du(%) 0
	public static int Dec2Binary(int nDec){
		if(nDec==0)
			return 0;
		else
			return (nDec%2 + 10*Dec2Binary(nDec/2));
	}
}