package Trees;

import Datastructures.TreeNode;

public class ConvertSortedArrayToBinarySearchTree {
    public static void main(String[] args) {
        ConvertSortedArrayToBinarySearchTree convertSortedArrayToBinarySearchTree = new ConvertSortedArrayToBinarySearchTree();
        System.out.println("" + convertSortedArrayToBinarySearchTree.sortedArrayToBST(new int[] { -10, -3, 0, 5, 9 }));
    }

    /*
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/94/
     * trees/631/
     * 
     * Given an integer array nums where the elements are sorted in ascending order,
     * convert it to a height-balanced binary search tree.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: nums = [-10,-3,0,5,9]
     * Output: [0,-3,9,-10,null,5]
     * Explanation: [0,-10,5,null,-3,null,9] is also accepted:
     * 
     * Example 2:
     * 
     * 
     * Input: nums = [1,3]
     * Output: [3,1]
     * Explanation: [1,null,3] and [3,1] are both height-balanced BSTs.
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 104
     * -104 <= nums[i] <= 104
     * nums is sorted in a strictly increasing order.
     */

    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length == 0) {
            return null;
        }
        return buildBST(nums, 0, nums.length - 1);
    }

    public TreeNode buildBST(int[] nums, int left, int right) {
        if (left > right)
            return null;

        int middle = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[middle]);
        root.left = buildBST(nums, left, middle - 1);
        root.right = buildBST(nums, middle + 1, right);
        return root;
    }


    /*
        # Convert Sorted Array to Binary Search Tree

        ---

        ## 1. Problem Statement

        ### In Your Own Words
        You are given a **sorted integer array** (sorted in ascending order). Your task is to convert it into a **Height-Balanced Binary Search Tree (BST)**.

        A height-balanced BST means: for every node in the tree, the heights of its left and right subtrees differ by **at most 1**.

        ### Input Format
        - An integer array `nums[]` sorted in **strictly ascending order**
        - Example: `[-10, -3, 0, 5, 9]`

        ### Output Format
        - The **root node** of a height-balanced BST
        - Multiple valid answers may exist — any valid height-balanced BST is accepted

        ### Constraints
        - `1 <= nums.length <= 10^4`
        - `-10^4 <= nums[i] <= 10^4`
        - `nums` is sorted in **strictly ascending order** (no duplicates)

        ### What Exactly Needs to Be Returned
        ```
        TreeNode — the root of the constructed height-balanced BST
        ```

        The `TreeNode` class is defined as:
        ```java
        public class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;
            TreeNode(int val) { this.val = val; }
        }
        ```

        ---

        ## 2. Intuition

        ### The Core Insight
        Think about what makes a BST **balanced**. If you pick the **middle element** of a sorted array as the root, you automatically split the remaining elements into two roughly equal halves — left half becomes the left subtree, right half becomes the right subtree.

        This is the key insight: **the middle element of any subarray is always the root of the balanced BST for that subarray.**

        ### How a Human Would Reason
        Imagine the array: `[1, 2, 3, 4, 5, 6, 7]`

        ```
        Step 1: Middle = index 3 → value 4 → becomes ROOT
        Step 2: Left half  [1, 2, 3] → middle = 2 → LEFT child of root
        Step 3: Right half [5, 6, 7] → middle = 6 → RIGHT child of root
        Step 4: [1] → left child of 2, [3] → right child of 2
        Step 5: [5] → left child of 6, [7] → right child of 6
        ```

        Result:
        ```
                4
            / \
            2   6
            / \ / \
            1  3 5  7
        ```

        This is **perfectly balanced** — every level is fully filled.

        ### What Makes This Interesting
        - The problem elegantly maps to **Divide and Conquer** — you keep splitting subarrays in half
        - It mirrors how **Binary Search** works — always pick the middle
        - The same pattern applies to building **balanced BSTs from sorted data** in databases and search trees
        - Multiple valid answers exist (odd-length arrays have an unambiguous middle; even-length arrays can use either middle)

        ---

        ## 3. Approach Overview

        | # | Approach | Key Idea | Time | Space | Use When |
        |---|----------|----------|------|-------|----------|
        | 1 | Recursive Divide & Conquer (Optimal) | Pick mid as root, recurse on halves | O(n) | O(log n) | Always — this IS the optimal solution |
        | 2 | Iterative with Stack/Queue | Simulate recursion using explicit stack | O(n) | O(n) | When stack overflow is a concern (very large inputs) |

        ### Which Is Optimal?
        **Approach 1 (Recursive)** is optimal and the standard solution. It visits every element exactly once and uses only O(log n) stack space because the tree is balanced. The iterative approach uses O(n) space and is more complex with no real benefit here.

        ---

        ## 4. Detailed Solutions in Java

        ---

        ### Approach 1: Recursive Divide & Conquer ✅ OPTIMAL

        #### Algorithm Step-by-Step
        1. Define a recursive helper with parameters: `left` index, `right` index
        2. **Base case**: if `left > right`, return `null` (empty subarray)
        3. **Find middle**: `mid = left + (right - left) / 2`
        4. **Create root node** with `nums[mid]`
        5. **Recurse left**: call helper on `[left, mid - 1]` → assign to `root.left`
        6. **Recurse right**: call helper on `[mid + 1, right]` → assign to `root.right`
        7. Return `root`

        ```java
        class Solution {

            public TreeNode sortedArrayToBST(int[] nums) {
                return buildBST(nums, 0, nums.length - 1);
            }

            private TreeNode buildBST(int[] nums, int left, int right) {
                // Base case: no elements in this subrange
                if (left > right) {
                    return null;
                }

                // Always pick the middle element as root to ensure balance
                int mid = left + (right - left) / 2;

                TreeNode root = new TreeNode(nums[mid]);

                // Left subtree is built from elements before mid
                root.left = buildBST(nums, left, mid - 1);

                // Right subtree is built from elements after mid
                root.right = buildBST(nums, mid + 1, right);

                return root;
            }
        }
        ```

        ---

        ### Approach 2: Iterative Using Explicit Stack

        #### Algorithm Step-by-Step
        1. Use a **stack** to store tuples of `(node, leftBound, rightBound)` — simulating recursive calls
        2. Create a **dummy root** and push the full range onto the stack
        3. For each stack frame:
        - Compute `mid` of the range
        - Create a `TreeNode` with `nums[mid]`
        - Assign it to the correct parent (left or right child)
        - Push left subrange `[left, mid-1]` if valid
        - Push right subrange `[mid+1, right]` if valid
        4. Continue until stack is empty
        5. Return the actual root (dummy's left child or right child)

        ```java
        class Solution {

            public TreeNode sortedArrayToBST(int[] nums) {
                if (nums == null || nums.length == 0) return null;

                int n = nums.length;
                int rootMid = 0 + (n - 1) / 2;  // middle index for entire array

                TreeNode root = new TreeNode(nums[rootMid]);

                // Stack stores: [node, leftBound, rightBound]
                // We use int[] arrays as lightweight tuples
                Deque<Object[]> stack = new ArrayDeque<>();

                // Push initial left and right subproblems
                stack.push(new Object[]{root, 0, rootMid - 1, "left"});
                stack.push(new Object[]{root, rootMid + 1, n - 1, "right"});

                while (!stack.isEmpty()) {
                    Object[] frame = stack.pop();
                    TreeNode parent = (TreeNode) frame[0];
                    int left     = (int) frame[1];
                    int right    = (int) frame[2];
                    String side  = (String) frame[3];

                    if (left > right) continue; // empty range, skip

                    int mid = left + (right - left) / 2;
                    TreeNode node = new TreeNode(nums[mid]);

                    // Attach to the correct side of the parent
                    if (side.equals("left")) {
                        parent.left = node;
                    } else {
                        parent.right = node;
                    }

                    // Push children subranges onto the stack
                    stack.push(new Object[]{node, left, mid - 1, "left"});
                    stack.push(new Object[]{node, mid + 1, right, "right"});
                }

                return root;
            }
        }
        ```

        > **Note**: The iterative solution is more verbose and uses more space. It's shown here for completeness and to demonstrate how recursion can always be converted to iteration.

        ---

        ## 5. Time & Space Complexity

        ### Approach 1: Recursive Divide & Conquer

        | Metric | Value | Reasoning |
        |--------|-------|-----------|
        | **Time** | O(n) | Every element is visited exactly once to create one `TreeNode`. There are exactly `n` recursive calls total. |
        | **Space** | O(log n) | The recursion depth equals the height of the balanced BST, which is `log₂(n)`. No extra data structures used. |

        #### Walk-through with small inputs:

        **n = 7:**
        - Recursion depth = log₂(7) ≈ 3 levels
        - Total nodes created = 7
        - Stack frames at any one time = at most 3 (one per level)

        **n = 10,000:**
        - Recursion depth ≈ log₂(10,000) ≈ 14 levels
        - Stack frames at any one time ≈ 14
        - Total nodes = 10,000

        ---

        ### Approach 2: Iterative with Stack

        | Metric | Value | Reasoning |
        |--------|-------|-----------|
        | **Time** | O(n) | Same — every element processed once |
        | **Space** | O(n) | In the worst case, the explicit stack holds O(n) frames because we push both children before processing them. The stack can hold up to O(n/2) frames at the leaf level. |

        ---

        ## 6. Complete Worked Examples

        ### Example 1: `nums = [-10, -3, 0, 5, 9]` (Approach 1 - Recursive)

        **Indices:** 0=`-10`, 1=`-3`, 2=`0`, 3=`5`, 4=`9`

        ```
        Call: buildBST(nums, 0, 4)
        mid = 0 + (4-0)/2 = 2  →  nums[2] = 0  → ROOT = 0
        ├── Call: buildBST(nums, 0, 1)     [left subtree]
        │     mid = 0 + (1-0)/2 = 0  →  nums[0] = -10  → node
        │     ├── Call: buildBST(nums, 0, -1)  → left > right → return null
        │     └── Call: buildBST(nums, 1, 1)
        │             mid = 1  →  nums[1] = -3  → node
        │             ├── buildBST(1, 0)  → null
        │             └── buildBST(2, 1)  → null
        │             return node(-3)
        │     → -10.right = -3
        │     return node(-10)
        └── Call: buildBST(nums, 3, 4)     [right subtree]
                mid = 3 + (4-3)/2 = 3  →  nums[3] = 5  → node
                ├── buildBST(3, 2)  → null
                └── buildBST(4, 4)
                        mid = 4  →  nums[4] = 9  → node
                        return node(9)
                → 5.right = 9
                return node(5)

        ROOT = 0
        ```

        **Final Tree:**
        ```
            0
            / \
        -10   5
            \    \
            -3    9
        ```

        **Valid?** ✅
        - BST property: `-10 < 0 < 5` ✓
        - Balanced: height of left = 2, height of right = 2 ✓

        ---

        ### Example 2: `nums = [1, 2, 3, 4, 5, 6, 7]` (Perfect case)

        ```
        Step-by-step state:

        buildBST(0, 6): mid=3 → root=4
        buildBST(0, 2): mid=1 → node=2
            buildBST(0, 0): mid=0 → node=1 (leaf)
            buildBST(2, 2): mid=2 → node=3 (leaf)
        buildBST(4, 6): mid=5 → node=6
            buildBST(4, 4): mid=4 → node=5 (leaf)
            buildBST(6, 6): mid=6 → node=7 (leaf)
        ```

        **Final Tree:**
        ```
                4
            / \
            2   6
            / \ / \
            1  3 5  7
        ```

        Height = 3 = ⌊log₂(7)⌋ + 1 → **Perfectly balanced** ✅

        ---

        ### Example 3: Even-length array `nums = [1, 2, 3, 4]`

        ```
        buildBST(0, 3): mid = 0 + (3)/2 = 1 → root = nums[1] = 2
        buildBST(0, 0): mid=0 → node=1 (leaf)
        buildBST(2, 3): mid=2 → node=3
            buildBST(2, 1): null
            buildBST(3, 3): node=4 (leaf)
        ```

        **Final Tree:**
        ```
            2
        / \
        1   3
            \
                4
        ```

        Height difference between left (1) and right (2) subtrees of root = 1 → **Valid** ✅

        ---

        ## 7. Edge Cases

        | Edge Case | Input Example | Behavior | Notes |
        |-----------|--------------|----------|-------|
        | **Single element** | `[5]` | Returns a single root node with value 5 | `left=0, right=0`, `mid=0`, no children |
        | **Two elements** | `[1, 2]` | Root = `1`, right child = `2` | `mid=0`, left subtree is null, right has `2` |
        | **All negative** | `[-9, -5, -1]` | Works identically | No special handling needed, values don't affect logic |
        | **Large array** | `n = 10,000` | Recursion depth ≈ 14 — no stack overflow | Java default stack handles this comfortably |
        | **Already balanced input** | `[1,2,3,4,5,6,7]` | Produces a perfect BST | Best case visually |
        | **Minimum size** | `[]` or `null` | `left > right` immediately → null returned | Base case handles it cleanly |
        | **Negative + positive mix** | `[-3, 0, 5]` | Mid = `0` becomes root | No issues with mixed signs |
        | **Overflow in mid calculation** | Large indices | Use `left + (right - left) / 2` NOT `(left + right) / 2` | The code already handles this safely |

        ### Key Risk: Mid Calculation Overflow
        ```java
        // WRONG — can overflow if left and right are both large
        int mid = (left + right) / 2;

        // CORRECT — safe for all int values
        int mid = left + (right - left) / 2;
        ```
        Although array indices won't overflow `int` for this problem's constraints (`n ≤ 10^4`), this is a **critical habit** in competitive programming.

        ---

        ## 8. Final Summary

        ### Approach Comparison

        | | Recursive (Optimal) | Iterative |
        |--|---------------------|-----------|
        | **Time** | O(n) | O(n) |
        | **Space** | O(log n) ✅ | O(n) |
        | **Code complexity** | Simple, elegant | Verbose |
        | **Recommended?** | ✅ YES | Only if recursion depth is a hard constraint |

        ### Recommendation
        **Use the recursive divide-and-conquer approach.** It is clean, correct, efficient, and the natural solution for this problem. It will be expected in any interview setting.

        ### What to Remember
        > **Pattern:** Sorted array → BST = always pick the middle element as root and recurse on halves. This is the same mental model as binary search, just applied to tree construction.

        > **Technique:** Divide and Conquer with index-based recursion — never copy subarrays, always pass `left` and `right` bounds for O(1) overhead per call.
    */
}
