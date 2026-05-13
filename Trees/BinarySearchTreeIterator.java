package Trees;

import java.util.Deque;

import Datastructures.TreeNode;

public class BinarySearchTreeIterator {
    public static void main(String[] args) {

    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/binary-search-tree-iterator/description/?envType=problem-list-v2&envId=tree
     * 
     * 
     * Implement the BSTIterator class that represents an iterator over the in-order
     * traversal of a binary search tree (BST):
     * 
     * BSTIterator(TreeNode root) Initializes an object of the BSTIterator class.
     * The root of the BST is given as part of the constructor. The pointer should
     * be initialized to a non-existent number smaller than any element in the BST.
     * boolean hasNext() Returns true if there exists a number in the traversal to
     * the right of the pointer, otherwise returns false.
     * int next() Moves the pointer to the right, then returns the number at the
     * pointer.
     * Notice that by initializing the pointer to a non-existent smallest number,
     * the first call to next() will return the smallest element in the BST.
     * 
     * You may assume that next() calls will always be valid. That is, there will be
     * at least a next number in the in-order traversal when next() is called.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input
     * ["BSTIterator", "next", "next", "hasNext", "next", "hasNext", "next",
     * "hasNext", "next", "hasNext"]
     * [[[7, 3, 15, null, null, 9, 20]], [], [], [], [], [], [], [], [], []]
     * Output
     * [null, 3, 7, true, 9, true, 15, true, 20, false]
     * 
     * Explanation
     * BSTIterator bSTIterator = new BSTIterator([7, 3, 15, null, null, 9, 20]);
     * bSTIterator.next(); // return 3
     * bSTIterator.next(); // return 7
     * bSTIterator.hasNext(); // return True
     * bSTIterator.next(); // return 9
     * bSTIterator.hasNext(); // return True
     * bSTIterator.next(); // return 15
     * bSTIterator.hasNext(); // return True
     * bSTIterator.next(); // return 20
     * bSTIterator.hasNext(); // return False
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [1, 105].
     * 0 <= Node.val <= 106
     * At most 105 calls will be made to hasNext, and next.
     * 
     * 
     * Follow up:
     * 
     * Could you implement next() and hasNext() to run in average O(1) time and use
     * O(h) memory, where h is the height of the tree?
     */
    // @formatter:on

    private Deque<TreeNode> stack;

    public BinarySearchTreeIterator() {

    }

    // public BinarySearchTreeIterator(TreeNode root) {
    // stack = new ArrayDeque<>();
    // pushAllLeft(root);
    // }

    // public int next() {
    // TreeNode current = stack.pop();
    // pushAllLeft(current.right);
    // return current.val;
    // }

    // public boolean hasNext() {
    // return !stack.isEmpty();
    // }

    private TreeNode current;
    private Integer nextVal;

    public BinarySearchTreeIterator(TreeNode root) {
        current = root;
        nextVal = null;
        advance();
    }

    public int next() {
        int result = nextVal;
        advance();
        return result;
    }

    public boolean hasNext() {
        return nextVal != null;
    }

    private void advance() {
        nextVal = null;
        while (current != null && nextVal == null) {
            if (current.left == null) {
                nextVal = current.val;
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
                    nextVal = current.val;
                    current = current.right;
                }
            }
        }
    }

    public void pushAllLeft(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    // @formatter:off
    /*
    # Binary Search Tree Iterator — Deep Dive

    ---

    ## 1. Problem Statement

    ### Restate in Plain Words
    You are given the **root of a Binary Search Tree (BST)**. You need to implement an iterator class (`BSTIterator`) that iterates over the BST in **ascending (in-order) order**.

    ### The Class Must Support:
    | Method | Description |
    |---|---|
    | `BSTIterator(TreeNode root)` | Constructor — initializes the iterator with the BST root |
    | `int next()` | Returns the **next smallest** integer in the BST |
    | `boolean hasNext()` | Returns `true` if there exists a next smallest number |

    ### Constraints:
    - Number of nodes: `[1, 10^5]`
    - Node values: `[-10^6, 10^6]`
    - At most `10^5` calls to `next()` and `hasNext()` combined
    - `next()` is always called when `hasNext()` is `true` (guaranteed valid calls)

    ### What Needs to Be Computed:
    Simulate an **in-order traversal** of a BST **lazily** — meaning you don't traverse everything upfront but rather produce values **one at a time**, on demand.

    ---

    ## 2. Intuition

    ### Core Idea in Simple Terms
    In a BST, an **in-order traversal (Left → Root → Right)** naturally produces all node values in **sorted ascending order**.

    The naive way: do a full in-order traversal upfront, store all values in a list, and use an index pointer to serve `next()`. But this uses O(n) space.

    The clever way: **simulate** the in-order traversal using an **explicit stack**, processing nodes **lazily** — only going as deep as needed at each step.

    ### How a Human Reasons About It:
    1. In-order means: go **as far left as possible** first.
    2. When you can't go left anymore, that node is the **next smallest**.
    3. After visiting a node, check its **right subtree** — and again go as far left as possible.
    4. An explicit stack mimics the **call stack** of recursive in-order traversal.

    ### What Makes This Tricky:
    - You must **pause** the traversal between `next()` calls — a recursive solution can't pause mid-execution.
    - The stack-based approach achieves **O(h) average space** (h = tree height), not O(n).
    - `next()` appears O(1) amortized — but individual calls can be O(h) in the worst case.

    ---

    ## 3. Approach Overview

    | # | Approach | Space | Time (next) | Notes |
    |---|---|---|---|---|
    | 1 | **Brute Force** — Full In-Order into List | O(n) | O(1) | Simple, interview-ok for small inputs |
    | 2 | **Optimal** — Controlled Stack-Based Lazy Traversal | O(h) | O(h) worst, O(1) amortized | ✅ Recommended |
    | 3 | **Morris Traversal** (Advanced) | O(1) | O(1) amortized | Complex, rarely asked in interviews |

    > ✅ **Approach 2 (Stack-based Lazy Traversal)** is the **standard expected answer** in interviews. It satisfies the follow-up constraint of O(h) space.

    ---

    ## 4. Detailed Solutions in Java

    ---

    ### ✅ Approach 1: Brute Force — Full In-Order into List

    #### Algorithm Step-by-Step:
    1. In the constructor, perform a **complete in-order DFS** of the BST.
    2. Store all node values in an `ArrayList<Integer>`.
    3. Maintain a pointer `index` starting at 0.
    4. `next()` returns `list.get(index++)`.
    5. `hasNext()` returns `index < list.size()`.

    ```java
    import java.util.ArrayList;
    import java.util.List;

    class BSTIterator {

        private List<Integer> sortedValues;
        private int index;

        public BSTIterator(TreeNode root) {
            sortedValues = new ArrayList<>();
            index = 0;
            inOrderTraversal(root);
        }

        // Perform full in-order traversal and collect values
        private void inOrderTraversal(TreeNode node) {
            if (node == null) return;
            inOrderTraversal(node.left);       // Visit left subtree
            sortedValues.add(node.val);        // Visit current node
            inOrderTraversal(node.right);      // Visit right subtree
        }

        public int next() {
            return sortedValues.get(index++);  // Return current and advance pointer
        }

        public boolean hasNext() {
            return index < sortedValues.size();
        }
    }
    ```

    ---

    ### ✅ Approach 2 (OPTIMAL): Controlled Stack-Based Lazy Traversal

    #### Algorithm Step-by-Step:
    1. Maintain an explicit `Deque<TreeNode>` (used as a stack).
    2. In the constructor, call `pushAllLeft(root)` — push the root and all left descendants onto the stack. The top of the stack is always the **next smallest node**.
    3. `hasNext()` → return `!stack.isEmpty()`.
    4. `next()`:
    - Pop the top node (this is the next smallest).
    - Call `pushAllLeft(node.right)` to prepare for future calls.
    - Return the popped node's value.
    5. `pushAllLeft(node)` → keeps pushing a node and all its left children onto the stack.

    ```java
    import java.util.ArrayDeque;
    import java.util.Deque;

    class BSTIterator {

        // Stack simulates the recursive call stack of in-order traversal
        private Deque<TreeNode> stack;

        public BSTIterator(TreeNode root) {
            stack = new ArrayDeque<>();
            pushAllLeft(root); // Initialize: push root and all left descendants
        }

        // Push the given node and all of its left children onto the stack
        // After this call, stack.peek() is the current minimum in the subtree
        private void pushAllLeft(TreeNode node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        public int next() {
            TreeNode currentMin = stack.pop();       // Top is always the next smallest
            pushAllLeft(currentMin.right);           // Prepare right subtree for future calls
            return currentMin.val;
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }
    }
    ```

    ---

    ### Approach 3 (Advanced): Morris Traversal

    #### Algorithm Step-by-Step:
    Morris Traversal modifies the tree temporarily (threading pointers) to achieve O(1) space. It is **not commonly expected in interviews** but demonstrates deep understanding.

    1. Maintain a `current` pointer starting at root.
    2. If `current` has no left child → it's the next value; move to `current.right`.
    3. If `current` has a left child → find the **in-order predecessor** (rightmost node of left subtree).
    - If predecessor's right is null → thread it to `current`, move left.
    - If predecessor's right is `current` → unthread, visit `current`, move right.

    ```java
    class BSTIteratorMorris {

        private TreeNode current;
        private Integer nextVal;

        public BSTIteratorMorris(TreeNode root) {
            current = root;
            nextVal = null;
            advance(); // Pre-compute the first value
        }

        // Advance the Morris traversal to compute the next in-order value
        private void advance() {
            nextVal = null;
            while (current != null && nextVal == null) {
                if (current.left == null) {
                    // No left subtree: current node is next in-order value
                    nextVal = current.val;
                    current = current.right;
                } else {
                    // Find in-order predecessor (rightmost node of left subtree)
                    TreeNode predecessor = current.left;
                    while (predecessor.right != null && predecessor.right != current) {
                        predecessor = predecessor.right;
                    }

                    if (predecessor.right == null) {
                        // Thread the predecessor's right to current, go left
                        predecessor.right = current;
                        current = current.left;
                    } else {
                        // Unthread: predecessor already points here → visit current
                        predecessor.right = null;
                        nextVal = current.val;
                        current = current.right;
                    }
                }
            }
        }

        public int next() {
            int result = nextVal;
            advance(); // Compute the next value for future call
            return result;
        }

        public boolean hasNext() {
            return nextVal != null;
        }
    }
    ```

    > ⚠️ Morris traversal **temporarily modifies the tree**. If the tree is shared or immutable, use Approach 2.

    ---

    ## 5. Time & Space Complexity

    ### Approach 1 — Brute Force (List)

    | | Complexity | Reasoning |
    |---|---|---|
    | **Constructor** | O(n) time | Visits all n nodes once during full in-order traversal |
    | **next()** | O(1) time | Simple indexed list access |
    | **hasNext()** | O(1) time | Single comparison |
    | **Space** | O(n) | Stores all n node values in the list |

    **Example:** 1,000 nodes → constructor does 1,000 node visits; each `next()` is 1 operation.

    ---

    ### Approach 2 — Stack-Based (OPTIMAL)

    | | Complexity | Reasoning |
    |---|---|---|
    | **Constructor** | O(h) time | Pushes only the leftmost path of length h |
    | **next()** | O(h) worst case, **O(1) amortized** | Each node is pushed and popped exactly once across all calls |
    | **hasNext()** | O(1) time | Stack empty check |
    | **Space** | **O(h)** | Stack holds at most h nodes (the leftmost path at any point) |

    **Amortized Analysis:** Over n calls to `next()`, total work = n pushes + n pops = O(n) total → O(1) per call amortized.

    **Example (Balanced BST, n=1000):** h ≈ log₂(1000) ≈ 10 → stack holds at most ~10 nodes. Extremely space-efficient!

    **Example (Skewed BST, n=1000):** h = 1000 → stack holds up to 1000 nodes. Still O(h) = O(n) in worst case for space.

    ---

    ### Approach 3 — Morris Traversal

    | | Complexity | Reasoning |
    |---|---|---|
    | **Constructor** | O(h) time | Advance() explores to first in-order node |
    | **next()** | O(1) amortized | Each node visited at most twice |
    | **Space** | **O(1)** | No extra data structures; uses tree threading |

    ---

    ## 6. Complete Worked Examples

    ### Example BST:
    ```
            7
        / \
        3   15
            /  \
            9   20
    ```
    In-order (expected): 3, 7, 9, 15, 20

    ---

    ### Approach 1 Walkthrough

    **Constructor:**
    - `inOrderTraversal(7)` → visits: 3, 7, 9, 15, 20
    - `sortedValues = [3, 7, 9, 15, 20]`, `index = 0`

    | Call | index before | Returns | index after |
    |---|---|---|---|
    | `hasNext()` | 0 | true | 0 |
    | `next()` | 0 | 3 | 1 |
    | `next()` | 1 | 7 | 2 |
    | `hasNext()` | 2 | true | 2 |
    | `next()` | 2 | 9 | 3 |
    | `next()` | 3 | 15 | 4 |
    | `next()` | 4 | 20 | 5 |
    | `hasNext()` | 5 | false | 5 |

    ---

    ### Approach 2 Walkthrough (Stack-Based) ✅

    **Constructor — `pushAllLeft(7)`:**
    - Push 7 → stack: `[7]`
    - Push 3 (7's left) → stack: `[7, 3]`
    - 3 has no left → stop
    - **Initial stack (top → bottom): `[3, 7]`**

    ---

    **Call 1: `next()`**
    - Pop `3` (top = smallest) → result = **3**
    - `pushAllLeft(3.right = null)` → nothing pushed
    - Stack: `[7]`

    ---

    **Call 2: `next()`**
    - Pop `7` → result = **7**
    - `pushAllLeft(7.right = 15)`:
    - Push 15 → stack: `[15]`
    - Push 9 (15's left) → stack: `[15, 9]`
    - 9 has no left → stop
    - Stack (top→bottom): `[9, 15]`

    ---

    **Call 3: `next()`**
    - Pop `9` → result = **9**
    - `pushAllLeft(9.right = null)` → nothing
    - Stack: `[15]`

    ---

    **Call 4: `next()`**
    - Pop `15` → result = **15**
    - `pushAllLeft(15.right = 20)`:
    - Push 20 → stack: `[20]`
    - 20 has no left → stop
    - Stack: `[20]`

    ---

    **Call 5: `next()`**
    - Pop `20` → result = **20**
    - `pushAllLeft(20.right = null)` → nothing
    - Stack: `[]`

    ---

    **Call 6: `hasNext()`**
    - Stack is empty → returns **false** ✅

    **Summary Table:**

    | Step | Action | Stack State | Output |
    |---|---|---|---|
    | Init | pushAllLeft(7) | [3, 7] | — |
    | next() | pop 3, pushAllLeft(null) | [7] | 3 |
    | next() | pop 7, pushAllLeft(15) | [9, 15] | 7 |
    | next() | pop 9, pushAllLeft(null) | [15] | 9 |
    | next() | pop 15, pushAllLeft(20) | [20] | 15 |
    | next() | pop 20, pushAllLeft(null) | [] | 20 |
    | hasNext() | stack empty | [] | false |

    ---

    ## 7. Edge Cases

    ### Case 1: Single Node Tree
    ```
    5
    ```
    - Constructor: `pushAllLeft(5)` → stack: `[5]`
    - `hasNext()` → true
    - `next()` → 5, stack becomes empty
    - `hasNext()` → false
    - ✅ All approaches handle correctly

    ---

    ### Case 2: Left-Skewed Tree (worst-case space for stack)
    ```
        4
    /
    3
    /
    2
    /
    1
    ```
    - Stack after constructor: `[1, 2, 3, 4]` — height h = 4
    - Approach 2: O(h) = O(n) space in this case
    - ✅ Still correct; just worst-case space

    ---

    ### Case 3: Right-Skewed Tree
    ```
    1
    \
    2
    \
        3
    ```
    - Constructor: `pushAllLeft(1)` → stack: `[1]` (no left children)
    - Each `next()` call pushes one more element at most
    - ✅ All approaches handle correctly

    ---

    ### Case 4: Negative Values
    - e.g., nodes: `-5, -3, 0, 2, 7`
    - In-order still produces them in sorted order
    - ✅ No special handling needed — comparisons work with negatives

    ---

    ### Case 5: Null Root
    - If `root == null` → `pushAllLeft(null)` → while loop doesn't execute → stack is empty
    - `hasNext()` → false immediately
    - ✅ Approach 2 handles this gracefully
    - ✅ Approach 1: `inOrderTraversal(null)` returns immediately → list is empty

    ---

    ### Case 6: Large Tree (10^5 nodes)
    - Approach 1: O(n) space = 10^5 integers in list — fine for most systems
    - Approach 2: O(h) space — at most ~17 nodes for a balanced tree of 10^5 nodes (log₂(100000) ≈ 17) — ✅ Extremely efficient

    ---

    ## 8. Final Summary

    ### Comparison Table

    | Approach | Constructor | next() | Space | Complexity Difficulty | Recommended? |
    |---|---|---|---|---|---|
    | Brute Force (List) | O(n) | O(1) | O(n) | Easy | ✅ For beginners / quick solutions |
    | Stack-Based Lazy | O(h) | O(1) amortized | **O(h)** | Medium | ✅✅ **Best — Standard Interview Answer** |
    | Morris Traversal | O(h) | O(1) amortized | **O(1)** | Hard | For advanced discussions only |

    ### What to Remember:
    > **Pattern:** Use an **explicit stack to simulate recursive in-order traversal lazily**. The stack always holds the "pending" leftmost path. This pattern applies to any problem requiring on-demand in-order traversal.
    >
    > **Key Insight:** `pushAllLeft()` is the heart of the solution — it ensures the stack top is always the **next minimum**, and it only does work proportional to what's actually needed.

    ---

    ## 9. Companies & Frequency

    | Company | Frequency | Notes |
    |---|---|---|
    | **Facebook / Meta** | ⭐⭐⭐⭐⭐ Very High | Appeared repeatedly in coding rounds |
    | **Amazon** | ⭐⭐⭐⭐⭐ Very High | Common in SDE-1 and SDE-2 rounds |
    | **Microsoft** | ⭐⭐⭐⭐ High | Regular appearance in onsite rounds |
    | **Google** | ⭐⭐⭐⭐ High | Asked in both phone screens and onsites |
    | **Bloomberg** | ⭐⭐⭐⭐ High | Frequently asked |
    | **LinkedIn** | ⭐⭐⭐ Medium | Seen in technical screens |
    | **Apple** | ⭐⭐⭐ Medium | Appears in coding challenges |
    | **Uber** | ⭐⭐⭐ Medium | Common in engineering interviews |
    | **Adobe** | ⭐⭐⭐ Medium | Frequently part of DSA rounds |
    | **Salesforce** | ⭐⭐ Moderate | Seen in coding assessments |

    > 📊 **LeetCode Problem #173** — Rated **Medium** — Appeared in **500+ interview reports** across major companies. Consistently one of the **top 20 most-asked BST problems** in technical interviews. The follow-up about O(h) memory (stack-based) is almost always asked explicitly.
    */
    // @formatter:on
}
