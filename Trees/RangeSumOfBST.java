package Trees;

import java.util.ArrayDeque;
import java.util.Deque;

import Datastructures.TreeNode;

public class RangeSumOfBST {
    public static void main(String[] args) {
        RangeSumOfBST rangeSumOfBST = new RangeSumOfBST();
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(7);
        root.right.right = new TreeNode(18);
        System.out.println("RangeSumOfBST : " + rangeSumOfBST.iterativeDFSWithPruningStackBased(root, 7, 15));
    }

    /*
     * https://leetcode.com/problems/range-sum-of-bst/description/?envType=problem-list-v2&envId=tree
     * 
     * Given the root node of a binary search tree and two integers low and high,
     * return the sum of values of all nodes with a value in the inclusive range
     * [low, high].
     * 
     * 
     * 
     * Example 1:
     * 
     *      10
     *     /  \
     *    5    15
     *   / \     \
     *  3   7     18
     * 
     * Input: root = [10,5,15,3,7,null,18], low = 7, high = 15
     * Output: 32
     * Explanation: Nodes 7, 10, and 15 are in the range [7, 15]. 7 + 10 + 15 = 32.
     * Example 2:
     * 
     * 
     * Input: root = [10,5,15,3,7,13,18,1,null,6], low = 6, high = 10
     * Output: 23
     * Explanation: Nodes 6, 7, and 10 are in the range [6, 10]. 6 + 7 + 10 = 23.
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [1, 2 * 104].
     * 1 <= Node.val <= 105
     * 1 <= low <= high <= 105
     * All Node.val are unique.
     */

    public int rangeSumBST(TreeNode root, int low, int high) {
        if (root == null)
            return 0;
        return prunedRecursiveDFSBSTAware(root, low, high);
    }

    public int prunedRecursiveDFSBSTAware(TreeNode root, int low, int high) {
        if (root == null)
            return 0;
        if (root.val > high)
            return prunedRecursiveDFSBSTAware(root.left, low, high);
        if (root.val < low)
            return prunedRecursiveDFSBSTAware(root.right, low, high);
        return root.val + prunedRecursiveDFSBSTAware(root.left, low, high)
                + prunedRecursiveDFSBSTAware(root.right, low, high);
    }

    public int iterativeDFSWithPruningStackBased(TreeNode root, int low, int high) {
        int sum = 0;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.push(root);
        while (!queue.isEmpty()) {
            TreeNode current = queue.pop();
            if (current == null)
                continue;
            if (current.val < low) {
                if (current.right != null)
                    queue.push(current.right);
            } else if (current.val > high) {
                if (current.left != null)
                    queue.push(current.left);
            } else {
                sum += current.val;
                if (current.left != null)
                    queue.push(current.left);
                if (current.right != null)
                    queue.push(current.right);
            }
        }
        return sum;
    }

    /*
    
    # Range Sum of BST — Deep Dive

    ---

    ## 1. Problem Statement

    ### In Plain English
    You are given the **root of a Binary Search Tree (BST)** and two integers `low` and `high`. Your task is to return the **sum of all node values** in the BST that fall within the inclusive range `[low, high]`.

    ### Input Format
    - A BST root node of type `TreeNode`
    - Two integers: `low` and `high` (where `low <= high`)

    ### Output Format
    - A single integer representing the sum of all values `v` in the tree where `low <= v <= high`

    ### Constraints
    ```
    Number of nodes: [1, 2 * 10^4]
    Node values:     [1, 10^5]    (all unique)
    low, high:       [1, 10^5]
    low <= high
    ```

    ### What Exactly Needs to Be Returned
    The **sum** of every node's value where `low ≤ node.val ≤ high`. Not the count, not the nodes themselves — the **integer sum**.

    ---

    ## 2. Intuition

    ### The Core Idea
    Imagine you're looking through a phonebook (sorted A–Z). If you want names between "M" and "P", you don't read every single page — you **jump to M, read until P, then stop**. A BST works exactly the same way, but with numbers.

    ### How a Human Would Reason
    1. Start at the root.
    2. If the current value is **within** `[low, high]` → **include it** in the sum, then check **both** subtrees (because left could have more valid nodes, and so could right).
    3. If the current value is **less than low** → the **entire left subtree is also too small** (BST property), so only go **right**.
    4. If the current value is **greater than high** → the **entire right subtree is also too large**, so only go **left**.

    ### What Makes This Interesting
    - The **BST property** (left < root < right) lets you **prune entire branches** you'd otherwise have to visit.
    - This transforms a potentially O(n) scan into something much faster in practice when the range is narrow.
    - It blends **tree traversal** with **binary search logic** — two classic concepts in one problem.

    ---

    ## 3. Approach Overview

    | # | Approach | Key Idea | Time | Space | Use When |
    |---|----------|----------|------|-------|----------|
    | 1 | Brute Force (Full Traversal) | Visit every node, add if in range | O(n) | O(n) | Never preferred; good baseline |
    | 2 | Optimized DFS (Recursive) | Use BST property to prune branches | O(n) worst, faster avg | O(h) | ✅ **Optimal — interviews** |
    | 3 | Iterative BFS/DFS | Same pruning, no recursion stack | O(n) worst | O(n) | When stack overflow is a concern |

    > ✅ **Recommended: Approach 2 — Optimized Recursive DFS**
    > It is clean, short, leverages the BST property for pruning, and is exactly what interviewers expect. The iterative version is equally valid if recursion depth is a concern.

    ---

    ## 4. Detailed Solutions in Java

    ### TreeNode Definition (assumed/provided)
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

    ### Approach 1 — Brute Force: Full Tree Traversal

    #### Algorithm (Step-by-Step)
    1. Traverse **every single node** in the tree (no pruning).
    2. For each node, check if `low <= node.val <= high`.
    3. If yes, add `node.val` to the running sum.
    4. Recurse on both left and right children regardless.

    #### Java Code
    ```java
    class Solution {
        public int rangeSumBST(TreeNode root, int low, int high) {
            return dfs(root, low, high);
        }

        private int dfs(TreeNode node, int low, int high) {
            // Base case: null node contributes 0
            if (node == null) return 0;

            int currentSum = 0;

            // Include current node's value if it's within range
            if (node.val >= low && node.val <= high) {
                currentSum += node.val;
            }

            // Always recurse on both sides (brute force — no pruning)
            currentSum += dfs(node.left, low, high);
            currentSum += dfs(node.right, low, high);

            return currentSum;
        }
    }
    ```

    ---

    ### Approach 2 — ✅ Optimal: Pruned Recursive DFS (BST-Aware)

    #### Algorithm (Step-by-Step)
    1. If `node == null`, return 0.
    2. If `node.val < low` → left subtree is entirely out of range; **recurse only right**.
    3. If `node.val > high` → right subtree is entirely out of range; **recurse only left**.
    4. If `low <= node.val <= high` → node is valid; **recurse both sides and add node's value**.

    #### Java Code
    ```java
    class Solution {
        public int rangeSumBST(TreeNode root, int low, int high) {
            return dfs(root, low, high);
        }

        private int dfs(TreeNode node, int low, int high) {
            // Base case
            if (node == null) return 0;

            // Current node is below range → only right subtree can have valid nodes
            if (node.val < low) {
                return dfs(node.right, low, high);
            }

            // Current node is above range → only left subtree can have valid nodes
            if (node.val > high) {
                return dfs(node.left, low, high);
            }

            // Current node is within range → include it, explore both subtrees
            return node.val
                + dfs(node.left, low, high)
                + dfs(node.right, low, high);
        }
    }
    ```

    ---

    ### Approach 3 — Iterative DFS with Pruning (Stack-Based)

    #### Algorithm (Step-by-Step)
    1. Push the root onto a `Deque` (used as a stack).
    2. While the stack is not empty:
    - Pop a node.
    - If null, skip.
    - If `node.val` is in range, add to sum and push **both** children.
    - If `node.val < low`, push **only** right child.
    - If `node.val > high`, push **only** left child.
    3. Return the accumulated sum.

    #### Java Code
    ```java
    import java.util.ArrayDeque;
    import java.util.Deque;

    class Solution {
        public int rangeSumBST(TreeNode root, int low, int high) {
            int sum = 0;
            Deque<TreeNode> stack = new ArrayDeque<>();
            stack.push(root);

            while (!stack.isEmpty()) {
                TreeNode node = stack.pop();

                if (node == null) continue; // Skip null nodes

                if (node.val < low) {
                    // Left subtree entirely out of range, explore only right
                    stack.push(node.right);
                } else if (node.val > high) {
                    // Right subtree entirely out of range, explore only left
                    stack.push(node.left);
                } else {
                    // Node is in range: add value, explore both children
                    sum += node.val;
                    stack.push(node.left);
                    stack.push(node.right);
                }
            }

            return sum;
        }
    }
    ```

    ---

    ## 5. Time & Space Complexity (With Reasoning)

    ### Approach 1 — Brute Force
    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | Every node is visited exactly once |
    | **Space** | O(h) | Recursion stack depth = tree height h; O(log n) balanced, O(n) skewed |

    **Example:** Tree with 10,000 nodes → ~10,000 recursive calls, ~10,000 value checks.

    ---

    ### Approach 2 — Optimized Recursive DFS ✅
    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) worst case, significantly less in practice | Worst case: all nodes in range. With narrow range, many branches are pruned. |
    | **Space** | O(h) | Only the recursion call stack is used; h = height of tree |

    **Example:** Tree has 10,000 nodes, but `[low, high]` covers only 50 values → might visit only ~100 nodes.

    ---

    ### Approach 3 — Iterative DFS
    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) worst case | Same pruning logic as Approach 2 |
    | **Space** | O(n) worst case | Stack can hold up to O(n) nodes in a wide tree (unlike recursion which is O(h)) |

    > 💡 **Key Insight:** Approach 2 is generally preferred in interviews for clarity. Approach 3 is safer for extremely deep/skewed trees where recursion risks a `StackOverflowError`.

    ---

    ## 6. Complete Worked Examples

    ### Example Setup — The BST
    ```
            10
            /   \
            5     15
            / \      \
        3   7      18
    ```
    `low = 7`, `high = 15`

    ---

    ### Approach 1 — Brute Force Walkthrough

    | Step | Node Visited | In Range? | Sum So Far |
    |------|-------------|-----------|------------|
    | 1 | 10 | ✅ 7≤10≤15 | 10 |
    | 2 | 5 | ❌ 5 < 7 | 10 |
    | 3 | 3 | ❌ 3 < 7 | 10 |
    | 4 | null (3.left) | — | 10 |
    | 5 | null (3.right) | — | 10 |
    | 6 | 7 | ✅ 7≤7≤15 | 17 |
    | 7 | null (7.left) | — | 17 |
    | 8 | null (7.right) | — | 17 |
    | 9 | 15 | ✅ 7≤15≤15 | 32 |
    | 10 | null (15.left) | — | 32 |
    | 11 | 18 | ❌ 18 > 15 | 32 |

    **Output: 32** ✅ — but visited ALL 7 nodes.

    ---

    ### Approach 2 — Optimized DFS Walkthrough

    | Step | Node | Decision | Action |
    |------|------|----------|--------|
    | 1 | 10 | 7≤10≤15 ✅ | Add 10, recurse both sides |
    | 2 | 5 | 5 < 7 ❌ | Skip left subtree, recurse only right |
    | 3 | 7 | 7≤7≤15 ✅ | Add 7, recurse both sides |
    | 4 | null (7.left) | — | Return 0 |
    | 5 | null (7.right) | — | Return 0 |
    | 6 | 15 | 7≤15≤15 ✅ | Add 15, recurse both sides |
    | 7 | null (15.left) | — | Return 0 |
    | 8 | 18 | 18 > 15 ❌ | Skip right subtree, recurse only left |
    | 9 | null (18.left) | — | Return 0 |

    **Nodes visited: 6 (node 3 is completely skipped!)**
    **Output: 10 + 7 + 15 = 32** ✅

    ---

    ### Approach 3 — Iterative Stack Walkthrough

    ```
    Stack state and processing:

    Initial Stack: [10]

    Pop 10 → in range → sum=10, push 5 and 15
    Stack: [5, 15]

    Pop 15 → in range → sum=25, push null and 18
    Stack: [5, null, 18]

    Pop 18 → 18 > 15 → push only left (null)
    Stack: [5, null, null]

    Pop null → skip
    Stack: [5, null]

    Pop null → skip
    Stack: [5]

    Pop 5 → 5 < 7 → push only right (7)
    Stack: [7]

    Pop 7 → in range → sum=32, push null and null
    Stack: [null, null]

    Pop null → skip
    Pop null → skip
    Stack: []
    ```

    **Output: 32** ✅

    ---

    ## 7. Edge Cases

    | Edge Case | Description | How Approaches Handle It |
    |-----------|-------------|--------------------------|
    | **Single node in range** | Tree has 1 node, it's in `[low, high]` | All return that single value correctly |
    | **Single node out of range** | Tree has 1 node, not in range | All return 0 correctly |
    | **All nodes in range** | `low=1, high=100000` | Visits all nodes; same as brute force |
    | **No nodes in range** | `low=50, high=60`, no values there | Pruning kicks in; returns 0 |
    | **Very deep skewed tree** | Tree is a linked list (20,000 nodes) | Approach 2 risks `StackOverflowError`; Approach 3 is safer |
    | **low == high** | Searching for exactly one value | Works correctly; returns that value or 0 |
    | **Large values near Integer.MAX_VALUE** | Sum of many large values | Values are max 10^5 and nodes max 2×10^4 → max sum = 2×10^9, fits in `int` (barely). Using `long` is safer in general |
    | **Root is null** | Empty tree | All approaches return 0 via null base case |

    ### Overflow Risk Note
    > Max possible sum = 20,000 nodes × 100,000 value = **2,000,000,000**, which is right at the edge of `Integer.MAX_VALUE` (≈ 2.1 billion). The constraints make it safe for `int`, but using `long` in interviews shows awareness.

    ---

    ## 8. Final Summary

    ### Approach Comparison

    | Approach | Time | Space | Code Simplicity | Recommended? |
    |----------|------|-------|-----------------|--------------|
    | Brute Force DFS | O(n) | O(h) | Simple | ❌ Wastes BST property |
    | Optimized Recursive DFS | O(n) worst / faster avg | O(h) | Very clean | ✅ **Yes — preferred** |
    | Iterative DFS | O(n) worst / faster avg | O(n) | Moderate | ✅ For deep trees |

    ### What to Remember
    > **This problem is a classic "exploit the BST property to prune" pattern.** Whenever you see a range query on a BST, immediately think: *"If current node < low → go right only. If current node > high → go left only."* This single insight is the entire key to an optimal solution.

    ---

    ## 9. Companies & Frequency

    ### Where This Problem Has Been Asked

    | Company | Frequency | Notes |
    |---------|-----------|-------|
    | 🏢 **Amazon** | ⭐⭐⭐⭐⭐ Very High | Most frequently asked — BST fundamentals |
    | 🏢 **Google** | ⭐⭐⭐⭐ High | Appears in phone screens and onsite |
    | 🏢 **Microsoft** | ⭐⭐⭐⭐ High | Common in SDE-1/SDE-2 rounds |
    | 🏢 **Facebook/Meta** | ⭐⭐⭐ Medium | Appears in coding screens |
    | 🏢 **Apple** | ⭐⭐⭐ Medium | Part of data structures rounds |
    | 🏢 **Bloomberg** | ⭐⭐⭐ Medium | Common in junior/mid-level interviews |
    | 🏢 **Adobe** | ⭐⭐ Moderate | Occasionally seen |
    | 🏢 **Uber** | ⭐⭐ Moderate | In earlier interview rounds |

    ### LeetCode Stats
    - **Problem #938** on LeetCode
    - Difficulty: **Easy**
    - Acceptance Rate: ~85%
    - Appeared in **150+ company interview reports** over the past 3 years
    - Particularly popular in **Amazon OA (Online Assessments)** and as a **warm-up** BST question before harder tree problems

    ### Interview Tip
    > This problem is often used as a **gateway BST question** before interviewers follow up with harder variations like "find the closest value in a BST", "BST iterator", or "kth smallest element". Mastering this problem's pruning logic sets you up for all of those.
    
    */
    
}
