package LinkedList;

import Datastructures.ListNode;

public class ReverseLinkedListII {
    public static void main(String[] args) {
        ReverseLinkedListII reverseLinkedListII = new ReverseLinkedListII();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        System.out.println("ReverseLinkedListII : " + reverseLinkedListII.reverseBetween(head, 2, 4));
    }

    /*
     * https://leetcode.com/problems/reverse-linked-list-ii/description/?envType=
     * problem-list-v2&envId=linked-list
     * 
     * Given the head of a singly linked list and two integers left and right where
     * left <= right, reverse the nodes of the list from position left to position
     * right, and return the reversed list.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: head = [1,2,3,4,5], left = 2, right = 4
     * Output: [1,4,3,2,5]
     * Example 2:
     * 
     * Input: head = [5], left = 1, right = 1
     * Output: [5]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the list is n.
     * 1 <= n <= 500
     * -500 <= Node.val <= 500
     * 1 <= left <= right <= n
     * 
     * 
     * Follow up: Could you do it in one pass?
     */

    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode beforeLeft = dummy;
        for (int i = 1; i < left; i++) {
            beforeLeft = beforeLeft.next;
        }

        ListNode curr = beforeLeft.next;

        for (int i = 0; i < (right - left); i++) {
            ListNode nodeToMove = curr.next;
            curr.next = nodeToMove.next;
            nodeToMove.next = beforeLeft.next;
            beforeLeft.next = nodeToMove;
        }
        return dummy.next;
    }


    /*
    # Reverse Linked List II — Deep Dive

---

## 1. Problem Statement

### In Plain English
You are given a singly linked list and two integers `left` and `right`. Your task is to **reverse only the portion of the linked list** from position `left` to position `right` (1-indexed), and return the modified list.

### Input Format
- Head of a singly linked list: `ListNode head`
- Two integers: `left` and `right` (1-indexed positions)

### Output Format
- Head of the modified linked list (after partial reversal)

### Constraints
- Number of nodes `n`: `1 ≤ n ≤ 500`
- Node values: `-500 ≤ val ≤ 500`
- `1 ≤ left ≤ right ≤ n`

### What Exactly Needs to Be Returned?
Return the **head** of the linked list after reversing nodes from position `left` to `right`. The nodes outside this range remain in their original order and connections.

### Visual Example
```
Input:  1 → 2 → 3 → 4 → 5,  left = 2, right = 4
                 ↑           ↑
              reverse this segment

Output: 1 → 4 → 3 → 2 → 5
```

---

## 2. Intuition

### Core Idea
Think of it like physically pulling out a segment of beads from a necklace, reversing that segment, and reconnecting it.

### How a Human Reasons Through This
1. **Walk to the start** of the reversal zone (position `left`)
2. **Remember the node just before** it — this node's `next` pointer will need to be updated
3. **Reverse the sublist** between `left` and `right`
4. **Reconnect** the reversed segment back to the rest of the list

### What Makes This Tricky?
| Challenge | Why It's Hard |
|---|---|
| Pointer management | You must track 4–5 pointers simultaneously without losing references |
| Edge case: `left = 1` | No "node before left" exists — need a dummy head trick |
| Off-by-one errors | 1-indexed positions are easy to mis-count |
| Reconnecting ends | After reversal, both endpoints must be sewn back correctly |

---

## 3. Approach Overview

| # | Approach | Key Idea | Best For | Optimal? |
|---|---|---|---|---|
| 1 | **Collect & Rebuild** | Extract values into array, reverse slice, rebuild | Beginners, clarity | ❌ Extra space |
| 2 | **Standard Two-Pass** | First find boundary nodes, then reverse sublist | Clean interviews | ✅ Near-optimal |
| 3 | **One-Pass (In-Place)** | Reverse while walking — no second traversal | Optimal, follow-up asks | ✅✅ Best |

**Optimal approach:** The **one-pass in-place** method using a dummy node and iterative pointer redirection — O(n) time, O(1) space, single traversal.

---

## 4. Detailed Solutions in Java

---

### Approach 1 — Collect Values, Reverse, Rebuild

#### Algorithm Steps
1. Walk the list and collect all node values into an `ArrayList`
2. Reverse the subarray from index `left-1` to `right-1`
3. Walk the list again, overwriting each node's value from the array

```java
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null) return null;

        // Step 1: Collect all values
        List<Integer> values = new ArrayList<>();
        ListNode current = head;
        while (current != null) {
            values.add(current.val);
            current = current.next;
        }

        // Step 2: Reverse the target subarray in-place (two-pointer swap)
        int lo = left - 1;  // convert to 0-indexed
        int hi = right - 1;
        while (lo < hi) {
            int temp = values.get(lo);
            values.set(lo, values.get(hi));
            values.set(hi, temp);
            lo++;
            hi--;
        }

        // Step 3: Write values back into the original list nodes
        current = head;
        for (int val : values) {
            current.val = val;
            current = current.next;
        }

        return head;
    }
}
```

---

### Approach 2 — Two-Pass: Find Boundaries, Then Reverse Sublist

#### Algorithm Steps
1. Create a **dummy node** pointing to head (handles `left = 1` cleanly)
2. Walk to the node **just before** position `left` — call it `beforeLeft`
3. `beforeLeft.next` is the **start** of the reversal zone
4. Reverse the sublist from `left` to `right` using standard iterative reversal
5. Reconnect: `beforeLeft.next → newHead`, `oldStart.next → nodeAfterRight`

```java
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // Step 1: Walk to the node just before position 'left'
        ListNode beforeLeft = dummy;
        for (int i = 1; i < left; i++) {
            beforeLeft = beforeLeft.next;
        }

        // Step 2: 'sublistStart' is the first node to be reversed
        ListNode sublistStart = beforeLeft.next;

        // Step 3: Reverse from 'left' to 'right'
        ListNode prev = null;
        ListNode curr = sublistStart;
        for (int i = 0; i <= right - left; i++) {
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }
        // After loop: 'prev' = new head of reversed segment
        //             'curr' = first node after reversed segment

        // Step 4: Reconnect both ends
        beforeLeft.next = prev;         // connect left boundary to new head
        sublistStart.next = curr;       // connect old start (now tail) to remainder

        return dummy.next;
    }
}
```

---

### Approach 3 — One-Pass In-Place (Optimal) ✅

#### Core Insight
Instead of reversing and then reconnecting, we **insert each node one at a time** at the front of the growing reversed segment during a single walk. This is called the **"head insertion"** or **"front-insertion"** technique.

#### Algorithm Steps (with diagram)

```
Start:  dummy → 1 → 2 → 3 → 4 → 5,  left=2, right=4

Setup:
  beforeLeft → node 1  (the anchor; never moves)
  curr       → node 2  (the node being moved each iteration)

Iteration 1: Move node 3 to front of reversed segment
  dummy → 1 → [3] → 2 → 4 → 5

Iteration 2: Move node 4 to front
  dummy → 1 → [4] → 3 → 2 → 5

Done! (right - left = 2 iterations total)
```

Each iteration takes `curr.next` and inserts it right after `beforeLeft`.

```java
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // Step 1: Move 'beforeLeft' to the node just before position 'left'
        ListNode beforeLeft = dummy;
        for (int i = 1; i < left; i++) {
            beforeLeft = beforeLeft.next;
        }

        // 'curr' is the node currently at position 'left'
        // It will eventually become the TAIL of the reversed segment
        ListNode curr = beforeLeft.next;

        // Step 2: Perform (right - left) front-insertions
        for (int i = 0; i < right - left; i++) {
            ListNode nodeToMove = curr.next;      // grab next node to move

            curr.next = nodeToMove.next;          // unlink nodeToMove from chain
            nodeToMove.next = beforeLeft.next;    // point nodeToMove to current front
            beforeLeft.next = nodeToMove;         // insert nodeToMove at front
        }

        return dummy.next;
    }
}
```

---

## 5. Time & Space Complexity

| Approach | Time | Space | Notes |
|---|---|---|---|
| Collect & Rebuild | O(n) | O(n) | Stores all values in a list |
| Two-Pass | O(n) | O(1) | Two linear walks, constant pointers |
| One-Pass (Optimal) | O(n) | O(1) | Single walk, constant pointers |

### Derivations

**Approach 1:**
- Walk 1 (collect): visits all `n` nodes → O(n)
- Reverse subarray: at most `n/2` swaps → O(n)
- Walk 2 (write back): visits all `n` nodes → O(n)
- Total: **O(n)** | ArrayList of `n` values → **O(n) space**

**Approach 2:**
- Walk to `left`: at most `n` steps → O(n)
- Reversal loop: `right - left + 1` steps → O(n) worst case
- Total: **O(n)** | Only 5–6 pointer variables → **O(1) space**

**Approach 3:**
- Walk to `left`: at most `n` steps
- Insertion loop: exactly `right - left` steps
- Total: **O(n)** single pass | Only 3 pointer variables → **O(1) space**

### Example Walk-Through for n = 500, left = 1, right = 500
- Approach 1: ~1500 operations, 500-element ArrayList
- Approach 3: ~500 operations, 3 pointers — **3× fewer operations, no extra heap**

---

## 6. Complete Worked Examples

---

### Example — Approach 1 (Collect & Rebuild)

**Input:** `1 → 2 → 3 → 4 → 5`, left=2, right=4

| Step | Action | State |
|---|---|---|
| Collect | Walk list | values = [1, 2, 3, 4, 5] |
| Swap lo=1, hi=3 | values[1]↔values[3] | values = [1, 4, 3, 2, 5] |
| Swap lo=2, hi=2 | lo ≥ hi, stop | values = [1, 4, 3, 2, 5] |
| Write back | Overwrite nodes | list = 1 → 4 → 3 → 2 → 5 |

**Output:** `1 → 4 → 3 → 2 → 5` ✅

---

### Example — Approach 2 (Two-Pass)

**Input:** `1 → 2 → 3 → 4 → 5`, left=2, right=4

```
dummy → 1 → 2 → 3 → 4 → 5

After walk:  beforeLeft = node(1),  sublistStart = node(2)

Reversal loop (3 iterations):
  i=0: prev=null, curr=2  →  prev=2, curr=3
  i=1: prev=2,    curr=3  →  prev=3, curr=4   (3→2→null)
  i=2: prev=3,    curr=4  →  prev=4, curr=5   (4→3→2→null)

Reconnect:
  beforeLeft(1).next = prev(4)    →  1 → 4 → 3 → 2 → null
  sublistStart(2).next = curr(5)  →  1 → 4 → 3 → 2 → 5
```

**Output:** `1 → 4 → 3 → 2 → 5` ✅

---

### Example — Approach 3 (One-Pass, Most Important)

**Input:** `1 → 2 → 3 → 4 → 5`, left=2, right=4

```
Setup:
  dummy → 1 → 2 → 3 → 4 → 5
  beforeLeft = node(1)
  curr = node(2)       ← this becomes the tail of reversed segment
```

**Iteration 1** (i=0):
```
  nodeToMove = curr.next = node(3)
  curr.next = node(3).next = node(4)       → chain: 2 → 4 → 5
  nodeToMove.next = beforeLeft.next = node(2)  → node(3) → node(2)
  beforeLeft.next = node(3)               → node(1) → node(3)

State: dummy → 1 → 3 → 2 → 4 → 5
                    ↑
              node(3) inserted at front
```

**Iteration 2** (i=1):
```
  nodeToMove = curr.next = node(4)
  curr.next = node(4).next = node(5)       → chain: 2 → 5
  nodeToMove.next = beforeLeft.next = node(3)  → node(4) → node(3)
  beforeLeft.next = node(4)               → node(1) → node(4)

State: dummy → 1 → 4 → 3 → 2 → 5
                    ↑
              node(4) inserted at front
```

**Output:** `1 → 4 → 3 → 2 → 5` ✅

---

### Edge Case Example — left = 1 (reversal starts at head)

**Input:** `1 → 2 → 3`, left=1, right=3

```
dummy → 1 → 2 → 3
beforeLeft = dummy   ← dummy node is crucial here!
curr = node(1)

Iteration 1: Move node(2) to front
  dummy → 2 → 1 → 3

Iteration 2: Move node(3) to front
  dummy → 3 → 2 → 1
```

**Output:** `3 → 2 → 1` ✅ — Without dummy node, `beforeLeft` would be `null` and crash.

---

## 7. Edge Cases

| Edge Case | What Happens | All Approaches Handle It? |
|---|---|---|
| `left == right` | No reversal needed; loop runs 0 times | ✅ All (loop condition prevents execution) |
| `left == 1` | Reversal starts at head; need dummy node | ✅ Approach 2 & 3 use dummy; ⚠️ Approach 1 is safe (value-based) |
| `right == n` | Reversal ends at tail; `curr` becomes `null` after loop | ✅ `sublistStart.next = null` is valid |
| Single node list | `left == right == 1`; 0 iterations | ✅ All approaches return head unchanged |
| Full list reversal | `left=1, right=n`; entire list reversed | ✅ All approaches handle correctly |
| Negative values | Values like `-500`; no effect on pointer logic | ✅ No arithmetic on values in Approaches 2 & 3 |
| Two-node list | Minimal meaningful test | ✅ Tested implicitly by above cases |

### Special Note on `left == right`
In Approach 3: `right - left = 0`, so the loop runs **zero** times. The list is returned completely unchanged. No special-case code is needed.

---

## 8. Final Summary

| Approach | Time | Space | Code Complexity | Recommended? |
|---|---|---|---|---|
| Collect & Rebuild | O(n) | O(n) | Simple | ❌ Only for learning |
| Two-Pass | O(n) | O(1) | Moderate | ✅ Good interview answer |
| One-Pass Front-Insert | O(n) | O(1) | Moderate | ✅✅ Best overall |

### Recommendation
Use the **one-pass front-insertion approach** in interviews and production. It's a single O(n) traversal with O(1) space. When an interviewer asks for a follow-up ("can you do it in one pass?"), this is exactly the answer they're looking for.

### What to Remember
> 🧠 **Pattern:** The **dummy node + front-insertion** technique is a reusable pattern for in-place linked list segment manipulation. Any time you need to reverse or reorder a segment of a linked list, reach for a dummy anchor node and think about pointer redirection rather than value swapping.
    */
}
