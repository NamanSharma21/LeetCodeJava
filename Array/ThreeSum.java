package Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ThreeSum {
    public static void main(String[] args) {
        ThreeSum threeSum = new ThreeSum();
        System.out.println("ThreeSum : " + threeSum.threeSumSortTwoPointers(new int[] { -1, 0, 1, 2, -1, -4 }));
        System.out.println("-------------------------------------");
        System.out.println("ThreeSum : " + threeSum.threeSumHashSet(new int[] { -1, 0, 1, 2, -1, -4 }));
        System.out.println("-------------------------------------");
        System.out.println("ThreeSum : " + threeSum.threeSumBruteForce(new int[] { -1, 0, 1, 2, -1, -4 }));
    }

    // @formatter:off
    /**
     * 
     * https://leetcode.com/problems/3sum/description/?envType=problem-list-v2&envId=array
     * 
     * Given an integer array nums, return all the triplets [nums[i], nums[j],
     * nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] +
     * nums[k] == 0.
     * 
     * Notice that the solution set must not contain duplicate triplets.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [-1,0,1,2,-1,-4]
     * Output: [[-1,-1,2],[-1,0,1]]
     * Explanation:
     * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
     * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
     * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
     * The distinct triplets are [-1,0,1] and [-1,-1,2].
     * Notice that the order of the output and the order of the triplets does not
     * matter.
     * Example 2:
     * 
     * Input: nums = [0,1,1]
     * Output: []
     * Explanation: The only possible triplet does not sum up to 0.
     * Example 3:
     * 
     * Input: nums = [0,0,0]
     * Output: [[0,0,0]]
     * Explanation: The only possible triplet sums up to 0.
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * | Approach                   | Time   | Space      | Code Complexity | Recommended?                    |
     * |----------------------------|--------|------------|-----------------|---------------------------------|
     * | Sort + Two Pointers        | O(n^2) | O(1) aux   | Medium          | ✅✅ best overall (least space) |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public List<List<Integer>> threeSumSortTwoPointers(int[] nums) {
        int n = nums.length;
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < n - 2; i++) {
            if (nums[i] > 0)
                break;
            if (i > 0 && nums[i] == nums[i - 1])
                continue;

            int left = i + 1, right = n - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1])
                        left++;
                    while (left < right && nums[right] == nums[right - 1])
                        right--;
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }

    // @formatter:off
    /**
     * | Approach                   | Time   | Space      | Code Complexity | Recommended?                    |
     * |----------------------------|--------|------------|-----------------|---------------------------------|
     * | HashSet per Anchor         | O(n^2) | O(n) aux   | Medium          | ✅ acceptable; avoids pointers  |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public List<List<Integer>> threeSumHashSet(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
            Set<Integer> seen = new HashSet<>();
            for (int j = i + 1; j < n; j++) {
                int complement = -nums[i] - nums[j];
                if (seen.contains(complement)) {
                    result.add(Arrays.asList(nums[i], complement, nums[j]));
                    while (j + 1 < n && nums[j] == nums[j + 1])
                        j++;
                }
                seen.add(nums[j]);
            }
        }
        return result;
    }

    // @formatter:off
    /**
     * | Approach                   | Time   | Space      | Code Complexity | Recommended?                    |
     * |----------------------------|--------|------------|-----------------|---------------------------------|
     * | Brute Force (3 loops+Set)  | O(n^3) | O(m) result| Low             | ❌ TLE for large n; validation  |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public List<List<Integer>> threeSumBruteForce(int[] nums) {

        int n = nums.length;
        Set<List<Integer>> uniqueTriplets = new HashSet<>();
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> triplets = Arrays.asList(nums[i], nums[j], nums[k]);
                        Collections.sort(triplets);
                        uniqueTriplets.add(triplets);
                    }
                }
            }
        }
        return new ArrayList<>(uniqueTriplets);
    }
}
// @formatter:off
/*
 * ============================================================
 * 3Sum — Deep Dive Explanation
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an array of integers, find every UNIQUE triplet of elements that
 * sum to zero. "Unique" is the key word: even if the same numeric triplet
 * can be formed from different index positions, it should appear only once
 * in the output.
 *
 * This is LeetCode #15, difficulty Medium.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums — an integer array. Elements may be negative, zero, or positive,
 * and duplicates are allowed.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * List<List<Integer>> — a list of triplets. Each inner list has exactly 3
 * integers summing to 0. Order of triplets and order within a triplet do not
 * matter, but no two triplets may be duplicates of each other.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * 3 <= nums.length <= 3000
 * -10^5 <= nums[i] <= 10^5
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Find all sets of three DISTINCT index positions (i, j, k) such that
 * nums[i] + nums[j] + nums[k] == 0, then collapse any value-duplicate triplets
 * into a single representative.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  nums = [-1, 0, 1, 2, -1, -4]
 *    Output: [[-1, -1, 2], [-1, 0, 1]]
 *
 *    Explanation:
 *      (-1) + (-1) + 2 = 0   OK
 *      (-1) +   0  + 1 = 0   OK
 *      The triplet [-1, 0, 1] can be formed via two different index sets,
 *      but appears only once in the output.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * 3Sum is really "Two Sum" run many times. Fix one number, and the remaining
 * task becomes: "find two other numbers that sum to the negative of the fixed
 * one." If I fix x, I now hunt for a pair that sums to -x. That reduces a
 * three-dimensional search to a familiar two-dimensional one.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Sort the numbers so they line up small -> large.
 * 2. Point at the first number and treat it as anchor x. Target is -x.
 * 3. Place one finger just after the anchor (left) and one at the far end (right).
 * 4. If the two values are too small, slide left up; if too big, slide right down;
 *    if exact, record the triplet.
 * 5. Skip over any repeated values so you never record the same triplet twice.
 * 6. Move the anchor forward and repeat.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                     | Why it's tricky                                             |
 * |-------------------------------|-------------------------------------------------------------|
 * | Avoiding duplicate triplets   | Same values can arise from different indices.               |
 * | Deduplicating efficiently     | A Set works but costs memory/hashing; in-place skip cleaner.|
 * | Three moving parts            | Anchor + two converging pointers; off-by-one errors easy.   |
 * | Sorting side effect           | Sorting destroys original indices (fine here — values only).|
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                  | Key Idea                                  | Best Used When              | Time   | Space          |
 * |---|---------------------------|-------------------------------------------|-----------------------------|--------|----------------|
 * | 1 | Brute Force (3 loops+Set) | Test every triple (i,j,k); dedup via Set  | Tiny inputs / baseline      | O(n^3) | O(m) result    |
 * | 2 | HashSet per anchor        | Fix i, run hash Two Sum on the rest       | Want O(n^2), pointers fiddly| O(n^2) | O(n) aux       |
 * | 3 | Sort + Two Pointers  ✅   | Sort, fix anchor, converge, skip dups     | General/optimal case        | O(n^2) ✅ | O(1)* aux ✅  |
 *
 * * Excluding output and the sort's internal overhead, approach 3 uses only
 * constant auxiliary space, whereas approach 2 allocates a hash set per anchor.
 *
 * Approaches 2 and 3 share O(n^2) time, so time alone doesn't separate them —
 * SPACE does. The hash-set method carries an O(n) set and builds keys to dedup;
 * sort + two pointers dedups by skipping equal neighbors with only a few pointer
 * variables. Approach 3 is recommended: same time, strictly leaner space, cleaner
 * duplicate handling. Use brute force only to validate a small case; prefer the
 * hash-set variant only if you specifically want to avoid sorting the input.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (3 Loops + Set)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Iterate i in [0,n-3], j in [i+1,n-2], k in [j+1,n-1].
 * 2. Check whether the three values sum to zero.
 * 3. If so, sort the three values and store in a Set to eliminate duplicates.
 * 4. Convert the set to a list at the end.
 *
 *    import java.util.*;
 *
 *    public class ThreeSumBruteForce {
 *        public static List<List<Integer>> threeSum(int[] nums) {
 *            Set<List<Integer>> uniqueTriplets = new HashSet<>();
 *            int n = nums.length;
 *
 *            for (int i = 0; i < n - 2; i++) {
 *                for (int j = i + 1; j < n - 1; j++) {
 *                    for (int k = j + 1; k < n; k++) {
 *                        if (nums[i] + nums[j] + nums[k] == 0) {
 *                            List<Integer> triplet =
 *                                Arrays.asList(nums[i], nums[j], nums[k]);
 *                            Collections.sort(triplet); // canonical order
 *                            uniqueTriplets.add(triplet);
 *                        }
 *                    }
 *                }
 *            }
 *            return new ArrayList<>(uniqueTriplets);
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {-1, 0, 1, 2, -1, -4};
 *            System.out.println(threeSum(nums));
 *            // Expected (order may vary): [[-1, -1, 2], [-1, 0, 1]]
 *        }
 *    }
 *
 * Sorting each 3-element triplet is O(1) work, so it does not change the cubic
 * bound. The Set guarantees uniqueness via canonicalized (sorted) lists.
 *
 * ------------------------------------------------------------
 * Approach 2: HashSet per Anchor
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Sort the array so duplicate anchors can be skipped cleanly.
 * 2. For each anchor i, walk the rest with a hash set 'seen'.
 * 3. For each later nums[j], compute complement = -nums[i]-nums[j]. If it is in
 *    'seen', found a triplet [nums[i], complement, nums[j]].
 * 4. Skip anchor duplicates and inner duplicates to avoid repeats.
 * 5. Add nums[j] to 'seen' and continue.
 *
 *    import java.util.*;
 *
 *    public class ThreeSumHashSet {
 *        public static List<List<Integer>> threeSum(int[] nums) {
 *            Arrays.sort(nums);
 *            List<List<Integer>> result = new ArrayList<>();
 *            int n = nums.length;
 *
 *            for (int i = 0; i < n - 2; i++) {
 *                if (i > 0 && nums[i] == nums[i - 1]) continue; // skip dup anchors
 *                Set<Integer> seen = new HashSet<>();
 *                for (int j = i + 1; j < n; j++) {
 *                    int complement = -nums[i] - nums[j];
 *                    if (seen.contains(complement)) {
 *                        result.add(Arrays.asList(nums[i], complement, nums[j]));
 *                        while (j + 1 < n && nums[j] == nums[j + 1]) j++;
 *                    }
 *                    seen.add(nums[j]);
 *                }
 *            }
 *            return result;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {-1, 0, 1, 2, -1, -4};
 *            System.out.println(threeSum(nums));
 *            // Expected: [[-1, -1, 2], [-1, 0, 1]]
 *        }
 *    }
 *
 * Sorting up front lets the anchor-skip suppress duplicate outer values, and the
 * inner while-skip suppresses duplicate matches. A match is emitted only when the
 * complement appeared BEFORE nums[j], so each value-triplet is produced once per
 * anchor.
 *
 * ------------------------------------------------------------
 * Approach 3: Sort + Two Pointers  ✅ (Optimal)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Sort the array ascending.
 * 2. Loop anchor i upward. If nums[i] > 0, break early.
 * 3. Skip the anchor if it equals the previous anchor value.
 * 4. Set left = i+1, right = n-1.
 * 5. While left < right, compute sum = nums[i]+nums[left]+nums[right].
 *    - sum == 0: record triplet, then advance left/right past equal values.
 *    - sum < 0:  left++  (need a bigger sum).
 *    - sum > 0:  right-- (need a smaller sum).
 *
 *    import java.util.*;
 *
 *    public class ThreeSumTwoPointers {
 *        public static List<List<Integer>> threeSum(int[] nums) {
 *            Arrays.sort(nums);
 *            List<List<Integer>> result = new ArrayList<>();
 *            int n = nums.length;
 *
 *            for (int i = 0; i < n - 2; i++) {
 *                if (nums[i] > 0) break;                        // no zero-sum beyond
 *                if (i > 0 && nums[i] == nums[i - 1]) continue; // skip dup anchors
 *
 *                int left = i + 1, right = n - 1;
 *                while (left < right) {
 *                    int sum = nums[i] + nums[left] + nums[right];
 *                    if (sum == 0) {
 *                        result.add(Arrays.asList(nums[i], nums[left], nums[right]));
 *                        while (left < right && nums[left] == nums[left + 1]) left++;
 *                        while (left < right && nums[right] == nums[right - 1]) right--;
 *                        left++;
 *                        right--;
 *                    } else if (sum < 0) {
 *                        left++;
 *                    } else {
 *                        right--;
 *                    }
 *                }
 *            }
 *            return result;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {-1, 0, 1, 2, -1, -4};
 *            System.out.println(threeSum(nums));
 *            // Expected: [[-1, -1, 2], [-1, 0, 1]]
 *        }
 *    }
 *
 * The nums[i] > 0 break is a real optimization: once the smallest of the three is
 * positive in a sorted array, every later triplet is strictly positive. The two
 * inner while loops advance past equal neighbors BEFORE the final left++/right--,
 * so the next comparison starts on fresh values.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Time:  C(n,3) ~= n^3/6 -> O(n^3). For n=3000, ~4.5e9 checks — too slow.
 * Space: O(m) for stored triplets; worst case O(n^2). Auxiliary O(1) otherwise.
 *
 * ------------------------------------------------------------
 * Approach 2: HashSet per Anchor
 * ------------------------------------------------------------
 * Time:  O(n) outer * O(n) inner = O(n^2); sort O(n log n) dominated.
 *        For n=3000, ~9e6 inner operations — fast.
 * Space: per-anchor 'seen' up to O(n); recreated but not additive -> O(n) aux.
 *
 * ------------------------------------------------------------
 * Approach 3: Sort + Two Pointers  ✅
 * ------------------------------------------------------------
 * Time:  Sort O(n log n) + n anchors * O(n) sweep = O(n^2) dominates.
 *        For n=3000, ~4.5e6 pointer steps.
 * Space: fixed set of variables -> O(1) auxiliary (excluding output & sort).
 *        Leanest of the three.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * For all examples, nums = [-1, 0, 1, 2, -1, -4].
 *
 * ------------------------------------------------------------
 * Approach 1 trace (Brute Force)
 * ------------------------------------------------------------
 *    (i,j,k) values -> sum -> action
 *    ├─ (-1, 0, 1)   -> 0  -> add [-1, 0, 1]
 *    ├─ (-1, 0, -1)  -> -2 -> skip
 *    ├─ (-1, 2, -1)  -> 0  -> add [-1, -1, 2]
 *    ├─ (0, 1, -1)   -> 0  -> duplicate of [-1, 0, 1], set ignores
 *    ├─ (1, 2, -4)   -> -1 -> skip
 *    └─ ... remaining triples -> none new
 *    Final set: { [-1, 0, 1], [-1, -1, 2] }
 *
 * ------------------------------------------------------------
 * Approach 2 trace (HashSet per Anchor)
 * ------------------------------------------------------------
 * After sorting: [-4, -1, -1, 0, 1, 2]
 *    anchor i=0 (-4), seen={}
 *      j=1 -1 comp=5  miss -> seen={-1}
 *      j=2 -1 comp=5  miss -> seen={-1}
 *      j=3  0 comp=4  miss -> seen={-1,0}
 *      j=4  1 comp=3  miss -> seen={-1,0,1}
 *      j=5  2 comp=2  miss -> seen={-1,0,1,2}
 *    anchor i=1 (-1), seen={}
 *      j=2 -1 comp=2  miss -> seen={-1}
 *      j=3  0 comp=1  miss -> seen={-1,0}
 *      j=4  1 comp=0  hit  -> add [-1, 0, 1]; seen={-1,0,1}
 *      j=5  2 comp=-1 hit  -> add [-1, -1, 2]
 *    anchor i=2 skipped (dup -1)
 *    anchor i=3 (0)
 *      j=4 1 comp=-1 miss -> seen={1}
 *      j=5 2 comp=-2 miss
 *    Result: [[-1, 0, 1], [-1, -1, 2]]
 *
 * ------------------------------------------------------------
 * Approach 3 trace (Sort + Two Pointers)
 * ------------------------------------------------------------
 * Sorted: [-4, -1, -1, 0, 1, 2] (indices 0..5)
 *
 * | i | nums[i] | left | right | sum | action                                 |
 * |---|---------|------|-------|-----|----------------------------------------|
 * | 0 | -4      | 1    | 5     | -3  | sum<0 -> left++                        |
 * | 0 | -4      | 2    | 5     | -3  | sum<0 -> left++                        |
 * | 0 | -4      | 3    | 5     | -2  | sum<0 -> left++                        |
 * | 0 | -4      | 4    | 5     | -1  | sum<0 -> left++                        |
 * | 0 | -4      | 5    | 5     | --  | left==right -> stop                     |
 * | 1 | -1      | 2    | 5     | 0   | add [-1,-1,2]; left->3, right->4        |
 * | 1 | -1      | 3    | 4     | 0   | add [-1,0,1];  left->4, right->3 stop   |
 * | 2 | -1      | --   | --    | --  | skipped (dup anchor)                   |
 * | 3 | 0       | 4    | 5     | 3   | sum>0 -> right-- -> stop                |
 * | 4 | 1       | --   | --    | --  | nums[i]>0 -> break                     |
 *
 *    Final output: [[-1, -1, 2], [-1, 0, 1]]
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                | Input             | Expected Output | How Handled                             |
 * |--------------------------|-------------------|-----------------|-----------------------------------------|
 * | Fewer than 3 elements    | [0, 1]            | []              | Loop bound i<n-2 never executes         |
 * | No triplet sums to zero  | [1, 2, 3]         | []              | Two pointers never hit sum==0           |
 * | All zeros                | [0, 0, 0, 0]      | [[0, 0, 0]]     | Anchor + pointer skips collapse dups    |
 * | Many duplicates          | [-2,0,0,2,2]      | [[-2, 0, 2]]    | Neighbor-equal skips prevent repeats    |
 * | All positive             | [1, 2, 3, 4]      | []              | nums[i]>0 break exits immediately       |
 * | Large mixed values       | [-1e5, 5, ...]    | valid triplets  | Three ints sum ~3e5, safe in int range  |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 — forgetting to skip duplicate anchors:
 *    // WRONG: emits [-1,0,1] twice when -1 repeats
 *    for (int i = 0; i < n - 2; i++) { ... }
 *    // CORRECT:
 *    for (int i = 0; i < n - 2; i++) {
 *        if (i > 0 && nums[i] == nums[i - 1]) continue;
 *        ...
 *    }
 *
 * Pitfall 2 — skipping inner duplicates BEFORE recording (misses valid triplets):
 *    // WRONG:
 *    if (sum == 0) {
 *        while (nums[left] == nums[left + 1]) left++;
 *        result.add(...); // may now point at the wrong pair
 *    }
 *    // CORRECT:
 *    if (sum == 0) {
 *        result.add(Arrays.asList(nums[i], nums[left], nums[right]));
 *        while (left < right && nums[left] == nums[left + 1]) left++;
 *        while (left < right && nums[right] == nums[right - 1]) right--;
 *        left++; right--;
 *    }
 *
 * Pitfall 3 — out-of-bounds in the skip loop: always guard left < right inside
 * the while-skips, or nums[left + 1] can read past right.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: Arrays shorter than 3 (guarded by loop bound), all-equal arrays (guarded by
 *    neighbor skips), and boundary values near +/-1e5 (safe: three ints sum to at
 *    most 3e5, inside int's ~2.1e9 range). No long cast needed here.
 *
 * Q: Are there any type mismatches?
 * A: Arrays.asList(nums[i], ...) autoboxes int to Integer, producing List<Integer>,
 *    matching the List<List<Integer>> return type. No narrowing/unchecked casts.
 *
 * Q: How can I verify this works right now?
 *    import java.util.*;
 *
 *    public class ThreeSumVerify {
 *        // ... include threeSum from Approach 3 ...
 *
 *        static void verify() {
 *            assert threeSum(new int[]{-1,0,1,2,-1,-4}).size() == 2 : "classic";
 *            assert threeSum(new int[]{0,0,0}).equals(
 *                     List.of(List.of(0,0,0))) : "all zeros";
 *            assert threeSum(new int[]{1,2,3}).isEmpty() : "no triplet";
 *            assert threeSum(new int[]{}).isEmpty() : "empty";
 *            System.out.println("All assertions passed.");
 *        }
 *
 *        public static void main(String[] args) {
 *            verify(); // run with:  java -ea ThreeSumVerify
 *        }
 *    }
 *
 * | Approach            | Risk                              | Mitigation                          |
 * |---------------------|-----------------------------------|-------------------------------------|
 * | Brute Force         | Cubic blowup at n=3000 (TLE)      | Use only for validation on tiny in. |
 * | HashSet per Anchor  | Extra O(n) memory + key hashing   | Prefer two-pointer when memory tight|
 * | Sort + Two Pointers | Off-by-one in dedup skips         | Guard skips; record before skipping |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #15, difficulty Medium. One of the most frequently asked array
 * problems; tens of thousands of reported interview appearances.
 *
 * | Company           | Frequency (stars) | Notes                                |
 * |-------------------|-------------------|--------------------------------------|
 * | Amazon            | ⭐⭐⭐⭐⭐        | Extremely common phone/onsite screen |
 * | Google            | ⭐⭐⭐⭐⭐        | Often extended to kSum follow-ups    |
 * | Meta (Facebook)   | ⭐⭐⭐⭐⭐        | Classic pointer + dedup discussion   |
 * | Microsoft         | ⭐⭐⭐⭐          | Frequently paired with Two Sum       |
 * | Apple             | ⭐⭐⭐⭐          | Asked as a warm-up array round       |
 * | Bloomberg         | ⭐⭐⭐⭐          | Popular for its dedup subtlety       |
 * | Adobe             | ⭐⭐⭐            | Appears in medium-tier rounds        |
 * | Uber              | ⭐⭐⭐            | Sometimes as 3Sum Closest variant    |
 * | Goldman Sachs     | ⭐⭐⭐            | Common in quant/dev screens          |
 * | TikTok/ByteDance  | ⭐⭐⭐            | Frequent two-pointer question        |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach                   | Time   | Space      | Code Complexity | Recommended?                    |
 * |----------------------------|--------|------------|-----------------|---------------------------------|
 * | Brute Force (3 loops+Set)  | O(n^3) | O(m) result| Low             | ❌ TLE for large n; validation  |
 * | HashSet per Anchor         | O(n^2) | O(n) aux   | Medium          | ✅ acceptable; avoids pointers  |
 * | Sort + Two Pointers        | O(n^2) | O(1) aux   | Medium          | ✅✅ best overall (least space) |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use Sort + Two Pointers. It matches the best known O(n^2) time while using only
 * constant auxiliary space and handling duplicates by neighbor-skipping. Since no
 * approach beats O(n^2) time and this one also wins on space, there is no
 * time-vs-space trade-off — it dominates the hash-set variant on space.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * 3Sum = sort, then fix an anchor and collapse the rest into Two Sum with
 * converging pointers. The single most important detail is duplicate suppression:
 * skip equal anchor values, and after recording a hit, skip equal left/right
 * neighbors BEFORE the final advance. The nums[i] > 0 early break is the free
 * optimization interviewers love to see.
 *
 */
// @formatter:on
