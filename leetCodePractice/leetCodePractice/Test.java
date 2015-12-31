package leetCodePractice;

public class Test {

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
