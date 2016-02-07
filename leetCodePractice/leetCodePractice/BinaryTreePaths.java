package leetCodePractice;

import java.util.List;

public class BinaryTreePaths {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreeNode root = new TreeNode(0);
		TreeNode left = new TreeNode(1);
		TreeNode right = new TreeNode(2);
		
		root.addLeftChild(left);
		root.addrightChild(right);
		
		root.printKids();
		//System.out.println(root.getVal());
	}
	
	 
	  
	public static class Solution {
	    public List<String> binaryTreePaths(TreeNode root) {
			
	        
	    	return null;
	    }
	}

}
