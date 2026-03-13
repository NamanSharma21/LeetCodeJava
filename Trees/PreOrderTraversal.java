package Trees;

import Datastructures.TreeNode;

public class PreOrderTraversal {
    public static void main(String[] args) {
        PreOrderTraversal preOrderTraversal = new PreOrderTraversal();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);
        preOrderTraversal.preOrderTraversal(root);
    }

    /*
     * Preorder traversal is a tree traversal method that follows the
     * Root-Left-Right order:
     * 
     * The root node of the subtree is visited first.
     * Next, the left subtree is recursively traversed.
     * Finally, the right subtree is recursively traversed.
     * 
     * 
     * https://www.geeksforgeeks.org/dsa/preorder-traversal-of-binary-tree/
     */

    public void preOrderTraversal(TreeNode root) {
        if (root == null)
            return;
        System.out.print("" + root.val);
        preOrderTraversal(root.left);
        preOrderTraversal(root.right);
    }
}
