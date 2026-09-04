package Array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MajorityElement {
    public static void main(String[] args) {
        MajorityElement majorityElement = new MajorityElement();
        System.out.println("MajorityElement : " + majorityElement.majorityElementBruteForce(new int[] { 3, 2, 3 }));
        System.out.println(
                "MajorityElement : " + majorityElement.majorityElementBruteForce(new int[] { 2, 2, 1, 1, 1, 2, 2 }));
        System.out.println("----------------------------------");
        System.out.println(
                "MajorityElement : " + majorityElement.majorityElementHashMapFrequencyCount(new int[] { 3, 2, 3 }));
        System.out.println(
                "MajorityElement : "
                        + majorityElement.majorityElementHashMapFrequencyCount(new int[] { 2, 2, 1, 1, 1, 2, 2 }));
        System.out.println("----------------------------------");
        System.out.println(
                "MajorityElement : " + majorityElement.majorityElementSortingMiddle(new int[] { 3, 2, 3 }));
        System.out.println(
                "MajorityElement : "
                        + majorityElement.majorityElementSortingMiddle(new int[] { 2, 2, 1, 1, 1, 2, 2 }));
        System.out.println("----------------------------------");
        System.out.println(
                "MajorityElement : " + majorityElement.majorityElementBoyerMooreVoting(new int[] { 3, 2, 3 }));
        System.out.println(
                "MajorityElement : "
                        + majorityElement.majorityElementBoyerMooreVoting(new int[] { 2, 2, 1, 1, 1, 2, 2 }));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/majority-element/description/?envType=problem-list-v2&envId=array
     * 
     * Given an array nums of size n, return the majority element.
     * 
     * The majority element is the element that appears more than ⌊n / 2⌋ times. You
     * may assume that the majority element always exists in the array.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [3,2,3]
     * Output: 3
     * Example 2:
     * 
     * Input: nums = [2,2,1,1,1,2,2]
     * Output: 2
     * 
     * 
     * Constraints:
     * 
     * n == nums.length
     * 1 <= n <= 5 * 104
     * -109 <= nums[i] <= 109
     * The input is generated such that a majority element will exist in the array.
     * 
     * 
     * Follow-up: Could you solve the problem in linear time and in O(1) space?
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     *| Approach     | Time       | Space        | Code Complexity        | Recommended?                    |
     *|--------------|------------|--------------|------------------------|---------------------------------|
     *| Brute Force  | O(n^2)     | O(1)         | Very simple            | NO - too slow for large n       |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int majorityElementBruteForce(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int candidate = nums[i];
            int count = 1;
            for (int y = i + 1; y < n; y++) {
                if (candidate == nums[y])
                    count++;
            }
            if (count > (n / 2))
                return candidate;
        }
        return -1;
    }

    // @formatter:off
    /**
     * 
     *| Approach     | Time       | Space        | Code Complexity        | Recommended?                    |
     *|--------------|------------|--------------|------------------------|---------------------------------|
     *| Hash Map     | O(n)       | O(n)         | Simple                 | OK - use if you need frequencies |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int majorityElementHashMapFrequencyCount(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            int updated = frequencyMap.getOrDefault(num, 0) + 1;
            frequencyMap.put(num, updated);
            if (updated > (n / 2))
                return num;
        }
        return -1;
    }

    public int majorityElementSortingMiddle(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    // @formatter:off
    /**
     *| Approach     | Time       | Space        | Code Complexity        | Recommended?                    |
     *| Boyer-Moore  | O(n)       | O(1)         | Moderate (needs idea)  | BEST - optimal on time and space |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int majorityElementBoyerMooreVoting(int[] nums) {
        int candidate = 0;
        int count = 0;
        for (int num : nums) {
            if (count == 0)
                candidate = num;
            count += (num == candidate) ? 1 : -1;
        }
        return candidate;
    }
}

// @formatter:off
/*
 * ============================================================
 * MAJORITY ELEMENT - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an array of integers, find the single element that appears
 * more than floor(n/2) times (strictly more than half of the array).
 * This is LeetCode #169. A majority element is GUARANTEED to exist,
 * so you never handle the "no answer" case.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums - an array of integers of length n (values may be
 * negative, zero, or positive; duplicates expected).
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * A single int - the value of the majority element.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * n == nums.length
 * 1 <= n <= 5 * 10^4
 * -10^9 <= nums[i] <= 10^9
 * A majority element is guaranteed to exist.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * The value v such that count(v) > n / 2 using integer division.
 * Because it occupies more than half the slots, there can be at
 * most one such element.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  nums = [2, 2, 1, 1, 1, 2, 2]
 *    n = 7, so we need an element appearing > 3 times.
 *    2 appears 4 times -> 4 > 3  OK
 *    Output: 2
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * The majority element is so dominant that it outnumbers everything
 * else combined. If you pit it against all others in a one-to-one
 * "cancellation" battle, it is mathematically impossible for it to lose.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Naive instinct: count how many times each value appears, then
 *    report the one whose count crosses the halfway line.
 * 2. Tidier: if you SORT the array, the majority floods the middle.
 *    Whatever sits at index n/2 must be the majority element.
 * 3. Deepest (Boyer-Moore): treat each majority element as a +1 soldier
 *    and every other element as a -1 soldier. Cancel pairwise. The
 *    majority side has strictly more soldiers, so at least one survives.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                     | Why it's tricky                                             |
 * |-------------------------------|-------------------------------------------------------------|
 * | Beating O(n) space            | Hash-map count uses O(n) memory; O(1) needs voting insight  |
 * | Believing Boyer-Moore correct | Looks foolable, but "> half" guarantee makes it airtight    |
 * | The strict inequality         | It's > n/2, not >= n/2; exactly half is NOT a majority      |
 * | Integer overflow in counting  | Values up to 10^9; count occurrences, never sum values      |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                | Key Idea                                   | Best Used When                     | Time       | Space          |
 * |---|-------------------------|--------------------------------------------|------------------------------------|------------|----------------|
 * | 1 | Brute Force             | For each element, count its occurrences    | Tiny inputs; illustration          | O(n^2)     | O(1)           |
 * | 2 | Hash Map                | Tally counts in one pass; return > n/2     | You also need frequency data       | O(n)       | O(n)           |
 * | 3 | Sorting (middle)        | Sort; element at index n/2 is the answer   | Array can be mutated               | O(n log n) | O(1) to O(n)   |
 * | 4 | Boyer-Moore Voting  OK  | One candidate + vote counter; cancel votes | The general optimal case           | O(n) *time*| O(1) *space*   |
 *
 * The axes do NOT conflict: Boyer-Moore wins on BOTH time and space, so it is the
 * unambiguous optimal. The hash map matches O(n) time but pays O(n) space; sorting
 * is O(1) space in place but O(n log n) time. Prefer Boyer-Moore in all cases;
 * reach for the hash map only when you also need the full frequency table.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (Nested Count)
 * ------------------------------------------------------------
 * 1. For each index i, take nums[i] as a candidate.
 * 2. Scan the whole array counting how many times that candidate appears.
 * 3. If the count exceeds n / 2, return the candidate.
 *
 *    public class MajorityBrute {
 *        public int majorityElement(int[] nums) {
 *            int n = nums.length;
 *            for (int i = 0; i < n; i++) {
 *                int candidate = nums[i];
 *                int count = 0;
 *                for (int j = 0; j < n; j++) {
 *                    if (nums[j] == candidate) {
 *                        count++;
 *                    }
 *                }
 *                if (count > n / 2) {
 *                    return candidate;
 *                }
 *            }
 *            return -1; // unreachable given the problem guarantee
 *        }
 *
 *        public static void main(String[] args) {
 *            MajorityBrute solver = new MajorityBrute();
 *            int[] nums = {2, 2, 1, 1, 1, 2, 2};
 *            System.out.println(solver.majorityElement(nums)); // 2
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 2: Hash Map (Frequency Count)
 * ------------------------------------------------------------
 * 1. Create a map from value -> running count.
 * 2. Walk the array once; increment each value's count.
 * 3. The moment any count exceeds n / 2, return that value.
 *
 *    import java.util.HashMap;
 *    import java.util.Map;
 *
 *    public class MajorityHashMap {
 *        public int majorityElement(int[] nums) {
 *            int n = nums.length;
 *            Map<Integer, Integer> counts = new HashMap<>();
 *            for (int value : nums) {
 *                int updated = counts.getOrDefault(value, 0) + 1;
 *                counts.put(value, updated);
 *                if (updated > n / 2) {
 *                    return value; // early exit once threshold crossed
 *                }
 *            }
 *            return -1; // unreachable given the problem guarantee
 *        }
 *
 *        public static void main(String[] args) {
 *            MajorityHashMap solver = new MajorityHashMap();
 *            int[] nums = {3, 2, 3};
 *            System.out.println(solver.majorityElement(nums)); // 3
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 3: Sorting (Middle Element)
 * ------------------------------------------------------------
 * 1. Sort the array in non-decreasing order.
 * 2. The majority element occupies > half the positions, so it is
 *    guaranteed to cover the middle index n / 2.
 * 3. Return nums[n / 2].
 *
 *    import java.util.Arrays;
 *
 *    public class MajoritySort {
 *        public int majorityElement(int[] nums) {
 *            Arrays.sort(nums);            // mutates input; clone first if needed
 *            return nums[nums.length / 2]; // middle slot belongs to the majority
 *        }
 *
 *        public static void main(String[] args) {
 *            MajoritySort solver = new MajoritySort();
 *            int[] nums = {6, 5, 5};
 *            System.out.println(solver.majorityElement(nums)); // 5
 *        }
 *    }
 *
 * Why the middle index works: a value occupying > n/2 slots forms a contiguous
 * block longer than half the array; any such block must straddle index n/2.
 *
 * ------------------------------------------------------------
 * Approach 4: Boyer-Moore Voting  (OPTIMAL)
 * ------------------------------------------------------------
 * 1. Keep candidate and count (count starts at 0).
 * 2. For each element x:
 *      - if count == 0, adopt x as the new candidate.
 *      - if x == candidate, count++; otherwise count--.
 * 3. After one pass, candidate holds the majority element.
 *
 *    public class MajorityBoyerMoore {
 *        public int majorityElement(int[] nums) {
 *            int candidate = 0;
 *            int count = 0;
 *            for (int value : nums) {
 *                if (count == 0) {
 *                    candidate = value; // reset to a fresh candidate
 *                }
 *                count += (value == candidate) ? 1 : -1;
 *            }
 *            return candidate; // guaranteed correct because a majority exists
 *        }
 *
 *        public static void main(String[] args) {
 *            MajorityBoyerMoore solver = new MajorityBoyerMoore();
 *            int[] nums = {2, 2, 1, 1, 1, 2, 2};
 *            System.out.println(solver.majorityElement(nums)); // 2
 *        }
 *    }
 *
 * Why it's correct: majority occurrences are +1, others -1. Non-majority elements
 * can cancel at most n/2 majority votes, but the majority has strictly more than
 * n/2 votes, so it can never be fully cancelled. The final survivor is the answer.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Approach 1 - Brute Force
 *   Time:  O(n^2). Outer n * inner n. For n=50000 -> ~2.5 * 10^9 ops (too slow).
 *   Space: O(1). Two integer counters.
 *
 * Approach 2 - Hash Map
 *   Time:  O(n). Single pass, O(1) average map ops. n=50000 -> ~50000 updates.
 *   Space: O(n). Map may hold up to n/2 + 1 keys.
 *
 * Approach 3 - Sorting
 *   Time:  O(n log n), dominated by the sort. n=50000 -> ~8 * 10^5 comparisons.
 *   Space: O(1) extra in place (O(log n) recursion for dual-pivot quicksort);
 *          O(n) if you clone to preserve the input.
 *
 * Approach 4 - Boyer-Moore Voting (OPTIMAL)
 *   Time:  O(n). One pass, constant work per element. n=50000 -> ~50000 ops.
 *   Space: O(1). Just candidate and count.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * nums = [2, 2, 1, 1, 1, 2, 2], n = 7, threshold > 3.
 *
 * ------------------------------------------------------------
 * Approach 1 - Brute Force
 * ------------------------------------------------------------
 *    i=0, candidate=2 -> occurrences of 2 at {0,1,5,6} -> 4 > 3 OK
 *    Return 2
 *
 * ------------------------------------------------------------
 * Approach 2 - Hash Map
 * ------------------------------------------------------------
 *    value 2 -> count 1
 *    value 2 -> count 2
 *    value 1 -> count 1
 *    value 1 -> count 2
 *    value 1 -> count 3
 *    value 2 -> count 3
 *    value 2 -> count 4 -> 4 > 3 OK -> return 2
 *
 * ------------------------------------------------------------
 * Approach 3 - Sorting
 * ------------------------------------------------------------
 *    sorted: [1, 1, 1, 2, 2, 2, 2]
 *    index n/2 = 7/2 = 3
 *    nums[3] = 2 -> return 2
 *
 * ------------------------------------------------------------
 * Approach 4 - Boyer-Moore Voting
 * ------------------------------------------------------------
 *    |- x=2 | count 0 -> set candidate=2, count 0->1
 *    |- x=2 | equals cand -> count 1->2
 *    |- x=1 | differs     -> count 2->1
 *    |- x=1 | differs     -> count 1->0
 *    |- x=1 | count 0 -> set candidate=1, count 0->1
 *    |- x=2 | differs     -> count 1->0
 *    +- x=2 | count 0 -> set candidate=2, count 0->1
 *    Final candidate = 2 OK
 *
 * The candidate briefly flipped to 1, then got overpowered again - the majority
 * reclaims the lead because it simply has more votes.
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case              | Input               | Expected | How Handled                                  |
 * |------------------------|---------------------|----------|----------------------------------------------|
 * | Single element         | [7]                 | 7        | Lone element is trivially majority (1 > 0)   |
 * | All identical          | [4,4,4,4]           | 4        | Count reaches n; voting never resets         |
 * | Majority is min value  | [6,5,5]             | 5        | Sorting puts 5 at the middle; voting survives|
 * | Negative values        | [-1,-1,-1,2,2]      | -1       | Compared by equality; sign irrelevant        |
 * | Majority at the end    | [1,2,2,2,2]         | 2        | Boyer-Moore recovers from differing prefix   |
 * | Large n boundary       | 50,000 elements     | majority | Only O(n) approaches are fast enough         |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 - Using >= instead of >:
 *    // WRONG: an element at exactly half is NOT a majority
 *    if (count >= n / 2) return value;
 *    // CORRECT:
 *    if (count > n / 2) return value;
 *
 * Pitfall 2 - Forgetting integer-division truncation:
 *    // For n = 7, n/2 = 3 (not 3.5). Threshold is "> 3", i.e. at least 4.
 *    // Relying on n/2.0 with floating point invites subtle comparison bugs.
 *
 * Pitfall 3 - Mutating the caller's array in the sort approach:
 *    // WRONG if the caller still needs original order:
 *    Arrays.sort(nums);
 *    // CORRECT (preserve input):
 *    int[] copy = nums.clone();
 *    Arrays.sort(copy);
 *    return copy[copy.length / 2];
 *
 * Pitfall 4 - Resetting the Boyer-Moore candidate incorrectly:
 *    // WRONG: fires when count is negative mid-step
 *    if (count <= 0) candidate = value;
 *    // CORRECT: reset only when count is exactly zero, before adjusting
 *    if (count == 0) candidate = value;
 *    count += (value == candidate) ? 1 : -1;
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: An array with NO true majority. This problem guarantees one exists, so
 *    Boyer-Moore returns a candidate without validating it. Remove the guarantee
 *    and you must add a SECOND pass to confirm count(candidate) > n/2.
 *
 * Q: Are there any type mismatches?
 * A: Values fit in int (-10^9..10^9 is within int's ~+/-2.1*10^9). Counts never
 *    exceed n <= 5*10^4, so no overflow. Never sum element VALUES (could overflow);
 *    only count OCCURRENCES.
 *
 * Q: How can I verify this works right now?
 *    public class MajorityVerify {
 *        public int majorityElement(int[] nums) {
 *            int candidate = 0, count = 0;
 *            for (int value : nums) {
 *                if (count == 0) candidate = value;
 *                count += (value == candidate) ? 1 : -1;
 *            }
 *            return candidate;
 *        }
 *
 *        public void verify() {
 *            assert majorityElement(new int[]{3, 2, 3}) == 3;
 *            assert majorityElement(new int[]{2, 2, 1, 1, 1, 2, 2}) == 2;
 *            assert majorityElement(new int[]{1}) == 1;
 *            assert majorityElement(new int[]{6, 5, 5}) == 5;
 *            assert majorityElement(new int[]{-1, -1, -1, 2, 2}) == -1;
 *            System.out.println("All assertions passed.");
 *        }
 *
 *        public static void main(String[] args) {
 *            new MajorityVerify().verify(); // run with: java -ea MajorityVerify
 *        }
 *    }
 *
 * | Approach     | Risk                                   | Mitigation                                   |
 * |--------------|----------------------------------------|----------------------------------------------|
 * | Brute Force  | Too slow at n=5*10^4 (O(n^2))          | Use for tiny inputs or as a test oracle      |
 * | Hash Map     | O(n) memory may be wasteful            | Prefer Boyer-Moore when only value is needed |
 * | Sorting      | Mutates input; O(n log n) slower       | Clone before sorting; use voting for speed   |
 * | Boyer-Moore  | Blindly trusts a candidate             | Add a verification pass if not guaranteed    |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * LeetCode #169 . Difficulty: Easy . A classic warm-up / follow-up problem.
 *
 * | Company        | Frequency | Notes                                             |
 * |----------------|-----------|---------------------------------------------------|
 * | Amazon         | *****     | Phone-screen warm-up; O(1)-space follow-up common |
 * | Google         | ****      | Probes WHY Boyer-Moore is correct, not just code  |
 * | Microsoft      | ****      | Frequent array-fundamentals question              |
 * | Meta           | ****      | Sometimes extended to Majority Element II (> n/3) |
 * | Apple          | ***       | Appears in early-round screens                    |
 * | Bloomberg      | ****      | Tests space-optimization instincts                |
 * | Adobe          | ***       | Standard array question                           |
 * | Oracle         | ***       | Frequently a first, easy question                 |
 * | Uber           | ***       | Quick screening problem                           |
 * | Goldman Sachs  | **        | Occasionally; hash-map solution often accepted    |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach     | Time       | Space        | Code Complexity        | Recommended?                    |
 * |--------------|------------|--------------|------------------------|---------------------------------|
 * | Brute Force  | O(n^2)     | O(1)         | Very simple            | NO - too slow for large n       |
 * | Hash Map     | O(n)       | O(n)         | Simple                 | OK - use if you need frequencies |
 * | Sorting      | O(n log n) | O(1)-O(n)    | Trivial                | OK - fine one-liner, not optimal |
 * | Boyer-Moore  | O(n)       | O(1)         | Moderate (needs idea)  | BEST - optimal on time and space |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Boyer-Moore Voting - simultaneously time-optimal (O(n)) and space-optimal (O(1)),
 * so there is no trade-off to weigh; it simply dominates. Use the hash map only
 * when you also need the complete frequency table.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * The majority element outnumbers everything else combined, which is why pairwise
 * cancellation (Boyer-Moore) leaves it standing. Memorize the two-variable pattern:
 * reset candidate when count hits 0, then +1 on a match, -1 on a mismatch. Key
 * gotcha: the STRICT > n/2 (not >=); if majority isn't guaranteed, add a verify pass.
 */
// @formatter:on
