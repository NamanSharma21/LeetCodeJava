package LinkedList;

import Datastructures.ListNode;

public class ReverseLinkedList {
    public static void main(String[] args) {
        ReverseLinkedList reverseLinkedList = new ReverseLinkedList();

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        reverseLinkedList.reverseList(head);
    }

    /*
     * Given the head of a singly linked list, reverse the list, and return the
     * reversed list.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: head = [1,2,3,4,5]
     * Output: [5,4,3,2,1]
     * Example 2:
     * 
     * 
     * Input: head = [1,2]
     * Output: [2,1]
     * Example 3:
     * 
     * Input: head = []
     * Output: []
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the list is the range [0, 5000].
     * -5000 <= Node.val <= 5000
     * 
     * 
     * Follow up: A linked list can be reversed either iteratively or recursively.
     * Could you implement both?
     */

    public ListNode reverseList(ListNode head) {
        // Input: head = [1,2,3,4,5]

        ListNode newHead = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = newHead;
            newHead = head;
            head = next;
        }

        while (newHead != null) {
            System.out.println("" + newHead.val);
            newHead = newHead.next;
        }
        // ListNode[] nodeArray = new ListNode[];
        return null;
    }

}
