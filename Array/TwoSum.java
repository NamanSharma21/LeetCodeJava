package Array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public static void main(String[] args) {
        TwoSum twoSum = new TwoSum();
        System.out.println("TwoSum : " + Arrays.toString(twoSum.twoSumSortTwoPointers(new int[] { 2, 7, 11, 15 }, 9)));
        System.out.println("TwoSum : " + Arrays.toString(twoSum.twoSumSortTwoPointers(new int[] { 3, 2, 4 }, 6)));
        System.out.println("TwoSum : " + Arrays.toString(twoSum.twoSumSortTwoPointers(new int[] { 3, 3 }, 6)));
        System.out.println("-----------------------------");
        System.out.println("TwoSum : " + Arrays.toString(twoSum.twoSumBruteForce(new int[] { 2, 7, 11, 15 }, 9)));
        System.out.println("TwoSum : " + Arrays.toString(twoSum.twoSumBruteForce(new int[] { 3, 2, 4 }, 6)));
        System.out.println("TwoSum : " + Arrays.toString(twoSum.twoSumBruteForce(new int[] { 3, 3 }, 6)));
        System.out.println("-----------------------------");
        System.out.println("TwoSum : " + Arrays.toString(twoSum.twoSumSortTwoPointers(new int[] { 2, 7, 11, 15 }, 9)));
        System.out.println("TwoSum : " + Arrays.toString(twoSum.twoSumSortTwoPointers(new int[] { 3, 2, 4 }, 6)));
        System.out.println("TwoSum : " + Arrays.toString(twoSum.twoSumSortTwoPointers(new int[] { 3, 3 }, 6)));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/two-sum/
     * Given an array of integers nums and an integer target, return indices of the
     * two numbers such that they add up to target.
     * 
     * You may assume that each input would have exactly one solution, and you may
     * not use the same element twice.
     * 
     * You can return the answer in any order.
     * Example 1:
     * 
     * Input: nums = [2,7,11,15], target = 9
     * Output: [0,1]
     * Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
     * Example 2:
     * 
     * Input: nums = [3,2,4], target = 6
     * Output: [1,2]
     * Example 3:
     * 
     * Input: nums = [3,3], target = 6
     * Output: [0,1]
     * 
     * 
     * Constraints:
     * 
     * 2 <= nums.length <= 104
     * -109 <= nums[i] <= 109
     * -109 <= target <= 109
     * Only one valid answer exists.
     * 
     * 
     * Follow-up: Can you come up with an algorithm that is less than O(n2) time
     * complexity?
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * 
     *| Approach            | Time       | Space | Code Complexity | Recommended?                    |
     *|---------------------|------------|-------|-----------------|----------------------------     |
     *| Brute Force         | O(n^2)     | O(1)  | Very simple     | ✅ best for low memory          |
     * 
     * @param nums
     * @param target
     * @return
     */
    // @formatter:on

    public int[] twoSumBruteForce(int[] nums, int target) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int first = nums[i];
            for (int y = i + 1; y < n; y++) {
                if (first + nums[y] == target) {
                    return new int[] { i, y };
                }
            }
        }
        return null;
    }

    // @formatter:off
    /**
     * 
     * 
     *| Approach            | Time       | Space | Code Complexity | Recommended?                    |
     *|---------------------|------------|-------|-----------------|----------------------------     |
     *| Sort + Two Pointers | O(n log n) | O(n)  | Moderate        | ❌ awkward (index bookkeep)     |
     * 
     * @param nums
     * @param target
     * @return
     */
    // @formatter:on
    public int[] twoSumSortTwoPointers(int[] nums, int target) {
        int n = nums.length;
        int[][] valueWithIndex = new int[n][2];
        for (int i = 0; i < n; i++) {
            valueWithIndex[i][0] = nums[i];
            valueWithIndex[i][1] = i;
        }

        Arrays.sort(valueWithIndex, (a, b) -> Integer.compare(a[0], b[0]));
        int left = 0, right = n - 1;
        while (left < right) {
            int sum = valueWithIndex[left][0] + valueWithIndex[right][0];
            if (sum == target) {
                return new int[] { valueWithIndex[left][1], valueWithIndex[right][1] };
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[] {};
    }

    // @formatter:off
    /**
     * 
     * 
     *| Approach            | Time       | Space | Code Complexity | Recommended?                    |
     *|---------------------|------------|-------|-----------------|----------------------------     |
     *| Hash Map (one pass) | O(n)       | O(n)  | Simple          | ✅✅ best for time             |
     * 
     * @param nums
     * @param target
     * @return
     */
    // @formatter:on
    public int[] twoSumHashMap(int[] nums, int target) {
        Map<Integer, Integer> numMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (numMap.containsKey(target - nums[i])) {
                return new int[] { i, numMap.get(target - nums[i]) };
            }
            numMap.put(nums[i], i);
        }
        return new int[] {};
    }
}

// @formatter:off
/*
 * ============================================================
 * TWO SUM — DEEP DIVE EXPLANATION
 * ============================================================
 *
 * LeetCode #1 | Difficulty: Easy
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an array of integers and a target integer, find the two
 * elements in the array that add up exactly to the target, and
 * return their POSITIONS (indices). Exactly one valid pair is
 * guaranteed, and the same element cannot be used twice.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 *  - int[] nums   : array of integers (may contain negatives/duplicates)
 *  - int target   : the sum we are trying to hit
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 *  - int[] of length 2: indices i, j with nums[i] + nums[j] == target
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 *  - 2 <= nums.length <= 10^4
 *  - -10^9 <= nums[i] <= 10^9
 *  - -10^9 <= target <= 10^9
 *  - Exactly one valid answer exists
 *  - Same element cannot be reused (i != j)
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * The INDICES of the two values (not the values themselves).
 * This matters: sorting destroys original indices.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    nums   = [2, 7, 11, 15]
 *    target = 9
 *    2 + 7 == 9  ->  indices 0 and 1
 *    Output: [0, 1]
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * For every number x, exactly one number completes it: the
 * COMPLEMENT = target - x. The problem reduces to one repeated
 * question: "Have I already seen the number I need?"
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 *  1. Pick up a number; ask what other number reaches the target.
 *  2. Keep a NOTEBOOK of every number seen so far + its position.
 *  3. For each new number, glance at the notebook for its
 *     complement. Found -> done. Not found -> jot it down, move on.
 * Trade repeated searching for one-time remembering: O(1) lookups.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                    | Why it's tricky                                   |
 * |------------------------------|---------------------------------------------------|
 * | Return indices, not values   | Sorting loses original positions                  |
 * | Same element can't reuse     | Order of check-then-insert matters                |
 * | Duplicate values             | value-keyed map can overwrite; check-first fixes  |
 * | Negatives / large values     | Complement can be negative; reason about overflow |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                | Key Idea                          | Best Used When                    | Time       | Space          |
 * |---|-------------------------|-----------------------------------|-----------------------------------|------------|----------------|
 * | 1 | Brute Force (nested)    | Test every pair (i, j)            | n tiny, or O(1) memory required   | O(n^2)     | O(1) [SPACE ✅]|
 * | 2 | Sort + Two Pointers     | Sort, converge from both ends     | Only values needed / already sort | O(n log n) | O(n)           |
 * | 3 | Hash Map (one pass)     | Store value->index; look up compl | General case, speed matters       | O(n) [TIME ✅]| O(n)         |
 *
 * Neither extreme dominates both axes. Approach 1 wins on space
 * (O(1)), Approach 3 wins on time (O(n)). Approach 2 is a distinct
 * middle (sort + two-pointer) but awkward here since indices are
 * required. PREFER the hash map by default; use brute force only
 * when n is small AND memory must stay O(1).
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Algorithm:
 *  1. Loop i from 0..n-1.
 *  2. Loop j from i+1..n-1 (start at i+1 -> no reuse, no dup pairs).
 *  3. If nums[i] + nums[j] == target, return {i, j}.
 *  4. Otherwise return a sentinel.
 *
 *    import java.util.Arrays;
 *
 *    public class TwoSumBruteForce {
 *        public static int[] twoSum(int[] nums, int target) {
 *            int n = nums.length;
 *            for (int i = 0; i < n; i++) {
 *                for (int j = i + 1; j < n; j++) {
 *                    if (nums[i] + nums[j] == target) {
 *                        return new int[] { i, j };
 *                    }
 *                }
 *            }
 *            return new int[] {};
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = { 2, 7, 11, 15 };
 *            System.out.println(Arrays.toString(twoSum(nums, 9))); // [0, 1]
 *        }
 *    }
 *
 * Starting the inner loop at j = i + 1 guarantees i != j and avoids
 * checking each unordered pair twice.
 *
 * ------------------------------------------------------------
 * Approach 2: Sort + Two Pointers
 * ------------------------------------------------------------
 * Algorithm:
 *  1. Build (value, originalIndex) pairs (sorting raw array loses indices).
 *  2. Sort pairs by value.
 *  3. left = 0, right = n-1.
 *  4. sum = value[left] + value[right]:
 *       == target -> return original indices
 *       <  target -> left++
 *       >  target -> right--
 *  5. Repeat until pointers meet.
 *
 *    import java.util.Arrays;
 *
 *    public class TwoSumTwoPointers {
 *        public static int[] twoSum(int[] nums, int target) {
 *            int n = nums.length;
 *            int[][] valueWithIndex = new int[n][2];
 *            for (int i = 0; i < n; i++) {
 *                valueWithIndex[i][0] = nums[i];
 *                valueWithIndex[i][1] = i;
 *            }
 *            Arrays.sort(valueWithIndex, (a, b) -> Integer.compare(a[0], b[0]));
 *            int left = 0, right = n - 1;
 *            while (left < right) {
 *                int sum = valueWithIndex[left][0] + valueWithIndex[right][0];
 *                if (sum == target) {
 *                    return new int[] { valueWithIndex[left][1], valueWithIndex[right][1] };
 *                } else if (sum < target) {
 *                    left++;
 *                } else {
 *                    right--;
 *                }
 *            }
 *            return new int[] {};
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = { 3, 2, 4 };
 *            System.out.println(Arrays.toString(twoSum(nums, 6))); // [1, 2]
 *        }
 *    }
 *
 * Returned indices point into the ORIGINAL array via the stored
 * second column. Pair order may differ but any valid pair is accepted.
 *
 * ------------------------------------------------------------
 * Approach 3: Hash Map (one pass)  ✅ OPTIMAL (time)
 * ------------------------------------------------------------
 * Algorithm:
 *  1. Empty HashMap<Integer,Integer> mapping value -> index.
 *  2. Scan left to right; complement = target - nums[i].
 *  3. CHECK FIRST: if map has complement, return { map.get(complement), i }.
 *  4. THEN INSERT: put nums[i] -> i.
 *  5. Check-before-insert prevents self-pairing and handles duplicates.
 *
 *    import java.util.HashMap;
 *    import java.util.Map;
 *    import java.util.Arrays;
 *
 *    public class TwoSumHashMap {
 *        public static int[] twoSum(int[] nums, int target) {
 *            Map<Integer, Integer> valueToIndex = new HashMap<>();
 *            for (int i = 0; i < nums.length; i++) {
 *                int complement = target - nums[i];
 *                if (valueToIndex.containsKey(complement)) {
 *                    return new int[] { valueToIndex.get(complement), i };
 *                }
 *                valueToIndex.put(nums[i], i);
 *            }
 *            return new int[] {};
 *        }
 *
 *        public static void main(String[] args) {
 *            System.out.println(Arrays.toString(twoSum(new int[] { 2, 7, 11, 15 }, 9))); // [0, 1]
 *            System.out.println(Arrays.toString(twoSum(new int[] { 3, 2, 4 }, 6)));      // [1, 2]
 *            System.out.println(Arrays.toString(twoSum(new int[] { 3, 3 }, 6)));         // [0, 1]
 *        }
 *    }
 *
 * Check-before-insert is the correctness key: reaching the second 3
 * in [3,3], the first 3 (index 0) is already stored, pairing with
 * index 1. Index 0 was never matched with itself.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Approach 1 (Brute Force)
 *  - Time:  O(n^2). n(n-1)/2 ~ n^2/2 pair checks.
 *  - Space: O(1). Only index variables.
 *  - Feel:  n=100 -> ~4,950 checks; n=10,000 -> ~50 million checks.
 *
 * Approach 2 (Sort + Two Pointers)
 *  - Time:  O(n log n), dominated by the sort; sweep is O(n).
 *  - Space: O(n) for (value, index) pairs.
 *  - Feel:  n=10,000 -> ~133,000 sort comparisons + 10,000 sweep.
 *
 * Approach 3 (Hash Map)
 *  - Time:  O(n). One pass; each map op amortized O(1).
 *  - Space: O(n). Worst case holds n-1 entries.
 *  - Feel:  n=10,000 -> ~10,000 map ops (~5000x fewer than brute).
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * Approach 1 — Brute Force   nums = [3, 2, 4], target = 6
 *   i=0 (3)
 *    ├─ j=1 (2): 3+2=5 != 6
 *    └─ j=2 (4): 3+4=7 != 6
 *   i=1 (2)
 *    └─ j=2 (4): 2+4=6 ✅ -> return [1, 2]
 *   Output: [1, 2]
 *
 * Approach 2 — Sort + Two Pointers   nums = [3, 2, 4], target = 6
 *   Pairs: (3,0) (2,1) (4,2)
 *   Sorted: (2,1) (3,0) (4,2)
 *            L           R
 *   left=0 (2), right=2 (4): 2+4=6 == target ✅ -> [1, 2]
 *   Output: [1, 2]
 *
 * Approach 3 — Hash Map   nums = [2, 7, 11, 15], target = 9
 *   | Step | i | nums[i] | complement | map before | in map? | action     |
 *   |------|---|---------|------------|------------|---------|------------|
 *   | 1    | 0 | 2       | 7          | {}         | no      | put 2->0   |
 *   | 2    | 1 | 7       | 2          | {2:0}      | YES     | return[0,1]|
 *   Output: [0, 1]
 *
 *   Duplicate check   nums = [3, 3], target = 6
 *   | Step | i | nums[i] | complement | map before | in map? | action     |
 *   |------|---|---------|------------|------------|---------|------------|
 *   | 1    | 0 | 3       | 3          | {}         | no      | put 3->0   |
 *   | 2    | 1 | 3       | 3          | {3:0}      | YES     | return[0,1]|
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                         | Input                     | Expected | How Handled                          |
 * |-----------------------------------|---------------------------|----------|--------------------------------------|
 * | Minimum length array              | [1,2], target=3           | [0,1]    | Only one pair; checked directly      |
 * | Duplicate values form the pair    | [3,3], target=6           | [0,1]    | Check-before-insert finds earlier 3  |
 * | Negative numbers                  | [-3,4,3,90], target=0     | [0,2]    | Complement math works for negatives  |
 * | Answer uses last element          | [1,5,2,8], target=10      | [2,3]    | Loop reaches final index             |
 * | Complement == value, distinct idx | [0,4,0], target=0         | [0,2]    | Two 0s at different indices paired    |
 *
 * Potential Pitfalls
 *   Reusing same element (brute force):
 *     WRONG:   for (int j = i;     j < n; j++)   // allows i == j
 *     CORRECT: for (int j = i + 1; j < n; j++)
 *
 *   Insert before check (hash map):
 *     WRONG:   map.put(nums[i], i);
 *              if (map.containsKey(target - nums[i])) { ... }  // self-match
 *     CORRECT: if (map.containsKey(target - nums[i])) { ... }
 *              map.put(nums[i], i);
 *
 *   Sorting raw array (two pointers):
 *     WRONG:   Arrays.sort(nums);   // loses original indices
 *     CORRECT: sort (value, originalIndex) pairs, return stored index
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: A value-keyed map mishandles duplicates if you insert before
 *    checking or overwrite a needed key. Check-before-insert fixes
 *    both; brute force is inherently safe (compares by position).
 *
 * Q: Are there any type mismatches?
 * A: HashMap<Integer,Integer> autoboxes int — safe. For values near
 *    Integer.MAX_VALUE, nums[i]+nums[j] could overflow; the hash map
 *    uses SUBTRACTION (target - nums[i]), sidestepping additive overflow.
 *
 * Q: How can I verify this works right now?
 *    public static void verify() {
 *        assert Arrays.equals(twoSum(new int[]{2,7,11,15}, 9), new int[]{0,1});
 *        assert Arrays.equals(twoSum(new int[]{3,2,4}, 6),     new int[]{1,2});
 *        assert Arrays.equals(twoSum(new int[]{3,3}, 6),       new int[]{0,1});
 *        assert Arrays.equals(twoSum(new int[]{-3,4,3,90}, 0), new int[]{0,2});
 *        System.out.println("All assertions passed.");
 *    }
 *    // Run with: java -ea TwoSumHashMap   (-ea enables assertions)
 *
 * | Approach     | Risk                              | Mitigation                                 |
 * |--------------|-----------------------------------|--------------------------------------------|
 * | Brute Force  | Too slow for large n              | Use only when n small or O(1) space needed |
 * | Two Pointers | Losing indices after sort         | Sort (value, index) pairs                  |
 * | Hash Map     | Wrong check/insert order          | Always check complement BEFORE inserting   |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * LeetCode #1 | Easy | one of the most-asked warm-up questions ever.
 *
 * | Company            | Frequency | Notes                                   |
 * |--------------------|-----------|-----------------------------------------|
 * | Amazon             | ⭐⭐⭐⭐⭐   | Extremely common phone-screen opener    |
 * | Google             | ⭐⭐⭐⭐⭐   | Often extended to 3Sum / 4Sum           |
 * | Microsoft          | ⭐⭐⭐⭐    | Hash-map fundamentals check             |
 * | Meta (Facebook)    | ⭐⭐⭐⭐    | Gauges complement/hash intuition        |
 * | Apple              | ⭐⭐⭐⭐    | Common early-round screen               |
 * | Bloomberg          | ⭐⭐⭐⭐⭐   | Perennial favorite                      |
 * | Adobe              | ⭐⭐⭐     | Initial rounds                          |
 * | Uber               | ⭐⭐⭐     | Warm-up before harder array problems    |
 * | Oracle             | ⭐⭐⭐     | Standard fundamentals question          |
 * | LinkedIn           | ⭐⭐⭐     | Sometimes "return all pairs" variant    |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach            | Time       | Space | Code Complexity | Recommended?               |
 * |---------------------|------------|-------|-----------------|----------------------------|
 * | Brute Force         | O(n^2)     | O(1)  | Very simple     | ✅ best for low memory      |
 * | Sort + Two Pointers | O(n log n) | O(n)  | Moderate        | ❌ awkward (index bookkeep) |
 * | Hash Map (one pass) | O(n)       | O(n)  | Simple          | ✅✅ best for time           |
 *
 * Recommended Approach
 *   Use the one-pass hash map: O(n) single scan remembering each
 *   value's index and looking up the complement. Fall back to brute
 *   force only when n is tiny and O(1) space is mandatory.
 *
 * What to Remember
 *   Two Sum is the archetype of the COMPLEMENT + HASH MAP pattern:
 *   for each x ask "have I seen target - x?" Key gotcha: CHECK the
 *   map for the complement BEFORE inserting the current element —
 *   this prevents self-pairing and handles duplicates correctly.
 *
 * ============================================================
 * END OF FILE
 * ============================================================
 */
// @formatter:on
