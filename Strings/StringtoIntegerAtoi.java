package Strings;

public class StringtoIntegerAtoi {
    public static void main(String[] args) {
        StringtoIntegerAtoi stringtoIntegerAtoi = new StringtoIntegerAtoi();
        System.out.println("StringToIntegerAtoi 42 : " + stringtoIntegerAtoi.myAtoi("42"));
        System.out.println("StringToIntegerAtoi  -042 : " + stringtoIntegerAtoi.myAtoi(" -042"));
        System.out.println("StringToIntegerAtoi 1337c0d3 : " + stringtoIntegerAtoi.myAtoi("1337c0d3"));
        System.out.println("StringToIntegerAtoi 0-1 : " + stringtoIntegerAtoi.myAtoi("0-1"));
        System.out.println("StringToIntegerAtoi words and 987 : " + stringtoIntegerAtoi.myAtoi("words and 987"));
        System.out
                .println("StringToIntegerAtoi words and -91283472332 : " + stringtoIntegerAtoi.myAtoi("-91283472332"));
        System.out.println("StringToIntegerAtoi words and +1 : " + stringtoIntegerAtoi.myAtoi("+1"));
        System.out.println("StringToIntegerAtoi words and +-12 : " + stringtoIntegerAtoi.myAtoi("+-12"));
    }

    /*
     * Implement the myAtoi(string s) function, which converts a string to a 32-bit
     * signed integer.
     * 
     * The algorithm for myAtoi(string s) is as follows:
     * 
     * Whitespace: Ignore any leading whitespace (" ").
     * Signedness: Determine the sign by checking if the next character is '-' or
     * '+', assuming positivity if neither present.
     * Conversion: Read the integer by skipping leading zeros until a non-digit
     * character is encountered or the end of the string is reached. If no digits
     * were read, then the result is 0.
     * Rounding: If the integer is out of the 32-bit signed integer range [-231, 231
     * - 1], then round the integer to remain in the range. Specifically, integers
     * less than -231 should be rounded to -231, and integers greater than 231 - 1
     * should be rounded to 231 - 1.
     * Return the integer as the final result.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: s = "42"
     * 
     * Output: 42
     * 
     * Explanation:
     * 
     * The underlined characters are what is read in and the caret is the current
     * reader position.
     * Step 1: "42" (no characters read because there is no leading whitespace)
     * ^
     * Step 2: "42" (no characters read because there is neither a '-' nor '+')
     * ^
     * Step 3: "42" ("42" is read in)
     * ^
     * Example 2:
     * 
     * Input: s = " -042"
     * 
     * Output: -42
     * 
     * Explanation:
     * 
     * Step 1: "   -042" (leading whitespace is read and ignored)
     * ^
     * Step 2: "   -042" ('-' is read, so the result should be negative)
     * ^
     * Step 3: "   -042" ("042" is read in, leading zeros ignored in the result)
     * ^
     * Example 3:
     * 
     * Input: s = "1337c0d3"
     * 
     * Output: 1337
     * 
     * Explanation:
     * 
     * Step 1: "1337c0d3" (no characters read because there is no leading
     * whitespace)
     * ^
     * Step 2: "1337c0d3" (no characters read because there is neither a '-' nor
     * '+')
     * ^
     * Step 3: "1337c0d3" ("1337" is read in; reading stops because the next
     * character is a non-digit)
     * ^
     * Example 4:
     * 
     * Input: s = "0-1"
     * 
     * Output: 0
     * 
     * Explanation:
     * 
     * Step 1: "0-1" (no characters read because there is no leading whitespace)
     * ^
     * Step 2: "0-1" (no characters read because there is neither a '-' nor '+')
     * ^
     * Step 3: "0-1" ("0" is read in; reading stops because the next character is a
     * non-digit)
     * ^
     * Example 5:
     * 
     * Input: s = "words and 987"
     * 
     * Output: 0
     * 
     * Explanation:
     * 
     * Reading stops at the first non-digit character 'w'.
     * 
     * 
     * 
     * Constraints:
     * 
     * 0 <= s.length <= 200
     * s consists of English letters (lower-case and upper-case), digits (0-9), ' ',
     * '+', '-', and '.'.
     * 
     */
    public int myAtoi(String s) {
        // boolean isLeadingZero = true;
        // boolean appendSign = false;
        // boolean hasNumeric = false;
        // char[] charArray = s.toCharArray();
        // StringBuilder sb = new StringBuilder();
        // for (int i = 0; i < charArray.length; i++) {
        // if (charArray[i] == ' ') {
        // continue;
        // }
        // if (charArray[i] == '0' && isLeadingZero) {
        // isLeadingZero = false;
        // continue;
        // }
        // if (charArray[i] == '-' && !appendSign) {
        // appendSign = true;
        // continue;
        // }

        // if (charArray[i] == '+' && !appendSign) {
        // appendSign = true;
        // continue;
        // }
        // if (!isNumeric(charArray[i]))
        // break;
        // else {
        // isLeadingZero = false;
        // sb.append(charArray[i]);
        // hasNumeric = true;
        // }
        // System.out.println("Char : " + charArray[i] + " Number : " + sb.toString());
        // }
        // String finalNumber = sb.toString();
        // if (finalNumber.isEmpty()) {
        // return 0;
        // }
        // if (finalNumber.length() < 10) {
        // return appendSign ? -Integer.parseInt(finalNumber) :
        // Integer.parseInt(finalNumber);
        // } else {
        // return appendSign ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        // }
        if (s == null || s.isEmpty()) {
            return 0;
        }
        int i = -1;

        while (++i < s.length() && s.charAt(i) == ' ') {

        }

        if (i == s.length())
            return 0;

        boolean isPositive = s.charAt(i) != '-';
        if (s.charAt(i) == '-' || s.charAt(i) == '+') {
            i++;
        }

        int result = 0;
        for (; i < s.length(); i++) {
            int charNumber = s.charAt(i) - '0';
            if (charNumber < 0 || charNumber > 9) {
                break;
            }

            if (isPositive && ((Integer.MAX_VALUE / 10 + 7) - (result + charNumber) <= 0)) {
                return Integer.MAX_VALUE;
            }

            if (!isPositive && ((Integer.MIN_VALUE / 10 - 8) + (result + charNumber) >= 0)) {
                return Integer.MIN_VALUE;
            }

            result = result * 10 + charNumber;
        }

        return isPositive ? result : -result;
    }

    public boolean isAlphabet(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public boolean isNumeric(char c) {
        return (c >= '0' && c <= '9');
    }

}
