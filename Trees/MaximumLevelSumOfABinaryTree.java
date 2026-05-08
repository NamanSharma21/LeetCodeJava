package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import Datastructures.TreeNode;

public class MaximumLevelSumOfABinaryTree {
    public static void main(String[] args) {
        MaximumLevelSumOfABinaryTree maximumLevelSumOfABinaryTree = new MaximumLevelSumOfABinaryTree();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(7);
        root.right = new TreeNode(0);
        root.left.left = new TreeNode(7);
        root.left.right = new TreeNode(-8);
        System.out.println("MaximumLevelSumOfABinaryTree : " +
                maximumLevelSumOfABinaryTree.maxLevelSumBFSQueue(root));
        System.out.println("MaximumLevelSumOfABinaryTree : " +
                maximumLevelSumOfABinaryTree.maxLevelSumDFS(root));

        TreeNode root1 = new TreeNode(10);
        root1.left = new TreeNode(3);
        root1.right = new TreeNode(7);

        System.out.println("MaximumLevelSumOfABinaryTree : " + maximumLevelSumOfABinaryTree.maxLevelSumBFSQueue(root1));
        System.out.println("MaximumLevelSumOfABinaryTree : " + maximumLevelSumOfABinaryTree.maxLevelSumDFS(root1));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/maximum-level-sum-of-a-binary-tree/description/?envType=problem-list-v2&envId=tree
     * 
     * Given the root of a binary tree, the level of its root is 1, the level of its
     * children is 2, and so on.
     * 
     * Return the smallest level x such that the sum of all the values of nodes at
     * level x is maximal.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: root = [1,7,0,7,-8,null,null]
     * Output: 2
     * Explanation:
     * Level 1 sum = 1.
     * Level 2 sum = 7 + 0 = 7.
     * Level 3 sum = 7 + -8 = -1.
     * So we return the level with the maximum sum which is level 2.
     * 
     * 
     *       1          (Level 1: Sum = 1)
     *      / \
     *     7   0        (Level 2: Sum = 7 + 0 = 7)
     *    / \
     *   7  -8          (Level 3: Sum = 7 + -8 = -1)
     *   Max Level: 2
     * 
     * 
     * 
     * Example 2:
     * 
     *       989        (Level 1: Sum = 989)
     *         \
     *         10250    (Level 2: Sum = 10250)
     *         /    \
     *     98693  -89388 (Level 3: Sum = 98693 - 89388 = 9305)
     *               \
     *              -32127 (Level 4: Sum = -32127)
     *   Max Level: 2
     * 
     * 
     * Input: root = [989,null,10250,98693,-89388,null,null,null,-32127]
     * Output: 2
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [1, 104].
     * -105 <= Node.val <= 105
     */
    // @formatter:on

    public int maxLevelSumBFSQueue(TreeNode root) {
        if (root == null)
            return 0;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 1;
        int maxSum = Integer.MIN_VALUE;
        int maxSumLevel = 1;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            int levelSum = 0;
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                levelSum += current.val;
                if (current.left != null)
                    queue.offer(current.left);
                if (current.right != null)
                    queue.offer(current.right);
            }
            if (levelSum > maxSum) {
                maxSum = levelSum;
                maxSumLevel = level;
            }
            level++;
        }
        return maxSumLevel;
    }

    List<Integer> levelSum = new ArrayList<>();

    public int maxLevelSumDFS(TreeNode root) {

        int maxSum = Integer.MIN_VALUE;
        int bestLevel = 1;
        dfs(root, 0);
        for (int i = 0; i < levelSum.size(); i++) {
            if (levelSum.get(i) > maxSum) {
                maxSum = levelSum.get(i);
                bestLevel = i;
            }
        }
        levelSum.clear();
        return bestLevel + 1;
    }

    public void dfs(TreeNode root, int depth) {
        if (root == null)
            return;
        if (depth == levelSum.size()) {
            levelSum.add(0);
        }
        levelSum.set(depth, levelSum.get(depth) + root.val);
        dfs(root.left, depth + 1);
        dfs(root.right, depth + 1);
    }

    // @formatter:off
    /*
    # Maximum Level Sum of a Binary Tree — Deep Dive

    ---

    ## 1. Problem Statement

    ### In Plain English
    Given the root of a binary tree where each node holds an integer value, find the **level (depth)** of the tree that has the **maximum sum** of all node values at that level. The root is at level 1. If multiple levels share the same maximum sum, return the **smallest level number**.

    ### Input Format
    - A binary tree root node: `TreeNode root`
    - Each `TreeNode` has: `int val`, `TreeNode left`, `TreeNode right`
    - Number of nodes: `1 ≤ n ≤ 10^4`
    - Node values: `-10^5 ≤ Node.val ≤ 10^5`

    ### Output Format
    - A single `int`: the **1-indexed level** with the maximum sum

    ### What Exactly to Return
    - The level number (starting from 1) whose nodes sum to the highest value
    - In case of a tie, return the **smaller** level number

    ---

    ## 2. Intuition

    ### Core Idea
    Think of a binary tree as a series of **horizontal layers**. Each layer is a "level." You want to weigh each layer, find the heaviest one, and return its floor number.

    ### Human Reasoning Step-by-Step
    1. Start at the top (root = level 1)
    2. Visit every node on that floor and add up their values
    3. Move to the next floor down
    4. Repeat until you've visited every floor
    5. Return the floor number with the biggest total weight

    ### What Makes This Interesting
    - Node values **can be negative**, so a deeper level with many nodes isn't automatically the winner — a level with fewer but highly positive nodes could beat a wide level with mostly negative nodes
    - The **tie-breaking rule** (smallest level wins) is easy to miss under pressure
    - This is a textbook **BFS (Breadth-First Search)** problem — the level-by-level structure of BFS maps perfectly onto this problem

    ---

    ## 3. Approach Overview

    | # | Approach | Key Idea | Use When | Optimal? |
    |---|----------|----------|----------|----------|
    | 1 | BFS with a Queue | Process level-by-level using a queue | Always — clean O(n) | ✅ Yes |
    | 2 | DFS with Level Tracking | Recursively track depth, store sums in a list | When you prefer recursion | ✅ Equal complexity |
    | 3 | Brute Force (naive DFS) | For each level, run a full DFS to sum it | Only for tiny trees | ❌ O(n·h) |

    ### Why BFS is the Natural Optimal
    BFS **naturally groups nodes by level** using a queue. At each iteration, you process exactly one level's worth of nodes — no extra bookkeeping needed. DFS works equally well in terms of Big-O but requires an extra data structure (array of sums).

    ---

    ## 4. Detailed Solutions in Java

    ### TreeNode Definition (Given by LeetCode)
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

    ### ✅ Approach 1 — BFS with a Queue (Optimal)

    #### Algorithm Step-by-Step
    1. Initialize a queue with the root node
    2. Set `currentLevel = 0`, `maxSum = Integer.MIN_VALUE`, `bestLevel = 1`
    3. While the queue is not empty:
    - Record how many nodes are in the queue right now (= nodes on this level)
    - Increment `currentLevel`
    - Sum up all node values at this level by polling exactly that many nodes
    - For each polled node, add its children to the queue for the next iteration
    - If `levelSum > maxSum`, update `maxSum` and `bestLevel`
    4. Return `bestLevel`

    ```java
    import java.util.LinkedList;
    import java.util.Queue;

    class Solution {
        public int maxLevelSum(TreeNode root) {
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            int maxSum = Integer.MIN_VALUE;
            int bestLevel = 1;
            int currentLevel = 0;

            while (!queue.isEmpty()) {
                int nodesOnThisLevel = queue.size(); // snapshot of current level size
                currentLevel++;
                long levelSum = 0;

                for (int i = 0; i < nodesOnThisLevel; i++) {
                    TreeNode node = queue.poll();
                    levelSum += node.val;

                    if (node.left != null)  queue.offer(node.left);
                    if (node.right != null) queue.offer(node.right);
                }

                // Only update if strictly greater — handles tie-breaking automatically
                if (levelSum > maxSum) {
                    maxSum = (int) levelSum;
                    bestLevel = currentLevel;
                }
            }

            return bestLevel;
        }
    }
    ```

    ---

    ### ✅ Approach 2 — DFS with Level Tracking

    #### Algorithm Step-by-Step
    1. Create a `List<Long>` called `levelSums` to store the sum at each depth index
    2. Recursively visit each node; pass the current depth as a parameter
    3. If `depth == levelSums.size()`, this is a new level — add a new entry
    4. Add `node.val` to `levelSums.get(depth)`
    5. After DFS completes, scan the list to find the index with the maximum value
    6. Return `bestIndex + 1` (convert 0-indexed to 1-indexed level)

    ```java
    import java.util.ArrayList;
    import java.util.List;

    class Solution {
        private List<Long> levelSums = new ArrayList<>();

        public int maxLevelSum(TreeNode root) {
            dfs(root, 0);

            int bestLevel = 0;
            long maxSum = Long.MIN_VALUE;

            for (int i = 0; i < levelSums.size(); i++) {
                if (levelSums.get(i) > maxSum) {
                    maxSum = levelSums.get(i);
                    bestLevel = i; // 0-indexed
                }
            }

            return bestLevel + 1; // convert to 1-indexed
        }

        private void dfs(TreeNode node, int depth) {
            if (node == null) return;

            // First time we reach this depth — create a new slot
            if (depth == levelSums.size()) {
                levelSums.add(0L);
            }

            levelSums.set(depth, levelSums.get(depth) + node.val);

            dfs(node.left,  depth + 1);
            dfs(node.right, depth + 1);
        }
    }
    ```

    ---

    ### ❌ Approach 3 — Brute Force (For Understanding Only)

    #### Algorithm Step-by-Step
    1. Find the height `h` of the tree
    2. For each level from `1` to `h`, run a full DFS and sum only nodes at that depth
    3. Track the maximum sum and corresponding level
    4. Return the level with the max sum

    ```java
    class Solution {
        public int maxLevelSum(TreeNode root) {
            int height = getHeight(root);
            int maxSum = Integer.MIN_VALUE;
            int bestLevel = 1;

            for (int level = 1; level <= height; level++) {
                int sum = sumAtLevel(root, level, 1);
                if (sum > maxSum) {
                    maxSum = sum;
                    bestLevel = level;
                }
            }
            return bestLevel;
        }

        private int sumAtLevel(TreeNode node, int targetLevel, int currentLevel) {
            if (node == null) return 0;
            if (currentLevel == targetLevel) return node.val;
            return sumAtLevel(node.left,  targetLevel, currentLevel + 1)
                + sumAtLevel(node.right, targetLevel, currentLevel + 1);
        }

        private int getHeight(TreeNode node) {
            if (node == null) return 0;
            return 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }
    }
    ```
    > ⚠️ **Not recommended** — O(n·h) time, which degrades to O(n²) for skewed trees. Only illustrative.

    ---

    ## 5. Time & Space Complexity

    ### Approach 1 — BFS

    | Metric | Value | Reasoning |
    |--------|-------|-----------|
    | Time | O(n) | Each node is enqueued and dequeued exactly once |
    | Space | O(w) | Queue holds at most one full level; max width `w` ≤ `n/2` for a complete tree, so worst case O(n) |

    **Example walkthrough:**
    - Tree with 15 nodes (perfect binary tree, depth 4): ~15 enqueue + 15 dequeue operations = ~30 operations → O(n)
    - Queue at widest level (level 4) holds 8 nodes → O(n/2) space

    ---

    ### Approach 2 — DFS

    | Metric | Value | Reasoning |
    |--------|-------|-----------|
    | Time | O(n) | Each node visited exactly once during recursion |
    | Space | O(h) call stack + O(h) list | Recursion depth = height `h`; list has one entry per level = h entries. For balanced tree h = log n; for skewed tree h = n |

    **Example walkthrough:**
    - Balanced tree with 1000 nodes: recursion stack depth ≈ 10, list size ≈ 10 → very space efficient
    - Skewed (linked-list) tree with 1000 nodes: recursion stack depth ≈ 1000 → O(n) stack → risk of StackOverflow for n = 10^4

    ---

    ### Approach 3 — Brute Force

    | Metric | Value | Reasoning |
    |--------|-------|-----------|
    | Time | O(n·h) | For each of `h` levels, we traverse all `n` nodes |
    | Space | O(h) | Only recursion call stack |

    ---

    ### Summary Table

    | Approach | Time | Space | Notes |
    |----------|------|-------|-------|
    | BFS | O(n) | O(n) worst | Safest & most readable |
    | DFS | O(n) | O(h) | Risky for skewed trees (stack overflow) |
    | Brute Force | O(n·h) | O(h) | Only for understanding |

    ---

    ## 6. Complete Worked Examples

    ### Example 1 — BFS Approach

    **Input Tree:**
    ```
            1
        / \
        7   0
        / \   \
        7  -8   9
    ```
    **Levels:**
    - Level 1: {1} → sum = 1
    - Level 2: {7, 0} → sum = 7
    - Level 3: {7, -8, 9} → sum = 8

    **BFS Step-by-Step:**

    | Step | currentLevel | Queue (before poll) | levelSum | maxSum | bestLevel |
    |------|-------------|---------------------|----------|--------|-----------|
    | Init | 0 | [1] | — | MIN | 1 |
    | Level 1 | 1 | [1] → poll 1, add 7,0 | 1 | 1 | 1 |
    | Level 2 | 2 | [7, 0] → poll both, add 7,-8,9 | 7 | 7 | 2 |
    | Level 3 | 3 | [7, -8, 9] → poll all, no children | 8 | 8 | 3 |

    **Output: 3** ✅

    ---

    ### Example 2 — DFS Approach (Same Tree)

    **DFS Traversal order:** 1 → 7 → 7 → -8 → 0 → 9

    | Visit | Node | Depth | levelSums after update |
    |-------|------|-------|------------------------|
    | 1 | 1 | 0 | [1] |
    | 2 | 7 | 1 | [1, 7] |
    | 3 | 7 | 2 | [1, 7, 7] |
    | 4 | -8 | 2 | [1, 7, -1] |
    | 5 | 0 | 1 | [1, 7, -1] → [1, 7, -1] wait — 0+7=7 → [1, 7, -1] |
    | 6 | 9 | 2 | [1, 7, 8] |

    **Final levelSums:** [1, 7, 8]
    **Max is 8 at index 2 → return index + 1 = 3**

    **Output: 3** ✅

    ---

    ### Example 3 — Tie-Breaking Test

    **Input Tree:**
    ```
        10
        /  \
        3    7
    ```
    - Level 1: {10} → sum = 10
    - Level 2: {3, 7} → sum = 10

    **BFS:** Both sums equal 10. Since we use `>` (strictly greater), level 1 wins and `bestLevel` is never updated beyond 1.

    **Output: 1** ✅ (smallest level on tie)

    ---

    ## 7. Edge Cases

    ### Case 1 — Single Node
    ```java
    Input: root = [5]
    ```
    - Only one level exists
    - BFS processes level 1, sum = 5
    - Returns 1 ✅

    ---

    ### Case 2 — All Negative Values
    ```java
    Input: root = [-1, -2, -3]
    // Level 1: sum = -1
    // Level 2: sum = -5
    ```
    - BFS still works correctly — `Integer.MIN_VALUE` initialization ensures any real sum wins
    - Returns level 1 ✅
    - ⚠️ **Risk:** If you initialize `maxSum = 0`, you'll miss all-negative trees. Always use `Integer.MIN_VALUE`

    ---

    ### Case 3 — Perfectly Skewed Tree (Left-Only Chain)
    ```
    1 → 2 → 3 → ... → 10000
    ```
    - BFS: Queue never holds more than 1 node at a time → O(1) space per level → safe ✅
    - DFS: Recursion depth = 10000 → **StackOverflowError risk** ⚠️

    ---

    ### Case 4 — Overflow Risk
    - Max nodes: 10^4, max value per node: 10^5
    - Maximum possible level sum: 10^4 × 10^5 = 10^9 which **exceeds `Integer.MAX_VALUE` (≈ 2.1 × 10^9)**... actually fits, but it's very close
    - Safer to use `long` for `levelSum` accumulation — both solutions above already do this ✅

    ---

    ### Case 5 — All Nodes at Same Level Have Mixed Positives and Negatives
    ```
        1
        /     \
    5      -5
    / \    /  \
    3  -3  2   -2
    ```
    - Level 3: 3 + (-3) + 2 + (-2) = 0
    - Level 2: 5 + (-5) = 0
    - Level 1: 1
    - Returns level 1 ✅

    ---

    ### Edge Case Stability Matrix

    | Edge Case | BFS | DFS | Brute Force |
    |-----------|-----|-----|-------------|
    | Single node | ✅ | ✅ | ✅ |
    | All negatives | ✅ (use MIN_VALUE init) | ✅ | ✅ |
    | Skewed tree (n=10^4) | ✅ | ⚠️ Stack risk | ✅ |
    | Sum overflow | ✅ (use long) | ✅ (use long) | ⚠️ Risk if int |
    | Tie on sum | ✅ (strict >) | ✅ (strict >) | ✅ |
    | Null root | ✅ (add null check) | ✅ | ✅ |

    ---

    ## 8. Final Summary

    ### Comparison Table

    | Approach | Time | Space | Readability | Interview Fit |
    |----------|------|-------|-------------|---------------|
    | BFS | O(n) | O(n) | ⭐⭐⭐⭐⭐ | ✅ Best |
    | DFS | O(n) | O(h) | ⭐⭐⭐⭐ | ✅ Good |
    | Brute Force | O(n·h) | O(h) | ⭐⭐⭐ | ❌ Too slow |

    ### Recommendation
    **Use BFS (Approach 1) in interviews and production.** It maps naturally to the level-by-level structure of the problem, avoids recursion depth risks, and is immediately readable by any engineer.

    ### What to Remember
    > **"Level-by-level problems → BFS with queue snapshot."** Any time a problem asks about a property at each depth/level of a tree, your first instinct should be BFS with `int levelSize = queue.size()` at the start of each iteration.

    ---

    ## 9. Company Interview Appearances

    | Company | Frequency | Notes |
    |---------|-----------|-------|
    | **Amazon** | ⭐⭐⭐⭐⭐ Very High | Frequently appears in OA and phone screens |
    | **Microsoft** | ⭐⭐⭐⭐ High | Common in tree/BFS rounds |
    | **Google** | ⭐⭐⭐ Medium | Appears in L4/L5 interviews |
    | **Facebook/Meta** | ⭐⭐⭐ Medium | BFS variant questions common |
    | **Bloomberg** | ⭐⭐⭐ Medium | Trees are a core topic |
    | **Adobe** | ⭐⭐ Moderate | Seen in SDE-2 rounds |
    | **Uber** | ⭐⭐ Moderate | Graph/tree traversal emphasis |

    **LeetCode Problem #1161** — Difficulty: **Medium**
    - Acceptance rate: ~67%
    - Appeared in **Amazon OA** multiple times (2021–2024)
    - Tagged under: **BFS, Binary Tree, Breadth-First Search**
    - Total reported interview appearances: **50+ times** across platforms (LeetCode discuss, Glassdoor, interviewing.io)
    */
    // @formatter:on
}
