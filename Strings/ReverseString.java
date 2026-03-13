package Strings;

import java.util.Arrays;

public class ReverseString {
    public static void main(String[] args) {
        ReverseString reverseString = new ReverseString();
        reverseString.reverseString(new char[] { 'h', 'e', 'l', 'l', 'o' });
        reverseString.reverseString(new char[] { 'r', 'a', 'c', 'e', 'a', 'c', 'a', 'r' });
    }

    /*
     * Write a function that reverses a string. The input string is given as an
     * array of characters s.
     * 
     * You must do this by modifying the input array in-place with O(1) extra
     * memory.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: s = ["h","e","l","l","o"]
     * Output: ["o","l","l","e","h"]
     * Example 2:
     * 
     * Input: s = ["H","a","n","n","a","h"]
     * Output: ["h","a","n","n","a","H"]
     * 
     * 
     * Constraints:
     * 
     * 1 <= s.length <= 105
     * s[i] is a printable ascii character.
     */

    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            System.out.println("" + Arrays.toString(s));
            left++;
            right--;
        }
    }
}
