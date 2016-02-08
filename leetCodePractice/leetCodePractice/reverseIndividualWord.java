package leetCodePractice;

public class reverseIndividualWord {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input = "I love you";
		System.out.println(reverseIndvidualWord(input));

	}

	public static String reverseIndvidualWord(String input){
		String[] inputArr = input.split(" ");
		StringBuilder ret = new StringBuilder();

		int i = 0;
		for(; i < inputArr.length; i++)
		{
			ret.append(reverseString(inputArr[i]) + " ");
		}

		return ret.toString();
	}

	public static String reverseString(String toReverse){
		StringBuilder rever = new StringBuilder();
		rever.append(toReverse);
		rever.trimToSize();
		rever.reverse();
		return rever.toString();  
	}
}