package Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CombinationSumII {
    public static void main(String[] args) {
        CombinationSumII combinationSum = new CombinationSumII();
        System.out
                .println("CombinationSumII : "
                        + combinationSum.combinationSumBackTrackingSortSkip(new int[] { 10, 1, 2, 7, 6, 1, 5 }, 8));
        System.out
                .println("CombinationSumII : "
                        + combinationSum.combinationSumBruteForce(new int[] { 10, 1, 2, 7, 6, 1, 5 }, 8));
    }

    // @formatter:off
    /**
     * 
     * https://leetcode.com/problems/combination-sum-ii/description/?envType=problem-list-v2&envId=array
     * 
     * Given a collection of candidate numbers (candidates) and a target number
     * (target), find all unique combinations in candidates where the candidate
     * numbers sum to target.
     * 
     * Each number in candidates may only be used once in the combination.
     * 
     * Note: The solution set must not contain duplicate combinations.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: candidates = [10,1,2,7,6,1,5], target = 8
     * Output:
     * [[1,1,6],[1,2,5],[1,7],[2,6]]
     * Example 2:
     * 
     * Input: candidates = [2,5,2,1,2], target = 5
     * Output:
     * [[1,2,2],[5]]
     * 
     * 
     * Constraints:
     * 
     * 1 <= candidates.length <= 100
     * 1 <= candidates[i] <= 50
     * 1 <= target <= 30
     * 
     */
    // @formatter:on

    public List<List<Integer>> combinationSumBackTrackingSortSkip(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        backTrackSortSkip(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    public void backTrackSortSkip(int[] candidates, int remaining, int index, List<Integer> current,
            List<List<Integer>> result) {
        if (remaining == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = index; i < candidates.length; i++) {
            if (i > index && candidates[i] == candidates[i - 1])
                continue;
            if (candidates[i] > remaining)
                break;
            current.add(candidates[i]);
            backTrackSortSkip(candidates, remaining - candidates[i], i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    public List<List<Integer>> combinationSumBruteForce(int[] candidates, int target) {
        Set<List<Integer>> result = new HashSet<>();
        int n = candidates.length;
        int totalSubsets = 1 << n;
        for (int mask = 0; mask < totalSubsets; mask++) {
            List<Integer> current = new ArrayList<>();
            int sum = 0;
            for (int j = 0; j < n; j++) {
                if ((mask & (1 << j)) != 0) {
                    current.add(candidates[j]);
                    sum += candidates[j];
                }
            }
            if (sum == target) {
                Collections.sort(current);
                result.add(current);
            }
        }
        return new ArrayList<>(result);
    }

}
// @formatter:off
/*
 * ============================================================
 * Combination Sum II — Deep Dive Explanation
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given a collection of candidate numbers (which may contain duplicates) and a
 * target value, find all unique combinations where the chosen numbers sum to the
 * target. Each number in the input may only be used ONCE in a combination.
 *
 * LeetCode #40 — Medium
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] candidates — array of positive integers (may have duplicates)
 * int target       — the sum we want to reach
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * List<List<Integer>> — all unique combinations that sum to target
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * 1 <= candidates.length <= 100
 * 1 <= candidates[i]    <= 50
 * 1 <= target           <= 30
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Pick subsets (each element used at most once) from candidates whose sum
 * equals target. No two returned combinations may be identical even though
 * the input array has duplicates.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 * Input:  candidates = [10, 1, 2, 7, 6, 1, 5], target = 8
 * Output: [[1,1,6], [1,2,5], [1,7], [2,6]]
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Think of it as picking items from a bag of numbered tokens (some duplicates
 * exist). You want to reach an exact weight. You can use each physical token
 * at most once, but you shouldn't report the same combination of values more
 * than once.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Sort the candidates — grouping duplicates together makes it easy to skip them.
 * 2. Try including each candidate and recurse for the remaining sum.
 * 3. After trying a number, skip all subsequent identical numbers at the same
 *    recursion depth — that's the key duplicate-pruning rule.
 * 4. If remaining sum hits 0, record the current combination.
 * 5. If remaining sum goes negative, stop exploring that branch.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                       | Why it's tricky                                          |
 * |---------------------------------|----------------------------------------------------------|
 * | Duplicate inputs                | [1,1,2] with target 3 should give [[1,2]] once, not twice |
 * | Each element used at most once  | Unlike LC #39, you can't reuse the same index             |
 * | Pruning duplicates at same level| You must skip duplicates only among siblings, not ancestors|
 * | Knowing when to skip            | if (i > start && candidates[i] == candidates[i-1]) — the  |
 * |                                 | condition i > start is subtle                            |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                          | Key Idea                              | Best Used When          | Time Complexity     |
 * |---|-----------------------------------|---------------------------------------|-------------------------|---------------------|
 * | 1 | Brute Force (all subsets)         | Generate all 2^n subsets, filter, dedup | Never (learning only) | O(2^n · n)          |
 * | 2 | Backtracking with sort + skip ✅  | Sort, recurse forward, skip siblings  | Always — standard       | O(2^n) worst case   |
 *
 * The backtracking approach is optimal because it prunes entire branches early
 * (when remaining < 0) and skips duplicate sibling branches in O(1) after sorting,
 * avoiding redundant work entirely. The brute-force approach does all the work
 * upfront and deduplicates at the end, wasting time on paths it shouldn't visit.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (All Subsets)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Generate all 2^n subsets of the candidates array.
 * 2. For each subset, compute its sum.
 * 3. If sum equals target, convert to a sorted list and add to a Set<List<Integer>>.
 * 4. Return the set as a list.
 *
 *    import java.util.*;
 *
 *    public class CombinationSum2BruteForce {
 *
 *        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
 *            Set<List<Integer>> resultSet = new HashSet<>();
 *            int n = candidates.length;
 *
 *            for (int mask = 0; mask < (1 << n); mask++) {
 *                List<Integer> subset = new ArrayList<>();
 *                int sum = 0;
 *                for (int i = 0; i < n; i++) {
 *                    if ((mask & (1 << i)) != 0) {
 *                        subset.add(candidates[i]);
 *                        sum += candidates[i];
 *                    }
 *                }
 *                if (sum == target) {
 *                    Collections.sort(subset);
 *                    resultSet.add(subset);
 *                }
 *            }
 *            return new ArrayList<>(resultSet);
 *        }
 *
 *        public static void main(String[] args) {
 *            CombinationSum2BruteForce sol = new CombinationSum2BruteForce();
 *            System.out.println(sol.combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8));
 *            // Expected: [[1,1,6],[1,2,5],[1,7],[2,6]]
 *        }
 *    }
 *
 * Note: The bitmask loop runs 2^n iterations. For n=20 that's ~1 million subsets;
 * for n=100 it's completely infeasible.
 *
 * ------------------------------------------------------------
 * Approach 2: Backtracking with Sort + Skip ✅
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Sort candidates so duplicates are adjacent.
 * 2. Call backtrack(candidates, target, startIndex, currentPath, result).
 * 3. At each call: if remaining == 0, add a copy of currentPath to results.
 * 4. Loop i from startIndex to end:
 *    - Skip duplicate siblings: if (i > startIndex && candidates[i] == candidates[i-1]) continue;
 *    - Early termination: if (candidates[i] > remaining) break;
 *    - Add candidates[i], recurse with startIndex = i + 1.
 *    - Remove candidates[i] (backtrack).
 *
 *    import java.util.*;
 *
 *    public class CombinationSum2Backtrack {
 *
 *        public List<List<Integer>> combinationSum2(int[] candidates, int target) {
 *            List<List<Integer>> result = new ArrayList<>();
 *            Arrays.sort(candidates);
 *            backtrack(candidates, target, 0, new ArrayList<>(), result);
 *            return result;
 *        }
 *
 *        private void backtrack(int[] candidates, int remaining,
 *                               int startIndex, List<Integer> currentPath,
 *                               List<List<Integer>> result) {
 *            if (remaining == 0) {
 *                result.add(new ArrayList<>(currentPath));
 *                return;
 *            }
 *
 *            for (int i = startIndex; i < candidates.length; i++) {
 *                // Skip duplicate values at the same recursion depth
 *                if (i > startIndex && candidates[i] == candidates[i - 1]) continue;
 *
 *                // No point going further — array is sorted
 *                if (candidates[i] > remaining) break;
 *
 *                currentPath.add(candidates[i]);
 *                backtrack(candidates, remaining - candidates[i], i + 1, currentPath, result);
 *                currentPath.remove(currentPath.size() - 1);
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            CombinationSum2Backtrack sol = new CombinationSum2Backtrack();
 *            System.out.println(sol.combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8));
 *            // [[1,1,6],[1,2,5],[1,7],[2,6]]
 *            System.out.println(sol.combinationSum2(new int[]{2, 5, 2, 1, 2}, 5));
 *            // [[1,2,2],[5]]
 *        }
 *    }
 *
 * Key line explained:
 *   if (i > startIndex && candidates[i] == candidates[i - 1]) continue;
 *   - i > startIndex: only skip if same value was already tried at THIS depth.
 *   - Without i > startIndex guard, we'd skip values chosen by parent calls,
 *     eliminating valid combinations like [1,1,6].
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Time:  O(2^n · n log n)
 *   - 2^n subsets, each taking O(n log n) to sort.
 *   - For n=7: 128 subsets, trivial. For n=100: 10^30 — infeasible.
 * Space: O(2^n · n) — storing all subsets in HashSet at worst.
 *
 * ------------------------------------------------------------
 * Approach 2: Backtracking ✅
 * ------------------------------------------------------------
 * Time:  O(2^n) worst case
 *   - In practice far better due to early break and sibling-skip.
 *   - Bounded by C(n,k) summed over all k — at most 2^n.
 * Space: O(n) recursion depth + O(n) for current path.
 *   - Depth at most n (each level picks one element, advances index).
 *
 * Concrete estimate:
 *   n=7, target=8 (example): ~12 recursive calls. Extremely fast.
 *   n=30, all ones, target=15: C(30,15) ≈ 155 million — worst case near limit.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 2 — Input: [10,1,2,7,6,1,5], target=8
 * After sorting: [1, 1, 2, 5, 6, 7, 10]
 * ------------------------------------------------------------
 *
 * backtrack(remaining=8, start=0, path=[])
 * ├─ i=0: pick 1 → path=[1]
 * │   backtrack(remaining=7, start=1, path=[1])
 * │   ├─ i=1: pick 1 → path=[1,1]
 * │   │   backtrack(remaining=6, start=2, path=[1,1])
 * │   │   ├─ i=2: pick 2 → path=[1,1,2]
 * │   │   │   backtrack(remaining=4, start=3): 5>4 → BREAK
 * │   │   ├─ i=3: pick 5 → path=[1,1,5]
 * │   │   │   backtrack(remaining=1, start=4): 6>1 → BREAK
 * │   │   ├─ i=4: pick 6 → path=[1,1,6] ✅ remaining=0, ADD [1,1,6]
 * │   │   ├─ i=5: 7>6 → BREAK
 * │   ├─ i=2: pick 2 → path=[1,2]
 * │   │   backtrack(remaining=5, start=3)
 * │   │   ├─ i=3: pick 5 → path=[1,2,5] ✅ ADD [1,2,5]
 * │   │   ├─ i=4: 6>5 → BREAK
 * │   ├─ i=3: pick 5 → path=[1,5]
 * │   │   backtrack(remaining=2, start=4): 6>2 → BREAK
 * │   ├─ i=4: pick 6 → path=[1,6]
 * │   │   backtrack(remaining=1, start=5): 7>1 → BREAK
 * │   ├─ i=5: pick 7 → path=[1,7] ✅ ADD [1,7]
 * │   ├─ i=6: 10>7 → BREAK
 * ├─ i=1: candidates[1]==candidates[0] AND i>start(0) → SKIP
 * ├─ i=2: pick 2 → path=[2]
 * │   backtrack(remaining=6, start=3)
 * │   ├─ i=3: pick 5 → path=[2,5]: 6>1 → BREAK
 * │   ├─ i=4: pick 6 → path=[2,6] ✅ ADD [2,6]
 * │   ├─ i=5: 7>6 → BREAK
 * ├─ i=3: pick 5 → path=[5]: 6>3 → BREAK
 * ├─ i=4: pick 6 → path=[6]: 7>2 → BREAK
 * ├─ i=5: pick 7 → path=[7]: 10>1 → BREAK
 * ├─ i=6: 10>8 → BREAK
 *
 * Final result: [[1,1,6],[1,2,5],[1,7],[2,6]] ✅
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                      | Input                    | Expected Output | How Handled                                  |
 * |-------------------------------|--------------------------|-----------------|----------------------------------------------|
 * | No valid combination          | [2,4], target=3          | []              | No path hits remaining=0                     |
 * | Single element equals target  | [5], target=5            | [[5]]           | Picked at i=0, remaining becomes 0           |
 * | Single element less than target| [3], target=5           | []              | 3 < 5, no more elements                      |
 * | All elements identical        | [2,2,2,2], target=4      | [[2,2]]         | Only first two 2s picked; siblings skipped   |
 * | Target equals largest element | [1,2,3,4,5], target=5    | [[1,4],[2,3],[5]]| Normal backtracking                          |
 * | All elements larger than target| [6,7,8], target=5       | []              | candidates[i] > remaining → break on first  |
 * | Many duplicates               | [1,1,1,1,1], target=3    | [[1,1,1]]       | Duplicate-skip ensures only one [1,1,1]      |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 *
 * Pitfall 1: Wrong skip condition (forgetting i > startIndex)
 *   WRONG:   if (candidates[i] == candidates[i - 1]) continue;
 *   CORRECT: if (i > startIndex && candidates[i] == candidates[i - 1]) continue;
 *
 * Pitfall 2: Passing i instead of i + 1 (reusing the same element)
 *   WRONG:   backtrack(candidates, remaining - candidates[i], i, currentPath, result);
 *   CORRECT: backtrack(candidates, remaining - candidates[i], i + 1, currentPath, result);
 *
 * Pitfall 3: Forgetting to sort
 *   WRONG:   int[] unsorted = candidates;  // no sort
 *   CORRECT: Arrays.sort(candidates);
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The most subtle is when startIndex is 0 and the first two elements are equal.
 *    The condition i > startIndex is 0 > 0 = false, so we do NOT skip the second
 *    element. This is correct! Tests with [1,1,2] and target 3 should verify
 *    [[1,2]] appears exactly once.
 *
 * Q: Are there any type mismatches?
 * A: No. candidates is int[], target is int, remaining is int. currentPath is
 *    List<Integer> (autoboxed). result is List<List<Integer>>. All match LeetCode signature.
 *
 * Q: How can I verify this works right now?
 *
 *    public static void verify() {
 *        CombinationSum2Backtrack sol = new CombinationSum2Backtrack();
 *
 *        List<List<Integer>> r1 = sol.combinationSum2(new int[]{10,1,2,7,6,1,5}, 8);
 *        List<List<Integer>> e1 = Arrays.asList(
 *            Arrays.asList(1,1,6), Arrays.asList(1,2,5),
 *            Arrays.asList(1,7),   Arrays.asList(2,6));
 *        assert r1.size() == 4 && r1.containsAll(e1) : "Test 1 failed: " + r1;
 *
 *        List<List<Integer>> r2 = sol.combinationSum2(new int[]{2,5,2,1,2}, 5);
 *        List<List<Integer>> e2 = Arrays.asList(Arrays.asList(1,2,2), Arrays.asList(5));
 *        assert r2.size() == 2 && r2.containsAll(e2) : "Test 2 failed: " + r2;
 *
 *        List<List<Integer>> r3 = sol.combinationSum2(new int[]{2,4}, 3);
 *        assert r3.isEmpty() : "Test 3 failed: " + r3;
 *
 *        List<List<Integer>> r4 = sol.combinationSum2(new int[]{1,1,1,1,1}, 3);
 *        assert r4.size() == 1 && r4.get(0).equals(Arrays.asList(1,1,1))
 *               : "Test 4 failed: " + r4;
 *
 *        System.out.println("All assertions passed ✅");
 *    }
 *
 * | Approach      | Risk                             | Mitigation                              |
 * |---------------|----------------------------------|-----------------------------------------|
 * | Brute Force   | Exponential blowup for n > 20    | Only for validation, never production   |
 * | Backtracking  | Incorrect duplicate skip cond.   | Always pair i > startIndex with check  |
 * | Backtracking  | Reuse of same element            | Always pass i + 1 to recursive call     |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * | Company        | Frequency | Notes                        |
 * |----------------|-----------|------------------------------|
 * | Amazon         | ⭐⭐⭐⭐⭐ | Very common in phone screens  |
 * | Google         | ⭐⭐⭐⭐  | Appears in backtracking rounds|
 * | Microsoft      | ⭐⭐⭐⭐  | Asked in onsite loops         |
 * | Facebook/Meta  | ⭐⭐⭐⭐  | Paired with Combination Sum I |
 * | Apple          | ⭐⭐⭐   | Appears in coding rounds      |
 * | Bloomberg      | ⭐⭐⭐   | Mid-level interviews          |
 * | Adobe          | ⭐⭐⭐   | Backtracking category         |
 * | LinkedIn       | ⭐⭐    | Occasionally seen             |
 * | Uber           | ⭐⭐    | Part of recursion sets        |
 * | TCS / Infosys  | ⭐⭐    | Product team interviews       |
 *
 * LeetCode #40 — Difficulty: Medium
 * Approximate total interview appearances: 2,000+ (top 15% most-asked problems)
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach                    | Time       | Space | Code Complexity | Recommended? |
 * |-----------------------------|------------|-------|-----------------|--------------|
 * | Brute Force                 | O(2^n · n) | O(2^n·n) | Low          | ❌ Not for production |
 * | Backtracking + Sort + Skip  | O(2^n)     | O(n)  | Medium          | ✅✅ Always use this  |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Backtracking with sorting and sibling-duplicate skip — sort once, recurse
 * forward with i + 1, and skip duplicate siblings with:
 *   if (i > startIndex && candidates[i] == candidates[i-1]) continue;
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * 1. Sort first — it enables both early-exit pruning (candidates[i] > remaining → break)
 *    and duplicate skipping.
 * 2. The duplicate skip rule is i > startIndex, NOT i > 0 — this is the single
 *    most common mistake on this problem.
 * 3. The pattern — backtrack over a sorted array, skip same-value siblings — also
 *    appears in Subsets II (#90), Permutations II (#47), and Palindrome Partitioning
 *    (#131), so mastering it pays dividends across many problems.
 */
// @formatter:on
