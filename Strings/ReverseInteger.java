package Strings;

public class ReverseInteger {
    public static void main(String[] args) {
        ReverseInteger reverseInteger = new ReverseInteger();
        // reverseInteger.reverse(123);
        System.out.println("Reverse Integer : " + reverseInteger.reverse(-123));
    }

    /*
     * Given a signed 32-bit integer x, return x with its digits reversed. If
     * reversing x causes the value to go outside the signed 32-bit integer range
     * [-231, 231 - 1], then return 0.
     * 
     * Assume the environment does not allow you to store 64-bit integers (signed or
     * unsigned).
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: x = 123
     * Output: 321
     * Example 2:
     * 
     * Input: x = -123
     * Output: -321
     * Example 3:
     * 
     * Input: x = 120
     * Output: 21
     * 
     * 
     * Constraints:
     * 
     * -231 <= x <= 231 - 1
     */
    public int reverse(int x) {
        int sign = x > 0 ? 1 : -1;
        int copy = Math.abs(x);
        int power = 10;
        int newNumber = 0;
        while (copy > 0) {
            if (Integer.MAX_VALUE / 10 < newNumber || (Integer.MAX_VALUE - copy % 10) < newNumber * 10) {
                return 0;
            }
            int rem = copy % power;
            copy = copy / power;
            newNumber = newNumber * power + rem;
            System.out.println("No : " + copy + " Power : " + power + " Rem : " + rem + " New : " + newNumber);
        }
        return sign * newNumber;

        // int sign = x < 0 ? -1 : 1;
        // x = Math.abs(x);
        // int res = 0;
        // while (x > 0) {
        //     if (Integer.MAX_VALUE / 10 < res || (Integer.MAX_VALUE - x % 10) < res * 10) {
        //         return 0;
        //     }
        //     res = res * 10 + x % 10;
        //     x /= 10;
        // }
        
        // return sign * res;
    }
}
