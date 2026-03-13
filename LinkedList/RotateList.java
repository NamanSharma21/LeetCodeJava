package LinkedList;

import Datastructures.ListNode;

public class RotateList {
    public static void main(String[] args) {
        RotateList rotateList = new RotateList();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        System.out.println("RotateList : " + rotateList.rotateRight(head, 2));
    }

    /*
     * https://leetcode.com/problems/rotate-list/description/?envType=problem-list-
     * v2&envId=linked-list
     * 
     * Given the head of a linked list, rotate the list to the right by k places.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: head = [1,2,3,4,5], k = 2
     * Output: [4,5,1,2,3]
     * Example 2:
     * 
     * 
     * Input: head = [0,1,2], k = 4
     * Output: [2,0,1]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the list is in the range [0, 500].
     * -100 <= Node.val <= 100
     * 0 <= k <= 2 * 109
     */

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }
        // ListNode current = head;
        // ListNode first = head;
        // ListNode last = null;
        // int counter = 0;
        // while (counter < k) {
        // while (current != null) {
        // if (current.next != null) {
        // last = current;
        // }
        // current = current.next;
        // }

        // /*
        // * [1,2,3,4,5]
        // */

        // ListNode newHead = last.next;
        // last.next = null;
        // newHead.next = first;
        // counter++;
        // current = newHead;
        // first = newHead;
        // System.out.println("Head : " + newHead);
        // }

        // System.out.println("First : " + first);
        // System.out.println("Last : " + last);

        int length = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }

        int effectiveK = k % length;
        if (effectiveK == 0)
            return head;

        int stepsToNewTail = length - effectiveK - 1;
        ListNode newTail = head;
        for (int i = 0; i < stepsToNewTail; i++) {
            newTail = newTail.next;
        }

        ListNode newHead = newTail.next;
        newTail.next = null;
        tail.next = head;

        return newHead;
    }


    /*
        # Rotate List — Deep Dive (Java)

---

## 1. Problem Statement

### Plain English Restatement
Given the head of a singly linked list and an integer `k`, rotate the list **to the right** by `k` places. This means the last `k` nodes get moved to the front of the list, preserving their relative order.

### Input Format
- A singly linked list (given as its `head` node)
- An integer `k` (number of rotations)

### Output Format
- The `head` of the modified (rotated) linked list

### Constraints
- Number of nodes: `0 ≤ n ≤ 500`
- Node values: `-100 ≤ val ≤ 100`
- `0 ≤ k ≤ 2 * 10⁹`

### What Exactly Needs to Be Computed
Move the last `k` nodes to the front. If `k ≥ n`, use `k % n` (rotating by a full cycle brings you back to the original). Return the new head.

**Visual Example:**
```
Input:  1 → 2 → 3 → 4 → 5,  k = 2
Output: 4 → 5 → 1 → 2 → 3

The last 2 nodes [4, 5] move to the front.
```

---

## 2. Intuition

### Core Idea in Simple Terms
Rotating right by `k` means: *cut* the list at position `(n - k % n - 1)` from the head. The second part becomes the new front, and the first part gets appended to the end.

### How a Human Would Reason
1. If I rotate right once, the last node jumps to the front.
2. Doing this `k` times naively is slow — but I can avoid repetition.
3. If the list has `n` nodes, rotating `n` times returns the original list → so effective rotations = `k % n`.
4. I need to find the *new tail* (node at position `n - k%n - 1`) and the *new head* (node right after it).
5. Re-link: new tail → null, old tail → old head, return new head.

### What Makes It Tricky
- `k` can be **much larger than n** (up to 2×10⁹ with n=500), so `k % n` is essential.
- Off-by-one errors in finding the split point are extremely common.
- Edge cases: empty list, single node, `k` exactly divisible by `n`.

---

## 3. Approach Overview

| # | Approach | Key Idea | Time | Space | Use When |
|---|----------|----------|------|-------|----------|
| 1 | Brute Force | Rotate one step at a time, k times | O(n·k) | O(1) | Only tiny k, tiny n |
| 2 | Array Conversion | Copy to array, re-index, rebuild list | O(n) | O(n) | Quick to code in interview |
| 3 | **Optimal: Find & Re-link** | Measure length, find split point, re-link | O(n) | O(1) | **Always — this is the answer** |

The **optimal approach** wins because it does everything in two passes (or one pass + traversal), uses no extra memory, and handles all edge cases cleanly.

---

## 4. Detailed Solutions in Java

### Approach 1 — Brute Force (Rotate One Step at a Time)

#### Algorithm
1. Repeat `k` times:
   - Walk to the second-to-last node.
   - The last node becomes the new head.
   - Re-link accordingly.

```java
public class Solution {

    public ListNode rotateRight_BruteForce(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;

        for (int i = 0; i < k; i++) {
            head = rotateOnce(head);
        }
        return head;
    }

    // Moves the last node to the front
    private ListNode rotateOnce(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        // Walk to the last node
        while (curr.next != null) {
            prev = curr;
            curr = curr.next;
        }

        // curr is the last node, prev is second-to-last
        prev.next = null;       // detach last node
        curr.next = head;       // last node points to old head
        return curr;            // last node is new head
    }
}
```

> ⚠️ **Warning:** `k` can be up to 2×10⁹. This will TLE (Time Limit Exceeded) for any non-trivial input. Only useful conceptually.

---

### Approach 2 — Array Conversion

#### Algorithm
1. Collect all node values into an array.
2. Compute effective rotation: `k % n`.
3. Read from index `(n - k%n)` wrapping around to reconstruct node values.
4. Rebuild the linked list.

```java
public ListNode rotateRight_Array(ListNode head, int k) {
    if (head == null || head.next == null) return head;

    // Step 1: Collect values
    List<Integer> values = new ArrayList<>();
    ListNode curr = head;
    while (curr != null) {
        values.add(curr.val);
        curr = curr.next;
    }

    int n = values.size();
    int effectiveK = k % n;
    if (effectiveK == 0) return head;  // no change needed

    // Step 2: New order starts from index (n - effectiveK)
    int startIndex = n - effectiveK;

    // Step 3: Rebuild list in new order
    ListNode dummy = new ListNode(0);
    ListNode builder = dummy;
    for (int i = 0; i < n; i++) {
        builder.next = new ListNode(values.get((startIndex + i) % n));
        builder = builder.next;
    }

    return dummy.next;
}
```

> ✅ Clean and easy to understand. Uses O(n) extra space for the array/list.

---

### Approach 3 — Optimal: Find Length, Find Split, Re-link ⭐

#### Algorithm (Step-by-Step)
1. **Measure length** by walking the entire list. Also capture the **tail node**.
2. Compute `effectiveK = k % n`. If 0, return head unchanged.
3. Find the **new tail** at position `n - effectiveK - 1` (0-indexed from head).
4. The **new head** is `newTail.next`.
5. **Re-link:**
   - `oldTail.next = head` (connect end to old front)
   - `newTail.next = null` (cut the list at new tail)
6. Return `newHead`.

```java
public class Solution {

    public ListNode rotateRight(ListNode head, int k) {
        // Edge cases: empty, single node, or no effective rotation
        if (head == null || head.next == null || k == 0) return head;

        // Step 1: Find length and locate the tail
        int length = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }

        // Step 2: Calculate effective rotations (handle k >= length)
        int effectiveK = k % length;
        if (effectiveK == 0) return head; // full cycles — list unchanged

        // Step 3: Find the new tail (node just before the new head)
        // New tail is at position (length - effectiveK - 1) from head (0-indexed)
        int stepsToNewTail = length - effectiveK - 1;
        ListNode newTail = head;
        for (int i = 0; i < stepsToNewTail; i++) {
            newTail = newTail.next;
        }

        // Step 4: Identify the new head
        ListNode newHead = newTail.next;

        // Step 5: Re-link the list
        newTail.next = null;    // cut: new tail now ends the list
        tail.next = head;       // old tail connects to old head (making a loop temporarily)

        return newHead;
    }
}
```

---

## 5. Time & Space Complexity

### Approach 1 — Brute Force
| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n · k) | Each rotation walks ~n nodes; we do k rotations |
| **Space** | O(1) | No extra data structures |

**Example:** n=500, k=2×10⁹ → ~10¹² operations. **Not viable.**

---

### Approach 2 — Array Conversion
| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | One pass to build array + one pass to rebuild list |
| **Space** | O(n) | ArrayList stores all n values |

**Example:** n=500, k=2×10⁹ → ~1000 operations total. Fast.

---

### Approach 3 — Optimal Re-link ⭐
| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | One full pass for length/tail + one partial pass for new tail |
| **Space** | O(1) | Only a handful of pointer variables |

**Example:** n=500, k=2×10⁹ → effectiveK = 2×10⁹ % 500 = some small number. At most ~1000 node visits total.

---

## 6. Complete Worked Examples

### Approach 3 — Worked Example 1

**Input:** `1 → 2 → 3 → 4 → 5`, `k = 2`

**Step 1: Measure length and find tail**
```
Walk: 1 → 2 → 3 → 4 → 5
length = 5
tail = node(5)
```

**Step 2: Compute effectiveK**
```
effectiveK = 2 % 5 = 2
```

**Step 3: Find new tail**
```
stepsToNewTail = 5 - 2 - 1 = 2
Start at node(1), move 2 steps:
  Step 0 → node(1)
  Step 1 → node(2)
  Step 2 → node(3)
newTail = node(3)
```

**Step 4: Identify new head**
```
newHead = newTail.next = node(4)
```

**Step 5: Re-link**
```
newTail.next = null       →  1 → 2 → 3 → null
tail.next = head          →  5 → 1 (old tail connects to old head)

Full picture: 4 → 5 → 1 → 2 → 3 → null
```

**Output:** `4 → 5 → 1 → 2 → 3` ✅

---

### Approach 3 — Worked Example 2 (k > n)

**Input:** `0 → 1 → 2`, `k = 4`

**Step 1:** length = 3, tail = node(2)

**Step 2:** effectiveK = 4 % 3 = **1**

**Step 3:**
```
stepsToNewTail = 3 - 1 - 1 = 1
node(0) → node(1)
newTail = node(1)
```

**Step 4:** newHead = node(2)

**Step 5:**
```
1 → null  (cut)
node(2).next = node(0)  → 2 → 0 → 1 → null
```

**Output:** `2 → 0 → 1` ✅

---

### Approach 3 — Worked Example 3 (effectiveK = 0)

**Input:** `1 → 2 → 3`, `k = 3`

**Step 2:** effectiveK = 3 % 3 = **0** → return head immediately.

**Output:** `1 → 2 → 3` ✅ (unchanged)

---

## 7. Edge Cases

| Edge Case | What Happens | How Approach 3 Handles It |
|---|---|---|
| `head == null` | Empty list | Early return `null` |
| Single node (`n=1`) | No rotation possible | Early return `head` |
| `k == 0` | No rotation | Early return `head` |
| `k == n` | Full cycle, unchanged | `k % n == 0` → early return |
| `k > n` (e.g., k=10⁹, n=3) | Many full cycles + remainder | `effectiveK = k % n` handles this |
| `k = n - 1` | Only first node stays at back | `stepsToNewTail = 0` → newTail = head, newHead = head.next |
| All same values | Logic unchanged | Works correctly — values are irrelevant |
| Two nodes | Minimal split case | Works: stepsToNewTail = 0 or 1 |

### Potential Pitfall
Off-by-one in `stepsToNewTail`. The formula `length - effectiveK - 1` is correct because:
- The new head is the `(length - effectiveK)`-th node (1-indexed)
- The new tail is one before it: index `(length - effectiveK - 1)` (0-indexed)

---

## 8. Final Summary

| Approach | Time | Space | Recommended? |
|---|---|---|---|
| Brute Force | O(n·k) | O(1) | ❌ TLE for large k |
| Array Conversion | O(n) | O(n) | ⚠️ Acceptable, but wasteful |
| **Optimal Re-link** | **O(n)** | **O(1)** | ✅ **Always use this** |

### What to Remember
> **Pattern:** Linked list rotation = *find the cut point using modular arithmetic, then re-link in two pointer moves.* The key insight is that `k % n` collapses arbitrarily large `k` into a small, manageable offset — and then it's purely a pointer manipulation problem.

> **Technique:** Whenever you see "rotate" or "cycle" on a linked list, immediately think: *measure length → mod → find split → re-link tail to head.*
    
    */
}
