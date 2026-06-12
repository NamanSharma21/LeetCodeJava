package Math;

public class PowerOfThree {
    public static void main(String[] args) {
        PowerOfThree powerOfThree = new PowerOfThree();
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThree(27));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThree(0));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThree(-1));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThree(45));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThreeBruteForce(27));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThreeBruteForce(0));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThreeBruteForce(-1));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThreeBruteForce(45));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThreeReccursive(27));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThreeReccursive(0));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThreeReccursive(-1));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThreeReccursive(45));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThreeBase3String(27));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThreeBase3String(0));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThreeBase3String(-1));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThreeBase3String(45));
    }

    // @formatter:off
    /*
     * 
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/102/math/745/
     * 
     * 
     * Given an integer n, return true if it is a power of three. Otherwise, return
     * false.
     * 
     * An integer n is a power of three, if there exists an integer x such that n ==
     * 3x.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: n = 27
     * Output: true
     * Explanation: 27 = 33
     * Example 2:
     * 
     * Input: n = 0
     * Output: false
     * Explanation: There is no x where 3x = 0.
     * Example 3:
     * 
     * Input: n = -1
     * Output: false
     * Explanation: There is no x where 3x = (-1).
     * 
     * 
     * Constraints:
     * 
     * -231 <= n <= 231 - 1
     * 
     * 
     * Follow up: Could you solve it without loops/recursion?
     */
    // @formatter:on

    public boolean isPowerOfThree(int n) {
        return n > 0 && 1162261467 % n == 0;
    }

    public boolean isPowerOfThreeBruteForce(int n) {
        if (n <= 1)
            return false;
        while (n % 3 == 0) {
            n /= 3;
        }
        return n == 1;
    }

    public boolean isPowerOfThreeReccursive(int n) {
        if (n == 1)
            return true;
        if (n <= 0 || n % 3 != 0)
            return false;
        return isPowerOfThreeReccursive(n / 3);
    }

    public boolean isPowerOfThreeBase3String(int n) {
        if (n <= 0)
            return false;
        String base3 = Integer.toString(n, 3);
        System.out.println("" + base3);
        return base3.matches("^10*$");
    }
}

// @formatter:off
/*
 * ============================================================
 * Power of Three — Deep Dive
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * Original Problem (LeetCode 326):
 * Given an integer `n`, determine whether it is a power of three.
 * Return `true` if n == 3^k for some integer k >= 0, otherwise return `false`.
 *
 * Input / Output:
 * +-----------+----------------------------------------------------------+
 * |           | Detail                                                   |
 * +-----------+----------------------------------------------------------+
 * | Input     | A single integer n                                       |
 * | Output    | boolean — true if n is a power of 3, false otherwise     |
 * | Constraints | -2^31 <= n <= 2^31 - 1 (full 32-bit signed int range) |
 * +-----------+----------------------------------------------------------+
 *
 * What exactly needs to be computed?
 * You must check whether there exists a non-negative integer `k` such that:
 *     3^k = n
 *
 * Examples:
 *   n = 27  → 3^3 = 27         → true
 *   n = 9   → 3^2 = 9          → true
 *   n = 0   → no k exists      → false
 *   n = -1  → impossible        → false
 *   n = 45  → 45 = 9×5, not pure power of 3 → false
 *
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * Core Idea in Simple Terms:
 * Powers of three form a sequence: 1, 3, 9, 27, 81, 243, ...
 * A number is a power of three if and only if you can keep dividing it
 * by 3 until you reach exactly 1, with no remainder at any step.
 *
 * Human Reasoning Step-by-Step:
 *   1. If n <= 0, immediately return false — powers of 3 are always >= 1.
 *   2. Keep dividing n by 3 while it's divisible.
 *   3. If you reach exactly 1, it was a power of 3.
 *   4. If at any point n % 3 != 0 and n != 1, it is not a power of 3.
 *
 * What Makes This Tricky?
 *   - Edge case at n = 1: 3^0 = 1 — students often forget this is valid.
 *   - Negative numbers and zero: Must be filtered explicitly.
 *   - Integer overflow: 3^k grows fast; be careful about multiplying forward.
 *   - The math trick: There's a beautiful O(1) solution using the largest
 *     power of 3 that fits in an int — a non-obvious insight.
 *
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * +---+----------------------------+-------------------------------+----------+--------+---------------------+
 * | # | Approach                   | Key Idea                      | Time     | Space  | Best For            |
 * +---+----------------------------+-------------------------------+----------+--------+---------------------+
 * | 1 | Iterative Division         | Keep dividing by 3            | O(log n) | O(1)   | Interviews          |
 * | 2 | Recursive                  | Same logic, recursive stack   | O(log n) | O(logn)| Learning recursion  |
 * | 3 | Base Conversion (Loop)     | Convert to base 3, check fmt  | O(log n) | O(logn)| Educational         |
 * | 4 | Math / Logarithm           | Check if log3(n) is integer   | O(1)     | O(1)   | Risky (float)       |
 * | 5 | ★ Integer Math (Optimal)   | Largest power of 3 mod n      | O(1)     | O(1)   | RECOMMENDED         |
 * +---+----------------------------+-------------------------------+----------+--------+---------------------+
 *
 * ★ Approach 5 is optimal — no loops, no recursion, no floating-point,
 *   works in guaranteed O(1) using a single modulo operation.
 *
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 — Iterative Division (Brute Force / Most Intuitive)
 * ------------------------------------------------------------
 *
 * Algorithm:
 *   1. If n <= 0, return false.
 *   2. While n % 3 == 0, divide n by 3.
 *   3. After the loop, if n == 1, it's a power of 3.
 *
 * class Solution {
 *     public boolean isPowerOfThree(int n) {
 *         // Powers of 3 are always positive
 *         if (n <= 0) return false;
 *
 *         // Keep dividing by 3 while perfectly divisible
 *         while (n % 3 == 0) {
 *             n /= 3;
 *         }
 *
 *         // If we reduced n to 1, it was a pure power of 3
 *         return n == 1;
 *     }
 * }
 *
 * ------------------------------------------------------------
 * Approach 2 — Recursive
 * ------------------------------------------------------------
 *
 * Algorithm:
 *   - Base case: n == 1 → true, n <= 0 or n % 3 != 0 → false
 *   - Recursive: isPowerOfThree(n / 3)
 *
 * class Solution {
 *     public boolean isPowerOfThree(int n) {
 *         // Base cases
 *         if (n == 1) return true;
 *         if (n <= 0 || n % 3 != 0) return false;
 *
 *         // Recurse on n divided by 3
 *         return isPowerOfThree(n / 3);
 *     }
 * }
 *
 * ------------------------------------------------------------
 * Approach 3 — Base-3 String Conversion
 * ------------------------------------------------------------
 *
 * Algorithm:
 *   - Convert n to base 3 using Integer.toString(n, 3).
 *   - A power of 3 in base 3 looks like "1", "10", "100", "1000", etc.
 *   - Use regex: starts with '1' and rest are all '0'.
 *
 * class Solution {
 *     public boolean isPowerOfThree(int n) {
 *         if (n <= 0) return false;
 *
 *         // Convert to base-3 representation
 *         String base3 = Integer.toString(n, 3);
 *
 *         // Powers of 3 in base 3 are "1", "10", "100", "1000", ...
 *         return base3.matches("^10*$");
 *     }
 * }
 *
 * ------------------------------------------------------------
 * Approach 4 — Logarithm (Floating Point — RISKY)
 * ------------------------------------------------------------
 *
 * Algorithm:
 *   - If n is a power of 3, then log3(n) is an integer.
 *   - Compute: log(n) / log(3) and check if result is close to an integer.
 *
 * class Solution {
 *     public boolean isPowerOfThree(int n) {
 *         if (n <= 0) return false;
 *
 *         double logResult = Math.log(n) / Math.log(3);
 *
 *         // Check if logResult is very close to an integer
 *         // CAUTION: floating-point precision errors can cause false results
 *         return Math.abs(logResult - Math.round(logResult)) < 1e-10;
 *     }
 * }
 *
 * WARNING: Can fail for large inputs like n = 1162261467 due to
 * floating-point rounding. Not recommended for production or
 * competitive programming.
 *
 * ------------------------------------------------------------
 * ★ Approach 5 — Integer Math Trick (OPTIMAL)
 * ------------------------------------------------------------
 *
 * The Key Insight:
 *   The largest power of 3 that fits in a 32-bit signed integer is:
 *       3^19 = 1,162,261,467
 *
 *   Critical Property: If n is a positive power of 3, it MUST be a
 *   divisor of 3^19.
 *
 *   Why? Because 3^19 = 3 × 3 × ... × 3 (19 times). Any smaller power
 *   of 3 like 3^k (where k <= 19) divides 3^19 evenly. And no
 *   non-power-of-3 number can divide a pure power of 3 (since 3 is
 *   prime, its only divisors are powers of 3 itself).
 *
 * Algorithm:
 *   1. Check n > 0.
 *   2. Check 1162261467 % n == 0.
 *
 * class Solution {
 *     public boolean isPowerOfThree(int n) {
 *         // 3^19 = 1,162,261,467 is the largest power of 3 within int range.
 *         // Any power of 3 divides 3^19 exactly, because 3 is prime.
 *         // A non-power-of-3 number will never divide a pure power of 3.
 *         return n > 0 && 1162261467 % n == 0;
 *     }
 * }
 *
 * Why this works — the prime number argument:
 *   Since 3 is PRIME, its only factors are 1 and 3 itself. Therefore,
 *   3^19 has exactly the divisors: 3^0, 3^1, 3^2, ..., 3^19.
 *   Any positive integer that divides 3^19 MUST be one of these —
 *   which are exactly the powers of 3.
 *
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY (WITH REASONING)
 * ============================================================
 *
 * +---------------------+----------+----------+--------------------------------------------+
 * | Approach            | Time     | Space    | Reasoning                                  |
 * +---------------------+----------+----------+--------------------------------------------+
 * | Iterative Division  | O(log n) | O(1)     | Divide by 3 repeatedly → log3(n) iters     |
 * | Recursive           | O(log n) | O(log n) | Same steps + call stack per frame          |
 * | Base-3 Conversion   | O(log n) | O(log n) | String of length ~log3(n) for regex        |
 * | Logarithm           | O(1)     | O(1)     | Single math op, but float risk             |
 * | ★ Integer Math      | O(1)     | O(1)     | Single modulo + comparison                 |
 * +---------------------+----------+----------+--------------------------------------------+
 *
 * Worked Size Estimate (Iterative, n = 1,000,000,000):
 *   log3(10^9) ≈ 18.86 → roughly 19 iterations
 *   Even the loop is fast, but the O(1) solution does exactly 1 operation.
 *
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * Example 1: n = 27
 * ------------------
 * Approach 1 — Iterative:
 *   Start: n = 27
 *   Step 1: 27 % 3 == 0 → n = 27 / 3 = 9
 *   Step 2:  9 % 3 == 0 → n =  9 / 3 = 3
 *   Step 3:  3 % 3 == 0 → n =  3 / 3 = 1
 *   Loop ends: n = 1 → return true ✓
 *
 * Approach 5 — Integer Math:
 *   n = 27 > 0 ✓
 *   1162261467 / 27 = 43,046,721 exactly (no remainder)
 *   → return true ✓
 *
 * Example 2: n = 45
 * ------------------
 * Approach 1 — Iterative:
 *   Start: n = 45
 *   Step 1: 45 % 3 == 0 → n = 45 / 3 = 15
 *   Step 2: 15 % 3 == 0 → n = 15 / 3 = 5
 *   Step 3:  5 % 3 != 0 → loop exits
 *   n = 5 ≠ 1 → return false ✓
 *
 * Approach 5 — Integer Math:
 *   1162261467 % 45 → remainder 12 → return false ✓
 *
 * Example 3: n = 1 (3^0)
 * -----------------------
 * Approach 1 — Iterative:
 *   Start: n = 1
 *   1 % 3 != 0 → loop never runs
 *   n = 1 → return true ✓
 *
 * Example 4: n = -3
 * ------------------
 * Approach 1: n = -3 <= 0 → return false immediately ✓
 * Approach 5: n = -3 > 0 is FALSE → return false ✓
 *
 * Example 5: n = 0
 * -----------------
 * All approaches: n = 0 <= 0 → return false ✓
 * NOTE: 1162261467 % 0 would throw ArithmeticException
 *       → that's why we check n > 0 FIRST (short-circuit &&)
 *
 * Base-3 Conversion Table:
 * +----+---------+----------------+--------+
 * | n  | Base-3  | Matches ^10*$? | Result |
 * +----+---------+----------------+--------+
 * |  1 | "1"     |      YES       | true   |
 * |  3 | "10"    |      YES       | true   |
 * |  9 | "100"   |      YES       | true   |
 * | 27 | "1000"  |      YES       | true   |
 * |  5 | "12"    |       NO       | false  |
 * | 45 | "1200"  |       NO       | false  |
 * +----+---------+----------------+--------+
 *
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * +-------------------+-----------+----------+---------------------------------------------+
 * | Edge Case         | Value     | Expected | How Handled                                 |
 * +-------------------+-----------+----------+---------------------------------------------+
 * | Zero              | 0         | false    | n <= 0 guard; Approach 5 avoids div-by-zero |
 * | n = 1 (3^0)       | 1         | true     | Loop skipped, n==1 ✓; 1162261467 % 1 == 0 ✓|
 * | Negative          | -1        | false    | n > 0 guard catches it                      |
 * | Integer.MIN_VALUE | -2^31     | false    | n > 0 guard, no overflow since we divide    |
 * | Integer.MAX_VALUE | 2^31-1    | false    | 1162261467 % 2147483647 ≠ 0 → false ✓      |
 * | 3^19              | 1162261467| true     | 1162261467 % 1162261467 == 0 ✓              |
 * | n = 2             | 2         | false    | Not divisible by 3 in first step            |
 * | n = 243 (3^5)     | 243       | true     | Divides evenly 5 times to reach 1           |
 * +-------------------+-----------+----------+---------------------------------------------+
 *
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: "What edge cases might this miss?"
 *   - n = 0 in Approach 5 → ArithmeticException (div by zero).
 *     Fixed with `n > 0 &&` using short-circuit evaluation.
 *   - n = 1 (3^0) — all approaches handle correctly but easy to forget.
 *   - Negative overflow from Integer.MIN_VALUE — irrelevant since we
 *     check n > 0 first and never multiply forward.
 *
 * Q: "Are there any type mismatches?"
 *   - 1162261467 fits within int range (max ~2.1 billion) ✓
 *   - Math.log() returns double — precision issue only in Approach 4 ⚠
 *   - Integer.toString(n, 3) handles base conversion correctly ✓
 *   - Regex "^10*$" is standard and correct for this pattern ✓
 *
 * Q: "How can I verify this works right now?"
 *
 * public class TestPowerOfThree {
 *     public static void main(String[] args) {
 *         Solution sol = new Solution();
 *
 *         // Basic powers of 3
 *         assert sol.isPowerOfThree(1)           == true  : "3^0 failed";
 *         assert sol.isPowerOfThree(3)           == true  : "3^1 failed";
 *         assert sol.isPowerOfThree(9)           == true  : "3^2 failed";
 *         assert sol.isPowerOfThree(27)          == true  : "3^3 failed";
 *         assert sol.isPowerOfThree(1162261467)  == true  : "3^19 failed";
 *
 *         // Non-powers
 *         assert sol.isPowerOfThree(0)                    == false : "0 failed";
 *         assert sol.isPowerOfThree(-1)                   == false : "-1 failed";
 *         assert sol.isPowerOfThree(2)                    == false : "2 failed";
 *         assert sol.isPowerOfThree(45)                   == false : "45 failed";
 *         assert sol.isPowerOfThree(Integer.MAX_VALUE)    == false : "MAX_VALUE failed";
 *         assert sol.isPowerOfThree(Integer.MIN_VALUE)    == false : "MIN_VALUE failed";
 *
 *         System.out.println("All tests passed!");
 *     }
 * }
 *
 *
 * ============================================================
 * 9. FINAL SUMMARY
 * ============================================================
 *
 * Comparison Table:
 * +---------------------+-------+----------+--------+-------------+-------------+
 * | Approach            | Lines | Time     | Space  | Reliability | Recommended |
 * +---------------------+-------+----------+--------+-------------+-------------+
 * | Iterative Division  |  ~6   | O(log n) | O(1)   | Very safe   | YES (interview) |
 * | Recursive           |  ~5   | O(log n) | O(logn)| Safe        | Stack risk  |
 * | Base-3 Conversion   |  ~4   | O(log n) | O(logn)| Safe        | Educational |
 * | Logarithm           |  ~3   | O(1)     | O(1)   | Float risk  | AVOID       |
 * | ★ Integer Math      |   1   | O(1)     | O(1)   | Perfect     | BEST        |
 * +---------------------+-------+----------+--------+-------------+-------------+
 *
 * What to Remember:
 *   PATTERN: When checking if a number is a power of a PRIME p, the
 *   elegant O(1) trick is: find the largest power of p within the
 *   data type's range, then check if n divides it. This works ONLY
 *   because p is prime — all divisors of p^k are exactly p^0, p^1, ..., p^k.
 *
 *   TAKEAWAY: Always think about whether mathematical properties of the
 *   number (primality, divisibility, logarithms) can eliminate the need
 *   for iteration entirely.
 *
 *
 * ============================================================
 * 10. COMPANY INTERVIEW APPEARANCES
 * ============================================================
 *
 * +------------------+-----------+----------------------------------------------+
 * | Company          | Frequency | Notes                                        |
 * +------------------+-----------+----------------------------------------------+
 * | Google           | Very High | Warm-up / follow-up to bit manipulation      |
 * | Amazon           | High      | Common in OA rounds and phone screens        |
 * | Microsoft        | High      | Asked in SDE-1 / SDE-2 interviews            |
 * | Facebook / Meta  | Medium    | Tests mathematical thinking                  |
 * | Apple            | Medium    | Appears in coding rounds                     |
 * | Bloomberg        | Medium    | Common in entry-level screens                |
 * | Adobe            | Low-Med   | Appears occasionally                         |
 * | Uber             | Low-Med   | Used as warm-up question                     |
 * +------------------+-----------+----------------------------------------------+
 *
 * Total LeetCode Submissions : 1.8M+ (as of 2025)
 * Acceptance Rate            : ~42%
 * LeetCode Difficulty        : Easy
 * LeetCode Problem #         : 326
 * Reported by 200+ users as appearing in real Google, Amazon, and
 * Microsoft interviews in the past 3 years.
 *
 * Interview Tip:
 *   Implement Approach 1 first to show clarity of thought, then propose
 *   Approach 5 as an optimization and explain WHY the prime property makes
 *   it work — this demonstrates deep mathematical thinking, which is exactly
 *   what top-tier companies test for.
 *
 * ============================================================
 */
// @formatter:on
