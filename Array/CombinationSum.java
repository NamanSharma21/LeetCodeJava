package Array;

import java.util.ArrayList;
import java.util.List;

public class CombinationSum {
    public static void main(String[] args) {
        CombinationSum combinationSum = new CombinationSum();
        System.out.println("CombinationSum : " + combinationSum.combinationSum(new int[] { 2, 3, 6, 7 }, 7));
    }

    /**
     * 
     * https://leetcode.com/problems/combination-sum/description/?envType=problem-list-v2&envId=array
     * 
     * Given an array of distinct integers candidates and a target integer target,
     * return a list of all unique combinations of candidates where the chosen
     * numbers sum to target. You may return the combinations in any order.
     * 
     * The same number may be chosen from candidates an unlimited number of times.
     * Two combinations are unique if the frequency of at least one of the chosen
     * numbers is different.
     * 
     * The test cases are generated such that the number of unique combinations that
     * sum up to target is less than 150 combinations for the given input.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: candidates = [2,3,6,7], target = 7
     * Output: [[2,2,3],[7]]
     * Explanation:
     * 2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple
     * times.
     * 7 is a candidate, and 7 = 7.
     * These are the only two combinations.
     * Example 2:
     * 
     * Input: candidates = [2,3,5], target = 8
     * Output: [[2,2,2,2],[2,3,3],[3,5]]
     * Example 3:
     * 
     * Input: candidates = [2], target = 1
     * Output: []
     * 
     * 
     * Constraints:
     * 
     * 1 <= candidates.length <= 30
     * 2 <= candidates[i] <= 40
     * All elements of candidates are distinct.
     * 1 <= target <= 40
     * 
     */

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        backTrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void backTrack(int[] candidates, int target, int index, List<Integer> current, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        if (target < 0 || index == candidates.length) {
            return;
        }

        backTrack(candidates, target, index + 1, current, result);
        current.add(candidates[index]);
        backTrack(candidates, target - candidates[index], index, current, result);
        current.remove(current.size() - 1);
    }
}
