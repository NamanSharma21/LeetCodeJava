package LinkedList;

import Datastructures.ListNode;

public class ReverseLinkedList {
    public static void main(String[] args) {
        ReverseLinkedList reverseLinkedList = new ReverseLinkedList();

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);

        System.out.println("ReverseLinkedList : " + reverseLinkedList.reverseList(head));
        System.out.println("ReverseLinkedList : " + reverseLinkedList.reverseListReccursion(head1));
    }

    // @formatter:off
    /*
     * Given the head of a singly linked list, reverse the list, and return the
     * reversed list.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: head = [1,2,3,4,5]
     * Output: [5,4,3,2,1]
     * Example 2:
     * 
     * 
     * Input: head = [1,2]
     * Output: [2,1]
     * Example 3:
     * 
     * Input: head = []
     * Output: []
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the list is the range [0, 5000].
     * -5000 <= Node.val <= 5000
     * 
     * 
     * Follow up: A linked list can be reversed either iteratively or recursively.
     * Could you implement both?
     */
    // @formatter:on

    public ListNode reverseList(ListNode head) {
        if (head == null)
            return null;
        ListNode current = head;
        ListNode prev = null;
        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }

    public ListNode reverseListReccursion(ListNode head) {
        if (head == null || head.next == null)
            return head;
        System.out.println("" + head.val);
        ListNode newHead = reverseListReccursion(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

}

// @formatter:off
/*
 * =============================================================================
 * REVERSE A LINKED LIST — DEEP DIVE
 * =============================================================================
 *
 * =============================================================================
 * 1. PROBLEM STATEMENT
 * =============================================================================
 *
 * In Plain English:
 * -----------------
 * Given the head node of a singly linked list, reverse the direction of all
 * the pointers so that the last node becomes the first, and the first becomes
 * the last. Return the new head (which was the old tail).
 *
 * Input Format:
 * -------------
 * - A reference to the head node of a singly linked list.
 * - Each node contains:
 *     int val      — the data stored
 *     ListNode next — pointer to the next node (or null if it's the tail)
 *
 * Output Format:
 * --------------
 * - The head reference of the REVERSED linked list.
 *
 * Constraints (LeetCode #206):
 * ----------------------------
 *   Number of nodes  : [0, 5000]
 *   Node value range : [-5000, 5000]
 *   Input can be empty: Yes (head == null)
 *
 * What Needs to Be Computed:
 * --------------------------
 * Flip every .next pointer so traversal goes in the opposite direction.
 * The original head's .next must become null (it's now the tail).
 *
 *   Input:  1 -> 2 -> 3 -> 4 -> 5 -> null
 *   Output: 5 -> 4 -> 3 -> 2 -> 1 -> null
 *
 * =============================================================================
 * 2. INTUITION
 * =============================================================================
 *
 * How a Human Thinks About It:
 * ----------------------------
 * Imagine a chain of people holding hands in a line. Each person holds the
 * hand of the person in front of them. To reverse, each person must let go
 * of the person in front and instead grab the hand of the person behind.
 *
 * The challenge: once you let go of the person in front (next), you've lost
 * your reference to the rest of the chain. So you MUST save that reference
 * before breaking the link.
 *
 * The Core Insight:
 * -----------------
 * At every node, you need exactly THREE things:
 *   1. Who is BEHIND me?       (already reversed portion)
 *   2. Who am I?
 *   3. Who is IN FRONT of me?  (not yet reversed — save before cutting!)
 *
 * What Makes This Tricky:
 * -----------------------
 * - You cannot reverse in one sweep without careful pointer management —
 *   one wrong move and you lose the rest of the list.
 * - The recursive version reverses your mental model (processes from tail
 *   to head), which is conceptually elegant but harder to see at first.
 * - Off-by-one errors are common — forgetting to set the original head's
 *   .next to null leaves a cycle.
 *
 * =============================================================================
 * 3. APPROACH OVERVIEW
 * =============================================================================
 *
 *  #  | Approach                    | Time | Space | Use When
 * ----|-----------------------------+------+-------+------------------------
 *  1  | Extra Storage (Stack/List)  | O(n) | O(n)  | Learning / very small n
 *  2  | Iterative (3-pointer)  [*]  | O(n) | O(1)  | Optimal — Interviews
 *  3  | Recursive                   | O(n) | O(n)  | Elegant, recursion demos
 *
 * [*] RECOMMENDED: Iterative (3-pointer)
 *     O(1) space, single pass, no call stack overhead,
 *     easiest to explain under interview pressure.
 *
 * =============================================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * =============================================================================
 *
 * ----------------------------------------------------------------------------
 * SOLUTION 1 — Extra Storage (Stack / ArrayList)
 * ----------------------------------------------------------------------------
 *
 * Algorithm:
 *   1. Traverse the list, push each node's VALUE into a Stack
 *      (LIFO gives reverse order).
 *   2. Traverse the original list again from head, popping values from
 *      the stack and overwriting each node's value.
 *   3. Return the same head (structure unchanged, values reversed).
 *
 * NOTE: This reverses values, not pointers. Technically valid for this
 * problem but wouldn't work if you needed to preserve node identity.
 *
 *   class Solution {
 *       public ListNode reverseList(ListNode head) {
 *           if (head == null) return null;
 *
 *           Stack<Integer> stack = new Stack<>();
 *           ListNode current = head;
 *
 *           // Phase 1: push all values onto the stack
 *           while (current != null) {
 *               stack.push(current.val);
 *               current = current.next;
 *           }
 *
 *           // Phase 2: pop values back (reversed order) into original nodes
 *           current = head;
 *           while (current != null) {
 *               current.val = stack.pop();
 *               current = current.next;
 *           }
 *
 *           return head; // same head node, values are now reversed
 *       }
 *   }
 *
 * ----------------------------------------------------------------------------
 * SOLUTION 2 — Iterative In-Place (OPTIMAL)
 * ----------------------------------------------------------------------------
 *
 * Algorithm Step-by-Step:
 *   1. Initialize prev = null, curr = head.
 *   2. At each step:
 *        - Save nextTemp = curr.next  (preserve the rest of the list)
 *        - Point curr.next = prev     (reverse the link)
 *        - Move prev = curr           (advance the "reversed" frontier)
 *        - Move curr = nextTemp       (advance to next node to process)
 *   3. When curr == null, prev is the new head — return it.
 *
 *   class Solution {
 *       public ListNode reverseList(ListNode head) {
 *           ListNode prev = null;   // the growing reversed portion
 *           ListNode curr = head;   // the node being processed
 *
 *           while (curr != null) {
 *               ListNode nextTemp = curr.next; // save next before we overwrite it
 *               curr.next = prev;              // reverse the pointer
 *               prev = curr;                   // move prev forward
 *               curr = nextTemp;               // move curr forward
 *           }
 *
 *           // prev is now pointing at the last node (new head)
 *           return prev;
 *       }
 *   }
 *
 * ----------------------------------------------------------------------------
 * SOLUTION 3 — Recursive
 * ----------------------------------------------------------------------------
 *
 * Algorithm Step-by-Step:
 *   1. Base case: if head == null or head.next == null, return head.
 *   2. Recursively reverse the rest of the list from head.next onward.
 *      The call returns the new head of the reversed suffix.
 *   3. After the recursive call, head.next still points to the node just
 *      after head — call it tail. Make tail.next = head.
 *   4. Set head.next = null to prevent a cycle.
 *   5. Return newHead (unchanged throughout — it's the original tail).
 *
 *   class Solution {
 *       public ListNode reverseList(ListNode head) {
 *           // Base case: empty list or single node
 *           if (head == null || head.next == null) {
 *               return head;
 *           }
 *
 *           // Recursively reverse the sublist starting at head.next
 *           ListNode newHead = reverseList(head.next);
 *
 *           // head.next is now the TAIL of the reversed sublist —
 *           // make it point back to current head
 *           head.next.next = head;
 *
 *           // current head becomes the new tail — cut its forward link
 *           head.next = null;
 *
 *           return newHead; // new head bubbles up unchanged
 *       }
 *   }
 *
 * =============================================================================
 * 5. TIME & SPACE COMPLEXITY (WITH REASONING)
 * =============================================================================
 *
 * Solution 1 — Stack:
 *   Time  : O(n)  — Two full traversals of n nodes
 *   Space : O(n)  — Stack stores all n values
 *   Example: n=1000 → ~2000 operations, 1000 stack entries
 *
 * Solution 2 — Iterative (OPTIMAL):
 *   Time  : O(n)  — Single pass; each node visited exactly once
 *   Space : O(1)  — Only 3 pointer variables regardless of list size
 *   Example: n=5000 → exactly 5000 iterations, 3 variables total
 *
 * Solution 3 — Recursive:
 *   Time  : O(n)  — One recursive call per node
 *   Space : O(n)  — Call stack depth equals list length;
 *                   risk of StackOverflow for very large n
 *   Example: n=5000 → 5000 stack frames on the call stack simultaneously
 *
 * =============================================================================
 * 6. COMPLETE WORKED EXAMPLES
 * =============================================================================
 *
 * Example for Solution 2 (Iterative) — Input: 1 -> 2 -> 3 -> 4 -> 5
 * --------------------------------------------------------------------
 *
 *  Step | prev | curr | nextTemp | Action
 * ------|------|------|----------+---------------------------------------
 *  Start|  null|   1  |    —     | —
 *    1  |  null|   1  |    2     | 1.next = null,  prev->1,  curr->2
 *    2  |    1 |   2  |    3     | 2.next = 1,     prev->2,  curr->3
 *    3  |    2 |   3  |    4     | 3.next = 2,     prev->3,  curr->4
 *    4  |    3 |   4  |    5     | 4.next = 3,     prev->4,  curr->5
 *    5  |    4 |   5  |   null   | 5.next = 4,     prev->5,  curr->null
 *  End  |    5 |  null|    —     | Return prev = node 5  [CORRECT]
 *
 *   Result: 5 -> 4 -> 3 -> 2 -> 1 -> null
 *
 * Example for Solution 3 (Recursive) — Input: 1 -> 2 -> 3
 * ---------------------------------------------------------
 *
 *   reverseList(1)
 *     reverseList(2)
 *       reverseList(3)
 *         3.next == null  ->  BASE CASE  ->  return node 3
 *       newHead = 3
 *       2.next.next = 2   ->  3.next = 2   (so: 3 -> 2)
 *       2.next = null     ->  list so far: 3 -> 2 -> null
 *       return 3
 *     newHead = 3
 *     1.next.next = 1     ->  2.next = 1   (so: 3 -> 2 -> 1)
 *     1.next = null       ->  list: 3 -> 2 -> 1 -> null
 *     return 3
 *
 *   Result: 3 -> 2 -> 1 -> null
 *
 * Example for Solution 1 (Stack) — Input: 1 -> 2 -> 3
 * -----------------------------------------------------
 *
 *   Phase 1 - Push:   Stack = [1, 2, 3]  (top = 3)
 *   Phase 2 - Pop:
 *     node(1).val = pop() = 3  ->  node values: 3, 2, 3
 *     node(2).val = pop() = 2  ->  node values: 3, 2, 3
 *     node(3).val = pop() = 1  ->  node values: 3, 2, 1
 *
 *   Result: 3 -> 2 -> 1 -> null
 *
 * =============================================================================
 * 7. EDGE CASES
 * =============================================================================
 *
 *  Edge Case               | What Happens       | How Each Solution Handles It
 * -------------------------+--------------------+------------------------------
 *  head == null            | No nodes           | All three: return null
 *  Single node (1->null)   | Already reversed   | All work correctly (see note)
 *  Two nodes (1->2)        | Classic swap       | All work correctly
 *  All same values (3->3)  | Works fine         | No special logic needed
 *  Very large list (n=5000)| Recursive may OOM  | Iterative is safe
 *  Negative values         | No effect          | All solutions handle it
 *
 * NOTE — Single Node Check for Iterative:
 *   Input: 1 -> null.  prev = null, curr = node(1).
 *   Loop: nextTemp = null, curr.next = null (already null),
 *         prev = node(1), curr = null.
 *   Returns prev = node(1).  CORRECT.
 *
 * =============================================================================
 * 8. SELF-CORRECTION & TESTING
 * =============================================================================
 *
 * "What edge cases might this miss?"
 * ------------------------------------
 * - Iterative  : Handles all cases (null, single, large). Zero StackOverflow risk.
 * - Recursive  : For n=5000, Java's default stack should handle it but is
 *                risky in production for much larger inputs.
 * - Stack approach: Modifies values, not node identity — if the problem
 *                required preserving object references, this would fail.
 *
 * "Are there any type mismatches?"
 * ---------------------------------
 * - All node values are int, Stack stores Integer (autoboxed) — no issues.
 * - ListNode is the standard LeetCode definition — ensure it's in scope.
 *
 * "How can I verify this works right now?"
 * -----------------------------------------
 *
 *   public class Test {
 *       public static void main(String[] args) {
 *           // Build: 1 -> 2 -> 3 -> 4 -> 5
 *           ListNode head = new ListNode(1);
 *           head.next = new ListNode(2);
 *           head.next.next = new ListNode(3);
 *           head.next.next.next = new ListNode(4);
 *           head.next.next.next.next = new ListNode(5);
 *
 *           Solution sol = new Solution();
 *           ListNode result = sol.reverseList(head);
 *
 *           // Print: should output 5 4 3 2 1
 *           while (result != null) {
 *               System.out.print(result.val + " ");
 *               result = result.next;
 *           }
 *
 *           // Test null input
 *           System.out.println(sol.reverseList(null)); // null
 *
 *           // Test single node
 *           ListNode single = new ListNode(42);
 *           ListNode r2 = sol.reverseList(single);
 *           System.out.println(r2.val); // 42
 *       }
 *   }
 *
 * =============================================================================
 * 9. COMPANIES & INTERVIEW FREQUENCY
 * =============================================================================
 *
 *  Company            | Frequency  | Notes
 * --------------------+------------+-------------------------------------------
 *  Amazon             | *****      | Top 10 most asked question
 *  Microsoft          | *****      | Asked in both SDE1 and SDE2 rounds
 *  Google             | ****       | Often as a warm-up or follow-up
 *  Meta (Facebook)    | *****      | Core linked list question
 *  Apple              | ****       | Common in early rounds
 *  Goldman Sachs      | ****       | Frequently appears in SDE rounds
 *  Adobe              | ****       | Standard DS round question
 *  Uber               | ***        | Often paired with cycle detection
 *  Bloomberg          | ****       | Classic warm-up question
 *  Oracle             | ***        | Appears in technical screens
 *
 * - LeetCode #206 — Solved over 4 million times
 * - Appears in 60%+ of FAANG linked list interview sets
 * - Often followed up with:
 *     "Now reverse in groups of k"       (LeetCode #25)
 *     "Reverse between positions i and j" (LeetCode #92)
 *
 * =============================================================================
 * 10. FINAL SUMMARY
 * =============================================================================
 *
 *  Approach               | Time | Space | Recommended?
 * ------------------------+------+-------+---------------------------
 *  Stack / ArrayList      | O(n) | O(n)  | No  — only for learning
 *  Iterative (3-pointer)  | O(n) | O(1)  | YES — always preferred
 *  Recursive              | O(n) | O(n)  | OK to know, not optimal
 *
 * What to Remember:
 * -----------------
 * The iterative 3-pointer pattern (prev, curr, nextTemp) is the backbone
 * of nearly all in-place linked list manipulation problems — mastering it
 * here unlocks Reverse in K-Groups, Palindrome Linked List, Reorder List,
 * and many more.
 *
 * The golden rule:
 *   ALWAYS save curr.next BEFORE you overwrite it — losing that reference
 *   means losing the rest of your list forever.
 *
 * =============================================================================
 */
// @formatter:on
