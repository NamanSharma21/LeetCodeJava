package Others;

import java.util.ArrayList;
import java.util.List;

public class PascalsTriangle {
    public static void main(String[] args) {
        PascalsTriangle pascalsTriangle = new PascalsTriangle();
        pascalsTriangle.generate(5);
    }
    /*
     * 
     * Given an integer numRows, return the first numRows of Pascal's triangle.
     * 
     * In Pascal's triangle, each number is the sum of the two numbers directly
     * above it as shown:
     * 
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: numRows = 5
     * Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
     * Example 2:
     * 
     * Input: numRows = 1
     * Output: [[1]]
     * 
     * 
     * Constraints:
     * 
     * 1 <= numRows <= 30
     * 
     */

    public List<List<Integer>> generate(int numRows) {
        // int row = 1;
        // List<List<Integer>> pascalTriangle = new ArrayList<>(numRows);
        // while (row <= numRows) {
        // List<Integer> terminalElements = new ArrayList<>();
        // int counter = 0;
        // while (counter < row) {
        // if (counter == 0 || counter == (row - 1)) {
        // terminalElements.add(counter, 1);
        // } else {
        // terminalElements.add(counter, 0);
        // }
        // counter++;
        // }
        // pascalTriangle.add(row - 1, terminalElements);
        // row++;
        // }

        // row = 0;

        // List<Integer> last = pascalTriangle.get(row);
        // while (row < numRows) {
        // List<Integer> list = pascalTriangle.get(row);
        // int first = 0, second = 1;
        // for (int i = 0; i < list.size(); i++) {
        // if (list.get(i) == 0 && last != null && first < last.size() && second <
        // last.size()) {
        // int sum = last.get(first) + last.get(second);
        // System.out.println("First : " + last.get(first) + " Second : " +
        // last.get(second) + " Sum : " + sum);
        // list.set(i, sum);
        // first++;
        // second++;
        // }
        // }
        // last = list;
        // row++;
        // }

        // System.out.println("" + pascalTriangle);
        // return pascalTriangle;

        List<List<Integer>> pascalsTriangle = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i)
                    list.add(1);
                else
                    list.add(pascalsTriangle.get(i - 1).get(j - 1) + pascalsTriangle.get(i - 1).get(j));
            }
            pascalsTriangle.add(list);
        }
        System.out.println("" + pascalsTriangle);
        return pascalsTriangle;
    }
}
