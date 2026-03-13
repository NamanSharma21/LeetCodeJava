package Array;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstringWithoutRepeatingCharacters {
    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingCharacters longestSubstringWithoutRepeatingCharacters = new LongestSubstringWithoutRepeatingCharacters();
        System.out.println("LongestSubstringWithoutRepeatingCharacters : "
                +
                longestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstring("abcabcbb"));
        System.out.println("LongestSubstringWithoutRepeatingCharacters : "
                +
                longestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstring("bbbbb"));
        System.out.println("LongestSubstringWithoutRepeatingCharacters : "
                + longestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstring("pwwkew"));
        System.out.println("LongestSubstringWithoutRepeatingCharacters : "
                + longestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstring(" "));
        System.out.println("LongestSubstringWithoutRepeatingCharacters : "
                + longestSubstringWithoutRepeatingCharacters.lengthOfLongestSubstring("c"));
    }

    /**
     * 
     * https://leetcode.com/explore/interview/card/top-interview-questions-medium/103/array-and-strings/779/
     * 
     * Given a string s, find the length of the longest substring without duplicate
     * characters.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: s = "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3. Note that "bca" and
     * "cab" are also correct answers.
     * Example 2:
     * 
     * Input: s = "bbbbb"
     * Output: 1
     * Explanation: The answer is "b", with the length of 1.
     * Example 3:
     * 
     * Input: s = "pwwkew"
     * Output: 3
     * Explanation: The answer is "wke", with the length of 3.
     * Notice that the answer must be a substring, "pwke" is a subsequence and not a
     * substring.
     * 
     * 
     * Constraints:
     * 
     * 0 <= s.length <= 5 * 104
     * s consists of English letters, digits, symbols and spaces.
     * 
     */

    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int left = 0, maxLength = 0;
        Map<Character, Integer> lastSeen = new HashMap<>();
        for (int right = 0; right < n; right++) {
            char c = s.charAt(right);
            if (lastSeen.containsKey(c)) {
                left = Math.max(left, lastSeen.get(c) + 1);
            }
            lastSeen.put(c, right);
            System.out.println(left+" -- "+right+" --- " + s.substring(left, right));
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }
}
