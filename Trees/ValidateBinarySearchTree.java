package Trees;

import java.util.LinkedList;
import java.util.Queue;

import Datastructures.TreeNode;

public class ValidateBinarySearchTree {
    public static void main(String[] args) {
        ValidateBinarySearchTree validateBinarySearchTree = new ValidateBinarySearchTree();
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        System.out.println("Is Valid BST : " + validateBinarySearchTree.isValidBST(root));

        TreeNode root1 = new TreeNode(5);
        root1.left = new TreeNode(4);
        root1.right = new TreeNode(6);
        root1.right.left = new TreeNode(3);
        root1.right.right = new TreeNode(7);
        System.out.println("Is Valid BST : " + validateBinarySearchTree.isValidBST(root1));
    }

    /*
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/94/
     * trees/625/
     * Given the root of a binary tree, determine if it is a valid binary search
     * tree (BST).
     * 
     * A valid BST is defined as follows:
     * 
     * The left subtree of a node contains only nodes with keys strictly less than
     * the node's key.
     * The right subtree of a node contains only nodes with keys strictly greater
     * than the node's key.
     * Both the left and right subtrees must also be binary search trees.
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: root = [2,1,3]
     * Output: true
     * Example 2:
     * 
     * 
     * Input: root = [5,1,4,null,null,3,6]
     * Output: false
     * Explanation: The root node's value is 5 but its right child's value is 4.
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [1, 104].
     * -231 <= Node.val <= 231 - 1
     */
    Integer prev = 0;

    public boolean isValidBST(TreeNode root) {
        // if (root == null)
        // return false;
        // int rootVal = root.val;
        // System.out.println("Root Val : " + rootVal);
        // Queue<TreeNode> q = new LinkedList<>();
        // q.add(root);
        // while (!q.isEmpty()) {
        // TreeNode curr = q.poll();
        // if (curr != null) {
        // if (curr.left != null) {
        // System.out.println("Left : " + curr.left.val + " Curr : " + curr.val);
        // if (!(curr.left.val < curr.val && curr.left.val < rootVal)) {
        // return false;
        // } else {
        // q.add(curr.left);
        // }
        // }
        // if (curr.right != null) {
        // System.out.println("Right : " + curr.right.val + " Curr : " + curr.val);
        // if (!(curr.right.val > curr.val && curr.right.val > rootVal)) {
        // return false;
        // } else {
        // q.add(curr.right);
        // }
        // }
        // }
        // }
        // return true;

        if (root == null)
            return true;
        if (!isValidBST(root.left))
            return false;
        if (prev != null && prev >= root.val)
            return false;
        prev = root.val;
        return isValidBST(root.right);
    }

    public boolean isValidBSTLeft(TreeNode root, int rootValue) {
        if (root == null) {
            return false;
        }

        if (root.left != null) {
            if (root.left.val < rootValue && root.left.val < root.val) {
                System.out.println("L" + root.left.val + " --- " + rootValue + " --- " + root.val);
                return isValidBSTLeft(root.left, rootValue);
            } else {
                return false;
            }
        }

        return true;
    }

    public boolean isValidBSTRight(TreeNode root, int rootValue) {
        if (root == null) {
            return false;
        }

        if (root.right != null) {
            if (root.right.val > rootValue && root.right.val > root.val) {
                System.out.println("R" + root.right.val + " --- " + rootValue + " --- " + root.val);
                return isValidBSTRight(root.right, rootValue);
            } else {
                return false;
            }
        }

        return true;
    }

}
