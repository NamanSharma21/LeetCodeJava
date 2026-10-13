package Array;

public class MaximumSumCircularSubarray {
    public static void main(String[] args) {
        MaximumSumCircularSubarray maximumSumCircularSubarray = new MaximumSumCircularSubarray();
        System.out.println("MaximumSumCircularSubarray : "
                + maximumSumCircularSubarray.maxSubarraySumCircularBruteForce(new int[] { 1, -2, 3, -2 }));
        System.out.println("MaximumSumCircularSubarray : "
                + maximumSumCircularSubarray.maxSubarraySumCircularBruteForce(new int[] { 5, -3, 5 }));
        System.out.println("MaximumSumCircularSubarray : "
                + maximumSumCircularSubarray.maxSubarraySumCircularBruteForce(new int[] { -3, -2, -3 }));
        System.out.println("-----------------------");
        System.out.println("MaximumSumCircularSubarray : "
                + maximumSumCircularSubarray.maxSubarraySumCircularPrefixSuffix(new int[] { 1, -2, 3, -2 }));
        System.out.println("MaximumSumCircularSubarray : "
                + maximumSumCircularSubarray.maxSubarraySumCircularPrefixSuffix(new int[] { 5, -3, 5 }));
        System.out.println("MaximumSumCircularSubarray : "
                + maximumSumCircularSubarray.maxSubarraySumCircularPrefixSuffix(new int[] { -3, -2, -3 }));
        System.out.println("-----------------------");
        System.out.println("MaximumSumCircularSubarray : "
                + maximumSumCircularSubarray.maxSubarraySumCircularMaxMinKadane(new int[] { 1, -2, 3, -2 }));
        System.out.println("MaximumSumCircularSubarray : "
                + maximumSumCircularSubarray.maxSubarraySumCircularMaxMinKadane(new int[] { 5, -3, 5 }));
        System.out.println("MaximumSumCircularSubarray : "
                + maximumSumCircularSubarray.maxSubarraySumCircularMaxMinKadane(new int[] { -3, -2, -3 }));
    }

    // @formatter:off
    /*
     * 
     * https://leetcode.com/problems/maximum-sum-circular-subarray/description/
     * 
     * Given a circular integer array nums of length n, return the maximum possible
     * sum of a non-empty subarray of nums.
     * 
     * A circular array means the end of the array connects to the beginning of the
     * array. Formally, the next element of nums[i] is nums[(i + 1) % n] and the
     * previous element of nums[i] is nums[(i - 1 + n) % n].
     * 
     * A subarray may only include each element of the fixed buffer nums at most
     * once. Formally, for a subarray nums[i], nums[i + 1], ..., nums[j], there does
     * not exist i <= k1, k2 <= j with k1 % n == k2 % n.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,-2,3,-2]
     * Output: 3
     * Explanation: Subarray [3] has maximum sum 3.
     * Example 2:
     * 
     * Input: nums = [5,-3,5]
     * Output: 10
     * Explanation: Subarray [5,5] has maximum sum 5 + 5 = 10.
     * Example 3:
     * 
     * Input: nums = [-3,-2,-3]
     * Output: -2
     * Explanation: Subarray [-2] has maximum sum -2.
     * 
     * 
     * Constraints:
     * 
     * n == nums.length
     * 1 <= n <= 3 * 104
     * -3 * 104 <= nums[i] <= 3 * 104
     */
    // @formatter:on

   // @formatter:off
   /**
    * 
    * | Approach        | Time   | Space | Code Complexity | Recommended?                       |
    * |-----------------|--------|-------|-----------------|------------------------------------|
    * | Brute Force     | O(n^2) | O(1)  | Low             | NO - oracle / small inputs only    |
    * 
    * @param nums
    * @return
    */
   // @formatter:on
    public int maxSubarraySumCircularBruteForce(int[] nums) {
        int n = nums.length;
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int currentSum = 0;
            for (int j = 1; j <= n; j++) {
                int index = (i + j - 1) % n;
                currentSum += nums[index];
                maxSum = Math.max(maxSum, currentSum);
            }
        }
        return maxSum;
    }

   // @formatter:off
   /**
    * 
    * | Approach        | Time   | Space | Code Complexity | Recommended?                       |
    * |-----------------|--------|-------|-----------------|------------------------------------|
    * | Prefix + Suffix | O(n)   | O(n)  | Medium          | YES - great for understanding wrap |
    * 
    * @param nums
    * @return
    */
   // @formatter:on
    public int maxSubarraySumCircularPrefixSuffix(int[] nums) {
        int n = nums.length;
        int current = nums[0], nonWrapBest = nums[0];
        for (int i = 1; i < n; i++) {
            current = Math.max(nums[i], current + nums[i]);
            nonWrapBest = Math.max(nonWrapBest, current);
        }

        int[] rightBest = new int[n + 1];
        rightBest[n] = Integer.MIN_VALUE;
        int suffixSum = 0;
        for (int i = n - 1; i >= 0; i--) {
            suffixSum += nums[i];
            rightBest[i] = Math.max(rightBest[i + 1], suffixSum);
        }
        int prefix = 0, wrapBest = Integer.MIN_VALUE;
        for (int i = 0; i < n - 1; i++) {
            prefix += nums[i];
            if (rightBest[i + 1] != Integer.MIN_VALUE) {
                wrapBest = Math.max(wrapBest, prefix + rightBest[i + 1]);
            }
        }
        return Math.max(nonWrapBest, wrapBest);
    }

   // @formatter:off
   /**
    * 
    * | Approach        | Time   | Space | Code Complexity | Recommended?                       |
    * |-----------------|--------|-------|-----------------|------------------------------------|
    * | Max/Min Kadane  | O(n)   | O(1)  | Low-Medium      | BEST - time AND space optimal      |
    * 
    * @param nums
    * @return
    */
   // @formatter:on
    public int maxSubarraySumCircularMaxMinKadane(int[] nums) {
        int total = 0;
        int maxSum = nums[0], currMax = 0;
        int minSum = nums[0], currMin = 0;
        for (int value : nums) {
            currMax = Math.max(currMax + value, value);
            maxSum = Math.max(maxSum, currMax);

            currMin = Math.min(currMin + value, value);
            minSum = Math.min(minSum, currMin);
            total += value;
        }
        return maxSum > 0 ? Math.max(maxSum, total - minSum) : maxSum;
    }

}

// @formatter:off
/*
 * ============================================================
 * MAXIMUM SUM CIRCULAR SUBARRAY - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You're given a *circular* integer array. "Circular" means the last element is
 * considered adjacent to the first - the array wraps around into a ring. Your job
 * is to find the contiguous, non-empty subarray with the largest possible sum,
 * where the subarray is allowed to wrap around the boundary AT MOST ONCE, and no
 * single element may be counted twice.
 *
 * This is LeetCode 918 - Maximum Sum Circular Subarray (Difficulty: Medium).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums - an array of length n where 1 <= n <= 3 * 10^4 and each nums[i]
 * is in [-3*10^4, 3*10^4].
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * A single int: the maximum subarray sum achievable on the circular array.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * - The subarray must be NON-EMPTY (critical for the all-negative case).
 * - Each element is used AT MOST ONCE - a wrapping subarray plus its wrap cannot
 *   overlap.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * The maximum over two shapes of subarray: (a) a normal, non-wrapping window
 * somewhere in the middle, and (b) a window that starts near the end, wraps past
 * index n-1, and continues from index 0.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 * nums = [5, -3, 5] -> answer is 10, taken by wrapping: last 5 + first 5
 * (indices 2 and 0), skipping the -3.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * There are only two ways the best answer can look:
 *   1. It doesn't wrap. Then this is just the classic "Maximum Subarray"
 *      (Kadane's algorithm) - find the best contiguous run.
 *   2. It wraps. A wrapping subarray takes a chunk from the front and a chunk
 *      from the back. The elements it LEAVES OUT form one contiguous block in the
 *      middle. So maximizing a wrapping subarray is the same as MINIMIZING the
 *      middle block you throw away. That gives: totalSum - (minimum subarray sum).
 * The final answer is max(caseA, caseB).
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 *   1. First solve the easy half: run Kadane's to get the best non-wrapping
 *      window. Call it maxSum.
 *   2. A wrapping window is "everything except a middle strip." To make the wrap
 *      as large as possible, the strip removed should be as negative as possible.
 *   3. Run an inverted Kadane's to find the MINIMUM subarray sum, minSum, and
 *      compute total - minSum.
 *   4. Answer = max(maxSum, total - minSum).
 *   5. Catch: if every number is negative, total - minSum equals 0 (I removed the
 *      entire array), but the empty subarray is illegal. In that case fall back to
 *      maxSum.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge            | Why it's tricky                                        |
 * |----------------------|--------------------------------------------------------|
 * | The circular wrap    | Best window may straddle the n-1 -> 0 seam.            |
 * | Two cases in one     | Must run both a max-Kadane and a min-Kadane.           |
 * | All-negative trap    | total - minSum silently yields 0, but empty is illegal.|
 * | "Complement" insight | A wrap is the complement of a NON-wrap - the key trick. |
 * | Overlap avoidance    | Front + back must not overlap; complement ensures this. |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                 | Key Idea                                             | Best Used When            | Time    | Space          |
 * |---|--------------------------|------------------------------------------------------|---------------------------|---------|----------------|
 * | 1 | Brute Force              | Fix a start, extend up to n via modulo, track max    | Tiny n / correctness oracle| O(n^2) | O(1) (space-opt)|
 * | 2 | Prefix + Suffix Arrays   | Kadane for non-wrap; prefix-sum + best suffix for wrap| Want explicit wrap logic  | O(n)    | O(n)           |
 * | 3 | Max-Kadane + Min-Kadane  | Non-wrap = max Kadane; wrap = total - minKadane; guard| General - canonical optimal| O(n) (time-opt)| O(1) (space-opt)|
 *
 * Time and space are independent axes, but this problem has a clean winner:
 * Approach 3 DOMINATES on both - O(n) time AND O(1) space, matching brute force's
 * space while crushing its time. Approach 2 is a genuinely distinct technique
 * (explicit prefix/suffix rather than the complement trick) worth understanding,
 * but it pays O(n) auxiliary space for the same time class - a teaching
 * stepping-stone, not the production choice. Prefer Approach 3 unless you
 * specifically need the explicit prefix/suffix construction for clarity.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (all subarrays)
 * ------------------------------------------------------------
 * Algorithm:
 *   1. For each starting index start from 0 to n-1:
 *   2. Maintain a running sum, adding elements one at a time.
 *   3. Extend window length from 1 up to n, using nums[(start+len-1) % n] to wrap.
 *   4. Track the best sum seen anywhere.
 *   5. Return the best.
 *
 *    public class CircularSubarrayBrute {
 *        public int maxSubarraySumCircular(int[] nums) {
 *            int n = nums.length;
 *            int best = Integer.MIN_VALUE;
 *            for (int start = 0; start < n; start++) {
 *                int runningSum = 0;
 *                for (int len = 1; len <= n; len++) {
 *                    int index = (start + len - 1) % n; // wrap with modulo
 *                    runningSum += nums[index];
 *                    best = Math.max(best, runningSum);
 *                }
 *            }
 *            return best;
 *        }
 *
 *        public static void main(String[] args) {
 *            CircularSubarrayBrute solver = new CircularSubarrayBrute();
 *            System.out.println(solver.maxSubarraySumCircular(new int[]{5, -3, 5}));   // 10
 *            System.out.println(solver.maxSubarraySumCircular(new int[]{-3, -2, -3})); // -2
 *        }
 *    }
 *
 * The len <= n bound is what enforces "each element at most once" - we never
 * extend a window beyond n total elements.
 *
 * ------------------------------------------------------------
 * Approach 2: Prefix + Suffix Arrays
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Run standard Kadane's over nums to get nonWrapBest.
 *   2. Build rightBest[i] = the maximum sum of any run STARTING exactly at index i
 *      and going rightward to the end. Compute from the right via running suffix sum.
 *   3. Sweep a left prefix sum prefix = nums[0..i]. For each i, a wrapping candidate
 *      is prefix + rightBest[i+1] - a front chunk [0..i] joined with the best back
 *      chunk that starts at i+1 or later.
 *   4. Track the best wrapping candidate; return max(nonWrapBest, wrapBest).
 *
 *    public class CircularSubarrayPrefixSuffix {
 *        public int maxSubarraySumCircular(int[] nums) {
 *            int n = nums.length;
 *
 *            // Step 1: best non-wrapping window via Kadane
 *            int current = nums[0], nonWrapBest = nums[0];
 *            for (int i = 1; i < n; i++) {
 *                current = Math.max(nums[i], current + nums[i]);
 *                nonWrapBest = Math.max(nonWrapBest, current);
 *            }
 *
 *            // Step 2: rightBest[i] = max sum of a run that STARTS at index i
 *            int[] rightBest = new int[n + 1];
 *            rightBest[n] = Integer.MIN_VALUE; // no run starts past the end
 *            int suffixSum = 0;
 *            for (int i = n - 1; i >= 0; i--) {
 *                suffixSum += nums[i];
 *                rightBest[i] = Math.max(rightBest[i + 1], suffixSum);
 *            }
 *
 *            // Step 3: join a front prefix [0..i] with best back run starting at i+1
 *            int prefix = 0, wrapBest = Integer.MIN_VALUE;
 *            for (int i = 0; i < n - 1; i++) {
 *                prefix += nums[i];
 *                if (rightBest[i + 1] != Integer.MIN_VALUE) {
 *                    wrapBest = Math.max(wrapBest, prefix + rightBest[i + 1]);
 *                }
 *            }
 *
 *            return Math.max(nonWrapBest, wrapBest);
 *        }
 *
 *        public static void main(String[] args) {
 *            CircularSubarrayPrefixSuffix solver = new CircularSubarrayPrefixSuffix();
 *            System.out.println(solver.maxSubarraySumCircular(new int[]{5, -3, 5}));    // 10
 *            System.out.println(solver.maxSubarraySumCircular(new int[]{3, -1, 2, -1}));// 4
 *        }
 *    }
 *
 * The suffixSum in step 2 is a running total from i to the end, so rightBest[i]
 * captures "grab everything from i up to some point on the right." Because the
 * front prefix ends at i and the back run starts at i+1 or later, they can never
 * overlap. This approach naturally never produces the empty subarray, so it needs
 * no all-negative special case.
 *
 * ------------------------------------------------------------
 * Approach 3: Max-Kadane + Min-Kadane (total - min)  [OPTIMAL]
 * ------------------------------------------------------------
 * Algorithm:
 *   1. In one pass track five quantities: total (sum of all), maxSum/curMax
 *      (running Kadane for the maximum), minSum/curMin (inverted Kadane for min).
 *   2. curMax = max(curMax + x, x); update maxSum.
 *   3. curMin = min(curMin + x, x); update minSum.
 *   4. Add x to total.
 *   5. If maxSum > 0, answer = max(maxSum, total - minSum).
 *   6. If maxSum <= 0, every element is non-positive (all-negative) - return maxSum
 *      directly to avoid the illegal empty subarray.
 *
 *    public class CircularSubarrayOptimal {
 *        public int maxSubarraySumCircular(int[] nums) {
 *            int total = 0;
 *            int maxSum = nums[0], curMax = 0;
 *            int minSum = nums[0], curMin = 0;
 *
 *            for (int value : nums) {
 *                curMax = Math.max(curMax + value, value);
 *                maxSum = Math.max(maxSum, curMax);
 *
 *                curMin = Math.min(curMin + value, value);
 *                minSum = Math.min(minSum, curMin);
 *
 *                total += value;
 *            }
 *
 *            // If maxSum > 0, some positive window exists; the wrap case
 *            // (total - minSum) is safe. Otherwise every element is negative
 *            // -> return the best single window.
 *            return maxSum > 0 ? Math.max(maxSum, total - minSum) : maxSum;
 *        }
 *
 *        public static void main(String[] args) {
 *            CircularSubarrayOptimal solver = new CircularSubarrayOptimal();
 *            System.out.println(solver.maxSubarraySumCircular(new int[]{1, -2, 3, -2})); // 3
 *            System.out.println(solver.maxSubarraySumCircular(new int[]{5, -3, 5}));      // 10
 *            System.out.println(solver.maxSubarraySumCircular(new int[]{-3, -2, -3}));    // -2
 *        }
 *    }
 *
 * The key formula total - minSum works because removing the minimum-sum middle
 * strip leaves exactly the front+back complement - the best wrapping subarray.
 * The guard maxSum > 0 is equivalent to "not all elements are negative": if the
 * best any single window can do is <= 0, then total - minSum would be 0 (whole
 * array removed), which is invalid.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Approach 1 - Brute Force
 *   Time: O(n^2). Outer loop n times; inner loop up to n times -> n*n additions.
 *         For n = 30,000 that's ~9 x 10^8 operations - borderline TLE.
 *   Space: O(1). Only scalar accumulators.
 *   Example: n = 1000 -> ~10^6 inner iterations. n = 30000 -> ~9 x 10^8 - too slow.
 *
 * Approach 2 - Prefix + Suffix Arrays
 *   Time: O(n). Three single passes (Kadane, suffix build, prefix sweep) = 3n.
 *   Space: O(n). The rightBest array of size n+1.
 *   Example: n = 30000 -> ~90,000 operations plus a 30,001-int array (~120 KB).
 *
 * Approach 3 - Max-Kadane + Min-Kadane
 *   Time: O(n). One pass updating five scalars per element -> ~5n steps.
 *   Space: O(1). Five integer variables, no arrays.
 *   Example: n = 30000 -> ~150,000 scalar ops, no allocation. n = 3 -> ~15 ops.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 - Brute Force on [5, -3, 5]
 * ------------------------------------------------------------
 * | start | len | index (start+len-1)%3 | element | runningSum | best |
 * |-------|-----|-----------------------|---------|------------|------|
 * | 0     | 1   | 0                     | 5       | 5          | 5    |
 * | 0     | 2   | 1                     | -3      | 2          | 5    |
 * | 0     | 3   | 2                     | 5       | 7          | 7    |
 * | 1     | 1   | 1                     | -3      | -3         | 7    |
 * | 1     | 2   | 2                     | 5       | 2          | 7    |
 * | 1     | 3   | 0                     | 5       | 7          | 7    |
 * | 2     | 1   | 2                     | 5       | 5          | 7    |
 * | 2     | 2   | 0                     | 5       | 10         | 10   |
 * | 2     | 3   | 1                     | -3      | 7          | 10   |
 * Final output: 10 (the wrap start=2: elements 5 then 5).
 *
 * ------------------------------------------------------------
 * Approach 2 - Prefix + Suffix on [3, -1, 2, -1]
 * ------------------------------------------------------------
 * Step 1 - Kadane (non-wrap):
 *   i=0: current=3,  nonWrapBest=3
 *   i=1: current=2,  nonWrapBest=3
 *   i=2: current=4,  nonWrapBest=4
 *   i=3: current=3,  nonWrapBest=4
 *   nonWrapBest = 4 (the run 3,-1,2).
 *
 * Step 2 - build rightBest (max run starting at i):
 *   rightBest[4] = -inf,  suffixSum=0
 *   i=3: suffixSum=-1, rightBest[3]=max(-inf,-1)=-1
 *   i=2: suffixSum= 1, rightBest[2]=max(-1, 1)= 1
 *   i=1: suffixSum= 0, rightBest[1]=max( 1, 0)= 1
 *   i=0: suffixSum= 3, rightBest[0]=max( 1, 3)= 3
 *
 * Step 3 - sweep prefix, join with best later start:
 *   i=0: prefix=3, candidate=3 + rightBest[1]=3+1=4, wrapBest=4
 *   i=1: prefix=2, candidate=2 + rightBest[2]=2+1=3, wrapBest=4
 *   i=2: prefix=4, candidate=4 + rightBest[3]=4+(-1)=3, wrapBest=4
 *   wrapBest = 4. Final = max(4, 4) = 4.
 *
 * ------------------------------------------------------------
 * Approach 3 - Optimal on [1, -2, 3, -2]
 * ------------------------------------------------------------
 * | value | curMax=max(curMax+v,v) | maxSum | curMin=min(curMin+v,v) | minSum | total |
 * |-------|------------------------|--------|------------------------|--------|-------|
 * | init  | 0                      | 1      | 0                      | 1      | 0     |
 * | 1     | max(1,1)=1             | 1      | min(1,1)=1             | 1      | 1     |
 * | -2    | max(-1,-2)=-1          | 1      | min(-1,-2)=-2          | -2     | -1    |
 * | 3     | max(2,3)=3             | 3      | min(1,3)=1             | -2     | 2     |
 * | -2    | max(1,-2)=1            | 3      | min(-1,-2)=-2          | -2     | 0     |
 * maxSum = 3 > 0, so answer = max(maxSum, total - minSum)
 *        = max(3, 0 - (-2)) = max(3, 2) = 3.
 * (The non-wrapping window [3] wins; the best wrap is not competitive.)
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case               | Input          | Expected | How Handled                                  |
 * |-------------------------|----------------|----------|----------------------------------------------|
 * | Single element (pos)    | [5]            | 5        | Loop runs once; maxSum=5, returns 5.         |
 * | Single element (neg)    | [-5]           | -5       | maxSum=-5 <= 0 -> guard returns maxSum.      |
 * | All negative            | [-3, -2, -3]   | -2       | maxSum=-2 <= 0 -> returns maxSum, skips 0.   |
 * | Wrap is optimal         | [5, -3, 5]     | 10       | total-minSum = 7-(-3) = 10 beats maxSum=5.   |
 * | No wrap needed          | [8, -1, 3, 4]  | 14       | maxSum=14 beats total-minSum.                |
 * | All positive            | [1, 2, 3]      | 6        | Whole array; maxSum=6, total-minSum=5 loses. |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Forgetting the all-negative guard:
 *    // WRONG - returns 0 for [-3,-2,-3] because it removes the whole array
 *    return Math.max(maxSum, total - minSum);
 *
 *    // CORRECT - fall back to maxSum when no positive window exists
 *    return maxSum > 0 ? Math.max(maxSum, total - minSum) : maxSum;
 *
 * Initializing the answer at 0 instead of a real element:
 *    // WRONG - maxSum initialized to 0 hides all-negative answers
 *    int maxSum = 0, curMax = 0;
 *
 *    // CORRECT - seed the answer with a real element
 *    int maxSum = nums[0], curMax = 0;
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The dangerous one is ALL-NEGATIVE input, where total - minSum collapses to 0
 *    (empty subarray). The maxSum > 0 guard covers it. Single-element arrays and
 *    ties between wrap and non-wrap are covered because maxSum is seeded with
 *    nums[0], never 0.
 *
 * Q: Are there any type mismatches?
 * A: No. Everything is int. Max sum magnitude is n * max|value| = 3x10^4 * 3x10^4
 *    = 9x10^8, comfortably under Integer.MAX_VALUE (~2.1x10^9). No long needed for
 *    the given constraints (use long if constraints grow).
 *
 * Q: How can I verify this works right now?
 *    public class Verify {
 *        static int solve(int[] nums) {
 *            int total = 0, maxSum = nums[0], curMax = 0, minSum = nums[0], curMin = 0;
 *            for (int v : nums) {
 *                curMax = Math.max(curMax + v, v); maxSum = Math.max(maxSum, curMax);
 *                curMin = Math.min(curMin + v, v); minSum = Math.min(minSum, curMin);
 *                total += v;
 *            }
 *            return maxSum > 0 ? Math.max(maxSum, total - minSum) : maxSum;
 *        }
 *        public static void main(String[] args) {
 *            assert solve(new int[]{1, -2, 3, -2}) == 3;
 *            assert solve(new int[]{5, -3, 5}) == 10;
 *            assert solve(new int[]{3, -1, 2, -1}) == 4;
 *            assert solve(new int[]{-3, -2, -3}) == -2;
 *            assert solve(new int[]{-5}) == -5;
 *            assert solve(new int[]{8, -1, 3, 4}) == 14;
 *            System.out.println("All assertions passed.");
 *        }
 *    }
 *    // Run with: javac Verify.java && java -ea Verify
 *
 * | Approach        | Risk                                      | Mitigation                                  |
 * |-----------------|-------------------------------------------|---------------------------------------------|
 * | Brute Force     | O(n^2) TLE at n = 3x10^4                   | Use only as an oracle / small n.            |
 * | Prefix + Suffix | Off-by-one on bounds & MIN_VALUE sentinel | Guard rightBest[i+1] != MIN_VALUE; test n=1.|
 * | Max/Min Kadane  | Wrong answer on all-negative input        | maxSum > 0 guard; seed maxSum = nums[0].     |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * LeetCode 918 - Maximum Sum Circular Subarray, Difficulty: Medium. Appears fairly
 * regularly as a twist on the classic Kadane's question (roughly a few hundred
 * reported appearances across major boards).
 *
 * | Company            | Frequency | Notes                                    |
 * |--------------------|-----------|------------------------------------------|
 * | Amazon             | *****     | Very common Kadane-family follow-up.     |
 * | Google             | ****      | Tests the complement insight & edges.    |
 * | Microsoft          | ****      | Popular array/DP round question.         |
 * | Meta               | ***       | Sometimes a phone-screen variant.        |
 * | Bloomberg          | ****      | Frequent in trading/data roles.          |
 * | Apple              | ***       | Appears in general SWE loops.            |
 * | Adobe              | ***       | Standard medium-difficulty rotation.     |
 * | Uber               | **        | Occasional array-round appearance.       |
 * | Goldman Sachs      | **        | Shows up in quant/dev screens.           |
 * | TikTok / ByteDance | ***       | Common Kadane extension.                 |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach        | Time   | Space | Code Complexity | Recommended?                       |
 * |-----------------|--------|-------|-----------------|------------------------------------|
 * | Brute Force     | O(n^2) | O(1)  | Low             | NO - oracle / small inputs only    |
 * | Prefix + Suffix | O(n)   | O(n)  | Medium          | YES - great for understanding wrap |
 * | Max/Min Kadane  | O(n)   | O(1)  | Low-Medium      | BEST - time AND space optimal      |
 *
 * Recommended Approach:
 * Use Max-Kadane + Min-Kadane: compute the best non-wrapping window and
 * total - minSum for the wrap in one pass, guarding the all-negative case. It's
 * optimal on both axes simultaneously, so there's no time/space trade-off to weigh.
 *
 * What to Remember:
 * A wrapping subarray is the COMPLEMENT of a non-wrapping one, so the best wrap =
 * total - (minimum subarray sum). Run Kadane's for both the max and the min in a
 * single sweep, and ALWAYS guard the all-negative case (maxSum > 0) - otherwise
 * total - minSum illegally returns 0 by "removing" the entire array.
 *
 * ============================================================
 * END OF EXPLANATION
 * ============================================================
 */
// @formatter:on
