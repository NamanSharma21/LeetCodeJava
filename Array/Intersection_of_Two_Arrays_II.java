package Array;

import java.util.Arrays;

public class Intersection_of_Two_Arrays_II {
    public static void main(String[] args) {
        Intersection_of_Two_Arrays_II intersection_of_Two_Arrays_II = new Intersection_of_Two_Arrays_II();
        System.out.println("Intersection : "
                + Arrays.toString(intersection_of_Two_Arrays_II.intersect(new int[] { 1, 2, 2, 1 }, new int[] {
                        2, 2 })));
        ;
        // System.out.println("Intersection : " + Arrays
        // .toString(intersection_of_Two_Arrays_II.intersect(new int[] { 4, 9, 5 }, new
        // int[] { 9, 4, 9, 8, 4 })));
        // System.out.println("Intersection : " + Arrays
        // .toString(intersection_of_Two_Arrays_II.intersect(new int[] { 1 }, new int[]
        // { 1, 1 })));
        // System.out.println("Intersection : " + Arrays
        // .toString(intersection_of_Two_Arrays_II.intersect(new int[] { 4, 9, 5 }, new
        // int[] { 9, 4, 9, 8, 4 })));
        // System.out.println("Intersection : " + Arrays
        // .toString(intersection_of_Two_Arrays_II.intersect(
        // new int[] { 61, 24, 20, 58, 95, 53, 17, 32, 45, 85, 70, 20, 83, 62, 35, 89,
        // 5, 95, 12, 86, 58,
        // 77, 30, 64, 46, 13, 5, 92, 67, 40, 20, 38, 31, 18, 89, 85, 7, 30, 67, 34, 62,
        // 35, 47,
        // 98, 3, 41, 53, 26, 66, 40, 54, 44, 57, 46, 70, 60, 4, 63, 82, 42, 65, 59, 17,
        // 98, 29,
        // 72, 1, 96, 82, 66, 98, 6, 92, 31, 43, 81, 88, 60, 10, 55, 66, 82, 0, 79, 11,
        // 81 },
        // new int[] { 5, 25, 4, 39, 57, 49, 93, 79, 7, 8, 49, 89, 2, 7, 73, 88, 45, 15,
        // 34, 92, 84, 38,
        // 85, 34, 16, 6, 99, 0, 2, 36, 68, 52, 73, 50, 77, 44, 61, 48 })));
    }

    /*
     * Given two integer arrays nums1 and nums2, return an array of their
     * intersection. Each element in the result must appear as many times as it
     * shows in both arrays and you may return the result in any order.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums1 = [1,2,2,1], nums2 = [2,2]
     * Output: [2,2]
     * Example 2:
     * 
     * Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * Output: [4,9]
     * Explanation: [9,4] is also accepted.
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums1.length, nums2.length <= 1000
     * 0 <= nums1[i], nums2[i] <= 1000
     * 
     * 
     * Follow up:
     * 
     * What if the given array is already sorted? How would you optimize your
     * algorithm?
     * What if nums1's size is small compared to nums2's size? Which algorithm is
     * better?
     * What if elements of nums2 are stored on disk, and the memory is limited such
     * that you cannot load all elements into the memory at once?
     */

    public int[] intersect(int[] nums1, int[] nums2) {
        // Map<Integer, Integer> num1Map = new HashMap<>();
        // List<Integer> intersection = new ArrayList<>();
        // if (nums1.length > nums2.length) {
        // for (int i = 0; i < nums2.length; i++) {
        // if (num1Map.get(nums2[i]) == null) {
        // num1Map.put(nums2[i], 1);
        // } else {
        // num1Map.put(nums2[i], num1Map.get(nums2[i]) + 1);
        // }
        // }

        // for (int i = 0; i < nums1.length; i++) {
        // if (num1Map.get(nums1[i]) != null && num1Map.get(nums1[i]) > 0) {
        // intersection.add(nums1[i]);
        // num1Map.put(nums1[i], num1Map.get(nums1[i]) - 1);
        // }
        // }
        // } else {
        // for (int i = 0; i < nums1.length; i++) {
        // if (num1Map.get(nums1[i]) == null) {
        // num1Map.put(nums1[i], 1);
        // } else {
        // num1Map.put(nums1[i], num1Map.get(nums1[i]) + 1);
        // }
        // }

        // for (int i = 0; i < nums2.length; i++) {
        // if (num1Map.get(nums2[i]) != null && num1Map.get(nums2[i]) > 0) {
        // intersection.add(nums2[i]);
        // num1Map.put(nums2[i], num1Map.get(nums2[i]) - 1);
        // }
        // }
        // }

        // System.out.println("Num 1 Map : " + num1Map);

        // return intersection.stream().mapToInt(Integer::intValue).toArray();

        int[] n1 = new int[1001];
        int[] n2 = new int[1001];

        for (int i = 0; i < nums1.length; i++) {
            n1[nums1[i]]++;
        }

        int k = 0;
        for (int i = 0; i < nums2.length; i++) {
            if (n1[nums2[i]] > 0) {
                n2[k++] = nums2[i];
                n1[nums2[i]]--;
            }
        }

        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {
            ans[i] = n2[i];
        }
        return ans;
        // int[] n1 = new int[1001];
        // int[] n2 = new int[1001];

        // for (int i = 0; i < nums1.length; i++) {
        // n1[nums1[i]]++;
        // }
        // int k = 0;
        // for (int i = 0; i < nums2.length; i++) {
        // if (n1[nums2[i]] > 0) {
        // n2[k++] = nums2[i];
        // n1[nums2[i]]--;

        // }
        // }
        // int[] ans = new int[k];
        // for (int i = 0; i < k; i++) {
        // ans[i] = n2[i];
        // }
        // return ans;

    }

    /*
     * ✔️ Approach 1: HashMap
     * 
     * 
     * Using HashMap to store occurrences of elements in the nums1 array.
     * Iterate x in nums2 array, check if cnt[x] > 0 then append x to our answer and
     * decrease cnt[x] by one.
     * To optimize the space, we ensure len(nums1) <= len(nums2) by swapping nums1
     * with nums2 if len(nums1) > len(nums2).
     * class Solution:
     * def intersect(self, nums1: List[int], nums2: List[int]) -> List[int]:
     * if len(nums1) > len(nums2): return self.intersect(nums2, nums1)
     * 
     * cnt = Counter(nums1)
     * ans = []
     * for x in nums2:
     * if cnt[x] > 0:
     * ans.append(x)
     * cnt[x] -= 1
     * return ans
     * Complexity:
     * 
     * 
     * Time: O(M + N), where M <= 1000 is length of nums1 array, N <= 1000 is length
     * of nums2 array.
     * Space: O(min(M, N))
     * ✔️ Approach 2: Sort then Two Pointers
     * 
     * 
     * class Solution:
     * def intersect(self, nums1: List[int], nums2: List[int]) -> List[int]:
     * nums1.sort()
     * nums2.sort()
     * 
     * ans = []
     * i = j = 0
     * while i < len(nums1) and j < len(nums2):
     * if nums1[i] < nums2[j]:
     * i += 1
     * elif nums1[i] > nums2[j]:
     * j += 1
     * else:
     * ans.append(nums1[i])
     * i += 1
     * j += 1
     * return ans
     * Complexity:
     * 
     * 
     * Time: O(MlogM + NlogN), where M <= 1000 is length of nums1 array, N <= 1000
     * is length of nums2 array.
     * Extra Space (without counting output as space): O(sorting)
     * ✔️ Follow-up Question 1: What if the given array is already sorted? How would
     * you optimize your algorithm?
     * 
     * 
     * Approach 2 is the best choice since we skip the cost of sorting.
     * So time complexity is O(M+N) and the space complexity is O(1).
     * ✔️ Follow-up Question 2: What if nums1's size is small compared to nums2's
     * size? Which algorithm is better?
     * 
     * 
     * Approach 1 is the best choice.
     * Time complexity is O(M+N) and the space complexity is O(M), where M is length
     * of nums1, N is length of nums2.
     * ✔️ Follow-up Question 3: What if elements of nums2 are stored on disk, and
     * the memory is limited such that you cannot load all elements into the memory
     * at once?
     * 
     * 
     * If nums1 fits into the memory, we can use Approach 1 which stores all
     * elements of nums1 in the HashMap. Then, we can sequentially load and process
     * nums2.
     * If neither nums1 nor nums2 fits into the memory, we split the numeric range
     * into numeric sub-ranges that fit into the memory.
     * We modify Approach 1 to count only elements which belong to the given numeric
     * sub-range.
     * We process each numeric sub-ranges one by one, util we process all numeric
     * sub-ranges.
     * For example:
     * Input constraint:
     * 1 <= nums1.length, nums2.length <= 10^10.
     * 0 <= nums1[i], nums2[i] < 10^5
     * Our memory can store up to 1000 elements.
     * Then we split numeric range into numeric sub-ranges [0...999], [1000...1999],
     * ..., [99000...99999], then call Approach 1 to process 100 numeric sub-ranges.
     * If you think this post is useful, I'm happy if you give a vote. Any questions
     * or discussions are welcome! Thank a lot.
     */
}
