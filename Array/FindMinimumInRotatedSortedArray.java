package Array;

public class FindMinimumInRotatedSortedArray {
    public static void main(String[] args) {
        FindMinimumInRotatedSortedArray findMinimumInRotatedSortedArray = new FindMinimumInRotatedSortedArray();
        System.out.println("FindMinimumInRotatedSortedArray : "
                + findMinimumInRotatedSortedArray.findMinLinearScan(new int[] { 3, 4, 5, 1, 2 }));
        System.out.println("FindMinimumInRotatedSortedArray : "
                + findMinimumInRotatedSortedArray.findMinLinearScan(new int[] { 4, 5, 6, 7, 0, 1, 2 }));
        System.out.println("FindMinimumInRotatedSortedArray : "
                + findMinimumInRotatedSortedArray.findMinLinearScan(new int[] { 11, 13, 15, 17 }));
        System.out.println("---------------------------------------");
        System.out.println("FindMinimumInRotatedSortedArray : "
                + findMinimumInRotatedSortedArray.findMinBinarySearchIterative(new int[] { 3, 4, 5, 1, 2 }));
        System.out.println("FindMinimumInRotatedSortedArray : "
                + findMinimumInRotatedSortedArray.findMinBinarySearchIterative(new int[] { 4, 5, 6, 7, 0, 1, 2 }));
        System.out.println("FindMinimumInRotatedSortedArray : "
                + findMinimumInRotatedSortedArray.findMinBinarySearchIterative(new int[] { 11, 13, 15, 17 }));
        System.out.println("---------------------------------------");
        System.out.println("FindMinimumInRotatedSortedArray : "
                + findMinimumInRotatedSortedArray.findMinBinarySearchRecursive(new int[] { 3, 4, 5, 1, 2 }));
        System.out.println("FindMinimumInRotatedSortedArray : "
                + findMinimumInRotatedSortedArray.findMinBinarySearchRecursive(new int[] { 4, 5, 6, 7, 0, 1, 2 }));
        System.out.println("FindMinimumInRotatedSortedArray : "
                + findMinimumInRotatedSortedArray.findMinBinarySearchRecursive(new int[] { 11, 13, 15, 17 }));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/description/
     * 
     * Suppose an array of length n sorted in ascending order is rotated between 1
     * and n times. For example, the array nums = [0,1,2,4,5,6,7] might become:
     * 
     * [4,5,6,7,0,1,2] if it was rotated 4 times.
     * [0,1,2,4,5,6,7] if it was rotated 7 times.
     * Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results
     * in the array [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
     * 
     * Given the sorted rotated array nums of unique elements, return the minimum
     * element of this array.
     * 
     * You must write an algorithm that runs in O(log n) time.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [3,4,5,1,2]
     * Output: 1
     * Explanation: The original array was [1,2,3,4,5] rotated 3 times.
     * Example 2:
     * 
     * Input: nums = [4,5,6,7,0,1,2]
     * Output: 0
     * Explanation: The original array was [0,1,2,4,5,6,7] and it was rotated 4
     * times.
     * Example 3:
     * 
     * Input: nums = [11,13,15,17]
     * Output: 11
     * Explanation: The original array was [11,13,15,17] and it was rotated 4 times.
     * 
     * 
     * Constraints:
     * 
     * n == nums.length
     * 1 <= n <= 5000
     * -5000 <= nums[i] <= 5000
     * All the integers of nums are unique.
     * nums is sorted and rotated between 1 and n times.
     */
    // @formatter:on

    // @formatter:off
    /**
     * | Approach                  | Time     | Space    | Code Complexity | Recommended?                                   |
     * | Linear Scan               | O(n)     | O(1)     | Trivial         | acceptable only when n tiny / O(log n) not req |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int findMinLinearScan(int[] nums) {
        int min = nums[0];
        for (int num : nums) {
            if (num < min)
                min = num;
        }
        return min;
    }

    // @formatter:off
    /**
     * | Approach                  | Time     | Space    | Code Complexity | Recommended?                                   |
     * | Binary Search (iterative) | O(log n) | O(1)     | Low-moderate    | BEST OVERALL (wins on time, ties on space)     |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int findMinBinarySearchIterative(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] > nums[hi])
                lo = mid + 1;
            else
                hi = mid;
        }
        return nums[lo];
    }

    // @formatter:off
    /**
     * | Approach                  | Time     | Space    | Code Complexity | Recommended?                                   |
     * | Binary Search (recursive) | O(log n) | O(log n) | Moderate        | fine, but iterative is strictly better on space|
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int findMinBinarySearchRecursive(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }

    public int helper(int[] nums, int lo, int hi) {
        if (lo == hi)
            return nums[lo];
        int mid = lo + (hi - lo) / 2;
        if (nums[mid] > nums[hi])
            return helper(nums, mid + 1, hi);
        else
            return helper(nums, lo, mid);
    }
}
// @formatter:off
/*
 * ============================================================
 * FIND MINIMUM IN ROTATED SORTED ARRAY — DEEP DIVE EXPLANATION
 * ============================================================
 * LeetCode #153 · Difficulty: Medium
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You're given an array that was originally sorted in ascending
 * order, then rotated at some unknown pivot point between 1 and n
 * times. A rotation moves the last element to the front (equivalently,
 * cut the array at some index and swap the two halves). Find the
 * MINIMUM element efficiently.
 *
 * Example: sorted [0,1,2,4,5,6,7] rotated -> [4,5,6,7,0,1,2].
 * The minimum is still 0, but no longer at index 0.
 *
 * All elements are UNIQUE (the duplicates variant is #154).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * - int[] nums, length n where 1 <= n <= 5000.
 * - nums is a rotation of a strictly ascending sorted array.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * - A single int: the minimum value in the array.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * | Constraint        | Value                                   |
 * | Array length n    | 1 <= n <= 5000                          |
 * | Element values    | -5000 <= nums[i] <= 5000                |
 * | Uniqueness        | All integers are distinct               |
 * | Guarantee         | nums is a rotated ascending-sorted array|
 * | Expected time     | O(log n) — required by the problem      |
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Return the smallest value. The minimum is the ONLY inflection
 * point — the single place where an element is smaller than its
 * predecessor. Finding that point in O(log n) is the challenge.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  [4,5,6,7,0,1,2]  -> Output: 0
 *    Input:  [3,4,5,1,2]      -> Output: 1
 *    Input:  [11,13,15,17]    -> Output: 11  (rotated n times = sorted)
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Picture a sorted strip of paper cut somewhere and the two pieces
 * swapped. The array is now two ascending "staircases": a HIGH
 * staircase followed by a LOW staircase. The minimum sits at the
 * bottom step where the high staircase drops to the low one.
 *
 * At any middle position you can tell which staircase you're on by
 * comparing your value to the RIGHTMOST element. Higher than the
 * right end -> upper staircase, drop-off is to the right. Lower than
 * the right end -> lower staircase, minimum is here or to the left.
 * Each comparison discards half the array — binary search.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Look at the last element nums[hi] (lower staircase / un-rotated).
 * 2. Check the middle element nums[mid].
 * 3. If nums[mid] > nums[hi]: middle is on the higher staircase,
 *    minimum is strictly right -> lo = mid + 1.
 * 4. If nums[mid] < nums[hi]: middle is on the lower staircase (or is
 *    the min), minimum is at mid or left -> hi = mid.
 * 5. Repeat until lo == hi. That surviving index is the minimum.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                     | Why it's tricky                                                            |
 * | Choosing the comparison anchor| Comparing to nums[lo] also works but needs care; nums[hi] is cleaner.      |
 * | The un-rotated case           | Fully sorted array has no visible "drop"; must still return nums[0].       |
 * | hi = mid vs hi = mid - 1      | Do NOT skip mid going left; mid itself might be the minimum.               |
 * | Loop condition lo < hi        | With hi = mid, using <= causes an infinite loop when lo == hi.             |
 * | Off-by-one on the pivot       | Misidentifying the boundary returns the element just before/after the min. |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                  | Key Idea                                   | Best Used When                          | Time      | Space           |
 * | 1 | Linear Scan               | Walk the array tracking the smallest value | n tiny, or O(log n) not required        | O(n)      | O(1) (ties)     |
 * | 2 | Binary Search (iterative) | Halve by comparing nums[mid] to nums[hi]   | Intended optimal; meets O(log n)        | O(log n)  | O(1)            |
 * | 3 | Binary Search (recursive) | Same logic, expressed recursively          | Teaching the divide step                | O(log n)  | O(log n) stack  |
 *
 * Linear scan and iterative binary search TIE on space at O(1), but
 * binary search dominates on time, so Approach 2 is the overall
 * winner — nothing beats it on time and nothing beats it on space.
 * The recursive version is logically identical but pays O(log n)
 * call-stack cost; prefer the iterative form. Linear scan stays
 * worth knowing because at n <= 5000 its simplicity is near-costless
 * and impossible to get wrong.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Linear Scan
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Initialize min to the first element.
 * 2. Iterate through every element.
 * 3. If current < min, update min.
 * 4. Return min.
 *
 *    public class FindMinLinear {
 *        public int findMin(int[] nums) {
 *            int min = nums[0];
 *            for (int value : nums) {
 *                if (value < min) {
 *                    min = value;
 *                }
 *            }
 *            return min;
 *        }
 *
 *        public static void main(String[] args) {
 *            FindMinLinear solver = new FindMinLinear();
 *            System.out.println(solver.findMin(new int[]{4, 5, 6, 7, 0, 1, 2})); // 0
 *            System.out.println(solver.findMin(new int[]{3, 4, 5, 1, 2}));       // 1
 *            System.out.println(solver.findMin(new int[]{11, 13, 15, 17}));      // 11
 *        }
 *    }
 *
 * This ignores the sorted-and-rotated structure entirely, which is
 * why it's O(n): it can't skip any element.
 *
 * ------------------------------------------------------------
 * Approach 2: Binary Search (Iterative)  [OPTIMAL]
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Set lo = 0, hi = nums.length - 1.
 * 2. While lo < hi:
 *    - mid = lo + (hi - lo) / 2  (overflow-safe midpoint).
 *    - If nums[mid] > nums[hi]: minimum lies strictly right -> lo = mid + 1.
 *    - Else (nums[mid] < nums[hi]): minimum at mid or left -> hi = mid.
 * 3. When lo == hi, return nums[lo].
 *
 *    public class FindMinBinary {
 *        public int findMin(int[] nums) {
 *            int lo = 0, hi = nums.length - 1;
 *            while (lo < hi) {
 *                int mid = lo + (hi - lo) / 2; // avoids (lo + hi) overflow
 *                if (nums[mid] > nums[hi]) {
 *                    lo = mid + 1;             // min is in the right half
 *                } else {
 *                    hi = mid;                 // min is at mid or in the left half
 *                }
 *            }
 *            return nums[lo]; // lo == hi: the inflection point
 *        }
 *
 *        public static void main(String[] args) {
 *            FindMinBinary solver = new FindMinBinary();
 *            System.out.println(solver.findMin(new int[]{4, 5, 6, 7, 0, 1, 2})); // 0
 *            System.out.println(solver.findMin(new int[]{3, 4, 5, 1, 2}));       // 1
 *            System.out.println(solver.findMin(new int[]{11, 13, 15, 17}));      // 11
 *            System.out.println(solver.findMin(new int[]{2, 1}));                // 1
 *            System.out.println(solver.findMin(new int[]{1}));                   // 1
 *        }
 *    }
 *
 * Why anchor on nums[hi] and not nums[lo]? The right end is always on
 * the lower staircase or equal to the pivot region, giving a clean,
 * monotone decision. Anchoring on nums[lo] misleads the fully-sorted
 * case unless you add a guard. Strict > is safe since all elements
 * are distinct.
 *
 * ------------------------------------------------------------
 * Approach 3: Binary Search (Recursive)
 * ------------------------------------------------------------
 * Algorithm (same decision rule, recursive over [lo, hi]):
 * 1. Base case: if lo == hi, return nums[lo].
 * 2. Compute mid.
 * 3. If nums[mid] > nums[hi], recurse on [mid + 1, hi].
 * 4. Else recurse on [lo, mid].
 *
 *    public class FindMinRecursive {
 *        public int findMin(int[] nums) {
 *            return search(nums, 0, nums.length - 1);
 *        }
 *
 *        private int search(int[] nums, int lo, int hi) {
 *            if (lo == hi) {
 *                return nums[lo];              // single element left
 *            }
 *            int mid = lo + (hi - lo) / 2;
 *            if (nums[mid] > nums[hi]) {
 *                return search(nums, mid + 1, hi);
 *            } else {
 *                return search(nums, lo, mid);
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            FindMinRecursive solver = new FindMinRecursive();
 *            System.out.println(solver.findMin(new int[]{4, 5, 6, 7, 0, 1, 2})); // 0
 *            System.out.println(solver.findMin(new int[]{3, 4, 5, 1, 2}));       // 1
 *            System.out.println(solver.findMin(new int[]{11, 13, 15, 17}));      // 11
 *        }
 *    }
 *
 * Recursion depth is bounded by ceil(log2 n), the source of the
 * O(log n) stack space.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Linear Scan
 * ------------------------------------------------------------
 * - Time  O(n): one pass, n comparisons.
 * - Space O(1): a single min variable.
 * - Estimate: n = 5000 -> ~5000 comparisons; n = 16 -> 16.
 *
 * ------------------------------------------------------------
 * Approach 2: Binary Search (Iterative)  [OPTIMAL]
 * ------------------------------------------------------------
 * - Time  O(log n): each iteration discards half. n -> n/2 -> ... -> 1
 *   takes log2 n steps.
 * - Space O(1): only lo, hi, mid.
 * - Estimate: n = 5000 -> ceil(log2 5000) ~ 13 iterations (vs 5000
 *   for linear); n = 16 -> 4 iterations.
 *
 * ------------------------------------------------------------
 * Approach 3: Binary Search (Recursive)
 * ------------------------------------------------------------
 * - Time  O(log n): T(n) = T(n/2) + O(1) = O(log n).
 * - Space O(log n): call stack holds up to ceil(log2 n) frames.
 * - Estimate: n = 5000 -> ~13 peak frames; n = 16 -> 4 frames.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 (Linear Scan) — nums = [3,4,5,1,2]
 * ------------------------------------------------------------
 * | Step | value | min before | min after |
 * | 1    | 3     | 3          | 3         |
 * | 2    | 4     | 3          | 3         |
 * | 3    | 5     | 3          | 3         |
 * | 4    | 1     | 3          | 1         |
 * | 5    | 2     | 1          | 1         |
 * Output: 1
 *
 * ------------------------------------------------------------
 * Approach 2 (Iterative Binary Search) — nums = [4,5,6,7,0,1,2]
 * ------------------------------------------------------------
 * Initial: lo=0, hi=6
 * ├─ Iter 1: mid = 3 -> nums[3]=7, nums[6]=2
 * │          7 > 2  -> min to the right -> lo = 4
 * ├─ Iter 2: lo=4, hi=6, mid = 5 -> nums[5]=1, nums[6]=2
 * │          1 < 2  -> min at mid or left -> hi = 5
 * ├─ Iter 3: lo=4, hi=5, mid = 4 -> nums[4]=0, nums[5]=1
 * │          0 < 1  -> hi = 4
 * └─ Loop ends: lo == hi == 4 -> return nums[4] = 0
 * Output: 0
 *
 * ------------------------------------------------------------
 * Approach 3 (Recursive) — nums = [11,13,15,17] (un-rotated)
 * ------------------------------------------------------------
 * search(lo=0, hi=3)
 * ├─ mid = 1 -> nums[1]=13, nums[3]=17 -> 13 < 17 -> search(0, 1)
 * │  └─ search(lo=0, hi=1)
 * │     ├─ mid = 0 -> nums[0]=11, nums[1]=13 -> 11 < 13 -> search(0, 0)
 * │     │  └─ search(lo=0, hi=0): lo == hi -> return nums[0] = 11
 * Output: 11  (fully-sorted array handled with no special case)
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                | Input           | Expected | How Handled                                   |
 * | Single element           | [1]             | 1        | Loop lo < hi false immediately; returns nums[0]. |
 * | Two elements, rotated    | [2,1]           | 1        | mid=0, 2 > 1 -> lo=1; returns nums[1].        |
 * | Fully sorted (not rotated)| [1,2,3,4,5]    | 1        | Every nums[mid] < nums[hi] -> hi shrinks to 0.|
 * | Rotated so min is first  | [0,1,2,3]       | 0        | Same as sorted; converges to index 0.        |
 * | Min at the very end      | [3,4,5,6,1]     | 1        | Search moves right to isolate the last index.|
 * | Negative values          | [2,-3,-1,0,1]   | -3       | Comparisons are value-based; sign irrelevant.|
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 — dropping mid when going left:
 *    // WRONG: skips mid, which might be the minimum
 *    if (nums[mid] < nums[hi]) hi = mid - 1;
 *    // CORRECT: keep mid as a candidate
 *    if (nums[mid] < nums[hi]) hi = mid;
 *
 * Pitfall 2 — wrong loop condition with hi = mid:
 *    // WRONG: infinite loop when lo == hi and hi = mid never shrinks
 *    while (lo <= hi) { ... hi = mid; }
 *    // CORRECT:
 *    while (lo < hi) { ... hi = mid; }
 *
 * Pitfall 3 — midpoint overflow:
 *    int mid = (lo + hi) / 2;        // WRONG: lo + hi can overflow int
 *    int mid = lo + (hi - lo) / 2;   // CORRECT: overflow-safe
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The single-element array (loop never runs) and the un-rotated
 *    array (no visible pivot) break naive implementations. Both are
 *    handled: the loop returns nums[lo], and the hi = mid branch walks
 *    left to index 0 when already sorted. Empty array is impossible
 *    per constraints (n >= 1), so no null/empty guard is required.
 *
 * Q: Are there any type mismatches?
 * A: No. All inputs/outputs are int. Values fit in int (-5000..5000),
 *    and the overflow-safe midpoint keeps indices safe. No casting or
 *    boxing occurs.
 *
 * Q: How can I verify this works right now?
 *
 *    public class Verify {
 *        static int findMin(int[] nums) {
 *            int lo = 0, hi = nums.length - 1;
 *            while (lo < hi) {
 *                int mid = lo + (hi - lo) / 2;
 *                if (nums[mid] > nums[hi]) lo = mid + 1;
 *                else hi = mid;
 *            }
 *            return nums[lo];
 *        }
 *        static void verify() {
 *            assert findMin(new int[]{4,5,6,7,0,1,2}) == 0;
 *            assert findMin(new int[]{3,4,5,1,2})     == 1;
 *            assert findMin(new int[]{11,13,15,17})   == 11;
 *            assert findMin(new int[]{2,1})           == 1;
 *            assert findMin(new int[]{1})             == 1;
 *            assert findMin(new int[]{2,-3,-1,0,1})   == -3;
 *            System.out.println("All assertions passed.");
 *        }
 *        public static void main(String[] args) {
 *            verify(); // run with: java -ea Verify
 *        }
 *    }
 *
 * | Approach                  | Risk                                   | Mitigation                                          |
 * | Linear Scan               | Too slow if O(log n) required          | Use only when n small or O(log n) not required      |
 * | Binary Search (iterative) | Off-by-one in hi = mid / loop pairing  | Keep lo < hi with hi = mid; test 1- and 2-elem cases|
 * | Binary Search (recursive) | Stack growth on adversarial sizes      | Bounded O(log n) frames; prefer iterative for big n |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #153 · Difficulty: Medium · Very high interview frequency
 * (a canonical binary-search screen).
 *
 * | Company        | Frequency | Notes                                            |
 * | Amazon         | *****     | Extremely common phone-screen binary search      |
 * | Google         | *****     | Often paired with the duplicates variant (#154)  |
 * | Microsoft      | ****      | Frequent onsite; tests loop-invariant clarity    |
 * | Meta           | ****      | Popular warm-up before harder search problems    |
 * | Apple          | ***       | Array/algorithm coding rounds                    |
 * | Bloomberg      | ****      | Sometimes framed as "find rotation index"        |
 * | Adobe          | ***       | Standard medium binary-search question           |
 * | Uber           | ***       | Asked alongside Search in Rotated Sorted (#33)   |
 * | Oracle         | ***       | Early screening rounds                            |
 * | Goldman Sachs  | **        | Occasionally, testing edge-case rigor            |
 *
 * Common follow-ups: #33 "Search in Rotated Sorted Array" and the
 * harder #154 where duplicates force worst-case O(n).
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach                  | Time     | Space    | Code Complexity | Recommended?                                   |
 * | Linear Scan               | O(n)     | O(1)     | Trivial         | acceptable only when n tiny / O(log n) not req |
 * | Binary Search (iterative) | O(log n) | O(1)     | Low-moderate    | BEST OVERALL (wins on time, ties on space)     |
 * | Binary Search (recursive) | O(log n) | O(log n) | Moderate        | fine, but iterative is strictly better on space|
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use the iterative binary search (Approach 2). It uniquely achieves
 * both optimal O(log n) time and O(1) space — no approach beats it on
 * either axis, so there's no time-vs-space trade-off here. The
 * recursive form is the same idea paying O(log n) stack for no benefit.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * The minimum in a rotated sorted array is the single inflection
 * point; locate it by binary-searching with nums[mid] compared to
 * nums[hi] — go right when nums[mid] > nums[hi], else go left keeping
 * mid as a candidate (hi = mid). Two gotchas: pair while (lo < hi)
 * with hi = mid (never hi = mid - 1, or you skip the answer), and use
 * the overflow-safe midpoint lo + (hi - lo) / 2.
 */
// @formatter:on
