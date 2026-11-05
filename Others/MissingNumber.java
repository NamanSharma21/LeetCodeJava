package Others;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MissingNumber {
    public static void main(String[] args) {
        MissingNumber missingNumber = new MissingNumber();
        System.out.println("Missing Number : " + missingNumber.missingNumberSorting(new int[] { 3, 0, 1 }));
        System.out.println("Missing Number : " + missingNumber.missingNumberSorting(new int[] { 0, 1 }));
        System.out.println(
                "Missing Number : " + missingNumber.missingNumberSorting(new int[] { 9, 6, 4, 2, 3, 5, 7, 0, 1 }));
        System.out.println("-------------------");
        System.out.println("Missing Number : " + missingNumber.missingNumberHashSet(new int[] { 3, 0, 1 }));
        System.out.println("Missing Number : " + missingNumber.missingNumberHashSet(new int[] { 0, 1 }));
        System.out.println(
                "Missing Number : " + missingNumber.missingNumberHashSet(new int[] { 9, 6, 4, 2, 3, 5, 7, 0, 1 }));
        System.out.println("-------------------");
        System.out.println("Missing Number : " + missingNumber.missingNumberSumGauss(new int[] { 3, 0, 1 }));
        System.out.println("Missing Number : " + missingNumber.missingNumberSumGauss(new int[] { 0, 1 }));
        System.out.println(
                "Missing Number : " + missingNumber.missingNumberSumGauss(new int[] { 9, 6, 4, 2, 3, 5, 7, 0, 1 }));
        System.out.println("-------------------");
        System.out.println("Missing Number : " + missingNumber.missingNumberXOR(new int[] { 3, 0, 1 }));
        System.out.println("Missing Number : " + missingNumber.missingNumberXOR(new int[] { 0, 1 }));
        System.out
                .println("Missing Number : " + missingNumber.missingNumberXOR(new int[] { 9, 6, 4, 2, 3, 5, 7, 0, 1 }));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/missing-number/description/
     * 
     * 
     * Given an array nums containing n distinct numbers in the range [0, n], return
     * the only number in the range that is missing from the array.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [3,0,1]
     * 
     * Output: 2
     * 
     * Explanation:
     * 
     * n = 3 since there are 3 numbers, so all numbers are in the range [0,3]. 2 is
     * the missing number in the range since it does not appear in nums.
     * 
     * Example 2:
     * 
     * Input: nums = [0,1]
     * 
     * Output: 2
     * 
     * Explanation:
     * 
     * n = 2 since there are 2 numbers, so all numbers are in the range [0,2]. 2 is
     * the missing number in the range since it does not appear in nums.
     * 
     * Example 3:
     * 
     * Input: nums = [9,6,4,2,3,5,7,0,1]
     * 
     * Output: 8
     * 
     * Explanation:
     * 
     * n = 9 since there are 9 numbers, so all numbers are in the range [0,9]. 8 is
     * the missing number in the range since it does not appear in nums.
     * 
     * 
     * Constraints:
     * 
     * n == nums.length
     * 1 <= n <= 104
     * 0 <= nums[i] <= n
     * All the numbers of nums are unique.
     * 
     * 
     * Follow up: Could you implement a solution using only O(1) extra space
     * complexity and O(n) runtime complexity?
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach    | Time       | Space    | Code Complexity          | Recommended? |
     * |-------------|------------|----------|--------------------------|--------------|
     * | Sorting     | O(n log n) | O(log n) | Medium (boundary checks) | NO — only if array must be sorted anyway |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int missingNumberSorting(int[] nums) {
        int[] sorted = nums.clone();
        int n = sorted.length;
        Arrays.sort(sorted);
        if (sorted[n - 1] != n)
            return n;
        if (sorted[0] != 0)
            return 0;

        for (int i = 1; i < n; i++) {
            if (sorted[i] != sorted[i - 1] + 1)
                return sorted[i - 1] + 1;
        }
        return -1;
    }

    // @formatter:off
    /**
     * 
     * | Approach    | Time       | Space    | Code Complexity          | Recommended? |
     * |-------------|------------|----------|--------------------------|--------------|
     * | HashSet     | O(n)       | O(n)     | Low                      | NO — wastes memory vs. co-optimal options |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int missingNumberHashSet(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            seen.add(num);
        }
        for (int i = 0; i <= nums.length; i++) {
            if (!seen.contains(i))
                return i;
        }
        return -1;
    }

    // @formatter:off
    /**
     * 
     * | Approach    | Time       | Space    | Code Complexity          | Recommended? |
     * |-------------|------------|----------|--------------------------|--------------|
     * | Sum (Gauss) | O(n)       | O(1)     | Very low                 | YES YES — best for readability (mind overflow) |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int missingNumberSumGauss(int[] nums) {
        int n = nums.length;
        int expected = n * (n + 1) / 2;
        int actual = 0;
        for (int num : nums) {
            actual += num;
        }
        return expected - actual;
    }

    // @formatter:off
    /**
     * 
     * | Approach    | Time       | Space    | Code Complexity          | Recommended? |
     * |-------------|------------|----------|--------------------------|--------------|
     * | XOR         | O(n)       | O(1)     | Low                      | YES YES — best for overflow-safety |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int missingNumberXOR(int[] nums) {
        int result = nums.length;
        for (int i = 0; i < nums.length; i++) {
            result ^= i ^ nums[i];
        }
        return result;
    }
}
// @formatter:off
/*
 * ============================================================
 * MISSING NUMBER — DEEP DIVE EXPLANATION
 * ============================================================
 * LeetCode #268 — Difficulty: Easy — Language: Java
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are given an array nums containing n DISTINCT integers drawn
 * from the range [0, n]. That range has n + 1 possible values, but the
 * array holds only n of them — so EXACTLY ONE number in [0, n] is
 * missing. Return that missing number.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums — array of length n, distinct integers, each in [0, n].
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * A single int: the one value in [0, n] that does not appear in nums.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * n == nums.length
 * 1 <= n <= 10^4
 * 0 <= nums[i] <= n
 * All numbers in nums are unique.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Out of the n + 1 candidate values {0, 1, ..., n}, the array uses n of
 * them exactly once. Find the single leftover value.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 * Input:  nums = [3, 0, 1]   (n = 3, range is [0, 3])
 * Values present: {0, 1, 3}
 * Range [0,3] = {0, 1, 2, 3}
 * Missing = 2
 * Output: 2
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Picture a class of students numbered 0 through n. Roll call is taken
 * but one seat is empty. Two easy ways to find who's absent: (a) sort the
 * roster and look for the gap, or (b) know the full roster's numbers sum
 * to a fixed total, add up who's PRESENT, and the difference is the
 * absentee.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. The complete set is fixed and known: {0, 1, ..., n}.
 * 2. The array gives you all of that set EXCEPT one element.
 * 3. So "the full thing minus what you have" leaves the missing piece.
 * 4. You can compute "the full thing" cheaply — as a SUM (0+1+...+n) or a
 *    running XOR — without ever storing the whole set.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                          | Why it's tricky |
 * |------------------------------------|-----------------|
 * | Range is [0, n], not [1, n]        | n + 1 candidate values for a length-n array; off-by-one errors are easy. |
 * | Missing value can be 0 or n        | Naive scans that assume the gap is "in the middle" break on boundaries. |
 * | Integer overflow with the sum trick| n(n+1)/2 for large n can exceed int range if types/ordering are careless. |
 * | Achieving O(1) extra space         | The obvious hash-set answer costs O(n) memory; constant space needs sum or XOR. |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach          | Key Idea                                              | Best Used When                              | Time       | Space          |
 * |---|-------------------|-------------------------------------------------------|---------------------------------------------|------------|----------------|
 * | 1 | Sorting           | Sort, then find first index where value != index      | Array already needed sorted; simple model   | O(n log n) | O(1)/O(log n)* |
 * | 2 | HashSet           | Insert all values, then probe 0..n for absent one     | Want simple O(n) time; memory is cheap      | O(n)       | O(n)           |
 * | 3 | Sum (Gauss)       | expected = n(n+1)/2; answer = expected - actualSum     | General optimal case; arithmetic is fine    | O(n) [OK]  | O(1) [OK]      |
 * | 4 | XOR (bit manip)   | XOR all indices 0..n with all values; pairs cancel     | Same optimality as sum, but avoid overflow  | O(n) [OK]  | O(1) [OK]      |
 *
 * * Sorting space depends on the algorithm; Java primitive Arrays.sort is
 *   dual-pivot quicksort using O(log n) stack, and here we sort a clone to
 *   avoid mutating input.
 *
 * Trade-off discussion:
 * Sorting is easiest to reason about but pays a log n factor. HashSet buys
 * O(n) time at O(n) memory. SUM and XOR are both optimal on BOTH axes —
 * O(n) time, O(1) space — so they jointly win. Prefer XOR when you want to
 * be bulletproof against integer overflow (it never exceeds n); prefer SUM
 * for the most readable one-liner. Both are marked optimal because neither
 * dominates the other.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Sorting
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Clone the array (avoid mutating caller input) and sort the clone.
 * 2. If the last element isn't n, the missing number is n.
 * 3. If the first element isn't 0, the missing number is 0.
 * 4. Otherwise scan adjacent pairs; first place where a[i] != a[i-1] + 1
 *    reveals the gap: return a[i-1] + 1.
 *
 *    import java.util.Arrays;
 *
 *    public class MissingNumberSorting {
 *        public int missingNumber(int[] nums) {
 *            int[] sorted = nums.clone();
 *            Arrays.sort(sorted);
 *            int n = sorted.length;
 *
 *            if (sorted[n - 1] != n) return n;   // top of range missing
 *            if (sorted[0] != 0) return 0;       // bottom of range missing
 *
 *            for (int i = 1; i < n; i++) {       // interior gap
 *                if (sorted[i] != sorted[i - 1] + 1) {
 *                    return sorted[i - 1] + 1;
 *                }
 *            }
 *            return -1; // unreachable given problem guarantees
 *        }
 *
 *        public static void main(String[] args) {
 *            MissingNumberSorting solver = new MissingNumberSorting();
 *            System.out.println(solver.missingNumber(new int[]{3, 0, 1})); // 2
 *        }
 *    }
 *
 * The three-way check (top, bottom, interior) makes this robust to the
 * boundary cases where 0 or n is the missing value.
 *
 * ------------------------------------------------------------
 * Approach 2: HashSet
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Insert every element of nums into a HashSet for O(1) membership.
 * 2. Iterate i from 0 to n inclusive (n + 1 probes).
 * 3. Return the first i not present in the set.
 *
 *    import java.util.HashSet;
 *    import java.util.Set;
 *
 *    public class MissingNumberHashSet {
 *        public int missingNumber(int[] nums) {
 *            Set<Integer> seen = new HashSet<>();
 *            for (int value : nums) {
 *                seen.add(value);
 *            }
 *            for (int i = 0; i <= nums.length; i++) {
 *                if (!seen.contains(i)) {
 *                    return i;
 *                }
 *            }
 *            return -1; // unreachable given problem guarantees
 *        }
 *
 *        public static void main(String[] args) {
 *            MissingNumberHashSet solver = new MissingNumberHashSet();
 *            System.out.println(solver.missingNumber(
 *                new int[]{9, 6, 4, 2, 3, 5, 7, 0, 1})); // 8
 *        }
 *    }
 *
 * The loop bound is i <= nums.length (i <= n), which lets it detect n
 * itself as the missing value.
 *
 * ------------------------------------------------------------
 * Approach 3: Sum (Gauss) Formula   [OPTIMAL]
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Compute expected = n * (n + 1) / 2, the sum of 0 + 1 + ... + n.
 * 2. Compute actual, the sum of the elements present.
 * 3. The missing number is expected - actual.
 *
 *    public class MissingNumberSum {
 *        public int missingNumber(int[] nums) {
 *            int n = nums.length;
 *            int expected = n * (n + 1) / 2; // sum of 0..n
 *            int actual = 0;
 *            for (int value : nums) {
 *                actual += value;
 *            }
 *            return expected - actual;
 *        }
 *
 *        public static void main(String[] args) {
 *            MissingNumberSum solver = new MissingNumberSum();
 *            System.out.println(solver.missingNumber(new int[]{0, 1})); // 2
 *        }
 *    }
 *
 * About overflow: with n <= 10^4, expected <= 10^4 * 10^4 / 2 ~= 5*10^7,
 * comfortably inside int (max ~= 2.1*10^9). For much larger n, use long.
 * Overflow-proof variant: start result = n, then for each i do
 * result += i - nums[i] so no large partial sum accumulates.
 *
 * ------------------------------------------------------------
 * Approach 4: XOR (Bit Manipulation)   [OPTIMAL]
 * ------------------------------------------------------------
 * Algorithm:
 * 1. XOR is associative/commutative, x ^ x = 0, x ^ 0 = x.
 * 2. XOR together all indices 0..n and all values in nums.
 * 3. Every number appearing as both index and value cancels to 0; only
 *    the missing number (an index with no matching value) survives.
 * 4. Seed the accumulator with n to cover the top index with no slot.
 *
 *    public class MissingNumberXor {
 *        public int missingNumber(int[] nums) {
 *            int result = nums.length; // covers index n (no element)
 *            for (int i = 0; i < nums.length; i++) {
 *                result ^= i ^ nums[i];
 *            }
 *            return result;
 *        }
 *
 *        public static void main(String[] args) {
 *            MissingNumberXor solver = new MissingNumberXor();
 *            System.out.println(solver.missingNumber(new int[]{3, 0, 1})); // 2
 *        }
 *    }
 *
 * Key formula: result = n ^ (0^nums[0]) ^ (1^nums[1]) ^ ... ^
 * ((n-1)^nums[n-1]). It XORs the full set {0..n} against the array;
 * identical elements annihilate, leaving the singleton. It never produces
 * a value greater than n, so overflow is impossible.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Approach 1: Sorting
 *   Time  — O(n log n): the sort dominates; the scan is O(n).
 *   Space — O(log n) sort recursion (plus the O(n) clone to protect input).
 *   Estimate — n = 10^4: ~1.3*10^5 comparisons.
 *
 * Approach 2: HashSet
 *   Time  — O(n): one insert pass + n+1 lookups.
 *   Space — O(n): up to n boxed Integer objects.
 *   Estimate — n = 10^4: ~2*10^4 hash ops, ~10^4 stored entries.
 *
 * Approach 3: Sum
 *   Time  — O(n): a single accumulation pass; formula is O(1).
 *   Space — O(1): two integer accumulators.
 *   Estimate — n = 10^4: exactly 10^4 additions; expected ~= 5*10^7.
 *
 * Approach 4: XOR
 *   Time  — O(n): single pass, two XORs per element.
 *   Space — O(1): one accumulator.
 *   Estimate — n = 10^4: ~2*10^4 XORs; accumulator never exceeds 10^4.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 (Sorting) — input [3, 0, 1]
 * ------------------------------------------------------------
 * clone + sort -> [0, 1, 3]        (n = 3)
 * |- last sorted[2]=3, n=3 -> equal, NOT top-missing
 * |- first sorted[0]=0 -> equal, NOT bottom-missing
 * |- scan interior:
 *    |- i=1: sorted[1]=1, sorted[0]+1=1 -> equal, continue
 *    |- i=2: sorted[2]=3, sorted[1]+1=2 -> 3 != 2 -> return sorted[1]+1 = 2
 * Output: 2
 *
 * ------------------------------------------------------------
 * Approach 2 (HashSet) — input [9, 6, 4, 2, 3, 5, 7, 0, 1]
 * ------------------------------------------------------------
 * seen = {0,1,2,3,4,5,6,7,9}       (n = 9)
 * probe i=0 -> present
 * probe i=1 -> present
 * ...
 * probe i=7 -> present
 * probe i=8 -> NOT present -> return 8
 * Output: 8
 *
 * ------------------------------------------------------------
 * Approach 3 (Sum) — input [0, 1]
 * ------------------------------------------------------------
 * n = 2
 * expected = 2*3/2 = 3
 * actual   = 0 + 1 = 1
 * missing  = 3 - 1 = 2
 * Output: 2
 *
 * ------------------------------------------------------------
 * Approach 4 (XOR) — input [3, 0, 1]
 * ------------------------------------------------------------
 * result = n = 3                       (binary 011)
 * i=0: result ^= 0 ^ 3 -> 3 ^ 0 ^ 3 = 0    (011 ^ 000 ^ 011 = 000)
 * i=1: result ^= 1 ^ 0 -> 0 ^ 1 ^ 0 = 1    (000 ^ 001 ^ 000 = 001)
 * i=2: result ^= 2 ^ 1 -> 1 ^ 2 ^ 1 = 2    (001 ^ 010 ^ 001 = 010)
 * Output: 2
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                | Input             | Expected | How Handled |
 * |--------------------------|-------------------|----------|-------------|
 * | Single element, 0 present| [0]               | 1        | Range [0,1]; 1 missing. Sum 1-0=1; XOR seeds n=1. |
 * | Single element, 0 missing| [1]               | 0        | Range [0,1]; 0 missing. Sum 1-1=0; XOR resolves 0. |
 * | Missing value is 0       | [1,2,3,4,5,6]     | 0        | Bottom-of-range; sorting's sorted[0]!=0 catches it. |
 * | Missing value is n (top) | [0,1,2,3,4,5]     | 6        | Top-of-range; sorting's sorted[n-1]!=n catches it. |
 * | Full shuffled array      | [9,6,4,2,3,5,7,0,1]| 8       | All approaches handle interior gaps. |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 — Wrong loop bound (missing the top value):
 *    // WRONG — stops at n-1, never checks whether n itself is missing
 *    for (int i = 0; i < nums.length; i++) { ... }
 *    // CORRECT — inclusive of n, since range is [0, n]
 *    for (int i = 0; i <= nums.length; i++) { ... }
 *
 * Pitfall 2 — Overflow in the sum formula for large n:
 *    // RISKY — int multiplication can overflow before division
 *    int expected = n * (n + 1) / 2;
 *    // SAFER — promote to long, or fold subtraction into the loop
 *    long expected = (long) n * (n + 1) / 2;
 *    // or: int result = n; for (i) result += i - nums[i];
 *
 * Pitfall 3 — Forgetting to seed the XOR with n:
 *    // WRONG — indices run 0..n-1, so index n is never included
 *    int result = 0;
 *    for (int i = 0; i < nums.length; i++) result ^= i ^ nums[i];
 *    // CORRECT — seed with n to represent the missing top index
 *    int result = nums.length;
 *    for (int i = 0; i < nums.length; i++) result ^= i ^ nums[i];
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The two boundary cases — missing value = 0 and missing value = n —
 *    are the classic failure points. Sum and XOR handle both automatically
 *    (they never assume the gap is interior). The sorting approach needs
 *    its explicit top/bottom checks; without them it would miss those.
 *
 * Q: Are there any type mismatches?
 * A: The only concern is int overflow in the sum formula's n*(n+1)/2.
 *    Within constraints (n <= 10^4) it's safe; promote to long or fold the
 *    subtraction into the loop for robustness. XOR has no arithmetic and so
 *    no overflow risk at all.
 *
 * Q: How can I verify this works right now?
 *    public class MissingNumberVerify {
 *        static int missingNumber(int[] nums) {   // XOR reference
 *            int result = nums.length;
 *            for (int i = 0; i < nums.length; i++) result ^= i ^ nums[i];
 *            return result;
 *        }
 *        public static void main(String[] args) {
 *            assert missingNumber(new int[]{3, 0, 1}) == 2;
 *            assert missingNumber(new int[]{0, 1}) == 2;
 *            assert missingNumber(new int[]{9,6,4,2,3,5,7,0,1}) == 8;
 *            assert missingNumber(new int[]{0}) == 1;            // missing top
 *            assert missingNumber(new int[]{1}) == 0;            // missing bottom
 *            assert missingNumber(new int[]{1,2,3,4,5,6}) == 0;  // missing 0
 *            assert missingNumber(new int[]{0,1,2,3,4,5}) == 6;  // missing n
 *            System.out.println("All assertions passed.");
 *        }
 *    }
 *    // Run with:  java -ea MissingNumberVerify
 *
 * | Approach | Risk                                | Mitigation |
 * |----------|-------------------------------------|------------|
 * | Sorting  | Forgetting boundary checks for 0/n  | Explicit top and bottom guards before the interior scan |
 * | HashSet  | Off-by-one loop bound; O(n) memory  | Loop i <= n; accept memory cost only when acceptable |
 * | Sum      | Integer overflow at large n         | Use long, or fold subtraction into the loop |
 * | XOR      | Forgetting to seed accumulator with n| Initialize result = nums.length before the loop |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #268 "Missing Number" — Difficulty: Easy. A very common
 * warm-up / phone-screen question; thousands of reported appearances.
 *
 * | Company          | Frequency (stars) | Notes |
 * |------------------|-------------------|-------|
 * | Amazon           | *****             | Popular phone-screen warm-up; often paired with overflow follow-up. |
 * | Google           | ****              | Probes XOR reasoning and the "why does it cancel" explanation. |
 * | Microsoft        | ****              | Common early-round filter question. |
 * | Facebook / Meta  | ****              | Frequently asked to reach the O(1)-space solution. |
 * | Apple            | ***               | Appears in coding screens as a quick check. |
 * | Bloomberg        | ****              | Loves the arithmetic-sum trick and overflow discussion. |
 * | Adobe            | ***               | Standard easy array question. |
 * | Oracle           | ***               | Shows up in screening rounds. |
 * | Goldman Sachs    | ***               | Occasionally as a fundamentals check. |
 * | Uber             | **                | Sometimes used as a quick opener. |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach    | Time       | Space    | Code Complexity        | Recommended? |
 * |-------------|------------|----------|------------------------|--------------|
 * | Sorting     | O(n log n) | O(log n) | Medium (boundary checks)| NO — only if array must be sorted anyway |
 * | HashSet     | O(n)       | O(n)     | Low                    | NO — wastes memory vs. co-optimal options |
 * | Sum (Gauss) | O(n)       | O(1)     | Very low               | YES YES — best for readability (mind overflow) |
 * | XOR         | O(n)       | O(1)     | Low                    | YES YES — best for overflow-safety |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use Sum or XOR — both O(n) time, O(1) space. They tie on complexity, so
 * choose by preference: XOR is safest (no overflow, result never exceeds
 * n), while Sum is the most readable one-liner. Reach for sorting or a
 * hash set only when the surrounding problem already demands them.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * The whole problem reduces to "complete set minus present set = the
 * missing element." Compute the "complete set" cheaply with either the
 * identity n(n+1)/2 or a running XOR over indices and values — both give
 * O(1) space. Two gotchas: the range is [0, n] so the loop/seed must
 * include n, and the sum trick can overflow, which XOR sidesteps entirely.
 */
// @formatter:on
