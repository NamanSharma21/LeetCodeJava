package Array;

public class LongestPalindromicSubstring {
    public static void main(String[] args) {
        LongestPalindromicSubstring longestPalindromicSubstring = new LongestPalindromicSubstring();
        System.out.println("LongestPalindromicSubstring : " + longestPalindromicSubstring.longestPalindrome("babad"));
        System.out.println("LongestPalindromicSubstring : " + longestPalindromicSubstring.longestPalindrome("cbbd"));
    }

    /**
     * https://leetcode.com/explore/interview/card/top-interview-questions-medium/103/array-and-strings/780/
     * 
     * Given a string s, return the longest palindromic substring in s.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: s = "babad"
     * Output: "bab"
     * Explanation: "aba" is also a valid answer.
     * Example 2:
     * 
     * Input: s = "cbbd"
     * Output: "bb"
     * 
     * 
     * Constraints:
     * 
     * 1 <= s.length <= 1000
     * s consist of only digits and English letters.
     * 
     */
    int bestLength;
    int bestStart;

    public String longestPalindrome(String s) {
        int n = s.length();
        if (n <= 1) {
            return s;
        }

        bestStart = 0;
        bestLength = 1;

        for (int center = 0; center < n; center++) {
            expandFromCenter(s, center, center);
            expandFromCenter(s, center, center + 1);
        }
        return s.substring(bestStart, bestStart + bestLength);
    }

    private void expandFromCenter(String s, int left, int right) {
        int n = s.length();
        while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
            int currentLength = right - left + 1;
            if (currentLength > bestLength) {
                bestLength = currentLength;
                bestStart = left;
            }
            left--;
            right++;
        }
    }


    /**
     * 
     * ## 1. Problem Statement

You are given a string `s`.  

You must find the **longest contiguous substring** of `s` that is a **palindrome**, and **return that substring itself** (not just its length). [geeksforgeeks](https://www.geeksforgeeks.org/dsa/longest-palindromic-substring/)

- A **palindrome** reads the same forwards and backwards, e.g., `"racecar"`, `"abba"`.
- Substring means contiguous: indices `i..j` with no gaps.

### Input / Output / Constraints

- **Input:** `String s`
  - Typical constraints:
    - `1 <= s.length() <= 1000` (LeetCode 5 style). [leetcode-in-java.github](https://leetcode-in-java.github.io/src/main/java/g0001_0100/s0005_longest_palindromic_substring/)
    - `s` consists of letters/digits (exact alphabet not important for algorithm).
- **Output:** `String`
  - Any longest palindromic substring; if multiple exist, returning any one is acceptable. [interviewbit](https://www.interviewbit.com/blog/longest-palindromic-substring/)

Example:

- `s = "babad"` → `"bab"` or `"aba"` are valid answers. [leetcode-in-java.github](https://leetcode-in-java.github.io/src/main/java/g0001_0100/s0005_longest_palindromic_substring/)
- `s = "cbbd"` → `"bb"`. [interviewbit](https://www.interviewbit.com/blog/longest-palindromic-substring/)

You must compute:  
Indices `(start, end)` such that `s.substring(start, end+1)` is a palindrome and has **maximum length**, then return that substring.

***

## 2. Intuition

A palindrome is symmetric around its **center**.

- For odd length (like `"racecar"`), there is a single center character.
- For even length (like `"abba"`), the center is **between** two characters.

Human reasoning:

- Pick a position (or gap) as center.
- Expand outwards (left--, right++) as long as characters match.
- Track the longest one you see.

Brute force would try all substrings and check each, but that’s O(n³).  
The key realization:

> Any palindromic substring has some center; there are only O(n) centers (2n−1 including gaps). Expanding from each center takes O(n) in worst case → O(n²) overall, which is fine for n ≤ 1000. [digitalocean](https://www.digitalocean.com/community/tutorials/longest-palindrome-substring-string-java)

More advanced: Manacher’s algorithm gets O(n) time, but is more complex and rarely required in interviews. [en.wikipedia](https://en.wikipedia.org/wiki/Longest_palindromic_substring)

***

## 3. Approach Overview

Let `n = s.length()`.

### Approach 1 – Brute force over all substrings

- **Key idea:**  
  For every pair `(i, j)`, check if `s[i..j]` is a palindrome by comparing characters from both ends.
- **When to use:**  
  Only for understanding; too slow for n up to 1000.
- **Complexity:**  
  - O(n²) substrings, each check up to O(n) → O(n³) time.

### Approach 2 – DP (2D table, O(n²) time and O(n²) space)

- **Key idea:**  
  `dp[i][j] = true` if substring `s[i..j]` is palindrome.  
  Build up from length 1 and 2 substrings to longer ones. [alexanderobregon.substack](https://alexanderobregon.substack.com/p/leetcode-5-longest-palindromic-substring)
- **When to use:**  
  Good educational approach; slightly heavier space.
- **Complexity:**  
  - O(n²) states, O(1) per state → O(n²) time, O(n²) space.

### Approach 3 – Expand Around Center (O(n²) time, O(1) space) – **most common**

- **Key idea:**  
  For each center (each index and each gap between two indices), expand while characters match. Track max length and start index. [programming.arhantjain](https://www.programming.arhantjain.com/Java/String/LongestPalindromicSubstring.html)
- **When to use:**  
  Standard interview solution; simple and efficient.
- **Complexity:**  
  - There are 2n−1 centers; each expansion O(n) worst-case → O(n²) time, O(1) extra space.

### Approach 4 – Manacher’s Algorithm (O(n) time, O(n) space) – **optimal but advanced**

- **Key idea:**  
  Transform string with separators (like `#`) to handle odd/even uniformly, then maintain an array of palindrome radii using mirror properties to avoid re-work. [geeksforgeeks](https://www.geeksforgeeks.org/dsa/longest-palindromic-substring/)
- **When to use:**  
  Rarely required in interviews; valuable for algorithmic completeness.

In practice, **Approach 3 (Expand Around Center)** is the recommended one.

***

## 4. Detailed Solutions in Java

### 4.1 Approach 1 – Brute Force

#### Algorithm

1. If `s` is empty or length 1, return `s`.
2. Initialize:
   - `int maxLen = 1;`
   - `int start = 0;`
3. For all `i` from 0 to n−1:
   - For all `j` from i to n−1:
     - If `(j - i + 1) <= maxLen`, you can optionally skip because it can’t beat current max.
     - Check if `s[i..j]` is palindrome by two pointers `(l=i, r=j)`:
       - While `l < r` and `s.charAt(l) == s.charAt(r)`, increment l, decrement r.
       - If `l >= r`, it is palindrome; if its length > `maxLen`, update `maxLen` and `start = i`.
4. Return `s.substring(start, start + maxLen)`.

#### Java Code (naive baseline)

```java
public class LongestPalindromicSubstringBrute {

    public String longestPalindrome(String s) {
        int n = s.length();
        if (n <= 1) return s;

        int maxLen = 1;
        int start = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {

                int len = j - i + 1;
                if (len <= maxLen) {
                    continue; // optional optimization
                }

                if (isPalindrome(s, i, j)) {
                    maxLen = len;
                    start = i;
                }
            }
        }

        return s.substring(start, start + maxLen);
    }

    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
```

#### Complexity

- Outer loop `i`: n.
- Inner loop `j`: ~n/2 on average → O(n²) substrings.
- `isPalindrome` worst-case O(n).
- Time: **O(n³)** worst case. [interviewbit](https://www.interviewbit.com/blog/longest-palindromic-substring/)
- Space: O(1) extra (besides result).

Example: n=1000 → about 10^9 char comparisons worst-case; too slow.

#### Worked Example – `"babad"` (high-level)

You’d check many substrings; eventually you see that `"bab"` and `"aba"` are palindromes of length 3 and no longer palindrome exists. Approach works conceptually but is inefficient.

***

### 4.2 Approach 2 – Dynamic Programming (O(n²) / O(n²))

#### Idea

`dp[i][j]` = true if `s[i..j]` is a palindrome.

Recurrence: [alexanderobregon.substack](https://alexanderobregon.substack.com/p/leetcode-5-longest-palindromic-substring)

- Length 1 substrings: `dp[i][i] = true`.
- Length 2 substrings: `dp[i][i+1] = (s[i] == s[i+1])`.
- For length ≥ 3:  
  `dp[i][j] = (s[i] == s[j]) && dp[i+1][j-1]`.

We fill the table in order of increasing substring length.

#### Algorithm

1. If n ≤ 1, return s.
2. Create `boolean[][] dp = new boolean[n][n];`.
3. Initialize:
   - All `dp[i][i] = true` (length 1).
   - Track `start = 0`, `maxLen = 1`.
4. Handle length 2 substrings:
   - For i in 0..n-2:
     - If `s.charAt(i) == s.charAt(i+1)`:
       - `dp[i][i+1] = true;`, `start = i;`, `maxLen = 2;`.
5. For length `len` from 3 to n:
   - For `i` from 0 to `n-len`:
     - `j = i + len - 1`.
     - If `s.charAt(i) == s.charAt(j)` and `dp[i+1][j-1]` is true:
       - `dp[i][j] = true;`
       - if `len > maxLen`, update `start` and `maxLen`.
6. Return `s.substring(start, start + maxLen)`.

#### Java Code

```java
public class LongestPalindromicSubstringDP {

    public String longestPalindrome(String s) {
        int n = s.length();
        if (n <= 1) {
            return s;
        }

        boolean[][] dp = new boolean[n][n];

        int start = 0;
        int maxLen = 1;

        // Length 1 substrings
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        // Length 2 substrings
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                start = i;
                maxLen = 2;
            }
        }

        // Length >= 3
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    if (len > maxLen) {
                        start = i;
                        maxLen = len;
                    }
                }
            }
        }

        return s.substring(start, start + maxLen);
    }
}
```

#### Complexity

- DP table size: n x n → **O(n²)** space. [alexanderobregon.substack](https://alexanderobregon.substack.com/p/leetcode-5-longest-palindromic-substring)
- Filling:
  - O(n) for initialization.
  - Nested loops over length and starting index: roughly ~n²/2 operations.
  - Time: **O(n²)**.

For n=1000 → 10^6 boolean entries; feasible.

#### Worked Example – `"babad"` (DP table sketch)

`s="b a b a d"`, n=5, indices 0..4.

- Step 1: `dp[i][i] = true` for all i; maxLen=1, start=0.
- Step 2: length 2:
  - (0,1) `"ba"`: b≠a → false.
  - (1,2) `"ab"`: a≠b → false.
  - (2,3) `"ba"`: b≠a → false.
  - (3,4) `"ad"`: a≠d → false.
- Step 3: length 3:
  - (0,2) `"bab"`:
    - s==s (b==b), dp is true → dp=true. [geeksforgeeks](https://www.geeksforgeeks.org/dsa/next-permutation/)
    - maxLen=3, start=0.
  - (1,3) `"aba"`:
    - s==s (a==a), dp true → dp=true. [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
    - maxLen=3, start could become 1.
  - (2,4) `"bad"`:
    - b!=d → false.
- Larger lengths will not produce longer palindromes in this example.

Result: `start=0` or `1`, `maxLen=3` → `"bab"` or `"aba"`.

***

### 4.3 Approach 3 – Expand Around Center (O(n²), O(1)) – Recommended

#### Idea

Every palindrome is defined by its **center**. There are two types of centers:

- Odd-length center at index `i`: like `"racecar"` centered at 'e'.
- Even-length center between indices `i` and `i+1`: like `"abba"` centered between the two 'b's.

For each possible center `(left, right)`:

- Expand:
  - While `left >= 0`, `right < n`, and `s[left] == s[right]`:
    - Update best if `(right-left+1)` > current max.
    - left--, right++.

We try:

- All `i` as odd centers (`i,i`).
- All gaps (`i,i+1`) as even centers.

#### Algorithm

1. If `s` length ≤ 1 → return `s`.
2. `int start = 0; int maxLen = 1;`
3. For `center` from 0 to n−1:
   - Expand odd: `expandFromCenter(s, center, center)`
   - Expand even: `expandFromCenter(s, center, center+1)`
   - Each expansion returns (or updates) best palindrome range.
4. Return `s.substring(start, start + maxLen)`.

We can implement `expandFromCenter` as a helper that updates external `start/maxLen`, or returns indices.

#### Java Code

```java
public class LongestPalindromicSubstringExpand {

    private int bestStart;
    private int bestLen;

    public String longestPalindrome(String s) {
        int n = s.length();
        if (n <= 1) {
            return s;
        }

        bestStart = 0;
        bestLen = 1;

        for (int center = 0; center < n; center++) {
            // Odd length: center at (center, center)
            expandFromCenter(s, center, center);

            // Even length: center between center and center+1
            expandFromCenter(s, center, center + 1);
        }

        return s.substring(bestStart, bestStart + bestLen);
    }

    private void expandFromCenter(String s, int left, int right) {
        int n = s.length();

        while (left >= 0 && right < n &&
               s.charAt(left) == s.charAt(right)) {

            int currentLen = right - left + 1;
            if (currentLen > bestLen) {
                bestLen = currentLen;
                bestStart = left;
            }

            left--;
            right++;
        }
    }
}
```

#### Complexity

- There are `n` choices for odd centers and `n-1` for even → O(n) centers.
- For each center, expansion will move `left` and `right` outward until mismatch or bounds; each character participates in at most O(1) expansion steps per center on average; worst-case O(n) per center but still O(n²) overall for n ≤ 1000. [digitalocean](https://www.digitalocean.com/community/tutorials/longest-palindrome-substring-string-java)
- Time: **O(n²)**.
- Space: only a few variables → **O(1)** extra.

#### Worked Example – `"babad"` (full center walk)

`s="b a b a d"`, indices 0..4.

We maintain `bestStart`, `bestLen`.

Start: `bestStart=0`, `bestLen=1`.

1) center=0:

- Odd center (0,0):
  - left=0,right=0: 'b'=='b' → len=1 (no change).
  - next left=-1,right=1 → stop.
- Even center (0,1):
  - left=0,right=1: 'b'!='a' → stop.

2) center=1:

- Odd center (1,1):
  - left=1,right=1: 'a'=='a' → len=1.
  - left=0,right=2: 'b'=='b' → len=3 > bestLen=1:
    - bestLen=3, bestStart=0.
  - left=-1,right=3 → stop.
- Even center (1,2):
  - left=1,right=2: 'a'!='b' → stop.

3) center=2:

- Odd center (2,2):
  - left=2,right=2: 'b'=='b' → len=1.
  - left=1,right=3: 'a'=='a' → len=3 == bestLen (could keep old).
  - left=0,right=4: 'b'!= 'd' → stop.
- Even center (2,3):
  - left=2,right=3: 'b'!='a' → stop.

4) center=3:

- Odd (3,3) gives palindrome `"a"` only.
- Even (3,4) `a` vs `d` → no.

5) center=4:

- Odd (4,4) `"d"`, even beyond bounds.

Final best: `bestStart=0`, `bestLen=3` → `"bab"` (or `"aba"` if you choose center=2 as tie-breaking differently).

***

### 4.4 Approach 4 – Manacher’s Algorithm (O(n))

High level only:

- Transform `s` into a new string `t` with separators and sentinels: e.g., `"abba"` → `"^#a#b#b#a#$"`.
- Maintain an array `p[i]` = radius of palindrome around center `i` in transformed string.
- Use a current rightmost palindrome `[L, R]` and mirror `i' = L+R-i` to reuse computations; expand where necessary. [en.wikipedia](https://en.wikipedia.org/wiki/Longest_palindromic_substring)
- After computing `p`, find the center with max radius and map back to original indices.

Time O(n), space O(n), but implementation is more complex and typically not required in interviews.

***

## 5. Edge Cases

1. **Length 1:** `"a"`
   - Longest palindrome is `"a"`.

2. **All characters same:** `"aaaa"`
   - Whole string is palindrome; result `"aaaa"`.

3. **No palindromes longer than 1:** `"abc"`
   - Each char alone; any 1-length substring is fine; return `"a"`, `"b"`, or `"c"`.

4. **Even-length palindrome only:** `"cbbd"`
   - `"bb"` is result; expand-around-center handles even centers explicitly.

5. **Empty string** (if allowed):
   - Return `""`.

All approaches handle these; just watch for index bounds.

***

## 6. Final Summary

- **Task:** Return the longest palindromic substring (contiguous) of a given string.
- **Core idea:** Palindromes are symmetric around a center. Expanding from centers is natural and efficient.

**Approaches:**

- Brute force: O(n³) – too slow, but conceptually straightforward.
- DP: O(n²) time & space – good for learning palindromic DP.
- **Expand around center:** O(n²) time, O(1) space – **recommended in practice**.
- Manacher: O(n) time, O(n) space – optimal but complex.

Key pattern to remember:

> Longest Palindromic Substring is the canonical **“expand around center”** problem. Think in terms of centers (both odd and even) and two-pointer expansion.

If you’d like, next step could be: you implement the expand-around-center Java version yourself and we walk through it on a custom tricky string like `"forgeeksskeegfor"` to see how it finds `"geeksskeeg"`.
     * 
     */
}
