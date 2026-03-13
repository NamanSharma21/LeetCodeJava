package LinkedList;

import Datastructures.ListNode;

public class RemoveDuplicatesFromSortedListII {
    public static void main(String[] args) {
        RemoveDuplicatesFromSortedListII removeDuplicatesFromSortedListII = new RemoveDuplicatesFromSortedListII();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(3);
        head.next.next.next.next = new ListNode(4);
        head.next.next.next.next.next = new ListNode(4);
        head.next.next.next.next.next.next = new ListNode(5);
        System.out.println(
                "RemoveDuplicatesFromSortedListII : " + removeDuplicatesFromSortedListII.deleteDuplicates(head));
    }

    /*
     * 
     * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
     * description/?envType=problem-list-v2&envId=linked-list
     * 
     * Given the head of a sorted linked list, delete all nodes that have duplicate
     * numbers, leaving only distinct numbers from the original list. Return the
     * linked list sorted as well.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: head = [1,2,3,3,4,4,5]
     * Output: [1,2,5]
     * Example 2:
     * 
     * 
     * Input: head = [1,1,1,2,3]
     * Output: [2,3]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the list is in the range [0, 300].
     * -100 <= Node.val <= 100
     * The list is guaranteed to be sorted in ascending order.
     */

    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode current = head;
        ListNode prev = dummy;
        while (current != null) {
            boolean isDuplicate = current.next != null && current.val == current.next.val;
            if (isDuplicate) {
                int duplicateVal = current.val;
                while (current != null && current.val == duplicateVal) {
                    current = current.next;
                }
                prev.next = current;
            } else {
                prev = current;
                current = current.next;
            }
        }
        return dummy.next;
    }


    /*
    
    # Remove Duplicates from Sorted List II

---

## 1. Problem Statement

### In Plain Terms
You are given the head of a **singly linked list that is already sorted in ascending order**. Your task is to remove **all nodes** that have duplicate values — meaning if a value appears more than once anywhere in the list, every node with that value must be deleted entirely. Return the head of the modified list containing only values that appeared **exactly once**.

### Input Format
- A singly linked list node (`ListNode head`)
- Each `ListNode` has an `int val` and a `ListNode next`
- The list is **sorted in non-decreasing order**

### Output Format
- Return the `head` of the cleaned linked list (only nodes with unique values remain)
- The relative order of remaining nodes must be preserved

### Constraints
- Number of nodes: `0 <= n <= 300`
- Node values: `-100 <= Node.val <= 100`
- The list is **guaranteed to be sorted**

### What Needs to Be Returned
Not just the duplicates removed once — but **all occurrences** of any value that appears more than once.

```
Input:  1 → 2 → 3 → 3 → 4 → 4 → 5
Output: 1 → 2 → 5

Input:  1 → 1 → 1 → 2 → 3
Output: 2 → 3
```

---

## 2. Intuition

### The Core Idea
Since the list is **already sorted**, all duplicates are **grouped together**. You never need to scan the whole list searching for a duplicate — if a node's value equals the next node's value, you've found a duplicate cluster right there.

### How a Human Reasons About It
1. Walk through the list node by node.
2. When you see two consecutive nodes with the **same value**, you know that entire "run" of that value must be skipped.
3. Keep skipping until you find a node with a **different value** (or reach the end).
4. Connect the previous "safe" node directly to whatever comes after the duplicate cluster.
5. Repeat until the end.

### What Makes This Tricky
- **The head itself might be a duplicate.** If `1 → 1 → 2`, the head must be removed entirely. This is the classic linked list edge case that trips people up — you can't just look ahead from a fixed starting point.
- **Distinguishing "remove one copy" vs "remove all copies".** This is LeetCode 82, not 83. You must remove the value completely, not just deduplicate.
- **Connecting pointers correctly** without losing the rest of the list.

---

## 3. Approach Overview

| # | Approach | Key Idea | Time | Space | Use When |
|---|----------|----------|------|-------|----------|
| 1 | **Collect Unique Values (Brute Force)** | Store values in a map, rebuild list | O(n) | O(n) | Quick prototyping, interviews with no space constraint |
| 2 | **In-place Two-pointer (Optimal)** | Sentinel/dummy node + skip duplicate runs | O(n) | O(1) | Always — this is the intended solution |

### ✅ Optimal: Approach 2 — In-place with a Dummy Node
Because the list is sorted, we can detect and skip entire duplicate clusters in a single linear pass without storing anything extra. This is the pattern interviewers expect.

---

## 4. Detailed Solutions in Java

### Approach 1: Collect Unique Values (Brute Force)

#### Algorithm
1. Make one pass through the list, counting frequency of each value using a `LinkedHashMap` (preserves insertion order).
2. Make a second pass: for each node whose value has a count of exactly 1, add it to a new list.
3. Return the head of the new list.

```java
import java.util.LinkedHashMap;
import java.util.Map;

class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) return null;

        // Count frequency of each value, preserving order
        Map<Integer, Integer> frequency = new LinkedHashMap<>();
        ListNode current = head;
        while (current != null) {
            frequency.put(current.val, frequency.getOrDefault(current.val, 0) + 1);
            current = current.next;
        }

        // Build a new list from values that appear exactly once
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        current = head;
        while (current != null) {
            if (frequency.get(current.val) == 1) {
                tail.next = new ListNode(current.val);
                tail = tail.next;
            }
            current = current.next;
        }

        return dummy.next;
    }
}
```

---

### Approach 2: In-place with Dummy Node + Skip (Optimal) ✅

#### Algorithm — Step by Step

1. **Create a dummy (sentinel) node** whose `next` points to `head`. This elegantly handles the case where the head itself is a duplicate, because we always have a node "before" whatever we're examining.

2. **`prev` pointer** starts at `dummy`. It always points to the last confirmed node that is safe to keep. We only advance `prev` when we confirm a value is not a duplicate.

3. **`curr` pointer** starts at `head`. It's our scanner — it walks forward.

4. **At each step:**
   - Check if `curr.next` exists and `curr.val == curr.next.val`
   - If **yes** → duplicate cluster found. Record the duplicate value. Advance `curr` until we've passed **all** nodes with that value.
   - Then set `prev.next = curr.next` to bypass the entire cluster.
   - If **no** → `curr` is a unique node. Advance `prev` to `curr`.
   - In both cases, advance `curr` to `curr.next`.

5. Return `dummy.next`.

```java
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        // Dummy node guards against head itself being a duplicate
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode prev = dummy; // last confirmed unique node
        ListNode curr = head;  // current node being examined

        while (curr != null) {
            boolean isDuplicate = curr.next != null && curr.val == curr.next.val;

            if (isDuplicate) {
                int duplicateVal = curr.val;

                // Skip all nodes sharing this duplicate value
                while (curr != null && curr.val == duplicateVal) {
                    curr = curr.next;
                }

                // Bypass the entire duplicate cluster
                prev.next = curr;
            } else {
                // curr is a unique node — safe to keep, advance prev
                prev = curr;
                curr = curr.next;
            }
        }

        return dummy.next;
    }
}
```

---

## 5. Time & Space Complexity

### Approach 1: Frequency Map

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | Two passes through the list of n nodes |
| **Space** | O(n) | The `LinkedHashMap` stores up to n distinct values |

**Example walkthrough:**
- List of 7 nodes → ~7 map insertions + ~7 checks = ~14 operations → still O(n)
- All duplicates (e.g., `1→1→1→1`) → map has 1 entry, but we still visit all 4 nodes

### Approach 2: In-place Dummy Node ✅

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | Each node is visited at most twice (once by `curr`, potentially once during the inner skip loop — but total moves across the entire algorithm is still n) |
| **Space** | O(1) | Only two pointers used (`prev`, `curr`), no extra data structures |

**Example walkthrough:**
- `1 → 2 → 3 → 3 → 4` (5 nodes): `curr` touches all 5 nodes once → 5 operations
- `1 → 1 → 1 → 1 → 1` (5 nodes): outer loop hits first node, inner while skips remaining 4 → still 5 total moves

---

## 6. Complete Worked Examples

### Approach 1: Frequency Map

**Input:** `1 → 2 → 3 → 3 → 4 → 4 → 5`

**Step 1 — Build frequency map:**

| Node visited | Map state |
|---|---|
| val=1 | {1:1} |
| val=2 | {1:1, 2:1} |
| val=3 | {1:1, 2:1, 3:1} |
| val=3 | {1:1, 2:1, 3:2} |
| val=4 | {1:1, 2:1, 3:2, 4:1} |
| val=4 | {1:1, 2:1, 3:2, 4:2} |
| val=5 | {1:1, 2:1, 3:2, 4:2, 5:1} |

**Step 2 — Rebuild list (frequency == 1 only):**
- val=1 → count 1 ✅ → add
- val=2 → count 1 ✅ → add
- val=3 → count 2 ❌ → skip
- val=3 → count 2 ❌ → skip
- val=4 → count 2 ❌ → skip
- val=4 → count 2 ❌ → skip
- val=5 → count 1 ✅ → add

**Output:** `1 → 2 → 5` ✅

---

### Approach 2: In-place Dummy Node ✅

**Input:** `1 → 2 → 3 → 3 → 4 → 4 → 5`

**List structure:**
```
dummy(0) → 1 → 2 → 3 → 3 → 4 → 4 → 5 → null
  ↑prev    ↑curr
```

**Step-by-step trace:**

| Step | prev.val | curr.val | curr.next.val | Duplicate? | Action |
|------|----------|----------|---------------|------------|--------|
| 1 | 0 (dummy) | 1 | 2 | No | prev→1, curr→2 |
| 2 | 1 | 2 | 3 | No | prev→2, curr→3 |
| 3 | 2 | 3 | 3 | **Yes** | Skip all 3s → curr→4, prev.next=4 |
| 4 | 2 | 4 | 4 | **Yes** | Skip all 4s → curr→5, prev.next=5 |
| 5 | 2 | 5 | null | No | prev→5, curr→null |
| End | — | — | — | — | Return dummy.next |

**List after all steps:**
```
dummy(0) → 1 → 2 → 5 → null
```

**Output:** `1 → 2 → 5` ✅

---

**Second Example — head is a duplicate:**

**Input:** `1 → 1 → 1 → 2 → 3`

```
dummy(0) → 1 → 1 → 1 → 2 → 3
  ↑prev    ↑curr
```

| Step | prev.val | curr.val | Duplicate? | Action |
|------|----------|----------|------------|--------|
| 1 | 0 (dummy) | 1 | **Yes** (1==1) | Skip all 1s → curr→2, prev.next=2 |
| 2 | 0 (dummy) | 2 | No (2≠3) | prev→2, curr→3 |
| 3 | 2 | 3 | No (next=null) | prev→3, curr→null |

**Output:** `2 → 3` ✅ — The dummy node saved us here. Without it, removing the head would require special-casing.

---

## 7. Edge Cases

| Edge Case | Input | Expected Output | How Approach 2 Handles It |
|---|---|---|---|
| Empty list | `null` | `null` | `curr` is null immediately, while loop skipped |
| Single node | `1` | `1` | No duplicate check needed, `prev` advances to it |
| All same value | `1→1→1` | `null` | All skipped; `dummy.next = null` |
| Two nodes, both same | `1→1` | `null` | Duplicate detected, skipped entirely |
| Two nodes, both different | `1→2` | `1→2` | Both confirmed unique |
| Head is duplicate | `1→1→2` | `2` | Dummy node allows `prev.next` to skip the 1s |
| Tail is duplicate | `1→2→2` | `1` | Inner while-loop drains to null |
| Alternating uniques | `1→2→3` | `1→2→3` | No duplicates detected, all kept |
| Negative values | `-3→-3→-1→0` | `-1→0` | Works identically, no special handling needed |
| Long run of duplicates | `5→5→5→5→5→6` | `6` | Inner while skips all five 5s at once |

---

## 8. Final Summary

### Comparison Table

| Criterion | Approach 1 (Frequency Map) | Approach 2 (In-place) ✅ |
|---|---|---|
| Time Complexity | O(n) | O(n) |
| Space Complexity | O(n) | **O(1)** |
| Passes needed | 2 | 1 |
| Code simplicity | Higher | Moderate |
| Interview recommendation | Acceptable | **Preferred** |

### ✅ Recommended: Approach 2 (Dummy Node + In-place Skip)

Use it every time. It's O(1) space, single-pass, and directly demonstrates mastery of pointer manipulation — which is exactly what interviewers want to see in linked list problems.

### 🧠 What to Remember

> **The Dummy Node Pattern** is your best friend for linked list problems where the head itself might be removed. Pair it with a `prev` pointer that only advances when a node is confirmed safe, and you have a universal tool for deletion problems on sorted linked lists.

The core insight of this problem: **sorted = duplicates are adjacent = one linear scan is enough**. You never need to look backward or use extra memory.
    
    */
}
