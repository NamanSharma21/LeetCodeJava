package Array;

import java.util.HashMap;
import java.util.Map;

public class SubarraySumEqualsK {
    public static void main(String[] args) {
        SubarraySumEqualsK subarraySumEqualsK = new SubarraySumEqualsK();
        System.out
                .println("SubarraySumEqualsK : " + subarraySumEqualsK.subarraySumBruteForce(new int[] { 1, 1, 1 }, 2));
        System.out
                .println("SubarraySumEqualsK : " + subarraySumEqualsK.subarraySumBruteForce(new int[] { 1, 2, 3 }, 3));
        System.out.println("---------------------------------------------");
        System.out
                .println("SubarraySumEqualsK : "
                        + subarraySumEqualsK.subarraySumPrefixSumHashMap(new int[] { 1, 1, 1 }, 2));
        System.out
                .println("SubarraySumEqualsK : "
                        + subarraySumEqualsK.subarraySumPrefixSumHashMap(new int[] { 1, 2, 3 }, 3));
    }

    // @formatter:off
    /*
     * 
     * https://leetcode.com/problems/subarray-sum-equals-k/description/
     * 
     * Given an array of integers nums and an integer k, return the total number of
     * subarrays whose sum equals to k.
     * 
     * A subarray is a contiguous non-empty sequence of elements within an array.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,1,1], k = 2
     * Output: 2
     * Example 2:
     * 
     * Input: nums = [1,2,3], k = 3
     * Output: 2
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 2 * 104
     * -1000 <= nums[i] <= 1000
     * -107 <= k <= 107
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach                | Time   | Space | Code Complexity | Recommended?              |
     * |-------------------------|--------|-------|-----------------|---------------------------|
     * | Brute Force (run sum)   | O(n^2) | O(1)  | Very simple     | OK - best for low memory  |
     * 
     * @param nums
     * @param k
     * @return
     */
    // @formatter:on
    public int subarraySumBruteForce(int[] nums, int k) {
        int n = nums.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            int runningSum = 0;
            for (int j = i; j < n; j++) {
                runningSum += nums[j];
                if (runningSum == k) {
                    count++;
                }
            }
        }
        return count;
    }

    // @formatter:off
    /**
     * 
     * | Approach                | Time   | Space | Code Complexity | Recommended?              |
     * |-------------------------|--------|-------|-----------------|---------------------------|
     * | Prefix Sum + Hash Map   | O(n)   | O(n)  | Moderate        | BEST - best for time      |
     * 
     * @param nums
     * @param k
     * @return
     */
    // @formatter:on
    public int subarraySumPrefixSumHashMap(int[] nums, int k) {
        int count = 0;
        int sum = 0;
        Map<Integer, Integer> prefixCount = new HashMap<>();
        prefixCount.put(0, 1);
        for (int num : nums) {
            sum += num;
            count += prefixCount.getOrDefault(sum - k, 0);
            prefixCount.put(sum, prefixCount.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}

// @formatter:off
/*
 * ============================================================
 * SUBARRAY SUM EQUALS K - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an array of integers, count how many CONTIGUOUS subarrays add up to
 * exactly a target value k. A subarray is a slice of consecutive elements -
 * order matters and you cannot skip elements in the middle.
 * This is LeetCode 560, difficulty Medium.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums - the array of integers (may contain negatives, zeros, positives)
 * int   k    - the target sum
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * int - the total number of contiguous subarrays whose sum equals k
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * | Constraint    | Value                        |
 * |---------------|------------------------------|
 * | Array length  | 1 <= nums.length <= 2 * 10^4 |
 * | Element range | -1000 <= nums[i] <= 1000     |
 * | Target range  | -10^7 <= k <= 10^7           |
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Count (not list, not return indices) every pair of boundaries (i, j) with
 * i <= j such that nums[i] + ... + nums[j] == k. Overlapping subarrays are
 * counted separately. The answer is a single integer.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    nums = [1, 1, 1], k = 2
 *    Subarrays that sum to 2: [1,1] (indices 0-1) and [1,1] (indices 1-2)
 *    Output = 2
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Keep a running total as you sweep left to right. The sum of a subarray
 * ending at position j and starting just after position i equals
 * runningSum(j) - runningSum(i). So "does some subarray ending at j sum to k?"
 * becomes "have I previously seen a running total equal to runningSum(j) - k?"
 * - and if so, HOW MANY TIMES, because each earlier occurrence is a distinct
 * valid subarray.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Walk a "prefix sum" - the total of everything seen so far.
 * 2. The subarray between two prefix positions has sum prefix[now] - prefix[earlier].
 * 3. To land on k, we need prefix[earlier] = prefix[now] - k.
 * 4. Keep a tally of every prefix sum encountered; at each new position, look up
 *    how many earlier prefixes equal prefix[now] - k and add that to the answer.
 * 5. Seed the tally with {0 : 1} - the empty prefix before the array starts -
 *    so subarrays that begin at index 0 are counted.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge             | Why it's tricky                                        |
 * |-----------------------|--------------------------------------------------------|
 * | Negative numbers      | Cannot use a sliding window; adding an element can     |
 * |                       | DECREASE the sum, breaking the monotonic assumption.   |
 * | Counting, not detect  | Must store the FREQUENCY of each prefix sum, since     |
 * |                       | multiple earlier positions can each form a subarray.   |
 * | The empty-prefix seed | Forgetting map.put(0,1) miscounts subarrays starting   |
 * |                       | at index 0.                                            |
 * | Order of operations   | Count BEFORE inserting the current prefix, else an     |
 * |                       | element can match itself when k == 0.                  |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                    | Key Idea                              | Best Used When                    | Time   | Space          |
 * |---|-----------------------------|---------------------------------------|-----------------------------------|--------|----------------|
 * | 1 | Brute Force (running sum)   | Fix a start, extend end, accumulate   | Tiny inputs, O(1) memory required | O(n^2) | O(1) space-opt |
 * | 2 | Prefix Sum + Hash Map       | Store freq of prefix sum; find sum-k  | General case - time matters       | O(n)   | O(n)           |
 *
 * The two approaches sit on opposite ends of the time/space trade-off. Brute
 * force uses only a couple of variables (O(1) space) but pays O(n^2) time by
 * re-summing overlapping ranges. The hash-map approach spends O(n) auxiliary
 * memory to remember every prefix sum, and collapses the problem to a single
 * linear pass. A sliding-window approach is NOT applicable because negatives
 * break monotonicity, so there is no genuinely distinct third approach.
 * Prefer the hash map by default (n up to 2*10^4 makes O(n^2) borderline slow);
 * fall back to brute force only for small n or when O(1) space is mandatory.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (Running Sum)
 * ------------------------------------------------------------
 * Steps:
 * 1. For each start index, reset sum = 0.
 * 2. For each end index >= start, add nums[end] to sum.
 * 3. Whenever sum == k, increment the counter.
 * 4. Return the counter.
 * The inner loop reuses the accumulated sum, so each pair costs O(1),
 * giving O(n^2) overall rather than O(n^3).
 *
 *    import java.util.*;
 *
 *    public class SubarraySumBrute {
 *
 *        public int subarraySum(int[] nums, int k) {
 *            int count = 0;
 *            int n = nums.length;
 *
 *            for (int start = 0; start < n; start++) {
 *                int runningSum = 0;
 *                for (int end = start; end < n; end++) {
 *                    runningSum += nums[end];      // extend subarray by one
 *                    if (runningSum == k) {
 *                        count++;                  // found a valid subarray
 *                    }
 *                }
 *            }
 *            return count;
 *        }
 *
 *        public static void main(String[] args) {
 *            SubarraySumBrute solver = new SubarraySumBrute();
 *            System.out.println(solver.subarraySum(new int[]{1, 1, 1}, 2)); // 2
 *            System.out.println(solver.subarraySum(new int[]{1, 2, 3}, 3)); // 2
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 2: Prefix Sum + Hash Map  [OPTIMAL]
 * ------------------------------------------------------------
 * Steps:
 * 1. Create a map prefixCount from prefix-sum value -> occurrences; seed {0:1}.
 * 2. Maintain running sum = 0 and count = 0.
 * 3. For each element: add it to sum.
 * 4. Look up sum - k in the map; if present, add its frequency to count.
 * 5. Increment the frequency of the current sum in the map.
 * 6. Return count.
 *
 *    import java.util.*;
 *
 *    public class SubarraySumOptimal {
 *
 *        public int subarraySum(int[] nums, int k) {
 *            Map<Integer, Integer> prefixCount = new HashMap<>();
 *            prefixCount.put(0, 1);            // enables subarrays starting at index 0
 *
 *            int sum = 0;
 *            int count = 0;
 *
 *            for (int num : nums) {
 *                sum += num;                   // running prefix sum
 *                count += prefixCount.getOrDefault(sum - k, 0);
 *                prefixCount.put(sum, prefixCount.getOrDefault(sum, 0) + 1);
 *            }
 *            return count;
 *        }
 *
 *        public static void main(String[] args) {
 *            SubarraySumOptimal solver = new SubarraySumOptimal();
 *            System.out.println(solver.subarraySum(new int[]{1, 1, 1}, 2));   // 2
 *            System.out.println(solver.subarraySum(new int[]{1, 2, 3}, 3));   // 2
 *            System.out.println(solver.subarraySum(new int[]{1, -1, 0}, 0));  // 3
 *        }
 *    }
 *
 * Non-obvious detail - why count BEFORE inserting: counting sum - k before
 * adding the current sum guarantees the current position only pairs with
 * strictly earlier prefixes. Inserting first could falsely count a zero-length
 * subarray when k == 0.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Time  - O(n^2): outer loop n times, inner up to n; total ~ n(n+1)/2.
 *         n = 2*10^4 -> ~2*10^8 additions. n = 100 -> ~5,050 ops.
 * Space - O(1): only count, runningSum, and indices.
 *
 * ------------------------------------------------------------
 * Approach 2: Prefix Sum + Hash Map  [OPTIMAL]
 * ------------------------------------------------------------
 * Time  - O(n): single pass, one O(1) lookup and insert each iteration.
 *         n = 2*10^4 -> ~2*10^4 core ops (~10,000x fewer than brute force).
 * Space - O(n): worst case all prefix sums distinct -> n + 1 entries.
 *         n = 2*10^4 -> ~20,001 key-value pairs.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 Trace - nums = [1, 2, 3], k = 3
 * ------------------------------------------------------------
 * | start | end | runningSum | == k? | count |
 * |-------|-----|------------|-------|-------|
 * | 0     | 0   | 1          | no    | 0     |
 * | 0     | 1   | 3          | YES   | 1     |
 * | 0     | 2   | 6          | no    | 1     |
 * | 1     | 1   | 2          | no    | 1     |
 * | 1     | 2   | 5          | no    | 1     |
 * | 2     | 2   | 3          | YES   | 2     |
 * Output = 2 -> subarrays [1,2] and [3].
 *
 * ------------------------------------------------------------
 * Approach 2 Trace - nums = [1, -1, 0], k = 0
 * ------------------------------------------------------------
 * Seed: map = {0:1}, sum = 0, count = 0.
 *
 *    Process 1:
 *    |- sum = 0 + 1 = 1
 *    |- need (sum-k) = 1 -> map has 0 -> count += 0 -> count = 0
 *    \- map[1] = 1                     -> map = {0:1, 1:1}
 *
 *    Process -1:
 *    |- sum = 1 + (-1) = 0
 *    |- need (sum-k) = 0 -> map has 1 -> count += 1 -> count = 1
 *    \- map[0] = 2                     -> map = {0:2, 1:1}
 *
 *    Process 0:
 *    |- sum = 0 + 0 = 0
 *    |- need (sum-k) = 0 -> map has 2 -> count += 2 -> count = 3
 *    \- map[0] = 3                     -> map = {0:3, 1:1}
 *
 * Output = 3 -> subarrays [1,-1], [1,-1,0], and [0].
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case               | Input                | Expected | How Handled                              |
 * |-------------------------|----------------------|----------|------------------------------------------|
 * | Single element == k     | nums=[3], k=3        | 1        | Seed {0:1}; sum=3, sum-k=0 found once.    |
 * | Single element != k     | nums=[5], k=3        | 0        | sum-k=2 never in map.                     |
 * | Negative numbers        | nums=[1,-1,0], k=0   | 3        | Prefix map handles non-monotonic sums.    |
 * | k = 0 with zeros        | nums=[0,0,0], k=0    | 6        | Repeated prefix 0 accumulates frequency.  |
 * | No valid subarray       | nums=[1,2,3], k=100  | 0        | No lookup ever matches.                   |
 * | Whole array is answer   | nums=[2,2], k=4      | 1        | Full-array prefix pairs with seed 0.      |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * - Forgetting the seed {0:1} - subarrays starting at index 0 go uncounted.
 *      WRONG:   Map<Integer,Integer> map = new HashMap<>();  // [3],k=3 -> 0
 *      CORRECT: map.put(0, 1);
 *
 * - Inserting the current prefix before counting - over-counts when k == 0.
 *      WRONG:   map.put(sum, map.getOrDefault(sum,0)+1);
 *               count += map.getOrDefault(sum - k, 0);
 *      CORRECT: count += map.getOrDefault(sum - k, 0);
 *               map.put(sum, map.getOrDefault(sum,0)+1);
 *
 * - Using containsKey and adding 1 instead of adding the stored frequency -
 *   under-counts when a prefix sum repeats.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: Chiefly k == 0 / repeated-zero cases and subarrays anchored at index 0.
 *    Both are covered by seeding {0:1} and counting before inserting.
 *    Negative-number arrays are handled naturally.
 *
 * Q: Are there any type mismatches?
 * A: Max prefix sum magnitude is 2*10^4 * 1000 = 2*10^7, within int range
 *    (~2.1*10^9), so int is safe. For much larger inputs, promote sum to long.
 *
 * Q: How can I verify this works right now?
 *
 *    public static void verify() {
 *        SubarraySumOptimal s = new SubarraySumOptimal();
 *        assert s.subarraySum(new int[]{1, 1, 1}, 2) == 2;
 *        assert s.subarraySum(new int[]{1, 2, 3}, 3) == 2;
 *        assert s.subarraySum(new int[]{1, -1, 0}, 0) == 3;
 *        assert s.subarraySum(new int[]{0, 0, 0}, 0) == 6;
 *        assert s.subarraySum(new int[]{3}, 3) == 1;
 *        assert s.subarraySum(new int[]{5}, 3) == 0;
 *        System.out.println("All tests passed.");
 *    }
 *    // Run with: java -ea SubarraySumOptimal   (-ea enables asserts)
 *
 * | Approach     | Risk                            | Mitigation                              |
 * |--------------|---------------------------------|-----------------------------------------|
 * | Brute Force  | O(n^2) too slow near n = 2*10^4  | Use only for small n; else hash map.    |
 * | Hash Map     | Wrong order or missing seed      | Seed {0:1}; count sum-k before insert.  |
 * | Hash Map     | Overflow on larger inputs        | Use long for the running sum.           |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * LeetCode 560 - Difficulty: Medium - ~1,000+ reported interview appearances
 *
 * | Company           | Frequency | Notes                                          |
 * |-------------------|-----------|------------------------------------------------|
 * | Amazon            | *****     | Extremely common prefix-sum warm-up.           |
 * | Google            | *****     | Asked with negatives / longest-subarray follow.|
 * | Facebook / Meta   | *****     | Frequent phone-screen question.                |
 * | Microsoft         | ****      | Pairs with Max Size Subarray Sum Equals k.     |
 * | Bloomberg         | ****      | Popular on-site; tests empty-prefix insight.   |
 * | Apple             | ***       | Appears in array/hashing rounds.               |
 * | Adobe             | ***       | Common in earlier interview stages.            |
 * | Uber              | ***       | Asked as a hashing pattern check.              |
 * | Goldman Sachs     | **        | Occasional; brute-force baseline first.        |
 * | TikTok / ByteDance| ***       | Growing frequency in recent cycles.            |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach                | Time   | Space | Code Complexity | Recommended?              |
 * |-------------------------|--------|-------|-----------------|---------------------------|
 * | Brute Force (run sum)   | O(n^2) | O(1)  | Very simple     | OK - best for low memory  |
 * | Prefix Sum + Hash Map   | O(n)   | O(n)  | Moderate        | BEST - best for time      |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use Prefix Sum + Hash Map for any non-trivial input - it turns an O(n^2) scan
 * into a single linear pass. Reach for brute force only when n is tiny or O(1)
 * auxiliary space is strictly required, since it is the only O(1)-space option.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * The pattern is prefix sum + frequency hash map: a subarray sums to k exactly
 * when currentPrefix - k was seen before, so store the COUNT of each prefix sum
 * and seed the map with {0:1}. The killer gotcha is that negatives forbid
 * sliding windows - this is why the map, not two pointers, is the right tool.
 *
 * ============================================================
 * END OF EXPLANATION
 * ============================================================
 */
// @formatter:on
