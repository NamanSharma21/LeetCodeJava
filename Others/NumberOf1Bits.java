package Others;

public class NumberOf1Bits {
    public static void main(String[] args) {
        NumberOf1Bits numberOf1Bits = new NumberOf1Bits();
        System.out.println("Hamming Weight : " + numberOf1Bits.hammingWeight(11));
    }

    /*
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/99/
     * others/565/
     * 
     * 
     * Given a positive integer n, write a function that returns the number of set
     * bits in its binary representation (also known as the Hamming weight).
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: n = 11
     * 
     * Output: 3
     * 
     * Explanation:
     * 
     * The input binary string 1011 has a total of three set bits.
     * 
     * Example 2:
     * 
     * Input: n = 128
     * 
     * Output: 1
     * 
     * Explanation:
     * 
     * The input binary string 10000000 has a total of one set bit.
     * 
     * Example 3:
     * 
     * Input: n = 2147483645
     * 
     * Output: 30
     * 
     * Explanation:
     * 
     * The input binary string 1111111111111111111111111111101 has a total of thirty
     * set bits.
     * 
     * 
     * 
     * Constraints:
     * 
     * 1 <= n <= 231 - 1
     * 
     * 
     * Follow up: If this function is called many times, how would you optimize it?
     * 
     */

    public int hammingWeight(int n) {
        int hammingWeight = 0;
        // while (n != 0) {
        // int remainder = n % 2;
        // n /= 2;
        // if (remainder == 1) {
        // hammingWeight++;
        // }
        // System.out.println("N : " + n + " R : " + remainder);
        // }

        while (n != 0) {
            n &= (n - 1);
            hammingWeight++;
            System.out.println("" + n + "---" + hammingWeight);
        }
        return hammingWeight;
    }
}
