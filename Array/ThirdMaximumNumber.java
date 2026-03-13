package Array;

import java.util.Arrays;

public class ThirdMaximumNumber {
    public static void main(String[] args) {
        ThirdMaximumNumber thirdMaximumNumber = new ThirdMaximumNumber();
        System.out.println("ThirdMaximumNumber : " + thirdMaximumNumber.thirdMax(new int[] { 3, 2, 1 }));
        System.out.println("ThirdMaximumNumber : " + thirdMaximumNumber.thirdMax(new int[] { 1, 2 }));
        System.out.println("ThirdMaximumNumber : " + thirdMaximumNumber.thirdMax(new int[] { 2, 2, 3, 1 }));
    }

    /**
     * 
     * https://leetcode.com/problems/third-maximum-number/description/?envType=problem-list-v2&envId=array
     * 
     * 
     * Given an integer array nums, return the third distinct maximum number in this
     * array. If the third maximum does not exist, return the maximum number.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [3,2,1]
     * Output: 1
     * Explanation:
     * The first distinct maximum is 3.
     * The second distinct maximum is 2.
     * The third distinct maximum is 1.
     * Example 2:
     * 
     * Input: nums = [1,2]
     * Output: 2
     * Explanation:
     * The first distinct maximum is 2.
     * The second distinct maximum is 1.
     * The third distinct maximum does not exist, so the maximum (2) is returned
     * instead.
     * Example 3:
     * 
     * Input: nums = [2,2,3,1]
     * Output: 1
     * Explanation:
     * The first distinct maximum is 3.
     * The second distinct maximum is 2 (both 2's are counted together since they
     * have the same value).
     * The third distinct maximum is 1.
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 104
     * -231 <= nums[i] <= 231 - 1
     * 
     * 
     * Follow up: Can you find an O(n) solution?
     * 
     * 
     */

    public int thirdMax(int[] nums) {
        // int[] maxDistinctArray = new int[nums.length];
        // int maxElement = 0;
        // int lastMax = 0;
        // for (int y = 0; y < nums.length; y++) {
        // for (int i = 0; i < nums.length; i++) {
        // if (nums[i] > maxElement && nums[i] != lastMax) {
        // maxElement = nums[i];
        // }
        // }
        // lastMax = maxElement;
        // maxDistinctArray[y] = maxElement;
        // maxElement = 0;
        // }
        // System.out.println("Max Array : " + Arrays.toString(maxDistinctArray));
        // return 0;

        // Long first = null, second = null, third = null;

        // for (int i = 0; i < nums.length; i++) {
        // long x = nums[i];
        // if ((first != null && x == first) || (second != null && x == second) ||
        // (third != null && x == third)) {
        // continue;
        // }

        // if (first == null || x > first) {
        // third = second;
        // second = first;
        // first = x;
        // } else if (second == null || x > second) {
        // third = second;
        // second = x;
        // } else if (third == null || x > third) {
        // third = x;
        // }
        // }

        // return third == null ? first.intValue() : third.intValue();

        int n = nums.length;
        Arrays.sort(nums);
        int current = nums[n - 1];
        int distinct = 1;
        for (int i = n - 2; i >= 0; i--) {
            if (current != nums[i]) {
                distinct++;
                current = nums[i];
            }
            if (distinct == 3) {
                return current;
            }
        }
        return nums[n - 1];
    }
}
