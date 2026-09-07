package Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupAnagrams {
   public static void main(String[] args) {
      GroupAnagrams groupAnagrams = new GroupAnagrams();
      System.out.println("GroupAnagrams : "
            + groupAnagrams.groupAnagramsSortKeyHashing(new String[] { "eat", "tea", "tan", "ate", "nat", "bat" }));
      System.out.println("--------------------------------------------------------");
      System.out.println("GroupAnagrams : "
            + groupAnagrams.groupAnagramsCountKeyHashing(new String[] { "eat", "tea", "tan", "ate", "nat", "bat" }));
      System.out.println("--------------------------------------------------------");
      System.out.println("GroupAnagrams : "
            + groupAnagrams.groupAnagramsBruteForce(new String[] { "eat", "tea", "tan", "ate", "nat", "bat" }));
   }

   // @formatter:off
    /**
     * 
     * https://leetcode.com/explore/interview/card/top-interview-questions-medium/103/array-and-strings/778/
     * 
     * 
     * Given an array of strings strs, group the anagrams together. You can return
     * the answer in any order.
     * 
     * To make a valid anagram, you must follow three simple rules:
     * Use the exact same letters: You must use every single letter from the original word or phrase.
     * You cannot add new letters or leave any out.
     * 
     * Use each letter only once: If the original word has one "T", your anagram can only have one "T". 
     * If it has two "E"s, your anagram must have exactly two "E"s.
     * 
     * Form real words: The rearranged letters must spell an actual, meaningful word or phrase in the language you are using. 
     * Random gibberish does not count.
     * 
     * 
     * Example 1:
     * 
     * Input: strs = ["eat","tea","tan","ate","nat","bat"]
     * 
     * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
     * 
     * Explanation:
     * 
     * There is no string in strs that can be rearranged to form "bat".
     * The strings "nat" and "tan" are anagrams as they can be rearranged to form
     * each other.
     * The strings "ate", "eat", and "tea" are anagrams as they can be rearranged to
     * form each other.
     * Example 2:
     * 
     * Input: strs = [""]
     * 
     * Output: [[""]]
     * 
     * Example 3:
     * 
     * Input: strs = ["a"]
     * 
     * Output: [["a"]]
     * 
     * 
     * 
     * Constraints:
     * 
     * 1 <= strs.length <= 104
     * 0 <= strs[i].length <= 100
     * strs[i] consists of lowercase English letters.
     * 
     */
    // @formatter:on

   // @formatter:off
    /**
     * 
     *Approach	               Time  	      Space	      Code Complexity      Recommended?
     *Sort-Key Hashing	      O(n · k log k)	O(n · k)	   Very low	            ✅ Great for interviews when k is small
     * 
     * @param strs
     * @return
     */
    // @formatter:on
   public List<List<String>> groupAnagramsSortKeyHashing(String[] strs) {
      Map<String, List<String>> group = new HashMap<>();
      for (String s : strs) {
         char[] sorted = s.toCharArray();
         Arrays.sort(sorted);
         String key = new String(sorted);
         group.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
      }
      return new ArrayList<>(group.values());
   }

   // @formatter:off
    /**
     * 
     *Approach	               Time  	      Space	      Code Complexity      Recommended?
     *Count-Key Hashing	      O(n · k)	      O(n · k)	   Low–moderate	      ✅✅ Best for time — optimal
     * 
     * @param strs
     * @return
     */
    // @formatter:on
   public List<List<String>> groupAnagramsCountKeyHashing(String[] strs) {
      Map<String, List<String>> charCountMap = new HashMap<>();
      for (String word : strs) {
         int[] count = new int[26];
         for (int i = 0; i < word.length(); i++) {
            count[word.charAt(i) - 'a']++;
         }
         StringBuilder keyBuilder = new StringBuilder();
         for (int c : count) {
            keyBuilder.append("#").append(c);
         }

         String key = keyBuilder.toString();
         charCountMap.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
      }
      return new ArrayList<>(charCountMap.values());
   }

   // @formatter:off
    /**
     * 
     *Approach	               Time  	      Space	      Code Complexity      Recommended?
     *Brute Force Pairwise 	O(n² · k)	   O(n · k)	   Low	               ❌ Not for production — too slow on large n
     * 
     * @param strs
     * @return
     */
    // @formatter:on
   public List<List<String>> groupAnagramsBruteForce(String[] strs) {
      List<List<String>> groups = new ArrayList<>();
      for (String word : strs) {
         boolean placed = false;
         for (List<String> group : groups) {
            if (isAnagram(word, group.get(0))) {
               group.add(word);
               placed = true;
               break;
            }
         }
         if (!placed) {
            List<String> newGroup = new ArrayList<>();
            newGroup.add(word);
            groups.add(newGroup);
         }
      }
      return groups;
   }

   private boolean isAnagram(String a, String b) {
      if (a.length() != b.length())
         return false;
      int[] count = new int[26];
      for (int i = 0; i < a.length(); i++) {
         count[a.charAt(i) - 'a']++;
         count[b.charAt(i) - 'a']--;
      }
      for (int c : count) {
         if (c != 0) {
            return false;
         }
      }
      return true;
   }
}

// @formatter:off
/*
 * ============================================================
 * GROUP ANAGRAMS - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You're given an array of lowercase strings. Group together all strings
 * that are ANAGRAMS of one another - words made of the exact same letters
 * with the exact same counts, just in a different order (e.g., "eat",
 * "tea", "ate"). Return the groups. Order of groups and order within a
 * group do not matter.
 *
 * This is LeetCode #49, Group Anagrams (Medium).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * String[] strs - array of strings, each lowercase letters (some may be "").
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * List<List<String>> - list of groups; each inner list holds mutual anagrams.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * 1 <= strs.length <= 10^4
 * 0 <= strs[i].length <= 100
 * strs[i] contains only lowercase English letters (a-z).
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * For every string, produce a canonical signature identical for all
 * anagrams and different for non-anagrams. Bucket strings by signature
 * and return the buckets.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 * Input:  ["eat","tea","tan","ate","nat","bat"]
 * Output: [["eat","tea","ate"], ["tan","nat"], ["bat"]]
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Two words are anagrams iff they have the same multiset of letters. Give
 * every anagram the same "fingerprint," then drop each word into a bucket
 * keyed by that fingerprint. A HashMap<fingerprint, List<String>> does the
 * grouping for us.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Anagrams differ only in letter ORDER, never in CONTENT.
 * 2. Need a representation that ignores order but preserves content.
 * 3. Two fingerprints: (a) SORT the letters - "eat"/"tea" -> "aet"; or
 *    (b) COUNT the letters - both become a:1, e:1, t:1.
 * 4. Use HashMap<fingerprint, List<String>>; append each word to its entry.
 * 5. Return all the map's values.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge              | Why it's tricky                                   |
 * |------------------------|---------------------------------------------------|
 * | Choosing a good key    | Must match anagrams, differ for non-anagrams.     |
 * | Making the key hashable| char[] uses identity hashCode; convert to String. |
 * | Avoiding O(n^2)        | Pairwise comparison is quadratic; map is linear.  |
 * | Count-key encoding     | Serialize counts with delimiters to avoid clashes.|
 * | Empty strings          | "" is valid and forms its own group.              |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach              | Key Idea                              | Best Used When            | Time            | Space      |
 * |---|-----------------------|---------------------------------------|---------------------------|-----------------|------------|
 * | 1 | Brute Force Pairwise  | Compare vs group representatives      | Tiny inputs / teaching    | O(n^2 * k)      | O(n * k)   |
 * | 2 | Sort-Key Hashing      | Sorted string as map key              | Simple code; small k      | O(n * k log k)  | O(n * k)   |
 * | 3 | Count-Key Hashing (*) | 26-length count array as key          | Optimal time; large k     | O(n * k)        | O(n * k)   |
 *
 * n = number of strings, k = max string length. All three use O(n * k)
 * space to store the answer (an O(n * k) floor), so SPACE is not the
 * differentiator - only TIME is. Brute force is quadratic in n; sorting
 * adds log k per string. Count-key hashing (Approach 3) is OPTIMAL,
 * removing both n^2 and log k. Prefer Approach 3 when k is large;
 * Approach 2 is often preferred in interviews for brevity when k is small.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force Pairwise
 * ------------------------------------------------------------
 * 1. Maintain a list of groups, each represented by its first member.
 * 2. For each string, test it against each group's representative.
 * 3. If it matches, add there; otherwise start a new group.
 * 4. Return all groups.
 *
 *    import java.util.*;
 *
 *    public class GroupAnagramsBrute {
 *        public List<List<String>> groupAnagrams(String[] strs) {
 *            List<List<String>> groups = new ArrayList<>();
 *            for (String word : strs) {
 *                boolean placed = false;
 *                for (List<String> group : groups) {
 *                    if (isAnagram(word, group.get(0))) {
 *                        group.add(word);
 *                        placed = true;
 *                        break;
 *                    }
 *                }
 *                if (!placed) {
 *                    List<String> newGroup = new ArrayList<>();
 *                    newGroup.add(word);
 *                    groups.add(newGroup);
 *                }
 *            }
 *            return groups;
 *        }
 *
 *        private boolean isAnagram(String a, String b) {
 *            if (a.length() != b.length()) return false;
 *            int[] count = new int[26];
 *            for (int i = 0; i < a.length(); i++) {
 *                count[a.charAt(i) - 'a']++;
 *                count[b.charAt(i) - 'a']--;
 *            }
 *            for (int c : count) if (c != 0) return false;
 *            return true;
 *        }
 *
 *        public static void main(String[] args) {
 *            GroupAnagramsBrute solver = new GroupAnagramsBrute();
 *            String[] input = {"eat","tea","tan","ate","nat","bat"};
 *            System.out.println(solver.groupAnagrams(input));
 *        }
 *    }
 *
 * Worst case (all unique) forms n groups; each string is compared against
 * up to n representatives at O(k) each -> O(n^2 * k).
 *
 * ------------------------------------------------------------
 * Approach 2: Sort-Key Hashing
 * ------------------------------------------------------------
 * 1. Create HashMap<String, List<String>>.
 * 2. Sort each word's characters to form the key.
 * 3. Append the original word to that key's list.
 * 4. Return all map values.
 *
 *    import java.util.*;
 *
 *    public class GroupAnagramsSort {
 *        public List<List<String>> groupAnagrams(String[] strs) {
 *            Map<String, List<String>> map = new HashMap<>();
 *            for (String word : strs) {
 *                char[] chars = word.toCharArray();
 *                Arrays.sort(chars);
 *                String key = new String(chars);
 *                map.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
 *            }
 *            return new ArrayList<>(map.values());
 *        }
 *
 *        public static void main(String[] args) {
 *            GroupAnagramsSort solver = new GroupAnagramsSort();
 *            String[] input = {"eat","tea","tan","ate","nat","bat"};
 *            System.out.println(solver.groupAnagrams(input));
 *        }
 *    }
 *
 * new String(chars) is essential - a raw char[] uses identity hashCode,
 * so equal arrays would hash to different buckets.
 *
 * ------------------------------------------------------------
 * Approach 3: Count-Key Hashing (OPTIMAL)
 * ------------------------------------------------------------
 * 1. Create HashMap<String, List<String>>.
 * 2. Build a 26-length int array of letter counts per word.
 * 3. Serialize counts with a delimiter (e.g., #1#0#0...#1...).
 * 4. Append the word to that key's list.
 * 5. Return all map values.
 *
 *    import java.util.*;
 *
 *    public class GroupAnagramsCount {
 *        public List<List<String>> groupAnagrams(String[] strs) {
 *            Map<String, List<String>> map = new HashMap<>();
 *            for (String word : strs) {
 *                int[] count = new int[26];
 *                for (int i = 0; i < word.length(); i++) {
 *                    count[word.charAt(i) - 'a']++;
 *                }
 *                StringBuilder keyBuilder = new StringBuilder();
 *                for (int c : count) {
 *                    keyBuilder.append('#').append(c);
 *                }
 *                String key = keyBuilder.toString();
 *                map.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
 *            }
 *            return new ArrayList<>(map.values());
 *        }
 *
 *        public static void main(String[] args) {
 *            GroupAnagramsCount solver = new GroupAnagramsCount();
 *            String[] input = {"eat","tea","tan","ate","nat","bat"};
 *            System.out.println(solver.groupAnagrams(input));
 *        }
 *    }
 *
 * The # delimiter matters: without it counts can run together (e.g., "1"
 * then "12" -> "112", same as "11" then "2"). Delimiting (#1#12 vs #11#2)
 * removes the ambiguity.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 * n = number of strings, k = max string length.
 *
 * Approach 1 - Brute Force Pairwise
 *   Time:  O(n^2 * k). Worst case n groups; each string vs up to n reps at
 *          O(k) each.
 *   Space: O(n * k) for the output groups.
 *   Example: n=1000 unique, k=10 -> ~1000*1000*10 = 10^7 char ops.
 *
 * Approach 2 - Sort-Key Hashing
 *   Time:  O(n * k log k). Sorting k chars per string is O(k log k).
 *   Space: O(n * k) for keys plus output.
 *   Example: n=10^4, k=100 -> ~10^4*100*7 = 7*10^6 ops.
 *
 * Approach 3 - Count-Key Hashing (OPTIMAL)
 *   Time:  O(n * k). One O(k) pass per string; 26-slot key is constant.
 *   Space: O(n * k) for keys plus output.
 *   Example: n=10^4, k=100 -> ~10^6 ops - smallest of the three.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * Input: ["eat","tea","tan","ate","nat","bat"]
 *
 * ------------------------------------------------------------
 * Approach 1 - Brute Force Pairwise
 * ------------------------------------------------------------
 * groups = []
 * "eat" -> no match          -> [["eat"]]
 * "tea" -> anagram of "eat"  -> [["eat","tea"]]
 * "tan" -> no match          -> [["eat","tea"], ["tan"]]
 * "ate" -> anagram of "eat"  -> [["eat","tea","ate"], ["tan"]]
 * "nat" -> anagram of "tan"  -> [["eat","tea","ate"], ["tan","nat"]]
 * "bat" -> no match          -> [["eat","tea","ate"], ["tan","nat"], ["bat"]]
 * Output: [["eat","tea","ate"], ["tan","nat"], ["bat"]]
 *
 * ------------------------------------------------------------
 * Approach 2 - Sort-Key Hashing
 * ------------------------------------------------------------
 * | Word | Sorted key | Map state after insertion                 |
 * |------|-----------|-------------------------------------------|
 * | eat  | aet       | {aet:[eat]}                               |
 * | tea  | aet       | {aet:[eat,tea]}                           |
 * | tan  | ant       | {aet:[eat,tea], ant:[tan]}                |
 * | ate  | aet       | {aet:[eat,tea,ate], ant:[tan]}            |
 * | nat  | ant       | {aet:[eat,tea,ate], ant:[tan,nat]}        |
 * | bat  | abt       | {aet:[eat,tea,ate], ant:[tan,nat], abt:[bat]} |
 * Output: [[eat,tea,ate], [tan,nat], [bat]]
 *
 * ------------------------------------------------------------
 * Approach 3 - Count-Key Hashing
 * ------------------------------------------------------------
 * | Word | Nonzero counts | Key  | Map bucket                          |
 * |------|----------------|------|-------------------------------------|
 * | eat  | a1 e1 t1       | K1   | {K1:[eat]}                          |
 * | tea  | a1 e1 t1       | K1   | {K1:[eat,tea]}                      |
 * | tan  | a1 n1 t1       | K2   | {K1:[eat,tea], K2:[tan]}            |
 * | ate  | a1 e1 t1       | K1   | {K1:[eat,tea,ate], K2:[tan]}        |
 * | nat  | a1 n1 t1       | K2   | {K1:[eat,tea,ate], K2:[tan,nat]}    |
 * | bat  | a1 b1 t1       | K3   | {K1:[eat,tea,ate], K2:[tan,nat], K3:[bat]} |
 * Output: [[eat,tea,ate], [tan,nat], [bat]]
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 * | Edge Case              | Input        | Expected Output       | How Handled                         |
 * |------------------------|--------------|-----------------------|-------------------------------------|
 * | Single string          | ["abc"]      | [["abc"]]             | One key, one group.                 |
 * | Empty string present   | [""]         | [[""]]                | Empty word -> consistent zero key.  |
 * | Multiple empty strings | ["",""]      | [["",""]]             | Same empty key -> grouped.          |
 * | No anagrams at all     | ["a","b","c"]| [["a"],["b"],["c"]]   | Distinct keys -> separate groups.   |
 * | All identical          | ["ab","ab"]  | [["ab","ab"]]         | Same key; duplicates preserved.     |
 * | Duplicates + anagrams  | ["ab","ba","ab"] | [["ab","ba","ab"]] | One key; duplicates kept.           |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 - Using a char[] directly as a map key.
 *   WRONG:
 *      char[] key = word.toCharArray();
 *      Arrays.sort(key);
 *      map.get(key); // identity hashCode; never matches
 *   CORRECT:
 *      String key = new String(chars);
 *
 * Pitfall 2 - Concatenating counts without a delimiter.
 *   WRONG:   for (int c : count) keyBuilder.append(c);   // "112" collisions
 *   CORRECT: for (int c : count) keyBuilder.append('#').append(c);
 *
 * Pitfall 3 - Deduplicating within a group. Groups keep duplicate strings;
 *   using a Set for the inner group would wrongly drop repeats.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 * Q: What edge cases might this miss?
 * A: Empty and duplicate strings are common misses; both handled here.
 *    Non-lowercase input would break the - 'a' indexing, but constraints
 *    guarantee lowercase; generalize with a Map<Character,Integer> if needed.
 *
 * Q: Are there any type mismatches?
 * A: No. Keys are String, values List<String>; return
 *    new ArrayList<>(map.values()) matches List<List<String>>.
 *    computeIfAbsent returns the list so .add chains safely.
 *
 * Q: How can I verify this works right now?
 *
 *    import java.util.*;
 *
 *    public class GroupAnagramsVerify {
 *        // paste groupAnagrams from Approach 3 here
 *
 *        private static Set<Set<String>> normalize(List<List<String>> groups) {
 *            Set<Set<String>> result = new HashSet<>();
 *            for (List<String> g : groups) result.add(new HashSet<>(g));
 *            return result;
 *        }
 *
 *        public static void main(String[] args) {
 *            GroupAnagramsCount solver = new GroupAnagramsCount();
 *            Set<Set<String>> got = normalize(solver.groupAnagrams(
 *                new String[]{"eat","tea","tan","ate","nat","bat"}));
 *            Set<Set<String>> want = normalize(Arrays.asList(
 *                Arrays.asList("eat","tea","ate"),
 *                Arrays.asList("tan","nat"),
 *                Arrays.asList("bat")));
 *            assert got.equals(want) : "basic case failed";
 *            assert solver.groupAnagrams(new String[]{""}).size() == 1 : "empty";
 *            assert solver.groupAnagrams(new String[]{"a"}).size() == 1 : "single";
 *            assert solver.groupAnagrams(new String[]{"a","b","c"}).size() == 3 : "none";
 *            System.out.println("All assertions passed.");
 *        }
 *    }
 *
 * Run with: java -ea GroupAnagramsVerify (compare as sets-of-sets since
 * group order and intra-group order are not guaranteed).
 *
 * | Approach   | Risk                          | Mitigation                          |
 * |------------|-------------------------------|-------------------------------------|
 * | Brute Force| Quadratic blowup on large n   | Use only for tiny inputs.           |
 * | Sort-Key   | char[] used as key by mistake | Wrap in new String(chars).          |
 * | Count-Key  | Ambiguous run-together counts | Delimit counts with '#'.            |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #49 - Difficulty: Medium - Very high interview frequency.
 *
 * | Company          | Frequency | Notes                                    |
 * |------------------|-----------|------------------------------------------|
 * | Amazon           | *****     | Classic hashing/grouping warm-up.        |
 * | Google           | *****     | Often paired with key-design follow-up.  |
 * | Facebook (Meta)  | *****     | Frequent phone-screen question.          |
 * | Microsoft        | ****      | Appears in OA and onsite rounds.         |
 * | Bloomberg        | ****      | Popular for testing map fluency.         |
 * | Apple            | ***       | Occasional; sometimes as a variant.      |
 * | Uber             | ***       | Shows up in phone screens.               |
 * | Adobe            | ***       | Common in India-based loops.             |
 * | Goldman Sachs    | **        | Occasionally in coding rounds.           |
 * | Oracle           | **        | Less frequent, but seen.                 |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 * | Approach            | Time            | Space    | Code Complexity | Recommended?                     |
 * |---------------------|-----------------|----------|-----------------|----------------------------------|
 * | Brute Force Pairwise| O(n^2 * k)      | O(n * k) | Low             | NO - too slow on large n         |
 * | Sort-Key Hashing    | O(n * k log k)  | O(n * k) | Very low        | OK - great for interviews (small k) |
 * | Count-Key Hashing   | O(n * k)        | O(n * k) | Low-moderate    | BEST for time - optimal          |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use Count-Key Hashing (Approach 3) for optimal O(n * k) time. All three
 * share the O(n * k) space floor (the output must hold every character), so
 * there is no separate space winner to trade off - pick the fastest.
 * Sort-Key Hashing is an acceptable, briefer interview answer when k is small.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * Pattern: canonical key + hash-bucket grouping. Reduce each item to an
 * order-independent signature, then group by it in a HashMap. For anagrams
 * the signature is the sorted string (O(k log k)) or a delimited 26-letter
 * count (O(k), optimal). Two gotchas: never use a raw char[] as a map key,
 * and always delimit count keys so digits can't run together.
 */
// @formatter:on
