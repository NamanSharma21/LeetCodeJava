package Datastructures;

public class AugmentedNode {
    public int val;
    public int leftCount; // number of nodes in the LEFT subtree
    public AugmentedNode left;
    public AugmentedNode right;

    public AugmentedNode(int val) {
        this.val = val;
        this.leftCount = 0;
        this.left = null;
        this.right = null;
    }
}
