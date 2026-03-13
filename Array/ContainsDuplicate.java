package Array;

import java.util.HashMap;
import java.util.Map;

public class ContainsDuplicate {
    public static void main(String[] args) {
        ContainsDuplicate containsDuplicate = new ContainsDuplicate();
        System.out.println("containsDuplicate : " + containsDuplicate.containsDuplicate(new int[] { 1, 2, 3, 1 }));
        ;
    }

    /*
     * Given an integer array nums, return true if any value appears at least twice
     * in the array, and return false if every element is distinct.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,2,3,1]
     * 
     * Output: true
     * 
     * Explanation:
     * 
     * The element 1 occurs at the indices 0 and 3.
     * 
     * Example 2:
     * 
     * Input: nums = [1,2,3,4]
     * 
     * Output: false
     * 
     * Explanation:
     * 
     * All elements are distinct.
     * 
     * Example 3:
     * 
     * Input: nums = [1,1,1,3,3,4,3,2,4,2]
     * 
     * Output: true
     * 
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 105
     * -109 <= nums[i] <= 109
     */
    public boolean containsDuplicate(int[] nums) {
        // for (int i = 0; i < nums.length; i++) {
        //     for (int y = i + 1; y < nums.length; y++) {
        //         if (nums[i] == nums[y]) {
        //             return true;
        //         }
        //     }
        // }

        Map<Integer,Integer> numberMap = new HashMap<>();

        for(int i = 0 ;i<nums.length;i++){
            if(numberMap.get(nums[i])==null){
                numberMap.put(nums[i], 1);
            }else{
                System.out.println(""+numberMap);
                return true;
            }
        }
        System.out.println(""+numberMap);

        return false;
    }
}
