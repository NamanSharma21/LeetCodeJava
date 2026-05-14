package Trees;

import Datastructures.TreeNode;

public class LowestCommonAncestorOfABinarySearchTree {
    public static void main(String[] args) {
        LowestCommonAncestorOfABinarySearchTree lowestCommonAncestorOfABinarySearchTree = new LowestCommonAncestorOfABinarySearchTree();
        TreeNode root = new TreeNode(6);
        root.left = new TreeNode(2);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(0);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(7);
        root.right.right = new TreeNode(9);
        root.left.right.left = new TreeNode(3);
        root.left.right.right = new TreeNode(5);
        System.out.println("LowestCommonAncestorOfABinarySearchTree : \n" + lowestCommonAncestorOfABinarySearchTree
                .lowestCommonAncestorRecursiveBST(root, root.left, root.right));

        System.out.println("LowestCommonAncestorOfABinarySearchTree : \n" + lowestCommonAncestorOfABinarySearchTree
                .lowestCommonAncestorIterativeBST(root, root.left, root.right));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/description/?envType=problem-list-v2&envId=tree
     * 
     * Given a binary search tree (BST), find the lowest common ancestor (LCA) node
     * of two given nodes in the BST.
     * 
     * According to the definition of LCA on Wikipedia: “The lowest common ancestor
     * is defined between two nodes p and q as the lowest node in T that has both p
     * and q as descendants (where we allow a node to be a descendant of itself).”
     * 
     * 
     * 
     * Example 1:
     * 
     * Case 1: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
     * Expected LCA: 6
     *       6
     *     /   \
     *    2     8
     *   / \   / \
     *  0   4 7   9
     *     / \
     *    3   5
     * 
     * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
     * Output: 6
     * Explanation: The LCA of nodes 2 and 8 is 6.
     * Example 2:
     * 
     * Case 2: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
     * Expected LCA: 2 (Node can be descendant of itself)
     *       6
     *     /   \
     *    2     8
     *   / \   / \
     *  0   4 7   9
     *     / \
     *    3   5
     * 
     * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
     * Output: 2
     * Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant
     * of itself according to the LCA definition.
     * Example 3:
     * 
     * Case 3: root = [2,1], p = 2, q = 1
     * Expected LCA: 2
     *    2
     *   /
     *  1
     * 
     * 
     * Input: root = [2,1], p = 2, q = 1
     * Output: 2
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [2, 105].
     * -109 <= Node.val <= 109
     * All Node.val are unique.
     * p != q
     * p and q will exist in the BST.
     */
    // @formatter:on

    public TreeNode lowestCommonAncestorRecursiveBST(TreeNode root, TreeNode p, TreeNode q) {
        int currentVal = root.val;
        int pVal = p.val;
        int qVal = q.val;
        if (pVal < currentVal && qVal < currentVal) {
            return lowestCommonAncestorRecursiveBST(root.left, p, q);
        } else if (pVal > currentVal && qVal > currentVal) {
            return lowestCommonAncestorRecursiveBST(root.right, p, q);
        } else {
            return root;
        }
    }

    public TreeNode lowestCommonAncestorIterativeBST(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode current = root;
        while (current != null) {
            if (p.val < current.val && q.val < current.val) {
                current = current.left;
            } else if (p.val > current.val && q.val > current.val) {
                current = current.right;
            } else {
                return current;
            }
        }
        return null;
    }

    // @formatter:off
    /*
    # Lowest Common Ancestor of a Binary Search Tree

---

## 1. Problem Statement

### Restatement
Given a **Binary Search Tree (BST)** and two nodes `p` and `q`, find their **Lowest Common Ancestor (LCA)** — the deepest node in the tree that is an ancestor of both `p` and `q` (a node can be an ancestor of itself).

### Input Format
- Root of a BST (`TreeNode root`)
- Two nodes `p` and `q` (both guaranteed to exist in the tree)
- Node values are **unique**

### Output Format
- Return the `TreeNode` that is the LCA of `p` and `q`

### Constraints
- Number of nodes: `[2, 10^5]`
- Node values: `-10^9 <= val <= 10^9`
- `p != q`
- Both `p` and `q` exist in the BST

### What Exactly Needs to Be Computed
Find the **deepest node** that is a common ancestor of both `p` and `q`. A node is its own ancestor, so if `p` is an ancestor of `q`, then `p` is the LCA.

---

## 2. Intuition

### Core Idea in Simple Terms

Think of a BST like a sorted family tree. In a BST:
- **Everything to the left** of a node is **smaller**
- **Everything to the right** is **larger**

This property is the key. Imagine you're standing at the root and need to find where `p` and `q` "diverge." The moment one node goes left and the other goes right (or one of them **is** the current node), that's your LCA.

### Human Reasoning Step-by-Step

> Suppose `p = 2`, `q = 8`, root = `6`

1. At node `6`: Is `2 < 6` and `8 > 6`? → They split here → **LCA is 6**

> Suppose `p = 2`, `q = 4`, root = `6`

1. At node `6`: Both `2 < 6` and `4 < 6` → Both are in the **left subtree** → go left
2. At node `2`: Is `4 > 2`? → They split here → **LCA is 2**

### What Makes This Problem Interesting

- A **generic binary tree** LCA requires O(n) traversal (no ordering to exploit)
- A **BST** lets you exploit the sorted property to navigate directly — no need to explore the entire tree
- The BST property transforms an O(n) problem into O(h) where `h` is tree height

---

## 3. Approach Overview

| # | Approach | Key Idea | Time | Space | When to Use |
|---|----------|----------|------|-------|-------------|
| 1 | **Brute Force** (Path Finding) | Find root-to-p and root-to-q paths, compare them | O(n) | O(n) | Understanding only |
| 2 | **Recursive BST** | Use BST property to recurse into correct subtree | O(h) | O(h) | Clean interviews |
| 3 | **Iterative BST** ⭐ | Same logic, no recursion stack | O(h) | O(1) | **Optimal** |

> ⭐ **Optimal Approach: Iterative BST Traversal** — O(h) time, O(1) space. No recursion overhead, directly leverages BST ordering.

---

## 4. Detailed Solutions in Java

### Solution 1: Brute Force — Path Finding

#### Algorithm
1. Traverse from root to `p`, storing the path (list of nodes)
2. Traverse from root to `q`, storing the path
3. Compare both paths from the beginning — the last node they share is the LCA

```java
import java.util.ArrayList;
import java.util.List;

class Solution {

    // Brute Force: Find paths to both nodes, then find last common node
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> pathToP = new ArrayList<>();
        List<TreeNode> pathToQ = new ArrayList<>();

        findPath(root, p, pathToP);
        findPath(root, q, pathToQ);

        TreeNode lca = null;
        int minLength = Math.min(pathToP.size(), pathToQ.size());

        // Walk both paths simultaneously; last matching node is the LCA
        for (int i = 0; i < minLength; i++) {
            if (pathToP.get(i).val == pathToQ.get(i).val) {
                lca = pathToP.get(i);
            } else {
                break;
            }
        }
        return lca;
    }

    // Recursively build path from root to target node
    private boolean findPath(TreeNode node, TreeNode target, List<TreeNode> path) {
        if (node == null) return false;

        path.add(node); // tentatively add current node

        if (node.val == target.val) return true;

        // Search left or right subtree
        if (findPath(node.left, target, path) || findPath(node.right, target, path)) {
            return true;
        }

        path.remove(path.size() - 1); // backtrack if not found in this subtree
        return false;
    }
}
```

---

### Solution 2: Recursive BST Traversal

#### Algorithm
1. If both `p` and `q` are **less than** current node → LCA is in left subtree
2. If both `p` and `q` are **greater than** current node → LCA is in right subtree
3. Otherwise → current node is the **split point** = LCA

```java
class Solution {

    // Recursive: exploit BST property to navigate directly
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        int currentVal = root.val;
        int pVal = p.val;
        int qVal = q.val;

        if (pVal < currentVal && qVal < currentVal) {
            // Both nodes are in the left subtree
            return lowestCommonAncestor(root.left, p, q);
        } else if (pVal > currentVal && qVal > currentVal) {
            // Both nodes are in the right subtree
            return lowestCommonAncestor(root.right, p, q);
        } else {
            // Split point found: one is left, one is right, or one equals current
            return root;
        }
    }
}
```

---

### Solution 3: Iterative BST Traversal ⭐ (Optimal)

#### Algorithm
Same logic as recursive, but uses a `while` loop — no call stack overhead.

1. Start at root
2. At each node, compare both `p.val` and `q.val` with current node's value
3. Navigate left or right, or return current node when split is found

```java
class Solution {

    // Optimal Iterative: O(h) time, O(1) space
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode current = root;

        while (current != null) {
            if (p.val < current.val && q.val < current.val) {
                // Both targets are in the left subtree
                current = current.left;
            } else if (p.val > current.val && q.val > current.val) {
                // Both targets are in the right subtree
                current = current.right;
            } else {
                // Current node is the split point — this is our LCA
                return current;
            }
        }

        return null; // should never reach here given valid inputs
    }
}
```

---

## 5. Time & Space Complexity

### Brute Force (Path Finding)

| | Complexity | Reasoning |
|--|-----------|-----------|
| **Time** | O(n) | In the worst case (skewed tree or targets at leaves), we visit every node to find paths |
| **Space** | O(n) | Two path lists each storing up to O(n) nodes; recursion stack also O(n) |

**Example walkthrough:** Tree with 100 nodes, `p` and `q` are leaf nodes → ~200 node visits total.

---

### Recursive BST

| | Complexity | Reasoning |
|--|-----------|-----------|
| **Time** | O(h) | At each step, we eliminate one half of the tree (like binary search); h = height |
| **Space** | O(h) | Recursion call stack depth equals tree height |

- **Balanced BST:** h = log n → O(log n) time, O(log n) space
- **Skewed BST:** h = n → O(n) time, O(n) space (worst case)

**Example:** 1000-node balanced BST → ~10 recursive calls (log₂ 1000 ≈ 10)

---

### Iterative BST ⭐

| | Complexity | Reasoning |
|--|-----------|-----------|
| **Time** | O(h) | Same navigation logic as recursive — one path down the tree |
| **Space** | **O(1)** | No recursion stack; only a single pointer `current` is maintained |

- **Balanced BST:** O(log n) time, O(1) space ← best possible
- **Skewed BST:** O(n) time, O(1) space

---

## 6. Complete Worked Examples

### Example 1 — All Three Approaches

**BST Structure:**
```
        6
       / \
      2   8
     / \ / \
    0  4 7  9
      / \
     3   5
```
**Input:** `p = 2`, `q = 8`

---

#### Brute Force Walkthrough

| Step | Action |
|------|--------|
| 1 | Find path to `p=2`: `[6, 2]` ✓ |
| 2 | Find path to `q=8`: `[6, 8]` ✓ |
| 3 | Compare index 0: both are `6` → lca = `6` |
| 4 | Compare index 1: `2 ≠ 8` → break |
| **Output** | **Node 6** |

---

#### Recursive BST Walkthrough

| Step | Current Node | p=2, q=8 | Decision |
|------|-------------|----------|----------|
| 1 | `6` | 2 < 6 but 8 > 6 | **Split! Return 6** |
| **Output** | **Node 6** | | |

---

#### Iterative BST Walkthrough

| Iteration | `current` | Condition | Action |
|-----------|-----------|-----------|--------|
| 1 | `6` | 2 < 6, 8 > 6 → neither both left nor both right | **Return 6** |
| **Output** | **Node 6** | | |

---

### Example 2 — p = 2, q = 4

**Same BST as above**

#### Iterative BST Walkthrough

| Iteration | `current.val` | p=2, q=4 comparison | Action |
|-----------|--------------|---------------------|--------|
| 1 | `6` | Both 2 < 6 AND 4 < 6 | Move to `current = current.left` (node 2) |
| 2 | `2` | p=2 == current, not both > 2 | **Split/match! Return node 2** |

**Output: Node 2** ✓ (since node 2 is ancestor of node 4, and is itself)

---

### Example 3 — p = 3, q = 5

#### Iterative BST Walkthrough

| Iteration | `current.val` | Condition | Action |
|-----------|--------------|-----------|--------|
| 1 | `6` | Both 3 < 6 AND 5 < 6 | Move left → node `2` |
| 2 | `2` | Both 3 > 2 AND 5 > 2 | Move right → node `4` |
| 3 | `4` | 3 < 4 but 5 > 4 | **Split! Return node 4** |

**Output: Node 4** ✓

---

## 7. Edge Cases

### Case 1: One Node is the Ancestor of the Other
```
p = 2, q = 4 (4 is in subtree of 2)
```
- ✅ **All approaches handle this correctly** — when `current == p` or `current == q`, the "split" condition naturally triggers and returns that node (since it's its own ancestor).

---

### Case 2: p and q Are Root's Immediate Children
```
p = 2 (left child of root 6), q = 8 (right child of root 6)
```
- ✅ Detected at the very first node (root). All approaches return root immediately.

---

### Case 3: Both Nodes Are Deep in the Same Subtree
```
p = 3, q = 5 (both deep in left subtree)
```
- ✅ Iterative correctly traverses left multiple times before finding split at node 4.
- ⚠️ Brute force visits more nodes unnecessarily.

---

### Case 4: p == q (not in constraints, but worth noting)
- The problem guarantees `p != q`, so this won't occur.
- If it did: all three approaches would return the node itself at the point `current.val == p.val == q.val`.

---

### Case 5: Skewed Tree (Worst Case for Time)
```
1 → 2 → 3 → 4 → 5 (all right children)
p = 1, q = 5
```
- ✅ All approaches work correctly
- ⚠️ Recursive approach risks **stack overflow** on very deep skewed trees (n up to 10^5)
- ✅ Iterative approach is safe — O(1) space, no stack overflow risk

---

### Case 6: Minimum Tree (2 nodes)
```
    2
   /
  1
p = 2, q = 1
```
- ✅ At node 2: p=2 matches current, q=1 < current → split condition triggers → returns node 2 correctly.

---

### Case 7: Large Values (Overflow Risk)
- Node values up to `10^9` → fits in `int`, no overflow in comparisons
- ✅ All approaches safe

---

## 8. Final Summary

### Comparison Table

| Approach | Time | Space | Pros | Cons |
|----------|------|-------|------|------|
| Brute Force (Paths) | O(n) | O(n) | Easy to understand | Doesn't use BST property, wastes space |
| Recursive BST | O(h) | O(h) | Clean, elegant code | Stack overflow risk on skewed BST |
| **Iterative BST** ⭐ | **O(h)** | **O(1)** | Optimal in every way, no stack risk | Slightly more verbose |

### Recommendation
**Use the Iterative BST approach** in interviews and production. It's optimal in both time and space, avoids recursion stack risks, and elegantly leverages the BST's core invariant.

### Key Pattern to Remember
> **"In a BST, the first node where p and q stop being on the same side is the LCA."** This is a direct application of the BST invariant — always think about how sorted order lets you eliminate half the tree at each step, just like binary search.

---

## 9. Company Appearances & Frequency

| Company | Frequency | Notes |
|---------|-----------|-------|
| **Amazon** | ⭐⭐⭐⭐⭐ Very High | One of the most common tree questions |
| **Microsoft** | ⭐⭐⭐⭐⭐ Very High | Asked in both phone screens and onsite rounds |
| **Facebook/Meta** | ⭐⭐⭐⭐ High | Often combined with general LCA follow-up |
| **Google** | ⭐⭐⭐⭐ High | May ask for both BST and general binary tree variant |
| **Bloomberg** | ⭐⭐⭐⭐ High | Frequently reported in recent interview cycles |
| **Apple** | ⭐⭐⭐ Medium | Appears in SDE and SDE-II rounds |
| **LinkedIn** | ⭐⭐⭐ Medium | Tree traversal rounds |
| **Adobe** | ⭐⭐⭐ Medium | Paired with BST validation questions |
| **Uber** | ⭐⭐ Medium-Low | Occasionally asked |
| **Oracle** | ⭐⭐ Medium-Low | Appears in algorithm rounds |

### Overall LeetCode Stats
- **LeetCode Problem #235** — Rated **Medium**
- Appeared in **100+ company interview reports**
- Among the **top 50 most frequently asked** tree problems globally
- Interviewers often follow up with **LeetCode #236** (LCA of a Binary Tree — without BST property), so always be ready to discuss both variants
    */
    // @formatter:on

}
