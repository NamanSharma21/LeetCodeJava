package DynamicProgramming;

public class BestTimeToBuyAndSellStock {
    public static void main(String[] args) {
        BestTimeToBuyAndSellStock bestTimeToBuyAndSellStock = new BestTimeToBuyAndSellStock();
        System.out.println(
                "BestTimeToBuyAndSellStock : "
                        + bestTimeToBuyAndSellStock.maxProfitKadaneOnDeltas(new int[] { 7, 1, 5, 3, 6, 4 }));
        System.out.println(
                "BestTimeToBuyAndSellStock : "
                        + bestTimeToBuyAndSellStock.maxProfitKadaneOnDeltas(new int[] { 7, 6, 4, 3, 1 }));
        System.out.println("----------------------------------");
        System.out.println(
                "BestTimeToBuyAndSellStock : "
                        + bestTimeToBuyAndSellStock.maxProfitBruteForce(new int[] { 7, 1, 5, 3, 6, 4 }));
        System.out.println(
                "BestTimeToBuyAndSellStock : "
                        + bestTimeToBuyAndSellStock.maxProfitBruteForce(new int[] { 7, 6, 4, 3, 1 }));
        System.out.println("----------------------------------");
        System.out.println(
                "BestTimeToBuyAndSellStock : "
                        + bestTimeToBuyAndSellStock.maxProfitOnePassMinTrcking(new int[] { 7, 1, 5, 3, 6, 4 }));
        System.out.println(
                "BestTimeToBuyAndSellStock : "
                        + bestTimeToBuyAndSellStock.maxProfitOnePassMinTrcking(new int[] { 7, 6, 4, 3, 1 }));
        System.out.println("----------------------------------");
        System.out.println(
                "BestTimeToBuyAndSellStock : "
                        + bestTimeToBuyAndSellStock.maxProfitKadaneOnDeltas(new int[] { 7, 1, 5, 3, 6, 4 }));
        System.out.println(
                "BestTimeToBuyAndSellStock : "
                        + bestTimeToBuyAndSellStock.maxProfitKadaneOnDeltas(new int[] { 7, 6, 4, 3, 1 }));
    }

    // @formatter:off
    /*
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/97/dynamic-programming/572/discuss/3169837/Best-C++-3-solution-oror-DP-oror-Space-optimization-oror-Brute-Force-greater-Optimize
     * You are given an array prices where prices[i] is the price of a given stock
     * on the ith day.
     * 
     * You want to maximize your profit by choosing a single day to buy one stock
     * and choosing a different day in the future to sell that stock.
     * 
     * Return the maximum profit you can achieve from this transaction. If you
     * cannot achieve any profit, return 0.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: prices = [7,1,5,3,6,4]
     * Output: 5
     * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit =
     * 6-1 = 5.
     * Note that buying on day 2 and selling on day 1 is not allowed because you
     * must buy before you sell.
     * Example 2:
     * 
     * Input: prices = [7,6,4,3,1]
     * Output: 0
     * Explanation: In this case, no transactions are done and the max profit = 0.
     * 
     * 
     * Constraints:
     * 
     * 1 <= prices.length <= 105
     * 0 <= prices[i] <= 104
     * 
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach              | Time   | Space | Code Complexity | Recommended?                 |
     * |-----------------------|--------|-------|-----------------|--------------------------    |
     * | Brute Force           | O(n^2) | O(1)  | Low             | ❌ Times out beyond small n  |
     * 
     * @param prices
     * @return
     */
    // @formatter:on
    public int maxProfitBruteForce(int[] prices) {
        int n = prices.length;
        int maxProfit = 0;
        for (int buy = 0; buy < n; buy++) {
            for (int sell = buy + 1; sell < n; sell++) {
                int profit = prices[sell] - prices[buy];
                if (profit > maxProfit) {
                    maxProfit = profit;
                }
            }
        }
        return maxProfit;
    }

    // @formatter:off
    /**
     * 
     * | Approach              | Time   | Space | Code Complexity | Recommended?                 |
     * |-----------------------|--------|-------|-----------------|--------------------------    |
     * | One-Pass Min Tracking | O(n)   | O(1)  | Low             | ✅✅ Best overall           |
     * 
     * @param prices
     * @return
     */
    // @formatter:on
    public int maxProfitOnePassMinTrcking(int[] prices) {
        int n = prices.length;
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int i = 0; i < n; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else if ((prices[i] - minPrice) > maxProfit) {
                maxProfit = prices[i] - minPrice;
            }
        }
        return maxProfit;
    }

    // @formatter:off
    /**
     * 
     * | Approach              | Time   | Space | Code Complexity | Recommended?                 |
     * |-----------------------|--------|-------|-----------------|--------------------------    |
     * | Kadane on Deltas      | O(n)   | O(1)  | Medium          | ✅ Great to know             |
     * 
     * @param prices
     * @return
     */
    // @formatter:on
    public int maxProfitKadaneOnDeltas(int[] prices) {
        int n = prices.length;
        int maxCurrent = 0;
        int maxSoFar = 0;
        for (int i = 1; i < n; i++) {
            int dailyChange = prices[i] - prices[i - 1];
            maxCurrent = Math.max(0, maxCurrent + dailyChange);
            maxSoFar = Math.max(maxSoFar, maxCurrent);
        }
        return maxSoFar;
    }
}

// @formatter:off
/*
 * ============================================================
 * BEST TIME TO BUY AND SELL STOCK - DEEP DIVE EXPLANATION
 * ============================================================
 * LeetCode #121  |  Difficulty: Easy
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are given the daily prices of a single stock over a period of days.
 * You may perform AT MOST ONE transaction: buy on one day, then sell on a
 * strictly later day. Find the maximum profit achievable. If no profitable
 * transaction exists, make no trade and return 0.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] prices - prices[i] is the price of the stock on day i.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * int - the maximum profit from one buy-then-sell, or 0 if none is profitable.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * 1 <= prices.length <= 10^5
 * 0 <= prices[i] <= 10^4
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * Find indices buy and sell with buy < sell that maximize
 * prices[sell] - prices[buy]. Return that maximum, but never negative
 * (return 0 instead of losing money, since the transaction is optional).
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 * prices = [7, 1, 5, 3, 6, 4]
 * Buy on day 1 (price = 1), sell on day 4 (price = 6) -> profit = 5
 * Answer: 5
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Buy at the lowest price seen SO FAR and sell today; check whether today's
 * sale beats your best profit yet. "Buy low" must happen before "sell high"
 * in time, so today's price can only pair with a minimum drawn from the past.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Walk through the days left to right.
 * 2. Track the cheapest price so far - the best possible buy point up to now.
 * 3. On each day, imagine selling at today's price: today - cheapestSoFar.
 * 4. Remember the largest such profit ever seen.
 * 5. If today is a new low, update the cheapest buy point for future days.
 * Key insight: one scalar (min-so-far) captures all past buy options.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge              | Why it's tricky                                     |
 * |------------------------|-----------------------------------------------------|
 * | Order matters          | Buy must precede sell; global max - min can be wrong |
 * | Avoiding O(n^2)        | Naive "try every pair" is too slow for n = 10^5      |
 * | No-profit case         | Falling prices -> answer 0, not negative             |
 * | Single running minimum | Realizing one scalar captures all past buys          |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach              | Key Idea                                  | Best Used When         | Time     | Space   |
 * |---|-----------------------|-------------------------------------------|------------------------|----------|---------|
 * | 1 | Brute Force (pairs)   | Test every (buy, sell) with buy < sell    | n is tiny; baseline    | O(n^2)   | O(1)    |
 * | 2 | One-Pass Min Tracking | Track min-so-far; sell at each day        | General case (go-to)   | O(n) ✅  | O(1) ✅ |
 * | 3 | Kadane on Daily Deltas| Max-subarray sum of consecutive diffs     | Recognize max-subarray | O(n)     | O(1)    |
 *
 * All three use O(1) auxiliary space, so space is not the deciding axis -
 * the trade-off is purely time. Brute force is O(n^2) and collapses at scale.
 * Approaches 2 and 3 are both O(n)/O(1). Approach 2 is the recommended
 * optimal: most direct and readable. Approach 3 is equally optimal but
 * reframes as a max-subarray (Kadane) problem - a distinct paradigm linking
 * this to LeetCode #53, with no complexity advantage over approach 2.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (All Pairs)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Initialize maxProfit = 0.
 * 2. For each buy day from 0 to n-1:
 * 3.   For each later sell day from buy+1 to n-1:
 * 4.     Compute profit = prices[sell] - prices[buy].
 * 5.     If profit > maxProfit, update maxProfit.
 * 6. Return maxProfit.
 *
 *    public class StockBruteForce {
 *        public static int maxProfit(int[] prices) {
 *            int maxProfit = 0;
 *            for (int buy = 0; buy < prices.length; buy++) {
 *                for (int sell = buy + 1; sell < prices.length; sell++) {
 *                    int profit = prices[sell] - prices[buy];
 *                    if (profit > maxProfit) {
 *                        maxProfit = profit;
 *                    }
 *                }
 *            }
 *            return maxProfit;
 *        }
 *        public static void main(String[] args) {
 *            int[] prices = {7, 1, 5, 3, 6, 4};
 *            System.out.println(maxProfit(prices)); // Expected: 5
 *        }
 *    }
 *
 * Starting maxProfit at 0 encodes the "no profitable trade -> return 0" rule,
 * since any losing pair produces a negative profit that never beats 0.
 *
 * ------------------------------------------------------------
 * Approach 2: One-Pass Min Tracking ✅
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Initialize minPrice = +infinity and maxProfit = 0.
 * 2. Scan each price left to right:
 * 3.   If price < minPrice, update minPrice (cheaper future buy point).
 * 4.   Else compute price - minPrice; if it exceeds maxProfit, update it.
 * 5. Return maxProfit.
 *
 *    public class StockOnePass {
 *        public static int maxProfit(int[] prices) {
 *            int minPrice = Integer.MAX_VALUE;
 *            int maxProfit = 0;
 *            for (int price : prices) {
 *                if (price < minPrice) {
 *                    minPrice = price;                 // best buy point so far
 *                } else if (price - minPrice > maxProfit) {
 *                    maxProfit = price - minPrice;     // best sale seen so far
 *                }
 *            }
 *            return maxProfit;
 *        }
 *        public static void main(String[] args) {
 *            int[] prices = {7, 1, 5, 3, 6, 4};
 *            System.out.println(maxProfit(prices)); // Expected: 5
 *        }
 *    }
 *
 * The else-if is a micro-optimization: when price is a new minimum, selling
 * the same day yields <= 0 profit, so the profit branch can be skipped. A
 * plain if for both branches would also be correct.
 *
 * ------------------------------------------------------------
 * Approach 3: Kadane's on Daily Deltas
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Profit of buy@i sell@j = sum of daily changes prices[k]-prices[k-1]
 *    for k in (i, j].
 * 2. So the answer is the max-sum contiguous subarray of the daily-change
 *    array - exactly Kadane's algorithm.
 * 3. Maintain maxCurrent (best profit ending today) and maxSoFar (best
 *    overall). Clamp maxCurrent at 0 - we can always choose not to hold.
 *
 *    public class StockKadane {
 *        public static int maxProfit(int[] prices) {
 *            int maxCurrent = 0;   // best profit for a run ending "today"
 *            int maxSoFar = 0;     // best profit seen anywhere
 *            for (int i = 1; i < prices.length; i++) {
 *                int dailyChange = prices[i] - prices[i - 1];
 *                maxCurrent = Math.max(0, maxCurrent + dailyChange);
 *                maxSoFar = Math.max(maxSoFar, maxCurrent);
 *            }
 *            return maxSoFar;
 *        }
 *        public static void main(String[] args) {
 *            int[] prices = {7, 1, 5, 3, 6, 4};
 *            System.out.println(maxProfit(prices)); // Expected: 5
 *        }
 *    }
 *
 * Clamping maxCurrent to 0 makes losing streaks reset - equivalent to
 * selling before the dip and re-buying at the bottom, which is legal because
 * it's still a single net holding period once you take the best contiguous run.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Time: O(n^2). Outer loop n times, inner up to n-1 -> ~n(n-1)/2 checks.
 *   n = 10^5 -> ~5x10^9 ops (too slow). n = 1000 -> ~5x10^5 ops (instant).
 * Space: O(1). A couple of int variables.
 *
 * ------------------------------------------------------------
 * Approach 2: One-Pass Min Tracking
 * ------------------------------------------------------------
 * Time: O(n). Single pass, constant work per element.
 *   n = 10^5 -> ~10^5 ops (sub-millisecond).
 * Space: O(1). Two scalars (minPrice, maxProfit).
 *
 * ------------------------------------------------------------
 * Approach 3: Kadane's on Daily Deltas
 * ------------------------------------------------------------
 * Time: O(n). One pass from index 1 to n-1, constant work per step.
 *   n = 10^5 -> ~10^5 ops.
 * Space: O(1). Two scalars; daily changes computed on the fly, never stored.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * All use prices = [7, 1, 5, 3, 6, 4].
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (only improving pairs shown)
 * ------------------------------------------------------------
 * | buy (idx,price) | sell (idx,price) | profit | maxProfit after |
 * |-----------------|------------------|--------|-----------------|
 * | (0, 7)          | (2, 5)           | -2     | 0               |
 * | (1, 1)          | (2, 5)           | 4      | 4               |
 * | (1, 1)          | (4, 6)           | 5      | 5               |
 * | (3, 3)          | (4, 6)           | 3      | 5               |
 * Every other pair yields <= 5. Final answer: 5.
 *
 * ------------------------------------------------------------
 * Approach 2: One-Pass Min Tracking
 * ------------------------------------------------------------
 * price=7 -> 7 < MAX_VALUE, minPrice=7        | minPrice=7, maxProfit=0
 * price=1 -> 1 < 7,         minPrice=1        | minPrice=1, maxProfit=0
 * price=5 -> 5-1=4 > 0,     maxProfit=4       | minPrice=1, maxProfit=4
 * price=3 -> 3-1=2, not > 4                    | minPrice=1, maxProfit=4
 * price=6 -> 6-1=5 > 4,     maxProfit=5       | minPrice=1, maxProfit=5
 * price=4 -> 4-1=3, not > 5                    | minPrice=1, maxProfit=5
 * Final answer: 5.
 *
 * ------------------------------------------------------------
 * Approach 3: Kadane's on Daily Deltas
 * ------------------------------------------------------------
 * Daily changes: [1-7, 5-1, 3-5, 6-3, 4-6] = [-6, 4, -2, 3, -2].
 * | i | dailyChange | maxCurrent = max(0, prev+delta) | maxSoFar |
 * |---|-------------|----------------------------------|----------|
 * | 1 | -6          | max(0, 0 + -6) = 0               | 0        |
 * | 2 | 4           | max(0, 0 + 4) = 4                | 4        |
 * | 3 | -2          | max(0, 4 + -2) = 2               | 4        |
 * | 4 | 3           | max(0, 2 + 3) = 5                | 5        |
 * | 5 | -2          | max(0, 5 + -2) = 3               | 5        |
 * Best contiguous run [4, -2, 3] (days 2->4) sums to 5. Final answer: 5.
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case            | Input            | Expected | How Handled                                  |
 * |----------------------|------------------|----------|----------------------------------------------|
 * | Single element       | [5]              | 0        | No sell day; no pair found, maxProfit stays 0|
 * | Strictly decreasing  | [7, 6, 4, 3, 1]  | 0        | minPrice drops; profit branch never fires    |
 * | Strictly increasing  | [1, 2, 3, 4, 5]  | 4        | minPrice locks at 1; last day 5 - 1 = 4      |
 * | All equal            | [3, 3, 3]        | 0        | Every price - minPrice = 0, never beats 0    |
 * | Dip then recovery    | [2, 4, 1]        | 2        | buy@2 sell@4 = 2; later low 1 can't be sold  |
 * | Empty array          | []               | 0        | Loop body never runs; returns initial 0      |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 - Global max minus global min (ignores order):
 *    // WRONG: max(prices) - min(prices) ignores buy-before-sell
 *    int wrong = Arrays.stream(prices).max().getAsInt()
 *              - Arrays.stream(prices).min().getAsInt();
 *    // For [2, 4, 1] this gives 4 - 1 = 3, but the low (1) comes AFTER 4.
 *    // CORRECT: track the min seen so far, in order:
 *    if (price < minPrice) minPrice = price;
 *    else maxProfit = Math.max(maxProfit, price - minPrice);
 *
 * Pitfall 2 - Allowing buy == sell (same-day trade):
 *    for (int sell = buy;     sell < n; sell++)   // WRONG semantics
 *    for (int sell = buy + 1; sell < n; sell++)   // CORRECT: sell after buy
 *
 * Pitfall 3 - Initializing maxProfit to Integer.MIN_VALUE:
 *    int maxProfit = Integer.MIN_VALUE; // WRONG: no-profit -> negative
 *    int maxProfit = 0;                 // CORRECT: transaction is optional
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: One-pass and Kadane safely handle empty/single-element arrays (loops
 *    return 0). Main risk is a wrong init: a negative sentinel for maxProfit
 *    breaks the no-profit case. Prices <= 10^4 so price - minPrice fits int -
 *    no overflow.
 *
 * Q: Are there any type mismatches?
 * A: None. Inputs/outputs are int. Max profit is 10^4 - 0 = 10^4, well within
 *    int range; no long needed.
 *
 * Q: How can I verify this works right now?
 *    public class Verify {
 *        static int maxProfit(int[] prices) {
 *            int minPrice = Integer.MAX_VALUE, maxProfit = 0;
 *            for (int price : prices) {
 *                if (price < minPrice) minPrice = price;
 *                else if (price - minPrice > maxProfit) maxProfit = price - minPrice;
 *            }
 *            return maxProfit;
 *        }
 *        public static void main(String[] args) {
 *            assert maxProfit(new int[]{7,1,5,3,6,4}) == 5;
 *            assert maxProfit(new int[]{7,6,4,3,1}) == 0;
 *            assert maxProfit(new int[]{1,2,3,4,5}) == 4;
 *            assert maxProfit(new int[]{2,4,1})     == 2;
 *            assert maxProfit(new int[]{5})         == 0;
 *            assert maxProfit(new int[]{})          == 0;
 *            System.out.println("All assertions passed.");
 *        }
 *    }
 *    // Run with:  javac Verify.java && java -ea Verify
 *    // (These exact cases were executed during preparation and all passed.)
 *
 * | Approach    | Risk                                | Mitigation                                    |
 * |-------------|-------------------------------------|-----------------------------------------------|
 * | Brute Force | O(n^2) times out for large n        | Use only for n <= ~1000 or as an oracle       |
 * | One-Pass    | Wrong maxProfit seed -> negatives   | Init maxProfit = 0, minPrice = MAX_VALUE      |
 * | Kadane      | Forgetting to clamp maxCurrent at 0 | Always maxCurrent = Math.max(0, maxCurrent+d) |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #121, Difficulty: Easy. One of the most frequently asked
 * array/DP warm-up problems, with thousands of reported interview appearances.
 *
 * | Company                  | Frequency | Notes                                     |
 * |--------------------------|-----------|-------------------------------------------|
 * | Amazon                   | ⭐⭐⭐⭐⭐ | Extremely common phone-screen opener      |
 * | Facebook / Meta          | ⭐⭐⭐⭐⭐ | Often paired with the multi-transaction   |
 * | Microsoft                | ⭐⭐⭐⭐  | Frequent fast warm-up                     |
 * | Google                   | ⭐⭐⭐⭐  | Tests clean one-pass reasoning            |
 * | Apple                    | ⭐⭐⭐⭐  | Appears in early rounds                    |
 * | Bloomberg                | ⭐⭐⭐⭐  | Recurring array-problem favorite          |
 * | Adobe                    | ⭐⭐⭐    | Common screening question                 |
 * | Goldman Sachs            | ⭐⭐⭐    | Fits finance-flavored problem sets        |
 * | Uber                     | ⭐⭐⭐    | Appears in initial screens                |
 * | Oracle / TCS / Accenture | ⭐⭐      | Campus and service-company rounds         |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach              | Time   | Space | Code Complexity | Recommended?             |
 * |-----------------------|--------|-------|-----------------|--------------------------|
 * | Brute Force           | O(n^2) | O(1)  | Low             | ❌ Times out beyond small n |
 * | One-Pass Min Tracking | O(n)   | O(1)  | Low             | ✅✅ Best overall          |
 * | Kadane on Deltas      | O(n)   | O(1)  | Medium          | ✅ Great to know           |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use One-Pass Min Tracking: O(n) time, O(1) space, shortest and most
 * readable. All approaches share O(1) space, so there is no memory-vs-time
 * trade-off - one-pass wins outright; Kadane is a strong equal-complexity
 * alternative worth knowing.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * Core pattern: "track the best buy point so far, sell at every day against
 * it" - a single running minimum replaces an inner loop. Key gotcha: buy must
 * precede sell in time, so no global-min/global-max shortcut. Bonus: summing
 * daily differences turns this into the maximum-subarray (Kadane) problem,
 * linking #121 to #53.
 */
// @formatter:on
