package LinkedList;


import Datastructures.ListNode;

public class RemoveNthNodeFromEndOfList {
    public static void main(String[] args) {
        RemoveNthNodeFromEndOfList removeNthNodeFromEndOfList = new RemoveNthNodeFromEndOfList();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next = new ListNode(5);
        removeNthNodeFromEndOfList.removeNthFromEnd(head, 2);

        ListNode head1 = new ListNode(1);
        removeNthNodeFromEndOfList.removeNthFromEnd(head1, 2);

        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(2);
        removeNthNodeFromEndOfList.removeNthFromEnd(head2, 1);
    }

    /*
     * Given the head of a linked list, remove the nth node from the end of the list
     * and return its head.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: head = [1,2,3,4,5], n = 2
     * Output: [1,2,3,5]
     * Example 2:
     * 
     * Input: head = [1], n = 1
     * Output: []
     * Example 3:
     * 
     * Input: head = [1,2], n = 1
     * Output: [1]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the list is sz.
     * 1 <= sz <= 30
     * 0 <= Node.val <= 100
     * 1 <= n <= sz
     * 
     * 
     * Follow up: Could you do this in one pass?
     */

    public ListNode removeNthFromEnd(ListNode head, int n) {
        // if (head.next == null) {
        // return new ListNode();
        // }
        // ListNode headCopy = new ListNode();
        // headCopy = head;

        // int counter = 0;
        // while (headCopy != null) {
        // counter++;
        // headCopy = headCopy.next;
        // }

        // headCopy = head;
        // int idx = counter - n;
        // System.out.println("Total Items : " + counter + " idx : " + idx);
        // counter = 0;
        // ListNode prevListNode = new ListNode();
        // ListNode tempListNode = new ListNode();
        // while (headCopy != null) {
        // if (counter == idx && headCopy.next != null) {
        // tempListNode = headCopy.next.next;
        // break;
        // }
        // counter++;
        // prevListNode = headCopy;
        // headCopy = headCopy.next;
        // }
        // counter = 0;
        // headCopy = head;
        // if (tempListNode != null) {
        // prevListNode.next = tempListNode;
        // head = prevListNode;

        // while (headCopy != null) {
        // counter++;
        // if (counter == idx) {
        // headCopy.next = tempListNode;
        // break;
        // }
        // headCopy = headCopy.next;
        // }
        // }
        // head = headCopy;
        // while (headCopy != null) {
        // System.out.println("" + headCopy.val);
        // headCopy = headCopy.next;
        // }

        // return head;
        if(head.next==null){
            return new ListNode();
        }
        ListNode fast = head, slow = head;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        if (fast == null)
            return head.next;
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        ListNode copy = new ListNode();
        copy = head;
        while (copy != null) {
            System.out.println("" + copy.val);
            copy = copy.next;
        }
        return head;
    }
}
