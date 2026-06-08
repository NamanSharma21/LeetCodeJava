package LinkedList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Datastructures.ListNode;

public class MergeTwoSortedLists {
    public static void main(String[] args) {
        MergeTwoSortedLists mergeTwoSortedLists = new MergeTwoSortedLists();

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(4);

        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(3);
        head1.next.next = new ListNode(4);

        System.out.println("MergeTwoSortedLists : " + mergeTwoSortedLists.mergeTwoListsBruteForce(head, head1));
    }

    // @formatter:off
    /*
     * You are given the heads of two sorted linked lists list1 and list2.
     * 
     * Merge the two lists into one sorted list. The list should be made by splicing
     * together the nodes of the first two lists.
     * 
     * Return the head of the merged linked list.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: list1 = [1,2,4], list2 = [1,3,4]
     * Output: [1,1,2,3,4,4]
     * Example 2:
     * 
     * Input: list1 = [], list2 = []
     * Output: []
     * Example 3:
     * 
     * Input: list1 = [], list2 = [0]
     * Output: [0]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in both lists is in the range [0, 50].
     * -100 <= Node.val <= 100
     * Both list1 and list2 are sorted in non-decreasing order.
     */
    // @formatter:on

    public ListNode mergeTwoListsIterative(ListNode list1, ListNode list2) {
        if (list1 == null && list2 == null)
            return null;
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        current.next = (list1 != null) ? list1 : list2;
        return dummy.next;
    }

    public ListNode mergeTwoListsReccursive(ListNode list1, ListNode list2) {
        if (list1 == null)
            return list2;
        if (list2 == null)
            return list1;
        if (list1.val < list2.val) {
            list1.next = mergeTwoListsReccursive(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoListsReccursive(list1, list2.next);
            return list2;
        }
    }

    public ListNode mergeTwoListsBruteForce(ListNode list1, ListNode list2) {
        List<Integer> values = new ArrayList<>();
        while (list1 != null) {
            values.add(list1.val);
            list1 = list1.next;
        }

        while (list2 != null) {
            values.add(list2.val);
            list2 = list2.next;
        }

        Collections.sort(values);
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int i = 0; i < values.size(); i++) {
            current.next = new ListNode(values.get(i));
            current = current.next;
        }
        return dummy.next;
    }
}

// @formatter:off
/*
 * ============================================================
 *  Merge Two Sorted Lists — Deep Dive
 * ============================================================
 *
 * ============================================================
 *  1. PROBLEM STATEMENT
 * ============================================================
 *
 *  Restated in Plain English:
 *  --------------------------
 *  You are given the heads of two singly linked lists, both of which are
 *  sorted in non-decreasing order. Your task is to merge them into a single
 *  sorted linked list and return the head of the merged list.
 *
 *  The merged list must be formed by splicing together the nodes of the two
 *  input lists — not by creating new nodes with copied values.
 *
 *  Input Format:
 *  -------------
 *    - ListNode list1 — head of the first sorted linked list
 *    - ListNode list2 — head of the second sorted linked list
 *
 *  Output Format:
 *  --------------
 *    - Return the head of the single merged sorted linked list
 *
 *  Constraints (LeetCode #21):
 *  ---------------------------
 *    | Constraint                        | Value               |
 *    | Number of nodes in each list      | [0, 50]             |
 *    | Node values                       | -100 <= val <= 100  |
 *    | Both lists are sorted             | non-decreasing      |
 *
 *  What Needs to Be Computed:
 *  --------------------------
 *    - Do NOT allocate new ListNode objects for data — reuse existing nodes
 *    - Return the head of the resulting merged sorted list
 *
 *
 * ============================================================
 *  2. INTUITION
 * ============================================================
 *
 *  Core Idea:
 *  ----------
 *  Think of it like merging two sorted decks of cards face-up on a table.
 *  You look at the top card of each deck. You pick whichever is smaller,
 *  place it on the result pile, then look at the new top card of that deck.
 *  Repeat until one deck runs out. Then dump the remaining deck onto the
 *  result pile.
 *
 *  Step-by-Step Human Reasoning:
 *  ------------------------------
 *    1. Compare the front elements of both lists
 *    2. Whichever is smaller "belongs first" in the merged result
 *    3. Advance the pointer of whichever list you just picked from
 *    4. When one list is exhausted, the remaining list is already sorted
 *       — just attach it
 *
 *  What Makes This Tricky / Interesting:
 *  --------------------------------------
 *    - Pointer manipulation is easy to get wrong (losing references, cycles)
 *    - The dummy head trick is a classic pattern that elegantly avoids edge cases
 *    - The recursive version is elegant but builds a call stack — important
 *      for interviews
 *    - Edge cases: one or both lists being empty must be handled gracefully
 *
 *
 * ============================================================
 *  3. APPROACH OVERVIEW
 * ============================================================
 *
 *  | #  | Approach                      | Key Idea                                              | Use When                          | Optimal? |
 *  |----|-------------------------------|-------------------------------------------------------|-----------------------------------|----------|
 *  | 1  | Iterative with Dummy Head     | Use a dummy node to anchor the result list;           | Always — clean, O(1) space        | YES      |
 *  |    |                               | compare and link nodes one by one                     |                                   |          |
 *  | 2  | Recursive                     | Recursively choose the smaller head and return        | Interviews for elegance,          | CAUTION  |
 *  |    |                               | merged sublist                                        | small inputs                      | (stack)  |
 *  | 3  | Collect + Sort (Brute Force)  | Dump all values into an array, sort, rebuild          | Never in production               | NO       |
 *
 *  Recommended: Iterative with Dummy Head — O(m+n) time, O(1) space, no
 *  stack overflow risk.
 *
 *
 * ============================================================
 *  4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 *  Solution 1 — Iterative with Dummy Head (OPTIMAL)
 * ------------------------------------------------------------
 *
 *  Algorithm Step-by-Step:
 *  ------------------------
 *    1. Create a dummy node (a fake head with value 0) — acts as an anchor
 *    2. Maintain a current pointer starting at dummy
 *    3. While BOTH lists have remaining nodes:
 *         - Compare list1.val and list2.val
 *         - Attach the smaller node to current.next
 *         - Advance that list's pointer forward
 *         - Advance current forward
 *    4. After the loop, at least one list is exhausted — attach the
 *       non-null remainder directly
 *    5. Return dummy.next (the true head of the merged list)
 *
 *  Java Code:
 *  ----------
 *
 *    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
 *        // Dummy node acts as a placeholder head — avoids null-check edge cases
 *        ListNode dummy = new ListNode(0);
 *        ListNode current = dummy;
 *
 *        // Compare front nodes of both lists until one is exhausted
 *        while (list1 != null && list2 != null) {
 *            if (list1.val <= list2.val) {
 *                current.next = list1;   // Attach smaller node
 *                list1 = list1.next;     // Advance list1
 *            } else {
 *                current.next = list2;
 *                list2 = list2.next;     // Advance list2
 *            }
 *            current = current.next;     // Move result pointer forward
 *        }
 *
 *        // Attach remaining nodes (already sorted, no further comparison needed)
 *        current.next = (list1 != null) ? list1 : list2;
 *
 *        return dummy.next; // dummy.next is the actual head of merged list
 *    }
 *
 * ------------------------------------------------------------
 *  Solution 2 — Recursive (Elegant but Stack Risk)
 * ------------------------------------------------------------
 *
 *  Algorithm Step-by-Step:
 *  ------------------------
 *    1. Base case: If either list is null, return the other list
 *    2. Recursive case: Compare the heads of both lists
 *         - If list1.val <= list2.val: set list1.next = merge(list1.next, list2),
 *           return list1
 *         - Otherwise: set list2.next = merge(list1, list2.next), return list2
 *    3. Each call reduces the total number of remaining nodes by 1
 *
 *  Java Code:
 *  ----------
 *
 *    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
 *        // Base cases: if either list is empty, return the other
 *        if (list1 == null) return list2;
 *        if (list2 == null) return list1;
 *
 *        if (list1.val <= list2.val) {
 *            // list1's head is smaller — its next should point to merged remainder
 *            list1.next = mergeTwoLists(list1.next, list2);
 *            return list1;
 *        } else {
 *            // list2's head is smaller — its next should point to merged remainder
 *            list2.next = mergeTwoLists(list1, list2.next);
 *            return list2;
 *        }
 *    }
 *
 * ------------------------------------------------------------
 *  Solution 3 — Brute Force: Collect, Sort, Rebuild (NOT Recommended)
 * ------------------------------------------------------------
 *
 *  Algorithm Step-by-Step:
 *  ------------------------
 *    1. Traverse both lists and collect all values into an ArrayList
 *    2. Sort the ArrayList
 *    3. Build a new linked list from the sorted values
 *    4. Return its head
 *
 *  Java Code:
 *  ----------
 *
 *    import java.util.*;
 *
 *    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
 *        List<Integer> values = new ArrayList<>();
 *
 *        // Collect all values from both lists
 *        while (list1 != null) {
 *            values.add(list1.val);
 *            list1 = list1.next;
 *        }
 *        while (list2 != null) {
 *            values.add(list2.val);
 *            list2 = list2.next;
 *        }
 *
 *        // Sort the combined values
 *        Collections.sort(values);
 *
 *        // Rebuild a new linked list from sorted values
 *        ListNode dummy = new ListNode(0);
 *        ListNode current = dummy;
 *        for (int val : values) {
 *            current.next = new ListNode(val);  // Creates NEW nodes (not ideal)
 *            current = current.next;
 *        }
 *
 *        return dummy.next;
 *    }
 *
 *  NOTE: This creates new nodes instead of reusing existing ones — violates
 *  the problem's spirit, and is O(n log n) due to sorting.
 *
 *
 * ============================================================
 *  5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 *  Solution 1 — Iterative (Optimal):
 *  -----------------------------------
 *    | Metric | Complexity | Reasoning                                          |
 *    |--------|------------|----------------------------------------------------|
 *    | Time   | O(m + n)   | Each node from both lists is visited exactly once  |
 *    | Space  | O(1)       | Only 2 extra pointers (dummy, current)             |
 *
 *    Example: m=3, n=4 => ~7 iterations => O(m+n) confirmed
 *
 *  Solution 2 — Recursive:
 *  ------------------------
 *    | Metric | Complexity | Reasoning                                          |
 *    |--------|------------|----------------------------------------------------|
 *    | Time   | O(m + n)   | Each call processes one node                       |
 *    | Space  | O(m + n)   | Call stack depth = total nodes                     |
 *
 *    Risk: With m+n=100 (max per constraints) stack is fine. For 10,000+
 *    nodes this risks StackOverflowError.
 *
 *  Solution 3 — Brute Force:
 *  --------------------------
 *    | Metric | Complexity            | Reasoning                              |
 *    |--------|-----------------------|----------------------------------------|
 *    | Time   | O((m+n) log(m+n))     | Dominated by Collections.sort()        |
 *    | Space  | O(m + n)              | ArrayList + rebuilt list               |
 *
 *
 * ============================================================
 *  6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 *  Example 1 — Iterative Approach:
 *  ---------------------------------
 *    Input:
 *      list1: 1 -> 2 -> 4
 *      list2: 1 -> 3 -> 4
 *
 *    | Step | list1  | list2  | Attached | Action                              |
 *    |------|--------|--------|----------|-------------------------------------|
 *    | 1    | 1->2->4| 1->3->4| 1(L1)    | list1.val(1) <= list2.val(1)        |
 *    | 2    | 2->4   | 1->3->4| 1(L2)    | list1.val(2) > list2.val(1)         |
 *    | 3    | 2->4   | 3->4   | 2(L1)    | list1.val(2) <= list2.val(3)        |
 *    | 4    | 4      | 3->4   | 3(L2)    | list1.val(4) > list2.val(3)         |
 *    | 5    | 4      | 4      | 4(L1)    | list1.val(4) <= list2.val(4)        |
 *    | 6    | null   | 4      | 4(L2)    | list1 null -> attach list2 remainder|
 *
 *    Output: 1 -> 1 -> 2 -> 3 -> 4 -> 4
 *
 *  Example 2 — Recursive Approach:
 *  ---------------------------------
 *    Input:
 *      list1: 1 -> 3
 *      list2: 2
 *
 *    Call tree:
 *      merge(1->3, 2)
 *        1 <= 2  =>  list1.next = merge(3, 2)
 *                        3 > 2   =>  list2.next = merge(3, null)
 *                                        list2 is null => return 3
 *                                    return 2->3
 *                    list1.next = 2->3
 *        return 1->2->3
 *
 *    Output: 1 -> 2 -> 3
 *
 *  Example 3 — Edge Case: Both Lists Empty:
 *  -----------------------------------------
 *    Input: list1 = null, list2 = null
 *
 *    - Iterative: while loop never runs => current.next = null
 *                 => returns dummy.next = null
 *    - Recursive: if (list1 == null) return list2 => returns null
 *
 *    Output: null
 *
 *
 * ============================================================
 *  7. EDGE CASES
 * ============================================================
 *
 *  | Edge Case                          | What Happens                                    | Handled? |
 *  |------------------------------------|-------------------------------------------------|----------|
 *  | Both lists null                    | Returns null                                    | YES      |
 *  | One list null, other not           | Returns the non-null list as-is                 | YES      |
 *  | Lists of unequal length            | Remainder attached directly after while loop    | YES      |
 *  | All elements in list1 < list2      | list1 fully consumed, list2 appended            | YES      |
 *  | Duplicate values across lists      | <= ensures stable ordering                      | YES      |
 *  | Single element each                | One comparison, remainder attached              | YES      |
 *  | Negative values (-100 to -50)      | Integer comparison handles naturally            | YES      |
 *  | All same values ([1,1,1],[1,1])    | All taken from list1 side first due to <=       | YES      |
 *
 *
 * ============================================================
 *  8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 *  "What edge cases might this miss?"
 *  ------------------------------------
 *    - Null inputs: handled by current.next = (list1 != null) ? list1 : list2
 *    - Single element lists: while loop runs once, remainder attached
 *    - Lists of different lengths: tail-attach step handles perfectly
 *    - Recursive approach: safe within constraints (max 50+50=100 nodes),
 *      but would fail for massive lists due to stack depth
 *
 *  "Are there any type mismatches?"
 *  ---------------------------------
 *    - All node values are int — no casting needed
 *    - dummy is ListNode, current is ListNode — no type confusion
 *    - Return type ListNode matches method signature
 *
 *  "How can I verify this works right now?"
 *  -----------------------------------------
 *
 *    public static void main(String[] args) {
 *        // Build list1: 1 -> 2 -> 4
 *        ListNode list1 = new ListNode(1, new ListNode(2, new ListNode(4)));
 *        // Build list2: 1 -> 3 -> 4
 *        ListNode list2 = new ListNode(1, new ListNode(3, new ListNode(4)));
 *
 *        Solution sol = new Solution();
 *        ListNode result = sol.mergeTwoLists(list1, list2);
 *
 *        // Print: should output 1 1 2 3 4 4
 *        while (result != null) {
 *            System.out.print(result.val + " ");
 *            result = result.next;
 *        }
 *
 *        // Edge case: both null
 *        System.out.println(sol.mergeTwoLists(null, null)); // null
 *
 *        // Edge case: one null
 *        ListNode single = new ListNode(5);
 *        ListNode result2 = sol.mergeTwoLists(null, single);
 *        System.out.print(result2.val); // 5
 *    }
 *
 *  Risk Assessment Per Approach:
 *  ------------------------------
 *    | Approach      | Risk                                        | Verdict         |
 *    |---------------|---------------------------------------------|-----------------|
 *    | Iterative     | Nearly zero — O(1) space, no recursion risk | SAFE always     |
 *    | Recursive     | StackOverflowError for very large lists     | RISKY > 10K     |
 *    | Brute Force   | Creates new nodes, O(n log n), wasteful     | AVOID in intrvw |
 *
 *
 * ============================================================
 *  9. COMPANY INTERVIEW APPEARANCES
 * ============================================================
 *
 *  This is LeetCode #21 and is one of the most commonly asked linked list
 *  problems across the industry.
 *
 *  | Company          | Reported Frequency                     |
 *  |------------------|----------------------------------------|
 *  | Amazon           | Very High (50+ reports)                |
 *  | Microsoft        | Very High (40+ reports)                |
 *  | Google           | High (30+ reports)                     |
 *  | Facebook / Meta  | High (25+ reports)                     |
 *  | Apple            | Medium (15+ reports)                   |
 *  | Adobe            | Medium (12+ reports)                   |
 *  | Bloomberg        | Medium (10+ reports)                   |
 *  | Uber             | Medium (10+ reports)                   |
 *  | LinkedIn         | Low-Medium (8+ reports)                |
 *
 *  Total LeetCode submissions : 10M+
 *  Acceptance rate            : ~63%
 *  Difficulty                 : Easy (but tested in Medium/Hard rounds
 *                               as a prerequisite skill)
 *
 *
 * ============================================================
 *  10. FINAL SUMMARY
 * ============================================================
 *
 *  | Approach               | Time           | Space    | Simplicity | Recommended? |
 *  |------------------------|----------------|----------|------------|--------------|
 *  | Iterative (Dummy Head) | O(m+n)         | O(1)     | *****      | YES          |
 *  | Recursive              | O(m+n)         | O(m+n)   | ****       | Small inputs |
 *  | Brute Force            | O((m+n)log n)  | O(m+n)   | ***        | NO           |
 *
 *  What to Remember:
 *  -----------------
 *    1. The Dummy Head pattern is your best friend for linked list construction
 *       problems — it eliminates null-check complexity at the head and appears
 *       in Merge K Lists, Sort List, and many more.
 *
 *    2. When one list is exhausted, attach the rest — you never need to loop
 *       through a remaining sorted list node by node.
 *
 *    3. This problem is the building block for Merge Sort on Linked Lists
 *       and Merge K Sorted Lists — mastering it unlocks a whole family of
 *       problems.
 *
 * ============================================================
 */

// Your solution goes below this line:

class Solution {

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }

        current.next = (list1 != null) ? list1 : list2;

        return dummy.next;
    }
}
// @formatter:on
