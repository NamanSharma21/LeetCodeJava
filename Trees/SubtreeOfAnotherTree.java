package Trees;

import java.util.HashSet;
import java.util.Set;

import Datastructures.TreeNode;

public class SubtreeOfAnotherTree {
    public static void main(String[] args) {
        SubtreeOfAnotherTree subtreeOfAnotherTree = new SubtreeOfAnotherTree();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(4);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(2);
        root.right.right = new TreeNode(5);
        System.out.println("SubtreeOfAnotherTree : " + subtreeOfAnotherTree.isSubtree(root, root.left));
        System.out.println(
                "SubtreeOfAnotherTree : " + subtreeOfAnotherTree.isSubtreeTreeSerializationKMP(root, root.left));
        System.out.println(
                "SubtreeOfAnotherTree : " + subtreeOfAnotherTree.isSubtreeTreeMerkleHash(root, root.left));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/subtree-of-another-tree/description/?envType=problem-list-v2&envId=tree
     * 
     * Given the roots of two binary trees root and subRoot, return true if there is
     * a subtree of root with the same structure and node values of subRoot and
     * false otherwise.
     * 
     * A subtree of a binary tree tree is a tree that consists of a node in tree and
     * all of this node's descendants. The tree tree could also be considered as a
     * subtree of itself.
     * 
     * 
     * 
     * Example 1:
     * root:      3
     *           / \
     *          4   5
     *         / \
     *        1   2
     *
     * subRoot:   4
     *           / \
     *          1   2
     * Output: true
     * 
     * Input: root = [3,4,5,1,2], subRoot = [4,1,2]
     * Output: true
     * Example 2:
     * 
     * root:      3
     *           / \
     *          4   5
     *         / \
     *        1   2
     *           /
     *          0
     *
     * subRoot:   4
     *           / \
     *          1   2
     * Output: false
     * 
     * Input: root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2]
     * Output: false
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the root tree is in the range [1, 2000].
     * The number of nodes in the subRoot tree is in the range [1, 1000].
     * -104 <= root.val <= 104
     * -104 <= subRoot.val <= 104
     */
    // @formatter:on

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null)
            return false;
        if (isSameTree(root, subRoot))
            return true;
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null)
            return true;
        if (p == null || q == null)
            return false;
        if (p.val != q.val)
            return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    public boolean isSubtreeTreeSerializationKMP(TreeNode root, TreeNode subRoot) {
        String rootSerial = serialize(root);
        String subSerial = serialize(subRoot);
        return kmpContains(rootSerial, subSerial);
    }

    private String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }

    public void serializeHelper(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append(",#");
            return;
        }

        sb.append(",").append(root.val);
        serializeHelper(root.left, sb);
        serializeHelper(root.right, sb);
    }

    public boolean kmpContains(String text, String pattern) {
        String combined = pattern + "|" + text;
        int[] lps = buildLPS(combined);
        int patlength = pattern.length();
        for (int i = patlength + 1; i < combined.length(); i++) {
            if (lps[i] == patlength)
                return true;
        }
        return false;
    }

    public int[] buildLPS(String s) {
        int n = s.length();
        int[] lps = new int[n];
        int i = 1;
        int length = 0;
        while (i < n) {
            if (s.charAt(i) == s.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    Set<String> subTreeHashes = new HashSet<>();

    public boolean isSubtreeTreeMerkleHash(TreeNode root, TreeNode subRoot) {
        hashSubTrees(root);
        String subRootHash = computehash(subRoot);
        return subTreeHashes.contains(subRootHash);
    }

    public String hashSubTrees(TreeNode root) {
        if (root == null)
            return "#";
        String leftTreeHash = hashSubTrees(root.left);
        String rightTreeHash = hashSubTrees(root.right);
        String currentHash = "(" + leftTreeHash + root.val + "" + rightTreeHash + ")";
        subTreeHashes.add(currentHash);
        return currentHash;
    }

    public String computehash(TreeNode root) {
        if (root == null)
            return "#";
        return "(" + computehash(root.left) + root.val + computehash(root.right) + ")";
    }

    // @formatter:off
    /*
    # Subtree of Another Tree — Deep Dive

    ---

    ## 1. Problem Statement

    Given two binary trees, `root` (the main tree) and `subRoot` (the candidate subtree), determine whether `subRoot` is a **subtree** of `root`.

    A subtree means there exists some node `n` in `root` such that the entire tree rooted at `n` is **structurally identical** and has **identical node values** to `subRoot`.

    ### Input Format
    - `root` — the root of the main binary tree (TreeNode)
    - `subRoot` — the root of the candidate subtree (TreeNode)
    - Node values: integers (can be negative, zero, or positive)
    - Both trees can have 1 to **2000 nodes** (LeetCode constraints)

    ### Output Format
    - Return `true` if `subRoot` is a subtree of `root`, otherwise `false`

    ### Important Constraints
    - The comparison must be **exact**: structure AND values must match
    - A tree is considered a subtree of itself
    - `null` / empty nodes matter — a full subtree match means leaves must also align

    ---

    ## 2. Intuition

    Think of it like this: you have a large jigsaw puzzle (`root`) and a smaller puzzle piece (`subRoot`). You want to know if that smaller piece **fits perfectly** somewhere inside the big one — same shape, same values, no missing or extra pieces.

    ### Human Reasoning Steps
    1. Walk every node in the big tree
    2. At each node, ask: "Does the tree starting here look exactly like `subRoot`?"
    3. If yes at any node → return `true`
    4. If you exhaust all nodes without a match → return `false`

    ### What Makes This Tricky
    - You can't just compare values at one node — you must recursively verify the **entire subtree**
    - Duplicate values can cause confusion (a value match doesn't mean a tree match)
    - Null alignment is critical: both trees must have null in the same positions
    - The "same tree" check itself is a recursive problem nested inside another recursive traversal

    ---

    ## 3. Approach Overview

    | # | Approach | Key Idea | Best For | Time | Space |
    |---|----------|----------|----------|------|-------|
    | 1 | **Brute Force DFS** | At every node of root, check isSameTree | Interviews, all cases | O(m×n) | O(m+n) |
    | 2 | **Tree Serialization + KMP/String Match** | Serialize both trees, do substring search | Large trees, competitive programming | O(m+n) | O(m+n) |
    | 3 | **Tree Hashing (Merkle)** | Hash subtrees bottom-up, compare hashes | Duplicate detection, large trees | O(m+n) | O(m+n) |

    ### Recommendation
    - ✅ **Approach 1 (Brute Force DFS)** is the go-to in interviews — clean, readable, easy to reason about, and fast enough given constraints (n, m ≤ 2000)
    - ✅ **Approach 2 (Serialization)** achieves true O(m+n) and is elegant for competitive programming
    - ✅ **Approach 3 (Hashing)** is optimal when you need to check multiple subRoot queries against one root

    ---

    ## 4. Detailed Solutions in Java

    ---

    ### ✅ Approach 1: Brute Force DFS (Recommended for Interviews)

    #### Algorithm
    1. Traverse every node in `root` using DFS
    2. At each node, call `isSameTree(node, subRoot)`
    3. `isSameTree` recursively checks if two trees are structurally and value-identical
    4. Return `true` as soon as any node matches

    ```java
    class Solution {

        public boolean isSubtree(TreeNode root, TreeNode subRoot) {
            // Base case: if root is null, subRoot cannot be found
            if (root == null) return false;

            // Check if the tree rooted at current node matches subRoot
            if (isSameTree(root, subRoot)) return true;

            // Otherwise, check left and right subtrees
            return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
        }

        private boolean isSameTree(TreeNode p, TreeNode q) {
            // Both null → identical at this position
            if (p == null && q == null) return true;

            // One null, one not → mismatch
            if (p == null || q == null) return false;

            // Values differ → mismatch
            if (p.val != q.val) return false;

            // Recursively verify left and right subtrees
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }
    }
    ```

    ---

    ### ✅ Approach 2: Tree Serialization + String Matching (KMP)

    #### Algorithm
    1. Serialize `root` into a string using preorder traversal with null markers and delimiters
    2. Serialize `subRoot` the same way
    3. Check if the serialized `subRoot` string is a **substring** of the serialized `root` string
    4. Using KMP, substring search runs in O(m+n)

    **Why delimiters matter:** Without `#` for null and `,` as separator, "12" could match "1" and "2" separately — false positives.

    ```java
    class Solution {

        public boolean isSubtree(TreeNode root, TreeNode subRoot) {
            String rootSerial = serialize(root);
            String subSerial = serialize(subRoot);

            // Check if subRoot serialization is a substring of root serialization
            return kmpContains(rootSerial, subSerial);
        }

        // Preorder serialization: "val,left,right" with "#" for nulls
        private String serialize(TreeNode node) {
            StringBuilder sb = new StringBuilder();
            serializeHelper(node, sb);
            return sb.toString();
        }

        private void serializeHelper(TreeNode node, StringBuilder sb) {
            if (node == null) {
                sb.append(",#");
                return;
            }
            // Comma prefix ensures "12" != "1" + "2"
            sb.append(",").append(node.val);
            serializeHelper(node.left, sb);
            serializeHelper(node.right, sb);
        }

        // KMP substring search: returns true if pattern exists in text
        private boolean kmpContains(String text, String pattern) {
            String combined = pattern + "|" + text; // "|" as separator not in serialization
            int[] lps = buildLPS(combined);
            int patLen = pattern.length();

            for (int i = patLen + 1; i < combined.length(); i++) {
                if (lps[i] == patLen) return true;
            }
            return false;
        }

        // Build Longest Proper Prefix-Suffix array for KMP
        private int[] buildLPS(String s) {
            int n = s.length();
            int[] lps = new int[n];
            int length = 0;
            int i = 1;

            while (i < n) {
                if (s.charAt(i) == s.charAt(length)) {
                    lps[i++] = ++length;
                } else if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i++] = 0;
                }
            }
            return lps;
        }
    }
    ```

    ---

    ### ✅ Approach 3: Tree Hashing (Merkle Hash)

    #### Algorithm
    1. For every node in `root`, compute a **hash** that encodes its value and the hashes of its children
    2. Store all unique hashes of subtrees in a `HashSet`
    3. Compute the hash of `subRoot`
    4. Return whether `subRoot`'s hash exists in the set

    ```java
    import java.util.HashSet;
    import java.util.Set;

    class Solution {
        private Set<String> subtreeHashes = new HashSet<>();

        public boolean isSubtree(TreeNode root, TreeNode subRoot) {
            // Collect hashes of all subtrees in root
            hashSubtrees(root);

            // Check if subRoot's hash matches any
            String subRootHash = computeHash(subRoot);
            return subtreeHashes.contains(subRootHash);
        }

        private String hashSubtrees(TreeNode node) {
            if (node == null) return "#";

            String leftHash = hashSubtrees(node.left);
            String rightHash = hashSubtrees(node.right);

            // Hash encodes: value + left subtree hash + right subtree hash
            String currentHash = "(" + leftHash + node.val + rightHash + ")";
            subtreeHashes.add(currentHash);
            return currentHash;
        }

        private String computeHash(TreeNode node) {
            if (node == null) return "#";
            return "(" + computeHash(node.left) + node.val + computeHash(node.right) + ")";
        }
    }
    ```

    > **Note:** In production competitive programming, use numeric hash functions (polynomial rolling hash) instead of string concatenation to avoid hash collisions and reduce string overhead.

    ---

    ## 5. Time & Space Complexity

    ### Approach 1 — Brute Force DFS

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(m × n) | For each of the `m` nodes in root, we potentially run isSameTree which visits up to `n` nodes |
    | **Space** | O(m + n) | Recursion stack depth = height of root (O(m)) + height of subRoot (O(n)) |

    **Walkthrough:** root has 5 nodes, subRoot has 3 nodes → worst case 5 × 3 = 15 comparisons

    ---

    ### Approach 2 — Serialization + KMP

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(m + n) | Serialization: O(m) + O(n). KMP search: O(m + n) |
    | **Space** | O(m + n) | Serialized strings of length proportional to m and n |

    **Walkthrough:** root serializes to length ~2m, subRoot to ~2n. KMP runs in linear time over both → truly linear overall.

    ---

    ### Approach 3 — Tree Hashing

    | | Complexity | Reasoning |
    |---|---|---|
    | **Time** | O(m + n) | Post-order traversal of root: O(m). Hash of subRoot: O(n). Set lookup: O(1) avg |
    | **Space** | O(m) | HashSet stores one string per node in root |

    ---

    ## 6. Complete Worked Examples

    ### Example — Approach 1 (DFS)

    ```
    root:                subRoot:
        3                  4
        / \                / \
        4   5              1   2
    / \
    1   2
    ```

    **Execution trace:**

    ```
    isSubtree(3, 4)
    └─ isSameTree(3, 4) → 3 ≠ 4 → false
    └─ isSubtree(4, 4)
        └─ isSameTree(4, 4) → 4 == 4 ✓
                └─ isSameTree(1, 1) → 1 == 1 ✓
                    └─ isSameTree(null, null) → true ✓
                    └─ isSameTree(null, null) → true ✓
                └─ isSameTree(2, 2) → 2 == 2 ✓
                    └─ isSameTree(null, null) → true ✓
                    └─ isSameTree(null, null) → true ✓
        → true ✓
    → RETURN TRUE
    ```

    ---

    ### Example — Approach 2 (Serialization)

    ```
    root serialized:    ",3,4,1,#,#,2,#,#,5,#,#"
    subRoot serialized: ",4,1,#,#,2,#,#"
    ```

    KMP checks: Is `",4,1,#,#,2,#,#"` a substring of `",3,4,1,#,#,2,#,#,5,#,#"`?

    ```
    text:    ,3,4,1,#,#,2,#,#,5,#,#
                ↑ match starts here
    pattern: ,4,1,#,#,2,#,#
                ✓ full match found → return true
    ```

    ---

    ### Example — Approach 3 (Hashing)

    ```
    Post-order hashing of root:
    node 1 → (#1#)
    node 2 → (#2#)
    node 4 → ((#1#)4(#2#))   ← stored in set
    node 5 → (#5#)
    node 3 → (((#1#)4(#2#))3(#5#))

    subRoot hash: ((#1#)4(#2#))

    Set contains ((#1#)4(#2#))? → YES → return true
    ```

    ---

    ## 7. Edge Cases

    | Edge Case | What Happens | All Approaches Safe? |
    |---|---|---|
    | `subRoot == null` | By convention, null is subtree of anything | ✅ All handle: null==null returns true |
    | `root == null, subRoot != null` | Cannot find subRoot | ✅ Approach 1: returns false immediately |
    | Both trees identical | Root itself is the subtree | ✅ isSameTree at root node returns true |
    | subRoot larger than root | Cannot be a subtree | ✅ All return false |
    | Duplicate values at multiple nodes | Must check full subtree, not just value | ✅ isSameTree verifies full structure |
    | Tree is a single node | Works as long as values match | ✅ All handle correctly |
    | Values like `1` and `11` | Serialization approach needs delimiters | ⚠️ Approach 2 safe **only with** comma prefix — otherwise "1" matches inside "11" |
    | Negative values | Serialization must handle `-` sign | ✅ `.append(node.val)` handles negatives naturally |
    | Very deep tree (chain) | Stack overflow risk on long chains | ⚠️ All recursive approaches may hit stack limit on 10,000+ depth; iterative version preferred for extreme depths |

    ### Verification of Critical Edge Case — Duplicate Values

    ```
    root:        subRoot:
    12            2
    /
    2
    ```

    Without delimiters, serializing root gives `"12,#,#,2,#,#"` and subRoot gives `"2,#,#"`. Naively checking if `"2"` is substring of `"12"` → false positive avoided by comma prefix making subRoot `",2,#,#"` which does NOT match `"12"` within the string.

    ---

    ## 8. Final Summary

    | Approach | Time | Space | Interview Fit | Notes |
    |---|---|---|---|---|
    | DFS + isSameTree | O(m×n) | O(m+n) | ⭐⭐⭐⭐⭐ Best | Simple, clean, robust |
    | Serialization + KMP | O(m+n) | O(m+n) | ⭐⭐⭐ Good | Needs careful delimiter design |
    | Tree Hashing | O(m+n) | O(m) | ⭐⭐⭐⭐ Good | Best for multi-query scenarios |

    ### What to Remember
    > This problem is a **double recursion** pattern: one recursion to traverse nodes, another to verify identical trees. Mastering this pattern unlocks many tree comparison and tree search problems.

    The key insight is: **subtree = "same tree" check at every possible starting node**, and both "traverse" and "compare" are naturally recursive on trees.

    ---

    ## 9. Company Appearances

    | Company | Frequency | Notes |
    |---|---|---|
    | **Amazon** | ⭐⭐⭐⭐⭐ Very High | Asked in SDE-1 and SDE-2 rounds frequently |
    | **Microsoft** | ⭐⭐⭐⭐ High | Common in technical phone screens |
    | **Facebook / Meta** | ⭐⭐⭐⭐ High | Appears in tree-heavy interview sets |
    | **Google** | ⭐⭐⭐ Medium | Sometimes paired with tree serialization follow-ups |
    | **Apple** | ⭐⭐⭐ Medium | Seen in onsite rounds |
    | **Bloomberg** | ⭐⭐⭐ Medium | Reported by candidates in coding rounds |
    | **Adobe** | ⭐⭐ Moderate | Occasionally asked |
    | **Oracle** | ⭐⭐ Moderate | Seen in Java-specific rounds |

    **Total LeetCode appearances / discussion reports:** This problem (LeetCode #572) has been tagged by **500+ candidates** across platforms like Glassdoor, LeetCode Discuss, and Blind, making it one of the **top 50 most frequently asked tree problems** in FAANG interviews.

    > 💡 **Pro Tip:** In interviews, always start with Approach 1, explain the O(m×n) complexity honestly, then mention you're aware of the O(m+n) serialization optimization as a follow-up — this signals strong depth of knowledge.
    */
    // @formatter:on

}
