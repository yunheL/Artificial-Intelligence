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


	public void eliminateBiconditions()
	{
		// TODO - Implement the first step in converting logic in tree to CNF:
		// Replace all biconditions with truth preserving conjunctions of conditions.

		//TODO - remove debug code
		//System.out.println("tree is " + tree);

		//call helper method to eliminate Biconditions
		traverseTreeRemoveBiconditions(tree);

		//set dirtyTree flag for display
		dirtyTree = true;
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
		return false; 
	}

	public String getAtomFromLiteral(XML literal) 
	{ 
		// TODO - Implement to return the name of the atom in this literal as a string.
		return "";
	}	

	public boolean clauseContainsLiteral(XML clause, String atom, boolean isNegated)
	{
		// TODO - Implement to return true when the provided clause contains a literal
		// with the atomic name and negation (isNegated).  Otherwise, return false.		
		return false;
	}

	public boolean setContainsClause(XML set, XML clause)
	{
		// TODO - Implement to return true when the set contains a clause with the
		// same set of literals as the clause parameter.  Otherwise, return false.
		return false;
	}

	public boolean clauseIsTautology(XML clause)
	{
		// TODO - Implement to return true when this clause contains a literal
		// along with the negated form of that same literal.  Otherwise, return false.
		return false;
	}	

}
