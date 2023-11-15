import java.lang.*;
public  class TestExer7{
	public static void main(String[] args){
        int A[]={7,-3,9,-8,-5,6};
		System.out.println("Test 7a: find min of arr: " + E7a_findMinArr(A,A.length));
	}
    // 7a. Find and return the minimum element in an array
	public static int E7a_findMinArr(int Arr[],int n){
		if(n==1)
			return Arr[0];
		else
			return Math.min(Arr[n-1],E7a_findMinArr(Arr, n-1));
	}
}