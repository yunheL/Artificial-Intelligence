//this solution is accepted as of 1/1/2016
//Question credit to Google Interview Code Sample
//Author: Yunhe Liu <liu348@wisc.edu>
package leetCodePractice;

import java.util.Arrays;

/*
 * This class works as:
 * Given an integer, duplicate one of its digits, insert
 * the duplicated digit next to the original digit. Find
 * the largest possible number that can be generated this
 * way.
 * For example:
 * input1: 123
 * output2: 1233
 * input2: 654
 * output2: 6654
 */
public class GoogleSample1 {

	public static void main(String[] args) {
		//enter test cases here
		int test = 123454321;
		solution(test);
	}
	
	//Take an integer array, duplicate element[position]
	//and insert next to the original element, convert the
	//resulted integer array to an integer and return the
	//integer.
	public static int convert(int[] A, int position)
	{
		int[] convNum = new int[A.length+1];
		
		int ret = 0;
		int i = 0;
		int j = 0;
		for(i = 0; i<position+1; i++)
		{
			convNum[i] = A[i];
		}
		convNum[position+1] = A[position];
		for(i = position+1; i<convNum.length; i++)
		{
			convNum[i] = A[i-1];
		}
		
		//System.out.println(Arrays.toString(convNum));
		
		for(j = 0; j<convNum.length;j++)
		{
			ret = ret + (int)(convNum[j] * Math.pow(10, convNum.length-1-j));
		}
		
		
		return ret;
	}
	
	//create an integer array from the input integer
	//call convert() in a loop to get all converted integer
	//return the largest converted integer
	public static int solution(int X) {
        // write your code in Java
		int i = 0;
		int Y = X;
		int numDigits = 1;
		int dig = 0;
		
		//count the digits in the integer
		while(Y>1)
		{
			numDigits++;
			Y = Y/10;
		}
		//System.out.println(numDigits);
		
		int[] digits = new int[numDigits];
		
		Y = X;
		for(i = 0; i<digits.length; i++)
		{
			dig = Y%10;
			Y = Y/10;
			digits[digits.length - 1 - i] = dig;
			//System.out.println("here");
		}
		//System.out.println(Arrays.toString(digits));
		
		
		//System.out.println("conv:"+convert(digits,0));
		int k = 0;
		int largest = convert(digits,0);
		for(k = 0; k<digits.length;k++)
		{
			if(convert(digits,k)>largest)
			{
				largest = convert(digits,k);
			}
		}
		
		System.out.println("largest:" +largest);
		return largest;
    }

}
