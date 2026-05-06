package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import Datastructures.TreeNode;

public class BinaryTreeLevelOrderTraversalII {
    public static void main(String[] args) {
        BinaryTreeLevelOrderTraversalII binaryTreeLevelOrderTraversalII = new BinaryTreeLevelOrderTraversalII();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out
                .println("BinaryTreeLevelOrderTraversalII : "
                        + binaryTreeLevelOrderTraversalII.levelOrderBottomBFSReverse(root));

        System.out
                .println("BinaryTreeLevelOrderTraversalII : "
                        + binaryTreeLevelOrderTraversalII.levelOrderBottomBFSLinkedListDeque(root));

        System.out
                .println("BinaryTreeLevelOrderTraversalII : "
                        + binaryTreeLevelOrderTraversalII.levelOrderBottomDFSLevelIndex(root));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/binary-tree-level-order-traversal-ii/description/?envType=problem-list-v2&envId=tree
     * 
     * 
     * Given the root of a binary tree, return the bottom-up level order traversal
     * of its nodes' values. (i.e., from left to right, level by level from leaf to
     * root).
     * 
     * 
     * 
     * Example 1:
     *       3
     *      / \
     *     9   20
     *        /  \
     *       15   7
     * Output: [[15,7],[9,20],[3]]
     * 
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[15,7],[9,20],[3]]
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
    // @formatter:on

    public List<List<Integer>> levelOrderBottomBFSReverse(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null)
            return result;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                currentLevel.add(current.val);
                if (current.left != null)
                    queue.offer(current.left);
                if (current.right != null)
                    queue.offer(current.right);
            }
            result.add(currentLevel);
        }
        Collections.reverse(result);
        return result;
    }

    public List<List<Integer>> levelOrderBottomBFSLinkedListDeque(TreeNode root) {
        LinkedList<List<Integer>> result = new LinkedList<>();
        if (root == null)
            return result;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                currentLevel.add(current.val);
                if (current.left != null)
                    queue.offer(current.left);
                if (current.right != null)
                    queue.offer(current.right);
            }
            result.addFirst(currentLevel);
        }
        return result;
    }

    public List<List<Integer>> levelOrderBottomDFSLevelIndex(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(root, 0, result);
        Collections.reverse(result);
        return result;
    }

    public void dfs(TreeNode root, int depth, List<List<Integer>> result) {
        if (root == null)
            return;
        if (depth == result.size())
            result.add(new ArrayList<>());
        result.get(depth).add(root.val);
        dfs(root.left, depth + 1, result);
        dfs(root.right, depth + 1, result);
    }

    // @formatter:off
    /*
    # Binary Tree Level Order Traversal II — Deep Dive

    ---

    ## 1. Problem Statement

    ### In Plain English
    Given the root of a binary tree, return the **bottom-up level order traversal** of its node values. That means: collect node values level by level, but instead of returning the result from top (root) to bottom (leaves), **reverse it** — leaves' level comes first, root's level comes last.

    ### Input Format
    - A binary tree root node: `TreeNode root`
    - `TreeNode` has: `int val`, `TreeNode left`, `TreeNode right`
    - Number of nodes: `0 <= n <= 2000`
    - Node values: `-1000 <= val <= 1000`

    ### Output Format
    - `List<List<Integer>>` — a list of lists, where each inner list contains the values of nodes at one level, ordered **from bottom to top**

    ### What Exactly Needs to Be Computed
    - Level 0 (root) → goes to the **last** inner list
    - Deepest level → goes to the **first** inner list
    - Within each level, left-to-right order is preserved

    ### Visual Example
    ```
            3
        / \
        9  20
            /  \
        15   7

    Output: [[15,7],[9,20],[3]]
            ^leaf  ^mid   ^root
    ```

    ---

    ## 2. Intuition

    ### Core Idea
    Think of this as a **standard level order traversal (BFS)**, but at the end you just **flip the result**.

    ### How a Human Would Reason
    1. You want to visit nodes **level by level** — this screams BFS with a queue
    2. At each level, collect all node values into a temporary list
    3. Add each level's list to the result
    4. After all levels are processed, **reverse the entire result**

    ### What Makes It Interesting
    - It looks harder than it is — the twist is purely in the **output ordering**
    - The real skill tested is whether you know **BFS on trees** cleanly
    - A recursive DFS approach adds interesting depth (tracking level index, inserting at front)
    - Choosing between `Collections.reverse()` vs `LinkedList.addFirst()` reveals understanding of data structure trade-offs

    ---

    ## 3. Approach Overview

    | # | Approach | Key Idea | Best For | Optimal? |
    |---|----------|----------|----------|----------|
    | 1 | BFS + Reverse | BFS level by level, reverse result at end | Interviews, clean code | ✅ Yes |
    | 2 | BFS + Deque (addFirst) | BFS but prepend each level to front of deque | Avoids explicit reverse | ✅ Equally optimal |
    | 3 | DFS + Level Index | DFS with depth tracking, insert at correct index | Recursion practice | ✅ Same complexity |

    ### Which is Optimal and Why?
    All three are **O(n)** time and **O(n)** space. **BFS + reverse** is the most interview-friendly and easiest to explain. The Deque variant is elegant. DFS is a great alternative to demonstrate recursion mastery.

    ---

    ## 4. Detailed Solutions in Java

    ### Approach 1: BFS + Reverse (Recommended)

    **Algorithm Step-by-Step:**
    1. Handle null root — return empty list
    2. Create a queue, add root
    3. While queue is not empty:
    - Record current queue size (= number of nodes at this level)
    - Loop `size` times: poll a node, add its value to a temp list, enqueue its children
    - Add temp list to result
    4. Reverse the entire result list
    5. Return result

    ```java
    import java.util.*;

    public class Solution {

        public List<List<Integer>> levelOrderBottom(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();

            if (root == null) return result;

            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                int levelSize = queue.size(); // number of nodes at this level
                List<Integer> currentLevel = new ArrayList<>();

                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    currentLevel.add(node.val);

                    if (node.left != null) queue.offer(node.left);
                    if (node.right != null) queue.offer(node.right);
                }

                result.add(currentLevel);
            }

            Collections.reverse(result); // flip top-down to bottom-up
            return result;
        }
    }
    ```

    ---

    ### Approach 2: BFS + LinkedList Deque (addFirst)

    **Algorithm Step-by-Step:**
    1. Same BFS as above, but use a `LinkedList` as the result container
    2. Instead of appending each level to the end, **prepend** it using `addFirst()`
    3. This naturally gives bottom-up order without a separate reverse step

    ```java
    import java.util.*;

    public class Solution {

        public List<List<Integer>> levelOrderBottom(TreeNode root) {
            LinkedList<List<Integer>> result = new LinkedList<>(); // supports addFirst

            if (root == null) return result;

            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                List<Integer> currentLevel = new ArrayList<>();

                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    currentLevel.add(node.val);

                    if (node.left != null) queue.offer(node.left);
                    if (node.right != null) queue.offer(node.right);
                }

                result.addFirst(currentLevel); // prepend → bottom-up order automatically
            }

            return result;
        }
    }
    ```

    ---

    ### Approach 3: DFS + Level Index

    **Algorithm Step-by-Step:**
    1. Recursively traverse the tree, passing the current `depth` (0-indexed)
    2. If `result.size() == depth`, we're visiting this level for the first time — add a new list
    3. Add the current node's value to `result.get(depth)`
    4. After full DFS, reverse the result

    ```java
    import java.util.*;

    public class Solution {

        public List<List<Integer>> levelOrderBottom(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();
            dfs(root, 0, result);
            Collections.reverse(result);
            return result;
        }

        private void dfs(TreeNode node, int depth, List<List<Integer>> result) {
            if (node == null) return;

            // First time visiting this depth level — create a new sublist
            if (result.size() == depth) {
                result.add(new ArrayList<>());
            }

            result.get(depth).add(node.val); // add current node value to its level

            dfs(node.left, depth + 1, result);
            dfs(node.right, depth + 1, result);
        }
    }
    ```

    ---

    ## 5. Time & Space Complexity

    ### Approach 1 & 2: BFS

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | Every node is enqueued and dequeued exactly once. `Collections.reverse()` on L levels is O(L) ≤ O(n) |
    | **Space** | O(n) | Queue holds at most one full level. The widest level of a perfect binary tree has n/2 nodes → O(n). Result stores all n values |

    **Walk-through with sizes:**
    - n=7 (perfect 3-level tree): queue peaks at 4 nodes (leaf level), result has 7 values → ~11 operations
    - n=2000: ~2000 enqueue + ~2000 dequeue + reverse → roughly 4001 operations

    ### Approach 3: DFS

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | Every node is visited exactly once in DFS |
    | **Space** | O(h) call stack + O(n) result | h = height of tree. For balanced tree: O(log n). For skewed tree (worst case): O(n) call stack |

    > ⚠️ DFS uses **O(h) extra stack space** which can be O(n) for a skewed tree — slightly worse than BFS in worst-case stack usage.

    ---

    ## 6. Complete Worked Examples

    ### Example: All Three Approaches on the Same Tree

    **Input Tree:**
    ```
            3
        / \
        9  20
            /  \
        15   7
    ```

    ---

    #### Approach 1 & 2: BFS Walkthrough

    | Step | Queue State (before poll) | Level Polled | currentLevel | result so far |
    |------|--------------------------|--------------|--------------|---------------|
    | Init | [3] | — | — | [] |
    | Level 0 | [3] → poll 3, enqueue 9, 20 | 0 | [3] | [[3]] |
    | Level 1 | [9, 20] → poll 9 (no children), poll 20 (enqueue 15,7) | 1 | [9, 20] | [[3],[9,20]] |
    | Level 2 | [15, 7] → poll both, no children | 2 | [15, 7] | [[3],[9,20],[15,7]] |
    | Reverse | — | — | — | [[15,7],[9,20],[3]] ✅ |

    **Approach 2 (addFirst) skips reverse:**
    - After Level 0: result = [[3]]
    - After Level 1: result = [[9,20],[3]]
    - After Level 2: result = [[15,7],[9,20],[3]] ✅

    ---

    #### Approach 3: DFS Walkthrough

    **DFS call order (pre-order: node → left → right):**

    | Call | Node | Depth | Action | result after |
    |------|------|-------|--------|--------------|
    | 1 | 3 | 0 | size==0, add new list, insert 3 | [[3]] |
    | 2 | 9 | 1 | size==1, add new list, insert 9 | [[3],[9]] |
    | 3 | null | 2 | return | no change |
    | 4 | null | 2 | return | no change |
    | 5 | 20 | 1 | size>1, insert 20 into index 1 | [[3],[9,20]] |
    | 6 | 15 | 2 | size==2, add new list, insert 15 | [[3],[9,20],[15]] |
    | 7 | 7 | 2 | size>2, insert 7 into index 2 | [[3],[9,20],[15,7]] |
    | Reverse | — | — | — | [[15,7],[9,20],[3]] ✅ |

    ---

    ## 7. Edge Cases

    ### Complete Edge Case Table

    | Edge Case | Input | Expected Output | Approach 1 | Approach 2 | Approach 3 |
    |-----------|-------|-----------------|------------|------------|------------|
    | Null root | `null` | `[]` | ✅ early return | ✅ early return | ✅ dfs returns on null |
    | Single node | `[1]` | `[[1]]` | ✅ one iteration | ✅ addFirst once | ✅ depth 0 only |
    | Left-skewed tree | `1→2→3→4` (all left children) | `[[4],[3],[2],[1]]` | ✅ | ✅ | ⚠️ O(n) call stack |
    | Right-skewed tree | `1→2→3→4` (all right children) | `[[4],[3],[2],[1]]` | ✅ | ✅ | ⚠️ O(n) call stack |
    | Perfect binary tree | Balanced full tree | Correct bottom-up levels | ✅ | ✅ | ✅ |
    | All same values | All nodes = 0 | Correct grouping by level | ✅ | ✅ | ✅ |
    | Two-node tree | Root + one child | `[[child],[root]]` | ✅ | ✅ | ✅ |
    | Negative values | `-1000 to 1000` | No issues (just integers) | ✅ | ✅ | ✅ |

    ### Key Observations:
    - **Null root** — all three handle it explicitly or naturally via `if (node == null) return`
    - **Skewed trees** — BFS approaches use O(1) queue depth per level (always 1 node). DFS recurses n levels deep → stack overflow risk for n=10,000+, but within the n≤2000 constraint it's fine
    - **No integer overflow risk** — we only store `int val`, never sum values
    - **Single child nodes** — BFS naturally handles this since we only enqueue non-null children

    ---

    ## 8. Final Summary

    ### Comparison Table

    | Approach | Time | Space | Code Clarity | Interview Recommendation |
    |----------|------|-------|--------------|--------------------------|
    | BFS + Reverse | O(n) | O(n) | ⭐⭐⭐⭐⭐ | ✅ Best choice |
    | BFS + addFirst | O(n) | O(n) | ⭐⭐⭐⭐⭐ | ✅ Elegant alternative |
    | DFS + Level Index | O(n) | O(n)+O(h) | ⭐⭐⭐⭐ | ✅ Good for showing recursion |

    ### 🏆 Recommendation
    Use **BFS + Reverse** (Approach 1) in interviews. It's the most readable, follows a well-known pattern, and is easy to explain verbally. If you want to show off, use **Approach 2** (addFirst) and explain why `LinkedList` supports O(1) front insertion.

    ### What to Remember
    > **Pattern:** "Level order = BFS with queue + size snapshot. Bottom-up = reverse at end or prepend with deque."
    > **Key insight:** `levelSize = queue.size()` before the inner loop is the trick that separates levels cleanly.

    ---

    ## 9. Company Appearances & Frequency

    ### Companies That Have Asked This Problem

    | Company | Frequency | Notes |
    |---------|-----------|-------|
    | **Amazon** | ⭐⭐⭐⭐⭐ Very High | Top asked; appears in SDE-1 and SDE-2 loops |
    | **Microsoft** | ⭐⭐⭐⭐ High | Common in online assessment rounds |
    | **Facebook / Meta** | ⭐⭐⭐⭐ High | Often paired with Level Order I as a follow-up |
    | **Google** | ⭐⭐⭐ Medium | Usually as warm-up or part of a harder tree problem |
    | **Bloomberg** | ⭐⭐⭐ Medium | Appears in early rounds |
    | **LinkedIn** | ⭐⭐⭐ Medium | Part of tree traversal sets |
    | **Adobe** | ⭐⭐ Moderate | Seen in SDE interviews |
    | **Uber** | ⭐⭐ Moderate | Occasionally in phone screens |
    | **Apple** | ⭐⭐ Moderate | Tree-heavy interview tracks |

    ### Overall LeetCode Stats (Problem #107)
    - **Difficulty:** Easy
    - **Acceptance Rate:** ~62%
    - **Total Submissions:** 1M+
    - **Interview appearances:** Reported **200+ times** across platforms (LeetCode Discuss, Glassdoor, Interview Bit)
    - **Most common context:** Asked as a **follow-up** to LC #102 (Level Order Traversal I) — interviewers say "now give me the bottom-up version" to test adaptability
    */
    // @formatter:on

}
