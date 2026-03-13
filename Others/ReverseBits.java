package Others;

public class ReverseBits {
    public static void main(String[] args) {
        ReverseBits reverseBits = new ReverseBits();
        System.out.println("Reverse Bits : " + reverseBits.reverseBits(43261596));
    }

    /*
     * 
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/99/
     * others/648/
     * 
     * 
     * Reverse bits of a given 32 bits signed integer.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: n = 43261596
     * 
     * Output: 964176192
     * 
     * Explanation:
     * 
     * Integer Binary
     * 43261596 00000010100101000001111010011100
     * 964176192 00111001011110000010100101000000
     * 
     * Example 2:
     * 
     * Input: n = 2147483644
     * 
     * Output: 1073741822
     * 
     * Explanation:
     * 
     * Integer Binary
     * 2147483644 01111111111111111111111111111100
     * 1073741822 00111111111111111111111111111110
     * 
     * 
     * Constraints:
     * 
     * 0 <= n <= 231 - 2
     * n is even.
     * 
     * 
     * Follow up: If this function is called many times, how would you optimize it?
     * 
     */

    public int reverseBits(int n) {
        // int forward = 0;
        // int reverse = 31;
        // int newNumber = 0;
        // StringBuilder sb = new StringBuilder();
        // StringBuilder sb1 = new StringBuilder();
        // while (forward < 31) {
        // boolean isBitSet = (n & (1 << forward)) != 0;
        // sb.append((isBitSet ? 1 : 0) + "");
        // if (isBitSet) {
        // newNumber |= (1 << reverse);
        // }
        // boolean isBitSet1 = (newNumber & (1 << reverse)) != 0;
        // sb1.append((isBitSet1 ? 1 : 0) + "");
        // forward++;
        // reverse--;
        // System.out.println("---"+newNumber);
        // }
        // System.out.println("New Number : " + newNumber + " : " + sb.toString() + "\n
        // : " + sb1.toString());
        // return newNumber;

        int result = 0;

        for (int i = 0; i < 32; i++) {
            result <<= 1;
            result |= (n & 1);
            n >>>= 1;
        }

        return result;
    }
}
