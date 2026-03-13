package Trees;

import Datastructures.TreeNode;

public class MaximumDepthofBinaryTree {
    public static void main(String[] args) {
        MaximumDepthofBinaryTree maximumDepthofBinaryTree = new MaximumDepthofBinaryTree();

        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);

        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println("MaximumDepthofBinaryTree : " + maximumDepthofBinaryTree.maxDepth(root));
    }

    /*
     * Given the root of a binary tree, return its maximum depth.
     * 
     * A binary tree's maximum depth is the number of nodes along the longest path
     * from the root node down to the farthest leaf node.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: root = [3,9,20,null,null,15,7]
     * Output: 3
     * Example 2:
     * 
     * Input: root = [1,null,2]
     * Output: 2
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [0, 104].
     * -100 <= Node.val <= 100
     */

    public int maxDepth(TreeNode root) {
        // BFS
        // if (root == null)
        // return 0;
        // int level = 1;
        // Queue<TreeNode> q = new LinkedList<>();
        // q.add(root);
        // while (!q.isEmpty()) {
        // TreeNode curr = q.poll();
        // if (curr != null) {
        // if (curr.right == null && curr.left == null)
        // continue;
        // if (curr.right != null)
        // q.add(curr.right);
        // if (curr.left != null)
        // q.add(curr.left);
        // level += 1;
        // }
        // }
        // System.out.println("Level : " + level);
        // return level;


        //DFS PreOrder
        if (root == null)
            return 0;
        int leftTreeLength = maxDepth(root.left);
        int rightTreeLength = maxDepth(root.right);
        System.out.println("Left : " + leftTreeLength + " Right : " + rightTreeLength);
        return Math.max(leftTreeLength + 1, rightTreeLength + 1);
    }
}
