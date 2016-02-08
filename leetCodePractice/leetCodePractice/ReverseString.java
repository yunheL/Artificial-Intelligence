package leetCodePractice;

public class ReverseString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String toReverse = "This is an example";
		System.out.println(reverseString(toReverse));;
	}
	
	public static String reverseString(String toReverse){
	   StringBuilder rever = new StringBuilder();
       rever.append(toReverse);
       rever.trimToSize();
       rever.reverse();
       return rever.toString();  
	}

}
