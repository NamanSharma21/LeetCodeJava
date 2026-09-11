package Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKFrequentElements {
    public static void main(String[] args) {
        TopKFrequentElements topKFrequentElements = new TopKFrequentElements();
        System.out.println("TopKFrequentElements : "
                + Arrays.toString(
                        topKFrequentElements.topKFrequentFrequencyMapSort(new int[] { 1, 1, 1, 2, 2, 3 }, 2)));
        System.out.println("---------------------------------");
        System.out.println("TopKFrequentElements : "
                + Arrays.toString(
                        topKFrequentElements.topKFrequentFrequencyMapMinHeap(new int[] { 1, 1, 1, 2, 2, 3 }, 2)));
        System.out.println("---------------------------------");
        System.out.println("TopKFrequentElements : "
                + Arrays.toString(
                        topKFrequentElements.topKFrequentFrequencyMapBucketSort(new int[] { 1, 1, 1, 2, 2, 3 }, 2)));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/top-k-frequent-elements/description/
     * 
     * Given an integer array nums and an integer k, return the k most frequent
     * elements. You may return the answer in any order.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,1,1,2,2,3], k = 2
     * 
     * Output: [1,2]
     * 
     * Example 2:
     * 
     * Input: nums = [1], k = 1
     * 
     * Output: [1]
     * 
     * Example 3:
     * 
     * Input: nums = [1,2,1,2,1,2,3,1,3,2], k = 2
     * 
     * Output: [1,2]
     * 
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 105
     * -104 <= nums[i] <= 104
     * k is in the range [1, the number of unique elements in the array].
     * It is guaranteed that the answer is unique.
     * 
     * 
     * Follow up: Your algorithm's time complexity must be better than O(n log n),
     * where n is the array's size.
     */
   // @formatter:on

   // @formatter:off
   /**
    * | # | Approach                    | Key Idea                                                    | Best Used When                            | Time       | Space |
    * |---|-----------------------------|-------------------------------------------------------------|-------------------------------------------|------------|-------|
    * | 1 | Frequency Map + Sort        | Count, sort distinct values by frequency desc, take first k | k close to number of distinct values      | O(n log n) | O(n)  |
    * 
    * @param nums
    * @param k
    * @return
    */
   // @formatter:on
    public int[] topKFrequentFrequencyMapSort(int[] nums, int k) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        List<Integer> uniueElements = new ArrayList<>(frequencyMap.keySet());
        uniueElements.sort((a, b) -> frequencyMap.get(b) - frequencyMap.get(a));
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = uniueElements.get(i);
        }
        return result;
    }

    // @formatter:off
   /**
    * | # | Approach                    | Key Idea                                                    | Best Used When                            | Time       | Space |
    * |---|-----------------------------|-------------------------------------------------------------|-------------------------------------------|------------|-------|
    * | 2 | Frequency Map + Min-Heap(k) | Count, keep size-k min-heap, evict least frequent           | k much smaller than distinct count        | O(n log k) | O(n)  |
    * 
    * @param nums
    * @param k
    * @return
    */
   // @formatter:on
    public int[] topKFrequentFrequencyMapMinHeap(int[] nums, int k) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> frequencyMap.get(a) - frequencyMap.get(b));

        for (int num : frequencyMap.keySet()) {
            minHeap.offer(num);
            if (minHeap.size() > k)
                minHeap.poll();
        }

        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = minHeap.poll();
        }
        return result;
    }

    // @formatter:off
   /**
    * | # | Approach                    | Key Idea                                                    | Best Used When                            | Time       | Space |
    * |---|-----------------------------|-------------------------------------------------------------|-------------------------------------------|------------|-------|
    * | 3 | Frequency Map + Bucket Sort | Count, bucket by frequency, scan buckets high -> low        | You want guaranteed linear time           | O(n) [BEST]| O(n)  |
    * 
    * @param nums
    * @param k
    * @return
    */
   // @formatter:on
    public int[] topKFrequentFrequencyMapBucketSort(int[] nums, int k) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums)
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        @SuppressWarnings("unchecked")
        List<Integer>[] buckets = new List[nums.length + 1];
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int freq = entry.getValue();
            if (buckets[freq] == null) {
                buckets[freq] = new ArrayList<>();
            }
            buckets[freq].add(entry.getKey());
        }

        int[] result = new int[k];
        int index = 0;
        for (int freq = buckets.length - 1; freq >= 0 && index < k; freq--) {
            if (buckets[freq] != null) {
                for (int num : buckets[freq]) {
                    result[index++] = num;
                    if (index == k)
                        break;
                }
            }
        }
        return result;
    }

}

// @formatter:off
/*
 * ============================================================
 * TOP K FREQUENT ELEMENTS - DEEP DIVE EXPLANATION
 * LeetCode #347 - Medium
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an array of integers, return the k values that appear MOST often.
 * The answer is guaranteed unique, and the returned order does not matter.
 * This is LeetCode #347 - Top K Frequent Elements (Medium).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 *  - int[] nums : the array of integers (may be negative, may repeat).
 *  - int k      : how many of the most frequent values to return.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 *  - int[] of length k : the k most frequent elements, in any order.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 *  - 1 <= nums.length <= 10^5
 *  - -10^4 <= nums[i] <= 10^4
 *  - k is in the range [1, number of distinct elements]
 *  - The answer is guaranteed to be unique (no ambiguous ties at the cutoff).
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * For each distinct value, compute how many times it occurs (its frequency).
 * Then select the k values with the highest frequencies.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  nums = [1, 1, 1, 2, 2, 3], k = 2
 *    Counts: 1 -> 3, 2 -> 2, 3 -> 1
 *    Top 2 by frequency: 1 (freq 3), 2 (freq 2)
 *    Output: [1, 2]
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Think of it as a popularity contest. First tally the votes each candidate
 * received (build a frequency map). Then hand out trophies to the top k
 * vote-getters. The problem splits into two clean phases: COUNT, then
 * SELECT the largest counts.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 *  1. Sweep the array once, keeping a running tally of each number's count.
 *  2. Now you have a small table of (value -> count) pairs, one row per
 *     distinct value.
 *  3. You no longer care about the original array; you only need the k rows
 *     with the biggest counts.
 *  4. "Find the k largest" is a well-known sub-problem with standard tools:
 *     sort them, use a heap, or exploit that counts are bounded integers
 *     (bucket sort).
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                              | Why it's tricky                                                         |
 * |----------------------------------------|-------------------------------------------------------------------------|
 * | Selecting "top k" efficiently          | Full sort is O(n log n); you often do better since you need only top k. |
 * | Frequencies bounded, values are not    | A count never exceeds n, enabling bucket sort - but map BY frequency.   |
 * | Heap direction is counter-intuitive    | To keep k largest, use a MIN-heap of size k and evict the smallest.     |
 * | Negative and repeated values           | Map key handles negatives; you cannot index a raw array by value.       |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 * | # | Approach                    | Key Idea                                                    | Best Used When                            | Time       | Space |
 * |---|-----------------------------|-------------------------------------------------------------|-------------------------------------------|------------|-------|
 * | 1 | Frequency Map + Sort        | Count, sort distinct values by frequency desc, take first k | k close to number of distinct values      | O(n log n) | O(n)  |
 * | 2 | Frequency Map + Min-Heap(k) | Count, keep size-k min-heap, evict least frequent           | k much smaller than distinct count        | O(n log k) | O(n)  |
 * | 3 | Frequency Map + Bucket Sort | Count, bucket by frequency, scan buckets high -> low        | You want guaranteed linear time           | O(n) [BEST]| O(n)  |
 *
 * All three share the same O(n) counting phase and the same O(n) space, so
 * SPACE is not the differentiator - the axis that separates them is TIME.
 * Sorting is easiest to remember but pays O(n log n). The min-heap improves
 * the selection phase to O(n log k), a real win when k << distinct count.
 * Bucket sort is the clear time-optimal choice at O(n) because frequencies
 * are integers bounded by n, letting us "sort" by counting instead of
 * comparing. Prefer bucket sort when time is the constraint; prefer the
 * min-heap for simpler idiomatic code with small k; reach for sort only when
 * brevity beats speed.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Frequency Map + Sort
 * ------------------------------------------------------------
 * Algorithm:
 *  1. Build a HashMap<Integer,Integer> of value -> count in one pass.
 *  2. Copy the distinct keys into a list.
 *  3. Sort the list by frequency in DESCENDING order.
 *  4. Take the first k entries into the result array.
 *
 *    import java.util.*;
 *
 *    public class TopKFrequentSorting {
 *        public int[] topKFrequent(int[] nums, int k) {
 *            Map<Integer, Integer> frequencyMap = new HashMap<>();
 *            for (int num : nums) {
 *                frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
 *            }
 *
 *            List<Integer> uniqueElements = new ArrayList<>(frequencyMap.keySet());
 *            // Sort distinct values by their frequency, highest first.
 *            uniqueElements.sort((a, b) -> frequencyMap.get(b) - frequencyMap.get(a));
 *
 *            int[] result = new int[k];
 *            for (int i = 0; i < k; i++) {
 *                result[i] = uniqueElements.get(i);
 *            }
 *            return result;
 *        }
 *
 *        public static void main(String[] args) {
 *            TopKFrequentSorting solver = new TopKFrequentSorting();
 *            int[] nums = {1, 1, 1, 2, 2, 3};
 *            int k = 2;
 *            System.out.println(Arrays.toString(solver.topKFrequent(nums, k))); // [1, 2]
 *        }
 *    }
 *
 * The comparator sorts descending. Frequencies are at most 10^5, so this
 * subtraction cannot overflow int here - but with larger counts use
 * Integer.compare(...) to be safe.
 *
 * ------------------------------------------------------------
 * Approach 2: Frequency Map + Min-Heap (size k)
 * ------------------------------------------------------------
 * Algorithm:
 *  1. Build the frequency map.
 *  2. Create a MIN-heap ordered by frequency (smallest frequency on top).
 *  3. Offer each distinct value; whenever the heap grows beyond k, poll the
 *     top - this discards the LEAST frequent so far.
 *  4. The k survivors are the most frequent. Drain them into the result.
 *
 *    import java.util.*;
 *
 *    public class TopKFrequentHeap {
 *        public int[] topKFrequent(int[] nums, int k) {
 *            Map<Integer, Integer> frequencyMap = new HashMap<>();
 *            for (int num : nums) {
 *                frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
 *            }
 *
 *            // Min-heap keyed by frequency: LEAST frequent element sits on top.
 *            PriorityQueue<Integer> minHeap =
 *                new PriorityQueue<>((a, b) -> frequencyMap.get(a) - frequencyMap.get(b));
 *
 *            for (int num : frequencyMap.keySet()) {
 *                minHeap.offer(num);
 *                if (minHeap.size() > k) {
 *                    minHeap.poll(); // drop the current least-frequent survivor
 *                }
 *            }
 *
 *            int[] result = new int[k];
 *            // Draining a min-heap yields ascending frequency; fill from the
 *            // back so the array ends highest-frequency first (order optional).
 *            for (int i = k - 1; i >= 0; i--) {
 *                result[i] = minHeap.poll();
 *            }
 *            return result;
 *        }
 *
 *        public static void main(String[] args) {
 *            TopKFrequentHeap solver = new TopKFrequentHeap();
 *            int[] nums = {1, 1, 1, 2, 2, 3};
 *            int k = 2;
 *            System.out.println(Arrays.toString(solver.topKFrequent(nums, k))); // [1, 2]
 *        }
 *    }
 *
 * Key insight: to retain the k LARGEST, evict from a MIN-heap. The heap never
 * exceeds k elements, so each insert/evict costs O(log k), not O(log n).
 *
 * ------------------------------------------------------------
 * Approach 3: Frequency Map + Bucket Sort [OPTIMAL]
 * ------------------------------------------------------------
 * Algorithm:
 *  1. Build the frequency map.
 *  2. Create an array buckets of length n+1, where buckets[f] holds every
 *     value whose frequency is exactly f (a frequency never exceeds n).
 *  3. Walk buckets from the highest index down to 0, collecting values until
 *     you have gathered k of them.
 *
 *    import java.util.*;
 *
 *    public class TopKFrequentBucket {
 *        public int[] topKFrequent(int[] nums, int k) {
 *            Map<Integer, Integer> frequencyMap = new HashMap<>();
 *            for (int num : nums) {
 *                frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
 *            }
 *
 *            // buckets[f] = list of all numbers that appear exactly f times.
 *            List<Integer>[] buckets = new List[nums.length + 1];
 *            for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
 *                int freq = entry.getValue();
 *                if (buckets[freq] == null) {
 *                    buckets[freq] = new ArrayList<>();
 *                }
 *                buckets[freq].add(entry.getKey());
 *            }
 *
 *            int[] result = new int[k];
 *            int index = 0;
 *            // Scan from the highest possible frequency downward.
 *            for (int freq = buckets.length - 1; freq >= 0 && index < k; freq--) {
 *                if (buckets[freq] != null) {
 *                    for (int num : buckets[freq]) {
 *                        result[index++] = num;
 *                        if (index == k) break;
 *                    }
 *                }
 *            }
 *            return result;
 *        }
 *
 *        public static void main(String[] args) {
 *            TopKFrequentBucket solver = new TopKFrequentBucket();
 *            int[] nums = {1, 1, 1, 2, 2, 3};
 *            int k = 2;
 *            System.out.println(Arrays.toString(solver.topKFrequent(nums, k))); // [1, 2]
 *        }
 *    }
 *
 * The enabling bound: a frequency is an integer in [1, n]. That lets us use
 * frequency directly as an array index and replace comparison-sorting with a
 * single linear scan. new List[...] triggers an unchecked generic-array
 * warning - harmless, avoidable via List<List<Integer>> if preferred.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Sort
 * ------------------------------------------------------------
 * Time: Counting O(n). Let m = distinct values (m <= n). Sorting distinct
 *       values is O(m log m) = O(n log n) worst case. Total: O(n log n).
 * Space: Map up to m entries + list of keys -> O(n).
 * Numeric feel: n=100000, all distinct -> ~100000 * 17 ~= 1.7M comparisons.
 *
 * ------------------------------------------------------------
 * Approach 2: Min-Heap
 * ------------------------------------------------------------
 * Time: Counting O(n). Each of m distinct values offered to a heap capped at
 *       k costs O(log k) -> O(m log k) = O(n log k). Since k <= m <= n, this
 *       is <= O(n log n) and much faster when k is small.
 * Space: Map O(n) + heap O(k) -> O(n) overall.
 * Numeric feel: n=100000 distinct, k=10 -> ~100000 * log2(10) ~= 330000 heap
 *       operations, roughly 5x fewer than a full sort.
 *
 * ------------------------------------------------------------
 * Approach 3: Bucket Sort [OPTIMAL]
 * ------------------------------------------------------------
 * Time: Counting O(n), building buckets O(m), final downward scan touches at
 *       most n+1 slots plus m stored values -> O(n).
 * Space: Frequency map O(m) + bucket array size n+1 -> O(n).
 * Numeric feel: n=100000 -> ~100000 insertions + scan of ~100001 slots
 *       ~= 200000 simple operations, no log factor at all.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * Input for all three: nums = [1, 1, 1, 2, 2, 3], k = 2.
 * Frequency map (shared): { 1->3, 2->2, 3->1 }, n = 6.
 *
 * ------------------------------------------------------------
 * Approach 1: Sort
 * ------------------------------------------------------------
 *    Distinct keys list: [1, 2, 3]
 *    Sort by frequency DESC:
 *      freq(1)=3, freq(2)=2, freq(3)=1
 *      sorted order -> [1, 2, 3]
 *    Take first k=2 -> [1, 2]
 *    Output: [1, 2]
 *
 * ------------------------------------------------------------
 * Approach 2: Min-Heap (size k = 2)
 * ------------------------------------------------------------
 *    offer 1  -> heap {1(f3)}                         size 1 <= 2, keep
 *    offer 2  -> heap {2(f2), 1(f3)}  (top = 2, min)  size 2 <= 2, keep
 *    offer 3  -> heap {3(f1), 1(f3), 2(f2)}           size 3 > 2 -> poll top
 *                poll removes 3(f1) -> heap {2(f2), 1(f3)}
 *    Drain (min first): poll 2(f2), then poll 1(f3)
 *    Fill result back-to-front -> result[1]=2, result[0]=1
 *    Output: [1, 2]
 *
 * ------------------------------------------------------------
 * Approach 3: Bucket Sort
 * ------------------------------------------------------------
 *    buckets index by frequency (length n+1 = 7):
 *      buckets[0] = null
 *      buckets[1] = [3]
 *      buckets[2] = [2]
 *      buckets[3] = [1]
 *      buckets[4..6] = null
 *
 *    Scan from freq=6 downward, collecting until we have k=2:
 *      freq 6 -> null
 *      freq 5 -> null
 *      freq 4 -> null
 *      freq 3 -> [1]  -> take 1        (index 1)
 *      freq 2 -> [2]  -> take 2        (index 2 == k, stop)
 *    Output: [1, 2]
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 * | Edge Case                | Input                 | Expected Output       | How Handled                                        |
 * |--------------------------|-----------------------|-----------------------|----------------------------------------------------|
 * | Single element           | nums=[1], k=1         | [1]                   | Map has one entry; every approach returns it.      |
 * | All identical values     | nums=[5,5,5,5], k=1   | [5]                   | One map entry freq 4; all approaches pick it.      |
 * | k = distinct count       | nums=[1,2,3], k=3     | [1,2,3] (any order)   | Every distinct value qualifies; loops fill all k.  |
 * | Negative values          | nums=[-1,-1,2], k=1   | [-1]                  | HashMap keys handle negatives; no value-as-index.  |
 * | Ties below the cutoff    | nums=[1,1,2,2,3], k=2 | [1,2] (unique)        | Problem guarantees no ambiguous boundary tie.      |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 - Using a max-heap of ALL elements when you only need k.
 *    WRONG: push all distinct values into a max-heap -> O(n log n), no better than sort.
 *    CORRECT: min-heap capped at size k -> O(n log k), evict when size > k.
 *
 * Pitfall 2 - Indexing buckets by VALUE instead of FREQUENCY.
 *    WRONG:   buckets[num].add(...);   // negatives/huge values -> out of bounds
 *    CORRECT: buckets[freq].add(num);  // frequency is bounded by n
 *
 * Pitfall 3 - Comparator subtraction overflow (general safety).
 *    RISKY:  (a, b) -> freq.get(b) - freq.get(a);
 *    SAFER:  (a, b) -> Integer.compare(freq.get(b), freq.get(a));
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 * Q: What edge cases might this miss?
 * A: The bucket array must be sized n+1, not n - a value can appear n times,
 *    so index n must exist. Off-by-one here throws ArrayIndexOutOfBounds.
 *    Also confirm the scan stops as soon as index == k so you never overrun
 *    result.
 *
 * Q: Are there any type mismatches?
 * A: new List[nums.length + 1] produces an unchecked generic-array warning
 *    (compiles and runs fine). Comparator lambdas return int as required.
 *    result is int[]; minHeap.poll() returns Integer which auto-unboxes
 *    cleanly since the heap is never empty when we poll exactly k times.
 *
 * Q: How can I verify this works right now?
 *    public static void verify() {
 *        TopKFrequentBucket s = new TopKFrequentBucket();
 *
 *        int[] r1 = s.topKFrequent(new int[]{1,1,1,2,2,3}, 2);
 *        Arrays.sort(r1);
 *        assert Arrays.equals(r1, new int[]{1,2}) : "case 1 failed";
 *
 *        int[] r2 = s.topKFrequent(new int[]{1}, 1);
 *        assert Arrays.equals(r2, new int[]{1}) : "case 2 failed";
 *
 *        int[] r3 = s.topKFrequent(new int[]{5,5,5,5}, 1);
 *        assert Arrays.equals(r3, new int[]{5}) : "case 3 failed";
 *
 *        int[] r4 = s.topKFrequent(new int[]{-1,-1,2}, 1);
 *        assert Arrays.equals(r4, new int[]{-1}) : "case 4 failed";
 *
 *        System.out.println("All assertions passed.");
 *    }
 *    // Run with: java -ea  (assertions enabled)
 *
 * | Approach   | Risk                                                     | Mitigation                                  |
 * |------------|----------------------------------------------------------|---------------------------------------------|
 * | Sort       | Comparator overflow with huge counts                     | Use Integer.compare instead of subtraction  |
 * | Min-Heap   | Accidentally max-heap over all elements (loses O(log k)) | Cap heap at k and evict the smallest        |
 * | Bucket Sort| Off-by-one bucket sizing; indexing by value              | Size n+1; index strictly by frequency       |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #347 - Top K Frequent Elements - Difficulty: Medium.
 * One of the most frequently asked heap / bucket-sort problems
 * (thousands of reported interview appearances).
 *
 * | Company          | Frequency (stars) | Notes                                             |
 * |------------------|-------------------|---------------------------------------------------|
 * | Amazon           | *****             | Extremely common; often paired with complexity Q. |
 * | Google           | *****             | Expect the O(n) bucket/quickselect follow-up.     |
 * | Facebook (Meta)  | *****             | Frequent phone-screen staple.                     |
 * | Microsoft        | ****              | Asked in both heap and bucket variants.           |
 * | Apple            | ****              | Common in data-structure rounds.                  |
 * | Bloomberg        | ****              | Heap version favored.                             |
 * | Uber             | ***               | Appears in onsite loops.                          |
 * | Adobe            | ***               | Medium-round rotation.                            |
 * | Oracle           | ***               | Occasionally with streaming twist.                |
 * | Spotify          | **                | Real-world "top tracks" framing.                  |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 * | Approach                    | Time       | Space | Code Complexity      | Recommended?              |
 * |-----------------------------|------------|-------|----------------------|---------------------------|
 * | Frequency Map + Sort        | O(n log n) | O(n)  | Lowest - a few lines | OK when brevity matters   |
 * | Frequency Map + Min-Heap    | O(n log k) | O(n)  | Moderate             | Great when k << distinct  |
 * | Frequency Map + Bucket Sort | O(n)       | O(n)  | Moderate             | [BEST] best for time      |
 *
 * Since all three use O(n) space, there is no separate space winner - the
 * decision is purely about time and readability.
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use bucket sort for the optimal O(n) runtime. If you prefer idiomatic,
 * easy-to-explain code and k is small, the min-heap (O(n log k)) is an
 * excellent interview-friendly choice. Fall back to plain sort only when you
 * value a two-line solution over speed.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * The pattern is always COUNT first, then SELECT the top k - the interesting
 * part is only how you select. Memorize the two tricks: to keep the k LARGEST
 * use a MIN-heap of size k and evict the smallest; and because frequencies
 * are integers bounded by n, you can BUCKET by frequency to sort in linear
 * time without any comparisons.
 */
// @formatter:on
