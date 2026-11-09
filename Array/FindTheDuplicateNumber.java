package Array;

import java.util.HashSet;
import java.util.Set;

public class FindTheDuplicateNumber {
    public static void main(String[] args) {
        FindTheDuplicateNumber findTheDuplicateNumber = new FindTheDuplicateNumber();
        System.out.println("FindTheDuplicateNumber : "
                + findTheDuplicateNumber.findDuplicateBruteForce(new int[] { 1, 3, 4, 2, 2 }));
        System.out.println("FindTheDuplicateNumber : "
                + findTheDuplicateNumber.findDuplicateBruteForce(new int[] { 3, 1, 3, 4, 2 }));
        System.out.println("FindTheDuplicateNumber : "
                + findTheDuplicateNumber.findDuplicateBruteForce(new int[] { 3, 3, 3, 3, 3 }));
        System.out.println("--------------------------");
        System.out.println("FindTheDuplicateNumber : "
                + findTheDuplicateNumber.findDuplicateHashSet(new int[] { 1, 3, 4, 2, 2 }));
        System.out.println("FindTheDuplicateNumber : "
                + findTheDuplicateNumber.findDuplicateHashSet(new int[] { 3, 1, 3, 4, 2 }));
        System.out.println("FindTheDuplicateNumber : "
                + findTheDuplicateNumber.findDuplicateHashSet(new int[] { 3, 3, 3, 3, 3 }));
        System.out.println("--------------------------");
        System.out.println("FindTheDuplicateNumber : "
                + findTheDuplicateNumber.findDuplicateBinarySearch(new int[] { 1, 3, 4, 2, 2 }));
        System.out.println("FindTheDuplicateNumber : "
                + findTheDuplicateNumber.findDuplicateBinarySearch(new int[] { 3, 1, 3, 4, 2 }));
        System.out.println("FindTheDuplicateNumber : "
                + findTheDuplicateNumber.findDuplicateBinarySearch(new int[] { 3, 3, 3, 3, 3 }));
        System.out.println("--------------------------");
        System.out.println("FindTheDuplicateNumber : "
                + findTheDuplicateNumber.findDuplicateFloydsCycleDetection(new int[] { 1, 3, 4, 2, 2 }));
        System.out.println("FindTheDuplicateNumber : "
                + findTheDuplicateNumber.findDuplicateFloydsCycleDetection(new int[] { 3, 1, 3, 4, 2 }));
        System.out.println("FindTheDuplicateNumber : "
                + findTheDuplicateNumber.findDuplicateFloydsCycleDetection(new int[] { 3, 3, 3, 3, 3 }));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/find-the-duplicate-number/
     * 
     * Given an array of integers nums containing n + 1 integers where each integer
     * is in the range [1, n] inclusive.
     * 
     * There is only one repeated number in nums, return this repeated number.
     * 
     * You must solve the problem without modifying the array nums and using only
     * constant extra space.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,3,4,2,2]
     * Output: 2
     * Example 2:
     * 
     * Input: nums = [3,1,3,4,2]
     * Output: 3
     * Example 3:
     * 
     * Input: nums = [3,3,3,3,3]
     * Output: 3
     * 
     * 
     * Constraints:
     * 
     * 1 <= n <= 105
     * nums.length == n + 1
     * 1 <= nums[i] <= n
     * All the integers in nums appear only once except for precisely one integer
     * which appears two or more times.
     * 
     * 
     * Follow up:
     * 
     * How can we prove that at least one duplicate number must exist in nums?
     * Can you solve the problem in linear runtime complexity?
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach               | Time       | Space | Code Complexity | Recommended?                          |
     * |------------------------|------------|-------|-----------------|---------------------------------------|
     * | Brute Force            | O(n^2)     | O(1)  | Trivial         | ❌ Times out for large n              |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int findDuplicateBruteForce(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j])
                    return nums[i];
            }
        }
        return -1;
    }

    // @formatter:off
    /**
     * 
     * | Approach               | Time       | Space | Code Complexity | Recommended?                          |
     * |------------------------|------------|-------|-----------------|---------------------------------------|
     * | Hash Set               | O(n)       | O(n)  | Trivial         | ❌ Violates the O(1)-space rule       |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int findDuplicateHashSet(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            if (!seen.add(num))
                return num;
        }
        return -1;
    }

    // @formatter:off
    /**
     * 
     * | Approach               | Time       | Space | Code Complexity | Recommended?                           |
     * |------------------------|------------|-------|-----------------|----------------------------------------|
     * | Binary Search on Value | O(n log n) | O(1)  | Moderate        | ✅ Acceptable — good if Floyd confuses |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int findDuplicateBinarySearch(int[] nums) {
        int low = 1;
        int high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            int count = 0;
            for (int num : nums) {
                if (num <= mid)
                    count++;
            }
            if (count > mid)
                high = mid;
            else
                low = mid + 1;
        }
        return low;
    }

    // @formatter:off
    /**
     * 
     * | Approach               | Time       | Space | Code Complexity | Recommended?                             |
     * |------------------------|------------|-------|-----------------|------------------------------------------|
     * | Floyd's Cycle Detection| O(n)       | O(1)  | Moderate–High   | ✅✅ Best — optimal time & space, no mut|
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int findDuplicateFloydsCycleDetection(int[] nums) {
        int slow = nums[0];
        int fast = nums[0];
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        slow = nums[0];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return fast;
    }
}

// @formatter:off
/*
 * ============================================================
 * FIND THE DUPLICATE NUMBER — DEEP DIVE EXPLANATION
 * (LeetCode #287 — Medium)
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are given an array nums of n + 1 integers, where every value
 * lies in the range [1, n]. By the pigeonhole principle at least one
 * value must repeat. Exactly one value is duplicated (it may appear
 * two or more times); every other value appears exactly once. Return
 * that duplicated value — WITHOUT modifying the array and using only
 * O(1) extra space.
 *
 * This is LeetCode #287 — Find the Duplicate Number (Medium).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums — an array of length n + 1.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * A single int: the value that is repeated.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * - 1 <= n <= 10^5, so nums.length is between 2 and 10^5 + 1.
 * - 1 <= nums[i] <= n (no element is ever 0 and none equals n+1).
 * - Exactly one value is repeated; it may appear two or more times.
 * - Cannot modify nums, and must use constant auxiliary space.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * The single integer value d that occupies more than one index.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    nums = [1, 3, 4, 2, 2]   ->   output: 2
 *    nums = [3, 1, 3, 4, 2]   ->   output: 3
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * The two restrictions (no sorting, no extra memory) kill the
 * obvious solutions. The unlock: because every value is a valid
 * index (values in [1, n], indices in [0, n]), the array secretly
 * describes a linked list. Node i points to node nums[i]. A repeated
 * value means two different nodes point to the same node — i.e. a
 * node with in-degree >= 2, which is exactly the ENTRANCE of a cycle.
 * So "find the duplicate" becomes "find where the cycle begins."
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Compare every pair -> too slow.
 * 2. Throw everything in a hash set -> forbidden extra space.
 * 3. Sort and check neighbors -> forbidden (mutation) and slower.
 * 4. Realize values map onto indices -> functional graph i -> nums[i].
 * 5. Apply Floyd's Tortoise and Hare to find the cycle entrance in
 *    O(n) time and O(1) space, touching nothing.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                  | Why it's tricky                                   |
 * |----------------------------|---------------------------------------------------|
 * | Two constraints at once    | O(1) space AND no mutation kills sort/hash/marking |
 * | Seeing the linked list     | Prompt never says "graph"; values double as index |
 * | Why index 0 is a safe start| Values in [1,n], so nothing points to index 0     |
 * | Cycle entrance = duplicate | Repeated value is the unique node w/ in-degree >=2 |
 * | Floyd's phase-2 reset      | Reset-to-head + step-by-one is unintuitive        |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                     | Key Idea                              | Best Used When                          | Time       | Space |
 * |---|------------------------------|---------------------------------------|-----------------------------------------|------------|-------|
 * | 1 | Brute Force (nested loops)   | Compare every pair (i, j)             | n is tiny; zero cleverness              | O(n^2)     | O(1)  |
 * | 2 | Hash Set                     | First already-seen value is answer    | Extra memory allowed; simplest fast code| O(n)       | O(n)  |
 * | 3 | Binary Search on Value Range | Count elements <= mid; narrow value   | Need O(1) space, tolerate log factor    | O(n log n) | O(1)  |
 * | 4 | Floyd's Cycle Detection  ✅  | Array as linked list; find entrance   | Need optimal time + O(1) space + no mut | O(n)       | O(1)  |
 *
 * Brute force is O(1) space but quadratic time. The hash set buys O(n)
 * time by spending O(n) space (disallowed here). Binary search meets
 * the space rule at the cost of an O(n log n) time factor. Floyd's ✅
 * dominates on both axes among the legal options: O(n) time, O(1)
 * space, and never mutates the array. Prefer binary search only if
 * you find it easier to reason about and the log n factor is fine.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * 1. For each index i, loop over every later index j.
 * 2. If nums[i] == nums[j], that shared value is the duplicate.
 * 3. Guarantee of a duplicate means the loops always return.
 *
 *    public class FindDuplicateBruteForce {
 *        public int findDuplicate(int[] nums) {
 *            for (int i = 0; i < nums.length; i++) {
 *                for (int j = i + 1; j < nums.length; j++) {
 *                    if (nums[i] == nums[j]) {
 *                        return nums[i];
 *                    }
 *                }
 *            }
 *            return -1; // unreachable: duplicate guaranteed
 *        }
 *
 *        public static void main(String[] args) {
 *            FindDuplicateBruteForce solver = new FindDuplicateBruteForce();
 *            int[] nums = {1, 3, 4, 2, 2};
 *            System.out.println(solver.findDuplicate(nums)); // 2
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 2: Hash Set
 * ------------------------------------------------------------
 * 1. Keep a Set<Integer> of seen values.
 * 2. set.add(num) returns false when num is already present.
 * 3. The first such num is the duplicate.
 *
 *    import java.util.HashSet;
 *    import java.util.Set;
 *
 *    public class FindDuplicateHashSet {
 *        public int findDuplicate(int[] nums) {
 *            Set<Integer> seen = new HashSet<>();
 *            for (int num : nums) {
 *                if (!seen.add(num)) { // false if already present
 *                    return num;
 *                }
 *            }
 *            return -1; // unreachable
 *        }
 *
 *        public static void main(String[] args) {
 *            FindDuplicateHashSet solver = new FindDuplicateHashSet();
 *            int[] nums = {3, 1, 3, 4, 2};
 *            System.out.println(solver.findDuplicate(nums)); // 3
 *        }
 *    }
 *
 * NOTE: violates the O(1)-space rule; included as the intuitive baseline.
 *
 * ------------------------------------------------------------
 * Approach 3: Binary Search on Value Range
 * ------------------------------------------------------------
 * 1. Binary-search over the ANSWER VALUE: low = 1, high = n.
 * 2. For a midpoint mid, count how many elements are <= mid.
 * 3. Pigeonhole: duplicate-free run of [1..mid] has at most mid
 *    elements <= mid. If count > mid, duplicate is in [low, mid];
 *    else it is in [mid+1, high].
 * 4. Shrink until low == high -> the duplicate.
 *
 *    public class FindDuplicateBinarySearch {
 *        public int findDuplicate(int[] nums) {
 *            int low = 1;
 *            int high = nums.length - 1; // this is n
 *            while (low < high) {
 *                int mid = low + (high - low) / 2;
 *                int count = 0;
 *                for (int num : nums) {
 *                    if (num <= mid) {
 *                        count++;
 *                    }
 *                }
 *                if (count > mid) {
 *                    high = mid;       // duplicate in lower half
 *                } else {
 *                    low = mid + 1;    // duplicate in upper half
 *                }
 *            }
 *            return low;
 *        }
 *
 *        public static void main(String[] args) {
 *            FindDuplicateBinarySearch solver = new FindDuplicateBinarySearch();
 *            int[] nums = {1, 3, 4, 2, 2};
 *            System.out.println(solver.findDuplicate(nums)); // 2
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 4: Floyd's Cycle Detection  ✅ (Optimal)
 * ------------------------------------------------------------
 * 1. Read the array as a linked list: from node x, next is nums[x].
 *    Start at index 0 (safe — nothing points to it).
 * 2. Phase 1: advance slow by one hop, fast by two hops until they
 *    collide inside the cycle.
 * 3. Phase 2: reset slow to the start, move both by one hop; they
 *    meet at the cycle entrance = the duplicate value.
 *
 *    public class FindDuplicateFloyd {
 *        public int findDuplicate(int[] nums) {
 *            // Phase 1: find an intersection point inside the cycle
 *            int slow = nums[0];
 *            int fast = nums[0];
 *            do {
 *                slow = nums[slow];          // one hop
 *                fast = nums[nums[fast]];    // two hops
 *            } while (slow != fast);
 *
 *            // Phase 2: find the entrance (= the duplicate)
 *            slow = nums[0];
 *            while (slow != fast) {
 *                slow = nums[slow];
 *                fast = nums[fast];
 *            }
 *            return slow;
 *        }
 *
 *        public static void main(String[] args) {
 *            FindDuplicateFloyd solver = new FindDuplicateFloyd();
 *            int[] nums = {1, 3, 4, 2, 2};
 *            System.out.println(solver.findDuplicate(nums)); // 2
 *        }
 *    }
 *
 * Why phase-2 reset works: let F be head->entrance distance, a be
 * entrance->meeting distance, C the cycle length. At meeting,
 * F ≡ (C − a) (mod C). Restarting one pointer at the head and stepping
 * both by one realigns them exactly at the entrance after F steps.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Approach 1: Brute Force
 *   Time  O(n^2): outer ~n+1, inner up to n => ~(n+1)*n/2. For
 *         n=10^5 that is ~5x10^9 ops — too slow.
 *   Space O(1): only loop counters.
 *
 * Approach 2: Hash Set
 *   Time  O(n): one pass, amortized O(1) per add/lookup. ~10^5 ops.
 *   Space O(n): set may hold nearly all n distinct values. Disqualifying.
 *
 * Approach 3: Binary Search on Value Range
 *   Time  O(n log n): range [1,n] halves ~log2 n (~17) times, each
 *         step scans all n+1 elements. ~1.7x10^6 ops for n=10^5.
 *   Space O(1): only low, high, mid, count.
 *
 * Approach 4: Floyd's Cycle Detection  ✅
 *   Time  O(n): each phase does a linear number of hops. ~2x10^5.
 *   Space O(1): two integer pointers.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * Using nums = [1, 3, 4, 2, 2] (indices 0..4), duplicate is 2.
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 *    i=0 (1): compare 3,4,2,2 -> no match
 *    i=1 (3): compare 4,2,2   -> no match
 *    i=2 (4): compare 2,2     -> no match
 *    i=3 (2): compare nums[4]=2 -> MATCH -> return 2
 *
 * ------------------------------------------------------------
 * Approach 2: Hash Set  (using [3,1,3,4,2] to show an early hit)
 * ------------------------------------------------------------
 *    num=3 -> set={3}
 *    num=1 -> set={1,3}
 *    num=3 -> already in set -> return 3
 *
 * ------------------------------------------------------------
 * Approach 3: Binary Search on Value Range  (low=1, high=4)
 * ------------------------------------------------------------
 * | Step | low | high | mid | count(<= mid) | Decision                    |
 * |------|-----|------|-----|---------------|-----------------------------|
 * | 1    | 1   | 4    | 2   | {1,2,2}=3     | 3>2 -> lower half -> high=2 |
 * | 2    | 1   | 2    | 1   | {1}=1         | 1<=1 -> upper half -> low=2 |
 * | 3    | 2   | 2    | —   | —             | low==high -> return 2       |
 *
 * ------------------------------------------------------------
 * Approach 4: Floyd's Cycle Detection
 * ------------------------------------------------------------
 * List view: 0->1, 1->3, 2->4, 3->2, 4->2. From 0: 0->1->3->2->4->2...
 * cycle 2<->4, entrance 2.
 *
 * Phase 1 (start slow=fast=nums[0]=1):
 *    slow=nums[1]=3 ; fast=nums[nums[1]]=nums[3]=2   -> slow=3, fast=2 (differ)
 *    slow=nums[3]=2 ; fast=nums[nums[2]]=nums[4]=2   -> slow=2, fast=2 (MEET at 2)
 *
 * Phase 2 (reset slow=nums[0]=1, keep fast=2):
 *    slow=1, fast=2 (differ) -> slow=nums[1]=3, fast=nums[2]=4
 *    slow=3, fast=4 (differ) -> slow=nums[3]=2, fast=nums[4]=2
 *    slow=2, fast=2 (MEET)   -> return 2
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                          | Input             | Expected | How Handled                                         |
 * |------------------------------------|-------------------|----------|-----------------------------------------------------|
 * | Minimum array (n = 1)              | [1, 1]            | 1        | slow=fast=nums[0]=1; phase1 meets, phase2 returns 1 |
 * | Duplicate appears many times       | [2, 2, 2, 2, 2]   | 2        | Tight self-cycle; all approaches return 2           |
 * | Duplicate at the ends              | [1, 3, 4, 2, 2]   | 2        | Handled by every approach (traced above)            |
 * | Duplicate is the largest value     | [1, 2, 3, 4, 4]   | 4        | Binary search narrows to 4; Floyd entrance is 4     |
 * | Unrelated self-loop, not the answer| [3, 1, 3, 4, 2]   | 3        | Node 1->1 never entered; traversal funnels to 3     |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * - Inconsistent Floyd start/reset:
 *      // WRONG: mixing a 0-start in phase 1 with a nums[0] reset in phase 2
 *      // CORRECT: canonical form — both start at nums[0], reset slow to nums[0]
 *      int slow = nums[0], fast = nums[0];
 * - Overflow in binary-search midpoint:
 *      int mid = (low + high) / 2;        // WRONG: can overflow
 *      int mid = low + (high - low) / 2;  // CORRECT
 * - Index-marking / negation trick finds the duplicate in O(n)/O(1)
 *   but MUTATES the array — violates the constraint. Do not use it here.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: Smallest input [1,1] (phase 2 must not underflow), duplicate
 *    equal to n (upper search bound), and arrays with an unrelated
 *    self-loop node that must never be entered. All covered by
 *    starting at index 0, which is outside every cycle.
 *
 * Q: Are there any type mismatches?
 * A: All values fit in int (n <= 10^5); no long needed. Guard the
 *    binary-search midpoint with low + (high - low) / 2. The hash set
 *    autoboxes int -> Integer, the very memory cost we avoid.
 *
 * Q: How can I verify this works right now?
 *
 *    public class FindDuplicateVerify {
 *        public int findDuplicate(int[] nums) {
 *            int slow = nums[0], fast = nums[0];
 *            do {
 *                slow = nums[slow];
 *                fast = nums[nums[fast]];
 *            } while (slow != fast);
 *            slow = nums[0];
 *            while (slow != fast) {
 *                slow = nums[slow];
 *                fast = nums[fast];
 *            }
 *            return slow;
 *        }
 *
 *        public void verify() {
 *            assert findDuplicate(new int[]{1, 3, 4, 2, 2}) == 2;
 *            assert findDuplicate(new int[]{3, 1, 3, 4, 2}) == 3;
 *            assert findDuplicate(new int[]{1, 1})          == 1;
 *            assert findDuplicate(new int[]{2, 2, 2, 2, 2}) == 2;
 *            assert findDuplicate(new int[]{1, 2, 3, 4, 4}) == 4;
 *            System.out.println("All assertions passed.");
 *        }
 *
 *        public static void main(String[] args) {
 *            new FindDuplicateVerify().verify(); // run with: java -ea FindDuplicateVerify
 *        }
 *    }
 *
 * | Approach      | Risk                                             | Mitigation                          |
 * |---------------|--------------------------------------------------|-------------------------------------|
 * | Brute Force   | O(n^2) times out for large n                     | Use only when n is small            |
 * | Hash Set      | O(n) space breaks the constraint                 | Switch to Floyd's or binary search  |
 * | Binary Search | Midpoint overflow; off-by-one on count > vs >=   | low+(high-low)/2; strictly count>mid|
 * | Floyd's       | Inconsistent start/reset; assumes a duplicate    | Keep phases consistent; rely on guar|
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #287 — Find the Duplicate Number — Difficulty: Medium.
 * A perennial favorite for the array-as-linked-list reframing;
 * appears very frequently in FAANG-tier loops.
 *
 * | Company            | Frequency | Notes                                         |
 * |--------------------|-----------|-----------------------------------------------|
 * | Amazon             | ⭐⭐⭐⭐⭐ | Very common; "why O(1) space" follow-up        |
 * | Google             | ⭐⭐⭐⭐⭐ | Expects the Floyd's insight, not just hash set |
 * | Microsoft          | ⭐⭐⭐⭐   | Frequent in phone screens                      |
 * | Meta               | ⭐⭐⭐⭐   | Likes the "no modification" discussion         |
 * | Apple              | ⭐⭐⭐⭐   | Appears in array-focused rounds                |
 * | Bloomberg          | ⭐⭐⭐⭐   | Common; sometimes accepts binary search        |
 * | Adobe              | ⭐⭐⭐     | Mid-frequency, often the hash-set version      |
 * | Oracle             | ⭐⭐⭐     | Shows up in screening rounds                    |
 * | Uber               | ⭐⭐⭐     | Asked alongside cycle-detection problems       |
 * | TikTok / ByteDance | ⭐⭐⭐     | Increasingly common in recent loops            |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach               | Time       | Space | Code Complexity | Recommended?                          |
 * |------------------------|------------|-------|-----------------|---------------------------------------|
 * | Brute Force            | O(n^2)     | O(1)  | Trivial         | ❌ Times out for large n              |
 * | Hash Set               | O(n)       | O(n)  | Trivial         | ❌ Violates the O(1)-space rule       |
 * | Binary Search on Value | O(n log n) | O(1)  | Moderate        | ✅ Acceptable — good if Floyd confuses|
 * | Floyd's Cycle Detection| O(n)       | O(1)  | Moderate–High   | ✅✅ Best — optimal time & space, no mut|
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Floyd's Tortoise and Hare — the only solution that is simultaneously
 * O(n) time, O(1) space, and leaves the array untouched. Use binary
 * search only when it's easier to justify under pressure and the extra
 * log n factor is acceptable.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * When an array's values are all valid indices, treat it as a
 * functional linked list where a duplicate value becomes a CYCLE
 * ENTRANCE — Floyd's two-phase pointer walk finds that entrance in
 * O(1) space. Key gotcha: index 0 is a guaranteed-safe head precisely
 * because values are in [1, n], so nothing ever points back to it.
 *
 */
// @formatter:on
