package DynamicProgramming;

import java.util.Arrays;

public class BestTimeToBuyAndSellStock {
    public static void main(String[] args) {
        BestTimeToBuyAndSellStock bestTimeToBuyAndSellStock = new BestTimeToBuyAndSellStock();
        bestTimeToBuyAndSellStock.maxProfit(new int[] { 7, 1, 5, 3, 6, 4 });
        // bestTimeToBuyAndSellStock.maxProfit(new int[] { 7, 6, 4, 3, 1 });
    }

    /*
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
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/97/
     * dynamic-programming/572/discuss/3169837/Best-C++-3-solution-oror-DP-oror-
     * Space-optimization-oror-Brute-Force-greater-Optimize.
     */

    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[] maxPrices = new int[n];
        maxPrices[n - 1] = prices[n - 1];
        System.out.println("" + Arrays.toString(prices));
        for (int i = n - 2; i >= 0; i--) {
            System.out.println("Max Prices : " + maxPrices[i + 1] + " Prices : " + prices[i] + " I : " + i);
            maxPrices[i] = Math.max(maxPrices[i + 1], prices[i]);
        }

        System.out.println("" + Arrays.toString(maxPrices));

        int maxProfit = 0;
        for (int i = 0; i < n; i++) {
            maxProfit = Math.max(maxProfit, maxPrices[i] - prices[i]);
        }
        System.out.println("Max Profit : " + maxProfit);
        return maxProfit;
    }
}
