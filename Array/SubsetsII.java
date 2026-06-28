package Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubsetsII {
    public static void main(String[] args) {
        SubsetsII subsetsII = new SubsetsII();
        System.out.println("SubsetsII : " + subsetsII.subsetsWithDupBackTrackSortPrune(new int[] { 1, 2, 2 }));
        System.out.println("SubsetsII : " + subsetsII.subsetsWithDupBackTrackSortPrune(new int[] { 0 }));
        System.out.println("SubsetsII : " + subsetsII.subsetsWithDupBruteForce(new int[] { 1, 2, 2 }));
        System.out.println("SubsetsII : " + subsetsII.subsetsWithDupBruteForce(new int[] { 0 }));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/subsets-ii/description/
     * 
     * Given an integer array nums that may contain duplicates, return all possible
     * subsets (the power set).
     * 
     * The solution set must not contain duplicate subsets. Return the solution in
     * any order.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,2,2]
     * Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]
     * Example 2:
     * 
     * Input: nums = [0]
     * Output: [[],[0]]
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 10
     * -10 <= nums[i] <= 10
     */
    // @formatter:on

    public List<List<Integer>> subsetsWithDupBackTrackSortPrune(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        backTrackSortPrune(nums, 0, new ArrayList<>(), result);
        return result;
    }

    public void backTrackSortPrune(int[] nums, int startIndex, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current));
        for (int i = startIndex; i < nums.length; i++) {
            if (i > startIndex && nums[i] == nums[i - 1])
                continue;
            current.add(nums[i]);
            backTrackSortPrune(nums, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    public List<List<Integer>> subsetsWithDupBruteForce(int[] nums) {
        Set<List<Integer>> result = new HashSet<>();
        int n = nums.length;
        int totalSubsets = 1 << n;
        for (int mask = 0; mask < totalSubsets; mask++) {
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(nums[i]);
                }
            }
            Collections.sort(subset);
            result.add(subset);
        }
        return new ArrayList<>(result);
    }
}

// @formatter:off
/*
 * ============================================================
 * Subsets II — Deep Dive Explanation
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an integer array that MAY CONTAIN DUPLICATES, generate all possible
 * subsets (the power set). The result must not contain duplicate subsets.
 * (LeetCode #90)
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums — an integer array of length 1 to 10, values between -10 and 10,
 * may contain duplicates.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * List<List<Integer>> — all unique subsets including the empty subset.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * 1 <= nums.length <= 10
 * -10 <= nums[i] <= 10
 * The answer must not contain duplicate subsets (in any order).
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Every possible combination of elements (chosen 0 to n times), but duplicates
 * in the input must not lead to duplicate subsets in the output.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 * Input:  nums = [1, 2, 2]
 * Output: [[], [1], [1,2], [1,2,2], [2], [2,2]]
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Think of it like choosing toppings for a pizza when some toppings appear
 * multiple times in the box. You want every unique combination of toppings —
 * not the same combination listed twice just because you grabbed duplicates
 * from different spots in the box.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Sort the array so duplicates are ADJACENT — this groups them for easy skipping.
 * 2. Use backtracking to try including or excluding each element.
 * 3. When at a certain index, if the current element equals the previous one
 *    AND we didn't include the previous one (i.e., we're skipping it at this
 *    recursion level), skip this one too — otherwise we'd generate the same subset.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge              | Why it's tricky                                        |
 * |------------------------|--------------------------------------------------------|
 * | Duplicate elements     | Same value at different positions → identical subsets   |
 * | Skip condition logic   | Must skip only when previous was skipped at same level  |
 * | Sorting requirement    | Without sorting, duplicates aren't adjacent → can't prune|
 * | Empty subset           | Must always be included — easy to forget               |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                    | Key Idea                             | Best Used When      | Time Complexity |
 * |---|-----------------------------|--------------------------------------|---------------------|-----------------|
 * | 1 | Brute Force + Set           | Generate all subsets, dedup via Set  | Never (conceptual)  | O(n · 2ⁿ)       |
 * | 2 | Backtracking + Sort + Skip ✅| Sort, skip dups at same level        | Always — optimal    | O(n · 2ⁿ)       |
 *
 * The backtracking approach is best because it avoids generating duplicate
 * subsets in the first place, rather than creating them and then deduplicating.
 * This is cleaner, uses less memory, and is the canonical interview answer.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force + Set
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Generate every possible subset using bitmask (2ⁿ subsets).
 * 2. For each subset, sort it and convert to a list.
 * 3. Add to a HashSet to deduplicate.
 * 4. Convert the set back to a list.
 *
 * ```java
 * import java.util.*;
 *
 * public class SubsetsIIBruteForce {
 *     public List<List<Integer>> subsetsWithDup(int[] nums) {
 *         int n = nums.length;
 *         Set<List<Integer>> resultSet = new HashSet<>();
 *
 *         for (int mask = 0; mask < (1 << n); mask++) {
 *             List<Integer> subset = new ArrayList<>();
 *             for (int i = 0; i < n; i++) {
 *                 if ((mask & (1 << i)) != 0) {
 *                     subset.add(nums[i]);
 *                 }
 *             }
 *             Collections.sort(subset); // sort so [2,1] and [1,2] are seen as equal
 *             resultSet.add(subset);
 *         }
 *
 *         return new ArrayList<>(resultSet);
 *     }
 *
 *     public static void main(String[] args) {
 *         SubsetsIIBruteForce sol = new SubsetsIIBruteForce();
 *         System.out.println(sol.subsetsWithDup(new int[]{1, 2, 2}));
 *         // Output: [[], [1], [2], [1,2], [2,2], [1,2,2]]
 *     }
 * }
 * ```
 *
 * This works but wastes memory building duplicate subsets only to discard them.
 *
 * ------------------------------------------------------------
 * Approach 2: Backtracking with Sort + Skip ✅
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Sort nums so duplicates are adjacent.
 * 2. Define a recursive backtracking function backtrack(startIndex, currentSubset).
 * 3. At each call, add the current subset to results.
 * 4. Loop i from startIndex to n-1:
 *    - If i > startIndex and nums[i] == nums[i-1], SKIP (prevents dup subsets at same depth).
 *    - Otherwise, add nums[i], recurse with i+1, then remove nums[i] (backtrack).
 *
 * ```java
 * import java.util.*;
 *
 * public class SubsetsII {
 *     public List<List<Integer>> subsetsWithDup(int[] nums) {
 *         List<List<Integer>> result = new ArrayList<>();
 *         Arrays.sort(nums); // critical: group duplicates
 *         backtrack(nums, 0, new ArrayList<>(), result);
 *         return result;
 *     }
 *
 *     private void backtrack(int[] nums, int startIndex,
 *                            List<Integer> currentSubset,
 *                            List<List<Integer>> result) {
 *         result.add(new ArrayList<>(currentSubset)); // snapshot current state
 *
 *         for (int i = startIndex; i < nums.length; i++) {
 *             // Skip duplicate at the same recursion level
 *             if (i > startIndex && nums[i] == nums[i - 1]) {
 *                 continue;
 *             }
 *             currentSubset.add(nums[i]);
 *             backtrack(nums, i + 1, currentSubset, result);
 *             currentSubset.remove(currentSubset.size() - 1); // backtrack
 *         }
 *     }
 *
 *     public static void main(String[] args) {
 *         SubsetsII sol = new SubsetsII();
 *         System.out.println(sol.subsetsWithDup(new int[]{1, 2, 2}));
 *         // Output: [[], [1], [1,2], [1,2,2], [2], [2,2]]
 *         System.out.println(sol.subsetsWithDup(new int[]{0}));
 *         // Output: [[], [0]]
 *     }
 * }
 * ```
 *
 * The key line is: if (i > startIndex && nums[i] == nums[i - 1])
 * The condition i > startIndex ensures we only skip duplicates AT THE SAME DEPTH,
 * not across different depths — this is what prevents over-skipping.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force + Set
 * ------------------------------------------------------------
 * Time:  O(n · 2ⁿ) — 2ⁿ masks, each takes O(n) to build and sort.
 *        For n=10: 10 × 1024 = 10,240 operations.
 * Space: O(n · 2ⁿ) — HashSet stores up to 2ⁿ subsets each of size up to n.
 *
 * ------------------------------------------------------------
 * Approach 2: Backtracking + Sort + Skip ✅
 * ------------------------------------------------------------
 * Time:  O(n · 2ⁿ) — worst case (no duplicates) visits 2ⁿ subsets, each copy O(n).
 *        For n=10: ~10 × 1024 = 10,240 ops. With duplicates, fewer subsets generated.
 * Space: O(n) — recursion stack depth ≤ n; currentSubset holds ≤ n elements at a time.
 *        (Output list not counted in auxiliary space.)
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 2: Backtracking — nums = [1, 2, 2] (after sort: [1, 2, 2])
 * ------------------------------------------------------------
 *
 * backtrack(start=0, current=[])
 *   → add [] to result
 *   i=0: add 1 → current=[1]
 *     backtrack(start=1, current=[1])
 *       → add [1] to result
 *       i=1: add 2 → current=[1,2]
 *         backtrack(start=2, current=[1,2])
 *           → add [1,2] to result
 *           i=2: i=2 > startIndex=2? No → NOT skipped
 *                add 2 → current=[1,2,2]
 *             backtrack(start=3, current=[1,2,2])
 *               → add [1,2,2] to result
 *               (loop ends, no more elements)
 *             backtrack: remove 2 → current=[1,2]
 *         backtrack: remove 2 → current=[1]
 *       i=2: nums[2]=2 == nums[1]=2, i=2 > startIndex=1 → SKIP ✋
 *     backtrack: remove 1 → current=[]
 *   i=1: add 2 → current=[2]
 *     backtrack(start=2, current=[2])
 *       → add [2] to result
 *       i=2: i=2 > startIndex=2? No → NOT skipped
 *            add 2 → current=[2,2]
 *         backtrack(start=3, current=[2,2])
 *           → add [2,2] to result
 *         backtrack: remove 2 → current=[2]
 *     backtrack: remove 2 → current=[]
 *   i=2: nums[2]=2 == nums[1]=2, i=2 > startIndex=0 → SKIP ✋
 *
 * Final result: [[], [1], [1,2], [1,2,2], [2], [2,2]]
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case            | Input       | Expected Output                    | How Handled                          |
 * |----------------------|-------------|------------------------------------|--------------------------------------|
 * | Single element       | [0]         | [[], [0]]                          | Backtrack adds [] then [0]           |
 * | All duplicates       | [2,2,2]     | [[], [2], [2,2], [2,2,2]]          | Sort + skip handles all levels       |
 * | No duplicates        | [1,2,3]     | All 8 subsets                      | Works like Subsets I (LC #78)        |
 * | Negative values      | [-1,0,1]    | [[], [-1], [-1,0], ... , [1]]      | Sorting works on negatives too       |
 * | Single duplicated pair| [1,1]      | [[], [1], [1,1]]                   | Skip at i=1 when startIndex=0        |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * WRONG skip condition — skips too aggressively:
 *   if (nums[i] == nums[i - 1]) continue;   // misses valid subsets!
 *
 * CORRECT skip condition:
 *   if (i > startIndex && nums[i] == nums[i - 1]) continue;
 *
 * WRONG — adds reference, not copy:
 *   result.add(currentSubset);   // all entries end up empty!
 *
 * CORRECT — snapshot:
 *   result.add(new ArrayList<>(currentSubset));
 *
 * Always sort nums before backtracking — without it, the skip logic breaks entirely.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The i > startIndex boundary condition is the most commonly missed. Also,
 *    forgetting to snapshot currentSubset when adding to results.
 *
 * Q: Are there any type mismatches?
 * A: No. nums is int[], internal list is List<Integer> (autoboxing handles conversion).
 *    Return type List<List<Integer>> is consistent.
 *
 * Q: How can I verify this works right now?
 *
 * ```java
 * public static void verify() {
 *     SubsetsII sol = new SubsetsII();
 *
 *     List<List<Integer>> r1 = sol.subsetsWithDup(new int[]{1, 2, 2});
 *     assert r1.size() == 6 : "Expected 6 subsets for [1,2,2]";
 *
 *     List<List<Integer>> r2 = sol.subsetsWithDup(new int[]{0});
 *     assert r2.size() == 2 : "Expected 2 subsets for [0]";
 *
 *     List<List<Integer>> r3 = sol.subsetsWithDup(new int[]{2, 2, 2});
 *     assert r3.size() == 4 : "Expected 4 subsets for [2,2,2]";
 *
 *     List<List<Integer>> r4 = sol.subsetsWithDup(new int[]{1, 2, 3});
 *     assert r4.size() == 8 : "Expected 8 subsets for [1,2,3] (no dups)";
 *
 *     System.out.println("All assertions passed!");
 * }
 * ```
 *
 * | Approach        | Risk                                    | Mitigation                              |
 * |-----------------|-----------------------------------------|-----------------------------------------|
 * | Brute Force     | Memory blowup from duplicate subsets    | Avoid in production                     |
 * | Backtracking    | Wrong skip condition causes over-pruning| Always use i > startIndex guard         |
 * | Backtracking    | Mutating shared list without copying    | Use new ArrayList<>(currentSubset)      |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * | Company        | Frequency | Notes                                  |
 * |----------------|-----------|----------------------------------------|
 * | Amazon         | ⭐⭐⭐⭐    | Common in phone screens                |
 * | Google         | ⭐⭐⭐⭐    | Part of backtracking set               |
 * | Microsoft      | ⭐⭐⭐     | Paired with Subsets I                  |
 * | Facebook/Meta  | ⭐⭐⭐     | Asked with permutations                |
 * | Apple          | ⭐⭐       | Less frequent                          |
 * | Bloomberg      | ⭐⭐⭐     | Classic backtracking ask               |
 * | Uber           | ⭐⭐       | Seen in onsite rounds                  |
 * | Adobe          | ⭐⭐       | Occasionally asked                     |
 * | Salesforce     | ⭐⭐       | Medium difficulty rounds               |
 * | LinkedIn       | ⭐⭐⭐     | Backtracking category problems         |
 *
 * LeetCode #90 · Medium · ~2,000+ recorded appearances in interview reports
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach              | Time      | Space     | Code Complexity | Recommended?       |
 * |-----------------------|-----------|-----------|-----------------|--------------------|
 * | Brute Force + Set     | O(n·2ⁿ)  | O(n·2ⁿ)  | Low             | ❌ Not recommended |
 * | Backtracking + Skip   | O(n·2ⁿ)  | O(n)      | Medium          | ✅✅ Best choice   |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Backtracking with sort + skip — sort first, then use the
 * i > startIndex && nums[i] == nums[i-1] guard to prune duplicates
 * at the same recursion level.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * 1. SORT FIRST — without sorting, adjacent duplicate detection is impossible.
 * 2. The skip condition i > startIndex is the heart of this problem — it skips
 *    duplicates only within the same level of recursion, not across levels.
 * 3. This exact skip pattern appears in Combination Sum II and Permutations II —
 *    mastering it here unlocks all three problems.
 */
// @formatter:on
