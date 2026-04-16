package Datastructures;

public class TreeNode {

    /*
     * Tree Datastructure Documentation
     * 
     * https://www.geeksforgeeks.org/dsa/tree-data-structure/
     * 
     * Tree Traversal Documentation
     * 
     * https://www.geeksforgeeks.org/dsa/tree-traversals-inorder-preorder-and-
     * postorder/
     */
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        if (this == null)
            return "(empty tree)";
        StringBuilder sb = new StringBuilder();
        buildTreeString(this, sb, "", false, true);
        return sb.toString();
    }

    /**
     * Recursively builds the tree string representation.
     *
     * @param node   current node being printed
     * @param sb     accumulates the full output
     * @param prefix the indentation/pipe characters inherited from the parent
     * @param isLeft true if this node is a left child (uses ├──), false for right
     *               (└──)
     * @param isRoot true only for the root node (no connector prefix)
     */
    private void buildTreeString(TreeNode node, StringBuilder sb,
            String prefix, boolean isLeft, boolean isRoot) {
        if (node == null)
            return;

        String connector = isRoot ? "" : (isLeft ? "└── " : "┌── ");
        sb.append(prefix).append(connector).append(node.val).append("\n");

        String childPrefix = isRoot ? "" : (isLeft ? prefix + "    " : prefix + "│   ");

        boolean hasLeft = node.left != null;
        boolean hasRight = node.right != null;

        if (hasLeft || hasRight) {
            // ✅ Print RIGHT child first (appears on top in output = visually right)
            if (hasRight)
                buildTreeString(node.right, sb, childPrefix, false, false);
            else
                sb.append(childPrefix).append("┌── null\n");

            // ✅ Print LEFT child second (appears on bottom in output = visually left)
            if (hasLeft)
                buildTreeString(node.left, sb, childPrefix, true, false);
            else
                sb.append(childPrefix).append("└── null\n");
        }
    }
}
