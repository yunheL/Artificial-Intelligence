package leetCodePractice;

import java.util.Arrays;

public class GoogleSample1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int test = 12511;
		solution(test);
	}
	
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
		
		
		//System.out.println("largest:" +largest);
		return largest;
    }

}
