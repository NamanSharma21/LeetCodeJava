package Datastructures;

public class TreeNode {

    /*
     * Tree Datastructure Documentation
     * 
     * https://www.geeksforgeeks.org/dsa/tree-data-structure/
     * 
     * Tree Traversal Documentation
     * 
     * https://www.geeksforgeeks.org/dsa/tree-traversals-inorder-preorder-and-postorder/
     */
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
