package Trees;

import java.util.ArrayDeque;
import java.util.Queue;

import Datastructures.TreeNode;

public class InvertBinaryTree {
    public static void main(String[] args) {
        InvertBinaryTree invertBinaryTree = new InvertBinaryTree();
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);

        System.out.println("InvertBinaryTree : "+invertBinaryTree.invertTreeBFSIterative(root));
    }

    /*
     * 
     * https://leetcode.com/problems/invert-binary-tree/description/?envType=problem
     * -list-v2&envId=tree
     * 
     * Given the root of a binary tree, invert the tree, and return its root.
     * 
     * 
     * 
     * Example 1:
     * Original:       Inverted:
     *      4               4
     *    /   \           /   \
     *   2     7         7     2
     *  / \   / \       / \   / \
     * 1   3 6   9     9   6 3   1
     * Input: root = [4,2,7,1,3,6,9]
     * Output: [4,7,2,9,6,3,1]
     * Example 2:
     * 
     * 
     * Input: root = [2,1,3]
     * Output: [2,3,1]
     * Example 3:
     * 
     * Input: root = []
     * Output: []
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [0, 100].
     * -100 <= Node.val <= 100
     */

    public TreeNode invertTreeDFSPreOrder(TreeNode root) {
        if (root == null)
            return null;
        swap(root, root.left, root.right);
        invertTreeDFSPreOrder(root.left);
        invertTreeDFSPreOrder(root.right);
        return root;
    }

    public TreeNode invertTreeDFSPostOrder(TreeNode root) {
        if (root == null)
            return null;
        invertTreeDFSPostOrder(root.left);
        invertTreeDFSPostOrder(root.right);
        swap(root, root.left, root.right);
        return root;
    }

    public TreeNode invertTreeBFSIterative(TreeNode root) {
        if (root == null)
            return null;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            swap(current, current.left, current.right);
            if (current.left != null)
                queue.offer(current.left);
            if (current.right != null)
                queue.offer(current.right);
        }
        return root;
    }

    public TreeNode swap(TreeNode root, TreeNode left, TreeNode right) {
        root.left = right;
        root.right = left;
        return root;
    }

    /*
        # Invert Binary Tree — Complete Deep Dive

        ---

        ## 1. Problem Statement

        ### What the Problem Says
        Given the **root** of a binary tree, invert the tree (mirror it about its vertical axis) and return the root of the inverted tree.

        **Inverting** means: for every node in the tree, swap its left and right children — recursively, all the way down.

        ### Input Format
        - A binary tree represented by its `root` node
        - Each node has: `int val`, `TreeNode left`, `TreeNode right`
        - Number of nodes: `0 ≤ n ≤ 100`
        - Node values: `-100 ≤ val ≤ 100`

        ### Output Format
        - Return the `root` of the now-inverted (mirrored) binary tree

        ### What Needs to Be Computed
        At every single node in the tree, swap its left and right child pointers. This must happen at **every level**, not just the root.

        ---

        ## 2. Intuition

        ### Core Idea (Think Like a Human)
        Imagine holding a mirror up to the right side of the tree. Every node's left child becomes its right child, and vice versa.

        **How would you reason through it?**
        1. Stand at the root. Swap its left and right children.
        2. Now go to the left subtree (which was originally the right) — do the same.
        3. Go to the right subtree (originally the left) — do the same.
        4. Repeat until you hit null nodes (leaves have no children to swap).

        This is naturally **recursive** — the definition of "invert this subtree" is the same at every node.

        ### What Makes It Interesting?
        - It's a classic example of **recursive tree thinking**
        - It can also be solved **iteratively** using BFS or DFS — good for understanding traversal
        - The problem looks simple but tests whether you truly understand tree structure and pointer manipulation
        - Famous for being [tweeted about by Max Howell](https://twitter.com/mxcl/status/608682016205344768) (creator of Homebrew) who was rejected from Google for not solving it on the whiteboard!

        ---

        ## 3. Approach Overview

        | # | Approach | Key Idea | Use When | Optimal? |
        |---|----------|----------|----------|----------|
        | 1 | **Recursive DFS (Post-order)** | Recurse to leaves first, swap on the way back up | Always — cleanest solution | ✅ Yes |
        | 2 | **Recursive DFS (Pre-order)** | Swap first, then recurse into swapped children | Alternative recursive style | ✅ Yes (same complexity) |
        | 3 | **Iterative BFS (Queue)** | Level-order traversal, swap children at each node | When stack overflow is a concern (very deep trees) | ✅ Yes (same complexity) |
        | 4 | **Iterative DFS (Stack)** | Explicit stack mimics recursion | When you want iterative DFS specifically | ✅ Yes (same complexity) |

        **Recommended:** Recursive Post-order or Pre-order — both are clean, intuitive, and interview-ready. BFS is great to mention as an alternative.

        ---

        ## 4. Detailed Solutions in Java

        ### TreeNode Definition (used in all solutions)

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

        ### ✅ Solution 1 — Recursive DFS (Post-order) [OPTIMAL & RECOMMENDED]

        #### Algorithm Steps
        1. **Base case:** If `root` is null, return null — nothing to invert
        2. **Recurse left:** Invert the entire left subtree
        3. **Recurse right:** Invert the entire right subtree
        4. **Swap:** Swap the left and right child pointers of the current node
        5. **Return** the current node (now with swapped, inverted subtrees)

        Post-order means we process children **before** the parent.

        ```java
        class Solution {
            public TreeNode invertTree(TreeNode root) {
                // Base case: empty node has nothing to invert
                if (root == null) return null;

                // Recursively invert both subtrees first (post-order)
                TreeNode invertedLeft = invertTree(root.left);
                TreeNode invertedRight = invertTree(root.right);

                // Swap the left and right children
                root.left = invertedRight;
                root.right = invertedLeft;

                return root;
            }
        }
        ```

        ---

        ### Solution 2 — Recursive DFS (Pre-order)

        #### Algorithm Steps
        1. **Base case:** If `root` is null, return null
        2. **Swap first:** Swap left and right children of current node
        3. **Recurse left:** Invert the new left subtree (originally the right)
        4. **Recurse right:** Invert the new right subtree (originally the left)
        5. **Return** current node

        Pre-order means we process the parent **before** its children.

        ```java
        class Solution {
            public TreeNode invertTree(TreeNode root) {
                if (root == null) return null;

                // Swap children BEFORE recursing (pre-order)
                TreeNode temp = root.left;
                root.left = root.right;
                root.right = temp;

                // Now recurse into the already-swapped children
                invertTree(root.left);
                invertTree(root.right);

                return root;
            }
        }
        ```

        ---

        ### Solution 3 — Iterative BFS (Queue) [Best for Very Deep Trees]

        #### Algorithm Steps
        1. Handle null root edge case
        2. Add root to a `Queue`
        3. While the queue is not empty:
        - Poll the front node
        - Swap its left and right children
        - If left child exists, add to queue
        - If right child exists, add to queue
        4. Return root

        ```java
        import java.util.LinkedList;
        import java.util.Queue;

        class Solution {
            public TreeNode invertTree(TreeNode root) {
                if (root == null) return null;

                Queue<TreeNode> queue = new LinkedList<>();
                queue.offer(root);

                while (!queue.isEmpty()) {
                    TreeNode current = queue.poll();

                    // Swap the children of the current node
                    TreeNode temp = current.left;
                    current.left = current.right;
                    current.right = temp;

                    // Enqueue children for future processing
                    if (current.left != null) queue.offer(current.left);
                    if (current.right != null) queue.offer(current.right);
                }

                return root;
            }
        }
        ```

        ---

        ### Solution 4 — Iterative DFS (Stack)

        #### Algorithm Steps
        1. Handle null root edge case
        2. Push root onto a `Stack`
        3. While the stack is not empty:
        - Pop the top node
        - Swap its left and right children
        - Push non-null children onto the stack
        4. Return root

        ```java
        import java.util.Stack;

        class Solution {
            public TreeNode invertTree(TreeNode root) {
                if (root == null) return null;

                Stack<TreeNode> stack = new Stack<>();
                stack.push(root);

                while (!stack.isEmpty()) {
                    TreeNode current = stack.pop();

                    // Swap children
                    TreeNode temp = current.left;
                    current.left = current.right;
                    current.right = temp;

                    // Push non-null children to process later
                    if (current.left != null) stack.push(current.left);
                    if (current.right != null) stack.push(current.right);
                }

                return root;
            }
        }
        ```

        ---

        ## 5. Time & Space Complexity

        | Approach | Time Complexity | Space Complexity |
        |----------|----------------|-----------------|
        | Recursive Post-order | O(n) | O(h) |
        | Recursive Pre-order | O(n) | O(h) |
        | Iterative BFS | O(n) | O(w) |
        | Iterative DFS (Stack) | O(n) | O(h) |

        ### Detailed Reasoning

        #### Time Complexity — All Approaches: **O(n)**
        - We visit **every node exactly once** (there's no way to skip any node — each one needs its children swapped)
        - For `n = 100` nodes → ~100 operations
        - For `n = 1,000` nodes → ~1,000 operations
        - Linear growth, no nested loops

        #### Space Complexity — Recursive: **O(h)** where h = height of tree
        - The **call stack** holds one frame per level of recursion
        - **Best case** (balanced tree): `h = log₂(n)` → O(log n)
        - e.g., 1000 nodes → ~10 levels deep
        - **Worst case** (skewed/linear tree): `h = n` → O(n)
        - e.g., a linked-list-shaped tree → 1000 levels deep

        #### Space Complexity — BFS: **O(w)** where w = max width of tree
        - The queue holds at most one full level at a time
        - **Worst case** (perfect binary tree, bottom level): `w = n/2` → O(n)
        - **Best case** (skewed tree): `w = 1` → O(1)

        ---

        ## 6. Complete Worked Examples

        ### Example — All Approaches

        #### Input Tree:
        ```
                4
            / \
            2   7
            / \ / \
            1  3 6  9
        ```

        ---

        ### Approach 1: Recursive Post-order Walkthrough

        | Step | Action | Current Node | State After |
        |------|--------|-------------|-------------|
        | 1 | Call `invertTree(4)` | 4 | Recurse left first |
        | 2 | Call `invertTree(2)` | 2 | Recurse left first |
        | 3 | Call `invertTree(1)` | 1 | Both children null → return node 1 |
        | 4 | Call `invertTree(3)` | 3 | Both children null → return node 3 |
        | 5 | Back at node 2 | 2 | Swap: left=3, right=1 → return node 2 |
        | 6 | Call `invertTree(7)` | 7 | Recurse left first |
        | 7 | Call `invertTree(6)` | 6 | Both children null → return node 6 |
        | 8 | Call `invertTree(9)` | 9 | Both children null → return node 9 |
        | 9 | Back at node 7 | 7 | Swap: left=9, right=6 → return node 7 |
        | 10 | Back at node 4 | 4 | Swap: left=7, right=2 → return node 4 |

        #### Output Tree:
        ```
                4
            / \
            7   2
            / \ / \
            9  6 3  1
        ```
        ✅ Perfectly mirrored!

        ---

        ### Approach 3: BFS Walkthrough

        | Step | Queue State | Node Processed | Swap Performed |
        |------|-------------|---------------|----------------|
        | Start | [4] | — | — |
        | 1 | [2, 7] | 4 | 4.left=7, 4.right=2 |
        | 2 | [7, 6, 9] | 2 (now left of 4... wait, already swapped) | 2.left=3, 2.right=1 |
        | 3 | [6, 9, 9, 6] | 7 | 7.left=9, 7.right=6 |
        | 4 | [9, 6] | 3 (leaf) | No children to add |
        | 5 | [6] | 1 (leaf) | No children to add |
        | 6 | [] | 9 (leaf) | No children to add |
        | 7 | [] | 6 (leaf) | No children to add |

        > **Note:** After processing node 4, its children become [7, 2] in the queue. Each gets its children swapped when dequeued.

        ---

        ## 7. Edge Cases

        | Edge Case | Input | Expected Output | How Each Approach Handles It |
        |-----------|-------|----------------|------------------------------|
        | **Empty tree** | `root = null` | `null` | All: base case `if (root == null) return null` handles this ✅ |
        | **Single node** | `root = [1]` | `[1]` | Swap null with null — no change ✅ |
        | **Two nodes (left only)** | `root = [1, 2, null]` | `[1, null, 2]` | Left becomes right, right becomes null ✅ |
        | **Two nodes (right only)** | `root = [1, null, 2]` | `[1, 2, null]` | Right becomes left ✅ |
        | **Perfectly balanced tree** | Standard case | Mirrored | Works perfectly for all approaches ✅ |
        | **Skewed tree (like linked list)** | `1→2→3→4→5` | Reversed | Recursive: O(n) stack depth — **risk of StackOverflow for very large n** ⚠️ BFS/Stack iterative: Safe ✅ |
        | **Duplicate values** | `[2, 2, 2]` | `[2, 2, 2]` | Values don't matter — only structure is changed ✅ |
        | **Negative values** | `[-1, -2, -3]` | `[-1, -3, -2]` | Values are irrelevant to the algorithm ✅ |

        ### ⚠️ Important Warning
        For the **recursive approach on deeply skewed trees** (essentially a linked list of 10,000+ nodes), Java's default stack size will cause a `StackOverflowError`. The **iterative BFS or DFS** approach avoids this entirely since it uses a heap-allocated Queue/Stack instead of the call stack.

        ---

        ## 8. Final Summary

        ### Comparison Table

        | Approach | Code Simplicity | Space (typical) | Overflow Safe | Interview Value |
        |----------|----------------|----------------|---------------|----------------|
        | Recursive Post-order | ⭐⭐⭐⭐⭐ Cleanest | O(log n) balanced | ❌ Risk on skewed | ⭐⭐⭐⭐⭐ |
        | Recursive Pre-order | ⭐⭐⭐⭐ Clean | O(log n) balanced | ❌ Risk on skewed | ⭐⭐⭐⭐ |
        | Iterative BFS | ⭐⭐⭐ Moderate | O(n) worst | ✅ Always safe | ⭐⭐⭐⭐ |
        | Iterative DFS (Stack) | ⭐⭐⭐ Moderate | O(h) | ✅ Always safe | ⭐⭐⭐ |

        ### 🏆 Recommendation
        **Use Recursive Post-order** in interviews — it's the most elegant and demonstrates deep understanding of recursion. **Mention Iterative BFS** as a follow-up to show awareness of stack overflow risks in production systems.

        ### 🧠 What to Remember
        > **Pattern:** This is a pure **recursive tree structural transformation** problem. The key insight is that inverting a tree = swap children + recursively invert both subtrees. Any tree traversal (pre, post, BFS, DFS) works as long as you swap at every node. Think: *"What do I need to do at each node?"* — and the answer is always just swap.

        ---

        ## 🏢 Company Interview Appearances

        | Company | Frequency | Notes |
        |---------|-----------|-------|
        | **Google** | ⭐⭐⭐⭐⭐ Very High | Made famous by the Homebrew incident |
        | **Amazon** | ⭐⭐⭐⭐⭐ Very High | Common in SDE-1 and SDE-2 rounds |
        | **Facebook / Meta** | ⭐⭐⭐⭐ High | Often paired with tree traversal questions |
        | **Microsoft** | ⭐⭐⭐⭐ High | Appears in entry-level rounds |
        | **Apple** | ⭐⭐⭐ Medium | Occasionally in DS&A screenings |
        | **Bloomberg** | ⭐⭐⭐ Medium | Common warm-up question |
        | **Uber** | ⭐⭐⭐ Medium | Tree fundamentals section |
        | **LinkedIn** | ⭐⭐ Moderate | Part of tree problem sets |

        ### 📊 LeetCode Stats (Problem #226)
        - **Difficulty:** Easy
        - **Acceptance Rate:** ~75%
        - **Total Submissions:** 3M+
        - **Liked by:** ~97% of users
        - One of the **Top 10 most famous** LeetCode problems due to the viral tweet — almost every FAANG interviewer knows it, making it a **must-know** for any coding interview
    */
}
