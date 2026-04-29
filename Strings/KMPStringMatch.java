package Strings;

import java.util.ArrayList;
import java.util.List;

public class KMPStringMatch {
    public static void main(String[] args) {
        String text = "aabxaabyaabyaab";
        String pattern = "aabyaab";

        List<Integer> matches = search(text, pattern);
        for (int idx : matches) {
            System.out.println("Pattern found at index: " + idx);
        }
    }

    private static int[] buildLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        lps[0] = 0;
        int length = 0;
        int i = 1;
        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    public static List<Integer> search(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();
        if (m == 0 || m > n)
            return result;
        int[] lps = buildLPS(pattern);
        int i = 0;
        int j = 0;
        while (i < n) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }
            if (j == m) {
                result.add(i - m);
                j = lps[j - 1];
            } else if (i < n && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return result;
    }

    // @formatter:off
    //     # String Matching (KMP) — Deep Dive in Java

    // ---

    // ## 1. Problem Statement

    // ### What the Problem Asks
    // Given two strings:
    // - A **text** string `T` of length `n`
    // - A **pattern** string `P` of length `m`

    // Find **all starting indices** in `T` where `P` occurs as a substring (0-indexed).

    // ### Input Format
    // ```
    // Text:    "aabxaabyaabyaab"
    // Pattern: "aabyaab"
    // ```

    // ### Output Format
    // ```
    // Pattern found at index: 4
    // Pattern found at index: 8
    // ```

    // ### Constraints
    // | Parameter | Typical Range |
    // |-----------|--------------|
    // | Text length `n` | 1 ≤ n ≤ 10⁶ |
    // | Pattern length `m` | 1 ≤ m ≤ n |
    // | Characters | Lowercase/uppercase ASCII |

    // ### What Exactly Needs to Be Computed
    // Return (or print) every index `i` such that `T[i..i+m-1] == P`.

    // ---

    // ## 2. Intuition

    // ### The Simple Human Reasoning
    // Imagine you're reading a book searching for the word **"aabyaab"**. Naively, you'd:
    // 1. Point to each character of the book.
    // 2. Try matching the full pattern starting there.
    // 3. If it fails at some character, back up completely and try the next position.

    // **The problem with that?** You throw away information you already discovered. If you matched 6 of 7 characters before failing, you already *know* what those 6 characters were — you shouldn't restart from scratch.

    // ### The KMP Insight
    // KMP (Knuth-Morris-Pratt) says:

    // > *"When a mismatch happens, use the structure of the pattern itself to know how far back to jump — never re-examine a text character you already passed."*

    // The key observation is: **the pattern contains information about itself**. Specifically, if a prefix of the pattern also appears as a suffix, then after a partial match we can "slide" the pattern smartly instead of resetting to zero.

    // ### What Makes This Tricky
    // - Building the **failure function** (also called LPS — Longest Proper Prefix which is also Suffix) is non-obvious.
    // - Understanding *why* we jump to `lps[j-1]` and not `0` after a mismatch takes careful reasoning.
    // - Off-by-one errors are common if you mix 0-indexed and 1-indexed thinking.

    // ---

    // ## 3. Approach Overview

    // | # | Approach | Core Idea | Time | Space | When to Use |
    // |---|----------|-----------|------|-------|-------------|
    // | 1 | **Brute Force** | Try every position, compare naively | O(n×m) | O(1) | Very small inputs, quick prototype |
    // | 2 | **KMP Algorithm** | Precompute LPS array, never re-examine text chars | O(n+m) | O(m) | ✅ **Optimal — always use this** |
    // | 3 | **Rabin-Karp** | Rolling hash to skip non-matching windows | O(n+m) avg | O(1) | Multiple pattern search, competitive |
    // | 4 | **Z-Algorithm** | Z-array for pattern matching | O(n+m) | O(n+m) | Alternative optimal, simpler to code |

    // ### ✅ Recommended: KMP
    // KMP is the canonical O(n+m) solution. It is the most commonly asked in interviews by name, especially at Google, Amazon, and Microsoft. It runs in **linear time with guaranteed worst-case performance** and no hash collision risk.

    // ---

    // ## 4. Detailed Solutions in Java

    // ---

    // ### Approach 1: Brute Force

    // #### Algorithm Steps
    // 1. For every index `i` from `0` to `n-m`:
    //    - Try to match `P` starting at `T[i]`.
    //    - Inner loop compares `T[i+j]` with `P[j]` for `j = 0..m-1`.
    //    - If all `m` characters match → record index `i`.
    //    - If any mismatch → break inner loop, move to `i+1`.

    // ```java
    // import java.util.ArrayList;
    // import java.util.List;

    // public class BruteForceStringMatch {

    //     public static List<Integer> search(String text, String pattern) {
    //         List<Integer> result = new ArrayList<>();
    //         int n = text.length();
    //         int m = pattern.length();

    //         // Try every possible starting position in the text
    //         for (int i = 0; i <= n - m; i++) {
    //             int j = 0;

    //             // Compare pattern with current window in text
    //             while (j < m && text.charAt(i + j) == pattern.charAt(j)) {
    //                 j++;
    //             }

    //             // Full match found
    //             if (j == m) {
    //                 result.add(i);
    //             }
    //         }
    //         return result;
    //     }

    //     public static void main(String[] args) {
    //         String text    = "aabxaabyaabyaab";
    //         String pattern = "aabyaab";

    //         List<Integer> matches = search(text, pattern);
    //         for (int idx : matches) {
    //             System.out.println("Pattern found at index: " + idx);
    //         }
    //     }
    // }
    // ```

    // ---

    // ### Approach 2: KMP Algorithm (Optimal) ✅

    // #### Phase 1 — Build the LPS (Failure Function) Array

    // The LPS array `lps[i]` = length of the **longest proper prefix** of `P[0..i]` that is also a suffix.

    // **Example for pattern `"aabyaab"`:**

    // | Index | Char | LPS Value | Meaning |
    // |-------|------|-----------|---------|
    // | 0 | a | 0 | No proper prefix |
    // | 1 | a | 1 | "a" is prefix and suffix |
    // | 2 | b | 0 | No match |
    // | 3 | y | 0 | No match |
    // | 4 | a | 1 | "a" = prefix & suffix |
    // | 5 | a | 2 | "aa" = prefix & suffix |
    // | 6 | b | 3 | "aab" = prefix & suffix |

    // #### Phase 2 — Searching Using the LPS Array

    // - Use two pointers: `i` for text, `j` for pattern.
    // - If `T[i] == P[j]`, advance both.
    // - If `j == m`, found a match at index `i - m`. Reset `j = lps[j-1]`.
    // - If mismatch and `j > 0`, set `j = lps[j-1]` (don't move `i`).
    // - If mismatch and `j == 0`, just advance `i`.

    // ```java
    // import java.util.ArrayList;
    // import java.util.List;

    // public class KMPStringMatch {

    //     /**
    //      * Builds the LPS (Longest Proper Prefix which is also Suffix) array.
    //      * lps[i] = length of longest proper prefix of pattern[0..i]
    //      *          that is also a suffix of pattern[0..i].
    //      */
    //     private static int[] buildLPS(String pattern) {
    //         int m = pattern.length();
    //         int[] lps = new int[m];

    //         lps[0] = 0;             // Base case: single char has no proper prefix
    //         int length = 0;         // Length of the previous longest prefix-suffix
    //         int i = 1;

    //         while (i < m) {
    //             if (pattern.charAt(i) == pattern.charAt(length)) {
    //                 // Characters match: extend the current prefix-suffix
    //                 length++;
    //                 lps[i] = length;
    //                 i++;
    //             } else {
    //                 if (length != 0) {
    //                     // Fall back using the LPS array itself (key KMP trick)
    //                     length = lps[length - 1];
    //                     // Do NOT increment i here — try again with shorter prefix
    //                 } else {
    //                     // No prefix-suffix possible at this position
    //                     lps[i] = 0;
    //                     i++;
    //                 }
    //             }
    //         }
    //         return lps;
    //     }

    //     /**
    //      * KMP search: finds all occurrences of pattern in text.
    //      * Returns list of 0-indexed starting positions.
    //      */
    //     public static List<Integer> search(String text, String pattern) {
    //         List<Integer> result = new ArrayList<>();
    //         int n = text.length();
    //         int m = pattern.length();

    //         if (m == 0 || m > n) return result;

    //         int[] lps = buildLPS(pattern);

    //         int i = 0; // pointer for text
    //         int j = 0; // pointer for pattern

    //         while (i < n) {
    //             if (text.charAt(i) == pattern.charAt(j)) {
    //                 i++;
    //                 j++;
    //             }

    //             if (j == m) {
    //                 // Complete match found; record start index
    //                 result.add(i - m);
    //                 // Use LPS to find next possible match without backtracking
    //                 j = lps[j - 1];
    //             } else if (i < n && text.charAt(i) != pattern.charAt(j)) {
    //                 if (j != 0) {
    //                     // Partial match: use LPS to skip redundant comparisons
    //                     j = lps[j - 1];
    //                 } else {
    //                     // No partial match: simply advance text pointer
    //                     i++;
    //                 }
    //             }
    //         }
    //         return result;
    //     }

    //     public static void main(String[] args) {
    //         String text    = "aabxaabyaabyaab";
    //         String pattern = "aabyaab";

    //         List<Integer> matches = search(text, pattern);
    //         for (int idx : matches) {
    //             System.out.println("Pattern found at index: " + idx);
    //         }
    //     }
    // }
    // ```

    // ---

    // ### Approach 3: Rabin-Karp (Bonus — Rolling Hash)

    // #### Algorithm Steps
    // 1. Compute hash of pattern `P`.
    // 2. Compute hash of first window `T[0..m-1]`.
    // 3. Slide window across text, updating hash in O(1) per step.
    // 4. When hashes match, verify character by character (to handle collisions).

    // ```java
    // import java.util.ArrayList;
    // import java.util.List;

    // public class RabinKarpStringMatch {

    //     private static final int BASE  = 31;
    //     private static final long MOD  = 1_000_000_007L;

    //     public static List<Integer> search(String text, String pattern) {
    //         List<Integer> result = new ArrayList<>();
    //         int n = text.length();
    //         int m = pattern.length();
    //         if (m > n) return result;

    //         // Precompute BASE^m mod MOD
    //         long highPow = 1;
    //         for (int k = 0; k < m - 1; k++) {
    //             highPow = (highPow * BASE) % MOD;
    //         }

    //         // Compute initial hashes
    //         long patternHash = 0;
    //         long windowHash  = 0;
    //         for (int k = 0; k < m; k++) {
    //             patternHash = (patternHash * BASE + (pattern.charAt(k) - 'a' + 1)) % MOD;
    //             windowHash  = (windowHash  * BASE + (text.charAt(k)    - 'a' + 1)) % MOD;
    //         }

    //         for (int i = 0; i <= n - m; i++) {
    //             if (windowHash == patternHash) {
    //                 // Verify to avoid false positives from hash collisions
    //                 if (text.substring(i, i + m).equals(pattern)) {
    //                     result.add(i);
    //                 }
    //             }
    //             // Roll the hash forward
    //             if (i < n - m) {
    //                 windowHash = (windowHash - (text.charAt(i) - 'a' + 1) * highPow % MOD + MOD) % MOD;
    //                 windowHash = (windowHash * BASE + (text.charAt(i + m) - 'a' + 1)) % MOD;
    //             }
    //         }
    //         return result;
    //     }

    //     public static void main(String[] args) {
    //         String text    = "aabxaabyaabyaab";
    //         String pattern = "aabyaab";

    //         List<Integer> matches = search(text, pattern);
    //         for (int idx : matches) {
    //             System.out.println("Pattern found at index: " + idx);
    //         }
    //     }
    // }
    // ```

    // ---

    // ## 5. Time & Space Complexity

    // ### Brute Force
    // | | Complexity | Reasoning |
    // |--|------------|-----------|
    // | **Time** | O(n × m) | For each of the `n-m+1` windows, we compare up to `m` characters |
    // | **Space** | O(1) | No extra structures; only two integer pointers |

    // **Example walkthrough (small):** `n=15, m=7` → up to `15×7 = 105` comparisons.

    // **Worst case:** Pattern = `"aaaaab"`, Text = `"aaaaaaaaa..."` → nearly every position needs full `m` comparisons before mismatch at the last character.

    // ---

    // ### KMP
    // | | Complexity | Reasoning |
    // |--|------------|-----------|
    // | **Time** | **O(n + m)** | LPS build = O(m); Search = O(n) — `i` never decrements, and `j` can only increase as many times as `i` increases, so total steps ≤ 2n |
    // | **Space** | **O(m)** | Only the LPS array of size `m` is stored |

    // **Example:** `n=10⁶, m=10³` → roughly 2×10⁶ operations total, regardless of pattern structure.

    // ---

    // ### Rabin-Karp
    // | | Complexity | Reasoning |
    // |--|------------|-----------|
    // | **Time** | O(n + m) average, O(n×m) worst case | Worst case: all hashes collide (e.g., all same chars) |
    // | **Space** | O(1) | Only hash values stored |

    // ---

    // ## 6. Complete Worked Examples

    // ### Example: KMP on `text = "aabxaabyaabyaab"`, `pattern = "aabyaab"`

    // #### Phase 1: Build LPS for `"aabyaab"`

    // ```
    // Pattern:  a  a  b  y  a  a  b
    // Index:    0  1  2  3  4  5  6
    // ```

    // | Step | i | length | Char Match? | lps[i] | Action |
    // |------|---|--------|-------------|--------|--------|
    // | 1 | 1 | 0 | `a==a` ✅ | 1 | length=1, i=2 |
    // | 2 | 2 | 1 | `b≠a` ❌ | — | length=lps[0]=0 |
    // | 3 | 2 | 0 | `b≠a` ❌ | 0 | i=3 |
    // | 4 | 3 | 0 | `y≠a` ❌ | 0 | i=4 |
    // | 5 | 4 | 0 | `a==a` ✅ | 1 | length=1, i=5 |
    // | 6 | 5 | 1 | `a==a` ✅ | 2 | length=2, i=6 |
    // | 7 | 6 | 2 | `b==b` ✅ | 3 | length=3, i=7 |

    // **Result:** `lps = [0, 1, 0, 0, 1, 2, 3]`

    // ---

    // #### Phase 2: KMP Search

    // ```
    // Text:    a  a  b  x  a  a  b  y  a  a  b  y  a  a  b
    // Index:   0  1  2  3  4  5  6  7  8  9  10 11 12 13 14
    // Pattern: a  a  b  y  a  a  b
    // ```

    // | i | j | T[i] | P[j] | Match? | Action |
    // |---|---|------|------|--------|--------|
    // | 0 | 0 | a | a | ✅ | i=1, j=1 |
    // | 1 | 1 | a | a | ✅ | i=2, j=2 |
    // | 2 | 2 | b | b | ✅ | i=3, j=3 |
    // | 3 | 3 | x | y | ❌ | j=lps[2]=0 |
    // | 3 | 0 | x | a | ❌ | i=4 |
    // | 4 | 0 | a | a | ✅ | i=5, j=1 |
    // | 5 | 1 | a | a | ✅ | i=6, j=2 |
    // | 6 | 2 | b | b | ✅ | i=7, j=3 |
    // | 7 | 3 | y | y | ✅ | i=8, j=4 |
    // | 8 | 4 | a | a | ✅ | i=9, j=5 |
    // | 9 | 5 | a | a | ✅ | i=10, j=6 |
    // | 10 | 6 | b | b | ✅ | i=11, j=7 → **MATCH at index 4!** j=lps[6]=3 |
    // | 11 | 3 | y | y | ✅ | i=12, j=4 |
    // | 12 | 4 | a | a | ✅ | i=13, j=5 |
    // | 13 | 5 | a | a | ✅ | i=14, j=6 |
    // | 14 | 6 | b | b | ✅ | i=15, j=7 → **MATCH at index 8!** j=lps[6]=3 |

    // **Output:**
    // ```
    // Pattern found at index: 4
    // Pattern found at index: 8
    // ```

    // > Notice at index 11 (after first match), `j` jumped to `3` instead of `0`. This is KMP's power — it reused the fact that `"aab"` (last 3 chars of pattern) was already matched.

    // ---

    // ## 7. Edge Cases

    // | Edge Case | Description | Brute Force | KMP | Rabin-Karp |
    // |-----------|-------------|-------------|-----|------------|
    // | **Empty pattern** | `pattern = ""` | Undefined behavior | Guard: return empty | Guard: return empty |
    // | **Pattern longer than text** | `m > n` | Loop never runs (safe) | Guard: return early | Guard: return early |
    // | **Pattern == Text** | `"abc"` in `"abc"` | Finds at index 0 ✅ | Finds at index 0 ✅ | Finds at index 0 ✅ |
    // | **No match** | `"xyz"` in `"abcdef"` | Returns empty ✅ | Returns empty ✅ | Returns empty ✅ |
    // | **All same chars** | `"aaa"` in `"aaaaa"` | O(n×m) worst case ⚠️ | O(n+m) guaranteed ✅ | Hash collisions, O(n×m) ⚠️ |
    // | **Overlapping matches** | `"aa"` in `"aaaa"` | Misses overlaps if not careful | Handled via `lps[j-1]` ✅ | Handled ✅ |
    // | **Single character** | `"a"` in `"a"` | Works ✅ | lps=[0], works ✅ | Works ✅ |
    // | **Unicode / special chars** | Non-ASCII input | Works (Java chars) | Works ✅ | Hash formula needs adjustment ⚠️ |
    // | **Very large input** | n=10⁶ | TLE likely ❌ | Fast ✅ | Fast avg ✅ |

    // ### Overlapping Match Verification (`"aa"` in `"aaaa"`)

    // ```
    // LPS for "aa": [0, 1]

    // i=0,j=0: match → i=1,j=1
    // i=1,j=1: match → i=2,j=2 → MATCH at 0! j=lps[1]=1
    // i=2,j=1: match → i=3,j=2 → MATCH at 1! j=lps[1]=1
    // i=3,j=1: match → i=4,j=2 → MATCH at 2! j=lps[1]=1

    // Output: [0, 1, 2] ✅  (all overlapping matches found)
    // ```

    // ---

    // ## 8. Final Summary

    // ### Approach Comparison

    // | Approach | Time | Space | Reliability | Interview Fit |
    // |----------|------|-------|-------------|---------------|
    // | Brute Force | O(n×m) | O(1) | Fails on large/adversarial input | Only for tiny inputs |
    // | **KMP** ✅ | **O(n+m)** | **O(m)** | **Guaranteed linear** | **Best for interviews** |
    // | Rabin-Karp | O(n+m) avg | O(1) | Hash collisions possible | Great for multi-pattern |
    // | Z-Algorithm | O(n+m) | O(n+m) | Guaranteed linear | Good alternative |

    // ### 🎯 What to Remember
    // > **KMP = Precompute the pattern's self-similarity (LPS array), then use it to skip redundant comparisons during search.**
    // > The LPS array is the heart of KMP — understanding it deeply means you understand the entire algorithm.

    // ---

    // ## 9. Companies & Interview Frequency

    // | Company | Frequency | Notes |
    // |---------|-----------|-------|
    // | 🟠 **Amazon** | ⭐⭐⭐⭐⭐ Very High | Asked extensively for SDE-2 and above roles |
    // | 🔵 **Google** | ⭐⭐⭐⭐⭐ Very High | Classic string algorithm; appears in L4/L5 |
    // | 🔷 **Microsoft** | ⭐⭐⭐⭐ High | Common in SDE II interviews |
    // | 🟣 **Facebook/Meta** | ⭐⭐⭐⭐ High | Combined with other string problems |
    // | 🔴 **Adobe** | ⭐⭐⭐ Medium | Frequently in online assessments |
    // | 🟢 **Uber** | ⭐⭐⭐ Medium | Sometimes Rabin-Karp variant |
    // | 🔵 **LinkedIn** | ⭐⭐⭐ Medium | String manipulation rounds |
    // | 🟡 **Goldman Sachs** | ⭐⭐ Medium-Low | Algorithmic coding rounds |
    // | 🔵 **Samsung** | ⭐⭐⭐ Medium | R&D division interviews |
    // | 🟠 **Flipkart** | ⭐⭐⭐ Medium | Backend engineering roles |

    // ### LeetCode Related Problems
    // | Problem | Difficulty | Uses KMP |
    // |---------|------------|----------|
    // | **#28 — Find the Index of the First Occurrence** | Easy | ✅ Direct KMP |
    // | **#214 — Shortest Palindrome** | Hard | ✅ LPS-based |
    // | **#459 — Repeated Substring Pattern** | Easy | ✅ LPS trick |
    // | **#686 — Repeated String Match** | Medium | ✅ KMP search |
    // | **#1392 — Longest Happy Prefix** | Hard | ✅ Pure LPS |

    // > KMP has appeared in **300+ competitive programming contests** on Codeforces, LeetCode, and HackerRank combined, and is one of the **top 10 most frequently asked string algorithms** in FAANG interviews.
    // @formatter:on
}
