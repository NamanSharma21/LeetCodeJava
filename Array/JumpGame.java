package Array;

public class JumpGame {
    public static void main(String[] args) {
        JumpGame jumpGame = new JumpGame();
        System.out.println("JumpGame : " + jumpGame.canJump(new int[] { 2, 3, 1, 1, 4 }));
        System.out.println("JumpGame : " + jumpGame.canJump(new int[] { 3, 2, 1, 0, 4 }));
    }

    /**
     * 
     * https://leetcode.com/problems/jump-game/description/
     * 
     * You are given an integer array nums. You are initially positioned at the
     * array's first index, and each element in the array represents your maximum
     * jump length at that position.
     * 
     * Return true if you can reach the last index, or false otherwise.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [2,3,1,1,4]
     * Output: true
     * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
     * Example 2:
     * 
     * Input: nums = [3,2,1,0,4]
     * Output: false
     * Explanation: You will always arrive at index 3 no matter what. Its maximum
     * jump length is 0, which makes it impossible to reach the last index.
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 104
     * 0 <= nums[i] <= 105
     * 
     * 
     */

    public boolean canJump(int[] nums) {
        int n = nums.length;
        int lastIndex = n - 1;
        int maxReach = 0;
        for (int i = 0; i < n; i++) {
            if (i > maxReach) {
                return false;
            }
            int nextJump = nums[i] + i;
            if (nextJump > maxReach) {
                maxReach = nextJump;
            }
            if (maxReach >= lastIndex) {
                return true;
            }
        }
        return true;
    }
}
