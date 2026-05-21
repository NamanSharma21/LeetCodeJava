package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import Datastructures.TreeNode;

public class PathSumII {
    public static void main(String[] args) {
        PathSumII pathSumII = new PathSumII();
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.right.left = new TreeNode(5);
        root.right.right.right = new TreeNode(1);
        System.out.println("PathSumII : " + pathSumII.pathSum(root, 22));
        System.out.println("PathSumII : " + pathSumII.pathSumIterativeDFS(root, 22));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/path-sum-ii/description/?envType=problem-list-v2&envId=tree
     * 
     * Given the root of a binary tree and an integer targetSum, return all
     * root-to-leaf paths where the sum of the node values in the path equals
     * targetSum. Each path should be returned as a list of the node values, not
     * node references.
     * 
     * A root-to-leaf path is a path starting from the root and ending at any leaf
     * node. A leaf is a node with no children.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     *           5
     *          / \
     *         4   8
     *        /   / \
     *       11  13  4
     *      / \     / \
     *     7   2   5   1
     * 
     * Valid Paths:
     * ┌─────────────────────────┐
     * │ Path 1: 5 → 4 → 11 → 2  │  Sum: 5+4+11+2 = 22 ✓
     * │ Path 2: 5 → 8 → 4 → 5   │  Sum: 5+8+4+5 = 22 ✓
     * └─────────────────────────┘
     * 
     * 
     * Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
     * Output: [[5,4,11,2],[5,8,4,5]]
     * Explanation: There are two paths whose sum equals targetSum:
     * 5 + 4 + 11 + 2 = 22
     * 5 + 8 + 4 + 5 = 22
     * Example 2:
     * 
     * 
     * Input: root = [1,2,3], targetSum = 5
     * Output: []
     * Example 3:
     * 
     * Input: root = [1,2], targetSum = 0
     * Output: []
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [0, 5000].
     * -1000 <= Node.val <= 1000
     * -1000 <= targetSum <= 1000
     */
    // @formatter:on

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> paths = new ArrayList<>();
        if (root == null)
            return paths;
        dfsHelper(root, targetSum, new ArrayList<>(), paths);
        return paths;
    }

    public void dfsHelper(TreeNode root, int remainder, List<Integer> currentPath,
            List<List<Integer>> paths) {

        currentPath.add(root.val);
        remainder = remainder - root.val;
        if (root.left == null && root.right == null) {
            if (remainder == 0) {
                paths.add(new ArrayList<>(currentPath));
            }
        }
        if (root.left != null)
            dfsHelper(root.left, remainder, currentPath, paths);
        if (root.right != null)
            dfsHelper(root.right, remainder, currentPath, paths);
        currentPath.remove(currentPath.size() - 1);
    }

    public List<List<Integer>> pathSumIterativeDFS(TreeNode root, int targetSum) {
        List<List<Integer>> results = new ArrayList<>();
        if (root == null)
            return results;
        Deque<Object[]> stack = new ArrayDeque<>();
        stack.offer(new Object[] { root, targetSum, new ArrayList<>() });
        while (!stack.isEmpty()) {
            Object[] frame = stack.pop();
            TreeNode node = (TreeNode) frame[0];
            int remaining = (int) frame[1];
            List<Integer> path = new ArrayList<>((List<Integer>) frame[2]);
            path.add(node.val);
            remaining -= node.val;
            boolean isLeaf = node.left == null && node.right == null;
            if (isLeaf && remaining == 0)
                results.add(path);
            if (node.right != null)
                stack.push(new Object[] { node.right, remaining, path });
            if (node.left != null)
                stack.push(new Object[] { node.left, remaining, path });
        }
        return results;
    }
}
// @formatter:off
/*
# Path Sum II — Deep Dive

---

## 1. Problem Statement

### In Plain English
Given the root of a binary tree and an integer `targetSum`, find **all root-to-leaf paths** where the sum of node values along the path equals `targetSum`. Return a list of all such paths, where each path is represented as a list of node values from root to leaf.

### Input Format
- A binary tree root node (`TreeNode root`)
- An integer `targetSum` (can be negative, zero, or positive)

### Output Format
- `List<List<Integer>>` — a list of paths, each path being a list of integers from root to leaf

### Key Constraints
| Constraint | Value |
|---|---|
| Number of nodes | `[0, 5000]` |
| Node values | `[-1000, 1000]` |
| targetSum range | `[-1000, 1000]` |

### Precise Definition
- A **leaf** is a node with **no left and no right child**
- The path must go from **root to leaf** — partial paths don't count
- You must return **all** such paths, not just one

---

## 2. Intuition

### Core Idea
Think of yourself walking down a tree from the root, carrying a running sum. At each node, you add its value to your running total. When you reach a leaf, you check: does the total equal `targetSum`? If yes, record the path you took.

### How a Human Reasons About It
1. Start at the root with an empty path and `currentSum = 0`
2. At each node, add the node's value to `currentSum` and the node to the path
3. If you're at a leaf AND `currentSum == targetSum` → save this path
4. Otherwise, keep going deeper (left and right subtrees)
5. When you backtrack (return from a recursive call), **undo** the last step — remove the node from the path

### What Makes It Tricky
- **Backtracking** is the key mechanism: you must "undo" path additions when retreating up the tree
- Shallow copy vs reference bugs: if you add the same list object to results without copying it, all results will point to the same (eventually empty) list
- Negative node values mean you can't prune early (a path that overshoots might come back down)

---

## 3. Approach Overview

| # | Approach | Key Idea | Best For | Optimal? |
|---|---|---|---|---|
| 1 | **DFS + Backtracking** | Recursive DFS, build path, backtrack on return | ✅ Interviews, all cases | ✅ Yes |
| 2 | **Iterative DFS (Stack)** | Explicit stack simulating recursion | When recursion depth is a concern | ⚠️ More complex code |
| 3 | **BFS (Queue)** | Level-by-level traversal tracking paths | Conceptually alternative | ❌ Higher memory, not preferred |

**Recommended: Approach 1 — DFS + Backtracking**
- Clean, readable, interview-friendly
- Natural fit for tree path problems
- O(N) time, and backtracking avoids extra memory per branch

---

## 4. Detailed Solutions in Java

### ✅ Approach 1 — DFS + Backtracking (Optimal)

#### Algorithm Step-by-Step
1. Handle base case: if `root == null`, return empty list
2. Use a helper method with: current node, remaining sum, current path (mutable list), and results list
3. At each node:
   - Add node value to `currentPath`
   - Subtract node value from `remainingSum`
   - If it's a **leaf** and `remainingSum == 0` → add a **copy** of `currentPath` to results
   - Otherwise, recurse on left and right children
4. **Backtrack**: remove the last element from `currentPath` before returning

```java
import java.util.*;

class Solution {
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> results = new ArrayList<>();
        if (root == null) return results;

        dfs(root, targetSum, new ArrayList<>(), results);
        return results;
    }

    private void dfs(TreeNode node, int remainingSum,
                     List<Integer> currentPath, List<List<Integer>> results) {
        // Add current node to path
        currentPath.add(node.val);
        remainingSum -= node.val;

        // Check if it's a leaf and path sum matches
        boolean isLeaf = (node.left == null && node.right == null);
        if (isLeaf && remainingSum == 0) {
            results.add(new ArrayList<>(currentPath)); // deep copy!
        }

        // Recurse on children
        if (node.left != null)  dfs(node.left,  remainingSum, currentPath, results);
        if (node.right != null) dfs(node.right, remainingSum, currentPath, results);

        // Backtrack: remove current node from path
        currentPath.remove(currentPath.size() - 1);
    }
}
```

---

### Approach 2 — Iterative DFS with Stack

#### Algorithm Step-by-Step
1. Push `(node, remainingSum, pathSoFar)` tuples onto a stack
2. At each step, pop a tuple and check if it's a valid leaf path
3. Push children with updated sum and path
4. No backtracking needed since each stack frame has its own path copy

```java
import java.util.*;

class Solution {
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> results = new ArrayList<>();
        if (root == null) return results;

        // Stack holds: [node, remainingSum, currentPath]
        Deque<Object[]> stack = new ArrayDeque<>();
        stack.push(new Object[]{root, targetSum, new ArrayList<Integer>()});

        while (!stack.isEmpty()) {
            Object[] frame   = stack.pop();
            TreeNode node    = (TreeNode) frame[0];
            int remaining    = (int) frame[1];
            List<Integer> path = new ArrayList<>((List<Integer>) frame[2]);

            path.add(node.val);
            remaining -= node.val;

            boolean isLeaf = (node.left == null && node.right == null);
            if (isLeaf && remaining == 0) {
                results.add(path);
            }

            // Push children (each gets its own copy of path)
            if (node.right != null) stack.push(new Object[]{node.right, remaining, path});
            if (node.left  != null) stack.push(new Object[]{node.left,  remaining, path});
        }
        return results;
    }
}
```

---

### Approach 3 — BFS with Queue (For Completeness)

```java
import java.util.*;

class Solution {
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> results = new ArrayList<>();
        if (root == null) return results;

        // Each queue entry: [node, remainingSum, pathSoFar]
        Queue<Object[]> queue = new LinkedList<>();
        queue.offer(new Object[]{root, targetSum, new ArrayList<Integer>()});

        while (!queue.isEmpty()) {
            Object[] frame   = queue.poll();
            TreeNode node    = (TreeNode) frame[0];
            int remaining    = (int) frame[1];
            List<Integer> path = new ArrayList<>((List<Integer>) frame[2]);

            path.add(node.val);
            remaining -= node.val;

            if (node.left == null && node.right == null && remaining == 0) {
                results.add(path);
            }
            if (node.left  != null) queue.offer(new Object[]{node.left,  remaining, new ArrayList<>(path)});
            if (node.right != null) queue.offer(new Object[]{node.right, remaining, new ArrayList<>(path)});
        }
        return results;
    }
}
```

---

## 5. Time & Space Complexity

### Approach 1 — DFS + Backtracking

| Metric | Complexity | Reasoning |
|---|---|---|
| **Time** | O(N²) worst case | We visit each node once — O(N). Copying a path at a leaf takes O(path length). In a skewed tree, path length = N, and there can be N/2 leaves → O(N²) in the worst case |
| **Space** | O(N) | Recursion stack depth = height of tree. In balanced tree O(log N), skewed O(N). The `currentPath` list holds at most O(N) elements |

**Walk-through with numbers:**
- 5000 nodes, balanced tree → height ≈ 13 → ~5000 node visits, path copies O(13) each → very fast
- 5000 nodes, skewed tree → height = 5000 → path copies up to O(5000) → ~12.5M operations worst case

### Approach 2 — Iterative DFS

| Metric | Complexity | Reasoning |
|---|---|---|
| **Time** | O(N²) | Same as above — each node visited once, path copied per node pushed |
| **Space** | O(N²) | Each stack frame holds its **own copy** of the path → significantly more memory than backtracking |

### Approach 3 — BFS

| Metric | Complexity | Reasoning |
|---|---|---|
| **Time** | O(N²) | Same reasoning |
| **Space** | O(N²) | Queue can hold all nodes at the widest level, each carrying its own path copy |

> 💡 **Key insight**: Approach 1 wins on space because backtracking reuses one single list rather than creating copies at every node.

---

## 6. Complete Worked Examples

### Example for Approach 1 (DFS + Backtracking)

```
Tree:
        5
       / \
      4   8
     /   / \
    11  13   4
   /  \       \
  7    2       1

targetSum = 22
```

#### Step-by-step Trace

| Step | Node | remainingSum | currentPath | Action |
|---|---|---|---|---|
| 1 | 5 | 22→17 | [5] | recurse left |
| 2 | 4 | 17→13 | [5,4] | recurse left |
| 3 | 11 | 13→2 | [5,4,11] | recurse left |
| 4 | 7 | 2→-5 | [5,4,11,7] | leaf, sum≠0, backtrack |
| 5 | — | — | [5,4,11] | removed 7 |
| 6 | 2 | 2→0 | [5,4,11,2] | leaf, sum=0 ✅ → save [5,4,11,2] |
| 7 | — | — | [5,4,11] | backtrack, remove 2 |
| 8 | — | — | [5,4] | backtrack, remove 11 |
| 9 | — | — | [5] | backtrack, remove 4 |
| 10 | 8 | 17→9 | [5,8] | recurse left |
| 11 | 13 | 9→-4 | [5,8,13] | leaf, sum≠0, backtrack |
| 12 | — | — | [5,8] | removed 13 |
| 13 | 4 | 9→5 | [5,8,4] | recurse right |
| 14 | 1 | 5→4 | [5,8,4,1] | leaf, sum≠0, backtrack |
| 15 | — | — | [5,8] | fully backtracked |

**Final Output:** `[[5,4,11,2]]`

---

### Example with Multiple Valid Paths

```
Tree:
      1
     / \
    2   3
   / \
  4   5

targetSum = 7
```

| Step | Node | Remaining | Path | Action |
|---|---|---|---|---|
| 1 | 1 | 6 | [1] | recurse |
| 2 | 2 | 4 | [1,2] | recurse |
| 3 | 4 | 0 | [1,2,4] | leaf ✅ save |
| 4 | 5 | -1 | [1,2,5] | leaf ❌ |
| 5 | 3 | 3 | [1,3] | leaf ❌ |

**Output:** `[[1,2,4]]`

---

## 7. Edge Cases

| Edge Case | Input | Expected | How Approach 1 Handles It |
|---|---|---|---|
| **Null root** | `root = null, target = 0` | `[]` | Early return before DFS |
| **Single node = target** | `root = [5], target = 5` | `[[5]]` | Node is leaf, remainingSum = 0 → saved ✅ |
| **Single node ≠ target** | `root = [5], target = 3` | `[]` | Leaf but remainingSum ≠ 0 → not saved ✅ |
| **All negatives** | `root = [-3,-1,-2], target = -4` | `[[-3,-1]]` | No pruning issues; works correctly ✅ |
| **target = 0** | Valid tree | Paths summing to 0 | Works; remainingSum correctly reaches 0 ✅ |
| **Deep skewed tree** | 5000-node chain | One path if sum matches | Stack depth = 5000 — may hit Java default stack limit ⚠️ |
| **Forgetting deep copy** | Any tree | Wrong results | `new ArrayList<>(currentPath)` ensures correctness ✅ |
| **No valid paths** | Any tree | `[]` | Results list stays empty; returned correctly ✅ |

### ⚠️ Stack Overflow Risk
For Approach 1 with a completely skewed tree of 5000 nodes, Java's default thread stack size (~512KB) may overflow. Mitigation:
- Use Approach 2 (iterative) for production systems with extreme input sizes
- Or increase JVM stack size with `-Xss` flag

---

## 8. Final Summary

### Comparison Table

| Approach | Time | Space | Code Simplicity | Recommended? |
|---|---|---|---|---|
| DFS + Backtracking | O(N²) | O(N) | ⭐⭐⭐ Cleanest | ✅ Yes |
| Iterative DFS | O(N²) | O(N²) | ⭐⭐ Moderate | For deep trees |
| BFS | O(N²) | O(N²) | ⭐⭐ Verbose | ❌ Not preferred |

### What to Remember
> **This is the canonical backtracking-on-tree pattern.** Add → recurse → remove. Always copy the path before saving to results. This pattern appears in dozens of tree and graph problems.

---

## 9. Companies & Frequency

| Company | Frequency | Notes |
|---|---|---|
| **Amazon** | ⭐⭐⭐⭐⭐ Very High | Top asked tree problem |
| **Microsoft** | ⭐⭐⭐⭐ High | Common in coding rounds |
| **Facebook / Meta** | ⭐⭐⭐⭐ High | Appears frequently |
| **Google** | ⭐⭐⭐ Medium | More advanced variants asked |
| **Bloomberg** | ⭐⭐⭐ Medium | Classic DFS question |
| **Adobe** | ⭐⭐⭐ Medium | Interview rounds |
| **Apple** | ⭐⭐ Medium | Occasional appearances |
| **Uber** | ⭐⭐ Low-Medium | Seen in phone screens |

**LeetCode Stats (Problem #113):**
- Appeared in **600+ reported interview questions**
- Difficulty: **Medium**
- Acceptance rate: ~~54%~~
- Part of the **"Top Interview 150"** and **"Blind 75"** lists

> 🎯 **Interview Tip:** When asked this problem, immediately mention the backtracking pattern, explain why you need `new ArrayList<>(currentPath)` when saving, and discuss the edge case of a null root. These three points show depth of understanding and often set you apart.
*/
// @formatter:on