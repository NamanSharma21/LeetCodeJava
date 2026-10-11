package DynamicProgramming;

public class MaximumSubarray {
    public static void main(String[] args) {
        MaximumSubarray maximumSubarray = new MaximumSubarray();
        System.out.println(
                "Max Sum : " + maximumSubarray.maxSubArrayBruteForce(new int[] { -2, 1, -3, 4, -1, 2, 1, -5, 4 }));
        System.out.println("Max Sum : " + maximumSubarray.maxSubArrayBruteForce(new int[] { 5, 4, -1, 7, 8 }));
        System.out.println("Max Sum : " + maximumSubarray.maxSubArrayBruteForce(new int[] { -2, -1 }));
        System.out.println("-----------------");
        System.out.println(
                "Max Sum : " + maximumSubarray.maxSubArrayDivideConquer(new int[] { -2, 1, -3, 4, -1, 2, 1, -5, 4 }));
        System.out.println("Max Sum : " + maximumSubarray.maxSubArrayDivideConquer(new int[] { 5, 4, -1, 7, 8 }));
        System.out.println("Max Sum : " + maximumSubarray.maxSubArrayDivideConquer(new int[] { -2, -1 }));
        System.out.println("-----------------");
        System.out.println(
                "Max Sum : " + maximumSubarray.maxSubArrayKadanes(new int[] { -2, 1, -3, 4, -1, 2, 1, -5, 4 }));
        System.out.println("Max Sum : " + maximumSubarray.maxSubArrayKadanes(new int[] { 5, 4, -1, 7, 8 }));
        System.out.println("Max Sum : " + maximumSubarray.maxSubArrayKadanes(new int[] { -2, -1 }));
    }

    // @formatter:off
    /*
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/97/dynamic-programming/566/
     * 
     * Given an integer array nums, find the subarray with the largest sum, and
     * return its sum.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
     * Output: 6
     * Explanation: The subarray [4,-1,2,1] has the largest sum 6.
     * Example 2:
     * 
     * Input: nums = [1]
     * Output: 1
     * Explanation: The subarray [1] has the largest sum 1.
     * Example 3:
     * 
     * Input: nums = [5,4,-1,7,8]
     * Output: 23
     * Explanation: The subarray [5,4,-1,7,8] has the largest sum 23.
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 105
     * -104 <= nums[i] <= 104
     * 
     * 
     * Follow up: If you have figured out the O(n) solution, try coding another
     * solution using the divide and conquer approach, which is more subtle.
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach          | Time       | Space    | Code Complexity | Recommended?                          |
     * |-------------------|------------|----------|-----------------|---------------------------------------|
     * | Brute Force       | O(n^2)     | O(1)     | Very simple     | X Too slow for large n; teaching only |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int maxSubArrayBruteForce(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int currentSum = 0;
            for (int y = i; y < nums.length; y++) {
                currentSum += nums[y];
                if (currentSum > maxSum)
                    maxSum = currentSum;
            }
        }
        return maxSum;
    }

    // @formatter:off
    /**
     * 
     * | Approach          | Time       | Space    | Code Complexity | Recommended?                          |
     * |-------------------|------------|----------|-----------------|---------------------------------------|
     * | Divide & Conquer  | O(n log n) | O(log n) | Moderate        | V Good to know for interviews/variants|
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int maxSubArrayDivideConquer(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }

    private static int helper(int[] nums, int left, int right) {
        if (left == right)
            return nums[left];
        int mid = left + (right - left) / 2;
        int leftBest = helper(nums, left, mid);
        int rightBest = helper(nums, mid + 1, right);
        int crossBest = maxCrossing(nums, left, mid, right);
        return Math.max(Math.max(leftBest, rightBest), crossBest);
    }

    private static int maxCrossing(int[] nums, int left, int mid, int right) {
        int sum = 0, leftSum = Integer.MIN_VALUE;
        for (int i = mid; i >= left; i--) {
            sum += nums[i];
            leftSum = Math.max(leftSum, sum);
        }

        sum = 0;
        int rightSum = Integer.MIN_VALUE;
        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            rightSum = Math.max(rightSum, sum);
        }
        return leftSum + rightSum;
    }

    // @formatter:off
    /**
     * 
     * | Approach          | Time       | Space    | Code Complexity | Recommended?                          |
     * |-------------------|------------|----------|-----------------|---------------------------------------|
     * | Kadane's          | O(n)       | O(1)     | Simple          | VV Best - optimal on time and space   |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int maxSubArrayKadanes(int[] nums) {
        int sum = 0;
        int max = nums[0];

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            max = Math.max(sum, max);
            if (sum < 0) {
                sum = 0;
            }
        }
        return max;
    }
}

// @formatter:off
/*
 * ============================================================
 * MAXIMUM SUBARRAY - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an array of integers (which may contain negative numbers), find the
 * CONTIGUOUS subarray - a slice of consecutive elements - that has the largest
 * possible sum, and return that sum. This is LeetCode #53.
 *
 * The key word is *contiguous*: you cannot cherry-pick elements from different
 * positions. You must pick a start index i and an end index j (with i <= j) and
 * take everything in between.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * - A single array int[] nums of length n, where 1 <= n.
 * - Values may be negative, zero, or positive.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * - A single int - the maximum sum achievable by any contiguous subarray.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * - 1 <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * - The array is guaranteed non-empty, so there is always a valid subarray.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Among all O(n^2) possible contiguous subarrays, compute the maximum of their
 * sums. You are asked for the SUM value, not the indices (though the indices can
 * be recovered with small bookkeeping).
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
 *    Output: 6
 *    Reason: The subarray [4, -1, 2, 1] sums to 6, which is the largest.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Walk through the array once, keeping a "running total" of the best subarray
 * ending exactly at your current position. At each new element you face one
 * decision: extend the previous run, or start fresh from the current element.
 * You start fresh whenever the previous run has become a liability (its total
 * dropped below zero), because carrying a negative prefix can only drag down
 * whatever comes next.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * Imagine you're walking along a path collecting and losing coins, and you can
 * start recording your net gain at any point and stop at any later point.
 *   1. You begin recording at some element.
 *   2. As you move right, you keep a running sum of what you've collected.
 *   3. The moment your running stretch turns net-negative, it's pointless to
 *      keep dragging it forward - any future stretch is better off starting
 *      clean. So you drop the baggage and restart at the next element.
 *   4. Throughout, you remember the single best running sum ever seen. That
 *      remembered value is the answer.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                    | Why it's tricky                                                     |
 * |------------------------------|---------------------------------------------------------------------|
 * | All-negative arrays          | Empty subarray isn't allowed; answer is the least-negative element. |
 * | Extend vs. restart decision  | The whole algorithm hinges on one comparison; wrong direction fails.|
 * | Contiguity constraint        | You can't skip a bad middle element and rejoin later.               |
 * | Initialization               | Starting best at 0 breaks the all-negative case.                    |
 * | Integer overflow             | Large n and magnitudes can grow sums; accumulator type matters.     |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                 | Key Idea                                             | Best Used When                 | Time       | Space       |
 * |---|--------------------------|------------------------------------------------------|--------------------------------|------------|-------------|
 * | 1 | Brute Force (inner sum)  | Fix start i, extend j accumulating sum, track max    | Tiny inputs / baseline         | O(n^2)     | O(1)        |
 * | 2 | Divide & Conquer         | Best is in left half, right half, or crosses midpoint| Practicing D&C / variants      | O(n log n) | O(log n)    |
 * | 3 | Kadane's Algorithm (DP) V| Track best sum ending at each index; extend/restart  | Essentially always (default)   | O(n) V     | O(1) V      |
 *
 * Kadane's algorithm dominates on BOTH axes - fastest (single linear pass) and
 * most memory-frugal (O(1)) - so there is no time-vs-space trade-off. Brute
 * force is O(n^2) with no compensating benefit (educational only). Divide &
 * conquer is a beautiful O(n log n) technique worth knowing for interviews and
 * generalizations (e.g., segment-tree variants supporting updates), but it is
 * strictly slower and uses stack space. Prefer Kadane's unconditionally.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (running inner sum)
 * ------------------------------------------------------------
 * Steps:
 *   1. Initialize maxSum to Integer.MIN_VALUE.
 *   2. For each start index i:
 *        - reset currentSum to 0.
 *        - for each end index j from i: add nums[j] to currentSum.
 *        - update maxSum if currentSum is larger.
 *   3. Return maxSum.
 * This avoids recomputing sums from scratch (which would be O(n^3)) by extending
 * the running sum as j advances.
 *
 *    public class MaximumSubarrayBruteForce {
 *
 *        public static int maxSubArray(int[] nums) {
 *            int maxSum = Integer.MIN_VALUE;
 *
 *            for (int start = 0; start < nums.length; start++) {
 *                int currentSum = 0;
 *                for (int end = start; end < nums.length; end++) {
 *                    currentSum += nums[end];        // extend the window [start..end]
 *                    if (currentSum > maxSum) {
 *                        maxSum = currentSum;        // record the best seen so far
 *                    }
 *                }
 *            }
 *            return maxSum;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
 *            System.out.println(maxSubArray(nums)); // Expected: 6
 *        }
 *    }
 *
 * Starting maxSum at Integer.MIN_VALUE (not 0) makes this correct for
 * all-negative arrays.
 *
 * ------------------------------------------------------------
 * Approach 2: Divide & Conquer
 * ------------------------------------------------------------
 * Steps:
 *   1. Base case: a single element's max subarray is itself.
 *   2. Split the array at the midpoint mid.
 *   3. Recursively compute the best subarray entirely in the left half and
 *      entirely in the right half.
 *   4. Compute the best CROSSING subarray (must include mid and mid+1):
 *        - expand leftward from mid, tracking the best prefix sum.
 *        - expand rightward from mid+1, tracking the best suffix sum.
 *        - crossing best = leftBest + rightBest.
 *   5. Return max of left, right, cross.
 *
 *    public class MaximumSubarrayDivideConquer {
 *
 *        public static int maxSubArray(int[] nums) {
 *            return helper(nums, 0, nums.length - 1);
 *        }
 *
 *        private static int helper(int[] nums, int left, int right) {
 *            if (left == right) return nums[left];          // base case
 *
 *            int mid = left + (right - left) / 2;
 *
 *            int leftBest  = helper(nums, left, mid);       // best in left half
 *            int rightBest = helper(nums, mid + 1, right);  // best in right half
 *            int crossBest = maxCrossing(nums, left, mid, right);
 *
 *            return Math.max(Math.max(leftBest, rightBest), crossBest);
 *        }
 *
 *        private static int maxCrossing(int[] nums, int left, int mid, int right) {
 *            int sum = 0, leftSum = Integer.MIN_VALUE;
 *            for (int i = mid; i >= left; i--) {            // expand leftward
 *                sum += nums[i];
 *                leftSum = Math.max(leftSum, sum);
 *            }
 *
 *            sum = 0;
 *            int rightSum = Integer.MIN_VALUE;
 *            for (int j = mid + 1; j <= right; j++) {       // expand rightward
 *                sum += nums[j];
 *                rightSum = Math.max(rightSum, sum);
 *            }
 *
 *            return leftSum + rightSum;                     // must use both sides
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
 *            System.out.println(maxSubArray(nums)); // Expected: 6
 *        }
 *    }
 *
 * The crossing subarray is O(n) per level, over O(log n) levels, giving
 * O(n log n) overall (recurrence T(n) = 2T(n/2) + O(n), same as merge sort).
 *
 * ------------------------------------------------------------
 * Approach 3: Kadane's Algorithm (DP)  V
 * ------------------------------------------------------------
 * Steps:
 *   1. currentSum = best subarray sum ending at current index, init to nums[0].
 *   2. maxSum = best subarray sum seen anywhere, init to nums[0].
 *   3. For each element from index 1:
 *        - currentSum = max(nums[i], currentSum + nums[i])  (extend vs restart)
 *        - maxSum = max(maxSum, currentSum)
 *   4. Return maxSum.
 *
 *    public class MaximumSubarrayKadane {
 *
 *        public static int maxSubArray(int[] nums) {
 *            int currentSum = nums[0]; // best sum ending exactly at index i
 *            int maxSum = nums[0];     // best sum found anywhere so far
 *
 *            for (int i = 1; i < nums.length; i++) {
 *                // Either extend the previous run, or start fresh at nums[i]
 *                currentSum = Math.max(nums[i], currentSum + nums[i]);
 *                maxSum = Math.max(maxSum, currentSum);
 *            }
 *            return maxSum;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
 *            System.out.println(maxSubArray(nums)); // Expected: 6
 *        }
 *    }
 *
 * The line currentSum = max(nums[i], currentSum + nums[i]) is the heart of the
 * algorithm. When currentSum < 0 it restarts; otherwise it extends. Initializing
 * both variables to nums[0] (not 0) makes all-negative inputs correct.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 - Brute Force
 * ------------------------------------------------------------
 * Time:  O(n^2). Outer loop n times; inner loop up to n. ~ n(n+1)/2 additions.
 *        n=1000 -> ~500,000; n=10^5 -> ~5x10^9 (too slow).
 * Space: O(1). Two scalar accumulators regardless of input size.
 *
 * ------------------------------------------------------------
 * Approach 2 - Divide & Conquer
 * ------------------------------------------------------------
 * Time:  O(n log n). T(n) = 2T(n/2) + O(n) -> O(n log n). n=10^5 -> ~1.7x10^6.
 * Space: O(log n). No aux arrays, but recursion stack is log n deep (~17 frames).
 *
 * ------------------------------------------------------------
 * Approach 3 - Kadane's Algorithm
 * ------------------------------------------------------------
 * Time:  O(n). One pass, constant work per element. n=10^5 -> ~10^5 ops.
 * Space: O(1). Two integer variables only.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * Using nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4] (expected answer: 6).
 *
 * ------------------------------------------------------------
 * Approach 1 - Brute Force (winning start = 3)
 * ------------------------------------------------------------
 *    start=3, currentSum trace as end advances:
 *      end=3: +4  -> 4      (maxSum -> 4)
 *      end=4: -1  -> 3
 *      end=5: +2  -> 5      (maxSum -> 5)
 *      end=6: +1  -> 6      (maxSum -> 6)   <-- best overall
 *      end=7: -5  -> 1
 *      end=8: +4  -> 5
 *    No other start beats 6, so brute force returns 6.
 *
 * ------------------------------------------------------------
 * Approach 2 - Divide & Conquer (top-level combine)
 * ------------------------------------------------------------
 *    Full array split at mid=4 (value -1):
 *    |- leftBest  = maxSubArray([-2, 1, -3, 4, -1])   = 4  (the "4")
 *    |- rightBest = maxSubArray([2, 1, -5, 4])        = 4  (the "4")
 *    |- crossBest = best-left-of-mid + best-right-of-mid
 *          left expansion from index 4 down to 0:  -1, 3, 0, 1, -1 -> max prefix = 3 (indices 3..4)
 *          right expansion from index 5 up to 8:    2, 3, -2, 2    -> max suffix = 3 (indices 5..6)
 *          crossBest = 3 + 3 = 6
 *    Result = max(4, 4, 6) = 6
 *    The maximum subarray [4, -1, 2, 1] straddles the midpoint, so it is found
 *    by the crossing case. Final answer 6.
 *
 * ------------------------------------------------------------
 * Approach 3 - Kadane's Algorithm (full state table)
 * ------------------------------------------------------------
 * | i | nums[i] | currentSum = max(nums[i], prev+nums[i]) | maxSum |
 * |---|---------|------------------------------------------|--------|
 * | 0 | -2      | -2 (init)                                | -2     |
 * | 1 |  1      | max(1, -2+1=-1) = 1                       | 1      |
 * | 2 | -3      | max(-3, 1-3=-2) = -2                      | 1      |
 * | 3 |  4      | max(4, -2+4=2) = 4                        | 4      |
 * | 4 | -1      | max(-1, 4-1=3) = 3                        | 4      |
 * | 5 |  2      | max(2, 3+2=5) = 5                         | 5      |
 * | 6 |  1      | max(1, 5+1=6) = 6                         | 6      |
 * | 7 | -5      | max(-5, 6-5=1) = 1                        | 6      |
 * | 8 |  4      | max(4, 1+4=5) = 5                         | 6      |
 * Final maxSum = 6.  V
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                | Input                | Expected | How Handled                                          |
 * |--------------------------|----------------------|----------|------------------------------------------------------|
 * | Single element           | [5]                  | 5        | Loop never runs; both vars init to nums[0] = 5.      |
 * | Single negative element  | [-3]                 | -3       | Init to nums[0]; returns -3 (not 0).                 |
 * | All negative             | [-8, -3, -6, -2]     | -2       | Restarts each step; maxSum tracks least-negative.    |
 * | All positive             | [1, 2, 3, 4]         | 10       | currentSum always extends; sum grows to total.       |
 * | Mixed with zeros         | [-1, 0, -2]          | 0        | Single-element [0] is the max; currentSum -> 0.      |
 * | Large uniform            | [10^4] * 10^5        | 10^9     | Fits in int (max ~2.1x10^9); no overflow.            |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * - Initializing maxSum to 0 breaks all-negative arrays:
 *      // WRONG - returns 0 for [-3, -1, -2], but answer is -1
 *      int maxSum = 0;
 *      // CORRECT - anchor to the first element
 *      int maxSum = nums[0];
 * - Restarting to 0 instead of to nums[i]:
 *      // WRONG - treats the array as if an empty subarray were allowed
 *      currentSum = Math.max(0, currentSum + nums[i]);
 *      // CORRECT - the subarray must be non-empty
 *      currentSum = Math.max(nums[i], currentSum + nums[i]);
 * - Off-by-one in the crossing scan (divide & conquer): left expansion starts at
 *   mid, right at mid + 1, so the crossing subarray always includes both halves.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The all-negative array is most dangerous - any variant that resets sums to
 *    0 or initializes the answer to 0 silently returns 0. Single-element and
 *    all-zero arrays are next. Kadane's (init to nums[0], restart to nums[i])
 *    handles all of them.
 *
 * Q: Are there any type mismatches?
 * A: For the constraints (|nums[i]| <= 10^4, n <= 10^5), the max sum is
 *    10^4 * 10^5 = 10^9, which fits in a 32-bit int (max ~2.147x10^9). For larger
 *    constraints, use long. Return type is int throughout.
 *
 * Q: How can I verify this works right now?
 *
 *    public class MaximumSubarrayVerify {
 *
 *        public static int maxSubArray(int[] nums) {
 *            int currentSum = nums[0], maxSum = nums[0];
 *            for (int i = 1; i < nums.length; i++) {
 *                currentSum = Math.max(nums[i], currentSum + nums[i]);
 *                maxSum = Math.max(maxSum, currentSum);
 *            }
 *            return maxSum;
 *        }
 *
 *        public static void verify() {
 *            assert maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}) == 6 : "mixed";
 *            assert maxSubArray(new int[]{5}) == 5 : "single positive";
 *            assert maxSubArray(new int[]{-3}) == -3 : "single negative";
 *            assert maxSubArray(new int[]{-8,-3,-6,-2}) == -2 : "all negative";
 *            assert maxSubArray(new int[]{1,2,3,4}) == 10 : "all positive";
 *            assert maxSubArray(new int[]{-1,0,-2}) == 0 : "with zero";
 *            System.out.println("All assertions passed.");
 *        }
 *
 *        public static void main(String[] args) {
 *            verify(); // run with:  java -ea MaximumSubarrayVerify
 *        }
 *    }
 *
 * | Approach          | Risk                                       | Mitigation                                          |
 * |-------------------|--------------------------------------------|-----------------------------------------------------|
 * | Brute Force       | Too slow for n=10^5 (TLE)                   | Use only for tiny inputs / as a correctness oracle. |
 * | Divide & Conquer  | Off-by-one in crossing scan; stack depth   | Unit-test crossing helper; depth O(log n) is safe.  |
 * | Kadane's          | Wrong init on all-negative arrays          | Init both to nums[0]; restart to nums[i] (never 0). |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #53 - Difficulty: Medium - one of the most-asked array/DP warm-ups.
 *
 * | Company        | Frequency (stars) | Notes                                                       |
 * |----------------|-------------------|-------------------------------------------------------------|
 * | Amazon         | *****             | Extremely common phone-screen opener; may ask for indices.  |
 * | Microsoft      | *****             | Often paired with a divide & conquer follow-up.             |
 * | Google         | ****              | Often extended to 2D (Maximum Sum Rectangle).               |
 * | Bloomberg      | ****              | Popular for testing clean Kadane's implementation.          |
 * | Apple          | ****              | Common quick screen before harder DP.                       |
 * | Meta           | ***               | Appears, often with a request to return the subarray.       |
 * | Adobe          | ***               | Standard DP-intro question.                                 |
 * | LinkedIn       | ***               | Used to gauge edge-case awareness (all-negative).           |
 * | Uber           | ***               | Warm-up before harder algo/system-design rounds.            |
 * | Goldman Sachs  | **                | Appears in quant/SWE screens.                               |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach          | Time       | Space    | Code Complexity | Recommended?                          |
 * |-------------------|------------|----------|-----------------|---------------------------------------|
 * | Brute Force       | O(n^2)     | O(1)     | Very simple     | X Too slow for large n; teaching only |
 * | Divide & Conquer  | O(n log n) | O(log n) | Moderate        | V Good to know for interviews/variants|
 * | Kadane's          | O(n)       | O(1)     | Simple          | VV Best - optimal on time and space   |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use Kadane's algorithm. It is optimal on BOTH axes simultaneously - a single
 * O(n) pass with O(1) memory - so there is no trade-off to weigh.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * Maximum Subarray is the canonical "extend or restart" DP pattern: at each
 * index keep the best sum ending here as max(nums[i], prev + nums[i]), and track
 * the global best alongside it. The one gotcha that separates a correct solution
 * from a subtly broken one is INITIALIZATION - anchor both running variables to
 * nums[0] and restart to nums[i] (never to 0), so all-negative arrays return the
 * least-negative element instead of a phantom 0.
 */
// @formatter:on
