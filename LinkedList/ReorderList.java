package LinkedList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import Datastructures.ListNode;

public class ReorderList {
    public static void main(String[] args) {
        ReorderList reorderList = new ReorderList();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        System.out.println("ReorderList : " + reorderList.reorderListApproach1(head));
    }

    /*
     * 
     * https://leetcode.com/problems/reorder-list/description/?envType=problem-list-
     * v2&envId=linked-list
     * 
     * 
     * You are given the head of a singly linked-list. The list can be represented
     * as:
     * 
     * L0 → L1 → … → Ln - 1 → Ln
     * Reorder the list to be on the following form:
     * 
     * L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
     * You may not modify the values in the list's nodes. Only nodes themselves may
     * be changed.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: head = [1,2,3,4]
     * Output: [1,4,2,3]
     * Example 2:
     * 
     * 
     * Input: head = [1,2,3,4,5]
     * Output: [1,5,2,4,3]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the list is in the range [1, 5 * 104].
     * 1 <= Node.val <= 1000
     */

    public ListNode reorderListApproach1(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode curr = head;
        List<ListNode> nodeList = new ArrayList<>();
        while (curr != null) {
            nodeList.add(curr);
            curr = curr.next;
        }

        int left = 0;
        int right = nodeList.size() - 1;
        while (left < right) {
            nodeList.get(left).next = nodeList.get(right);
            left++;
            if (left == right)
                break;

            nodeList.get(right).next = nodeList.get(left);
            right--;
        }

        nodeList.get(left).next = null;
        return nodeList.get(0);
    }

    public ListNode reorderListApproach2(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode current = head;
        Deque<ListNode> deque = new ArrayDeque<>();

        while (current != null) {
            deque.addLast(current);
            current = current.next;
        }
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        boolean pickFirst = true;
        while (!deque.isEmpty()) {
            tail.next = pickFirst ? deque.pollFirst() : deque.pollLast();
            tail = tail.next;
            pickFirst = !pickFirst;
        }
        tail.next = null;
        return tail;
    }

    public void reorderListApproach3(ListNode head) {
        ListNode middle = findMiddle(head);
        ListNode secondHalf = reverseList(middle.next);
        middle.next = null;
        mergeList(head, secondHalf);
    }

    public ListNode findMiddle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public ListNode reverseList(ListNode head) {
        ListNode curr = head;
        ListNode prev = null;
        while (curr != null) {
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }
        return prev;
    }

    public ListNode mergeList(ListNode first, ListNode second) {
        while (second != null) {
            ListNode firstNext = first.next;
            ListNode secondNext = second.next;

            first.next = second;
            second.next = firstNext;

            first = firstNext;
            second = secondNext;
        }
        return null;
    }
}
