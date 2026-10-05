package Array;

import java.util.HashMap;
import java.util.Map;

public class ContiguousArray {
    public static void main(String[] args) {
        ContiguousArray contiguousArray = new ContiguousArray();
        System.out.println("ContiguousArray : " + contiguousArray.findMaxLengthBruteForce(new int[] { 0, 1 }));
        System.out.println("ContiguousArray : " + contiguousArray.findMaxLengthBruteForce(new int[] { 0, 1, 0 }));
        System.out.println("ContiguousArray : "
                + contiguousArray.findMaxLengthBruteForce(new int[] { 0, 1, 1, 1, 1, 1, 0, 0, 0 }));
        System.out.println("------------------------------------------");
        System.out.println("ContiguousArray : " + contiguousArray.findMaxLengthPrefixSumHashMap(new int[] { 0, 1 }));
        System.out.println("ContiguousArray : " + contiguousArray.findMaxLengthPrefixSumHashMap(new int[] { 0, 1, 0 }));
        System.out.println("ContiguousArray : "
                + contiguousArray.findMaxLengthPrefixSumHashMap(new int[] { 0, 1, 1, 1, 1, 1, 0, 0, 0 }));
    }

    // @formatter:off
    /**
     * 
     * https://leetcode.com/problems/contiguous-array/description/
     * 
     * Given a binary array nums, return the maximum length of a contiguous subarray
     * with an equal number of 0 and 1.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [0,1]
     * Output: 2
     * Explanation: [0, 1] is the longest contiguous subarray with an equal number
     * of 0 and 1.
     * Example 2:
     * 
     * Input: nums = [0,1,0]
     * Output: 2
     * Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal
     * number of 0 and 1.
     * Example 3:
     * 
     * Input: nums = [0,1,1,1,1,1,0,0,0]
     * Output: 6
     * Explanation: [1,1,1,0,0,0] is the longest contiguous subarray with equal
     * number of 0 and 1.
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 105
     * nums[i] is either 0 or 1.
     * 
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach           | Time   | Space | Code Complexity | Recommended?                              |
     * |--------------------|--------|-------|-----------------|-------------------------------------------|
     * | Brute Force        | O(n^2) | O(1)  | Low             | [check] best for low memory / tiny n only |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int findMaxLengthBruteForce(int[] nums) {
        int maxLen = 0;
        for (int start = 0; start < nums.length; start++) {
            int balance = 0;
            for (int end = start; end < nums.length; end++) {
                balance += (nums[end] == 1) ? 1 : -1;
                if (balance == 0)
                    maxLen = Math.max(maxLen, end - start + 1);
            }
        }
        return maxLen;
    }

    // @formatter:off
    /**
     * 
     * | Approach           | Time   | Space | Code Complexity | Recommended?                              |
     * |--------------------|--------|-------|-----------------|-------------------------------------------|
     * | Prefix Sum+HashMap | O(n)   | O(n)  | Low-Medium      | [check][check] best overall (use this)    |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int findMaxLengthPrefixSumHashMap(int[] nums) {
        Map<Integer, Integer> firstSeen = new HashMap<>();
        firstSeen.put(0, -1);
        int count = 0;
        int maxLen = 0;
        for (int i = 0; i < nums.length; i++) {
            count += (nums[i] == 1) ? 1 : -1;
            if (firstSeen.containsKey(count)) {
                maxLen = Math.max(maxLen, i - firstSeen.get(count));
            } else {
                firstSeen.put(count, i);
            }
        }
        return maxLen;
    }
}

// @formatter:off
/*
 * ============================================================
 * CONTIGUOUS ARRAY - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given a binary array (containing only 0s and 1s), find the length of the
 * LONGEST contiguous subarray that contains an EQUAL number of 0s and 1s.
 * This is LeetCode 525 (Medium).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums - an array where every element is either 0 or 1.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * A single int - the maximum length of a contiguous subarray with equal counts
 * of 0s and 1s. If none exists, return 0.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 *   1 <= nums.length <= 10^5
 *   nums[i] is 0 or 1.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Among all contiguous slices nums[i..j], we want the maximum (j - i + 1) such
 * that count(0) == count(1) within that slice. The subarray must be contiguous
 * (no reordering, no skipping), and a valid subarray always has EVEN length.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  nums = [0, 1, 0]
 *    Output: 2
 *    Explanation: [0, 1] (indices 0..1) has one 0 and one 1. So does [1, 0].
 *                 Both have length 2, which is the maximum.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Counting 0s and 1s separately is awkward. The trick is to RELABEL every 0 as
 * -1. Now "equal number of 0s and 1s" becomes "the elements sum to ZERO." So the
 * problem transforms into: find the longest contiguous subarray whose sum is 0.
 *
 * Once it's a "subarray summing to zero" problem, we use a running (prefix) sum.
 * If the running sum has the SAME value at two different positions, then
 * everything BETWEEN those positions must sum to zero - because nothing net was
 * added.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 *   1. Replace each 0 with -1. Now the array is made of +1 and -1.
 *   2. Keep a running total 'count' as you walk left to right.
 *   3. If 'count' returns to a value it held before at index j, the stretch from
 *      j+1 to the current index i netted out to zero -> equal +1s and -1s ->
 *      equal 0s and 1s.
 *   4. The length of that stretch is i - j. To make it as long as possible, we
 *      want j to be the EARLIEST index where that count value first appeared.
 *   5. Remember the FIRST index at which each count value occurs, never overwrite.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                | Why it's tricky                                  |
 * |--------------------------|--------------------------------------------------|
 * | Two separate counts      | Tracking 0s and 1s independently makes "equal"   |
 * |                          | hard to express; 0 -> -1 collapses it to one sum.|
 * | Longest, not just any    | You must store the FIRST occurrence of each      |
 * |                          | prefix sum; overwriting shrinks the window.      |
 * | The empty-prefix base    | A subarray starting at index 0 needs prefix sum  |
 * | case                     | 0 "seen" before it began. Seed map with {0: -1}. |
 * | Off-by-one in length     | Length is i - firstIndex, NOT +1, because the    |
 * |                          | matching prefix sum sits just before the window. |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach           | Key Idea                    | Best Used When       | Time   | Space         |
 * |---|--------------------|-----------------------------|----------------------|--------|---------------|
 * | 1 | Brute Force        | For every start, extend end | n is small, or aux   | O(n^2) | O(1) [check]  |
 * |   | (expand from start)| tracking +1/-1 balance;     | memory must be O(1)  |        | space-optimal |
 * |   |                    | record length when 0        |                      |        |               |
 * | 2 | Prefix Sum+HashMap | Remap 0 -> -1; store first  | General case; large  | O(n)   | O(n)          |
 * |   |                    | index of each running sum;  | n where speed matters| [check]|               |
 * |   |                    | equal sums bound zero window|                      | time-opt|              |
 *
 * The two approaches trade the axes against each other. The brute force uses NO
 * extra memory but is quadratic in time; the hashmap approach is LINEAR in time
 * but stores up to O(n) distinct prefix-sum keys. For any realistic input
 * (n up to 10^5) the quadratic approach is far too slow (~10^10 ops), so
 * Approach 2 is the one to use. Reach for brute force only when n is tiny and
 * you must guarantee constant auxiliary space.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (expand from each start)
 * ------------------------------------------------------------
 * Algorithm:
 *   1. For each start index, initialize a running balance = 0.
 *   2. Extend end from start to the array end, adding +1 for a 1, -1 for a 0.
 *   3. Whenever balance == 0, window [start..end] has equal 0s and 1s; update
 *      maxLen with end - start + 1.
 *   4. Return the largest length found.
 *
 *    import java.util.*;
 *
 *    public class ContiguousArrayBrute {
 *        public int findMaxLength(int[] nums) {
 *            int maxLen = 0;
 *            for (int start = 0; start < nums.length; start++) {
 *                int balance = 0; // +1 for a 1, -1 for a 0
 *                for (int end = start; end < nums.length; end++) {
 *                    balance += (nums[end] == 1) ? 1 : -1;
 *                    if (balance == 0) {
 *                        maxLen = Math.max(maxLen, end - start + 1);
 *                    }
 *                }
 *            }
 *            return maxLen;
 *        }
 *
 *        public static void main(String[] args) {
 *            ContiguousArrayBrute sol = new ContiguousArrayBrute();
 *            int[] nums = {0, 1, 0, 1, 1, 0, 0};
 *            System.out.println(sol.findMaxLength(nums)); // Expected: 6
 *        }
 *    }
 *
 * The inner loop reuses balance incrementally instead of recounting from scratch,
 * so each (start, end) pair costs O(1) - but there are O(n^2) such pairs.
 *
 * ------------------------------------------------------------
 * Approach 2: Prefix Sum + HashMap [check] OPTIMAL
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Create map firstSeen from a prefix-sum value to the FIRST index at which
 *      it occurred. Seed with firstSeen.put(0, -1) to handle windows starting at
 *      index 0.
 *   2. Walk the array with a running count, adding +1 for a 1, -1 for a 0.
 *   3. If count already exists in the map at index j = firstSeen.get(count), then
 *      nums[j+1..i] sums to zero -> candidate length i - j; update maxLen.
 *   4. Otherwise this count is new - record firstSeen.put(count, i) (never
 *      overwrite, to keep the earliest index).
 *   5. Return maxLen.
 *
 *    import java.util.*;
 *
 *    public class ContiguousArray {
 *        public int findMaxLength(int[] nums) {
 *            Map<Integer, Integer> firstSeen = new HashMap<>();
 *            firstSeen.put(0, -1);          // empty prefix: sum 0 before index 0
 *            int count = 0;                 // running balance after 0 -> -1
 *            int maxLen = 0;
 *
 *            for (int i = 0; i < nums.length; i++) {
 *                count += (nums[i] == 1) ? 1 : -1;
 *                if (firstSeen.containsKey(count)) {
 *                    maxLen = Math.max(maxLen, i - firstSeen.get(count));
 *                } else {
 *                    firstSeen.put(count, i); // store FIRST occurrence only
 *                }
 *            }
 *            return maxLen;
 *        }
 *
 *        public static void main(String[] args) {
 *            ContiguousArray sol = new ContiguousArray();
 *            System.out.println(sol.findMaxLength(new int[]{0,1,0,1,1,0,0})); // 6
 *            System.out.println(sol.findMaxLength(new int[]{0, 1}));          // 2
 *            System.out.println(sol.findMaxLength(new int[]{1, 1, 1, 1}));    // 0
 *        }
 *    }
 *
 * The subtle bound is the length formula i - firstSeen.get(count). Because the
 * stored index is the position where the prefix sum was already count, the
 * balanced window begins at the NEXT index, so we do NOT add 1.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 *   Time:  O(n^2). Outer loop runs n times; for each start the inner loop runs
 *          up to n - start times. Total pairs = n + (n-1) + ... + 1 = n(n+1)/2,
 *          which is O(n^2).
 *   Space: O(1). Only scalar variables (maxLen, balance, indices).
 *   Numeric feel: n = 1,000 -> ~500,000 inner iterations (fine). n = 100,000 ->
 *          ~5e9 iterations - seconds to minutes, effectively too slow.
 *
 * ------------------------------------------------------------
 * Approach 2: Prefix Sum + HashMap [check]
 * ------------------------------------------------------------
 *   Time:  O(n). Single pass; each step is O(1) amortized hashmap work.
 *   Space: O(n). Prefix sum ranges over [-n, n], so up to n + 1 distinct keys.
 *   Numeric feel: n = 100,000 -> ~100,000 iterations and at most ~100,001 map
 *          entries - runs in milliseconds.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force on nums = [0, 1, 0]
 * ------------------------------------------------------------
 * Remap intuition: 0 -> -1, 1 -> +1, so values are [-1, +1, -1].
 *
 * | start | end | balance | balance == 0? | window length | maxLen |
 * |-------|-----|---------|---------------|---------------|--------|
 * | 0     | 0   | -1      | no            | -             | 0      |
 * | 0     | 1   | 0       | YES           | 2             | 2      |
 * | 0     | 2   | -1      | no            | -             | 2      |
 * | 1     | 1   | +1      | no            | -             | 2      |
 * | 1     | 2   | 0       | YES           | 2             | 2      |
 * | 2     | 2   | -1      | no            | -             | 2      |
 *
 * Final output: 2 [check]
 *
 * ------------------------------------------------------------
 * Approach 2: Prefix Sum + HashMap on nums = [0, 1, 0, 1, 1, 0, 0]
 * ------------------------------------------------------------
 * Start with firstSeen = {0: -1}, count = 0, maxLen = 0.
 *
 *    i=0  nums[0]=0  count = -1
 *         |__ -1 not in map -> store {-1: 0}
 *    i=1  nums[1]=1  count =  0
 *         |__ 0 seen at -1 -> len = 1 - (-1) = 2 -> maxLen = 2
 *    i=2  nums[2]=0  count = -1
 *         |__ -1 seen at 0 -> len = 2 - 0 = 2 -> maxLen = 2
 *    i=3  nums[3]=1  count =  0
 *         |__ 0 seen at -1 -> len = 3 - (-1) = 4 -> maxLen = 4
 *    i=4  nums[4]=1  count =  1
 *         |__ 1 not in map -> store {1: 4}
 *    i=5  nums[5]=0  count =  0
 *         |__ 0 seen at -1 -> len = 5 - (-1) = 6 -> maxLen = 6
 *    i=6  nums[6]=0  count = -1
 *         |__ -1 seen at 0 -> len = 6 - 0 = 6 -> maxLen = 6
 *
 * Final map: {0:-1, -1:0, 1:4}. Final output: 6 [check]
 * (window [0,1,0,1,1,0] has three 0s and three 1s.)
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case            | Input             | Expected | How Handled                         |
 * |----------------------|-------------------|----------|-------------------------------------|
 * | Single element       | [0]               | 0        | Balance never 0 over odd span.      |
 * | All ones             | [1,1,1,1]         | 0        | count climbs 1..4, never revisits.  |
 * | All zeros            | [0,0,0,0]         | 0        | count falls -1..-4, never revisits. |
 * | Balanced whole array | [0,1]             | 2        | Prefix sum returns to 0 (seed -1).  |
 * | Balanced middle      | [1,1,0,0,1]       | 4        | Earliest-index storage -> len 4.    |
 * | Longest middle slice | [0,1,0,1,1,0,0]   | 6        | First-occurrence yields len 6.      |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 *   - Overwriting the first occurrence. Keep the EARLIEST index per prefix sum.
 *        // WRONG - always overwrites, shrinks the window
 *        firstSeen.put(count, i);
 *        // CORRECT - only store the first time this sum appears
 *        if (!firstSeen.containsKey(count)) firstSeen.put(count, i);
 *   - Forgetting the seed {0: -1}. Any balanced subarray starting at index 0 is
 *     missed and the length math is off by one.
 *   - Wrong length formula. Using i - firstSeen.get(count) + 1 over-counts by
 *     one, since the stored index precedes the balanced window.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The base case (subarrays anchored at index 0, fixed by seeding {0:-1}) and
 *    the "never balanced" inputs (all-0 or all-1), which correctly return 0
 *    because no prefix sum repeats. Odd total length can still contain valid
 *    even-length windows, so no special handling is needed there.
 *
 * Q: Are there any type mismatches?
 * A: Keys and values are both Integer; count can be negative (down to -n), and
 *    HashMap<Integer,Integer> handles negatives fine. No overflow since
 *    |count| <= n <= 10^5, inside int range. Guard against unboxing null by
 *    always checking containsKey before get.
 *
 * Q: How can I verify this works right now?
 *    public static void verify() {
 *        ContiguousArray sol = new ContiguousArray();
 *        assert sol.findMaxLength(new int[]{0, 1}) == 2;
 *        assert sol.findMaxLength(new int[]{0, 1, 0}) == 2;
 *        assert sol.findMaxLength(new int[]{0, 1, 0, 1, 1, 0, 0}) == 6;
 *        assert sol.findMaxLength(new int[]{1, 1, 1, 1}) == 0;
 *        assert sol.findMaxLength(new int[]{0, 0, 0, 0}) == 0;
 *        assert sol.findMaxLength(new int[]{0}) == 0;
 *        assert sol.findMaxLength(new int[]{1, 0, 1, 0, 1, 0}) == 6;
 *        System.out.println("All assertions passed.");
 *    }
 *    // Run with:  java -ea ContiguousArray   (the -ea flag enables assertions)
 *
 * | Approach           | Risk                          | Mitigation                         |
 * |--------------------|-------------------------------|------------------------------------|
 * | Brute Force        | Too slow for large n (O(n^2)) | Small inputs only; switch to map.  |
 * | Prefix Sum+HashMap | Overwrite first occ; off-by-1 | Store when unseen; use i-firstIdx; |
 * |                    |                               | seed {0:-1}.                       |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode 525 - Difficulty: Medium - Appears frequently in mid-level screens
 * (thousands of reported interview appearances).
 *
 * | Company            | Frequency (stars) | Notes                                       |
 * |--------------------|-------------------|---------------------------------------------|
 * | Amazon             | *****             | Very common; prefix-sum-with-hashmap probe. |
 * | Facebook (Meta)    | *****             | Follow-up to "Subarray Sum Equals K."       |
 * | Google             | ****              | Tests the 0 -> -1 transform insight.        |
 * | Microsoft          | ****              | Phone screens and OAs.                       |
 * | Bloomberg          | ****              | Hashmap + running-sum reasoning.            |
 * | Apple              | ***               | Occasional; medium rounds.                  |
 * | Adobe              | ***               | Array-focused sets.                         |
 * | Uber               | ***               | Data-structure round staple.                |
 * | TikTok / ByteDance | ***               | Increasingly common in OAs.                 |
 * | Goldman Sachs      | **                | Occasional in mixed rounds.                 |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach           | Time   | Space | Code Complexity | Recommended?                              |
 * |--------------------|--------|-------|-----------------|-------------------------------------------|
 * | Brute Force        | O(n^2) | O(1)  | Low             | [check] best for low memory / tiny n only |
 * | Prefix Sum+HashMap | O(n)   | O(n)  | Low-Medium      | [check][check] best overall (use this)    |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use the Prefix Sum + HashMap approach. It solves the full constraint range in
 * linear time; the only cost is O(n) auxiliary memory, a non-issue here. Prefer
 * the brute force ONLY if you're constrained to O(1) extra space and n is small.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * The key move is remapping 0 -> -1, which turns "equal 0s and 1s" into
 * "subarray with sum 0." Then a prefix sum + first-occurrence hashmap finds the
 * longest zero-sum window in one pass. Two gotchas to memorize: seed the map with
 * {0: -1} and store only the EARLIEST index of each prefix sum.
 *
 * ============================================================
 * END OF EXPLANATION
 * ============================================================
 */
// @formatter:on
