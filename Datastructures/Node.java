package Datastructures;

public class Node {
    public int val;
    public Node prev;
    public Node next;

    public Node() {
    }

    public Node(int val) {
        this.val = val;
        this.prev = null;
        this.next = null;
    }

    public Node(int val, Node prev, Node next) {
        this.val = val;
        this.prev = prev;
        this.next = next;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node current = this;
        while (current != null) {
            sb.append(current.val);
            if (current.next != null) {
                sb.append(" <-> ");
            }
            current = current.next;
        }
        return sb.toString();
    }
}
