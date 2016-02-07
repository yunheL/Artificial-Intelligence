package leetCodePractice;

import java.util.ArrayList;
import java.util.List;

public class arrayRange {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] input = new int[]{0};
		System.out.print(summaryRangesLinear(input));
	}
	
	public static List<String> summaryRangesBinary(int[] nums) {
		List<String> range = new ArrayList<String>();
		
		//figure out how many numbers are missing, for later optimize
		//int missNum = nums[nums.length] - nums[0] + 1 - nums.length;
		
		
		//this is linear search implementation, binary search implementation
		//is also coming
		//figure out 1 range
		String st = "";
		String ed = "";
		String arrow = "->";
		String interval = "";
		
		int i = 0;
		int j = 0;
		int lo = 0;
		int hi = nums.length;
		int mid = nums.length/2;
		
		//if(nums)
		
		
		
		
	    return range;
	}

	
	public static List<String> summaryRangesLinear(int[] nums) {
		List<String> range = new ArrayList<String>();
		
		//figure out how many numbers are missing, for later optimize
		//int missNum = nums[nums.length] - nums[0] + 1 - nums.length;
		
		
		//this is linear search implementation, binary search implementation
		//is also coming
		//figure out 1 range
		int i = 0;
		int j = 0;
		int start = 0;
		int end = 0;
		String st = "";
		String ed = "";
		String arrow = "->";
		String interval = "";
		
		if(nums.length == 0)
		{
			return range;
		}
		
		if(nums.length == 1)
		{
			range.add(String.valueOf(nums[0]));
			return range;
		}
		
		//TODO - change this range
		while(i < nums.length-1)
		{
			//record start index
			start = i;
			
			//set the incrementer
			j = i;
			
			while((nums[j] + 1) == nums[j+1])
			{
				if(j < nums.length-2)
					j++;
				else
				{
					j++;
					break;
				}
			}
			//stop when the array is not consecutive anymore
			//record the end value
			end = j;
			
			//put the incrementer at the next spot
			j++;
			
			if(start == end)
			{
				range.add(Integer.toString(nums[start]));
			}
			else{
				st = String.valueOf(nums[start]);
				ed = String.valueOf(nums[end]);
				interval = st + arrow + ed;
				range.add(interval);
			}
			i = j;
			//System.out.println(i);
		}
		
		if(nums[nums.length-2] + 1 != nums[nums.length-1])
		{
			range.add(Integer.toString(nums[nums.length-1]));
		}
		
	    return range;
	}
}
