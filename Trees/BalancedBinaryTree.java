package Trees;

import Datastructures.TreeNode;

public class BalancedBinaryTree {
    public static void main(String[] args) {
        BalancedBinaryTree balancedBinaryTree = new BalancedBinaryTree();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println("BalancedBinaryTree : " + balancedBinaryTree.isBalanced(root));

        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(2);
        root1.left.left = new TreeNode(3);
        root.left.right = new TreeNode(3);
        root1.left.left.left = new TreeNode(4);
        root1.left.left.right = new TreeNode(4);

        System.out.println("BalancedBinaryTree : " + balancedBinaryTree.isBalanced(root1));
    }

    /*
     * https://leetcode.com/problems/balanced-binary-tree/description/?envType=
     * problem-list-v2&envId=tree
     * 
     * 
     * Given a binary tree, determine if it is height-balanced.
     * 
     * A height-balanced binary tree is a binary tree in which the depth of the two
     * subtrees of every node never differs by more than one.
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: root = [3,9,20,null,null,15,7]
     * Output: true
     * Example 2:
     * 
     * 
     * Input: root = [1,2,2,3,3,null,null,4,4]
     * Output: false
     * Example 3:
     * 
     * Input: root = []
     * Output: true
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [0, 5000].
     * -104 <= Node.val <= 104
     */

    public boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }

    public int checkHeight(TreeNode root) {
        if (root == null)
            return 0;
        int leftHeight = checkHeight(root.left);
        if (leftHeight == -1)
            return leftHeight;
        int rightHeight = checkHeight(root.right);
        if (rightHeight == -1)
            return rightHeight;

        if (Math.abs(leftHeight - rightHeight) > 1)
            return -1;

        return 1 + Math.max(leftHeight, rightHeight);
    }

//     # Balanced Binary Tree — Deep Dive

    // ---

    // ## 1. Problem Statement

    // ### What the Problem Says
    // Given a binary tree, determine whether it is **height-balanced**.

    // A binary tree is height-balanced if, **for every node** in the tree, the absolute difference between the heights of its **left subtree** and **right subtree** is **at most 1**.

    // ### Input Format
    // - The root of a binary tree: `TreeNode root`
    // - `TreeNode` has: `int val`, `TreeNode left`, `TreeNode right`

    // ### Output Format
    // - `boolean` — `true` if the tree is balanced, `false` otherwise

    // ### Constraints (LeetCode #110)
    // - Number of nodes: `0 ≤ n ≤ 5000`
    // - Node values: `-10⁴ ≤ val ≤ 10⁴`

    // ### What Exactly Needs to Be Computed
    // For **every single node** (not just the root), verify:
    // ```
    // | height(node.left) - height(node.right) | <= 1
    // ```
    // All nodes must satisfy this condition simultaneously.

    // ---

    // ## 2. Intuition

    // ### Think Like a Human
    // Imagine you're physically measuring a real tree. You want to check if it "looks balanced" — no branch is drastically taller than its sibling on any side, at **any level**.

    // - Start at the bottom (leaves)
    // - Each leaf has height 0
    // - Move up: a node's height = 1 + max(left height, right height)
    // - At each node, check: is the difference between left and right height ≤ 1?

    // ### The Core Insight
    // The key insight is that **height computation is recursive by nature** — to know the height of a node, you must already know the heights of its children. This means we can **piggyback the balance check onto the height computation itself**, killing two birds with one stone.

    // ### What Makes This Tricky
    // - The naive approach recomputes heights redundantly, visiting nodes multiple times
    // - The elegant solution requires returning **two pieces of information** from recursion: the height AND whether the subtree is balanced — which Java handles cleanly with a sentinel value (`-1` as "unbalanced signal")

    // ---

    // ## 3. Approach Overview

    // | # | Approach | Key Idea | Time | Space | Use When |
    // |---|----------|----------|------|-------|----------|
    // | 1 | **Brute Force** | For each node, separately compute heights of left/right subtrees | O(n²) | O(n) | Never in production; shows understanding |
    // | 2 | **Optimal (Bottom-Up DFS)** | Combine height calculation + balance check in single DFS pass | O(n) | O(n) | Always — this is the standard solution |

    // > ✅ **Approach 2 is optimal.** It visits each node exactly once.

    // ---

    // ## 4. Detailed Solutions in Java

    // ### Approach 1 — Brute Force (Top-Down)

    // **Algorithm:**
    // 1. At every node, recursively compute the height of the left subtree
    // 2. Recursively compute the height of the right subtree
    // 3. Check if their difference ≤ 1
    // 4. Recursively verify that both left and right subtrees are themselves balanced
    // 5. All four conditions must hold

    // ```java
    // class Solution {
        
    //     public boolean isBalanced(TreeNode root) {
    //         // Base case: empty tree is balanced
    //         if (root == null) return true;
            
    //         // Compute heights of left and right subtrees independently
    //         int leftHeight  = getHeight(root.left);
    //         int rightHeight = getHeight(root.right);
            
    //         // Check balance at this node AND recursively in both subtrees
    //         boolean currentNodeBalanced = Math.abs(leftHeight - rightHeight) <= 1;
    //         boolean leftSubtreeBalanced  = isBalanced(root.left);
    //         boolean rightSubtreeBalanced = isBalanced(root.right);
            
    //         return currentNodeBalanced && leftSubtreeBalanced && rightSubtreeBalanced;
    //     }
        
    //     // Computes the height of a subtree rooted at 'node'
    //     // Height = number of edges on the longest path to a leaf
    //     private int getHeight(TreeNode node) {
    //         if (node == null) return 0;
    //         return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    //     }
    // }
    // ```

    // **Why it's inefficient:** `getHeight` is called separately for every node, and each call re-traverses entire subtrees. A node deep in the tree gets visited O(n) times in the worst case.

    // ---

    // ### Approach 2 — Optimal Bottom-Up DFS (Single Pass)

    // **Algorithm:**
    // 1. Perform a **post-order DFS** (left → right → current)
    // 2. Each recursive call returns the **height** of the subtree at that node
    // 3. Use `-1` as a **sentinel value** meaning "this subtree is unbalanced"
    // 4. If either child returns `-1`, immediately propagate `-1` upward (early exit)
    // 5. If the height difference at the current node exceeds 1, return `-1`
    // 6. Otherwise, return the actual height: `1 + max(leftHeight, rightHeight)`
    // 7. After the full DFS, check if the result is `!= -1`

    // ```java
    // class Solution {
        
    //     public boolean isBalanced(TreeNode root) {
    //         // If checkHeight returns -1, tree is unbalanced
    //         return checkHeight(root) != -1;
    //     }
        
    //     /**
    //      * Returns the height of the subtree rooted at 'node' if it is balanced,
    //      * or -1 if the subtree is unbalanced (sentinel value).
    //      *
    //      * Post-order: process children before the current node.
    //      */
    //     private int checkHeight(TreeNode node) {
    //         // Base case: null node has height 0 and is trivially balanced
    //         if (node == null) return 0;
            
    //         // --- Recurse Left ---
    //         int leftHeight = checkHeight(node.left);
    //         if (leftHeight == -1) return -1;  // Left subtree already unbalanced, prune early
            
    //         // --- Recurse Right ---
    //         int rightHeight = checkHeight(node.right);
    //         if (rightHeight == -1) return -1; // Right subtree already unbalanced, prune early
            
    //         // --- Check balance at current node ---
    //         if (Math.abs(leftHeight - rightHeight) > 1) return -1; // Unbalanced here
            
    //         // Balanced at this node — return actual height to parent
    //         return 1 + Math.max(leftHeight, rightHeight);
    //     }
    // }
    // ```

    // ---

    // ## 5. Time & Space Complexity

    // ### Approach 1 — Brute Force

    // | | Complexity | Reasoning |
    // |--|------------|-----------|
    // | **Time** | O(n²) | For each of the n nodes, `getHeight` traverses its entire subtree. For a skewed tree, node at depth k causes O(k) work → total = O(1+2+...+n) = O(n²) |
    // | **Space** | O(n) | Recursion stack depth = O(h). In a skewed tree, h = n → O(n) |

    // **Worked example at scale:**
    // - Tree with 1000 nodes in a straight line (worst case skewed)
    // - Root calls `getHeight` → visits 999 nodes
    // - Root's child calls `getHeight` → visits 998 nodes
    // - ...Total ≈ 999 + 998 + ... + 1 ≈ **499,500 operations**

    // ### Approach 2 — Optimal

    // | | Complexity | Reasoning |
    // |--|------------|-----------|
    // | **Time** | O(n) | Every node is visited exactly once in the post-order DFS |
    // | **Space** | O(n) | Recursion stack = O(h). Worst case skewed tree: O(n). Balanced tree: O(log n) |

    // **Worked example at scale:**
    // - Tree with 1000 nodes
    // - Each node is visited exactly once → **exactly 1000 operations**

    // ---

    // ## 6. Complete Worked Examples

    // ### Example Tree
    // ```
    //         1
    //        / \
    //       2   3
    //      / \
    //     4   5
    //    /
    //   6
    // ```

    // ### Approach 1 — Brute Force Walkthrough

    // | Node | leftHeight | rightHeight | |diff| ≤ 1? | left balanced? | right balanced? | Result |
    // |------|-----------|------------|-------------|----------------|-----------------|--------|
    // | 6 | 0 | 0 | ✅ 0 | ✅ | ✅ | `true` |
    // | 4 | 1 | 0 | ✅ 1 | ✅ | ✅ | `true` |
    // | 5 | 0 | 0 | ✅ 0 | ✅ | ✅ | `true` |
    // | 2 | **2** | **1** | ✅ 1 | ✅ | ✅ | `true` |
    // | 3 | 0 | 0 | ✅ 0 | ✅ | ✅ | `true` |
    // | 1 | **3** | **1** | ❌ **2** | — | — | **`false`** |

    // **Output: `false`** — node `1` has left height 3, right height 1, difference = 2.

    // Note the redundancy: `getHeight(node 2)` is called once from `isBalanced(1)` and once from `isBalanced(2)` — duplicate work.

    // ---

    // ### Approach 2 — Optimal Walkthrough

    // Post-order traversal visits: **6 → 4 → 5 → 2 → 3 → 1**

    // **Step-by-step `checkHeight` calls:**

    // ```
    // checkHeight(6):
    //   left  = checkHeight(null) = 0
    //   right = checkHeight(null) = 0
    //   |0 - 0| = 0 ≤ 1  ✅
    //   returns 1 + max(0,0) = 1

    // checkHeight(4):
    //   left  = checkHeight(6) = 1
    //   right = checkHeight(null) = 0
    //   |1 - 0| = 1 ≤ 1  ✅
    //   returns 1 + max(1,0) = 2

    // checkHeight(5):
    //   left  = checkHeight(null) = 0
    //   right = checkHeight(null) = 0
    //   |0 - 0| = 0 ≤ 1  ✅
    //   returns 1 + max(0,0) = 1

    // checkHeight(2):
    //   left  = checkHeight(4) = 2
    //   right = checkHeight(5) = 1
    //   |2 - 1| = 1 ≤ 1  ✅
    //   returns 1 + max(2,1) = 3

    // checkHeight(3):
    //   left  = checkHeight(null) = 0
    //   right = checkHeight(null) = 0
    //   returns 1

    // checkHeight(1):
    //   left  = checkHeight(2) = 3
    //   right = checkHeight(3) = 1
    //   |3 - 1| = 2 > 1  ❌
    //   returns -1  ← sentinel!

    // isBalanced: checkHeight(root) = -1 → returns false
    // ```

    // **Output: `false`** ✅ — reached in a single clean pass, no redundant computation.

    // ---

    // ### Second Example — Balanced Tree

    // ```
    //       1
    //      / \
    //     2   3
    //    / \
    //   4   5
    // ```

    // **Optimal walkthrough:**
    // ```
    // checkHeight(4) = 1
    // checkHeight(5) = 1
    // checkHeight(2): left=1, right=1, |diff|=0 ✅ → returns 2
    // checkHeight(3) = 1
    // checkHeight(1): left=2, right=1, |diff|=1 ✅ → returns 3

    // isBalanced: 3 != -1 → true ✅
    // ```

    // ---

    // ## 7. Edge Cases

    // | Edge Case | What Happens | Both Approaches |
    // |-----------|-------------|-----------------|
    // | `root == null` | Empty tree | Returns `true` (base case: `null` is balanced) |
    // | Single node | No children, height = 1 | Returns `true` — diff = 0 |
    // | Perfect binary tree | All levels full, all diffs = 0 | Returns `true` |
    // | Completely skewed tree (linked list) | Every node has diff = 1 at most...wait — actually the ROOT has diff = n-1 | Returns `false` for n ≥ 3 |
    // | Two nodes only | Root + one child | Returns `false` — root has diff of 1... wait, that IS ≤ 1 → `true` |
    // | Subtree balanced but root not | e.g., left subtree height 3, right height 1 | Correctly returns `false` at root level |
    // | Very deep tree (n=5000, skewed) | Brute force: ~12.5M ops; Optimal: 5000 ops | Approach 2 handles gracefully |

    // ### Special Case — "Locally Balanced, Globally Unbalanced"
    // ```
    //         1
    //        / \
    //       2   3
    //      /
    //     4
    //    /
    //   5
    // ```
    // - Node 4: diff = 1 ✅
    // - Node 2: left height = 2, right height = 0, diff = **2** ❌
    // - Correctly returns `false`

    // This is the classic trap: **you must check every node, not just leaves or the root.**

    // ---

    // ## 8. Final Summary

    // | Approach | Time | Space | Recommended? |
    // |----------|------|-------|--------------|
    // | Brute Force (Top-Down) | O(n²) | O(n) | ❌ Only for understanding |
    // | Optimal Bottom-Up DFS | O(n) | O(n) | ✅ Always use this |

    // ### What to Remember

    // > **The key pattern:** Whenever you need to compute a property at every node in a tree AND that property depends on children, use **bottom-up post-order DFS** — compute children first, then use their results at the parent. Use a **sentinel return value** (like `-1`) to signal failure and enable early pruning.

    // This same pattern appears in many tree problems: **Diameter of Binary Tree**, **Maximum Path Sum**, **Lowest Common Ancestor** — mastering it here gives you a reusable mental template for the entire category.
}

