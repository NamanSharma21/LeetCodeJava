package Array;

import java.util.HashMap;
import java.util.Map;

public class ContainsDuplicateII {
    public static void main(String[] args) {
        ContainsDuplicateII containsDuplicateII = new ContainsDuplicateII();
        System.out.println("" + containsDuplicateII.containsNearbyDuplicate(new int[] { 1, 2, 3, 1 }, 3));
        System.out.println("" + containsDuplicateII.containsNearbyDuplicate(new int[] { 1, 0, 1, 1 }, 1));
        System.out.println("" + containsDuplicateII.containsNearbyDuplicate(new int[] { 1, 2, 3, 1, 2, 3 }, 2));
    }

    /**
     * 
     * https://leetcode.com/problems/contains-duplicate-ii/description/?envType=problem-list-v2&envId=array
     * 
     * Given an integer array nums and an integer k, return true if there are two
     * distinct indices i and j in the array such that nums[i] == nums[j] and abs(i
     * - j) <= k.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,2,3,1], k = 3
     * Output: true
     * Example 2:
     * 
     * Input: nums = [1,0,1,1], k = 1
     * Output: true
     * Example 3:
     * 
     * Input: nums = [1,2,3,1,2,3], k = 2
     * Output: false
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 105
     * -109 <= nums[i] <= 109
     * 0 <= k <= 105
     * 
     */

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        // for (int i = 0; i < nums.length; i++) {
        // int first = nums[i];
        // for (int y = i + 1; y < nums.length; y++) {
        // int second = nums[y];
        // if (first == second) {
        // if (Math.abs(i - y) <= k) {
        // return true;
        // }
        // }
        // }
        // }
        // return false;

        Map<Integer, Integer> lastSeen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (lastSeen.containsKey(nums[i]) && i - lastSeen.get(nums[i]) <= k) {
                return true;
            }
            lastSeen.put(nums[i], i);
        }
        return false;
    }
}
