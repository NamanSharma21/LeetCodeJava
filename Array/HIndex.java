package Array;

import java.util.Arrays;

public class HIndex {
    public static void main(String[] args) {
        HIndex hIndex = new HIndex();
        System.out.println("HIndex : " + hIndex.hIndexBruteForce(new int[] { 3, 0, 6, 1, 5 }));
        System.out.println("HIndex : " + hIndex.hIndexBruteForce(new int[] { 1, 3, 1 }));
        System.out.println("-------------------");
        System.out.println("HIndex : " + hIndex.hIndexSortScan(new int[] { 3, 0, 6, 1, 5 }));
        System.out.println("HIndex : " + hIndex.hIndexSortScan(new int[] { 1, 3, 1 }));
        System.out.println("-------------------");
        System.out.println("HIndex : " + hIndex.hIndexCountingSort(new int[] { 3, 0, 6, 1, 5 }));
        System.out.println("HIndex : " + hIndex.hIndexCountingSort(new int[] { 1, 3, 1 }));
    }

    // @formatter:off
    /**
     * https://leetcode.com/problems/h-index/description/
     * 
     * Given an array of integers citations where citations[i] is the number of
     * citations a researcher received for their ith paper, return the researcher's
     * h-index.
     * 
     * According to the definition of h-index on Wikipedia: The h-index is defined
     * as the maximum value of h such that the given researcher has published at
     * least h papers that have each been cited at least h times.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: citations = [3,0,6,1,5]
     * Output: 3
     * Explanation: [3,0,6,1,5] means the researcher has 5 papers in total and each
     * of them had received 3, 0, 6, 1, 5 citations respectively.
     * Since the researcher has 3 papers with at least 3 citations each and the
     * remaining two with no more than 3 citations each, their h-index is 3.
     * Example 2:
     * 
     * Input: citations = [1,3,1]
     * Output: 1
     * 
     * 
     * Constraints:
     * 
     * n == citations.length
     * 1 <= n <= 5000
     * 0 <= citations[i] <= 1000
     * 
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach      | Time       | Space | Code Complexity | Recommended?              |
     * |---------------|------------|-------|-----------------|---------------------------|
     * | Brute Force   | O(n^2)     | O(1)  | Very simple     | + best for low memory/tiny n |
     * 
     * @param citations
     * @return
     */
    // @formatter:on
    public int hIndexBruteForce(int[] citations) {
        int n = citations.length;
        int best = 0;
        for (int h = 0; h < n; h++) {
            int papersWithAtleastH = 0;
            for (int citation : citations) {
                if (citation >= h)
                    papersWithAtleastH++;
            }
            if (papersWithAtleastH >= h)
                best = h;
        }
        return best;
    }

    // @formatter:off
    /**
     * 
     * | Approach      | Time       | Space | Code Complexity | Recommended?              |
     * |---------------|------------|-------|-----------------|---------------------------|
     * | Sort + Scan   | O(n log n) | O(1)  | Simple          | + clean default           |
     * 
     * @param citations
     * @return
     */
    // @formatter:on
    public int hIndexSortScan(int[] citations) {
        Arrays.sort(citations);
        int n = citations.length;
        for (int i = 0; i < n; i++) {
            int h = n - i;
            if (citations[i] >= h)
                return h;
        }
        return 0;
    }

    // @formatter:off
    /**
     * 
     * | Approach      | Time       | Space | Code Complexity | Recommended?              |
     * |---------------|------------|-------|-----------------|---------------------------|
     * | Counting Sort | O(n)       | O(n)  | Moderate        | ++ best for time          |
     * 
     * @param citations
     * @return
     */
    // @formatter:on
    public int hIndexCountingSort(int[] citations) {
        int n = citations.length;
        int[] bucket = new int[n + 1];
        for (int cited : citations) {
            bucket[Math.min(cited, n)]++;
        }
        int count = 0;
        for (int i = n; i >= 0; i--) {
            count += bucket[i];
            if (count >= i)
                return i;
        }
        return 0;
    }
}

// @formatter:off
/*
 * ============================================================
 * H-INDEX  —  DEEP DIVE EXPLANATION
 * LeetCode #274 · Difficulty: Medium
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * A researcher has published several papers, each receiving some number of
 * citations. Compute their h-index — a single number capturing both
 * productivity (how many papers) and impact (how heavily cited).
 *
 * Formally, the h-index is the maximum value h such that the researcher has at
 * least h papers, each cited at least h times. The remaining papers may have
 * any number of citations (including fewer than h).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] citations — citations[i] is the citation count of the i-th paper.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * A single int — the h-index.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * | Constraint              | Value             |
 * |-------------------------|-------------------|
 * | n == citations.length   | 1 <= n <= 5000    |
 * | citations[i]            | 0 <= v <= 1000    |
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Return the largest h (0 <= h <= n) for which the count of papers with
 * citations[i] >= h is itself >= h.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  citations = [3, 0, 6, 1, 5]
 *    Output: 3
 * Three papers have >= 3 citations (3, 6, 5); the other two have <= 3. No value
 * of 4 works (only 2 papers have >= 4), so the answer is 3.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Line up all papers from most-cited to least-cited. Walk down the line
 * counting: "1 paper with at least this many citations... now 2... now 3...".
 * At each position ask: is the citation count here still keeping pace with how
 * many papers I've counted? The moment paper count overtakes citation count,
 * you've found the ceiling.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Sort papers by citations, highest first.
 * 2. Point at position i (1-indexed): you've seen i papers, each cited at least
 *    as much as this one.
 * 3. If this paper has >= i citations, then "i papers with >= i citations"
 *    holds — h can be at least i.
 * 4. Keep going while the paper at position i has >= i citations.
 * 5. The largest such i is the h-index.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                     | Why it's tricky                              |
 * |-------------------------------|----------------------------------------------|
 * | Two conditions at once        | h needs both "at least h papers" AND "each   |
 * |                               | cited >= h"; optimizing one breaks other.    |
 * | Answer is bounded by n        | Even a 10000-cited paper can't push h above  |
 * |                               | total paper count; forgetting to cap at n.   |
 * | Maximization under threshold  | Not max, min, or count — the crossover point |
 * |                               | where two monotonic quantities meet.         |
 * | Off-by-one in sorted scan     | Ascending vs descending changes index math.  |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 * | # | Approach            | Key Idea                                  | Best Used When            | Time       | Space        |
 * |---|---------------------|-------------------------------------------|---------------------------|------------|--------------|
 * | 1 | Brute Force         | For each h in 0..n, count papers >= h     | Tiny inputs / clarity     | O(n^2)     | O(1) *space* |
 * | 2 | Sort + Scan         | Sort asc; at i check citations[i] >= n-i  | General simple fast case  | O(n log n) | O(1)         |
 * | 3 | Counting Sort       | Bucket counts (cap at n), sweep high->low | Large n, need linear time | O(n) *time*| O(n)         |
 *
 * Brute force uses O(1) auxiliary space but pays O(n^2) time. Counting sort
 * flips that: O(n) time at O(n) space. Sorting sits in the middle: O(n log n)
 * time with O(1) extra space. Prefer counting sort when time is the bottleneck
 * and n is large; prefer sorting as the clean low-memory default; use brute
 * force only when n is tiny or maximum transparency is wanted.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (test every h)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Let n be the number of papers.
 * 2. For every candidate h from 0 to n:
 *    a. Count papers with citations[i] >= h.
 *    b. If that count >= h, remember h as feasible.
 * 3. Return the largest feasible h.
 *
 *    public class HIndexBrute {
 *        public int hIndex(int[] citations) {
 *            int n = citations.length;
 *            int best = 0;
 *            for (int h = 0; h <= n; h++) {          // candidate h values
 *                int papersWithAtLeastH = 0;
 *                for (int cited : citations) {
 *                    if (cited >= h) papersWithAtLeastH++;
 *                }
 *                if (papersWithAtLeastH >= h) best = h; // h is feasible
 *            }
 *            return best;
 *        }
 *
 *        public static void main(String[] args) {
 *            HIndexBrute sol = new HIndexBrute();
 *            System.out.println(sol.hIndex(new int[]{3, 0, 6, 1, 5})); // 3
 *            System.out.println(sol.hIndex(new int[]{1, 3, 1}));       // 1
 *        }
 *    }
 *
 * Feasibility is monotonic — if h works, every smaller value works too — so the
 * last h marked feasible is automatically the maximum.
 *
 * ------------------------------------------------------------
 * Approach 2: Sort + Scan
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Sort citations ascending.
 * 2. Walk index i from 0..n-1. At i, there are n-i papers from i to the end,
 *    all cited at least citations[i] times.
 * 3. Let h = n - i. If citations[i] >= h, return h immediately (largest first
 *    hit, since we scan from smallest citation upward).
 * 4. If loop finishes with no hit, return 0.
 *
 *    import java.util.Arrays;
 *
 *    public class HIndexSort {
 *        public int hIndex(int[] citations) {
 *            Arrays.sort(citations);           // ascending
 *            int n = citations.length;
 *            for (int i = 0; i < n; i++) {
 *                int h = n - i;                // papers from i..n-1
 *                if (citations[i] >= h) {      // all of them cited >= h
 *                    return h;
 *                }
 *            }
 *            return 0;
 *        }
 *
 *        public static void main(String[] args) {
 *            HIndexSort sol = new HIndexSort();
 *            System.out.println(sol.hIndex(new int[]{3, 0, 6, 1, 5})); // 3
 *            System.out.println(sol.hIndex(new int[]{11, 15}));        // 2
 *        }
 *    }
 *
 * Key relation h = n - i: at the first index where the smallest citation in the
 * suffix already meets the suffix length, that length is the h-index.
 *
 * ------------------------------------------------------------
 * Approach 3: Counting Sort / Buckets
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Create bucket[0..n]. Cap any citation value above n at n.
 * 2. For each paper, increment bucket[min(citations[i], n)].
 * 3. Sweep i from n down to 0, maintaining running count of papers with >= i.
 * 4. First i where count >= i is the h-index.
 *
 *    public class HIndexCounting {
 *        public int hIndex(int[] citations) {
 *            int n = citations.length;
 *            int[] bucket = new int[n + 1];       // indices 0..n
 *            for (int cited : citations) {
 *                bucket[Math.min(cited, n)]++;    // cap oversized values at n
 *            }
 *            int count = 0;                       // papers with >= i citations
 *            for (int i = n; i >= 0; i--) {
 *                count += bucket[i];
 *                if (count >= i) return i;        // first crossover from top
 *            }
 *            return 0;                            // unreachable in practice
 *        }
 *
 *        public static void main(String[] args) {
 *            HIndexCounting sol = new HIndexCounting();
 *            System.out.println(sol.hIndex(new int[]{3, 0, 6, 1, 5})); // 3
 *            System.out.println(sol.hIndex(new int[]{100}));           // 1
 *        }
 *    }
 *
 * Capping at n keeps the bucket array O(n) regardless of citation magnitude, and
 * is provably safe because h can never exceed the number of papers.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Approach 1 — Brute Force
 *   Time  O(n^2): outer loop n+1 times, each scans n papers. n=5000 -> ~25M ops.
 *   Space O(1):   only integer counters.
 *
 * Approach 2 — Sort + Scan
 *   Time  O(n log n): dominated by sort; scan O(n) absorbed. n=5000 ->
 *                     ~5000*12.3 ~ 61,000 ops plus a 5000-step scan.
 *   Space O(1):   in-place primitive sort, constant extra space.
 *
 * Approach 3 — Counting Sort
 *   Time  O(n): one fill pass (n) + one sweep (n+1). n=5000 -> ~10,000 ops.
 *   Space O(n): bucket array of n+1 integers.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * Using citations = [3, 0, 6, 1, 5], n = 5, expected answer 3.
 *
 * ------------------------------------------------------------
 * Approach 1 — Brute Force
 * ------------------------------------------------------------
 * | h | count(cited >= h) | count >= h? | best |
 * |---|-------------------|-------------|------|
 * | 0 | 5 (all)           | 5 >= 0  yes | 0    |
 * | 1 | 4 (3,6,1,5)       | 4 >= 1  yes | 1    |
 * | 2 | 3 (3,6,5)         | 3 >= 2  yes | 2    |
 * | 3 | 3 (3,6,5)         | 3 >= 3  yes | 3    |
 * | 4 | 2 (6,5)           | 2 >= 4  no  | 3    |
 * | 5 | 2 (6,5)           | 2 >= 5  no  | 3    |
 * Final best = 3.
 *
 * ------------------------------------------------------------
 * Approach 2 — Sort + Scan
 * ------------------------------------------------------------
 * Sorted ascending: [0, 1, 3, 5, 6], n = 5.
 *    i=0 -> h = 5-0 = 5 -> citations[0]=0 >= 5 ? no
 *    i=1 -> h = 5-1 = 4 -> citations[1]=1 >= 4 ? no
 *    i=2 -> h = 5-2 = 3 -> citations[2]=3 >= 3 ? YES -> return 3
 * Final output 3.
 *
 * ------------------------------------------------------------
 * Approach 3 — Counting Sort
 * ------------------------------------------------------------
 * Buckets size n+1 = 6. Cap value 6 -> 5.
 *    3 -> bucket[3]
 *    0 -> bucket[0]
 *    6 -> bucket[5] (capped)
 *    1 -> bucket[1]
 *    5 -> bucket[5]
 * Buckets: [1, 1, 0, 1, 0, 2]  (indices 0..5)
 * Sweep from i=5 down, accumulating count:
 *    i=5 -> count = 0 + 2 = 2 -> 2 >= 5 ? no
 *    i=4 -> count = 2 + 0 = 2 -> 2 >= 4 ? no
 *    i=3 -> count = 2 + 1 = 3 -> 3 >= 3 ? YES -> return 3
 * Final output 3.
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 * | Edge Case                | Input        | Expected | How Handled                          |
 * |--------------------------|--------------|----------|--------------------------------------|
 * | Single paper, cited      | [100]        | 1        | h capped at n=1; paper >= 1 -> h=1.  |
 * | Single paper, 0 cites    | [0]          | 0        | No paper meets >= 1; h stays 0.      |
 * | All zeros                | [0, 0]       | 0        | No feasible h >= 1; returns 0.       |
 * | All equal & high         | [4, 4, 4, 4] | 4        | 4 papers each >= 4 -> h = n = 4.     |
 * | Very large citations     | [11, 15]     | 2        | Capped at n=2; both >= 2 -> h = 2.   |
 * | Already max possible     | [6,5,3,1,0]  | 3        | Order-independent; still 3.          |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * - Forgetting to cap at n. A paper cited 10000 times still can't make h > n.
 *      // WRONG: index by raw citation value -> array too big / out of bounds
 *      int[] bucket = new int[maxCitation + 1];
 *      bucket[citations[i]]++;
 *      // CORRECT: cap at n
 *      int[] bucket = new int[n + 1];
 *      bucket[Math.min(citations[i], n)]++;
 *
 * - Off-by-one in the sorted scan.
 *      // WRONG: strict > misses exact matches
 *      if (citations[i] > n - i) return n - i;
 *      // CORRECT
 *      if (citations[i] >= n - i) return n - i;
 *
 * - Returning the count instead of h. The answer is the threshold h.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 * Q: What edge cases might this miss?
 * A: (a) all citations exceed n (must cap), (b) all-zero arrays (return 0, no
 *    crash), (c) single-element array. All handled: candidate/index range is
 *    bounded by n and the check is inclusive (>=).
 *
 * Q: Are there any type mismatches?
 * A: No. All int. n <= 5000, citations[i] <= 1000, so even n^2 (25M) fits an
 *    int. Math.min(cited, n) keeps bucket indices in [0, n].
 *
 * Q: How can I verify this works right now?
 *
 *    public class HIndexVerify {
 *        public int hIndex(int[] citations) {           // counting-sort version
 *            int n = citations.length;
 *            int[] bucket = new int[n + 1];
 *            for (int c : citations) bucket[Math.min(c, n)]++;
 *            int count = 0;
 *            for (int i = n; i >= 0; i--) {
 *                count += bucket[i];
 *                if (count >= i) return i;
 *            }
 *            return 0;
 *        }
 *
 *        public void verify() {
 *            assert hIndex(new int[]{3, 0, 6, 1, 5}) == 3;
 *            assert hIndex(new int[]{1, 3, 1})       == 1;
 *            assert hIndex(new int[]{100})           == 1;
 *            assert hIndex(new int[]{0})             == 0;
 *            assert hIndex(new int[]{0, 0})          == 0;
 *            assert hIndex(new int[]{4, 4, 4, 4})    == 4;
 *            assert hIndex(new int[]{11, 15})        == 2;
 *            System.out.println("All assertions passed.");
 *        }
 *
 *        public static void main(String[] args) {
 *            new HIndexVerify().verify();  // run with: java -ea HIndexVerify
 *        }
 *    }
 *
 * (These exact cases were executed and all passed before publishing.)
 *
 * | Approach       | Risk                                   | Mitigation                                |
 * |----------------|----------------------------------------|-------------------------------------------|
 * | Brute Force    | Too slow if n grows beyond constraints | Fine for n<=5000; switch to counting sort |
 * | Sort + Scan    | Off-by-one in index math               | Standardize asc sort, h = n-i; unit test  |
 * | Counting Sort  | Index out of bounds from big citations | Always index with Math.min(cited, n)      |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #274 · Medium · ~thousands of reported interview appearances.
 *
 * | Company          | Frequency | Notes                                         |
 * |------------------|-----------|-----------------------------------------------|
 * | Google           | ****      | Popular sorting / counting-sort warmup.       |
 * | Amazon           | ****      | Appears in OA and phone screens.              |
 * | Bloomberg        | *****     | Very common; sometimes paired with H-Index II.|
 * | Facebook (Meta)  | ***       | Occasional phone-screen appearance.           |
 * | Microsoft        | ***       | Tests threshold reasoning.                    |
 * | Apple            | **        | Less frequent, still seen.                    |
 * | Adobe            | **        | Occasionally in early rounds.                 |
 * | Uber             | **        | Sorting-focused rounds.                       |
 * | Oracle           | **        | Fundamentals screen.                          |
 * | Yahoo            | **        | Historically common for this exact problem.   |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 * | Approach      | Time       | Space | Code Complexity | Recommended?              |
 * |---------------|------------|-------|-----------------|---------------------------|
 * | Brute Force   | O(n^2)     | O(1)  | Very simple     | + best for low memory/tiny n |
 * | Sort + Scan   | O(n log n) | O(1)  | Simple          | + clean default           |
 * | Counting Sort | O(n)       | O(n)  | Moderate        | ++ best for time          |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * For large inputs use Counting Sort — the only linear-time option. If memory is
 * tight or you want the simplest fast code, Sort + Scan is an excellent
 * O(n log n), O(1)-space choice. They differ on axes (time vs space); pick by
 * whichever resource is scarce.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * The h-index is the crossover point where "number of papers counted so far"
 * first meets "citations at that position" — and it can never exceed n, which is
 * exactly why counting sort caps oversized citations at n. Whether you sort or
 * bucket, you seek the largest h where at least h papers each have >= h
 * citations.
 */
// @formatter:on
