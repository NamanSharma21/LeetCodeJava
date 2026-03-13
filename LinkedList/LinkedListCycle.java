package LinkedList;

import Datastructures.ListNode;

public class LinkedListCycle {
    public static void main(String[] args) {
        LinkedListCycle linkedListCycle = new LinkedListCycle();
        ListNode head = new ListNode(3);
        head.next = new ListNode(2);
        head.next.next = new ListNode();
        head.next.next.next = new ListNode(-4);
        head.next.next.next.next = head.next;
        System.out.println("Has Cycle : " + linkedListCycle.hasCycle(head));

    }

    /*
     * Given head, the head of a linked list, determine if the linked list has a
     * cycle in it.
     * 
     * There is a cycle in a linked list if there is some node in the list that can
     * be reached again by continuously following the next pointer. Internally, pos
     * is used to denote the index of the node that tail's next pointer is connected
     * to. Note that pos is not passed as a parameter.
     * 
     * Return true if there is a cycle in the linked list. Otherwise, return false.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: head = [3,2,0,-4], pos = 1
     * Output: true
     * Explanation: There is a cycle in the linked list, where the tail connects to
     * the 1st node (0-indexed).
     * Example 2:
     * 
     * 
     * Input: head = [1,2], pos = 0
     * Output: true
     * Explanation: There is a cycle in the linked list, where the tail connects to
     * the 0th node.
     * Example 3:
     * 
     * 
     * Input: head = [1], pos = -1
     * Output: false
     * Explanation: There is no cycle in the linked list.
     * 
     * 
     * Constraints:
     * 
     * The number of the nodes in the list is in the range [0, 104].
     * -105 <= Node.val <= 105
     * pos is -1 or a valid index in the linked-list.
     * 
     * 
     * Follow up: Can you solve it using O(1) (i.e. constant) memory?
     */

    public boolean hasCycle(ListNode head) {
        if (head == null)
            return false;
        ListNode walker = head, runner = head;
        while (runner.next != null && runner.next.next != null) {
            walker = walker.next;
            runner = runner.next.next;
            if (walker == runner) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * ## 1. Problem Statement

Given the `head` of a **singly linked list**, determine whether the list **contains a cycle**. [leetcode.doocs](https://leetcode.doocs.org/en/lc/141/)

A cycle exists if there is some node that can be reached again by continuously following the `next` pointer (i.e., at least one node’s `next` eventually points back to a previous node instead of `null`). [codeanddebug](https://codeanddebug.in/blog/linked-list-cycle-leetcode-141/)

Typical `ListNode`:

```java
public class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; next = null; }
}
```

### Input / Output / Constraints

- **Input:** `ListNode head`
- **Output:** `boolean`
  - `true` if the list has a cycle, `false` otherwise. [github](https://github.com/doocs/leetcode/blob/main/solution/0100-0199/0141.Linked%20List%20Cycle/README_EN.md)
- Constraints (LeetCode 141 style): [codeanddebug](https://codeanddebug.in/blog/linked-list-cycle-leetcode-141/)
  - Number of nodes: `0 <= n <= 10^4`
  - Node values: `-10^5 <= val <= 10^5`
  - There is usually a hidden parameter `pos` indicating the index where the tail connects (or `-1` if no cycle), but your function only sees `head`. [github](https://github.com/doocs/leetcode/blob/main/solution/0100-0199/0141.Linked%20List%20Cycle/README_EN.md)

You must compute:  
Does continuously following `next` pointers from `head` ever **loop back** to a previously visited node?

***

## 2. Intuition

Human view:

- If you walk along the list, either:
  - You eventually hit `null` → no cycle.
  - Or you keep visiting nodes without ever reaching `null` → if you ever re-visit a node, there’s a cycle.

Two natural strategies:

1. **Remember visited nodes** in a set.
   - As soon as you see a node you’ve seen before, there’s a cycle.
2. **Tortoise and hare (Floyd’s cycle detection)**:
   - Use two pointers: `slow` (1-step) and `fast` (2-step). [ajay-dhangar.github](https://ajay-dhangar.github.io/algo/docs/extra/linked-list/floyds-cycle-detection/)
   - If there’s no cycle, `fast` will hit `null` first.
   - If there’s a cycle, `fast` will eventually “lap” `slow` and they will meet at some node inside the cycle. [leetcode-in-java.github](https://leetcode-in-java.github.io/src/main/java/g0101_0200/s0141_linked_list_cycle/)

Why this is interesting:

- You can solve it in O(n) time with:
  - O(n) space (set), or
  - O(1) space (Floyd’s algorithm).
- The tortoise-hare pattern generalizes to many cycle-detection problems. [geeksforgeeks](https://www.geeksforgeeks.org/dsa/detect-loop-in-a-linked-list/)

***

## 3. Approach Overview

Let `n` = number of nodes.

### Approach 1 – HashSet of visited nodes (simple, O(n) space)

- **Key idea:**  
  Traverse nodes; store each node reference in a `Set<ListNode>`. If you ever encounter a node that’s already in the set, a cycle exists; if you reach `null`, no cycle. [algo](https://algo.monster/liteproblems/141)
- **When to use:**  
  Very easy to implement and reason about; good first solution.
- **Cost:**  
  O(n) time, O(n) space.

### Approach 2 – Floyd’s Cycle Detection (tortoise & hare), O(1) space (optimal)

- **Key idea:**  
  Use `slow` and `fast` pointers starting at `head`. Move:
  - `slow = slow.next`
  - `fast = fast.next.next`
  each step. If at any point `slow == fast`, there is a cycle. If `fast` or `fast.next` becomes null, no cycle. [ajay-dhangar.github](https://ajay-dhangar.github.io/algo/docs/extra/linked-list/floyds-cycle-detection/)
- **When to use:**  
  Standard optimal interview solution; avoids extra memory.

### Approach 3 – “Modify list” (marking nodes) – generally **not recommended**

- **Key idea:**  
  Some texts suggest marking `next` pointers or node values as you traverse. This modifies the input structure and can break other code, so it’s discouraged. [algo](https://algo.monster/liteproblems/141)
- **When to use:**  
  Typically avoid; violates “do not modify data” expectation in interviews.

Optimal approach: **Approach 2 (Floyd’s algorithm)** – O(n) time, O(1) space.

***

## 4. Detailed Solutions in Java

### 4.1 Approach 1 – HashSet of Visited Nodes

#### Algorithm (step-by-step)

1. If `head == null`, return false (empty list has no cycle).
2. Initialize an empty `Set<ListNode>` (e.g., `HashSet`).
3. Set `curr = head`.
4. While `curr != null`:
   - If `curr` is in the set:
     - Return `true` (we’ve seen this node before → cycle). [geeksforgeeks](https://www.geeksforgeeks.org/dsa/detect-loop-in-a-linked-list/)
   - Else:
     - Add `curr` to the set.
     - Move `curr = curr.next`.
5. If loop ends with `curr == null`, return `false` (no cycle).

#### Java Code

```java
import java.util.HashSet;
import java.util.Set;

public class LinkedListCycleHashSet {

    public boolean hasCycle(ListNode head) {
        Set<ListNode> visited = new HashSet<>();

        ListNode curr = head;
        while (curr != null) {
            if (visited.contains(curr)) {
                return true; // we have seen this node before -> cycle
            }
            visited.add(curr);
            curr = curr.next;
        }

        return false; // reached null, no cycle
    }
}
```

#### Complexity

- **Time:**  
  - For each node, one hash lookup and one insert; overall at most n iterations.
  - Time: **O(n)**. [algo](https://algo.monster/liteproblems/141)
- **Space:**  
  - Set stores at most all nodes → **O(n)**.

Example: n=10^4, a few tens of thousands of hash ops – fine, but uses memory.

#### Worked Example – cycle: `3 → 2 → 0 → -4 ↘`  
                                     `↑          ↙`  
                                       (cycle back to 2)

- Start at 3: visited={3}.
- Move to 2: visited={3,2}.
- Move to 0: visited={3,2,0}.
- Move to -4: visited={3,2,0,-4}.
- Move next: back to 2.
- 2 is in visited already → return true.

***

### 4.2 Approach 2 – Floyd’s Tortoise and Hare (Optimal)

#### Algorithm (step-by-step)

1. If `head == null` or `head.next == null`, return false (empty/1-node list cannot cycle without extra pointer).
2. Initialize:
   - `ListNode slow = head;`
   - `ListNode fast = head;`
3. Loop while `fast != null && fast.next != null`: [leetcode-in-java.github](https://leetcode-in-java.github.io/src/main/java/g0101_0200/s0141_linked_list_cycle/)
   - `slow = slow.next;`         // move 1 step
   - `fast = fast.next.next;`    // move 2 steps
   - If at any point `slow == fast`, return `true` (cycle detected).
4. If loop exits (fast hits null), return `false` (no cycle).

**Why it works (intuition):** [enjoyalgorithms](https://www.enjoyalgorithms.com/blog/detect-loop-in-linked-list/)

- If there is no cycle, the `fast` pointer eventually reaches `null` (or `fast.next` is null), so the loop ends with no meet.
- If there is a cycle, `slow` and `fast` both eventually enter the cycle and move around it; since `fast` moves 2 steps and `slow` 1 step each time, `fast` eventually “laps” `slow` and they meet inside the cycle (pigeonhole principle + relative speed argument).

#### Java Code

```java
public class LinkedListCycleFloyd {

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head;

        // Move slow by 1 and fast by 2
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            // If they meet, there is a cycle
            if (slow == fast) {
                return true;
            }
        }

        // If fast reaches null, no cycle
        return false;
    }
}
```

#### Complexity

- **Time:**  
  - In worst case (no cycle), `fast` traverses the entire list; `slow` roughly half that.
  - If cycle exists, they meet in at most O(n) steps.
  - Time: **O(n)**. [codeanddebug](https://codeanddebug.in/blog/linked-list-cycle-leetcode-141/)
- **Space:**  
  - Only two pointers (slow, fast) → **O(1)** extra space.

This satisfies the follow-up requirement. [github](https://github.com/doocs/leetcode/blob/main/solution/0100-0199/0141.Linked%20List%20Cycle/README_EN.md)

#### Worked Example 1 – List with no cycle: `1 → 2 → 3 → 4 → null`

- slow=1, fast=1.
- Loop:
  - Iter1: slow=2, fast=3.
  - Iter2: slow=3, fast=null (fast.next is 4, fast.next.next is null).
- Condition `fast != null && fast.next != null` fails → exit loop.
- Return false.

#### Worked Example 2 – List with cycle: `3 → 2 → 0 → -4 ↘` back to 2

Let’s label nodes: A(3), B(2), C(0), D(-4).
Cycle: D.next = B.

- Initial: slow=A, fast=A.

Iteration 1:

- slow = B (2)
- fast = C (0) (A → B → C)

Iteration 2:

- slow = C (0)
- fast = B (2) (C → D → B)

Iteration 3:

- slow = D (-4)
- fast = D (-4) (B → C → D)
- slow == fast → return true.

***

### 4.3 Approach 3 – “Mark nodes by modifying list” (Not recommended)

Some ideas:

- Temporarily change `next` pointers to point to a special marker node, or change `val` to a sentinel, and check if you see that sentinel again. [algo](https://algo.monster/liteproblems/141)
- Problems:
  - Modifies input list, violating typical expectations.
  - Not thread-safe; other code may observe corrupted list.
  - Requires extra passes to restore list if you want to keep it. [algo](https://algo.monster/liteproblems/141)

Generally **avoid** this in interviews; stick to hash set or Floyd’s algorithm.

***

## 5. Edge Cases

1. **Empty list:** `head = null`
   - Obviously no cycle → false.
   - Both approaches return false quickly.

2. **Single node, no cycle:** `head.next = null`
   - No cycle → false.
   - Floyd: head != null but head.next == null → immediate false.

3. **Single node with cycle:** node’s `next` points to itself.
   - Floyd:
     - slow = head, fast = head.
     - Iter1:
       - slow = head.next (head)
       - fast = head.next.next (head)
       - slow == fast → cycle detected.

4. **Two nodes:**
   - `1 → 2 → null` → no cycle.
   - `1 → 2 → (back to 1)` → cycle.
   - Floyd still works.

5. **Cycle starts early vs late:**
   - Floyd’s algorithm doesn’t care where the cycle starts; detection time is bounded by O(n).

6. **Huge list (`n` up to 10^4):**
   - HashSet O(n) space may be fine.
   - Floyd O(1) space is preferred as a pattern.

***

## 6. Final Summary

- Problem: detect whether a singly linked list has a **cycle**.
- Straightforward solution:  
  - Use a `HashSet<ListNode>` to record visited nodes; if a node repeats, return true.  
  - Time O(n), space O(n).
- Optimal solution (follow-up):  
  - Use **Floyd’s Tortoise and Hare**:
    - Two pointers: `slow` moves 1 step, `fast` moves 2 steps.
    - If they ever meet, there’s a cycle; if `fast` reaches null, there isn’t.
    - Time O(n), **space O(1)**. [ajay-dhangar.github](https://ajay-dhangar.github.io/algo/docs/extra/linked-list/floyds-cycle-detection/)

Key idea to remember:

> “Linked List Cycle” is the archetypal **Floyd’s cycle detection** problem.  
> Always think: **two pointers with different speeds**; if they ever meet, we are looping.

If you want, next we can extend this to “Linked List Cycle II” (finding the exact node where the cycle begins) using the same Floyd’s algorithm idea.
     * 
     */
}
