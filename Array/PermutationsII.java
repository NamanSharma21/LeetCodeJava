package Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PermutationsII {
    public static void main(String[] args) {
        PermutationsII permutations = new PermutationsII();
        System.out.println("PermutationsII : " + permutations.permuteUniqueBackTrackPruning(new int[] { 1, 2, 3 }));
        System.out.println("PermutationsII : " + permutations.permuteUniqueBackTrackPruning(new int[] { 1, 1, 2 }));
        System.out.println("PermutationsII : " + permutations.permuteUniqueBackTrackPruning(new int[] { 2, 2, 1, 1 }));
        System.out.println("PermutationsII : " + permutations.permuteUniqueBruteForce(new int[] { 1, 2, 3 }));
        System.out.println("PermutationsII : " + permutations.permuteUniqueBruteForce(new int[] { 1, 1, 2 }));
        System.out.println("PermutationsII : " + permutations.permuteUniqueBruteForce(new int[] { 2, 2, 1, 1 }));
    }

    // @formatter:off
    /**
     * 
     * https://leetcode.com/problems/permutations-ii/description/?envType=problem-list-v2&envId=array
     * 
     * 
     * 
     * Given a collection of numbers, nums, that might contain duplicates, return
     * all possible unique permutations in any order.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,1,2]
     * Output:
     * [[1,1,2],[1,2,1],[2,1,1]]
     * Example 2:
     * 
     * Input: nums = [1,2,3]
     * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 8
     * -10 <= nums[i] <= 10
     * 
     */
    // @formatter:off

    public List<List<Integer>> permuteUniqueBackTrackPruning(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];
        List<Integer> path = new ArrayList<>();
        backTrackPruning(nums, used, path, result);
        return result;
    }

    private void backTrackPruning(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> result) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }

            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }

            used[i] = true;
            path.add(nums[i]);
            backTrackPruning(nums, used, path, result);
            path.remove(path.size() - 1);
            used[i] = false;
        }
    }

    public List<List<Integer>> permuteUniqueBruteForce(int[] nums) {
        Set<List<Integer>> result = new HashSet<>();
        backTrackBruteForceInPlaceSwap(nums, 0, result);
        return new ArrayList<>(result);
    }

    public void backTrackBruteForceInPlaceSwap(int[] nums,int position,Set<List<Integer>> result){
        if(position==nums.length){
            List<Integer> temp = new ArrayList<>();
            for(int num:nums){
                temp.add(num);
            }
            result.add(temp);
            return;
        }

        for(int i = position;i<nums.length;i++){
            swap(nums, position, i);
            backTrackBruteForceInPlaceSwap(nums, position+1, result);
            swap(nums, position, i);
        }
    }

    public void swap(int[] nums, int left,int right){
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }
}

// @formatter:off
/*
 * ============================================================
 * Permutations II — Deep Dive Explanation
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an array of integers that MAY CONTAIN DUPLICATES, return all unique
 * permutations of the array. Unlike Permutations I (which guarantees distinct
 * elements), here duplicate numbers can appear multiple times and we must
 * ensure no duplicate permutations appear in the output.
 *
 * LeetCode #47 — Medium
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums — an array of integers (may contain duplicates), length 1 ≤ n ≤ 8
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * List<List<Integer>> — all unique permutations
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * 1 ≤ nums.length ≤ 8
 * -10 ≤ nums[i] ≤ 10
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Every possible ordering of the array elements, but each ordering counted
 * only once even if duplicate elements exist.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 * Input:  [1, 1, 2]
 * Output: [[1,1,2], [1,2,1], [2,1,1]]
 *
 * Notice: [1,1,2] appears only once even though there are two 1s.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Imagine placing numbered balls one by one into positions. When two balls
 * look identical (same number), swapping them gives you the same arrangement
 * — we want to SKIP that swap.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Build permutations recursively, picking one element at a time for each slot.
 * 2. At each recursion level, track which values have already been tried.
 * 3. If the same value was already tried at this level, SKIP it — placing it
 *    again can only produce a duplicate permutation.
 * 4. Use a sorted array + a used[] boolean array to enforce this rule.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge              | Why It's Tricky                                        |
 * |------------------------|--------------------------------------------------------|
 * | Identical elements     | Two 1s are the same; naive backtracking emits duplicates|
 * | Knowing when to skip   | Skip nums[i] only if nums[i]==nums[i-1] && !used[i-1]  |
 * | Sorting requirement    | Dedup rule only works if equal elements are adjacent    |
 * | used[] vs visited set  | Forgetting to reset used[i] causes wrong results        |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                          | Key Idea                              | Best Used When          | Time Complexity |
 * |---|-----------------------------------|---------------------------------------|-------------------------|-----------------|
 * | 1 | Brute Force (generate all, dedup) | Generate every perm, put in Set       | Learning only           | O(n! × n)       |
 * | 2 | Backtracking + Pruning ✅          | Sort first; skip dup at same depth    | Always — standard opt.  | O(n! × n)       |
 *
 * The brute force has the same Big-O but carries heavy constant overhead from
 * hashing full permutations and discarding duplicates. The optimal backtracking
 * prunes duplicate branches BEFORE they are explored, using no hash set at all.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force — Generate All, Deduplicate
 * ------------------------------------------------------------
 *
 * Algorithm:
 * 1. Use standard recursive permutation generation (swap-based).
 * 2. Convert each permutation to a List<Integer> and add to a Set<List<Integer>>.
 * 3. Convert the set to a list at the end.
 *
 *    import java.util.*;
 *
 *    public class PermutationsIIBrute {
 *
 *        public List<List<Integer>> permuteUnique(int[] nums) {
 *            Set<List<Integer>> resultSet = new HashSet<>();
 *            permute(nums, 0, resultSet);
 *            return new ArrayList<>(resultSet);
 *        }
 *
 *        private void permute(int[] nums, int start, Set<List<Integer>> resultSet) {
 *            if (start == nums.length) {
 *                List<Integer> perm = new ArrayList<>();
 *                for (int num : nums) perm.add(num);
 *                resultSet.add(perm);
 *                return;
 *            }
 *            for (int i = start; i < nums.length; i++) {
 *                swap(nums, start, i);
 *                permute(nums, start + 1, resultSet);
 *                swap(nums, start, i); // backtrack
 *            }
 *        }
 *
 *        private void swap(int[] nums, int a, int b) {
 *            int temp = nums[a];
 *            nums[a] = nums[b];
 *            nums[b] = temp;
 *        }
 *
 *        public static void main(String[] args) {
 *            PermutationsIIBrute sol = new PermutationsIIBrute();
 *            System.out.println(sol.permuteUnique(new int[]{1, 1, 2}));
 *            // Expected: [[1,1,2], [1,2,1], [2,1,1]] (order may vary)
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 2: Backtracking + Pruning ✅ (Optimal)
 * ------------------------------------------------------------
 *
 * Algorithm:
 * 1. SORT nums so duplicates are adjacent.
 * 2. Maintain a boolean[] used array (same length as nums).
 * 3. Maintain a currentPerm list and the result list.
 * 4. At each call, iterate over all indices i:
 *    - If used[i] is true → skip (already placed in this branch).
 *    - If i > 0 && nums[i] == nums[i-1] && !used[i-1] → skip (duplicate at this depth).
 *    - Otherwise: mark used[i]=true, add nums[i] to currentPerm, recurse, backtrack.
 * 5. When currentPerm.size() == nums.length, add a copy to result.
 *
 *    import java.util.*;
 *
 *    public class PermutationsII {
 *
 *        public List<List<Integer>> permuteUnique(int[] nums) {
 *            Arrays.sort(nums); // critical: makes duplicates adjacent
 *            List<List<Integer>> result = new ArrayList<>();
 *            boolean[] used = new boolean[nums.length];
 *            backtrack(nums, used, new ArrayList<>(), result);
 *            return result;
 *        }
 *
 *        private void backtrack(int[] nums, boolean[] used,
 *                               List<Integer> currentPerm, List<List<Integer>> result) {
 *            if (currentPerm.size() == nums.length) {
 *                result.add(new ArrayList<>(currentPerm));
 *                return;
 *            }
 *
 *            for (int i = 0; i < nums.length; i++) {
 *                if (used[i]) continue;
 *
 *                // Skip duplicate: same value as previous, and previous was NOT used
 *                if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;
 *
 *                used[i] = true;
 *                currentPerm.add(nums[i]);
 *
 *                backtrack(nums, used, currentPerm, result);
 *
 *                // Backtrack
 *                used[i] = false;
 *                currentPerm.remove(currentPerm.size() - 1);
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            PermutationsII sol = new PermutationsII();
 *            System.out.println(sol.permuteUnique(new int[]{1, 1, 2}));
 *            // Output: [[1, 1, 2], [1, 2, 1], [2, 1, 1]]
 *
 *            System.out.println(sol.permuteUnique(new int[]{1, 2, 3}));
 *            // Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *        }
 *    }
 *
 * Why !used[i-1]?
 * When nums[i] == nums[i-1] and used[i-1] is FALSE, it means nums[i-1] was
 * already tried and completed at this recursion depth. Trying nums[i] (same value)
 * would start an identical sub-tree — so we skip. When used[i-1] is TRUE, it means
 * nums[i-1] is placed earlier in the current path — nums[i] is a different slot
 * and that's valid.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 — Brute Force
 * ------------------------------------------------------------
 * Time:  O(n! × n)
 *   - We generate all n! permutations (each taking O(n) to build and hash).
 *   - For n=8: 40,320 permutations × 8 = ~322,560 operations.
 *
 * Space: O(n! × n) — the HashSet stores up to n! lists of size n.
 *
 * ------------------------------------------------------------
 * Approach 2 — Backtracking + Pruning ✅
 * ------------------------------------------------------------
 * Time:  O(n! × n) worst case (when all elements are distinct).
 *   - With duplicates, actual recursive calls are far fewer (branches pruned early).
 *   - For [1,1,2] with 3 unique perms: only 3!/2! = 3 leaves, brute force visits 6.
 *
 * Space: O(n)
 *   - used[] array: O(n)
 *   - currentPerm list: O(n)
 *   - Recursion stack depth: O(n)
 *   - Result list: O(unique_perms × n) — unavoidable output space.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 — Brute Force: [1, 1, 2]
 * ------------------------------------------------------------
 * All swap-based permutations (raw, before dedup):
 *   [1, 1, 2]  ← added to Set
 *   [1, 2, 1]  ← added to Set
 *   [1, 2, 1]  ← DUPLICATE, Set ignores
 *   [1, 1, 2]  ← DUPLICATE, Set ignores
 *   [2, 1, 1]  ← added to Set
 *   [2, 1, 1]  ← DUPLICATE, Set ignores
 * Final Set → {[1,1,2], [1,2,1], [2,1,1]}
 *
 * ------------------------------------------------------------
 * Approach 2 — Backtracking: [1, 1, 2] (sorted: [1, 1, 2])
 * ------------------------------------------------------------
 * backtrack([], used=[F,F,F])
 * ├─ i=0: pick nums[0]=1  → used=[T,F,F], perm=[1]
 * │   backtrack([1], used=[T,F,F])
 * │   ├─ i=0: used[0]=T → skip
 * │   ├─ i=1: pick nums[1]=1 → used=[T,T,F], perm=[1,1]
 * │   │   backtrack([1,1], used=[T,T,F])
 * │   │   ├─ i=0: used[0]=T → skip
 * │   │   ├─ i=1: used[1]=T → skip
 * │   │   └─ i=2: pick nums[2]=2 → perm=[1,1,2] ✅ ADD
 * │   └─ i=2: pick nums[2]=2 → used=[T,F,T], perm=[1,2]
 * │       backtrack([1,2], used=[T,F,T])
 * │       ├─ i=0: used[0]=T → skip
 * │       ├─ i=1: pick nums[1]=1 → perm=[1,2,1] ✅ ADD
 * │       └─ i=2: used[2]=T → skip
 * │
 * ├─ i=1: nums[1]==nums[0] && !used[0] → SKIP (prune duplicate branch)
 * │
 * └─ i=2: pick nums[2]=2 → used=[F,F,T], perm=[2]
 *     backtrack([2], used=[F,F,T])
 *     ├─ i=0: pick nums[0]=1 → perm=[2,1]
 *     │   backtrack([2,1], used=[T,F,T])
 *     │   ├─ i=0: used[0]=T → skip
 *     │   ├─ i=1: pick nums[1]=1 → perm=[2,1,1] ✅ ADD
 *     │   └─ i=2: used[2]=T → skip
 *     ├─ i=1: nums[1]==nums[0] && !used[0] → SKIP
 *     └─ i=2: used[2]=T → skip
 *
 * Result: [[1,1,2], [1,2,1], [2,1,1]] — 3 unique permutations, no duplicates explored.
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case          | Input       | Expected Output        | How Handled                                             |
 * |--------------------|-------------|------------------------|---------------------------------------------------------|
 * | Single element     | [5]         | [[5]]                  | Base case: currentPerm.size()==1==nums.length, added    |
 * | All identical      | [2,2,2]     | [[2,2,2]]              | Pruning skips all duplicate indices at every depth      |
 * | All distinct       | [1,2,3]     | All 6 permutations     | No pruning triggered; behaves like Permutations I       |
 * | Two elems, dups    | [0,0]       | [[0,0]]                | i=1 pruned at depth 0; only [0,0] generated             |
 * | Negative numbers   | [-1,-1,0]   | [[-1,-1,0],...]        | Sorting places -1 before 0; pruning works identically   |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 *
 * Forgetting to sort:
 *   // WRONG — duplicates not adjacent, pruning rule fails silently
 *   int[] nums = {2, 1, 1};  // not sorted
 *
 *   // CORRECT
 *   Arrays.sort(nums);  // → [1, 1, 2] before calling backtrack
 *
 * Wrong pruning condition — using used[i-1] instead of !used[i-1]:
 *   // WRONG — skips too aggressively; misses valid permutations
 *   if (i > 0 && nums[i] == nums[i-1] && used[i-1]) continue;
 *
 *   // CORRECT
 *   if (i > 0 && nums[i] == nums[i-1] && !used[i-1]) continue;
 *
 * Forgetting to copy the list before adding:
 *   // WRONG — adds reference; all entries will be empty list at the end
 *   result.add(currentPerm);
 *
 *   // CORRECT
 *   result.add(new ArrayList<>(currentPerm));
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: An all-identical array like [3,3,3,3] — verify only one permutation is returned.
 *    Also test negative numbers since sorting still works correctly with them.
 *
 * Q: Are there any type mismatches?
 * A: None. nums is int[], used is boolean[], currentPerm is List<Integer>
 *    (autoboxing handles int→Integer), result is List<List<Integer>>.
 *
 * Q: How can I verify this works right now?
 *
 *    public static void verify() {
 *        PermutationsII sol = new PermutationsII();
 *
 *        // Test 1: standard case with duplicates
 *        List<List<Integer>> r1 = sol.permuteUnique(new int[]{1, 1, 2});
 *        assert r1.size() == 3 : "Expected 3 unique permutations";
 *
 *        // Test 2: all identical
 *        List<List<Integer>> r2 = sol.permuteUnique(new int[]{2, 2, 2});
 *        assert r2.size() == 1 : "Expected 1 unique permutation";
 *
 *        // Test 3: all distinct
 *        List<List<Integer>> r3 = sol.permuteUnique(new int[]{1, 2, 3});
 *        assert r3.size() == 6 : "Expected 6 unique permutations";
 *
 *        // Test 4: single element
 *        List<List<Integer>> r4 = sol.permuteUnique(new int[]{5});
 *        assert r4.size() == 1 && r4.get(0).get(0) == 5 : "Expected [[5]]";
 *
 *        System.out.println("All assertions passed!");
 *    }
 *
 * | Approach                  | Risk                                  | Mitigation                            |
 * |---------------------------|---------------------------------------|---------------------------------------|
 * | Brute Force               | HashSet overhead, unpredictable order | Use only for testing/reference        |
 * | Backtracking + Pruning    | Wrong pruning condition: missing perms| Unit test all-identical & mixed arrays|
 * | Backtracking + Pruning    | Forgetting Arrays.sort: missed prunes | Always sort as first line             |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * | Company          | Frequency | Notes                              |
 * |------------------|-----------|------------------------------------|
 * | Amazon           | ⭐⭐⭐⭐⭐    | Very common in SDE interviews      |
 * | Google           | ⭐⭐⭐⭐     | Tests backtracking mastery         |
 * | Microsoft        | ⭐⭐⭐⭐     | Often paired with Permutations I   |
 * | Facebook / Meta  | ⭐⭐⭐⭐     | Classic pruning question           |
 * | Apple            | ⭐⭐⭐      | Appears in on-site rounds          |
 * | Bloomberg        | ⭐⭐⭐      | Seen in phone screens              |
 * | Adobe            | ⭐⭐⭐      | Used to test recursion understanding|
 * | Uber             | ⭐⭐⭐      | Appears in mid-level interviews    |
 * | LinkedIn         | ⭐⭐       | Occasional appearance              |
 * | Oracle           | ⭐⭐       | Less frequent, but known to appear |
 *
 * LeetCode #47 — Medium
 * ~3,000+ reported interview appearances across platforms
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach                     | Time      | Space      | Code Complexity | Recommended?                  |
 * |------------------------------|-----------|------------|-----------------|-------------------------------|
 * | Brute Force (Set dedup)      | O(n! × n) | O(n! × n)  | Low             | ❌ wasteful, heavy memory      |
 * | Backtracking + Pruning       | O(n! × n) | O(n)       | Medium          | ✅✅ always use this           |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Backtracking with sort + used[] array and the !used[i-1] pruning condition.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * Sort the array first — without this, equal elements aren't adjacent and the
 * pruning rule silently fails. The dedup condition nums[i]==nums[i-1] && !used[i-1]
 * means: "this value was already tried at this recursion depth, so skip it."
 * The !used[i-1] part is the subtle insight — if used[i-1] were true, nums[i-1]
 * would be in an earlier slot, making this a legitimately different permutation.
 */
// @formatter:on
