package Array;

public class IncreasingTripletSubsequence {
    public static void main(String[] args) {
        IncreasingTripletSubsequence increasingTripletSubsequence = new IncreasingTripletSubsequence();
        System.out.println("IncreasingTripletSubsequence : "
                + increasingTripletSubsequence.increasingTriplet(new int[] { 1, 2, 3, 4, 5 }));
        System.out.println("IncreasingTripletSubsequence : "
                + increasingTripletSubsequence.increasingTriplet(new int[] { 5, 4, 3, 2, 1 }));
        System.out.println("IncreasingTripletSubsequence : "
                + increasingTripletSubsequence.increasingTriplet(new int[] { 2, 1, 5, 0, 4, 6 }));
    }

    /**
     * https://leetcode.com/explore/interview/card/top-interview-questions-medium/103/array-and-strings/781/
     * 
     * Given an integer array nums, return true if there exists a triple of indices
     * (i, j, k) such that i < j < k and nums[i] < nums[j] < nums[k]. If no such
     * indices exists, return false.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,2,3,4,5]
     * Output: true
     * Explanation: Any triplet where i < j < k is valid.
     * Example 2:
     * 
     * Input: nums = [5,4,3,2,1]
     * Output: false
     * Explanation: No triplet exists.
     * Example 3:
     * 
     * Input: nums = [2,1,5,0,4,6]
     * Output: true
     * Explanation: One of the valid triplet is (1, 4, 5), because nums[1] == 1 <
     * nums[4] == 4 < nums[5] == 6.
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 5 * 105
     * -231 <= nums[i] <= 231 - 1
     * 
     * 
     * Follow up: Could you implement a solution that runs in O(n) time complexity
     * and O(1) space complexity?
     * 
     */

    public boolean increasingTriplet(int[] nums) {
        int n = nums.length;
        if (n < 3) {
            return false;
        }

        int first = Integer.MAX_VALUE;
        int second = Integer.MAX_VALUE;

        for (int num : nums) {
            if (num <= first) {
                first = num;
            } else if (num <= second) {
                second = num;
            } else {
                return true;
            }
        }
        return false;
    }



    /**
     * 
     * ## 1. Problem Statement

You are given an integer array `nums`.  

You must determine **whether there exists an increasing subsequence of length 3**, i.e., indices `i < j < k` such that:

- `nums[i] < nums[j] < nums[k]`.

You only need to answer **true / false**, not return the triplet itself. [leetcode.doocs](https://leetcode.doocs.org/en/lc/334/)

### Input / Output / Constraints

- **Input:** `int[] nums`
  - Typical constraints: [github](https://github.com/doocs/leetcode/blob/main/solution/0300-0399/0334.Increasing%20Triplet%20Subsequence/README_EN.md)
    - `1 <= nums.length <= 5 * 10^5`
    - `-2^31 <= nums[i] <= 2^31 - 1`
- **Output:** `boolean`
  - `true` if such indices `(i,j,k)` exist.
  - `false` otherwise.

You must compute:  
“Is there a strictly increasing subsequence of length at least 3?”

Example: [leetcode](https://leetcode.com/problems/increasing-triplet-subsequence/)

- `nums = [1,2,3,4,5]` → `true` (e.g. 1 < 2 < 3).
- `nums = [5,4,3,2,1]` → `false`.
- `nums = [2,1,5,0,4,6]` → `true` (`0 < 4 < 6`).

***

## 2. Intuition

Think: “Do I ever see three numbers in order that go strictly up?”

Naive human reasoning:

- Pick each index `j` as the middle.
- Check if there’s some smaller value to the left and some bigger value to the right.
- That’s conceptually correct but leads to O(n²) in code.

Better idea:

- Try to maintain the **best two candidates** for the first and second positions in a potential triplet:
  - `first`: the smallest value seen so far (good candidate for `nums[i]`).
  - `second`: the smallest value that is **greater than `first`** (good candidate for `nums[j]`). [algo](https://algo.monster/liteproblems/334)
- As you scan `nums`:
  - If current `x` is **<= first**, update `first = x` (even better first).
  - Else if `x` is **<= second`, update `second = x` (better second after this first).
  - Else (`x > second`), you have `first < second < x` → triplet exists → return `true`. [massivealgorithms.blogspot](https://massivealgorithms.blogspot.com/2016/02/leetcode-334-increasing-triplet.html)

Why this works:

- You are always maintaining the **smallest possible** `first` and `second`, which makes it easier to find a `third` larger value.
- If you ever find a number that’s larger than `second`, you immediately know there’s an increasing triplet.

Key subtlety: use `<=` when updating `first` and `second` (not `<`), to handle duplicates correctly. [designgurus](https://www.designgurus.io/answers/detail/334-increasing-triplet-subsequence-3incr3trip4subs)

***

## 3. Approach Overview

Let `n = nums.length`.

### Approach 1 – Brute Force O(n³)

- **Key idea:**  
  Check all triplets `(i,j,k)` with three nested loops; if any satisfies `i<j<k` and `nums[i]<nums[j]<nums[k]`, return true. [algomaster](https://algomaster.io/learn/dsa/increasing-triplet-subsequence)
- **When used:**  
  Only for conceptual understanding; impossible for n up to 5e5.

### Approach 2 – DP-like / LIS O(n²)

- **Key idea:**  
  Classic LIS DP: `dp[i] = length of LIS ending at i`. If any `dp[i] >= 3`, return true. [algomaster](https://algomaster.io/learn/dsa/increasing-triplet-subsequence)
- **When used:**  
  Shows relation to LIS; works for smaller n but too slow for 5e5.

### Approach 3 – Optimal O(n) O(1) using two trackers (first, second)

- **Key idea:**  
  Single pass, track two numbers (`first`, `second`) as described above. [docs.vultr](https://docs.vultr.com/problem-set/increasing-triplet-subsequence)
- **When used:**  
  This is the standard interview solution and meets the follow-up requirement of O(n) time and O(1) space. [leetcode.doocs](https://leetcode.doocs.org/en/lc/334/)

Approach 3 is optimal and recommended.

***

## 4. Detailed Solutions in Java

### 4.1 Approach 1 – Brute Force (Triple Nested Loops)

#### Algorithm

1. For every `i` from 0 to n-3:
2. For every `j` from i+1 to n-2:
3. For every `k` from j+1 to n-1:
   - If `nums[i] < nums[j]` and `nums[j] < nums[k]`: return true.
4. If no such triple found, return false.

#### Java Code

```java
public class IncreasingTripletBruteForce {

    public boolean increasingTriplet(int[] nums) {
        int n = nums.length;
        if (n < 3) return false;

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                if (nums[j] <= nums[i]) continue; // must be strictly greater

                for (int k = j + 1; k < n; k++) {
                    if (nums[k] > nums[j]) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
```

#### Complexity

- Time:  
  - Three nested loops up to n each → roughly n³ / 6 checks.
  - Time: **O(n³)**. [algomaster](https://algomaster.io/learn/dsa/increasing-triplet-subsequence)
- Space:  
  - O(1) extra.

Example: n=1000 → ~1.6e8 triplets; too slow for constraints 5e5.

#### Worked Example – `[2,1,5,0,4,6]` (short glimpse)

- `i=0 (2)`, `j=1 (1)` fails since `1 <= 2`.
- `i=0`, `j=2 (5)`:  
  - `k=3 (0)` fails; `k=4 (4)` fails; `k=5 (6)` → `2<5<6` true → return true.

***

### 4.2 Approach 2 – DP / LIS-like O(n²)

We only need to know if there exists LIS of length ≥ 3.

#### Algorithm

1. If n < 3 return false.
2. Initialize `int[] dp = new int[n];` with all 1s (LIS ending at i at least length 1).
3. For each `i` from 0..n-1:
   - For each `j` from 0..i-1:
     - If `nums[j] < nums[i]`:
       - `dp[i] = Math.max(dp[i], dp[j] + 1);`
     - If `dp[i] >= 3`, return true.
4. If finish all, return false. [algomaster](https://algomaster.io/learn/dsa/increasing-triplet-subsequence)

#### Java Code

```java
public class IncreasingTripletDP {

    public boolean increasingTriplet(int[] nums) {
        int n = nums.length;
        if (n < 3) return false;

        int[] dp = new int[n];
        // LIS length at least 1 at each index
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    if (dp[i] >= 3) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
```

#### Complexity

- Time:  
  - For each `i`, loop all `j < i` → ~n²/2 comparisons.
  - Time: **O(n²)**. [algomaster](https://algomaster.io/learn/dsa/increasing-triplet-subsequence)
- Space:  
  - `dp` array length n → **O(n)**.

Example: n=5000 → ~12.5M operations; okay, but for n=5e5 → 1.25e11 operations → too slow.

#### Worked Example – `[2,1,5,0,4,6]`

Indices: 0..5

- dp initially: `[1,1,1,1,1,1]`
- i=0: nothing before → dp=1.
- i=1 (1): j=0 (2) not less → dp=1. [geeksforgeeks](https://www.geeksforgeeks.org/java/implementing-next_permutation-in-java-with-examples/)
- i=2 (5):
  - j=0: 2<5 → dp=max(1, 1+1)=2 [geeksforgeeks](https://www.geeksforgeeks.org/dsa/next-permutation/)
  - j=1: 1<5 → dp=max(2, 1+1)=2 [geeksforgeeks](https://www.geeksforgeeks.org/dsa/next-permutation/)
- i=3 (0): j=0,1,2 all >0 → dp=1. [youtube](https://www.youtube.com/watch?v=cuq7XXxYYOY)
- i=4 (4):
  - j=0: 2<4 → dp=2 [interviewbit](https://www.interviewbit.com/blog/next-permutation-problem/)
  - j=1: 1<4 → dp=2 [interviewbit](https://www.interviewbit.com/blog/next-permutation-problem/)
  - j=2: 5<4? no
  - j=3: 0<4 → dp=max(2, 1+1)=2 [interviewbit](https://www.interviewbit.com/blog/next-permutation-problem/)
- i=5 (6):
  - j=0: 2<6 → dp=2 [algo](https://algo.monster/liteproblems/31)
  - j=1: 1<6 → dp=2 [algo](https://algo.monster/liteproblems/31)
  - j=2: 5<6 → dp=max(2, 2+1)=3 → >=3 → true. [algo](https://algo.monster/liteproblems/31)

DP confirms existence.

***

### 4.3 Approach 3 – Optimal Greedy O(n) / O(1) (two variables)

#### Idea

Maintain two values:

- `first`: smallest number seen so far.
- `second`: smallest number greater than `first` seen so far.

Initialize `first = second = +∞` (use `Integer.MAX_VALUE` or `Long.MAX_VALUE` in Java, given constraints). [youtube](https://www.youtube.com/watch?v=zwA9jdgscgc)

When traversing each `x` in `nums`:

1. If `x <= first`:
   - Update `first = x`.
   - This is a new, better candidate starting point.
2. Else if `x <= second`:
   - Now `first < x <= second`.
   - Update `second = x` (better middle value).
3. Else:
   - `x > second` and since we always maintain `first < second`, we now have `first < second < x` → return true. [algo](https://algo.monster/liteproblems/334)

If loop ends without triggering `true`, return false.

#### Java Code

```java
public class IncreasingTripletOptimal {

    public boolean increasingTriplet(int[] nums) {
        // Need at least 3 numbers
        if (nums.length < 3) {
            return false;
        }

        // Use long to safely cover full int range when using MAX_VALUE
        long first = Long.MAX_VALUE;
        long second = Long.MAX_VALUE;

        for (int num : nums) {
            if (num <= first) {
                // Found new smallest candidate for first
                first = num;
            } else if (num <= second) {
                // Found a better candidate for second (greater than first)
                second = num;
            } else {
                // num > second -> found third element larger than both
                return true;
            }
        }

        return false;
    }
}
```

Using `num <= first` and `num <= second` is crucial to handle duplicates correctly. [massivealgorithms.blogspot](https://massivealgorithms.blogspot.com/2016/02/leetcode-334-increasing-triplet.html)

#### Why this is correct (intuition)

- Invariants:
  - `first` is the smallest value seen so far.
  - `second` is the smallest value > `first` seen so far.
- If we ever see `num > second`, then there exists some earlier index where:
  - `nums[i] = first`, `nums[j] = second`, and `nums[k] = num` with `i<j<k`.
- Even if `first` and/or `second` shift over time, we only need **existence** of a triplet, not indices; the condition guarantees they appear in order. [designgurus](https://www.designgurus.io/answers/detail/334-increasing-triplet-subsequence-3incr3trip4subs)

#### Complexity

- Time:
  - Single pass through array; constant work per element → **O(n)**. [docs.vultr](https://docs.vultr.com/problem-set/increasing-triplet-subsequence)
- Space:
  - Only two extra variables (`first`, `second`) → **O(1)** extra.

For n=5e5, this is ideal.

#### Worked Example 1 – `[1,2,3,4,5]`

Track `(first, second)`:

- Start: `first=inf`, `second=inf`.

1) x=1:
   - 1 <= first → first=1.
   - state: first=1, second=inf.
2) x=2:
   - 2 <= first? no.
   - 2 <= second? yes → second=2.
   - state: first=1, second=2.
3) x=3:
   - 3 <= first? no.
   - 3 <= second? no.
   - So num > second → found triplet 1 < 2 < 3 → return true.

#### Worked Example 2 – `[2,1,5,0,4,6]` (classic)

Initial: `first=inf`, `second=inf`.

1) 2:
   - 2 <= first → first=2, second=inf.
2) 1:
   - 1 <= first → first=1, second=inf. (better first)
3) 5:
   - 5 <= first? no.
   - 5 <= second? yes → second=5.
   - Now first=1, second=5.
4) 0:
   - 0 <= first → first=0, second=5. (we improved first; second still a candidate middle after some earlier first, but we still look for something better)
5) 4:
   - 4 <= first? no.
   - 4 <= second? yes → second=4. (better second with current first=0)
6) 6:
   - 6 <= first? no.
   - 6 <= second? no → 6 > 4 → we have 0 < 4 < 6 → return true.

#### Worked Example 3 – `[5,4,3,2,1]` (no triplet)

- 5: first=5
- 4: first=4
- 3: first=3
- 2: first=2
- 1: first=1
- second remains inf; never find third → false.

***

## 5. Edge Cases

1. **Length < 3** (`nums.length < 3`):
   - Impossible to have triplet → return false (all approaches handle this naturally).

2. **All decreasing** `[5,4,3,2,1]`:
   - Brute and DP: no increasing triplet found.
   - Optimal: `first` keeps shrinking, `second` stays inf; never return true.

3. **Many duplicates but one increasing triplet**: `[1,1,1,1,2,3]`
   - Correct handling requires `<=` not `<` when updating `first`/`second`. [algo](https://algo.monster/liteproblems/334)
   - Flow:
     - first becomes 1 (repeated).
     - second becomes 2.
     - 3 > second → true.

4. **All equal**: `[2,2,2,2]`
   - first=2, second stays 2, but never get `num > second` since all equal → false.

5. **Mixed negatives and positives**: `[-2,-1,0]`
   -  -2→first=-2
   -  -1→second=-1
   -  0>second→true.

6. **Large values near int limits**:
   - Using `long` for first and second is safe with constraints `nums[i]` in full int range. [youtube](https://www.youtube.com/watch?v=i340M1N4i8Y)

Brute and DP will also handle content, but may be too slow on size.

***

## 6. Final Summary

- Goal: decide whether an **increasing triplet subsequence** exists: `nums[i] < nums[j] < nums[k]` with `i<j<k`.
- Brute force and DP illustrate the concept but are too slow for large n.
- The standard and optimal solution is:

> Maintain two running minima:  
> `first` = smallest so far; `second` = smallest greater than `first`.  
> As you traverse:
> - If `num <= first` → update `first`.  
> - Else if `num <= second` → update `second`.  
> - Else → `num` is a valid `third` (> `second`) → return true.

**Key pattern to remember:**

> This problem is a mini version of LIS: you only care if LIS length ≥ 3, so you can compress the state to **two scalars** and detect the third element in one pass with O(1) space. [github](https://github.com/doocs/leetcode/blob/main/solution/0300-0399/0334.Increasing%20Triplet%20Subsequence/README_EN.md)

If you’d like, next we can adapt this reasoning to detect an increasing subsequence of length **k > 3** or relate it to the classic O(n log n) LIS algorithm.
     * 
     */
}
