package Strings;

public class ImplementstrStr {
    public static void main(String[] args) {
        ImplementstrStr implementstrStr = new ImplementstrStr();
        System.out.println(" strStr sadbutsad - sad : " + implementstrStr.strStr("sadbutsad", "sad"));
        System.out.println(" strStr leetcode - leeto : " + implementstrStr.strStr("leetcode", "leeto"));
        System.out.println(" strStr hello - ll : " + implementstrStr.strStr("hello", "ll"));
        System.out.println(" strStr a - a : " + implementstrStr.strStr("a", "a"));
        System.out.println(" strStr aaa - aa : " + implementstrStr.strStr("aaa", "aa"));
    }
    /*
     * Given two strings needle and haystack, return the index of the first
     * occurrence of needle in haystack, or -1 if needle is not part of haystack.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: haystack = "sadbutsad", needle = "sad"
     * Output: 0
     * Explanation: "sad" occurs at index 0 and 6.
     * The first occurrence is at index 0, so we return 0.
     * Example 2:
     * 
     * Input: haystack = "leetcode", needle = "leeto"
     * Output: -1
     * Explanation: "leeto" did not occur in "leetcode", so we return -1.
     * 
     * 
     * Constraints:
     * 
     * 1 <= haystack.length, needle.length <= 104
     * haystack and needle consist of only lowercase English characters.
     */

    public int strStr(String haystack, String needle) {
        int hayStackLength = haystack.length();
        int needleLength = needle.length();
        if (hayStackLength == needleLength) {
            return haystack.equals(needle) ? 0 : -1;
        }
        if (haystack.isEmpty() || needle.isEmpty() || hayStackLength < needleLength) {
            return -1;
        }
        int hayStackIndex = 0;
        int needleIndex = 0;
        int strIndex = 0;
        while (hayStackIndex < hayStackLength && needleIndex < needleLength) {
            System.out.println(
                    " " + haystack.charAt(hayStackIndex) + " : " +
                            needle.charAt(needleIndex)
                             + " NIdx : " + needleIndex + " HIdx : "
                            + hayStackIndex+ " StrIdx : " + strIndex);
            if (haystack.charAt(hayStackIndex) != needle.charAt(needleIndex)) {
                strIndex = hayStackIndex - needleIndex + 1;
                hayStackIndex = strIndex;
                needleIndex = 0;
            } else {
                hayStackIndex++;
                needleIndex++;
            }
            if (needleIndex == needleLength) {
                return hayStackIndex-needleIndex ;
            }
        }
        return -1;
        // int hLen = haystack.length();
        // int nLen = needle.length();
        // int nIndex = 0;
        // for (int i = 0; i < hLen; i++) {
        // // as long as the characters are equal, increment needleIndex
        // System.out.println("nIndex : " + nIndex + " i : " + i);
        // if (haystack.charAt(i) == needle.charAt(nIndex)) {
        // nIndex++;
        // } else {
        // // start from the next index of previous start index
        // i = i - nIndex;
        // // needle should start from index 0
        // nIndex = 0;
        // }
        // // check if needleIndex reached needle length
        // if (nIndex == nLen) {
        // // return the first index
        // return i - nLen + 1;
        // }
        // }
        // return -1;
    }
}
