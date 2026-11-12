package Array;

public class BinarySearch {
    public static void main(String[] args) {
        BinarySearch binarySearch = new BinarySearch();
        System.out.println("BinarySearch : " + binarySearch.searchLinearScan(new int[] { -1, 0, 3, 5, 9, 12 }, 9));
        System.out.println("BinarySearch : " + binarySearch.searchLinearScan(new int[] { -1, 0, 3, 5, 9, 12 }, 2));
        System.out.println("----------------------");
        System.out.println("BinarySearch : " + binarySearch.searchIterative(new int[] { -1, 0, 3, 5, 9, 12 }, 9));
        System.out.println("BinarySearch : " + binarySearch.searchIterative(new int[] { -1, 0, 3, 5, 9, 12 }, 2));
        System.out.println("----------------------");
        System.out.println("BinarySearch : " + binarySearch.searchRecursive(new int[] { -1, 0, 3, 5, 9, 12 }, 9));
        System.out.println("BinarySearch : " + binarySearch.searchRecursive(new int[] { -1, 0, 3, 5, 9, 12 }, 2));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/binary-search/description/
     * 
     * Given an array of integers nums which is sorted in ascending order, and an
     * integer target, write a function to search target in nums. If target exists,
     * then return its index. Otherwise, return -1.
     * 
     * You must write an algorithm with O(log n) runtime complexity.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [-1,0,3,5,9,12], target = 9
     * Output: 4
     * Explanation: 9 exists in nums and its index is 4
     * Example 2:
     * 
     * Input: nums = [-1,0,3,5,9,12], target = 2
     * Output: -1
     * Explanation: 2 does not exist in nums so return -1
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 104
     * -104 < nums[i], target < 104
     * All the integers in nums are unique.
     * nums is sorted in ascending order.
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach                | Time     | Space    | Code Complexity | Recommended?                |
     * |-------------------------|----------|----------|-----------------|-----------------------------|
     * | Linear Scan             | O(n)     | O(1)     | Very low        | ❌ Ignores sorted property  |
     * | Iterative Binary Search | O(log n) | O(1)     | Low             | ✅✅ Best overall           |
     * | Recursive Binary Search | O(log n) | O(log n) | Low             | ✅ Fine, but extra stack    |
     * 
     * @param nums
     * @param target
     * @return
     */
    // @formatter:on
    public int searchLinearScan(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target)
                return i;
        }
        return -1;
    }

    // @formatter:off
    /**
     * 
     * | Approach                | Time     | Space    | Code Complexity | Recommended?                |
     * |-------------------------|----------|----------|-----------------|-----------------------------|
     * | Iterative Binary Search | O(log n) | O(1)     | Low             | ✅✅ Best overall           |
     * 
     * @param nums
     * @param target
     * @return
     */
    // @formatter:on
    public int searchIterative(int[] nums, int target) {
        if (target > nums[nums.length - 1] || target < nums[0])
            return -1;
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target)
                return mid;
            else if (nums[mid] < target)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return -1;
    }

    // @formatter:off
    /**
     * 
     * | Approach                | Time     | Space    | Code Complexity | Recommended?                |
     * |-------------------------|----------|----------|-----------------|-----------------------------|
     * | Recursive Binary Search | O(log n) | O(log n) | Low             | ✅ Fine, but extra stack    |
     * 
     * @param nums
     * @param target
     * @return
     */
    // @formatter:on
    public int searchRecursive(int[] nums, int target) {
        return helper(nums, target, 0, nums.length - 1);
    }

    public int helper(int[] nums, int target, int low, int high) {
        if (low > high)
            return -1;
        int mid = low + (high - low) / 2;
        if (nums[mid] == target)
            return mid;
        else if (nums[mid] < target)
            return helper(nums, target, mid + 1, high);
        else
            return helper(nums, target, low, mid - 1);
    }
}
// @formatter:off
/*
 * ============================================================
 * BINARY SEARCH — DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given a SORTED array of integers and a target value, find the index of the
 * target. If the target exists, return its index; if it does not exist, return
 * -1 (classic LeetCode 704 formulation). The defining constraint is that the
 * array is sorted, which lets us discard half the search space each step instead
 * of scanning linearly.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 *  - int[] nums   : array sorted in ascending order, all elements distinct.
 *  - int target   : the value to locate.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 *  - int : index i such that nums[i] == target, or -1 if absent.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 *  - 1 <= nums.length <= 10^4
 *  - -10^4 < nums[i], target < 10^4
 *  - All values in nums are unique.
 *  - nums is sorted in ascending order.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * The position of target inside nums, exploiting the sorted property to achieve
 * logarithmic time rather than linear time.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    nums = [-1, 0, 3, 5, 9, 12], target = 9   -> output: 4
 *    nums = [-1, 0, 3, 5, 9, 12], target = 2   -> output: -1
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Because the array is sorted, looking at the MIDDLE element tells you which
 * half the target must be in. If the middle is too small, the answer can only be
 * to its right; if too big, only to its left. Every comparison throws away half
 * of what remains.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * Finding a word in a physical dictionary:
 *   1. Open to the middle.
 *   2. Is your word before or after this page? Ignore half the book.
 *   3. Repeat on the remaining half.
 *   4. Keep halving until you find the word or run out of pages.
 * Formally: maintain a window [low, high]. Compute mid. Compare nums[mid] to
 * target, then shrink to the correct half. Stop on match or empty window.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                          | Why it's tricky                                             |
 * |------------------------------------|-------------------------------------------------------------|
 * | Loop boundary (<= vs <)            | Off-by-one skips the last candidate or loops forever        |
 * | Computing mid safely               | (low + high) can overflow fixed-width ints                  |
 * | Updating pointers correctly        | Missing +/- 1 re-examines mid forever (infinite loop)       |
 * | Recognizing the "not found" exit   | Must return -1 exactly when the window empties              |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 * | # | Approach                  | Key Idea                          | Best Used When            | Time       | Space         |
 * |---|---------------------------|-----------------------------------|---------------------------|------------|---------------|
 * | 1 | Linear Scan (brute force) | Check each element left to right  | Tiny or unsorted array    | O(n)       | O(1)          |
 * | 2 | Iterative Binary Search   | Halve the [low, high] window loop | General case; sorted input| O(log n) ✅ | O(1) ✅        |
 * | 3 | Recursive Binary Search   | Same halving via recursion        | Recursive clarity wanted  | O(log n)   | O(log n) stack|
 *
 * The linear scan ignores the sorted property and pays O(n). Both binary search
 * variants exploit sorting for O(log n) time. Approach 2 wins outright: same
 * time as recursion but O(1) space vs the recursive O(log n) call stack. Prefer
 * the iterative version unless recursive readability matters more.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Linear Scan (Brute Force)
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Loop i from 0 to n - 1.
 *   2. If nums[i] == target, return i.
 *   3. If loop ends with no match, return -1.
 *
 *    public class LinearSearch {
 *        public int search(int[] nums, int target) {
 *            for (int i = 0; i < nums.length; i++) {
 *                if (nums[i] == target) {
 *                    return i;
 *                }
 *            }
 *            return -1;
 *        }
 *
 *        public static void main(String[] args) {
 *            LinearSearch solver = new LinearSearch();
 *            int[] nums = {-1, 0, 3, 5, 9, 12};
 *            System.out.println(solver.search(nums, 9));  // 4
 *            System.out.println(solver.search(nums, 2));  // -1
 *        }
 *    }
 *
 * Works on any array but wastes the sorted guarantee.
 *
 * ------------------------------------------------------------
 * Approach 2: Iterative Binary Search  ✅ (Optimal)
 * ------------------------------------------------------------
 * Algorithm:
 *   1. low = 0, high = n - 1.
 *   2. While low <= high:
 *        - mid = low + (high - low) / 2   (overflow-safe)
 *        - if nums[mid] == target -> return mid
 *        - if nums[mid] < target  -> low = mid + 1
 *        - else                   -> high = mid - 1
 *   3. Return -1.
 *
 *    public class BinarySearchIterative {
 *        public int search(int[] nums, int target) {
 *            int low = 0;
 *            int high = nums.length - 1;
 *
 *            while (low <= high) {
 *                int mid = low + (high - low) / 2;  // avoids (low + high) overflow
 *
 *                if (nums[mid] == target) {
 *                    return mid;
 *                } else if (nums[mid] < target) {
 *                    low = mid + 1;                 // target is in the right half
 *                } else {
 *                    high = mid - 1;                // target is in the left half
 *                }
 *            }
 *            return -1;
 *        }
 *
 *        public static void main(String[] args) {
 *            BinarySearchIterative solver = new BinarySearchIterative();
 *            int[] nums = {-1, 0, 3, 5, 9, 12};
 *            System.out.println(solver.search(nums, 9));  // 4
 *            System.out.println(solver.search(nums, 2));  // -1
 *        }
 *    }
 *
 * mid = low + (high - low) / 2 equals (low + high) / 2 mathematically but never
 * forms the risky sum, so it cannot overflow int.
 *
 * ------------------------------------------------------------
 * Approach 3: Recursive Binary Search
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Call helper with current low and high.
 *   2. Base case: if low > high, return -1.
 *   3. Compute mid, compare, recurse into correct half with adjusted bounds.
 *
 *    public class BinarySearchRecursive {
 *        public int search(int[] nums, int target) {
 *            return helper(nums, target, 0, nums.length - 1);
 *        }
 *
 *        private int helper(int[] nums, int target, int low, int high) {
 *            if (low > high) {
 *                return -1;                          // window empty -> not found
 *            }
 *            int mid = low + (high - low) / 2;
 *
 *            if (nums[mid] == target) {
 *                return mid;
 *            } else if (nums[mid] < target) {
 *                return helper(nums, target, mid + 1, high);
 *            } else {
 *                return helper(nums, target, low, mid - 1);
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            BinarySearchRecursive solver = new BinarySearchRecursive();
 *            int[] nums = {-1, 0, 3, 5, 9, 12};
 *            System.out.println(solver.search(nums, 9));  // 4
 *            System.out.println(solver.search(nums, 2));  // -1
 *        }
 *    }
 *
 * Recursion depth equals the number of halvings, O(log n).
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 * Approach 1 - Linear Scan
 *   Time:  O(n). Worst case inspects all n elements. n = 10^4 -> ~10,000 checks.
 *   Space: O(1). Only a loop index.
 *
 * Approach 2 - Iterative Binary Search
 *   Time:  O(log n). Each step halves the window: floor(log2 n) + 1 steps.
 *          n = 10^4 -> log2(10000) ~= 13.3 -> at most 14 comparisons.
 *   Space: O(1). Only low, high, mid.
 *
 * Approach 3 - Recursive Binary Search
 *   Time:  O(log n). Same halving, ~14 calls for n = 10^4.
 *   Space: O(log n). One stack frame per call, depth ~log2 n (~14).
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * Shared input: nums = [-1, 0, 3, 5, 9, 12], indices 0..5.
 *
 * ---- Approach 1 - Linear Scan, target = 9 ----
 * | Step | i | nums[i] | Match?             |
 * |------|---|---------|--------------------|
 * | 1    | 0 | -1      | no                 |
 * | 2    | 1 | 0       | no                 |
 * | 3    | 2 | 3       | no                 |
 * | 4    | 3 | 5       | no                 |
 * | 5    | 4 | 9       | yes -> return 4    |
 * Output: 4.
 *
 * ---- Approach 2 - Iterative Binary Search, target = 9 ----
 * | Step | low | high | mid | nums[mid] | Action              |
 * |------|-----|------|-----|-----------|---------------------|
 * | 1    | 0   | 5    | 2   | 3         | 3 < 9 -> low = 3    |
 * | 2    | 3   | 5    | 4   | 9         | match -> return 4   |
 * Output: 4.
 *
 * ---- Approach 2 - not found, target = 2 ----
 * | Step | low | high | mid | nums[mid] | Action                |
 * |------|-----|------|-----|-----------|-----------------------|
 * | 1    | 0   | 5    | 2   | 3         | 3 > 2 -> high = 1     |
 * | 2    | 0   | 1    | 0   | -1        | -1 < 2 -> low = 1     |
 * | 3    | 1   | 1    | 1   | 0         | 0 < 2 -> low = 2      |
 * | 4    | 2   | 1    | -   | -         | low > high -> return -1|
 * Output: -1.
 *
 * ---- Approach 3 - Recursive Binary Search, target = 9 ----
 *   helper(low=0, high=5)
 *   │  mid = 2, nums[2] = 3, 3 < 9 -> recurse right
 *   └─ helper(low=3, high=5)
 *      │  mid = 4, nums[4] = 9 -> match
 *      └─ return 4
 * Output: 4.
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 * | Edge Case                | Input                     | Expected | How Handled                         |
 * |--------------------------|---------------------------|----------|-------------------------------------|
 * | Single element, present  | nums=[5], target=5        | 0        | low=high=0, mid=0, immediate match  |
 * | Single element, absent   | nums=[5], target=3        | -1       | window shrinks to empty -> -1       |
 * | Target is first element  | nums=[1,2,3], target=1    | 0        | window narrows left to index 0      |
 * | Target is last element   | nums=[1,2,3], target=3    | 2        | window narrows right to last index  |
 * | Target below all values  | nums=[2,4,6], target=1    | -1       | high shrinks past low -> -1         |
 * | Target above all values  | nums=[2,4,6], target=9    | -1       | low grows past high -> -1           |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 - Overflow in mid:
 *    // WRONG: low + high can overflow int
 *    int mid = (low + high) / 2;
 *    // CORRECT:
 *    int mid = low + (high - low) / 2;
 *
 * Pitfall 2 - Wrong loop condition:
 *    // WRONG: skips final single-element window
 *    while (low < high) { ... }
 *    // CORRECT:
 *    while (low <= high) { ... }
 *
 * Pitfall 3 - Forgetting +/- 1 on pointers:
 *    // WRONG: re-examines mid forever -> infinite loop
 *    if (nums[mid] < target) low = mid;
 *    // CORRECT:
 *    if (nums[mid] < target) low = mid + 1;
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 * Q: What edge cases might this miss?
 * A: The single-element window at the end of the search. Using low <= high (not
 *    <) guarantees it is inspected. Empty arrays are outside 704's constraints,
 *    but the code still returns -1 safely because high = -1 makes low <= high
 *    false immediately.
 *
 * Q: Are there any type mismatches?
 * A: All operands are int. The overflow-safe mid keeps intermediates in range.
 *    Return type is int; -1 is a valid sentinel distinct from any index.
 *
 * Q: How can I verify this works right now?
 *
 *    public class VerifyBinarySearch {
 *        public int search(int[] nums, int target) {
 *            int low = 0, high = nums.length - 1;
 *            while (low <= high) {
 *                int mid = low + (high - low) / 2;
 *                if (nums[mid] == target) return mid;
 *                else if (nums[mid] < target) low = mid + 1;
 *                else high = mid - 1;
 *            }
 *            return -1;
 *        }
 *
 *        public void verify() {
 *            int[] a = {-1, 0, 3, 5, 9, 12};
 *            assert search(a, 9) == 4  : "mid-array hit failed";
 *            assert search(a, 2) == -1 : "absent value failed";
 *            assert search(a, -1) == 0 : "first element failed";
 *            assert search(a, 12) == 5 : "last element failed";
 *            assert search(new int[]{5}, 5) == 0  : "single present failed";
 *            assert search(new int[]{5}, 3) == -1 : "single absent failed";
 *            System.out.println("All assertions passed.");
 *        }
 *
 *        public static void main(String[] args) {
 *            new VerifyBinarySearch().verify();  // run with: java -ea VerifyBinarySearch
 *        }
 *    }
 *
 * | Approach                | Risk                    | Mitigation                                  |
 * |-------------------------|-------------------------|---------------------------------------------|
 * | Linear Scan             | Too slow for large n    | Use only when unsorted or tiny              |
 * | Iterative Binary Search | Off-by-one / overflow   | Use low <= high and low + (high-low)/2      |
 * | Recursive Binary Search | Stack depth on huge n   | Prefer iterative; depth only O(log n)       |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #704 · Difficulty: Easy · Appears in interviews very frequently.
 *
 * | Company        | Frequency (stars) | Notes                                       |
 * |----------------|-------------------|---------------------------------------------|
 * | Google         | ⭐⭐⭐⭐⭐        | Foundational; extended to rotated/2D variants|
 * | Amazon         | ⭐⭐⭐⭐⭐        | Very common warm-up and follow-up base       |
 * | Microsoft      | ⭐⭐⭐⭐          | Paired with first/last position variants     |
 * | Meta           | ⭐⭐⭐⭐          | Expects clean boundary handling              |
 * | Apple          | ⭐⭐⭐⭐          | Asked in phone screens                       |
 * | Bloomberg      | ⭐⭐⭐⭐          | Popular in on-site rounds                    |
 * | Adobe          | ⭐⭐⭐            | Standard screening question                  |
 * | Uber           | ⭐⭐⭐            | Often a subroutine in harder problems        |
 * | Oracle         | ⭐⭐⭐            | Fundamentals check                           |
 * | Goldman Sachs  | ⭐⭐⭐            | Appears in coding assessments                |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 * | Approach                | Time     | Space    | Code Complexity | Recommended?                |
 * |-------------------------|----------|----------|-----------------|-----------------------------|
 * | Linear Scan             | O(n)     | O(1)     | Very low        | ❌ Ignores sorted property  |
 * | Iterative Binary Search | O(log n) | O(1)     | Low             | ✅✅ Best overall           |
 * | Recursive Binary Search | O(log n) | O(log n) | Low             | ✅ Fine, but extra stack    |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use iterative binary search. Optimal on both axes - O(log n) time and O(1)
 * space - with no trade-off, since one approach wins both.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * Binary search only works on SORTED data, and every step halves the search
 * space to reach O(log n). Memorize the safe skeleton: while (low <= high),
 * mid = low + (high - low) / 2, and always move the losing pointer by +/- 1.
 * The two bugs that bite everyone are the <= boundary and forgetting the +/- 1.
 */
// @formatter:on
