package LinkedList;

import Datastructures.ListNode;

public class RemoveDuplicatesFromSortedList {
    public static void main(String[] args) {
        RemoveDuplicatesFromSortedList removeDuplicatesFromSortedList = new RemoveDuplicatesFromSortedList();
        ListNode head = new ListNode(1);
        head.next = new ListNode(1);
        head.next.next = new ListNode(2);
        System.out.println("RemoveDuplicatesFromSortedList : " + removeDuplicatesFromSortedList.deleteDuplicates(head));
    }

    /*
     * Given the head of a sorted linked list, delete all duplicates such that each
     * element appears only once. Return the linked list sorted as well.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: head = [1,1,2]
     * Output: [1,2]
     * Example 2:
     * 
     * 
     * Input: head = [1,1,2,3,3]
     * Output: [1,2,3]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the list is in the range [0, 300].
     * -100 <= Node.val <= 100
     * The list is guaranteed to be sorted in ascending order.
     */

    public ListNode deleteDuplicates(ListNode head) {
        ListNode last = head;
        while (last != null) {
            if (last.next != null && last.val == last.next.val) {
                last.next = last.next.next;
            } else {
                last = last.next;
            }
        }
        return head;
    }
}
