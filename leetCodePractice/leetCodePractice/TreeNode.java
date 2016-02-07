package leetCodePractice;

public class TreeNode {
	private int val;
	private TreeNode left;
	private TreeNode right;

	public TreeNode(int x)
	{ 
		this.val = x; 
		this.left = null;
		this.right = null;
	}

	public TreeNode getLeftNode()
	{
		return this.left;
	}
	
	public TreeNode getrightNode()
	{
		return this.right;
	}
	
	public int getVal()
	{
		return this.val;
	}
	
	public int getLeftVal()
	{
		return this.left.val;
	}
	
	public int getRightVal()
	{
		return this.right.val;
	}
	
	public void addLeftChild(TreeNode leftChild){
		this.left = leftChild;
	}
	
	public void addrightChild(TreeNode rightChild){
		this.right = rightChild;
	}
	
	public void printKids(){
		System.out.print(this.val);
		TreeNode[] childList = new TreeNode[2]; 
		childList[0] = this.left;
		childList[1] = this.right;
		
		if(childList[0] != null)
			left.printKids();
		if(childList[1] != null)
			right.printKids();
	}

}
