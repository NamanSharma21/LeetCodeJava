package Array;

public class MaximumAverageSubarrayI {
    public static void main(String[] args) {
        MaximumAverageSubarrayI maximumAverageSubarrayI = new MaximumAverageSubarrayI();
        System.out.println("MaximumAverageSubarrayI : "
                + maximumAverageSubarrayI.findMaxAverageSlidingWindow(new int[] { 1, 12, -5, -6, 50, 3 }, 4));
        System.out.println("--------------------------------------");
        System.out.println("MaximumAverageSubarrayI : "
                + maximumAverageSubarrayI.findMaxAveragePrefixSum(new int[] { 1, 12, -5, -6, 50, 3 }, 4));
        System.out.println("--------------------------------------");
        System.out.println("MaximumAverageSubarrayI : "
                + maximumAverageSubarrayI.findMaxAverageBruteForce(new int[] { 1, 12, -5, -6, 50, 3 }, 4));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/maximum-average-subarray-i/
     * 
     * 
     * You are given an integer array nums consisting of n elements, and an integer
     * k.
     * 
     * Find a contiguous subarray whose length is equal to k that has the maximum
     * average value and return this value. Any answer with a calculation error less
     * than 10-5 will be accepted.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,12,-5,-6,50,3], k = 4
     * Output: 12.75000
     * Explanation: Maximum average is (12 - 5 - 6 + 50) / 4 = 51 / 4 = 12.75
     * Example 2:
     * 
     * Input: nums = [5], k = 1
     * Output: 5.00000
     * 
     * 
     * Constraints:
     * 
     * n == nums.length
     * 1 <= k <= n <= 105
     * -104 <= nums[i] <= 104
     */
    // @formatter:on

    // @formatter:off
    /**
     *   | Approach       | Time   | Space | Code Complexity | Recommended?                     |
     *   |----------------|--------|-------|-----------------|----------------------------------|
     *   | Brute Force    | O(n*k) | O(1)  | Very simple     | X Too slow - baseline only       |
     * 
     * @param nums
     * @param k
     * @return
     */
    // @formatter:on
    public double findMaxAverageBruteForce(int[] nums, int k) {
        int n = nums.length;
        double maxSum = Double.NEGATIVE_INFINITY;
        for (int start = 0; start <= n - k; start++) {
            int windowSum = 0;
            for (int j = start; j < start + k; j++) {
                windowSum += nums[j];
            }
            maxSum = Math.max(windowSum, maxSum);
        }
        return maxSum / k;
    }

    // @formatter:off
    /**
     *   | Approach       | Time   | Space | Code Complexity | Recommended?                     |
     *   |----------------|--------|-------|-----------------|----------------------------------|
     *   | Prefix Sum     | O(n)   | O(n)  | Simple          | OK when many range queries needed|
     * 
     * @param nums
     * @param k
     * @return
     */
    // @formatter:on
    public double findMaxAveragePrefixSum(int[] nums, int k) {
        int n = nums.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        long maxSum = Integer.MIN_VALUE;
        for (int i = 0; i + k <= n; i++) {
            long windowSum = prefix[i + k] - prefix[i];
            maxSum = Math.max(maxSum, windowSum);
        }
        return (double) maxSum / k;
    }

    // @formatter:off
    /**
     *   | Approach       | Time   | Space | Code Complexity | Recommended?                     |
     *   |----------------|--------|-------|-----------------|----------------------------------|
     *   | Sliding Window | O(n)   | O(1)  | Simple          | BEST - optimal on time and space |
     * 
     * @param nums
     * @param k
     * @return
     */
    // @formatter:on
    public double findMaxAverageSlidingWindow(int[] nums, int k) {
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }

        int maxSum = windowSum;
        for (int i = k; i < nums.length; i++) {
            windowSum += nums[i] - nums[i - k];
            maxSum = Math.max(maxSum, windowSum);
        }
        return (double) maxSum / k;
    }
}

// @formatter:off
/*
 * ============================================================
 * MAXIMUM AVERAGE SUBARRAY I - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are given an integer array and a fixed window length k. Among ALL
 * contiguous subarrays of length exactly k, find the one whose average value
 * (arithmetic mean) is the largest, and return that maximum average.
 *
 * This is LeetCode #643 - Maximum Average Subarray I (Easy).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 *   int[] nums - an array of n integers (may include negatives and zeros).
 *   int k      - the exact length of the subarray to consider, 1 <= k <= n.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 *   A single double - the maximum average. Answers within 1e-5 of the true
 *   value are accepted.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 *   | Constraint        | Value                |
 *   |-------------------|----------------------|
 *   | n == nums.length  | 1 <= n <= 10^5       |
 *   | nums[i] range     | -10^4 <= nums[i] <= 10^4 |
 *   | k range           | 1 <= k <= n          |
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Over every window nums[i .. i+k-1], compute its sum, divide by k, and return
 * the maximum such quotient. Because k is constant across all windows,
 * maximizing the AVERAGE is equivalent to maximizing the SUM - divide only
 * once at the end.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    nums = [1, 12, -5, -6, 50, 3],  k = 4
 *
 *    Windows of length 4:
 *      [1, 12, -5, -6]   sum =  2   avg =  0.5
 *      [12, -5, -6, 50]  sum = 51   avg = 12.75   <- best
 *      [-5, -6, 50, 3]   sum = 42   avg = 10.5
 *
 *    Output: 12.75000
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Imagine a window of exactly k seats sliding along a row. Instead of
 * recounting everyone every time you shift by one seat, you just note who LEFT
 * (the seat behind) and who JOINED (the seat ahead). One subtraction, one
 * addition - the window's total updates in constant time.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 *   1. Since k is fixed, "maximum average" = "maximum sum / k", so focus on
 *      the window SUM.
 *   2. Compute the sum of the very first window (nums[0..k-1]) once.
 *   3. To move the window right by one: subtract the element that falls off the
 *      left, add the new element on the right. New window sum in O(1).
 *   4. Track the largest window sum seen. Divide by k at the very end.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 *   | Challenge                  | Why it's tricky                              |
 *   |----------------------------|----------------------------------------------|
 *   | Recomputing sums naively   | Summing each window from scratch is O(n*k).  |
 *   | Negative numbers           | Can't assume the best window sits on big     |
 *   |                            | numbers; a negative can drag it down.        |
 *   | Integer vs floating point  | Sum fits in int (~10^9) but average is       |
 *   |                            | fractional - divide as double.               |
 *   | Initializing the max       | Starting maxSum at 0 breaks on all-negative  |
 *   |                            | arrays; seed from the first window instead.  |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 *   | # | Approach        | Key Idea                                   | Best Used When                   | Time    | Space |
 *   |---|-----------------|--------------------------------------------|----------------------------------|---------|-------|
 *   | 1 | Brute Force     | Re-sum every window of length k            | Tiny inputs / correctness baseline | O(n*k) | O(1)  |
 *   | 2 | Prefix Sum      | pre[i+k] - pre[i] gives window sum in O(1) | Many range-sum queries needed    | O(n)    | O(n)  |
 *   | 3 | Sliding Window * | Running sum: drop outgoing, add incoming  | Optimal, general-purpose         | O(n)    | O(1)  |
 *
 *   (* = optimal)
 *
 * All three are correct. Brute force wastes work re-adding overlapping
 * elements. Prefix sum drops time to O(n) but pays O(n) auxiliary space.
 * Sliding window achieves the same O(n) time while keeping space at O(1) - it
 * dominates prefix sum on space and brute force on time. There is no trade-off
 * to weigh: sliding window is best on BOTH axes. Prefix sum is still worth
 * knowing because its "range sum in O(1)" idea generalizes to problems with
 * many arbitrary queries.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Loop the window start index i from 0 to n - k.
 *   2. For each start, add up the k elements nums[i .. i+k-1].
 *   3. Track the maximum sum encountered.
 *   4. Return maxSum / k as a double.
 *
 *    public class MaxAverageBruteForce {
 *        public double findMaxAverage(int[] nums, int k) {
 *            int n = nums.length;
 *            double maxSum = Double.NEGATIVE_INFINITY;
 *            for (int start = 0; start <= n - k; start++) {
 *                int windowSum = 0;
 *                for (int j = start; j < start + k; j++) {
 *                    windowSum += nums[j];
 *                }
 *                maxSum = Math.max(maxSum, windowSum);
 *            }
 *            return maxSum / k;
 *        }
 *        public static void main(String[] args) {
 *            MaxAverageBruteForce solver = new MaxAverageBruteForce();
 *            System.out.println(solver.findMaxAverage(new int[]{1,12,-5,-6,50,3}, 4)); // 12.75
 *        }
 *    }
 *
 * Note: outer loop runs n-k+1 times, inner loop k times -> O(n*k).
 *
 * ------------------------------------------------------------
 * Approach 2: Prefix Sum
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Build prefix[0..n] where prefix[i] = sum of first i elements.
 *   2. Sum of window nums[i .. i+k-1] = prefix[i+k] - prefix[i].
 *   3. Slide i from 0 to n-k, computing each window sum in O(1), tracking max.
 *   4. Return maxSum / k.
 *
 *    public class MaxAveragePrefixSum {
 *        public double findMaxAverage(int[] nums, int k) {
 *            int n = nums.length;
 *            long[] prefix = new long[n + 1];
 *            for (int i = 0; i < n; i++) {
 *                prefix[i + 1] = prefix[i] + nums[i];
 *            }
 *            long maxSum = Long.MIN_VALUE;
 *            for (int i = 0; i + k <= n; i++) {
 *                long windowSum = prefix[i + k] - prefix[i];
 *                maxSum = Math.max(maxSum, windowSum);
 *            }
 *            return (double) maxSum / k;
 *        }
 *        public static void main(String[] args) {
 *            MaxAveragePrefixSum solver = new MaxAveragePrefixSum();
 *            System.out.println(solver.findMaxAverage(new int[]{1,12,-5,-6,50,3}, 4)); // 12.75
 *        }
 *    }
 *
 * Note: prefix[i+k] - prefix[i] telescopes to nums[i] + ... + nums[i+k-1].
 *
 * ------------------------------------------------------------
 * Approach 3: Sliding Window * (Optimal)
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Compute sum of first window nums[0..k-1]; set maxSum = windowSum.
 *   2. For each new right edge i from k to n-1:
 *        windowSum += nums[i] - nums[i-k]  (add incoming, drop outgoing).
 *   3. Update maxSum = max(maxSum, windowSum).
 *   4. Return maxSum / k.
 *
 *    public class MaxAverageSlidingWindow {
 *        public double findMaxAverage(int[] nums, int k) {
 *            int windowSum = 0;
 *            for (int i = 0; i < k; i++) {
 *                windowSum += nums[i];
 *            }
 *            int maxSum = windowSum;
 *            for (int i = k; i < nums.length; i++) {
 *                windowSum += nums[i] - nums[i - k];
 *                maxSum = Math.max(maxSum, windowSum);
 *            }
 *            return (double) maxSum / k;
 *        }
 *        public static void main(String[] args) {
 *            MaxAverageSlidingWindow solver = new MaxAverageSlidingWindow();
 *            System.out.println(solver.findMaxAverage(new int[]{1,12,-5,-6,50,3}, 4)); // 12.75
 *            System.out.println(solver.findMaxAverage(new int[]{-1}, 1));               // -1.0
 *        }
 *    }
 *
 * Note: cast to double BEFORE dividing. maxSum / k with two ints does integer
 * division (51 / 4 = 12, losing the .75); (double) maxSum / k promotes first.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 - Brute Force
 * ------------------------------------------------------------
 *   Time:  O(n*k). Outer n-k+1 iterations, inner k additions each. For
 *          n=10^5, k=5*10^4 that is ~2.5*10^9 ops - too slow.
 *   Space: O(1). A couple of scalars.
 *   Estimate: n=10, k=4 -> (10-4+1)*4 = 28 additions.
 *
 * ------------------------------------------------------------
 * Approach 2 - Prefix Sum
 * ------------------------------------------------------------
 *   Time:  O(n). One pass to build prefix, one pass over windows (~2n).
 *   Space: O(n). The prefix array of n+1 longs.
 *   Estimate: n=10^5 -> ~2*10^5 ops, array of 100001 longs (~800 KB).
 *
 * ------------------------------------------------------------
 * Approach 3 - Sliding Window *
 * ------------------------------------------------------------
 *   Time:  O(n). k additions to build first window, then n-k O(1) slides (~n).
 *   Space: O(1). Just windowSum and maxSum.
 *   Estimate: n=10^5 -> ~10^5 ops, zero extra array allocation.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * Using nums = [1, 12, -5, -6, 50, 3], k = 4 for all three.
 *
 * ------------------------------------------------------------
 * Approach 1 - Brute Force
 * ------------------------------------------------------------
 *   start = 0 -> 1 + 12 + (-5) + (-6) = 2      maxSum = 2
 *   start = 1 -> 12 + (-5) + (-6) + 50 = 51    maxSum = 51
 *   start = 2 -> (-5) + (-6) + 50 + 3 = 42     maxSum = 51
 *   Result: 51 / 4 = 12.75
 *
 * ------------------------------------------------------------
 * Approach 2 - Prefix Sum
 * ------------------------------------------------------------
 *   prefix = [0, 1, 13, 8, 2, 52, 55]
 *             i:  0  1   2  3  4   5   6
 *   window i=0: prefix[4] - prefix[0] = 2  - 0  = 2      maxSum = 2
 *   window i=1: prefix[5] - prefix[1] = 52 - 1  = 51     maxSum = 51
 *   window i=2: prefix[6] - prefix[2] = 55 - 13 = 42     maxSum = 51
 *   Result: 51 / 4 = 12.75
 *
 * ------------------------------------------------------------
 * Approach 3 - Sliding Window *
 * ------------------------------------------------------------
 *   First window [1,12,-5,-6]        windowSum = 2     maxSum = 2
 *   i = 4 (add 50, drop nums[0]=1):
 *         windowSum = 2 + 50 - 1 = 51                   maxSum = 51
 *   i = 5 (add 3,  drop nums[1]=12):
 *         windowSum = 51 + 3 - 12 = 42                  maxSum = 51
 *   Result: 51 / 4 = 12.75
 *
 * All three agree: 12.75
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 *   | Edge Case                | Input                  | Expected | How Handled                          |
 *   |--------------------------|------------------------|----------|--------------------------------------|
 *   | k == n (only one window) | nums=[1,2,3], k=3      | 2.0      | First window runs; slide loop skips. |
 *   | k == 1 (single elements) | nums=[5,-2,9], k=1     | 9.0      | Window sum = one element; take max.  |
 *   | Single element           | nums=[-1], k=1         | -1.0     | maxSum seeded from first window.     |
 *   | All negative values      | nums=[-3,-1,-4], k=2   | -2.0     | maxSum seeded (not 0), keeps neg max.|
 *   | Max magnitude / overflow | nums=[10^4,...], k=10^5| large    | Sum ~10^9 fits in int; prefix=long.  |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 *   WRONG: seeding maxSum at 0 fails when all numbers are negative
 *      int maxSum = 0;
 *      // nums=[-3,-1], k=2 -> windowSum=-4, maxSum stays 0 -> returns 0.0 (wrong)
 *   CORRECT: seed from the actual first window
 *      int maxSum = windowSum;
 *
 *   WRONG: integer division truncates the fractional part
 *      return maxSum / k;          // 51 / 4 -> 12 (loses .75)
 *   CORRECT: promote to double before dividing
 *      return (double) maxSum / k; // 12.75
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The all-negative case (if maxSum is mis-seeded at 0), and k == n (ensure
 *    the slide loop simply does not run). Both handled by seeding maxSum from
 *    the first window and using i < nums.length as the slide bound.
 *
 * Q: Are there any type mismatches?
 * A: The final division. windowSum and k are both int; dividing does integer
 *    division. The (double) cast fixes it. Sums stay within int here
 *    (~10^9 < 2.1*10^9); the prefix version uses long defensively.
 *
 * Q: How can I verify this works right now?
 *    public static void verify() {
 *        MaxAverageSlidingWindow s = new MaxAverageSlidingWindow();
 *        assert Math.abs(s.findMaxAverage(new int[]{1,12,-5,-6,50,3}, 4) - 12.75) < 1e-5;
 *        assert Math.abs(s.findMaxAverage(new int[]{-1}, 1) - (-1.0))            < 1e-5;
 *        assert Math.abs(s.findMaxAverage(new int[]{-3,-1,-4}, 2) - (-2.0))      < 1e-5;
 *        assert Math.abs(s.findMaxAverage(new int[]{5,-2,9}, 1) - 9.0)           < 1e-5;
 *        System.out.println("All assertions passed.");
 *    }
 *    // Run with:  java -ea MaxAverageSlidingWindow
 *
 *   | Approach       | Risk                              | Mitigation                                   |
 *   |----------------|-----------------------------------|----------------------------------------------|
 *   | Brute Force    | Too slow for large n*k            | Baseline only; use sliding window for real.  |
 *   | Prefix Sum     | Extra O(n) memory                 | Fine for range queries; else sliding window. |
 *   | Sliding Window | Mis-seeded maxSum / int division  | Seed from first window; cast before dividing.|
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #643 - Maximum Average Subarray I. Difficulty: Easy. A very common
 * warm-up for the sliding-window pattern; frequent in phone screens and OAs.
 *
 *   | Company          | Frequency | Notes                                      |
 *   |------------------|-----------|--------------------------------------------|
 *   | Amazon           | ****      | Popular OA / phone-screen intro.           |
 *   | Google           | ***       | Lead-in before harder variable windows.    |
 *   | Microsoft        | ***       | Common warm-up in early rounds.            |
 *   | Meta             | ***       | Tests the fixed-window idea.               |
 *   | Apple            | **        | Occasional screening question.             |
 *   | Bloomberg        | ***       | Frequent for array/window fundamentals.    |
 *   | Adobe            | **        | Appears in OA sets.                        |
 *   | Uber             | **        | Sliding-window basics.                      |
 *   | Goldman Sachs    | **        | Assessment-style question.                 |
 *   | TikTok/ByteDance | **        | Early-round array question.                |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 *   | Approach       | Time   | Space | Code Complexity | Recommended?                     |
 *   |----------------|--------|-------|-----------------|----------------------------------|
 *   | Brute Force    | O(n*k) | O(1)  | Very simple     | X Too slow - baseline only       |
 *   | Prefix Sum     | O(n)   | O(n)  | Simple          | OK when many range queries needed|
 *   | Sliding Window | O(n)   | O(1)  | Simple          | BEST - optimal on time and space |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use the sliding window. It hits O(n) time and O(1) space simultaneously - no
 * trade-off exists here, so unlike many problems there is no "best-for-time vs
 * best-for-space" split. Reach for prefix sum only when the broader problem
 * needs repeated arbitrary range-sum queries.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * For a FIXED-length window, never recompute the sum - slide it:
 * windowSum += nums[i] - nums[i-k]. Seed your max from the FIRST real window
 * (not 0) so all-negative inputs work, and cast to double BEFORE dividing so
 * you do not silently truncate the fractional average.
 *
 * ============================================================
 * END OF EXPLANATION
 * ============================================================
 */
// @formatter:on
