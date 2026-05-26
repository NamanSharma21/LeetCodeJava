package Trees;

import java.util.ArrayList;
import java.util.List;

import Datastructures.TreeNode;

public class FlattenBinaryTreeToLinkedList {
    public static void main(String[] args) {
        FlattenBinaryTreeToLinkedList flattenBinaryTreeToLinkedList = new FlattenBinaryTreeToLinkedList();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(6);
        System.out.println("FlattenBinaryTreeToLinkedList : ");
        flattenBinaryTreeToLinkedList.flatten(root);

        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(5);
        root1.left.left = new TreeNode(3);
        root1.left.right = new TreeNode(4);
        root1.right.right = new TreeNode(6);
        System.out.println("FlattenBinaryTreeToLinkedList : ");
        flattenBinaryTreeToLinkedList.flattenPreOrderList(root1);

        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(5);
        root2.left.left = new TreeNode(3);
        root2.left.right = new TreeNode(4);
        root2.right.right = new TreeNode(6);
        System.out.println("FlattenBinaryTreeToLinkedList : ");
        flattenBinaryTreeToLinkedList.flattenReversePreOrder(root2);
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/flatten-binary-tree-to-linked-list/description/?envType=problem-list-v2&envId=tree
     * 
     * Given the root of a binary tree, flatten the tree into a "linked list":
     * 
     * The "linked list" should use the same TreeNode class where the right child
     * pointer points to the next node in the list and the left child pointer is
     * always null.
     * The "linked list" should be in the same order as a pre-order traversal of the
     * binary tree.
     * 
     * 
     * Example 1:
     * 
     * Input Tree Structure:
     *       1
     *      / \
     *     2   5
     *    / \   \
     *   3   4   6
     *
     * Flattened Output (Pre-order Traversal):
     *   1
     *    \
     *     2
     *      \
     *       3
     *        \
     *         4
     *          \
     *           5
     *            \
     *             6
     * 
     * Input: root = [1,2,5,3,4,null,6]
     * Output: [1,null,2,null,3,null,4,null,5,null,6]
     * Example 2:
     * 
     * Input: root = []
     * Output: []
     * Example 3:
     * 
     * Input: root = [0]
     * Output: [0]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [0, 2000].
     * -100 <= Node.val <= 100
     * 
     * 
     * Follow up: Can you flatten the tree in-place (with O(1) extra space)?
     */
    // @formatter:on

    public void flattenPreOrderList(TreeNode root) {
        if (root == null)
            return;
        List<TreeNode> preOrderList = new ArrayList<>();
        collectPreOrder(root, preOrderList);

        for (int i = 0; i < preOrderList.size() - 1; i++) {
            TreeNode current = preOrderList.get(i);
            current.left = null;
            current.right = preOrderList.get(i + 1);
        }

        preOrderList.get(preOrderList.size() - 1).right = null;
        preOrderList.get(preOrderList.size() - 1).left = null;
        System.out.println("" + root);
    }

    public void collectPreOrder(TreeNode root, List<TreeNode> preOrderList) {
        if (root == null)
            return;
        preOrderList.add(root);
        collectPreOrder(root.left, preOrderList);
        collectPreOrder(root.right, preOrderList);
    }

    private TreeNode previousNode = null;

    public void flattenReversePreOrder(TreeNode root) {
        if (root == null)
            return;
        flatten(root.right);
        flatten(root.left);
        root.right = previousNode;
        root.left = null;
        previousNode = root;
    }

    public void flatten(TreeNode root) {
        TreeNode current = root;
        while (current != null) {
            if (current.left != null) {
                TreeNode rightMostNode = findRightMostNode(current.left);
                rightMostNode.right = current.right;
                current.right = current.left;
                current.left = null;
            }
            current = current.right;
        }
        System.out.println("" + root);
    }

    public TreeNode findRightMostNode(TreeNode root) {
        while (root.right != null) {
            root = root.right;
        }
        return root;
    }
}

// @formatter:off
/*
 * ============================================================
 * Flatten Binary Tree to Linked List (LeetCode 114)
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * In Plain English:
 * -----------------
 * Given the root of a binary tree, flatten it into a linked list
 * in-place. The linked list should use the same TreeNode class,
 * where every node's left child is null and the right child points
 * to the next node in the list. The order of nodes in the list
 * must follow pre-order traversal (Root -> Left -> Right).
 *
 * Input Format:
 * -------------
 * - A TreeNode root of a binary tree
 * - TreeNode has: int val, TreeNode left, TreeNode right
 * - Number of nodes: 0 <= n <= 2000
 * - Node values: -100 <= val <= 100
 *
 * Output Format:
 * --------------
 * - No return value (void) -- modify the tree in-place
 * - After the call, the tree root should be the head of a
 *   right-skewed linked list
 *
 * What Needs to Be Done:
 * ----------------------
 *
 *   Input Tree:              Output (linked list as tree):
 *
 *         1                       1
 *        / \                       \
 *       2   5          ->           2
 *      / \   \                       \
 *     3   4   6                       3
 *                                      \
 *                                       4
 *                                        \
 *                                         5
 *                                          \
 *                                           6
 *
 *   Pre-order: 1 -> 2 -> 3 -> 4 -> 5 -> 6
 *
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * Core Idea:
 * ----------
 * Pre-order traversal visits: Root, then Left subtree, then Right
 * subtree. The flattened list must mirror this exact order. The
 * challenge is: how do we rewire left/right pointers while not
 * losing track of nodes?
 *
 * How a Human Reasons About It:
 * ------------------------------
 * 1. Start at root 1. It has left child 2 and right child 5.
 * 2. The left subtree (2->3->4) should come BEFORE right (5->6).
 * 3. Take the rightmost node of the left subtree (that's 4) and
 *    attach the right subtree (5->6) to it.
 * 4. Move the entire left subtree to the right and null the left.
 * 5. Repeat this process for every node moving down the right spine.
 *
 * Why It's Tricky:
 * ----------------
 * - You must NOT lose the right subtree when rewiring the left subtree
 * - Doing this recursively requires careful ordering of operations
 * - Doing it iteratively is elegant but requires understanding
 *   Morris Traversal style pointer manipulation
 *
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 *  #  | Approach                    | Time  | Space | When to Use
 * ----+-----------------------------+-------+-------+---------------------
 *  1  | Pre-order + List            | O(n)  | O(n)  | Easiest to understand
 *  2  | Recursive Post-order        | O(n)  | O(h)  | Clean recursive thinking
 *  3  | Iterative (Morris-style) *  | O(n)  | O(1)  | Optimal for interviews
 *
 *  * OPTIMAL: Approach 3 -- O(n) time, O(1) space, fully in-place,
 *    no recursion stack concerns.
 *
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Pre-order Traversal + List Rewiring
 * ------------------------------------------------------------
 *
 * Algorithm:
 * 1. Perform a standard pre-order traversal, collect all nodes
 *    in a List<TreeNode>.
 * 2. Iterate through the list. For each node:
 *    - Set left  = null
 *    - Set right = next node in list (or null for last node)
 *
 * ------------------------------------------------------------
 *
 * class Solution {
 *     public void flatten(TreeNode root) {
 *         if (root == null) return;
 *
 *         List<TreeNode> preOrderNodes = new ArrayList<>();
 *         collectPreOrder(root, preOrderNodes);
 *
 *         for (int i = 0; i < preOrderNodes.size() - 1; i++) {
 *             TreeNode current = preOrderNodes.get(i);
 *             current.left  = null;                       // always null in linked list
 *             current.right = preOrderNodes.get(i + 1);  // next node in pre-order
 *         }
 *
 *         // last node has no right child
 *         preOrderNodes.get(preOrderNodes.size() - 1).left  = null;
 *         preOrderNodes.get(preOrderNodes.size() - 1).right = null;
 *     }
 *
 *     private void collectPreOrder(TreeNode node, List<TreeNode> result) {
 *         if (node == null) return;
 *         result.add(node);                        // visit root first
 *         collectPreOrder(node.left,  result);     // then left
 *         collectPreOrder(node.right, result);     // then right
 *     }
 * }
 *
 * ------------------------------------------------------------
 * Approach 2: Recursive (Reverse Pre-order / Right-Left-Root)
 * ------------------------------------------------------------
 *
 * Algorithm:
 * Process nodes in REVERSE pre-order (Right -> Left -> Root).
 * Maintain a previousNode pointer. As we visit each node in reverse:
 * 1. Set node.right = previousNode
 * 2. Set node.left  = null
 * 3. Update previousNode = node
 *
 * This builds the list from the TAIL to the HEAD, which is elegant
 * and avoids losing pointers.
 *
 * Why Reverse Pre-order Works:
 * - Pre-order is Root->Left->Right
 * - Reverse is Right->Left->Root
 * - By building from the end, when we set root.right = previousNode,
 *   previousNode is already the correctly flattened tail.
 *
 * ------------------------------------------------------------
 *
 * class Solution {
 *     private TreeNode previousNode = null;  // tracks last processed node
 *
 *     public void flatten(TreeNode root) {
 *         if (root == null) return;
 *
 *         // Process in REVERSE pre-order: right -> left -> root
 *         flatten(root.right);
 *         flatten(root.left);
 *
 *         // Wire current node to the previously processed node
 *         root.right    = previousNode;
 *         root.left     = null;
 *         previousNode  = root;
 *     }
 * }
 *
 * ------------------------------------------------------------
 * Approach 3: Iterative Morris-Style (OPTIMAL) *** RECOMMENDED ***
 * ------------------------------------------------------------
 *
 * Algorithm:
 * For every node on the right spine of the tree:
 * 1. If current node has a left child:
 *    a. Find the RIGHTMOST node of its left subtree (the "predecessor")
 *    b. Attach current node's right subtree to that predecessor's right
 *    c. Move the left subtree to the right
 *    d. Set left to null
 * 2. Move to next node via current = current.right
 *
 * Visual Walkthrough of One Step:
 *
 *   Before:                    After one iteration at node 1:
 *         1                           1
 *        / \                           \
 *       2   5          ->               2
 *      / \   \                         / \
 *     3   4   6                       3   4
 *                                          \
 *                                           5
 *                                            \
 *                                             6
 *
 * ------------------------------------------------------------
 *
 * class Solution {
 *     public void flatten(TreeNode root) {
 *         TreeNode current = root;
 *
 *         while (current != null) {
 *             if (current.left != null) {
 *                 // Step 1: Find rightmost node in the left subtree
 *                 TreeNode rightmostOfLeft = findRightmost(current.left);
 *
 *                 // Step 2: Attach current's right subtree to that rightmost node
 *                 rightmostOfLeft.right = current.right;
 *
 *                 // Step 3: Move left subtree to the right
 *                 current.right = current.left;
 *                 current.left  = null;  // clear left pointer
 *             }
 *
 *             // Move to next node in the (now modified) right chain
 *             current = current.right;
 *         }
 *     }
 *
 *     private TreeNode findRightmost(TreeNode node) {
 *         while (node.right != null) {
 *             node = node.right;
 *         }
 *         return node;
 *     }
 * }
 *
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY (WITH REASONING)
 * ============================================================
 *
 *  Approach  | Time  | Space | Reasoning
 * -----------+-------+-------+----------------------------------------
 *  1 (List)  | O(n)  | O(n)  | Visit each node once; store all n nodes
 *  2 (Recur) | O(n)  | O(h)  | Visit each node once; call stack depth
 *            |       |       | = tree height h (O(log n) balanced,
 *            |       |       | O(n) worst case)
 *  3 (Iter)  | O(n)  | O(1)  | Each node visited at most twice: once
 *            |       |       | as current, once as rightmost finder
 *
 * Worked Complexity Example (Approach 3) for 6 nodes:
 * - Node 1: find rightmost of left subtree (2->4) = 2 steps
 * - Node 2: find rightmost of left subtree (3)   = 1 step
 * - Nodes 3,4,5,6: no left child -> just move right, 1 op each
 *   Total ~= O(n)
 * Each node is visited as "rightmost" at most once across all iterations.
 *
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES FOR EACH APPROACH
 * ============================================================
 *
 * Input Tree: [1, 2, 5, 3, 4, null, 6]
 *
 *       1
 *      / \
 *     2   5
 *    / \   \
 *   3   4   6
 *
 * ------------------------------------------------------------
 * Approach 3 (Iterative) -- Step-by-Step:
 * ------------------------------------------------------------
 *
 * Step 1: current = node(1)
 *   - Has left child (node 2) [YES]
 *   - Find rightmost of left subtree: 2 -> 4 => rightmost = node(4)
 *   - Attach node(1)'s right (5->6) to node(4): 4.right = 5
 *   - Move left to right: 1.right = 2, 1.left = null
 *
 *   State:  1 -> 2
 *              / \
 *             3   4
 *                  \
 *                   5
 *                    \
 *                     6
 *
 * Step 2: current = node(2)
 *   - Has left child (node 3) [YES]
 *   - Find rightmost of left subtree: node(3)
 *   - Attach node(2)'s right (4->5->6) to node(3): 3.right = 4
 *   - Move left to right: 2.right = 3, 2.left = null
 *
 *   State:  1 -> 2 -> 3
 *                      \
 *                       4
 *                        \
 *                         5
 *                          \
 *                           6
 *
 * Step 3: current = node(3) -- no left child, move right
 * Step 4: current = node(4) -- no left child, move right
 * Step 5: current = node(5) -- no left child, move right
 * Step 6: current = node(6) -- no left child, move right
 * Step 7: current = null    -- loop ends [DONE]
 *
 * Final Result: 1 -> 2 -> 3 -> 4 -> 5 -> 6
 * (all left = null, right = next)
 *
 * ------------------------------------------------------------
 * Approach 2 (Recursive) -- Step-by-Step:
 * Reverse pre-order visits: 6, 5, 4, 3, 2, 1
 * ------------------------------------------------------------
 *
 *  Step | Node | Action                             | previousNode
 * ------+------+------------------------------------+-------------
 *    1  |  6   | 6.right=null,   6.left=null        | node(6)
 *    2  |  5   | 5.right=node(6),5.left=null        | node(5)
 *    3  |  4   | 4.right=node(5),4.left=null        | node(4)
 *    4  |  3   | 3.right=node(4),3.left=null        | node(3)
 *    5  |  2   | 2.right=node(3),2.left=null        | node(2)
 *    6  |  1   | 1.right=node(2),1.left=null        | node(1)
 *
 * Result: 1 -> 2 -> 3 -> 4 -> 5 -> 6 [CORRECT]
 *
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 *  Edge Case             | Input           | Expected | A1 | A2 | A3
 * -----------------------+-----------------+----------+----+----+----
 *  Null root             | null            | nothing  | OK | OK | OK
 *  Single node           | [1]             | [1]      | OK | OK | OK
 *  Already flat          | [1,null,2,null,3| same     | OK | OK | OK
 *  Left-skewed tree      | [1,2,null,3]    | 1->2->3  | OK | OK | OK
 *  Perfect binary tree   | [1,2,3,4,5,6,7] | preorder | OK | OK | OK
 *  Two nodes, left only  | [1,2]           | 1->2     | OK | OK | OK
 *  Two nodes, right only | [1,null,2]      | 1->2     | OK | OK | OK
 *
 * Potential Risk:
 * ---------------
 * Approach 2 with highly unbalanced trees (n=2000, left-skewed)
 * => recursion depth O(n) => possible STACK OVERFLOW in Java
 * (default stack ~500-1000 frames for complex calls).
 * Approach 3 avoids this entirely.
 *
 *
 * ============================================================
 * 8. FINAL SUMMARY
 * ============================================================
 *
 *  Criterion               | Approach 1 | Approach 2 | Approach 3 ***
 * -------------------------+------------+------------+---------------
 *  Ease of understanding   | Easiest    | Medium     | Needs insight
 *  Time complexity         | O(n)       | O(n)       | O(n)
 *  Space complexity        | O(n)       | O(h)       | O(1)
 *  In-place?               | No         | Yes        | Yes
 *  Stack overflow risk?    | No         | Yes        | No
 *  Recommended interview?  | Fallback   | Good       | BEST
 *
 * What to Remember:
 * -----------------
 * PATTERN: "Find the predecessor, attach the dangling subtree, rewire"
 * -- this is the same core idea as Morris Traversal. Whenever you see
 * an in-place tree problem with O(1) space, think about predecessor-
 * based pointer threading.
 *
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 *  Company         | Frequency       | Notes
 * -----------------+-----------------+--------------------------
 *  Microsoft       | Very High *5    | Among top 20 most asked
 *  Amazon          | Very High *5    | Frequently in SDE interviews
 *  Google          | High *4         | L4/L5 rounds
 *  Facebook/Meta   | High *4         | Production engineering rounds
 *  Apple           | Medium *3       | iOS/software roles
 *  Bloomberg       | Medium *3       | Financial systems interviews
 *  Adobe           | Medium *3       | SDE roles
 *  Uber            | Medium *2       | Backend rounds
 *
 * LeetCode stats: This problem has appeared 500+ times in reported
 * interview experiences and has been solved by millions. It is
 * classified as Medium but the O(1) space solution is considered
 * hard-level thinking.
 *
 * Core Takeaway: Master the iterative Morris-style approach -- it
 * demonstrates deep understanding of pointer manipulation, in-place
 * algorithms, and binary tree structure, exactly what top-tier
 * interviewers are looking for.
 * ============================================================
 */
// @formatter:on
