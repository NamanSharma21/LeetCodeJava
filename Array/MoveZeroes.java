package Array;

import java.util.Arrays;

public class MoveZeroes {
    public static void main(String[] args) {
        MoveZeroes moveZeroes = new MoveZeroes();
        moveZeroes.moveZeroes(new int[] { 0, 1, 0, 3, 12 });
        moveZeroes.moveZeroes(new int[] { 1, 0 });
        System.out.println("----------------------------");
        moveZeroes.moveZeroesTwoPassOverwrite(new int[] { 0, 1, 0, 3, 12 });
        moveZeroes.moveZeroesTwoPassOverwrite(new int[] { 1, 0 });
        System.out.println("----------------------------");
        moveZeroes.moveZeroesOnePassSwap(new int[] { 0, 1, 0, 3, 12 });
        moveZeroes.moveZeroesOnePassSwap(new int[] { 1, 0 });
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/move-zeroes/description/
     * Given an integer array nums, move all 0's to the end of it while maintaining
     * the relative order of the non-zero elements.
     * 
     * Note that you must do this in-place without making a copy of the array.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [0,1,0,3,12]
     * Output: [1,3,12,0,0]
     * Example 2:
     * 
     * Input: nums = [0]
     * Output: [0]
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 104
     * -231 <= nums[i] <= 231 - 1
     * 
     * 
     * Follow up: Could you minimize the total number of operations done?
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach            | Time | Space | Code Complexity | Recommended?                 |
     * |---------------------|------|-------|-----------------|------------------------------|
     * | Extra Array         | O(n) | O(n)  | Low             | X Violates in-place          |
     * | Two-Pass Overwrite  | O(n) | O(1)  | Low             | OK Acceptable, readable      |
     * | One-Pass Swap       | O(n) | O(1)  | Low             | BEST - minimizes writes      |
     * 
     * @param nums
     */
    // @formatter:on
    public void moveZeroes(int[] nums) {
        System.out.println("Before : " + Arrays.toString(nums));
        int arrayLenght = nums.length;
        if (arrayLenght == 1) {
            return;
        }
        int zeroLength = 0;
        for (int i = 0; i < arrayLenght; i++) {
            if (nums[i] == 0) {
                zeroLength++;
            } else if (zeroLength > 0) {
                int temp = nums[i];
                nums[i] = 0;
                nums[i - zeroLength] = temp;
            }
        }
        System.out.println("After : " + Arrays.toString(nums));
    }

    // @formatter:off
    /**
     * 
     * | Approach            | Time | Space | Code Complexity | Recommended?                 |
     * |---------------------|------|-------|-----------------|------------------------------|
     * | Two-Pass Overwrite  | O(n) | O(1)  | Low             | OK Acceptable, readable      |
     * 
     * @param nums
     */
    // @formatter:on
    public void moveZeroesTwoPassOverwrite(int[] nums) {
        System.out.println("Before : " + Arrays.toString(nums));
        int insertPos = 0;
        for (int num : nums) {
            if (num != 0)
                nums[insertPos++] = num;
        }

        while (insertPos < nums.length) {
            nums[insertPos++] = 0;
        }
        System.out.println("After : " + Arrays.toString(nums));
    }

    // @formatter:off
    /**
     * 
     * | Approach            | Time | Space | Code Complexity | Recommended?                 |
     * |---------------------|------|-------|-----------------|------------------------------|
     * | One-Pass Swap       | O(n) | O(1)  | Low             | BEST - minimizes writes      |
     * 
     * @param nums
     */
    // @formatter:on
    public void moveZeroesOnePassSwap(int[] nums) {
        System.out.println("Before : " + Arrays.toString(nums));
        int insertPos = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                if (i != insertPos) {
                    int temp = nums[insertPos];
                    nums[insertPos] = nums[i];
                    nums[i] = temp;
                }
                insertPos++;
            }
        }
        System.out.println("After : " + Arrays.toString(nums));
    }
}

// @formatter:off
/*
 * ============================================================
 * MOVE ZEROES - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an integer array, move every 0 to the END of the array while keeping
 * the RELATIVE ORDER of all non-zero elements unchanged. Must be done IN PLACE
 * (no second array as primary storage).
 * This is LeetCode #283, difficulty Easy.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums - an array of integers (zeros, positives, negatives).
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * void. The array nums is mutated in place: non-zeros keep order, zeros pushed
 * to the tail.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * 1 <= nums.length <= 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 * Follow-up: minimize the total number of write operations.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Rearrange nums so that: (1) non-zeros keep original relative order,
 * (2) all zeros come after all non-zeros, (3) in-place, O(1) aux space ideally.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  [0, 1, 0, 3, 12]
 *    Output: [1, 3, 12, 0, 0]
 * Non-zeros 1, 3, 12 keep their order; the two zeros go to the back.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Tidying a bookshelf with empty gaps (zeros). Pack all real books (non-zeros)
 * tightly on the left in the same order, collapse gaps to the right. Sweep
 * left to right; each real book slides into the next available packed slot.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Keep a pointer insertPos for where the next non-zero should land (start 0).
 * 2. Scan the array with a reading pointer.
 * 3. On each non-zero, place it at insertPos, then advance insertPos.
 * 4. After the scan, indices from insertPos to end must be filled with zeros.
 * This is the classic STABLE PARTITION pattern.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                     | Why it's tricky                                        |
 * |-------------------------------|--------------------------------------------------------|
 * | In-place requirement          | Overwrite while reading without clobbering needed data |
 * | Preserving relative order     | Naive swap-with-end breaks non-zero order              |
 * | Minimizing writes (follow-up) | Only write when a swap is genuinely needed             |
 * | Not returning a value         | It's void; returning a new array is a common mistake   |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach              | Key Idea                                      | Best Used When              | Time  | Space          |
 * |---|-----------------------|-----------------------------------------------|-----------------------------|-------|----------------|
 * | 1 | Extra Array           | Copy non-zeros to new array, pad, copy back   | Learning / not in-place     | O(n)  | O(n)           |
 * | 2 | Two-Pass Overwrite    | Pass1 compact non-zeros; Pass2 fill zeros     | Simple, readable, in-place  | O(n)  | O(1) space-opt |
 * | 3 | One-Pass Swap (2 ptr) | Swap non-zero into insertPos; write if needed | Minimizing writes           | O(n)  | O(1) space-opt |
 *
 * All three are linear time. Extra Array is disqualified by in-place (O(n) space).
 * Two-Pass writes to nearly every position; One-Pass Swap only writes when a
 * non-zero needs relocating -> answers the minimize-writes follow-up. Both in-place
 * options are O(n) time and O(1) space, so prefer One-Pass Swap (Approach 3).
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Extra Array (Out-of-Place)
 * ------------------------------------------------------------
 * 1. Create result array of same length (Java zero-inits it).
 * 2. Copy each non-zero into the next free slot of result.
 * 3. Copy result back into nums.
 *
 *    import java.util.Arrays;
 *
 *    public class MoveZeroesExtraArray {
 *        public void moveZeroes(int[] nums) {
 *            int[] result = new int[nums.length]; // all zeros by default
 *            int insertPos = 0;
 *            for (int num : nums) {
 *                if (num != 0) {
 *                    result[insertPos] = num;
 *                    insertPos++;
 *                }
 *            }
 *            System.arraycopy(result, 0, nums, 0, nums.length);
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {0, 1, 0, 3, 12};
 *            new MoveZeroesExtraArray().moveZeroes(nums);
 *            System.out.println(Arrays.toString(nums)); // [1, 3, 12, 0, 0]
 *        }
 *    }
 *
 * Untouched slots of result are already 0 (Java zero-inits int[]) -> no padding.
 *
 * ------------------------------------------------------------
 * Approach 2: Two-Pass Overwrite (In-Place)
 * ------------------------------------------------------------
 * 1. insertPos = 0.
 * 2. Pass 1: for each non-zero, write nums[insertPos], increment insertPos.
 * 3. Pass 2: fill nums[insertPos..end] with 0.
 *
 *    import java.util.Arrays;
 *
 *    public class MoveZeroesTwoPass {
 *        public void moveZeroes(int[] nums) {
 *            int insertPos = 0;
 *            for (int num : nums) {          // Pass 1: compact non-zeros
 *                if (num != 0) {
 *                    nums[insertPos] = num;
 *                    insertPos++;
 *                }
 *            }
 *            while (insertPos < nums.length) { // Pass 2: zero-fill tail
 *                nums[insertPos] = 0;
 *                insertPos++;
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {0, 1, 0, 3, 12};
 *            new MoveZeroesTwoPass().moveZeroes(nums);
 *            System.out.println(Arrays.toString(nums)); // [1, 3, 12, 0, 0]
 *        }
 *    }
 *
 * Since insertPos <= readIndex always holds, overwriting never destroys an
 * unread non-zero.
 *
 * ------------------------------------------------------------
 * Approach 3: One-Pass Swap (Two Pointers) - Optimal
 * ------------------------------------------------------------
 * 1. insertPos = 0 marks next slot for a non-zero.
 * 2. Scan i from 0 to n-1.
 * 3. If nums[i] != 0, swap nums[i] with nums[insertPos], increment insertPos.
 * 4. Swap sends non-zero forward, carries a zero back -> order preserved.
 *
 *    import java.util.Arrays;
 *
 *    public class MoveZeroesOnePass {
 *        public void moveZeroes(int[] nums) {
 *            int insertPos = 0;
 *            for (int i = 0; i < nums.length; i++) {
 *                if (nums[i] != 0) {
 *                    if (i != insertPos) { // only swap when needed
 *                        int temp = nums[insertPos];
 *                        nums[insertPos] = nums[i];
 *                        nums[i] = temp;
 *                    }
 *                    insertPos++;
 *                }
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {0, 1, 0, 3, 12};
 *            new MoveZeroesOnePass().moveZeroes(nums);
 *            System.out.println(Arrays.toString(nums)); // [1, 3, 12, 0, 0]
 *        }
 *    }
 *
 * The (i != insertPos) guard minimizes writes: when no zeros precede i,
 * i == insertPos and the swap is skipped -> zero unnecessary writes.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Extra Array
 * ------------------------------------------------------------
 * Time: O(n) - fill result (n) + arraycopy back (n) = ~2n.
 * Space: O(n) - auxiliary result array of size n.
 * Example: n = 10^4 -> ~2 x 10^4 ops, ~40 KB extra.
 *
 * ------------------------------------------------------------
 * Approach 2: Two-Pass Overwrite
 * ------------------------------------------------------------
 * Time: O(n) - Pass 1 touches n; Pass 2 touches <= n trailing positions.
 * Space: O(1) - only insertPos.
 * Example: n=5, [0,1,0,3,12] -> 5 reads + 3 non-zero writes + 2 zero writes = 10 ops.
 *
 * ------------------------------------------------------------
 * Approach 3: One-Pass Swap
 * ------------------------------------------------------------
 * Time: O(n) - single scan, each swap O(1); swaps <= number of non-zeros.
 * Space: O(1) - insertPos + one temp.
 * Example: [0,1,0,3,12] -> 5 reads, only 3 swaps. On [1,2,3] the guard yields 0 swaps.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 - Extra Array on [0, 1, 0, 3, 12]
 * ------------------------------------------------------------
 *    result = [0,0,0,0,0], insertPos = 0
 *    |- num=0  -> skip
 *    |- num=1  -> result[0]=1, insertPos=1   -> [1,0,0,0,0]
 *    |- num=0  -> skip
 *    |- num=3  -> result[1]=3, insertPos=2   -> [1,3,0,0,0]
 *    +- num=12 -> result[2]=12, insertPos=3  -> [1,3,12,0,0]
 *    Copy back -> nums = [1, 3, 12, 0, 0]
 *
 * ------------------------------------------------------------
 * Approach 2 - Two-Pass on [0, 1, 0, 3, 12]
 * ------------------------------------------------------------
 *    Pass 1 (compact): insertPos = 0
 *    |- 0  -> skip
 *    |- 1  -> nums[0]=1, insertPos=1  -> [1,1,0,3,12]
 *    |- 0  -> skip
 *    |- 3  -> nums[1]=3, insertPos=2  -> [1,3,0,3,12]
 *    +- 12 -> nums[2]=12, insertPos=3 -> [1,3,12,3,12]
 *    Pass 2 (zero-fill from index 3):
 *    |- nums[3]=0 -> [1,3,12,0,12]
 *    +- nums[4]=0 -> [1,3,12,0,0]
 *
 * ------------------------------------------------------------
 * Approach 3 - One-Pass Swap on [0, 1, 0, 3, 12]
 * ------------------------------------------------------------
 * | i | nums[i] | insertPos | Action                    | Array after         |
 * |---|---------|-----------|---------------------------|---------------------|
 * | 0 | 0       | 0         | zero -> skip              | [0,1,0,3,12]        |
 * | 1 | 1       | 0         | swap(1,0)                 | [1,0,0,3,12] pos->1 |
 * | 2 | 0       | 1         | zero -> skip              | [1,0,0,3,12]        |
 * | 3 | 3       | 1         | swap(3,1)                 | [1,3,0,0,12] pos->2 |
 * | 4 | 12      | 2         | swap(4,2)                 | [1,3,12,0,0] pos->3 |
 * Final: [1, 3, 12, 0, 0]
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                | Input          | Expected Output | How Handled                                  |
 * |--------------------------|----------------|-----------------|----------------------------------------------|
 * | Single element, non-zero | [5]            | [5]             | i == insertPos guard skips swap              |
 * | Single element, zero     | [0]            | [0]             | Zero skipped; insertPos stays 0              |
 * | No zeros at all          | [1,2,3]        | [1,2,3]         | i == insertPos throughout -> zero writes     |
 * | All zeros                | [0,0,0]        | [0,0,0]         | No non-zeros; array untouched                |
 * | Zeros already at end     | [1,2,0,0]      | [1,2,0,0]       | Non-zeros land in place; zeros stay          |
 * | Leading zeros            | [0,0,1]        | [1,0,0]         | 1 swaps to index 0; tail becomes zeros       |
 * | Negative values          | [0,-1,0,-2]    | [-1,-2,0,0]     | Only == 0 is a zero; negatives preserved     |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 - Returning a new array instead of mutating in place
 *    WRONG:   public int[] moveZeroes(int[] nums) { ... return result; }
 *    CORRECT: public void moveZeroes(int[] nums) { ... }
 *
 * Pitfall 2 - Naive swap-to-end that breaks order
 *    WRONG:   if (nums[i] == 0) swap(nums, i, --last); // order NOT preserved
 *    CORRECT: advance a single insertPos (Approach 3)
 *
 * Pitfall 3 - Forgetting the second pass in the overwrite method
 *    WRONG:   for (int num : nums) if (num != 0) nums[insertPos++] = num; // stale tail
 *    CORRECT: add Pass 2 to zero-fill from insertPos to end
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: All-zeros (leave unchanged), no-zeros (zero swaps via guard), single-element.
 *    Negatives are safe because the test is strictly == 0, not <= 0. Two-pass must
 *    not forget Pass 2 or the tail keeps stale duplicates.
 *
 * Q: Are there any type mismatches?
 * A: No. All int. Values fit in int; never summed/multiplied -> no overflow.
 *    Method returns void, matching the in-place contract.
 *
 * Q: How can I verify this works right now?
 *
 *    import java.util.Arrays;
 *
 *    public class MoveZeroesVerify {
 *        public void moveZeroes(int[] nums) {
 *            int insertPos = 0;
 *            for (int i = 0; i < nums.length; i++) {
 *                if (nums[i] != 0) {
 *                    if (i != insertPos) {
 *                        int temp = nums[insertPos];
 *                        nums[insertPos] = nums[i];
 *                        nums[i] = temp;
 *                    }
 *                    insertPos++;
 *                }
 *            }
 *        }
 *
 *        public void verify() {
 *            int[] a = {0, 1, 0, 3, 12};
 *            moveZeroes(a);
 *            assert Arrays.equals(a, new int[]{1, 3, 12, 0, 0}) : "basic case failed";
 *
 *            int[] b = {0, 0, 0};
 *            moveZeroes(b);
 *            assert Arrays.equals(b, new int[]{0, 0, 0}) : "all zeros failed";
 *
 *            int[] c = {1, 2, 3};
 *            moveZeroes(c);
 *            assert Arrays.equals(c, new int[]{1, 2, 3}) : "no zeros failed";
 *
 *            int[] d = {0};
 *            moveZeroes(d);
 *            assert Arrays.equals(d, new int[]{0}) : "single zero failed";
 *
 *            int[] e = {0, -1, 0, -2};
 *            moveZeroes(e);
 *            assert Arrays.equals(e, new int[]{-1, -2, 0, 0}) : "negatives failed";
 *
 *            System.out.println("All tests passed!");
 *        }
 *
 *        public static void main(String[] args) {
 *            // Run with: java -ea MoveZeroesVerify   (-ea enables assertions)
 *            new MoveZeroesVerify().verify();
 *        }
 *    }
 *
 * | Approach       | Risk                                      | Mitigation                              |
 * |----------------|-------------------------------------------|-----------------------------------------|
 * | Extra Array    | Violates in-place; extra O(n) memory      | Use only for learning                   |
 * | Two-Pass       | Forgetting Pass 2 leaves stale tail       | Always zero-fill from insertPos to end  |
 * | One-Pass Swap  | Missing i != insertPos causes self-swaps  | Keep the guard to minimize writes       |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #283. Difficulty: Easy. Very high interview frequency.
 *
 * | Company           | Frequency | Notes                                             |
 * |-------------------|-----------|---------------------------------------------------|
 * | Facebook / Meta   | *****     | Common phone-screen warm-up; asks minimize-writes |
 * | Amazon            | *****     | Frequent OA and interview appearance              |
 * | Google            | ****      | Two-pointer fundamentals check                    |
 * | Microsoft         | ****      | Common in early rounds                            |
 * | Apple             | ***       | Array-manipulation sets                           |
 * | Bloomberg         | ***       | Popular for in-place array reasoning              |
 * | Adobe             | ***       | Screening rounds                                  |
 * | Yahoo             | **        | Occasional appearance                             |
 * | Uber              | **        | Two-pointer warm-up                               |
 * | TikTok/ByteDance  | ***       | Frequently asked in OA                            |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach            | Time | Space | Code Complexity | Recommended?                 |
 * |---------------------|------|-------|-----------------|------------------------------|
 * | Extra Array         | O(n) | O(n)  | Low             | X Violates in-place          |
 * | Two-Pass Overwrite  | O(n) | O(1)  | Low             | OK Acceptable, readable      |
 * | One-Pass Swap       | O(n) | O(1)  | Low             | BEST - minimizes writes      |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use One-Pass Swap (Approach 3). O(n) time, O(1) space, and the i != insertPos
 * guard minimizes writes -> answers the follow-up. It also has the best space
 * profile, so no time-vs-space trade-off: it's the single best choice.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * This is the STABLE PARTITION / TWO-POINTER pattern: keep an insertPos pointer
 * for "where the next kept element goes," and swap/write kept elements forward as
 * you scan. Key gotcha: preserve RELATIVE ORDER (rules out swap-with-end tricks);
 * follow-up gotcha: MINIMIZE WRITES (guard against swapping an element with itself).
 */
// @formatter:on
