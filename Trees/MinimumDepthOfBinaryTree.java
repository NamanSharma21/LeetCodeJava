package Trees;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

import Datastructures.TreeNode;

public class MinimumDepthOfBinaryTree {
    public static void main(String[] args) {
        MinimumDepthOfBinaryTree minimumDepthOfBinaryTree = new MinimumDepthOfBinaryTree();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.left.right = new TreeNode(7);
        System.out.println("MinimumDepthOfBinaryTree : " + minimumDepthOfBinaryTree.minDepth(root));
        System.out.println("MinimumDepthOfBinaryTree : " + minimumDepthOfBinaryTree.minDepthDFSIterativeStack(root));
        System.out.println("MinimumDepthOfBinaryTree : " + minimumDepthOfBinaryTree.minDepthBFSIterative(root));
    }

    /*
     * https://leetcode.com/problems/minimum-depth-of-binary-tree/description/?
     * envType=problem-list-v2&envId=tree
     * 
     * Given a binary tree, find its minimum depth.
     * 
     * The minimum depth is the number of nodes along the shortest path from the
     * root node down to the nearest leaf node.
     * 
     * Note: A leaf is a node with no children.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: root = [3,9,20,null,null,15,7]
     * Output: 2
     * Example 2:
     * 
     * Input: root = [2,null,3,null,4,null,5,null,6]
     * Output: 5
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [0, 105].
     * -1000 <= Node.val <= 1000
     * 
     */

    public int minDepth(TreeNode root) {
        if (root == null)
            return 0;
        if (root.left == null && root.right == null)
            return 1;
        if (root.left == null)
            return 1 + minDepth(root.right);
        if (root.right == null)
            return 1 + minDepth(root.left);
        int leftHeight = minDepth(root.left);
        int rightHeight = minDepth(root.left);
        return 1 + Math.min(leftHeight, rightHeight);
    }

    public int minDepthDFSIterativeStack(TreeNode root) {
        if (root == null)
            return 0;
        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<int[]> depthStack = new Stack<>();
        nodeStack.push(root);
        depthStack.push(new int[] { 1 });
        int minDepth = Integer.MAX_VALUE;
        while (!nodeStack.isEmpty()) {
            TreeNode current = nodeStack.pop();
            int currentDepth = depthStack.pop()[0];
            if (current.left == null && current.right == null) {
                minDepth = Math.min(minDepth, currentDepth);
            }

            if (current.left != null) {
                nodeStack.push(current.left);
                depthStack.push(new int[] { currentDepth + 1 });
            }
            if (current.right != null) {
                nodeStack.push(current.right);
                depthStack.push(new int[] { currentDepth + 1 });
            }
        }
        return minDepth;
    }

    public int minDepthBFSIterative(TreeNode root) {
        if (root == null)
            return 0;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int depth = 1;
        while (!queue.isEmpty()) {
            int queueLength = queue.size();
            for (int i = 0; i < queueLength; i++) {
                TreeNode current = queue.poll();
                if (current.left == null && current.right == null) {
                    return depth;
                }
                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
            }
            depth++;
        }
        return depth;
    }


    /*
    # Minimum Depth of Binary Tree — Deep Dive

    ---

    ## 1. Problem Statement

    ### What is being asked?
    Given a **binary tree**, find the **minimum depth** — the number of nodes along the **shortest path from the root node down to the nearest leaf node**.

    ### Critical Definition
    > A **leaf node** is a node that has **no children** (both left and right child are `null`).

    ### Input Format
    - Root of a binary tree (`TreeNode root`)
    - Each `TreeNode` has: `int val`, `TreeNode left`, `TreeNode right`

    ### Output Format
    - A single integer: the minimum depth

    ### Constraints (LeetCode #111)
    - Number of nodes: `0 <= n <= 10^5`
    - Node values: `-1000 <= Node.val <= 1000`
    - Tree can be empty (`root == null`)

    ### What makes this tricky?
    A **skewed tree** like the one below has a minimum depth of **3**, NOT 1 — because node `1` has a right child, so it is **not a leaf**:
    ```
        1
        \
        2
        \
            3
    ```
    The only leaf is `3`, so min depth = 3. This catches many people off guard.

    ---

    ## 2. Intuition

    ### Human Reasoning
    Imagine you're standing at the root of a tree and want to reach the **closest exit** (a leaf). You can go left or right at each node. The minimum depth is simply the **fewest steps** to reach any exit.

    ### The Key Insight
    - If a node has **both** children → recurse into both and take the minimum
    - If a node has **only a left child** → you **cannot** count the missing right side as depth 1; you must go left
    - If a node has **only a right child** → similarly, you must go right
    - If a node has **no children** → it's a leaf, depth = 1

    ### Why BFS is Often Better Here
    BFS explores level by level. The **very first leaf** it encounters is guaranteed to be at the minimum depth — so BFS can **stop early**, while DFS must explore the entire tree.

    ---

    ## 3. Approach Overview

    | # | Approach | Key Idea | Best When | Optimal? |
    |---|----------|----------|-----------|----------|
    | 1 | **DFS Recursive** | Recurse into both subtrees, handle skew cases | Interviews, clean code | ✅ For balanced trees |
    | 2 | **DFS Iterative** (Stack) | Simulate recursion with explicit stack | When stack overflow is a concern | ✅ Avoids recursion limit |
    | 3 | **BFS Iterative** (Queue) | Level-order traversal, stop at first leaf | **Optimal for skewed trees** | ✅ **Recommended** |

    **Why BFS is optimal in the worst case:**
    In a deeply skewed tree (like a linked list of 10^5 nodes), DFS visits every node. BFS stops the moment it finds the first leaf — potentially after visiting just a handful of nodes.

    ---

    ## 4. Detailed Solutions in Java

    ### Solution 1: DFS Recursive

    #### Algorithm
    1. If root is `null` → return 0
    2. If root is a **leaf** (no children) → return 1
    3. If only **left** child exists → recurse left only (+ 1)
    4. If only **right** child exists → recurse right only (+ 1)
    5. Both children exist → return `1 + min(left depth, right depth)`

    ```java
    class Solution {
        public int minDepth(TreeNode root) {
            // Base case: empty tree
            if (root == null) return 0;

            // Leaf node: no children
            if (root.left == null && root.right == null) return 1;

            // Only right child exists — cannot go left (no leaf there)
            if (root.left == null) return 1 + minDepth(root.right);

            // Only left child exists — cannot go right (no leaf there)
            if (root.right == null) return 1 + minDepth(root.left);

            // Both children exist — explore both, take the minimum
            return 1 + Math.min(minDepth(root.left), minDepth(root.right));
        }
    }
    ```

    ---

    ### Solution 2: DFS Iterative (Explicit Stack)

    #### Algorithm
    Use a stack storing `(node, currentDepth)` pairs. Track the minimum depth seen at any leaf.

    ```java
    import java.util.Stack;

    class Solution {
        public int minDepth(TreeNode root) {
            if (root == null) return 0;

            Stack<TreeNode[]> stack = new Stack<>();
            // Store node and its depth as a pair using a 2-element array trick
            // We'll use a helper class instead for clarity
            Stack<int[]> depthStack = new Stack<>();
            Stack<TreeNode> nodeStack = new Stack<>();

            nodeStack.push(root);
            depthStack.push(new int[]{1});

            int minDepth = Integer.MAX_VALUE;

            while (!nodeStack.isEmpty()) {
                TreeNode current = nodeStack.pop();
                int currentDepth = depthStack.pop()[0];

                // Found a leaf — update minimum if this path is shorter
                if (current.left == null && current.right == null) {
                    minDepth = Math.min(minDepth, currentDepth);
                }

                // Push children with incremented depth
                if (current.right != null) {
                    nodeStack.push(current.right);
                    depthStack.push(new int[]{currentDepth + 1});
                }
                if (current.left != null) {
                    nodeStack.push(current.left);
                    depthStack.push(new int[]{currentDepth + 1});
                }
            }

            return minDepth;
        }
    }
    ```

    ---

    ### Solution 3: BFS Iterative — OPTIMAL ✅

    #### Algorithm
    1. Use a queue for level-order traversal
    2. Process nodes level by level
    3. The **very first leaf** encountered = minimum depth (return immediately)

    ```java
    import java.util.LinkedList;
    import java.util.Queue;

    class Solution {
        public int minDepth(TreeNode root) {
            if (root == null) return 0;

            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            int depth = 1; // Root is already at depth 1

            while (!queue.isEmpty()) {
                int levelSize = queue.size(); // Number of nodes at current level

                // Process all nodes at the current depth level
                for (int i = 0; i < levelSize; i++) {
                    TreeNode current = queue.poll();

                    // First leaf found — this is the minimum depth (BFS guarantees it)
                    if (current.left == null && current.right == null) {
                        return depth;
                    }

                    // Add children to queue for next level
                    if (current.left != null) queue.offer(current.left);
                    if (current.right != null) queue.offer(current.right);
                }

                depth++; // Move to next level
            }

            return depth; // Should never reach here if tree is valid
        }
    }
    ```

    ---

    ## 5. Time & Space Complexity

    ### Solution 1: DFS Recursive

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | Every node is visited exactly once |
    | **Space** | O(h) | Call stack depth = tree height h. Worst case (skewed tree): O(n). Best case (balanced): O(log n) |

    **Example:** Tree with 1000 nodes, height 10 (balanced) → ~1000 recursive calls, stack depth 10.

    ---

    ### Solution 2: DFS Iterative

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | Every node is visited exactly once |
    | **Space** | O(n) | Stack can hold all nodes in worst case (skewed tree where all nodes are pushed before any leaf is found) |

    ---

    ### Solution 3: BFS Iterative — OPTIMAL ✅

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | **O(n)** worst case, **O(d × w)** best case | d = depth of first leaf, w = max width at that level. Stops early! |
    | **Space** | O(w) | Queue holds at most one full level at a time. w = max width ≤ n/2 |

    **Early-stop power:**
    In a perfect binary tree with 10^5 nodes and minimum depth = 1 (root is a leaf) → BFS returns after checking 1 node. DFS would still visit all 10^5.

    ---

    ## 6. Complete Worked Examples

    ### Example 1: Normal Binary Tree
    ```
            3
        / \
        9   20
            /  \
            15    7
    ```
    **Expected Output:** `2` (path: 3 → 9)

    #### DFS Recursive Trace
    ```
    minDepth(3)
    ├── left=9,  right=20  → both exist → 1 + min(minDepth(9), minDepth(20))
    │
    ├── minDepth(9)
    │   └── left=null, right=null → LEAF → return 1  ✅
    │
    └── minDepth(20)
        ├── left=15, right=7 → both exist → 1 + min(minDepth(15), minDepth(7))
        ├── minDepth(15) → LEAF → return 1
        └── minDepth(7)  → LEAF → return 1
        └── returns 1 + min(1,1) = 2

    Final: 1 + min(1, 2) = 2 ✅
    ```

    #### BFS Trace
    ```
    Level 1: queue = [3],       depth = 1
    → 3 has children (9, 20) → not a leaf → enqueue 9, 20

    Level 2: queue = [9, 20],   depth = 2
    → Process 9: left=null, right=null → LEAF → return 2 ✅ (EARLY STOP!)
    ```

    ---

    ### Example 2: Skewed Tree (The Tricky Case)
    ```
        1
        \
        2
        \
            3
    ```
    **Expected Output:** `3`

    #### DFS Recursive Trace
    ```
    minDepth(1)
    ├── left=null, right=2
    └── Only right child → return 1 + minDepth(2)

    minDepth(2)
    ├── left=null, right=3
    └── Only right child → return 1 + minDepth(3)

    minDepth(3)
    └── left=null, right=null → LEAF → return 1

    Final: 1 + 1 + 1 = 3 ✅
    ```
    > Without the "only right child" guard, you might wrongly return `1` thinking the null left child is a leaf path!

    #### BFS Trace
    ```
    Level 1: queue=[1],    depth=1 → has right child → enqueue 2
    Level 2: queue=[2],    depth=2 → has right child → enqueue 3
    Level 3: queue=[3],    depth=3 → LEAF → return 3 ✅
    ```

    ---

    ### Example 3: Single Node
    ```
        42
    ```
    **Expected Output:** `1`

    #### All Approaches
    - DFS: `root.left == null && root.right == null` → return 1 ✅
    - BFS: Level 1, node 42 is a leaf → return 1 ✅

    ---

    ## 7. Edge Cases

    | Edge Case | Input | Expected | How Each Approach Handles It |
    |-----------|-------|----------|------------------------------|
    | **Empty tree** | `root = null` | `0` | All three return 0 immediately ✅ |
    | **Single node** | Just root | `1` | Leaf check triggers immediately ✅ |
    | **Left-skewed tree** | Every node has only left child | `n` | DFS: only recurses left. BFS: traverses all levels ✅ |
    | **Right-skewed tree** | Every node has only right child | `n` | Same as above ✅ |
    | **Perfect binary tree** | All leaves at same level | `log(n)+1` | BFS returns at first leaf on bottom level ✅ |
    | **Unbalanced tree** | Deep left, shallow right | Shallow depth | DFS must explore both; BFS stops at first leaf ✅ |
    | **Large n (10^5)** | Max constraint | varies | DFS Recursive risks **StackOverflow** on skewed trees! BFS/Iterative DFS safe ✅ |

    ### ⚠️ Important Warning
    For a skewed tree of 10^5 nodes, **DFS Recursive will cause a StackOverflowError** in Java (default stack ~500–1000 frames). Always prefer **BFS or Iterative DFS** in production or with large inputs.

    ---

    ## 8. Final Summary

    | Approach | Time | Space | Early Stop? | Stack Overflow Risk? | Recommended? |
    |----------|------|-------|-------------|----------------------|--------------|
    | DFS Recursive | O(n) | O(h) | ❌ | ⚠️ Yes (skewed) | Interview only |
    | DFS Iterative | O(n) | O(n) | ❌ | ✅ No | Acceptable |
    | **BFS Iterative** | **O(n)** | **O(w)** | **✅ Yes** | **✅ No** | **✅ Best** |

    ### What to Remember
    > **BFS is the natural fit for "minimum depth / shortest path" problems on trees** because it explores level by level and can stop the moment it finds the answer — without needing to explore the entire tree.

    The **core trap** in this problem is the skewed tree: a node with only one child is **not a leaf**, so you must always continue down the existing child — never treat a missing child as a valid path to a leaf.
    
    */
}
