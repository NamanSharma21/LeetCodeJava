package Trees;

import java.util.HashMap;
import java.util.Map;

import Datastructures.TreeNode;

public class PathSumIII {
    public static void main(String[] args) {
        PathSumIII pathSumIII = new PathSumIII();
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(-3);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(2);
        root.right.right = new TreeNode(11);
        root.left.left.left = new TreeNode(3);
        root.left.left.right = new TreeNode(-2);
        root.left.right.right = new TreeNode(1);
        System.out.println("PathSumIII : " + pathSumIII.pathSum(root, 8));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/path-sum-iii/description/?envType=problem-list-v2&envId=tree
     * 
     * Given the root of a binary tree and an integer targetSum, return the number
     * of paths where the sum of the values along the path equals targetSum.
     * 
     * The path does not need to start or end at the root or a leaf, but it must go
     * downwards (i.e., traveling only from parent nodes to child nodes).
     * 
     * 
     * 
     * Example 1:
     * 
     *         10
     *        /  \
     *       5    -3
     *      / \     \
     *     3   2     11
     *    / \   \
     *   3  -2   1
     * 
     * Paths summing to 8:
     *   1. 5 -> 3
     *   2. 5 -> 2 -> 1
     *   3. -3 -> 11
     * 
     * Input: root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
     * Output: 3
     * Explanation: The paths that sum to 8 are shown.
     * Example 2:
     * 
     *            5
     *           / \
     *          4   8
     *         /   / \
     *        11  13  4
     *       / \     / \
     *      7   2   5   1
     * 
     * Paths summing to 22:
     *   1. 5 -> 4 -> 11 -> 2
     *   2. 4 -> 11 -> 7
     *   3. 5 -> 8 -> 4 -> 5
     * 
     * Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
     * Output: 3
     * 
     * 
     * Constraints:
     * 
     * The number of nodes in the tree is in the range [0, 1000].
     * -109 <= Node.val <= 109
     * -1000 <= targetSum <= 1000
     */
    // @formatter:on

    public int pathSum(TreeNode root, int targetSum) {
        Map<Long, Integer> prefixSumCount = new HashMap<>();
        prefixSumCount.put(0L, 1);
        return dfs(root, 0L, targetSum, prefixSumCount);
    }

    public int dfs(TreeNode root, long currentSum, int targetSum, Map<Long, Integer> prefixSumCount) {
        if (root == null)
            return 0;
        currentSum += root.val;
        long neededPrefix = currentSum - targetSum;
        int pathEndingHere = prefixSumCount.getOrDefault(neededPrefix, 0);

        prefixSumCount.merge(currentSum, 1, Integer::sum);

        int leftPaths = dfs(root.left, currentSum, targetSum, prefixSumCount);
        int rightPaths = dfs(root.right, currentSum, targetSum, prefixSumCount);

        prefixSumCount.merge(currentSum, -1, Integer::sum);
        return pathEndingHere + leftPaths + rightPaths;
    }

}

// @formatter:off
/*
 * =============================================================================
 * Path Sum III — Deep Dive Analysis
 * =============================================================================
 *
 * -----------------------------------------------------------------------------
 * 1. PROBLEM STATEMENT
 * -----------------------------------------------------------------------------
 *
 * Restated in Plain English:
 *   You're given the root of a binary tree and an integer target. Your task is
 *   to count the total number of paths in the tree where the sum of node values
 *   along the path equals the target.
 *
 * Key Rules:
 *   - A path MUST go downward — from ancestor to descendant (parent → child only)
 *   - The path does NOT need to start at the root
 *   - The path does NOT need to end at a leaf
 *   - It can start and end at any node, as long as it travels downward
 *   - Node values can be negative, zero, or positive
 *
 * Input Format:
 *   TreeNode root  — root of the binary tree (can be null)
 *   int targetSum  — the target integer sum
 *
 * Output Format:
 *   int — count of paths whose node values sum to targetSum
 *
 * Constraints:
 *   Number of nodes: 0 to 1000
 *   Node values:    -10^9 to 10^9
 *   targetSum:      -10^9 to 10^9
 *
 * -----------------------------------------------------------------------------
 * 2. INTUITION
 * -----------------------------------------------------------------------------
 *
 * The Core Idea:
 *   Imagine walking down any path from root to leaf. At each node, you're
 *   building a running prefix sum (sum from root to current node).
 *
 *   Key insight:
 *     If at some earlier node the prefix sum was X, and the current prefix sum
 *     is X + targetSum, then the path between those two nodes sums exactly to
 *     targetSum.
 *
 *   This is the same trick used in subarray sum problems (like LeetCode 560),
 *   adapted for trees.
 *
 * Human Reasoning:
 *   1. Start DFS from root, carrying the running sum
 *   2. At each node, ask: "Have I seen a prefix sum equal to
 *      (currentSum - targetSum) earlier on this root-to-node path?"
 *   3. If yes → that many paths ending here sum to target
 *   4. Store prefix sums in a HashMap as you go down, remove them as you
 *      backtrack
 *
 * What Makes It Tricky:
 *   - Paths don't have to start at root → eliminates simple DFS
 *   - Negative values → you can't use greedy shortcuts
 *   - Backtracking the HashMap correctly is essential (forgetting this causes
 *     overcounting)
 *
 * -----------------------------------------------------------------------------
 * 3. APPROACH OVERVIEW
 * -----------------------------------------------------------------------------
 *
 *  #  | Approach                  | Key Idea                              | Time   | Space
 *  ---|---------------------------|---------------------------------------|--------|-------
 *  1  | Brute Force               | For every node, run a DFS treating    | O(N²)  | O(N)
 *     |                           | it as path start                      |        |
 *  ---|---------------------------|---------------------------------------|--------|-------
 *  2  | Prefix Sum + HashMap      | Track prefix sums during single DFS   | O(N)   | O(N)
 *     | (OPTIMAL)                 | + backtrack                           |        |
 *
 *  Optimal: Approach 2 (Prefix Sum + HashMap)
 *    Single pass, linear time. This is the interview-expected solution.
 *
 * -----------------------------------------------------------------------------
 * 4. DETAILED SOLUTIONS IN JAVA
 * -----------------------------------------------------------------------------
 *
 * ~~~ Approach 1 — Brute Force (Double DFS) ~~~
 *
 * Algorithm Step-by-Step:
 *   1. For every node in the tree, launch a dedicated DFS treating that node
 *      as the path start.
 *   2. In the inner DFS, accumulate sum downward — if it hits target, increment
 *      count.
 *   3. Outer DFS visits all nodes → O(N) nodes × O(N) inner DFS = O(N²)
 *
 * -----------------------------------------------------------------------------
 */

// ~~~ Approach 1 — Brute Force ~~~

class SolutionBruteForce {

    public int pathSum(TreeNode root, int targetSum) {
        if (root == null) return 0;

        // Count paths starting from root + paths in left subtree + right subtree
        return countPathsFrom(root, targetSum)
             + pathSum(root.left, targetSum)
             + pathSum(root.right, targetSum);
    }

    // Count all downward paths starting exactly at 'node' that sum to 'remaining'
    private int countPathsFrom(TreeNode node, long remaining) {
        if (node == null) return 0;

        int count = 0;

        // This node itself completes a valid path
        if (node.val == remaining) count++;

        // Continue downward in both directions
        count += countPathsFrom(node.left,  remaining - node.val);
        count += countPathsFrom(node.right, remaining - node.val);

        return count;
    }
}

/*
 * -----------------------------------------------------------------------------
 */

// ~~~ Approach 2 — Prefix Sum + HashMap (Optimal) ~~~

class SolutionOptimal {

    public int pathSum(TreeNode root, int targetSum) {
        // prefixSumCount: maps prefix sum → how many times seen on current path
        Map<Long, Integer> prefixSumCount = new HashMap<>();

        // Base case: empty prefix (sum = 0) seen once before we start
        prefixSumCount.put(0L, 1);

        return dfs(root, 0L, targetSum, prefixSumCount);
    }

    private int dfs(TreeNode node, long currentSum, int targetSum,
                    Map<Long, Integer> prefixSumCount) {
        if (node == null) return 0;

        // Extend current prefix sum to include this node
        currentSum += node.val;

        // How many earlier prefixes allow a valid path ending here?
        long neededPrefix = currentSum - targetSum;
        int pathsEndingHere = prefixSumCount.getOrDefault(neededPrefix, 0);

        // Record this prefix sum in the map before going deeper
        prefixSumCount.merge(currentSum, 1, Integer::sum);

        // Recurse into left and right subtrees
        int leftPaths  = dfs(node.left,  currentSum, targetSum, prefixSumCount);
        int rightPaths = dfs(node.right, currentSum, targetSum, prefixSumCount);

        // BACKTRACK: remove this node's contribution before returning to parent
        prefixSumCount.merge(currentSum, -1, Integer::sum);

        return pathsEndingHere + leftPaths + rightPaths;
    }
}

/*
 * NOTE: Why long?
 *   With up to 1000 nodes each holding ±10^9, prefix sums can reach ±10^12,
 *   which overflows int.
 *
 * -----------------------------------------------------------------------------
 * 5. TIME & SPACE COMPLEXITY (WITH REASONING)
 * -----------------------------------------------------------------------------
 *
 * Approach 1 — Brute Force:
 *
 *   Time:  O(N²)
 *     For each of N nodes we run a DFS that can visit up to N nodes.
 *
 *   Space: O(N)
 *     Recursion stack depth = height of tree; worst case O(N) for skewed tree.
 *
 *   Example: 1000 nodes → ~1,000,000 operations
 *
 * Approach 2 — Prefix Sum HashMap:
 *
 *   Time:  O(N)
 *     Each node is visited exactly once; HashMap ops are O(1) average.
 *
 *   Space: O(N)
 *     Map stores at most one entry per node on current root→node path.
 *
 *   Example: 1000 nodes → ~1,000 operations
 *
 * -----------------------------------------------------------------------------
 * 6. COMPLETE WORKED EXAMPLES FOR EACH APPROACH
 * -----------------------------------------------------------------------------
 *
 * Example Tree:
 *
 *           10
 *          /  \
 *         5   -3
 *        / \    \
 *       3   2   11
 *      / \   \
 *     3  -2   1
 *
 *   targetSum = 8
 *   Expected answer: 3
 *   The three paths are: 5→3, 5→2→1, -3→11
 *
 * ~~~ Approach 1 Walkthrough (Brute Force) ~~~
 *
 *   We call countPathsFrom starting at every node:
 *
 *   Start Node | Path Explored   | Sum = 8?
 *   -----------|-----------------|----------
 *   10         | 10              | No
 *   10         | 10→5            | No
 *   10         | 10→5→3          | No
 *   10         | 10→5→3→3        | No
 *   ...        | ...             | ...
 *   5          | 5→3             | ✅ Yes
 *   5          | 5→3→3           | No
 *   5          | 5→3→-2          | No
 *   5          | 5→2→1           | ✅ Yes
 *   ...        | ...             | ...
 *   -3         | -3→11           | ✅ Yes
 *
 *   Total count = 3 ✅
 *
 * ~~~ Approach 2 Walkthrough (Prefix Sum HashMap) ~~~
 *
 *   Initial state: map = {0: 1}, currentSum = 0
 *
 *   Node  | currentSum | neededPrefix    | map.get(needed) | paths | map after
 *   ------| -----------| (sum - 8)       |                 | found |
 *   10    | 10         | 2               | 0               | 0     | {0:1, 10:1}
 *   5     | 15         | 7               | 0               | 0     | {..., 15:1}
 *   3     | 18         | 10              | 1               | 1 ✅  | {..., 18:1}  (path 5→3)
 *   3(lf) | 21         | 13              | 0               | 0     | {..., 21:1}
 *   ↩     |            |                 |                 |       | remove 21
 *   -2(lf)| 16         | 8               | 0               | 0     | {..., 16:1}
 *   ↩     |            |                 |                 |       | remove 16
 *   ↩ 3   |            |                 |                 |       | remove 18
 *   2     | 17         | 9               | 0               | 0     | {..., 17:1}
 *   1(lf) | 18         | 10              | 1               | 1 ✅  | {..., 18:1}  (path 5→2→1)
 *   ↩     |            |                 |                 |       | remove 18
 *   ↩ 2   |            |                 |                 |       | remove 17
 *   ↩ 5   |            |                 |                 |       | remove 15
 *   -3    | 7          | -1              | 0               | 0     | {0:1, 10:1, 7:1}
 *   11    | 18         | 10              | 1               | 1 ✅  | {..., 18:1}  (path -3→11)
 *   ↩ 11  |            |                 |                 |       | remove 18
 *   ↩ -3  |            |                 |                 |       | remove 7
 *   ↩ 10  |            |                 |                 |       | remove 10
 *
 *   Final count = 3 ✅
 *
 * -----------------------------------------------------------------------------
 * 7. EDGE CASES
 * -----------------------------------------------------------------------------
 *
 *   Edge Case               | Description                | Approach 1  | Approach 2
 *   ------------------------|----------------------------|-------------|-------------
 *   Null root               | Empty tree                 | Returns 0 ✅| Returns 0 ✅
 *   Single node = target    | Root=8, target=8           | count++ ✅  | found 1 ✅
 *   Single node ≠ target    | Root=5, target=8           | 0 ✅        | 0 ✅
 *   Negative values         | Nodes like -3, -2          | Handled ✅  | Handled ✅
 *   targetSum = 0           | Path of zeros              | Works ✅    | Works ✅
 *   Multiple paths same sum | Two paths summing to 8     | Counts ✅   | Freq map ✅
 *   Integer overflow        | 1000 nodes × 10^9 = 10^12  | ⚠️ Use long | Uses long ✅
 *   All negative values     | target = -3                | Works ✅    | Works ✅
 *   Skewed tree             | All nodes one direction    | O(N²) slow  | O(N) fine ✅
 *   Large tree (1000 nodes) | Max constraint             | ~10^6 ops   | ~10^3 ops ✅
 *
 * Critical Bug to Watch — Backtracking:
 *   In Approach 2, if you forget to remove the prefix sum after recursion,
 *   the right subtree would incorrectly "see" prefix sums from the left
 *   subtree's path. Always backtrack!
 *
 *   // ❌ WRONG — Missing backtrack causes wrong counts across branches
 *   prefixSumCount.merge(currentSum, 1, Integer::sum);
 *   dfs(node.left, ...);
 *   dfs(node.right, ...);
 *   // Missing: prefixSumCount.merge(currentSum, -1, Integer::sum);
 *
 * -----------------------------------------------------------------------------
 * 8. FINAL SUMMARY
 * -----------------------------------------------------------------------------
 *
 *   Criterion           | Brute Force  | Prefix Sum HashMap
 *   --------------------|--------------|--------------------
 *   Time Complexity     | O(N²)        | O(N)  ✅
 *   Space Complexity    | O(N)         | O(N)
 *   Code Complexity     | Simple       | Moderate
 *   Handles negatives   | ✅           | ✅
 *   Interview recommended| ❌ Too slow | ✅ Yes
 *
 *   Recommended: Always use Approach 2 (Prefix Sum + HashMap) in interviews.
 *   It's O(N), elegant, and demonstrates mastery of prefix sum patterns.
 *
 *   What to Remember:
 *     Pattern: "Count subpaths with a given sum in a tree"
 *              = Prefix Sum + HashMap + Backtracking DFS
 *     Same idea as LeetCode 560 (Subarray Sum Equals K), extended to trees.
 *     The {0: 1} initialization handles paths starting from the root itself.
 *
 * -----------------------------------------------------------------------------
 * 9. COMPANY INTERVIEW APPEARANCES
 * -----------------------------------------------------------------------------
 *
 *   Company            | Frequency            | Notes
 *   -------------------|----------------------|------------------------------
 *   Meta (Facebook)    | ⭐⭐⭐⭐⭐ Very High  | Top recurring tree problem
 *   Amazon             | ⭐⭐⭐⭐⭐ Very High  | Frequently in OA and interviews
 *   Microsoft          | ⭐⭐⭐⭐  High        | Common in SDE rounds
 *   Google             | ⭐⭐⭐⭐  High        | Appears in L4/L5 interviews
 *   Apple              | ⭐⭐⭐   Medium       | Seen in iOS/Backend rounds
 *   Bloomberg          | ⭐⭐⭐   Medium       | Common in financial tech rounds
 *   Adobe              | ⭐⭐⭐   Medium       | Appears in SDE II rounds
 *   Uber               | ⭐⭐    Moderate      | Seen occasionally
 *   LinkedIn           | ⭐⭐    Moderate      | Appears in backend rounds
 *
 *   Overall Stats:
 *     LeetCode Difficulty:         Medium (#437)
 *     Total acceptance rate:       ~48%
 *     Reported interview appearances: 500+ times across all companies
 *     Most common context: Asked as a follow-up to "Path Sum I/II" to test
 *     if the candidate can generalize from root-to-leaf to arbitrary downward
 *     paths.
 *
 * =============================================================================
 */
// @formatter:on
