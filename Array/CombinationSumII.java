package Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSumII {
    public static void main(String[] args) {
        CombinationSumII combinationSum = new CombinationSumII();
        System.out
                .println("CombinationSumII : " + combinationSum.combinationSum(new int[] { 10, 1, 2, 7, 6, 1, 5 }, 8));
    }

    /**
     * 
     * https://leetcode.com/problems/combination-sum-ii/description/?envType=problem-list-v2&envId=array
     * 
     * Given a collection of candidate numbers (candidates) and a target number
     * (target), find all unique combinations in candidates where the candidate
     * numbers sum to target.
     * 
     * Each number in candidates may only be used once in the combination.
     * 
     * Note: The solution set must not contain duplicate combinations.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: candidates = [10,1,2,7,6,1,5], target = 8
     * Output:
     * [
     * [1,1,6],
     * [1,2,5],
     * [1,7],
     * [2,6]
     * ]
     * Example 2:
     * 
     * Input: candidates = [2,5,2,1,2], target = 5
     * Output:
     * [
     * [1,2,2],
     * [5]
     * ]
     * 
     * 
     * Constraints:
     * 
     * 1 <= candidates.length <= 100
     * 1 <= candidates[i] <= 50
     * 1 <= target <= 30
     * 
     */

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        backTrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void backTrack(int[] candidates, int target, int index, List<Integer> current, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        if (target < 0) {
            return;
        }

        for (int i = index; i < candidates.length; i++) {
            if (i > index && candidates[i] == candidates[i - 1]) {
                continue;
            }

            int val = candidates[i];
            if (val > target) {
                break;
            }

            current.add(val);
            backTrack(candidates, target - val, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
}
