package Math;

public class PowerOfThree {
    public static void main(String[] args) {
        PowerOfThree powerOfThree = new PowerOfThree();
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThree(27));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThree(0));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThree(-1));
        System.out.println("Power Of 3 : " + powerOfThree.isPowerOfThree(45));
    }

    /*
     * 
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/102/
     * math/745/
     * 
     * 
     * Given an integer n, return true if it is a power of three. Otherwise, return
     * false.
     * 
     * An integer n is a power of three, if there exists an integer x such that n ==
     * 3x.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: n = 27
     * Output: true
     * Explanation: 27 = 33
     * Example 2:
     * 
     * Input: n = 0
     * Output: false
     * Explanation: There is no x where 3x = 0.
     * Example 3:
     * 
     * Input: n = -1
     * Output: false
     * Explanation: There is no x where 3x = (-1).
     * 
     * 
     * Constraints:
     * 
     * -231 <= n <= 231 - 1
     * 
     * 
     * Follow up: Could you solve it without loops/recursion?
     */

    public boolean isPowerOfThree(int n) {
        if (n < 3)
            return false;

        // int remainder = n;
        // int divisor = n;
        // while (divisor != 0) {
        // System.out.println("R : " + remainder + " D : " + divisor);
        // remainder = divisor % 3;
        // divisor = divisor / 3;
        // if (remainder != 0)
        // return false;
        // }
        // // if (n % 3 == 0)
        // // return true;
        // // else
        // // return false;
        // return true;
        // if (n < 1) return false;
        // while (n % 3 == 0) {
        // n /= 3;
        // }
        // return n == 1;
        System.out.println(""+(int) Math.pow(3, 19));
        return n > 0 && ((int) Math.pow(3, 19)) % n == 0;
    }
}
