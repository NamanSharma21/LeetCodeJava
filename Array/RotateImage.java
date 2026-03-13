package Array;

import java.util.Arrays;

public class RotateImage {
    public static void main(String[] args) {
        RotateImage rotateImage = new RotateImage();
        // rotateImage.rotate(new int[][] {
        // { 1, 2, 3 },
        // { 4, 5, 6 },
        // { 7, 8, 9 }
        // });
        rotateImage.rotate(new int[][] {
                { 5, 1, 9, 11 },
                { 2, 4, 8, 10 },
                { 13, 3, 6, 7 },
                { 15, 14, 12, 16 }
        });
    }

    /*
     * You are given an n x n 2D matrix representing an image, rotate the image by
     * 90 degrees (clockwise).
     * 
     * You have to rotate the image in-place, which means you have to modify the
     * input 2D matrix directly. DO NOT allocate another 2D matrix and do the
     * rotation.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * Output: [[7,4,1],[8,5,2],[9,6,3]]
     * Example 2:
     * 
     * 
     * Input: matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
     * Output: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
     * 
     * 
     * Constraints:
     * 
     * n == matrix.length == matrix[i].length
     * 1 <= n <= 20
     * -1000 <= matrix[i][j] <= 1000
     */

    public void rotate(int[][] matrix) {
        int matrixLength = matrix.length - 1;
        int level = 0;
        int totalNumberOFLevels = matrix.length / 2;
        while (level < totalNumberOFLevels) {
            for (int i = level; i < matrixLength; i++) {
                swap(matrix, level, i, i, matrixLength);
                swap(matrix, level, i, matrixLength, matrixLength - i + level);
                swap(matrix, level, i, matrixLength - i + level, level);
            }
            level++;
            matrixLength--;
        }
        System.out.println("" + Arrays.deepToString(matrix));
    }

    public void swap(int[][] matrix, int first, int second, int third, int fourth) {
        int temp = matrix[first][second];
        matrix[first][second] = matrix[third][fourth];
        matrix[third][fourth] = temp;
    }

}
