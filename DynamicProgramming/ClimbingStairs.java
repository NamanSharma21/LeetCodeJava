package DynamicProgramming;

import java.util.HashMap;
import java.util.Map;

public class ClimbingStairs {
    public static void main(String[] args) {
        ClimbingStairs climbingStairs = new ClimbingStairs();
        System.out.println("No Of Ways : " + climbingStairs.climbStairsTabulation(3));
        System.out.println("No Of Ways : " + climbingStairs.climbingStairsMemoization(3));
    }

    /*
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/97/
     * dynamic-programming/569/
     * 
     * 
     * You are climbing a staircase. It takes n steps to reach the top.
     * 
     * Each time you can either climb 1 or 2 steps. In how many distinct ways can
     * you climb to the top?
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: n = 2
     * Output: 2
     * Explanation: There are two ways to climb to the top.
     * 1. 1 step + 1 step
     * 2. 2 steps
     * Example 2:
     * 
     * Input: n = 3
     * Output: 3
     * Explanation: There are three ways to climb to the top.
     * 1. 1 step + 1 step + 1 step
     * 2. 1 step + 2 steps
     * 3. 2 steps + 1 step
     * 
     * 
     * Constraints:
     * 
     * 1 <= n <= 45
     */

    public int climbStairsTabulation(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        int[] table = new int[n + 1];
        table[0] = 1;
        table[1] = 1;
        for (int i = 2; i <= n; i++) {
            table[i] = table[i - 1] + table[i - 2];
        }
        return table[n];
    }

    public int climbingStairsMemoization(int n) {
        Map<Integer, Integer> memo = new HashMap<>();
        return climbingStairsMemoization(n, memo);
    }

    public int climbingStairsMemoization(int n, Map<Integer, Integer> memo) {
        if (n == 0 || n == 1) {
            return 1;
        }

        if (!memo.containsKey(n)) {
            memo.put(n, climbingStairsMemoization(n - 1, memo) + climbingStairsMemoization(n - 2, memo));
        }

        return memo.get(n);
    }
}
