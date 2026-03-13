package LinkedList;

import Datastructures.ListNode;

public class SwapNodesInPairs {
    public static void main(String[] args) {
        SwapNodesInPairs swapNodesInPairs = new SwapNodesInPairs();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        System.out.println("SwapNodesInPairs : " + swapNodesInPairs.swapPairsReccursive(head));
    }

    /*
     * https://leetcode.com/problems/swap-nodes-in-pairs/description/?envType=
     * problem-list-v2&envId=linked-list
     * 
     * Given a linked list, swap every two adjacent nodes and return its head. You
     * must solve the problem without modifying the values in the list's nodes
     * (i.e., only nodes themselves may be changed.)
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: head = [1,2,3,4]
     * 
     * Output: [2,1,4,3]
     * 
     * Explanation:
     * 
     * 
     * 
     * Example 2:
     * 
     * Input: head = []
     * 
     * Output: []
     * 
     * Example 3:
     * 
     * Input: head = [1]
     * 
     * Output: [1]
     * 
     * Example 4:
     * 
     * Input: head = [1,2,3]
     * 
     * Output: [2,1,3]
     * 
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the list is in the range [0, 100].
     * 0 <= Node.val <= 100
     */

    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;
            ListNode second = prev.next.next;
            first.next = second.next;
            second.next = first;
            prev.next = second;
            prev = first;
        }
        return dummy.next;
    }

    public ListNode swapPairsReccursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode first = head;
        ListNode second = head.next;
        first.next = swapPairsReccursive(second.next);
        second.next = first;
        return second;
    }

    /*
    
    # Swap Nodes in Pairs — Deep Dive

---

## 1. Problem Statement

### What the Problem Says
Given the head of a singly linked list, swap every two adjacent nodes and return the head of the modified list. You must swap the **nodes themselves**, not just their values.

### Input Format
- A singly linked list represented by its head node
- Each node contains an integer value and a `next` pointer

### Output Format
- The head of the modified linked list after all pairwise swaps

### Constraints
- Number of nodes: `0 ≤ n ≤ 100`
- Node values: `0 ≤ Node.val ≤ 100`
- **Must swap nodes, not values**

### What Exactly Needs to Be Computed
Given: `1 → 2 → 3 → 4`
Return: `2 → 1 → 4 → 3`

Given: `1 → 2 → 3`
Return: `2 → 1 → 3` ← the last lone node stays in place

---

## 2. Intuition

### Core Idea in Simple Terms
Imagine you have pairs of people standing in a line. You want to swap each pair's positions. The first pair swaps, then the next pair swaps, and so on. If there's an odd person left at the end, they stay put.

### How a Human Would Reason Step by Step
1. Look at the first two nodes — call them `first` and `second`
2. Make `second` come before `first`
3. Connect `first.next` to whatever comes after this pair
4. Move forward by two nodes and repeat
5. Stop when fewer than two nodes remain

### What Makes This Tricky
- **Pointer management**: You must carefully update 3–4 pointers per swap without losing references to the rest of the list
- **No value swapping**: Forces you to actually rewire the list structure
- **The "connector" problem**: After swapping a pair, you need the tail of that pair (`first`) to point correctly to the head of the next swapped pair — which you don't know yet (recursive insight)
- **Odd-length lists**: The last lone node must remain untouched

---

## 3. Approach Overview

| # | Approach | Key Idea | When to Use |
|---|----------|----------|-------------|
| 1 | **Iterative with dummy node** | Use a `prev` pointer to re-link pairs | ✅ Optimal for interviews |
| 2 | **Recursive** | Swap first two, recurse on rest | Clean, elegant, slightly more space |
| 3 | **Convert to array** | Dump values into array, swap, rebuild | Brute force; violates "no value swap" spirit |

### Which Is Optimal and Why
**Iterative with dummy node** is optimal:
- O(n) time, O(1) space
- No recursion stack overhead
- Most interviewers prefer iterative linked list manipulation

**Recursive** is equally time-efficient but uses O(n) stack space — still excellent and often cleaner to explain.

**Array approach** violates the constraint of swapping nodes (not values), so it should be avoided in interviews.

---

## 4. Detailed Solutions in Java

### Node Definition (shared by all approaches)
```java
public class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
```

---

### Approach 1: Iterative with Dummy Node (Optimal)

#### Algorithm Step-by-Step
1. Create a `dummy` node pointing to `head` — this avoids special-casing the head swap
2. Maintain a `prev` pointer starting at `dummy`
3. In each iteration:
   - Identify `first` = `prev.next` and `second` = `prev.next.next`
   - If either is null, stop — no complete pair remains
   - Perform the swap: rewire 3 pointers
   - Advance `prev` to `first` (which is now the second node of the swapped pair)
4. Return `dummy.next`

#### The 3-Pointer Swap Visualized
```
Before:  prev → first → second → rest
After:   prev → second → first → rest
```
Steps:
```
first.next = second.next   // first now points to rest
second.next = first        // second now points to first
prev.next = second         // prev now points to second (new head of pair)
prev = first               // move prev forward for next iteration
```

```java
class Solution {
    public ListNode swapPairs(ListNode head) {
        // Dummy node simplifies edge cases (empty list, single node)
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;         // first node of the pair
            ListNode second = prev.next.next;   // second node of the pair

            // Step 1: first skips over second, points to rest of list
            first.next = second.next;

            // Step 2: second points back to first (completing the swap)
            second.next = first;

            // Step 3: prev connects to second (now the front of this pair)
            prev.next = second;

            // Move prev to first (now the tail of this swapped pair)
            prev = first;
        }

        return dummy.next;
    }
}
```

---

### Approach 2: Recursive (Elegant & Clean)

#### Algorithm Step-by-Step
1. **Base case**: If `head` is null or `head.next` is null, return `head` — nothing to swap
2. Identify `first = head` and `second = head.next`
3. Recursively swap everything from `second.next` onward
4. Wire: `second.next = first`, `first.next = result of recursion`
5. Return `second` — it's the new head of this subproblem

#### Recursive Mental Model
```
swapPairs(1→2→3→4)
  first=1, second=2
  first.next = swapPairs(3→4)
                 first=3, second=4
                 first.next = swapPairs(null) = null
                 second.next = first (3)
                 return 4→3→null
  second.next = first (1)
  return 2→1→4→3
```

```java
class Solution {
    public ListNode swapPairs(ListNode head) {
        // Base case: 0 or 1 node — nothing to swap
        if (head == null || head.next == null) {
            return head;
        }

        ListNode first = head;           // will become second in the pair
        ListNode second = head.next;     // will become first in the pair

        // first's next becomes the result of swapping the remaining list
        first.next = swapPairs(second.next);

        // second now points to first (the actual swap)
        second.next = first;

        // second is now the new head of this pair
        return second;
    }
}
```

---

### Approach 3: Array/Brute Force (For Understanding Only — Violates Constraints)

#### Algorithm Step-by-Step
1. Traverse the list, collect all values into an `ArrayList`
2. Swap adjacent elements in the list (index 0↔1, 2↔3, etc.)
3. Rebuild the linked list from the swapped values

```java
class Solution {
    public ListNode swapPairs(ListNode head) {
        // Collect all node values
        List<Integer> values = new ArrayList<>();
        ListNode current = head;
        while (current != null) {
            values.add(current.val);
            current = current.next;
        }

        // Swap adjacent values
        for (int i = 0; i + 1 < values.size(); i += 2) {
            int temp = values.get(i);
            values.set(i, values.get(i + 1));
            values.set(i + 1, temp);
        }

        // Rebuild linked list
        ListNode dummy = new ListNode(0);
        ListNode builder = dummy;
        for (int val : values) {
            builder.next = new ListNode(val);
            builder = builder.next;
        }

        return dummy.next;
    }
}
```
> ⚠️ **This violates the problem constraint** of swapping nodes (not values). Only use this to verify your logic during practice.

---

## 5. Time & Space Complexity

### Approach 1: Iterative

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | We visit each node exactly once. For n=100, ~100 operations. |
| **Space** | O(1) | Only a fixed set of pointers (`dummy`, `prev`, `first`, `second`) — no extra data structures |

**Walk-through with n=4**: 2 iterations, each doing 3 pointer reassignments = ~6 operations total

### Approach 2: Recursive

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | Each node is processed in exactly one recursive call |
| **Space** | O(n) | The call stack grows to depth n/2 (one frame per pair). For n=100 → 50 stack frames |

**Walk-through with n=4**: `swapPairs(1→2→3→4)` → `swapPairs(3→4)` → `swapPairs(null)` = 3 calls

### Approach 3: Array/Brute Force

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n) | One pass to collect, one pass to swap, one pass to rebuild |
| **Space** | O(n) | ArrayList stores all n values |

---

## 6. Complete Worked Examples

### Example: `1 → 2 → 3 → 4`

---

#### Approach 1 (Iterative) — Step-by-Step

**Initial state:**
```
dummy(0) → 1 → 2 → 3 → 4 → null
prev = dummy
```

**Iteration 1:**
```
first  = node(1)
second = node(2)

Step 1: first.next  = second.next  →  node(1).next = node(3)
Step 2: second.next = first        →  node(2).next = node(1)
Step 3: prev.next   = second       →  dummy.next   = node(2)
Move:   prev = first = node(1)

List: dummy → 2 → 1 → 3 → 4 → null
                    ↑
                   prev
```

**Iteration 2:**
```
first  = node(3)
second = node(4)

Step 1: first.next  = second.next  →  node(3).next = null
Step 2: second.next = first        →  node(4).next = node(3)
Step 3: prev.next   = second       →  node(1).next = node(4)
Move:   prev = first = node(3)

List: dummy → 2 → 1 → 4 → 3 → null
```

**Check loop condition:** `prev.next = null` → stop

**Return:** `dummy.next = node(2)` → **`2 → 1 → 4 → 3`** ✅

---

#### Approach 2 (Recursive) — Call Stack Trace

```
swapPairs(1→2→3→4)
│  first=1, second=2
│  first.next = swapPairs(3→4)
│              │  first=3, second=4
│              │  first.next = swapPairs(null) → returns null
│              │  node(3).next = null
│              │  node(4).next = node(3)
│              └─ returns node(4)    [4→3→null]
│  node(1).next = node(4)   [1→4→3→null]
│  node(2).next = node(1)   [2→1→4→3→null]
└─ returns node(2)
```

**Output: `2 → 1 → 4 → 3`** ✅

---

#### Odd-Length Example: `1 → 2 → 3` (Iterative)

**Initial:**
```
dummy → 1 → 2 → 3 → null
prev = dummy
```

**Iteration 1:**
```
first=1, second=2
node(1).next = node(3)
node(2).next = node(1)
dummy.next   = node(2)
prev = node(1)

List: dummy → 2 → 1 → 3 → null
                    ↑
                   prev
```

**Loop check:** `prev.next = node(3)`, `prev.next.next = null` → **condition fails, stop**

**Return:** `dummy.next` → **`2 → 1 → 3`** ✅ (node 3 untouched)

---

## 7. Edge Cases

| Edge Case | Input | Expected Output | Iterative Handling | Recursive Handling |
|-----------|-------|-----------------|-------------------|-------------------|
| Empty list | `null` | `null` | Loop never starts, `dummy.next = null` ✅ | Base case returns `null` ✅ |
| Single node | `1` | `1` | `prev.next.next = null` → loop skips ✅ | Base case returns `head` ✅ |
| Two nodes | `1→2` | `2→1` | One iteration, standard swap ✅ | One recursive call ✅ |
| Even length | `1→2→3→4` | `2→1→4→3` | Two full iterations ✅ | Two recursive levels ✅ |
| Odd length | `1→2→3` | `2→1→3` | Last lone node skipped by loop condition ✅ | Base case handles it ✅ |
| All same values | `5→5→5→5` | `5→5→5→5` | Works correctly (values irrelevant) ✅ | ✅ |
| Very long list (n=100) | — | — | O(1) space, safe ✅ | 50 stack frames, fine for n≤100 ✅ |

> **Stack overflow risk**: For the recursive approach, if n were very large (e.g., n = 100,000), the 50,000-deep call stack could cause a `StackOverflowError`. With n ≤ 100 here, it's completely safe.

---

## 8. Final Summary

### Comparison Table

| Approach | Time | Space | Code Simplicity | Interview Recommended |
|----------|------|-------|-----------------|----------------------|
| Iterative (dummy node) | O(n) | O(1) | Medium | ✅ **Yes** |
| Recursive | O(n) | O(n) | High (elegant) | ✅ Yes (explain tradeoff) |
| Array/brute force | O(n) | O(n) | Easy | ❌ Violates constraints |

### Recommendation
Use the **iterative approach** in interviews — it demonstrates careful pointer manipulation with O(1) space, which is exactly what interviewers want to see. If asked for an alternative, offer the recursive solution and proactively mention the O(n) stack space tradeoff.

### What to Remember
> **Pattern**: Linked list pair manipulation always needs a `dummy` node + a `prev` pointer to avoid head special-casing. The key insight is that after swapping a pair, the original `first` node becomes the new **tail** of the pair and must connect to the **result of the next swap** — this is why recursion feels so natural here.
    */
}
