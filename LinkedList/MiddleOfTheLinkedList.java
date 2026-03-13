package LinkedList;

import Datastructures.ListNode;

public class MiddleOfTheLinkedList {
    public static void main(String[] args) {
        MiddleOfTheLinkedList middleOfTheLinkedList = new MiddleOfTheLinkedList();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        System.out.println("MiddleOfTheLinkedList : " + middleOfTheLinkedList.middleNode(head));
        head.next.next.next.next.next = new ListNode(6);
        System.out.println("MiddleOfTheLinkedList : " + middleOfTheLinkedList.middleNode(head));
    }

    /*
     * https://leetcode.com/problems/middle-of-the-linked-list/description/?envType=
     * problem-list-v2&envId=linked-list
     * 
     * 
     * Given the head of a singly linked list, return the middle node of the linked
     * list.
     * 
     * If there are two middle nodes, return the second middle node.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: head = [1,2,3,4,5]
     * Output: [3,4,5]
     * Explanation: The middle node of the list is node 3.
     * Example 2:
     * 
     * 
     * Input: head = [1,2,3,4,5,6]
     * Output: [4,5,6]
     * Explanation: Since the list has two middle nodes with values 3 and 4, we
     * return the second one.
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the list is in the range [1, 100].
     * 1 <= Node.val <= 100
     */

    public ListNode middleNode(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
}
