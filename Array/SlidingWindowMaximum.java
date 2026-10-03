package Array;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.PriorityQueue;

public class SlidingWindowMaximum {
    public static void main(String[] args) {
        SlidingWindowMaximum slidingWindowMaximum = new SlidingWindowMaximum();
        System.out.println("SlidingWindowMaximum : "
                + Arrays.toString(
                        slidingWindowMaximum.maxSlidingWindowBruteForce(new int[] { 1, 3, -1, -3, 5, 3, 6, 7 }, 3)));
        System.out.println("-----------------------------------------------");
        System.out.println("SlidingWindowMaximum : "
                + Arrays.toString(
                        slidingWindowMaximum.maxSlidingWindowMaxHeap(new int[] { 1, 3, -1, -3, 5, 3, 6, 7 }, 3)));
        System.out.println("-----------------------------------------------");
        System.out.println("SlidingWindowMaximum : "
                + Arrays.toString(
                        slidingWindowMaximum.maxSlidingWindowMonotonicDeque(new int[] { 1, 3, -1, -3, 5, 3, 6, 7 },
                                3)));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/sliding-window-maximum/description/
     * 
     * You are given an array of integers nums, there is a sliding window of size k
     * which is moving from the very left of the array to the very right. You can
     * only see the k numbers in the window. Each time the sliding window moves
     * right by one position.
     * 
     * Return the max sliding window.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
     * Output: [3,3,5,5,6,7]
     * Explanation: 
     * Window position                Max
     * ---------------               -----
     * [1  3  -1] -3  5  3  6  7       3
     *  1 [3  -1  -3] 5  3  6  7       3
     *  1  3 [-1  -3  5] 3  6  7       5
     *  1  3  -1 [-3  5  3] 6  7       5
     *  1  3  -1  -3 [5  3  6] 7       6
     *  1  3  -1  -3  5 [3  6  7]      7
     * Example 2:
     * 
     * Input: nums = [1], k = 1
     * Output: [1]
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     *  Approach        | Time       | Space | Code Complexity | Recommended?
     *  ----------------|------------|-------|-----------------|-----------------------------------
     *  Brute force     | O(n*k)     | O(1)  | Very low        | NO - too slow beyond tiny inputs;
     *                  |            |       |                 | best for low memory only
     * 
     * @param nums
     * @param k
     * @return
     */
    // @formatter:on
    public int[] maxSlidingWindowBruteForce(int[] nums, int k) {
        int n = nums.length;
        if (n == 0 || k == 0)
            return new int[0];
        int[] maxSlidingWindow = new int[n - k + 1];
        for (int i = 0; i + k <= n; i++) {
            int maxInWindow = 0;
            for (int j = i; j < i + k; j++) {
                maxInWindow = Math.max(maxInWindow, nums[j]);
            }
            maxSlidingWindow[i] = maxInWindow;
        }
        return maxSlidingWindow;
    }

    // @formatter:off
    /**
     * 
     *  Approach        | Time       | Space | Code Complexity | Recommended?
     *  ----------------|------------|-------|-----------------|-----------------------------------
     *  Max-heap        | O(n log n) | O(n)  | Medium          | OK - good when a priority structure
     *                  |            |       |                 | is reused elsewhere
     * 
     * @param nums
     * @param k
     * @return
     */
    // @formatter:on
    public int[] maxSlidingWindowMaxHeap(int[] nums, int k) {
        int n = nums.length;
        if (n == 0 || k == 0)
            return new int[0];
        int[] result = new int[n - k + 1];
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));
        for (int i = 0; i < n; i++) {
            pq.offer(new int[] { nums[i], i });
            if (i >= k - 1) {
                while (pq.peek()[1] <= i - k)
                    pq.poll();
                result[i - k + 1] = pq.peek()[0];
            }
        }
        return result;
    }

    // @formatter:off
    /**
     * 
     *  Approach        | Time       | Space | Code Complexity | Recommended?
     *  ----------------|------------|-------|-----------------|-----------------------------------
     *  Monotonic deque | O(n)       | O(k)  | Medium          | BEST overall - linear time, tiny space
     * 
     * @param nums
     * @param k
     * @return
     */
    // @formatter:on
    public int[] maxSlidingWindowMonotonicDeque(int[] nums, int k) {
        int n = nums.length;
        if (n == 0 || k == 0)
            return new int[0];
        int[] result = new int[n - k + 1];
        Deque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!dq.isEmpty() && dq.peekFirst() <= i - k)
                dq.pollFirst();
            while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i])
                dq.pollLast();
            dq.offerLast(i);
            if (i >= k - 1)
                result[i - k + 1] = nums[dq.peekFirst()];
        }
        return result;
    }
}

// @formatter:off
/*
 * ============================================================
 * SLIDING WINDOW MAXIMUM - DEEP DIVE EXPLANATION
 * ============================================================
 * LeetCode 239  |  Difficulty: Hard
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are given an integer array and a window of fixed width k.
 * Imagine that window sitting over the first k elements, then
 * sliding one step to the right at a time until it reaches the
 * end. At every position the window occupies, you must report
 * the largest value currently inside it. The answer is the
 * ordered list of those maxima, one per window position.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 *  - int[] nums : the array of integers.
 *  - int k      : the window width, 1 <= k <= nums.length.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 *  - int[] of length nums.length - k + 1 : the maximum of each
 *    window, left to right.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 *  - 1 <= nums.length <= 10^5
 *  - -10^4 <= nums[i] <= 10^4
 *  - 1 <= k <= nums.length
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * For every start index i from 0 to n - k, compute
 * max(nums[i..i+k-1]), and place these maxima into the result
 * array in order. The value range fits comfortably in int, so
 * overflow is not a concern for the values themselves.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *  nums = [1, 3, -1, -3, 5, 3, 6, 7], k = 3
 *
 *  window                     max
 *  [1  3  -1] -3  5  3  6  7 -> 3
 *   1 [3  -1  -3] 5  3  6  7 -> 3
 *   1  3 [-1  -3  5] 3  6  7 -> 5
 *   1  3  -1 [-3  5  3] 6  7 -> 5
 *   1  3  -1  -3 [5  3  6] 7 -> 6
 *   1  3  -1  -3  5 [3  6  7]-> 7
 *
 *  output = [3, 3, 5, 5, 6, 7]
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * The naive instinct is to re-scan every window from scratch,
 * but that repeats enormous amounts of work: neighbouring
 * windows overlap in k-1 elements. The real insight is that
 * most elements can never be the answer. If a newer element is
 * bigger than an older one still in the window, that older
 * element is permanently useless - it will expire before the
 * newer one and is always overshadowed while both are present.
 * So we keep only "candidates that could still win," in
 * decreasing order, and the front of that shortlist is the
 * current maximum.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 *  1. Slide the window one step. One new element enters on the
 *     right; one old element may fall off the left.
 *  2. Ask: "Does this new element make anyone already waiting
 *     obsolete?" Any waiting candidate smaller than the newcomer
 *     can be discarded - the newcomer is younger AND larger.
 *  3. Keep the surviving candidates sorted from largest
 *     (oldest-still-relevant) to smallest.
 *  4. Before reading the answer, evict the front candidate if it
 *     has slid out of the window's left edge.
 *  5. The front of the shortlist is now the window's maximum.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 *  Challenge                  | Why it's tricky
 *  ---------------------------|----------------------------------
 *  Overlapping windows        | Adjacent windows share k-1
 *                             | elements; recomputing each from
 *                             | scratch wastes work -> O(n*k).
 *  The max can leave window   | When the current maximum slides
 *                             | off the left, you need the next
 *                             | largest instantly - you must have
 *                             | tracked runners-up.
 *  Duplicates                 | Equal values must not wrongly
 *                             | evict each other, or a valid max
 *                             | leaves the structure too early.
 *  Achieving true O(n)        | Every candidate is added once and
 *                             | removed once; linear time needs
 *                             | O(1) push/pop at both ends.
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *  # | Approach        | Key Idea                    | Best Used When            | Time       | Space
 *  --|-----------------|-----------------------------|---------------------------|------------|--------------------
 *  1 | Brute force     | Re-scan all k elements of   | Tiny n or k; clarity      | O(n*k)     | O(1) [space-optimal]
 *    |                 | every window                | over speed                |            |
 *  2 | Max-heap (lazy) | Push (value,index); pop     | Also need order stats /   | O(n log n) | O(n)
 *    |                 | stale entries off the top   | a priority structure      |            |
 *  3 | Monotonic deque | Keep window indices with    | This exact problem -      | O(n)       | O(k)
 *    |                 | decreasing values; front=max| the intended answer       | [time-opt] |
 *
 * The three approaches trade time against space along genuinely
 * different axes. Brute force uses the least memory (O(1)) but
 * its time is unacceptable for n = 10^5. The heap is a solid
 * middle ground and reuses a general-purpose data structure, but
 * it carries a log n factor and can hold up to n entries. The
 * monotonic deque is the intended optimal: strictly linear time
 * because each index is pushed and popped at most once, and only
 * O(k) extra space. Prefer the deque in essentially all cases;
 * reach for brute force only when O(1) auxiliary memory is a hard
 * requirement and inputs are small.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Algorithm, step by step:
 *  1. Allocate result of length n - k + 1.
 *  2. For each window start i from 0 to n - k:
 *     a. Initialise mx to nums[i].
 *     b. Scan j from i to i + k - 1, mx = max(mx, nums[j]).
 *     c. Store mx at result[i].
 *  3. Return result.
 *
 *    import java.util.Arrays;
 *
 *    public class SlidingWindowMaxBrute {
 *        public static int[] maxSlidingWindow(int[] nums, int k) {
 *            int n = nums.length;
 *            if (n == 0 || k == 0) return new int[0];
 *            int[] result = new int[n - k + 1];
 *            for (int i = 0; i + k <= n; i++) {         // each window start
 *                int mx = nums[i];
 *                for (int j = i; j < i + k; j++) {       // scan the whole window
 *                    mx = Math.max(mx, nums[j]);
 *                }
 *                result[i] = mx;
 *            }
 *            return result;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
 *            System.out.println(Arrays.toString(maxSlidingWindow(nums, 3)));
 *            // [3, 3, 5, 5, 6, 7]
 *        }
 *    }
 *
 * The result length n - k + 1 counts how many positions a
 * width-k window can occupy in an array of length n.
 *
 * ------------------------------------------------------------
 * Approach 2: Max-Heap with Lazy Deletion
 * ------------------------------------------------------------
 * Algorithm, step by step:
 *  1. Create a max-heap ordered by value, storing (value,index).
 *  2. For each i, push (nums[i], i).
 *  3. Once the window is full (i >= k - 1):
 *     a. While the top's index is <= i - k (outside the window),
 *        pop it - lazy deletion: stale entries are removed only
 *        when they surface at the top.
 *     b. The top's value is the current window maximum; write it
 *        to result[i - k + 1].
 *  4. Return result.
 *
 *    import java.util.*;
 *
 *    public class SlidingWindowMaxHeap {
 *        public static int[] maxSlidingWindow(int[] nums, int k) {
 *            int n = nums.length;
 *            if (n == 0 || k == 0) return new int[0];
 *            int[] result = new int[n - k + 1];
 *            // max-heap by value; Integer.compare avoids any subtraction overflow
 *            PriorityQueue<int[]> pq =
 *                new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));
 *            for (int i = 0; i < n; i++) {
 *                pq.offer(new int[]{nums[i], i});
 *                if (i >= k - 1) {
 *                    // discard maxima that have slid out of the window
 *                    while (pq.peek()[1] <= i - k) pq.poll();
 *                    result[i - k + 1] = pq.peek()[0];
 *                }
 *            }
 *            return result;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
 *            System.out.println(Arrays.toString(maxSlidingWindow(nums, 3)));
 *            // [3, 3, 5, 5, 6, 7]
 *        }
 *    }
 *
 * Stale entries are not removed the moment they expire - only
 * when they reach the top and block a read. This keeps each
 * insertion O(log n) while guaranteeing the reported top is
 * always in-window.
 *
 * ------------------------------------------------------------
 * Approach 3: Monotonic Deque (Optimal)
 * ------------------------------------------------------------
 * Algorithm, step by step:
 *  1. Keep a deque of indices whose corresponding values are
 *     strictly decreasing from front to back.
 *  2. For each i:
 *     a. Expire the front: while the front index is <= i - k,
 *        remove it - it has left the window.
 *     b. Maintain monotonicity: while the back index's value is
 *        < nums[i], pop it - it can never again be a maximum.
 *     c. Append i to the back.
 *     d. If i >= k - 1, the front index holds the window's
 *        maximum; write nums[front] to result[i - k + 1].
 *  3. Return result.
 *
 *    import java.util.*;
 *
 *    public class SlidingWindowMaxDeque {
 *        public static int[] maxSlidingWindow(int[] nums, int k) {
 *            int n = nums.length;
 *            if (n == 0 || k == 0) return new int[0];
 *            int[] result = new int[n - k + 1];
 *            Deque<Integer> dq = new ArrayDeque<>(); // holds indices, values decreasing
 *
 *            for (int i = 0; i < n; i++) {
 *                // 1) drop indices that have fallen out of the window on the left
 *                while (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();
 *                // 2) drop back indices whose values are dominated by nums[i]
 *                while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) dq.pollLast();
 *                // 3) this index is now a live candidate
 *                dq.offerLast(i);
 *                // 4) once the first full window is formed, front is the max
 *                if (i >= k - 1) result[i - k + 1] = nums[dq.peekFirst()];
 *            }
 *            return result;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
 *            System.out.println(Arrays.toString(maxSlidingWindow(nums, 3)));
 *            // [3, 3, 5, 5, 6, 7]
 *        }
 *    }
 *
 * The strict < in step 2 means equal values are both kept, so a
 * duplicate maximum is not evicted early - the older copy expires
 * naturally by index while the newer copy still guards the answer.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 * ------------------------------------------------------------
 * Approach 1 - Brute Force
 * ------------------------------------------------------------
 *  Time : O(n*k). There are n - k + 1 windows, each scanned in k
 *         steps: (n - k + 1)*k = Theta(n*k). For n = 10^5,
 *         k = 5*10^4 that is ~5*10^9 operations - far too slow.
 *  Space: O(1) auxiliary - just the loop scalars (output array
 *         is not counted as auxiliary).
 *  Estimate: n=1000, k=500 -> ~250,000 ops. n=10^5, k=100 ->
 *         ~10^7 ops (fine only for small k).
 *
 * ------------------------------------------------------------
 * Approach 2 - Max-Heap
 * ------------------------------------------------------------
 *  Time : O(n log n). Each of the n elements is inserted once;
 *         lazy deletion removes each at most once. The heap can
 *         grow to n entries, so every offer/poll is O(log n).
 *  Space: O(n). Stale entries linger until they reach the top,
 *         so the heap may hold up to n pairs simultaneously.
 *  Estimate: n=10^5 -> ~10^5 * 17 = 1.7*10^6 comparisons.
 *
 * ------------------------------------------------------------
 * Approach 3 - Monotonic Deque
 * ------------------------------------------------------------
 *  Time : O(n). Each index is added exactly once and removed at
 *         most once; every inner while iteration is a distinct
 *         removal. Total pushes + pops <= 2n -> linear,
 *         independent of k (amortized).
 *  Space: O(k). The deque never holds more than k indices - older
 *         indices have already been expired from the front.
 *  Estimate: n=10^5 -> at most 2*10^5 deque operations.
 *
 * ============================================================
 * 6. COMPLEXITY COMPARISON GRAPH
 * ============================================================
 *  TIME - Size of input data (n)  vs  Time to complete
 *  time |                                  / Brute  O(n*k)      Bad
 *       |                            /
 *       |                      /          ___ Heap  O(n log n)  Fair
 *       |                /  __/‾‾‾
 *       |           /_ _/‾‾
 *       |      /_/‾‾        _______________ Deque  O(n)  Good  [optimal]
 *       |  /_/‾  ____/‾‾‾
 *       |/_/‾‾
 *       +--------------------------------------> n
 *        small                          large
 *
 *  SPACE - Size of input data (n)  vs  Memory used
 *   mem |                                ___ Heap  O(n)   Good
 *       |                          ___/‾‾
 *       |                    ___/‾‾
 *       |              ___/‾‾      ______ Deque  O(k)  Good
 *       |        __ /‾‾ __/‾‾‾‾‾‾‾
 *       |   _ /‾__/‾‾
 *       |/_/‾  ______________________________ Brute  O(1)  Excellent  [optimal]
 *       +--------------------------------------> n
 *        small                          large
 *
 * Reading them off: the deque is optimal on time (O(n), flattest
 * time curve), while brute force is optimal on space (O(1)); the
 * deque's O(k) memory is negligible, so it wins the practical
 * trade-off overwhelmingly - brute force's constant-space edge
 * never justifies its O(n*k) blow-up on real inputs.
 *
 * ============================================================
 * 7. COMPLETE WORKED EXAMPLES
 * ============================================================
 * Input for all three: nums = [1, 3, -1, -3, 5, 3, 6, 7], k = 3.
 * Expected output: [3, 3, 5, 5, 6, 7].
 *
 * ------------------------------------------------------------
 * Approach 1 - Brute Force (scan each window fully)
 * ------------------------------------------------------------
 *  Start i | Window     | Scan -> max | result
 *  --------|------------|-------------|------------------
 *  0       | 1, 3, -1   | max = 3     | [3]
 *  1       | 3, -1, -3  | max = 3     | [3,3]
 *  2       | -1, -3, 5  | max = 5     | [3,3,5]
 *  3       | -3, 5, 3   | max = 5     | [3,3,5,5]
 *  4       | 5, 3, 6    | max = 6     | [3,3,5,5,6]
 *  5       | 3, 6, 7    | max = 7     | [3,3,5,5,6,7]
 *
 * ------------------------------------------------------------
 * Approach 2 - Max-Heap (top is largest in-window value@index)
 * ------------------------------------------------------------
 *  i | push  | top after evicting stale | write
 *  --|-------|--------------------------|---------------
 *  0 | 1@0   | (window not full)        | -
 *  1 | 3@1   | -                        | -
 *  2 | -1@2  | top 3@1 in window        | result[0]=3
 *  3 | -3@3  | top 3@1 in window        | result[1]=3
 *  4 | 5@4   | top 5@4                  | result[2]=5
 *  5 | 3@5   | top 5@4 in window        | result[3]=5
 *  6 | 6@6   | top 6@6                  | result[4]=6
 *  7 | 7@7   | top 7@7                  | result[5]=7
 *  Final: [3, 3, 5, 5, 6, 7].
 *
 * ------------------------------------------------------------
 * Approach 3 - Monotonic Deque (indices; values in brackets)
 * ------------------------------------------------------------
 *  i | nums[i] | expire front | pop smaller backs | push | deque (values)          | write
 *  --|---------|--------------|-------------------|------|-------------------------|------------
 *  0 | 1       | -            | -                 | 0    | [0(1)]                  | -
 *  1 | 3       | -            | pop 0(1)          | 1    | [1(3)]                  | -
 *  2 | -1      | -            | -                 | 2    | [1(3),2(-1)]            | result[0]=3
 *  3 | -3      | -            | -                 | 3    | [1(3),2(-1),3(-3)]      | result[1]=3
 *  4 | 5       | pop 1 (<=1)  | pop 3(-3),2(-1)   | 4    | [4(5)]                  | result[2]=5
 *  5 | 3       | -            | -                 | 5    | [4(5),5(3)]             | result[3]=5
 *  6 | 6       | -            | pop 5(3),4(5)     | 6    | [6(6)]                  | result[4]=6
 *  7 | 7       | -            | pop 6(6)          | 7    | [7(7)]                  | result[5]=7
 *  Final: [3, 3, 5, 5, 6, 7] - all three agree.
 *
 * ============================================================
 * 8. EDGE CASES
 * ============================================================
 *  Edge Case              | Input                        | Expected        | How Handled
 *  -----------------------|------------------------------|-----------------|----------------------------------
 *  Single element, k=1    | nums=[1], k=1                | [1]             | Length n-k+1=1; element is its
 *                         |                              |                 | own window max.
 *  k=1 (one cell)         | nums=[9,8,7], k=1            | [9,8,7]         | Deque holds one index; front
 *                         |                              |                 | always equals nums[i].
 *  k=n (one window)       | nums=[1,2,3,4,5], k=5        | [5]             | Single output; front ends at the
 *                         |                              |                 | global max index.
 *  Strictly decreasing    | nums=[9,8,7,6,5], k=2        | [9,8,7,6]       | No back-pops; front expires each
 *                         |                              |                 | step -> left element.
 *  Strictly increasing    | nums=[1,2,3,4,5], k=2        | [2,3,4,5]       | Each new element pops all backs;
 *                         |                              |                 | deque stays size 1.
 *  All equal              | nums=[4,4,4,4], k=2          | [4,4,4]         | Strict < keeps duplicates; older
 *                         |                              |                 | copies expire by index correctly.
 *  Negatives + duplicates | nums=[-7,-8,7,5,7,1,6,0],k=4 | [7,7,7,7,7]     | Equal 7s coexist; newer 7 guards
 *                         |                              |                 | the max after the older expires.
 *
 * ============================================================
 * 9. APPROACH RISK MITIGATION
 * ============================================================
 *  Approach        | Risk                              | Mitigation
 *  ----------------|-----------------------------------|----------------------------------------
 *  Brute force     | O(n*k) times out for large n and  | Use only for small inputs or as a
 *                  | mid-size k.                       | correctness oracle; switch to the deque
 *                  |                                   | for n >= ~10^4.
 *  Max-heap        | Forgetting lazy deletion returns  | Always evict index <= i - k before
 *                  | an out-of-window max; comparator  | reading the top; use Integer.compare in
 *                  | subtraction (b[0]-a[0]) overflows.| the comparator.
 *  Monotonic deque | Using <= instead of < when        | Pop backs only on strict <; store
 *                  | popping backs evicts equal values | INDICES so front-expiry can compare
 *                  | early; storing values breaks      | against i - k.
 *                  | window-expiry.                    |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *  Approach        | Time       | Space | Code Complexity | Recommended?
 *  ----------------|------------|-------|-----------------|-----------------------------------
 *  Brute force     | O(n*k)     | O(1)  | Very low        | NO - too slow beyond tiny inputs;
 *                  |            |       |                 | best for low memory only
 *  Max-heap        | O(n log n) | O(n)  | Medium          | OK - good when a priority structure
 *                  |            |       |                 | is reused elsewhere
 *  Monotonic deque | O(n)       | O(k)  | Medium          | BEST overall - linear time, tiny space
 *
 * Recommended Approach: The monotonic deque - O(n) time and O(k)
 * space. Brute force is the only one that beats it on space
 * (O(1)), so choose brute force solely when constant auxiliary
 * memory is mandatory and inputs are small.
 *
 * What to Remember: This is the archetypal monotonic deque
 * pattern - keep a deque of INDICES whose values are strictly
 * decreasing, so the front is always the window maximum. Pop
 * expired indices off the front (compare against i - k) and pop
 * dominated indices off the back (strict < to preserve
 * duplicates). Each index enters and leaves at most once, which
 * is what buys the amortized O(n).
 * ============================================================
 */
// @formatter:on
