package Others;

public class HammingDistance {
    public static void main(String[] args) {
        HammingDistance hammingDistance = new HammingDistance();
        hammingDistance.hammingDistance(1, 4);
    }

    /*
     * 
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/99/
     * others/762/
     * 
     * 
     * The Hamming distance between two integers is the number of positions at which
     * the corresponding bits are different.
     * 
     * Given two integers x and y, return the Hamming distance between them.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: x = 1, y = 4
     * Output: 2
     * Explanation:
     * 1 (0 0 0 1)
     * 4 (0 1 0 0)
     * -----↑---↑
     * The above arrows point to positions where the corresponding bits are
     * different.
     * Example 2:
     * 
     * Input: x = 3, y = 1
     * Output: 1
     * 
     * 
     * Constraints:
     * 
     * 0 <= x, y <= 231 - 1
     * 
     * 
     * Note: This question is the same as 2220: Minimum Bit Flips to Convert Number.
     * 
     */

    public int hammingDistance(int x, int y) {
        int count = 0;
        int hammingDistance = 0;
        while (count < 32) {
            boolean isXSet = (x & (1 << count)) != 0;
            boolean isYSet = (y & (1 << count)) != 0;
            System.out.println("X : " + isXSet + " isYSet : " + isYSet + " H : " + hammingDistance);
            if ((!isXSet && isYSet) || (isXSet && !isYSet)) {
                hammingDistance++;
            }
            count++;
        }
        return hammingDistance;
    }
}
