package LinkedList;

import Datastructures.ListNode;

public class MergeTwoSortedLists {
    public static void main(String[] args) {
        MergeTwoSortedLists mergeTwoSortedLists = new MergeTwoSortedLists();

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(4);

        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(3);
        head1.next.next = new ListNode(4);

        mergeTwoSortedLists.mergeTwoLists(head, head1);
    }

    /*
     * You are given the heads of two sorted linked lists list1 and list2.
     * 
     * Merge the two lists into one sorted list. The list should be made by splicing
     * together the nodes of the first two lists.
     * 
     * Return the head of the merged linked list.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: list1 = [1,2,4], list2 = [1,3,4]
     * Output: [1,1,2,3,4,4]
     * Example 2:
     * 
     * Input: list1 = [], list2 = []
     * Output: []
     * Example 3:
     * 
     * Input: list1 = [], list2 = [0]
     * Output: [0]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in both lists is in the range [0, 50].
     * -100 <= Node.val <= 100
     * Both list1 and list2 are sorted in non-decreasing order.
     */

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // Input: list1 = [1,2,4], list2 = [1,3,4]
        // ListNode merged = list1;
        // while (list1 != null) {
        // if (list2 != null) {
        // if (list1.val < list2.val && list2.val < list1.next.val) {
        // list2.next = list1.next;
        // list1.next = list2;
        // }
        // // System.out.print("" + list1.val + "--" + list2.val);

        // list2 = list2.next;
        // }
        // list1 = list1.next;
        // merged = list1;
        // }

        // ListNode copy = new ListNode();
        // copy = list2;

        // while (copy != null) {
        // System.out.print("" + copy.val);
        // copy = copy.next;
        // }
        // return null;

        // if (list1 == null)
        // return list2;
        // if (list2 == null)
        // return list1;
        // if (list1.val < list2.val) {
        // list1.next = mergeTwoLists(list1.next, list2);
        // return list1;
        // } else {
        // list2.next = mergeTwoLists(list1, list2.next);
        // return list2;
        // }

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                current = list1.next;
                list1 = list1.next;
            } else {
                current = list2.next;
                list2 = list2.next;
            }
            current = current.next;
        }
        if (list1 != null) {
            current.next = list1;
        }
        if (list2 != null) {
            current.next = list2;
        }

        return dummy.next;

    }
}
