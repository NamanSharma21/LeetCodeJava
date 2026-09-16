package Array;

import java.util.Arrays;

public class SortColors {
    public static void main(String[] args) {
        SortColors sortColors = new SortColors();
        sortColors.sortColorsLibrarySort(new int[] { 2, 0, 2, 1, 1, 0 });
        System.out.println("----------------------------------");
        sortColors.sortColorsCountingSort(new int[] { 2, 0, 2, 1, 1, 0 });
        System.out.println("----------------------------------");
        sortColors.sortColorsDutchNationalFlag(new int[] { 2, 0, 2, 1, 1, 0 });
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/sort-colors/
     * 
     * You are given an array nums with n objects colored red, white, or blue, sort
     * them in-place so that objects of the same color are adjacent, with the colors
     * in the order red, white, and blue.
     * 
     * We will use the integers 0, 1, and 2 to represent the color red, white, and
     * blue, respectively.
     * 
     * You must solve this problem without using the library's sort function.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [2,0,2,1,1,0]
     * 
     * Output: [0,0,1,1,2,2]
     * 
     * Explanation:
     * 
     * The array has two 0s, two 1s, and two 2s. Sorting them in-place places all 0s
     * first, then all 1s, then all 2s.
     * 
     * Example 2:
     * 
     * Input: nums = [2,0,1]
     * 
     * Output: [0,1,2]
     * 
     * Explanation:
     * 
     * The array has one each of 0, 1, and 2, arranged in-place in the order 0, 1,
     * 2.
     * 
     * 
     * 
     * Constraints:
     * 
     * n == nums.length
     * 1 <= n <= 300
     * nums[i] is either 0, 1, or 2.
     * 
     * 
     * Follow up: Could you come up with a one-pass algorithm using only constant
     * extra space?
     */
    // @formatter:on

    // @formatter:off
    /**
     * | Approach                       | Time       | Space     | Code Complexity | Recommended?                          |
     * |--------------------------------|------------|-----------|-----------------|---------------------------------------|
     * | Library Sort                   | O(n log n) | O(1)-O(n) | Trivial         | NO - sidesteps the problem; baseline  |
     * 
     * @param nums
     */
    // @formatter:on
    public void sortColorsLibrarySort(int[] nums) {
        System.out.println("Before : " + Arrays.toString(nums));
        Arrays.sort(nums);
        System.out.println("After : " + Arrays.toString(nums));
    }

    // @formatter:off
    /**
     * | Approach                       | Time       | Space     | Code Complexity | Recommended?                          |
     * |--------------------------------|------------|-----------|-----------------|---------------------------------------|
     * | Counting Sort (two-pass)       | O(n)       | O(1)      | Low             | YES - great when simplicity matters   |
     * 
     * @param nums
     */
    // @formatter:on
    public void sortColorsCountingSort(int[] nums) {
        System.out.println("Before : " + Arrays.toString(nums));
        int[] count = new int[3];
        for (int value : nums) {
            count[value]++;
        }

        int index = 0;
        for (int color = 0; color < 3; color++) {
            for (int k = 0; k < count[color]; k++) {
                nums[index++] = color;
            }
        }
        System.out.println("After : " + Arrays.toString(nums));
    }

    // @formatter:off
    /**
     * | Approach                       | Time       | Space     | Code Complexity | Recommended?                          |
     * |--------------------------------|------------|-----------|-----------------|---------------------------------------|
     * | Dutch National Flag (one-pass) | O(n)       | O(1)      | Moderate        | BEST - one pass, in-place, expected   |
     * 
     * @param nums
     */
    // @formatter:on
    public void sortColorsDutchNationalFlag(int[] nums) {
        System.out.println("Before : " + Arrays.toString(nums));
        int low = 0, mid = 0, high = nums.length - 1;
        while (mid <= high) {
            switch (nums[mid]) {
                case 0:
                    swap(nums, low, mid);
                    low++;
                    mid++;
                    break;
                case 1:
                    mid++;
                    break;
                case 2:
                    swap(nums, mid, high);
                    high--;
                    break;
                default:
                    break;
            }
        }
        System.out.println("After : " + Arrays.toString(nums));
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

// @formatter:off
/*
 * ============================================================
 * SORT COLORS - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are given an array where every element is one of exactly three values:
 * 0, 1, or 2. These represent red (0), white (1), and blue (2). Rearrange the
 * array IN-PLACE so all 0s come first, then all 1s, then all 2s.
 *
 * This is LeetCode #75 - Sort Colors (Medium). The catch: solve it WITHOUT a
 * library sort, ideally in a SINGLE pass using CONSTANT extra space.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums - an array where each value is guaranteed to be 0, 1, or 2.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * No return value (void). The array nums is modified in-place.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * | Constraint          | Value                          |
 * |---------------------|--------------------------------|
 * | Array length n      | 1 <= n <= 300                  |
 * | Element values      | Each nums[i] is 0, 1, or 2     |
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Rearrange nums into non-decreasing order. Because there are only three
 * distinct values, this is a PARTITIONING problem, not a general comparison
 * sort - which is what unlocks the linear-time solution.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  nums = [2, 0, 2, 1, 1, 0]
 *    Output: nums = [0, 0, 1, 1, 2, 2]
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Sorting laundry into three bins: sweep through once, tossing each item toward
 * its correct end - 0s to the far left, 2s to the far right, 1s settle in the
 * middle. This is the Dutch National Flag algorithm (Dijkstra); the Dutch flag
 * has three stripes - red, white, blue - exactly our three colors.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Keep three regions: known-0 on the left, known-2 on the right, and an
 *    unprocessed middle.
 * 2. Walk a scanner through the unprocessed middle.
 * 3. See a 0 -> swap into the left region and grow it.
 * 4. See a 2 -> swap into the right region and shrink the middle from the right.
 * 5. See a 1 -> leave it and move the scanner forward.
 * 6. When the scanner meets the right boundary, partitioning is complete.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                       | Why it's tricky                                                        |
 * |---------------------------------|------------------------------------------------------------------------|
 * | Single pass with O(1) space     | Must partition live during the scan; no counting array + rewrite pass. |
 * | The three-pointer dance         | low, mid, high move under different rules; mixing them causes bugs.    |
 * | Not advancing mid after 2-swap  | The value swapped in from high is unexamined; advancing mid skips it.  |
 * | In-place, no extra structures   | Mutate via swaps, do not build a new sorted copy.                      |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                        | Key Idea                                   | Best Used When                     | Time       | Space               |
 * |---|---------------------------------|--------------------------------------------|------------------------------------|------------|---------------------|
 * | 1 | Library Sort                    | Call Arrays.sort(nums)                     | Relaxed constraints / baseline     | O(n log n) | O(1)-O(n) impl-dep. |
 * | 2 | Counting Sort (two-pass)        | Count 0s/1s/2s, then overwrite the array   | Two passes ok; simple & robust     | O(n)       | O(1) (3-slot count) |
 * | 3 | Dutch National Flag (one-pass) *| Three pointers partition in a single scan  | Optimal: one pass, in-place        | O(n) *     | O(1) *              |
 *
 * (* = optimal)
 *
 * All three run within the tiny n <= 300 limit, so "best" is about elegance and
 * the implied constraints: no built-in sort, one pass, constant space. Counting
 * Sort hits O(n)/O(1) but needs TWO passes. Dutch National Flag achieves the
 * same complexity in a SINGLE pass - the canonical, expected answer. Prefer
 * Counting Sort for dead-simple code; prefer DNF when the one-pass in-place
 * solution is requested (it almost always is).
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Library Sort
 * ------------------------------------------------------------
 * 1. Call the built-in sort on the array.
 * 2. Done - the array is sorted in place.
 *
 *    import java.util.Arrays;
 *
 *    public class SortColorsLibrary {
 *        public void sortColors(int[] nums) {
 *            Arrays.sort(nums); // works, but sidesteps the point
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {2, 0, 2, 1, 1, 0};
 *            new SortColorsLibrary().sortColors(nums);
 *            System.out.println(Arrays.toString(nums)); // [0, 0, 1, 1, 2, 2]
 *        }
 *    }
 *
 * Baseline only. Usually earns "now do it without the library sort."
 *
 * ------------------------------------------------------------
 * Approach 2: Counting Sort (Two-Pass)
 * ------------------------------------------------------------
 * 1. Create a size-3 counter array.
 * 2. Pass 1: increment count[value] for each element.
 * 3. Pass 2: overwrite nums - count[0] zeros, then count[1] ones, then count[2] twos.
 *
 *    import java.util.Arrays;
 *
 *    public class SortColorsCounting {
 *        public void sortColors(int[] nums) {
 *            int[] count = new int[3];        // count[0], count[1], count[2]
 *
 *            for (int value : nums) {         // Pass 1: tally each color
 *                count[value]++;
 *            }
 *
 *            int index = 0;                   // Pass 2: rewrite the array
 *            for (int color = 0; color < 3; color++) {
 *                for (int k = 0; k < count[color]; k++) {
 *                    nums[index++] = color;
 *                }
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {2, 0, 2, 1, 1, 0};
 *            new SortColorsCounting().sortColors(nums);
 *            System.out.println(Arrays.toString(nums)); // [0, 0, 1, 1, 2, 2]
 *        }
 *    }
 *
 * The counter is a fixed 3 slots regardless of n, so auxiliary space is O(1).
 * Only downside vs optimal: it touches the data twice.
 *
 * ------------------------------------------------------------
 * Approach 3: Dutch National Flag (One-Pass)  [OPTIMAL]
 * ------------------------------------------------------------
 * 1. Initialize low = 0, mid = 0, high = n - 1.
 *      [0 .. low-1]   finalized 0s
 *      [low .. mid-1] finalized 1s
 *      [mid .. high]  unexplored
 *      [high+1 .. n-1] finalized 2s
 * 2. While mid <= high, examine nums[mid]:
 *      - 0: swap(low, mid), advance BOTH low and mid.
 *      - 1: already in place, advance only mid.
 *      - 2: swap(mid, high), decrement high ONLY (do NOT advance mid).
 * 3. Stop when mid passes high.
 *
 *    import java.util.Arrays;
 *
 *    public class SortColorsDNF {
 *        public void sortColors(int[] nums) {
 *            int low = 0, mid = 0, high = nums.length - 1;
 *
 *            while (mid <= high) {
 *                switch (nums[mid]) {
 *                    case 0:                       // belongs on the left
 *                        swap(nums, low, mid);
 *                        low++;
 *                        mid++;
 *                        break;
 *                    case 1:                       // already centered
 *                        mid++;
 *                        break;
 *                    case 2:                       // belongs on the right
 *                        swap(nums, mid, high);
 *                        high--;
 *                        // note: mid does NOT advance here
 *                        break;
 *                }
 *            }
 *        }
 *
 *        private void swap(int[] nums, int i, int j) {
 *            int temp = nums[i];
 *            nums[i] = nums[j];
 *            nums[j] = temp;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {2, 0, 2, 1, 1, 0};
 *            new SortColorsDNF().sortColors(nums);
 *            System.out.println(Arrays.toString(nums)); // [0, 0, 1, 1, 2, 2]
 *        }
 *    }
 *
 * Why mid does NOT advance on a 2: the element pulled from high is unexamined -
 * it could be a 0 that still needs to travel left. Keeping mid put re-examines it.
 * Why mid DOES advance on a 0: the element pulled from low comes from the region
 * [low .. mid-1], which only held already-scanned 1s, so it needs no re-inspection.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Library Sort
 * ------------------------------------------------------------
 * Time:  O(n log n). For n = 300, ~ 300 * 8.2 ~ 2,470 comparisons.
 * Space: Implementation-dependent (O(log n) to O(n)).
 *
 * ------------------------------------------------------------
 * Approach 2: Counting Sort
 * ------------------------------------------------------------
 * Time:  O(n). Pass 1 = n increments, Pass 2 = n writes -> ~2n. n=300 -> ~600 ops.
 * Space: O(1). Counter is always 3 integers.
 *
 * ------------------------------------------------------------
 * Approach 3: Dutch National Flag  [OPTIMAL]
 * ------------------------------------------------------------
 * Time:  O(n). Each iteration advances mid or decrements high; the gap
 *        (high - mid + 1) shrinks by >= 1 each step, so at most n iterations.
 *        n=300 -> at most 300 iterations of O(1) work.
 * Space: O(1). Three integer pointers plus a swap temp.
 *
 * Concrete at n=6: DNF finishes in 6 iterations with a few swaps; Counting Sort
 * does 6 + 6 = 12 array touches; library sort adds a log-factor (negligible here).
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Library Sort - nums = [2, 0, 2, 1, 1, 0]
 * ------------------------------------------------------------
 *    Arrays.sort -> [0, 0, 1, 1, 2, 2]
 *
 * ------------------------------------------------------------
 * Approach 2: Counting Sort - nums = [2, 0, 2, 1, 1, 0]
 * ------------------------------------------------------------
 * Pass 1 - build counts:
 * | Element seen | count[0] | count[1] | count[2] |
 * |--------------|----------|----------|----------|
 * | 2            | 0        | 0        | 1        |
 * | 0            | 1        | 0        | 1        |
 * | 2            | 1        | 0        | 2        |
 * | 1            | 1        | 1        | 2        |
 * | 1            | 1        | 2        | 2        |
 * | 0            | 2        | 2        | 2        |
 *
 * Final counts: count = [2, 2, 2]
 *
 * Pass 2 - rewrite:
 *    write two 0s -> [0, 0, _, _, _, _]
 *    write two 1s -> [0, 0, 1, 1, _, _]
 *    write two 2s -> [0, 0, 1, 1, 2, 2]
 * Final: [0, 0, 1, 1, 2, 2]
 *
 * ------------------------------------------------------------
 * Approach 3: Dutch National Flag - nums = [2, 0, 2, 1, 1, 0]
 * ------------------------------------------------------------
 * Start: low=0, mid=0, high=5. [..] marks the value at mid.
 *
 *    Step 0: [2] 0  2  1  1  0     low=0 mid=0 high=5
 *            nums[mid]=2 -> swap(mid,high), high--
 *    Step 1:  0  0  2  1  1  2     low=0 mid=0 high=4
 *            (mid stays) nums[mid]=0 -> swap(low,mid), low++, mid++
 *    Step 2:  0 [0] 2  1  1  2     low=1 mid=1 high=4
 *            nums[mid]=0 -> swap(low,mid) self-swap, low++, mid++
 *    Step 3:  0  0 [2] 1  1  2     low=2 mid=2 high=4
 *            nums[mid]=2 -> swap(mid,high), high--
 *    Step 4:  0  0  1  1  2  2     low=2 mid=2 high=3
 *            (mid stays) nums[mid]=1 -> mid++
 *    Step 5:  0  0  1 [1] 2  2     low=2 mid=3 high=3
 *            nums[mid]=1 -> mid++
 *    Step 6:  mid=4 > high=3 -> STOP
 *
 * Final: [0, 0, 1, 1, 2, 2]
 *
 * Pointer trace summary:
 * | Step | Action                | low   | mid   | high  | Array           |
 * |------|-----------------------|-------|-------|-------|-----------------|
 * | 0    | see 2, swap mid<->high| 0     | 0     | 5->4  | [0,0,2,1,1,2]   |
 * | 1    | see 0, swap low<->mid | 0->1  | 0->1  | 4     | [0,0,2,1,1,2]   |
 * | 2    | see 0, self-swap      | 1->2  | 1->2  | 4     | [0,0,2,1,1,2]   |
 * | 3    | see 2, swap mid<->high| 2     | 2     | 4->3  | [0,0,1,1,2,2]   |
 * | 4    | see 1, advance mid    | 2     | 2->3  | 3     | [0,0,1,1,2,2]   |
 * | 5    | see 1, advance mid    | 2     | 3->4  | 3     | [0,0,1,1,2,2]   |
 * | 6    | mid > high, stop      | -     | -     | -     | [0,0,1,1,2,2]   |
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case         | Input          | Expected Output | How Handled                                             |
 * |-------------------|----------------|-----------------|--------------------------------------------------------|
 * | Single element    | [0]            | [0]             | low=mid=high=0; one iteration classifies it, loop exits.|
 * | All same value    | [1, 1, 1]      | [1, 1, 1]       | Every element is a 1; mid walks to the end, no swaps.   |
 * | Already sorted    | [0, 1, 2]      | [0, 1, 2]       | Each value handled by its case; minimal swaps.          |
 * | Reverse sorted    | [2, 1, 0]      | [0, 1, 2]       | 2 swaps right, 0 swaps left, 1 centers.                 |
 * | Only two colors   | [2, 0, 0, 2]   | [0, 0, 2, 2]    | Absent 1s never trigger the case-1 branch.              |
 * | Two elements      | [2, 0]         | [0, 2]          | First 2 swaps to high, then 0 handled; loop exits.      |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 - Advancing mid after a 2-swap:
 *    // WRONG: skips the unexamined value swapped in from high
 *    case 2:
 *        swap(nums, mid, high);
 *        high--;
 *        mid++;          // BUG - may leave a 0 stranded on the right
 *        break;
 *    // CORRECT: leave mid put so the new value gets inspected
 *    case 2:
 *        swap(nums, mid, high);
 *        high--;         // mid unchanged
 *        break;
 *
 * Pitfall 2 - Wrong loop condition:
 *    while (mid < high)   // misses the final element at mid == high
 *    while (mid <= high)  // processes the boundary element
 *
 * Pitfall 3 - Not advancing mid after a 0-swap: the value from low is a known 1,
 * so you MUST advance mid (and low); forgetting causes an infinite loop or
 * misplacement.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: Missing color (e.g., no 1s), single-element arrays, and the mid == high
 *    boundary iteration. All covered: branches are value-driven and the loop
 *    condition is inclusive. Empty input isn't in constraints (n >= 1) but is
 *    handled too - high = -1 makes mid <= high false immediately.
 *
 * Q: Are there any type mismatches?
 * A: No. Everything is int; array is int[]; indices max at 299 - no overflow,
 *    no boxing, no narrowing.
 *
 * Q: How can I verify this works right now?
 *    import java.util.Arrays;
 *
 *    public class SortColorsVerify {
 *        public void sortColors(int[] nums) {
 *            int low = 0, mid = 0, high = nums.length - 1;
 *            while (mid <= high) {
 *                if (nums[mid] == 0)      { swap(nums, low++, mid++); }
 *                else if (nums[mid] == 1) { mid++; }
 *                else                     { swap(nums, mid, high--); }
 *            }
 *        }
 *        private void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }
 *
 *        public static void main(String[] args) {
 *            SortColorsVerify s = new SortColorsVerify();
 *            int[] a = {2, 0, 2, 1, 1, 0}; s.sortColors(a);
 *            assert Arrays.equals(a, new int[]{0, 0, 1, 1, 2, 2}) : "mixed failed";
 *            int[] b = {1}; s.sortColors(b);
 *            assert Arrays.equals(b, new int[]{1}) : "single failed";
 *            int[] c = {2, 1, 0}; s.sortColors(c);
 *            assert Arrays.equals(c, new int[]{0, 1, 2}) : "reverse failed";
 *            int[] d = {2, 0, 0, 2}; s.sortColors(d);
 *            assert Arrays.equals(d, new int[]{0, 0, 2, 2}) : "two-color failed";
 *            System.out.println("All tests passed."); // run with: java -ea
 *        }
 *    }
 *
 * | Approach            | Risk                                    | Mitigation                                    |
 * |---------------------|-----------------------------------------|-----------------------------------------------|
 * | Library Sort        | Sidesteps intended skill; may be barred | Use as sanity baseline, then implement DNF    |
 * | Counting Sort       | Two passes; miscount if values outside  | Trust the constraint; add a guard if untrusted|
 * | Dutch National Flag | Pointer-advance bugs (esp. 2-case)      | Advance mid on 0 and 1; hold mid on 2         |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * LeetCode #75 - Sort Colors - Difficulty: Medium. A perennial interview
 * favorite with thousands of reported appearances; the Dutch National Flag
 * pattern also appears as the partition step in quicksort variants.
 *
 * | Company           | Frequency (stars) | Notes                                        |
 * |-------------------|-------------------|----------------------------------------------|
 * | Amazon            | *****             | Extremely common phone-screen array question |
 * | Microsoft         | *****             | Often paired with a one-pass follow-up        |
 * | Facebook (Meta)   | ****              | Tests in-place partitioning fluency           |
 * | Google            | ****              | Often asked to generalize to k colors         |
 * | Apple             | ***               | Appears in early-round screens                |
 * | Bloomberg         | ****              | Popular for its clean pointer logic           |
 * | Adobe             | ***               | Common warm-up problem                        |
 * | Nvidia            | ***               | Paired with counting-sort discussion          |
 * | Uber              | ***               | Shows up in array/partition rounds            |
 * | Oracle            | **                | Less frequent but seen                        |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach                       | Time       | Space     | Code Complexity | Recommended?                          |
 * |--------------------------------|------------|-----------|-----------------|---------------------------------------|
 * | Library Sort                   | O(n log n) | O(1)-O(n) | Trivial         | NO - sidesteps the problem; baseline  |
 * | Counting Sort (two-pass)       | O(n)       | O(1)      | Low             | YES - great when simplicity matters   |
 * | Dutch National Flag (one-pass) | O(n)       | O(1)      | Moderate        | BEST - one pass, in-place, expected   |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use the Dutch National Flag three-pointer algorithm: single pass, in place,
 * constant space - exactly the implied constraints. If you value bulletproof
 * simplicity over a single pass, Counting Sort is an equally optimal-complexity
 * fallback (just two passes).
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * This is a THREE-WAY PARTITION, not a sort - exploit that there are only three
 * distinct values. Keep three pointers low, mid, high; advance mid on 0 and 1,
 * but HOLD mid when you swap a 2 to the back, because the value pulled in from
 * high hasn't been inspected yet. That one rule is the entire problem.
 */
// @formatter:on
