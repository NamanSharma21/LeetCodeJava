package Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Intersection_of_Two_Arrays {
    public static void main(String[] args) {
        Intersection_of_Two_Arrays intersection_of_Two_Arrays = new Intersection_of_Two_Arrays();
        System.out.println("Intersection_of_Two_Arrays : "
                + Arrays.toString(
                        intersection_of_Two_Arrays.intersection(new int[] { 1, 2, 2, 1 }, new int[] { 2, 2 })));

    }

    /**
     * 
     * https://leetcode.com/problems/intersection-of-two-arrays/description/?envType=problem-list-v2&envId=array
     * 
     * Given two integer arrays nums1 and nums2, return an array of their
     * intersection. Each element in the result must be unique and you may return
     * the result in any order.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums1 = [1,2,2,1], nums2 = [2,2]
     * Output: [2]
     * Example 2:
     * 
     * Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * Output: [9,4]
     * Explanation: [4,9] is also accepted.
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums1.length, nums2.length <= 1000
     * 0 <= nums1[i], nums2[i] <= 1000
     * 
     */

    public int[] intersection(int[] nums1, int[] nums2) {
        int i = 0, j = 0;
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> result = new ArrayList<>();
        while (i < nums1.length && j < nums2.length) {
            int a = nums1[i];
            int b = nums2[j];
            if (a == b) {
                if (result.isEmpty() || result.get(result.size() - 1) != a) {
                    result.add(a);
                }
                i++;
                j++;
            } else if (a < b) {
                i++;
            } else {
                j++;
            }
        }

        int[] intersect = new int[result.size()];
        for (int y = 0; y < result.size(); y++)
            intersect[y] = result.get(y);
        return intersect;
    }

}
