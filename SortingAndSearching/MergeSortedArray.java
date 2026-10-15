package SortingAndSearching;

import java.util.Arrays;

public class MergeSortedArray {
    public static void main(String[] args) {
        MergeSortedArray mergeSortedArray = new MergeSortedArray();
        mergeSortedArray.mergeConcatenateSort(new int[] { 1, 2, 3, 0, 0, 0 }, 3, new int[] { 2, 5, 6 }, 3);
        mergeSortedArray.mergeConcatenateSort(new int[] { 4, 5, 6, 0, 0, 0 }, 3, new int[] { 1, 2, 3 }, 3);
        System.out.println("---------------------------");
        mergeSortedArray.mergeForwardCopy(new int[] { 1, 2, 3, 0, 0, 0 }, 3, new int[] { 2, 5, 6 }, 3);
        mergeSortedArray.mergeForwardCopy(new int[] { 4, 5, 6, 0, 0, 0 }, 3, new int[] { 1, 2, 3 }, 3);
        System.out.println("---------------------------");
        mergeSortedArray.mergeBackMergeTwoPointers(new int[] { 1, 2, 3, 0, 0, 0 }, 3, new int[] { 2, 5, 6 }, 3);
        mergeSortedArray.mergeBackMergeTwoPointers(new int[] { 4, 5, 6, 0, 0, 0 }, 3, new int[] { 1, 2, 3 }, 3);
    }

    // @formatter:off
    /*
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/96/sorting-and-searching/587/
     * 
     * 
     * You are given two integer arrays nums1 and nums2, sorted in non-decreasing
     * order, and two integers m and n, representing the number of elements in nums1
     * and nums2 respectively.
     * 
     * Merge nums1 and nums2 into a single array sorted in non-decreasing order.
     * 
     * The final sorted array should not be returned by the function, but instead be
     * stored inside the array nums1. To accommodate this, nums1 has a length of m +
     * n, where the first m elements denote the elements that should be merged, and
     * the last n elements are set to 0 and should be ignored. nums2 has a length of
     * n.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
     * Output: [1,2,2,3,5,6]
     * Explanation: The arrays we are merging are [1,2,3] and [2,5,6].
     * The result of the merge is [1,2,2,3,5,6] with the underlined elements coming
     * from nums1.
     * Example 2:
     * 
     * Input: nums1 = [1], m = 1, nums2 = [], n = 0
     * Output: [1]
     * Explanation: The arrays we are merging are [1] and [].
     * The result of the merge is [1].
     * Example 3:
     * 
     * Input: nums1 = [0], m = 0, nums2 = [1], n = 1
     * Output: [1]
     * Explanation: The arrays we are merging are [] and [1].
     * The result of the merge is [1].
     * Note that because m = 0, there are no elements in nums1. The 0 is only there
     * to ensure the merge result can fit in nums1.
     * 
     * 
     * Constraints:
     * 
     * nums1.length == m + n
     * nums2.length == n
     * 0 <= m, n <= 200
     * 1 <= m + n <= 200
     * -109 <= nums1[i], nums2[j] <= 109
     * 
     * 
     * Follow up: Can you come up with an algorithm that runs in O(m + n) time?
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach                   | Time              | Space        | Code Complexity | Recommended?                        |
     * |----------------------------|-------------------|--------------|-----------------|-------------------------------------|
     * | Concatenate + Sort         | O((m+n) log(m+n)) | O(log(m+n))  | Very low        | V acceptable for quick/one-off use  |
     * 
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    // @formatter:on
    public void mergeConcatenateSort(int[] nums1, int m, int[] nums2, int n) {
        System.out.println("Before : " + Arrays.toString(nums1));
        for (int idx = 0; idx < m; idx++) {
            nums1[m + idx] = nums2[idx];
        }
        Arrays.sort(nums1);
        System.out.println("After : " + Arrays.toString(nums1));
    }

    // @formatter:off
    /**
     * 
     * | Approach                   | Time              | Space        | Code Complexity | Recommended?                        |
     * |----------------------------|-------------------|--------------|-----------------|-------------------------------------|
     * | Forward + copy             | O(m+n)            | O(m)         | Low             | V acceptable when back-merge tricky |
     * 
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    // @formatter:on
    public void mergeForwardCopy(int[] nums1, int m, int[] nums2, int n) {
        System.out.println("Before : " + Arrays.toString(nums1));
        int[] copy = Arrays.copyOf(nums1, m);
        int i = 0, j = 0, k = 0;
        while (i < m && j < n) {
            if (copy[i] <= nums2[j]) {
                nums1[k++] = copy[i++];
            } else {
                nums1[k++] = nums2[j++];
            }
        }

        while (i < m)
            nums1[k++] = copy[i++];
        while (j < n)
            nums1[k++] = nums2[j++];
        System.out.println("After : " + Arrays.toString(nums1));
    }

    // @formatter:off
    /**
     * 
     * | Approach                   | Time              | Space        | Code Complexity | Recommended?                        |
     * |----------------------------|-------------------|--------------|-----------------|-------------------------------------|
     * | Back-merge (two pointers)  | O(m+n)            | O(1)         | Low             | VV best — optimal on time AND space |
     * 
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    // @formatter:on
    public void mergeBackMergeTwoPointers(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int y = n - 1;
        int k = m + n - 1;
        System.out.println("Before : " + Arrays.toString(nums1));
        while (y >= 0) {
            if (i >= 0 && nums1[i] >= nums2[y]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[y--];
            }
        }
        System.out.println("After : " + Arrays.toString(nums1));
    }
}

// @formatter:off
/*
 * ============================================================
 * MERGE SORTED ARRAY — DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are given two integer arrays, nums1 and nums2, each already sorted in
 * non-decreasing order. Array nums1 is oversized on purpose: it has room for
 * m + n elements, where the first m slots hold real values and the last n slots
 * are zero placeholders. Array nums2 holds n real values. Merge everything into
 * nums1 so it ends up as one fully sorted array. The merge must happen IN PLACE
 * — the answer lives inside nums1, not in a returned array.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 *   int[] nums1 — length m + n; first m entries meaningful, trailing n are 0.
 *   int   m     — count of real elements in nums1.
 *   int[] nums2 — length n.
 *   int   n     — count of real elements in nums2.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 *   No return value (void). nums1 is mutated to hold all m + n elements sorted.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 *   nums1.length == m + n
 *   nums2.length == n
 *   0 <= m, n <= 200
 *   1 <= m + n <= 200
 *   -10^9 <= nums1[i], nums2[j] <= 10^9
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 *   Rearrange the combined m + n values so that
 *   nums1[0] <= nums1[1] <= ... <= nums1[m+n-1], written directly into nums1.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *   nums1 = [1,2,3,0,0,0], m = 3
 *   nums2 = [2,5,6],       n = 3
 *   -> nums1 = [1,2,2,3,5,6]
 *
 *   This is LeetCode #88, difficulty Easy.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Both inputs are already sorted, so we never need a full re-sort — we just
 * interleave two ordered streams. The clever twist is WHERE we write. nums1 has
 * empty space at its tail, so if we fill from the BACK (largest values first),
 * we always write into slots that are placeholders or already-consumed, and we
 * never clobber a value we still need to read.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 *   1. Picture two sorted piles of numbered cards, plus a long tray with m cards
 *      on the left and n empty slots on the right.
 *   2. Compare the largest unused card from each pile.
 *   3. Place the bigger one into the rightmost empty slot of the tray.
 *   4. Move that pile's pointer inward and repeat.
 *   5. When nums2 is exhausted, whatever remains in nums1 is already in its
 *      correct sorted position, so you can stop.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                 | Why it's tricky                                        |
 * |---------------------------|--------------------------------------------------------|
 * | In-place requirement      | No fresh result array (optimal), writes must not lose data |
 * | Front-to-back overwrites  | Merging left-to-right overwrites nums1's own elements   |
 * | The placeholder zeros      | Trailing 0s are not data; treating them as real corrupts it |
 * | One array runs out first  | Must handle leftovers when either array empties first   |
 * | m or n equal to 0         | Degenerate inputs must still behave correctly           |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 * | # | Approach                       | Key Idea                                        | Best Used When                       | Time                    | Space                 |
 * |---|--------------------------------|-------------------------------------------------|--------------------------------------|-------------------------|-----------------------|
 * | 1 | Concatenate + Sort             | Copy nums2 into nums1's tail, then Arrays.sort  | Quick to write; optimality unimportant | O((m+n) log(m+n))       | O(log(m+n)) sort stack |
 * | 2 | Two Pointers, forward into copy| Copy nums1's real part aside, merge left-to-right | Want linear time, back-merge feels tricky | O(m+n)               | O(m) for the copy      |
 * | 3 | Two Pointers, from the back  V | Fill nums1 from rightmost slot using larger tail | The intended optimal                 | O(m+n)  [optimal]       | O(1)  [optimal]        |
 *
 * Approaches 2 and 3 share the same linear TIME class but differ sharply on
 * SPACE: approach 2 needs an O(m) auxiliary copy; approach 3 exploits the empty
 * tail to merge with O(1) extra memory. Approach 1 is easiest to write but pays
 * a log factor it doesn't need. Approach 3 dominates on BOTH axes among the
 * linear options — there is no time-vs-space tension in this problem.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Concatenate + Sort
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Copy all n elements of nums2 into nums1 starting at index m.
 *   2. Sort the entire nums1 array.
 *   3. Done — placeholders overwritten with real data, everything ordered.
 *
 *    import java.util.Arrays;
 *
 *    public class MergeSortedArrayConcat {
 *        public void merge(int[] nums1, int m, int[] nums2, int n) {
 *            // Drop nums2's values into the trailing placeholder slots
 *            for (int idx = 0; idx < n; idx++) {
 *                nums1[m + idx] = nums2[idx];
 *            }
 *            // A single sort orders the full combined array
 *            Arrays.sort(nums1);
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums1 = {1, 2, 3, 0, 0, 0};
 *            int[] nums2 = {2, 5, 6};
 *            new MergeSortedArrayConcat().merge(nums1, 3, nums2, 3);
 *            System.out.println(Arrays.toString(nums1)); // [1, 2, 2, 3, 5, 6]
 *        }
 *    }
 *
 * This ignores that both inputs are already sorted, hence the extra log factor.
 *
 * ------------------------------------------------------------
 * Approach 2: Two Pointers, forward into a copy
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Copy the first m real elements of nums1 into a separate array copy.
 *   2. Keep pointers i over copy, j over nums2, k over the write position (0).
 *   3. While both have elements, write the smaller front element and advance.
 *   4. Drain whichever array still has leftovers into nums1.
 *
 *    import java.util.Arrays;
 *
 *    public class MergeSortedArrayCopy {
 *        public void merge(int[] nums1, int m, int[] nums2, int n) {
 *            int[] copy = Arrays.copyOf(nums1, m); // snapshot of nums1's real part
 *            int i = 0, j = 0, k = 0;
 *
 *            while (i < m && j < n) {
 *                if (copy[i] <= nums2[j]) {
 *                    nums1[k++] = copy[i++];
 *                } else {
 *                    nums1[k++] = nums2[j++];
 *                }
 *            }
 *            while (i < m) nums1[k++] = copy[i++];  // leftover from nums1's copy
 *            while (j < n) nums1[k++] = nums2[j++];  // leftover from nums2
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums1 = {1, 2, 3, 0, 0, 0};
 *            int[] nums2 = {2, 5, 6};
 *            new MergeSortedArrayCopy().merge(nums1, 3, nums2, 3);
 *            System.out.println(Arrays.toString(nums1)); // [1, 2, 2, 3, 5, 6]
 *        }
 *    }
 *
 * Using copy[i] <= nums2[j] (rather than <) keeps the merge STABLE.
 *
 * ------------------------------------------------------------
 * Approach 3: Two Pointers, from the back  V  (Optimal)
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Set i = m-1 (last real of nums1), j = n-1 (last of nums2),
 *      k = m+n-1 (last slot of nums1).
 *   2. Loop while j >= 0 (still nums2 elements to place).
 *   3. If i >= 0 AND nums1[i] > nums2[j], write nums1[i] to nums1[k], i--;
 *      else write nums2[j], j--.
 *   4. Decrement k each write.
 *   5. Stop when j < 0: remaining nums1 elements are already in place.
 *
 *    import java.util.Arrays;
 *
 *    public class MergeSortedArrayBack {
 *        public void merge(int[] nums1, int m, int[] nums2, int n) {
 *            int i = m - 1;       // last real element in nums1
 *            int j = n - 1;       // last element in nums2
 *            int k = m + n - 1;   // last writable slot in nums1
 *
 *            while (j >= 0) {
 *                if (i >= 0 && nums1[i] > nums2[j]) {
 *                    nums1[k--] = nums1[i--]; // nums1's tail is larger
 *                } else {
 *                    nums1[k--] = nums2[j--]; // nums2's tail wins (or nums1 done)
 *                }
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums1 = {1, 2, 3, 0, 0, 0};
 *            int[] nums2 = {2, 5, 6};
 *            new MergeSortedArrayBack().merge(nums1, 3, nums2, 3);
 *            System.out.println(Arrays.toString(nums1)); // [1, 2, 2, 3, 5, 6]
 *        }
 *    }
 *
 * Key insight behind while (j >= 0): once nums2 is fully placed, every remaining
 * nums1[0..i] value is smaller than everything already written to the right and
 * is already located correctly — no separate while (i >= 0) drain loop needed.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Concatenate + Sort
 * ------------------------------------------------------------
 *   Time  — O((m+n) log(m+n)): copy loop is O(n), sorting m+n dominates.
 *   Space — O(log(m+n)): dual-pivot quicksort recursion stack; no result array.
 *   Numeric feel: m = n = 100 -> ~ 200 * log2(200) ~ 200 * 7.6 ~ 1,530 comparisons.
 *
 * ------------------------------------------------------------
 * Approach 2: Two Pointers, forward into a copy
 * ------------------------------------------------------------
 *   Time  — O(m+n): each output slot written once; each compare advances a pointer.
 *   Space — O(m): the copy array holds nums1's m real elements.
 *   Numeric feel: m = n = 100 -> ~200 writes, <=200 compares, 100-element copy.
 *
 * ------------------------------------------------------------
 * Approach 3: Two Pointers, from the back  V
 * ------------------------------------------------------------
 *   Time  — O(m+n): places one element per iteration, at most m+n iterations.
 *   Space — O(1): only three integer indices; no auxiliary array.
 *   Numeric feel: m = n = 100 -> ~200 writes, <=200 compares, 0 extra array bytes.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * Shared input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3.
 *
 * ------------------------------------------------------------
 * Approach 1 trace (Concatenate + Sort)
 * ------------------------------------------------------------
 *   Copy nums2 into tail:  nums1 = [1, 2, 3, 2, 5, 6]
 *   Arrays.sort(nums1):    nums1 = [1, 2, 2, 3, 5, 6]   <- final
 *
 * ------------------------------------------------------------
 * Approach 2 trace (forward into a copy)
 * ------------------------------------------------------------
 *   copy = [1,2,3]   (nums2 = [2,5,6])
 *   i j k | compare            | write        | nums1
 *   0 0 0 | copy0=1 <= n2_0=2  | nums1[0]=1   | [1,_,_,_,_,_]
 *   1 0 1 | copy1=2 <= n2_0=2  | nums1[1]=2   | [1,2,_,_,_,_]
 *   2 0 2 | copy2=3 >  n2_0=2  | nums1[2]=2   | [1,2,2,_,_,_]
 *   2 1 3 | copy2=3 <= n2_1=5  | nums1[3]=3   | [1,2,2,3,_,_]
 *   i==m -> drain nums2:   nums1[4]=5, nums1[5]=6
 *   Final: [1,2,2,3,5,6]
 *
 * ------------------------------------------------------------
 * Approach 3 trace (from the back)  V
 * ------------------------------------------------------------
 *   i=2  j=2  k=5
 *   |- nums1[2]=3 > nums2[2]=6? NO  -> nums1[5]=nums2[2]=6, j=1, k=4 -> [1,2,3,0,0,6]
 *   |- nums1[2]=3 > nums2[1]=5? NO  -> nums1[4]=nums2[1]=5, j=0, k=3 -> [1,2,3,0,5,6]
 *   |- nums1[2]=3 > nums2[0]=2? YES -> nums1[3]=nums1[2]=3, i=1, k=2 -> [1,2,3,3,5,6]
 *   |- nums1[1]=2 > nums2[0]=2? NO  -> nums1[2]=nums2[0]=2, j=-1,k=1 -> [1,2,2,3,5,6]
 *   L- j < 0 -> STOP. nums1[0..1]=[1,2] already correct.
 *   Final: [1,2,2,3,5,6]
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 * | Edge Case              | Input                                        | Expected Output   | How Handled                                             |
 * |------------------------|----------------------------------------------|-------------------|---------------------------------------------------------|
 * | nums2 empty (n = 0)    | nums1=[1], m=1, nums2=[], n=0                 | [1]               | Loop condition j >= 0 false immediately; nothing changes |
 * | nums1 empty (m = 0)    | nums1=[1], m=0, nums2=[1], n=1               | [1]               | i starts at -1; i >= 0 guard forces all writes from nums2 |
 * | All of nums2 smaller   | nums1=[4,5,6,0,0,0], m=3, nums2=[1,2,3], n=3 | [1,2,3,4,5,6]     | nums1 tail wins first; nums2 drains into the front later |
 * | All of nums2 larger    | nums1=[1,2,3,0,0,0], m=3, nums2=[4,5,6], n=3 | [1,2,3,4,5,6]     | nums2 tail wins early; nums1 already in place            |
 * | Duplicates across      | nums1=[2,0], m=1, nums2=[2], n=1             | [2,2]             | Strict > preserves stability; equals go to nums2 branch  |
 * | Negative values        | nums1=[-1,0,0], m=1, nums2=[-2,3], n=2       | [-2,-1,3]         | Comparisons work identically for negatives               |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 — merging front-to-back in place (overwrites live data):
 *    // WRONG: writing from index 0 clobbers nums1's own unread elements
 *    int i = 0, j = 0, k = 0;
 *    while (i < m && j < n) {
 *        if (nums1[i] <= nums2[j]) nums1[k++] = nums1[i++]; // may overwrite nums1[i']
 *        else nums1[k++] = nums2[j++];
 *    }
 *    // CORRECT: write from the back so you only overwrite placeholders/consumed slots
 *    int i = m - 1, j = n - 1, k = m + n - 1;
 *    while (j >= 0) {
 *        if (i >= 0 && nums1[i] > nums2[j]) nums1[k--] = nums1[i--];
 *        else nums1[k--] = nums2[j--];
 *    }
 *
 * Pitfall 2 — forgetting the i >= 0 guard:
 *    // WRONG: when nums1 is exhausted, nums1[i] with i == -1 misbehaves
 *    if (nums1[i] > nums2[j]) { ... }
 *    // CORRECT: check the pointer before dereferencing
 *    if (i >= 0 && nums1[i] > nums2[j]) { ... }
 *
 * Pitfall 3 — looping m+n fixed times instead of j >= 0 can read nums2[-1].
 *    Gating on j >= 0 is cleaner and safe.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 * Q: What edge cases might this miss?
 * A: The risky ones are m = 0 (all writes from nums2) and n = 0 (loop must not
 *    run). Back-merge handles both: the i >= 0 guard covers m = 0, the j >= 0
 *    loop condition covers n = 0. Duplicates and negatives use plain comparison.
 *
 * Q: Are there any type mismatches?
 * A: No. All arrays are int[], all indices int, values fit in int
 *    (|value| <= 10^9 < 2^31 - 1). No casting; the comparison subtracts nothing,
 *    so no overflow risk even at the value bounds.
 *
 * Q: How can I verify this works right now?
 *    import java.util.Arrays;
 *
 *    public class MergeVerify {
 *        static void merge(int[] nums1, int m, int[] nums2, int n) {
 *            int i = m - 1, j = n - 1, k = m + n - 1;
 *            while (j >= 0) {
 *                if (i >= 0 && nums1[i] > nums2[j]) nums1[k--] = nums1[i--];
 *                else nums1[k--] = nums2[j--];
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] a = {1,2,3,0,0,0}; merge(a, 3, new int[]{2,5,6}, 3);
 *            assert Arrays.equals(a, new int[]{1,2,2,3,5,6});
 *
 *            int[] b = {1};           merge(b, 1, new int[]{}, 0);
 *            assert Arrays.equals(b, new int[]{1});
 *
 *            int[] c = {0};           merge(c, 0, new int[]{1}, 1);
 *            assert Arrays.equals(c, new int[]{1});
 *
 *            int[] d = {4,5,6,0,0,0}; merge(d, 3, new int[]{1,2,3}, 3);
 *            assert Arrays.equals(d, new int[]{1,2,3,4,5,6});
 *
 *            System.out.println("All assertions passed.");
 *        }
 *    }
 *    // Run with assertions enabled:  javac MergeVerify.java && java -ea MergeVerify
 *
 * | Approach            | Risk                                      | Mitigation                             |
 * |---------------------|-------------------------------------------|----------------------------------------|
 * | Concatenate + Sort  | Wastes pre-sorted structure; log factor   | Use only when simplicity trumps speed  |
 * | Forward + copy      | Extra O(m) memory                         | Prefer back-merge when memory matters  |
 * | Back-merge          | Off-by-one on i,j,k; missing i >= 0 guard | Init all three to length-1; guard i    |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #88 · Difficulty: Easy · Thousands of reported interview appearances
 * (one of the most common "Easy" array questions).
 *
 * | Company           | Frequency (stars) | Notes                                        |
 * |-------------------|-------------------|----------------------------------------------|
 * | Facebook / Meta   | *****             | Extremely common phone-screen warmup.        |
 * | Amazon            | *****             | Frequent; often extends to merge-k-lists.    |
 * | Microsoft         | ****              | Popular for testing in-place reasoning.      |
 * | Google            | ****              | Lead-in to harder merge variants.            |
 * | Apple             | ***               | Appears in early-round screens.              |
 * | Bloomberg         | ****              | Classic two-pointer favorite.                |
 * | Adobe             | ***               | Shows up in array rounds.                     |
 * | Uber              | ***               | Sometimes a merge-step in a larger problem.  |
 * | Oracle            | ***               | Standard fundamentals check.                 |
 * | Goldman Sachs     | **                | Occasional; tests clean in-place code.       |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 * | Approach                   | Time              | Space        | Code Complexity | Recommended?                        |
 * |----------------------------|-------------------|--------------|-----------------|-------------------------------------|
 * | Concatenate + Sort         | O((m+n) log(m+n)) | O(log(m+n))  | Very low        | V acceptable for quick/one-off use  |
 * | Forward + copy             | O(m+n)            | O(m)         | Low             | V acceptable when back-merge tricky |
 * | Back-merge (two pointers)  | O(m+n)            | O(1)         | Low             | VV best — optimal on time AND space |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use the back-to-front two-pointer merge. Linear time, constant extra space,
 * and — unlike most problems — no time-vs-space tension: it wins on both axes.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * When you must merge into an array that has spare room at the END, fill from
 * the BACK using the larger of the two tails — that's what makes an in-place
 * O(1)-space merge possible without overwriting unread data. Guard the shrinking
 * nums1 pointer with i >= 0, and loop while j >= 0 because leftover nums1 values
 * are already home. This "write into the empty tail" trick generalizes to many
 * in-place array-shifting problems.
 */
// @formatter:on
