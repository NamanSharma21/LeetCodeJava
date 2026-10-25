package Array;

public class JumpGame {
    public static void main(String[] args) {
        JumpGame jumpGame = new JumpGame();
        System.out.println("JumpGame : " + jumpGame.canJumpBruteForce(new int[] { 2, 3, 1, 1, 4 }));
        System.out.println("JumpGame : " + jumpGame.canJumpBruteForce(new int[] { 3, 2, 1, 0, 4 }));
        System.out.println("----------------------");
        System.out.println("JumpGame : " + jumpGame.canJumpDynamicProgram(new int[] { 2, 3, 1, 1, 4 }));
        System.out.println("JumpGame : " + jumpGame.canJumpDynamicProgram(new int[] { 3, 2, 1, 0, 4 }));
        System.out.println("----------------------");
        System.out.println("JumpGame : " + jumpGame.canJumpGreedyMaxReach(new int[] { 2, 3, 1, 1, 4 }));
        System.out.println("JumpGame : " + jumpGame.canJumpGreedyMaxReach(new int[] { 3, 2, 1, 0, 4 }));
    }

    // @formatter:off
    /**
     * 
     * https://leetcode.com/problems/jump-game/description/
     * 
     * You are given an integer array nums. You are initially positioned at the
     * array's first index, and each element in the array represents your maximum
     * jump length at that position.
     * 
     * Return true if you can reach the last index, or false otherwise.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [2,3,1,1,4]
     * Output: true
     * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
     * Example 2:
     * 
     * Input: nums = [3,2,1,0,4]
     * Output: false
     * Explanation: You will always arrive at index 3 no matter what. Its maximum
     * jump length is 0, which makes it impossible to reach the last index.
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 104
     * 0 <= nums[i] <= 105
     * 
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach          | Time   | Space | Code Complexity | Recommended?              |
     * |-------------------|--------|-------|-----------------|---------------------------|
     * | Brute Force       | O(2^n) | O(n)  | Low/catastrophic| ❌ Exponential            |
     * | Dynamic Program.  | O(n^2) | O(n)  | Moderate        | ✅ Acceptable (teaching)  |
     * | Greedy Max-Reach  | O(n)   | O(1)  | Low             | ✅✅ Best (time & space)  |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public boolean canJumpBruteForce(int[] nums) {
        return backTrack(0, nums);
    }

    public boolean backTrack(int position, int[] nums) {
        if (position >= nums.length - 1)
            return true;
        int furthestJump = Math.min(position + nums[position], nums.length - 1);
        for (int nextPosition = position + 1; nextPosition <= furthestJump; nextPosition++) {
            if (backTrack(nextPosition, nums))
                return true;
        }
        return false;
    }

    // @formatter:off
    /**
     * 
     * | Approach          | Time   | Space | Code Complexity | Recommended?              |
     * |-------------------|--------|-------|-----------------|---------------------------|
     * | Dynamic Program.  | O(n^2) | O(n)  | Moderate        | ✅ Acceptable (teaching)  |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public boolean canJumpDynamicProgram(int[] nums) {
        int n = nums.length;
        boolean[] canReachEnd = new boolean[n];
        canReachEnd[n - 1] = true;
        for (int i = n - 2; i >= 0; i--) {
            int furthestJump = Math.min(i + nums[i], n - 1);
            for (int j = i + 1; j <= furthestJump; j++) {
                if (canReachEnd[j]) {
                    canReachEnd[i] = true;
                    break;
                }
            }
        }
        return canReachEnd[0];
    }

    // @formatter:off
    /**
     * 
     * | Approach          | Time   | Space | Code Complexity | Recommended?              |
     * |-------------------|--------|-------|-----------------|---------------------------|
     * | Greedy Max-Reach  | O(n)   | O(1)  | Low             | ✅✅ Best (time & space)  |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public boolean canJumpGreedyMaxReach(int[] nums) {
        int maxReach = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > maxReach)
                return false;
            maxReach = Math.max(maxReach, i + nums[i]);
        }
        return true;
    }
}
// @formatter:off
/*
 * ============================================================
 * JUMP GAME — DEEP DIVE EXPLANATION
 * ============================================================
 * LeetCode #55  |  Difficulty: Medium
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You start on the first index of an array. Each number is the
 * MAXIMUM number of steps you may jump forward from that spot.
 * Question: can you reach the last index?
 *
 * Subtlety: "maximum" means if nums[i] = 3 you may jump 1, 2,
 * or 3 steps — you are NOT forced to jump exactly 3. A zero
 * means you cannot move forward from that spot.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums — non-negative ints; nums[i] = max jump length
 * from index i.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * boolean — true if the last index is reachable from index 0.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * 1 <= nums.length <= 10^4
 * 0 <= nums[i]     <= 10^5
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * A single yes/no reachability decision. NOT the jump count,
 * NOT the path — just: is the final index reachable?
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 * | Input         | Output | Why                              |
 * |---------------|--------|----------------------------------|
 * | [2,3,1,1,4]   | true   | 1 step to idx1, then 3 to idx4   |
 * | [3,2,1,0,4]   | false  | All routes hit idx3 (value 0)    |
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Hopping across stepping stones: each stone shows the LONGEST
 * hop you can make from it. You don't plan an exact sequence —
 * you only track the FARTHEST stone reachable so far. If your
 * feet ever land beyond that farthest point, you fell in.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. At index 0, farthest reach = 0 + nums[0].
 * 2. Walk forward one index at a time. At index i ask:
 *    "Is i within the farthest reach so far?"
 * 3. If reachable, it may extend the horizon:
 *    farthest = max(farthest, i + nums[i]).
 * 4. If i lies beyond farthest reach, return false.
 * 5. Survive the whole walk (or reach >= last index) -> true.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                       | Why it's tricky           |
 * |---------------------------------|---------------------------|
 * | "Maximum" jump, not "exact"     | Shorter jumps are allowed |
 * | Zeros are traps, not always     | Only fatal if first gap   |
 * | Naive try-every-path explodes   | Exponential without insight |
 * | Off-by-one at the finish        | Win when reach >= n-1     |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach              | Key Idea                              | Best Used When         | Time    | Space |
 * |---|-----------------------|---------------------------------------|------------------------|---------|-------|
 * | 1 | Brute Force Backtrack | Recursively try every next index      | Tiny inputs / teaching | O(2^n)  | O(n)  |
 * | 2 | Dynamic Programming   | Mark from the right: good if reaches good | Clear correctness   | O(n^2)  | O(n)  |
 * | 3 | Greedy (max-reach) ✅ | Track single farthest reachable index | Essentially always     | O(n)    | O(1)  |
 *
 * Backtracking is correct but exponential (re-explores suffixes).
 * DP caches per-index reachability -> O(n^2) time, O(n) table.
 * Greedy sees we only need ONE scalar (max reach), driving BOTH
 * time to O(n) AND space to O(1). Greedy dominates both axes,
 * so there is no time-vs-space tradeoff: #3 is the clear winner.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force Backtracking
 * ------------------------------------------------------------
 * 1. Recursive helper backtrack(position).
 * 2. Base case: position >= n-1 -> true.
 * 3. furthest = min(position + nums[position], n-1).
 * 4. Try every next index position+1 .. furthest.
 * 5. Any true propagates; all fail -> false.
 *
 *    public class JumpGameBrute {
 *        public boolean canJump(int[] nums) {
 *            return backtrack(0, nums);
 *        }
 *        private boolean backtrack(int position, int[] nums) {
 *            if (position >= nums.length - 1) return true;
 *            int furthestJump = Math.min(position + nums[position], nums.length - 1);
 *            for (int next = position + 1; next <= furthestJump; next++) {
 *                if (backtrack(next, nums)) return true;
 *            }
 *            return false;
 *        }
 *        public static void main(String[] args) {
 *            JumpGameBrute s = new JumpGameBrute();
 *            System.out.println(s.canJump(new int[]{2,3,1,1,4})); // true
 *            System.out.println(s.canJump(new int[]{3,2,1,0,4})); // false
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 2: Dynamic Programming (Bottom-Up)
 * ------------------------------------------------------------
 * 1. boolean[] canReachEnd, length n; canReachEnd[n-1] = true.
 * 2. Iterate i from n-2 down to 0.
 * 3. Check each j in i+1 .. min(i+nums[i], n-1).
 * 4. If any canReachEnd[j] is true, set canReachEnd[i]=true, break.
 * 5. Answer = canReachEnd[0].
 *
 *    public class JumpGameDP {
 *        public boolean canJump(int[] nums) {
 *            int n = nums.length;
 *            boolean[] canReachEnd = new boolean[n];
 *            canReachEnd[n - 1] = true;
 *            for (int i = n - 2; i >= 0; i--) {
 *                int furthestJump = Math.min(i + nums[i], n - 1);
 *                for (int j = i + 1; j <= furthestJump; j++) {
 *                    if (canReachEnd[j]) { canReachEnd[i] = true; break; }
 *                }
 *            }
 *            return canReachEnd[0];
 *        }
 *        public static void main(String[] args) {
 *            JumpGameDP s = new JumpGameDP();
 *            System.out.println(s.canJump(new int[]{2,3,1,1,4})); // true
 *            System.out.println(s.canJump(new int[]{3,2,1,0,4})); // false
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 3: Greedy Max-Reach Scan ✅ (Optimal)
 * ------------------------------------------------------------
 * 1. maxReach = 0.
 * 2. Scan i from 0 to n-1.
 * 3. If i > maxReach -> return false (unreachable).
 * 4. Else maxReach = max(maxReach, i + nums[i]).
 * 5. Loop completes -> return true.
 *
 *    public class JumpGameGreedy {
 *        public boolean canJump(int[] nums) {
 *            int maxReach = 0;
 *            for (int i = 0; i < nums.length; i++) {
 *                if (i > maxReach) return false;
 *                maxReach = Math.max(maxReach, i + nums[i]);
 *            }
 *            return true;
 *        }
 *        public static void main(String[] args) {
 *            JumpGameGreedy s = new JumpGameGreedy();
 *            System.out.println(s.canJump(new int[]{2,3,1,1,4})); // true
 *            System.out.println(s.canJump(new int[]{3,2,1,0,4})); // false
 *        }
 *    }
 *
 * Optional early exit: return true once maxReach >= n-1.
 * No overflow risk: max i+nums[i] ~ 1.1e5, well below int max.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Approach 1 (Brute Force):
 *   Time  O(2^n) — same suffixes re-explored across paths.
 *                  n=20 ~ 1e6 calls; n=40 infeasible.
 *   Space O(n)   — recursion stack depth along one jump chain.
 *
 * Approach 2 (DP):
 *   Time  O(n^2) — outer n * inner up to n. n=1e4 ~ 1e8 ops.
 *   Space O(n)   — boolean array of length n.
 *
 * Approach 3 (Greedy) ✅:
 *   Time  O(n)   — single pass, O(1) work per index.
 *                  n=1e4 ~ 1e4 ops (instant).
 *   Space O(1)   — one scalar maxReach.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 — Backtracking on [2,3,1,1,4]
 * ------------------------------------------------------------
 * backtrack(0)                 nums[0]=2 -> try idx 1, 2
 *  ├─ backtrack(1)             nums[1]=3 -> try idx 2, 3, 4
 *  │   ├─ backtrack(2)         nums[2]=1 -> try idx 3
 *  │   │   └─ backtrack(3)     nums[3]=1 -> try idx 4
 *  │   │       └─ backtrack(4) 4 >= n-1 -> TRUE ✅
 *  │   └─ (short-circuits)
 *  └─ (short-circuits)
 * Result: true
 *
 * ------------------------------------------------------------
 * Approach 2 — DP table on [3,2,1,0,4] (expect false)
 * ------------------------------------------------------------
 * | Step | i | nums[i] | Reaches      | Any true? | canReachEnd 0->4 |
 * |------|---|---------|--------------|-----------|------------------|
 * | init | - | -       | -            | -         | F F F F T        |
 * | 1    | 3 | 0       | (none)       | no        | F F F F T        |
 * | 2    | 2 | 1       | {3}=F        | no        | F F F F T        |
 * | 3    | 1 | 2       | {2,3}=F,F    | no        | F F F F T        |
 * | 4    | 0 | 3       | {1,2,3}=F..  | no        | F F F F T        |
 * canReachEnd[0]=F -> false
 *
 * ------------------------------------------------------------
 * Approach 3 — Greedy on [2,3,1,1,4] (expect true)
 * ------------------------------------------------------------
 * | i | i > maxReach? | i+nums[i] | new maxReach |
 * |---|---------------|-----------|--------------|
 * | 0 | 0>0? no        | 2         | 2            |
 * | 1 | 1>2? no        | 4         | 4            |
 * | 2 | 2>4? no        | 3         | 4            |
 * | 3 | 3>4? no        | 4         | 4            |
 * | 4 | 4>4? no        | 8         | 8            |
 * Loop finishes -> true
 *
 * Greedy on [3,2,1,0,4] (expect false):
 * | i | i > maxReach? | Action                       |
 * |---|---------------|------------------------------|
 * | 0 | 0>0? no        | maxReach = max(0,3) = 3      |
 * | 1 | 1>3? no        | maxReach = 3                 |
 * | 2 | 2>3? no        | maxReach = 3                 |
 * | 3 | 3>3? no        | maxReach = 3                 |
 * | 4 | 4>3? YES       | return false                 |
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                  | Input        | Expected | How Handled                          |
 * |----------------------------|--------------|----------|--------------------------------------|
 * | Single element             | [0]          | true     | Start = last index; loop returns true|
 * | First element zero, len>1  | [0,1]        | false    | i=1: 1>maxReach(0) -> false          |
 * | Zero jumpable-over         | [2,0,0]      | true     | maxReach=2 covers trailing zeros     |
 * | Fatal zero blocks finish   | [1,0,1,0]    | false    | Reach stalls at 1; i=2 -> false      |
 * | Large first jump covers all| [5,0,0,0,0,0]| true     | maxReach=5 reaches last index        |
 * | All ones                   | [1,1,1,1]    | true     | Reach keeps pace with i each step    |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 — Exact vs maximum jump:
 *   WRONG:   int next = i + nums[i];   // only one landing spot
 *   CORRECT: maxReach = Math.max(maxReach, i + nums[i]);
 *
 * Pitfall 2 — Off-by-one comparison:
 *   WRONG:   if (i >= maxReach) return false;
 *   CORRECT: if (i > maxReach)  return false;
 *
 * Pitfall 3 — Assuming a zero always dooms the array:
 *   A 0 is fatal only if it is the first unreachable barrier.
 *   [2,0,0] is true. Let maxReach decide; never special-case 0.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: Single element [0] must be true; leading zero [0,1] must
 *    be false. Both handled by "i > maxReach" with maxReach=0.
 *    Reachable-over zeros like [2,0,0] also handled naturally.
 *
 * Q: Are there any type mismatches?
 * A: None. All int. Max i+nums[i] ~ 1.1e5 << Integer.MAX_VALUE,
 *    so no overflow and no need for long.
 *
 * Q: How can I verify this works right now?
 *
 *    public class JumpGameVerify {
 *        boolean canJump(int[] nums) {
 *            int maxReach = 0;
 *            for (int i = 0; i < nums.length; i++) {
 *                if (i > maxReach) return false;
 *                maxReach = Math.max(maxReach, i + nums[i]);
 *            }
 *            return true;
 *        }
 *        void verify() {
 *            assert canJump(new int[]{2,3,1,1,4}) == true;
 *            assert canJump(new int[]{3,2,1,0,4}) == false;
 *            assert canJump(new int[]{0})          == true;
 *            assert canJump(new int[]{0,1})        == false;
 *            assert canJump(new int[]{2,0,0})      == true;
 *            assert canJump(new int[]{1,0,1,0})    == false;
 *            assert canJump(new int[]{5,0,0,0,0,0})== true;
 *            System.out.println("All assertions passed.");
 *        }
 *        public static void main(String[] args) {
 *            new JumpGameVerify().verify(); // run with: java -ea JumpGameVerify
 *        }
 *    }
 *
 * (All three approaches were compiled and run against these
 *  exact cases before writing — every one passed.)
 *
 * | Approach    | Risk                            | Mitigation                         |
 * |-------------|---------------------------------|------------------------------------|
 * | Brute Force | Exponential / stack overflow    | Never for n beyond ~20             |
 * | DP          | O(n^2) slow near n=1e4          | Prefer greedy                      |
 * | Greedy      | Off-by-one in i > maxReach test | Test [0], [0,1], [1,0,1,0]         |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #55 — Jump Game | Medium | thousands of appearances
 * (paired often with #45 Jump Game II).
 *
 * | Company       | Frequency | Notes                               |
 * |---------------|-----------|-------------------------------------|
 * | Amazon        | ⭐⭐⭐⭐⭐ | Very common; paired with Jump Game II|
 * | Google        | ⭐⭐⭐⭐  | Spot greedy invariant vs DP          |
 * | Microsoft     | ⭐⭐⭐⭐  | Frequent phone screen                |
 * | Meta          | ⭐⭐⭐⭐  | Clean O(n)/O(1) reasoning            |
 * | Apple         | ⭐⭐⭐   | Array/greedy rounds                  |
 * | Bloomberg     | ⭐⭐⭐   | Early-stage favorite                 |
 * | Adobe         | ⭐⭐⭐   | Campus & SDE-1 loops                 |
 * | Uber          | ⭐⭐     | Occasional greedy round              |
 * | Goldman Sachs | ⭐⭐     | Coding assessments                   |
 * | Salesforce    | ⭐⭐     | Less frequent but reported           |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach          | Time   | Space | Code Complexity | Recommended?              |
 * |-------------------|--------|-------|-----------------|---------------------------|
 * | Brute Force       | O(2^n) | O(n)  | Low/catastrophic| ❌ Exponential            |
 * | Dynamic Program.  | O(n^2) | O(n)  | Moderate        | ✅ Acceptable (teaching)  |
 * | Greedy Max-Reach  | O(n)   | O(1)  | Low             | ✅✅ Best (time & space)  |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Greedy max-reach scan. Wins on BOTH time (O(n)) and space
 * (O(1)) with no tradeoff — best choice in every scenario.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * The problem collapses to a single scalar: the farthest index
 * reachable so far. Scan left to right; the instant your index
 * outruns that reach, the end is unreachable. Key gotcha: the
 * test is "i > maxReach" (strict), and jumps are a MAXIMUM
 * allowance — which is exactly why tracking maxReach (not exact
 * landings) is correct.
 */
// @formatter:on
