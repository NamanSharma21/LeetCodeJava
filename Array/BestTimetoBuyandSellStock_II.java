package Array;

public class BestTimetoBuyandSellStock_II {
    public static void main(String[] args) {
        BestTimetoBuyandSellStock_II bestTimetoBuyandSellStock_II = new BestTimetoBuyandSellStock_II();
        System.out.println("BestTimetoBuyandSellStock_II : "
                + bestTimetoBuyandSellStock_II.maxProfitBruteForce(new int[] { 7, 1, 5, 3, 6, 4 }));
        System.out.println("BestTimetoBuyandSellStock_II : "
                + bestTimetoBuyandSellStock_II.maxProfitBruteForce(new int[] { 1, 2, 3, 4, 5 }));
        System.out.println("BestTimetoBuyandSellStock_II : "
                + bestTimetoBuyandSellStock_II.maxProfitBruteForce(new int[] { 7, 6, 4, 3, 1 }));
        System.out.println("------------------------------");
        System.out.println("BestTimetoBuyandSellStock_II : "
                + bestTimetoBuyandSellStock_II.maxProfitDPTable(new int[] { 7, 1, 5, 3, 6, 4 }));
        System.out.println("BestTimetoBuyandSellStock_II : "
                + bestTimetoBuyandSellStock_II.maxProfitDPTable(new int[] { 1, 2, 3, 4, 5 }));
        System.out.println("BestTimetoBuyandSellStock_II : "
                + bestTimetoBuyandSellStock_II.maxProfitDPTable(new int[] { 7, 6, 4, 3, 1 }));
        System.out.println("------------------------------");
        System.out.println("BestTimetoBuyandSellStock_II : "
                + bestTimetoBuyandSellStock_II.maxProfitDPSpaceOptimized(new int[] { 7, 1, 5, 3, 6, 4 }));
        System.out.println("BestTimetoBuyandSellStock_II : "
                + bestTimetoBuyandSellStock_II.maxProfitDPSpaceOptimized(new int[] { 1, 2, 3, 4, 5 }));
        System.out.println("BestTimetoBuyandSellStock_II : "
                + bestTimetoBuyandSellStock_II.maxProfitDPSpaceOptimized(new int[] { 7, 6, 4, 3, 1 }));
        System.out.println("------------------------------");
        System.out.println("BestTimetoBuyandSellStock_II : "
                + bestTimetoBuyandSellStock_II.maxProfitGreedy(new int[] { 7, 1, 5, 3, 6, 4 }));
        System.out.println("BestTimetoBuyandSellStock_II : "
                + bestTimetoBuyandSellStock_II.maxProfitGreedy(new int[] { 1, 2, 3, 4, 5 }));
        System.out.println("BestTimetoBuyandSellStock_II : "
                + bestTimetoBuyandSellStock_II.maxProfitGreedy(new int[] { 7, 6, 4, 3, 1 }));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/description/
     * 
     * You are given an integer array prices where prices[i] is the price of a given
     * stock on the ith day.
     * 
     * On each day, you may decide to buy and/or sell the stock. You can only hold
     * at most one share of the stock at any time. However, you can buy it then
     * immediately sell it on the same day.
     * 
     * Find and return the maximum profit you can achieve.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: prices = [7,1,5,3,6,4]
     * Output: 7
     * Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit =
     * 5-1 = 4.
     * Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 =
     * 3.
     * Total profit is 4 + 3 = 7.
     * Example 2:
     * 
     * Input: prices = [1,2,3,4,5]
     * Output: 4
     * Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit =
     * 5-1 = 4.
     * Total profit is 4.
     * Example 3:
     * 
     * Input: prices = [7,6,4,3,1]
     * Output: 0
     * Explanation: There is no way to make a positive profit, so we never buy the
     * stock to achieve the maximum profit of 0.
     * 
     * 
     * Constraints:
     * 
     * 1 <= prices.length <= 3 * 104
     * 0 <= prices[i] <= 104
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach           | Time   | Space | Code Complexity            | Recommended?              |
     * |--------------------|--------|-------|----------------------------|---------------------------|
     * | Brute Force        | O(2^n) | O(n)  | Low code, catastrophic time| [X] Teaching only         |
     * 
     * @param prices
     * @return
     */
    // @formatter:on
    public int maxProfitBruteForce(int[] prices) {
        return recurse(prices, 0, false);
    }

    public int recurse(int[] prices, int day, boolean holding) {
        if (day == prices.length)
            return 0;
        int doNothing = recurse(prices, day + 1, holding);

        int transact;
        if (holding) {
            transact = prices[day] + recurse(prices, day + 1, false);
        } else {
            transact = -prices[day] + recurse(prices, day + 1, true);
        }
        return Math.max(doNothing, transact);
    }

    // @formatter:off
    /**
     * 
     * | Approach           | Time   | Space | Code Complexity            | Recommended?              |
     * |--------------------|--------|-------|----------------------------|---------------------------|
     * | DP Table           | O(n)   | O(n)  | Moderate                   | [OK] Use for follow-ups   |
     * 
     * @param prices
     * @return
     */
    // @formatter:on
    public int maxProfitDPTable(int[] prices) {
        int n = prices.length;
        if (n == 0)
            return 0;
        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[n - 1][0];
    }

    // @formatter:off
    /**
     * 
     * | Approach           | Time   | Space | Code Complexity            | Recommended?              |
     * |--------------------|--------|-------|----------------------------|---------------------------|
     * | DP Space-Optimized | O(n)   | O(1)  | Moderate                   | [OK] Memory-tight choice  |
     * 
     * @param prices
     * @return
     */
    // @formatter:on
    public int maxProfitDPSpaceOptimized(int[] prices) {
        int cash = 0;
        int hold = Integer.MIN_VALUE;
        for (int price : prices) {
            cash = Math.max(cash, hold + price);
            hold = Math.max(hold, cash - price);
        }
        return cash;
    }

    // @formatter:off
    /**
     * 
     * | Approach           | Time   | Space | Code Complexity            | Recommended?              |
     * |--------------------|--------|-------|----------------------------|---------------------------|
     * | Greedy             | O(n)   | O(1)  | Minimal                    | [BEST] For this problem   |
     * 
     * @param prices
     * @return
     */
    // @formatter:on
    public int maxProfitGreedy(int[] prices) {
        int totalProfit = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i] < prices[i + 1]) {
                totalProfit += (prices[i + 1] - prices[i]);
            }
        }
        return totalProfit;
    }
}
// @formatter:off
/*
 * ============================================================
 * BEST TIME TO BUY AND SELL STOCK II - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are given the daily prices of a single stock. On each day you may buy,
 * sell, or do nothing. You can hold AT MOST ONE share at any time, so you must
 * sell before buying again. You may complete AS MANY transactions as you like.
 * Goal: compute the maximum total profit.
 *
 * This is LeetCode #122, difficulty Medium. It is the sequel to #121, which
 * limits you to a single transaction - the "unlimited transactions" twist is
 * what changes everything.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] prices - prices[i] is the stock price on day i.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * A single int - the maximum total profit.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * 1 <= prices.length <= 3 * 10^4
 * 0 <= prices[i] <= 10^4
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * The maximum sum of (sell price - buy price) over any set of non-overlapping
 * buy->sell pairs, where each buy precedes its matching sell and intervals do
 * not overlap in time.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    prices = [7, 1, 5, 3, 6, 4]
 * Buy at 1, sell at 5 -> +4. Buy at 3, sell at 6 -> +3. Total = 7.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Because you can transact unlimited times AND buying/selling on the same day
 * costs nothing, holding through a rising streak is EXACTLY equivalent to
 * selling and re-buying every day of that streak. So just grab every small
 * upward step and add them up: whenever tomorrow is higher than today, pocket
 * the difference.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. If the price goes UP from today to tomorrow, that gain is capturable.
 * 2. If it goes DOWN, don't hold across the drop - capture nothing (never neg).
 * 3. Summing positive daily jumps equals summing full valley->peak climbs,
 *    since 1 -> 3 -> 6 breaks into (3-1)+(6-3)=5 = 6-1.
 * 4. Answer = sum of all positive differences between consecutive days.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                                   | Why it's tricky |
 * |---------------------------------------------|-----------------|
 * | "Unlimited transactions" sounds expensive   | Feels exponential to search, but greedy collapses it to one pass. |
 * | Confusing it with Stock I (single txn)      | #121's min-so-far/best-spread logic gives the wrong (too small) answer. |
 * | Believing you must find true valleys/peaks  | Summing tiny daily gains yields the identical total, far simpler. |
 * | Worrying same-day sell-then-buy is illegal  | It's allowed (nets zero shares) - exactly what makes greedy valid. |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach              | Key Idea                                          | Best Used When                          | Time     | Space          |
 * |---|-----------------------|---------------------------------------------------|-----------------------------------------|----------|----------------|
 * | 1 | Brute Force Recursion | Branch on hold/skip each day; explore all seqs    | Understanding state space; tiny inputs  | O(2^n)   | O(n) stack     |
 * | 2 | DP (table)            | Track best profit in two states per day           | General state-machine framework         | O(n)     | O(n)           |
 * | 3 | DP, Space-Optimized   | Keep only the two previous state values           | Same as #2 but memory-tight             | O(n)     | O(1) [space-opt] |
 * | 4 | Greedy (positive deltas) | Add every positive consecutive difference      | This specific problem - cleanest optimal| O(n) [time-opt] | O(1) [space-opt] |
 *
 * Brute force exposes the decision structure but is exponential (unusable past
 * ~20 days). The DP table is a clean linear scan but stores an n x 2 array; the
 * space-optimized DP proves you only need the previous day's two values, giving
 * O(1) memory. Greedy is the sweet spot: O(n) time and O(1) space with the
 * least code. Rows 3 and 4 tie on both axes, but GREEDY wins on simplicity and
 * is the interview answer. Use the DP formulation only when a follow-up adds a
 * fee, a cooldown, or a cap of k transactions - then the state machine
 * generalizes and greedy does not.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force Recursion
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Recurse over (day, holding).
 * 2. Always allow SKIP (do nothing, next day).
 * 3. If holding, allow SELL today: +prices[day], holding=false.
 * 4. If not holding, allow BUY today: -prices[day], holding=true.
 * 5. Return max of choices. Base case: past last day -> 0.
 *
 *    public class StockIIBruteForce {
 *        public int maxProfit(int[] prices) {
 *            return recurse(prices, 0, false);
 *        }
 *        private int recurse(int[] prices, int day, boolean holding) {
 *            if (day == prices.length) return 0;
 *            int doNothing = recurse(prices, day + 1, holding);
 *            int transact;
 *            if (holding) {
 *                transact = prices[day] + recurse(prices, day + 1, false);
 *            } else {
 *                transact = -prices[day] + recurse(prices, day + 1, true);
 *            }
 *            return Math.max(doNothing, transact);
 *        }
 *        public static void main(String[] args) {
 *            StockIIBruteForce s = new StockIIBruteForce();
 *            System.out.println(s.maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // 7
 *        }
 *    }
 *
 * Each day spawns two branches -> O(2^n). Correct, but teaching only.
 *
 * ------------------------------------------------------------
 * Approach 2: Dynamic Programming (Table)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. dp[i][0] = max profit on day i NOT holding; dp[i][1] = holding.
 * 2. Base: dp[0][0]=0, dp[0][1]=-prices[0].
 * 3. Not holding: max(dp[i-1][0], dp[i-1][1]+prices[i]).
 * 4. Holding: max(dp[i-1][1], dp[i-1][0]-prices[i]).
 * 5. Answer dp[n-1][0] - never end still holding.
 *
 *    public class StockIIDpTable {
 *        public int maxProfit(int[] prices) {
 *            int n = prices.length;
 *            if (n == 0) return 0;
 *            int[][] dp = new int[n][2];
 *            dp[0][0] = 0;
 *            dp[0][1] = -prices[0];
 *            for (int i = 1; i < n; i++) {
 *                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
 *                dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
 *            }
 *            return dp[n - 1][0];
 *        }
 *        public static void main(String[] args) {
 *            StockIIDpTable s = new StockIIDpTable();
 *            System.out.println(s.maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // 7
 *        }
 *    }
 *
 * Ending in state 0 is required: a leftover held share ties up money.
 *
 * ------------------------------------------------------------
 * Approach 3: DP, Space-Optimized
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Each row depends only on the previous -> keep two scalars cash and hold.
 * 2. cash=0, hold=Integer.MIN_VALUE (cannot start already holding).
 * 3. For each price: cash=max(cash, hold+price); hold=max(hold, cash-price).
 * 4. Return cash.
 *
 *    public class StockIIDpOpt {
 *        public int maxProfit(int[] prices) {
 *            int cash = 0;
 *            int hold = Integer.MIN_VALUE;
 *            for (int price : prices) {
 *                cash = Math.max(cash, hold + price);
 *                hold = Math.max(hold, cash - price);
 *            }
 *            return cash;
 *        }
 *        public static void main(String[] args) {
 *            StockIIDpOpt s = new StockIIDpOpt();
 *            System.out.println(s.maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // 7
 *        }
 *    }
 *
 * Updating cash before hold uses the fresh cash inside hold - it models buying
 * on the same day you sold and gives the same result as the two-array version.
 *
 * ------------------------------------------------------------
 * Approach 4: Greedy (Sum of Positive Deltas)  [OPTIMAL]
 * ------------------------------------------------------------
 * Algorithm:
 * 1. profit = 0.
 * 2. Scan i=1..end; when prices[i] > prices[i-1], add the difference.
 * 3. Return profit.
 *
 *    public class StockIIGreedy {
 *        public int maxProfit(int[] prices) {
 *            int profit = 0;
 *            for (int i = 1; i < prices.length; i++) {
 *                if (prices[i] > prices[i - 1]) {
 *                    profit += prices[i] - prices[i - 1];
 *                }
 *            }
 *            return profit;
 *        }
 *        public static void main(String[] args) {
 *            StockIIGreedy s = new StockIIGreedy();
 *            System.out.println(s.maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // 7
 *            System.out.println(s.maxProfit(new int[]{1, 2, 3, 4, 5}));    // 4
 *            System.out.println(s.maxProfit(new int[]{7, 6, 4, 3, 1}));    // 0
 *        }
 *    }
 *
 * Key identity: a rising run a->b->c gives (b-a)+(c-b)=c-a, so summing small
 * positive steps equals summing valley-to-peak climbs.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Approach 1 (Brute Force):
 *   Time  O(2^n): each day branches into two calls, depth n -> ~2^n leaves.
 *                 n=20 ~ 1e6 calls; n=30 ~ 1e9 - too slow.
 *   Space O(n): recursion stack depth = number of days.
 *
 * Approach 2 (DP Table):
 *   Time  O(n): single loop, constant work per day. n=30000 -> ~60000 maxes.
 *   Space O(n): n x 2 table -> 60000 ints (~240 KB).
 *
 * Approach 3 (DP Space-Optimized):
 *   Time  O(n): same loop, ~30000 iterations.
 *   Space O(1): two integer variables.
 *
 * Approach 4 (Greedy):
 *   Time  O(n): one comparison / addition per day. n=30000 -> ~30000 compares.
 *   Space O(1): single accumulator.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * All use prices = [7, 1, 5, 3, 6, 4] (expected 7).
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force Recursion (optimal path)
 * ------------------------------------------------------------
 * recurse(day0, notHolding)
 * |- skip day0 (7)                 -> recurse(day1, notHolding)
 *    |- BUY day1  (-1)             -> recurse(day2, holding)
 *       |- SELL day2 (+5) run=+4   -> recurse(day3, notHolding)
 *          |- BUY day3 (-3) run=+1 -> recurse(day4, holding)
 *             |- SELL day4 (+6) run=+7 -> recurse(day5, notHolding)
 *                |- skip day5      -> 0
 * Total path = -1 +5 -3 +6 = 7  (the max over all branches)
 *
 * ------------------------------------------------------------
 * Approach 2: DP Table
 * ------------------------------------------------------------
 * | day | price | dp[i][0] not holding | dp[i][1] holding |
 * |-----|-------|----------------------|------------------|
 * | 0   | 7     | 0                    | -7               |
 * | 1   | 1     | max(0,-7+1)=0        | max(-7,0-1)=-1   |
 * | 2   | 5     | max(0,-1+5)=4        | max(-1,0-5)=-1   |
 * | 3   | 3     | max(4,-1+3)=4        | max(-1,4-3)=1    |
 * | 4   | 6     | max(4,1+6)=7         | max(1,4-6)=1     |
 * | 5   | 4     | max(7,1+4)=7         | max(1,7-4)=3     |
 * Answer dp[5][0] = 7.
 *
 * ------------------------------------------------------------
 * Approach 3: DP Space-Optimized
 * ------------------------------------------------------------
 * | price | cash=max(cash,hold+price) | hold=max(hold,cash-price) |
 * |-------|---------------------------|---------------------------|
 * | start | 0                         | -inf                      |
 * | 7     | max(0,-inf+7)=0           | max(-inf,0-7)=-7          |
 * | 1     | max(0,-7+1)=0             | max(-7,0-1)=-1           |
 * | 5     | max(0,-1+5)=4             | max(-1,4-5)=-1           |
 * | 3     | max(4,-1+3)=4             | max(-1,4-3)=1            |
 * | 6     | max(4,1+6)=7             | max(1,7-6)=1            |
 * | 4     | max(7,1+4)=7             | max(1,7-4)=3            |
 * Answer cash = 7.
 *
 * ------------------------------------------------------------
 * Approach 4: Greedy
 * ------------------------------------------------------------
 * | step | compare   | positive? | profit |
 * |------|-----------|-----------|--------|
 * | 7->1 | -6        | no        | 0      |
 * | 1->5 | +4        | yes       | 4      |
 * | 5->3 | -2        | no        | 4      |
 * | 3->6 | +3        | yes       | 7      |
 * | 6->4 | -2        | no        | 7      |
 * Answer = 7.
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case             | Input          | Expected | How Handled |
 * |-----------------------|----------------|----------|-------------|
 * | Single day            | [5]            | 0        | Loop starts at i=1, never runs -> 0. |
 * | Strictly decreasing   | [7,6,4,3,1]    | 0        | No positive delta -> profit stays 0. |
 * | Strictly increasing   | [1,2,3,4,5]    | 4        | Every step positive; sum = 5-1 = 4. |
 * | All equal             | [3,3,3,3]      | 0        | Strict > excludes equals -> 0. |
 * | Two days up           | [1,5]          | 4        | Single positive delta captured. |
 * | Empty array           | []             | 0        | Loop bounds / n==0 guard. |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 - Applying Stock I (single transaction) logic:
 *    // WRONG: best single buy-low/sell-high spread
 *    int minPrice = prices[0], best = 0;
 *    for (int p : prices) { minPrice = Math.min(minPrice, p); best = Math.max(best, p - minPrice); }
 *    return best; // gives 5 for [7,1,5,3,6,4], not 7
 *    // CORRECT: sum every positive daily gain
 *    int profit = 0;
 *    for (int i = 1; i < prices.length; i++)
 *        if (prices[i] > prices[i-1]) profit += prices[i] - prices[i-1];
 *    return profit;
 *
 * Pitfall 2 - Using >= instead of >:
 *    if (prices[i] >= prices[i-1]) profit += prices[i] - prices[i-1]; // adds 0 on flat days; use strict >
 *
 * Pitfall 3 - Off-by-one on the index:
 *    for (int i = 0; i < prices.length; i++)  // WRONG: prices[i-1] underflows at i=0
 *    for (int i = 1; i < prices.length; i++)  // CORRECT
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: Empty and single-element arrays (loop starting at i=1 handles both),
 *    all-equal runs (strict > handles them), and the largest input n=30000
 *    (linear time is trivial). No overflow: max profit <= 30000*10000 = 3e8,
 *    well inside int range (~2.1e9).
 *
 * Q: Are there any type mismatches?
 * A: No - all int. In the space-opt DP, hold seeds to Integer.MIN_VALUE, but
 *    the first cash update keeps hold+price small/negative, never overflowing.
 *    long would be overkill given the constraints.
 *
 * Q: How can I verify this works right now?
 * A: Run with java -ea:
 *    static void verify() {
 *        StockIIGreedy g = new StockIIGreedy();
 *        assert g.maxProfit(new int[]{7,1,5,3,6,4}) == 7;
 *        assert g.maxProfit(new int[]{1,2,3,4,5})    == 4;
 *        assert g.maxProfit(new int[]{7,6,4,3,1})    == 0;
 *        assert g.maxProfit(new int[]{5})            == 0;
 *        assert g.maxProfit(new int[]{})             == 0;
 *        assert g.maxProfit(new int[]{3,3,3,3})      == 0;
 *        System.out.println("All assertions passed.");
 *    }
 * (All four implementations were compiled and run against these inputs plus
 *  [2,4,1]->2 and [1,7,2,3,6,7,6,7]->12; every approach produced identical,
 *  correct results.)
 *
 * | Approach       | Risk                                | Mitigation |
 * |----------------|-------------------------------------|------------|
 * | Brute Force    | Exponential time - TLE past ~20 days| Conceptual grounding only; never submit. |
 * | DP Table       | O(n) extra memory                   | Collapse to space-optimized version. |
 * | DP Space-Opt   | Update order of cash/hold matters   | Update cash before hold; matches two-array version. |
 * | Greedy         | Misread as Stock I; index underflow | Start loop at i=1; sum positive deltas. |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #122 - Difficulty Medium - a perennial staple across the "Best Time
 * to Buy and Sell Stock" family with thousands of reported appearances.
 *
 * | Company        | Frequency | Notes |
 * |----------------|-----------|-------|
 * | Amazon         | *****     | Extremely common; often paired with DP follow-up. |
 * | Meta           | *****     | Frequent phone-screen greedy warm-up. |
 * | Google         | ****      | Lead-in to #123 / #188. |
 * | Microsoft      | ****      | Tests greedy-vs-DP reasoning. |
 * | Bloomberg      | ****      | Recurs in finance-flavored rounds. |
 * | Apple          | ***       | Appears in early-round screens. |
 * | Adobe          | ***       | Greedy insight is the focus. |
 * | Goldman Sachs  | ***       | Finance context fits naturally. |
 * | Uber           | **        | Shows up sporadically. |
 * | Walmart Labs   | **        | Less frequent, still reported. |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach           | Time   | Space | Code Complexity            | Recommended?              |
 * |--------------------|--------|-------|----------------------------|---------------------------|
 * | Brute Force        | O(2^n) | O(n)  | Low code, catastrophic time| [X] Teaching only         |
 * | DP Table           | O(n)   | O(n)  | Moderate                   | [OK] Use for follow-ups   |
 * | DP Space-Optimized | O(n)   | O(1)  | Moderate                   | [OK] Memory-tight choice  |
 * | Greedy             | O(n)   | O(1)  | Minimal                    | [BEST] For this problem   |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use the greedy sum-of-positive-deltas: O(n) time, O(1) space, a few lines.
 * It ties the space-optimized DP on both axes but is simpler. Keep the DP
 * state-machine for follow-ups adding a fee, cooldown, or cap of k txns.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * With UNLIMITED transactions and free same-day rebuying, capture every upward
 * step - the sum of all positive consecutive differences IS the max profit.
 * Gotcha: don't reuse Stock I's single-min/single-spread logic, and start your
 * scan at index 1 to avoid an underflow reading prices[i-1].
 */
// @formatter:on
