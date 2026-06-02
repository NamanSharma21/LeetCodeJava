package Trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import Datastructures.TreeNode;

public class BinaryTreeVerticalOrderTraversal {
    public static void main(String[] args) {
        BinaryTreeVerticalOrderTraversal binaryTreeVerticalOrderTraversal = new BinaryTreeVerticalOrderTraversal();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println(
                "BinaryTreeVerticalOrderTraversal : " + binaryTreeVerticalOrderTraversal.verticalOrderBFSHashMap(root));
        System.out.println(
                "BinaryTreeVerticalOrderTraversal : " + binaryTreeVerticalOrderTraversal.verticalOrderBFSTreeMap(root));
    }

    // @formatter:off
    /*
     * https://algo.monster/liteproblems/314
     * 
     * Given the root of a binary tree, return all nodes grouped by their vertical
     * column, ordered from the leftmost column to the rightmost column. Within each
     * column, nodes should appear in top-to-bottom order (by row/level). If two
     * nodes share the same row and column, they should appear in left-to-right
     * order (left subtree before right subtree).
     * Input Format
     * 
     * A binary tree root node (TreeNode root)
     * Each TreeNode has int val, TreeNode left, TreeNode right
     * Tree can have 0 to 100 nodes
     * Node values: -100 ≤ val ≤ 100
     * 
     * Output Format
     * 
     * List<List<Integer>> — a list of groups, where each group contains node values
     * belonging to the same vertical column, sorted top-to-bottom (and
     * left-to-right within the same level)
     * 
     * 
     * 
     * Column:   -1      0      1      2
     * ==================================
     * Level 0:          3
     *                 /   \
     * Level 1:   9         20
     *                     /  \
     * Level 2:          15    7
     * ==================================
     * Result:   [9]  [3, 15]  [20]   [7]
     * The vertical order traversal would group nodes by their column positions:
     * 
     * Column -1: [9]
     * Column 0: [3, 15]
     * Column 1: [20]
     * Column 2: [7]
     * 
     * So the output would be: [[9], [3, 15], [20], [7]]
     */
    // @formatter:on

    public List<List<Integer>> verticalOrderBFSHashMap(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null)
            return result;
        Queue<Object[]> queue = new LinkedList<>();
        Map<Integer, List<Integer>> columnMap = new HashMap<>();
        queue.offer(new Object[] { root, 0 });
        int minCol = 0, maxCol = 0;
        while (!queue.isEmpty()) {
            Object[] pair = queue.poll();
            TreeNode node = (TreeNode) pair[0];
            int col = (int) pair[1];
            columnMap.computeIfAbsent(col, k -> new ArrayList<>()).add(node.val);
            minCol = Math.min(minCol, col);
            maxCol = Math.max(maxCol, col);
            if (node.left != null)
                queue.offer(new Object[] { node.left, col - 1 });
            if (node.right != null)
                queue.offer(new Object[] { node.right, col + 1 });
        }
        for (int col = minCol; col <= maxCol; col++) {
            result.add(columnMap.get(col));
        }
        return result;
    }

    public List<List<Integer>> verticalOrderBFSTreeMap(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null)
            return result;
        Queue<Object[]> queue = new LinkedList<>();
        queue.offer(new Object[] { root, 0 });
        TreeMap<Integer, List<Integer>> columnMap = new TreeMap<>();
        while (!queue.isEmpty()) {
            Object[] pair = queue.poll();
            TreeNode node = (TreeNode) pair[0];
            int col = (int) pair[1];
            columnMap.computeIfAbsent(col, k -> new ArrayList<>()).add(node.val);
            if (node.left != null)
                queue.offer(new Object[] { node.left, col - 1 });
            if (node.right != null)
                queue.offer(new Object[] { node.right, col + 1 });
        }
        result.addAll(columnMap.values());
        return result;
    }

}

// @formatter:off
/*
 * ============================================================
 *  Binary Tree Vertical Order Traversal
 * ============================================================
 *
 * ============================================================
 *  1. Problem Statement
 * ============================================================
 *
 *  In Your Own Words
 *  -----------------
 *  Given the root of a binary tree, return all nodes grouped by
 *  their vertical column, ordered from the leftmost column to
 *  the rightmost column. Within each column, nodes should appear
 *  in top-to-bottom order (by row/level). If two nodes share the
 *  same row and column, they should appear in left-to-right order
 *  (left subtree before right subtree).
 *
 *  Input Format
 *  ------------
 *  - A binary tree root node (TreeNode root)
 *  - Each TreeNode has: int val, TreeNode left, TreeNode right
 *  - Tree can have 0 to 100 nodes
 *  - Node values: -100 <= val <= 100
 *
 *  Output Format
 *  -------------
 *  - List<List<Integer>> — a list of groups, where each group
 *    contains node values belonging to the same vertical column,
 *    sorted top-to-bottom (and left-to-right within the same level)
 *
 *  What Needs to Be Computed
 *  -------------------------
 *  Assign every node a column index (root = 0, left child = parent - 1,
 *  right child = parent + 1). Group all nodes by column index, preserving
 *  level-order (BFS) ordering within each column. Return groups sorted
 *  by column index ascending.
 *
 *
 * ============================================================
 *  2. Intuition
 * ============================================================
 *
 *  Core Idea
 *  ---------
 *  Imagine dropping a vertical line through every node in the tree.
 *  Nodes that the same vertical line passes through belong to the same
 *  "column." The root sits at column 0. Moving left decrements the
 *  column; moving right increments it.
 *
 *  Human Reasoning Step-by-Step
 *  -----------------------------
 *  1. Assign coordinates: Root gets column 0. Each left child gets
 *     parent_col - 1, each right child gets parent_col + 1.
 *  2. Group by column: Use a map from column -> list of node values.
 *  3. Preserve top-to-bottom order: Use BFS (level-order traversal) so
 *     nodes are naturally visited level by level. This guarantees that
 *     within a column, higher nodes (smaller depth) appear first.
 *  4. Left-to-right tie-breaking: BFS naturally visits left children
 *     before right children at the same level — this handles the
 *     tie-breaking automatically.
 *  5. Sort columns: Collect all unique column indices and sort them to
 *     produce the final output.
 *
 *  What Makes It Tricky
 *  --------------------
 *  - Order matters: DFS would require explicit depth tracking and
 *    sorting; BFS handles ordering naturally.
 *  - Negative column indices: The leftmost columns can have negative
 *    indices — you must handle this carefully (can't use array indexing
 *    directly).
 *  - Tie-breaking rule: Same row, same column -> left before right.
 *    BFS handles this for free; DFS does not.
 *
 *
 * ============================================================
 *  3. Approach Overview
 * ============================================================
 *
 *  +---+-------------------------+-------------------------------+------------------+---------+
 *  | # | Approach                | Key Idea                      | Use Case         | Optimal |
 *  +---+-------------------------+-------------------------------+------------------+---------+
 *  | 1 | DFS + Sorting           | Assign (col, row) to each     | Understanding    |   No    |
 *  |   |                         | node via DFS, sort and group  | only             |         |
 *  +---+-------------------------+-------------------------------+------------------+---------+
 *  | 2 | BFS + HashMap           | BFS to assign columns         | Interviews       |  Yes    |
 *  |   |                         | naturally; HashMap to group   |                  | (Best)  |
 *  +---+-------------------------+-------------------------------+------------------+---------+
 *  | 3 | BFS + TreeMap           | Same as BFS but use TreeMap   | Cleaner code     |  Yes    |
 *  |   |                         | to auto-sort columns          |                  | (Good)  |
 *  +---+-------------------------+-------------------------------+------------------+---------+
 *
 *  Why BFS is Optimal
 *  ------------------
 *  - BFS visits nodes level-by-level, so within any column, top nodes
 *    are automatically inserted before bottom nodes — no sorting needed
 *    within columns.
 *  - Left children are enqueued before right children at each level —
 *    no tie-breaking logic needed.
 *  - Overall complexity: O(N log N) due to column sorting at the end
 *    (or O(N) with HashMap + min/max tracking).
 *
 *
 * ============================================================
 *  4. Detailed Solutions in Java
 * ============================================================
 *
 *  TreeNode Definition
 *  -------------------
 *
 *      public class TreeNode {
 *          int val;
 *          TreeNode left, right;
 *          TreeNode(int val) { this.val = val; }
 *      }
 *
 * ------------------------------------------------------------
 *  Approach 1: DFS + Explicit Sorting
 * ------------------------------------------------------------
 *
 *  Algorithm:
 *  1. Traverse the tree with DFS, tracking (column, row) for each node.
 *  2. Store (row, col, value) tuples in a list.
 *  3. Sort the list by (col, row, value) to satisfy ordering.
 *  4. Group by column and build the result.
 *
 *  WARNING: The value-based tie-breaking (a[2] - b[2]) is an
 *  approximation. The problem requires left-before-right for same
 *  row/col nodes — sorting by value doesn't always achieve this
 *  correctly if values are equal or non-monotonic. BFS handles
 *  this correctly without any sorting.
 *
 *      import java.util.*;
 *
 *      public class VerticalOrderDFS {
 *
 *          // Stores (column, row, value) for each node
 *          private List<int[]> nodeList = new ArrayList<>();
 *
 *          public List<List<Integer>> verticalOrder(TreeNode root) {
 *              List<List<Integer>> result = new ArrayList<>();
 *              if (root == null) return result;
 *
 *              // Step 1: DFS to collect (col, row, val) for every node
 *              dfs(root, 0, 0);
 *
 *              // Step 2: Sort by column first, then row, then value (left-to-right tie-break)
 *              nodeList.sort((a, b) -> {
 *                  if (a[0] != b[0]) return a[0] - b[0]; // sort by column
 *                  if (a[1] != b[1]) return a[1] - b[1]; // sort by row within column
 *                  return a[2] - b[2];                    // sort by value if same row & col
 *              });
 *
 *              // Step 3: Group consecutive same-column entries
 *              int prevCol = Integer.MIN_VALUE;
 *              for (int[] node : nodeList) {
 *                  int col = node[0], val = node[2];
 *                  if (col != prevCol) {
 *                      result.add(new ArrayList<>());
 *                      prevCol = col;
 *                  }
 *                  result.get(result.size() - 1).add(val);
 *              }
 *
 *              return result;
 *          }
 *
 *          private void dfs(TreeNode node, int col, int row) {
 *              if (node == null) return;
 *              nodeList.add(new int[]{col, row, node.val});
 *              dfs(node.left,  col - 1, row + 1);
 *              dfs(node.right, col + 1, row + 1);
 *          }
 *      }
 *
 * ------------------------------------------------------------
 *  Approach 2: BFS + HashMap (Optimal — Interview Recommended)
 * ------------------------------------------------------------
 *
 *  Algorithm Step-by-Step:
 *  1. Use a Queue to perform BFS. Each queue entry holds the TreeNode
 *     and its column index.
 *  2. Use a HashMap<Integer, List<Integer>> to map each column ->
 *     list of node values.
 *  3. Track minCol and maxCol to know the range of columns seen.
 *  4. After BFS, iterate from minCol to maxCol and collect results
 *     in order.
 *
 *      import java.util.*;
 *
 *      public class VerticalOrderBFS {
 *
 *          public List<List<Integer>> verticalOrder(TreeNode root) {
 *              List<List<Integer>> result = new ArrayList<>();
 *              if (root == null) return result;
 *
 *              // Maps column index -> list of node values (in BFS/top-to-bottom order)
 *              Map<Integer, List<Integer>> columnMap = new HashMap<>();
 *
 *              // Queue holds pairs: [node, columnIndex]
 *              Queue<Object[]> queue = new LinkedList<>();
 *              queue.offer(new Object[]{root, 0});
 *
 *              int minCol = 0, maxCol = 0;
 *
 *              // BFS traversal
 *              while (!queue.isEmpty()) {
 *                  Object[] pair = queue.poll();
 *                  TreeNode node = (TreeNode) pair[0];
 *                  int col = (int) pair[1];
 *
 *                  // Add node value to its column group
 *                  columnMap.computeIfAbsent(col, k -> new ArrayList<>()).add(node.val);
 *
 *                  // Update column range
 *                  minCol = Math.min(minCol, col);
 *                  maxCol = Math.max(maxCol, col);
 *
 *                  // Enqueue children with updated column indices
 *                  // Left child before right child -> guarantees left-to-right order
 *                  if (node.left  != null) queue.offer(new Object[]{node.left,  col - 1});
 *                  if (node.right != null) queue.offer(new Object[]{node.right, col + 1});
 *              }
 *
 *              // Collect results from minCol to maxCol (already sorted)
 *              for (int col = minCol; col <= maxCol; col++) {
 *                  result.add(columnMap.get(col));
 *              }
 *
 *              return result;
 *          }
 *      }
 *
 * ------------------------------------------------------------
 *  Approach 3: BFS + TreeMap (Cleaner Column Ordering)
 * ------------------------------------------------------------
 *
 *  Algorithm:
 *  Same as Approach 2, but use a TreeMap instead of HashMap.
 *  TreeMap automatically keeps keys (column indices) in sorted order,
 *  so there's no need to track minCol/maxCol manually.
 *
 *  Practical Note: TreeMap adds a log factor per insertion (O(log N)
 *  per node), making total time O(N log N). HashMap with min/max
 *  tracking is O(N) for BFS + O(W) for result collection where
 *  W = number of columns <= N.
 *
 *      import java.util.*;
 *
 *      public class VerticalOrderTreeMap {
 *
 *          public List<List<Integer>> verticalOrder(TreeNode root) {
 *              List<List<Integer>> result = new ArrayList<>();
 *              if (root == null) return result;
 *
 *              // TreeMap auto-sorts by column index
 *              TreeMap<Integer, List<Integer>> columnMap = new TreeMap<>();
 *
 *              // Queue holds [node, columnIndex]
 *              Queue<Object[]> queue = new LinkedList<>();
 *              queue.offer(new Object[]{root, 0});
 *
 *              while (!queue.isEmpty()) {
 *                  Object[] pair   = queue.poll();
 *                  TreeNode node   = (TreeNode) pair[0];
 *                  int col         = (int) pair[1];
 *
 *                  columnMap.computeIfAbsent(col, k -> new ArrayList<>()).add(node.val);
 *
 *                  if (node.left  != null) queue.offer(new Object[]{node.left,  col - 1});
 *                  if (node.right != null) queue.offer(new Object[]{node.right, col + 1});
 *              }
 *
 *              // TreeMap.values() already returns lists in ascending column order
 *              result.addAll(columnMap.values());
 *              return result;
 *          }
 *      }
 *
 *
 * ============================================================
 *  5. Time & Space Complexity (with reasoning)
 * ============================================================
 *
 *  Approach 1: DFS + Sorting
 *  -------------------------
 *  +-------------+-------------+-----------------------------------------------+
 *  |             | Complexity  | Reasoning                                     |
 *  +-------------+-------------+-----------------------------------------------+
 *  | Time        | O(N log N)  | DFS visits all N nodes -> O(N).               |
 *  |             |             | Sorting N tuples -> O(N log N). Dominates.    |
 *  +-------------+-------------+-----------------------------------------------+
 *  | Space       | O(N)        | nodeList stores one entry per node.           |
 *  |             |             | Recursion stack up to O(H) where H = height.  |
 *  +-------------+-------------+-----------------------------------------------+
 *  Example: Tree with 8 nodes -> 8 tuples -> sort takes ~8 x log(8) = 24 ops.
 *
 *  Approach 2: BFS + HashMap
 *  -------------------------
 *  +-------------+-------------+-----------------------------------------------+
 *  |             | Complexity  | Reasoning                                     |
 *  +-------------+-------------+-----------------------------------------------+
 *  | Time        | O(N)        | Each node enqueued and dequeued exactly once. |
 *  |             |             | HashMap ops are O(1). Final loop over         |
 *  |             |             | W columns <= N.                               |
 *  +-------------+-------------+-----------------------------------------------+
 *  | Space       | O(N)        | Queue holds at most O(W) nodes at a time      |
 *  |             |             | (width of tree). HashMap stores all N values. |
 *  +-------------+-------------+-----------------------------------------------+
 *  Example: Tree with 100 nodes -> 100 BFS iterations, each O(1) -> 100 total.
 *
 *  Approach 3: BFS + TreeMap
 *  -------------------------
 *  +-------------+-------------+-----------------------------------------------+
 *  |             | Complexity  | Reasoning                                     |
 *  +-------------+-------------+-----------------------------------------------+
 *  | Time        | O(N log N)  | Each TreeMap insertion is O(log W) where      |
 *  |             |             | W = number of unique columns <= N.            |
 *  |             |             | Total: O(N log N).                            |
 *  +-------------+-------------+-----------------------------------------------+
 *  | Space       | O(N)        | Same as HashMap approach.                     |
 *  +-------------+-------------+-----------------------------------------------+
 *
 *
 * ============================================================
 *  6. Complete Worked Examples for Each Approach
 * ============================================================
 *
 *  Example Tree:
 *
 *          3
 *         / \
 *        9   20
 *           /  \
 *          15    7
 *
 *  Column assignments:
 *      Col -1: 9
 *      Col  0: 3, 15   (3 at row 0, 15 at row 2)
 *      Col  1: 20
 *      Col  2: 7
 *
 * ------------------------------------------------------------
 *  Approach 1 (DFS) — Walk-Through
 * ------------------------------------------------------------
 *
 *  +------+-------+-----+-----+-----------------------------+
 *  | Step | Node  | Col | Row | nodeList state              |
 *  +------+-------+-----+-----+-----------------------------+
 *  |  1   |   3   |  0  |  0  | [(0,0,3)]                   |
 *  |  2   |   9   | -1  |  1  | [(0,0,3),(-1,1,9)]          |
 *  |  3   |  20   |  1  |  1  | [...,(1,1,20)]              |
 *  |  4   |  15   |  0  |  2  | [...,(0,2,15)]              |
 *  |  5   |   7   |  2  |  2  | [...,(2,2,7)]               |
 *  +------+-------+-----+-----+-----------------------------+
 *
 *  After sort by (col, row):
 *      (-1,1,9) -> (0,0,3) -> (0,2,15) -> (1,1,20) -> (2,2,7)
 *
 *  Grouping:
 *      Col -1: [9]
 *      Col  0: [3, 15]
 *      Col  1: [20]
 *      Col  2: [7]
 *
 *  Output: [[9], [3, 15], [20], [7]]  ✓
 *
 * ------------------------------------------------------------
 *  Approach 2 (BFS + HashMap) — Walk-Through
 * ------------------------------------------------------------
 *
 *  Initial queue: [(3, col=0)], minCol=0, maxCol=0
 *
 *  +------+---------+-----+-------------------------------------------+-------------------+
 *  | Step | Dequeue | Col | columnMap state                           | Enqueue           |
 *  +------+---------+-----+-------------------------------------------+-------------------+
 *  |  1   | node 3  |  0  | {0:[3]}                                   | (9,-1), (20,1)    |
 *  |  2   | node 9  | -1  | {0:[3], -1:[9]}                           | (no children)     |
 *  |  3   | node 20 |  1  | {0:[3], -1:[9], 1:[20]}                   | (15,0), (7,2)     |
 *  |  4   | node 15 |  0  | {0:[3,15], -1:[9], 1:[20]}                | (no children)     |
 *  |  5   | node 7  |  2  | {0:[3,15], -1:[9], 1:[20], 2:[7]}         | (no children)     |
 *  +------+---------+-----+-------------------------------------------+-------------------+
 *
 *  minCol = -1, maxCol = 2
 *
 *  Iterate col from -1 to 2:
 *      col -1 -> [9]
 *      col  0 -> [3, 15]
 *      col  1 -> [20]
 *      col  2 -> [7]
 *
 *  Output: [[9], [3, 15], [20], [7]]  ✓
 *
 * ------------------------------------------------------------
 *  Tricky Example — Tie-Breaking (Same Row & Column)
 * ------------------------------------------------------------
 *
 *          1
 *         / \
 *        2   3
 *         \  /
 *          4 5
 *
 *  Nodes 4 and 5 both land at col = 0, row = 2.
 *
 *  BFS order: Node 2 is enqueued before node 3 -> node 4 (child of 2)
 *  is enqueued before node 5 (child of 3).
 *
 *  So col 0 gets: [1, 4, 5] — left-to-right order preserved  ✓
 *
 *  DFS with value sorting would get this wrong if values were arbitrary.
 *
 *
 * ============================================================
 *  7. Edge Cases
 * ============================================================
 *
 *  Case 1: Empty Tree (root = null)
 *  ---------------------------------
 *      Input:           null
 *      Expected Output: []
 *      All approaches: if (root == null) return result; handles this. ✓
 *
 *  Case 2: Single Node
 *  --------------------
 *      Input:           root = [1]
 *      Expected Output: [[1]]
 *      BFS enqueues (1, col=0), processes it, no children -> [[1]] ✓
 *
 *  Case 3: Skewed Tree (All Right)
 *  --------------------------------
 *      1
 *       \
 *        2
 *         \
 *          3
 *      Cols: 1->col0, 2->col1, 3->col2
 *      Output: [[1],[2],[3]] ✓
 *
 *  Case 4: All Left (Deep Negative Columns)
 *  -----------------------------------------
 *          1
 *         /
 *        2
 *       /
 *      3
 *      Cols: 1->0, 2->-1, 3->-2
 *      minCol = -2, maxCol = 0
 *      Output: [[3],[2],[1]] ✓
 *      (negative indices handled correctly by tracking min/max)
 *
 *  Case 5: Nodes with Same Value
 *  ------------------------------
 *          5
 *         / \
 *        5   5
 *      Col -1:[5], Col 0:[5], Col 1:[5]
 *      Values identical but grouping is by column, not value -> ✓
 *
 *  Case 6: Large Balanced Tree (N = 100)
 *  ---------------------------------------
 *      Max column spread ~= +/-50 from root
 *      HashMap or TreeMap both handle this with no issues ✓
 *
 *  Case 7: Null Children Mid-Tree
 *  --------------------------------
 *      BFS checks if (node.left != null) before enqueuing -> safe ✓
 *
 *  Approach Risk Table:
 *  +-------------------------------------------+---------------+---------------+---------------+
 *  | Edge Case                                 | DFS + Sort    | BFS + HashMap | BFS + TreeMap |
 *  +-------------------------------------------+---------------+---------------+---------------+
 *  | Empty tree                                |      ✓        |      ✓        |      ✓        |
 *  | Single node                               |      ✓        |      ✓        |      ✓        |
 *  | Deep skew                                 |      ✓        |      ✓        |      ✓        |
 *  | Tie-breaking (same row/col)               |   ⚠ Risky    |      ✓        |      ✓        |
 *  | Negative columns                          |      ✓        |      ✓        |      ✓        |
 *  | Large N                                   |      ✓        |      ✓        |      ✓        |
 *  +-------------------------------------------+---------------+---------------+---------------+
 *
 *
 * ============================================================
 *  8. Final Summary
 * ============================================================
 *
 *  Comparison Table:
 *  +-----------------+-------------+--------+---------------+-----------------+
 *  | Approach        | Time        | Space  | Correctness   | Code Simplicity |
 *  +-----------------+-------------+--------+---------------+-----------------+
 *  | DFS + Sort      | O(N log N)  | O(N)   | ⚠ Tie-break  | Medium          |
 *  | BFS + HashMap   | O(N)        | O(N)   | ✓ Perfect     | Medium          |
 *  | BFS + TreeMap   | O(N log N)  | O(N)   | ✓ Perfect     | ✓ Cleanest     |
 *  +-----------------+-------------+--------+---------------+-----------------+
 *
 *  Recommendation:
 *  Use BFS + HashMap (Approach 2) in interviews for its O(N) time
 *  and correctness. Use BFS + TreeMap (Approach 3) if you prefer
 *  cleaner code and O(N log N) is acceptable.
 *
 *  Key Takeaway:
 *  "BFS naturally preserves vertical order — pair it with a column
 *  index to solve vertical traversal problems without any sorting
 *  within columns."
 *
 *  The pattern to remember: BFS + column offset tracking — this same
 *  technique extends to problems like Vertical Order Sum, Top View of
 *  Binary Tree, and Bottom View of Binary Tree.
 *
 *
 * ============================================================
 *  9. Company Appearances
 * ============================================================
 *
 *  +-----------------------+-----------------+-----------------------------------+
 *  | Company               | Frequency       | Notes                             |
 *  +-----------------------+-----------------+-----------------------------------+
 *  | Facebook / Meta       | Very High **** | One of their most frequently asked |
 *  |                       |                 | tree problems                     |
 *  +-----------------------+-----------------+-----------------------------------+
 *  | Amazon                | High ****       | Common in SDE-1 and SDE-2 rounds   |
 *  +-----------------------+-----------------+-----------------------------------+
 *  | Microsoft             | Medium ***      | Appears in mid-level interviews    |
 *  +-----------------------+-----------------+-----------------------------------+
 *  | Google                | Medium ***      | Asked in phone screens and onsite  |
 *  +-----------------------+-----------------+-----------------------------------+
 *  | Bloomberg             | Medium ***      | Frequently asked in NYC rounds     |
 *  +-----------------------+-----------------+-----------------------------------+
 *  | Uber                  | Moderate **     | Appears occasionally               |
 *  +-----------------------+-----------------+-----------------------------------+
 *  | LinkedIn              | Moderate **     | Part of tree traversal sets        |
 *  +-----------------------+-----------------+-----------------------------------+
 *  | Apple                 | Moderate **     | Seen in iOS/backend loops          |
 *  +-----------------------+-----------------+-----------------------------------+
 *
 *  LeetCode Problem: #314 (Premium)
 *  Reported 500+ times in interview reports.
 *  Facebook alone accounts for 200+ reported occurrences historically.
 *  Considered a MUST-KNOW tree problem for FAANG preparation.
 *
 * ============================================================
 */
// @formatter:on
