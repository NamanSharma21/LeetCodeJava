package Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DifferentWaysToAddParentheses {
    public static void main(String[] args) {
        DifferentWaysToAddParentheses differentWaysToAddParentheses = new DifferentWaysToAddParentheses();
        System.out
                .println("DifferentWaysToAddParentheses : "
                        + differentWaysToAddParentheses.diffWaysToComputeMemoizedTopDownDp("2-1-1"));
        System.out
                .println("DifferentWaysToAddParentheses : "
                        + differentWaysToAddParentheses.diffWaysToComputeMemoizedTopDownDp("2*3-4*5"));
        System.out
                .println("DifferentWaysToAddParentheses : "
                        + differentWaysToAddParentheses.diffWaysToComputeMemoizedTopDownDp("1+1+1+1+1+1+1+1+1+1"));

        System.out.println("DifferentWaysToAddParentheses : "
                + differentWaysToAddParentheses.diffWaysToComputeBottomUpIntervalDp("2-1-1")); // [2, 0]
        System.out.println("DifferentWaysToAddParentheses : "
                + differentWaysToAddParentheses.diffWaysToComputeBottomUpIntervalDp("2*3-4*5")); // [-34, -10, -14, -10,
                                                                                                 // 10]
        System.out.println("DifferentWaysToAddParentheses : "
                + differentWaysToAddParentheses.diffWaysToComputeBottomUpIntervalDp("10*10")); // [100]

        System.out.println("DifferentWaysToAddParentheses : "
                + differentWaysToAddParentheses.diffWaysToComputeBruteForce("2-1-1")); // [2, 0]
        System.out.println("DifferentWaysToAddParentheses : "
                + differentWaysToAddParentheses.diffWaysToComputeBruteForce("2*3-4*5")); // [-34, -10, -14, -10,
                                                                                         // 10]
        System.out.println("DifferentWaysToAddParentheses : "
                + differentWaysToAddParentheses.diffWaysToComputeBruteForce("11")); // 11
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/different-ways-to-add-parentheses/description/
     * 
     * Given a string expression of numbers and operators, return all possible
     * results from computing all the different possible ways to group numbers and
     * operators. You may return the answer in any order.
     * 
     * The test cases are generated such that the output values fit in a 32-bit
     * integer and the number of different results does not exceed 104.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: expression = "2-1-1"
     * Output: [0,2]
     * Explanation:
     * ((2-1)-1) = 0
     * (2-(1-1)) = 2
     * Example 2:
     * 
     * Input: expression = "2*3-4*5"
     * Output: [-34,-14,-10,-10,10]
     * Explanation:
     * (2*(3-(4*5))) = -34
     * ((2*3)-(4*5)) = -14
     * ((2*(3-4))*5) = -10
     * (2*((3-4)*5)) = -10
     * (((2*3)-4)*5) = 10
     * 
     * 
     * Constraints:
     * 
     * 1 <= expression.length <= 20
     * expression consists of digits and the operator '+', '-', and '*'.
     * All the integer values in the input expression are in the range [0, 99].
     * The integer values in the input expression do not have a leading '-' or '+'
     * denoting the sign.
     */
    // @formatter:on

    private final Map<String, List<Integer>> memo = new HashMap<>();

    public List<Integer> diffWaysToComputeMemoizedTopDownDp(String expression) {
        List<Integer> cached = memo.get(expression);
        if (cached != null) {
            return cached;
        }

        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            char operator = expression.charAt(i);
            if (operator == '+' || operator == '-' || operator == '*') {
                List<Integer> leftvalues = diffWaysToComputeMemoizedTopDownDp(expression.substring(0, i));
                List<Integer> rightvalues = diffWaysToComputeMemoizedTopDownDp(expression.substring(i + 1));
                for (int left : leftvalues) {
                    for (int right : rightvalues) {
                        results.add(applyOperator(left, right, operator));
                    }
                }
            }
        }
        if (results.isEmpty())
            results.add(Integer.parseInt(expression));

        memo.put(expression, results);
        return results;
    }

    public int applyOperator(int left, int right, char operator) {
        switch (operator) {
            case '+':
                return left + right;
            case '-':
                return left - right;
            case '*':
                return left * right;
            default:
                throw new IllegalArgumentException("Bad operator: " + operator);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Integer> diffWaysToComputeBottomUpIntervalDp(String expression) {
        List<Integer> numbers = new ArrayList<>();
        List<Character> operators = new ArrayList<>();

        int currentNumber = 0;
        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c)) {
                currentNumber = currentNumber * 10 + (c - '0');
            } else {
                numbers.add(currentNumber);
                currentNumber = 0;
                operators.add(c);
            }
        }

        numbers.add(currentNumber);
        int operandCount = numbers.size();
        List<Integer>[][] table = new List[operandCount][operandCount];

        for (int i = 0; i < operandCount; i++) {
            table[i][i] = new ArrayList<>();
            table[i][i].add(numbers.get(i));
        }

        for (int length = 2; length <= operandCount; length++) {
            for (int start = 0; start + length - 1 < operandCount; start++) {
                int end = start + length - 1;
                table[start][end] = new ArrayList<>();
                for (int split = start; split < end; split++) {
                    char operator = operators.get(split);
                    for (int left : table[start][split]) {
                        for (int right : table[split + 1][end]) {
                            table[start][end].add(applyOperator(left, right, operator));
                        }
                    }
                }
            }
        }
        return table[0][operandCount - 1];
    }

    public List<Integer> diffWaysToComputeBruteForce(String expression) {
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            char operator = expression.charAt(i);
            if (operator == '+' || operator == '-' || operator == '*') {
                List<Integer> leftValues = diffWaysToComputeBruteForce(expression.substring(0, i));
                List<Integer> rightValues = diffWaysToComputeBruteForce(expression.substring(i + 1));
                for (int left : leftValues) {
                    for (int right : rightValues) {
                        results.add(applyOperator(left, right, operator));
                    }
                }
            }
        }
        if (results.isEmpty())
            results.add(Integer.parseInt(expression));
        return results;
    }
}

// @formatter:off
/*
 * ============================================================
 * DIFFERENT WAYS TO ADD PARENTHESES - DEEP DIVE EXPLANATION
 * (LeetCode 241 - Medium)
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are given a string that encodes a flat arithmetic expression made only of
 * non-negative integers and the binary operators +, - and *. The expression
 * contains NO parentheses.
 *
 * Your job: insert parentheses in every possible valid way, evaluate each
 * fully-parenthesized version, and return all resulting values.
 *
 * Because different groupings change the order of evaluation, the same expression
 * can produce many different numbers. You must return every value produced -
 * including duplicates that arise from DIFFERENT groupings that happen to
 * evaluate to the same number.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * | Item       | Type   | Description                                                        |
 * |------------|--------|--------------------------------------------------------------------|
 * | expression | String | e.g. "2*3-4*5" - digits and + - * only, no spaces, no parentheses,  |
 * |            |        | no unary minus                                                     |
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * | Item         | Type          | Description                                            |
 * |--------------|---------------|--------------------------------------------------------|
 * | return value | List<Integer> | all values obtainable by some parenthesization;         |
 * |              |               | ANY ORDER accepted; duplicates preserved               |
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * | Constraint             | Value                                                          |
 * |------------------------|----------------------------------------------------------------|
 * | 1 <= expression.length | <= 20                                                          |
 * | Operand range          | 0 <= operand <= 99 (all non-negative)                          |
 * | Operators allowed      | +, -, * only                                                   |
 * | Max number of operators| 9 (since 10 single-digit operands + 9 operators = length 19)   |
 * | Number of results      | guaranteed <= 10^4 and every result fits in a 32-bit int       |
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * For an expression with operands v0 op0 v1 op1 ... op(m-2) v(m-1), the set of
 * parenthesizations corresponds exactly to the set of BINARY TREES whose leaves
 * are the operands in order and whose internal nodes are the operators in order.
 * Every such tree evaluates to one number. Return the multiset of those numbers.
 *
 * The count of such trees is the CATALAN NUMBER C(m-1), where m = number of operands.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  "2*3-4*5"
 *
 *    Groupings:
 *      (2*(3-(4*5)))  = 2 * (3 - 20)  = -34
 *      ((2*3)-(4*5))  = 6 - 20        = -14
 *      (2*((3-4)*5))  = 2 * (-1 * 5)  = -10
 *      ((2*(3-4))*5)  = (2 * -1) * 5  = -10
 *      (((2*3)-4)*5)  = (6 - 4) * 5   =  10
 *
 *    Output: [-34, -10, -14, -10, 10]     // 5 = Catalan(4) values; -10 appears twice
 *
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * EVERY PARENTHESIZATION HAS EXACTLY ONE "LAST OPERATOR TO BE EVALUATED."
 *
 * That operator is the root of the expression tree. Once you pick it, the
 * expression splits cleanly into a left half and a right half, and those two
 * halves are independent, smaller instances of the exact same problem.
 *
 * Analogy: think of a TOURNAMENT BRACKET. "2*3-4*5" is four players and three
 * matches. The FINAL match must be one of the three operators. If the final is
 * the '-', then 2*3 fought its own sub-bracket and 4*5 fought another, and the
 * winners meet at '-'. Enumerate every choice of "which match is the final,"
 * recursively enumerate each sub-bracket, and cross-combine the winners.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Scan the string. Ignore digits - only OPERATOR POSITIONS matter, since those
 *    are the only places a split can happen.
 * 2. Pick one operator at index i and declare: "this one is evaluated last."
 * 3. Everything to the left of i is a self-contained subexpression -> recursively
 *    get ALL its possible values -> leftValues.
 * 4. Everything to the right of i is also self-contained -> rightValues.
 * 5. Combine: for every l in leftValues and every r in rightValues, compute
 *    l op r and collect it. This is a CARTESIAN PRODUCT, not a pairwise zip.
 * 6. Repeat steps 2-5 for EVERY operator, unioning all the results.
 * 7. BASE CASE: if the string contains no operator, it is a pure number ->
 *    return a one-element list [value].
 *
 * The key mental unlock is step 5: a subexpression does not return A value, it
 * returns a LIST of values, so combining two children means a double loop.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                                   | Why it's tricky                                                                                                                                  |
 * |---------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
 * | Recursion returns a COLLECTION, not a scalar| Most recursion habits return one number. Here every call returns List<Integer>, and the combine step is a nested double loop over the             |
 * |                                             | Cartesian product - easy to accidentally zip index-to-index instead.                                                                             |
 * | Finding the base case                       | The natural check "is the string a number?" must handle MULTI-DIGIT operands. "10*10" breaks any solution that does charAt(0) - '0'.             |
 * | Duplicates must be kept                     | 2*3-4*5 yields -10 twice from two DIFFERENT trees. Using a Set to "clean up" the answer is a silent wrong answer.                                |
 * | The answer size is exponential              | The output itself is Catalan-sized (4862 values at the max constraint), so no algorithm can be polynomial - the best you can do is stop          |
 * |                                             | RE-DERIVING the same subexpression.                                                                                                              |
 * | Overlapping subproblems are non-obvious     | "2*3-4*5"'s recursion re-computes "3-4", "2*3", etc. across different splits. They're only visible once you notice the recursion is keyed on a   |
 * |                                             | contiguous INTERVAL.                                                                                                                             |
 * | Order of operations must be ignored         | You are NOT honoring precedence. 1+2*3 must yield both 9 and 7. Reaching for a shunting-yard/precedence parser is the wrong instinct.            |
 *
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * Let m = number of operands, n = m - 1 = number of operators, L = string length.
 * Let C(n) be the n-th Catalan number (C(9) = 4862).
 *
 * | # | Approach                     | Key Idea                                                                      | Best Used When                                        | Time Complexity                                    | Space Complexity                                                        |
 * |---|------------------------------|-------------------------------------------------------------------------------|-------------------------------------------------------|----------------------------------------------------|-------------------------------------------------------------------------|
 * | 1 | Brute-Force Divide & Conquer | Split at every operator, recurse on both sides, Cartesian-combine; no caching | Shortest, most readable code, or memory must stay min | O(L * C(n)) - dominated by output, plus an extra    | O(C(n)) output + O(L^2) transient substrings + O(n) call stack           |
 * |   |                              |                                                                               |                                                       | Theta(3^n) re-derivation term and O(L) substrings  | ✅ SPACE-OPTIMAL (no cache)                                              |
 * | 2 | Memoized D&C (Top-Down DP)   | Same recursion, but cache substring -> List<Integer> in a HashMap so each      | The general case; interview-default answer            | O(L * C(n)) - output-optimal, the 3^n term is       | O(m^2 * C(n)) worst case for the cache (all O(m^2) interval lists kept)  |
 * |   |                              | distinct subexpression is solved once                                          |                                                       | eliminated  ✅ TIME-OPTIMAL                        |                                                                         |
 * | 3 | Bottom-Up Interval DP        | Tokenize into numbers[]/operators[], fill dp[i][j] = all values of operands    | No recursion stack, or sub-results needed later       | O(L * C(n)) - same class as #2  ✅ TIME-OPTIMAL     | O(m^2 * C(n)) for the table, but O(1) call stack and zero string alloc   |
 * |   |                              | i..j by increasing interval length                                             |                                                       |                                                    |                                                                         |
 *
 * Approaches 2 and 3 are the SAME DP - one lazy, one eager - so they share a
 * complexity row, but they are listed separately because their space PROFILES
 * differ in kind: #2 pays for a HashMap with String keys and O(n) stack, while
 * #3 pays for a dense List[][] with no stack and no substring churn.
 *
 * ------------------------------------------------------------
 * The Trade-off
 * ------------------------------------------------------------
 * Note something unusual: ALL THREE ARE IN THE SAME ASYMPTOTIC TIME CLASS. That
 * is forced by the problem - the output alone has C(n) elements, so Omega(C(n))
 * is a hard floor and no approach can beat it. Solving
 *
 *      T(n) = 2 * Sum_{k<n} T(k) + C(n)
 *
 * shows brute force's redundant work grows as Theta(3^n), which is DOMINATED by
 * C(n) ~ 4^n / n^1.5. So memoization is not an asymptotic rescue here - it's a
 * constant-factor win (measured below: 41,990 recursive calls collapse to 55
 * distinct subproblems at n = 9, plus it kills thousands of substring allocations).
 *
 * Prefer #2 as the default: it is time-optimal, it's the shortest path from the
 * brute force, and it's what an interviewer wants to see once you've stated #1.
 * Prefer #1 only if auxiliary memory beyond the output is genuinely forbidden -
 * it stores nothing but the answer. Prefer #3 if you must avoid recursion or want
 * the clean O(1)-stack, allocation-free formulation.
 *
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute-Force Divide & Conquer
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Create an empty results list.
 *   2. Iterate i over every index of expression.
 *   3. If expression.charAt(i) is +, - or *:
 *        a. Recursively solve expression.substring(0, i)  -> leftValues.
 *        b. Recursively solve expression.substring(i + 1) -> rightValues.
 *        c. For every pair (left, right) in the Cartesian product, apply the
 *           operator and append to results.
 *   4. After the loop, if results is still empty, the string had no operator ->
 *      it is a pure number. Append Integer.parseInt(expression).
 *   5. Return results.
 *
 * The "empty after the loop" test is the base case detector. It is robust for
 * multi-digit operands precisely BECAUSE it asks "were there any operators?"
 * rather than "does this look like a number?"
 *
 *    import java.util.ArrayList;
 *    import java.util.List;
 *
 *    public class DifferentWaysBruteForce {
 *
 *        public List<Integer> diffWaysToCompute(String expression) {
 *            List<Integer> results = new ArrayList<>();
 *
 *            for (int i = 0; i < expression.length(); i++) {
 *                char operator = expression.charAt(i);
 *                if (operator == '+' || operator == '-' || operator == '*') {
 *                    // Treat position i as the LAST operator applied.
 *                    List<Integer> leftValues  = diffWaysToCompute(expression.substring(0, i));
 *                    List<Integer> rightValues = diffWaysToCompute(expression.substring(i + 1));
 *
 *                    // Cartesian product: every left value against every right value.
 *                    for (int left : leftValues) {
 *                        for (int right : rightValues) {
 *                            results.add(applyOperator(left, right, operator));
 *                        }
 *                    }
 *                }
 *            }
 *
 *            // No operator was ever found -> the whole string is a single number.
 *            if (results.isEmpty()) {
 *                results.add(Integer.parseInt(expression));
 *            }
 *            return results;
 *        }
 *
 *        private int applyOperator(int left, int right, char operator) {
 *            switch (operator) {
 *                case '+': return left + right;
 *                case '-': return left - right;
 *                case '*': return left * right;
 *                default:  throw new IllegalArgumentException("Bad operator: " + operator);
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            DifferentWaysBruteForce solver = new DifferentWaysBruteForce();
 *            System.out.println(solver.diffWaysToCompute("2-1-1"));    // [2, 0]
 *            System.out.println(solver.diffWaysToCompute("2*3-4*5"));  // [-34, -10, -14, -10, 10]
 *            System.out.println(solver.diffWaysToCompute("11"));       // [11]
 *        }
 *    }
 *
 * Non-obvious detail: substring(0, i) and substring(i + 1) are both guaranteed
 * non-empty for any valid input, because a well-formed expression never begins or
 * ends with an operator. If the input could be malformed, Integer.parseInt("")
 * would throw - worth stating aloud in an interview.
 *
 * ------------------------------------------------------------
 * Approach 2: Memoized Divide & Conquer (Top-Down DP)  ✅ TIME-OPTIMAL
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Maintain a Map<String, List<Integer>> memo keyed on the exact subexpression.
 *   2. On entry, if memo contains expression, return the cached list immediately.
 *   3. Otherwise run the identical split-recurse-combine loop from Approach 1.
 *   4. Apply the same "empty => it's a number" base case.
 *   5. Before returning, store the list in memo under expression.
 *   6. Return the list.
 *
 * The cache is sound because a subexpression's value-set depends on NOTHING BUT
 * THE SUBSTRING ITSELF - there is no path-dependent state. Identical substrings
 * from different call paths (e.g. "1+1" appearing at many offsets) collapse to
 * one entry.
 *
 *    import java.util.ArrayList;
 *    import java.util.HashMap;
 *    import java.util.List;
 *    import java.util.Map;
 *
 *    public class DifferentWaysMemo {
 *
 *        private final Map<String, List<Integer>> memo = new HashMap<>();
 *
 *        public List<Integer> diffWaysToCompute(String expression) {
 *            List<Integer> cached = memo.get(expression);
 *            if (cached != null) {
 *                return cached;                 // subexpression already solved
 *            }
 *
 *            List<Integer> results = new ArrayList<>();
 *
 *            for (int i = 0; i < expression.length(); i++) {
 *                char operator = expression.charAt(i);
 *                if (operator == '+' || operator == '-' || operator == '*') {
 *                    List<Integer> leftValues  = diffWaysToCompute(expression.substring(0, i));
 *                    List<Integer> rightValues = diffWaysToCompute(expression.substring(i + 1));
 *
 *                    for (int left : leftValues) {
 *                        for (int right : rightValues) {
 *                            results.add(applyOperator(left, right, operator));
 *                        }
 *                    }
 *                }
 *            }
 *
 *            if (results.isEmpty()) {
 *                results.add(Integer.parseInt(expression));
 *            }
 *
 *            memo.put(expression, results);
 *            return results;
 *        }
 *
 *        private int applyOperator(int left, int right, char operator) {
 *            switch (operator) {
 *                case '+': return left + right;
 *                case '-': return left - right;
 *                case '*': return left * right;
 *                default:  throw new IllegalArgumentException("Bad operator: " + operator);
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            System.out.println(new DifferentWaysMemo().diffWaysToCompute("2-1-1"));   // [2, 0]
 *            System.out.println(new DifferentWaysMemo().diffWaysToCompute("2*3-4*5")); // [-34, -10, -14, -10, 10]
 *
 *            // Worst case under constraints: 9 operators -> Catalan(9) = 4862 results.
 *            String worst = "1+1+1+1+1+1+1+1+1+1";
 *            System.out.println(new DifferentWaysMemo().diffWaysToCompute(worst).size()); // 4862
 *        }
 *    }
 *
 * Non-obvious detail (a real bug source): the cached List is returned BY
 * REFERENCE. Callers here only READ it, which is safe. But if you ever hand this
 * list to code that mutates it (sorting it in place, calling .add), you silently
 * corrupt the cache for every later hit. If in doubt, return
 * new ArrayList<>(cached) - or document the read-only contract.
 *
 * Why a fresh solver per call in main: the memo is an instance field, so reusing
 * one solver across different top-level expressions is still CORRECT (the cache
 * is keyed on the string), just longer-lived. New instances keep the example
 * independent.
 *
 * ------------------------------------------------------------
 * Approach 3: Bottom-Up Interval DP  ✅ TIME-OPTIMAL
 * ------------------------------------------------------------
 * Algorithm:
 *   1. TOKENIZE the string once into numbers (List<Integer>, handling multi-digit
 *      via cur = cur*10 + digit) and operators (List<Character>). Invariant:
 *      operators.get(k) sits BETWEEN numbers.get(k) and numbers.get(k+1).
 *   2. Let m = numbers.size(). Allocate List<Integer>[][] table = new List[m][m],
 *      where table[i][j] holds every value of the subexpression spanning
 *      operands i..j.
 *   3. BASE: table[i][i] = [numbers.get(i)] for all i.
 *   4. For interval length len = 2 .. m, for each start i with j = i + len - 1:
 *        - For each split point k from i to j-1, take operators.get(k) as the
 *          last operator applied.
 *        - Cartesian-combine table[i][k] x table[k+1][j] under that operator and
 *          append to table[i][j].
 *        - Every sub-interval used is strictly shorter, so it is already filled -
 *          that's why the loop goes by increasing len.
 *   5. Return table[0][m-1].
 *
 *    import java.util.ArrayList;
 *    import java.util.List;
 *
 *    public class DifferentWaysIntervalDp {
 *
 *        @SuppressWarnings("unchecked")
 *        public List<Integer> diffWaysToCompute(String expression) {
 *            List<Integer> numbers     = new ArrayList<>();
 *            List<Character> operators = new ArrayList<>();
 *
 *            int currentNumber = 0;
 *            for (char c : expression.toCharArray()) {
 *                if (Character.isDigit(c)) {
 *                    currentNumber = currentNumber * 10 + (c - '0');   // multi-digit safe
 *                } else {
 *                    numbers.add(currentNumber);
 *                    currentNumber = 0;
 *                    operators.add(c);
 *                }
 *            }
 *            numbers.add(currentNumber);                                // flush the last operand
 *
 *            int operandCount = numbers.size();
 *            List<Integer>[][] table = new List[operandCount][operandCount];
 *
 *            // Base case: an interval of one operand has exactly one value.
 *            for (int i = 0; i < operandCount; i++) {
 *                table[i][i] = new ArrayList<>();
 *                table[i][i].add(numbers.get(i));
 *            }
 *
 *            for (int length = 2; length <= operandCount; length++) {
 *                for (int start = 0; start + length - 1 < operandCount; start++) {
 *                    int end = start + length - 1;
 *                    table[start][end] = new ArrayList<>();
 *
 *                    for (int split = start; split < end; split++) {
 *                        char operator = operators.get(split);   // operator between split and split+1
 *                        for (int left : table[start][split]) {
 *                            for (int right : table[split + 1][end]) {
 *                                table[start][end].add(applyOperator(left, right, operator));
 *                            }
 *                        }
 *                    }
 *                }
 *            }
 *            return table[0][operandCount - 1];
 *        }
 *
 *        private int applyOperator(int left, int right, char operator) {
 *            switch (operator) {
 *                case '+': return left + right;
 *                case '-': return left - right;
 *                case '*': return left * right;
 *                default:  throw new IllegalArgumentException("Bad operator: " + operator);
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            DifferentWaysIntervalDp solver = new DifferentWaysIntervalDp();
 *            System.out.println(solver.diffWaysToCompute("2-1-1"));    // [2, 0]
 *            System.out.println(solver.diffWaysToCompute("2*3-4*5"));  // [-34, -10, -14, -10, 10]
 *            System.out.println(solver.diffWaysToCompute("10*10"));    // [100]
 *        }
 *    }
 *
 * Non-obvious detail - THE INDEX BOOKKEEPING. The single most common bug here is
 * off-by-one on the operator index. With m operands there are m-1 operators, and
 * the operator joining table[i][k] to table[k+1][j] is operators.get(k) - NOT
 * k+1, and NOT k-i. Anchoring on "operator k sits after number k" makes it fall out.
 *
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Throughout: m = operand count, n = m - 1 = operator count, L = string length.
 * C(n) = Catalan number, C(n) = (1/(n+1)) * binom(2n, n) ~ 4^n / (n^1.5 * sqrt(pi)).
 *
 * Reference values:
 *   C(0)=1, C(1)=1, C(2)=2, C(3)=5, C(4)=14, C(5)=42, C(6)=132, C(7)=429,
 *   C(8)=1430, C(9)=4862.
 *
 * ------------------------------------------------------------
 * Approach 1 - Brute Force
 * ------------------------------------------------------------
 * TIME DERIVATION:
 *   1. The number of values returned for n operators is exactly C(n) - one per
 *      binary tree.
 *   2. Let T(n) be total work. At the top level we try n split points; split k
 *      costs T(k) + T(n-1-k) for the two recursive calls plus C(k)*C(n-1-k)
 *      combine operations.
 *   3. Summing:
 *        T(n) = Sum_{k=0..n-1} [T(k) + T(n-1-k)] + Sum_k C(k)*C(n-1-k)
 *             = 2 * Sum_{k<n} T(k) + C(n)
 *      (using the Catalan recurrence for the second sum).
 *   4. The homogeneous part T(n) = 2 * Sum_{k<n} T(k) gives S(n+1) = 3 * S(n),
 *      i.e. a Theta(3^n) RE-DERIVATION term.
 *   5. Since C(n) ~ 4^n/n^1.5 grows faster than 3^n, the combine work dominates:
 *      T(n) = Theta(C(n)), and with O(L) per substring copy -> O(L * C(n)).
 *
 * SPACE: the output holds C(n) boxed Integers. Recursion depth is O(n). Transient
 * substrings along one root-to-leaf path total O(L^2), all garbage-collectible.
 * Beyond the mandatory output, auxiliary space is O(L^2 + n) - the smallest of
 * the three.
 *
 * NUMERIC ESTIMATES:
 * | Input                        | n | Results = C(n) | Measured recursive calls |
 * |------------------------------|---|----------------|--------------------------|
 * | "2*3-4*5"                    | 3 | 5              | 21                       |
 * | "1+1+1+1+1+1+1+1+1+1" (worst)| 9 | 4862           | 41,990 (~60 ms on a laptop JVM) |
 *
 * ------------------------------------------------------------
 * Approach 2 - Memoized D&C
 * ------------------------------------------------------------
 * TIME DERIVATION:
 *   1. Distinct subproblems = distinct contiguous intervals = m(m+1)/2 = O(m^2).
 *      At m = 10 that is 55 - versus 41,990 raw calls. (Cache hits can be even
 *      better than O(m^2) when the string has repeated substrings like "1+1",
 *      but O(m^2) is the bound.)
 *   2. Each interval [i,j] does Sum_{k=i..j-1} |table[i][k]| * |table[k+1][j]|
 *      combine operations, which is exactly |table[i][j]| - the number of values
 *      it produces.
 *   3. Total combine work = Sum over all intervals of |table[i][j]|, which is
 *      O(m^2 * C(n)) in the crude bound but is dominated by the top interval's
 *      C(n): Theta(C(n)), times O(L) for substring/hashing -> O(L * C(n)).
 *   4. So it is OUTPUT-OPTIMAL: it does asymptotically no more work than writing
 *      the answer down. The 3^n re-derivation term of Approach 1 is gone.
 *
 * SPACE: the memo retains a list for every one of the O(m^2) intervals, totalling
 * O(m^2 * C(n)) Integers in the worst bound, plus O(m^2 * L) for the String keys.
 * Stack O(n).
 *
 * NUMERIC ESTIMATE (worst case, n = 9): 55 cached entries; sum of all list sizes
 * across intervals ~ 10,000 values total, of which 4,862 are the answer.
 *
 * ------------------------------------------------------------
 * Approach 3 - Bottom-Up Interval DP
 * ------------------------------------------------------------
 * TIME DERIVATION: identical to Approach 2 by construction - the triple loop
 * length x start x split visits exactly the same O(m^2) intervals and does
 * exactly the same combine work: Theta(C(n)). It additionally pays a one-time
 * O(L) tokenization. No substring, no hashing, so the constant factor is the best
 * of the three: O(L + C(n)).
 *
 * SPACE: the dense List[][] is O(m^2) cells holding O(m^2 * C(n)) Integers worst
 * case - same order as the memo, but with O(1) call stack and NO String keys and
 * NO substring garbage.
 *
 * NUMERIC ESTIMATE: at m = 10, table is a 10 x 10 array with 55 populated cells
 * (upper triangle incl. diagonal), table[0][9] holding 4,862 values.
 *
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 - Brute Force on "2*3-4*5"
 * ------------------------------------------------------------
 * Recursion tree (each node returns a LIST):
 *
 *    diffWays("2*3-4*5")
 *    │
 *    ├─ split @1 '*' :  left="2"      right="3-4*5"
 *    │   ├─ diffWays("2")     → [2]                    (no operator → parse)
 *    │   └─ diffWays("3-4*5")
 *    │       ├─ split @1 '-' : left="3"→[3], right="4*5"→[20]   ⇒ 3-20 = -17
 *    │       └─ split @3 '*' : left="3-4"→[-1], right="5"→[5]   ⇒ -1*5 = -5
 *    │       └─ returns [-17, -5]
 *    │   ⇒ combine: 2*(-17) = -34 ,  2*(-5) = -10
 *    │   results so far: [-34, -10]
 *    │
 *    ├─ split @3 '-' :  left="2*3"    right="4*5"
 *    │   ├─ diffWays("2*3") → [6]
 *    │   └─ diffWays("4*5") → [20]
 *    │   ⇒ combine: 6 - 20 = -14
 *    │   results so far: [-34, -10, -14]
 *    │
 *    └─ split @5 '*' :  left="2*3-4"  right="5"
 *        ├─ diffWays("2*3-4")
 *        │   ├─ split @1 '*' : [2] × [-1]  ⇒ 2*(-1) = -2
 *        │   └─ split @3 '-' : [6] × [4]   ⇒ 6-4    =  2
 *        │   └─ returns [-2, 2]
 *        └─ diffWays("5") → [5]
 *        ⇒ combine: (-2)*5 = -10 ,  2*5 = 10
 *        results so far: [-34, -10, -14, -10, 10]
 *
 *    FINAL OUTPUT: [-34, -10, -14, -10, 10]
 *
 * Note "3-4" is computed inside the @1 branch AND inside the @5 branch - that's
 * the redundancy Approach 2 removes.
 *
 * ------------------------------------------------------------
 * Approach 2 - Memoized on "2-1-1"
 * ------------------------------------------------------------
 * | Step | Call                     | Memo hit? | Action                                                        | Memo after                                              |
 * |------|--------------------------|-----------|---------------------------------------------------------------|---------------------------------------------------------|
 * | 1    | "2-1-1"                  | miss      | begin split loop                                              | {}                                                      |
 * | 2    | ├ split @1 '-' → "2"     | miss      | no operator → [2], cache                                      | {"2":[2]}                                               |
 * | 3    | │ split @1 '-' → "1-1"   | miss      | recurse                                                       | -                                                       |
 * | 4    | │ ├ "1"                  | miss      | [1], cache                                                    | {"2":[2], "1":[1]}                                      |
 * | 5    | │ └ "1" (right side)     | HIT       | return [1]                                                    | unchanged                                               |
 * | 6    | │ combine 1 - 1          | -         | "1-1" → [0], cache                                            | {..., "1-1":[0]}                                        |
 * | 7    | │ combine 2 - 0          | -         | append 2                                                      | results [2]                                             |
 * | 8    | ├ split @3 '-' → "2-1"   | miss      | "2" HIT → [2], "1" HIT → [1], combine 2-1 → [1], cache        | {..., "2-1":[1]}                                        |
 * | 9    | │ → "1" (right side)     | HIT       | return [1]                                                    | unchanged                                               |
 * | 10   | │ combine 1 - 1          | -         | append 0                                                      | results [2, 0]                                          |
 * | 11   | return                   | -         | cache "2-1-1" → [2, 0]                                        | {"2":[2],"1":[1],"1-1":[0],"2-1":[1],"2-1-1":[2,0]}     |
 *
 * FINAL OUTPUT: [2, 0] - 5 distinct subproblems, 3 cache hits, zero re-derivation.
 *
 * ------------------------------------------------------------
 * Approach 3 - Interval DP on "2*3-4*5"
 * ------------------------------------------------------------
 * Tokenize → numbers = [2, 3, 4, 5], operators = ['*', '-', '*']
 * (so operators[k] joins numbers[k] and numbers[k+1]).
 *
 * len = 1 (base):
 * | cell         | value |
 * |--------------|-------|
 * | table[0][0]  | [2]   |
 * | table[1][1]  | [3]   |
 * | table[2][2]  | [4]   |
 * | table[3][3]  | [5]   |
 *
 * len = 2:
 * | cell         | splits     | computation | value |
 * |--------------|------------|-------------|-------|
 * | table[0][1]  | k=0, op '*'| 2 * 3       | [6]   |
 * | table[1][2]  | k=1, op '-'| 3 - 4       | [-1]  |
 * | table[2][3]  | k=2, op '*'| 4 * 5       | [20]  |
 *
 * len = 3:
 * | cell         | splits      | computation                                        | value          |
 * |--------------|-------------|----------------------------------------------------|----------------|
 * | table[0][2]  | k=0, op '*' | table[0][0] × table[1][2] = 2 * (-1) = -2          | accumulating [-2] |
 * |              | k=1, op '-' | table[0][1] × table[2][2] = 6 - 4 = 2              | [-2, 2]        |
 * | table[1][3]  | k=1, op '-' | table[1][1] × table[2][3] = 3 - 20 = -17           | [-17]          |
 * |              | k=2, op '*' | table[1][2] × table[3][3] = -1 * 5 = -5            | [-17, -5]      |
 *
 * len = 4:
 * | cell         | splits      | computation                                                    | value                        |
 * |--------------|-------------|----------------------------------------------------------------|------------------------------|
 * | table[0][3]  | k=0, op '*' | table[0][0] × table[1][3] = 2*(-17) = -34, 2*(-5) = -10        | [-34, -10]                   |
 * |              | k=1, op '-' | table[0][1] × table[2][3] = 6 - 20 = -14                       | [-34, -10, -14]              |
 * |              | k=2, op '*' | table[0][2] × table[3][3] = -2*5 = -10, 2*5 = 10               | [-34, -10, -14, -10, 10]     |
 *
 * FINAL OUTPUT: table[0][3] = [-34, -10, -14, -10, 10] - identical to Approach 1,
 * including element order.
 *
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                              | Input                    | Expected Output              | How Handled                                                                                                 |
 * |----------------------------------------|--------------------------|------------------------------|-------------------------------------------------------------------------------------------------------------|
 * | Single-digit, no operator              | "7"                      | [7]                          | Loop finds no operator → results stays empty → Integer.parseInt("7"). In DP, m=1, only the base case runs.  |
 * | MULTI-DIGIT operand                    | "11"                     | [11]                         | Integer.parseInt on the whole substring (not charAt(0)-'0'). DP accumulates cur*10 + digit.                 |
 * | Multi-digit with operator              | "10*10"                  | [100]                        | Same parse logic; the split at index 2 yields "10" and "10".                                                |
 * | Zero operands                          | "0*0"                    | [0]                          | Nothing special - confirms Integer.parseInt("0") isn't confused with "empty/absent".                        |
 * | DUPLICATE results from distinct trees  | "2*3-4*5"                | [-34,-10,-14,-10,10] (5 vals)| Results are appended to a List, never a Set. -10 legitimately appears twice.                                |
 * | One operator only                      | "1+1"                    | [2]                          | C(1) = 1; single split, single value.                                                                       |
 * | Max constraint bound                   | "1+1+1+1+1+1+1+1+1+1"    | 4862 values                  | C(9) = 4862 <= 10^4, matches the problem's guarantee. Memo/DP keeps it fast (~ms).                          |
 * | Negative intermediate values           | "2-3*4"                  | [-4, -10]                    | (2-3)*4 = -4 and 2-(3*4) = -10. Operands are non-negative but RESULTS are freely negative.                  |
 * | Precedence must be ignored             | "1+2*3"                  | [9, 7]                       | The algorithm enumerates both trees. A precedence-aware evaluator would wrongly return only 7.              |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * PITFALL 1 - single-char parsing breaks multi-digit operands
 *
 *    // ❌ WRONG - "10*10" returns [1] or garbage
 *    if (results.isEmpty()) {
 *        results.add(expression.charAt(0) - '0');
 *    }
 *
 *    // ✅ CORRECT
 *    if (results.isEmpty()) {
 *        results.add(Integer.parseInt(expression));
 *    }
 *
 * PITFALL 2 - de-duplicating the answer
 *
 *    // ❌ WRONG - silently drops the second -10 for "2*3-4*5"
 *    Set<Integer> results = new HashSet<>();
 *
 *    // ✅ CORRECT - duplicates from different parenthesizations are distinct answers
 *    List<Integer> results = new ArrayList<>();
 *
 * PITFALL 3 - zipping instead of Cartesian-combining
 *
 *    // ❌ WRONG - pairs index-to-index, misses most combinations and can go out of bounds
 *    for (int i = 0; i < leftValues.size(); i++) {
 *        results.add(applyOperator(leftValues.get(i), rightValues.get(i), operator));
 *    }
 *
 *    // ✅ CORRECT - every left against every right
 *    for (int left : leftValues) {
 *        for (int right : rightValues) {
 *            results.add(applyOperator(left, right, operator));
 *        }
 *    }
 *
 * PITFALL 4 - detecting the base case with a fragile predicate
 *
 *    // ❌ WRONG - duplicates work the loop already does, and people commonly
 *    //            write it as a broken length==1 check
 *    if (expression.length() == 1) { ... }
 *
 *    // ✅ CORRECT - "the loop found no operator" is exactly the base case
 *    if (results.isEmpty()) { results.add(Integer.parseInt(expression)); }
 *
 * PITFALL 5 - mutating a memoized list
 *
 *    // ❌ WRONG - sorts the CACHED list in place, corrupting every future cache hit
 *    List<Integer> values = diffWaysToCompute(sub);
 *    Collections.sort(values);
 *
 *    // ✅ CORRECT - copy before mutating, or treat the returned list as read-only
 *    List<Integer> values = new ArrayList<>(diffWaysToCompute(sub));
 *    Collections.sort(values);
 *
 * PITFALL 6 - off-by-one on the operator index in the DP
 *
 *    // ❌ WRONG - grabs the operator after the split, shifting the whole expression
 *    char operator = operators.get(split + 1);
 *
 *    // ✅ CORRECT - operators.get(k) sits between numbers.get(k) and numbers.get(k+1)
 *    char operator = operators.get(split);
 *
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: Three real ones and one non-issue.
 *    - MULTI-DIGIT OPERANDS - the most-failed case. "10*10" must give [100], not
 *      [1] or [0]. Both the Integer.parseInt base case and the cur*10 + digit
 *      tokenizer are written specifically for this.
 *    - DUPLICATE VALUES - "2*3-4*5" returns -10 twice. Any Set anywhere in the
 *      pipeline is a wrong answer that passes the "2-1-1" example.
 *    - MALFORMED INPUT - a leading/trailing operator ("+1", "1+") makes substring
 *      produce "" and Integer.parseInt("") throw. LeetCode guarantees well-formed
 *      input, so this is out of scope, but say so rather than silently assuming it.
 *    - NON-ISSUE: the "empty string" edge case. expression.length() >= 1 is
 *      guaranteed, and no RECURSIVE call can receive "" on well-formed input, so
 *      no empty-string guard is needed.
 *
 * Q: Are there any type mismatches?
 * A: Yes, several worth naming.
 *    - List<Integer> is a list of BOXED Integers. for (int left : leftValues)
 *      auto-unboxes safely, but comparing results with == instead of .equals()
 *      would fail outside the [-128, 127] Integer cache. Test with equals-style
 *      logic on sorted lists, not == on elements.
 *    - results.add(left - right) autoboxes an int - fine. But results.remove(someInt)
 *      would call remove(int index), not remove(Object). Not used here; a classic
 *      trap if you extend the code.
 *    - new List[m][m] is an UNCHECKED generic array creation - Java forbids
 *      new List<Integer>[m][m]. The @SuppressWarnings("unchecked") annotation is
 *      required and correct; the alternative is List<List<List<Integer>>>, which
 *      is worse to read.
 *    - Overflow: the problem guarantees every result fits in a 32-bit int, so int
 *      arithmetic is safe. If operands were unbounded, * chains would overflow and
 *      you'd need long.
 *
 * Q: How can I verify this works right now?
 * A: Run this verify() - it cross-checks all three approaches against each other
 *    and against hand-computed answers. Run with `java -ea Verify` (assertions are
 *    OFF by default in the JVM; without -ea this silently passes).
 *
 *    import java.util.*;
 *
 *    public class Verify {
 *
 *        // ---- Approach 1 ----
 *        static List<Integer> brute(String expression) {
 *            List<Integer> results = new ArrayList<>();
 *            for (int i = 0; i < expression.length(); i++) {
 *                char op = expression.charAt(i);
 *                if (op == '+' || op == '-' || op == '*') {
 *                    for (int left : brute(expression.substring(0, i)))
 *                        for (int right : brute(expression.substring(i + 1)))
 *                            results.add(apply(left, right, op));
 *                }
 *            }
 *            if (results.isEmpty()) results.add(Integer.parseInt(expression));
 *            return results;
 *        }
 *
 *        // ---- Approach 2 ----
 *        static List<Integer> memoized(String expression, Map<String, List<Integer>> memo) {
 *            List<Integer> cached = memo.get(expression);
 *            if (cached != null) return cached;
 *            List<Integer> results = new ArrayList<>();
 *            for (int i = 0; i < expression.length(); i++) {
 *                char op = expression.charAt(i);
 *                if (op == '+' || op == '-' || op == '*') {
 *                    for (int left : memoized(expression.substring(0, i), memo))
 *                        for (int right : memoized(expression.substring(i + 1), memo))
 *                            results.add(apply(left, right, op));
 *                }
 *            }
 *            if (results.isEmpty()) results.add(Integer.parseInt(expression));
 *            memo.put(expression, results);
 *            return results;
 *        }
 *
 *        // ---- Approach 3 ----
 *        @SuppressWarnings("unchecked")
 *        static List<Integer> intervalDp(String expression) {
 *            List<Integer> numbers = new ArrayList<>();
 *            List<Character> operators = new ArrayList<>();
 *            int current = 0;
 *            for (char c : expression.toCharArray()) {
 *                if (Character.isDigit(c)) current = current * 10 + (c - '0');
 *                else { numbers.add(current); current = 0; operators.add(c); }
 *            }
 *            numbers.add(current);
 *            int m = numbers.size();
 *            List<Integer>[][] table = new List[m][m];
 *            for (int i = 0; i < m; i++) { table[i][i] = new ArrayList<>(); table[i][i].add(numbers.get(i)); }
 *            for (int len = 2; len <= m; len++)
 *                for (int i = 0; i + len - 1 < m; i++) {
 *                    int j = i + len - 1;
 *                    table[i][j] = new ArrayList<>();
 *                    for (int k = i; k < j; k++)
 *                        for (int left : table[i][k])
 *                            for (int right : table[k + 1][j])
 *                                table[i][j].add(apply(left, right, operators.get(k)));
 *                }
 *            return table[0][m - 1];
 *        }
 *
 *        static int apply(int a, int b, char op) {
 *            return op == '+' ? a + b : op == '-' ? a - b : a * b;
 *        }
 *
 *        static List<Integer> sorted(List<Integer> values) {
 *            List<Integer> copy = new ArrayList<>(values);
 *            Collections.sort(copy);
 *            return copy;
 *        }
 *
 *        static void verify() {
 *            // 1. Known answers (multiset equality via sorting).
 *            assert sorted(brute("2-1-1")).equals(Arrays.asList(0, 2))
 *                    : "2-1-1 should be {0,2}";
 *            assert sorted(brute("2*3-4*5")).equals(Arrays.asList(-34, -14, -10, -10, 10))
 *                    : "2*3-4*5 must keep the DUPLICATE -10";
 *
 *            // 2. Precedence must be IGNORED: 1+2*3 -> both 9 and 7.
 *            assert sorted(brute("1+2*3")).equals(Arrays.asList(7, 9)) : "must ignore precedence";
 *
 *            // 3. Multi-digit parsing.
 *            assert brute("11").equals(List.of(11))       : "multi-digit single operand";
 *            assert brute("10*10").equals(List.of(100))   : "multi-digit with operator";
 *            assert brute("0*0").equals(List.of(0))       : "zero operands";
 *
 *            // 4. Catalan-count sanity at the constraint bound (9 operators -> C(9)=4862).
 *            assert memoized("1+1+1+1+1+1+1+1+1+1", new HashMap<>()).size() == 4862
 *                    : "worst case must produce Catalan(9) results";
 *
 *            // 5. All three approaches must agree as multisets on every test.
 *            for (String test : new String[]{"2-1-1", "2*3-4*5", "11", "1+2*3-4", "0*0", "10*10", "7"}) {
 *                List<Integer> a = sorted(brute(test));
 *                List<Integer> b = sorted(memoized(test, new HashMap<>()));
 *                List<Integer> c = sorted(intervalDp(test));
 *                assert a.equals(b) : "brute vs memo disagree on " + test;
 *                assert b.equals(c) : "memo vs dp disagree on " + test;
 *            }
 *
 *            System.out.println("All assertions passed.");
 *        }
 *
 *        public static void main(String[] args) {
 *            boolean assertionsEnabled = false;
 *            assert assertionsEnabled = true;                  // side effect is intentional
 *            if (!assertionsEnabled) System.out.println("WARNING: run with -ea or assertions do nothing!");
 *            verify();
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Risk Table
 * ------------------------------------------------------------
 * | Approach       | Risk                                                                                          | Mitigation                                                                                     |
 * |----------------|-----------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------|
 * | 1. Brute Force | Re-derives every overlapping subexpression (Theta(3^n) redundant term) and allocates O(L)      | Acceptable within L <= 20, but add the memo the moment constraints loosen; state the           |
 * |                | substrings per call - ~42,000 calls at n=9                                                    | redundancy aloud in an interview even if you don't fix it                                      |
 * | 1. Brute Force | Deep recursion on a long expression                                                            | Depth is only O(n) <= 9 here; no stack risk under constraints                                   |
 * | 2. Memoized    | Returning the cached List BY REFERENCE - a caller that sorts/mutates it poisons the cache      | Document the read-only contract, or return new ArrayList<>(cached)                              |
 * | 2. Memoized    | String keys make hashing/substring an O(L) cost on every lookup                                | Key on an (i, j) index pair over a pre-tokenized array instead (which is exactly Approach 3)    |
 * | 3. Interval DP | Off-by-one between numbers[k] and operators[k]                                                 | Anchor on "operators[k] follows numbers[k]"; verify on "2*3-4*5" where a shift is visible       |
 * | 3. Interval DP | Tokenizer drops the final operand if you forget the post-loop flush                            | Always numbers.add(current) after the loop; test "11" which fails loudly without it             |
 * | All            | Using a Set and losing legitimate duplicate values                                             | Test on "2*3-4*5" - it must return 5 values with -10 twice                                      |
 * | All            | Multi-digit operands parsed as a single char                                                   | Test on "10*10" → [100]                                                                         |
 *
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * LeetCode 241 · Difficulty: Medium
 * Topics: Recursion, Divide & Conquer, Memoization, Dynamic Programming, Math (Catalan)
 * Approximate reported interview appearances: ~450+ across major boards in the last
 * several years - a steady mid-frequency question, most common at companies that
 * probe recursion depth rather than trick knowledge.
 *
 * | Company       | Frequency  | Notes                                                                                                             |
 * |---------------|------------|-------------------------------------------------------------------------------------------------------------------|
 * | Google        | ⭐⭐⭐⭐⭐ | Classic phone-screen/onsite pick. Expect the follow-up: "now handle division and parentheses already in the input" |
 * |               |            | → leads to LC 224/227 territory.                                                                                   |
 * | Amazon        | ⭐⭐⭐⭐   | Usually asked as-is. Interviewers focus on whether you spot the memoization opportunity unprompted.                |
 * | Meta          | ⭐⭐⭐⭐   | Often paired with "how many results are there?" to see if you recognize Catalan numbers.                           |
 * | Microsoft     | ⭐⭐⭐⭐   | Frequently framed as an expression-evaluator design question rather than a bare LC prompt.                         |
 * | Bloomberg     | ⭐⭐⭐⭐   | Strong favorite - Bloomberg likes parsing/expression problems generally.                                           |
 * | Apple         | ⭐⭐⭐     | Appears in the compiler/tooling org loops more than in general SWE loops.                                          |
 * | Uber          | ⭐⭐⭐     | Typically the memoized version is expected within the time limit.                                                  |
 * | Adobe         | ⭐⭐⭐     | Asked alongside LC 95 (Unique BSTs II) - same Catalan/interval-split skeleton.                                     |
 * | LinkedIn      | ⭐⭐⭐     | Often as a follow-up to Basic Calculator.                                                                          |
 * | Salesforce    | ⭐⭐       | Lower frequency; usually the brute force is accepted.                                                              |
 * | Oracle        | ⭐⭐       | Occasional; appears in senior loops with the "return the expressions, not just the values" twist.                  |
 * | Goldman Sachs | ⭐⭐       | Rare, but shows up in quant-adjacent SWE screens.                                                                  |
 *
 * Sibling problems built on the identical "split at every operator / build every
 * tree" skeleton: LC 95 Unique Binary Search Trees II, LC 96 Unique Binary Search
 * Trees (just the Catalan count), LC 312 Burst Balloons (interval DP with a
 * last-to-act choice), LC 1039 Minimum Score Triangulation. Recognizing the family
 * is worth more than memorizing this one problem.
 *
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach                                | Time                                    | Space                                                  | Code Complexity              | Recommended?                                       |
 * |-----------------------------------------|-----------------------------------------|--------------------------------------------------------|------------------------------|----------------------------------------------------|
 * | 1. Brute-Force Divide & Conquer         | O(L * C(n)) (+ Theta(3^n) term, dominated) | O(C(n)) output + O(L^2 + n) aux - LOWEST AUXILIARY   | Very low (~15 lines)         | ✅ BEST FOR LOW MEMORY - valid submission at L<=20 |
 * | 2. Memoized Divide & Conquer            | O(L * C(n)) - output-optimal            | O(m^2 * C(n)) cache + O(n) stack                       | Low (brute force + 3 lines)  | ✅✅ BEST OVERALL / INTERVIEW DEFAULT              |
 * | 3. Bottom-Up Interval DP                | O(L + C(n)) - output-optimal, best const| O(m^2 * C(n)) table, O(1) stack, no string churn       | Medium (tokenizer + 3 loops) | ✅ BEST FOR NO-RECURSION or reusable sub-results   |
 * | ❌ Precedence-aware evaluator / Set dedupe | -                                     | -                                                      | -                            | ❌ WRONG ANSWERS - misses parenthesizations and     |
 * |                                         |                                         |                                                        |                              | drops legitimate duplicates                        |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * APPROACH 2 (memoized divide & conquer). It is time-optimal, it's a three-line
 * delta from the brute force you'd write first, and it's the version interviewers
 * expect. The honest caveat: because the OUTPUT ITSELF is Catalan-sized, no
 * approach can beat Omega(C(n)) - so if auxiliary memory is the binding
 * constraint, APPROACH 1 is genuinely the better choice (it stores nothing but the
 * answer), and the memo buys you a constant factor, not a complexity class. Reach
 * for APPROACH 3 only when recursion is off the table or you need the
 * sub-interval results afterward.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * EVERY PARENTHESIZATION HAS EXACTLY ONE LAST-EVALUATED OPERATOR - so loop over
 * operator positions, recurse on both sides, and Cartesian-combine the two
 * returned LISTS. The recursion is keyed on a contiguous interval, which is why a
 * substring -> List<Integer> memo (or a dp[i][j] interval table) is the natural
 * optimization; the same "choose the last operation, split, combine" skeleton
 * solves Unique BSTs II and Burst Balloons.
 *
 * KEY GOTCHAS: parse with Integer.parseInt on the whole substring (never
 * charAt(0) - '0') because operands are multi-digit; use a List and NEVER a Set,
 * because two different trees producing -10 are two different answers; and the
 * result count is the Catalan number C(n) - 4862 at the n = 9 bound - which is the
 * floor no algorithm can go below.
 *
 * ============================================================
 * END OF EXPLANATION
 * ============================================================
 */
// @formatter:on
