package Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class FourSum {
    public static void main(String[] args) {
        FourSum fourSum = new FourSum();
        // System.out.println("FourSum : " + fourSum.fourSum(new int[] { 1, 0, -1, 0,
        // -2, 2 }, 0));
        // System.out.println("FourSum : " + fourSum.fourSum(new int[] { 2, 2, 2, 2, 2
        // }, 8));
        System.out.println("FourSum : " + fourSum.fourSum(new int[] { -3, -1, 0, 2, 4, 5 }, 2));
    }

    /**
     * 
     * https://leetcode.com/problems/4sum/description/?envType=problem-list-v2&envId=array
     * 
     * 
     * 
     * Given an array nums of n integers, return an array of all the unique
     * quadruplets [nums[a], nums[b], nums[c], nums[d]] such that:
     * 
     * 0 <= a, b, c, d < n
     * a, b, c, and d are distinct.
     * nums[a] + nums[b] + nums[c] + nums[d] == target
     * You may return the answer in any order.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,0,-1,0,-2,2], target = 0
     * Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
     * Example 2:
     * 
     * Input: nums = [2,2,2,2,2], target = 8
     * Output: [[2,2,2,2]]
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 200
     * -109 <= nums[i] <= 109
     * -109 <= target <= 109
     * 
     */

    public List<List<Integer>> fourSum(int[] nums, int target) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        // HashSet<String> keys = new HashSet<>();
        // for (int i = 0; i < n - 3; i++) {
        // int left = i + 1;
        // int right = n - 1;
        // while (left < right) {
        // int sum = nums[i] + nums[left] + nums[right];
        // int diff = target - sum;
        // for (int y = left + 1; y < right; y++) {
        // if (nums[y] == diff) {
        // if (keys.add("" + nums[i] + "_" + nums[left] + "_" + nums[right] + "_" +
        // nums[y])) {
        // result.add(Arrays.asList(nums[i], nums[left], nums[right], nums[y]));
        // System.out.println("" + nums[i] + "_" + nums[left] + "_" + nums[right] + "_"
        // + nums[y]
        // + "---Diff_" + diff);
        // }
        // }
        // }
        // if (sum < target) {
        // left++;
        // } else {
        // right--;
        // }
        // }
        // }

        // for (int i = 0; i < n - 3; i++) {
        // if (i > 0 && nums[i] == nums[i - 1]) {
        // continue;
        // }
        // for (int y = i + 1; y < n - 2; y++) {
        // if (y > i + 1 && nums[y] == nums[y - 1]) {
        // continue;
        // }
        // int left = y + 1;
        // int right = n - 1;
        // long twoTarget = (long) target - nums[i] - nums[y];
        // while (left < right) {
        // long sumLR = (long) nums[left] + nums[right];
        // if (sumLR == twoTarget) {
        // result.add(Arrays.asList(nums[i], nums[y], nums[left], nums[right]));
        // System.out.println("" + nums[i] + "_" + nums[left] + "_" + nums[right] + "_"
        // + nums[y]);
        // left++;
        // right--;
        // while (left < right && nums[left] == nums[left - 1]) {
        // left++;
        // }
        // while (left < right && nums[right] == nums[right + 1]) {
        // right--;
        // }
        // } else if (sumLR < twoTarget) {
        // left++;
        // } else {
        // right--;
        // }
        // }
        // }
        // }
        // return result;

        return kSum(nums, 0, 4, target);
    }

    private List<List<Integer>> kSum(int[] nums, int start, int k, int target) {
        int n = nums.length;
        List<List<Integer>> result = new ArrayList<>();
        if (start == n) {
            return result;
        }

        int minSum = 0;
        for (int i = 0; i < k; i++) {
            if (start + i >= n) {
                return result;
            }
            minSum += nums[start + i];
        }

        if (minSum > target) {
            return result;
        }

        int maxSum = 0;
        for (int i = 0; i < k; i++) {
            if (n - 1 - i < start) {
                return result;
            }
            maxSum += nums[n - 1 - i];
        }
        if (maxSum < target) {
            return result;
        }

        if (k == 2) {
            return twoSum(nums, start, target);
        }

        for (int i = start; i < n; i++) {
            if (i > start && nums[start] == nums[i - 1]) {
                continue;
            }

            for (List<Integer> subset : kSum(nums, i + 1, k - 1, target - nums[i])) {
                List<Integer> quad = new ArrayList<>();
                quad.add(nums[i]);
                quad.addAll(subset);
                result.add(quad);
            }
        }
        return result;
    }

    private List<List<Integer>> twoSum(int[] nums, int start, long target) {
        List<List<Integer>> res = new ArrayList<>();
        int left = start, right = nums.length - 1;

        while (left < right) {
            long sum = (long) nums[left] + nums[right];
            if (sum == target) {
                res.add(Arrays.asList(nums[left], nums[right]));
                left++;
                right--;
                while (left < right && nums[left] == nums[left - 1])
                    left++;
                while (left < right && nums[right] == nums[right + 1])
                    right--;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return res;
    }
}
