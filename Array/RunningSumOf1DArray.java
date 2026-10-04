package Array;

import java.util.Arrays;

public class RunningSumOf1DArray {
    public static void main(String[] args) {
        RunningSumOf1DArray runningSumOf1DArray = new RunningSumOf1DArray();
        System.out.println(
                "RunningSumOf1DArray : "
                        + Arrays.toString(runningSumOf1DArray.runningSumBruteForce(new int[] { 1, 2, 3, 4 })));
        System.out.println("--------------------------------------");
        System.out.println(
                "RunningSumOf1DArray : "
                        + Arrays.toString(runningSumOf1DArray.runningSumPrefixSumInPlace(new int[] { 1, 2, 3, 4 })));
        System.out.println("--------------------------------------");
        System.out.println(
                "RunningSumOf1DArray : "
                        + Arrays.toString(
                                runningSumOf1DArray.runningSumPrefixSumSeparateArray(new int[] { 1, 2, 3, 4 })));
    }

    // @formatter:off
    /**
     * 
     * https://leetcode.com/problems/running-sum-of-1d-array/description/
     * 
     * 
     * Given an array nums. We define a running sum of an array as runningSum[i] =
     * sum(nums[0]…nums[i]).
     * 
     * Return the running sum of nums.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,2,3,4]
     * Output: [1,3,6,10]
     * Explanation: Running sum is obtained as follows: [1, 1+2, 1+2+3, 1+2+3+4].
     * Example 2:
     * 
     * Input: nums = [1,1,1,1,1]
     * Output: [1,2,3,4,5]
     * Explanation: Running sum is obtained as follows: [1, 1+1, 1+1+1, 1+1+1+1,
     * 1+1+1+1+1].
     * Example 3:
     * 
     * Input: nums = [3,1,2,10,1]
     * Output: [3,4,6,16,17]
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 1000
     * -10^6 <= nums[i] <= 10^6
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach                    | Time   | Space | Code Complexity | Recommended?                               |
     * |-----------------------------|--------|-------|-----------------|--------------------------------------------|
     * | Brute Force                 | O(n^2) | O(n)  | Low             | [X] Not recommended — needlessly quadratic |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int[] runningSumBruteForce(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            int runningSum = 0;
            for (int j = 0; j <= i; j++) {
                runningSum += nums[j];
            }
            result[i] = runningSum;
        }
        return result;
    }

    // @formatter:off
    /**
     * 
     * | Approach                    | Time   | Space | Code Complexity | Recommended?                               |
     * |-----------------------------|--------|-------|-----------------|--------------------------------------------|
     * | Prefix Sum (separate array) | O(n)   | O(n)  | Low             | [+] best when input must be preserved      |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int[] runningSumPrefixSumSeparateArray(int[] nums) {
        int n = nums.length;
        int runningSum = 0;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            runningSum += nums[i];
            result[i] = runningSum;
        }
        return result;
    }

    // @formatter:off
    /**
     * 
     * | Approach                    | Time   | Space | Code Complexity | Recommended?                               |
     * |-----------------------------|--------|-------|-----------------|--------------------------------------------|
     * | Prefix Sum (in-place)       | O(n)   | O(1)  | Low             | [++] best for time and low memory          |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int[] runningSumPrefixSumInPlace(int[] nums) {
        int n = nums.length;
        for (int i = 1; i < n; i++) {
            nums[i] += nums[i - 1];
        }
        return nums;
    }

}

// @formatter:off
/*
 * ============================================================
 * RUNNING SUM OF 1D ARRAY — DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an array of integers, produce a new array where each position holds the
 * cumulative total of all elements up to and including that position. This
 * cumulative total is called a running sum (also known as a prefix sum).
 *
 * Formally, if the input is nums, then the output runningSum is defined by
 * runningSum[i] = nums[0] + nums[1] + ... + nums[i].
 *
 * This is LeetCode #1480, difficulty Easy.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * A single array int[] nums of length n.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * An array int[] of the same length n, where each element is the prefix sum
 * up to that index.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * 1 <= nums.length <= 1000
 * -10^6 <= nums[i] <= 10^6
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * For every index i from 0 to n-1, compute the sum of the sub-array nums[0..i]
 * (inclusive) and place it at runningSum[i].
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  nums = [1, 2, 3, 4]
 *    Output: [1, 3, 6, 10]
 *
 *    Because:
 *      index 0 -> 1
 *      index 1 -> 1 + 2         = 3
 *      index 2 -> 1 + 2 + 3     = 6
 *      index 3 -> 1 + 2 + 3 + 4 = 10
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Imagine walking along the array carrying a bucket. At each element you pour
 * that element's value into the bucket, then read off how full the bucket is.
 * The bucket never empties — it only accumulates — so each reading is the total
 * of everything you've poured so far. That "current bucket level" at each step
 * is exactly the running sum.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Start with a total of 0 (empty bucket).
 * 2. Move to index 0, add nums[0] to the total, write it at runningSum[0].
 * 3. Move to index 1, add nums[1] to the SAME total, write it at runningSum[1].
 * 4. Keep going. You never recompute earlier elements — the running total
 *    already contains them.
 * 5. Key realization: runningSum[i] = runningSum[i-1] + nums[i]. Each answer is
 *    just the previous answer plus one new number.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                 | Why it's tricky                                                        |
 * |---------------------------|------------------------------------------------------------------------|
 * | Avoiding recomputation    | Re-summing nums[0..i] each index turns an O(n) job into O(n^2).         |
 * | The recurrence            | Seeing runningSum[i] = runningSum[i-1] + nums[i] is the key leap.       |
 * | In-place modification     | You can overwrite input for O(1) space since each nums[i] is read once. |
 * | Base case                 | Index 0 has no predecessor: runningSum[0] = nums[0].                    |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                    | Key Idea                                  | Best Used When                          | Time   | Space               |
 * |---|-----------------------------|-------------------------------------------|-----------------------------------------|--------|---------------------|
 * | 1 | Brute Force (nested loops)  | For each i, re-sum nums[0..i] from scratch| Never in practice — naive baseline only | O(n^2) | O(n) (or O(1))      |
 * | 2 | Prefix Sum, separate output | Carry a running total; write to new array | You must preserve the original input    | O(n) [+] time-optimal | O(n)      |
 * | 3 | Prefix Sum, in-place        | Same recurrence, overwrite nums itself    | Memory tight and mutating input allowed | O(n) [+] time-optimal | O(1) [+] space-optimal |
 *
 * Brute force is dominated on time by both prefix-sum variants and offers no
 * compensating advantage, so it is never the right production choice. Approaches
 * 2 and 3 share the optimal O(n) time; they differ only in space. Approach 3
 * reuses the input array and reaches O(1) auxiliary space, so prefer Approach 3
 * when you are allowed to mutate the input and memory matters; prefer Approach 2
 * when the caller still needs the original array intact (the extra O(n) array is
 * the price of preserving the input).
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (nested loops)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Create an output array result of length n.
 * 2. For each index i from 0 to n-1:
 *    - Initialize sum = 0.
 *    - Loop j from 0 to i, adding nums[j] to sum.
 *    - Store sum in result[i].
 * 3. Return result.
 *
 *    public class RunningSumBruteForce {
 *        public static int[] runningSum(int[] nums) {
 *            int n = nums.length;
 *            int[] result = new int[n];
 *            for (int i = 0; i < n; i++) {
 *                int sum = 0;
 *                for (int j = 0; j <= i; j++) {  // re-add everything up to i
 *                    sum += nums[j];
 *                }
 *                result[i] = sum;
 *            }
 *            return result;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {1, 2, 3, 4};
 *            int[] out = runningSum(nums);
 *            System.out.println(java.util.Arrays.toString(out)); // [1, 3, 6, 10]
 *        }
 *    }
 *
 * The inner loop repeats work: index i re-adds all elements 0..i, so nums[0]
 * gets added n times total. This repetition is what makes it quadratic.
 *
 * ------------------------------------------------------------
 * Approach 2: Prefix Sum with a separate output array [+] (time-optimal)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Create an output array result of length n.
 * 2. Maintain a variable running initialized to 0.
 * 3. For each index i from 0 to n-1:
 *    - Add nums[i] to running.
 *    - Store running in result[i].
 * 4. Return result.
 *
 *    public class RunningSumPrefix {
 *        public static int[] runningSum(int[] nums) {
 *            int n = nums.length;
 *            int[] result = new int[n];
 *            int running = 0;
 *            for (int i = 0; i < n; i++) {
 *                running += nums[i];   // each answer = previous total + current value
 *                result[i] = running;
 *            }
 *            return result;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {3, 1, 2, 10, 1};
 *            int[] out = runningSum(nums);
 *            System.out.println(java.util.Arrays.toString(out)); // [3, 4, 6, 16, 17]
 *        }
 *    }
 *
 * Each element is visited once and contributes to the running total once, which
 * is why this is linear. The original nums array is untouched.
 *
 * ------------------------------------------------------------
 * Approach 3: Prefix Sum in-place [+] (space-optimal)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Loop i from 1 to n-1.
 * 2. Add the previous cumulative value nums[i-1] into nums[i].
 * 3. After the loop, nums itself holds the running sums; return nums.
 *
 *    public class RunningSumInPlace {
 *        public static int[] runningSum(int[] nums) {
 *            for (int i = 1; i < nums.length; i++) {
 *                nums[i] += nums[i - 1];  // nums[i-1] already holds its prefix sum
 *            }
 *            return nums;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {1, 1, 1, 1, 1};
 *            int[] out = runningSum(nums);
 *            System.out.println(java.util.Arrays.toString(out)); // [1, 2, 3, 4, 5]
 *        }
 *    }
 *
 * Correctness hinges on order: by the time we process index i, index i-1 has
 * already been converted into its prefix sum, so nums[i] += nums[i-1] propagates
 * the accumulation forward. Index 0 is left alone because it is already its own
 * prefix sum. No extra array is allocated, giving O(1) auxiliary space.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 — Brute Force
 * ------------------------------------------------------------
 * Time: O(n^2). Outer loop runs n times; inner loop runs i+1 times for outer
 * index i. Total = 1 + 2 + ... + n = n(n+1)/2 = O(n^2). For n = 1000, roughly
 * 500,000 additions.
 * Space: O(n) for result (or O(1) if overwriting in place, but time stays
 * quadratic).
 *
 * ------------------------------------------------------------
 * Approach 2 — Prefix Sum, separate array
 * ------------------------------------------------------------
 * Time: O(n). One pass, one addition + one assignment per element. For n = 1000,
 * about 1,000 additions — 500x fewer than brute force.
 * Space: O(n). Stores a new result array of size n plus one running scalar.
 *
 * ------------------------------------------------------------
 * Approach 3 — Prefix Sum, in-place
 * ------------------------------------------------------------
 * Time: O(n). Single pass from index 1 to n-1, one addition each — about n-1
 * additions.
 * Space: O(1). No new array; only the loop counter is extra. Output reuses input
 * memory.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 — Brute Force, input [1, 2, 3, 4]
 * ------------------------------------------------------------
 * | i | inner loop j: values added                | sum | result so far |
 * |---|-------------------------------------------|-----|---------------|
 * | 0 | nums[0]=1                                  | 1   | [1]           |
 * | 1 | nums[0]=1, nums[1]=2                       | 3   | [1, 3]        |
 * | 2 | nums[0]=1, nums[1]=2, nums[2]=3            | 6   | [1, 3, 6]     |
 * | 3 | nums[0]=1, nums[1]=2, nums[2]=3, nums[3]=4 | 10  | [1, 3, 6, 10] |
 * Final output: [1, 3, 6, 10]
 *
 * ------------------------------------------------------------
 * Approach 2 — Prefix Sum separate array, input [3, 1, 2, 10, 1]
 * ------------------------------------------------------------
 * running = 0
 * ├─ i=0: running += 3  -> running = 3   result = [3]
 * ├─ i=1: running += 1  -> running = 4   result = [3, 4]
 * ├─ i=2: running += 2  -> running = 6   result = [3, 4, 6]
 * ├─ i=3: running += 10 -> running = 16  result = [3, 4, 6, 16]
 * └─ i=4: running += 1  -> running = 17  result = [3, 4, 6, 16, 17]
 * Final output: [3, 4, 6, 16, 17]
 *
 * ------------------------------------------------------------
 * Approach 3 — Prefix Sum in-place, input [1, 1, 1, 1, 1]
 * ------------------------------------------------------------
 * start: [1, 1, 1, 1, 1]   (index 0 untouched)
 * ├─ i=1: nums[1] += nums[0]=1 -> [1, 2, 1, 1, 1]
 * ├─ i=2: nums[2] += nums[1]=2 -> [1, 2, 3, 1, 1]
 * ├─ i=3: nums[3] += nums[2]=3 -> [1, 2, 3, 4, 1]
 * └─ i=4: nums[4] += nums[3]=4 -> [1, 2, 3, 4, 5]
 * Final output: [1, 2, 3, 4, 5]
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                 | Input                         | Expected Output | How Handled                                                        |
 * |---------------------------|-------------------------------|-----------------|--------------------------------------------------------------------|
 * | Single element            | [5]                           | [5]             | In-place loop starts at i=1 and never runs; separate version writes index 0. |
 * | All negative values       | [-1, -2, -3]                  | [-1, -3, -6]    | Addition handles negatives naturally.                              |
 * | Mixed positive/negative   | [3, -3, 5]                    | [3, 0, 8]       | Running total can dip and rise; recurrence still holds.            |
 * | All zeros                 | [0, 0, 0]                     | [0, 0, 0]       | Adding zero leaves the total unchanged.                            |
 * | Maximum-magnitude values  | [10^6, 10^6, ...] x1000       | up to 10^9      | Fits in 32-bit int (max ~2.1x10^9); no overflow within constraints.|
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Re-summing from scratch each index (quadratic):
 *    // WRONG — recomputes the whole prefix every time -> O(n^2)
 *    for (int i = 0; i < n; i++) {
 *        int s = 0;
 *        for (int j = 0; j <= i; j++) s += nums[j];
 *        result[i] = s;
 *    }
 *    // CORRECT — carry the running total -> O(n)
 *    int running = 0;
 *    for (int i = 0; i < n; i++) { running += nums[i]; result[i] = running; }
 *
 * Starting the in-place loop at i = 0:
 *    // WRONG — nums[0-1] is index -1 -> ArrayIndexOutOfBoundsException
 *    for (int i = 0; i < n; i++) nums[i] += nums[i - 1];
 *    // CORRECT — start at 1; index 0 is already its own prefix sum
 *    for (int i = 1; i < n; i++) nums[i] += nums[i - 1];
 *
 * Resetting the running total inside the loop: declaring running = 0 inside the
 * loop body wipes the accumulation each iteration. Declare it once, outside.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: A length-1 array is the main boundary — the in-place loop must correctly
 *    NOT execute, and the separate-array version must still write index 0.
 *    Negative and zero values are safe. Overflow is not a concern within the
 *    stated constraints (max total 10^9 fits in int); larger constraints would
 *    require long.
 *
 * Q: Are there any type mismatches?
 * A: None within constraints. Inputs and outputs are both int[]. If the
 *    cumulative sum could exceed ~2.1x10^9, switch return/accumulator to
 *    long[] / long.
 *
 * Q: How can I verify this works right now?
 *
 *    import java.util.Arrays;
 *
 *    public class RunningSumVerify {
 *        public static int[] runningSum(int[] nums) {
 *            for (int i = 1; i < nums.length; i++) nums[i] += nums[i - 1];
 *            return nums;
 *        }
 *
 *        static void verify() {
 *            assert Arrays.equals(runningSum(new int[]{1, 2, 3, 4}), new int[]{1, 3, 6, 10});
 *            assert Arrays.equals(runningSum(new int[]{1, 1, 1, 1, 1}), new int[]{1, 2, 3, 4, 5});
 *            assert Arrays.equals(runningSum(new int[]{3, 1, 2, 10, 1}), new int[]{3, 4, 6, 16, 17});
 *            assert Arrays.equals(runningSum(new int[]{5}), new int[]{5});
 *            assert Arrays.equals(runningSum(new int[]{-1, -2, -3}), new int[]{-1, -3, -6});
 *            System.out.println("All assertions passed.");
 *        }
 *
 *        public static void main(String[] args) {
 *            verify(); // run with:  java -ea RunningSumVerify
 *        }
 *    }
 *
 * | Approach                | Risk                                        | Mitigation                                     |
 * |-------------------------|---------------------------------------------|------------------------------------------------|
 * | Brute Force             | O(n^2) too slow; wasted work                | Replace with the running-total recurrence.     |
 * | Prefix Sum (separate)   | Extra O(n) memory allocated                 | Acceptable when input must be preserved.       |
 * | Prefix Sum (in-place)   | Mutates caller's array; off-by-one at idx 0 | Document the mutation; start loop at i = 1.    |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * | Company          | Frequency (stars) | Notes                                           |
 * |------------------|-------------------|-------------------------------------------------|
 * | Amazon           | * * * *           | Common warm-up / phone-screen filter.           |
 * | Microsoft        | * * *             | Lead-in to harder prefix-sum problems.          |
 * | Google           | * * *             | Tests clean O(n) reasoning and in-place tricks. |
 * | Meta (Facebook)  | * * *             | Foundation for range-sum follow-ups.            |
 * | Apple            | * *               | Occasional easy screener.                       |
 * | Bloomberg        | * * *             | Popular for entry-level array rounds.           |
 * | Adobe            | * *               | Shows up in early screening rounds.             |
 * | Goldman Sachs    | * *               | Common in quantitative/dev screens.             |
 * | Uber             | * *               | Warm-up array question.                         |
 * | TCS / Infosys    | * * *             | Frequent in mass-hiring coding tests.           |
 *
 * LeetCode #1480, difficulty Easy. One of the most-attempted easy array problems
 * and a classic gateway to the broader prefix-sum pattern (range-sum queries,
 * subarray-sum problems, etc.).
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach                    | Time   | Space | Code Complexity | Recommended?                               |
 * |-----------------------------|--------|-------|-----------------|--------------------------------------------|
 * | Brute Force                 | O(n^2) | O(n)  | Low             | [X] Not recommended — needlessly quadratic |
 * | Prefix Sum (separate array) | O(n)   | O(n)  | Low             | [+] best when input must be preserved      |
 * | Prefix Sum (in-place)       | O(n)   | O(1)  | Low             | [++] best for time and low memory          |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use the in-place prefix sum (Approach 3) — optimal O(n) time with O(1) extra
 * space. The only trade-off: it mutates the input array, so if the caller still
 * needs the original data intact, switch to the separate-array prefix sum
 * (Approach 2), which costs an extra O(n) array to preserve the input.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * The pattern is prefix sum: runningSum[i] = runningSum[i-1] + nums[i] — every
 * answer is the previous answer plus one new value, so a single pass suffices.
 * Carry a running total instead of re-summing, and you can even fold the result
 * back into the input array for O(1) space. The one gotcha: start the in-place
 * loop at index 1, since index 0 is already its own prefix sum.
 */
// @formatter:on
