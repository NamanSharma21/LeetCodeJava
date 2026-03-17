package LinkedList;

import Datastructures.ListNode;

public class PartitionList {
    public static void main(String[] args) {
        PartitionList partitionList = new PartitionList();
        ListNode head = new ListNode(1);
        head.next = new ListNode(4);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(2);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(2);
        System.out.println("PartitionList : " + partitionList.partition(head, 3));

    }

    /*
     * https://leetcode.com/problems/partition-list/description/?envType=problem-
     * list-v2&envId=linked-list
     * 
     * 
     * Given the head of a linked list and a value x, partition it such that all
     * nodes less than x come before nodes greater than or equal to x.
     * 
     * You should preserve the original relative order of the nodes in each of the
     * two partitions.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: head = [1,4,3,2,5,2], x = 3
     * Output: [1,2,2,4,3,5]
     * Example 2:
     * 
     * Input: head = [2,1], x = 2
     * Output: [1,2]
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the list is in the range [0, 200].
     * -100 <= Node.val <= 100
     * -200 <= x <= 200
     */

    public ListNode partition(ListNode head, int x) {
        ListNode leftDummy = new ListNode(0);
        ListNode rightDummy = new ListNode(0);
        ListNode leftTail = leftDummy;
        ListNode rightTail = rightDummy;
        ListNode current = head;
        while (current != null) {
            ListNode nextNode = current.next;
            if (current.val < x) {
                leftTail.next = current;
                leftTail = leftTail.next;
            } else {
                rightTail.next = current;
                rightTail = rightTail.next;
            }
            current = nextNode;
        }

        rightTail.next = null;
        leftTail.next = rightDummy.next;
        return leftDummy.next;
    }
}
