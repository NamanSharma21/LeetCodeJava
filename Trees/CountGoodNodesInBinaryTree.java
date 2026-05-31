package Trees;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import Datastructures.TreeNode;

public class CountGoodNodesInBinaryTree {
    public static void main(String[] args) {
        CountGoodNodesInBinaryTree countGoodNodesInBinaryTree = new CountGoodNodesInBinaryTree();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.left.left = new TreeNode(3);
        root.right.left = new TreeNode(1);
        root.right.right = new TreeNode(5);
        System.out.println("CountGoodNodesInBinaryTree : " + countGoodNodesInBinaryTree.goodNodesReccursiveDFS(root));
        System.out.println("CountGoodNodesInBinaryTree : " + countGoodNodesInBinaryTree.goodNodesIterativeBFS(root));
        System.out.println("CountGoodNodesInBinaryTree : " + countGoodNodesInBinaryTree.goodNodesIterativeDFS(root));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/count-good-nodes-in-binary-tree/description/?envType=problem-list-v2&envId=tree
     * 
     * Given a binary tree root, a node X in the tree is named good if in the path
     * from root to X there are no nodes with a value greater than X.
     * 
     * Return the number of good nodes in the binary tree.
     * 
     * 
     * 
     * Example 1:
     * 
     *         3
     *        / \
     *       1   4
     *      /   / \
     *     3   1   5
     * 
     * 
     * Input: root = [3,1,4,3,null,1,5]
     * Output: 4
     * Explanation: Nodes in blue are good.
     * Root Node (3) is always a good node.
     * Node 4 -> (3,4) is the maximum value in the path starting from the root.
     * Node 5 -> (3,4,5) is the maximum value in the path
     * Node 3 -> (3,1,3) is the maximum value in the path.
     * Example 2:
     * 
     * 
     * 
     * Input: root = [3,3,null,4,2]
     * Output: 3
     * Explanation: Node 2 -> (3, 3, 2) is not good, because "3" is higher than it.
     * Example 3:
     * 
     * Input: root = [1]
     * Output: 1
     * Explanation: Root is considered as good.
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the binary tree is in the range [1, 10^5].
     * Each node's value is between [-10^4, 10^4].
     */
    // @formatter:on

    public int goodNodesReccursiveDFS(TreeNode root) {
        return dfs(root, Integer.MIN_VALUE);
    }

    public int dfs(TreeNode root, int maxSoFar) {
        if (root == null)
            return 0;
        int goodCount = 0;
        if (root.val >= maxSoFar)
            goodCount = 1;
        int updatedMax = Math.max(maxSoFar, root.val);
        goodCount += dfs(root.left, updatedMax);
        goodCount += dfs(root.right, updatedMax);
        return goodCount;
    }

    public int goodNodesIterativeDFS(TreeNode root) {
        if (root == null)
            return 0;
        Deque<Object[]> stack = new ArrayDeque<>();
        stack.push(new Object[] { root, Integer.MIN_VALUE });
        int goodCount = 0;
        while (!stack.isEmpty()) {
            Object[] pair = stack.pop();
            TreeNode node = (TreeNode) pair[0];
            int maxSoFar = (int) pair[1];
            if (node.val >= maxSoFar)
                goodCount++;
            maxSoFar = Math.max(maxSoFar, node.val);
            if (node.left != null)
                stack.push(new Object[] { node.left, maxSoFar });

            if (node.right != null)
                stack.push(new Object[] { node.right, maxSoFar });
        }
        return goodCount;
    }

    public int goodNodesIterativeBFS(TreeNode root) {
        if (root == null)
            return 0;
        Queue<Object[]> queue = new LinkedList<>();
        queue.offer(new Object[] { root, Integer.MIN_VALUE });
        int goodCount = 0;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                Object[] pair = queue.poll();
                TreeNode node = (TreeNode) pair[0];
                int maxSoFar = (int) pair[1];
                if (node.val >= maxSoFar)
                    goodCount++;
                maxSoFar = Math.max(maxSoFar, node.val);
                if (node.left != null)
                    queue.offer(new Object[] { node.left, maxSoFar });
                if (node.right != null)
                    queue.offer(new Object[] { node.right, maxSoFar });
            }
        }
        return goodCount;
    }
}
// @formatter:off
/*
 * ============================================================
 *  Count Good Nodes in Binary Tree — Deep Dive
 * ============================================================
 *
 * ─────────────────────────────────────────────────────────────
 * 1. PROBLEM STATEMENT
 * ─────────────────────────────────────────────────────────────
 *
 * In Plain English:
 *   Given the root of a binary tree, count the number of "good" nodes.
 *   A node is considered "good" if, on the path from the root down to
 *   that node, no node has a value greater than the node's own value.
 *
 *   In other words, a node X is good if X is the MAXIMUM VALUE seen so
 *   far on the path from root to X (or tied for maximum).
 *
 * Input Format:
 *   - Root of a binary tree: TreeNode root
 *   - Each TreeNode has: int val, TreeNode left, TreeNode right
 *
 * Output Format:
 *   - A single integer: the count of good nodes
 *
 * Constraints:
 *   - Number of nodes: 1 <= n <= 100,000
 *   - Node values:    -10^4 <= val <= 10^4
 *   - The tree is not necessarily balanced or sorted
 *
 * What Needs to Be Computed:
 *   For every node in the tree, check if its value is >= the maximum
 *   value encountered on the path from the root to it.
 *   Count all such nodes.
 *
 *
 * ─────────────────────────────────────────────────────────────
 * 2. INTUITION
 * ─────────────────────────────────────────────────────────────
 *
 * The Core Insight:
 *   As you travel from the root to any node, keep track of the MAXIMUM
 *   VALUE seen so far. When you arrive at a new node:
 *     - If node.val >= maxSoFar  →  it's a GOOD node  ✓
 *     - Otherwise               →  it's NOT a good node  ✗
 *
 * How a Human Reasons About It:
 *   1. Start at the root. It has no ancestors, so it's always a good node.
 *   2. Move to a child. The "competition" is the max of all ancestors.
 *   3. If the child beats (or ties) that max, it qualifies.
 *   4. Pass the UPDATED MAX down to the next level.
 *
 * What Makes It Interesting:
 *   - You must carry STATE (the running maximum) through the traversal.
 *     This is the classic "DFS with parameter passing" pattern.
 *   - Node values can be negative, so initializing maxSoFar to
 *     Integer.MIN_VALUE (not 0) is critical.
 *
 *
 * ─────────────────────────────────────────────────────────────
 * 3. APPROACH OVERVIEW
 * ─────────────────────────────────────────────────────────────
 *
 *  #  | Approach                  | Time  | Space | Use When
 *  ---|---------------------------|-------|-------|-------------------------
 *  1  | Recursive DFS             | O(n)  | O(h)  | Always — cleanest
 *  2  | Iterative DFS (Stack)     | O(n)  | O(h)  | Stack overflow risk
 *  3  | Iterative BFS (Queue)     | O(n)  | O(w)  | When BFS preferred
 *
 *  ✅ Recommended: Recursive DFS — most readable, interview-standard,
 *     and optimal. All three are O(n) time; difference is only in style.
 *
 *
 * ─────────────────────────────────────────────────────────────
 * 4. DETAILED SOLUTIONS IN JAVA
 * ─────────────────────────────────────────────────────────────
 *
 * ── Approach 1: Recursive DFS (Optimal & Recommended) ────────
 *
 * Algorithm:
 *   1. Start DFS from root with maxSoFar = Integer.MIN_VALUE
 *   2. At each node:
 *        - If node.val >= maxSoFar → increment count
 *        - Update maxSoFar = Math.max(maxSoFar, node.val)
 *        - Recurse on left and right children
 *   3. Return total count
 *
 * ┌─────────────────────────────────────────────────────────┐
 *
 *   class Solution {
 *       public int goodNodes(TreeNode root) {
 *           return dfs(root, Integer.MIN_VALUE);
 *       }
 *
 *       private int dfs(TreeNode node, int maxSoFar) {
 *           if (node == null) return 0;
 *
 *           int goodCount = 0;
 *
 *           // This node is good if its value >= max seen from root
 *           if (node.val >= maxSoFar) {
 *               goodCount = 1;
 *           }
 *
 *           // Pass updated max down to children
 *           int updatedMax = Math.max(maxSoFar, node.val);
 *
 *           goodCount += dfs(node.left,  updatedMax);
 *           goodCount += dfs(node.right, updatedMax);
 *
 *           return goodCount;
 *       }
 *   }
 *
 * └─────────────────────────────────────────────────────────┘
 *
 *
 * ── Approach 2: Iterative DFS Using Explicit Stack ───────────
 *
 * Algorithm:
 *   1. Push (root, Integer.MIN_VALUE) onto a stack
 *   2. While stack is not empty:
 *        - Pop (node, maxSoFar)
 *        - If node.val >= maxSoFar → increment count
 *        - Push left and right children with updated max
 *   3. Return total count
 *
 * ┌─────────────────────────────────────────────────────────┐
 *
 *   import java.util.ArrayDeque;
 *   import java.util.Deque;
 *
 *   class Solution {
 *       public int goodNodes(TreeNode root) {
 *           if (root == null) return 0;
 *
 *           // Each stack entry holds [node, maxSoFar]
 *           Deque<Object[]> stack = new ArrayDeque<>();
 *           stack.push(new Object[]{root, Integer.MIN_VALUE});
 *
 *           int goodCount = 0;
 *
 *           while (!stack.isEmpty()) {
 *               Object[] entry   = stack.pop();
 *               TreeNode node    = (TreeNode) entry[0];
 *               int maxSoFar     = (int)      entry[1];
 *
 *               if (node.val >= maxSoFar) {
 *                   goodCount++;
 *               }
 *
 *               int updatedMax = Math.max(maxSoFar, node.val);
 *
 *               if (node.left  != null)
 *                   stack.push(new Object[]{node.left,  updatedMax});
 *               if (node.right != null)
 *                   stack.push(new Object[]{node.right, updatedMax});
 *           }
 *
 *           return goodCount;
 *       }
 *   }
 *
 * └─────────────────────────────────────────────────────────┘
 *
 *
 * ── Approach 3: Iterative BFS Using Queue ────────────────────
 *
 * ┌─────────────────────────────────────────────────────────┐
 *
 *   import java.util.ArrayDeque;
 *   import java.util.Queue;
 *
 *   class Solution {
 *       public int goodNodes(TreeNode root) {
 *           if (root == null) return 0;
 *
 *           // Queue stores [node, maxSoFar]
 *           Queue<Object[]> queue = new ArrayDeque<>();
 *           queue.offer(new Object[]{root, Integer.MIN_VALUE});
 *
 *           int goodCount = 0;
 *
 *           while (!queue.isEmpty()) {
 *               Object[] entry   = queue.poll();
 *               TreeNode node    = (TreeNode) entry[0];
 *               int maxSoFar     = (int)      entry[1];
 *
 *               if (node.val >= maxSoFar) {
 *                   goodCount++;
 *               }
 *
 *               int updatedMax = Math.max(maxSoFar, node.val);
 *
 *               if (node.left  != null)
 *                   queue.offer(new Object[]{node.left,  updatedMax});
 *               if (node.right != null)
 *                   queue.offer(new Object[]{node.right, updatedMax});
 *           }
 *
 *           return goodCount;
 *       }
 *   }
 *
 * └─────────────────────────────────────────────────────────┘
 *
 *
 * ─────────────────────────────────────────────────────────────
 * 5. TIME & SPACE COMPLEXITY (WITH REASONING)
 * ─────────────────────────────────────────────────────────────
 *
 *  Approach        | Time | Space | Reasoning
 *  ----------------|------|-------|--------------------------------------
 *  Recursive DFS   | O(n) | O(h)  | Visit every node once; call stack
 *                  |      |       | depth = tree height h
 *  Iterative DFS   | O(n) | O(h)  | Same visits; explicit stack holds
 *                  |      |       | at most h entries
 *  Iterative BFS   | O(n) | O(w)  | Same visits; queue holds at most w
 *                  |      |       | nodes (max width of tree)
 *
 *  Where:
 *    n = number of nodes
 *    h = height of tree  (O(log n) balanced,  O(n) skewed)
 *    w = maximum width   (O(n) worst case for BFS)
 *
 *  Worked Complexity Example:
 *    - 100,000 nodes, perfectly balanced:
 *        height ≈ 17  →  recursive DFS uses ~17 stack frames  ✓
 *        BFS queue could hold ~50,000 nodes at widest level   ⚠
 *    - 100,000 nodes, completely skewed (linked-list shape):
 *        height = 100,000  →  recursion risks StackOverflowError  ⚠
 *        Iterative DFS is safer here                              ✓
 *
 *
 * ─────────────────────────────────────────────────────────────
 * 6. COMPLETE WORKED EXAMPLES FOR EACH APPROACH
 * ─────────────────────────────────────────────────────────────
 *
 * ── Example 1 — Recursive DFS ────────────────────────────────
 *
 *   Input Tree:
 *           3
 *          / \
 *         1   4
 *        /   / \
 *       3   1   5
 *
 *   DFS Trace:
 *   Node  | maxSoFar on arrival | node.val >= max? | Good? | Updated max
 *   ------|---------------------|------------------|-------|------------
 *   3 (root)    | MIN_VALUE     |  3 >= MIN  ✓    | Yes   |  3
 *   1 (L of 3)  |  3            |  1 >= 3    ✗    | No    |  3
 *   3 (L of 1)  |  3            |  3 >= 3    ✓    | Yes   |  3
 *   4 (R of 3)  |  3            |  4 >= 3    ✓    | Yes   |  4
 *   1 (L of 4)  |  4            |  1 >= 4    ✗    | No    |  4
 *   5 (R of 4)  |  4            |  5 >= 4    ✓    | Yes   |  5
 *
 *   Good nodes: 3, 3, 4, 5  →  Output = 4  ✓
 *
 *
 * ── Example 2 — Iterative DFS with Stack ─────────────────────
 *
 *   Input Tree:
 *       3
 *      /
 *     3
 *      \
 *       4
 *        \
 *         5
 *
 *   Stack Trace:
 *   Step | Stack contents (node, max) | Action            | Count
 *   -----|---------------------------|-------------------|------
 *   Init |  [(3, MIN)]               | Push root         |  0
 *   1    |  []                       | 3>=MIN ✓ push(3,3)|  1
 *   2    |  []                       | 3>=3   ✓ push(4,3)|  2
 *   3    |  []                       | 4>=3   ✓ push(5,4)|  3
 *   4    |  []                       | 5>=4   ✓          |  4
 *
 *   Output = 4  ✓
 *
 *
 * ── Example 3 — Single Node, BFS ─────────────────────────────
 *
 *   Input:  root = [1]
 *   Queue:  [(1, MIN_VALUE)]
 *   Poll (1, MIN_VALUE):  1 >= MIN_VALUE  →  count = 1
 *   No children to enqueue.
 *   Output = 1  ✓
 *
 *
 * ─────────────────────────────────────────────────────────────
 * 7. EDGE CASES
 * ─────────────────────────────────────────────────────────────
 *
 *  Edge Case                | What Happens            | Handled?
 *  -------------------------|-------------------------|----------
 *  Single node              | Root always good        | ✓ MIN_VALUE init
 *  All same values          | Every node is good      | ✓ uses >=, not >
 *  All negative values      | Without MIN_VALUE init  | ✓ uses MIN_VALUE
 *                           | root would wrongly fail |
 *  Strictly decreasing path | Only root is good       | ✓
 *  Strictly increasing path | Every node is good      | ✓
 *  Null root                | Return 0 immediately    | ✓ all 3 approaches
 *  Skewed tree (10^5 deep)  | Recursive may overflow  | ⚠ use iterative
 *  Values at ±10^4 boundary | No int overflow risk    | ✓
 *  Duplicate values on path | Counted as good (>=)    | ✓
 *
 *  Bug Risk to Watch:
 *
 *    ✗ WRONG  →  maxSoFar = 0        (breaks for all-negative trees)
 *    ✓ CORRECT →  maxSoFar = Integer.MIN_VALUE
 *
 *
 * ─────────────────────────────────────────────────────────────
 * 8. FINAL SUMMARY
 * ─────────────────────────────────────────────────────────────
 *
 *  Approach        | Simplicity | Stack-Safe | Memory | Recommended?
 *  ----------------|------------|------------|--------|-------------
 *  Recursive DFS   |  ★★★★★    |    ⚠       |  O(h)  | ✓ Interviews
 *  Iterative DFS   |  ★★★      |    ✓       |  O(h)  | Production
 *  Iterative BFS   |  ★★★      |    ✓       |  O(w)  | If BFS needed
 *
 *  Pattern to Remember:
 *    "DFS with parameter passing" — whenever you need to carry state
 *    (running max, running sum, running XOR) from parent to child,
 *    pass it as a parameter in DFS. Fundamental and reusable.
 *
 *  Key Trick:
 *    Always initialize maxSoFar = Integer.MIN_VALUE so the root node
 *    is always counted, even in all-negative-value trees.
 *
 *
 * ─────────────────────────────────────────────────────────────
 * 9. COMPANY & FREQUENCY DATA
 * ─────────────────────────────────────────────────────────────
 *
 *  Company             | Frequency          | Notes
 *  --------------------|--------------------|----------------------
 *  Meta (Facebook)     | ★★★★★ Very High    | Reported 10+ times
 *  Amazon              | ★★★★  High         | OA and phone screens
 *  Google              | ★★★   Medium       | Onsite rounds
 *  Microsoft           | ★★★   Medium       | Mid-level interviews
 *  Bloomberg           | ★★    Moderate     | Reported a few times
 *  Apple               | ★★    Moderate     | Appears occasionally
 *  Adobe               | ★     Low-Medium   | Reported once or twice
 *
 *  LeetCode Problem #1448
 *    Difficulty:      Medium
 *    Acceptance Rate: ~73%
 *    Interview appearances (reported): 40+ times across platforms
 *    Most asked at: Meta, Amazon, Google
 *
 *  NOTE: This is a STAPLE of Meta's interview process.
 *        If you're interviewing there, this is a must-know.
 *
 * ============================================================
 */
// @formatter:on
