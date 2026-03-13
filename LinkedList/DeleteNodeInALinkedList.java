package LinkedList;

import Datastructures.ListNode;

public class DeleteNodeInALinkedList {
    public static void main(String[] args) {
        DeleteNodeInALinkedList deleteNodeInALinkedList = new DeleteNodeInALinkedList();
        ListNode head = new ListNode(4);
        head.next = new ListNode(5);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(9);
        deleteNodeInALinkedList.deleteNode(head);
    }

    /*
     * There is a singly-linked list head and we want to delete a node node in it.
     * 
     * You are given the node to be deleted node. You will not be given access to
     * the first node of head.
     * 
     * All the values of the linked list are unique, and it is guaranteed that the
     * given node node is not the last node in the linked list.
     * 
     * Delete the given node. Note that by deleting the node, we do not mean
     * removing it from memory. We mean:
     * 
     * The value of the given node should not exist in the linked list.
     * The number of nodes in the linked list should decrease by one.
     * All the values before node should be in the same order.
     * All the values after node should be in the same order.
     * Custom testing:
     * 
     * For the input, you should provide the entire linked list head and the node to
     * be given node. node should not be the last node of the list and should be an
     * actual node in the list.
     * We will build the linked list and pass the node to your function.
     * The output will be the entire list after calling your function.
     * 
     * Input: head = [4,5,1,9], node = 5
     * Output: [4,1,9]
     * Explanation: You are given the second node with value 5, the linked list
     * should become 4 -> 1 -> 9 after calling your function.
     * 
     * 
     * Input: head = [4,5,1,9], node = 1
     * Output: [4,5,9]
     * Explanation: You are given the third node with value 1, the linked list
     * should become 4 -> 5 -> 9 after calling your function.
     * 
     * Constraints:
     * 
     * The number of the nodes in the given list is in the range [2, 1000].
     * -1000 <= Node.val <= 1000
     * The value of each node in the list is unique.
     * The node to be deleted is in the list and is not a tail node.
     */
    public void deleteNode(ListNode node) {
        ListNode tempNode = null;
        ListNode copy = new ListNode();
        ListNode prevListNode = new ListNode();
        copy = node;
        while (node != null) {
            System.out.println(node.val);
            if (node.val == 5) {
                tempNode = node.next;
                System.out.println("Found : 5 " + tempNode.val);
                break;
            }
            prevListNode = node;
            node = node.next;
        }
        if (tempNode != null) {
            prevListNode.next = tempNode;
        }
        while (prevListNode != null) {
            System.out.print("" + prevListNode.val);
            prevListNode = prevListNode.next;
        }
        // node.val = node.next.val;
        // node.next = node.next.next;
    }
}
