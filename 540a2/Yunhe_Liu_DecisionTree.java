import java.util.ArrayList;

import processing.core.*;
import processing.data.*;

public class Yunhe_Liu_DecisionTree extends DrawableTree
{
	public Yunhe_Liu_DecisionTree(PApplet p) { super(p); }

	// This method loads the examples from the provided filename, and
	// then builds a decision tree (stored in the inherited field: tree).
	// Each of the nodes in this resulting tree will be named after
	// either an attribute to split on (vote01, vote02, etc), or a party
	// classification (DEMOCRAT, REPUBLICAN, or possibly TIE).

	//data contain in the data file
	//private XML data;

	/*
	 * This is a helper method that I wrote to recursive
	 * traverse through the tree and remove white spaces.
	 */
	public void removeWhiteSpace(XML data)
	{
		//base case for recursion
		if (data == null)	
			return;

		//The list contains all the children of the current
		//node.
		XML[] childrenList = data.getChildren();

		//traverse through all the nodes within the list and
		//remove white space and do it recursively
		int i = 0;
		while(i < childrenList.length)
		{
			//if the node is whitespace, then remove it
			if (childrenList[i].getName() == "#text")
			{
				data.removeChild(childrenList[i]);
			}
			//otherwise recursively traverse based on this node
			else{
				removeWhiteSpace(childrenList[i]);
			}
			i++;
		}
	}

	public void learnFromTrainingData(String filename)
	{
		// NOTE: Set the inherited field dirtyTree to true after building the
		// decision tree and storing it in the inherited field tree.  This will
		// trigger the DrawableTree's graphical rendering of the tree.

		// TODO - implement this method
		tree = p.loadXML(filename);
		removeWhiteSpace(tree);
		
		XML[] childrenList = tree.getChildren();
		
		//System.out.print(childrenList[0].getString("party"));
		dirtyTree = true;
		
		/*
		int[] test = {1,2,3};
		System.out.println();
		System.out.println(test.length);
		*/
		//int i = 4;
		//System.out.println("vote0" + i);
		//System.out.println("here");
		//System.out.println(chooseSplitAttribute(tree));
		//System.out.println(B(0.5));//passsed
		//System.out.println(calculateEntropy(tree));//inifinite loop
		//System.out.println(childrenList.length);
		System.out.println(calculateEntropy(tree));
	}

	// This method recursively builds a decision tree based on
	// the set of examples that are children of dataset.
	public void recursiveBuildTree(XML dataset, XML tree)
	{
		// NOTE: You MUST add YEA branches to your decision nodes before
		// adding NAY branches.  This will result in YEA branches being
		// child[0], which will be drawn to the left of any NAY branches.
		// The grading tests assume that you are following this convention.

		// TODO - implement this method
		/*
		 * recursively split on the best attribute
		 * stop when:
		 * 1. entropy = 0, then pick that single party as the decision
		 * 2. finished all the 16 attributes, then pick the party with more
		 * examples as the decision
		 */

	}

	// This method calculates and returns the mode (most common value) among
	// the party attributes of the children examples under dataset.  If there
	// happens to be an exact tie, this method returns "TIE".
	public String plurality(XML dataset)
	{
		// TODO - implement this method
		XML[] childrenList = dataset.getChildren();
		int republicanCount = 0;
		int demoCount = 0;
		String plurality = "";
		
		int i = 0;
		while(i < childrenList.length)
		{
			if(childrenList[i].getString("party") == "REPUBLICAN")
			{
				republicanCount++;
			} 
		}
		
		demoCount = childrenList.length - republicanCount;
		
		if(republicanCount > demoCount)
		{
			plurality = "REPUBLICAN";
		}
		else if(republicanCount == demoCount)
		{
			plurality = "TIE";
		}
		else
		{
			plurality = "DEMOCRAT";
		}
		
		return plurality;
	}

	// This method calculates and returns the name of the attribute that results
	// in the lowest entropy, after splitting all children examples according
	// to their value for this attribute into two separate groups: YEA vs. NAY.	
	public String chooseSplitAttribute(XML dataset)
	{
		// TODO - implement this method
		double lowestEntropy = 1.0;
		double currEntropy = 1.0;
		String attribute = "";
		String chosenAttribute = "vote01";
		
		int i = 1;
		for(i = 1; i <= 16; i++)
		{
			if(i < 10)
			{
				attribute = "vote0" + i;
			}
			else
			{
				attribute = "vote" + i;
			}
			currEntropy = calculatePostSplitEntropy(attribute, dataset);
			
			if (currEntropy < lowestEntropy)
			{
				lowestEntropy = currEntropy;
				chosenAttribute = attribute;
			}
		}
		
		return chosenAttribute;
	}

	// This method calculates and returns the entropy that results after 
	// splitting the children examples of dataset into two groups based
	// on their YEA vs. NAY value for the specified attribute.
	public double calculatePostSplitEntropy(String attribute, XML dataset)
	{		
		// TODO - implement this method
		XML[] childrenList = dataset.getChildren();
		ArrayList<XML> YEAList = new ArrayList<XML>();
		double YEACount = 0.0;
		double YEARate = 0.0;
		double entropyYEA = 0.0;
		
		//ArrayList<XML> NAYList = new ArrayList<XML>();
		
		int i = 0;
		while(i < childrenList.length)
		{
			if(childrenList[i].getString(attribute) == "YEA")
			{
				YEAList.add(childrenList[i]);
			} 
		}
		
		YEACount = (double)(YEAList.size());
		YEARate = (double)(YEACount/((double)(childrenList.length)));
		entropyYEA = B(YEARate);
		System.out.println("here");
		return entropyYEA;
	}

	// This method calculates and returns the entropy for the children examples
	// of a single dataset node with respect to which party they belong to.
	public double calculateEntropy(XML dataset)
	{
		// TODO - implement this method
		XML[] childrenList = dataset.getChildren();
		double republicanCount = 0.0;
		double republicanRate = 0.0;
		double entropyRepublican = 0.0;
		
		int i = 0;
		while(i < childrenList.length)
		{
			if(childrenList[i].getString("party") == "REPUBLICAN")
			{
				republicanCount = republicanCount + 1.0;
			}
		}

		republicanRate = republicanCount/((double)(childrenList.length));
		entropyRepublican = B(republicanRate);	
		return entropyRepublican;
	}

	// This method calculates and returns the entropy of a Boolean random 
	// variable that is true with probability q (as on page 704 of the text).
	// Don't forget to use the limit, when q makes this formula unstable.
	public static double B(double q)
	{
		// TODO - implement this method
		if(q==1)
		{
			return 0;
		}
		
		double entropy = 0.0;
		double supriseSuccess = 0.0;
		double supriseFail = 0.0;
		double success = q;
		double fail = 1.0-q;

		supriseSuccess = (-1) * success * (Math.log(success)/Math.log(2));
		supriseFail = (-1) * fail * (Math.log(fail)/Math.log(2));

		entropy = supriseSuccess + supriseFail;

		return entropy;
	}

	// This method loads and runs an entire file of examples against the 
	// decision tree, and returns the percentage of those examples that this
	// decision tree correctly predicts.
	public double runTests(String filename)
	{
		// TODO - implement this method
		return 0.0;
	}

	// This method runs a single example through the decision tree, and then 
	// returns the party that this tree predicts the example to belonging to.
	// If this example contains a party attribute, it should be ignored here.	
	public String predict(XML example, XML decisionTree)
	{
		// TODO - implement this method
		return "";
	}
}
