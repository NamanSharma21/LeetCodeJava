package Array;

import java.util.ArrayList;
import java.util.List;

public class Subsets {
    public static void main(String[] args) {
        Subsets subsets = new Subsets();
        System.out.println("Subsets : " + subsets.subsetsBackTracking(new int[] { 1, 2, 3 }));
        System.out.println("Subsets : " + subsets.subsetsBitManipulation(new int[] { 1, 2, 3 }));
        System.out.println("Subsets : " + subsets.subsetsBruteForce(new int[] { 1, 2, 3 }));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/subsets/?envType=problem-list-v2&envId=array
     * 
     * 
     * Given an integer array nums of unique elements, return all possible subsets
     * (the power set).
     * 
     * The solution set must not contain duplicate subsets. Return the solution in
     * any order.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,2,3]
     * Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     * Example 2:
     * 
     * Input: nums = [0]
     * Output: [[],[0]]
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 10
     * -10 <= nums[i] <= 10
     * All the numbers of nums are unique.
     */
    // @formatter:on

    public List<List<Integer>> subsetsBackTracking(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backTrack(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private void backTrack(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current));
        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]);
            backTrack(nums, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    public List<List<Integer>> subsetsBitManipulation(int[] nums) {
        int n = nums.length;
        int totalSubsets = 1 << n;
        List<List<Integer>> result = new ArrayList<>();
        for (int mask = 0; mask < totalSubsets; mask++) {
            List<Integer> subsets = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if ((mask & (1 << j)) != 0) {
                    subsets.add(nums[j]);
                }
            }
            result.add(subsets);
        }
        return result;
    }

    public List<List<Integer>> subsetsBruteForce(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        for (int num : nums) {
            int currentSize = result.size();
            for (int i = 0; i < currentSize; i++) {
                List<Integer> newSubset = new ArrayList<>(result.get(i));
                newSubset.add(num);
                result.add(newSubset);
            }
        }
        return result;
    }
}

// @formatter:off
/*
 * ============================================================
 *  Subsets — Deep Dive Explanation
 * ============================================================
 *
 * ============================================================
 *  1. PROBLEM STATEMENT
 * ============================================================
 *
 *  What the problem asks:
 *  Given an integer array `nums` of UNIQUE elements, return ALL
 *  possible subsets (the power set).
 *
 *  - The solution set must NOT contain duplicate subsets.
 *  - The order of subsets in the output does not matter.
 *  - The order of elements within each subset does not matter.
 *
 *  Input Format:
 *      int[] nums  ->  an array of unique integers
 *      Constraints: 1 <= nums.length <= 10, -10 <= nums[i] <= 10
 *
 *  Output Format:
 *      List<List<Integer>>  ->  all 2^n possible subsets
 *                               including [] and the full set
 *
 *  What exactly needs to be computed:
 *  Every possible combination of elements — from the empty set []
 *  to the full array — with no repetitions.
 *
 *
 * ============================================================
 *  2. INTUITION
 * ============================================================
 *
 *  The Core Idea — in plain English:
 *  Imagine standing in front of each element one by one.
 *  For EVERY element, you make a binary decision:
 *      "Do I INCLUDE this element in my current subset,
 *       or do I SKIP it?"
 *
 *  Since each of n elements has 2 choices, the total number
 *  of subsets is always 2^n.
 *
 *  How a human reasons about it:
 *
 *      nums = [1, 2, 3]
 *
 *      Start with []
 *        +-- Include 1 --> [1]
 *        |     +-- Include 2 --> [1,2]
 *        |     |     +-- Include 3 --> [1,2,3]  (*)
 *        |     |     +-- Skip 3    --> [1,2]     (*)
 *        |     +-- Skip 2   --> [1]
 *        |           +-- Include 3 --> [1,3]     (*)
 *        |           +-- Skip 3    --> [1]        (*)
 *        +-- Skip 1   --> []
 *              +-- Include 2 --> [2]
 *              |     +-- Include 3 --> [2,3]     (*)
 *              |     +-- Skip 3    --> [2]        (*)
 *              +-- Skip 2   --> []
 *                    +-- Include 3 --> [3]        (*)
 *                    +-- Skip 3    --> []         (*)
 *
 *  What makes this interesting:
 *  - It's a foundational combinatorics problem — most backtracking,
 *    permutation, and combination problems build on this.
 *  - The bit manipulation approach reveals a beautiful mathematical
 *    structure.
 *  - It connects recursion, backtracking, iteration, and binary
 *    representation — all in one problem.
 *
 *
 * ============================================================
 *  3. APPROACH OVERVIEW
 * ============================================================
 *
 *  #  | Approach                   | Key Idea
 *  ---+----------------------------+-------------------------------
 *  1  | Brute Force — Iterative    | Start with [[]], for each
 *     | (Cascading / BFS-style)    | element add it to all existing
 *     |                            | subsets.
 *  ---+----------------------------+-------------------------------
 *  2  | Backtracking (Recursion)   | DFS tree — at each index,
 *     | *** RECOMMENDED ***        | choose include or skip.
 *  ---+----------------------------+-------------------------------
 *  3  | Bit Manipulation           | Each number 0..2^n-1 maps to
 *     |                            | a unique subset via binary bits.
 *  ---+----------------------------+-------------------------------
 *
 *  All three are O(n * 2^n) in time and produce the same output.
 *  The Backtracking approach is the most recommended in interviews
 *  because it generalizes to harder problems
 *  (Subsets II, Combinations, Permutations).
 *
 *
 * ============================================================
 *  4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 *  APPROACH 1 — Iterative (Cascading / BFS-style)
 * ------------------------------------------------------------
 *
 *  Algorithm:
 *  1. Start with a result list containing just the empty subset: [[]]
 *  2. For each number in nums, take every existing subset, make a
 *     copy of it, add the current number, and append it back to
 *     the result.
 *  3. After processing all numbers, result contains all 2^n subsets.
 *
 *  import java.util.*;
 *
 *  public class SubsetsIterative {
 *
 *      public List<List<Integer>> subsets(int[] nums) {
 *          List<List<Integer>> result = new ArrayList<>();
 *          result.add(new ArrayList<>()); // Start with empty subset
 *
 *          for (int num : nums) {
 *              int currentSize = result.size(); // Snapshot before adding
 *              for (int i = 0; i < currentSize; i++) {
 *                  // Clone each existing subset and add current number
 *                  List<Integer> newSubset = new ArrayList<>(result.get(i));
 *                  newSubset.add(num);
 *                  result.add(newSubset);
 *              }
 *          }
 *
 *          return result;
 *      }
 *  }
 *
 *
 * ------------------------------------------------------------
 *  APPROACH 2 — Backtracking (Recommended for Interviews)
 * ------------------------------------------------------------
 *
 *  Algorithm:
 *  1. Use a recursive helper with a `start` index and a `current` list.
 *  2. At each call, immediately add the current subset to results
 *     (every node in the tree is valid).
 *  3. Loop from `start` to end of array: add nums[i], recurse with
 *     i+1, then remove nums[i] (backtrack).
 *  4. Base case: naturally when start == nums.length, the loop
 *     doesn't execute and we return.
 *
 *  import java.util.*;
 *
 *  public class SubsetsBacktracking {
 *
 *      public List<List<Integer>> subsets(int[] nums) {
 *          List<List<Integer>> result = new ArrayList<>();
 *          backtrack(nums, 0, new ArrayList<>(), result);
 *          return result;
 *      }
 *
 *      private void backtrack(int[] nums, int start,
 *                             List<Integer> current,
 *                             List<List<Integer>> result) {
 *          // Every state of 'current' is a valid subset — add a snapshot
 *          result.add(new ArrayList<>(current));
 *
 *          for (int i = start; i < nums.length; i++) {
 *              current.add(nums[i]);                    // Choose: include nums[i]
 *              backtrack(nums, i + 1, current, result); // Explore further
 *              current.remove(current.size() - 1);      // Un-choose: backtrack
 *          }
 *      }
 *  }
 *
 *
 * ------------------------------------------------------------
 *  APPROACH 3 — Bit Manipulation
 * ------------------------------------------------------------
 *
 *  Algorithm:
 *  1. For n elements, there are 2^n total subsets.
 *  2. Each number from 0 to 2^n - 1 represents a unique subset
 *     via its binary bits.
 *  3. If bit j of number i is 1, include nums[j] in that subset.
 *
 *  Example: nums = [1, 2, 3], n = 3
 *
 *      i=0 -> 000 -> []          (no bits set)
 *      i=1 -> 001 -> [1]         (bit 0 set -> nums[0]=1)
 *      i=2 -> 010 -> [2]         (bit 1 set -> nums[1]=2)
 *      i=3 -> 011 -> [1,2]       (bits 0,1 set)
 *      i=4 -> 100 -> [3]         (bit 2 set -> nums[2]=3)
 *      i=5 -> 101 -> [1,3]
 *      i=6 -> 110 -> [2,3]
 *      i=7 -> 111 -> [1,2,3]
 *
 *  import java.util.*;
 *
 *  public class SubsetsBitManipulation {
 *
 *      public List<List<Integer>> subsets(int[] nums) {
 *          int n = nums.length;
 *          int totalSubsets = 1 << n; // 2^n using left bit shift
 *          List<List<Integer>> result = new ArrayList<>();
 *
 *          for (int mask = 0; mask < totalSubsets; mask++) {
 *              List<Integer> subset = new ArrayList<>();
 *              for (int j = 0; j < n; j++) {
 *                  // Check if the j-th bit is set in 'mask'
 *                  if ((mask & (1 << j)) != 0) {
 *                      subset.add(nums[j]);
 *                  }
 *              }
 *              result.add(subset);
 *          }
 *
 *          return result;
 *      }
 *  }
 *
 *
 * ============================================================
 *  5. TIME & SPACE COMPLEXITY (with reasoning)
 * ============================================================
 *
 *  General Analysis:
 *  - There are exactly 2^n subsets.
 *  - Each subset on average has n/2 elements (to copy/build).
 *  - So total work is always O(n * 2^n) regardless of approach.
 *
 *  Approach           | Time         | Space              | Notes
 *  -------------------+--------------+--------------------+----------------------
 *  Iterative          | O(n * 2^n)   | O(n * 2^n)         | Output storage dominates
 *  Backtracking       | O(n * 2^n)   | O(n * 2^n) + O(n)  | Stack depth = n
 *  Bit Manipulation   | O(n * 2^n)   | O(n * 2^n)         | No recursion overhead
 *
 *  Walk-through with numbers:
 *      n=3  -> 2^3  =    8 subsets, each costs O(3)  work ->     ~24 operations
 *      n=10 -> 2^10 = 1024 subsets, each costs O(10) work -> ~10,240 operations
 *
 *  Very fast even at max constraint (n=10).
 *
 *
 * ============================================================
 *  6. COMPLETE WORKED EXAMPLES FOR EACH APPROACH
 * ============================================================
 *
 * ------------------------------------------------------------
 *  Example — Approach 1 (Iterative)
 * ------------------------------------------------------------
 *
 *  Input: nums = [1, 2, 3]
 *
 *  Initial result: [[]]
 *
 *  --- Processing num = 1 ---
 *  currentSize = 1
 *    i=0: clone [] -> add 1 -> [1]   -> result = [[], [1]]
 *
 *  --- Processing num = 2 ---
 *  currentSize = 2
 *    i=0: clone []  -> add 2 -> [2]    -> result = [[], [1], [2]]
 *    i=1: clone [1] -> add 2 -> [1,2]  -> result = [[], [1], [2], [1,2]]
 *
 *  --- Processing num = 3 ---
 *  currentSize = 4
 *    i=0: clone []    -> add 3 -> [3]
 *    i=1: clone [1]   -> add 3 -> [1,3]
 *    i=2: clone [2]   -> add 3 -> [2,3]
 *    i=3: clone [1,2] -> add 3 -> [1,2,3]
 *
 *  Final result: [[], [1], [2], [1,2], [3], [1,3], [2,3], [1,2,3]]
 *
 *
 * ------------------------------------------------------------
 *  Example — Approach 2 (Backtracking)
 * ------------------------------------------------------------
 *
 *  Input: nums = [1, 2, 3]
 *
 *  backtrack(start=0, current=[])
 *    (*) Add []
 *    i=0: add 1 -> current=[1]
 *      backtrack(start=1, current=[1])
 *        (*) Add [1]
 *        i=1: add 2 -> current=[1,2]
 *          backtrack(start=2, current=[1,2])
 *            (*) Add [1,2]
 *            i=2: add 3 -> current=[1,2,3]
 *              backtrack(start=3, current=[1,2,3])
 *                (*) Add [1,2,3]   <- loop doesn't run
 *              remove 3 -> current=[1,2]
 *          remove 2 -> current=[1]
 *        i=2: add 3 -> current=[1,3]
 *          backtrack(start=3, current=[1,3])
 *            (*) Add [1,3]
 *          remove 3 -> current=[1]
 *      remove 1 -> current=[]
 *    i=1: add 2 -> current=[2]
 *      backtrack(start=2, current=[2])
 *        (*) Add [2]
 *        i=2: add 3 -> current=[2,3]
 *          (*) Add [2,3]
 *          remove 3 -> current=[2]
 *      remove 2 -> current=[]
 *    i=2: add 3 -> current=[3]
 *      (*) Add [3]
 *      remove 3 -> current=[]
 *
 *  Output: [[], [1], [1,2], [1,2,3], [1,3], [2], [2,3], [3]]
 *
 *
 * ------------------------------------------------------------
 *  Example — Approach 3 (Bit Manipulation)
 * ------------------------------------------------------------
 *
 *  Input: nums = [1, 2, 3], n=3, totalSubsets=8
 *
 *  mask | Binary | Bit0(nums[0]=1) | Bit1(nums[1]=2) | Bit2(nums[2]=3) | Subset
 *  -----+--------+-----------------+-----------------+-----------------+--------
 *    0  |  000   |       -         |       -         |       -         | []
 *    1  |  001   |       +         |       -         |       -         | [1]
 *    2  |  010   |       -         |       +         |       -         | [2]
 *    3  |  011   |       +         |       +         |       -         | [1,2]
 *    4  |  100   |       -         |       -         |       +         | [3]
 *    5  |  101   |       +         |       -         |       +         | [1,3]
 *    6  |  110   |       -         |       +         |       +         | [2,3]
 *    7  |  111   |       +         |       +         |       +         | [1,2,3]
 *
 *
 * ============================================================
 *  7. EDGE CASES
 * ============================================================
 *
 *  Edge Case           | Input      | Expected Output         | All 3 Handle?
 *  --------------------+------------+-------------------------+--------------
 *  Single element      | [5]        | [[], [5]]               | YES
 *  Max size            | [1..10]    | 1024 subsets            | YES
 *  Negative numbers    | [-1, -2]   | [[], [-1],[-2],[-1,-2]] | YES
 *  Zero in array       | [0, 1]     | [[], [0], [1], [0,1]]   | YES
 *  Single element zero | [0]        | [[], [0]]               | YES
 *  Max/min values      | [-10, 10]  | 4 subsets               | YES
 *
 *  NOTE: The problem guarantees UNIQUE elements, so we don't need
 *  to handle duplicates here. For Subsets II (with duplicates),
 *  you'd need sort + skip logic in the backtracking approach.
 *
 *
 * ============================================================
 *  8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 *  Q: "What edge cases might this miss?"
 *
 *  - Iterative approach: Must snapshot currentSize before the inner
 *    loop — otherwise adding new subsets while iterating causes
 *    incorrect behavior.
 *    FIX: int currentSize = result.size() before inner loop. (DONE)
 *
 *  - Backtracking: Must use new ArrayList<>(current) when adding to
 *    result — otherwise all entries point to the same mutable list
 *    and end up empty.
 *    FIX: result.add(new ArrayList<>(current)) (DONE)
 *
 *  - Bit Manipulation: 1 << n for n=30+ would overflow int.
 *    But since n <= 10 here, int is safe. (SAFE for given constraints)
 *
 *  Q: "Are there any type mismatches?"
 *  - current.remove(current.size() - 1) uses the remove(int index)
 *    overload correctly since current is List<Integer>.
 *    No autoboxing confusion. (SAFE)
 *
 *  Q: "How can I verify this works right now?"
 *
 *  public static void main(String[] args) {
 *      SubsetsBacktracking sol = new SubsetsBacktracking();
 *
 *      // Test 1: Standard case
 *      int[] nums1 = {1, 2, 3};
 *      System.out.println(sol.subsets(nums1));
 *      // Expected: 8 subsets including [] and [1,2,3]
 *
 *      // Test 2: Single element
 *      int[] nums2 = {0};
 *      System.out.println(sol.subsets(nums2));
 *      // Expected: [[], [0]]
 *
 *      // Test 3: Two elements with negatives
 *      int[] nums3 = {-1, 2};
 *      System.out.println(sol.subsets(nums3));
 *      // Expected: [[], [-1], [2], [-1, 2]]
 *
 *      // Verify count: should always be 2^n
 *      assert sol.subsets(nums1).size() == 8;
 *      assert sol.subsets(nums2).size() == 2;
 *      assert sol.subsets(nums3).size() == 4;
 *  }
 *
 *
 * ============================================================
 *  9. COMPANIES & INTERVIEW FREQUENCY
 * ============================================================
 *
 *  Company             | Frequency        | Notes
 *  --------------------+------------------+---------------------
 *  Amazon              | ***** Very High  | Top 20 most asked
 *  Google              | ***** Very High  | Common in phone screens
 *  Facebook / Meta     | ****  High       | Backtracking round
 *  Microsoft           | ****  High       | SDE-2 interviews
 *  Apple               | ***   Medium     |
 *  Bloomberg           | ***   Medium     |
 *  Adobe               | ***   Medium     |
 *  Uber                | ***   Medium     |
 *  LinkedIn            | **    Medium     |
 *  Salesforce          | **    Medium     |
 *
 *  LeetCode Stats: Problem #78 — Appeared in interviews 1,000+ times.
 *  Tagged: Array, Backtracking, Bit Manipulation. Difficulty: Medium.
 *
 *
 * ============================================================
 *  10. FINAL SUMMARY
 * ============================================================
 *
 *  Approach           | Pros                         | Cons                          | Verdict
 *  -------------------+------------------------------+-------------------------------+------------------
 *  Iterative          | Simple, no recursion         | Less intuitive for follow-ups | Good for beginners
 *  Backtracking       | Generalizable, interview gold | Slight overhead from recursion | *** BEST ***
 *  Bit Manipulation   | Elegant, O(1) space overhead | Hard to extend to duplicates  | Great for CP
 *
 *  What to Remember:
 *
 *  "This is the mother of all backtracking problems."
 *  Master the pattern of:
 *      add -> recurse -> remove    (choose, explore, un-choose)
 *  and you unlock solutions to Combinations, Permutations,
 *  Subsets II, Letter Combinations, and dozens of other problems.
 *
 *  The bit manipulation trick — where each integer from 0 to 2^n-1
 *  maps to a subset — is a reusable pattern in competitive programming
 *  wherever you need to enumerate all subsets of a small set.
 *
 * ============================================================
 */
// @formatter:on
