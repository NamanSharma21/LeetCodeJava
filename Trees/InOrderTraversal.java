package Trees;

import Datastructures.TreeNode;

public class InOrderTraversal {
    public static void main(String[] args) {
        InOrderTraversal inOrderTraversal = new InOrderTraversal();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);
        inOrderTraversal.inOrderTraversal(root);
    }

    /*
     * Inorder traversal is a depth-first traversal method that follows this
     * sequence:
     * 
     * Left subtree is visited first.
     * Root node is processed next.
     * Right subtree is visited last.
     * 
     * https://www.geeksforgeeks.org/dsa/inorder-traversal-of-binary-tree/
     */

    public void inOrderTraversal(TreeNode root) {
        if (root == null)
            return;
        inOrderTraversal(root.left);
        System.out.print("" + root.val);
        inOrderTraversal(root.right);
    }
}
