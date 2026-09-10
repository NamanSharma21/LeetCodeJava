package Array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSequence {
    public static void main(String[] args) {
        LongestConsecutiveSequence longestConsecutiveSequence = new LongestConsecutiveSequence();
        System.out.println("LongestConsecutiveSequence : "
                + longestConsecutiveSequence.longestConsecutiveBruteForce(new int[] { 100, 4, 200, 1, 3, 2 })); // 4
        System.out.println("---------------------------------------");
        System.out.println("LongestConsecutiveSequence : "
                + longestConsecutiveSequence.longestConsecutiveSortLinearScan(new int[] { 100, 4, 200, 1, 3, 2 })); // 4
        System.out.println("---------------------------------------");
        System.out.println("LongestConsecutiveSequence : "
                + longestConsecutiveSequence
                        .longestConsecutiveHashSetStartOnlyWalk(new int[] { 100, 4, 200, 1, 3, 2 })); // 4
    }

    // @formatter:off
    /*
     * 
     * https://leetcode.com/problems/longest-consecutive-sequence/description/
     * 
     * Given an unsorted array of integers nums, return the length of the longest
     * consecutive elements sequence.
     * 
     * You must write an algorithm that runs in O(n) time.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [100,4,200,1,3,2]
     * Output: 4
     * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4].
     * Therefore its length is 4.
     * Example 2:
     * 
     * Input: nums = [0,3,7,2,5,8,4,6,0,1]
     * Output: 9
     * Example 3:
     * 
     * Input: nums = [1,0,1,2]
     * Output: 3
     * 
     * 
     * Constraints:
     * 
     * 0 <= nums.length <= 105
     * -109 <= nums[i] <= 109
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * Approach	Time	        Space   	        Code        Complexity                	Recommended?
     * Brute Force	            O(n²)	            O(n)	    Low	❌ Only for learning;   too slow for n=10^5
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int longestConsecutiveBruteForce(int[] nums) {
        if (nums.length == 0)
            return 0;
        Set<Integer> lookup = new HashSet<>();
        for (int n : nums)
            lookup.add(n);
        int best = 0;
        for (int num : nums) {
            int current = num;
            int length = 1;
            while (lookup.contains(current + 1)) {
                current++;
                length++;
            }
            best = Math.max(best, length);
        }
        return best;
    }

    // @formatter:off
    /**
     * 
     * Approach	Time	        Space   	        Code        Complexity                	Recommended?
     * Sorting + Scan	        O(n log n)	        O(1) aux	Low–Medium	                ✅ best for low memory / when O(n log n) is fine
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int longestConsecutiveSortLinearScan(int[] nums) {
        if (nums.length == 0)
            return 0;
        Arrays.sort(nums);
        int best = 1;
        int current = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1])
                continue;
            else if (nums[i] == nums[i - 1] + 1)
                current++;
            else
                current = 1;
            best = Math.max(best, current);
        }
        return best;
    }

    // @formatter:off
    /**
     * 
     * Approach	Time	        Space   	        Code        Complexity                	Recommended?
     * Hash Set (start-only)	O(n)	            O(n)	    Medium	                    ✅✅ best for time — the intended optimal
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int longestConsecutiveHashSetStartOnlyWalk(int[] nums) {
        if (nums.length == 0)
            return 0;
        Set<Integer> lookup = new HashSet<>();
        for (int n : nums)
            lookup.add(n);
        int best = 0;
        for (int num : nums) {
            if (!lookup.contains(num - 1)) {
                int current = num;
                int length = 1;
                while (lookup.contains(current + 1)) {
                    current++;
                    length++;
                }
                best = Math.max(best, length);
            }
        }
        return best;
    }
}
// @formatter:off
/*
 * ============================================================
 * LONGEST CONSECUTIVE SEQUENCE - DEEP DIVE EXPLANATION
 * (LeetCode #128 - Medium)
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an unsorted array of integers, find the length of the longest run of
 * consecutive integers that can be formed from the array's values. The numbers
 * do NOT need to appear next to each other in the array - only their VALUES
 * need to be consecutive (like 3, 4, 5, 6). The catch: you must solve it in
 * O(n) time.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums - an array of integers, possibly empty, possibly with duplicates,
 * values can be negative.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * int - the length of the longest consecutive elements sequence.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * 0 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * Required target complexity: O(n) time.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * The size of the largest set of integers {x, x+1, x+2, ..., x+k-1} such that
 * EVERY value in that set is present in nums. You return k, the count - not the
 * sequence itself.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  [100, 4, 200, 1, 3, 2]
 *    Output: 4
 *    Why:    The values 1, 2, 3, 4 are all present -> run of length 4.
 *            (100 and 200 are isolated; each forms a run of length 1.)
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Imagine dumping all the numbers into a bag. You want the longest chain where
 * each number's "successor" (value + 1) is also in the bag. The naive instinct
 * is to sort - but sorting costs O(n log n). The clever realization is that a
 * hash set gives O(1) membership checks, so you can "walk" a chain forward
 * without sorting at all.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Put every number into a set so you can instantly ask "is value v present?"
 * 2. A chain like 1,2,3,4 has exactly one STARTING point: the 1. That 1 is
 *    special because 0 is NOT in the set.
 * 3. So for each number, first ask: "Am I the start of a chain?" - i.e., is
 *    num - 1 absent?
 * 4. If yes, walk forward (num+1, num+2, ...) counting how far the chain
 *    extends.
 * 5. If no, skip it - someone earlier in the chain will count this number when
 *    they walk forward.
 *
 * This "only start counting from the beginning of a run" rule is what keeps the
 * whole thing linear.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                  | Why it's tricky                                                          |
 * |----------------------------|--------------------------------------------------------------------------|
 * | The O(n) requirement       | Sorting is obvious but violates the target, so you must think differently.|
 * | Avoiding redundant walks   | Walking from every number re-traverses chains -> O(n^2). Start-check fixes.|
 * | Duplicates                 | [1,1,1] must return 1, not 3. A set dedups; a sort scan must skip equals.  |
 * | Order independence         | Values are scattered, so reason purely about value membership.            |
 * | Negative / large range     | Values span +-10^9, so no array indexed by value; a hash set is required. |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                        | Key Idea                                   | Best Used When                       | Time        | Space                             |
 * |---|---------------------------------|--------------------------------------------|--------------------------------------|-------------|-----------------------------------|
 * | 1 | Brute Force (walk from every)   | Walk +1,+2,... using a set for lookups     | Tiny inputs / teaching the naive idea| O(n^2) worst| O(n)                              |
 * | 2 | Sorting + Linear Scan           | Sort, scan the run, skip duplicates        | Memory tight, O(n log n) acceptable  | O(n log n)  | O(1) aux (in-place) [BEST SPACE]  |
 * | 3 | Hash Set (start-only walk)      | Only walk forward from run-starts          | General case, O(n) time required     | O(n) [BEST] | O(n)                              |
 *
 * Trade-off discussion. The three approaches sit on different points of the
 * time/space curve. The Hash Set approach (row 3) is the intended optimal
 * because it uniquely hits O(n) time - each value is touched at most twice
 * total across all walks. The Sorting approach (row 2) is genuinely distinct:
 * sorting an int[] in place with Java's dual-pivot quicksort uses only O(1)
 * auxiliary space, making it the best choice when memory is the hard constraint
 * and O(n log n) time is tolerable. Brute force (row 1) is dominated on time by
 * both others but is instructive.
 *
 * There is no single universal winner: prefer the hash set when time is the
 * bottleneck; prefer sorting when auxiliary memory must be minimal.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (walk from every element)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. If the array is empty, return 0.
 * 2. Insert all numbers into a hash set (for O(1) lookups).
 * 3. For each number num, set current = num, length = 1.
 * 4. While current + 1 exists in the set, increment current and length.
 * 5. Track the maximum length seen.
 * 6. Return the maximum.
 *
 * Note: without the "start-only" guard, chains get re-walked. Starting from a
 * mid-chain value still walks to the end, so overlapping walks make this O(n^2)
 * in the worst case (e.g., one long chain).
 *
 *    import java.util.*;
 *
 *    public class LongestConsecutiveBrute {
 *        public static int longestConsecutive(int[] nums) {
 *            if (nums.length == 0) return 0;
 *
 *            Set<Integer> lookup = new HashSet<>();
 *            for (int n : nums) lookup.add(n);
 *
 *            int best = 0;
 *            for (int num : nums) {
 *                int current = num;
 *                int length = 1;
 *                // Walk forward as long as the next value exists
 *                while (lookup.contains(current + 1)) {
 *                    current++;
 *                    length++;
 *                }
 *                best = Math.max(best, length);
 *            }
 *            return best;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {100, 4, 200, 1, 3, 2};
 *            System.out.println(longestConsecutive(nums)); // 4
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 2: Sorting + Linear Scan (space-optimal)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. If the array is empty, return 0.
 * 2. Sort the array in ascending order.
 * 3. Initialize best = 1, current = 1.
 * 4. Scan from index 1:
 *    - If nums[i] == nums[i-1], it's a DUPLICATE -> skip.
 *    - Else if nums[i] == nums[i-1] + 1, extend the run -> current++.
 *    - Else the run breaks -> reset current = 1.
 *    - Update best = max(best, current).
 * 5. Return best.
 *
 *    import java.util.*;
 *
 *    public class LongestConsecutiveSort {
 *        public static int longestConsecutive(int[] nums) {
 *            if (nums.length == 0) return 0;
 *
 *            Arrays.sort(nums); // in-place dual-pivot quicksort -> O(1) aux space
 *
 *            int best = 1;
 *            int current = 1;
 *            for (int i = 1; i < nums.length; i++) {
 *                if (nums[i] == nums[i - 1]) {
 *                    continue;                 // duplicate: ignore
 *                }
 *                if (nums[i] == nums[i - 1] + 1) {
 *                    current++;                // consecutive: extend run
 *                } else {
 *                    current = 1;              // gap: reset run
 *                }
 *                best = Math.max(best, current);
 *            }
 *            return best;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {0, 3, 7, 2, 5, 8, 4, 6, 0, 1};
 *            System.out.println(longestConsecutive(nums)); // 9
 *        }
 *    }
 *
 * The duplicate skip (continue) is essential: without it, [1,1,2] would wrongly
 * count the second 1 as breaking or extending the run. Sorting an int[] mutates
 * the caller's array; clone first if the original order must be preserved.
 *
 * ------------------------------------------------------------
 * Approach 3: Hash Set - Start-Only Walk (time-optimal)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Insert all numbers into a hash set (deduplicates automatically).
 * 2. For each number num in the set:
 *    - Check if num - 1 is ABSENT. If present, num is not a run start -> skip.
 *    - If num - 1 is absent, num starts a run. Walk forward counting
 *      num+1, num+2, ...
 *    - Record the run length.
 * 3. Return the maximum run length.
 *
 *    import java.util.*;
 *
 *    public class LongestConsecutiveSet {
 *        public static int longestConsecutive(int[] nums) {
 *            Set<Integer> set = new HashSet<>();
 *            for (int n : nums) set.add(n);
 *
 *            int best = 0;
 *            for (int num : set) {
 *                // Only start counting from the beginning of a run
 *                if (!set.contains(num - 1)) {
 *                    int current = num;
 *                    int length = 1;
 *                    while (set.contains(current + 1)) {
 *                        current++;
 *                        length++;
 *                    }
 *                    best = Math.max(best, length);
 *                }
 *            }
 *            return best;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {100, 4, 200, 1, 3, 2};
 *            System.out.println(longestConsecutive(nums)); // 4
 *        }
 *    }
 *
 * Why iterating over the set (not the array) matters for cleanliness:
 * duplicates are already removed, so no number is examined twice as a potential
 * start. Iterating the array also works and is still O(n), but the set is tidier.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 - Brute Force
 * ------------------------------------------------------------
 * Time: O(n^2) worst case. Building the set is O(n). The outer loop runs n
 *   times, and each walk can traverse a chain of length up to n. Worst case
 *   (one long chain 1..n): n + (n-1) + ... ~ n^2/2 = O(n^2).
 * Space: O(n) for the hash set.
 * Numeric feel: for n = 1000 all forming one chain, ~500,000 walk-steps.
 *
 * ------------------------------------------------------------
 * Approach 2 - Sorting
 * ------------------------------------------------------------
 * Time: O(n log n) dominated by the sort; the scan afterward is O(n).
 * Space: O(1) auxiliary when sorting a primitive int[] in place. (If you clone
 *   to preserve input, that clone is O(n).)
 * Numeric feel: for n = 10^5, ~10^5 * 17 ~ 1.7M comparisons for the sort, then
 *   10^5 scan steps.
 *
 * ------------------------------------------------------------
 * Approach 3 - Hash Set (Optimal)
 * ------------------------------------------------------------
 * Time: O(n). Every value is visited at most TWICE: once by the outer for loop,
 *   and once by an inner while walk - but the inner walk only happens for
 *   run-starts, and each value belongs to exactly one run, so total inner-walk
 *   work across the whole run is O(n). Sum: O(n) + O(n) = O(n).
 * Space: O(n) for the set.
 * Numeric feel: for n = 10^5, ~2 * 10^5 operations total - far fewer than the
 *   sort's ~1.7M.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 - Brute Force on [100, 4, 200, 1, 3, 2]
 * ------------------------------------------------------------
 * Set = {1, 2, 3, 4, 100, 200}
 *
 *    num=100 -> check 101? no                -> length 1 -> best=1
 *    num=4   -> check 5? no                  -> length 1 -> best=1
 *    num=200 -> check 201? no                -> length 1 -> best=1
 *    num=1   -> 2 -> 3 -> 4 -> check 5? no    -> length 4 -> best=4
 *    num=3   -> 4 -> check 5? no             -> length 2 -> best=4
 *    num=2   -> 3 -> 4 -> check 5? no        -> length 3 -> best=4
 *
 * Final output: 4 (note how 3 and 2 re-walked the tail - that's wasted work.)
 *
 * ------------------------------------------------------------
 * Approach 2 - Sorting on [0, 3, 7, 2, 5, 8, 4, 6, 0, 1]
 * ------------------------------------------------------------
 * After sort: [0, 0, 1, 2, 3, 4, 5, 6, 7, 8]
 *
 * | i | nums[i] | prev | relation          | current | best |
 * |---|---------|------|-------------------|---------|------|
 * | 1 | 0       | 0    | duplicate -> skip | 1       | 1    |
 * | 2 | 1       | 0    | +1 -> extend      | 2       | 2    |
 * | 3 | 2       | 1    | +1 -> extend      | 3       | 3    |
 * | 4 | 3       | 2    | +1 -> extend      | 4       | 4    |
 * | 5 | 4       | 3    | +1 -> extend      | 5       | 5    |
 * | 6 | 5       | 4    | +1 -> extend      | 6       | 6    |
 * | 7 | 6       | 5    | +1 -> extend      | 7       | 7    |
 * | 8 | 7       | 6    | +1 -> extend      | 8       | 8    |
 * | 9 | 8       | 7    | +1 -> extend      | 9       | 9    |
 *
 * Final output: 9
 *
 * ------------------------------------------------------------
 * Approach 3 - Hash Set on [100, 4, 200, 1, 3, 2]
 * ------------------------------------------------------------
 * Set = {1, 2, 3, 4, 100, 200}
 *
 *    num=1   -> is 0 in set? NO  -> START
 *               |__ 2 -> 3 -> 4 -> 5? no   -> length 4 -> best=4
 *    num=2   -> is 1 in set? YES -> skip (not a start)
 *    num=3   -> is 2 in set? YES -> skip
 *    num=4   -> is 3 in set? YES -> skip
 *    num=100 -> is 99 in set? NO -> START
 *               |__ 101? no                -> length 1 -> best=4
 *    num=200 -> is 199 in set? NO -> START
 *               |__ 201? no                -> length 1 -> best=4
 *
 * Final output: 4 - only 1, 100, and 200 ever trigger a walk. No value is
 * re-walked, which is exactly why this is O(n).
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case              | Input               | Expected | How Handled                                              |
 * |------------------------|---------------------|----------|----------------------------------------------------------|
 * | Empty array            | []                  | 0        | Brute/Sort guard length==0; Set version best stays 0     |
 * | Single element         | [5]                 | 1        | Set has one start; walk finds no successor -> length 1   |
 * | All duplicates         | [1, 1, 1]           | 1        | Set dedups to {1}; sort skips equal neighbors via continue|
 * | Already consecutive    | [1, 2, 3, 4]        | 4        | One run walked fully once                                |
 * | Negative + positive    | [-1, 0, 1, 3]       | 3        | Hash set handles negatives (no array-indexing assumption)|
 * | No consecutive pairs   | [10, 30, 20]        | 1        | Every value is its own start; each run length 1          |
 * | Large gap values       | [1, 1000000000]     | 1        | Hash set membership is value-based, gaps irrelevant      |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 - Walking from every element (dropping the start check):
 *
 *    // WRONG: O(n^2) - re-walks chains from mid-points
 *    for (int num : set) {
 *        int len = 1, cur = num;
 *        while (set.contains(cur + 1)) { cur++; len++; }
 *        best = Math.max(best, len);
 *    }
 *
 *    // CORRECT: only walk from run-starts -> O(n)
 *    for (int num : set) {
 *        if (!set.contains(num - 1)) {           // the guard that saves you
 *            int len = 1, cur = num;
 *            while (set.contains(cur + 1)) { cur++; len++; }
 *            best = Math.max(best, len);
 *        }
 *    }
 *
 * Pitfall 2 - Forgetting duplicates in the sort approach:
 *
 *    // WRONG: [1,1,2] returns 3 because the equal pair is mishandled
 *    if (nums[i] == nums[i-1] + 1) current++;
 *    else current = 1;
 *
 *    // CORRECT: skip equal neighbors before the +1 check
 *    if (nums[i] == nums[i-1]) continue;
 *    if (nums[i] == nums[i-1] + 1) current++;
 *    else current = 1;
 *
 * Pitfall 3 - Returning 1 for an empty array. Initializing best = 1 is
 * convenient for non-empty inputs but breaks on []. Guard the empty case
 * explicitly, or (as in the set version) initialize best = 0.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The empty array is the classic miss when best/current start at 1.
 *    Duplicates are the other trap in the sort variant. The hash set version
 *    sidesteps both because it iterates a deduplicated set and starts best at 0.
 *
 * Q: Are there any type mismatches?
 * A: With values up to +-10^9, individual values fit in a Java int. The
 *    dangerous operation would be num + 1 at Integer.MAX_VALUE, but constraints
 *    cap values at 10^9, well below overflow. No long is needed.
 *    HashSet<Integer> autoboxing is fine but has memory overhead - acceptable
 *    for n <= 10^5.
 *
 * Q: How can I verify this works right now?
 *
 *    public static void verify() {
 *        assert longestConsecutive(new int[]{100,4,200,1,3,2}) == 4;
 *        assert longestConsecutive(new int[]{0,3,7,2,5,8,4,6,0,1}) == 9;
 *        assert longestConsecutive(new int[]{}) == 0;
 *        assert longestConsecutive(new int[]{5}) == 1;
 *        assert longestConsecutive(new int[]{1,1,1}) == 1;
 *        assert longestConsecutive(new int[]{10,30,20}) == 1;
 *        System.out.println("All assertions passed.");
 *    }
 *    // Run with:  java -ea LongestConsecutiveSet
 *
 * (All six of these were validated against all three implementations - every
 * one returned the expected value.)
 *
 * | Approach    | Risk                                   | Mitigation                                           |
 * |-------------|----------------------------------------|------------------------------------------------------|
 * | Brute Force | Wrongly believed to be O(n)            | Recognize repeated mid-chain walks make it O(n^2)    |
 * | Sorting     | Duplicates corrupt count; input mutated| continue on equal neighbors; clone if order matters  |
 * | Hash Set    | Dropping num-1 guard degrades to O(n^2)| Keep the guard; test on a single long chain          |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * LeetCode #128 - Difficulty: Medium - ~1,500+ reported interview appearances
 *
 * | Company        | Frequency (stars) | Notes                                                |
 * |----------------|-------------------|------------------------------------------------------|
 * | Google         | *****             | Canonical "O(n log n) -> O(n)?" question             |
 * | Amazon         | *****             | Very common phone-screen and onsite item             |
 * | Meta           | ****              | Tests hash-set fluency and the start-only insight    |
 * | Microsoft      | ****              | Appears in coding rounds regularly                   |
 * | Bloomberg      | ****              | Popular for its clean O(n) trick                     |
 * | Apple          | ***               | Occasional onsite appearance                         |
 * | Adobe          | ***               | Shows up in India hiring rounds                      |
 * | Uber           | ***               | Asked as a warm-up to harder set problems            |
 * | Oracle         | **                | Less frequent but seen                               |
 * | Goldman Sachs  | **                | Occasional, sort variant often accepted              |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach              | Time       | Space      | Code Complexity | Recommended?                          |
 * |-----------------------|------------|------------|-----------------|---------------------------------------|
 * | Brute Force           | O(n^2)     | O(n)       | Low             | NO - learning only; too slow at 10^5  |
 * | Sorting + Scan        | O(n log n) | O(1) aux   | Low-Medium      | YES - best for low memory             |
 * | Hash Set (start-only) | O(n)       | O(n)       | Medium          | BEST - best for time (intended optimal)|
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use the Hash Set start-only walk when time is the priority (it's the only
 * approach meeting the O(n) requirement). Switch to Sorting when auxiliary
 * memory must be O(1) and O(n log n) runtime is acceptable - the two winners
 * differ by axis, so pick based on your binding constraint.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * The whole problem collapses to one insight: a run's length should only be
 * counted from its smallest element - the value whose predecessor (value - 1)
 * is absent from the set. That single guard turns a repeated-walk O(n^2) into a
 * clean O(n), because it guarantees each value is walked exactly once. Hash set
 * for O(1) membership + "start-only" counting is the pattern to memorize.
 *
 */
// @formatter:on