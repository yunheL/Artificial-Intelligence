package leetCodePractice;

public class UniqueWordAbbre {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] dictionary = new String[]{"dig"};
		
		ValidWordAbbr vwa = new ValidWordAbbr(dictionary);
		System.out.println(vwa.isUnique("dog"));
		

	}
}
