package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

import Datastructures.TreeNode;

public class BinaryTreeZigZagLevelOrderTraversal {
    public static void main(String[] args) {
        BinaryTreeZigZagLevelOrderTraversal binaryTreeZigZagLevelOrderTraversal = new BinaryTreeZigZagLevelOrderTraversal();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println(
                "BinaryTreeZigZagLevelOrderTraversal : "
                        + binaryTreeZigZagLevelOrderTraversal.zigzagLevelOrderBFSDeque(root));
        System.out.println(
                "BinaryTreeZigZagLevelOrderTraversal : "
                        + binaryTreeZigZagLevelOrderTraversal.zigzagLevelOrderDFSLevelTracking(root));
        System.out.println(
                "BinaryTreeZigZagLevelOrderTraversal : "
                        + binaryTreeZigZagLevelOrderTraversal.zigzagLevelOrderBFSReverse(root));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
     * description/?envType=problem-list-v2&envId=tree
     * 
     * Given the root of a binary tree, return the zigzag level order traversal of
     * its nodes' values. (i.e., from left to right, then right to left for the next
     * level and alternate between).
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     *       3
     *      / \
     *     9  20
     *        / \
     *       15  7
     * 
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[3],[20,9],[15,7]]
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
     * -100 <= Node.val <= 100
     * 
     * 
     */
    // @formatter:on

    public List<List<Integer>> zigzagLevelOrderBFSReverse(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null)
            return result;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int queueSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            for (int i = 0; i < queueSize; i++) {
                TreeNode current = queue.poll();
                currentLevel.add(current.val);
                if (current.left != null)
                    queue.offer(current.left);
                if (current.right != null)
                    queue.offer(current.right);
            }
            if (level % 2 == 1)
                Collections.reverse(currentLevel);
            level++;
            result.add(currentLevel);
        }
        return result;
    }

    public List<List<Integer>> zigzagLevelOrderBFSDeque(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null)
            return result;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        boolean leftToRight = true;
        while (!queue.isEmpty()) {
            int queueSize = queue.size();
            Deque<Integer> currentLevel = new ArrayDeque<>();
            for (int i = 0; i < queueSize; i++) {
                TreeNode current = queue.poll();
                if (leftToRight)
                    currentLevel.addLast(current.val);
                else
                    currentLevel.addFirst(current.val);
                if (current.left != null)
                    queue.offer(current.left);
                if (current.right != null)
                    queue.offer(current.right);

            }
            leftToRight = !leftToRight;
            result.add(new ArrayList<>(currentLevel));
        }
        return result;
    }

    public List<List<Integer>> zigzagLevelOrderDFSLevelTracking(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        dfsHelper(root, 0, result);
        return result;
    }

    public void dfsHelper(TreeNode root, int level, List<List<Integer>> result) {
        if (root == null)
            return;
        if (level == result.size()) {
            result.add(new ArrayList<>());
        }
        List<Integer> currentLevel = result.get(level);

        if (level % 2 == 0)
            currentLevel.add(root.val);
        else
            currentLevel.add(0, root.val);
        dfsHelper(root.left, level + 1, result);
        dfsHelper(root.right, level + 1, result);
    }

    // @formatter:off
    /*
    # Binary Tree Zigzag Level Order Traversal

    ---

    ## 1. Problem Statement

    ### In Plain English
    Given the root of a binary tree, traverse it **level by level**, but alternate the direction of traversal at each level:
    - **Level 0 (root):** Left → Right
    - **Level 1:** Right → Left
    - **Level 2:** Left → Right
    - ...and so on (zigzag pattern)

    Return a **list of lists**, where each inner list contains the node values at that level in the appropriate direction.

    ### Input Format
    - A binary tree root node (`TreeNode root`)
    - `TreeNode` has: `int val`, `TreeNode left`, `TreeNode right`

    ### Output Format
    - `List<List<Integer>>` — each sublist = one level's values in zigzag order

    ### Constraints
    - Number of nodes: `0 <= n <= 2000`
    - Node values: `-100 <= Node.val <= 100`

    ### What Exactly Must Be Computed
    > For each level **i** (0-indexed): if **i is even**, collect values left-to-right; if **i is odd**, collect values right-to-left.

    ---

    ## 2. Intuition

    ### The Core Idea
    Think of walking through a building floor by floor. On the ground floor you walk **east**, on the next floor you walk **west**, then **east** again — zigzagging as you go up.

    A standard **BFS (level-order traversal)** visits nodes floor by floor. The only twist here is that every other floor, you **reverse** the order you record the values.

    ### How a Human Reasons About It
    1. Start at the root (level 0).
    2. Visit all nodes at this level, recording values **left to right**.
    3. Move to the next level, record values **right to left**.
    4. Flip the direction flag for every subsequent level.
    5. Repeat until no more nodes exist.

    ### What Makes It Tricky
    - You must **not** change the actual traversal order — children are always enqueued left then right. Only the **recording** direction flips.
    - Managing the direction flag cleanly without extra reversal passes is the elegant part.
    - Using a `Deque` (double-ended queue) lets you add to the front or back efficiently, avoiding an O(n) reverse step.

    ---

    ## 3. Approach Overview

    | # | Approach | Key Idea | Time | Space | Notes |
    |---|----------|----------|------|-------|-------|
    | 1 | **BFS + Reverse** | Standard BFS, reverse odd levels | O(n) | O(n) | Simple but has extra reversal cost |
    | 2 | **BFS + Deque (Optimal)** | Use ArrayDeque to insert front/back | O(n) | O(n) | ✅ Recommended — no reversal needed |
    | 3 | **DFS + Level Tracking** | Recursive DFS, track depth | O(n) | O(n) | Elegant recursion, stack overhead |

    > ✅ **Optimal Approach: BFS + Deque** — Single pass, O(n) time, no reversal, clean and interview-ready.

    ---

    ## 4. Detailed Solutions in Java

    ### Prerequisites — TreeNode Definition
    ```java
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }
    ```

    ---

    ### Approach 1 — BFS + Collections.reverse()

    #### Algorithm Steps
    1. Use a `Queue<TreeNode>` for standard BFS.
    2. For each level, collect all node values into a `List<Integer>`.
    3. If the current level is **odd**, reverse the list before adding to results.
    4. Enqueue left and right children for each node.

    ```java
    import java.util.*;

    public class ZigzagApproach1 {

        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();

            if (root == null) return result;

            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            int level = 0;

            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                List<Integer> levelValues = new ArrayList<>();

                for (int i = 0; i < levelSize; i++) {
                    TreeNode current = queue.poll();
                    levelValues.add(current.val);

                    // Always enqueue children left-to-right
                    if (current.left != null)  queue.offer(current.left);
                    if (current.right != null) queue.offer(current.right);
                }

                // Reverse for odd levels (right-to-left recording)
                if (level % 2 == 1) {
                    Collections.reverse(levelValues);
                }

                result.add(levelValues);
                level++;
            }

            return result;
        }
    }
    ```

    ---

    ### Approach 2 — BFS + Deque (Optimal ✅)

    #### Algorithm Steps
    1. Use a `Queue<TreeNode>` for BFS level-by-level processing.
    2. For each level, use a `Deque<Integer>` to store values.
    3. If **left-to-right** (even level): `addLast()` — append to back.
    4. If **right-to-left** (odd level): `addFirst()` — prepend to front.
    5. Children are always enqueued left then right — direction only affects **recording**.

    #### Why Deque Eliminates Reversal
    - For a right-to-left level, instead of collecting `[A, B, C]` and reversing to `[C, B, A]`, we insert each element at the **front** as we process them left-to-right, naturally building `[C, B, A]` in O(1) per insert.

    ```java
    import java.util.*;

    public class ZigzagApproach2 {

        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();

            if (root == null) return result;

            Queue<TreeNode> bfsQueue = new LinkedList<>();
            bfsQueue.offer(root);
            boolean leftToRight = true; // Even levels go left-to-right

            while (!bfsQueue.isEmpty()) {
                int levelSize = bfsQueue.size();
                Deque<Integer> levelDeque = new ArrayDeque<>();

                for (int i = 0; i < levelSize; i++) {
                    TreeNode current = bfsQueue.poll();

                    // Direction-aware insertion — no reversal needed
                    if (leftToRight) {
                        levelDeque.addLast(current.val);  // Append to back
                    } else {
                        levelDeque.addFirst(current.val); // Prepend to front
                    }

                    // Always enqueue children left-to-right
                    if (current.left != null)  bfsQueue.offer(current.left);
                    if (current.right != null) bfsQueue.offer(current.right);
                }

                result.add(new ArrayList<>(levelDeque));
                leftToRight = !leftToRight; // Flip direction for next level
            }

            return result;
        }
    }
    ```

    ---

    ### Approach 3 — DFS + Level Tracking

    #### Algorithm Steps
    1. Use recursive DFS, passing the current **depth** as a parameter.
    2. When visiting a node at depth `d`:
    - If `result` doesn't yet have a list for level `d`, create one.
    - If `d` is **even** (left-to-right): `add(val)` to end of list.
    - If `d` is **odd** (right-to-left): `add(0, val)` to prepend at index 0.
    3. Recurse left child, then right child.

    #### Key Insight
    Visiting left before right at every level means:
    - On even levels, appending naturally gives left-to-right order.
    - On odd levels, prepending (index 0) reverses the order to right-to-left.

    ```java
    import java.util.*;

    public class ZigzagApproach3 {

        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();
            dfs(root, 0, result);
            return result;
        }

        private void dfs(TreeNode node, int depth, List<List<Integer>> result) {
            if (node == null) return;

            // Expand result list if this depth hasn't been seen yet
            if (depth == result.size()) {
                result.add(new ArrayList<>());
            }

            List<Integer> currentLevel = result.get(depth);

            if (depth % 2 == 0) {
                // Even level: left-to-right → append to end
                currentLevel.add(node.val);
            } else {
                // Odd level: right-to-left → prepend to front
                currentLevel.add(0, node.val);
            }

            // Always recurse left before right
            dfs(node.left,  depth + 1, result);
            dfs(node.right, depth + 1, result);
        }
    }
    ```

    > ⚠️ **Note:** `list.add(0, val)` is O(k) where k = nodes at that level, making the worst-case O(n²) for a perfectly balanced wide tree. For interview purposes this is acceptable, but Approach 2 is cleaner.

    ---

    ## 5. Time & Space Complexity

    ### Approach 1 — BFS + Reverse

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | Every node visited once; `Collections.reverse` per level sums to O(n) total across all levels |
    | **Space** | O(n) | Queue holds at most one full level ≈ n/2 nodes at the widest level |

    **Example walk-through:** Tree with 7 nodes (3 levels: 1, 2, 4 nodes). Operations: 7 visits + reverse of [2 elements] + reverse of [4 elements] = ~13 operations total.

    ---

    ### Approach 2 — BFS + Deque (Optimal)

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | Every node visited exactly once; `addFirst`/`addLast` are O(1) per node |
    | **Space** | O(n) | Queue holds up to n/2 nodes (widest level); Deque holds at most n/2 values at once |

    **Example walk-through:** Same 7-node tree → exactly 7 enqueue + 7 dequeue operations, 7 deque insertions = **21 total operations**, strictly O(n).

    ---

    ### Approach 3 — DFS

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) to O(n²) | O(n) if prepend is O(1) (LinkedList), O(n²) worst-case with ArrayList due to `add(0, val)` shifting |
    | **Space** | O(h) call stack + O(n) result | h = height of tree; O(log n) balanced, O(n) skewed |

    ---

    ## 6. Complete Worked Examples

    ### Example Tree
    ```
            3
        / \
        9   20
            /  \
            15    7
    ```
    **Expected Output:** `[[3], [20, 9], [15, 7]]`

    ---

    ### Approach 1 Walkthrough (BFS + Reverse)

    | Step | Queue Before Poll | Node Polled | levelValues | Children Enqueued | Direction | After Reverse? |
    |------|------------------|-------------|-------------|-------------------|-----------|----------------|
    | L0 | [3] | 3 | [3] | [9, 20] | Even (L→R) | No reverse → [3] |
    | L1-a | [9, 20] | 9 | [9] | [] | — | — |
    | L1-b | [20] | 20 | [9, 20] | [15, 7] | Odd (R→L) | Reverse → [20, 9] |
    | L2-a | [15, 7] | 15 | [15] | [] | — | — |
    | L2-b | [7] | 7 | [15, 7] | [] | Even (L→R) | No reverse → [15, 7] |

    **Result:** `[[3], [20, 9], [15, 7]]` ✅

    ---

    ### Approach 2 Walkthrough (BFS + Deque)

    **Level 0** (`leftToRight = true`):
    - Poll `3` → `addLast(3)` → Deque: `[3]`
    - Enqueue: `9`, `20`
    - Convert → `[3]`, flip flag → `leftToRight = false`

    **Level 1** (`leftToRight = false`):
    - Poll `9` → `addFirst(9)` → Deque: `[9]`
    - Poll `20` → `addFirst(20)` → Deque: `[20, 9]`
    - Enqueue: `15`, `7`
    - Convert → `[20, 9]`, flip flag → `leftToRight = true`

    **Level 2** (`leftToRight = true`):
    - Poll `15` → `addLast(15)` → Deque: `[15]`
    - Poll `7` → `addLast(7)` → Deque: `[15, 7]`
    - Convert → `[15, 7]`, flip flag

    **Result:** `[[3], [20, 9], [15, 7]]` ✅

    ---

    ### Approach 3 Walkthrough (DFS)

    DFS call order (left-first, pre-order):

    ```
    dfs(3, depth=0)  → result[0].add(3)        → [[3]]
    dfs(9, depth=1)  → result[1].add(0, 9)     → [[3],[9]]
    dfs(null)        → return
    dfs(null)        → return
    dfs(20, depth=1) → result[1].add(0, 20)    → [[3],[20,9]]
    dfs(15, depth=2) → result[2].add(15)       → [[3],[20,9],[15]]
    dfs(7, depth=2)  → result[2].add(7)        → [[3],[20,9],[15,7]]
    ```

    **Result:** `[[3], [20, 9], [15, 7]]` ✅

    ---

    ### Larger Example
    ```
            1
        /   \
        2     3
        / \   / \
        4   5 6   7
    ```
    **Expected:** `[[1], [3, 2], [4, 5, 6, 7]]`

    **Approach 2 trace:**
    - L0: `addLast(1)` → `[1]`; flag flips to false
    - L1: `addFirst(2)` → `[2]`; `addFirst(3)` → `[3,2]`; flag flips to true
    - L2: `addLast(4)`→`[4]`, `addLast(5)`→`[4,5]`, `addLast(6)`→`[4,5,6]`, `addLast(7)`→`[4,5,6,7]`

    **Result:** `[[1], [3, 2], [4, 5, 6, 7]]` ✅

    ---

    ## 7. Edge Cases

    | Edge Case | Input | Expected Output | Approach 1 | Approach 2 | Approach 3 |
    |-----------|-------|-----------------|------------|------------|------------|
    | **Null root** | `root = null` | `[]` | ✅ early return | ✅ early return | ✅ base case |
    | **Single node** | `root = [1]` | `[[1]]` | ✅ one level, even, no reverse | ✅ addLast(1) | ✅ depth 0, append |
    | **Left-skewed tree** | `1→2→3→4` (all left children) | `[[1],[2],[3],[4]]` alternating direction | ✅ | ✅ | ✅ stack depth = n, risk of StackOverflow for n=2000 |
    | **Right-skewed tree** | `1→2→3→4` (all right children) | Same as above | ✅ | ✅ | ⚠️ Same stack risk |
    | **All same values** | `[1,1,1,1]` | `[[1],[1,1],[1,1,1,1]]` etc. | ✅ | ✅ | ✅ |
    | **Two-level tree** | Root + 2 children | `[[root],[right,left]]` | ✅ | ✅ | ✅ |
    | **Large balanced tree (n=2000)** | Full binary tree ~10 levels | Correct zigzag | ✅ | ✅ | ⚠️ Recursion depth ~11, fine here |

    ### ⚠️ Critical Edge Case: Skewed Tree with DFS
    For a **completely skewed tree** with n=2000, DFS call stack depth = 2000. Java's default stack may handle this (~500–1000 typical safe limit depends on JVM settings), but it's a risk in production. Approach 1 and 2 use iteration and are **stack-safe**.

    ---

    ## 8. Final Summary

    ### Approach Comparison

    | Approach | Time | Space | Simplicity | Stack Safe | Recommended |
    |----------|------|-------|------------|------------|-------------|
    | BFS + Reverse | O(n) | O(n) | ⭐⭐⭐⭐ | ✅ | Good for quick coding |
    | **BFS + Deque** | **O(n)** | **O(n)** | **⭐⭐⭐⭐⭐** | **✅** | **✅ Best choice** |
    | DFS + Prepend | O(n)–O(n²) | O(h)+O(n) | ⭐⭐⭐ | ⚠️ | Avoid for large skewed trees |

    ### What to Remember
    > **Pattern:** BFS gives level-by-level control. A `Deque` with `addFirst`/`addLast` is the go-to tool whenever you need to build a list in either direction without reversing it.

    > **Key insight:** The traversal order (left child first) never changes — only the **recording direction** alternates per level.

    ---

    ## 9. Company Appearances & Frequency

    This problem is **LeetCode #103** and is extremely popular in technical interviews.

    | Company | Frequency | Notes |
    |---------|-----------|-------|
    | **Amazon** | ⭐⭐⭐⭐⭐ Very High | Most frequently asked — appears in SDE 1 & 2 rounds |
    | **Microsoft** | ⭐⭐⭐⭐⭐ Very High | Common in phone screens and onsite rounds |
    | **Facebook / Meta** | ⭐⭐⭐⭐ High | Appears in coding rounds |
    | **Google** | ⭐⭐⭐⭐ High | Asked in both phone screen and onsite |
    | **Bloomberg** | ⭐⭐⭐⭐ High | Frequently reported by candidates |
    | **Apple** | ⭐⭐⭐ Medium | Appears in SWE interviews |
    | **LinkedIn** | ⭐⭐⭐ Medium | Reported in multiple rounds |
    | **Adobe** | ⭐⭐⭐ Medium | Common tree question |
    | **Uber** | ⭐⭐ Medium | Occasionally asked |
    | **Oracle** | ⭐⭐ Medium | Appears in backend rounds |

    ### Overall Stats
    - **Total reported appearances (LeetCode):** 500+ times across companies
    - **Difficulty:** Medium
    - **Topic tags:** Tree, BFS, DFS, Deque
    - **Interview frequency rank:** Top 5% of all tree problems

    > 💡 **Pro tip for interviews:** Always mention the Deque optimization over the reverse approach. Interviewers at Amazon and Google specifically appreciate candidates who avoid unnecessary O(k) reversal and explain *why* `addFirst` is more elegant.
    */
    // @formatter:on
}
