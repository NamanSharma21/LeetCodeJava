package Array;

public class MaximumProductSubarray {
    public static void main(String[] args) {
        MaximumProductSubarray maximumProductSubarray = new MaximumProductSubarray();
        System.out.println(
                "MaximumProductSubarray : " + maximumProductSubarray.maxProductBruteForce(new int[] { 2, 3, -2, 4 }));
        System.out.println(
                "MaximumProductSubarray : " + maximumProductSubarray.maxProductBruteForce(new int[] { -2, 0, -1 }));
        System.out.println("---------------------------------------");
        System.out.println("MaximumProductSubarray : "
                + maximumProductSubarray.maxProductDPTrackMaxMin(new int[] { 2, 3, -2, 4 }));
        System.out.println("MaximumProductSubarray : "
                + maximumProductSubarray.maxProductDPTrackMaxMin(new int[] { -2, 0, -1 }));
        System.out.println("---------------------------------------");
        System.out.println("MaximumProductSubarray : "
                + maximumProductSubarray.maxProductPrefixSuffixProducts(new int[] { 2, 3, -2, 4 }));
        System.out.println("MaximumProductSubarray : "
                + maximumProductSubarray.maxProductPrefixSuffixProducts(new int[] { -2, 0, -1 }));
    }

    // @formatter:off
    /**
     * 
     * https://leetcode.com/problems/maximum-product-subarray/description/
     * 
     * Given an integer array nums, find a subarray that has the largest product,
     * and return the product.
     * 
     * The test cases are generated so that the answer will fit in a 32-bit integer.
     * 
     * Note that the product of an array with a single element is the value of that
     * element.
     * 
     * 
     * 
     * ​​​​​​​Example 1:
     * 
     * Input: nums = [2,3,-2,4]
     * Output: 6
     * Explanation: [2,3] has the largest product 6.
     * Example 2:
     * 
     * Input: nums = [-2,0,-1]
     * Output: 0
     * Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 2 * 104
     * -10 <= nums[i] <= 10
     * The product of any subarray of nums is guaranteed to fit in a 32-bit integer.
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach                | Time   | Space | Code Complexity | Recommended?                          |
     * |-------------------------|--------|-------|-----------------|---------------------------------------|
     * | Brute Force             | O(n^2) | O(1)  | Very simple     | NO - too slow; test oracle only       |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int maxProductBruteForce(int[] nums) {
        int maxProduct = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int currentProd = 1;
            for (int j = i; j < nums.length; j++) {
                currentProd *= nums[j];
                maxProduct = Math.max(maxProduct, currentProd);
            }
        }
        return maxProduct;
    }

    // @formatter:off
    /**
     * 
     * | Approach                | Time   | Space | Code Complexity | Recommended?                          |
     * |-------------------------|--------|-------|-----------------|---------------------------------------|
     * | DP: Track Max & Min     | O(n)   | O(1)  | Moderate        | YES YES - best overall (canonical)    |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int maxProductDPTrackMaxMin(int[] nums) {
        int curMax = nums[0];
        int curMin = nums[0];
        int result = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int n = nums[i];
            if (n < 0) {
                int temp = curMax;
                curMax = curMin;
                curMin = temp;
            }

            curMax = Math.max(n, curMax * n);
            curMin = Math.min(n, curMin * n);
            result = Math.max(result, curMax);
        }
        return result;
    }

    // @formatter:off
    /**
     * 
     * | Approach                | Time   | Space | Code Complexity | Recommended?                          |
     * |-------------------------|--------|-------|-----------------|---------------------------------------|
     * | Prefix x Suffix Products| O(n)   | O(1)  | Simple/short    | YES - great alternative               |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int maxProductPrefixSuffixProducts(int[] nums) {
        int n = nums.length;
        int prefix = 0, suffix = 0;
        int result = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            prefix = (prefix == 0 ? 1 : prefix) * nums[i];
            suffix = (suffix == 0 ? 1 : suffix) * nums[n - 1 - i];
            result = Math.max(result, Math.max(prefix, suffix));
        }
        return result;
    }
}

// @formatter:off
/*
 * ============================================================
 * MAXIMUM PRODUCT SUBARRAY - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an integer array, find the CONTIGUOUS subarray (containing at
 * least one number) that has the LARGEST product, and return that product.
 * "Contiguous" is key: you must pick a run of adjacent elements - you
 * cannot skip elements in the middle.
 * This is LeetCode #152, difficulty Medium.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums - an array of integers that may include positives,
 * negatives, and zeros.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * A single int - the maximum product achievable by any contiguous
 * subarray. The problem guarantees the answer fits in a 32-bit integer.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * 1 <= nums.length <= 2 * 10^4
 * -10 <= nums[i] <= 10
 * The product of any prefix or suffix of nums fits in a 32-bit integer.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * The maximum over all pairs (i, j) with i <= j of
 * nums[i] * nums[i+1] * ... * nums[j].
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  nums = [2, 3, -2, 4]
 *    Output: 6
 *    Explanation: The subarray [2, 3] has the largest product = 6.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * For a SUM problem (Kadane) you track one running best. For a PRODUCT
 * problem there is a twist: a negative times a negative becomes positive.
 * That means the SMALLEST (most negative) running product is valuable too
 * - one more negative can catapult it into the LARGEST positive product.
 * So at every position we track TWO running values: the max product
 * ending here and the min product ending here.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Walk left to right, keeping a running product of the current subarray.
 * 2. Positives make the running product bigger - good.
 * 3. A ZERO annihilates everything: any subarray crossing it has product 0,
 *    so it is a hard reset point.
 * 4. A NEGATIVE flips the sign: current max becomes negative and current
 *    min becomes positive. Keep the min around - it is your insurance for
 *    the next negative.
 * 5. Because a negative swaps roles, the new max might come from
 *    min * nums[i], and the new min from max * nums[i]. Always also
 *    consider starting fresh at nums[i] itself.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                        | Why it's tricky                                                             |
 * |----------------------------------|-----------------------------------------------------------------------------|
 * | Negative numbers flip sign       | Largest product can emerge from the smallest prior product.                 |
 * | Zeros reset the chain            | A zero forces the product to 0; must be able to restart.                    |
 * | Two negatives beat all positives | [-2,-3,-4] -> best is [-2,-3]=6, not the whole array -24.                    |
 * | Can't just adapt Kadane naively  | max(nums[i], prev+nums[i]) doesn't account for the min-becomes-max flip.    |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                | Key Idea                                                  | Best Used When                        | Time    | Space        |
 * |---|-------------------------|-----------------------------------------------------------|---------------------------------------|---------|--------------|
 * | 1 | Brute Force             | Product of every subarray (i, j); track the maximum       | Tiny inputs / obvious baseline        | O(n^2)  | O(1) (tied)  |
 * | 2 | DP: Track Max & Min     | Keep curMax and curMin; a negative swaps their roles      | General case - standard optimal       | O(n) OK | O(1)         |
 * | 3 | Prefix x Suffix Products| Scan products from both ends, reset to 1 on a zero        | Short, symmetric, clean zero handling | O(n)    | O(1)         |
 *
 * All three use O(1) auxiliary space, so space is not the differentiator -
 * TIME is. Brute force is O(n^2) and only viable for tiny inputs.
 * Approaches 2 and 3 are both O(n) time and O(1) space; they are genuinely
 * distinct techniques. Approach 2 is the canonical optimal and the one to
 * reach for in interviews; Approach 3 is an elegant alternative where zeros
 * are handled by a simple reset rather than sign bookkeeping.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * 1. Initialize result to the smallest possible value.
 * 2. For each starting index i, reset product = 1.
 * 3. For each ending index j from i to n-1, multiply product by nums[j].
 * 4. Update result with product after each multiplication.
 * 5. Return result.
 *
 *    public class MaxProductBruteForce {
 *        public int maxProduct(int[] nums) {
 *            int result = Integer.MIN_VALUE;
 *            for (int i = 0; i < nums.length; i++) {
 *                int product = 1;
 *                for (int j = i; j < nums.length; j++) {
 *                    product *= nums[j];          // product of nums[i..j]
 *                    result = Math.max(result, product);
 *                }
 *            }
 *            return result;
 *        }
 *
 *        public static void main(String[] args) {
 *            MaxProductBruteForce solver = new MaxProductBruteForce();
 *            System.out.println(solver.maxProduct(new int[]{2, 3, -2, 4})); // 6
 *            System.out.println(solver.maxProduct(new int[]{-2, 0, -1}));   // 0
 *        }
 *    }
 *
 * We build the product incrementally as j advances, so each subarray costs
 * O(1) extra work - but there are still O(n^2) subarrays.
 *
 * ------------------------------------------------------------
 * Approach 2: DP - Track Max & Min  [OPTIMAL]
 * ------------------------------------------------------------
 * 1. Initialize curMax = curMin = result = nums[0].
 * 2. For each subsequent element n = nums[i]:
 *    - If n < 0, swap curMax and curMin (multiplying by a negative flips
 *      which one is largest).
 *    - curMax = max(n, curMax * n)   -> start fresh or extend.
 *    - curMin = min(n, curMin * n).
 *    - result = max(result, curMax).
 * 3. Return result.
 *
 *    public class MaxProductDP {
 *        public int maxProduct(int[] nums) {
 *            int curMax = nums[0];
 *            int curMin = nums[0];
 *            int result = nums[0];
 *
 *            for (int i = 1; i < nums.length; i++) {
 *                int n = nums[i];
 *                if (n < 0) {                     // negative flips max <-> min
 *                    int temp = curMax;
 *                    curMax = curMin;
 *                    curMin = temp;
 *                }
 *                curMax = Math.max(n, curMax * n); // extend or restart
 *                curMin = Math.min(n, curMin * n);
 *                result = Math.max(result, curMax);
 *            }
 *            return result;
 *        }
 *
 *        public static void main(String[] args) {
 *            MaxProductDP solver = new MaxProductDP();
 *            System.out.println(solver.maxProduct(new int[]{2, 3, -2, 4})); // 6
 *            System.out.println(solver.maxProduct(new int[]{-2, 3, -4}));   // 24
 *            System.out.println(solver.maxProduct(new int[]{-2, 0, -1}));   // 0
 *        }
 *    }
 *
 * Why the swap works: multiplying every candidate by a negative n reverses
 * the number line. The old minimum becomes the maximum after multiplication
 * and vice versa. Swapping first, then applying the same max/min formulas,
 * keeps the logic uniform. Comparing against n alone handles zeros and the
 * restart case: after a zero, curMax*n and curMin*n are 0, and max(n, 0)
 * correctly restarts from the fresh element.
 *
 * ------------------------------------------------------------
 * Approach 3: Prefix x Suffix Products
 * ------------------------------------------------------------
 * 1. Keep prefix (left->right) and suffix (right->left), both starting at 0.
 * 2. For each index i from 0 to n-1:
 *    - If prefix is 0, reset to 1; multiply by nums[i].
 *    - If suffix is 0, reset to 1; multiply by nums[n-1-i].
 *    - Update result with both prefix and suffix.
 * 3. Return result.
 *
 *    public class MaxProductPrefixSuffix {
 *        public int maxProduct(int[] nums) {
 *            int n = nums.length;
 *            int prefix = 0, suffix = 0;
 *            int result = Integer.MIN_VALUE;
 *
 *            for (int i = 0; i < n; i++) {
 *                prefix = (prefix == 0 ? 1 : prefix) * nums[i];
 *                suffix = (suffix == 0 ? 1 : suffix) * nums[n - 1 - i];
 *                result = Math.max(result, Math.max(prefix, suffix));
 *            }
 *            return result;
 *        }
 *
 *        public static void main(String[] args) {
 *            MaxProductPrefixSuffix solver = new MaxProductPrefixSuffix();
 *            System.out.println(solver.maxProduct(new int[]{2, 3, -2, 4})); // 6
 *            System.out.println(solver.maxProduct(new int[]{-2, 0, -1}));   // 0
 *        }
 *    }
 *
 * Why both ends suffice: the max-product subarray is bounded by two zeros
 * (or array ends). Within a zero-free block, the max is either the whole
 * block, or is achieved by chopping a chunk off the LEFT or the RIGHT to
 * discard an odd trailing negative. The prefix scan catches "chop from the
 * right"; the suffix scan catches "chop from the left". Together they cover
 * every optimal window.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Time:  O(n^2). n + (n-1) + ... + 1 = n(n+1)/2 multiplications.
 *        n = 20,000 -> ~2 x 10^8 ops - borderline too slow.
 * Space: O(1). Scalars result and product.
 * Counts: n = 5 -> 15 inner iterations. n = 1000 -> ~500,500.
 *
 * ------------------------------------------------------------
 * Approach 2: DP - Track Max & Min  [OPTIMAL]
 * ------------------------------------------------------------
 * Time:  O(n). One pass; constant work per step.
 * Space: O(1). Three scalars: curMax, curMin, result.
 * Counts: n = 5 -> 4 iterations. n = 20,000 -> ~8 x 10^4 ops total - fast.
 *
 * ------------------------------------------------------------
 * Approach 3: Prefix x Suffix Products
 * ------------------------------------------------------------
 * Time:  O(n). Single loop of n iterations, constant work.
 * Space: O(1). Scalars prefix, suffix, result.
 * Counts: n = 5 -> 5 iterations. n = 20,000 -> 20,000 iterations.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force on [2, 3, -2, 4]
 * ------------------------------------------------------------
 * | i(start) | j(end) | running product | result |
 * |----------|--------|-----------------|--------|
 * | 0        | 0      | 2               | 2      |
 * | 0        | 1      | 6               | 6      |
 * | 0        | 2      | -12             | 6      |
 * | 0        | 3      | -48             | 6      |
 * | 1        | 1      | 3               | 6      |
 * | 1        | 2      | -6              | 6      |
 * | 1        | 3      | -24             | 6      |
 * | 2        | 2      | -2              | 6      |
 * | 2        | 3      | -8              | 6      |
 * | 3        | 3      | 4               | 6      |
 * Output: 6
 *
 * ------------------------------------------------------------
 * Approach 2: DP on [-2, 3, -4]
 * ------------------------------------------------------------
 * Init: curMax = -2, curMin = -2, result = -2
 *
 * i=1, n=3 (positive, no swap):
 * |- curMax = max(3, -2*3 = -6)  = 3
 * |- curMin = min(3, -2*3 = -6)  = -6
 * |_ result = max(-2, 3)         = 3
 *
 * i=2, n=-4 (negative -> swap first):
 * |- swap: curMax = -6, curMin = 3
 * |- curMax = max(-4, -6*-4 = 24)  = 24
 * |- curMin = min(-4,  3*-4 = -12) = -12
 * |_ result = max(3, 24)           = 24
 *
 * Output: 24 - the two negatives -2 and -4 (with 3 between) multiply to a
 * large positive, captured only because we kept curMin.
 *
 * ------------------------------------------------------------
 * Approach 3: Prefix x Suffix on [-2, 0, -1]  (n = 3)
 * ------------------------------------------------------------
 * | i | prefix step           | prefix | suffix step (nums[n-1-i]) | suffix | result |
 * |---|-----------------------|--------|---------------------------|--------|--------|
 * | 0 | (0->1)*nums[0]=-2     | -2     | (0->1)*nums[2]=-1         | -1     | -1     |
 * | 1 | -2*nums[1]=0          | 0      | -1*nums[1]=0              | 0      | 0      |
 * | 2 | (0->1)*nums[2]=-1     | -1     | (0->1)*nums[0]=-2         | -2     | 0      |
 * Output: 0
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case               | Input             | Expected | How Handled                                   |
 * |-------------------------|-------------------|----------|-----------------------------------------------|
 * | Single element          | [5]               | 5        | result seeded with nums[0]; loop body skipped.|
 * | Single negative         | [-3]              | -3       | Sole element is the answer.                   |
 * | Contains a zero         | [-2, 0, -1]       | 0        | max(n, curMax*n) restarts at n; 0 is valid.   |
 * | All negatives, even cnt | [-1,-2,-3,-4]     | 24       | Whole-array product positive; min-tracking.   |
 * | All negatives, odd cnt  | [-1,-2,-3]        | 6        | Best is [-2,-3]=6; restart logic finds it.    |
 * | Leading/trailing zeros  | [0, 2, 3, 0]      | 6        | Middle block [2,3] scanned; zeros reset.      |
 * | Mix with big flip       | [-2, 3, -4]       | 24       | Swap-on-negative surfaces min*n as new max.   |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 - Forgetting to track the minimum.
 *    // WRONG: only tracks max, misses negative*negative
 *    curMax = Math.max(n, curMax * n);
 *    result = Math.max(result, curMax);
 *    // On [-2, 3, -4] this returns 3, not 24.
 *    // CORRECT: track both, swap on negative
 *    if (n < 0) { int t = curMax; curMax = curMin; curMin = t; }
 *    curMax = Math.max(n, curMax * n);
 *    curMin = Math.min(n, curMin * n);
 *
 * Pitfall 2 - Initializing result to 0.
 *    int result = 0;        // WRONG: fails on all-negative input like [-3]
 *    int result = nums[0];  // CORRECT: seed with a real element
 *
 * Pitfall 3 - Overwriting curMax before curMin reads it.
 *    // WRONG: curMax already overwritten when curMin uses it
 *    curMax = Math.max(n, curMax * n);
 *    curMin = Math.min(n, curMax * n);   // uses NEW curMax - bug
 *    // Fix: swap first (Approach 2), so both formulas read consistent values.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The riskiest are all-negative arrays (odd vs even length) and arrays
 *    where the max is a single element straddled by zeros. The result=nums[0]
 *    seed plus the max(n, ...) restart handle both; an empty array is
 *    excluded by constraints (length >= 1), but a guard is cheap.
 *
 * Q: Are there any type mismatches?
 * A: No. Inputs and answer are int, and the problem guarantees every
 *    prefix/suffix product fits in 32 bits, so no long is required. If
 *    constraints allowed larger values, promote curMax/curMin to long.
 *
 * Q: How can I verify this works right now?
 *    public class Verify {
 *        static int maxProduct(int[] nums) {
 *            int curMax = nums[0], curMin = nums[0], result = nums[0];
 *            for (int i = 1; i < nums.length; i++) {
 *                int n = nums[i];
 *                if (n < 0) { int t = curMax; curMax = curMin; curMin = t; }
 *                curMax = Math.max(n, curMax * n);
 *                curMin = Math.min(n, curMin * n);
 *                result = Math.max(result, curMax);
 *            }
 *            return result;
 *        }
 *        public static void main(String[] args) {
 *            assert maxProduct(new int[]{2, 3, -2, 4}) == 6;
 *            assert maxProduct(new int[]{-2, 0, -1}) == 0;
 *            assert maxProduct(new int[]{-2, 3, -4}) == 24;
 *            assert maxProduct(new int[]{-1, -2, -3}) == 6;
 *            assert maxProduct(new int[]{-3}) == -3;
 *            assert maxProduct(new int[]{0, 2, 3, 0}) == 6;
 *            System.out.println("All assertions passed.");
 *        }
 *    }
 *    // Run with:  java -ea Verify
 *
 * | Approach       | Risk                                   | Mitigation                                          |
 * |----------------|----------------------------------------|-----------------------------------------------------|
 * | Brute Force    | Too slow at n = 2x10^4                  | Use only as a correctness oracle for small tests.   |
 * | DP Max/Min     | Overwriting curMax before curMin reads | Swap first, then apply both formulas.               |
 * | Prefix/Suffix  | Forgetting the zero-reset              | Reset prefix/suffix to 1 on hitting 0 before x.     |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * LeetCode #152 - Difficulty: Medium - Very frequently asked.
 *
 * | Company        | Frequency | Notes                                                     |
 * |----------------|-----------|-----------------------------------------------------------|
 * | Amazon         | *****     | Classic array/DP screen; negative-handling follow-up.     |
 * | Microsoft      | *****     | Common phone-screen question.                             |
 * | Google         | ****      | Paired with Maximum Subarray (sum vs product).            |
 * | Meta           | ****      | Frequently asked, sometimes with the prefix/suffix twist. |
 * | Apple          | ***       | Appears in coding rounds.                                 |
 * | Bloomberg      | ****      | Popular for its clean O(n)/O(1) insight.                  |
 * | Adobe          | ***       | Recurring medium-tier problem.                            |
 * | Oracle         | ***       | Shows up in OA and onsite rounds.                         |
 * | Uber           | ***       | Asked in past cycles.                                     |
 * | Goldman Sachs  | **        | Occasional appearance in tech assessments.               |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach                | Time   | Space | Code Complexity | Recommended?                          |
 * |-------------------------|--------|-------|-----------------|---------------------------------------|
 * | Brute Force             | O(n^2) | O(1)  | Very simple     | NO - too slow; test oracle only       |
 * | DP: Track Max & Min     | O(n)   | O(1)  | Moderate        | YES YES - best overall (canonical)    |
 * | Prefix x Suffix Products| O(n)   | O(1)  | Simple/short    | YES - great alternative               |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use Approach 2 (track curMax and curMin, swapping on a negative). It is
 * O(n) time and O(1) space and is the solution interviewers expect. Both
 * O(n) approaches share the same profile, so pick DP for ubiquity or
 * Prefix/Suffix for its shorter, symmetric form.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * This is Kadane's sign-aware cousin: because a negative flips smallest into
 * largest, carry BOTH a running max and a running min, and swap them when
 * the current element is negative. Seed everything with nums[0] (never 0),
 * and let max(n, running*n) handle restarts after zeros for free.
 */
// @formatter:on
