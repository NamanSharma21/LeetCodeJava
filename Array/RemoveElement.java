package Array;

public class RemoveElement {
    public static void main(String[] args) {
        RemoveElement removeElement = new RemoveElement();
        System.out.println("Remove Element : " + removeElement.removeElementArrayCopy(new int[] { 3, 2, 2, 3 }, 3));
        System.out.println(
                "Remove Element : " + removeElement.removeElementArrayCopy(new int[] { 0, 1, 2, 2, 3, 0, 4, 2 }, 2));
        System.out.println("-------------------");
        System.out.println(
                "Remove Element : " + removeElement.removeElementFastSlowPointers(new int[] { 3, 2, 2, 3 }, 3));
        System.out.println("Remove Element : "
                + removeElement.removeElementFastSlowPointers(new int[] { 0, 1, 2, 2, 3, 0, 4, 2 }, 2));
        System.out.println("-------------------");
        System.out.println("Remove Element : " + removeElement.removeElementSwapFromEnd(new int[] { 3, 2, 2, 3 }, 3));
        System.out.println(
                "Remove Element : " + removeElement.removeElementSwapFromEnd(new int[] { 0, 1, 2, 2, 3, 0, 4, 2 }, 2));
    }

    // @formatter:off
    /*
     * 
     * https://leetcode.com/problems/remove-element/description/?envType=problem-list-v2&envId=array
     * 
     * 
     * Given an integer array nums and an integer val, remove all occurrences of val
     * in nums in-place. The order of the elements may be changed. Then return the
     * number of elements in nums which are not equal to val.
     * 
     * Consider the number of elements in nums which are not equal to val be k, to
     * get accepted, you need to do the following things:
     * 
     * Change the array nums such that the first k elements of nums contain the
     * elements which are not equal to val. The remaining elements of nums are not
     * important as well as the size of nums.
     * Return k.
     * Custom Judge:
     * 
     * The judge will test your solution with the following code:
     * 
     * int[] nums = [...]; // Input array
     * int val = ...; // Value to remove
     * int[] expectedNums = [...]; // The expected answer with correct length.
     * // It is sorted with no values equaling val.
     * 
     * int k = removeElement(nums, val); // Calls your implementation
     * 
     * assert k == expectedNums.length;
     * sort(nums, 0, k); // Sort the first k elements of nums
     * for (int i = 0; i < actualLength; i++) {
     * assert nums[i] == expectedNums[i];
     * }
     * If all assertions pass, then your solution will be accepted.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [3,2,2,3], val = 3
     * Output: 2, nums = [2,2,_,_]
     * Explanation: Your function should return k = 2, with the first two elements
     * of nums being 2.
     * It does not matter what you leave beyond the returned k (hence they are
     * underscores).
     * Example 2:
     * 
     * Input: nums = [0,1,2,2,3,0,4,2], val = 2
     * Output: 5, nums = [0,1,4,0,3,_,_,_]
     * Explanation: Your function should return k = 5, with the first five elements
     * of nums containing 0, 0, 1, 3, and 4.
     * Note that the five elements can be returned in any order.
     * It does not matter what you leave beyond the returned k (hence they are
     * underscores).
     * 
     * 
     * Constraints:
     * 
     * 0 <= nums.length <= 100
     * 0 <= nums[i] <= 50
     * 0 <= val <= 100
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     *  | Approach          | Time | Space | Code Complexity | Recommended?                          |
     *  |-------------------|------|-------|-----------------|---------------------------------------|
     *  | Extra-array copy  | O(n) | O(n)  | Low             | ❌ Wastes memory; baseline only        |
     * 
     * @param nums
     * @param val
     * @return
     */
    // @formatter:on
    public int removeElementArrayCopy(int[] nums, int val) {
        int n = nums.length;
        int k = 0;
        int[] temp = new int[n];
        for (int i = 0; i < n; i++) {
            if (nums[i] != val) {
                temp[k] = nums[i];
                k++;
            }
        }
        for (int i = 0; i < n; i++) {
            nums[i] = temp[i];
        }
        return k;
    }

    // @formatter:off
    /**
     * 
     *  | Approach          | Time | Space | Code Complexity | Recommended?                          |
     *  |-------------------|------|-------|-----------------|---------------------------------------|
     *  | Fast/slow pointers| O(n) | O(1)  | Low             | ✅✅ Best overall (time + space + order)|
     * 
     * @param nums
     * @param val
     * @return
     */
    // @formatter:on
    public int removeElementFastSlowPointers(int[] nums, int val) {
        int n = nums.length;
        int slow = 0;
        for (int fast = 0; fast < n; fast++) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow;
    }

    // @formatter:off
    /**
     * 
     *  | Approach          | Time | Space | Code Complexity | Recommended?                          |
     *  |-------------------|------|-------|-----------------|---------------------------------------|
     *  | Swap from end     | O(n) | O(1)  | Medium          | ✅ Best when writes must be minimized   |
     * 
     * @param nums
     * @param val
     * @return
     */
    // @formatter:on
    public int removeElementSwapFromEnd(int[] nums, int val) {
        int i = 0;
        int n = nums.length;
        while (i < n) {
            if (nums[i] == val) {
                nums[i] = nums[n - 1];
                n--;
            } else {
                i++;
            }
        }
        return n;
    }
}
// @formatter:off
/*
 * ============================================================
 * REMOVE ELEMENT — DEEP DIVE EXPLANATION
 * ============================================================
 * LeetCode #27 · Difficulty: Easy
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an integer array `nums` and an integer `val`, remove ALL
 * occurrences of `val` from `nums` IN-PLACE (no new result array),
 * and return the count `k` of elements not equal to `val`. Element
 * order may change, and only the first `k` slots of `nums` matter —
 * anything beyond index `k` is ignored by the grader.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 *  - int[] nums : the array to filter (may be empty)
 *  - int   val  : the value to strip out
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 *  - int k : number of remaining elements. The first k positions of
 *            nums must hold those surviving elements (any order).
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 *  | Constraint            | Value |
 *  |-----------------------|-------|
 *  | 0 <= nums.length      | <= 100|
 *  | 0 <= nums[i]          | <= 50 |
 *  | 0 <= val              | <= 100|
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Two coupled outputs: (1) integer count k, and (2) a MUTATED nums
 * whose first k entries are exactly the elements != val. Both are
 * graded — right number but untouched array = fail.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  nums = [3, 2, 2, 3], val = 3
 *    Output: 2, nums = [2, 2, _, _]   (_ = don't care)
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Treat the array as a shelf. Walk left to right. Each time you find
 * a "keeper" (element != val), place it at the next open slot on the
 * left. A separate pointer tracks that open slot. Elements == val are
 * skipped and never placed.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 *  1. Write pointer k starts at 0 — marks where the next keeper goes.
 *  2. Read pointer i scans every element once.
 *  3. If nums[i] != val -> keeper -> write nums[k] = nums[i], k++.
 *  4. If nums[i] == val -> do nothing, let i move on.
 *  5. When the scan ends, k is both the count and the valid boundary.
 * The gap between i and k equals the number of vals seen so far.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 *  | Challenge                    | Why it's tricky |
 *  |------------------------------|-----------------|
 *  | "In-place" requirement       | No fresh array; overwrite input with O(1) extra memory. |
 *  | Return value vs array state  | Correct k isn't enough — front of nums must hold keepers. |
 *  | Order-not-preserved loophole | Reordering is allowed, unlocking a swap trick when val is rare. |
 *  | Two-pointer bookkeeping      | Advancing k on a non-keeper silently corrupts the result. |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *  | # | Approach                 | Key Idea                                   | Best Used When                         | Time      | Space        |
 *  |---|--------------------------|--------------------------------------------|----------------------------------------|-----------|--------------|
 *  | 1 | Extra-array copy         | Copy keepers to a new array, then copy back| Baseline only (violates in-place)      | O(n)      | O(n)         |
 *  | 2 | Two Pointers (fast/slow) | Read with i, write keepers at k            | General case; stable, order-preserving | O(n) ✅   | O(1) ✅      |
 *  | 3 | Two Pointers (swap end)  | Swap val elements with tail, shrink n      | val is rare -> minimize writes         | O(n)      | O(1)         |
 *
 * Approach 1 wastes O(n) memory for no benefit. The fast/slow two-pointer
 * (Approach 2) dominates on BOTH axes: linear time, constant space, and it
 * preserves order. Approach 3 matches those complexities but reduces the
 * number of WRITES when val is rare, at the cost of scrambling order — a
 * constant-factor optimization, not asymptotic. Prefer Approach 2 by default;
 * use Approach 3 only when write count matters and order does not.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Extra-Array Copy (baseline)
 * ------------------------------------------------------------
 * Algorithm:
 *  1. Allocate temp of the same length.
 *  2. Scan nums; copy each keeper into temp, tracking count k.
 *  3. Copy first k elements of temp back into nums.
 *  4. Return k.
 *
 *    public class RemoveElementBaseline {
 *        public static int removeElement(int[] nums, int val) {
 *            int[] temp = new int[nums.length];
 *            int k = 0;
 *            for (int i = 0; i < nums.length; i++) {
 *                if (nums[i] != val) {
 *                    temp[k] = nums[i];  // stash the keeper
 *                    k++;
 *                }
 *            }
 *            for (int i = 0; i < k; i++) {
 *                nums[i] = temp[i];      // write keepers back to the front
 *            }
 *            return k;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {3, 2, 2, 3};
 *            int k = removeElement(nums, 3);
 *            // k = 2, nums = [2, 2]
 *        }
 *    }
 *
 * Works but allocates a second array, defeating the in-place goal.
 *
 * ------------------------------------------------------------
 * Approach 2: Two Pointers — Fast/Slow ✅ (optimal)
 * ------------------------------------------------------------
 * Algorithm:
 *  1. Initialize write pointer k = 0.
 *  2. For each read index i in 0..n-1:
 *       if nums[i] != val -> nums[k] = nums[i]; k++.
 *  3. Return k. First k entries hold all keepers in original order.
 *
 *    public class RemoveElement {
 *        public static int removeElement(int[] nums, int val) {
 *            int k = 0;                       // next slot for a keeper
 *            for (int i = 0; i < nums.length; i++) {
 *                if (nums[i] != val) {        // found a keeper
 *                    nums[k] = nums[i];       // place it at the front region
 *                    k++;
 *                }
 *            }
 *            return k;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {0, 1, 2, 2, 3, 0, 4, 2};
 *            int k = removeElement(nums, 2);
 *            // k = 5, nums = [0, 1, 3, 0, 4]
 *        }
 *    }
 *
 * When i == k (no val seen yet), nums[k] = nums[i] is a harmless
 * self-assignment. After a val is skipped, k lags i and real
 * compaction begins.
 *
 * ------------------------------------------------------------
 * Approach 3: Two Pointers — Swap From End
 * ------------------------------------------------------------
 * Algorithm:
 *  1. Set i = 0 and n = nums.length (shrinking logical length).
 *  2. While i < n:
 *       if nums[i] == val -> nums[i] = nums[n-1]; n-- (do NOT advance i).
 *       else -> i++.
 *  3. Return n.
 *
 *    public class RemoveElementSwap {
 *        public static int removeElement(int[] nums, int val) {
 *            int i = 0;
 *            int n = nums.length;             // shrinking boundary of live region
 *            while (i < n) {
 *                if (nums[i] == val) {
 *                    nums[i] = nums[n - 1];   // pull the tail element into this slot
 *                    n--;                     // shrink; don't move i — recheck this slot
 *                } else {
 *                    i++;
 *                }
 *            }
 *            return n;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {0, 1, 2, 2, 3, 0, 4, 2};
 *            int k = removeElement(nums, 2);
 *            // k = 5, order may differ, e.g. [0, 1, 4, 0, 3]
 *        }
 *    }
 *
 * Subtlety: after pulling nums[n-1] into i, RE-EXAMINE nums[i] — the
 * moved value could itself equal val — hence i is not incremented.
 * A write happens only when a val is found, so rare val -> fewer writes.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Approach 1: Extra-Array Copy
 *  - Time:  O(n) — fill temp (n) + copy back (<= n) = 2n -> O(n).
 *  - Space: O(n) — temp holds up to n elements.
 *  - Example: n = 8 -> ~13 element ops plus an 8-slot allocation.
 *
 * Approach 2: Fast/Slow Two Pointers ✅
 *  - Time:  O(n) — single pass; one compare and at most one write each.
 *  - Space: O(1) — only the integer k.
 *  - Example: n = 8, val = 2 (x3) -> 8 compares + 5 writes = 13 ops, 0 alloc.
 *             n = 100 -> <= 200 ops.
 *
 * Approach 3: Swap From End
 *  - Time:  O(n) — each iteration advances i or shrinks n; total <= n.
 *  - Space: O(1) — two integer pointers.
 *  - Example: length 8 with a SINGLE val -> only 1 write vs up to 7 in
 *             Approach 2. Rare val -> write-minimizing choice.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 — nums = [3, 2, 2, 3], val = 3
 * ------------------------------------------------------------
 *    temp = [_, _, _, _], k = 0
 *    i=0: nums[0]=3 == val -> skip
 *    i=1: nums[1]=2 != val -> temp[0]=2, k=1
 *    i=2: nums[2]=2 != val -> temp[1]=2, k=2
 *    i=3: nums[3]=3 == val -> skip
 *    copy back: nums[0]=2, nums[1]=2
 *    Result: k=2, nums=[2,2,_,_]
 *
 * ------------------------------------------------------------
 * Approach 2 — nums = [0,1,2,2,3,0,4,2], val = 2
 * ------------------------------------------------------------
 *  | i | nums[i] | keeper? | action    | k after | front (0..k-1) |
 *  |---|---------|---------|-----------|---------|----------------|
 *  | 0 | 0       | yes     | nums[0]=0 | 1       | [0]            |
 *  | 1 | 1       | yes     | nums[1]=1 | 2       | [0,1]          |
 *  | 2 | 2       | no      | skip      | 2       | [0,1]          |
 *  | 3 | 2       | no      | skip      | 2       | [0,1]          |
 *  | 4 | 3       | yes     | nums[2]=3 | 3       | [0,1,3]        |
 *  | 5 | 0       | yes     | nums[3]=0 | 4       | [0,1,3,0]      |
 *  | 6 | 4       | yes     | nums[4]=4 | 5       | [0,1,3,0,4]    |
 *  | 7 | 2       | no      | skip      | 5       | [0,1,3,0,4]    |
 *  Final: k=5, nums[0..4]=[0,1,3,0,4] — order preserved. ✅
 *
 * ------------------------------------------------------------
 * Approach 3 — nums = [0,1,2,2,3,0,4,2], val = 2
 * ------------------------------------------------------------
 *    i=0, n=8: nums[0]=0 != 2 -> i=1
 *    i=1, n=8: nums[1]=1 != 2 -> i=2
 *    i=2, n=8: nums[2]=2 == 2 -> nums[2]=nums[7]=2, n=7  (recheck i=2)
 *    i=2, n=7: nums[2]=2 == 2 -> nums[2]=nums[6]=4, n=6  (recheck i=2)
 *    i=2, n=6: nums[2]=4 != 2 -> i=3
 *    i=3, n=6: nums[3]=2 == 2 -> nums[3]=nums[5]=0, n=5  (recheck i=3)
 *    i=3, n=5: nums[3]=0 != 2 -> i=4
 *    i=4, n=5: nums[4]=3 != 2 -> i=5
 *    i=5, n=5: loop ends (i == n)
 *    Result: k=5, nums[0..4]=[0,1,4,0,3] (valid — order differs)
 *  Both keeper-sets are the multiset {0,1,3,0,4}; only arrangement differs.
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *  | Edge Case              | Input                 | Expected           | How Handled                                   |
 *  |------------------------|-----------------------|--------------------|-----------------------------------------------|
 *  | Empty array            | nums=[], val=1        | k=0                | Loop body never runs; k stays 0.              |
 *  | No element == val      | nums=[1,2,3], val=9   | k=3, unchanged     | All keepers; k reaches n via self-assignments.|
 *  | All elements == val    | nums=[7,7,7], val=7   | k=0                | No keeper written; k stays 0.                 |
 *  | val at boundaries      | nums=[2,1,2], val=2   | k=1, front=[1]     | Leading/trailing vals skipped; only 1 written.|
 *  | Single element, is val | nums=[4], val=4       | k=0                | Element skipped, k=0.                         |
 *  | Single, not val        | nums=[4], val=9       | k=1, front=[4]     | Element kept at index 0.                       |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 — advancing k on a non-keeper:
 *    // WRONG: k increments even when element == val
 *    for (int i = 0; i < nums.length; i++) {
 *        if (nums[i] != val) nums[k] = nums[i];
 *        k++;   // ❌ always increments -> count and layout both wrong
 *    }
 *    // CORRECT: k only advances when a keeper is written
 *    for (int i = 0; i < nums.length; i++) {
 *        if (nums[i] != val) { nums[k] = nums[i]; k++; }  // ✅
 *    }
 *
 * Pitfall 2 — advancing i after a swap in Approach 3:
 *    // WRONG: skips re-checking the swapped-in value
 *    if (nums[i] == val) { nums[i] = nums[n-1]; n--; i++; }  // ❌ may leave a val
 *    // CORRECT: don't move i; recheck the freshly swapped element
 *    if (nums[i] == val) { nums[i] = nums[n-1]; n--; }       // ✅
 *    else { i++; }
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 * Q: What edge cases might this miss?
 * A: Empty array (no nums[-1] indexing — guarded by i < n), all-val array
 *    (return 0), and no-val array (return full length). All handled because
 *    the pointers are driven by comparisons, never fixed offsets.
 *
 * Q: Are there any type mismatches?
 * A: No. nums is int[], val and return k are int. All int-to-int. No boxing,
 *    no overflow (values <= 50, length <= 100).
 *
 * Q: How can I verify this works right now?
 * A: Run the verify() method below (java -ea) — it asserts both the count and
 *    the multiset of survivors.
 *
 *    import java.util.Arrays;
 *
 *    public class RemoveElementVerify {
 *        public static int removeElement(int[] nums, int val) {
 *            int k = 0;
 *            for (int i = 0; i < nums.length; i++) {
 *                if (nums[i] != val) { nums[k] = nums[i]; k++; }
 *            }
 *            return k;
 *        }
 *
 *        static void check(int[] input, int val, int expectedK, int[] expectedFrontSorted) {
 *            int k = removeElement(input, val);
 *            assert k == expectedK : "k mismatch: got " + k + " want " + expectedK;
 *            int[] front = Arrays.copyOfRange(input, 0, k);
 *            Arrays.sort(front);
 *            assert Arrays.equals(front, expectedFrontSorted)
 *                    : "front mismatch: " + Arrays.toString(front);
 *        }
 *
 *        public static void verify() {
 *            check(new int[]{3,2,2,3}, 3, 2, new int[]{2,2});
 *            check(new int[]{0,1,2,2,3,0,4,2}, 2, 5, new int[]{0,0,1,3,4});
 *            check(new int[]{}, 1, 0, new int[]{});
 *            check(new int[]{7,7,7}, 7, 0, new int[]{});
 *            check(new int[]{1,2,3}, 9, 3, new int[]{1,2,3});
 *            System.out.println("All assertions passed.");
 *        }
 *
 *        public static void main(String[] args) {
 *            verify();   // run with: java -ea RemoveElementVerify
 *        }
 *    }
 *
 *  | Approach          | Risk                                    | Mitigation                                   |
 *  |-------------------|-----------------------------------------|----------------------------------------------|
 *  | Extra-array copy  | Uses O(n) memory needlessly             | Prefer in-place two-pointer; baseline only.  |
 *  | Fast/slow pointers| Advancing k unconditionally corrupts    | Increment k strictly inside the keeper branch|
 *  | Swap from end     | Advancing i after swap leaves a val     | Keep i fixed on swap branch; recheck the slot|
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #27 · Easy · ~1M+ total interview appearances (one of the
 * most-asked Easy array problems).
 *
 *  | Company        | Frequency  | Notes                                              |
 *  |----------------|------------|----------------------------------------------------|
 *  | Amazon         | ⭐⭐⭐⭐⭐ | Classic phone-screen warm-up; lead-in to harder.   |
 *  | Google         | ⭐⭐⭐⭐   | Tests in-place reasoning and clean pointers.       |
 *  | Microsoft      | ⭐⭐⭐⭐   | Common early-round filter.                          |
 *  | Meta           | ⭐⭐⭐⭐   | Paired with order-preservation follow-ups.         |
 *  | Apple          | ⭐⭐⭐     | Appears in array-manipulation rounds.              |
 *  | Bloomberg      | ⭐⭐⭐⭐   | Emphasis on in-place, memory-tight code.           |
 *  | Adobe          | ⭐⭐⭐     | Standard easy-array screen.                         |
 *  | Goldman Sachs  | ⭐⭐⭐     | Tests careful boundary handling.                    |
 *  | Uber           | ⭐⭐       | Occasional warm-up.                                 |
 *  | Oracle         | ⭐⭐       | Basic screening question.                           |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *  | Approach          | Time | Space | Code Complexity | Recommended?                          |
 *  |-------------------|------|-------|-----------------|---------------------------------------|
 *  | Extra-array copy  | O(n) | O(n)  | Low             | ❌ Wastes memory; baseline only        |
 *  | Fast/slow pointers| O(n) | O(1)  | Low             | ✅✅ Best overall (time + space + order)|
 *  | Swap from end     | O(n) | O(1)  | Medium          | ✅ Best when writes must be minimized   |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use the fast/slow two-pointer method: O(n) time, O(1) space,
 * order-preserving, simplest correct in-place solution. Switch to
 * swap-from-end only when minimizing the NUMBER of writes matters and
 * reordering is acceptable — same complexity, different write count.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * The core pattern is read/write two pointers for in-place compaction:
 * one pointer scans everything, a second marks where the next kept
 * element goes and advances ONLY on a keeper. Key gotcha: k must
 * increment strictly inside the keep-branch — and in the swap variant,
 * never advance the read pointer after pulling in a tail element,
 * because that swapped-in value still needs checking.
 */
// @formatter:on
