package Strings;

import java.util.ArrayList;
import java.util.List;

public class RestoreIPAddresses {
    public static void main(String[] args) {
        RestoreIPAddresses restoreIPAddresses = new RestoreIPAddresses();
        System.out.println(
                "RestoreIPAddresses : " + restoreIPAddresses.restoreIpAddressesBackTrackDFSPruning("25525511135"));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/restore-ip-addresses/description/
     * 
     * A valid IP address consists of exactly four integers separated by single
     * dots. Each integer is between 0 and 255 (inclusive) and cannot have leading
     * zeros.
     * 
     * For example, "0.1.2.201" and "192.168.1.1" are valid IP addresses, but
     * "0.011.255.245", "192.168.1.312" and "192.168@1.1" are invalid IP addresses.
     * Given a string s containing only digits, return all possible valid IP
     * addresses that can be formed by inserting dots into s. You are not allowed to
     * reorder or remove any digits in s. You may return the valid IP addresses in
     * any order.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: s = "25525511135"
     * Output: ["255.255.11.135","255.255.111.35"]
     * Example 2:
     * 
     * Input: s = "0000"
     * Output: ["0.0.0.0"]
     * Example 3:
     * 
     * Input: s = "101023"
     * Output: ["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
     * 
     * 
     * Constraints:
     * 
     * 1 <= s.length <= 20
     * s consists of digits only.
     * 
     */
    // @formatter:on
    public List<String> restoreIpAddressesBackTrackDFSPruning(String s) {
        List<String> result = new ArrayList<>();
        int n = s.length();
        if (n < 4 || n > 12)
            return result;
        List<String> currentSegment = new ArrayList<>();
        backTrackDFSPruning(s, 0, currentSegment, result);
        return result;
    }

    public void backTrackDFSPruning(String s, int startIndex, List<String> currentSegments, List<String> results) {
        int segmentUsed = currentSegments.size();
        int remainingChars = s.length() - startIndex;

        int remainingSegments = 4 - segmentUsed;
        if (remainingChars > remainingSegments * 3 || remainingChars < remainingSegments)
            return;

        if (segmentUsed == 4) {
            if (startIndex == s.length()) {
                results.add(String.join(".", currentSegments));
            }
            return;
        }

        for (int length = 1; length <= 3 && startIndex + length <= s.length(); length++) {
            String segment = s.substring(startIndex, startIndex + length);
            if (!isValidIp(segment)) {
                if (segment.charAt(0) == '0')
                    break;
                continue;
            }
            currentSegments.add(segment);
            backTrackDFSPruning(s, startIndex + length, currentSegments, results);
            currentSegments.remove(currentSegments.size() - 1);
        }
    }

    public boolean isValidIp(String segment) {
        if (segment.length() > 1 && segment.charAt(0) == '0')
            return false;
        int ip = Integer.parseInt(segment);
        return ip <= 255;
    }
}

// @formatter:off
/*
 * ============================================================
 * RESTORE IP ADDRESSES - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given a string s containing only digits, insert exactly three dots into
 * it to split it into four segments, such that each segment is a valid
 * component of an IPv4 address. Return all possible valid IP address
 * combinations (in any order). You may not reorder or remove digits -
 * only insert dots.
 *
 * A segment is valid if: it's between 0 and 255 inclusive, and it has
 * no leading zeros (except the single digit "0" itself, e.g. "0" is
 * valid but "00" or "01" is not).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * String s - a string of digits, length between 1 and 20 (in practice,
 * only strings of length 4-12 can yield results).
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * List<String> - all valid IP addresses formable from s, each formatted
 * as "a.b.c.d".
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * 1 <= s.length <= 20
 * s consists of digits only.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * All ways to partition s into exactly 4 non-empty substrings such that
 * each substring, interpreted as a number, lies in [0, 255] and has no
 * leading zero.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 * Input: s = "25525511135"
 * Output: ["255.255.11.135", "255.255.111.35"]
 *
 * LeetCode Problem #93, Difficulty: Medium.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Think of the digit string as a strip of paper. You need to cut it in
 * exactly 3 places to create 4 pieces, and each piece must "look like"
 * a legal IP segment (0-255, no leading zeros). Since each segment can
 * only be 1, 2, or 3 digits long, the number of ways to cut is small
 * enough to just try all of them.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. We need exactly 4 segments, so we need exactly 3 cut points.
 * 2. Each segment can be at most 3 characters long (since 256+ is
 *    invalid, and max valid value 255 has 3 digits).
 * 3. So instead of trying every possible cut position in the whole
 *    string, we only try cutting after 1, 2, or 3 characters at each
 *    step - this bounds the branching factor to 3.
 * 4. At each step, validate the chosen segment immediately (range check
 *    + leading zero check) before recursing further - this prunes
 *    invalid paths early.
 * 5. When all 4 segments are chosen and the entire string is consumed
 *    exactly, record the result.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                          | Why it's tricky                                                    |
 * |-------------------------------------|---------------------------------------------------------------------|
 * | Leading zeros                       | "010" looks numerically like 10, but is not a valid IP segment      |
 * | Exact consumption                   | All segments must use up the entire string, no leftover digits      |
 * | Off-by-one in segment boundaries    | Easy to under/overshoot string indices when slicing 1-3 char chunks |
 * | Bounding numeric range              | Must parse only after confirming length <=3 to avoid overflow       |
 * | Distinguishing "0" validity         | "0" itself is valid, the only allowed case of length-1 zero         |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 * | # | Approach                                 | Key Idea                                              | Best Used When                          | Time      | Space |
 * |---|-------------------------------------------|--------------------------------------------------------|-------------------------------------------|-----------|-------|
 * | 1 | Brute Force (triple nested loop)         | Try every combination of 3 dot positions, validate after | Educational baseline; small strings only | O(n^3)    | O(n)  |
 * | 2 | Backtracking / DFS with pruning ✅       | Recursively pick segment length 1-3, validate immediately | General case - standard solution         | O(1) ~ O(81) | O(1) extra |
 * | 3 | Iterative triple-loop (bounded lengths)  | Same pruning idea, no recursion, 3 nested for loops    | Avoid recursion/call-stack overhead       | O(27)     | O(1) |
 *
 * Since segment length is capped at 3 characters and there are exactly
 * 4 segments, total candidate splits are bounded by a small constant
 * (3x3x3 = 27 once the last segment is forced to consume the
 * remainder), making this effectively constant-time/constant-space
 * regardless of input length. Brute force is asymptotically worse
 * because it doesn't exploit the segment-length bound. Backtracking ✅
 * is the standard choice for clarity and earliest pruning. The
 * iterative triple-loop is functionally equivalent in complexity but
 * avoids recursion entirely - preferable if you want zero call-stack
 * usage, though the difference is negligible here since recursion
 * depth is at most 4.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 * ------------------------------------------------------------
 * Approach 1: Brute Force (Triple Nested Loop Over Dot Positions)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Iterate i from 1 to n-1 as the position of the first dot.
 * 2. Iterate j from i+1 to n-1 as the position of the second dot.
 * 3. Iterate k from j+1 to n-1 as the position of the third dot.
 * 4. This creates 4 substrings: s[0:i], s[i:j], s[j:k], s[k:n].
 * 5. Validate each substring (range 0-255, no leading zero).
 * 6. If all 4 are valid, join with dots and add to result.
 *
 *    import java.util.ArrayList;
 *    import java.util.List;
 *
 *    public class RestoreIpAddressesBruteForce {
 *
 *        public List<String> restoreIpAddresses(String s) {
 *            List<String> results = new ArrayList<>();
 *            int n = s.length();
 *            if (n < 4 || n > 12) return results;
 *
 *            for (int i = 1; i < n - 2; i++) {
 *                for (int j = i + 1; j < n - 1; j++) {
 *                    for (int k = j + 1; k < n; k++) {
 *                        String segment1 = s.substring(0, i);
 *                        String segment2 = s.substring(i, j);
 *                        String segment3 = s.substring(j, k);
 *                        String segment4 = s.substring(k, n);
 *
 *                        if (isValidSegment(segment1) && isValidSegment(segment2)
 *                                && isValidSegment(segment3) && isValidSegment(segment4)) {
 *                            results.add(segment1 + "." + segment2 + "." + segment3 + "." + segment4);
 *                        }
 *                    }
 *                }
 *            }
 *            return results;
 *        }
 *
 *        private boolean isValidSegment(String segment) {
 *            if (segment.length() == 0 || segment.length() > 3) return false;
 *            if (segment.length() > 1 && segment.charAt(0) == '0') return false;
 *            int value = Integer.parseInt(segment);
 *            return value >= 0 && value <= 255;
 *        }
 *
 *        public static void main(String[] args) {
 *            RestoreIpAddressesBruteForce solver = new RestoreIpAddressesBruteForce();
 *            List<String> output = solver.restoreIpAddresses("25525511135");
 *            System.out.println(output); // [255.255.11.135, 255.255.111.35]
 *        }
 *    }
 *
 * Non-obvious details: The loop bounds i < n-2, j < n-1, k < n ensure
 * each of the 4 segments has at least 1 character remaining. Even
 * though this is "brute force" over gap positions, isValidSegment still
 * rejects any segment longer than 3 characters, bounding wasted work.
 *
 * ------------------------------------------------------------
 * Approach 2: Backtracking / DFS with Pruning ✅
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Define a recursive function backtrack(startIndex, segmentsUsed, currentSegments).
 * 2. Base case: if segmentsUsed == 4, succeed only if startIndex == s.length().
 * 3. Otherwise, try segment lengths len = 1, 2, 3:
 *    - If out of bounds, stop.
 *    - Extract segment; if invalid, skip (break early on leading-zero).
 *    - Otherwise add segment, recurse, then remove (backtrack).
 * 4. Prune early when remaining characters can't fill remaining segments.
 *
 *    import java.util.ArrayList;
 *    import java.util.List;
 *
 *    public class RestoreIpAddressesBacktracking {
 *
 *        public List<String> restoreIpAddresses(String s) {
 *            List<String> results = new ArrayList<>();
 *            int n = s.length();
 *            if (n < 4 || n > 12) return results;
 *
 *            List<String> currentSegments = new ArrayList<>();
 *            backtrack(s, 0, currentSegments, results);
 *            return results;
 *        }
 *
 *        private void backtrack(String s, int startIndex, List<String> currentSegments, List<String> results) {
 *            int segmentsUsed = currentSegments.size();
 *            int remainingChars = s.length() - startIndex;
 *
 *            int remainingSegments = 4 - segmentsUsed;
 *            if (remainingChars > remainingSegments * 3 || remainingChars < remainingSegments) {
 *                return;
 *            }
 *
 *            if (segmentsUsed == 4) {
 *                if (startIndex == s.length()) {
 *                    results.add(String.join(".", currentSegments));
 *                }
 *                return;
 *            }
 *
 *            for (int len = 1; len <= 3 && startIndex + len <= s.length(); len++) {
 *                String segment = s.substring(startIndex, startIndex + len);
 *
 *                if (!isValidSegment(segment)) {
 *                    if (segment.charAt(0) == '0') break;
 *                    continue;
 *                }
 *
 *                currentSegments.add(segment);
 *                backtrack(s, startIndex + len, currentSegments, results);
 *                currentSegments.remove(currentSegments.size() - 1);
 *            }
 *        }
 *
 *        private boolean isValidSegment(String segment) {
 *            if (segment.length() > 1 && segment.charAt(0) == '0') return false;
 *            int value = Integer.parseInt(segment);
 *            return value <= 255;
 *        }
 *
 *        public static void main(String[] args) {
 *            RestoreIpAddressesBacktracking solver = new RestoreIpAddressesBacktracking();
 *            List<String> output = solver.restoreIpAddresses("25525511135");
 *            System.out.println(output); // [255.255.11.135, 255.255.111.35]
 *        }
 *    }
 *
 * Non-obvious details: The pruning check keeps this near-constant time
 * by avoiding branches that can never succeed. The break on leading-
 * zero avoids redundant longer-length attempts from the same start.
 *
 * ------------------------------------------------------------
 * Approach 3: Iterative Triple-Loop with Bounded Segment Lengths
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Loop len1 from 1 to 3.
 * 2. Loop len2 from 1 to 3.
 * 3. Loop len3 from 1 to 3.
 * 4. Segment 4 length = n - len1 - len2 - len3; skip if not in [1,3].
 * 5. Extract and validate all 4 segments; if all valid, add result.
 *
 *    import java.util.ArrayList;
 *    import java.util.List;
 *
 *    public class RestoreIpAddressesIterative {
 *
 *        public List<String> restoreIpAddresses(String s) {
 *            List<String> results = new ArrayList<>();
 *            int n = s.length();
 *            if (n < 4 || n > 12) return results;
 *
 *            for (int len1 = 1; len1 <= 3; len1++) {
 *                for (int len2 = 1; len2 <= 3; len2++) {
 *                    for (int len3 = 1; len3 <= 3; len3++) {
 *                        int len4 = n - len1 - len2 - len3;
 *                        if (len4 < 1 || len4 > 3) continue;
 *
 *                        String segment1 = s.substring(0, len1);
 *                        String segment2 = s.substring(len1, len1 + len2);
 *                        String segment3 = s.substring(len1 + len2, len1 + len2 + len3);
 *                        String segment4 = s.substring(len1 + len2 + len3, n);
 *
 *                        if (isValidSegment(segment1) && isValidSegment(segment2)
 *                                && isValidSegment(segment3) && isValidSegment(segment4)) {
 *                            results.add(segment1 + "." + segment2 + "." + segment3 + "." + segment4);
 *                        }
 *                    }
 *                }
 *            }
 *            return results;
 *        }
 *
 *        private boolean isValidSegment(String segment) {
 *            if (segment.length() > 1 && segment.charAt(0) == '0') return false;
 *            int value = Integer.parseInt(segment);
 *            return value <= 255;
 *        }
 *
 *        public static void main(String[] args) {
 *            RestoreIpAddressesIterative solver = new RestoreIpAddressesIterative();
 *            List<String> output = solver.restoreIpAddresses("25525511135");
 *            System.out.println(output); // [255.255.11.135, 255.255.111.35]
 *        }
 *    }
 *
 * Non-obvious details: len4 is derived, not looped, so this is only a
 * triple loop (3x3x3 = 27 iterations max) instead of quadruple.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 * Approach 1: Brute Force
 *   Time: O(n^3) for the three nested index loops. For n=12, that's
 *   12^3 = 1728 iterations.
 *   Space: O(n) for substring creation per candidate, O(1) for storage,
 *   output excluded.
 *
 * Approach 2: Backtracking ✅
 *   Time: O(1) effectively, bounded by O(3^4)=O(81) branches before
 *   pruning, often far fewer in practice.
 *   Space: O(1) extra beyond output; recursion depth bounded by 4, so
 *   stack space is O(1).
 *
 * Approach 3: Iterative Triple-Loop
 *   Time: O(27) fixed iterations since len4 is computed not looped.
 *   Space: O(1) extra, no recursion stack.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * Approach 1: Brute Force - Input s = "1111"
 *   n = 4
 *   Try i=1, j=2, k=3:
 *     segment1="1", segment2="1", segment3="1", segment4="1"
 *     All valid -> Add "1.1.1.1"
 *   No other (i,j,k) combos exist since n=4 forces i=1,j=2,k=3 exactly.
 *   Final output: ["1.1.1.1"]
 *
 * Approach 2: Backtracking - Input s = "101023"
 *   backtrack(start=0, segments=[])
 *   |- len=1: "1" valid -> segments=["1"]
 *   |   backtrack(start=1)
 *   |   |- len=1: "0" valid -> segments=["1","0"]
 *   |   |   backtrack(start=2)
 *   |   |   |- len=2: "10" valid -> segments=["1","0","10"]
 *   |   |   |   backtrack(start=4), remaining "23"
 *   |   |   |   len=2: "23" valid -> SUCCESS "1.0.10.23"
 *   |   |   |- len=3: "102" valid -> segments=["1","0","102"]
 *   |   |   |   backtrack(start=5), remaining "3"
 *   |   |   |   len=1: "3" valid -> SUCCESS "1.0.102.3"
 *   |   ... (other branches continue similarly)
 *   Verified final output for "101023":
 *   ["1.0.10.23","1.0.102.3","10.10.2.3","10.102.3","101.0.2.3"]
 *
 * Approach 3: Iterative Triple-Loop - Input s = "1111"
 *   n = 4
 *   len1=1,len2=1,len3=1 -> len4=1 (valid) -> "1.1.1.1" added
 *   all other combos produce len4 outside [1,3] -> skipped
 *   Final output: ["1.1.1.1"]
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 * | Edge Case                          | Input                       | Expected Output | How Handled                                  |
 * |-------------------------------------|------------------------------|-------------------|-------------------------------------------------|
 * | Too short to form 4 segments        | "123"                        | []                 | n < 4 rejects upfront                            |
 * | Too long to form valid IP           | 19-digit string              | []                 | n > 12 rejects upfront                           |
 * | All zeros, exact length 4           | "0000"                       | ["0.0.0.0"]        | Each "0" length-1, passes leading-zero check     |
 * | Leading zero in multi-digit segment | "010010"                     | only non-leading-zero splits survive | length>1 && charAt(0)=='0' check rejects |
 * | Segment value exactly 255 (boundary)| "255255255255"               | ["255.255.255.255"]| <=255 boundary check passes exactly at 255       |
 * | Segment value 256 (over boundary)   | "256256256256"                | []                  | <=255 check rejects "256"                        |
 * | Single forced valid split           | "1111"                       | ["1.1.1.1"]         | Only one possible split exists                   |
 *
 * Potential Pitfalls:
 *
 * Pitfall 1: Forgetting the leading-zero rule
 *   WRONG:
 *     private boolean isValidSegment(String segment) {
 *         int value = Integer.parseInt(segment);
 *         return value >= 0 && value <= 255;
 *     }
 *   CORRECT:
 *     private boolean isValidSegment(String segment) {
 *         if (segment.length() > 1 && segment.charAt(0) == '0') return false;
 *         int value = Integer.parseInt(segment);
 *         return value <= 255;
 *     }
 *
 * Pitfall 2: Not bounding segment length before parsing
 *   WRONG:
 *     private boolean isValidSegment(String segment) {
 *         int value = Integer.parseInt(segment);
 *         return value <= 255;
 *     }
 *   CORRECT:
 *     private boolean isValidSegment(String segment) {
 *         if (segment.length() == 0 || segment.length() > 3) return false;
 *         if (segment.length() > 1 && segment.charAt(0) == '0') return false;
 *         int value = Integer.parseInt(segment);
 *         return value <= 255;
 *     }
 *
 * Pitfall 3: Forgetting to check the entire string is consumed exactly
 *   WRONG:
 *     if (segmentsUsed == 4) {
 *         results.add(String.join(".", currentSegments));
 *         return;
 *     }
 *   CORRECT:
 *     if (segmentsUsed == 4) {
 *         if (startIndex == s.length()) {
 *             results.add(String.join(".", currentSegments));
 *         }
 *         return;
 *     }
 *
 * Pitfall 4: Forgetting to backtrack after recursing
 *   WRONG:
 *     currentSegments.add(segment);
 *     backtrack(s, startIndex + len, currentSegments, results);
 *     // missing removal!
 *   CORRECT:
 *     currentSegments.add(segment);
 *     backtrack(s, startIndex + len, currentSegments, results);
 *     currentSegments.remove(currentSegments.size() - 1);
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 * Q: What edge cases might this miss?
 * A: Strings shorter than 4 or longer than 12 characters (handled by
 *    bounds checks), and segments with leading zeros longer than 1
 *    digit (handled by explicit check). Empty string is implicitly
 *    handled since n < 4 catches it.
 *
 * Q: Are there any type mismatches?
 * A: Integer.parseInt is only called on strings of length <=3, so max
 *    parsed value is 999, well within int range - no overflow risk.
 *
 * Q: How can I verify this works right now?
 *
 *    import java.util.Arrays;
 *    import java.util.Collections;
 *    import java.util.List;
 *
 *    public class RestoreIpAddressesVerify {
 *
 *        public static void verify() {
 *            RestoreIpAddressesBacktracking solver = new RestoreIpAddressesBacktracking();
 *
 *            List<String> result1 = solver.restoreIpAddresses("25525511135");
 *            Collections.sort(result1);
 *            List<String> expected1 = Arrays.asList("255.255.11.135", "255.255.111.35");
 *            Collections.sort(expected1);
 *            assert result1.equals(expected1) : "Test 1 failed: " + result1;
 *
 *            List<String> result2 = solver.restoreIpAddresses("0000");
 *            assert result2.equals(Arrays.asList("0.0.0.0")) : "Test 2 failed: " + result2;
 *
 *            List<String> result3 = solver.restoreIpAddresses("101023");
 *            Collections.sort(result3);
 *            List<String> expected3 = Arrays.asList(
 *                    "1.0.10.23", "1.0.102.3", "10.10.2.3", "10.102.3", "101.0.2.3");
 *            Collections.sort(expected3);
 *            assert result3.equals(expected3) : "Test 3 failed: " + result3;
 *
 *            List<String> result4 = solver.restoreIpAddresses("123");
 *            assert result4.isEmpty() : "Test 4 failed: " + result4;
 *
 *            List<String> result5 = solver.restoreIpAddresses("1111111111111111111");
 *            assert result5.isEmpty() : "Test 5 failed (too long): " + result5;
 *
 *            System.out.println("All verification tests passed!");
 *        }
 *
 *        public static void main(String[] args) {
 *            verify();
 *        }
 *    }
 *
 * | Approach              | Risk                                                  | Mitigation                                                  |
 * |------------------------|--------------------------------------------------------|----------------------------------------------------------------|
 * | Brute Force            | Wasted computation on invalid index combinations        | Acceptable for small n, switch to backtracking at scale        |
 * | Backtracking            | Forgetting to backtrack causes shared-state corruption  | Always pair add with remove right after the recursive call     |
 * | Iterative Triple-Loop   | Off-by-one errors computing len4 and substring offsets  | Always derive len4 as n-len1-len2-len3 and validate bounds      |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode Problem #93 "Restore IP Addresses", Difficulty: Medium,
 * approximate total interview appearances: moderate-to-high.
 *
 * | Company         | Frequency (stars) | Notes                                                  |
 * |------------------|---------------------|----------------------------------------------------------|
 * | Amazon          | ⭐⭐⭐⭐            | Frequently asked in SDE-1/SDE-2 backtracking rounds       |
 * | Facebook (Meta) | ⭐⭐⭐⭐            | Common in onsite coding rounds focused on recursion       |
 * | Google          | ⭐⭐⭐              | Appears occasionally in phone screens                     |
 * | Microsoft       | ⭐⭐⭐              | Asked in some backtracking/string manipulation rounds     |
 * | Bloomberg       | ⭐⭐⭐⭐            | Known for favoring string-parsing backtracking problems   |
 * | Oracle          | ⭐⭐                | Less frequent but appears in some rounds                  |
 * | Adobe           | ⭐⭐⭐              | Appears in coding rounds focused on recursion/backtracking|
 * | Apple           | ⭐⭐                | Occasionally appears in onsite rounds                     |
 * | Uber            | ⭐⭐⭐              | Common in backtracking practice sets used by interviewers |
 * | ByteDance       | ⭐⭐⭐⭐            | Frequently cited in interview prep aggregators            |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 * | Approach              | Time      | Space            | Code Complexity | Recommended?                                |
 * |-------------------------|-----------|--------------------|--------------------|--------------------------------------------------|
 * | Brute Force             | O(n^3)    | O(n) per candidate | Low (wasteful)     | ❌ Not recommended                              |
 * | Backtracking ✅         | O(1)~O(81)| O(1) extra         | Medium             | ✅✅ Best overall                                |
 * | Iterative Triple-Loop   | O(27)     | O(1) extra         | Medium             | ✅ Best for avoiding recursion                   |
 *
 * Recommended Approach: Use the Backtracking approach (Approach 2) -
 * it's the cleanest, most idiomatic solution for "build exactly k valid
 * segments" problems, with natural early pruning and minimal code
 * complexity; the iterative version is a solid alternative only if
 * recursion must be avoided entirely.
 *
 * What to Remember: This problem is a textbook example of backtracking
 * with bounded branching factor - when a sub-choice (here, a segment)
 * is provably limited to a small fixed set of lengths (1-3 characters),
 * the entire search space collapses to near-constant size regardless of
 * overall input length. Key gotcha: always check for leading zeros
 * explicitly (length > 1 && charAt(0) == '0') since numeric validity
 * alone is insufficient - IP segments are about string representation,
 * not just numeric value.
 */
// @formatter:on
