import java.util.ArrayList;

import processing.core.*;
import processing.data.*;

//correctfilemark : this is the correct file to be hand in_10/21/15_8pm

public class Yunhe_Liu_DecisionTree extends DrawableTree
{
	public Yunhe_Liu_DecisionTree(PApplet p) { super(p); }

	// This method loads the examples from the provided filename, and
	// then builds a decision tree (stored in the inherited field: tree).
	// Each of the nodes in this resulting tree will be named after
	// either an attribute to split on (vote01, vote02, etc), or a party
	// classification (DEMOCRAT, REPUBLICAN, or possibly TIE).

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

	/*
	 * This method put string into groups according to attribute and opnion
	 * Opinion means YEA and NAY
	 */
	public void addChildrenToGroup(XML src, XML dest, String attribute, String opinion)
	{
		//base case for recursion
		if (src == null)	
			return;

		//The list contains all the children of the current
		//node.
		XML[] childrenList = src.getChildren();

		int i = 0;
		while(i < childrenList.length)
		{
			if(childrenList[i].getString(attribute).equalsIgnoreCase(opinion))
			{
				dest.addChild(childrenList[i]);
			}
			i++;
		}
	}

	//This method is used in calculatePostSplitEntropy() to
	//remove the Children who don't belong the current group.
	//The two groups are "YEA" group and "NAY" group.
	//The parameter opinion can either be "YEA" or "NAY"
	public void removeAllChildren(XML data)
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
			data.removeChild(childrenList[i]);
			i++;
		}
	}

	/*
	 * This method helps debugging display
	 */
	public void toDisplay(XML dataset)
	{
		XML[] childrenList = dataset.getChildren();

		int i = 0;
		for(i = 0; i < childrenList.length; i++)
		{
			System.out.println("Child[" + i +"] is:" + childrenList[i]);
		}
	}

	public void learnFromTrainingData(String filename)
	{
		// NOTE: Set the inherited field dirtyTree to true after building the
		// decision tree and storing it in the inherited field tree.  This will
		// trigger the DrawableTree's graphical rendering of the tree.

		XML root = new XML("root");
		tree = root;

		XML dataset = new XML("dataset");
		dataset = p.loadXML(filename);
		removeWhiteSpace(dataset);

		dirtyTree = true;

		recursiveBuildTree(dataset, tree);
		
		/*
		XML test = new XML("test");
		test = p.loadXML(filename);
		System.out.println("before remove all children " + test.toString());
		removeAllChildren(test);
		System.out.println("after remove all children " + test.toString());
		*/
	}

	// This method recursively builds a decision tree based on
	// the set of examples that are children of dataset.
	public void recursiveBuildTree(XML dataset, XML tree)
	{
		// NOTE: You MUST add YEA branches to your decision nodes before
		// adding NAY branches.  This will result in YEA branches being
		// child[0], which will be drawn to the left of any NAY branches.
		// The grading tests assume that you are following this convention.

		/*
		 * recursively split on the best attribute
		 * stop when:
		 * 1. entropy = 0, then pick that single party as the decision
		 * 2. keep splitting until cannot be split anymore, then pick the party with more
		 * examples as the decision
		 */

		//base case, either cannot be split anymore or entropy = 0
		XML YEATest = new XML("YEARoot");
		XML NAYTest = new XML("NAYRoot");
		
		String attriTest = "a";
		int attributeCount = 0;
		int i = 1;
		for(i = 1; i <= 16; i++)
		{
			//make sure to put a 0 in place which the attribute number is 
			//smaller than 10
			if(i < 10)
			{
				attriTest = "vote0" + i;
			}
			else
			{
				attriTest = "vote" + i;
			}
			
			addChildrenToGroup(dataset, YEATest, attriTest, "YEA");
			addChildrenToGroup(dataset, NAYTest, attriTest, "NAY");
		
			if((YEATest.getChildCount() == 0) || (NAYTest.getChildCount() == 0))
			{
				attributeCount++;
			}
			
			removeAllChildren(YEATest);
			removeAllChildren(NAYTest);
		}

		if ((dataset.getChildCount() < 2) || ((calculateEntropy(dataset)) == 0.0) || (attributeCount == 16))
		{
			String predict = plurality(dataset);
			XML result = new XML(predict);
			tree.addChild(result);
			result = tree.getChild(0);
			return;
		}

		
		String attribute = chooseSplitAttribute(dataset);

		//reduced dataset to be passed on
		XML YEAGroup = new XML("YEAGroup");
		XML NAYGroup = new XML("NAYGroup");

		//node to put into tree
		XML YEA = new XML(attribute+"YEA");
		XML NAY = new XML(attribute+"NAY");

		//split groups
		addChildrenToGroup(dataset, YEAGroup, attribute, "YEA");
		addChildrenToGroup(dataset, NAYGroup, attribute, "NAY");

		//link the nodes
		tree.addChild(YEA);
		tree.addChild(NAY);

		YEA = tree.getChild(0);
		NAY = tree.getChild(1);

		//recursive calls
		recursiveBuildTree(YEAGroup, YEA);
		recursiveBuildTree(NAYGroup, NAY);
	}

	// This method calculates and returns the mode (most common value) among
	// the party attributes of the children examples under dataset.  If there
	// happens to be an exact tie, this method returns "TIE".
	public String plurality(XML dataset)
	{
		XML[] childrenList = dataset.getChildren();
		int republicanCount = 0;
		int demoCount = 0;
		String plurality = "";

		//go through all children and count republican
		int i = 0;
		while(i < childrenList.length)
		{
			if(childrenList[i].getString("party").equalsIgnoreCase("REPUBLICAN"))
			{
				republicanCount++;
			}
			i++;
		}

		demoCount = childrenList.length - republicanCount;

		//determine which is the mode
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
		double lowestEntropy = 1.0;
		double currEntropy = 1.0;
		String attribute = "";
		String chosenAttribute = "vote01";

		//traverse through all 16 attributes
		int i = 1;
		for(i = 1; i <= 16; i++)
		{
			//make sure to put a 0 in place which the attribute number is 
			//smaller than 10
			if(i < 10)
			{
				attribute = "vote0" + i;
			}
			else
			{
				attribute = "vote" + i;
			}
			currEntropy = calculatePostSplitEntropy(attribute, dataset);

			//find the smallest entropy
			if (currEntropy < lowestEntropy)
			{
				lowestEntropy = currEntropy;
				chosenAttribute = attribute;
			}
		}
		//System.out.println(lowestEntropy);
		//System.out.println("here");
		return chosenAttribute;
	}

	// This method calculates and returns the entropy that results after 
	// splitting the children examples of dataset into two groups based
	// on their YEA vs. NAY value for the specified attribute.
	public double calculatePostSplitEntropy(String attribute, XML dataset)
	{		
		XML YEAGroup = new XML("YEAGroup");
		XML NAYGroup = new XML("NAYGroup");

		double YEAEntropy = 0.0;
		double NAYEntropy = 0.0;
		double YEAWeight = 0.0;
		double NAYWeight = 0.0;
		double totalEntropy = 0.0;

		//split children into groups based on YEA/NAY and attribute
		addChildrenToGroup(dataset, YEAGroup, attribute, "YEA");
		addChildrenToGroup(dataset, NAYGroup, attribute, "NAY");

		//calculate separate entropy
		YEAEntropy = calculateEntropy(YEAGroup);
		NAYEntropy = calculateEntropy(NAYGroup);

		//calculate group weight
		YEAWeight = ((double)(YEAGroup.getChildCount()))/((double)(dataset.getChildCount()));
		NAYWeight = ((double)(NAYGroup.getChildCount()))/((double)(dataset.getChildCount()));

		//get total entropy
		totalEntropy = YEAEntropy * YEAWeight + NAYEntropy * NAYWeight;

		return totalEntropy;
	}

	// This method calculates and returns the entropy for the children examples
	// of a single dataset node with respect to which party they belong to.
	public double calculateEntropy(XML dataset)
	{
		XML[] childrenList = dataset.getChildren();
		double republicanCount = 0.0;
		double republicanRate = 0.0;
		double entropyRepublican = 0.0;

		//go through the list and count republican
		int i = 0;
		while(i < childrenList.length)
		{
			if(childrenList[i].getString("party").equalsIgnoreCase("REPUBLICAN"))
			{
				republicanCount = republicanCount + 1.0;
			}
			i++;
		}

		//calculate entropy based on the probablity of being an republican
		republicanRate = republicanCount/((double)(childrenList.length));
		entropyRepublican = B(republicanRate);	
		return entropyRepublican;
	}

	// This method calculates and returns the entropy of a Boolean random 
	// variable that is true with probability q (as on page 704 of the text).
	// Don't forget to use the limit, when q makes this formula unstable.
	public static double B(double q)
	{
		//take care of special cases
		if(q==1)
		{
			return 0;
		}

		if(q==0)
		{
			return 0;
		}

		double entropy = 0.0;
		double supriseSuccess = 0.0;
		double supriseFail = 0.0;
		double success = q;
		double fail = 1.0-q;

		//calculate entropy based on the method mentioned on p704 on textbook
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
		String result = "guess";
		int correctCount = 0;
		double correctRate = 0.0;

		//load test data
		XML dataset = new XML("dataset");
		dataset = p.loadXML(filename);
		removeWhiteSpace(dataset);

		XML[] childrenList = dataset.getChildren();
		//System.out.print(childrenList.length);

		//go throught the list, compare predict with reality. Keep track of
		//correct count
		int i = 0;
		while(i < childrenList.length)
		{
			result = predict(childrenList[i], tree);
			if(result.equalsIgnoreCase(childrenList[i].getString("party")))
			{
				correctCount++;
			}
			i++;
		}
		//calculate correct ratio
		correctRate = (double)((double)(correctCount))/((double)(childrenList.length));

		return correctRate;
	}

	// This method runs a single example through the decision tree, and then 
	// returns the party that this tree predicts the example to belonging to.
	// If this example contains a party attribute, it should be ignored here.	
	public String predict(XML example, XML decisionTree)
	{
		String attribute = "abc";
		XML curr =  new XML("curr");

		//keep traversing the tree until reach a leaf
		curr = decisionTree;
		while(curr.getChild(0).getName().substring(0, 2).equalsIgnoreCase("vo"))
		{
			attribute = curr.getChild(0).getName().substring(0, 6);

			if(example.getString(attribute).equalsIgnoreCase("YEA"))
			{
				curr = curr.getChild(0);
			}
			else if(example.getString(attribute).equalsIgnoreCase("NAY"))
			{
				curr = curr.getChild(1);
			}
			else
			{
				System.out.println("Error: input file invalid");
				return "error";
			}

		}

		//determine the party based on the tree
		if(curr.getChild(0).getName().substring(0, 3).equalsIgnoreCase("REP") || curr.getChild(0).getName().substring(0, 3).equalsIgnoreCase("DEM") || curr.getChild(0).getName().substring(0, 3).equalsIgnoreCase("TIE"))
		{
			if(curr.getChild(0).getName().substring(0, 3).equalsIgnoreCase("REP"))
			{
				return "REPUBLICAN";
			}
			else if(curr.getChild(0).getName().substring(0, 3).equalsIgnoreCase("DEM"))
			{
				return "DEMOCRAT";
			}
			else if(curr.getChild(0).getName().substring(0, 3).equalsIgnoreCase("TIE"))
			{
				//System.out.println("here");
				return "TIE";
			}
			else
			{
				System.out.println("Error: input file invalid");
				return "error";
			}

		}
		else
		{
			System.out.println("Error: input file invalid");
			return "error";
		}
	}
}
