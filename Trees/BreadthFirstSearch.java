package Trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import Datastructures.TreeNode;

public class BreadthFirstSearch {
    public static void main(String[] args) {
        BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch();
        // 5
        // / \
        // 12 13
        // / \ \
        // 7 14 2
        // / \ / \ / \
        // 17 23 27 3 8 11

        // TreeNode root = new TreeNode(5);
        // root.left = new TreeNode(12);
        // root.right = new TreeNode(13);

        // root.left.left = new TreeNode(7);
        // root.left.right = new TreeNode(14);

        // root.right.right = new TreeNode(2);

        // root.left.left.left = new TreeNode(17);
        // root.left.left.right = new TreeNode(23);

        // root.left.right.left = new TreeNode(27);
        // root.left.right.right = new TreeNode(3);

        // root.right.right.left = new TreeNode(8);
        // root.right.right.right = new TreeNode(11);

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);

        System.out.println("" + breadthFirstSearch.breadthFirstSearch(root).stream().map(String::valueOf)
                .collect(Collectors.joining(",")));
    }

    /*
     * https://www.geeksforgeeks.org/dsa/level-order-tree-traversal/
     * 
     * How does Level Order Traversal work?
     * Level Order Traversal visits all TreeNodes at a lower level before moving to
     * a
     * higher level.
     */

    public List<Integer> breadthFirstSearch(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null)
            return ans;

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            TreeNode curr = q.poll();
            ans.add(curr.val);
            if (curr.left != null)
                q.add(curr.left);
            if (curr.right != null)
                q.add(curr.right);
        }
        return ans;
    }
}
