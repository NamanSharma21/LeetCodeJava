package Array;


public class RangeSumQuery_Immutable {
    public static void main(String[] args) {
    }

    /**
     * 
     * https://leetcode.com/problems/range-sum-query-immutable/description/?envType=problem-list-v2&envId=array
     * 
     * 
     * Given an integer array nums, handle multiple queries of the following type:
     * 
     * Calculate the sum of the elements of nums between indices left and right
     * inclusive where left <= right.
     * Implement the NumArray class:
     * 
     * NumArray(int[] nums) Initializes the object with the integer array nums.
     * int sumRange(int left, int right) Returns the sum of the elements of nums
     * between indices left and right inclusive (i.e. nums[left] + nums[left + 1] +
     * ... + nums[right]).
     * 
     * 
     * Example 1:
     * 
     * Input
     * ["NumArray", "sumRange", "sumRange", "sumRange"]
     * [[[-2, 0, 3, -5, 2, -1]], [0, 2], [2, 5], [0, 5]]
     * Output
     * [null, 1, -1, -3]
     * 
     * Explanation
     * NumArray numArray = new NumArray([-2, 0, 3, -5, 2, -1]);
     * numArray.sumRange(0, 2); // return (-2) + 0 + 3 = 1
     * numArray.sumRange(2, 5); // return 3 + (-5) + 2 + (-1) = -1
     * numArray.sumRange(0, 5); // return (-2) + 0 + 3 + (-5) + 2 + (-1) = -3
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 104
     * -105 <= nums[i] <= 105
     * 0 <= left <= right < nums.length
     * At most 104 calls will be made to sumRange.
     * 
     */

    class NumArray {
        private int[] prefix;

        public NumArray(int[] nums) {
            // this.nums = Arrays.copyOf(nums, nums.length);
            int n = nums.length;
            prefix = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                prefix[i] = prefix[i - 1] + nums[i - 1];
            }
        }

        public int sumRange(int left, int right) {
            // int sum = 0;
            // while (left <= right) {
            // sum += nums[left];
            // left++;
            // }
            // System.out.println("Total Sum : " + sum);
            // return sum;
            return prefix[right + 1] - prefix[left];
        }
    }
}
