package Array;

public class SearchInsertPosition {
    public static void main(String[] args) {
        SearchInsertPosition searchInsertPosition = new SearchInsertPosition();
        System.out.println(
                "SearchInsertPosition : " + searchInsertPosition.searchInsertLinearScan(new int[] { 1, 3, 5, 6 }, 5));
        System.out.println(
                "SearchInsertPosition : " + searchInsertPosition.searchInsertLinearScan(new int[] { 1, 3, 5, 6 }, 2));
        System.out.println(
                "SearchInsertPosition : " + searchInsertPosition.searchInsertLinearScan(new int[] { 1, 3, 5, 6 }, 7));
        System.out.println("--------------------------");
        System.out.println(
                "SearchInsertPosition : " + searchInsertPosition.searchInsertBinarySearch(new int[] { 1, 3, 5, 6 }, 5));
        System.out.println(
                "SearchInsertPosition : " + searchInsertPosition.searchInsertBinarySearch(new int[] { 1, 3, 5, 6 }, 2));
        System.out.println(
                "SearchInsertPosition : " + searchInsertPosition.searchInsertBinarySearch(new int[] { 1, 3, 5, 6 }, 7));
    }

    // @formatter:off
    /*
     * 
     * https://leetcode.com/problems/search-insert-position/description/?envType=problem-list-v2&envId=array
     * 
     * 
     * Given a sorted array of distinct integers and a target value, return the
     * index if the target is found. If not, return the index where it would be if
     * it were inserted in order.
     * 
     * You must write an algorithm with O(log n) runtime complexity.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,3,5,6], target = 5
     * Output: 2
     * Example 2:
     * 
     * Input: nums = [1,3,5,6], target = 2
     * Output: 1
     * Example 3:
     * 
     * Input: nums = [1,3,5,6], target = 7
     * Output: 4
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 104
     * -104 <= nums[i] <= 104
     * nums contains distinct values sorted in ascending order.
     * -104 <= target <= 104
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach      | Time     | Space | Code Complexity | Recommended?                                   |
     * |---------------|----------|-------|-----------------|------------------------------------------------|
     * | Linear Scan   | O(n)     | O(1)  | Very low        | NO - fails the O(log n) requirement; baseline  |
     * 
     * @param nums
     * @param target
     * @return
     */
    // @formatter:on
    public int searchInsertLinearScan(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target)
                return i;
        }
        return nums.length;
    }

    // @formatter:off
    /**
     * 
     * | Approach      | Time     | Space | Code Complexity | Recommended?                                   |
     * |---------------|----------|-------|-----------------|------------------------------------------------|
     * | Binary Search | O(log n) | O(1)  | Low             | YES (best) - best on time, ties on space       |
     * 
     * @param nums
     * @param target
     * @return
     */
    // @formatter:on
    public int searchInsertBinarySearch(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int middle = low + (high - low) / 2;
            if (nums[middle] == target) {
                return middle;
            } else if (nums[middle] < target) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return low;
    }
}

// @formatter:off
/*
 * ============================================================
 * SEARCH INSERT POSITION - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are given a sorted array of DISTINCT integers and a target value.
 * If the target exists, return its index. If it does not, return the index
 * where it would be inserted to keep the array sorted. An O(log n) runtime
 * is explicitly required.
 *
 * This is LeetCode #35, difficulty Easy.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 *  - int[] nums : a sorted array of distinct integers, ascending.
 *  - int target : the value to locate or place.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 *  - A single int: index of the target, or the index it should be inserted at.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 *  - 1 <= nums.length <= 10^4
 *  - -10^4 <= nums[i] <= 10^4
 *  - nums is strictly ascending (no duplicates)
 *  - -10^4 <= target <= 10^4
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * The smallest index i where nums[i] >= target. If no such index exists,
 * the answer is nums.length (append at the end).
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 * | nums        | target | Output | Why                         |
 * |-------------|--------|--------|-----------------------------|
 * | [1,3,5,6]   | 5      | 2      | 5 is found at index 2       |
 * | [1,3,5,6]   | 2      | 1      | 2 belongs between 1 and 3   |
 * | [1,3,5,6]   | 7      | 4      | 7 larger than all -> append |
 * | [1,3,5,6]   | 0      | 0      | 0 smaller than all -> front |
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Because the array is sorted, the answer is the first slot from the left
 * that is not smaller than the target. Everything before it is < target;
 * everything from it onward is >= target. That boundary IS the insert
 * position - and if the target sits on the boundary, it is also where it
 * is found.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * Think of a bookshelf sorted by height, sliding in a new book:
 *  1. Do not compare against every book. Glance at the middle.
 *  2. If the middle book is shorter than yours, yours belongs to the RIGHT
 *     - ignore the entire left half.
 *  3. If the middle book is taller or equal, yours belongs at the middle or
 *     to the LEFT.
 *  4. Repeat on the surviving half until the window collapses to one gap.
 *     That gap is the insert position.
 * This halve-the-search-space instinct is binary search, which is why the
 * problem demands O(log n).
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                            | Why it's tricky                                                        |
 * |--------------------------------------|------------------------------------------------------------------------|
 * | Position for a MISSING element       | Classic search returns -1 when absent; here that position IS the answer|
 * | Off-by-one on the insert index       | mid vs left vs right - only one is correct at loop exit                 |
 * | The append case                      | Target beyond everything -> answer is nums.length, one past the last   |
 * | Overflow-safe midpoint               | (left + right) / 2 can overflow; use left + (right - left) / 2          |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach       | Key Idea                                         | Best Used When                                | Time      | Space          |
 * |---|----------------|--------------------------------------------------|-----------------------------------------------|-----------|----------------|
 * | 1 | Linear Scan    | Walk left->right, return first nums[i] >= target | Array tiny, or sortedness not assumed         | O(n)      | O(1) space-tie |
 * | 2 | Binary Search  | Halve the range; return left when window closes  | Intended: sorted input, O(log n) required     | O(log n)  | O(1) space-tie |
 *
 * There are only two genuinely distinct approaches, and they share O(1) space.
 * They differ purely on TIME: linear is O(n), binary is O(log n). Since neither
 * costs extra memory, binary search STRICTLY DOMINATES - it wins on time and
 * ties on space, so there is no trade-off. The linear scan is worth knowing
 * only as a correctness baseline or for the rare case where the array is not
 * guaranteed sorted; the problem's own O(log n) requirement rules it out for
 * the intended answer.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Linear Scan
 * ------------------------------------------------------------
 * Algorithm:
 *  1. Iterate i from 0 to nums.length - 1.
 *  2. Check whether nums[i] >= target.
 *  3. The first index satisfying it is the answer (target itself or insert slot).
 *  4. If the loop ends, every element was < target -> return nums.length.
 *
 *    public class SearchInsertLinear {
 *
 *        public static int searchInsert(int[] nums, int target) {
 *            for (int i = 0; i < nums.length; i++) {
 *                // First position whose value is >= target is the answer.
 *                if (nums[i] >= target) {
 *                    return i;
 *                }
 *            }
 *            // Target is larger than every element -> insert at the end.
 *            return nums.length;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {1, 3, 5, 6};
 *            System.out.println(searchInsert(nums, 5)); // 2 (found)
 *            System.out.println(searchInsert(nums, 2)); // 1 (insert)
 *            System.out.println(searchInsert(nums, 7)); // 4 (append)
 *            System.out.println(searchInsert(nums, 0)); // 0 (front)
 *        }
 *    }
 *
 * The single >= comparison folds the "found" and "insert" cases together -
 * no separate equality check is needed.
 *
 * ------------------------------------------------------------
 * Approach 2: Binary Search   (Optimal)
 * ------------------------------------------------------------
 * Algorithm:
 *  1. Set left = 0 and right = nums.length - 1.
 *  2. While left <= right:
 *     - mid = left + (right - left) / 2  (overflow-safe)
 *     - if nums[mid] == target -> return mid (exact hit)
 *     - if nums[mid] < target  -> left = mid + 1  (answer lies strictly right)
 *     - else                   -> right = mid - 1 (answer is mid or to its left)
 *  3. When the loop exits, left is exactly the insert position. Return left.
 *
 * Invariant: everything at indices < left is < target, and everything at
 * indices > right is >= target. When left > right, left is the first >= slot.
 *
 *    public class SearchInsertBinary {
 *
 *        public static int searchInsert(int[] nums, int target) {
 *            int left = 0;
 *            int right = nums.length - 1;
 *
 *            while (left <= right) {
 *                int mid = left + (right - left) / 2; // avoids (left+right) overflow
 *
 *                if (nums[mid] == target) {
 *                    return mid;                      // exact match found
 *                } else if (nums[mid] < target) {
 *                    left = mid + 1;                  // discard left half incl. mid
 *                } else {
 *                    right = mid - 1;                 // discard right half incl. mid
 *                }
 *            }
 *            // left is now the first index where nums[index] >= target.
 *            return left;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {1, 3, 5, 6};
 *            System.out.println(searchInsert(nums, 5)); // 2 (found)
 *            System.out.println(searchInsert(nums, 2)); // 1 (insert)
 *            System.out.println(searchInsert(nums, 7)); // 4 (append)
 *            System.out.println(searchInsert(nums, 0)); // 0 (front)
 *        }
 *    }
 *
 * Why return left and not right? On the final failing iteration, either
 * nums[mid] < target pushed left past right, or nums[mid] > target pulled
 * right below left. In both cases left points at the smallest index whose
 * value is >= target - the insert position.
 *
 * ============================================================
 * 5. TIME AND SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Linear Scan
 * ------------------------------------------------------------
 * Time - O(n). Worst case (target >= last element, or absent at the end)
 * inspects all n elements once, one comparison each.
 *   - n = 10,000 -> up to ~10,000 comparisons.
 *   - n = 16     -> up to 16 comparisons.
 * Space - O(1). Only the loop counter i is stored.
 *
 * ------------------------------------------------------------
 * Approach 2: Binary Search
 * ------------------------------------------------------------
 * Time - O(log n). Each iteration discards half the window:
 * n -> n/2 -> n/4 -> ... -> 1, so iterations = ceil(log2 n).
 *   - n = 10,000 -> about ceil(log2 10000) = 14 iterations.
 *   - n = 16     -> at most log2 16 = 4 iterations.
 * Space - O(1). Only left, right, mid pointers, independent of n. This is
 * iterative, so no recursion call stack (a recursive version would cost
 * O(log n) stack frames).
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Linear Scan - nums = [1,3,5,6], target = 2
 * ------------------------------------------------------------
 * | Step | i | nums[i] | nums[i] >= 2? | Action     |
 * |------|---|---------|---------------|------------|
 * | 1    | 0 | 1       | false         | continue   |
 * | 2    | 1 | 3       | true          | return 1   |
 * Final output: 1 - 2 slots between index 0 (value 1) and index 1 (value 3).
 *
 * ------------------------------------------------------------
 * Approach 2: Binary Search - nums = [1,3,5,6], target = 7
 * ------------------------------------------------------------
 * Initial: left=0, right=3
 * |- Iter 1: mid = 0 + (3-0)/2 = 1 -> nums[1]=3 < 7 -> left = mid+1 = 2
 * |          window is now [2..3]
 * |- Iter 2: mid = 2 + (3-2)/2 = 2 -> nums[2]=5 < 7 -> left = mid+1 = 3
 * |          window is now [3..3]
 * |- Iter 3: mid = 3 + (3-3)/2 = 3 -> nums[3]=6 < 7 -> left = mid+1 = 4
 * |          window is now [4..3] -> left > right, loop ends
 * |_ return left = 4
 * Final output: 4 - 7 exceeds every element, so it appends past the last index.
 *
 * Second trace (exact hit) - nums = [1,3,5,6], target = 5:
 * Initial: left=0, right=3
 * |- Iter 1: mid = 1 -> nums[1]=3 < 5 -> left = 2
 * |- Iter 2: mid = 2 + (3-2)/2 = 2 -> nums[2]=5 == 5 -> return 2
 * Final output: 2 - exact match, returned immediately.
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                     | Input                     | Expected | How Handled                                      |
 * |-------------------------------|---------------------------|----------|--------------------------------------------------|
 * | Target smaller than all       | [1,3,5,6], target=0       | 0        | right shrinks below 0; left stays 0              |
 * | Target larger than all        | [1,3,5,6], target=7       | 4        | left advances past last index to nums.length     |
 * | Target equals first element   | [1,3,5,6], target=1       | 0        | Exact match at mid=0 (or first >= in scan)       |
 * | Target equals last element    | [1,3,5,6], target=6       | 3        | Exact match found at the right end               |
 * | Single-element, target below  | [1], target=0             | 0        | mid=0, nums[0] > 0 -> right=-1, return left=0     |
 * | Single-element, target above  | [1], target=2             | 1        | mid=0, nums[0] < 2 -> left=1, return left=1       |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 - Overflow in the midpoint.
 *    // WRONG: left + right can overflow int for very large indices
 *    int mid = (left + right) / 2;
 *    // CORRECT: mathematically identical, overflow-safe
 *    int mid = left + (right - left) / 2;
 *
 * Pitfall 2 - Returning the wrong pointer at loop exit.
 *    // WRONG: right ends one slot to the left of the answer
 *    return right;
 *    // CORRECT: left is the first index with value >= target
 *    return left;
 *
 * Pitfall 3 - Wrong loop boundary condition.
 *    // WRONG: skips the case left == right, missing single-slot windows
 *    while (left < right) { ... }
 *    // CORRECT: must include equality so a 1-element window is examined
 *    while (left <= right) { ... }
 *
 * ============================================================
 * 8. SELF-CORRECTION AND TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The two dangerous ones are the append case (target bigger than everything,
 *    answer = nums.length) and the front case (target smaller than everything,
 *    answer = 0). Both are covered because binary search returns left, which
 *    lands at nums.length or 0 respectively. A single-element array is the
 *    smallest window, handled by left <= right. The problem guarantees
 *    nums.length >= 1, so an empty array won't appear - though both
 *    implementations return 0 for it, the correct append-into-empty behavior.
 *
 * Q: Are there any type mismatches?
 * A: No. Inputs are int[] and int; the return is int. Indices stay within int
 *    range (n <= 10^4), and the overflow-safe midpoint keeps arithmetic inside
 *    int bounds even at the largest sizes.
 *
 * Q: How can I verify this works right now?
 * A: Run this verify() method - it fires assertions across found, insert,
 *    front, append, and single-element cases.
 *
 *    public class VerifySearchInsert {
 *
 *        static int searchInsert(int[] nums, int target) {
 *            int left = 0, right = nums.length - 1;
 *            while (left <= right) {
 *                int mid = left + (right - left) / 2;
 *                if (nums[mid] == target) return mid;
 *                else if (nums[mid] < target) left = mid + 1;
 *                else right = mid - 1;
 *            }
 *            return left;
 *        }
 *
 *        static void verify() {
 *            assert searchInsert(new int[]{1,3,5,6}, 5) == 2 : "found";
 *            assert searchInsert(new int[]{1,3,5,6}, 2) == 1 : "insert middle";
 *            assert searchInsert(new int[]{1,3,5,6}, 7) == 4 : "append";
 *            assert searchInsert(new int[]{1,3,5,6}, 0) == 0 : "front";
 *            assert searchInsert(new int[]{1},       0) == 0 : "single below";
 *            assert searchInsert(new int[]{1},       2) == 1 : "single above";
 *            System.out.println("All assertions passed.");
 *        }
 *
 *        public static void main(String[] args) {
 *            verify(); // run with: java -ea VerifySearchInsert
 *        }
 *    }
 *
 * Run it with assertions enabled: java -ea VerifySearchInsert
 *
 * | Approach      | Risk                                        | Mitigation                                          |
 * |---------------|---------------------------------------------|-----------------------------------------------------|
 * | Linear Scan   | Too slow O(n) for required O(log n)         | Use only as a baseline; ship binary search          |
 * | Binary Search | Off-by-one / wrong pointer returned         | Rely on left <= right invariant; always return left |
 * | Binary Search | Midpoint overflow at large indices          | Compute mid = left + (right - left) / 2             |
 *
 * ============================================================
 * 9. COMPANIES AND FREQUENCY
 * ============================================================
 *
 * LeetCode #35 - Difficulty: Easy - Appears very frequently as a warm-up /
 * binary-search fundamentals question.
 *
 * | Company            | Frequency | Notes                                         |
 * |--------------------|-----------|-----------------------------------------------|
 * | Amazon             | ****      | Common phone-screen binary-search check       |
 * | Google             | ****      | Probes boundary / off-by-one reasoning        |
 * | Microsoft          | ***       | Frequent easy warm-up before a harder follow-up|
 * | Meta               | ***       | Lead-in to "lower bound" style questions      |
 * | Apple              | ***       | Tests clean binary-search implementation      |
 * | Bloomberg          | ***       | Popular for array/search fundamentals         |
 * | Adobe              | **        | Occasional screening question                 |
 * | Oracle             | **        | Basic binary-search competence check          |
 * | Uber               | **        | Warm-up before system-heavy rounds            |
 * | TikTok / ByteDance | **        | Fundamentals screen                           |
 *
 * The concept it tests - "find the lower bound in a sorted array" - is one of
 * the most reused binary-search patterns across interviews, which is why it
 * shows up far beyond its Easy label.
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach      | Time     | Space | Code Complexity | Recommended?                                   |
 * |---------------|----------|-------|-----------------|------------------------------------------------|
 * | Linear Scan   | O(n)     | O(1)  | Very low        | NO - fails the O(log n) requirement; baseline  |
 * | Binary Search | O(log n) | O(1)  | Low             | YES (best) - best on time, ties on space       |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use binary search. It meets the required O(log n) bound, uses only O(1)
 * extra space, and - because the linear scan matches it on space but loses on
 * time - there is no trade-off: binary search strictly dominates.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * Search Insert Position is the canonical "lower bound" pattern: find the first
 * index where nums[i] >= target. Implement with left <= right, and when the
 * loop exits, RETURN left - that pointer is simultaneously the found position
 * and the insert position. The one gotcha to memorize is the overflow-safe
 * midpoint, mid = left + (right - left) / 2.
 *
 * ============================================================
 * END OF EXPLANATION
 * ============================================================
 */
// @formatter:on
