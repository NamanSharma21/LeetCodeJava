package LinkedList;

import Datastructures.ListNode;

public class DeleteNNodesAfterMNodesOfALinkedList {
    public static void main(String[] args) {
        DeleteNNodesAfterMNodesOfALinkedList deleteNNodesAfterMNodesOfALinkedList = new DeleteNNodesAfterMNodesOfALinkedList();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(6);
        head.next.next.next.next.next.next = new ListNode(7);
        head.next.next.next.next.next.next.next = new ListNode(8);
        head.next.next.next.next.next.next.next.next = new ListNode(9);
        head.next.next.next.next.next.next.next.next.next = new ListNode(10);
        System.out.println("DeleteNNodesAfterMNodesOfALinkedList : "
                + deleteNNodesAfterMNodesOfALinkedList.deleteNodes(head, 3, 2));
    }

    /*
     * 
     * 1. Problem Statement
     * In Plain English
     * Given a singly linked list, you must repeatedly:
     * 
     * Keep the first m nodes
     * Delete the next n nodes
     * Repeat this pattern from the remaining list until the list ends
     * 
     * Return the modified linked list's head.
     * Input Format
     * 
     * head — the head of a singly linked list (ListNode)
     * m — number of nodes to keep (positive integer)
     * n — number of nodes to delete (positive integer)
     * 
     * Output Format
     * 
     * The head of the modified linked list after deletions
     * 
     * Constraints
     * 
     * 1 <= m, n <= 1000
     * Number of nodes in the list: 1 <= length <= 10^4
     * Node values: 1 <= val <= 10^6
     */

    public ListNode deleteNodes(ListNode head, int m, int n) {
        ListNode dummy = head;
        while (dummy != null) {
            int skipCount = 1;
            while (dummy.next != null && skipCount < m) {
                dummy = dummy.next;
                skipCount++;
            }

            if (dummy.next == null) {
                return head;
            }

            int delCount = 0;
            ListNode temp = dummy.next;
            while (temp != null && delCount < n) {
                temp = temp.next;
                delCount++;
            }
            dummy.next = temp;
            dummy = temp;
        }
        return head;
    }

    /*
    # Delete N Nodes After M Nodes of a Linked List

---

## 1. Problem Statement

### In Plain English
Given a singly linked list, you must repeatedly:
1. **Keep** the first `m` nodes
2. **Delete** the next `n` nodes
3. Repeat this pattern from the remaining list until the list ends

Return the modified linked list's head.

### Input Format
- `head` — the head of a singly linked list (`ListNode`)
- `m` — number of nodes to **keep** (positive integer)
- `n` — number of nodes to **delete** (positive integer)

### Output Format
- The head of the modified linked list after deletions

### Constraints
- `1 <= m, n <= 1000`
- Number of nodes in the list: `1 <= length <= 10^4`
- Node values: `1 <= val <= 10^6`

### What Needs to Be Computed
Traverse the list in a cycle of (keep `m`, delete `n`) and stitch together the surviving nodes.

---

## 2. Intuition

Think of it like a **queue line with a bouncer**:
- Let `m` people through the door
- Kick out `n` people
- Repeat

The key insight is that you need two pointers working together:
- A **"keeper" pointer** that advances `m` steps (marking end of surviving segment)
- A **"skipper" pointer** that advances `n` steps past the keeper (marking start of next surviving segment)
- Then you **reconnect** the keeper's `next` to the skipper's current position

**What makes it tricky:**
- You must carefully handle `null` checks mid-traversal (list may end during either the keep or delete phase)
- The pointer reconnection must happen *before* moving on, or you lose your reference

---

## 3. Approach Overview

| Approach | Key Idea | Use Case |
|---|---|---|
| **Single Pass (Optimal)** | Two-pointer in-place traversal with reconnection | Always — this is the only sensible approach |

There is genuinely only one practical approach here. The problem is inherently sequential (linked list, order matters), so:
- No sorting helps
- No hashing helps
- No divide and conquer applies

The single-pass in-place pointer manipulation **is** the optimal solution.

---

## 4. Detailed Solution in Java

### Algorithm Step-by-Step

1. Start `current` at `head`
2. **Keep phase:** advance `current` exactly `m` steps (or until `null`)
3. If `current` is `null`, we're done — return `head`
4. **Delete phase:** starting from `current.next`, advance a `temp` pointer `n` steps (or until `null`)
5. **Reconnect:** set `current.next = temp` (skipping the deleted nodes)
6. Move `current` to `temp` (the new starting point for next keep phase)
7. Repeat from step 2

```java
class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }
}

class Solution {

    public ListNode deleteNodes(ListNode head, int m, int n) {
        ListNode current = head;

        while (current != null) {
            // --- KEEP PHASE: advance m-1 more steps from current ---
            // current is already on the 1st kept node, so walk m-1 more
            int keepCount = 1;
            while (keepCount < m && current.next != null) {
                current = current.next;
                keepCount++;
            }

            // If we've exhausted the list during keep phase, we're done
            if (current.next == null) {
                return head;
            }

            // --- DELETE PHASE: skip n nodes starting from current.next ---
            ListNode temp = current.next;
            int deleteCount = 0;
            while (deleteCount < n && temp != null) {
                temp = temp.next;
                deleteCount++;
            }

            // --- RECONNECT: link end of kept segment to start of next segment ---
            current.next = temp;

            // Move current forward to begin next keep phase
            current = temp;
        }

        return head;
    }
}
```

---

## 5. Time & Space Complexity

### Time Complexity: **O(L)**
- `L` = total number of nodes in the list
- Every node is visited **exactly once**: kept nodes are traversed in the keep phase, deleted nodes are traversed in the delete phase
- No nested loops over the same data; inner `while` loops together consume the list linearly
- Example: list of 1000 nodes, m=3, n=2 → ~200 reconnections, but still 1000 total node visits

### Space Complexity: **O(1)**
- Only a constant number of pointers used: `current`, `temp`, `keepCount`, `deleteCount`
- No auxiliary data structures (no array copy, no stack, no map)
- The modifications are done **in-place** on the original list

---

## 6. Complete Worked Example

### Input
```
List: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10
m = 3, n = 2
Expected output: 1 -> 2 -> 3 -> 6 -> 7 -> 8 -> null (then 10 if existed past 9)
```

Let's trace carefully:

| Iteration | current starts at | Keep Phase | After Keep, current at | Delete Phase (skip n=2) | temp lands at | Reconnect |
|---|---|---|---|---|---|---|
| 1 | node(1) | walk 2 more steps: 1→2→3 | node(3) | skip node(4), node(5) → temp | node(6) | 3.next = node(6) |
| 2 | node(6) | walk 2 more steps: 6→7→8 | node(8) | skip node(9), node(10) → temp | null | 8.next = null |
| 3 | null | loop exits | — | — | — | done |

**Resulting list:** `1 -> 2 -> 3 -> 6 -> 7 -> 8 -> null`

### Step-by-Step State

```
Initial:  1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10

After iteration 1:
  kept: [1,2,3], deleted: [4,5]
  List: 1 -> 2 -> 3 -> 6 -> 7 -> 8 -> 9 -> 10

After iteration 2:
  kept: [6,7,8], deleted: [9,10]
  List: 1 -> 2 -> 3 -> 6 -> 7 -> 8 -> null

Final: 1 -> 2 -> 3 -> 6 -> 7 -> 8
```

---

## 7. Edge Cases

| Edge Case | Description | How Code Handles It |
|---|---|---|
| **List shorter than m** | e.g., list has 2 nodes, m=5 | Keep phase runs out: `current.next == null`, return `head` immediately — nothing deleted |
| **List exactly m nodes** | Nothing to delete | Same as above — keep phase ends at last node, `current.next == null`, return |
| **List shorter than m+n** | Keep phase fine, delete phase runs short | `temp` hits `null` before `n` deletions — `current.next = null`, list truncated cleanly |
| **m=1** | Keep only 1 node, delete n | Works: `keepCount` starts at 1, inner loop doesn't execute; jumps to delete phase |
| **n=1** | Delete only 1 node each cycle | `deleteCount` reaches 1 quickly, `temp` advances once |
| **Single node list** | `head.next == null` from start | Keep phase: `current.next == null` immediately, return `head` |
| **All nodes deleted** | m=0 isn't possible per constraints (m≥1), so safe | N/A |
| **Very large list (10^4 nodes)** | Performance concern | O(L) handles this trivially |

**Key safety:** every `null` check is done **before** dereferencing, so no `NullPointerException` risks.

---

## 8. Final Summary

### Comparison

| | Single Pass (In-Place) |
|---|---|
| Time | O(L) |
| Space | O(1) |
| Code complexity | Low |
| Recommended? | ✅ Yes, always |

### What to Remember

This is a **pointer reconnection pattern** on a linked list — the same fundamental technique behind reversing sublists, removing every k-th node, and merging sorted lists. The core skill is **identifying the "tail of kept segment"** and **"head of next segment"** and stitching them together, while carefully checking for `null` at every step.

**The mental model:** think in terms of "where does the kept segment end, and where does the surviving part resume?" — then connect those two points and advance.
    */
}
