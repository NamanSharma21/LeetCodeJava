package Trees;

import java.util.ArrayDeque;
import java.util.Deque;

import Datastructures.TreeNode;

public class MaximumDepthofBinaryTree {
    public static void main(String[] args) {
        MaximumDepthofBinaryTree maximumDepthofBinaryTree = new MaximumDepthofBinaryTree();

        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);

        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out
                .println("MaximumDepthofBinaryTree : " + maximumDepthofBinaryTree.maxDepthReccursiveDFSPostOrder(root));
        System.out.println("MaximumDepthofBinaryTree : " + maximumDepthofBinaryTree.maxDepthIterativeBFS(root));
    }

    /*
     * Given the root of a binary tree, return its maximum depth.
     * 
     * A binary tree's maximum depth is the number of nodes along the longest path
     * from the root node down to the farthest leaf node.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: root = [3,9,20,null,null,15,7]
     * Output: 3
     * Example 2:
     * 
     * Input: root = [1,null,2]
     * Output: 2
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [0, 104].
     * -100 <= Node.val <= 100
     */

    public int maxDepthReccursiveDFSPostOrder(TreeNode root) {
        if (root == null)
            return 0;

        int leftSubTreeLength = maxDepthReccursiveDFSPostOrder(root.left);
        int rightSubTreeLength = maxDepthReccursiveDFSPostOrder(root.right);
        return 1 + Math.max(leftSubTreeLength, rightSubTreeLength);
    }

    public int maxDepthIterativeBFS(TreeNode root) {
        if (root == null)
            return 0;
        Deque<TreeNode> q = new ArrayDeque<>();
        q.offer(root);

        int depth = 0;
        while (!q.isEmpty()) {
            int queLength = q.size();
            for (int i = 0; i < queLength; i++) {
                TreeNode curr = q.poll();
                if (curr.left != null)
                    q.offer(curr.left);
                if (curr.right != null)
                    q.offer(curr.right);
            }
            depth++;
        }
        return depth;
    }


    /*
        # Maximum Depth of Binary Tree — Deep Dive

---

## 1. Problem Statement

### In Plain Words
Given a binary tree, find its **maximum depth** — the number of nodes along the longest path from the root node down to the farthest leaf node.

### Input Format
- A binary tree represented by its root node (`TreeNode root`)
- Each `TreeNode` has: `int val`, `TreeNode left`, `TreeNode right`
- The tree can be empty (`root == null`)

### Output Format
- A single `int` representing the maximum depth

### Constraints
- Number of nodes: `0 to 10,000`
- Node values: `-100 to 100` (irrelevant to this problem — depth doesn't depend on values)
- Depth is counted in **nodes**, not edges

### What Exactly Needs to Be Computed
> The length (in nodes) of the longest root-to-leaf path.

```
        3          ← depth 1
       / \
      9  20        ← depth 2
        /  \
       15   7      ← depth 3

Answer: 3
```

---

## 2. Intuition

### The Core Idea
A binary tree is **recursive by nature** — every subtree is itself a binary tree. So the depth of a tree is simply:

```
depth(tree) = 1 + max(depth(left subtree), depth(right subtree))
```

### How a Human Reasons About It
1. Stand at the root. You want the longest path downward.
2. You can only go left or right — so ask: *"Which side is deeper?"*
3. Whatever the deeper side returns, add 1 (for the current node).
4. Repeat this reasoning at every node — that's recursion.

### What Makes It Interesting
- It's a **gateway problem** to tree traversals — mastering this unlocks DFS, BFS, and DP on trees.
- The recursive solution is elegant but uses **implicit stack space**.
- The iterative BFS solution builds strong intuition for **level-order traversal**.

---

## 3. Approach Overview

| # | Approach | Key Idea | Best For | Optimal? |
|---|----------|----------|----------|----------|
| 1 | Recursive DFS | Post-order: solve children first, combine | Interviews, clean code | ✅ Yes (tied) |
| 2 | Iterative BFS (Level Order) | Count levels using a queue | When stack overflow is a concern | ✅ Yes (tied) |
| 3 | Iterative DFS (Explicit Stack) | Simulate recursion with a stack | Avoiding system call stack | ✅ Yes (tied) |

All three are **O(n) time** and **O(n) space** in the worst case. The recursive DFS is the most concise and interview-preferred. BFS is the most intuitive for "depth = number of levels."

---

## 4. Detailed Solutions in Java

### Setup: The TreeNode Class
```java
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) { this.val = val; }
}
```

---

### ✅ Approach 1: Recursive DFS (Post-Order)

#### Algorithm — Step by Step
1. **Base case:** If the current node is `null`, return `0` (empty tree has depth 0).
2. **Recurse left:** Get the max depth of the left subtree.
3. **Recurse right:** Get the max depth of the right subtree.
4. **Combine:** Return `1 + max(leftDepth, rightDepth)` — 1 accounts for the current node.

This is a **post-order** traversal: you process children before the parent.

```java
class Solution {
    public int maxDepth(TreeNode root) {
        // Base case: empty tree or leaf's child
        if (root == null) return 0;

        int leftDepth  = maxDepth(root.left);   // depth of left subtree
        int rightDepth = maxDepth(root.right);  // depth of right subtree

        // Current node adds 1 level on top of the deeper subtree
        return 1 + Math.max(leftDepth, rightDepth);
    }
}
```

---

### ✅ Approach 2: Iterative BFS (Level-Order Traversal)

#### Algorithm — Step by Step
1. If root is null, return 0.
2. Use a `Queue<TreeNode>`. Add root to start.
3. **Each iteration of the outer while-loop = one level of the tree.**
4. Process all nodes at the current level (inner for-loop), enqueue their children.
5. Increment `depth` after processing each level.
6. Return `depth` when the queue is empty.

```java
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // number of nodes at current level

            // Process every node at this level
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();

                if (current.left  != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }

            depth++; // finished processing one full level
        }

        return depth;
    }
}
```

---

### ✅ Approach 3: Iterative DFS (Explicit Stack)

#### Algorithm — Step by Step
1. Use a stack that stores **pairs**: `(node, currentDepth)`.
2. Push `(root, 1)` initially.
3. While the stack is not empty:
   - Pop a node and its depth.
   - Update `maxDepth` if this depth is greater.
   - Push left and right children with `currentDepth + 1`.
4. Return `maxDepth`.

```java
import java.util.Stack;

class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;

        Stack<TreeNode> nodeStack  = new Stack<>();
        Stack<Integer>  depthStack = new Stack<>();

        nodeStack.push(root);
        depthStack.push(1);

        int maxDepth = 0;

        while (!nodeStack.isEmpty()) {
            TreeNode current      = nodeStack.pop();
            int      currentDepth = depthStack.pop();

            maxDepth = Math.max(maxDepth, currentDepth);

            // Push children with incremented depth
            if (current.left  != null) {
                nodeStack.push(current.left);
                depthStack.push(currentDepth + 1);
            }
            if (current.right != null) {
                nodeStack.push(current.right);
                depthStack.push(currentDepth + 1);
            }
        }

        return maxDepth;
    }
}
```

---

## 5. Time & Space Complexity

### Approach 1 — Recursive DFS

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | Every node is visited exactly once |
| **Space** | O(h) where h = height | Call stack depth = height of tree. Worst case (skewed tree): O(n). Best case (balanced): O(log n) |

**Walk-through example:** Tree with 7 nodes, balanced → height = 3 → ~3 stack frames deep at any point.

---

### Approach 2 — Iterative BFS

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | Each node enqueued and dequeued exactly once |
| **Space** | O(w) where w = max width | Queue holds at most one full level at a time. Worst case: O(n/2) = O(n) for a complete binary tree's last level |

**Walk-through example:** A complete tree with 1000 nodes — the bottom level has ~500 nodes, so the queue holds ~500 nodes at peak.

---

### Approach 3 — Iterative DFS

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | Every node pushed and popped once |
| **Space** | O(h) | Stack holds at most one path from root to deepest node |

---

## 6. Complete Worked Examples

### Example Tree
```
        1
       / \
      2   3
     / \
    4   5
```
Expected output: **3**

---

### Approach 1 — Recursive DFS Trace

```
maxDepth(1)
  ├── maxDepth(2)
  │     ├── maxDepth(4)
  │     │     ├── maxDepth(null) → 0
  │     │     └── maxDepth(null) → 0
  │     │   returns 1 + max(0,0) = 1
  │     ├── maxDepth(5)
  │     │     ├── maxDepth(null) → 0
  │     │     └── maxDepth(null) → 0
  │     │   returns 1 + max(0,0) = 1
  │     returns 1 + max(1,1) = 2
  ├── maxDepth(3)
  │     ├── maxDepth(null) → 0
  │     └── maxDepth(null) → 0
  │   returns 1 + max(0,0) = 1
  returns 1 + max(2,1) = 3  ✅
```

---

### Approach 2 — BFS Level-Order Trace

| Iteration | Queue Before | Level Size | Nodes Processed | Queue After | Depth |
|-----------|-------------|------------|-----------------|-------------|-------|
| 1 | [1] | 1 | node 1 → enqueue 2, 3 | [2, 3] | 1 |
| 2 | [2, 3] | 2 | node 2 → enqueue 4, 5; node 3 → no children | [4, 5] | 2 |
| 3 | [4, 5] | 2 | node 4 → no children; node 5 → no children | [] | 3 |

Queue empty → return **3** ✅

---

### Approach 3 — Iterative DFS Trace

| Step | Action | nodeStack | depthStack | maxDepth |
|------|--------|-----------|------------|----------|
| Init | Push (1,1) | [1] | [1] | 0 |
| 1 | Pop (1,1), push children | [2,3] | [2,2] | 1 |
| 2 | Pop (3,2), no children | [2] | [2] | 2 |
| 3 | Pop (2,2), push children | [4,5] | [3,3] | 2 |
| 4 | Pop (5,3), no children | [4] | [3] | 3 |
| 5 | Pop (4,3), no children | [] | [] | 3 |

Stack empty → return **3** ✅

---

## 7. Edge Cases

| Edge Case | Input | Expected | How Each Approach Handles It |
|-----------|-------|----------|------------------------------|
| **Empty tree** | `root = null` | `0` | All 3: immediate null check returns 0 |
| **Single node** | Just root, no children | `1` | Recursive: `1 + max(0,0) = 1` ✅ |
| **Left-skewed tree** | Each node has only a left child | `n` | All work; recursive uses O(n) stack — **risk of StackOverflow for n=10,000** |
| **Right-skewed tree** | Each node has only a right child | `n` | Same as above |
| **Perfect binary tree** | All levels full | `log₂(n+1)` | BFS queue holds n/2 nodes at peak — high memory |
| **Two nodes** | Root + one child | `2` | All handle correctly |
| **Negative values** | Values like -100 | Unaffected | Depth doesn't use node values |

### ⚠️ Important Warning
For **skewed trees with 10,000 nodes**, the **recursive approach may throw a `StackOverflowError`** in Java, since the default stack depth is limited. In production or with strict constraints, prefer the **iterative BFS or DFS** approach.

---

## 8. Final Summary

| Approach | Time | Space | Code Simplicity | Interview Preference |
|----------|------|-------|-----------------|----------------------|
| Recursive DFS | O(n) | O(h) | ⭐⭐⭐ Cleanest | ✅ Most common answer |
| Iterative BFS | O(n) | O(w) | ⭐⭐ Clear | ✅ Great follow-up |
| Iterative DFS | O(n) | O(h) | ⭐ More verbose | Good to know |

### 🎯 Recommendation
**Start with Recursive DFS** in interviews — it's concise, idiomatic, and demonstrates understanding of tree recursion. **Mention BFS as an alternative** to show awareness of the level-order pattern and stack overflow risk.

### 💡 What to Remember
> **Binary trees and recursion are natural partners** — any problem asking for a property of a tree can often be solved by combining results from left and right subtrees. The pattern `return 1 + f(left, right)` appears again and again in tree problems (diameter, height, path sum, etc.)
    
    */
}
