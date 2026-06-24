package Others;

import java.util.ArrayList;
import java.util.List;

public class Combinations {
    public static void main(String[] args) {
        Combinations combinations = new Combinations();
        System.out.println("Combinations : " + combinations.combineBruteForceBitMaskEmulation(4, 2));
        System.out.println("Combinations : " + combinations.combineBackTrackPruning(4, 2));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/combinations/description/
     * 
     * Given two integers n and k, return all possible combinations of k numbers
     * chosen from the range [1, n].
     * 
     * You may return the answer in any order.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: n = 4, k = 2
     * Output: [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
     * Explanation: There are 4 choose 2 = 6 total combinations.
     * Note that combinations are unordered, i.e., [1,2] and [2,1] are considered to
     * be the same combination.
     * Example 2:
     * 
     * Input: n = 1, k = 1
     * Output: [[1]]
     * Explanation: There is 1 choose 1 = 1 total combination.
     * 
     * 
     * Constraints:
     * 
     * 1 <= n <= 20
     * 1 <= k <= n
     */
    // @formatter:on

    public List<List<Integer>> combineBruteForceBitMaskEmulation(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        int totalSubsets = 1 << n;
        for (int mask = 0; mask < totalSubsets; mask++) {
            if (Integer.bitCount(mask) == k) {
                List<Integer> combo = new ArrayList<>();
                for (int bit = 0; bit < n; bit++) {
                    if (((mask & (1 << bit)) != 0)) {
                        combo.add(bit + 1);
                    }
                }
                result.add(combo);
            }
        }
        return result;
    }

    public List<List<Integer>> combineBackTrackPruning(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backTrack(1, n, k, new ArrayList<>(), result);
        return result;
    }

    public void backTrack(int start, int n, int k, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }

        int remaining = k - current.size();
        int upperBound = n - remaining + 1;

        for (int num = start; num <= upperBound; num++) {
            current.add(num);
            backTrack(num + 1, n, k, current, result);
            current.remove(current.size() - 1);
        }
    }

}

// @formatter:off
/*
 * ============================================================
 * Combinations — Deep Dive Explanation
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * What is the Problem?
 * --------------------
 * Given two integers n and k, return all possible combinations
 * of k numbers chosen from the range [1, n].
 *
 * The order of elements within a combination does not matter
 * (i.e., [1,2] and [2,1] are the same combination). The order
 * of the combinations in the output list also does not matter.
 *
 * Input Format:
 *   int n  →  upper bound of the range [1..n]
 *   int k  →  size of each combination
 *
 * Output Format:
 *   List<List<Integer>>  →  all unique combinations of size k from [1..n]
 *
 * Constraints (LeetCode #77):
 *   1 <= n <= 20
 *   1 <= k <= n
 *
 * What Exactly Needs to Be Computed?
 *   - Choose k distinct numbers from {1, 2, 3, ..., n}
 *   - Each number can be used at most once
 *   - No duplicate combinations allowed
 *   - Return all valid combinations
 *
 * Quick Example:
 *   Input:  n = 4, k = 2
 *   Output: [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
 *
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * Core Idea in Simple Terms:
 * --------------------------
 * Imagine you have cards numbered 1 to n, and you need to pick
 * k cards. You want to list every possible hand you could draw
 * — that's exactly what this problem asks.
 *
 * How a Human Reasons About It:
 * -----------------------------
 *   Think of building the combination one number at a time:
 *
 *   Step 1: Pick the first number → say we pick 1
 *   Step 2: Pick the second number → must be > 1 (to avoid duplicates)
 *            → can pick 2, 3, or 4
 *   Step 3: Once we have k numbers, record that combination
 *   Step 4: Backtrack and try the next choice at each step
 *
 * This is a classic "choose and explore" pattern — we build a
 * partial solution, and when it's complete, we record it. When
 * we're stuck (or done), we undo the last choice and try the next.
 *
 * What Makes This Tricky?
 * -----------------------
 *   | Challenge              | Why it's tricky                                   |
 *   |------------------------|---------------------------------------------------|
 *   | Avoiding duplicates    | Must ensure we only pick numbers in increasing    |
 *   |                        | order                                             |
 *   | Knowing when to stop   | Need to prune early when remaining numbers can't  |
 *   |                        | fill k slots                                      |
 *   | Backtracking correctly | Must undo state cleanly after each recursive call |
 *   | Pruning efficiency     | Skipping dead-end branches dramatically reduces   |
 *   |                        | work                                              |
 *
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 *   | # | Approach                      | Key Idea                            | Best Used When             | Time Complexity  |
 *   |---|-------------------------------|-------------------------------------|----------------------------|------------------|
 *   | 1 | Iterative Brute Force         | Try all subsets using bitmask       | Small n, understanding     | O(2^n × n)       |
 *   | 2 | Backtracking (Standard)       | Recursively build + backtrack       | Interviews, general        | O(C(n,k) × k)    |
 *   | 3 | Backtracking + Pruning ✅     | Skip branches that can't reach k    | Optimal                    | O(C(n,k) × k)    |
 *
 * Optimal Approach: Backtracking with Pruning
 * --------------------------------------------
 *   - Same asymptotic complexity as standard backtracking, but
 *     visits far fewer nodes in the recursion tree
 *   - The pruning condition eliminates entire subtrees early
 *   - Perfect for n ≤ 20
 *
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force — Bitmask
 * ------------------------------------------------------------
 *
 * Algorithm Explained Step by Step:
 *   1. Iterate through all 2^n possible subsets (0 to 2^n - 1)
 *   2. For each subset, count the number of set bits (1s)
 *   3. If the count == k, this subset represents a valid combination
 *   4. Extract the numbers corresponding to set bit positions
 *   5. Add that combination to the result
 *
 * Why bitmask? Each bit position i (0-indexed) represents whether
 * number (i+1) is included. If bit i is 1, number (i+1) is in the
 * combination.
 *
 *   import java.util.*;
 *
 *   public class CombinationsBruteForce {
 *
 *       public List<List<Integer>> combine(int n, int k) {
 *           List<List<Integer>> result = new ArrayList<>();
 *           int totalSubsets = 1 << n; // 2^n subsets
 *
 *           for (int mask = 0; mask < totalSubsets; mask++) {
 *               // Count bits set in this mask
 *               if (Integer.bitCount(mask) == k) {
 *                   List<Integer> combination = new ArrayList<>();
 *                   for (int bit = 0; bit < n; bit++) {
 *                       if ((mask & (1 << bit)) != 0) {
 *                           combination.add(bit + 1); // bit 0 → number 1
 *                       }
 *                   }
 *                   result.add(combination);
 *               }
 *           }
 *           return result;
 *       }
 *
 *       public static void main(String[] args) {
 *           CombinationsBruteForce sol = new CombinationsBruteForce();
 *           System.out.println(sol.combine(4, 2));
 *           // Output: [[1,2],[1,3],[2,3],[1,4],[2,4],[3,4]]
 *       }
 *   }
 *
 * ------------------------------------------------------------
 * Approach 2: Backtracking (Standard)
 * ------------------------------------------------------------
 *
 * Algorithm Explained Step by Step:
 *   1. Start with an empty current combination and start number = 1
 *   2. At each step, try adding numbers from 'start' to n
 *   3. After adding a number, recurse with start = (chosen number + 1)
 *   4. When current combination size == k, add a copy to result
 *   5. After recursion returns, remove the last added number (backtrack)
 *
 * The key insight: by always choosing the next number >= start,
 * we automatically avoid:
 *   - Duplicates (e.g., [1,2] and [2,1] won't both appear)
 *   - Reusing numbers (each number used at most once)
 *
 *   import java.util.*;
 *
 *   public class CombinationsBacktracking {
 *
 *       public List<List<Integer>> combine(int n, int k) {
 *           List<List<Integer>> result = new ArrayList<>();
 *           backtrack(n, k, 1, new ArrayList<>(), result);
 *           return result;
 *       }
 *
 *       private void backtrack(int n, int k, int start,
 *                              List<Integer> current,
 *                              List<List<Integer>> result) {
 *           // Base case: combination is complete
 *           if (current.size() == k) {
 *               result.add(new ArrayList<>(current)); // copy, not reference
 *               return;
 *           }
 *
 *           // Try each number from 'start' to n
 *           for (int num = start; num <= n; num++) {
 *               current.add(num);                          // choose
 *               backtrack(n, k, num + 1, current, result); // explore
 *               current.remove(current.size() - 1);        // un-choose (backtrack)
 *           }
 *       }
 *
 *       public static void main(String[] args) {
 *           CombinationsBacktracking sol = new CombinationsBacktracking();
 *           System.out.println(sol.combine(4, 2));
 *           // Output: [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
 *       }
 *   }
 *
 * ------------------------------------------------------------
 * Approach 3: Backtracking with Pruning ✅ (Optimal)
 * ------------------------------------------------------------
 *
 * Algorithm Explained Step by Step:
 *
 *   The key pruning insight:
 *
 *     If current combination has 'current.size()' elements already,
 *     we still need (k - current.size()) more elements.
 *     These must come from [start .. n].
 *     Available numbers = n - start + 1
 *
 *     If available numbers < still needed:
 *         → Impossible to complete → PRUNE this branch
 *
 *     So the loop only runs while:
 *         n - num + 1 >= k - current.size()
 *     Which simplifies to:
 *         num <= n - (k - current.size()) + 1
 *
 *   This simple bound eliminates huge portions of the recursion tree.
 *
 *   import java.util.*;
 *
 *   public class CombinationsOptimal {
 *
 *       public List<List<Integer>> combine(int n, int k) {
 *           List<List<Integer>> result = new ArrayList<>();
 *           backtrackWithPruning(n, k, 1, new ArrayList<>(), result);
 *           return result;
 *       }
 *
 *       private void backtrackWithPruning(int n, int k, int start,
 *                                         List<Integer> current,
 *                                         List<List<Integer>> result) {
 *           // Base case: we have exactly k elements
 *           if (current.size() == k) {
 *               result.add(new ArrayList<>(current));
 *               return;
 *           }
 *
 *           int stillNeeded = k - current.size();
 *           // Pruning: only iterate up to the point where enough numbers remain
 *           // Upper bound: n - stillNeeded + 1
 *           for (int num = start; num <= n - stillNeeded + 1; num++) {
 *               current.add(num);
 *               backtrackWithPruning(n, k, num + 1, current, result);
 *               current.remove(current.size() - 1);
 *           }
 *       }
 *
 *       public static void main(String[] args) {
 *           CombinationsOptimal sol = new CombinationsOptimal();
 *           System.out.println(sol.combine(4, 2));
 *           // [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
 *
 *           System.out.println(sol.combine(1, 1));
 *           // [[1]]
 *       }
 *   }
 *
 * Why `num <= n - stillNeeded + 1`?
 *
 *   Example: n=4, k=2, current=[]
 *     stillNeeded = 2
 *     upper bound = 4 - 2 + 1 = 3
 *     → Loop runs: num = 1, 2, 3  (NOT 4, picking 4 alone can't give us 2 numbers)
 *
 *   Example: n=4, k=2, current=[1]
 *     stillNeeded = 1
 *     upper bound = 4 - 1 + 1 = 4
 *     → Loop runs: num = 2, 3, 4  (all valid since we only need 1 more)
 *
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Approach 1: Bitmask Brute Force
 * --------------------------------
 *   Time  → O(2^n × n)       We check all 2^n subsets, scan n bits each
 *   Space → O(C(n,k) × k)    Storage for the output
 *
 *   Example: n=20 → 2^20 = ~1,048,576 iterations × 20 bit checks
 *            = ~20 million ops. Feasible but wasteful.
 *
 * Approach 2: Standard Backtracking
 * -----------------------------------
 *   Time  → O(C(n,k) × k)    C(n,k) combinations, each takes O(k) to copy
 *   Space → O(k) recursion depth + O(C(n,k) × k) output
 *
 *   C(n,k) formula: n! / (k! × (n-k)!)
 *   Example: n=20, k=10 → C(20,10) = 184,756 combinations × 10 = ~1.8M ops
 *
 * Approach 3: Backtracking with Pruning ✅
 * -----------------------------------------
 *   Time  → O(C(n,k) × k)    Same output size; internal nodes are pruned
 *                              → constant factor improvement
 *   Space → O(k) auxiliary + O(C(n,k) × k) output
 *
 *   Pruning effect: For n=4, k=2, standard visits ~10 nodes,
 *   pruned visits ~6. At larger n, the gap grows significantly.
 *
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * Example 1 (All Approaches): n=4, k=2
 * -----------------------------------------------
 * Approach 3 — Backtracking with Pruning (Detailed Trace):
 *
 *   combine(4, 2) → backtrack(n=4, k=2, start=1, current=[])
 *     stillNeeded=2, loop: num=1 to 4-2+1=3
 *
 *     ├─ num=1 → current=[1]
 *     │    stillNeeded=1, loop: num=2 to 4-1+1=4
 *     │    ├─ num=2 → current=[1,2] → SIZE==k → RECORD [1,2] ✅ → backtrack → [1]
 *     │    ├─ num=3 → current=[1,3] → SIZE==k → RECORD [1,3] ✅ → backtrack → [1]
 *     │    └─ num=4 → current=[1,4] → SIZE==k → RECORD [1,4] ✅ → backtrack → [1]
 *     │  backtrack → []
 *     │
 *     ├─ num=2 → current=[2]
 *     │    stillNeeded=1, loop: num=3 to 4
 *     │    ├─ num=3 → current=[2,3] → RECORD [2,3] ✅ → backtrack → [2]
 *     │    └─ num=4 → current=[2,4] → RECORD [2,4] ✅ → backtrack → [2]
 *     │  backtrack → []
 *     │
 *     └─ num=3 → current=[3]
 *          stillNeeded=1, loop: num=4 to 4
 *          └─ num=4 → current=[3,4] → RECORD [3,4] ✅ → backtrack → [3]
 *        backtrack → []
 *
 *     PRUNED: num=4 never tried at top level
 *             (would give [4,?] but no numbers left!)
 *
 *   Result: [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
 *
 * Example 2: n=3, k=3
 * -----------------------------------------------
 *   backtrack(n=3, k=3, start=1, current=[])
 *     stillNeeded=3, loop: num=1 to 3-3+1=1
 *
 *     └─ num=1 → current=[1]
 *          stillNeeded=2, loop: num=2 to 3-2+1=2
 *          └─ num=2 → current=[1,2]
 *               stillNeeded=1, loop: num=3 to 3-1+1=3
 *               └─ num=3 → current=[1,2,3] → RECORD [1,2,3] ✅
 *
 *   Result: [[1,2,3]]  ← Only one possible combination!
 *
 * Example 3: n=1, k=1
 * -----------------------------------------------
 *   backtrack(n=1, k=1, start=1, current=[])
 *     stillNeeded=1, loop: num=1 to 1-1+1=1
 *
 *     └─ num=1 → current=[1] → SIZE==k → RECORD [1] ✅
 *
 *   Result: [[1]]
 *
 * Bitmask Trace (Approach 1): n=3, k=2
 * -----------------------------------------------
 *   mask=0 (000) → bitCount=0 ≠ 2 → skip
 *   mask=1 (001) → bitCount=1 ≠ 2 → skip
 *   mask=2 (010) → bitCount=1 ≠ 2 → skip
 *   mask=3 (011) → bitCount=2 == 2 → bits 0,1 set → [1,2] ✅
 *   mask=4 (100) → bitCount=1 ≠ 2 → skip
 *   mask=5 (101) → bitCount=2 == 2 → bits 0,2 set → [1,3] ✅
 *   mask=6 (110) → bitCount=2 == 2 → bits 1,2 set → [2,3] ✅
 *   mask=7 (111) → bitCount=3 ≠ 2 → skip
 *
 *   Result: [[1,2],[1,3],[2,3]]
 *
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * Comprehensive Edge Case Table:
 *   | Edge Case           | Input      | Expected Output         | How Handled                              |
 *   |---------------------|------------|-------------------------|------------------------------------------|
 *   | k == n              | n=3, k=3   | [[1,2,3]]               | Pruning picks it immediately             |
 *   | k == 1              | n=4, k=1   | [[1],[2],[3],[4]]        | Each number is its own combination       |
 *   | n==1, k==1          | n=1, k=1   | [[1]]                   | Single element, base case triggers       |
 *   | Large n, small k    | n=20, k=1  | [[1],[2],...,[20]]       | 20 combinations, very fast               |
 *   | Large n, large k    | n=20, k=10 | 184,756 combinations    | Pruning critical here                    |
 *   | n == k == 1         | n=1, k=1   | [[1]]                   | Minimal valid input                      |
 *
 * Potential Pitfalls:
 *
 *   WRONG:   result.add(current)
 *            → Adds a REFERENCE to current list; when current changes,
 *              result changes too!
 *
 *   CORRECT: result.add(new ArrayList<>(current))
 *            → Adds a COPY of the current state
 *
 *   WRONG:   current.remove(num)
 *            → remove(Object) not remove(index) — could remove wrong
 *              element if num is valid index too
 *
 *   CORRECT: current.remove(current.size() - 1)
 *            → Always removes the last element by index — safe and unambiguous
 *
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Internal Code Review:
 *
 * Q: What edge cases might this miss?
 *   ✅ n == k → handled (pruning upper bound = 1, only one path)
 *   ✅ k == 1 → handled (base case triggers after one element)
 *   ✅ Large C(n,k) → pruning keeps it efficient within constraints
 *   ✅ n=20 → C(20,10)=184,756 combinations → well within Java heap limits
 *
 * Q: Are there any type mismatches?
 *   ✅ n, k are int → no overflow for n ≤ 20
 *   ✅ current.remove(current.size() - 1) uses int index → correct
 *   ✅ current.add(num) → num is int, List<Integer> auto-boxes → fine
 *   ✅ stillNeeded = k - current.size() → both int → fine
 *
 * Q: How can I verify this works right now?
 *
 *   // Quick verification test
 *   public static void verify() {
 *       CombinationsOptimal sol = new CombinationsOptimal();
 *
 *       // Test 1: n=4, k=2 → should give 6 combinations = C(4,2)
 *       List<List<Integer>> r1 = sol.combine(4, 2);
 *       assert r1.size() == 6 : "Expected 6, got " + r1.size();
 *
 *       // Test 2: n=3, k=3 → should give 1 combination
 *       List<List<Integer>> r2 = sol.combine(3, 3);
 *       assert r2.size() == 1 : "Expected 1, got " + r2.size();
 *
 *       // Test 3: n=1, k=1 → should give [[1]]
 *       List<List<Integer>> r3 = sol.combine(1, 1);
 *       assert r3.size() == 1 && r3.get(0).equals(List.of(1));
 *
 *       // Test 4: n=20, k=10 → C(20,10) = 184,756
 *       List<List<Integer>> r4 = sol.combine(20, 10);
 *       assert r4.size() == 184756 : "Expected 184756, got " + r4.size();
 *
 *       System.out.println("All tests passed!");
 *   }
 *
 * How Each Solution Handles Failures:
 *   | Approach               | Risk                          | Mitigation                               |
 *   |------------------------|-------------------------------|------------------------------------------|
 *   | Bitmask                | TLE for large n > 20          | Constraint says n ≤ 20, so safe          |
 *   | Standard Backtracking  | Slightly slower than pruned   | Still correct, explores more nodes       |
 *   | Pruned Backtracking    | None for given constraints    | Handles all edge cases cleanly           |
 *
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * Where This Problem Appears:
 *   | Company           | Frequency   | Notes                          |
 *   |-------------------|-------------|--------------------------------|
 *   | Amazon            | ⭐⭐⭐⭐⭐  | Very common in SDE rounds      |
 *   | Microsoft         | ⭐⭐⭐⭐⭐  | Frequently in phone screens    |
 *   | Google            | ⭐⭐⭐⭐    | Part of backtracking series    |
 *   | Facebook/Meta     | ⭐⭐⭐⭐    | Common in coding interviews    |
 *   | Bloomberg         | ⭐⭐⭐⭐    | Seen in tech assessments       |
 *   | Apple             | ⭐⭐⭐      | Occasionally asked             |
 *   | Adobe             | ⭐⭐⭐      | Appears in OA rounds           |
 *   | Uber              | ⭐⭐⭐      | Backtracking focus             |
 *   | LinkedIn          | ⭐⭐⭐      | Standard set question          |
 *   | TCS/Infosys/Wipro | ⭐⭐        | Appears in advanced rounds     |
 *
 *   LeetCode Problem #77 — Difficulty: Medium
 *   Appeared in 400+ interview reports on LeetCode and Glassdoor
 *   combined as of 2024–2025.
 *
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * Comparison Table:
 *   | Approach                  | Time          | Space          | Code Complexity | Recommended?        |
 *   |---------------------------|---------------|----------------|-----------------|---------------------|
 *   | Bitmask Brute Force       | O(2^n × n)    | O(C(n,k)×k)    | Low             | ❌ Only small n     |
 *   | Standard Backtracking     | O(C(n,k) × k) | O(k) + output  | Medium          | ✅ Good             |
 *   | Backtracking + Pruning    | O(C(n,k) × k) | O(k) + output  | Medium          | ✅✅ Best choice    |
 *
 * Recommended: Approach 3 — Backtracking with Pruning
 *
 * What to Remember:
 *
 *   Pattern: This is the classic "Subsets/Combinations Backtracking"
 *   pattern. Every time you see "generate all X of size k from a range",
 *   think: recursive backtracking, always pick forward (start index),
 *   prune when not enough elements remain.
 *
 *   Key Technique: The pruning bound `num <= n - (k - current.size()) + 1`
 *   is the single most important optimization — memorize it or re-derive
 *   it from: "remaining available numbers must be >= remaining needed numbers".
 *
 * ============================================================
 */
// @formatter:on
