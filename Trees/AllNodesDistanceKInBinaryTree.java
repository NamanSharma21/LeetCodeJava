package Trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import Datastructures.TreeNode;

public class AllNodesDistanceKInBinaryTree {
    public static void main(String[] args) {
        AllNodesDistanceKInBinaryTree allNodesDistanceKInBinaryTree = new AllNodesDistanceKInBinaryTree();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(4);
        System.out.println(
                "AllNodesDistanceKInBinaryTree : "
                        + allNodesDistanceKInBinaryTree.distanceKIterativeBFS(root, root.left, 2));
        System.out.println(
                "AllNodesDistanceKInBinaryTree : "
                        + allNodesDistanceKInBinaryTree.distanceKReccursiveDFS(root, root.left, 2));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/description/?envType=problem-list-v2&envId=tree
     * 
     * Given the root of a binary tree, the value of a target node target, and an
     * integer k, return an array of the values of all nodes that have a distance k
     * from the target node.
     * 
     * You can return the answer in any order.
     * 
     * 
     * 
     * Example 1:
     * 
     *        3
     *      /   \
     *     5     1
     *    / \   / \  
     *   6   2 0   8
     *  / \
     * 7   4
     * 
     * Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, k = 2
     * Output: [7,4,1]
     * Explanation: The nodes that are a distance 2 from the target node (with value
     * 5) have values 7, 4, and 1.
     * Example 2:
     * 
     * Input: root = [1], target = 1, k = 3
     * Output: []
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [1, 500].
     * 0 <= Node.val <= 500
     * All the values Node.val are unique.
     * target is the value of one of the nodes in the tree.
     * 0 <= k <= 1000
     */
    // @formatter:on

    public List<Integer> distanceKIterativeBFS(TreeNode root, TreeNode target, int k) {
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        buildParentMap(root, null, parentMap);
        Queue<TreeNode> queue = new LinkedList<>();
        Set<TreeNode> visited = new HashSet<>();
        queue.offer(target);
        visited.add(target);

        int currentDistance = 0;
        while (!queue.isEmpty()) {
            if (currentDistance == k) {
                List<Integer> result = new ArrayList<>();
                for (TreeNode node : queue) {
                    result.add(node.val);
                }
                return result;
            }
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                if (current.left != null && !visited.contains(current.left)) {
                    queue.offer(current.left);
                    visited.add(current.left);
                }
                if (current.right != null && !visited.contains(current.right)) {
                    queue.offer(current.right);
                    visited.add(current.right);
                }

                TreeNode parent = parentMap.get(current);
                if (parent != null && !visited.contains(parent)) {
                    queue.offer(parent);
                    visited.add(parent);
                }
            }
            currentDistance++;
        }
        return new ArrayList<>();
    }

    public void buildParentMap(TreeNode root, TreeNode parent, Map<TreeNode, TreeNode> parentMap) {
        if (root == null)
            return;
        parentMap.put(root, parent);
        buildParentMap(root.left, root, parentMap);
        buildParentMap(root.right, root, parentMap);
    }

    private List<Integer> result = new ArrayList<>();
    private int k;

    public List<Integer> distanceKReccursiveDFS(TreeNode root, TreeNode target, int k) {
        this.k = k;
        dfs(root, target);
        return result;
    }

    public int dfs(TreeNode root, TreeNode target) {
        if (root == null)
            return -1;

        if (root == target) {
            collectDescendants(root, 0);
            return 1;
        }

        int leftDist = dfs(root.left, target);
        if (leftDist != -1) {
            if (leftDist == k)
                result.add(root.val);
            collectDescendants(root.right, leftDist + 1);
            return leftDist + 1;
        }

        int rightDist = dfs(root.right, target);
        if (rightDist != -1) {
            if (rightDist == k)
                result.add(root.val);
            collectDescendants(root.left, rightDist + 1);
            return rightDist + 1;
        }

        return -1;

    }

    public void collectDescendants(TreeNode root, int stepsFormTarget) {
        if (root == null)
            return;
        if (stepsFormTarget == k) {
            result.add(root.val);
            return;
        }
        collectDescendants(root.left, stepsFormTarget + 1);
        collectDescendants(root.right, stepsFormTarget + 1);
    }
}

// @formatter:off
/*
 * =============================================================================
 * All Nodes Distance K in Binary Tree — Deep Dive
 * =============================================================================
 *
 * -----------------------------------------------------------------------------
 * 1. PROBLEM STATEMENT
 * -----------------------------------------------------------------------------
 *
 * Given a binary tree, a TARGET node within that tree, and an integer K,
 * find all nodes that are exactly K edges away from the target node.
 *
 * Input Format:
 *   - root   : the root of a binary tree (TreeNode)
 *   - target : a reference to a specific node in the tree (TreeNode)
 *   - k      : a non-negative integer
 *
 * Output Format:
 *   - A List<Integer> of node VALUES at distance exactly K from the target
 *     node (any order)
 *
 * Constraints:
 *   - Number of nodes : [1, 500]
 *   - Node values     : 0 to 500, ALL UNIQUE
 *   - target is guaranteed to be in the tree
 *   - 0 <= k <= 1000
 *
 * What Needs to Be Computed:
 *   Every node (including the target itself when K=0) that is exactly K edges
 *   away — this includes nodes BELOW the target (descendants) AND nodes ABOVE
 *   (ancestors and their subtrees).
 *
 * -----------------------------------------------------------------------------
 * 2. INTUITION
 * -----------------------------------------------------------------------------
 *
 * Simple Human Reasoning:
 *   Imagine you are standing at the target node. You can walk along edges in
 *   ANY direction — down to children, or UP to the parent. After exactly K
 *   steps, wherever you can be is a valid answer.
 *
 *   The challenge is that a binary tree only has DOWNWARD pointers
 *   (left/right children). You cannot go "up" to a parent natively.
 *
 * The Key Insight:
 *   If we could treat the tree as an UNDIRECTED GRAPH, this becomes a simple
 *   BFS from the target node — expand outward level by level, and stop at
 *   level K.
 *
 * What Makes It Tricky:
 *   - Trees are directional (parent -> child only)
 *   - To go "upward," you need to either:
 *       (a) Pre-build parent pointers (convert to graph), or
 *       (b) Do a DFS from root and track how far ancestors are from target
 *
 * -----------------------------------------------------------------------------
 * 3. APPROACH OVERVIEW
 * -----------------------------------------------------------------------------
 *
 *  #  | Approach                   | Key Idea                              | Complexity | Use When
 *  ---|----------------------------|---------------------------------------|------------|------------------------------
 *  1  | Brute Force DFS            | For every node, compute distance to   | O(n^2)     | Very small trees, conceptual
 *     |                            | target via LCA                        |            | understanding
 *  2  | Parent Map + BFS [OPTIMAL] | Build parent map, then BFS treating   | O(n)       | Interviews, production
 *     |                            | tree as undirected graph              |            |
 *  3  | DFS with Return Value      | Single DFS, propagate distance info   | O(n)       | Elegant one-pass solution
 *     |                            | back up through return values         |            |
 *
 *  Recommended: Approach 2 (Parent Map + BFS) — cleanest, most intuitive,
 *  easiest to explain in an interview.
 *
 * -----------------------------------------------------------------------------
 * 4. DETAILED SOLUTIONS IN JAVA
 * -----------------------------------------------------------------------------
 *
 * ~~~ APPROACH 1 — Brute Force: Distance via LCA ~~~
 *
 * Algorithm:
 *   1. For every node in the tree, compute its distance to target
 *   2. Distance between two nodes = distance via their Lowest Common Ancestor
 *   3. Collect all nodes where distance == K
 *
 * ----- CODE -----
 *
 *  class Solution {
 *      public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
 *          List<Integer> result = new ArrayList<>();
 *          findDistanceFromRoot(root, target, k, result);
 *          return result;
 *      }
 *
 *      // Returns distance from this subtree's root to target (-1 if not found)
 *      private int findDistanceFromRoot(TreeNode node, TreeNode target,
 *                                       int k, List<Integer> result) {
 *          if (node == null) return -1;
 *
 *          if (node == target) {
 *              // Target found: collect descendants at distance k below it
 *              collectAtDistance(node, k, result);
 *              return 0; // distance from target to itself
 *          }
 *
 *          int leftDist = findDistanceFromRoot(node.left, target, k, result);
 *          if (leftDist != -1) {
 *              // Target is in left subtree, current node is (leftDist+1) from target
 *              if (leftDist + 1 == k) result.add(node.val);
 *              else collectAtDistance(node.right, k - leftDist - 2, result);
 *              return leftDist + 1;
 *          }
 *
 *          int rightDist = findDistanceFromRoot(node.right, target, k, result);
 *          if (rightDist != -1) {
 *              if (rightDist + 1 == k) result.add(node.val);
 *              else collectAtDistance(node.left, k - rightDist - 2, result);
 *              return rightDist + 1;
 *          }
 *
 *          return -1; // target not in this subtree
 *      }
 *
 *      // Collect all nodes exactly 'dist' levels below 'node'
 *      private void collectAtDistance(TreeNode node, int dist,
 *                                     List<Integer> result) {
 *          if (node == null || dist < 0) return;
 *          if (dist == 0) {
 *              result.add(node.val);
 *              return;
 *          }
 *          collectAtDistance(node.left,  dist - 1, result);
 *          collectAtDistance(node.right, dist - 1, result);
 *      }
 *  }
 *
 * ~~~ APPROACH 2 — OPTIMAL: Parent Map + BFS ~~~
 *
 * Algorithm:
 *   Phase 1 — Build Parent Map:
 *     DFS through the entire tree. For each node, record its parent in a
 *     HashMap<TreeNode, TreeNode>.
 *
 *   Phase 2 — BFS from Target:
 *     Treat the tree as an undirected graph. Each node has up to 3 neighbors:
 *     left, right, parent. BFS outward from target exactly K levels.
 *     The nodes at level K are the answer.
 *
 * ----- CODE -----
 *
 *  class Solution {
 *      public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
 *          // Phase 1: Map each node to its parent
 *          Map<TreeNode, TreeNode> parentMap = new HashMap<>();
 *          buildParentMap(root, null, parentMap);
 *
 *          // Phase 2: BFS from target treating tree as undirected graph
 *          Queue<TreeNode> queue   = new LinkedList<>();
 *          Set<TreeNode>   visited = new HashSet<>();
 *          queue.offer(target);
 *          visited.add(target);
 *
 *          int currentDistance = 0;
 *
 *          while (!queue.isEmpty()) {
 *              if (currentDistance == k) {
 *                  // All nodes currently in queue are exactly distance k
 *                  List<Integer> result = new ArrayList<>();
 *                  for (TreeNode node : queue) result.add(node.val);
 *                  return result;
 *              }
 *
 *              int levelSize = queue.size();
 *              for (int i = 0; i < levelSize; i++) {
 *                  TreeNode current = queue.poll();
 *
 *                  // Explore left child
 *                  if (current.left != null && !visited.contains(current.left)) {
 *                      visited.add(current.left);
 *                      queue.offer(current.left);
 *                  }
 *                  // Explore right child
 *                  if (current.right != null && !visited.contains(current.right)) {
 *                      visited.add(current.right);
 *                      queue.offer(current.right);
 *                  }
 *                  // Explore parent (the key move that makes this work!)
 *                  TreeNode parent = parentMap.get(current);
 *                  if (parent != null && !visited.contains(parent)) {
 *                      visited.add(parent);
 *                      queue.offer(parent);
 *                  }
 *              }
 *              currentDistance++;
 *          }
 *
 *          return new ArrayList<>(); // k > tree depth, no nodes found
 *      }
 *
 *      // DFS to record parent of every node
 *      private void buildParentMap(TreeNode node, TreeNode parent,
 *                                  Map<TreeNode, TreeNode> parentMap) {
 *          if (node == null) return;
 *          parentMap.put(node, parent);
 *          buildParentMap(node.left,  node, parentMap);
 *          buildParentMap(node.right, node, parentMap);
 *      }
 *  }
 *
 * ~~~ APPROACH 3 — Elegant DFS with Return Value ~~~
 *
 * Algorithm:
 *   Single DFS pass. When the target is found in a subtree, return the
 *   DISTANCE from that node to the target. As we bubble back up, at each
 *   ancestor we know:
 *     - How far we are above the target
 *     - We can now look into the OTHER subtree at the right remaining distance
 *
 * ----- CODE -----
 *
 *  class Solution {
 *      private List<Integer> result = new ArrayList<>();
 *      private int k;
 *
 *      public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
 *          this.k = k;
 *          dfs(root, target);
 *          return result;
 *      }
 *
 *      // Returns distance from 'node' to 'target', or -1 if not in subtree
 *      private int dfs(TreeNode node, TreeNode target) {
 *          if (node == null) return -1;
 *
 *          if (node == target) {
 *              collectDescendants(node, 0); // collect nodes k levels below target
 *              return 1; // distance 1 from target for parent's use
 *          }
 *
 *          int leftDist = dfs(node.left, target);
 *          if (leftDist != -1) {
 *              // Target was found in left subtree
 *              if (leftDist == k) result.add(node.val);
 *              // Right subtree: target is leftDist away, need (k-leftDist-1) more
 *              collectDescendants(node.right, leftDist + 1);
 *              return leftDist + 1;
 *          }
 *
 *          int rightDist = dfs(node.right, target);
 *          if (rightDist != -1) {
 *              if (rightDist == k) result.add(node.val);
 *              collectDescendants(node.left, rightDist + 1);
 *              return rightDist + 1;
 *          }
 *
 *          return -1;
 *      }
 *
 *      // Collect all nodes exactly 'stepsFromTarget' away, descending from node
 *      private void collectDescendants(TreeNode node, int stepsFromTarget) {
 *          if (node == null) return;
 *          if (stepsFromTarget == k) {
 *              result.add(node.val);
 *              return;
 *          }
 *          collectDescendants(node.left,  stepsFromTarget + 1);
 *          collectDescendants(node.right, stepsFromTarget + 1);
 *      }
 *  }
 *
 * -----------------------------------------------------------------------------
 * 5. TIME & SPACE COMPLEXITY (WITH REASONING)
 * -----------------------------------------------------------------------------
 *
 * Approach 1 — Brute Force:
 *   Time  : O(n^2) — For every node, we may traverse O(n) nodes to find
 *                    target/distance
 *   Space : O(h)   — Recursion stack of height h
 *                    (O(log n) balanced, O(n) worst case skewed)
 *   Example: 500 nodes -> ~250,000 operations worst case
 *
 * Approach 2 — Parent Map + BFS [OPTIMAL]:
 *   Time  : O(n)   — DFS visits all n nodes once; BFS visits at most n nodes
 *   Space : O(n)   — Parent map stores n entries; visited set stores up to n;
 *                    queue holds up to n
 *   Example: 500 nodes -> ~1000 operations total (500 DFS + 500 BFS max)
 *
 * Approach 3 — DFS with Return Value:
 *   Time  : O(n)   — Each node visited at most twice (main DFS +
 *                    collectDescendants)
 *   Space : O(h)   — Only recursion stack, no extra data structures
 *   Example: 500 nodes balanced -> ~500-1000 operations,
 *            O(log 500) ≈ 9 stack frames
 *
 * -----------------------------------------------------------------------------
 * 6. COMPLETE WORKED EXAMPLES
 * -----------------------------------------------------------------------------
 *
 * Example Tree:
 *
 *           3
 *          / \
 *         5   1
 *        / \ / \
 *       6  2 0  8
 *         / \
 *        7   4
 *
 *  Target = 5, K = 2
 *  Expected Output: [7, 4, 1]
 *
 * --- Approach 2: Parent Map + BFS Walkthrough ---
 *
 *   Phase 1: Build Parent Map
 *
 *     Node | Parent
 *     -----|-------
 *      3   | null
 *      5   | 3
 *      1   | 3
 *      6   | 5
 *      2   | 5
 *      0   | 1
 *      8   | 1
 *      7   | 2
 *      4   | 2
 *
 *   Phase 2: BFS from node 5
 *
 *     Distance 0:  Queue = [5]          visited = {5}
 *                  -> process 5:
 *                       left=6  -> enqueue
 *                       right=2 -> enqueue
 *                       parent=3 -> enqueue
 *
 *     Distance 1:  Queue = [6, 2, 3]    visited = {5,6,2,3}
 *                  -> process 6: left=null, right=null, parent=5(visited)
 *                  -> process 2: left=7 -> enqueue, right=4 -> enqueue,
 *                                parent=5(visited)
 *                  -> process 3: left=5(visited), right=1 -> enqueue,
 *                                parent=null
 *
 *     Distance 2:  Queue = [7, 4, 1]  <- currentDistance == k == 2
 *
 *   => Return [7, 4, 1]  ✓
 *
 * --- Approach 3: DFS with Return Value Walkthrough ---
 *
 *   Target = 5, K = 2
 *
 *   dfs(3, target=5)
 *     -> dfs(5, target=5)
 *         -> node==target! collectDescendants(5, 0)
 *             -> dist=0 != 2, recurse left/right
 *             -> collectDescendants(6, 1) -> dist=1 != 2, no children
 *             -> collectDescendants(2, 1)
 *                 -> collectDescendants(7, 2) -> dist==k=2  add 7
 *                 -> collectDescendants(4, 2) -> dist==k=2  add 4
 *         -> return 1
 *
 *     leftDist = 1
 *     leftDist (1) != k (2), don't add node 3
 *     collectDescendants(node.right=1, leftDist+1=2)
 *         -> stepsFromTarget=2 == k=2  add 1
 *     return 2
 *
 *   => Result = [7, 4, 1]  ✓
 *
 * --- Example 2: K = 0 (Edge Case) ---
 *
 *   Target = 5, K = 0
 *   BFS starts at 5, currentDistance == 0 == k immediately.
 *   => Return [5]  ✓
 *
 * --- Example 3: Target is Root ---
 *
 *   Target = 3, K = 2
 *   BFS from 3 expands:
 *     Distance 1: [5, 1]
 *     Distance 2: [6, 2, 0, 8]
 *   => Return [6, 2, 0, 8]  ✓
 *
 * -----------------------------------------------------------------------------
 * 7. EDGE CASES
 * -----------------------------------------------------------------------------
 *
 *  Edge Case             | What To Check                     | Approach 2 | Approach 3
 *  ----------------------|-----------------------------------|------------|------------
 *  K = 0                 | Return just [target.val]          | OK         | OK
 *  K > tree depth        | Return []                         | OK         | OK
 *  Single node tree      | K=0 -> [root.val], K>0 -> []      | OK         | OK
 *  Target is root        | No parent, search all descendants | OK         | OK
 *  Target is a leaf      | No children, traverse ancestors   | OK         | OK
 *  Linear/skewed tree    | Stack overflow risk for Approach 3| OK (iter.) | WARN (O(n)
 *                        |                                   |            | recursion,
 *                        |                                   |            | safe for n<=500)
 *  K > 1000              | BFS simply exhausts all nodes     | OK         | OK
 *  All unique node values| No ambiguity per constraints      | OK         | OK
 *
 * -----------------------------------------------------------------------------
 * 8. FINAL SUMMARY
 * -----------------------------------------------------------------------------
 *
 *  Approach                 | Time   | Space | Code Simplicity | Interview
 *  -------------------------|--------|-------|-----------------|----------
 *  Brute Force DFS          | O(n^2) | O(h)  | Medium          | NO (slow)
 *  Parent Map + BFS [BEST]  | O(n)   | O(n)  | High            | YES
 *  DFS with Return Value    | O(n)   | O(h)  | Medium-Hard     | YES (elegant)
 *
 *  RECOMMENDATION:
 *    Use Approach 2 (Parent Map + BFS) in interviews. It's intuitive to
 *    explain ("make the tree undirected, then BFS"), easy to code correctly
 *    under pressure, and optimal in time.
 *
 *  KEY PATTERN TO REMEMBER:
 *    "When a tree problem requires movement in BOTH directions (up and down),
 *    either build parent pointers and BFS as a graph, OR use DFS return values
 *    to propagate ancestor distances."
 *
 *    This pattern appears in many problems:
 *      - Distance in trees
 *      - Find nearest node
 *      - Path between two nodes
 *
 * -----------------------------------------------------------------------------
 * 9. COMPANY APPEARANCES
 * -----------------------------------------------------------------------------
 *
 *  Company         | Frequency        | Notes
 *  ----------------|------------------|--------------------------------------
 *  Amazon          | Very High (★★★★★) | Top asked tree question
 *  Google          | Very High (★★★★★) | Appears in phone screens and onsite
 *  Microsoft       | High      (★★★★ ) | Common in SDE2/SDE interviews
 *  Facebook/Meta   | High      (★★★★ ) | Tree + graph crossover
 *  Bloomberg       | Medium    (★★★  ) | Backend engineering rounds
 *  Adobe           | Medium    (★★★  ) | Core DS&A rounds
 *  Apple           | Medium    (★★★  ) | Senior engineer interviews
 *  LinkedIn        | Moderate  (★★   ) | Occasionally appears
 *
 *  Total LeetCode Community Reports (as of 2025):
 *    This problem (LeetCode #863) has been reported 200+ times across
 *    interview experiences. It is considered a MUST-KNOW tree problem for
 *    FAANG interviews, ranking in the TOP 30 most frequently asked tree
 *    problems.
 *
 * =============================================================================
 */
// @formatter:on
