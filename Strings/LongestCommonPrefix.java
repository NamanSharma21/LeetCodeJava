package Strings;

import java.util.Arrays;

public class LongestCommonPrefix {
    public static void main(String[] args) {
        LongestCommonPrefix longestCommonPrefix = new LongestCommonPrefix();
        System.out.println("longestCommonPrefix For : " + Arrays.toString(new String[] { "flower", "flow", "flight" })
                + " Is : " + longestCommonPrefix.longestCommonPrefix(new String[] { "flower", "flow", "flight" }));
        System.out.println("longestCommonPrefix For : " + Arrays.toString(new String[] { "dog", "racecar", "car" })
                + " Is : " + longestCommonPrefix.longestCommonPrefix(new String[] { "dog", "racecar", "car" }));

    }
    /*
     * Write a function to find the longest common prefix string amongst an array of
     * strings.
     * 
     * If there is no common prefix, return an empty string "".
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: strs = ["flower","flow","flight"]
     * Output: "fl"
     * Example 2:
     * 
     * Input: strs = ["dog","racecar","car"]
     * Output: ""
     * Explanation: There is no common prefix among the input strings.
     * 
     * 
     * Constraints:
     * 
     * 1 <= strs.length <= 200
     * 0 <= strs[i].length <= 200
     * strs[i] consists of only lowercase English letters if it is non-empty.
     * 
     */

    public String longestCommonPrefix(String[] strs) {
        if(strs.length == 0 || strs == null){
            return "";
        }
        for (int i = 0; i < strs[0].length(); i++) {
            for (int y = 1; y < strs.length; y++) {
                if (i == strs[y].length() || strs[0].charAt(i) != strs[y].charAt(i)) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }
}
