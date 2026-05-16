package Trees;

import java.util.HashMap;

import Datastructures.TreeNode;

public class ConstructBinaryTreeFromPreorderAndInorderTraversal {
    public static void main(String[] args) {
        ConstructBinaryTreeFromPreorderAndInorderTraversal constructBinaryTreeFromPreorderAndInorderTraversal = new ConstructBinaryTreeFromPreorderAndInorderTraversal();
        int[] preOrder = new int[] { 3, 9, 20, 15, 7 };
        int[] inOrder = new int[] { 9, 3, 15, 20, 7 };
        System.out.println("ConstructBinaryTreeFromPreorderAndInorderTraversal : \n"
                + constructBinaryTreeFromPreorderAndInorderTraversal.buildTree(preOrder, inOrder));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/?envType=problem-list-v2&envId=tree
     * 
     * Given two integer arrays preorder and inorder where preorder is the preorder
     * traversal of a binary tree and inorder is the inorder traversal of the same
     * tree, construct and return the binary tree.
     * 
     * 
     * 
     * Example 1:
     * 
     *       3
     *      / \
     *     9   20
     *         / \
     *        15  7
     * 
     * Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
     * Output: [3,9,20,null,null,15,7]
     * Example 2:
     * 
     * Input: preorder = [-1], inorder = [-1]
     * Output: [-1]
     * 
     * 
     * Constraints:
     * 
     * 1 <= preorder.length <= 3000
     * inorder.length == preorder.length
     * -3000 <= preorder[i], inorder[i] <= 3000
     * preorder and inorder consist of unique values.
     * Each value of inorder also appears in preorder.
     * preorder is guaranteed to be the preorder traversal of the tree.
     * inorder is guaranteed to be the inorder traversal of the tree.
     */
    // @formatter:on

    private HashMap<Integer, Integer> inOrderIndexMap;
    private int preOrderIndex;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int inOrderSize = inorder.length;
        preOrderIndex = 0;
        inOrderIndexMap = new HashMap<>();
        for (int i = 0; i < inOrderSize; i++) {
            inOrderIndexMap.put(inorder[i], i);
        }
        return buildSubTree(preorder, 0, inOrderSize - 1);
    }

    public TreeNode buildSubTree(int[] preOrder, int inOrderLeft, int inOrderRight) {
        if (inOrderLeft > inOrderRight)
            return null;
        int rootVal = preOrder[preOrderIndex++];
        TreeNode root = new TreeNode(rootVal);

        int rootInOrderIndex = inOrderIndexMap.get(rootVal);
        root.left = buildSubTree(preOrder, inOrderLeft, rootInOrderIndex - 1);
        root.right = buildSubTree(preOrder, rootInOrderIndex + 1, inOrderRight);
        return root;
    }

}

// @formatter:off
// # Construct Binary Tree from Preorder and Inorder Traversal

// ---

// ## 1. Problem Statement

// ### In Plain Terms:
// You are given two integer arrays:
// - **`preorder[]`** — the result of a **preorder traversal** (Root → Left → Right) of a binary tree
// - **`inorder[]`** — the result of an **inorder traversal** (Left → Root → Right) of the **same** binary tree

// Your task is to **reconstruct and return the original binary tree** (as a `TreeNode` root).

// ### Input Format:
// ```
// preorder = [3, 9, 20, 15, 7]
// inorder  = [9, 3, 15, 20, 7]
// ```

// ### Output Format:
// ```
// Return the root TreeNode of the reconstructed binary tree:
//         3
//        / \
//       9  20
//         /  \
//        15   7
// ```

// ### Constraints:
// | Constraint | Value |
// |---|---|
// | `1 <= preorder.length <= 3000` | Array is non-empty |
// | `preorder.length == inorder.length` | Both arrays same size |
// | `-3000 <= preorder[i], inorder[i] <= 3000` | Values can be negative |
// | All values are **unique** | No duplicates in the tree |
// | `inorder` is guaranteed to be a valid inorder of the same tree | Input is always valid |

// ### What Must Be Returned:
// The `TreeNode` object representing the **root** of the fully reconstructed binary tree.

// ---

// ## 2. Intuition

// ### The Core Insight:

// Think about what each traversal **tells you**:

// ```
// PREORDER  → [ROOT, left subtree nodes..., right subtree nodes...]
//                ↑
//            First element is ALWAYS the root!

// INORDER   → [left subtree nodes..., ROOT, right subtree nodes...]
//                                       ↑
//                     Root splits array into LEFT and RIGHT subtrees!
// ```

// ### How a Human Reasons Through This:

// **Step 1:** Look at `preorder[0]` → that's your root. Always.

// **Step 2:** Find that root value in `inorder[]`. Everything to its **left** is the left subtree. Everything to its **right** is the right subtree.

// **Step 3:** Count the nodes in the left subtree (call it `leftSize`). In `preorder[]`, the next `leftSize` elements after index 0 are the **left subtree's preorder**, and the rest are the **right subtree's preorder**.

// **Step 4:** Repeat this process **recursively** for left and right subtrees.

// ### Visual Intuition:
// ```
// preorder = [3, 9, 20, 15, 7]
// inorder  = [9, 3, 15, 20, 7]

// Step 1: Root = preorder[0] = 3
// Step 2: Find 3 in inorder → index 1
//         Left inorder:  [9]        (size = 1)
//         Right inorder: [15, 20, 7]

// Step 3: Left preorder:  [9]       (next 1 element after root)
//         Right preorder: [20, 15, 7]

// Step 4: Recurse on left  → root=9,  no children
//         Recurse on right → root=20, left=15, right=7
// ```

// ### What Makes It Tricky:
// - Correctly **slicing** the preorder and inorder arrays for each recursive call
// - The **index arithmetic** — off-by-one errors are easy to make
// - Naively searching for the root in inorder is `O(n)` per call → leads to `O(n²)` total. A **HashMap** eliminates this.

// ---

// ## 3. Approach Overview

// | # | Approach | Key Idea | Time | Space | Use When |
// |---|---|---|---|---|---|
// | 1 | **Brute Force** (Array Slicing) | Slice subarrays at each recursive call | O(n²) | O(n²) | Learning / very small input |
// | 2 | **Optimal** (HashMap + Index Pointers) | Precompute inorder indices in HashMap, use index pointers instead of slicing | O(n) | O(n) | **Always — interviews & production** |

// > ✅ **Approach 2 is optimal** — it eliminates redundant linear scans and avoids creating new arrays at every step, giving true O(n) time.

// ---

// ## 4. Detailed Solutions in Java

// ### ✅ Approach 1 — Brute Force (Array Slicing)

// #### Algorithm Step-by-Step:
// 1. Base case: if either array is empty, return `null`
// 2. The first element of `preorder` is the **root value** → create a `TreeNode`
// 3. Find the root's index in `inorder` (linear scan)
// 4. Split `inorder` into left and right halves around that index
// 5. Split `preorder` into left and right halves using `leftSize`
// 6. Recursively build left and right subtrees
// 7. Return root

// ```java
// class Solution {
    
//     // TreeNode definition (given by LeetCode)
//     // public class TreeNode {
//     //     int val;
//     //     TreeNode left, right;
//     //     TreeNode(int val) { this.val = val; }
//     // }

//     public TreeNode buildTree(int[] preorder, int[] inorder) {
//         // Base case: empty arrays mean no subtree
//         if (preorder.length == 0 || inorder.length == 0) return null;

//         // The first element of preorder is always the root
//         int rootVal = preorder[0];
//         TreeNode root = new TreeNode(rootVal);

//         // Find root's position in inorder to split left and right subtrees
//         int rootIndexInInorder = findIndex(inorder, rootVal);

//         // Everything left of rootIndex in inorder = left subtree
//         int[] leftInorder  = Arrays.copyOfRange(inorder, 0, rootIndexInInorder);
//         int[] rightInorder = Arrays.copyOfRange(inorder, rootIndexInInorder + 1, inorder.length);

//         // In preorder: after the root, next leftSize elements = left subtree
//         int leftSize = leftInorder.length;
//         int[] leftPreorder  = Arrays.copyOfRange(preorder, 1, 1 + leftSize);
//         int[] rightPreorder = Arrays.copyOfRange(preorder, 1 + leftSize, preorder.length);

//         // Recursively build subtrees
//         root.left  = buildTree(leftPreorder, leftInorder);
//         root.right = buildTree(rightPreorder, rightInorder);

//         return root;
//     }

//     // Linear scan to find the index of a value in an array
//     private int findIndex(int[] arr, int target) {
//         for (int i = 0; i < arr.length; i++) {
//             if (arr[i] == target) return i;
//         }
//         return -1; // Will never reach here given valid input
//     }
// }
// ```

// ---

// ### ✅ Approach 2 — Optimal (HashMap + Index Pointers)

// #### Algorithm Step-by-Step:
// 1. **Precompute** a `HashMap<value, index>` for `inorder` — O(n) one-time cost
// 2. Use a **global `preorderIndex`** pointer that advances as we pick each root
// 3. For each recursive call, pass `inorderLeft` and `inorderRight` bounds instead of new arrays
// 4. Look up the root's inorder index in **O(1)** using the HashMap
// 5. Recursively build left subtree (inorder window: `[inorderLeft, rootInorderIndex - 1]`)
// 6. Recursively build right subtree (inorder window: `[rootInorderIndex + 1, inorderRight]`)
// 7. Return root

// ```java
// import java.util.HashMap;

// class Solution {

//     // Maps each value to its index in the inorder array for O(1) lookup
//     private HashMap<Integer, Integer> inorderIndexMap;
    
//     // Global pointer tracking current position in preorder array
//     private int preorderIndex;

//     public TreeNode buildTree(int[] preorder, int[] inorder) {
//         preorderIndex = 0;
//         inorderIndexMap = new HashMap<>();

//         // Precompute: value → index for every element in inorder
//         for (int i = 0; i < inorder.length; i++) {
//             inorderIndexMap.put(inorder[i], i);
//         }

//         return buildSubtree(preorder, 0, inorder.length - 1);
//     }

//     /**
//      * Recursively constructs the subtree for the inorder window [left, right].
//      *
//      * @param preorder    The full preorder array
//      * @param inorderLeft  Left boundary of current subtree in inorder array
//      * @param inorderRight Right boundary of current subtree in inorder array
//      */
//     private TreeNode buildSubtree(int[] preorder, int inorderLeft, int inorderRight) {
//         // No elements remain in this window → empty subtree
//         if (inorderLeft > inorderRight) return null;

//         // Pick the current root from preorder (left to right)
//         int rootVal = preorder[preorderIndex++];
//         TreeNode root = new TreeNode(rootVal);

//         // Find root's position in inorder in O(1)
//         int rootInorderIndex = inorderIndexMap.get(rootVal);

//         // IMPORTANT: Build LEFT subtree BEFORE right (preorderIndex advances in order)
//         root.left  = buildSubtree(preorder, inorderLeft, rootInorderIndex - 1);
//         root.right = buildSubtree(preorder, rootInorderIndex + 1, inorderRight);

//         return root;
//     }
// }
// ```

// > 🔑 **Critical Note:** We must build the **left subtree before right** because `preorderIndex` is a global counter that moves forward as roots are consumed — preorder visits left subtree nodes before right subtree nodes.

// ---

// ## 5. Time & Space Complexity

// ### Approach 1 — Brute Force

// | | Complexity | Reasoning |
// |---|---|---|
// | **Time** | **O(n²)** | For each of n nodes, we do a linear scan of inorder (up to n) + array copy (up to n) |
// | **Space** | **O(n²)** | At each level of recursion we create new subarrays; total memory across all calls is O(n²) |

// **Concrete Example:**
// ```
// n = 5 nodes:
// - Root call: scan 5 elements, copy 5 elements
// - Two recursive calls each scan up to 4, etc.
// - Total ≈ 5 + 4 + 3 + 2 + 1 = O(n²) in worst case (skewed tree)
// ```

// ### Approach 2 — Optimal HashMap

// | | Complexity | Reasoning |
// |---|---|---|
// | **Time** | **O(n)** | Each node is processed exactly once; HashMap lookup is O(1); n total nodes = O(n) |
// | **Space** | **O(n)** | HashMap stores n entries; recursion stack is O(h) where h = height ≤ n; overall O(n) |

// **Concrete Example:**
// ```
// n = 3000 nodes (max constraint):
// - Brute force: ~9,000,000 operations (worst case skewed tree)
// - Optimal:     ~3,000 operations (exactly n nodes processed once each)
// ```

// ---

// ## 6. Complete Worked Examples

// ### Example for Both Approaches

// **Input:**
// ```
// preorder = [3, 9, 20, 15, 7]
// inorder  = [9, 3, 15, 20, 7]
// ```

// ---

// #### Approach 1 — Step-by-Step Trace:

// ```
// Call 1: preorder=[3,9,20,15,7], inorder=[9,3,15,20,7]
//   rootVal = 3
//   rootIndexInInorder = 1
//   leftInorder  = [9]
//   rightInorder = [15, 20, 7]
//   leftPreorder  = [9]
//   rightPreorder = [20, 15, 7]
//   → Recurse left, Recurse right

//   Call 2 (LEFT): preorder=[9], inorder=[9]
//     rootVal = 9
//     rootIndexInInorder = 0
//     leftInorder  = []   → null
//     rightInorder = []   → null
//     → Return Node(9), no children ✅

//   Call 3 (RIGHT): preorder=[20,15,7], inorder=[15,20,7]
//     rootVal = 20
//     rootIndexInInorder = 1
//     leftInorder  = [15]
//     rightInorder = [7]
//     leftPreorder  = [15]
//     rightPreorder = [7]

//     Call 4 (LEFT): preorder=[15], inorder=[15]
//       rootVal = 15 → Return Node(15) ✅

//     Call 5 (RIGHT): preorder=[7], inorder=[7]
//       rootVal = 7  → Return Node(7) ✅

//     → Return Node(20).left=15, Node(20).right=7 ✅

// → Final tree:
//         3
//        / \
//       9  20
//         /  \
//        15   7
// ```

// ---

// #### Approach 2 — Step-by-Step Trace (HashMap + Index Pointers):

// **Initial Setup:**
// ```
// inorderIndexMap = {9→0, 3→1, 15→2, 20→3, 7→4}
// preorderIndex = 0
// ```

// | Call | preorderIndex | rootVal | inorderLeft | inorderRight | rootInorderIdx | Action |
// |------|--------------|---------|-------------|--------------|----------------|--------|
// | 1 | 0→1 | 3 | 0 | 4 | 1 | Create Node(3), recurse left[0..0], right[2..4] |
// | 2 | 1→2 | 9 | 0 | 0 | 0 | Create Node(9), left[-1] null, right[1] null |
// | 3 | 2→3 | 20 | 2 | 4 | 3 | Create Node(20), recurse left[2..2], right[4..4] |
// | 4 | 3→4 | 15 | 2 | 2 | 2 | Create Node(15), both children null |
// | 5 | 4→5 | 7 | 4 | 4 | 4 | Create Node(7), both children null |

// **Final Tree:**
// ```
//         3
//        / \
//       9  20
//         /  \
//        15   7
// ```

// ---

// ## 7. Edge Cases

// ### Complete Edge Case Analysis:

// #### Edge Case 1: Single Node
// ```java
// preorder = [1]
// inorder  = [1]
// ```
// - **Approach 1:** `preorder.length == 1`, root = 1, both sub-arrays empty → returns `Node(1)` ✅
// - **Approach 2:** `inorderLeft (0) == inorderRight (0)`, picks root = 1, both recurse with `inorderLeft > inorderRight` → returns `Node(1)` ✅

// #### Edge Case 2: Left-Skewed Tree (Worst Case for Time)
// ```
// preorder = [5, 4, 3, 2, 1]
// inorder  = [1, 2, 3, 4, 5]
// Tree:  5→4→3→2→1 (all left children)
// ```
// - **Approach 1:** Each call creates arrays of size n-1, n-2, ... → O(n²) time + space ⚠️
// - **Approach 2:** HashMap lookup is still O(1) per node → O(n) total ✅

// #### Edge Case 3: Right-Skewed Tree
// ```
// preorder = [1, 2, 3, 4, 5]
// inorder  = [1, 2, 3, 4, 5]
// Tree: 1→2→3→4→5 (all right children)
// ```
// - Both approaches handle correctly; Approach 1 again hits O(n²) ⚠️
// - **Approach 2:** Recursion depth = O(n) — stack overflow risk for n=3000? No, Java default stack handles ~3000 deep calls fine ✅

// #### Edge Case 4: Negative Values
// ```
// preorder = [-10, -3, -1]
// inorder  = [-10, -3, -1]
// ```
// - HashMap keys are integers, handles negatives perfectly ✅
// - `Arrays.copyOfRange` is value-agnostic ✅

// #### Edge Case 5: Two Nodes (Left Child Only)
// ```
// preorder = [1, 2]
// inorder  = [2, 1]
// ```
// - Root = 1, inorder index = 1
// - Left inorder = [2], right = []
// - Returns Node(1).left = Node(2) ✅

// #### Edge Case 6: Two Nodes (Right Child Only)
// ```
// preorder = [1, 2]
// inorder  = [1, 2]
// ```
// - Root = 1, inorder index = 0
// - Left inorder = [], right = [2]
// - Returns Node(1).right = Node(2) ✅

// ### Bug Check Summary:
// | Potential Bug | Approach 1 | Approach 2 |
// |---|---|---|
// | Off-by-one in array slicing | Fixed with `copyOfRange` semantics | Not applicable (uses bounds) |
// | Root not found in inorder | Returns -1 → ArrayIndexOutOfBounds | Never happens (guaranteed valid input) |
// | Left before right recursion | Order doesn't matter (new arrays passed) | **Critical** — must be left then right |
// | Stack overflow on large n | O(n) depth, safe for n≤3000 | O(n) depth, safe for n≤3000 |

// ---

// ## 8. Final Summary

// ### Comparison Table:

// | Criterion | Approach 1 (Brute Force) | Approach 2 (HashMap + Pointers) |
// |---|---|---|
// | Time Complexity | O(n²) | **O(n)** ✅ |
// | Space Complexity | O(n²) | **O(n)** ✅ |
// | Code Simplicity | Simpler to reason about | Slightly more setup |
// | Interview Suitability | Start here to explain idea | **This is what you code** ✅ |
// | Production Ready | No | **Yes** ✅ |

// ### ✅ Recommended Approach:
// **Always use Approach 2** (HashMap + index pointers) in interviews and production. Start by explaining the intuition using Approach 1 verbally, then implement Approach 2.

// ### 🧠 What to Remember:
// > **"Preorder gives you the root; inorder gives you the split."**
// > The key pattern is: **preorder[0] = root → locate in inorder → divide and conquer recursively**. Use a **HashMap for O(1) inorder lookup** and **index pointers instead of array copies** to achieve O(n) time. This divide-and-conquer + HashMap pattern appears in many tree reconstruction problems.

// ---

// ## 9. Company Appearances & Frequency

// ### Companies That Have Asked This Problem:

// | Company | Frequency | Notes |
// |---|---|---|
// | **Amazon** | ⭐⭐⭐⭐⭐ Very High | Top 3 most common tree problem at Amazon |
// | **Microsoft** | ⭐⭐⭐⭐⭐ Very High | Frequently in SDE-II rounds |
// | **Google** | ⭐⭐⭐⭐ High | L4/L5 interviews |
// | **Meta (Facebook)** | ⭐⭐⭐⭐ High | Production engineering & SWE rounds |
// | **Adobe** | ⭐⭐⭐ Medium | |
// | **Bloomberg** | ⭐⭐⭐ Medium | |
// | **Apple** | ⭐⭐⭐ Medium | |
// | **Oracle** | ⭐⭐ Low-Medium | |
// | **Uber** | ⭐⭐ Low-Medium | |
// | **LinkedIn** | ⭐⭐ Low-Medium | |

// ### LeetCode Stats (Problem #105):
// - **Difficulty:** Medium
// - **Acceptance Rate:** ~63%
// - **Tagged as:** Array, Hash Table, Divide and Conquer, Tree, Binary Tree
// - **Appearance frequency:** One of the **top 50 most asked LeetCode problems** in FAANG/MAANG interviews
// - **Total interview reports:** 500+ verified company reports on LeetCode Discuss

// > 💡 **Pro Tip:** This problem is almost always paired with its sibling — **[LC #106: Construct Binary Tree from Inorder and Postorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/)** — in interviews. Master both together, as the pattern is identical with minor index changes.
// @formatter:on
