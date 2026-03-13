package Array;

import java.util.Arrays;

public class SetMatrixZeroes {
    public static void main(String[] args) {
        SetMatrixZeroes setMatrixZeroes = new SetMatrixZeroes();
        setMatrixZeroes.setZeroes(new int[][] { { 1, 1, 1 }, { 1, 0, 1 }, { 1, 1, 1 }
        });
        setMatrixZeroes.setZeroes(new int[][] { { 0, 1, 2, 0 }, { 3, 4, 5, 2 }, { 1, 3, 1, 5 } });
        setMatrixZeroes.setZeroes(new int[][] { { 0, 1 } });
    }

    /**
     * 
     * https://leetcode.com/explore/interview/card/top-interview-questions-medium/103/array-and-strings/777/
     * 
     * 
     * Given an m x n integer matrix matrix, if an element is 0, set its entire row
     * and column to 0's.
     * 
     * You must do it in place.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: matrix = [[1,1,1],[1,0,1],[1,1,1]]
     * Output: [[1,0,1],[0,0,0],[1,0,1]]
     * Example 2:
     * 
     * 
     * Input: matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
     * Output: [[0,0,0,0],[0,4,5,0],[0,3,1,0]]
     * 
     * 
     * Constraints:
     * 
     * m == matrix.length
     * n == matrix[0].length
     * 1 <= m, n <= 200
     * -231 <= matrix[i][j] <= 231 - 1
     * 
     * 
     * Follow up:
     * 
     * A straightforward solution using O(mn) space is probably a bad idea.
     * A simple improvement uses O(m + n) space, but still not the best solution.
     * Could you devise a constant space solution?
     * 
     */

    public void setZeroes(int[][] matrix) {
        int row = matrix.length;
        int column = matrix[0].length;
        boolean firstRowZero = false;
        boolean firstColumnZero = false;
        for (int i = 0; i < column; i++) {
            if (matrix[0][i] == 0) {
                firstRowZero = true;
                break;
            }
        }

        for (int i = 0; i < row; i++) {
            if (matrix[i][0] == 0) {
                firstColumnZero = true;
                break;
            }
        }

        for (int i = 1; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        for (int i = 1; i < row; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 1; j < column; j++) {
                    matrix[i][j] = 0;
                }
            }
        }

        for (int i = 1; i < column; i++) {
            if (matrix[0][i] == 0) {
                for (int j = 1; j < row; j++) {
                    matrix[j][i] = 0;
                }
            }
        }

        if (firstRowZero) {
            for (int i = 0; i < column; i++) {
                matrix[0][i] = 0;
            }
        }

        if (firstColumnZero) {
            for (int i = 0; i < row; i++) {
                matrix[i][0] = 0;
            }
        }
        System.out.println("" + Arrays.deepToString(matrix));
    }
}

/**
 * 
 * ## 1. Problem Statement
 * 
 * ### Restatement
 * 
 * You are given an `m x n` integer matrix `matrix`.
 * 
 * If any cell `matrix[i][j]` is `0`, then:
 * 
 * - The entire **row i** must become `0`.
 * - The entire **column j** must become `0`.
 * 
 * All of this must be done **in-place**: you must modify the given matrix
 * directly, not return a new one, and the optimal constraint version asks to
 * use **O(1) extra space** (beyond a few variables).
 * [algo](https://algo.monster/liteproblems/73)
 * 
 * ### Input / Output / Constraints
 * 
 * - **Input:** `int[][] matrix`
 * - Dimensions:
 * - `1 <= m, n <= 200` (typical)
 * - Values: integers (can be positive, negative, or zero).
 * - **Output:** `void` (modify `matrix` in-place).
 * - **Goal:**
 * After the function, matrix must satisfy:
 * For **every** original zero in the matrix, its entire row and column are
 * zeroed.
 * 
 * Example:
 * 
 * - Input:
 * `[[1,1,1], [1,0,1], [1,1,1]]`
 * Output:
 * `[[1,0,1], [0,0,0], [1,0,1]]`.
 * [sparkcodehub](https://www.sparkcodehub.com/leetcode/73/set-matrix-zeroes)
 ***
 * 
 * 
 * ## 2. Intuition
 * 
 * ### Simple view
 * 
 * - Whenever a cell is 0, its whole row and column must become 0.
 * - Straightforward idea:
 * - Scan to find all zeros.
 * - Remember which rows and which columns must be zeroed.
 * - Then, second pass: set those rows and columns to 0.
 * 
 * This is easy if you’re allowed extra space (e.g., `O(m + n)` for two arrays
 * or sets). [codeanddebug](https://codeanddebug.in/blog/set-matrix-zeros/)
 * 
 * The interesting part is the **follow‑up**: do this with **O(1) extra space**,
 * i.e., without separate arrays.
 * 
 * ### Core trick
 * 
 * Use the **first row** and **first column** of the matrix itself as markers:
 * 
 * - When you find a zero at `(i,j)` (not in first row/col), set:
 * - `matrix[i][0] = 0` (mark row i)
 * - `matrix[0][j] = 0` (mark column j)
 * - Later, use those markers to zero out rows/columns.
 * 
 * But: first row and first column themselves might need to be zeroed (if they
 * originally contained zeros). You can’t overwrite them before remembering
 * this, so you keep two boolean flags:
 * 
 * - `firstRowZero` – whether there was any 0 in the first row.
 * - `firstColZero` – whether there was any 0 in the first column.
 * [github](https://github.com/doocs/leetcode/blob/main/solution/0000-0099/0073.Set%20Matrix%20Zeroes/README_EN.md)
 * 
 * This is what makes the problem interesting: using the matrix as its own
 * bookkeeping structure.
 ***
 * 
 * 
 * ## 3. Approach Overview
 * 
 * ### Approach 1 – Extra space with row/col markers (O(m+n) space)
 * 
 * - **Key idea:**
 * Use two arrays (or sets) to remember which rows and columns contain zeros:
 * `zeroRows[m]`, `zeroCols[n]`.
 * Then, in a second pass, set `matrix[i][j] = 0` if `zeroRows[i]` or
 * `zeroCols[j]` is true.
 * [codeanddebug](https://codeanddebug.in/blog/set-matrix-zeros/)
 * - **When to use:**
 * Simple to write and reason about; fine if extra space is allowed.
 * - **Optimal?**
 * Time is optimal (O(mn)), but space is not (O(m+n)).
 * 
 * ### Approach 2 – In-place with first row/column as markers (O(1) extra space)
 * – **optimal**
 * 
 * - **Key idea:**
 * Use `matrix[i][0]` and `matrix[0][j]` as markers indicating “row i must be
 * zeroed” and “column j must be zeroed”.
 * Keep booleans for whether the first row and first column themselves should be
 * zeroed.
 * [blog.unwiredlearning](https://blog.unwiredlearning.com/set-matrix-zeroes)
 * - **When to use:**
 * This is the standard interview solution; meets the O(1) extra space
 * requirement.
 * - **Optimal?**
 * Yes: O(mn) time, O(1) extra space.
 * 
 * (There are other variants, e.g., DFS-based, but they are overkill / less
 * clean.) [youtube](https://www.youtube.com/watch?v=Bnf4fT_CLNY)
 * 
 * We’ll cover Approaches 1 and 2 in detail.
 ***
 * 
 * 
 * ## 4. Detailed Solutions in Java
 * 
 * ### 4.1 Approach 1 – Extra Space (Rows/Cols Arrays)
 * 
 * #### Algorithm (step-by-step)
 * 
 * 1. Let `m = matrix.length`, `n = matrix[0].length`.
 * 2. Create:
 * - `boolean[] zeroRows = new boolean[m];`
 * - `boolean[] zeroCols = new boolean[n];`
 * 3. First pass (mark zeros):
 * - For `i` in `0..m-1`:
 * - For `j` in `0..n-1`:
 * - If `matrix[i][j] == 0`:
 * - `zeroRows[i] = true;`
 * - `zeroCols[j] = true;`
 * 4. Second pass (set matrix values to 0):
 * - For `i` in `0..m-1`:
 * - For `j` in `0..n-1`:
 * - If `zeroRows[i] || zeroCols[j]`:
 * - `matrix[i][j] = 0;`.
 * [sparkcodehub](https://www.sparkcodehub.com/leetcode/73/set-matrix-zeroes)
 * 
 * #### Java Code
 * 
 * ```java
 * public class SetMatrixZeroesExtraSpace {
 * 
 * public void setZeroes(int[][] matrix) {
 * int m = matrix.length;
 * int n = matrix[0].length;
 * 
 * boolean[] zeroRows = new boolean[m];
 * boolean[] zeroCols = new boolean[n];
 * 
 * // First pass: record which rows and columns need to be zeroed
 * for (int i = 0; i < m; i++) {
 * for (int j = 0; j < n; j++) {
 * if (matrix[i][j] == 0) {
 * zeroRows[i] = true;
 * zeroCols[j] = true;
 * }
 * }
 * }
 * 
 * // Second pass: set cells to zero based on recorded rows and columns
 * for (int i = 0; i < m; i++) {
 * for (int j = 0; j < n; j++) {
 * if (zeroRows[i] || zeroCols[j]) {
 * matrix[i][j] = 0;
 * }
 * }
 * }
 * }
 * }
 * ```
 * 
 * #### Complexity
 * 
 * - **Time:**
 * - First pass: O(mn) checks.
 * - Second pass: O(mn) updates.
 * - Total: **O(mn)**.
 * - **Space:**
 * - `zeroRows[m]` and `zeroCols[n]` → **O(m + n)** extra.
 * [github](https://github.com/doocs/leetcode/blob/main/solution/0000-0099/0073.Set%20Matrix%20Zeroes/README_EN.md)
 * 
 * Example rough operations:
 * - 3x3 → 9 checks + 9 updates = 18 operations.
 * - 100x100 → 10,000 + 10,000 = ~20k operations.
 * 
 * #### Worked Example – Approach 1
 * 
 * Input:
 * 
 * `matrix = [[1,1,1], [1,0,1], [1,1,1]]`
 * 
 * 1. First pass (mark):
 * 
 * - i=1, j=1 → `matrix [algo](https://algo.monster/liteproblems/73) == 0`:
 * - `zeroRows [algo](https://algo.monster/liteproblems/73) = true`
 * - `zeroCols [algo](https://algo.monster/liteproblems/73) = true`
 * - Others non-zero.
 * 
 * After pass:
 * 
 * - `zeroRows = [false, true, false]`
 * - `zeroCols = [false, true, false]`
 * 
 * 2. Second pass:
 * 
 * - Row 0:
 * - (0,0): row 0 false, col 0 false → stays 1.
 * - (0,1): col 1 true → becomes 0.
 * - (0,2): col 2 false → stays 1.
 * - Row 1:
 * - row 1 is true → all become 0.
 * - Row 2:
 * - (2,0): row 2 false, col 0 false → stays 1.
 * - (2,1): col 1 true → 0.
 * - (2,2): stays 1.
 * 
 * Output: `[[1,0,1], [0,0,0], [1,0,1]]`.
 ***
 * 
 * 
 * ### 4.2 Approach 2 – In-Place Using First Row and Column (O(1) Extra Space,
 * Optimal)
 * 
 * #### Algorithm (step-by-step)
 * 
 * Let `m = matrix.length`, `n = matrix[0].length`.
 * 
 * 1. **Detect if first row/col need to be zeroed:**
 * - `boolean firstRowZero = false;`
 * - `boolean firstColZero = false;`
 * - Check all cells in row 0: if any `0` → `firstRowZero = true`.
 * - Check all cells in column 0: if any `0` → `firstColZero = true`.
 * [dev](https://dev.to/rahulgithubweb/leetcode-challenge-73-set-matrix-zeroes-javascript-solution-215j)
 * 
 * 2. **Use row 0 and col 0 as markers:**
 * - For all cells `(i,j)` with `i>0` and `j>0`:
 * - If `matrix[i][j] == 0`:
 * - Set `matrix[i][0] = 0;` // this row must be zero
 * - Set `matrix[0][j] = 0;` // this column must be zero
 * 
 * 3. **Zero out rows/cols based on markers (excluding first row/col):**
 * - For each `i` in `1..m-1`:
 * - If `matrix[i][0] == 0`:
 * - Set entire row `i` (from col 1..n-1) to 0.
 * - For each `j` in `1..n-1`:
 * - If `matrix[0][j] == 0`:
 * - Set entire column `j` (from row 1..m-1) to 0.
 * 
 * 4. **Finally handle first row and first column:**
 * - If `firstRowZero`:
 * - Set all cells in row 0 to 0.
 * - If `firstColZero`:
 * - Set all cells in col 0 to 0.
 * [blog.unwiredlearning](https://blog.unwiredlearning.com/set-matrix-zeroes)
 * 
 * This uses the matrix’s own first row/col as the row/column marker arrays.
 * 
 * #### Java Code
 * 
 * ```java
 * public class SetMatrixZeroesInPlace {
 * 
 * public void setZeroes(int[][] matrix) {
 * int m = matrix.length;
 * int n = matrix[0].length;
 * 
 * boolean firstRowZero = false;
 * boolean firstColZero = false;
 * 
 * // 1) Check if first row needs to be zeroed
 * for (int j = 0; j < n; j++) {
 * if (matrix[0][j] == 0) {
 * firstRowZero = true;
 * break;
 * }
 * }
 * 
 * // 2) Check if first column needs to be zeroed
 * for (int i = 0; i < m; i++) {
 * if (matrix[i][0] == 0) {
 * firstColZero = true;
 * break;
 * }
 * }
 * 
 * // 3) Use first row and column as markers
 * // Mark rows and columns that need to be zeroed
 * for (int i = 1; i < m; i++) {
 * for (int j = 1; j < n; j++) {
 * if (matrix[i][j] == 0) {
 * matrix[i][0] = 0; // mark row i
 * matrix[0][j] = 0; // mark column j
 * }
 * }
 * }
 * 
 * // 4) Zero out cells based on row markers (excluding first row)
 * for (int i = 1; i < m; i++) {
 * if (matrix[i][0] == 0) {
 * for (int j = 1; j < n; j++) {
 * matrix[i][j] = 0;
 * }
 * }
 * }
 * 
 * // 5) Zero out cells based on column markers (excluding first col)
 * for (int j = 1; j < n; j++) {
 * if (matrix[0][j] == 0) {
 * for (int i = 1; i < m; i++) {
 * matrix[i][j] = 0;
 * }
 * }
 * }
 * 
 * // 6) Finally, zero out first row if needed
 * if (firstRowZero) {
 * for (int j = 0; j < n; j++) {
 * matrix[0][j] = 0;
 * }
 * }
 * 
 * // 7) Zero out first column if needed
 * if (firstColZero) {
 * for (int i = 0; i < m; i++) {
 * matrix[i][0] = 0;
 * }
 * }
 * }
 * }
 * ```
 * 
 * #### Complexity
 * 
 * - **Time:**
 * - Checking first row: O(n).
 * - Checking first col: O(m).
 * - Marking zeros: O(mn).
 * - Zeroing rows: O(mn).
 * - Zeroing cols: O(mn).
 * Overall still linear in number of cells: **O(mn)**.
 * [sparkcodehub](https://www.sparkcodehub.com/leetcode/73/set-matrix-zeroes)
 * - **Space:**
 * - Only 2 boolean flags (`firstRowZero`, `firstColZero`) plus a few loop
 * variables.
 * - No row/col arrays.
 * - Extra space: **O(1)**.
 * [dev](https://dev.to/rahulgithubweb/leetcode-challenge-73-set-matrix-zeroes-javascript-solution-215j)
 * 
 * For m,n up to 200, this is very efficient.
 * 
 * #### Worked Example – In-place approach
 * 
 * Input:
 * 
 * `matrix = [[1,1,1], [1,0,1], [1,1,1]]`
 * 
 * Indices (i,j):
 * 
 * - (0,0)=1 (0,1)=1 (0,2)=1
 * - (1,0)=1 (1,1)=0 (1,2)=1
 * - (2,0)=1 (2,1)=1 (2,2)=1
 ** 
 * Step 1: detect firstRowZero**
 * 
 * - First row: → no zero → `firstRowZero = false`.
 * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
 ** 
 * Step 2: detect firstColZero**
 * 
 * - First column: → no zero → `firstColZero = false`.
 * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
 ** 
 * Step 3: mark using first row/col (scan i=1..2, j=1..2)**
 * 
 * - (1,1) == 0:
 * - Mark row 1: `matrix [algo](https://algo.monster/liteproblems/73) = 0`
 * - Mark col 1: `matrix[0][1] = 0`
 * - After marking, matrix becomes:
 * 
 * - Row 0:
 * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
 * - Row 1:
 * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
 * - Row 2:
 * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
 ** 
 * Step 4: zero rows based on markers (`matrix[i][0] == 0`)**
 * 
 * - i=1: `matrix [algo](https://algo.monster/liteproblems/73) == 0` → zero row
 * 1 from col 1..2:
 * - row1:
 * - i=2: `matrix [youtube](https://www.youtube.com/watch?v=T41rL0L3Pnw) == 1` →
 * no change.
 * 
 * Matrix now:
 * 
 * -
 * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
 * -
 * -
 * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
 ** 
 * Step 5: zero cols based on markers (`matrix[0][j] == 0`)**
 * 
 * - j=1: `matrix[0][1] == 0` → zero column 1 from row 1..2:
 * - (1,1) already 0
 * - (2,1) ← 0
 * 
 * Matrix becomes:
 * 
 * -
 * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
 * -
 * -
 * [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
 ** 
 * Step 6: handle first row**
 * 
 * - `firstRowZero = false` → leave row 0.
 ** 
 * Step 7: handle first col**
 * 
 * - `firstColZero = false` → leave col 0.
 * 
 * Final matrix:
 * 
 * `[[1,0,1], [0,0,0], [1,0,1]]` – correct.
 * [blog.unwiredlearning](https://blog.unwiredlearning.com/set-matrix-zeroes)
 ***
 * 
 * 
 * ## 5. Edge Cases
 * 
 * 1. **Single row:** e.g., `[[1,0,3]]`
 * - First row and first col are the same conceptually; flags handle row0, col0
 * correctly.
 * - Approach 2:
 * - `firstRowZero` = true (since row has 0).
 * - `firstColZero` = if `matrix[0][0]==0`.
 * - Marking phase only sees row0, so markers still behave; final step zeros row
 * 0.
 * 
 * 2. **Single column:** e.g., `[
 * [algo](https://algo.monster/liteproblems/73),[0],
 * [codeanddebug](https://codeanddebug.in/blog/set-matrix-zeros/)]`
 * - Similar to above; first col zero handling ensures full column zero.
 * 
 * 3. **All zeros:** e.g., `[[0,0],[0,0]]`
 * - Both `firstRowZero` and `firstColZero` become true.
 * - After marking, everything stays zero; final steps keep all zero.
 * 
 * 4. **No zeros at all:** e.g., `[[1,2],[3,4]]`
 * - No markers set, flags false → matrix unchanged.
 * 
 * 5. **Zero in first row or first column (tricky part):** e.g.,
 * `[[0,1,2],[3,4,5],[1,3,1]]` from typical explanations.
 * [sparkcodehub](https://www.sparkcodehub.com/leetcode/73/set-matrix-zeroes)
 * - `firstRowZero = true` because (0,0) or some (0,j) is 0.
 * - `firstColZero = true` if any (i,0) is 0.
 * - Marking still uses other rows/cols; final step zeros first row and/or col
 * entirely.
 * 
 * Approach 1 handles all these naturally; Approach 2 requires careful flag
 * logic but, once implemented, is robust.
 ***
 * 
 * 
 * ## 6. Final Summary
 * 
 * - The problem: for every 0 in the matrix, set its entire row and column to 0,
 * **modifying in‑place**.
 * - **Approach 1 (row/col arrays)**:
 * - Time: O(mn), Space: O(m+n).
 * - Easy and intuitive; good as a first implementation.
 * - **Approach 2 (in-place markers, optimal)**:
 * - Time: O(mn), Space: O(1).
 * - Uses first row/column as marker arrays plus two booleans for whether they
 * themselves should become zero.
 * - This is the standard interview solution you should know.
 * [algo](https://algo.monster/liteproblems/73)
 * 
 * What to remember:
 * 
 * > This is a textbook example of **in-place marking**: use parts of the input
 * (here, first row/col) as a cheap data structure to record metadata, then do a
 * second pass to apply changes, with special care for those marker cells
 * themselves.
 * 
 * If you want, next we can dry‑run the O(1) solution on a trickier example like
 * `[[0,1,2,0],[3,4,5,2],[1,3,1,5]]` step-by-step, cell by cell.
 * 
 */