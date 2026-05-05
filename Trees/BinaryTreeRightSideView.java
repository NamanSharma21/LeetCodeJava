package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import Datastructures.TreeNode;

public class BinaryTreeRightSideView {
    public static void main(String[] args) {
        BinaryTreeRightSideView binaryTreeRightSideView = new BinaryTreeRightSideView();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.right = new TreeNode(5);
        root.right = new TreeNode(3);
        root.right.right = new TreeNode(4);
        System.out.println(
                "BinaryTreeRightSideView : " + binaryTreeRightSideView.rightSideViewDFSRightFirstPreOrder(root));

        TreeNode root1 = new TreeNode(1);
        root1.right = new TreeNode(3);
        System.out.println(
                "BinaryTreeRightSideView : " + binaryTreeRightSideView.rightSideViewDFSRightFirstPreOrder(root1));

        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(3);
        root2.left.left = new TreeNode(4);
        root2.left.left.left = new TreeNode(5);
        System.out.println(
                "BinaryTreeRightSideView : " + binaryTreeRightSideView.rightSideViewDFSRightFirstPreOrder(root2));

        System.out.println(
                "BinaryTreeRightSideView : " + binaryTreeRightSideView.rightSideViewBFS(root));

        System.out.println(
                "BinaryTreeRightSideView : " + binaryTreeRightSideView.rightSideViewBFS(root1));

        System.out.println(
                "BinaryTreeRightSideView : " + binaryTreeRightSideView.rightSideViewBFS(root2));

        System.out.println(
                "BinaryTreeRightSideView : " + binaryTreeRightSideView.rightSideViewBFSNullSentinel(root));

        System.out.println(
                "BinaryTreeRightSideView : " + binaryTreeRightSideView.rightSideViewBFSNullSentinel(root1));

        System.out.println(
                "BinaryTreeRightSideView : " + binaryTreeRightSideView.rightSideViewBFSNullSentinel(root2));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/binary-tree-right-side-view/description/?envType=problem-list-v2&envId=tree
     * 
     * Given the root of a binary tree, imagine yourself standing on the right side
     * of it, return the values of the nodes you can see ordered from top to bottom.
     * 
     *       1
     *      / \
     *     2   3
     *      \    \
     *       5    4
     * Output: [1, 3, 4]
     * 
     * Example 1:
     * 
     * Input: root = [1,2,3,null,5,null,4]
     * 
     * Output: [1,3,4]
     * 
     *        1
     *       / \
     *      2   3
     *     /
     *    4
     *   /
     *  5
     * 
     * 
     * Example 2:
     * 
     *       1
     *        \
     *         3
     * Output: [1, 3]
     * 
     * 
     * Input: root = [1,2,3,4,null,null,null,5]
     * 
     * Output: [1,3,4,5]
     * 
     * 
     * 
     * 
     * Example 3:
     * 
     * Input: root = [1,null,3]
     * 
     * Output: [1,3]
     * 
     * Example 4:
     * 
     * Input: root = []
     * 
     * Output: []
     * 
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [0, 100].
     * -100 <= Node.val <= 100
     */
    // @formatter:on

    public List<Integer> rightSideViewDFSRightFirstPreOrder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        dfsHelper(root, 0, result);
        return result;
    }

    public void dfsHelper(TreeNode root, int depth, List<Integer> result) {
        if (root == null)
            return;
        if (depth == result.size())
            result.add(root.val);
        dfsHelper(root.right, depth + 1, result);
        dfsHelper(root.left, depth + 1, result);
    }

    public List<Integer> rightSideViewBFS(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                TreeNode current = queue.poll();
                if (i == queueSize - 1)
                    result.add(current.val);
                if (current.left != null)
                    queue.offer(current.left);
                if (current.right != null)
                    queue.offer(current.right);
            }
        }
        return result;
    }

    public List<Integer> rightSideViewBFSNullSentinel(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        queue.offer(null);
        while (!queue.isEmpty()) {
            int queueSize = queue.size();
            TreeNode lastSeen = null;
            for (int i = 0; i < queueSize; i++) {
                TreeNode current = queue.poll();
                if (current == null) {
                    result.add(lastSeen.val);
                    if (!queue.isEmpty())
                        queue.offer(null);
                } else {
                    lastSeen = current;
                    if (current.left != null)
                        queue.offer(current.left);
                    if (current.right != null)
                        queue.offer(current.right);
                }

            }
        }
        return result;
    }

    // @formatter:off
    /*
    # Binary Tree Right Side View — Deep Dive

    ---

    ## 1. Problem Statement

    ### In Plain English
    Given the root of a binary tree, imagine yourself standing on the **right side** of it. You can only see the nodes that are **visible from the right** — meaning the **last (rightmost) node at each level** of the tree.

    Return a list of those visible node values, from top to bottom.

    ### Input / Output
    - **Input:** `TreeNode root` — the root of a binary tree (may be `null`)
    - **Output:** `List<Integer>` — values of rightmost visible nodes, one per level, top to bottom

    ### Constraints
    - Number of nodes: `[0, 100]`
    - Node values: `-100 ≤ Node.val ≤ 100`

    ### What Exactly Needs to Be Computed
    For each depth level of the tree, find the **value of the rightmost node** at that level and collect them all in order from root depth to maximum depth.

    ---

    ## 2. Intuition

    ### The Core Idea
    Think of shining a horizontal flashlight at the tree from the right side. At each level (row) of the tree, only the **last node you'd encounter** (rightmost) is visible; everything to its left is blocked.

    ### How a Human Reasons About It
    1. Process the tree **level by level** (BFS makes this natural).
    2. At each level, the **last node processed** is the one visible from the right.
    3. Alternatively, do DFS but always visit the **right child before the left**, and record the first node you see at each new depth.

    ### What Makes It Interesting
    - It seems like "just take all right children," but that's **wrong** — if a right child is `null` but left child exists, the left child becomes visible.
    - The insight is **level-order processing**, not just pointer-following.

    ---

    ## 3. Approach Overview

    | # | Approach | Key Idea | Complexity | Use Case |
    |---|----------|----------|------------|----------|
    | 1 | **BFS (Level Order)** | Queue-based traversal; record last node per level | O(n) time, O(n) space | ✅ Most intuitive — **Optimal for interviews** |
    | 2 | **DFS (Right-first)** | Recurse right before left; first visit at each depth = answer | O(n) time, O(h) space | ✅ Elegant, space-efficient for balanced trees |
    | 3 | **BFS with null sentinel** | Use `null` markers in queue to detect level boundaries | O(n) time, O(n) space | Less clean, avoid in interviews |

    **Recommended:** BFS (Approach 1) for clarity; DFS (Approach 2) for space efficiency on balanced trees.

    ---

    ## 4. Detailed Solutions in Java

    ### Approach 1 — BFS (Level-Order Traversal)

    #### Algorithm Step-by-Step
    1. Handle edge case: if `root` is `null`, return empty list.
    2. Initialize a `Queue<TreeNode>` and add `root`.
    3. While the queue is not empty:
    - Determine the number of nodes at the **current level** (`levelSize`).
    - Iterate through exactly `levelSize` nodes.
    - For the **last node** in this level, add its value to the result.
    - Enqueue left and right children (if not null).
    4. Return the result list.

    ```java
    import java.util.*;

    public class BinaryTreeRightSideView {

        public List<Integer> rightSideView(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            if (root == null) return result;

            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                int levelSize = queue.size(); // number of nodes at this level

                for (int i = 0; i < levelSize; i++) {
                    TreeNode current = queue.poll();

                    // Last node in this level is visible from the right
                    if (i == levelSize - 1) {
                        result.add(current.val);
                    }

                    // Enqueue children for next level (left first, then right)
                    if (current.left != null)  queue.offer(current.left);
                    if (current.right != null) queue.offer(current.right);
                }
            }

            return result;
        }
    }
    ```

    ---

    ### Approach 2 — DFS (Right-First Preorder)

    #### Algorithm Step-by-Step
    1. Recurse with parameters: `(node, depth, result)`.
    2. Base case: if `node == null`, return.
    3. If `depth == result.size()`, this is the **first time we're visiting this depth** → add the node's value. Since we go right first, the first node seen at each depth is the rightmost.
    4. Recurse **right child** first (depth + 1), then left child (depth + 1).

    ```java
    import java.util.*;

    public class BinaryTreeRightSideViewDFS {

        public List<Integer> rightSideView(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            dfs(root, 0, result);
            return result;
        }

        private void dfs(TreeNode node, int depth, List<Integer> result) {
            if (node == null) return;

            // First node encountered at this depth (right-first) is the answer
            if (depth == result.size()) {
                result.add(node.val);
            }

            // Visit right before left to ensure rightmost node is seen first
            dfs(node.right, depth + 1, result);
            dfs(node.left,  depth + 1, result);
        }
    }
    ```

    ---

    ### Approach 3 — BFS with Null Sentinel (Illustrative Only)

    #### Algorithm Step-by-Step
    Use `null` as a level-end marker in the queue. When you dequeue `null`, the previous node was the last of that level.

    ```java
    import java.util.*;

    public class BinaryTreeRightSideViewSentinel {

        public List<Integer> rightSideView(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            if (root == null) return result;

            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            queue.offer(null); // sentinel marks end of level 0

            TreeNode lastSeen = null;

            while (!queue.isEmpty()) {
                TreeNode current = queue.poll();

                if (current == null) {
                    // End of a level — lastSeen is the rightmost node
                    result.add(lastSeen.val);
                    if (!queue.isEmpty()) {
                        queue.offer(null); // sentinel for next level
                    }
                } else {
                    lastSeen = current;
                    if (current.left  != null) queue.offer(current.left);
                    if (current.right != null) queue.offer(current.right);
                }
            }

            return result;
        }
    }
    ```
    > ⚠️ **Note:** This approach works but is less clean. Avoid in interviews unless you explain the sentinel pattern explicitly.

    ---

    ## 5. Time & Space Complexity

    ### Approach 1 — BFS

    | | Complexity | Reasoning |
    |--|-----------|-----------|
    | **Time** | O(n) | Every node is enqueued and dequeued exactly once |
    | **Space** | O(w) ≈ O(n) | Queue holds at most the widest level; worst case (complete tree) the last level has ~n/2 nodes |

    **Example walk-through (n=7, complete tree):**
    - Level 0: 1 node processed
    - Level 1: 2 nodes processed
    - Level 2: 4 nodes processed
    - Total operations = 7 = n ✅

    ### Approach 2 — DFS

    | | Complexity | Reasoning |
    |--|-----------|-----------|
    | **Time** | O(n) | Each node is visited exactly once |
    | **Space** | O(h) | Recursion stack depth equals tree height; O(log n) balanced, O(n) skewed |

    **Example (n=100, balanced tree):**
    - Height ≈ log₂(100) ≈ 7
    - Stack depth never exceeds 7 frames — very memory efficient

    ### Approach 3 — BFS Sentinel

    | | Complexity | Reasoning |
    |--|-----------|-----------|
    | **Time** | O(n) | Same as BFS |
    | **Space** | O(n) | Same as BFS; slightly more due to null sentinels |

    ---

    ## 6. Complete Worked Examples

    ### Example 1 — Standard Tree (BFS, Approach 1)

    ```
    Input tree:
            1
        / \
        2   3
        \   \
            5   4
    ```

    **Step-by-step BFS:**

    | Iteration | Queue State (before poll) | levelSize | Nodes Processed | Last Node (added to result) |
    |-----------|--------------------------|-----------|-----------------|----------------------------|
    | Level 0 | [1] | 1 | 1 | **1** |
    | Level 1 | [2, 3] | 2 | 2, 3 | **3** |
    | Level 2 | [5, 4] | 2 | 5, 4 | **4** |

    **Output:** `[1, 3, 4]` ✅

    ---

    ### Example 2 — Left-Heavy Tree (Shows Why "Just Follow Right" is Wrong)

    ```
    Input tree:
            1
        /
        2
        /
        3
    ```

    **BFS walkthrough:**

    | Level | Queue | levelSize | Last Node |
    |-------|-------|-----------|-----------|
    | 0 | [1] | 1 | **1** |
    | 1 | [2] | 1 | **2** |
    | 2 | [3] | 1 | **3** |

    **Output:** `[1, 2, 3]` — all left-side nodes are visible! ✅

    ---

    ### Example 3 — Same Tree via DFS (Approach 2)

    ```
            1
        / \
        2   3
        \
            5
    ```

    **DFS call trace (right before left):**

    ```
    dfs(1, depth=0) → depth==result.size(0) → result=[1]
    dfs(3, depth=1) → depth==result.size(1) → result=[1,3]
        dfs(null, depth=2) → return
        dfs(null, depth=2) → return
    dfs(2, depth=1) → depth(1) != result.size(2) → skip
        dfs(null, depth=2) → return (right of 2 is null)
        dfs(5, depth=2) → depth==result.size(2) → result=[1,3,5]
        dfs(null, depth=3) → return
        dfs(null, depth=3) → return
    ```

    **Output:** `[1, 3, 5]` ✅

    ---

    ## 7. Edge Cases

    | Edge Case | Input | Expected Output | BFS Handles? | DFS Handles? |
    |-----------|-------|-----------------|--------------|--------------|
    | **Empty tree** | `root = null` | `[]` | ✅ Early return | ✅ Base case null check |
    | **Single node** | `root = [1]` | `[1]` | ✅ One level, one node | ✅ depth==0==result.size |
    | **All left children** | 1→2→3 (left only) | `[1,2,3]` | ✅ Last node per level = the only node | ✅ Right null, left visited |
    | **All right children** | 1→2→3 (right only) | `[1,2,3]` | ✅ Right child always last | ✅ Right visited first |
    | **Complete balanced tree** | Full binary tree | Last node per level | ✅ | ✅ |
    | **Null right child, existing left** | Node has only left child | Left child IS visible | ✅ Correctly takes last in level | ✅ Goes left after right=null |
    | **Negative values** | Nodes with `-100` | Handled naturally | ✅ | ✅ |
    | **Single level (all leaves)** | Just the root | `[root.val]` | ✅ | ✅ |

    ### Notable Bug Risk
    In Approach 3 (Sentinel), if the queue is empty when you try to add `lastSeen.val` after dequeuing `null`, you must guard against `lastSeen` being `null`. The implementation above handles this correctly since we always set `lastSeen` before hitting `null`.

    ---

    ## 8. Final Summary

    ### Approach Comparison

    | Approach | Time | Space | Readability | Interview Suitability |
    |----------|------|-------|-------------|----------------------|
    | BFS (Level Order) | O(n) | O(n) | ⭐⭐⭐⭐⭐ | ✅ **Best choice** |
    | DFS (Right-first) | O(n) | O(h) | ⭐⭐⭐⭐ | ✅ Great alternative |
    | BFS Sentinel | O(n) | O(n) | ⭐⭐⭐ | ⚠️ Only if asked specifically |

    ### Recommendation
    **Use BFS in interviews** — it directly mirrors the mental model of "looking at each row." If the interviewer asks for a recursive solution, switch to DFS (right-first).

    ### Key Pattern to Remember
    > **"Right side view = last node at each level."**
    > BFS naturally groups nodes by level; DFS achieves the same by visiting right subtrees first and using depth as an index.

    ---

    ## 9. Companies & Frequency

    This problem (**LeetCode #199**) is a **high-frequency interview question**, especially at top tech companies:

    | Company | Frequency | Interview Round |
    |---------|-----------|-----------------|
    | **Amazon** | ⭐⭐⭐⭐⭐ Very High | SDE-1, SDE-2 Onsite |
    | **Facebook / Meta** | ⭐⭐⭐⭐⭐ Very High | Coding Rounds |
    | **Microsoft** | ⭐⭐⭐⭐ High | Technical Screen |
    | **Google** | ⭐⭐⭐⭐ High | Onsite Coding |
    | **Bloomberg** | ⭐⭐⭐⭐ High | Coding Screen |
    | **Apple** | ⭐⭐⭐ Medium | Coding Rounds |
    | **LinkedIn** | ⭐⭐⭐ Medium | SWE Interview |
    | **Uber** | ⭐⭐⭐ Medium | Technical Phone |
    | **Oracle** | ⭐⭐⭐ Medium | Multiple rounds |
    | **Salesforce** | ⭐⭐ Moderate | Coding Rounds |

    - Appeared **200+ times** on LeetCode discuss/reports across company tags
    - Consistently in the **Top 50 most-asked tree problems**
    - Particularly popular at **Amazon and Meta** where BFS/tree traversal problems are heavily tested
    */
    // @formatter:on
}
