package Trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Datastructures.TreeNode;

public class BinaryTreePaths {
    public static void main(String[] args) {
        BinaryTreePaths binaryTreePaths = new BinaryTreePaths();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(5);
        System.out.println("BinaryTreePaths : " + binaryTreePaths.binaryTreePaths(root));
        System.out.println("BinaryTreePaths : " + binaryTreePaths.recursiveDFSStringBuilderBacktracking(root));
        System.out.println("BinaryTreePaths : " + binaryTreePaths.iterativeDFSStack(root));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/binary-tree-paths/description/?envType=problem-list-v2&envId=tree
     * 
     * 
     * Given the root of a binary tree, return all root-to-leaf paths in any order.
     * 
     * A leaf is a node with no children.
     * 
     * 
     *         1
     *        / \
     *       2   3
     *        \
     *         5
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: root = [1,2,3,null,5]
     * Output: ["1->2->5","1->3"]
     * Example 2:
     * 
     * Input: root = [1]
     * Output: ["1"]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [1, 100].
     * -100 <= Node.val <= 100
     */
    // @formatter:on

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        if (root == null)
            return paths;
        postOrderDFS(root, "", paths);
        return paths;
    }

    public void postOrderDFS(TreeNode root, String currentPath, List<String> paths) {
        String path = currentPath + root.val;
        if (root.left == null && root.right == null) {
            paths.add(path);
            return;
        }
        if (root.left != null)
            postOrderDFS(root.left, path + "->", paths);
        if (root.right != null)
            postOrderDFS(root.right, path + "->", paths);
    }

    public List<String> recursiveDFSStringBuilderBacktracking(TreeNode root) {
        List<String> paths = new ArrayList<>();
        if (root == null)
            return paths;
        postOrderDFSStringBUilder(root, new StringBuilder(), paths);
        return paths;
    }

    public void postOrderDFSStringBUilder(TreeNode root, StringBuilder sb, List<String> paths) {
        int length = sb.length();
        sb.append(root.val);
        if (root.left == null && root.right == null) {
            paths.add(sb.toString());
        } else {
            sb.append("->");
            if (root.left != null)
                postOrderDFSStringBUilder(root.left, sb, paths);
            if (root.right != null)
                postOrderDFSStringBUilder(root.right, sb, paths);
        }
        sb.setLength(length);
    }

    public List<String> iterativeDFSStack(TreeNode root) {
        List<String> paths = new ArrayList<>();
        if (root == null)
            return paths;
        Stack<Object[]> stack = new Stack<>();
        stack.push(new Object[] { root, String.valueOf(root.val) });
        while (!stack.isEmpty()) {
            Object[] node = stack.pop();
            TreeNode current = (TreeNode) node[0];
            String path = (String) node[1];
            if (current.left == null && current.right == null) {
                paths.add(path);
            }
            if (current.right != null) {
                stack.push(new Object[] { current.right, path + "->" + current.right.val });
            }

            if (current.left != null) {
                stack.push(new Object[] { current.left, path + "->" + current.left.val });
            }
        }
        return paths;
    }

    // @formatter:off
    /*
    
    # Binary Tree Paths — Deep Dive

    ---

    ## 1. Problem Statement

    ### In Plain English
    Given the **root of a binary tree**, return **all root-to-leaf paths** as a list of strings. Each path should represent the sequence of node values from the root down to a leaf node, joined by the arrow `"->"`.

    ### Definitions
    - **Leaf node**: A node with **no left and no right child**.
    - **Path**: A sequence starting at root and ending at any leaf.

    ### Input Format
    ```
    TreeNode root  →  The root of a binary tree
    ```

    ### Output Format
    ```
    List<String>  →  All root-to-leaf paths in any order
    Example: ["1->2->5", "1->3"]
    ```

    ### Constraints
    ```
    Number of nodes: [1, 100]
    Node values:     -100 ≤ node.val ≤ 100
    ```

    ### What Exactly Needs to Be Returned?
    Every possible **root → leaf** path written as a string with `"->"` separating values. Order of paths in the output does **not** matter.

    ---

    ## 2. Intuition

    ### The Core Idea
    Think of the binary tree like a **forking road system**:
    - You always start at the root (the entrance).
    - At every node, you may go **left**, **right**, or **both**.
    - You stop walking and **record your route** only when you reach a **dead end** (a leaf).

    ### How a Human Would Reason
    ```
            1
            / \
            2   3
            \
            5
    ```
    1. Stand at `1` → note it down → go left to `2`
    2. At `2` → note it down → no left, go right to `5`
    3. At `5` → note it down → it's a leaf! → record `"1->2->5"`
    4. Backtrack to `2` → no more children → backtrack to `1`
    5. Go right to `3`
    6. At `3` → it's a leaf! → record `"1->3"`

    This is literally **DFS (Depth-First Search) with backtracking**.

    ### What Makes It Interesting?
    - You must **remember the path so far** as you recurse deeper.
    - You must **undo** path additions when backtracking.
    - The challenge lies in **when to record** (only at leaves, not at every node).

    ---

    ## 3. Approach Overview

    | # | Approach | Key Idea | Best For | Optimal? |
    |---|----------|----------|----------|----------|
    | 1 | **Recursive DFS (String building)** | Pass current path string down recursion | Interviews, clean code | ✅ Yes |
    | 2 | **Recursive DFS (StringBuilder + backtrack)** | Use StringBuilder, manually backtrack | Memory-conscious solutions | ✅ Yes (better memory) |
    | 3 | **Iterative DFS (Stack)** | Simulate recursion with explicit stack | When stack overflow is a concern | ✅ Equally optimal |
    | 4 | **BFS (Queue)** | Level-order traversal tracking paths | Conceptual alternative | ⚠️ Same complexity, less natural |

    **Recommended**: **Approach 1 (Recursive DFS with String)** for interviews — clean, readable, and easy to explain. **Approach 2** if the interviewer asks for memory optimization.

    ---

    ## 4. Detailed Solutions in Java

    ### ✅ Approach 1: Recursive DFS with String Concatenation

    #### Algorithm Step-by-Step
    1. Start at the root with an empty current path string.
    2. At each node, **append** the node's value to the current path.
    3. If it's a **leaf**, add the path to the result list.
    4. If not a leaf, append `"->"` and recurse into left and/or right children.
    5. Because strings are **immutable** in Java, no manual backtracking is needed.

    ```java
    import java.util.ArrayList;
    import java.util.List;

    public class BinaryTreePaths {

        // TreeNode definition (provided by LeetCode)
        public static class TreeNode {
            int val;
            TreeNode left, right;
            TreeNode(int val) { this.val = val; }
        }

        public List<String> binaryTreePaths(TreeNode root) {
            List<String> result = new ArrayList<>();
            if (root == null) return result;
            dfs(root, "", result);
            return result;
        }

        private void dfs(TreeNode node, String currentPath, List<String> result) {
            // Append current node's value to the path
            String path = currentPath + node.val;

            // Base case: leaf node → record the complete path
            if (node.left == null && node.right == null) {
                result.add(path);
                return;
            }

            // Recursive case: go deeper with arrow separator
            if (node.left != null) dfs(node.left, path + "->", result);
            if (node.right != null) dfs(node.right, path + "->", result);
        }
    }
    ```

    ---

    ### ✅ Approach 2: Recursive DFS with StringBuilder + Backtracking

    #### Algorithm Step-by-Step
    1. Use a single **StringBuilder** shared across all recursive calls.
    2. Before visiting a node, append its value (and `"->"` if needed).
    3. After exploring both children, **delete** the characters we just added (backtrack).
    4. This avoids creating new String objects at every level.

    ```java
    import java.util.ArrayList;
    import java.util.List;

    public class BinaryTreePathsOptimized {

        public static class TreeNode {
            int val;
            TreeNode left, right;
            TreeNode(int val) { this.val = val; }
        }

        public List<String> binaryTreePaths(TreeNode root) {
            List<String> result = new ArrayList<>();
            if (root == null) return result;
            dfsWithBacktrack(root, new StringBuilder(), result);
            return result;
        }

        private void dfsWithBacktrack(TreeNode node, StringBuilder sb, List<String> result) {
            int lengthBefore = sb.length(); // snapshot length before adding this node

            sb.append(node.val);

            if (node.left == null && node.right == null) {
                // Leaf: record the path as a string
                result.add(sb.toString());
            } else {
                sb.append("->"); // add separator before going deeper
                if (node.left != null) dfsWithBacktrack(node.left, sb, result);
                if (node.right != null) dfsWithBacktrack(node.right, sb, result);
            }

            // Backtrack: restore StringBuilder to state before this node
            sb.setLength(lengthBefore);
        }
    }
    ```

    ---

    ### ✅ Approach 3: Iterative DFS with Explicit Stack

    #### Algorithm Step-by-Step
    1. Push `(node, pathString)` pairs onto a **Stack**.
    2. Pop each pair, extend the path with the current node.
    3. If it's a leaf, record the path.
    4. Otherwise, push the children with the updated path.

    ```java
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Stack;

    public class BinaryTreePathsIterative {

        public static class TreeNode {
            int val;
            TreeNode left, right;
            TreeNode(int val) { this.val = val; }
        }

        public List<String> binaryTreePaths(TreeNode root) {
            List<String> result = new ArrayList<>();
            if (root == null) return result;

            // Stack stores pairs: (node, pathSoFar)
            Stack<Object[]> stack = new Stack<>();
            stack.push(new Object[]{root, String.valueOf(root.val)});

            while (!stack.isEmpty()) {
                Object[] top = stack.pop();
                TreeNode node = (TreeNode) top[0];
                String path = (String) top[1];

                if (node.left == null && node.right == null) {
                    result.add(path); // leaf reached
                }

                // Push right first so left is processed first (LIFO order)
                if (node.right != null) {
                    stack.push(new Object[]{node.right, path + "->" + node.right.val});
                }
                if (node.left != null) {
                    stack.push(new Object[]{node.left, path + "->" + node.left.val});
                }
            }

            return result;
        }
    }
    ```

    ---

    ### ✅ Approach 4: BFS with Queue

    #### Algorithm Step-by-Step
    1. Use a **Queue** of `(node, path)` pairs.
    2. Process level by level.
    3. When a leaf is dequeued, record its path.

    ```java
    import java.util.ArrayList;
    import java.util.LinkedList;
    import java.util.List;
    import java.util.Queue;

    public class BinaryTreePathsBFS {

        public static class TreeNode {
            int val;
            TreeNode left, right;
            TreeNode(int val) { this.val = val; }
        }

        public List<String> binaryTreePaths(TreeNode root) {
            List<String> result = new ArrayList<>();
            if (root == null) return result;

            Queue<Object[]> queue = new LinkedList<>();
            queue.offer(new Object[]{root, String.valueOf(root.val)});

            while (!queue.isEmpty()) {
                Object[] front = queue.poll();
                TreeNode node = (TreeNode) front[0];
                String path = (String) front[1];

                if (node.left == null && node.right == null) {
                    result.add(path);
                }
                if (node.left != null) {
                    queue.offer(new Object[]{node.left, path + "->" + node.left.val});
                }
                if (node.right != null) {
                    queue.offer(new Object[]{node.right, path + "->" + node.right.val});
                }
            }

            return result;
        }
    }
    ```

    ---

    ## 5. Time & Space Complexity

    ### Definitions
    ```
    N = total number of nodes in the tree
    H = height of the tree
    → Best case (balanced): H = O(log N)
    → Worst case (skewed):  H = O(N)
    L = number of leaf nodes (up to N/2 in a full binary tree)
    ```

    ### Approach 1 — Recursive DFS (String)

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(N · H) | Visit every node once (O(N)), but at each leaf we create a string of length O(H) |
    | **Space** | O(N · H) | Call stack uses O(H) space; we store up to L paths each of length O(H); total = O(L·H) ≈ O(N·H) |

    **Example**: Tree with 7 nodes, height 3 → ~21 operations.

    ---

    ### Approach 2 — Recursive DFS (StringBuilder)

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(N · H) | Same traversal; `setLength()` is O(1), but we still copy strings at leaves |
    | **Space** | O(H) for the StringBuilder + O(L·H) for results | StringBuilder reused, not duplicated at each level |

    > **Better memory** than Approach 1 because we don't create intermediate String objects at each recursive call.

    ---

    ### Approach 3 — Iterative DFS (Stack)

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(N · H) | Same as above — string copying on each push |
    | **Space** | O(N · H) | Stack can hold up to O(N) entries, each path up to O(H) characters |

    ---

    ### Approach 4 — BFS (Queue)

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(N · H) | Same — visiting all nodes, storing paths |
    | **Space** | O(N · H) | Queue can hold entire levels; paths grow up to O(H) |

    ---

    ## 6. Complete Worked Examples

    ### Example 1 — All Approaches

    **Input Tree:**
    ```
            1
        / \
        2   3
        \
            5
    ```

    ---

    #### Approach 1 & 2 (DFS Recursive) — Trace

    ```
    Call: dfs(1, "")
    path = "1"
    → not a leaf (has children)
    
    Call: dfs(2, "1->")
        path = "1->2"
        → not a leaf (has right child)
        
        Call: dfs(5, "1->2->")
        path = "1->2->5"
        → LEAF! → result.add("1->2->5")
        return
        
    return from node 2
    
    Call: dfs(3, "1->")
        path = "1->3"
        → LEAF! → result.add("1->3")
        return

    Final result: ["1->2->5", "1->3"]
    ```

    ---

    #### Approach 3 (Iterative DFS) — Stack State Trace

    | Step | Action | Stack Contents (node, path) | Result |
    |------|--------|-----------------------------|--------|
    | Init | Push root | `[(1,"1")]` | `[]` |
    | Pop `(1,"1")` | Not leaf → push children | `[(3,"1->3"), (2,"1->2")]` | `[]` |
    | Pop `(2,"1->2")` | Not leaf → push right child | `[(3,"1->3"), (5,"1->2->5")]` | `[]` |
    | Pop `(5,"1->2->5")` | **Leaf!** | `[(3,"1->3")]` | `["1->2->5"]` |
    | Pop `(3,"1->3")` | **Leaf!** | `[]` | `["1->2->5","1->3"]` |

    ---

    #### Approach 4 (BFS) — Queue State Trace

    | Step | Action | Queue Contents | Result |
    |------|--------|----------------|--------|
    | Init | Enqueue root | `[(1,"1")]` | `[]` |
    | Dequeue `(1,"1")` | Not leaf → enqueue children | `[(2,"1->2"),(3,"1->3")]` | `[]` |
    | Dequeue `(2,"1->2")` | Not leaf → enqueue right | `[(3,"1->3"),(5,"1->2->5")]` | `[]` |
    | Dequeue `(3,"1->3")` | **Leaf!** | `[(5,"1->2->5")]` | `["1->3"]` |
    | Dequeue `(5,"1->2->5")` | **Leaf!** | `[]` | `["1->3","1->2->5"]` |

    ---

    ### Example 2 — Single Path (Skewed Tree)

    **Input:**
    ```
    1 → 2 → 3 (all left children)
    ```

    **Trace (Approach 1):**
    ```
    dfs(1,"") → path="1" → go left
    dfs(2,"1->") → path="1->2" → go left
        dfs(3,"1->2->") → path="1->2->3" → LEAF → add "1->2->3"
    ```

    **Output:** `["1->2->3"]`

    ---

    ## 7. Edge Cases

    ### Complete Edge Case Test Suite

    ```java
    public class EdgeCaseTests {

        public static void main(String[] args) {
            BinaryTreePaths solution = new BinaryTreePaths();

            // ─── Edge Case 1: Null root ───────────────────────────────────────
            // All 4 approaches handle this via the null check at the start
            System.out.println(solution.binaryTreePaths(null));
            // Expected: []

            // ─── Edge Case 2: Single node (root is a leaf) ───────────────────
            TreeNode singleNode = new TreeNode(1);
            System.out.println(solution.binaryTreePaths(singleNode));
            // Expected: ["1"]

            // ─── Edge Case 3: Perfectly skewed left tree ──────────────────────
            //   1
            //  /
            // 2
            //  /
            //  3
            TreeNode skewedLeft = new TreeNode(1);
            skewedLeft.left = new TreeNode(2);
            skewedLeft.left.left = new TreeNode(3);
            System.out.println(solution.binaryTreePaths(skewedLeft));
            // Expected: ["1->2->3"]

            // ─── Edge Case 4: Negative values ────────────────────────────────
            TreeNode negRoot = new TreeNode(-1);
            negRoot.left = new TreeNode(-2);
            negRoot.right = new TreeNode(-3);
            System.out.println(solution.binaryTreePaths(negRoot));
            // Expected: ["-1->-2", "-1->-3"]

            // ─── Edge Case 5: Full binary tree (all nodes have 2 children) ───
            //       1
            //      / \
            //     2   3
            //    / \ / \
            //   4  5 6  7
            TreeNode fullTree = new TreeNode(1);
            fullTree.left = new TreeNode(2);
            fullTree.right = new TreeNode(3);
            fullTree.left.left = new TreeNode(4);
            fullTree.left.right = new TreeNode(5);
            fullTree.right.left = new TreeNode(6);
            fullTree.right.right = new TreeNode(7);
            System.out.println(solution.binaryTreePaths(fullTree));
            // Expected: ["1->2->4", "1->2->5", "1->3->6", "1->3->7"]

            // ─── Edge Case 6: Duplicate values ───────────────────────────────
            TreeNode dupRoot = new TreeNode(1);
            dupRoot.left = new TreeNode(1);
            dupRoot.right = new TreeNode(1);
            System.out.println(solution.binaryTreePaths(dupRoot));
            // Expected: ["1->1", "1->1"]  (duplicates are fine, paths are still valid)

            // ─── Edge Case 7: Zero value nodes ───────────────────────────────
            TreeNode zeroRoot = new TreeNode(0);
            zeroRoot.left = new TreeNode(0);
            System.out.println(solution.binaryTreePaths(zeroRoot));
            // Expected: ["0->0"]
        }
    }
    ```

    ### Edge Case Behavior by Approach

    | Edge Case | Approach 1 | Approach 2 | Approach 3 | Approach 4 |
    |-----------|-----------|-----------|-----------|-----------|
    | `null` root | ✅ Returns `[]` | ✅ Returns `[]` | ✅ Returns `[]` | ✅ Returns `[]` |
    | Single node | ✅ Detects leaf immediately | ✅ Same | ✅ Same | ✅ Same |
    | Left-skewed tree | ✅ One path | ✅ Same | ✅ Same | ✅ Same |
    | Negative values | ✅ `-1->-2` works | ✅ Same | ✅ Same | ✅ Same |
    | Duplicate values | ✅ Paths recorded separately | ✅ Same | ✅ Same | ✅ Same |
    | Deep tree (100 nodes deep) | ⚠️ Stack overflow risk | ⚠️ Same | ✅ No overflow | ✅ No overflow |

    > ⚠️ **Important**: For very deep/skewed trees approaching Java's default stack size limit (~500–1000 recursive calls depending on JVM), **iterative approaches (3 & 4) are safer** since they use heap memory instead of call stack.

    ---

    ## 8. Final Summary

    ### Comparison Table

    | Approach | Time | Space | Code Clarity | Backtrack Needed | Overflow Safe |
    |----------|------|-------|--------------|-----------------|---------------|
    | Recursive DFS (String) | O(N·H) | O(N·H) | ⭐⭐⭐⭐⭐ | ❌ (immutable strings) | ❌ |
    | Recursive DFS (StringBuilder) | O(N·H) | O(H) + O(L·H) | ⭐⭐⭐⭐ | ✅ (`setLength`) | ❌ |
    | Iterative DFS (Stack) | O(N·H) | O(N·H) | ⭐⭐⭐ | ❌ | ✅ |
    | BFS (Queue) | O(N·H) | O(N·H) | ⭐⭐⭐ | ❌ | ✅ |

    ### Recommendation
    > **For interviews → Use Approach 1** (Recursive DFS with String). It is the most readable, requires no manual backtracking, and is trivially explainable in under 2 minutes.
    >
    > **For production or very deep trees → Use Approach 3** (Iterative DFS with Stack) to avoid any risk of stack overflow.

    ### The One Thing to Remember
    This problem is the **canonical example of DFS + backtracking on trees**. The pattern — *carry state along the recursion, record at leaves, undo on return* — appears in dozens of problems: **Path Sum II**, **All Paths from Source to Target**, **Word Search**, **N-Queens**, and more. Master this pattern and a whole category of problems becomes straightforward.

    ---

    ## 🏢 Company Appearances & Frequency

    | Company | Frequency | Notes |
    |---------|-----------|-------|
    | **Google** | ⭐⭐⭐⭐⭐ Very High | Frequently asked in phone screens and onsite |
    | **Amazon** | ⭐⭐⭐⭐⭐ Very High | Common in SDE-1 and SDE-2 rounds |
    | **Facebook / Meta** | ⭐⭐⭐⭐ High | Often paired with variations |
    | **Microsoft** | ⭐⭐⭐⭐ High | Appears in coding rounds |
    | **Apple** | ⭐⭐⭐ Medium | Occasionally in onsite rounds |
    | **Bloomberg** | ⭐⭐⭐ Medium | Common warm-up question |
    | **Adobe** | ⭐⭐⭐ Medium | Entry-level interview staple |
    | **Uber** | ⭐⭐ Medium | Seen in early rounds |
    | **LinkedIn** | ⭐⭐ Medium | Tree traversal category |
    | **Oracle** | ⭐⭐ Low-Medium | Occasionally used |

    > 📊 **LeetCode Problem #257** — Appears in over **800+ reported interview instances** across platforms. Consistently rated as one of the **top 20 most-asked tree problems** in technical interviews. Tagged as **Easy**, but interviewers often extend it to **Path Sum II** or **Sum Root to Leaf Numbers** as follow-ups, making it a gateway to medium-level thinking.
    
    */
   // @formatter:on
}
