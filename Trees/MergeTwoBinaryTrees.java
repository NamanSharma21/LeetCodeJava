package Trees;

import java.util.ArrayDeque;
import java.util.Queue;

import Datastructures.TreeNode;

public class MergeTwoBinaryTrees {
    public static void main(String[] args) {
        MergeTwoBinaryTrees mergeTwoBinaryTrees = new MergeTwoBinaryTrees();
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(3);
        root1.right = new TreeNode(2);
        root1.left.left = new TreeNode(5);


        TreeNode root2 = new TreeNode(2);
        root2.left = new TreeNode(1);
        root2.right = new TreeNode(3);
        root2.left.right = new TreeNode(4);
        root2.right.right = new TreeNode(7);
        System.out.println("MergeTwoBinaryTrees : \n"+mergeTwoBinaryTrees.mergeTreesBFSIterative(root1, root2));

    }

    /*
     * https://leetcode.com/problems/merge-two-binary-trees/description/?envType=
     * problem-list-v2&envId=tree
     * 
     * You are given two binary trees root1 and root2.
     * 
     * Imagine that when you put one of them to cover the other, some nodes of the
     * two trees are overlapped while the others are not. You need to merge the two
     * trees into a new binary tree. The merge rule is that if two nodes overlap,
     * then sum node values up as the new value of the merged node. Otherwise, the
     * NOT null node will be used as the node of the new tree.
     * 
     * Return the merged tree.
     * 
     * Note: The merging process must start from the root nodes of both trees.
     * 
     * Tree 1:
     *        1
     *       / \
     *      3   2
     *     /
     *    5
     * 
     *  Tree 2:
     *        2
     *       / \
     *      1   3
     *       \   \
     *        4   7
     *  Merged Tree:
     *        3
     *       / \
     *      4   5
     *     / \   \
     *    5   4   7
     * 
     * Example 1:
     * 
     * 
     * Input: root1 = [1,3,2,5], root2 = [2,1,3,null,4,null,7]
     * Output: [3,4,5,5,4,null,7]
     * Example 2:
     * 
     * Input: root1 = [1], root2 = [1,2]
     * Output: [2,2]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in both trees is in the range [0, 2000].
     * -104 <= Node.val <= 104
     */

    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null)
            return root2;
        if (root2 == null)
            return root1;
        TreeNode mergedTree = new TreeNode(root1.val + root2.val);
        mergedTree.left = mergeTrees(root1.left, root2.left);
        mergedTree.right = mergeTrees(root1.right, root2.right);
        return mergedTree;
    }


    public TreeNode mergeTreesInPlaceModification(TreeNode root1, TreeNode root2) {
        if (root1 == null)
            return root2;
        if (root2 == null)
            return root1;
        root1.val = root1.val + root2.val;
        root1.left = mergeTreesInPlaceModification(root1.left, root2.left);
        root1.right = mergeTreesInPlaceModification(root1.right, root2.right);
        return root1;
    }

    public TreeNode mergeTreesBFSIterative(TreeNode root1, TreeNode root2) {
        if (root1 == null)
            return root2;
        if (root2 == null)
            return root1;

        Queue<TreeNode[]> queue = new ArrayDeque<>();
        queue.offer(new TreeNode[] { root1, root2 });

        while (!queue.isEmpty()) {
            TreeNode[] pair = queue.poll();
            TreeNode node1 = pair[0];
            TreeNode node2 = pair[1];
            node1.val += node2.val;
            if (node1.left != null && node2.left != null) {
                queue.offer(new TreeNode[] { node1.left, node2.left });
            } else if (node1.left == null) {
                node1.left = node2.left;
            }

            if (node1.right != null && node2.right != null) {
                queue.offer(new TreeNode[] { node1.right, node2.right });
            } else if (node1.right == null) {
                node1.right = node2.right;
            }
        }
        return root1;
    }


    /*
    
    # Merge Two Binary Trees — Deep Dive

    ---

    ## 1. Problem Statement

    ### Plain English Restatement
    You are given two binary trees. Your task is to merge them into a single binary tree by **overlapping** them on top of each other. When two nodes overlap (i.e., both trees have a node at the same position), their values are **summed**. If only one tree has a node at a given position, that node is used as-is in the merged tree.

    ### Input Format
    - `TreeNode root1` — root of the first binary tree
    - `TreeNode root2` — root of the second binary tree

    ### Output Format
    - Return the `TreeNode` root of the **merged binary tree**

    ### Constraints
    - Number of nodes in each tree: `[0, 2000]`
    - Node values: `-10^4 ≤ Node.val ≤ 10^4`

    ### What Exactly Needs to Be Returned
    A single merged binary tree where:
    - If both trees have a node → merged node value = `node1.val + node2.val`
    - If only one tree has a node → use that node as-is
    - If neither tree has a node → `null`

    ---

    ## 2. Intuition

    ### The Core Idea
    Think of two transparent overhead projector sheets, each with a tree drawn on them. When you **stack** them on top of each other, nodes that land on top of each other get their values added, and nodes that have no counterpart just show through unchanged.

    ### How a Human Would Reason
    1. Start at the roots of both trees simultaneously.
    2. If both roots exist → create a new node with sum of both values, then recurse into both left and right subtrees.
    3. If only one root exists → just return that node (no merging needed).
    4. If neither exists → return null.

    ### What Makes This Interesting
    - It's a **beautiful recursive decomposition** — the same logic applies identically to every subtree.
    - You can solve it **in-place** (modifying tree1) or by **creating a new tree** — both are valid and have subtle trade-offs.
    - It elegantly tests whether you understand **simultaneous tree traversal**.

    ---

    ## 3. Approach Overview

    | # | Approach | Key Idea | Time | Space | Use When |
    |---|----------|----------|------|-------|----------|
    | 1 | **Recursive (New Tree)** | Build a brand-new merged tree via DFS recursion | O(min(m,n)) | O(min(m,n)) | Interview default — clean & safe |
    | 2 | **Recursive (In-Place)** | Modify tree1 directly during recursion | O(min(m,n)) | O(min(m,n)) | When mutation is allowed |
    | 3 | **Iterative (BFS Queue)** | Use a queue storing node pairs, merge level by level | O(min(m,n)) | O(min(m,n)) | When stack overflow risk is a concern |

    ### ✅ Recommended Approach
    **Approach 1 (Recursive — New Tree)** is the cleanest, most readable, and interview-preferred. Approach 3 (iterative) is preferred for extremely deep/skewed trees where recursion depth could hit Java's stack limit.

    ---

    ## 4. Detailed Solutions in Java

    ---

    ### ✅ Approach 1: Recursive — Build New Tree (Optimal & Recommended)

    #### Algorithm Steps
    1. **Base cases:** If `root1 == null`, return `root2`. If `root2 == null`, return `root1`.
    2. **Merge current nodes:** Create a new node with value = `root1.val + root2.val`.
    3. **Recurse left:** `merged.left = merge(root1.left, root2.left)`
    4. **Recurse right:** `merged.right = merge(root1.right, root2.right)`
    5. Return the merged node.

    ```java
    class Solution {

        public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
            // If one tree is missing at this position, return the other
            if (root1 == null) return root2;
            if (root2 == null) return root1;

            // Both nodes exist — create a new merged node with summed value
            TreeNode mergedNode = new TreeNode(root1.val + root2.val);

            // Recursively merge left and right subtrees
            mergedNode.left  = mergeTrees(root1.left,  root2.left);
            mergedNode.right = mergeTrees(root1.right, root2.right);

            return mergedNode;
        }
    }
    ```

    ---

    ### Approach 2: Recursive — In-Place Modification of Tree1

    #### Algorithm Steps
    1. **Base cases:** If `root1 == null`, return `root2`. If `root2 == null`, return `root1` (tree1 already has the right structure).
    2. **Modify in place:** Add `root2.val` directly into `root1.val`.
    3. **Recurse left and right** on `root1`, updating its children.
    4. Return modified `root1`.

    ```java
    class Solution {

        public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
            // If tree1 has no node here, use tree2's node as-is
            if (root1 == null) return root2;
            // If tree2 has no node here, tree1 stays unchanged
            if (root2 == null) return root1;

            // Merge tree2's value into tree1's node directly
            root1.val += root2.val;

            // Recurse into children, modifying tree1 in place
            root1.left  = mergeTrees(root1.left,  root2.left);
            root1.right = mergeTrees(root1.right, root2.right);

            return root1;
        }
    }
    ```

    > ⚠️ **Trade-off:** This mutates the original `root1` tree. If the caller needs `root1` unchanged after the call, use Approach 1 instead.

    ---

    ### Approach 3: Iterative — BFS with Queue of Node Pairs

    #### Algorithm Steps
    1. If either root is null, return the other immediately (base case).
    2. Push a pair `(root1, root2)` onto a queue.
    3. While the queue is not empty:
    - Dequeue a pair `(node1, node2)`.
    - Add `node2.val` to `node1.val` (we build the result into tree1 iteratively).
    - **Left children:** If both exist, push the left pair. If only `node2.left` exists, assign it to `node1.left`.
    - **Right children:** Same logic for right.
    4. Return `root1` as the merged tree.

    ```java
    import java.util.LinkedList;
    import java.util.Queue;

    class Solution {

        public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
            // Edge cases: if one tree is empty, return the other
            if (root1 == null) return root2;
            if (root2 == null) return root1;

            // Queue holds pairs of nodes to be merged simultaneously
            Queue<TreeNode[]> queue = new LinkedList<>();
            queue.offer(new TreeNode[]{root1, root2});

            while (!queue.isEmpty()) {
                TreeNode[] pair  = queue.poll();
                TreeNode   node1 = pair[0];
                TreeNode   node2 = pair[1];

                // Both nodes exist at this position — add tree2's value into tree1
                node1.val += node2.val;

                // Process left children
                if (node1.left != null && node2.left != null) {
                    // Both have left children — enqueue for further merging
                    queue.offer(new TreeNode[]{node1.left, node2.left});
                } else if (node1.left == null) {
                    // Tree1 missing left child — borrow tree2's
                    node1.left = node2.left;
                }
                // If node2.left == null, node1.left is already correct — no action needed

                // Process right children (same logic)
                if (node1.right != null && node2.right != null) {
                    queue.offer(new TreeNode[]{node1.right, node2.right});
                } else if (node1.right == null) {
                    node1.right = node2.right;
                }
            }

            return root1; // tree1 now holds the merged result
        }
    }
    ```

    ---

    ## 5. Time & Space Complexity

    ### Approach 1 & 2: Recursive

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(min(m, n)) | We only visit nodes where **both** trees have a node. Once one tree runs out of nodes, recursion returns immediately — we never traverse the unmatched portion. |
    | **Space** | O(min(m, n)) | The recursion call stack depth equals the depth of the **smaller** tree. In the worst case (perfectly balanced trees), this is O(log(min(m,n))). In the worst case (completely skewed trees), it's O(min(m,n)). |

    **Example walkthrough — sizes:**
    - Tree1 has 7 nodes (depth 3), Tree2 has 4 nodes (depth 3)
    - We visit at most 4 nodes where both overlap → ~4 recursive calls
    - Stack depth ≤ 3 frames

    ---

    ### Approach 3: Iterative BFS

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(min(m, n)) | Same reasoning — only overlapping nodes are enqueued and processed |
    | **Space** | O(min(m, n)) | Queue holds at most the width of the smaller tree at any level. For a balanced tree, max queue size = O(min(m,n)/2) ≈ O(min(m,n)) |

    **Why iterative over recursive?**
    - Java's default stack size is ~512KB–1MB. A highly skewed tree with 2000 nodes could cause `StackOverflowError` in recursion.
    - Iterative BFS uses heap memory (the queue), which is far more generous.

    ---

    ## 6. Complete Worked Examples

    ---

    ### Example for Approach 1 (Recursive — New Tree)

    #### Input:
    ```
    Tree 1:        Tree 2:
        1               2
    / \             / \
    3   2           1   3
    /                 \   \
    5                   4   7
    ```

    #### Step-by-Step Execution:

    ```
    merge(1, 2)
    → Both exist → new node(1+2=3)
    → merge(3, 1)  [left subtree]
        → Both exist → new node(3+1=4)
        → merge(5, null)  [left]
            → root2 is null → return node(5)
        → merge(null, 4)  [right]
            → root1 is null → return node(4)
        → returns node(4) with left=5, right=4
    → merge(2, 3)  [right subtree]
        → Both exist → new node(2+3=5)
        → merge(null, null) [left] → null
        → merge(null, 7)   [right] → return node(7)
        → returns node(5) with right=7
    → returns node(3) with left=node(4), right=node(5)
    ```

    #### Output:
    ```
        3
        / \
        4   5
    / \   \
    5   4   7
    ```

    ---

    ### Example for Approach 3 (Iterative BFS)

    #### Input: Same trees as above

    #### Queue State Trace:

    | Step | Queue Contents | Action |
    |------|---------------|--------|
    | Start | `[(1,2)]` | Init queue with roots |
    | Poll (1,2) | `[(3,1), (2,3)]` | node1.val=1+2=3; both left children exist→enqueue; both right children exist→enqueue |
    | Poll (3,1) | `[(2,3), (5,null?), (null,4)]` | node1.val=3+1=4; node1.left=5, node2.left=null→no action; node1.right=null, node2.right=4→node1.right=4 |
    | Poll (2,3) | `[]` | node1.val=2+3=5; no left children; node1.right=null, node2.right=7→node1.right=7 |
    | Queue empty | — | Done |

    #### Final tree (tree1 modified in place):
    ```
        3
        / \
        4   5
    / \   \
    5   4   7
    ```

    ---

    ## 7. Edge Cases

    | Edge Case | Description | How Handled |
    |-----------|-------------|-------------|
    | **Both trees null** | `root1=null, root2=null` | Approach 1/2: `root1==null` → return `root2` (which is also null) ✅ |
    | **One tree is null** | `root1=null, root2={...}` | Return the non-null tree immediately — no merging needed ✅ |
    | **Single node trees** | Both trees have just a root | Recurse → both children are null → base cases fire immediately ✅ |
    | **Completely skewed trees** | All nodes go left (like a linked list) | Recursive depth = n → risk of `StackOverflowError` for n=2000. Use iterative (Approach 3) for safety ⚠️ |
    | **Highly unbalanced overlap** | Tree1 is deep, Tree2 is shallow | Recursion stops early when tree2 runs out — tree1 nodes just get returned as-is ✅ |
    | **Negative values** | Node values like `-10^4` | Simple addition handles negatives correctly; no overflow risk since `-10^4 + -10^4 = -20000` fits in `int` ✅ |
    | **Value overflow** | Max: `10^4 + 10^4 = 20000` | Well within `int` range (~2.1 billion) ✅ |
    | **Identical tree structures** | Both trees perfectly mirror each other | Every node pair is processed — O(n) where n = size of either tree ✅ |

    ---

    ## 8. Final Summary

    ### Comparison Table

    | Approach | Code Clarity | Mutates Input | Stack Safe | Recommended |
    |----------|-------------|---------------|------------|-------------|
    | Recursive (New Tree) | ⭐⭐⭐⭐⭐ | ❌ No | ⚠️ Mostly | ✅ **Yes** |
    | Recursive (In-Place) | ⭐⭐⭐⭐ | ✅ Yes | ⚠️ Mostly | Conditionally |
    | Iterative BFS | ⭐⭐⭐ | ✅ Yes | ✅ Yes | For deep trees |

    ### What to Remember
    > 🧠 **Pattern:** *Simultaneous DFS/BFS on two trees* — whenever you need to process two trees in parallel, traverse both trees at the same time using the same recursive/iterative structure, handling the three cases: both present, only left present, only right present.

    > 💡 **Technique:** The base case `if (root == null) return otherRoot` is the elegant key that handles all structural asymmetry in a single line — no need for messy null checks scattered throughout.

    ---

    ## 🏢 Companies & Frequency

    | Company | Frequency | Notes |
    |---------|-----------|-------|
    | **Facebook / Meta** | ⭐⭐⭐⭐⭐ Very High | One of their most frequently asked easy tree problems |
    | **Amazon** | ⭐⭐⭐⭐ High | Common in phone screens |
    | **Microsoft** | ⭐⭐⭐⭐ High | Appears in online assessments |
    | **Google** | ⭐⭐⭐ Medium | Occasionally used as a warm-up tree problem |
    | **Bloomberg** | ⭐⭐⭐ Medium | Seen in early interview rounds |
    | **Apple** | ⭐⭐ Low-Medium | Reported occasionally |
    | **Uber** | ⭐⭐ Low-Medium | Seen in intern/new grad screens |

    ### Overall Appearance Stats
    - **LeetCode Problem #617** — rated **Easy**
    - Appeared in **500+ interview reports** on LeetCode discuss
    - Consistently ranks in the **Top 50 most-asked tree problems** in FAANG interviews
    - Particularly beloved by **Meta/Facebook interviewers** as a quick tree fundamentals check before harder follow-ups
    */
}
