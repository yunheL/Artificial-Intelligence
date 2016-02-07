package leetCodePractice;

public class StringBuilderLearning {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String test = "it";
		String test1 = "AB";
		String test2 = "AB";
		String test3 = " FG";
		StringBuilder result = new StringBuilder();
		StringBuilder result1 = new StringBuilder();
		
		
		result = result.append(test);
		result = result.append(test1);
		result = result.append(test2);
		result = result.append(test3);
		
		result1 = result1.append(test).append(test1).append(test2).append(test3);
		
		System.out.println(result);
		System.out.println(result1.toString());
		
		System.out.println(test1.equals(test2));
		System.out.println(result.toString().equals(result1.toString()));
		
		System.out.println(result.capacity());
		System.out.println(result.charAt(2));
		System.out.println(result.codePointAt(3)); //return ascii seq in decimal
		
		result.ensureCapacity(100);
		System.out.println(result.capacity());
		System.out.println(result.lastIndexOf(test2));
		
		result.trimToSize();
		System.out.println(result.capacity());
		
		System.out.println(result1.capacity());
		System.out.println(result1.length());
		
		System.out.println(result1.reverse());
		
	}

}
