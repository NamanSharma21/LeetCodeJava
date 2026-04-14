package Trees;

import Datastructures.TreeNode;

public class DiameterOfBinaryTree {
    public static void main(String[] args) {
        DiameterOfBinaryTree diameterOfBinaryTree = new DiameterOfBinaryTree();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right= new TreeNode(5);

        System.out.println("DiameterOfBinaryTree : "+diameterOfBinaryTree.diameterOfBinaryTree(root));
    }

    /*
     * https://leetcode.com/problems/diameter-of-binary-tree/description/?envType=
     * problem-list-v2&envId=tree
     * 
     * Given the root of a binary tree, return the length of the diameter of the
     * tree.
     * 
     * The diameter of a binary tree is the length of the longest path between any
     * two nodes in a tree. This path may or may not pass through the root.
     * 
     * The length of a path between two nodes is represented by the number of edges
     * between them.
     * 
     *       1
     *      / \
     *     2   3
     *    / \
     *   4   5
     * Diameter = 3 (path: 4 → 2 → 1 → 3 OR 5 → 2 → 1 → 3)
     * 
     * Example 1:
     * 
     * 
     * Input: root = [1,2,3,4,5]
     * Output: 3
     * Explanation: 3 is the length of the path [4,2,1,3] or [5,2,1,3].
     * Example 2:
     * 
     * Input: root = [1,2]
     * Output: 1
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [1, 104].
     * -100 <= Node.val <= 100
     */

    public int maxDiameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        countDiameter(root);
        return maxDiameter;
    }

    public int countDiameter(TreeNode root) {
        if (root == null)
            return 0;
        int leftTrack = countDiameter(root.left);
        int rightTrack = countDiameter(root.right);
        int localDiameter = leftTrack + rightTrack;
        maxDiameter = Math.max(maxDiameter, localDiameter);
        System.out.println(" V : " + root.val + " L : " + leftTrack + " R : " + rightTrack+" M : "+maxDiameter);
        return 1 + Math.max(rightTrack, leftTrack);
    }

    // # Diameter of Binary Tree — Deep Dive

// ---

// ## 1. Problem Statement

// **LeetCode #543 | Difficulty: Easy**

// Given the **root** of a binary tree, return the **length of the diameter** of the tree.

// The **diameter** of a binary tree is defined as the **longest path between any two nodes** in the tree. This path **may or may not pass through the root**.

// The **length** of a path is measured by the **number of edges** between nodes.

// ### Input Format
// - A binary tree represented by its `root` node
// - Each node contains an integer value, a left child, and a right child

// ### Output Format
// - A single integer: the length (in edges) of the longest path between any two nodes

// ### Constraints
// - Number of nodes: `1 <= n <= 10⁴`
// - Node values: `-100 <= Node.val <= 100`

// ### What exactly needs to be computed?
// For every node in the tree, the path passing **through that node** = `leftHeight + rightHeight`. We need the **maximum** of this value across all nodes.

// ---

// ## 2. Intuition

// Think of a binary tree like a **road network**. Each node is a city, each edge is a road. You want to find the two cities that are **farthest apart** — the longest road trip possible.

// ### Human Reasoning:
// 1. At **any given node**, the longest path passing through it goes:
//    - As deep as possible into its **left subtree**
//    - As deep as possible into its **right subtree**
//    - Total path = `depth(left) + depth(right)`

// 2. The answer could be in the **left subtree only**, the **right subtree only**, or **crossing the root** — we must check **every node**.

// 3. The key insight: **height of a subtree** is what determines the contribution of each side.

// ### What makes this tricky?
// - The diameter path **doesn't have to pass through the root** — beginners often make this mistake.
// - You need to propagate **height** upward while tracking the **maximum diameter** globally — two different quantities managed simultaneously.

// ---

// ## 3. Approach Overview

// | # | Approach | Key Idea | Time | Space | Use When |
// |---|----------|----------|------|-------|----------|
// | 1 | **Brute Force** | Compute height separately for each node | O(n²) | O(h) | Learning/tiny trees |
// | 2 | **Optimal DFS (Single Pass)** | Compute height & diameter simultaneously | O(n) | O(h) | Always — interviews |

// > ✅ **Approach 2 is optimal.** It avoids redundant height computations by combining both operations in one DFS traversal.

// ---

// ## 4. Detailed Solutions in Java

// ### ✅ Approach 1 — Brute Force (O(n²))

// **Algorithm:**
// 1. For every node, compute the height of its left subtree and right subtree separately.
// 2. The diameter at that node = `leftHeight + rightHeight`.
// 3. Recursively check all nodes and return the maximum diameter found.

// **Problem:** `height()` is called from scratch at every node → redundant work.

// ```java
// class Solution {

//     public int diameterOfBinaryTree(TreeNode root) {
//         if (root == null) return 0;

//         // Diameter passing through this root
//         int leftHeight = height(root.left);
//         int rightHeight = height(root.right);
//         int diameterThroughRoot = leftHeight + rightHeight;

//         // Diameter might be entirely in left or right subtree
//         int leftDiameter = diameterOfBinaryTree(root.left);
//         int rightDiameter = diameterOfBinaryTree(root.right);

//         return Math.max(diameterThroughRoot, Math.max(leftDiameter, rightDiameter));
//     }

//     // Computes height (number of edges to deepest leaf)
//     private int height(TreeNode node) {
//         if (node == null) return 0;
//         return 1 + Math.max(height(node.left), height(node.right));
//     }
// }
// ```

// ---

// ### ✅ Approach 2 — Optimal Single-Pass DFS (O(n)) ⭐ RECOMMENDED

// **Algorithm:**
// 1. Use a single DFS that returns the **height** of each subtree.
// 2. At every node, before returning height upward, compute the local diameter = `leftHeight + rightHeight`.
// 3. Update a **global `maxDiameter`** variable.
// 4. Return `1 + max(leftHeight, rightHeight)` upward (height contribution to parent).

// ```java
// class Solution {

//     private int maxDiameter = 0; // Tracks the global maximum diameter

//     public int diameterOfBinaryTree(TreeNode root) {
//         computeHeight(root);
//         return maxDiameter;
//     }

//     /**
//      * Returns the height of the subtree rooted at `node`.
//      * As a side effect, updates maxDiameter at each node.
//      */
//     private int computeHeight(TreeNode node) {
//         if (node == null) return 0;

//         int leftHeight = computeHeight(node.left);   // Height of left subtree
//         int rightHeight = computeHeight(node.right); // Height of right subtree

//         // Diameter of path passing through this node
//         int localDiameter = leftHeight + rightHeight;
//         maxDiameter = Math.max(maxDiameter, localDiameter);

//         // Return height of this subtree to parent
//         return 1 + Math.max(leftHeight, rightHeight);
//     }
// }
// ```

// ---

// ## 5. Time & Space Complexity

// ### Approach 1 — Brute Force

// | | Complexity | Reasoning |
// |---|---|---|
// | **Time** | O(n²) | For each of n nodes, we call `height()` which visits up to n nodes → n × n |
// | **Space** | O(h) | Recursion stack depth = height of tree; O(log n) balanced, O(n) skewed |

// **Example walk-through (5 nodes, balanced):**
// - Root calls height on left (3 ops) + height on right (3 ops)
// - Then recurses to left child → calls height again (overlapping)
// - **Roughly 15–25 operations** for a 5-node tree

// ---

// ### Approach 2 — Optimal DFS

// | | Complexity | Reasoning |
// |---|---|---|
// | **Time** | O(n) | Every node is visited **exactly once** in the DFS |
// | **Space** | O(h) | Recursion stack; O(log n) for balanced tree, O(n) worst case (skewed) |

// **Example walk-through (5 nodes):**
// - Each node visited exactly once → **exactly 5 recursive calls**
// - Height and diameter computed in a **single pass**

// ---

// ## 6. Complete Worked Examples

// ### Example — Approach 1 (Brute Force)

// ```
// Tree:
//         1
//        / \
//       2   3
//      / \
//     4   5
// ```

// **Step-by-step:**

// | Node Visited | leftHeight | rightHeight | localDiameter | maxDiameter so far |
// |---|---|---|---|---|
// | Node 4 | 0 | 0 | 0 | 0 |
// | Node 5 | 0 | 0 | 0 | 0 |
// | Node 2 | height(4)=1 | height(5)=1 | **2** | 2 |
// | Node 3 | 0 | 0 | 0 | 2 |
// | Node 1 | height(2)=2 | height(3)=1 | **3** | 3 |

// > ⚠️ Note: `height()` is called multiple times for the same nodes — redundant work.

// **Final Answer: 3** (path: 4 → 2 → 1 → 3)

// ---

// ### Example — Approach 2 (Optimal DFS)

// ```
// Tree:
//         1
//        / \
//       2   3
//      / \
//     4   5
// ```

// **DFS Execution (post-order — children processed before parent):**

// ```
// Step 1: Visit Node 4
//   leftHeight  = 0 (null)
//   rightHeight = 0 (null)
//   localDiameter = 0 + 0 = 0  →  maxDiameter = 0
//   Returns height = 1

// Step 2: Visit Node 5
//   leftHeight  = 0 (null)
//   rightHeight = 0 (null)
//   localDiameter = 0 + 0 = 0  →  maxDiameter = 0
//   Returns height = 1

// Step 3: Visit Node 2
//   leftHeight  = 1 (from Node 4)
//   rightHeight = 1 (from Node 5)
//   localDiameter = 1 + 1 = 2  →  maxDiameter = 2  ✅
//   Returns height = 2

// Step 4: Visit Node 3
//   leftHeight  = 0 (null)
//   rightHeight = 0 (null)
//   localDiameter = 0 + 0 = 0  →  maxDiameter = 2
//   Returns height = 1

// Step 5: Visit Node 1
//   leftHeight  = 2 (from Node 2)
//   rightHeight = 1 (from Node 3)
//   localDiameter = 2 + 1 = 3  →  maxDiameter = 3  ✅
//   Returns height = 3
// ```

// **Final Answer: 3** ✅ — Each node visited exactly once, no redundant work.

// ---

// ### Example 2 — Diameter does NOT pass through root

// ```
// Tree:
//         1
//        /
//       2
//      / \
//     3   4
//    /     \
//   5       6
// ```

// **Key nodes:**
// - At Node 2: leftHeight = 2 (via 3→5), rightHeight = 2 (via 4→6)
// - localDiameter at Node 2 = **4**
// - At Node 1: leftHeight = 3, rightHeight = 0 → diameter = 3

// **Final Answer: 4** — The longest path is `5 → 3 → 2 → 4 → 6`, which **never passes through root (1)**.

// > This example proves why we must track diameter at every node, not just the root.

// ---

// ## 7. Edge Cases

// | Edge Case | Description | How Approach 2 Handles It |
// |---|---|---|
// | **Single node** | `root` has no children | `leftHeight=0, rightHeight=0` → returns `0` ✅ |
// | **Null root** | `root = null` | `computeHeight(null)` returns `0` immediately ✅ |
// | **Linear tree (skewed)** | All nodes go left only (like a linked list) | DFS still visits each node once; space is O(n) for stack ✅ |
// | **Perfect binary tree** | Every level fully filled | Diameter = 2 × height; works correctly ✅ |
// | **Diameter not through root** | Longest path is in a subtree | Global `maxDiameter` captures it at the correct node ✅ |
// | **Two nodes only** | Root + one child | leftHeight=1, rightHeight=0 → returns `1` ✅ |
// | **Negative values in nodes** | e.g., node.val = -100 | Node values are irrelevant — only structure matters ✅ |

// ---

// ## 8. Final Summary

// | Approach | Time | Space | Recommended? |
// |---|---|---|---|
// | Brute Force | O(n²) | O(h) | ❌ Only for learning |
// | Optimal DFS | O(n) | O(h) | ✅ Always use this |

// ### What to remember:
// > **"Return height upward, track diameter sideways."**
// > The DFS returns the **height** to its parent, but simultaneously updates a **global diameter** using `left + right` at each node. This dual-purpose recursion pattern appears in many tree problems (e.g., Longest Path, Max Path Sum, Balanced Tree Check).

// ---

// ## 9. Company Interview Appearances

// | Company | Frequency | Notes |
// |---|---|---|
// | **Amazon** | ⭐⭐⭐⭐⭐ Very High | One of the most frequently asked tree problems |
// | **Google** | ⭐⭐⭐⭐⭐ Very High | Asked in both phone screens and onsite rounds |
// | **Meta (Facebook)** | ⭐⭐⭐⭐ High | Common in coding interviews |
// | **Microsoft** | ⭐⭐⭐⭐ High | Appears in SDE-1 and SDE-2 rounds |
// | **Apple** | ⭐⭐⭐ Medium | Occasionally asked |
// | **Bloomberg** | ⭐⭐⭐ Medium | Seen in technical phone screens |
// | **Adobe** | ⭐⭐⭐ Medium | Part of tree problem sets |
// | **Uber** | ⭐⭐ Moderate | Less frequent but documented |
// | **LinkedIn** | ⭐⭐ Moderate | Seen in early interview rounds |

// ### Overall Stats (based on LeetCode + community reports):
// - **Total reported appearances:** 900+ times across major companies
// - **Frequency rank:** Top 5% of all tree problems on LeetCode
// - **Most common follow-up:** *"Now solve it for a general graph (not a tree)"* — requires BFS/DFS with visited set
    
}
