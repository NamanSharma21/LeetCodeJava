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

        // Root has no connector; left children use ├──, right children use └──
        String connector = isRoot ? "" : (isLeft ? "├── " : "└── ");
        sb.append(prefix).append(connector).append(node.val).append("\n");

        // If this node is a left child, extend its column downward with │
        // If it's a right child (or root), the column is closed, so use spaces
        String childPrefix = isRoot ? "" : (isLeft ? prefix + "│   " : prefix + "    ");

        boolean hasLeft = node.left != null;
        boolean hasRight = node.right != null;

        // Only print null placeholders when a sibling exists on the other side
        // (avoids cluttering leaf-only subtrees with redundant nulls)
        if (hasLeft || hasRight) {
            if (hasLeft)
                buildTreeString(node.left, sb, childPrefix, true, false);
            else
                sb.append(childPrefix).append("├── null\n");

            if (hasRight)
                buildTreeString(node.right, sb, childPrefix, false, false);
            else
                sb.append(childPrefix).append("└── null\n");
        }
    }
}
