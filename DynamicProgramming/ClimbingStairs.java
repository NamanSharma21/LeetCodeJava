package DynamicProgramming;

import java.util.HashMap;
import java.util.Map;

public class ClimbingStairs {
    public static void main(String[] args) {
        ClimbingStairs climbingStairs = new ClimbingStairs();
        System.out.println("No Of Ways : " + climbingStairs.climbStairsTabulation(3));
        System.out.println("No Of Ways : " + climbingStairs.climbingStairsMemoization(3));
        System.out.println("No Of Ways : " + climbingStairs.climbingStairsSpaceOptimizedThreeVar(3));
    }

    // @formatter:off
    /*
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/97/dynamic-programming/569/
     * 
     * 
     * You are climbing a staircase. It takes n steps to reach the top.
     * 
     * Each time you can either climb 1 or 2 steps. In how many distinct ways can
     * you climb to the top?
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: n = 2
     * Output: 2
     * Explanation: There are two ways to climb to the top.
     * 1. 1 step + 1 step
     * 2. 2 steps
     * Example 2:
     * 
     * Input: n = 3
     * Output: 3
     * Explanation: There are three ways to climb to the top.
     * 1. 1 step + 1 step + 1 step
     * 2. 1 step + 2 steps
     * 3. 2 steps + 1 step
     * 
     * 
     * Constraints:
     * 
     * 1 <= n <= 45
     */
    // @formatter:on

    public int climbStairsTabulation(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        int[] table = new int[n + 1];
        table[0] = 1;
        table[1] = 1;
        for (int i = 2; i <= n; i++) {
            table[i] = table[i - 1] + table[i - 2];
        }
        return table[n];
    }

    public int climbingStairsMemoization(int n) {
        Map<Integer, Integer> memo = new HashMap<>();
        return climbingStairsMemoization(n, memo);
    }

    public int climbingStairsMemoization(int n, Map<Integer, Integer> memo) {
        if (n == 0 || n == 1) {
            return 1;
        }
        if (!memo.containsKey(n)) {
            memo.put(n, climbingStairsMemoization(n - 1, memo) + climbingStairsMemoization(n - 2, memo));
        }
        return memo.get(n);
    }

    public int climbingStairsSpaceOptimizedThreeVar(int n) {
        if (n <= 1)
            return 1;
        int prev = 1;
        int current = 1;

        for (int i = 2; i <= n; i++) {
            int next = prev + current;
            prev = current;
            current = next;
        }
        return current;
    }
}

// @formatter:off
/*
 * ============================================================
 *  CLIMBING STAIRS — DEEP DIVE
 * ============================================================
 *
 * ============================================================
 *  1. PROBLEM STATEMENT
 * ============================================================
 *
 *  Plain English Description:
 *  ---------------------------
 *  You are climbing a staircase with `n` steps. Each time you
 *  can either climb 1 step or 2 steps. Return the total number
 *  of distinct ways to reach the top (step `n`), starting from
 *  step `0`.
 *
 *  Input / Output:
 *  ---------------
 *  Input  : A single integer `n` (number of stairs)
 *  Output : A single integer — total distinct ways to climb to step `n`
 *
 *  Constraints:
 *  ------------
 *  - 1 <= n <= 45
 *  - No negative values, no arrays — purely a single integer input
 *
 *  What Needs to be Computed:
 *  --------------------------
 *  For each step `i`, you can arrive from step `i-1` (by taking 1 step)
 *  OR from step `i-2` (by taking 2 steps). Count ALL unique sequences
 *  of moves (1s and 2s) that sum to exactly `n`.
 *
 *
 * ============================================================
 *  2. INTUITION
 * ============================================================
 *
 *  The Core Insight:
 *  -----------------
 *  Think about the LAST move you make to reach step `n`:
 *    - You either came from step n-1 (took 1 step), OR
 *    - You came from step n-2 (took 2 steps)
 *
 *  So: ways(n) = ways(n-1) + ways(n-2)
 *  This is EXACTLY the Fibonacci sequence in disguise!
 *
 *  Human Reasoning:
 *  ----------------
 *    n=1 → only one way: [1]
 *    n=2 → two ways: [1,1] or [2]
 *    n=3 → three ways: [1,1,1], [1,2], [2,1]
 *    n=4 → five ways: [1,1,1,1], [1,1,2], [1,2,1], [2,1,1], [2,2]
 *
 *  Notice: ways(3) = ways(2) + ways(1) = 2 + 1 = 3  ✓
 *  Notice: ways(4) = ways(3) + ways(2) = 3 + 2 = 5  ✓
 *
 *  What Makes It Interesting / Tricky:
 *  ------------------------------------
 *  - It LOOKS like a combinatorics problem but is really a DP/recurrence problem
 *  - The naive recursive solution has EXPONENTIAL overlap — same subproblems
 *    are solved repeatedly
 *  - Recognizing the Fibonacci pattern unlocks a beautiful O(1) space solution
 *
 *
 * ============================================================
 *  3. APPROACH OVERVIEW
 * ============================================================
 *
 *  #  | Approach                  | Time    | Space | Use When
 *  ---|---------------------------|---------|-------|----------------------------
 *  1  | Brute Force Recursion     | O(2^n)  | O(n)  | Never in production
 *  2  | Memoization (Top-Down DP) | O(n)    | O(n)  | Natural recursive thinking
 *  3  | Tabulation (Bottom-Up DP) | O(n)    | O(n)  | Clean, interview-safe
 *  4  | Space-Optimized DP ★      | O(n)    | O(1)  | OPTIMAL — use in interviews
 *  5  | Matrix Exponentiation     | O(logn) | O(1)  | Theoretical; overkill here
 *
 *  ★ RECOMMENDED: Space-Optimized DP (Approach 4)
 *    — linear time, constant space, clean code.
 *
 *
 * ============================================================
 *  4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 *  APPROACH 1: Brute Force Recursion
 * ------------------------------------------------------------
 *
 *  Algorithm:
 *  1. Base case: if n == 0 or n == 1, return 1
 *  2. Recurse: climbStairs(n) = climbStairs(n-1) + climbStairs(n-2)
 *  3. No caching — every call branches into two more
 *
 *  Why it fails at scale:
 *  For n=45, this makes ~2^36 calls. climbStairs(44) and climbStairs(43)
 *  are each fully recomputed from scratch.
 *
 * ------------------------------------------------------------
 *
 *  public class ClimbStairsBruteForce {
 *
 *      public int climbStairs(int n) {
 *          // Base cases: 0 or 1 step remaining = exactly 1 way
 *          if (n <= 1) return 1;
 *          return climbStairs(n - 1) + climbStairs(n - 2);
 *      }
 *  }
 *
 * ------------------------------------------------------------
 *  APPROACH 2: Memoization (Top-Down DP)
 * ------------------------------------------------------------
 *
 *  Algorithm:
 *  1. Create a memo[] array of size n+1, initialized to -1
 *  2. Before recursing, check if memo[n] is already computed
 *  3. Store result in memo[n] before returning
 *
 *  Improvement: Each subproblem is computed exactly once.
 *  Redundant branches are pruned by cache lookup.
 *
 * ------------------------------------------------------------
 *
 *  import java.util.Arrays;
 *
 *  public class ClimbStairsMemo {
 *
 *      private int[] memo;
 *
 *      public int climbStairs(int n) {
 *          memo = new int[n + 1];
 *          Arrays.fill(memo, -1);
 *          return solve(n);
 *      }
 *
 *      private int solve(int n) {
 *          if (n <= 1) return 1;
 *
 *          // Return cached result if already computed
 *          if (memo[n] != -1) return memo[n];
 *
 *          memo[n] = solve(n - 1) + solve(n - 2);
 *          return memo[n];
 *      }
 *  }
 *
 * ------------------------------------------------------------
 *  APPROACH 3: Tabulation (Bottom-Up DP)
 * ------------------------------------------------------------
 *
 *  Algorithm:
 *  1. Create dp[] of size n+1
 *  2. Set dp[0] = 1, dp[1] = 1 (base cases)
 *  3. For each i from 2 to n: dp[i] = dp[i-1] + dp[i-2]
 *  4. Return dp[n]
 *
 * ------------------------------------------------------------
 *
 *  public class ClimbStairsTabulation {
 *
 *      public int climbStairs(int n) {
 *          if (n <= 1) return 1;
 *
 *          int[] dp = new int[n + 1];
 *          dp[0] = 1; // 1 way to stand at the start (do nothing)
 *          dp[1] = 1; // 1 way to reach step 1
 *
 *          for (int i = 2; i <= n; i++) {
 *              dp[i] = dp[i - 1] + dp[i - 2];
 *          }
 *
 *          return dp[n];
 *      }
 *  }
 *
 * ------------------------------------------------------------
 *  APPROACH 4: Space-Optimized DP (OPTIMAL) ★
 * ------------------------------------------------------------
 *
 *  Algorithm:
 *  1. We only ever need the LAST TWO VALUES to compute the next
 *  2. Use two variables:
 *       prev2 = ways to reach i-2
 *       prev1 = ways to reach i-1
 *  3. Roll forward: current = prev1 + prev2
 *  4. Shift:        prev2 = prev1,  prev1 = current
 *
 * ------------------------------------------------------------
 *
 *  public class ClimbStairsOptimal {
 *
 *      public int climbStairs(int n) {
 *          if (n <= 1) return 1;
 *
 *          int prev2 = 1; // ways to reach step 0 (or i-2)
 *          int prev1 = 1; // ways to reach step 1 (or i-1)
 *
 *          for (int i = 2; i <= n; i++) {
 *              int current = prev1 + prev2; // ways to reach step i
 *              prev2 = prev1;               // slide the window forward
 *              prev1 = current;
 *          }
 *
 *          return prev1;
 *      }
 *  }
 *
 * ------------------------------------------------------------
 *  APPROACH 5: Matrix Exponentiation (Advanced / Theoretical)
 * ------------------------------------------------------------
 *
 *  For completeness — Fibonacci can be computed in O(log n) via
 *  matrix power:
 *
 *  | F(n+1) |   | 1 1 |^n   | 1 |
 *  | F(n)   | = | 1 0 |   * | 0 |
 *
 * ------------------------------------------------------------
 *
 *  public class ClimbStairsMatrix {
 *
 *      public int climbStairs(int n) {
 *          int[][] matrix = {{1, 1}, {1, 0}};
 *          int[][] result = matrixPow(matrix, n);
 *          return result[0][0]; // F(n+1) = climbStairs(n)
 *      }
 *
 *      private int[][] matrixPow(int[][] matrix, int power) {
 *          int[][] result = {{1, 0}, {0, 1}}; // identity matrix
 *          while (power > 0) {
 *              if ((power & 1) == 1) {
 *                  result = matrixMultiply(result, matrix);
 *              }
 *              matrix = matrixMultiply(matrix, matrix);
 *              power >>= 1;
 *          }
 *          return result;
 *      }
 *
 *      private int[][] matrixMultiply(int[][] a, int[][] b) {
 *          return new int[][] {
 *              { a[0][0]*b[0][0] + a[0][1]*b[1][0],
 *                a[0][0]*b[0][1] + a[0][1]*b[1][1] },
 *              { a[1][0]*b[0][0] + a[1][1]*b[1][0],
 *                a[1][0]*b[0][1] + a[1][1]*b[1][1] }
 *          };
 *      }
 *  }
 *
 *  NOTE: For n <= 45, this is over-engineering. Use Approach 4 in interviews.
 *
 *
 * ============================================================
 *  5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 *  Approach              | Time    | Space | Reasoning
 *  ----------------------|---------|-------|------------------------------------
 *  Brute Force           | O(2^n)  | O(n)  | Binary recursion tree; stack depth n
 *  Memoization           | O(n)    | O(n)  | n+1 states computed once; stack O(n)
 *  Tabulation            | O(n)    | O(n)  | Single loop; dp array of size n+1
 *  Space-Optimized DP ★  | O(n)    | O(1)  | Single loop; only 2 integer variables
 *  Matrix Exponentiation | O(logn) | O(1)  | Matrix halves the problem each step
 *
 *  Concrete Example — n = 10:
 *  --------------------------
 *  Brute Force  : ~2^10 = 1,024 calls
 *  Optimal DP   : exactly 8 loop iterations (i=2 to 10)
 *
 *  n = 45 (max constraint):
 *  -------------------------
 *  Brute Force  : ~2^45 ≈ 35 trillion operations — completely infeasible
 *  Optimal DP   : exactly 43 loop iterations — instant
 *
 *
 * ============================================================
 *  6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 *  Example: n = 5
 *  --------------
 *
 *  Approach 1 — Brute Force Recursion Tree:
 *
 *                      f(5)
 *                  /         \
 *              f(4)            f(3)
 *             /    \          /    \
 *          f(3)   f(2)     f(2)   f(1)
 *          / \    / \      / \
 *       f(2) f(1) f(1) f(0) f(1) f(0)
 *       / \
 *    f(1) f(0)
 *
 *  Many nodes are RECOMPUTED (f(3) appears twice, f(2) three times).
 *  Result: f(5) = 8
 *
 *  Approach 3 — Tabulation DP Table:
 *
 *  Step i | dp[i] = dp[i-1] + dp[i-2] | Reasoning
 *  -------|----------------------------|----------------------------
 *    0    |            1               | Base: 1 way to be at start
 *    1    |            1               | Base: take one 1-step
 *    2    |          1+1 = 2           | [1,1] or [2]
 *    3    |          2+1 = 3           | [1,1,1], [1,2], [2,1]
 *    4    |          3+2 = 5           | [1,1,1,1],[1,2,1],[2,1,1],[1,1,2],[2,2]
 *    5    |          5+3 = 8           | All 8 distinct sequences
 *
 *  Output: 8  ✓
 *
 *  Approach 4 — Space-Optimized (Variable Roll):
 *
 *  i     | prev2 | prev1 | current = prev1+prev2 | Action
 *  -------|-------|-------|----------------------|------------------
 *  start  |   1   |   1   |          —           | initialized
 *  i=2    |   1   |   1   |          2           | prev2=1, prev1=2
 *  i=3    |   1   |   2   |          3           | prev2=2, prev1=3
 *  i=4    |   2   |   3   |          5           | prev2=3, prev1=5
 *  i=5    |   3   |   5   |          8           | prev2=5, prev1=8
 *
 *  Return prev1 = 8  ✓
 *
 *  Example: n = 1
 *  --------------
 *  Only one move: [1]
 *  climbStairs(1) → returns 1 via base case check
 *
 *  Example: n = 2
 *  --------------
 *  Two moves: [1,1] or [2]
 *  Loop runs once (i=2): current = 1+1 = 2 → returns 2  ✓
 *
 *
 * ============================================================
 *  7. EDGE CASES
 * ============================================================
 *
 *  Edge Case      | Value | Expected    | Handled?
 *  ---------------|-------|-------------|-----------------------------------
 *  Single step    | n=1   | 1           | ✓ Base case: if (n <= 1) return 1
 *  Two steps      | n=2   | 2           | ✓ First loop iteration
 *  Max constraint | n=45  | 1836311903  | ✓ Fits in int (max int ~2.1B)
 *  Overflow check | n=45  | 1836311903  | ✓ No overflow for n<=45
 *  n=0            | impl. | 1           | ✓ Handled as base case
 *
 *  Overflow Analysis:
 *  ------------------
 *  Fibonacci(47) = 2,971,215,073 > Integer.MAX_VALUE (2,147,483,647)
 *  For n <= 45, climbStairs(45) = 1,836,311,903 — safely within int.
 *  If constraints were larger, switch to long.
 *
 *
 * ============================================================
 *  8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 *  Q: What edge cases might this miss?
 *  ------------------------------------
 *  - n=0 : Problem states n >= 1, but if called with 0,
 *           n <= 1 returns 1 (mathematically correct — 1 way to do nothing)
 *  - n=1 : Handled by if (n <= 1) return 1 before the loop
 *  - Overflow: For the given constraint n <= 45, int is safe.
 *              If n were extended, we'd need long.
 *
 *  Q: Are there any type mismatches?
 *  -----------------------------------
 *  - All variables are int, return type is int —
 *    matches LeetCode's method signature
 *  - No casting issues; no array index out of bounds
 *    since loop is 2 <= i <= n
 *
 *  Q: How can I verify this works right now?
 *  ------------------------------------------
 *
 *  public static void main(String[] args) {
 *      ClimbStairsOptimal sol = new ClimbStairsOptimal();
 *
 *      // Known Fibonacci-mapped values
 *      System.out.println(sol.climbStairs(1));  // Expected: 1
 *      System.out.println(sol.climbStairs(2));  // Expected: 2
 *      System.out.println(sol.climbStairs(3));  // Expected: 3
 *      System.out.println(sol.climbStairs(5));  // Expected: 8
 *      System.out.println(sol.climbStairs(10)); // Expected: 89
 *      System.out.println(sol.climbStairs(45)); // Expected: 1836311903
 *  }
 *
 *  Brute Force Risks:
 *  ------------------
 *  - Brute force will TIME OUT or STACK OVERFLOW for n >= 40
 *    in interview environments
 *  - Memoization recursive solution may cause stack overflow for very
 *    large n if Java's default stack size is small
 *    (not an issue for n <= 45)
 *
 *
 * ============================================================
 *  9. FINAL SUMMARY
 * ============================================================
 *
 *  Approach              | Time    | Space | Practical?
 *  ----------------------|---------|-------|----------------------------
 *  Brute Force Recursion | O(2^n)  | O(n)  | ✗ Too slow
 *  Memoization           | O(n)    | O(n)  | ✓ Good for interviews
 *  Tabulation            | O(n)    | O(n)  | ✓ Clean and readable
 *  Space-Opt DP ★        | O(n)    | O(1)  | ★ BEST — use this
 *  Matrix Exponentiation | O(logn) | O(1)  | ~ Academic / system design
 *
 *  Recommendation:
 *  ---------------
 *  Use Space-Optimized DP — it's O(n) time, O(1) space, trivially
 *  simple to code, and demonstrates you recognize the Fibonacci pattern.
 *
 *  Key Takeaway:
 *  -------------
 *  "When a problem's answer at position `i` depends only on the previous
 *  two positions, recognize it as a SLIDING-WINDOW FIBONACCI DP. You
 *  never need more than two variables."
 *
 *
 * ============================================================
 *  10. COMPANY APPEARANCES
 * ============================================================
 *
 *  Company          | Frequency          | Notes
 *  -----------------|--------------------|-----------------------------
 *  Amazon           | ★★★★★ Very High    | Classic warm-up in coding screens
 *  Google           | ★★★★★ Very High    | Often asked as intro DP
 *  Microsoft        | ★★★★★ Very High    | Frequent in SDE 1/2 rounds
 *  Adobe            | ★★★★  High         | Common in phone screens
 *  Apple            | ★★★★  High         | Entry-level interviews
 *  Goldman Sachs    | ★★★   Medium       | Quant/SWE rounds
 *  Uber             | ★★★   Medium       | Early coding rounds
 *  Facebook/Meta    | ★★★   Medium       | Warm-up problem
 *  Bloomberg        | ★★★   Medium       | Frequent in OAs
 *  Cisco            | ★★★   Medium       | SDE interviews
 *
 *  LeetCode Stats (Problem #70):
 *  -----------------------------
 *  - Acceptance Rate     : ~52%
 *  - Difficulty          : Easy
 *  - Total Submissions   : 7+ million
 *  - Interview frequency : Top 10 most frequently asked DP problems globally
 *
 * ============================================================
 */
// @formatter:on
