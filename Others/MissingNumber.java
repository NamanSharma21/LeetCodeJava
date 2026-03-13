package Others;

public class MissingNumber {
    public static void main(String[] args) {
        MissingNumber missingNumber = new MissingNumber();
        System.out.println("Missing Number : " + missingNumber.missingNumber(new int[] { 3, 0, 1 }));
        System.out.println("Missing Number : " + missingNumber.missingNumber(new int[] { 0, 1 }));
        System.out.println("Missing Number : " + missingNumber.missingNumber(new int[] { 9, 6, 4, 2, 3, 5, 7, 0, 1 }));
    }

    /*
     * 
     * Given an array nums containing n distinct numbers in the range [0, n], return
     * the only number in the range that is missing from the array.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [3,0,1]
     * 
     * Output: 2
     * 
     * Explanation:
     * 
     * n = 3 since there are 3 numbers, so all numbers are in the range [0,3]. 2 is
     * the missing number in the range since it does not appear in nums.
     * 
     * Example 2:
     * 
     * Input: nums = [0,1]
     * 
     * Output: 2
     * 
     * Explanation:
     * 
     * n = 2 since there are 2 numbers, so all numbers are in the range [0,2]. 2 is
     * the missing number in the range since it does not appear in nums.
     * 
     * Example 3:
     * 
     * Input: nums = [9,6,4,2,3,5,7,0,1]
     * 
     * Output: 8
     * 
     * Explanation:
     * 
     * n = 9 since there are 9 numbers, so all numbers are in the range [0,9]. 8 is
     * the missing number in the range since it does not appear in nums.
     * 
     * 
     * Constraints:
     * 
     * n == nums.length
     * 1 <= n <= 104
     * 0 <= nums[i] <= n
     * All the numbers of nums are unique.
     * 
     * 
     * Follow up: Could you implement a solution using only O(1) extra space
     * complexity and O(n) runtime complexity?
     * 
     */

    public int missingNumber(int[] nums) {
        int arrayLength = nums.length;
        int arraySum = 0;
        int counter = 0;
        int counterSum = 0;
        while (counter < (arrayLength + 1)) {
            counterSum += counter;
            counter++;
        }
        for (int i = 0; i < arrayLength; i++) {
            arraySum += nums[i];
        }
        System.out.println("Array Sum : " + arraySum + " Counter Sum : " + counterSum);
        return counterSum - arraySum;
    }
}
