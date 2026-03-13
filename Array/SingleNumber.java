package Array;

import java.util.Arrays;

public class SingleNumber {
    public static void main(String[] args) {
        SingleNumber singleNumber = new SingleNumber();
        // System.out.println("Single Number : " + singleNumber.singleNumber(new int[] {
        // 2, 2, 1 }));
        System.out.println("Single Number : " + singleNumber.singleNumber(new int[] { 4, 1, 2, 1, 2 }));
    }

    /*
     * Given a non-empty array of integers nums, every element appears twice except
     * for one. Find that single one.
     * 
     * You must implement a solution with a linear runtime complexity and use only
     * constant extra space.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [2,2,1]
     * 
     * Output: 1
     * 
     * Example 2:
     * 
     * Input: nums = [4,1,2,1,2]
     * 
     * Output: 4
     * 
     * Example 3:
     * 
     * Input: nums = [1]
     * 
     * Output: 1
     * 
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 3 * 104
     * -3 * 104 <= nums[i] <= 3 * 104
     * Each element in the array appears twice except for one element which appears
     * only once.
     */

    public int singleNumber(int[] nums) {
        // Arrays.sort(nums);
        // int numberToCompare = nums[0];
        // int counter = 0;
        // System.out.println("Sorted Array : "+Arrays.toString(nums));
        // for (int i = 1; i < nums.length; i += 2) {
        // System.out.println("First : " + numberToCompare + " Second : " + nums[i]);
        // if (numberToCompare != nums[i]) {
        // return numberToCompare;
        // } else {
        // counter += 2;
        // numberToCompare = nums[counter];
        // }
        // }
        // return numberToCompare;

        // if(nums.length==1){
        //     return nums[0];
        // }

        // Map<Integer, Integer> numberMap = new HashMap<>();
        // for (int i = 0; i < nums.length; i++) {
        //     if (numberMap.get(nums[i]) == null) {
        //         numberMap.put(nums[i], 1);
        //     } else {
        //         numberMap.put(nums[i], numberMap.get(nums[i]) + 1);
        //     }
        // }

        // for(Map.Entry<Integer,Integer> entry:numberMap.entrySet()){
        //     if(entry.getValue()==1){
        //         return entry.getKey();
        //     }
        // }
        // return nums[0];

        int result = 0;
        for(int i = 0;i< nums.length;i++){
            result^=nums[i];
        }
        return result;
    }

    public int[] sortArray(int[] input, boolean isAscendingOrder) {
        // return isAscendingOrder ? sortArrayAsc(input) : sortArrayDesc(input);
        return isAscendingOrder ? sortArrayAsc(input) : sortArrayDesc(input);
    }

    private int[] sortArrayAsc(int[] input) {
        int smallest = input[0];
        int nextSmallestIndex = 0;
        for (int i = 1; i < input.length; i++) {
            System.out.println(
                    "Smallest : " + smallest + " Number : " + input[i] + " Smallest Index : " + nextSmallestIndex);
            if (input[i] <= smallest) {
                System.out.println("Replacing : " + input[i] + " With : " + smallest);
                int temp = smallest;
                smallest = input[i];
                input[i] = temp;
                input[nextSmallestIndex] = smallest;
                nextSmallestIndex++;
            }
            System.out.println("" + Arrays.toString(input));
        }
        return input;
    }

    private int[] sortArrayDesc(int[] input) {
        int largest = input[0];
        int nextLargestIndex = 0;
        for (int i = 1; i < input.length; i++) {
            if (input[i] > largest) {
                largest = input[i];
                input[i] = input[nextLargestIndex];
                input[nextLargestIndex] = largest;
                nextLargestIndex++;
            }
            // System.out.println("" + Arrays.toString(input));
        }
        return input;
    }
}
