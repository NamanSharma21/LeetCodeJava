package Trees;

import Datastructures.TreeNode;

public class InsertIntoABinarySearchTree {
    public static void main(String[] args) {
        InsertIntoABinarySearchTree insertIntoABinarySearchTree = new InsertIntoABinarySearchTree();
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        System.out.println(
                "InsertIntoABinarySearchTree : \n" + insertIntoABinarySearchTree.insertIntoBSTReccursive(root, 5));

        System.out.println(
                "InsertIntoABinarySearchTree : \n" + insertIntoABinarySearchTree.insertIntoBSTIterative(root, 5));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/insert-into-a-binary-search-tree/description/?envType=problem-list-v2&envId=tree
     * 
     * You are given the root node of a binary search tree (BST) and a value to
     * insert into the tree. Return the root node of the BST after the insertion. It
     * is guaranteed that the new value does not exist in the original BST.
     * 
     * Notice that there may exist multiple valid ways for the insertion, as long as
     * the tree remains a BST after insertion. You can return any of them.
     * 
     * 
     * 
     * Example 1:
     * 
     *     4
     *    / \
     *   2   7
     *  / \ 
     * 1  3 
     * 
     * Input: root = [4,2,7,1,3], val = 5
     * Output: [4,2,7,1,3,5]
     * Explanation: Another accepted tree is:
     * 
     * Example 2:
     * 
     * 
     *      40
     *     /  \
     *   20    60
     *  / \    / \
     * 10 30  50  70
     *    /
     *   25
     * 
     * Input: root = [40,20,60,10,30,50,70], val = 25
     * Output: [40,20,60,10,30,50,70,null,null,25]
     * Example 3:
     * 
     * Input: root = [4,2,7,1,3,null,null,null,null,null,null], val = 5
     * Output: [4,2,7,1,3,5]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree will be in the range [0, 104].
     * -108 <= Node.val <= 108
     * All the values Node.val are unique.
     * -108 <= val <= 108
     * It's guaranteed that val does not exist in the original BST.
     * 
     * 
     */
    // @formatter:on

    public TreeNode insertIntoBSTReccursive(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);
        System.out.println("" + root.val);
        if (val < root.val)
            root.left = insertIntoBSTReccursive(root.left, val);
        if (val > root.val)
            root.right = insertIntoBSTReccursive(root.right, val);
        return root;
    }

    public TreeNode insertIntoBSTIterative(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);
        TreeNode current = root;
        while (true) {
            if (val < current.val) {
                if (current.left == null) {
                    current.left = new TreeNode(val);
                    break;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new TreeNode(val);
                    break;
                }
                current = current.right;
            }
        }
        return root;
    }
}
// @formatter:off
/*
# Insert into a Binary Search Tree — Deep Dive

---

## 1. Problem Statement

### In Plain English
You are given the root of a **Binary Search Tree (BST)** and an integer value. Your task is to insert that value into the BST such that the BST property is maintained, and return the **root of the modified tree**.

### BST Property (Critical)
For every node:
- All values in the **left subtree** are **strictly less than** the node's value
- All values in the **right subtree** are **strictly greater than** the node's value

### Input Format
- `TreeNode root` — root of an existing BST (may be `null`)
- `int val` — the integer value to insert

### Output Format
- Return the `TreeNode` representing the **root** of the BST after insertion

### Constraints
- Number of nodes: `0 <= n <= 10⁴`
- Node values: `1 <= Node.val <= 10⁸`
- `1 <= val <= 10⁸`
- **It is guaranteed that `val` does not exist in the original BST** (no duplicates)

### What Exactly Needs to Be Computed
Find the correct position for `val` in the BST, insert a new node there, and return the root. The tree structure must still satisfy BST rules after insertion.

---

## 2. Intuition

### The Core Idea
A BST gives you a **binary decision at every node**: the value you're inserting is either smaller (go left) or larger (go right). You keep making these decisions until you fall off the tree — that empty spot is exactly where the new node belongs.

### How a Human Reasons About It
Imagine you're filing a document in a sorted filing cabinet:
1. Start at the first drawer (root)
2. Is your document's number smaller? Go to the left section
3. Is it larger? Go to the right section
4. Keep narrowing until you find an empty slot
5. Place the document there

### What Makes This Interesting
- The BST structure **guides you to the insertion point** — no searching needed
- The new node **always becomes a leaf** (you never need to restructure existing nodes)
- This problem is a gateway to understanding **BST traversal patterns** used in deletion, search, and balancing

---

## 3. Approach Overview

| # | Approach | Key Idea | Time | Space | Use When |
|---|----------|----------|------|-------|----------|
| 1 | **Recursive** | Recurse left/right, attach node on null | O(H) | O(H) call stack | Interviews, clean code |
| 2 | **Iterative** | Use a pointer, walk down tree manually | O(H) | O(1) | Production, deep trees |

> **H = height of tree** = O(log n) for balanced BST, O(n) worst case (skewed tree)

### Which is Optimal?
Both are O(H) time. The **iterative approach is optimal** in practice because it uses **O(1) space** (no recursion stack). However, the **recursive approach is preferred in interviews** for its clarity and elegance. Know both.

---

## 4. Detailed Solutions in Java

### TreeNode Definition (Given)
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

### Approach 1 — Recursive

#### Algorithm Step-by-Step
1. **Base case**: If `root` is `null`, we've found the insertion point — return a new `TreeNode(val)`
2. If `val < root.val`: recurse into the left subtree, and assign the result back to `root.left`
3. If `val > root.val`: recurse into the right subtree, and assign the result back to `root.right`
4. Return `root` (the current node, unchanged except for the newly attached child deep down)

#### Why "assign result back"?
This is the elegant trick of recursive BST insertion. When the recursion bottoms out and creates a new node, that node bubbles back up through the return chain and gets attached to its parent automatically.

```java
class Solution {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // Base case: found the correct empty position
        if (root == null) {
            return new TreeNode(val);
        }

        if (val < root.val) {
            // New value belongs in the left subtree
            root.left = insertIntoBST(root.left, val);
        } else {
            // New value belongs in the right subtree (val > root.val, no duplicates)
            root.right = insertIntoBST(root.right, val);
        }

        // Return current node (its structure is now updated)
        return root;
    }
}
```

---

### Approach 2 — Iterative

#### Algorithm Step-by-Step
1. **Edge case**: If `root` is `null`, the tree is empty — return a new node directly
2. Use a pointer `current` to walk down the tree
3. At each node, decide: go left or go right
4. Before moving, check if the child in that direction is `null`
   - If yes: **this is the insertion point** — attach the new node and stop
   - If no: move `current` to that child and continue
5. Return the original `root` (it never changes)

```java
class Solution {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // Edge case: inserting into an empty tree
        if (root == null) {
            return new TreeNode(val);
        }

        TreeNode current = root;

        while (true) {
            if (val < current.val) {
                // New value belongs to the left
                if (current.left == null) {
                    current.left = new TreeNode(val); // Insert here
                    break;
                }
                current = current.left; // Move left
            } else {
                // New value belongs to the right
                if (current.right == null) {
                    current.right = new TreeNode(val); // Insert here
                    break;
                }
                current = current.right; // Move right
            }
        }

        return root; // Root is always unchanged
    }
}
```

---

## 5. Time & Space Complexity

### Approach 1 — Recursive

| Metric | Value | Reasoning |
|--------|-------|-----------|
| Time | **O(H)** | We visit exactly one node per level, following one path root → leaf |
| Space | **O(H)** | The call stack holds one frame per level of recursion |

- **Balanced BST** (H = log n): ~14 operations for n = 10,000
- **Skewed BST** (H = n): ~10,000 operations for n = 10,000 (degrades to linked list)

### Approach 2 — Iterative

| Metric | Value | Reasoning |
|--------|-------|-----------|
| Time | **O(H)** | Same traversal — one path from root to insertion point |
| Space | **O(1)** | Only one pointer `current` regardless of tree size |

- For n = 10,000 balanced: ~14 comparisons, 1 pointer variable
- For n = 10,000 skewed: ~10,000 comparisons, still just 1 pointer

> **Key Insight**: Both have the same time complexity but iterative wins on space. In deeply skewed trees, recursive could cause a **StackOverflowError** in Java — iterative is safer.

---

## 6. Complete Worked Examples

### Example 1 — Recursive Approach

**Input Tree:**
```
        4
       / \
      2   7
     / \
    1   3

val = 5
```

**Step-by-Step Trace:**

| Call | root.val | val | Decision | Action |
|------|----------|-----|----------|--------|
| 1st | 4 | 5 | 5 > 4 | recurse right → node(7) |
| 2nd | 7 | 5 | 5 < 7 | recurse left → null |
| 3rd | null | 5 | base case | return new TreeNode(5) |
| Back at 2nd | 7 | — | — | node(7).left = node(5) |
| Back at 1st | 4 | — | — | node(4).right = node(7) [unchanged] |

**Result Tree:**
```
        4
       / \
      2   7
     / \ /
    1  3 5
```

✅ BST property holds: 5 < 7 and 5 > 4

---

### Example 2 — Iterative Approach

**Input Tree:**
```
        40
       /  \
      20   60
     /  \
    10   30

val = 25
```

**Step-by-Step Pointer Trace:**

```
Step 1: current = node(40), val=25 < 40 → go left
        current.left = node(20) ≠ null → move

Step 2: current = node(20), val=25 > 20 → go right
        current.right = node(30) ≠ null → move

Step 3: current = node(30), val=25 < 30 → go left
        current.left = null → INSERT HERE!
        node(30).left = new TreeNode(25)
        break
```

**Result Tree:**
```
        40
       /  \
      20   60
     /  \
    10   30
        /
       25
```

✅ BST property holds: 20 < 25 < 30

---

### Example 3 — Empty Tree Edge Case

**Input:** `root = null`, `val = 10`

- **Recursive**: Immediately hits base case, returns `new TreeNode(10)`
- **Iterative**: Immediately hits null check, returns `new TreeNode(10)`

**Output:** A single node tree with value 10. ✅

---

## 7. Edge Cases

### Edge Case Analysis Table

| Edge Case | Description | Recursive Handling | Iterative Handling |
|-----------|-------------|-------------------|-------------------|
| **Empty tree** | `root = null` | Base case returns new node immediately ✅ | Null check at top returns new node ✅ |
| **Single node, insert left** | val < root.val, tree has 1 node | Recurse left → null → create node ✅ | current.left == null → insert immediately ✅ |
| **Single node, insert right** | val > root.val | Recurse right → null → create node ✅ | current.right == null → insert immediately ✅ |
| **Insert smallest value** | val smaller than all existing | Walks all the way to leftmost null ✅ | Same path ✅ |
| **Insert largest value** | val larger than all existing | Walks all the way to rightmost null ✅ | Same path ✅ |
| **Highly skewed tree (left)** | Like a linked list going left | O(n) recursion depth — **StackOverflow risk** ⚠️ | O(1) space — safe ✅ |
| **Highly skewed tree (right)** | Like a linked list going right | Same StackOverflow risk ⚠️ | Safe ✅ |
| **Max constraint** | n = 10,000 nodes | Risky for skewed trees ⚠️ | Completely safe ✅ |

### Critical Warning on Recursive Approach
Java's default stack depth is ~500–1000 frames. For a **skewed BST with 10,000 nodes**, the recursive solution **will throw a StackOverflowError**. The iterative solution is robust here.

### Duplicate Values
The problem guarantees no duplicates, but if they occurred: neither solution handles them explicitly — the value would be inserted in the right subtree by default (due to the `else` branch). This is a valid tie-breaking convention.

---

## 8. Final Summary

### Comparison Table

| Criterion | Recursive | Iterative |
|-----------|-----------|-----------|
| Code clarity | ⭐⭐⭐⭐⭐ Elegant, minimal | ⭐⭐⭐⭐ Slightly more verbose |
| Time complexity | O(H) | O(H) |
| Space complexity | O(H) — call stack | **O(1)** — winner |
| Interview preference | ✅ Great for interviews | ✅ Shows depth of knowledge |
| Production safety | ⚠️ Stack risk on skewed trees | ✅ Always safe |
| Ease of understanding | Very intuitive | Intuitive with pointer logic |

### Recommendation
- **In interviews**: Lead with the recursive solution for its elegance, then mention the iterative solution and its space advantage to impress the interviewer
- **In production**: Always use the iterative solution

### What to Remember
> **"In a BST, insertion always lands at a leaf. Let the BST property guide you at each node — smaller goes left, larger goes right — until you fall off the tree."**

This pattern (recurse/iterate left or right based on comparison, act on null) is the **foundational template** for BST search, insertion, and deletion.

---

## 9. Companies & Frequency

### Where This Problem Has Been Asked

| Company | Frequency | Notes |
|---------|-----------|-------|
| **Amazon** | ⭐⭐⭐⭐⭐ Very High | Extremely common in SDE interviews |
| **Microsoft** | ⭐⭐⭐⭐ High | Common in phone screens |
| **Google** | ⭐⭐⭐ Medium | Often as warm-up or follow-up |
| **Facebook/Meta** | ⭐⭐⭐ Medium | Part of tree question sets |
| **Bloomberg** | ⭐⭐⭐⭐ High | Frequently reported |
| **Adobe** | ⭐⭐⭐ Medium | Common in technical rounds |
| **LinkedIn** | ⭐⭐ Moderate | Occasional |
| **Apple** | ⭐⭐ Moderate | Occasional |

### LeetCode Statistics
- **Problem**: LeetCode #701
- **Difficulty**: Easy
- **Acceptance Rate**: ~77%
- **Total Submissions**: 1M+
- **Frequency Tag**: Appears in **Top Interview Questions** and **Amazon Question Bank**

### Interview Context
This problem is frequently used as:
1. A **warm-up question** before harder BST problems (deletion, validation, LCA)
2. A **foundation check** — interviewers want to see if you understand BST invariants
3. A **segue** into follow-ups like: *"Now delete a node"*, *"Now insert into an AVL tree"*, *"What if the tree is unbalanced?"*
*/
// @formatter:on