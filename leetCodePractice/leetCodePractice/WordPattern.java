//this solution is accepted as of 12/31/2015
//Question credit to leetcode
//Author: Yunhe Liu <liu348@wisc.edu>
package leetCodePractice;
//import java.util.Arrays;

/*
 * This class works as:
 * Given a string of words and a string of pattern.
 * See whether the string matches the pattern. Return a boolean.
 * 
 * For example:
 * Input string words: "cat dog dog cat"
 * Input String pattern: "abba"
 * Output: True
 * 
 * Input string words: "dog dog dog dog"
 * Input String pattern: "abba"
 * Output: false
 */
public class WordPattern {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String abba = "cat dog dog cat";
		System.out.println(wordPattern("abba", abba));
		
	}
	
	public static boolean wordPattern(String pattern, String str) {
		//split pattern into char array
		//split word into string array
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
				//if pattern same and word different return false
				if(charList[i] == charList[j])
				{
					if(!wordList[i].equalsIgnoreCase(wordList[j]))
					{
						return false;
					}
				}
				
				//if pattern different and word same return false
				if(charList[i] != charList[j])
				{
					if(wordList[i].equalsIgnoreCase(wordList[j]))
					{
						return false;
					}
				}
			}
		}
		
		//otherwise the pattern and word list agrees
		return true;
    }

}
