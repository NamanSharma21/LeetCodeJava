package Trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Datastructures.TreeNode;

public class FindDuplicateSubtrees {
    public static void main(String[] args) {
        FindDuplicateSubtrees findDuplicateSubtrees = new FindDuplicateSubtrees();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.right.left = new TreeNode(2);
        root.right.right = new TreeNode(4);
        root.right.left.left = new TreeNode(4);
        System.out.println(
                "FindDuplicateSubtrees : " + findDuplicateSubtrees.findDuplicateSubtreesSerializationHashMap(root));
        System.out.println(
                "FindDuplicateSubtrees : "
                        + findDuplicateSubtrees.findDuplicateSubtreesSerializationIntegerIdEncoding(root));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/find-duplicate-subtrees/description/?envType=problem-list-v2&envId=tree
     * 
     * Given the root of a binary tree, return all duplicate subtrees.
     * 
     * For each kind of duplicate subtrees, you only need to return the root node of
     * any one of them.
     * 
     * Two trees are duplicate if they have the same structure with the same node
     * values.
     * 
     * 
     * 
     * Example 1:
     * 
     *       1
     *      / \
     *     2   3
     *    /   / \
     *   4   2   4
     *      /
     *     4
     * Duplicate Subtrees:
     * 1. The leaf node [4] appears twice (under the left '2' and the right '2').
     * 2. The subtree [2, 4] appears twice.
     * 
     * Input: root = [1,2,3,4,null,2,4,null,null,4]
     * Output: [[2,4],[4]]
     * 
     * Example 2:
     * 
     *       2
     *      / \
     *     1   1
     * Duplicate Subtrees:
     * 1. The leaf node [1] appears on both the left and right sides.
     * 
     * Input: root = [2,1,1]
     * Output: [[1]]
     * 
     * 
     * Example 3:
     * 
     *       2
     *      / \
     *     2   2
     *    /   /
     *   3   3
     * 
     * Duplicate Subtrees:
     * 1. The leaf node [3] appears twice.
     * 2. The subtree [2, 3] appears twice (as the left child and right child of the
     * root).
     * 
     * Input: root = [2,2,2,3,null,3,null]
     * Output: [[2,3],[3]]
     * 
     * 
     * Constraints:
     * 
     * The number of the nodes in the tree will be in the range [1, 5000]
     * -200 <= Node.val <= 200
     */
    // @formatter:on

    private Map<String, Integer> countMap = new HashMap<>();
    List<TreeNode> result = new ArrayList<>();

    public List<TreeNode> findDuplicateSubtreesSerializationHashMap(TreeNode root) {
        serialize(root);
        System.out.println("" + countMap);
        return result;
    }

    public String serialize(TreeNode node) {
        if (node == null)
            return "#";
        String leftSerial = serialize(node.left);
        String rightSerial = serialize(node.right);

        String key = "(" + leftSerial + "," + node.val + "," + rightSerial + ")";
        System.out.println(key);
        int count = countMap.merge(key, 1, Integer::sum);
        if (count == 2)
            result.add(node);
        return key;
    }

    private List<TreeNode> results = new ArrayList<>();
    private Map<String, Integer> serializationToId = new HashMap<>();
    private Map<Integer, Integer> idCount = new HashMap<>();
    private int nextId = 0;

    public List<TreeNode> findDuplicateSubtreesSerializationIntegerIdEncoding(TreeNode root) {
        getSubTreeId(root);
        return results;
    }

    public int getSubTreeId(TreeNode root) {
        if (root == null)
            return 0;
        int leftId = getSubTreeId(root.left);
        int rightId = getSubTreeId(root.right);

        String tripletkey = leftId + "," + root.val + "," + rightId;
        int myId = serializationToId.computeIfAbsent(tripletkey, k -> nextId++);
        int count = idCount.merge(myId, 1, Integer::sum);
        if (count == 2)
            results.add(root);
        return myId;
    }

}

// @formatter:off
/*
 * ============================================================
 *  Find Duplicate Subtrees — Deep Dive
 * ============================================================
 *
 * ============================================================
 *  1. PROBLEM STATEMENT
 * ============================================================
 *
 *  Restate
 *  -------
 *  Given the root of a binary tree, find all subtrees that appear
 *  more than once. Two subtrees are considered duplicates if they
 *  have the same structure AND the same node values at every
 *  corresponding position.
 *
 *  Return a list containing one representative root node from each
 *  group of duplicate subtrees (you only need one representative,
 *  not all copies).
 *
 *  Input / Output / Constraints
 *  -----------------------------
 *  Input       : TreeNode root — root of a binary tree
 *  Output      : List<TreeNode> — one node per duplicate subtree
 *  Node values : -200 <= Node.val <= 200
 *  Tree size   : 1 <= number of nodes <= 5000
 *  Null subtrees count as structural components (must be encoded)
 *
 *  What exactly must be computed?
 *  ------------------------------
 *  For every unique subtree shape+value that appears >= 2 times
 *  in the tree, add exactly one of those subtree roots to your
 *  answer list.
 *
 *
 * ============================================================
 *  2. INTUITION
 * ============================================================
 *
 *  Core Idea in Simple Terms
 *  --------------------------
 *  Imagine you had a magic "fingerprint" for every subtree — a
 *  string that uniquely captures both its structure and values.
 *  Then two subtrees are duplicates if and only if they produce
 *  the same fingerprint.
 *
 *  Plan:
 *    1. Walk every node in the tree (post-order — children first,
 *       then parent)
 *    2. Build a serialization string for each subtree
 *    3. Track how many times each serialization has been seen
 *    4. The first time a serialization hits count = 2, record
 *       that node
 *
 *  Why Post-Order?
 *  ---------------
 *  You need the full description of both children BEFORE you can
 *  describe the parent. Post-order (left -> right -> root)
 *  naturally gives you this bottom-up construction.
 *
 *  What Makes This Tricky?
 *  ------------------------
 *  - You must encode null children explicitly, otherwise
 *    structurally different trees can produce the same string.
 *    e.g., a node 1 with only a left child 2 vs a node 1 with
 *    only a right child 2 must produce different serializations.
 *  - Returning one representative per group (not all duplicates)
 *    requires carefully tracking when count transitions 1 -> 2.
 *
 *
 * ============================================================
 *  3. APPROACH OVERVIEW
 * ============================================================
 *
 *  #  | Approach                          | Key Idea
 *  ---|-----------------------------------|----------------------------------
 *  1  | Brute Force                       | Compare every pair of subtrees
 *     |                                   | structurally
 *  ---|-----------------------------------|----------------------------------
 *  2  | Serialization + HashMap           | Serialize each subtree to a
 *     |                                   | string; use a map to count
 *  ---|-----------------------------------|----------------------------------
 *  3  | Serialization + Integer ID        | Assign integer IDs to unique
 *     | (Optimal)                         | serializations; compare integers
 *  ---|-----------------------------------|----------------------------------
 *
 *  Complexity Summary:
 *    Approach 1 : O(n^3) time | Use: Never in interviews
 *    Approach 2 : O(n^2) worst case | Use: Good interview answer
 *    Approach 3 : O(n)  time | Use: OPTIMAL — recommended
 *
 *
 * ============================================================
 *  4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 *  APPROACH 1 — Brute Force (Structural Comparison of All Pairs)
 * ------------------------------------------------------------
 *
 *  Algorithm:
 *    1. Collect all subtree roots into a list (DFS)
 *    2. For every pair (i, j) where i != j, check if the subtrees
 *       rooted at i and j are identical
 *    3. If identical and i's subtree hasn't been added yet, add it
 *

// ----- Approach 1 -----

import java.util.*;

class BruteForce {

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        List<TreeNode> allNodes = new ArrayList<>();
        collectAllNodes(root, allNodes);

        Set<TreeNode> alreadyAdded = new HashSet<>();
        List<TreeNode> result = new ArrayList<>();

        for (int i = 0; i < allNodes.size(); i++) {
            for (int j = i + 1; j < allNodes.size(); j++) {
                TreeNode a = allNodes.get(i);
                TreeNode b = allNodes.get(j);

                if (!alreadyAdded.contains(a) && isSameTree(a, b)) {
                    result.add(a);
                    alreadyAdded.add(a);
                    alreadyAdded.add(b); // prevent b from also being added
                }
            }
        }
        return result;
    }

    // DFS to collect every node in the tree
    private void collectAllNodes(TreeNode node, List<TreeNode> list) {
        if (node == null) return;
        list.add(node);
        collectAllNodes(node.left, list);
        collectAllNodes(node.right, list);
    }

    // Standard recursive tree equality check
    private boolean isSameTree(TreeNode a, TreeNode b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        if (a.val != b.val) return false;
        return isSameTree(a.left, b.left) && isSameTree(a.right, b.right);
    }
}

// ----- Approach 2 -----

/*
 * ------------------------------------------------------------
 *  APPROACH 2 — Serialization + HashMap
 * ------------------------------------------------------------
 *
 *  Algorithm:
 *    1. Do a post-order DFS on the tree
 *    2. At each node, build a string serialization:
 *         "#" for null nodes
 *         "(leftSerial,val,rightSerial)" for non-null nodes
 *    3. Store each serialization in a HashMap<String, Integer>
 *    4. When count becomes exactly 2, add the node to result
 *
 *  Why count == 2 and not count >= 2?
 *    If three identical subtrees exist, we still only want ONE
 *    node in the result. Checking == 2 ensures we add it exactly
 *    once (on the second occurrence).


class SerializationHashMap {

    private Map<String, Integer> countMap = new HashMap<>();
    private List<TreeNode> result = new ArrayList<>();

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        serialize(root);
        return result;
    }

    private String serialize(TreeNode node) {
        if (node == null) return "#"; // encode null explicitly

        // Post-order: serialize left, then right, then build key
        String leftSerial  = serialize(node.left);
        String rightSerial = serialize(node.right);

        // Unique representation: left + delimiter + value + delimiter + right
        String key = leftSerial + "," + node.val + "," + rightSerial;

        int count = countMap.merge(key, 1, Integer::sum); // increment count

        if (count == 2) {
            result.add(node); // add once per duplicate group
        }

        return key;
    }
}

// ----- Approach 3 (Optimal) -----

/*
 * ------------------------------------------------------------
 *  APPROACH 3 — Serialization + Integer ID Encoding (OPTIMAL)
 * ------------------------------------------------------------
 *
 *  Key insight:
 *    The bottleneck in Approach 2 is that string keys can be O(n)
 *    characters long (a chain tree serializes to a deeply nested
 *    string). We fix this by replacing string keys with small
 *    integers.
 *
 *    Maintain a second map serializationToId that assigns each
 *    unique serialization an integer ID. Then the "key" stored in
 *    the count map is a compact triplet (leftId, nodeVal, rightId)
 *    — constant size, O(1) comparison.
 */

/*class OptimalIntegerID {

    // Maps a serialization triplet -> a unique integer ID
    private Map<String, Integer> serializationToId = new HashMap<>();
    // Maps an integer ID -> how many times this subtree has appeared
    private Map<Integer, Integer> idCount = new HashMap<>();
    private List<TreeNode> result = new ArrayList<>();
    private int nextId = 1; // IDs start at 1; 0 reserved for null

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        getSubtreeId(root);
        return result;
    }

    private int getSubtreeId(TreeNode node) {
        if (node == null) return 0; // null always gets ID = 0

        // Post-order: get IDs of children first
        int leftId  = getSubtreeId(node.left);
        int rightId = getSubtreeId(node.right);

        // Build a compact key from three integers
        String tripletKey = leftId + "," + node.val + "," + rightId;

        // Assign a new ID if this triplet is new, reuse existing otherwise
        int myId = serializationToId.computeIfAbsent(tripletKey, k -> nextId++);

        // Count how many times this exact subtree structure has been seen
        int count = idCount.merge(myId, 1, Integer::sum);

        if (count == 2) {
            result.add(node);
        }

        return myId;
    }
}
*/

/*
 *
 * ============================================================
 *  5. TIME & SPACE COMPLEXITY (WITH REASONING)
 * ============================================================
 *
 *  Approach 1 — Brute Force
 *  -------------------------
 *  Time  : O(n^3)
 *            O(n^2) pairs x O(n) per isSameTree comparison
 *  Space : O(n)
 *            Node list + recursion stack
 *  Example: n=5000 -> ~125 billion operations. Infeasible.
 *
 *  Approach 2 — Serialization + HashMap
 *  --------------------------------------
 *  Time  : O(n^2) worst case
 *            Each serialization can be O(n) characters long
 *            (chain tree); building + hashing it takes O(n);
 *            done for n nodes.
 *  Space : O(n^2)
 *            Storing up to n serialization strings, each up to
 *            O(n) long.
 *  Note  : For a balanced tree, strings are O(log n) long ->
 *          closer to O(n log n) in practice.
 *
 *  Approach 3 — Integer ID Encoding (Optimal)
 *  --------------------------------------------
 *  Time  : O(n)
 *            Each node visited once; triplet key is O(1) to
 *            build; all map operations O(1) average.
 *  Space : O(n)
 *            At most n unique triplets stored in
 *            serializationToId; recursion stack O(h).
 *  Example: n=5000 -> ~5000 operations. Extremely fast.
 *
 *
 * ============================================================
 *  6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 *  Tree Setup:
 *
 *          1
 *         / \
 *        2   3
 *       /   / \
 *      4   2   4
 *         /
 *        4
 *
 *  Expected duplicates:
 *    - Subtree rooted at 4 (leaf) appears 3 times -> add one 4
 *    - Subtree rooted at 2 (with left child 4) appears 2 times
 *      -> add one 2
 *
 *  ............................................................
 *  Approach 2 Walkthrough (Post-order DFS):
 *  ............................................................
 *
 *  Step | Node          | Left Serial | Right Serial | Key Built          | Count | Action
 *  -----|---------------|-------------|--------------|---------------------|-------|--------
 *  1    | 4 (leftmost)  | #           | #            | #,4,#              | 1     | —
 *  2    | 2 (left of 1) | #,4,#       | #            | #,4,#,2,#          | 1     | —
 *  3    | 4 (child r-2) | #           | #            | #,4,#              | 2     | Add 4
 *  4    | 2 (right of 3)| #,4,#       | #            | #,4,#,2,#          | 2     | Add 2
 *  5    | 4 (right of 3)| #           | #            | #,4,#              | 3     | — (skip)
 *  6    | 3             | #,4,#,2,#   | #,4,#        | #,4,#,2,#,3,#,4,#  | 1     | —
 *  7    | 1             | ...         | ...          | full key           | 1     | —
 *
 *  Result: [node(4), node(2)]  ✓
 *
 *  ............................................................
 *  Approach 3 Walkthrough (Integer IDs):
 *  ............................................................
 *
 *  Step | Node          | leftId | rightId | Triplet Key | ID  | idCount | Action
 *  -----|---------------|--------|---------|-------------|-----|---------|--------
 *  1    | 4 (leaf)      | 0      | 0       | 0,4,0       | 1   | {1:1}   | —
 *  2    | 2 (left of 1) | 1      | 0       | 1,2,0       | 2   | {2:1}   | —
 *  3    | 4 (child r-2) | 0      | 0       | 0,4,0       | 1   | {1:2}   | Add 4
 *  4    | 2 (right of 3)| 1      | 0       | 1,2,0       | 2   | {2:2}   | Add 2
 *  5    | 4 (right of 3)| 0      | 0       | 0,4,0       | 1   | {1:3}   | — (skip)
 *  6    | 3             | 2      | 1       | 2,3,1       | 3   | {3:1}   | —
 *  7    | 1             | 2      | 3       | 2,1,3       | 4   | {4:1}   | —
 *
 *  Result: [node(4), node(2)]  ✓
 *
 *
 * ============================================================
 *  7. EDGE CASES
 * ============================================================
 *
 *  Edge Case               | How Approaches 2 & 3 Handle It
 *  ------------------------|------------------------------------------
 *  Single node tree        | Serializes to "#,val,#"; count=1;
 *                          | result is empty ✓
 *  All identical nodes     | Correctly identifies duplicate subtrees
 *                          | at every level ✓
 *  Linear chain (skewed)   | No duplicates; each serialization is
 *                          | unique ✓
 *  Null root               | Returns ID 0 / empty string; result
 *                          | list is empty ✓
 *  Mirror subtrees         | Different serializations because
 *                          | left/right order matters ✓
 *  Negative values         | Included in the key string naturally;
 *                          | no issue ✓
 *  Same value, diff struct | (#,1,#,#,2,#) != (#,2,#,1,#,#) ->
 *                          | correctly different ✓
 *  3+ identical subtrees   | count == 2 fires once; count == 3
 *                          | is ignored -> exactly one node added ✓
 *
 *  Brute Force Risks:
 *    - alreadyAdded set logic can have subtle bugs for many
 *      duplicate nodes
 *    - Doesn't scale past n ~ 200 due to O(n^3) complexity
 *
 *
 * ============================================================
 *  8. FINAL SUMMARY
 * ============================================================
 *
 *  Approach                   | Time   | Space  | Recommended?
 *  ---------------------------|--------|--------|-------------
 *  Brute Force                | O(n^3) | O(n)   | No
 *  Serialization + HashMap    | O(n^2) | O(n^2) | Good for interviews
 *  Integer ID Encoding        | O(n)   | O(n)   | YES — Best
 *
 *  What to Remember:
 *    "Fingerprint subtrees via post-order serialization + HashMap"
 *    is the canonical approach for subtree identity problems.
 *    The optimization of replacing string keys with integer IDs
 *    (a form of string interning) is a powerful general technique
 *    that reduces comparison cost from O(n) to O(1).
 *
 *
 * ============================================================
 *  9. COMPANY INTERVIEW APPEARANCES
 * ============================================================
 *
 *  Company          | Frequency      | Notes
 *  -----------------|----------------|---------------------------
 *  Amazon           | Very High ****  | SDE-2 / L5 rounds
 *  Google           | High    ****   | Coding + design rounds
 *  Facebook / Meta  | High    ****   | E4/E5 level tree problem
 *  Microsoft        | Medium  ***    | Backend / infrastructure
 *  LinkedIn         | Medium  ***    | Phone screens
 *  Bloomberg        | Med-Low **     | Occasionally final rounds
 *  Uber             | Med-Low **     | Infrastructure team
 *
 *  LeetCode Problem #652 — Tagged: Tree, DFS, Hash Table
 *  Reported in 100+ interview reports across all levels.
 *  Particularly popular at Amazon and Google for mid-to-senior
 *  level positions.
 *
 *  Tests: tree traversal, serialization, hashing, deduplication
 *
 * ============================================================
 */
//import java.util.*;

/*
 * ============================================================
 *  BUG REPORT: Approach 2 — Serialization + HashMap
 * ============================================================
 *
 *  Failing Input : [0, 0, 0, 0, null, null, 0, null, null, null, 0]
 *
 *  Tree Structure:
 *
 *              0
 *             / \
 *            0   0
 *           /     \
 *          0       0
 *                   \
 *                    0
 *
 *  Expected Result : ONE duplicate found — the single leaf node (0)
 *  Buggy Result    : TWO duplicates falsely reported
 *
 * ============================================================
 *  ROOT CAUSE: Delimiter Collision (Ambiguous Serialization)
 * ============================================================
 *
 *  The original serialization format is:
 *      key = leftSerial + "," + nodeVal + "," + rightSerial
 *
 *  Since "," is ALSO used inside the serialized child strings,
 *  the same key string can be produced by TWO DIFFERENT structures.
 *
 *  Example — both structures below produce key = "#,0,#,0,#":
 *
 *    Structure A: node(0) with LEFT=leaf(0), RIGHT=null
 *      leftSerial  = "#,0,#"
 *      rightSerial = "#"
 *      key = "#,0,#" + "," + "0" + "," + "#"  ==>  "#,0,#,0,#"
 *
 *    Structure B: node(0) with LEFT=null, RIGHT=leaf(0)
 *      leftSerial  = "#"
 *      rightSerial = "#,0,#"
 *      key = "#" + "," + "0" + "," + "#,0,#"  ==>  "#,0,#,0,#"
 *
 *  Structure A has a LEFT child. Structure B has a RIGHT child.
 *  They are structurally DIFFERENT — yet they hash to the SAME key.
 *  This causes a FALSE POSITIVE duplicate to be reported.
 *
 *  This bug is most visible when node values are 0 (or any value
 *  that matches the null sentinel "#") because the boundary between
 *  the child serial and the delimiter becomes indistinguishable.
 *
 * ============================================================
 *  THE FIX: Wrap each subtree serialization in parentheses
 * ============================================================
 *
 *  Change:
 *      key = leftSerial + "," + nodeVal + "," + rightSerial
 *  To:
 *      key = "(" + leftSerial + "," + nodeVal + "," + rightSerial + ")"
 *
 *  Now each subtree is unambiguously bounded. The parser (and the
 *  hash map) can never mistake a child's internal comma for a
 *  top-level separator.
 *
 *  Structure A fixed: key = "((#,0,#),0,#)"
 *  Structure B fixed: key = "(#,0,(#,0,#))"
 *  -> Correctly DIFFERENT. No false positive. ✓
 *
 * ============================================================
 */

/*
public class FindDuplicateSubtrees_Fixed {

    // --------------------------------------------------------
    //  BUGGY VERSION (DO NOT USE)
    //  Bug: comma delimiter collides with commas inside child
    //       serializations -> false positive duplicates
    // --------------------------------------------------------
    static class BuggyApproach2 {
        private Map<String, Integer> countMap = new HashMap<>();
        private List<TreeNode> result = new ArrayList<>();

        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            serialize(root);
            return result;
        }

        private String serialize(TreeNode node) {
            if (node == null) return "#";
            String left  = serialize(node.left);
            String right = serialize(node.right);
            // BUG: "," appears both as a separator and inside child serials
            String key = left + "," + node.val + "," + right;
            if (countMap.merge(key, 1, Integer::sum) == 2) result.add(node);
            return key;
        }
    }

    // --------------------------------------------------------
    //  FIXED VERSION — Approach 2
    //  Fix: wrap each node's serialization in parentheses so
    //       the boundary of every subtree is unambiguous
    // --------------------------------------------------------
    static class FixedApproach2 {
        private Map<String, Integer> countMap = new HashMap<>();
        private List<TreeNode> result = new ArrayList<>();

        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            serialize(root);
            return result;
        }

        private String serialize(TreeNode node) {
            if (node == null) return "#";
            String left  = serialize(node.left);
            String right = serialize(node.right);
            // FIX: parentheses bound each subtree unambiguously
            String key = "(" + left + "," + node.val + "," + right + ")";
            if (countMap.merge(key, 1, Integer::sum) == 2) result.add(node);
            return key;
        }
    }

    // --------------------------------------------------------
    //  OPTIMAL VERSION — Approach 3 (Integer ID encoding)
    //  This approach is immune to the bug because it never
    //  concatenates raw child strings — it uses integer IDs
    //  as keys, so there is no delimiter ambiguity possible.
    // --------------------------------------------------------
    static class OptimalApproach3 {
        private Map<String, Integer> serializationToId = new HashMap<>();
        private Map<Integer, Integer> idCount = new HashMap<>();
        private List<TreeNode> result = new ArrayList<>();
        private int nextId = 1;

        public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
            getSubtreeId(root);
            return result;
        }

        private int getSubtreeId(TreeNode node) {
            if (node == null) return 0;
            int leftId  = getSubtreeId(node.left);
            int rightId = getSubtreeId(node.right);
            // Triplet of integers: no string concatenation of child serials,
            // so delimiter collision is structurally impossible
            String tripletKey = leftId + "," + node.val + "," + rightId;
            int myId = serializationToId.computeIfAbsent(tripletKey, k -> nextId++);
            if (idCount.merge(myId, 1, Integer::sum) == 2) result.add(node);
            return myId;
        }
    }
}
*/
/*
 * ============================================================
 *  WHY APPROACH 3 IS IMMUNE TO THIS BUG
 * ============================================================
 *
 *  In Approach 3, the tripletKey is always:
 *      leftId + "," + node.val + "," + rightId
 *
 *  where leftId and rightId are plain integers (never strings
 *  containing commas). So the comma separator can NEVER appear
 *  inside the "child" portion of the key. There is zero chance
 *  of ambiguity — the bug is architecturally impossible.
 *
 *  This is one more reason Approach 3 is preferred.
 *
 * ============================================================
 *  SUMMARY OF CHANGES
 * ============================================================
 *
 *  File/Class        | Change
 *  ------------------|----------------------------------------
 *  BuggyApproach2    | Original code — kept for reference only
 *  FixedApproach2    | One-line fix: wrap key in parentheses
 *  OptimalApproach3  | No change needed — immune by design
 *
 *  The only line that changed in FixedApproach2:
 *
 *  BEFORE:
 *      String key = left + "," + node.val + "," + right;
 *
 *  AFTER:
 *      String key = "(" + left + "," + node.val + "," + right + ")";
 *
 * ============================================================
 */
// @formatter:on
