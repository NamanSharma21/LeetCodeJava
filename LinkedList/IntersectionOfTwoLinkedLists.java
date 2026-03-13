package LinkedList;

import Datastructures.ListNode;

public class IntersectionOfTwoLinkedLists {
    public static void main(String[] args) {
        IntersectionOfTwoLinkedLists intersectionOfTwoLinkedLists = new IntersectionOfTwoLinkedLists();
        ListNode headA = new ListNode(4);
        headA.next = new ListNode(1);

        ListNode intersection = new ListNode(8);
        intersection.next = new ListNode(4);
        intersection.next.next = new ListNode(5);
        headA.next.next = intersection;

        ListNode headB = new ListNode(5);
        headB.next = new ListNode(6);
        headB.next.next = new ListNode(1);
        headB.next.next.next = intersection;
        System.out.println(
                "IntersectionOfTwoLinkedLists : " + intersectionOfTwoLinkedLists.getIntersectionNode(headA, headB));
    }

    /*
     * 
     * https://leetcode.com/problems/intersection-of-two-linked-lists/description/?
     * envType=problem-list-v2&envId=linked-list
     * 
     * Given the heads of two singly linked-lists headA and headB, return the node
     * at which the two lists intersect. If the two linked lists have no
     * intersection at all, return null.
     * 
     * For example, the following two linked lists begin to intersect at node c1:
     * 
     * 
     * The test cases are generated such that there are no cycles anywhere in the
     * entire linked structure.
     * 
     * Note that the linked lists must retain their original structure after the
     * function returns.
     * 
     * Custom Judge:
     * 
     * The inputs to the judge are given as follows (your program is not given these
     * inputs):
     * 
     * intersectVal - The value of the node where the intersection occurs. This is 0
     * if there is no intersected node.
     * listA - The first linked list.
     * listB - The second linked list.
     * skipA - The number of nodes to skip ahead in listA (starting from the head)
     * to get to the intersected node.
     * skipB - The number of nodes to skip ahead in listB (starting from the head)
     * to get to the intersected node.
     * The judge will then create the linked structure based on these inputs and
     * pass the two heads, headA and headB to your program. If you correctly return
     * the intersected node, then your solution will be accepted.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA =
     * 2, skipB = 3
     * Output: Intersected at '8'
     * Explanation: The intersected node's value is 8 (note that this must not be 0
     * if the two lists intersect).
     * From the head of A, it reads as [4,1,8,4,5]. From the head of B, it reads as
     * [5,6,1,8,4,5]. There are 2 nodes before the intersected node in A; There are
     * 3 nodes before the intersected node in B.
     * - Note that the intersected node's value is not 1 because the nodes with
     * value 1 in A and B (2nd node in A and 3rd node in B) are different node
     * references. In other words, they point to two different locations in memory,
     * while the nodes with value 8 in A and B (3rd node in A and 4th node in B)
     * point to the same location in memory.
     * Example 2:
     * 
     * 
     * Input: intersectVal = 2, listA = [1,9,1,2,4], listB = [3,2,4], skipA = 3,
     * skipB = 1
     * Output: Intersected at '2'
     * Explanation: The intersected node's value is 2 (note that this must not be 0
     * if the two lists intersect).
     * From the head of A, it reads as [1,9,1,2,4]. From the head of B, it reads as
     * [3,2,4]. There are 3 nodes before the intersected node in A; There are 1 node
     * before the intersected node in B.
     * Example 3:
     * 
     * 
     * Input: intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
     * Output: No intersection
     * Explanation: From the head of A, it reads as [2,6,4]. From the head of B, it
     * reads as [1,5]. Since the two lists do not intersect, intersectVal must be 0,
     * while skipA and skipB can be arbitrary values.
     * Explanation: The two lists do not intersect, so return null.
     * 
     * 
     * Constraints:
     * 
     * The number of nodes of listA is in the m.
     * The number of nodes of listB is in the n.
     * 1 <= m, n <= 3 * 104
     * 1 <= Node.val <= 105
     * 0 <= skipA <= m
     * 0 <= skipB <= n
     * intersectVal is 0 if listA and listB do not intersect.
     * intersectVal == listA[skipA] == listB[skipB] if listA and listB intersect.
     * 
     * 
     * Follow up: Could you write a solution that runs in O(m + n) time and use only
     * O(1) memory?
     */

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null)
            return null;
        ListNode pA = headA;
        ListNode pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }

    public ListNode getIntersectionNodeWithLength(ListNode headA, ListNode headB) {
        if (headA == null || headB == null)
            return null;
        int lengthA = getLengthOfList(headA);
        int lengthB = getLengthOfList(headB);
        ListNode pA = headA;
        ListNode pB = headB;
        int diff = Math.abs(lengthA - lengthB);
        if (lengthA > lengthB) {
            for (int i = 0; i < diff; i++) {
                pA = pA.next;
            }
        } else {
            for (int i = 0; i < diff; i++) {
                pB = pB.next;
            }
        }

        while (pA != null && pB != null) {
            if (pA == pB) {
                return pA;
            }
            pA = pA.next;
            pB = pB.next;
        }
        return null;
    }

    public int getLengthOfList(ListNode head) {
        int counter = 0;
        ListNode dummy = head;
        while (dummy != null) {
            counter++;
            dummy = dummy.next;
        }
        return counter;
    }


    /*
    
    ## 1. Problem Statement

You are given the heads of two **singly linked lists**, `headA` and `headB`.  

These lists may or may not **intersect** (share some tail nodes).  

You must return the **node where the intersection begins**, or `null` if the lists do not intersect. [algo](https://algo.monster/liteproblems/160)

Important:  
Intersection is by **node reference**, not by value:

- If `a` and `b` are nodes, intersection means `a == b` (same object), **not** `a.val == b.val`. [codeanddebug](https://codeanddebug.in/blog/intersection-of-two-linked-lists-leetcode-160/)

Typical `ListNode`:

```java
public class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; next = null; }
}
```

### Input / Output / Constraints

- **Input:** `ListNode headA, ListNode headB`
- **Output:** `ListNode` – first common node by reference, or `null` if no intersection. [leetcode](https://leetcode.com/problems/intersection-of-two-linked-lists/)

Constraints (LeetCode 160 style): [github](https://github.com/doocs/leetcode/blob/main/solution/0100-0199/0160.Intersection%20of%20Two%20Linked%20Lists/README_EN.md)

- `1 <= m, n <= 3 * 10^4` (m, n are lengths of the two lists)
- `1 <= Node.val <= 10^5`
- Lists have **no cycles**, and you must **not modify** their structure.

You must compute: the first node `X` such that `X` is reachable from both `headA` and `headB`, by following `next` pointers.

***

## 2. Intuition

Visualize two lists that intersect:

- List A: `a1 → a2 → c1 → c2 → c3`
- List B: `b1 → b2 → b3 → c1 → c2 → c3`

They share the tail `c1 → c2 → c3`. The intersection point is `c1`.

Key observations: [leetcode.doocs](https://leetcode.doocs.org/en/lc/160/)

- After intersection, they share **exactly the same nodes**.
- The difference is only in their **prefix lengths**:
  - A has some unique nodes before `c1`.
  - B has some unique nodes before `c1`.

Intuitive ways to find the intersection:

1. **Brute force:**  
   Compare every node in A with every node in B – too slow.

2. **Hash-based:**  
   Store addresses of one list’s nodes in a set, then walk the other and find first node present in the set.

3. **Length alignment:**  
   Count lengths `lenA`, `lenB`. Advance the longer list’s pointer by `|lenA - lenB|` so both have same distance to end, then walk together until they meet.

4. **Two-pointer swapping trick (most elegant):** [youtube](https://www.youtube.com/watch?v=D0X0BONOQhI)
   - Use two pointers `pA` and `pB`.
   - Move each one step at a time.
   - When `pA` reaches end of A, redirect it to `headB`.
   - When `pB` reaches end of B, redirect it to `headA`.
   - Eventually, either:
     - They meet at the intersection; or
     - Both become `null` simultaneously (no intersection).

This last method works because each pointer traverses exactly `lenA + lenB` steps; by the time they’ve both traversed both lists, they are “aligned” in terms of distance from the tail. [youtube](https://www.youtube.com/watch?v=IpBfg9d4dmQ)

***

## 3. Approach Overview

Let `m = len(A)`, `n = len(B)`.

### Approach 1 – Brute Force O(m·n)

- **Key idea:**  
  For each node in list A, walk entire list B and check if any node is the same reference. [youtube](https://www.youtube.com/watch?v=jf99MjAq4jU)
- **When used:**  
  Only for conceptual understanding; not acceptable for big `m,n`.

### Approach 2 – HashSet of nodes (O(m+n), O(m))

- **Key idea:** [prepinsta](https://prepinsta.com/leetcode-top-100-liked-questions-with-solution/intersection-of-two-linked-lists/)
  - Insert all nodes of list A into a `HashSet<ListNode>`.
  - Traverse list B; first node found in the set is the intersection; if none found, return null.
- **When used:**  
  Very straightforward; uses extra memory.

### Approach 3 – Length alignment (O(m+n), O(1))

- **Key idea:** [codeanddebug](https://codeanddebug.in/blog/intersection-of-two-linked-lists-leetcode-160/)
  - Compute lengths `lenA`, `lenB`.
  - Advance pointer of longer list by `|lenA - lenB|`.
  - Then traverse both in lockstep until they meet or both null.

### Approach 4 – Two-pointer swap trick (O(m+n), O(1), optimal)

- **Key idea:** [youtube](https://www.youtube.com/watch?v=0JHQ26NQcPk)
  - `pA = headA`, `pB = headB`.
  - While `pA != pB`:
    - `pA = (pA == null ? headB : pA.next);`
    - `pB = (pB == null ? headA : pB.next);`
  - When loop ends:
    - Either they met at intersection node, or both are null (no intersection).

Approach 4 is optimal and very popular in interviews.

***

## 4. Detailed Solutions in Java

Assume:

```java
public class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; next = null; }
}
```

### 4.1 Approach 1 – Brute Force (Conceptual Only)

#### Algorithm

1. For each node `a` in list A:
   - Walk list B from `headB`:
     - If any node `b` satisfies `a == b`, return `a`.
2. If no match found, return `null`. [prepinsta](https://prepinsta.com/leetcode-top-100-liked-questions-with-solution/intersection-of-two-linked-lists/)

#### Java Code (not for production)

```java
public class IntersectionBruteForce {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        for (ListNode a = headA; a != null; a = a.next) {
            for (ListNode b = headB; b != null; b = b.next) {
                if (a == b) { // compare by reference
                    return a;
                }
            }
        }
        return null;
    }
}
```

#### Complexity

- For each of `m` nodes in A, scan up to `n` nodes in B.
- Time: **O(m·n)**. [prepinsta](https://prepinsta.com/leetcode-top-100-liked-questions-with-solution/intersection-of-two-linked-lists/)
- Space: O(1).

Worked example omitted since this is obviously inefficient.

***

### 4.2 Approach 2 – HashSet of Nodes

#### Algorithm

1. If `headA == null` or `headB == null`, return null.
2. Create a `HashSet<ListNode> visited`.
3. Traverse list A:
   - For each node `a`, add `a` (the reference) into `visited`.
4. Traverse list B:
   - For each node `b`:
     - If `visited.contains(b)`, return `b` (first common node).
5. If loop ends, return null. [codeanddebug](https://codeanddebug.in/blog/intersection-of-two-linked-lists-leetcode-160/)

#### Java Code

```java
import java.util.HashSet;
import java.util.Set;

public class IntersectionHashSet {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        Set<ListNode> visited = new HashSet<>();

        // Store all nodes from list A
        ListNode current = headA;
        while (current != null) {
            visited.add(current);
            current = current.next;
        }

        // Check nodes from list B
        current = headB;
        while (current != null) {
            if (visited.contains(current)) {
                return current; // first common node
            }
            current = current.next;
        }

        return null;
    }
}
```

#### Complexity

- Time:
  - Traverse A: O(m).
  - Traverse B: O(n), each set lookup O(1) average.
  - Total: **O(m + n)**. [codeanddebug](https://codeanddebug.in/blog/intersection-of-two-linked-lists-leetcode-160/)
- Space:
  - HashSet stores up to m nodes → **O(m)** extra.

#### Worked Example – A: `4→1→8→4→5`, B: `5→6→1→8→4→5`, intersect at first 8

- Traverse A:
  - visited = { node(4a), node(1a), node(8), node(4b), node(5b) }.
- Traverse B:
  - 5: not in set.
  - 6: not in set.
  - 1b: not in set (different reference from 1a).
  - 8: in set → return node(8).

***

### 4.3 Approach 3 – Length Alignment

#### Algorithm

1. If either head is null, return null.
2. Compute length and tail of A:
   - `lenA = 0`, `tailA = headA`;
   - loop: `lenA++`, move until `tailA.next == null`.
3. Compute length and tail of B similarly. [prepinsta](https://prepinsta.com/leetcode-top-100-liked-questions-with-solution/intersection-of-two-linked-lists/)
4. If `tailA != tailB` (different last node references), return null (no intersection is possible).
5. Determine longer / shorter list:
   - If `lenA > lenB`, advance pointer `pA` by `lenA - lenB` steps.
   - Else, advance `pB` by `lenB - lenA` steps.
6. Now both pointers have same distance to end. Traverse both in tandem:
   - While `pA != pB`:
     - `pA = pA.next`, `pB = pB.next`.
7. Return `pA` (or `pB`), which is either intersection node or null. [codeanddebug](https://codeanddebug.in/blog/intersection-of-two-linked-lists-leetcode-160/)

#### Java Code

```java
public class IntersectionLengthAlign {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        // 1) Get lengths and tails
        int lenA = 0;
        int lenB = 0;
        ListNode tailA = headA;
        ListNode tailB = headB;

        while (tailA.next != null) {
            lenA++;
            tailA = tailA.next;
        }
        lenA++; // count last node

        while (tailB.next != null) {
            lenB++;
            tailB = tailB.next;
        }
        lenB++; // count last node

        // 2) If tails differ, no intersection
        if (tailA != tailB) {
            return null;
        }

        // 3) Align starting points
        ListNode pA = headA;
        ListNode pB = headB;

        if (lenA > lenB) {
            int diff = lenA - lenB;
            for (int i = 0; i < diff; i++) {
                pA = pA.next;
            }
        } else if (lenB > lenA) {
            int diff = lenB - lenA;
            for (int i = 0; i < diff; i++) {
                pB = pB.next;
            }
        }

        // 4) Traverse together until they meet
        while (pA != pB) {
            pA = pA.next;
            pB = pB.next;
        }

        return pA; // could be null or intersection node
    }
}
```

#### Complexity

- Time:
  - Two passes to compute lengths & tails: O(m + n).
  - One more pass to align + traverse together: ≤ O(m + n).
  - Overall: **O(m + n)**. [youtube](https://www.youtube.com/watch?v=jf99MjAq4jU)
- Space:
  - Only pointers and counters → **O(1)**.

#### Worked Example – A: `4→1→8→4→5`, B: `5→6→1→8→4→5`

Suppose:

- A length: 5 nodes.
- B length: 6 nodes.
- They share tail starting at `8`.

1. Compute lengths & tails:
   - lenA=5, tailA=last node (5).
   - lenB=6, tailB=last node (5).
   - tailA == tailB → possible intersection.

2. Align:

- lenB > lenA by 1 → advance pB one step:
  - pA=headA (4a).
  - pB=headB.next (6).

3. Traverse together:

- Step1: pA=1a, pB=1b.
- Step2: pA=8, pB=8 → meet; return node(8).

***

### 4.4 Approach 4 – Two-Pointer Switching (Optimal & Elegant)

This is the common interview favorite. [algo](https://algo.monster/liteproblems/160)

#### Core idea

- Use `pA` starting at `headA`, `pB` starting at `headB`.
- Move both one step at a time.
- When `pA` reaches end, redirect it to `headB`.
- When `pB` reaches end, redirect it to `headA`.
- Eventually:
  - If there is an intersection, `pA` and `pB` will meet there.
  - If there is no intersection, both will become `null` after traversing both lists.

Rationale:  

- Each pointer traverses exactly `m + n` nodes:
  - `pA`: A then B (m + n steps).
  - `pB`: B then A (n + m steps).
- After this equal distance, if they share any tail, they reach the same node at the same time. [youtube](https://www.youtube.com/watch?v=IpBfg9d4dmQ)

#### Algorithm

1. If `headA == null` or `headB == null`, return null.
2. Set `pA = headA`, `pB = headB`.
3. While `pA != pB`:
   - `pA = (pA == null) ? headB : pA.next;`
   - `pB = (pB == null) ? headA : pB.next;`
4. When loop ends, `pA` (and `pB`) is either:
   - Intersection node; or
   - `null` if no intersection.
5. Return `pA`. [youtube](https://www.youtube.com/watch?v=D0X0BONOQhI)

#### Java Code

```java
public class IntersectionTwoPointerSwitch {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // If either list is empty, they cannot intersect
        if (headA == null || headB == null) {
            return null;
        }

        ListNode pA = headA;
        ListNode pB = headB;

        // Traverse until the two pointers meet or both become null
        while (pA != pB) {
            // When pA reaches end, redirect to headB
            pA = (pA == null) ? headB : pA.next;

            // When pB reaches end, redirect to headA
            pB = (pB == null) ? headA : pB.next;
        }

        // Either the intersection node or null
        return pA;
    }
}
```

#### Complexity

- Each pointer traverses at most 2 * max(m, n) nodes.
- Time: **O(m + n)**. [leetcode.doocs](https://leetcode.doocs.org/en/lc/160/)
- Space: Only two pointers → **O(1)**.

#### Worked Example – A: `4→1→8→4→5`, B: `5→6→1→8→4→5`

Label nodes by identity, but focus on positions.

Initialize:

- pA = 4a (A's head)
- pB = 5b (B's head)

Step-by-step:

1. pA=1a, pB=6
2. pA=8, pB=1b
3. pA=4b, pB=8
4. pA=5b, pB=4b
5. pA=null, pB=5b
6. pA=headB (5b), pB=null
7. pA=6, pB=headA (4a)
8. pA=1b, pB=1a
9. pA=8, pB=8 → meet here → return 8.

On no-intersection example A: `[2,6,4]`, B: `[1,5]`:

- pA traverses A→B, pB traverses B→A. After `m+n` steps both become null at same time, so loop ends with `pA==pB==null` and returns null. [codeanddebug](https://codeanddebug.in/blog/intersection-of-two-linked-lists-leetcode-160/)

***

## 5. Edge Cases

1. **No intersection at all**
   - Example: A=`[2,6,4]`, B=`[1,5]`.
   - All approaches eventually return `null`.
   - Two-pointer method: both pointers end at null together.

2. **Intersection at head**
   - `headA == headB` – the lists are actually the same list.
   - All approaches return `headA`.

3. **One list is empty**
   - `headA == null` or `headB == null` → cannot intersect → return null immediately.

4. **Intersection near the end**
   - e.g., long separate prefixes but only last node shared.
   - Methods still detect correctly; two-pointer method just walks more steps.

5. **Lists of very different lengths**
   - Brute force: worst-case O(m·n).
   - Hash/length/two-pointer: still O(m+n).

6. **Values repeated but no shared nodes**
   - e.g., A=`[1,2,1]`, B=`[1,2,1]` but separate allocations.
   - Equality by **reference**, so no intersection, return null.

***

## 6. Final Summary

- Problem: find intersection node of two singly linked lists (by reference), or return null.
- Naive: compare every pair → O(m·n).
- Better: hash one list’s nodes and check the other → O(m+n) time, O(m) space.
- Better O(1) space:
  - **Length alignment:** compute lengths, align starts, walk together.
  - **Two-pointer switching (recommended):** let pointers traverse A+B and B+A; they meet at intersection or null.

Key pattern to remember:

> For “Intersection of Two Linked Lists”, the elegant trick is:
>
> - `pA = (pA == null) ? headB : pA.next;`
> - `pB = (pB == null) ? headA : pB.next;`
>
> This ensures both pointers traverse the same total distance (`m+n`), so they align at the intersection node in O(m+n) time and O(1) space. [algo](https://algo.monster/liteproblems/160)

If you want, next we can do a hand-drawn trace for a custom input you choose and step the two-pointer method line-by-line.
    */
}
