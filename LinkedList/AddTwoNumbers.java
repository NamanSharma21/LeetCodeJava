package LinkedList;

import Datastructures.ListNode;

public class AddTwoNumbers {
    public static void main(String[] args) {
        AddTwoNumbers addTwoNumbers = new AddTwoNumbers();
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);
        System.out.println("AddTwoNumbers : " + addTwoNumbers.addTwoNumbers(l1, l2));

        ListNode l11 = new ListNode(9);
        l11.next = new ListNode(9);
        l11.next.next = new ListNode(9);
        l11.next.next.next = new ListNode(9);
        l11.next.next.next.next = new ListNode(9);
        l11.next.next.next.next.next = new ListNode(9);
        l11.next.next.next.next.next.next = new ListNode(9);

        ListNode l22 = new ListNode(9);
        l22.next = new ListNode(9);
        l22.next.next = new ListNode(9);
        l22.next.next.next = new ListNode(9);
        System.out.println("AddTwoNumbers : " + addTwoNumbers.addTwoNumbers(l11, l22));
    }

    /*
     * https://leetcode.com/explore/interview/card/top-interview-questions-medium/
     * 107/linked-list/783/
     * 
     * 
     * You are given two non-empty linked lists representing two non-negative
     * integers. The digits are stored in reverse order, and each of their nodes
     * contains a single digit. Add the two numbers and return the sum as a linked
     * list.
     * 
     * You may assume the two numbers do not contain any leading zero, except the
     * number 0 itself.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: l1 = [2,4,3], l2 = [5,6,4]
     * Output: [7,0,8]
     * Explanation: 342 + 465 = 807.
     * Example 2:
     * 
     * Input: l1 = [0], l2 = [0]
     * Output: [0]
     * Example 3:
     * 
     * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
     * Output: [8,9,9,9,0,0,0,1]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in each linked list is in the range [1, 100].
     * 0 <= Node.val <= 9
     * It is guaranteed that the list represents a number that does not have leading
     * zeros.
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode sumNode = new ListNode(0);
        ListNode current = sumNode;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int val1 = l1 != null ? l1.val : 0;
            int val2 = l2 != null ? l2.val : 0;
            int sum = carry + val1 + val2;
            carry = sum / 10;
            int digit = sum % 10;
            current.next = new ListNode(digit);
            current = current.next;
            if (l1 != null)
                l1 = l1.next;
            if (l2 != null)
                l2 = l2.next;
        }
        return sumNode.next;
    }

    /*
    # Add Two Numbers — Deep Dive

## 1. Problem Statement

### In Plain English
You are given two non-empty linked lists, each representing a **non-negative integer** stored in **reverse order** (the least significant digit comes first). Each node contains a single digit. Add the two numbers and return the result as a linked list, also in reverse order.

### Input Format
- Two singly linked lists: `l1` and `l2`
- Each node holds one digit (0–9)
- Digits are stored in **reverse order** (ones place → tens place → hundreds place...)
- Neither list is empty

### Output Format
- A new singly linked list representing the sum, also in **reverse order**

### Constraints (LeetCode #2)
- Number of nodes in each list: `[1, 100]`
- Each node value: `0 ≤ Node.val ≤ 9`
- No leading zeros (except the number `0` itself)

### What Exactly Must Be Returned
A reference to the **head** of the resulting linked list.

**Example:**
```
l1: 2 → 4 → 3       represents 342
l2: 5 → 6 → 4       represents 465
Sum:                          807
Output: 7 → 0 → 8
```

---

## 2. Intuition

### The Core Idea
Think about how you add two numbers **by hand**, column by column, right to left:
```
  342
+ 465
-----
  807
```
You start at the **rightmost** digit (ones), add, carry if ≥ 10, move left. Since the lists are already stored in reverse (ones digit first), you can just **traverse both lists simultaneously from the head**, simulating exactly this grade-school addition process.

### Human Reasoning Step by Step
1. Start at the head of both lists (ones digit).
2. Add the two digits plus any carry from the previous step.
3. The current node's value = `sum % 10`.
4. The carry forward = `sum / 10`.
5. Advance both pointers.
6. Repeat until both lists are exhausted **and** carry is zero.

### What Makes This Tricky
| Challenge | Why It's Tricky |
|---|---|
| Unequal list lengths | One list ends before the other; must treat missing nodes as 0 |
| Final carry | After both lists end, a leftover carry means one more node is needed |
| Linked list construction | Must build the output list cleanly without off-by-one errors |
| No leading zeros in input | Means each list is a valid number, simplifying parsing |

---

## 3. Approach Overview

| # | Approach | Key Idea | When to Use |
|---|---|---|---|
| 1 | **Convert → Add → Convert** | Extract numbers, add with `BigInteger`, rebuild list | Never in interviews; conceptually simple but fragile |
| 2 | **Simulated Addition (Optimal)** | Traverse both lists simultaneously, simulate carry arithmetic | Always — this is the intended and optimal solution |

### Why Approach 1 is Inferior
- Up to 100 nodes → number can have 100 digits, far exceeding `long` (19 digits max). Requires `BigInteger`, which is slow and non-idiomatic.
- Converting back to a linked list adds complexity with no benefit.

### ✅ Optimal: Approach 2
Simulated addition is O(max(m, n)) time, O(max(m, n)) space, and directly mirrors how addition works — elegant, efficient, interview-ready.

---

## 4. Detailed Solutions in Java

### Approach 1: Convert → BigInteger → Rebuild (Brute Force)

#### Algorithm
1. Walk each linked list, reconstruct the number as a string (reverse the digits since list is already reversed → read front to back gives ones, tens... so append normally then reverse).
2. Convert both strings to `BigInteger`.
3. Add them.
4. Convert the result string into a new linked list (again in reverse order).

```java
import java.math.BigInteger;

class Solution {
    public ListNode addTwoNumbers_BruteForce(ListNode l1, ListNode l2) {
        // Step 1: Extract digits (already in reverse → reading forward gives ones,tens,...)
        //         We need to reverse to get the actual number string
        String num1 = extractReversedNumber(l1);
        String num2 = extractReversedNumber(l2);

        // Step 2: Add using BigInteger (handles arbitrarily large numbers)
        BigInteger sum = new BigInteger(num1).add(new BigInteger(num2));
        String sumStr = sum.toString(); // Most significant digit first

        // Step 3: Build result list in reverse order (ones digit first)
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int i = sumStr.length() - 1; i >= 0; i--) {
            current.next = new ListNode(sumStr.charAt(i) - '0');
            current = current.next;
        }
        return dummy.next;
    }

    private String extractReversedNumber(ListNode node) {
        StringBuilder sb = new StringBuilder();
        while (node != null) {
            sb.append(node.val); // appending ones digit first
            node = node.next;
        }
        // sb currently = "ones tens hundreds..." → reverse → "hundreds tens ones"
        return sb.reverse().toString();
    }
}
```

---

### ✅ Approach 2: Simulated Column-by-Column Addition (Optimal)

#### Algorithm — Step by Step
1. Create a **dummy head** node so you never have to handle the "first node" as a special case.
2. Keep a `current` pointer and a `carry` variable (starts at 0).
3. Loop while `l1 != null OR l2 != null OR carry != 0`.
4. Inside the loop:
   - Get `val1` from `l1` (or 0 if `l1` is null).
   - Get `val2` from `l2` (or 0 if `l2` is null).
   - `total = val1 + val2 + carry`
   - `carry = total / 10`
   - Create a new node with value `total % 10`, attach to `current.next`.
   - Advance `current`, `l1`, `l2`.
5. Return `dummy.next`.

```java
class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }
}

class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0); // Sentinel to simplify head handling
        ListNode current = dummy;
        int carry = 0;

        // Continue while either list has nodes OR there's a pending carry
        while (l1 != null || l2 != null || carry != 0) {
            int val1 = (l1 != null) ? l1.val : 0; // Treat exhausted list as 0
            int val2 = (l2 != null) ? l2.val : 0;

            int total = val1 + val2 + carry;
            carry = total / 10;          // Will be 0 or 1 (max digit sum = 9+9+1=19)
            int digit = total % 10;      // The digit to store in this position

            current.next = new ListNode(digit);
            current = current.next;

            // Advance list pointers only if they still have nodes
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        return dummy.next; // dummy.next is the true head of the result
    }
}
```

---

## 5. Time & Space Complexity

### Approach 1: BigInteger
| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(m + n) | Traverse both lists once; BigInteger addition is proportional to digit count |
| **Space** | O(m + n) | Strings and BigInteger objects proportional to list lengths |

### ✅ Approach 2: Simulated Addition
| | Complexity | Reasoning |
|---|---|---|
| **Time** | **O(max(m, n))** | We iterate once through both lists together; the longer list determines the loop count |
| **Space** | **O(max(m, n))** | Output list has at most max(m,n)+1 nodes (the +1 for a possible final carry) |

#### Concrete Walk-Through of Operations
- `l1` has 3 nodes, `l2` has 4 nodes → loop runs **4 times** (possibly 5 if final carry).
- At 100 nodes each → exactly ~100 iterations, ~100 node creations. Extremely fast.

---

## 6. Complete Worked Examples

### Example 1 — Approach 2 (Different Lengths + Carry)
```
l1: 9 → 9 → 9      (represents 999)
l2: 1              (represents 1)
Expected: 0 → 0 → 0 → 1  (represents 1000)
```

| Iteration | l1.val | l2.val | carry_in | total | digit | carry_out | Output so far |
|---|---|---|---|---|---|---|---|
| 1 | 9 | 1 | 0 | 10 | **0** | 1 | 0 → |
| 2 | 9 | 0 (null) | 1 | 10 | **0** | 1 | 0 → 0 → |
| 3 | 9 | 0 (null) | 1 | 10 | **0** | 1 | 0 → 0 → 0 → |
| 4 | null | null | 1 | 1 | **1** | 0 | 0 → 0 → 0 → 1 |

**Result:** `0 → 0 → 0 → 1` ✅ (represents 1000)

---

### Example 2 — Approach 2 (Standard Case)
```
l1: 2 → 4 → 3   (342)
l2: 5 → 6 → 4   (465)
Expected: 7 → 0 → 8  (807)
```

| Iteration | l1.val | l2.val | carry_in | total | digit | carry_out |
|---|---|---|---|---|---|---|
| 1 | 2 | 5 | 0 | 7 | **7** | 0 |
| 2 | 4 | 6 | 0 | 10 | **0** | 1 |
| 3 | 3 | 4 | 1 | 8 | **8** | 0 |

**Result:** `7 → 0 → 8` ✅

---

### Example 3 — Brute Force Approach (Same as Example 2)
- `l1` traversal: `2 → 4 → 3` → `sb = "243"` → reversed = `"342"`
- `l2` traversal: `5 → 6 → 4` → `sb = "564"` → reversed = `"465"`
- `BigInteger("342") + BigInteger("465") = 807`
- `sumStr = "807"`, build list right to left: `7 → 0 → 8` ✅

---

## 7. Edge Cases

| Edge Case | Description | How Approach 2 Handles It | Approach 1 Risk |
|---|---|---|---|
| **Different lengths** | `l1 = [1]`, `l2 = [9,9,9]` | Missing node treated as 0 via ternary check | Handled by `BigInteger` naturally |
| **Final carry** | `[9,9]` + `[1]` = `[0,0,1]` | Loop continues while `carry != 0` | Handled by string arithmetic |
| **Both single digits, no carry** | `[3]` + `[4]` = `[7]` | One iteration, carry stays 0 | Works fine |
| **Both single digits, carry** | `[9]` + `[9]` = `[8,1]` | carry = 1, loop runs a second time | Works fine |
| **Large numbers (100 nodes)** | 100-digit numbers | ✅ Fully safe, purely arithmetic | ✅ BigInteger handles it, but slow |
| **Adding zero** | `[0]` + `[7]` = `[7]` | val1=0, total=7, carry=0, done | Works |
| **Overflow with `int`** | Max digit sum = 9+9+1 = **19** | Fits in `int` easily — no overflow possible | BigInteger immune |

### Key Insight on Overflow
The maximum possible value in `total` at any step is `9 + 9 + 1 = 19`. This **never overflows** an `int`. Carry can only ever be `0` or `1`.

---

## 8. Final Summary

| | Brute Force (BigInteger) | ✅ Simulated Addition |
|---|---|---|
| Time | O(m + n) | O(max(m, n)) |
| Space | O(m + n) | O(max(m, n)) |
| Code simplicity | Moderate (string gymnastics) | Clean and elegant |
| Interview suitability | ❌ Not recommended | ✅ Ideal |
| Overflow risk | None (BigInteger) | None (max sum = 19) |

### ✅ Recommended Approach: Simulated Addition
It directly models how addition works, needs no data conversion, handles all edge cases naturally, and is exactly what interviewers want to see.

### What to Remember
> **This is a linked list traversal + carry arithmetic problem.** The key patterns are: (1) use a **dummy head node** to simplify list construction, and (2) use a `while (l1 != null || l2 != null || carry != 0)` loop to handle unequal lengths and the final carry in one clean condition. These two patterns appear repeatedly in linked list problems.
    */
}
