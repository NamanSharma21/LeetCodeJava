package Trees;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

import Datastructures.TreeNode;

public class MinimumDepthOfBinaryTree {
    public static void main(String[] args) {
        MinimumDepthOfBinaryTree minimumDepthOfBinaryTree = new MinimumDepthOfBinaryTree();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.left.right = new TreeNode(7);
        System.out.println("MinimumDepthOfBinaryTree : " + minimumDepthOfBinaryTree.minDepth(root));
        System.out.println("MinimumDepthOfBinaryTree : " + minimumDepthOfBinaryTree.minDepthDFSIterativeStack(root));
        System.out.println("MinimumDepthOfBinaryTree : " + minimumDepthOfBinaryTree.minDepthBFSIterative(root));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/minimum-depth-of-binary-tree/description/?envType=problem-list-v2&envId=tree
     * 
     * Given a binary tree, find its minimum depth.
     * 
     * The minimum depth is the number of nodes along the shortest path from the
     * root node down to the nearest leaf node.
     * 
     * Note: A leaf is a node with no children.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: root = [3,9,20,null,null,15,7]
     * Output: 2
     * Example 2:
     * 
     * Input: root = [2,null,3,null,4,null,5,null,6]
     * Output: 5
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [0, 105].
     * -1000 <= Node.val <= 1000
     * 
     */
    // @formatter:on

    public int minDepth(TreeNode root) {
        if (root == null)
            return 0;
        if (root.left == null && root.right == null)
            return 1;
        if (root.left == null)
            return 1 + minDepth(root.right);
        if (root.right == null)
            return 1 + minDepth(root.left);
        int leftHeight = minDepth(root.left);
        int rightHeight = minDepth(root.left);
        return 1 + Math.min(leftHeight, rightHeight);
    }

    public int minDepthDFSIterativeStack(TreeNode root) {
        if (root == null)
            return 0;
        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<int[]> depthStack = new Stack<>();
        nodeStack.push(root);
        depthStack.push(new int[] { 1 });
        int minDepth = Integer.MAX_VALUE;
        while (!nodeStack.isEmpty()) {
            TreeNode current = nodeStack.pop();
            int currentDepth = depthStack.pop()[0];
            if (current.left == null && current.right == null) {
                minDepth = Math.min(minDepth, currentDepth);
            }

            if (current.left != null) {
                nodeStack.push(current.left);
                depthStack.push(new int[] { currentDepth + 1 });
            }
            if (current.right != null) {
                nodeStack.push(current.right);
                depthStack.push(new int[] { currentDepth + 1 });
            }
        }
        return minDepth;
    }

    public int minDepthBFSIterative(TreeNode root) {
        if (root == null)
            return 0;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int depth = 1;
        while (!queue.isEmpty()) {
            int queueLength = queue.size();
            for (int i = 0; i < queueLength; i++) {
                TreeNode current = queue.poll();
                if (current.left == null && current.right == null) {
                    return depth;
                }
                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
            }
            depth++;
        }
        return depth;
    }

}
// @formatter:off
/*
 * ===========================================================================
 * CODE EXPLANATION & ANALYSIS — minDepth(TreeNode root)
 * ===========================================================================
 *
 * FIRST IMPRESSION
 * -----------------------------------------------------------------------
 * This code ATTEMPTS to find the minimum depth using a BFS-like approach,
 * but it has serious bugs and is fundamentally broken. Below is a full
 * dissection: what it's trying to do, what actually happens, and why it
 * fails.
 *
 *
 * WHAT THE AUTHOR INTENDED
 * -----------------------------------------------------------------------
 * The author is trying to:
 *   1. Use the `val` field of each node to STORE DEPTH (overwriting the
 *      original value)
 *   2. Use a queue to do BFS level-by-level
 *   3. Return root.val when a leaf is found
 *
 * This is a creative but INCORRECT repurposing of `val`.
 *
 *
 * LINE-BY-LINE WALKTHROUGH
 * -----------------------------------------------------------------------
 *
 *   if (root == null)
 *       return 0;
 *
 *   -> Correct. Empty tree -> depth 0.
 *
 *   ---------------------------------------------------------------------
 *
 *   Queue<TreeNode> queue = new ArrayDeque<>();
 *   root.val = 1;
 *   queue.add(root);
 *
 *   -> Initializes queue, sets root depth = 1, enqueues root.
 *   -> SIDE EFFECT WARNING: this DESTROYS the original value of the root
 *      node permanently.
 *
 *   ---------------------------------------------------------------------
 *
 *   while (root.left != null || root.right != null) {
 *
 *   -> BUG #1: Wrong loop condition.
 *      This checks if the CURRENT `root` pointer's children are non-null,
 *      but `root` keeps changing inside the loop. This is NOT checking
 *      "are there still nodes to process in the queue?" like proper BFS
 *      does. It should be: while (!queue.isEmpty())
 *
 *   ---------------------------------------------------------------------
 *
 *   if (root.left != null) {
 *       root.left.val = root.val + 1;  // store depth in child
 *       root = root.left;              // move root pointer to left child
 *       queue.add(root);                // enqueue left child
 *   }
 *
 *   -> BUG #2: `root` pointer is hijacked mid-loop.
 *      The variable `root` is supposed to represent the CURRENT node being
 *      processed, but here it's immediately reassigned to root.left BEFORE
 *      the right child is even checked. The right-child check below then
 *      operates on the LEFT CHILD, not the original node.
 *
 *   ---------------------------------------------------------------------
 *
 *   root = queue.poll();
 *
 *   -> BUG #3: Unconditional poll disrupts BFS order.
 *      This dequeues a node regardless of whether the left child was
 *      processed. If root.left was null (the if above was skipped), this
 *      polls the WRONG NODE — not the node whose right child we want to
 *      check next.
 *
 *   ---------------------------------------------------------------------
 *
 *   if (root.right != null) {
 *       root.right.val = root.val + 1;
 *       root = root.right;
 *       queue.add(root);
 *   }
 *   root = queue.peek();
 *
 *   -> BUG #4: peek() doesn't advance the queue.
 *      peek() only looks at the front without removing it. Combined with
 *      the earlier poll(), this creates a desync between what's in the
 *      queue and what `root` points to. The loop condition then checks
 *      root.left/right on whatever peek() returned, which may be
 *      completely wrong.
 *
 *
 * DEMONSTRATING THE FAILURE WITH AN EXAMPLE
 * -----------------------------------------------------------------------
 *
 * Test Tree (Correct answer: 2, path 3 -> 9):
 *
 *           3
 *          / \
 *         9   20
 *            /  \
 *           15   7
 *
 * Trace:
 *
 *   Initial:
 *     root = node(3), root.val = 1
 *     queue = [node(3,val=1)]
 *
 *   --- Iteration 1 ---
 *   Loop check: root.left(9) != null -> TRUE, enter loop
 *
 *     root.left != null (9 exists):
 *       node(9).val = 1 + 1 = 2
 *       root = node(9)          <- root now points to node 9!
 *       queue = [node(3), node(9)]
 *
 *     root = queue.poll()       <- polls node(3)
 *       root = node(3)
 *
 *     root.right != null (20 exists):
 *       node(20).val = 3.val + 1   <- 3.val is STILL 1 (original poll)
 *       node(20).val = 2           <- should be 2, accidentally correct
 *       root = node(20)
 *       queue = [node(9), node(20)]
 *
 *     root = queue.peek()       <- peeks node(9)
 *       root = node(9)
 *
 *   --- Iteration 2 ---
 *   Loop check: root(node 9).left == null AND root(node 9).right == null
 *     -> FALSE (9 is a leaf!) -> LOOP EXITS
 *
 *   return root.val = node(9).val = 2
 *
 * This accidentally returns 2 (the correct answer) but FOR THE WRONG
 * REASONS — it worked because node 9 happened to be at the front of the
 * queue when the loop exited.
 *
 *
 * NOW TEST THE SKEWED TREE (Correct answer: 3):
 *
 *     1
 *      \
 *       2
 *        \
 *         3
 *
 *   Initial:
 *     root = node(1), root.val = 1
 *     queue = [node(1)]
 *
 *   --- Iteration 1 ---
 *   Loop check: root.left==null, root.right(2)!=null -> TRUE
 *
 *     root.left == null -> SKIP if block
 *
 *     root = queue.poll() -> root = node(1)   <- polls the only item!
 *     queue = []  <- NOW EMPTY
 *
 *     root.right != null (2 exists):
 *       node(2).val = 1 + 1 = 2
 *       root = node(2)
 *       queue = [node(2)]
 *
 *     root = queue.peek() -> root = node(2)
 *
 *   --- Iteration 2 ---
 *   Loop check: node(2).left==null, node(2).right(3)!=null -> TRUE
 *
 *     root.left == null -> SKIP
 *
 *     root = queue.poll() -> root = node(2)
 *     queue = []
 *
 *     root.right != null (3 exists):
 *       node(3).val = 2 + 1 = 3
 *       root = node(3)
 *       queue = [node(3)]
 *
 *     root = queue.peek() -> root = node(3)
 *
 *   --- Iteration 3 ---
 *   Loop check: node(3).left==null AND node(3).right==null -> FALSE
 *     LOOP EXITS
 *
 *   return root.val = node(3).val = 3   (accidentally correct again)
 *
 *
 * FINAL BREAKING CASE (Correct answer: 2, path 1 -> 2):
 *
 *       1
 *      / \
 *     2   3
 *          \
 *           4
 *            \
 *             5
 *
 * The buggy code will NOT reliably find node 2 first because the `root`
 * pointer gets reassigned before proper BFS ordering is maintained — it
 * will likely return 3 or produce incorrect behavior depending on
 * traversal accidents.
 *
 *
 * SUMMARY OF ALL BUGS
 * -----------------------------------------------------------------------
 *   #1  while condition       - checks a shifting `root` pointer, not
 *                                queue emptiness
 *   #2  root = root.left       - hijacks the current node before the right
 *       inside if               child is processed
 *   #3  unconditional          - dequeues wrong node when left child is
 *       queue.poll()             null
 *   #4  queue.peek() at end    - doesn't advance queue; creates
 *                                pointer/queue desync
 *   #5  root.val overwrite     - destroys original tree data (bad side
 *                                effect)
 *   #6  no leaf detection      - never explicitly checks for a leaf;
 *                                relies on loop exit accident
 *
 * BOTTOM LINE: This code is NOT correct. It produces right answers on
 * simple cases by accident, but will fail on unbalanced trees, certain
 * skewed trees, and trees where the shortest path is on the right side.
 * Do not use this code.
 * ===========================================================================
 */

/*
 * ===========================================================================
 * CORRECT BFS SOLUTION (FOR COMPARISON)
 * ===========================================================================
 *
 * public int minDepth(TreeNode root) {
 *     if (root == null) return 0;
 *
 *     Queue<TreeNode> queue = new LinkedList<>();
 *     queue.offer(root);
 *     int depth = 1;
 *
 *     while (!queue.isEmpty()) {
 *         int levelSize = queue.size();          // snapshot current level
 *
 *         for (int i = 0; i < levelSize; i++) {
 *             TreeNode current = queue.poll();
 *
 *             // First leaf encountered = minimum depth (BFS guarantees it)
 *             if (current.left == null && current.right == null)
 *                 return depth;
 *
 *             if (current.left != null)  queue.offer(current.left);
 *             if (current.right != null) queue.offer(current.right);
 *         }
 *         depth++;
 *     }
 *     return depth;
 * }
 * ===========================================================================
 */
// @formatter:on
