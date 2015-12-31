package leetCodePractice;
import java.util.Arrays;

public class MoveZeros {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = new int[]{0,1,0,3,12};
		
		moveZeroes(nums);
		System.out.print(Arrays.toString(nums));
	}

	public static void moveZeroes(int[] nums) {
		int i = nums.length-1;
		for(;i>-1;i--)
		{
			if(nums[i] == 0)
			{
				if(i == nums.length-1)
				{
					//do nothing
				}
				else if(nums[i+1] == 0)
				{
					//do nothing
				}
				else
				{
					int j = i;
					while(nums[j] != 0)
					{
						j++;
						if(j == nums.length)
						{
							break;
						}
					}
					if(j == nums.length)
					{
						while(j > i+1)
						{
							nums[j-2] = nums[j-1];
							j--;
						}
						nums[nums.length-1] = 0;
					}
					else
					{
						int temp = j-1;
						while(j > i+1)
						{
							nums[j-2] = nums[j-1];
							j--;
						}
						nums[temp] = 0;
					}

				}
			}
		}

	}

}
