package LinkedList;

import java.util.Arrays;

import Datastructures.Node;

public class ConvertDoublyLinkedListToArrayI {
    public static void main(String[] args) {
        ConvertDoublyLinkedListToArrayI convertDoublyLinkedListToArrayI = new ConvertDoublyLinkedListToArrayI();
        Node head = new Node(1);
        Node next = new Node(3, head, null);
        head.next = next;
        Node third = new Node(7, next, null);
        next.next = third;
        Node fourth = new Node(2, third, null);
        third.next = fourth;
        Node fifth = new Node(5, fourth, null);
        fourth.next = fifth;
        System.out.println(
                "ConvertDoublyLinkedListToArrayI : " + Arrays.toString(convertDoublyLinkedListToArrayI.toArray(head)));
    }

    /*
     * 1. Problem Statement
     * You are given a reference to the head node of a doubly linked list. Your task
     * is to convert this doubly linked list into a 1-indexed integer array (i.e., a
     * regular Java int[]) where each element in the array corresponds to the value
     * of the node at that position, preserving the original order from head to
     * tail.
     * Input Format
     * 
     * A Node head — the head of a doubly linked list.
     * Each Node has:
     * 
     * int val — the value stored.
     * Node prev — pointer to the previous node (null for head).
     * Node next — pointer to the next node (null for tail).
     * 
     * 
     * 
     * Output Format
     * 
     * An int[] array where result[0] holds the value of the head node, result[1]
     * holds the second node's value, and so on.
     * 
     * Constraints
     * 
     * The list may be empty (head is null) → return an empty array.
     * The list may have just one node.
     * Node values can be any valid integers (positive, negative, zero).
     * You must preserve the original order (head → tail).
     * 
     * What Needs to Be Computed
     * Walk the list from head to the last node (where next == null), collecting
     * each node's val in order, and return them as a fixed-size array.
     */

    public int[] toArray(Node head) {
        Node current = head;
        int nodeLength = 0;
        while (current != null) {
            nodeLength++;
            current = current.next;
        }

        int[] result = new int[nodeLength];
        current = head;
        int counter = 0;
        while (current != null) {
            result[counter] = current.val;
            current = current.next;
            counter++;
        }
        return result;
    }

    /*
    # Convert Doubly Linked List to Array I — Deep Dive

---

## 1. Problem Statement

You are given a reference to the **head node** of a **doubly linked list**. Your task is to convert this doubly linked list into a **1-indexed integer array** (i.e., a regular Java `int[]`) where each element in the array corresponds to the value of the node at that position, preserving the original order from head to tail.

### Input Format
- A `Node head` — the head of a doubly linked list.
- Each `Node` has:
  - `int val` — the value stored.
  - `Node prev` — pointer to the previous node (null for head).
  - `Node next` — pointer to the next node (null for tail).

### Output Format
- An `int[]` array where `result[0]` holds the value of the head node, `result[1]` holds the second node's value, and so on.

### Constraints
- The list may be empty (head is null) → return an empty array.
- The list may have just one node.
- Node values can be any valid integers (positive, negative, zero).
- You must preserve the original order (head → tail).

### What Needs to Be Computed
Walk the list from `head` to the last node (where `next == null`), collecting each node's `val` in order, and return them as a fixed-size array.

---

## 2. Intuition

Think of a doubly linked list like a **train with cars linked together**. Each car (node) knows the car in front of it (`next`) and the car behind it (`prev`). You want to write down the cargo (`val`) in each car in order, from the engine (head) to the caboose (tail).

### Human Reasoning
1. Start at the head.
2. Record the value.
3. Move to the next node.
4. Repeat until there are no more nodes.
5. Package all recorded values into an array.

### What Makes This Interesting
- Arrays have a **fixed size** — you must know the length before allocating. This means you either:
  - Make **two passes** (one to count, one to fill), or
  - Use a **dynamic structure** (like `ArrayList`) and convert at the end.
- The `prev` pointer is available but not needed for a simple head-to-tail traversal — recognizing which pointers are relevant is a key skill.

---

## 3. Approach Overview

| # | Approach | Key Idea | Best For |
|---|----------|----------|----------|
| 1 | Two-Pass Traversal | Count nodes first, then fill array | Memory efficiency, no extra collections |
| 2 | ArrayList + Convert | Collect in dynamic list, convert to array | Simplicity, interview speed |
| 3 | Tail-back traversal (via prev) | Walk to tail first, then backwards | Academic curiosity only |

### ✅ Optimal Approach
**Approach 1 (Two-Pass)** is optimal:
- **O(n) time**, **O(1) auxiliary space** (only the result array, no intermediate collection).
- Clean, interview-ready, and demonstrates understanding of linked list mechanics.

**Approach 2** is nearly as good and slightly simpler to write under pressure, but uses O(n) extra space for the `ArrayList`.

---

## 4. Detailed Solutions in Java

### Approach 1 — Two-Pass Traversal (Optimal)

#### Algorithm
1. Handle null head edge case → return empty array.
2. **Pass 1:** Traverse the entire list to count the number of nodes.
3. Allocate an `int[]` of exactly that size.
4. **Pass 2:** Traverse again from head, filling each index.
5. Return the filled array.

```java
class Solution {

    public int[] toArray(Node head) {
        // Edge case: empty list
        if (head == null) return new int[0];

        // Pass 1: Count total nodes
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }

        // Allocate result array of exact size
        int[] result = new int[count];

        // Pass 2: Fill array with node values
        current = head;
        int index = 0;
        while (current != null) {
            result[index++] = current.val;
            current = current.next;
        }

        return result;
    }
}
```

---

### Approach 2 — ArrayList + Convert

#### Algorithm
1. Handle null head edge case.
2. Walk the list once, appending each `val` to an `ArrayList<Integer>`.
3. Convert the `ArrayList` to an `int[]` using a stream or manual copy.
4. Return the array.

```java
import java.util.ArrayList;
import java.util.List;

class Solution {

    public int[] toArray(Node head) {
        if (head == null) return new int[0];

        List<Integer> values = new ArrayList<>();

        // Single pass: collect all values dynamically
        Node current = head;
        while (current != null) {
            values.add(current.val);
            current = current.next;
        }

        // Convert List<Integer> to int[]
        int[] result = new int[values.size()];
        for (int i = 0; i < values.size(); i++) {
            result[i] = values.get(i);
        }

        return result;
    }
}
```

> **Note:** You could also use `values.stream().mapToInt(Integer::intValue).toArray()` but the manual loop above avoids autoboxing overhead and is more transparent in interviews.

---

### Approach 3 — Tail-Back Traversal (Academic)

#### Algorithm
1. Walk forward to the **tail** node.
2. Walk **backwards** using `prev` pointers, filling the array from the end.

```java
class Solution {

    public int[] toArray(Node head) {
        if (head == null) return new int[0];

        // Walk to the tail and count
        int count = 0;
        Node tail = head;
        while (tail.next != null) {
            tail = tail.next;
            count++;
        }
        count++; // include the tail node itself

        int[] result = new int[count];

        // Walk backwards from tail to fill array
        Node current = tail;
        int index = count - 1;
        while (current != null) {
            result[index--] = current.val;
            current = current.prev;
        }

        return result;
    }
}
```

> This approach is mostly useful for demonstrating your awareness of `prev` pointers. In practice, prefer Approach 1.

---

## 5. Time & Space Complexity

### Approach 1 — Two-Pass

| | Complexity | Reasoning |
|--|------------|-----------|
| **Time** | O(n) | Two linear passes over n nodes = 2n operations → O(n) |
| **Space** | O(1) auxiliary | No intermediate data structure; result array is required output |

**Example:** For n = 1,000 nodes → ~2,000 pointer dereferences. For n = 1,000,000 → ~2,000,000 operations, still linear.

---

### Approach 2 — ArrayList

| | Complexity | Reasoning |
|--|------------|-----------|
| **Time** | O(n) | Single pass + O(n) conversion loop = O(n) |
| **Space** | O(n) auxiliary | `ArrayList` stores n `Integer` objects (boxed), then the final `int[]` also holds n elements → 2n extra allocations |

**Note:** The `ArrayList` uses autoboxing (`int` → `Integer`), which adds minor GC pressure for large lists.

---

### Approach 3 — Tail-Back

| | Complexity | Reasoning |
|--|------------|-----------|
| **Time** | O(n) | Forward pass to tail + backward pass = 2n = O(n) |
| **Space** | O(1) auxiliary | No intermediate structure beyond the result array |

Same asymptotic complexity as Approach 1, but fills array in reverse — more cognitive overhead for no gain.

---

## 6. Complete Worked Examples

### Example for Approach 1 — Two-Pass

**Input List:** `1 <-> 3 <-> 7 <-> 2 <-> 5`

#### Pass 1 — Count nodes

| Step | `current.val` | `count` |
|------|--------------|---------|
| 1 | 1 | 1 |
| 2 | 3 | 2 |
| 3 | 7 | 3 |
| 4 | 2 | 4 |
| 5 | 5 | 5 |
| 6 | null → stop | 5 |

→ Allocate `int[5]`

#### Pass 2 — Fill array

| Step | `current.val` | `index` | `result` state |
|------|--------------|---------|----------------|
| 1 | 1 | 0 | `[1, 0, 0, 0, 0]` |
| 2 | 3 | 1 | `[1, 3, 0, 0, 0]` |
| 3 | 7 | 2 | `[1, 3, 7, 0, 0]` |
| 4 | 2 | 3 | `[1, 3, 7, 2, 0]` |
| 5 | 5 | 4 | `[1, 3, 7, 2, 5]` |

**Output:** `[1, 3, 7, 2, 5]` ✅

---

### Example for Approach 2 — ArrayList

**Input List:** `10 <-> -4 <-> 0 <-> 99`

#### Single Pass

| Step | `current.val` | `values` (ArrayList) |
|------|--------------|----------------------|
| 1 | 10 | `[10]` |
| 2 | -4 | `[10, -4]` |
| 3 | 0 | `[10, -4, 0]` |
| 4 | 99 | `[10, -4, 0, 99]` |

#### Conversion to `int[]`

```
index 0 → 10
index 1 → -4
index 2 → 0
index 3 → 99
```

**Output:** `[10, -4, 0, 99]` ✅

---

### Example for Approach 3 — Tail-Back

**Input List:** `5 <-> 9 <-> 3`

#### Forward pass to tail

```
head = Node(5)
tail walk: 5 → 9 → 3 (next is null)
count = 3, tail = Node(3)
```

#### Backward fill

| Step | `current.val` | `index` | `result` state |
|------|--------------|---------|----------------|
| 1 | 3 | 2 | `[0, 0, 3]` |
| 2 | 9 | 1 | `[0, 9, 3]` |
| 3 | 5 | 0 | `[5, 9, 3]` |

**Output:** `[5, 9, 3]` ✅

---

## 7. Edge Cases

| Edge Case | What Happens | How Each Approach Handles It |
|-----------|-------------|------------------------------|
| `head == null` (empty list) | No nodes to traverse | All approaches return `new int[0]` immediately ✅ |
| Single node (`head.next == null`) | List has exactly 1 element | Count = 1, array = `[val]` — all approaches work ✅ |
| Negative values (e.g., `-999`) | Normal `int` storage | `int[]` handles full integer range — no issue ✅ |
| `Integer.MIN_VALUE` / `Integer.MAX_VALUE` | Boundary integer values | Stored directly in `int[]` — no overflow risk ✅ |
| Very large list (e.g., 10⁶ nodes) | Memory concern | Approach 1 uses O(1) aux space ✅; Approach 2 creates n `Integer` objects ⚠️ |
| All same values (e.g., `3 <-> 3 <-> 3`) | No uniqueness assumed | All approaches handle duplicates naturally ✅ |
| Broken `prev` pointers | `prev` chain is corrupt | Approaches 1 & 2 unaffected (only use `next`); Approach 3 would produce wrong results ❌ |

---

## 8. Final Summary

| Approach | Time | Aux Space | Simplicity | Recommended? |
|----------|------|-----------|------------|--------------|
| Two-Pass Traversal | O(n) | O(1) | Medium | ✅ **Yes** |
| ArrayList + Convert | O(n) | O(n) | High | ✅ Good for interviews |
| Tail-Back via `prev` | O(n) | O(1) | Low | ❌ No advantage |

### ✅ Recommended: Approach 1 (Two-Pass)
It is the most memory-efficient and demonstrates deliberate, clean thinking. Use **Approach 2** if you need to write fast in an interview and don't want to count nodes manually — both are perfectly acceptable.

### 🧠 Key Pattern to Remember
> **"When converting a linked list to an array, you need the size upfront. Either count first (two-pass, O(1) space) or accumulate dynamically (ArrayList, O(n) space). The `next` pointer alone is sufficient — `prev` is a distraction here."**

This problem is a foundational exercise in **linked list traversal** and the **fixed-size vs. dynamic collection tradeoff** — patterns that appear constantly in more complex problems like LRU Cache, Flatten Multilevel Lists, and Merge K Sorted Lists.
    */
}
