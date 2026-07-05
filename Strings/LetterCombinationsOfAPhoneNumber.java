package Strings;

import java.util.ArrayList;
import java.util.List;

public class LetterCombinationsOfAPhoneNumber {
    public static void main(String[] args) {
        LetterCombinationsOfAPhoneNumber letterCombinationsOfAPhoneNumber = new LetterCombinationsOfAPhoneNumber();
        System.out.println(
                "LetterCombinationsOfAPhoneNumber : "
                        + letterCombinationsOfAPhoneNumber.letterCombinationsBackTrackingDFS("23"));
        System.out.println(
                "LetterCombinationsOfAPhoneNumber : "
                        + letterCombinationsOfAPhoneNumber.letterCombinationsIterativeBFS("23"));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/letter-combinations-of-a-phone-number/description/
     * 
     * Given a string containing digits from 2-9 inclusive, return all possible
     * letter combinations that the number could represent. Return the answer in any
     * order.
     * 
     * A mapping of digits to letters (just like on the telephone buttons) is given
     * below. Note that 1 does not map to any letters.
     * 
     * Telephone Keypad Digit-to-Letter Mapping:
     * 2 -> "a", "b", "c"
     * 3 -> "d", "e", "f"
     * 4 -> "g", "h", "i"
     * 5 -> "j", "k", "l"
     * 6 -> "m", "n", "o"
     * 7 -> "p", "q", "r", "s"
     * 8 -> "t", "u", "v"
     * 9 -> "w", "x", "y", "z"
     * 
     * Note: 0 and 1 do not map to any letters.
     * Example 1:
     * 
     * Input: digits = "23"
     * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
     * Example 2:
     * 
     * Input: digits = "2"
     * Output: ["a","b","c"]
     * 
     * 
     * Constraints:
     * 
     * 1 <= digits.length <= 4
     * digits[i] is a digit in the range ['2', '9'].
     */
    // @formatter:on

    private static final String[] PHONE_MAP = {
            "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
    };

    public List<String> letterCombinationsBackTrackingDFS(String digits) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        backTrack(digits, sb, 0, result);
        return result;
    }

    public void backTrack(String digits, StringBuilder sb, int startPosition, List<String> result) {
        if (startPosition == digits.length()) {
            result.add(sb.toString());
            return;
        }

        String letters = PHONE_MAP[digits.charAt(startPosition) - '0'];
        for (char letter : letters.toCharArray()) {
            sb.append(letter);
            backTrack(digits, sb, startPosition + 1, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public List<String> letterCombinationsIterativeBFS(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.isEmpty())
            return result;
        String[] phoneMap = { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
        result.add("");

        for (char digit : digits.toCharArray()) {
            String letters = phoneMap[digit - '0'];
            List<String> expanded = new ArrayList<>();
            for (String combo : result) {
                for (char letter : letters.toCharArray()) {
                    expanded.add(combo + letter);
                }
            }
            result = expanded;
        }
        return result;
    }
}

// @formatter:off
/*
 * ============================================================
 * Letter Combinations of a Phone Number — Deep Dive Explanation
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given a string of digits (from '2' to '9'), return all possible letter
 * combinations that those digits could represent on a classic T9 telephone
 * keypad. Each digit maps to a set of letters, just like on a phone. You must
 * generate every possible string formed by picking one letter per digit, in
 * all possible orderings.
 *
 * LeetCode #17 — Medium
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * - String digits — a string containing digits '2' through '9', possibly empty
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * - List<String> — all possible letter combinations (order does not matter)
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * - 0 <= digits.length <= 4
 * - Each character in digits is in the range ['2', '9']
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * For each digit, look up its corresponding letters using the phone mapping:
 *   2 → abc    3 → def    4 → ghi    5 → jkl
 *   6 → mno    7 → pqrs   8 → tuv    9 → wxyz
 * Then generate the Cartesian product of all letter sets, where each product
 * element is concatenated into a string.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *   Input:  digits = "23"
 *   Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * Digit '2' → {a,b,c}, Digit '3' → {d,e,f} → all 3×3 = 9 combinations.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Think of it as building a word letter by letter. For each digit, you branch
 * into all letters it could be. You're exploring a decision tree — at each
 * level you commit to one letter for the current digit, then move to the next.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Start with an empty string.
 * 2. Look at the first digit — try each of its letters.
 * 3. For each choice, look at the second digit — try each of its letters appended.
 * 4. Continue until you've used all digits → record that as a valid combination.
 * 5. Backtrack and try the next option.
 *
 * This is exactly what recursion with backtracking does.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                    | Why it's tricky                                     |
 * |------------------------------|-----------------------------------------------------|
 * | Digit-to-letter mapping      | Digit '7' has 4 letters (pqrs), '9' has 4 (wxyz)   |
 * | Empty input                  | Must return [], not [""]                            |
 * | Building strings efficiently | StringBuilder + backtracking avoids extra allocs    |
 * | Iterative vs recursive model | BFS approach is less intuitive but equally valid    |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                   | Key Idea                              | Best Used When                        | Time       | Space           |
 * |---|----------------------------|---------------------------------------|---------------------------------------|------------|-----------------|
 * | 1 | Brute Force (nested loops) | One loop per digit, hardcoded nesting | Only for fixed-length input (e.g., 2) | O(4^n · n) | O(4^n · n)      |
 * | 2 | Iterative BFS / Queue      | Expand all combos level by level      | Prefer iterative style                | O(4^n · n) | O(4^n · n)      |
 * | 3 | Backtracking DFS ✅         | Recursive build with undo step        | General, clean, interview-standard    | O(4^n · n) | O(n) aux stack  |
 *
 * Trade-off: All approaches share the same output size. The key differentiator
 * is auxiliary space. Backtracking DFS uses only O(n) working memory (shared
 * StringBuilder + call stack), while BFS materializes all intermediate combos
 * in a queue at once costing O(4^n · n) working memory. For n ≤ 4 it doesn't
 * matter in practice, but backtracking is cleanest and most scalable.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (Nested Loops — Fixed Length 2)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Look up the letters for digit 1 and digit 2.
 * 2. Use two nested loops to try all (letter1, letter2) pairs.
 * 3. Append each pair to the result list.
 *
 * ⚠️ Only works when digits.length() == 2. Shown for contrast.
 *
 *    import java.util.*;
 *    public class LetterCombinationsBrute {
 *        public List<String> letterCombinations(String digits) {
 *            List<String> result = new ArrayList<>();
 *            if (digits == null || digits.isEmpty()) return result;
 *            String[] phoneMap = {"","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
 *            String letters1 = phoneMap[digits.charAt(0) - '0'];
 *            String letters2 = phoneMap[digits.charAt(1) - '0'];
 *            for (char c1 : letters1.toCharArray()) {
 *                for (char c2 : letters2.toCharArray()) {
 *                    result.add("" + c1 + c2);
 *                }
 *            }
 *            return result;
 *        }
 *        public static void main(String[] args) {
 *            LetterCombinationsBrute sol = new LetterCombinationsBrute();
 *            System.out.println(sol.letterCombinations("23"));
 *            // Expected: [ad, ae, af, bd, be, bf, cd, ce, cf]
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 2: Iterative BFS (Queue)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Initialize result with one empty string "".
 * 2. For each digit, expand every existing combo with all new letters.
 * 3. Replace the list with the expanded combos.
 * 4. Return the final list.
 *
 *    import java.util.*;
 *    public class LetterCombinationsBFS {
 *        public List<String> letterCombinations(String digits) {
 *            List<String> result = new ArrayList<>();
 *            if (digits == null || digits.isEmpty()) return result;
 *            String[] phoneMap = {"","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
 *            result.add("");
 *            for (char digit : digits.toCharArray()) {
 *                String letters = phoneMap[digit - '0'];
 *                List<String> expanded = new ArrayList<>();
 *                for (String combo : result) {
 *                    for (char letter : letters.toCharArray()) {
 *                        expanded.add(combo + letter);
 *                    }
 *                }
 *                result = expanded;
 *            }
 *            return result;
 *        }
 *        public static void main(String[] args) {
 *            LetterCombinationsBFS sol = new LetterCombinationsBFS();
 *            System.out.println(sol.letterCombinations("23")); // [ad,ae,af,bd,be,bf,cd,ce,cf]
 *            System.out.println(sol.letterCombinations(""));   // []
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 3: Backtracking DFS ✅ (Optimal)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Create the phone map.
 * 2. If digits is empty, return empty list immediately.
 * 3. Define backtrack(index, current):
 *    - Base case: if index == digits.length(), add current.toString() to result.
 *    - Recursive: for each letter in phoneMap[digits[index]]:
 *        a. Append the letter to current (StringBuilder)
 *        b. Recurse with index + 1
 *        c. Remove the last character (backtrack)
 * 4. Call backtrack(0, new StringBuilder()).
 *
 *    import java.util.*;
 *    public class LetterCombinationsBacktrack {
 *        private static final String[] PHONE_MAP = {
 *            "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
 *        };
 *        public List<String> letterCombinations(String digits) {
 *            List<String> result = new ArrayList<>();
 *            if (digits == null || digits.isEmpty()) return result;
 *            backtrack(digits, 0, new StringBuilder(), result);
 *            return result;
 *        }
 *        private void backtrack(String digits, int index, StringBuilder current, List<String> result) {
 *            if (index == digits.length()) {
 *                result.add(current.toString());
 *                return;
 *            }
 *            String letters = PHONE_MAP[digits.charAt(index) - '0'];
 *            for (char letter : letters.toCharArray()) {
 *                current.append(letter);                              // choose
 *                backtrack(digits, index + 1, current, result);      // explore
 *                current.deleteCharAt(current.length() - 1);         // un-choose
 *            }
 *        }
 *        public static void main(String[] args) {
 *            LetterCombinationsBacktrack sol = new LetterCombinationsBacktrack();
 *            System.out.println(sol.letterCombinations("23")); // [ad,ae,af,bd,be,bf,cd,ce,cf]
 *            System.out.println(sol.letterCombinations("2"));  // [a, b, c]
 *            System.out.println(sol.letterCombinations(""));   // []
 *        }
 *    }
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (2-digit fixed)
 * ------------------------------------------------------------
 * Time:  O(1) — fixed 2 loops, at most 4×4 = 16 iterations
 * Space: O(1) auxiliary — result size is bounded by a constant
 *
 * ------------------------------------------------------------
 * Approach 2: Iterative BFS
 * ------------------------------------------------------------
 * Time:  O(4^n · n)
 *   At each of the n digit levels, every existing string (up to 4^(n-1)) is
 *   extended by up to 4 letters. Total work = n · 4^n.
 *   For n=4: 4 · 256 = 1,024 string operations.
 * Space: O(4^n · n)
 *   The intermediate expanded list holds up to 4^n strings of length up to n.
 *   All partial results exist in memory simultaneously.
 *
 * ------------------------------------------------------------
 * Approach 3: Backtracking DFS ✅
 * ------------------------------------------------------------
 * Time:  O(4^n · n)
 *   Same total number of combinations. Each leaf-node write costs O(n) to
 *   copy the StringBuilder → n · 4^n total.
 * Space:
 *   Auxiliary (call stack + StringBuilder): O(n) — recursion depth = digits.length()
 *   Output: O(4^n · n) — unavoidable; this is the size of the answer
 *   Advantage: only O(n) working memory vs BFS's O(4^n · n) working memory
 *
 * Concrete estimate (n=4, worst case digit 7/9):
 *   Total combinations: 4^4 = 256
 *   Each string length:  4
 *   Total chars written: 1,024
 *   DFS call-stack depth: 4 frames
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 2: BFS — digits = "23"
 * ------------------------------------------------------------
 * Start:  result = [""]
 *
 * --- Process digit '2' (letters = "abc") ---
 *   "" + 'a' → "a"
 *   "" + 'b' → "b"
 *   "" + 'c' → "c"
 *   result = ["a", "b", "c"]
 *
 * --- Process digit '3' (letters = "def") ---
 *   "a" + 'd' → "ad"
 *   "a" + 'e' → "ae"
 *   "a" + 'f' → "af"
 *   "b" + 'd' → "bd"
 *   "b" + 'e' → "be"
 *   "b" + 'f' → "bf"
 *   "c" + 'd' → "cd"
 *   "c" + 'e' → "ce"
 *   "c" + 'f' → "cf"
 *   result = ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * ------------------------------------------------------------
 * Approach 3: Backtracking DFS — digits = "23"
 * ------------------------------------------------------------
 * backtrack(index=0, current="")
 * ├─ letter='a' → current="a"
 * │   backtrack(index=1, current="a")
 * │   ├─ letter='d' → current="ad"
 * │   │   backtrack(index=2) → BASE CASE → add "ad" ✅
 * │   │   backtrack → current="a"
 * │   ├─ letter='e' → current="ae"
 * │   │   backtrack(index=2) → BASE CASE → add "ae" ✅
 * │   │   backtrack → current="a"
 * │   └─ letter='f' → current="af"
 * │       backtrack(index=2) → BASE CASE → add "af" ✅
 * │       backtrack → current="a"
 * │   backtrack → current=""
 * ├─ letter='b' → current="b"
 * │   backtrack(index=1, current="b")
 * │   ├─ ... → add "bd", "be", "bf" ✅
 * │   backtrack → current=""
 * └─ letter='c' → current="c"
 *     backtrack(index=1, current="c")
 *     ├─ ... → add "cd", "ce", "cf" ✅
 *     backtrack → current=""
 *
 * Final result: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case               | Input  | Expected Output              | How Handled                           |
 * |-------------------------|--------|------------------------------|---------------------------------------|
 * | Empty string            | ""     | []                           | Early return check at top of method   |
 * | Single digit            | "2"    | ["a","b","c"]                | Base case fires after one recursion   |
 * | Digit with 4 letters    | "7"    | ["p","q","r","s"]            | PHONE_MAP[7]="pqrs", loop runs 4x     |
 * | Max length              | "9999" | 256 combinations             | Recursion depth=4, handled uniformly  |
 * | Digit '0' or '1'        | n/a    | (outside constraints)        | Constraints guarantee '2'–'9' only    |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 — Returning [""] instead of [] for empty input:
 *   ❌ WRONG: result.add(""); return result; // returns [""] when digits=""
 *   ✅ CORRECT: if (digits.isEmpty()) return result; // guard first
 *
 * Pitfall 2 — Using String += instead of StringBuilder:
 *   ❌ WRONG: backtrack(digits, index + 1, current + letter, result);
 *   ✅ CORRECT:
 *     current.append(letter);
 *     backtrack(digits, index + 1, current, result);
 *     current.deleteCharAt(current.length() - 1);
 *
 * Pitfall 3 — Off-by-one in phone map array:
 *   ❌ WRONG: String[] map = {"abc","def",...}; // index 2 → "abc" won't align
 *   ✅ CORRECT: String[] map = {"","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The most common miss is returning [""] for empty input. Always guard with
 *    if (digits.isEmpty()) return result before seeding any state.
 *
 * Q: Are there any type mismatches?
 * A: digits.charAt(i) - '0' yields an int used as array index — correct since
 *    char arithmetic in Java produces int. PHONE_MAP is a String[], and iterating
 *    with .toCharArray() returns char[] — all type-safe.
 *
 * Q: How can I verify this works right now?
 *
 *    public static void verify() {
 *        LetterCombinationsBacktrack sol = new LetterCombinationsBacktrack();
 *        List<String> r1 = sol.letterCombinations("23");
 *        assert r1.size() == 9 : "Expected 9 combinations for '23'";
 *        assert r1.containsAll(Arrays.asList("ad","ae","af","bd","be","bf","cd","ce","cf"));
 *        List<String> r2 = sol.letterCombinations("");
 *        assert r2.isEmpty() : "Expected empty list for empty input";
 *        List<String> r3 = sol.letterCombinations("2");
 *        assert r3.equals(Arrays.asList("a","b","c")) : "Expected [a,b,c]";
 *        List<String> r4 = sol.letterCombinations("7");
 *        assert r4.size() == 4 : "Expected 4 combinations for '7'";
 *        System.out.println("All assertions passed ✅");
 *    }
 *
 * | Approach           | Risk                                      | Mitigation                            |
 * |--------------------|-------------------------------------------|---------------------------------------|
 * | Brute Force        | Only works for fixed digit count          | Not suitable for general input        |
 * | BFS Iterative      | High memory use on wide inputs            | Fine for n ≤ 4; not scalable beyond   |
 * | Backtracking DFS ✅ | Subtle backtrack step can be forgotten   | Always append before, delete after    |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * | Company            | Frequency | Notes                              |
 * |--------------------|-----------|-------------------------------------|
 * | Amazon             | ⭐⭐⭐⭐⭐    | Extremely common in phone screens   |
 * | Google             | ⭐⭐⭐⭐⭐    | Classic backtracking warm-up        |
 * | Microsoft          | ⭐⭐⭐⭐      | Frequently tests recursion          |
 * | Meta (Facebook)    | ⭐⭐⭐⭐      | Often asked as an opener            |
 * | Apple              | ⭐⭐⭐       | Phone screen staple                 |
 * | Bloomberg          | ⭐⭐⭐       | Common in first rounds              |
 * | Uber               | ⭐⭐⭐       | Tests Cartesian product reasoning   |
 * | Adobe              | ⭐⭐         | Occasionally seen                   |
 * | Salesforce         | ⭐⭐         | Used in mid-level interviews        |
 * | LinkedIn           | ⭐⭐         | Part of backtracking question sets  |
 *
 * LeetCode #17 · Medium · ~1.5 million total submissions
 * Top 10 most commonly asked medium-level problems
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach           | Time       | Space (Aux) | Code Complexity | Recommended?                     |
 * |--------------------|------------|-------------|-----------------|----------------------------------|
 * | Brute Force        | O(1) fixed | O(1)        | Low             | ❌ Only for fixed-length input   |
 * | BFS Iterative      | O(4^n · n) | O(4^n · n)  | Medium          | ✅ Acceptable, iterative style   |
 * | Backtracking DFS   | O(4^n · n) | O(n)        | Medium          | ✅✅ Best overall                |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Backtracking DFS — shares the same time complexity as BFS but uses only O(n)
 * auxiliary memory (one shared StringBuilder + call stack depth of n). It also
 * directly expresses the recursive structure of the problem.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * 1. This problem is the textbook backtracking template: choose → explore → un-choose.
 *    Memorize: append / recurse / deleteCharAt as the three-line core.
 * 2. The phone map array has 10 slots (indices 0–9); indices 0 and 1 are empty
 *    strings — always account for this offset.
 * 3. Key Gotcha: return an empty list (not [""]) when digits is empty — seeding
 *    with "" and not guarding is the most common interview mistake on this problem.
 */
// @formatter:on
