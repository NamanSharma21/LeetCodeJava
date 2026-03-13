package LinkedList;

import Datastructures.ListNode;

public class PalindromeLinkedList {
    public static void main(String[] args) {
        PalindromeLinkedList palindromeLinkedList = new PalindromeLinkedList();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(2);
        head.next.next.next = new ListNode(1);
        // System.out.println("Is Palindrome List : " +
        // palindromeLinkedList.isPalindrome(head));
        System.out.println("Is Palindrome List : " + palindromeLinkedList.isPalindrome2(head));

        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        // System.out.println("Is Palindrome List : " + palindromeLinkedList.isPalindrome(head1));
        System.out.println("Is Palindrome List : " + palindromeLinkedList.isPalindrome2(head1));

    }

    /*
     * Given the head of a singly linked list, return true if it is a palindrome or
     * false otherwise.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: head = [1,2,2,1]
     * Output: true
     * Example 2:
     * 
     * 
     * Input: head = [1,2]
     * Output: false
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the list is in the range [1, 105].
     * 0 <= Node.val <= 9
     * 
     * 
     * Follow up: Could you do it in O(n) time and O(1) space?
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * Idea:
     * The naive approach here would be to run through the linked list and create an
     * array of its values, then compare the array to its reverse to find out if
     * it's a palindrome. Though this is easy enough to accomplish, we're challenged
     * to find an approach with a space complexity of only O(1) while maintaining a
     * time complexity of O(N).
     * 
     * 
     * The only way to check for a palindrome in O(1) space would require us to be
     * able to access both nodes for comparison at the same time, rather than
     * storing values for later comparison. This would seem to be a challenge, as
     * the linked list only promotes travel in one direction.
     * 
     * 
     * But what if it didn't?
     * 
     * 
     * The answer is to reverse the back half of the linked list to have the next
     * attribute point to the previous node instead of the next node. (Note: we
     * could instead add a prev attribute as we iterate through the linked list,
     * rather than overwriting next on the back half, but that would technically use
     * O(N) extra space, just as if we'd created an external array of node values.)
     * 
     * 
     * The first challenge then becomes finding the middle of the linked list in
     * order to start our reversing process there. For that, we can look to Floyd's
     * Cycle Detection Algorithm.
     * 
     * 
     * With Floyd's, we'll travel through the linked list with two pointers, one of
     * which is moving twice as fast as the other. When the fast pointer reaches the
     * end of the list, the slow pointer must then be in the middle.
     * Diagram 1
     * With slow now at the middle, we can reverse the back half of the list with
     * the help of another variable to contain a reference to the previous node
     * (prev) and a three-way swap. Before we do this, however, we'll want to set
     * prev.next = null, so that we break the reverse cycle and avoid an endless
     * loop.
     * Diagram 2
     * Once the back half is properly reversed and slow is once again at the end of
     * the list, we can now start fast back over again at the head and compare the
     * two halves simultaneously, with no extra space required.
     * Diagram 3
     * If the two pointers ever disagree in value, we can return false, otherwise we
     * can return true if both pointers reach the middle successfully.
     * 
     * 
     * (Note: This process works regardless of whether the length of the linked list
     * is odd or even, as the comparison will stop when slow reaches the "dead-end"
     * node.)
     * 
     * 
     * Diagram 4
     * 
     */

    public boolean isPalindrome(ListNode head) {
        // ListNode prevNode = null;
        // ListNode copy = deepCopy(head);

        // while (head != null) {
        // ListNode next = head.next;
        // head.next = prevNode;
        // prevNode = head;
        // head = next;
        // }

        // while (prevNode != null && copy != null) {
        // System.out.print("|" + prevNode.val + " : " + copy.val);
        // if (prevNode.val != copy.val) {
        // return false;
        // }
        // prevNode = prevNode.next;
        // copy = copy.next;
        // }
        // return true;

        ListNode fast = head, slow = head, prev, temp;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        prev = slow;
        slow = slow.next;
        prev.next = null;
        while (slow != null) {
            temp = slow.next;
            slow.next = prev;
            prev = slow;
            slow = temp;
        }

        fast = head;
        slow = prev;
        while (slow != null) {
            if (slow.val != fast.val)
                return false;
            slow = slow.next;
            fast = fast.next;
        }
        return true;
    }

    public ListNode deepCopy(ListNode head) {
        ListNode dummy = new ListNode(0);
        ListNode copy = dummy;
        ListNode currentOrig = head;
        while (currentOrig != null) {
            copy.next = new ListNode(currentOrig.val);
            copy = copy.next;
            currentOrig = currentOrig.next;
        }
        return dummy.next;
    }

    public boolean isPalindrome2(ListNode head) {

        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode prev = null;
        ListNode curr = slow.next;
        while (curr != null) {
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }

        ListNode p1 = head;
        while (prev != null) {
            if (p1.val != prev.val) {
                return false;
            }
            prev = prev.next;
            p1 = p1.next;
        }

        return true;
    }

    /*
    
    ## 1. Problem Statement

Given the `head` of a **singly linked list**, return `true` if the list is a **palindrome**, and `false` otherwise. [leetcode.doocs](https://leetcode.doocs.org/en/lc/234/)

A linked list is a palindrome if the sequence of node values is the same **forward and backward**.  

Example:

- `1 → 2 → 2 → 1` → palindrome → `true`
- `1 → 2` → not palindrome → `false` [codeanddebug](https://codeanddebug.in/blog/palindrome-linked-list-leetcode-234/)

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

- **Input:** `ListNode head`
- **Output:** `boolean` – `true` if the list is palindrome, else `false`. [leetcode.doocs](https://leetcode.doocs.org/en/lc/234/)
- Constraints (LeetCode 234 style): [codeanddebug](https://codeanddebug.in/blog/palindrome-linked-list-leetcode-234/)
  - Number of nodes: `1 <= n <= 10^5`
  - `0 <= Node.val <= 9`
- Follow-up: can you do it in **O(n)** time and **O(1)** extra space? [github](https://github.com/doocs/leetcode/blob/main/solution/0200-0299/0234.Palindrome%20Linked%20List/README_EN.md)

You must compute:  
Does the sequence `[head.val, head.next.val, ...]` equal its reverse?

***

## 2. Intuition

Brute-force idea:

- Traverse list, copy values into an array, then check if the array is a palindrome (compare `arr[i]` with `arr[n-1-i]`). [scaler](https://www.scaler.in/palindrome-linked-list/)
- Time O(n), space O(n). Simple but doesn’t satisfy the follow-up.

To get **O(1) extra space**, you need to avoid storing all values. Key trick:

1. Use **fast and slow pointers** to find the **middle** of the list.
2. **Reverse the second half** of the list **in-place**.
3. Compare the first half and the reversed second half node by node.
4. Optionally restore the list (reverse back second half). [algo](https://algo.monster/liteproblems/234)

What makes it interesting:

- Uses multiple classic linked-list patterns:
  - Fast/slow pointers to find middle.
  - In-place reversal of a list.
  - Comparing two list halves.
- Must be careful with **odd vs even length** and pointer boundaries. [leetcodee](https://leetcodee.com/problems/palindrome-linked-list/)

***

## 3. Approach Overview

Assume `n` = number of nodes.

### Approach 1 – Array / List copy (O(n) time, O(n) space)

- **Key idea:**  
  Copy all node values into an `ArrayList<Integer>` or array, then use two-pointer palindrome check on that array. [scaler](https://www.scaler.in/palindrome-linked-list/)
- **When used:**  
  Very straightforward; good first solution. Does not meet O(1) space follow-up.

### Approach 2 – Stack (O(n) time, O(n) space)

- **Key idea:**  
  Use a stack to store values from first half; then pop and compare with second half values. [youtube](https://www.youtube.com/watch?v=WyI5dXMHW5c)
- **When used:**  
  Good if you want to emphasize stack/LIFO properties. Still O(n) space.

### Approach 3 – Optimal: fast/slow + reverse second half + compare (O(n) time, O(1) space)

- **Key idea:** [geeksforgeeks](https://www.geeksforgeeks.org/dsa/function-to-check-if-a-singly-linked-list-is-palindrome/)
  1. Use fast/slow pointers to find middle.
  2. Reverse the second half of the list.
  3. Compare first half and reversed second half.
  4. Optionally restore the list by reversing second half again.
- **When used:**  
  This is the standard interview solution and satisfies follow-up.

Approach 3 is optimal; Approach 1 is the easiest to write and reason about.

***

## 4. Detailed Solutions in Java

### 4.1 Approach 1 – Copy to Array / List

#### Algorithm

1. Traverse the linked list, pushing `head.val` into an `ArrayList<Integer> values`.
2. Use two indices: `left = 0`, `right = values.size() - 1`.
3. While `left < right`:
   - If `values.get(left) != values.get(right)`, return `false`.
   - `left++`, `right--`.
4. If loop finishes, return `true`. [leetcode.doocs](https://leetcode.doocs.org/en/lc/234/)

#### Java Code

```java
import java.util.ArrayList;
import java.util.List;

public class PalindromeLinkedListArray {

    public boolean isPalindrome(ListNode head) {
        List<Integer> values = new ArrayList<>();

        // Copy list values into array list
        ListNode curr = head;
        while (curr != null) {
            values.add(curr.val);
            curr = curr.next;
        }

        // Two-pointer check on the values list
        int left = 0;
        int right = values.size() - 1;

        while (left < right) {
            if (!values.get(left).equals(values.get(right))) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }
}
```

#### Complexity

- **Time:**  
  - Traverse list once to fill array: O(n).
  - Two-pointer check: O(n).
  - Total: **O(n)**. [codeanddebug](https://codeanddebug.in/blog/palindrome-linked-list-leetcode-234/)
- **Space:**  
  - Array list stores all n values → **O(n)** extra.

#### Worked Example – `1 → 2 → 2 → 1`

- Copy step:
  - values =. [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
- Check:
  - left=0,right=3 → 1==1 OK.
  - left=1,right=2 → 2==2 OK.
  - left=2,right=1 stop.
- No mismatch → `true`.

***

### 4.2 Approach 2 – Stack-based

#### Algorithm

Idea: store the first half in a stack, then compare while traversing the second half. [youtube](https://www.youtube.com/watch?v=WyI5dXMHW5c)

1. Use fast/slow pointers:
   - `slow = head`, `fast = head`.
   - While `fast != null && fast.next != null`:
     - Push `slow.val` onto stack.
     - `slow = slow.next`
     - `fast = fast.next.next`
   - After loop, slow is at middle.
2. If `fast != null` (odd length), skip the middle element:
   - `slow = slow.next`.
3. Now `slow` points to start of second half. While `slow != null`:
   - Pop `top` from stack.
   - If `top != slow.val`, return false.
   - `slow = slow.next`.
4. If all matched, return true.

#### Java Code

```java
import java.util.Stack;

public class PalindromeLinkedListStack {

    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;

        Stack<Integer> stack = new Stack<>();

        ListNode slow = head;
        ListNode fast = head;

        // Push first half values into stack
        while (fast != null && fast.next != null) {
            stack.push(slow.val);
            slow = slow.next;
            fast = fast.next.next;
        }

        // If odd length, skip the middle node
        if (fast != null) {
            slow = slow.next;
        }

        // Compare second half with values from stack
        while (slow != null) {
            int top = stack.pop();
            if (top != slow.val) {
                return false;
            }
            slow = slow.next;
        }

        return true;
    }
}
```

#### Complexity

- **Time:** Each node is visited at most once → **O(n)**. [geeksforgeeks](https://www.geeksforgeeks.org/dsa/function-to-check-if-a-singly-linked-list-is-palindrome/)
- **Space:** Stack holds about n/2 values → **O(n)** extra.

#### Worked Example – `1 → 2 → 2 → 1`

- slow=1, fast=1.
- Loop:
  - Iter1: push 1, slow=2, fast jumps to 2 (third node).
  - Iter2: fast.next null?  
    - fast at 2 (third), fast.next=1 (fourth), fast.next.next=null → loop ends after second iter.
  - Stack: ? (depends on exact loop; with given code, we push slow before moving fast). [geeksforgeeks](https://www.geeksforgeeks.org/dsa/next-permutation/)
  Actually precisely:
  - Iter1: fast/head=1, fast.next!=null → push slow.val=1; slow=2; fast=fast.next.next=3rd node (2).
  - Iter2: fast!=null && fast.next!=null? fast=3rd node, fast.next=4th node not null → push slow.val=2; slow=3rd node? Wait slow already 2 second node; but after iter1 slow=second node; iter2 pushes 2, slow moves to 3rd (2), fast moves to fast.next.next= null. So stack. [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
- fast=null → even length, skip middle? fast==null, so no skip; slow at 3rd node (2).
- Compare:
  - Pop 2 vs slow.val=2 → OK.
  - slow moves to 4th (1); pop 1 vs 1 → OK.
- Done → true.

(Exact pointer positions depend on initial conditions, but idea holds.)

***

### 4.3 Approach 3 – Optimal: Reverse Second Half In-place and Compare

This is the follow-up solution: **O(n)** time, **O(1)** extra space. [youtube](https://www.youtube.com/watch?v=Q68Lwd_IpXw)

#### High-level steps

1. **Find middle** of the list using fast/slow pointers.
2. **Reverse the second half** of the list starting from middle’s next node (for even/odd properly).
3. **Compare** first half and reversed second half node by node.
4. Optionally **restore** the list to original by reversing second half again.

#### Step 1: Find middle

Standard pattern:

```java
ListNode slow = head;
ListNode fast = head;

while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}
```

After loop:

- For even length (e.g., 1→2→2→1), `slow` ends at first node of **second half** or just past first half depending on variant.
- For odd length (e.g., 1→2→3→2→1), `slow` ends at **middle** node. [algo](https://algo.monster/liteproblems/234)

For LeetCode 234 typical implementation: [leetcodee](https://leetcodee.com/problems/palindrome-linked-list/)

- Let slow stop at the **end of first half**, then reverse from `slow.next`.

We can use the pattern:

```java
private ListNode endOfFirstHalf(ListNode head) {
    ListNode fast = head;
    ListNode slow = head;
    while (fast.next != null && fast.next.next != null) {
        fast = fast.next.next;
        slow = slow.next;
    }
    return slow;
}
```

This makes `slow` point to end of first half for both even and odd lengths. [github](https://github.com/doocs/leetcode/blob/main/solution/0200-0299/0234.Palindrome%20Linked%20List/README_EN.md)

#### Step 2: Reverse second half

Standard iterative reverse:

```java
private ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    while (curr != null) {
        ListNode nextTemp = curr.next;
        curr.next = prev;
        prev = curr;
        curr = nextTemp;
    }
    return prev; // new head of reversed list
}
```

#### Step 3: Compare halves

- `p1` from `head` (start of first half).
- `p2` from `secondHalfHead` (reversed second half).
- While `p2 != null`:
  - If `p1.val != p2.val`, not palindrome → false.
  - Move both.

#### Java Code (full optimal solution)

```java
public class PalindromeLinkedListOptimal {

    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        // 1) Find the end of the first half
        ListNode firstHalfEnd = endOfFirstHalf(head);

        // 2) Reverse the second half
        ListNode secondHalfStart = reverseList(firstHalfEnd.next);

        // 3) Compare the first and the reversed second halves
        ListNode p1 = head;
        ListNode p2 = secondHalfStart;
        boolean isPalin = true;

        while (p2 != null) { // only need to check second half
            if (p1.val != p2.val) {
                isPalin = false;
                break;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // 4) (Optional) Restore the list by reversing the second half again
        firstHalfEnd.next = reverseList(secondHalfStart);

        return isPalin;
    }

    // Helper: find the end of the first half using fast/slow pointers
    private ListNode endOfFirstHalf(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        // Move fast twice as quickly as slow
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow; // slow is at end of first half
    }

    // Helper: reverse a singly linked list
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }

        return prev;
    }
}
```

This pattern matches common editorial and blog solutions. [youtube](https://www.youtube.com/watch?v=Q68Lwd_IpXw)

#### Complexity

- **Time:**  
  - Finding middle: O(n).
  - Reversing second half: O(n).
  - Comparing halves: O(n).
  - Reversing back: O(n).
  - Overall: **O(n)**. [leetcodee](https://leetcodee.com/problems/palindrome-linked-list/)
- **Space:**  
  - Only a few pointers; reversal is in-place.
  - Extra space: **O(1)**. [github](https://github.com/doocs/leetcode/blob/main/solution/0200-0299/0234.Palindrome%20Linked%20List/README_EN.md)

#### Worked Example – `head = [1,2,2,1]` (even length)

1. Original list: `1 → 2 → 2 → 1`.

**Find end of first half**

- slow=1, fast=1.
- loop condition `fast.next != null && fast.next.next != null`:
  - Iter1: fast.next=2, fast.next.next=2 → true.  
    - fast=fast.next.next → fast=2 (3rd node).  
    - slow=slow.next → slow=2 (2nd node).
  - Iter2: fast.next=1, fast.next.next=null → condition fails, stop.
- `firstHalfEnd = slow` (2nd node, value 2).  
  First half: `1 → 2`. Second half starts at `firstHalfEnd.next` (3rd node: 2).

**Reverse second half**

- secondHalfStart = reverseList(3rd node list: `2→1`).
- Reversal step:
  - prev=null, curr=2:
    - nextTemp=1; curr.next=prev (null); prev=2; curr=1.
  - curr=1:
    - nextTemp=null; curr.next=2; prev=1; curr=null.
- secondHalfStart = prev = node 1, list: `1 → 2`.

Now structure (conceptually):

- first half: `1 → 2 → (link to reversed second half?)`  
  After reversing, `firstHalfEnd.next` still points to old head of second half (but we treat them logically separate).
- reversed second half: `1 → 2`.

**Compare halves**

- p1=head (1), p2=secondHalfStart (1).
- Iter1: compare 1 vs 1 → ok; p1=2 (2nd), p2=2 (2nd of reversed).
- Iter2: compare 2 vs 2 → ok; p1=3rd, p2=null.
- p2==null → stop, isPalin = true.

**Restore** (optional)

- firstHalfEnd.next = reverseList(secondHalfStart) → reverse `1→2` back to `2→1`.  
- List restored to `1 → 2 → 2 → 1`.

Return `true`.

#### Worked Example – `head = [1,2,3,2,1]` (odd length)

- endOfFirstHalf:
  - slow reaches node with value 3 (middle).
- secondHalfStart = reverseList(slow.next), i.e., reverse `2→1` → `1→2`.
- first half: `1→2→3` (but we only compare up to length of second half).
- p1=head(1), p2=1 → equal → move.
- p1=2, p2=2 → equal → move.
- p2=null → done → palindrome.

Middle element (3) is naturally skipped.

***

## 5. Edge Cases

1. **Single node**: ` [leetcode.doocs](https://leetcode.doocs.org/en/lc/234/)`
   - Any single-element list is a palindrome → true.
   - Approach 3:
     - endOfFirstHalf=head (1), secondHalfStart=reverseList(null)=null.
     - p2==null → loop skipped, isPalin=true.

2. **Two nodes equal / not equal**: `[1,1]` vs `[1,2]`
   - `[1,1]` → palindrome.
   - `[1,2]` → not.
   - Approach 3 handles both via second-half reversal and comparison.

3. **All nodes equal**: `[2,2,2,2]`
   - Always palindrome; comparisons all match.

4. **Large list** (`n = 10^5`)
   - Approach 1 & 2: time O(n), space O(n) – may be fine, but not optimal.
   - Approach 3: time O(n), space O(1) – best for follow-up. [leetcode.doocs](https://leetcode.doocs.org/en/lc/234/)

5. **Even vs odd length**:
   - Approach 3’s `endOfFirstHalf` logic properly handles both by comparing length of second half only.

***

## 6. Final Summary

- Problem: Given a singly linked list, check if it’s a **palindrome** (values read same forward and backward).
- Simple solution: copy values to array and do two-pointer palindrome check (O(n) time, O(n) space).
- Stack solution: use stack for first half, compare with second half (also O(n) time, O(n) space).
- **Optimal solution (recommended)**:  
  - Find end of first half using fast/slow pointers.
  - Reverse the second half **in-place**.
  - Compare first half and reversed second half.
  - Optionally restore the list.
  - O(n) time, **O(1)** extra space. [algo](https://algo.monster/liteproblems/234)

Key pattern to remember:

> This is a classic **fast/slow + in-place reverse + compare** linked-list problem.  
> Whenever you see “palindrome linked list in O(1) space”, think **“find middle, reverse second half, compare two halves”**.

If you want, next we can write a small helper to print the list before/after the optimal algorithm and dry-run it on a custom tricky case you choose.
    
    */
}
