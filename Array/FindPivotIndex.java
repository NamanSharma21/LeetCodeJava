package Array;

public class FindPivotIndex {
    public static void main(String[] args) {
        FindPivotIndex findPivotIndex = new FindPivotIndex();
        System.out.println("FindPivotIndex : " + findPivotIndex.pivotIndexBruteForce(new int[] { 1, 7, 3, 6, 5, 6 }));
        System.out.println("---------------------------");
        System.out
                .println("FindPivotIndex : " + findPivotIndex.pivotIndexPrefixSumArray(new int[] { 1, 7, 3, 6, 5, 6 }));
        System.out.println("---------------------------");
        System.out
                .println("FindPivotIndex : " + findPivotIndex.pivotIndexRunningLeftSum(new int[] { 1, 7, 3, 6, 5, 6 }));
    }

    // @formatter:off
    /**
     * 
     * https://leetcode.com/problems/find-pivot-index/
     * 
     * Given an array of integers nums, calculate the pivot index of this array.
     * 
     * The pivot index is the index where the sum of all the numbers strictly to the
     * left of the index is equal to the sum of all the numbers strictly to the
     * index's right.
     * 
     * If the index is on the left edge of the array, then the left sum is 0 because
     * there are no elements to the left. This also applies to the right edge of the
     * array.
     * 
     * Return the leftmost pivot index. If no such index exists, return -1.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,7,3,6,5,6]
     * Output: 3
     * Explanation:
     * The pivot index is 3.
     * Left sum = nums[0] + nums[1] + nums[2] = 1 + 7 + 3 = 11
     * Right sum = nums[4] + nums[5] = 5 + 6 = 11
     * Example 2:
     * 
     * Input: nums = [1,2,3]
     * Output: -1
     * Explanation:
     * There is no index that satisfies the conditions in the problem statement.
     * Example 3:
     * 
     * Input: nums = [2,1,-1]
     * Output: 0
     * Explanation:
     * The pivot index is 0.
     * Left sum = 0 (no elements to the left of index 0)
     * Right sum = nums[1] + nums[2] = 1 + -1 = 0
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 104
     * -1000 <= nums[i] <= 1000
     * 
     * 
     * Note: This question is the same as 1991:
     * https://leetcode.com/problems/find-the-middle-index-in-array/
     * 
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach         | Time   | Space | Code Complexity | Recommended?                       |
     * |------------------|--------|-------|-----------------|------------------------------------|
     * | Brute Force      | O(n^2) | O(1)  | Very simple     | ❌ Not for large n - too slow      |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int pivotIndexBruteForce(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int leftSum = 0;
            for (int j = 0; j < i; j++) {
                leftSum += nums[j];
            }

            int rightSUm = 0;
            for (int j = i + 1; j < n; j++) {
                rightSUm += nums[j];
            }
            if (leftSum == rightSUm)
                return i;
        }
        return -1;
    }

    // @formatter:off
    /**
     * 
     * | Approach         | Time   | Space | Code Complexity | Recommended?                       |
     * |------------------|--------|-------|-----------------|------------------------------------|
     * | Prefix Sum Array | O(n)   | O(n)  | Simple          | ✅ Acceptable - if sums reused     |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int pivotIndexPrefixSumArray(int[] nums) {
        int n = nums.length;
        int[] pre = new int[n + 1];
        for (int i = 0; i < n; i++) {
            pre[i + 1] = pre[i] + nums[i];
        }

        int total = pre[n];
        for (int i = 0; i < n; i++) {
            int leftSum = pre[i];
            int rightSum = total - pre[i + 1];
            if (leftSum == rightSum)
                return i;
        }
        return -1;
    }

    // @formatter:off
    /**
     * 
     * | Approach         | Time   | Space | Code Complexity | Recommended?                       |
     * |------------------|--------|-------|-----------------|------------------------------------|
     * | Running Left Sum | O(n)   | O(1)  | Simple          | ✅✅ Best overall (time and space) |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int pivotIndexRunningLeftSum(int[] nums) {
        int n = nums.length;
        int totalSum = 0;
        for (int i = 0; i < n; i++) {
            totalSum += nums[i];
        }

        int leftSum = 0;
        for (int i = 0; i < n; i++) {
            int rightSum = totalSum - leftSum - nums[i];
            if (leftSum == rightSum)
                return i;
            leftSum += nums[i];

        }
        return -1;
    }
}

// @formatter:off
/*
 * ============================================================
 * FIND PIVOT INDEX - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 * LeetCode #724 - Difficulty: Easy
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are given an array of integers. Find the PIVOT INDEX: the
 * position where the sum of every element strictly to its LEFT
 * equals the sum of every element strictly to its RIGHT. The
 * element at the pivot index is excluded from both sides. If
 * several pivots exist, return the LEFTMOST. If none exists,
 * return -1.
 *
 * Rule: if the pivot is at index 0, the left side is empty and
 * its sum is 0. Same idea at the right edge.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums - array of integers (negative, zero, or positive).
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * int - the leftmost pivot index, or -1 if none exists.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * | Constraint     | Value                    |
 * |----------------|--------------------------|
 * | Array length   | 1 <= nums.length <= 10^4 |
 * | Element range  | -1000 <= nums[i] <= 1000 |
 *
 * Max |sum| ~ 10^4 * 10^3 = 10^7, so an int is sufficient.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * For each candidate index i, compare leftSum(i) with rightSum(i):
 *    leftSum(i)  = nums[0] + ... + nums[i-1]
 *    rightSum(i) = nums[i+1] + ... + nums[n-1]
 * Return the first i where leftSum(i) == rightSum(i).
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    nums = [1, 7, 3, 6, 5, 6]
 *    At index 3:
 *       left  = 1 + 7 + 3 = 11
 *       right = 5 + 6     = 11   -> balanced
 *    Answer: 3
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Picture a seesaw plank of numbered blocks. Find the single
 * block to stand on so the weight to your left balances the
 * weight to your right. Your own block does not count - you are
 * the fulcrum.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Naively, re-weigh both sides for every standing spot - lots
 *    of repeated adding.
 * 2. But stepping one block right: the block you left joins your
 *    left pile, and your new block leaves the right pile.
 * 3. Track one running total (leftSum) and derive the other from
 *    the grand total.
 * 4. Relation: rightSum = total - leftSum - nums[i], checkable in
 *    one pass.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge            | Why it's tricky                          |
 * |----------------------|------------------------------------------|
 * | Edge pivots          | At index 0 or n-1 one side is empty;     |
 * |                      | empty sum must be treated as 0.          |
 * | Negative numbers     | Sums can shrink; no monotonic pruning.   |
 * | Excluding nums[i]    | Pivot belongs to neither side (off-by-1).|
 * | Leftmost requirement | Return on the FIRST match, not the last. |
 * | Recomputation cost   | Naive re-sum is O(n^2); reuse a running  |
 * |                      | sum instead.                             |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 * | # | Approach              | Key Idea                        | Best Used When              | Time    | Space  |
 * |---|-----------------------|---------------------------------|-----------------------------|---------|--------|
 * | 1 | Brute Force           | Re-sum left and right per index | Tiny arrays; clarity        | O(n^2)  | O(1)   |
 * | 2 | Prefix Sum Array      | Precompute cumulative sums      | Reused range queries        | O(n)    | O(n)   |
 * | 3 | Running Left Sum  (✅) | total - left - nums[i]          | General optimal case        | O(n) ✅ | O(1) ✅ |
 *
 * Approaches 2 and 3 tie on O(n) time but differ on SPACE: the
 * prefix array materializes n+1 integers, while the running-sum
 * method keeps only two scalars. They are also distinct ideas:
 * precompute-then-query vs single-pass streaming.
 *
 * Approach 3 dominates on BOTH axes, so it is the clear winner.
 * Prefer Approach 2 only when reusable prefix sums are needed;
 * prefer Approach 1 only when n is trivially small.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Loop i over every index.
 * 2. Sum elements before i into leftSum.
 * 3. Sum elements after i into rightSum.
 * 4. If leftSum == rightSum, return i (leftmost).
 * 5. If loop ends with no match, return -1.
 *
 *    public class PivotBruteForce {
 *        public static int pivotIndex(int[] nums) {
 *            int n = nums.length;
 *            for (int i = 0; i < n; i++) {
 *                int leftSum = 0;
 *                for (int j = 0; j < i; j++) {
 *                    leftSum += nums[j];
 *                }
 *                int rightSum = 0;
 *                for (int j = i + 1; j < n; j++) {
 *                    rightSum += nums[j];
 *                }
 *                if (leftSum == rightSum) {
 *                    return i;               // leftmost pivot
 *                }
 *            }
 *            return -1;                      // no pivot found
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {1, 7, 3, 6, 5, 6};
 *            System.out.println(pivotIndex(nums)); // 3
 *        }
 *    }
 *
 * The inner loops re-scan for every i - source of quadratic cost.
 *
 * ------------------------------------------------------------
 * Approach 2: Prefix Sum Array
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Build pre[] of length n+1, pre[k] = sum of first k elements,
 *    pre[0] = 0.
 * 2. Grand total = pre[n].
 * 3. For index i: leftSum = pre[i], rightSum = total - pre[i+1].
 * 4. Return first i where they match; else -1.
 *
 *    public class PivotPrefixSum {
 *        public static int pivotIndex(int[] nums) {
 *            int n = nums.length;
 *            int[] pre = new int[n + 1];         // pre[0] = 0
 *            for (int i = 0; i < n; i++) {
 *                pre[i + 1] = pre[i] + nums[i];
 *            }
 *            int total = pre[n];
 *            for (int i = 0; i < n; i++) {
 *                int leftSum = pre[i];
 *                int rightSum = total - pre[i + 1];
 *                if (leftSum == rightSum) {
 *                    return i;
 *                }
 *            }
 *            return -1;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {1, 7, 3, 6, 5, 6};
 *            System.out.println(pivotIndex(nums)); // 3
 *        }
 *    }
 *
 * rightSum = total - pre[i+1] works because pre[i+1] covers
 * indices 0..i inclusive, leaving exactly i+1..n-1.
 *
 * ------------------------------------------------------------
 * Approach 3: Running Left Sum  ✅ (Optimal)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Compute total = sum of all elements in one pass.
 * 2. Initialize leftSum = 0.
 * 3. Sweep i left to right; rightSum = total - leftSum - nums[i].
 * 4. If leftSum == rightSum, return i.
 * 5. Else fold nums[i] into leftSum and continue.
 * 6. Return -1 if no match.
 *
 *    public class PivotRunningSum {
 *        public static int pivotIndex(int[] nums) {
 *            int total = 0;
 *            for (int value : nums) {
 *                total += value;                 // grand total
 *            }
 *
 *            int leftSum = 0;
 *            for (int i = 0; i < nums.length; i++) {
 *                int rightSum = total - leftSum - nums[i];
 *                if (leftSum == rightSum) {
 *                    return i;                   // leftmost pivot
 *                }
 *                leftSum += nums[i];             // move into left pile
 *            }
 *            return -1;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {1, 7, 3, 6, 5, 6};
 *            System.out.println(pivotIndex(nums)); // 3
 *        }
 *    }
 *
 * Key step: rightSum = total - leftSum - nums[i]. Everything not
 * in the left pile and not the current element lies to the right.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Time  - O(n^2): each of n indices triggers inner loops scanning
 *         up to n elements (~n*n/2 additions). n=10^4 -> ~5*10^7.
 * Space - O(1): only leftSum and rightSum scalars.
 *
 * ------------------------------------------------------------
 * Approach 2: Prefix Sum Array
 * ------------------------------------------------------------
 * Time  - O(n): one pass builds pre[], one pass checks -> ~2n.
 *         n=10^4 -> ~2*10^4 operations.
 * Space - O(n): pre[] holds n+1 integers.
 *
 * ------------------------------------------------------------
 * Approach 3: Running Left Sum
 * ------------------------------------------------------------
 * Time  - O(n): one pass for total, one to scan -> ~2n. Smaller
 *         constant than Approach 2 (no prefix indexing overhead).
 * Space - O(1): only total and leftSum scalars.
 *
 * Approaches 2 and 3 tie on time; 3 wins on space (O(1) vs O(n)),
 * making 3 the overall optimal.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * nums = [1, 7, 3, 6, 5, 6], total = 28. Expected answer: 3.
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 *    i = 0 -> left = 0,           right = 7+3+6+5+6 = 27  -> no
 *    i = 1 -> left = 1,           right = 3+6+5+6   = 20  -> no
 *    i = 2 -> left = 1+7 = 8,     right = 6+5+6     = 17  -> no
 *    i = 3 -> left = 1+7+3 = 11,  right = 5+6       = 11  -> ✅ 3
 *
 * ------------------------------------------------------------
 * Approach 2: Prefix Sum Array
 * ------------------------------------------------------------
 *    index :   -   0   1   2   3   4   5
 *    nums  :       1   7   3   6   5   6
 *    pre   : 0   1   8  11  17  22  28
 *
 * | i | leftSum = pre[i] | rightSum = total - pre[i+1] | Match? |
 * |---|------------------|-----------------------------|--------|
 * | 0 | 0                | 28 - 1  = 27                | ❌     |
 * | 1 | 1                | 28 - 8  = 20                | ❌     |
 * | 2 | 8                | 28 - 11 = 17                | ❌     |
 * | 3 | 11               | 28 - 17 = 11                | ✅ 3   |
 *
 * ------------------------------------------------------------
 * Approach 3: Running Left Sum
 * ------------------------------------------------------------
 * | i | nums[i] | leftSum(before) | right = 28-left-nums[i] | Match? | leftSum(after) |
 * |---|---------|-----------------|-------------------------|--------|----------------|
 * | 0 | 1       | 0               | 27                      | ❌     | 1              |
 * | 1 | 7       | 1               | 20                      | ❌     | 8              |
 * | 2 | 3       | 8               | 17                      | ❌     | 11             |
 * | 3 | 6       | 11              | 11                      | ✅     | return 3       |
 *
 * All three converge on index 3.
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 * | Edge Case            | Input             | Expected | How Handled                          |
 * |----------------------|-------------------|----------|--------------------------------------|
 * | Pivot at left edge   | [2, 1, -1]        | 0        | left=0, right=1+(-1)=0 -> match i=0   |
 * | Single element       | [5]               | 0        | both sides empty -> 0==0 at index 0   |
 * | No pivot exists      | [1, 2, 3]         | -1       | loop finishes with no match           |
 * | Negative values      | [-1,-1,-1,0,1,1]  | 0        | left=0, right=-1-1+0+1+1=0 -> i=0      |
 * | All zeros            | [0, 0, 0, 0]      | 0        | every index balances; leftmost first  |
 *
 * Single element: empty sides sum to 0, so index 0 is a valid pivot.
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 - including nums[i] in a side:
 *    // WRONG:  int rightSum = total - leftSum;
 *    // RIGHT:  int rightSum = total - leftSum - nums[i];
 *
 * Pitfall 2 - returning last match instead of first:
 *    // WRONG:  if (leftSum == rightSum) ans = i;  // keeps looping
 *    // RIGHT:  if (leftSum == rightSum) return i; // leftmost
 *
 * Pitfall 3 - updating leftSum before the comparison:
 *    // WRONG:  leftSum += nums[i];
 *    //         if (leftSum == total - leftSum) ...
 *    // RIGHT:  if (leftSum == total - leftSum - nums[i]) return i;
 *    //         leftSum += nums[i];
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 * Q: What edge cases might this miss?
 * A: Empty-side edges (index 0 and n-1) and arrays with negatives
 *    that make sums non-monotonic. The running-sum method derives
 *    rightSum, never assumes positivity, so both are covered. So
 *    are single-element and all-equal arrays.
 *
 * Q: Are there any type mismatches?
 * A: None for given constraints. Max |total| ~ 10^7, well within
 *    int range. For larger inputs, promote accumulators to long.
 *
 * Q: How can I verify this works right now?
 *
 *    public class PivotVerify {
 *        public static int pivotIndex(int[] nums) {
 *            int total = 0;
 *            for (int v : nums) total += v;
 *            int leftSum = 0;
 *            for (int i = 0; i < nums.length; i++) {
 *                if (leftSum == total - leftSum - nums[i]) return i;
 *                leftSum += nums[i];
 *            }
 *            return -1;
 *        }
 *
 *        public static void verify() {
 *            assert pivotIndex(new int[]{1,7,3,6,5,6}) == 3;
 *            assert pivotIndex(new int[]{1,2,3}) == -1;
 *            assert pivotIndex(new int[]{2,1,-1}) == 0;
 *            assert pivotIndex(new int[]{5}) == 0;
 *            assert pivotIndex(new int[]{-1,-1,-1,0,1,1}) == 0;
 *            assert pivotIndex(new int[]{0,0,0,0}) == 0;
 *            System.out.println("All assertions passed.");
 *        }
 *
 *        public static void main(String[] args) {
 *            verify();   // run with:  java -ea PivotVerify
 *        }
 *    }
 *
 * | Approach     | Risk                              | Mitigation                          |
 * |--------------|-----------------------------------|-------------------------------------|
 * | Brute Force  | Too slow for large n (O(n^2))     | Use only for small n                |
 * | Prefix Sum   | Extra O(n) memory; off-by-one     | pre length n+1, pre[0]=0            |
 * | Running Sum  | Forget to exclude nums[i]; early  | Compare before accumulating         |
 * |              | leftSum update                    |                                     |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #724 - Difficulty: Easy - very common warm-up /
 * phone-screen question (thousands of reported appearances).
 *
 * | Company            | Frequency | Notes                          |
 * |--------------------|-----------|--------------------------------|
 * | Amazon             | ⭐⭐⭐⭐⭐ | Frequent phone-screen / OA     |
 * | Google             | ⭐⭐⭐⭐   | Prefix-sum fundamentals        |
 * | Microsoft          | ⭐⭐⭐⭐   | Early-round array question     |
 * | Facebook (Meta)    | ⭐⭐⭐⭐   | One-pass optimization instinct |
 * | Apple              | ⭐⭐⭐    | Coding screens                 |
 * | Adobe              | ⭐⭐⭐    | Array-manipulation staple      |
 * | Bloomberg          | ⭐⭐⭐    | Prefix-sum favorite            |
 * | TikTok / ByteDance | ⭐⭐⭐    | OA rounds                      |
 * | Uber               | ⭐⭐     | Occasional screening           |
 * | Oracle             | ⭐⭐     | Fundamentals round             |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 * | Approach         | Time   | Space | Code Complexity | Recommended?                       |
 * |------------------|--------|-------|-----------------|------------------------------------|
 * | Brute Force      | O(n^2) | O(1)  | Very simple     | ❌ Not for large n - too slow      |
 * | Prefix Sum Array | O(n)   | O(n)  | Simple          | ✅ Acceptable - if sums reused     |
 * | Running Left Sum | O(n)   | O(1)  | Simple          | ✅✅ Best overall (time and space) |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use the Running Left Sum method - O(n) time and O(1) space at
 * once, so there is no time-vs-space trade-off here. Use the
 * prefix-sum array only for repeated range/pivot queries.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * Core pattern: "derive the right side from the whole" ->
 * rightSum = total - leftSum - nums[i]. Sweep once with a running
 * left accumulator, and ALWAYS compare before you accumulate so
 * the current element belongs to neither side. Empty-side-equals-
 * zero is what makes edge indices and single-element arrays valid.
 */
// @formatter:on
