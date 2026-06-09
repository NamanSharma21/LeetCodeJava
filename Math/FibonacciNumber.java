package Math;

import java.util.HashMap;
import java.util.Map;

public class FibonacciNumber {
    public static void main(String[] args) {
        FibonacciNumber fibonacciNumber = new FibonacciNumber();
        System.out.println("FibonacciNumber : " + fibonacciNumber.fibReccursion(2));
        System.out.println("FibonacciNumber : " + fibonacciNumber.fibReccursion(3));
        System.out.println("FibonacciNumber : " + fibonacciNumber.fibReccursion(4));

        System.out.println("FibonacciNumber : " + fibonacciNumber.fibMemoizationTopDown(2));
        System.out.println("FibonacciNumber : " + fibonacciNumber.fibMemoizationTopDown(3));
        System.out.println("FibonacciNumber : " + fibonacciNumber.fibMemoizationTopDown(4));

        System.out.println("FibonacciNumber : " + fibonacciNumber.fibMemoizationTabulationBottomUp(2));
        System.out.println("FibonacciNumber : " + fibonacciNumber.fibMemoizationTabulationBottomUp(3));
        System.out.println("FibonacciNumber : " + fibonacciNumber.fibMemoizationTabulationBottomUp(4));

        System.out.println("FibonacciNumber : " + fibonacciNumber.fibSpaceOptimizedIteration(2));
        System.out.println("FibonacciNumber : " + fibonacciNumber.fibSpaceOptimizedIteration(3));
        System.out.println("FibonacciNumber : " + fibonacciNumber.fibSpaceOptimizedIteration(4));

        System.out.println("FibonacciNumber : " + fibonacciNumber.fibMatrixExponentiation(2));
        System.out.println("FibonacciNumber : " + fibonacciNumber.fibMatrixExponentiation(3));
        System.out.println("FibonacciNumber : " + fibonacciNumber.fibMatrixExponentiation(4));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/fibonacci-number/
     * The Fibonacci numbers, commonly denoted F(n) form a sequence, called the
     * Fibonacci sequence, such that each number is the sum of the two preceding
     * ones, starting from 0 and 1. That is,
     * 
     * F(0) = 0, F(1) = 1
     * F(n) = F(n - 1) + F(n - 2), for n > 1.
     * Given n, calculate F(n).
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: n = 2
     * Output: 1
     * Explanation: F(2) = F(1) + F(0) = 1 + 0 = 1.
     * Example 2:
     * 
     * Input: n = 3
     * Output: 2
     * Explanation: F(3) = F(2) + F(1) = 1 + 1 = 2.
     * Example 3:
     * 
     * Input: n = 4
     * Output: 3
     * Explanation: F(4) = F(3) + F(2) = 2 + 1 = 3.
     * 
     * 
     * Constraints:
     * 
     * 0 <= n <= 30
     */
    // @formatter:on

    public int fibReccursion(int n) {
        if (n <= 1)
            return n;
        return fibReccursion(n - 1) + fibReccursion(n - 2);
    }

    private Map<Integer, Integer> memo = new HashMap<>();

    public int fibMemoizationTopDown(int n) {
        if (n <= 1)
            return n;
        if (memo.containsKey(n))
            return memo.get(n);
        int result = fibMemoizationTopDown(n - 1) + fibMemoizationTopDown(n - 2);
        memo.put(n, result);
        return result;
    }

    public int fibMemoizationTabulationBottomUp(int n) {
        if (n <= 1)
            return n;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public int fibSpaceOptimizedIteration(int n) {
        if (n <= 1)
            return n;
        int prev = 0;
        int curr = 1;

        for (int i = 2; i <= n; i++) {
            int next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }

    public int fibMatrixExponentiation(int n) {
        if (n <= 1)
            return n;

        long[][] baseMatrix = { { 1, 1 }, { 1, 0 } };
        long[][] resultMatrix = matrixPow(baseMatrix, n);

        // F(n) is at position [0][1] or [1][0] after M^n
        return (int) resultMatrix[0][1];
    }

    private long[][] multiplyMatrix(long[][] A, long[][] B) {
        long[][] result = new long[2][2];
        result[0][0] = A[0][0] * B[0][0] + A[0][1] * B[1][0];
        result[0][1] = A[0][0] * B[0][1] + A[0][1] * B[1][1];
        result[1][0] = A[1][0] * B[0][0] + A[1][1] * B[1][0];
        result[1][1] = A[1][0] * B[0][1] + A[1][1] * B[1][1];
        return result;
    }

    private long[][] matrixPow(long[][] M, int p) {
        long[][] result = { { 1, 0 }, { 0, 1 } };
        while (p > 0) {
            if ((p & 1) == 1) {
                result = multiplyMatrix(result, M);
            }
            M = multiplyMatrix(M, M);
            p >>= 1;
        }
        return result;
    }

    public void cleanUp() {
        this.memo.clear();
    }
}

// @formatter:off
/*
 * ============================================================
 * Fibonacci Number — Deep Dive (Java)
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * What is being asked?
 * --------------------
 * Given an integer `n`, compute the n-th Fibonacci number where:
 *   F(0) = 0
 *   F(1) = 1
 *   F(n) = F(n-1) + F(n-2)  for n >= 2
 *
 * Input Format:
 *   - A single integer `n`
 *   - Constraint: 0 <= n <= 30 (LeetCode #509 standard constraint)
 *
 * Output Format:
 *   - A single integer representing F(n)
 *
 * What exactly needs to be returned?
 *   The value at position n in the Fibonacci sequence (0-indexed):
 *
 *   Index:  0  1  2  3  4  5  6   7   8   9  10
 *   Value:  0  1  1  2  3  5  8  13  21  34  55
 *
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * Core Idea:
 *   Each Fibonacci number is simply the sum of the two numbers
 *   before it. That's the whole recurrence. The challenge isn't
 *   the math — it's doing it efficiently without redundant work.
 *
 * How a Human Reasons About It:
 *   1. You know F(0) and F(1) by definition.
 *   2. To get F(5), you need F(4) + F(3).
 *   3. To get F(4), you need F(3) + F(2) — and F(3) is computed again!
 *   4. A naive recursive solution recomputes the same subproblems
 *      exponentially.
 *   5. The insight: remember (cache) what you've already computed,
 *      or better yet, build bottom-up using just two variables.
 *
 * What Makes This Interesting?
 *   - It's the canonical example of overlapping subproblems in DP.
 *   - It demonstrates the progression: exponential → linear → O(1) space.
 *   - It also has a mathematical closed-form (Binet's Formula) and a
 *     matrix exponentiation approach that achieves O(log n).
 *
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 *  #  | Approach                  | Time    | Space | Use When
 * ----|---------------------------|---------|-------|------------------------------
 *  1  | Naive Recursion           | O(2^n)  | O(n)  | Never in production
 *  2  | Memoization (Top-Down DP) | O(n)    | O(n)  | Small n, interview warm-up
 *  3  | Tabulation (Bottom-Up DP) | O(n)    | O(n)  | Clear DP demonstration
 *  4* | Space-Optimized Iteration | O(n)    | O(1)  | ✅ Recommended — interviews
 *  5  | Matrix Exponentiation     | O(logn) | O(1)  | Large n, competitive prog.
 *  6  | Binet's Formula (Math)    | O(1)    | O(1)  | Trivia / approximation only
 *
 *  * Approach 4 (Space-Optimized Iteration) is optimal for interviews:
 *    O(n) time, O(1) space, clean and readable.
 *
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Naive Recursion
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Base cases: return n if n <= 1 (F(0)=0, F(1)=1).
 *   2. Recursively return fib(n-1) + fib(n-2).
 *
 * Code:
 * ------------------------------------------------------------
 *
 *   public class FibonacciRecursive {
 *
 *       public int fib(int n) {
 *           // Base cases: F(0) = 0, F(1) = 1
 *           if (n <= 1) return n;
 *
 *           // Recursive call — causes exponential recomputation
 *           return fib(n - 1) + fib(n - 2);
 *       }
 *
 *       public static void main(String[] args) {
 *           FibonacciRecursive sol = new FibonacciRecursive();
 *           System.out.println(sol.fib(10)); // 55
 *       }
 *   }
 *
 * ------------------------------------------------------------
 * Approach 2: Memoization (Top-Down DP)
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Use a HashMap to store already-computed results.
 *   2. Before computing fib(n), check if it's in the cache.
 *   3. If yes, return cached value. If no, compute, store, return.
 *
 * Code:
 * ------------------------------------------------------------
 *
 *   import java.util.HashMap;
 *   import java.util.Map;
 *
 *   public class FibonacciMemo {
 *
 *       // Cache to store previously computed Fibonacci values
 *       private Map<Integer, Integer> memo = new HashMap<>();
 *
 *       public int fib(int n) {
 *           if (n <= 1) return n;
 *
 *           // Return cached result if available
 *           if (memo.containsKey(n)) return memo.get(n);
 *
 *           // Compute, cache, and return
 *           int result = fib(n - 1) + fib(n - 2);
 *           memo.put(n, result);
 *           return result;
 *       }
 *
 *       public static void main(String[] args) {
 *           FibonacciMemo sol = new FibonacciMemo();
 *           System.out.println(sol.fib(10)); // 55
 *       }
 *   }
 *
 * ------------------------------------------------------------
 * Approach 3: Tabulation (Bottom-Up DP)
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Create a dp[] array of size n+1.
 *   2. Set dp[0] = 0, dp[1] = 1.
 *   3. Fill from index 2 to n: dp[i] = dp[i-1] + dp[i-2].
 *   4. Return dp[n].
 *
 * Code:
 * ------------------------------------------------------------
 *
 *   public class FibonacciTabulation {
 *
 *       public int fib(int n) {
 *           if (n <= 1) return n;
 *
 *           int[] dp = new int[n + 1];
 *           dp[0] = 0;
 *           dp[1] = 1;
 *
 *           // Fill the table bottom-up
 *           for (int i = 2; i <= n; i++) {
 *               dp[i] = dp[i - 1] + dp[i - 2];
 *           }
 *
 *           return dp[n];
 *       }
 *
 *       public static void main(String[] args) {
 *           FibonacciTabulation sol = new FibonacciTabulation();
 *           System.out.println(sol.fib(10)); // 55
 *       }
 *   }
 *
 * ------------------------------------------------------------
 * Approach 4: Space-Optimized Iteration (*** RECOMMENDED ***)
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Handle base cases n=0 and n=1 directly.
 *   2. Use two variables: prev (F(i-2)) and curr (F(i-1)).
 *   3. Each iteration: next = prev + curr, then prev=curr, curr=next.
 *   4. After n-1 iterations, curr holds F(n).
 *
 * Code:
 * ------------------------------------------------------------
 *
 *   public class FibonacciOptimal {
 *
 *       public int fib(int n) {
 *           if (n <= 1) return n;
 *
 *           int prev = 0; // Represents F(i-2), starts at F(0)
 *           int curr = 1; // Represents F(i-1), starts at F(1)
 *
 *           for (int i = 2; i <= n; i++) {
 *               int next = prev + curr; // F(i) = F(i-1) + F(i-2)
 *               prev = curr;            // Slide the window forward
 *               curr = next;
 *           }
 *
 *           return curr; // curr now holds F(n)
 *       }
 *
 *       public static void main(String[] args) {
 *           FibonacciOptimal sol = new FibonacciOptimal();
 *           System.out.println(sol.fib(0));  // 0
 *           System.out.println(sol.fib(1));  // 1
 *           System.out.println(sol.fib(10)); // 55
 *           System.out.println(sol.fib(30)); // 832040
 *       }
 *   }
 *
 * ------------------------------------------------------------
 * Approach 5: Matrix Exponentiation (O(log n))
 * ------------------------------------------------------------
 * Algorithm:
 *   The Fibonacci recurrence can be expressed as a matrix equation:
 *
 *     | F(n+1) |   | 1  1 |^n   | F(1) |
 *     | F(n)   | = | 1  0 |   * | F(0) |
 *
 *   Use fast matrix exponentiation (repeated squaring) to compute
 *   this in O(log n) multiplications.
 *
 * Code:
 * ------------------------------------------------------------
 *
 *   public class FibonacciMatrix {
 *
 *       // Multiply two 2x2 matrices
 *       private long[][] multiplyMatrix(long[][] A, long[][] B) {
 *           long[][] result = new long[2][2];
 *           result[0][0] = A[0][0] * B[0][0] + A[0][1] * B[1][0];
 *           result[0][1] = A[0][0] * B[0][1] + A[0][1] * B[1][1];
 *           result[1][0] = A[1][0] * B[0][0] + A[1][1] * B[1][0];
 *           result[1][1] = A[1][0] * B[0][1] + A[1][1] * B[1][1];
 *           return result;
 *       }
 *
 *       // Raise matrix M to the power p using fast exponentiation
 *       private long[][] matrixPow(long[][] M, int p) {
 *           // Identity matrix (base case)
 *           long[][] result = {{1, 0}, {0, 1}};
 *
 *           while (p > 0) {
 *               if ((p & 1) == 1) {         // If current bit is set
 *                   result = multiplyMatrix(result, M);
 *               }
 *               M = multiplyMatrix(M, M);   // Square the matrix
 *               p >>= 1;                     // Shift right (divide by 2)
 *           }
 *           return result;
 *       }
 *
 *       public int fib(int n) {
 *           if (n <= 1) return n;
 *
 *           long[][] baseMatrix = {{1, 1}, {1, 0}};
 *           long[][] resultMatrix = matrixPow(baseMatrix, n);
 *
 *           // F(n) is at position [0][1] or [1][0] after M^n
 *           return (int) resultMatrix[0][1];
 *       }
 *
 *       public static void main(String[] args) {
 *           FibonacciMatrix sol = new FibonacciMatrix();
 *           System.out.println(sol.fib(10)); // 55
 *           System.out.println(sol.fib(30)); // 832040
 *       }
 *   }
 *
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 *  Approach             | Time     | Reasoning                          | Space | Reasoning
 * ----------------------|----------|------------------------------------|-------|----------------------------
 *  Naive Recursion      | O(2^n)   | Binary call tree of height n       | O(n)  | Call stack depth = n
 *  Memoization          | O(n)     | Each value computed exactly once   | O(n)  | HashMap + call stack
 *  Tabulation           | O(n)     | Single loop from 2 to n            | O(n)  | Array of size n+1
 *  Space-Optimized      | O(n)     | Single loop from 2 to n            | O(1)  | Only 3 integer variables
 *  Matrix Exponentiation| O(log n) | Matrix squaring halves exponent    | O(1)  | Fixed 2x2 matrices only
 *
 * Concrete Example — n = 10:
 *   - Naive Recursion:          ~2^10 = 1024 calls
 *   - Memoization / Tabulation: exactly 10 unique computations
 *   - Space-Optimized:          exactly 9 loop iterations (i = 2..10)
 *   - Matrix Exponentiation:    ~log2(10) ≈ 4 matrix multiplications
 *
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 & 2: Recursion / Memoization — n = 5
 * ------------------------------------------------------------
 *
 * Call Tree (Naive Recursion):
 *
 *                      fib(5)
 *                    /        \
 *               fib(4)         fib(3)
 *              /     \         /    \
 *           fib(3)  fib(2)  fib(2)  fib(1)
 *           /   \    /  \    /  \
 *        fib(2) fib(1) fib(1) fib(0) fib(1) fib(0)
 *        /   \
 *     fib(1) fib(0)
 *
 *  ⚠ fib(3) computed TWICE, fib(2) computed THREE times — exponential blowup!
 *
 * With Memoization — Cache State:
 *
 *   Call fib(5) → not in cache
 *     Call fib(4) → not in cache
 *       Call fib(3) → not in cache
 *         Call fib(2) → not in cache
 *           fib(1)=1, fib(0)=0 → fib(2)=1, cache{2:1}
 *         Call fib(1) → returns 1
 *         fib(3) = 1+1 = 2, cache{2:1, 3:2}
 *       Call fib(2) → IN CACHE → returns 1
 *       fib(4) = 2+1 = 3, cache{2:1, 3:2, 4:3}
 *     Call fib(3) → IN CACHE → returns 2
 *     fib(5) = 3+2 = 5, cache{2:1, 3:2, 4:3, 5:5}
 *
 *   Output: 5 ✅
 *
 * ------------------------------------------------------------
 * Approach 3: Tabulation — n = 6
 * ------------------------------------------------------------
 *
 *  i    | dp[0] | dp[1] | dp[2] | dp[3] | dp[4] | dp[5] | dp[6]
 * ------|-------|-------|-------|-------|-------|-------|------
 *  init |   0   |   1   |   -   |   -   |   -   |   -   |   -
 *  i=2  |   0   |   1   |   1   |   -   |   -   |   -   |   -
 *  i=3  |   0   |   1   |   1   |   2   |   -   |   -   |   -
 *  i=4  |   0   |   1   |   1   |   2   |   3   |   -   |   -
 *  i=5  |   0   |   1   |   1   |   2   |   3   |   5   |   -
 *  i=6  |   0   |   1   |   1   |   2   |   3   |   5   |   8
 *
 *   Output: dp[6] = 8 ✅
 *
 * ------------------------------------------------------------
 * Approach 4: Space-Optimized — n = 7
 * ------------------------------------------------------------
 *
 *  Iteration (i) | prev (F(i-2)) | curr (F(i-1)) | next (F(i))
 * ---------------|---------------|---------------|---------------------
 *  Start         |      0        |      1        |  —
 *  i=2           |      0        |      1        |  1 → prev=1, curr=1
 *  i=3           |      1        |      1        |  2 → prev=1, curr=2
 *  i=4           |      1        |      2        |  3 → prev=2, curr=3
 *  i=5           |      2        |      3        |  5 → prev=3, curr=5
 *  i=6           |      3        |      5        |  8 → prev=5, curr=8
 *  i=7           |      5        |      8        | 13 → prev=8, curr=13
 *
 *   Output: curr = 13 = F(7) ✅
 *
 * ------------------------------------------------------------
 * Approach 5: Matrix Exponentiation — n = 6
 * ------------------------------------------------------------
 *
 *   Base matrix M = [[1,1],[1,0]]
 *
 *   M^6 using fast exponentiation:
 *     6 in binary = 110
 *     Step 1: bit=0, M^2 = [[2,1],[1,1]]
 *     Step 2: bit=1, result = M^2 = [[2,1],[1,1]], M^4 = [[5,3],[3,2]]
 *     Step 3: bit=1, result = result x M^4 = [[13,8],[8,5]]
 *
 *   result[0][1] = 8 = F(6) ✅
 *
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 *  Edge Case     | Expected Output | Risk / Notes
 * ---------------|-----------------|--------------------------------------------
 *  n = 0         |       0         | All approaches must check n <= 1 or n == 0
 *  n = 1         |       1         | Same base case guard
 *  n = 2         |       1         | First iteration of loop — must start correctly
 *  n = 30        |    832040       | Max LeetCode constraint — fits in int
 *  n = 46        |  1836311903     | Max int before overflow (~2.1B limit)
 *  n = 47        |  OVERFLOW int!  | Use long if n > 46
 *  Large n (>70) |       —         | Even long overflows at F(93); use BigInteger
 *
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * "What edge cases might this miss?"
 *   - Naive recursion and space-optimized correctly handle n=0 and n=1
 *     via the `if (n <= 1) return n` guard.
 *   - Matrix exponentiation must also guard n=0 (returns 0 directly
 *     before calling matrixPow).
 *   - Integer overflow: for n > 46, int overflows.
 *     For the given constraint n <= 30, int is safe (F(30) = 832040).
 *
 * "Are there any type mismatches?"
 *   - All approaches use int return type — safe for n <= 30.
 *   - Matrix exponentiation uses long[][] internally to avoid intermediate
 *     overflow, then casts to int at the end — intentional and correct.
 *
 * "How can I verify this works right now?"
 *   Run this test harness against all implementations:
 *
 *     public class FibTest {
 *         public static void main(String[] args) {
 *             int[] expected = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 832040};
 *             int[] testN    = {0, 1, 2, 3, 4, 5, 6,  7,  8,  9, 10,     30};
 *
 *             FibonacciOptimal sol = new FibonacciOptimal();
 *             for (int i = 0; i < testN.length; i++) {
 *                 int result = sol.fib(testN[i]);
 *                 String status = result == expected[i] ? "PASS" : "FAIL";
 *                 System.out.printf("fib(%2d) = %7d | Expected: %7d | %s%n",
 *                     testN[i], result, expected[i], status);
 *             }
 *         }
 *     }
 *
 *   Expected output:
 *     fib( 0) =       0 | Expected:       0 | PASS
 *     fib( 1) =       1 | Expected:       1 | PASS
 *     fib( 2) =       1 | Expected:       1 | PASS
 *     fib( 3) =       2 | Expected:       2 | PASS
 *     ...
 *     fib(30) =  832040 | Expected:  832040 | PASS
 *
 *
 * ============================================================
 * 9. COMPANY APPEARANCES & FREQUENCY
 * ============================================================
 *
 *  Company          | Frequency          | Notes
 * ------------------|--------------------|----------------------------------------------
 *  Amazon           | ***** Very High    | Often asked as warm-up; DP follow-up expected
 *  Google           | ***** Very High    | Tests DP fundamentals; matrix expo follow-up
 *  Microsoft        | ****  High         | Standard recursion → DP progression
 *  Facebook/Meta    | ****  High         | Intro problem before harder DP
 *  Apple            | ***   Medium       | Appears in phone screens
 *  Goldman Sachs    | ***   Medium       | Follow-up: handle large n with BigInteger
 *  Adobe            | ***   Medium       | Recursion + space optimization expected
 *  Bloomberg        | **    Low-Medium   | Sometimes asked as a warm-up
 *  Uber             | **    Low-Medium   | Stepping stone to harder problems
 *
 *  Total LeetCode appearances (Problem #509):
 *    Reported 600+ times — one of the top 50 most frequently asked problems globally.
 *
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 *  Approach             | Time     | Space | Recommendation
 * ----------------------|----------|-------|-------------------------------
 *  Naive Recursion      | O(2^n)   | O(n)  | ❌ Never use
 *  Memoization          | O(n)     | O(n)  | ✅ Good for teaching DP
 *  Tabulation           | O(n)     | O(n)  | ✅ Good for clarity
 *  Space-Optimized      | O(n)     | O(1)  | ⭐ Best for interviews
 *  Matrix Exponentiation| O(log n) | O(1)  | ✅ Best for large n
 *
 * Key Takeaway:
 *   Fibonacci is the textbook example of Dynamic Programming.
 *   The critical insight is that naive recursion recomputes subproblems
 *   exponentially, and the fix — whether memoization, tabulation, or
 *   space-optimized iteration — reduces this to linear time. Always start
 *   with the space-optimized iterative solution in an interview, then
 *   mention matrix exponentiation as the O(log n) extension if asked.
 *
 * Pattern to remember:
 *   Whenever you see a recurrence of the form f(n) = f(n-1) + f(n-2)
 *   (or similar with constant lookback), you can almost always reduce it
 *   to O(1) space using a rolling variables technique.
 *
 * ============================================================
 */
// @formatter:on
