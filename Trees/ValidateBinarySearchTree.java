package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import Datastructures.TreeNode;

public class ValidateBinarySearchTree {
    public static void main(String[] args) {
        ValidateBinarySearchTree validateBinarySearchTree = new ValidateBinarySearchTree();
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        System.out.println("Is Valid BST : " + validateBinarySearchTree.isValidBSTInOrderIterative(root));

        TreeNode root1 = new TreeNode(5);
        root1.left = new TreeNode(4);
        root1.right = new TreeNode(6);
        root1.right.left = new TreeNode(3);
        root1.right.right = new TreeNode(7);
        System.out.println("Is Valid BST : " + validateBinarySearchTree.isValidBSTInOrderIterative(root1));
    }

    /*
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/94/
     * trees/625/
     * Given the root of a binary tree, determine if it is a valid binary search
     * tree (BST).
     * 
     * A valid BST is defined as follows:
     * 
     * The left subtree of a node contains only nodes with keys strictly less than
     * the node's key.
     * The right subtree of a node contains only nodes with keys strictly greater
     * than the node's key.
     * Both the left and right subtrees must also be binary search trees.
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: root = [2,1,3]
     * Output: true
     * Example 2:
     * 
     * 
     * Input: root = [5,1,4,null,null,3,6]
     * Output: false
     * Explanation: The root node's value is 5 but its right child's value is 4.
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [1, 104].
     * -231 <= Node.val <= 231 - 1
     */
    Integer prev = 0;

    public boolean isValidBST(TreeNode root) {
        if (root == null)
            return true;
        if (!isValidBST(root.left))
            return false;
        if (prev != null && prev >= root.val)
            return false;
        prev = root.val;
        return isValidBST(root.right);
    }

    public boolean isValidBSTInOrderToList(TreeNode root) {
        List<Integer> values = new ArrayList<>();
        collectInOrder(root, values);
        return isStriclyIncreasing(values);
    }

    public void collectInOrder(TreeNode root, List<Integer> values) {
        if (root == null)
            return;
        collectInOrder(root.left, values);
        values.add(root.val);
        collectInOrder(root.right, values);
    }

    public boolean isStriclyIncreasing(List<Integer> values) {
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) <= values.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean isValidBSTReccursiveInOrder(TreeNode root) {
        return isValidBSTWithBounds(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBSTWithBounds(TreeNode root, long minBound, long maxBound) {
        if (root == null)
            return true;
        if (root.val <= minBound || root.val >= maxBound)
            return false;
        boolean isLeftValid = isValidBSTWithBounds(root.left, minBound, root.val);
        boolean isRightValid = isValidBSTWithBounds(root.right, root.val, maxBound);
        return isLeftValid && isRightValid;
    }

    public boolean isValidBSTInOrderIterative(TreeNode root) {
        Deque<TreeNode> q = new ArrayDeque<>();
        TreeNode current = root;
        long prevVal = Long.MIN_VALUE;
        while (current != null || !q.isEmpty()) {
            while (current != null) {
                q.push(current);
                current = current.left;
            }
            current = q.pop();

            if (current.val <= prevVal) {
                return false;
            }

            prevVal = current.val;
            current = current.right;
        }

        return true;
    }



    /*
    
    # Validate Binary Search Tree (LeetCode 98)

---

## 1. Problem Statement

### In Plain English
Given the root of a binary tree, determine whether it is a **valid Binary Search Tree (BST)**.

### BST Rules
- Every node's value must be **strictly greater** than all values in its **left subtree**
- Every node's value must be **strictly less** than all values in its **right subtree**
- Both left and right subtrees must also be valid BSTs
- **No duplicates** are allowed (strict inequalities)

### Input / Output
```
Input:  Root node of a binary tree (TreeNode)
Output: boolean — true if valid BST, false otherwise
```

### TreeNode Definition (given)
```java
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val) { this.val = val; }
}
```

### Constraints
- Number of nodes: `[1, 10^4]`
- Node values: `-2^31 <= node.val <= 2^31 - 1` *(Integer.MIN_VALUE to Integer.MAX_VALUE — overflow risk!)*

---

## 2. Intuition

### The Core Insight
The most common beginner mistake is checking only that:
> *"left child < parent < right child"*

**This is WRONG.** Consider:

```
        5
       / \
      1   4
         / \
        3   6
```
Each node locally satisfies parent rules, but **3 < 5**, so this is NOT a valid BST. Node `3` is in the right subtree of `5` but is less than `5`.

### The Real Rule
Every node carries an **inherited valid range** `(min, max)`. As you go:
- **Left** → the current node's value becomes the new **upper bound**
- **Right** → the current node's value becomes the new **lower bound**

A node is valid only if `min < node.val < max`.

### Why This Is Tricky
- Local comparisons aren't enough — you need **global context**
- Integer bounds can equal `Integer.MIN_VALUE` or `Integer.MAX_VALUE`, causing **overflow** if you use `int` for bounds
- The constraint is **strict** (no equal values allowed)

---

## 3. Approach Overview

| # | Approach | Key Idea | Time | Space | Use When |
|---|----------|----------|------|-------|----------|
| 1 | **Brute Force** — Inorder to List | Collect inorder traversal → check if strictly increasing | O(n) | O(n) | Simple to reason about |
| 2 | **Better** — Recursive with Bounds | Pass `(min, max)` bounds down the recursion | O(n) | O(h) | Interviews — clean & elegant |
| 3 | **Optimal** — Iterative Inorder | Morris or stack-based inorder, track previous node | O(n) | O(h) | Space-conscious production code |

> ✅ **Recommended**: **Approach 2 (Recursive with Bounds)** — most readable, interview-friendly, and handles all edge cases cleanly. Approach 3 is equally optimal but slightly harder to read.

---

## 4. Detailed Solutions in Java

---

### ✅ Approach 1 — Brute Force: Inorder Traversal to List

#### Algorithm
1. Perform an **inorder traversal** (left → root → right) and collect all values into a list
2. A BST's inorder traversal produces values in **strictly increasing order**
3. Check if the list is strictly increasing — if yes, it's a valid BST

#### Why It Works
Inorder of a BST always yields sorted order. If any element is ≥ its successor, BST property is violated.

```java
import java.util.ArrayList;
import java.util.List;

class Solution {

    public boolean isValidBST(TreeNode root) {
        List<Integer> inorderValues = new ArrayList<>();
        collectInorder(root, inorderValues);
        return isStrictlyIncreasing(inorderValues);
    }

    // Step 1: Collect all values via inorder traversal
    private void collectInorder(TreeNode node, List<Integer> values) {
        if (node == null) return;
        collectInorder(node.left, values);
        values.add(node.val);
        collectInorder(node.right, values);
    }

    // Step 2: Verify list is strictly increasing
    private boolean isStrictlyIncreasing(List<Integer> values) {
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) <= values.get(i - 1)) {
                return false; // duplicate or wrong order found
            }
        }
        return true;
    }
}
```

> ⚠️ **Overflow Risk**: If node values include `Integer.MIN_VALUE` or `Integer.MAX_VALUE`, storing as `int` is fine here since we're just comparing adjacent elements — no arithmetic.

---

### ✅ Approach 2 — Recursive with Min/Max Bounds *(Recommended)*

#### Algorithm
1. Start with bounds `(-∞, +∞)` at the root
2. At each node, check: `min < node.val < max`
3. Recurse left with updated upper bound: `(min, node.val)`
4. Recurse right with updated lower bound: `(node.val, max)`
5. Use `Long` instead of `int` to safely represent `-∞` and `+∞` without overflow

#### Why `Long`?
If a node's value is `Integer.MIN_VALUE`, we can't pass `Integer.MIN_VALUE - 1` as `int` — it overflows. Using `Long.MIN_VALUE` and `Long.MAX_VALUE` as sentinels avoids this entirely.

```java
class Solution {

    public boolean isValidBST(TreeNode root) {
        // Start with the widest possible valid range
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean validate(TreeNode node, long minBound, long maxBound) {
        // Base case: null nodes are always valid
        if (node == null) return true;

        // Current node must be strictly within (minBound, maxBound)
        if (node.val <= minBound || node.val >= maxBound) {
            return false;
        }

        // Left subtree: all values must be < node.val (tighten upper bound)
        boolean leftValid = validate(node.left, minBound, node.val);

        // Right subtree: all values must be > node.val (tighten lower bound)
        boolean rightValid = validate(node.right, node.val, maxBound);

        return leftValid && rightValid;
    }
}
```

---

### ✅ Approach 3 — Iterative Inorder with Previous Node Tracking

#### Algorithm
1. Simulate inorder traversal using an explicit **stack**
2. Track the **previously visited node's value** (the last value added in inorder)
3. At each node visited, check: `current.val > previousValue`
4. If this ever fails → return false

#### Why This Is Space-Efficient
No extra list is needed. We compare on-the-fly with just one `prev` variable.

```java
import java.util.Deque;
import java.util.ArrayDeque;

class Solution {

    public boolean isValidBST(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        long previousValue = Long.MIN_VALUE; // tracks last seen inorder value

        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {

            // Reach the leftmost node
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            // Process the node
            current = stack.pop();

            // Inorder value must be strictly greater than previous
            if (current.val <= previousValue) {
                return false;
            }

            previousValue = current.val; // update previous
            current = current.right;     // move to right subtree
        }

        return true;
    }
}
```

---

## 5. Time & Space Complexity

### Approach 1 — Inorder to List

| | Complexity | Reasoning |
|--|-----------|-----------|
| **Time** | O(n) | Visit every node once during traversal + one pass over the list |
| **Space** | O(n) | List stores all `n` node values + O(h) call stack |

**Example**: Tree with 1000 nodes → 1000 list insertions + 999 comparisons ≈ 2000 operations.

---

### Approach 2 — Recursive Bounds

| | Complexity | Reasoning |
|--|-----------|-----------|
| **Time** | O(n) | Each node is visited exactly once |
| **Space** | O(h) | Recursion stack depth = tree height. O(log n) balanced, O(n) worst (skewed) |

**Example**: Balanced tree with 1000 nodes → height ≈ 10 → only ~10 stack frames at any time.

---

### Approach 3 — Iterative Inorder

| | Complexity | Reasoning |
|--|-----------|-----------|
| **Time** | O(n) | Each node pushed/popped from stack once |
| **Space** | O(h) | Stack holds at most one path root→leaf = O(h) nodes |

**Example**: Skewed tree (like a linked list) with 1000 nodes → stack grows to 1000 at worst.

---

## 6. Complete Worked Examples

---

### Example 1 — Valid BST (All Approaches)

```
Input Tree:
        5
       / \
      3   7
     / \
    2   4
```

---

#### Approach 1 — Inorder to List

| Step | Action | List State |
|------|--------|------------|
| Visit 2 | Add 2 | [2] |
| Visit 3 | Add 3 | [2, 3] |
| Visit 4 | Add 4 | [2, 3, 4] |
| Visit 5 | Add 5 | [2, 3, 4, 5] |
| Visit 7 | Add 7 | [2, 3, 4, 5, 7] |
| Check | 2<3<4<5<7 ✅ | **return true** |

---

#### Approach 2 — Recursive Bounds

| Node | minBound | maxBound | Valid? |
|------|----------|----------|--------|
| 5 | -∞ | +∞ | -∞ < 5 < +∞ ✅ |
| 3 | -∞ | **5** | -∞ < 3 < 5 ✅ |
| 2 | -∞ | **3** | -∞ < 2 < 3 ✅ |
| 4 | **3** | **5** | 3 < 4 < 5 ✅ |
| 7 | **5** | +∞ | 5 < 7 < +∞ ✅ |

All nodes valid → **return true**

---

#### Approach 3 — Iterative Inorder

| Step | Stack | Current | prev | Check |
|------|-------|---------|------|-------|
| Init | [] | 5 | -∞ | — |
| Push left chain | [5,3,2] | null | -∞ | — |
| Pop 2 | [5,3] | 2 | -∞ | 2 > -∞ ✅, prev=2 |
| Move right (null) | [5,3] | null | 2 | — |
| Pop 3 | [5] | 3 | 2 | 3 > 2 ✅, prev=3 |
| Push right 4 | [5,4] | null | 3 | — |
| Pop 4 | [5] | 4 | 3 | 4 > 3 ✅, prev=4 |
| Pop 5 | [] | 5 | 4 | 5 > 4 ✅, prev=5 |
| Push right 7 | [7] | null | 5 | — |
| Pop 7 | [] | 7 | 5 | 7 > 5 ✅, prev=7 |

**return true**

---

### Example 2 — Invalid BST (The Classic Trap)

```
Input Tree:
        5
       / \
      1   4
         / \
        3   6
```

#### Approach 2 — Recursive Bounds (most illustrative)

| Node | minBound | maxBound | Valid? |
|------|----------|----------|--------|
| 5 | -∞ | +∞ | ✅ |
| 1 | -∞ | 5 | ✅ |
| 4 | **5** | +∞ | 4 > 5? ❌ **FAIL here!** |

**return false** immediately when visiting node `4`

> The bound `minBound = 5` was inherited from being in the right subtree of 5. Node 4 violates it.

---

### Example 3 — Overflow Edge Case

```
Input Tree:
    [Integer.MIN_VALUE]
           \
    [Integer.MAX_VALUE]
```

#### Approach 2 — Recursive Bounds

| Node | minBound | maxBound | Valid? |
|------|----------|----------|--------|
| MIN_VALUE | Long.MIN_VALUE | Long.MAX_VALUE | Long.MIN_VALUE < -2147483648 ✅ |
| MAX_VALUE | **-2147483648L** | Long.MAX_VALUE | -2147483648L < 2147483647 ✅ |

**return true** — using `Long` prevents any overflow!

---

## 7. Edge Cases

| Edge Case | Description | How Each Approach Handles It |
|-----------|-------------|------------------------------|
| **Single node** | Tree with just root | All approaches return true — no subtrees to violate anything |
| **All left / all right** | Skewed tree (like a linked list) | All work correctly; Approach 2 uses O(n) stack space in worst case |
| **Duplicate values** | Node value equals parent | Bounds use strict `<` and `>`, so duplicates → false ✅ |
| **Integer.MIN_VALUE node** | Node value = -2147483648 | Approach 2 & 3 use `Long` bounds → safe. Approach 1 safe (no arithmetic) |
| **Integer.MAX_VALUE node** | Node value = 2147483647 | Same as above — `Long` sentinel handles it |
| **Negative values** | Tree with negative node values | All approaches handle naturally — no unsigned assumptions made |
| **Large tree (10^4 nodes)** | Max constraint | All O(n) solutions handle comfortably |
| **Root is null** | Empty tree (not in constraints, but defensive) | All: null check at start → return true |

### ⚠️ Critical Warning — Integer Overflow
If you use `int` bounds in Approach 2 and initialize them as `Integer.MIN_VALUE - 1`, you get **silent overflow to `Integer.MAX_VALUE`**. Always use `Long` for bounds.

---

## 8. Final Summary

### Approach Comparison

| Approach | Code Simplicity | Space Efficiency | Interview Friendliness |
|----------|----------------|-----------------|------------------------|
| Inorder to List | ⭐⭐⭐⭐ Very simple | ⭐⭐ Uses O(n) extra | ⭐⭐⭐ Good starting point |
| Recursive Bounds | ⭐⭐⭐⭐⭐ Clean & readable | ⭐⭐⭐⭐ O(h) | ⭐⭐⭐⭐⭐ Best for interviews |
| Iterative Inorder | ⭐⭐⭐ Moderate | ⭐⭐⭐⭐⭐ O(h), no list | ⭐⭐⭐⭐ Good follow-up |

### 🏆 Recommended: Approach 2 (Recursive with Bounds)
Use this in interviews. It's clean, directly encodes the BST definition, handles overflow safely with `Long`, and is easy to explain.

### What To Remember
> **"Each node in a BST isn't just bounded by its parent — it carries inherited constraints from all its ancestors. Model this with a `(min, max)` range passed down the recursion, and use `Long` to avoid overflow at integer boundaries."**

This **"pass bounds down recursion"** pattern appears in many tree validation problems — master it here and it transfers directly to problems like validating AVL trees, checking BST range queries, and more.
    
    */

}
