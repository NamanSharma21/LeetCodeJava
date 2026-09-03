package Array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ContainsDuplicate {
    public static void main(String[] args) {
        ContainsDuplicate containsDuplicate = new ContainsDuplicate();
        System.out
                .println("ContainsDuplicate : " + containsDuplicate.containsDuplicateHashSet(new int[] { 1, 2, 3, 1 }));
        System.out.println("----------------------------------------------------");
        System.out.println(
                "ContainsDuplicate : " + containsDuplicate.containsDuplicateBruteForce(new int[] { 1, 2, 3, 1 }));
        System.out.println("----------------------------------------------------");
        System.out.println(
                "ContainsDuplicate : " + containsDuplicate.containsDuplicateSortScanAdjacent(new int[] { 1, 2, 3, 1 }));
    }

    // @formatter:off
    /*
     * Given an integer array nums, return true if any value appears at least twice
     * in the array, and return false if every element is distinct.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,2,3,1]
     * 
     * Output: true
     * 
     * Explanation:
     * 
     * The element 1 occurs at the indices 0 and 3.
     * 
     * Example 2:
     * 
     * Input: nums = [1,2,3,4]
     * 
     * Output: false
     * 
     * Explanation:
     * 
     * All elements are distinct.
     * 
     * Example 3:
     * 
     * Input: nums = [1,1,1,3,3,4,3,2,4,2]
     * 
     * Output: true
     * 
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 105
     * -109 <= nums[i] <= 109
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * Approach 1: Brute Force
     * 
     * Time: O(n²) — for each of the n elements, we may compare against up to n
     * others. Worst case (no duplicates) performs roughly n(n-1)/2 comparisons.
     * 
     * Space: O(1) — no extra data structures, only loop counters.
     * 
     * Example: n = 1,000 with no duplicates → ~499,500 comparisons. n = 10,000 →
     * ~49,995,000 comparisons — already too slow for 10^5-sized inputs within
     * typical time limits.
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public boolean containsDuplicateBruteForce(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int y = i + 1; y < n; y++) {
                if (nums[i] == nums[y]) {
                    return true;
                }
            }
        }
        return false;
    }

    // @formatter:off
    /**
     * 
     * Approach 2: Sort then Scan
     * 
     * Time: O(n log n) — dominated by the sort; the adjacent scan afterward is O(n)
     * and doesn't change the overall order.
     * 
     * Space: O(1) extra if sorting in place (Arrays.sort on primitive int[] uses an
     * in-place dual-pivot quicksort); O(n) if you must preserve the original array
     * and copy it first.
     * 
     * Example: n = 100,000 → sort does roughly 100,000 × log₂(100,000) ≈ 100,000 ×
     * 17 ≈ 1.7 million comparisons — dramatically better than brute force's ~5
     * billion.
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public boolean containsDuplicateSortScanAdjacent(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        for (int i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * Approach 3: Hash Set
     * 
     * Time: O(n) — each insertion/lookup in a HashSet is O(1) on average, and we do
     * this once per element.
     * 
     * Space: O(n) — in the worst case (no duplicates), every element gets stored in
     * the set.
     * 
     * Example: n = 100,000 → exactly 100,000 constant-time operations in the
     * average case — the fastest of the three.
     * 
     * @param nums
     * @return
     */
    public boolean containsDuplicateHashSet(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            if (!seen.add(num)) {
                return true;
            }
        }
        return false;
    }
}

// @formatter:off
/*
 * ============================================================
 * CONTAINS DUPLICATE — DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an array of integers, determine whether any value appears
 * more than once. You don't need to find WHICH values repeat or
 * WHERE — just a yes/no answer.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums — an array of integers (can include negatives, zero,
 * and duplicates in any position)
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * boolean — true if at least one value appears at least twice,
 * false if all values are distinct
 *
 * ------------------------------------------------------------
 * Constraints (typical LeetCode 217 constraints)
 * ------------------------------------------------------------
 * 1 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * A single boolean answering: "does this array contain at least
 * one duplicate element?"
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 * Input:  nums = [1, 2, 3, 1]
 * Output: true   (1 appears twice)
 *
 * Input:  nums = [1, 2, 3, 4]
 * Output: false  (all unique)
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * You're checking off names on a guest list. If you ever try to
 * check off a name that's already checked, you've found a duplicate.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Look at each number one at a time.
 * 2. Ask: "Have I seen this number before?"
 * 3. If yes -> duplicate found, stop immediately and answer true.
 * 4. If you finish scanning without ever answering "yes" -> answer false.
 * 5. The only real design decision is HOW you remember "numbers
 *    I've already seen" efficiently.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                              | Why it's tricky                                                          |
 * |-----------------------------------------|---------------------------------------------------------------------------|
 * | Efficient "have I seen this?" check      | A naive approach re-scans the whole array for every element (slow)       |
 * | Large value range (-10^9 to 10^9)        | Rules out using values directly as array indices (counting array trick)  |
 * | Early termination                        | Correct solution should stop as soon as a duplicate is found              |
 * | Balancing time vs. space                 | Fastest approach uses extra memory; frugal approach is slower            |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 * | # | Approach                    | Key Idea                                             | Best Used When                                             | Time       | Space                              |
 * |---|------------------------------|-------------------------------------------------------|--------------------------------------------------------------|------------|-------------------------------------|
 * | 1 | Brute Force (nested loops)  | Compare every pair (i, j) for equality                | n is tiny, or memory is the hard constraint                  | O(n^2)     | O(1) - space-optimal                |
 * | 2 | Sort then Scan Adjacent     | Sort the array; duplicates become adjacent            | Memory limited but O(n^2) time unacceptable; in-place sort ok | O(n log n) | O(1) in-place / O(n) if copy needed |
 * | 3 | Hash Set (one pass)         | Insert each value into a set; failed insert = dup     | General case - time is the priority                            | O(n) - time-optimal | O(n)                       |
 *
 * Row 1 wins on space (O(1), no auxiliary structures at all), and
 * row 3 wins on time (O(n), linear scan). Row 2 is a genuinely
 * distinct middle ground: it trades an O(n log n) sort for the
 * ability to detect duplicates with just a single adjacent-pair
 * scan and no hash structure. Prefer the Hash Set approach when
 * speed matters most (default for interviews/production, since n
 * can be up to 10^5). Prefer Sort-then-Scan when memory is the
 * tight constraint and mutating the input is allowed. Brute force
 * is only reasonable for teaching purposes or extremely small n.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (Nested Loops)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Loop over each index i from 0 to n-1.
 * 2. For each i, loop over every index j from i+1 to n-1.
 * 3. If nums[i] == nums[j], a duplicate exists - return true immediately.
 * 4. If the loops finish with no match found, return false.
 *
 *    public class ContainsDuplicateBruteForce {
 *
 *        public static boolean containsDuplicate(int[] nums) {
 *            int n = nums.length;
 *            for (int i = 0; i < n; i++) {
 *                for (int j = i + 1; j < n; j++) {
 *                    if (nums[i] == nums[j]) {
 *                        return true; // early exit as soon as a match is found
 *                    }
 *                }
 *            }
 *            return false;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] example = {1, 2, 3, 1};
 *            System.out.println("Contains duplicate: " + containsDuplicate(example)); // true
 *        }
 *    }
 *
 * No non-obvious formulas here - it's a direct pairwise comparison.
 * The j = i + 1 starting point avoids comparing an element with
 * itself and avoids redundant reverse comparisons.
 *
 * ------------------------------------------------------------
 * Approach 2: Sort then Scan Adjacent
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Sort the array (Arrays.sort, in place, O(n log n)).
 * 2. Walk through the sorted array from index 1 to n-1.
 * 3. Compare nums[i] with nums[i-1]. Sorted duplicates sit adjacent.
 * 4. If a match is found, return true.
 * 5. If the scan finishes with no match, return false.
 *
 *    import java.util.Arrays;
 *
 *    public class ContainsDuplicateSort {
 *
 *        public static boolean containsDuplicate(int[] nums) {
 *            Arrays.sort(nums); // O(n log n), sorts in place
 *            for (int i = 1; i < nums.length; i++) {
 *                if (nums[i] == nums[i - 1]) {
 *                    return true; // adjacent equal values after sorting = duplicate
 *                }
 *            }
 *            return false;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] example = {4, 3, 2, 7, 8, 2};
 *            System.out.println("Contains duplicate: " + containsDuplicate(example)); // true
 *        }
 *    }
 *
 * The key insight: sorting turns "search for any matching pair
 * anywhere" into "check only neighbors." Note this mutates the
 * caller's array; if that's not allowed, copy it first
 * (Arrays.copyOf), which then costs O(n) extra space.
 *
 * ------------------------------------------------------------
 * Approach 3: Hash Set (One Pass) - Optimal for Time
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Create an empty HashSet<Integer>.
 * 2. Iterate through nums once.
 * 3. For each value, try to add it to the set.
 * 4. If add() returns false (already present), return true immediately.
 * 5. If the loop finishes without any failed insertion, return false.
 *
 *    import java.util.HashSet;
 *    import java.util.Set;
 *
 *    public class ContainsDuplicateHashSet {
 *
 *        public static boolean containsDuplicate(int[] nums) {
 *            Set<Integer> seen = new HashSet<>();
 *            for (int num : nums) {
 *                if (!seen.add(num)) {
 *                    // add() returns false if the element was already present
 *                    return true;
 *                }
 *            }
 *            return false;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] example = {1, 2, 3, 1};
 *            System.out.println("Contains duplicate: " + containsDuplicate(example)); // true
 *        }
 *    }
 *
 * The non-obvious part is relying on Set.add()'s return value:
 * it returns true only if the set did NOT already contain the
 * element, letting you check-and-insert in a single call.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Approach 1: Brute Force
 * - Time: O(n^2) - for each of the n elements, up to n comparisons.
 *   Worst case (no duplicates) ~ n(n-1)/2 comparisons.
 * - Space: O(1) - no extra data structures, only loop counters.
 * - Example: n=1,000 with no duplicates -> ~499,500 comparisons.
 *   n=10,000 -> ~49,995,000 comparisons - too slow for 10^5 inputs.
 *
 * Approach 2: Sort then Scan
 * - Time: O(n log n) - dominated by the sort; adjacent scan is O(n).
 * - Space: O(1) extra if sorting in place; O(n) if copying first.
 * - Example: n=100,000 -> sort does ~100,000 x log2(100,000)
 *   ~ 100,000 x 17 ~ 1.7 million comparisons - far better than brute force.
 *
 * Approach 3: Hash Set
 * - Time: O(n) - each insertion/lookup is O(1) average, done once per element.
 * - Space: O(n) - worst case (no duplicates), every element stored.
 * - Example: n=100,000 -> exactly 100,000 constant-time operations average case.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * Input: nums = [4, 3, 2, 7, 8, 2]
 *
 * Approach 1: Brute Force
 *    i=0 (4): compare with j=1(3) no, j=2(2) no, j=3(7) no, j=4(8) no, j=5(2) no
 *    i=1 (3): compare with j=2(2) no, j=3(7) no, j=4(8) no, j=5(2) no
 *    i=2 (2): compare with j=3(7) no, j=4(8) no, j=5(2) -> MATCH -> return true
 *    Output: true
 *
 * Approach 2: Sort then Scan
 *    Before sort: [4, 3, 2, 7, 8, 2]
 *    After sort:  [2, 2, 3, 4, 7, 8]
 *    i=1: nums[1]=2, nums[0]=2 -> equal -> return true
 *    Output: true
 *
 * Approach 3: Hash Set
 *    seen = {}
 *    num=4 -> add(4) succeeds -> seen = {4}
 *    num=3 -> add(3) succeeds -> seen = {4, 3}
 *    num=2 -> add(2) succeeds -> seen = {4, 3, 2}
 *    num=7 -> add(7) succeeds -> seen = {4, 3, 2, 7}
 *    num=8 -> add(8) succeeds -> seen = {4, 3, 2, 7, 8}
 *    num=2 -> add(2) FAILS (already in seen) -> return true
 *    Output: true
 *
 * All three approaches agree on the result, confirming correctness.
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 * | Edge Case                        | Input                                    | Expected Output | How Handled                                                                 |
 * |------------------------------------|--------------------------------------------|--------------------|--------------------------------------------------------------------------------|
 * | Single element                   | [5]                                      | false            | No pair / no adjacent / set has one entry - no approach finds a duplicate    |
 * | All identical elements            | [7, 7, 7, 7]                             | true             | First comparison / first adjacent pair / second insertion triggers true      |
 * | All unique, large range           | [-1000000000, 0, 999999999]              | false            | All approaches handle negative/extreme int values correctly                  |
 * | Duplicate at the very end         | [1, 2, 3, 4, 1]                          | true             | Found regardless of original position once detected                          |
 * | Minimum size array (n=1)          | [42]                                     | false            | Loops/scans don't execute enough to find a match                             |
 * | Two elements, duplicate           | [9, 9]                                   | true             | Smallest case where a duplicate is actually possible                          |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * - Off-by-one in brute force loop bounds
 *   WRONG:   for (int j = i; j < n; j++)  // compares nums[i] with itself
 *   CORRECT: for (int j = i + 1; j < n; j++)
 *
 * - Using == instead of .equals() with boxed Integer types
 *   WRONG:   if (numsList.get(i) == numsList.get(j))  // fails outside -128..127 cache range
 *   CORRECT: Use primitive int[] (as done above) or .equals() for boxed comparisons
 *
 * - Mutating the input array unexpectedly with the sort approach
 *   WRONG:   Sorting nums in place when caller expects original order preserved
 *   CORRECT: int[] copy = Arrays.copyOf(nums, nums.length); Arrays.sort(copy);
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: If nums were null or empty, all three implementations above
 *    would throw a NullPointerException or simply skip the loop
 *    and return false. Constraints guarantee nums.length >= 1, so
 *    empty isn't a real concern here, but defensive code might
 *    still guard against null.
 *
 * Q: Are there any type mismatches?
 * A: No - all three solutions consistently use primitive int for
 *    values and int[] for the array, avoiding the Integer
 *    autoboxing/caching pitfall. HashSet<Integer> autoboxes each
 *    int, but since we only use .add() (not ==), this is safe.
 *
 * Q: How can I verify this works right now?
 *
 *    public class ContainsDuplicateVerify {
 *        public static void verify() {
 *            assert ContainsDuplicateHashSet.containsDuplicate(new int[]{1, 2, 3, 1}) == true;
 *            assert ContainsDuplicateHashSet.containsDuplicate(new int[]{1, 2, 3, 4}) == false;
 *            assert ContainsDuplicateHashSet.containsDuplicate(new int[]{1}) == false;
 *            assert ContainsDuplicateHashSet.containsDuplicate(new int[]{7, 7}) == true;
 *            assert ContainsDuplicateHashSet.containsDuplicate(new int[]{-1000000000, 0, 999999999}) == false;
 *            System.out.println("All assertions passed!");
 *        }
 *
 *        public static void main(String[] args) {
 *            verify();
 *        }
 *    }
 *
 * (Run with -ea flag enabled in the JVM for assertions to actually execute.)
 *
 * | Approach       | Risk                                                  | Mitigation                                                     |
 * |------------------|----------------------------------------------------------|----------------------------------------------------------------|
 * | Brute Force    | Times out on large inputs (n up to 10^5)              | Use only for small n or as a correctness baseline in testing    |
 * | Sort then Scan | Unexpectedly mutates caller's array                    | Copy the array first if original order must be preserved       |
 * | Hash Set       | Uses O(n) extra memory                                 | Fall back to Sort-then-Scan if memory is the binding constraint |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode 217 - Contains Duplicate - Difficulty: Easy - one of the
 * most frequently asked warm-up/screening questions, with tens of
 * thousands of reported interview appearances across platforms.
 *
 * | Company        | Frequency (stars) | Notes                                              |
 * |------------------|----------------------|-----------------------------------------------------|
 * | Amazon         | ⭐⭐⭐⭐⭐         | Common OA (online assessment) and phone-screen warm-up |
 * | Google         | ⭐⭐⭐⭐          | Often used as an easy warm-up before a harder follow-up |
 * | Microsoft      | ⭐⭐⭐⭐          | Frequently appears in early-round screens           |
 * | Meta (Facebook)| ⭐⭐⭐            | Used as a quick baseline check on hash set fluency  |
 * | Apple          | ⭐⭐⭐            | Appears in OA question banks                        |
 * | Bloomberg      | ⭐⭐⭐⭐          | Common in their OA rounds                            |
 * | Adobe          | ⭐⭐⭐            | Appears in entry-level screens                       |
 * | Uber           | ⭐⭐⭐            | Occasionally used as a rapid-fire warm-up question   |
 * | Goldman Sachs  | ⭐⭐⭐            | Common in quant/dev OA screens                       |
 * | Oracle         | ⭐⭐              | Occasionally appears in initial screens              |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 * | Approach       | Time       | Space                | Code Complexity | Recommended?                        |
 * |------------------|--------------|-------------------------|--------------------|----------------------------------------|
 * | Brute Force    | O(n^2)     | O(1)                  | Very simple      | ❌ Not recommended beyond a few thousand n |
 * | Sort then Scan | O(n log n) | O(1) in-place / O(n) | Simple           | ✅ best for low memory                 |
 * | Hash Set       | O(n)       | O(n)                  | Simple           | ✅✅ best for time                      |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use the Hash Set approach by default - it's the fastest and the
 * code is just as simple as sorting. Switch to Sort then Scan only
 * if you're in a memory-constrained environment and can tolerate
 * slightly worse time complexity.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * The pattern here is "seen-before" detection via a hash-based
 * structure, letting you turn an O(n^2) pairwise-comparison problem
 * into an O(n) single pass. Key gotcha: Set.add() in Java returns
 * false if the element already exists, so you can check-and-insert
 * in one call - no need for a separate contains() check.
 */
// @formatter:on
