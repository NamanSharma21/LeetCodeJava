package DynamicProgramming;

import java.util.Arrays;

public class HouseRobber {
    public static void main(String[] args) {
        HouseRobber houseRobber = new HouseRobber();
        System.out.println("Total Amount You Can Rob : " + houseRobber.rob(new int[] { 1, 2, 3, 1 }));
        System.out.println("Total Amount You Can Rob : " + houseRobber.rob(new int[] { 2, 7, 9, 3, 1 }));
        System.out.println("Total Amount You Can Rob : " + houseRobber.rob(new int[] { 1, 2 }));
        System.out.println("Total Amount You Can Rob : " + houseRobber.rob(new int[] { 1, 3, 1 }));
        System.out.println("Total Amount You Can Rob : " + houseRobber.rob(new int[] { 1, 2, 1, 1 }));
        System.out.println("Total Amount You Can Rob : " + houseRobber.rob(new int[] { 2, 1, 1, 2 }));
    }

    /*
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/97/
     * dynamic-programming/576/
     * 
     * You are a professional robber planning to rob houses along a street. Each
     * house has a certain amount of money stashed, the only constraint stopping you
     * from robbing each of them is that adjacent houses have security systems
     * connected and it will automatically contact the police if two adjacent houses
     * were broken into on the same night.
     * 
     * Given an integer array nums representing the amount of money of each house,
     * return the maximum amount of money you can rob tonight without alerting the
     * police.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,2,3,1]
     * Output: 4
     * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
     * Total amount you can rob = 1 + 3 = 4.
     * Example 2:
     * 
     * Input: nums = [2,7,9,3,1]
     * Output: 12
     * Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5
     * (money = 1).
     * Total amount you can rob = 2 + 9 + 1 = 12.
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 400
     * 
     * Solution :
     * 
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/97/
     * dynamic-programming/576/discuss/156523/From-good-to-great.-How-to-approach-
     * most-of-DP-problems.
     * 
     */

    public int rob(int[] nums) {
        // if (nums.length < 2) {
        // return nums[0];
        // }
        // if (nums.length == 2) {
        // return nums[0] > nums[1] ? nums[0] : nums[1];
        // }
        // int counter = 0;
        // int totalAmount = 0;
        // while (counter < nums.length) {
        // totalAmount += nums[counter];
        // System.out.println("Total Amount : " + totalAmount + " Counter : " +
        // counter);
        // counter += 2;
        // }

        // int maxOfArray = nums[0];
        // for (int i = 1; i < nums.length; i++) {
        // if (nums[i] > maxOfArray) {
        // maxOfArray = nums[i];
        // }
        // }

        // System.out.println("Max : " + maxOfArray);
        // return totalAmount > maxOfArray ? totalAmount : maxOfArray;

        // int counter = 0;
        // int dp[] = new int[nums.length];
        // int maxAmount = 0;
        // while (counter < nums.length) {
        // int innerCounter = counter;
        // int totalAmount = 0;
        // while (innerCounter < nums.length) {
        // totalAmount += nums[innerCounter];
        // innerCounter += 2;
        // }
        // dp[counter] = totalAmount;
        // maxAmount = Math.max(maxAmount, totalAmount);
        // counter++;
        // }

        // System.out.println("Array : " + Arrays.toString(nums) + " DP Array : " +
        // Arrays.toString(dp) + " Max Amount : "
        // + maxAmount);
        // return maxAmount;

        // Reccursion
        // return robHelper(nums, nums.length - 1);

        int[] memo = new int[nums.length + 1];
        memo[0] = 0;
        memo[1] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int val = nums[i];
            memo[i + 1] = Math.max(memo[i], memo[i - 1] + val);
        }
        return memo[nums.length];
    }

    public int robHelper(int[] nums, int i) {
        if (i < 0) {
            return 0;
        }
        return Math.max(robHelper(nums, i - 2) + nums[i], robHelper(nums, i - 1));
    }
}
