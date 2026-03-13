package SortingAndSearching;

import java.util.Arrays;

public class MergeSortedArray {
    public static void main(String[] args) {
        MergeSortedArray mergeSortedArray = new MergeSortedArray();
        mergeSortedArray.merge(new int[] { 1, 2, 3, 0, 0, 0 }, 3, new int[] { 2, 5, 6 }, 3);
        // mergeSortedArray.merge(new int[] { 4, 5, 6, 0, 0, 0 }, 3, new int[] { 1, 2, 3
        // }, 3);
    }

    /*
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/96/
     * sorting-and-searching/587/
     * 
     * 
     * You are given two integer arrays nums1 and nums2, sorted in non-decreasing
     * order, and two integers m and n, representing the number of elements in nums1
     * and nums2 respectively.
     * 
     * Merge nums1 and nums2 into a single array sorted in non-decreasing order.
     * 
     * The final sorted array should not be returned by the function, but instead be
     * stored inside the array nums1. To accommodate this, nums1 has a length of m +
     * n, where the first m elements denote the elements that should be merged, and
     * the last n elements are set to 0 and should be ignored. nums2 has a length of
     * n.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
     * Output: [1,2,2,3,5,6]
     * Explanation: The arrays we are merging are [1,2,3] and [2,5,6].
     * The result of the merge is [1,2,2,3,5,6] with the underlined elements coming
     * from nums1.
     * Example 2:
     * 
     * Input: nums1 = [1], m = 1, nums2 = [], n = 0
     * Output: [1]
     * Explanation: The arrays we are merging are [1] and [].
     * The result of the merge is [1].
     * Example 3:
     * 
     * Input: nums1 = [0], m = 0, nums2 = [1], n = 1
     * Output: [1]
     * Explanation: The arrays we are merging are [] and [1].
     * The result of the merge is [1].
     * Note that because m = 0, there are no elements in nums1. The 0 is only there
     * to ensure the merge result can fit in nums1.
     * 
     * 
     * Constraints:
     * 
     * nums1.length == m + n
     * nums2.length == n
     * 0 <= m, n <= 200
     * 1 <= m + n <= 200
     * -109 <= nums1[i], nums2[j] <= 109
     * 
     * 
     * Follow up: Can you come up with an algorithm that runs in O(m + n) time?
     */

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // System.out.println("Total Size : " + (n + m));
        // int left = 0, right = m;
        // int counter = m;
        // for (int i = 0; i < n; i++) {
        // nums1[counter] = nums2[i];
        // counter++;
        // }

        // while (left < nums1.length && right < nums1.length) {
        // int first = nums1[left];
        // int second = nums1[right];
        // System.out
        // .println("First : " + first + " Second : " + second + " L : " + left + " R :
        // " + right + " - " + m);
        // if (second < first) {
        // System.out.println("F : " + first + " S : " + second);
        // int temp = nums1[right];
        // nums1[right] = nums1[left];
        // nums1[left] = temp;
        // }
        // left++;
        // right++;
        // }

        // System.out.println("" + Arrays.toString(nums1));

        int i = m - 1;
        int y = n - 1;
        int k = m + n - 1;

        while (y >= 0) {
            System.out.println("" + Arrays.toString(nums1) + " I : " + i + " Y : " + y + " K : " + k);
            if (i >= 0 && nums1[i] >= nums2[y]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[y--];
            }
        }
    }
}
