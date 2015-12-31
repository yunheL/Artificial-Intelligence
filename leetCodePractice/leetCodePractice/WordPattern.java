package leetCodePractice;
//import java.util.Arrays;

public class WordPattern {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String abba = "cat dog dog cat";
		System.out.println(wordPattern("abba", abba));
		
	}
	
	public static boolean wordPattern(String pattern, String str) {
		String[] wordList = str.split(" ");
		char[] charList = pattern.toCharArray();
		//int[] indexList = new int[wordList.length];
		
		//TODO - Test input split correct or not
		//System.out.println("Str: " + Arrays.toString(wordList));
		//System.out.println("Cha: " + Arrays.toString(charList));
		
		//if array length is wrong return false directly
		if(wordList.length != charList.length)
		{
			return false;
		}
		
		//loop to check
		int i = 0;
		int j = 0;
		
		for(;i<charList.length;i++)
		{
			for(j=i+1;j<charList.length;j++)
			{
				if(charList[i] == charList[j])
				{
					if(!wordList[i].equalsIgnoreCase(wordList[j]))
					{
						return false;
					}
				}
				
				if(charList[i] != charList[j])
				{
					if(wordList[i].equalsIgnoreCase(wordList[j]))
					{
						return false;
					}
				}
			}
		}
		
		return true;
    }

}
