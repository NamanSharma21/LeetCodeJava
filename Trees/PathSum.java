package Trees;

import java.util.ArrayDeque;
import java.util.Queue;

import Datastructures.TreeNode;

public class PathSum {
    public static void main(String[] args) {
        PathSum pathSum = new PathSum();
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.right.right = new TreeNode(1);
        System.out.println("PathSum : " + pathSum.hasPathSumReccursivePreOrderDFS(root, 22));
        System.out.println("PathSum : " + pathSum.hasPathSumIterativePreOrderDFS(root, 22));
    }

    /*
     * https://leetcode.com/problems/path-sum/description/?envType=problem-list-v2&
     * envId=tree
     * 
     * 
     * Given the root of a binary tree and an integer targetSum, return true if the
     * tree has a root-to-leaf path such that adding up all the values along the
     * path equals targetSum.
     * 
     * A leaf is a node with no children.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
     * Output: true
     * Explanation: The root-to-leaf path with the target sum is shown.
     * Example 2:
     * 
     * 
     * Input: root = [1,2,3], targetSum = 5
     * Output: false
     * Explanation: There are two root-to-leaf paths in the tree:
     * (1 --> 2): The sum is 3.
     * (1 --> 3): The sum is 4.
     * There is no root-to-leaf path with sum = 5.
     * Example 3:
     * 
     * Input: root = [], targetSum = 0
     * Output: false
     * Explanation: Since the tree is empty, there are no root-to-leaf paths.
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [0, 5000].
     * -1000 <= Node.val <= 1000
     * -1000 <= targetSum <= 1000
     */

    public boolean hasPathSumReccursivePreOrderDFS(TreeNode root, int targetSum) {
        if (root == null)
            return false;
        int remaining = targetSum - root.val;
        if (root.left == null && root.right == null)
            return remaining == 0;
        return hasPathSumReccursivePreOrderDFS(root.left, remaining)
                || hasPathSumReccursivePreOrderDFS(root.right, remaining);
    }

    public boolean hasPathSumIterativePreOrderDFS(TreeNode root, int targetSum) {
        if (root == null)
            return false;
        Queue<TreeNode> nodeQueue = new ArrayDeque<>();
        Queue<Integer> sumQueue = new ArrayDeque<>();
        nodeQueue.offer(root);
        sumQueue.offer(targetSum);
        while (!nodeQueue.isEmpty()) {
            TreeNode current = nodeQueue.poll();
            int remainder = sumQueue.poll() - current.val;
            if (current.left == null && current.right == null && remainder == 0) {
                return true;
            }
            if (current.left != null) {
                nodeQueue.offer(current.left);
                sumQueue.offer(remainder);
            }
            if (current.right != null) {
                nodeQueue.offer(current.right);
                sumQueue.offer(remainder);
            }
        }

        return false;
    }


    /*
    # Path Sum — Deep Dive

    ---

    ## 1. Problem Statement

    ### What is the problem asking?

    Given a **binary tree** and a **target integer** `targetSum`, determine whether the tree contains a **root-to-leaf path** such that the sum of all node values along that path equals `targetSum`.

    - A **root-to-leaf path** starts at the root and ends at a **leaf node** (a node with no left or right child).
    - You must return a **boolean**: `true` if such a path exists, `false` otherwise.

    ### Input Format
    ```
    TreeNode root   — root of a binary tree (may be null)
    int targetSum   — the target sum to check against
    ```

    ### Output Format
    ```
    boolean — true if any root-to-leaf path sums to targetSum, else false
    ```

    ### Constraints (LeetCode #112)
    - Number of nodes: `0 ≤ n ≤ 5000`
    - Node values: `-1000 ≤ Node.val ≤ 1000`
    - Target sum: `-1000 ≤ targetSum ≤ 1000`

    ### Key Clarifications
    - An **empty tree** (`root == null`) → always `false`
    - The path **must** end at a **leaf** — stopping at an internal node doesn't count
    - Node values can be **negative**, so you can't prune early just because the running sum exceeds targetSum

    ---

    ## 2. Intuition

    ### Think like a human first

    Imagine you're standing at the root of a tree holding a "remaining budget" of `targetSum`. At each node you visit, you **spend** that node's value from your budget. When you reach a leaf node, you check: **is my remaining budget exactly zero?**

    If yes → a valid path exists. If not → try another path.

    ```
    Tree:           targetSum = 22
        5
        / \
        4   8
    /   / \
    11  13   4
    /  \       \
    7    2        1

    Path: 5 → 4 → 11 → 2  =  22 ✓
    ```

    ### Why is this interesting?

    - The "leaf" requirement is the subtle trap — many beginners check at any node, not just leaves.
    - Negative values prevent greedy pruning.
    - It's a perfect problem to understand **recursion on trees** and **DFS traversal**.

    ---

    ## 3. Approach Overview

    | # | Approach | Key Idea | Time | Space | Use When |
    |---|----------|----------|------|-------|----------|
    | 1 | **Recursive DFS** | Subtract node value, recurse to children | O(n) | O(h) | Always — cleanest solution |
    | 2 | **Iterative DFS (Stack)** | Simulate recursion with explicit stack of (node, remainingSum) pairs | O(n) | O(h) | When recursion depth is a concern |
    | 3 | **Iterative BFS (Queue)** | Level-order traversal with paired queue of remainders | O(n) | O(n) | When tree is very deep (skewed) |

    > ✅ **Recommended: Recursive DFS** — cleanest, most readable, interview-preferred, and O(n) optimal since every node must potentially be visited.

    ---

    ## 4. Detailed Solutions in Java

    ### Setup: TreeNode Definition
    ```java
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    ```

    ---

    ### ✅ Approach 1 — Recursive DFS (Optimal)

    #### Algorithm, Step by Step

    1. **Base case — null node:** Return `false` (no path possible).
    2. **Subtract** the current node's value from `targetSum` → `remaining = targetSum - node.val`.
    3. **Leaf check:** If node has no children AND `remaining == 0`, return `true`.
    4. **Recurse** into left and right subtrees with the updated `remaining`.
    5. Return `true` if **either** subtree returns `true`.

    ```java
    class Solution {
        public boolean hasPathSum(TreeNode root, int targetSum) {
            // Base case: empty tree or fallen off the tree
            if (root == null) return false;

            int remaining = targetSum - root.val;

            // At a leaf node: check if we've exactly consumed the target
            if (root.left == null && root.right == null) {
                return remaining == 0;
            }

            // Recurse into whichever children exist
            return hasPathSum(root.left, remaining)
                || hasPathSum(root.right, remaining);
        }
    }
    ```

    ---

    ### Approach 2 — Iterative DFS with Stack

    #### Algorithm, Step by Step

    1. Use a **stack** of `int[]` pairs: `{node reference encoded, remaining sum}`.  
    *(In Java we use two parallel stacks or a Deque of a custom pair — we'll use two `Deque`s for clarity.)*
    2. Push `(root, targetSum)` onto the stack.
    3. While the stack is not empty:
    - Pop `(node, remaining)`.
    - Compute `remaining -= node.val`.
    - If it's a leaf and `remaining == 0` → return `true`.
    - Otherwise push left child and/or right child with the updated `remaining`.
    4. If the stack empties → return `false`.

    ```java
    import java.util.Deque;
    import java.util.ArrayDeque;

    class Solution {
        public boolean hasPathSum(TreeNode root, int targetSum) {
            if (root == null) return false;

            // Two stacks: one for nodes, one for corresponding remaining sums
            Deque<TreeNode> nodeStack = new ArrayDeque<>();
            Deque<Integer> sumStack  = new ArrayDeque<>();

            nodeStack.push(root);
            sumStack.push(targetSum);

            while (!nodeStack.isEmpty()) {
                TreeNode current   = nodeStack.pop();
                int remaining      = sumStack.pop() - current.val;

                // Leaf node: check if path sum matches
                if (current.left == null && current.right == null && remaining == 0) {
                    return true;
                }

                // Push children with updated remaining sum
                if (current.left != null) {
                    nodeStack.push(current.left);
                    sumStack.push(remaining);
                }
                if (current.right != null) {
                    nodeStack.push(current.right);
                    sumStack.push(remaining);
                }
            }

            return false;
        }
    }
    ```

    ---

    ### Approach 3 — Iterative BFS with Queue

    #### Algorithm, Step by Step

    1. Use a **queue** (level-order) with two parallel queues: nodes and their running remainders.
    2. Enqueue `(root, targetSum)`.
    3. While queue is non-empty:
    - Dequeue `(node, remaining)`, subtract `node.val`.
    - Leaf + `remaining == 0` → return `true`.
    - Enqueue left/right children with updated `remaining`.
    4. Return `false` if exhausted.

    ```java
    import java.util.Queue;
    import java.util.LinkedList;

    class Solution {
        public boolean hasPathSum(TreeNode root, int targetSum) {
            if (root == null) return false;

            Queue<TreeNode> nodeQueue = new LinkedList<>();
            Queue<Integer>  sumQueue  = new LinkedList<>();

            nodeQueue.offer(root);
            sumQueue.offer(targetSum);

            while (!nodeQueue.isEmpty()) {
                TreeNode current = nodeQueue.poll();
                int remaining    = sumQueue.poll() - current.val;

                if (current.left == null && current.right == null && remaining == 0) {
                    return true;
                }

                if (current.left != null) {
                    nodeQueue.offer(current.left);
                    sumQueue.offer(remaining);
                }
                if (current.right != null) {
                    nodeQueue.offer(current.right);
                    sumQueue.offer(remaining);
                }
            }

            return false;
        }
    }
    ```

    ---

    ## 5. Time & Space Complexity

    ### Approach 1 — Recursive DFS

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | Every node is visited exactly once in the worst case (no valid path found) |
    | **Space** | O(h) | Recursion call stack depth = height of tree. Best case O(log n) for balanced, worst case O(n) for skewed |

    **Example walkthrough (size-based):**
    - 5-node balanced tree: ~5 recursive calls, stack depth ~3
    - 5-node skewed tree (linked list shape): ~5 recursive calls, stack depth ~5

    ---

    ### Approach 2 — Iterative DFS

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | Each node is pushed and popped once |
    | **Space** | O(h) | Stack holds at most one path worth of nodes at any time (proportional to height) |

    ---

    ### Approach 3 — Iterative BFS

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | Each node is enqueued and dequeued once |
    | **Space** | O(w) | Queue holds at most one full level of the tree; worst case is O(n/2) ≈ O(n) for the last level of a complete binary tree |

    > **Key insight:** BFS uses more memory on wide trees; DFS (recursive or iterative) is more memory-efficient on balanced trees.

    ---

    ## 6. Complete Worked Examples

    ### Example 1 (All Approaches) — targetSum = 22

    ```
    Tree:
            5
        / \
        4   8
        /   / \
        11  13   4
    /  \       \
    7    2        1
    ```

    #### Recursive DFS trace:

    ```
    hasPathSum(5, 22)
    remaining = 22 - 5 = 17
    hasPathSum(4, 17)
        remaining = 17 - 4 = 13
        hasPathSum(11, 13)
        remaining = 13 - 11 = 2
        hasPathSum(7, 2)
            remaining = 2 - 7 = -5
            leaf node, -5 ≠ 0 → false
        hasPathSum(2, 2)
            remaining = 2 - 2 = 0
            leaf node, 0 == 0 → ✅ TRUE
    ```

    Result: `true`

    ---

    #### Iterative DFS trace (stack state):

    | Step | Stack (node, remaining) | Action |
    |------|------------------------|--------|
    | Start | `[(5, 22)]` | Push root |
    | Pop 5 | `[]` remaining=17, push children | Push (4,17), (8,17) |
    | Pop 8 | `[(4,17)]` remaining=9, push children | Push (13,9),(4,9) |
    | Pop 4 | `[(4,17),(13,9)]` remaining=8 | Push (1,8) |
    | Pop 1 | `[(4,17),(13,9)]` remaining=7, leaf, 7≠0 | Continue |
    | Pop 13 | `[(4,17)]` remaining=-4, leaf, ≠0 | Continue |
    | Pop 4 | `[]` remaining=13 | Push (11,13) |
    | Pop 11 | `[]` remaining=2 | Push (7,2),(2,2) |
    | Pop 2 | `[(7,2)]` remaining=0, leaf, ==0 | ✅ Return TRUE |

    ---

    ### Example 2 — Single node, targetSum = 1

    ```
    Tree:   [1]     targetSum = 1
    ```

    **Recursive:**
    - `hasPathSum(node(1), 1)`
    - `remaining = 1 - 1 = 0`
    - `node.left == null && node.right == null` → leaf!
    - `remaining == 0` → ✅ `true`

    ---

    ### Example 3 — targetSum not achievable

    ```
    Tree:
        1
    / \
    2   3

    targetSum = 5
    ```

    **Recursive trace:**
    ```
    hasPathSum(1, 5) → remaining = 4
    hasPathSum(2, 4) → remaining = 2, leaf, 2≠0 → false
    hasPathSum(3, 4) → remaining = 1, leaf, 1≠0 → false
    → false
    ```

    ---

    ## 7. Edge Cases

    | Edge Case | Description | How Each Approach Handles It |
    |-----------|-------------|------------------------------|
    | **Empty tree** (`root == null`) | No nodes at all | All approaches return `false` immediately via null check |
    | **Single node, matches** | Tree is one leaf equal to targetSum | Leaf check passes, `remaining == 0` → `true` |
    | **Single node, no match** | One leaf, value ≠ targetSum | Leaf check fails → `false` |
    | **Negative values** | Path `5 → -3 → 6` sums to 8 | All approaches handle this naturally — no early pruning based on magnitude |
    | **All negative values** | e.g., target = -10 | Works correctly — subtraction tracks running total regardless of sign |
    | **targetSum = 0** | Need a root-to-leaf path that sums to 0 | Works — checks `remaining == 0` at leaf |
    | **Deep skewed tree (n=5000)** | Like a linked list, recursion depth = 5000 | Recursive DFS **risks StackOverflow** in Java (~default stack ~500-1000 deep). Iterative DFS/BFS is safe here |
    | **Overflow risk** | Node values ≤ 1000, max 5000 nodes → max sum = 5,000,000 | Fits in `int` (max ~2.1 billion) — no overflow risk given constraints |
    | **Internal node matches but not leaf** | e.g., root has value == targetSum but has children | The `left == null && right == null` guard correctly rejects this |

    > ⚠️ **Important Java-specific warning:** For very deep skewed trees (n = 5000), the **recursive approach may cause a `StackOverflowError`**. The iterative DFS or BFS approaches are safer for production code.

    ---

    ## 8. Final Summary

    ### Comparison Table

    | Approach | Time | Space | Pros | Cons |
    |----------|------|-------|------|------|
    | Recursive DFS | O(n) | O(h) | Cleanest, most readable, natural for trees | Stack overflow on deep skewed trees |
    | Iterative DFS | O(n) | O(h) | Safe, same efficiency as recursive | Slightly more verbose |
    | Iterative BFS | O(n) | O(w) | Level-by-level, finds shallow paths faster | Uses more memory on wide/complete trees |

    ### ✅ Recommended Approach
    **Recursive DFS** for interviews — it's the most natural and readable. If the interviewer mentions very deep trees or asks about iterative solutions, pivot to **Iterative DFS with a stack**.

    ### What to Remember
    > **Pattern:** This is a classic **DFS with running state** problem. The key insight is to *subtract* the current node's value and check for zero *only at a leaf*. This same pattern extends directly to **Path Sum II** (collect all paths), **Path Sum III** (any subpath), and many other tree-path problems.
    
    */
}
