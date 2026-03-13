package Array;

import java.util.HashSet;
import java.util.Set;

public class ValidSudoku {
    public static void main(String[] args) {
        ValidSudoku validSudoku = new ValidSudoku();
        // validSudoku.isValidSudoku(new char[][] {
        // { '5', '3', '.', '.', '7', '.', '.', '.', '.' },
        // { '6', '.', '.', '1', '9', '5', '.', '.', '.' },
        // { '.', '9', '8', '.', '.', '.', '.', '6', '.' },
        // { '8', '.', '.', '.', '6', '.', '.', '.', '3' },
        // { '4', '.', '.', '8', '.', '3', '.', '.', '1' },
        // { '7', '.', '.', '.', '2', '.', '.', '.', '6' },
        // { '.', '6', '.', '.', '.', '.', '2', '8', '.' },
        // { '.', '.', '.', '4', '1', '9', '.', '.', '5' },
        // { '.', '.', '.', '.', '8', '.', '.', '7', '9' }
        // });

        // validSudoku.isValidSudoku(new char[][] {
        // { '8', '3', '.', '.', '7', '.', '.', '.', '.' },
        // { '6', '.', '.', '1', '9', '5', '.', '.', '.' },
        // { '.', '9', '8', '.', '.', '.', '.', '6', '.' },
        // { '8', '.', '.', '.', '6', '.', '.', '.', '3' },
        // { '4', '.', '.', '8', '.', '3', '.', '.', '1' },
        // { '7', '.', '.', '.', '2', '.', '.', '.', '6' },
        // { '.', '6', '.', '.', '.', '.', '2', '8', '.' },
        // { '.', '.', '.', '4', '1', '9', '.', '.', '5' },
        // { '.', '.', '.', '.', '8', '.', '.', '7', '9' }
        // });
        System.out.println("Is Valid Sudoku : " + validSudoku.isValidSudoku(new char[][] {
                { '.', '.', '.', '.', '5', '.', '.', '1', '.' },
                { '.', '4', '.', '3', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '3', '.', '.', '1' },
                { '8', '.', '.', '.', '.', '.', '.', '2', '.' },
                { '.', '.', '2', '.', '7', '.', '.', '.', '.' },
                { '.', '1', '5', '.', '.', '.', '.', '.', '.' },
                { '.', '.', '.', '.', '.', '2', '.', '.', '.' },
                { '.', '2', '.', '9', '.', '.', '.', '.', '.' },
                { '.', '.', '4', '.', '.', '.', '.', '.', '.' }
        }));
        ;

    }

    /*
     * Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be
     * validated according to the following rules:
     * 
     * Each row must contain the digits 1-9 without repetition.
     * Each column must contain the digits 1-9 without repetition.
     * Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9
     * without repetition.
     * Note:
     * 
     * A Sudoku board (partially filled) could be valid but is not necessarily
     * solvable.
     * Only the filled cells need to be validated according to the mentioned rules.
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: board =
     * [["5","3",".",".","7",".",".",".","."]
     * ,["6",".",".","1","9","5",".",".","."]
     * ,[".","9","8",".",".",".",".","6","."]
     * ,["8",".",".",".","6",".",".",".","3"]
     * ,["4",".",".","8",".","3",".",".","1"]
     * ,["7",".",".",".","2",".",".",".","6"]
     * ,[".","6",".",".",".",".","2","8","."]
     * ,[".",".",".","4","1","9",".",".","5"]
     * ,[".",".",".",".","8",".",".","7","9"]]
     * Output: true
     * Example 2:
     * 
     * Input: board =
     * [["8","3",".",".","7",".",".",".","."]
     * ,["6",".",".","1","9","5",".",".","."]
     * ,[".","9","8",".",".",".",".","6","."]
     * ,["8",".",".",".","6",".",".",".","3"]
     * ,["4",".",".","8",".","3",".",".","1"]
     * ,["7",".",".",".","2",".",".",".","6"]
     * ,[".","6",".",".",".",".","2","8","."]
     * ,[".",".",".","4","1","9",".",".","5"]
     * ,[".",".",".",".","8",".",".","7","9"]]
     * Output: false
     * Explanation: Same as Example 1, except with the 5 in the top left corner
     * being modified to 8. Since there are two 8's in the top left 3x3 sub-box, it
     * is invalid.
     * 
     * 
     * Constraints:
     * 
     * board.length == 9
     * board[i].length == 9
     * board[i][j] is a digit 1-9 or '.'.
     */

    public boolean isValidSudoku(char[][] board) {
        int rowLength = board.length;
        int columnLength = board[0].length;
        // Map<Character, Integer> rowElementMap = new HashMap<>();
        // Map<Character, Integer> columnElementMap = new HashMap<>();
        // for (int i = 0; i < rowLength; i++) {
        // for (int y = 0; y < columnLength; y++) {
        // if (board[i][y] != '.') {
        // if (columnElementMap.get(board[i][y]) == null) {
        // columnElementMap.put(board[i][y], 1);
        // } else {
        // System.out.println("Found Duplicate : " + board[y][i] + " In Column : " + i +
        // "," + y);
        // return false;
        // }
        // }
        // // System.out.print(board[i][y]);
        // }
        // columnElementMap.clear();
        // System.out.println("");
        // }

        // for (int i = 0; i < columnLength; i++) {
        // for (int y = 0; y < rowLength; y++) {
        // if (board[y][i] != '.') {
        // if (rowElementMap.get(board[y][i]) == null) {
        // rowElementMap.put(board[y][i], 1);
        // } else {
        // System.out.println("Found Duplicate : " + board[y][i] + " In Row : " + i +
        // "," + y);
        // return false;
        // }
        // }
        // System.out.print(board[y][i]);
        // }
        // rowElementMap.clear();
        // System.out.println("");
        // }
        // return true;

        Set<String> seen = new HashSet<>();
        for (int i = 0; i < rowLength; i++) {
            for (int y = 0; y < columnLength; y++) {
                if (board[i][y] != '.') {
                    String b = "(" + board[i][y] + ")";
                    if (!seen.add(b + i) || !seen.add(y + b) || !seen.add((i / 3) + b + (y / 3)))
                        return false;
                }
            }
        }
        return true;
    }
}
