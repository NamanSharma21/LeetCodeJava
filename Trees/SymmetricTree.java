package Trees;

import Datastructures.TreeNode;

public class SymmetricTree {
    public static void main(String[] args) {
        SymmetricTree symmetricTree = new SymmetricTree();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);
        System.out.println("IsSymmetric : " + symmetricTree.isSymmetric(root));

        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(2);
        root1.left.right = new TreeNode(3);
        root1.right.right = new TreeNode(3);
        System.out.println("IsSymmetric : " + symmetricTree.isSymmetric(root1));
    }
    /*
     * Given the root of a binary tree, check whether it is a mirror of itself
     * (i.e., symmetric around its center).
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: root = [1,2,2,3,4,4,3]
     * Output: true
     * Example 2:
     * 
     * 
     * Input: root = [1,2,2,null,3,null,3]
     * Output: false
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [1, 1000].
     * -100 <= Node.val <= 100
     * 
     * 
     * Follow up: Could you solve it both recursively and iteratively?
     */

    public boolean isSymmetric(TreeNode root) {
        // if (root == null) {
        // return false;
        // }
        // Queue<TreeNode> q = new LinkedList<>();
        // q.add(root);
        // while (!q.isEmpty()) {
        // TreeNode curr = q.poll();
        // if (curr != null) {
        // if (curr.left != null) {
        // q.add(curr.left);
        // }
        // if (curr.right != null) {
        // q.add(curr.right);
        // }
        // }
        // }
        // return false;

        if (root == null)
            return true;
        return isSymmetricHelper(root.left, root.right);
    }

    public boolean isSymmetricHelper(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        System.out.println("" + left.val + " --- " + right.val);
        return left.val == right.val && isSymmetricHelper(left.left, right.right)
                && isSymmetricHelper(left.right, right.left);
    }
}
