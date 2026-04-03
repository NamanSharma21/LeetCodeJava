package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import Datastructures.TreeNode;

public class BinaryTreeLevelOrderTraversal {
    public static void main(String[] args) {
        BinaryTreeLevelOrderTraversal binaryTreeLevelOrderTraversal = new BinaryTreeLevelOrderTraversal();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);
        // binaryTreeLevelOrderTraversal.levelOrder(root);
        System.out.println("" + binaryTreeLevelOrderTraversal.levelOrder(root));
    }

    /*
     * Given the root of a binary tree, return the level order traversal of its
     * nodes' values. (i.e., from left to right, level by level).
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[3],[9,20],[15,7]]
     * Example 2:
     * 
     * Input: root = [1]
     * Output: [[1]]
     * Example 3:
     * 
     * Input: root = []
     * Output: []
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [0, 2000].
     * -1000 <= Node.val <= 1000
     */

    public List<List<Integer>> levelOrderIterativeBFS(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null)
            return result;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                TreeNode current = queue.poll();
                level.add(current.val);
                if (current.left != null)
                    queue.offer(current.left);
                if (current.right != null)
                    queue.offer(current.right);
            }
            result.add(level);
        }
        return result;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        preOrderDFS(root, 0, result);
        return result;
    }

    public void preOrderDFS(TreeNode root, int depth, List<List<Integer>> result) {
        if (root == null)
            return;

        if (depth == result.size())
            result.add(new ArrayList<>());

        result.get(depth).add(root.val);
        preOrderDFS(root.left, depth + 1, result);
        preOrderDFS(root.right, depth + 1, result);
    }


    /*
        # Binary Tree Level Order Traversal — Deep Dive

    ---

    ## 1. Problem Statement

    ### In Plain English
    Given the **root of a binary tree**, return all node values **grouped by their depth level** — all nodes at depth 0 (the root) in one group, all nodes at depth 1 in the next group, and so on, from **left to right** within each level.

    ### Input Format
    - A binary tree root node (`TreeNode root`)
    - `TreeNode` has: `int val`, `TreeNode left`, `TreeNode right`
    - Tree can have **0 to 2000 nodes**
    - Node values: `-1000 ≤ val ≤ 1000`

    ### Output Format
    ```
    List<List<Integer>>
    ```
    A list of lists, where each inner list contains all node values at that level, left to right.

    ### What Exactly Must Be Returned
    ```
    Input tree:         Output:
        3             [[3],
        / \             [9, 20],
        9  20            [15, 7]]
        /  \
        15   7
    ```
    If the tree is **empty**, return an **empty list** `[]`.

    ---

    ## 2. Intuition

    ### The Core Idea
    Imagine you're standing in front of a building (the tree) and taking a **floor-by-floor photograph**. You capture everyone on **floor 0** first, then **floor 1**, then **floor 2**, etc. That's exactly level order traversal — also called **Breadth-First Search (BFS)**.

    ### How a Human Reasons About It
    1. Start at the root — that's level 0.
    2. Look at the root's children — they form level 1.
    3. Look at those children's children — they form level 2.
    4. Continue until no more nodes exist.

    The key insight: **nodes at the same depth should be processed together and in order**, which naturally suggests a **queue** (FIFO — First In, First Out). You enqueue nodes left-to-right, and when you dequeue them, their children (the next level) get enqueued in the correct left-to-right order automatically.

    ### What Makes This Interesting
    - It's one of the **foundational tree traversal patterns** — dozens of harder problems (zigzag traversal, right side view, max width, etc.) are variations of this.
    - The tricky part is knowing **where one level ends and the next begins** while using a single queue.
    - There's an elegant trick: **snapshot the queue's size** at the start of each level — that tells you exactly how many nodes belong to the current level.

    ---

    ## 3. Approach Overview

    | # | Approach | Key Idea | Use When | Optimal? |
    |---|----------|----------|----------|----------|
    | 1 | **BFS with Queue** | Process nodes level by level using a queue | Always — this is the natural fit | ✅ Yes |
    | 2 | **DFS with Level Tracking** | Recursively traverse, pass depth as a parameter, insert into correct bucket | When recursion is preferred or stack space is acceptable | ✅ Equal complexity, different style |
    | 3 | **BFS with Null Sentinel** | Use `null` as a marker between levels in the queue | Conceptually interesting, slightly messier | ❌ Not recommended — harder to read |

    ### Which Is Optimal and Why?
    **Approach 1 (BFS with Queue)** is the canonical, interview-standard solution. It's iterative (no recursion stack risk), clean, and directly models the problem. Both BFS and DFS achieve O(n) time and O(n) space, but BFS is more natural here and easier to explain under pressure.

    ---

    ## 4. Detailed Solutions in Java

    ### Approach 1 — BFS with Queue (Optimal / Recommended)

    #### Algorithm, Step by Step
    1. Handle the edge case: if `root` is `null`, return an empty list immediately.
    2. Create a `Queue<TreeNode>` and enqueue the root.
    3. While the queue is not empty:
    - Record `levelSize = queue.size()` — this is how many nodes are on the current level.
    - Create an empty `currentLevel` list.
    - Loop exactly `levelSize` times:
        - Dequeue a node.
        - Add its value to `currentLevel`.
        - If it has a left child, enqueue it.
        - If it has a right child, enqueue it.
    - Add `currentLevel` to the result.
    4. Return the result.

    ```java
    import java.util.*;

    public class BinaryTreeLevelOrder {

        public static class TreeNode {
            int val;
            TreeNode left, right;
            TreeNode(int val) { this.val = val; }
        }

        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();

            if (root == null) return result; // Edge case: empty tree

            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root); // Start BFS from the root

            while (!queue.isEmpty()) {
                int levelSize = queue.size(); // Number of nodes at THIS level
                List<Integer> currentLevel = new ArrayList<>();

                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    currentLevel.add(node.val);

                    // Enqueue children for the NEXT level
                    if (node.left != null)  queue.offer(node.left);
                    if (node.right != null) queue.offer(node.right);
                }

                result.add(currentLevel); // Snapshot this level
            }

            return result;
        }
    }
    ```

    ---

    ### Approach 2 — DFS with Level (Depth) Tracking

    #### Algorithm, Step by Step
    1. Define a recursive helper that takes a node and its current depth.
    2. If the node is `null`, return (base case).
    3. If `depth == result.size()`, this is the first node we've seen at this depth — add a new empty list to `result`.
    4. Add the node's value to `result.get(depth)`.
    5. Recurse on the left child with `depth + 1`.
    6. Recurse on the right child with `depth + 1`.

    ```java
    import java.util.*;

    public class BinaryTreeLevelOrderDFS {

        public static class TreeNode {
            int val;
            TreeNode left, right;
            TreeNode(int val) { this.val = val; }
        }

        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();
            dfs(root, 0, result);
            return result;
        }

        private void dfs(TreeNode node, int depth, List<List<Integer>> result) {
            if (node == null) return; // Base case

            // First time we reach this depth — create a new level bucket
            if (depth == result.size()) {
                result.add(new ArrayList<>());
            }

            result.get(depth).add(node.val); // Place value in the correct level

            dfs(node.left,  depth + 1, result); // Go left first (preserves L→R order)
            dfs(node.right, depth + 1, result);
        }
    }
    ```

    ---

    ### Approach 3 — BFS with Null Sentinel (Illustrative Only)

    #### Algorithm, Step by Step
    Use `null` as a **"end-of-level" marker** placed after all nodes of a given level. When you dequeue a `null`, you know the current level just finished.

    ```java
    import java.util.*;

    public class BinaryTreeLevelOrderSentinel {

        public static class TreeNode {
            int val;
            TreeNode left, right;
            TreeNode(int val) { this.val = val; }
        }

        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();
            if (root == null) return result;

            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            queue.offer(null); // Sentinel marks end of level 0

            List<Integer> currentLevel = new ArrayList<>();

            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();

                if (node == null) {
                    // End of this level
                    result.add(new ArrayList<>(currentLevel));
                    currentLevel.clear();

                    // Add next sentinel only if more nodes remain
                    if (!queue.isEmpty()) queue.offer(null);
                } else {
                    currentLevel.add(node.val);
                    if (node.left  != null) queue.offer(node.left);
                    if (node.right != null) queue.offer(node.right);
                }
            }

            return result;
        }
    }
    ```
    > ⚠️ **Note:** This approach works but is harder to reason about and adds edge cases around the final sentinel. Avoid in interviews unless specifically prompted.

    ---

    ## 5. Time & Space Complexity

    ### Approach 1 — BFS with Queue

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | **O(n)** | Every node is enqueued once and dequeued once — exactly 2 operations per node |
    | **Space** | **O(n)** | The queue holds at most one full level at a time. In a perfect binary tree, the last level has n/2 nodes → O(n) |

    **Walk-through with numbers:**
    - Tree with **7 nodes** (3 levels): queue peaks at 4 nodes (last level) → ~14 queue operations total.
    - Tree with **2000 nodes**: at most 1000 nodes in the queue at once at the widest level.

    ### Approach 2 — DFS with Depth

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | **O(n)** | We visit each node exactly once in the recursion |
    | **Space** | **O(h)** call stack + **O(n)** result | h = height of tree. Worst case (skewed tree): h = n → O(n) total |

    **Important distinction:** DFS uses **call stack** space proportional to tree height, which is O(log n) for balanced trees but O(n) for degenerate (skewed) trees. BFS queue space is O(n) in the worst case for either shape.

    ### Approach 3 — BFS with Sentinel

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | **O(n)** | Same as Approach 1 |
    | **Space** | **O(n)** | Same queue behavior, plus one extra null per level (negligible) |

    ---

    ## 6. Complete Worked Examples

    ### Example — Approach 1 (BFS)

    **Input Tree:**
    ```
            3
        / \
        9  20
            /  \
        15   7
    ```

    **Step-by-step execution:**

    | Iteration | `queue.size()` (levelSize) | Nodes Dequeued | Children Enqueued | `currentLevel` | `result` so far |
    |-----------|--------------------------|----------------|-------------------|----------------|-----------------|
    | 1 | 1 | 3 | 9, 20 | [3] | [[3]] |
    | 2 | 2 | 9, 20 | 15, 7 (from 20) | [9, 20] | [[3],[9,20]] |
    | 3 | 2 | 15, 7 | (none) | [15, 7] | [[3],[9,20],[15,7]] |

    **Queue state at each moment:**
    ```
    Initial:        [3]
    After level 0:  [9, 20]
    After level 1:  [15, 7]
    After level 2:  []  ← loop ends
    ```

    **Final Output:** `[[3], [9, 20], [15, 7]]` ✅

    ---

    ### Example — Approach 2 (DFS)

    **Same input tree.** DFS visits: 3 → 9 → 20 → 15 → 7

    | Call | `node` | `depth` | `result` before | Action |
    |------|--------|---------|-----------------|--------|
    | 1 | 3 | 0 | `[]` | depth==size → add bucket; result=`[[3]]` |
    | 2 | 9 | 1 | `[[3]]` | depth==size → add bucket; result=`[[3],[9]]` |
    | 3 | null | 2 | — | return (9 has no children) |
    | 4 | 20 | 1 | `[[3],[9]]` | depth<size → append; result=`[[3],[9,20]]` |
    | 5 | 15 | 2 | `[[3],[9,20]]` | depth==size → add bucket; result=`[[3],[9,20],[15]]` |
    | 6 | 7 | 2 | `[[3],[9,20],[15]]` | depth<size → append; result=`[[3],[9,20],[15,7]]` |

    **Final Output:** `[[3], [9, 20], [15, 7]]` ✅

    ---

    ### Example — Single Node (Edge Case)

    **Input:** `root = TreeNode(1)`, no children

    **BFS:**
    - Enqueue `1`. `levelSize = 1`. Dequeue `1`, no children enqueued.
    - `result = [[1]]`

    **Output:** `[[1]]` ✅

    ---

    ## 7. Edge Cases

    | Edge Case | Description | How BFS Handles It | How DFS Handles It |
    |-----------|-------------|-------------------|-------------------|
    | **Empty tree** | `root == null` | Returns `[]` immediately (null check at top) | `dfs(null, 0, result)` returns immediately |
    | **Single node** | Root with no children | One iteration: dequeues root, enqueues nothing | One call: adds bucket, adds value, both recurse to null |
    | **Skewed left tree** | Each node only has a left child (like a linked list) | Queue never holds more than 1 node — very memory efficient | Recursion depth = n — **risk of StackOverflowError** for n=2000+ |
    | **Skewed right tree** | Each node only has a right child | Same as above | Same stack overflow risk |
    | **Perfect binary tree** | All levels full | Queue peaks at n/2 nodes (last level) — maximum memory usage | Stack depth = log₂(n) — very efficient |
    | **Negative values** | e.g., `val = -1000` | No issue — values are just stored as-is | No issue |
    | **All same values** | e.g., all nodes have `val = 0` | Works fine — no uniqueness assumption | Works fine |
    | **Large tree (n=2000)** | Max constraint | Well within queue capacity | Skewed case may hit Java default stack limit (~500-1000 deep) |

    > ⚠️ **Key risk with DFS:** On a perfectly skewed tree of 2000 nodes, the recursion depth is 2000. Java's default thread stack typically supports ~500–1000 recursive calls safely. BFS has **no such risk** since it's iterative.

    ---

    ## 8. Final Summary

    ### Comparison Table

    | Criterion | BFS (Queue) | DFS (Recursive) | BFS (Sentinel) |
    |-----------|-------------|-----------------|----------------|
    | Intuition | Natural fit ✅ | Clever but indirect | Roundabout |
    | Code clarity | Very clean ✅ | Clean ✅ | Messy ❌ |
    | Stack overflow risk | None ✅ | Yes (skewed trees) ⚠️ | None ✅ |
    | Interview preference | ⭐ First choice | Good alternative | Not recommended |
    | Time complexity | O(n) | O(n) | O(n) |
    | Space complexity | O(n) | O(n) worst | O(n) |

    ### ✅ Recommendation
    **Use BFS with Queue (Approach 1)** in all interviews and production code. It's the clearest expression of the problem's intent and has no recursion depth risk.

    ### 🧠 What to Remember
    > **"Level order traversal = BFS with a queue. Snapshot `queue.size()` at the start of each level to know exactly how many nodes belong to it."**

    This **size-snapshotting pattern** is the core building block for at least a dozen LeetCode problems: zigzag traversal (102→103), right side view (199), level averages (637), max depth (104), and more. Master this pattern and all those problems become straightforward variations.
        */

}
