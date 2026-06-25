package Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum {
    public static void main(String[] args) {
        CombinationSum combinationSum = new CombinationSum();
        System.out
                .println("CombinationSum : " + combinationSum.combinationSumBackTracking(new int[] { 2, 3, 6, 7 }, 7));
        System.out
                .println("CombinationSum : " + combinationSum.combinationSumBruteForce(new int[] { 2, 3, 6, 7 }, 7));
        System.out
                .println("CombinationSum : "
                        + combinationSum.combinationSumBackTrackingSortingPrune(new int[] { 2, 3, 6, 7 }, 7));
    }

    // @formatter:off
    /**
     * 
     * https://leetcode.com/problems/combination-sum/description/?envType=problem-list-v2&envId=array
     * 
     * Given an array of distinct integers candidates and a target integer target,
     * return a list of all unique combinations of candidates where the chosen
     * numbers sum to target. You may return the combinations in any order.
     * 
     * The same number may be chosen from candidates an unlimited number of times.
     * Two combinations are unique if the frequency of at least one of the chosen
     * numbers is different.
     * 
     * The test cases are generated such that the number of unique combinations that
     * sum up to target is less than 150 combinations for the given input.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: candidates = [2,3,6,7], target = 7
     * Output: [[2,2,3],[7]]
     * Explanation:
     * 2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple
     * times.
     * 7 is a candidate, and 7 = 7.
     * These are the only two combinations.
     * Example 2:
     * 
     * Input: candidates = [2,3,5], target = 8
     * Output: [[2,2,2,2],[2,3,3],[3,5]]
     * Example 3:
     * 
     * Input: candidates = [2], target = 1
     * Output: []
     * 
     * 
     * Constraints:
     * 
     * 1 <= candidates.length <= 30
     * 2 <= candidates[i] <= 40
     * All elements of candidates are distinct.
     * 1 <= target <= 40
     * 
     */
    // @formatter:on

    public List<List<Integer>> combinationSumBackTracking(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        backTrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void backTrack(int[] candidates, int target, int index, List<Integer> current, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        if (target < 0 || index == candidates.length) {
            return;
        }

        backTrack(candidates, target, index + 1, current, result);
        current.add(candidates[index]);
        backTrack(candidates, target - candidates[index], index, current, result);
        current.remove(current.size() - 1);
    }

    public List<List<Integer>> combinationSumBruteForce(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        generateAll(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    public void generateAll(int[] candidates, int target, int startIndex, List<Integer> current,
            List<List<Integer>> result) {
        int currentSum = current.stream().mapToInt(Integer::intValue).sum();
        if (currentSum == target) {
            result.add(new ArrayList<>(current));
            return;
        }
        if (currentSum > target)
            return;
        for (int i = startIndex; i < candidates.length; i++) {
            current.add(candidates[i]);
            generateAll(candidates, target, i, current, result);
            current.remove(current.size() - 1);
        }
    }

    public List<List<Integer>> combinationSumBackTrackingSortingPrune(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        backTrackSortingPrune(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void backTrackSortingPrune(int[] candidates, int remaining, int startIndex, List<Integer> current,
            List<List<Integer>> result) {
        if (remaining == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = startIndex; i < candidates.length; i++) {
            if (candidates[i] > remaining)
                break;
            current.add(candidates[i]);
            backTrackSortingPrune(candidates, remaining - candidates[i], i, current, result);
            current.remove(current.size() - 1);
        }
    }
}

// @formatter:off
/*
 * ============================================================
 * Combination Sum — Deep Dive Explanation
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given a list of distinct positive integers (candidates) and a target integer,
 * find all unique combinations of candidates where the chosen numbers sum to the
 * target. Each number in the candidates array may be used an unlimited number of
 * times. The solution set must not contain duplicate combinations.
 *
 * LeetCode #39 — Medium
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * - int[] candidates — array of distinct positive integers
 * - int target       — the target sum
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * - List<List<Integer>> — all combinations (each sorted ascending) that sum to target
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * - 1 <= candidates.length <= 30
 * - 2 <= candidates[i] <= 40
 * - All elements of candidates are distinct
 * - 1 <= target <= 40
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Every unique multiset of elements from candidates (with repetition allowed)
 * that sums exactly to target.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    candidates = [2, 3, 6, 7], target = 7
 *    Output: [[2, 2, 3], [7]]
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Think of it like making exact change with coins (where you have unlimited of
 * each denomination). You try every coin, subtract its value from what you still
 * owe, and keep going until either you hit exactly zero (valid combination!) or
 * go negative (dead end).
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Start with remaining = target
 * 2. Try each candidate — pick one, reduce remaining by it
 * 3. Recurse with the same or later candidates (no going back, to avoid duplicates)
 * 4. If remaining == 0, record the current combination
 * 5. If remaining < 0, backtrack — this path is invalid
 * 6. Once all paths are explored, return all recorded combinations
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                        | Why it's tricky                                              |
 * |----------------------------------|--------------------------------------------------------------|
 * | Allowing reuse of elements       | You must not skip a candidate when recurring — stay at same  |
 * |                                  | index                                                        |
 * | Avoiding duplicate combinations  | Without an index constraint, [2,3] and [3,2] would both      |
 * |                                  | appear                                                       |
 * | Knowing when to stop             | Negative remaining means prune immediately                   |
 * | Building results by reference    | Java's list references mean you must copy the current path   |
 * |                                  | before adding to results                                     |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                      | Key Idea                              | Best Used When                         | Time Complexity  |
 * |---|-------------------------------|---------------------------------------|----------------------------------------|------------------|
 * | 1 | Brute Force                   | Generate all multisets, filter by sum | Never (exponential, impractical)       | O(N^(T/M))       |
 * | 2 | Backtracking ✅               | DFS with index to avoid duplicates,   | Always — canonical solution            | O(N^(T/M))       |
 * |   |                               | prune on overshoot                    |                                        | but pruned       |
 * | 3 | Backtracking + Sorting Prune  | Sort candidates; break early when     | When candidates are large and target   | O(N^(T/M))       |
 * |   |                               | candidate > remaining                 | is small                               | better constant  |
 *
 * ✅ Why Backtracking is optimal:
 * Combination Sum is inherently a search problem — you must enumerate valid paths
 * in a decision tree. Backtracking explores each path exactly once, prunes dead
 * ends immediately (when remaining < 0), and avoids duplicates via the startIndex
 * constraint. No DP table is needed because the problem asks for ALL combinations,
 * not just a count.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Generate all combinations of candidates with repetition (up to depth T/minCandidate)
 * 2. Compute sum for each combination
 * 3. If sum equals target, add to results
 *
 *    import java.util.*;
 *
 *    public class CombinationSumBruteForce {
 *
 *        public List<List<Integer>> combinationSum(int[] candidates, int target) {
 *            List<List<Integer>> results = new ArrayList<>();
 *            generateAll(candidates, target, 0, new ArrayList<>(), results);
 *            return results;
 *        }
 *
 *        private void generateAll(int[] candidates, int target, int startIndex,
 *                                  List<Integer> current, List<List<Integer>> results) {
 *            int currentSum = current.stream().mapToInt(Integer::intValue).sum();
 *            if (currentSum == target) {
 *                results.add(new ArrayList<>(current));
 *                return;
 *            }
 *            if (currentSum > target) return;
 *
 *            for (int i = startIndex; i < candidates.length; i++) {
 *                current.add(candidates[i]);
 *                generateAll(candidates, target, i, current, results); // i not i+1 → reuse allowed
 *                current.remove(current.size() - 1);
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            CombinationSumBruteForce solver = new CombinationSumBruteForce();
 *            System.out.println(solver.combinationSum(new int[]{2, 3, 6, 7}, 7));
 *            // Expected: [[2, 2, 3], [7]]
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 2: Backtracking ✅ (Optimal)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Sort candidates (enables early pruning in approach 3; harmless here)
 * 2. Call backtrack(startIndex=0, remaining=target, currentPath=[])
 * 3. Base case A: remaining == 0 → add a copy of currentPath to results
 * 4. Base case B: remaining < 0  → return (prune)
 * 5. Loop from startIndex to end of candidates:
 *    - Add candidates[i] to currentPath
 *    - Recurse with startIndex = i (same index → reuse allowed), remaining - candidates[i]
 *    - Remove last element (backtrack)
 *
 *    import java.util.*;
 *
 *    public class CombinationSum {
 *
 *        public List<List<Integer>> combinationSum(int[] candidates, int target) {
 *            List<List<Integer>> results = new ArrayList<>();
 *            backtrack(candidates, target, 0, new ArrayList<>(), results);
 *            return results;
 *        }
 *
 *        private void backtrack(int[] candidates, int remaining, int startIndex,
 *                                List<Integer> currentPath, List<List<Integer>> results) {
 *            if (remaining == 0) {
 *                results.add(new ArrayList<>(currentPath)); // snapshot — not a reference!
 *                return;
 *            }
 *            if (remaining < 0) return; // pruned
 *
 *            for (int i = startIndex; i < candidates.length; i++) {
 *                currentPath.add(candidates[i]);
 *                backtrack(candidates, remaining - candidates[i], i, currentPath, results);
 *                currentPath.remove(currentPath.size() - 1); // undo choice
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            CombinationSum solver = new CombinationSum();
 *            System.out.println(solver.combinationSum(new int[]{2, 3, 6, 7}, 7));
 *            // Expected: [[2, 2, 3], [7]]
 *            System.out.println(solver.combinationSum(new int[]{2, 3, 5}, 8));
 *            // Expected: [[2, 2, 2, 2], [2, 3, 3], [3, 5]]
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 3: Backtracking + Sorting Prune
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Sort candidates ascending
 * 2. In the loop, if candidates[i] > remaining, break immediately
 * 3. Everything else is identical to Approach 2
 *
 *    import java.util.*;
 *
 *    public class CombinationSumSorted {
 *
 *        public List<List<Integer>> combinationSum(int[] candidates, int target) {
 *            Arrays.sort(candidates); // key addition
 *            List<List<Integer>> results = new ArrayList<>();
 *            backtrack(candidates, target, 0, new ArrayList<>(), results);
 *            return results;
 *        }
 *
 *        private void backtrack(int[] candidates, int remaining, int startIndex,
 *                                List<Integer> currentPath, List<List<Integer>> results) {
 *            if (remaining == 0) {
 *                results.add(new ArrayList<>(currentPath));
 *                return;
 *            }
 *
 *            for (int i = startIndex; i < candidates.length; i++) {
 *                if (candidates[i] > remaining) break; // prune entire suffix
 *                currentPath.add(candidates[i]);
 *                backtrack(candidates, remaining - candidates[i], i, currentPath, results);
 *                currentPath.remove(currentPath.size() - 1);
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            CombinationSumSorted solver = new CombinationSumSorted();
 *            System.out.println(solver.combinationSum(new int[]{2, 3, 6, 7}, 7));
 *            // Expected: [[2, 2, 3], [7]]
 *        }
 *    }
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Let N = number of candidates, T = target, M = minimum candidate value.
 *
 * ------------------------------------------------------------
 * Approach 1 & 2: Backtracking
 * ------------------------------------------------------------
 * Time Complexity: O(N^(T/M))
 * - The recursion tree has depth at most T/M (deepest path uses smallest candidate)
 * - At each level, you branch up to N times
 * - Total nodes ≈ N^(T/M)
 *
 * Concrete example: candidates=[2,3,6,7], target=7, M=2
 * - Depth ≤ 7/2 = 3–4 levels
 * - Branches ≤ 4 at each level → ~4^4 = 256 nodes max (many pruned)
 *
 * Space Complexity: O(T/M)
 * - Recursion stack depth = at most T/M
 * - currentPath holds at most T/M elements
 * - Output space excluded (results list)
 *
 * ------------------------------------------------------------
 * Approach 3: Backtracking + Sort Prune
 * ------------------------------------------------------------
 * Time Complexity: O(N^(T/M)) — same worst case, but break significantly
 *                  reduces the constant factor in practice.
 * Space Complexity: O(T/M) — same.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * candidates = [2, 3, 6, 7], target = 7
 *
 * backtrack(remaining=7, start=0, path=[])
 * ├─ pick 2 → backtrack(remaining=5, start=0, path=[2])
 * │   ├─ pick 2 → backtrack(remaining=3, start=0, path=[2,2])
 * │   │   ├─ pick 2 → backtrack(remaining=1, start=0, path=[2,2,2])
 * │   │   │   ├─ pick 2 → remaining=-1 ✗ prune
 * │   │   │   ├─ pick 3 → remaining=-2 ✗ prune
 * │   │   │   └─ ... all prune
 * │   │   ├─ pick 3 → backtrack(remaining=0, path=[2,2,3]) ✅ FOUND!
 * │   │   ├─ pick 6 → remaining=-3 ✗ prune
 * │   │   └─ pick 7 → remaining=-4 ✗ prune
 * │   ├─ pick 3 → backtrack(remaining=2, start=1, path=[2,3])
 * │   │   ├─ pick 3 → remaining=-1 ✗ prune
 * │   │   └─ ... all prune
 * │   ├─ pick 6 → remaining=-1 ✗ prune
 * │   └─ pick 7 → remaining=-2 ✗ prune
 * ├─ pick 3 → backtrack(remaining=4, start=1, path=[3])
 * │   ├─ pick 3 → backtrack(remaining=1, start=1, path=[3,3])
 * │   │   └─ all prune
 * │   ├─ pick 6 → remaining=-2 ✗ prune
 * │   └─ pick 7 → remaining=-3 ✗ prune
 * ├─ pick 6 → backtrack(remaining=1, start=2, path=[6])
 * │   └─ all prune
 * └─ pick 7 → backtrack(remaining=0, path=[7]) ✅ FOUND!
 *
 * Result: [[2, 2, 3], [7]]
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                           | Input                        | Expected Output        | How Handled                                          |
 * |-------------------------------------|------------------------------|------------------------|------------------------------------------------------|
 * | No valid combination                | candidates=[3,5], target=7   | []                     | Recursion exhausts all paths; base case A never hit  |
 * | Single candidate equals target      | candidates=[7], target=7     | [[7]]                  | Immediate hit on first recursion level               |
 * | Single candidate repeated           | candidates=[2], target=8     | [[2,2,2,2]]            | Index stays at 0; candidate reused until remaining=0 |
 * | Minimum target (target=1)           | candidates=[2,3], target=1   | []                     | All candidates > 1; pruned immediately               |
 * | Large target, small candidate       | candidates=[2], target=40    | [[2,2,...,2]] (20 twos)| Deep recursion (depth 20); within stack limits       |
 * | Multiple valid combinations         | candidates=[2,3,5], target=8 | [[2,2,2,2],[2,3,3],[3,5]]| All three paths found correctly                   |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 *
 * Pitfall 1: Forgetting to copy the path
 *    // WRONG — adds the reference; list will be empty in results later
 *    results.add(currentPath);
 *
 *    // CORRECT — snapshot the current state
 *    results.add(new ArrayList<>(currentPath));
 *
 * Pitfall 2: Advancing startIndex past current (no reuse)
 *    // WRONG — passes i+1, preventing reuse of candidates[i]
 *    backtrack(candidates, remaining - candidates[i], i + 1, currentPath, results);
 *
 *    // CORRECT — passes i, allowing reuse
 *    backtrack(candidates, remaining - candidates[i], i, currentPath, results);
 *
 * Pitfall 3: Using continue instead of break in sorted approach
 *    // WRONG — skips one candidate but keeps trying larger ones
 *    if (candidates[i] > remaining) continue;
 *
 *    // CORRECT — once sorted, all further candidates are also too large
 *    if (candidates[i] > remaining) break;
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The main risk is when every candidate is larger than the target — the loop
 *    never adds anything and returns empty, which is correct. Also: if candidates
 *    contains 1 (not possible per constraints, but hypothetically), depth could
 *    reach target — still handled, just deeper stack.
 *
 * Q: Are there any type mismatches?
 * A: No. candidates is int[], remaining is int, and currentPath is List<Integer>.
 *    currentPath.remove(currentPath.size() - 1) correctly removes by INDEX (not by
 *    value), which is important — remove(Integer.valueOf(x)) would remove by value
 *    and could behave unexpectedly.
 *
 * Q: How can I verify this works right now?
 *
 *    public static void verify() {
 *        CombinationSum solver = new CombinationSum();
 *
 *        List<List<Integer>> r1 = solver.combinationSum(new int[]{2, 3, 6, 7}, 7);
 *        assert r1.size() == 2 : "Expected 2 combinations";
 *        assert r1.contains(Arrays.asList(2, 2, 3)) : "Missing [2,2,3]";
 *        assert r1.contains(Arrays.asList(7)) : "Missing [7]";
 *
 *        List<List<Integer>> r2 = solver.combinationSum(new int[]{2, 3, 5}, 8);
 *        assert r2.size() == 3 : "Expected 3 combinations";
 *
 *        List<List<Integer>> r3 = solver.combinationSum(new int[]{3, 5}, 7);
 *        assert r3.isEmpty() : "Expected no combinations";
 *
 *        System.out.println("All assertions passed!");
 *    }
 *
 * | Approach              | Risk                                          | Mitigation                                     |
 * |-----------------------|-----------------------------------------------|------------------------------------------------|
 * | Backtracking          | Forgetting to copy path → empty results       | Always new ArrayList<>(currentPath)            |
 * | Backtracking          | Using i+1 instead of i → misses reuse         | Confirm startIndex = i in recursive call       |
 * | Sorted Prune          | Using continue instead of break → wasted work | Ensure array is sorted before using break      |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * | Company         | Frequency | Notes                                          |
 * |-----------------|-----------|------------------------------------------------|
 * | Amazon          | ⭐⭐⭐⭐⭐  | Very frequently asked; tests backtracking      |
 * | Google          | ⭐⭐⭐⭐⭐  | Common in phone screens and onsite rounds      |
 * | Facebook/Meta   | ⭐⭐⭐⭐    | Often paired with Combination Sum II           |
 * | Microsoft       | ⭐⭐⭐⭐    | Appears in SDE II rounds                       |
 * | Apple           | ⭐⭐⭐      | Occasionally seen in technical screens         |
 * | Bloomberg       | ⭐⭐⭐      | Standard backtracking question pool            |
 * | LinkedIn        | ⭐⭐⭐      | Mid-level interview staple                     |
 * | Uber            | ⭐⭐⭐      | Seen in backend engineer rounds                |
 * | Adobe           | ⭐⭐        | Appears in senior engineer interviews          |
 * | Salesforce      | ⭐⭐        | Occasionally asked                             |
 *
 * LeetCode #39 | Difficulty: Medium | ~1,800+ interview appearances reported
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach                     | Time        | Space   | Code Complexity | Recommended?          |
 * |------------------------------|-------------|---------|-----------------|------------------------|
 * | Brute Force                  | O(N^(T/M))  | O(T/M)  | Low (but slow)  | ❌ Not recommended     |
 * | Backtracking                 | O(N^(T/M))  | O(T/M)  | Low–Medium      | ✅ Solid choice        |
 * | Backtracking + Sort Prune    | O(N^(T/M))  | O(T/M)  | Low–Medium      | ✅✅ Best choice       |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Backtracking with sorted candidates and early break — it's clean, interview-
 * friendly, and prunes the search space as aggressively as possible without
 * added complexity.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * - The key insight is using startIndex = i (not i+1) in the recursive call —
 *   this is what allows reuse of the same element.
 * - Always copy the path (new ArrayList<>(currentPath)) before adding to results
 *   — never add the reference.
 * - Sorting + break transforms O(N) per level wasted iterations into O(k) where
 *   k is the number of valid choices — a simple optimization that matters at scale.
 */
// @formatter:on
