package LinkedList;

import Datastructures.ListNode;

public class RemoveLinkedListElements {
    public static void main(String[] args) {
        RemoveLinkedListElements removeLinkedListElements = new RemoveLinkedListElements();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(6);
        head.next.next.next = new ListNode(3);
        head.next.next.next.next = new ListNode(4);
        head.next.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next.next = new ListNode(6);
        System.out.println("RemoveLinkedListElements : " + removeLinkedListElements.removeElementsRecursive(head, 6));

        ListNode head1 = new ListNode(7);
        head1.next = new ListNode(7);
        head1.next.next = new ListNode(7);
        head1.next.next.next = new ListNode(7);
        System.out.println("RemoveLinkedListElements : " + removeLinkedListElements.removeElementsRecursive(head1, 7));
    }

    /*
     * https://leetcode.com/problems/remove-linked-list-elements/description/?
     * envType=problem-list-v2&envId=linked-list
     * 
     * Given the head of a linked list and an integer val, remove all the nodes of
     * the linked list that has Node.val == val, and return the new head.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: head = [1,2,6,3,4,5,6], val = 6
     * Output: [1,2,3,4,5]
     * Example 2:
     * 
     * Input: head = [], val = 1
     * Output: []
     * Example 3:
     * 
     * Input: head = [7,7,7,7], val = 7
     * Output: []
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the list is in the range [0, 104].
     * 1 <= Node.val <= 50
     * 0 <= val <= 50
     * 
     * 
     */

    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return null;
        }

        ListNode dummy = new ListNode(0, head);
        ListNode previous = dummy;
        while (previous.next != null) {
            if (previous.next.val == val) {
                previous.next = previous.next.next;
            } else {
                previous = previous.next;
            }
        }
        return dummy.next;
    }

    public ListNode removeElementsRecursive(ListNode head, int val) {
        if (head == null) {
            return null;
        }

        head.next = removeElementsRecursive(head.next, val);
        if (head.val == val) {
            return head.next;
        } else {
            return head;
        }
    }


    /*
    ## 1. Problem Statement

You are given the `head` of a **singly linked list** and an integer `val`.  

Remove **all nodes** whose value equals `val` and return the **head of the modified list**. [algo](https://algo.monster/liteproblems/203)

Typical `ListNode`:

```java
public class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
```

### Input / Output / Constraints

- **Input:**  
  - `ListNode head` – head of a (possibly empty) singly linked list  
  - `int val` – target value to remove
- **Output:**  
  - `ListNode` – head of the list after **all** nodes with `val` removed. [github](https://github.com/doocs/leetcode/blob/main/solution/0200-0299/0203.Remove%20Linked%20List%20Elements/README_EN.md)

Typical constraints: [github](https://github.com/doocs/leetcode/blob/main/solution/0200-0299/0203.Remove%20Linked%20List%20Elements/README_EN.md)

- `0 <= number of nodes <= 10^4`
- `1 <= Node.val <= 50`
- `0 <= val <= 50`

You must compute:  
A list where every node with `node.val == val` has been deleted from the chain (links fixed), and return its new head (which may be `null` or different from the original head).

***

## 2. Intuition

Core idea: **filter** the linked list to remove nodes with a specific value.

Human reasoning:

- You walk along the list.
- For each node, if its value equals `val`, you “skip” it in the result.
- For the others, you keep them.

Tricky part:

- Removing a **non-head** node is easy: if you are at `prev` and `prev.next.val == val`, do `prev.next = prev.next.next`.
- Removing the **head** (or multiple leading nodes) is trickier if you try to treat it like other nodes because there is no previous pointer to `head`, and `head` itself might need to move forward multiple times. [stackoverflow](https://stackoverflow.com/questions/74973791/203-remove-linked-list-elements-from-the-leetcode)

Standard trick: use a **dummy node** that points to `head`. Then you always remove using `prev.next` logic, and `dummy.next` becomes the new head. [walkccc](https://walkccc.me/LeetCode/problems/203/)

***

## 3. Approach Overview

Let `n` be the number of nodes.

### Approach 1 – Iterative with dummy node (recommended, optimal)

- **Key idea:**  
  Create `dummy -> head`. Use a pointer `prev` that walks the list via `prev.next`.  
  If `prev.next.val == val`, skip that node; otherwise move `prev` forward. [algo](https://algo.monster/liteproblems/203)
- **When used:**  
  This is the standard robust solution; handles all cases (removing head, many consecutive matches) cleanly.
- **Complexity:**  
  O(n) time, O(1) extra space.

### Approach 2 – Recursive filtering

- **Key idea:**  
  Define `removeElements(head, val)` as:
  - If `head == null`, return `null`.
  - Recursively clean `head.next`, then decide whether to keep or drop `head`:
    - If `head.val == val`, return recursive result (skip head).
    - Else set `head.next` to recursive result and return `head`. [youtube](https://www.youtube.com/watch?v=Z9NLxRKreO0)
- **When used:**  
  Elegant and short; good when recursion depth (n) is safe.
- **Complexity:**  
  Also O(n) time, but recursion uses O(n) call stack.

There is no need for extra data structures (like `HashSet`), and building a separate new list is overkill unless you explicitly want to avoid mutating the original list structure.

**Optimal in practice:** Approach 1 (iterative + dummy) – O(n) time, O(1) space, very robust.

***

## 4. Detailed Solutions in Java

### 4.1 Approach 1 – Iterative with Dummy Node (Recommended)

#### Algorithm (step-by-step)

1. Create a dummy node: `dummy = new ListNode(0, head);`. [youtube](https://www.youtube.com/watch?v=Z9NLxRKreO0)
2. Set `prev = dummy`.
3. While `prev.next != null`:
   - If `prev.next.val == val`:
     - Node to delete is `prev.next`.
     - Remove it by: `prev.next = prev.next.next;`
     - **Do not move `prev`**; check the new `prev.next` again, because there may be multiple nodes in a row with value `val`.
   - Else:
     - Node’s value != val, keep it and move: `prev = prev.next;`.
4. At the end, the new head is `dummy.next`. Return `dummy.next`.

This reflects the pattern described in AlgoMonster / editorial solutions. [walkccc](https://walkccc.me/LeetCode/problems/203/)

#### Java Code

```java
public class RemoveLinkedListElementsIterative {

    public ListNode removeElements(ListNode head, int val) {
        // Dummy node to handle deletions at the head uniformly
        ListNode dummy = new ListNode(0, head);

        ListNode previous = dummy;

        // Traverse the list using previous.next
        while (previous.next != null) {
            if (previous.next.val == val) {
                // Skip the node with matching value
                previous.next = previous.next.next;
            } else {
                // Move forward only when we keep the node
                previous = previous.next;
            }
        }

        // New head might be different from original if head was removed
        return dummy.next;
    }
}
```

#### Complexity

- **Time:**
  - Each node is inspected at most once, and each deletion is O(1).
  - Total operations ≈ n node checks → **O(n)**. [algo](https://algo.monster/liteproblems/203)
- **Space:**
  - Only dummy + previous + locals → **O(1)** extra space.

Example: `n = 10^4` → at most 10k iterations; trivial.

#### Worked Example – `[1,2,6,3,4,5,6]`, `val = 6`

Goal: remove all 6’s → result should be `[1,2,3,4,5]`. [github](https://github.com/doocs/leetcode/blob/main/solution/0200-0299/0203.Remove%20Linked%20List%20Elements/README_EN.md)

Initial:

- dummy → 1 → 2 → 6 → 3 → 4 → 5 → 6 → null  
- previous = dummy

Step-by-step:

1) previous at dummy, previous.next = 1

- previous.next.val = 1 != 6 → keep, move previous to 1

2) previous at 1, previous.next = 2

- 2 != 6 → move previous to 2

3) previous at 2, previous.next = 6

- 6 == 6 → delete:
  - previous.next = previous.next.next → 2.next = 3
- list now: dummy → 1 → 2 → 3 → 4 → 5 → 6
- previous stays at 2

4) previous at 2, previous.next = 3

- 3 != 6 → move previous to 3

5) previous at 3, previous.next = 4

- 4 != 6 → move previous to 4

6) previous at 4, previous.next = 5

- 5 != 6 → move previous to 5

7) previous at 5, previous.next = 6

- 6 == 6 → delete:
  - 5.next = 6.next (null)
- list now: dummy → 1 → 2 → 3 → 4 → 5 → null
- previous still 5; previous.next == null → loop ends.

Return `dummy.next` → `1 → 2 → 3 → 4 → 5`.

***

### 4.2 Approach 2 – Recursive Filtering

This follows the clean recursive pattern “process rest, then decide for current”.

#### Algorithm

Define `removeElements(head, val)` recursively: [youtube](https://www.youtube.com/watch?v=xasf2yCruBQ)

1. Base: if `head == null`, return null.
2. Recursively process the rest: `head.next = removeElements(head.next, val);`
3. Now decide for `head`:
   - If `head.val == val`, skip this node and return `head.next` (result of recursion).
   - Else, keep this node and return `head`.

#### Java Code

```java
public class RemoveLinkedListElementsRecursive {

    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return null;
        }

        // First, clean the rest of the list
        head.next = removeElements(head.next, val);

        // Then decide whether to keep head
        if (head.val == val) {
            return head.next; // skip this node
        } else {
            return head;      // keep this node
        }
    }
}
```

This matches common recursive solutions given in Q&A threads. [stackoverflow](https://stackoverflow.com/questions/74973791/203-remove-linked-list-elements-from-the-leetcode)

#### Complexity

- **Time:**
  - Each node is processed once in a recursive call → **O(n)**. [youtube](https://www.youtube.com/watch?v=xasf2yCruBQ)
- **Space:**
  - Recursion call stack depth up to n → **O(n)** extra.
  - No additional data structures.

For n up to 10^4, recursion might be borderline in some languages/environments; in Java it’s usually okay but iterative is safer.

#### Worked Example – `[1,2,6,3,4,5,6]`, `val = 6`

Call chain (top-down):

- `removeElements(1→2→6→3→4→5→6)`
  - head=1, process rest: `head.next = removeElements(2→6→3→4→5→6)`

- `removeElements(2→6→3→4→5→6)`
  - head=2, head.next = `removeElements(6→3→4→5→6)`

- `removeElements(6→3→4→5→6)`
  - head=6, head.next = `removeElements(3→4→5→6)`

- `removeElements(3→4→5→6)`
  - head=3, head.next = `removeElements(4→5→6)`

- `removeElements(4→5→6)`
  - head=4, head.next = `removeElements(5→6)`

- `removeElements(5→6)`
  - head=5, head.next = `removeElements(6)`

- `removeElements(6)`
  - head=6, head.next = `removeElements(null)`

- `removeElements(null)` → returns null.

Now unwind:

- For last 6:
  - head=6, head.next=null, head.val==6 → return head.next (null)
- For 5:
  - head=5, head.next = result of last call = null, head.val!=6 → return head (5→null)
- For 4:
  - head=4, head.next = 5→null, return 4→5→null
- For 3:
  - head=3, next=4→5→null, return 3→4→5→null
- For 6 (the middle one):
  - head=6, next=3→4→5→null, head.val==6 → return next (3→4→5→null)
- For 2:
  - head=2, next=3→4→5→null, keep → 2→3→4→5→null
- For 1:
  - head=1, next=2→3→4→5→null, keep → 1→2→3→4→5→null

Result `1→2→3→4→5`.

***

## 5. Edge Cases

1. **Empty list (`head = null`)**
   - No nodes to remove; result is `null`.
   - Iterative: dummy.next=null, loop skipped, return null.
   - Recursive: base case returns null.

2. **All nodes have value `val`** (e.g., `[7,7,7,7]`, `val=7`)
   - Iterative: each node will be skipped; `dummy.next` becomes null → result `[]`. [github](https://github.com/doocs/leetcode/blob/main/solution/0200-0299/0203.Remove%20Linked%20List%20Elements/README_EN.md)
   - Recursive: every call returns its `head.next` until null; final result null.

3. **No nodes match `val`** (e.g., `[1,2,3]`, `val=4`)
   - List unchanged; both approaches keep every node.

4. **Only head matches** (e.g., `[6,1,2,3]`, `val=6`)
   - Iterative: dummy→6→1→2→3:
     - prev=dummy, prev.next.val=6 → skip → prev.next=1.
   - Result `[1,2,3]`.
   - Recursive: first call sees head=6, will ultimately return `removeElements(1→2→3)`.

5. **Consecutive matches in middle or end**:
   - `[1,2,6,6,6,3]`, `val=6`.
   - Iterative keeps `prev` at node 2 while repeatedly removing `prev.next` until it is 3 or null.

6. **Large list (`n=10^4`)**
   - Iterative: safe, O(n) time and O(1) space.
   - Recursive: may risk stack overflow in some environments; Java often can handle but iterative is preferred in interviews.

***

## 6. Final Summary

- Problem: remove **all nodes** from a singly linked list whose value equals a given `val`, and return the new head.
- Main difficulty: correctly handle **removing the head** and **multiple consecutive matches** without losing the list.

**Approaches:**

- **Iterative with dummy (recommended):**
  - Attach a dummy before head.
  - Use `previous.next` to inspect and skip matching nodes.
  - O(n) time, O(1) space; very robust for all edge cases. [walkccc](https://walkccc.me/LeetCode/problems/203/)
- **Recursive:**
  - Recursively clean `head.next`, then decide whether to keep or drop `head`.
  - O(n) time, O(n) stack space; elegant but less robust for very large lists. [stackoverflow](https://stackoverflow.com/questions/74973791/203-remove-linked-list-elements-from-the-leetcode)

Key pattern to remember:

> For “remove nodes matching some condition” in linked lists, the standard pattern is **“dummy node + `prev.next` skipping”**; for recursion, think **“process rest first, then conditionally keep head”**.
    */
}
