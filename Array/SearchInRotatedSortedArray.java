package Array;

public class SearchInRotatedSortedArray {
    public static void main(String[] args) {
        SearchInRotatedSortedArray searchInRotatedSortedArray = new SearchInRotatedSortedArray();
        System.out.println("SearchInRotatedSortedArray : "
                + searchInRotatedSortedArray.searchBruteForce(new int[] { 4, 5, 6, 7, 0, 1, 2 }, 0));
        System.out.println("SearchInRotatedSortedArray : "
                + searchInRotatedSortedArray.searchBruteForce(new int[] { 4, 5, 6, 7, 0, 1, 2 }, 3));
        System.out.println("SearchInRotatedSortedArray : "
                + searchInRotatedSortedArray.searchBruteForce(new int[] { 1 }, 0));
        System.out.println("----------------------------");
        System.out.println("SearchInRotatedSortedArray : "
                + searchInRotatedSortedArray.searchFindPivotBinarySearch(new int[] { 4, 5, 6, 7, 0, 1, 2 }, 0));
        System.out.println("SearchInRotatedSortedArray : "
                + searchInRotatedSortedArray.searchFindPivotBinarySearch(new int[] { 4, 5, 6, 7, 0, 1, 2 }, 3));
        System.out.println("SearchInRotatedSortedArray : "
                + searchInRotatedSortedArray.searchFindPivotBinarySearch(new int[] { 1 }, 0));
        System.out.println("----------------------------");
        System.out.println("SearchInRotatedSortedArray : "
                + searchInRotatedSortedArray.searchOnePassModifiedBinarySearch(new int[] { 4, 5, 6, 7, 0, 1, 2 }, 0));
        System.out.println("SearchInRotatedSortedArray : "
                + searchInRotatedSortedArray.searchOnePassModifiedBinarySearch(new int[] { 4, 5, 6, 7, 0, 1, 2 }, 3));
        System.out.println("SearchInRotatedSortedArray : "
                + searchInRotatedSortedArray.searchOnePassModifiedBinarySearch(new int[] { 1 }, 0));
    }

    // @formatter:off
    /**
     * 
     * 
     * https://leetcode.com/problems/search-in-rotated-sorted-array/description/?envType=problem-list-v2&envId=array
     * 
     * There is an integer array nums sorted in ascending order (with distinct
     * values).
     * 
     * Prior to being passed to your function, nums is possibly left rotated at an
     * unknown index k (1 <= k < nums.length) such that the resulting array is
     * [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]
     * (0-indexed). For example, [0,1,2,4,5,6,7] might be left rotated by 3 indices
     * and become [4,5,6,7,0,1,2].
     * 
     * Given the array nums after the possible rotation and an integer target,
     * return the index of target if it is in nums, or -1 if it is not in nums.
     * 
     * You must write an algorithm with O(log n) runtime complexity.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [4,5,6,7,0,1,2], target = 0
     * Output: 4
     * Example 2:
     * 
     * Input: nums = [4,5,6,7,0,1,2], target = 3
     * Output: -1
     * Example 3:
     * 
     * Input: nums = [1], target = 0
     * Output: -1
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 5000
     * -104 <= nums[i] <= 104
     * All values of nums are unique.
     * nums is an ascending array that is possibly rotated.
     * -104 <= target <= 104
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach                        | Time     | Space     | Code Complexity | Recommended?                            |
     * |---------------------------------|----------|-----------|-----------------|-----------------------------------------|
     * | Brute Force (linear)            | O(n)     | O(1)      | Very low        | ❌ Violates O(log n) - baseline only   |
     * 
     * @param nums
     * @param target
     * @return
     */
    // @formatter:on
    public int searchBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target)
                return i;
        }
        return -1;
    }

    // @formatter:off
    /**
     * 
     * | Approach                        | Time     | Space     | Code Complexity | Recommended?                            |
     * |---------------------------------|----------|-----------|-----------------|-----------------------------------------|
     * | Find Pivot + Binary Search      | O(log n) | O(1)      | Medium          | ✅ When you also need the pivot/minimum|
     * 
     * @param nums
     * @param target
     * @return
     */
    // @formatter:on
    public int searchFindPivotBinarySearch(int[] nums, int target) {
        int n = nums.length;
        int pivot = findPivot(nums);
        if (pivot == 0)
            return binarySearch(nums, 0, n - 1, target);
        if (target >= nums[pivot] && target <= nums[n - 1])
            return binarySearch(nums, pivot, n - 1, target);

        return binarySearch(nums, 0, pivot - 1, target);
    }

    public int binarySearch(int[] nums, int lo, int hi, int target) {
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target)
                return mid;
            if (nums[mid] < target)
                lo = mid + 1;
            else
                hi = mid - 1;
        }
        return -1;
    }

    public int findPivot(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] > nums[hi]) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    // @formatter:off
    /**
     * 
     * | Approach                        | Time     | Space     | Code Complexity | Recommended?                            |
     * |---------------------------------|----------|-----------|-----------------|-----------------------------------------|
     * | One-Pass Modified Binary Search | O(log n) | O(1)      | Low-medium      | ✅✅ Best general-purpose              |
     * 
     * @param nums
     * @param target
     * @return
     */
    // @formatter:on
    public int searchOnePassModifiedBinarySearch(int[] nums, int target) {
        int n = nums.length;
        int left = 0, right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            if (nums[left] <= nums[mid]) {
                if (nums[left] <= target && target <= nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (nums[mid] <= target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
}
// @formatter:off
/*
 * ============================================================
 * SEARCH IN ROTATED SORTED ARRAY - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are given an array originally sorted in ascending order with
 * DISTINCT values, then rotated at some unknown pivot. Example:
 * [0,1,2,4,5,6,7] rotated at index 4 becomes [4,5,6,7,0,1,2].
 * Given this rotated array and a target, return the INDEX of the
 * target, or -1 if absent. Must run in O(log n) time.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * - int[] nums : rotated sorted array of DISTINCT integers
 *                (may be rotated 0 times, i.e., still sorted)
 * - int target : the value to locate
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * - int : index of target in nums, or -1 if not present
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * - 1 <= nums.length <= 5000
 * - -10^4 <= nums[i] <= 10^4
 * - All values in nums are unique
 * - -10^4 <= target <= 10^4
 * - nums is a possibly-rotated ascending sorted array
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * The single index i with nums[i] == target, in logarithmic time.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  nums = [4,5,6,7,0,1,2], target = 0  -> Output: 4
 *    Input:  nums = [4,5,6,7,0,1,2], target = 3  -> Output: -1
 * This is LeetCode #33 (Medium).
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * A rotated sorted array is like a clock face cut at one point and
 * laid flat. Cut it ANYWHERE in the middle and at least one of the
 * two halves is still perfectly sorted. We adapt binary search to
 * first find WHICH half is sorted, then check if the target could
 * live inside that sorted half's known value range.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Pick the middle element nums[mid].
 * 2. If nums[left] <= nums[mid], the LEFT half is sorted;
 *    otherwise the RIGHT half is sorted.
 * 3. The sorted half's value range is exactly its two endpoints.
 *    Ask: does target fall inside this range?
 * 4. If yes, search inside the sorted half; if no, search the
 *    other (messy) half, which will itself contain a sorted
 *    sub-half next round.
 * 5. Repeat, halving the search space, until found or empty.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                     | Why it's tricky |
 * |-------------------------------|-----------------|
 * | Array isn't globally sorted   | Plain binary search's rule breaks at the rotation point |
 * | Identifying the sorted half   | Wrong < vs <= causes infinite loops or missed hits |
 * | Range-boundary checks         | Must use the sorted half's actual endpoints with exact inequalities |
 * | Rotation could be zero        | Non-rotated array must still work (left-half-sorted branch handles it) |
 * | Off-by-one pointer updates    | mid-1 / mid+1 must be consistent or you loop forever |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach | Key Idea | Best Used When | Time | Space |
 * |---|----------|----------|----------------|------|-------|
 * | 1 | Brute Force (linear scan) | Check every element | n tiny, log-n irrelevant | O(n) | O(1) |
 * | 2 | Find Pivot, then Binary Search | Find rotation index, pick sorted segment, standard binary search | You also need the pivot | O(log n) | O(1) |
 * | 3 | One-Pass Modified Binary Search | Detect sorted half each step, decide side | General single-query case (cleanest) | O(log n) ✅ | O(1) ✅ |
 *
 * All three use O(1) space, so space is not a differentiating axis;
 * the decision is time and code cleanliness. Brute force is O(n) and
 * violates the required bound (teaching baseline only). Approaches 2
 * and 3 are both O(log n)/O(1) but genuinely distinct: approach 2 does
 * TWO binary searches (find pivot, then search a chosen segment);
 * approach 3 folds everything into ONE pass. Approach 3 is optimal:
 * fewer moving parts, no separate pivot handling. Prefer approach 2
 * only if you also need the pivot index (many queries / find minimum).
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (Linear Scan)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Iterate i from 0 to n-1.
 * 2. If nums[i] == target, return i.
 * 3. If no match, return -1.
 *
 *    public class RotatedSearchBruteForce {
 *        public static int search(int[] nums, int target) {
 *            for (int i = 0; i < nums.length; i++) {
 *                if (nums[i] == target) {
 *                    return i;                 // found it
 *                }
 *            }
 *            return -1;                        // not present
 *        }
 *        public static void main(String[] args) {
 *            int[] nums = {4, 5, 6, 7, 0, 1, 2};
 *            System.out.println(search(nums, 0));   // 4
 *            System.out.println(search(nums, 3));   // -1
 *        }
 *    }
 *
 * Ignores the sorted structure entirely - correct, but O(n).
 *
 * ------------------------------------------------------------
 * Approach 2: Find Pivot, then Binary Search
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Binary-search the pivot (smallest element) by comparing
 *    nums[mid] with nums[right]: if nums[mid] > nums[right],
 *    pivot is right (left = mid + 1), else right = mid.
 * 2. After the loop, left is the pivot index.
 * 3. If target is in [nums[pivot], nums[n-1]], search the right
 *    segment; otherwise search the left segment.
 * 4. Run standard binary search on the chosen segment.
 *
 *    public class RotatedSearchPivot {
 *        public static int search(int[] nums, int target) {
 *            int n = nums.length;
 *            int pivot = findPivot(nums);
 *            if (pivot == 0) {                       // not rotated
 *                return binarySearch(nums, 0, n - 1, target);
 *            }
 *            if (target >= nums[pivot] && target <= nums[n - 1]) {
 *                return binarySearch(nums, pivot, n - 1, target);
 *            }
 *            return binarySearch(nums, 0, pivot - 1, target);
 *        }
 *        private static int findPivot(int[] nums) {
 *            int left = 0, right = nums.length - 1;
 *            while (left < right) {
 *                int mid = left + (right - left) / 2;
 *                if (nums[mid] > nums[right]) left = mid + 1;
 *                else right = mid;
 *            }
 *            return left;
 *        }
 *        private static int binarySearch(int[] nums, int lo, int hi, int target) {
 *            while (lo <= hi) {
 *                int mid = lo + (hi - lo) / 2;
 *                if (nums[mid] == target) return mid;
 *                if (nums[mid] < target) lo = mid + 1;
 *                else hi = mid - 1;
 *            }
 *            return -1;
 *        }
 *        public static void main(String[] args) {
 *            int[] nums = {4, 5, 6, 7, 0, 1, 2};
 *            System.out.println(search(nums, 0));   // 4
 *            System.out.println(search(nums, 6));   // 2
 *            System.out.println(search(nums, 3));   // -1
 *        }
 *    }
 *
 * Pivot search compares against nums[right] (not nums[left]) so it
 * converges to the minimum cleanly, even for an already-sorted array.
 *
 * ------------------------------------------------------------
 * Approach 3: One-Pass Modified Binary Search  ✅ (Optimal)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. left = 0, right = n - 1.
 * 2. While left <= right, mid = left + (right - left) / 2.
 * 3. If nums[mid] == target, return mid.
 * 4. If nums[left] <= nums[mid], LEFT half [left, mid] is sorted:
 *      if nums[left] <= target < nums[mid] -> right = mid - 1;
 *      else -> left = mid + 1.
 *    Else RIGHT half [mid, right] is sorted:
 *      if nums[mid] < target <= nums[right] -> left = mid + 1;
 *      else -> right = mid - 1.
 * 5. If the window empties, return -1.
 *
 *    public class RotatedSearchOnePass {
 *        public static int search(int[] nums, int target) {
 *            int left = 0, right = nums.length - 1;
 *            while (left <= right) {
 *                int mid = left + (right - left) / 2;
 *                if (nums[mid] == target) {
 *                    return mid;
 *                }
 *                if (nums[left] <= nums[mid]) {              // left half sorted
 *                    if (nums[left] <= target && target < nums[mid]) {
 *                        right = mid - 1;
 *                    } else {
 *                        left = mid + 1;
 *                    }
 *                } else {                                    // right half sorted
 *                    if (nums[mid] < target && target <= nums[right]) {
 *                        left = mid + 1;
 *                    } else {
 *                        right = mid - 1;
 *                    }
 *                }
 *            }
 *            return -1;
 *        }
 *        public static void main(String[] args) {
 *            int[] nums = {4, 5, 6, 7, 0, 1, 2};
 *            System.out.println(search(nums, 0));          // 4
 *            System.out.println(search(nums, 5));          // 1
 *            System.out.println(search(nums, 3));          // -1
 *            System.out.println(search(new int[]{1}, 1));  // 0
 *        }
 *    }
 *
 * Key subtlety: nums[left] <= nums[mid] (with <=) correctly classifies
 * a single left element as a sorted half when left == mid.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Time  O(n): worst case inspects all n elements. n = 5000 -> ~5000 comparisons.
 * Space O(1): only a loop counter.
 *
 * ------------------------------------------------------------
 * Approach 2: Find Pivot, then Binary Search
 * ------------------------------------------------------------
 * Time  O(log n): pivot search ~log2 n + binary search ~log2 n = ~2 log2 n.
 *       n = 5000 -> ~13 + ~13 = ~26 comparisons.
 * Space O(1): a few index variables; iterative, no recursion.
 *
 * ------------------------------------------------------------
 * Approach 3: One-Pass Modified Binary Search
 * ------------------------------------------------------------
 * Time  O(log n): each iteration discards half. n = 5000 -> ~13 iterations,
 *       roughly half the work of approach 2.
 * Space O(1): only left, right, mid; iterative.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 - Linear Scan   (nums=[4,5,6,7,0,1,2], target=0)
 * ------------------------------------------------------------
 * | i | nums[i] | Match? |
 * |---|---------|--------|
 * | 0 | 4       | no     |
 * | 1 | 5       | no     |
 * | 2 | 6       | no     |
 * | 3 | 7       | no     |
 * | 4 | 0       | ✅ return 4 |
 * Output: 4
 *
 * ------------------------------------------------------------
 * Approach 2 - Find Pivot, then Binary Search  (target=6)
 * ------------------------------------------------------------
 * Phase 1 - find pivot (nums[mid] vs nums[right]):
 *    left=0,right=6,mid=3 -> 7 > 2 -> left=4
 *    left=4,right=6,mid=5 -> 1 <= 2 -> right=5
 *    left=4,right=5,mid=4 -> 0 <= 1 -> right=4
 *    left==right==4 -> pivot=4 (nums[4]=0 is min)
 * Phase 2 - target 6 in [0, 2]? No -> search left segment [0,3]:
 *    lo=0,hi=3,mid=1 -> 5 < 6 -> lo=2
 *    lo=2,hi=3,mid=2 -> 6 == 6 -> return 2 ✅
 * Output: 2
 *
 * ------------------------------------------------------------
 * Approach 3 - One-Pass Modified Binary Search  (target=0)
 * ------------------------------------------------------------
 * | Step | left | right | mid | nums[mid] | Sorted half        | Decision |
 * |------|------|-------|-----|-----------|--------------------|----------|
 * | 1    | 0    | 6     | 3   | 7         | left [0,3] (4<=7)  | 4<=0<7? no -> left=4 |
 * | 2    | 4    | 6     | 5   | 1         | left [4,5] (0<=1)  | 0<=0<1? yes -> right=4 |
 * | 3    | 4    | 4     | 4   | 0         | -                  | nums[4]==0 -> return 4 ✅ |
 * Output: 4
 *
 * Second trace - target = 3 (absent):
 *    [0,6] mid=3 nums=7, left sorted, 4<=3<7? no -> left=4
 *    [4,6] mid=5 nums=1, left sorted (0<=1), 0<=3<1? no -> left=6
 *    [6,6] mid=6 nums=2, left sorted (2<=2), 2<=3<2? no -> left=7
 *    left(7) > right(6) -> return -1
 * Output: -1
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case | Input | Expected | How Handled |
 * |-----------|-------|----------|-------------|
 * | Single element, matches | [5], t=5 | 0 | equality check returns immediately |
 * | Single element, no match | [5], t=3 | -1 | loop runs once, window empties |
 * | Not rotated (sorted) | [1,2,3,4,5], t=4 | 3 | left-half-sorted branch = plain binary search |
 * | Target at rotation point | [4,5,6,7,0,1,2], t=0 | 4 | right-half range check locates it |
 * | Largest, pre-rotation | [4,5,6,7,0,1,2], t=7 | 3 | left-half range + equality catch |
 * | Target absent | [4,5,6,7,0,1,2], t=3 | -1 | window collapses, returns -1 |
 * | Two elements, rotated | [2,1], t=1 | 1 | right half detected sorted, moves left |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * - Wrong inequality on sorted-half test:
 *      WRONG:   if (nums[left] < nums[mid]) { ... }   // fails when left==mid
 *      CORRECT: if (nums[left] <= nums[mid]) { ... }
 * - Range check off-by-one:
 *      WRONG:   if (nums[left] <= target && target <= nums[mid]) right = mid;
 *      CORRECT: if (nums[left] <= target && target < nums[mid]) right = mid - 1;
 * - Overflow in mid: use left + (right - left) / 2, not (left + right) / 2.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: Single-element windows and zero-rotation. Both covered: the
 *    nums[left] <= nums[mid] test (with <=) treats a one-element
 *    sorted half correctly, and a fully sorted array degenerates to
 *    standard binary search. NOTE: this assumes DISTINCT values;
 *    duplicates (LeetCode 81) need an extra left++ shrink step.
 *
 * Q: Are there any type mismatches?
 * A: No. All indices and values are int, well within range; mid uses
 *    left + (right - left) / 2 to avoid overflow; returns int.
 *
 * Q: How can I verify this works right now?
 *    public class RotatedSearchVerify {
 *        public static void main(String[] args) {
 *            assert search(new int[]{4,5,6,7,0,1,2}, 0) == 4;
 *            assert search(new int[]{4,5,6,7,0,1,2}, 3) == -1;
 *            assert search(new int[]{4,5,6,7,0,1,2}, 7) == 3;
 *            assert search(new int[]{5}, 5) == 0;
 *            assert search(new int[]{5}, 3) == -1;
 *            assert search(new int[]{1,2,3,4,5}, 4) == 3;
 *            assert search(new int[]{2,1}, 1) == 1;
 *            System.out.println("All tests passed.");   // run with -ea
 *        }
 *        static int search(int[] nums, int target) {
 *            int left = 0, right = nums.length - 1;
 *            while (left <= right) {
 *                int mid = left + (right - left) / 2;
 *                if (nums[mid] == target) return mid;
 *                if (nums[left] <= nums[mid]) {
 *                    if (nums[left] <= target && target < nums[mid]) right = mid - 1;
 *                    else left = mid + 1;
 *                } else {
 *                    if (nums[mid] < target && target <= nums[right]) left = mid + 1;
 *                    else right = mid - 1;
 *                }
 *            }
 *            return -1;
 *        }
 *    }
 * Run with: java -ea RotatedSearchVerify
 *
 * | Approach | Risk | Mitigation |
 * |----------|------|------------|
 * | Brute Force | Too slow O(n) | Use only as baseline / oracle |
 * | Find Pivot + BS | Two-phase off-by-one | Test pivot fn separately; verify pivot==0 case |
 * | One-Pass BS | Wrong </<= loops or misses | Keep <= on sorted-half test, strict bounds inside |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * LeetCode #33 - Medium - one of the most-asked binary-search variants.
 *
 * | Company | Frequency | Notes |
 * |---------|-----------|-------|
 * | Amazon | ⭐⭐⭐⭐⭐ | Extremely common phone/onsite screen |
 * | Google | ⭐⭐⭐⭐⭐ | Classic modify-binary-search question |
 * | Microsoft | ⭐⭐⭐⭐ | Often paired with duplicates variant (81) |
 * | Meta | ⭐⭐⭐⭐ | Often asked with find-minimum (153) |
 * | Apple | ⭐⭐⭐⭐ | Common in coding rounds |
 * | Bloomberg | ⭐⭐⭐⭐ | Tests edge-case rigor |
 * | Adobe | ⭐⭐⭐ | Intern/new-grad loops |
 * | Uber | ⭐⭐⭐ | Array-rotation themes |
 * | LinkedIn | ⭐⭐⭐ | Binary-search focus rounds |
 * | Oracle | ⭐⭐ | Occasional |
 * | Goldman Sachs | ⭐⭐ | Quant/dev screens |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach | Time | Space | Code Complexity | Recommended? |
 * |----------|------|-------|-----------------|--------------|
 * | Brute Force (linear) | O(n) | O(1) | Very low | ❌ Violates O(log n) - baseline only |
 * | Find Pivot + Binary Search | O(log n) | O(1) | Medium | ✅ When you also need the pivot/minimum |
 * | One-Pass Modified Binary Search | O(log n) | O(1) | Low-medium | ✅✅ Best general-purpose |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use Approach 3 (one-pass modified binary search) for the standard
 * single-query problem: same asymptotics as the pivot method but with
 * one loop and no pivot bookkeeping. Use Approach 2 only when you also
 * need the rotation index. All three are O(1) space, so choose on time
 * and clarity, not memory.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * In a rotated sorted array with distinct values, at least one half
 * around mid is always sorted - detect it with nums[left] <= nums[mid],
 * then check whether the target lies within that sorted half's range to
 * decide which side to keep. The killer gotcha is inequality precision:
 * <= on sorted-half detection, strict bounds on the inner range test.
 * This half-is-always-sorted trick powers the whole rotated-array
 * family (33, 81, 153, 154).
 */
// @formatter:on