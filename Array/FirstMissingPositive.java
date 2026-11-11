package Array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FirstMissingPositive {
    public static void main(String[] args) {
        FirstMissingPositive firstMissingPositive = new FirstMissingPositive();
        System.out.println(
                "FirstMissingPositive : " + firstMissingPositive.firstMissingPositiveBruteForce(new int[] { 1, 2, 0 }));
        System.out.println("FirstMissingPositive : "
                + firstMissingPositive.firstMissingPositiveBruteForce(new int[] { 3, 4, -1, 1 }));
        System.out.println("FirstMissingPositive : "
                + firstMissingPositive.firstMissingPositiveBruteForce(new int[] { 7, 8, 9, 11, 12 }));
        System.out.println("--------------------------");
        System.out.println(
                "FirstMissingPositive : " + firstMissingPositive.firstMissingPositiveSortScan(new int[] { 1, 2, 0 }));
        System.out.println("FirstMissingPositive : "
                + firstMissingPositive.firstMissingPositiveSortScan(new int[] { 3, 4, -1, 1 }));
        System.out.println("FirstMissingPositive : "
                + firstMissingPositive.firstMissingPositiveSortScan(new int[] { 7, 8, 9, 11, 12 }));
        System.out.println("--------------------------");
        System.out.println(
                "FirstMissingPositive : " + firstMissingPositive.firstMissingPositiveHashSet(new int[] { 1, 2, 0 }));
        System.out.println("FirstMissingPositive : "
                + firstMissingPositive.firstMissingPositiveHashSet(new int[] { 3, 4, -1, 1 }));
        System.out.println("FirstMissingPositive : "
                + firstMissingPositive.firstMissingPositiveHashSet(new int[] { 7, 8, 9, 11, 12 }));
        System.out.println("--------------------------");
        System.out.println(
                "FirstMissingPositive : " + firstMissingPositive.firstMissingPositiveCyclicSort(new int[] { 1, 2, 0 }));
        System.out.println("FirstMissingPositive : "
                + firstMissingPositive.firstMissingPositiveCyclicSort(new int[] { 3, 4, -1, 1 }));
        System.out.println("FirstMissingPositive : "
                + firstMissingPositive.firstMissingPositiveCyclicSort(new int[] { 7, 8, 9, 11, 12 }));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/first-missing-positive/description/
     * 
     * Given an unsorted integer array nums. Return the smallest positive integer
     * that is not present in nums.
     * 
     * You must implement an algorithm that runs in O(n) time and uses O(1)
     * auxiliary space.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,2,0]
     * Output: 3
     * Explanation: The numbers in the range [1,2] are all in the array.
     * Example 2:
     * 
     * Input: nums = [3,4,-1,1]
     * Output: 2
     * Explanation: 1 is in the array but 2 is missing.
     * Example 3:
     * 
     * Input: nums = [7,8,9,11,12]
     * Output: 1
     * Explanation: The smallest positive integer 1 is missing.
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 105
     * -231 <= nums[i] <= 231 - 1
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * | Approach     | Time       | Space     | Code Complexity | Recommended?             |
     * |--------------|------------|-----------|-----------------|--------------------------|
     * | Brute Force  | O(n^2)     | O(1)      | Trivial         | X Too slow for large n    |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int firstMissingPositiveBruteForce(int[] nums) {
        int n = nums.length;
        for (int candidate = 1; candidate <= n + 1; candidate++) {
            boolean found = false;
            for (int value : nums) {
                if (value == candidate) {
                    found = true;
                    break;
                }
            }
            if (!found)
                return candidate;
        }
        return n + 1;
    }

    // @formatter:off
    /**
     * | Approach     | Time       | Space     | Code Complexity | Recommended?             |
     * |--------------|------------|-----------|-----------------|--------------------------|
     * | Sort + Scan  | O(n log n) | O(1)–O(n) | Low             | X Violates O(n)-time req   |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int firstMissingPositiveSortScan(int[] nums) {
        Arrays.sort(nums);
        int nextExpected = 1;
        for (int value : nums) {
            if (value == nextExpected)
                nextExpected++;
            else if (value > nextExpected)
                break;
        }
        return nextExpected;
    }

    // @formatter:off
    /**
     * | Approach     | Time       | Space     | Code Complexity | Recommended?             |
     * |--------------|------------|-----------|-----------------|--------------------------|
     * | Hash Set     | O(n)       | O(n)      | Low             | OK only if memory is free |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int firstMissingPositiveHashSet(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            seen.add(num);
        }
        int candidate = 1;
        while (seen.contains(candidate))
            candidate++;
        return candidate;
    }

    // @formatter:off
    /**
     * | Approach     | Time       | Space     | Code Complexity | Recommended?             |
     * |--------------|------------|-----------|-----------------|--------------------------|
     * | Cyclic Sort  | O(n)       | O(1)      | Medium          | BEST — meets both limits  |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int firstMissingPositiveCyclicSort(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                int homeIndex = nums[i] - 1;
                int temp = nums[homeIndex];
                nums[homeIndex] = nums[i];
                nums[i] = temp;
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1)
                return i + 1;
        }
        return n + 1;
    }
}

// @formatter:off
/*
 * ============================================================
 * FIRST MISSING POSITIVE — DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an unsorted array of integers, find the smallest positive integer
 * (starting from 1) that does NOT appear in the array. The twist that makes
 * this a Hard problem: you must do it in O(n) time and use only O(1) auxiliary
 * space (constant extra memory, ignoring the input array itself).
 *
 * This is LeetCode #41 — First Missing Positive (Difficulty: Hard).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * A single integer array int[] nums.
 * Values can be negative, zero, positive, or duplicated, in no particular order.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * A single int — the smallest missing positive integer.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * 1 <= nums.length <= 10^5
 * -2^31 <= nums[i] <= 2^31 - 1  (full 32-bit int range)
 * Required: O(n) time, O(1) auxiliary space.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * The answer is always in the range [1, n+1] where n = nums.length. If the
 * array of length n contains all of 1..n, the answer is n+1. Otherwise at least
 * one of 1..n is missing, and the smallest such one is the answer. Negatives,
 * zeros, and values > n are all irrelevant.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 * | Input          | Output | Reasoning                       |
 * |----------------|--------|---------------------------------|
 * | [1, 2, 0]      | 3      | 1 and 2 present, 3 missing       |
 * | [3, 4, -1, 1]  | 2      | 1 present, 2 missing             |
 * | [7, 8, 9, 11]  | 1      | No small positives → 1 missing   |
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Imagine a coat-check room with n numbered hooks (1..n). Every guest whose
 * ticket number is between 1 and n must hang their coat on the matching hook.
 * Guests with junk tickets (negative, zero, or > n) are ignored. Afterward you
 * walk down the hooks: the first empty hook is your answer. If all hooks are
 * full, the answer is n+1.
 *
 * The array IS the coat-check room. Value v belongs at index v - 1. This is the
 * cyclic sort / index-as-hash technique — the array's own indices become a hash
 * table, which is how we avoid extra space.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. The answer can only be in 1..n+1, so anything outside is noise.
 * 2. If I could instantly check "is k present?", I'd return the first failing k.
 * 3. A hash set gives O(1) checks — but O(n) space.
 * 4. Trick: valid values are bounded by n, so store presence inside the array by
 *    placing value v at position v - 1.
 * 5. After placing, index i should hold i + 1. First violation = the answer.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                     | Why it's tricky                                             |
 * |-------------------------------|-------------------------------------------------------------|
 * | O(1) space requirement        | Rules out hash-set / boolean-array; must reuse the input    |
 * | Junk values                   | Negatives, zeros, huge numbers must be ignored cleanly      |
 * | Duplicates                    | Swapping can loop forever without a "home already correct" check |
 * | Full 32-bit range             | MAX_VALUE / negatives can cause OOB or overflow if misused  |
 * | The n+1 case                  | A complete [1..n] array still has an answer (n+1)           |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 * | # | Approach                         | Key Idea                                   | Best Used When                 | Time       | Space              |
 * |---|----------------------------------|--------------------------------------------|--------------------------------|------------|--------------------|
 * | 1 | Brute Force (scan per k)         | For k=1,2,..., scan array for presence     | Tiny arrays; clarity           | O(n^2)     | O(1) [space-opt]   |
 * | 2 | Sort then Scan                   | Sort, walk tracking next expected positive | Sorting acceptable; mutable    | O(n log n) | O(1)–O(n)          |
 * | 3 | Hash Set                         | Dump values in set, probe 1,2,3,...        | Extra memory is free           | O(n) [time-opt]| O(n)           |
 * | 4 | Cyclic Sort / Index Placement    | Place value v at index v-1, find mismatch  | THE intended solution          | O(n) [best]| O(1) [best]        |
 *
 * Approaches 1–3 each win on one axis but violate the other requirement: brute
 * force is space-cheap but O(n^2); sorting is neither truly O(n) nor guaranteed
 * O(1) space; the hash set is O(n) time but O(n) space. Approach 4 is the only
 * one that is simultaneously O(n) time and O(1) space — the uniquely correct
 * answer for this Hard problem. Prefer the hash set only when memory is truly
 * unconstrained; otherwise Approach 4 dominates.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (Linear Scan per Candidate)
 * ------------------------------------------------------------
 * 1. For each candidate k from 1 to n+1:
 * 2. Scan the whole array to see if k is present.
 * 3. Return the first k not found.
 *
 *    public class FirstMissingPositiveBruteForce {
 *        public int firstMissingPositive(int[] nums) {
 *            int n = nums.length;
 *            for (int candidate = 1; candidate <= n + 1; candidate++) {
 *                boolean found = false;
 *                for (int value : nums) {
 *                    if (value == candidate) { found = true; break; }
 *                }
 *                if (!found) return candidate; // first gap wins
 *            }
 *            return n + 1; // unreachable
 *        }
 *        public static void main(String[] args) {
 *            FirstMissingPositiveBruteForce solver = new FirstMissingPositiveBruteForce();
 *            System.out.println(solver.firstMissingPositive(new int[]{3, 4, -1, 1})); // 2
 *        }
 *    }
 *
 * Outer loop runs at most n+1 times, each with an O(n) scan → O(n^2).
 *
 * ------------------------------------------------------------
 * Approach 2: Sort then Scan
 * ------------------------------------------------------------
 * 1. Sort ascending.
 * 2. Walk through with nextExpected = 1.
 * 3. Skip non-positives and duplicates; on exact match, increment target.
 * 4. When scan ends, nextExpected is the answer.
 *
 *    import java.util.Arrays;
 *    public class FirstMissingPositiveSort {
 *        public int firstMissingPositive(int[] nums) {
 *            Arrays.sort(nums);
 *            int nextExpected = 1;
 *            for (int value : nums) {
 *                if (value == nextExpected) nextExpected++;
 *                else if (value > nextExpected) break; // gap found
 *                // value < nextExpected → ignore
 *            }
 *            return nextExpected;
 *        }
 *        public static void main(String[] args) {
 *            FirstMissingPositiveSort solver = new FirstMissingPositiveSort();
 *            System.out.println(solver.firstMissingPositive(new int[]{7, 8, 9, 11, 12})); // 1
 *        }
 *    }
 *
 * Once a value exceeds nextExpected, the gap is confirmed → stop early.
 *
 * ------------------------------------------------------------
 * Approach 3: Hash Set
 * ------------------------------------------------------------
 * 1. Insert every value into a HashSet.
 * 2. Probe k=1,2,3,... and return the first k absent.
 *
 *    import java.util.HashSet;
 *    import java.util.Set;
 *    public class FirstMissingPositiveHashSet {
 *        public int firstMissingPositive(int[] nums) {
 *            Set<Integer> present = new HashSet<>();
 *            for (int value : nums) present.add(value);
 *            int candidate = 1;
 *            while (present.contains(candidate)) candidate++;
 *            return candidate;
 *        }
 *        public static void main(String[] args) {
 *            FirstMissingPositiveHashSet solver = new FirstMissingPositiveHashSet();
 *            System.out.println(solver.firstMissingPositive(new int[]{1, 2, 0})); // 3
 *        }
 *    }
 *
 * The probe loop runs at most n+1 times.
 *
 * ------------------------------------------------------------
 * Approach 4: Cyclic Sort / Index Placement  [OPTIMAL]
 * ------------------------------------------------------------
 * 1. Iterate index i from 0 to n-1.
 * 2. While nums[i] is a valid target (1..n) AND not already home
 *    (nums[nums[i]-1] != nums[i]), swap it to index nums[i]-1.
 * 3. The second condition places the value AND terminates on duplicates.
 * 4. Scan: first index i where nums[i] != i+1 → answer i+1.
 * 5. If all match, answer is n+1.
 *
 *    public class FirstMissingPositiveCyclic {
 *        public int firstMissingPositive(int[] nums) {
 *            int n = nums.length;
 *            for (int i = 0; i < n; i++) {
 *                while (nums[i] > 0 && nums[i] <= n
 *                       && nums[nums[i] - 1] != nums[i]) {
 *                    int homeIndex = nums[i] - 1;
 *                    int temp = nums[homeIndex];
 *                    nums[homeIndex] = nums[i];
 *                    nums[i] = temp;
 *                }
 *            }
 *            for (int i = 0; i < n; i++) {
 *                if (nums[i] != i + 1) return i + 1;
 *            }
 *            return n + 1;
 *        }
 *        public static void main(String[] args) {
 *            FirstMissingPositiveCyclic solver = new FirstMissingPositiveCyclic();
 *            System.out.println(solver.firstMissingPositive(new int[]{3, 4, -1, 1})); // 2
 *            System.out.println(solver.firstMissingPositive(new int[]{1, 2, 0}));     // 3
 *            System.out.println(solver.firstMissingPositive(new int[]{7, 8, 9, 11})); // 1
 *        }
 *    }
 *
 * Capture homeIndex into a temp first because the swap mutates nums[i]. Use the
 * nums[nums[i]-1] != nums[i] guard (not nums[i] != i+1) so duplicates stop
 * immediately instead of swapping forever.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Approach 1 — Brute Force
 *   Time: O(n^2). (n+1) candidates x O(n) scan. n=10^5 → ~10^10 ops (too slow).
 *   Space: O(1). Few scalars.
 *
 * Approach 2 — Sort then Scan
 *   Time: O(n log n). Sort dominates; scan O(n). n=10^5 → ~1.7x10^6 comparisons.
 *   Space: O(1)–O(n). Primitive dual-pivot quicksort is near in-place.
 *
 * Approach 3 — Hash Set
 *   Time: O(n). Build set + at most n+1 probes. n=10^5 → ~2x10^5 ops.
 *   Space: O(n). Stores up to n boxed Integers — fails the constraint.
 *
 * Approach 4 — Cyclic Sort  [OPTIMAL]
 *   Time: O(n). Each swap permanently homes one value; total swaps <= n → O(n)
 *     amortized, plus O(n) scan. n=10^5 → ~2–3x10^5 ops.
 *   Space: O(1). Fully in-place; only scalar temporaries.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * Approach 1 — Brute Force on [3, 4, -1, 1]
 *   k=1 → found (index 3). advance.
 *   k=2 → not found → return 2.
 *
 * Approach 2 — Sort then Scan on [3, 4, -1, 1]
 *   sorted: [-1, 1, 3, 4], nextExpected=1
 *   -1 (<1) ignore; 1 (==1) → nextExpected=2; 3 (>2) break → return 2.
 *
 * Approach 3 — Hash Set on [3, 4, -1, 1]
 *   set = {3,4,-1,1}; probe 1 present; probe 2 absent → return 2.
 *
 * Approach 4 — Cyclic Sort on [3, 4, -1, 1] (n=4)
 *   Start: [3, 4, -1, 1]
 *   i=0: nums[0]=3, home=2, nums[2]=-1 != 3 → swap(0,2)
 *        [-1, 4, 3, 1]; nums[0]=-1 not positive → stop
 *   i=1: nums[1]=4, home=3, nums[3]=1 != 4 → swap(1,3)
 *        [-1, 1, 3, 4]; nums[1]=1, home=0, nums[0]=-1 != 1 → swap(1,0)
 *        [1, -1, 3, 4]; nums[1]=-1 not positive → stop
 *   i=2: nums[2]=3 already home → stop
 *   i=3: nums[3]=4 already home → stop
 *   After Phase 1: [1, -1, 3, 4]
 *   Scan: i=0 → 1==1 ok; i=1 → -1 != 2 → return 2.
 *   Output: 2  (consistent with every approach)
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 * | Edge Case                | Input                | Expected | How Handled                                     |
 * |--------------------------|----------------------|----------|-------------------------------------------------|
 * | All negatives / zeros    | [-3, -1, 0]          | 1        | Nothing placed; index 0 != 1 → returns 1        |
 * | Perfect permutation 1..n | [1, 2, 3]            | 4        | All slots match; loop completes → n+1           |
 * | Single element = 1       | [1]                  | 2        | Slot 0 holds 1 → n+1 = 2                         |
 * | Single element != 1      | [5]                  | 1        | 5 > n=1 ignored; slot 0 != 1 → 1                 |
 * | Duplicates               | [1, 1, 2, 2]         | 3        | Duplicate stops via home-equals guard → 3       |
 * | Values far above n       | [7, 8, 9, 11, 12]    | 1        | All > n, none placed; slot 0 != 1 → 1           |
 * | Contains MAX_VALUE       | [2147483647, 1]      | 2        | MAX_VALUE > n ignored; 1 present → 2            |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 — Infinite loop on duplicates:
 *    // WRONG — loops forever on [1, 1]
 *    while (nums[i] > 0 && nums[i] <= n) { swap(i, nums[i]-1); }
 *    // CORRECT — stop when home already holds this value
 *    while (nums[i] > 0 && nums[i] <= n && nums[nums[i]-1] != nums[i]) { ... }
 *
 * Pitfall 2 — Reading nums[i] after mutating it mid-swap:
 *    // WRONG
 *    int t = nums[nums[i]-1];
 *    nums[nums[i]-1] = nums[i];   // index now wrong
 *    nums[i] = t;
 *    // CORRECT — capture home index BEFORE touching anything
 *    int home = nums[i] - 1;
 *    int t = nums[home];
 *    nums[home] = nums[i];
 *    nums[i] = t;
 *
 * Pitfall 3 — Forgetting the n+1 return for a complete [1..n] array.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 * Q: What edge cases might this miss?
 * A: Duplicates (infinite loop), MAX_VALUE/MIN_VALUE (OOB if range guard dropped),
 *    and the complete-permutation case (must return n+1). The three guards >0,
 *    <=n, and nums[home] != nums[i] neutralize all three.
 *
 * Q: Are there any type mismatches?
 * A: No. All int. nums[i]-1 is only computed when 1 <= nums[i] <= n, so it stays
 *    a valid index; no overflow-prone additions.
 *
 * Q: How can I verify this works right now?
 *    public class FirstMissingPositiveVerify {
 *        public int firstMissingPositive(int[] nums) {
 *            int n = nums.length;
 *            for (int i = 0; i < n; i++) {
 *                while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
 *                    int home = nums[i] - 1;
 *                    int temp = nums[home];
 *                    nums[home] = nums[i];
 *                    nums[i] = temp;
 *                }
 *            }
 *            for (int i = 0; i < n; i++) if (nums[i] != i + 1) return i + 1;
 *            return n + 1;
 *        }
 *        public static void verify() {
 *            FirstMissingPositiveVerify s = new FirstMissingPositiveVerify();
 *            assert s.firstMissingPositive(new int[]{1, 2, 0}) == 3;
 *            assert s.firstMissingPositive(new int[]{3, 4, -1, 1}) == 2;
 *            assert s.firstMissingPositive(new int[]{7, 8, 9, 11, 12}) == 1;
 *            assert s.firstMissingPositive(new int[]{1, 1, 2, 2}) == 3;
 *            assert s.firstMissingPositive(new int[]{1}) == 2;
 *            assert s.firstMissingPositive(new int[]{5}) == 1;
 *            assert s.firstMissingPositive(new int[]{2147483647, 1}) == 2;
 *            System.out.println("All assertions passed.");
 *        }
 *        public static void main(String[] args) { verify(); }
 *    }
 * Run with the -ea flag to enable assertions.
 *
 * | Approach     | Risk                          | Mitigation                          |
 * |--------------|-------------------------------|-------------------------------------|
 * | Brute Force  | Too slow (O(n^2)) for 10^5    | Use only for tiny inputs            |
 * | Sort         | Not truly O(n); mutates order | Use only if constraint relaxed      |
 * | Hash Set     | O(n) space fails requirement  | Use only when memory is free        |
 * | Cyclic Sort  | Infinite loop / OOB if guard dropped | Keep all three while-conditions |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #41 · Difficulty: Hard · ~1,000+ reported interview appearances.
 *
 * | Company        | Frequency | Notes                                   |
 * |----------------|-----------|-----------------------------------------|
 * | Amazon         | *****     | Very common array/in-place favorite      |
 * | Google         | *****     | Loves the O(1)-space discussion          |
 * | Microsoft      | ****      | Frequent onsite / phone-screen           |
 * | Meta           | ****      | Tests index-as-hash insight              |
 * | Apple          | ***       | Appears in array rounds                  |
 * | Bloomberg      | ****      | Popular for its trickiness               |
 * | Adobe          | ***       | Medium-frequency                         |
 * | Uber           | ***       | Appears in coding rounds                 |
 * | Salesforce     | **        | Occasionally                             |
 * | Goldman Sachs  | **        | Occasionally, quant/eng roles            |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 * | Approach     | Time       | Space     | Code Complexity | Recommended?             |
 * |--------------|------------|-----------|-----------------|--------------------------|
 * | Brute Force  | O(n^2)     | O(1)      | Trivial         | X Too slow for large n    |
 * | Sort + Scan  | O(n log n) | O(1)–O(n) | Low             | X Violates O(n)-time req   |
 * | Hash Set     | O(n)       | O(n)      | Low             | OK only if memory is free |
 * | Cyclic Sort  | O(n)       | O(1)      | Medium          | BEST — meets both limits  |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use Approach 4 (Cyclic Sort / Index Placement). It is the only solution that
 * is simultaneously O(n) time and O(1) space — exactly the pair of constraints
 * the problem demands — so time-optimal and space-optimal coincide here.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * When values are bounded by the array length and you need O(1) space, use the
 * array's own indices as a hash table — value v belongs at index v-1. Memorize
 * the three-part swap guard: in range (>0, <=n) AND not already home
 * (nums[nums[i]-1] != nums[i]); that last condition prevents the infinite loop
 * on duplicates. After placement, the first index i where nums[i] != i+1 is the
 * answer, defaulting to n+1.
 */
// @formatter:on
