package Strings;

import java.util.ArrayDeque;
import java.util.Deque;

public class DecodeString {
    public static void main(String[] args) {
        DecodeString decodeString = new DecodeString();
        System.out.println("DecodeString : " + decodeString.decodeStringTwoStacks("3[a]2[bc]")); // aaabcbc
        System.out.println("DecodeString : " + decodeString.decodeStringTwoStacks("3[a2[c]]")); // accaccacc
        System.out.println("DecodeString : " + decodeString.decodeStringTwoStacks("2[abc]3[cd]ef")); // abcabccdcdcdef
        System.out.println("DecodeString : " + decodeString.decodeStringTwoStacks("100[leetcode]").length()); // 800
        System.out.println("DecodeString : " + decodeString.decodeStringRecursive("3[a]2[bc]")); // aaabcbc
        System.out.println("DecodeString : " + decodeString.decodeStringRecursive("3[a2[c]]")); // accaccacc
        System.out.println("DecodeString : " + decodeString.decodeStringRecursive("2[abc]3[cd]ef")); // abcabccdcdcdef
        System.out.println("DecodeString : " + decodeString.decodeStringRecursive("100[leetcode]").length()); // 800
        System.out.println("DecodeString : " + decodeString.decodeStringBruteForce("3[a]2[bc]")); // aaabcbc
        System.out.println("DecodeString : " + decodeString.decodeStringBruteForce("3[a2[c]]")); // accaccacc
        System.out.println("DecodeString : " + decodeString.decodeStringBruteForce("2[abc]3[cd]ef")); // abcabccdcdcdef
        System.out.println("DecodeString : " + decodeString.decodeStringBruteForce("100[leetcode]").length()); // 800
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/decode-string/description/
     * 
     * Given an encoded string, return its decoded string.
     * 
     * The encoding rule is: k[encoded_string], where the encoded_string inside the
     * square brackets is being repeated exactly k times. Note that k is guaranteed
     * to be a positive integer.
     * 
     * You may assume that the input string is always valid; there are no extra
     * white spaces, square brackets are well-formed, etc. Furthermore, you may
     * assume that the original data does not contain any digits and that digits are
     * only for those repeat numbers, k. For example, there will not be input like
     * 3a or 2[4].
     * 
     * The test cases are generated so that the length of the output will never
     * exceed 105.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: s = "3[a]2[bc]"
     * Output: "aaabcbc"
     * Example 2:
     * 
     * Input: s = "3[a2[c]]"
     * Output: "accaccacc"
     * Example 3:
     * 
     * Input: s = "2[abc]3[cd]ef"
     * Output: "abcabccdcdcdef"
     * 
     * 
     * Constraints:
     * 
     * 1 <= s.length <= 30
     * s consists of lowercase English letters, digits, and square brackets '[]'.
     * s is guaranteed to be a valid input.
     * All the integers in s are in the range [1, 300].
     */
    // @formatter:on

    /*
     * O(L) ✅ time-optimal O(L) ✅ space-optimal
     */
    public String decodeStringTwoStacks(String s) {
        Deque<Integer> countStack = new ArrayDeque<>();
        Deque<StringBuilder> stringStack = new ArrayDeque<>();
        StringBuilder current = new StringBuilder();
        int repeatCount = 0;

        for (char currentChar : s.toCharArray()) {
            if (Character.isDigit(currentChar)) {
                repeatCount = repeatCount * 10 + (currentChar - '0');
            } else if (currentChar == '[') {
                countStack.push(repeatCount);
                stringStack.push(current);
                repeatCount = 0;
                current = new StringBuilder();
            } else if (currentChar == ']') {
                StringBuilder prefix = stringStack.pop();
                int timeToRepeat = countStack.pop();
                for (int i = 0; i < timeToRepeat; i++) {
                    prefix.append(current);
                }
                current = prefix;
            } else {
                current.append(currentChar);
            }
        }
        return current.toString();
    }

    /*
     * O(L) O(L + d) — O(L) output + O(d) call stack
     */
    private int index;

    public String decodeStringRecursive(String s) {
        index = 0;
        return decodeFrom(s);
    }

    private String decodeFrom(String s) {
        StringBuilder builder = new StringBuilder();
        while (index < s.length() && s.charAt(index) != ']') {
            char currentChar = s.charAt(index);
            if (Character.isDigit(currentChar)) {
                int repeatCount = 0;
                while (Character.isDigit(s.charAt(index))) {
                    repeatCount = repeatCount * 10 + (s.charAt(index) - '0');
                    index++;
                }
                index++;
                String innerText = decodeFrom(s);
                index++;
                for (int i = 0; i < repeatCount; i++) {
                    builder.append(innerText);
                }
            } else {
                builder.append(currentChar);
                index++;
            }
        }
        return builder.toString();
    }

    /*
     * O(L²) O(L)
     */
    public String decodeStringBruteForce(String s) {
        String current = s;
        while (current.indexOf("[") != -1) {
            int closeIndex = current.indexOf("]");
            int openIndex = current.lastIndexOf("[", closeIndex);
            int countStart = openIndex - 1;
            while (countStart >= 0 && Character.isDigit(current.charAt(countStart))) {
                countStart--;
            }
            countStart++;
            int repeatCount = Integer.parseInt(current.substring(countStart, openIndex));
            String innerText = current.substring(openIndex + 1, closeIndex);

            StringBuilder expanded = new StringBuilder();
            for (int i = 0; i < repeatCount; i++) {
                expanded.append(innerText);
            }

            current = current.substring(0, countStart) + expanded + current.substring(closeIndex + 1);
        }
        return current;
    }
}

// @formatter:off
/*
 * ============================================================
 * DECODE STRING - DEEP DIVE EXPLANATION
 * LeetCode #394 | Difficulty: Medium | Language: Java
 * ============================================================
 *
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 *
 * You are given an encoded string that compresses repeated substrings using the
 * pattern k[encoded_string], meaning "repeat encoded_string exactly k times."
 * These patterns can be NESTED inside one another to arbitrary depth, and plain
 * letters can sit freely between them. Your job is to expand the encoding back
 * into the original plain string.
 *
 * The encoding is guaranteed to be well-formed: brackets are always balanced, k
 * is always a valid positive integer, and there are never stray digits outside a
 * repeat count (so you'll never see something like 3a or 2[4]).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 *
 * | Item | Type   | Description                                |
 * |------|--------|--------------------------------------------|
 * | s    | String | The encoded string, e.g. "3[a2[c]]"        |
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 *
 * | Item   | Type   | Description                                        |
 * |--------|--------|----------------------------------------------------|
 * | return | String | The fully decoded/expanded string, e.g. "accaccacc"|
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 *
 *   - 1 <= s.length <= 30
 *   - s consists of lowercase English letters, digits, and the characters [ and ]
 *   - s is guaranteed to be a VALID encoding
 *   - All integers k satisfy 1 <= k <= 300
 *   - The DECODED string is guaranteed to fit in a standard String
 *     (at most ~10^5 characters in the official test set)
 *
 * NOTE the asymmetry that defines this problem: the INPUT is tiny (<= 30 chars)
 * but the OUTPUT can be enormous. "300[300[ab]]" would be 180,000 characters.
 * Complexity must be expressed in terms of the OUTPUT length, not the input length.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 *
 * Recursively expand every k[...] group, innermost-first, concatenating the
 * results with any surrounding literal letters, and return the final flat string.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *
 * | Input           | Output           | Reading                          |
 * |-----------------|------------------|----------------------------------|
 * | "3[a]2[bc]"     | "aaabcbc"        | a x3, then bc x2                 |
 * | "3[a2[c]]"      | "accaccacc"      | inner c x2 = cc -> acc -> x3     |
 * | "2[abc]3[cd]ef" | "abcabccdcdcdef" | groups plus trailing literal ef  |
 *
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 *
 * Think of the encoded string as a set of RUSSIAN NESTING DOLLS. Each k[ opens a
 * doll, and each ] closes it. You cannot know what a doll contains until you have
 * opened every doll inside it. So you keep opening - pushing each outer doll
 * aside, half-finished - until you hit the innermost one. That one has nothing but
 * letters inside, so you can finish it immediately. Then you hand its result back
 * to the doll that was waiting outside it, which multiplies it and continues.
 *
 * That phrase - "set the outer work aside, finish the inner work, hand the result
 * back outward" - is the literal definition of a STACK. Every bracket-matching
 * problem in existence is secretly this same shape.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 *
 * Decoding "3[a2[c]]" by hand:
 *
 *   1. Read 3      -> remember: whatever comes in the next bracket repeats 3 times.
 *   2. See [       -> open a doll. Set aside the number 3 and the (empty) text so far.
 *   3. Read a      -> start building "a" inside this doll.
 *   4. Read 2      -> remember: the next bracket repeats 2 times.
 *   5. See [       -> open another doll. Set aside the number 2 and the text "a".
 *   6. Read c      -> build "c" inside the inner doll.
 *   7. See ]       -> close the inner doll. Take "c" x 2 = "cc", retrieve the
 *                     set-aside "a", glue them: "acc".
 *   8. See ]       -> close the outer doll. Take "acc" x 3 = "accaccacc", retrieve
 *                     the set-aside "", glue: "accaccacc".
 *   9. String exhausted -> answer is "accaccacc".
 *
 * Step 7 is the heart of it: when a ] arrives, the thing you multiply is ONLY the
 * text built since the matching [ - not everything you've accumulated. That is
 * exactly why the prefix "a" had to be pushed away in step 5 and reclaimed in step 7.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 *
 * | Challenge                       | Why it's tricky                                                                                                                              |
 * |---------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------|
 * | Nesting is unbounded            | You can't handle it with a fixed number of variables or a single pass of regex; you need a structure that grows with depth (stack or call stack). |
 * | Multi-digit repeat counts       | 12[ab] means 12 times, not 1 then 2. You must consume digits greedily with k = k*10 + d rather than reading one character.                    |
 * | Two things must be saved per [  | Beginners save only the count. You must ALSO save the partially-built prefix ("a" in the trace above), or "3[a2[c]]" wrongly becomes garbage. |
 * | Literals can appear anywhere    | Before, after, and between groups ("2[abc]3[cd]ef"), so you can't assume the string is a clean sequence of brackets.                          |
 * | Output >> input                 | The 30-char input limit lulls you into thinking O(n^2) on input is fine - but the real cost driver is the DECODED length.                     |
 * | Naive string concatenation      | result += inner inside a repeat loop copies the whole string each time, silently turning a linear algorithm quadratic. StringBuilder is not optional. |
 *
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                                  | Key Idea                                                                                                 | Best Used When                                                                        | Time Complexity   | Space Complexity                              |
 * |---|-------------------------------------------|----------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------|-------------------|-----------------------------------------------|
 * | 1 | Brute Force - Repeated Innermost Expansion| Repeatedly locate the first ], find its matching [, expand that one group in place, restart the scan      | Tiny inputs; when you want a solution you can reason about with zero data structures   | O(L^2)            | O(L)                                          |
 * | 2 | Recursive Descent (shared index)          | Let the call stack mirror the bracket nesting; each call decodes until it hits its own ] and returns      | The nesting is shallow and you want the code that most closely mirrors the grammar     | O(L)              | O(L + d) - O(L) output + O(d) call stack      |
 * | 3 | Two Stacks (iterative) ✅                  | Explicit countStack + stringStack; push state on [, pop-and-multiply on ]                                 | GENERAL CASE - the production answer. Linear time, no call stack, robust to deep nesting| O(L) ✅ time-optimal | O(L) ✅ space-optimal                         |
 *
 * Where L = length of the DECODED output, d = maximum nesting depth.
 *
 * THE TRADE-OFF. Approach 1 is quadratic for one reason: every expansion rebuilds
 * the entire string from scratch via substring + concatenation, so a string that
 * grows to length L gets copied on the order of L times. It is correct and easy to
 * explain, but it does redundant work by re-scanning material it has already decoded.
 *
 * Approaches 2 and 3 both fix this by touching each output character exactly once -
 * both are O(L) time, and neither can be beaten, since you must at minimum WRITE L
 * characters. So the tiebreak is space. Both need O(L) to hold the answer, which is
 * unavoidable and therefore not a real differentiator; the actual difference is the
 * AUXILIARY structure. Recursion consumes O(d) frames of JVM CALL STACK, which is a
 * fixed, non-growable resource - a deeply nested input like 1[1[1[1[...]]]] throws
 * StackOverflowError rather than degrading gracefully. The two-stack version keeps
 * that same O(d) bookkeeping on the HEAP, where it is bounded only by available memory.
 *
 * So Approach 3 wins on BOTH axes and is the unambiguous recommendation: identical
 * O(L) time, identical asymptotic space, but strictly more robust in practice.
 * Approach 2 remains worth knowing - it's the more elegant expression of the grammar
 * and is perfectly safe within LeetCode's d <= 15-ish limits. Reach for Approach 1
 * only as a whiteboard warm-up.
 *
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force - Repeated Innermost Expansion
 * ------------------------------------------------------------
 *
 * Algorithm:
 *   1. Loop as long as the string still contains a [.
 *   2. Find the index of the FIRST ]. Because it is the first one, the bracket
 *      group it closes is guaranteed to contain no nested brackets - it is an
 *      innermost group.
 *   3. Scan BACKWARDS from that ] to find the nearest preceding [. That is its
 *      matching open bracket.
 *   4. From the open bracket, walk backwards over consecutive digits to find where
 *      the repeat count starts.
 *   5. Parse the count k and extract the literal text between the brackets.
 *   6. Build the expanded text (inner repeated k times) with a StringBuilder.
 *   7. Rewrite the string as prefix + expanded + suffix, deleting the brackets and
 *      the count.
 *   8. Repeat. Each iteration removes exactly one bracket pair, so the loop terminates.
 *   9. When no [ remains, the string is fully decoded.
 *
 *    public class DecodeStringBruteForce {
 *
 *        public static String decodeString(String encoded) {
 *            String current = encoded;
 *
 *            // Each iteration eliminates exactly one innermost bracket pair.
 *            while (current.indexOf('[') != -1) {
 *                int closeIndex = current.indexOf(']');           // first ']' closes an innermost group
 *                int openIndex  = current.lastIndexOf('[', closeIndex);
 *
 *                // Walk backwards over the digits that form the repeat count.
 *                int countStart = openIndex - 1;
 *                while (countStart >= 0 && Character.isDigit(current.charAt(countStart))) {
 *                    countStart--;
 *                }
 *                countStart++;  // step forward onto the first digit
 *
 *                int repeatCount = Integer.parseInt(current.substring(countStart, openIndex));
 *                String innerText = current.substring(openIndex + 1, closeIndex);
 *
 *                StringBuilder expanded = new StringBuilder();
 *                for (int i = 0; i < repeatCount; i++) {
 *                    expanded.append(innerText);
 *                }
 *
 *                current = current.substring(0, countStart)
 *                        + expanded
 *                        + current.substring(closeIndex + 1);
 *            }
 *
 *            return current;
 *        }
 *
 *        public static void main(String[] args) {
 *            System.out.println(decodeString("3[a]2[bc]"));      // aaabcbc
 *            System.out.println(decodeString("3[a2[c]]"));       // accaccacc
 *            System.out.println(decodeString("2[abc]3[cd]ef"));  // abcabccdcdcdef
 *            System.out.println(decodeString("abc"));            // abc
 *        }
 *    }
 *
 * Non-obvious details. The claim in step 2 - the first ] always closes an innermost
 * group - is what makes this work without any matching logic. If that group contained
 * a nested group, the nested group's ] would appear earlier, contradicting "first."
 * The countStart++ after the backward digit walk is the classic off-by-one trap: the
 * loop exits one position BEFORE the first digit (or at -1), so you must step forward
 * onto it.
 *
 * ------------------------------------------------------------
 * Approach 2: Recursive Descent (shared index)
 * ------------------------------------------------------------
 *
 * Algorithm:
 *   1. Keep a single SHARED CURSOR index that every recursive call advances. It is
 *      never reset - it marches monotonically through the input exactly once. This
 *      is what keeps the algorithm linear.
 *   2. Define decodeFrom(s): build and return the decoded text for the current
 *      bracket level.
 *   3. Loop while the cursor is in bounds AND the current character is not ]
 *      (a ] means "this level is done - return to my caller").
 *   4. If the character is a digit: consume all consecutive digits into repeatCount
 *      via k = k*10 + digit.
 *   5. Skip the [, then RECURSE to decode the contents of that bracket.
 *   6. Skip the ] that the recursive call stopped on.
 *   7. Append the returned text repeatCount times to this level's builder.
 *   8. Otherwise (a plain letter): append it and advance the cursor by one.
 *   9. Return the builder's contents. The top-level call terminates by running out
 *      of input rather than by hitting a ].
 *
 *    public class DecodeStringRecursive {
 *
 *        private int index;  // shared cursor - advances monotonically, never rewinds
 *
 *        public String decodeString(String encoded) {
 *            index = 0;
 *            return decodeFrom(encoded);
 *        }
 *
 *        private String decodeFrom(String s) {
 *            StringBuilder builder = new StringBuilder();
 *
 *            // Stop at ']' (my caller's business) or at end of input (top level).
 *            while (index < s.length() && s.charAt(index) != ']') {
 *                char currentChar = s.charAt(index);
 *
 *                if (Character.isDigit(currentChar)) {
 *                    int repeatCount = 0;
 *                    while (Character.isDigit(s.charAt(index))) {
 *                        repeatCount = repeatCount * 10 + (s.charAt(index) - '0');
 *                        index++;
 *                    }
 *                    index++;                              // skip '['
 *                    String innerText = decodeFrom(s);     // decode nested level
 *                    index++;                              // skip the ']' we stopped on
 *
 *                    for (int i = 0; i < repeatCount; i++) {
 *                        builder.append(innerText);
 *                    }
 *                } else {
 *                    builder.append(currentChar);
 *                    index++;
 *                }
 *            }
 *
 *            return builder.toString();
 *        }
 *
 *        public static void main(String[] args) {
 *            DecodeStringRecursive solver = new DecodeStringRecursive();
 *            System.out.println(solver.decodeString("3[a]2[bc]"));      // aaabcbc
 *            System.out.println(solver.decodeString("3[a2[c]]"));       // accaccacc
 *            System.out.println(solver.decodeString("2[abc]3[cd]ef"));  // abcabccdcdcdef
 *            System.out.println(solver.decodeString("10[ab]"));         // 20 chars
 *        }
 *    }
 *
 * Non-obvious details. index MUST be a field, not a parameter - Java passes int by
 * value, so a parameter version would lose the callee's progress on return and
 * re-scan the same characters. The repeatCount * 10 + (c - '0') idiom is standard
 * digit accumulation: '7' - '0' == 7 because the ASCII digits are contiguous. Note
 * the inner while (Character.isDigit(s.charAt(index))) needs no bounds check only
 * because the input is guaranteed valid - a digit is always followed by at least a
 * [, so we can never run off the end mid-number.
 *
 * ------------------------------------------------------------
 * Approach 3: Two Stacks (iterative) ✅ OPTIMAL
 * ------------------------------------------------------------
 *
 * Algorithm:
 *   1. Maintain four pieces of state:
 *        countStack  - repeat counts of every bracket level currently open
 *        stringStack - the text built BEFORE each currently-open bracket
 *        current     - the text being built at the current level
 *        repeatCount - the number being accumulated right now
 *   2. Scan the input left to right, one character, branching on its type:
 *   3. DIGIT -> repeatCount = repeatCount * 10 + digit. (Do not reset; multi-digit
 *      numbers arrive one char at a time.)
 *   4. [ -> a new level opens. Push repeatCount onto countStack and current onto
 *      stringStack, then reset both to empty. The prefix is now safely parked and
 *      cannot be wrongly multiplied.
 *   5. ] -> the current level closes. Pop the saved prefix and the saved count,
 *      append current to the prefix count times, and make that the new current.
 *   6. LETTER -> append to current.
 *   7. After the scan, all brackets are closed, so current holds the full answer.
 *
 *    import java.util.ArrayDeque;
 *    import java.util.Deque;
 *
 *    public class DecodeStringTwoStacks {
 *
 *        public static String decodeString(String encoded) {
 *            Deque<Integer> countStack = new ArrayDeque<>();
 *            Deque<StringBuilder> stringStack = new ArrayDeque<>();
 *            StringBuilder current = new StringBuilder();
 *            int repeatCount = 0;
 *
 *            for (char currentChar : encoded.toCharArray()) {
 *                if (Character.isDigit(currentChar)) {
 *                    // Accumulate multi-digit numbers: "12" -> 1, then 1*10+2 = 12
 *                    repeatCount = repeatCount * 10 + (currentChar - '0');
 *
 *                } else if (currentChar == '[') {
 *                    // Park the count and the prefix; start a fresh level.
 *                    countStack.push(repeatCount);
 *                    stringStack.push(current);
 *                    repeatCount = 0;
 *                    current = new StringBuilder();
 *
 *                } else if (currentChar == ']') {
 *                    // Close the level: prefix + (current repeated count times)
 *                    StringBuilder prefix = stringStack.pop();
 *                    int timesToRepeat = countStack.pop();
 *                    for (int i = 0; i < timesToRepeat; i++) {
 *                        prefix.append(current);
 *                    }
 *                    current = prefix;
 *
 *                } else {
 *                    current.append(currentChar);
 *                }
 *            }
 *
 *            return current.toString();
 *        }
 *
 *        public static void main(String[] args) {
 *            System.out.println(decodeString("3[a]2[bc]"));      // aaabcbc
 *            System.out.println(decodeString("3[a2[c]]"));       // accaccacc
 *            System.out.println(decodeString("2[abc]3[cd]ef"));  // abcabccdcdcdef
 *            System.out.println(decodeString("100[leetcode]").length());  // 800
 *        }
 *    }
 *
 * Non-obvious details. current = prefix (reusing the popped builder rather than
 * allocating a new one) avoids an extra full copy per bracket close. ArrayDeque is
 * chosen over java.util.Stack deliberately: Stack extends Vector and synchronizes
 * every method, making it measurably slower with no benefit here. Pushing
 * StringBuilder objects rather than String matters too - pushing current.toString()
 * would force a defensive copy at every [.
 *
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Throughout: n = input length, L = decoded output length, d = maximum nesting depth.
 * Remember L can be vastly larger than n.
 *
 * ------------------------------------------------------------
 * Approach 1 - Brute Force
 * ------------------------------------------------------------
 *
 * TIME: O(L^2)
 *
 * Derivation, step by step:
 *   1. Each while iteration eliminates exactly one bracket pair. With b pairs in the
 *      input, there are exactly b iterations - and b <= n/3, a small constant-ish
 *      number (<= 10 for n = 30).
 *   2. But the WORK PER ITERATION is the problem. indexOf, lastIndexOf, both
 *      substring calls, and the + concatenation each traverse or copy the string as
 *      it currently stands.
 *   3. The string grows monotonically toward length L, so late iterations each cost
 *      Theta(L) just in copying.
 *   4. Additionally, building expanded costs O(repeatCount x innerLength), which is
 *      itself bounded by O(L).
 *   5. Summing Theta(L) work across iterations, with the string repeatedly rebuilt as
 *      it inflates, gives O(L^2) in the worst case - the expensive part is re-copying
 *      already-decoded material over and over.
 *
 * SPACE: O(L) - the working string itself, plus one expanded builder and the
 * temporary substring copies alive during a single concatenation. All bounded by L.
 *
 * Concrete counts:
 *   - "3[a2[c]]" -> L = 9. Two iterations, strings of length <= 9 -> well under 100
 *     character operations. Instant.
 *   - "300[300[ab]]" -> L = 180,000. The final concatenation alone copies 180,000
 *     chars, and the intermediate "300[abab...ab]" (600 chars) is expanded into it.
 *     Order 10^8-10^10 character-copies in the general worst case - this is where the
 *     quadratic bites.
 *
 * ------------------------------------------------------------
 * Approach 2 - Recursive Descent
 * ------------------------------------------------------------
 *
 * TIME: O(L)
 *
 * Derivation:
 *   1. The cursor index only ever increases and never rewinds. Across ALL recursive
 *      calls combined, it advances through the input exactly once -> O(n) total for
 *      parsing.
 *   2. The remaining work is builder.append(innerText) executed repeatCount times per
 *      bracket. Each such append writes characters that appear verbatim in the final
 *      output.
 *   3. Amortized over the whole run, every character of the output is written by
 *      exactly one append at its own level, then copied outward once per enclosing
 *      bracket - but since each outward copy IS a character of a longer output string
 *      that also had to be produced, the total characters written across all levels is
 *      Theta(L).
 *   4. StringBuilder.append is amortized O(1) per character (doubling growth), so
 *      total time is O(n + L) = O(L) since L >= 1 and dominates.
 *
 * SPACE: O(L + d)
 *   - O(L) on the heap - the output string plus the builders held live at each open
 *     level. The sum of all live builder contents is bounded by L.
 *   - O(d) on the JVM CALL STACK - one frame per nesting level. This is the dangerous
 *     part: it is a hard, non-growable limit (~10^4 frames by default), and exceeding
 *     it is a StackOverflowError, not a slowdown.
 *
 * Concrete counts:
 *   - "3[a2[c]]" -> L = 9, d = 2. 8 cursor advances, 9 characters written, 3 stack
 *     frames deep at peak. Trivial.
 *   - "300[300[ab]]" -> L = 180,000, d = 2. About 180,000 character writes, still only
 *     3 frames. Fast - the depth, not the size, is what threatens recursion.
 *
 * ------------------------------------------------------------
 * Approach 3 - Two Stacks ✅
 * ------------------------------------------------------------
 *
 * TIME: O(L)
 *
 * Derivation:
 *   1. The for loop runs exactly n times - one pass, one character each, no
 *      rescanning -> O(n).
 *   2. Digit and letter branches are O(1) each (amortized, for append).
 *   3. The [ branch is O(1): two pushes and two resets, no copying (we push the
 *      builder REFERENCE).
 *   4. The ] branch appends current into prefix timesToRepeat times. Every character
 *      written this way is a character that survives into some enclosing string, and
 *      ultimately into the output. Summed over all ] characters, the total is Theta(L).
 *   5. Total: O(n + L) = O(L). This is OPTIMAL AND CANNOT BE IMPROVED, because merely
 *      writing the answer requires Omega(L) operations.
 *
 * SPACE: O(L)
 *   - stringStack holds at most d builders, whose contents sum to less than L.
 *   - countStack holds at most d integers -> O(d), and d <= n/2 = O(L).
 *   - current and the returned string are O(L).
 *   - NO CALL STACK AT ALL - every byte of bookkeeping is heap-allocated and therefore
 *     limited only by the JVM heap rather than by a fixed thread-stack size.
 *
 * Concrete counts:
 *   - "3[a2[c]]" -> 8 loop iterations, peak stack depth 2, 9 characters written. About
 *     20 elementary operations total.
 *   - "300[300[ab]]" -> 12 loop iterations to parse (input is only 12 chars!), then
 *     600 + 180,000 = 180,600 character appends. Roughly 1.8 x 10^5 operations - about
 *     FIVE ORDERS OF MAGNITUDE better than the brute force on the same input. This is
 *     the clearest illustration of why L, not n, is the right complexity variable.
 *
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 - Brute Force on "3[a2[c]]"
 * ------------------------------------------------------------
 *
 * Each row is one full pass of the while loop.
 *
 * | Iter | current (before) | first ]      | matching [ | countStart | k | innerText | expanded  | current (after) |
 * |------|------------------|--------------|------------|------------|---|-----------|-----------|-----------------|
 * | 1    | 3[a2[c]]         | idx 6        | idx 4      | idx 3      | 2 | c         | cc        | 3[acc]          |
 * | 2    | 3[acc]           | idx 5        | idx 1      | idx 0      | 3 | acc       | accaccacc | accaccacc       |
 * | -    | accaccacc        | no [ -> exit |            |            |   |           |           |                 |
 *
 * Detail of iteration 1: indices are 0:'3' 1:'[' 2:'a' 3:'2' 4:'[' 5:'c' 6:']' 7:']'.
 * The backward digit walk starts at openIndex - 1 = 3, sees '2' (digit, step to 2),
 * sees 'a' (not a digit, stop), then countStart++ lands back on 3. So the count
 * substring is [3, 4) = "2".
 *
 * Rebuild: substring(0,3) = "3[a" + "cc" + substring(7) = "]" -> "3[acc]".
 *
 * FINAL OUTPUT: accaccacc ✅
 *
 * ------------------------------------------------------------
 * Approach 2 - Recursive Descent on "3[a2[c]]"
 * ------------------------------------------------------------
 *
 * Recursion trace (the shared index value is shown at each transition):
 *
 *    decodeFrom()  [level 0]  index=0
 *    │  char '3' at 0 → digit → repeatCount=3, index=1
 *    │  skip '[' → index=2
 *    │
 *    ├─ decodeFrom()  [level 1]  index=2
 *    │  │  char 'a' at 2 → letter → builder="a", index=3
 *    │  │  char '2' at 3 → digit → repeatCount=2, index=4
 *    │  │  skip '[' → index=5
 *    │  │
 *    │  ├─ decodeFrom()  [level 2]  index=5
 *    │  │  │  char 'c' at 5 → letter → builder="c", index=6
 *    │  │  │  char ']' at 6 → STOP, do not consume
 *    │  │  └─ returns "c"   (index=6)
 *    │  │
 *    │  │  skip ']' → index=7
 *    │  │  append "c" × 2 → builder="a" + "cc" = "acc"
 *    │  │  char ']' at 7 → STOP, do not consume
 *    │  └─ returns "acc"   (index=7)
 *    │
 *    │  skip ']' → index=8
 *    │  append "acc" × 3 → builder="accaccacc"
 *    │  index=8 == length → loop ends
 *    └─ returns "accaccacc"
 *
 * Note how index never decreases: 0 → 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8. Every character
 * is visited exactly once across the entire recursion - that is the linearity
 * guarantee made visible.
 *
 * FINAL OUTPUT: accaccacc ✅
 *
 * ------------------------------------------------------------
 * Approach 3 - Two Stacks on "3[a2[c]]"
 * ------------------------------------------------------------
 *
 * State after processing each character (stack tops shown on the left):
 *
 * | Step | Char   | Action                            | repeatCount | countStack | stringStack | current     |
 * |------|--------|-----------------------------------|-------------|------------|-------------|-------------|
 * | -    | (init) | -                                 | 0           | []         | []          | ""          |
 * | 1    | 3      | digit → 0*10+3                    | 3           | []         | []          | ""          |
 * | 2    | [      | push 3, push "", reset            | 0           | [3]        | [""]        | ""          |
 * | 3    | a      | append letter                     | 0           | [3]        | [""]        | "a"         |
 * | 4    | 2      | digit → 0*10+2                    | 2           | [3]        | [""]        | "a"         |
 * | 5    | [      | push 2, push "a", reset           | 0           | [2, 3]     | ["a", ""]   | ""          |
 * | 6    | c      | append letter                     | 0           | [2, 3]     | ["a", ""]   | "c"         |
 * | 7    | ]      | pop "a" & 2 → "a"+"c"+"c"         | 0           | [3]        | [""]        | "acc"       |
 * | 8    | ]      | pop "" & 3 → ""+"acc"×3           | 0           | []         | []          | "accaccacc" |
 *
 * Step 7 is the crux the table makes obvious: current was "c", and ONLY "c" got
 * multiplied - the prefix "a" was retrieved from stringStack and prepended ONCE,
 * unmultiplied. Had we not parked "a" at step 5, we'd have multiplied "ac" and
 * produced "acac". That single design decision is what the whole stringStack exists for.
 *
 * FINAL OUTPUT: accaccacc ✅
 *
 * ------------------------------------------------------------
 * Approach 3 - Second example, "2[abc]3[cd]ef"
 * (sequential groups + trailing literal)
 * ------------------------------------------------------------
 *
 * | Step  | Char      | repeatCount | countStack | stringStack | current          |
 * |-------|-----------|-------------|------------|-------------|------------------|
 * | 1     | 2         | 2           | []         | []          | ""               |
 * | 2     | [         | 0           | [2]        | [""]        | ""               |
 * | 3-5   | a,b,c     | 0           | [2]        | [""]        | "abc"            |
 * | 6     | ]         | 0           | []         | []          | "abcabc"         |
 * | 7     | 3         | 3           | []         | []          | "abcabc"         |
 * | 8     | [         | 0           | [3]        | ["abcabc"]  | ""               |
 * | 9-10  | c,d       | 0           | [3]        | ["abcabc"]  | "cd"             |
 * | 11    | ]         | 0           | []         | []          | "abcabccdcdcd"   |
 * | 12-13 | e,f       | 0           | []         | []          | "abcabccdcdcdef" |
 *
 * Steps 8 and 11 show sibling groups (rather than nested ones) being handled by the
 * identical mechanism: the already-decoded "abcabc" gets parked and reclaimed exactly
 * like any other prefix. Steps 12-13 show trailing literals needing no special case at
 * all - with empty stacks, current simply keeps growing.
 *
 * FINAL OUTPUT: abcabccdcdcdef ✅
 *
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                          | Input          | Expected Output      | How Handled                                                                                                       |
 * |------------------------------------|----------------|----------------------|-------------------------------------------------------------------------------------------------------------------|
 * | No brackets at all (pure literal)  | "abc"          | "abc"                | Every char takes the else branch; stacks stay empty; current returned as-is. Brute force's while never executes.   |
 * | Minimum length input               | "a"            | "a"                  | Single letter appended, returned directly.                                                                        |
 * | Multi-digit count                  | "12[a]"        | 12 a's               | repeatCount = 1, then 1*10+2 = 12. Reading one digit would wrongly give "a" + literal 2.                           |
 * | Maximum count bound                | "300[a]"       | 300 a's              | Accumulator handles 3 digits identically; int is nowhere near overflow.                                            |
 * | Deep nesting                       | "2[2[2[a]]]"   | 8 a's                | Stack depth 3; each ] collapses one level. Recursion uses 4 frames.                                                |
 * | Adjacent sibling groups            | "3[a]2[bc]"    | "aaabcbc"            | After the first ], stacks are empty and current="aaa"; the second [ parks "aaa" as the prefix.                     |
 * | Literal before, between, and after | "x2[y]z"       | "xyyz"               | "x" accumulates into current, gets parked at [, reclaimed at ]; "z" appends afterward with empty stacks.           |
 * | Nested group with prefix letters   | "3[a2[c]]"     | "accaccacc"          | The stringStack preserves "a" so only "c" is doubled. THE CANONICAL FAILURE CASE.                                  |
 * | Count of 1                         | "1[abc]"       | "abc"                | Repeat loop runs once; behaves as a no-op wrapper.                                                                 |
 * | Output much larger than input      | "300[300[ab]]" | 180,000 chars        | Linear approaches handle it in ~10^5 ops; brute force degrades badly. Shows why L, not n, drives complexity.       |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 *
 * PITFALL 1 - Pushing only the count, forgetting the prefix. The single most common bug.
 *
 *    // ❌ WRONG - 'a' gets multiplied along with 'c'; "3[a2[c]]" -> "acacacac..."
 *    else if (currentChar == '[') {
 *        countStack.push(repeatCount);
 *        repeatCount = 0;
 *        // current is NOT parked - the prefix built so far will be repeated too
 *    }
 *
 *    // ✅ CORRECT - park the prefix so only the bracket's own contents repeat
 *    else if (currentChar == '[') {
 *        countStack.push(repeatCount);
 *        stringStack.push(current);
 *        repeatCount = 0;
 *        current = new StringBuilder();
 *    }
 *
 * PITFALL 2 - Reading a single digit instead of accumulating.
 *
 *    // ❌ WRONG - "12[a]" is read as count 1, then '2' is treated as another token
 *    int repeatCount = currentChar - '0';
 *
 *    // ✅ CORRECT - accumulate across consecutive digit characters
 *    repeatCount = repeatCount * 10 + (currentChar - '0');
 *
 * PITFALL 3 - Forgetting to reset repeatCount after [.
 *
 *    // ❌ WRONG - "2[a]3[b]" leaves repeatCount at 2, so the next number becomes 2*10+3 = 23
 *    countStack.push(repeatCount);
 *    stringStack.push(current);
 *    current = new StringBuilder();
 *
 *    // ✅ CORRECT - clear the accumulator once it has been banked
 *    countStack.push(repeatCount);
 *    stringStack.push(current);
 *    repeatCount = 0;
 *    current = new StringBuilder();
 *
 * PITFALL 4 - Passing the cursor by value in the recursive version.
 *
 *    // ❌ WRONG - Java copies the int; the caller never learns how far the callee read,
 *    //            so the same characters are parsed again and the result is corrupted
 *    private String decodeFrom(String s, int index) { ... }
 *
 *    // ✅ CORRECT - a field (or int[1] holder) so progress is shared across all frames
 *    private int index;
 *    private String decodeFrom(String s) { ... }
 *
 * PITFALL 5 - String concatenation inside the repeat loop.
 *
 *    // ❌ WRONG - O(L^2): each += copies the entire accumulated string
 *    String result = "";
 *    for (int i = 0; i < repeatCount; i++) result += innerText;
 *
 *    // ✅ CORRECT - amortized O(1) per character appended
 *    StringBuilder result = new StringBuilder();
 *    for (int i = 0; i < repeatCount; i++) result.append(innerText);
 *
 * PITFALL 6 - Double-consuming the ] (both callee and caller skip it). The callee must
 * stop AT the ] without advancing past it; the caller does index++ to skip it. Doing it
 * in both places silently skips a character and desynchronizes everything downstream.
 * Pick one owner - the caller - and stick to it.
 *
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * ------------------------------------------------------------
 * Q: What edge cases might this miss?
 * ------------------------------------------------------------
 *
 * A: Three real risks and one non-risk.
 *
 *   1. Input with no brackets whatsoever ("abc"). Safe in all three: the stacks stay
 *      empty and current accumulates the literal. Worth an explicit test because it's
 *      the one path where the "interesting" code never runs.
 *   2. Deep nesting overflowing the call stack - a genuine miss for APPROACH 2 ONLY.
 *      LeetCode's n <= 30 caps depth at ~10, so it never fires there, but a 50,000-level
 *      input in production would throw StackOverflowError. Approach 3 is immune. This is
 *      the concrete reason to prefer Approach 3.
 *   3. Invalid input ("3[a", "][", "3a"). All three solutions assume validity per the
 *      constraints and would misbehave - Approach 2's while (Character.isDigit(...))
 *      could throw StringIndexOutOfBoundsException on a trailing digit. If the guarantee
 *      were lifted, every solution would need bounds and balance checks. Given the stated
 *      constraints, not handling this is correct, not a bug.
 *   4. repeatCount = 0 ("0[abc]") is excluded by 1 <= k <= 300, but all three degrade
 *      gracefully anyway - the repeat loop simply runs zero times, yielding "". No crash.
 *
 * ------------------------------------------------------------
 * Q: Are there any type mismatches?
 * ------------------------------------------------------------
 *
 * A: Four places worth checking, all handled correctly above.
 *
 *   - currentChar - '0' yields an int, not a char. Assigning it to a char would be a
 *     compile error; using it in the arithmetic is correct.
 *   - Deque<Integer> AUTO-UNBOXES on countStack.pop() into an int. This would
 *     NullPointerException on an empty stack - impossible here because valid input
 *     guarantees a matching [ for every ].
 *   - stringStack is declared Deque<StringBuilder>, not Deque<String>. Storing the
 *     builder avoids a toString() copy at every [. Mixing them up wouldn't compile,
 *     which is a feature.
 *   - prefix.append(current) appends a StringBuilder via the CharSequence overload -
 *     no intermediate String is created. Correct and intentional.
 *   - Integer.parseInt in Approach 1 returns int; with k <= 300 there is no overflow risk.
 *
 * ------------------------------------------------------------
 * Q: How can I verify this works right now?
 * ------------------------------------------------------------
 *
 * A: Run this - it cross-checks all three implementations against known answers AND
 * against each other. Invoke with: java -ea DecodeStringVerify
 * (the -ea flag is required or assert statements are silently ignored).
 *
 *    import java.util.ArrayDeque;
 *    import java.util.Deque;
 *
 *    public class DecodeStringVerify {
 *
 *        // ---------- Approach 1 ----------
 *        static String bruteForce(String encoded) {
 *            String current = encoded;
 *            while (current.indexOf('[') != -1) {
 *                int closeIndex = current.indexOf(']');
 *                int openIndex = current.lastIndexOf('[', closeIndex);
 *                int countStart = openIndex - 1;
 *                while (countStart >= 0 && Character.isDigit(current.charAt(countStart))) countStart--;
 *                countStart++;
 *                int repeatCount = Integer.parseInt(current.substring(countStart, openIndex));
 *                String innerText = current.substring(openIndex + 1, closeIndex);
 *                StringBuilder expanded = new StringBuilder();
 *                for (int i = 0; i < repeatCount; i++) expanded.append(innerText);
 *                current = current.substring(0, countStart) + expanded + current.substring(closeIndex + 1);
 *            }
 *            return current;
 *        }
 *
 *        // ---------- Approach 2 ----------
 *        static int index;
 *        static String recursive(String encoded) {
 *            index = 0;
 *            return decodeFrom(encoded);
 *        }
 *        static String decodeFrom(String s) {
 *            StringBuilder builder = new StringBuilder();
 *            while (index < s.length() && s.charAt(index) != ']') {
 *                char c = s.charAt(index);
 *                if (Character.isDigit(c)) {
 *                    int repeatCount = 0;
 *                    while (Character.isDigit(s.charAt(index))) {
 *                        repeatCount = repeatCount * 10 + (s.charAt(index) - '0');
 *                        index++;
 *                    }
 *                    index++;
 *                    String innerText = decodeFrom(s);
 *                    index++;
 *                    for (int i = 0; i < repeatCount; i++) builder.append(innerText);
 *                } else {
 *                    builder.append(c);
 *                    index++;
 *                }
 *            }
 *            return builder.toString();
 *        }
 *
 *        // ---------- Approach 3 ----------
 *        static String twoStacks(String encoded) {
 *            Deque<Integer> countStack = new ArrayDeque<>();
 *            Deque<StringBuilder> stringStack = new ArrayDeque<>();
 *            StringBuilder current = new StringBuilder();
 *            int repeatCount = 0;
 *            for (char c : encoded.toCharArray()) {
 *                if (Character.isDigit(c)) {
 *                    repeatCount = repeatCount * 10 + (c - '0');
 *                } else if (c == '[') {
 *                    countStack.push(repeatCount);
 *                    stringStack.push(current);
 *                    repeatCount = 0;
 *                    current = new StringBuilder();
 *                } else if (c == ']') {
 *                    StringBuilder prefix = stringStack.pop();
 *                    int times = countStack.pop();
 *                    for (int i = 0; i < times; i++) prefix.append(current);
 *                    current = prefix;
 *                } else {
 *                    current.append(c);
 *                }
 *            }
 *            return current.toString();
 *        }
 *
 *        static void check(String input, String expected) {
 *            assert bruteForce(input).equals(expected)
 *                    : "bruteForce(" + input + ") = " + bruteForce(input) + ", expected " + expected;
 *            assert recursive(input).equals(expected)
 *                    : "recursive(" + input + ") = " + recursive(input) + ", expected " + expected;
 *            assert twoStacks(input).equals(expected)
 *                    : "twoStacks(" + input + ") = " + twoStacks(input) + ", expected " + expected;
 *            System.out.println("PASS  " + input + "  ->  " + expected);
 *        }
 *
 *        public static void verify() {
 *            check("3[a]2[bc]", "aaabcbc");            // sibling groups
 *            check("3[a2[c]]", "accaccacc");           // nesting with a prefix letter
 *            check("2[abc]3[cd]ef", "abcabccdcdcdef"); // trailing literal
 *            check("abc", "abc");                      // no brackets
 *            check("a", "a");                          // minimal input
 *            check("12[a]", "aaaaaaaaaaaa");           // multi-digit count
 *            check("1[abc]", "abc");                   // count of 1
 *            check("2[2[2[a]]]", "aaaaaaaa");          // deep nesting
 *            check("x2[y]z", "xyyz");                  // literals on both sides
 *            check("3[z]2[2[y]pq4[2[jk]e1[f]]]ef",     // stress test
 *                  "zzzyypqjkjkejkjkejkjkejkjkefyypqjkjkejkjkejkjkejkjkefef");
 *
 *            // Cross-check all three agree on a larger generated case.
 *            String big = "5[10[ab]cd]";
 *            assert bruteForce(big).equals(twoStacks(big));
 *            assert recursive(big).equals(twoStacks(big));
 *            assert twoStacks(big).length() == 5 * (10 * 2 + 2);  // 110
 *            System.out.println("PASS  cross-check, length = " + twoStacks(big).length());
 *
 *            System.out.println("\nAll assertions passed.");
 *        }
 *
 *        public static void main(String[] args) {
 *            boolean assertionsEnabled = false;
 *            assert assertionsEnabled = true;   // side effect fires only with -ea
 *            if (!assertionsEnabled) {
 *                System.out.println("WARNING: run with -ea or assertions are ignored.");
 *            }
 *            verify();
 *        }
 *    }
 *
 * Trace the stress case "3[z]2[2[y]pq4[2[jk]e1[f]]]ef" if you want to convince
 * yourself: 2[jk]e1[f] -> jkjkef, wrapped in 4[...] -> that x4, prefixed by 2[y]pq
 * -> yypq, all x2, after 3[z] -> zzz, plus trailing ef. Each of the three
 * implementations reaches the same 54-character string by a different route - which is
 * precisely what makes the cross-check meaningful.
 *
 * ------------------------------------------------------------
 * Risk Table
 * ------------------------------------------------------------
 *
 * | Approach       | Risk                                                                                              | Mitigation                                                                                          |
 * |----------------|---------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------|
 * | 1 Brute Force  | O(L^2) blow-up from repeated substring + concatenation as the string inflates                     | Use only for tiny inputs or as an explanatory warm-up; switch to Approach 3 for anything real        |
 * | 1 Brute Force  | Off-by-one in the backward digit walk (countStart++ forgotten) -> NumberFormatException or wrong k | Unit-test multi-digit counts (12[a]) and counts at index 0 (3[a]) specifically                       |
 * | 2 Recursive    | StackOverflowError on deeply nested input - O(d) frames on a fixed-size call stack                 | Prefer Approach 3 in production; the identical bookkeeping lives on the heap there                   |
 * | 2 Recursive    | Cursor passed by value instead of shared -> silent re-parsing and corrupted output                | Make index a field; assert it advances monotonically; test 3[a2[c]] where re-parsing is visible      |
 * | 2 Recursive    | Double-consuming the ] (both callee and caller skip it)                                           | Fix one owner (the caller does index++); the callee stops AT ] and never consumes it                 |
 * | 3 Two Stacks   | Forgetting stringStack and multiplying the prefix along with the bracket contents                 | The 3[a2[c]] test catches this immediately - it's the reason that case is canonical                  |
 * | 3 Two Stacks   | Forgetting repeatCount = 0 after [ -> counts bleed together (2[a]3[b] reads 23)                   | Test consecutive sibling groups; keep the reset adjacent to the push so they're edited together      |
 * | All            | String += inside a repeat loop quietly turning a linear algorithm quadratic                       | Always StringBuilder.append; treat += on a string inside any loop as a code smell                    |
 *
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * LeetCode #394 | Difficulty: Medium | Approximate interview appearances: ~1,400+
 * reported (top-40 most-asked Medium; one of the most common stack problems after
 * Valid Parentheses)
 *
 * | Company         | Frequency  | Notes                                                                                                       |
 * |-----------------|------------|-------------------------------------------------------------------------------------------------------------|
 * | Google          | ⭐⭐⭐⭐⭐ | A signature Google question. Follow-ups: nested variables, or a streaming/lazy decoder.                      |
 * | Amazon          | ⭐⭐⭐⭐⭐ | Extremely common in phone screens. Interviewers watch for StringBuilder vs += and clean state handling.      |
 * | Meta (Facebook) | ⭐⭐⭐⭐   | Often paired with Basic Calculator II in the same loop - same stack skeleton, different payload.             |
 * | Microsoft       | ⭐⭐⭐⭐   | Standard onsite fare. Expect a follow-up on validating malformed input.                                      |
 * | Apple           | ⭐⭐⭐⭐   | Asked as a parsing/string-manipulation warm-up before a harder second question.                              |
 * | Bloomberg       | ⭐⭐⭐⭐   | A recurring favorite; Bloomberg leans heavily on stack-based parsing problems.                               |
 * | Uber            | ⭐⭐⭐     | Appears in the backend loop, sometimes framed as expanding a config/template string.                         |
 * | Oracle          | ⭐⭐⭐     | Classic list; usually just the base version with no twists.                                                  |
 * | Adobe           | ⭐⭐⭐     | Straightforward ask, typically with the two-stack solution expected.                                         |
 * | Salesforce      | ⭐⭐⭐     | Shows up in the phone screen; recursion is accepted here.                                                    |
 * | LinkedIn        | ⭐⭐       | Less frequent; often with an "explain the complexity in terms of output size" follow-up.                     |
 * | Goldman Sachs   | ⭐⭐       | Occasional; sometimes as a written round question.                                                           |
 *
 * Common follow-ups to expect:
 *   1. "What if the input might be invalid?" -> add balance checking and bounds guards;
 *      return an error or throw.
 *   2. "What's the complexity?" -> the trap. Answering O(n) where n is the input length
 *      is technically defensible but shows you missed the point; say O(L) in the decoded
 *      length and explain why L >> n.
 *   3. "Can you do it without recursion?" -> they want Approach 3, and they want to hear
 *      WHY (fixed call-stack limit vs. heap).
 *   4. "What if the string is a stream you can only read once?" -> the two-stack version
 *      already handles this; the recursion does too, but only because the cursor is
 *      monotone. Say so.
 *
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach                                   | Time   | Space                                | Code Complexity                                              | Recommended?                                                                                             |
 * |--------------------------------------------|--------|--------------------------------------|--------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|
 * | 1 Brute Force (repeated innermost expansion)| O(L^2) | O(L)                                 | Low - no data structures, but fiddly index math               | ❌ Not for production. Quadratic from repeated string rebuilds. Useful only to explain the insight.       |
 * | 2 Recursive Descent (shared index)         | O(L)   | O(L + d) - O(d) of it on the CALL STACK | Low-Medium - elegant, mirrors the grammar, shared-cursor trap | ✅ Acceptable. Optimal time; correct within LeetCode limits. Risks StackOverflowError on deep nesting.    |
 * | 3 Two Stacks (iterative)                   | O(L)   | O(L) - all on the HEAP               | Medium - four pieces of state, each branch 2-3 lines          | ✅✅ BEST ON BOTH TIME AND SPACE. The production answer and the expected interview answer.                |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 *
 * APPROACH 3 (Two Stacks). Unusually for this kind of problem, there is no trade-off to
 * negotiate: it ties Approach 2 on time (O(L), provably optimal - you can't beat the cost
 * of writing the output) and ties it asymptotically on space, while moving the O(d)
 * bookkeeping off the fixed-size call stack and onto the heap. It is strictly more robust
 * for free. Reach for Approach 2 only if an interviewer explicitly asks for the recursive
 * formulation or you want to show you see the grammar underneath; reach for Approach 1
 * never, except to narrate how you arrived at the insight.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 *
 * k[...] IS A BRACKET-MATCHING PROBLEM, AND EVERY BRACKET-MATCHING PROBLEM IS A STACK
 * PROBLEM - when you see nesting you cannot bound in advance, stop looking for clever
 * variables and push. The one non-obvious move, and the one interviewers actually score,
 * is that EACH [ MUST PUSH TWO THINGS: THE REPEAT COUNT AND THE TEXT BUILT SO FAR -
 * because when the ] arrives, only the current level's text gets multiplied, and the
 * parked prefix gets prepended exactly once. Memorize "3[a2[c]]" -> "accaccacc" as your
 * canary: any solution that forgets the prefix stack, or reads only one digit of a
 * multi-digit count, or uses += instead of StringBuilder inside the repeat loop, will
 * visibly break on it.
 *
 * ============================================================
 * END OF EXPLANATION
 * ============================================================
 */
// @formatter:on
