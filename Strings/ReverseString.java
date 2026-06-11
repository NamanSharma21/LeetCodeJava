package Strings;

import java.util.Arrays;

public class ReverseString {
    public static void main(String[] args) {
        ReverseString reverseString = new ReverseString();
        reverseString.reverseString(new char[] { 'h', 'e', 'l', 'l', 'o' });
        reverseString.reverseString(new char[] { 'r', 'a', 'c', 'e', 'a', 'c', 'a', 'r' });
        reverseString.reverseStringReccursion(new char[] { 'h', 'e', 'l', 'l', 'o' });
        reverseString.reverseStringReccursion(new char[] { 'r', 'a', 'c', 'e', 'a', 'c', 'a', 'r' });
    }

    // @formatter:off
    /*
     * Write a function that reverses a string. The input string is given as an
     * array of characters s.
     * 
     * You must do this by modifying the input array in-place with O(1) extra
     * memory.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: s = ["h","e","l","l","o"]
     * Output: ["o","l","l","e","h"]
     * Example 2:
     * 
     * Input: s = ["H","a","n","n","a","h"]
     * Output: ["h","a","n","n","a","H"]
     * 
     * 
     * Constraints:
     * 
     * 1 <= s.length <= 105
     * s[i] is a printable ascii character.
     */
    // @formatter:on

    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        System.out.println("Original : " + Arrays.toString(s));
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
        System.out.println("Reversed : " + Arrays.toString(s));
    }

    public void reverseStringReccursion(char[] s) {
        System.out.println("Original : " + Arrays.toString(s));
        revRec(s, 0, s.length - 1);
        System.out.println("Reversed : " + Arrays.toString(s));
    }

    public void revRec(char[] s, int left, int right) {
        if (left >= right)
            return;
        char temp = s[left];
        s[left] = s[right];
        s[right] = temp;
        revRec(s, left + 1, right - 1);
    }
}

// @formatter:off
/*
 * =============================================================================
 * Reverse String — Deep Dive
 * =============================================================================
 *
 * -----------------------------------------------------------------------------
 * 1. PROBLEM STATEMENT
 * -----------------------------------------------------------------------------
 *
 * In Plain Terms:
 *   Given a string (or character array), reverse it IN-PLACE — meaning you must
 *   modify the original data structure without allocating a new one for the result.
 *
 * LeetCode 344 — Official Format:
 *   - Input:       char[] s — a character array
 *   - Output:      void — modify the array in-place; nothing is returned
 *   - Constraints:
 *       * 1 <= s.length <= 10^5
 *       * s[i] is a printable ASCII character
 *       * Must be done IN-PLACE with O(1) extra memory
 *
 * What Exactly Needs to Be Done:
 *   Rearrange the characters so the first becomes the last, the second becomes
 *   the second-to-last, and so on — WITHOUT creating a new array.
 *
 * -----------------------------------------------------------------------------
 * 2. INTUITION
 * -----------------------------------------------------------------------------
 *
 * How a Human Thinks About It:
 *   Imagine a row of numbered seats. To reverse the seating order, you don't
 *   need to build a new row — you simply swap the person in seat 1 with seat N,
 *   then seat 2 with seat N-1, and keep moving inward until you reach the middle.
 *   Once the two pointers meet, every element has been swapped exactly once.
 *
 * Why It's Interesting:
 *   - It teaches the TWO-POINTER TECHNIQUE — one of the most reused patterns
 *     in competitive programming.
 *   - The in-place constraint forces you to think about SPACE EFFICIENCY.
 *   - It appears trivially easy but has real depth: Unicode characters, surrogate
 *     pairs, and multi-byte encodings make real-world reversal significantly harder.
 *
 * -----------------------------------------------------------------------------
 * 3. APPROACH OVERVIEW
 * -----------------------------------------------------------------------------
 *
 *   #  | Approach                        | Extra Space | Time | When to Use
 *   ---+---------------------------------+-------------+------+----------------------
 *   1  | Brute Force (new array)         | O(n)        | O(n) | Never in interviews
 *   2  | StringBuilder / Stack           | O(n)        | O(n) | Conceptual only
 *   3* | Two-Pointer In-Place  ← OPTIMAL | O(1)        | O(n) | Always
 *   4  | Recursive Two-Pointer           | O(n) stack  | O(n) | Educational only
 *
 *   * OPTIMAL: Two-Pointer In-Place — linear time, constant space, zero allocations.
 *
 * -----------------------------------------------------------------------------
 * 4. DETAILED SOLUTIONS IN JAVA
 * -----------------------------------------------------------------------------
 *
 * ─── APPROACH 1: Brute Force (New Array) ─────────────────────────────────────
 *
 * Algorithm:
 *   1. Create a new char[] of the same length.
 *   2. Iterate from the end of s to the beginning, filling the new array front-to-back.
 *   3. Copy everything back into s.
 *
 * Code:
 *
 *   public void reverseString(char[] s) {
 *       int n = s.length;
 *       char[] reversed = new char[n]; // extra O(n) space — violates constraint
 *
 *       // Fill reversed array from back to front
 *       for (int i = 0; i < n; i++) {
 *           reversed[i] = s[n - 1 - i];
 *       }
 *
 *       // Copy back into original array
 *       for (int i = 0; i < n; i++) {
 *           s[i] = reversed[i];
 *       }
 *   }
 *
 *   ⚠ NOT acceptable in an interview for this problem — uses O(n) space.
 *
 * ─── APPROACH 2: Stack-Based ──────────────────────────────────────────────────
 *
 * Algorithm:
 *   1. Push all characters onto a Stack<Character>.
 *   2. Pop them back into s — LIFO order naturally reverses the sequence.
 *
 * Code:
 *
 *   import java.util.Stack;
 *
 *   public void reverseString(char[] s) {
 *       Stack<Character> stack = new Stack<>();
 *
 *       // Push all characters onto the stack
 *       for (char c : s) {
 *           stack.push(c);
 *       }
 *
 *       // Pop back in reverse order
 *       for (int i = 0; i < s.length; i++) {
 *           s[i] = stack.pop();
 *       }
 *   }
 *
 *   ⚠ O(n) space due to the stack. Good for teaching LIFO but not optimal here.
 *
 * ─── APPROACH 3: Two-Pointer In-Place (OPTIMAL) ★ ────────────────────────────
 *
 * Algorithm Step-by-Step:
 *   1. Place pointer `left`  at index 0       (start of array).
 *   2. Place pointer `right` at index n - 1   (end of array).
 *   3. WHILE left < right:
 *        - Swap s[left] and s[right] using a temp variable.
 *        - Increment left.
 *        - Decrement right.
 *   4. When left >= right, every pair has been swapped — done.
 *
 * Code:
 *
 *   public void reverseString(char[] s) {
 *       int left  = 0;
 *       int right = s.length - 1;
 *
 *       while (left < right) {
 *           // Swap characters at left and right pointers
 *           char temp = s[left];
 *           s[left]   = s[right];
 *           s[right]  = temp;
 *
 *           left++;
 *           right--;
 *       }
 *   }
 *
 *   // Bonus: XOR swap (no temp variable) — interview curiosity
 *   public void reverseStringXOR(char[] s) {
 *       int left  = 0;
 *       int right = s.length - 1;
 *
 *       while (left < right) {
 *           s[left]  ^= s[right];
 *           s[right] ^= s[left];
 *           s[left]  ^= s[right];
 *           left++;
 *           right--;
 *       }
 *   }
 *
 * ─── APPROACH 4: Recursive Two-Pointer ───────────────────────────────────────
 *
 * Algorithm:
 *   Recursively swap the outermost characters, then recurse on the inner subarray.
 *
 * Code:
 *
 *   public void reverseString(char[] s) {
 *       reverseHelper(s, 0, s.length - 1);
 *   }
 *
 *   private void reverseHelper(char[] s, int left, int right) {
 *       // Base case: pointers have met or crossed
 *       if (left >= right) return;
 *
 *       // Swap outermost characters
 *       char temp = s[left];
 *       s[left]   = s[right];
 *       s[right]  = temp;
 *
 *       // Recurse on the inner portion
 *       reverseHelper(s, left + 1, right - 1);
 *   }
 *
 *   ⚠ O(n/2) recursive calls → O(n) call stack space.
 *     Risk of StackOverflowError for very large arrays (10^5 chars).
 *
 * -----------------------------------------------------------------------------
 * 5. TIME & SPACE COMPLEXITY (WITH REASONING)
 * -----------------------------------------------------------------------------
 *
 *   Approach              | Time  | Why                          | Space | Why
 *   ----------------------+-------+------------------------------+-------+---------------------------
 *   Brute Force           | O(n)  | Two linear passes            | O(n)  | New array of size n
 *   Stack                 | O(n)  | Push n + pop n               | O(n)  | Stack holds n chars
 *   Two-Pointer ★         | O(n)  | n/2 swaps, each O(1)        | O(1)  | Only left, right, temp
 *   Recursive             | O(n)  | n/2 recursive calls          | O(n)  | Call stack depth n/2
 *
 * Worked Complexity Example (n = 100,000):
 *   - Two-Pointer : exactly 50,000 swaps → ~150,000 operations total. Runs in microseconds.
 *   - Recursive   : 50,000 stack frames → potential stack overflow risk (avoid this pattern).
 *
 * -----------------------------------------------------------------------------
 * 6. COMPLETE WORKED EXAMPLES
 * -----------------------------------------------------------------------------
 *
 * ─── Example 1: Two-Pointer on ['h','e','l','l','o'] ─────────────────────────
 *
 *   Initial:  [h, e, l, l, o]
 *              ^           ^
 *            left=0     right=4
 *
 *   Step | left | right | Action           | Array State
 *   -----+------+-------+------------------+-------------------
 *     1  |  0   |   4   | swap s[0] & s[4] | [o, e, l, l, h]
 *     2  |  1   |   3   | swap s[1] & s[3] | [o, l, l, e, h]
 *     3  |  2   |   2   | left>=right STOP | [o, l, l, e, h]
 *
 *   Output: ['o','l','l','e','h'] ✓
 *
 * ─── Example 2: Two-Pointer on ['A','B','C','D'] (even length) ───────────────
 *
 *   Initial:  [A, B, C, D]
 *              ^        ^
 *            left=0  right=3
 *
 *   Step | left | right | Action           | Array State
 *   -----+------+-------+------------------+-------------------
 *     1  |  0   |   3   | swap s[0] & s[3] | [D, B, C, A]
 *     2  |  1   |   2   | swap s[1] & s[2] | [D, C, B, A]
 *     3  |  2   |   1   | left>=right STOP | [D, C, B, A]
 *
 *   Output: ['D','C','B','A'] ✓
 *
 * ─── Example 3: Recursive on ['1','2','3'] ───────────────────────────────────
 *
 *   Call 1: reverseHelper(s, 0, 2) → swap s[0] & s[2] → ['3','2','1']
 *   Call 2: reverseHelper(s, 1, 1) → left==right → BASE CASE, return
 *
 *   Output: ['3','2','1'] ✓
 *
 * -----------------------------------------------------------------------------
 * 7. EDGE CASES
 * -----------------------------------------------------------------------------
 *
 *   Edge Case          | Input              | Expected Output    | Two-Pointer Handles?
 *   -------------------+--------------------+--------------------+------------------------------
 *   Single character   | ['a']              | ['a']              | ✓ loop never runs
 *   Two characters     | ['a','b']          | ['b','a']          | ✓ one swap
 *   All same chars     | ['z','z','z']      | ['z','z','z']      | ✓ swaps, result unchanged
 *   Palindrome         | ['r','a','c','e',  | same string        | ✓ swaps happen correctly
 *                      |  'c','a','r']      |                    |
 *   Empty array        | []                 | []                 | ✓ s.length-1 = -1, no loop
 *   Special chars      | ['!','@','#']      | ['#','@','!']      | ✓ works on any ASCII char
 *   Max length (10^5)  | 100,000 chars      | reversed           | ✓ only 50,000 iterations
 *
 * -----------------------------------------------------------------------------
 * 8. SELF-CORRECTION & TESTING
 * -----------------------------------------------------------------------------
 *
 * Q: What edge cases might this miss?
 *   - null input: The problem guarantees s is non-null and non-empty per constraints,
 *     but defensive code could add: if (s == null || s.length == 0) return;
 *   - Unicode surrogate pairs (e.g., emoji): A char in Java is 16-bit. Some Unicode
 *     code points need two chars (a surrogate pair). This solution would swap them
 *     incorrectly — but LeetCode 344 only uses printable ASCII, so it's safe here.
 *     Real-world reversal needs codePointAt handling.
 *
 * Q: Are there any type mismatches?
 *   - char temp = s[left]  →  s[left] is a char, temp is char.  ✓
 *   - left and right are int — correct for indexing.             ✓
 *   - No integer overflow risk since we only increment/decrement within bounds. ✓
 *
 * Q: How can I verify this works right now?
 *
 *   public static void main(String[] args) {
 *       ReverseStringOptimal sol = new ReverseStringOptimal();
 *
 *       // Test 1: Odd length
 *       char[] t1 = {'h','e','l','l','o'};
 *       sol.reverseString(t1);
 *       System.out.println(Arrays.toString(t1)); // [o, l, l, e, h]
 *
 *       // Test 2: Even length
 *       char[] t2 = {'H','a','n','n','a','h'};
 *       sol.reverseString(t2);
 *       System.out.println(Arrays.toString(t2)); // [h, a, n, n, a, H]
 *
 *       // Test 3: Single char
 *       char[] t3 = {'x'};
 *       sol.reverseString(t3);
 *       System.out.println(Arrays.toString(t3)); // [x]
 *
 *       // Test 4: Empty
 *       char[] t4 = {};
 *       sol.reverseString(t4);
 *       System.out.println(Arrays.toString(t4)); // []
 *
 *       // Test 5: Two chars
 *       char[] t5 = {'a','b'};
 *       sol.reverseString(t5);
 *       System.out.println(Arrays.toString(t5)); // [b, a]
 *   }
 *
 * -----------------------------------------------------------------------------
 * 9. COMPANY INTERVIEW APPEARANCES
 * -----------------------------------------------------------------------------
 *
 *   Company                    | Frequency          | Notes
 *   ---------------------------+--------------------+-----------------------------------
 *   Amazon                     | ★★★★★ Very High    | Common warm-up / screening question
 *   Google                     | ★★★★  High         | Often asked as a follow-up/variant
 *   Microsoft                  | ★★★★  High         | Entry-level and intern rounds
 *   Facebook / Meta            | ★★★   Medium       | Paired with harder string problems
 *   Apple                      | ★★★   Medium       | Phone screen warm-up
 *   Bloomberg                  | ★★★   Medium       | Junior engineer rounds
 *   Adobe                      | ★★★   Medium       | Frequently reported in SDE-1 rounds
 *   Goldman Sachs              | ★★    Low-Medium   | Occasionally in coding assessments
 *   TCS / Infosys / Wipro      | ★★★★  High         | Standard in mass hiring tests
 *
 *   Total estimated appearances on LeetCode discuss alone: 400+
 *   Reported in 100+ company interview reports globally.
 *
 * -----------------------------------------------------------------------------
 * 10. FINAL SUMMARY
 * -----------------------------------------------------------------------------
 *
 *   Approach                 | Time  | Space | Recommended?
 *   -------------------------+-------+-------+------------------------------
 *   Brute Force (new array)  | O(n)  | O(n)  | ✗ Violates constraint
 *   Stack-Based              | O(n)  | O(n)  | ✗ Unnecessary overhead
 *   Two-Pointer In-Place ★   | O(n)  | O(1)  | ✓ ALWAYS USE THIS
 *   Recursive                | O(n)  | O(n)  | ⚠ Educational only
 *
 * What to Remember:
 *   PATTERN: The TWO-POINTER TECHNIQUE — converging from both ends — is the
 *   gold-standard pattern for in-place array/string manipulation. Mastering
 *   this here unlocks solutions to: Valid Palindrome, Container With Most Water,
 *   3Sum, and Trapping Rain Water.
 *
 *   KEY INSIGHT: You only need n/2 swaps to fully reverse an array — once the
 *   pointers meet in the middle, the job is done.
 *
 * =============================================================================
 */
// @formatter:on
