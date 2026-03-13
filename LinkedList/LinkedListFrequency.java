package LinkedList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Datastructures.ListNode;

public class LinkedListFrequency {
    public static void main(String[] args) {
        LinkedListFrequency linkedListFrequency = new LinkedListFrequency();
        ListNode head = new ListNode(1);
        head.next = new ListNode(1);
        head.next.next = new ListNode(2);
        head.next.next.next = new ListNode(1);
        head.next.next.next.next = new ListNode(1);
        System.out.println("LinkedListFrequency : " + linkedListFrequency.frequenciesOfElements(head));
    }

    /*
     * 1. Problem Statement
     * Given the head of a singly linked list where values may repeat, return a new
     * linked list where each node's value represents the frequency (count) of each
     * distinct consecutive group of equal values in the original list.
     * Input: Head of a linked list (e.g., 1 → 1 → 2 → 1 → 1)
     * Output: A new linked list of frequencies (e.g., 2 → 1 → 2)
     * Constraints:
     * 
     * The number of nodes is in range [1, 10^5]
     * 1 <= Node.val <= 10^5
     * This is about consecutive runs, not global frequency
     * 
     * What to compute: Traverse the list, group consecutive equal values, count
     * each group, and emit those counts as a new linked list in order.
     */

    public ListNode frequenciesOfElements(ListNode head) {
        ListNode freqListNode = new ListNode(0);
        ListNode resultTail = freqListNode;
        ListNode dummy = head;
        while (dummy != null) {
            int runVal = dummy.val;
            int count = 0;
            while (dummy != null && dummy.val == runVal) {
                count++;
                dummy = dummy.next;
            }
            resultTail.next = new ListNode(count);
            resultTail = resultTail.next;
        }
        return freqListNode.next;
    }
}
