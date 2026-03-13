package Math;

import java.util.ArrayList;
import java.util.List;

public class FizzBuzz {
    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz();
        fizzBuzz.fizzBuzz(3);
        fizzBuzz.fizzBuzz(5);
        fizzBuzz.fizzBuzz(15);
    }

    /*
     * 
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/102/
     * math/743/
     * 
     * Given an integer n, return a string array answer (1-indexed) where:
     * 
     * answer[i] == "FizzBuzz" if i is divisible by 3 and 5.
     * answer[i] == "Fizz" if i is divisible by 3.
     * answer[i] == "Buzz" if i is divisible by 5.
     * answer[i] == i (as a string) if none of the above conditions are true.
     * 
     * 
     * Example 1:
     * 
     * Input: n = 3
     * Output: ["1","2","Fizz"]
     * Example 2:
     * 
     * Input: n = 5
     * Output: ["1","2","Fizz","4","Buzz"]
     * Example 3:
     * 
     * Input: n = 15
     * Output:
     * ["1","2","Fizz","4","Buzz","Fizz","7","8","Fizz","Buzz","11","Fizz","13","14"
     * ,"FizzBuzz"]
     * 
     * 
     * Constraints:
     * 
     * 1 <= n <= 104
     * 
     */

    public List<String> fizzBuzz(int n) {
        List<String> fizzBuzzList = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            boolean isDivisibleBy3 = i % 3 == 0;
            boolean isDivisibleBy5 = i % 5 == 0;
            if (isDivisibleBy3 && isDivisibleBy5) {
                fizzBuzzList.add("FizzBuzz");
            } else if (isDivisibleBy3) {
                fizzBuzzList.add("Fizz");
            } else if (isDivisibleBy5) {
                fizzBuzzList.add("Buzz");
            } else {
                fizzBuzzList.add("" + i);
            }
        }
        System.out.println("" + fizzBuzzList);
        return fizzBuzzList;
    }
}
