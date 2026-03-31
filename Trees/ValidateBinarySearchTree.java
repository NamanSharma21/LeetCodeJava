package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import Datastructures.TreeNode;

public class ValidateBinarySearchTree {
    public static void main(String[] args) {
        ValidateBinarySearchTree validateBinarySearchTree = new ValidateBinarySearchTree();
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        System.out.println("Is Valid BST : " + validateBinarySearchTree.isValidBSTInOrderIterative(root));

        TreeNode root1 = new TreeNode(5);
        root1.left = new TreeNode(4);
        root1.right = new TreeNode(6);
        root1.right.left = new TreeNode(3);
        root1.right.right = new TreeNode(7);
        System.out.println("Is Valid BST : " + validateBinarySearchTree.isValidBSTInOrderIterative(root1));
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
        if (root == null)
            return true;
        if (!isValidBST(root.left))
            return false;
        if (prev != null && prev >= root.val)
            return false;
        prev = root.val;
        return isValidBST(root.right);
    }

    public boolean isValidBSTInOrderToList(TreeNode root) {
        List<Integer> values = new ArrayList<>();
        collectInOrder(root, values);
        return isStriclyIncreasing(values);
    }

    public void collectInOrder(TreeNode root, List<Integer> values) {
        if (root == null)
            return;
        collectInOrder(root.left, values);
        values.add(root.val);
        collectInOrder(root.right, values);
    }

    public boolean isStriclyIncreasing(List<Integer> values) {
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) <= values.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean isValidBSTReccursiveInOrder(TreeNode root) {
        return isValidBSTWithBounds(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBSTWithBounds(TreeNode root, long minBound, long maxBound) {
        if (root == null)
            return true;
        if (root.val <= minBound || root.val >= maxBound)
            return false;
        boolean isLeftValid = isValidBSTWithBounds(root.left, minBound, root.val);
        boolean isRightValid = isValidBSTWithBounds(root.right, root.val, maxBound);
        return isLeftValid && isRightValid;
    }

    public boolean isValidBSTInOrderIterative(TreeNode root) {
        Deque<TreeNode> q = new ArrayDeque<>();
        TreeNode current = root;
        long prevVal = Long.MIN_VALUE;
        while (current != null || !q.isEmpty()) {
            while (current != null) {
                q.push(current);
                current = current.left;
            }
            current = q.pop();

            if (current.val <= prevVal) {
                return false;
            }

            prevVal = current.val;
            current = current.right;
        }

        return true;
    }

}
