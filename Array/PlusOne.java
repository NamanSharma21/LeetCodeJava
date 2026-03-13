package Array;

import java.util.Arrays;

public class PlusOne {
    public static void main(String[] args) {
        PlusOne plusOne = new PlusOne();
        System.out.println("Plus One : " + Arrays.toString(plusOne.plusOne(new int[] { 1, 2, 3 })));
        System.out.println("Plus One : " + Arrays.toString(plusOne.plusOne(new int[] { 9, 9, 9 })));
    }

    /*
     * You are given a large integer represented as an integer array digits, where
     * each digits[i] is the ith digit of the integer. The digits are ordered from
     * most significant to least significant in left-to-right order. The large
     * integer does not contain any leading 0's.
     * 
     * Increment the large integer by one and return the resulting array of digits.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: digits = [1,2,3]
     * Output: [1,2,4]
     * Explanation: The array represents the integer 123.
     * Incrementing by one gives 123 + 1 = 124.
     * Thus, the result should be [1,2,4].
     * Example 2:
     * 
     * Input: digits = [4,3,2,1]
     * Output: [4,3,2,2]
     * Explanation: The array represents the integer 4321.
     * Incrementing by one gives 4321 + 1 = 4322.
     * Thus, the result should be [4,3,2,2].
     * Example 3:
     * 
     * Input: digits = [9]
     * Output: [1,0]
     * Explanation: The array represents the integer 9.
     * Incrementing by one gives 9 + 1 = 10.
     * Thus, the result should be [1,0].
     * 
     * 
     * Constraints:
     * 
     * 1 <= digits.length <= 100
     * 0 <= digits[i] <= 9
     * digits does not contain any leading 0's.
     */
    public int[] plusOne(int[] digits) {
        // int arrayLength = digits.length;
        // int power = Double.valueOf(Math.pow(10, arrayLength - 1)).intValue();
        // System.out.println("Array Length : " + arrayLength + " Power : " + power);

        // int number = 0;
        // int numberLength = 0;
        // for (int i = 0; i < arrayLength; i++) {
        // number += digits[i] * power;
        // power /= 10;
        // numberLength++;
        // System.out.println("Number : " + number + " Power : " + power);
        // }
        // number += 1;
        // int numBackup = number;
        // System.out.println("After increment : " + number + " Number Length : " +
        // numberLength);

        // int numCount = 1;
        // power = 10;
        // while (numBackup > 0 ) {
        // numBackup = numBackup / power;
        // power *= 10;
        // numCount++;
        // }
        // int[] plusOne = new int[numCount];
        // power = Double.valueOf(Math.pow(10, numCount-1)).intValue();
        // for (int i = 0; i < numCount; i++) {
        // number = number / power;
        // plusOne[i] = number;
        // power /= 10;
        // }
        // return plusOne;

        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }
        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;
    }
}
