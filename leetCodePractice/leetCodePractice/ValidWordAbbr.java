package leetCodePractice;

public class ValidWordAbbr {

	private String[] dictionary;

	public ValidWordAbbr(String[] dictionary) {
		this.dictionary = dictionary;
	}

	public boolean isUnique(String word) {
		StringBuilder abbr = new StringBuilder();
		//String abbrString = "";
		String first = "";
		String last = "";
		int num = 0;
		int i = 0;
		int count = 0;

		if(word.length() == 0)
			return true;

		if(dictionary == null)
			return true;

		if(dictionary.length == 1 && dictionary[0].equals(""))
			return true;

		if (word.length() == 1)
		{
			abbr.append(word);
			first = word;
			last = word;
		}
		else if (word.length() == 2)
		{
			abbr.append(word);
			first = word.substring(0,1);
			last = word.substring(1);
			num = 0;
		}
		else{
			abbr.append(word);
			first = word.substring(0,1);
			last = word.substring(word.length()-1);
			num = word.length()-2;
		}

		//abbrString = abbr.toString();

		for(i = 0; i < dictionary.length; i++)
		{
			if(!dictionary[i].equals(word))
			{
				if(first.equals(dictionary[i].substring(0,1)))
				{
					if(last.equals(dictionary[i].substring(dictionary[i].length()-1)))
					{
						if(word.length() == dictionary[i].length())
						{
							return false;
						}
					}
				}
			}

		}
		return true;
	}

}
