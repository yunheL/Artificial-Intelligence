package leetCodePractice;

public class ValidWordAbbr {
	private String[] dictionary;

    public ValidWordAbbr(String[] dictionary) {
        this.dictionary = dictionary;
    }

    public boolean isUnique(String word) {
    	if (word.length() > 2)
    	{
    		
    	}
    	
        String first = word.substring(0);
        String last = word.substring(word.length()-1);
        int num = word.length()-2;
        String abbr = first;
        
        
        return true;
    }

}
