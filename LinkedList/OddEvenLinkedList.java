package LinkedList;

import Datastructures.ListNode;

public class OddEvenLinkedList {
    public static void main(String[] args) {
        OddEvenLinkedList oddEvenLinkedList = new OddEvenLinkedList();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        System.out.println("OddEvenLinkedList : " + oddEvenLinkedList.oddEvenList(head));
    }

    /*
     * https://leetcode.com/explore/interview/card/top-interview-questions-medium/
     * 107/linked-list/784/
     * 
     * Given the head of a singly linked list, group all the nodes with odd indices
     * together followed by the nodes with even indices, and return the reordered
     * list.
     * 
     * The first node is considered odd, and the second node is even, and so on.
     * 
     * Note that the relative order inside both the even and odd groups should
     * remain as it was in the input.
     * 
     * You must solve the problem in O(1) extra space complexity and O(n) time
     * complexity.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: head = [1,2,3,4,5]
     * Output: [1,3,5,2,4]
     * Example 2:
     * 
     * 
     * Input: head = [2,1,3,5,6,4,7]
     * Output: [2,3,6,7,1,5,4]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the linked list is in the range [0, 104].
     * -106 <= Node.val <= 106
     */

    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        ListNode oddTail = head;
        ListNode evenHead = head.next;
        ListNode evenTail = head.next;
        while (evenTail != null && evenTail.next != null) {
            oddTail.next = evenTail.next;
            oddTail = oddTail.next;

            evenTail.next = oddTail.next;
            evenTail = evenTail.next;
        }
        oddTail.next = evenHead;
        return head;
    }


    /*
    # Odd Even Linked List — Deep Dive

---

## 1. Problem Statement

### What the Problem Says
Given the head of a singly linked list, **reorder** it so that all nodes at **odd positions** come first, followed by all nodes at **even positions**.

> ⚠️ "Odd" and "Even" refer to **1-based index positions**, NOT node values.

### Input Format
- A singly linked list head node: `ListNode head`
- Node positions are 1-indexed: node at index 1 is "odd", index 2 is "even", etc.

### Output Format
- Return the **head** of the modified linked list (in-place reordering, no new nodes).

### Constraints
- Number of nodes: `0 ≤ n ≤ 10⁴`
- Node values: `-10⁶ ≤ val ≤ 10⁶`
- Must solve in **O(1) extra space** and **O(n) time**

### What Exactly to Compute
```
Input:  1 → 2 → 3 → 4 → 5
         ↑   ↑   ↑   ↑   ↑
pos:     1   2   3   4   5
        odd even odd even odd

Output: 1 → 3 → 5 → 2 → 4
        [odd nodes] [even nodes]
```

---

## 2. Intuition

### The Core Idea
Think of it like sorting a deck of cards where you pull out all cards in odd seats from a row and place them before all cards in even seats — **without shuffling the relative order within each group**.

### How a Human Would Reason
1. Walk through the list and mentally tag each node: odd, even, odd, even...
2. Collect odd-positioned nodes in one chain, even-positioned in another
3. At the end, attach the even chain to the tail of the odd chain

### What Makes This Tricky
- You cannot create new nodes — only **rewire existing `next` pointers**
- You must preserve **relative order** within each group
- You need to carefully handle pointer advancement to avoid losing references
- Edge cases: lists of length 0, 1, or 2 behave differently from longer lists

---

## 3. Approach Overview

| # | Approach | Key Idea | Space | Time | Use When |
|---|----------|----------|-------|------|----------|
| 1 | **Extra List (Brute Force)** | Collect values into two ArrayLists, rebuild list | O(n) | O(n) | Never in production; good for understanding |
| 2 | **Two-Pointer In-Place (Optimal)** | Maintain two sub-chains with pointers, weave them live | O(1) | O(n) | Always — this is the intended solution |

### ✅ Optimal: Two-Pointer In-Place
- No extra collections needed
- Single pass through the list
- Rewires `next` pointers directly

---

## 4. Detailed Solutions in Java

---

### Approach 1 — Brute Force (Extra Storage)

#### Algorithm Step-by-Step
1. Traverse the list, track position index starting at 1
2. If position is odd → add node's value to `oddVals` list
3. If position is even → add node's value to `evenVals` list
4. Traverse the original list a second time, overwriting each node's value in order: first all oddVals, then all evenVals

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public ListNode oddEvenList(ListNode head) {
        if (head == null) return null;

        List<Integer> oddVals = new ArrayList<>();
        List<Integer> evenVals = new ArrayList<>();

        ListNode current = head;
        int position = 1;

        // First pass: separate values by position parity
        while (current != null) {
            if (position % 2 == 1) {
                oddVals.add(current.val);
            } else {
                evenVals.add(current.val);
            }
            current = current.next;
            position++;
        }

        // Second pass: overwrite node values — odds first, then evens
        current = head;
        for (int val : oddVals) {
            current.val = val;
            current = current.next;
        }
        for (int val : evenVals) {
            current.val = val;
            current = current.next;
        }

        return head;
    }
}
```

> ⚠️ This approach modifies **values**, not structure. It works for this problem but violates the spirit of linked list manipulation. In interviews, avoid it unless explicitly told values can be changed.

---

### Approach 2 — Two-Pointer In-Place (Optimal)

#### Algorithm Step-by-Step

**Setup:**
- `oddTail` starts at node 1 (first odd node = head)
- `evenHead` starts at node 2 (first even node)
- `evenTail` starts at node 2 (pointer to advance through even nodes)

**Loop Invariant:**
At every iteration, `oddTail` is the last node in the odd chain, `evenTail` is the last node in the even chain.

**Each Iteration:**
1. `oddTail.next = evenTail.next` → skip over the even node, link to next odd node
2. Advance `oddTail` to that next odd node
3. `evenTail.next = oddTail.next` → skip over the odd node, link to next even node
4. Advance `evenTail` to that next even node

**Termination:**
- Loop ends when `evenTail == null` or `evenTail.next == null`

**Final Step:**
- `oddTail.next = evenHead` → attach even chain to end of odd chain

```java
class Solution {
    public ListNode oddEvenList(ListNode head) {
        // Handle trivial cases: 0, 1, or 2 nodes need no reordering
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }

        ListNode oddTail = head;           // Trails the end of the odd chain
        ListNode evenHead = head.next;     // Anchor of even chain (never moves)
        ListNode evenTail = head.next;     // Trails the end of the even chain

        // Continue while there are more nodes to process
        while (evenTail != null && evenTail.next != null) {
            // Step 1: Connect oddTail to the next odd node (skip evenTail)
            oddTail.next = evenTail.next;
            oddTail = oddTail.next;         // Advance oddTail

            // Step 2: Connect evenTail to the next even node (skip oddTail)
            evenTail.next = oddTail.next;
            evenTail = evenTail.next;       // Advance evenTail
        }

        // Step 3: Attach the entire even chain after the odd chain
        oddTail.next = evenHead;

        return head;
    }
}
```

---

## 5. Time & Space Complexity

### Approach 1 — Brute Force

| Metric | Value | Reasoning |
|--------|-------|-----------|
| Time | O(n) | Two passes over n nodes |
| Space | O(n) | Two ArrayLists storing up to n values total |

**Walk-through with n=5:**
- Pass 1: 5 iterations to separate values
- Pass 2: 5 iterations to rewrite values
- Total ≈ 10 operations → still O(n)

---

### Approach 2 — Two-Pointer In-Place

| Metric | Value | Reasoning |
|--------|-------|-----------|
| Time | O(n) | Single pass; each node is visited exactly once |
| Space | O(1) | Only 3 pointer variables regardless of list size |

**Walk-through with n=5:**
- Loop runs ⌊n/2⌋ = 2 times (pairs of nodes processed per iteration)
- Total pointer moves ≈ n → O(n)

**Walk-through with n=10,000:**
- Loop runs ≈ 5,000 times
- 3 pointer variables used throughout — space stays O(1)

---

## 6. Complete Worked Examples

---

### Example for Approach 1 (Brute Force)

**Input:** `1 → 2 → 3 → 4 → 5`

| Step | Node | Position | oddVals | evenVals |
|------|------|----------|---------|----------|
| 1 | 1 | 1 (odd) | [1] | [] |
| 2 | 2 | 2 (even) | [1] | [2] |
| 3 | 3 | 3 (odd) | [1,3] | [2] |
| 4 | 4 | 4 (even) | [1,3] | [2,4] |
| 5 | 5 | 5 (odd) | [1,3,5] | [2,4] |

**Rewrite Pass:**
- Overwrite with [1, 3, 5, 2, 4] left to right

**Output:** `1 → 3 → 5 → 2 → 4` ✅

---

### Example for Approach 2 (Optimal) — Main Example

**Input:** `1 → 2 → 3 → 4 → 5`

**Initial State:**
```
oddTail  = [1]
evenHead = [2]
evenTail = [2]

List: 1 → 2 → 3 → 4 → 5
```

---

**Iteration 1** (`evenTail=[2]`, `evenTail.next=[3]` → both non-null, enter loop):

```
Step 1: oddTail.next = evenTail.next
        [1].next = [3]       → 1 → 3 → 4 → 5  (2 is now dangling from even chain)
        oddTail = oddTail.next = [3]

Step 2: evenTail.next = oddTail.next
        [2].next = [4]       → 2 → 4 → 5
        evenTail = evenTail.next = [4]
```

State after Iteration 1:
```
Odd chain:  1 → 3 → 4 → 5   (oddTail = [3])
Even chain: 2 → 4 → 5       (evenTail = [4])
```

---

**Iteration 2** (`evenTail=[4]`, `evenTail.next=[5]` → both non-null, continue):

```
Step 1: oddTail.next = evenTail.next
        [3].next = [5]       → odd chain: 1 → 3 → 5
        oddTail = oddTail.next = [5]

Step 2: evenTail.next = oddTail.next
        [4].next = [5].next = null  → even chain: 2 → 4 → null
        evenTail = evenTail.next = null
```

State after Iteration 2:
```
Odd chain:  1 → 3 → 5 → null   (oddTail = [5])
Even chain: 2 → 4 → null       (evenTail = null)
```

---

**Loop Check:** `evenTail == null` → exit loop

**Final Step:**
```
oddTail.next = evenHead
[5].next = [2]   → 1 → 3 → 5 → 2 → 4 → null
```

**Output:** `1 → 3 → 5 → 2 → 4` ✅

---

### Example 2 — Even-Length List

**Input:** `1 → 2 → 3 → 4`

**Initial State:**
```
oddTail=[1], evenHead=[2], evenTail=[2]
```

**Iteration 1:**
```
oddTail.next = [3], oddTail = [3]
evenTail.next = [4], evenTail = [4]
```

**Iteration 2 check:** `evenTail=[4]`, `evenTail.next=null` → **exit loop**

**Final:** `[3].next = [2]` → `1 → 3 → 2 → 4` ✅

---

## 7. Edge Cases

| Edge Case | Input | Expected Output | How Optimal Handles It |
|-----------|-------|-----------------|------------------------|
| Empty list | `null` | `null` | Early return: `head == null` |
| Single node | `[1]` | `[1]` | Early return: `head.next == null` |
| Two nodes | `1 → 2` | `1 → 2` | Early return: `head.next.next == null` |
| All same values | `3 → 3 → 3` | `3 → 3 → 3` | Works correctly; values irrelevant |
| Two nodes only | `1 → 2` | `1 → 2` | Already in correct order; early exit handles it |
| Large list n=10⁴ | Long chain | Reordered | O(n) loop handles without stack overflow |
| Negative values | `-1 → -2 → -3` | `-1 → -3 → -2` | Values never compared; purely positional |

### Key Observations:
- The brute force approach handles all edge cases safely but uses O(n) space
- The optimal approach's early return for n ≤ 2 **prevents null pointer exceptions** when trying to access `head.next.next`
- Neither approach has overflow risk since we never do arithmetic on node values

---

## 8. Final Summary

### Comparison Table

| Criterion | Brute Force | Two-Pointer Optimal |
|-----------|-------------|---------------------|
| Time | O(n) | O(n) |
| Space | O(n) | ✅ O(1) |
| Passes | 2 | 1 |
| Modifies values? | Yes | No |
| Interview recommended? | ❌ No | ✅ Yes |
| Code complexity | Simple | Moderate |

### ✅ Recommended: Two-Pointer In-Place
Always use the two-pointer approach. It satisfies the O(1) space constraint stated in the problem, is a single pass, and demonstrates true mastery of linked list pointer manipulation.

### 🧠 What to Remember
> **Pattern:** Whenever you need to **partition a linked list into two groups** and **rejoin them**, maintain two separate chain tails and an anchor for the second group's head. This exact pattern also appears in problems like *Partition List* and *Split Linked List in Parts*.

> **Technique:** The key discipline is always saving `evenHead` before the loop — it's the **junction point** you'll need to reconnect at the end, and once the loop starts, you lose direct access to it.
    */
}
