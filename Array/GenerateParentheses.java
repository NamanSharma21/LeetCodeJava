package Array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerateParentheses {
    public static void main(String[] args) {
        GenerateParentheses generateParentheses = new GenerateParentheses();
        System.out.println("GenerateParentheses : " + generateParentheses.generateParenthesisBruteForce(3));
        System.out.println("GenerateParentheses : " + generateParentheses.generateParenthesisBruteForce(1));
        System.out.println("GenerateParentheses : " + generateParentheses.generateParenthesisBackTrackPruning(3));
        System.out.println("GenerateParentheses : " + generateParentheses.generateParenthesisBackTrackPruning(1));
        System.out.println("GenerateParentheses : " + generateParentheses.generateParenthesisDp(3));
        System.out.println("GenerateParentheses : " + generateParentheses.generateParenthesisDp(1));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/generate-parentheses/description/
     * 
     * Given n pairs of parentheses, write a function to generate all combinations
     * of well-formed parentheses.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: n = 3
     * Output: ["((()))","(()())","(())()","()(())","()()()"]
     * Example 2:
     * 
     * Input: n = 1
     * Output: ["()"]
     * 
     * 
     * Constraints:
     * 
     * 1 <= n <= 8
     */
    // @formatter:on

    public List<String> generateParenthesisBruteForce(int n) {
        List<String> results = new ArrayList<>();
        generateAll(new char[2 * n], 0, results);
        return results;
    }

    private void generateAll(char[] current, int position, List<String> results) {
        if (position == current.length) {
            if (isValid(current)) {
                results.add(new String(current));
            }
            return;
        }

        current[position] = '(';
        generateAll(current, position + 1, results);
        current[position] = ')';
        generateAll(current, position + 1, results);
    }

    public boolean isValid(char[] current) {
        int balance = 0;
        for (char c : current) {
            if (c == '(')
                balance++;
            else
                balance--;
            if (balance < 0)
                return false;
        }
        return balance == 0;
    }

    public List<String> generateParenthesisBackTrackPruning(int n) {
        List<String> results = new ArrayList<>();
        backTrackPruning(results, new StringBuilder(), 0, 0, n);
        return results;
    }

    public void backTrackPruning(List<String> results, StringBuilder current, int openCount, int closeCount, int max) {
        if (current.length() == 2 * max) {
            results.add(current.toString());
            return;
        }
        if (openCount < max) {
            current.append("(");
            backTrackPruning(results, current, openCount + 1, closeCount, max);
            current.deleteCharAt(current.length() - 1);
        }

        if (closeCount < openCount) {
            current.append(")");
            backTrackPruning(results, current, openCount, closeCount + 1, max);
            current.deleteCharAt(current.length() - 1);
        }
    }

    public List<String> generateParenthesisDp(int n) {
        List<List<String>> dp = new ArrayList<>();
        dp.add(Collections.singletonList(""));
        for (int i = 1; i <= n; i++) {
            List<String> currentList = new ArrayList<>();
            for (int k = 0; k < i; k++) {
                for (String inner : dp.get(k)) {
                    for (String outer : dp.get(i - k - 1)) {
                        currentList.add("(" + inner + ")" + outer);
                    }
                }
            }
            dp.add(currentList);
        }
        return dp.get(n);
    }
}

// @formatter:off
/*
 * ============================================================
 * Generate Parentheses — Deep Dive Explanation
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given a positive integer n, generate all combinations of well-formed
 * parentheses using exactly n pairs of opening '(' and closing ')' parentheses.
 *
 * LeetCode #22 — Medium
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int n — number of pairs of parentheses (1 <= n <= 8)
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * List<String> — all valid parentheses combinations in any order
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * 1 <= n <= 8
 * No duplicate strings in the output
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * You must enumerate every string of length 2n that is a valid parenthesis
 * sequence — meaning every prefix has at least as many '(' as ')', and the
 * total counts are equal.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 * Input:  n = 3
 * Output: ["((()))", "(()())", "(())()", "()(())", "()()()"]
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Think of building the string one character at a time, left to right.
 * At every position you have a choice: place '(' or ')'. But not every
 * choice is legal. You can place '(' as long as you haven't used all n.
 * You can place ')' only if there are more '('s placed than ')'s — otherwise
 * you'd create an invalid prefix.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Start with an empty string and counters: open = 0, close = 0.
 * 2. At each step, if open < n, you CAN add '('.
 * 3. If close < open, you CAN add ')'.
 * 4. When the string reaches length 2n, it's valid — add it to results.
 * 5. Recurse / backtrack to explore all valid branches.
 *
 * Real-world analogy: Imagine distributing n left shoes and n right shoes in
 * a line. You can place a left shoe anytime (up to n), but a right shoe only
 * after at least one more left shoe than right shoes — otherwise the pair is
 * incomplete.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge               | Why It's Tricky                                  |
 * |-------------------------|--------------------------------------------------|
 * | Counting open vs close  | Need two counters — string length isn't enough   |
 * | Knowing when to stop    | Premature ')' makes the whole subtree invalid     |
 * | Avoiding duplicates     | Without the open/close guard, repeats can form    |
 * | Combinatorial explosion | Grows as Catalan number — fast but not 2^(2n)    |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                        | Key Idea                         | Best Used When         | Time           | Space          |
 * |---|---------------------------------|----------------------------------|------------------------|----------------|----------------|
 * | 1 | Brute Force (generate+filter)   | Generate all 2^(2n), keep valid  | Never (illustration)   | O(2^(2n) * n)  | O(2^(2n) * n)  |
 * | 2 | Backtracking with Pruning [OK]  | Build only valid via open/close  | Always — canonical     | O(4^n / sqrt(n))| O(4^n / sqrt(n))|
 * | 3 | Dynamic Programming             | Combine smaller subproblem lists | Bottom-up view desired | O(4^n / sqrt(n))| O(4^n / sqrt(n))|
 *
 * Trade-off: Brute force is purely educational. Backtracking and DP share
 * the same asymptotic output size (Catalan number). Backtracking prunes
 * eagerly and is simpler; DP demonstrates the recursive subproblem structure.
 * Both time-optimal approaches tie; backtracking is preferred for simplicity.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (Generate All, Then Filter)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Generate all 2^(2n) strings of length 2n.
 * 2. Validate each: scan left-to-right, maintain balance counter.
 * 3. Increment for '(', decrement for ')'.
 * 4. If balance goes negative — invalid. Ends at 0 — valid.
 * 5. Collect all valid strings.
 *
 *    import java.util.*;
 *
 *    public class GenerateParenthesesBrute {
 *
 *        public List<String> generateParenthesis(int n) {
 *            List<String> results = new ArrayList<>();
 *            generateAll(new char[2 * n], 0, results);
 *            return results;
 *        }
 *
 *        private void generateAll(char[] current, int position, List<String> results) {
 *            if (position == current.length) {
 *                if (isValid(current)) {
 *                    results.add(new String(current));
 *                }
 *                return;
 *            }
 *            current[position] = '(';
 *            generateAll(current, position + 1, results);
 *            current[position] = ')';
 *            generateAll(current, position + 1, results);
 *        }
 *
 *        private boolean isValid(char[] current) {
 *            int balance = 0;
 *            for (char c : current) {
 *                if (c == '(') balance++;
 *                else balance--;
 *                if (balance < 0) return false;
 *            }
 *            return balance == 0;
 *        }
 *
 *        public static void main(String[] args) {
 *            GenerateParenthesesBrute solver = new GenerateParenthesesBrute();
 *            System.out.println(solver.generateParenthesis(3));
 *            // Expected: ["((()))", "(()())", "(())()", "()(())", "()()()"]
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 2: Backtracking with Pruning [OPTIMAL]
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Start with empty StringBuilder, openCount=0, closeCount=0.
 * 2. Base case: if current.length()==2n, add to results.
 * 3. Branch A: if openCount < n, append '(' and recurse with openCount+1.
 * 4. Branch B: if closeCount < openCount, append ')' and recurse with closeCount+1.
 * 5. Backtrack (deleteCharAt) after each recursive call.
 *
 *    import java.util.*;
 *
 *    public class GenerateParenthesesBacktrack {
 *
 *        public List<String> generateParenthesis(int n) {
 *            List<String> results = new ArrayList<>();
 *            backtrack(results, new StringBuilder(), 0, 0, n);
 *            return results;
 *        }
 *
 *        private void backtrack(List<String> results, StringBuilder current,
 *                               int openCount, int closeCount, int max) {
 *            if (current.length() == 2 * max) {
 *                results.add(current.toString());
 *                return;
 *            }
 *
 *            if (openCount < max) {
 *                current.append('(');
 *                backtrack(results, current, openCount + 1, closeCount, max);
 *                current.deleteCharAt(current.length() - 1);
 *            }
 *
 *            if (closeCount < openCount) {
 *                current.append(')');
 *                backtrack(results, current, openCount, closeCount + 1, max);
 *                current.deleteCharAt(current.length() - 1);
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            GenerateParenthesesBacktrack solver = new GenerateParenthesesBacktrack();
 *            System.out.println(solver.generateParenthesis(3));
 *            System.out.println(solver.generateParenthesis(1));
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 3: Dynamic Programming
 * ------------------------------------------------------------
 * Algorithm:
 * 1. dp[0] = [""] (empty string is the only 0-pair combination).
 * 2. For each i from 1 to n, generate all strings:
 *    "(" + dp[k] + ")" + dp[i-1-k] for k from 0 to i-1.
 * 3. This covers k pairs inside the first outer pair, i-1-k pairs after it.
 * 4. Return dp[n].
 *
 *    import java.util.*;
 *
 *    public class GenerateParenthesesDP {
 *
 *        public List<String> generateParenthesis(int n) {
 *            List<List<String>> dp = new ArrayList<>();
 *            dp.add(Collections.singletonList(""));
 *
 *            for (int i = 1; i <= n; i++) {
 *                List<String> currentList = new ArrayList<>();
 *                for (int k = 0; k < i; k++) {
 *                    for (String inner : dp.get(k)) {
 *                        for (String outer : dp.get(i - 1 - k)) {
 *                            currentList.add("(" + inner + ")" + outer);
 *                        }
 *                    }
 *                }
 *                dp.add(currentList);
 *            }
 *
 *            return dp.get(n);
 *        }
 *
 *        public static void main(String[] args) {
 *            GenerateParenthesesDP solver = new GenerateParenthesesDP();
 *            System.out.println(solver.generateParenthesis(3));
 *            System.out.println(solver.generateParenthesis(1));
 *        }
 *    }
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Time:  O(2^(2n) * n)
 *   - 2 choices at each of 2n positions → 2^(2n) strings
 *   - Validating each takes O(n)
 *   - n=3: 2^6 = 64 strings × 6 chars = 384 operations
 *
 * Space: O(2^(2n) * n)
 *   - All strings stored before filtering
 *
 * ------------------------------------------------------------
 * Approach 2: Backtracking [OPTIMAL]
 * ------------------------------------------------------------
 * Time:  O(4^n / sqrt(n))
 *   - Valid strings count = n-th Catalan number Cn = C(2n,n)/(n+1) ≈ 4^n/(n^(3/2)*sqrt(pi))
 *   - Each string takes O(n) to build → O(Cn * n) = O(4^n / sqrt(n))
 *   - n=3: C3=5 strings × 6 chars = 30 ops (vs 384 brute force)
 *
 * Space: O(4^n / sqrt(n))
 *   - Output list dominates. Recursion stack depth is O(n) — negligible.
 *
 * ------------------------------------------------------------
 * Approach 3: Dynamic Programming
 * ------------------------------------------------------------
 * Time:  O(4^n / sqrt(n))
 *   - Same asymptotic — generates exactly Catalan number of strings
 *   - String concatenation adds O(n) factor per string
 *
 * Space: O(4^n / sqrt(n))
 *   - All intermediate dp lists are stored
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 2: Backtracking — n = 2
 * ------------------------------------------------------------
 * backtrack("", open=0, close=0)
 * ├─ append '(' → backtrack("(", open=1, close=0)
 * │   ├─ append '(' → backtrack("((", open=2, close=0)
 * │   │   └─ append ')' → backtrack("(()", open=2, close=1)
 * │   │       └─ append ')' → backtrack("(())", open=2, close=2)
 * │   │           └─ length==4 → ADD "(())"
 * │   └─ append ')' → backtrack("()", open=1, close=1)
 * │       └─ append '(' → backtrack("()(", open=2, close=1)
 * │           └─ append ')' → backtrack("()()", open=2, close=2)
 * │               └─ length==4 → ADD "()()"
 *
 * Output: ["(())", "()()"]
 *
 * ------------------------------------------------------------
 * Approach 3: Dynamic Programming — n = 3
 * ------------------------------------------------------------
 * dp[0] = [""]
 * dp[1]:
 *   k=0: "(" + "" + ")" + "" = "()"
 *   → ["()"]
 *
 * dp[2]:
 *   k=0: "(" + dp[0] + ")" + dp[1] → "()()"
 *   k=1: "(" + dp[1] + ")" + dp[0] → "(())"
 *   → ["()()", "(())"]
 *
 * dp[3]:
 *   k=0: "(" + dp[0] + ")" + dp[2] → "()()()", "()(())"
 *   k=1: "(" + dp[1] + ")" + dp[1] → "(())()"
 *   k=2: "(" + dp[2] + ")" + dp[0] → "(()())", "((()))"
 *   → ["()()()", "()(())", "(())()", "(()())", "((()))"]
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case          | Input | Expected Output                       | How Handled                                  |
 * |--------------------|-------|---------------------------------------|----------------------------------------------|
 * | Minimum input      | n = 1 | ["()"]                                | open reaches 1, close reaches 1 immediately |
 * | n = 2              | n = 2 | ["(())", "()()"]                      | Catalan(2) = 2 valid strings                 |
 * | Maximum constraint | n = 8 | 1430 strings                          | Still feasible — Catalan(8) = 1430           |
 * | Single pair check  | n = 1 | Only "()"                             | One path only in backtrack                   |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * WRONG — using string length alone to gate closing:
 *   if (current.length() < 2 * n) current.append(')');
 *   // allows invalid prefix "((" to get ")" before it's valid
 *
 * CORRECT — use close < open guard:
 *   if (closeCount < openCount) current.append(')');
 *
 * WRONG — not backtracking after appending to StringBuilder:
 *   backtrack(results, current.append('('), ...);
 *   // current is polluted for the next branch!
 *
 * CORRECT — always pair append with deleteCharAt:
 *   current.append('(');
 *   backtrack(results, current, ...);
 *   current.deleteCharAt(current.length() - 1);
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The minimum case n=1 — always verify it produces exactly ["()"].
 *    Also confirm n=0 behavior matches the problem spec.
 *
 * Q: Are there any type mismatches?
 * A: No. Input is int, output is List<String>. The only non-obvious API is
 *    StringBuilder.deleteCharAt — confirm index is current.length()-1, not 0.
 *
 * Q: How can I verify this works right now?
 *
 *    public static void verify() {
 *        GenerateParenthesesBacktrack solver = new GenerateParenthesesBacktrack();
 *
 *        List<String> result1 = solver.generateParenthesis(1);
 *        assert result1.size() == 1 : "n=1 should have 1 result";
 *        assert result1.contains("()") : "n=1 must contain ()";
 *
 *        List<String> result2 = solver.generateParenthesis(2);
 *        assert result2.size() == 2 : "n=2 should have 2 results";
 *        assert result2.containsAll(Arrays.asList("(())", "()()")) : "n=2 wrong";
 *
 *        List<String> result3 = solver.generateParenthesis(3);
 *        assert result3.size() == 5 : "n=3 should have 5 results";
 *
 *        List<String> result4 = solver.generateParenthesis(4);
 *        assert result4.size() == 14 : "n=4 should have 14 results";
 *
 *        System.out.println("All assertions passed!");
 *    }
 *
 * | Approach      | Risk                                          | Mitigation                              |
 * |---------------|-----------------------------------------------|-----------------------------------------|
 * | Brute Force   | Exponential space for n > 6                   | Never use in production                 |
 * | Backtracking  | Forgetting deleteCharAt causes wrong strings  | Always pair append with deleteCharAt    |
 * | DP            | Off-by-one in k loop (k < i, not k <= i)      | Trace with n=2 manually before submit   |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * | Company     | Frequency (stars) | Notes                              |
 * |-------------|-------------------|------------------------------------|
 * | Google      | ⭐⭐⭐⭐⭐        | Extremely common in phone screens  |
 * | Amazon      | ⭐⭐⭐⭐⭐        | Frequently asked at L4-L5 level    |
 * | Meta        | ⭐⭐⭐⭐          | Appears in coding rounds           |
 * | Microsoft   | ⭐⭐⭐⭐          | Common for SDE-2 interviews        |
 * | Apple       | ⭐⭐⭐            | Moderate frequency                 |
 * | Bloomberg   | ⭐⭐⭐            | Seen in analyst and SWE rounds     |
 * | Adobe       | ⭐⭐⭐            | Appears in onsite rounds           |
 * | Uber        | ⭐⭐⭐            | Common for backend roles           |
 * | LinkedIn    | ⭐⭐              | Occasional appearance              |
 * | Oracle      | ⭐⭐              | Less frequent but documented       |
 *
 * LeetCode #22 — Medium difficulty — ~8,000+ reported interview appearances
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach               | Time            | Space           | Code Complexity | Recommended?                    |
 * |------------------------|-----------------|-----------------|-----------------|----------------------------------|
 * | Brute Force            | O(2^(2n) * n)   | O(2^(2n) * n)   | Low             | ❌ Never in interviews           |
 * | Backtracking (Pruning) | O(4^n / sqrt(n))| O(4^n / sqrt(n))| Low             | ✅✅ Best overall                |
 * | Dynamic Programming    | O(4^n / sqrt(n))| O(4^n / sqrt(n))| Medium          | ✅ Acceptable; shows DP insight  |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Backtracking with Pruning — it models the decision tree directly, prunes
 * invalid branches eagerly, and produces output in a single clean recursive
 * pass. DP is a great alternative to demonstrate understanding of the
 * Catalan number recurrence.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * - The two guards `openCount < n` and `closeCount < openCount` are the
 *   complete validity invariant — memorize them.
 * - The number of valid strings for n pairs is the n-th Catalan number
 *   (C3=5, C4=14, C5=42...), growing as O(4^n / n^(3/2)).
 * - This is the canonical template for constrained backtracking — the
 *   pattern of "add, recurse, remove" with pruning conditions appears
 *   everywhere (N-Queens, Sudoku, Word Search).
 */
// @formatter:on
