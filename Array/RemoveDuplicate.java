package Array;

import java.util.ArrayList;
import java.util.List;

public class RemoveDuplicate {

    public static void main(String[] args) {
        RemoveDuplicate removeDuplicate = new RemoveDuplicate();
        System.out.println(
                "RemoveDuplicate : " + removeDuplicate.removeDuplicatesAuxiliaryCollection(new int[] { 1, 1, 2 }));
        System.out.println("RemoveDuplicate : "
                + removeDuplicate.removeDuplicatesAuxiliaryCollection(new int[] { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 }));
        System.out.println("-------------------------");
        System.out.println("RemoveDuplicate : " + removeDuplicate.removeDuplicatesTwoPointers(new int[] { 1, 1, 2 }));
        System.out.println("RemoveDuplicate : "
                + removeDuplicate.removeDuplicatesTwoPointers(new int[] { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 }));
        System.out.println("-------------------------");
        System.out.println("RemoveDuplicate : " + removeDuplicate.removeDuplicatesUniqueCount(new int[] { 1, 1, 2 }));
        System.out.println("RemoveDuplicate : "
                + removeDuplicate.removeDuplicatesUniqueCount(new int[] { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 }));
    }

    // @formatter:off
    /*
     * 
     * https://leetcode.com/problems/remove-duplicates-from-sorted-array/description/
     * 
     * Given an integer array nums sorted in non-decreasing order, remove the
     * duplicates in-place such that each unique element appears only once. The
     * relative order of the elements should be kept the same. Then return the
     * number of unique elements in nums.
     * 
     * Consider the number of unique elements of nums to be k, to get accepted, you
     * need to do the following things:
     * 
     * Change the array nums such that the first k elements of nums contain the
     * unique elements in the order they were present in nums initially. The
     * remaining elements of nums are not important as well as the size of nums.
     * Return k.
     * Custom Judge:
     * 
     * The judge will test your solution with the following code:
     * 
     * int[] nums = [...]; // Input array
     * int[] expectedNums = [...]; // The expected answer with correct length
     * 
     * int k = removeDuplicates(nums); // Calls your implementation
     * 
     * assert k == expectedNums.length;
     * for (int i = 0; i < k; i++) {
     * assert nums[i] == expectedNums[i];
     * }
     * If all assertions pass, then your solution will be accepted.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,1,2]
     * Output: 2, nums = [1,2,_]
     * Explanation: Your function should return k = 2, with the first two elements
     * of nums being 1 and 2 respectively.
     * It does not matter what you leave beyond the returned k (hence they are
     * underscores).
     * Example 2:
     * 
     * Input: nums = [0,0,1,1,1,2,2,3,3,4]
     * Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
     * Explanation: Your function should return k = 5, with the first five elements
     * of nums being 0, 1, 2, 3, and 4 respectively.
     * It does not matter what you leave beyond the returned k (hence they are
     * underscores).
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 3 * 104
     * -100 <= nums[i] <= 100
     * nums is sorted in non-decreasing order.
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach             | Time  | Space | Code Complexity | Recommended?                                                      |
     * |----------------------|-------|-------|-----------------|-------------------------------------------------------------------|
     * | Auxiliary Collection | O(n)  | O(n)  | Low-Medium      | ❌ Not for prod/interview - wastes O(n) space; only if no mutate  |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int removeDuplicatesAuxiliaryCollection(int[] nums) {
        if (nums.length == 0)
            return 0;
        List<Integer> uniques = new ArrayList<>();
        for (int value : nums) {
            if (uniques.isEmpty() || uniques.get(uniques.size() - 1) != value)
                uniques.add(value);
        }

        for (int i = 0; i < uniques.size(); i++) {
            nums[i] = uniques.get(i);
        }
        return uniques.size();
    }

    // @formatter:off
    /**
     * 
     * | Approach             | Time  | Space | Code Complexity | Recommended?                                                      |
     * |----------------------|-------|-------|-----------------|-------------------------------------------------------------------|
     * | Two Pointers         | O(n)  | O(1)  | Low             | ✅✅ Best on both time and space - the intended solution         |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int removeDuplicatesTwoPointers(int[] nums) {
        if (nums.length == 0)
            return 0;
        int slow = 0;
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow]) {
                slow++;
                nums[slow] = nums[fast];
            }
        }
        return slow + 1;
    }

    public int removeDuplicatesUniqueCount(int[] nums) {
        int uniqueCount = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[uniqueCount]) {
                uniqueCount++;
                nums[uniqueCount] = nums[i];
            }
        }
        return uniqueCount + 1;
    }

}
// @formatter:off
/*
 * ============================================================
 * REMOVE DUPLICATES FROM SORTED ARRAY - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are given an integer array that is already sorted in non-decreasing
 * order. You must remove the duplicate elements in-place so that each unique
 * value appears exactly once, keeping the relative order. You then return k,
 * the number of unique elements. The first k slots of the array must hold
 * those unique values; whatever sits beyond index k-1 does not matter.
 *
 * This is LeetCode #26 - Remove Duplicates from Sorted Array (Easy).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums - a sorted array of integers.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * An int k - the count of unique elements.
 * Side effect: nums[0..k-1] must contain the unique values in their original
 * sorted order (mutated in place).
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * 1 <= nums.length <= 3 * 10^4
 * -100 <= nums[i] <= 100
 * nums is sorted in non-decreasing order.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Two things at once: the number of distinct values, AND an in-place
 * rearrangement so the front of the array is the deduplicated sequence.
 * The grader reads only the first k entries.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  nums = [0,0,1,1,1,2,2,3,3,4]
 *    Output: k = 5,  nums = [0,1,2,3,4,_,_,_,_,_]   (underscores = don't-care)
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Because the array is sorted, every group of identical values is contiguous.
 * Duplicates are always right next to each other. So to detect a duplicate,
 * you never need to look across the whole array - you only need to compare a
 * value against the last unique value you kept.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * Imagine stamping guests into a guest book, where guests arrive alphabetically:
 *   1. Keep a "write cursor" pointing at the last name you wrote.
 *   2. Walk through arrivals one by one with a "read cursor."
 *   3. If the new arrival's name equals the last written name, it's a repeat - skip it.
 *   4. If it's different, advance the write cursor and copy the new name there.
 *   5. When done, the number of names written (write cursor + 1) is your answer.
 * The write cursor never overtakes the read cursor, so overwriting is always safe.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                     | Why it's tricky                                                              |
 * |-------------------------------|------------------------------------------------------------------------------|
 * | "In-place" requirement        | No second array for full credit; overwrite within nums using O(1) space.     |
 * | Two return semantics at once  | You return count k but ALSO must arrange the first k cells correctly.        |
 * | Off-by-one on the pointer     | Return slow vs slow+1 depends on index-vs-length - a classic mistake.        |
 * | Don't-care tail               | Values after index k-1 are irrelevant; cleaning them wastes effort/bugs.     |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach              | Key Idea                                             | Best Used When                                | Time    | Space  |
 * |---|-----------------------|------------------------------------------------------|-----------------------------------------------|---------|--------|
 * | 1 | Auxiliary Collection  | Copy uniques into a List/new array, write them back  | Input can't be mutated / not guaranteed sorted| O(n)    | O(n)   |
 * | 2 | Two Pointers ✅        | Slow write-pointer at last unique; fast reads ahead  | Sorted array + in-place O(1) space required   | O(n) ✅ | O(1) ✅|
 *
 * Both approaches visit every element once, so both are linear in time. The
 * distinguishing axis is space: Approach 1 spends O(n) auxiliary memory to
 * stage the uniques, while Approach 2 exploits sortedness to do everything
 * inside the original array with a constant number of variables. Approach 2
 * dominates on BOTH axes - same time, strictly less space - and it's what
 * interviewers expect. Approach 1 is worth knowing only as a conceptual
 * stepping stone or when mutation of the input is disallowed.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Auxiliary Collection
 * ------------------------------------------------------------
 * Algorithm steps:
 *   1. Handle the trivial empty case (returns 0).
 *   2. Create a List<Integer> to collect uniques.
 *   3. Iterate through nums; add an element only if the list is empty or its
 *      last collected value differs from the current one.
 *   4. Copy the list back into the front of nums.
 *   5. Return the list size as k.
 *
 *    import java.util.ArrayList;
 *    import java.util.Arrays;
 *    import java.util.List;
 *
 *    public class RemoveDuplicatesAux {
 *
 *        public int removeDuplicates(int[] nums) {
 *            if (nums.length == 0) return 0;
 *
 *            List<Integer> uniques = new ArrayList<>();
 *            for (int value : nums) {
 *                // Since input is sorted, comparing to the last kept value suffices.
 *                if (uniques.isEmpty() || uniques.get(uniques.size() - 1) != value) {
 *                    uniques.add(value);
 *                }
 *            }
 *
 *            // Write the deduplicated values back into the front of nums.
 *            for (int i = 0; i < uniques.size(); i++) {
 *                nums[i] = uniques.get(i);
 *            }
 *            return uniques.size();
 *        }
 *
 *        public static void main(String[] args) {
 *            RemoveDuplicatesAux solver = new RemoveDuplicatesAux();
 *            int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
 *            int k = solver.removeDuplicates(nums);
 *            System.out.println("k = " + k);                       // 5
 *            System.out.println(Arrays.toString(Arrays.copyOf(nums, k))); // [0, 1, 2, 3, 4]
 *        }
 *    }
 *
 * Note on the comparison: uniques.get(...) != value compares Integer objects.
 * It works here because autoboxing plus the small-value range keeps things
 * correct in this specific logic, but the safe habit is comparing the primitive
 * last value. In the two-pointer version below there's no boxing at all.
 *
 * ------------------------------------------------------------
 * Approach 2: Two Pointers (slow / fast) ✅ Optimal
 * ------------------------------------------------------------
 * Algorithm steps:
 *   1. If the array is empty, return 0. (length >= 1 by constraints, but guard.)
 *   2. Set slow = 0. slow marks the index of the last unique value written.
 *   3. Move fast from 1 to the end.
 *   4. Compare nums[fast] with nums[slow]:
 *        - If equal, it's a duplicate -> do nothing, fast advances.
 *        - If different, increment slow, then copy nums[fast] into nums[slow].
 *   5. After the loop, slow is the index of the last unique, so count = slow + 1.
 *
 *    import java.util.Arrays;
 *
 *    public class RemoveDuplicatesTwoPointers {
 *
 *        public int removeDuplicates(int[] nums) {
 *            if (nums.length == 0) return 0;
 *
 *            int slow = 0; // index of the last unique value written
 *            for (int fast = 1; fast < nums.length; fast++) {
 *                if (nums[fast] != nums[slow]) {
 *                    slow++;
 *                    nums[slow] = nums[fast];
 *                }
 *            }
 *            return slow + 1; // slow is an index -> length is index + 1
 *        }
 *
 *        public static void main(String[] args) {
 *            RemoveDuplicatesTwoPointers solver = new RemoveDuplicatesTwoPointers();
 *
 *            int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
 *            int k = solver.removeDuplicates(nums);
 *            System.out.println("k = " + k);                        // 5
 *            System.out.println(Arrays.toString(Arrays.copyOf(nums, k))); // [0, 1, 2, 3, 4]
 *
 *            int[] single = {7};
 *            System.out.println("k = " + solver.removeDuplicates(single)); // 1
 *        }
 *    }
 *
 * Why slow + 1 and not slow: slow is used throughout as an index into the
 * array. The number of elements from index 0 to index slow inclusive is
 * slow + 1. Returning slow would undercount by exactly one.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Auxiliary Collection
 * ------------------------------------------------------------
 * Time - O(n): One pass to build the list (n insertions, amortized O(1)),
 *   plus one pass of size k <= n to copy back. Total O(n) + O(k) = O(n).
 * Space - O(n): The uniques list can hold up to n elements when all distinct.
 * Numeric estimate: For n = 30,000 all-distinct, ~30,000 insertions + ~30,000
 *   copy-backs ~= 60,000 operations, plus a list of 30,000 Integer objects.
 *
 * ------------------------------------------------------------
 * Approach 2: Two Pointers ✅
 * ------------------------------------------------------------
 * Time - O(n): fast advances exactly n-1 times; each step does one comparison
 *   and at most one assignment. Linear.
 * Space - O(1): Only two integer variables (slow, fast) regardless of input.
 * Numeric estimate: For n = 30,000, ~30,000 comparisons and at most 30,000
 *   writes, with zero extra allocation. For [1,1,...,1] (30,000 ones), it does
 *   30,000 comparisons and zero writes - the write branch never fires.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 - trace on nums = [1, 1, 2, 3, 3]
 * ------------------------------------------------------------
 * Scan building uniques:
 * ├─ value=1 → list empty        → add 1 → uniques=[1]
 * ├─ value=1 → last=1 == 1       → skip  → uniques=[1]
 * ├─ value=2 → last=1 != 2       → add 2 → uniques=[1,2]
 * ├─ value=3 → last=2 != 3       → add 3 → uniques=[1,2,3]
 * └─ value=3 → last=3 == 3       → skip  → uniques=[1,2,3]
 *
 * Copy back into nums: nums = [1, 2, 3, 3, 3]  (indices 3,4 are don't-care)
 * Return k = 3. Front three: [1, 2, 3] ✅
 *
 * ------------------------------------------------------------
 * Approach 2 - trace on nums = [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]
 * ------------------------------------------------------------
 * | fast | nums[fast] | nums[slow] | equal? | action            | slow after | array front  |
 * |------|------------|------------|--------|-------------------|------------|--------------|
 * | 1    | 0          | 0 (idx0)   | yes    | skip              | 0          | [0,...]      |
 * | 2    | 1          | 0 (idx0)   | no     | slow→1, nums[1]=1  | 1          | [0,1,...]    |
 * | 3    | 1          | 1 (idx1)   | yes    | skip              | 1          | [0,1,...]    |
 * | 4    | 1          | 1 (idx1)   | yes    | skip              | 1          | [0,1,...]    |
 * | 5    | 2          | 1 (idx1)   | no     | slow→2, nums[2]=2  | 2          | [0,1,2,...]  |
 * | 6    | 2          | 2 (idx2)   | yes    | skip              | 2          | [0,1,2,...]  |
 * | 7    | 3          | 2 (idx2)   | no     | slow→3, nums[3]=3  | 3          | [0,1,2,3,...]|
 * | 8    | 3          | 3 (idx3)   | yes    | skip              | 3          | [0,1,2,3,...]|
 * | 9    | 4          | 3 (idx3)   | no     | slow→4, nums[4]=4  | 4          | [0,1,2,3,4,.]|
 *
 * Loop ends. slow = 4 → return slow + 1 = 5. Front five: [0,1,2,3,4] ✅
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case              | Input          | Expected Output    | How Handled                                                    |
 * |------------------------|----------------|--------------------|----------------------------------------------------------------|
 * | Single element         | [5]            | k=1, [5]           | Loop never runs (fast starts at 1 >= length); returns slow+1=1.|
 * | All identical          | [2,2,2,2]      | k=1, [2]           | Every comparison equal -> write never fires -> slow=0 -> 1.    |
 * | All distinct           | [1,2,3,4]      | k=4, [1,2,3,4]     | Every comparison differs -> each step advances slow and copies.|
 * | Two elements, duplicate| [9,9]          | k=1, [9]           | fast=1 equal -> skip -> returns 1.                             |
 * | Negative values        | [-3,-3,-1,0]   | k=3, [-3,-1,0]     | Comparison works on ints regardless of sign.                  |
 * | Empty (defensive)      | []             | k=0                | Early return 0 guard.                                         |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 - returning the index instead of the length:
 *    // WRONG
 *    return slow;        // undercounts by 1
 *    // CORRECT
 *    return slow + 1;    // slow is an index; length = index + 1
 *
 * Pitfall 2 - starting fast at 0 and comparing nums[fast] to itself:
 *    // WRONG - at fast=0 you compare nums[0] to nums[0], logic gets muddled
 *    for (int fast = 0; fast < nums.length; fast++) { ... }
 *    // CORRECT - the first element is trivially unique, start reading from 1
 *    for (int fast = 1; fast < nums.length; fast++) { ... }
 *
 * Pitfall 3 - advancing the write pointer before the check:
 *    // WRONG - writes on every iteration, duplicating values forward
 *    slow++;
 *    if (nums[fast] != nums[slow]) nums[slow] = nums[fast];
 *    // CORRECT - only advance/write when a new unique is found
 *    if (nums[fast] != nums[slow]) { slow++; nums[slow] = nums[fast]; }
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The main risk is the empty array and the single-element array. Both are
 *    covered: the empty case hits the early return 0, and the single-element
 *    case skips the loop and returns 1. All-identical and all-distinct inputs
 *    are the two extremes of the write branch (never fires vs fires every time)
 *    and both are handled.
 *
 * Q: Are there any type mismatches?
 * A: In the two-pointer version, no - everything is primitive int, and the
 *    return type int matches slow + 1. In the auxiliary version, List<Integer>
 *    comparisons use object references via autoboxing; comparing the primitive
 *    last value avoids surprises. Neither slow+1 nor array indices overflow for
 *    n <= 3*10^4.
 *
 * Q: How can I verify this works right now?
 *    public static void verify() {
 *        RemoveDuplicatesTwoPointers s = new RemoveDuplicatesTwoPointers();
 *
 *        int[] a = {0,0,1,1,1,2,2,3,3,4};
 *        assert s.removeDuplicates(a) == 5;
 *        assert a[0]==0 && a[1]==1 && a[2]==2 && a[3]==3 && a[4]==4;
 *
 *        int[] b = {1};
 *        assert s.removeDuplicates(b) == 1;
 *
 *        int[] c = {2,2,2,2};
 *        assert s.removeDuplicates(c) == 1 && c[0] == 2;
 *
 *        int[] d = {1,2,3,4};
 *        assert s.removeDuplicates(d) == 4;
 *
 *        int[] e = {-3,-3,-1,0};
 *        assert s.removeDuplicates(e) == 3 && e[0]==-3 && e[1]==-1 && e[2]==0;
 *
 *        System.out.println("All assertions passed.");
 *    }
 *    // Run with: java -ea RemoveDuplicatesTwoPointers  (the -ea flag enables asserts)
 *
 * | Approach             | Risk                                          | Mitigation                                                     |
 * |----------------------|-----------------------------------------------|----------------------------------------------------------------|
 * | Auxiliary Collection | Extra O(n) memory; Integer boxing overhead    | Prefer only when mutation disallowed; compare primitive value. |
 * | Two Pointers         | Off-by-one on return; wrong fast start        | Anchor slow as "last unique index," start fast at 1, ret slow+1|
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * LeetCode #26 - Difficulty: Easy - Appears very frequently as a warm-up /
 * phone-screen question (thousands of reported interview appearances).
 *
 * | Company        | Frequency (stars) | Notes                                                            |
 * |----------------|-------------------|------------------------------------------------------------------|
 * | Amazon         | ⭐⭐⭐⭐⭐        | Common phone-screen warm-up; often paired with #80 (keep 2 dupes)|
 * | Microsoft      | ⭐⭐⭐⭐          | Tests in-place / two-pointer fluency                            |
 * | Google         | ⭐⭐⭐⭐          | Usually a lead-in before a harder array problem                 |
 * | Meta           | ⭐⭐⭐⭐          | Quick screen; expects the O(1)-space answer                     |
 * | Apple          | ⭐⭐⭐            | Appears in early rounds                                         |
 * | Bloomberg      | ⭐⭐⭐⭐          | A staple two-pointer question                                   |
 * | Adobe          | ⭐⭐⭐            | Shows up in screening rounds                                    |
 * | Goldman Sachs  | ⭐⭐⭐            | Used for foundational array reasoning                           |
 * | Uber           | ⭐⭐              | Occasional warm-up                                             |
 * | Oracle         | ⭐⭐              | Appears in entry-level screens                                 |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach             | Time  | Space | Code Complexity | Recommended?                                                      |
 * |----------------------|-------|-------|-----------------|-------------------------------------------------------------------|
 * | Auxiliary Collection | O(n)  | O(n)  | Low-Medium      | ❌ Not for prod/interview - wastes O(n) space; only if no mutate  |
 * | Two Pointers         | O(n)  | O(1)  | Low             | ✅✅ Best on both time and space - the intended solution         |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use the two-pointer (slow/fast) method. It's linear time, constant space,
 * and dominates the auxiliary approach on every axis - there's no trade-off to
 * weigh here, since the sorted input lets you deduplicate entirely in place.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * The key pattern is the read/write two-pointer on a sorted array: a slow
 * pointer marks the end of the "kept" region while fast scans ahead, and you
 * only write when a NEW value appears. Memorize that sortedness makes
 * duplicates contiguous, so comparing against the last kept element is
 * sufficient - and that slow is an index, so the answer is slow + 1.
 */
// @formatter:on
