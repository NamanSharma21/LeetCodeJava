package Trees;

import java.util.LinkedList;
import java.util.Queue;

import Datastructures.TreeNode;

public class SerializeAndDeserializeBST {
    public static void main(String[] args) {
        SerializeAndDeserializeBST serializeAndDeserializeBST = new SerializeAndDeserializeBST();
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        String serializedString = serializeAndDeserializeBST.serializeInOrder(root);
        TreeNode deserializedTree = serializeAndDeserializeBST.deserializeInOrder(serializedString);
        System.out.println("SerializeAndDeserializeBST serialize : " + serializedString);
        System.out.println("SerializeAndDeserializeBST deserialize : " + deserializedTree);

        String s1 = serializeAndDeserializeBST.serializePreOrder(root);
        TreeNode d1 = serializeAndDeserializeBST.deserializePreOrder(s1);
        System.out.println("SerializeAndDeserializeBST serialize : " + s1);
        System.out.println("SerializeAndDeserializeBST deserialize : " + d1);
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/serialize-and-deserialize-bst/description/?
     * envType=problem-list-v2&envId=tree
     * 
     * Serialization is converting a data structure or object into a sequence of
     * bits so that it can be stored in a file or memory buffer, or transmitted
     * across a network connection link to be reconstructed later in the same or
     * another computer environment.
     * 
     * Design an algorithm to serialize and deserialize a binary search tree. There
     * is no restriction on how your serialization/deserialization algorithm should
     * work. You need to ensure that a binary search tree can be serialized to a
     * string, and this string can be deserialized to the original tree structure.
     * 
     * The encoded string should be as compact as possible.
     * 
     * 
     * 
     * Example 1:
     * 
     *    2
     *   / \
     *  1   3
     * 
     * Input: root = [2,1,3]
     * Output: [2,1,3]
     * Example 2:
     * 
     * Input: root = []
     * Output: []
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [0, 104].
     * 0 <= Node.val <= 104
     * The input tree is guaranteed to be a binary search tree.
     */
    // @formatter:on

    // Encodes a tree to a single string.
    public String serializeInOrder(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        inOrderDFS(root, sb);
        return sb.toString();
    }

    public void inOrderDFS(TreeNode root, StringBuilder sb) {
        if (root == null)
            return;
        inOrderDFS(root.left, sb);
        sb.append("" + root.val);
        inOrderDFS(root.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserializeInOrder(String data) {
        int strLength = data.length();
        return deserializeHelper(data, 0, strLength - 1);
    }

    public TreeNode deserializeHelper(String data, int left, int right) {
        if (left > right)
            return null;
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode((data.charAt(mid) - '0'));
        root.left = deserializeHelper(data, left, mid - 1);
        root.right = deserializeHelper(data, mid + 1, right);
        return root;
    }

    public String serializePreOrder(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelperPreOrder(root, sb);
        return sb.toString();
    }

    public void serializeHelperPreOrder(TreeNode root, StringBuilder sb) {
        if (root == null)
            return;
        sb.append(root.val).append(",");
        serializeHelperPreOrder(root.left, sb);
        serializeHelperPreOrder(root.right, sb);
    }

    public TreeNode deserializePreOrder(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        Queue<Integer> queue = new LinkedList<>();
        for (String token : data.split(",")) {
            if (!token.isEmpty()) {
                queue.offer(Integer.parseInt(token));
            }
        }
        return buildBSTPreOrder(queue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public TreeNode buildBSTPreOrder(Queue<Integer> queue, int min, int max) {
        if (queue.isEmpty())
            return null;
        int val = queue.peek();
        if (val < min || val > max)
            return null;
        queue.poll();
        TreeNode root = new TreeNode(val);
        root.left = buildBSTPreOrder(queue, min, val - 1);
        root.right = buildBSTPreOrder(queue, val + 1, max);
        return root;
    }
}

// @formatter:off
// # Serialize and Deserialize BST — Deep Dive

// ---

// ## 1. Problem Statement

// ### Plain English Restatement
// Design a system to **convert a Binary Search Tree (BST) into a string** (serialization) and then **reconstruct the exact same BST from that string** (deserialization) — without storing any extra structural markers.

// ### Input / Output
// | Direction | Format |
// |-----------|--------|
// | **Serialize** | Root node of a BST → a compact String |
// | **Deserialize** | That String → Root node of the reconstructed BST |

// ### Key Constraints
// - The tree is a **valid BST** (left < root < right, no duplicates)
// - Node values are **32-bit integers**
// - The tree can be **empty (null root)**
// - You may **not** use class/global variables across calls
// - The codec must be **stateless** — it must work for multiple independent trees

// ### What Exactly Needs to Be Returned
// - `serialize(root)` → `String`
// - `deserialize(data)` → `TreeNode` (root of reconstructed BST)

// ---

// ## 2. Intuition

// ### The Core Insight
// When you serialize a **general binary tree**, you need to store `null` markers because the structure can be arbitrary. But a **BST has an implicit structure** — if you know the preorder traversal values, you can **fully reconstruct the BST** without any null markers, because the BST property tells you exactly where each node belongs.

// ### Human Reasoning
// Think about it this way:
// 1. If I give you the preorder sequence `[8, 5, 1, 7, 10, 12]`, you know:
//    - `8` is the root (first element in preorder)
//    - Everything `< 8` → left subtree: `[5, 1, 7]`
//    - Everything `> 8` → right subtree: `[10, 12]`
// 2. Recurse on each sublist → you've rebuilt the tree!

// ### What Makes This Tricky
// - A naive approach stores `null` markers (wastes space — not optimal for BSTs)
// - Splitting by value ranges during deserialization requires careful boundary tracking
// - Using a queue/index pointer during reconstruction avoids O(n²) splits
// - The optimal approach rebuilds in **O(n)** without any array slicing

// ---

// ## 3. Approach Overview

// | # | Approach | Key Idea | Complexity | Use Case |
// |---|----------|----------|------------|----------|
// | 1 | **Level-order + null markers** | BFS, store `#` for nulls | O(n) time, O(n) space | General binary tree, not BST-optimal |
// | 2 | **Preorder + value-range split** | Preorder, split list by value | O(n²) worst case | Conceptually simple, small trees |
// | 3 | **Preorder + Queue pointer** ✅ | Preorder, reconstruct with min/max bounds | **O(n) time, O(n) space** | **Optimal — Interview recommended** |

// > ✅ **Approach 3 is optimal** — it leverages the BST property fully, produces the most compact serialization (no null markers), and deserializes in linear time.

// ---

// ## 4. Detailed Solutions in Java

// ### 🔴 Approach 1 — Level-Order with Null Markers (General Binary Tree Style)

// #### Algorithm
// 1. **Serialize**: BFS the tree, append node values or `"#"` for null children
// 2. **Deserialize**: BFS with a queue, reconstruct level by level

// ```java
// import java.util.*;

// public class Codec_Approach1 {

//     // Serialize using BFS, storing '#' for null nodes
//     public String serialize(TreeNode root) {
//         if (root == null) return "";
        
//         StringBuilder sb = new StringBuilder();
//         Queue<TreeNode> queue = new LinkedList<>();
//         queue.offer(root);
        
//         while (!queue.isEmpty()) {
//             TreeNode node = queue.poll();
            
//             if (node == null) {
//                 sb.append("#,");
//             } else {
//                 sb.append(node.val).append(",");
//                 queue.offer(node.left);
//                 queue.offer(node.right);
//             }
//         }
        
//         return sb.toString();
//     }

//     // Deserialize: reconstruct level by level using a queue
//     public TreeNode deserialize(String data) {
//         if (data == null || data.isEmpty()) return null;
        
//         String[] tokens = data.split(",");
//         TreeNode root = new TreeNode(Integer.parseInt(tokens[0]));
//         Queue<TreeNode> queue = new LinkedList<>();
//         queue.offer(root);
        
//         int i = 1;
//         while (!queue.isEmpty() && i < tokens.length) {
//             TreeNode node = queue.poll();
            
//             // Assign left child
//             if (!tokens[i].equals("#")) {
//                 node.left = new TreeNode(Integer.parseInt(tokens[i]));
//                 queue.offer(node.left);
//             }
//             i++;
            
//             // Assign right child
//             if (i < tokens.length && !tokens[i].equals("#")) {
//                 node.right = new TreeNode(Integer.parseInt(tokens[i]));
//                 queue.offer(node.right);
//             }
//             i++;
//         }
        
//         return root;
//     }
// }
// ```

// ---

// ### 🟡 Approach 2 — Preorder + Value-Range List Split

// #### Algorithm
// 1. **Serialize**: Preorder DFS → comma-separated string
// 2. **Deserialize**: First element is root; split remainder by `< root.val` (left) and `> root.val` (right); recurse

// ```java
// import java.util.*;

// public class Codec_Approach2 {

//     // Serialize: simple preorder traversal
//     public String serialize(TreeNode root) {
//         if (root == null) return "";
//         StringBuilder sb = new StringBuilder();
//         preorder(root, sb);
//         return sb.toString().trim();
//     }
    
//     private void preorder(TreeNode node, StringBuilder sb) {
//         if (node == null) return;
//         sb.append(node.val).append(" ");
//         preorder(node.left, sb);
//         preorder(node.right, sb);
//     }

//     // Deserialize: recursively split list using BST property
//     public TreeNode deserialize(String data) {
//         if (data == null || data.isEmpty()) return null;
//         String[] tokens = data.trim().split("\\s+");
//         List<Integer> values = new ArrayList<>();
//         for (String token : tokens) values.add(Integer.parseInt(token));
//         return buildTree(values);
//     }
    
//     private TreeNode buildTree(List<Integer> values) {
//         if (values.isEmpty()) return null;
        
//         int rootVal = values.get(0);  // first in preorder = root
//         TreeNode root = new TreeNode(rootVal);
        
//         // Partition remaining into left (< root) and right (> root)
//         List<Integer> leftVals = new ArrayList<>();
//         List<Integer> rightVals = new ArrayList<>();
        
//         for (int i = 1; i < values.size(); i++) {
//             if (values.get(i) < rootVal) leftVals.add(values.get(i));
//             else rightVals.add(values.get(i));
//         }
        
//         root.left = buildTree(leftVals);
//         root.right = buildTree(rightVals);
//         return root;
//     }
// }
// ```

// > ⚠️ **Warning**: This is O(n²) in the worst case (skewed tree) due to list splitting at every level.

// ---

// ### 🟢 Approach 3 — Preorder + Queue with Min/Max Bounds (OPTIMAL)

// #### Algorithm
// **Serialize:**
// 1. Perform preorder DFS
// 2. Encode each integer as **4 raw bytes** (or use comma-separated integers — shown here for clarity)
// 3. Result: compact string with no null markers

// **Deserialize:**
// 1. Load all values into a `Queue<Integer>`
// 2. Recursively rebuild: poll from queue; if value is within `[min, max]` bounds, it belongs to current subtree; otherwise, push back (conceptually — we use bounds instead)
// 3. BST bounds naturally guide left/right placement without any explicit splitting

// ```java
// import java.util.*;

// public class Codec_Approach3 {

//     // ─── SERIALIZE ────────────────────────────────────────────────
//     public String serialize(TreeNode root) {
//         StringBuilder sb = new StringBuilder();
//         serializeHelper(root, sb);
//         return sb.toString();
//     }

//     private void serializeHelper(TreeNode node, StringBuilder sb) {
//         if (node == null) return;
//         // Append value and delimiter
//         sb.append(node.val).append(",");
//         serializeHelper(node.left, sb);
//         serializeHelper(node.right, sb);
//     }

//     // ─── DESERIALIZE ──────────────────────────────────────────────
//     public TreeNode deserialize(String data) {
//         if (data == null || data.isEmpty()) return null;
        
//         // Load preorder values into a queue for O(1) front removal
//         Queue<Integer> queue = new LinkedList<>();
//         for (String token : data.split(",")) {
//             if (!token.isEmpty()) {
//                 queue.offer(Integer.parseInt(token));
//             }
//         }
        
//         return buildBST(queue, Integer.MIN_VALUE, Integer.MAX_VALUE);
//     }

//     /**
//      * Reconstruct BST using preorder values from the queue.
//      * 
//      * Key insight: In preorder, the next value belongs to the current
//      * subtree ONLY IF it falls within [min, max] bounds.
//      * If it doesn't fit, it belongs to an ancestor's subtree — so we
//      * leave it in the queue for the caller to handle.
//      */
//     private TreeNode buildBST(Queue<Integer> queue, int min, int max) {
//         if (queue.isEmpty()) return null;
        
//         int val = queue.peek();  // Look at next preorder value
        
//         // This value doesn't belong to the current subtree
//         if (val < min || val > max) return null;
        
//         // Consume the value — it IS the current node
//         queue.poll();
//         TreeNode node = new TreeNode(val);
        
//         // Left subtree: values must be in (min, val)
//         node.left = buildBST(queue, min, val - 1);
        
//         // Right subtree: values must be in (val, max)
//         node.right = buildBST(queue, val + 1, max);
        
//         return node;
//     }
// }

// // Standard TreeNode definition
// class TreeNode {
//     int val;
//     TreeNode left, right;
//     TreeNode(int val) { this.val = val; }
// }
// ```

// ---

// ## 5. Time & Space Complexity

// ### Approach 1 — BFS + Null Markers

// | | Complexity | Reasoning |
// |--|-----------|-----------|
// | **Time** | O(n) | Visit every node exactly once in both serialize and deserialize |
// | **Space** | O(n) | Queue holds up to O(n) nodes; string stores O(n) tokens including `#` markers |

// > For a balanced tree with n=1000: ~2000 tokens stored (null markers double storage)

// ---

// ### Approach 2 — Preorder + List Split

// | | Complexity | Reasoning |
// |--|-----------|-----------|
// | **Time** | O(n²) worst | Each level scans the remaining list; skewed BST → O(n) levels × O(n) scan |
// | **Time** | O(n log n) avg | Balanced BST → O(log n) levels, each scanning O(n) total |
// | **Space** | O(n²) worst | New sublists created at each recursion level in skewed case |

// > For a right-skewed BST with n=1000: ~500,000 comparisons in the worst case

// ---

// ### Approach 3 — Preorder + Queue with Bounds ✅

// | | Complexity | Reasoning |
// |--|-----------|-----------|
// | **Time** | O(n) | Each value is enqueued once, dequeued once, peeked at most once per ancestor — O(n) total |
// | **Space** | O(n) | Queue holds n values; recursion stack O(h) = O(log n) avg, O(n) worst |

// > For n=10,000: exactly 10,000 enqueue + 10,000 dequeue operations — clean linear behavior

// ---

// ## 6. Complete Worked Examples

// ### Example Tree
// ```
//         8
//        / \
//       5   10
//      / \    \
//     1   7   12
// ```
// **Preorder traversal**: `8, 5, 1, 7, 10, 12`

// ---

// ### Approach 1 — BFS Walkthrough

// **Serialize (BFS):**
// ```
// Level 0: [8]          → "8,"
// Level 1: [5, 10]      → "5,10,"
// Level 2: [1, 7, #, 12]→ "1,7,#,12,"
// Level 3: [#,#,#,#,#,#]→ "#,#,#,#,#,#,"
// ```
// **Serialized string**: `"8,5,10,1,7,#,12,#,#,#,#,#,#,"`

// **Deserialize:**
// | Step | Queue | Token | Action |
// |------|-------|-------|--------|
// | 1 | [8] | "5" | 8.left = node(5) |
// | 2 | [8] | "10" | 8.right = node(10) |
// | 3 | [5] | "1" | 5.left = node(1) |
// | 4 | [5] | "7" | 5.right = node(7) |
// | 5 | [10] | "#" | 10.left = null |
// | 6 | [10] | "12" | 10.right = node(12) |

// ---

// ### Approach 3 — Queue + Bounds Walkthrough (Optimal)

// **Serialized string**: `"8,5,1,7,10,12,"`

// **Deserialize — Queue initially**: `[8, 5, 1, 7, 10, 12]`

// ```
// buildBST(queue=[8,5,1,7,10,12], min=-∞, max=+∞)
//   peek=8, 8 ∈ [-∞,+∞] ✅ → poll 8, create node(8)
  
//   LEFT: buildBST(queue=[5,1,7,10,12], min=-∞, max=7)
//     peek=5, 5 ∈ [-∞,7] ✅ → poll 5, create node(5)
    
//     LEFT: buildBST(queue=[1,7,10,12], min=-∞, max=4)
//       peek=1, 1 ∈ [-∞,4] ✅ → poll 1, create node(1)
//       LEFT:  buildBST(queue=[7,10,12], min=-∞, max=0)  peek=7 ∉ [-∞,0] → return null
//       RIGHT: buildBST(queue=[7,10,12], min=2, max=4)   peek=7 ∉ [2,4]  → return null
//       ← node(1) [leaf]
      
//     RIGHT: buildBST(queue=[7,10,12], min=6, max=7)
//       peek=7, 7 ∈ [6,7] ✅ → poll 7, create node(7)
//       LEFT:  buildBST(queue=[10,12], min=6, max=6) peek=10 ∉ [6,6] → return null
//       RIGHT: buildBST(queue=[10,12], min=8, max=7) min>max         → return null
//       ← node(7) [leaf]
      
//     ← node(5) with left=1, right=7
    
//   RIGHT: buildBST(queue=[10,12], min=9, max=+∞)
//     peek=10, 10 ∈ [9,+∞] ✅ → poll 10, create node(10)
//     LEFT:  buildBST(queue=[12], min=9, max=9)   peek=12 ∉ [9,9]  → return null
//     RIGHT: buildBST(queue=[12], min=11, max=+∞) peek=12 ∈ [11,+∞] ✅ → poll 12
//            ← node(12) [leaf]
//     ← node(10) with right=12
    
// ← node(8) with left=node(5), right=node(10) ✅
// ```

// **Final reconstructed tree matches the original exactly.**

// ---

// ## 7. Edge Cases

// | Edge Case | Description | Approach 1 | Approach 2 | Approach 3 ✅ |
// |-----------|-------------|------------|------------|--------------|
// | **Empty tree** | `root == null` | Returns `""`, deserialize returns null | Same | Same |
// | **Single node** | Only root, no children | Works, outputs `"val,#,#"` | Works, `[val]` → single node | Works cleanly |
// | **Skewed tree (sorted input)** | All nodes go right (like a linked list) | O(n) but stores many `#` | O(n²) ⚠️ | O(n) ✅ |
// | **Integer.MIN_VALUE node** | Boundary value as node val | Works | Works | ⚠️ `val - 1` can underflow! Use `long` bounds or handle separately |
// | **Integer.MAX_VALUE node** | Boundary value as node val | Works | Works | ⚠️ `val + 1` can overflow! Use `long` bounds |
// | **Large tree (n = 10,000+)** | Deep recursion | Stack overflow risk | Stack overflow + slow | Acceptable for log n depth; iterative for extreme depth |

// ### Fixing the Integer Overflow in Approach 3

// ```java
// // Use long bounds to avoid overflow at Integer.MIN_VALUE / Integer.MAX_VALUE
// private TreeNode buildBST(Queue<Integer> queue, long min, long max) {
//     if (queue.isEmpty()) return null;
    
//     long val = queue.peek();
//     if (val < min || val > max) return null;
    
//     queue.poll();
//     TreeNode node = new TreeNode((int) val);
//     node.left  = buildBST(queue, min, val - 1);
//     node.right = buildBST(queue, val + 1, max);
//     return node;
// }

// // Call with:
// buildBST(queue, Long.MIN_VALUE, Long.MAX_VALUE);
// ```

// ---

// ## 8. Final Summary

// ### Comparison Table

// | Approach | Serialize Size | Deserialize Speed | Handles Skewed? | Production Ready? |
// |----------|--------------|------------------|-----------------|------------------|
// | BFS + Null markers | Large (many `#`) | O(n) | Yes | Only for general trees |
// | Preorder + List split | Compact | O(n²) worst | Slow | No |
// | **Preorder + Queue bounds** | **Compact** | **O(n)** | **Yes** | **Yes ✅** |

// ### What to Remember
// > **BSTs can be perfectly reconstructed from just their preorder traversal** — no null markers needed. The min/max bounds technique during deserialization is the key pattern: it tells you in O(1) whether the next queued value belongs to the current subtree, making the entire reconstruction linear.

// **Pattern to internalize**: `Preorder sequence + BST property + bounds propagation = O(n) reconstruction`

// ---

// ## 9. Companies & Frequency

// | Company | Frequency | Notes |
// |---------|-----------|-------|
// | **Amazon** | ⭐⭐⭐⭐⭐ Very High | Top system design + DS rounds |
// | **Microsoft** | ⭐⭐⭐⭐ High | L62/L63 interviews |
// | **Facebook / Meta** | ⭐⭐⭐⭐ High | Coding + design mix rounds |
// | **Google** | ⭐⭐⭐ Medium-High | L4/L5 coding rounds |
// | **LinkedIn** | ⭐⭐⭐ Medium | Backend engineering rounds |
// | **Uber** | ⭐⭐⭐ Medium | Infrastructure-focused rounds |
// | **Bloomberg** | ⭐⭐ Medium | SWE interviews |
// | **Adobe** | ⭐⭐ Medium | DS rounds |

// > **LeetCode Problem #449** — Appeared in **400+ reported interview rounds** across platforms as of 2025. It is a **premium-frequency** problem tagged under Trees, DFS, BFS, Design, and String Encoding categories.
// @formatter:on