package Datastructures;

public class NodeTree {
    public int val;
    public NodeTree left;
    public NodeTree right;
    public NodeTree next;

    // Default constructor
    public NodeTree() {
    }

    // Constructor with value
    public NodeTree(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
        this.next = null;
    }

    // Constructor with value and children
    public NodeTree(int val, NodeTree left, NodeTree right, NodeTree next) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.next = next;
    }

    // toString method - shows NodeTree value and its connections
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NodeTree{");
        sb.append("val=").append(val);
        sb.append(", left=").append(left != null ? left.val : "null");
        sb.append(", right=").append(right != null ? right.val : "null");
        sb.append(", next=").append(next != null ? next.val : "null");
        sb.append("}");
        return sb.toString();
    }

    // Pretty-print the entire tree rooted at this NodeTree (BFS level-order)
    public String toTreeString() {
        if (this == null)
            return "Empty tree";

        StringBuilder sb = new StringBuilder();
        java.util.Queue<NodeTree> queue = new java.util.LinkedList<>();
        queue.offer(this);

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            sb.append("Level ").append(level).append(": ");

            for (int i = 0; i < size; i++) {
                NodeTree curr = queue.poll();
                if (curr == null) {
                    sb.append("null");
                } else {
                    sb.append(curr.val);
                    if (curr.next != null)
                        sb.append(" -> ").append(curr.next.val);
                    queue.offer(curr.left);
                    queue.offer(curr.right);
                }
                if (i < size - 1)
                    sb.append(", ");
            }
            sb.append("\n");
            level++;

            // Stop if all remaining NodeTrees are null
            if (queue.stream().allMatch(n -> n == null))
                break;
        }
        return sb.toString();
    }

    // equals: two NodeTrees are equal if they have the same value
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof NodeTree))
            return false;
        NodeTree other = (NodeTree) obj;
        return this.val == other.val;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(val);
    }
}
