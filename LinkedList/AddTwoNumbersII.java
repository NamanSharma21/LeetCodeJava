package LinkedList;

import java.util.ArrayDeque;
import java.util.Deque;

import Datastructures.ListNode;

public class AddTwoNumbersII {
    public static void main(String[] args) {
        AddTwoNumbersII addTwoNumbersII = new AddTwoNumbersII();
        ListNode l1 = new ListNode(7);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(4);
        l1.next.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);
        System.out.println("AddTwoNumbersII : " + addTwoNumbersII.addTwoNumbersApproachStack(l1, l2));
    }

    /*
     * https://leetcode.com/problems/add-two-numbers-ii/description/?envType=problem
     * -list-v2&envId=linked-list
     * 
     * 
     * You are given two non-empty linked lists representing two non-negative
     * integers. The most significant digit comes first and each of their nodes
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
     * Input: l1 = [7,2,4,3], l2 = [5,6,4]
     * Output: [7,8,0,7]
     * Example 2:
     * 
     * Input: l1 = [2,4,3], l2 = [5,6,4]
     * Output: [8,0,7]
     * Example 3:
     * 
     * Input: l1 = [0], l2 = [0]
     * Output: [0]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in each linked list is in the range [1, 100].
     * 0 <= Node.val <= 9
     * It is guaranteed that the list represents a number that does not have leading
     * zeros.
     * 
     * 
     * Follow up: Could you solve it without reversing the input lists?
     */
    public ListNode addTwoNumbersApproach1(ListNode l1, ListNode l2) {
        ListNode r1 = null;
        while (l1 != null) {
            ListNode next = l1.next;
            l1.next = r1;
            r1 = l1;
            l1 = next;
        }

        ListNode r2 = null;
        while (l2 != null) {
            ListNode next = l2.next;
            l2.next = r2;
            r2 = l2;
            l2 = next;
        }

        ListNode resultHead = null;
        int carry = 0;
        while (r1 != null || r2 != null || carry != 0) {
            int val1 = r1 == null ? 0 : r1.val;
            int val2 = r2 == null ? 0 : r2.val;
            int sum = val1 + val2 + carry;
            carry = sum / 10;
            int digit = sum % 10;
            ListNode nextNode = new ListNode(digit);
            nextNode.next = resultHead;
            resultHead = nextNode;
            if (r1 != null)
                r1 = r1.next;

            if (r2 != null)
                r2 = r2.next;
        }
        return resultHead;
    }

    public ListNode addTwoNumbersApproachStack(ListNode l1, ListNode l2) {
        Deque<ListNode> stack1 = new ArrayDeque<>();
        Deque<ListNode> stack2 = new ArrayDeque<>();
        ListNode curr = l1;
        while (curr != null) {
            stack1.push(curr);
            curr = curr.next;
        }
        curr = l2;
        while (curr != null) {
            stack2.push(curr);
            curr = curr.next;
        }

        ListNode resultHead = null;
        int carry = 0;
        while (!stack1.isEmpty() || !stack2.isEmpty() || carry != 0) {
            int val1 = !stack1.isEmpty() ? stack1.pop().val : 0;
            int val2 = !stack2.isEmpty() ? stack2.pop().val : 0;

            int sum = val1 + val2 + carry;
            int digit = sum % 10;
            carry = sum / 10;

            ListNode nextNode = new ListNode(digit);
            nextNode.next = resultHead;
            resultHead = nextNode;
        }
        return resultHead;
    }


    /*
    
    # Add Two Numbers II — Deep Dive

---

## 1. Problem Statement

### What the problem says (in plain English):
You are given two **non-empty linked lists** representing two **non-negative integers**. The digits are stored in **most-significant-digit-first** order (i.e., the head of the list holds the most significant digit). The two numbers may have **different lengths**. You must add the two numbers and return the result as a linked list, also in most-significant-digit-first order.

> **No reversing the input lists is allowed** (as per the spirit of the problem — though some solutions do reverse for simplicity).

### Input Format:
- `ListNode l1` — head of the first linked list
- `ListNode l2` — head of the second linked list
- Each node contains a single digit `[0–9]`
- No leading zeros (except the number `0` itself)

### Output Format:
- A `ListNode` — head of a new linked list representing the sum

### Constraints:
- Number of nodes: `[1, 100]`
- Node values: `[0, 9]`
- Lists are non-empty
- No leading zeros in either input

### What exactly needs to be computed:
Given `l1 = 7 → 2 → 4 → 3` and `l2 = 5 → 6 → 4`:

```
  7243
+  564
------
  7807
```
Return: `7 → 8 → 0 → 7`

---

## 2. Intuition

### Core Idea:
Elementary school addition — you add digits **from right to left** and carry over. But the linked lists give us digits **left to right**. That's the central challenge.

### How a human reasons about this:
1. You'd naturally want to start adding from the **last digits** (ones place) first.
2. But linked lists only give you forward traversal.
3. So the key insight is: **reverse access** — either literally reverse the lists, or use a **stack** to simulate backward traversal.

### What makes this tricky:
- Lists can have **different lengths** — you must align them from the right.
- **Carry propagation** can extend the result (e.g., `999 + 1 = 1000` — one extra digit).
- You must build the result list in **forward (MSB-first)** order, but you compute in **reverse (LSB-first)** order.

---

## 3. Approach Overview

| # | Approach | Key Idea | Time | Space | Use When |
|---|----------|----------|------|-------|----------|
| 1 | **Reverse Lists** | Reverse both lists, add, reverse result | O(n+m) | O(1) extra | Modification allowed |
| 2 | **Stacks** | Push both lists onto stacks, pop to add | O(n+m) | O(n+m) | Clean interview solution |
| 3 | **Convert to Number** | Parse to long, add, rebuild list | O(n+m) | O(1) | Only for small inputs |

### ✅ Optimal Approach: **Stack-based (Approach 2)**
- Does **not** modify input lists (interviewer-friendly).
- Handles different lengths **elegantly**.
- Handles carry and result construction naturally.
- Clear, readable, and O(n+m) in both time and space.

---

## 4. Detailed Solutions in Java

---

### Approach 1 — Reverse Lists

#### Algorithm:
1. Reverse both `l1` and `l2` in-place.
2. Walk both lists simultaneously, adding digit by digit with carry.
3. Build result list **prepending** each new node (so the result is MSB-first).
4. Optionally re-reverse the inputs to restore them.

#### Code:
```java
public class AddTwoNumbersII_Reverse {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // Step 1: Reverse both input lists
        l1 = reverse(l1);
        l2 = reverse(l2);

        ListNode resultHead = null;
        int carry = 0;

        // Step 2: Add digit by digit from LSB to MSB
        while (l1 != null || l2 != null || carry != 0) {
            int digit1 = (l1 != null) ? l1.val : 0;
            int digit2 = (l2 != null) ? l2.val : 0;

            int sum = digit1 + digit2 + carry;
            carry = sum / 10;
            int currentDigit = sum % 10;

            // Prepend new node so result is MSB-first
            ListNode newNode = new ListNode(currentDigit);
            newNode.next = resultHead;
            resultHead = newNode;

            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        return resultHead;
    }

    private ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }
        return prev;
    }
}
```

---

### Approach 2 — Stacks (⭐ Optimal / Recommended)

#### Algorithm:
1. Push all digits of `l1` onto `stack1`, all digits of `l2` onto `stack2`.
2. Pop from both stacks simultaneously, adding with carry.
3. Prepend each resulting node to build MSB-first result list.
4. If carry remains after both stacks are empty, prepend a `1` node.

#### Code:
```java
import java.util.Deque;
import java.util.ArrayDeque;

public class AddTwoNumbersII_Stack {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // Step 1: Load both lists into stacks (top = least significant digit)
        Deque<Integer> stack1 = new ArrayDeque<>();
        Deque<Integer> stack2 = new ArrayDeque<>();

        ListNode curr = l1;
        while (curr != null) {
            stack1.push(curr.val);
            curr = curr.next;
        }

        curr = l2;
        while (curr != null) {
            stack2.push(curr.val);
            curr = curr.next;
        }

        // Step 2: Add from LSB to MSB using stacks
        ListNode resultHead = null;
        int carry = 0;

        while (!stack1.isEmpty() || !stack2.isEmpty() || carry != 0) {
            int digit1 = stack1.isEmpty() ? 0 : stack1.pop();
            int digit2 = stack2.isEmpty() ? 0 : stack2.pop();

            int sum = digit1 + digit2 + carry;
            carry = sum / 10;
            int currentDigit = sum % 10;

            // Prepend to build result in MSB-first order
            ListNode newNode = new ListNode(currentDigit);
            newNode.next = resultHead;
            resultHead = newNode;
        }

        return resultHead;
    }
}
```

---

### Approach 3 — Convert to Long (Simple but Limited)

#### Algorithm:
1. Traverse `l1` and build the number as a `long`.
2. Traverse `l2` and build the number as a `long`.
3. Add them.
4. Convert the sum back to a linked list digit by digit.

#### Code:
```java
public class AddTwoNumbersII_Convert {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        long num1 = toNumber(l1);
        long num2 = toNumber(l2);
        long total = num1 + num2;

        // Edge case: total is 0
        if (total == 0) return new ListNode(0);

        // Build result list by prepending (gives MSB-first order)
        ListNode resultHead = null;
        while (total > 0) {
            ListNode newNode = new ListNode((int)(total % 10));
            newNode.next = resultHead;
            resultHead = newNode;
            total /= 10;
        }

        return resultHead;
    }

    private long toNumber(ListNode head) {
        long number = 0;
        while (head != null) {
            number = number * 10 + head.val;
            head = head.next;
        }
        return number;
    }
}
```

> ⚠️ **Limitation**: Overflows if list length > ~18 digits. `long` can hold at most ~19 digits. With 100 nodes per list, this **will overflow**.

---

## 5. Time & Space Complexity

### Approach 1 — Reverse Lists

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n + m) | One pass to reverse each list, one pass to add |
| **Space** | O(1) extra | Reversal is in-place; result nodes are required output |

**Example:** `l1` has 4 nodes, `l2` has 3 nodes → ~14 operations total (4+3 reverse + 4 add + 3 add).

---

### Approach 2 — Stacks ⭐

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n + m) | One pass per list to push, one combined pass to pop and add |
| **Space** | O(n + m) | Two stacks storing all digits of both lists |

**Example:** `l1` = 100 nodes, `l2` = 100 nodes → stacks hold 200 integers, ~200 push + ~100 pop operations.

---

### Approach 3 — Convert to Long

| | Complexity | Reasoning |
|---|---|---|
| **Time** | O(n + m) | One pass per list to build number, one pass to rebuild list |
| **Space** | O(1) extra | Only two `long` variables; result is required output |

> ❌ Unsafe for lists longer than ~18 nodes due to overflow.

---

## 6. Complete Worked Examples

---

### Example for Approach 2 (Stacks) — Primary walkthrough

**Input:**
```
l1: 7 → 2 → 4 → 3   (represents 7243)
l2: 5 → 6 → 4        (represents  564)
Expected output: 7 → 8 → 0 → 7  (represents 7807)
```

#### Step 1: Build stacks

```
stack1 (top→bottom): 3 | 4 | 2 | 7
stack2 (top→bottom): 4 | 6 | 5
```

#### Step 2: Pop and add

| Iteration | digit1 (stack1) | digit2 (stack2) | carry in | sum | carry out | currentDigit | resultHead |
|-----------|----------------|----------------|----------|-----|-----------|--------------|------------|
| 1 | 3 | 4 | 0 | 7 | 0 | 7 | `7` |
| 2 | 4 | 6 | 0 | 10 | 1 | 0 | `0→7` |
| 3 | 2 | 5 | 1 | 8 | 0 | 8 | `8→0→7` |
| 4 | 7 | 0 (empty) | 0 | 7 | 0 | 7 | `7→8→0→7` |

#### Final Output: `7 → 8 → 0 → 7` ✅

---

### Example with carry overflow

**Input:**
```
l1: 9 → 9 → 9   (represents 999)
l2: 1            (represents   1)
Expected output: 1 → 0 → 0 → 0  (represents 1000)
```

#### Stacks:
```
stack1: 9 | 9 | 9
stack2: 1
```

| Iteration | digit1 | digit2 | carry in | sum | carry out | currentDigit | resultHead |
|-----------|--------|--------|----------|-----|-----------|--------------|------------|
| 1 | 9 | 1 | 0 | 10 | 1 | 0 | `0` |
| 2 | 9 | 0 | 1 | 10 | 1 | 0 | `0→0` |
| 3 | 9 | 0 | 1 | 10 | 1 | 0 | `0→0→0` |
| 4 | 0 (empty) | 0 (empty) | 1 | 1 | 0 | 1 | `1→0→0→0` |

#### Final Output: `1 → 0 → 0 → 0` ✅

---

### Example for Approach 1 (Reverse Lists)

**Input:**
```
l1: 2 → 4 → 3   (represents 243)
l2: 5 → 6 → 4   (represents 564)
Expected: 8 → 0 → 7  (represents 807)
```

#### After reversal:
```
l1: 3 → 4 → 2
l2: 4 → 6 → 5
```

#### Addition (LSB first, prepend each node):

| Step | digit1 | digit2 | carry | sum | resultHead |
|------|--------|--------|-------|-----|------------|
| 1 | 3 | 4 | 0 | 7 | `7` |
| 2 | 4 | 6 | 0 | 10 | `0→7` |
| 3 | 2 | 5 | 1 | 8 | `8→0→7` |

#### Final Output: `8 → 0 → 7` ✅

---

## 7. Edge Cases

| Edge Case | Description | Stack Approach | Reverse Approach | Convert Approach |
|-----------|-------------|----------------|------------------|------------------|
| **Same length lists** | `999 + 111` | ✅ Handles naturally | ✅ Handles naturally | ✅ Fine |
| **Different lengths** | `7243 + 564` | ✅ Empty stack returns 0 | ✅ Null node returns 0 | ✅ Fine |
| **Carry at MSB** | `999 + 1 = 1000` | ✅ `carry != 0` loop condition | ✅ Same condition | ✅ Fine |
| **Single node each** | `5 + 5 = 10` | ✅ carry creates extra node | ✅ Same | ✅ Fine |
| **One list is `0`** | `0 + 123 = 123` | ✅ 0 + digit = digit | ✅ Same | ✅ But edge in rebuild |
| **Both lists are `0`** | `0 + 0 = 0` | ✅ returns `0` node | ✅ returns `0` node | ⚠️ Needs special `if (total == 0)` check |
| **100-node lists** | Max constraint | ✅ O(n+m) safe | ✅ O(n+m) safe | ❌ **OVERFLOW** with `long` |
| **All 9s** | `9999...9 + 1` | ✅ Carry chain handled | ✅ Same | ❌ Overflow at 19+ digits |

### Key handling notes:
- **Carry after loop ends**: All three approaches use `carry != 0` as a loop continuation condition — this handles the extra digit case like `999 + 1 = 1000`.
- **Different lengths**: The stack approach handles this with `stack.isEmpty() ? 0 : stack.pop()` — a very clean pattern.
- **Input modification**: Approach 1 **mutates** the input lists during reversal. In an interview, always ask if input modification is allowed.

---

## 8. Final Summary

### Comparison Table

| Approach | Time | Space | Modifies Input | Safe for 100 nodes | Interview-Friendly |
|----------|------|-------|----------------|-------------------|-------------------|
| Reverse Lists | O(n+m) | O(1) | ✅ Yes | ✅ Yes | ⚠️ Ask permission |
| **Stacks** ⭐ | O(n+m) | O(n+m) | ❌ No | ✅ Yes | ✅ Best choice |
| Convert to Long | O(n+m) | O(1) | ❌ No | ❌ No (overflow) | ❌ Avoid |

### ✅ Recommended: Stack Approach
- Doesn't mutate inputs.
- Handles all edge cases cleanly.
- Code is concise and easy to explain in an interview.

### 🧠 What to Remember:
> **When you need to process a singly linked list in reverse order without actually reversing it — use a Stack.** This is a classic and reusable pattern that appears in many linked list problems (palindrome check, reverse traversal, etc.).

The secondary insight is the **prepend trick** for building a result list in forward order while computing in reverse — instead of appending and reversing at the end, simply set `newNode.next = resultHead` and update `resultHead = newNode`.
    */
}
