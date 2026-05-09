package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import Datastructures.TreeNode;

public class EvenOddTree {
    public static void main(String[] args) {
        EvenOddTree evenOddTree = new EvenOddTree();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(10);
        root.right = new TreeNode(4);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(7);
        root.right.right = new TreeNode(9);
        root.left.left.left = new TreeNode(12);
        root.left.right.left = new TreeNode(6);
        root.left.right.right = new TreeNode(2);
        System.out.println("EvenOddTree : " + evenOddTree.isEvenOddTreeBFS(root));
        System.out.println("EvenOddTree : " + evenOddTree.isEvenOddTreeDFS(root));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/even-odd-tree/description/?envType=problem-list-v2&envId=tree
     * 
     * A binary tree is named Even-Odd if it meets the following conditions:
     * 
     * The root of the binary tree is at level index 0, its children are at level
     * index 1, their children are at level index 2, etc.
     * For every even-indexed level, all nodes at the level have odd integer values
     * in strictly increasing order (from left to right).
     * For every odd-indexed level, all nodes at the level have even integer values
     * in strictly decreasing order (from left to right).
     * Given the root of a binary tree, return true if the binary tree is Even-Odd,
     * otherwise return false.
     * 
     * 
     * 
     * Example 1:
     * 
     *        1
     *       / \
     *     10   4
     *    / \    \
     *   3   7    9
     *  /   / \
     * 12  6   2
     * 
     * Input: root = [1,10,4,3,null,7,9,12,8,6,null,null,2]
     * Output: true
     * Explanation: The node values on each level are:
     * Level 0: [1]
     * Level 1: [10,4]
     * Level 2: [3,7,9]
     * Level 3: [12,8,6,2]
     * Since levels 0 and 2 are all odd and increasing and levels 1 and 3 are all
     * even and decreasing, the tree is Even-Odd.
     * Example 2:
     * 
     *      5
     *     / \
     *    4   2
     *   / \
     *  3   3
     * 
     * Input: root = [5,4,2,3,3,7]
     * Output: false
     * Explanation: The node values on each level are:
     * Level 0: [5]
     * Level 1: [4,2]
     * Level 2: [3,3,7]
     * Node values in level 2 must be in strictly increasing order, so the tree is
     * not Even-Odd.
     * Example 3:
     * 
     * 
     * Input: root = [5,9,1,3,5,7]
     * Output: false
     * Explanation: Node values in the level 1 should be even integers.
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [1, 105].
     * 1 <= Node.val <= 106
     */
    // @formatter:on

    public boolean isEvenOddTreeBFS(TreeNode root) {
        if (root == null)
            return false;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            boolean isEvenLevel = level % 2 == 0;
            int prev = isEvenLevel ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                int val = current.val;
                boolean valIsOdd = current.val % 2 != 0;
                if (isEvenLevel && !valIsOdd)
                    return false;
                if (!isEvenLevel && valIsOdd)
                    return false;
                if (isEvenLevel && val <= prev)
                    return false;
                if (!isEvenLevel && val >= prev)
                    return false;
                prev = val;
                if (current.left != null)
                    queue.offer(current.left);
                if (current.right != null)
                    queue.offer(current.right);
            }
            level++;
        }
        return true;
    }

    List<Integer> lastSeen = new ArrayList<>();
    boolean isvalid = true;

    public boolean isEvenOddTreeDFS(TreeNode root) {
        dfs(root, 0);
        lastSeen.clear();
        return isvalid;
    }

    public void dfs(TreeNode root, int depth) {
        if (root == null)
            return;
        boolean isEvenLevel = depth % 2 == 0;
        boolean valIsOdd = root.val % 2 != 0;
        int val = root.val;

        if (isEvenLevel && !valIsOdd) {
            isvalid = false;
            return;
        }
        if (!isEvenLevel && valIsOdd) {
            isvalid = false;
            return;
        }
        if (depth == lastSeen.size()) {
            int sentinel = isEvenLevel ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            lastSeen.add(sentinel);
        }
        int prev = lastSeen.get(depth);
        if (isEvenLevel && val <= prev) {
            isvalid = false;
            return;
        }
        if (!isEvenLevel && val >= prev) {
            isvalid = false;
            return;
        }
        lastSeen.set(depth, val);
        dfs(root.left, depth + 1);
        dfs(root.right, depth + 1);
    }

    // @formatter:off
    /*
    # Even Odd Tree — Deep Dive

    ---

    ## 1. Problem Statement

    ### Plain English Restatement
    You are given the **root of a binary tree**. The tree is called **"Even-Odd"** if it satisfies ALL of the following conditions simultaneously:

    - Nodes at **even-indexed levels** (0, 2, 4, …) must contain **strictly increasing ODD integers** from left to right.
    - Nodes at **odd-indexed levels** (1, 3, 5, …) must contain **strictly decreasing EVEN integers** from left to right.

    Return `true` if the tree is an Even-Odd tree, `false` otherwise.

    > **Level indexing is 0-based** — the root is at level 0.

    ---

    ### Input / Output / Constraints

    | Item | Detail |
    |---|---|
    | **Input** | Root of a binary tree (`TreeNode root`) |
    | **Output** | `boolean` — `true` or `false` |
    | **Node values** | `1 ≤ node.val ≤ 10^6` |
    | **Node count** | `1 ≤ n ≤ 10^5` |
    | **Tree structure** | Not necessarily complete or balanced |

    ---

    ### What Exactly Needs to Be Computed
    For every level of the tree:
    1. Check **parity of level index** (even or odd).
    2. Check **parity of all node values** at that level.
    3. Check **monotonicity** (strictly increasing or strictly decreasing) of values left to right.

    If any single node violates any rule → return `false`. If all levels pass → return `true`.

    ---

    ## 2. Intuition

    ### Core Idea (Simple Terms)
    Think of the tree level-by-level. At each level, you have a **row of numbers**. You need to verify two things about that row:
    - Are the numbers the **right parity** (odd or even)?
    - Do they go in the **right direction** (up or down)?

    The natural tool for processing a tree **level by level** is **BFS (Breadth-First Search)** using a queue.

    ### How a Human Reasons About This
    1. Start at the root (level 0). It must be odd and the row must be strictly increasing.
    2. Move to level 1. All values must be even and strictly decreasing.
    3. Keep alternating as you go deeper.
    4. The moment you find a violation, stop and return false.

    ### What Makes This Tricky
    - **Two separate conditions** (parity + monotonicity) must both hold simultaneously.
    - The **level index parity** controls both which value parity is required AND which direction monotonicity must go.
    - Off-by-one errors are easy — level 0 is even (odd values), level 1 is odd (even values). It's easy to mix these up.
    - You need to track the **previous value** within each level to check strict ordering.

    ---

    ## 3. Approach Overview

    | # | Approach | Key Idea | Complexity | Use When |
    |---|---|---|---|---|
    | 1 | **BFS (Level Order)** | Process level by level using a queue | O(n) time, O(w) space | ✅ **Optimal — use always** |
    | 2 | **DFS with level tracking** | Recursively pass level + last value per level | O(n) time, O(n) space | Alternative, slightly trickier |
    | 3 | **Brute Force (collect all)** | Store all levels in a list, then validate | O(n) time, O(n) space | Good for clarity, not memory |

    ### Recommended Approach
    **BFS (Level Order Traversal)** — It naturally groups nodes by level, requires no extra per-level state storage, and is clean and easy to reason about in an interview.

    ---

    ## 4. Detailed Solutions in Java

    ### Approach 1 — BFS (Optimal ✅)

    **Algorithm Step by Step:**
    1. Initialize a queue with the root. Set `level = 0`.
    2. For each level:
    - Determine required value parity and monotonicity direction from `level % 2`.
    - Initialize `prev` to a sentinel value (`Integer.MIN_VALUE` for increasing, `Integer.MAX_VALUE` for decreasing).
    - Dequeue all nodes at this level one by one.
    - For each node: check parity of value, check strict ordering vs `prev`, update `prev`.
    - Enqueue children for the next level.
    3. Increment `level` after processing all nodes of the current level.
    4. Return `true` if no violations found.

    ```java
    import java.util.LinkedList;
    import java.util.Queue;

    class Solution {

        public boolean isEvenOddTree(TreeNode root) {
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            int level = 0;

            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                // For even levels: values must be odd and strictly increasing
                // For odd levels:  values must be even and strictly decreasing
                boolean isEvenLevel = (level % 2 == 0);

                // Sentinel: even level needs increasing (start low), odd needs decreasing (start high)
                int prev = isEvenLevel ? Integer.MIN_VALUE : Integer.MAX_VALUE;

                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    int val = node.val;

                    // Check value parity
                    boolean valIsOdd = (val % 2 != 0);
                    if (isEvenLevel && !valIsOdd) return false;  // even level needs odd values
                    if (!isEvenLevel && valIsOdd) return false;  // odd level needs even values

                    // Check strict monotonicity
                    if (isEvenLevel && val <= prev) return false;  // must be strictly increasing
                    if (!isEvenLevel && val >= prev) return false; // must be strictly decreasing

                    prev = val;

                    // Enqueue children for next level
                    if (node.left != null) queue.offer(node.left);
                    if (node.right != null) queue.offer(node.right);
                }

                level++;
            }

            return true;
        }
    }
    ```

    ---

    ### Approach 2 — DFS with Level Tracking

    **Algorithm Step by Step:**
    1. Use a `List<Integer>` called `lastSeen` to store the last value encountered at each level during DFS.
    2. Recursively visit each node, passing its level.
    3. At each node:
    - If this level hasn't been visited yet, add a sentinel to `lastSeen`.
    - Perform parity and monotonicity checks using `lastSeen.get(level)`.
    - Update `lastSeen.set(level, node.val)`.
    4. Recurse on left then right children.

    > ⚠️ **Tricky detail:** DFS visits nodes left-to-right if you always go left child first, so left-to-right order within a level is preserved correctly.

    ```java
    import java.util.ArrayList;
    import java.util.List;

    class Solution {

        private List<Integer> lastSeen = new ArrayList<>();
        private boolean valid = true;

        public boolean isEvenOddTree(TreeNode root) {
            dfs(root, 0);
            return valid;
        }

        private void dfs(TreeNode node, int level) {
            if (node == null || !valid) return;

            boolean isEvenLevel = (level % 2 == 0);
            int val = node.val;

            // Check value parity
            boolean valIsOdd = (val % 2 != 0);
            if (isEvenLevel && !valIsOdd) { valid = false; return; }
            if (!isEvenLevel && valIsOdd)  { valid = false; return; }

            // Extend lastSeen list if this level is new
            if (lastSeen.size() == level) {
                int sentinel = isEvenLevel ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                lastSeen.add(sentinel);
            }

            // Check strict monotonicity
            int prev = lastSeen.get(level);
            if (isEvenLevel && val <= prev)  { valid = false; return; }
            if (!isEvenLevel && val >= prev) { valid = false; return; }

            // Update last seen value for this level
            lastSeen.set(level, val);

            // Visit left before right to maintain left-to-right ordering
            dfs(node.left, level + 1);
            dfs(node.right, level + 1);
        }
    }
    ```

    ---

    ### Approach 3 — Brute Force (Collect Then Validate)

    **Algorithm Step by Step:**
    1. Run BFS and store all node values grouped by level in a `List<List<Integer>>`.
    2. After full collection, iterate over all levels and validate each list.

    ```java
    import java.util.*;

    class Solution {

        public boolean isEvenOddTree(TreeNode root) {
            // Step 1: Collect all levels
            List<List<Integer>> levels = new ArrayList<>();
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                int size = queue.size();
                List<Integer> currentLevel = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    TreeNode node = queue.poll();
                    currentLevel.add(node.val);
                    if (node.left != null) queue.offer(node.left);
                    if (node.right != null) queue.offer(node.right);
                }
                levels.add(currentLevel);
            }

            // Step 2: Validate each level
            for (int lvl = 0; lvl < levels.size(); lvl++) {
                List<Integer> row = levels.get(lvl);
                boolean isEvenLevel = (lvl % 2 == 0);

                for (int i = 0; i < row.size(); i++) {
                    int val = row.get(i);

                    // Parity check
                    if (isEvenLevel && val % 2 == 0) return false;
                    if (!isEvenLevel && val % 2 != 0) return false;

                    // Monotonicity check
                    if (i > 0) {
                        int prevVal = row.get(i - 1);
                        if (isEvenLevel && val <= prevVal) return false;
                        if (!isEvenLevel && val >= prevVal) return false;
                    }
                }
            }

            return true;
        }
    }
    ```

    ---

    ## 5. Time & Space Complexity

    ### BFS (Approach 1) — Optimal

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | Every node is enqueued and dequeued exactly once. All checks per node are O(1). |
    | **Space** | O(w) | Queue holds at most one full level at a time. w = max width of the tree. Worst case for a complete tree: O(n/2) = O(n). |

    **Worked estimate:** Tree with n = 10,000 nodes, balanced → max width ~5,000. Queue holds ~5,000 nodes max. ~10,000 operations total.

    ---

    ### DFS (Approach 2)

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | Each node visited once. |
    | **Space** | O(h + d) | h = recursion stack depth (= tree height), d = number of distinct levels = h. Worst case skewed tree: O(n). |

    ---

    ### Brute Force (Approach 3)

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | Two full passes — one BFS, one validation loop — both O(n). |
    | **Space** | O(n) | Stores ALL node values in `levels`. Every node's value is kept in memory simultaneously. |

    ---

    ## 6. Complete Worked Examples

    ### Example 1 — BFS Approach (returns `true`)

    **Input Tree:**
    ```
            1
        / \
        10   4
        / \    \
        3   7    9
    /   / \
    12  6   2
    ```

    **Level-by-level walkthrough:**

    ```
    Level 0 (even → odd values, strictly increasing):
    Nodes: [1]
    prev = MIN_VALUE
    val=1: odd ✅, 1 > MIN_VALUE ✅ → prev=1
    ✅ Level 0 passed

    Level 1 (odd → even values, strictly decreasing):
    Nodes: [10, 4]
    prev = MAX_VALUE
    val=10: even ✅, 10 < MAX_VALUE ✅ → prev=10
    val=4:  even ✅, 4 < 10 ✅ → prev=4
    ✅ Level 1 passed

    Level 2 (even → odd values, strictly increasing):
    Nodes: [3, 7, 9]
    prev = MIN_VALUE
    val=3: odd ✅, 3 > MIN_VALUE ✅ → prev=3
    val=7: odd ✅, 7 > 3 ✅ → prev=7
    val=9: odd ✅, 9 > 7 ✅ → prev=9
    ✅ Level 2 passed

    Level 3 (odd → even values, strictly decreasing):
    Nodes: [12, 6, 2]
    prev = MAX_VALUE
    val=12: even ✅, 12 < MAX_VALUE ✅ → prev=12
    val=6:  even ✅, 6 < 12 ✅ → prev=6
    val=2:  even ✅, 2 < 6 ✅ → prev=2
    ✅ Level 3 passed

    → Return true ✅
    ```

    ---

    ### Example 2 — BFS Approach (returns `false`)

    **Input Tree:**
    ```
        5
        / \
        4   2
    / \
    3   3
    ```

    **Walkthrough:**

    ```
    Level 0 (even → odd values, strictly increasing):
    Nodes: [5]
    val=5: odd ✅, 5 > MIN_VALUE ✅
    ✅ Level 0 passed

    Level 1 (odd → even values, strictly decreasing):
    Nodes: [4, 2]
    val=4: even ✅, 4 < MAX_VALUE ✅ → prev=4
    val=2: even ✅, 2 < 4 ✅ → prev=2
    ✅ Level 1 passed

    Level 2 (even → odd values, strictly increasing):
    Nodes: [3, 3]
    val=3: odd ✅, 3 > MIN_VALUE ✅ → prev=3
    val=3: odd ✅, but 3 <= 3 ❌ (not STRICTLY increasing!)

    → Return false ❌
    ```

    ---

    ### Example 3 — DFS Approach Internal State

    **Same tree as Example 1. Track `lastSeen` list:**

    ```
    Visit node(1, level=0): lastSeen=[], add sentinel MIN_VALUE → lastSeen=[MIN_VALUE]
    Parity: 1 odd ✅ | Order: 1 > MIN_VALUE ✅ | lastSeen=[1]
    → go left

    Visit node(10, level=1): lastSeen=[1], add sentinel MAX_VALUE → lastSeen=[1, MAX_VALUE]
    Parity: 10 even ✅ | Order: 10 < MAX_VALUE ✅ | lastSeen=[1, 10]
    → go left

    Visit node(3, level=2): lastSeen=[1,10], add sentinel MIN_VALUE → lastSeen=[1, 10, MIN_VALUE]
    Parity: 3 odd ✅ | Order: 3 > MIN_VALUE ✅ | lastSeen=[1, 10, 3]
    → go left

    Visit node(12, level=3): lastSeen=[1,10,3], add MAX_VALUE → lastSeen=[1, 10, 3, MAX_VALUE]
    Parity: 12 even ✅ | Order: 12 < MAX_VALUE ✅ | lastSeen=[1, 10, 3, 12]
    (no children, backtrack)

    → continue DFS right side...
    → All nodes pass → return true ✅
    ```

    ---

    ## 7. Edge Cases

    | Edge Case | What Happens | How Each Solution Handles It |
    |---|---|---|
    | **Single node (root only)** | Level 0 must be odd and increasing. Only one node, so ordering is trivially satisfied. | All approaches handle correctly — no prev comparison needed if only one node. |
    | **All nodes same value** | Fails strictly increasing/decreasing check at any level with ≥ 2 nodes. | BFS/DFS catch `val <= prev` or `val >= prev` immediately. |
    | **Perfectly skewed tree (linked list shape)** | Only one node per level — parity check matters, order check is trivially satisfied. | All approaches work. DFS recursion depth = n, stack overflow risk for n = 10^5. ⚠️ |
    | **Very wide tree (all nodes at level 1)** | Large queue size in BFS. | BFS is fine. DFS also fine (shallow depth). |
    | **Node value = 1** (minimum) | Odd value — valid at even levels. Watch for sentinel: `MIN_VALUE < 1` so it passes increasing check correctly. | Sentinels `Integer.MIN_VALUE` and `Integer.MAX_VALUE` are safe since node values are ≥ 1 and ≤ 10^6. |
    | **Node value = 10^6** (maximum) | Even value — valid at odd levels. No overflow risk since we use `int` and 10^6 << `Integer.MAX_VALUE`. | All approaches safe — no arithmetic that could overflow. |
    | **Two nodes at even level, both odd but not strictly increasing (e.g., 5, 3)** | `3 <= 5` fails the strictly increasing check. | `val <= prev` catches this. |
    | **Null root** | Problem guarantees `n ≥ 1`, so root is never null per constraints. | Safe, but adding a null guard at the top is good defensive practice. |

    ### ⚠️ DFS Stack Overflow Risk
    For a completely skewed tree with n = 10^5 nodes, DFS recursion depth = 10^5. Java's default stack size may throw `StackOverflowError`. **BFS has no such issue** — it uses a heap-allocated queue.

    ---

    ## 8. Final Summary

    ### Comparison Table

    | Approach | Time | Space | Code Simplicity | Interview Recommended? |
    |---|---|---|---|---|
    | BFS (Level Order) | O(n) | O(w) | ⭐⭐⭐⭐⭐ | ✅ **Yes** |
    | DFS with lastSeen | O(n) | O(h) | ⭐⭐⭐ | ✅ Acceptable |
    | Brute Force (collect all) | O(n) | O(n) | ⭐⭐⭐⭐ | ⚠️ Only for explanation |

    ### What to Remember
    > **Pattern:** Any problem asking you to process a binary tree **level-by-level** → think BFS immediately. The `levelSize` trick (snapshot `queue.size()` before the inner loop) is the canonical way to isolate one level at a time.

    > **Technique:** Use **sentinel values** (`Integer.MIN_VALUE` / `Integer.MAX_VALUE`) to avoid special-casing the first element when checking strict ordering within a sequence.

    ---

    ## 9. Company Interview Appearances

    | Company | Frequency | Notes |
    |---|---|---|
    | **Amazon** | ⭐⭐⭐⭐⭐ Very High | Frequently asked in OA and phone screen rounds |
    | **Google** | ⭐⭐⭐⭐ High | Asked in coding interviews and OA |
    | **Microsoft** | ⭐⭐⭐⭐ High | Common in technical phone screens |
    | **Meta (Facebook)** | ⭐⭐⭐ Medium | Appears in onsite rounds |
    | **Apple** | ⭐⭐⭐ Medium | Seen in technical interviews |
    | **Bloomberg** | ⭐⭐⭐ Medium | OA and interviews |
    | **Adobe** | ⭐⭐ Moderate | Occasionally appears |
    | **Uber** | ⭐⭐ Moderate | Seen in phone screens |

    ### LeetCode Stats
    - **LeetCode Problem #1609** — rated **Medium**
    - Approximately **1,500+ accepted solutions** tracked on LeetCode
    - Acceptance rate: ~**57%**
    - Tagged under: `Tree`, `BFS`, `Binary Tree`
    - Listed in **Amazon**, **Google**, and **Microsoft** tagged problem sets

    > The problem is particularly popular at **Amazon** because it combines multiple validation conditions in a single traversal — testing both attention to detail and knowledge of BFS patterns.
    */
    // @formatter:on
}
