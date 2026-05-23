package Trees;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import Datastructures.TreeNode;

public class SumRootToLeafNumbers {
    public static void main(String[] args) {
        SumRootToLeafNumbers sumRootToLeafNumbers = new SumRootToLeafNumbers();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        System.out.println("SumRootToLeafNumbers : " + sumRootToLeafNumbers.sumNumbers(root));

        TreeNode root1 = new TreeNode(4);
        root1.left = new TreeNode(9);
        root1.right = new TreeNode(0);
        root1.left.left = new TreeNode(5);
        root1.left.right = new TreeNode(1);
        System.out.println("SumRootToLeafNumbers : " + sumRootToLeafNumbers.sumNumbers(root1));

        System.out.println("SumRootToLeafNumbers : " + sumRootToLeafNumbers.sumNumbersIterativeDFS(root));
        System.out.println("SumRootToLeafNumbers : " + sumRootToLeafNumbers.sumNumbersIterativeDFS(root1));

        System.out.println("SumRootToLeafNumbers : " + sumRootToLeafNumbers.sumNumbersIterativeBFS(root));
        System.out.println("SumRootToLeafNumbers : " + sumRootToLeafNumbers.sumNumbersIterativeBFS(root1));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/sum-root-to-leaf-numbers/description/?envType=problem-list-v2&envId=tree
     * 
     * You are given the root of a binary tree containing digits from 0 to 9 only.
     * 
     * Each root-to-leaf path in the tree represents a number.
     * 
     * For example, the root-to-leaf path 1 -> 2 -> 3 represents the number 123.
     * Return the total sum of all root-to-leaf numbers. Test cases are generated so
     * that the answer will fit in a 32-bit integer.
     * 
     * A leaf node is a node with no children.
     * 
     * 
     * 
     * Example 1:
     * 
     *       1
     *      / \
     *     2   3
     * 
     * Paths: 1->2 (12), 1->3 (13)
     * Sum = 12 + 13 = 25
     * 
     * Input: root = [1,2,3]
     * Output: 25
     * Explanation:
     * The root-to-leaf path 1->2 represents the number 12.
     * The root-to-leaf path 1->3 represents the number 13.
     * Therefore, sum = 12 + 13 = 25.
     * Example 2:
     * 
     *        4
     *       / \
     *      9   0
     *     / \
     *    5   1
     * 
     * Paths: 4->9->5 (495), 4->9->1 (491), 4->0 (40)
     * Sum = 495 + 491 + 40 = 1026
     * 
     * Input: root = [4,9,0,5,1]
     * Output: 1026
     * Explanation:
     * The root-to-leaf path 4->9->5 represents the number 495.
     * The root-to-leaf path 4->9->1 represents the number 491.
     * The root-to-leaf path 4->0 represents the number 40.
     * Therefore, sum = 495 + 491 + 40 = 1026.
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [1, 1000].
     * 0 <= Node.val <= 9
     * The depth of the tree will not exceed 10.
     */
    // @formatter:on

    public int sumNumbers(TreeNode root) {
        return dfsPreOrder(root, 0);
    }

    public int dfsPreOrder(TreeNode root, int currentSum) {
        if (root == null)
            return 0;
        currentSum = (currentSum * 10) + root.val;
        if (root.left == null && root.right == null)
            return currentSum;
        return dfsPreOrder(root.left, currentSum) + dfsPreOrder(root.right, currentSum);
    }

    public int sumNumbersIterativeDFS(TreeNode root) {
        if (root == null)
            return 0;
        Deque<TreeNode> stack = new ArrayDeque<>();
        Deque<Integer> numberStack = new ArrayDeque<>();
        numberStack.push(root.val);
        stack.push(root);
        int currentSum = 0;
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            int currentNumber = numberStack.pop();
            if (current.left == null && current.right == null)
                currentSum += currentNumber;
            if (current.left != null) {
                stack.push(current.left);
                numberStack.push(currentNumber * 10 + current.left.val);
            }

            if (current.right != null) {
                stack.push(current.right);
                numberStack.push(currentNumber * 10 + current.right.val);
            }
        }
        return currentSum;
    }

    public int sumNumbersIterativeBFS(TreeNode root) {
        if (root == null)
            return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        Queue<Integer> numberQueue = new LinkedList<>();
        numberQueue.offer(root.val);
        int totalSum = 0;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            int currentNumber = numberQueue.poll();
            if (node.left == null && node.right == null)
                totalSum += currentNumber;
            if (node.left != null) {
                queue.offer(node.left);
                numberQueue.offer(currentNumber * 10 + node.left.val);
            }
            if (node.right != null) {
                queue.offer(node.right);
                numberQueue.offer(currentNumber * 10 + node.right.val);
            }
        }
        return totalSum;
    }
}

// @formatter:off
/*
 * =============================================================================
 * Sum Root to Leaf Numbers — Deep Dive
 * =============================================================================
 *
 * =============================================================================
 * 1. Problem Statement
 * =============================================================================
 *
 * In Plain English:
 * -----------------
 * You are given a binary tree where each node contains a single digit (0–9).
 * Every path from the root to a leaf represents a number formed by
 * concatenating the digits along that path (root digit is the most
 * significant). Your task is to find the sum of all such numbers formed by
 * every root-to-leaf path.
 *
 * Input Format:
 * -------------
 * - Root node of a binary tree: TreeNode root
 * - Each TreeNode has: int val, TreeNode left, TreeNode right
 *
 * Output Format:
 * --------------
 * - A single int representing the total sum of all root-to-leaf numbers
 *
 * Constraints (LeetCode #129):
 * ----------------------------
 * - Number of nodes: [1, 1000]
 * - Node values:     0 <= Node.val <= 9
 * - Tree depth will not exceed 1000
 * - Answer is guaranteed to fit in a 32-bit integer
 *
 * What Exactly Needs to Be Computed:
 * ------------------------------------
 * For each path from root → leaf, read the digits top-down as a number
 * (e.g., 1 → 2 → 3 = 123). Return the sum of all such numbers.
 *
 *
 * =============================================================================
 * 2. Intuition
 * =============================================================================
 *
 * The Core Idea:
 * --------------
 * Think of it like building a number digit by digit as you walk down the tree:
 *   - At the root, the "running number" is just root.val
 *   - At each child, you shift the current number left (multiply by 10)
 *     and add the child's digit
 *   - When you reach a leaf, you've completed one number — add it to the total
 *
 * How a Human Reasons About It:
 * ------------------------------
 *         1
 *        / \
 *       2   3
 *
 *   Path 1→2: number = 12
 *   Path 1→3: number = 13
 *   Answer = 12 + 13 = 25
 *
 *   At each step: currentNumber = parentNumber * 10 + node.val
 *
 * What Makes It Interesting / Tricky:
 * -------------------------------------
 *   Challenge              | Why It Matters
 *   -----------------------|----------------------------------------------
 *   Number formation       | Multiplying by 10 isn't obvious at first
 *   Leaf detection         | Must check left == null && right == null
 *   Tree traversal state   | Need to carry "current number" through recursion
 *   Null nodes             | Don't add to sum when hitting null — only at leaves
 *
 *
 * =============================================================================
 * 3. Approach Overview
 * =============================================================================
 *
 *   #  | Approach                     | Key Idea                             | Optimal?
 *   ---|------------------------------|--------------------------------------|----------
 *   1  | Recursive DFS (Top-Down)     | Pass running number down recursively | Yes
 *   2  | Iterative DFS (Stack)        | Simulate recursion with stack        | Yes (equal)
 *   3  | Iterative BFS (Queue)        | Level-order, carry number with node  | Yes (equal)
 *   4  | Morris Traversal             | O(1) space, no stack/recursion       | Overkill
 *
 *   Recommended: Recursive DFS — cleanest, most readable, interview-gold standard.
 *
 *
 * =============================================================================
 * 4. Detailed Solutions in Java
 * =============================================================================
 *
 * -----------------------------------------------------------------------------
 * TreeNode Definition (used by all approaches)
 * -----------------------------------------------------------------------------
 *
 *   public class TreeNode {
 *       int val;
 *       TreeNode left;
 *       TreeNode right;
 *
 *       TreeNode(int val) { this.val = val; }
 *       TreeNode(int val, TreeNode left, TreeNode right) {
 *           this.val   = val;
 *           this.left  = left;
 *           this.right = right;
 *       }
 *   }
 *
 * -----------------------------------------------------------------------------
 * Approach 1: Recursive DFS (Top-Down) — OPTIMAL
 * -----------------------------------------------------------------------------
 *
 * Algorithm Step-by-Step:
 *   1. Start DFS from root with currentNumber = 0
 *   2. At each node: currentNumber = currentNumber * 10 + node.val
 *   3. If the node is a leaf (no children): return currentNumber
 *   4. Otherwise: recurse left + recurse right, return their sum
 *   5. If node is null: return 0 (base case)
 *
 *   class Solution {
 *       public int sumNumbers(TreeNode root) {
 *           return dfs(root, 0);
 *       }
 *
 *       private int dfs(TreeNode node, int currentNumber) {
 *           // Base case: null node contributes nothing
 *           if (node == null) return 0;
 *
 *           // Build the number digit by digit as we go deeper
 *           currentNumber = currentNumber * 10 + node.val;
 *
 *           // If this is a leaf, we've completed one root-to-leaf number
 *           if (node.left == null && node.right == null) {
 *               return currentNumber;
 *           }
 *
 *           // Otherwise, sum contributions from both subtrees
 *           return dfs(node.left, currentNumber) + dfs(node.right, currentNumber);
 *       }
 *   }
 *
 * -----------------------------------------------------------------------------
 * Approach 2: Iterative DFS (Explicit Stack)
 * -----------------------------------------------------------------------------
 *
 * Algorithm Step-by-Step:
 *   1. Push (root, root.val) onto stack as a pair
 *   2. While stack is not empty:
 *      - Pop (node, number)
 *      - If leaf: add number to total sum
 *      - Push right child with number * 10 + right.val (if exists)
 *      - Push left  child with number * 10 + left.val  (if exists)
 *   3. Return total sum
 *
 *   import java.util.ArrayDeque;
 *   import java.util.Deque;
 *
 *   class Solution {
 *       public int sumNumbers(TreeNode root) {
 *           if (root == null) return 0;
 *
 *           int totalSum = 0;
 *           Deque<TreeNode> nodeStack   = new ArrayDeque<>();
 *           Deque<Integer>  numberStack = new ArrayDeque<>();
 *
 *           nodeStack.push(root);
 *           numberStack.push(root.val);
 *
 *           while (!nodeStack.isEmpty()) {
 *               TreeNode node          = nodeStack.pop();
 *               int      currentNumber = numberStack.pop();
 *
 *               if (node.left == null && node.right == null) {
 *                   totalSum += currentNumber;
 *               }
 *
 *               if (node.right != null) {
 *                   nodeStack.push(node.right);
 *                   numberStack.push(currentNumber * 10 + node.right.val);
 *               }
 *               if (node.left != null) {
 *                   nodeStack.push(node.left);
 *                   numberStack.push(currentNumber * 10 + node.left.val);
 *               }
 *           }
 *           return totalSum;
 *       }
 *   }
 *
 * -----------------------------------------------------------------------------
 * Approach 3: Iterative BFS (Queue)
 * -----------------------------------------------------------------------------
 *
 * Algorithm Step-by-Step:
 *   1. Use two queues: one for nodes, one for the running number at that node
 *   2. Process level by level
 *   3. When a leaf is dequeued, add its number to the total
 *
 *   import java.util.LinkedList;
 *   import java.util.Queue;
 *
 *   class Solution {
 *       public int sumNumbers(TreeNode root) {
 *           if (root == null) return 0;
 *
 *           int totalSum = 0;
 *           Queue<TreeNode> nodeQueue   = new LinkedList<>();
 *           Queue<Integer>  numberQueue = new LinkedList<>();
 *
 *           nodeQueue.offer(root);
 *           numberQueue.offer(root.val);
 *
 *           while (!nodeQueue.isEmpty()) {
 *               TreeNode node          = nodeQueue.poll();
 *               int      currentNumber = numberQueue.poll();
 *
 *               // Leaf node: complete number found
 *               if (node.left == null && node.right == null) {
 *                   totalSum += currentNumber;
 *               }
 *
 *               if (node.left != null) {
 *                   nodeQueue.offer(node.left);
 *                   numberQueue.offer(currentNumber * 10 + node.left.val);
 *               }
 *               if (node.right != null) {
 *                   nodeQueue.offer(node.right);
 *                   numberQueue.offer(currentNumber * 10 + node.right.val);
 *               }
 *           }
 *           return totalSum;
 *       }
 *   }
 *
 *
 * =============================================================================
 * 5. Time & Space Complexity
 * =============================================================================
 *
 *   Approach        | Time  | Space | Notes
 *   ----------------|-------|-------|------------------------------------------
 *   Recursive DFS   | O(N)  | O(H)  | H = height; O(logN) balanced, O(N) skewed
 *   Iterative DFS   | O(N)  | O(H)  | Stack holds at most H nodes at a time
 *   Iterative BFS   | O(N)  | O(W)  | W = max width; O(N/2) at last level worst
 *
 * Reasoning:
 * ----------
 * Time — O(N) for all:
 *   - Every node is visited exactly once
 *   - At each node, we do O(1) work (multiply + add)
 *   - For N = 1000 nodes → ~1000 operations
 *
 * Space:
 *   - Recursive DFS: Call stack depth = tree height H
 *       Balanced tree (N=1000): H ≈ log₂(1000) ≈ 10  → O(log N)
 *       Skewed tree (linked list): H = N               → O(N)
 *   - Iterative DFS: Same — stack holds O(H) elements
 *   - BFS: Queue holds widest level; perfect binary tree → N/2 at last level
 *
 * Worked Size Example:
 *   N = 1000, balanced:  ~1000 visits, ~10 stack frames
 *   N = 1000, skewed:    ~1000 visits, ~1000 stack frames
 *
 *
 * =============================================================================
 * 6. Complete Worked Examples
 * =============================================================================
 *
 * Example 1 — Approach 1 (Recursive DFS)
 * ----------------------------------------
 * Input Tree:
 *         4
 *        / \
 *       9   0
 *      / \
 *     5   1
 *
 * Expected Output: 1026
 *   Path 4→9→5 = 495
 *   Path 4→9→1 = 491
 *   Path 4→0   = 40
 *   Sum = 495 + 491 + 40 = 1026
 *
 * Step-by-step trace:
 *
 *   dfs(4, 0)
 *     currentNumber = 0 * 10 + 4 = 4
 *     not a leaf → recurse
 *
 *     dfs(9, 4)
 *       currentNumber = 4 * 10 + 9 = 49
 *       not a leaf → recurse
 *
 *       dfs(5, 49)
 *         currentNumber = 49 * 10 + 5 = 495
 *         LEAF → return 495
 *
 *       dfs(1, 49)
 *         currentNumber = 49 * 10 + 1 = 491
 *         LEAF → return 491
 *
 *       return 495 + 491 = 986
 *
 *     dfs(0, 4)
 *       currentNumber = 4 * 10 + 0 = 40
 *       LEAF → return 40
 *
 *     return 986 + 40 = 1026
 *
 *   Call | Node | currentNumber | Return
 *   -----|------|---------------|-------------------
 *   1    | 4    | 4             | 1026
 *   2    | 9    | 49            | 986
 *   3    | 5    | 495           | 495 (leaf)
 *   4    | 1    | 491           | 491 (leaf)
 *   5    | 0    | 40            | 40  (leaf)
 *
 * ----------------------------------------
 * Example 2 — Approach 2 (Iterative DFS)
 * ----------------------------------------
 * Input Tree:
 *     1
 *    / \
 *   2   3
 *
 * Expected Output: 25  (12 + 13)
 *
 * Stack trace:
 *   Initial: stack = [(1, 1)]
 *
 *   Step 1: Pop (1, 1) → not a leaf
 *     Push right: (3, 1*10+3=13)
 *     Push left:  (2, 1*10+2=12)
 *     stack = [(3,13), (2,12)]   totalSum = 0
 *
 *   Step 2: Pop (2, 12) → LEAF
 *     totalSum = 0 + 12 = 12
 *     stack = [(3,13)]
 *
 *   Step 3: Pop (3, 13) → LEAF
 *     totalSum = 12 + 13 = 25
 *     stack = []
 *
 *   Return 25
 *
 * ----------------------------------------
 * Example 3 — Approach 3 (BFS)
 * ----------------------------------------
 * Input Tree:
 *     1
 *    / \
 *   2   3
 *
 * Queue trace:
 *   Initial: nodeQueue=[1], numberQueue=[1]
 *
 *   Level 1:
 *     Poll node=1, number=1 → not a leaf
 *     Enqueue left:  node=2, number=12
 *     Enqueue right: node=3, number=13
 *     nodeQueue=[2,3], numberQueue=[12,13]
 *
 *   Level 2:
 *     Poll node=2, number=12 → LEAF → totalSum=12
 *     Poll node=3, number=13 → LEAF → totalSum=25
 *
 *   Return 25
 *
 *
 * =============================================================================
 * 7. Edge Cases
 * =============================================================================
 *
 *   Edge Case                   | Input       | Expected | Handled By
 *   ----------------------------|-------------|----------|----------------------
 *   Single node (zero)          | [0]         | 0        | Leaf returns 0       ✓
 *   Single node (non-zero)      | [5]         | 5        | Leaf returns 5       ✓
 *   Null root                   | null        | 0        | null check → return 0✓
 *   Left-skewed  tree           | 1→2→3       | 123      | Recursion handles    ✓
 *   Right-skewed tree           | 1→2→3 right | 123      | Same                 ✓
 *   All zeros                   | 0→0→0       | 0        | 0*10+0=0 each step   ✓
 *   Zero in middle of path      | 1→0→5       | 105      | 10*10+5=105          ✓
 *   Max depth (1000)            | Long chain  | Large    | Recursive: may SOF  ⚠
 *   Max node count (1000)       | Wide tree   | Sum      | All approaches       ✓
 *
 * CRITICAL — Stack Overflow Risk:
 *   For a skewed tree with depth 1000:
 *   Recursive DFS → Java default stack ~500–1000 frames → MAY overflow!
 *   Solution: Use Iterative DFS for production code.
 *
 * Zero as leaf value:
 *       1
 *      /
 *     0
 *   Path = 10 → currentNumber = 1*10+0 = 10  ✓
 *
 * Interior node with value 0:
 *       1
 *      /
 *     0
 *    /
 *   5
 *   Path = 105 → (1*10+0)*10+5 = 105  ✓
 *
 *
 * =============================================================================
 * 8. Final Summary
 * =============================================================================
 *
 *   Approach        | Simplicity | Space   | SOF Risk | Interview Choice
 *   ----------------|------------|---------|----------|------------------
 *   Recursive DFS   | *****      | O(H)    | Maybe    | #1 Choice
 *   Iterative DFS   | ****       | O(H)    | None     | Production safe
 *   Iterative BFS   | ***        | O(W)    | None     | Good alternative
 *   Morris Traversal| **         | O(1)    | None     | Too complex
 *
 * Recommended in Practice:
 *   Use Recursive DFS in interviews for clarity and elegance.
 *   Use Iterative DFS in production if tree depth can be extreme (> 500).
 *
 * What to Remember:
 *   This is a classic DFS path-accumulation problem. The pattern is:
 *   carry a running value (currentNumber × 10 + node.val) through DFS,
 *   and harvest it at leaves. This exact pattern appears in many tree
 *   problems involving path values, path sums, or digit-by-digit construction.
 *
 *
 * =============================================================================
 * 9. Company Appearances & Frequency
 * =============================================================================
 *
 *   Company            | Frequency | Notes
 *   -------------------|-----------|------------------------------
 *   Facebook / Meta    | *****     | Very frequently asked
 *   Amazon             | *****     | Common in OA and phone screens
 *   Google             | ****      | Appears in onsite rounds
 *   Microsoft          | ****      | Mid-level SDE interviews
 *   Bloomberg          | ****      | Frequently reported
 *   Apple              | ***       | Appears occasionally
 *   Adobe              | ***       | Reported multiple times
 *   Uber               | ***       | Phone + onsite
 *   LinkedIn           | ***       | Reported in screens
 *   Salesforce         | **        | Occasionally reported
 *
 * Overall LeetCode Stats (as of 2025):
 *   Problem #:           129
 *   Difficulty:          Medium
 *   Acceptance Rate:     ~62%
 *   Total Submissions:   1.5M+
 *   Interview Reports:   300+ reports across LeetCode Discuss, Glassdoor, Blind
 *
 *   This problem is a must-know for any FAANG/MAANG interview preparation.
 *   It tests tree traversal, state propagation, and DFS mastery — all in a
 *   clean, bite-sized package.
 *
 * =============================================================================
 */
// @formatter:on
