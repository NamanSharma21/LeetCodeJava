package Array;

import java.util.ArrayList;
import java.util.List;

public class PascalsTriangle_II {
    public static void main(String[] args) {
        PascalsTriangle_II pascalsTriangle_II = new PascalsTriangle_II();
        System.out.println("PascalsTriangle_II : " + pascalsTriangle_II.getRow(3));
    }

    /*
     * https://leetcode.com/problems/pascals-triangle-ii/description/?envType=
     * problem-list-v2&envId=array
     * 
     * Given an integer rowIndex, return the rowIndexth (0-indexed) row of the
     * Pascal's triangle.
     * 
     * In Pascal's triangle, each number is the sum of the two numbers directly
     * above it as shown:
     * 
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: rowIndex = 3
     * Output: [1,3,3,1]
     * Example 2:
     * 
     * Input: rowIndex = 0
     * Output: [1]
     * Example 3:
     * 
     * Input: rowIndex = 1
     * Output: [1,1]
     * 
     * 
     * Constraints:
     * 
     * 0 <= rowIndex <= 33
     * 
     * 
     * Follow up: Could you optimize your algorithm to use only O(rowIndex) extra
     * space?
     */

    public List<Integer> getRow(int rowIndex) {
        // List<List<Integer>> pascalsTriangle = new ArrayList<>();
        // for (int i = 0; i <= rowIndex; i++) {
        // List<Integer> list = new ArrayList<>();
        // for (int j = 0; j <= i; j++) {
        // if (j == 0 || j == i)
        // list.add(1);
        // else
        // list.add(pascalsTriangle.get(i - 1).get(j - 1) + pascalsTriangle.get(i -
        // 1).get(j));
        // }
        // pascalsTriangle.add(list);
        // }
        // System.out.println("" + pascalsTriangle);
        // return pascalsTriangle.get(rowIndex);

        long current = 1;
        List<Integer> row = new ArrayList<>();
        row.add(1);
        for (int i = 1; i <= rowIndex; i++) {
            current = current * (rowIndex - i + 1) / i;
            row.add((int) current);
        }

        System.out.println("" + row);
        return row;
    }
}