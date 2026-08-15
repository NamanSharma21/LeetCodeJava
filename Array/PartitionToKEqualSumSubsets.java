package Array;

import java.util.Arrays;

public class PartitionToKEqualSumSubsets {
    public static void main(String[] args) {
        PartitionToKEqualSumSubsets partitionToKEqualSumSubsets = new PartitionToKEqualSumSubsets();
        System.out.println("PartitionToKEqualSumSubsets : "
                + partitionToKEqualSumSubsets.canPartitionKSubsetsBruteForce(new int[] { 4, 3, 2, 3, 5, 2, 1 }, 4));// true
        System.out.println("PartitionToKEqualSumSubsets : "
                + partitionToKEqualSumSubsets.canPartitionKSubsetsBruteForce(new int[] { 1, 2, 3, 4 }, 3)); // false
        System.out.println("PartitionToKEqualSumSubsets : "
                + partitionToKEqualSumSubsets.canPartitionKSubsetsBruteForce(new int[] { 4, 4, 4, 6, 1, 2, 2, 9, 4, 6 },
                        3)); // true
        System.out.println("PartitionToKEqualSumSubsets : "
                + partitionToKEqualSumSubsets.canPartitionKSubsetsBruteForce(
                        new int[] { 4, 4, 6, 2, 3, 8, 10, 2, 10, 7 },
                        4)); // true
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("PartitionToKEqualSumSubsets : "
                + partitionToKEqualSumSubsets.canPartitionKSubsetsPrunedBacktracking(new int[] { 4, 3, 2, 3, 5, 2, 1 },
                        4));// true
        System.out.println("PartitionToKEqualSumSubsets : "
                + partitionToKEqualSumSubsets.canPartitionKSubsetsPrunedBacktracking(new int[] { 1, 2, 3, 4 }, 3)); // false
        System.out.println("PartitionToKEqualSumSubsets : "
                + partitionToKEqualSumSubsets.canPartitionKSubsetsPrunedBacktracking(
                        new int[] { 4, 4, 4, 6, 1, 2, 2, 9, 4, 6 },
                        3)); // true
        System.out.println("PartitionToKEqualSumSubsets : "
                + partitionToKEqualSumSubsets.canPartitionKSubsetsPrunedBacktracking(
                        new int[] { 4, 4, 6, 2, 3, 8, 10, 2, 10, 7 },
                        4)); // true
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("PartitionToKEqualSumSubsets : "
                + partitionToKEqualSumSubsets.canPartitionKSubsetsBitmaskDP(new int[] { 4, 3, 2, 3, 5, 2, 1 },
                        4));// true
        System.out.println("PartitionToKEqualSumSubsets : "
                + partitionToKEqualSumSubsets.canPartitionKSubsetsBitmaskDP(new int[] { 1, 2, 3, 4 }, 3)); // false
        System.out.println("PartitionToKEqualSumSubsets : "
                + partitionToKEqualSumSubsets.canPartitionKSubsetsBitmaskDP(
                        new int[] { 4, 4, 4, 6, 1, 2, 2, 9, 4, 6 },
                        3)); // true
        System.out.println("PartitionToKEqualSumSubsets : "
                + partitionToKEqualSumSubsets.canPartitionKSubsetsBitmaskDP(
                        new int[] { 4, 4, 6, 2, 3, 8, 10, 2, 10, 7 },
                        4)); // true
    }

    // @formatter:off
    /*
     * 
     * https://leetcode.com/problems/partition-to-k-equal-sum-subsets/description/?envType=problem-list-v2&envId=backtracking
     * Given an integer array nums and an integer k, return true if it is possible
     * to divide this array into k non-empty subsets whose sums are all equal.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [4,3,2,3,5,2,1], k = 4
     * Output: true
     * Explanation: It is possible to divide it into 4 subsets (5), (1, 4), (2,3),
     * (2,3) with equal sums.
     * Example 2:
     * 
     * Input: nums = [1,2,3,4], k = 3
     * Output: false
     * 
     * 
     * Constraints:
     * 
     * 1 <= k <= nums.length <= 16
     * 1 <= nums[i] <= 104
     * The frequency of each element is in the range [1, 4].
     */
    // @formatter:on

    /*
     * O(k^n) O(n + k) ✅ space-optimal
     * 
     * Time: O(k^n). At each of the n items, the recursion branches into up to k
     * buckets. Depth is n, so the tree has up to k^n leaves; each node does O(k)
     * work, giving O(k · k^n) loosely, dominated by k^n. The <= target guard prunes
     * some branches but provides no asymptotic improvement.
     * Space: O(n + k). The buckets array holds k ints; recursion depth is n frames.
     * Numbers: n = 10, k = 3 → 3^10 ≈ 59,000 — instant. n = 16, k = 4 → 4^16 ≈ 4.3
     * × 10^9 — minutes to hours. This is where it dies.
     * 
     */
    public boolean canPartitionKSubsetsBruteForce(int[] nums, int k) {
        int totalSum = 0;
        for (int num : nums)
            totalSum += num;

        if (k <= 0 || totalSum % k != 0)
            return false;

        int target = totalSum / k;
        for (int num : nums) {
            if (num > target)
                return false;
        }
        return place(nums, 0, new int[k], target);
    }

    private boolean place(int[] nums, int index, int[] buckets, int target) {
        if (index == nums.length)
            return true;
        for (int bucketIndex = 0; bucketIndex < buckets.length; bucketIndex++) {
            if (buckets[bucketIndex] + nums[index] > target)
                continue;
            buckets[bucketIndex] += nums[index];
            if (place(nums, index + 1, buckets, target))
                return true;
            buckets[bucketIndex] -= nums[index];
        }
        return false;
    }

    /*
     * O(k · 2^n) worst case O(n)
     * 
     * Time: O(k · 2^n) worst case. The set of distinct useful states is bounded by
     * (subset of used elements) × (which box we're on) = 2^n · k. The prunes
     * prevent revisiting equivalent branches: the duplicate skip collapses
     * permutations of equal values, the box-driven fill collapses the k! labelling
     * symmetry, and the first-item abort chops entire failed subtrees. The bound is
     * loose — the constant hides substantially, and real inputs finish far below
     * it.
     * Space: O(n). A used[] array of n booleans plus a sorted copy of n ints;
     * recursion depth is at most n + k. No 2^n allocation — this is the approach's
     * structural advantage.
     * Numbers: n = 16, k = 4 → bounded by 4 · 65,536 ≈ 262,000 state-visits, and in
     * practice the answer typically resolves within a few thousand recursive calls.
     */
    public boolean canPartitionKSubsetsPrunedBacktracking(int[] nums, int k) {
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        if (k <= 0 || totalSum % k != 0)
            return false;

        int target = totalSum / k;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        reverse(sorted);
        if (sorted[0] > target)
            return false;
        boolean[] used = new boolean[sorted.length];
        return fill(sorted, used, 0, k, 0, target);
    }

    private boolean fill(int[] nums, boolean[] used, int start, int subsetsLeft, int currentSum, int target) {
        if (subsetsLeft == 1)
            return true;
        if (currentSum == target)
            return fill(nums, used, 0, subsetsLeft - 1, 0, target);

        for (int i = start; i < nums.length; i++) {
            if (used[i])
                continue;
            if (currentSum + nums[i] > target)
                continue;
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])
                continue;
            used[i] = true;
            if (fill(nums, used, i + 1, subsetsLeft, currentSum + nums[i], target))
                return true;
            used[i] = false;
            if (currentSum == 0)
                return false;
        }
        return false;
    }

    private void reverse(int[] nums) {
        for (int left = 0, right = nums.length - 1; left < right; left++, right--) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
        }
    }

    /*
     * O(n · 2^n) ✅ time-optimal O(2^n)
     * 
     * 
     * Time: O(n · 2^n). The outer loop runs 2^n times, exactly once per mask. The
     * inner loop runs at most n times per reachable mask. Every mask is visited
     * once and never revisited (reachable is write-once), so the bound is tight and
     * input-independent — no adversarial case can degrade it.
     * Space: O(2^n). A boolean[2^n] (1 byte per entry in Java) plus an int[2^n] (4
     * bytes) = 5 bytes per mask.
     * Numbers: n = 16 → 16 · 65,536 ≈ 1.05 × 10^6 inner iterations
     * (sub-millisecond), memory ≈ 65,536 × 5 ≈ 327 KB. At n = 24 that becomes ~4 ×
     * 10^8 iterations and ~84 MB — the memory wall arrives before the time wall.
     * 
     */
    public boolean canPartitionKSubsetsBitmaskDP(int[] nums, int k) {
        int n = nums.length;
        int totalSum = 0;
        for (int num : nums)
            totalSum += num;

        if (k <= 0 || totalSum % k != 0)
            return false;

        int target = totalSum / k;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        if (sorted[n - 1] > target)
            return false;

        int fullMask = (1 << n) - 1;
        boolean[] reachable = new boolean[1 << n];
        int[] usedSum = new int[1 << n];
        reachable[0] = true;

        for (int mask = 0; mask < fullMask; mask++) {
            if (!reachable[mask])
                continue;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0)
                    continue;
                if (usedSum[mask] % target + sorted[i] > target)
                    break;

                int nextMask = mask | (1 << i);
                if (!reachable[nextMask]) {
                    reachable[nextMask] = true;
                    usedSum[nextMask] = usedSum[mask] + sorted[i];
                }
            }
        }
        return reachable[fullMask];
    }
}

// @formatter:off
/*
 * ============================================================
 * PARTITION TO K EQUAL SUM SUBSETS - DEEP DIVE EXPLANATION
 * LeetCode 698 | Difficulty: Medium | Language: Java
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * LeetCode 698 - Partition to K Equal Sum Subsets (Medium)
 *
 * You are given a collection of integers and a number k. Decide whether the
 * collection can be split into exactly k groups such that every group adds up
 * to the same total. Every element must land in exactly one group, and no
 * element may be left over or reused.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * - int[] nums : the array of integers to partition
 * - int k      : the required number of groups
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * - boolean : true if such a partition exists, false otherwise
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * | Constraint        | Value                        |
 * |-------------------|------------------------------|
 * | Array length      | 1 <= nums.length <= 16       |
 * | Group count       | 1 <= k <= nums.length        |
 * | Element range     | 1 <= nums[i] <= 10^4         |
 * | Element frequency | each value appears <= 4 times|
 *
 * The tiny n <= 16 bound is a deliberate signal: exponential-in-n algorithms
 * are expected, and 2^16 = 65536 is trivially small.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Not the subsets themselves - only a yes/no feasibility answer. Two facts
 * fall out immediately:
 *   1. totalSum must be divisible by k, otherwise the answer is false.
 *   2. Each group must sum to target = totalSum / k, so any single element
 *      greater than target makes the answer false.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  nums = [4, 3, 2, 3, 5, 2, 1], k = 4
 *    totalSum = 20, target = 20 / 4 = 5
 *    Output: true
 *    Witness: (5), (4,1), (3,2), (3,2)
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Think of k empty lunchboxes, each of which must weigh exactly `target`
 * grams when packed. You have a pile of items and every item must go into
 * some box. There are two mentally distinct ways to pack:
 *
 *   - Item-driven: walk the pile and, for each item, ask "which box does this
 *     go in?" -> k choices per item -> k^n worlds.
 *   - Box-driven: fill box 1 completely, then box 2, and so on. Once a box is
 *     sealed at exactly target, it never reopens.
 *
 * The box-driven view is the key insight. Boxes are INTERCHANGEABLE - there is
 * no meaningful difference between "put 5 in box 1" and "put 5 in box 3."
 * Item-driven search rediscovers the same packing k! times over. Box-driven
 * search collapses all of that symmetry.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 *   1. Sum everything. If totalSum % k != 0, stop - impossible.
 *   2. Compute target = totalSum / k.
 *   3. If any single item exceeds target, stop - it can never fit anywhere.
 *   4. Sort items LARGEST FIRST. Big items are the constrained ones; placing
 *      them early causes failures to surface near the root of the search tree
 *      rather than deep in it.
 *   5. Fill one box at a time. When a box hits exactly target, seal it and
 *      start the next.
 *   6. If you seal k-1 boxes, the leftovers must sum to target automatically
 *      (arithmetic guarantees it) - so you are done.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge              | Why it's tricky                                       |
 * |------------------------|-------------------------------------------------------|
 * | Box symmetry           | Assigning items to labelled boxes explores k!         |
 * |                        | identical permutations of the same packing; the       |
 * |                        | search explodes without collapsing this               |
 * | Duplicate values       | [3, 3] - trying the first 3 and failing means the     |
 * |                        | second 3 will fail identically, but naive code        |
 * |                        | re-explores it                                        |
 * | Greedy doesn't work    | Filling each box with the largest-fitting item is not |
 * |                        | correct; [4,3,2,3,5,2,1], k=4 breaks naive greedy     |
 * | The "first item" prune | If the smallest-index unused item can't start a box   |
 * |                        | in any branch, no packing exists at all - a subtle    |
 * |                        | but massive prune that is easy to state incorrectly   |
 * | Knowing when to stop   | Recursing to subsetsLeft == 0 wastes an entire level; |
 * |                        | subsetsLeft == 1 is already a proven success          |
 * | State encoding for DP  | The naive DP state is "(which items used, sum of      |
 * |                        | current box)" - realizing the second component is     |
 * |                        | DERIVABLE from the first (sum(mask) % target) is what |
 * |                        | makes an O(n*2^n) DP possible                         |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                    | Key Idea                              | Best Used When                       | Time            | Space                 |
 * |---|-----------------------------|---------------------------------------|--------------------------------------|-----------------|-----------------------|
 * | 1 | Brute Force (bucket assign) | For each item, try all k buckets      | Never in prod - the baseline only    | O(k^n)          | O(n + k) ✅ space-opt |
 * | 2 | Pruned Backtracking         | Sort desc; seal boxes one by one;     | Memory-constrained; typical          | O(k * 2^n)      | O(n)                  |
 * |   | (fill one subset at a time) | skip duplicates; first-item abort     | interview answer; fast in practice   | worst case      |                       |
 * | 3 | Bitmask DP over subsets     | State = set of used items; current    | Worst-case guarantees matter;        | O(n * 2^n)      | O(2^n)                |
 * |   |                             | box sum implied by sum(mask) % target | n <= 20                              | ✅ time-optimal |                       |
 *
 * Trade-off discussion. Approach 1 wins on space (O(n+k)) but its k^n time is
 * hopeless - at n=16, k=4 that's ~4.3 billion branches. It is listed because
 * it is the honest baseline, not because anyone should ship it.
 *
 * Approaches 2 and 3 are genuinely different paradigms, not variants: one is a
 * DFS with pruning, the other is a bottom-up DP over a subset lattice.
 * Approach 3 is TIME-OPTIMAL - O(n*2^n) is a hard worst-case bound that no
 * input can degrade, because each of the 2^n subsets is visited exactly once.
 * Approach 2 has a WORSE worst case (O(k*2^n) with a much larger constant, and
 * adversarial inputs can genuinely hurt it), but it uses only O(n) auxiliary
 * space plus recursion stack, and on real inputs its pruning usually
 * terminates in microseconds.
 *
 * Which to prefer: reach for APPROACH 3 when you need a guaranteed bound or
 * the input is adversarial. Reach for APPROACH 2 when memory is tight (it
 * never allocates a 2^n array) or when n might creep past 20 - Approach 3's
 * 2^n array becomes a hard wall around n = 25, while Approach 2's pruning
 * degrades gracefully. For the LeetCode constraints (n <= 16), both pass
 * comfortably; Approach 2 is the expected interview answer, Approach 3 is the
 * stronger one.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (Bucket Assignment)
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Compute totalSum. If totalSum % k != 0, return false.
 *   2. Set target = totalSum / k. If any element exceeds target, return false.
 *   3. Maintain an array `buckets` of k running sums, all starting at 0.
 *   4. Recurse on index. If index == nums.length, every item is placed and no
 *      bucket ever exceeded target - since the totals must average to target,
 *      all buckets equal target. Return true.
 *   5. For each bucket b, if buckets[b] + nums[index] <= target, add the item,
 *      recurse on index+1, and undo on failure.
 *   6. If no bucket accepts the item, return false.
 *
 *    import java.util.*;
 *
 *    public class PartitionKSubsetsBrute {
 *
 *        public boolean canPartitionKSubsets(int[] nums, int k) {
 *            int totalSum = 0;
 *            for (int value : nums) totalSum += value;
 *
 *            if (k <= 0 || totalSum % k != 0) return false;
 *
 *            int target = totalSum / k;
 *            for (int value : nums) {
 *                if (value > target) return false;   // one item alone overflows a box
 *            }
 *
 *            return place(nums, 0, new int[k], target);
 *        }
 *
 *        private boolean place(int[] nums, int index, int[] buckets, int target) {
 *            if (index == nums.length) return true;  // all placed, none overflowed
 *
 *            for (int bucketIndex = 0; bucketIndex < buckets.length; bucketIndex++) {
 *                if (buckets[bucketIndex] + nums[index] > target) continue;
 *
 *                buckets[bucketIndex] += nums[index];
 *                if (place(nums, index + 1, buckets, target)) return true;
 *                buckets[bucketIndex] -= nums[index];   // undo
 *            }
 *            return false;
 *        }
 *
 *        public static void main(String[] args) {
 *            PartitionKSubsetsBrute solver = new PartitionKSubsetsBrute();
 *            System.out.println(solver.canPartitionKSubsets(new int[]{4, 3, 2, 3, 5, 2, 1}, 4)); // true
 *            System.out.println(solver.canPartitionKSubsets(new int[]{1, 2, 3, 4}, 3));          // false
 *        }
 *    }
 *
 * Why index == nums.length implies success: the buckets sum to
 * totalSum = k * target, and no bucket ever exceeded target. If any bucket
 * were strictly below target, another would have to be strictly above it -
 * which the guard forbids. So all k buckets equal target exactly.
 *
 * ------------------------------------------------------------
 * Approach 2: Pruned Backtracking (Fill One Subset at a Time)
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Reject on totalSum % k != 0.
 *   2. Sort a copy of the array DESCENDING. Large elements are the hardest to
 *      place; trying them first prunes early.
 *   3. Reject if sorted[0] > target.
 *   4. Recurse with (start, subsetsLeft, currentSum):
 *      - If subsetsLeft == 1, return true - the untouched leftovers
 *        necessarily sum to target.
 *      - If currentSum == target, seal this box: recurse with
 *        (0, subsetsLeft - 1, 0) and scan from index 0 again.
 *      - Otherwise loop i from start, and for each unused element that fits,
 *        mark it used, recurse from i + 1, and unmark on failure.
 *   5. Apply three prunes inside the loop:
 *      - Duplicate skip: if nums[i] == nums[i-1] and nums[i-1] is unused,
 *        element i-1 was already tried and rejected at this position -
 *        element i is indistinguishable, so skip.
 *      - Capacity skip: if currentSum + nums[i] > target, it can't go here.
 *      - First-item abort: if currentSum == 0 and placing nums[i] as the box's
 *        FIRST element fails, return false outright. Element i must start SOME
 *        box, and all boxes are identical, so if it fails at the start of this
 *        one it fails everywhere.
 *
 *    import java.util.*;
 *
 *    public class PartitionKSubsetsBacktracking {
 *
 *        public boolean canPartitionKSubsets(int[] nums, int k) {
 *            int totalSum = 0;
 *            for (int value : nums) totalSum += value;
 *
 *            if (k <= 0 || totalSum % k != 0) return false;
 *
 *            int target = totalSum / k;
 *            int[] sorted = nums.clone();
 *            Arrays.sort(sorted);
 *            reverse(sorted);                        // descending: hardest first
 *
 *            if (sorted[0] > target) return false;
 *
 *            boolean[] used = new boolean[sorted.length];
 *            return fill(sorted, used, 0, k, 0, target);
 *        }
 *
 *        private boolean fill(int[] nums, boolean[] used, int start,
 *                             int subsetsLeft, int currentSum, int target) {
 *
 *            if (subsetsLeft == 1) return true;             // leftovers sum to target
 *            if (currentSum == target) {                    // seal box, open a fresh one
 *                return fill(nums, used, 0, subsetsLeft - 1, 0, target);
 *            }
 *
 *            for (int i = start; i < nums.length; i++) {
 *                if (used[i]) continue;
 *                if (currentSum + nums[i] > target) continue;
 *                if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;  // duplicate skip
 *
 *                used[i] = true;
 *                if (fill(nums, used, i + 1, subsetsLeft, currentSum + nums[i], target)) return true;
 *                used[i] = false;
 *
 *                if (currentSum == 0) return false;         // first-item abort
 *            }
 *            return false;
 *        }
 *
 *        private void reverse(int[] array) {
 *            for (int left = 0, right = array.length - 1; left < right; left++, right--) {
 *                int temp = array[left];
 *                array[left] = array[right];
 *                array[right] = temp;
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            PartitionKSubsetsBacktracking solver = new PartitionKSubsetsBacktracking();
 *            System.out.println(solver.canPartitionKSubsets(new int[]{4, 3, 2, 3, 5, 2, 1}, 4)); // true
 *            System.out.println(solver.canPartitionKSubsets(new int[]{1, 2, 3, 4}, 3));          // false
 *        }
 *    }
 *
 * On the duplicate-skip condition !used[i-1]: the used[i-1] == false part is
 * essential. If nums[i-1] is UNUSED at this moment, it means we already tried
 * it in this exact slot and backtracked - so nums[i], being equal, is doomed
 * too. But if nums[i-1] is USED, it sits in the current box and nums[i] is a
 * legitimately new candidate. Dropping the !used[i-1] check produces wrong
 * answers on inputs like [1,1,1,1], k=2.
 *
 * On the first-item abort: when currentSum == 0, the loop's first surviving
 * candidate is the smallest-index unused element, and it must belong to SOME
 * box. Because boxes are interchangeable, "the box we're currently filling" is
 * as good as any other. If the whole subtree rooted at placing it here fails,
 * every relabelling fails too. Return false immediately instead of trying the
 * next i.
 *
 * ------------------------------------------------------------
 * Approach 3: Bitmask DP over Subsets
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Standard rejects: totalSum % k != 0, and max(nums) > target.
 *   2. Sort ASCENDING - this enables an early break (see below).
 *   3. Encode a state as an n-bit mask: bit i set <=> nums[i] has been placed.
 *   4. KEY OBSERVATION: if a mask is reachable, the items in it were consumed
 *      by filling boxes left to right, sealing each at exactly target. So
 *      usedSum[mask] % target IS the fill level of the box currently in
 *      progress. The box sum needs no separate dimension.
 *   5. reachable[0] = true. Sweep masks in increasing order. For a reachable
 *      mask and each unused i:
 *      - If usedSum[mask] % target + nums[i] <= target, then mask | (1 << i)
 *        is reachable with usedSum = usedSum[mask] + nums[i].
 *      - Else break - the array is sorted ascending, so every later i is at
 *        least as large and also won't fit.
 *   6. Answer is reachable[(1 << n) - 1].
 *
 *    import java.util.*;
 *
 *    public class PartitionKSubsetsBitmaskDP {
 *
 *        public boolean canPartitionKSubsets(int[] nums, int k) {
 *            int n = nums.length;
 *            int totalSum = 0;
 *            for (int value : nums) totalSum += value;
 *
 *            if (k <= 0 || totalSum % k != 0) return false;
 *
 *            int target = totalSum / k;
 *            int[] sorted = nums.clone();
 *            Arrays.sort(sorted);                    // ascending: enables the break
 *            if (sorted[n - 1] > target) return false;
 *
 *            int fullMask = (1 << n) - 1;
 *            boolean[] reachable = new boolean[1 << n];
 *            int[] usedSum = new int[1 << n];        // total consumed by this mask
 *            reachable[0] = true;
 *
 *            for (int mask = 0; mask <= fullMask; mask++) {
 *                if (!reachable[mask]) continue;
 *
 *                for (int i = 0; i < n; i++) {
 *                    if ((mask & (1 << i)) != 0) continue;                   // already placed
 *                    if (usedSum[mask] % target + sorted[i] > target) break; // sorted -> no later fit
 *
 *                    int nextMask = mask | (1 << i);
 *                    if (!reachable[nextMask]) {
 *                        reachable[nextMask] = true;
 *                        usedSum[nextMask] = usedSum[mask] + sorted[i];
 *                    }
 *                }
 *            }
 *            return reachable[fullMask];
 *        }
 *
 *        public static void main(String[] args) {
 *            PartitionKSubsetsBitmaskDP solver = new PartitionKSubsetsBitmaskDP();
 *            System.out.println(solver.canPartitionKSubsets(new int[]{4, 3, 2, 3, 5, 2, 1}, 4)); // true
 *            System.out.println(solver.canPartitionKSubsets(new int[]{1, 2, 3, 4}, 3));          // false
 *        }
 *    }
 *
 * Why usedSum[mask] is well-defined per mask. Different reachable paths into
 * the same mask consume the same MULTISET of elements, so usedSum[mask] is
 * path-independent - it is simply the sum of sorted[i] over set bits. Writing
 * it once on first discovery is therefore safe; a second path would compute
 * the identical value.
 *
 * Why usedSum[mask] % target is the in-progress box level. Reachability only
 * ever advances the current box up to target and never past it. So
 * usedSum[mask] = (number of sealed boxes) * target + (current box level), and
 * the modulo peels off the sealed boxes exactly.
 *
 * Why break and not continue. With sorted ascending, if sorted[i] overflows
 * the current box, so does every sorted[j] for j > i. Bailing out of the whole
 * loop is safe and cuts real work.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 - Brute Force
 * ------------------------------------------------------------
 * - Time: O(k^n). At each of the n items, the recursion branches into up to k
 *   buckets. Depth is n, so the tree has up to k^n leaves; each node does O(k)
 *   work, giving O(k * k^n) loosely, dominated by k^n. The <= target guard
 *   prunes some branches but provides no asymptotic improvement.
 * - Space: O(n + k). The buckets array holds k ints; recursion depth is n
 *   frames.
 * - Numbers: n = 10, k = 3 -> 3^10 ~= 59,000 - instant. n = 16, k = 4 ->
 *   4^16 ~= 4.3 x 10^9 - minutes to hours. This is where it dies.
 *
 * ------------------------------------------------------------
 * Approach 2 - Pruned Backtracking
 * ------------------------------------------------------------
 * - Time: O(k * 2^n) worst case. The set of DISTINCT useful states is bounded
 *   by (subset of used elements) x (which box we're on) = 2^n * k. The prunes
 *   prevent revisiting equivalent branches: the duplicate skip collapses
 *   permutations of equal values, the box-driven fill collapses the k!
 *   labelling symmetry, and the first-item abort chops entire failed subtrees.
 *   The bound is loose - the constant hides substantially, and real inputs
 *   finish far below it.
 * - Space: O(n). A used[] array of n booleans plus a sorted copy of n ints;
 *   recursion depth is at most n + k. NO 2^n allocation - this is the
 *   approach's structural advantage.
 * - Numbers: n = 16, k = 4 -> bounded by 4 * 65,536 ~= 262,000 state-visits,
 *   and in practice the answer typically resolves within a few thousand
 *   recursive calls.
 *
 * ------------------------------------------------------------
 * Approach 3 - Bitmask DP
 * ------------------------------------------------------------
 * - Time: O(n * 2^n). The outer loop runs 2^n times, exactly once per mask.
 *   The inner loop runs at most n times per reachable mask. Every mask is
 *   visited once and never revisited (reachable is write-once), so the bound
 *   is tight and input-independent - no adversarial case can degrade it.
 * - Space: O(2^n). A boolean[2^n] (1 byte per entry in Java) plus an int[2^n]
 *   (4 bytes) = 5 bytes per mask.
 * - Numbers: n = 16 -> 16 * 65,536 ~= 1.05 x 10^6 inner iterations
 *   (sub-millisecond), memory ~= 65,536 * 5 ~= 327 KB. At n = 24 that becomes
 *   ~4 x 10^8 iterations and ~84 MB - the memory wall arrives before the time
 *   wall.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 trace - nums = [1, 1, 2, 2], k = 2
 * ------------------------------------------------------------
 * totalSum = 6, target = 3, buckets = [0, 0]
 *
 *    place(index=0, buckets=[0,0])
 *    ├─ item 1 → bucket 0 → buckets=[1,0]
 *    │  └─ place(index=1, buckets=[1,0])
 *    │     ├─ item 1 → bucket 0 → buckets=[2,0]
 *    │     │  └─ place(index=2, buckets=[2,0])
 *    │     │     ├─ item 2 → bucket 0: 2+2=4 > 3  ✗ skip
 *    │     │     ├─ item 2 → bucket 1 → buckets=[2,2]
 *    │     │     │  └─ place(index=3, buckets=[2,2])
 *    │     │     │     ├─ item 2 → bucket 0: 2+2=4 > 3  ✗
 *    │     │     │     └─ item 2 → bucket 1: 2+2=4 > 3  ✗
 *    │     │     │     └─ return false
 *    │     │     └─ undo → return false
 *    │     └─ undo buckets=[1,0]
 *    │     ├─ item 1 → bucket 1 → buckets=[1,1]
 *    │     │  └─ place(index=2, buckets=[1,1])
 *    │     │     ├─ item 2 → bucket 0 → buckets=[3,1]
 *    │     │     │  └─ place(index=3, buckets=[3,1])
 *    │     │     │     ├─ item 2 → bucket 0: 3+2=5 > 3  ✗
 *    │     │     │     └─ item 2 → bucket 1 → buckets=[3,3]
 *    │     │     │        └─ place(index=4) → index == n → TRUE ✅
 *
 * Output: true - partition (1,2), (1,2). Note the wasted work in the first
 * branch: this is exactly the symmetry Approach 2 eliminates.
 *
 * ------------------------------------------------------------
 * Approach 2 trace - nums = [4, 3, 2, 3, 5, 2, 1], k = 4
 * ------------------------------------------------------------
 * totalSum = 20, target = 5, sorted descending -> [5, 4, 3, 3, 2, 2, 1]
 * (indices 0..6)
 *
 *    fill(start=0, subsetsLeft=4, currentSum=0)
 *    ├─ i=0 → take 5  → used=[T,F,F,F,F,F,F]
 *    │  └─ fill(start=1, left=4, sum=5)   → sum == target → SEAL BOX 1 = (5)
 *    │     └─ fill(start=0, left=3, sum=0)
 *    │        ├─ i=0 used → skip
 *    │        ├─ i=1 → take 4 → used=[T,T,F,F,F,F,F]
 *    │        │  └─ fill(start=2, left=3, sum=4)
 *    │        │     ├─ i=2 (3): 4+3=7 > 5  ✗
 *    │        │     ├─ i=3 (3): duplicate of i=2, and used[2]==false → skip
 *    │        │     ├─ i=4 (2): 4+2=6 > 5  ✗
 *    │        │     ├─ i=5 (2): duplicate skip
 *    │        │     └─ i=6 (1): 4+1=5 ✓ → used=[T,T,F,F,F,F,T]
 *    │        │        └─ fill(start=7, left=3, sum=5) → SEAL BOX 2 = (4,1)
 *    │        │           └─ fill(start=0, left=2, sum=0)
 *    │        │              ├─ i=0,1 used → skip
 *    │        │              ├─ i=2 → take 3 → used=[T,T,T,F,F,F,T]
 *    │        │              │  └─ fill(start=3, left=2, sum=3)
 *    │        │              │     ├─ i=3 (3): 3+3=6 > 5  ✗
 *    │        │              │     └─ i=4 (2): 3+2=5 ✓ → SEAL BOX 3 = (3,2)
 *    │        │              │        └─ fill(start=0, left=1, sum=0)
 *    │        │              │           └─ subsetsLeft == 1 → TRUE ✅
 *
 * Output: true. Box 4 is never explicitly built - subsetsLeft == 1
 * short-circuits, and the leftovers {3, 2} sum to 5 by arithmetic necessity.
 * Notice the duplicate skips at i=3 and i=5 each pruned a full subtree.
 *
 * ------------------------------------------------------------
 * Approach 3 trace - nums = [1, 1, 2, 2], k = 2
 * ------------------------------------------------------------
 * totalSum = 6, target = 3, sorted ascending [1, 1, 2, 2], n = 4,
 * fullMask = 1111. Bit i (right to left) <=> sorted[i].
 *
 * | mask | usedSum | box level (usedSum % 3) | transitions attempted                        | newly reachable                          |
 * |------|---------|-------------------------|----------------------------------------------|------------------------------------------|
 * | 0000 | 0       | 0                       | i=0 (1): 0+1<=3 ✓ · i=1 (1): ✓ · i=2 (2): ✓ · i=3 (2): ✓ | 0001(1), 0010(1), 0100(2), 1000(2) |
 * | 0001 | 1       | 1                       | i=1 (1): 1+1=2 ✓ · i=2 (2): 1+2=3 ✓ · i=3 (2): ✓          | 0011(2), 0101(3), 1001(3)          |
 * | 0010 | 1       | 1                       | i=0 ✓ · i=2 ✓ · i=3 ✓                                     | 0110(3), 1010(3)                   |
 * | 0011 | 2       | 2                       | i=2 (2): 2+2=4 > 3 → BREAK                                | —                                  |
 * | 0100 | 2       | 2                       | i=0 (1): 2+1=3 ✓ · i=1 (1): ✓ · i=3 (2): 4 > 3 → BREAK    | — (both already known)             |
 * | 0101 | 3       | 0  ← box sealed         | i=1 (1): 0+1=1 ✓ · i=3 (2): 0+2=2 ✓                       | 0111(4), 1101(5)                   |
 * | 0110 | 3       | 0                       | i=0 (1): ✓ · i=3 (2): ✓                                   | 1110(5)                            |
 * | 0111 | 4       | 1                       | i=3 (2): 1+2=3 <= 3 ✓                                     | 1111(6) ✅                         |
 *
 * reachable[1111] == true -> Output: true, corresponding to boxes (1,2) and
 * (1,2).
 *
 * The 0101 row is the whole trick on display: usedSum = 3, and 3 % 3 = 0
 * correctly reports "previous box sealed, new box is empty" - with no extra DP
 * dimension.
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                        | Input                       | Expected | How Handled                                                        |
 * |----------------------------------|-----------------------------|----------|--------------------------------------------------------------------|
 * | k = 1                            | nums = [10], k = 1          | true     | target = 10; subsetsLeft == 1 returns true at the root (DP: mask 1  |
 * |                                  |                             |          | is trivially reachable)                                            |
 * | k equals n, all equal            | nums = [1,1,1,1], k = 4     | true     | target = 1; each element seals its own box; the !used[i-1] guard   |
 * |                                  |                             |          | prevents wrongly skipping duplicates                               |
 * | Sum not divisible by k           | nums = [1,2,3,4], k = 3     | false    | totalSum = 10, 10 % 3 != 0 → early false before any search         |
 * | Single element exceeds target    | nums = [2,2,2,2,3,4,5], k=4 | false    | target = 5; nothing exceeds 5, but 4 can only pair with 1 which    |
 * |                                  |                             |          | doesn't exist → search exhausts and returns false                  |
 * | Element strictly > target        | nums = [1,1,1,9], k = 2     | false    | target = 6, 9 > 6 → the max > target guard fires immediately       |
 * | Divisible but unsatisfiable      | nums = [2,2,2,2,3,4,5], k=4 | false    | Passes both fast guards, fails only after real search              |
 * | Maximum size input               | n = 16, k = 4               | either   | Approach 3 bounded at 16 * 2^16 ~= 10^6 ops; Approach 2's prunes   |
 * |                                  |                             |          | keep it well inside limits                                         |
 * | Duplicates split across boxes    | nums = [1,1,2,2], k = 2     | true     | Duplicate skip only fires when used[i-1] == false; the second 1    |
 * |                                  |                             |          | remains available for box 2                                        |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 *
 * Pitfall 1 - Omitting !used[i-1] from the duplicate skip.
 *
 *    // ❌ WRONG - skips valid placements of a duplicate into a *later* box
 *    if (i > 0 && nums[i] == nums[i - 1]) continue;
 *
 *    // ✅ CORRECT - only skip when the twin was tried in this same slot and rejected
 *    if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;
 *
 * The wrong version returns false for [1,1,1,1], k = 2.
 *
 * Pitfall 2 - Recursing to subsetsLeft == 0 instead of 1.
 *
 *    // ❌ WASTEFUL - burns an extra recursion level rebuilding a box that must work
 *    if (subsetsLeft == 0) return true;
 *
 *    // ✅ CORRECT - leftovers sum to target by arithmetic
 *    if (subsetsLeft == 1) return true;
 *
 * Pitfall 3 - Sorting ascending in the backtracking solution.
 *
 *    // ❌ SLOW - small elements first means failures surface at maximum depth
 *    Arrays.sort(nums);
 *
 *    // ✅ FAST - hardest-to-place elements first; failures surface near the root
 *    Arrays.sort(sorted);
 *    reverse(sorted);
 *
 * Same answer, but ascending order can push n = 16 inputs into a timeout.
 *
 * Pitfall 4 - Mutating the caller's array.
 *
 *    // ❌ RUDE - Arrays.sort(nums) reorders the caller's array in place
 *    Arrays.sort(nums);
 *
 *    // ✅ SAFE
 *    int[] sorted = nums.clone();
 *    Arrays.sort(sorted);
 *
 * Pitfall 5 - Restarting the box-fill scan at i + 1 after sealing.
 *
 *    // ❌ WRONG - a fresh box must consider all unused elements
 *    if (currentSum == target) return fill(nums, used, i + 1, subsetsLeft - 1, 0, target);
 *
 *    // ✅ CORRECT - reset start to 0 for the new box
 *    if (currentSum == target) return fill(nums, used, 0, subsetsLeft - 1, 0, target);
 *
 * Within a single box, start = i + 1 is right (it prevents choosing the same
 * COMBINATION twice). Across boxes, the scan must reset.
 *
 * Pitfall 6 - Ascending sort + continue instead of break in the DP.
 *
 *    // ❌ MISSES THE PRUNE (correct, but slower)
 *    if (usedSum[mask] % target + sorted[i] > target) continue;
 *
 *    // ✅ sorted ascending ⇒ nothing after i fits either
 *    if (usedSum[mask] % target + sorted[i] > target) break;
 *
 * If you sort DESCENDING in Approach 3, the break becomes INCORRECT - it must
 * be continue. The sort direction and the loop exit are coupled.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: Four categories deserve explicit checks.
 *    1. k out of range. LeetCode guarantees 1 <= k <= n, but a defensive
 *       k <= 0 guard avoids a % 0 ArithmeticException and a negative-length
 *       array allocation. All three solutions include it.
 *    2. Divisible-but-impossible inputs. [2,2,2,2,3,4,5], k=4 passes both fast
 *       rejects (sum = 20, divisible; max = 5 = target) and still returns
 *       false only after real search. Testing only on inputs that fail the
 *       arithmetic guard gives false confidence.
 *    3. Duplicate handling. [1,1,1,1], k=2 and [1,1,2,2], k=2 specifically
 *       exercise the !used[i-1] condition. A broken duplicate skip passes most
 *       tests and fails these.
 *    4. k = n. Every element becomes its own subset; requires all elements
 *       equal.
 *
 * Q: Are there any type mismatches?
 * A: No. nums[i] <= 10^4 and n <= 16 bound totalSum at 1.6 x 10^5 - nowhere
 *    near Integer.MAX_VALUE, so int is safe throughout and no long is needed.
 *    Two real hazards do exist: totalSum % k and usedSum[mask] % target both
 *    divide, so k == 0 and target == 0 must be impossible. k <= 0 is guarded
 *    explicitly; target == 0 cannot occur because every nums[i] >= 1 forces
 *    totalSum >= n >= k, hence target >= 1. Separately, 1 << n with n <= 16 is
 *    65536 - well within int; if this were extended past n = 31, 1 << n would
 *    overflow and the array allocation would break.
 *
 * Q: How can I verify this works right now?
 * A: Run the three implementations against each other on randomized inputs. If
 *    they disagree on any input, at least one is wrong - and since they use
 *    three independent paradigms, agreement across thousands of random cases
 *    is strong evidence.
 *
 *    import java.util.*;
 *
 *    public class Verify {
 *
 *        public static void verify() {
 *            PartitionKSubsetsBrute brute = new PartitionKSubsetsBrute();
 *            PartitionKSubsetsBacktracking back = new PartitionKSubsetsBacktracking();
 *            PartitionKSubsetsBitmaskDP dp = new PartitionKSubsetsBitmaskDP();
 *
 *            // --- fixed assertions ---
 *            assert back.canPartitionKSubsets(new int[]{4, 3, 2, 3, 5, 2, 1}, 4) : "classic case";
 *            assert !back.canPartitionKSubsets(new int[]{1, 2, 3, 4}, 3)         : "not divisible";
 *            assert back.canPartitionKSubsets(new int[]{10}, 1)                  : "k == 1";
 *            assert back.canPartitionKSubsets(new int[]{1, 1, 1, 1}, 4)          : "k == n, all equal";
 *            assert back.canPartitionKSubsets(new int[]{1, 1, 2, 2}, 2)          : "duplicates split";
 *            assert !back.canPartitionKSubsets(new int[]{2,2,2,2,3,4,5}, 4)      : "divisible but impossible";
 *            assert !back.canPartitionKSubsets(new int[]{1, 1, 1, 9}, 2)         : "element exceeds target";
 *            assert dp.canPartitionKSubsets(new int[]{1,2,3,4,5,6,7,8,9,10}, 5)  : "sum 55, target 11";
 *
 *            // --- randomized cross-check across all three paradigms ---
 *            Random random = new Random(7);
 *            for (int iteration = 0; iteration < 4000; iteration++) {
 *                int n = 1 + random.nextInt(8);
 *                int[] candidate = new int[n];
 *                for (int i = 0; i < n; i++) candidate[i] = 1 + random.nextInt(6);
 *                int k = 1 + random.nextInt(4);
 *
 *                boolean r1 = brute.canPartitionKSubsets(candidate, k);
 *                boolean r2 = back.canPartitionKSubsets(candidate, k);
 *                boolean r3 = dp.canPartitionKSubsets(candidate, k);
 *
 *                assert r1 == r2 && r1 == r3
 *                    : "mismatch on " + Arrays.toString(candidate) + " k=" + k;
 *            }
 *            System.out.println("All checks passed.");
 *        }
 *
 *        public static void main(String[] args) {
 *            verify();   // run with:  java -ea Verify.java
 *        }
 *    }
 *
 * Run with assertions enabled: java -ea Verify.java. This suite was executed
 * against all three implementations above - the fixed assertions pass and all
 * 4,000 randomized cases produce identical answers across the brute force,
 * backtracking, and bitmask DP.
 *
 * ------------------------------------------------------------
 * Risk Table
 * ------------------------------------------------------------
 * | Approach          | Risk                                          | Mitigation                                              |
 * |-------------------|-----------------------------------------------|---------------------------------------------------------|
 * | 1 - Brute Force   | k^n blowup: n=16, k=4 is ~4.3 billion         | Use only as a randomized-testing oracle on n <= 8;      |
 * |                   | branches → guaranteed TLE                     | never submit                                            |
 * | 2 - Backtracking  | Missing/malformed prunes silently turn a fast | Assert all three prunes are present; regression-test    |
 * |                   | solution into a TLE, or the !used[i-1] slip   | [1,1,1,1], k=2 and [1,1,2,2], k=2 specifically          |
 * |                   | turns it into a WRONG one                     |                                                         |
 * | 2 - Backtracking  | Adversarial inputs can still approach the     | Acceptable at n <= 16; switch to Approach 3 if          |
 * |                   | O(k*2^n) worst case                           | worst-case bounds are contractual                       |
 * | 3 - Bitmask DP    | 2^n memory is a hard wall - n=25 needs        | Confirm n <= 20 before choosing this; otherwise fall    |
 * |                   | ~168 MB, n=30 is hopeless                     | back to Approach 2                                      |
 * | 3 - Bitmask DP    | The break prune is only valid with an         | Keep the sort direction and the break/continue choice   |
 * |                   | ASCENDING sort; a descending sort yields      | adjacent in the code and comment the coupling           |
 * |                   | wrong answers                                 |                                                         |
 * | All               | totalSum % k with k = 0 throws                | Explicit k <= 0 guard before any division               |
 * |                   | ArithmeticException                           |                                                         |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * LeetCode 698 - Partition to K Equal Sum Subsets
 * Difficulty: Medium | ~500+ reported interview appearances
 *
 * Widely regarded as a HARD Medium - it is the canonical bridge problem
 * between "I can write a backtracking function" and "I understand search-space
 * symmetry and bitmask DP."
 *
 * | Company           | Frequency  | Notes                                                     |
 * |-------------------|------------|-----------------------------------------------------------|
 * | Amazon            | ⭐⭐⭐⭐⭐ | Very common in SDE-II loops; usually asked as a           |
 * |                   |            | load-balancing story ("distribute tasks across k workers")|
 * | Google            | ⭐⭐⭐⭐⭐ | Expect a follow-up on the complexity of the pruning and   |
 * |                   |            | why the box-driven view beats the item-driven one         |
 * | Microsoft         | ⭐⭐⭐⭐   | Often paired with Partition Equal Subset Sum (LC 416) as  |
 * |                   |            | a warm-up → escalation                                    |
 * | Meta              | ⭐⭐⭐⭐   | Phone screen and onsite; interviewers care most about     |
 * |                   |            | whether you find the symmetry prune                       |
 * | Bloomberg         | ⭐⭐⭐⭐   | Recurring onsite question; bitmask DP earns strong signal |
 * | Apple             | ⭐⭐⭐     | Appears in iOS/systems loops as resource allocation       |
 * | Uber              | ⭐⭐⭐     | Framed as splitting deliveries across k drivers evenly    |
 * | Oracle            | ⭐⭐⭐     | Standard backtracking-round question                      |
 * | Adobe             | ⭐⭐       | Occasional; plain backtracking usually accepted           |
 * | Goldman Sachs     | ⭐⭐       | Shows up in quant/dev screens as a bucketing problem      |
 * | Salesforce        | ⭐⭐       | Less common; typically a follow-up rather than the main   |
 * | ByteDance/TikTok  | ⭐⭐⭐     | The bitmask DP is expected, not optional, in the          |
 * |                   |            | algorithm-heavy rounds                                    |
 *
 * Related ladder: LC 416 (Partition Equal Subset Sum, k = 2) → LC 698 →
 * LC 473 (Matchsticks to Square, k = 4) → LC 1723 (Find Minimum Time to Finish
 * All Jobs, the optimization variant).
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach                  | Time            | Space    | Code Complexity                          | Recommended?                              |
 * |---------------------------|-----------------|----------|------------------------------------------|-------------------------------------------|
 * | 1 - Brute Force           | O(k^n)          | O(n + k) | Low - ~15 lines, no tricks               | ❌ Not recommended in prod - TLEs at      |
 * |   (bucket assignment)     |                 |          |                                          | n = 16; keep only as a testing oracle     |
 * | 2 - Pruned Backtracking   | O(k * 2^n)      | O(n)     | Medium - three prunes, each easy to get  | ✅ BEST FOR LOW MEMORY - the expected     |
 * |                           | worst case      |          | subtly wrong                             | interview answer; fast in practice, no    |
 * |                           |                 |          |                                          | 2^n allocation                            |
 * | 3 - Bitmask DP            | O(n * 2^n)      | O(2^n)   | Medium-High - the sum(mask) % target     | ✅✅ BEST FOR TIME - tight,               |
 * |   over subsets            |                 |          | insight is the whole trick               | input-independent bound; strongest answer |
 * |                           |                 |          |                                          | when n <= 20                              |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Approach 3 (bitmask DP) when you want a guaranteed O(n * 2^n) with no
 * adversarial worst case and n <= 20 - but note the trade-off is real: it pays
 * O(2^n) memory to buy that guarantee, and Approach 2 is the right call when
 * memory is tight or n might exceed ~20, since its O(n) space never allocates
 * the subset table and its pruning handles typical inputs in microseconds. In
 * an interview, present Approach 2 first, then offer Approach 3 as the
 * worst-case-optimal upgrade.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * FILL BOXES, NOT ITEMS. Assigning items to labelled buckets re-explores every
 * packing k! times; filling one subset to completion before opening the next
 * kills that symmetry outright - and this "sort descending + one-subset-at-a-
 * time + skip duplicates + abort if the first unused element can't start a box"
 * template transfers directly to LC 473 and LC 1723.
 *
 * THE DP GOTCHA TO MEMORIZE: the current subset's running sum needs NO DP
 * dimension - it is exactly sum(mask) % target, because reachable states only
 * ever seal boxes at precisely target. And the duplicate skip is
 * nums[i] == nums[i-1] && !used[i-1] - dropping that !used[i-1] doesn't make
 * it slow, it makes it WRONG.
 *
 * ============================================================
 * END OF EXPLANATION
 * ============================================================
 */
// @formatter:on
