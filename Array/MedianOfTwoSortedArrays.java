package Array;

public class MedianOfTwoSortedArrays {
    public static void main(String[] args) {
        MedianOfTwoSortedArrays medianOfTwoSortedArrays = new MedianOfTwoSortedArrays();
        System.out.println("MedianOfTwoSortedArrays : "
                + medianOfTwoSortedArrays.findMedianSortedArraysMergeFull(new int[] { 1, 3 }, new int[] { 2 }));
        System.out.println("MedianOfTwoSortedArrays : "
                + medianOfTwoSortedArrays.findMedianSortedArraysMergeFull(new int[] { 1, 2 }, new int[] { 3, 4 }));
        System.out.println("---------------------------------");
        System.out.println("MedianOfTwoSortedArrays : "
                + medianOfTwoSortedArrays.findMedianSortedArraysMergeHalfway(new int[] { 1, 3 }, new int[] { 2 }));
        System.out.println("MedianOfTwoSortedArrays : "
                + medianOfTwoSortedArrays.findMedianSortedArraysMergeHalfway(new int[] { 1, 2 }, new int[] { 3, 4 }));
        System.out.println("---------------------------------");
        System.out.println("MedianOfTwoSortedArrays : "
                + medianOfTwoSortedArrays.findMedianSortedArraysBinarySearchPartition(new int[] { 1, 3 },
                        new int[] { 2 }));
        System.out.println("MedianOfTwoSortedArrays : "
                + medianOfTwoSortedArrays.findMedianSortedArraysBinarySearchPartition(new int[] { 1, 2 },
                        new int[] { 3, 4 }));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/median-of-two-sorted-arrays/description/
     * 
     * Given two sorted arrays nums1 and nums2 of size m and n respectively, return
     * the median of the two sorted arrays.
     * 
     * The overall run time complexity should be O(log (m+n)).
     * 
     * 
     * 
     * Example 1:
     * 
     * Input:
     * int[] nums1 = new int[]{1, 3};
     * int[] nums2 = new int[]{2};
     * 
     * Output: 2.00000
     * Explanation: merged array = [1,2,3] and median is 2.
     * Example 2:
     * 
     * Input:
     * int[] nums1 = new int[]{1, 2};
     * int[] nums2 = new int[]{3, 4};
     * 
     * Output: 2.50000
     * Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
     * 
     * 
     * Constraints:
     * 
     * nums1.length == m
     * nums2.length == n
     * 0 <= m <= 1000
     * 0 <= n <= 1000
     * 1 <= m + n <= 2000
     * -106 <= nums1[i], nums2[i] <= 106
     */
    // @formatter:on

    // @formatter:off
    /**
     * | Approach                    | Time             | Space    | Code Complexity | Recommended?                          |
     * |-----------------------------|------------------|----------|-----------------|---------------------------------------|
     * | 1. Merge full               | O(m+n)           | O(m+n)   | Low             | X wasteful memory; baseline only      |
     * 
     * @param nums1
     * @param nums2
     * @return
     */
    // @formatter:on
    public double findMedianSortedArraysMergeFull(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int total = m + n;
        int[] merged = new int[total];
        int i = 0, j = 0, k = 0;
        while (i < m && j < n) {
            if (nums1[i] < nums2[j])
                merged[k++] = nums1[i++];
            else
                merged[k++] = nums2[j++];
        }
        while (i < m)
            merged[k++] = nums1[i++];
        while (j < n)
            merged[k++] = nums2[j++];

        int mid = total / 2;
        return total % 2 == 0 ? ((merged[mid - 1] + merged[mid]) / 2.0) : merged[mid];
    }

    // @formatter:off
    /**
     * | Approach                    | Time             | Space    | Code Complexity | Recommended?                          |
     * |-----------------------------|------------------|----------|-----------------|---------------------------------------|
     * | 2. Merge halfway            | O(m+n)           | O(1)     | Low             | OK best for low memory at linear time |
     * 
     * @param nums1
     * @param nums2
     * @return
     */
    // @formatter:on
    public double findMedianSortedArraysMergeHalfway(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int total = m + n;

        int stopIndex = total / 2;
        int i = 0, j = 0;
        int prev = 0, curr = 0;
        for (int count = 0; count <= stopIndex; count++) {
            prev = curr;
            if (i < m && (j >= n || nums1[i] <= nums2[j])) {
                curr = nums1[i++];
            } else {
                curr = nums2[j++];
            }
        }

        if (total % 2 == 1)
            return curr;
        else
            return (prev + curr) / 2.0;
    }

    // @formatter:off
    /**
     * | Approach                    | Time             | Space    | Code Complexity | Recommended?                          |
     * |-----------------------------|------------------|----------|-----------------|---------------------------------------|
     * | 3. Binary search partition  | O(log(min(m,n))) | O(1)     | High            | BEST overall — wins on time and space |
     * 
     * @param nums1
     * @param nums2
     * @return
     */
    // @formatter:on
    public double findMedianSortedArraysBinarySearchPartition(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length)
            return findMedianSortedArraysBinarySearchPartition(nums2, nums1);

        int m = nums1.length, n = nums2.length;
        int leftHalfLength = (m + n + 1) / 2;
        int lo = 0, hi = m;

        while (lo <= hi) {
            int i = (lo + hi) / 2;
            int j = leftHalfLength - i;

            int left1 = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int right1 = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int left2 = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int right2 = (j == n) ? Integer.MAX_VALUE : nums2[j];

            if (left1 <= right2 && left2 <= right1) {
                int maxLeft = Math.max(left1, left2);
                if ((m + n) % 2 == 1)
                    return maxLeft;
                int minRight = Math.min(right1, right2);
                return (maxLeft + minRight) / 2.0;
            } else if (left1 > right2) {
                hi = i - 1;
            } else {
                lo = i + 1;
            }
        }
        throw new IllegalArgumentException("Input arrays are not sorted.");
    }
}

// @formatter:off
/*
 * ============================================================
 * MEDIAN OF TWO SORTED ARRAYS — DEEP DIVE EXPLANATION
 * ============================================================
 * LeetCode #4 · Difficulty: Hard
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are given two arrays, each already sorted in ascending order.
 * Conceptually, if you merged them into one big sorted array, that
 * merged array would have a MEDIAN — the middle value (odd total
 * length) or the average of the two middle values (even total
 * length). Compute that median WITHOUT necessarily building the
 * merged array, ideally in logarithmic time.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums1 — a sorted array of length m (0 <= m)
 * int[] nums2 — a sorted array of length n (0 <= n)
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * double — the median of the combined set of m + n numbers.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * | Constraint            | Value                       |
 * |-----------------------|-----------------------------|
 * | nums1.length (m)      | 0 <= m <= 1000              |
 * | nums2.length (n)      | 0 <= n <= 1000              |
 * | Combined length       | 1 <= m + n <= 2000          |
 * | Element value range   | -10^6 <= nums[i] <= 10^6    |
 * | Required time         | O(log(m + n))               |
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Let total = m + n.
 *  - total odd  -> element at position total/2 (0-indexed) of merged sort.
 *  - total even -> average of positions total/2 - 1 and total/2.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    nums1 = [1, 3], nums2 = [2]
 *    Merged = [1, 2, 3]  -> total = 3 (odd)  -> median = 2.0
 *
 *    nums1 = [1, 2], nums2 = [3, 4]
 *    Merged = [1, 2, 3, 4] -> total = 4 (even) -> median = (2+3)/2 = 2.5
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * The median splits a sorted collection into a LEFT half and a
 * RIGHT half of (almost) equal size, where every element on the
 * left is <= every element on the right. If we draw a vertical
 * "cut" through BOTH arrays at once so the combined left side has
 * exactly the right number of elements, the median sits at that
 * boundary. Find that cut with BINARY SEARCH, not by merging.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Merge mentally first (brute idea): median trivial to read,
 *    but O(m+n) — correct but slow.
 * 2. Notice I only need the middle, not the whole thing.
 * 3. Reframe as "partitioning": cut nums1 after i elements and
 *    nums2 after j elements so i + j = size of the left half.
 * 4. The left half is valid only when the largest left element
 *    is <= the smallest right element across BOTH arrays:
 *    nums1[i-1] <= nums2[j]  AND  nums2[j-1] <= nums1[i].
 * 5. Binary search the cut; search only the SMALLER array so the
 *    range is O(log(min(m,n))).
 * 6. Read the median off the four boundary values.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                     | Why it's tricky                                              |
 * |-------------------------------|--------------------------------------------------------------|
 * | Two arrays, one cut condition | j is derived from i, so it's a 1-D search, not 2-D.          |
 * | Boundary / empty partitions   | Cuts at index 0 or end need -inf / +inf substitutes.        |
 * | Odd vs even total             | (m+n+1)/2 chosen so one formula handles both parities.      |
 * | Search the smaller array      | Else j can go negative or overflow the other array.         |
 * | Off-by-one in half size       | (m+n+1)/2 puts the extra odd element in the left half.      |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                    | Key Idea                                          | Best Used When                        | Time                    | Space          |
 * |---|-----------------------------|---------------------------------------------------|---------------------------------------|-------------------------|----------------|
 * | 1 | Merge fully, then index     | Build merged sorted array, read the middle        | Simplicity; small inputs; baseline    | O(m + n)                | O(m + n)       |
 * | 2 | Merge halfway (two pointers)| Advance to median position, keep last two values  | Linear time but O(1) extra space      | O(m + n)                | O(1) [space-optimal] |
 * | 3 | Binary search on partition  | Binary-search a cut in the smaller array          | Large inputs; O(log) required         | O(log(min(m,n))) [time-optimal] | O(1)   |
 *
 * Trade-off: Approaches 1 and 2 are both linear; Approach 2 dominates 1
 * (same time, O(1) space, no merged array built). Approach 3 wins on time
 * AND keeps O(1) space, so the time-optimal and space-optimal choices
 * coincide in Approach 3. Prefer Approach 3 for large inputs or the
 * logarithmic follow-up; Approach 2 is a simpler fallback for modest m+n.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Merge Fully, Then Index
 * ------------------------------------------------------------
 * Steps:
 * 1. Allocate a result array of size m + n.
 * 2. Two-pointer merge nums1 and nums2 in sorted order.
 * 3. Copy any leftover elements.
 * 4. Odd total -> merged[total/2].
 * 5. Even total -> average of merged[total/2 - 1] and merged[total/2].
 *
 *    public class MedianMergeFull {
 *        public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
 *            int m = nums1.length, n = nums2.length;
 *            int total = m + n;
 *            int[] merged = new int[total];
 *            int i = 0, j = 0, k = 0;
 *            while (i < m && j < n) {
 *                if (nums1[i] <= nums2[j]) merged[k++] = nums1[i++];
 *                else                      merged[k++] = nums2[j++];
 *            }
 *            while (i < m) merged[k++] = nums1[i++];
 *            while (j < n) merged[k++] = nums2[j++];
 *            int mid = total / 2;
 *            if (total % 2 == 1) return merged[mid];
 *            else return (merged[mid - 1] + merged[mid]) / 2.0;
 *        }
 *        public static void main(String[] args) {
 *            System.out.println(findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));    // 2.0
 *            System.out.println(findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4})); // 2.5
 *        }
 *    }
 *
 * Note: / 2.0 forces floating-point division, so (2 + 3) / 2.0 = 2.5.
 *
 * ------------------------------------------------------------
 * Approach 2: Merge Halfway (Two Pointers, No Array)
 * ------------------------------------------------------------
 * Steps:
 * 1. total = m + n; we only need index total/2 in the virtual merge.
 * 2. Walk total/2 + 1 steps, each time picking the smaller front.
 * 3. Keep rolling prev and curr — no full array stored.
 * 4. After the loop curr = element at total/2, prev = total/2 - 1.
 * 5. Odd -> curr; Even -> (prev + curr) / 2.0.
 *
 *    public class MedianMergeHalf {
 *        public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
 *            int m = nums1.length, n = nums2.length;
 *            int total = m + n;
 *            int stopIndex = total / 2;
 *            int i = 0, j = 0;
 *            int prev = 0, curr = 0;
 *            for (int count = 0; count <= stopIndex; count++) {
 *                prev = curr;
 *                if (i < m && (j >= n || nums1[i] <= nums2[j])) curr = nums1[i++];
 *                else                                           curr = nums2[j++];
 *            }
 *            if (total % 2 == 1) return curr;
 *            else return (prev + curr) / 2.0;
 *        }
 *        public static void main(String[] args) {
 *            System.out.println(findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));    // 2.0
 *            System.out.println(findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4})); // 2.5
 *        }
 *    }
 *
 * The guard (j >= n || nums1[i] <= nums2[j]) handles exhaustion.
 *
 * ------------------------------------------------------------
 * Approach 3: Binary Search on Partition [OPTIMAL]
 * ------------------------------------------------------------
 * Steps:
 * 1. Ensure nums1 is the smaller array; swap if not.
 * 2. leftHalfLen = (m + n + 1) / 2 (extra odd element goes left).
 * 3. Binary-search i (cut in nums1) in [0, m]; j = leftHalfLen - i.
 * 4. Boundary values with +/- infinity where a partition is empty/full:
 *      left1  = (i == 0) ? -inf : nums1[i-1]
 *      right1 = (i == m) ? +inf : nums1[i]
 *      left2  = (j == 0) ? -inf : nums2[j-1]
 *      right2 = (j == n) ? +inf : nums2[j]
 * 5. Valid when left1 <= right2 AND left2 <= right1.
 *      left1 > right2 -> cut too far right -> hi = i - 1
 *      else           -> cut too far left  -> lo = i + 1
 * 6. maxLeft = max(left1, left2); minRight = min(right1, right2).
 *      Odd  -> maxLeft
 *      Even -> (maxLeft + minRight) / 2.0
 *
 *    public class MedianBinarySearch {
 *        public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
 *            if (nums1.length > nums2.length) return findMedianSortedArrays(nums2, nums1);
 *            int m = nums1.length, n = nums2.length;
 *            int leftHalfLen = (m + n + 1) / 2;
 *            int lo = 0, hi = m;
 *            while (lo <= hi) {
 *                int i = (lo + hi) / 2;
 *                int j = leftHalfLen - i;
 *                int left1  = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
 *                int right1 = (i == m) ? Integer.MAX_VALUE : nums1[i];
 *                int left2  = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
 *                int right2 = (j == n) ? Integer.MAX_VALUE : nums2[j];
 *                if (left1 <= right2 && left2 <= right1) {
 *                    int maxLeft = Math.max(left1, left2);
 *                    if ((m + n) % 2 == 1) return maxLeft;
 *                    int minRight = Math.min(right1, right2);
 *                    return (maxLeft + minRight) / 2.0;
 *                } else if (left1 > right2) {
 *                    hi = i - 1;
 *                } else {
 *                    lo = i + 1;
 *                }
 *            }
 *            throw new IllegalArgumentException("Input arrays are not sorted.");
 *        }
 *        public static void main(String[] args) {
 *            System.out.println(findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));       // 2.0
 *            System.out.println(findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}));    // 2.5
 *            System.out.println(findMedianSortedArrays(new int[]{}, new int[]{1}));           // 1.0
 *            System.out.println(findMedianSortedArrays(new int[]{0, 0}, new int[]{0, 0}));    // 0.0
 *        }
 *    }
 *
 * Why (m + n + 1) / 2? Odd total 5 -> left half holds 3, median = maxLeft.
 * Even total 4 -> left half holds 2, median = average of maxLeft, minRight.
 * The MIN_VALUE / MAX_VALUE sentinels stand in for +/- infinity so boundary
 * cuts never index out of bounds.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Approach 1 — Merge Fully
 *  - Time: O(m + n). Each element written once; median read O(1).
 *  - Space: O(m + n) for the merged array.
 *  - Feel: m = n = 1000 -> ~2000 writes and a 2000-int buffer.
 *
 * Approach 2 — Merge Halfway
 *  - Time: O(m + n). Loop runs ~(m+n)/2 iterations, each O(1).
 *  - Space: O(1). Only prev, curr, two pointers.
 *  - Feel: m = n = 1000 -> ~1001 iterations, 0 extra cells.
 *
 * Approach 3 — Binary Search on Partition [OPTIMAL]
 *  - Time: O(log(min(m, n))). Range halves each step; O(1) per step.
 *  - Space: O(1). A few scalars (recursive swap adds one frame).
 *  - Feel: m = n = 1000 -> ~10 iterations; m = n = 1,000,000 -> ~20.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 — Merge Fully
 * ------------------------------------------------------------
 * Input: nums1 = [1, 3], nums2 = [2], total = 3 (odd).
 * | Step | Compare  | Take               | merged     |
 * |------|----------|--------------------|------------|
 * | 1    | 1 vs 2   | 1 (from nums1)     | [1]        |
 * | 2    | 3 vs 2   | 2 (from nums2)     | [1, 2]     |
 * | 3    | nums2 empty | 3 (leftover)    | [1, 2, 3]  |
 * mid = 1, odd -> merged[1] = 2 -> median = 2.0
 *
 * ------------------------------------------------------------
 * Approach 2 — Merge Halfway
 * ------------------------------------------------------------
 * Input: nums1 = [1, 2], nums2 = [3, 4], total = 4 (even), stopIndex = 2.
 *   count=0: compare 1 vs 3 -> take 1 (i=1); prev=0, curr=1
 *   count=1: compare 2 vs 3 -> take 2 (i=2); prev=1, curr=2
 *   count=2: i>=m -> take nums2[0]=3 (j=1);  prev=2, curr=3
 * Even -> (prev + curr) / 2.0 = (2 + 3) / 2.0 -> median = 2.5
 *
 * ------------------------------------------------------------
 * Approach 3 — Binary Search on Partition
 * ------------------------------------------------------------
 * Input: nums1 = [1, 3], nums2 = [2]. Swap so smaller is nums1:
 *   nums1 = [2] (m=1), nums2 = [1, 3] (n=2).
 * leftHalfLen = (1 + 2 + 1)/2 = 2. Search i in [0, 1], lo=0, hi=1.
 *
 *   Iteration 1: i = 0, j = 2
 *     left1 = -inf; right1 = nums1[0] = 2
 *     left2 = nums2[1] = 3; right2 = +inf
 *     Check: -inf <= +inf OK; 3 <= 2 ? NO -> left2 > right1 -> lo = 1
 *   Iteration 2: i = 1, j = 1
 *     left1 = nums1[0] = 2; right1 = +inf
 *     left2 = nums2[0] = 1; right2 = nums2[1] = 3
 *     Check: 2 <= 3 OK; 1 <= +inf OK -> VALID
 *     total = 3 (odd) -> maxLeft = max(2, 1) = 2
 * median = 2.0 (matches Approaches 1 and 2)
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case            | Input                          | Expected | How Handled                                        |
 * |----------------------|--------------------------------|----------|----------------------------------------------------|
 * | One array empty      | nums1=[], nums2=[1]            | 1.0      | Empty array drives search; ±inf sentinels; i=0 ok  |
 * | Both single element  | nums1=[1], nums2=[2]          | 1.5      | Even total; maxLeft=1, minRight=2 -> average        |
 * | All equal values     | nums1=[0,0], nums2=[0,0]      | 0.0      | Any valid cut gives maxLeft = minRight = 0         |
 * | Disjoint ranges      | nums1=[1,2], nums2=[3,4]      | 2.5      | Cut lands between arrays; sentinels handle sides   |
 * | Very different sizes | nums1=[1], nums2=[2,3,4,5,6]  | 3.5      | Swap -> search length-1 array -> 1-2 iterations     |
 * | Negative values      | nums1=[-5,-3], nums2=[-2,-1]  | -2.5     | MIN_VALUE sentinel below all real values           |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * - Integer division in the even case:
 *      return (maxLeft + minRight) / 2;    // WRONG: (2+3)/2 = 2
 *      return (maxLeft + minRight) / 2.0;  // CORRECT: 2.5
 * - Searching the larger array: j can go negative or exceed n.
 *      // WRONG: no swap when m > n
 *      // CORRECT: if (nums1.length > nums2.length) swap first
 * - Forgetting the +/- infinity sentinels:
 *      int left1 = nums1[i - 1];                               // WRONG at i=0
 *      int left1 = (i == 0) ? Integer.MIN_VALUE : nums1[i-1];  // CORRECT
 * - Wrong half size: (m + n) / 2 breaks the odd case; use (m + n + 1) / 2.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: Empty arrays and boundary cuts (i = 0 or i = m). Both covered by the
 *    MIN_VALUE / MAX_VALUE sentinels, letting empty/full partitions compare
 *    correctly. All-equal and negative values work via relational compares.
 *
 * Q: Are there any type mismatches?
 * A: The even-median division must be floating point (/ 2.0). Sums stay well
 *    within int range (max ~2x10^6 < Integer.MAX_VALUE), so no overflow.
 *    Return type is double throughout.
 *
 * Q: How can I verify this works right now?
 *
 *    public class MedianVerify {
 *        public static void main(String[] args) {
 *            verify();
 *            System.out.println("All assertions passed.");
 *        }
 *        static void verify() {
 *            // Enable with:  java -ea MedianVerify
 *            assert MedianBinarySearch.findMedianSortedArrays(new int[]{1, 3}, new int[]{2}) == 2.0;
 *            assert MedianBinarySearch.findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}) == 2.5;
 *            assert MedianBinarySearch.findMedianSortedArrays(new int[]{}, new int[]{1}) == 1.0;
 *            assert MedianBinarySearch.findMedianSortedArrays(new int[]{0, 0}, new int[]{0, 0}) == 0.0;
 *            assert MedianBinarySearch.findMedianSortedArrays(new int[]{1}, new int[]{2, 3, 4, 5, 6}) == 3.5;
 *            assert MedianBinarySearch.findMedianSortedArrays(new int[]{-5, -3}, new int[]{-2, -1}) == -2.5;
 *        }
 *    }
 *
 * Run with assertions enabled (java -ea) — silence means every case matched.
 *
 * | Approach        | Risk                                   | Mitigation                                  |
 * |-----------------|----------------------------------------|---------------------------------------------|
 * | 1. Merge full   | O(m+n) memory blows up on huge inputs  | Prefer Approach 2/3 when memory matters     |
 * | 2. Merge half   | Off-by-one / pointer exhaustion        | Loop stopIndex+1 times; guard with j >= n   |
 * | 3. Binary search| Out-of-bounds cuts; wrong array        | ±inf sentinels; always swap to smaller array|
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #4 · Difficulty: Hard · Very frequent (thousands of appearances).
 *
 * | Company           | Frequency (stars) | Notes                                          |
 * |-------------------|-------------------|------------------------------------------------|
 * | Amazon            | *****             | Recurring hard-round; expects O(log) partition |
 * | Google            | *****             | Classic binary-search-on-answer test           |
 * | Microsoft         | ****              | Often paired with k-th element follow-up        |
 * | Meta (Facebook)   | ****              | Phone and onsite loops                          |
 * | Apple             | ****              | Algorithm-heavy interviews                      |
 * | Bloomberg         | ****              | Popular for edge-case density                   |
 * | Adobe             | ***               | Senior-level rounds                             |
 * | Goldman Sachs     | ***               | Numeric precision and edge handling             |
 * | Uber              | ***               | Occasional onsite                               |
 * | ByteDance/TikTok  | ***               | Competitive-heavy loops                         |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach                    | Time             | Space    | Code Complexity | Recommended?                          |
 * |-----------------------------|------------------|----------|-----------------|---------------------------------------|
 * | 1. Merge full               | O(m+n)           | O(m+n)   | Low             | X wasteful memory; baseline only      |
 * | 2. Merge halfway            | O(m+n)           | O(1)     | Low             | OK best for low memory at linear time |
 * | 3. Binary search partition  | O(log(min(m,n))) | O(1)     | High            | BEST overall — wins on time and space |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use Approach 3 (binary search on partition): optimal on both axes
 * (O(log(min(m,n))) time, O(1) space) and the expected interview answer.
 * If short on time or inputs are small, Approach 2 gives O(1) space at
 * linear time with far less room for error.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * The median is a PARTITION problem: find a cut so the combined left half
 * has (m+n+1)/2 elements and left1 <= right2 && left2 <= right1. Binary-
 * search the SMALLER array, derive j = leftHalfLen - i, and guard every
 * boundary with +/- infinity sentinels. Key gotcha: divide by 2.0 (not 2)
 * for the even case.
 *
 */
// @formatter:on
