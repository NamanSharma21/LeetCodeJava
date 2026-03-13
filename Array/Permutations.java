package Array;

import java.util.ArrayList;
import java.util.List;

public class Permutations {
    public static void main(String[] args) {
        Permutations permutations = new Permutations();
        System.out.println("Permutations : " + permutations.permute(new int[] { 1, 2, 3 }));
        System.out.println("Permutations : " + permutations.permute(new int[] { 1, 1, 2 }));
    }

    /**
     * 
     * https://leetcode.com/problems/permutations/description/?envType=problem-list-v2&envId=array
     * 
     * Given an array nums of distinct integers, return all the possible
     * permutations. You can return the answer in any order.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,2,3]
     * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     * Example 2:
     * 
     * Input: nums = [0,1]
     * Output: [[0,1],[1,0]]
     * Example 3:
     * 
     * Input: nums = [1]
     * Output: [[1]]
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 6
     * -10 <= nums[i] <= 10
     * All the integers of nums are unique.
     * 
     */

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backTrack(nums, 0, result);
        return result;
    }

    private void backTrack(int[] nums, int position, List<List<Integer>> result) {
        if (position == nums.length) {
            List<Integer> temp = new ArrayList<>();
            for (int num : nums) {
                temp.add(num);
            }
            result.add(temp);
            return;
        }

        for (int i = position; i < nums.length; i++) {
            swap(nums, position, i);
            backTrack(nums, position + 1, result);
            swap(nums, position, i);
        }
    }

    public void swap(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }

    /**
     * 
     * ## 1. Problem Statement
     ** 
     * Name:** Permutations (LeetCode 46)
     * 
     * You are given an array `nums` of **distinct integers**.
     * 
     * - You must generate **all possible permutations** of these integers.
     * - Each permutation:
     * - Uses **every element exactly once**.
     * - Has length `nums.length`.
     * - The permutations can be returned in **any order**.
     * [leetcode](https://leetcode.com/problems/permutations/)
     * 
     * ### Input / Output
     * 
     * - **Input:** `int[] nums`
     * - Typical constraints:
     * - `1 <= nums.length <= 6` (sometimes up to 8)
     * - `-10 <= nums[i] <= 10`
     * - All `nums[i]` are **unique**. [algo](https://algo.monster/liteproblems/46)
     * - **Output:** `List<List<Integer>>`
     * - A list containing **all permutations** of `nums`.
     * 
     * You need to **compute and return the full list of permutations**, not just
     * count them or check existence. For an array of length `n`, there are exactly
     * `n!` permutations. [algo](https://algo.monster/liteproblems/46)
     ***
     * 
     * 
     * ## 2. Intuition
     * 
     * Permutation = **reordering of all elements**.
     * 
     * For `nums = [1,2,3]`, permutations are:
     * 
     * - `[1,2,3]`
     * - `[1,3,2]`
     * - `[2,1,3]`
     * - `[2,3,1]`
     * - `[3,1,2]`
     * - `[3,2,1]`
     * 
     * How a human would think:
     * 
     * - First position: choose any of the 3 numbers.
     * - Second position: choose any of the remaining 2.
     * - Third position: only 1 number left.
     * - That branching structure is naturally **recursive**.
     * [algo](https://algo.monster/liteproblems/46)
     * 
     * This suggests:
     * 
     * - Build permutations **position by position**.
     * - At each position, try each number that hasn’t been used yet.
     * - After placing it, go to the next position.
     * - When all positions are filled, record that permutation.
     * - Then **backtrack**: undo last choice, try another number.
     * 
     * What makes the problem interesting:
     * 
     * - Output size is **factorial**; you can’t do better than O(n · n!) time
     * overall because you have to **output** n! permutations.
     * - The challenge is expressing the recursion/backtracking cleanly:
     * - Either via a **used[]** array.
     * - Or via **in-place swapping**.
     * - It’s a fundamental backtracking template reused in many problems (subsets,
     * combinations, permutations, etc.).
     * [algo](https://algo.monster/liteproblems/46)
     ***
     * 
     * 
     * ## 3. Approach Overview
     * 
     * All reasonable approaches essentially do **backtracking / DFS**, but with
     * slightly different implementation styles.
     * 
     * ### Approach 1 – Backtracking with `used[]` (canonical, very clear)
     * 
     * - **Key idea:** Keep a `List<Integer> path` representing the current partial
     * permutation and a `boolean[] used` to mark which indices are already in
     * `path`.
     * - **When to use:** Standard “clean” version; great in interviews.
     * - **Optimal?** Yes; time is O(n · n!), which is optimal up to a constant, and
     * space O(n) (ignoring output).
     * 
     * ### Approach 2 – Backtracking with in-place swapping
     * 
     * - **Key idea:** Permute the array in-place: fix a `position` and swap each
     * index `i >= position` into that position, recurse, then swap back.
     * - **When to use:** Also standard; shows you understand in-place generation.
     * - **Optimal?** Same complexity; constant additional space beyond recursion
     * and result.
     * 
     * ### Approach 3 – Next-Permutation–based iterative generation
     * 
     * - **Key idea:** Sort `nums` to the smallest lexicographic permutation, then
     * repeatedly generate the next lexicographic permutation (like
     * `nextPermutation`) until no more.
     * - **When to use:** Good if you already have `nextPermutation` helper;
     * conceptually more complex.
     * - **Optimal?** Same asymptotic cost but more overhead and trickier to
     * implement correctly in an interview.
     * 
     * In practice, **Approach 1 (used[] + path)** or **Approach 2 (swap)** is
     * recommended.
     ***
     * 
     * 
     * ## 4. Detailed Solutions in Java
     * 
     * ### 4.1 Approach 1 – Backtracking with `used[]` (Recommended)
     * 
     * #### Algorithm (step-by-step)
     * 
     * 1. Let `n = nums.length`.
     * 2. Maintain:
     * - `List<List<Integer>> result` – to store all permutations.
     * - `List<Integer> path` – current partial permutation.
     * - `boolean[] used` – `used[i] == true` if `nums[i]` is already in `path`.
     * 3. Define a recursive method `backtrack()`:
     * - If `path.size() == n`:
     * - Add a **copy** of `path` to `result`.
     * - Return.
     * - Otherwise:
     * - For each index `i` from 0 to n-1:
     * - If `used[i]` is true, skip (number already used).
     * - Otherwise:
     * - Mark `used[i] = true`.
     * - Add `nums[i]` to `path`.
     * - Recurse `backtrack()`.
     * - After recursion, **backtrack**:
     * - Remove last element from `path`.
     * - Set `used[i] = false`.
     * 
     * #### Java Code
     * 
     * ```java
     * import java.util.ArrayList;
     * import java.util.List;
     * 
     * public class PermutationsUsedArray {
     * 
     * public List<List<Integer>> permute(int[] nums) {
     * List<List<Integer>> result = new ArrayList<>();
     * int n = nums.length;
     * 
     * boolean[] used = new boolean[n];
     * List<Integer> path = new ArrayList<>();
     * 
     * backtrack(nums, used, path, result);
     * return result;
     * }
     * 
     * private void backtrack(int[] nums, boolean[] used,
     * List<Integer> path,
     * List<List<Integer>> result) {
     * 
     * // If the current permutation has length n, record it
     * if (path.size() == nums.length) {
     * result.add(new ArrayList<>(path)); // copy
     * return;
     * }
     * 
     * // Try every unused number at the current position
     * for (int i = 0; i < nums.length; i++) {
     * if (used[i]) {
     * continue; // skip numbers already in the current path
     * }
     * 
     * // Choose nums[i]
     * used[i] = true;
     * path.add(nums[i]);
     * 
     * // Recurse to fill the next position
     * backtrack(nums, used, path, result);
     * 
     * // Backtrack: undo the choice
     * path.remove(path.size() - 1);
     * used[i] = false;
     * }
     * }
     * }
     * ```
     * 
     * #### Time & Space Complexity
     * 
     * - **Time:**
     * - There are `n!` permutations.
     * - For each permutation, we do O(n) work to build it (or to traverse recursion
     * levels).
     * - Total = **O(n · n!)**. [algo](https://algo.monster/liteproblems/46)
     * - **Space:**
     * - `used[]` is size n → O(n).
     * - `path` size ≤ n → O(n).
     * - Recursion depth ≤ n → call stack O(n).
     * - Result list stores all permutations: O(n · n!), which is required output,
     * normally not counted as extra.
     * - Auxiliary (excluding output): **O(n)**.
     * [algo](https://algo.monster/liteproblems/46)
     * 
     * For example:
     * 
     * - n = 3 → 6 permutations; about 6 · 3 = 18 placements.
     * - n = 6 → 720 permutations; about 720 · 6 ≈ 4320 placements.
     * 
     * #### Worked Example – `nums = [1, 2, 3]`
     * 
     * We build permutations as a recursion tree.
     * 
     * Start:
     * - `path = []`, `used = [F, F, F]`.
     * 
     * 1. Choose i=0 → use 1:
     * - `path = [leetcode](https://leetcode.com/problems/permutations/)`, `used =
     * [T, F, F]`.
     * - Recurse:
     * - i=0 → used, skip.
     * - i=1 → use 2:
     * - `path = [1,2]`, `used = [T, T, F]`.
     * - Recurse:
     * - i=0,1 used.
     * - i=2 → use 3:
     * - `path = [1,2,3]` (size==3) → add `[1,2,3]` to result.
     * - Backtrack: remove 3 → `path=[1,2]`, `used
     * [leetcode](https://leetcode.com/problems/next-permutation/)=F`.
     * - Next i=2:
     * - use 3:
     * - `path = [1,3]`, etc. → you eventually get `[1,3,2]`.
     * - Backtrack to `path=
     * [leetcode](https://leetcode.com/problems/permutations/)`, `used=[T,F,F]`.
     * 
     * 2. Backtrack fully from i=0:
     * - `path=[]`, `used=[F,F,F]`.
     * - i=1 → use 2 as first:
     * - `path= [leetcode](https://leetcode.com/problems/next-permutation/)`...
     * - Similarly generate `[2,1,3]`, `[2,3,1]`.
     * - i=2 → start with 3 as first:
     * - `[3,1,2]`, `[3,2,1]`.
     * 
     * Final result:
     * `[[1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]]`.
     ***
     * 
     * 
     * ### 4.2 Approach 2 – Backtracking with In-Place Swapping
     * 
     * This avoids a `used[]` array; instead you **rearrange the array** as you go.
     * 
     * #### Algorithm (step-by-step)
     * 
     * Let `n = nums.length`.
     * 
     * 1. Define recursive method `backtrack(position)`:
     * - `position` is the index we’re currently filling.
     * 2. If `position == n`:
     * - We have a complete permutation in `nums`.
     * - Copy `nums` into a list and add to `result`.
     * 3. Else:
     * - For every index `i` from `position` to `n-1`:
     * - Swap `nums[position]` with `nums[i]` (place `nums[i]` at `position`).
     * - Recurse with `backtrack(position + 1)`.
     * - Swap back (undo) to restore original order for next iterations.
     * 
     * Because the input consists of **distinct** integers, we don’t need
     * duplicate-handling logic (that’s needed in Permutations II, not here).
     * [faun](https://faun.pub/leetcode-permutations-6ac8de2b3dba)
     * 
     * #### Java Code
     * 
     * ```java
     * import java.util.ArrayList;
     * import java.util.List;
     * 
     * public class PermutationsSwap {
     * 
     * public List<List<Integer>> permute(int[] nums) {
     * List<List<Integer>> result = new ArrayList<>();
     * backtrack(nums, 0, result);
     * return result;
     * }
     * 
     * private void backtrack(int[] nums, int position,
     * List<List<Integer>> result) {
     * 
     * // If position reached the end, we have a full permutation
     * if (position == nums.length) {
     * List<Integer> perm = new ArrayList<>();
     * for (int num : nums) {
     * perm.add(num);
     * }
     * result.add(perm);
     * return;
     * }
     * 
     * // For each index i, swap nums[position] with nums[i]
     * for (int i = position; i < nums.length; i++) {
     * swap(nums, position, i);
     * backtrack(nums, position + 1, result);
     * swap(nums, position, i); // backtrack (restore array)
     * }
     * }
     * 
     * private void swap(int[] nums, int i, int j) {
     * if (i == j) return;
     * int temp = nums[i];
     * nums[i] = nums[j];
     * nums[j] = temp;
     * }
     * }
     * ```
     * 
     * #### Time & Space Complexity
     * 
     * - **Time:** Same logic as before – O(n · n!) permutations and work per
     * permutation.
     * - **Space:**
     * - Recursion depth O(n).
     * - No `used[]`, but we still store `result`.
     * - Auxiliary (excluding output) = **O(n)**.
     * 
     * #### Worked Example – `nums = [1, 2, 3]`
     * 
     * We trace `position`:
     * 
     * - `position = 0`, nums =
     * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
     * - i=0:
     * - swap(0,0): nums=
     * [geeksforgeeks](https://www.geeksforgeeks.org/dsa/next-permutation/)
     * - `position=1`:
     * - i=1:
     * - swap(1,1): → recurse position=2:
     * [youtube](https://www.youtube.com/watch?v=cuq7XXxYYOY)
     * - i=2:
     * - swap(2,2): → position=3 → record.
     * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
     * - backtrack:.
     * [geeksforgeeks](https://www.geeksforgeeks.org/dsa/next-permutation/)
     * - i=2:
     * - swap(1,2): → position=2:
     * [youtube](https://www.youtube.com/watch?v=cuq7XXxYYOY)
     * - swap(2,2): → record.
     * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
     * - backtrack: swap(1,2) →.
     * [geeksforgeeks](https://www.geeksforgeeks.org/dsa/next-permutation/)
     * - i=1:
     * - swap(0,1): → position=1:
     * [youtube](https://www.youtube.com/watch?v=cuq7XXxYYOY)
     * - i=1: → position=2 → record.
     * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
     * - i=2: swap(1,2): → record.
     * [geeksforgeeks](https://www.geeksforgeeks.org/dsa/next-permutation/)
     * - backtrack: swap(0,1) →.
     * [youtube](https://www.youtube.com/watch?v=cuq7XXxYYOY)
     * - i=2:
     * - swap(0,2): → continue similarly for and.
     * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
     * 
     * Result is same set of permutations.
     ***
     * 
     * 
     * ### 4.3 Approach 3 – Next-Permutation Iterative
     * 
     * This is less common in interviews but good conceptually.
     * 
     * #### Algorithm
     * 
     * 1. Sort `nums` to get the **smallest lexicographic order**.
     * 2. Add the current permutation to `result`.
     * 3. Repeatedly:
     * - Generate the **next lexicographic permutation** using the standard
     * `nextPermutation` algorithm (like in LeetCode 31).
     * - After each successful generation, add copy of the new permutation to
     * `result`.
     * - Stop when you cycle back or detect no next permutation (array is in
     * descending order).
     * 
     * The `nextPermutation` subroutine:
     * 
     * 1. Find the rightmost index `i` where `nums[i] < nums[i+1]`. If none, you’re
     * at last permutation.
     * 2. Find rightmost `j > i` where `nums[j] > nums[i]`.
     * 3. Swap `nums[i]` and `nums[j]`.
     * 4. Reverse the segment `nums[i+1..end]`.
     * 
     * Repeatedly applying this enumerates all permutations in lexicographic order.
     * [leetcode](https://leetcode.com/problems/next-permutation/)
     * 
     * #### Java Sketch
     * 
     * ```java
     * import java.util.*;
     * 
     * public class PermutationsNext {
     * 
     * public List<List<Integer>> permute(int[] nums) {
     * List<List<Integer>> result = new ArrayList<>();
     * 
     * Arrays.sort(nums);
     * result.add(toList(nums));
     * 
     * while (nextPermutation(nums)) {
     * result.add(toList(nums));
     * }
     * 
     * return result;
     * }
     * 
     * private List<Integer> toList(int[] nums) {
     * List<Integer> list = new ArrayList<>(nums.length);
     * for (int num : nums) list.add(num);
     * return list;
     * }
     * 
     * // Returns false if no next permutation (nums is in descending order)
     * private boolean nextPermutation(int[] nums) {
     * int n = nums.length;
     * int i = n - 2;
     * 
     * // 1) Find pivot
     * while (i >= 0 && nums[i] >= nums[i + 1]) {
     * i--;
     * }
     * if (i < 0) {
     * return false; // already highest permutation
     * }
     * 
     * // 2) Find successor
     * int j = n - 1;
     * while (nums[j] <= nums[i]) {
     * j--;
     * }
     * 
     * // 3) Swap pivot and successor
     * swap(nums, i, j);
     * 
     * // 4) Reverse suffix
     * reverse(nums, i + 1, n - 1);
     * return true;
     * }
     * 
     * private void swap(int[] nums, int i, int j) {
     * int tmp = nums[i]; nums[i] = nums[j]; nums[j] = tmp;
     * }
     * 
     * private void reverse(int[] nums, int left, int right) {
     * while (left < right) {
     * swap(nums, left++, right--);
     * }
     * }
     * }
     * ```
     * 
     * #### Complexity
     * 
     * - Generating each next permutation is O(n).
     * - There are `n!` permutations.
     * - Total time: **O(n · n!)**, same as backtracking.
     * - Space: O(1) extra (besides output), plus temporary list copies.
     * 
     * This is more code and more prone to bugs, so backtracking is usually
     * preferred.
     ***
     * 
     * 
     * ## 5. Edge Cases
     * 
     * 1. **Empty array** (`nums.length == 0`)
     * - LeetCode constraints usually avoid this (length ≥ 1).
     * - If allowed, mathematically, there is 1 permutation: empty list `[]`.
     * - Backtracking:
     * - `path.size() == 0 == n` triggers base case, returns `[[]]`.
     * 
     * 2. **Single element**: `nums = [algo](https://algo.monster/liteproblems/46)`
     * - Only one permutation: `[ [algo](https://algo.monster/liteproblems/46)]`.
     * - Backtracking: one recursive call, base triggers quickly.
     * - Swap method: position 0, i=0, then position=1 → record `
     * [algo](https://algo.monster/liteproblems/46)`.
     * 
     * 3. **Already sorted / unsorted**:
     * - Order of `nums` doesn’t matter for backtracking; all permutations will be
     * generated.
     * - Next-permutation approach demands initial sort to enumerate in
     * lexicographic order.
     * 
     * 4. **Negative values, large values**:
     * - Values themselves don’t matter; we only permute positions, no arithmetic
     * constraints.
     * - All approaches handle them identically.
     * 
     * 5. **Duplicates**:
     * - This problem version assumes **distinct** integers.
     * [leetcode](https://leetcode.com/problems/permutations/)
     * - If duplicates exist → you must handle dedup (that is LeetCode 47:
     * Permutations II).
     * - The plain approaches here will generate duplicate permutations in that
     * case.
     * 
     * 6. **Maximum size (e.g., n = 6 or 8)**:
     * - n = 6 → 720 permutations; n = 8 → 40320 permutations.
     * - Time and memory grow quickly; but constraints are chosen so that O(n · n!)
     * is acceptable.
     ***
     * 
     * 
     * ## 6. Final Summary
     * 
     * - All approaches fundamentally explore the **permutation tree** with
     * branching factor decreasing from n to 1 and depth n.
     * - **Backtracking with `used[]`**:
     * - Very clear, directly matches the “choose/mark/recurse/unmark” pattern.
     * - Recommended in interviews.
     * - **Backtracking with swapping**:
     * - In-place, avoids `used[]`.
     * - Also common and efficient.
     * - **Next-permutation iterative**:
     * - Elegant if you already know/need lexicographic order.
     * - More moving parts (pivot, successor, reverse).
     * 
     * What to remember:
     * 
     * - Generating permutations is a canonical use-case for **DFS + backtracking**.
     * - Core template:
     * - “Choose an unused element → add → recurse → remove → mark unused again.”
     * - The complexity is inherently **O(n · n!)**, dominated by the number of
     * permutations you must output.
     * 
     * If you want, next we can adapt this to **Permutations II** (handling
     * duplicates) or walk through your own Java implementation on a custom array
     * step by step.
     * 
     */
}
