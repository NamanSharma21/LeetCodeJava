package LinkedList;

import Datastructures.ListNode;

public class ConvertBinaryNumberInALinkedListToInteger {
    public static void main(String[] args) {
        ConvertBinaryNumberInALinkedListToInteger convertBinaryNumberInALinkedListToInteger = new ConvertBinaryNumberInALinkedListToInteger();
        ListNode head = new ListNode(1);
        head.next = new ListNode(0);
        head.next.next = new ListNode(1);
        System.out.println("ConvertBinaryNumberInALinkedListToInteger : "
                + convertBinaryNumberInALinkedListToInteger.getDecimalValue(head));
    }

    /*
     * https://leetcode.com/problems/convert-binary-number-in-a-linked-list-to-
     * integer/description/?envType=problem-list-v2&envId=linked-list
     * 
     * Given head which is a reference node to a singly-linked list. The value of
     * each node in the linked list is either 0 or 1. The linked list holds the
     * binary representation of a number.
     * 
     * Return the decimal value of the number in the linked list.
     * 
     * The most significant bit is at the head of the linked list.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: head = [1,0,1]
     * Output: 5
     * Explanation: (101) in base 2 = (5) in base 10
     * Example 2:
     * 
     * Input: head = [0]
     * Output: 0
     * 
     * 
     * Constraints:
     * 
     * The Linked List is not empty.
     * Number of nodes will not exceed 30.
     * Each node's value is either 0 or 1.
     */

    public int getDecimalValue(ListNode head) {
        int result = 0;
        ListNode current = head;
        while (current != null) {
            result = (result << 1) | current.val;
            current = current.next;
        }
        return result;
    }

    /*

    # Convert Binary Number in a Linked List to Integer

---

## Problem Statement

You are given the head of a singly linked list where each node contains a single digit — either `0` or `1`. The entire linked list represents a binary number, with the **most significant bit (MSB) at the head** and the **least significant bit (LSB) at the tail**.

**Input:** `head` — the head node of a singly linked list  
**Output:** An `int` representing the decimal value of the binary number encoded in the list  
**Constraints:**
- The number of nodes is in the range `[1, 30]`
- Each `Node.val` is either `0` or `1`
- The answer fits in a 32-bit integer (guaranteed by the constraint of max 30 nodes)

**What to compute:** Traverse the linked list, read the binary number it represents (MSB first), and return its integer equivalent.

---

## Intuition

Think of how binary numbers work: `1 → 0 → 1` represents `101` in binary, which is `5` in decimal.

A human would naturally:
1. Read all the bits from head to tail to get the binary string
2. Convert that string to a decimal number

The elegant trick is you don't need to know the length upfront. As you walk the list node by node, you can **build the number incrementally** using the classic shift-and-add pattern:

> At each step: `result = result * 2 + current_bit`

This works because multiplying by 2 in binary is the same as a left shift — it makes room for the next bit. This is the same technique used to parse binary strings character by character.

What makes this interesting: it elegantly avoids needing to know the list length, collect all bits first, or use `Math.pow`.

---

## Approach Overview

| # | Approach | Key Idea | Best For |
|---|----------|----------|----------|
| 1 | String Building | Collect bits as a string, use `Integer.parseInt` | Clarity/readability |
| 2 | Collect into List/Array | Store bits, then compute with positional powers | Understanding the math explicitly |
| 3 | Bit Shifting (Optimal) | Shift-and-add in a single pass | Interviews, production |

**Optimal: Approach 3** — single pass, O(1) space, no auxiliary structures needed.

---

## Detailed Solutions in Java

### Approach 1: String Building

**Algorithm:**
1. Traverse the list, appending each `val` to a `StringBuilder`
2. Use `Integer.parseInt(binaryString, 2)` to convert to decimal

```java
class Solution {
    public int getDecimalValue(ListNode head) {
        StringBuilder binaryStr = new StringBuilder();
        
        ListNode current = head;
        while (current != null) {
            binaryStr.append(current.val); // build the binary string
            current = current.next;
        }
        
        // parse the binary string as base-2 integer
        return Integer.parseInt(binaryStr.toString(), 2);
    }
}
```

**When to use:** Great for readability; delegates the conversion math to the standard library.

---

### Approach 2: Collect Bits, Then Compute

**Algorithm:**
1. Traverse and store all bits in a `List<Integer>`
2. Iterate in reverse with positional powers of 2

```java
class Solution {
    public int getDecimalValue(ListNode head) {
        List<Integer> bits = new ArrayList<>();
        
        ListNode current = head;
        while (current != null) {
            bits.add(current.val);
            current = current.next;
        }
        
        int result = 0;
        int length = bits.size();
        
        for (int i = 0; i < length; i++) {
            // bit at index i has positional value 2^(length-1-i)
            result += bits.get(i) * (1 << (length - 1 - i));
        }
        
        return result;
    }
}
```

**When to use:** Good for understanding the positional math explicitly; not space-optimal.

---

### Approach 3: Bit Shifting — Optimal ✅

**Algorithm:**
1. Initialize `result = 0`
2. For each node: `result = (result << 1) | node.val`
   - Left shift makes room for the new bit
   - OR (or +) places the new bit in the lowest position
3. Return `result`

```java
class Solution {
    public int getDecimalValue(ListNode head) {
        int result = 0;
        
        ListNode current = head;
        while (current != null) {
            result = (result << 1) | current.val; // shift left and add new bit
            current = current.next;
        }
        
        return result;
    }
}
```

**Why `<<` and `|`?**
- `result << 1` is equivalent to `result * 2` — shifts all existing bits one position left
- `| current.val` sets the lowest bit to the current node's value (0 or 1)

---

## Time & Space Complexity

| Approach | Time | Space | Reasoning |
|----------|------|-------|-----------|
| String Building | O(n) | O(n) | One traversal; string stores n characters |
| Collect + Compute | O(n) | O(n) | One traversal to collect; one pass to compute; list holds n elements |
| Bit Shifting | O(n) | O(1) | One traversal; only `result` integer is maintained |

**Example walkthrough:** For a list of length 5 (`1→0→1→1→0`):
- All approaches do exactly 5 node visits
- Approach 3 does 5 shift-and-OR operations with no allocations

---

## Complete Worked Examples

### Example: `1 → 0 → 1` (binary `101` = decimal `5`)

#### Approach 3 — Bit Shifting

| Step | Node Val | Operation | `result` (binary) | `result` (decimal) |
|------|----------|-----------|-------------------|---------------------|
| Start | — | init | `0` | 0 |
| 1 | 1 | `(0 << 1) \| 1` | `1` | 1 |
| 2 | 0 | `(1 << 1) \| 0` | `10` | 2 |
| 3 | 1 | `(10 << 1) \| 1` | `101` | **5** |

**Output: `5`** ✅

---

### Example: `1 → 1 → 1` (binary `111` = decimal `7`)

#### Approach 1 — String Building

- Traversal builds string: `"111"`
- `Integer.parseInt("111", 2)` = **7**

**Output: `7`** ✅

---

### Example: `1 → 0 → 0 → 0 → 0` (binary `10000` = decimal `16`)

#### Approach 3 — Bit Shifting

| Step | Node Val | `result` before | `result` after |
|------|----------|-----------------|----------------|
| 1 | 1 | 0 | 1 |
| 2 | 0 | 1 | 2 |
| 3 | 0 | 2 | 4 |
| 4 | 0 | 4 | 8 |
| 5 | 0 | 8 | **16** |

**Output: `16`** ✅

---

## Edge Cases

| Edge Case | Description | How It's Handled |
|-----------|-------------|------------------|
| Single node `[0]` | Smallest possible input | All approaches return `0` immediately after one iteration |
| Single node `[1]` | Returns `1` | Shift: `(0 << 1) \| 1 = 1` ✅ |
| All zeros `[0,0,0]` | Result should be `0` | OR-ing 0s repeatedly keeps `result = 0` ✅ |
| All ones `[1,1,...,1]` (30 nodes) | Largest possible value: `2^30 - 1 = 1,073,741,823` | Fits in `int` (max ~2.1 billion). Problem guarantees this ✅ |
| Overflow risk | 31+ nodes of `1` could overflow `int` | Not possible given constraint of max 30 nodes; if worried, use `long` |

---

## Final Summary

| Approach | Time | Space | Recommended? |
|----------|------|-------|--------------|
| String Building | O(n) | O(n) | For readability only |
| Collect + Compute | O(n) | O(n) | Educational |
| Bit Shifting | O(n) | O(1) | ✅ Yes — optimal |

**Use the bit-shifting approach in interviews and production.** It's a single pass with constant space and no library dependencies.

**Key pattern to remember:** The shift-and-add (or shift-and-OR) technique — `result = (result << 1) | bit` — is a universal pattern for converting binary (or any base) sequences to integers in a single left-to-right pass. It appears in many problems involving bit manipulation, stream parsing, and encoding.
    */

}
