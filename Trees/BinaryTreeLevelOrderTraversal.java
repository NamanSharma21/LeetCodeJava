package Trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import Datastructures.TreeNode;

public class BinaryTreeLevelOrderTraversal {
    public static void main(String[] args) {
        BinaryTreeLevelOrderTraversal binaryTreeLevelOrderTraversal = new BinaryTreeLevelOrderTraversal();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);
        // binaryTreeLevelOrderTraversal.levelOrder(root);
        binaryTreeLevelOrderTraversal.levelOrderReccursion(root);
    }

    /*
     * Given the root of a binary tree, return the level order traversal of its
     * nodes' values. (i.e., from left to right, level by level).
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[3],[9,20],[15,7]]
     * Example 2:
     * 
     * Input: root = [1]
     * Output: [[1]]
     * Example 3:
     * 
     * Input: root = []
     * Output: []
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [0, 2000].
     * -1000 <= Node.val <= 1000
     */

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> levelOrderList = new ArrayList<>();
        if (root == null)
            return levelOrderList;

        int level = 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            int queueLength = q.size();
            List<Integer> orderList = new ArrayList<>();
            for (int i = 0; i < queueLength; i++) {
                TreeNode curr = q.poll();
                if (curr != null) {
                    orderList.add(curr.val);
                    if (curr.left != null) {
                        q.add(curr.left);
                    }
                    if (curr.right != null) {
                        q.add(curr.right);
                    }
                }
            }
            if (!orderList.isEmpty()) {
                levelOrderList.add(level, orderList);
                level += 1;
            }
        }
        System.out.println("" + levelOrderList);
        return levelOrderList;
    }

    public List<List<Integer>> levelOrderReccursion(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        lor(root, 0, res);
        System.out.println("" + res);
        return res;
    }

    public void lor(TreeNode root, int level, List<List<Integer>> res) {
        if (root == null) {
            return;
        }
        if (res.size() <= level) {
            res.add(level, new ArrayList<>());
        }
        res.get(level).add(root.val);
        lor(root.left, level + 1, res);
        lor(root.right, level + 1, res);
    }
}
