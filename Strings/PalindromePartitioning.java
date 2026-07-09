package Strings;

import java.util.ArrayList;
import java.util.List;

public class PalindromePartitioning {
    public static void main(String[] args) {
        PalindromePartitioning palindromePartitioning = new PalindromePartitioning();
        System.out.println("PalindromePartitioning : " + palindromePartitioning.partitionBruteForce("aab"));
        System.out.println("PalindromePartitioning : " + palindromePartitioning.partitionBruteForce("bb"));
        System.out.println("PalindromePartitioning : " + palindromePartitioning.partitionBackTrackDPTabulation("aab"));
        System.out.println("PalindromePartitioning : " + palindromePartitioning.partitionBackTrackDPTabulation("bb"));
        System.out.println("PalindromePartitioning : " + palindromePartitioning.partitionBackTrackDPMemoization("aab"));
        System.out.println("PalindromePartitioning : " + palindromePartitioning.partitionBackTrackDPMemoization("bb"));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/palindrome-partitioning/description/
     * 
     * Given a string s, partition s such that every substring of the partition is a
     * palindrome. Return all possible palindrome partitioning of s.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: s = "aab"
     * Output: [["a","a","b"],["aa","b"]]
     * Example 2:
     * 
     * Input: s = "a"
     * Output: [["a"]]
     * 
     * 
     * Constraints:
     * 
     * 1 <= s.length <= 16
     * s contains only lowercase English letters.
     */
    // @formatter:on

    public List<List<String>> partitionBruteForce(String s) {
        List<List<String>> result = new ArrayList<>();
        backTrackBruteForce(s, 0, new ArrayList<>(), result);
        return result;
    }

    public void backTrackBruteForce(String s, int start, List<String> currentPartition, List<List<String>> result) {
        if (start == s.length()) {
            result.add(new ArrayList<>(currentPartition));
            return;
        }

        for (int end = start + 1; end <= s.length(); end++) {
            if (isPalindrome(s, start, end - 1)) {
                currentPartition.add(s.substring(start, end));
                backTrackBruteForce(s, end, currentPartition, result);
                currentPartition.remove(currentPartition.size() - 1);
            }
        }
    }

    public boolean isPalindrome(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start) != s.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    public List<List<String>> partitionBackTrackDPTabulation(String s) {
        int n = s.length();
        List<List<String>> result = new ArrayList<>();
        boolean[][] dp = buildPalindromeTable(s, n);
        backTrackDPTabulation(s, 0, dp, new ArrayList<>(), result);
        return result;
    }

    public void backTrackDPTabulation(String s, int start, boolean[][] dp, List<String> currentPartition,
            List<List<String>> result) {
        int n = s.length();
        if (start == s.length()) {
            result.add(new ArrayList<>(currentPartition));
        }

        for (int end = start; end < n; end++) {
            if (dp[start][end]) {
                currentPartition.add(s.substring(start, end + 1));
                backTrackDPTabulation(s, end + 1, dp, currentPartition, result);
                currentPartition.remove(currentPartition.size() - 1);
            }
        }
    }

    public boolean[][] buildPalindromeTable(String s, int n) {
        boolean[][] dp = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        for (int length = 2; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = length == 2 || dp[i + 1][j - 1];
                }
            }
        }
        return dp;
    }

    private Boolean[][] memo;

    public List<List<String>> partitionBackTrackDPMemoization(String s) {
        int n = s.length();
        List<List<String>> result = new ArrayList<>();
        memo = new Boolean[n][n];
        List<String> currentPartition = new ArrayList<>();
        backTrackDPMemoization(s, 0, currentPartition, result);
        return result;
    }

    public void backTrackDPMemoization(String s, int start, List<String> current, List<List<String>> result) {
        if (start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int end = start; end < s.length(); end++) {
            if (isPalindromeDPMemoization(s, start, end)) {
                current.add(s.substring(start, end + 1));
                backTrackDPMemoization(s, end + 1, current, result);
                current.remove(current.size() - 1);
            }
        }
    }

    public boolean isPalindromeDPMemoization(String s, int start, int end) {
        if (start >= end)
            return true;
        if (memo[start][end] != null)
            return memo[start][end];
        boolean result = s.charAt(start) == s.charAt(end) && isPalindrome(s, start + 1, end - 1);
        memo[start][end] = result;
        return result;
    }
}

// @formatter:off
/*
 * ============================================================
 * PALINDROME PARTITIONING - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ------------------------------------------------------------
 * 1. PROBLEM STATEMENT
 * ------------------------------------------------------------
 * What is the Problem?
 * Given a string s, partition s into substrings such that every
 * substring in the partition is a palindrome. Return all possible
 * palindrome partitionings of s.
 *
 * Input Format: String s (lowercase English letters)
 * Output Format: List<List<String>> result
 * Constraints: 1 <= s.length <= 16, lowercase English letters only.
 * What Exactly Needs to Be Computed?
 * At every position, decide where to cut so the left piece is a
 * palindrome, then recursively partition the remainder. Collect
 * every complete chain of cuts that covers the whole string.
 *
 * Quick Example:
 * Input: s = "aab"
 * Output: [["a","a","b"], ["aa","b"]]
 *
 * ------------------------------------------------------------
 * 2. INTUITION
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * Walk left to right. At each starting index, try every possible
 * next cut. If the piece up to that cut is a palindrome, take it,
 * then recurse on the rest of the string.
 *
 * How a Human Reasons About It
 * 1. Start at index 0.
 * 2. Try the shortest piece (1 char) - always a palindrome.
 * 3. Try a 2-char piece - valid only if both chars match.
 * 4. Keep extending and checking every valid length.
 * 5. For each valid piece, recurse from where it ended.
 * 6. At end of string, record the completed partition.
 * 7. Backtrack and try the next valid length at each decision point.
 *
 * What Makes This Tricky?
 * | Challenge                    | Why it's tricky                                          |
 * |-------------------------------|-----------------------------------------------------------|
 * | Exponential branching         | Up to n valid cut points per index -> heavy recursion tree |
 * | Repeated palindrome checks    | Naive isPalindrome costs O(n) each call, multiplies cost   |
 * | Building vs restoring state   | Must remove last piece after recursing (backtrack)         |
 * | Off-by-one substring bounds   | substring(start,end) is end-exclusive; must stay consistent|
 * | All solutions required        | Cannot prune valid branches, unlike a single-answer search |
 *
 * ------------------------------------------------------------
 * 3. APPROACH OVERVIEW
 * ------------------------------------------------------------
 * | # | Approach                                   | Key Idea                                   | Best Used When                          | Time            | Space          |
 * |---|---------------------------------------------|---------------------------------------------|------------------------------------------|-----------------|----------------|
 * | 1 | Brute-Force Backtracking                     | Check palindrome on the fly each call        | n very small, simplicity valued          | O(n * 2^n)      | O(n) (space-optimal) |
 * | 2 | Backtracking + Precomputed DP Table (BEST)   | Precompute dp[i][j] in O(n^2), O(1) lookups  | General/optimal case                     | O(n * 2^n), small constant | O(n^2) |
 * | 3 | Backtracking + Memoized Palindrome Check     | Lazy top-down memoization                    | Sparse queries, not all substrings probed| O(n * 2^n)      | O(n^2) worst case |
 *
 * Approaches 1 and 2 share the same exponential branching (output
 * is inherently exponential, e.g. "aaaa...a"). The difference is
 * the constant factor per palindrome check: brute force rescans
 * O(n) characters each time; the DP table answers in O(1) after an
 * O(n^2) precomputation. Approach 3 is similar to Approach 2 but
 * computes entries lazily. Approach 2 is the standard recommended
 * solution for production/interview use given n <= 16.
 *
 * ------------------------------------------------------------
 * 4. DETAILED SOLUTIONS IN JAVA
 * ------------------------------------------------------------
 *
 * --- Approach 1: Brute-Force Backtracking ---
 * Steps:
 *   1. Recursive helper backtrack(start, currentPartition).
 *   2. If start == s.length(), record a copy of currentPartition.
 *   3. For each end from start+1 to s.length():
 *      a. substring = s.substring(start, end)
 *      b. If palindrome, add to currentPartition
 *      c. Recurse backtrack(end, currentPartition)
 *      d. Undo: remove last added piece
 *   4. Start with backtrack(0, new ArrayList<>())
 *
 *    import java.util.ArrayList;
 *    import java.util.List;
 *
 *    public class PalindromePartitioningBruteForce {
 *
 *        public List<List<String>> partition(String s) {
 *            List<List<String>> result = new ArrayList<>();
 *            List<String> currentPartition = new ArrayList<>();
 *            backtrack(s, 0, currentPartition, result);
 *            return result;
 *        }
 *
 *        private void backtrack(String s, int start, List<String> currentPartition, List<List<String>> result) {
 *            if (start == s.length()) {
 *                result.add(new ArrayList<>(currentPartition));
 *                return;
 *            }
 *            for (int end = start + 1; end <= s.length(); end++) {
 *                if (isPalindrome(s, start, end - 1)) {
 *                    currentPartition.add(s.substring(start, end));
 *                    backtrack(s, end, currentPartition, result);
 *                    currentPartition.remove(currentPartition.size() - 1);
 *                }
 *            }
 *        }
 *
 *        private boolean isPalindrome(String s, int left, int right) {
 *            while (left < right) {
 *                if (s.charAt(left) != s.charAt(right)) return false;
 *                left++;
 *                right--;
 *            }
 *            return true;
 *        }
 *
 *        public static void main(String[] args) {
 *            PalindromePartitioningBruteForce solver = new PalindromePartitioningBruteForce();
 *            System.out.println(solver.partition("aab"));
 *        }
 *    }
 *
 * Note: isPalindrome uses inclusive right bound (end-1) because
 * end is exclusive when used in substring(start, end).
 *
 * --- Approach 2: Backtracking + Precomputed DP Table (OPTIMAL) ---
 * Steps:
 *   1. Build dp[i][j]: true if s[i..j] inclusive is a palindrome.
 *   2. Fill bottom-up by increasing length using the recurrence
 *      dp[i][j] = (s[i]==s[j]) && (length<=2 || dp[i+1][j-1])
 *   3. Backtrack using dp[start][end] for O(1) lookups.
 *
 *    import java.util.ArrayList;
 *    import java.util.List;
 *
 *    public class PalindromePartitioningDP {
 *
 *        public List<List<String>> partition(String s) {
 *            int n = s.length();
 *            boolean[][] dp = buildPalindromeTable(s, n);
 *
 *            List<List<String>> result = new ArrayList<>();
 *            List<String> currentPartition = new ArrayList<>();
 *            backtrack(s, 0, dp, currentPartition, result);
 *            return result;
 *        }
 *
 *        private boolean[][] buildPalindromeTable(String s, int n) {
 *            boolean[][] dp = new boolean[n][n];
 *            for (int i = 0; i < n; i++) {
 *                dp[i][i] = true;
 *            }
 *            for (int length = 2; length <= n; length++) {
 *                for (int i = 0; i <= n - length; i++) {
 *                    int j = i + length - 1;
 *                    if (s.charAt(i) == s.charAt(j)) {
 *                        dp[i][j] = (length == 2) || dp[i + 1][j - 1];
 *                    }
 *                }
 *            }
 *            return dp;
 *        }
 *
 *        private void backtrack(String s, int start, boolean[][] dp,
 *                                List<String> currentPartition, List<List<String>> result) {
 *            int n = s.length();
 *            if (start == n) {
 *                result.add(new ArrayList<>(currentPartition));
 *                return;
 *            }
 *            for (int end = start; end < n; end++) {
 *                if (dp[start][end]) {
 *                    currentPartition.add(s.substring(start, end + 1));
 *                    backtrack(s, end + 1, dp, currentPartition, result);
 *                    currentPartition.remove(currentPartition.size() - 1);
 *                }
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            PalindromePartitioningDP solver = new PalindromePartitioningDP();
 *            System.out.println(solver.partition("aab"));
 *        }
 *    }
 *
 * Note: the table must be filled in increasing length order since
 * dp[i][j] depends on dp[i+1][j-1], a strictly shorter substring.
 *
 * --- Approach 3: Backtracking + Memoized Palindrome Check ---
 * Steps:
 *   1. Boolean[][] memo initialized to null (unknown).
 *   2. isPalindromeMemo(s,i,j) checks memo first; computes and
 *      stores lazily via the same recurrence as Approach 2.
 *   3. Backtracking structure identical to Approach 2.
 *
 *    import java.util.ArrayList;
 *    import java.util.List;
 *
 *    public class PalindromePartitioningMemo {
 *
 *        private Boolean[][] memo;
 *
 *        public List<List<String>> partition(String s) {
 *            int n = s.length();
 *            memo = new Boolean[n][n];
 *            List<List<String>> result = new ArrayList<>();
 *            List<String> currentPartition = new ArrayList<>();
 *            backtrack(s, 0, currentPartition, result);
 *            return result;
 *        }
 *
 *        private void backtrack(String s, int start, List<String> currentPartition, List<List<String>> result) {
 *            if (start == s.length()) {
 *                result.add(new ArrayList<>(currentPartition));
 *                return;
 *            }
 *            for (int end = start; end < s.length(); end++) {
 *                if (isPalindromeMemo(s, start, end)) {
 *                    currentPartition.add(s.substring(start, end + 1));
 *                    backtrack(s, end + 1, currentPartition, result);
 *                    currentPartition.remove(currentPartition.size() - 1);
 *                }
 *            }
 *        }
 *
 *        private boolean isPalindromeMemo(String s, int i, int j) {
 *            if (i >= j) return true;
 *            if (memo[i][j] != null) return memo[i][j];
 *            boolean result = (s.charAt(i) == s.charAt(j)) && isPalindromeMemo(s, i + 1, j - 1);
 *            memo[i][j] = result;
 *            return result;
 *        }
 *
 *        public static void main(String[] args) {
 *            PalindromePartitioningMemo solver = new PalindromePartitioningMemo();
 *            System.out.println(solver.partition("aab"));
 *        }
 *    }
 *
 * Note: i >= j handles both empty range and single-char range
 * in one base case.
 *
 * ------------------------------------------------------------
 * 5. TIME & SPACE COMPLEXITY
 * ------------------------------------------------------------
 * Approach 1 (Brute Force):
 *   Time: O(n * 2^n) - up to 2^(n-1) partitions, each up to O(n)
 *   work for substring copies and palindrome checks.
 *   Space: O(n) recursion stack and current partition list.
 *   Example: n=10 ("aaaaaaaaaa") -> up to 512 partitions, each
 *   needing up to 10 checks of up to 10 chars.
 *
 * Approach 2 (DP Table):
 *   Time: O(n^2) precompute + O(n * 2^n) backtracking with O(1)
 *   checks; same asymptotic class as Approach 1 but smaller constant.
 *   Space: O(n^2) for table + O(n) recursion stack.
 *   Example: n=16 (max constraint) -> table has 256 entries.
 *
 * Approach 3 (Memoization):
 *   Time: O(n * 2^n), same as Approach 2 worst case.
 *   Space: O(n^2) worst case for memo + O(n) recursion stack
 *   (both partitioning recursion and palindrome-check recursion).
 *   Example: n=16 -> memo table same max size as Approach 2's
 *   table, but possibly fewer entries computed in sparse inputs.
 *
 * ------------------------------------------------------------
 * 6. COMPLETE WORKED EXAMPLES
 * ------------------------------------------------------------
 *
 * Approach 1: Brute Force - Input s = "aab"
 *
 * backtrack(start=0, partition=[])
 * |- end=1: substr="a" -> palindrome -> partition=["a"]
 * |   |- backtrack(start=1, partition=["a"])
 * |       |- end=2: substr="a" -> palindrome -> partition=["a","a"]
 * |       |   |- backtrack(start=2, partition=["a","a"])
 * |       |       |- end=3: substr="b" -> palindrome -> partition=["a","a","b"]
 * |       |           |- backtrack(start=3,...) -> RECORD ["a","a","b"]
 * |       |       |- undo "b" -> partition=["a","a"]
 * |       |   |- undo "a" -> partition=["a"]
 * |       |- end=3: substr="ab" -> NOT palindrome -> skip
 * |   |- undo "a" -> partition=[]
 * |- end=2: substr="aa" -> palindrome -> partition=["aa"]
 * |   |- backtrack(start=2, partition=["aa"])
 * |       |- end=3: substr="b" -> palindrome -> partition=["aa","b"]
 * |           |- backtrack(start=3,...) -> RECORD ["aa","b"]
 * |       |- undo "b" -> partition=["aa"]
 * |   |- undo "aa" -> partition=[]
 * |- end=3: substr="aab" -> NOT palindrome -> skip
 *
 * Final output: [["a","a","b"], ["aa","b"]]
 *
 * Approach 2: DP Table - Input s = "aab"
 *
 * Step 1 - Build dp table (n=3, indices 0='a',1='a',2='b'):
 *   Length 1: dp[0][0]=T, dp[1][1]=T, dp[2][2]=T
 *   Length 2: dp[0][1]: s[0]=='a'==s[1] -> T. dp[1][2]: 'a'!='b' -> F
 *   Length 3: dp[0][2]: 'a'!='b' -> F
 *
 * Final table:
 *         j=0   j=1   j=2
 *   i=0    T     T     F
 *   i=1    -     T     F
 *   i=2    -     -     T
 *
 * Step 2 - Backtrack using table:
 * backtrack(start=0)
 * |- end=0: dp[0][0]=T -> take "a" -> backtrack(start=1)
 * |   |- end=1: dp[1][1]=T -> take "a" -> backtrack(start=2)
 * |   |   |- end=2: dp[2][2]=T -> take "b" -> RECORD ["a","a","b"]
 * |   |- end=2: dp[1][2]=F -> skip
 * |- end=1: dp[0][1]=T -> take "aa" -> backtrack(start=2)
 * |   |- end=2: dp[2][2]=T -> take "b" -> RECORD ["aa","b"]
 * |- end=2: dp[0][2]=F -> skip
 *
 * Final output: [["a","a","b"], ["aa","b"]] - same result, fewer
 * repeated character comparisons.
 *
 * Approach 3: Memoization - Input s = "aab"
 * Trace is structurally identical to Approach 2, with memo filled
 * lazily:
 *   isPalindromeMemo(0,0) -> memo[0][0]=T
 *   isPalindromeMemo(1,1) -> memo[1][1]=T
 *   isPalindromeMemo(2,2) -> memo[2][2]=T
 *   isPalindromeMemo(0,1) -> s[0]==s[1] and base case T -> memo[0][1]=T
 *   isPalindromeMemo(1,2) -> 'a'!='b' -> memo[1][2]=F (short-circuit)
 *   isPalindromeMemo(0,2) -> 'a'!='b' -> memo[0][2]=F
 *
 * Final output: [["a","a","b"], ["aa","b"]]
 *
 * ------------------------------------------------------------
 * 7. EDGE CASES
 * ------------------------------------------------------------
 * | Edge Case                          | Input               | Expected Output                                | How Handled                                                   |
 * |--------------------------------------|----------------------|--------------------------------------------------|------------------------------------------------------------------|
 * | Single character                   | s = "a"              | [["a"]]                                        | Base case triggers after one single-char piece taken            |
 * | All identical characters           | s = "aaaa"           | All 2^(n-1)=8 partitions                       | Every prefix is a palindrome, max exponential blowup             |
 * | No palindrome longer than 1        | s = "abcde"          | [["a","b","c","d","e"]] only                   | Only single-char checks ever succeed                              |
 * | Already a single palindrome        | s = "racecar"        | Includes [["racecar"]] plus other splits       | dp[0][n-1] true, so "whole string" branch is valid too           |
 * | Two identical characters           | s = "aa"             | [["a","a"], ["aa"]]                            | Both 2-piece and 1-piece splits are valid                        |
 * | Maximum constraint length          | 16 same-letter chars | 2^15 = 32768 partitions                        | Demonstrates exponential output size, not just runtime           |
 *
 * Potential Pitfalls
 * WRONG: Forgetting to backtrack (remove the last element):
 *    currentPartition.add(piece);
 *    backtrack(...);
 *    // missing currentPartition.remove(...) -- corrupts future branches
 * CORRECT:
 *    currentPartition.add(piece);
 *    backtrack(...);
 *    currentPartition.remove(currentPartition.size() - 1);
 *
 * WRONG: Adding the live mutable list reference to result:
 *    result.add(currentPartition); // later mutations corrupt it
 * CORRECT:
 *    result.add(new ArrayList<>(currentPartition));
 *
 * WRONG: Mixing inclusive/exclusive bounds between substring() and dp table.
 * CORRECT: Be consistent -- dp[i][j] inclusive both ends, call
 *    s.substring(start, end + 1) to extract.
 *
 * WRONG: Filling DP table in wrong order, reading uninitialized
 * dp[i+1][j-1] values.
 * CORRECT: Always fill by increasing substring length.
 *
 * ------------------------------------------------------------
 * 8. SELF-CORRECTION & TESTING
 * ------------------------------------------------------------
 * Q: What edge cases might this miss?
 * A: Single-character strings and full-palindrome strings (like
 * "racecar") stress both the shortest-piece and longest-piece
 * branches and should be explicitly tested.
 *
 * Q: Are there any type mismatches?
 * A: Memoization requires Boolean[][] (boxed) rather than
 * boolean[][] (primitive) to represent the "not yet computed"
 * null state; a primitive array cannot represent "unknown".
 *
 * Q: How can I verify this works right now?
 *
 *    import java.util.*;
 *
 *    public class PalindromePartitioningVerify {
 *        public static void verify() {
 *            PalindromePartitioningDP solver = new PalindromePartitioningDP();
 *
 *            List<List<String>> result1 = solver.partition("aab");
 *            assert containsPartition(result1, List.of("a","a","b"));
 *            assert containsPartition(result1, List.of("aa","b"));
 *            assert result1.size() == 2;
 *
 *            List<List<String>> result2 = solver.partition("a");
 *            assert result2.size() == 1;
 *            assert containsPartition(result2, List.of("a"));
 *
 *            List<List<String>> result3 = solver.partition("aaa");
 *            assert result3.size() == 4;
 *
 *            System.out.println("All assertions passed.");
 *        }
 *
 *        private static boolean containsPartition(List<List<String>> result, List<String> target) {
 *            return result.contains(target);
 *        }
 *
 *        public static void main(String[] args) {
 *            verify();
 *        }
 *    }
 *
 * | Approach       | Risk                                              | Mitigation                                          |
 * |-----------------|----------------------------------------------------|-------------------------------------------------------|
 * | Brute Force     | Repeated O(n) scans blow up runtime on long strings| Switch to DP table/memo for n beyond ~12-14            |
 * | DP Table        | O(n^2) memory wasted on short strings              | Acceptable given small constraints (n <= 16)           |
 * | Memoization     | Boxed Boolean[][] overhead vs primitive boolean[][]| Use only when partial coverage meaningfully helps      |
 *
 * ------------------------------------------------------------
 * 9. COMPANIES & FREQUENCY
 * ------------------------------------------------------------
 * LeetCode Problem #131, Medium difficulty, frequently asked
 * backtracking problem.
 *
 * | Company        | Frequency (stars) | Notes                                                    |
 * |------------------|----------------------|-------------------------------------------------------------|
 * | Amazon          | ***** | Often paired with "min cuts" DP follow-up                  |
 * | Google          | ****  | Tests backtracking + pruning clarity                        |
 * | Microsoft       | ****  | Common in onsite recursion rounds                            |
 * | Facebook/Meta   | ****  | Sometimes asks for partition count only                     |
 * | Bloomberg       | ***   | Appears in string-manipulation interview sets                |
 * | Apple           | ***   | Seen in general SWE coding rounds                            |
 * | Adobe           | ***   | Common in early/mid-level interviews                         |
 * | Uber            | ***   | Tests backtracking template fluency                          |
 * | LinkedIn        | **    | Occasionally in phone screens                                |
 * | Oracle          | **    | Appears in general algorithm rounds                          |
 *
 * ------------------------------------------------------------
 * 10. FINAL SUMMARY
 * ------------------------------------------------------------
 * | Approach        | Time                         | Space            | Code Complexity | Recommended?                       |
 * |-------------------|---------------------------------|--------------------|--------------------|---------------------------------------|
 * | Brute Force      | O(n * 2^n)                    | O(n)              | Low               | best for low memory / small n        |
 * | DP Table         | O(n * 2^n), small constant    | O(n^2)            | Medium            | BEST OVERALL / time-optimal          |
 * | Memoization      | O(n * 2^n)                    | O(n^2) worst case | Medium            | acceptable, similar to DP table      |
 *
 * Recommended Approach: Use the DP-table backtracking solution by
 * default -- fastest practical runtime via O(1) palindrome lookups,
 * at a modest O(n^2) memory cost given the small constraints.
 * Fall back to brute force only if memory is the binding constraint.
 *
 * What to Remember: This is the canonical "backtracking with a
 * choice at every position" template -- try every valid next cut,
 * recurse, then undo before trying the next option. The key
 * optimization is precomputing palindrome validity in O(n^2) so
 * the exponential search never re-pays the cost of checking the
 * same substring twice.
 */
// @formatter:on
