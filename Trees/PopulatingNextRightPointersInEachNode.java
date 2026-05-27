package Trees;

import java.util.LinkedList;
import java.util.Queue;

import Datastructures.NodeTree;

public class PopulatingNextRightPointersInEachNode {
    public static void main(String[] args) {
        PopulatingNextRightPointersInEachNode populatingNextRightPointersInEachNode = new PopulatingNextRightPointersInEachNode();
        NodeTree root = new NodeTree(1);
        root.left = new NodeTree(2);
        root.right = new NodeTree(3);
        root.left.left = new NodeTree(4);
        root.left.right = new NodeTree(5);
        root.right.left = new NodeTree(6);
        root.right.right = new NodeTree(7);

        NodeTree root1 = root;
        NodeTree root2 = root;
        System.out.println(
                "PopulatingNextRightPointersInEachNode : "
                        + populatingNextRightPointersInEachNode.connectIterativeBFS(root).toTreeString());

        System.out.println(
                "PopulatingNextRightPointersInEachNode : "
                        + populatingNextRightPointersInEachNode.connectReccursiveDFS(root1).toTreeString());

        System.out.println(
                "PopulatingNextRightPointersInEachNode : "
                        + populatingNextRightPointersInEachNode.connectIterativeDFS(root2).toTreeString());
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/populating-next-right-pointers-in-each-node/description/?envType=problem-list-v2&envId=tree
     * 
     * You are given a perfect binary tree where all leaves are on the same level,
     * and every parent has two children. The binary tree has the following
     * definition:
     * 
     * struct Node {
     * int val;
     * Node *left;
     * Node *right;
     * Node *next;
     * }
     * Populate each next pointer to point to its next right node. If there is no
     * next right node, the next pointer should be set to NULL.
     * 
     * Initially, all next pointers are set to NULL.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * 
     *         1
     *     /       \
     *    2   ->    3
     *   / \       / \
     *  4 -> 5 -> 6 -> 7
     * 
     * Output Serialization Logic:
     * Level 1: 1 -> #
     * Level 2: 2 -> 3 -> #
     * Level 3: 4 -> 5 -> 6 -> 7 -> #
     * 
     * Input: root = [1,2,3,4,5,6,7]
     * Output: [1,#,2,3,#,4,5,6,7,#]
     * Explanation: Given the above perfect binary tree (Figure A), your function
     * should populate each next pointer to point to its next right node, just like
     * in Figure B. The serialized output is in level order as connected by the next
     * pointers, with '#' signifying the end of each level.
     * Example 2:
     * 
     * Input: root = []
     * Output: []
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [0, 212 - 1].
     * -1000 <= Node.val <= 1000
     * 
     * 
     * Follow-up:
     * 
     * You may only use constant extra space.
     * The recursive approach is fine. You may assume implicit stack space does not
     * count as extra space for this problem.
     */
    // @formatter:on

    public NodeTree connectIterativeBFS(NodeTree root) {
        if (root == null)
            return null;
        Queue<NodeTree> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int queSize = queue.size();
            for (int i = 0; i < queSize; i++) {
                NodeTree current = queue.poll();
                if (i < queSize - 1) {
                    current.next = queue.peek();
                }

                if (current.left != null)
                    queue.offer(current.left);
                if (current.right != null)
                    queue.offer(current.right);
            }
        }
        return root;
    }

    public NodeTree connectReccursiveDFS(NodeTree root) {
        if (root == null || root.left == null)
            return root;
        root.left.next = root.right;
        if (root.next != null)
            root.right.next = root.next.left;

        connectReccursiveDFS(root.left);
        connectReccursiveDFS(root.right);
        return root;
    }

    public NodeTree connectIterativeDFS(NodeTree root) {
        if (root == null)
            return null;
        NodeTree leftMost = root;
        while (leftMost.left != null) {
            NodeTree current = leftMost;
            while (current != null) {
                current.left.next = current.right;
                if (current.next != null)
                    current.right.next = current.next.left;
                current = current.next;
            }
            leftMost = leftMost.left;
        }
        return root;
    }

}

// @formatter:off
/*
 * # Populating Next Right Pointers in Each Node
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 1. PROBLEM STATEMENT
 * ─────────────────────────────────────────────────────────────────────────────
 *
 * IN PLAIN ENGLISH:
 * You are given a perfect binary tree — every internal node has exactly two
 * children, and all leaf nodes are at the same level.
 *
 * Each node has an extra pointer called `next`, initially set to null. Your
 * task is to populate every node's next pointer to point to the next right
 * node on the same level. If there is no next right node, next should remain
 * null.
 *
 * NODE STRUCTURE (Given):
 *
 *   class Node {
 *       public int val;
 *       public Node left;
 *       public Node right;
 *       public Node next;
 *   }
 *
 * INPUT:
 *   - Root of a perfect binary tree with 0 to 2^12 - 1 = 4095 nodes.
 *   - Node values are in range [-1000, 1000].
 *
 * OUTPUT:
 *   - The same root, with all next pointers correctly populated.
 *
 * VISUAL EXAMPLE:
 *
 *   Input:                          Output:
 *           1                               1 -> NULL
 *         /   \                           /   \
 *        2     3            ->           2  ->  3 -> NULL
 *       / \   / \                       / \   / \
 *      4   5 6   7                     4-> 5-> 6-> 7 -> NULL
 *
 * KEY CONSTRAINT:
 *   The tree is always a perfect binary tree — this enables the most elegant
 *   solution.
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 2. INTUITION
 * ─────────────────────────────────────────────────────────────────────────────
 *
 * THE CORE IDEA:
 *   Think of each level of the tree as a linked list waiting to be built.
 *   You're converting a tree level-by-level into a series of linked lists
 *   using the next pointer.
 *
 * HOW A HUMAN REASONS ABOUT IT:
 *   1. At the root level, there's only one node — nothing to connect.
 *   2. At level 2, you need to connect 2 -> 3.
 *   3. At level 3, you need to connect 4 -> 5 -> 6 -> 7.
 *
 *   The tricky part is connecting nodes that don't share a parent (like 5->6).
 *   Both 5 and 6 are children of different parents (2 and 3), yet they need
 *   to be linked.
 *
 * THE ELEGANT INSIGHT:
 *   If the next pointers on the current level are already set, you can use
 *   them to traverse across and set next pointers on the level below —
 *   without any extra data structures! This leads to the O(1) space solution.
 *
 * WHAT MAKES IT TRICKY:
 *   - Connecting across subtree boundaries (e.g., 5 -> 6) requires knowing
 *     the next pointer of the parent level.
 *   - A perfect binary tree guarantees every node has 0 or 2 children,
 *     which simplifies logic greatly.
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 3. APPROACH OVERVIEW
 * ─────────────────────────────────────────────────────────────────────────────
 *
 *   #  | Approach                   | Space    | Time | Use When
 *   ---|----------------------------|----------|------|----------------------
 *   1  | BFS (Level Order)          | O(n)     | O(n) | Easiest; works on
 *      |                            |          |      | imperfect trees too
 *   2  | Recursive DFS              | O(log n) | O(n) | Clean code, good for
 *      |                            |          |      | interviews
 *   3  | Iterative O(1) Space [OPT] | O(1)     | O(n) | OPTIMAL — best for
 *      |                            |          |      | interviews
 *
 *   OPTIMAL APPROACH: Iterative O(1) Space
 *   Uses the structure of the perfect binary tree and the already-populated
 *   next pointers to avoid any extra memory.
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 4. DETAILED SOLUTIONS IN JAVA
 * ─────────────────────────────────────────────────────────────────────────────
 *
 * ── APPROACH 1: BFS (Level Order Traversal) ──────────────────────────────────
 *
 * ALGORITHM:
 *   1. Push the root into a queue.
 *   2. For each level, determine the number of nodes (levelSize).
 *   3. Process nodes one by one; connect each node's next to the next node
 *      dequeued (except the last in each level).
 *   4. Enqueue left and right children.
 *
 *   import java.util.LinkedList;
 *   import java.util.Queue;
 *
 *   public Node connect(Node root) {
 *       if (root == null) return null;
 *
 *       Queue<Node> queue = new LinkedList<>();
 *       queue.offer(root);
 *
 *       while (!queue.isEmpty()) {
 *           int levelSize = queue.size(); // number of nodes at current level
 *
 *           for (int i = 0; i < levelSize; i++) {
 *               Node current = queue.poll();
 *
 *               // Connect to next node in same level (not for the last node)
 *               if (i < levelSize - 1) {
 *                   current.next = queue.peek();
 *               }
 *
 *               // Enqueue children for next level processing
 *               if (current.left != null)  queue.offer(current.left);
 *               if (current.right != null) queue.offer(current.right);
 *           }
 *       }
 *       return root;
 *   }
 *
 * ── APPROACH 2: Recursive DFS ────────────────────────────────────────────────
 *
 * ALGORITHM:
 *   1. For each node, connect left child -> right child (same parent).
 *   2. Connect right child -> left child of next sibling (cross-subtree),
 *      using node.next.
 *   3. Recurse left first, then right.
 *
 *   public Node connect(Node root) {
 *       if (root == null || root.left == null) return root;
 *
 *       // Case 1: Connect left child to right child (same parent)
 *       root.left.next = root.right;
 *
 *       // Case 2: Connect right child to left child of root's next sibling
 *       if (root.next != null) {
 *           root.right.next = root.next.left;
 *       }
 *
 *       connect(root.left);
 *       connect(root.right);
 *       return root;
 *   }
 *
 *   NOTE: This works because we process top-down. By the time we recurse into
 *   children, the parent's next is already set, so root.next.left is accessible.
 *
 * ── APPROACH 3: Iterative O(1) Space [OPTIMAL] ───────────────────────────────
 *
 * ALGORITHM:
 *   1. Start at the leftmost node of each level.
 *   2. Use already-connected next pointers on the current level to traverse
 *      across and populate the next level's next pointers.
 *   3. Move leftmost down one level after finishing each level.
 *
 *   public Node connect(Node root) {
 *       if (root == null) return null;
 *
 *       Node leftmost = root; // first node of the current level
 *
 *       while (leftmost.left != null) { // stop when we reach leaf level
 *           Node current = leftmost;    // traverse using next pointers
 *
 *           while (current != null) {
 *               // Connection 1: left child -> right child (same parent)
 *               current.left.next = current.right;
 *
 *               // Connection 2: right child -> left child of next node
 *               if (current.next != null) {
 *                   current.right.next = current.next.left;
 *               }
 *
 *               // Move to the next node in the current level
 *               current = current.next;
 *           }
 *
 *           // Move down to the first node of the next level
 *           leftmost = leftmost.left;
 *       }
 *       return root;
 *   }
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 5. TIME & SPACE COMPLEXITY (WITH REASONING)
 * ─────────────────────────────────────────────────────────────────────────────
 *
 * APPROACH 1: BFS
 *   Time:  O(n) — Each node is enqueued and dequeued exactly once.
 *   Space: O(n) — Queue holds at most the largest level = n/2 nodes
 *                 (last level of a perfect binary tree).
 *   Example: n=15 nodes (4-level tree) → queue holds up to 8 nodes at
 *            level 4. ~15 enqueue + 15 dequeue = 30 operations.
 *
 * APPROACH 2: Recursive DFS
 *   Time:  O(n)     — Every node is visited exactly once.
 *   Space: O(log n) — Call stack depth = height of perfect tree = log2(n).
 *   Example: n=1023 nodes (10 levels) → recursion depth = 10.
 *
 * APPROACH 3: Iterative O(1) Space [OPTIMAL]
 *   Time:  O(n) — Every node is visited exactly once across all levels.
 *   Space: O(1) — Only 2 pointers used (leftmost, current); no queue/stack.
 *   Example: n=4095 nodes (12 levels) → exactly 2 pointer variables,
 *            regardless of n.
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 6. COMPLETE WORKED EXAMPLES
 * ─────────────────────────────────────────────────────────────────────────────
 *
 * EXAMPLE INPUT:
 *
 *         1
 *       /   \
 *      2     3
 *     / \   / \
 *    4   5 6   7
 *
 * ── APPROACH 1: BFS Walkthrough ──────────────────────────────────────────────
 *
 *   Initial queue: [1]
 *
 *   Level 1 (size=1):
 *     i=0: poll 1, i == levelSize-1, no next set. Enqueue 2, 3.
 *     Queue: [2, 3]
 *
 *   Level 2 (size=2):
 *     i=0: poll 2, peek=3 -> 2.next = 3. Enqueue 4, 5.
 *     i=1: poll 3, last in level. Enqueue 6, 7.
 *     Queue: [4, 5, 6, 7]
 *
 *   Level 3 (size=4):
 *     i=0: poll 4, peek=5 -> 4.next = 5.
 *     i=1: poll 5, peek=6 -> 5.next = 6.
 *     i=2: poll 6, peek=7 -> 6.next = 7.
 *     i=3: poll 7, last in level.
 *     Queue: []
 *
 *   Result: 1->null | 2->3->null | 4->5->6->7->null  [CORRECT]
 *
 * ── APPROACH 3: Iterative O(1) Walkthrough ───────────────────────────────────
 *
 *   leftmost = 1
 *
 *   Level 1 (leftmost=1, leftmost.left=2 != null, continue):
 *     current = 1
 *       current.left.next  = current.right         ->  2.next = 3
 *       current.next == null, skip cross-connection
 *       current = current.next = null -> stop
 *     leftmost = leftmost.left = 2
 *
 *   Level 2 (leftmost=2, leftmost.left=4 != null, continue):
 *     current = 2
 *       current.left.next  = current.right         ->  4.next = 5
 *       current.next = 3 != null
 *         current.right.next = current.next.left   ->  5.next = 6
 *       current = current.next = 3
 *     current = 3
 *       current.left.next  = current.right         ->  6.next = 7
 *       current.next == null, skip
 *       current = current.next = null -> stop
 *     leftmost = leftmost.left = 4
 *
 *   Level 3 (leftmost=4, leftmost.left=null, STOP)
 *
 *   Final State:
 *     1 -> null
 *     2 -> 3 -> null
 *     4 -> 5 -> 6 -> 7 -> null  [CORRECT]
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 7. EDGE CASES
 * ─────────────────────────────────────────────────────────────────────────────
 *
 *   Edge Case       | Input      | Expected     | BFS | Recursive | Iterative
 *   ----------------|------------|--------------|-----|-----------|----------
 *   Null root       | null       | null         |  OK |    OK     |    OK
 *   Single node     | [1]        | 1 -> null    |  OK |    OK     |    OK
 *   Two levels      | [1,2,3]    | 1->null,     |  OK |    OK     |    OK
 *                   |            | 2->3->null   |     |           |
 *   All same values | [5,5,5...] | Same struct  |  OK |    OK     |    OK
 *   Negative values | [-1,-2,-3] | Works same   |  OK |    OK     |    OK
 *   Max depth       | 4095 nodes | Correct ptrs |  OK |    OK     |    OK
 *   (12 levels)     |            |              |     |           |
 *
 * DETAILED NOTES:
 *   - Null root:    All three solutions check root == null at start and return.
 *   - Single node:  BFS: levelSize=1, skips next assignment.
 *                   Recursive: root.left == null, returns immediately.
 *                   Iterative: exits while loop since leftmost.left == null.
 *   - Max tree:     BFS may use O(2048) queue space for last level.
 *                   Iterative stays at O(1) regardless.
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 8. FINAL SUMMARY
 * ─────────────────────────────────────────────────────────────────────────────
 *
 *   Approach           | Time | Space    | Simplicity | Recommendation
 *   -------------------|------|----------|------------|-------------------
 *   BFS                | O(n) | O(n)     | *****      | Good starting point
 *   Recursive DFS      | O(n) | O(log n) | ****       | Shows DFS mastery
 *   Iterative O(1) [*] | O(n) | O(1)     | ***        | BEST — optimal
 *
 *   RECOMMENDATION:
 *   Start with BFS to show understanding, then present the O(1) iterative
 *   solution to demonstrate mastery. Recruiters love seeing the progression
 *   from obvious to optimal.
 *
 *   KEY PATTERN TO REMEMBER:
 *   "Use already-populated pointers as a traversal mechanism."
 *   This is a hallmark technique in linked list and tree problems — instead
 *   of external data structures, repurpose the structure itself for traversal.
 *   Same idea appears in: Flatten Binary Tree to Linked List, Morris Traversal.
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 9. COMPANY APPEARANCES
 * ─────────────────────────────────────────────────────────────────────────────
 *
 *   Company            | Frequency   | Notes
 *   -------------------|-------------|----------------------------------
 *   Amazon             | Very High   | Top asked tree problem
 *   Microsoft          | High        | Common in SDE rounds
 *   Facebook / Meta    | High        | Often paired with Part II
 *   Google             | Medium      | Focus on O(1) space follow-up
 *   Bloomberg          | Medium      | Tree traversal interviews
 *   Apple              | Medium      |
 *   Adobe              | Medium      |
 *   Uber               | Medium      |
 *
 *   LeetCode Problem #116 — Appeared in 200+ reported interviews.
 *   Follow-up Problem #117 (imperfect tree) — Appeared in 150+ interviews.
 *   Combined, this problem family is in the TOP 20 most-asked tree problems
 *   in FAANG interviews.
 *
 *   PRO TIP: Always ask the interviewer — "Is this always a perfect binary
 *   tree?" This signals you know constraints matter, and it's the exact
 *   constraint that unlocks the O(1) solution.
 */
// @formatter:on
