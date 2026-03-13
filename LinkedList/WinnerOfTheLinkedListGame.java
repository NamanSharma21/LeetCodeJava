package LinkedList;

import Datastructures.ListNode;

public class WinnerOfTheLinkedListGame {
    public static void main(String[] args) {
        WinnerOfTheLinkedListGame winnerOfTheLinkedListGame = new WinnerOfTheLinkedListGame();
        ListNode head = new ListNode(2);
        head.next = new ListNode(5);
        head.next.next = new ListNode(4);
        head.next.next.next = new ListNode(7);
        head.next.next.next.next = new ListNode(20);
        head.next.next.next.next.next = new ListNode(5);
        System.out.println("WinnerOfTheLinkedListGame : " + winnerOfTheLinkedListGame.gameResult(head));

        ListNode head1 = new ListNode(2);
        head1.next = new ListNode(1);
        System.out.println("WinnerOfTheLinkedListGame : " + winnerOfTheLinkedListGame.gameResult(head1));
    }

    /*
     * 
     * 1. 📋 Problem Statement
     * In Plain Terms:
     * You are given the head of a linked list of even length containing integers.
     * Each even-indexed node contains an even integer, and each odd-indexed node
     * contains an odd integer.
     * Every pair of consecutive nodes — (index 0, index 1), (index 2, index 3),
     * etc. — plays a "round":
     * 
     * If the even-indexed node's value is higher, the "Even" team scores a point.
     * If the odd-indexed node's value is higher, the "Odd" team scores a point.
     * 
     * Return the name of the team with more points. If tied, return "Tie". Medium
     * Input / Output / Constraints:
     * PropertyDetailInputhead of a singly linked listOutput"Even", "Odd", or "Tie"
     * (String)List length[2, 100] — always evenNode valuesEven-indexed nodes hold
     * even integers; odd-indexed nodes hold odd integersTie guaranteed?No — must
     * handle all three outcomes
     * What Exactly Must Be Computed?
     * Walk through the list two nodes at a time (pair by pair). For each pair
     * (evenNode, oddNode), compare their values and tally a running score. After
     * processing all pairs, return the winner.
     */

    public String gameResult(ListNode head) {
        ListNode current = head;
        int evenCount = 0;
        int oddCount = 0;
        while (current != null) {
            int evenVal = current.val;
            int oddVal = current.next.val;
            if (evenVal > oddVal) {
                evenCount++;
            } else {
                oddCount++;
            }
            current = current.next.next;
        }

        if (evenCount > oddCount) {
            return "Even";
        }
        if (oddCount > evenCount) {
            return "Odd";
        }
        return "Tie";
    }

    /*
        Now I have everything I need. Let me write a comprehensive breakdown.

---

# 🏆 LeetCode 3062 — Winner of the Linked List Game
### Deep-Dive Explanation in Java

---

## 1. 📋 Problem Statement

### In Plain Terms:

You are given the head of a linked list of **even length** containing integers. Each **even-indexed** node contains an even integer, and each **odd-indexed** node contains an odd integer.

Every pair of consecutive nodes — `(index 0, index 1)`, `(index 2, index 3)`, etc. — plays a "round":
- If the **even-indexed** node's value is **higher**, the `"Even"` team scores a point.
- If the **odd-indexed** node's value is **higher**, the `"Odd"` team scores a point.

Return the name of the team with more points. If tied, return `"Tie"`.

### Input / Output / Constraints:

| Property | Detail |
|---|---|
| **Input** | `head` of a singly linked list |
| **Output** | `"Even"`, `"Odd"`, or `"Tie"` (String) |
| **List length** | `[2, 100]` — always even |
| **Node values** | Even-indexed nodes hold even integers; odd-indexed nodes hold odd integers |
| **Tie guaranteed?** | No — must handle all three outcomes |

### What Exactly Must Be Computed?

Walk through the list **two nodes at a time** (pair by pair). For each pair `(evenNode, oddNode)`, compare their values and tally a running score. After processing all pairs, return the winner.

---

## 2. 💡 Intuition

### Core Idea:

The problem asks to determine the winner of a game by comparing pairs of nodes in a linked list, where each pair consists of an even-indexed node and the next odd-indexed node. If the even-indexed node is greater, the "Even" team gets a point. If the odd-indexed node is greater, the "Odd" team gets a point.

Think of it like a **sports league scoreboard**:
- You have **two teams** ("Even" and "Odd").
- Each pair of nodes is one **match**.
- At the end, whoever won more matches is the champion.

### How a Human Reasons Through It:

1. Start at the beginning of the list — this is always an even-indexed node (index 0).
2. Look at this node and the one immediately after it (the odd-indexed partner).
3. Compare their values. Award a point to the appropriate team.
4. **Skip two nodes ahead** — the next pair starts at index 2.
5. Repeat until the list is exhausted.
6. Compare final scores and return the winner.

### What Makes It Interesting?

- The guarantee that **even-indexed nodes hold even values** and **odd-indexed nodes hold odd values** is a red herring — it doesn't change the logic at all. You compare whatever values are there. This constraint exists to make the problem flavor feel clean.
- The real pattern to recognize is **pair-wise traversal**: jump by 2 each step (`head = head.next.next`).
- It tests whether you understand **linked list pointer manipulation** without needing extra data structures.

---

## 3. 🗺️ Approach Overview

| # | Approach | Key Idea | Use Case | Optimal? |
|---|---|---|---|---|
| 1 | **Convert to Array + Iterate** | Collect all values in a list/array, then loop with index | Simple to reason about, good for beginners | ❌ Extra space |
| 2 | **Single-Pass Pointer Walk (In-Place)** | Traverse list two nodes at a time with a pointer | Interviews, production code | ✅ **Optimal** |

Both approaches are O(N) in time, but the second is O(1) in space vs O(N) for the first.

---

## 4. 🔧 Detailed Solutions in Java

### Approach 1 — Convert to ArrayList, Then Index

#### Algorithm (Step by Step):
1. Walk the entire linked list and collect all values into an `ArrayList<Integer>`.
2. Loop through the array with index `i`, stepping by 2: process pairs `(i, i+1)`.
3. Compare `arr[i]` (even-indexed) vs `arr[i+1]` (odd-indexed).
4. Increment `evenScore` or `oddScore` accordingly.
5. Compare scores and return the result string.

```java
import java.util.ArrayList;
import java.util.List;

public class Solution {

    public String gameResult(ListNode head) {
        // Step 1: Flatten the linked list into an array for easy index access
        List<Integer> values = new ArrayList<>();
        ListNode current = head;
        while (current != null) {
            values.add(current.val);
            current = current.next;
        }

        int evenScore = 0;
        int oddScore = 0;

        // Step 2: Process pairs using index — i is even-indexed, i+1 is odd-indexed
        for (int i = 0; i < values.size(); i += 2) {
            int evenVal = values.get(i);
            int oddVal  = values.get(i + 1);

            if (evenVal > oddVal) {
                evenScore++;
            } else {
                // Problem guarantees no ties within a pair (one is even, one is odd integer)
                oddScore++;
            }
        }

        // Step 3: Determine and return the winner
        return determineWinner(evenScore, oddScore);
    }

    private String determineWinner(int evenScore, int oddScore) {
        if (evenScore > oddScore) return "Even";
        if (oddScore > evenScore) return "Odd";
        return "Tie";
    }
}
```

> **Note:** Within any single pair, a tie is impossible by the constraints (one value is even, the other odd — they can never be equal). However, the *overall* score across all pairs can absolutely be tied.

---

### Approach 2 — Optimal Single-Pass Pointer Walk ✅

#### Algorithm (Step by Step):
1. Start `current` pointer at `head`.
2. While `current` is not null:
   - Read `evenVal = current.val` (the even-indexed node).
   - Read `oddVal = current.next.val` (the odd-indexed node, guaranteed to exist since length is even).
   - Compare and increment the appropriate score counter.
   - Advance: `current = current.next.next` (skip the pair, land on next even-indexed node).
3. Return winner string.

```java
public class Solution {

    public String gameResult(ListNode head) {
        int evenScore = 0;
        int oddScore  = 0;

        ListNode current = head;

        // Each iteration processes one complete pair (even-indexed + odd-indexed node)
        while (current != null) {
            int evenVal = current.val;           // Even-indexed node
            int oddVal  = current.next.val;      // Odd-indexed node (always exists)

            if (evenVal > oddVal) {
                evenScore++;
            } else {
                oddScore++;  // oddVal > evenVal guaranteed within a pair
            }

            current = current.next.next;         // Jump to start of next pair
        }

        // Compare totals and return result
        if (evenScore > oddScore) return "Even";
        if (oddScore > evenScore) return "Odd";
        return "Tie";
    }
}
```

---

## 5. ⏱️ Time & Space Complexity

### Approach 1 — ArrayList Method

| Dimension | Complexity | Reasoning |
|---|---|---|
| **Time** | O(N) | One full pass to build the list + one pass to compare pairs = 2N iterations → O(N) |
| **Space** | O(N) | We store all N node values in an `ArrayList` |

**Example:** For N = 100 nodes → ~200 operations total, storing 100 integers.

---

### Approach 2 — Single-Pass Pointer Walk ✅

| Dimension | Complexity | Reasoning |
|---|---|---|
| **Time** | O(N) | We visit every node exactly once; `current = current.next.next` means N/2 iterations, each touching 2 nodes |
| **Space** | O(1) | Only a pointer (`current`) and two integer counters are used — no extra memory proportional to input |

**Example:** For N = 100 nodes → exactly 50 loop iterations, zero extra memory beyond 3 variables.

> Time Complexity: O(N) because each node is visited exactly once.

---

## 6. 🔍 Complete Worked Examples

### Example 1 — Single Pair (`[2, 1]`)

**Input:** `2 → 1`

| Step | `current` | `evenVal` | `oddVal` | Winner of pair | `evenScore` | `oddScore` |
|---|---|---|---|---|---|---|
| 1 | node(2) | 2 | 1 | Even (2 > 1) | 1 | 0 |
| — | null | — | — | Loop ends | 1 | 0 |

**Result:** `evenScore=1, oddScore=0` → **"Even"** ✅

---

### Example 2 — Multiple Pairs (`[2, 5, 4, 7, 20, 5]`)

**Input:** `2 → 5 → 4 → 7 → 20 → 5`

Pairs: `(2,5)`, `(4,7)`, `(20,5)`

| Step | `current` | `evenVal` | `oddVal` | Winner of pair | `evenScore` | `oddScore` |
|---|---|---|---|---|---|---|
| 1 | node(2) | 2 | 5 | Odd (5 > 2) | 0 | 1 |
| 2 | node(4) | 4 | 7 | Odd (7 > 4) | 0 | 2 |
| 3 | node(20) | 20 | 5 | Even (20 > 5) | 1 | 2 |
| — | null | — | — | Loop ends | 1 | 2 |

**Result:** `evenScore=1, oddScore=2` → **"Odd"** ✅

---

### Example 3 — Tie (`[4, 5, 2, 1]`)

**Input:** `4 → 5 → 2 → 1`

Pairs: `(4,5)`, `(2,1)`

| Step | `current` | `evenVal` | `oddVal` | Winner of pair | `evenScore` | `oddScore` |
|---|---|---|---|---|---|---|
| 1 | node(4) | 4 | 5 | Odd (5 > 4) | 0 | 1 |
| 2 | node(2) | 2 | 1 | Even (2 > 1) | 1 | 1 |
| — | null | — | — | Loop ends | 1 | 1 |

**Result:** `evenScore=1, oddScore=1` → **"Tie"** ✅

---

## 7. 🚨 Edge Cases

| Edge Case | Description | How Approach 2 Handles It |
|---|---|---|
| **Minimum list (2 nodes)** | Only one pair to compare | Loop runs once, correctly returns a winner |
| **All Even wins** | Every even-indexed node dominates | `oddScore` stays 0, returns "Even" |
| **All Odd wins** | Every odd-indexed node dominates | `evenScore` stays 0, returns "Odd" |
| **Perfect tie** | Equal number of pair-wins per team | Both scores equal, returns "Tie" |
| **Large values** | Node values can be large integers | Java `int` comparison with `>` is safe; no overflow risk since we're not summing values |
| **Null safety on `current.next`** | Could `current.next` ever be null mid-loop? | No — the problem **guarantees** even length. If `current != null`, then `current.next != null` always holds. |
| **Intra-pair tie** | Can `evenVal == oddVal` within a pair? | No — constraints guarantee even-indexed nodes hold even values and odd-indexed hold odd values; an even and odd integer can never be equal. Our `else` branch is safe. |

> ⚠️ **Trap:** If you mistakenly use `current = current.next` instead of `current = current.next.next`, you'll double-count nodes and get wrong results. Always jump by two.

---

## 8. 📊 Final Summary

| Approach | Time | Space | Recommended? |
|---|---|---|---|
| ArrayList then index | O(N) | O(N) | For beginners / readability |
| Single-pass pointer | O(N) | O(1) | ✅ Always preferred |

### ✅ Recommended Approach: Single-Pass Pointer Walk

Both are O(N) time, but the pointer walk uses O(1) space and is the pattern interviewers expect when they hand you a linked list problem.

### 🧠 What to Remember:

> **Pattern:** Pair-wise linked list traversal — when you need to process consecutive pairs of nodes, use `current = current.next.next` to hop over each pair after processing it. This is a fundamental linked list idiom that appears in many problems (e.g., swapping pairs, reversing in groups of two, etc.).

The deeper lesson is: **don't convert a linked list to an array unless you genuinely need random access.** Here, sequential pair processing is all that's required, making O(1) space entirely achievable.
    */
}
