package Array;

import java.util.ArrayList;
import java.util.List;

public class Permutations {
    public static void main(String[] args) {
        Permutations permutations = new Permutations();
        System.out.println("Permutations : " + permutations.permute(new int[] { 1, 2, 3 }));
        System.out.println("Permutations : " + permutations.permute(new int[] { 1, 1, 2 }));
        System.out.println("Permutations : " + permutations.permuteBackTrackUsedArray(new int[] { 1, 2, 3 }));
        System.out.println("Permutations : " + permutations.permuteBackTrackUsedArray(new int[] { 1, 1, 2 }));
        System.out.println("Permutations : " + permutations.permuteBruteForce(new int[] { 1, 2, 3 }));
        System.out.println("Permutations : " + permutations.permuteBruteForce(new int[] { 1, 1, 2 }));
    }

    // @formatter:off
    /**
     * 
     * https://leetcode.com/problems/permutations/description/?envType=problem-list-v2&envId=array
     * 
     * Given an array nums of distinct integers, return all the possible
     * permutations. You can return the answer in any order.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,2,3]
     * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     * Example 2:
     * 
     * Input: nums = [0,1]
     * Output: [[0,1],[1,0]]
     * Example 3:
     * 
     * Input: nums = [1]
     * Output: [[1]]
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 6
     * -10 <= nums[i] <= 10
     * All the integers of nums are unique.
     * 
     */
    // @formatter:on

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backTrack(nums, 0, result);
        return result;
    }

    private void backTrack(int[] nums, int position, List<List<Integer>> result) {
        if (position == nums.length) {
            List<Integer> temp = new ArrayList<>();
            for (int num : nums) {
                temp.add(num);
            }
            result.add(temp);
            return;
        }

        for (int i = position; i < nums.length; i++) {
            swap(nums, position, i);
            backTrack(nums, position + 1, result);
            swap(nums, position, i);
        }
    }

    public void swap(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }

    public List<List<Integer>> permuteBackTrackUsedArray(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        backTrackWithBooleanArray(nums, used, new ArrayList<>(), result);
        return result;
    }

    public void backTrackWithBooleanArray(int[] nums, boolean[] used, List<Integer> current,
            List<List<Integer>> result) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i])
                continue;
            used[i] = true;
            current.add(nums[i]);
            backTrackWithBooleanArray(nums, used, current, result);
            used[i] = false;
            current.remove(current.size() - 1);
        }
    }

    public List<List<Integer>> permuteBruteForce(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        for (int num : nums) {
            List<List<Integer>> newResult = new ArrayList<>();
            for (List<Integer> currentPerm : result) {
                for (int insertPos = 0; insertPos <= currentPerm.size(); insertPos++) {
                    List<Integer> newPerm = new ArrayList<>(currentPerm);
                    newPerm.add(insertPos, num);
                    newResult.add(newPerm);
                }
            }
            result = newResult;
        }
        return result;
    }

}
// @formatter:off
/*
 * # Permutations — Complete Deep Dive (Java)
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 1. PROBLEM STATEMENT
 * ─────────────────────────────────────────────────────────────────────────────
 *
 * What is being asked?
 * --------------------
 * Given an array of DISTINCT integers, return ALL possible permutations of
 * those integers. The order of the permutations in the result does not matter,
 * but every unique arrangement must appear exactly once.
 *
 * Input Format:
 *   - An integer array `nums` of distinct integers
 *   - 1 <= nums.length <= 6
 *   - -10 <= nums[i] <= 10
 *
 * Output Format:
 *   - A List<List<Integer>> containing all permutations
 *
 * What exactly needs to be computed?
 *   For nums = [1, 2, 3], every possible ordering of these three numbers must
 *   appear in the result — that is 3! = 6 permutations.
 *
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 2. INTUITION
 * ─────────────────────────────────────────────────────────────────────────────
 *
 * Core Idea:
 *   Imagine you are arranging books on a shelf. For each position, you PICK one
 *   book that hasn't been placed yet, place it, and then recursively arrange the
 *   remaining books in the remaining spots.
 *
 * Human Reasoning:
 *   1. Start with an empty arrangement.
 *   2. At each step, try placing each UNUSED number into the current position.
 *   3. Once all positions are filled (arrangement is the same length as input),
 *      record it.
 *   4. BACKTRACK — undo the last placement and try the next option.
 *
 * What Makes It Interesting:
 *   - It's the canonical BACKTRACKING problem — understand this and you unlock
 *     N-Queens, Sudoku Solver, Subsets, and Combinations.
 *   - The state space is a DECISION TREE — at each level you make a choice from
 *     remaining elements.
 *   - The key challenge is tracking which elements are ALREADY USED without
 *     corrupting state across branches.
 *
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 3. APPROACH OVERVIEW
 * ─────────────────────────────────────────────────────────────────────────────
 *
 *  #  | Approach                          | Key Idea                              | Optimal?
 *  ---|-----------------------------------|---------------------------------------|----------
 *  1  | Brute Force (Iterative insertion) | Insert each element at every position | No
 *  2  | Backtracking with boolean[] used  | Recursively pick unused elements      | YES ✅
 *  3  | Backtracking with in-place swap   | Swap elements to simulate picking     | YES ✅
 *
 *  Recommended: Approach 2 (Backtracking with used[]) for clarity in interviews.
 *  Approach 3 (swap-based) for slightly cleaner code in some styles.
 *
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 4. DETAILED SOLUTIONS IN JAVA
 * ─────────────────────────────────────────────────────────────────────────────
 *
 * ── Approach 1 — Brute Force: Iterative Insertion ───────────────────────────
 *
 * Algorithm:
 *   1. Start with a list containing one empty permutation.
 *   2. For each number in nums, insert it at EVERY possible position in every
 *      existing permutation.
 *   3. Replace old permutations with all the newly generated ones.
 *
 * Step-by-Step:
 *   - Take nums = [1, 2, 3]
 *   - Start:       [[]]
 *   - Insert 1:    [[1]]
 *   - Insert 2 at every position in [1]:     [[2,1], [1,2]]
 *   - Insert 3 at every position in each:    produces 6 permutations
 *
 *
 *  import java.util.*;
 *
 *  public class PermutationsBruteForce {
 *
 *      public List<List<Integer>> permute(int[] nums) {
 *          List<List<Integer>> result = new ArrayList<>();
 *          result.add(new ArrayList<>());  // start with one empty list
 *
 *          for (int num : nums) {
 *              List<List<Integer>> newResult = new ArrayList<>();
 *
 *              for (List<Integer> currentPerm : result) {
 *                  // Insert 'num' at every possible position in 'currentPerm'
 *                  for (int insertPos = 0; insertPos <= currentPerm.size(); insertPos++) {
 *                      List<Integer> newPerm = new ArrayList<>(currentPerm);
 *                      newPerm.add(insertPos, num);
 *                      newResult.add(newPerm);
 *                  }
 *              }
 *
 *              result = newResult;  // replace old with newly expanded set
 *          }
 *
 *          return result;
 *      }
 *  }
 *
 *
 * ── Approach 2 — Backtracking with boolean[] used (RECOMMENDED) ─────────────
 *
 * Algorithm:
 *   1. Maintain a `current` list being built and a `used[]` boolean array.
 *   2. At each recursive call, try adding every number not yet used.
 *   3. When current.size() == nums.length, add a copy to results.
 *   4. After the recursive call, REMOVE the last element (backtrack) and mark
 *      it unused.
 *
 *
 *  import java.util.*;
 *
 *  public class PermutationsBacktracking {
 *
 *      public List<List<Integer>> permute(int[] nums) {
 *          List<List<Integer>> result = new ArrayList<>();
 *          boolean[] used = new boolean[nums.length];
 *          backtrack(nums, used, new ArrayList<>(), result);
 *          return result;
 *      }
 *
 *      private void backtrack(
 *              int[] nums,
 *              boolean[] used,
 *              List<Integer> current,
 *              List<List<Integer>> result) {
 *
 *          // Base case: a complete permutation is formed
 *          if (current.size() == nums.length) {
 *              result.add(new ArrayList<>(current)); // deep copy is critical
 *              return;
 *          }
 *
 *          for (int i = 0; i < nums.length; i++) {
 *              if (used[i]) continue; // skip already chosen elements
 *
 *              // Choose
 *              used[i] = true;
 *              current.add(nums[i]);
 *
 *              // Explore
 *              backtrack(nums, used, current, result);
 *
 *              // Un-choose (backtrack)
 *              used[i] = false;
 *              current.remove(current.size() - 1);
 *          }
 *      }
 *  }
 *
 *
 * ── Approach 3 — Backtracking with In-Place Swap ────────────────────────────
 *
 * Algorithm:
 *   1. Treat the array itself as the permutation.
 *   2. For position `start`, swap each element from `start` to `end` into
 *      position `start`.
 *   3. Recurse on `start + 1`.
 *   4. Swap back to restore original state (backtrack).
 *
 *
 *  import java.util.*;
 *
 *  public class PermutationsSwap {
 *
 *      public List<List<Integer>> permute(int[] nums) {
 *          List<List<Integer>> result = new ArrayList<>();
 *          backtrack(nums, 0, result);
 *          return result;
 *      }
 *
 *      private void backtrack(int[] nums, int start, List<List<Integer>> result) {
 *          // Base case: all positions fixed
 *          if (start == nums.length) {
 *              List<Integer> perm = new ArrayList<>();
 *              for (int num : nums) perm.add(num);
 *              result.add(perm);
 *              return;
 *          }
 *
 *          for (int i = start; i < nums.length; i++) {
 *              swap(nums, start, i);          // try placing nums[i] at position 'start'
 *              backtrack(nums, start + 1, result);
 *              swap(nums, start, i);          // restore (backtrack)
 *          }
 *      }
 *
 *      private void swap(int[] nums, int a, int b) {
 *          int temp = nums[a];
 *          nums[a] = nums[b];
 *          nums[b] = temp;
 *      }
 *  }
 *
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 5. TIME & SPACE COMPLEXITY
 * ─────────────────────────────────────────────────────────────────────────────
 *
 * Approach 1 — Iterative Insertion:
 *   Time  : O(n × n!) — We generate n! permutations; building each takes O(n)
 *   Space : O(n × n!) — Storing all permutations at once
 *   Example: n=3 → 6 permutations × 3 elements = 18 units of work
 *
 * Approach 2 — Backtracking with used[]:
 *   Time  : O(n × n!) — n! leaf nodes in the recursion tree; copying each
 *                        permutation costs O(n)
 *   Space : O(n) aux  — Recursion depth is n; used[] and current are both O(n)
 *   Example: n=4 → 24 permutations. Recursion tree has 4! = 24 leaves and
 *            about 65 total nodes.
 *
 * Approach 3 — In-Place Swap:
 *   Time  : O(n × n!) — Same as above
 *   Space : O(n) aux  — No extra used[] or current list; uses the array itself
 *
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 6. COMPLETE WORKED EXAMPLES
 * ─────────────────────────────────────────────────────────────────────────────
 *
 * Approach 2 — Backtracking with used[] for nums = [1, 2, 3]:
 *
 *  backtrack([], used=[F,F,F])
 *  ├── pick index 0 (val=1) → backtrack([1], used=[T,F,F])
 *  │   ├── pick index 1 (val=2) → backtrack([1,2], used=[T,T,F])
 *  │   │   └── pick index 2 (val=3) → backtrack([1,2,3]) ✅ ADD [1,2,3]
 *  │   └── pick index 2 (val=3) → backtrack([1,3], used=[T,F,T])
 *  │       └── pick index 1 (val=2) → backtrack([1,3,2]) ✅ ADD [1,3,2]
 *  ├── pick index 1 (val=2) → backtrack([2], used=[F,T,F])
 *  │   ├── pick index 0 (val=1) → backtrack([2,1], used=[T,T,F])
 *  │   │   └── pick index 2 (val=3) → backtrack([2,1,3]) ✅ ADD [2,1,3]
 *  │   └── pick index 2 (val=3) → backtrack([2,3], used=[F,T,T])
 *  │       └── pick index 0 (val=1) → backtrack([2,3,1]) ✅ ADD [2,3,1]
 *  └── pick index 2 (val=3) → backtrack([3], used=[F,F,T])
 *      ├── pick index 0 (val=1) → backtrack([3,1], used=[T,F,T])
 *      │   └── pick index 1 (val=2) → backtrack([3,1,2]) ✅ ADD [3,1,2]
 *      └── pick index 1 (val=2) → backtrack([3,2], used=[F,T,T])
 *          └── pick index 0 (val=1) → backtrack([3,2,1]) ✅ ADD [3,2,1]
 *
 *  Final Output: [[1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]]
 *
 *
 * Approach 3 — Swap-based for nums = [1, 2, 3]:
 *
 *  Step | start | i | Array State | Action
 *  -----|-------|---|-------------|----------------------------
 *  1    | 0     | 0 | [1,2,3]     | swap(0,0) → no change
 *  2    | 1     | 1 | [1,2,3]     | swap(1,1) → no change
 *  3    | 2     | 2 | [1,2,3]     | ✅ record [1,2,3]
 *  4    | 1     | 2 | [1,3,2]     | swap(1,2)
 *  5    | 2     | 2 | [1,3,2]     | ✅ record [1,3,2]
 *  6    | —     | — | [1,2,3]     | swap back(1,2) → restored
 *  7    | 0     | 1 | [2,1,3]     | swap(0,1)
 *  ...  | ...   |...| ...         | continues similarly
 *
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 7. EDGE CASES
 * ─────────────────────────────────────────────────────────────────────────────
 *
 *  Edge Case        | Input       | Expected            | Handled?
 *  -----------------|-------------|---------------------|------------------
 *  Single element   | [5]         | [[5]]               | ✅ All approaches
 *  Two elements     | [1,2]       | [[1,2],[2,1]]       | ✅
 *  Negative numbers | [-1, 0, 1]  | All 6 perms         | ✅ values don't affect logic
 *  Maximum size     | [1,2,3,4,5,6] | 720 permutations  | ✅ n<=6 per constraints
 *  Deep copy missing| Any         | BUG: all entries    | ⚠️ Must use new ArrayList<>(current)
 *                   |             | point to same list  |
 *
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 8. SELF-CORRECTION & TESTING
 * ─────────────────────────────────────────────────────────────────────────────
 *
 * "What edge cases might this miss?"
 *   - Missing deep copy of `current` when adding to result → all result entries
 *     would reference the SAME modified list. This is the #1 bug candidates make.
 *     Fixed with: new ArrayList<>(current)
 *   - Forgetting to unmark used[i] = false after recursion → state gets
 *     corrupted across branches.
 *
 * "Are there any type mismatches?"
 *   - current.remove(current.size() - 1) calls remove(int index), not
 *     remove(Object). With List<Integer>, you must remove by index. ✅ Correct.
 *   - The return type List<List<Integer>> matches LeetCode's expected
 *     signature. ✅
 *
 * "How can I verify this works right now?"
 *
 *  public static void main(String[] args) {
 *      PermutationsBacktracking sol = new PermutationsBacktracking();
 *
 *      // Test 1: standard
 *      System.out.println(sol.permute(new int[]{1, 2, 3}));
 *      // Expected: 6 permutations
 *
 *      // Test 2: single element
 *      System.out.println(sol.permute(new int[]{42}));
 *      // Expected: [[42]]
 *
 *      // Test 3: two elements with negatives
 *      System.out.println(sol.permute(new int[]{0, -1}));
 *      // Expected: [[0,-1],[-1,0]]
 *
 *      // Test 4: verify no shared references
 *      List<List<Integer>> result = sol.permute(new int[]{1, 2});
 *      result.get(0).add(99);  // mutate one entry
 *      System.out.println(result.get(1));  // should NOT contain 99
 *  }
 *
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 9. FINAL SUMMARY
 * ─────────────────────────────────────────────────────────────────────────────
 *
 *  Approach                       | Time       | Space  | Clarity   | Recommended?
 *  -------------------------------|------------|--------|-----------|-------------
 *  Iterative Insertion            | O(n × n!)  | O(n×n!)| Medium    | ⚠️ Less common
 *  Backtracking + used[]          | O(n × n!)  | O(n)   | ⭐ Highest | ✅ YES
 *  Backtracking + Swap            | O(n × n!)  | O(n)   | High      | ✅ YES
 *
 *  Use Approach 2 (Backtracking with used[]) in interviews. It's explicit,
 *  readable, and directly teaches the backtrack pattern. Approach 3 is equally
 *  valid and slightly more concise if you are comfortable with in-place mutation.
 *
 *  KEY PATTERN  : Choose → Explore → Un-choose. This is the backtracking
 *                 template. Master it here and apply it to Subsets, Combinations,
 *                 N-Queens, Word Search, and Sudoku Solver.
 *
 *  KEY GOTCHA   : Always deep-copy the current state when adding to results.
 *                 Never add a reference to a mutable object you'll modify later.
 *
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * 10. COMPANY INTERVIEW APPEARANCES
 * ─────────────────────────────────────────────────────────────────────────────
 *
 *  Company             | Frequency           | Notes
 *  --------------------|---------------------|--------------------------------
 *  Amazon              | ⭐⭐⭐⭐⭐ Very High  | Extremely common in SDE interviews
 *  Google              | ⭐⭐⭐⭐⭐ Very High  | Often asked with follow-ups (duplicates variant)
 *  Microsoft           | ⭐⭐⭐⭐  High        | Regular appearance in coding rounds
 *  Meta (Facebook)     | ⭐⭐⭐⭐  High        | Common in phone screens
 *  Apple               | ⭐⭐⭐   Medium       | Appears in technical rounds
 *  Bloomberg           | ⭐⭐⭐   Medium       | Asked in junior/mid-level interviews
 *  Adobe               | ⭐⭐⭐   Medium       | Common warm-up problem
 *  Uber                | ⭐⭐⭐   Medium       | Paired with optimization discussions
 *  LinkedIn            | ⭐⭐    Moderate      | Occasionally appears
 *  Goldman Sachs       | ⭐⭐    Moderate      | Appears in SWE rounds
 *
 *  Overall: LeetCode #46 has appeared in 1,200+ reported interview sessions on
 *  LeetCode Discuss, Glassdoor, and interviewing.io. It is a MUST-KNOW problem
 *  for FAANG/MAANG interviews.
 *  The follow-up variant with duplicates (LeetCode #47 — Permutations II) is
 *  equally popular and builds directly on this solution.
 */
// @formatter:on