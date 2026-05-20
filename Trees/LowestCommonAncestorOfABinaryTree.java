package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Datastructures.TreeNode;

public class LowestCommonAncestorOfABinaryTree {
    public static void main(String[] args) {
        LowestCommonAncestorOfABinaryTree lowestCommonAncestorOfABinaryTree = new LowestCommonAncestorOfABinaryTree();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);
        System.out.println("LowestCommonAncestorOfABinaryTree : \n"
                + lowestCommonAncestorOfABinaryTree.lowestCommonAncestor(root, root.left, root.right));

        System.out.println("LowestCommonAncestorOfABinaryTree : \n"
                + lowestCommonAncestorOfABinaryTree.lowestCommonAncestorBruteForce(root, root.left, root.right));

        System.out.println("LowestCommonAncestorOfABinaryTree : \n"
                + lowestCommonAncestorOfABinaryTree.lowestCommonAncestorParentMap(root, root.left, root.right));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/description/?envType=problem-list-v2&envId=tree
     * 
     * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes
     * in the tree.
     * 
     * According to the definition of LCA on Wikipedia: “The lowest common ancestor
     * is defined between two nodes p and q as the lowest node in T that has both p
     * and q as descendants (where we allow a node to be a descendant of itself).”
     * 
     * 
     * 
     * Example 1:
     * 
     * Test Case 1: p = 5, q = 1 → LCA = 3
     * 
     *         3
     *        / \
     *       5   1
     *      / \ / \
     *     6  2 0  8
     *       / \
     *      7   4
     * 
     * Nodes 5 and 1 are in different subtrees of root 3.
     * 
     * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
     * Output: 3
     * Explanation: The LCA of nodes 5 and 1 is 3.
     * Example 2:
     * 
     * Test Case 2: p = 5, q = 4 → LCA = 5
     * 
     *         3
     *        / \
     *       5   1
     *      / \ / \
     *     6  2 0  8
     *       / \
     *      7   4
     * 
     * Node 4 is a descendant of node 5, so LCA is 5 itself.
     * 
     * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
     * Output: 5
     * Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant
     * of itself according to the LCA definition.
     * Example 3:
     * 
     * Input: root = [1,2], p = 1, q = 2
     * Output: 1
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [2, 105].
     * -109 <= Node.val <= 109
     * All Node.val are unique.
     * p != q
     * p and q will exist in the tree.
     */
    // @formatter:on

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q)
            return root;
        TreeNode leftResult = lowestCommonAncestor(root.left, p, q);
        TreeNode rightResult = lowestCommonAncestor(root.right, p, q);
        if (leftResult != null && rightResult != null)
            return root;
        return leftResult != null ? leftResult : rightResult;
    }

    public TreeNode lowestCommonAncestorBruteForce(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> pathToP = new ArrayList<>();
        List<TreeNode> pathToQ = new ArrayList<>();
        findPath(root, p, pathToP);
        findPath(root, q, pathToQ);

        TreeNode lca = null;
        int minSize = Math.min(pathToP.size(), pathToQ.size());
        for (int i = 0; i < minSize; i++) {
            if (pathToP.get(i) == pathToQ.get(i))
                lca = pathToP.get(i);
            else
                break;
        }
        return lca;
    }

    public boolean findPath(TreeNode root, TreeNode target, List<TreeNode> path) {
        if (root == null)
            return false;
        path.add(root);
        if (root == target)
            return true;
        if (findPath(root.left, target, path) || findPath(root.right, target, path)) {
            return true;
        }
        path.remove(path.size() - 1);
        return false;
    }

    public TreeNode lowestCommonAncestorParentMap(TreeNode root, TreeNode p, TreeNode q) {
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.push(root);
        parentMap.put(root, null);

        while (!parentMap.containsKey(p) || !parentMap.containsKey(q)) {
            TreeNode node = queue.pop();
            if (node.left != null) {
                parentMap.put(node.left, node);
                queue.push(node.left);
            }

            if (node.right != null) {
                parentMap.put(node.right, node);
                queue.push(node.right);
            }
        }

        Set<TreeNode> ancestorOfP = new HashSet<>();
        TreeNode curr = p;
        while (curr != null) {
            ancestorOfP.add(curr);
            curr = parentMap.get(curr);
        }

        TreeNode qCurr = q;
        while (!ancestorOfP.contains(qCurr)) {
            qCurr = parentMap.get(qCurr);
        }
        return qCurr;
    }
}

// @formatter:off
/*
# Lowest Common Ancestor of a Binary Tree

---

## 1. Problem Statement

Given a **binary tree** (not necessarily a BST) and two nodes `p` and `q`, find their **Lowest Common Ancestor (LCA)**.

The **LCA** of two nodes `p` and `q` in a binary tree is defined as the **deepest node** that has both `p` and `q` as descendants, where a node is also considered a descendant of itself.

### Input Format
- `root` — the root of a binary tree (`TreeNode`)
- `p` — a `TreeNode` reference (guaranteed to exist in the tree)
- `q` — a `TreeNode` reference (guaranteed to exist in the tree)

### Output Format
- Return a single `TreeNode` — the LCA of `p` and `q`

### Constraints
- Number of nodes: `[2, 10^5]`
- Node values: `[-10^9, 10^9]`
- All node values are **unique**
- Both `p` and `q` **exist** in the tree
- `p ≠ q`

### What Needs to Be Computed
Find the **deepest** node in the tree that is an ancestor of **both** `p` and `q`. A node can be its own ancestor (so if `p` is an ancestor of `q`, the answer is `p`).

---

## 2. Intuition

### Core Idea in Simple Terms

Imagine you and a friend both start at different locations in a city and walk toward the city center. The **first intersection where your paths meet** is the LCA.

In a tree:
- Start from the **root** and recurse down
- If you find `p` or `q` at a node, **report back up**
- If the **left subtree** found one and the **right subtree** found the other → the **current node is the LCA**
- If only one side found something → **bubble that result upward**

### Human Reasoning
```
        3
       / \
      5   1
     / \ / \
    6  2 0  8
      / \
     7   4
```
To find LCA(5, 4):
1. At node `3`: go left and right
2. Left subtree (`5`): found `5` itself → report `5` upward
3. Right subtree (`1`): found nothing → report `null`
4. Since only the left side returned something → LCA is `5`

To find LCA(6, 4):
1. At node `3`: recurse both sides
2. Left subtree finds `6` (left) and `4` (right of `2`) → node `5` has both → return `5`
3. Right subtree returns `null`
4. Answer: `5`

### What Makes It Tricky
- It's **not** a BST, so we **cannot** use value comparisons to navigate
- A node can be its **own ancestor**
- The solution must handle both "one is ancestor of the other" and "they're in separate subtrees"

---

## 3. Approach Overview

| # | Approach | Key Idea | Time | Space | Use When |
|---|----------|----------|------|-------|----------|
| 1 | **Brute Force — Path Finding** | Find root→p and root→q paths, compare | O(n) | O(n) | Small trees, easy to reason |
| 2 | **Iterative with Parent Map** | BFS + parent pointers + ancestor set | O(n) | O(n) | Avoids recursion stack limits |
| 3 | **Recursive Postorder (Optimal)** | Single DFS, bubble up results | O(n) | O(h) | Interviews, production code |

> ✅ **Optimal: Approach 3** — Single recursive DFS. Clean, minimal, O(h) space (stack depth), O(n) time. The canonical interview solution.

---

## 4. Detailed Solutions in Java

### TreeNode Definition (used by all approaches)
```java
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
```

---

### Approach 1 — Brute Force: Path Finding

#### Algorithm
1. Find the path from `root → p` (list of nodes)
2. Find the path from `root → q` (list of nodes)
3. Walk both paths simultaneously — the **last node where they agree** is the LCA

```java
import java.util.ArrayList;
import java.util.List;

public class LCA_BruteForce {

    // Find path from root to target node
    private boolean findPath(TreeNode root, TreeNode target, List<TreeNode> path) {
        if (root == null) return false;

        path.add(root); // tentatively add current node

        if (root == target) return true; // found it

        // Search left and right subtrees
        if (findPath(root.left, target, path) || findPath(root.right, target, path)) {
            return true;
        }

        path.remove(path.size() - 1); // backtrack
        return false;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> pathToP = new ArrayList<>();
        List<TreeNode> pathToQ = new ArrayList<>();

        findPath(root, p, pathToP);
        findPath(root, q, pathToQ);

        TreeNode lca = null;
        int minLen = Math.min(pathToP.size(), pathToQ.size());

        // Walk both paths and find last common node
        for (int i = 0; i < minLen; i++) {
            if (pathToP.get(i) == pathToQ.get(i)) {
                lca = pathToP.get(i);
            } else {
                break;
            }
        }

        return lca;
    }
}
```

---

### Approach 2 — Iterative with Parent Map

#### Algorithm
1. BFS/DFS the entire tree, recording each node's **parent**
2. Collect all **ancestors of `p`** into a set (by walking up via parent map)
3. Walk up from `q` — the **first ancestor of `q`** that's also in `p`'s ancestor set is the LCA

```java
import java.util.*;

public class LCA_Iterative {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Map each node to its parent
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        parentMap.put(root, null); // root has no parent

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        // Build parent map until both p and q are found
        while (!parentMap.containsKey(p) || !parentMap.containsKey(q)) {
            TreeNode node = stack.pop();

            if (node.left != null) {
                parentMap.put(node.left, node);
                stack.push(node.left);
            }
            if (node.right != null) {
                parentMap.put(node.right, node);
                stack.push(node.right);
            }
        }

        // Collect all ancestors of p (including p itself)
        Set<TreeNode> ancestorsOfP = new HashSet<>();
        TreeNode curr = p;
        while (curr != null) {
            ancestorsOfP.add(curr);
            curr = parentMap.get(curr);
        }

        // Walk up from q — first hit in ancestorsOfP is the LCA
        TreeNode qCurr = q;
        while (!ancestorsOfP.contains(qCurr)) {
            qCurr = parentMap.get(qCurr);
        }

        return qCurr;
    }
}
```

---

### Approach 3 — Optimal Recursive Postorder DFS ✅

#### Algorithm (Step-by-Step)

```
lowestCommonAncestor(node, p, q):
  1. BASE CASE: if node is null → return null
  2. BASE CASE: if node == p OR node == q → return node (found one target)
  3. Recurse LEFT  → leftResult
  4. Recurse RIGHT → rightResult
  5. If BOTH leftResult and rightResult are non-null
       → current node is the LCA (p and q are on opposite sides)
       → return node
  6. If only LEFT is non-null  → return leftResult  (both targets are in left subtree)
  7. If only RIGHT is non-null → return rightResult (both targets are in right subtree)
```

```java
public class LCA_Optimal {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Base case: null node or found one of the targets
        if (root == null || root == p || root == q) {
            return root;
        }

        // Recursively search left and right subtrees
        TreeNode leftResult  = lowestCommonAncestor(root.left,  p, q);
        TreeNode rightResult = lowestCommonAncestor(root.right, p, q);

        // Both sides found a target → current node is the LCA
        if (leftResult != null && rightResult != null) {
            return root;
        }

        // Only one side found something → propagate that result upward
        return (leftResult != null) ? leftResult : rightResult;
    }
}
```

> This is **9 lines of logic** — clean, correct, and the most commonly expected answer in interviews.

---

## 5. Time & Space Complexity

### Approach 1 — Path Finding

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | We traverse the tree twice (once per path search) — each traversal visits at most `n` nodes |
| **Space** | O(n) | Two path lists, each up to `n` nodes long in a skewed tree |

**Example:** Tree with 100 nodes, skewed → path list could hold 100 nodes → ~200 nodes stored.

---

### Approach 2 — Iterative Parent Map

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | BFS/DFS builds parent map by visiting every node once |
| **Space** | O(n) | `parentMap` stores all `n` nodes; ancestor set stores up to `h` nodes |

**Example:** Balanced tree with 10,000 nodes → parentMap has 10,000 entries → O(n) space.

---

### Approach 3 — Recursive DFS (Optimal) ✅

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | Every node is visited exactly once in the DFS |
| **Space** | O(h) | Recursion call stack depth = height `h`. Best case O(log n) balanced, worst case O(n) skewed |

**Example walkthrough on sizes:**
- 1,000-node balanced tree → height ≈ 10 → ~10 stack frames
- 1,000-node skewed tree → height = 1,000 → ~1,000 stack frames

> Approach 3 wins because it's a **single pass** and uses **O(h) space** (not O(n) like the others).

---

## 6. Complete Worked Examples

### Example Tree (used for all approaches)
```
           3
          / \
         5   1
        / \ / \
       6  2 0  8
         / \
        7   4
```

---

### Example 1: LCA(5, 1) — Targets in separate subtrees

**Approach 3 Walkthrough:**

| Step | Node | left result | right result | Action |
|------|------|-------------|--------------|--------|
| 1 | `6` | null | null | return null |
| 2 | `7` | null | null | return null |
| 3 | `4` | null | null | return null |
| 4 | `2` | null (7) | null (4) | return null |
| 5 | `5` | **5** (itself) | null | return **5** ← hit p |
| 6 | `0` | null | null | return null |
| 7 | `8` | null | null | return null |
| 8 | `1` | **1** (itself) | null | return **1** ← hit q |
| 9 | `3` | **5** (left) | **1** (right) | **Both non-null → return 3** ✅ |

**Output: `3`**

---

### Example 2: LCA(5, 4) — One is ancestor of the other

**Approach 3 Walkthrough:**

```
Call stack (postorder):
lowestCommonAncestor(3, 5, 4)
  └─ lowestCommonAncestor(5, 5, 4)
       └─ root == p (5) → return 5  ← STOP, don't go deeper
  └─ lowestCommonAncestor(1, 5, 4)
       └─ ... returns null
  leftResult = 5, rightResult = null → return 5
```

| Step | Node | Observation | Return |
|------|------|-------------|--------|
| 1 | `5` | `root == p` | return `5` immediately |
| 2 | `1` | Neither subtree has `5` or `4` | return `null` |
| 3 | `3` | left=`5`, right=`null` | return `5` ✅ |

**Output: `5`**

> Key insight: Once we hit `p`, we return it — we **trust** that `q` must be somewhere in `p`'s subtree (since the result bubbles up as `p`).

---

### Example 3: LCA(6, 4) — Deep nodes, different branches of same subtree

**Approach 3 Walkthrough:**

```
lowestCommonAncestor(3, 6, 4)
  ├─ lowestCommonAncestor(5, 6, 4)
  │    ├─ lowestCommonAncestor(6, 6, 4) → root==p → return 6
  │    └─ lowestCommonAncestor(2, 6, 4)
  │         ├─ lowestCommonAncestor(7, 6, 4) → null
  │         └─ lowestCommonAncestor(4, 6, 4) → root==q → return 4
  │         Both sides non-null → return 2? No...
  │    left=6, right=4 → both non-null → return 5 ✅
  └─ lowestCommonAncestor(1, 6, 4) → null
```

| Call | left | right | Return |
|------|------|-------|--------|
| `6` | — | — | `6` (is p) |
| `7` | null | null | `null` |
| `4` | — | — | `4` (is q) |
| `2` | null | `4` | `4` |
| `5` | `6` | `4` | **`5`** ← both sides non-null |
| `1` | null | null | `null` |
| `3` | `5` | null | `5` |

**Output: `5`** ✅

---

## 7. Edge Cases

| Edge Case | Description | Approach 1 | Approach 2 | Approach 3 |
|-----------|-------------|------------|------------|------------|
| **p is root** | `p = root`, answer must be `root` | ✅ Path for p = [root], first overlap is root | ✅ Ancestors of p = {root}, q's walk hits root | ✅ `root == p` → return root immediately |
| **p is ancestor of q** | e.g., LCA(5, 4) in example | ✅ Last common in paths is `5` | ✅ Ancestor set of p includes q's walk-up point | ✅ Returns p when found, q never triggers split |
| **p and q are siblings** | Adjacent nodes at same depth | ✅ Paths diverge at parent | ✅ Their ancestor walks converge at parent | ✅ Both sides return non-null at parent |
| **Tree is a single path (skewed)** | All nodes in a line | ✅ Works, paths are long | ✅ Works, more stack/map entries | ⚠️ Stack overflow risk for n=10^5 (recursion depth = n) |
| **p and q are leaf nodes** | Deepest nodes | ✅ Handles | ✅ Handles | ✅ Handles |
| **p == q** | Problem says p ≠ q, but if tested | ✅ Returns p | ✅ Returns p | ✅ Returns p (first base case hit) |
| **Minimum tree (2 nodes)** | Root and one child | ✅ Works | ✅ Works | ✅ Works |

### Critical Warning: Stack Overflow in Approach 3
For **extremely skewed trees** with 10^5 nodes (all left children), Approach 3's recursion depth hits 10^5, which **can cause `StackOverflowError`** in Java. Approach 2 (iterative) is safer in production for such inputs.

---

## 8. Final Summary

### Comparison Table

| Approach | Time | Space | Code Simplicity | Production Safety |
|----------|------|-------|-----------------|-------------------|
| Path Finding | O(n) | O(n) | Medium | ✅ Safe |
| Iterative Parent Map | O(n) | O(n) | Medium | ✅ Safest (no recursion) |
| Recursive DFS | O(n) | O(h) | ✅ Simplest | ⚠️ Risk on skewed trees |

### Recommended Approach
> **Use Approach 3 (Recursive DFS)** in interviews — it's the most elegant and expected solution. For production code with potentially skewed/deep trees, **prefer Approach 2** (iterative with parent map) to avoid stack overflow.

### What to Remember
> **Pattern:** Postorder DFS — process children before parent. The moment **both sides return non-null**, you've found the split point — that's your LCA. This "bubble up and split" pattern appears in many tree problems.

---

## 9. Companies & Frequency

### Where This Problem Has Been Asked

| Company | Frequency | Notes |
|---------|-----------|-------|
| **Meta (Facebook)** | ⭐⭐⭐⭐⭐ Very High | Top 5 most asked tree question |
| **Amazon** | ⭐⭐⭐⭐⭐ Very High | Appears in SDE-1, SDE-2, Senior rounds |
| **Google** | ⭐⭐⭐⭐ High | Common in L4/L5 interviews |
| **Microsoft** | ⭐⭐⭐⭐ High | Frequently asked in coding rounds |
| **LinkedIn** | ⭐⭐⭐ Medium-High | Reported in multiple interview rounds |
| **Apple** | ⭐⭐⭐ Medium | Appears in senior SWE interviews |
| **Bloomberg** | ⭐⭐⭐ Medium | Core DS&A interview question |
| **Uber** | ⭐⭐⭐ Medium | Reported in backend engineer rounds |
| **Adobe** | ⭐⭐ Medium | Appears occasionally |
| **Salesforce** | ⭐⭐ Medium | Reported in senior rounds |

### Overall Appearance Stats
- **LeetCode problem #236** — Marked as **Medium**
- Appeared in **1,000+ reported interview experiences** on LeetCode and Glassdoor
- Consistently in the **Top 20 most asked LeetCode problems** for FAANG interviews
- Part of the canonical **"Blind 75"** and **"NeetCode 150"** problem sets — must-know for any serious interview prep
*/
// @formatter:on
