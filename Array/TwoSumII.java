package Array;

import java.util.Arrays;

public class TwoSumII {
    public static void main(String[] args) {
        TwoSumII twoSumII = new TwoSumII();
        System.out.println("TwoSumII : " + Arrays.toString(twoSumII.twoSumTwoPointers(new int[] { 2, 7, 11, 15 }, 9)));
        System.out.println("-------------------");
        System.out.println("TwoSumII : " + Arrays.toString(twoSumII.twoSumBinarySearch(new int[] { 2, 7, 11, 15 }, 9)));
        System.out.println("-------------------");
        System.out.println("TwoSumII : " + Arrays.toString(twoSumII.twoSumBruteForce(new int[] { 2, 7, 11, 15 }, 9)));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/description/
     * 
     * You are given a 1-indexed array of integers numbers that is already sorted in
     * non-decreasing order.
     * 
     * Find two numbers such that they add up to a specific target number. Let these
     * two numbers be numbers[index1] and numbers[index2] where 1 <= index1 < index2
     * <= numbers.length.
     * 
     * Return the indices of the two numbers index1 and index2 as an integer array
     * [index1, index2] of length 2.
     * 
     * The tests are generated such that there is exactly one solution. You may not
     * use the same element twice.
     * 
     * Your solution must use only constant extra space.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: numbers = [2,7,11,15], target = 9
     * Output: [1,2]
     * Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We
     * return [1, 2].
     * Example 2:
     * 
     * Input: numbers = [2,3,4], target = 6
     * Output: [1,3]
     * Explanation: The sum of 2 and 4 is 6. Therefore index1 = 1, index2 = 3. We
     * return [1, 3].
     * Example 3:
     * 
     * Input: numbers = [-1,0], target = -1
     * Output: [1,2]
     * Explanation: The sum of -1 and 0 is -1. Therefore index1 = 1, index2 = 2. We
     * return [1, 2].
     * 
     * 
     * Constraints:
     * 
     * 2 <= numbers.length <= 3 * 104
     * -1000 <= numbers[i] <= 1000
     * numbers is sorted in non-decreasing order.
     * -1000 <= target <= 1000
     * The tests are generated such that there is exactly one solution.
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach      | Time       | Space | Code Complexity | Recommended?                          |
     * |---------------|------------|-------|-----------------|---------------------------------------|
     * | Two Pointers  | O(n)       | O(1)  | Simple          | Best on time & space                  |
     * 
     * @param numbers
     * @param target
     * @return
     */
    // @formatter:on
    public int[] twoSumTwoPointers(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            if (numbers[left] + numbers[right] == target) {
                return new int[] { left + 1, right + 1 };
            } else if (numbers[left] + numbers[right] < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[] { -1, -1 };
    }

    // @formatter:off
    /**
     * 
     * | Approach      | Time       | Space | Code Complexity | Recommended?                          |
     * |---------------|------------|-------|-----------------|---------------------------------------|
     * | Binary Search | O(n log n) | O(1)  | Moderate        | Acceptable if two-ptr missed          |
     * 
     * @param numbers
     * @param target
     * @return
     */
    // @formatter:on
    public int[] twoSumBinarySearch(int[] numbers, int target) {
        int n = numbers.length;
        for (int i = 0; i < n; i++) {
            int complement = target - numbers[i];
            int lo = i + 1, hi = n - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (numbers[mid] == complement) {
                    return new int[] { i + 1, mid + 1 };
                } else if (numbers[mid] < complement) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }

        }
        return new int[] { -1, -1 };
    }

    // @formatter:off
    /**
     * 
     * | Approach      | Time       | Space | Code Complexity | Recommended?                          |
     * |---------------|------------|-------|-----------------|---------------------------------------|
     * | Brute Force   | O(n^2)     | O(1)  | Very simple     | X Too slow; only for tiny n           |
     * 
     * @param numbers
     * @param target
     * @return
     */
    // @formatter:on
    public int[] twoSumBruteForce(int[] numbers, int target) {
        int n = numbers.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (numbers[i] + numbers[j] == target)
                    return new int[] { i + 1, j + 1 };
            }
        }
        return new int[] { -1, -1 };
    }
}
// @formatter:off
/*
 * ============================================================
 * Two Sum II - Input Array Is Sorted -- Deep Dive Explanation
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You're given an array of integers already sorted in non-decreasing order.
 * You must find the two distinct elements that add up to a given target, and
 * return their 1-based positions. The problem guarantees that exactly one
 * valid pair exists, so you never handle "no answer" or "multiple answers".
 *
 * This is LeetCode #167 -- Two Sum II - Input Array Is Sorted (Medium).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * - int[] numbers : a 1-indexed array sorted in non-decreasing order (length n, 2 <= n).
 * - int target    : the sum you must hit.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * - int[] of length 2 : [index1, index2] where index1 < index2, both 1-based.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * - 2 <= numbers.length <= 3 * 10^4
 * - -1000 <= numbers[i] <= 1000
 * - numbers is sorted in non-decreasing order.
 * - -1000 <= target <= 1000
 * - Exactly one valid solution exists; you may not reuse the same element.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Two positions i < j such that numbers[i] + numbers[j] == target, returned
 * as [i+1, j+1] in 1-based form.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    numbers = [2, 7, 11, 15], target = 9
 *    2 + 7 = 9  -> 0-based indices (0, 1) -> 1-based answer [1, 2]
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * The array is sorted -- that's the whole gift. In plain "Two Sum" the array
 * is unsorted, so you reach for a hash map. Here, sortedness lets you squeeze
 * from both ends: start with the smallest and largest values, and let their
 * sum tell you which way to move.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * Picture cards laid out left-to-right smallest to largest; find two summing to target:
 *   1. Point one finger at the leftmost card (smallest), one at the rightmost (largest).
 *   2. Add the two cards your fingers touch.
 *   3. If the sum is too big, move the right finger left (to a smaller number).
 *   4. If the sum is too small, move the left finger right (to a bigger number).
 *   5. If the sum equals target, you're done.
 * Every move strictly shrinks the window and never skips the answer -> one pass.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                          | Why it's tricky                                                    |
 * |------------------------------------|--------------------------------------------------------------------|
 * | 1-based indexing                   | Answer must be 1-based; forgetting the +1 is the most common bug.  |
 * | Why a pointer move never skips ans | Discarding an endpoint needs a correctness argument from sortedness.|
 * | Not reusing the same element       | Indices must differ; the left < right loop condition enforces this.|
 * | Resisting the hash-map reflex      | Sortedness enables O(1) space, unlike unsorted Two Sum I.          |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                  | Key Idea                                              | Best Used When                     | Time       | Space   |
 * |---|---------------------------|-------------------------------------------------------|------------------------------------|------------|---------|
 * | 1 | Brute Force (nested loops)| Test every pair (i, j)                                | Tiny inputs; ignoring sortedness   | O(n^2)     | O(1)    |
 * | 2 | Binary Search             | For each numbers[i], binary-search complement in suffix| Leveraging sortedness, pre two-ptr | O(n log n) | O(1)    |
 * | 3 | Two Pointers              | Converge inward from both ends, guided by the sum     | The array is sorted (this problem) | O(n) checkmark | O(1) checkmark |
 *
 * The three approaches form a clean ladder. Brute force ignores sorted structure
 * and pays O(n^2). Binary search uses sortedness partially (fix one index, search
 * for the partner) -> O(n log n). Two pointers uses sortedness fully: every
 * comparison eliminates an entire endpoint, collapsing to one O(n) sweep. All three
 * are O(1) space, so there's no time-vs-space tension -- two pointers dominates on
 * BOTH axes. A hash-map solution would be O(n) time but O(n) space for no benefit,
 * so it's strictly dominated and not listed.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (Nested Loops)
 * ------------------------------------------------------------
 * Algorithm:
 *   1. For each index i from 0 to n-2:
 *   2.   For each index j from i+1 to n-1:
 *   3.     If numbers[i] + numbers[j] == target, return [i+1, j+1] (1-based).
 *   4. If nothing found (won't happen given the guarantee), return failure.
 *
 *    public class TwoSumIIBruteForce {
 *        public int[] twoSum(int[] numbers, int target) {
 *            int n = numbers.length;
 *            for (int i = 0; i < n - 1; i++) {
 *                for (int j = i + 1; j < n; j++) {
 *                    if (numbers[i] + numbers[j] == target) {
 *                        return new int[]{i + 1, j + 1}; // 1-based indices
 *                    }
 *                }
 *            }
 *            return new int[]{-1, -1}; // unreachable given the guarantee
 *        }
 *
 *        public static void main(String[] args) {
 *            TwoSumIIBruteForce solver = new TwoSumIIBruteForce();
 *            int[] result = solver.twoSum(new int[]{2, 7, 11, 15}, 9);
 *            System.out.println(result[0] + ", " + result[1]); // 1, 2
 *        }
 *    }
 *
 * This ignores sortedness completely -- same answer even on an unsorted array.
 *
 * ------------------------------------------------------------
 * Approach 2: Binary Search
 * ------------------------------------------------------------
 * Algorithm:
 *   1. For each i from 0 to n-1, compute complement = target - numbers[i].
 *   2. Binary-search complement in the suffix numbers[i+1 .. n-1]
 *      (searching only right avoids reusing element i and duplicate pairs).
 *   3. If found at index j, return [i+1, j+1] (1-based).
 *   4. Otherwise continue to the next i.
 *
 *    public class TwoSumIIBinarySearch {
 *        public int[] twoSum(int[] numbers, int target) {
 *            int n = numbers.length;
 *            for (int i = 0; i < n - 1; i++) {
 *                int complement = target - numbers[i];
 *                int lo = i + 1, hi = n - 1;
 *                while (lo <= hi) {
 *                    int mid = lo + (hi - lo) / 2; // avoids integer overflow
 *                    if (numbers[mid] == complement) {
 *                        return new int[]{i + 1, mid + 1};
 *                    } else if (numbers[mid] < complement) {
 *                        lo = mid + 1;
 *                    } else {
 *                        hi = mid - 1;
 *                    }
 *                }
 *            }
 *            return new int[]{-1, -1}; // unreachable given the guarantee
 *        }
 *
 *        public static void main(String[] args) {
 *            TwoSumIIBinarySearch solver = new TwoSumIIBinarySearch();
 *            int[] result = solver.twoSum(new int[]{2, 3, 4}, 6);
 *            System.out.println(result[0] + ", " + result[1]); // 1, 3
 *        }
 *    }
 *
 * mid = lo + (hi - lo) / 2 prevents lo + hi from overflowing int on large arrays.
 *
 * ------------------------------------------------------------
 * Approach 3: Two Pointers  checkmark (Optimal)
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Set left = 0 (start) and right = n - 1 (end).
 *   2. While left < right:
 *        - sum = numbers[left] + numbers[right].
 *        - If sum == target, return [left+1, right+1] (1-based).
 *        - If sum < target, left++  (need a larger value).
 *        - If sum > target, right-- (need a smaller value).
 *   3. If the loop ends (won't happen given the guarantee), return failure.
 *
 *    public class TwoSumIITwoPointers {
 *        public int[] twoSum(int[] numbers, int target) {
 *            int left = 0, right = numbers.length - 1;
 *            while (left < right) {
 *                int sum = numbers[left] + numbers[right];
 *                if (sum == target) {
 *                    return new int[]{left + 1, right + 1}; // 1-based indices
 *                } else if (sum < target) {
 *                    left++;   // sum too small -> grow the smaller end
 *                } else {
 *                    right--;  // sum too big -> shrink the larger end
 *                }
 *            }
 *            return new int[]{-1, -1}; // unreachable given the guarantee
 *        }
 *
 *        public static void main(String[] args) {
 *            TwoSumIITwoPointers solver = new TwoSumIITwoPointers();
 *            System.out.println(java.util.Arrays.toString(
 *                solver.twoSum(new int[]{2, 7, 11, 15}, 9)));  // [1, 2]
 *            System.out.println(java.util.Arrays.toString(
 *                solver.twoSum(new int[]{-1, 0}, -1)));         // [1, 2]
 *        }
 *    }
 *
 * Why moving a pointer never skips the answer (correctness sketch):
 * If numbers[left] + numbers[right] < target, then pairing numbers[left] with
 * anything smaller than numbers[right] is even smaller -- so left can never be
 * part of the answer with any remaining partner, and we discard it by moving
 * left right. The symmetric argument holds when the sum exceeds target. Each
 * step eliminates exactly one index, so the correct pair stays in the window.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Time  -- O(n^2): outer loop n-1 times, inner up to n-1; ~ n(n-1)/2 comparisons.
 *          For n = 30,000, ~4.5 * 10^8 operations.
 * Space -- O(1): only loop counters.
 *
 * ------------------------------------------------------------
 * Approach 2: Binary Search
 * ------------------------------------------------------------
 * Time  -- O(n log n): n outer positions, each a O(log n) binary search.
 *          For n = 30,000, ~30,000 * 15 ~ 4.5 * 10^5 operations.
 * Space -- O(1): iterative binary search uses scalars only.
 *
 * ------------------------------------------------------------
 * Approach 3: Two Pointers  checkmark
 * ------------------------------------------------------------
 * Time  -- O(n): the gap right - left shrinks by 1 each step, starting at n-1,
 *          stopping at 0 -> at most n-1 iterations. For n = 30,000, ~3 * 10^4 ops.
 * Space -- O(1): two index variables and a sum temporary.
 *
 * Numeric estimates at n = 30,000: ~4.5e8 vs ~4.5e5 vs ~3e4 -- a ~4-order-of-
 * magnitude advantage for two pointers, all at constant space.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force -- numbers = [2, 7, 11, 15], target = 9
 * ------------------------------------------------------------
 *   i=0 (numbers[0]=2):
 *   |- j=1: 2 + 7 = 9 == 9 -> MATCH -> return [0+1, 1+1] = [1, 2]
 *   Output: [1, 2]
 *
 * ------------------------------------------------------------
 * Approach 2: Binary Search -- numbers = [2, 3, 4], target = 6
 * ------------------------------------------------------------
 *   i=0 (numbers[0]=2), complement = 4, search suffix [3, 4] (indices 1..2):
 *   |- lo=1, hi=2, mid=1 -> numbers[1]=3 < 4 -> lo = 2
 *   |- lo=2, hi=2, mid=2 -> numbers[2]=4 == 4 -> MATCH -> return [1, 3]
 *   Output: [1, 3]   (2 + 4 = 6)
 *
 * ------------------------------------------------------------
 * Approach 3: Two Pointers -- numbers = [2, 3, 4, 8, 11, 15], target = 14
 * ------------------------------------------------------------
 * | Step | left | right | numbers[left] | numbers[right] | sum | vs target | Action  |
 * |------|------|-------|---------------|----------------|-----|-----------|---------|
 * | 1    | 0    | 5     | 2             | 15             | 17  | > 14      | right-- |
 * | 2    | 0    | 4     | 2             | 11             | 13  | < 14      | left++  |
 * | 3    | 1    | 4     | 3             | 11             | 14  | == 14     | return [2, 5] |
 *   Output: [2, 5] (1-based) -> numbers[1]=3 and numbers[4]=11, 3 + 11 = 14
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                       | Input                          | Expected | How Handled                                       |
 * |---------------------------------|--------------------------------|----------|---------------------------------------------------|
 * | Minimum length array            | [1,2], target=3                | [1, 2]   | left=0, right=1; matches on the single iteration. |
 * | Negative numbers                | [-3,-1,0,4], target=-4         | [1, 2]   | Sum logic identical for negatives; -3 + -1 = -4.  |
 * | Answer at the two extremes      | [1,5], target=6                | [1, 2]   | First pointer pairing is already the answer.      |
 * | Answer in the middle            | [1,2,3,4,5], target=5          | [1, 4]   | Pointers converge: 1+5=6>5 -> 1+4=5 match.        |
 * | Duplicate values                | [3,3], target=6                | [1, 2]   | Distinct indices, equal values; left < right.     |
 * | Large values near bounds        | [1000,1000], target=2000       | [1, 2]   | int sum 2000 is in range; no overflow.            |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 -- Returning 0-based indices.
 *    // WRONG -- LeetCode expects 1-based indices
 *    return new int[]{left, right};
 *    // CORRECT
 *    return new int[]{left + 1, right + 1};
 *
 * Pitfall 2 -- Using left <= right instead of left < right.
 *    // WRONG -- allows left == right, reusing the same element
 *    while (left <= right) { ... }
 *    // CORRECT -- the two indices must be distinct
 *    while (left < right) { ... }
 *
 * Pitfall 3 -- Moving the wrong pointer.
 *    // WRONG -- inverted logic; array never converges toward target
 *    if (sum < target) right--;
 *    else left++;
 *    // CORRECT -- too small => move left up; too big => move right down
 *    if (sum < target) left++;
 *    else right--;
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: Two pointers handles negatives, duplicates, and minimum-length arrays
 *    naturally. The real risks are a logic inversion (wrong pointer) or an
 *    off-by-one in the return (forgetting 1-based). The guarantee removes the
 *    "no answer" case, but keeping return {-1,-1} makes reuse elsewhere safe.
 *
 * Q: Are there any type mismatches?
 * A: None. With -1000 <= numbers[i] <= 1000, max sum magnitude is 2000, well
 *    inside int range -- no overflow, no long needed. Return type int[] matches.
 *
 * Q: How can I verify this works right now?
 *    public static void verify() {
 *        TwoSumIITwoPointers s = new TwoSumIITwoPointers();
 *        assert java.util.Arrays.equals(s.twoSum(new int[]{2,7,11,15}, 9),  new int[]{1,2});
 *        assert java.util.Arrays.equals(s.twoSum(new int[]{2,3,4}, 6),      new int[]{1,3});
 *        assert java.util.Arrays.equals(s.twoSum(new int[]{-1,0}, -1),      new int[]{1,2});
 *        assert java.util.Arrays.equals(s.twoSum(new int[]{3,3}, 6),        new int[]{1,2});
 *        assert java.util.Arrays.equals(s.twoSum(new int[]{1,2,3,4,5}, 5),  new int[]{1,4});
 *        System.out.println("All assertions passed.");
 *    }
 *    // Run with:  java -ea TwoSumIITwoPointers   (the -ea flag enables assertions)
 *
 * | Approach      | Risk                                          | Mitigation                                        |
 * |---------------|-----------------------------------------------|---------------------------------------------------|
 * | Brute Force   | Too slow for large n (O(n^2))                 | Fine only for tiny inputs; prefer two pointers.   |
 * | Binary Search | Off-by-one in lo/hi; searching whole array    | Search suffix i+1..n-1; use mid = lo + (hi-lo)/2. |
 * | Two Pointers  | Pointer-move inversion or <= vs < bug         | Trace one example; keep while (left < right).     |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * LeetCode #167 -- Difficulty: Medium -- Very frequent warm-up / Two Sum I follow-up.
 *
 * | Company        | Frequency (stars) | Notes                                                          |
 * |----------------|-------------------|----------------------------------------------------------------|
 * | Amazon         | *****             | A staple; often paired with unsorted Two Sum.                 |
 * | Google         | ****              | Probes whether you exploit sortedness vs reflexively hashing. |
 * | Microsoft      | ****              | Common phone-screen question.                                 |
 * | Meta           | ****              | Frequently a lead-in to 3Sum, which builds on two pointers.   |
 * | Apple          | ***               | Appears in early-round screens.                               |
 * | Bloomberg      | ****              | Popular for its clean two-pointer discussion.                 |
 * | Adobe          | ***               | Standard array/two-pointer warm-up.                           |
 * | Uber           | ***               | Stepping stone toward harder pointer problems.                |
 * | Oracle         | **                | Occasional appearance in screens.                             |
 * | Goldman Sachs  | **                | Fundamentals check in coding rounds.                          |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach      | Time       | Space | Code Complexity | Recommended?                          |
 * |---------------|------------|-------|-----------------|---------------------------------------|
 * | Brute Force   | O(n^2)     | O(1)  | Very simple     | X Too slow; only for tiny n           |
 * | Binary Search | O(n log n) | O(1)  | Moderate        | Acceptable if two-ptr missed          |
 * | Two Pointers  | O(n)       | O(1)  | Simple          | Best on time & space                  |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Two Pointers. Optimal on BOTH axes -- one O(n) pass in O(1) space -- with no
 * trade-off to weigh, because sortedness lets each comparison discard an endpoint.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * When an array is sorted and you need a pair (or sum condition), reach for two
 * pointers converging from both ends before a hash map -- it turns O(n) space
 * into O(1) for free. The signature gotcha here is the 1-based return: convert
 * with left + 1 and right + 1. Keep the loop condition left < right so you never
 * reuse a single element.
 */
// @formatter:on