package Array;

import java.util.Arrays;

public class FindFirstAndLastPositionOfElementInSortedArray {
    public static void main(String[] args) {
        FindFirstAndLastPositionOfElementInSortedArray firstAndLastPositionOfElementInSortedArray = new FindFirstAndLastPositionOfElementInSortedArray();
        System.out.println("FindFirstAndLastPositionOfElementInSortedArray : "
                + Arrays.toString(
                        firstAndLastPositionOfElementInSortedArray.searchRange(new int[] { 5, 7, 7, 8, 8, 10 }, 8)));

        System.out.println("FindFirstAndLastPositionOfElementInSortedArray : "
                + Arrays.toString(
                        firstAndLastPositionOfElementInSortedArray.searchRange(new int[] { 1 }, 1)));

        System.out.println("FindFirstAndLastPositionOfElementInSortedArray : "
                + Arrays.toString(
                        firstAndLastPositionOfElementInSortedArray.searchRange(new int[] { 1, 4 }, 4)));

        System.out.println("FindFirstAndLastPositionOfElementInSortedArray : "
                + Arrays.toString(
                        firstAndLastPositionOfElementInSortedArray.searchRange(new int[] { 2, 2 }, 2)));
    }

    /**
     * 
     * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/description/?envType=problem-list-v2&envId=array
     * 
     * 
     * 
     * 
     * Given an array of integers nums sorted in non-decreasing order, find the
     * starting and ending position of a given target value.
     * 
     * If target is not found in the array, return [-1, -1].
     * 
     * You must write an algorithm with O(log n) runtime complexity.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [5,7,7,8,8,10], target = 8
     * Output: [3,4]
     * Example 2:
     * 
     * Input: nums = [5,7,7,8,8,10], target = 6
     * Output: [-1,-1]
     * Example 3:
     * 
     * Input: nums = [], target = 0
     * Output: [-1,-1]
     * 
     * 
     * Constraints:
     * 
     * 0 <= nums.length <= 105
     * -109 <= nums[i] <= 109
     * nums is a non-decreasing array.
     * -109 <= target <= 109
     * 
     */

    public int[] searchRange(int[] nums, int target) {
        int n = nums.length;
        int left = 0, right = n - 1;
        // while (left <= right) {
        // int mid = left + (right - left) / 2;
        // if (nums[mid] == target) {
        // if ((mid + 1 <= n - 1) && nums[mid + 1] == target) {
        // return new int[] { mid, mid + 1 };
        // } else {
        // int leftIndex = mid - 1 >= 0 ? mid - 1 : 0;
        // if (nums[leftIndex] == target) {
        // return new int[] { leftIndex, mid };
        // } else {
        // return new int[] { mid, mid };
        // }
        // }
        // }
        // if (nums[mid] < target) {
        // left = mid + 1;
        // } else {
        // right = mid - 1;
        // }
        // }
        // return new int[] { -1, -1 };

        int first = findFirst(nums, target);
        int last = findLast(nums, target);
        return new int[] { first, last };
    }

    private int findLast(int[] nums, int target) {
        int n = nums.length;
        int left = 0, right = n - 1, last = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                last = mid;
                left = mid + 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return last;
    }

    private int findFirst(int[] nums, int target) {
        int n = nums.length;
        int left = 0, right = n - 1, first = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                first = mid;
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return first;
    }
}
