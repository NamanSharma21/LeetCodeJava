package Array;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public static void main(String[] args) {
        TwoSum twoSum = new TwoSum();
        // twoSum.twoSum(new int[] { 2, 7, 11, 15 }, 9);
        // twoSum.twoSum(new int[] { 3, 2, 4 }, 6);
        twoSum.twoSum(new int[] { 3, 3 }, 6);
    }

    /*
     * Given an array of integers nums and an integer target, return indices of the
     * two numbers such that they add up to target.
     * 
     * You may assume that each input would have exactly one solution, and you may
     * not use the same element twice.
     * 
     * You can return the answer in any order.
     * Example 1:
     * 
     * Input: nums = [2,7,11,15], target = 9
     * Output: [0,1]
     * Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
     * Example 2:
     * 
     * Input: nums = [3,2,4], target = 6
     * Output: [1,2]
     * Example 3:
     * 
     * Input: nums = [3,3], target = 6
     * Output: [0,1]
     * 
     * 
     * Constraints:
     * 
     * 2 <= nums.length <= 104
     * -109 <= nums[i] <= 109
     * -109 <= target <= 109
     * Only one valid answer exists.
     * 
     * 
     * Follow-up: Can you come up with an algorithm that is less than O(n2) time
     * complexity?
     * 
     */

    public int[] twoSum(int[] nums, int target) {
        // int numArrayLength = nums.length;
        // int[] twoSum = new int[2];
        // for (int i = 0; i < numArrayLength; i++) {
        // int first = nums[i];
        // for (int y = i + 1; y < numArrayLength; y++) {
        // int second = nums[y];
        // if (first + second == target) {
        // twoSum[0] = i;
        // twoSum[1] = y;
        // break;
        // }
        // }
        // }
        // return twoSum;

        Map<Integer, Integer> numMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (numMap.containsKey(target - nums[i])) {
                System.out.println("Found : " + i + " : " + numMap.get(target - nums[i]));
                return new int[] { i, numMap.get(target - nums[i]) };
            }
            numMap.put(nums[i], i);
        }
        return null;
    }
}
