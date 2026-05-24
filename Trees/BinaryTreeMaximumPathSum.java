package Trees;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import Datastructures.TreeNode;

public class BinaryTreeMaximumPathSum {
    public static void main(String[] args) {
        BinaryTreeMaximumPathSum binaryTreeMaximumPathSum = new BinaryTreeMaximumPathSum();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        System.out.println("BinaryTreeMaximumPathSum : " + binaryTreeMaximumPathSum.maxPathSum(root));
        System.out.println("BinaryTreeMaximumPathSum : " + binaryTreeMaximumPathSum.maxPathSumIterativeDFS(root));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/binary-tree-maximum-path-sum/description/?envType=problem-list-v2&envId=tree
     * 
     * A path in a binary tree is a sequence of nodes where each pair of adjacent
     * nodes in the sequence has an edge connecting them. A node can only appear in
     * the sequence at most once. Note that the path does not need to pass through
     * the root.
     * 
     * The path sum of a path is the sum of the node's values in the path.
     * 
     * Given the root of a binary tree, return the maximum path sum of any non-empty
     * path.
     * 
     * 
     * 
     * Example 1:
     * 
     *       1
     *      / \
     *     2   3
     * 
     * Optimal path: 2 -> 1 -> 3 (sum = 6)
     * 
     * Input: root = [1,2,3]
     * Output: 6
     * Explanation: The optimal path is 2 -> 1 -> 3 with a path sum of 2 + 1 + 3 =
     * 6.
     * Example 2:
     * 
     * 
     *        -10
     *        / \
     *       9  20
     *          / \
     *         15  7
     * 
     * Optimal path: 15 -> 20 -> 7 (sum = 42)
     * 
     * Input: root = [-10,9,20,null,null,15,7]
     * Output: 42
     * Explanation: The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7
     * = 42.
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [1, 3 * 104].
     * -1000 <= Node.val <= 1000
     */
    // @formatter:on

    private int globalMax = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        globalMax = Integer.MIN_VALUE;
        dfs(root);
        return globalMax;
    }

    public int dfs(TreeNode node) {
        if (node == null)
            return 0;
        int leftGain = Math.max(0, dfs(node.left));
        int rightGain = Math.max(0, dfs(node.right));

        int archSum = node.val + leftGain + rightGain;
        globalMax = Math.max(globalMax, archSum);

        return node.val + Math.max(leftGain, rightGain);
    }

    public int maxPathSumIterativeDFS(TreeNode root) {
        if (root == null)
            return 0;
        int globalMax = Integer.MIN_VALUE;
        Deque<TreeNode> stack = new ArrayDeque<>();
        Map<TreeNode, Integer> gainMap = new HashMap<>();
        gainMap.put(null, 0);
        TreeNode current = root;
        TreeNode lastVisisted = null;
        while (!stack.isEmpty() || current != null) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            TreeNode peekNode = stack.peek();
            if (peekNode.right != null && peekNode.right != lastVisisted) {
                current = peekNode.right;
            } else {
                stack.pop();
                int leftGain = Math.max(0, gainMap.getOrDefault(peekNode.left, 0));
                int rightGain = Math.max(0, gainMap.getOrDefault(peekNode.right, 0));

                int archSum = peekNode.val + leftGain + rightGain;
                globalMax = Math.max(globalMax, archSum);

                gainMap.put(peekNode, peekNode.val + Math.max(leftGain, rightGain));
                lastVisisted = peekNode;
            }
        }
        return globalMax;
    }
}

// @formatter:off
/*
 * ============================================================
 * Binary Tree Maximum Path Sum — Deep Dive
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * IN PLAIN ENGLISH:
 * Given a binary tree where each node holds an integer value
 * (which can be negative, zero, or positive), find the maximum
 * sum achievable by any path in the tree.
 *
 * A PATH is defined as any sequence of nodes where:
 *   - Each consecutive pair of nodes is connected by an edge
 *   - No node appears more than once
 *   - The path does NOT need to pass through the root
 *   - A path can start and end at ANY node in the tree
 *
 * INPUT FORMAT:
 *   - Root of a binary tree: TreeNode root
 *   - Each TreeNode has: int val, TreeNode left, TreeNode right
 *
 * OUTPUT FORMAT:
 *   - A single int — the maximum path sum
 *
 * CONSTRAINTS:
 *   - Number of nodes: 1 <= n <= 30,000
 *   - Node values: -1000 <= node.val <= 1000
 *   - The tree is NOT necessarily balanced or complete
 *
 * WHAT EXACTLY NEEDS TO BE COMPUTED:
 *   Return the single highest sum reachable by traveling along
 *   any valid path. That path can be a single node, a straight
 *   chain, or an arch that goes down-left then up-right through
 *   some node acting as the "peak."
 *
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * THE CORE IDEA:
 *   Imagine you are standing at every node in the tree, one by
 *   one. At each node, you ask:
 *
 *   "What is the best path sum if this node is the highest
 *    point (the bend/peak) of the path?"
 *
 *   If a node is the peak, the path can extend:
 *     - Only through the LEFT subtree
 *     - Only through the RIGHT subtree
 *     - Through BOTH subtrees (left -> node -> right)
 *     - Or just the NODE ALONE (if children contribute negatively)
 *
 *   You compute this for every node and track the global maximum.
 *
 * HOW A HUMAN REASONS THROUGH IT:
 *   1. Start at the leaves — their best contribution upward is
 *      just their own value.
 *   2. Move up. At each internal node, ask: "How much can my
 *      left child contribute? How much can my right child?"
 *   3. A child only contributes if its subtree sum is POSITIVE —
 *      otherwise you ignore it (take 0 instead).
 *   4. The candidate answer at this node = node.val + leftGain
 *      + rightGain.
 *   5. But you can only PASS UPWARD one arm (left or right, not
 *      both), because including both would make an "arch" and
 *      you can't continue upward.
 *
 * WHAT MAKES IT TRICKY:
 *   - Negative values — you must decide when to "cut off" a subtree.
 *   - The path doesn't need to go through root — you can't just
 *     do a single root-to-leaf traversal.
 *   - Two roles per node — a node simultaneously contributes to a
 *     local candidate answer (arch) and an upward chain (single arm
 *     for parent use).
 *   - Overflow — with 30,000 nodes each worth +/-1000, max sum is
 *     +/-30,000,000 — safely within int range.
 *
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 *  #  | Approach                           | Key Idea                           | Complexity | Use Case
 * ----+------------------------------------+------------------------------------+------------+------------------
 *  1  | Brute Force                        | Try all node pairs, find path      | O(n^3)     | Understanding only
 *     |                                    | between them using LCA             |            |
 * ----+------------------------------------+------------------------------------+------------+------------------
 *  2  | Better — Recursive with global var | DFS, at each node compute local    | O(n)       | This IS optimal
 *     |                                    | arch sum and return single-arm gain|            |
 * ----+------------------------------------+------------------------------------+------------+------------------
 *  3  | Optimal — Iterative Post-order     | Same logic using explicit stack    | O(n)       | Very deep trees
 *     |                                    | (no recursion)                     |            | (stack overflow
 *     |                                    |                                    |            |  safety)
 *
 *  [✓] Approach 2 is the standard optimal solution used in interviews.
 *      Approach 3 is a bonus for extremely deep trees where Java's
 *      call stack might overflow.
 *
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * APPROACH 1 — Brute Force (All Paths via LCA)
 * ------------------------------------------------------------
 *
 * ALGORITHM:
 *   1. For every pair of nodes (u, v), find the path between them.
 *   2. The path between any two nodes in a tree passes through
 *      their Lowest Common Ancestor (LCA).
 *   3. Sum all node values along that path.
 *   4. Track the maximum.
 *
 * WHY THIS IS IMPRACTICAL:
 *   - Finding all pairs: O(n^2)
 *   - Finding path for each pair: O(n)
 *   - Total: O(n^3) — way too slow for n = 30,000
 *
 * CODE:
 *
 *   class BruteForce {
 *
 *       private List<TreeNode> allNodes = new ArrayList<>();
 *       private int maxSum = Integer.MIN_VALUE;
 *
 *       public int maxPathSum(TreeNode root) {
 *           collectNodes(root);
 *           for (int i = 0; i < allNodes.size(); i++) {
 *               for (int j = i; j < allNodes.size(); j++) {
 *                   int pathSum = getPathSum(root,
 *                                            allNodes.get(i),
 *                                            allNodes.get(j));
 *                   maxSum = Math.max(maxSum, pathSum);
 *               }
 *           }
 *           return maxSum;
 *       }
 *
 *       private void collectNodes(TreeNode node) {
 *           if (node == null) return;
 *           allNodes.add(node);
 *           collectNodes(node.left);
 *           collectNodes(node.right);
 *       }
 *
 *       private int getPathSum(TreeNode root, TreeNode u, TreeNode v) {
 *           List<TreeNode> pathU = new ArrayList<>();
 *           List<TreeNode> pathV = new ArrayList<>();
 *           findPath(root, u, pathU);
 *           findPath(root, v, pathV);
 *
 *           int i = 0;
 *           while (i < pathU.size() && i < pathV.size()
 *                  && pathU.get(i) == pathV.get(i)) {
 *               i++;
 *           }
 *           int sum = 0;
 *           for (int k = pathU.size() - 1; k >= i - 1; k--)
 *               sum += pathU.get(k).val;
 *           for (int k = i; k < pathV.size(); k++)
 *               sum += pathV.get(k).val;
 *           return sum;
 *       }
 *
 *       private boolean findPath(TreeNode node,
 *                                 TreeNode target,
 *                                 List<TreeNode> path) {
 *           if (node == null) return false;
 *           path.add(node);
 *           if (node == target) return true;
 *           if (findPath(node.left, target, path)
 *               || findPath(node.right, target, path))
 *               return true;
 *           path.remove(path.size() - 1);
 *           return false;
 *       }
 *   }
 *
 *
 * ------------------------------------------------------------
 * APPROACH 2 — Optimal Recursive DFS [RECOMMENDED]
 * ------------------------------------------------------------
 *
 * ALGORITHM — STEP BY STEP:
 *
 *   Key insight: Every node plays TWO roles:
 *     1. As the ARCH PEAK: can use both left and right arms
 *        -> updates global max
 *     2. As a CONTRIBUTOR TO ITS PARENT: can only send ONE arm upward
 *
 *   At each node:
 *     leftGain  = max(0, dfs(node.left))   <- ignore negative contributions
 *     rightGain = max(0, dfs(node.right))  <- ignore negative contributions
 *     Local candidate (arch) = node.val + leftGain + rightGain -> update global max
 *     Return to parent       = node.val + max(leftGain, rightGain) -> one arm only
 *
 * CODE:
 *
 *   class Solution {
 *
 *       private int globalMax = Integer.MIN_VALUE;
 *
 *       public int maxPathSum(TreeNode root) {
 *           globalMax = Integer.MIN_VALUE;
 *           dfs(root);
 *           return globalMax;
 *       }
 *
 *       
 *        * Returns the maximum gain this node can contribute
 *        * to its parent (single-arm: go left OR right, not both).
 *        
 *       private int dfs(TreeNode node) {
 *           if (node == null) return 0;
 *
 *           // Max gain from left child (0 if negative)
 *           int leftGain = Math.max(0, dfs(node.left));
 *
 *           // Max gain from right child
 *           int rightGain = Math.max(0, dfs(node.right));
 *
 *           // This node as the ARCH PEAK
 *           int archSum = node.val + leftGain + rightGain;
 *
 *           // Update global maximum
 *           globalMax = Math.max(globalMax, archSum);
 *
 *           // Return best single-arm gain to parent
 *           return node.val + Math.max(leftGain, rightGain);
 *       }
 *   }
 *
 *
 * ------------------------------------------------------------
 * APPROACH 3 — Iterative Post-order DFS (Stack-Safe)
 * ------------------------------------------------------------
 *
 * ALGORITHM:
 *   Same logic as Approach 2, but uses an explicit stack for
 *   post-order traversal to avoid recursion stack overflow for
 *   very deep (skewed) trees.
 *
 * CODE:
 *
 *   class IterativeSolution {
 *
 *       public int maxPathSum(TreeNode root) {
 *           if (root == null) return 0;
 *
 *           int globalMax = Integer.MIN_VALUE;
 *           Map<TreeNode, Integer> gainMap = new HashMap<>();
 *           gainMap.put(null, 0);
 *
 *           Deque<TreeNode> stack = new ArrayDeque<>();
 *           TreeNode current = root;
 *           TreeNode lastVisited = null;
 *
 *           while (!stack.isEmpty() || current != null) {
 *               while (current != null) {
 *                   stack.push(current);
 *                   current = current.left;
 *               }
 *
 *               TreeNode peekNode = stack.peek();
 *
 *               if (peekNode.right != null
 *                   && peekNode.right != lastVisited) {
 *                   current = peekNode.right;
 *               } else {
 *                   stack.pop();
 *
 *                   int leftGain  = Math.max(0,
 *                       gainMap.getOrDefault(peekNode.left, 0));
 *                   int rightGain = Math.max(0,
 *                       gainMap.getOrDefault(peekNode.right, 0));
 *
 *                   int archSum = peekNode.val + leftGain + rightGain;
 *                   globalMax = Math.max(globalMax, archSum);
 *
 *                   gainMap.put(peekNode,
 *                       peekNode.val + Math.max(leftGain, rightGain));
 *
 *                   lastVisited = peekNode;
 *               }
 *           }
 *           return globalMax;
 *       }
 *   }
 *
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY (WITH REASONING)
 * ============================================================
 *
 * APPROACH 1 — Brute Force:
 *   Time  : O(n^3) — O(n^2) pairs x O(n) path finding each
 *   Space : O(n)   — path lists stored during traversal
 *   Note  : n=100 -> ~1,000,000 ops. n=30,000 -> ~27 billion. Infeasible.
 *
 * APPROACH 2 — Recursive DFS:
 *   Time  : O(n)   — every node visited exactly once
 *   Space : O(h)   — recursion call stack depth = tree height h
 *     Best case  (balanced tree): h = log2(n) -> O(log n) space
 *     Worst case (skewed tree)  : h = n       -> O(n) space
 *   Note  : n=30,000 balanced -> ~15 levels deep on the stack
 *
 * APPROACH 3 — Iterative Post-order:
 *   Time  : O(n)   — every node visited exactly once
 *   Space : O(n)   — stack + gainMap both store up to n entries
 *   Note  : Uses O(n) space always (due to gainMap), whereas
 *           Approach 2 uses O(h) which is better for balanced trees.
 *           However, Approach 3 is safer for skewed trees where
 *           recursion causes a stack overflow.
 *
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES FOR EACH APPROACH
 * ============================================================
 *
 * ------------------------------------------------------------
 * EXAMPLE 1 — Approach 2 (Simple Tree)
 * ------------------------------------------------------------
 *
 *   Input Tree:
 *          -10
 *         /    \
 *        9      20
 *              /  \
 *             15    7
 *
 *   Expected Output: 42  (path: 15 -> 20 -> 7)
 *
 *   Step | Node | leftGain | rightGain | archSum | globalMax | Returns
 *   -----+------+----------+-----------+---------+-----------+--------
 *     1  |   9  |    0     |     0     |    9    |     9     |    9
 *     2  |  15  |    0     |     0     |   15    |    15     |   15
 *     3  |   7  |    0     |     0     |    7    |    15     |    7
 *     4  |  20  |   15     |     7     |   42    |    42     |   35
 *     5  | -10  |    9     |    35     |   34    |    42     |   25
 *
 *   Final answer: 42 [CORRECT]
 *
 *
 * ------------------------------------------------------------
 * EXAMPLE 2 — Approach 2 (All Negatives)
 * ------------------------------------------------------------
 *
 *   Input Tree:
 *       -3
 *      /
 *    -2
 *
 *   Expected Output: -2  (must include at least one node)
 *
 *   Step | Node | leftGain       | rightGain | archSum | globalMax | Returns
 *   -----+------+----------------+-----------+---------+-----------+--------
 *     1  |  -2  |     0          |     0     |   -2    |    -2     |   -2
 *     2  |  -3  | max(0,-2) = 0  |     0     |   -3    |    -2     |   -3
 *
 *   Final answer: -2 [CORRECT]
 *
 *
 * ------------------------------------------------------------
 * EXAMPLE 3 — Approach 2 (Negative Children, Positive Root Wins)
 * ------------------------------------------------------------
 *
 *   Input Tree:
 *         5
 *        / \
 *      -4   -3
 *
 *   Step | Node | leftGain       | rightGain      | archSum | globalMax | Returns
 *   -----+------+----------------+----------------+---------+-----------+--------
 *     1  |  -4  |     0          |     0          |   -4    |    -4     |   -4
 *     2  |  -3  |     0          |     0          |   -3    |    -3     |   -3
 *     3  |   5  | max(0,-4) = 0  | max(0,-3) = 0  |    5    |     5    |    5
 *
 *   Final answer: 5 [CORRECT] — both children are negative,
 *   so the best path is just node 5 alone.
 *
 *
 * ------------------------------------------------------------
 * EXAMPLE 4 — Iterative (Approach 3) Trace (same tree as Ex. 1)
 * ------------------------------------------------------------
 *
 *   Post-order visits: 9, 15, 7, 20, -10
 *
 *   Stack evolution:
 *     Push -10 -> push (go left) -> push 9
 *       Pop 9:  leftGain=0, rightGain=0, arch=9,  globalMax=9,  gainMap[9]=9
 *     Back to -10, go right -> push 20 -> go left -> push 15
 *       Pop 15: arch=15, globalMax=15, gainMap[15]=15
 *     Back to 20, go right -> push 7
 *       Pop 7:  arch=7,  globalMax=15, gainMap[7]=7
 *       Pop 20: leftGain=15, rightGain=7, arch=42, globalMax=42, gainMap[20]=35
 *       Pop -10: leftGain=9, rightGain=35, arch=34, globalMax=42, gainMap[-10]=25
 *
 *   Final answer: 42 [CORRECT]
 *
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 *  Edge Case               | Description                         | Approach 2 Behavior                                         | Risk?
 * -------------------------+-------------------------------------+-------------------------------------------------------------+-------
 *  Single node             | root = [5]                          | leftGain=0, rightGain=0, arch=5, returns 5                  | Safe
 *  Single negative node    | root = [-5]                         | arch=-5, globalMax=-5                                       | Safe
 *  All negative tree       | Every node < 0                      | max(0, dfs(...)) clamps to 0; least-negative node wins      | Safe
 *  All same values         | [2,2,2,2,2]                         | Normal flow, arch at root gives maximum                     | Safe
 *  Skewed left tree        | Linear chain going left             | Recursion depth = n; possible StackOverflow for n=30,000    | WARN: Use Approach 3
 *  Skewed right tree       | Linear chain going right            | Same StackOverflow risk                                     | WARN: Use Approach 3
 *  Root is null            | root = null                         | dfs(null) returns 0; problem guarantees n >= 1              | Safe
 *  Overflow risk           | 30,000 nodes x 1000 = 30M           | Within int range (max ~2.1B)                                | Safe with int
 *  Path is just one node   | Best path is a leaf                 | Captured when leftGain=rightGain=0, arch = node.val         | Safe
 *  Neg root, deep pos path | Best path never touches root        | DFS finds it without needing root in path                   | Safe
 *
 *
 * BUG CHECK — Integer.MIN_VALUE initialization:
 *
 *   CORRECT:
 *     private int globalMax = Integer.MIN_VALUE;
 *     // Ensures even a tree of all -1000 nodes returns the correct max.
 *
 *   WRONG:
 *     private int globalMax = 0;
 *     // Would return 0 for all-negative trees — INCORRECT!
 *
 *
 * ============================================================
 * 8. FINAL SUMMARY
 * ============================================================
 *
 *  Approach             | Time   | Space | Handles Deep Skew? | Interview Ready?
 * ----------------------+--------+-------+--------------------+-----------------
 *  Brute Force          | O(n^3) | O(n)  | Yes                | No  (too slow)
 *  Recursive DFS        | O(n)   | O(h)  | Risk at n=30k      | Yes (best choice)
 *  Iterative Post-order | O(n)   | O(n)  | Yes                | Yes (bonus points)
 *
 * [RECOMMENDED] Use Approach 2 (Recursive DFS) in interviews — it's clean,
 * concise, and runs in O(n). If your interviewer asks about very deep skewed
 * trees, mention Approach 3 as the stack-overflow-safe alternative.
 *
 * WHAT TO REMEMBER:
 *   Pattern:   Post-order DFS where each node has a dual role — computing a
 *              local "arch" candidate for the global answer, and returning a
 *              single-arm gain to its parent. This pattern applies to many
 *              tree path problems.
 *
 *   Technique: Math.max(0, dfs(child)) is the key move — it elegantly "cuts
 *              off" negative subtrees without any explicit if-else.
 *
 *
 * ============================================================
 * 9. COMPANY APPEARANCES & FREQUENCY
 * ============================================================
 *
 *  Company              | Frequency        | Notes
 * ----------------------+------------------+------------------------------------------
 *  Facebook / Meta      | ***** Very High  | One of their most repeated tree problems
 *  Google               | ***** Very High  | Regularly in phone screens and onsites
 *  Amazon               | ****  High       | Common in SDE-II and senior rounds
 *  Microsoft            | ****  High       | Frequently asked in Azure/Core teams
 *  Uber                 | ***   Medium     | Tree rounds, senior roles
 *  Bloomberg            | ***   Medium     | Appears in structured coding rounds
 *  LinkedIn             | ***   Medium     | Common in backend/infra interviews
 *  Apple                | **    Moderate   | Occasionally in software engineering rounds
 *  Snap                 | **    Moderate   | Tree-focused interview loops
 *  Adobe                | **    Moderate   | SDE rounds
 *
 * LEETCODE STATISTICS:
 *   - Problem #124 — Rated HARD
 *   - Appeared in 500+ reported interview experiences on LeetCode
 *   - Acceptance rate: ~39%
 *   - Listed in LeetCode's Top Interview 150 and Blind 75 problem sets
 *   - One of the MOST IMPORTANT tree problems to master for technical interviews
 *
 * ============================================================
 */

// public class BinaryTreeMaxPathSum {

//     // Definition for a binary tree node.
//     public static class TreeNode {
//         int val;
//         TreeNode left;
//         TreeNode right;
//         TreeNode(int val) { this.val = val; }
//     }

//     // --------------------------------------------------------
//     // Approach 2: Optimal Recursive DFS [RECOMMENDED]
//     // --------------------------------------------------------

//     private int globalMax = Integer.MIN_VALUE;

//     public int maxPathSum(TreeNode root) {
//         globalMax = Integer.MIN_VALUE;
//         dfs(root);
//         return globalMax;
//     }

//     /**
//      * Returns the maximum gain this node can contribute
//      * to its parent (single-arm: go left OR right, not both).
//      */
//     private int dfs(TreeNode node) {
//         if (node == null) return 0;

//         // Max gain from left child (0 if negative — we won't go there)
//         int leftGain = Math.max(0, dfs(node.left));

//         // Max gain from right child
//         int rightGain = Math.max(0, dfs(node.right));

//         // This node as the ARCH PEAK: both arms + this node's value
//         int archSum = node.val + leftGain + rightGain;

//         // Update global maximum with this arch candidate
//         globalMax = Math.max(globalMax, archSum);

//         // Return the best single-arm gain to the parent
//         return node.val + Math.max(leftGain, rightGain);
//     }
// }
// @formatter:on
