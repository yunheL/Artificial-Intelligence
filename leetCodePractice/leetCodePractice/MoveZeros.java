//This solution is accepted as of 12/31/2015
//Question credit to leetcode
//Author: Yunhe Liu <liu348@wisc.edu>
package leetCodePractice;
import java.util.Arrays;

/*
 * This method works as:
 * Given an integer array, move all the 0 to
 * the end of the array without changing the 
 * sequence of the other elements
 * For example:
 * input1: [0, 1, 0, 3, 12]
 * output1: [1, 3, 12, 0, 0]
 */
public class MoveZeros {

	public static void main(String[] args) {
		//Enter test cases here
		int[] nums = new int[]{0,1,0,3,12};
		int[] nums1 = new int[]{0};
		int[] nums2 = new int[]{2,1,0,3,12};
		
		moveZeroes(nums);
		System.out.println("Test");
		System.out.println("Exp: [1, 3, 12, 0, 0]");
		System.out.println("Now: " + Arrays.toString(nums));
		System.out.println();
		
		moveZeroes(nums1);
		System.out.println("Test");
		System.out.println("Exp: [0]");
		System.out.println("Now: " + Arrays.toString(nums1));
		System.out.println();
		
		moveZeroes(nums2);
		System.out.println("Test");
		System.out.println("Exp: [2, 1, 3, 12, 0]");
		System.out.println("Now: " + Arrays.toString(nums2));
		System.out.println();
	}

	public static void moveZeroes(int[] nums) {
		int i = nums.length-1;
		for(;i>-1;i--)
		{
			//find the zeros
			if(nums[i] == 0)
			{
				
				//if this zero is already at the end of the array 
				if(i == nums.length-1)
				{
					//do nothing
				}
				
				//if this zero is already in the zero list 
				else if(nums[i+1] == 0)
				{
					//do nothing
				}
				
				//if this zero needs to be taken care of
				else
				{
					int j = i+1;
					//TODO
					//System.out.println("i = " + i);
					//find the index of the next zero
					while(nums[j] != 0)
					{
						j++;
						
						//if there is no more zero in the array
						if(j == nums.length)
						{
							break;
						}
					}
					
					//TODO
					//System.out.println("j = " + j);
					if(j == nums.length)
					{
						//TODO
						//System.out.println("here: nums.length hit");
						while(i < j-1)
						{
							nums[i] = nums[i+1];
							i++;
						}
						nums[nums.length-1] = 0;
					}
					else
					{
						int temp = j-1;
						while(i < j-1)
						{
							nums[i] = nums[i+1];
							i++;
						}
						nums[temp] = 0;
					}

				}
			}
		}

	}

}
