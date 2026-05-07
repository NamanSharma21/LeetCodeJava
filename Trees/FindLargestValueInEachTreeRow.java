package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

import Datastructures.TreeNode;

public class FindLargestValueInEachTreeRow {
    public static void main(String[] args) {
        FindLargestValueInEachTreeRow findLargestValueInEachTreeRow = new FindLargestValueInEachTreeRow();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(3);
        root.right = new TreeNode(2);
        root.left.left = new TreeNode(5);
        root.left.right = new TreeNode(3);
        root.right.right = new TreeNode(9);
        System.out.println("FindLargestValueInEachTreeRow : " + findLargestValueInEachTreeRow.largestValuesBFS(root));

        TreeNode root1 = new TreeNode(0);
        root1.left = new TreeNode(-1);
        System.out.println("FindLargestValueInEachTreeRow : " + findLargestValueInEachTreeRow.largestValuesBFS(root1));

        System.out.println("FindLargestValueInEachTreeRow : " + findLargestValueInEachTreeRow.largestValuesDFS(root));
        System.out.println("FindLargestValueInEachTreeRow : " + findLargestValueInEachTreeRow.largestValuesDFS(root1));

        System.out.println("FindLargestValueInEachTreeRow : "
                + findLargestValueInEachTreeRow.largestValuesDFSIterativeStack(root));
        System.out.println("FindLargestValueInEachTreeRow : "
                + findLargestValueInEachTreeRow.largestValuesDFSIterativeStack(root1));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/find-largest-value-in-each-tree-row/description/?envType=problem-list-v2&envId=tree
     * 
     * Given the root of a binary tree, return an array of the largest value in each
     * row of the tree (0-indexed).
     * 
     *        1
     *       / \
     *      3   2
     *     / \   \
     *    5   3   9
     *
     * Level 0: [1]           -> max = 1
     * Level 1: [3, 2]        -> max = 3
     * Level 2: [5, 3, 9]     -> max = 9
     * 
     * Example 1:
     * 
     * 
     * Input: root = [1,3,2,5,3,null,9]
     * Output: [1,3,9]
     * Example 2:
     * 
     *        1
     *       / \
     *      2   3
     *
     * Level 0: [1]           -> max = 1
     * Level 1: [2, 3]        -> max = 3
     * 
     * Input: root = [1,2,3]
     * Output: [1,3]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree will be in the range [0, 104].
     * -231 <= Node.val <= 231 - 1
     */
    // @formatter:on

    public List<Integer> largestValuesBFS(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            int levelMax = Integer.MIN_VALUE;
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                if (current.val > levelMax)
                    levelMax = current.val;
                if (current.left != null)
                    queue.offer(current.left);
                if (current.right != null)
                    queue.offer(current.right);
            }
            result.add(level, levelMax);
            level++;
        }
        return result;
    }

    List<Integer> result = null;

    public List<Integer> largestValuesDFS(TreeNode root) {
        result = new ArrayList<>();
        dfsHelper(root, 0);
        return result;
    }

    public void dfsHelper(TreeNode root, int depth) {
        if (root == null)
            return;
        if (depth == result.size()) {
            result.add(depth, root.val);
        } else {
            result.set(depth, Math.max(result.get(depth), root.val));
        }
        dfsHelper(root.left, depth + 1);
        dfsHelper(root.right, depth + 1);
    }

    public List<Integer> largestValuesDFSIterativeStack(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;
        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        Deque<Integer> depthStack = new ArrayDeque<>();

        nodeStack.push(root);
        depthStack.push(0);

        while (!nodeStack.isEmpty()) {
            TreeNode current = nodeStack.pop();
            int level = depthStack.pop();
            if (level == result.size()) {
                result.add(current.val);
            } else {
                result.set(level, Math.max(result.get(level), current.val));
            }

            if (current.left != null) {
                nodeStack.push(current.left);
                depthStack.push(level + 1);
            }

            if (current.right != null) {
                nodeStack.push(current.right);
                depthStack.push(level + 1);
            }
        }
        return result;
    }

    // @formatter:off
    /*
    # Find Largest Value in Each Tree Row

    ---

    ## 1. Problem Statement

    Given the **root** of a binary tree, find the **maximum value** in each row (level) of the tree and return all these maximums as a list.

    ### Input Format
    - A binary tree root node of type `TreeNode`
    - Each node has: `int val`, `TreeNode left`, `TreeNode right`
    - Node values can be **negative, zero, or positive**

    ### Output Format
    - `List<Integer>` — one maximum value per level, from top (root level) to bottom (leaf level)

    ### Constraints
    - Number of nodes: `0 ≤ n ≤ 10⁴`
    - Node values: `-2³¹ ≤ val ≤ 2³¹ - 1`

    ### What Must Be Computed
    For every depth level of the binary tree, identify the single largest integer among all nodes at that level, and return these values in level-order (top to bottom).

    ---

    ## 2. Intuition

    Think of the tree as a **building with floors**. Each floor is a level of the tree. You want to walk across each floor, look at every room (node), and record the biggest number on that floor.

    ### Human Reasoning Step-by-Step
    1. Start at the root (floor 0) — it's the only node, so it's trivially the max
    2. Move to floor 1 — check left and right children, pick the larger value
    3. Continue floor by floor until no more nodes exist
    4. Return all the recorded maximums in order

    ### What Makes This Interesting
    - Node values can be **negative**, so you can't initialize `max = 0` — you must use `Integer.MIN_VALUE`
    - The tree is **not necessarily balanced** — some levels may have 1 node, others may have thousands
    - Two fundamentally different traversal strategies (BFS vs DFS) both solve this elegantly but think about the problem differently

    ---

    ## 3. Approach Overview

    | # | Approach | Key Idea | Best Used When |
    |---|----------|----------|----------------|
    | 1 | BFS (Level-Order Traversal) | Process level by level using a Queue | **Optimal — interviews, production** |
    | 2 | DFS (Recursive with depth tracking) | Track depth, update max at each depth | Clean recursive alternative |
    | 3 | DFS (Iterative with Stack) | Simulate DFS using explicit stack | Avoids recursion stack overflow |

    ### ✅ Optimal Approach
    **BFS** is the most natural and intuitive fit — the problem is inherently level-by-level, and BFS processes exactly one level at a time. DFS works but requires extra bookkeeping.

    ---

    ## 4. Detailed Solutions in Java

    ### TreeNode Definition (shared by all approaches)

    ```java
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    ```

    ---

    ### Approach 1 — BFS (Level-Order Traversal) ✅ OPTIMAL

    #### Algorithm Step-by-Step
    1. Handle edge case: if root is null, return empty list
    2. Initialize a `Queue<TreeNode>` and add the root
    3. While the queue is not empty:
    - Record how many nodes are in the queue right now → this is `levelSize` (all nodes of the current level)
    - Initialize `levelMax = Integer.MIN_VALUE`
    - Loop exactly `levelSize` times: poll a node, update `levelMax`, enqueue its non-null children
    - After the inner loop, add `levelMax` to the result list
    4. Return the result list

    ```java
    import java.util.*;

    class Solution {
        public List<Integer> largestValues(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            if (root == null) return result;

            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                int levelSize = queue.size(); // snapshot: number of nodes at this level
                int levelMax = Integer.MIN_VALUE;

                for (int i = 0; i < levelSize; i++) {
                    TreeNode current = queue.poll();
                    levelMax = Math.max(levelMax, current.val);

                    if (current.left != null) queue.offer(current.left);
                    if (current.right != null) queue.offer(current.right);
                }

                result.add(levelMax);
            }

            return result;
        }
    }
    ```

    ---

    ### Approach 2 — DFS Recursive (Depth Tracking)

    #### Algorithm Step-by-Step
    1. Maintain a `result` list where `result.get(depth)` = current max at that depth
    2. Recurse into the tree, passing the current depth
    3. At each node:
    - If `depth == result.size()`, this is the first node at this depth → add `node.val`
    - Otherwise, update `result.set(depth, Math.max(result.get(depth), node.val))`
    4. Recurse left with `depth + 1`, then right with `depth + 1`

    ```java
    import java.util.*;

    class Solution {
        private List<Integer> result = new ArrayList<>();

        public List<Integer> largestValues(TreeNode root) {
            dfs(root, 0);
            return result;
        }

        private void dfs(TreeNode node, int depth) {
            if (node == null) return;

            if (depth == result.size()) {
                // First node encountered at this depth
                result.add(node.val);
            } else {
                // Update max if current node is larger
                result.set(depth, Math.max(result.get(depth), node.val));
            }

            dfs(node.left, depth + 1);
            dfs(node.right, depth + 1);
        }
    }
    ```

    ---

    ### Approach 3 — DFS Iterative (Explicit Stack)

    #### Algorithm Step-by-Step
    1. Use a `Deque<TreeNode>` as a stack paired with a `Deque<Integer>` for depths
    2. Push root with depth 0
    3. While stack is not empty:
    - Pop a node and its depth
    - Update `result` at that depth (same logic as recursive DFS)
    - Push right child first, then left child (so left is processed first)
    4. Return result

    ```java
    import java.util.*;

    class Solution {
        public List<Integer> largestValues(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            if (root == null) return result;

            Deque<TreeNode> nodeStack = new ArrayDeque<>();
            Deque<Integer> depthStack = new ArrayDeque<>();

            nodeStack.push(root);
            depthStack.push(0);

            while (!nodeStack.isEmpty()) {
                TreeNode current = nodeStack.pop();
                int depth = depthStack.pop();

                if (depth == result.size()) {
                    result.add(current.val);
                } else {
                    result.set(depth, Math.max(result.get(depth), current.val));
                }

                // Push right first so left is processed first (LIFO)
                if (current.right != null) {
                    nodeStack.push(current.right);
                    depthStack.push(depth + 1);
                }
                if (current.left != null) {
                    nodeStack.push(current.left);
                    depthStack.push(depth + 1);
                }
            }

            return result;
        }
    }
    ```

    ---

    ## 5. Time & Space Complexity

    | Approach | Time Complexity | Space Complexity |
    |----------|----------------|-----------------|
    | BFS | O(n) | O(w) — max queue width |
    | DFS Recursive | O(n) | O(h) — call stack height |
    | DFS Iterative | O(n) | O(h) — explicit stack |

    ### Detailed Reasoning

    #### BFS
    - **Time:** Every node is enqueued and dequeued exactly once → **O(n)**
    - **Space:** The queue holds at most one full level at a time. In a perfect binary tree, the last level has `n/2` nodes → **O(w)** where `w` = max width. Worst case O(n)
    - **Example:** 15-node perfect binary tree → last level has 8 nodes → queue holds up to 8 nodes

    #### DFS Recursive
    - **Time:** Every node is visited exactly once → **O(n)**
    - **Space:** Call stack depth = tree height `h`. Balanced tree: O(log n). Skewed tree (like a linked list): **O(n)**
    - **Example:** 1000-node balanced tree → call stack depth ≈ 10

    #### DFS Iterative
    - **Time:** Every node is pushed/popped once → **O(n)**
    - **Space:** Explicit stack depth mirrors tree height → **O(h)**, same worst-case as recursive

    ---

    ## 6. Complete Worked Examples

    ### Example Input Tree
    ```
            1
        / \
        3   2
        / \   \
        5   3   9
    ```

    ---

    ### Approach 1 — BFS Walkthrough

    | Step | Queue (before poll) | levelSize | Nodes Processed | levelMax | Result So Far |
    |------|---------------------|-----------|-----------------|----------|---------------|
    | 1 | [1] | 1 | Node(1) | 1 | [1] |
    | 2 | [3, 2] | 2 | Node(3), Node(2) | 3 | [1, 3] |
    | 3 | [5, 3, 9] | 3 | Node(5), Node(3), Node(9) | 9 | [1, 3, 9] |

    **Output:** `[1, 3, 9]` ✅

    ---

    ### Approach 2 — DFS Recursive Walkthrough

    **Call Order:** (pre-order: root → left → right)

    ```
    dfs(1, depth=0) → result=[1]
    dfs(3, depth=1) → result=[1, 3]
        dfs(5, depth=2) → result=[1, 3, 5]
        dfs(3, depth=2) → max(5,3)=5, result=[1, 3, 5]
    dfs(2, depth=1) → max(3,2)=3, result=[1, 3, 5]
        dfs(null, depth=2) → return
        dfs(9, depth=2) → max(5,9)=9, result=[1, 3, 9]
    ```

    **Output:** `[1, 3, 9]` ✅

    ---

    ### Approach 3 — DFS Iterative Walkthrough

    | Step | Stack (top→bottom) | Depth Stack | current | depth | result |
    |------|---------------------|-------------|---------|-------|--------|
    | Init | [1] | [0] | — | — | [] |
    | Pop | [] | [] | 1 | 0 | [1] |
    | Push children | [3, 2] | [1, 1] | — | — | [1] |
    | Pop | [2] | [1] | 3 | 1 | [1, 3] |
    | Push children | [5, 3, 2] | [2, 2, 1] | — | — | [1, 3] |
    | Pop | [3, 2] | [2, 1] | 5 | 2 | [1, 3, 5] |
    | Pop | [2] | [1] | 3 | 2 | max(5,3)=5 → [1,3,5] |
    | Pop | [] | [] | 2 | 1 | max(3,2)=3 → [1,3,5] |
    | Push right child | [9] | [2] | — | — | [1, 3, 5] |
    | Pop | [] | [] | 9 | 2 | max(5,9)=9 → [1,3,9] |

    **Output:** `[1, 3, 9]` ✅

    ---

    ## 7. Edge Cases

    ### Case 1 — Null Root
    - **Input:** `root = null`
    - **Expected:** `[]`
    - **BFS:** Returns immediately with empty list ✅
    - **DFS Recursive:** `dfs(null, 0)` returns immediately ✅
    - **DFS Iterative:** null check at start returns empty list ✅

    ---

    ### Case 2 — Single Node
    - **Input:** `root = [42]`
    - **Expected:** `[42]`
    - **BFS:** One iteration, queue has just root, `levelMax = 42` ✅
    - **DFS:** `dfs(42, 0)` → `result.add(42)`, both children null → done ✅

    ---

    ### Case 3 — All Negative Values
    ```
        -1
        /   \
    -5    -3
    ```
    - **Expected:** `[-1, -3]`
    - **Critical:** Must initialize `levelMax = Integer.MIN_VALUE`, NOT `0` — all three approaches do this correctly ✅
    - **DFS:** Uses `result.add(node.val)` on first visit, so no incorrect `0` initialization ✅

    ---

    ### Case 4 — Completely Skewed Tree (Linked List Shape)
    ```
    1 → 2 → 3 → 4 → 5
    ```
    - **Expected:** `[1, 2, 3, 4, 5]` (each level has exactly one node)
    - **BFS Space:** Queue never holds more than 1 node → **O(1)** space ✅
    - **DFS Recursive Space:** Call stack depth = n → **O(n)** — risk of `StackOverflowError` for n = 10,000+ ⚠️
    - **DFS Iterative:** Explicit stack handles this without overflow ✅

    ---

    ### Case 5 — Perfect Binary Tree (Maximum Width)
    - A perfect tree of height 14 has 2¹⁴ - 1 = 16,383 nodes; last level has 8,192 nodes
    - **BFS:** Queue can hold up to 8,192 nodes simultaneously — highest memory usage here
    - **DFS:** Stack/call depth only 14 — very memory efficient here ✅

    ---

    ### Case 6 — Integer Overflow Risk
    - Values range from `Integer.MIN_VALUE` to `Integer.MAX_VALUE`
    - Using `Math.max()` on `int` values is safe — no arithmetic is performed, so no overflow ✅
    - Initializing with `Integer.MIN_VALUE` is safe because `Math.max(Integer.MIN_VALUE, anyInt)` always returns `anyInt` ✅

    ---

    ### Case 7 — Duplicate Maximum Values
    ```
        5
    / \
    5   5
    ```
    - **Expected:** `[5, 5]`
    - All approaches handle this correctly since `Math.max(5, 5) = 5` ✅

    ---

    ## 8. Final Summary

    | Criterion | BFS | DFS Recursive | DFS Iterative |
    |-----------|-----|---------------|---------------|
    | Intuitiveness | ⭐⭐⭐ Natural fit | ⭐⭐ Requires depth param | ⭐⭐ Verbose |
    | Time Complexity | O(n) | O(n) | O(n) |
    | Space (balanced) | O(n/2) = O(n) | O(log n) | O(log n) |
    | Space (skewed) | O(1) | O(n) ⚠️ | O(n) |
    | Overflow risk | None | None | None |
    | Interview recommended | ✅ Yes | ✅ Good alternative | Rarely preferred |

    ### ✅ Recommended: BFS

    BFS is the **canonical solution** — it naturally maps level-by-level processing to level-by-level iteration. In interviews, leading with BFS and then offering the DFS recursive solution as an alternative demonstrates strong breadth of knowledge.

    ### 🧠 Key Takeaway
    > **"Level-order problems → BFS with level-size snapshot."** Whenever a problem asks for something per-level (max, min, average, zigzag), reach for BFS and use `int levelSize = queue.size()` to isolate each level. Always initialize per-level aggregators with `Integer.MIN_VALUE` or `Integer.MAX_VALUE` to handle negative values safely.

    ---

    ## 🏢 Company Appearances

    | Company | Frequency | Notes |
    |---------|-----------|-------|
    | **Amazon** | ⭐⭐⭐⭐⭐ Very High | Appears frequently in SDE-1/SDE-2 loops |
    | **Microsoft** | ⭐⭐⭐⭐ High | Common in online assessments |
    | **Facebook/Meta** | ⭐⭐⭐ Medium | Appears as warm-up in tree rounds |
    | **Google** | ⭐⭐⭐ Medium | Used to test BFS fluency |
    | **Bloomberg** | ⭐⭐⭐ Medium | Common in phone screens |
    | **Apple** | ⭐⭐ Moderate | Occasionally appears |
    | **LinkedIn** | ⭐⭐ Moderate | Seen in early interview rounds |

    **LeetCode Problem #515** — Reported **400+ times** in interview reports historically. Difficulty: **Medium**. It is one of the most commonly used **BFS template problems** in technical interviews and serves as a gateway to harder level-order traversal problems like Binary Tree Level Order Traversal (LC #102) and Binary Tree Zigzag Level Order Traversal (LC #103).
    */
    // @formatter:on

}
