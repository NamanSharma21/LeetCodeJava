package Trees;

import java.util.LinkedList;
import java.util.Queue;

import Datastructures.TreeNode;

public class SymmetricTree {
    public static void main(String[] args) {
        SymmetricTree symmetricTree = new SymmetricTree();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(3);
        System.out.println("IsSymmetric : " + symmetricTree.isSymmetricIterativeBFS(root));

        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(2);
        root1.left.right = new TreeNode(3);
        root1.right.right = new TreeNode(3);
        System.out.println("IsSymmetric : " + symmetricTree.isSymmetricIterativeBFS(root1));
    }
    /*
     * Given the root of a binary tree, check whether it is a mirror of itself
     * (i.e., symmetric around its center).
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: root = [1,2,2,3,4,4,3]
     * Output: true
     * Example 2:
     * 
     * 
     * Input: root = [1,2,2,null,3,null,3]
     * Output: false
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [1, 1000].
     * -100 <= Node.val <= 100
     * 
     * 
     * Follow up: Could you solve it both recursively and iteratively?
     */

    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        return isSymmetricHelper(root.left, root.right);
    }

    public boolean isSymmetricHelper(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        System.out.println("" + left.val + " --- " + right.val);
        return left.val == right.val && isSymmetricHelper(left.left, right.right)
                && isSymmetricHelper(left.right, right.left);
    }

    public boolean isSymmetricIterativeBFS(TreeNode root) {
        if (root == null)
            return true;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root.left);
        queue.offer(root.right);

        while (!queue.isEmpty()) {
            TreeNode left = queue.poll();
            TreeNode right = queue.poll();
            if (left == null && right == null)
                continue;
            if (left == null || right == null)
                return false;
            if (left.val != right.val)
                return false;
            queue.offer(left.left);
            queue.offer(right.right);
            queue.offer(left.right);
            queue.offer(right.left);
        }
        return true;
    }


    /*
    
    # Symmetric Tree — Deep Dive

---

## 1. Problem Statement

### In Plain Words
Given the root of a binary tree, determine whether the tree is a **mirror image of itself** around its center (i.e., symmetric about the root axis).

### Input Format
- A binary tree root node of type `TreeNode`
- `TreeNode` has: `int val`, `TreeNode left`, `TreeNode right`
- Number of nodes: `1 ≤ n ≤ 1000`
- Node values: `-100 ≤ val ≤ 100`

### Output Format
- Return `true` if the tree is symmetric, `false` otherwise

### What Exactly Needs to Be Computed
A tree is symmetric if the **left subtree is a mirror of the right subtree**. Mirroring means:
- The roots of the two subtrees have equal values
- The left child of the left subtree mirrors the right child of the right subtree
- The right child of the left subtree mirrors the left child of the right subtree

```
Symmetric:          Not Symmetric:
      1                   1
     / \                 / \
    2   2               2   2
   / \ / \               \   \
  3  4 4  3               3   3
```

---

## 2. Intuition

### The Core Idea
Imagine folding the tree in half along a vertical axis through the root. If the left and right halves perfectly overlap (same values at every mirrored position), the tree is symmetric.

### How a Human Reasons About It
1. Start at the root — it has a left child and a right child
2. Ask: "Does the left subtree mirror the right subtree?"
3. To check mirroring, compare two nodes simultaneously:
   - Their **values must match**
   - The **outer children** must mirror each other (left.left ↔ right.right)
   - The **inner children** must mirror each other (left.right ↔ right.left)
4. Keep descending both sides in tandem until you hit `null` or a mismatch

### What Makes This Tricky
- You can't just compare left and right subtrees for **equality** — you need **mirrored equality**, which requires a different traversal pairing
- Handling `null` nodes carefully: two `null`s are symmetric, but one `null` and one non-`null` are not
- Both **recursive** (DFS) and **iterative** (BFS/queue) solutions are elegant and worth knowing

---

## 3. Approach Overview

| # | Approach | Key Idea | Best For |
|---|----------|----------|----------|
| 1 | **Recursive DFS** | Mirror-check two nodes simultaneously using recursion | Interviews — clean and elegant |
| 2 | **Iterative BFS (Queue)** | Use a queue to compare node pairs level by level | When stack overflow is a concern |
| 3 | **Iterative DFS (Stack)** | Same as BFS but uses a stack instead of a queue | Alternative iterative style |

### ✅ Recommended: Approach 1 (Recursive DFS)
It is the most concise, readable, and interview-friendly. The recursion naturally models the mirror structure of the problem. All three approaches have the same O(n) time and O(n) space, so clarity wins.

---

## 4. Detailed Solutions in Java

### TreeNode Definition (assumed throughout)
```java
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val) { this.val = val; }
}
```

---

### ✅ Approach 1 — Recursive DFS (Optimal & Recommended)

#### Algorithm Step-by-Step
1. Call a helper `isMirror(left, right)` with `root.left` and `root.right`
2. **Base cases:**
   - Both nodes are `null` → symmetric here → return `true`
   - Exactly one is `null` → asymmetric → return `false`
3. **Recursive case:**
   - Values must match: `left.val == right.val`
   - Outer children must mirror: `isMirror(left.left, right.right)`
   - Inner children must mirror: `isMirror(left.right, right.left)`
   - All three must be `true`

```java
class Solution {
    public boolean isSymmetric(TreeNode root) {
        // A single-node tree is always symmetric
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode left, TreeNode right) {
        // Both absent: this branch is symmetric
        if (left == null && right == null) return true;
        // One absent, one present: asymmetric
        if (left == null || right == null) return false;

        // Values must match, then check outer and inner children
        return (left.val == right.val)
            && isMirror(left.left, right.right)   // outer pair
            && isMirror(left.right, right.left);  // inner pair
    }
}
```

---

### Approach 2 — Iterative BFS (Queue)

#### Algorithm Step-by-Step
1. Use a `Queue<TreeNode>` (allow nulls — use `LinkedList`)
2. Initially enqueue `root.left` and `root.right` as the first pair
3. Each iteration: dequeue **two nodes** at a time as a pair
4. Apply the same null/value checks as the recursive approach
5. Enqueue children in mirror order:
   - `left.left` and `right.right` (outer pair)
   - `left.right` and `right.left` (inner pair)
6. If queue empties without mismatch → return `true`

```java
class Solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root.left);
        queue.offer(root.right);

        while (!queue.isEmpty()) {
            TreeNode left = queue.poll();
            TreeNode right = queue.poll();

            // Both null: this pair is fine, continue
            if (left == null && right == null) continue;
            // One null or value mismatch: not symmetric
            if (left == null || right == null) return false;
            if (left.val != right.val) return false;

            // Enqueue children in mirror order
            queue.offer(left.left);
            queue.offer(right.right);
            queue.offer(left.right);
            queue.offer(right.left);
        }

        return true;
    }
}
```

---

### Approach 3 — Iterative DFS (Stack)

#### Algorithm Step-by-Step
- Identical logic to BFS, but uses a `Deque` (stack) instead of a queue
- Processes pairs in LIFO order instead of FIFO — doesn't affect correctness, only traversal order

```java
class Solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root.left);
        stack.push(root.right);

        while (!stack.isEmpty()) {
            TreeNode right = stack.pop();
            TreeNode left = stack.pop();

            if (left == null && right == null) continue;
            if (left == null || right == null) return false;
            if (left.val != right.val) return false;

            // Push children in mirror order (as pairs)
            stack.push(left.left);
            stack.push(right.right);
            stack.push(left.right);
            stack.push(right.left);
        }

        return true;
    }
}
```

---

## 5. Time & Space Complexity

### Approach 1 — Recursive DFS

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | Every node is visited exactly once; each call processes one node pair |
| **Space** | O(h) where h = tree height | Recursion call stack depth equals tree height. O(log n) balanced, O(n) skewed |

**Example:** Tree with 7 nodes, height 3 → ~3 stack frames deep at most

---

### Approach 2 — Iterative BFS (Queue)

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | Each node is enqueued and dequeued exactly once |
| **Space** | O(w) where w = max width | Queue holds at most one full level of nodes. O(n) in worst case (complete tree) |

**Example:** A complete tree with 1000 nodes — bottom level has ~500 nodes → queue holds ~500 pairs at peak

---

### Approach 3 — Iterative DFS (Stack)

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | Same as BFS — every node processed once |
| **Space** | O(h) | Stack depth bounded by tree height, same as recursion |

---

## 6. Complete Worked Examples

### Example 1 — Symmetric Tree `[1,2,2,3,4,4,3]`

```
        1
       / \
      2   2
     / \ / \
    3  4 4  3
```

**Using Approach 1 (Recursive DFS):**

```
isSymmetric(root=1)
  └─ isMirror(node2_L, node2_R)
        ├─ Both non-null ✓, values 2==2 ✓
        ├─ isMirror(node3_L, node3_R)   ← outer pair
        │     ├─ Both non-null ✓, values 3==3 ✓
        │     ├─ isMirror(null, null) → true ✓
        │     └─ isMirror(null, null) → true ✓
        │     └─ RETURN true
        └─ isMirror(node4_L, node4_R)   ← inner pair
              ├─ Both non-null ✓, values 4==4 ✓
              ├─ isMirror(null, null) → true ✓
              └─ isMirror(null, null) → true ✓
              └─ RETURN true
  └─ RETURN true ✅
```

---

### Example 2 — Asymmetric Tree `[1,2,2,null,3,null,3]`

```
        1
       / \
      2   2
       \   \
        3   3
```

**Using Approach 2 (Iterative BFS):**

| Step | Queue State (pairs) | Action |
|------|--------------------|-----------------------------|
| Init | `[2L, 2R]` | Enqueue root's children |
| 1 | `[null, null, 3L, 3R]` | Poll 2L,2R → match ✓ → enqueue outer: (null,null), inner: (3L,3R) |
| 2 | `[3L, 3R]` | Poll null,null → both null → `continue` |
| 3 | `[]` | Poll 3L (left.right=3) and 3R (right.left=null) |
| | | `3L != null` but `3R == null` → **return false** ❌ |

Wait — let me trace the exact enqueueing:
- When processing `2L` and `2R`:
  - `left.left = null`, `right.right = null` → enqueue (null, null)
  - `left.right = 3L`, `right.left = null` → enqueue **(3L, null)**
- Dequeue **(3L, null)** → one is null → **return `false`** ✅

---

### Example 3 — Single Node `[1]`

```
    1
```
- `root.left = null`, `root.right = null`
- `isMirror(null, null)` → both null → return `true` ✅

---

## 7. Edge Cases

| Edge Case | Description | How It's Handled |
|-----------|-------------|-----------------|
| `root == null` | Empty tree | Early return `true` (all approaches) |
| Single node | Only root, no children | `isMirror(null, null)` → `true` |
| All same values e.g. `[2,2,2,2,2,2,2]` | Values match but structure could differ | Structure is checked via child pairing, not just values |
| Skewed tree (only left or only right) | One side always null | Null checks catch this immediately |
| Negative values e.g. `[-1,-2,-2]` | No special handling needed | `==` comparison works for all int values |
| Large tree (n=1000) | Deep recursion possible | Recursive: O(1000) stack depth — safe. Iterative avoids risk entirely |
| Mirror values, wrong structure e.g. `[1,2,2,2,null,2]` | Values seem fine but positions differ | Mirror pairing (outer/inner) catches structural asymmetry |

**Risk Note:** For extremely deep skewed trees (e.g., n = 10,000+ in a linked-list shape), the recursive approach *could* cause `StackOverflowError`. The iterative approaches are safer in production systems.

---

## 8. Final Summary

| Approach | Time | Space | Readability | Recommended? |
|----------|------|-------|-------------|--------------|
| Recursive DFS | O(n) | O(h) | ⭐⭐⭐⭐⭐ | ✅ Yes — interviews |
| Iterative BFS | O(n) | O(w) | ⭐⭐⭐⭐ | ✅ For large/skewed trees |
| Iterative DFS | O(n) | O(h) | ⭐⭐⭐⭐ | ✅ Alternative iterative |

### What to Remember
> **Pattern:** Whenever a problem asks about tree symmetry or mirroring, think "compare two nodes simultaneously" — one traversing left-first, one traversing right-first. This "dual traversal" pattern also appears in problems like *Same Tree*, *Invert Binary Tree*, and *Path Sum*.

> **Key Insight:** The recursive solution is beautiful because the structure of the code **mirrors** the structure of the problem — the function calls itself twice, once for the outer pair and once for the inner pair, exactly reflecting the definition of tree symmetry.
    
    */
}
