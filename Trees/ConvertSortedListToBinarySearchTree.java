package Trees;

import Datastructures.ListNode;
import Datastructures.TreeNode;

public class ConvertSortedListToBinarySearchTree {
    public static void main(String[] args) {
        ConvertSortedListToBinarySearchTree convertSortedListToBinarySearchTree = new ConvertSortedListToBinarySearchTree();
        ListNode head = new ListNode(-10);
        head.next = new ListNode(-3);
        head.next.next = new ListNode(0);
        head.next.next.next = new ListNode(5);
        head.next.next.next.next = new ListNode(9);
        System.out.println(
                "ConvertSortedArrayToBinarySearchTree : \n"
                        + convertSortedListToBinarySearchTree.sortedListToBSTTwoPointer(head));

        ListNode head1 = new ListNode(-10);
        head1.next = new ListNode(-3);
        head1.next.next = new ListNode(0);
        head1.next.next.next = new ListNode(5);
        head1.next.next.next.next = new ListNode(9);
        System.out.println(
                "ConvertSortedArrayToBinarySearchTree : \n"
                        + convertSortedListToBinarySearchTree.sortedListToBSTInOrderSimulation(head1));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/description/?envType=problem-list-v2&envId=tree
     * 
     * Given the head of a singly linked list where elements are sorted in ascending
     * order, convert it to a height-balanced binary search tree.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * 
     * Input Linked List:
     * -10 -> -3 -> 0 -> 5 -> 9
     * 
     * Corresponding Height-Balanced BST:
     *       0
     *      / \
     *    -3   5
     *    /     \
     *  -10      9
     * 
     * Input: head = [-10,-3,0,5,9]
     * Output: [0,-3,9,-10,null,5]
     * Explanation: One possible answer is [0,-3,9,-10,null,5], which represents the
     * shown height balanced BST.
     * Example 2:
     * 
     * Input: head = []
     * Output: []
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in head is in the range [0, 2 * 104].
     * -105 <= Node.val <= 105
     */
    // @formatter:on

    public TreeNode sortedListToBSTTwoPointer(ListNode head) {
        if (head == null)
            return null;
        if (head.next == null)
            return new TreeNode(head.val);
        ListNode prevMiddle = getPrevMiddle(head);
        ListNode middle = prevMiddle.next;
        TreeNode root = new TreeNode(middle.val);
        prevMiddle.next = null;
        root.left = sortedListToBSTTwoPointer(head);
        root.right = sortedListToBSTTwoPointer(middle.next);
        return root;
    }

    public ListNode getPrevMiddle(ListNode head) {
        ListNode prev = head;
        ListNode fast = head.next;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        return prev;
    }

    private ListNode current;

    public TreeNode sortedListToBSTInOrderSimulation(ListNode head) {
        int size = countNodes(head);
        current = head;
        return inOrderBuild(0, size - 1);
    }

    public TreeNode inOrderBuild(int left, int right) {
        if (left > right)
            return null;
        int mid = left + (right - left) / 2;
        TreeNode leftChild = inOrderBuild(left, mid - 1);
        TreeNode root = new TreeNode(current.val);
        current = current.next;
        root.left = leftChild;
        root.right = inOrderBuild(mid + 1, right);
        return root;
    }

    public int countNodes(ListNode head) {
        int count = 0;
        while (head != null) {
            count++;
            head = head.next;
        }
        return count;
    }

    // @formatter:off
    /*
    # Convert Sorted List to Binary Search Tree

    ---

    ## 1. Problem Statement

    ### In Plain Terms
    You are given the **head of a singly linked list** where elements are sorted in **ascending order**. Your task is to convert it into a **height-balanced Binary Search Tree (BST)**.

    A height-balanced BST means: for every node, the heights of the left and right subtrees differ by **at most 1**.

    ### Input Format
    - Head node of a singly linked list: `ListNode head`
    - Each `ListNode` has: `int val`, `ListNode next`

    ### Output Format
    - Root node of a height-balanced BST: `TreeNode root`
    - Each `TreeNode` has: `int val`, `TreeNode left`, `TreeNode right`

    ### Constraints
    - Number of nodes: `0 <= n <= 2 * 10⁴`
    - Node values: `-10⁵ <= Node.val <= 10⁵`
    - The list is guaranteed to be sorted in **ascending (non-decreasing) order**

    ### What Needs to Be Computed
    Find the **middle element** of the list (or sublist) repeatedly — it becomes the root (ensuring balance), then recursively build left and right subtrees from the left and right halves of the list.

    ---

    ## 2. Intuition

    ### The Core Idea
    A sorted array/list maps naturally to a BST because **in-order traversal of a BST gives a sorted sequence**. So we're essentially working backwards: given the sorted sequence, reconstruct the BST.

    ### Human Reasoning
    1. If the list were `[-10, -3, 0, 5, 9]`, a balanced BST needs `0` at the root (middle element)
    2. Left subtree comes from `[-10, -3]` → middle is `-3`, with `-10` as its left child
    3. Right subtree comes from `[5, 9]` → middle is `9`, with `5` as its left child
    4. This is exactly **divide and conquer** — split at the midpoint, recurse on both halves

    ### What Makes This Tricky
    - **Linked lists don't support random access** — finding the middle requires traversal (unlike arrays)
    - Maintaining **height balance** requires always picking the middle element
    - The most elegant optimal solution uses **in-order simulation** — building the tree in sync with traversing the list, which is a non-obvious insight

    ---

    ## 3. Approach Overview

    | # | Approach | Key Idea | Time | Space | Use When |
    |---|----------|----------|------|-------|----------|
    | 1 | **Brute Force** — Convert to Array | Store all values in ArrayList, then use array-based recursion | O(n) | O(n) | Always safe; simple to code |
    | 2 | **Better** — Slow/Fast Pointer (In-place) | Find midpoint via two-pointer, recursively split the linked list | O(n log n) | O(log n) | Space-conscious but slower |
    | 3 | **Optimal** — In-order Simulation | Simulate in-order traversal; advance list pointer in sync with tree construction | O(n) | O(log n) | Interviews & production |

    ### ✅ Recommended: Approach 3 (In-order Simulation)
    It achieves **O(n) time and O(log n) space** — best possible — by cleverly avoiding repeated traversal of the list.

    ---

    ## 4. Detailed Solutions in Java

    ### Node Definitions (Shared Across All Approaches)

    ```java
    // Definition for singly-linked list node
    class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // Definition for binary tree node
    class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }
    ```

    ---

    ### ✅ Approach 1 — Brute Force: Convert to Array

    #### Algorithm (Step-by-Step)
    1. Traverse the linked list, store all values in an `ArrayList<Integer>`
    2. Define a recursive function `buildBST(list, left, right)`
    3. Base case: if `left > right`, return `null`
    4. Find `mid = left + (right - left) / 2`
    5. Create a `TreeNode` with `list.get(mid)` as value
    6. Recursively build left subtree from `[left, mid-1]`
    7. Recursively build right subtree from `[mid+1, right]`
    8. Return the root node

    ```java
    class Solution {
        public TreeNode sortedListToBST(ListNode head) {
            // Step 1: Collect all values from the linked list
            List<Integer> values = new ArrayList<>();
            ListNode current = head;
            while (current != null) {
                values.add(current.val);
                current = current.next;
            }
            // Step 2: Build BST recursively using array indices
            return buildBST(values, 0, values.size() - 1);
        }

        private TreeNode buildBST(List<Integer> values, int left, int right) {
            // Base case: no elements in this range
            if (left > right) return null;

            // Always pick the middle element to ensure height balance
            int mid = left + (right - left) / 2;
            TreeNode node = new TreeNode(values.get(mid));

            // Recurse on left and right halves
            node.left  = buildBST(values, left, mid - 1);
            node.right = buildBST(values, mid + 1, right);

            return node;
        }
    }
    ```

    ---

    ### ✅ Approach 2 — Better: Slow/Fast Pointer (Two-Pointer Midpoint)

    #### Algorithm (Step-by-Step)
    1. Use **slow/fast pointer** technique to find the midpoint of the list
    2. Detach the left half from the midpoint (set `prev.next = null`)
    3. Create a `TreeNode` with the midpoint's value
    4. Recursively build the left subtree from `head` to `mid - 1`
    5. Recursively build the right subtree from `mid.next` onward
    6. Return root

    ```java
    class Solution {
        public TreeNode sortedListToBST(ListNode head) {
            // Base cases
            if (head == null) return null;
            if (head.next == null) return new TreeNode(head.val);

            // Find the middle node and the node just before it
            ListNode prevMiddle = getPrevMiddle(head);
            ListNode middle = prevMiddle.next;

            // Detach left half: left sublist ends just before middle
            prevMiddle.next = null;

            // Build current node from middle value
            TreeNode node = new TreeNode(middle.val);

            // Left subtree: [head ... prevMiddle]
            node.left  = sortedListToBST(head);

            // Right subtree: [middle.next ... end]
            node.right = sortedListToBST(middle.next);

            return node;
        }

        // Returns the node just BEFORE the middle (to allow detachment)
        private ListNode getPrevMiddle(ListNode head) {
            ListNode prevSlow = head;
            ListNode slow = head;
            ListNode fast = head.next;

            while (fast != null && fast.next != null) {
                prevSlow = slow;
                slow = slow.next;
                fast = fast.next.next;
            }
            return prevSlow;
        }
    }
    ```

    ---

    ### ✅ Approach 3 — Optimal: In-Order Simulation

    #### Core Insight
    In-order traversal of a BST visits nodes in **left → root → right** order, which maps perfectly to a sorted list traversed **left to right**. So instead of finding the mid explicitly, we:
    1. Count the total number of nodes `n`
    2. Recursively simulate building the tree by range `[left, right]`
    3. The **left subtree is built first** (in-order), which advances the `current` list pointer automatically
    4. Then we read the current list node for the root
    5. Then the right subtree is built

    This avoids re-traversal at every level — each list node is visited **exactly once**.

    ```java
    class Solution {
        // Global pointer into the linked list — advances as we do in-order traversal
        private ListNode current;

        public TreeNode sortedListToBST(ListNode head) {
            // Count total nodes in the list
            int size = countNodes(head);

            // Initialize global pointer to start of list
            current = head;

            return inOrderBuild(0, size - 1);
        }

        private TreeNode inOrderBuild(int left, int right) {
            // Base case: empty range
            if (left > right) return null;

            int mid = left + (right - left) / 2;

            // 1. Recursively build the LEFT subtree first
            //    This will advance `current` to the correct list node for this root
            TreeNode leftChild = inOrderBuild(left, mid - 1);

            // 2. `current` is now pointing at the mid-th element — use it as root
            TreeNode node = new TreeNode(current.val);
            current = current.next; // Advance pointer AFTER reading

            // 3. Attach the left subtree
            node.left = leftChild;

            // 4. Recursively build the RIGHT subtree
            node.right = inOrderBuild(mid + 1, right);

            return node;
        }

        private int countNodes(ListNode head) {
            int count = 0;
            while (head != null) {
                count++;
                head = head.next;
            }
            return count;
        }
    }
    ```

    ---

    ## 5. Time & Space Complexity

    ### Approach 1 — Array Conversion

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | One pass to build array + O(n) recursive calls, each doing O(1) work |
    | **Space** | O(n) | ArrayList of size n + O(log n) recursion stack → dominated by O(n) |

    **Walk-through:** For n=5: 5 array writes + 5 tree nodes created = 10 operations. For n=10,000: ~20,000 operations.

    ---

    ### Approach 2 — Two Pointer (In-place)

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n log n) | At each recursion level, we scan the sublist to find the midpoint. There are O(log n) levels, each scanning O(n) total nodes across all subproblems |
    | **Space** | O(log n) | No extra array; recursion stack depth is O(log n) for a balanced tree |

    **Walk-through:** For n=16: Level 0 scans 16 nodes, Level 1 scans 8+8=16, Level 2 scans 4×4=16 — 4 levels × 16 = 64 ≈ n log n.

    ---

    ### Approach 3 — In-Order Simulation ✅

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(n) | Each list node is visited exactly once (pointer only moves forward); O(n) recursive calls each do O(1) work |
    | **Space** | O(log n) | Only the recursion call stack, depth = height of balanced BST = O(log n) |

    **Walk-through:** For n=10,000: exactly 10,000 node reads + 10,000 TreeNode creations = O(n). Stack depth ≈ log₂(10000) ≈ 14 frames.

    ---

    ## 6. Complete Worked Examples

    ### Input: `head = [-10, -3, 0, 5, 9]`

    ---

    #### Approach 1 — Array Method

    ```
    values = [-10, -3, 0, 5, 9]  (indices 0..4)

    buildBST(0, 4):
    mid = 2 → node(0)
    ├── buildBST(0, 1):
    │     mid = 0 → node(-10)  ❌ Wait: mid = 0+(1-0)/2 = 0
    │     Actually: mid = 0 + (1-0)/2 = 0 → node(-10)
    │     ├── buildBST(0, -1) → null
    │     └── buildBST(1, 1):
    │           mid = 1 → node(-3)
    │           ├── null
    │           └── null
    │     → node(-10).right = node(-3)
    └── buildBST(3, 4):
            mid = 3 → node(5)
            ├── buildBST(3, 2) → null
            └── buildBST(4, 4):
                mid = 4 → node(9)
                → node(5).right = node(9)
    ```

    **Resulting Tree:**
    ```
            0
        / \
        -10    5
        \     \
        -3     9
    ```
    ✅ Height-balanced BST

    ---

    #### Approach 3 — In-Order Simulation (Most Important to Trace)

    **Setup:** `size = 5`, `current → -10`

    ```
    inOrderBuild(0, 4):  mid = 2
    │
    ├── LEFT: inOrderBuild(0, 1):  mid = 0
    │     ├── LEFT: inOrderBuild(0, -1) → null
    │     │
    │     ├── READ current(-10), advance current → -3
    │     │   node = TreeNode(-10), node.left = null
    │     │
    │     └── RIGHT: inOrderBuild(1, 1):  mid = 1
    │           ├── LEFT: inOrderBuild(1, 0) → null
    │           ├── READ current(-3), advance current → 0
    │           │   node = TreeNode(-3), node.left = null
    │           └── RIGHT: inOrderBuild(2, 1) → null
    │           return TreeNode(-3)
    │
    │     → node(-10).right = node(-3)
    │     return TreeNode(-10)
    │
    ├── READ current(0), advance current → 5
    │   node = TreeNode(0), node.left = TreeNode(-10)
    │
    └── RIGHT: inOrderBuild(3, 4):  mid = 3
            ├── LEFT: inOrderBuild(3, 2) → null
            ├── READ current(5), advance current → 9
            │   node = TreeNode(5), node.left = null
            └── RIGHT: inOrderBuild(4, 4):  mid = 4
                ├── LEFT: inOrderBuild(4, 3) → null
                ├── READ current(9), advance current → null
                └── RIGHT: inOrderBuild(5, 4) → null
                return TreeNode(9)
            → node(5).right = node(9)
            return TreeNode(5)

    Final: TreeNode(0).left = TreeNode(-10), TreeNode(0).right = TreeNode(5)
    ```

    **Output Tree:**
    ```
            0
        / \
        -10    5
        \     \
        -3     9
    ```

    ---

    #### Input: Single element `head = [1]`
    - `size = 1`, `current → 1`
    - `inOrderBuild(0, 0)`: mid=0, no left recursion, read `1`, no right recursion
    - **Output:** `TreeNode(1)` — root only ✅

    ---

    ## 7. Edge Cases

    | Edge Case | Input | Expected Output | All 3 Approaches Handle? |
    |-----------|-------|-----------------|--------------------------|
    | **Empty list** | `head = null` | `null` | ✅ All handle via `if (head == null)` or `size = 0` → `inOrderBuild(0, -1)` returns null |
    | **Single node** | `[5]` | `TreeNode(5)` | ✅ All correct |
    | **Two nodes** | `[1, 3]` | Root=1, right=3 OR Root=3, left=1 (both valid) | ✅ Approach 1 & 3: mid=0 → root=1, right=3 |
    | **All negatives** | `[-9, -5, -3]` | Valid BST | ✅ Values don't affect structure logic |
    | **Duplicates** | `[1, 1, 1, 1]` | Valid height-balanced BST | ✅ All handle duplicates (BST property: equal vals can go right) |
    | **Large n = 20,000** | Large list | Must not stack overflow | ✅ Approaches 1 & 3 use O(log n) stack ≈ 15 frames — safe |
    | **Already a BST structure** | `[1, 2, 3]` | Height-balanced tree | ✅ |
    | **Negative + Positive** | `[-5, 0, 5]` | Root=0, left=-5, right=5 | ✅ |

    ### Checking for Bugs:

    **Approach 1:** `mid = left + (right - left) / 2` avoids integer overflow vs. `(left + right) / 2` ✅

    **Approach 2:** When list has 2 nodes — `getPrevMiddle` returns `head` (prevSlow never moves since `fast.next == null` immediately), `middle = head.next`, `prevSlow.next = null` detaches correctly ✅

    **Approach 3:** `current` is a class-level field. In multi-test environments (e.g., LeetCode calling `sortedListToBST` multiple times), `current` is reset at the start of each call ✅

    ---

    ## 8. Final Summary

    ### Comparison Table

    | Approach | Time | Space | Code Complexity | Recommendation |
    |----------|------|-------|-----------------|----------------|
    | Array Conversion | O(n) | O(n) | Very Simple | ✅ Default choice for interviews |
    | Two-Pointer In-place | O(n log n) | O(log n) | Moderate | Use only if space is critical and O(n log n) is acceptable |
    | In-Order Simulation | O(n) | O(log n) | Elegant but requires insight | ✅ Best overall — optimal in both dimensions |

    ### 🏆 Recommended in Practice
    **Approach 3 (In-Order Simulation)** is the gold standard — O(n) time and O(log n) space. For most interviews, **Approach 1 (Array)** is perfectly acceptable and faster to code under pressure.

    ### Key Pattern to Remember
    > **"In-order traversal of a BST = sorted sequence."** This bidirectional insight — sorted list → BST, BST → sorted list — is the foundation of this problem and several related ones (e.g., Convert Sorted Array to BST, Validate BST, BST Iterator).

    ---

    ## 9. Company Appearances & Frequency

    | Company | Frequency | Notes |
    |---------|-----------|-------|
    | **Amazon** | ⭐⭐⭐⭐⭐ Very High | Top asked; appears in SDE-1 and SDE-2 rounds |
    | **Microsoft** | ⭐⭐⭐⭐ High | Common in coding + system design preliminary rounds |
    | **Facebook / Meta** | ⭐⭐⭐⭐ High | Linked list + tree combo is a Meta favorite |
    | **Google** | ⭐⭐⭐ Medium | Often as a follow-up to "Convert Sorted Array to BST" |
    | **Adobe** | ⭐⭐⭐ Medium | Appears in backend/data engineering interviews |
    | **Apple** | ⭐⭐ Medium-Low | Occasionally in iOS/backend roles |
    | **Bloomberg** | ⭐⭐⭐ Medium | Common in financial software engineering interviews |

    **LeetCode Problem #109** — Reported **over 800+ accepted solutions discussions** and listed as a **Medium** problem with high frequency in real interview reports on Glassdoor and LeetCode Discuss.
    */
    // @formatter:on

}
