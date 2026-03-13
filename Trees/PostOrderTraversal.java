package Trees;

import Datastructures.TreeNode;

public class PostOrderTraversal {
    public static void main(String[] args) {
        PostOrderTraversal postOrderTraversal = new PostOrderTraversal();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);
        postOrderTraversal.postOrderTraversal(root);
    }

    /*
     * Postorder traversal is a tree traversal method that follows the
     * Left-Right-Root order:
     * 
     * The left subtree is visited first.
     * The right subtree is visited next.
     * The root node is processed last.
     * 
     * https://www.geeksforgeeks.org/dsa/postorder-traversal-of-binary-tree/
     */

    public void postOrderTraversal(TreeNode root) {
        if (root == null)
            return;
        postOrderTraversal(root.left);
        postOrderTraversal(root.right);
        System.out.print("" + root.val);
    }
}
