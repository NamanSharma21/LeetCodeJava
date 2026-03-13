import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Datastructures.ListNode;

class LeetCodeProblems {
    public static void main(String[] args) {
        // makeFancyString("leeetcodeee");
        // twoSum(new int[] { 2, 7, 11, 15 }, 9);
        // System.out.println("Is Valid String : " + isValid("(]"));
        // System.out.println("Is Valid String : " + isValid("()[]{}"));
        // System.out.println("Is Valid String : " + isValid("([)]"));
        // System.out.println("Is Valid String : " + isValidSolution("([)]"));
        // System.out.println("Is Valid String : " + isValidSolution("()[]{}"));
        // System.out.println("Is Valid String : " + isValidSolution("(([)]"));
        // System.out.println("Is Valid String : " + isValidSolution("([])"));
        // System.out.println("Is Valid String : " + isValidSolution("[[[]"));
        System.out.println("Is Valid String : " + isValidSolution("(([]){})"));
    }

    public static String makeFancyString(String s) {
        char[] inputCharArray = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        char nextChar = inputCharArray[0];
        int counter = 0;
        for (int i = 0; i < inputCharArray.length; i++) {
            if (nextChar == inputCharArray[i]) {
                counter++;
            } else {
                nextChar = inputCharArray[i];
                counter = 1;
            }
            if (counter < 3) {
                sb.append(nextChar);
            }
            System.out.println("Next Char : " + nextChar + " Counter : " + counter);
        }
        System.out.println("Final String : " + sb.toString());
        return sb.toString();
    }

    public static int[] twoSum(int[] nums, int target) {
        int numArrayLength = nums.length;
        int[] twoSum = new int[2];
        for (int i = 0; i < numArrayLength; i++) {
            int first = nums[i];
            for (int y = i + 1; y < numArrayLength; y++) {
                int second = nums[y];
                if (first + second == target) {
                    twoSum[0] = i;
                    twoSum[1] = y;
                }
            }
        }
        System.out.println(Arrays.toString(twoSum));
        return twoSum;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode sumNode = new ListNode();
        while (l1.next != null && l2.next != null) {

        }
        return null;
    }

    /**
     * 20. Valid Parentheses
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
     */

    public static boolean isValid(String s) {
        HashMap<Character, Integer> openingCharacterMap = new HashMap<>();
        HashMap<Character, Integer> closingCharacterMap = new HashMap<>();
        HashMap<Character, Character> characterMap = new HashMap<>();

        characterMap.put(Character.valueOf('('), ')');
        characterMap.put(Character.valueOf('{'), '}');
        characterMap.put(Character.valueOf('['), ']');

        openingCharacterMap.put(Character.valueOf('{'), 0);
        closingCharacterMap.put(Character.valueOf('}'), 0);
        openingCharacterMap.put(Character.valueOf('['), 0);
        closingCharacterMap.put(Character.valueOf(']'), 0);

        openingCharacterMap.put(Character.valueOf('('), 0);
        closingCharacterMap.put(Character.valueOf(')'), 0);
        openingCharacterMap.put(Character.valueOf('{'), 0);
        closingCharacterMap.put(Character.valueOf('}'), 0);
        openingCharacterMap.put(Character.valueOf('['), 0);
        closingCharacterMap.put(Character.valueOf(']'), 0);

        char[] charArray = s.toCharArray();
        boolean isValidString = false;
        for (int i = 0; i < charArray.length - 1; i++) {
            char firstChar = charArray[i];
            boolean isFirstOpeningChar = openingCharacterMap.containsKey(firstChar);
            boolean isFirstClosingChar = closingCharacterMap.containsKey(firstChar);

            for (int y = 1; y < charArray.length; y++) {
                char secondChar = charArray[y];
                boolean isSecondOpeningChar = openingCharacterMap.containsKey(secondChar);
                boolean isSecondClosingChar = closingCharacterMap.containsKey(secondChar);

                if (isFirstOpeningChar && isSecondClosingChar) {
                    boolean isMatchingParenthesis = characterMap.get(firstChar) == Character.valueOf(secondChar);
                    System.out.println(
                            "First Opening & Second Closing Char isMatchingParenthesis : " + isMatchingParenthesis);
                    if (isMatchingParenthesis) {
                        isValidString = true;
                        break;
                    }
                } else if (isFirstClosingChar) {
                    boolean isMatchingParenthesis = characterMap.values().stream()
                            .filter(it -> (it == Character.valueOf(firstChar)))
                            .findAny().isPresent();
                    // System.out.println("First Closing & Second Closing Char isMatchingParenthesis
                    // : "+isMatchingParenthesis);
                    if (isMatchingParenthesis) {
                        isValidString = true;
                    }
                } else {
                    isValidString = false;
                    return isValidString;
                }
                System.out.println("First Char : " + firstChar + " Second Char : " + secondChar
                        + " isFirstOpeningChar : " + isFirstOpeningChar + " isFirstClosingChar : " + isFirstClosingChar
                        + " isSecondOpeningChar : " + isSecondOpeningChar + " isSecondClosingChar : "
                        + isSecondClosingChar + " isValidString : " + isValidString);
            }
        }
        return isValidString;
    }

    public static boolean isValidSolution(String s) {
        if (!(s.length() % 2 == 0)) {
            return false;
        }
        String nextValidChar = "";
        char[] charArray = s.toCharArray();
        for (int i = 0; i < charArray.length; i++) {

            if (charArray[i] == '(') {
                nextValidChar = ")" + nextValidChar;
            } else if (charArray[i] == '[') {
                nextValidChar = "]" + nextValidChar;
            } else if (charArray[i] == '{') {
                nextValidChar = "}" + nextValidChar;
            } else {
                if (nextValidChar.isEmpty()) {
                    System.out.println("Current Char : " + charArray[i] + " nextValidChar Is Empty");
                    return false;
                } else if (!(nextValidChar.isEmpty()) && nextValidChar.charAt(0) == charArray[i]) {
                    System.out.println("Current Char : " + charArray[i] + " nextValidChar matches");
                    nextValidChar = nextValidChar.replaceFirst(String.valueOf("\\" + charArray[i]), "");
                    System.out.println("Replacing " + charArray[i] + " nextValidChar : " + nextValidChar);
                } else {
                    System.out.println("Current Char : " + charArray[i] + " nextValidChar : " + nextValidChar);
                    return false;
                }
            }
            if (nextValidChar.length() > (s.length() / 2)) {
                System.out.println("Stack Length Greater Than Half Of Vaild String");
                return false;
            }
            System.out.println("Current Char : " + charArray[i] + " nextValidChar : " + nextValidChar);
        }

        return nextValidChar.isEmpty();
    }

    public int removeDuplicates(int[] nums) {
        return 0;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode slow = head, fast = head;

        for (int i = 0; i < n; i++) {
            if (fast.next != null) {
                fast = fast.next;
            }

            if (fast == null) {
                return head.next;
            }
            while (slow.next != null || fast.next != null) {
                fast = fast.next;
                slow = slow.next;
            }

            slow.next = slow.next.next;
        }
        return head;
    }

    public ListNode reverseList(ListNode head) {
        // [1,2,3,4,5]
        ListNode newHead = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = newHead;
            newHead = head;
            head = next;
        }
        return newHead;
    }

    public boolean isPalindrome(ListNode head) {

        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode prev = null;
        ListNode curr = head;
        while (slow.next != null) {
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }

        ListNode p1 = head;
        while (slow.next != null) {
            if (p1.val != slow.val) {
                return false;
            }
            slow = slow.next;
            p1 = p1.next;
        }

        return true;
    }
}