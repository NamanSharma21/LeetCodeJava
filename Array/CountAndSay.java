package Array;

public class CountAndSay {
    public static void main(String[] args) {
        CountAndSay countAndSay = new CountAndSay();
        System.out.println("CountAndSay : " + countAndSay.countAndSay(4));
    }

    /**
     * 
     * https://leetcode.com/explore/interview/card/top-interview-questions-medium/103/array-and-strings/4153/
     * 
     * 
     * The count-and-say sequence is a sequence of digit strings defined by the
     * recursive formula:
     * 
     * countAndSay(1) = "1"
     * countAndSay(n) is the run-length encoding of countAndSay(n - 1).
     * Run-length encoding (RLE) is a string compression method that works by
     * replacing consecutive identical characters (repeated 2 or more times) with
     * the concatenation of the character and the number marking the count of the
     * characters (length of the run). For example, to compress the string "3322251"
     * we replace "33" with "23", replace "222" with "32", replace "5" with "15" and
     * replace "1" with "11". Thus the compressed string becomes "23321511".
     * 
     * Given a positive integer n, return the nth element of the count-and-say
     * sequence.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: n = 4
     * 
     * Output: "1211"
     * 
     * Explanation:
     * 
     * countAndSay(1) = "1"
     * countAndSay(2) = RLE of "1" = "11"
     * countAndSay(3) = RLE of "11" = "21"
     * countAndSay(4) = RLE of "21" = "1211"
     * Example 2:
     * 
     * Input: n = 1
     * 
     * Output: "1"
     * 
     * Explanation:
     * 
     * This is the base case.
     * 
     * 
     * 
     * Constraints:
     * 
     * 1 <= n <= 30
     * 
     * 
     * Follow up: Could you solve it iteratively?
     * 
     */

    public String countAndSay(int n) {
        // String curr = "1";
        // for (int step = 2; step <= n; step++) {
        // StringBuilder sb = new StringBuilder();
        // int i = 0;
        // while (i < curr.length()) {
        // char digit = curr.charAt(i);
        // int count = 1;
        // while (i + 1 < curr.length() && curr.charAt(i + 1) == digit) {
        // count++;
        // i++;
        // }
        // sb.append(count);
        // sb.append(digit);
        // i++;
        // }
        // curr = sb.toString();
        // }
        // return curr;

        if (n == 1) {
            return "1";
        }

        String prev = countAndSay(n - 1);

        int i = 0;

        StringBuilder next = new StringBuilder();
        while (i < prev.length()) {
            char digit = prev.charAt(i);
            int count = 1;
            while (i + 1 < prev.length() && prev.charAt(i + 1) == digit) {
                count++;
                i++;
            }
            next.append(count);
            next.append(digit);
            i++;
        }
        return next.toString();
    }

    /**
     * 
     * 
     * ## 1. Problem Statement

You are given a positive integer `n`.  

You must return the **n-th term** of the **count-and-say sequence** (also known as the look-and-say sequence), defined as: [sparkcodehub](https://www.sparkcodehub.com/leetcode/38/count-and-say)

- `countAndSay(1) = "1"`.
- For `n > 1`, `countAndSay(n)` is obtained by **“reading”** `countAndSay(n-1)`:
  - Group **consecutive identical digits**,
  - For each group, say “`<count><digit>`”,
  - Concatenate all such descriptions to form the new term.

Example sequence (first few terms): [geeksforgeeks](https://www.geeksforgeeks.org/dsa/look-and-say-sequence/)

1. `1`
2. `11`        → “one 1”
3. `21`        → “two 1s”
4. `1211`      → “one 2, one 1”
5. `111221`    → “one 1, one 2, two 1s”
6. `312211`    → “three 1s, two 2s, one 1”

### Input / Output / Constraints

- **Input:** `int n`
  - Typical constraints: `1 <= n <= 30`. [github](https://github.com/doocs/leetcode/blob/main/solution/0000-0099/0038.Count%20and%20Say/README_EN.md)
- **Output:** `String`
  - The n-th term of the count-and-say sequence.

You must compute:  
`countAndSay(n)` as a string, using the rules above.

***

## 2. Intuition

The process is **iterative description** of the previous term:

- Start from base `"1"`.
- Each step, “read” current term **left to right**:
  - Count how many times the same digit repeats consecutively.
  - Append the count and that digit to build the next term.
- Repeat this `n-1` times.

For example, from `"21"` to `"1211"`: [sparkcodehub](https://www.sparkcodehub.com/leetcode/38/count-and-say)

- `"21"`:  
  - One `'2'` → `"12"`  
  - One `'1'` → `"11"`  
  - Result `"1211"`.

What makes it interesting:

- It’s a good test of **string scanning** and **run-length encoding**.
- You must be careful:
  - To correctly handle runs of length > 1.
  - To handle the **last run** (no `i+1` element after the end).
- Both iterative and recursive formulations are natural:
  - Iterative: loop generating terms 2..n.
  - Recursive: `countAndSay(n)` calls `countAndSay(n-1)` then processes one level. [educative](https://www.educative.io/answers/what-is-the-count-and-say-sequence-algorithm)

***

## 3. Approach Overview

Let’s denote `curr` as the current term string.

### Approach 1 – Iterative simulation (recommended)

- **Key idea:**  
  Start with `"1"` and iteratively build each next term: for each `curr`, scan and build `next` by counting runs of the same digit. [leetcode-in-java.github](https://leetcode-in-java.github.io/src/main/java/g0001_0100/s0038_count_and_say/)
- **Use when:**  
  This is the standard and optimal solution for constraints up to 30.
- **Complexity:**  
  Time ~ O(total length of all terms up to n), which is exponential in n but tiny for n ≤ 30. In practice, O(L) per step, L = length of term.

### Approach 2 – Recursive definition

- **Key idea:**  
  Use the recurrence:
  - If `n == 1` → `"1"`.
  - Else `countAndSay(n) = describe(countAndSay(n-1))`.
- **Use when:**  
  More “mathematical”; nice to show recurrence. Slightly more overhead than iterative.
- **Complexity:**  
  Same underlying work; recursion depth = n. [geeksforgeeks](https://www.geeksforgeeks.org/dsa/look-and-say-sequence/)

Approach 1 is preferred in interviews (straightforward and iterative).

***

## 4. Detailed Solutions in Java

### 4.1 Approach 1 – Iterative Simulation (Run-Length Encoding)

#### Algorithm (step-by-step)

1. If `n == 1`, return `"1"`.
2. Set `curr = "1"`.
3. Repeat `n-1` times:
   - Initialize an empty `StringBuilder next`.
   - Scan `curr` from left to right, using an index `i`:
     - Let `count = 1`.
     - While `i + 1 < curr.length()` and `curr.charAt(i) == curr.charAt(i+1)`:
       - Increment `count` and `i`.
     - Append `count` (as string) and `curr.charAt(i)` to `next`.
     - Move `i` to next position `i+1` and continue.
   - After finishing the scan of `curr`, set `curr = next.toString()`.
4. After finishing `n-1` iterations, return `curr`. [educative](https://www.educative.io/answers/what-is-the-count-and-say-sequence-algorithm)

#### Java Code (iterative, recommended)

```java
public class CountAndSayIterative {

    public String countAndSay(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }

        String curr = "1"; // base case: term 1

        for (int step = 2; step <= n; step++) {
            StringBuilder next = new StringBuilder();
            int i = 0;

            while (i < curr.length()) {
                char digit = curr.charAt(i);
                int count = 1;

                // Count how many times 'digit' repeats consecutively
                while (i + 1 < curr.length() && curr.charAt(i + 1) == digit) {
                    count++;
                    i++;
                }

                // Append "<count><digit>" to the next term
                next.append(count);
                next.append(digit);

                // Move to the next new digit
                i++;
            }

            curr = next.toString();
        }

        return curr;
    }
}
```

#### Complexity

- Let `L_k` be the length of the k-th term. There are n-1 transformations.
- For each transformation, we scan `curr` once → O(L_k).
- Total time: O(L_1 + L_2 + ... + L_n). For n ≤ 30, this is small and acceptable. [github](https://github.com/doocs/leetcode/blob/main/solution/0000-0099/0038.Count%20and%20Say/README_EN.md)
- Space:
  - We store `curr` and `next` at each step, each of length O(L_k).
  - Extra space at any time: O(L_max) where `L_max` is max term length up to n.
  - For constraints, **O(L_max)**, which is fine.

Example small sizes:  
- n=4: terms lengths 1,2,2,4 → at most 4 operations per step.
- n up to 10: length growth is noticeable but still tiny.

#### Worked Example – `n = 4`

We want `countAndSay(4)`.

- Start: `curr = "1"` (n=1).

**Step 2: build term 2 from `"1"`**

- `curr = "1"`, i=0:
  - digit='1', count=1.
  - i+1=1 == len → stop inner.
  - append "1" + "1" → `"11"`.
  - i=1 → end loop.
- `curr = "11"`.

**Step 3: build term 3 from `"11"`**

- `curr = "11"`, i=0:
  - digit='1', count=1.
  - i+1=1 < 2 and curr=='1' → count=2, i=1. [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
  - i+1=2 == len → stop.
  - append "2" + "1" → `"21"`.
  - i=2 → end.

- `curr = "21"`.

**Step 4: build term 4 from `"21"`**

- `curr = "21"`, i=0:
  - digit='2', count=1.
  - i+1=1 <2 and curr=='1' (different) → stop. [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
  - append "1" + "2" → `"12"`.
  - i=1.
- i=1:
  - digit='1', count=1.
  - i+1=2 == len → stop.
  - append "1" + "1" → `"1211"`.
  - i=2 → end.

Final: `curr = "1211"` → return `"1211"`. [talentd](https://www.talentd.in/dsa-corner/questions/count-and-say)

***

### 4.2 Approach 2 – Recursive Definition

#### Algorithm

Define:

- `countAndSay(1) = "1"`.
- For `n > 1`:
  1. Compute `prev = countAndSay(n-1)` recursively.
  2. Run the same run-length-encoding process on `prev` to produce `result`.
  3. Return `result`. [sparkcodehub](https://www.sparkcodehub.com/leetcode/38/count-and-say)

#### Java Code (recursive)

```java
public class CountAndSayRecursive {

    public String countAndSay(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        if (n == 1) {
            return "1";
        }

        // Recursively get the (n-1)th term
        String prev = countAndSay(n - 1);

        // Describe prev to build the nth term
        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < prev.length()) {
            char digit = prev.charAt(i);
            int count = 1;

            while (i + 1 < prev.length() && prev.charAt(i + 1) == digit) {
                count++;
                i++;
            }

            result.append(count);
            result.append(digit);

            i++;
        }

        return result.toString();
    }
}
```

#### Complexity

- Recursion depth: n (≤ 30).
- Each level computes a description of the previous term → same work as iterative, but in reverse order (top-down).
- Time: same O(L_1 + ... + L_n). [geeksforgeeks](https://www.geeksforgeeks.org/dsa/look-and-say-sequence/)
- Space:
  - O(L_max) for building strings.
  - Plus recursion stack O(n), which is negligible for n ≤ 30.

#### Worked Example – `n = 4`

Call stack:

- `countAndSay(4)`:
  - Needs `countAndSay(3)`:
    - Needs `countAndSay(2)`:
      - Needs `countAndSay(1)`:
        - base → `"1"`.
      - Describe "1" → `"11"` (term 2).
    - Describe "11" → `"21"` (term 3).
  - Describe "21" → `"1211"` (term 4). [sparkcodehub](https://www.sparkcodehub.com/leetcode/38/count-and-say)

Description steps at each level are identical to the iterative example, just executed bottom-up via recursion.

***

## 5. Edge Cases

1. **n = 1**
   - Return `"1"` directly; both implementations handle this base case.

2. **Minimum and maximum n**:
   - `n = 1` → `"1"`.
   - `n` up to 30: sequence length grows but remains manageable in memory; iterative and recursive both fine. [github](https://github.com/doocs/leetcode/blob/main/solution/0000-0099/0038.Count%20and%20Say/README_EN.md)

3. **Empty / invalid n**:
   - Problem guarantees `n >= 1`. If not, you can throw an exception or define behavior.

4. **Digits beyond 1–3** in later terms:
   - Sequence naturally introduces digits like 2,3, etc. as counts; code handles them as characters; logic is generic for any digit characters.

No overflow issues since we store everything as strings.

***

## 6. Final Summary

- **Task:** Return the n-th element of the **count-and-say** (look-and-say) sequence.
- **Core pattern:** This is just repeated **run-length encoding** of the previous string term.

**Approaches:**

- **Iterative simulation (recommended):**
  - Start from `"1"`.
  - For each iteration: scan current term, count consecutive same digits, append `<count><digit>` to build next term.
  - Straightforward, O(total length) time, O(L_max) space.

- **Recursive:**
  - `countAndSay(n)` calls `countAndSay(n-1)` then describes it.
  - Same work but uses recursion; good if you want to emphasize the recurrence.

What to remember:

> “Count and Say” is a clean example of **string run-length encoding applied repeatedly**. The main skills are careful string scanning, correctly handling the last run, and choosing either iterative or recursive generation.

If you want, next we can trace your own Java implementation for a larger `n` like 6 to make sure you’re comfortable with how the terms evolve.
     */
}
