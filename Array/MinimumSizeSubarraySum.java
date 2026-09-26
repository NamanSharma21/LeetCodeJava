package Array;

public class MinimumSizeSubarraySum {
    public static void main(String[] args) {
        MinimumSizeSubarraySum minimumSizeSubarraySum = new MinimumSizeSubarraySum();
        System.out.println(
                "MinimumSizeSubarraySum : "
                        + minimumSizeSubarraySum.minSubArrayLenBruteForce(7, new int[] { 2, 3, 1, 2, 4, 3 }));
        System.out.println("-------------------------------------");
        System.out.println(
                "MinimumSizeSubarraySum : "
                        + minimumSizeSubarraySum.minSubArrayLenPrefixBinarySearch(7, new int[] { 2, 3, 1, 2, 4, 3 }));
        System.out.println("-------------------------------------");
        System.out.println(
                "MinimumSizeSubarraySum : "
                        + minimumSizeSubarraySum.minSubArrayLenSlidingWindow(7, new int[] { 2, 3, 1, 2, 4, 3 }));
    }

    // @formatter:off
    /**
     * 
     * https://leetcode.com/problems/minimum-size-subarray-sum/
     * 
     * Given an array of positive integers nums and a positive integer target,
     * return the minimal length of a subarray whose sum is greater than or equal to
     * target. If there is no such subarray, return 0 instead.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: target = 7, nums = [2,3,1,2,4,3]
     * Output: 2
     * Explanation: The subarray [4,3] has the minimal length under the problem
     * constraint.
     * Example 2:
     * 
     * Input: target = 4, nums = [1,4,4]
     * Output: 1
     * Example 3:
     * 
     * Input: target = 11, nums = [1,1,1,1,1,1,1,1]
     * Output: 0
     * 
     * 
     * Constraints:
     * 
     * 1 <= target <= 109
     * 1 <= nums.length <= 105
     * 1 <= nums[i] <= 104
     * 
     * 
     * Follow up: If you have figured out the O(n) solution, try coding another
     * solution of which the time complexity is O(n log(n)).
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach              | Time       | Space | Code Complexity | Recommended?                                          |
     * |-----------------------|------------|-------|-----------------|-------------------------------------------------------|
     * | Brute Force           | O(n^2)     | O(1)  | Low             | NO - too slow for large inputs; baseline/oracle only  |
     * 
     * @param target
     * @param nums
     * @return
     */
    // @formatter:on
    public int minSubArrayLenBruteForce(int target, int[] nums) {
        int n = nums.length;
        int minLength = Integer.MAX_VALUE;

        for (int start = 0; start < n; start++) {
            int windowSum = 0;
            for (int end = start; end < n; end++) {
                windowSum += nums[end];
                if (windowSum >= target) {
                    minLength = Math.min(minLength, end - start + 1);
                    break;
                }
            }
        }
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }

    // @formatter:off
    /**
     * 
     * | Approach              | Time       | Space | Code Complexity | Recommended?                                          |
     * |-----------------------|------------|-------|-----------------|-------------------------------------------------------|
     * | Prefix+Binary Search  | O(n log n) | O(n)  | Medium          | YES - good for the follow-up / negative-value variant |
     * 
     * @param target
     * @param nums
     * @return
     */
    // @formatter:on
    public int minSubArrayLenPrefixBinarySearch(int target, int[] nums) {
        int n = nums.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        int minLength = Integer.MAX_VALUE;
        for (int i = 0; i <= n; i++) {
            long needed = prefix[i] + target;
            int j = lowerBound(prefix, needed);
            if (j != -1) {
                minLength = Math.min(minLength, j - i);
            }
        }
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }

    public int lowerBound(long[] prefix, long key) {
        int lo = 0, hi = prefix.length - 1, result = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (prefix[mid] >= key) {
                result = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return result;
    }

    // @formatter:off
    /**
     * 
     * | Approach              | Time       | Space | Code Complexity | Recommended?                                          |
     * |-----------------------|------------|-------|-----------------|-------------------------------------------------------|
     * | Sliding Window        | O(n)       | O(1)  | Low             | BEST - wins on both time and space; the go-to solution|
     * 
     * @param target
     * @param nums
     * @return
     */
    // @formatter:on
    public int minSubArrayLenSlidingWindow(int target, int[] nums) {
        int n = nums.length;
        int left = 0;
        int windowSum = 0;
        int minLength = Integer.MAX_VALUE;

        for (int right = 0; right < n; right++) {
            windowSum += nums[right];
            while (windowSum >= target) {
                minLength = Math.min(minLength, right - left + 1);
                windowSum -= nums[left];
                left++;
            }
        }
        return minLength = minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
}

// @formatter:off
/*
 * ============================================================
 * MINIMUM SIZE SUBARRAY SUM - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * LeetCode #209 - Difficulty: Medium
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an array of POSITIVE integers and a target value, find the length of
 * the SHORTEST contiguous subarray whose elements sum to AT LEAST the target.
 * If no such subarray exists, return 0.
 *
 * The key word is *contiguous* - you cannot skip elements or reorder them.
 * You are looking for an unbroken window of the array.
 *
 * This is LeetCode #209 (Medium).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * - int target : a positive integer, the sum we must meet or exceed.
 * - int[] nums : an array of positive integers.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * - int : the minimal length of a qualifying subarray, or 0 if none exists.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * - 1 <= target <= 10^9
 * - 1 <= nums.length <= 10^5
 * - 1 <= nums[i] <= 10^4
 * - All values are strictly positive (this fact unlocks the efficient approaches).
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Among all subarrays nums[i..j] (contiguous) whose sum >= target, we want the
 * MINIMUM value of (j - i + 1). We do not return the subarray itself, only its length.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    target = 7
 *    nums   = [2, 3, 1, 2, 4, 3]
 *
 * The subarray [4, 3] sums to 7 >= 7 and has length 2. No shorter subarray
 * reaches 7, so the answer is 2.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Imagine a stretchy window sliding across the array. You grow the window on
 * the right to accumulate more sum. The moment the sum inside is "big enough"
 * (>= target), you try to shrink from the left to see how tight you can make
 * the window while still qualifying. Because all numbers are positive, adding
 * an element always increases the sum and removing one always decreases it, so
 * the window's sum moves predictably.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Start with an empty window at the front of the array.
 * 2. Keep adding elements on the right, tracking the running sum.
 * 3. As soon as the running sum reaches target, record the current window length.
 * 4. Now greedily drop elements from the left as long as the sum stays >= target,
 *    recording ever-smaller lengths.
 * 5. When the sum drops below target, resume expanding on the right.
 * 6. The smallest length ever recorded is the answer.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                        | Why it's tricky                                                       |
 * |----------------------------------|-----------------------------------------------------------------------|
 * | Choosing when to shrink vs grow  | You must shrink aggressively every time you qualify, not just once.   |
 * | "At least" vs "exactly"          | The condition is >= target, not == target, so overshooting is allowed.|
 * | Handling "no answer"             | If even the whole-array sum is below target, you must return 0.      |
 * | Relying on positivity            | Sliding window is only correct because values are positive.          |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                     | Key Idea                                            | Best Used When                          | Time       | Space     |
 * |---|------------------------------|-----------------------------------------------------|-----------------------------------------|------------|-----------|
 * | 1 | Brute Force (nested loops)   | Try every start, extend end until sum >= target     | Tiny inputs, teaching baseline          | O(n^2)     | O(1)      |
 * | 2 | Prefix Sum + Binary Search   | Build prefix sums; binary-search earliest end       | Need O(n log n) / threshold pattern     | O(n log n) | O(n)      |
 * | 3 | Sliding Window (Two Ptr) OK  | Expand right, shrink left while still >= target      | The general optimal case                | O(n) TIME  | O(1) SPACE|
 *
 * The brute force is O(n^2) and quickly becomes infeasible near n = 10^5
 * (~10 billion operations). Prefix Sum + Binary Search cuts time to O(n log n)
 * but pays O(n) auxiliary space for the prefix array. The Sliding Window
 * DOMINATES on both axes - it is simultaneously the fastest (O(n)) and the
 * leanest (O(1)), a rare case where one approach is unambiguously best. Prefer
 * the sliding window in essentially all situations; the binary-search variant
 * is mainly valuable as a stepping stone to problems where the array contains
 * negatives (where sliding windows break and prefix techniques still apply).
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Algorithm steps:
 * 1. For each starting index start, initialize a running sum to 0.
 * 2. Extend end from start forward, adding nums[end] to the sum each step.
 * 3. The instant the sum reaches target, record window length end - start + 1
 *    and break (extending further only lengthens the window).
 * 4. Track the minimum length across all starts.
 * 5. If nothing ever qualified, return 0.
 *
 *    public class MinSubarrayBruteForce {
 *        public int minSubArrayLen(int target, int[] nums) {
 *            int n = nums.length;
 *            int minLength = Integer.MAX_VALUE;
 *
 *            for (int start = 0; start < n; start++) {
 *                int windowSum = 0;
 *                for (int end = start; end < n; end++) {
 *                    windowSum += nums[end];
 *                    if (windowSum >= target) {
 *                        minLength = Math.min(minLength, end - start + 1);
 *                        break; // extending further only lengthens the window
 *                    }
 *                }
 *            }
 *
 *            return minLength == Integer.MAX_VALUE ? 0 : minLength;
 *        }
 *
 *        public static void main(String[] args) {
 *            MinSubarrayBruteForce solver = new MinSubarrayBruteForce();
 *            System.out.println(solver.minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3})); // 2
 *            System.out.println(solver.minSubArrayLen(4, new int[]{1, 4, 4}));          // 1
 *            System.out.println(solver.minSubArrayLen(11, new int[]{1, 1, 1, 1, 1}));   // 0
 *        }
 *    }
 *
 * The inner break is an important optimization: once a window starting at start
 * qualifies, any longer window from the same start is worse, so we stop immediately.
 *
 * ------------------------------------------------------------
 * Approach 2: Prefix Sum + Binary Search
 * ------------------------------------------------------------
 * Algorithm steps:
 * 1. Build a prefix-sum array where prefix[i] = sum of the first i elements
 *    (prefix[0] = 0). Because all nums[i] > 0, this array is strictly increasing.
 * 2. For each start index i, we need the smallest j > i with
 *    prefix[j] - prefix[i] >= target, i.e. prefix[j] >= prefix[i] + target.
 * 3. Since prefix is sorted, binary-search for that threshold prefix[i] + target.
 * 4. If found at index j, the subarray length is j - i. Track the minimum.
 * 5. Return 0 if nothing qualified.
 *
 *    public class MinSubarrayPrefixBinarySearch {
 *        public int minSubArrayLen(int target, int[] nums) {
 *            int n = nums.length;
 *            long[] prefix = new long[n + 1];          // long avoids overflow
 *            for (int i = 0; i < n; i++) {
 *                prefix[i + 1] = prefix[i] + nums[i];
 *            }
 *
 *            int minLength = Integer.MAX_VALUE;
 *            for (int i = 0; i <= n; i++) {
 *                long needed = prefix[i] + target;     // find earliest prefix >= needed
 *                int j = lowerBound(prefix, needed);
 *                if (j != -1) {
 *                    minLength = Math.min(minLength, j - i);
 *                }
 *            }
 *
 *            return minLength == Integer.MAX_VALUE ? 0 : minLength;
 *        }
 *
 *        // Smallest index whose prefix value >= key, or -1 if none
 *        private int lowerBound(long[] prefix, long key) {
 *            int lo = 0, hi = prefix.length - 1, result = -1;
 *            while (lo <= hi) {
 *                int mid = lo + (hi - lo) / 2;
 *                if (prefix[mid] >= key) {
 *                    result = mid;
 *                    hi = mid - 1;
 *                } else {
 *                    lo = mid + 1;
 *                }
 *            }
 *            return result;
 *        }
 *
 *        public static void main(String[] args) {
 *            MinSubarrayPrefixBinarySearch solver = new MinSubarrayPrefixBinarySearch();
 *            System.out.println(solver.minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3})); // 2
 *            System.out.println(solver.minSubArrayLen(4, new int[]{1, 4, 4}));          // 1
 *            System.out.println(solver.minSubArrayLen(11, new int[]{1, 1, 1, 1, 1}));   // 0
 *        }
 *    }
 *
 * Using long for the prefix array guards against overflow: with n = 10^5 and
 * each value up to 10^4, the total can reach 10^9, which fits in int, but adding
 * target (up to 10^9) on top can reach ~2x10^9, exceeding Integer.MAX_VALUE
 * (~2.147x10^9). long sidesteps that risk entirely.
 *
 * ------------------------------------------------------------
 * Approach 3: Sliding Window (Two Pointers) - OPTIMAL
 * ------------------------------------------------------------
 * Algorithm steps:
 * 1. Keep a left pointer and a running windowSum, starting at 0.
 * 2. Move right across the array, adding nums[right] to windowSum.
 * 3. While windowSum >= target: record length right - left + 1, then subtract
 *    nums[left] and advance left (shrink the window).
 * 4. Continue until right reaches the end.
 * 5. Return the minimum length found, or 0.
 *
 *    public class MinSubarraySlidingWindow {
 *        public int minSubArrayLen(int target, int[] nums) {
 *            int n = nums.length;
 *            int left = 0;
 *            int windowSum = 0;
 *            int minLength = Integer.MAX_VALUE;
 *
 *            for (int right = 0; right < n; right++) {
 *                windowSum += nums[right];
 *
 *                // Shrink from the left while the window still qualifies
 *                while (windowSum >= target) {
 *                    minLength = Math.min(minLength, right - left + 1);
 *                    windowSum -= nums[left];
 *                    left++;
 *                }
 *            }
 *
 *            return minLength == Integer.MAX_VALUE ? 0 : minLength;
 *        }
 *
 *        public static void main(String[] args) {
 *            MinSubarraySlidingWindow solver = new MinSubarraySlidingWindow();
 *            System.out.println(solver.minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3})); // 2
 *            System.out.println(solver.minSubArrayLen(4, new int[]{1, 4, 4}));          // 1
 *            System.out.println(solver.minSubArrayLen(11, new int[]{1, 1, 1, 1, 1}));   // 0
 *        }
 *    }
 *
 * The subtle part is that shrinking is a while, not an if: once the window
 * qualifies, you keep dropping left-side elements as long as it STILL qualifies,
 * capturing every tighter window before the sum finally dips below target.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * - Time: O(n^2). Outer loop runs n times; inner loop up to n times per start.
 *   Worst case ~ n^2/2 additions. For n = 10^5, roughly 5x10^9 operations - too slow.
 * - Space: O(1). Only a few scalar variables.
 *
 * ------------------------------------------------------------
 * Approach 2: Prefix Sum + Binary Search
 * ------------------------------------------------------------
 * - Time: O(n log n). Building prefix is O(n). Then n+1 binary searches at
 *   O(log n) each gives O(n log n). For n = 10^5, ~ 10^5 x 17 ~ 1.7x10^6 ops.
 * - Space: O(n). The prefix-sum array holds n+1 longs.
 *
 * ------------------------------------------------------------
 * Approach 3: Sliding Window
 * ------------------------------------------------------------
 * - Time: O(n). Each element is added once (by right) and removed at most once
 *   (by left). Total pointer movement bounded by 2n. For n = 10^5, ~ 2x10^5 ops.
 * - Space: O(1). Only pointers and running scalars; no auxiliary array.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * Using target = 7, nums = [2, 3, 1, 2, 4, 3] for all three.
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * | start | extends end until sum >= 7 | window     | length |
 * |-------|----------------------------|------------|--------|
 * | 0     | 2->5->6->8 (ends idx 3)    | [2,3,1,2]  | 4      |
 * | 1     | 3->4->6->10 (ends idx 4)   | [3,1,2,4]  | 4      |
 * | 2     | 1->3->7 (ends idx 4)       | [1,2,4]    | 3      |
 * | 3     | 2->6->9 (ends idx 5)       | [2,4,3]    | 3      |
 * | 4     | 4->7 (ends idx 5)          | [4,3]      | 2      |
 * | 5     | 3 (never reaches 7)        | -          | -      |
 * Minimum = 2.
 *
 * ------------------------------------------------------------
 * Approach 2: Prefix Sum + Binary Search
 * ------------------------------------------------------------
 * Prefix array: [0, 2, 5, 6, 8, 12, 15]
 *
 * | i | needed = prefix[i] + 7 | earliest prefix >= needed | j | length = j - i |
 * |---|------------------------|---------------------------|---|----------------|
 * | 0 | 7                      | 8 (index 4)               | 4 | 4              |
 * | 1 | 9                      | 12 (index 5)              | 5 | 4              |
 * | 2 | 12                     | 12 (index 5)              | 5 | 3              |
 * | 3 | 13                     | 15 (index 6)              | 6 | 3              |
 * | 4 | 15                     | 15 (index 6)              | 6 | 2              |
 * | 5 | 19                     | none                      | - | -              |
 * | 6 | 22                     | none                      | - | -              |
 * Minimum = 2.
 *
 * ------------------------------------------------------------
 * Approach 3: Sliding Window
 * ------------------------------------------------------------
 * right=0: sum=2               (2<7, no shrink)
 * right=1: sum=5               (5<7)
 * right=2: sum=6               (6<7)
 * right=3: sum=8  >=7 -> len=4, drop nums[0]=2 -> sum=6, left=1
 * right=4: sum=6+4=10 >=7 -> len=min(4,4)=4, drop nums[1]=3 -> sum=7,
 *                         >=7 -> len=min(4,3)=3, drop nums[2]=1 -> sum=6, left=3
 * right=5: sum=6+3=9  >=7 -> len=min(3,3)=3, drop nums[3]=2 -> sum=7,
 *                         >=7 -> len=min(3,2)=2, drop nums[4]=4 -> sum=3, left=5
 * End -> minLength = 2
 * Minimum = 2.
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                        | Input                       | Expected | How Handled                                         |
 * |----------------------------------|-----------------------------|----------|-----------------------------------------------------|
 * | Single element already >= target | target=4, nums=[1,4,4]      | 1        | Window of size 1 qualifies; minLength becomes 1.    |
 * | No subarray reaches target       | target=11, nums=[1,1,1,1,1] | 0        | minLength stays MAX_VALUE; sentinel converts to 0.  |
 * | Whole array needed               | target=15, nums=[1,2,3,4,5] | 5        | Target met only at last element; full window used.  |
 * | First element alone suffices     | target=3, nums=[5,1,1]      | 1        | Window shrinks to length 1 on the first step.       |
 * | All elements equal               | target=6, nums=[2,2,2,2]    | 3        | [2,2,2]=6 qualifies; shorter windows sum to 4 < 6.  |
 * | Single-element array, qualifies  | target=1, nums=[100]        | 1        | The lone element >= target.                         |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 - using if instead of while when shrinking:
 *
 *    // WRONG: shrinks at most once, misses tighter windows
 *    if (windowSum >= target) {
 *        minLength = Math.min(minLength, right - left + 1);
 *        windowSum -= nums[left];
 *        left++;
 *    }
 *
 *    // CORRECT: keep shrinking while it still qualifies
 *    while (windowSum >= target) {
 *        minLength = Math.min(minLength, right - left + 1);
 *        windowSum -= nums[left];
 *        left++;
 *    }
 *
 * Pitfall 2 - forgetting the "no answer" sentinel:
 *
 *    // WRONG: returns a huge garbage number when nothing qualifies
 *    return minLength;
 *
 *    // CORRECT: map the untouched sentinel to 0
 *    return minLength == Integer.MAX_VALUE ? 0 : minLength;
 *
 * Pitfall 3 - integer overflow in the prefix/binary-search variant:
 *
 *    // WRONG: prefix[i] + target may exceed Integer.MAX_VALUE
 *    int needed = prefix[i] + target;
 *
 *    // CORRECT: use long for prefix sums and the threshold
 *    long needed = prefix[i] + target;
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The classic misses are (1) returning Integer.MAX_VALUE instead of 0 when no
 *    window qualifies, and (2) an off-by-one in the length formula - it must be
 *    right - left + 1, not right - left. The sliding window handles both correctly.
 *    The prefix variant must also ensure lowerBound returns -1 (not a valid index)
 *    when no prefix meets the threshold.
 *
 * Q: Are there any type mismatches?
 * A: In the sliding-window and brute-force versions, all sums fit in int
 *    (max total ~ 10^9 < 2.147x10^9). The prefix + binary search version uses long
 *    because prefix[i] + target can reach ~2x10^9, which would overflow int.
 *    Lengths are always int.
 *
 * Q: How can I verify this works right now?
 *
 *    public static void verify() {
 *        MinSubarraySlidingWindow s = new MinSubarraySlidingWindow();
 *        assert s.minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3}) == 2;
 *        assert s.minSubArrayLen(4, new int[]{1, 4, 4}) == 1;
 *        assert s.minSubArrayLen(11, new int[]{1, 1, 1, 1, 1}) == 0;
 *        assert s.minSubArrayLen(15, new int[]{1, 2, 3, 4, 5}) == 5;
 *        assert s.minSubArrayLen(6, new int[]{2, 2, 2, 2}) == 3;
 *        assert s.minSubArrayLen(1, new int[]{100}) == 1;
 *        System.out.println("All assertions passed.");
 *    }
 *    // Run with: java -ea MinSubarraySlidingWindow   (the -ea flag enables asserts)
 *
 * | Approach              | Risk                                          | Mitigation                                          |
 * |-----------------------|-----------------------------------------------|-----------------------------------------------------|
 * | Brute Force           | Too slow for large n (O(n^2))                 | Use only for small inputs or as a correctness oracle|
 * | Prefix+Binary Search  | Overflow in threshold; off-by-one in bound    | Use long; test lowerBound against known thresholds  |
 * | Sliding Window        | if-instead-of-while shrink; missing 0 sentinel| Use while; map MAX_VALUE -> 0 on return             |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * LeetCode #209 - Difficulty: Medium - Estimated appearances: very high
 * (a canonical sliding-window interview problem).
 *
 * | Company          | Frequency (stars) | Notes                                             |
 * |------------------|-------------------|---------------------------------------------------|
 * | Amazon           | *****             | A staple sliding-window screen; many variants.    |
 * | Google           | ****              | Tests positivity insight + O(n log n) follow-up.  |
 * | Microsoft        | ****              | Common phone-screen two-pointer question.         |
 * | Meta (Facebook)  | ****              | Warm-up before harder window problems.            |
 * | Bloomberg        | ***               | Appears in array/window rounds.                   |
 * | Apple            | ***               | Occasionally, testing edge-case handling.         |
 * | Goldman Sachs    | ***               | Shows up in coding assessments.                   |
 * | Adobe            | **                | Less frequent, but in the rotation.               |
 * | Uber             | ***               | Popular for its clean linear-time solution.       |
 * | Oracle           | **                | Appears in OA rounds.                              |
 *
 * The follow-up "can you also solve it in O(n log n)?" is what makes the prefix +
 * binary search approach worth knowing even though the sliding window is better.
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach              | Time       | Space | Code Complexity | Recommended?                                          |
 * |-----------------------|------------|-------|-----------------|-------------------------------------------------------|
 * | Brute Force           | O(n^2)     | O(1)  | Low             | NO - too slow for large inputs; baseline/oracle only  |
 * | Prefix+Binary Search  | O(n log n) | O(n)  | Medium          | YES - good for the follow-up / negative-value variant |
 * | Sliding Window        | O(n)       | O(1)  | Low             | BEST - wins on both time and space; the go-to solution|
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use the Sliding Window. It is the unusual case where one approach wins on BOTH
 * axes - O(n) time and O(1) space - so there is no time-vs-space trade-off to
 * weigh. Keep the binary-search variant in your back pocket only for the interview
 * follow-up or when values can be negative (where the window technique fails).
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * The moment you see "shortest/longest contiguous subarray with a sum/count
 * condition" over POSITIVE numbers, reach for a two-pointer sliding window:
 * expand right to satisfy the condition, then shrink left greedily to minimize.
 * The two gotchas to burn into memory: shrink with a WHILE, not an if, and map
 * the untouched Integer.MAX_VALUE sentinel back to 0 when no window qualifies.
 *
 * ============================================================
 * END OF EXPLANATION
 * ============================================================
 */
// @formatter:on
