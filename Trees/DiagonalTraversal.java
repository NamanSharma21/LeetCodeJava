package Trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

import Datastructures.TreeNode;

public class DiagonalTraversal {
    public static void main(String[] args) {
        DiagonalTraversal diagonalTraversal = new DiagonalTraversal();
        TreeNode root = new TreeNode(8);
        root.left = new TreeNode(3);
        root.right = new TreeNode(10);
        root.left.left = new TreeNode(1);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(14);
        root.right.right.left = new TreeNode(13);
        root.right.left.left = new TreeNode(4);
        root.right.left.right = new TreeNode(7);
        System.out.println("" + diagonalTraversal.diagnolTraversal(root).stream().map(String::valueOf)
                .collect(Collectors.joining(",")));
    }

    /*
     * https://www.geeksforgeeks.org/dsa/iterative-diagonal-traversal-binary-tree/
     */

    public ArrayList<Integer> diagnolTraversal(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>();
        if (root == null)
            return ans;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode curr = q.poll();
            while (curr != null) {
                ans.add(curr.val);
                if (curr.left != null)
                    q.add(curr.left);
                curr = curr.right;
            }
        }
        return ans;
    }
}
