package Array;

public class JumpGameII {
    public static void main(String[] args) {
        JumpGameII jumpGame = new JumpGameII();
        System.out.println("JumpGameII : " + jumpGame.jump(new int[] { 2, 3, 1, 1, 4 }));
        System.out.println("JumpGameII : " + jumpGame.jump(new int[] { 2, 3, 0, 1, 4 }));
        System.out.println("JumpGameII : " + jumpGame.jump(new int[] { 7, 0, 9, 6, 9, 6, 1, 7, 9, 0, 1, 2, 9, 0, 3 }));
    }

    /**
     * 
     * https://leetcode.com/problems/jump-game-ii/description/
     * 
     * You are given a 0-indexed array of integers nums of length n. You are
     * initially positioned at index 0.
     * 
     * Each element nums[i] represents the maximum length of a forward jump from
     * index i. In other words, if you are at index i, you can jump to any index (i
     * + j) where:
     * 
     * 0 <= j <= nums[i] and
     * i + j < n
     * Return the minimum number of jumps to reach index n - 1. The test cases are
     * generated such that you can reach index n - 1.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [2,3,1,1,4]
     * Output: 2
     * Explanation: The minimum number of jumps to reach the last index is 2. Jump 1
     * step from index 0 to 1, then 3 steps to the last index.
     * Example 2:
     * 
     * Input: nums = [2,3,0,1,4]
     * Output: 2
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 104
     * 0 <= nums[i] <= 1000
     * It's guaranteed that you can reach nums[n - 1].
     * 
     * 
     */

    public int jump(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return 0;
        }
        int jumps = 0;
        int farthest = 0;
        int currentEnd = 0;

        for (int i = 0; i < n - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;
                if (currentEnd >= n - 1) {
                    break;
                }
            }
        }
        return jumps;
    }
}
