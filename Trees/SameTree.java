package Trees;

import Datastructures.TreeNode;

public class SameTree {
    public static void main(String[] args) {
        SameTree sameTree = new SameTree();
        TreeNode p = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        p.left = left;
        p.right = right;
        TreeNode q = new TreeNode(1);
        q.left = left;
        q.right = right;
        System.out.println("SameTree : " + sameTree.isSameTree(p, q));
    }

    /*
     * https://leetcode.com/problems/same-tree/description/?envType=problem-list-v2&
     * envId=tree
     * 
     * 
     * Given the roots of two binary trees p and q, write a function to check if
     * they are the same or not.
     * 
     * Two binary trees are considered the same if they are structurally identical,
     * and the nodes have the same value.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: p = [1,2,3], q = [1,2,3]
     * Output: true
     * Example 2:
     * 
     * 
     * Input: p = [1,2], q = [1,null,2]
     * Output: false
     * Example 3:
     * 
     * 
     * Input: p = [1,2,1], q = [1,1,2]
     * Output: false
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in both trees is in the range [0, 100].
     * -104 <= Node.val <= 104
     */

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null)
            return true;
        if ((p == null || q == null) || (p.val != q.val))
            return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }


    /*
    # Same Tree — Deep Dive

    ---

    ## 1. Problem Statement

    ### In Plain Terms
    You are given the roots of **two binary trees**, `p` and `q`. Your task is to determine whether both trees are **exactly the same** — meaning they have identical structure AND identical node values at every corresponding position.

    ### Input Format
    - Two `TreeNode` references: `p` and `q`
    - `TreeNode` is defined as:
    ```java
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) { this.val = val; }
    }
    ```
    - Number of nodes in each tree: `0` to `100`
    - Node values: `-10^4` to `10^4`

    ### Output Format
    - Return `true` if both trees are identical
    - Return `false` otherwise

    ### What Exactly Needs to Be Computed
    At **every corresponding node pair** across the two trees:
    1. Both must be `null`, OR
    2. Both must be non-`null` with the **same value**, AND their **left subtrees** are the same, AND their **right subtrees** are the same

    ---

    ## 2. Intuition

    ### How a Human Reasons About This

    Imagine you have two physical trees drawn on paper. You place them side by side and walk through them **node by node, in the same order**. At each step you ask:

    > "Are these two nodes the same?"

    - If **both are empty** → ✅ this branch matches
    - If **one is empty and the other isn't** → ❌ structure differs
    - If **both exist but values differ** → ❌ values differ
    - If **both exist with same value** → recurse left, recurse right

    ### What Makes It Interesting
    - It's a **recursive decomposition** problem — the definition of "same tree" is naturally recursive
    - The **base cases** (both null, one null) are what make it terminate
    - It appears simple but teaches a foundational tree traversal pattern used in dozens of harder problems (subtree checking, symmetric tree, etc.)

    ---

    ## 3. Approach Overview

    | # | Approach | Key Idea | Best For |
    |---|----------|----------|----------|
    | 1 | **Recursive DFS** | Check nodes top-down recursively | ✅ Optimal, interviews |
    | 2 | **Iterative BFS (Queue)** | Level-order traversal with paired nodes | When stack overflow is a concern |
    | 3 | **Iterative DFS (Stack)** | Explicit stack mimics recursion | Stack overflow avoidance, same performance |

    ### Which Is Optimal?
    All three are **O(n) time and O(h) / O(n) space**, but **Recursive DFS** is the cleanest, most readable, and expected in interviews. The iterative approaches are useful when the tree could be extremely deep (stack overflow risk in Java with ~10,000+ levels).

    ---

    ## 4. Detailed Solutions in Java

    ---

    ### ✅ Approach 1 — Recursive DFS (Optimal)

    #### Algorithm Step-by-Step
    1. **Base Case A:** If both `p` and `q` are `null` → return `true` (both branches ended together)
    2. **Base Case B:** If exactly one is `null` → return `false` (structural mismatch)
    3. **Value Check:** If `p.val != q.val` → return `false`
    4. **Recurse:** Return `isSameTree(p.left, q.left) && isSameTree(p.right, q.right)`

    ```java
    class Solution {
        public boolean isSameTree(TreeNode p, TreeNode q) {
            // Base case: both nodes are null — subtrees match
            if (p == null && q == null) return true;

            // One is null, the other isn't — structural mismatch
            if (p == null || q == null) return false;

            // Values differ — not the same
            if (p.val != q.val) return false;

            // Recursively verify left and right subtrees
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }
    }
    ```

    ---

    ### Approach 2 — Iterative BFS (Queue)

    #### Algorithm Step-by-Step
    1. Use a `Queue` that stores **pairs** of nodes (one from each tree) at the same position
    2. Seed the queue with `(p, q)`
    3. Each iteration: poll a pair, apply the same 3 checks as recursion
    4. If checks pass, enqueue both left children as a pair, both right children as a pair
    5. If queue empties without returning false → return `true`

    ```java
    import java.util.LinkedList;
    import java.util.Queue;

    class Solution {
        public boolean isSameTree(TreeNode p, TreeNode q) {
            Queue<TreeNode[]> queue = new LinkedList<>();
            queue.offer(new TreeNode[]{p, q});

            while (!queue.isEmpty()) {
                TreeNode[] pair = queue.poll();
                TreeNode nodeP = pair[0];
                TreeNode nodeQ = pair[1];

                // Both null: this pair matches, continue
                if (nodeP == null && nodeQ == null) continue;

                // Structural mismatch or value mismatch
                if (nodeP == null || nodeQ == null) return false;
                if (nodeP.val != nodeQ.val) return false;

                // Enqueue children pairs for further comparison
                queue.offer(new TreeNode[]{nodeP.left, nodeQ.left});
                queue.offer(new TreeNode[]{nodeP.right, nodeQ.right});
            }

            return true;
        }
    }
    ```

    ---

    ### Approach 3 — Iterative DFS (Stack)

    #### Algorithm Step-by-Step
    Same logic as BFS but uses a `Stack` (LIFO) instead of a queue, giving depth-first order. Behaviorally identical to recursion but avoids call-stack usage.

    ```java
    import java.util.Stack;

    class Solution {
        public boolean isSameTree(TreeNode p, TreeNode q) {
            Stack<TreeNode[]> stack = new Stack<>();
            stack.push(new TreeNode[]{p, q});

            while (!stack.isEmpty()) {
                TreeNode[] pair = stack.pop();
                TreeNode nodeP = pair[0];
                TreeNode nodeQ = pair[1];

                if (nodeP == null && nodeQ == null) continue;
                if (nodeP == null || nodeQ == null) return false;
                if (nodeP.val != nodeQ.val) return false;

                // Push right first so left is processed first (mirrors recursive DFS)
                stack.push(new TreeNode[]{nodeP.right, nodeQ.right});
                stack.push(new TreeNode[]{nodeP.left, nodeQ.left});
            }

            return true;
        }
    }
    ```

    ---

    ## 5. Time & Space Complexity

    ### Approach 1 — Recursive DFS

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | We visit each node at most once; `n` = total nodes in smaller tree |
    | **Space** | O(h) | Call stack depth = tree height `h`; worst case O(n) for skewed tree, O(log n) for balanced |

    **Example walk-through:**
    - Tree with 7 nodes (complete binary tree, height 3) → ~7 recursive calls, call stack at most 3 frames deep
    - Skewed tree with 100 nodes → 100 calls, 100 frames deep

    ---

    ### Approach 2 — Iterative BFS

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | Every node pair is enqueued and dequeued exactly once |
    | **Space** | O(n) | Queue can hold up to O(n) pairs in the worst case (last level of a perfect binary tree has n/2 nodes) |

    ---

    ### Approach 3 — Iterative DFS

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | Every node pair is pushed/popped once |
    | **Space** | O(h) | Stack holds at most O(h) pairs at any time — better than BFS for wide trees |

    ---

    ## 6. Complete Worked Examples

    ---

    ### Example 1 — Trees Are the Same

    **Input:**
    ```
    Tree p:       Tree q:
        1             1
    / \           / \
    2   3         2   3
    ```

    **Recursive DFS Trace:**

    | Call | p.val | q.val | Result |
    |------|-------|-------|--------|
    | isSameTree(1, 1) | 1 | 1 | values match → recurse |
    | isSameTree(2, 2) | 2 | 2 | values match → recurse |
    | isSameTree(null, null) [left of 2] | — | — | ✅ true |
    | isSameTree(null, null) [right of 2] | — | — | ✅ true |
    | isSameTree(3, 3) | 3 | 3 | values match → recurse |
    | isSameTree(null, null) [left of 3] | — | — | ✅ true |
    | isSameTree(null, null) [right of 3] | — | — | ✅ true |

    **Final Output:** `true`

    ---

    ### Example 2 — Structural Mismatch

    **Input:**
    ```
    Tree p:       Tree q:
        1             1
    /               \
    2                 2
    ```

    **Recursive DFS Trace:**

    | Call | nodeP | nodeQ | Result |
    |------|-------|-------|--------|
    | isSameTree(1, 1) | 1 | 1 | match → recurse left |
    | isSameTree(2, null) | 2 | null | ❌ one is null → false |

    **Short-circuit:** The `&&` in the left call returns `false` immediately, the right subtree is never checked.

    **Final Output:** `false`

    ---

    ### Example 3 — Value Mismatch Deep in Tree

    **Input:**
    ```
    Tree p:       Tree q:
        1             1
    / \           / \
    2   3         2   4
    ```

    **Recursive DFS Trace:**

    | Call | p.val | q.val | Result |
    |------|-------|-------|--------|
    | isSameTree(1, 1) | 1 | 1 | match → recurse |
    | isSameTree(2, 2) | 2 | 2 | match → recurse |
    | isSameTree(null,null) ×2 | — | — | ✅ true |
    | isSameTree(3, 4) | 3 | 4 | ❌ values differ → false |

    **Final Output:** `false`

    ---

    ### BFS Queue State — Example 1

    | Step | Queue Contents (pairs) | Action |
    |------|----------------------|--------|
    | Start | [(1,1)] | Seed queue |
    | Poll (1,1) | [] | vals match → enqueue children |
    | After enqueue | [(2,2), (3,3)] | — |
    | Poll (2,2) | [(3,3)] | vals match → enqueue children |
    | After enqueue | [(3,3), (null,null), (null,null)] | — |
    | Poll (3,3) | [(null,null),(null,null)] | vals match → enqueue children |
    | Poll (null,null) ×4 | [] | continue |
    | Queue empty | — | ✅ return true |

    ---

    ## 7. Edge Cases

    | Edge Case | Description | How Each Approach Handles It |
    |-----------|-------------|------------------------------|
    | **Both trees empty** | `p = null, q = null` | Base case A → `true` immediately ✅ |
    | **One tree empty** | `p = null, q = [1]` or vice versa | Base case B → `false` immediately ✅ |
    | **Single node, same value** | `p=[1], q=[1]` | Values match, both children null → `true` ✅ |
    | **Single node, different value** | `p=[1], q=[2]` | Value check → `false` ✅ |
    | **Same structure, one value differs** | Deep in a large tree | Recursion/iteration reaches the differing node → `false` ✅ |
    | **Identical values, different structure** | `p: 1->2` left child vs `q: 1->2` right child | Structural check catches it ✅ |
    | **Skewed tree (degenerate)** | 100 nodes all left children | Recursive DFS: 100 call frames (fine for n≤100). For larger n, prefer iterative ⚠️ |
    | **Negative values** | Values like `-10^4` | `!=` comparison handles negative integers correctly ✅ |
    | **Duplicate values** | All nodes have value `0` | Comparisons still work; structure check ensures correctness ✅ |

    ---

    ## 8. Final Summary

    | Approach | Time | Space | Readability | Recommended? |
    |----------|------|-------|-------------|--------------|
    | Recursive DFS | O(n) | O(h) | ⭐⭐⭐ Excellent | ✅ **Yes — use this** |
    | Iterative BFS | O(n) | O(n) | ⭐⭐ Good | Only if very wide trees |
    | Iterative DFS | O(n) | O(h) | ⭐⭐ Good | If stack overflow is a concern |

    ### What to Remember
    > **"Same Tree" is the canonical example of recursive tree decomposition** — break it into: handle nulls first, check current node, recurse on children. This exact pattern (with minor modifications) solves Symmetric Tree, Subtree of Another Tree, and many other tree problems.

    The key insight is that **both null counts as equal, one null means unequal** — getting those base cases right is the entire trick.
    */
}
