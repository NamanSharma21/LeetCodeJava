package Array;

public class SubarrayProductLessThanK {
    public static void main(String[] args) {
        SubarrayProductLessThanK subarrayProductLessThanK = new SubarrayProductLessThanK();
        System.out.println("SubarrayProductLessThanK : " + subarrayProductLessThanK
                .numSubarrayProductLessThanKBruteForce(new int[] { 10, 5, 2, 6 }, 100));
        System.out.println("--------------------------------------------------");
        System.out.println("SubarrayProductLessThanK : " + subarrayProductLessThanK
                .numSubarrayProductLessThanKPrefixLogBinarySearch(new int[] { 10, 5, 2, 6 }, 100));
        System.out.println("--------------------------------------------------");
        System.out.println("SubarrayProductLessThanK : " + subarrayProductLessThanK
                .numSubarrayProductLessThanKSlidingWindow(new int[] { 10, 5, 2, 6 }, 100));
    }

    // @formatter:off
    /**
     * https://leetcode.com/problems/subarray-product-less-than-k/description/
     * 
     * You are given an array of integers nums and an integer k.
     * 
     * Return the number of contiguous subarrays where the product of all the
     * elements in the subarray is strictly less than k.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [10,5,2,6], k = 100
     * Output: 8
     * Explanation: The 8 subarrays that have product less than 100 are:
     * [10], [5], [2], [6], [10, 5], [5, 2], [2, 6], [5, 2, 6]
     * Note that [10, 5, 2] is not included as the product of 100 is not strictly
     * less than k.
     * Example 2:
     * 
     * Input: nums = [1,2,3], k = 0
     * Output: 0
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 3 * 104
     * 1 <= nums[i] <= 1000
     * 0 <= k <= 106
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach                   | Time       | Space | Code Complexity | Recommended?                          |
     * |----------------------------|------------|-------|-----------------|---------------------------------------|
     * | Brute force                | O(n^2)     | O(1)  | Low             | X Too slow at max n; baseline only    |
     * 
     * @param nums
     * @param k
     * @return
     */
    // @formatter:on
    public int numSubarrayProductLessThanKBruteForce(int[] nums, int k) {
        int n = nums.length;
        int count = 0;
        for (int start = 0; start < n; start++) {
            long product = 1;
            for (int end = start; end < n; end++) {
                product *= nums[end];
                if (product < k)
                    count++;
                else
                    break;
            }
        }
        return count;
    }

    // @formatter:off
    /**
     * 
     * | Approach                   | Time       | Space | Code Complexity | Recommended?                          |
     * |----------------------------|------------|-------|-----------------|---------------------------------------|
     * | Prefix-log + binary search | O(n log n) | O(n)  | High            | X Precision risk; not for production  |
     * 
     * @param nums
     * @param k
     * @return
     */
    // @formatter:on
    public int numSubarrayProductLessThanKPrefixLogBinarySearch(int[] nums, int k) {
        int n = nums.length;
        if (k <= 1)
            return 0;
        double logK = Math.log(k);
        double[] prefix = new double[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + Math.log(nums[i]);
        }

        int count = 0;
        double eps = 1e-9;
        for (int j = 0; j < n; j++) {
            double target = prefix[j + 1] - logK + eps;
            int lo = firstGreater(prefix, j + 1, target);
            count += (j + 1) - lo;
        }
        return count;
    }

    public int firstGreater(double[] prefix, int hi, double value) {
        int lo = 0;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (prefix[mid] > value)
                hi = mid;
            else
                lo = mid + 1;
        }
        return lo;
    }

    // @formatter:off
    /**
     * 
     * | Approach                   | Time       | Space | Code Complexity | Recommended?                          |
     * |----------------------------|------------|-------|-----------------|---------------------------------------|
     * | Sliding window             | O(n)       | O(1)  | Low             | OK OK Best on both time and space     |
     * 
     * @param nums
     * @param k
     * @return
     */
    // @formatter:on
    public int numSubarrayProductLessThanKSlidingWindow(int[] nums, int k) {
        if (k <= 1)
            return 0;
        int n = nums.length;
        int count = 0;
        int product = 1;
        int left = 0;
        for (int right = 0; right < n; right++) {
            product *= nums[right];
            while (product >= k) {
                product /= nums[left];
                left++;
            }
            count += right - left + 1;
        }
        return count;
    }
}

// @formatter:off
/*
 * ============================================================
 * SUBARRAY PRODUCT LESS THAN K - DEEP DIVE EXPLANATION
 * ============================================================
 * LeetCode #713 | Difficulty: Medium
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an array of POSITIVE integers and a threshold k, count how
 * many CONTIGUOUS subarrays have a product of all their elements
 * STRICTLY LESS THAN k. A subarray is a run of consecutive elements,
 * so [5, 2] counts but [10, 6] (skipping elements) does not.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * - int[] nums : an array of positive integers.
 * - int k      : the strict upper bound on the product.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * - int : the number of contiguous subarrays whose product < k.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * - 1 <= nums.length <= 3 * 10^4
 * - 1 <= nums[i]    <= 1000   (every element >= 1 : positivity is key)
 * - 0 <= k          <= 10^6
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * The total count of index pairs (i, j) with i <= j such that
 * nums[i] * nums[i+1] * ... * nums[j] < k. We want the COUNT,
 * not the subarrays themselves.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 * nums = [10, 5, 2, 6], k = 100  ->  8
 * Qualifying subarrays: [10], [5], [2], [6], [10,5], [5,2],
 * [2,6], [5,2,6]. Note [10,5,2] has product exactly 100, which
 * is NOT < 100, so it is excluded.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Because every element is >= 1, multiplying in one more element can
 * only keep the product the same or make it larger - it never shrinks.
 * That monotonicity means once a window's product hits k, the only way
 * to bring it back down is to drop elements from the LEFT. This is the
 * exact shape a SLIDING WINDOW is built for.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Fix the RIGHT end of the window at each position, one at a time.
 * 2. Multiply the new right element into a running product.
 * 3. If the product has reached or exceeded k, slide the LEFT end
 *    rightward - dividing those elements out - until product < k again.
 * 4. Now every window that ends at right and starts anywhere in
 *    [left, right] is valid. There are exactly right - left + 1 of
 *    them, so add that number.
 * 5. Move right forward and repeat.
 *
 * The insight in step 4 is the crux: if the WHOLE window [left..right]
 * has product < k, then every SUFFIX of it ending at right also has
 * product < k (dropping leading elements >= 1 can only shrink the
 * product). So one subtraction gives you a whole batch of subarrays.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                       | Why it's tricky                                          |
 * |---------------------------------|----------------------------------------------------------|
 * | Counting subarrays, not one     | Must convert a valid window into a COUNT; the            |
 * |                                 | right - left + 1 batch-counting trick is non-obvious.    |
 * | The k <= 1 boundary             | Products are always >= 1, so nothing can be < 1. Without |
 * |                                 | a guard the window shrinks past right and reads OOB.     |
 * | Strict < vs <=                  | Bound is strict. The exactly-k case ([10,5,2]) must be   |
 * |                                 | excluded, deciding >= vs > in the loop.                  |
 * | Avoiding double counting        | Anchoring the count to the RIGHT end guarantees each     |
 * |                                 | subarray is counted exactly once.                        |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 * | # | Approach                   | Key Idea                                  | Best Used When              | Time       | Space |
 * |---|----------------------------|-------------------------------------------|-----------------------------|------------|-------|
 * | 1 | Brute force (enumerate)    | For each start, extend end, multiply,     | Tiny inputs / baseline      | O(n^2)     | O(1)  |
 * |   |                            | break when product >= k                   |                             |            |       |
 * | 2 | Prefix-log + binary search | Turn products into sums via log; prefix   | Teaching product->sum       | O(n log n) | O(n)  |
 * |   |                            | sums + binary search count valid starts   | transform                   |            |       |
 * | 3 | Sliding window (2 ptr) *   | Grow right, shrink left while product >=k,| The intended solution;      | O(n)       | O(1)  |
 * |   |                            | add right - left + 1                       | positivity => monotonic     |            |       |
 *
 * (* = optimal)
 *
 * Approach 3 is optimal on BOTH axes: linear time and constant extra
 * space, strictly dominating brute force (same space, worse time) and
 * the log approach (worse on both). Approach 2 is a genuinely different
 * PARADIGM - it reframes the task as "count subarrays whose sum is below
 * a bound" - but pays with O(n) space and floating-point precision risk
 * near the exact-k boundary. Prefer the sliding window unless you want
 * to demonstrate the logarithm transform.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Algorithm:
 * 1. For each starting index start, initialize product = 1.
 * 2. Extend end from start rightward, multiplying nums[end] in.
 * 3. If product < k, increment the count.
 * 4. Otherwise break - since all elements >= 1, extending further can
 *    only grow the product, so no longer subarray here can qualify.
 *
 *    public class SolutionBrute {
 *        public int numSubarrayProductLessThanK(int[] nums, int k) {
 *            int count = 0;
 *            int n = nums.length;
 *            for (int start = 0; start < n; start++) {
 *                long product = 1; // guards overflow while extending
 *                for (int end = start; end < n; end++) {
 *                    product *= nums[end];
 *                    if (product < k) {
 *                        count++;
 *                    } else {
 *                        break; // monotonic growth: stop extending
 *                    }
 *                }
 *            }
 *            return count;
 *        }
 *
 *        public static void main(String[] args) {
 *            SolutionBrute s = new SolutionBrute();
 *            System.out.println(s.numSubarrayProductLessThanK(new int[]{10, 5, 2, 6}, 100)); // 8
 *        }
 *    }
 *
 * The break is an optimization justified by positivity, but the worst
 * case (all 1s with large k) never breaks, so the bound stays O(n^2).
 * long for product avoids overflow before the comparison.
 *
 * ------------------------------------------------------------
 * Approach 2: Prefix-Log + Binary Search
 * ------------------------------------------------------------
 * Algorithm:
 * 1. If k <= 1, return 0 (no positive product can be below 1).
 * 2. product < k  <=>  sum of logs < log(k), since log is increasing.
 * 3. Build a prefix-log array; prefix[i] = sum of log(nums[0..i-1]).
 *    It is non-decreasing since each log(nums[i]) >= 0.
 * 4. [i..j] valid  <=>  prefix[j+1] - prefix[i] < log(k)
 *                  <=>  prefix[i] > prefix[j+1] - log(k).
 * 5. For each right end j, binary-search the FIRST prefix index whose
 *    value exceeds prefix[j+1] - log(k); all starts there..j are valid.
 * 6. A small eps nudges the threshold up to keep the exact-k boundary
 *    on the correct (excluded) side despite rounding.
 *
 *    public class SolutionLog {
 *        public int numSubarrayProductLessThanK(int[] nums, int k) {
 *            if (k <= 1) return 0;
 *            int n = nums.length;
 *            double logK = Math.log(k);
 *            double[] prefix = new double[n + 1];
 *            for (int i = 0; i < n; i++) {
 *                prefix[i + 1] = prefix[i] + Math.log(nums[i]);
 *            }
 *            int count = 0;
 *            double eps = 1e-9; // guards the exact-k boundary
 *            for (int j = 0; j < n; j++) {
 *                double target = prefix[j + 1] - logK + eps;
 *                int lo = firstGreater(prefix, j + 1, target);
 *                count += (j + 1) - lo; // starts lo..j are valid
 *            }
 *            return count;
 *        }
 *
 *        // smallest index in [0, hi] with prefix[index] > value
 *        private int firstGreater(double[] prefix, int hi, double value) {
 *            int lo = 0;
 *            while (lo < hi) {
 *                int mid = (lo + hi) >>> 1;
 *                if (prefix[mid] > value) hi = mid;
 *                else lo = mid + 1;
 *            }
 *            return lo;
 *        }
 *
 *        public static void main(String[] args) {
 *            SolutionLog s = new SolutionLog();
 *            System.out.println(s.numSubarrayProductLessThanK(new int[]{10, 5, 2, 6}, 100)); // 8
 *        }
 *    }
 *
 * The + eps matters: for [10,5,2] the log-sum equals log(100) in exact
 * math, but rounding can drift it below, which would wrongly count the
 * product-100 subarray. Nudging up by 1e-9 keeps that boundary excluded
 * - though on adversarial inputs no fixed eps is perfectly safe, which
 * is this approach's central weakness.
 *
 * ------------------------------------------------------------
 * Approach 3: Sliding Window (Optimal) *
 * ------------------------------------------------------------
 * Algorithm:
 * 1. If k <= 1, return 0 (essential guard - else the window shrinks
 *    past right and reads out of bounds).
 * 2. Keep a running product, a left pointer, and a count.
 * 3. For each right, multiply nums[right] into product.
 * 4. While product >= k, divide out nums[left] and advance left.
 * 5. Add right - left + 1 - valid subarrays ending at right.
 *
 *    public class SolutionSliding {
 *        public int numSubarrayProductLessThanK(int[] nums, int k) {
 *            if (k <= 1) return 0; // no product of positives can be < 1
 *            int count = 0;
 *            int product = 1;
 *            int left = 0;
 *            for (int right = 0; right < nums.length; right++) {
 *                product *= nums[right];
 *                while (product >= k) {
 *                    product /= nums[left];
 *                    left++;
 *                }
 *                count += right - left + 1;
 *            }
 *            return count;
 *        }
 *
 *        public static void main(String[] args) {
 *            SolutionSliding s = new SolutionSliding();
 *            System.out.println(s.numSubarrayProductLessThanK(new int[]{10, 5, 2, 6}, 100)); // 8
 *        }
 *    }
 *
 * int is safe for product: whenever we multiply, the previous product
 * was already < k <= 10^6, so the new value is below 10^6 * 1000 = 10^9,
 * which fits a 32-bit int (max ~ 2.14 * 10^9). The k <= 1 guard is not
 * cosmetic - with k = 1 the while condition is always true, so left would
 * run past right and dereference outside the array.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 * ------------------------------------------------------------
 * Approach 1 - Brute Force
 * ------------------------------------------------------------
 * Time : O(n^2). Outer loop n times; inner up to n - start. Summed,
 *        n + (n-1) + ... + 1 = n(n+1)/2 = O(n^2). The break helps in
 *        practice but all-1s-with-large-k never breaks. n = 3*10^4:
 *        worst ~ 4.5 * 10^8 multiplications.
 * Space: O(1) - only scalar counters.
 *
 * ------------------------------------------------------------
 * Approach 2 - Prefix-Log + Binary Search
 * ------------------------------------------------------------
 * Time : O(n log n). Building prefix is O(n); then n binary searches,
 *        each O(log n). n = 3*10^4: ~ 3*10^4 * 15 ~ 4.5 * 10^5 compares.
 * Space: O(n) - the prefix array of n + 1 doubles.
 *
 * ------------------------------------------------------------
 * Approach 3 - Sliding Window
 * ------------------------------------------------------------
 * Time : O(n). right advances n times; left also advances at most n
 *        times total (never backward). Each element multiplied in once,
 *        divided out at most once - amortized O(1) per step, O(n) total.
 *        n = 3*10^4: ~ 6 * 10^4 pointer moves.
 * Space: O(1) - three scalars.
 *
 * ============================================================
 * 6. COMPLEXITY COMPARISON GRAPH
 * ============================================================
 * TIME - Size of input data (n)  vs  Time to complete
 * time |                                    / Brute        O(n^2)       Bad
 *      |                              /
 *      |                        /
 *      |                  /
 *      |             / /
 *      |        / /      ____________________ Log+BS      O(n log n)  Fair
 *      |     // ___/''''
 *      |  //_/''      _____________________ Sliding      O(n)  Good  * optimal
 *      |//_/'' ___/'''
 *      +--------------------------------------> n
 *        small                          large
 *
 * SPACE - Size of input data (n)  vs  Memory used
 *  mem |                                ___ Log+BS       O(n)         Good
 *      |                          ___/''
 *      |                    ___/''
 *      |              ___/''
 *      |        __/''
 *      |   _ /''
 *      |/_______________________________________ Brute & Sliding  O(1)  Excellent  * optimal
 *      +--------------------------------------> n
 *        small                          large
 *
 * On time, the sliding window is optimal (O(n), flattest curve); on
 * space, brute force and the sliding window tie at O(1) (optimal), while
 * the log approach costs O(n) - so the sliding window wins both axes.
 *
 * ============================================================
 * 7. COMPLETE WORKED EXAMPLES
 * ============================================================
 * Input for all three: nums = [10, 5, 2, 6], k = 100. Expected: 8.
 *
 * ------------------------------------------------------------
 * Approach 1 - Brute Force
 * ------------------------------------------------------------
 * | start | end sequence (product)                        | counted | running |
 * |-------|-----------------------------------------------|---------|---------|
 * | 0     | [10]=10 ok, [10,5]=50 ok, [10,5,2]=100 X break | 2       | 2       |
 * | 1     | [5]=5 ok, [5,2]=10 ok, [5,2,6]=60 ok           | 3       | 5       |
 * | 2     | [2]=2 ok, [2,6]=12 ok                          | 2       | 7       |
 * | 3     | [6]=6 ok                                       | 1       | 8       |
 * Final: 8.
 *
 * ------------------------------------------------------------
 * Approach 2 - Prefix-Log + Binary Search
 * ------------------------------------------------------------
 * prefix = [0, 2.3026, 3.9120, 4.6052, 6.3969], logK = log(100) ~ 4.6052,
 * eps = 1e-9.
 * | j | target = prefix[j+1] - logK + eps | lo (first prefix > target) | += (j+1)-lo | running |
 * |---|-----------------------------------|----------------------------|-------------|---------|
 * | 0 | 2.3026 - 4.6052 ~ -2.3026         | 0                          | 1 - 0 = 1   | 1       |
 * | 1 | 3.9120 - 4.6052 ~ -0.6931         | 0                          | 2 - 0 = 2   | 3       |
 * | 2 | 4.6052 - 4.6052 + eps ~ 1e-9      | 1 (prefix[0]=0 NOT > 1e-9) | 3 - 1 = 2   | 5       |
 * | 3 | 6.3969 - 4.6052 ~ 1.7918          | 1                          | 4 - 1 = 3   | 8       |
 * Final: 8. At j = 2 the eps keeps index 0 (product-100 subarray
 * [10,5,2]) excluded - exactly the boundary that flips under rounding.
 *
 * ------------------------------------------------------------
 * Approach 3 - Sliding Window
 * ------------------------------------------------------------
 * | right | nums[right] | product after x, then shrink   | left | add (r-l+1) | count |
 * |-------|-------------|--------------------------------|------|-------------|-------|
 * | 0     | 10          | 10                             | 0    | 1           | 1     |
 * | 1     | 5           | 50                             | 0    | 2           | 3     |
 * | 2     | 2           | 100 -> shrink /10 -> 10        | 1    | 2           | 5     |
 * | 3     | 6           | 60                             | 1    | 3           | 8     |
 * Final: 8. At right = 2 the product hit 100 >= k, so left advanced from
 * 0 to 1 (dividing out the 10), bringing the product back to 10.
 *
 * ============================================================
 * 8. EDGE CASES
 * ============================================================
 * | Edge Case                | Input                          | Expected | How Handled                                        |
 * |--------------------------|--------------------------------|----------|----------------------------------------------------|
 * | k <= 1 (impossible)      | nums=[1,2,3], k=0 or k=1        | 0        | if (k <= 1) return 0; also prevents OOB shrink.    |
 * | Single element < k       | nums=[1000], k=1000000         | 1        | product 1000 < k, adds right-left+1 = 1.           |
 * | Single element >= k      | nums=[1000], k=500             | 0        | product >= k, window shrinks so left>right, adds 0.|
 * | All ones, k > 1          | nums=[1,1,1], k=2              | 6        | product stays 1 < k; all n(n+1)/2 = 6 count.       |
 * | Exact-k product          | [10,5,2] within k=100          | excluded | Strict product >= k shrink drops exactly-100 win.  |
 * | Largest input            | n=3*10^4, values up to 1000    | varies   | Sliding stays O(n); int product safe below 10^9.   |
 *
 * ============================================================
 * 9. APPROACH RISK MITIGATION
 * ============================================================
 * | Approach                   | Risk                                             | Mitigation                                             |
 * |----------------------------|--------------------------------------------------|--------------------------------------------------------|
 * | Brute force                | O(n^2) times out at n=3*10^4; overflow extending | Baseline only; use long for product; rely on break.    |
 * | Prefix-log + binary search | FP rounding misclassifies products at/near k     | Add small eps to threshold; prefer integer method when |
 * |                            |                                                  | exactness is required.                                 |
 * | Sliding window             | k <= 1 shrinks window past right -> OOB read      | Guard if (k <= 1) return 0; verify product fits int.   |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 * | Approach                   | Time       | Space | Code Complexity | Recommended?                          |
 * |----------------------------|------------|-------|-----------------|---------------------------------------|
 * | Brute force                | O(n^2)     | O(1)  | Low             | X Too slow at max n; baseline only    |
 * | Prefix-log + binary search | O(n log n) | O(n)  | High            | X Precision risk; not for production  |
 * | Sliding window             | O(n)       | O(1)  | Low             | OK OK Best on both time and space     |
 *
 * Recommended Approach: The sliding window - optimal on time AND space
 * simultaneously, with no trade-off to weigh.
 *
 * What to Remember: When all elements are positive, a product-bounded
 * subarray count is a MONOTONIC sliding window: grow the right, shrink
 * the left while the product reaches the bound, and add right - left + 1
 * to batch-count every valid subarray ending at the current right. Never
 * forget the k <= 1 guard, and remember the < k bound is strict, which is
 * why the shrink condition is product >= k.
 */
// @formatter:on
