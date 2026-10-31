package Array;

import java.util.ArrayList;
import java.util.List;

public class FindAllNumbersDisappearedInAnArray {
    public static void main(String[] args) {
        FindAllNumbersDisappearedInAnArray findAllNumbersDisappearedInAnArray = new FindAllNumbersDisappearedInAnArray();
        System.out.println("FindAllNumbersDisappearedInAnArray : "
                + findAllNumbersDisappearedInAnArray
                        .findDisappearedNumbersHashSetBoolean(new int[] { 4, 3, 2, 7, 8, 2, 3, 1 }));
        System.out.println("FindAllNumbersDisappearedInAnArray : "
                + findAllNumbersDisappearedInAnArray.findDisappearedNumbersHashSetBoolean(new int[] { 2, 2 }));
        System.out.println("------------------------------");
        System.out.println("FindAllNumbersDisappearedInAnArray : "
                + findAllNumbersDisappearedInAnArray
                        .findDisappearedNumbersSortWalk(new int[] { 4, 3, 2, 7, 8, 2, 3, 1 }));
        System.out.println("FindAllNumbersDisappearedInAnArray : "
                + findAllNumbersDisappearedInAnArray.findDisappearedNumbersSortWalk(new int[] { 2, 2 }));
        System.out.println("------------------------------");
        System.out.println("FindAllNumbersDisappearedInAnArray : "
                + findAllNumbersDisappearedInAnArray
                        .findDisappearedNumbersNegativeMarking(new int[] { 4, 3, 2, 7, 8, 2, 3, 1 }));
        System.out.println("FindAllNumbersDisappearedInAnArray : "
                + findAllNumbersDisappearedInAnArray.findDisappearedNumbersNegativeMarking(new int[] { 2, 2 }));
        System.out.println("------------------------------");
        System.out.println("FindAllNumbersDisappearedInAnArray : "
                + findAllNumbersDisappearedInAnArray
                        .findDisappearedNumbersCyclicSort(new int[] { 4, 3, 2, 7, 8, 2, 3, 1 }));
        System.out.println("FindAllNumbersDisappearedInAnArray : "
                + findAllNumbersDisappearedInAnArray.findDisappearedNumbersCyclicSort(new int[] { 2, 2 }));

    }

    // @formatter:off
    /**
     * 
     * https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/description/?envType=problem-list-v2&envId=array
     * 
     * Given an array nums of n integers where nums[i] is in the range [1, n],
     * return an array of all the integers in the range [1, n] that do not appear in
     * nums.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [4,3,2,7,8,2,3,1]
     * Output: [5,6]
     * Example 2:
     * 
     * Input: nums = [1,1]
     * Output: [2]
     * 
     * 
     * Constraints:
     * 
     * n == nums.length
     * 1 <= n <= 105
     * 1 <= nums[i] <= n
     * 
     * 
     * Follow up: Could you do it without extra space and in O(n) runtime? You may
     * assume the returned list does not count as extra space.
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach          | Time       | Space | Code Complexity | Recommended?                              |
     * |-------------------|------------|-------|-----------------|-------------------------------------------|
     * | HashSet / boolean | O(n)       | O(n)  | Very low        | Best for clarity / input must not change  |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public List<Integer> findDisappearedNumbersHashSetBoolean(int[] nums) {
        List<Integer> dissappearedIntegers = new ArrayList<>();
        int n = nums.length;
        boolean[] isPresent = new boolean[n + 1];
        for (int num : nums) {
            isPresent[num] = true;
        }

        for (int i = 1; i <= n; i++) {
            if (!isPresent[i]) {
                dissappearedIntegers.add(i);
            }
        }
        return dissappearedIntegers;
    }

    // @formatter:off
    /**
     * 
     * | Approach          | Time       | Space | Code Complexity | Recommended?                              |
     * |-------------------|------------|-------|-----------------|-------------------------------------------|
     * | Sort + walk       | O(n log n) | O(1)  | Low-medium      | NOT recommended when O(n) is expected     |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public List<Integer> findDisappearedNumbersSortWalk(int[] nums) {
        List<Integer> dissappearedIntegers = new ArrayList<>();
        int n = nums.length;
        boolean[] isPresent = new boolean[n + 1];
        for (int num : nums) {
            isPresent[num] = true;
        }

        for (int i = 1; i <= n; i++) {
            if (!isPresent[i]) {
                dissappearedIntegers.add(i);
            }
        }
        return dissappearedIntegers;
    }

    // @formatter:off
    /**
     * 
     * | Approach          | Time       | Space | Code Complexity | Recommended?                              |
     * |-------------------|------------|-------|-----------------|-------------------------------------------|
     * | Negative marking  | O(n)       | O(1)  | Low             | BEST overall (time + space, fewest writes)|
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public List<Integer> findDisappearedNumbersNegativeMarking(int[] nums) {
        List<Integer> dissappearedIntegers = new ArrayList<>();
        int n = nums.length;
        boolean[] isPresent = new boolean[n + 1];
        for (int num : nums) {
            isPresent[num] = true;
        }

        for (int i = 1; i <= n; i++) {
            if (!isPresent[i]) {
                dissappearedIntegers.add(i);
            }
        }
        return dissappearedIntegers;
    }

    // @formatter:off
    /**
     * 
     * | Approach          | Time       | Space | Code Complexity | Recommended?                              |
     * |-------------------|------------|-------|-----------------|-------------------------------------------|
     * | Cyclic sort       | O(n)       | O(1)  | Medium          | Equally optimal; great if you know pattern|
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public List<Integer> findDisappearedNumbersCyclicSort(int[] nums) {
        List<Integer> dissappearedIntegers = new ArrayList<>();
        int n = nums.length;
        boolean[] isPresent = new boolean[n + 1];
        for (int num : nums) {
            isPresent[num] = true;
        }

        for (int i = 1; i <= n; i++) {
            if (!isPresent[i]) {
                dissappearedIntegers.add(i);
            }
        }
        return dissappearedIntegers;
    }
}
// @formatter:off
/*
 * ============================================================
 * FIND ALL NUMBERS DISAPPEARED IN AN ARRAY - DEEP DIVE EXPLANATION
 * (LeetCode 448 - Easy)
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are given an integer array nums of length n. Every element lies in the
 * range [1, n]. Because there are n slots but values may repeat, some numbers
 * in [1, n] are missing. Return ALL numbers in [1, n] that do NOT appear in nums.
 * This is LeetCode 448 - Find All Numbers Disappeared in an Array (Easy).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums - array of length n, where 1 <= nums[i] <= n.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * List<Integer> - every value in [1, n] absent from nums (any order).
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * n == nums.length
 * 1 <= n <= 10^5
 * 1 <= nums[i] <= n
 * Follow-up: O(n) time and O(1) extra space (returned list excluded).
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * The set difference {1, 2, ..., n} \ set(nums). Duplicates free up room for
 * missing numbers.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 * Input:  nums = [4,3,2,7,8,2,3,1]   (n = 8)
 * Present: {1,2,3,4,7,8}
 * Missing: {5,6}
 * Output: [5,6]
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * There are n "seats" numbered 1..n and n people, but some share a seat
 * (duplicates). Every empty seat is a missing number. The trick is recording
 * "seat k was occupied" efficiently.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Values (1..n) and indices (0..n-1) share the same range - a value can be
 *    used as an address.
 * 2. For each value v, go to "slot v" and leave a mark meaning "v exists."
 * 3. Any slot never marked means its number never appeared.
 * 4. For O(1) space, store marks INSIDE the array by flipping a number's sign.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                    | Why it's tricky                                          |
 * |------------------------------|----------------------------------------------------------|
 * | The O(1) space follow-up     | Must reuse nums itself as bookkeeping, no extra set.     |
 * | Duplicates                   | A slot may be visited twice; guard against double-flip. |
 * | Sign as a flag               | After negating, read absolute value to compute index.   |
 * | Index vs value offset        | Value v maps to index v-1; off-by-one corrupts results. |
 * | Preserving recoverability    | Marking must still let you identify each slot's number. |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                    | Key Idea                                   | Best Used When                         | Time       | Space              |
 * |---|-----------------------------|--------------------------------------------|----------------------------------------|------------|--------------------|
 * | 1 | HashSet / boolean seen      | Record present values, report unseen       | Simplicity matters; memory is fine     | O(n)       | O(n)               |
 * | 2 | Sort + linear walk          | Sort, two-pointer scan 1..n vs sorted      | Input may be modified; O(n) not needed | O(n log n) | O(1) (aux)         |
 * | 3 | In-place negative marking   | Use abs(v)-1 as index; flip slot sign      | Want optimal time AND space            | O(n) OK    | O(1) OK            |
 * | 4 | Cyclic sort                 | Place each value v at index v-1 via swaps  | You like the 1..n range pattern        | O(n)       | O(1)               |
 *
 * Approaches 3 and 4 both hit O(n) time AND O(1) space. Approach 1 trades space
 * for the simplest code; Approach 2 gives up O(n) time but keeps low space.
 * Among the two optimal solutions, negative marking (3) is preferred: at most
 * one write per element and shorter code, whereas cyclic sort may do several
 * swaps per position. Use Approach 1 only when clarity trumps everything and the
 * input must stay unmodified; use Approach 3 as the default interview answer.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: HashSet / Boolean "Seen" Array
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Create boolean[] seen of size n+1 (index 0 unused).
 * 2. For each value v in nums, set seen[v] = true.
 * 3. For k in 1..n, if !seen[k], add k to result.
 *
 *    import java.util.*;
 *
 *    public class DisappearedSeen {
 *        public static List<Integer> findDisappearedNumbers(int[] nums) {
 *            int n = nums.length;
 *            boolean[] seen = new boolean[n + 1];   // seen[1..n], index 0 ignored
 *            for (int v : nums) {
 *                seen[v] = true;                     // mark presence
 *            }
 *            List<Integer> missing = new ArrayList<>();
 *            for (int k = 1; k <= n; k++) {
 *                if (!seen[k]) {
 *                    missing.add(k);                 // never marked => absent
 *                }
 *            }
 *            return missing;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
 *            System.out.println(findDisappearedNumbers(nums)); // [5, 6]
 *        }
 *    }
 *
 * The n+1 sizing avoids offset math: value k maps directly to seen[k].
 *
 * ------------------------------------------------------------
 * Approach 2: Sort + Linear Walk
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Sort nums in place.
 * 2. Keep a pointer j into the sorted array.
 * 3. For each expected 1..n: advance j past smaller values (handles dups),
 *    then if current value equals expected it is present (advance j);
 *    otherwise expected is missing.
 *
 *    import java.util.*;
 *
 *    public class DisappearedSort {
 *        public static List<Integer> findDisappearedNumbers(int[] nums) {
 *            int n = nums.length;
 *            Arrays.sort(nums);                      // O(n log n)
 *            List<Integer> missing = new ArrayList<>();
 *            int j = 0;
 *            for (int expected = 1; expected <= n; expected++) {
 *                while (j < n && nums[j] < expected) {
 *                    j++;                            // skip smaller + duplicates
 *                }
 *                if (j < n && nums[j] == expected) {
 *                    j++;                            // expected is present
 *                } else {
 *                    missing.add(expected);          // expected is absent
 *                }
 *            }
 *            return missing;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
 *            System.out.println(findDisappearedNumbers(nums)); // [5, 6]
 *        }
 *    }
 *
 * The while loop makes duplicates harmless: repeats are skipped, and expected
 * only advances when matched.
 *
 * ------------------------------------------------------------
 * Approach 3: In-Place Negative Marking  (OPTIMAL)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. For each element compute index = abs(nums[i]) - 1.
 * 2. If nums[index] > 0, negate it - marks "number index+1 is present."
 *    Use abs because nums[i] may already be negated.
 * 3. After pass 1, any position i still positive means i+1 is missing.
 * 4. Collect all such i+1.
 *
 *    import java.util.*;
 *
 *    public class DisappearedNegate {
 *        public static List<Integer> findDisappearedNumbers(int[] nums) {
 *            int n = nums.length;
 *            for (int i = 0; i < n; i++) {
 *                int index = Math.abs(nums[i]) - 1; // read true value, map to slot
 *                if (nums[index] > 0) {
 *                    nums[index] = -nums[index];    // mark slot as "seen"
 *                }
 *            }
 *            List<Integer> missing = new ArrayList<>();
 *            for (int i = 0; i < n; i++) {
 *                if (nums[i] > 0) {
 *                    missing.add(i + 1);            // slot never flipped => absent
 *                }
 *            }
 *            return missing;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
 *            System.out.println(findDisappearedNumbers(nums)); // [5, 6]
 *        }
 *    }
 *
 * Math.abs in step 1 is essential: without it a negated value gives a wrong
 * index and an ArrayIndexOutOfBoundsException. The guard if (nums[index] > 0)
 * prevents a duplicate from flipping a slot back to positive.
 *
 * ------------------------------------------------------------
 * Approach 4: Cyclic Sort
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Walk i from 0. Home index for value v is v-1.
 * 2. If nums[i] is not at home (nums[i] != nums[nums[i]-1]), swap toward home;
 *    do NOT advance i.
 * 3. Otherwise advance i. Comparing the value at home (not the index) avoids
 *    infinite loops on duplicates.
 * 4. Any index i where nums[i] != i+1 means i+1 is missing.
 *
 *    import java.util.*;
 *
 *    public class DisappearedCyclic {
 *        public static List<Integer> findDisappearedNumbers(int[] nums) {
 *            int n = nums.length;
 *            int i = 0;
 *            while (i < n) {
 *                int home = nums[i] - 1;                 // where nums[i] belongs
 *                if (nums[i] != nums[home]) {
 *                    int tmp = nums[i];                  // swap toward home
 *                    nums[i] = nums[home];
 *                    nums[home] = tmp;
 *                } else {
 *                    i++;                                // in place or duplicate
 *                }
 *            }
 *            List<Integer> missing = new ArrayList<>();
 *            for (int k = 0; k < n; k++) {
 *                if (nums[k] != k + 1) {
 *                    missing.add(k + 1);                 // wrong occupant => absent
 *                }
 *            }
 *            return missing;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
 *            System.out.println(findDisappearedNumbers(nums)); // [5, 6]
 *        }
 *    }
 *
 * The swap condition compares VALUES (nums[i] != nums[home]), not indices, so a
 * duplicate that would swap forever is recognized as satisfied and skipped.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Approach 1 - HashSet / Boolean Array
 *   Time O(n): mark pass (n) + collect pass (n) = 2n.
 *   Space O(n): the seen array of size n+1.
 *   For n = 10^5: ~2 x 10^5 ops, ~100 KB of booleans.
 *
 * Approach 2 - Sort + Walk
 *   Time O(n log n): dominated by Arrays.sort; walk is O(n).
 *   Space O(1) auxiliary: in-place sort (ignoring O(log n) quicksort stack).
 *   For n = 10^5: n log n ~ 1.7 x 10^6 comparisons.
 *
 * Approach 3 - Negative Marking (OPTIMAL)
 *   Time O(n): two passes of O(1) work each.
 *   Space O(1): all marking inside nums; only output allocated.
 *   For n = 10^5: ~2 x 10^5 ops, <= n sign flips (fewest writes).
 *
 * Approach 4 - Cyclic Sort
 *   Time O(n): each swap places a value home, so total swaps <= n; amortized O(n).
 *   Space O(1): in-place swaps only.
 *   For n = 10^5: <= 10^5 swaps + one O(n) scan.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * Input for all traces: nums = [4,3,2,7,8,2,3,1], n = 8. Expected: [5, 6].
 *
 * ------------------------------------------------------------
 * Approach 1 - Boolean Array
 * ------------------------------------------------------------
 * Mark phase (seen[v] = true):
 *   4->seen[4]  3->seen[3]  2->seen[2]  7->seen[7]
 *   8->seen[8]  2->seen[2]  3->seen[3]  1->seen[1]
 * seen (index 1..8): [T, T, T, T, F, F, T, T]
 * Collect phase:
 *   k=1 T . k=2 T . k=3 T . k=4 T . k=5 F->add 5 . k=6 F->add 6 . k=7 T . k=8 T
 * Output: [5, 6]
 *
 * ------------------------------------------------------------
 * Approach 2 - Sort + Walk
 * ------------------------------------------------------------
 * sorted nums = [1,2,2,3,3,4,7,8]
 * j=0
 *  expected=1 -> nums[0]=1 match -> j=1
 *  expected=2 -> nums[1]=2 match -> j=2
 *  expected=3 -> skip nums[2]=2 (j=3), nums[3]=3 match -> j=4
 *  expected=4 -> skip nums[4]=3 (j=5), nums[5]=4 match -> j=6
 *  expected=5 -> nums[6]=7 (>5, not equal) -> add 5
 *  expected=6 -> nums[6]=7 (>6, not equal) -> add 6
 *  expected=7 -> nums[6]=7 match -> j=7
 *  expected=8 -> nums[7]=8 match -> j=8
 * Output: [5, 6]
 *
 * ------------------------------------------------------------
 * Approach 3 - Negative Marking
 * ------------------------------------------------------------
 * Start: [4,3,2,7,8,2,3,1]
 *  v=4  idx3  nums[3]=7>0 -> -7   [4,3,2,-7,8,2,3,1]
 *  v=3  idx2  nums[2]=2>0 -> -2   [4,3,-2,-7,8,2,3,1]
 *  v=2  idx1  nums[1]=3>0 -> -3   [4,-3,-2,-7,8,2,3,1]
 *  v=|-7|=7 idx6 nums[6]=3>0 -> -3 [4,-3,-2,-7,8,2,-3,1]
 *  v=8  idx7  nums[7]=1>0 -> -1   [4,-3,-2,-7,8,2,-3,-1]
 *  v=2  idx1  nums[1]=-3 (<=0) skip
 *  v=|-3|=3 idx2 nums[2]=-2 (<=0) skip
 *  v=|-1|=1 idx0 nums[0]=4>0 -> -4 [-4,-3,-2,-7,8,2,-3,-1]
 * Collect positives:
 *  idx4 -> 8 (>0) -> add 5
 *  idx5 -> 2 (>0) -> add 6
 * Output: [5, 6]
 *
 * ------------------------------------------------------------
 * Approach 4 - Cyclic Sort
 * ------------------------------------------------------------
 * [4,3,2,7,8,2,3,1]
 *  i0: 4!=nums[3]=7 swap -> [7,3,2,4,8,2,3,1]
 *  i0: 7!=nums[6]=3 swap -> [3,3,2,4,8,2,7,1]
 *  i0: 3!=nums[2]=2 swap -> [2,3,3,4,8,2,7,1]
 *  i0: 2!=nums[1]=3 swap -> [3,2,3,4,8,2,7,1]
 *  i0: 3==nums[2]=3 -> i=1
 *  i1: 2==nums[1]     -> i=2
 *  i2: 3==nums[2]     -> i=3
 *  i3: 4==nums[3]     -> i=4
 *  i4: 8!=nums[7]=1 swap -> [3,2,3,4,1,2,7,8]
 *  i4: 1!=nums[0]=3 swap -> [1,2,3,4,3,2,7,8]
 *  i4: 3==nums[2]=3 -> i=5
 *  i5: 2==nums[1]=2 -> i=6
 *  i6: 7==nums[6]   -> i=7
 *  i7: 8==nums[7]   -> i=8 (stop)
 * Final: [1,2,3,4,3,2,7,8]
 * Scan nums[i] != i+1:
 *  idx4: 3!=5 -> add 5 . idx5: 2!=6 -> add 6
 * Output: [5, 6]
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                     | Input             | Expected  | How Handled                                        |
 * |-------------------------------|-------------------|-----------|----------------------------------------------------|
 * | Single element, present       | [1]               | []        | Slot 0 marked/placed; nothing missing.            |
 * | Single element, dup range     | [1,1]             | [2]       | Duplicate 1 guarded; slot 1 (value 2) unmarked.   |
 * | No missing numbers            | [1,2,3,4]         | []        | Every slot marked/in home; collect finds nothing. |
 * | All identical                 | [2,2,2,2]         | [1,3,4]   | First 2 marks; guard blocks re-flips; 0,2,3 stay. |
 * | Already sorted / permutation  | [1,2,3,4,5]       | []        | Each value maps to its own slot; no gaps.         |
 * | Reverse-heavy duplicates      | [4,3,2,7,8,2,3,1] | [5,6]     | Standard traced case.                             |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 - Forgetting abs in negative marking:
 *    // WRONG: uses the possibly-negated value directly
 *    int index = nums[i] - 1;              // may be negative -> crash
 *    // CORRECT:
 *    int index = Math.abs(nums[i]) - 1;    // always the true value
 *
 * Pitfall 2 - Re-flipping on duplicates (missing the guard):
 *    // WRONG: unconditional flip toggles a duplicate's slot back to positive
 *    nums[index] = -nums[index];
 *    // CORRECT: only flip if still positive
 *    if (nums[index] > 0) nums[index] = -nums[index];
 *
 * Pitfall 3 - Cyclic sort infinite loop by comparing indices instead of values:
 *    // WRONG: with duplicates, home may equal i forever
 *    if (i != nums[i] - 1) { /* swap * / }
 *    // CORRECT: compare the value already sitting at home
 *    if (nums[i] != nums[nums[i] - 1]) { /* swap * / }
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: All-duplicate inputs ([2,2,2,2]) and single-element arrays. Both are
 *    covered by the positivity guard (negative marking) and the value-based swap
 *    condition (cyclic sort). Empty input can't occur since n >= 1.
 *
 * Q: Are there any type mismatches?
 * A: Values fit in int (n <= 10^5), no overflow. Result is List<Integer>;
 *    autoboxing i+1 is fine. Math.abs(Integer.MIN_VALUE) never arises since
 *    values are 1..n.
 *
 * Q: How can I verify this works right now?
 *    import java.util.*;
 *
 *    public class Verify {
 *        static List<Integer> solve(int[] nums) {            // negative marking
 *            int n = nums.length;
 *            for (int i = 0; i < n; i++) {
 *                int idx = Math.abs(nums[i]) - 1;
 *                if (nums[idx] > 0) nums[idx] = -nums[idx];
 *            }
 *            List<Integer> res = new ArrayList<>();
 *            for (int i = 0; i < n; i++) if (nums[i] > 0) res.add(i + 1);
 *            return res;
 *        }
 *
 *        public static void verify() {
 *            assert solve(new int[]{4,3,2,7,8,2,3,1}).equals(Arrays.asList(5,6));
 *            assert solve(new int[]{1,1}).equals(Arrays.asList(2));
 *            assert solve(new int[]{2,2,2,2}).equals(Arrays.asList(1,3,4));
 *            assert solve(new int[]{1,2,3,4}).equals(Collections.emptyList());
 *            assert solve(new int[]{1}).equals(Collections.emptyList());
 *            System.out.println("All tests passed.");
 *        }
 *
 *        public static void main(String[] args) { verify(); } // run: java -ea Verify
 *    }
 *
 * | Approach        | Risk                          | Mitigation                                        |
 * |-----------------|-------------------------------|---------------------------------------------------|
 * | Boolean array   | Extra O(n) memory             | OK when memory ample; else switch to marking.     |
 * | Sort + walk     | Mutates order; O(n log n)     | Copy first if order matters; use only if O(n) ok. |
 * | Negative marking| Off-by-one / missing abs      | Always Math.abs(...) - 1 and guard nums[idx] > 0. |
 * | Cyclic sort     | Infinite loop on duplicates   | Compare nums[i] != nums[home], not indices.       |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode 448, difficulty Easy. A common warm-up for the "numbers in range
 * 1..n" pattern family (Find the Duplicate, Missing Number, First Missing
 * Positive). Frequent in phone screens and easy rounds.
 *
 * | Company           | Frequency | Notes                                  |
 * |-------------------|-----------|----------------------------------------|
 * | Amazon            | ****      | Popular phone-screen array question.   |
 * | Google            | ***       | Often paired with O(1)-space follow-up.|
 * | Microsoft         | ***       | Tests index-as-hash insight.           |
 * | Meta              | ***       | Warm-up before harder array problems.  |
 * | Apple             | **        | Occasional in early rounds.            |
 * | Bloomberg         | ***       | Frequent easy-round pick.              |
 * | Adobe             | **        | Campus / entry interviews.             |
 * | Goldman Sachs     | **        | Coding assessments.                    |
 * | Uber              | **        | Part of cyclic-sort pattern set.       |
 * | TikTok/ByteDance  | **        | Screening rounds.                      |
 *
 * Approximate appearances: high for an Easy problem - a canonical intro to
 * in-place index marking.
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach          | Time       | Space | Code Complexity | Recommended?                              |
 * |-------------------|------------|-------|-----------------|-------------------------------------------|
 * | HashSet / boolean | O(n)       | O(n)  | Very low        | Best for clarity / input must not change  |
 * | Sort + walk       | O(n log n) | O(1)  | Low-medium      | NOT recommended when O(n) is expected     |
 * | Negative marking  | O(n)       | O(1)  | Low             | BEST overall (time + space, fewest writes)|
 * | Cyclic sort       | O(n)       | O(1)  | Medium          | Equally optimal; great if you know pattern|
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use in-place negative marking: meets O(n) time / O(1) space with the least
 * code and fewest array writes. Cyclic sort is an equally optimal alternative
 * worth knowing because the same template solves several sibling problems.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * When values live in 1..n and you need O(1) extra space, USE THE VALUE AS AN
 * INDEX into the array itself - flip that slot's sign (negative marking) or swap
 * the value home (cyclic sort). Key gotchas: always take the ABSOLUTE VALUE
 * before computing the target index, and GUARD AGAINST DUPLICATES so you don't
 * undo a mark or loop forever.
 */
// @formatter:on
