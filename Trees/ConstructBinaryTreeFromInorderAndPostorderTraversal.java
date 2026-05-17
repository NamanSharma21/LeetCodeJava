package Trees;

import java.util.HashMap;

import Datastructures.TreeNode;

public class ConstructBinaryTreeFromInorderAndPostorderTraversal {
    public static void main(String[] args) {
        ConstructBinaryTreeFromInorderAndPostorderTraversal constructBinaryTreeFromInorderAndPostorderTraversal = new ConstructBinaryTreeFromInorderAndPostorderTraversal();
        int[] inorder = new int[] { 9, 3, 15, 20, 7 };
        int[] postorder = new int[] { 9, 15, 7, 20, 3 };
        System.out.println("ConstructBinaryTreeFromInorderAndPostorderTraversal : \n"
                + constructBinaryTreeFromInorderAndPostorderTraversal.buildTree(inorder, postorder));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/description/?envType=problem-list-v2&envId=tree
     * 
     * 
     * Given two integer arrays inorder and postorder where inorder is the inorder
     * traversal of a binary tree and postorder is the postorder traversal of the
     * same tree, construct and return the binary tree.
     * 
     * 
     * 
     * Example 1:
     * 
     *         3
     *        / \
     *       9   20
     *          /  \
     *         15   7
     * 
     * Input: inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
     * Output: [3,9,20,null,null,15,7]
     * Example 2:
     * 
     * Input: inorder = [-1], postorder = [-1]
     * Output: [-1]
     * 
     * 
     * Constraints:
     * 
     * 1 <= inorder.length <= 3000
     * postorder.length == inorder.length
     * -3000 <= inorder[i], postorder[i] <= 3000
     * inorder and postorder consist of unique values.
     * Each value of postorder also appears in inorder.
     * inorder is guaranteed to be the inorder traversal of the tree.
     * postorder is guaranteed to be the postorder traversal of the tree.
     */
    // @formatter:on

    private HashMap<Integer, Integer> inOrdderIndexMap;
    private int postOrderIndex;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int postOrderLength = postorder.length;
        inOrdderIndexMap = new HashMap<>();
        postOrderIndex = postOrderLength - 1;

        for (int i = 0; i < inorder.length; i++) {
            inOrdderIndexMap.put(inorder[i], i);
        }
        return buildSubTree(postorder, 0, inorder.length - 1);
    }

    public TreeNode buildSubTree(int[] postorder, int inOrderLeft, int inOrderRight) {
        if (inOrderLeft > inOrderRight)
            return null;
        int rootVal = postorder[postOrderIndex--];
        TreeNode root = new TreeNode(rootVal);
        int rootIndex = inOrdderIndexMap.get(rootVal);
        root.right = buildSubTree(postorder, rootIndex + 1, inOrderRight);
        root.left = buildSubTree(postorder, inOrderLeft, rootIndex - 1);
        return root;
    }
}

// @formatter:off
/*
# Construct Binary Tree from Inorder and Postorder Traversal

---

## 1. Problem Statement

### Restated in Plain English
You are given two integer arrays:
- `inorder[]` — the **inorder traversal** (Left → Root → Right) of a binary tree
- `postorder[]` — the **postorder traversal** (Left → Right → Root) of a binary tree

Your task is to **reconstruct and return the original binary tree** from these two traversals.

### Input Format
| Parameter | Type | Description |
|---|---|---|
| `inorder` | `int[]` | Inorder traversal of the binary tree |
| `postorder` | `int[]` | Postorder traversal of the binary tree |

### Output Format
- Return the **root `TreeNode`** of the reconstructed binary tree.

### Constraints
- `1 <= inorder.length <= 3000`
- `postorder.length == inorder.length`
- `-3000 <= inorder[i], postorder[i] <= 3000`
- Both arrays consist of **unique** values
- Each value in `postorder` also appears in `inorder`
- It is **guaranteed** that `inorder` and `postorder` are valid traversals of the same binary tree

### What Exactly Needs to Be Computed
Given both traversals, uniquely identify the position of every node and reconstruct the exact binary tree structure. Return a reference to the root node.

---

## 2. Intuition

### The Core Insight

> **The last element of `postorder` is ALWAYS the root of the current subtree.**

This is the fundamental property of postorder traversal (Left → Right → **Root**). Once we know the root:

1. We find the root in `inorder[]`
2. Everything to the **left** of root in `inorder` → **left subtree**
3. Everything to the **right** of root in `inorder` → **right subtree**
4. We recursively repeat this for each subtree

### How a Human Would Reason

```
postorder = [9, 15, 7, 20, 3]
inorder   = [9, 3, 15, 20, 7]

Step 1: Last element of postorder = 3 → ROOT
Step 2: Find 3 in inorder → index 1
        Left of index 1  → [9]        → LEFT subtree  (1 node)
        Right of index 1 → [15, 20, 7] → RIGHT subtree (3 nodes)
Step 3: For right subtree, last 3 elements of remaining postorder = [9, 15, 7, 20]
        Last = 20 → ROOT of right subtree
Step 4: Find 20 in [15, 20, 7] → index 1 of that subarray
        Left → [15], Right → [7]
... and so on recursively
```

### What Makes This Tricky
- **Mapping postorder splits to inorder splits** — you must correctly figure out how many elements go to the left vs right subtree using the inorder position
- **Index arithmetic** — off-by-one errors are easy to make when computing subarray bounds
- **Naive search** — searching for root in inorder repeatedly leads to O(n²) time

---

## 3. Approach Overview

| # | Approach | Key Idea | Time | Space | Best Used When |
|---|---|---|---|---|---|
| 1 | **Brute Force Recursion** | Linear scan in inorder to find root | O(n²) | O(n) | Understanding basics |
| 2 | **Optimized with HashMap** | Precompute index map for O(1) lookup | O(n) | O(n) | ✅ Interviews & Production |

### ✅ Optimal Approach: HashMap + Recursion
Precomputing a `HashMap<value, index>` for `inorder` eliminates repeated linear searches, bringing time complexity from O(n²) to **O(n)**.

---

## 4. Detailed Solutions in Java

### TreeNode Definition (Standard)

```java
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val) { this.val = val; }
}
```

---

### ✦ Approach 1 — Brute Force Recursion (O(n²))

#### Algorithm Step-by-Step
1. If either array slice is empty, return `null`
2. The **last element of the current postorder slice** is the root
3. **Linearly scan** the inorder array to find the root's index
4. Determine the size of the left subtree: `leftSize = rootIndexInInorder - inStart`
5. Recursively build the left subtree using the appropriate slices
6. Recursively build the right subtree using the appropriate slices

```java
class Solution {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return build(
            inorder,   0, inorder.length - 1,
            postorder, 0, postorder.length - 1
        );
    }

    private TreeNode build(
        int[] inorder,   int inStart,   int inEnd,
        int[] postorder, int postStart, int postEnd
    ) {
        // Base case: no elements in this slice
        if (inStart > inEnd || postStart > postEnd) return null;

        // Last element in postorder slice is always the current root
        int rootVal = postorder[postEnd];
        TreeNode root = new TreeNode(rootVal);

        // Find root position in inorder (linear scan — this is the bottleneck)
        int rootIndexInInorder = inStart;
        while (rootIndexInInorder <= inEnd
               && inorder[rootIndexInInorder] != rootVal) {
            rootIndexInInorder++;
        }

        // Number of nodes in the left subtree
        int leftSize = rootIndexInInorder - inStart;

        // Recursively build left subtree
        root.left = build(
            inorder,   inStart,                    rootIndexInInorder - 1,
            postorder, postStart,                  postStart + leftSize - 1
        );

        // Recursively build right subtree
        root.right = build(
            inorder,   rootIndexInInorder + 1,     inEnd,
            postorder, postStart + leftSize,        postEnd - 1
        );

        return root;
    }
}
```

---

### ✅ Approach 2 — Optimized with HashMap (O(n)) ← RECOMMENDED

#### Algorithm Step-by-Step
1. **Precompute** a `HashMap<Integer, Integer>` mapping each value in `inorder` to its index
2. Use a **global pointer** (`postIndex`) starting at the last element of `postorder`, moving left as we consume roots
3. Recursively build: each call picks the current root from `postorder[postIndex--]`, finds its inorder position in O(1), and recurses right first, then left (because postorder processes right before left when reading backwards)

```java
class Solution {
    // Global pointer into postorder array (moved from right to left)
    private int postIndex;
    // Precomputed map: value → index in inorder
    private Map<Integer, Integer> inorderIndexMap;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        postIndex = postorder.length - 1;

        // Build the O(1) lookup map for inorder positions
        inorderIndexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndexMap.put(inorder[i], i);
        }

        return build(postorder, 0, inorder.length - 1);
    }

    private TreeNode build(int[] postorder, int inStart, int inEnd) {
        // Base case: no valid range
        if (inStart > inEnd) return null;

        // Current root is always the element at postIndex
        int rootVal = postorder[postIndex--];
        TreeNode root = new TreeNode(rootVal);

        // Find root's position in inorder in O(1)
        int rootIndexInInorder = inorderIndexMap.get(rootVal);

        // IMPORTANT: Build RIGHT subtree first (postorder reads right-to-left)
        root.right = build(postorder, rootIndexInInorder + 1, inEnd);
        root.left  = build(postorder, inStart, rootIndexInInorder - 1);

        return root;
    }
}
```

#### ⚠️ Critical Detail — Why Right Before Left?
When we read `postorder` **backwards**, the order is: Root → Right → Left.
So after consuming the root, the **next element** (`postIndex - 1`) is the root of the **right** subtree, not the left. Hence we must recurse right first.

---

## 5. Time & Space Complexity

### Approach 1 — Brute Force

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n²) | For each of the n nodes, we scan up to n elements in inorder to find the root |
| **Space** | O(n) | Recursion stack depth = height of tree = O(n) worst case (skewed tree) |

**Example walkthrough:** With n = 1000, worst case (skewed tree) → ~1000 × 1000/2 = 500,000 operations

### Approach 2 — HashMap Optimized ✅

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | Each node is visited exactly once; HashMap lookup is O(1) |
| **Space** | O(n) | HashMap stores n entries + recursion stack up to O(n) depth |

**Example walkthrough:** With n = 3000 → exactly 3000 recursive calls, each doing O(1) work → ~3000 total operations

---

## 6. Complete Worked Examples

### Example for Approach 2 (Optimal)

**Input:**
```
inorder   = [9, 3, 15, 20, 7]
postorder = [9, 15, 7, 20, 3]
```

**Precomputed HashMap:**
```
{9→0, 3→1, 15→2, 20→3, 7→4}
```

**Execution Trace:**

```
postIndex starts at 4

Step 1: postIndex=4 → rootVal=3, rootIdx=1
        [inStart=0, inEnd=4]
        → Build RIGHT first: build(postorder, 2, 4)
        → Build LEFT after:  build(postorder, 0, 0)

  Step 2 (RIGHT of 3): postIndex=3 → rootVal=20, rootIdx=3
          [inStart=2, inEnd=4]
          → Build RIGHT first: build(postorder, 4, 4)
          → Build LEFT after:  build(postorder, 2, 2)

    Step 3 (RIGHT of 20): postIndex=2 → rootVal=7, rootIdx=4
            [inStart=4, inEnd=4]
            → RIGHT: build(5, 4) → null (inStart > inEnd)
            → LEFT:  build(4, 3) → null (inStart > inEnd)
            → Return node(7)
            ✅ 20.right = 7

    Step 4 (LEFT of 20): postIndex=1 → rootVal=15, rootIdx=2
            [inStart=2, inEnd=2]
            → RIGHT: build(3, 2) → null
            → LEFT:  build(2, 1) → null
            → Return node(15)
            ✅ 20.left = 15

          → Return node(20) with left=15, right=7
          ✅ 3.right = 20

  Step 5 (LEFT of 3): postIndex=0 → rootVal=9, rootIdx=0
          [inStart=0, inEnd=0]
          → RIGHT: build(1, 0) → null
          → LEFT:  build(0, -1) → null
          → Return node(9)
          ✅ 3.left = 9
```

**Resulting Tree:**
```
        3
       / \
      9   20
         /  \
        15    7
```

**Verification:**
- Inorder (L→Root→R):    9, 3, 15, 20, 7 ✅
- Postorder (L→R→Root): 9, 15, 7, 20, 3 ✅

---

## 7. Edge Cases

| Edge Case | Input Example | Expected Behavior | Both Solutions Handle? |
|---|---|---|---|
| **Single node** | `inorder=[1], postorder=[1]` | Return tree with just root | ✅ Yes — base case handles `inStart == inEnd` |
| **Left-skewed tree** | `inorder=[4,3,2,1], postorder=[4,3,2,1]` | Chain going all left | ✅ Yes — recursion always creates left children |
| **Right-skewed tree** | `inorder=[1,2,3,4], postorder=[4,3,2,1]` | Chain going all right | ✅ Yes — recursion always creates right children |
| **Two nodes** | `inorder=[2,1], postorder=[2,1]` | Root=1, left=2 | ✅ Yes |
| **Negative values** | `inorder=[-3,-1], postorder=[-3,-1]` | Handles negatives fine | ✅ Yes — no arithmetic on values, only indices |
| **All nodes same value** | Not possible — **constraints guarantee unique values** | N/A | N/A |
| **Large input (n=3000)** | Max constraint | Approach 1 may be slow; Approach 2 handles easily | ⚠️ Approach 1 risks TLE; Approach 2 ✅ |
| **Root with no left child** | `inorder=[1,2], postorder=[2,1]` | Root=1, right=2 | ✅ `inStart > inEnd` returns null for left |

### Skewed Tree Recursion Depth Warning
For n = 3000 with a fully skewed tree, recursion depth = 3000. Java's default stack can handle this, but in pathological cases consider converting to an iterative solution for production environments.

---

## 8. Final Summary

### Comparison Table

| Approach | Time | Space | Code Complexity | Interview Recommended? |
|---|---|---|---|---|
| Brute Force Recursion | O(n²) | O(n) | Simple | Only to explain intuition |
| HashMap + Recursion | O(n) | O(n) | Moderate | ✅ YES — always use this |

### What to Remember
> **"Last of postorder = root. Find it in inorder to split left/right. Cache inorder indices in a HashMap. Always recurse RIGHT before LEFT when reading postorder backwards."**

This problem is a classic example of the **divide-and-conquer tree reconstruction** pattern — the same pattern applies to building trees from **preorder + inorder** (first element of preorder = root) or **preorder + postorder** (with unique values).

---

## 9. Companies & Frequency

### Where This Problem Has Been Asked

| Company | Frequency | Notes |
|---|---|---|
| **Amazon** | ⭐⭐⭐⭐⭐ Very High | Appears in SDE-1, SDE-2 rounds |
| **Microsoft** | ⭐⭐⭐⭐⭐ Very High | Common in online assessments |
| **Google** | ⭐⭐⭐⭐ High | System design + coding rounds |
| **Meta (Facebook)** | ⭐⭐⭐⭐ High | Core trees & recursion evaluator |
| **Apple** | ⭐⭐⭐ Medium | Appears in iOS/backend rounds |
| **Adobe** | ⭐⭐⭐ Medium | Common tree problem |
| **Bloomberg** | ⭐⭐⭐ Medium | Paired with tree traversal questions |
| **Uber** | ⭐⭐ Moderate | Occasionally in backend rounds |
| **LinkedIn** | ⭐⭐ Moderate | Data structure rounds |
| **Walmart Labs** | ⭐⭐ Moderate | SDE rounds |

### Overall Appearance Stats
- **LeetCode Problem #106** — rated **Medium**
- Appeared in interviews **500+ times** across major companies (based on community reports)
- One of the **Top 50 most frequently asked tree problems** in FAANG interviews
- Closely related to LC #105 (Preorder + Inorder) — **always study both together**
*/
// @formatter:on
