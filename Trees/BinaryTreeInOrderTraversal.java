package Trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Datastructures.TreeNode;

public class BinaryTreeInOrderTraversal {
    public static void main(String[] args) {
        BinaryTreeInOrderTraversal binaryTreeInOrderTraversal = new BinaryTreeInOrderTraversal();
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(3);
        System.out
                .println("BinaryTreeInOrderTraversal : " + binaryTreeInOrderTraversal.inorderMorrisTraversal(root));
    }

    /*
     * https://leetcode.com/problems/binary-tree-inorder-traversal/description/?
     * envType=problem-list-v2&envId=tree
     * 
     * Given the root of a binary tree, return the inorder traversal of its nodes'
     * values.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: root = [1,null,2,3]
     * 
     * Output: [1,3,2]
     * 
     * Explanation:
     * 
     * 
     * 
     * Example 2:
     * 
     * Input: root = [1,2,3,4,5,null,8,null,null,6,7,9]
     * 
     * Output: [4,2,6,5,7,1,3,9,8]
     * 
     * Explanation:
     * 
     * 
     * 
     * Example 3:
     * 
     * Input: root = []
     * 
     * Output: []
     * 
     * Example 4:
     * 
     * Input: root = [1]
     * 
     * Output: [1]
     * 
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [0, 100].
     * -100 <= Node.val <= 100
     * 
     * 
     * Follow up: Recursive solution is trivial, could you do it iteratively?
     */

    public List<Integer> inorderTraversalReccursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        iot(root, result);
        return result;
    }

    public void iot(TreeNode root, List<Integer> result) {
        if (root == null)
            return;
        iot(root.left, result);
        result.add(root.val);
        iot(root.right, result);
    }

    public List<Integer> inorderTraversalIterative(TreeNode root) {
        Stack<TreeNode> queue = new Stack<>();
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;
        while (current != null || !queue.isEmpty()) {
            while (current != null) {
                queue.add(current);
                current = current.left;
            }
            current = queue.pop();
            result.add(current.val);
            current = current.right;
        }
        return result;
    }

    public List<Integer> inorderMorrisTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;
        while (current != null) {
            if (current.left == null) {
                result.add(current.val);
                current = current.right;
            } else {
                TreeNode predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }
                if (predecessor.right == null) {
                    predecessor.right = current;
                    current = current.left;
                } else {
                    predecessor.right = null;
                    result.add(current.val);
                    current = current.right;
                }
            }
        }
        return result;
    }


    /*
    # Binary Tree Inorder Traversal — Deep Dive

    ---

    ## 1. Problem Statement

    ### In Plain English
    Given the root of a binary tree, return a list of all node values visited in **inorder** sequence — meaning you visit the **left subtree first**, then the **current node (root)**, then the **right subtree**, recursively.

    ### Input Format
    - A reference to the `root` of a binary tree (`TreeNode root`)
    - Each `TreeNode` has: `int val`, `TreeNode left`, `TreeNode right`
    - The tree can have **0 to 100 nodes**
    - Node values range from **-100 to 100**

    ### Output Format
    - A `List<Integer>` containing node values in inorder order

    ### What Exactly Needs to Be Computed
    For a tree like:
    ```
        1
        \
        2
        /
        3
    ```
    Inorder traversal visits: `1 → 3 → 2`, so return `[1, 3, 2]`

    ---

    ## 2. Intuition

    ### The Core Idea
    Inorder traversal follows the pattern: **Left → Root → Right**

    Think of it like reading a **sorted BST** — inorder traversal of a BST always yields a sorted sequence. That's not a coincidence; it's the defining property.

    ### How a Human Reasons About It
    Imagine you're standing at the root of a tree. You say to yourself:
    1. "Before I record myself, I must fully explore everything to my left."
    2. "Now I record my own value."
    3. "Now I explore everything to my right."

    This natural recursive thinking maps directly to **recursion**, but it can also be simulated with a **stack** (iteratively), or done cleverly using **Morris Traversal** without any extra space.

    ### What Makes It Interesting
    - The recursive solution is trivially short — almost too easy.
    - The iterative solution teaches you how **the call stack works** under the hood.
    - Morris Traversal teaches you how to use **tree pointers themselves** as temporary bookmarks — an elegant O(1) space trick.

    ---

    ## 3. Approach Overview

    | # | Approach | Key Idea | Best For | Space |
    |---|----------|----------|----------|-------|
    | 1 | **Recursive DFS** | Use the call stack implicitly | Quick interviews, clarity | O(h) |
    | 2 | **Iterative with Stack** | Simulate the call stack explicitly | Follow-up questions | O(h) |
    | 3 | **Morris Traversal** | Use tree structure as temporary links | Space-constrained systems | O(1) |

    - **h** = height of tree = O(log n) for balanced, O(n) worst case
    - ✅ **Optimal for interviews**: Iterative Stack (demonstrates understanding)
    - ✅ **Optimal for space**: Morris Traversal
    - ✅ **Simplest to write**: Recursive

    ---

    ## 4. Detailed Solutions in Java

    ### Approach 1 — Recursive DFS

    **Step-by-step algorithm:**
    1. Base case: if node is `null`, return immediately
    2. Recursively traverse the left subtree
    3. Add the current node's value to the result list
    4. Recursively traverse the right subtree

    ```java
    class Solution {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            recurse(root, result);
            return result;
        }

        private void recurse(TreeNode node, List<Integer> result) {
            if (node == null) return;           // base case: nothing to visit

            recurse(node.left, result);         // 1. go all the way left
            result.add(node.val);               // 2. visit current node
            recurse(node.right, result);        // 3. go right
        }
    }
    ```

    ---

    ### Approach 2 — Iterative with Explicit Stack

    **Step-by-step algorithm:**
    1. Maintain a `Stack<TreeNode>` and a `current` pointer starting at root
    2. **Phase A — Go Left:** Push nodes onto the stack while drilling left
    3. **Phase B — Visit:** When `current` is null, pop from stack, record value
    4. **Phase C — Go Right:** Move `current` to the popped node's right child
    5. Repeat until both stack is empty and `current` is null

    ```java
    class Solution {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();
            TreeNode current = root;

            while (current != null || !stack.isEmpty()) {

                // Phase A: drill down to the leftmost node
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }

                // Phase B: backtrack — visit the node on top of stack
                current = stack.pop();
                result.add(current.val);

                // Phase C: move to right subtree
                current = current.right;
            }

            return result;
        }
    }
    ```

    ---

    ### Approach 3 — Morris Traversal (O(1) Space)

    **Core Trick:** Before moving left, find the **inorder predecessor** (rightmost node of left subtree) and temporarily set its `right` pointer back to the current node. This creates a "return path" so you can come back up without a stack.

    **Step-by-step algorithm:**
    1. Start at `current = root`
    2. If no left child → **visit** current, move right
    3. If left child exists:
    - Find the **inorder predecessor** (rightmost of left subtree)
    - If predecessor's right is `null` → set it to `current`, move left (making the link)
    - If predecessor's right is `current` → restore it to `null`, **visit** current, move right (cleaning up)
    4. Repeat until `current` is null

    ```java
    class Solution {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            TreeNode current = root;

            while (current != null) {

                if (current.left == null) {
                    // No left subtree — visit and move right
                    result.add(current.val);
                    current = current.right;

                } else {
                    // Find the inorder predecessor of current
                    TreeNode predecessor = current.left;
                    while (predecessor.right != null && predecessor.right != current) {
                        predecessor = predecessor.right;
                    }

                    if (predecessor.right == null) {
                        // First visit: create the threaded link back to current
                        predecessor.right = current;
                        current = current.left;

                    } else {
                        // Second visit: remove the link, visit current, go right
                        predecessor.right = null;
                        result.add(current.val);
                        current = current.right;
                    }
                }
            }

            return result;
        }
    }
    ```

    ---

    ## 5. Time & Space Complexity

    ### Approach 1 — Recursive

    | | Complexity | Reasoning |
    |--|------------|-----------|
    | **Time** | O(n) | Every node is visited exactly once |
    | **Space** | O(h) | Call stack depth = tree height; O(log n) balanced, O(n) skewed |

    **Example:** 7-node balanced tree → ~3 levels of recursion on the call stack at most.

    ---

    ### Approach 2 — Iterative Stack

    | | Complexity | Reasoning |
    |--|------------|-----------|
    | **Time** | O(n) | Each node is pushed and popped from the stack exactly once |
    | **Space** | O(h) | Stack holds at most h nodes at any time (the path to the deepest left node) |

    **Example:** A completely left-skewed tree of 100 nodes → stack grows to size 100.

    ---

    ### Approach 3 — Morris Traversal

    | | Complexity | Reasoning |
    |--|------------|-----------|
    | **Time** | O(n) | Each node is visited at most twice (once to set the link, once to clean up); finding predecessors across the whole traversal is O(n) amortized |
    | **Space** | O(1) | No stack, no recursion — only a few pointer variables |

    **Example:** Any tree of 1,000,000 nodes — the memory footprint stays constant (just `current` and `predecessor` pointers).

    ---

    ## 6. Complete Worked Examples

    ### Example Tree
    ```
            4
        / \
        2   6
        / \ / \
        1  3 5   7
    ```
    Expected inorder output: `[1, 2, 3, 4, 5, 6, 7]`

    ---

    ### Approach 1 — Recursive Walkthrough

    ```
    recurse(4)
    recurse(2)
        recurse(1)
        recurse(null) → return
        add(1)         → result = [1]
        recurse(null) → return
        add(2)           → result = [1, 2]
        recurse(3)
        recurse(null) → return
        add(3)         → result = [1, 2, 3]
        recurse(null) → return
    add(4)             → result = [1, 2, 3, 4]
    recurse(6)
        recurse(5)
        add(5)         → result = [1, 2, 3, 4, 5]
        add(6)           → result = [1, 2, 3, 4, 5, 6]
        recurse(7)
        add(7)         → result = [1, 2, 3, 4, 5, 6, 7]
    ```

    ---

    ### Approach 2 — Iterative Stack Walkthrough

    | Step | current | Stack (bottom→top) | Result |
    |------|---------|-------------------|--------|
    | Start | 4 | [] | [] |
    | Drill left | 2 | [4] | [] |
    | Drill left | 1 | [4, 2] | [] |
    | Drill left | null | [4, 2, 1] | [] |
    | Pop 1, visit | 1's right = null | [4, 2] | [1] |
    | Pop 2, visit | 2's right = 3 | [4] | [1, 2] |
    | Drill left on 3 | 3 | [4] | [1, 2] |
    | Drill left | null | [4, 3] | [1, 2] |
    | Pop 3, visit | 3's right = null | [4] | [1, 2, 3] |
    | Pop 4, visit | 4's right = 6 | [] | [1, 2, 3, 4] |
    | Drill left on 6 | 5 | [6] | [1,2,3,4] |
    | Pop 5, visit | 5's right = null | [6] | [1,2,3,4,5] |
    | Pop 6, visit | 6's right = 7 | [] | [1,2,3,4,5,6] |
    | Pop 7, visit | null | [] | [1,2,3,4,5,6,7] |

    ---

    ### Approach 3 — Morris Traversal Walkthrough

    Using a simpler tree for clarity:
    ```
        2
    / \
    1   3
    ```

    | Step | current | predecessor | Action | Result |
    |------|---------|-------------|--------|--------|
    | 1 | 2 | 1 (rightmost of left=1) | predecessor.right = null → set to 2; move left | [] |
    | 2 | 1 | — (no left) | Visit 1, move right (which is now 2) | [1] |
    | 3 | 2 | 1 (predecessor.right == 2) | Clean up link (pred.right=null), visit 2, move right | [1, 2] |
    | 4 | 3 | — (no left) | Visit 3, move right (null) | [1, 2, 3] |
    | 5 | null | — | Loop ends | **[1, 2, 3]** ✅ |

    ---

    ## 7. Edge Cases

    | Edge Case | What Happens | All 3 Approaches Handle It? |
    |-----------|--------------|----------------------------|
    | `root == null` | Return empty list immediately | ✅ All handle: base case / while-condition |
    | Single node (`root` with no children) | Visit only root; return `[root.val]` | ✅ |
    | Left-skewed tree (like a linked list going left) | Worst-case stack depth O(n) for Recursive/Iterative | ⚠️ Recursive may stack overflow on very deep trees (>10,000 nodes in Java); Morris handles fine |
    | Right-skewed tree | Minimal stack usage; each node visited and immediately moved right | ✅ |
    | Duplicate values | All values are collected as-is; no deduplication needed | ✅ |
    | Tree with all same values | Works correctly; list will contain n identical values | ✅ |
    | Negative values (-100 to -1) | Stored as normal integers; no special handling needed | ✅ |

    > ⚠️ **Important:** Java's default stack size can cause a `StackOverflowError` for deeply skewed trees (thousands of nodes) with the recursive approach. The iterative and Morris approaches are immune to this.

    ---

    ## 8. Final Summary

    | Approach | Time | Space | Recommended? |
    |----------|------|-------|--------------|
    | Recursive | O(n) | O(h) | ✅ For simplicity & readability |
    | Iterative Stack | O(n) | O(h) | ✅✅ Best for interviews |
    | Morris Traversal | O(n) | O(1) | ✅ Best for space-critical systems |

    ### What to Remember
    > **Pattern:** Inorder traversal is "Left → Root → Right." The iterative version teaches you exactly how recursive DFS maps to a manual stack — a technique that generalizes to **all tree and graph DFS problems**.

    > **Key Insight:** The moment an interviewer says *"can you do it without recursion?"* — reach for the explicit stack. And if they follow up with *"can you do it in O(1) space?"* — Morris Traversal is your answer.
    */
}
