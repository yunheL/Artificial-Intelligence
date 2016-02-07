package leetCodePractice;

public class SubstringLearningExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String test = "This is a test";
		String whatWeLearned = "If only beginIndex is provided, substring()"
				+ "will be starting from the beginIndex to the end of the "
				+ "String";
		
		String onlyBeginIndex0 = test.substring(2);
		String onlyBeginIndex1 = test.substring(5);
		
		String bothIndex0 = test.substring(2,5);
		String bothIndex1 = test.substring(2,3);
		
		System.out.println("onlyBeginIndex0: " + onlyBeginIndex0);
		System.out.println("onlyBeginIndex1: " + onlyBeginIndex1);
		System.out.println("bothIndex0: " + bothIndex0);
		System.out.println("bothIndex1: " + bothIndex1);
		
		System.out.println("What we learned:\n" + whatWeLearned);
		
		System.out.println("===========================");
		String part0 = "ab";
		String part1 = "cd";
		String combine = part0 + part1;
		System.out.println("combine is: "+ combine);
		System.out.println("Apparently, we can use "+" operator to "
				+ "combine strings");
	}

}
