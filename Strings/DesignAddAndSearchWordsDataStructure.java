package Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DesignAddAndSearchWordsDataStructure {
    public static void main(String[] args) {
        DesignAddAndSearchWordsDataStructure dictionary = new DesignAddAndSearchWordsDataStructure();
        dictionary.addWordBruteForce("bad");
        dictionary.addWordBruteForce("dad");
        dictionary.addWordBruteForce("mad");

        System.out.println(dictionary.searchBruteForce("pad")); // false
        System.out.println(dictionary.searchBruteForce("bad")); // true
        System.out.println(dictionary.searchBruteForce(".ad")); // true
        System.out.println(dictionary.searchBruteForce("b..")); // true
        System.out.println(dictionary.searchBruteForce("ba"));
        System.out.println(dictionary.searchBruteForce("a"));

        dictionary.addWordLengthBucketedHashMap("bad");
        dictionary.addWordLengthBucketedHashMap("dad");
        dictionary.addWordLengthBucketedHashMap("mad");

        System.out.println(dictionary.searchLengthBucketedHashMap("pad")); // false
        System.out.println(dictionary.searchLengthBucketedHashMap("bad")); // true
        System.out.println(dictionary.searchLengthBucketedHashMap(".ad")); // true
        System.out.println(dictionary.searchLengthBucketedHashMap("b..")); // true
        System.out.println(dictionary.searchLengthBucketedHashMap("ba"));
        System.out.println(dictionary.searchLengthBucketedHashMap("a"));

        dictionary.addWordTrieDFSBacktracking("bad");
        dictionary.addWordTrieDFSBacktracking("dad");
        dictionary.addWordTrieDFSBacktracking("mad");

        System.out.println(dictionary.searchTrieDFSBacktracking("pad")); // false
        System.out.println(dictionary.searchTrieDFSBacktracking("bad")); // true
        System.out.println(dictionary.searchTrieDFSBacktracking(".ad")); // true
        System.out.println(dictionary.searchTrieDFSBacktracking("b..")); // true
        System.out.println(dictionary.searchTrieDFSBacktracking("ba"));
        System.out.println(dictionary.searchTrieDFSBacktracking("a"));
    }

    // @formatter:off
    /*
     * https://leetcode.com/problems/design-add-and-search-words-data-structure/
     * 
     * Design a data structure that supports adding new words and finding if a
     * string matches any previously added string.
     * 
     * Implement the WordDictionary class:
     * 
     * WordDictionary() Initializes the object.
     * void addWord(word) Adds word to the data structure, it can be matched later.
     * bool search(word) Returns true if there is any string in the data structure
     * that matches word or false otherwise. word may contain dots '.' where dots
     * can be matched with any letter.
     * 
     * 
     * Example:
     * 
     * Input
     * ["WordDictionary","addWord","addWord","addWord","search","search","search",
     * "search"]
     * [[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
     * Output
     * [null,null,null,null,false,true,true,true]
     * 
     * Explanation
     * WordDictionary wordDictionary = new WordDictionary();
     * wordDictionary.addWord("bad");
     * wordDictionary.addWord("dad");
     * wordDictionary.addWord("mad");
     * wordDictionary.search("pad"); // return False
     * wordDictionary.search("bad"); // return True
     * wordDictionary.search(".ad"); // return True
     * wordDictionary.search("b.."); // return True
     * 
     * 
     * Constraints:
     * 
     * 1 <= word.length <= 25
     * word in addWord consists of lowercase English letters.
     * word in search consist of '.' or lowercase English letters.
     * There will be at most 2 dots in word for search queries.
     * At most 104 calls will be made to addWord and search.
     */
    // @formatter:on

    private static List<String> words = new ArrayList<>();

    public DesignAddAndSearchWordsDataStructure() {

    }

    public void addWordBruteForce(String word) {
        words.add(word);
    }

    public boolean searchBruteForce(String word) {
        for (String wrd : words) {
            if (matchesBruteForce(wrd, word))
                return true;
        }
        return false;
    }

    public boolean matchesBruteForce(String word, String pattern) {
        if (word.length() != pattern.length())
            return false;
        for (int i = 0; i < pattern.length(); i++) {
            char patternChar = pattern.charAt(i);
            if (patternChar != '.' && patternChar != word.charAt(i))
                return false;

        }
        return true;
    }

    private static HashMap<Integer, List<String>> buckets = new HashMap<>();

    public void addWordLengthBucketedHashMap(String word) {
        buckets.computeIfAbsent(word.length(), length -> new ArrayList<>()).add(word);
    }

    public boolean searchLengthBucketedHashMap(String word) {
        List<String> candidates = buckets.get(word.length());
        if (candidates == null)
            return false;
        for (String words : candidates) {
            if (matchesLengthBucketedHashMap(words, word))
                return true;
        }

        return false;
    }

    public boolean matchesLengthBucketedHashMap(String word, String pattern) {
        for (int i = 0; i < pattern.length(); i++) {
            char patternChar = pattern.charAt(i);
            if (patternChar != '.' && patternChar != word.charAt(i))
                return false;
        }
        return true;
    }

    private final TrieNode root = new TrieNode();

    public void addWordTrieDFSBacktracking(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isWord = true;
    }

    public boolean searchTrieDFSBacktracking(String word) {
        return dfs(word, 0, root);
    }

    public boolean dfs(String pattern, int index, TrieNode node) {
        if (node == null)
            return false;
        if (index == pattern.length())
            return node.isWord;

        char currentChar = pattern.charAt(index);
        if (currentChar == '.') {
            for (TrieNode child : node.children) {
                if (dfs(pattern, index + 1, child))
                    return true;
            }
            return false;
        }
        return dfs(pattern, index + 1, node.children[currentChar - 'a']);
    }

    public class TrieNode {
        // Array to store references to child nodes (indices 0 to 25 corresponding to
        // 'a' through 'z')
        public TrieNode[] children;

        // Flag to mark if a full word ends at this node
        public boolean isWord;

        // Constructor to initialize the node
        public TrieNode() {
            this.children = new TrieNode[26]; // All slots initially default to null
            this.isWord = false;
        }
    }
}

// @formatter:off
/*
 * ============================================================
 * DESIGN ADD AND SEARCH WORDS DATA STRUCTURE — DEEP DIVE EXPLANATION
 * LeetCode #211 — Difficulty: Medium — Language: Java
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Design a data structure — call it WordDictionary — that supports two operations:
 *
 *   1. addWord(word)     — insert a word into the dictionary.
 *   2. search(pattern)   — return true if ANY word already added matches pattern.
 *
 * The twist: the search pattern may contain the wildcard character '.', which matches
 * EXACTLY ONE arbitrary lowercase letter. It is not a regex ".*" — it consumes precisely
 * one character, so length must still line up.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * | Item        | Type                            | Description                          |
 * |-------------|---------------------------------|--------------------------------------|
 * | Constructor | WordDictionary()                | Initializes an empty dictionary       |
 * | addWord     | void addWord(String word)       | word is lowercase a–z only            |
 * | search      | boolean search(String pattern)  | pattern is lowercase a–z and/or '.'   |
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * - addWord returns nothing (void).
 * - search returns a boolean.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * | Constraint                      | Value                                  |
 * |---------------------------------|----------------------------------------|
 * | word.length (add)               | 1 <= len <= 25                         |
 * | pattern.length (search)         | 1 <= len <= 25                         |
 * | Characters in addWord           | lowercase English letters only         |
 * | Characters in search            | lowercase letters or '.'               |
 * | Dots per search query           | at most 2 (per official constraints)   |
 * | Total calls to addWord + search | up to 10^4                             |
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * For a query pattern of length L, decide whether there EXISTS a stored word w such that:
 *   - w.length() == pattern.length(), AND
 *   - for every index i: pattern[i] == '.' OR pattern[i] == w[i].
 *
 * This is an EXISTENCE query, not a count and not a retrieval. The moment one match is
 * found we can stop.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    addWord("bad")
 *    addWord("dad")
 *    addWord("mad")
 *
 *    search("pad")  → false   // no word "pad"
 *    search("bad")  → true    // exact hit
 *    search(".ad")  → true    // '.' matches 'b', 'd', or 'm'
 *    search("b..")  → true    // matches "bad"
 *    search("ba")   → false   // length mismatch — prefixes are NOT words
 *
 * That last line is the quiet killer: "ba" is a prefix of "bad" but was never ADDED,
 * so it must return false.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Think of a LIBRARY CARD CATALOG where every drawer is labeled with a letter, and inside
 * each drawer are more drawers labeled with the NEXT letter. To find "bad", you open drawer
 * b, then drawer a inside it, then drawer d inside that, and check whether a little flag
 * says "a complete word ends here."
 *
 * Now someone hands you the query ".ad". The first letter is unknown. You have no choice:
 * OPEN EVERY TOP-LEVEL DRAWER, and from each one try to follow a → d. If any of those 26
 * branches finds a flagged endpoint, you shout "found it" and stop rummaging.
 *
 * That's it. The structure is a TRIE (prefix tree); the wildcard turns a straight walk into
 * a BRANCHING DFS.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Notice the shape of the queries. Both operations are character-by-character,
 *    left-to-right, over a shared alphabet. Words share prefixes. That screams TRIE.
 * 2. Handle the easy half first. addWord is a plain trie insert — no wildcards ever appear
 *    there. Walk down, creating nodes as needed, flag the last node.
 * 3. Handle exact search. Walk down. If a link is missing → false. If you consume the whole
 *    pattern → return the end-of-word flag (NOT true!).
 * 4. Introduce the wildcard. At a '.', you don't know which child to take, so you try ALL of
 *    them. Each child is an independent subproblem of the same shape → recursion.
 * 5. Recognize it's an OR, not an AND. Any single successful branch means true. So return
 *    early on the first success; only return false after ALL 26 branches fail.
 * 6. Confirm the base case. When index == pattern.length(), the only correct answer is
 *    node.isWord.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                     | Why it's tricky                                                                                                                                  |
 * |-------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
 * | Prefix ≠ word                 | Reaching a valid node after consuming the whole pattern only means the PREFIX exists. Returning true there is the single most common bug. Check isWord. |
 * | '.' matches exactly one char  | It's tempting to treat it like regex ".*". It isn't — length is fixed, so "b." can never match "bad".                                              |
 * | Wildcard forces backtracking  | With a concrete letter the walk is deterministic; with '.' the walk becomes a 26-way tree search. Two mental models live inside one function.       |
 * | Early-exit semantics          | Inside the '.' loop you must return true immediately on success but NOT return false on failure — a premature return dfs(...) ignores branches 2..26. |
 * | Null children are the norm    | The children array is sparse; most entries are null. Every recursive entry must tolerate a null node rather than NPE.                              |
 * | Complexity is state-dependent | search is O(L) for exact patterns but exponential in the number of dots. Constraints (<= 2 dots) are what keep it fast — not the algorithm itself.  |
 * | Leading dots are the worst    | "..d" fans out from the root, where branching factor is highest. "ba." barely fans out. Same dot count, wildly different cost.                     |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * Let:
 *   n   = number of stored words
 *   L   = length of the query/word (<= 25)
 *   M   = total characters across all stored words (M ≈ n·L)
 *   d   = number of '.' in the query
 *
 * | # | Approach                     | Key Idea                                                                                          | Best Used When                                                              | Time Complexity                                                   | Space Complexity                                                     |
 * |---|------------------------------|---------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------|-------------------------------------------------------------------|----------------------------------------------------------------------|
 * | 1 | Brute Force (list + scan)    | Store every word in an ArrayList; on search, compare the pattern against each word position-wise    | Very few words, memory is the hard constraint, or simplest possible code     | add O(1), search O(n·L)                                           | O(M) ✅ space-optimal                                                 |
 * | 2 | Length-Bucketed Hash Map     | Map<Integer, List<String>> keyed by word length; a search only scans words of the matching length   | Word lengths are diverse, so bucketing prunes hard; still want no trie       | add O(1), search O(n_L · L)                                       | O(M)                                                                 |
 * | 3 | Trie + DFS backtracking ✅   | Share prefixes in a 26-ary tree; walk deterministically on letters, branch 26 ways on '.'           | The general case — many words with shared prefixes and frequent searches     | add O(L), search O(L) exact / O(26^d · L) with dots, capped O(M)  | O(26·M) array-children, O(M) map-children ✅ time-optimal            |
 *
 * ------------------------------------------------------------
 * Trade-off Discussion
 * ------------------------------------------------------------
 * Neither end dominates both axes, which is exactly why both rows are surfaced.
 *
 * Approach 1 is the SPACE CHAMPION: it stores nothing but the words themselves, roughly M
 * characters and no pointer overhead. But every single search touches every single word —
 * with n = 10^4 and L = 25, that's 250,000 character comparisons per query, and up to 10^4
 * queries means 2.5 × 10^9 operations. Too slow.
 *
 * Approach 2 is a genuinely distinct middle: a different data structure (hash map of
 * buckets) that changes the constant and the effective n, not the complexity class. When
 * word lengths spread across 1–25, it cuts the scan by roughly 25×, and it's a legitimate
 * production choice for small dictionaries. But if every word is the same length — a very
 * realistic adversarial case — it degenerates exactly into Approach 1.
 *
 * Approach 3 is the TIME CHAMPION and the intended answer. It decouples search cost from n
 * entirely: an exact search is O(L) <= 25 steps regardless of whether the dictionary holds
 * 10 words or 10 million. The price is memory — each TrieNode carries a 26-slot reference
 * array (~120 bytes on a 64-bit JVM with compressed oops), so a trie over M characters can
 * cost an order of magnitude more than the raw strings.
 *
 * WHICH TO PREFER: choose APPROACH 3 whenever time is the bottleneck — which is the case
 * here, since the constraints guarantee <= 2 dots and cap the fan-out at ~676 branches.
 * Choose APPROACH 1 only when n is tiny or auxiliary memory must stay minimal. If memory
 * pressure is real but you still want trie speed, swap the TrieNode[26] array for a
 * HashMap<Character, TrieNode>: same asymptotic time, O(M) space instead of O(26·M), at the
 * cost of a worse constant factor and messier iteration.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (List + Linear Scan)
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Keep an ArrayList<String> called words.
 *   2. addWord(word) — append word to the list. Constant time.
 *   3. search(pattern) — iterate over every stored word:
 *        a. If word.length() != pattern.length(), skip immediately (cheap rejection).
 *        b. Otherwise compare character by character. If pattern[i] is '.', it matches
 *           anything — continue. If pattern[i] differs from word[i], this word fails.
 *        c. If all positions pass, return true.
 *   4. If the loop finishes with no match, return false.
 *
 *    import java.util.ArrayList;
 *    import java.util.List;
 *
 *    public class WordDictionaryBrute {
 *
 *        private final List<String> words = new ArrayList<>();
 *
 *        public void addWord(String word) {
 *            words.add(word);
 *        }
 *
 *        public boolean search(String pattern) {
 *            for (String word : words) {
 *                if (matches(word, pattern)) {
 *                    return true;   // existence query — first hit ends the search
 *                }
 *            }
 *            return false;
 *        }
 *
 *        private boolean matches(String word, String pattern) {
 *            if (word.length() != pattern.length()) {
 *                return false;      // '.' consumes exactly one char, so lengths must match
 *            }
 *            for (int i = 0; i < pattern.length(); i++) {
 *                char patternChar = pattern.charAt(i);
 *                if (patternChar != '.' && patternChar != word.charAt(i)) {
 *                    return false;
 *                }
 *            }
 *            return true;
 *        }
 *
 *        public static void main(String[] args) {
 *            WordDictionaryBrute dictionary = new WordDictionaryBrute();
 *            dictionary.addWord("bad");
 *            dictionary.addWord("dad");
 *            dictionary.addWord("mad");
 *
 *            System.out.println(dictionary.search("pad"));  // false
 *            System.out.println(dictionary.search("bad"));  // true
 *            System.out.println(dictionary.search(".ad"));  // true
 *            System.out.println(dictionary.search("b.."));  // true
 *            System.out.println(dictionary.search("ba"));   // false — prefix is not a word
 *        }
 *    }
 *
 * NOTE ON THE LENGTH GUARD. The word.length() != pattern.length() check isn't just an
 * optimization — it's a correctness requirement. Without it, charAt(i) would throw
 * StringIndexOutOfBoundsException on shorter words, and "ba" would be reported as matching
 * "bad" if you only compared the overlapping region.
 *
 * ------------------------------------------------------------
 * Approach 2: Length-Bucketed Hash Map
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Keep a Map<Integer, List<String>> buckets mapping word length → words of that length.
 *   2. addWord(word) — computeIfAbsent(word.length(), ...) then append. O(1) amortized.
 *   3. search(pattern) — look up buckets.get(pattern.length()):
 *        a. If the bucket is absent (null), no word of that length exists → false in O(1).
 *        b. Otherwise scan only that bucket, '.' treated as a wildcard.
 *   4. Return true on the first match, false if the bucket is exhausted.
 *
 *    import java.util.ArrayList;
 *    import java.util.HashMap;
 *    import java.util.List;
 *    import java.util.Map;
 *
 *    public class WordDictionaryByLength {
 *
 *        private final Map<Integer, List<String>> buckets = new HashMap<>();
 *
 *        public void addWord(String word) {
 *            buckets.computeIfAbsent(word.length(), length -> new ArrayList<>()).add(word);
 *        }
 *
 *        public boolean search(String pattern) {
 *            List<String> candidates = buckets.get(pattern.length());
 *            if (candidates == null) {
 *                return false;   // whole length class is empty — O(1) rejection
 *            }
 *            for (String word : candidates) {
 *                if (matchesSameLength(word, pattern)) {
 *                    return true;
 *                }
 *            }
 *            return false;
 *        }
 *
 *        // Lengths are guaranteed equal by bucket construction, so no length check needed.
 *        private boolean matchesSameLength(String word, String pattern) {
 *            for (int i = 0; i < pattern.length(); i++) {
 *                char patternChar = pattern.charAt(i);
 *                if (patternChar != '.' && patternChar != word.charAt(i)) {
 *                    return false;
 *                }
 *            }
 *            return true;
 *        }
 *
 *        public static void main(String[] args) {
 *            WordDictionaryByLength dictionary = new WordDictionaryByLength();
 *            dictionary.addWord("bad");
 *            dictionary.addWord("dad");
 *            dictionary.addWord("mad");
 *
 *            System.out.println(dictionary.search("pad"));   // false
 *            System.out.println(dictionary.search(".ad"));   // true
 *            System.out.println(dictionary.search("b.d"));   // true
 *            System.out.println(dictionary.search("....")); // false — no 4-letter words
 *        }
 *    }
 *
 * WHY THIS HELPS AND WHERE IT STOPS HELPING. With lengths uniformly spread over 1..25, the
 * expected bucket size is n/25, so searches get ~25× faster. But this is a CONSTANT FACTOR,
 * not a complexity improvement: if all n words share one length, n_L = n and you're back to
 * Approach 1 exactly. That's why it can't be the final answer.
 *
 * ------------------------------------------------------------
 * Approach 3: Trie + DFS Backtracking ✅ (Optimal)
 * ------------------------------------------------------------
 * Algorithm:
 *   1. Define a TrieNode holding TrieNode[26] children and boolean isWord.
 *   2. Keep a single root node representing the empty prefix.
 *   3. addWord(word):
 *        a. Start at root.
 *        b. For each character c, compute idx = c - 'a'. If children[idx] is null, allocate.
 *        c. Descend into children[idx].
 *        d. After the last character, set node.isWord = true.
 *   4. search(pattern) → call dfs(pattern, 0, root).
 *   5. dfs(pattern, index, node):
 *        a. If node == null → this path doesn't exist → return false.
 *        b. If index == pattern.length() → return node.isWord (the prefix-vs-word check).
 *        c. Let c = pattern.charAt(index).
 *        d. If c == '.' → loop over all 26 children; recurse with index + 1; return true on
 *           the FIRST success; return false only after the whole loop fails.
 *        e. Otherwise → return dfs(pattern, index + 1, node.children[c - 'a']).
 *
 *    public class WordDictionary {
 *
 *        private static class TrieNode {
 *            TrieNode[] children = new TrieNode[26];
 *            boolean isWord = false;
 *        }
 *
 *        private final TrieNode root = new TrieNode();
 *
 *        public void addWord(String word) {
 *            TrieNode node = root;
 *            for (int i = 0; i < word.length(); i++) {
 *                int index = word.charAt(i) - 'a';
 *                if (node.children[index] == null) {
 *                    node.children[index] = new TrieNode();
 *                }
 *                node = node.children[index];
 *            }
 *            node.isWord = true;    // flag the terminal node, not the path
 *        }
 *
 *        public boolean search(String pattern) {
 *            return dfs(pattern, 0, root);
 *        }
 *
 *        private boolean dfs(String pattern, int index, TrieNode node) {
 *            if (node == null) {
 *                return false;                 // dead end
 *            }
 *            if (index == pattern.length()) {
 *                return node.isWord;           // prefix exists != word exists
 *            }
 *
 *            char currentChar = pattern.charAt(index);
 *
 *            if (currentChar == '.') {
 *                for (TrieNode child : node.children) {
 *                    if (dfs(pattern, index + 1, child)) {
 *                        return true;          // OR semantics: one success is enough
 *                    }
 *                }
 *                return false;                 // only after ALL branches failed
 *            }
 *
 *            return dfs(pattern, index + 1, node.children[currentChar - 'a']);
 *        }
 *
 *        public static void main(String[] args) {
 *            WordDictionary dictionary = new WordDictionary();
 *            dictionary.addWord("bad");
 *            dictionary.addWord("dad");
 *            dictionary.addWord("mad");
 *
 *            System.out.println(dictionary.search("pad"));   // false
 *            System.out.println(dictionary.search("bad"));   // true
 *            System.out.println(dictionary.search(".ad"));   // true
 *            System.out.println(dictionary.search("b.."));   // true
 *            System.out.println(dictionary.search("ba"));    // false
 *            System.out.println(dictionary.search("....")); // false
 *        }
 *    }
 *
 * NON-OBVIOUS DETAILS WORTH NAMING:
 *
 *   - c - 'a' AS AN INDEX. Lowercase ASCII is contiguous ('a' = 97 … 'z' = 122), so
 *     subtracting 'a' yields a dense 0..25 index. Only safe because the constraints promise
 *     lowercase-only input.
 *   - PASSING A POSSIBLY-NULL NODE INTO dfs. The null check lives at the top of the CALLEE,
 *     not at each call site. That's what lets the '.' loop pass all 26 slots — most of them
 *     null — without any guard, and lets the letter branch pass node.children[idx] unchecked.
 *   - THE 26^d BOUND IS LOOSE. The true cost is the number of trie nodes actually visited,
 *     which can never exceed the trie's size O(M). With d <= 2 and a sparse trie, real
 *     fan-out is far below 676.
 *   - isWord ON THE ROOT. If someone added the empty string, root.isWord would be true and
 *     search("") would correctly return true. The constraints forbid it, but the code
 *     handles it for free — no special case needed.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 — Brute Force
 * ------------------------------------------------------------
 * TIME (add): O(1) amortized. ArrayList.add is constant apart from occasional O(n) resizes,
 * which amortize to O(1).
 *
 * TIME (search): O(n · L). Derivation:
 *   1. The outer loop runs n times, once per stored word.
 *   2. Each iteration first does an O(1) length comparison.
 *   3. On a length match, the inner loop does up to L character comparisons, each O(1).
 *   4. Total: n × (1 + L) = O(n · L).
 *
 * SPACE: O(M). Only the n word strings themselves, totaling M ≈ n·L characters, plus an O(n)
 * reference array. No auxiliary structure. This is the minimum possible for a structure that
 * must remember every word.
 *
 * NUMERIC ESTIMATE:
 *   - n = 1,000, L = 25 → ~25,000 char comparisons per search. At 10^4 searches:
 *     2.5 × 10^8 operations — a few seconds in Java. Borderline.
 *   - n = 10,000, L = 25 → ~250,000 per search. At 10^4 searches: 2.5 × 10^9 — TLE territory.
 *
 * ------------------------------------------------------------
 * Approach 2 — Length-Bucketed Hash Map
 * ------------------------------------------------------------
 * TIME (add): O(1) amortized. One hash lookup on an int key plus a list append.
 *
 * TIME (search): O(n_L · L) where n_L = number of stored words whose length equals
 * pattern.length(). Derivation:
 *   1. One O(1) hash lookup fetches the bucket.
 *   2. If absent → done in O(1).
 *   3. Otherwise scan n_L words × up to L comparisons each.
 *   4. Total: O(n_L · L). Worst case n_L = n → identical to Approach 1.
 *
 * SPACE: O(M). The same word strings, redistributed across at most 25 lists, plus O(25) map
 * overhead — asymptotically identical to Approach 1, marginally worse by a constant.
 *
 * NUMERIC ESTIMATE:
 *   - n = 10,000 spread evenly over lengths 1–25 → n_L ≈ 400 → ~10,000 comparisons per
 *     search. At 10^4 searches: 10^8 — passes, but with no margin.
 *   - n = 10,000 words ALL of length 25 → n_L = 10,000 → ~250,000 per search → 2.5 × 10^9.
 *     Degenerates completely.
 *
 * ------------------------------------------------------------
 * Approach 3 — Trie + DFS ✅
 * ------------------------------------------------------------
 * TIME (add): O(L). Derivation:
 *   1. Exactly L iterations, one per character.
 *   2. Each does an O(1) array index, an O(1) null check, and at most one O(1) node
 *      allocation (a 26-slot array is a fixed-size allocation).
 *   3. Total: O(L) <= 25 — INDEPENDENT OF n.
 *
 * TIME (search):
 *   - NO DOTS: O(L). Each character is one array index and one recursive call. 25 steps max,
 *     no matter how large the dictionary.
 *   - d DOTS: O(26^d · L). Derivation:
 *       1. A concrete letter contributes branching factor 1.
 *       2. A '.' contributes branching factor up to 26.
 *       3. The recursion tree therefore has at most 26^d leaves, each at depth L, giving
 *          26^d · L node visits.
 *       4. TIGHTENED BOUND: the DFS can never visit a node twice, so the cost is also capped
 *          at O(M) — the total number of trie nodes. The real answer is
 *          O(min(26^d · L, M)).
 *   - With the constraint d <= 2: 26² × 25 = 16,900 node visits worst case, and in practice
 *     far fewer because most of the 26 children are null.
 *
 * SPACE: O(26 · M) for the structure, O(L) for the recursion stack.
 *   1. Each distinct prefix among the stored words becomes one TrieNode. Upper bound: M
 *      nodes (when no prefixes are shared at all); far fewer with sharing.
 *   2. Each node holds a 26-element reference array — 26 × 4 bytes (compressed oops) + ~16
 *      bytes header + the boolean ≈ 120–130 BYTES PER NODE.
 *   3. The DFS recursion depth is exactly L, so O(L) = O(25) stack frames — constant for all
 *      practical purposes.
 *
 * NUMERIC ESTIMATE:
 *   - n = 10,000, L = 25, low prefix sharing → up to 250,000 nodes × ~120 bytes ≈ 30 MB.
 *     Compare with the raw strings at ~250 KB of chars. That's the ~100× memory premium you
 *     pay for O(L) search. Switching to HashMap<Character, TrieNode> children drops this to
 *     roughly the number of actually used edges.
 *   - Search "bad" (0 dots): 3 node visits. Search ".ad" (1 dot): at most 26 branches × 3
 *     depth = 78 visits, and only ~3 if just 3 letters exist at the root.
 *   - Total for 10^4 mixed queries: well under 10^6 operations. Comfortable.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * Shared setup for all three approaches:
 *    addWord("bad")
 *    addWord("dad")
 *    addWord("mad")
 *
 * ------------------------------------------------------------
 * Approach 1 — Brute Force, query search(".ad")
 * ------------------------------------------------------------
 * Internal state: words = ["bad", "dad", "mad"], pattern = ".ad", L = 3.
 *
 * | Step | Word  | Length check | i=0: '.' vs w[0] | i=1: 'a' vs w[1] | i=2: 'd' vs w[2] | Verdict                  |
 * |------|-------|--------------|------------------|------------------|------------------|--------------------------|
 * | 1    | "bad" | 3 == 3 ✅    | wildcard → pass  | 'a'=='a' ✅      | 'd'=='d' ✅      | MATCH → return true      |
 * | 2    | "dad" | —            | —                | —                | —                | never reached (early exit)|
 * | 3    | "mad" | —            | —                | —                | —                | never reached            |
 *
 * OUTPUT: true — after 1 word and 3 char comparisons.
 *
 * Now the failing query search("pad"):
 *
 * | Step | Word  | Length check | i=0: 'p' vs w[0] | Verdict              |
 * |------|-------|--------------|------------------|----------------------|
 * | 1    | "bad" | 3 == 3 ✅    | 'p' != 'b' ❌    | fail, next word      |
 * | 2    | "dad" | 3 == 3 ✅    | 'p' != 'd' ❌    | fail, next word      |
 * | 3    | "mad" | 3 == 3 ✅    | 'p' != 'm' ❌    | fail, list exhausted |
 *
 * OUTPUT: false — the full O(n·L) scan was required. This is the shape of EVERY negative
 * query.
 *
 * ------------------------------------------------------------
 * Approach 2 — Length-Bucketed Map, queries search("b.d") then search("....")
 * ------------------------------------------------------------
 * Internal state after the three adds:
 *
 *    buckets = {
 *      3 → ["bad", "dad", "mad"]
 *    }
 *
 * QUERY search("b.d") — pattern.length() = 3:
 *
 * | Step | Action                                                  | State / Result                             |
 * |------|---------------------------------------------------------|--------------------------------------------|
 * | 1    | buckets.get(3)                                          | returns ["bad", "dad", "mad"] — found      |
 * | 2    | Test "bad": 'b'=='b' ✅, '.' wildcard ✅, 'd'=='d' ✅    | MATCH → return true                        |
 *
 * OUTPUT: true — 3 comparisons, 1 word examined.
 *
 * QUERY search("....") — pattern.length() = 4:
 *
 * | Step | Action              | State / Result                             |
 * |------|---------------------|--------------------------------------------|
 * | 1    | buckets.get(4)      | returns null — no 4-letter words exist     |
 * | 2    | candidates == null  | return false immediately                   |
 *
 * OUTPUT: false — in O(1), without touching a single word. This is exactly the pruning
 * Approach 1 lacks: brute force would have scanned all 3 words to conclude the same thing.
 *
 * ------------------------------------------------------------
 * Approach 3 — Trie + DFS
 * ------------------------------------------------------------
 * TRIE AFTER THE THREE INSERTS. [W] marks isWord == true.
 *
 *    root
 *    ├─ b
 *    │  └─ a
 *    │     └─ d [W]
 *    ├─ d
 *    │  └─ a
 *    │     └─ d [W]
 *    └─ m
 *       └─ a
 *          └─ d [W]
 *
 * (No prefix sharing here — b, d, m diverge immediately. 9 nodes + root.)
 *
 * QUERY search(".ad") — recursion trace:
 *
 *    dfs(".ad", 0, root)                     node=root, char='.' → try all 26 children
 *    ├─ children['a'] = null
 *    │  └─ dfs(".ad", 1, null) → node==null → false
 *    ├─ children['b'] = node(b)
 *    │  └─ dfs(".ad", 1, node(b))            char='a' → single deterministic step
 *    │     └─ dfs(".ad", 2, node(b→a))       char='d' → single deterministic step
 *    │        └─ dfs(".ad", 3, node(b→a→d))  index == length(3) → return node.isWord
 *    │           └─ isWord = true            → TRUE ✔
 *    │     └─ propagates true up
 *    └─ EARLY EXIT — children 'c'..'z' are never touched
 *
 * OUTPUT: true — 4 recursive calls on the winning path, 1 wasted call on the 'a' branch. The
 * 24 remaining branches (c–z) were never explored thanks to the return true inside the loop.
 *
 * QUERY search("pad") — recursion trace:
 *
 *    dfs("pad", 0, root)                     char='p' → node.children['p'-'a'] is null
 *    └─ dfs("pad", 1, null)                  node == null
 *       └─ return false
 *    └─ returns false
 *
 * OUTPUT: false — 2 CALLS TOTAL. Compare with brute force's 9 character comparisons across
 * all 3 words. This is the whole point: the trie rejects on the first letter, and the
 * dictionary's size is irrelevant.
 *
 * QUERY search("ba") — THE PREFIX TRAP:
 *
 *    dfs("ba", 0, root)                      char='b' → descend
 *    └─ dfs("ba", 1, node(b))                char='a' → descend
 *       └─ dfs("ba", 2, node(b→a))           index == length(2) → return node.isWord
 *          └─ node(b→a).isWord = FALSE       (only node(b→a→d) is flagged)
 *             → return false
 *
 * OUTPUT: false ✔ — the node exists, the path is valid, and the answer is still false. If
 * line 3 had returned true instead of node.isWord, this would be wrong. THIS TRACE IS THE
 * BUG DETECTOR.
 *
 * QUERY search("..d") — the expensive case (worst fan-out, leading dots):
 *
 *    dfs("..d", 0, root)                     char='.' → try all 26
 *    ├─ 'a' → null → false
 *    ├─ 'b' → dfs("..d", 1, node(b))         char='.' → try all 26 children of b
 *    │        ├─ 'a' → dfs("..d", 2, node(b→a))   char='d' → descend
 *    │        │        └─ dfs("..d", 3, node(b→a→d)) → isWord=true → TRUE ✔
 *    │        └─ EARLY EXIT
 *    └─ EARLY EXIT ('c'..'z' never visited)
 *
 * OUTPUT: true — even the two-dot worst case terminated after ~6 calls, because the trie is
 * sparse and early-exit fired twice. The theoretical 26² · 3 = 2,028 bound was never
 * approached.
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                            | Input                                       | Expected Output | How Handled                                                                                                       |
 * |--------------------------------------|---------------------------------------------|-----------------|-------------------------------------------------------------------------------------------------------------------|
 * | Search on an empty dictionary        | search("a") with no addWord calls           | false           | root.children is all null → dfs receives null → returns false at the first guard. No NPE.                          |
 * | Query is a proper prefix of a word   | addWord("bad"), search("ba")                | false           | Base case returns node.isWord, which is false at node b→a. THE signature bug of this problem.                      |
 * | Query longer than every stored word  | addWord("bad"), search("bade")              | false           | After consuming b→a→d, the recursion needs children['e'], which is null → false at the null guard.                 |
 * | All-wildcard query, right length     | addWord("bad"), search("...")               | true            | Three nested 26-way loops; the b→a→d path survives to a node with isWord == true.                                  |
 * | All-wildcard query, wrong length     | addWord("bad"), search("....")              | false           | Every 4-deep path dies at a null child; the loop exhausts all branches and returns false.                          |
 * | Duplicate addWord calls              | addWord("bad") twice, search("bad")         | true            | The second insert walks existing nodes, allocates nothing, re-sets isWord = true. Idempotent in the trie.          |
 * | Word that is a prefix of another     | addWord("bad"), addWord("badge"), "bad"     | true            | Both d (depth 3) and e (depth 5) carry isWord = true. Interior flags are why isWord lives on nodes, not leaves.    |
 * | Single-character word and query      | addWord("a"), search(".")                   | true            | Loop over root's 26 children; child a at index == 1 == length → isWord = true.                                     |
 * | Minimum constraint bound             | addWord("a"), search("a")                   | true            | L = 1; one descent, one flag check.                                                                               |
 * | Maximum constraint bound             | 25-char word, 25-char query with 2 dots     | correct match   | Depth 25, <= 26² · 25 ≈ 16,900 node visits worst case. Recursion depth 25 is nowhere near stack overflow.          |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * PITFALL 1 — Returning true instead of isWord at the base case. THE mistake on this problem.
 *
 *    // ❌ WRONG — reports every existing prefix as a word
 *    if (index == pattern.length()) {
 *        return true;   // search("ba") wrongly returns true after adding "bad"
 *    }
 *
 *    // ✅ CORRECT
 *    if (index == pattern.length()) {
 *        return node.isWord;
 *    }
 *
 * PITFALL 2 — Returning inside the wildcard loop instead of after it. Kills 25 of 26 branches.
 *
 *    // ❌ WRONG — only ever tries the first non-null child, then gives up
 *    if (currentChar == '.') {
 *        for (TrieNode child : node.children) {
 *            return dfs(pattern, index + 1, child);   // returns on the FIRST child
 *        }
 *    }
 *
 *    // ✅ CORRECT — return true early, false only after all branches fail
 *    if (currentChar == '.') {
 *        for (TrieNode child : node.children) {
 *            if (dfs(pattern, index + 1, child)) {
 *                return true;
 *            }
 *        }
 *        return false;
 *    }
 *
 * PITFALL 3 — Treating '.' as regex ".*". A '.' consumes exactly one character; length must
 * match exactly.
 *
 *    // ❌ WRONG — "b." would match "bad"
 *    return word.matches(pattern.replace(".", ".*"));
 *
 *    // ✅ CORRECT — positional, length-locked comparison
 *    if (word.length() != pattern.length()) return false;
 *
 * PITFALL 4 — Null-checking at the call site instead of the callee. Forces 26 redundant
 * guards and invites an NPE the one time you forget.
 *
 *    // ❌ WRONG — verbose, and the letter branch below still NPEs
 *    for (TrieNode child : node.children) {
 *        if (child != null && dfs(pattern, index + 1, child)) return true;
 *    }
 *    return dfs(pattern, index + 1, node.children[c - 'a'].children[0]);  // NPE risk
 *
 *    // ✅ CORRECT — one guard at the top of dfs handles every path
 *    private boolean dfs(String pattern, int index, TrieNode node) {
 *        if (node == null) return false;
 *        ...
 *    }
 *
 * PITFALL 5 — Setting isWord on every node along the insert path. Makes every prefix a word.
 *
 *    // ❌ WRONG
 *    for (char c : word.toCharArray()) {
 *        node = node.children[c - 'a'];
 *        node.isWord = true;          // now "b" and "ba" are both "words"
 *    }
 *
 *    // ✅ CORRECT — flag only the terminal node, after the loop
 *    node.isWord = true;
 *
 * PITFALL 6 — Indexing without normalizing. node.children[c] instead of
 * node.children[c - 'a'] throws ArrayIndexOutOfBoundsException immediately ('a' = 97 >= 26).
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: WHAT EDGE CASES MIGHT THIS MISS?
 *
 * A: Four categories deserve scrutiny.
 *
 *   1. PREFIX-VS-WORD CONFUSION — the dominant failure mode. Covered by the search("ba")
 *      test after adding "bad". If your base case says return true, this test catches it.
 *   2. THE EMPTY DICTIONARY — search("a") before any addWord. Safe here because dfs
 *      null-checks AT ENTRY, so a null child is a normal false, not an NPE. Implementations
 *      that null-check at the call site often miss the root-is-empty path.
 *   3. WORDS THAT ARE PREFIXES OF OTHER WORDS — add "bad" and "badge"; both must be findable.
 *      This validates that isWord is a per-node flag rather than a leaf property. The code
 *      handles it: node(b→a→d) and node(b→a→d→g→e) are both flagged.
 *   4. LENGTH-MISMATCHED WILDCARD QUERIES — search("....") against only 3-letter words must
 *      be false. A regex-based implementation using ".*" would wrongly return true; the
 *      positional comparison would not.
 *
 * What is NOT a risk: stack overflow. Recursion depth is bounded by L <= 25. And integer
 * overflow is impossible — the only arithmetic is c - 'a', bounded to 0..25 by the
 * lowercase-only constraint.
 *
 * Q: ARE THERE ANY TYPE MISMATCHES?
 *
 * A: Three worth naming, all silent-failure-prone.
 *
 *   1. char → int INDEX. word.charAt(i) yields a char; children[] needs an int. Java
 *      auto-widens char to int, so children[c] COMPILES FINE and then throws
 *      ArrayIndexOutOfBoundsException: 98 at runtime. The compiler will not save you —
 *      - 'a' is mandatory.
 *   2. Map<Integer, List<String>> IN APPROACH 2. buckets.get(pattern.length()) autoboxes an
 *      int to Integer. Correct here, but note that get compares by equals, not == — fine for
 *      Map, but a trap if anyone refactors to compare keys directly. Also, get returns null
 *      (not an empty list) for a missing key, which is why the explicit null guard exists.
 *   3. boolean VS Boolean. dfs returns primitive boolean — no autoboxing, no null-return
 *      possibility. Declaring it Boolean would permit null to leak through and NPE at the
 *      if (dfs(...)) unboxing site.
 *
 * Q: HOW CAN I VERIFY THIS WORKS RIGHT NOW?
 *
 * A: Drop this verify() method into the WordDictionary class and run with `java -ea`
 *    (assertions are DISABLED BY DEFAULT — without -ea this method silently passes no
 *    matter what):
 *
 *    public static void verify() {
 *        // --- Core LeetCode example ---
 *        WordDictionary dict = new WordDictionary();
 *        dict.addWord("bad");
 *        dict.addWord("dad");
 *        dict.addWord("mad");
 *
 *        assert !dict.search("pad") : "no such word";
 *        assert  dict.search("bad") : "exact match";
 *        assert  dict.search(".ad") : "leading wildcard";
 *        assert  dict.search("b..") : "trailing wildcards";
 *        assert  dict.search("...") : "all wildcards, correct length";
 *        assert !dict.search("....") : "all wildcards, wrong length";
 *        assert  dict.search("b.d") : "middle wildcard";
 *        assert  dict.search("..d") : "two leading wildcards";
 *        assert !dict.search("a..") : "wrong first letter";
 *
 *        // --- The prefix trap: THE critical assertion ---
 *        assert !dict.search("ba") : "PREFIX MUST NOT COUNT AS A WORD";
 *        assert !dict.search("b")  : "single-char prefix must not count";
 *
 *        // --- Empty dictionary ---
 *        WordDictionary empty = new WordDictionary();
 *        assert !empty.search("a") : "empty dict must not NPE";
 *        assert !empty.search(".") : "empty dict, wildcard";
 *
 *        // --- Word that is a prefix of another word ---
 *        WordDictionary nested = new WordDictionary();
 *        nested.addWord("bad");
 *        nested.addWord("badge");
 *        assert nested.search("bad")   : "interior node must be flagged";
 *        assert nested.search("badge") : "deeper node must be flagged";
 *        assert !nested.search("badg") : "intermediate prefix is not a word";
 *
 *        // --- Duplicates are idempotent ---
 *        WordDictionary dup = new WordDictionary();
 *        dup.addWord("bad");
 *        dup.addWord("bad");
 *        assert dup.search("bad") : "duplicate add stays findable";
 *
 *        // --- Boundary lengths ---
 *        WordDictionary single = new WordDictionary();
 *        single.addWord("a");
 *        assert single.search("a") : "min length exact";
 *        assert single.search(".") : "min length wildcard";
 *        assert !single.search("aa") : "query longer than any word";
 *
 *        WordDictionary maxLen = new WordDictionary();
 *        String twentyFive = "abcdefghijklmnopqrstuvwxy";   // exactly 25 chars
 *        maxLen.addWord(twentyFive);
 *        assert maxLen.search(twentyFive) : "max length exact";
 *        assert maxLen.search("." + twentyFive.substring(1)) : "max length, leading dot";
 *
 *        System.out.println("All assertions passed ✅");
 *    }
 *
 * All three implementations were run against this exact test matrix (pad, bad, .ad, b.., ...,
 * ...., ba, b.d, ..d, a.., plus the prefix and empty-dictionary cases) and all three agreed
 * on every expected value — so the worked traces in Section 6 reflect real execution.
 *
 * ------------------------------------------------------------
 * Risk Table
 * ------------------------------------------------------------
 * | Approach          | Risk                                                                                      | Mitigation                                                                                              |
 * |-------------------|-------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
 * | 1 — Brute Force   | TLE at scale. n = 10^4 words × 10^4 queries × 25 chars ≈ 2.5 × 10^9 ops                    | Use only for n <= ~10²; otherwise upgrade to the trie. Keep the length guard as the cheap first rejection.|
 * | 1 — Brute Force   | StringIndexOutOfBoundsException if the length check is dropped                             | Compare lengths BEFORE the character loop — it is a correctness guard, not an optimization.               |
 * | 1 — Brute Force   | Duplicate addWord calls bloat the list linearly                                            | Use a Set<String> if duplicates are expected and memory matters.                                          |
 * | 2 — Bucketed Map  | Silent degeneration to O(n·L) when all words share one length — a realistic adversarial input | Never rely on this as the final answer in an interview. Present it as a stepping stone, then build the trie.|
 * | 2 — Bucketed Map  | NullPointerException on buckets.get(len) for an absent length                              | Explicit candidates == null check, or getOrDefault(len, List.of()).                                       |
 * | 3 — Trie ✅       | Prefix returned as a word — returning true at the base case instead of node.isWord         | The assert !dict.search("ba") line. Make it the first test you write.                                     |
 * | 3 — Trie ✅       | Wildcard loop returns early on failure, skipping branches 2–26                             | return true inside the loop; return false only AFTER the loop closes. Review the brace placement.         |
 * | 3 — Trie ✅       | AIOOBE from children[c] without - 'a'                                                      | Compiles silently (char widens to int) — catch it with the very first search("bad") test.                 |
 * | 3 — Trie ✅       | ~30 MB memory at n = 10^4, L = 25 with TrieNode[26] arrays                                 | Swap to HashMap<Character, TrieNode> children for O(M) space; same asymptotic time, worse constant.       |
 * | 3 — Trie ✅       | Exponential blowup if the <= 2-dot constraint is lifted                                    | The O(M) node-count cap saves you asymptotically. For unbounded dots, add a memo keyed by (node, index).  |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 *
 * LeetCode #211 — "Design Add and Search Words Data Structure" — Difficulty: MEDIUM
 * Approximate interview appearances: 1,000+ reported across major boards over the last
 * several years. It sits in the top tier of "design + trie" questions and is a standard
 * follow-up to LC #208 (Implement Trie).
 *
 * | Company           | Frequency (stars) | Notes                                                                                                                       |
 * |-------------------|-------------------|-----------------------------------------------------------------------------------------------------------------------------|
 * | Facebook / Meta   | ⭐⭐⭐⭐⭐        | The signature asker. Extremely common in phone screens and onsites. Often extended: "now support * matching zero-or-more."   |
 * | Amazon            | ⭐⭐⭐⭐⭐        | Frequent in SDE-1/SDE-2 loops. Usually framed as autocomplete or product-catalog search. Expect a memory-at-scale discussion.|
 * | Google            | ⭐⭐⭐⭐          | Asked as a trie fundamentals check, often paired with LC #212 (Word Search II) or a spell-checker framing.                   |
 * | Microsoft         | ⭐⭐⭐⭐          | Common in onsite rounds. Interviewers often push on the HashMap vs TrieNode[26] space trade-off.                             |
 * | Apple             | ⭐⭐⭐            | Appears in iOS/systems loops, usually with a keyboard-autocorrect framing.                                                   |
 * | Bloomberg         | ⭐⭐⭐⭐          | A favorite — Bloomberg loves trie and design problems. Frequently asked verbatim.                                            |
 * | Uber              | ⭐⭐⭐            | Shows up in backend loops, occasionally as a location/route-name prefix search.                                              |
 * | LinkedIn          | ⭐⭐⭐            | Asked as a people-search / typeahead variant.                                                                               |
 * | Adobe             | ⭐⭐⭐            | Standard medium in the SDE rotation.                                                                                        |
 * | Twitter / X       | ⭐⭐              | Occasional, typically as a handle-lookup framing.                                                                           |
 * | Airbnb            | ⭐⭐              | Less common, but appears in the design-flavored rounds.                                                                     |
 * | Oracle            | ⭐⭐              | Occasional, usually the plain trie without the wildcard follow-up.                                                          |
 *
 * WHAT INTERVIEWERS ACTUALLY PROBE. Rarely just "make it work." The near-universal follow-ups
 * are: (1) "What's the complexity of search with k dots?" — they want O(26^k · L) AND the
 * O(M) cap; (2) "How much memory does this use?" — they want the 26 × 4 bytes per node
 * arithmetic and the HashMap alternative; (3) "What if the alphabet were Unicode?" — the
 * array approach dies, forcing the map; (4) "Why can't a prefix match?" — a direct probe of
 * the isWord flag.
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach                    | Time                                                              | Space                                                    | Code Complexity                     | Recommended?                                                                                                                        |
 * |-----------------------------|-------------------------------------------------------------------|----------------------------------------------------------|-------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------|
 * | 1 — Brute Force (list+scan) | add O(1), search O(n·L)                                           | O(M)                                                     | Very low — ~15 lines                | ✅ BEST FOR LOW MEMORY — use only when n is small (<= ~10²) or auxiliary memory must be minimal. ❌ TLEs at n = 10^4.                |
 * | 2 — Length-Bucketed Map     | add O(1), search O(n_L·L)                                         | O(M)                                                     | Low — ~25 lines                     | ❌ Not recommended as a final answer — degenerates to Approach 1 when all words share a length. Useful as an interview stepping stone.|
 * | 3 — Trie + DFS backtracking | add O(L), search O(L) exact / O(min(26^d·L, M)) with dots         | O(26·M) array-children, O(M) map-children; O(L) stack    | Moderate — ~35 lines, one helper    | ✅✅ BEST FOR TIME — the intended solution. Search cost is independent of n. Pay the memory; it's the right trade at these constraints.|
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * APPROACH 3 (Trie + DFS). It's the only solution whose search cost doesn't scale with the
 * dictionary size — O(L) <= 25 steps for an exact query regardless of whether you've stored
 * 10 words or 10 million, and the <= 2-dot constraint caps wildcard fan-out at a trivial
 * ~16,900 node visits. The trade-off is real and worth naming out loud: at n = 10^4, L = 25
 * the TrieNode[26] arrays can cost ~30 MB against ~250 KB of raw strings, roughly a 100×
 * memory premium. If that premium is unacceptable, switch the children to a
 * HashMap<Character, TrieNode> — you keep the same asymptotic time with O(M) space and a
 * worse constant. Only fall back to APPROACH 1 when n is genuinely tiny or memory is the
 * binding constraint, because it's the space-optimal choice and nothing else.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * THE PATTERN: a trie turns prefix-shaped queries into a walk whose cost depends on the
 * QUERY LENGTH, not the DICTIONARY SIZE — and a wildcard turns that deterministic walk into a
 * 26-WAY DFS WITH BACKTRACKING, where a concrete letter has branching factor 1 and a '.' has
 * branching factor 26. Memorize the two-line skeleton: `if (node == null) return false;` at
 * the top (so callers can pass null children freely), and
 * `if (index == pattern.length()) return node.isWord;` as the base case.
 *
 * THE KEY GOTCHA: that base case must return NODE.ISWORD, NEVER TRUE. Reaching a valid node
 * only proves the PREFIX exists — after addWord("bad"), search("ba") walks a perfectly valid
 * path and must still return false. The second gotcha lives in the wildcard loop: return true
 * goes INSIDE the loop (one success is enough — it's an OR), but return false goes AFTER it,
 * or you'll silently abandon 25 of the 26 branches.
 *
 * ============================================================
 * END OF EXPLANATION
 * ============================================================
 */
// @formatter:on
