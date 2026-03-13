package Array;

import java.util.Arrays;

public class RotateArray {
    public static void main(String[] args) {
        RotateArray rotateArray = new RotateArray();
        rotateArray.rotate(new int[] { 1, 2, 3, 4, 5, 6, 7 }, 3);
    }

    /*
     * Given an integer array nums, rotate the array to the right by k steps, where
     * k is non-negative.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,2,3,4,5,6,7], k = 3
     * Output: [5,6,7,1,2,3,4]
     * Explanation:
     * rotate 1 steps to the right: [7,1,2,3,4,5,6]
     * rotate 2 steps to the right: [6,7,1,2,3,4,5]
     * rotate 3 steps to the right: [5,6,7,1,2,3,4]
     * Example 2:
     * 
     * Input: nums = [-1,-100,3,99], k = 2
     * Output: [3,99,-1,-100]
     * Explanation:
     * rotate 1 steps to the right: [99,-1,-100,3]
     * rotate 2 steps to the right: [3,99,-1,-100]
     * 
     * 
     * Constraints:
     * 
     * 1 <= nums.length <= 105
     * -231 <= nums[i] <= 231 - 1
     * 0 <= k <= 105
     * 
     * 
     * Follow up:
     * 
     * Try to come up with as many solutions as you can. There are at least three
     * different ways to solve this problem.
     * Could you do it in-place with O(1) extra space?
     */

    public void rotate(int[] nums, int k) {
        k %= nums.length;
        rotateArray(nums, 0, nums.length);
        rotateArray(nums, 0, k);
        rotateArray(nums, k, nums.length);
    }

    public int[] rotateArray(int[] input, int start, int end) {
        for (int i = start; i < end; i++) {
            int temp = input[i];
            input[i] = input[end - 1];
            input[end - 1] = temp;
            end--;
            System.out.println("" + Arrays.toString(input));
        }
        return input;
    }


    /*
    # Rotate Array — Deep Dive Explanation

---

## 1. Problem Statement

### What the Problem Says
Given an integer array `nums` and a non-negative integer `k`, **rotate the array to the right by `k` steps**.

Rotating right by 1 step means the **last element moves to the front**, and every other element shifts one position to the right.

### Input Format
- `int[] nums` — an array of integers (can include negatives, duplicates, zeros)
- `int k` — number of rotation steps (non-negative)

### Output Format
- The array `nums` must be **modified in-place** (no return value needed)

### Constraints (LeetCode #189)
- `1 <= nums.length <= 10^5`
- `-2^31 <= nums[i] <= 2^31 - 1`
- `0 <= k <= 10^5`

### What Needs to Be Computed
After rotating right by `k`:
- The element at index `i` moves to index `(i + k) % n`
- Equivalently, the **last `k % n` elements** wrap around to the **front**

**Example:**
```
nums = [1, 2, 3, 4, 5, 6, 7],  k = 3
After rotation: [5, 6, 7, 1, 2, 3, 4]
```
The last 3 elements `[5, 6, 7]` moved to the front.

---

## 2. Intuition

### Core Idea in Plain English
Imagine the array is a circular track. Each element has a car on it. Rotating right by `k` means every car moves `k` positions clockwise. The cars that "fall off" the right end reappear at the left end.

### How a Human Reasons About It
1. If `k >= n`, rotating by `n` brings everything back to the original position — so only `k % n` matters.
2. The last `k % n` elements are the ones that wrap to the front.
3. The first `n - k` elements shift right to fill the back.

### What Makes This Tricky
- **`k` can be larger than `n`** — you must handle `k = k % n`
- The problem says **in-place** — you can't just build a new array (well, you can for brute force, but optimal solutions avoid extra space)
- The **reverse trick** is non-obvious and elegant — it's a classic interview insight worth memorizing

---

## 3. Approach Overview

| # | Approach | Time | Space | Notes |
|---|----------|------|-------|-------|
| 1 | Extra Array (Brute Force) | O(n) | O(n) | Simple, clean, but uses extra space |
| 2 | Rotate One Step at a Time | O(n×k) | O(1) | Very slow for large k |
| 3 | Cyclic Replacements | O(n) | O(1) | Optimal but tricky to implement |
| 4 | **Three Reverses (Optimal)** | **O(n)** | **O(1)** | **Simplest optimal — recommended** |

### Which Is Best?
**Approach 4 (Three Reverses)** is the recommended approach:
- O(n) time, O(1) space
- Dead simple to code correctly in an interview
- Based on a beautiful mathematical insight

---

## 4. Detailed Solutions in Java

---

### ✅ Approach 1: Extra Array

#### Algorithm
1. Compute `k = k % n` to handle over-rotation
2. Create a new array `result` of same size
3. For each index `i`, place `nums[i]` at position `(i + k) % n` in `result`
4. Copy `result` back into `nums`

```java
class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n; // Handle k >= n

        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            result[(i + k) % n] = nums[i]; // Place each element at its rotated position
        }

        // Copy result back into nums (in-place requirement)
        System.arraycopy(result, 0, nums, 0, n);
    }
}
```

---

### ⚠️ Approach 2: Rotate One Step at a Time

#### Algorithm
1. Repeat `k` times: save the last element, shift everything right by 1, put saved element at front
2. Apply `k = k % n` first to avoid unnecessary work

```java
class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;

        for (int step = 0; step < k; step++) {
            int last = nums[n - 1]; // Save the last element

            // Shift every element one position to the right
            for (int i = n - 1; i > 0; i--) {
                nums[i] = nums[i - 1];
            }

            nums[0] = last; // Place saved element at front
        }
    }
}
```
> ⚠️ This is O(n×k) — very slow if both n and k are large (e.g., n=100000, k=50000 → 5 billion operations). **Do not use in production or for large inputs.**

---

### ✅ Approach 3: Cyclic Replacements

#### Algorithm
The key insight: element at index `i` should go to `(i + k) % n`. So we can follow the "cycle" — place element `i` at its destination, save what was there, and repeat until we return to start.

If `n` and `k` share a GCD > 1, there are multiple independent cycles. We start a new cycle for each one.

```java
class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        if (k == 0) return;

        int count = 0; // Total elements placed

        for (int start = 0; count < n; start++) {
            int current = start;
            int prevValue = nums[start];

            do {
                int next = (current + k) % n; // Destination index
                int temp = nums[next];        // Save what's there
                nums[next] = prevValue;        // Place current element
                prevValue = temp;              // Move to next in cycle
                current = next;
                count++;
            } while (current != start); // Stop when cycle completes
        }
    }
}
```

---

### ⭐ Approach 4: Three Reverses (Optimal — Recommended)

#### The Mathematical Insight

For `nums = [1,2,3,4,5,6,7]`, `k = 3`:

The result should be `[5,6,7,1,2,3,4]`.

Notice:
- The **last k elements** `[5,6,7]` go to the front
- The **first n-k elements** `[1,2,3,4]` go to the back

**Trick:** If you reverse the entire array, then reverse each part separately, you get exactly this!

```
Original:         [1, 2, 3, 4, 5, 6, 7]
Step 1 - Reverse all:      [7, 6, 5, 4, 3, 2, 1]
Step 2 - Reverse [0..k-1]: [5, 6, 7, 4, 3, 2, 1]
Step 3 - Reverse [k..n-1]: [5, 6, 7, 1, 2, 3, 4]  ✓
```

#### Algorithm
1. `k = k % n`
2. Reverse the entire array
3. Reverse the first `k` elements
4. Reverse the remaining `n - k` elements

```java
class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n; // Normalize k
        if (k == 0) return; // No rotation needed

        reverse(nums, 0, n - 1);     // Step 1: Reverse entire array
        reverse(nums, 0, k - 1);     // Step 2: Reverse first k elements
        reverse(nums, k, n - 1);     // Step 3: Reverse remaining n-k elements
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
}
```

---

## 5. Time & Space Complexity

### Approach 1: Extra Array
| | Complexity | Reasoning |
|--|--|--|
| **Time** | O(n) | One pass to fill result + one `arraycopy` of size n |
| **Space** | O(n) | We allocate a new array of size n |

**Walk-through:** n=7, k=3 → 7 writes + 7 copies = 14 operations total.

---

### Approach 2: Rotate One Step at a Time
| | Complexity | Reasoning |
|--|--|--|
| **Time** | O(n × k) | k outer iterations, each does n shifts |
| **Space** | O(1) | Only one temp variable |

**Walk-through:** n=100,000, k=50,000 → 5,000,000,000 operations — too slow!

---

### Approach 3: Cyclic Replacements
| | Complexity | Reasoning |
|--|--|--|
| **Time** | O(n) | Every element is moved exactly once |
| **Space** | O(1) | Only a few integer variables |

**Walk-through:** n=7, k=3 → 7 placements total regardless of cycle structure.

---

### Approach 4: Three Reverses
| | Complexity | Reasoning |
|--|--|--|
| **Time** | O(n) | 3 reverse passes together touch each element at most twice |
| **Space** | O(1) | Only swap variable; in-place reversal |

**Walk-through:** n=7, k=3:
- Reverse all: 3 swaps (pairs: 0↔6, 1↔5, 2↔4)
- Reverse first 3: 1 swap (0↔2)
- Reverse last 4: 2 swaps (3↔6, 4↔5)
- Total: ~6 operations for n=7

---

## 6. Complete Worked Examples

---

### Approach 1: Extra Array

**Input:** `nums = [1, 2, 3, 4, 5]`, `k = 2`

```
n = 5, k = 2 % 5 = 2
result = [0, 0, 0, 0, 0]

i=0: result[(0+2)%5] = result[2] = 1   → result = [0,0,1,0,0]
i=1: result[(1+2)%5] = result[3] = 2   → result = [0,0,1,2,0]
i=2: result[(2+2)%5] = result[4] = 3   → result = [0,0,1,2,3]
i=3: result[(3+2)%5] = result[0] = 4   → result = [4,0,1,2,3]
i=4: result[(4+2)%5] = result[1] = 5   → result = [4,5,1,2,3]

Copy back → nums = [4, 5, 1, 2, 3]  ✓
```

---

### Approach 3: Cyclic Replacements

**Input:** `nums = [1, 2, 3, 4, 5, 6]`, `k = 2`

```
n=6, k=2, gcd(6,2)=2 → there will be 2 independent cycles

start=0, count=0:
  current=0, prevValue=nums[0]=1
  → next=(0+2)%6=2, save nums[2]=3, place 1 at idx 2 → nums=[1,2,1,4,5,6], prev=3, current=2, count=1
  → next=(2+2)%6=4, save nums[4]=5, place 3 at idx 4 → nums=[1,2,1,4,3,6], prev=5, current=4, count=2
  → next=(4+2)%6=0, save nums[0]=1, place 5 at idx 0 → nums=[5,2,1,4,3,6], prev=1, current=0, count=3
  → current==start → stop cycle

start=1, count=3:
  current=1, prevValue=nums[1]=2
  → next=(1+2)%6=3, save nums[3]=4, place 2 at idx 3 → nums=[5,2,1,2,3,6], prev=4, current=3, count=4
  → next=(3+2)%6=5, save nums[5]=6, place 4 at idx 5 → nums=[5,2,1,2,3,4], prev=6, current=5, count=5
  → next=(5+2)%6=1, save nums[1]=2, place 6 at idx 1 → nums=[5,6,1,2,3,4], prev=2, current=1, count=6
  → current==start → stop cycle

count=6=n → done

Final: [5, 6, 1, 2, 3, 4]  ✓
```

---

### Approach 4: Three Reverses

**Input:** `nums = [1, 2, 3, 4, 5, 6, 7]`, `k = 3`

```
n=7, k=3%7=3

Step 1 — Reverse entire array [0..6]:
  Swap idx 0↔6: [7, 2, 3, 4, 5, 6, 1]
  Swap idx 1↔5: [7, 6, 3, 4, 5, 2, 1]
  Swap idx 2↔4: [7, 6, 5, 4, 3, 2, 1]
  → [7, 6, 5, 4, 3, 2, 1]

Step 2 — Reverse first k=3 elements [0..2]:
  Swap idx 0↔2: [5, 6, 7, 4, 3, 2, 1]
  → [5, 6, 7, 4, 3, 2, 1]

Step 3 — Reverse last n-k=4 elements [3..6]:
  Swap idx 3↔6: [5, 6, 7, 1, 3, 2, 4]
  Swap idx 4↔5: [5, 6, 7, 1, 2, 3, 4]
  → [5, 6, 7, 1, 2, 3, 4]  ✓
```

---

**Second example with k > n:**

**Input:** `nums = [1, 2, 3]`, `k = 7`

```
k = 7 % 3 = 1  ← effective rotation is just 1

Step 1 — Reverse [0..2]: [3, 2, 1]
Step 2 — Reverse [0..0]: [3, 2, 1]  (single element, no change)
Step 3 — Reverse [1..2]: [3, 1, 2]

Final: [3, 1, 2]  ✓ (last element moved to front)
```

---

## 7. Edge Cases

| Edge Case | Example | What Happens |
|-----------|---------|--------------|
| `k = 0` | `[1,2,3], k=0` | `k % n = 0`, early return — array unchanged |
| `k = n` | `[1,2,3], k=3` | `k % n = 0`, same as above — full rotation = no change |
| `k > n` | `[1,2,3], k=7` | `k = 7 % 3 = 1`, only 1 effective step |
| Single element | `[5], k=100` | `k % 1 = 0`, nothing changes |
| Two elements | `[1,2], k=1` | Swap: `[2,1]` — all approaches handle naturally |
| All same elements | `[3,3,3], k=2` | Result looks identical — all approaches still correct |
| Large values | `[-2^31, 2^31-1]` | No arithmetic on values (just moves) — no overflow risk |
| k exactly n/2 | `[1,2,3,4], k=2` | Two equal halves swap — `[3,4,1,2]` |

### Approach-Specific Edge Case Handling

- **Approach 1:** Handles all cleanly. `System.arraycopy` is safe.
- **Approach 2:** After `k = k % n`, `k=0` means the loop simply doesn't execute. Safe.
- **Approach 3:** Must check `k == 0` after normalization to avoid infinite `do-while` loop where `start == current` immediately. ✅ (handled in code above)
- **Approach 4:** `k == 0` short-circuit prevents calling `reverse(nums, 0, -1)` which would be an invalid range. ✅ (handled in code above)

---

## 8. Final Summary

### Comparison Table

| Approach | Time | Space | Interview Suitability |
|----------|------|-------|-----------------------|
| Extra Array | O(n) | O(n) | ✅ Good first answer |
| One Step at a Time | O(n×k) | O(1) | ❌ Too slow |
| Cyclic Replacements | O(n) | O(1) | ⚠️ Hard to get right |
| **Three Reverses** | **O(n)** | **O(1)** | ✅✅ **Best choice** |

### Recommendation
**Always go with the Three Reverses approach.** It's O(n) time and O(1) space, is easy to explain clearly, and is hard to get wrong once you understand the pattern. In an interview, mention the Extra Array solution first to show you understand the problem, then optimize to the reversal approach.

### What to Remember
> **Pattern:** "Reverse the whole, then reverse each part" is a powerful in-place rearrangement technique that appears in rotation problems, string manipulation, and more. Memorize: normalize `k` with `k % n`, reverse all, reverse `[0..k-1]`, reverse `[k..n-1]`.
    */

}
