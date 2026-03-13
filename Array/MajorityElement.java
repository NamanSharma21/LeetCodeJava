package Array;

public class MajorityElement {
    public static void main(String[] args) {
        MajorityElement majorityElement = new MajorityElement();
        System.out.println("MajorityElement : " + majorityElement.majorityElement(new int[] { 3, 2, 3 }));
        System.out.println("MajorityElement : " + majorityElement.majorityElement(new int[] { 2, 2, 1, 1, 1, 2, 2 }));
    }

    /*
     * https://leetcode.com/problems/majority-element/description/?envType=problem-
     * list-v2&envId=array
     * 
     * Given an array nums of size n, return the majority element.
     * 
     * The majority element is the element that appears more than ⌊n / 2⌋ times. You
     * may assume that the majority element always exists in the array.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [3,2,3]
     * Output: 3
     * Example 2:
     * 
     * Input: nums = [2,2,1,1,1,2,2]
     * Output: 2
     * 
     * 
     * Constraints:
     * 
     * n == nums.length
     * 1 <= n <= 5 * 104
     * -109 <= nums[i] <= 109
     * The input is generated such that a majority element will exist in the array.
     * 
     * 
     * Follow-up: Could you solve the problem in linear time and in O(1) space?
     * 
     * 
     * 
     * Explanation:
     * Algorithm:
     * 
     * Initialize two variables: count and candidate. Set count to 0 and candidate
     * to an arbitrary value.
     * Iterate through the array nums:
     * a. If count is 0, assign the current element as the new candidate and
     * increment count by 1.
     * b. If the current element is the same as the candidate, increment count by 1.
     * c. If the current element is different from the candidate, decrement count by
     * 1.
     * After the iteration, the candidate variable will hold the majority element.
     * Explanation:
     * 
     * The algorithm starts by assuming the first element as the majority candidate
     * and sets the count to 1.
     * As it iterates through the array, it compares each element with the
     * candidate:
     * a. If the current element matches the candidate, it suggests that it
     * reinforces the majority element because it appears again. Therefore, the
     * count is incremented by 1.
     * b. If the current element is different from the candidate, it suggests that
     * there might be an equal number of occurrences of the majority element and
     * other elements. Therefore, the count is decremented by 1.
     * Note that decrementing the count doesn't change the fact that the majority
     * element occurs more than n/2 times.
     * If the count becomes 0, it means that the current candidate is no longer a
     * potential majority element. In this case, a new candidate is chosen from the
     * remaining elements.
     * The algorithm continues this process until it has traversed the entire array.
     * The final value of the candidate variable will hold the majority element.
     * Explanation of Correctness:
     * The algorithm works on the basis of the assumption that the majority element
     * occurs more than n/2 times in the array. This assumption guarantees that even
     * if the count is reset to 0 by other elements, the majority element will
     * eventually regain the lead.
     * 
     * Let's consider two cases:
     * 
     * If the majority element has more than n/2 occurrences:
     * 
     * The algorithm will ensure that the count remains positive for the majority
     * element throughout the traversal, guaranteeing that it will be selected as
     * the final candidate.
     * If the majority element has exactly n/2 occurrences:
     * 
     * In this case, there will be an equal number of occurrences for the majority
     * element and the remaining elements combined.
     * However, the majority element will still be selected as the final candidate
     * because it will always have a lead over any other element.
     * In both cases, the algorithm will correctly identify the majority element.
     * 
     * The time complexity of the Moore's Voting Algorithm is O(n) since it
     * traverses the array once.
     * 
     * This approach is efficient compared to sorting as it requires only a single
     * pass through the array and does not change the original order of the
     * elements.
     */

    public int majorityElement(int[] nums) {
        // HashMap<Integer, Integer> counterMap = new HashMap<>();
        // int majorityThreshold = nums.length / 2;
        // for (int i = 0; i < nums.length; i++) {
        // if (counterMap.containsKey(nums[i]))
        // counterMap.put(nums[i], counterMap.get(nums[i]) + 1);
        // else
        // counterMap.put(nums[i], 1);
        // }
        // for (Entry<Integer, Integer> entry : counterMap.entrySet()) {
        // if (entry.getValue() > majorityThreshold) {
        // return entry.getKey();
        // }
        // }
        // System.out.println("" + counterMap);
        // return 0;

        int candidate = 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Can : " + candidate + " Co : " + count + " N : " + nums[i]);
            if (count == 0) {
                candidate = nums[i];
            }
            if (candidate == nums[i]) {
                count++;
            } else {
                count--;
            }
            System.out.println("-----Can : " + candidate + " Co : " + count + " N : " + nums[i]);
        }
        return candidate;
    }
}
