import processing.core.PApplet;
import processing.data.XML;

public class Yunhe_Liu_Resolution extends DrawableTree
{	
	public Yunhe_Liu_Resolution(PApplet p, XML tree) 
	{ 
		super(p); 
		this.tree = tree; 
		dirtyTree = true;
	}

	/*
	 * This helper method removes all the children of the
	 * XML node passed in to the method
	 */
	public void removeAllChildren(XML data)
	{
		//base case for recursion
		if (data == null)	
			return;

		//The list contains all the children of the current
		//node.
		XML[] childrenList = data.getChildren();

		//remove all the children of the passed in XML node
		int i = 0;
		while(i < childrenList.length)
		{
			data.removeChild(childrenList[i]);
			i++;
		}
	}

	/*
	 * This helper method removes all the children that has the name "name" of the
	 * XML node passed in to the method
	 */
	public void removeSpecificChildren(XML data, String name)
	{
		//base case for recursion
		if (data == null)	
			return;

		//The list contains all the children of the current
		//node.
		XML[] childrenList = data.getChildren();

		//remove all the children of the passed in XML node
		int i = 0;
		while(i < childrenList.length)
		{
			//System.out.println(childrenList[i]);
			if(childrenList[i].getName().equalsIgnoreCase(name))
			{
				//System.out.print("here");
				data.removeChild(childrenList[i]);
			}
			i++;
		}
	}

	/*
	 * This helper method add child as the (index)th children of
	 * parent. Index is 0-based.
	 */
	public void addChildAt(XML parent, XML child, int index)
	{
		XML[] childrenList = parent.getChildren();

		removeAllChildren(parent);

		int i = 0;
		for(i = 0; i< index; i++)
		{
			parent.addChild(childrenList[i]);
		}
		parent.addChild(child);
		while(i<childrenList.length)
		{
			parent.addChild(childrenList[i]);
			i++;
		}
	}

	/*
	 * This helper recursively traverse through the tree
	 * and replace biconditions with two conditions
	 */
	public void traverseTreeRemoveBiconditions(XML curr)
	{
		//base case
		if(curr == null)
			return;

		XML[] childrenList = curr.getChildren();

		int i = 0;
		while(i < childrenList.length)
		{
			//if current node is bicondition
			if(childrenList[i].getName().equalsIgnoreCase("bicondition") )
			{
				//TODO - remove debug code
				//System.out.println("here");

				//keep traversing biconditional children list
				traverseTreeRemoveBiconditions(childrenList[i]);

				//remove bicondition
				//validation: make sure childrenList[i] has at least
				//two children
				if(childrenList[i].getChildCount() != 2)
				{
					System.out.println("remove bicondition children"
							+ " count error");
					return;
				}

				//get the left and right logic of the bicondition
				XML leftLogic = childrenList[i].getChild(0);
				XML rightLogic = childrenList[i].getChild(1);

				//eliminate biconditoin connect by "and"
				childrenList[i].setName("and");

				//remove all the children for reconstruction
				removeAllChildren(childrenList[i]);

				//add two conditon children
				childrenList[i].addChild("condition");
				childrenList[i].addChild("condition");

				//add logic to the left condition
				childrenList[i].getChild(0).addChild(leftLogic);
				childrenList[i].getChild(0).addChild(rightLogic);

				//add logic to the right condition
				childrenList[i].getChild(1).addChild(rightLogic);
				childrenList[i].getChild(1).addChild(leftLogic);
			}
			else{
				//keep traversing if childrenList[i] is not bicondition
				traverseTreeRemoveBiconditions(childrenList[i]);
			}
			//don't forget the increase counter!
			i++;
		}
	}

	/*
	 * This helper recursively traverse through the tree
	 * and replace conditions with corresponding logic
	 */
	public void traverseTreeRemoveConditions(XML curr){
		//base case
		if(curr == null)
			return;

		XML[] childrenList = curr.getChildren();

		int i = 0;
		while(i < childrenList.length)
		{
			//if current node is condition
			if(childrenList[i].getName().equalsIgnoreCase("condition") )
			{
				//TODO - remove debug code
				//System.out.println("here");

				//keep traversing conditional children list
				traverseTreeRemoveConditions(childrenList[i]);

				//remove condition
				//validation: make sure childrenList[i] has at least
				//two children
				if(childrenList[i].getChildCount() != 2)
				{
					System.out.println("remove condition children"
							+ " count error");
					return;
				}

				//get the left and right logic of the condition
				XML leftLogic = childrenList[i].getChild(0);
				XML rightLogic = childrenList[i].getChild(1);

				//eliminate condition connect by "or"
				childrenList[i].setName("or");

				//remove all the children for reconstruction
				removeAllChildren(childrenList[i]);

				//add two children of "or"
				childrenList[i].addChild("not");
				childrenList[i].addChild(rightLogic);

				//add left logic as the children of "not"
				childrenList[i].getChild(0).addChild(leftLogic);
			}
			else{
				//keep traversing if childrenList[i] is not condition
				traverseTreeRemoveConditions(childrenList[i]);
			}
			//don't forget the increase counter!
			i++;
		}
	}

	/*
	 * This helper recursively traverse through the tree
	 * and moves not correspondingly
	 */
	public void traverseTreeMoveNot(XML curr)
	{
		//base case
		if(curr == null)
			return;

		XML[] childrenList = curr.getChildren();

		int i = 0;
		while(i < childrenList.length)
		{
			//if current node is "not"
			if(childrenList[i].getName().equalsIgnoreCase("not") )
			{
				//TODO - remove debug code
				//System.out.println("here");

				//recursively processing the updated tree
				traverseTreeMoveNot(childrenList[i]);

				//move not
				//validation: make sure childrenList[i] has 1 children
				if(childrenList[i].getChildCount() != 1)
				{
					System.out.println("Move not children"
							+ " count error");
					return;
				}

				//TODO - double check referencing by pointer
				//get the child and parent of "not" (childList[i])
				XML childOfNot = childrenList[i].getChild(0);
				XML parent = childrenList[i].getParent();

				//if childOfNot is "not"
				if(childOfNot.getName().equalsIgnoreCase("not"))
				{
					XML grandChild = childOfNot.getChild(0);
					parent.removeChild(childrenList[i]);
					//parent.addChild(grandChild);
					addChildAt(parent, grandChild, i);
				}
				//if childOfNot is "and"
				else if(childOfNot.getName().equalsIgnoreCase("and"))
				{
					//get the left and right logic of childOfNot
					XML leftLogic = childOfNot.getChild(0);
					XML rightLogic = childOfNot.getChild(1);

					//eliminate condition connect by "or"
					childrenList[i].setName("or");

					//remove all the children for reconstruction
					removeAllChildren(childrenList[i]);

					//add two children of "or"
					childrenList[i].addChild("not");
					childrenList[i].addChild("not");

					//add left and right logic back
					childrenList[i].getChild(0).addChild(leftLogic);
					childrenList[i].getChild(1).addChild(rightLogic);

					//recursively processing the updated tree
					traverseTreeMoveNot(childrenList[i]);
				}
				//if childOfNot is "or"
				else if(childOfNot.getName().equalsIgnoreCase("or"))
				{	
					//get the left and right logic of childOfNot
					XML leftLogic = childOfNot.getChild(0);
					XML rightLogic = childOfNot.getChild(1);

					//eliminate condition connect by "or"
					childrenList[i].setName("and");

					//remove all the children for reconstruction
					removeAllChildren(childrenList[i]);

					//add two children of "or"
					childrenList[i].addChild("not");
					childrenList[i].addChild("not");

					//add left and right logic back
					childrenList[i].getChild(0).addChild(leftLogic);
					childrenList[i].getChild(1).addChild(rightLogic);

					//recursively processing the updated tree
					traverseTreeMoveNot(childrenList[i]);
				}
				/* 
				 * Never mind, it looks like it can be the literals as well.
				//else we have a problem
				else
				{
					System.out.println("Error in moving not, not child type");
					System.out.println("It is " + childOfNot.getName());
				}
				 */
			}
			else{
				//keep traversing if childrenList[i] is not "not"
				traverseTreeMoveNot(childrenList[i]);
			}
			//don't forget to increase counter!
			i++;
		}
	}

	/*
	 * This helper recursively traverse through the tree
	 * and replace conditions with corresponding logic
	 */
	public void traverseTreeDistributeOr(XML curr){
		//base case
		if(curr == null)
			return;

		XML[] childrenList = curr.getChildren();

		int i = 0;
		while(i < childrenList.length)
		{
			//if current node is or
			if(childrenList[i].getName().equalsIgnoreCase("or"))
			{
				//TODO - remove debug code
				//System.out.println("here");

				//keep traversing its children
				traverseTreeDistributeOr(childrenList[i]);

				//distribute or
				//validation: make sure childrenList[i] has at least
				//two children
				if(childrenList[i].getChildCount() != 2)
				{
					System.out.println("remove condition children"
							+ " count error");
					return;
				}

				//get the left and right logic of the condition
				XML leftLogic = childrenList[i].getChild(0);
				XML rightLogic = childrenList[i].getChild(1);

				//boolean leftFixed = false;

				//right child is "and"
				if(rightLogic.getName().equalsIgnoreCase("and"))
				{
					//System.out.println("Here1");
					XML leftGrandChild = childrenList[i].getChild(1).getChild(0);
					XML rightGrandChild = childrenList[i].getChild(1).getChild(1);

					//eliminate "or" connect by "and"
					childrenList[i].setName("and");

					//remove all the children for reconstruction
					removeAllChildren(childrenList[i]);

					//add two children of "or"
					childrenList[i].addChild("or");
					childrenList[i].addChild("or");

					//add child to left child
					childrenList[i].getChild(0).addChild(leftLogic);
					childrenList[i].getChild(0).addChild(leftGrandChild);

					//add child to right child
					childrenList[i].getChild(1).addChild(leftLogic);
					childrenList[i].getChild(1).addChild(rightGrandChild);

					//leftFixed = true;
					traverseTreeRemoveConditions(childrenList[i]);
				}
				//left child is "and"
				else if(leftLogic.getName().equalsIgnoreCase("and"))
				{
					//System.out.println("Here2");
					XML leftGrandChild = childrenList[i].getChild(0).getChild(0);
					XML rightGrandChild = childrenList[i].getChild(0).getChild(1);

					//eliminate "or" connect by "and"
					childrenList[i].setName("and");

					//remove all the children for reconstruction
					removeAllChildren(childrenList[i]);

					//add two children of "or"
					childrenList[i].addChild("or");
					childrenList[i].addChild("or");

					//add child to left child
					childrenList[i].getChild(0).addChild(leftGrandChild);
					childrenList[i].getChild(0).addChild(rightLogic);

					//add child to right child
					childrenList[i].getChild(1).addChild(rightGrandChild);
					childrenList[i].getChild(1).addChild(rightLogic);

					traverseTreeRemoveConditions(childrenList[i]);
				}

				//keep traversing both children in case original both
				//children were "and"
				//System.out.println("here");
				//System.out.println("Child0 is: " + childrenList[i].getChild(0));
				//System.out.println("child1 is: " + childrenList[i].getChild(1));
				//traverseTreeDistributeOr(childrenList[i].getChild(0));
				//traverseTreeDistributeOr(childrenList[i].getChild(1));
				//traverseTreeRemoveConditions(childrenList[i]);
			}
			else{
				//keep traversing if childrenList[i] is not condition
				traverseTreeRemoveConditions(childrenList[i]);
			}
			//don't forget the increase counter!
			i++;
		}
	}

	public void traverseTreeCreateNNary(XML curr, String name)
	{
		//System.out.println("here");
		//base case
		if(curr == null)
			return;

		XML[] childrenList = curr.getChildren();
		//System.out.println("here0");
		int i = 0;
		while(i < childrenList.length)
		{
			//System.out.println("here1");
			//if current node is bicondition
			if(childrenList[i].getName().equalsIgnoreCase(name) )
			{
				//System.out.println(i);

				//TODO - remove debug code
				//System.out.println("here2");

				//keep traversing recusively
				traverseTreeCreateNNary(childrenList[i], name);

				//create N-nary
				//validation: make sure childrenList[i] has at least
				//two children
				/*
				if(childrenList[i].getChildCount() < 2)
				{
					System.out.println("remove bicondition children"
							+ " count error");
					return;
				}
				 */

				XML[] subChildrenList = childrenList[i].getChildren();

				int j = 0;
				while(j < subChildrenList.length)
				{
					if(subChildrenList[j].getName().equalsIgnoreCase(name))
					{
						//TODO - remove debug code
						//System.out.println("here3");
						XML[] grandChildrenList = subChildrenList[i].getChildren();

						int k = 0;
						for(k = 0; k<grandChildrenList.length; k++)
						{
							childrenList[i].addChild(grandChildrenList[k]);
						}

					}
					j++;
				}
				removeSpecificChildren(childrenList[i], name);

			}
			else{
				//keep traversing if childrenList[i] is not bicondition
				traverseTreeCreateNNary(childrenList[i], name);
			}
			//don't forget the increase counter!
			i++;
		}
	}

	public void traverseTreeRemoveReduendentLiteral(XML curr)
	{
		//base case
		if(curr == null)
			return;

		XML[] childrenList = curr.getChildren();

		int i = 0;
		while(i < childrenList.length)
		{
			//if current node is clause
			if(childrenList[i].getName().equalsIgnoreCase("or") )
			{
				//TODO - remove debug code
				//System.out.println("here");

				//keep traversing
				traverseTreeRemoveReduendentLiteral(childrenList[i]);

				//remove bicondition
				XML[] subChildrenList = childrenList[i].getChildren();

				int j = 0;
				while(j < subChildrenList.length)
				{
					if(subChildrenList[j].getName().equalsIgnoreCase("not"))
					{
						childrenList[i].removeChild(subChildrenList[j]);
						if(!clauseContainsLiteral(childrenList[i], getAtomFromLiteral(subChildrenList[j]), true))
						{
							childrenList[i].addChild(subChildrenList[j]);
						}
					}
					else
					{
						childrenList[i].removeChild(subChildrenList[j]);
						if(!clauseContainsLiteral(childrenList[i], getAtomFromLiteral(subChildrenList[j]), false))
						{
							childrenList[i].addChild(subChildrenList[j]);
						}
					}
				}
			}
			else{
				//keep traversing if childrenList[i] is not bicondition
				traverseTreeRemoveReduendentLiteral(childrenList[i]);
			}
			//don't forget the increase counter!
			i++;
		}
	}

	public void eliminateBiconditions()
	{
		// TODO - Implement the first step in converting logic in tree to CNF:
		// Replace all biconditions with truth preserving conjunctions of conditions.

		//TODO - remove debug code
		//System.out.println("tree is " + tree);

		//call helper method to eliminate Biconditions
		//TODO - Debug code reverse
		traverseTreeRemoveBiconditions(tree);



		//TODO - Debug Code
		//traverseTreeCreateNNary(tree);

		//set dirtyTree flag for display
		dirtyTree = true;

		/*
		//TODO - Debug code
		XML neg = new XML("not");
		XML pos = new XML("yes");
		neg.addChild(pos);
		System.out.println("Is negated?: " + isLiteralNegated(pos));
		System.out.println("Atom fom neg: " + getAtomFromLiteral(neg));
		System.out.println("Atom fom pos: " + getAtomFromLiteral(pos));
		 */

		/*
		//TODO - Debug code
		XML Clause = new XML ("or");
		XML child0 = new XML("a");
		XML neg = new XML("not");
		XML child1 = new XML("a");
		Clause.addChild(child0);
		neg.addChild(child1);
		Clause.addChild(neg);

		System.out.println("should be true: " + clauseIsTautology(Clause));

		//test case need to be revised to test differenment methods
		System.out.println("should be false: " + clauseContainsLiteral(Clause, "a", true));
		System.out.println("should be true: " + clauseContainsLiteral(Clause, "a", false));
		System.out.println("should be true: " + clauseContainsLiteral(Clause, "b", true));
		System.out.println("should be false: " + clauseContainsLiteral(Clause, "a", true));
		System.out.println("should be false: " + clauseContainsLiteral(Clause, "c", true));
		System.out.println("should be false: " + clauseContainsLiteral(Clause, "c", false));

		XML set = new XML ("and");
		XML clause1 = new XML ("or");
		XML clause2 = new XML ("or");
		XML liter1 = new XML ("a");
		XML liter2 = new XML ("b");
		XML liter3 = new XML ("c");
		XML liter4 = new XML ("d");
		XML neg = new XML("not");
		clause1.addChild(liter1);
		clause1.addChild(liter2);
		clause2.addChild(liter3);
		neg.addChild(liter4);
		clause2.addChild(neg);
		set.addChild(clause1);
		set.addChild(clause2);

		XML neg2 = new XML("not");
		XML clause3 = new XML ("or");
		XML clause4 = new XML ("or");
		XML clause5 = new XML ("or");
		XML clause6 = new XML ("or");
		XML clause7 = new XML ("or");
		XML clause8 = new XML ("or");

		XML liter5 = new XML ("a");
		XML liter6 = new XML ("b");
		XML liter7 = new XML ("c");
		XML liter8 = new XML ("d");
		XML liter9 = new XML ("f");

		neg2.addChild(liter8);
		clause3.addChild(liter6);
		clause3.addChild(liter5);

		clause4.addChild(liter5);
		clause4.addChild(liter6);

		clause5.addChild(liter5);
		clause5.addChild(liter7);

		clause6.addChild(liter5);
		clause6.addChild(liter9);

		clause7.addChild(liter7);
		clause7.addChild(neg2);



		System.out.println("set is: " + set.toString());
		System.out.println("clause7 is: " + clause7.toString());

		System.out.println("should be true: " + setContainsClause(set, clause3));
		System.out.println("should be true: " + setContainsClause(set, clause4));
		System.out.println("should be false: " + setContainsClause(set, clause5));
		System.out.println("should be false: " + setContainsClause(set, clause6));
		System.out.println("should be true: " + setContainsClause(set, clause7));
		 */
	}	

	public void eliminateConditions()
	{
		// TODO - Implement the second step in converting logic in tree to CNF:
		// Replace all conditions with truth preserving disjunctions.

		//call helper method to eliminate conditions
		traverseTreeRemoveConditions(tree);

		//set dirtyTree flag for display
		dirtyTree = true;
	}

	public void moveNegationInwards()
	{
		// TODO - Implement the third step in converting logic in tree to CNF:
		// Move negations in a truth preserving way to apply only to literals.

		//call helper method to eliminate conditions
		traverseTreeMoveNot(tree);

		//set dirtyTree flag for display
		dirtyTree = true;
	}

	public void distributeOrsOverAnds()
	{
		// TODO - Implement the fourth step in converting logic in tree to CNF:
		// Move negations in a truth preserving way to apply only to literals.

		//call helper method to eliminate conditions
		traverseTreeDistributeOr(tree);

		//set dirtyTree flag for display
		dirtyTree = true;
	}

	public void collapse()
	{
		// TODO - Clean up logic in tree in preparation for Resolution:
		// 1) Convert nested binary ands and ors into n-ary operators so
		// there is a single and-node child of the root logic-node, all of
		// the children of this and-node are or-nodes, and all of the
		// children of these or-nodes are literals: either atomic or negated	
		// 2) Remove redundant literals from every clause, and then remove
		// redundant clauses from the tree.
		// 3) Also remove any clauses that are always true (tautologies)
		// from your tree to help speed up resolution.

		//call helper method to eliminate conditions
		//attention that the sequence of or first then and is important
		traverseTreeCreateNNary(tree, "or");
		traverseTreeCreateNNary(tree, "and");

		//traverseTreeRemoveReduendentLiteral(tree);
		//System.out.println(tree);

		//removeSpecificChildren(tree, "and");
		//set dirtyTree flag for display
		dirtyTree = true;


	}	

	public boolean applyResolution()
	{
		// TODO - Implement resolution on the logic in tree.  New resolvents
		// should be added as children to the only and-node in tree.  This
		// method should return true when a conflict is found, otherwise it
		// should only return false after exploring all possible resolvents.
		// Note: you are welcome to leave out resolvents that are always
		// true (tautologies) to help speed up your search.
		return false;
	}

	public XML resolve(XML clause1, XML clause2)
	{
		// TODO - Attempt to resolve these two clauses and return the resulting
		// resolvent.  You should remove any redundant literals from this 
		// resulting resolvent.  If there is a conflict, you will simply be
		// returning an XML node with zero children.  If the two clauses cannot
		// be resolved, then return null instead.
		
		
		
		return null;
	}	

	// REQUIRED HELPERS: may be helpful to implement these before collapse(), applyResolution(), and resolve()
	// Some terminology reminders regarding the following methods:
	// atom: a single named proposition with no children independent of whether it is negated
	// literal: either an atom-node containing a name, or a not-node with that atom as a child
	// clause: an or-node, all the children of which are literals
	// set: an and-node, all the children of which are clauses (disjunctions)

	public boolean isLiteralNegated(XML literal) 
	{ 
		// TODO - Implement to return true when this literal is negated and false otherwise.
		if(literal.getName().equalsIgnoreCase("not"))
		{
			return true;
		}

		return false; 

	}

	public String getAtomFromLiteral(XML literal) 
	{ 
		// TODO - Implement to return the name of the atom in this literal as a string.

		if(isLiteralNegated(literal))
		{
			return literal.getChild(0).getName();
		}
		else
		{
			return literal.getName();
		}
	}	

	public boolean clauseContainsLiteral(XML clause, String atom, boolean isNegated)
	{
		// TODO - Implement to return true when the provided clause contains a literal
		// with the atomic name and negation (isNegated).  Otherwise, return false.	
		XML[] childrenList = clause.getChildren();
		//System.out.println(childrenList[0]);
		//System.out.println(childrenList[1]);
		//System.out.println(childrenList[1].getChild(0));
		if(!isNegated)
		{
			int i = 0;
			while(i < childrenList.length)
			{
				if(childrenList[i].getName().equalsIgnoreCase(atom))
				{
					return true;
				}
				i++;
			}
		}
		else
		{
			int i = 0;
			while(i < childrenList.length)
			{
				if(childrenList[i].getName().equalsIgnoreCase("not"))
				{
					//System.out.println("childrenList[i] is: " + childrenList[i].getName());
					//System.out.println("its child[0] is: " + childrenList[i].getChild(0));

					if(childrenList[i].getChild(0) != null)
					{
						if(childrenList[i].getChild(0).getName().equalsIgnoreCase(atom))
						{
							return true;
						}
					}
					else
					{
						System.out.println("Error in clauseContainsLiteral");
					}
				}
				i++;
			}
		}
		return false;
	}

	public boolean setContainsClause(XML set, XML clause)
	{
		// TODO - Implement to return true when the set contains a clause with the
		// same set of literals as the clause parameter.  Otherwise, return false.
		XML[] childrenList = set.getChildren();
		//System.out.println("childrenList length is: "+ childrenList.length);
		boolean contains = false;

		int i = 0;
		while(i < childrenList.length)
		{
			XML[] subChildrenList = childrenList[i].getChildren();
			XML[] clauseChildrenList = clause.getChildren();

			//System.out.println("subChildrenList length is: "+subChildrenList.length);
			//System.out.println("clauseChildrenList length is: "+clauseChildrenList.length);

			if(subChildrenList.length == clauseChildrenList.length)
			{
				int j = 0;
				while(j < subChildrenList.length)
				{
					if(isLiteralNegated(subChildrenList[j]))
					{
						contains = clauseContainsLiteral(clause, getAtomFromLiteral(subChildrenList[j]), true);
					}
					else
					{
						contains = clauseContainsLiteral(clause, getAtomFromLiteral(subChildrenList[i]), false);
					}

					if(contains == false)
					{
						break;
					}

					j++;
				}
			}
			else
			{
				contains = false;
			}

			if(contains == true)
			{
				return true;
			}

			i++;
		}

		return false;
	}

	public boolean clauseIsTautology(XML clause)
	{
		// TODO - Implement to return true when this clause contains a literal
		// along with the negated form of that same literal.  Otherwise, return false.
		XML[] childrenList = clause.getChildren();
		boolean isTautology = false;

		int i = 0;
		while(i < childrenList.length)
		{
			if(isLiteralNegated(childrenList[i]))
			{
				isTautology = clauseContainsLiteral(clause, getAtomFromLiteral(childrenList[i]), false);
			}
			else
			{
				isTautology = clauseContainsLiteral(clause, getAtomFromLiteral(childrenList[i]), true);
			}

			if(isTautology == true)
			{
				return true;
			}

			i++;
		}
		return false;
	}

}
