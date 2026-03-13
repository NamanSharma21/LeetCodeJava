package Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermutationsII {
    public static void main(String[] args) {
        PermutationsII permutations = new PermutationsII();
        System.out.println("PermutationsII : " + permutations.permuteUnique(new int[] { 1, 2, 3 }));
        System.out.println("PermutationsII : " + permutations.permuteUnique(new int[] { 1, 1, 2 }));
        System.out.println("PermutationsII : " + permutations.permuteUnique(new int[] { 2, 2, 1, 1 }));
    }

    /**
     * 
     * https://leetcode.com/problems/permutations-ii/description/?envType=problem-list-v2&envId=array
     * 
     * Given an array nums of distinct integers, return all the possible
     * permutations. You can return the answer in any order.
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
     * [[1,1,2],
     * [1,2,1],
     * [2,1,1]]
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

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];
        List<Integer> path = new ArrayList<>();
        backTrack(nums, used, path, result);
        return result;
    }

    private void backTrack(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> result) {
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
            backTrack(nums, used, path, result);
            path.remove(path.size() - 1);
            used[i] = false;
        }
    }

    /**
     * 
     * ## 1. Problem Statement
     ** 
     * Name:** Permutations II (LeetCode 47)
     * 
     * You are given an integer array `nums` that **may contain duplicates**.
     * 
     * - You must generate **all possible unique permutations** of `nums`.
     * - Each permutation:
     * - Uses every element exactly once.
     * - Has length `nums.length`.
     * - The result must not contain duplicate permutations, even if `nums` has
     * repeated numbers. [algo](https://algo.monster/liteproblems/47)
     * 
     * ### Input / Output
     * 
     * - **Input:** `int[] nums`
     * - Typical constraints:
     * [algo](https://algo.monster/liteproblems/permutations-ii)
     * - `1 <= nums.length <= 8`
     * - `-10 <= nums[i] <= 10`
     * - **Output:** `List<List<Integer>>`
     * - A list of all **unique** permutations of `nums` in any order.
     ** 
     * You must compute:**
     * A list of permutations such that:
     * 
     * - Each is a permutation of the entire input array.
     * - No two permutations in the result are identical.
     * 
     * Example:
     * 
     * - `nums = [1,1,2]` → `[[1,1,2], [1,2,1], [2,1,1]]`.
     * [sparkcodehub](https://www.sparkcodehub.com/leetcode/47/permutations-ii)
     ***
     * 
     * 
     * ## 2. Intuition
     * 
     * This is exactly the **Permutations** problem, except now the array can have
     * **duplicate values**.
     * 
     * If you naïvely generate all permutations and don’t handle duplicates, you
     * will see repeated permutations because swapping equal values doesn’t change
     * the sequence.
     * 
     * For example, `nums = [1,1,2]`:
     * 
     * - If you treat the two `1`s as distinct positions, permutations like:
     * - `1(1st),1(2nd),2`
     * - `1(2nd),1(1st),2`
     * look different by index, but as values they’re both `[1,1,2]` and must appear
     * only once.
     * 
     * So the **key challenge**:
     * 
     * > Avoid generating the same value permutation more than once when `nums`
     * contains equal values. [algo](https://algo.monster/liteproblems/47)
     * 
     * Human reasoning:
     * 
     * - If the numbers were all distinct, you’d pick any unused number at each step
     * and recurse.
     * - With duplicates, you need a rule like:
     * - “If I’ve already used the same value at this decision level, skip future
     * identical choices.”
     * - A common way:
     * - **Sort** the array so that duplicates are adjacent,
     * - Then, in the loop, **skip** choosing the same number at the same recursion
     * depth.
     ***
     * 
     * 
     * ## 3. Approach Overview
     * 
     * ### Approach 1 – Backtracking with `used[]` and duplicate skipping (optimal &
     * standard)
     * 
     * - **Key idea:**
     * - Sort `nums`.
     * - Use a `boolean[] used` to mark indices already in the current path.
     * - When iterating over indices in a loop, if `nums[i] == nums[i-1]` and the
     * previous identical value was **not used** at this depth, skip `i` to avoid
     * duplicate permutations.
     * [algo](https://algo.monster/liteproblems/permutations-ii)
     * - **When used:** Default choice in interviews; clear and robust.
     * - **Optimal?** Yes; asymptotically O(n · n!) in time, which is unavoidable
     * when listing all unique permutations, and O(n) extra space.
     * 
     * ### Approach 2 – Backtracking with in-place swapping + set per level
     * 
     * - **Key idea:**
     * - Use the swap-based permutation algorithm (like Permutations I).
     * - At each recursion `position`, use a `Set<Integer>` to remember which values
     * have already been placed at this `position` to avoid reusing the same value
     * and causing duplicates.
     * [youtube](https://www.youtube.com/watch?v=is_T6uzlTyg)
     * - **When used:** Also accepted; shows knowledge of set-based pruning.
     * - **Optimal?** Same complexity as Approach 1; uses a bit more overhead due to
     * sets.
     * 
     * ### Approach 3 – Generate all permutations then deduplicate
     * 
     * - **Key idea:**
     * - Ignore duplicates at generation time, generate all permutations (like in
     * Permutations I).
     * - Store permutations in a `Set<List<Integer>>` to remove duplicates.
     * - **When used:** Only for conceptual understanding or very small input;
     * wasteful.
     * - **Optimal?** No. Generates `n!` permutations where many are duplicates,
     * then filters; more overhead.
     * 
     * In practice, **Approach 1** is the recommended solution.
     ***
     * 
     * 
     * ## 4. Detailed Solutions in Java
     * 
     * ### 4.1 Approach 1 – Backtracking with `used[]` and skip rule (Recommended)
     * 
     * #### Core idea
     * 
     * - Sort array so equal values are adjacent.
     * - Normally, at each recursion depth, you iterate `i=0..n-1` and pick unused
     * elements.
     * - To avoid duplicates:
     * For a given depth, if `nums[i] == nums[i-1]` and `used[i-1] == false`, then
     * **skip** `i`.
     * [sparkcodehub](https://www.sparkcodehub.com/leetcode/47/permutations-ii)
     * 
     * Why this works:
     * 
     * - For identical values (say three `1`s), you only allow the **first unused
     * `1`** at any depth to start a branch.
     * - Any branch that would differ only by picking another `1` in the same depth
     * is skipped.
     * 
     * #### Algorithm (step-by-step)
     * 
     * 1. Sort `nums`.
     * 2. Maintain:
     * - `List<List<Integer>> result`.
     * - `List<Integer> path`.
     * - `boolean[] used` (length n).
     * 3. DFS/backtrack:
     * - If `path.size() == n`: add a copy of `path` to `result`.
     * - Else:
     * - For i in `0..n-1`:
     * - If `used[i]`, continue (already in path).
     * - If `i > 0 && nums[i] == nums[i-1] && !used[i-1]`, continue (skip duplicate
     * at this depth).
     * - Mark `used[i] = true`, add `nums[i]` to `path`, recurse.
     * - After recursion, `path.removeLast()`, `used[i] = false`.
     * 
     * #### Java Code
     * 
     * ```java
     * import java.util.ArrayList;
     * import java.util.Arrays;
     * import java.util.List;
     * 
     * public class PermutationsII_UsedArray {
     * 
     * public List<List<Integer>> permuteUnique(int[] nums) {
     * List<List<Integer>> result = new ArrayList<>();
     * Arrays.sort(nums); // sort to group duplicates
     * 
     * boolean[] used = new boolean[nums.length];
     * List<Integer> path = new ArrayList<>();
     * 
     * backtrack(nums, used, path, result);
     * return result;
     * }
     * 
     * private void backtrack(int[] nums, boolean[] used,
     * List<Integer> path, List<List<Integer>> result) {
     * 
     * // If path length == nums length, we have a full permutation
     * if (path.size() == nums.length) {
     * result.add(new ArrayList<>(path));
     * return;
     * }
     * 
     * // Try every index as next element
     * for (int i = 0; i < nums.length; i++) {
     * 
     * // Skip if this index is already used in the current path
     * if (used[i]) {
     * continue;
     * }
     * 
     * // Skip duplicates:
     * // If current value equals previous, and previous is NOT used in this path,
     * // then choosing nums[i] here would create a duplicate permutation.
     * if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
     * continue;
     * }
     * 
     * // Choose nums[i]
     * used[i] = true;
     * path.add(nums[i]);
     * 
     * // Recurse to fill the next position
     * backtrack(nums, used, path, result);
     * 
     * // Backtrack
     * path.remove(path.size() - 1);
     * used[i] = false;
     * }
     * }
     * }
     * ```
     * 
     * #### Complexity
     * 
     * - **Time:**
     * - In the worst case with all distinct elements, this behaves like ordinary
     * permutation generation: **O(n · n!)**.
     * - With duplicates, the number of *unique* permutations is smaller, but the
     * algorithm still explores a subset of the n! tree; complexity is still O(n ·
     * n!) in standard analysis.
     * [progiez](https://progiez.com/47-permutations-ii-leetcode-solution)
     * - **Space:**
     * - `used[]` → O(n).
     * - `path` → O(n).
     * - Recursion depth → O(n).
     * - Output → O(n · k) where k is number of unique permutations (≤ n!).
     * - Auxiliary (excluding output): **O(n)**.
     * [progiez](https://progiez.com/47-permutations-ii-leetcode-solution)
     * 
     * Example:
     * - n = 3 (`[1,1,2]`) → at most 6 theoretical permutations, but only 3 unique.
     * - At each depth, skip rule prunes branches early.
     * 
     * #### Worked Example – `nums = [1,1,2]`
     * 
     * 1. Sort: `nums = [1,1,2]`.
     * 2. `used = [F,F,F]`, `path = []`, `result = []`.
     ** 
     * Depth 0:**
     * 
     * - i = 0:
     * - `used[0]=false`, `i>0` is false → no skip.
     * - Choose `1` (index 0): `path= [algo](https://algo.monster/liteproblems/47)`,
     * `used=[T,F,F]`.
     * - Recurse.
     ** 
     * Depth 1 (path=):**
     * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
     * 
     * - i = 0:
     * - `used[0]=true` → skip.
     * - i = 1:
     * - `used [algo](https://algo.monster/liteproblems/47)=false`.
     * - `i>0 && nums [algo](https://algo.monster/liteproblems/47)==nums[0] &&
     * !used[0]` → `1>0 && 1==1 && !true` → false.
     * - So we **allow** this second `1` because the first identical `1` is already
     * used in the path.
     * - Choose `1` (index 1): `path=[1,1]`, `used=[T,T,F]`.
     * - Recurse.
     ** 
     * Depth 2 (path=):**
     * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
     * 
     * - i=0,1: used → skip.
     * - i=2:
     * - `used [youtube](https://www.youtube.com/watch?v=qhBVWf0YafA)=false`, `nums
     * [youtube](https://www.youtube.com/watch?v=qhBVWf0YafA)=2`.
     * - No duplicate condition triggered.
     * - Choose 2: `path=[1,1,2]`, `used=[T,T,T]`.
     * - Recurse → length 3 → add `[1,1,2]`.
     * 
     * Backtrack to `path= [algo](https://algo.monster/liteproblems/47)`,
     * `used=[T,F,F]`.
     * 
     * - Continue Depth 1 after finishing i=1:
     * 
     * - i = 2:
     * - `used [youtube](https://www.youtube.com/watch?v=qhBVWf0YafA)=false`.
     * - `i>0 && nums [youtube](https://www.youtube.com/watch?v=qhBVWf0YafA)==nums
     * [algo](https://algo.monster/liteproblems/47) && !used
     * [algo](https://algo.monster/liteproblems/47)` → `2>0 && 2==1` → false.
     * - Choose 2: `path=[1,2]`, `used=[T,F,T]`.
     * - Recurse.
     ** 
     * Depth 2 (path=):**
     * [geeksforgeeks](https://www.geeksforgeeks.org/dsa/next-permutation/)
     * 
     * - i=0: used.
     * - i=1:
     * - `used [algo](https://algo.monster/liteproblems/47)=false`.
     * - `i>0 && nums [algo](https://algo.monster/liteproblems/47)==nums[0] &&
     * !used[0]` → `1>0 && 1==1 && !T` → false.
     * - Choose 1 at index 1: `path=[1,2,1]` → add to result.
     * 
     * Backtrack back up:
     * 
     * - Eventually `result = [[1,1,2], [1,2,1], ...]`.
     * 
     * Now consider starting with i=1 at Depth 0:
     * 
     * - i=1:
     * - `used [algo](https://algo.monster/liteproblems/47)=false`.
     * - Check duplicate skip: `i>0 && nums
     * [algo](https://algo.monster/liteproblems/47)==nums[0] && !used[0]` → `1>0 &&
     * 1==1 && !false` → `true && true && true` → **true** → skip.
     * 
     * This prevents generating permutations where the “second 1” is chosen as the
     * first element at the same recursion depth as the “first 1”, which would lead
     * to duplicate permutations.
     * 
     * Continuing, the third unique permutation `[2,1,1]` is generated when we start
     * from `2` at index 2 and then pick the two `1`s.
     * 
     * Final `result` = `[[1,1,2], [1,2,1], [2,1,1]]`.
     ***
     * 
     * 
     * ### 4.2 Approach 2 – Backtracking with Swapping + Set per Level
     * 
     * Instead of `used[]`, we can use the **in-place swap** approach and avoid
     * duplicates by a `Set` at each recursion depth.
     * 
     * #### Algorithm
     * 
     * 1. Sort `nums` (helpful but not strictly necessary, though often used).
     * 2. Recursively define `backtrack(position)`:
     * - If `position == n`: record current array as permutation.
     * - Else:
     * - Create a `Set<Integer> seen` for this `position`.
     * - Loop `i` from `position` to `n-1`:
     * - If `nums[i]` is already in `seen`, skip (we already placed this value at
     * this position).
     * - Add `nums[i]` to `seen`.
     * - Swap `nums[position]` and `nums[i]`.
     * - Recurse `backtrack(position+1)`.
     * - Swap back.
     * 
     * This ensures that, at each index `position`, each **value** is used only
     * once, even if it appears multiple times in different indices.
     * [youtube](https://www.youtube.com/watch?v=is_T6uzlTyg)
     * 
     * #### Java Code
     * 
     * ```java
     * import java.util.ArrayList;
     * import java.util.HashSet;
     * import java.util.List;
     * import java.util.Set;
     * 
     * public class PermutationsII_Swap {
     * 
     * public List<List<Integer>> permuteUnique(int[] nums) {
     * List<List<Integer>> result = new ArrayList<>();
     * // Sorting optional but usually done for consistency
     * java.util.Arrays.sort(nums);
     * backtrack(nums, 0, result);
     * return result;
     * }
     * 
     * private void backtrack(int[] nums, int position,
     * List<List<Integer>> result) {
     * 
     * if (position == nums.length) {
     * List<Integer> perm = new ArrayList<>(nums.length);
     * for (int num : nums) {
     * perm.add(num);
     * }
     * result.add(perm);
     * return;
     * }
     * 
     * Set<Integer> seen = new HashSet<>();
     * 
     * for (int i = position; i < nums.length; i++) {
     * 
     * // If we have already placed this value at 'position', skip
     * if (seen.contains(nums[i])) {
     * continue;
     * }
     * seen.add(nums[i]);
     * 
     * swap(nums, position, i);
     * backtrack(nums, position + 1, result);
     * swap(nums, position, i); // backtrack
     * }
     * }
     * 
     * private void swap(int[] nums, int i, int j) {
     * if (i == j) return;
     * int tmp = nums[i];
     * nums[i] = nums[j];
     * nums[j] = tmp;
     * }
     * }
     * ```
     * 
     * #### Complexity
     * 
     * - Time: Same O(n · n!) in worst case; we still generate each unique
     * permutation exactly once, and we do constant extra work per recursion aside
     * from building permutations.
     * - Space:
     * - Recursion depth O(n).
     * - A `HashSet` at each level; worst-case storing up to n distinct values at
     * each level → O(n) extra across the stack.
     * - Overall auxiliary space O(n) (plus output).
     * 
     * #### Worked Example – `nums = [1,1,2]`
     * 
     * Initial (sorted): `[1,1,2]`, position=0.
     * 
     * - Level 0 (position=0), seen = {}
     * - i=0, `nums[0]=1`, not in seen → seen={1}
     * - swap(0,0) →
     * [geeksforgeeks](https://www.geeksforgeeks.org/dsa/next-permutation/)
     * - recurse position=1
     * 
     * - Level 1 (position=1), seen = {}
     * - i=1, `nums [algo](https://algo.monster/liteproblems/47)=1`, not in seen →
     * seen={1}
     * - swap(1,1) →
     * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
     * - recurse position=2
     * 
     * - Level 2 (position=2), seen = {}
     * - i=2, `nums [youtube](https://www.youtube.com/watch?v=qhBVWf0YafA)=2` →
     * seen={2}
     * - swap(2,2) →
     * [geeksforgeeks](https://www.geeksforgeeks.org/dsa/next-permutation/)
     * - position=3 → record `[1,1,2]`
     * - backtrack.
     * 
     * - i=2, `nums [youtube](https://www.youtube.com/watch?v=qhBVWf0YafA)=2`, not
     * in seen {1} → seen={1,2}
     * - swap(1,2) →
     * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
     * - position=2 → loop with i=2:
     * - swap(2,2), record `[1,2,1]`
     * - backtrack: swap(1,2) →
     * [geeksforgeeks](https://www.geeksforgeeks.org/dsa/next-permutation/)
     * 
     * - back to Level 0 (after i=0), swap(0,0) (no change).
     * 
     * - i=1, `nums [algo](https://algo.monster/liteproblems/47)=1`.
     * - But seen at Level 0 already has {1}, so skip.
     * This prevents starting a branch with the “second” 1 at index 1 at position 0.
     * 
     * - i=2, `nums [youtube](https://www.youtube.com/watch?v=qhBVWf0YafA)=2`, not
     * in seen {1} → seen={1,2}
     * - swap(0,2) →
     * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
     * - position=1:
     * - seen={}
     * - i=1: `1` → seen={1}, swap(1,1), position=2 → `[2,1,1]` → record.
     * - i=2: `1` again, but seen has 1 → skip, avoids duplicate again.
     * [geeksforgeeks](https://www.geeksforgeeks.org/dsa/next-permutation/)
     * 
     * Final result: `[[1,1,2], [1,2,1], [2,1,1]]`.
     ***
     * 
     * 
     * ### 4.3 Approach 3 – Generate All Permutations then Deduplicate (Not
     * Recommended)
     * 
     * #### Algorithm
     * 
     * 1. Use permutations algorithm from **Permutations I** (backtracking/swapping)
     * assuming all elements are distinct.
     * 2. Insert each resulting `List<Integer>` into a `Set<List<Integer>>` (e.g.,
     * `HashSet`) to enforce uniqueness.
     * 3. At the end, convert set back to a list.
     * 
     * #### Java Sketch
     * 
     * ```java
     * public class PermutationsII_DedupAfter {
     * 
     * public List<List<Integer>> permuteUnique(int[] nums) {
     * List<List<Integer>> allPerms = new ArrayList<>();
     * // Use standard permutations generation (assuming distinct)
     * generatePerms(nums, 0, allPerms);
     * 
     * // Deduplicate using a set
     * java.util.Set<List<Integer>> set = new java.util.HashSet<>(allPerms);
     * return new ArrayList<>(set);
     * }
     * 
     * private void generatePerms(int[] nums, int position, List<List<Integer>>
     * result) {
     * if (position == nums.length) {
     * List<Integer> perm = new ArrayList<>();
     * for (int num : nums) perm.add(num);
     * result.add(perm);
     * return;
     * }
     * for (int i = position; i < nums.length; i++) {
     * swap(nums, position, i);
     * generatePerms(nums, position + 1, result);
     * swap(nums, position, i);
     * }
     * }
     * 
     * private void swap(int[] nums, int i, int j) {
     * int tmp = nums[i]; nums[i] = nums[j]; nums[j] = tmp;
     * }
     * }
     * ```
     * 
     * #### Complexity
     * 
     * - Time:
     * - Still O(n · n!) to generate permutations (including duplicates).
     * - Additional O(k) insertion into set with hashing; k≈n!.
     * - Slightly more overhead than Approach 1 or 2; more memory churn.
     * - Space:
     * - Need to hold all permutations, including duplicates, in memory.
     * - Set + list duplicates → can be up to 2× the unique permutations size.
     * 
     * Because of the waste and higher risk of memory/time issues, this is **not
     * recommended** for interviews.
     ***
     * 
     * 
     * ## 6. Edge Cases
     * 
     * 1. **Single element:** `nums = [algo](https://algo.monster/liteproblems/47)`
     * - All approaches return `[ [algo](https://algo.monster/liteproblems/47)]`.
     * - Backtracking hits base immediately.
     * 
     * 2. **All identical elements:** `nums = [1,1,1]`
     * - Mathematically, only one unique permutation exists: `[[1,1,1]]`.
     * - Approach 1:
     * - Sorting keeps `[1,1,1]`.
     * - At each depth, skip rule ensures only one branch.
     * - Approach 2:
     * - Set at each position ensures value `1` is only used once per position.
     * - Approach 3:
     * - Generates 3! = 6 identical permutations and deduplicates to one.
     * 
     * 3. **No duplicates (same as Permutations I)**: `nums = [1,2,3]`
     * - Approaches 1 and 2 produce all 6 permutations, same as Permutations I.
     * - Skip conditions never trigger because no equal neighbors/values.
     * 
     * 4. **Negative values / zeros:** `nums = [0,1,1]`, `[-1,-1,2]`
     * - Values don’t matter; only equality and index usage matters, which is
     * handled via sorting + skip or sets.
     * 
     * 5. **Maximum length (e.g., n = 8)**:
     * - Worst-case number of unique permutations with all distinct elements = 8! =
     * 40320.
     * - Approaches 1 and 2 can handle it; typical for constraints.
     ***
     * 
     * 
     * ## 7. Final Summary
     * 
     * - **Goal:** generate all **unique** permutations when `nums` may contain
     * duplicates.
     * - **Key difficulty:** avoid duplicate permutations due to repeated values.
     ** 
     * Approaches:**
     * 
     * - **Approach 1 (Sorted + used[] + skip rule)**:
     * - Sort input, and in DFS, skip `nums[i]` when `nums[i] == nums[i-1] &&
     * !used[i-1]`.
     * - Clean, standard backtracking solution.
     * - Recommended for interviews and practice.
     * - **Approach 2 (Swap + per-level Set)**:
     * - In-place permutations with a `Set` to avoid using same value at the same
     * position multiple times.
     * - Also good, but uses extra small set per recursion level.
     * - **Approach 3 (Generate & dedup)**:
     * - Conceptual, but inefficient.
     ** 
     * What to remember:**
     * 
     * > This problem is a classic example of **backtracking with duplicate
     * handling**.
     * > The core pattern is: **sort** the input and, at each recursion depth,
     * **skip** choosing the same value twice (or use a set per level) to avoid
     * generating duplicate branches.
     * 
     * If you’d like, next we can dry-run the `used[]` solution step-by-step on a
     * trickier input like `[1,1,2,2]` to see exactly how the skip rule prunes
     * duplicate permutations.
     */
}
