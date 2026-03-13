package Array;

import java.util.ArrayList;
import java.util.List;

public class SummaryRanges {
    public static void main(String[] args) {
        SummaryRanges summaryRanges = new SummaryRanges();
        System.out.println("Summary Ranges : " + summaryRanges.summaryRanges(new int[] { 0, 1, 2, 4, 5, 7 }));
    }

    /**
     * https://leetcode.com/problems/summary-ranges/description/?envType=problem-list-v2&envId=array
     * 
     * You are given a sorted unique integer array nums.
     * 
     * A range [a,b] is the set of all integers from a to b (inclusive).
     * 
     * Return the smallest sorted list of ranges that cover all the numbers in the
     * array exactly. That is, each element of nums is covered by exactly one of the
     * ranges, and there is no integer x such that x is in one of the ranges but not
     * in nums.
     * 
     * Each range [a,b] in the list should be output as:
     * 
     * "a->b" if a != b
     * "a" if a == b
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [0,1,2,4,5,7]
     * Output: ["0->2","4->5","7"]
     * Explanation: The ranges are:
     * [0,2] --> "0->2"
     * [4,5] --> "4->5"
     * [7,7] --> "7"
     * Example 2:
     * 
     * Input: nums = [0,2,3,4,6,8,9]
     * Output: ["0","2->4","6","8->9"]
     * Explanation: The ranges are:
     * [0,0] --> "0"
     * [2,4] --> "2->4"
     * [6,6] --> "6"
     * [8,9] --> "8->9"
     * 
     * 
     * Constraints:
     * 
     * 0 <= nums.length <= 20
     * -231 <= nums[i] <= 231 - 1
     * All the values of nums are unique.
     * nums is sorted in ascending order.
     * 
     */

    public List<String> summaryRanges(int[] nums) {

        // int counter = 0;
        // int start = 0;
        // int prev = nums[0];
        // List<String> summaryList = new ArrayList<>();
        // while (counter < nums.length) {
        // System.out
        // .println("Start : " + nums[start] + " Counter : " + nums[counter] + " Prev :
        // " + (nums[prev] + 1));
        // if (counter == 0) {
        // prev = nums[counter];
        // start = counter;
        // } else if ((counter == nums.length - 1)) {
        // System.out.println("----");
        // summaryList.add(nums[counter]+"");
        // } else {
        // if (!(nums[counter] == (nums[prev] + 1))) {
        // summaryList.add(nums[start] + "->" + nums[prev]);
        // start = counter;
        // }
        // prev = counter;
        // }
        // counter++;
        // }
        // return summaryList;

        List<String> result = new ArrayList<>();
        int n = nums.length;
        if (n == 0)
            return result;
        int start = nums[0];
        for (int i = 1; i < n; i++) {
            if (nums[i] != nums[i - 1] + 1) {
                if (start == nums[i - 1]) {
                    result.add(String.valueOf(start));
                } else {
                    result.add(String.valueOf(start).concat("->").concat(String.valueOf(nums[i - 1])));
                }
                start = nums[i];
            }
        }

        if (start == nums[n - 1]) {
            result.add(String.valueOf(start));
        } else {
            result.add(String.valueOf(start).concat("->").concat(String.valueOf(nums[n - 1])));
        }
        return result;
    }
}
