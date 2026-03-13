package DynamicProgramming;

public class MaximumSubarray {
    public static void main(String[] args) {
        MaximumSubarray maximumSubarray = new MaximumSubarray();
        System.out.println("Max Sum : " + maximumSubarray.maxSubArray(new int[] { -2, 1, -3, 4, -1, 2, 1, -5, 4 }));
        System.out.println("Max Sum : " + maximumSubarray.maxSubArray(new int[] { 5, 4, -1, 7, 8 }));
        System.out.println("Max Sum : " + maximumSubarray.maxSubArray(new int[] { -2, -1 }));
    }

    /*
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/97/
     * dynamic-programming/566/
     * 
     * Given an integer array nums, find the subarray with the largest sum, and
     * return its sum.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
     * Output: 6
     * Explanation: The subarray [4,-1,2,1] has the largest sum 6.
     * Example 2:
     * 
     * Input: nums = [1]
     * Output: 1
     * Explanation: The subarray [1] has the largest sum 1.
     * Example 3:
     * 
     * Input: nums = [5,4,-1,7,8]
     * Output: 23
     * Explanation: The subarray [5,4,-1,7,8] has the largest sum 23.
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 105
     * -104 <= nums[i] <= 104
     * 
     * 
     * Follow up: If you have figured out the O(n) solution, try coding another
     * solution using the divide and conquer approach, which is more subtle.
     */

    public int maxSubArray(int[] nums) {
        // int arrayLength = nums.length;
        // if (arrayLength == 1) {
        // return nums[0];
        // }
        // int start = 0;
        // int end = start + 1;
        // int totalSum = nums[0];
        // int maxSum = 0;

        // int maxSumStart = 0;
        // int maxSumEnd = 0;

        // while (start < arrayLength && end < arrayLength) {
        // System.out.println("Start : " + start + " End : " + end + " S : " +
        // nums[start] + " E : " + nums[end]
        // + " T : " + (totalSum + nums[end]) + " Total Sum : " + totalSum + " Max Sum :
        // " + maxSum
        // + " MS : "
        // + maxSumStart + " ME : " + maxSumEnd);
        // if (totalSum + nums[end] > 0) {
        // totalSum += nums[end];
        // end++;
        // } else {
        // start++;
        // totalSum = nums[start];
        // end = start + 1;
        // }
        // if (totalSum > maxSum) {
        // maxSum = totalSum;
        // maxSumStart = start;
        // maxSumEnd = end;
        // }
        // }
        // return maxSum;

        // DP

        // int[] dp = new int[nums.length];
        // dp[0] = nums[0];
        // int max = dp[0];

        // for (int i = 1; i < nums.length; i++) {
        // dp[i] = nums[i] + (dp[i - 1] > 0 ? dp[i - 1] : 0);
        // max = Math.max(max, dp[i]);
        // }
        // return max;

        int sum = 0;
        int max = nums[0];

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            max = Math.max(sum,max);
            if (sum < 0) {
                sum = 0;
            }
        }
        return max;
    }
}
