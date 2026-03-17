package LinkedList;

import Datastructures.ListNode;

public class PartitionList {
    public static void main(String[] args) {
        PartitionList partitionList = new PartitionList();
        ListNode head = new ListNode(1);
        head.next = new ListNode(4);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(2);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(2);
        System.out.println("PartitionList : " + partitionList.partition(head, 3));

    }

    /*
     * https://leetcode.com/problems/partition-list/description/?envType=problem-
     * list-v2&envId=linked-list
     * 
     * 
     * Given the head of a linked list and a value x, partition it such that all
     * nodes less than x come before nodes greater than or equal to x.
     * 
     * You should preserve the original relative order of the nodes in each of the
     * two partitions.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: head = [1,4,3,2,5,2], x = 3
     * Output: [1,2,2,4,3,5]
     * Example 2:
     * 
     * Input: head = [2,1], x = 2
     * Output: [1,2]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the list is in the range [0, 200].
     * -100 <= Node.val <= 100
     * -200 <= x <= 200
     */

    public ListNode partition(ListNode head, int x) {
        ListNode leftDummy = new ListNode(0);
        ListNode rightDummy = new ListNode(0);
        ListNode leftTail = leftDummy;
        ListNode rightTail = rightDummy;
        ListNode current = head;
        while (current != null) {
            ListNode nextNode = current.next;
            if (current.val < x) {
                leftTail.next = current;
                leftTail = leftTail.next;
            } else {
                rightTail.next = current;
                rightTail = rightTail.next;
            }
            current = nextNode;
        }

        rightTail.next = null;
        leftTail.next = rightDummy.next;
        return leftDummy.next;
    }

    /*
    
    # Partition List — Deep Dive

## 1. Problem Statement

### Restated in Plain English
Given the **head of a singly linked list** and a value `x`, rearrange the nodes so that:
- All nodes with values **less than `x`** come **before** all nodes with values **greater than or equal to `x`**.
- The **relative order** of nodes within each group must be **preserved** (this is the key constraint — it's not just a sort).

### Input / Output
| | Detail |
|---|---|
| **Input** | `head` — reference to the first node of a linked list; `int x` — the partition value |
| **Output** | `head` of the **modified** linked list |
| **Node values** | `-100 ≤ Node.val ≤ 100` |
| **List length** | `0 ≤ n ≤ 200` |

### What Exactly Needs to Be Computed
Return the head of a list where every node with `val < x` appears before every node with `val >= x`, **preserving original relative order within both halves**.

---

## 2. Intuition

### The Core Idea
Imagine physically picking up each node from the list and placing it into **one of two buckets**:
- **Bucket L ("less")** — for nodes where `val < x`
- **Bucket R ("greater-or-equal")** — for nodes where `val >= x`

You walk the list left-to-right exactly once, so relative order is automatically preserved. At the end, you **stitch the tail of L to the head of R**, and you're done.

### How a Human Reasons About It
1. Walk the list node by node.
2. Ask: *is this node's value less than x?* → left pile. Otherwise → right pile.
3. After processing all nodes, connect the two piles.
4. Make sure the tail of the right pile points to `null` (it may still point to an old node).

### What Makes It Tricky
- You must **not change node values** — only re-link `next` pointers.
- Forgetting to **null-terminate** the right partition causes an infinite loop or wrong output.
- Using **sentinel (dummy) head nodes** for both partitions greatly simplifies edge cases (empty left or right partition).

---

## 3. Approach Overview

| # | Approach | Key Idea | Use When | Optimal? |
|---|---|---|---|---|
| 1 | **Collect + Rebuild** | Collect values into two `ArrayList`s, rebuild list | Understanding the problem | ❌ Uses extra space, loses node identity |
| 2 | **Two-Pointer In-Place** | Re-link original nodes using two dummy heads | Always — interviews, production | ✅ **Optimal** |

> **Approach 1** is shown for learning/intuition. **Approach 2** is the standard, optimal, interview-expected solution.

---

## 4. Detailed Solutions in Java

### Approach 1 — Collect Values & Rebuild (Brute Force)

#### Algorithm Step-by-Step
1. Traverse the list; push `val < x` values into `leftVals`, others into `rightVals`.
2. Create a dummy head and rebuild the list by appending all left values then right values.
3. Return `dummy.next`.

```java
class Solution {
    public ListNode partition(ListNode head, int x) {
        List<Integer> leftVals  = new ArrayList<>();
        List<Integer> rightVals = new ArrayList<>();

        // Step 1: Collect values into two buckets
        ListNode curr = head;
        while (curr != null) {
            if (curr.val < x) {
                leftVals.add(curr.val);
            } else {
                rightVals.add(curr.val);
            }
            curr = curr.next;
        }

        // Step 2: Rebuild the list using a dummy sentinel
        ListNode dummy = new ListNode(0);
        ListNode builder = dummy;

        for (int val : leftVals) {
            builder.next = new ListNode(val);
            builder = builder.next;
        }
        for (int val : rightVals) {
            builder.next = new ListNode(val);
            builder = builder.next;
        }

        return dummy.next;
    }
}
```

---

### Approach 2 — Two Dummy Heads, In-Place Re-linking ✅ OPTIMAL

#### Algorithm Step-by-Step
1. Create two **dummy (sentinel) nodes**: `leftDummy` and `rightDummy`.
   - Sentinels avoid special-casing an empty partition — you never need to check "is this the first node?".
2. Maintain **tail pointers** `leftTail` and `rightTail`, both initially pointing to their dummy.
3. Traverse the original list with pointer `curr`:
   - If `curr.val < x` → append to left partition: `leftTail.next = curr`, advance `leftTail`.
   - Otherwise → append to right partition: `rightTail.next = curr`, advance `rightTail`.
4. After the loop:
   - **Null-terminate** the right partition: `rightTail.next = null`.
     *(Critical! `curr`'s old `next` may point somewhere inside the list.)*
   - **Join** the two partitions: `leftTail.next = rightDummy.next`.
5. Return `leftDummy.next`.

```java
class Solution {
    public ListNode partition(ListNode head, int x) {
        // Sentinel nodes eliminate null-checks when appending first elements
        ListNode leftDummy  = new ListNode(0);
        ListNode rightDummy = new ListNode(0);

        ListNode leftTail  = leftDummy;   // tracks end of left partition
        ListNode rightTail = rightDummy;  // tracks end of right partition

        ListNode curr = head;
        while (curr != null) {
            ListNode nextNode = curr.next; // save next before re-linking

            if (curr.val < x) {
                leftTail.next = curr;
                leftTail = leftTail.next;
            } else {
                rightTail.next = curr;
                rightTail = rightTail.next;
            }

            curr = nextNode;
        }

        // Critical: seal the right partition to avoid a cycle
        rightTail.next = null;

        // Join left → right
        leftTail.next = rightDummy.next;

        return leftDummy.next;
    }
}
```

---

## 5. Time & Space Complexity

### Approach 1 — Collect & Rebuild

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | Single pass to collect + single pass to rebuild |
| **Space** | O(n) | Two ArrayLists holding all n values; also creates n new nodes |

**Walk-through:** For n = 200 nodes → ~400 operations (collect + rebuild) + 200 new node allocations.

---

### Approach 2 — Two Dummy Heads ✅

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | Single pass; each node is visited exactly once |
| **Space** | O(1) | Only 4 extra pointers (`leftDummy`, `rightDummy`, `leftTail`, `rightTail`) — no new nodes created |

**Walk-through:** For n = 200 nodes → exactly 200 iterations. For n = 10,000 → exactly 10,000 iterations. Constant extra memory regardless of n.

---

## 6. Complete Worked Examples

### Example for Approach 2 (Optimal)

**Input:** `1 → 4 → 3 → 2 → 5 → 2`, `x = 3`

**Expected Output:** `1 → 2 → 2 → 4 → 3 → 5`

#### Step-by-Step Trace

```
Initial:
  leftDummy → [0]       leftTail  = leftDummy
  rightDummy → [0]      rightTail = rightDummy
  curr = [1]
```

| Step | curr | curr.val < 3? | Left Partition | Right Partition |
|------|------|---------------|----------------|-----------------|
| 1 | Node(1) | ✅ 1 < 3 | dummy→**1** | dummy |
| 2 | Node(4) | ❌ 4 ≥ 3 | dummy→1 | dummy→**4** |
| 3 | Node(3) | ❌ 3 ≥ 3 | dummy→1 | dummy→4→**3** |
| 4 | Node(2) | ✅ 2 < 3 | dummy→1→**2** | dummy→4→3 |
| 5 | Node(5) | ❌ 5 ≥ 3 | dummy→1→2 | dummy→4→3→**5** |
| 6 | Node(2) | ✅ 2 < 3 | dummy→1→2→**2** | dummy→4→3→5 |
| 7 | null | — | done | done |

**After loop:**
- `rightTail.next = null` → `...→5→null` ✅ (Node(2)'s old next was null anyway here, but we always set it)
- `leftTail.next = rightDummy.next` → `...→2→4→...`

**Result:** `1 → 2 → 2 → 4 → 3 → 5` ✅

---

### Example 2 — All Nodes Go to One Side

**Input:** `5 → 6 → 7`, `x = 3`

| Step | curr | curr.val < 3? | Left | Right |
|------|------|---------------|------|-------|
| 1 | Node(5) | ❌ | dummy | dummy→**5** |
| 2 | Node(6) | ❌ | dummy | dummy→5→**6** |
| 3 | Node(7) | ❌ | dummy | dummy→5→6→**7** |

- `rightTail.next = null`
- `leftTail.next = rightDummy.next = Node(5)`
- `leftDummy.next = rightDummy.next`

**Result:** `5 → 6 → 7` (unchanged) ✅

---

## 7. Edge Cases

| Edge Case | Input Example | How Approach 2 Handles It |
|---|---|---|
| **Empty list** | `head = null` | Loop never runs; `leftDummy.next = null`, `rightDummy.next = null` → returns `null` ✅ |
| **Single node, goes left** | `[1]`, x=3 | Left gets Node(1), right is empty. `leftTail.next = null` (rightDummy.next). Returns `[1]` ✅ |
| **Single node, goes right** | `[5]`, x=3 | Left empty, right gets Node(5). `rightTail.next = null`. `leftDummy.next = Node(5)`. Returns `[5]` ✅ |
| **All values < x** | `[1,2]`, x=5 | All go left, right stays empty. No issue — `rightDummy.next = null`, so left list is null-terminated ✅ |
| **All values ≥ x** | `[5,6]`, x=3 | All go right, left stays empty. `leftDummy.next = rightDummy.next`. Returns right list ✅ |
| **Duplicates equal to x** | `[3,3,3]`, x=3 | 3 ≥ 3, all go to right. Order preserved ✅ |
| **Forgetting null-termination** | Any list | ⚠️ Without `rightTail.next = null`, the last right-partition node still points to its old `next`, causing a **cycle or stale pointer**. Approach 1 is immune (builds new nodes). Approach 2 **requires** this step. |

---

## 8. Final Summary

| Approach | Time | Space | Notes |
|---|---|---|---|
| Collect & Rebuild | O(n) | O(n) | Good for understanding; not ideal for memory |
| Two Dummy Heads ✅ | O(n) | O(1) | Optimal; standard interview answer |

**Recommendation:** Always use the **Two Dummy Heads** approach. It's O(1) space, a single pass, and elegant.

### 🧠 Key Pattern to Remember
> **"Two dummy sentinel nodes + stitch at the end"** is the universal pattern for **stable in-place linked list partitioning**. The sentinel eliminates all empty-list edge cases for each half, and the mandatory `rightTail.next = null` prevents cycles — never skip it.
    */
}
