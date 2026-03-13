package Math;

import java.util.Arrays;

public class CountPrimes {
    public static void main(String[] args) {
        CountPrimes countPrimes = new CountPrimes();
        System.out.println("Count Prime : " + countPrimes.countPrimes(10));
        System.out.println("Count Prime : " + countPrimes.countPrimes(0));
        System.out.println("Count Prime : " + countPrimes.countPrimes(1));
    }

    /*
     * 
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/102/
     * math/744/
     * 
     * Given an integer n, return the number of prime numbers that are strictly less
     * than n.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: n = 10
     * Output: 4
     * Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.
     * Example 2:
     * 
     * Input: n = 0
     * Output: 0
     * Example 3:
     * 
     * Input: n = 1
     * Output: 0
     * 
     * 
     * Constraints:
     * 
     * 0 <= n <= 5 * 106
     * 
     */

    public int countPrimes(int n) {
        // int factorCount = 0;
        // int primeCount = 0;
        // List<Integer> primeList = new ArrayList<>();
        // for (int i = 2; i < n; i++) {
        // for (int y = 1; y <= i; y++) {
        // if (i % y == 0) {
        // factorCount++;
        // }
        // }

        // if (factorCount == 2) {
        // primeCount++;
        // primeList.add(i);
        // }
        // // System.out.println("I : " + i + " Prime Count : " + primeCount + " Factor
        // Count : " + factorCount);
        // factorCount = 0;
        // }
        // System.out.println("Prime List : " + primeList);
        // return primeCount;
        if (n <= 2)
            return 0;
        boolean[] isPrime = new boolean[n];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        int count = 0;
        for (int i = 2; i * i < n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        for (int i = 2; i < n; i++) {
            if (isPrime[i])
                count++;
        }
        return count;
    }

    public boolean isPrime(int num) {
        if (num < 2)
            return false;
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0)
                return false;
        }
        return true;
    }
}
