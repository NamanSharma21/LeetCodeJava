package Strings;

import java.util.LinkedHashMap;
import java.util.Map;

public class FirstUniqueCharacterinaString {
    public static void main(String[] args) {
        FirstUniqueCharacterinaString firstUniqueCharacterinaString = new FirstUniqueCharacterinaString();
        System.out
                .println("FirstUniqueCharacterinaString : " +
                        firstUniqueCharacterinaString.firstUniqChar("leetcode"));
        System.out
                .println("FirstUniqueCharacterinaString : "
                        + firstUniqueCharacterinaString.firstUniqChar("loveleetcode"));
        System.out
                .println("FirstUniqueCharacterinaString : "
                        + firstUniqueCharacterinaString.firstUniqChar("aabb"));

    }

    /*
     * Given a string s, find the first non-repeating character in it and return its
     * index. If it does not exist, return -1.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: s = "leetcode"
     * 
     * Output: 0
     * 
     * Explanation:
     * 
     * The character 'l' at index 0 is the first character that does not occur at
     * any other index.
     * 
     * Example 2:
     * 
     * Input: s = "loveleetcode"
     * 
     * Output: 2
     * 
     * Example 3:
     * 
     * Input: s = "aabb"
     * 
     * Output: -1
     * 
     * 
     * 
     * Constraints:
     * 
     * 1 <= s.length <= 105
     * s consists of only lowercase English letters.
     */

    public int firstUniqChar(String s) {
        // Map<Character, Integer> charCountMap = new LinkedHashMap<>();
        // char[] charArray = s.toCharArray();
        // for (int i = 0; i < charArray.length; i++) {
        // if (charCountMap.get(charArray[i]) == null) {
        // charCountMap.put(charArray[i], 1);
        // } else {
        // charCountMap.put(charArray[i], charCountMap.get(charArray[i]) + 1);
        // }
        // }
        // System.out.println("" + charCountMap);
        // boolean flag = charCountMap.entrySet().stream().filter(it -> it.getValue() ==
        // 1).findFirst().isPresent();
        // char temp = flag ? charCountMap.entrySet().stream().filter(it ->
        // it.getValue() == 1).findFirst().get().getKey()
        // : ' ';
        // return s.indexOf(temp);

        int[] charSet = new int[26];
        int[] indices = new int[26];

        int i = 0, ind = 0;
        while (i < s.length()) {
            ind = s.charAt(i) - 'a';
            charSet[ind] += 1;
            if (indices[ind] == 0)
                indices[ind] = i;
            i++;
        }

        ind = Integer.MAX_VALUE;
        for (int y = 0; y < 26; y++) {
            if (charSet[y] == 1 && indices[y] < ind)
                ind = indices[y];
        }
        return ind == Integer.MAX_VALUE ? -1 : ind;
    }
}
