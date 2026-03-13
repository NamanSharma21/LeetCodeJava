package Array;

import java.util.ArrayList;
import java.util.List;

public class FindAllNumbersDisappearedInAnArray {
    public static void main(String[] args) {
        FindAllNumbersDisappearedInAnArray findAllNumbersDisappearedInAnArray = new FindAllNumbersDisappearedInAnArray();
        System.out.println(" : "
                + findAllNumbersDisappearedInAnArray.findDisappearedNumbers(new int[] { 4, 3, 2, 7, 8, 2, 3, 1 }));
        System.out.println(" : "
                + findAllNumbersDisappearedInAnArray.findDisappearedNumbers(new int[] { 2, 2 }));

    }

    /**
     * 
     * https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/description/?envType=problem-list-v2&envId=array
     * 
     * Given an array nums of n integers where nums[i] is in the range [1, n],
     * return an array of all the integers in the range [1, n] that do not appear in
     * nums.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [4,3,2,7,8,2,3,1]
     * Output: [5,6]
     * Example 2:
     * 
     * Input: nums = [1,1]
     * Output: [2]
     * 
     * 
     * Constraints:
     * 
     * n == nums.length
     * 1 <= n <= 105
     * 1 <= nums[i] <= n
     * 
     * 
     * Follow up: Could you do it without extra space and in O(n) runtime? You may
     * assume the returned list does not count as extra space.
     * 
     */

    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> dissappearedIntegers = new ArrayList<>();
        int n = nums.length;
        boolean[] isPresent = new boolean[n + 1];
        for (int num : nums) {
            isPresent[num] = true;
        }

        for (int i = 1; i <= n; i++) {
            if (!isPresent[i]) {
                dissappearedIntegers.add(i);
            }
        }

        return dissappearedIntegers;
    }
}
