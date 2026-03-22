package LinkedList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import Datastructures.ListNode;

public class ReorderList {
    public static void main(String[] args) {
        ReorderList reorderList = new ReorderList();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        System.out.println("ReorderList : " + reorderList.reorderListApproach1(head));
    }

    /*
     * 
     * https://leetcode.com/problems/reorder-list/description/?envType=problem-list-
     * v2&envId=linked-list
     * 
     * 
     * You are given the head of a singly linked-list. The list can be represented
     * as:
     * 
     * L0 → L1 → … → Ln - 1 → Ln
     * Reorder the list to be on the following form:
     * 
     * L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
     * You may not modify the values in the list's nodes. Only nodes themselves may
     * be changed.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: head = [1,2,3,4]
     * Output: [1,4,2,3]
     * Example 2:
     * 
     * 
     * Input: head = [1,2,3,4,5]
     * Output: [1,5,2,4,3]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the list is in the range [1, 5 * 104].
     * 1 <= Node.val <= 1000
     */

    public ListNode reorderListApproach1(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode curr = head;
        List<ListNode> nodeList = new ArrayList<>();
        while (curr != null) {
            nodeList.add(curr);
            curr = curr.next;
        }

        int left = 0;
        int right = nodeList.size() - 1;
        while (left < right) {
            nodeList.get(left).next = nodeList.get(right);
            left++;
            if (left == right)
                break;

            nodeList.get(right).next = nodeList.get(left);
            right--;
        }

        nodeList.get(left).next = null;
        return nodeList.get(0);
    }

    public ListNode reorderListApproach2(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode current = head;
        Deque<ListNode> deque = new ArrayDeque<>();

        while (current != null) {
            deque.addLast(current);
            current = current.next;
        }
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        boolean pickFirst = true;
        while (!deque.isEmpty()) {
            tail.next = pickFirst ? deque.pollFirst() : deque.pollLast();
            tail = tail.next;
            pickFirst = !pickFirst;
        }
        tail.next = null;
        return tail;
    }

    public void reorderListApproach3(ListNode head) {
        ListNode middle = findMiddle(head);
        ListNode secondHalf = reverseList(middle.next);
        middle.next = null;
        mergeList(head, secondHalf);
    }

    public ListNode findMiddle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public ListNode reverseList(ListNode head) {
        ListNode curr = head;
        ListNode prev = null;
        while (curr != null) {
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }
        return prev;
    }

    public ListNode mergeList(ListNode first, ListNode second) {
        while (second != null) {
            ListNode firstNext = first.next;
            ListNode secondNext = second.next;

            first.next = second;
            second.next = firstNext;

            first = firstNext;
            second = secondNext;
        }
        return null;
    }


    /**
     * 
     * # Reorder List — Deep Dive

---

## 1. Problem Statement

### In Plain English
You are given the head of a singly linked list:

```
L0 → L1 → L2 → ... → Ln-1 → Ln
```

You must **reorder it in-place** to:

```
L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → ...
```

You are **not** allowed to modify node values — only the `next` pointers.

### Input / Output
- **Input:** Head of a singly linked list (node with `int val` and `ListNode next`)
- **Output:** `void` — modify the list **in-place**
- **Constraints:**
  - Number of nodes: `1 ≤ n ≤ 50,000`
  - Node values: `1 ≤ val ≤ 1000`

### What Exactly Must Be Computed?
The nodes must be rearranged so that the first node is followed by the last, then the second, then the second-to-last, and so on — **interleaving from both ends toward the middle**.

---

## 2. Intuition

### The Core Idea

Imagine holding the list like a ribbon. You fold the ribbon in half, then weave the two halves together.

```
Original:   1 → 2 → 3 → 4 → 5

Fold:       1 → 2 → 3      (first half)
            5 → 4           (second half, reversed)

Weave:      1 → 5 → 2 → 4 → 3
```

### Human Reasoning Step-by-Step
1. Find the **middle** of the list.
2. **Reverse** the second half.
3. **Merge** the two halves by alternating nodes.

### What Makes This Tricky?
- It's not one operation — it's **three distinct linked list operations** composed together.
- All three sub-problems (find middle, reverse, merge) are classic interview problems individually.
- You must manage `next` pointers carefully or you'll create cycles or lose nodes.
- The in-place constraint rules out simply collecting nodes into an array and rebuilding.

---

## 3. Approach Overview

| # | Approach | Key Idea | Time | Space | Use When |
|---|----------|----------|------|-------|----------|
| 1 | **ArrayList + Rebuild** | Store nodes in list, use two pointers | O(n) | O(n) | Quick prototype, small n |
| 2 | **Deque** | Use deque to simulate front/back interleave | O(n) | O(n) | Readable, interview-friendly |
| 3 | **Find Middle + Reverse + Merge** ✅ | In-place pointer manipulation | O(n) | O(1) | **Optimal — use this** |

The **optimal approach** (Approach 3) is preferred because it runs in O(n) time with **O(1) extra space** — no auxiliary data structures needed.

---

## 4. Detailed Solutions in Java

### Approach 1 — ArrayList + Rebuild

#### Algorithm
1. Traverse the list and store all nodes in an `ArrayList`.
2. Use two pointers `left` (start) and `right` (end) to interleave nodes.
3. Reconnect `next` pointers according to the interleaved order.

```java
class Solution {
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        // Step 1: Collect all nodes into a list
        List<ListNode> nodes = new ArrayList<>();
        ListNode current = head;
        while (current != null) {
            nodes.add(current);
            current = current.next;
        }

        // Step 2: Use two pointers to interleave from both ends
        int left = 0, right = nodes.size() - 1;

        while (left < right) {
            // Connect left node to right node
            nodes.get(left).next = nodes.get(right);
            left++;

            // If they've crossed, stop
            if (left == right) break;

            // Connect right node to the new left node
            nodes.get(right).next = nodes.get(left);
            right--;
        }

        // Terminate the list at the new last node
        nodes.get(left).next = null;
    }
}
```

---

### Approach 2 — Deque

#### Algorithm
1. Load all nodes into a `Deque`.
2. Alternately poll from the front and back, linking them together.

```java
class Solution {
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        // Load all nodes into deque
        Deque<ListNode> deque = new ArrayDeque<>();
        ListNode current = head;
        while (current != null) {
            deque.addLast(current);
            current = current.next;
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        boolean pickFromFront = true;

        while (!deque.isEmpty()) {
            // Alternate picking from front and back
            ListNode node = pickFromFront ? deque.pollFirst() : deque.pollLast();
            tail.next = node;
            tail = tail.next;
            pickFromFront = !pickFromFront;
        }

        tail.next = null; // Terminate the list
    }
}
```

---

### Approach 3 — Find Middle + Reverse Second Half + Merge ✅ OPTIMAL

#### Algorithm

**Step 1: Find the middle using slow/fast pointers**
- `slow` moves 1 step, `fast` moves 2 steps.
- When `fast` reaches the end, `slow` is at the middle.

**Step 2: Reverse the second half**
- Starting from `slow.next`, reverse the chain.
- Cut the first half off at `slow.next = null`.

**Step 3: Merge two halves**
- Interleave nodes from the first and reversed second half.

```java
class Solution {
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        // ── Step 1: Find the middle ──────────────────────────────
        ListNode middle = findMiddle(head);

        // ── Step 2: Reverse the second half ─────────────────────
        ListNode secondHalf = reverseList(middle.next);
        middle.next = null; // Disconnect the two halves

        // ── Step 3: Merge the two halves ─────────────────────────
        mergeLists(head, secondHalf);
    }

    // Finds the middle node using slow/fast pointers
    private ListNode findMiddle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        // fast.next != null handles even-length lists
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow; // slow is now at the middle
    }

    // Reverses a linked list and returns the new head
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;

        while (current != null) {
            ListNode nextNode = current.next; // Save next
            current.next = prev;             // Reverse the pointer
            prev = current;                  // Advance prev
            current = nextNode;              // Advance current
        }
        return prev; // prev is the new head
    }

    // Merges two lists by interleaving their nodes
    private void mergeLists(ListNode first, ListNode second) {
        while (second != null) {
            ListNode firstNext = first.next;   // Save next of first half
            ListNode secondNext = second.next; // Save next of second half

            first.next = second;   // first → second
            second.next = firstNext; // second → original next of first

            // Advance both pointers
            first = firstNext;
            second = secondNext;
        }
    }
}
```

---

## 5. Time & Space Complexity

### Approach 1 — ArrayList

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | One pass to collect, one pass to rewire |
| **Space** | O(n) | ArrayList stores all n nodes |

**Example:** n = 50,000 → ~50,000 iterations + 50,000 node references stored.

---

### Approach 2 — Deque

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | One pass to load, one pass to drain deque |
| **Space** | O(n) | Deque stores all n nodes |

---

### Approach 3 — Optimal (In-Place)

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | findMiddle: O(n), reverseList: O(n/2), merge: O(n/2) → total O(n) |
| **Space** | O(1) | Only a fixed number of pointer variables used |

**Example:** n = 50,000 → ~75,000 pointer operations total. Zero heap allocations beyond a few variables.

---

## 6. Complete Worked Examples

### Example — Approach 3 (Optimal): `1 → 2 → 3 → 4 → 5`

#### Step 1: Find Middle

```
slow/fast start at 1

Iteration 1: slow=2, fast=3
Iteration 2: slow=3, fast=5  → fast.next is null, STOP

Middle = node(3)
```

#### Step 2: Reverse Second Half (`4 → 5`)

```
Start:   prev=null, current=4

Iter 1:  next=5, 4.next=null, prev=4, current=5
Iter 2:  next=null, 5.next=4, prev=5, current=null

Reversed second half: 5 → 4 → null
First half (after cut): 1 → 2 → 3 → null
```

#### Step 3: Merge

```
first=1, second=5

Iter 1:
  firstNext=2, secondNext=4
  1.next=5, 5.next=2
  List so far: 1→5→2→...
  first=2, second=4

Iter 2:
  firstNext=3, secondNext=null
  2.next=4, 4.next=3
  List so far: 1→5→2→4→3→...
  first=3, second=null

second==null → STOP
```

**Final list:** `1 → 5 → 2 → 4 → 3` ✅

---

### Example — Even Length: `1 → 2 → 3 → 4`

#### Step 1: Find Middle

```
slow/fast start at 1

Iteration 1: slow=2, fast=3
fast.next = 4, fast.next.next = null → STOP

Middle = node(2)
```

#### Step 2: Reverse Second Half (`3 → 4`)

```
Reversed: 4 → 3 → null
First half: 1 → 2 → null
```

#### Step 3: Merge

```
Iter 1: 1→4→2→...  (first=2, second=3)
Iter 2: 2→3→null  (first=null, second=null)
```

**Final list:** `1 → 4 → 2 → 3` ✅

---

### Example — Approach 1 (ArrayList): `1 → 2 → 3 → 4 → 5`

```
nodes = [node1, node2, node3, node4, node5]
left=0, right=4

Iter 1: node1.next=node5 → left=1
        node5.next=node2 → right=3

Iter 2: node2.next=node4 → left=2
        node4.next=node3 → right=2

left==right (both=2) → STOP
node3.next = null
```

**Final:** `1 → 5 → 2 → 4 → 3` ✅

---

## 7. Edge Cases

| Edge Case | Description | How Approach 3 Handles It |
|-----------|-------------|--------------------------|
| **Single node** | `1 → null` | Early return: `head.next == null` |
| **Two nodes** | `1 → 2` | Middle=node1, second half=node2, merge gives `1→2` ✅ |
| **Already ordered** | Shouldn't matter — still reorders correctly | Algorithm is order-agnostic |
| **All same values** | `1 → 1 → 1` | Values irrelevant; pointers still relinked correctly |
| **Even length** | Different split behavior | `findMiddle` handles this with `fast.next != null && fast.next.next != null` |
| **Odd length** | Middle node ends up in first half | First half is 1 longer; merge runs out of second-half nodes first — correct ✅ |
| **Large n = 50,000** | Approaches 1 & 2 use O(n) space | Approach 3 handles gracefully with O(1) space |

### ⚠️ Risk in Approach 1 & 2
For very large inputs (n = 50,000), storing all nodes in an ArrayList or Deque consumes significant heap memory. In a memory-constrained environment, Approach 3 is far safer.

---

## 8. Final Summary

| Approach | Time | Space | Recommended? |
|----------|------|-------|--------------|
| ArrayList + Rebuild | O(n) | O(n) | ✅ Quick prototype |
| Deque | O(n) | O(n) | ✅ Readable/interview |
| **Find Middle + Reverse + Merge** | **O(n)** | **O(1)** | ✅✅ **Best — use this** |

### What to Remember
> This problem is a **composition of three classic linked list patterns**: find-middle (slow/fast pointers), reverse a list (iterative pointer reversal), and merge two lists (pointer interleaving). Recognizing that you can **decompose a complex problem into sub-problems you already know** is the key insight here.

**Mental model to tattoo on your brain:**
```
Reorder List = findMiddle() + reverseList() + mergeLists()
```
     * 
     * 
     */
}
