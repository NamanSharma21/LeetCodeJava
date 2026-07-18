package Array;

public class WordSearch {
    public static void main(String[] args) {
        WordSearch wordSearch = new WordSearch();
        char[][] board = {
                { 'A', 'B', 'C', 'E' },
                { 'S', 'F', 'C', 'S' },
                { 'A', 'D', 'E', 'E' }
        };
        System.out.println("WordSearch : " + wordSearch.existBruteForceDFS(board, "ABCCED"));
        System.out.println("WordSearch : " + wordSearch.existBruteForceDFS(board, "SEE"));
        System.out.println("WordSearch : " + wordSearch.existBruteForceDFS(board, "ABCB"));
        System.out.println("WordSearch : " + wordSearch.existDFSInPlaceMarking(board, "ABCCED"));
        System.out.println("WordSearch : " + wordSearch.existDFSInPlaceMarking(board, "SEE"));
        System.out.println("WordSearch : " + wordSearch.existDFSInPlaceMarking(board, "ABCB"));
        System.out.println("WordSearch : " + wordSearch.existDFSFrequencyPruningWordReversal(board, "ABCCED"));
        System.out.println("WordSearch : " + wordSearch.existDFSFrequencyPruningWordReversal(board, "SEE"));
        System.out.println("WordSearch : " + wordSearch.existDFSFrequencyPruningWordReversal(board, "ABCB"));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/word-search/description/
     * 
     * 
     * Given an m x n grid of characters board and a string word, return true if
     * word exists in the grid.
     * 
     * The word can be constructed from letters of sequentially adjacent cells,
     * where adjacent cells are horizontally or vertically neighboring. The same
     * letter cell may not be used more than once.
     * 
     * +---+---+---+---+
     * | A | B | C | E |
     * +---+---+---+---+
     * | S | F | C | S |
     * +---+---+---+---+
     * | A | D | E | E |
     * +---+---+---+---+
     * 
     * Example 1:
     * 
     * 
     * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word
     * = "ABCCED"
     * Output: true
     * Example 2:
     * 
     * 
     * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word
     * = "SEE"
     * Output: true
     * Example 3:
     * 
     * 
     * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word
     * = "ABCB"
     * Output: false
     * 
     * 
     * Constraints:
     * 
     * m == board.length
     * n = board[i].length
     * 1 <= m, n <= 6
     * 1 <= word.length <= 15
     * board and word consists of only lowercase and uppercase English letters.
     * 
     * 
     * Follow up: Could you use search pruning to make your solution faster with a
     * larger board?
     * 
     * 
     */
    // @formatter:on
    public boolean existBruteForceDFS(char[][] board, String word) {
        int rows = board.length;
        int cols = board[0].length;
        boolean[][] visited = new boolean[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (dfsBruteForce(board, word, row, col, 0, visited))
                    return true;
            }
        }
        return false;
    }

    public boolean dfsBruteForce(char[][] board, String word, int row, int col, int index, boolean[][] visited) {
        if (index == word.length())
            return true;
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length)
            return false;
        if (visited[row][col] || board[row][col] != word.charAt(index))
            return false;
        visited[row][col] = true;
        boolean found = dfsBruteForce(board, word, row - 1, col, index + 1, visited) ||
                dfsBruteForce(board, word, row + 1, col, index + 1, visited) ||
                dfsBruteForce(board, word, row, col - 1, index + 1, visited) ||
                dfsBruteForce(board, word, row, col + 1, index + 1, visited);
        visited[row][col] = false;
        return found;
    }

    private static final char SENTINEL = '#';

    public boolean existDFSInPlaceMarking(char[][] board, String word) {
        int rows = board.length;
        int cols = board[0].length;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (dfsInPlaceMarking(board, word, row, col, 0))
                    return true;
            }
        }
        return false;
    }

    public boolean dfsInPlaceMarking(char[][] board, String word, int row, int col, int index) {
        if (index == word.length())
            return true;
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length)
            return false;
        char current = board[row][col];
        if (current != word.charAt(index))
            return false;
        board[row][col] = SENTINEL;
        boolean found = dfsInPlaceMarking(board, word, row - 1, col, index + 1) ||
                dfsInPlaceMarking(board, word, row + 1, col, index + 1) ||
                dfsInPlaceMarking(board, word, row, col - 1, index + 1) ||
                dfsInPlaceMarking(board, word, row, col + 1, index + 1);
        board[row][col] = current;
        return found;
    }

    private static final int ALPHABET = 128;

    public boolean existDFSFrequencyPruningWordReversal(char[][] board, String word) {
        int[] boardCount = new int[ALPHABET];
        for (char[] rowArray : board) {
            for (char cell : rowArray) {
                boardCount[cell]++;
            }
        }

        int[] wordCount = new int[ALPHABET];
        for (char ch : word.toCharArray()) {
            wordCount[ch]++;
        }

        for (int ch = 0; ch < ALPHABET; ch++) {
            if (wordCount[ch] > boardCount[ch]) {
                return false;
            }
        }

        char first = word.charAt(0);
        char last = word.charAt(word.length() - 1);
        String target = boardCount[first] > boardCount[last] ? new StringBuilder(word).reverse().toString() : word;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (dfsFrequencyPruningWordReversal(board, target, row, col, 0))
                    return true;
            }
        }
        return false;
    }

    public boolean dfsFrequencyPruningWordReversal(char[][] board, String word, int row, int col, int index) {
        if (index == word.length())
            return true;

        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length)
            return false;

        char current = board[row][col];
        if (current != word.charAt(index))
            return false;
        board[row][col] = SENTINEL;
        boolean found = dfsFrequencyPruningWordReversal(board, word, row - 1, col, index + 1)
                || dfsFrequencyPruningWordReversal(board, word, row + 1, col, index + 1)
                || dfsFrequencyPruningWordReversal(board, word, row, col - 1, index + 1)
                || dfsFrequencyPruningWordReversal(board, word, row, col + 1, index + 1);
        board[row][col] = current;
        return found;
    }
}

// @formatter:off
/*
 * ============================================================
 * WORD SEARCH - DEEP DIVE EXPLANATION
 * LeetCode 79 | Difficulty: Medium | Pattern: DFS + Backtracking
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 *
 * You are given a rectangular grid of single characters and a target word.
 * Decide whether the word can be spelled out by walking through the grid, one
 * cell at a time, moving only up/down/left/right (no diagonals), and NEVER
 * stepping on the same cell twice within a single path.
 *
 * The letters must be adjacent in sequence: the cell holding word[0] must touch
 * the cell holding word[1], which must touch the cell holding word[2], and so on.
 * You may start anywhere.
 *
 * This is LeetCode 79 - Word Search (Medium).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 *
 * | Parameter | Type     | Meaning                            |
 * |-----------|----------|------------------------------------|
 * | board     | char[][] | An m x n grid of characters        |
 * | word      | String   | The target word to search for      |
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 *
 * boolean - true if the word exists as a valid path in the grid, false otherwise.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 *
 * | Constraint            | Value                                              |
 * |-----------------------|----------------------------------------------------|
 * | m == board.length     | 1 <= m <= 6                                        |
 * | n == board[i].length  | 1 <= n <= 6                                        |
 * | word.length           | 1 <= word.length <= 15                             |
 * | Characters            | board and word are English letters only (upper+lower) |
 * | Grid size             | At most 36 cells                                   |
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 *
 * Does there exist a sequence of distinct cells
 * (r0,c0), (r1,c1), ..., (r[L-1],c[L-1]) such that:
 *
 *   1. board[ri][ci] == word.charAt(i) for every i
 *   2. |ri - ri+1| + |ci - ci+1| == 1  (each consecutive pair is orthogonally adjacent)
 *   3. All cells in the sequence are pairwise distinct
 *
 * We only need existence - a yes/no answer, not the path itself.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *
 *    board = [ ['A','B','C','E'],
 *              ['S','F','C','S'],
 *              ['A','D','E','E'] ]
 *
 * | Word     | Output | Reason                                              |
 * |----------|--------|-----------------------------------------------------|
 * | "ABCCED" | true   | Path: (0,0)->(0,1)->(0,2)->(1,2)->(2,2)->(2,1)      |
 * | "SEE"    | true   | Path: (1,3)->(2,3)->(2,2)                           |
 * | "ABCB"   | false  | The second B would require reusing cell (0,1)       |
 *
 * Note that "ABCB" is the heart of the problem: without the no-reuse rule it
 * would trivially be true.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 *
 * Think of the grid as a MAZE MADE OF LETTERS, and you are a mouse holding a
 * shopping list of letters in strict order. You may drop a breadcrumb on each
 * tile you stand on so you never revisit it in the same run. If you reach a dead
 * end, you BACK UP, PICK UP YOUR BREADCRUMB, and try a different turn. If you
 * exhaust every possible route from every possible starting tile, the word isn't
 * there.
 *
 * That "back up and pick up your breadcrumb" step is exactly BACKTRACKING. The
 * breadcrumb must be removed on the way out, because a cell that's off-limits for
 * THIS path may be perfectly usable for a different path starting elsewhere.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 *
 *   1. Find candidate starts. Scan the grid for any cell matching word[0]. Every
 *      such cell is a possible beginning - you can't know in advance which one works.
 *   2. Commit to one and walk. From a matching cell, look at its four neighbours
 *      for word[1].
 *   3. Branch on ties. If two neighbours both hold word[1], you can't tell which
 *      is right yet - so try one fully, and if it fails, try the other.
 *   4. Mark as you go. Before recursing, mark the current cell as "in use" so a
 *      later step in the same path can't hop back onto it.
 *   5. Declare success at the end of the list. When your index reaches
 *      word.length(), every character has been matched in order - return true
 *      immediately and let it bubble up through all callers.
 *   6. Undo on failure. If all four directions fail, unmark the cell (restore it)
 *      and report failure to the caller. That restoration is what makes
 *      independent paths possible.
 *   7. Exhaust all starts. Only after every starting cell has failed do you
 *      return false.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 *
 * | Challenge                                   | Why it's tricky                                                                                                                                                                 |
 * |---------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
 * | Cells must be unique PER PATH, not globally | A naive "visited" set that is never cleared will block valid paths that start elsewhere. The mark must be undone as the recursion unwinds - this is the #1 bug in this problem.  |
 * | Greedy fails completely                     | Picking the "first matching neighbour" and committing is wrong. "ABCCED" may require the SECOND C, and you only learn this three levels deeper. Backtracking is mandatory.       |
 * | Every cell is a potential start             | You can't anchor the search at one place; the outer double loop is part of the algorithm, and it multiplies the cost by m*n.                                                     |
 * | Base cases must be ordered correctly        | Checking index == word.length() BEFORE bounds/mismatch checks matters. Reverse them and a word ending on a border cell can be mis-rejected.                                      |
 * | Restoring state on the success path too     | If you mark with '#' and return true early without restoring, the caller's board is corrupted. Harmless on LeetCode, a real bug in production.                                   |
 * | Exponential blow-up hides in plain sight    | The tiny constraints (6x6, length 15) are a hint that the intended solution is EXPONENTIAL - 3^15 is fine, 3^40 would not be.                                                    |
 * | The || short-circuit is load-bearing        | a() || b() stops evaluating at the first true. Replacing it with an eager | or with four separate statements silently destroys the pruning.                                      |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                                    | Key Idea                                                                                                              | Best Used When                                                    | Time Complexity                | Space Complexity            |
 * |---|---------------------------------------------|-----------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------|--------------------------------|-----------------------------|
 * | 1 | Brute-Force DFS + visited matrix            | Recurse in 4 directions; track used cells in a parallel boolean[][]                                                    | Board must stay strictly read-only (e.g. shared across threads)   | O(m*n*3^L)                     | O(m*n + L)                  |
 * | 2 | DFS + In-Place Marking (backtracking) ✅    | Same recursion, but temporarily overwrite the cell with a sentinel '#' and restore it on the way out - no aux grid     | The general case; the standard interview answer                   | O(m*n*3^L) ✅ time-optimal     | O(L) ✅ space-optimal       |
 * | 3 | DFS + Frequency Pruning & Word Reversal     | Before searching, reject impossible words via a character-count check; search from the RARER end of the word           | Adversarial/large test suites where most queries are unsatisfiable| O(m*n + S) best, O(m*n*3^L) worst | O(L + S), S = 128 slots  |
 *
 * Where m = rows, n = columns, L = word.length().
 *
 * THE TRADE-OFF. Approaches 1 and 2 are the SAME ALGORITHM; they differ only in
 * how "already used" is recorded. Approach 2 dominates approach 1 on both axes -
 * identical time, but O(L) space instead of O(m*n + L), since the board itself
 * carries the marking. There is no scenario where approach 1 is faster; it exists
 * only for the case where mutating the input is forbidden. So unlike many
 * problems, here ONE APPROACH GENUINELY WINS ON BOTH TIME AND SPACE: approach 2
 * is marked ✅ on both.
 *
 * Approach 3 is not a different complexity class - its worst case is identical to
 * approach 2 - but it is a genuinely distinct IDEA (a counting-based feasibility
 * pre-filter plus a direction heuristic) built on a different data structure, and
 * it converts many false answers from exponential into linear. Prefer approach 2
 * as your interview answer and mention approach 3 as the optimization; prefer
 * approach 1 only when the board must not be touched.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute-Force DFS + visited Matrix
 * ------------------------------------------------------------
 *
 * Algorithm:
 *
 *   1. Allocate visited, a boolean[m][n] all false.
 *   2. For every cell (row, col) in the grid, launch dfs(row, col, 0). If any
 *      returns true, return true.
 *   3. Inside dfs(row, col, index):
 *        a. If index == word.length(), every character has matched -> return true.
 *        b. If (row, col) is out of bounds -> return false.
 *        c. If the cell is already visited, or board[row][col] != word.charAt(index)
 *           -> return false.
 *        d. Set visited[row][col] = true.
 *        e. Recurse into up, down, left, right with index + 1, combined with ||
 *           so evaluation stops at the first success.
 *        f. Set visited[row][col] = false (BACKTRACK) and return the combined result.
 *   4. If no starting cell succeeded, return false.
 *
 *    public class WordSearchBruteForce {
 *
 *        public boolean exist(char[][] board, String word) {
 *            int rows = board.length;
 *            int cols = board[0].length;
 *            boolean[][] visited = new boolean[rows][cols];
 *
 *            for (int row = 0; row < rows; row++) {
 *                for (int col = 0; col < cols; col++) {
 *                    if (dfs(board, word, row, col, 0, visited)) {
 *                        return true;
 *                    }
 *                }
 *            }
 *            return false;
 *        }
 *
 *        private boolean dfs(char[][] board, String word,
 *                            int row, int col, int index, boolean[][] visited) {
 *
 *            // All characters matched - success bubbles up through every caller.
 *            if (index == word.length()) {
 *                return true;
 *            }
 *            if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
 *                return false;
 *            }
 *            if (visited[row][col] || board[row][col] != word.charAt(index)) {
 *                return false;
 *            }
 *
 *            visited[row][col] = true;
 *
 *            boolean found = dfs(board, word, row - 1, col, index + 1, visited)   // up
 *                         || dfs(board, word, row + 1, col, index + 1, visited)   // down
 *                         || dfs(board, word, row, col - 1, index + 1, visited)   // left
 *                         || dfs(board, word, row, col + 1, index + 1, visited);  // right
 *
 *            visited[row][col] = false;   // backtrack: free the cell for other paths
 *
 *            return found;
 *        }
 *
 *        public static void main(String[] args) {
 *            WordSearchBruteForce solver = new WordSearchBruteForce();
 *            char[][] board = {
 *                {'A', 'B', 'C', 'E'},
 *                {'S', 'F', 'C', 'S'},
 *                {'A', 'D', 'E', 'E'}
 *            };
 *            System.out.println(solver.exist(board, "ABCCED")); // true
 *            System.out.println(solver.exist(board, "SEE"));    // true
 *            System.out.println(solver.exist(board, "ABCB"));   // false
 *        }
 *    }
 *
 * NON-OBVIOUS DETAILS. The bounds check must come BEFORE the visited[row][col]
 * access, or you get an ArrayIndexOutOfBoundsException - Java's || short-circuits
 * left to right, and that ordering is what protects the array read. The
 * visited[row][col] = false line is not cleanup for tidiness; it is
 * algorithmically required. Delete it and exist(board, "SEE") on the example
 * board can still pass while other inputs silently fail, which makes the bug
 * painful to spot.
 *
 * ------------------------------------------------------------
 * Approach 2: DFS + In-Place Marking ✅
 * ------------------------------------------------------------
 *
 * Algorithm:
 *
 *   1. For every cell, launch dfs(row, col, 0).
 *   2. Inside dfs(row, col, index):
 *        a. If index == word.length() -> return true.
 *        b. If out of bounds -> return false.
 *        c. Save current = board[row][col]. If current != word.charAt(index)
 *           -> return false.
 *        d. Write the sentinel board[row][col] = '#'. Since '#' never appears in
 *           word (letters only), any later step in this path that lands here will
 *           fail the character comparison - that single write replaces the entire
 *           visited grid.
 *        e. Recurse in four directions with ||.
 *        f. Restore board[row][col] = current and return the result.
 *   3. Return false if all starts fail.
 *
 *    public class WordSearchOptimal {
 *
 *        private static final char SENTINEL = '#';
 *
 *        public boolean exist(char[][] board, String word) {
 *            for (int row = 0; row < board.length; row++) {
 *                for (int col = 0; col < board[0].length; col++) {
 *                    if (dfs(board, word, row, col, 0)) {
 *                        return true;
 *                    }
 *                }
 *            }
 *            return false;
 *        }
 *
 *        private boolean dfs(char[][] board, String word, int row, int col, int index) {
 *
 *            if (index == word.length()) {
 *                return true;
 *            }
 *            if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
 *                return false;
 *            }
 *
 *            char current = board[row][col];
 *            if (current != word.charAt(index)) {
 *                return false;   // covers both a real mismatch and re-entering a '#' cell
 *            }
 *
 *            board[row][col] = SENTINEL;
 *
 *            boolean found = dfs(board, word, row - 1, col, index + 1)
 *                         || dfs(board, word, row + 1, col, index + 1)
 *                         || dfs(board, word, row, col - 1, index + 1)
 *                         || dfs(board, word, row, col + 1, index + 1);
 *
 *            board[row][col] = current;   // restore on BOTH the success and failure path
 *
 *            return found;
 *        }
 *
 *        public static void main(String[] args) {
 *            WordSearchOptimal solver = new WordSearchOptimal();
 *            char[][] board = {
 *                {'A', 'B', 'C', 'E'},
 *                {'S', 'F', 'C', 'S'},
 *                {'A', 'D', 'E', 'E'}
 *            };
 *            System.out.println(solver.exist(board, "ABCCED")); // true
 *            System.out.println(solver.exist(board, "SEE"));    // true
 *            System.out.println(solver.exist(board, "ABCB"));   // false
 *            System.out.println(solver.exist(board, "ABCCEDF"));// true (D at (2,1) -> F at (1,1))
 *        }
 *    }
 *
 * NON-OBVIOUS DETAILS. The restore happens AFTER found is computed but BEFORE the
 * return, so it runs on the success path too - the board is left byte-identical
 * to the input. The mismatch check now does double duty: it rejects wrong letters
 * and revisits with one comparison, because '#' can never equal a letter of word.
 * This is only safe because the constraints guarantee word is letters-only; if
 * the alphabet could include '#', you would need a sentinel outside the input
 * alphabet (or approach 1).
 *
 * ------------------------------------------------------------
 * Approach 3: DFS + Frequency Pruning & Word Reversal
 * ------------------------------------------------------------
 *
 * Algorithm:
 *
 *   1. Build boardCount[128] - the number of occurrences of each character in the grid.
 *   2. Build wordCount[128] for the target word.
 *   3. FEASIBILITY FILTER: if wordCount[ch] > boardCount[ch] for any ch, the word
 *      needs more copies of a letter than physically exist -> return false
 *      immediately, with no search at all. This alone kills "ABCB" in O(m*n + L).
 *   4. REVERSAL HEURISTIC: a valid path is reversible - if word exists, so does
 *      reverse(word) along the same cells backwards. So if the first character is
 *      more common on the board than the last, reverse the word. Fewer starting
 *      cells means fewer top-level DFS launches, and the rare letter prunes earlier.
 *   5. Run the same in-place-marking DFS from approach 2.
 *
 *    public class WordSearchPruned {
 *
 *        private static final int ALPHABET = 128;
 *        private static final char SENTINEL = '#';
 *
 *        public boolean exist(char[][] board, String word) {
 *
 *            int[] boardCount = new int[ALPHABET];
 *            for (char[] rowArray : board) {
 *                for (char cell : rowArray) {
 *                    boardCount[cell]++;
 *                }
 *            }
 *
 *            int[] wordCount = new int[ALPHABET];
 *            for (char ch : word.toCharArray()) {
 *                wordCount[ch]++;
 *            }
 *
 *            // Filter 1: the board simply lacks the letters.
 *            for (int ch = 0; ch < ALPHABET; ch++) {
 *                if (wordCount[ch] > boardCount[ch]) {
 *                    return false;
 *                }
 *            }
 *
 *            // Filter 2: start from the rarer end of the word.
 *            char first = word.charAt(0);
 *            char last = word.charAt(word.length() - 1);
 *            String target = boardCount[first] > boardCount[last]
 *                    ? new StringBuilder(word).reverse().toString()
 *                    : word;
 *
 *            for (int row = 0; row < board.length; row++) {
 *                for (int col = 0; col < board[0].length; col++) {
 *                    if (dfs(board, target, row, col, 0)) {
 *                        return true;
 *                    }
 *                }
 *            }
 *            return false;
 *        }
 *
 *        private boolean dfs(char[][] board, String word, int row, int col, int index) {
 *            if (index == word.length()) {
 *                return true;
 *            }
 *            if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
 *                return false;
 *            }
 *            char current = board[row][col];
 *            if (current != word.charAt(index)) {
 *                return false;
 *            }
 *
 *            board[row][col] = SENTINEL;
 *            boolean found = dfs(board, word, row - 1, col, index + 1)
 *                         || dfs(board, word, row + 1, col, index + 1)
 *                         || dfs(board, word, row, col - 1, index + 1)
 *                         || dfs(board, word, row, col + 1, index + 1);
 *            board[row][col] = current;
 *
 *            return found;
 *        }
 *
 *        public static void main(String[] args) {
 *            WordSearchPruned solver = new WordSearchPruned();
 *            char[][] board = {
 *                {'A', 'B', 'C', 'E'},
 *                {'S', 'F', 'C', 'S'},
 *                {'A', 'D', 'E', 'E'}
 *            };
 *            System.out.println(solver.exist(board, "ABCCED")); // true (searched as "DECCBA")
 *            System.out.println(solver.exist(board, "SEE"));    // true
 *            System.out.println(solver.exist(board, "ABCB"));   // false (rejected by counting)
 *        }
 *    }
 *
 * WHY THE REVERSAL IS CORRECT. Adjacency is symmetric and the "distinct cells"
 * constraint is order-independent, so the cell sequence p0...p[L-1] spelling word
 * is valid iff p[L-1]...p0 spelling reverse(word) is valid. The answer is
 * therefore identical, but the WORK is not: the outer loop only recurses
 * meaningfully from cells matching the new first character, so anchoring on the
 * rarer letter shrinks the top of the search tree. The array is sized 128 rather
 * than 52 so that raw char values index directly with no - 'A' arithmetic and no
 * branch for case.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Let m = rows, n = cols, L = word length, S = alphabet size (128 here, a constant).
 *
 * ------------------------------------------------------------
 * Approach 1: DFS + visited Matrix
 * ------------------------------------------------------------
 *
 * TIME - O(m*n * 3^L)
 *
 *   1. The outer loops launch a DFS from each of the m*n cells.
 *   2. In one DFS, the first cell has 4 directions to try.
 *   3. Every cell after that has only 3 viable directions, because the fourth is
 *      the cell you just came from - it is marked visited, so that branch dies in O(1).
 *   4. The recursion depth is capped at L (it terminates at index == word.length()).
 *   5. So one DFS explores at most 4 * 3^(L-1) nodes -> O(3^L).
 *   6. Total: O(m*n * 3^L).
 *
 * SPACE - O(m*n + L)
 *
 *   - visited is a boolean[m][n] -> O(m*n).
 *   - The recursion call stack goes at most L frames deep -> O(L).
 *
 * Numeric estimates:
 *
 * | Input                                       | Calculation           | Rough node count                                          |
 * |---------------------------------------------|-----------------------|-----------------------------------------------------------|
 * | 3x4 board, L = 6                            | 12 * 4*3^5 = 12 * 972 | ~11,700 (in practice far fewer - mismatches prune early)  |
 * | 6x6 board, L = 15, all letters 'A' (worst)  | 36 * 4*3^14 = 36 * 19.1M | ~690 million - the true adversarial ceiling            |
 *
 * The all-'A' board with word = "AAAAAAAAAAAAAAA" is the input that actually
 * reaches this bound, because no comparison ever prunes. Real inputs are orders
 * of magnitude cheaper.
 *
 * ------------------------------------------------------------
 * Approach 2: DFS + In-Place Marking ✅
 * ------------------------------------------------------------
 *
 * TIME - O(m*n * 3^L) - identical derivation to approach 1, node for node.
 * Replacing the visited lookup with a '#' comparison changes the constant factor
 * slightly (one fewer array access per node, better cache locality - the board is
 * already hot) but not the asymptotics.
 *
 * SPACE - O(L)
 *
 *   - No auxiliary grid at all; the marking lives inside board, which is input,
 *     not auxiliary.
 *   - Only the recursion stack remains: at most L frames, each holding a few ints
 *     and a char -> O(L).
 *
 * Numeric estimates:
 *
 * | Input                                | Auxiliary space                                                                                                        |
 * |--------------------------------------|------------------------------------------------------------------------------------------------------------------------|
 * | 6x6 board, L = 15                    | ~15 stack frames. Approach 1 additionally allocates a 36-element boolean[][] plus 6 array object headers.               |
 * | 1000x1000 hypothetical board, L = 15 | Still ~15 frames - whereas approach 1 would allocate a 1,000,000-entry boolean[][] (~1 MB). Where the difference bites. |
 *
 * ------------------------------------------------------------
 * Approach 3: DFS + Frequency Pruning & Reversal
 * ------------------------------------------------------------
 *
 * TIME - O(m*n + L + S) best case, O(m*n * 3^L) worst case
 *
 *   1. Counting the board is O(m*n); counting the word is O(L); comparing counts
 *      is O(S) = O(128) = O(1).
 *   2. If the filter fires, we return there - total O(m*n + L), LINEAR, no search
 *      whatsoever.
 *   3. If the filter passes, we run approach 2's DFS -> the worst case is
 *      unchanged at O(m*n * 3^L). Pruning is a constant-factor and average-case
 *      win, never an asymptotic one.
 *   4. The reversal costs O(L) and reduces the NUMBER OF VIABLE ROOTS, not the
 *      branching factor.
 *
 * SPACE - O(L + S) = O(L)
 *
 *   - Two int[128] arrays -> O(S), which is O(1) for a fixed alphabet.
 *   - The reversed string copy -> O(L).
 *   - Recursion stack -> O(L).
 *
 * Numeric estimates:
 *
 * | Input                                        | Cost                                                                                                                                  |
 * |----------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------|
 * | 3x4 board, word = "ABCB"                     | 12 board counts + 4 word counts + 128 comparisons = ~144 operations, then false. Approach 2 explores hundreds of nodes for the same.   |
 * | 6x6 all-'A' board, word = "AAAAAAAAAAAAAAA"  | Filter passes (36 >= 15), reversal is a no-op -> NO HELP AT ALL, ~690M nodes. The pruning is worthless precisely on the worst case.    |
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * Board used throughout (row, col are 0-indexed):
 *
 *          c0  c1  c2  c3
 *     r0 [ A   B   C   E ]
 *     r1 [ S   F   C   S ]
 *     r2 [ A   D   E   E ]
 *
 * ------------------------------------------------------------
 * Approach 1 - word = "ABCCED", trace with visited
 * ------------------------------------------------------------
 *
 * The outer loop starts at (0,0), which holds 'A' = word[0].
 * Recursion tree (✗ = pruned, ✓ = matched):
 *
 * dfs(0,0, i=0, 'A')  board='A' ✓  → visited={(0,0)}
 * ├─ up    (-1,0)  ✗ out of bounds
 * ├─ down  (1,0)  i=1 need 'B', board='S'  ✗ mismatch
 * ├─ left  (0,-1) ✗ out of bounds
 * └─ right (0,1)  i=1 need 'B', board='B' ✓ → visited={(0,0),(0,1)}
 *    ├─ up    (-1,1) ✗ out of bounds
 *    ├─ down  (1,1)  i=2 need 'C', board='F' ✗ mismatch
 *    ├─ left  (0,0)  i=2 visited ✗          ← this is why marking matters
 *    └─ right (0,2)  i=2 need 'C', board='C' ✓ → visited={(0,0),(0,1),(0,2)}
 *       ├─ up    (-1,2) ✗ out of bounds
 *       ├─ down  (1,2)  i=3 need 'C', board='C' ✓ → visited={...,(1,2)}
 *       │  ├─ up    (0,2)  i=4 visited ✗
 *       │  ├─ down  (2,2)  i=4 need 'E', board='E' ✓ → visited={...,(2,2)}
 *       │  │  ├─ up    (1,2)  i=5 visited ✗
 *       │  │  ├─ down  (3,2)  ✗ out of bounds
 *       │  │  ├─ left  (2,1)  i=5 need 'D', board='D' ✓ → visited={...,(2,1)}
 *       │  │  │  └─ dfs(..., i=6) → i == word.length() → return TRUE ✓✓✓
 *       │  │  └─ (right never evaluated - || short-circuits)
 *       │  └─ (left/right never evaluated)
 *       └─ (left/right never evaluated)
 *
 * | Step | Cell  | index | Char needed | visited after marking            |
 * |------|-------|-------|-------------|----------------------------------|
 * | 1    | (0,0) | 0     | A           | {(0,0)}                          |
 * | 2    | (0,1) | 1     | B           | {(0,0),(0,1)}                    |
 * | 3    | (0,2) | 2     | C           | {(0,0),(0,1),(0,2)}              |
 * | 4    | (1,2) | 3     | C           | {(0,0),(0,1),(0,2),(1,2)}        |
 * | 5    | (2,2) | 4     | E           | {...,(2,2)}                      |
 * | 6    | (2,1) | 5     | D           | {...,(2,1)}                      |
 * | 7    | -     | 6     | (end)       | return TRUE                      |
 *
 * true propagates up through all six frames untouched; each frame's
 * visited[row][col] = false still executes on the way out, so visited is
 * all-false again at the end. OUTPUT: true.
 *
 * ------------------------------------------------------------
 * Approach 2 - word = "ABCB", trace with in-place '#' (a failing search)
 * ------------------------------------------------------------
 *
 * The interesting case: this word CANNOT exist, and watching the board mutate and
 * restore shows why.
 *
 * Start scan → (0,0)='A' matches word[0].
 *
 * dfs(0,0, i=0)  'A'✓   board becomes:  [ #  B  C  E ]
 * │                                     [ S  F  C  S ]
 * │                                     [ A  D  E  E ]
 * ├─ up (-1,0) ✗ oob
 * ├─ down (1,0) i=1 need 'B', board='S' ✗
 * ├─ left (0,-1) ✗ oob
 * └─ right (0,1) i=1 need 'B', board='B' ✓  board:  [ #  #  C  E ]
 *    ├─ up (-1,1) ✗ oob
 *    ├─ down (1,1) i=2 need 'C', board='F' ✗
 *    ├─ left (0,0) i=2 need 'C', board='#' ✗   ← sentinel blocks revisit, no visited[][] needed
 *    └─ right (0,2) i=2 need 'C', board='C' ✓  board:  [ #  #  #  E ]
 *       ├─ up (-1,2) ✗ oob
 *       ├─ down (1,2) i=3 need 'B', board='C' ✗
 *       ├─ left (0,1) i=3 need 'B', board='#' ✗   ← the ONLY 'B' on the board is masked
 *       └─ right (0,3) i=3 need 'B', board='E' ✗
 *       └─ all four failed → restore (0,2)='C', return false   board: [ #  #  C  E ]
 *    └─ all four failed → restore (0,1)='B', return false      board: [ #  B  C  E ]
 * └─ all four failed → restore (0,0)='A', return false         board: [ A  B  C  E ]  (original)
 *
 * Outer loop continues: (0,1)='B'≠'A' ✗, (0,2)='C'≠'A' ✗, (0,3)='E'≠'A' ✗,
 *                       (1,0)='S' ✗, (1,1)='F' ✗, (1,2)='C' ✗, (1,3)='S' ✗,
 *                       (2,0)='A' ✓ → dfs launches, fails identically at depth 1
 *                         (neighbours of (2,0) are 'S' and 'D', neither is 'B')
 *                       (2,1)='D' ✗, (2,2)='E' ✗, (2,3)='E' ✗
 * → every start exhausted → return FALSE
 *
 * OUTPUT: false. Board is byte-for-byte identical to the input - verified in the
 * test run: after exist(board, "ABCCED") the flattened board still reads
 * ABCESFCSADEE.
 *
 * ------------------------------------------------------------
 * Approach 3 - word = "ABCCED", trace with pruning + reversal
 * ------------------------------------------------------------
 *
 * PHASE 1 - count the board:
 *
 * | Char  | A | B | C | D | E | F | S |
 * |-------|---|---|---|---|---|---|---|
 * | Count | 2 | 1 | 2 | 1 | 3 | 1 | 2 |
 *
 * PHASE 2 - count the word "ABCCED":  A:1, B:1, C:2, E:1, D:1
 *
 * PHASE 3 - feasibility filter:
 *
 * | Char | Needed | Available | Verdict              |
 * |------|--------|-----------|----------------------|
 * | A    | 1      | 2         | ok                   |
 * | B    | 1      | 1         | ok                   |
 * | C    | 2      | 2         | ok (exactly enough)  |
 * | D    | 1      | 1         | ok                   |
 * | E    | 1      | 3         | ok                   |
 *
 * Filter passes → proceed.
 *
 * PHASE 4 - reversal heuristic: first = 'A' with boardCount['A'] = 2;
 * last = 'D' with boardCount['D'] = 1. Since 2 > 1, reverse: the search target
 * becomes "DECCBA". There is now only ONE viable root instead of two.
 *
 * PHASE 5 - DFS on "DECCBA":
 *
 * | Step | Cell  | index | Char needed | Notes                                                                       |
 * |------|-------|-------|-------------|-----------------------------------------------------------------------------|
 * | 1    | (2,1) | 0     | D           | Outer loop skips (0,0)...(2,0) - none hold 'D'. First root is the ONLY root. |
 * | 2    | (2,2) | 1     | E           | up=(1,1)F✗, down oob, left=(2,0)A✗, right=(2,2)E✓                            |
 * | 3    | (1,2) | 2     | C           | up=(1,2)C✓ on first try                                                      |
 * | 4    | (0,2) | 3     | C           | up=(0,2)C✓                                                                   |
 * | 5    | (0,1) | 4     | B           | up oob, down=(1,2)#✗, left=(0,1)B✓                                           |
 * | 6    | (0,0) | 5     | A           | left=(0,0)A✓                                                                 |
 * | 7    | -     | 6     | (end)       | index == 6 == length → return TRUE                                          |
 *
 * OUTPUT: true - the same answer as approaches 1 and 2, reached along the
 * mirrored cell sequence (2,1)→(2,2)→(1,2)→(0,2)→(0,1)→(0,0), which is exactly
 * approach 1's path read backwards.
 *
 * CONTRAST - word = "ABCB" under approach 3: wordCount['B'] = 2 but
 * boardCount['B'] = 1 → 2 > 1 → return FALSE at the filter, in ~144 operations,
 * with the DFS never invoked at all. Approach 2 needed the entire tree above to
 * reach the same conclusion.
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                                | Input                                              | Expected Output | How Handled                                                                                                                                              |
 * |------------------------------------------|----------------------------------------------------|-----------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|
 * | Single-cell board, single-char, match     | board=[['a']], word="a"                            | true            | dfs(0,0,0) matches, recurses to index=1 == length → true. Requires the length check FIRST, before bounds checks on out-of-grid neighbours.                |
 * | Single-cell board, word longer than board | board=[['a']], word="aa"                           | false           | Marks (0,0) as '#'; all four neighbours are out of bounds → false. No cell reuse.                                                                          |
 * | Word longer than the whole grid           | board=[['a','a']], word="aaa"                      | false           | Only 2 cells exist; every path exhausts at length 2. Approach 3 kills it instantly: needs 3 'a', board has 2.                                              |
 * | Character not on the board at all         | word="Z" on the example board                      | false           | Outer loop never finds a matching start; every dfs returns at the mismatch check.                                                                          |
 * | Word requires reusing a cell              | word="ABCB"                                        | false           | The sentinel '#' / visited flag blocks the revisit - the defining edge case of this problem.                                                               |
 * | Full-board Hamiltonian path               | word="ABCESFCSADEE" (all 12 cells, if such a path) | depends         | Handled naturally; depth reaches m*n. Confirms the recursion is bounded by L, not by grid size.                                                            |
 * | Word exists only via a LATER start        | word="ASA" (starts at (0,0) or (2,0))              | true            | The outer double loop tries every cell; a failure from cell 1 must not abort the scan. Verified true in the test run.                                      |
 * | All-identical board, adversarial          | 6x6 all 'A', word="AAAAAAAAAAAAAAA"                | true            | The 3^L worst case. Passes only because L <= 15 and m,n <= 6.                                                                                              |
 * | Word ending on a border cell              | word="SEE" ending at (2,2)                         | true            | Success is detected by index == length BEFORE any neighbour bounds test, so a border ending is fine.                                                       |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 *
 * PITFALL 1 - Forgetting to backtrack. The single most common bug in this problem.
 *
 *    // ❌ WRONG - the mark is never undone
 *    visited[row][col] = true;
 *    return dfs(board, word, row - 1, col, index + 1, visited)
 *        || dfs(board, word, row + 1, col, index + 1, visited)
 *        || dfs(board, word, row, col - 1, index + 1, visited)
 *        || dfs(board, word, row, col + 1, index + 1, visited);
 *    // Cells burned by a failed path stay blocked forever → false negatives.
 *
 *    // ✅ CORRECT - capture the result, unmark, then return
 *    visited[row][col] = true;
 *    boolean found = dfs(board, word, row - 1, col, index + 1, visited)
 *                 || dfs(board, word, row + 1, col, index + 1, visited)
 *                 || dfs(board, word, row, col - 1, index + 1, visited)
 *                 || dfs(board, word, row, col + 1, index + 1, visited);
 *    visited[row][col] = false;
 *    return found;
 *
 * PITFALL 2 - Checking bounds before the success base case.
 *
 *    // ❌ WRONG - a word ending at a border cell can recurse out of bounds and report false
 *    if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) return false;
 *    if (index == word.length()) return true;
 *
 *    // ✅ CORRECT - success is decided before geometry is ever consulted
 *    if (index == word.length()) return true;
 *    if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) return false;
 *
 * PITFALL 3 - Restoring only on the failure path.
 *
 *    // ❌ WRONG - early return leaves '#' baked into the caller's board
 *    board[row][col] = '#';
 *    if (dfs(board, word, row - 1, col, index + 1)) return true;   // board never restored!
 *    // ... more directions ...
 *    board[row][col] = current;
 *    return false;
 *
 *    // ✅ CORRECT - one restore point that both paths flow through
 *    board[row][col] = '#';
 *    boolean found = dfs(...) || dfs(...) || dfs(...) || dfs(...);
 *    board[row][col] = current;
 *    return found;
 *
 * PITFALL 4 - Using | instead of ||.
 *
 *    // ❌ WRONG - eager evaluation runs all four subtrees even after one succeeds
 *    boolean found = dfs(board, word, row - 1, col, index + 1)
 *                  | dfs(board, word, row + 1, col, index + 1)
 *                  | dfs(board, word, row, col - 1, index + 1)
 *                  | dfs(board, word, row, col + 1, index + 1);
 *    // Still correct, but throws away the early exit and can be 4x slower.
 *
 *    // ✅ CORRECT - short-circuit stops at the first success
 *    boolean found = dfs(board, word, row - 1, col, index + 1)
 *                 || dfs(board, word, row + 1, col, index + 1)
 *                 || dfs(board, word, row, col - 1, index + 1)
 *                 || dfs(board, word, row, col + 1, index + 1);
 *
 * PITFALL 5 - A sentinel that could collide with real data.
 *
 *    // ❌ WRONG (in a generalized setting) - if the alphabet could contain '#',
 *    // a legitimate '#' cell becomes indistinguishable from a marked cell.
 *    board[row][col] = '#';
 *
 *    // ✅ CORRECT - flip a high bit; guaranteed disjoint from any ASCII input, and reversible
 *    board[row][col] ^= 256;
 *    // ... recurse ...
 *    board[row][col] ^= 256;   // XOR is its own inverse - restores exactly, no temp needed
 *
 * PITFALL 6 - board[0].length on an empty board. With LeetCode's constraints
 * (m, n >= 1) this never fires, but a defensive
 * if (board == null || board.length == 0 || board[0].length == 0) return false;
 * guard costs nothing and prevents an NPE if the problem is reused in production code.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 *
 * A: Four real risks:
 *
 *   1. The backtrack restore is omitted or misplaced. The code still compiles and
 *      still passes "ABCCED" on the sample board - it fails only on inputs where
 *      two candidate paths share a prefix. That silent partial correctness is
 *      exactly what makes it dangerous. Test "ASA" on the sample board
 *      specifically: it needs the scan to survive the failure of the first 'A' start.
 *   2. Base-case ordering. If bounds are checked before index == word.length(), a
 *      word whose final letter sits on a border may be rejected. Test "SEE" (ends
 *      at (2,2)) and board=[['a']], word="a" (ends immediately, all neighbours
 *      out of bounds).
 *   3. Board mutation leaking to the caller. Approach 2 mutates the input. If the
 *      same board array is reused across multiple exist calls in a test harness -
 *      as my verification did - an unrestored '#' corrupts every subsequent query.
 *      This is why I asserted the flattened board equals ABCESFCSADEE after a
 *      successful search.
 *   4. The all-identical worst case. A 6x6 grid of 'A' with a 15-'A' word is the
 *      only input that reaches ~690M nodes. It is within limits for these
 *      constraints, but it is worth knowing that this problem is ONLY tractable
 *      because m, n <= 6 and L <= 15.
 *
 * Q: Are there any type mismatches?
 *
 * A: Several worth naming:
 *
 *   - word.charAt(index) returns char; board[row][col] is char. Comparing with ==
 *     is correct here - these are primitives, NOT Strings, so .equals() is neither
 *     needed nor available. Beginners who store the board as String[] and reach
 *     for == on String get reference comparison instead.
 *   - index is an int compared against word.length(), also int. No boxing, no
 *     overflow - L <= 15 is nowhere near Integer.MAX_VALUE.
 *   - In approach 3, boardCount[cell] uses a char as an array index. Java WIDENS
 *     char to int SILENTLY, and since char is unsigned 0-65535, there is no
 *     negative-index risk - but the array must be sized 128 (or 65536) to cover
 *     the input; sizing it 26 and forgetting - 'A' would throw
 *     ArrayIndexOutOfBoundsException on uppercase input.
 *   - The sentinel '#' is a char literal, not the String "#". board[row][col] = "#"
 *     does not compile - a good thing.
 *   - The board[row][col] ^= 256 variant from Pitfall 5 relies on char being
 *     16-bit; it compiles because compound assignment has an implicit narrowing
 *     cast. Writing board[row][col] = board[row][col] ^ 256 does NOT compile (the
 *     ^ promotes to int).
 *
 * Q: How can I verify this works right now?
 *
 * A: Run this. It exercises all three approaches against every edge case in
 * section 7, plus the board-restoration invariant. Compile with
 * java -ea WordSearchVerification.java - THE -ea FLAG IS REQUIRED, or every
 * assert is a no-op and the whole thing passes vacuously.
 *
 *    public class WordSearchVerification {
 *
 *        private static char[][] sampleBoard() {
 *            return new char[][] {
 *                {'A', 'B', 'C', 'E'},
 *                {'S', 'F', 'C', 'S'},
 *                {'A', 'D', 'E', 'E'}
 *            };
 *        }
 *
 *        private static String flatten(char[][] board) {
 *            StringBuilder builder = new StringBuilder();
 *            for (char[] rowArray : board) {
 *                builder.append(rowArray);
 *            }
 *            return builder.toString();
 *        }
 *
 *        public static void verify() {
 *            WordSearchBruteForce bruteForce = new WordSearchBruteForce();
 *            WordSearchOptimal   optimal     = new WordSearchOptimal();
 *            WordSearchPruned    pruned      = new WordSearchPruned();
 *
 *            String[]  words    = {"ABCCED", "SEE", "ABCB", "A", "ASA",
 *                                  "ABCCEDF", "Z", "ABCESEEEFS"};
 *            boolean[] expected = { true,     true,  false,  true, true,
 *                                   true,      false, false };
 *
 *            for (int i = 0; i < words.length; i++) {
 *                assert bruteForce.exist(sampleBoard(), words[i]) == expected[i]
 *                        : "BruteForce failed on: " + words[i];
 *                assert optimal.exist(sampleBoard(), words[i]) == expected[i]
 *                        : "Optimal failed on: " + words[i];
 *                assert pruned.exist(sampleBoard(), words[i]) == expected[i]
 *                        : "Pruned failed on: " + words[i];
 *            }
 *
 *            // Degenerate boards
 *            assert optimal.exist(new char[][]{{'a'}}, "a");
 *            assert !optimal.exist(new char[][]{{'a'}}, "aa");
 *            assert !optimal.exist(new char[][]{{'a', 'a'}}, "aaa");
 *
 *            // Invariant: in-place marking must leave the board untouched
 *            char[][] board = sampleBoard();
 *            String before = flatten(board);
 *            optimal.exist(board, "ABCCED");           // success path
 *            assert flatten(board).equals(before) : "Board corrupted after success";
 *            optimal.exist(board, "ABCB");             // failure path
 *            assert flatten(board).equals(before) : "Board corrupted after failure";
 *
 *            // Reversal heuristic must not change the answer
 *            assert pruned.exist(sampleBoard(), "ABCCED")
 *                == optimal.exist(sampleBoard(), "ABCCED") : "Reversal changed the answer";
 *
 *            System.out.println("All assertions passed.");
 *        }
 *
 *        public static void main(String[] args) {
 *            boolean assertionsEnabled = false;
 *            assert assertionsEnabled = true;   // intentional side effect
 *            if (!assertionsEnabled) {
 *                System.out.println("WARNING: run with -ea or assertions do nothing.");
 *            }
 *            verify();
 *        }
 *    }
 *
 * Two of these expectations are counterintuitive and worth pausing on - I got
 * both wrong on my first pass, and the live run corrected me:
 *
 *   - "ABCCEDF" is TRUE, not false. The "ABCCED" path ends at D = (2,1), whose
 *     upward neighbour (1,1) holds 'F'. The word extends by one.
 *   - "ABCESEEEFS" is FALSE. The path A→B→C→E→S→E→E reaches (2,2), but the eighth
 *     character needs another E and (2,2)'s only unvisited neighbours are 'C' and
 *     'D'. Counting says the board has three Es and the word needs four -
 *     approach 3 rejects it at the filter without searching.
 *
 * | Approach              | Risk                                                                                                    | Mitigation                                                                                                                                       |
 * |-----------------------|---------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------|
 * | 1 - DFS + visited     | Forgetting visited[row][col] = false → false negatives that survive the sample tests                     | Capture the result in a local found, unmark, then return. Test "ASA", which requires the scan to recover from a failed first start.               |
 * | 1 - DFS + visited     | O(m*n) allocation per call; on a large board or in a hot loop this dominates                             | Prefer approach 2 unless the board is genuinely read-only. If it is, hoist the visited array out and clear it per call rather than reallocating.  |
 * | 2 - In-place marking  | Mutating the caller's input; a missed restore corrupts later queries                                     | Single restore point after the || chain, reached by both success and failure. Assert board equality before/after in tests.                        |
 * | 2 - In-place marking  | Sentinel collides with the input alphabet if the problem is generalized                                  | Constraints guarantee letters-only. For safety in reusable code, use board[row][col] ^= 256 - provably disjoint and self-inverse.                 |
 * | 2 - In-place marking  | Not thread-safe: two threads searching one board race on the sentinel                                    | Deep-copy the board per thread, or fall back to approach 1 with a per-thread visited.                                                             |
 * | 3 - Pruning + reversal| The reversal is only valid because paths are symmetric - a variant (e.g. forward-only moves) breaks it   | State the symmetry argument explicitly. Assert that pruned and optimal agree on every test case.                                                  |
 * | 3 - Pruning + reversal| Gives a false sense of speed: zero help on the adversarial all-'A' input                                 | Treat it as an average-case filter only. Never quote its best case as the complexity.                                                             |
 * | All                   | Exponential blow-up if constraints were larger                                                           | Recognize that m,n <= 6 and L <= 15 make this tractable. For MANY words on one board, switch to a Trie + DFS (LeetCode 212, Word Search II).      |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * LeetCode 79 - Word Search | Difficulty: MEDIUM | Approximate total reported
 * interview appearances: ~2,000+ over the last several years, placing it in the
 * top tier of backtracking questions. It is one of the most-asked grid-DFS
 * problems, second only to Number of Islands (LC 200) among matrix traversal
 * questions.
 *
 * | Company          | Frequency  | Notes                                                                                                                            |
 * |------------------|------------|----------------------------------------------------------------------------------------------------------------------------------|
 * | Amazon           | ⭐⭐⭐⭐⭐ | Extremely common in phone screens and on-sites. Frequently paired with Number of Islands to test whether you know WHY one needs backtracking. |
 * | Microsoft        | ⭐⭐⭐⭐⭐ | A long-standing favourite. Interviewers commonly ask you to avoid the visited array as a follow-up - approach 2 is expected.       |
 * | Meta             | ⭐⭐⭐⭐   | Usually appears as a warm-up before escalating to Word Search II (LC 212) with a Trie in the remaining time.                       |
 * | Google           | ⭐⭐⭐⭐   | Less about the base solution, more about the follow-ups: "what if the board is 10^4 x 10^4?", "what if you get a million queries?" |
 * | Bloomberg        | ⭐⭐⭐⭐   | Frequent on-site question. Expects clean backtracking and correct complexity analysis out loud.                                    |
 * | Apple            | ⭐⭐⭐     | Appears in iOS/backend loops; emphasis on clean code structure and edge-case handling.                                             |
 * | Uber             | ⭐⭐⭐     | Occasionally asked with a diagonal-movement twist, which changes the branching factor from 3 to 7.                                 |
 * | Adobe            | ⭐⭐⭐     | Standard variant, typically in the second round.                                                                                   |
 * | Oracle           | ⭐⭐⭐     | Common in SDE-1/SDE-2 loops.                                                                                                       |
 * | Salesforce       | ⭐⭐       | Occasional; usually the vanilla version.                                                                                           |
 * | Goldman Sachs    | ⭐⭐       | Appears in technical rounds, often with a request for the complexity derivation.                                                   |
 * | TikTok/ByteDance | ⭐⭐       | Growing in frequency, often as the Word Search II escalation.                                                                      |
 *
 * THE FOLLOW-UP YOU SHOULD EXPECT: "Now search for a LIST of words on the same
 * board." Do NOT loop your exist over the list - that is O(k*m*n*3^L). Build a
 * TRIE of all words and DFS the board once, matching all words simultaneously
 * (LeetCode 212). Naming this unprompted is a strong signal.
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach                                | Time                              | Space       | Code Complexity                                   | Recommended?                                                                                                                     |
 * |-----------------------------------------|-----------------------------------|-------------|---------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------|
 * | 1 - Brute-Force DFS + visited matrix    | O(m*n*3^L)                        | O(m*n + L)  | Low - the most explicit and readable version      | ✅ Acceptable - use ONLY when the board must not be mutated (read-only or shared across threads); strictly dominated otherwise    |
 * | 2 - DFS + In-Place Marking              | O(m*n*3^L)                        | O(L)        | Low - the same code minus one array               | ✅✅ BEST ON BOTH TIME AND SPACE - the standard interview answer; identical speed to #1 with no auxiliary grid                    |
 * | 3 - DFS + Frequency Pruning & Reversal  | O(m*n + L) best, O(m*n*3^L) worst | O(L)        | Medium - two counting arrays + a symmetry argument| ✅ Acceptable - a strong average-case optimization to MENTION, but the worst case is unchanged; don't lead with it                |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 *
 * APPROACH 2 - DFS with in-place '#' marking. Unusually for this kind of problem,
 * there is no time-vs-space tension to negotiate: approach 2 matches the brute
 * force node-for-node on time while using O(L) auxiliary space instead of
 * O(m*n + L), so it wins on both axes and approach 1 survives only for the
 * read-only-board case. Layer approach 3's counting filter on top if you're being
 * graded on wall-clock time against a large test suite - it turns most false
 * answers linear - but present it as an optimization, never as a complexity
 * improvement, because the all-identical-letters worst case is untouched by it.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 *
 * WORD SEARCH IS THE CANONICAL "DFS + BACKTRACKING ON A GRID" PROBLEM, AND ITS
 * ONE NON-NEGOTIABLE IDEA IS: MARK BEFORE YOU RECURSE, UNMARK AFTER YOU RETURN.
 * The mark must be undone as the stack unwinds, because a cell is off-limits FOR
 * THE CURRENT PATH ONLY - a cell that dead-ends one route may be essential to
 * another starting three cells away; forget the restore and you get false
 * negatives that still pass the sample tests. The two gotchas that separate a
 * working solution from a subtly broken one: check index == word.length() BEFORE
 * the bounds check (or words ending on a border get rejected), and place the
 * restore AFTER the || chain so it runs on the success path too - and remember
 * that the '#' sentinel replaces the entire visited grid only because the input
 * is guaranteed letters-only.
 *
 * ============================================================
 * END OF EXPLANATION
 * ============================================================
 *
 */
// @formatter:on
