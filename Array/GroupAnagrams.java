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
                + groupAnagrams.groupAnagrams(new String[] { "eat", "tea", "tan", "ate", "nat", "bat" }));
    }

    /**
     * 
     * https://leetcode.com/explore/interview/card/top-interview-questions-medium/103/array-and-strings/778/
     * 
     * 
     * Given an array of strings strs, group the anagrams together. You can return
     * the answer in any order.
     * 
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

    public List<List<String>> groupAnagrams(String[] strs) {

        // Map<String, List<String>> groups = new HashMap<>();
        // for (String str : strs) {
        // char[] cArray = str.toCharArray();
        // int[] count = new int[26];
        // for (int i = 0; i < cArray.length; i++) {
        // count[cArray[i] - 'a']++;
        // }

        // StringBuilder key = new StringBuilder();
        // for (Integer freq : count) {
        // key.append("#");
        // key.append(freq);
        // }

        // groups.computeIfAbsent(key.toString(), k -> new ArrayList<>()).add(str);
        // }

        // return new ArrayList<>(groups.values());

        Map<String, List<String>> group = new HashMap<>();
        for (String s : strs) {
            char[] sorted = s.toCharArray();
            Arrays.sort(sorted);
            String key = new String(sorted);
            group.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(group.values());
    }

    /**
     * 
     * 
     * ## 1. Problem Statement

### Restate in own words

You are given an array of strings `strs`. You must **group together all strings that are anagrams of each other**.  

Two strings are anagrams if:

- They contain the **same characters**,
- With the **same frequencies**,
- Order doesn’t matter (e.g., `"eat"`, `"tea"`, `"ate"` are anagrams).

The groups can be returned in **any order**, and the strings inside each group can be in any order.

### Input / Output / Constraints

- **Input:** `String[] strs`
  - Typical constraints (LeetCode 49 style):  
    - `1 <= strs.length <= 10^4`  
    - `0 <= strs[i].length <= 100`  
    - Strings contain lowercase English letters `a-z`. [algomap](https://algomap.io/problems/group-anagrams)
- **Output:** `List<List<String>>`
  - Each inner list is a group of anagrams.

### What to compute

Given `strs`, return a list of groups so that:

- Each string appears in **exactly one** group.
- Within each group, **all** strings are pairwise anagrams.
- No string appears in more than one group.

Example:

`["eat","tea","tan","ate","nat","bat"]` → one valid output:

- `["eat","tea","ate"]`
- `["tan","nat"]`
- `["bat"]` [algo](https://algo.monster/liteproblems/49)

***

## 2. Intuition

### Core idea

Anagrams share the **same multiset of characters**. If you can transform each string into a **canonical representation** that is identical for anagrams, you can use that representation as a **hash key** and group easily using a map. [studyalgorithms](https://studyalgorithms.com/string/leetcode-group-anagrams-solution/)

Two common canonical representations:

1. **Sorted characters**  
   - Sort characters in the string; anagrams become identical.  
     - `"eat"` → `"aet"`  
     - `"tea"` → `"aet"`  
     - `"ate"` → `"aet"`  
   - Use this sorted string as the map key.

2. **Character frequency signature**  
   - Count occurrences of each letter (`a` to `z`) into an array of size 26.
   - Convert the count array to a string key, e.g., `"aab"` → counts `[2,1,0,0,...]` → key `"2#1#0#0#...#0"`.  
   - Anagrams will produce the same count vector, thus same key.

### How a human might reason

- Given a list like `["act","pots","tops","cat","stop","hat"]`:
  - Notice `"act"` and `"cat"` share letters {a,c,t}.
  - `"pots","tops","stop"` share letters {o,p,s,t}.
  - “hat” is alone.
- You naturally classify by “bag of letters”.  

Algorithmically:

- For each string:
  - Build an “anagram signature”.
  - Put it into the bucket (list) for that signature.
- Return all buckets.

### Why this is interesting

- Shows how to use **hash maps** with **custom keys**.
- Shows the idea of **normalization**: transforming data to a canonical form for grouping / classification.
- It has two reasonable solutions:
  - Sorting-based: simpler but `O(k log k)` per string.
  - Frequency-based: a little more code but `O(k)` per string, often faster. [neetcode](https://neetcode.io/solutions/group-anagrams)

***

## 3. Approach Overview

Assume:

- `N` = number of strings (`strs.length`).
- `K` = maximum length of a string.

### Approach 1 – Sort each string (Straightforward, very common)

- **Key idea:** Sort chars of each string; use sorted string as key in `Map<String, List<String>>`.
- **Complexity:** O(N·K log K) time; O(N·K) space.
- **When used:** Preferred in interviews because it’s simple and robust. [educative](https://www.educative.io/answers/group-anagrams-leetcode)

### Approach 2 – Character frequency key (Better asymptotically)

- **Key idea:** For each string, build a 26-length count array and use that as a key (converted to string) in map.
- **Complexity:** O(N·K) time; O(N·K) space.
- **When used:** When you want optimal complexity and can assume only lowercase `a-z`. Very popular in editorial/NeetCode style. [learn.innoskrit](https://learn.innoskrit.in/blog/group-anagrams/)

### Approach 3 – Brute-force pairwise anagram checking (Not recommended)

- **Key idea:** For each string, compare with others to decide if anagrams, grouping manually.
- **Complexity:** O(N²·K) or worse.
- **When used:** Only for conceptual understanding; not used for real constraints. [studyalgorithms](https://studyalgorithms.com/string/leetcode-group-anagrams-solution/)

**Optimal:** Approach 2 is asymptotically best; Approach 1 is usually accepted and simpler to code.

***

## 4. Detailed Solutions in Java

### 4.1 Approach 1 – Sort each string

#### Algorithm

1. Create a `Map<String, List<String>>` (e.g., `HashMap`) called `groups`.
2. For each string `s` in `strs`:
   - Convert `s` to a char array, sort it, and make a new `String` from it: `key`.
   - Put `s` into `groups.get(key)` (creating the list if needed).
3. Return `new ArrayList<>(groups.values())`.

#### Java Code

```java
import java.util.*;

public class GroupAnagramsSort {

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();

        for (String s : strs) {
            // Convert string to char array and sort it
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars); // sorted representation

            // Add original string to the correct anagram group
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }

        // Return all groups
        return new ArrayList<>(groups.values());
    }
}
```

#### Complexity

- For each string (N strings):
  - Sorting characters takes O(K log K).
- Total: **O(N · K log K)** time. [algomap](https://algomap.io/problems/group-anagrams)
- Space:
  - Hash map stores N strings plus keys; keys are length up to K.
  - Total string data O(N·K).
  - Extra overhead for map → **O(N·K)**.

Example scale:

- N = 1000, K = 20:  
  Sort cost ~ 1000 · 20 log 20 ≈ 1000 · 20 · 4 ≈ 80k char comparisons; trivial.

#### Worked Example – Approach 1

Input:

`["eat","tea","tan","ate","nat","bat"]`

Initialize: `groups = {}`

1. s = "eat"
   - chars = ['e','a','t'] → sort → ['a','e','t'] → key = "aet"
   - groups["aet"] = ["eat"]

2. s = "tea"
   - chars = ['t','e','a'] → sort → "aet"
   - groups["aet"] = ["eat","tea"]

3. s = "tan"
   - chars = ['t','a','n'] → sort → "ant"
   - groups["ant"] = ["tan"]

4. s = "ate"
   - sort → "aet"
   - groups["aet"] = ["eat","tea","ate"]

5. s = "nat"
   - sort → "ant"
   - groups["ant"] = ["tan","nat"]

6. s = "bat"
   - sort → "abt"
   - groups["abt"] = ["bat"]

Final `groups.values()` →

- `["eat","tea","ate"]`
- `["tan","nat"]`
- `["bat"]`

Order can vary, but grouping is correct.

***

### 4.2 Approach 2 – Character Frequency Key (Optimal time)

This avoids sorting each string.

#### Algorithm

1. Create `Map<String, List<String>>` called `groups`.
2. For each string `s` in `strs`:
   - Create an `int[26]` count array initialised to 0.
   - For each character `c` in `s`, do `count[c - 'a']++`.
   - Convert `count` to a string key, e.g., with a delimiter: `"#1#0#2#..."`.  
     (Important: use a delimiter to distinguish `[1,11]` vs `[11,1]`.)
   - Add `s` to `groups.get(key)`.
3. Return `new ArrayList<>(groups.values())`.

#### Java Code

```java
import java.util.*;

public class GroupAnagramsCount {

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();

        for (String s : strs) {
            // Frequency array for 26 lowercase letters
            int[] count = new int[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }

            // Build a key from counts, e.g. "#1#0#2..."
            StringBuilder keyBuilder = new StringBuilder();
            for (int freq : count) {
                keyBuilder.append('#');
                keyBuilder.append(freq);
            }
            String key = keyBuilder.toString();

            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }

        return new ArrayList<>(groups.values());
    }
}
```

This pattern matches standard editorial solutions: frequency vector as key. [learn.innoskrit](https://learn.innoskrit.in/blog/group-anagrams/)

#### Complexity

- For each string of length K:
  - Counting characters is O(K).
  - Building key is O(26) = O(1).
- Total: **O(N · K)** time, better than sorting for large K. [neetcode](https://neetcode.io/solutions/group-anagrams)
- Space:
  - Map holds N strings and their keys. Keys are fixed size (~26 ints → short string).
  - Overall roughly **O(N·K)** for storing original strings plus O(N) for keys.

Example scale:

- N = 1000, K = 100:  
  1000 · 100 = 100k char visits; very fast.

#### Worked Example – Approach 2

`strs = ["eat","tea","tan","ate","nat","bat"]`.

We’ll show keys:

1. "eat"
   - counts: e(1), a(1), t(1), others 0.
   - key: `"#1#0#0#0#1#0#0...#1#..."` (actual string but conceptually same).
   - groups[key] = ["eat"].

2. "tea"
   - same counts as "eat".
   - same key.
   - groups[key] = ["eat","tea"].

3. "tan"
   - counts: t(1), a(1), n(1).
   - new key.
   - groups[key2] = ["tan"].

4. "ate"
   - same as "eat"/"tea" → key1.
   - groups[key1] = ["eat","tea","ate"].

5. "nat"
   - same as "tan" → key2.
   - groups[key2] = ["tan","nat"].

6. "bat"
   - counts: b(1), a(1), t(1).
   - key3.
   - groups[key3] = ["bat"].

Result identical grouping.

***

### 4.3 Approach 3 – Brute Force (Pairwise Checking, Not Recommended)

#### Algorithm

1. Maintain a `List<List<String>> groups`.
2. For each string `s`:
   - Try to place it into an existing group by checking if `s` is an anagram of group’s representative (say first string in that group).
   - If matches an existing group, add to that group.
   - Otherwise create a new group.
3. The anagram check can be done by sorting or counting each time.

#### Why it’s bad

- For each string, you may compare with many groups: worst O(N) per string.
- Each anagram check is at least O(K log K) (sorting) or O(K) (counting).
- Total time can be **O(N² · K)**, not acceptable for large N. [studyalgorithms](https://studyalgorithms.com/string/leetcode-group-anagrams-solution/)

This is mostly a conceptual “anti-pattern” here.

***

## 5. Edge Cases

1. **Single string**: `["abc"]`
   - Output: `[["abc"]]`.
   - Both approaches handle this naturally (map with one key → one group).

2. **Empty string(s)**: `["",""]`, `["","abc","bca"]`
   - Empty strings are all anagrams of each other.
   - Sorting: `""` sorted is `""`; all empties share same key.
   - Frequency: counts array all zeros → same key.

3. **No anagrams at all**: `["a","b","c"]`
   - Each string forms its own group.

4. **All strings identical**: `["aa","aa","aa"]`
   - All are anagrams; all go into one group.

5. **Large input**:
   - N up to 10^4, K up to 100. Both Approaches 1 and 2 are fine; Approach 2 is more scalable.

6. **Non-lowercase / Unicode** (if constraints changed):
   - Sorting approach still works if you can sort characters.
   - Frequency approach needs adjusting (map char→count with larger alphabet).

***

## 6. Final Summary

- Problem: group strings such that each group contains anagrams.
- Core trick: design a **canonical key** for each string so anagrams share the same key, and use a **hash map** from key to list of strings.

**Approaches:**

- **Sorting-based (Approach 1):**
  - Key: sorted characters of the string.
  - Time O(N·K log K), Space O(N·K).
  - Very easy and widely used.

- **Frequency-based (Approach 2, optimal):**
  - Key: frequency vector of 26 letters encoded as string.
  - Time O(N·K), Space O(N·K).
  - Slightly more code, but best complexity.

- **Brute-force (Approach 3):**
  - Pairwise comparisons; O(N²·K), not suitable.

**What to remember:**

> “Group Anagrams” is a quintessential **hashing + normalization** problem:  
> Normalize each string (by sorting or counting), use that as a **hash key**, and group via a map.

If you want, next we can compare actual runtime characteristics between sorting-based and counting-based implementations in Java, or walk through a tricky example like `["","b",""]` or large sets of repeated anagrams.
     * 
     */
}
