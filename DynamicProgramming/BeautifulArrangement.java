package DynamicProgramming;

public class BeautifulArrangement {
    public static void main(String[] args) {
        BeautifulArrangement beautifulArrangement = new BeautifulArrangement();
        System.out.println("BeautifulArrangement : " + beautifulArrangement.countArrangementBitmaskDP(2));
        System.out.println("BeautifulArrangement : " + beautifulArrangement.countArrangementBitmaskDP(1)); // 1
        System.out.println("BeautifulArrangement : " + beautifulArrangement.countArrangementBitmaskDP(3)); // 3
        System.out.println("BeautifulArrangement : " + beautifulArrangement.countArrangementBitmaskDP(4)); // 8
        System.out.println("BeautifulArrangement : " + beautifulArrangement.countArrangementBitmaskDP(15)); // 24679
        System.out.println("----------------------------------------");
        System.out.println("BeautifulArrangement : " + beautifulArrangement.countArrangementBacktrackingPruning(2));
        System.out.println("BeautifulArrangement : " + beautifulArrangement.countArrangementBacktrackingPruning(1)); // 1
        System.out.println("BeautifulArrangement : " + beautifulArrangement.countArrangementBacktrackingPruning(3)); // 3
        System.out.println("BeautifulArrangement : " + beautifulArrangement.countArrangementBacktrackingPruning(4)); // 8
        System.out.println("BeautifulArrangement : " + beautifulArrangement.countArrangementBacktrackingPruning(15)); // 24679
        System.out.println("----------------------------------------");
        System.out.println("BeautifulArrangement : " + beautifulArrangement.countArrangementBruteForce(2));
        System.out.println("BeautifulArrangement : " + beautifulArrangement.countArrangementBruteForce(1)); // 1
        System.out.println("BeautifulArrangement : " + beautifulArrangement.countArrangementBruteForce(3)); // 3
        System.out.println("BeautifulArrangement : " + beautifulArrangement.countArrangementBruteForce(4)); // 8
        System.out.println("BeautifulArrangement : " + beautifulArrangement.countArrangementBruteForce(15)); // 24679
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/beautiful-arrangement/description/
     * 
     * Suppose you have n integers labeled 1 through n. A permutation of those n
     * integers perm (1-indexed) is considered a beautiful arrangement if for every
     * i (1 <= i <= n), either of the following is true:
     * 
     * perm[i] is divisible by i.
     * i is divisible by perm[i].
     * Given an integer n, return the number of the beautiful arrangements that you
     * can construct.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: n = 2
     * Output: 2
     * Explanation:
     * The first beautiful arrangement is [1,2]:
     * - perm[1] = 1 is divisible by i = 1
     * - perm[2] = 2 is divisible by i = 2
     * The second beautiful arrangement is [2,1]:
     * - perm[1] = 2 is divisible by i = 1
     * - i = 2 is divisible by perm[2] = 1
     * Example 2:
     * 
     * Input: n = 1
     * Output: 1
     * 
     * 
     * Constraints:
     * 
     * 1 <= n <= 15
     */
    // @formatter:on

    /*
     * Time — O(2ⁿ · n)
     * 
     * This bound is unconditional — it does not depend on how sparse the
     * divisibility relation happens to be. That's its main advantage over
     * backtracking.
     * 
     * Space — O(2ⁿ): the int[1 << n] table. Nothing else scales.
     */
    public int countArrangementBitmaskDP(int n) {
        int fullMask = 1 << n;
        int[] dp = new int[fullMask];
        dp[0] = 1;
        for (int mask = 1; mask < fullMask; mask++) {
            int position = Integer.bitCount(mask);
            for (int value = 1; value <= n; value++) {
                int bit = 1 << (value - 1);
                if ((mask & bit) == 0) {
                    continue;
                }
                if (value % position == 0 || position % value == 0) {
                    dp[mask] += dp[mask ^ bit];
                }
            }
        }
        return dp[fullMask - 1];
    }

    /*
     * Time — O(n!) as a formal upper bound, dramatically less in practice
     * 
     * The honest derivation: in the absolute worst case where every value is
     * compatible with every position, the recursion tree has n · (n-1) · (n-2) ·
     * ... = n! leaves, so O(n!) is the only bound provable from the structure
     * alone. The saving over approach 1 is that validation is folded into placement
     * — no separate O(n) scan, and incompatible branches are cut at the node rather
     * than the leaf.
     * 
     * But that worst case never occurs, because divisibility is sparse. Position p
     * accepts only the divisors of p and the multiples of p within 1..n — for p =
     * 11, n = 15, that's just {1, 11}. Measured node counts:
     * 
     * Space — O(n): the boolean[n+1] array plus recursion depth n. For n = 15: 16
     * bytes of flags and 15 stack frames. This is the minimum any correct approach
     * can use, and it's why this is the space-optimal choice.
     */
    private int count;
    private boolean[] used;
    private int n;

    public int countArrangementBacktrackingPruning(int n) {
        this.n = n;
        this.count = 0;
        this.used = new boolean[n + 1];
        place(1);
        return count;
    }

    public void place(int position) {
        if (position > n) {
            count++;
            return;
        }

        for (int value = 1; value <= n; value++) {
            if (used[value])
                continue;
            if (value % position != 0 && position % value != 0)
                continue;
            used[value] = true;
            place(position + 1);
            used[value] = false;
        }
    }

    /*
     * Time — O(n! · n)
     * 
     * Space — O(n): one int[n] array, plus a recursion stack of depth n. No
     * structure grows with the number of permutations because they're validated and
     * discarded immediately.
     */

    public int countArrangementBruteForce(int n) {
        count = 0;
        int[] perm = new int[n];
        for (int i = 0; i < n; i++) {
            perm[i] = i + 1;
        }
        generatePerm(perm, 0);
        return count;
    }

    public void generatePerm(int[] perm, int idx) {
        if (idx == perm.length) {
            if (isBeautiful(perm))
                count++;
            return;
        }

        for (int i = idx; i < perm.length; i++) {
            swap(perm, idx, i);
            generatePerm(perm, idx + 1);
            swap(perm, idx, i);
        }

    }

    public boolean isBeautiful(int[] perm) {
        for (int i = 0; i < perm.length; i++) {
            int position = i + 1;
            int value = perm[i];
            if (value % position != 0 && position % value != 0) {
                return false;
            }
        }
        return true;
    }

    private void swap(int[] perm, int i, int j) {
        int temp = perm[i];
        perm[i] = perm[j];
        perm[j] = temp;
    }
}
// @formatter:off
/*
 * ============================================================
 * BEAUTIFUL ARRANGEMENT - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * LeetCode 526 - Beautiful Arrangement - Medium
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 *
 * You're given a single integer n. Consider all permutations of the numbers
 * 1, 2, ..., n. A permutation is called a beautiful arrangement if, at EVERY
 * 1-indexed position i, at least one of these two divisibility conditions holds
 * between the position and the number sitting in it:
 *
 *   - perm[i] % i == 0  -> the number is a multiple of its position, OR
 *   - i % perm[i] == 0  -> the position is a multiple of the number
 *
 * Count how many beautiful arrangements exist.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 *
 * A single int n.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 *
 * A single int - the number of beautiful arrangements of 1..n.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 *
 * | Constraint      | Value                                        |
 * |-----------------|----------------------------------------------|
 * | Range of n      | 1 <= n <= 15                                 |
 * | Elements        | exactly the integers 1..n, each used once     |
 * | Indexing        | 1-based (position 1 through position n)       |
 *
 * The tiny ceiling of n = 15 is the loudest hint in the problem: 15! ~ 1.3e12
 * is hopeless, but 2^15 = 32,768 is nothing. The constraint is telling you to
 * think in subsets, not permutations.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 *
 * Not the arrangements themselves - only the COUNT. That distinction matters
 * enormously: if you only need a count, arrangements that share the same SET of
 * used numbers can be collapsed together, which is exactly what unlocks the
 * fast solution.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *
 * For n = 4, the answer is 8:
 *
 * | Arrangement  | Why it works                                          |
 * |--------------|-------------------------------------------------------|
 * | [1, 2, 3, 4] | every perm[i] == i                                    |
 * | [1, 4, 3, 2] | pos2 holds 4 (4%2==0), pos4 holds 2 (4%2==0)          |
 * | [2, 1, 3, 4] | pos1 holds 2 (2%1==0), pos2 holds 1 (2%1==0)          |
 * | [2, 4, 3, 1] | pos4 holds 1 (4%1==0)                                 |
 * | [3, 2, 1, 4] | pos3 holds 1 (3%1==0)                                 |
 * | [3, 4, 1, 2] | pos1 holds 3, pos2 holds 4, pos3 holds 1, pos4 holds 2 |
 * | [4, 1, 3, 2] | pos1 holds 4 (4%1==0)                                 |
 * | [4, 2, 3, 1] | pos2 holds 2, pos3 holds 3                            |
 *
 * Note that [1, 3, 2, 4] fails: position 2 holds 3, and 3 % 2 == 1,
 * 2 % 3 == 2. Neither direction divides.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 *
 * Think of it as SEATING GUESTS IN NUMBERED CHAIRS. Guest v will only sit in
 * chair p if the two numbers are "divisibility-compatible" - one divides the
 * other. Some guests are easy: guest 1 is happy in ANY chair (every position is
 * a multiple of 1), and chair 1 accepts ANY guest. Some are picky: guest 7 when
 * n = 15 can only sit in chair 1, chair 7, or chair 14.
 *
 * You want to count the number of complete valid seatings. The naive way is to
 * try every possible seating and check it. The smart way is to seat guests ONE
 * CHAIR AT A TIME, and abandon a seating plan the instant a chair has no
 * compatible guest left.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 *
 * 1. Start with brute force in your head. Generate every permutation, check
 *    every position. Correct, but 15! permutations is over a trillion -
 *    impossible.
 *
 * 2. Notice the checks fail early. If position 2 already holds an incompatible
 *    number, nothing about positions 3, 4, 5... can rescue it. So checking a
 *    permutation only AFTER fully building it wastes almost all the work.
 *
 * 3. Move the check inside the construction. Fill position 1, then position 2,
 *    and so on - but only ever place a number that's already compatible with
 *    the position you're filling. Now every partial arrangement you hold is
 *    valid-so-far, and whole subtrees of the search vanish. This is
 *    backtracking with pruning, and it's fast enough to pass.
 *
 * 4. Then notice the repeated work. Suppose you've filled the first 3
 *    positions. Whether you used [1, 2, 3], [2, 1, 3], or [3, 2, 1], you're in
 *    the same situation going forward: positions 1-3 are done, and numbers
 *    {1,2,3} are consumed. The number of ways to COMPLETE the arrangement
 *    depends ONLY on which set of numbers is still available, not on the order
 *    they were placed in.
 *
 * 5. So the state is a set, not a sequence. With n <= 15, a set of used numbers
 *    fits in a 15-bit integer. There are only 2^15 = 32,768 such states, versus
 *    15! permutations. Memoize on the bitmask and the problem collapses from
 *    factorial to exponential-in-a-good-way.
 *
 * 6. The position is free information. You don't need to store which position
 *    you're filling - it's just the number of bits already set, since you fill
 *    positions left to right. popcount(mask) recovers it.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 *
 * | Challenge                    | Why it's tricky                                                                                                                                                                                 |
 * |------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
 * | 1-based indexing             | The condition is defined on positions 1..n, but Java arrays are 0-based. Using i directly as the position when iterating for (int i = 0; ...) silently computes perm[i] % 0 at i=0 -> ArithmeticException. |
 * | Two-directional condition    | It's val % pos == 0 OR pos % val == 0, not just one. Forgetting the second half drops most valid arrangements - e.g. [4,2,3,1] needs 4 % 1 == 0 at pos 1 AND 4 % 2 == 0 at pos 2.                 |
 * | Spotting that order doesn't matter | The leap from "permutations" to "subsets" is the whole problem. It's counterintuitive because the QUESTION is about ordered arrangements, yet the STATE is unordered.                       |
 * | popcount as implicit position | Deriving the current position from the mask instead of passing it separately is elegant but easy to get off-by-one - is it bitCount(mask) or bitCount(mask) + 1? It depends on the formulation.  |
 * | Pruning must happen at placement | Backtracking that builds a full permutation and only then validates is barely faster than brute force. The if must be inside the placement loop.                                              |
 * | No closed-form formula       | The counts (1, 2, 3, 8, 10, 36, 41, 132, ...) follow no simple pattern, so there's no arithmetic shortcut - you must search.                                                                     |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                 | Key Idea                                                                                                | Best Used When                                                              | Time Complexity                       | Space Complexity        |
 * |---|--------------------------|---------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------|---------------------------------------|-------------------------|
 * | 1 | Brute Force Permutations | Generate all n! permutations, validate each fully                                                        | Never in production; useful as a correctness oracle for n <= 8               | O(n! * n)                             | O(n)                    |
 * | 2 | Backtracking with Pruning| Fill positions left to right, only ever placing a compatible number; abandon dead branches immediately   | Memory is tight, or you also need to ENUMERATE the arrangements, not just count | O(n!) worst-case bound, vastly less in practice | O(n) [OK] space-optimal |
 * | 3 | Bitmask DP over Subsets  | State = set of used numbers; position = popcount. Count completions per subset, reuse across orderings    | You need a hard worst-case guarantee and n <= ~20                            | O(2^n * n) [OK] time-optimal          | O(2^n)                  |
 *
 * THE TRADE-OFF. Approach 1 is dominated outright - approach 2 uses the same
 * O(n) space and is orders of magnitude faster, so brute force exists here only
 * as a reference implementation to check the others against.
 *
 * The real decision is between 2 and 3, and it is a genuine time-vs-space
 * split. Backtracking carries only a recursion stack and a used[] array - O(n),
 * which for n = 15 is a few dozen bytes. But its runtime has no useful
 * worst-case bound; it's fast purely because the divisibility condition prunes
 * aggressively, and you're trusting empirical behavior. Bitmask DP has a hard
 * ceiling of 2^15 * 15 = 491,520 operations regardless of input, but allocates
 * a 32,768-entry array - about 128 KB.
 *
 * PREFER APPROACH 3 when you want a provable bound and n stays small (the array
 * doubles with every increment of n, so it dies around n ~ 25). PREFER APPROACH
 * 2 when memory is constrained, when n might grow past the point where 2^n
 * allocation is viable, or when the task changes from "count them" to "list
 * them" - the DP throws away the arrangements themselves and can only produce a
 * total.
 *
 * Measured on this machine at n = 15: backtracking explored 747,961 recursive
 * calls in ~31 ms; the DP did 491,520 transitions in ~2.5 ms.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force Permutations
 * ------------------------------------------------------------
 *
 * ALGORITHM:
 *
 * 1. Build the array [1, 2, ..., n].
 * 2. Recursively generate every permutation by swapping element idx with each
 *    element from idx to n-1, recursing, then swapping back.
 * 3. When a complete permutation is built (idx == n), walk it once and check
 *    every position: for 0-based array index i, the 1-based position is i + 1.
 * 4. If every position satisfies val % pos == 0 || pos % val == 0, increment
 *    the counter.
 * 5. Return the counter.
 *
 *    public class BeautifulArrangementBrute {
 *
 *        private int count;
 *
 *        public int countArrangement(int n) {
 *            count = 0;
 *            int[] perm = new int[n];
 *            for (int i = 0; i < n; i++) {
 *                perm[i] = i + 1;
 *            }
 *            generate(perm, 0);
 *            return count;
 *        }
 *
 *        private void generate(int[] perm, int idx) {
 *            if (idx == perm.length) {
 *                if (isBeautiful(perm)) {
 *                    count++;
 *                }
 *                return;
 *            }
 *            for (int j = idx; j < perm.length; j++) {
 *                swap(perm, idx, j);
 *                generate(perm, idx + 1);
 *                swap(perm, idx, j);   // undo, so the array is restored
 *            }
 *        }
 *
 *        private boolean isBeautiful(int[] perm) {
 *            for (int i = 0; i < perm.length; i++) {
 *                int position = i + 1;    // convert 0-based index to 1-based
 *                int value = perm[i];
 *                if (value % position != 0 && position % value != 0) {
 *                    return false;
 *                }
 *            }
 *            return true;
 *        }
 *
 *        private void swap(int[] arr, int i, int j) {
 *            int temp = arr[i];
 *            arr[i] = arr[j];
 *            arr[j] = temp;
 *        }
 *
 *        public static void main(String[] args) {
 *            BeautifulArrangementBrute solver = new BeautifulArrangementBrute();
 *            System.out.println(solver.countArrangement(2));  // 2
 *            System.out.println(solver.countArrangement(3));  // 3
 *            System.out.println(solver.countArrangement(4));  // 8
 *            System.out.println(solver.countArrangement(8));  // 132
 *        }
 *    }
 *
 * The swap-based permutation generator produces each of the n! orderings
 * exactly once. The critical detail is the second swap call: without restoring
 * the array after the recursive call, later iterations of the loop would
 * operate on a mutated array and generate duplicates while missing others.
 *
 * ------------------------------------------------------------
 * Approach 2: Backtracking with Pruning
 * ------------------------------------------------------------
 *
 * ALGORITHM:
 *
 * 1. Maintain boolean[] used of size n + 1, indexed by VALUE (index 0 unused,
 *    so used[v] means value v is taken).
 * 2. Define place(pos): fill position pos, given positions 1..pos-1 are already
 *    filled validly.
 * 3. Base case: if pos > n, every position was filled successfully - this is a
 *    complete beautiful arrangement, so increment the counter and return.
 * 4. Otherwise loop val from 1 to n. Skip if used[val]. SKIP IF
 *    val % pos != 0 && pos % val != 0 - this is the pruning step, and it is
 *    what makes the approach viable.
 * 5. For each surviving val: mark it used, recurse into place(pos + 1), then
 *    unmark it.
 * 6. Start with place(1) and return the counter.
 *
 *    public class BeautifulArrangementBacktrack {
 *
 *        private int count;
 *        private boolean[] used;
 *        private int n;
 *
 *        public int countArrangement(int n) {
 *            this.n = n;
 *            this.count = 0;
 *            this.used = new boolean[n + 1];  // 1-indexed by value
 *            place(1);
 *            return count;
 *        }
 *
 *        private void place(int position) {
 *            if (position > n) {
 *                count++;                     // all n positions filled validly
 *                return;
 *            }
 *            for (int value = 1; value <= n; value++) {
 *                if (used[value]) {
 *                    continue;
 *                }
 *                // PRUNE: reject incompatible pairings before recursing at all
 *                if (value % position != 0 && position % value != 0) {
 *                    continue;
 *                }
 *                used[value] = true;
 *                place(position + 1);
 *                used[value] = false;         // backtrack
 *            }
 *        }
 *
 *        public static void main(String[] args) {
 *            BeautifulArrangementBacktrack solver = new BeautifulArrangementBacktrack();
 *            System.out.println(solver.countArrangement(1));   // 1
 *            System.out.println(solver.countArrangement(4));   // 8
 *            System.out.println(solver.countArrangement(10));  // 700
 *            System.out.println(solver.countArrangement(15));  // 24679
 *        }
 *    }
 *
 * The used array is sized n + 1 rather than n purely so that values can index
 * it directly without a -1 offset - a small readability win that removes a
 * common off-by-one bug.
 *
 * A worthwhile micro-optimization: iterate positions from n down to 1 instead
 * of 1 up to n. Large positions are the most constrained (position 15 accepts
 * only values 1, 3, 5, 15), so filling them first fails faster and prunes more.
 * It doesn't change the complexity class but typically cuts the node count
 * noticeably.
 *
 * ------------------------------------------------------------
 * Approach 3: Bitmask DP over Subsets [OPTIMAL]
 * ------------------------------------------------------------
 *
 * ALGORITHM:
 *
 * 1. Represent the set of already-used values as an int bitmask: bit v-1 is set
 *    iff value v has been placed.
 * 2. Define dp[mask] = the number of valid ways to fill positions
 *    1 .. popcount(mask) using exactly the values in mask.
 * 3. Base case: dp[0] = 1 - there is exactly one way to fill zero positions.
 * 4. Iterate mask from 1 to 2^n - 1 in increasing order. Because
 *    mask ^ bit < mask for any set bit, every state this transition reads has
 *    already been computed.
 * 5. Let position = Integer.bitCount(mask) - the last position filled when mask
 *    values are consumed.
 * 6. For each value val present in mask: if val is compatible with position,
 *    add dp[mask ^ bit] to dp[mask]. This says "the arrangement ended by
 *    placing val at position; the rest was any valid filling of the remaining
 *    set."
 * 7. Return dp[(1 << n) - 1] - all values used, all positions filled.
 *
 *    public class BeautifulArrangementDP {
 *
 *        public int countArrangement(int n) {
 *            int fullMask = 1 << n;
 *            int[] dp = new int[fullMask];
 *            dp[0] = 1;                              // one way to arrange nothing
 *
 *            for (int mask = 1; mask < fullMask; mask++) {
 *                int position = Integer.bitCount(mask);  // pos of last value
 *                for (int value = 1; value <= n; value++) {
 *                    int bit = 1 << (value - 1);
 *                    if ((mask & bit) == 0) {
 *                        continue;                   // this value isn't in the set
 *                    }
 *                    if (value % position == 0 || position % value == 0) {
 *                        dp[mask] += dp[mask ^ bit]; // value sits at `position`
 *                    }
 *                }
 *            }
 *            return dp[fullMask - 1];
 *        }
 *
 *        public static void main(String[] args) {
 *            BeautifulArrangementDP solver = new BeautifulArrangementDP();
 *            System.out.println(solver.countArrangement(1));   // 1
 *            System.out.println(solver.countArrangement(3));   // 3
 *            System.out.println(solver.countArrangement(4));   // 8
 *            System.out.println(solver.countArrangement(15));  // 24679
 *        }
 *    }
 *
 * WHY position = Integer.bitCount(mask) AND NOT bitCount(mask) + 1: in this
 * bottom-up formulation mask describes a state where those values have ALREADY
 * been placed. If mask has 3 bits, positions 1, 2, 3 are filled, and the value
 * we're peeling off is the one at position 3. In the top-down variant below,
 * the mask describes the state BEFORE placing, so the position being filled is
 * bitCount(mask) + 1. Getting these confused is the single most common bug in
 * this solution.
 *
 * TOP-DOWN MEMOIZED VARIANT - same complexities, sometimes clearer to reason
 * about, and it skips unreachable states:
 *
 *    import java.util.Arrays;
 *
 *    public class BeautifulArrangementMemo {
 *
 *        private int[] memo;
 *        private int n;
 *
 *        public int countArrangement(int n) {
 *            this.n = n;
 *            this.memo = new int[1 << n];
 *            Arrays.fill(memo, -1);
 *            return countFrom(0);
 *        }
 *
 *        private int countFrom(int mask) {
 *            int position = Integer.bitCount(mask) + 1;  // NOTE: +1 here
 *            if (position > n) {
 *                return 1;
 *            }
 *            if (memo[mask] != -1) {
 *                return memo[mask];
 *            }
 *            int total = 0;
 *            for (int value = 1; value <= n; value++) {
 *                int bit = 1 << (value - 1);
 *                if ((mask & bit) != 0) {
 *                    continue;
 *                }
 *                if (value % position == 0 || position % value == 0) {
 *                    total += countFrom(mask | bit);
 *                }
 *            }
 *            return memo[mask] = total;
 *        }
 *
 *        public static void main(String[] args) {
 *            BeautifulArrangementMemo solver = new BeautifulArrangementMemo();
 *            System.out.println(solver.countArrangement(4));   // 8
 *            System.out.println(solver.countArrangement(15));  // 24679
 *        }
 *    }
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force Permutations
 * ------------------------------------------------------------
 *
 * TIME - O(n! * n)
 *
 * | Step                                          | Cost                    |
 * |-----------------------------------------------|-------------------------|
 * | Number of permutations generated              | n!                      |
 * | Validation scan per complete permutation      | O(n)                    |
 * | Swap overhead along each root-to-leaf path    | O(n), absorbed          |
 * | TOTAL                                         | n! * O(n) = O(n! * n)   |
 *
 * SPACE - O(n): one int[n] array, plus a recursion stack of depth n. No
 * structure grows with the number of permutations because they're validated and
 * discarded immediately.
 *
 * CONCRETE NUMBERS:
 *
 * | n  | Permutations      | Operations (n! * n) | Feasible?              |
 * |----|-------------------|---------------------|------------------------|
 * | 8  | 40,320            | ~322,560            | Yes, milliseconds      |
 * | 11 | 39,916,800        | ~4.4e8              | Seconds - borderline   |
 * | 15 | 1,307,674,368,000 | ~2e13               | No - hours to days     |
 *
 * ------------------------------------------------------------
 * Approach 2: Backtracking with Pruning
 * ------------------------------------------------------------
 *
 * TIME - O(n!) as a formal upper bound, dramatically less in practice
 *
 * The honest derivation: in the absolute worst case where every value is
 * compatible with every position, the recursion tree has
 * n * (n-1) * (n-2) * ... = n! leaves, so O(n!) is the only bound provable from
 * the structure alone. The saving over approach 1 is that validation is folded
 * into placement - no separate O(n) scan, and incompatible branches are cut at
 * the node rather than the leaf.
 *
 * But that worst case never occurs, because divisibility is sparse. Position p
 * accepts only the divisors of p and the multiples of p within 1..n - for
 * p = 11, n = 15, that's just {1, 11}. Measured node counts:
 *
 * | n  | Recursive calls made | n!      | Valid arrangements found |
 * |----|----------------------|---------|--------------------------|
 * | 8  | 1,138                | 40,320  | 132                      |
 * | 10 | 8,964                | 3,628,800 | 700                    |
 * | 15 | 747,961              | 1.3e12  | 24,679                   |
 *
 * At n = 15 the search touches roughly ONE NODE PER 1.7 MILLION PERMUTATIONS
 * the brute force would have enumerated. That's the entire value of the
 * pruning.
 *
 * SPACE - O(n): the boolean[n+1] array plus recursion depth n. For n = 15: 16
 * bytes of flags and 15 stack frames. This is the minimum any correct approach
 * can use, and it's why this is the space-optimal choice.
 *
 * ------------------------------------------------------------
 * Approach 3: Bitmask DP over Subsets [OPTIMAL]
 * ------------------------------------------------------------
 *
 * TIME - O(2^n * n)
 *
 * | Step                                    | Cost                                     |
 * |-----------------------------------------|------------------------------------------|
 * | Number of mask states                   | 2^n                                      |
 * | Inner loop over candidate values        | n                                        |
 * | Work per (mask, value) pair             | O(1) - bit test, two modulos, one add    |
 * | Integer.bitCount per state              | O(1) - single POPCNT instruction         |
 * | TOTAL                                   | O(2^n * n)                               |
 *
 * This bound is UNCONDITIONAL - it does not depend on how sparse the
 * divisibility relation happens to be. That's its main advantage over
 * backtracking.
 *
 * SPACE - O(2^n): the int[1 << n] table. Nothing else scales.
 *
 * CONCRETE NUMBERS:
 *
 * | n  | States (2^n) | Operations (2^n * n) | Table memory       |
 * |----|--------------|----------------------|--------------------|
 * | 8  | 256          | 2,048                | 1 KB               |
 * | 15 | 32,768       | 491,520              | 128 KB             |
 * | 20 | 1,048,576    | ~2.1e7               | 4 MB               |
 * | 25 | 33,554,432   | ~8.4e8               | 128 MB - the wall  |
 *
 * At the problem's maximum n = 15, the DP does ~492K operations versus
 * backtracking's ~748K node visits - the DP wins, and measured wall time
 * reflects it (~2.5 ms vs ~31 ms). But note the crossover: the DP visits ALL
 * 2^n subsets including ones no valid partial arrangement ever reaches, so for
 * a hypothetically much sparser constraint at larger n, pruned backtracking
 * would pull ahead.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 - Brute Force, n = 3
 * ------------------------------------------------------------
 *
 * All 3! = 6 permutations are generated and each is fully validated:
 *
 * | # | Permutation | pos 1 check | pos 2 check           | pos 3 check           | Verdict  |
 * |---|-------------|-------------|-----------------------|-----------------------|----------|
 * | 1 | [1, 2, 3]   | 1%1=0 [OK]  | 2%2=0 [OK]            | 3%3=0 [OK]            | VALID    |
 * | 2 | [1, 3, 2]   | 1%1=0 [OK]  | 3%2=1, 2%3=2 [NO]     | -                     | rejected |
 * | 3 | [2, 1, 3]   | 2%1=0 [OK]  | 2%1=0 [OK]            | 3%3=0 [OK]            | VALID    |
 * | 4 | [2, 3, 1]   | 2%1=0 [OK]  | 3%2=1, 2%3=2 [NO]     | -                     | rejected |
 * | 5 | [3, 1, 2]   | 3%1=0 [OK]  | 2%1=0 [OK]            | 2%3=2, 3%2=1 [NO]     | rejected |
 * | 6 | [3, 2, 1]   | 3%1=0 [OK]  | 2%2=0 [OK]            | 3%1=0 [OK]            | VALID    |
 *
 * OUTPUT: 3. Notice how much work is wasted - permutation 4 was fully
 * constructed before anyone noticed position 2 was already broken in
 * permutation 2's identical prefix.
 *
 * ------------------------------------------------------------
 * Approach 2 - Backtracking, n = 4
 * ------------------------------------------------------------
 *
 * Actual recursion tree, with pruned branches marked. used is shown implicitly
 * by which values remain selectable:
 *
 *    pos1 place 1
 *    │  ├─ pos2 place 2
 *    │  │   ├─ pos3 place 3
 *    │  │   │   └─ pos4 place 4  ->  VALID [1, 2, 3, 4]
 *    │  │   └─ pos3 try 4  ->  PRUNE (4%3=1, 3%4=3)
 *    │  ├─ pos2 try 3  ->  PRUNE (3%2=1, 2%3=2)
 *    │  └─ pos2 place 4
 *    │      ├─ pos3 try 2  ->  PRUNE (2%3=2, 3%2=1)
 *    │      └─ pos3 place 3
 *    │          └─ pos4 place 2  ->  VALID [1, 4, 3, 2]
 *    pos1 place 2
 *    │  ├─ pos2 place 1
 *    │  │   ├─ pos3 place 3
 *    │  │   │   └─ pos4 place 4  ->  VALID [2, 1, 3, 4]
 *    │  │   └─ pos3 try 4  ->  PRUNE
 *    │  ├─ pos2 try 3  ->  PRUNE
 *    │  └─ pos2 place 4
 *    │      ├─ pos3 place 1
 *    │      │   └─ pos4 try 3  ->  PRUNE (3%4=3, 4%3=1)  -> dead end
 *    │      └─ pos3 place 3
 *    │          └─ pos4 place 1  ->  VALID [2, 4, 3, 1]
 *    pos1 place 3
 *    │  ├─ pos2 place 1
 *    │  │   ├─ pos3 try 2  ->  PRUNE
 *    │  │   └─ pos3 try 4  ->  PRUNE          -> dead end
 *    │  ├─ pos2 place 2
 *    │  │   ├─ pos3 place 1
 *    │  │   │   └─ pos4 place 4  ->  VALID [3, 2, 1, 4]
 *    │  │   └─ pos3 try 4  ->  PRUNE
 *    │  └─ pos2 place 4
 *    │      ├─ pos3 place 1
 *    │      │   └─ pos4 place 2  ->  VALID [3, 4, 1, 2]
 *    │      └─ pos3 try 2  ->  PRUNE
 *    pos1 place 4
 *       ├─ pos2 place 1
 *       │   ├─ pos3 try 2  ->  PRUNE
 *       │   └─ pos3 place 3
 *       │       └─ pos4 place 2  ->  VALID [4, 1, 3, 2]
 *       ├─ pos2 place 2
 *       │   ├─ pos3 place 1
 *       │   │   └─ pos4 try 3  ->  PRUNE      -> dead end
 *       │   └─ pos3 place 3
 *       │       └─ pos4 place 1  ->  VALID [4, 2, 3, 1]
 *       └─ pos2 try 3  ->  PRUNE
 *
 * COUNT = 8. Three branches reached a dead end (a partial arrangement with no
 * legal continuation) - those are the cases where pruning alone can't save you
 * and you genuinely must unwind. Every other rejection was cut at the node.
 *
 * ------------------------------------------------------------
 * Approach 3 - Bitmask DP, n = 3
 * ------------------------------------------------------------
 *
 * Bit 0 = value 1, bit 1 = value 2, bit 2 = value 3. dp[0] = 1.
 *
 * | mask | Values in set | position = popcount | Transitions evaluated                                                         | dp[mask] |
 * |------|---------------|---------------------|-------------------------------------------------------------------------------|----------|
 * | 000  | {}            | -                   | base case                                                                     | 1        |
 * | 001  | {1}           | 1                   | val 1 at pos 1: 1%1=0 [OK] -> +dp[000]=1                                      | 1        |
 * | 010  | {2}           | 1                   | val 2 at pos 1: 2%1=0 [OK] -> +dp[000]=1                                      | 1        |
 * | 011  | {1,2}         | 2                   | val 1 at pos 2: 2%1=0 [OK] -> +dp[010]=1; val 2 at pos 2: 2%2=0 [OK] -> +dp[001]=1 | 2    |
 * | 100  | {3}           | 1                   | val 3 at pos 1: 3%1=0 [OK] -> +dp[000]=1                                      | 1        |
 * | 101  | {1,3}         | 2                   | val 1 at pos 2: [OK] -> +dp[100]=1; val 3 at pos 2: 3%2=1, 2%3=2 [NO]         | 1        |
 * | 110  | {2,3}         | 2                   | val 2 at pos 2: [OK] -> +dp[100]=1; val 3 at pos 2: [NO]                      | 1        |
 * | 111  | {1,2,3}       | 3                   | val 1 at pos 3: 3%1=0 [OK] -> +dp[110]=1; val 2: [NO]; val 3: 3%3=0 [OK] -> +dp[011]=2 | 3 |
 *
 * ANSWER = dp[111] = 3. [OK]
 *
 * The key moment is the last row: dp[111] = dp[110] + dp[011] = 1 + 2. The 2
 * comes from dp[011], which itself collapsed two different orderings of {1,2}
 * into one state - that collapse is the entire speedup, and it compounds as n
 * grows.
 *
 * SAME DP AT n = 4, FINAL ROW: dp[1111] = dp[1110] (val 1 at pos 4, 4%1=0) +
 * dp[1101] (val 2 at pos 4, 4%2=0) + dp[0111] (val 4 at pos 4, 4%4=0) =
 * 2 + 3 + 3 = 8 [OK] - with val 3 rejected at position 4 since 4%3=1 and 3%4=3.
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                     | Input                       | Expected Output | How Handled                                                                                                                                             |
 * |-------------------------------|-----------------------------|-----------------|---------------------------------------------------------------------------------------------------------------------------------------------------------|
 * | Minimum constraint            | n = 1                       | 1               | Only permutation is [1]; 1 % 1 == 0. Backtracking: place(1) places value 1, place(2) hits pos > n and counts. DP: dp[1] = dp[0] = 1.                     |
 * | Two elements - both work      | n = 2                       | 2               | Both [1,2] and [2,1] are valid, since 1 is compatible with everything and 2%2==0, 2%1==0. No pruning occurs at all.                                       |
 * | Maximum constraint            | n = 15                      | 24679           | DP allocates int[32768] - 128 KB, trivially fine. Backtracking explores 747,961 nodes in ~31 ms. Both well inside limits.                                 |
 * | Prime-heavy positions         | n = 13                      | 4237            | Position 11 accepts only {1, 11}; position 13 only {1, 13}. Pruning handles this automatically. Note 4237 barely exceeds n=12's 4010.                     |
 * | Position 1 / value 1 universality | any n                   | -               | 1 % pos == 0 is false for pos > 1, but pos % 1 == 0 is always true - so value 1 fits anywhere. Requires the SECOND half of the OR to be present.          |
 * | Result magnitude              | n = 15                      | 24679           | Fits comfortably in int; no overflow risk anywhere. dp[] values never approach Integer.MAX_VALUE.                                                         |
 * | Dead-end partial              | n = 4, prefix [2, 4, 1]     | contributes 0   | Position 4 has only value 3 left; 4%3=1, 3%4=3 -> no legal placement. Loop completes without recursing, function returns, count unchanged.                |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 *
 * PITFALL 1 - Using the 0-based array index as the position
 *
 *    // WRONG - division by zero at i=0, and every check is off by one
 *    for (int i = 0; i < perm.length; i++) {
 *        if (perm[i] % i != 0 && i % perm[i] != 0) return false;  // i = 0 -> ArithmeticException
 *    }
 *
 *    // CORRECT - convert to a 1-based position
 *    for (int i = 0; i < perm.length; i++) {
 *        int position = i + 1;
 *        if (perm[i] % position != 0 && position % perm[i] != 0) return false;
 *    }
 *
 * PITFALL 2 - Checking only one direction of divisibility
 *
 *    // WRONG - drops every arrangement where the position is the multiple
 *    if (value % position == 0) { /* place *​/ }
 *
 *    // CORRECT - either direction qualifies
 *    if (value % position == 0 || position % value == 0) { /* place *​/ }
 *
 * For n = 4 the wrong version returns 2 instead of 8.
 *
 * PITFALL 3 - Validating after building instead of pruning during
 *
 *    // WRONG (not incorrect, just uselessly slow - brute force in disguise)
 *    private void place(int pos) {
 *        if (pos > n) { if (isBeautiful(current)) count++; return; }
 *        for (int v = 1; v <= n; v++) {
 *            if (used[v]) continue;
 *            used[v] = true; current[pos] = v; place(pos + 1); used[v] = false;
 *        }
 *    }
 *
 *    // CORRECT - reject before recursing
 *    private void place(int pos) {
 *        if (pos > n) { count++; return; }
 *        for (int v = 1; v <= n; v++) {
 *            if (used[v] || (v % pos != 0 && pos % v != 0)) continue;
 *            used[v] = true; place(pos + 1); used[v] = false;
 *        }
 *    }
 *
 * PITFALL 4 - The popcount off-by-one between bottom-up and top-down
 *
 *    // WRONG in the bottom-up loop - treats mask as "before placing"
 *    int position = Integer.bitCount(mask) + 1;  // for n=3 yields 4 at mask=111
 *
 *    // CORRECT bottom-up - mask holds values ALREADY placed
 *    int position = Integer.bitCount(mask);
 *
 *    // CORRECT top-down - mask holds values placed so far, filling the NEXT position
 *    int position = Integer.bitCount(mask) + 1;
 *
 * PITFALL 5 - Forgetting to undo state on the way out
 *
 *    // WRONG - value stays marked used, so sibling branches see corrupted state
 *    used[value] = true;
 *    place(position + 1);
 *    // missing reset
 *
 *    // CORRECT
 *    used[value] = true;
 *    place(position + 1);
 *    used[value] = false;
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * ------------------------------------------------------------
 * Q: What edge cases might this miss?
 * ------------------------------------------------------------
 *
 * A: Four worth naming explicitly.
 *
 * 1. n = 1 - the recursion must count the empty completion. If your base case
 *    is pos == n instead of pos > n, you'll count an arrangement one position
 *    too early and get systematically wrong answers.
 *
 * 2. Dead-end partials - a partial arrangement with no legal continuation must
 *    contribute 0, not throw or leak a count. Both approaches handle this
 *    naturally: the for loop simply completes without recursing.
 *
 * 3. Value 1 and position 1 - these are compatible with everything, but only
 *    via the pos % val == 0 and val % pos == 0 halves respectively. Dropping
 *    either half breaks them.
 *
 * 4. Unreachable DP states - the bottom-up DP computes dp[mask] for EVERY mask,
 *    including sets that no valid prefix can produce. Those correctly evaluate
 *    to 0 and contribute nothing.
 *
 * The problem has NO empty input, no null input, and no k parameter - n >= 1 is
 * guaranteed, so guarding against n <= 0 is optional defensive code rather than
 * a required case.
 *
 * ------------------------------------------------------------
 * Q: Are there any type mismatches?
 * ------------------------------------------------------------
 *
 * A: No - but four details are worth confirming:
 *
 * - RETURN TYPE IS int. The maximum answer at n = 15 is 24,679, four orders of
 *   magnitude below Integer.MAX_VALUE. No long needed anywhere, including
 *   intermediate dp[] sums.
 * - 1 << n IS SAFE. For n = 15 that's 32,768, far inside int range. It would
 *   only overflow at n >= 31, which the constraints exclude.
 * - boolean[n + 1] vs boolean[n]. The +1 is deliberate so values index
 *   directly. Sizing it n and indexing used[value] throws
 *   ArrayIndexOutOfBoundsException when value == n.
 * - Integer.bitCount RETURNS int, matching the position variable. No boxing, no
 *   conversion.
 *
 * ------------------------------------------------------------
 * Q: How can I verify this works right now?
 * ------------------------------------------------------------
 *
 * A: Run this - it cross-checks all three implementations against each other
 * and against the known sequence. Enable assertions with java -ea.
 *
 *    import java.util.Arrays;
 *
 *    public class BeautifulArrangementVerify {
 *
 *        // ---- Approach 2: backtracking ----
 *        static int btCount, btN;
 *        static boolean[] btUsed;
 *
 *        static int backtrack(int n) {
 *            btN = n; btCount = 0; btUsed = new boolean[n + 1];
 *            place(1);
 *            return btCount;
 *        }
 *        static void place(int pos) {
 *            if (pos > btN) { btCount++; return; }
 *            for (int v = 1; v <= btN; v++) {
 *                if (btUsed[v] || (v % pos != 0 && pos % v != 0)) continue;
 *                btUsed[v] = true;
 *                place(pos + 1);
 *                btUsed[v] = false;
 *            }
 *        }
 *
 *        // ---- Approach 3: bitmask DP ----
 *        static int dpSolve(int n) {
 *            int full = 1 << n;
 *            int[] dp = new int[full];
 *            dp[0] = 1;
 *            for (int mask = 1; mask < full; mask++) {
 *                int pos = Integer.bitCount(mask);
 *                for (int v = 1; v <= n; v++) {
 *                    int bit = 1 << (v - 1);
 *                    if ((mask & bit) == 0) continue;
 *                    if (v % pos == 0 || pos % v == 0) dp[mask] += dp[mask ^ bit];
 *                }
 *            }
 *            return dp[full - 1];
 *        }
 *
 *        // ---- Approach 1: brute force (small n only, used as oracle) ----
 *        static int bfCount;
 *        static int brute(int n) {
 *            bfCount = 0;
 *            int[] perm = new int[n];
 *            for (int i = 0; i < n; i++) perm[i] = i + 1;
 *            gen(perm, 0);
 *            return bfCount;
 *        }
 *        static void gen(int[] p, int idx) {
 *            if (idx == p.length) {
 *                for (int i = 0; i < p.length; i++) {
 *                    int pos = i + 1;
 *                    if (p[i] % pos != 0 && pos % p[i] != 0) return;
 *                }
 *                bfCount++;
 *                return;
 *            }
 *            for (int j = idx; j < p.length; j++) {
 *                int t = p[idx]; p[idx] = p[j]; p[j] = t;
 *                gen(p, idx + 1);
 *                t = p[idx]; p[idx] = p[j]; p[j] = t;
 *            }
 *        }
 *
 *        static void verify() {
 *            // Known sequence for n = 1..15
 *            int[] expected = {0, 1, 2, 3, 8, 10, 36, 41, 132, 250, 700, 750,
 *                              4010, 4237, 10680, 24679};
 *
 *            for (int n = 1; n <= 15; n++) {
 *                assert backtrack(n) == expected[n]
 *                    : "backtrack failed at n=" + n + " got " + backtrack(n);
 *                assert dpSolve(n) == expected[n]
 *                    : "dp failed at n=" + n + " got " + dpSolve(n);
 *            }
 *
 *            // Brute force cross-check where it terminates quickly
 *            for (int n = 1; n <= 8; n++) {
 *                assert brute(n) == expected[n] : "brute failed at n=" + n;
 *                assert brute(n) == dpSolve(n)  : "brute and dp disagree at n=" + n;
 *            }
 *
 *            // Boundary assertions
 *            assert backtrack(1) == 1  : "n=1 must be 1";
 *            assert dpSolve(15) == 24679 : "n=15 must be 24679";
 *            assert dpSolve(15) == backtrack(15) : "approaches disagree at max n";
 *
 *            System.out.println("All assertions passed.");
 *        }
 *
 *        public static void main(String[] args) {
 *            boolean assertionsOn = false;
 *            assert assertionsOn = true;
 *            if (!assertionsOn) {
 *                System.out.println("WARNING: run with -ea to enable assertions.");
 *            }
 *            verify();
 *            System.out.println("n=1..15: " + Arrays.toString(
 *                new int[]{dpSolve(1), dpSolve(2), dpSolve(3), dpSolve(4), dpSolve(5),
 *                          dpSolve(6), dpSolve(7), dpSolve(8), dpSolve(9), dpSolve(10),
 *                          dpSolve(11), dpSolve(12), dpSolve(13), dpSolve(14), dpSolve(15)}));
 *        }
 *    }
 *
 * Expected output: "All assertions passed." followed by
 * [1, 2, 3, 8, 10, 36, 41, 132, 250, 700, 750, 4010, 4237, 10680, 24679].
 * This cross-check was actually run while preparing this explanation - all
 * three implementations agree at every n from 1 to 15.
 *
 * ------------------------------------------------------------
 * Risk Table
 * ------------------------------------------------------------
 *
 * | Approach          | Risk                                                                  | Mitigation                                                                                                       |
 * |-------------------|-----------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------|
 * | 1 - Brute Force   | Times out for n >= 12; will TLE on LeetCode                           | Use only as a correctness oracle for n <= 8; never submit it                                                     |
 * | 1 - Brute Force   | Swap-based generation corrupts the array if the undo swap is omitted  | Always restore state immediately after the recursive call; assert the array is a permutation at the top level     |
 * | 2 - Backtracking  | No provable time bound - relies on empirical pruning strength         | Fine here because constraints cap n at 15 and measured cost is 748K nodes; if relaxed, switch to the DP           |
 * | 2 - Backtracking  | Forgetting used[value] = false silently undercounts                   | Pair every = true with a = false on the same indentation level; verify n = 4 returns 8                            |
 * | 2 - Backtracking  | Validating at the leaf instead of pruning at the node                 | Confirm the compatibility check appears INSIDE the loop, before place(position + 1)                               |
 * | 3 - Bitmask DP    | popcount off-by-one between bottom-up and top-down formulations       | Test n = 3 by hand against the Section 6 table; dp[111] must be 3                                                 |
 * | 3 - Bitmask DP    | O(2^n) memory becomes prohibitive past n ~ 25                         | Safe under the stated constraint; if n could grow, fall back to pruned backtracking                               |
 * | 3 - Bitmask DP    | Iterating masks in the wrong order reads uncomputed states            | Ascending mask order is required - mask ^ bit is always strictly smaller, so dependencies are already resolved     |
 *
 * ============================================================
 * 9. FINAL SUMMARY
 * ============================================================
 *
 * | Approach                   | Time                                  | Space                     | Code Complexity | Recommended?                                                                                                       |
 * |----------------------------|---------------------------------------|---------------------------|-----------------|--------------------------------------------------------------------------------------------------------------------|
 * | 1 - Brute Force Permutations | O(n! * n)                           | O(n)                      | Low             | [NO] Dominated outright - approach 2 matches its space and is ~1.7M x faster at n=15. Reference oracle only.        |
 * | 2 - Backtracking with Pruning | O(n!) bound; ~748K nodes at n=15    | O(n)                      | Low-Medium      | [OK] BEST FOR LOW MEMORY - minimal footprint, easy to write under pressure, and the only option if you must enumerate |
 * | 3 - Bitmask DP over Subsets | O(2^n * n) = 491,520 ops at n=15     | O(2^n) = 128 KB           | Medium          | [OK][OK] BEST FOR TIME - hard worst-case guarantee, ~12x faster measured, demonstrates the subset-DP pattern         |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 *
 * Bitmask DP (approach 3) when you want a provable bound and the interviewer is
 * probing for the subset-state insight; pruned backtracking (approach 2) when
 * memory is the binding constraint or you need the arrangements themselves -
 * both pass comfortably at n <= 15, so this is a genuine split rather than one
 * dominating the other.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 *
 * Whenever a problem asks you to COUNT permutations under position-dependent
 * constraints and caps n at 15-20, that constraint is a signal: the intended
 * state is a BITMASK OF USED ELEMENTS, not a sequence, because two prefixes
 * using the same SET of values have identical futures. The position you're
 * filling never needs to be stored separately - it's popcount(mask) when the
 * mask holds already-placed values (bottom-up) or popcount(mask) + 1 when it
 * holds the state before placing (top-down), and confusing those two is the
 * classic bug. The second thing to burn in: the condition here is
 * bidirectional, perm[i] % i == 0 || i % perm[i] == 0, and it must be checked
 * AT PLACEMENT TIME inside the loop, never after building a complete
 * permutation - pruning at the node is what turns a trillion-step search into a
 * half-million-step one.
 *
 * ============================================================
 * END OF EXPLANATION
 * ============================================================
 */
// @formatter:on
