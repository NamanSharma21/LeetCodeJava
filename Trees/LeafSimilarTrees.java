package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import Datastructures.TreeNode;

public class LeafSimilarTrees {
    public static void main(String[] args) {

        LeafSimilarTrees leafSimilarTrees = new LeafSimilarTrees();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.right.left = new TreeNode(9);
        root.right.right = new TreeNode(8);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);

        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(5);
        root1.right = new TreeNode(1);
        root1.left.left = new TreeNode(6);
        root1.left.right = new TreeNode(7);
        root1.right.left = new TreeNode(4);
        root1.right.right = new TreeNode(2);
        root1.right.right.left = new TreeNode(9);
        root1.right.right.right = new TreeNode(8);
        System.out.println("LeafSimilarTrees : " + leafSimilarTrees.leafSimilarDFSList(root, root1));
        System.out.println("LeafSimilarTrees : " + leafSimilarTrees.leafSimilarIterativeDFS(root, root1));
        System.out.println("LeafSimilarTrees : " + leafSimilarTrees.leafSimilarLeafIterator(root, root1));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/leaf-similar-trees/description/
     * 
     * Consider all the leaves of a binary tree, from left to right order, the
     * values of those leaves form a leaf value sequence.
     * 
     * 
     * 
     * For example, in the given tree above, the leaf value sequence is (6, 7, 4, 9,
     * 8).
     * 
     * Two binary trees are considered leaf-similar if their leaf value sequence is
     * the same.
     * 
     * Return true if and only if the two given trees with head nodes root1 and
     * root2 are leaf-similar.
     * 
     * 
     * 
     * Example 1:
     * 
     * * Tree 1 (root1):
     *          3
     *        /   \
     *       5     1
     *      / \   / \
     *     6   2 9   8
     *        / \
     *       7   4
     * Leaf sequence: [6, 7, 4, 9, 8]
     *
     * Tree 2 (root2):
     *          3
     *        /   \
     *       5     1
     *      / \   / \
     *     6   7 4   2
     *              / \
     *             9   8
     * * Leaf sequence: [6, 7, 4, 9, 8]
     * * Result: true (Leaf-Similar)
     * 
     * Input: root1 = [3,5,1,6,2,9,8,null,null,7,4], root2 =
     * [3,5,1,6,7,4,2,null,null,null,null,null,null,9,8]
     * Output: true
     * Example 2:
     * 
     * 
     * Input: root1 = [1,2,3], root2 = [1,3,2]
     * Output: false
     * 
     * Tree 1 (root1):
     *       1
     *      / \
     *     2   3
     * Leaf sequence: [2, 3]
     *
     * Tree 2 (root2):
     *       1
     *      / \
     *     3   2
     * * Leaf sequence: [3, 2]
     * * Result: false (Not Leaf-Similar)
     * 
     * Constraints:
     * 
     * The number of nodes in each tree will be in the range [1, 200].
     * Both of the given trees will have values in the range [0, 200].
     */
    // @formatter:on

    public boolean leafSimilarDFSList(TreeNode root1, TreeNode root2) {
        List<Integer> root1Leaf = new ArrayList<>();
        List<Integer> root2Leaf = new ArrayList<>();
        dfs(root1, root1Leaf);
        dfs(root2, root2Leaf);
        return root1Leaf.equals(root2Leaf);
    }

    public void dfs(TreeNode root, List<Integer> leaf) {
        if (root == null)
            return;
        if (root.left == null && root.right == null) {
            leaf.add(root.val);
            return;
        }
        dfs(root.left, leaf);
        dfs(root.right, leaf);
    }

    public boolean leafSimilarIterativeDFS(TreeNode root1, TreeNode root2) {
        return getLeaves(root1).equals(getLeaves(root2));
    }

    public List<Integer> getLeaves(TreeNode root) {
        List<Integer> leaves = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        if (root != null)
            stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            if (curr.left == null && curr.right == null) {
                leaves.add(curr.val);
            } else {
                if (curr.left != null)
                    stack.push(curr.left);
                if (curr.right != null)
                    stack.push(curr.right);
            }
        }
        return leaves;
    }

    public boolean leafSimilarLeafIterator(TreeNode root1, TreeNode root2) {
        Iterator<Integer> iterator1 = leafIterator(root1);
        Iterator<Integer> iterator2 = leafIterator(root2);
        while (iterator1.hasNext() && iterator2.hasNext()) {
            if (!iterator1.next().equals(iterator2.next())) {
                return false;
            }
        }
        return !iterator1.hasNext() && !iterator2.hasNext();
    }

    private Iterator<Integer> leafIterator(TreeNode root) {
        return new Iterator<Integer>() {
            Deque<TreeNode> stack = new ArrayDeque<>();
            {
                if (root != null)
                    stack.push(root);
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public Integer next() {
                while (!stack.isEmpty()) {
                    TreeNode curr = stack.pop();
                    if (curr.left == null && curr.right == null)
                        return curr.val;
                    if (curr.right != null)
                        stack.push(curr.right);
                    if (curr.left != null)
                        stack.push(curr.left);
                }
                throw new NoSuchElementException();
            }
        };
    }
}

// @formatter:off
/*
 * =============================================================================
 * Leaf-Similar Trees — Deep Dive
 * =============================================================================
 *
 * -----------------------------------------------------------------------------
 * 1. PROBLEM STATEMENT
 * -----------------------------------------------------------------------------
 *
 * What the Problem Says:
 *   Given two binary trees, determine whether they are "leaf-similar".
 *   Two trees are leaf-similar if their leaf value sequences — the values of
 *   all leaf nodes read from left to right — are identical.
 *
 * Input Format:
 *   - Two binary tree roots: root1 and root2
 *   - Each node has: int val, TreeNode left, TreeNode right
 *
 * Output Format:
 *   - Return true if both trees have the same leaf sequence, false otherwise
 *
 * Constraints:
 *   - Number of nodes in each tree: [1, 200]
 *   - Node values: [0, 200]
 *   - A leaf node is a node with no left and no right child
 *
 * What Needs to Be Computed:
 *   Collect all leaf values (left to right order) from each tree separately,
 *   then compare the two sequences for equality.
 *
 * -----------------------------------------------------------------------------
 * 2. INTUITION
 * -----------------------------------------------------------------------------
 *
 * Core Idea in Simple Terms:
 *   Imagine shaking a tree and only catching the bottom-most leaves as they
 *   fall left to right. Do both trees drop leaves in the same order and same
 *   values? That's exactly what this problem asks.
 *
 * How a Human Would Reason:
 *   1. Walk tree 1 from left to right, noting only leaf nodes
 *      -> you get a sequence like [6, 7, 4, 9, 8]
 *   2. Walk tree 2 from left to right, noting only leaf nodes
 *      -> another sequence
 *   3. Compare the two sequences
 *
 * What Makes This Interesting:
 *   - You must ignore the tree's structure — only leaves matter
 *   - The traversal order (left before right) is critical —
 *     same leaves in different order = NOT leaf-similar
 *   - It tests your ability to extract specific information during traversal
 *
 * -----------------------------------------------------------------------------
 * 3. APPROACH OVERVIEW
 * -----------------------------------------------------------------------------
 *
 *   #  | Approach                    | Key Idea                          | Best For
 *   ---|-----------------------------|-----------------------------------|------------------------------
 *   1  | DFS + List Collection       | Collect leaves into lists,        | Interviews, Clean code ✅
 *      |                             | then compare                      |
 *   2  | DFS + String Comparison     | Build leaf strings and compare    | Simple but not recommended
 *   3  | Iterative DFS with Stack    | Use explicit stack to simulate    | Follow-up / large trees
 *      |                             | recursion                         |
 *   4  | Generator / Lazy Iterator   | Compare leaves on-the-fly         | Optimal for large inputs ✅
 *      |                             | without storing all               |
 *
 *   Recommended:
 *   - Approach 1 (DFS + List) is the cleanest and most interview-ready.
 *   - Approach 4 (Lazy Iterator) is theoretically optimal since it can
 *     short-circuit early without collecting all leaves.
 *
 * -----------------------------------------------------------------------------
 * 4. DETAILED SOLUTIONS IN JAVA
 * -----------------------------------------------------------------------------
 *
 * ✅ Approach 1: DFS + List Collection (Recommended)
 * ---------------------------------------------------
 *
 * Algorithm Steps:
 *   1. Define a helper that does pre-order DFS (left before right).
 *   2. At each node: if it's a leaf, add its value to the list.
 *   3. Otherwise, recurse left then right.
 *   4. Collect lists for both trees, then compare with .equals().
 *
 * Code:
 *
 *   import java.util.ArrayList;
 *   import java.util.List;
 *
 *   class Solution {
 *
 *       public boolean leafSimilar(TreeNode root1, TreeNode root2) {
 *           List<Integer> leaves1 = new ArrayList<>();
 *           List<Integer> leaves2 = new ArrayList<>();
 *
 *           collectLeaves(root1, leaves1);
 *           collectLeaves(root2, leaves2);
 *
 *           return leaves1.equals(leaves2); // checks size + element-by-element
 *       }
 *
 *       private void collectLeaves(TreeNode node, List<Integer> leaves) {
 *           if (node == null) return;
 *
 *           // It's a leaf if it has no children
 *           if (node.left == null && node.right == null) {
 *               leaves.add(node.val);
 *               return;
 *           }
 *
 *           collectLeaves(node.left, leaves);   // go left first
 *           collectLeaves(node.right, leaves);  // then right
 *       }
 *   }
 *
 * --------------------------------------------------
 * Approach 2: DFS + String Comparison
 * --------------------------------------------------
 *
 * Algorithm Steps:
 *   1. Traverse leaves and build a comma-separated string for each tree.
 *   2. Compare the two strings.
 *
 * Code:
 *
 *   class Solution {
 *
 *       public boolean leafSimilar(TreeNode root1, TreeNode root2) {
 *           return getLeafString(root1).equals(getLeafString(root2));
 *       }
 *
 *       private String getLeafString(TreeNode node) {
 *           if (node == null) return "";
 *
 *           if (node.left == null && node.right == null) {
 *               return node.val + ","; // comma as delimiter to avoid "1,2" vs "12" ambiguity
 *           }
 *
 *           return getLeafString(node.left) + getLeafString(node.right);
 *       }
 *   }
 *
 *   WARNING: String concatenation creates many intermediate strings
 *            -> O(n^2) time in worst case. Not ideal for large inputs.
 *
 * --------------------------------------------------
 * Approach 3: Iterative DFS with Explicit Stack
 * --------------------------------------------------
 *
 * Algorithm Steps:
 *   1. Use a Deque<TreeNode> as a stack.
 *   2. Push root, then loop: pop node -> if leaf, add to list
 *      -> else push right then left (so left is processed first).
 *   3. Compare lists.
 *
 * Code:
 *
 *   import java.util.*;
 *
 *   class Solution {
 *
 *       public boolean leafSimilar(TreeNode root1, TreeNode root2) {
 *           return getLeaves(root1).equals(getLeaves(root2));
 *       }
 *
 *       private List<Integer> getLeaves(TreeNode root) {
 *           List<Integer> leaves = new ArrayList<>();
 *           Deque<TreeNode> stack = new ArrayDeque<>();
 *
 *           if (root != null) stack.push(root);
 *
 *           while (!stack.isEmpty()) {
 *               TreeNode node = stack.pop();
 *
 *               if (node.left == null && node.right == null) {
 *                   leaves.add(node.val); // it's a leaf
 *               } else {
 *                   // Push right first so left is processed first (LIFO)
 *                   if (node.right != null) stack.push(node.right);
 *                   if (node.left != null) stack.push(node.left);
 *               }
 *           }
 *
 *           return leaves;
 *       }
 *   }
 *
 * --------------------------------------------------
 * ✅ Approach 4: Lazy Iterator (Optimal — Short-Circuit)
 * --------------------------------------------------
 *
 * Algorithm Steps:
 *   1. Use two Iterator<Integer> objects that yield leaves one at a time.
 *   2. Compare them one by one — stop as soon as a mismatch is found.
 *   3. Both iterators must be exhausted simultaneously for true.
 *
 * Code:
 *
 *   import java.util.*;
 *
 *   class Solution {
 *
 *       public boolean leafSimilar(TreeNode root1, TreeNode root2) {
 *           Iterator<Integer> iter1 = leafIterator(root1);
 *           Iterator<Integer> iter2 = leafIterator(root2);
 *
 *           while (iter1.hasNext() && iter2.hasNext()) {
 *               if (!iter1.next().equals(iter2.next())) {
 *                   return false; // mismatch found — short-circuit
 *               }
 *           }
 *
 *           // Both must be fully exhausted at the same time
 *           return !iter1.hasNext() && !iter2.hasNext();
 *       }
 *
 *       private Iterator<Integer> leafIterator(TreeNode root) {
 *           return new Iterator<Integer>() {
 *               Deque<TreeNode> stack = new ArrayDeque<>();
 *
 *               {
 *                   if (root != null) stack.push(root);
 *               }
 *
 *               @Override
 *               public boolean hasNext() {
 *                   return !stack.isEmpty();
 *               }
 *
 *               @Override
 *               public Integer next() {
 *                   while (!stack.isEmpty()) {
 *                       TreeNode node = stack.pop();
 *
 *                       if (node.left == null && node.right == null) {
 *                           return node.val; // yield this leaf
 *                       }
 *
 *                       if (node.right != null) stack.push(node.right);
 *                       if (node.left != null) stack.push(node.left);
 *                   }
 *                   throw new NoSuchElementException();
 *               }
 *           };
 *       }
 *   }
 *
 * -----------------------------------------------------------------------------
 * 5. TIME & SPACE COMPLEXITY (WITH REASONING)
 * -----------------------------------------------------------------------------
 *
 * Approach 1: DFS + List
 *   Time : O(T1 + T2)  — Visit every node in both trees exactly once
 *   Space: O(H1+H2+L)  — H = height (call stack), L = total leaves stored
 *   Example: Tree with 15 nodes, height 4 -> stack depth <= 4, leaf list <= 8
 *            Two such trees -> ~30 node visits total
 *
 * Approach 2: String Comparison
 *   Time : O(n^2) worst case — String concatenation rebuilds strings repeatedly
 *   Space: O(n)            — Recursion stack + string sizes
 *   NOTE: Avoid in interviews unless specifically asked.
 *
 * Approach 3: Iterative DFS
 *   Time : O(T1 + T2)  — Same as Approach 1, just iterative
 *   Space: O(H1+H2+L)  — Stack holds at most H nodes + leaf list
 *
 * Approach 4: Lazy Iterator
 *   Time : O(min(L1,L2)) best case  — Short-circuits on first mismatch
 *          O(T1 + T2)    worst case
 *   Space: O(H1 + H2)   — Only stack space, no leaf list stored
 *   NOTE: Best for very large trees where early mismatches are common.
 *
 * -----------------------------------------------------------------------------
 * 6. COMPLETE WORKED EXAMPLES FOR EACH APPROACH
 * -----------------------------------------------------------------------------
 *
 * Example 1 (LeetCode's Standard Example):
 *
 *   Tree 1:               Tree 2:
 *         3                     3
 *        / \                   / \
 *       5   1                 5   1
 *      / \ / \               / \ / \
 *     6  2 9  8             6  7 4  2
 *       / \                        / \
 *      7   4                      9   8
 *
 * Approach 1 Walkthrough — Tree 1 collectLeaves trace:
 *   visit(3)  -> not leaf -> go left
 *     visit(5)  -> not leaf -> go left
 *       visit(6)  -> LEAF -> add 6
 *     visit(5)  -> go right
 *       visit(2)  -> not leaf -> go left
 *         visit(7)  -> LEAF -> add 7
 *       visit(2)  -> go right
 *         visit(4)  -> LEAF -> add 4
 *   visit(3)  -> go right
 *     visit(1)  -> not leaf -> go left
 *       visit(9)  -> LEAF -> add 9
 *     visit(1)  -> go right
 *       visit(8)  -> LEAF -> add 8
 *   leaves1 = [6, 7, 4, 9, 8]
 *
 * Approach 1 Walkthrough — Tree 2 collectLeaves trace:
 *   visit(3) -> go left
 *     visit(5) -> go left
 *       visit(6)  -> LEAF -> add 6
 *     visit(5) -> go right
 *       visit(7)  -> LEAF -> add 7
 *   visit(3) -> go right
 *     visit(1) -> go left
 *       visit(4)  -> LEAF -> add 4
 *     visit(1) -> go right
 *       visit(2) -> go left
 *         visit(9)  -> LEAF -> add 9
 *       visit(2) -> go right
 *         visit(8)  -> LEAF -> add 8
 *   leaves2 = [6, 7, 4, 9, 8]
 *
 *   Comparison: [6,7,4,9,8].equals([6,7,4,9,8]) -> true ✅
 *
 * Example 2 (Not Similar):
 *
 *   Tree 1:    Tree 2:
 *       1          1
 *      / \        / \
 *     2   3      3   2
 *
 *   leaves1 = [2, 3]
 *   leaves2 = [3, 2]
 *   [2,3].equals([3,2]) -> false ✅
 *
 * Example 3 — Approach 4 Short-Circuit Demo:
 *
 *   Tree 1 leaves: [1, 5, 9, ...]
 *   Tree 2 leaves: [1, 5, 7, ...]
 *
 *   Compare 1 == 1  ✓
 *   Compare 5 == 5  ✓
 *   Compare 9 != 7  -> return false immediately
 *
 *   Tree 2's remaining leaves are never even visited — that's lazy evaluation.
 *
 * -----------------------------------------------------------------------------
 * 7. EDGE CASES
 * -----------------------------------------------------------------------------
 *
 *   Edge Case                   | What Could Go Wrong          | How Handled
 *   ----------------------------|------------------------------|----------------------------
 *   Single-node tree            | Root itself is a leaf        | left==null && right==null
 *                               |                              | adds root val correctly
 *   Both trees identical        | Should return true           | List equality handles this
 *   Skewed tree (single path)   | Deep recursion               | Approach 3 & 4 avoid
 *                               |                              | stack overflow; safe <=200
 *   Same leaves, different count| e.g. [1,2] vs [1,2,2]       | List.equals() checks size
 *   All nodes are leaves        | All at same level            | All get collected correctly
 *   Node value = 0              | Falsy in some languages      | Java int — 0 is fine
 *   Null roots                  | Empty trees                  | null guard returns early;
 *                               |                              | empty lists compare equal
 *   Duplicate leaf values       | e.g. [5,5] vs [5,5]         | Positional comparison
 *                               |                              | is correct
 *
 * -----------------------------------------------------------------------------
 * 8. SELF-CORRECTION & TESTING
 * -----------------------------------------------------------------------------
 *
 * Internal Code Review:
 *
 *   Q: What edge cases might this miss?
 *      ✅ Null root        : handled by `if (node == null) return`
 *      ✅ Single-node trees: left==null && right==null catches this
 *      ✅ Same values,
 *         different order  : List.equals() is order-sensitive
 *      ✅ Different leaf
 *         counts           : List.equals() checks size first
 *
 *   Q: Are there any type mismatches?
 *      - In Approach 4, iter1.next().equals(iter2.next()) — both return
 *        Integer (boxed). Using .equals() instead of == is correct here
 *        since == on Integer compares references, not values
 *        (risky for values > 127 due to Integer cache).
 *      - In Approach 1, List<Integer>.equals() uses .equals() internally
 *        -> safe ✅
 *
 *   Q: How can I verify this works right now?
 *
 *      // Quick manual test harness
 *      public static void main(String[] args) {
 *          // Tree 1: root=3, left=5(leaf), right=1(leaf)
 *          TreeNode root1 = new TreeNode(3);
 *          root1.left  = new TreeNode(5);
 *          root1.right = new TreeNode(1);
 *
 *          // Tree 2: root=3, left=5(leaf), right=1(leaf)
 *          TreeNode root2 = new TreeNode(3);
 *          root2.left  = new TreeNode(5);
 *          root2.right = new TreeNode(1);
 *
 *          Solution sol = new Solution();
 *          System.out.println(sol.leafSimilar(root1, root2)); // Expected: true
 *
 *          // Edge: single node each
 *          TreeNode singleA = new TreeNode(1);
 *          TreeNode singleB = new TreeNode(1);
 *          System.out.println(sol.leafSimilar(singleA, singleB)); // Expected: true
 *
 *          TreeNode singleC = new TreeNode(1);
 *          TreeNode singleD = new TreeNode(2);
 *          System.out.println(sol.leafSimilar(singleC, singleD)); // Expected: false
 *      }
 *
 * -----------------------------------------------------------------------------
 * 9. FINAL SUMMARY
 * -----------------------------------------------------------------------------
 *
 *   Approach             | Time     | Space  | Short-Circuit | Recommended?
 *   ---------------------|----------|--------|---------------|----------------
 *   DFS + List           | O(n)     | O(n)   | No            | ✅ Best for interviews
 *   String Comparison    | O(n^2)   | O(n)   | No            | ❌ Avoid
 *   Iterative Stack      | O(n)     | O(n)   | No            | ✅ Good follow-up
 *   Lazy Iterator        | O(n)best | O(h)   | Yes ✅        | ✅ Best for scale
 *
 *   What to Remember:
 *   - PATTERN: Collect-then-compare is the bread-and-butter pattern for
 *     tree sequence problems. When you need to compare sequences derived
 *     from two trees, DFS with a list (or lazy iterator) is your go-to tool.
 *   - KEY INSIGHT: A leaf is defined structurally (left==null && right==null),
 *     and traversal order (left before right) determines the sequence —
 *     both are critical to get right.
 *
 * -----------------------------------------------------------------------------
 * 10. COMPANY APPEARANCES
 * -----------------------------------------------------------------------------
 *
 *   Company         | Frequency         | Notes
 *   ----------------|-------------------|-------------------------------------
 *   Amazon          | ⭐⭐⭐⭐⭐ Very High | Frequently in SDE I/II rounds
 *   Google          | ⭐⭐⭐ Medium       | Appears in phone screens
 *   Microsoft       | ⭐⭐⭐ Medium       | Common in online assessments
 *   Facebook/Meta   | ⭐⭐ Low-Medium    | Occasionally in coding rounds
 *   Bloomberg       | ⭐⭐ Low-Medium    | Seen in OA rounds
 *   Adobe           | ⭐⭐ Low           | Occasionally reported
 *
 *   LeetCode Problem #872 — Categorized as Easy, but the lazy iterator
 *   follow-up elevates it to a Medium-level discussion in interviews.
 *
 *   Total reported appearances (LeetCode Discuss + Glassdoor): 100+ times
 *   across companies, with Amazon being the most frequent asker, particularly
 *   in their OA rounds for new grad and SDE I positions.
 *
 * =============================================================================
 */
// @formatter:on
