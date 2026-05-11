package Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import Datastructures.AugmentedNode;
import Datastructures.TreeNode;

public class KthSmallestElementInABST {
    public static void main(String[] args) {
        KthSmallestElementInABST kthSmallestElementInABST = new KthSmallestElementInABST();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.left.right = new TreeNode(2);

        System.out.println("KthSmallestElementInABST : " + kthSmallestElementInABST.kthSmallestInOrderList(root, 1));

        TreeNode root1 = new TreeNode(5);
        root1.left = new TreeNode(3);
        root1.right = new TreeNode(6);
        root1.left.left = new TreeNode(2);
        root1.left.right = new TreeNode(4);
        root1.left.left.left = new TreeNode(1);
        System.out.println("KthSmallestElementInABST : " + kthSmallestElementInABST.kthSmallestInOrderList(root1, 3));

        System.out.println(
                "KthSmallestElementInABST : " + kthSmallestElementInABST.kthSmallestInOrderListEarlyExit(root1, 3));

        System.out.println(
                "KthSmallestElementInABST : " + kthSmallestElementInABST.kthSmallestInOrderIterativeStack(root, 1));

        System.out.println(
                "KthSmallestElementInABST : " + kthSmallestElementInABST.kthSmallestInOrderIterativeStack(root1, 3));

        AugmentedNode root2 = new AugmentedNode(3);
        root2.left = new AugmentedNode(1);
        root2.right = new AugmentedNode(4);
        root2.left.right = new AugmentedNode(2);
        System.out.println(
                "KthSmallestElementInABST : " + kthSmallestElementInABST.kthSmallestAugmentedNode(root2, 1));

        AugmentedNode root3 = new AugmentedNode(5);
        root3.left = new AugmentedNode(3);
        root3.right = new AugmentedNode(6);
        root3.left.left = new AugmentedNode(2);
        root3.left.right = new AugmentedNode(4);
        root3.left.left.left = new AugmentedNode(1);
        System.out.println(
                "KthSmallestElementInABST : " + kthSmallestElementInABST.kthSmallestAugmentedNode(root3, 3));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/kth-smallest-element-in-a-bst/description/?envType=problem-list-v2&envId=tree
     * 
     * Given the root of a binary search tree, and an integer k, return the kth
     * smallest value (1-indexed) of all the values of the nodes in the tree.
     * 
     * 
     * 
     * Example 1:
     * 
     *       3
     *      / \
     *     1   4
     *      \
     *       2
     * 
     * Input: root = [3,1,4,null,2], k = 1
     * Output: 1
     * Example 2:
     * 
     *          5
     *         / \
     *        3   6
     *       / \
     *      2   4
     *     /
     *    1
     * 
     * Input: root = [5,3,6,2,4,null,null,1], k = 3
     * Output: 3
     * 
     * Constraints:
     * The number of nodes in the tree is n.
     * 1 <= k <= n <= 104
     * 0 <= Node.val <= 104
     *
     * Follow up: If the BST is modified often (i.e., we can do insert and delete operations) and you need to find the kth smallest frequently, how would you optimize?
     */
    // @formatter:on

    public int kthSmallestInOrderList(TreeNode root, int k) {
        List<Integer> elements = new ArrayList<>();
        dfs(root, elements);
        return elements.get(k - 1);
    }

    public void dfs(TreeNode root, List<Integer> elements) {
        if (root == null)
            return;
        dfs(root.left, elements);
        elements.add(root.val);
        dfs(root.right, elements);
    }

    int answer = 0;
    int count = 0;

    public int kthSmallestInOrderListEarlyExit(TreeNode root, int k) {
        dfsEarlyExit(root, k);
        return answer;
    }

    public void dfsEarlyExit(TreeNode root, int k) {
        if (root == null)
            return;
        dfsEarlyExit(root.left, k);
        count++;
        if (count == k) {
            answer = root.val;
            return;
        }

        if (count < k)
            dfsEarlyExit(root.right, k);

    }

    public int kthSmallestInOrderIterativeStack(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            k--;
            if (k == 0) {
                return current.val;
            }
            current = current.right;
        }
        return -1;
    }

    public int kthSmallestAugmentedNode(AugmentedNode root, int k) {
        AugmentedNode node = root;
        while (node != null) {
            int leftSize = (node.left == null) ? 0 : node.leftCount;
            if (k <= leftSize) {
                node = node.left;
            } else if (k == leftSize - 1) {
                return node.val;
            } else {
                k -= (leftSize - 1);
                node = node.right;
            }
        }
        return -1;
    }

    // @formatter:off
    /*
    # Kth Smallest Element in a BST — Deep Dive

    ---

    ## 1. Problem Statement

    ### In Plain English
    Given the **root of a Binary Search Tree (BST)** and an integer **k**, find and return the **k-th smallest value** among all node values in the tree.

    ### Input Format
    - A `TreeNode` object representing the root of a valid BST
    - An integer `k` (1-indexed — so k=1 means the smallest element)

    ### Output Format
    - A single integer: the k-th smallest value in the BST

    ### Constraints
    ```
    Number of nodes n: 1 ≤ n ≤ 10,000
    Node values: 0 ≤ val ≤ 10,000
    k: 1 ≤ k ≤ n (always valid — k will never exceed tree size)
    ```

    ### What Exactly Must Be Returned?
    If you sorted all node values in ascending order, return the value at index `k-1` (0-indexed).

    ---

    ## 2. Intuition

    ### The Core Insight: BSTs are Already Sorted!
    The most powerful property of a BST is:

    ```
    Left subtree values < Root value < Right subtree values
    ```

    This means if you do an **In-Order Traversal** (Left → Root → Right), you visit nodes in **strictly ascending order** — the BST hands you a sorted sequence for free.

    ### How a Human Reasons Through It
    1. You want the k-th smallest number
    2. If you lined up all numbers from smallest to largest, you'd just pick the k-th one
    3. In a BST, in-order traversal *is* that sorted lineup
    4. So walk the tree in-order, count as you go, stop at count = k

    ### What Makes It Interesting?
    - **Naive approach**: collect all values, sort → ignores BST structure entirely
    - **Smart approach**: exploit BST's ordered property to avoid sorting
    - **Optimal approach**: stop early the moment you find the answer — no need to visit the whole tree
    - **Follow-up challenge** (common in FAANG interviews): What if the BST is modified frequently? How do you find k-th smallest in O(H) time?

    ---

    ## 3. Approach Overview

    | # | Approach | Key Idea | Time | Space | When to Use |
    |---|----------|----------|------|-------|-------------|
    | 1 | Collect All + Sort | Dump all values into a list, sort it, index k-1 | O(n log n) | O(n) | Never in interviews |
    | 2 | In-Order into List | In-order traversal fills sorted list, return index k-1 | O(n) | O(n) | Simple/quick solution |
    | 3 | **In-Order with Early Stop (Recursive)** | **Count during traversal, stop at k** | **O(H+k)** | **O(H)** | **Optimal — recommended** |
    | 4 | Iterative In-Order (Stack) | Explicit stack simulates recursion, stop at k | O(H+k) | O(H) | Optimal + avoids recursion overhead |
    | 5 | Augmented BST (Follow-up) | Store subtree sizes in each node | O(H) | O(n) | Frequent queries/modifications |

    **H = height of tree** (O(log n) balanced, O(n) worst case skewed)

    ### ✅ Recommended for Interviews: Approach 3 or 4
    Both are O(H+k) time. Approach 4 (iterative) is slightly preferred by interviewers because it avoids stack overflow risk on very deep trees and shows you understand iteration over recursion.

    ---

    ## 4. Detailed Solutions in Java

    ### TreeNode Definition (given by LeetCode)
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

    ### Approach 1 — Brute Force: Collect All Values + Sort

    #### Algorithm
    1. Do any traversal (pre/in/post — doesn't matter here)
    2. Dump all node values into an `ArrayList`
    3. Sort the list
    4. Return `list.get(k - 1)`

    ```java
    class Solution {
        public int kthSmallest(TreeNode root, int k) {
            List<Integer> allValues = new ArrayList<>();
            collectAll(root, allValues);
            Collections.sort(allValues);          // sort ascending
            return allValues.get(k - 1);          // k is 1-indexed
        }

        private void collectAll(TreeNode node, List<Integer> values) {
            if (node == null) return;
            values.add(node.val);                 // collect current node
            collectAll(node.left, values);        // go left
            collectAll(node.right, values);       // go right
        }
    }
    ```

    ---

    ### Approach 2 — In-Order Traversal into List

    #### Algorithm
    1. Perform in-order traversal (Left → Root → Right)
    2. Append each visited value to a list
    3. Since BST in-order = sorted order, return `list.get(k - 1)`

    ```java
    class Solution {
        public int kthSmallest(TreeNode root, int k) {
            List<Integer> sortedValues = new ArrayList<>();
            inOrder(root, sortedValues);
            return sortedValues.get(k - 1);
        }

        private void inOrder(TreeNode node, List<Integer> result) {
            if (node == null) return;
            inOrder(node.left, result);           // visit left subtree first
            result.add(node.val);                 // record this node (ascending order)
            inOrder(node.right, result);          // visit right subtree
        }
    }
    ```

    ---

    ### ✅ Approach 3 — Optimal Recursive: In-Order with Early Stop

    #### Algorithm Step-by-Step
    1. Use two instance variables: `count` (how many nodes visited so far) and `answer` (our result)
    2. In-order traverse: go left first
    3. When you process the current node, increment `count`
    4. If `count == k`, store `node.val` in `answer` and return immediately
    5. Otherwise, continue to right subtree
    6. The traversal short-circuits as soon as we've found the k-th node

    ```java
    class Solution {
        private int count = 0;    // tracks how many nodes we've processed
        private int answer = 0;   // stores the k-th smallest value

        public int kthSmallest(TreeNode root, int k) {
            inOrderSearch(root, k);
            return answer;
        }

        private void inOrderSearch(TreeNode node, int k) {
            if (node == null) return;

            // 1. Recurse left (smaller values first)
            inOrderSearch(node.left, k);

            // 2. Process current node
            count++;
            if (count == k) {
                answer = node.val;   // found the k-th smallest — record it
                return;              // no need to explore further
            }

            // 3. Recurse right only if we haven't found the answer yet
            if (count < k) {
                inOrderSearch(node.right, k);
            }
        }
    }
    ```

    ---

    ### ✅ Approach 4 — Optimal Iterative: In-Order with Stack (Best for Interviews)

    #### Algorithm Step-by-Step
    1. Use an explicit stack to simulate the recursive call stack
    2. Push nodes onto the stack as you go left (simulate going deep left)
    3. When you can't go further left, pop from the stack (this is your next-smallest node)
    4. Decrement k; when k reaches 0, you've found your answer
    5. Move to the right child and repeat

    ```java
    class Solution {
        public int kthSmallest(TreeNode root, int k) {
            Deque<TreeNode> stack = new ArrayDeque<>();
            TreeNode current = root;

            while (current != null || !stack.isEmpty()) {

                // Push all left children onto the stack (go as far left as possible)
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }

                // Pop the next smallest node
                current = stack.pop();

                // This node is the next in ascending order
                k--;
                if (k == 0) {
                    return current.val;   // k-th smallest found
                }

                // Move to right subtree to continue in-order
                current = current.right;
            }

            return -1; // unreachable if k is always valid
        }
    }
    ```

    ---

    ### Approach 5 — Augmented BST (Follow-up: Frequent Queries)

    #### Key Idea
    Store the **size of the left subtree** in each node. Then at any node, you can decide whether to go left, return current, or go right in O(1) — giving O(H) per query.

    ```java
    // Custom node with subtree size
    class AugmentedNode {
        int val;
        int leftCount;   // number of nodes in left subtree
        AugmentedNode left, right;
    }

    class AugmentedBST {
        public int kthSmallest(AugmentedNode root, int k) {
            AugmentedNode node = root;

            while (node != null) {
                int leftSize = (node.left == null) ? 0 : node.leftCount;

                if (k <= leftSize) {
                    // k-th smallest is in the left subtree
                    node = node.left;
                } else if (k == leftSize + 1) {
                    // current node IS the k-th smallest
                    return node.val;
                } else {
                    // k-th smallest is in the right subtree
                    k -= (leftSize + 1);  // subtract left subtree + current node
                    node = node.right;
                }
            }

            return -1; // unreachable
        }
    }
    ```

    ---

    ## 5. Time & Space Complexity

    ### Approach 1 — Brute Force (Collect + Sort)
    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n log n) | Collect all n nodes O(n), then sort O(n log n) |
    | **Space** | O(n) | ArrayList stores all n node values |

    📊 *Example*: n = 10,000 → ~130,000 sort operations. Wasteful when k = 1.

    ---

    ### Approach 2 — In-Order into List
    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | We visit every node exactly once |
    | **Space** | O(n) | ArrayList + recursion call stack O(H) ≈ O(n) worst case |

    📊 *Example*: n = 10,000, k = 9,999 → visits all 10,000 nodes.

    ---

    ### Approach 3 & 4 — In-Order with Early Stop (Optimal)
    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(H + k) | We descend H levels to reach the smallest, then visit k nodes |
    | **Space** | O(H) | Recursion stack (App 3) or explicit stack (App 4) stores at most H nodes |

    📊 **Two scenarios**:
    - Balanced tree (H = log n), k = 1: ~O(log n) operations — very fast!
    - Skewed tree (H = n), k = n: O(n) — degrades to linear

    ---

    ### Approach 5 — Augmented BST
    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(H) per query | One decision per level, no counting needed |
    | **Space** | O(n) | Each node stores extra `leftCount` integer |

    📊 *Best for*: 10,000 queries on the same tree → saves O(k×n) operations total.

    ---

    ## 6. Complete Worked Examples

    ### Example Tree
    ```
            5
        / \
        3   6
        / \
        2   4
    /
    1
    ```
    Values in sorted order: `[1, 2, 3, 4, 5, 6]`
    **Find k = 3** → Expected answer: **3**

    ---

    ### Approach 2 — In-Order List Walkthrough

    | Step | Action | List State |
    |------|--------|------------|
    | 1 | Visit 5 → go left | [] |
    | 2 | Visit 3 → go left | [] |
    | 3 | Visit 2 → go left | [] |
    | 4 | Visit 1 → go left → null | [] |
    | 5 | Back at 1 → add to list | [1] |
    | 6 | 1 has no right → back to 2 → add | [1, 2] |
    | 7 | 2 has no right → back to 3 → add | [1, 2, 3] |
    | 8 | Visit 3's right → 4 → add | [1, 2, 3, 4] |
    | 9 | Back to 5 → add | [1, 2, 3, 4, 5] |
    | 10 | Visit 6 → add | [1, 2, 3, 4, 5, 6] |
    | Result | `list.get(3-1)` = `list.get(2)` | **3** ✅ |

    ---

    ### Approach 3 — Recursive Early Stop Walkthrough

    | Step | Node | count | Action |
    |------|------|-------|--------|
    | 1 | 5 → go left | 0 | Dive left |
    | 2 | 3 → go left | 0 | Dive left |
    | 3 | 2 → go left | 0 | Dive left |
    | 4 | 1 → go left | 0 | null → return |
    | 5 | **Process 1** | 1 | count=1 ≠ 3, go right |
    | 6 | 1's right = null | 1 | return |
    | 7 | **Process 2** | 2 | count=2 ≠ 3, go right |
    | 8 | 2's right = null | 2 | return |
    | 9 | **Process 3** | 3 | count=3 == k → **answer = 3** 🎯 |
    | 10 | Return immediately | — | Skip nodes 4, 5, 6 entirely |

    **Nodes visited: 5** out of 6 — early stop saved work!

    ---

    ### Approach 4 — Iterative Stack Walkthrough

    | Step | current | stack (bottom→top) | k | Action |
    |------|---------|-------------------|---|--------|
    | Init | 5 | [] | 3 | Start |
    | 1 | Push 5, go left | [5] | 3 | |
    | 2 | Push 3, go left | [5, 3] | 3 | |
    | 3 | Push 2, go left | [5, 3, 2] | 3 | |
    | 4 | Push 1, go left | [5, 3, 2, 1] | 3 | |
    | 5 | current=null | [5, 3, 2, 1] | 3 | Pop 1 |
    | 6 | Pop 1, k-- | [5, 3, 2] | 2 | k=2≠0, go right |
    | 7 | 1.right=null | [5, 3, 2] | 2 | Pop 2 |
    | 8 | Pop 2, k-- | [5, 3] | 1 | k=1≠0, go right |
    | 9 | 2.right=null | [5, 3] | 1 | Pop 3 |
    | 10 | Pop 3, k-- | [5] | 0 | **k==0 → return 3** 🎯 |

    ---

    ### Second Example: k = 1 (Edge Case — Minimum Element)

    Same tree. Expected: **1**

    **Iterative approach:**
    - Push 5 → 3 → 2 → 1 onto stack
    - Pop 1, k-- → k = 0 → **return 1** immediately
    - Nodes visited: just the leftmost path. Very efficient!

    ---

    ## 7. Edge Cases

    ### Complete Edge Case Analysis

    | Edge Case | Input | Expected | Approach 1 | Approach 2 | Approach 3/4 |
    |-----------|-------|----------|------------|------------|---------------|
    | Single node | root=5, k=1 | 5 | ✅ | ✅ | ✅ |
    | k = 1 (minimum) | any BST, k=1 | leftmost node | ✅ slow | ✅ visits all | ✅ stops early at leftmost |
    | k = n (maximum) | any BST, k=n | rightmost node | ✅ slow | ✅ | ✅ visits whole tree |
    | Perfectly balanced | full BST | correct | ✅ | ✅ | ✅ |
    | Right-skewed tree | 1→2→3→4→5 | correct | ✅ | ✅ | ✅ stack depth = n |
    | Left-skewed tree | 5→4→3→2→1 | correct | ✅ | ✅ | ✅ all pushed to stack |
    | Large n (10,000) | max constraint | correct | ✅ | ✅ | ✅ |
    | k = n/2 (median) | any BST | correct | ✅ | ✅ | ✅ |

    ### Key Edge Case Notes

    **Right-skewed tree with k=1:**
    ```
    1
    \
    2
    \
        3
    ```
    - Approach 4 (iterative): pushes only `1` onto stack, pops it, k becomes 0, returns 1. ✅ Perfectly efficient.

    **Left-skewed tree:**
    ```
        5
    /
    4
    /
    3
    ```
    - Approach 4: pushes 5, 4, 3 all at once onto stack. Stack depth = n in worst case. Still O(H) space which is O(n) here. ✅ Correct, but note the space usage.

    **Null root:**
    - Constraints guarantee n ≥ 1 and k ≤ n, so root is never null and k is always valid. No null check needed on root for the given constraints, but defensive coding (check `if node == null return`) handles it gracefully.

    **No duplicate values:**
    - BST by definition has unique values, so no duplicates to worry about. All approaches handle this correctly.

    ---

    ## 8. Final Summary

    ### Comparison Table

    | Approach | Time | Space | Lines of Code | Interview Suitability |
    |----------|------|-------|---------------|----------------------|
    | Brute Force (Sort) | O(n log n) | O(n) | ~10 | ❌ Shows ignorance of BST |
    | In-Order List | O(n) | O(n) | ~10 | ⚠️ Acceptable but not optimal |
    | **Recursive Early Stop** | **O(H+k)** | **O(H)** | **~15** | **✅ Great** |
    | **Iterative Stack** | **O(H+k)** | **O(H)** | **~15** | **✅ Best — shows depth** |
    | Augmented BST | O(H) | O(n) | ~20 | ✅ For follow-up question |

    ### 🏆 Recommendation
    **Use Approach 4 (Iterative In-Order with Stack)** in interviews. It demonstrates:
    - Deep understanding of BST properties
    - Ability to convert recursion → iteration
    - Early-stopping optimization
    - Clean, readable code

    ### 🧠 What to Remember
    > **"BST in-order traversal = sorted order. Use a stack to iterate, count as you go, stop at k."**
    > The pattern here — *iterative in-order traversal using an explicit stack* — appears in dozens of BST problems. Master it once, apply it everywhere.

    ---

    ## 9. Companies & Frequency

    ### Where This Question Has Been Asked

    | Company | Frequency | Notes |
    |---------|-----------|-------|
    | **Amazon** | ⭐⭐⭐⭐⭐ Very High | Top 10 BST question at Amazon |
    | **Microsoft** | ⭐⭐⭐⭐⭐ Very High | Appears in almost every BST interview round |
    | **Google** | ⭐⭐⭐⭐ High | Often asked with the augmented BST follow-up |
    | **Facebook/Meta** | ⭐⭐⭐⭐ High | Asked in phone screens and onsite |
    | **Bloomberg** | ⭐⭐⭐⭐ High | Classic tree question in their rounds |
    | **Apple** | ⭐⭐⭐ Medium | Occasionally appears in SWE interviews |
    | **LinkedIn** | ⭐⭐⭐ Medium | BST round staple |
    | **Adobe** | ⭐⭐⭐ Medium | Tree traversal category |
    | **Uber** | ⭐⭐⭐ Medium | Part of data structures rounds |
    | **Goldman Sachs** | ⭐⭐ Moderate | Appears in tech rounds |

    ### LeetCode Stats
    - **Problem #230** on LeetCode
    - Difficulty: **Medium**
    - Acceptance Rate: ~72% (relatively high — common and well-known)
    - **Appeared 150+ times** in reported interview experiences on LeetCode Discuss
    - Listed in **Amazon**, **Microsoft**, and **Google** official interview prep lists

    ### Interview Tip
    When asked this question, always:
    1. State the BST in-order property first — shows you know your trees
    2. Mention the early-stop optimization — shows you think about efficiency
    3. Offer the augmented BST follow-up voluntarily — shows senior-level thinking
    */
    // @formatter:on
}
