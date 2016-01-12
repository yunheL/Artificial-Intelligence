/*
 * Question credit to "Cracking the coding
 * Interview" 4th edition by Laakmann.
 */
package leetCodePractice;

/*
 * Given a time, this class returns the angle between
 * the time hand and hour hand in degrees.
 * Time is formatted as a integer pair.
 * e.g. Given 12:00am, return 0
 * 		Given 9:00opm return 90
 */


/*
 * TODO - Further thinkings
 * What if the time is given as "String:String"
 */
public class analogClockAngle {

	public static void main(String[] args) {
		test();
	}
	
	public static void test()
	{
		int passCount = 0;
		int failCount = 0;
		
		float[] expected = new float[]{30,180, 0, 90, 
				(float)(82.5), (float)(90+22.5), (float)(22.5)};
		float[] result = new float[]{solution(1,0), solution(6,0), 
				solution(12,0),solution(21, 0),solution(0, 15),
				solution(0, 45), solution(21, 45)};
		int i = 0;
		for(; i<result.length; i++)
		{
			if(result[i] == expected[i])
			{
				System.out.print("Passed! ");
				passCount++;
			}
			else
			{
				System.out.print("Failed! ");
				failCount++;
			}
			System.out.println("Expected: " + expected[i] 
					+ " result = " + result[i]);
		}
		
		System.out.println("=====Test Summary=====");
		if(passCount == result.length)
			System.out.println("Congrats! All passed! ^_^");
		else
			System.out.println("Oops! Some case did not pass! >_<");
		
		System.out.print("Passed: " + passCount + " Failed: " + failCount);
	}
	
	public static void error(String msg)
	{
		System.out.println("Error" + msg);
	}
	
	public static float solution(int hr, int min){
		
		//input validation
		if(min > 59 || min < 0)
		{
			error("invalid param: min outOfBound");
			return -1;
		}
		
		if(hr>24 || hr < 0)
		{
			error("invalid param: hr outOfBound");
			return -1;
		}
		else if(hr > 11)
		{
			hr = hr - 12;
		}
		
		//declare variables
		float hrAngle;
		float minAngle;
		float diff;
		
		//avoiding magic numbers 
		final float aFullCircle = 360;
		final float minutes = 60;
		final float hours = 12;
		final float degreePerMinute = aFullCircle/minutes;
		final float degreePerHour = aFullCircle/hours;
		
		//calculate min hand angle
		minAngle = min * degreePerMinute;
		
		//calculate hr hand angel (hr value + minute-related offset)
		hrAngle = hr * degreePerHour + min * degreePerMinute/hours;
		
		//test
		//System.out.println("hourAngelIs: " + hrAngle);
		
		//get the difference
		diff = hrAngle - minAngle;
		if(diff<0)
			diff = - diff;
		
		if (diff>180)
			diff = 360 - diff;
		
		//return the correct value
		//System.out.println(diff);
		return diff;
	}

}
