package Array;

import java.util.Arrays;

public class JumpGameII {
    public static void main(String[] args) {
        JumpGameII jumpGame = new JumpGameII();
        System.out.println("JumpGameII : " + jumpGame.jumpBruteForceRecursion(new int[] { 2, 3, 1, 1, 4 }));
        System.out.println("JumpGameII : " + jumpGame.jumpBruteForceRecursion(new int[] { 2, 3, 0, 1, 4 }));
        System.out.println("JumpGameII : "
                + jumpGame.jumpBruteForceRecursion(new int[] { 7, 0, 9, 6, 9, 6, 1, 7, 9, 0, 1, 2, 9, 0, 3 }));
        System.out.println("---------------");
        System.out.println("JumpGameII : " + jumpGame.jumpDynamicProgramming(new int[] { 2, 3, 1, 1, 4 }));
        System.out.println("JumpGameII : " + jumpGame.jumpDynamicProgramming(new int[] { 2, 3, 0, 1, 4 }));
        System.out.println("JumpGameII : "
                + jumpGame.jumpDynamicProgramming(new int[] { 7, 0, 9, 6, 9, 6, 1, 7, 9, 0, 1, 2, 9, 0, 3 }));
        System.out.println("---------------");
        System.out.println("JumpGameII : " + jumpGame.jumpGreedyBFSLayers(new int[] { 2, 3, 1, 1, 4 }));
        System.out.println("JumpGameII : " + jumpGame.jumpGreedyBFSLayers(new int[] { 2, 3, 0, 1, 4 }));
        System.out.println("JumpGameII : "
                + jumpGame.jumpGreedyBFSLayers(new int[] { 7, 0, 9, 6, 9, 6, 1, 7, 9, 0, 1, 2, 9, 0, 3 }));
    }

    // @formatter:off
    /**
     * 
     * https://leetcode.com/problems/jump-game-ii/description/
     * 
     * You are given a 0-indexed array of integers nums of length n. You are
     * initially positioned at index 0.
     * 
     * Each element nums[i] represents the maximum length of a forward jump from
     * index i. In other words, if you are at index i, you can jump to any index (i
     * + j) where:
     * 
     * 0 <= j <= nums[i] and
     * i + j < n
     * Return the minimum number of jumps to reach index n - 1. The test cases are
     * generated such that you can reach index n - 1.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [2,3,1,1,4]
     * Output: 2
     * Explanation: The minimum number of jumps to reach the last index is 2. Jump 1
     * step from index 0 to 1, then 3 steps to the last index.
     * Example 2:
     * 
     * Input: nums = [2,3,0,1,4]
     * Output: 2
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 104
     * 0 <= nums[i] <= 1000
     * It's guaranteed that you can reach nums[n - 1].
     * 
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach              | Time   | Space | Code Complexity   | Recommended? |
     * |-----------------------|--------|-------|-------------------|--------------|
     * | Brute-Force Recursion | O(2^n) | O(n)  | Low (impractical) | [X] Understanding only — too slow |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int jumpBruteForceRecursion(int[] nums) {
        return minJumps(nums, 0);
    }

    public int minJumps(int[] nums, int position) {
        if (position >= nums.length - 1)
            return 0;
        int bestFromHere = Integer.MAX_VALUE;
        for (int step = 1; step <= nums[position]; step++) {
            int nextPosition = position + step;
            if (nextPosition < nums.length) {
                int jumpAfter = minJumps(nums, nextPosition);
                if (jumpAfter != Integer.MAX_VALUE)
                    bestFromHere = Math.min(bestFromHere, jumpAfter + 1);
            }
        }
        return bestFromHere;
    }

    // @formatter:off
    /**
     * 
     * | Approach              | Time   | Space | Code Complexity   | Recommended? |
     * |-----------------------|--------|-------|-------------------|--------------|
     * | Dynamic Programming   | O(n^2) | O(n)  | Moderate          | [check] Acceptable if DP requested; risky at max n |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int jumpDynamicProgramming(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            for (int step = 1; step <= nums[i] && i + step < n; step++) {
                dp[i + step] = Math.min(dp[i + step], dp[i] + 1);
            }
        }
        return dp[n - 1];
    }

    // @formatter:off
    /**
     * 
     * | Approach              | Time   | Space | Code Complexity   | Recommended? |
     * |-----------------------|--------|-------|-------------------|--------------|
     * | Greedy / BFS Layers   | O(n)   | O(1)  | Low               | [check][check] Best on both time and space |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int jumpGreedyBFSLayers(int[] nums) {
        int n = nums.length;
        int jumps = 0;
        int farthest = 0;
        int currentEnd = 0;

        for (int i = 0; i < n - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;
            }
        }
        return jumps;
    }
}
// @formatter:off
/*
 * ============================================================
 * JUMP GAME II — DEEP DIVE EXPLANATION
 * (LeetCode #45 — Medium)
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You start standing on the first index of an array. Each cell tells you the
 * maximum number of steps you're allowed to jump forward from that cell. Your
 * goal is to reach the last index using as few jumps as possible. The problem
 * guarantees that the last index is always reachable, so you never have to
 * worry about getting stuck.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums — an array of non-negative integers, where nums[i] is the maximum
 * forward jump length from index i.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * int — the minimum number of jumps required to go from index 0 to index
 * nums.length - 1.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * 1 <= nums.length <= 10^4
 * 0 <= nums[i] <= 1000
 * It is guaranteed that you can reach nums[n - 1].
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * The minimum count of jumps. Not the path, not whether it's reachable (that's
 * the easier "Jump Game I") — just the smallest number of hops. If you're
 * already on the last index (single-element array), the answer is 0.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    nums = [2, 3, 1, 1, 4]
 *    Index:  0  1  2  3  4
 *
 *    Jump 1: index 0 -> index 1 (jump length 1, allowed since nums[0]=2)
 *    Jump 2: index 1 -> index 4 (jump length 3, allowed since nums[1]=3)
 *
 *    Answer: 2
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Think of the array as a series of launch pads. From your current pad you can
 * reach a range of pads ahead. With one jump you can land anywhere in that
 * range. The question is: what's the fewest number of "launches" to reach the
 * end? The key realization: you don't need to decide exactly which pad to land
 * on. You only need to know how far the whole current reachable range can throw
 * you on the next jump. So you treat all positions reachable with k jumps as
 * one "layer," and expand to the next layer greedily.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. With 0 jumps, you can only stand on index 0.
 * 2. From index 0, look at everything you can reach in 1 jump — that's the range
 *    [1 .. nums[0]]. That whole span is "layer 1."
 * 3. To find layer 2, scan every index in layer 1 and record the farthest index
 *    any of them can reach. That farthest point is the right boundary of layer 2.
 * 4. Keep expanding layer by layer. Each time you cross into a new layer, that's
 *    one more jump.
 * 5. The layer number in which the last index first appears is the answer.
 *
 * This is exactly breadth-first search on ranges — but because the ranges are
 * contiguous, you can do it with a simple linear scan instead of a real queue.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                                   | Why it's tricky |
 * |---------------------------------------------|-----------------|
 * | It's a minimization, not reachability       | Jump Game I only asks "can you reach the end?" Here you must optimize the count, which tempts people toward slow DP. |
 * | The obvious greedy ("jump as far as possible") is WRONG | Jumping to the physically farthest cell isn't always best — you want the cell that sets up the farthest next reach. |
 * | Off-by-one on the loop bound                | You must iterate only to n - 2. Looping to n - 1 can trigger a phantom extra jump when you're already on the goal. |
 * | "farthest reachable now" vs "current boundary" | Two separate variables are needed; collapsing them into one breaks the layer logic. |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach | Key Idea | Best Used When | Time | Space |
 * |---|----------|----------|----------------|------|-------|
 * | 1 | Brute-Force Recursion | From each index, recursively try every jump length and take the min | Teaching only; n tiny | O(2^n) | O(n) stack |
 * | 2 | Dynamic Programming (bottom-up) | dp[i] = min jumps to reach i; relax forward from every reachable cell | Want an intuitive polynomial solution | O(n^2) | O(n) |
 * | 3 | Greedy / BFS Layers (OPTIMAL) | Track current jump's boundary + farthest reachable; increment at each boundary | Always — the production answer | O(n) [check] | O(1) [check] |
 *
 * The three approaches form a clean ladder. Brute force explores an exponential
 * tree of choices and is only useful for building intuition. DP collapses that
 * tree into a table, trading exponential time for O(n^2) — correct and easy to
 * reason about, but too slow at n = 10^4 in the worst case (a cell like
 * nums[i] = 1000 makes each inner relaxation loop long). The greedy layered scan
 * DOMINATES both other approaches on both axes — it's O(n) time AND O(1) space —
 * so unlike many problems, there is no time-vs-space tradeoff here: approach 3 is
 * the unambiguous winner. Reach for DP only if an interviewer explicitly wants
 * you to demonstrate the DP formulation first.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute-Force Recursion
 * ------------------------------------------------------------
 * Steps:
 * 1. Define minJumps(pos) = fewest jumps from pos to the last index.
 * 2. Base case: if pos >= n - 1, return 0.
 * 3. Try every jump length step from 1 to nums[pos].
 * 4. Recurse on pos + step, take the minimum, add 1 for the jump just made.
 * 5. If no forward move helps, return "infinity".
 *
 *    public class JumpGameIIBrute {
 *
 *        public static int jump(int[] nums) {
 *            return minJumps(nums, 0);
 *        }
 *
 *        private static int minJumps(int[] nums, int position) {
 *            if (position >= nums.length - 1) {
 *                return 0;
 *            }
 *            int bestFromHere = Integer.MAX_VALUE;
 *            for (int step = 1; step <= nums[position]; step++) {
 *                int nextPosition = position + step;
 *                if (nextPosition < nums.length) {
 *                    int jumpsAfter = minJumps(nums, nextPosition);
 *                    if (jumpsAfter != Integer.MAX_VALUE) {
 *                        bestFromHere = Math.min(bestFromHere, jumpsAfter + 1);
 *                    }
 *                }
 *            }
 *            return bestFromHere;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {2, 3, 1, 1, 4};
 *            System.out.println("Minimum jumps: " + jump(nums)); // Expected: 2
 *        }
 *    }
 *
 * The guard if (jumpsAfter != Integer.MAX_VALUE) prevents overflow — adding 1 to
 * Integer.MAX_VALUE would wrap to a negative number and corrupt the minimum.
 *
 * ------------------------------------------------------------
 * Approach 2: Dynamic Programming (bottom-up)
 * ------------------------------------------------------------
 * Steps:
 * 1. Create dp[] of length n, filled with a large sentinel; set dp[0] = 0.
 * 2. Walk i from 0 to n - 1. If dp[i] is still the sentinel, skip it.
 * 3. From each reachable i, relax every cell in its jump range:
 *    dp[i + step] = min(dp[i + step], dp[i] + 1).
 * 4. dp[n - 1] holds the answer.
 *
 *    import java.util.Arrays;
 *
 *    public class JumpGameIIDP {
 *
 *        public static int jump(int[] nums) {
 *            int n = nums.length;
 *            int[] dp = new int[n];
 *            Arrays.fill(dp, Integer.MAX_VALUE);
 *            dp[0] = 0;
 *            for (int i = 0; i < n; i++) {
 *                if (dp[i] == Integer.MAX_VALUE) {
 *                    continue;
 *                }
 *                for (int step = 1; step <= nums[i] && i + step < n; step++) {
 *                    dp[i + step] = Math.min(dp[i + step], dp[i] + 1);
 *                }
 *            }
 *            return dp[n - 1];
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {2, 3, 0, 1, 4};
 *            System.out.println("Minimum jumps: " + jump(nums)); // Expected: 2
 *        }
 *    }
 *
 * Because you always relax forward and each cell is finalized only from earlier
 * cells, a single left-to-right pass suffices.
 *
 * ------------------------------------------------------------
 * Approach 3: Greedy / BFS Layers (OPTIMAL) [check]
 * ------------------------------------------------------------
 * Steps:
 * 1. Keep jumps (count so far), currentEnd (last index reachable with 'jumps'
 *    jumps — the current layer boundary), farthest (farthest reachable with one
 *    more jump).
 * 2. Iterate i from 0 to n - 2 (never from the final index).
 * 3. At each i, update farthest = max(farthest, i + nums[i]).
 * 4. When i reaches currentEnd, increment jumps and set currentEnd = farthest.
 * 5. After the loop, jumps is the answer.
 *
 *    public class JumpGameIIGreedy {
 *
 *        public static int jump(int[] nums) {
 *            int jumps = 0;
 *            int currentEnd = 0;
 *            int farthest = 0;
 *            for (int i = 0; i < nums.length - 1; i++) {
 *                farthest = Math.max(farthest, i + nums[i]);
 *                if (i == currentEnd) {
 *                    jumps++;
 *                    currentEnd = farthest;
 *                }
 *            }
 *            return jumps;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {2, 3, 1, 1, 4};
 *            System.out.println("Minimum jumps: " + jump(nums)); // Expected: 2
 *        }
 *    }
 *
 * currentEnd is NOT the physically farthest cell you jumped to — it's the
 * boundary of everything the previous layer could reach. You "commit" to a jump
 * only when forced to (when i == currentEnd), which makes the greedy optimal.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Approach 1 — Brute-Force Recursion
 *   Time:  O(2^n). Each position branches into up to nums[i] children with no
 *          memoization; overlapping subproblems are recomputed. For n = 20 you
 *          may see hundreds of thousands to millions of calls.
 *   Space: O(n) — recursion depth at most n along a path.
 *
 * Approach 2 — Dynamic Programming
 *   Time:  O(n^2). Outer loop n times; inner relaxation up to nums[i] ~ n-1.
 *          Worst case ~ n^2/2. For n = 10^4 that's ~5 x 10^7 operations.
 *   Space: O(n) for the dp array.
 *
 * Approach 3 — Greedy / BFS Layers [check]
 *   Time:  O(n). Single pass; each index does O(1) work. For n = 10^4 that's
 *          ~10^4 operations — thousands of times fewer than the DP worst case.
 *   Space: O(1). Three scalar variables regardless of input size.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 — Brute Force on nums = [2, 3, 1, 1, 4]
 * ------------------------------------------------------------
 * minJumps(0)                       nums[0]=2 -> try step 1,2
 * |- minJumps(1)                    nums[1]=3 -> try step 1,2,3
 * |  |- minJumps(2)                 nums[2]=1 -> try step 1
 * |  |  |- minJumps(3)              nums[3]=1 -> try step 1
 * |  |  |  |- minJumps(4) = 0       base case
 * |  |  |     => minJumps(3) = 1
 * |  |  |     => minJumps(2) = 2
 * |  |- minJumps(3) = 1             (recomputed — no memo)
 * |  |- minJumps(4) = 0             base case -> contributes 1
 * |     => minJumps(1) = min(2+1, 1+1, 0+1) = 1
 * |- minJumps(2) = 2                (recomputed again)
 *    => minJumps(0) = min(1+1, 2+1) = 2
 * Final output: 2 — the recomputation of minJumps(2)/minJumps(3) is the blowup.
 *
 * ------------------------------------------------------------
 * Approach 2 — DP on nums = [2, 3, 0, 1, 4]
 * ------------------------------------------------------------
 * | Step | Action                                  | dp [i0,i1,i2,i3,i4] |
 * |------|-----------------------------------------|---------------------|
 * | init | dp[0]=0, rest inf                       | [0, inf, inf, inf, inf] |
 * | i=0  | relax 1,2 -> dp[1],dp[2] = 1            | [0, 1, 1, inf, inf] |
 * | i=1  | relax 1,2,3 -> dp[2]=1, dp[3]=2, dp[4]=2| [0, 1, 1, 2, 2]     |
 * | i=2  | nums=0, no steps                        | [0, 1, 1, 2, 2]     |
 * | i=3  | relax 1 -> dp[4]=min(2,3)=2             | [0, 1, 1, 2, 2]     |
 * | i=4  | last cell                               | [0, 1, 1, 2, 2]     |
 * Final output: dp[4] = 2.
 *
 * ------------------------------------------------------------
 * Approach 3 — Greedy on nums = [2, 3, 1, 1, 4]
 * ------------------------------------------------------------
 * | i | nums[i] | farthest=max(farthest,i+nums[i]) | i==currentEnd? | Action      | jumps | currentEnd |
 * |---|---------|----------------------------------|----------------|-------------|-------|------------|
 * | - | -       | 0                                | -              | init        | 0     | 0          |
 * | 0 | 2       | max(0, 0+2)=2                    | yes (0==0)     | jump, end->2| 1     | 2          |
 * | 1 | 3       | max(2, 1+3)=4                    | no (1!=2)      | -           | 1     | 2          |
 * | 2 | 1       | max(4, 2+1)=4                    | yes (2==2)     | jump, end->4| 2     | 4          |
 * | 3 | 1       | max(4, 3+1)=4                    | no (3!=4)      | -           | 2     | 4          |
 * Loop stops at i = 3 (n - 1 = 4). Final output: jumps = 2.
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                         | Input           | Expected | How Handled |
 * |-----------------------------------|-----------------|----------|-------------|
 * | Single element (already at goal)  | [0]             | 0        | Loop 0..n-2 = 0..-1 never runs -> returns 0. |
 * | Two elements                      | [1, 2]          | 1        | i=0: farthest=1, i==currentEnd -> jumps=1. |
 * | Big first jump clears everything  | [5, 1, 1, 1, 1] | 1        | i=0: farthest=5 >= last index -> one jump. |
 * | All ones (forced single steps)    | [1, 1, 1, 1]    | 3        | Each layer advances one cell -> n-1 jumps. |
 * | Zero mid-array but reachable over | [2, 3, 0, 1, 4] | 2        | Range from index 1 leaps over the 0 cell. |
 * | Max reach values                  | [1000, ...]     | depends  | i + nums[i] may exceed array; max + loop bound stay safe. |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 — Looping to the last index.
 *   WRONG:   for (int i = 0; i < nums.length; i++)     // phantom extra jump
 *   CORRECT: for (int i = 0; i < nums.length - 1; i++) // stop before the goal
 *
 * Pitfall 2 — Greedily jumping to the farthest cell instead of the farthest reach.
 *   WRONG:   pick the largest index in range and jump there (ignores next reach)
 *   CORRECT: expand 'farthest' across the whole layer, commit only at boundary
 *
 * Pitfall 3 — Overflow in brute/DP versions.
 *   WRONG:   Math.min(best, minJumps(next) + 1) when next is Integer.MAX_VALUE
 *   CORRECT: guard with if (sub != Integer.MAX_VALUE) before adding 1
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The greedy version is robust as long as the loop bound is n - 1. Double-check
 *    the single-element array — if you initialize jumps = 1 or loop to n, [0] would
 *    wrongly return 1. With the correct bound it returns 0. Arrays with 0 values
 *    mid-path are fine because reachability is guaranteed.
 *
 * Q: Are there any type mismatches?
 * A: All values fit in int: n <= 10^4 and nums[i] <= 1000, so i + nums[i] maxes
 *    around 11000, nowhere near Integer.MAX_VALUE. Only the brute/DP sentinel
 *    arithmetic risks overflow, prevented by the MAX_VALUE guard. Return type is int.
 *
 * Q: How can I verify this works right now?
 *    public class JumpGameIIVerify {
 *
 *        public static int jump(int[] nums) {
 *            int jumps = 0, currentEnd = 0, farthest = 0;
 *            for (int i = 0; i < nums.length - 1; i++) {
 *                farthest = Math.max(farthest, i + nums[i]);
 *                if (i == currentEnd) {
 *                    jumps++;
 *                    currentEnd = farthest;
 *                }
 *            }
 *            return jumps;
 *        }
 *
 *        public static void verify() {
 *            assert jump(new int[]{2, 3, 1, 1, 4}) == 2 : "basic case";
 *            assert jump(new int[]{2, 3, 0, 1, 4}) == 2 : "zero mid-array";
 *            assert jump(new int[]{0}) == 0            : "single element";
 *            assert jump(new int[]{1, 2}) == 1         : "two elements";
 *            assert jump(new int[]{5, 1, 1, 1, 1}) == 1: "one big jump";
 *            assert jump(new int[]{1, 1, 1, 1}) == 3   : "all ones";
 *            System.out.println("All assertions passed.");
 *        }
 *
 *        public static void main(String[] args) {
 *            verify(); // run with:  java -ea JumpGameIIVerify
 *        }
 *    }
 *
 * | Approach    | Risk                                        | Mitigation |
 * |-------------|---------------------------------------------|------------|
 * | Brute force | Exponential time; stack overflow on large n | Small inputs only; add memoization -> O(n^2). |
 * | DP          | O(n^2) too slow near n = 10^4 with large nums[i] | Prefer greedy; DP only if requested. |
 * | Greedy      | Off-by-one loop bound -> phantom extra jump | Loop to i < n - 1; unit-test the [0] case. |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #45 - Difficulty: Medium - Approx. total interview appearances: very high
 * (one of the most-asked greedy/array problems).
 *
 * | Company            | Frequency (stars) | Notes |
 * |--------------------|-------------------|-------|
 * | Amazon             | *****             | Extremely common; often paired with Jump Game I. |
 * | Google             | ****              | Tests greedy-vs-DP reasoning and complexity justification. |
 * | Microsoft          | ****              | Frequent phone-screen array question. |
 * | Meta (Facebook)    | ****              | Interviewers probe why the greedy is optimal. |
 * | Apple              | ***               | Appears in array/greedy rounds. |
 * | Bloomberg          | ***               | Common in first-round coding. |
 * | Adobe              | ***               | Shows up in on-site loops. |
 * | Uber               | **                | Occasional greedy-round question. |
 * | TikTok / ByteDance | ***               | Rising frequency in algorithm rounds. |
 * | Goldman Sachs      | **                | Seen in tech assessments. |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach              | Time   | Space | Code Complexity   | Recommended? |
 * |-----------------------|--------|-------|-------------------|--------------|
 * | Brute-Force Recursion | O(2^n) | O(n)  | Low (impractical) | [X] Understanding only — too slow |
 * | Dynamic Programming   | O(n^2) | O(n)  | Moderate          | [check] Acceptable if DP requested; risky at max n |
 * | Greedy / BFS Layers   | O(n)   | O(1)  | Low               | [check][check] Best on both time and space |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use the Greedy / BFS-layers solution. It's O(n) time and O(1) space, and it
 * strictly dominates every alternative on both axes, so there's no time-vs-space
 * tradeoff to weigh. DP or brute force are preferable only as teaching steps.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * Model this as BFS over contiguous ranges collapsed into a linear scan: keep
 * currentEnd (the boundary of what your current jump count can reach) and farthest
 * (the best reach for one more jump), and increment your jump counter only when
 * the scan index hits the boundary. The killer gotcha is the loop bound — iterate
 * to n - 2, never n - 1, or you'll count a phantom jump on the goal cell.
 */
// @formatter:on
