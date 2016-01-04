//Question credit to Google Interview Practice
//Author: Yunhe Liu <liu348@wisc.edu>
package leetCodePractice;

/*
 * This class is supposed to work as:
 * Returning one of the existing equilibrium point in O(N)
 * Time complexity
 * Defining a equilibrium point:
 * Given a integer array with length N. If there is a
 * zero-based index P such that the sum of the elements[0..P-1]
 * equals to the sum of the elements [P+1, N-1], then we call
 * P a equilibrium point. 
 * For example:
 * Given integer array {1,2,3,4,3,2,1}
 * index 3 (element = 4) would be a equilibrium point 
 * 
 * Note, when P = 0 or P = N-1, we define
 * one the sums as 0
 * For example:
 * Given integer array {1, -1, 1}
 * index 0 would be a equilibrium point.
 * 
 * Worst-case time complexity O(N)
 */
public class equilibriumPoint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] A = new int[]{-1, 3, -4, 5, 1, -6, 2, 1};
		//System.out.println(A.length);
		System.out.println(solution(A));
	}
	
	public static int solution(int[] A) {
        // write your code in Java
        int i = 0;
        int sumLeft = 0;
        int sumRight = 0;
        int j = 0;
        //System.out.println("A:"+A.length);
		
        for(i=0;i<A.length;i++)
		{
			sumLeft = 0;
			sumRight = 0;
        	//handle special case
			if(i == 0)
			{
				j = i;
				//System.out.println("Here2");
				sumLeft = 0;
				for(j = 1; j<A.length; j++)
				{
					sumRight = sumRight + A[j];
				}
			}
			else if(j == A.length-1)
			{
				//System.out.println("Here3");
				sumRight = 0;
				for(j = 0; j<A.length-1; j++)
				{
					sumLeft = sumLeft + A[j];
				}
			}
			else
			{
				System.out.println("Here1");
				//calculate sumLeft
				for(j = 0; j<i; j++)
				{
					sumLeft = sumLeft + A[j];
				}
				//calculate sumRight
				for(j = i+1; j<A.length; j++)
				{
					System.out.println("A[j] = " + A[j]);
					sumRight = sumRight + A[j];
				}
			}
			//System.out.println("Here");
			System.out.println("sumLeft = " + sumLeft);
			System.out.println("sumRight = " + sumRight);
			if(sumRight == sumLeft)
			{
				return i;
			}
		}
		//System.out.println("Here");
		return -1;
    }

}
