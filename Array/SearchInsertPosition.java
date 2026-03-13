package Array;

import java.util.Arrays;

public class SearchInsertPosition {
    public static void main(String[] args) {
        SearchInsertPosition searchInsertPosition = new SearchInsertPosition();
        System.out.println("SearchInsertPosition : " + searchInsertPosition.searchInsert(new int[] { 1, 3, 5, 6 }, 5));
        System.out.println("SearchInsertPosition : " + searchInsertPosition.searchInsert(new int[] { 1, 3, 5, 6 }, 2));
        System.out.println("SearchInsertPosition : " + searchInsertPosition.searchInsert(new int[] { 1, 3, 5, 6 }, 7));
    }

    /*
     * 
     * https://leetcode.com/problems/search-insert-position/description/?envType=
     * problem-list-v2&envId=array
     * 
     * 
     * Given a sorted array of distinct integers and a target value, return the
     * index if the target is found. If not, return the index where it would be if
     * it were inserted in order.
     * 
     * You must write an algorithm with O(log n) runtime complexity.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,3,5,6], target = 5
     * Output: 2
     * Example 2:
     * 
     * Input: nums = [1,3,5,6], target = 2
     * Output: 1
     * Example 3:
     * 
     * Input: nums = [1,3,5,6], target = 7
     * Output: 4
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 104
     * -104 <= nums[i] <= 104
     * nums contains distinct values sorted in ascending order.
     * -104 <= target <= 104
     * 
     */
    public int searchInsert(int[] nums, int target) {
        int arrayLength = nums.length;
        int low = 0, high = arrayLength - 1;
        System.out.println("" + Arrays.toString(nums));
        while (low <= high) {
            int middle = low + (high - low) / 2;
            System.out.println("L : " + low + " H : " + high + " M : " + middle);
            if (target < nums[middle]) {
                high = middle - 1;
            } else if (target > nums[middle]) {
                low = middle + 1;
            } else {
                return middle;
            }
        }
        return low;
    }
}
