package Strings;

import java.util.Arrays;

public class ValidPalindrome {
    public static void main(String[] args) {
        ValidPalindrome validPalindrome = new ValidPalindrome();
        System.out.println(" Is Valid Palindrome : " + validPalindrome.isPalindrome("A man, a plan, a canal: Panama"));
        System.out.println(" Is Valid Palindrome : " + validPalindrome.isPalindrome("race a car"));
        System.out.println(" Is Valid Palindrome : " + validPalindrome.isPalindrome(" "));
        System.out.println(" Is Valid Palindrome : " + validPalindrome.isPalindrome("0P"));
    }

    /*
     * A phrase is a palindrome if, after converting all uppercase letters into
     * lowercase letters and removing all non-alphanumeric characters, it reads the
     * same forward and backward. Alphanumeric characters include letters and
     * numbers.
     * 
     * Given a string s, return true if it is a palindrome, or false otherwise.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: s = "A man, a plan, a canal: Panama"
     * Output: true
     * Explanation: "amanaplanacanalpanama" is a palindrome.
     * Example 2:
     * 
     * Input: s = "race a car"
     * Output: false
     * Explanation: "raceacar" is not a palindrome.
     * Example 3:
     * 
     * Input: s = " "
     * Output: true
     * Explanation: s is an empty string "" after removing non-alphanumeric
     * characters.
     * Since an empty string reads the same forward and backward, it is a
     * palindrome.
     * 
     * 
     * Constraints:
     * 
     * 1 <= s.length <= 2 * 105
     * s consists only of printable ASCII characters.
     */

    public boolean isPalindrome(String s) {
        // if (s.trim().isEmpty())
        // return true;
        // String afterReplacementString = s.toLowerCase().replaceAll("[^A-Za-z0-9]",
        // "");
        // char[] charArray = afterReplacementString.toCharArray();
        // int left = 0;
        // int right = charArray.length - 1;
        // while (left < right) {
        // char temp = charArray[left];
        // charArray[left] = charArray[right];
        // charArray[right] = temp;
        // left++;
        // right--;
        // }
        // String reversedString = String.valueOf(charArray);
        // System.out.println(
        // " After Replacement String : " + afterReplacementString + " Reversed String :
        // " + reversedString);
        // if (afterReplacementString.equals(reversedString))
        // return true;
        // return false;

        int start = 0;
        int end = s.length() - 1;
        while (start < end) {
            if (!isAlphaNumeric(s.charAt(start))) {
                start++;
                continue;
            }

            if (!isAlphaNumeric(s.charAt(end))) {
                end--;
                continue;
            }

            if (!isEqual(s.charAt(start), s.charAt(end)))
                return false;

            start++;
            end--;
        }
        return true;
    }

    public boolean isAlphaNumeric(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
    }

    public boolean isEqual(char a, char b) {
        if (a == b)
            return true;

        if (a >= 'a' && a <= 'z') {
            a = (char) (a - 'a' + 'A');
        }

        if (b >= 'a' && b <= 'z') {
            b = (char) (b - 'a' + 'A');
        }

        return a == b;
    }
}
