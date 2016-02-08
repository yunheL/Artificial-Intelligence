package leetCodePractice;

public class BracketCheck {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "((";
		System.out.println(isValid(s));
	}
	
	public static boolean isValid(String s) {
        if(s.length() == 1)
            return false;
            
        int count0 = 0;
        int count1 = 0;
        int count2 = 0;
        
        int i = 0;
        for(; i<s.length(); i++)
        {
          if(s.charAt(i) == '(')
          {
        	  System.out.println("here");
              count0++;
              System.out.println(count0);
          }
          if(s.charAt(i) == ')')
          {
              count0--;
          }
          if(s.charAt(i) == '[')
          {
              count1++;
          }
          if(s.charAt(i) == ']')
          {
              count1--;
          }
          
          if(s.charAt(i) == '{')
          {
              count2++;
          }
          
          if(s.charAt(i) != '}')
          {
              count2--;
          }
          
          if(count0>1 || count1>1 || count2>1)
          {
              return false;
          }
        }
        return true;
    }

}
