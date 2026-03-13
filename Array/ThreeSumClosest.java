package Array;

import java.util.Arrays;

public class ThreeSumClosest {
    public static void main(String[] args) {
        ThreeSumClosest threeSumClosest = new ThreeSumClosest();
        System.out.println("ThreeSumClosest : " + threeSumClosest.threeSumClosest(new int[] { -1, 2, 1, -4 }, 1));
        System.out.println("ThreeSumClosest : " + threeSumClosest.threeSumClosest(new int[] { 1, 1, 1, 1 }, -100));
    }

    /**
     * 
     * https://leetcode.com/problems/3sum-closest/description/?envType=problem-list-v2&envId=array
     * 
     * 
     * Given an integer array nums of length n and an integer target, find three
     * integers in nums such that the sum is closest to target.
     * 
     * Return the sum of the three integers.
     * 
     * You may assume that each input would have exactly one solution.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [-1,2,1,-4], target = 1
     * Output: 2
     * Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
     * Example 2:
     * 
     * Input: nums = [0,0,0], target = 1
     * Output: 0
     * Explanation: The sum that is closest to the target is 0. (0 + 0 + 0 = 0).
     * 
     * 
     * Constraints:
     * 
     * 3 <= nums.length <= 500
     * -1000 <= nums[i] <= 1000
     * -104 <= target <= 104
     * 
     */

    public int threeSumClosest(int[] nums, int target) {
        int n = nums.length;
        int closestSum = nums[0] + nums[1] + nums[2];
        Arrays.sort(nums);
        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = n - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (Math.abs(sum - target) < Math.abs(closestSum - target)) {
                    closestSum = sum;
                }
                if (sum == target) {
                    return sum;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return closestSum;
    }
}
