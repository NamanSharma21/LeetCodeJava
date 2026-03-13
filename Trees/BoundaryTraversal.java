package Trees;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Datastructures.TreeNode;

public class BoundaryTraversal {
    public static void main(String[] args) {
        BoundaryTraversal boundaryTraversal = new BoundaryTraversal();
        TreeNode root = new TreeNode(20);
        root.left = new TreeNode(8);
        root.right = new TreeNode(22);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(12);
        root.left.right.left = new TreeNode(10);
        root.left.right.right = new TreeNode(14);
        root.right.right = new TreeNode(25);
        System.out.println("" + boundaryTraversal.boundaryTraversal(root).stream().map(String::valueOf)
                .collect(Collectors.joining(",")));
    }

    /*
     * 
     * Given a binary tree, the task is to find the boundary nodes of the binary
     * tree Anti-Clockwise starting from the root. The boundary includes:
     * 
     * left boundary (nodes on left excluding leaf nodes)
     * leaves (consist of only the leaf nodes)
     * right boundary (nodes on right excluding leaf nodes)
     * The left boundary is defined as the path from the root to the left-most leaf
     * node (excluding leaf node itself).
     * The right boundary is defined as the path from the root to the right-most
     * leaf node (excluding leaf node itself).
     * 
     * Note: If the root doesn't have a left subtree or right subtree, then the root
     * itself is the left or right boundary.
     * 
     * https://www.geeksforgeeks.org/dsa/boundary-traversal-of-binary-tree/
     */

    public List<Integer> boundaryTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;
        if (!isLeaf(root))
            result.add(root.val);
        collectBoundaryLeft(root.left, result);
        collectLeaves(root, result);
        collectBoundaryRight(root.right, result);
        return result;
    }

    public void collectBoundaryLeft(TreeNode root, List<Integer> result) {
        if (root == null || isLeaf(root))
            return;
        result.add(root.val);
        if (root.left != null)
            collectBoundaryLeft(root.left, result);
        else if (root.right != null)
            collectBoundaryLeft(root.right, result);
    }

    public void collectLeaves(TreeNode root, List<Integer> result) {
        if (root == null)
            return;
        if (isLeaf(root)) {
            result.add(root.val);
            return;
        }
        collectLeaves(root.left, result);
        collectLeaves(root.right, result);
    }

    public void collectBoundaryRight(TreeNode root, List<Integer> result) {
        if (root == null || isLeaf(root))
            return;
        if (root.right != null)
            collectBoundaryRight(root.right, result);
        else if (root.left != null)
            collectBoundaryRight(root.left, result);
        result.add(root.val);
    }

    public boolean isLeaf(TreeNode root) {
        if (root.left == null && root.right == null) {
            return true;
        }
        return false;
    }
}
