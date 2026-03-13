package Array;

public class IslandPerimeter {
    public static void main(String[] args) {
        IslandPerimeter islandPerimeter = new IslandPerimeter();
        System.out.println("IslandPerimeter : " + islandPerimeter
                .islandPerimeter(new int[][] { { 0, 1, 0, 0 }, { 1, 1, 1, 0 }, { 0, 1, 0, 0 }, { 1, 1, 0, 0 } }));
    }

    /**
     * 
     * https://leetcode.com/problems/island-perimeter/description/?envType=problem-list-v2&envId=array
     * 
     * You are given row x col grid representing a map where grid[i][j] = 1
     * represents land and grid[i][j] = 0 represents water.
     * 
     * Grid cells are connected horizontally/vertically (not diagonally). The grid
     * is completely surrounded by water, and there is exactly one island (i.e., one
     * or more connected land cells).
     * 
     * The island doesn't have "lakes", meaning the water inside isn't connected to
     * the water around the island. One cell is a square with side length 1. The
     * grid is rectangular, width and height don't exceed 100. Determine the
     * perimeter of the island.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: grid = [[0,1,0,0],[1,1,1,0],[0,1,0,0],[1,1,0,0]]
     * Output: 16
     * Explanation: The perimeter is the 16 yellow stripes in the image above.
     * Example 2:
     * 
     * Input: grid = [[1]]
     * Output: 4
     * Example 3:
     * 
     * Input: grid = [[1,0]]
     * Output: 4
     * 
     * 
     * Constraints:
     * 
     * row == grid.length
     * col == grid[i].length
     * 1 <= row, col <= 100
     * grid[i][j] is 0 or 1.
     * There is exactly one island in grid.
     * 
     */

    public int islandPerimeter(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int perimeterCount = 0;
        // for (int i = 0; i < row; i++) {
        // for (int y = 0; y < col; y++) {
        // // System.out.print("" + grid[i][y]);
        // if (grid[i][y] == 1) {
        // if (i == 0 || grid[i - 1][y] == 0) {
        // perimeterCount++;
        // }
        // if (i == row - 1 || grid[i + 1][y] == 0) {
        // perimeterCount++;
        // }
        // if (y == 0 || grid[i][y - 1] == 0) {
        // perimeterCount++;
        // }
        // if (y == col - 1 || grid[i][y + 1] == 0) {
        // perimeterCount++;
        // }
        // }
        // }
        // System.out.println("------------" + perimeterCount);
        // }

        // /**
        // *
        // * [R,C+1]
        // * [R,C-1]
        // * [R-1,C]
        // * [R+1,C]
        // *
        // */

        for (int i = 0; i < row; i++) {
            for (int y = 0; y < col; y++) {
                if (grid[i][y] == 1) {
                    perimeterCount += 4;
                    if (y + 1 < col && grid[i][y + 1] == 1) {
                        perimeterCount -= 2;
                    }
                    if (i + 1 < row && grid[i + 1][y] == 1) {
                        perimeterCount -= 2;
                    }
                }
            }
        }

        return perimeterCount;
    }
}
