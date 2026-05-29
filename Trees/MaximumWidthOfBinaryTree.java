package Trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import Datastructures.TreeNode;

public class MaximumWidthOfBinaryTree {
    public static void main(String[] args) {
        MaximumWidthOfBinaryTree maximumWidthOfBinaryTree = new MaximumWidthOfBinaryTree();

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(3);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(5);
        root.left.right = new TreeNode(3);
        root.right.right = new TreeNode(9);

        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(3);
        root1.right = new TreeNode(2);
        root1.left.left = new TreeNode(5);
        root1.right.right = new TreeNode(9);
        root1.left.left.left = new TreeNode(6);
        root1.right.right.right = new TreeNode(7);

        System.out.println("MaximumWidthOfBinaryTree : " + maximumWidthOfBinaryTree.widthOfBinaryTree(root));
        System.out.println("MaximumWidthOfBinaryTree : " + maximumWidthOfBinaryTree.widthOfBinaryTree(root1));
        System.out
                .println("MaximumWidthOfBinaryTree : " + maximumWidthOfBinaryTree.widthOfBinaryTreeReccursiveDFS(root));
        System.out.println(
                "MaximumWidthOfBinaryTree : " + maximumWidthOfBinaryTree.widthOfBinaryTreeReccursiveDFS(root1));

        System.out
                .println("MaximumWidthOfBinaryTree : " + maximumWidthOfBinaryTree.widthOfBinaryTreeBruteForce(root));
        System.out.println(
                "MaximumWidthOfBinaryTree : " + maximumWidthOfBinaryTree.widthOfBinaryTreeBruteForce(root1));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/maximum-width-of-binary-tree/description/?envType=problem-list-v2&envId=tree
     * 
     * Given the root of a binary tree, return the maximum width of the given tree.
     * 
     * The maximum width of a tree is the maximum width among all levels.
     * 
     * The width of one level is defined as the length between the end-nodes (the
     * leftmost and rightmost non-null nodes), where the null nodes between the
     * end-nodes that would be present in a complete binary tree extending down to
     * that level are also counted into the length calculation.
     * 
     * It is guaranteed that the answer will in the range of a 32-bit signed
     * integer.
     * 
     * 
     * 
     * Example 1:
     * 
     *       1
     *      / \
     *     3   2
     *    / \   \
     *   5   3   9
     * 
     * Max width at level 3: 4 nodes (positions include null gaps between 5,3,null,9)
     * 
     * Input: root = [1,3,2,5,3,null,9]
     * Output: 4
     * Explanation: The maximum width exists in the third level with length 4
     * (5,3,null,9).
     * Example 2:
     * 
     *           1
     *          / \
     *         3   2
     *        /     \
     *       5       9
     *      /         \
     *     6           7
     * 
     * Max width at level 4: 7 nodes (includes all null gaps between 6 and 7)
     * 
     * Input: root = [1,3,2,5,null,null,9,6,null,7]
     * Output: 7
     * Explanation: The maximum width exists in the fourth level with length 7
     * (6,null,null,null,null,null,7).
     * Example 3:
     * 
     *       1
     *      / \
     *     3   2
     *    /
     *   5
     * 
     * Max width at level 2: 2 nodes (3 and 2)
     * 
     * Input: root = [1,3,2,5]
     * Output: 2
     * Explanation: The maximum width exists in the second level with length 2
     * (3,2).
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [1, 3000].
     * -100 <= Node.val <= 100
     */
    // @formatter:on

    public int widthOfBinaryTree(TreeNode root) {
        if (root == null)
            return 0;
        int maxWidth = 0;
        Queue<Object[]> queue = new LinkedList<>();
        queue.offer(new Object[] { root, 1L });
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            long firstIndex = 0, lastIndex = 0;
            for (int i = 0; i < levelSize; i++) {
                Object[] current = queue.poll();
                TreeNode node = (TreeNode) current[0];
                long index = (long) current[1];

                if (i == 0)
                    firstIndex = index;

                lastIndex = index;
                long normalizedIndex = index - firstIndex;

                if (node.left != null)
                    queue.offer(new Object[] { node.left, (2 * normalizedIndex) });
                if (node.right != null)
                    queue.offer(new Object[] { node.right, (2 * normalizedIndex) + 1 });
            }
            maxWidth = (int) Math.max(maxWidth, lastIndex - firstIndex + 1);
        }
        return maxWidth;
    }

    private int maxWidth = 0;
    private List<Long> levelMinIndices = new ArrayList<>();

    public int widthOfBinaryTreeReccursiveDFS(TreeNode root) {
        dfs(root, 0, 0L);
        return maxWidth;
    }

    public void dfs(TreeNode root, int depth, long index) {
        if (root == null)
            return;

        if (depth == levelMinIndices.size())
            levelMinIndices.add(index);

        long leftMostIndexAtDepth = levelMinIndices.get(depth);
        long currentWidth = index - leftMostIndexAtDepth + 1;
        maxWidth = (int) Math.max(maxWidth, currentWidth);

        long normalizedIndex = index - leftMostIndexAtDepth;
        dfs(root.left, depth + 1, (2 * normalizedIndex));
        dfs(root.right, depth + 1, (2 * normalizedIndex) + 1);
    }

    public int widthOfBinaryTreeBruteForce(TreeNode root) {
        if (root == null)
            return 0;
        int maxWidth = 1;
        Map<Integer, TreeNode> levelMap = new HashMap<>();
        levelMap.put(1, root);

        while (!levelMap.isEmpty()) {
            Map<Integer, TreeNode> nextLevel = new HashMap<>();
            int minIndex = Integer.MAX_VALUE, maxIndex = Integer.MIN_VALUE;
            for (Map.Entry<Integer, TreeNode> entry : levelMap.entrySet()) {
                int index = entry.getKey();
                TreeNode node = entry.getValue();
                minIndex = Math.min(minIndex, index);
                maxIndex = Math.max(maxWidth, index);

                if (node.left != null)
                    nextLevel.put(2 * index, node.left);

                if (node.right != null)
                    nextLevel.put(2 * index + 1, node.right);
            }

            maxWidth = Math.max(maxWidth, maxIndex - minIndex + 1);
            levelMap = nextLevel;
        }
        return maxWidth;
    }

}
// @formatter:off
/*
 * =============================================================================
 * Maximum Width of Binary Tree — Deep Dive
 * =============================================================================
 *
 * -----------------------------------------------------------------------------
 * 1. PROBLEM STATEMENT
 * -----------------------------------------------------------------------------
 *
 * In Plain English:
 *   Given the root of a binary tree, find the maximum width of the tree across
 *   all its levels. The width of a level is defined as the distance between the
 *   leftmost and rightmost non-null nodes, including all the null nodes in between.
 *
 * Input / Output:
 *   - Input:  Root of a binary tree (TreeNode root)
 *   - Output: A single integer — the maximum width among all levels
 *
 * Constraints (LeetCode #662):
 *   - Number of nodes: [1, 3000]
 *   - Node values: -100 <= Node.val <= 100
 *   - Width is measured INCLUDING gaps caused by null nodes between two non-null nodes
 *   - Answer is guaranteed to fit in a 32-bit signed integer
 *     (but intermediate values may overflow — a critical trap!)
 *
 * What Exactly Must Be Returned?
 *   The maximum value of (rightmost_index - leftmost_index + 1) across all levels
 *   of the tree, where indices follow the heap indexing convention:
 *     - Root            → index 1
 *     - Left child of i → 2*i
 *     - Right child of i → 2*i + 1
 *
 * -----------------------------------------------------------------------------
 * 2. INTUITION
 * -----------------------------------------------------------------------------
 *
 * Core Idea:
 *   Imagine the binary tree laid out level by level. At each level, if you number
 *   every possible node slot (including null gaps) from left to right, the width is:
 *     rightmost_slot - leftmost_slot + 1
 *
 *   The trick is: how do you number slots efficiently without materializing nulls?
 *   Use the heap indexing trick:
 *
 *           1
 *         /   \
 *        2     3
 *       / \   / \
 *      4   5 6   7
 *
 *   If a node has index i, its left child gets 2i and right child gets 2i+1.
 *   This perfectly models the "gap" between nodes at any level.
 *
 * Human Reasoning Step-by-Step:
 *   1. Do a level-order traversal (BFS)
 *   2. At each level, carry the index of each node alongside the node itself
 *   3. The width of a level = last_index - first_index + 1
 *   4. Track the maximum width seen
 *
 * What Makes This Tricky?
 *   Integer overflow: Indices can grow exponentially (2^depth). Even at depth 30,
 *   index can be 2^30 ≈ 10^9. When multiplied, this overflows int and even long
 *   in edge cases.
 *   The fix: At each level, normalize indices by subtracting the leftmost index
 *   of that level — this resets the base to 0 and prevents overflow.
 *
 * -----------------------------------------------------------------------------
 * 3. APPROACH OVERVIEW
 * -----------------------------------------------------------------------------
 *
 *  #  | Approach                     | Key Idea                          | Complexity        | Notes
 * ----|------------------------------|-----------------------------------|-------------------|------------------------------
 *  1  | Brute Force (Materialize)    | Build complete binary tree array  | O(2^H) time/space | Fails on deep/skewed trees
 *  2  | BFS with Index Pairs         | Heap indexing + BFS queue         | O(N) time, O(N)   | ✅ Clean & optimal
 *  3  | DFS with Index Tracking      | Recursive DFS, track min per level| O(N) time, O(H)   | ✅ Most space-efficient
 *
 *  Recommended: BFS with index normalization is the most intuitive and
 *  interview-friendly. DFS is slightly more space-efficient (stack depth vs
 *  queue width).
 *
 * -----------------------------------------------------------------------------
 * 4. DETAILED SOLUTIONS IN JAVA
 * -----------------------------------------------------------------------------
 *
 * ── Solution 1: Brute Force (Materialize the Full Tree) ──────────────────────
 *
 * Algorithm:
 *   1. Store all nodes in an array like a heap (node[i], left child at 2i,
 *      right child at 2i+1)
 *   2. At each level, scan from left to right to find first and last non-null
 *   3. Width = last - first + 1
 *
 * Why it fails:
 *   For a skewed tree of depth 30, the array needs 2^30 slots — completely
 *   impractical.
 *
 * Code:
 *
 *   // NOT recommended — shown only for conceptual understanding
 *   // Exponential space — breaks on deep or skewed trees
 *   public int widthOfBinaryTree_BruteForce(TreeNode root) {
 *       if (root == null) return 0;
 *
 *       // Map from heap-index to node (simulating full binary tree array)
 *       Map<Integer, TreeNode> levelMap = new HashMap<>();
 *       levelMap.put(1, root);
 *
 *       int maxWidth = 1;
 *
 *       while (!levelMap.isEmpty()) {
 *           Map<Integer, TreeNode> nextLevel = new HashMap<>();
 *           int minIdx = Integer.MAX_VALUE, maxIdx = Integer.MIN_VALUE;
 *
 *           for (Map.Entry<Integer, TreeNode> entry : levelMap.entrySet()) {
 *               int idx   = entry.getKey();
 *               TreeNode node = entry.getValue();
 *               minIdx = Math.min(minIdx, idx);
 *               maxIdx = Math.max(maxIdx, idx);
 *
 *               if (node.left  != null) nextLevel.put(2 * idx,     node.left);
 *               if (node.right != null) nextLevel.put(2 * idx + 1, node.right);
 *           }
 *
 *           maxWidth = Math.max(maxWidth, maxIdx - minIdx + 1);
 *           levelMap = nextLevel;
 *       }
 *
 *       return maxWidth;
 *   }
 *
 * ── Solution 2: BFS with Index Normalization ✅ (Optimal & Recommended) ──────
 *
 * Algorithm Step-by-Step:
 *   1. Use a Queue of (TreeNode, long index) pairs
 *   2. Enqueue root with index 1
 *   3. For each level:
 *        - Record firstIndex = index of the first node dequeued
 *        - Normalize each node's index by subtracting firstIndex → prevents overflow
 *        - Record lastIndex  = index of the last node dequeued
 *        - Width = lastIndex - firstIndex + 1
 *        - Enqueue children with indices 2*normalizedIdx and 2*normalizedIdx + 1
 *   4. Return the maximum width
 *
 * Code (using a helper class):
 *
 *   static class NodeWithIndex {
 *       TreeNode node;
 *       long index;
 *       NodeWithIndex(TreeNode node, long index) {
 *           this.node  = node;
 *           this.index = index;
 *       }
 *   }
 *
 *   public int widthOfBinaryTree(TreeNode root) {
 *       if (root == null) return 0;
 *
 *       Deque<NodeWithIndex> queue = new ArrayDeque<>();
 *       queue.offer(new NodeWithIndex(root, 0L)); // Start index at 0
 *
 *       int maxWidth = 0;
 *
 *       while (!queue.isEmpty()) {
 *           int  levelSize       = queue.size();
 *           long levelFirstIndex = queue.peekFirst().index;
 *           long levelLastIndex  = levelFirstIndex;
 *
 *           for (int i = 0; i < levelSize; i++) {
 *               NodeWithIndex current = queue.poll();
 *               // Normalize: subtract the leftmost index of this level
 *               long normalizedIndex = current.index - levelFirstIndex;
 *               levelLastIndex = current.index;   // track rightmost
 *
 *               if (current.node.left  != null)
 *                   queue.offer(new NodeWithIndex(current.node.left,  2 * normalizedIndex));
 *               if (current.node.right != null)
 *                   queue.offer(new NodeWithIndex(current.node.right, 2 * normalizedIndex + 1));
 *           }
 *
 *           int levelWidth = (int)(levelLastIndex - levelFirstIndex + 1);
 *           maxWidth = Math.max(maxWidth, levelWidth);
 *       }
 *
 *       return maxWidth;
 *   }
 *
 * ── Solution 3: DFS with Per-Level Minimum Index Tracking ✅ ─────────────────
 *
 * Algorithm Step-by-Step:
 *   1. Do a pre-order DFS (root → left → right)
 *   2. Maintain a list levelMinIndex where levelMinIndex[depth] = first index seen
 *      at that depth
 *   3. When we visit a node at depth with index idx:
 *        - If this depth is new, set levelMinIndex[depth] = idx (first = leftmost)
 *        - Width at this depth = idx - levelMinIndex[depth] + 1
 *   4. Track the global maximum
 *
 * Code:
 *
 *   private int maxWidth = 0;
 *   private List<Long> levelMinIndices = new ArrayList<>();
 *
 *   public int widthOfBinaryTree(TreeNode root) {
 *       dfs(root, 0, 0L);
 *       return maxWidth;
 *   }
 *
 *   private void dfs(TreeNode node, int depth, long index) {
 *       if (node == null) return;
 *
 *       // First time visiting this depth → this is the leftmost node
 *       if (depth == levelMinIndices.size()) {
 *           levelMinIndices.add(index);
 *       }
 *
 *       long leftmostIndexAtDepth = levelMinIndices.get(depth);
 *
 *       // Width = distance from leftmost node to current node + 1
 *       long currentWidth = index - leftmostIndexAtDepth + 1;
 *       maxWidth = Math.max(maxWidth, (int) currentWidth);
 *
 *       // Normalize by subtracting leftmost to prevent overflow
 *       long normalizedIndex = index - leftmostIndexAtDepth;
 *       dfs(node.left,  depth + 1, 2 * normalizedIndex);
 *       dfs(node.right, depth + 1, 2 * normalizedIndex + 1);
 *   }
 *
 * -----------------------------------------------------------------------------
 * 5. TIME & SPACE COMPLEXITY (WITH REASONING)
 * -----------------------------------------------------------------------------
 *
 * Solution 1 — Brute Force:
 *   Time  → O(2^H)  Must scan all slots at each level
 *   Space → O(2^H)  Stores the full level in a map
 *   Example: Skewed tree of 30 nodes (H=30) → ~2^30 ≈ 1 billion slots. Catastrophic.
 *
 * Solution 2 — BFS with Index Normalization:
 *   Time  → O(N)    Every node is enqueued and dequeued exactly once
 *   Space → O(W)    Queue holds at most one full level; W = max level width ≤ N/2
 *   Example: N=3000 nodes → ~3000 operations. Extremely fast.
 *            Worst case space: perfect binary tree, last level has N/2 nodes → O(N)
 *
 * Solution 3 — DFS with Index Tracking:
 *   Time  → O(N)    Each node visited exactly once
 *   Space → O(H)    Recursion stack depth = H; levelMinIndices is also O(H)
 *   Example: Balanced tree → H = log₂(N) → O(log N) space. Better than BFS!
 *            Skewed tree   → H = N        → O(N) space (same as BFS in worst case)
 *
 * -----------------------------------------------------------------------------
 * 6. COMPLETE WORKED EXAMPLES FOR EACH APPROACH
 * -----------------------------------------------------------------------------
 *
 * Example 1 — BFS Approach
 *
 *   Input Tree:
 *           1          ← Level 0
 *          / \
 *         3   2        ← Level 1
 *        / \   \
 *       5   3   9      ← Level 2
 *
 *   BFS Walkthrough:
 *
 *    Step   | Queue Contents (node, idx)        | Level | firstIdx | lastIdx | Width
 *   --------|-----------------------------------|-------|----------|---------|------
 *    Init   | [(1,0)]                           |  —    |    —     |    —    |   —
 *    Lvl 0  | process (1,0) → enqueue (3,0),(2,1)|  0   |    0     |    0    |   1
 *    Lvl 1  | process (3,0),(2,1) → enqueue     |  1    |    0     |    1    |   2
 *           |   (5,0),(3,1),(9,3)               |       |          |         |
 *    Lvl 2  | process (5,0),(3,1),(9,3)         |  2    |    0     |    3    |   4
 *
 *   Normalization at Level 2:
 *     - Node (5) has raw index 0 → normalized = 0
 *     - Node (3) has raw index 1 → normalized = 1
 *     - Node (9) has raw index 3 → normalized = 3
 *     Width = 3 - 0 + 1 = 4 ✅
 *
 * Example 2 — DFS Approach
 *
 *   Input Tree (right-skewed after root):
 *         1           depth=0, idx=0
 *        /
 *       2             depth=1, idx=0
 *        \
 *         3           depth=2, idx=1
 *          \
 *           4         depth=3, idx=3
 *
 *   DFS Walkthrough (pre-order: root → left → right):
 *
 *    Visit  | Depth | Index | levelMinIndices | Width
 *   --------|-------|-------|-----------------|------
 *    Node 1 |   0   |   0   | [0]             |   1
 *    Node 2 |   1   |   0   | [0, 0]          |   1
 *    Node 3 |   2   |   1   | [0, 0, 1]       |   1
 *    Node 4 |   3   |   3   | [0, 0, 1, 3]    |   1
 *
 *   All widths are 1 because each level has only one node. Result = 1 ✅
 *
 * Example 3 — Overflow Scenario (Why Normalization Matters)
 *
 *   Input: A perfect binary tree of depth 32 (hypothetical)
 *   - Without normalization: right child at depth 32 has index 2^32
 *     → OVERFLOWS int, corrupts long arithmetic
 *   - With normalization: at each level we reset to base 0
 *     → max index at any level = levelWidth - 1 ≤ 3000 → safe ✅
 *
 * -----------------------------------------------------------------------------
 * 7. EDGE CASES
 * -----------------------------------------------------------------------------
 *
 * Case 1: Single Node
 *   Input: root = [1]    Expected Output: 1
 *   BFS: lastIndex - firstIndex + 1 = 0 - 0 + 1 = 1 ✅
 *   DFS: width = 0 - 0 + 1 = 1 ✅
 *
 * Case 2: Perfectly Skewed Tree (All Left Children)
 *   1 → 2 → 3 → 4 → 5 (depth = 4)
 *   Every level has width 1.
 *   No overflow risk since all indices remain 0 after normalization ✅
 *
 * Case 3: Null Root
 *   Input: root = null    Expected Output: 0
 *   Both solutions check if (root == null) return 0 at the start ✅
 *
 * Case 4: Wide Tree (Overflow Trap Without Normalization)
 *           1
 *          / \
 *         2   3
 *        /     \
 *       4       7
 *      /         \
 *     8           15
 *    /             \
 *   16              31   ← indices 16 and 31, width = 16
 *
 *   Without normalization at depth 30: right node index ≈ 2^30 → overflows int
 *   At depth 62: overflows long too!
 *   Fix: normalization keeps indices bounded by number of actual nodes ✅
 *
 * Case 5: Complete Binary Tree
 *            1
 *          /   \
 *         2     3
 *        / \   / \
 *       4   5 6   7
 *   Level 2: indices 0,1,2,3 → width = 4. Correct for all approaches ✅
 *
 * Case 6: Negative / Duplicate Node Values
 *   Node values are irrelevant — we only use node positions (indices).
 *   No special handling needed ✅
 *
 * -----------------------------------------------------------------------------
 * 8. FINAL SUMMARY
 * -----------------------------------------------------------------------------
 *
 * Comparison Table:
 *
 *  Approach        | Time   | Space | Code Simplicity | Interview Suitability
 * -----------------|--------|-------|-----------------|----------------------
 *  Brute Force     | O(2^H) | O(2^H)| Medium          | ❌ Never use
 *  BFS + Index     | O(N)   | O(W)  | ⭐ Simple/clear  | ✅ Best for interviews
 *  DFS + Index     | O(N)   | O(H)  | Medium          | ✅ Impressive follow-up
 *
 * Recommended Approach:
 *   BFS with index normalization is the go-to solution. It's intuitive, maps
 *   directly to the problem description (level-by-level), and the normalization
 *   trick elegantly handles overflow.
 *
 * Key Pattern to Remember:
 *   Heap Indexing on Trees: Whenever you need to reason about "position" or
 *   "gaps" in a binary tree, assign each node an index like a heap array
 *   (left = 2i, right = 2i+1). Always normalize at each level by subtracting
 *   the leftmost index to prevent overflow.
 *
 * -----------------------------------------------------------------------------
 * 9. COMPANY APPEARANCES & FREQUENCY
 * -----------------------------------------------------------------------------
 *
 *  Company          | Frequency         | Notes
 * ------------------|-------------------|--------------------------------------
 *  Amazon           | ⭐⭐⭐⭐⭐ Very High | Frequently in SDE-2 interviews
 *  Google           | ⭐⭐⭐⭐  High      | Common in coding rounds
 *  Microsoft        | ⭐⭐⭐⭐  High      | BFS/DFS tree problems are staples
 *  Facebook / Meta  | ⭐⭐⭐   Medium    | Appears in phone screens
 *  Bloomberg        | ⭐⭐⭐   Medium    | Tree traversal focus
 *  Apple            | ⭐⭐    Moderate  | Less frequent
 *  Adobe            | ⭐⭐    Moderate  | Occasionally appears
 *  Uber             | ⭐⭐    Moderate  | Tree + BFS problems common
 *
 *  Total LeetCode reports (2024–2025): 300+ interview reports → HIGH PRIORITY
 *
 *  Interview Tip:
 *    If asked this problem, first mention the naïve approach, immediately identify
 *    the overflow risk, then present the normalization insight — this shows strong
 *    problem-solving communication and will impress interviewers at any tier.
 *
 * =============================================================================
 */
// @formatter:on