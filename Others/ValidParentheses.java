package Others;

public class ValidParentheses {
    public static void main(String[] args) {
        ValidParentheses validParentheses = new ValidParentheses();
        System.out.println("Is ValidParentheses : " + validParentheses.isValid("()"));
        System.out.println("Is ValidParentheses : " + validParentheses.isValid("()[]{}"));
        System.out.println("Is ValidParentheses : " + validParentheses.isValid("(]"));
        System.out.println("Is ValidParentheses : " + validParentheses.isValid("([])"));
        System.out.println("Is ValidParentheses : " + validParentheses.isValid("([)]"));
    }

    /*
     * 
     * Given a string s containing just the characters '(', ')', '{', '}', '[' and
     * ']', determine if the input string is valid.
     * 
     * An input string is valid if:
     * 
     * Open brackets must be closed by the same type of brackets.
     * Open brackets must be closed in the correct order.
     * Every close bracket has a corresponding open bracket of the same type.
     * 
     * 
     * Example 1:
     * 
     * Input: s = "()"
     * 
     * Output: true
     * 
     * Example 2:
     * 
     * Input: s = "()[]{}"
     * 
     * Output: true
     * 
     * Example 3:
     * 
     * Input: s = "(]"
     * 
     * Output: false
     * 
     * Example 4:
     * 
     * Input: s = "([])"
     * 
     * Output: true
     * 
     * Example 5:
     * 
     * Input: s = "([)]"
     * 
     * Output: false
     * 
     * 
     * 
     * Constraints:
     * 
     * 1 <= s.length <= 104
     * s consists of parentheses only '()[]{}'.
     * 
     */

    public boolean isValid(String s) {
        if (!(s.length() % 2 == 0)) {
            return false;
        }
        char[] charArray = s.toCharArray();
        String nextValidChar = "";

        for (int i = 0; i < charArray.length; i++) {
            char nextChar = charArray[i];
            if (nextChar == '(') {
                nextValidChar = ")" + nextValidChar;
            } else if (nextChar == '[') {
                nextValidChar = "]" + nextValidChar;
            } else if (nextChar == '{') {
                nextValidChar = "}" + nextValidChar;
            } else {
                if (nextValidChar.isEmpty()) {
                    return false;
                } else if (!(nextValidChar.isEmpty()) && nextChar == nextValidChar.charAt(0)) {
                    nextValidChar = nextValidChar.replaceFirst("\\" + nextChar, "");
                } else {
                    return false;
                }
            }
            if (nextValidChar.length() > (s.length() / 2)) {
                return false;
            }
        }
        return nextValidChar.isEmpty();
    }
}
