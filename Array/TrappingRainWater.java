package Array;

import java.util.ArrayDeque;
import java.util.Deque;

public class TrappingRainWater {
    public static void main(String[] args) {
        TrappingRainWater trappingRainWater = new TrappingRainWater();
        System.out.println("TrappingRainWater : "
                + trappingRainWater.trapTwoPointers(new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 }));
        System.out.println("-------------------------------");
        System.out.println("TrappingRainWater : "
                + trappingRainWater.trapMonotonicStack(new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 }));
        System.out.println("-------------------------------");
        System.out.println("TrappingRainWater : "
                + trappingRainWater.trapDPPrefixSuffix(new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 }));
        System.out.println("-------------------------------");
        System.out.println("TrappingRainWater : "
                + trappingRainWater.trapBruteForce(new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 }));
    }

    // @formatter:off
    /**
     * 
     * https://leetcode.com/problems/trapping-rain-water/description/
     * 
     * Given n non-negative integers representing an elevation map where the width
     * of each bar is 1, compute how much water it can trap after raining.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
     * Output: 6
     * Explanation: The above elevation map (black section) is represented by array
     * [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section)
     * are being trapped.
     * Example 2:
     * 
     * Input: height = [4,2,0,3,2,5]
     * Output: 9
     * 
     * 
     * Constraints:
     * 
     * n == height.length
     * 1 <= n <= 2 * 104
     * 0 <= height[i] <= 105
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach            | Time   | Space | Code Complexity | Recommended?                  |
     * |---------------------|--------|-------|-----------------|----------------------------   |
     * | Two Pointers        | O(n)   | O(1)  | Moderate        | ✅✅ best overall             |
     * 
     * @param height
     * @return
     */
    // @formatter:on
    public int trapTwoPointers(int[] height) {
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0, totalWater = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    totalWater += leftMax - height[left];
                }
                left++;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    totalWater += rightMax - height[right];
                }
                right--;
            }
        }
        return totalWater;
    }

    // @formatter:off
    /**
     * 
     * | Approach            | Time   | Space | Code Complexity | Recommended?                  |
     * |---------------------|--------|-------|-----------------|----------------------------   |
     * | Monotonic Stack     | O(n)   | O(n)  | Moderate-hard   | ✅ if stack is being tested   |
     * 
     * @param height
     * @return
     */
    // @formatter:on
    public int trapMonotonicStack(int[] height) {
        Deque<Integer> stack = new ArrayDeque<>();
        int totalWater = 0;
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int floor = stack.pop();
                if (stack.isEmpty())
                    break;
                int leftIndex = stack.peek();
                int width = i - leftIndex - 1;
                int boundedHeight = Math.min(height[i], height[leftIndex]) - height[floor];
                totalWater += width * boundedHeight;
            }
            stack.push(i);
        }
        return totalWater;
    }

    // @formatter:off
    /**
     * 
     * | Approach            | Time   | Space | Code Complexity | Recommended?                  |
     * |---------------------|--------|-------|-----------------|----------------------------   |
     * | DP (Prefix/Suffix)  | O(n)   | O(n)  | Simple/readable | ✅ great for clarity          |
     * 
     * @param height
     * @return
     */
    // @formatter:on
    public int trapDPPrefixSuffix(int[] height) {
        int n = height.length;
        if (n == 0)
            return 0;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        int totalWater = 0;
        for (int i = 0; i < n; i++) {
            totalWater += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return totalWater;
    }

    // @formatter:off
    /**
     * 
     * | Approach            | Time   | Space | Code Complexity | Recommended?                  |
     * |---------------------|--------|-------|-----------------|----------------------------   |
     * | Brute Force         | O(n^2) | O(1)  | Very simple     | ❌ TLE; teaching only         |
     * 
     * @param height
     * @return
     */
    // @formatter:on
    public int trapBruteForce(int[] height) {
        int n = height.length;
        int totalWater = 0;
        for (int i = 0; i < n; i++) {
            int leftMax = 0;
            for (int l = 0; l <= i; l++) {
                leftMax = Math.max(leftMax, height[l]);
            }
            int rightMax = 0;
            for (int r = i; r < n; r++) {
                rightMax = Math.max(rightMax, height[r]);
            }
            totalWater += Math.min(leftMax, rightMax) - height[i];
        }
        return totalWater;
    }
}

// @formatter:off
/*
 * ============================================================
 * TRAPPING RAIN WATER — DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are given a set of vertical bars packed side by side, each with a width of
 * 1 unit but varying heights. When it rains, water pools in the valleys between
 * taller bars. The task is to compute the TOTAL VOLUME of water trapped after
 * raining. This is LeetCode #42 (Hard).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * A single integer array int[] height where height[i] is the height of the bar
 * at index i.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * A single int — the total units of trapped water.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * n == height.length
 * 1 <= n <= 2 * 10^4
 * 0 <= height[i] <= 10^5
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * For each position i, the water sitting on top of that bar equals
 * min(maxLeft, maxRight) - height[i], where maxLeft is the tallest bar at or to
 * the left of i and maxRight is the tallest bar at or to the right of i. Water is
 * only added when this value is positive. The answer is the sum over all
 * positions.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  height = [0,1,0,2,1,0,1,3,2,1,2,1]
 *    Output: 6
 * The water fills the dips between the peaks (the 2 and 3 towers), totalling 6.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * Water on top of any single bar is bounded by the SHORTER of the two tallest
 * walls surrounding it — one on its left, one on its right. Think of a bathtub:
 * the water level can never rise above the lowest rim, or it would spill over.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Pick a position i.
 * 2. Look left and find the highest wall — call it L.
 * 3. Look right and find the highest wall — call it R.
 * 4. The water above bar i can rise only to min(L, R).
 * 5. Subtract the bar's own height: trapped water = min(L, R) - height[i] (>= 0).
 * 6. Add up every position.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                        | Why it's tricky                            |
 * |----------------------------------|--------------------------------------------|
 * | Water depends on BOTH sides      | Can't decide a cell by one side alone.     |
 * | Naive recomputation is wasteful  | Re-scanning both sides is O(n^2), too slow.|
 * | Getting to O(1) space is subtle  | Two-pointer relies on a non-obvious invariant. |
 * | The "min of two maxes" insight   | A single global max gives wrong answers.   |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach        | Key Idea                                   | Best Used When            | Time   | Space        |
 * |---|-----------------|--------------------------------------------|---------------------------|--------|--------------|
 * | 1 | Brute Force     | For each index rescan left-max & right-max | Teaching; tiny inputs     | O(n^2) | O(1) *space-tied*  |
 * | 2 | DP Prefix/Suffix| Precompute leftMax[] and rightMax[]        | You want clear O(n) time  | O(n)   | O(n)         |
 * | 3 | Two Pointers    | Converge from ends, advance shorter wall   | The general optimal case  | O(n) ✅ time | O(1) ✅ space |
 * | 4 | Monotonic Stack | Fill water layer-by-layer between bars     | Interviewer wants a stack | O(n)   | O(n)         |
 *
 * All four share the same core insight (min(leftMax, rightMax) - height). Brute
 * force spends O(1) space but pays O(n^2) time. DP trades O(n) space to cache the
 * maxes and drops time to O(n). The stack also achieves O(n) time but computes
 * water horizontally, using O(n) stack space. The two-pointer method is the clear
 * overall winner — O(n) time AND O(1) space simultaneously, dominating both axes.
 * Prefer two pointers universally; use the stack only if an interviewer wants
 * basin-filling, and DP when you want the most readable O(n) solution.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * 1. Initialize totalWater = 0.
 * 2. For each index i: scan 0..i for leftMax, scan i..n-1 for rightMax.
 * 3. Add min(leftMax, rightMax) - height[i] (always >= 0).
 * 4. Return the total.
 *
 *    public class TrappingRainWaterBruteForce {
 *        public int trap(int[] height) {
 *            int n = height.length;
 *            int totalWater = 0;
 *            for (int i = 0; i < n; i++) {
 *                int leftMax = 0;
 *                for (int l = 0; l <= i; l++) {
 *                    leftMax = Math.max(leftMax, height[l]);
 *                }
 *                int rightMax = 0;
 *                for (int r = i; r < n; r++) {
 *                    rightMax = Math.max(rightMax, height[r]);
 *                }
 *                totalWater += Math.min(leftMax, rightMax) - height[i];
 *            }
 *            return totalWater;
 *        }
 *        public static void main(String[] args) {
 *            TrappingRainWaterBruteForce solver = new TrappingRainWaterBruteForce();
 *            int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
 *            System.out.println(solver.trap(height)); // Expected: 6
 *        }
 *    }
 *
 * Because both maxes include index i, the expression can never be negative, so no
 * explicit clamp is needed.
 *
 * ------------------------------------------------------------
 * Approach 2: Dynamic Programming (Prefix/Suffix Max)
 * ------------------------------------------------------------
 * 1. Build leftMax[i] via a left-to-right pass.
 * 2. Build rightMax[i] via a right-to-left pass.
 * 3. For each i, add min(leftMax[i], rightMax[i]) - height[i].
 * 4. Return the sum.
 *
 *    public class TrappingRainWaterDP {
 *        public int trap(int[] height) {
 *            int n = height.length;
 *            if (n == 0) return 0;
 *            int[] leftMax = new int[n];
 *            int[] rightMax = new int[n];
 *            leftMax[0] = height[0];
 *            for (int i = 1; i < n; i++) {
 *                leftMax[i] = Math.max(leftMax[i - 1], height[i]);
 *            }
 *            rightMax[n - 1] = height[n - 1];
 *            for (int i = n - 2; i >= 0; i--) {
 *                rightMax[i] = Math.max(rightMax[i + 1], height[i]);
 *            }
 *            int totalWater = 0;
 *            for (int i = 0; i < n; i++) {
 *                totalWater += Math.min(leftMax[i], rightMax[i]) - height[i];
 *            }
 *            return totalWater;
 *        }
 *        public static void main(String[] args) {
 *            TrappingRainWaterDP solver = new TrappingRainWaterDP();
 *            int[] height = {4, 2, 0, 3, 2, 5};
 *            System.out.println(solver.trap(height)); // Expected: 9
 *        }
 *    }
 *
 * The two precomputed arrays turn each per-index lookup into O(1).
 *
 * ------------------------------------------------------------
 * Approach 3: Two Pointers ✅ (Optimal)
 * ------------------------------------------------------------
 * 1. left=0, right=n-1; leftMax=rightMax=0.
 * 2. While left < right:
 *    - If height[left] < height[right], left side limits: update leftMax; add
 *      leftMax - height[left] if lower; move left right.
 *    - Else right side limits (symmetric); move right left.
 * 3. Return accumulated water.
 * Why it works: when height[left] < height[right], the right side guarantees a
 * wall taller than height[left], so leftMax alone is the true bound at left.
 *
 *    public class TrappingRainWaterTwoPointers {
 *        public int trap(int[] height) {
 *            int left = 0, right = height.length - 1;
 *            int leftMax = 0, rightMax = 0;
 *            int totalWater = 0;
 *            while (left < right) {
 *                if (height[left] < height[right]) {
 *                    if (height[left] >= leftMax) {
 *                        leftMax = height[left];
 *                    } else {
 *                        totalWater += leftMax - height[left];
 *                    }
 *                    left++;
 *                } else {
 *                    if (height[right] >= rightMax) {
 *                        rightMax = height[right];
 *                    } else {
 *                        totalWater += rightMax - height[right];
 *                    }
 *                    right--;
 *                }
 *            }
 *            return totalWater;
 *        }
 *        public static void main(String[] args) {
 *            TrappingRainWaterTwoPointers solver = new TrappingRainWaterTwoPointers();
 *            int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
 *            System.out.println(solver.trap(height)); // Expected: 6
 *        }
 *    }
 *
 * The invariant: the pointer on the shorter side is always safe to resolve.
 *
 * ------------------------------------------------------------
 * Approach 4: Monotonic Stack
 * ------------------------------------------------------------
 * 1. Keep a stack of indices with non-increasing heights.
 * 2. For each bar i: while stack non-empty and height[i] > height[top]:
 *    - Pop top (the floor). If stack now empty, stop (no left wall).
 *    - width = i - stack.top - 1; boundedHeight = min(height[i], height[top]) - height[floor].
 *    - Add width * boundedHeight.
 * 3. Push i. Return total.
 *
 *    import java.util.Deque;
 *    import java.util.ArrayDeque;
 *    public class TrappingRainWaterStack {
 *        public int trap(int[] height) {
 *            Deque<Integer> stack = new ArrayDeque<>();
 *            int totalWater = 0;
 *            for (int i = 0; i < height.length; i++) {
 *                while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
 *                    int floor = stack.pop();
 *                    if (stack.isEmpty()) break;
 *                    int leftIndex = stack.peek();
 *                    int width = i - leftIndex - 1;
 *                    int boundedHeight = Math.min(height[i], height[leftIndex]) - height[floor];
 *                    totalWater += width * boundedHeight;
 *                }
 *                stack.push(i);
 *            }
 *            return totalWater;
 *        }
 *        public static void main(String[] args) {
 *            TrappingRainWaterStack solver = new TrappingRainWaterStack();
 *            int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
 *            System.out.println(solver.trap(height)); // Expected: 6
 *        }
 *    }
 *
 * The stack computes water in horizontal layers rather than per-column.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Time:  O(n^2). Outer loop n times; each does ~n inner work. n=20000 -> ~4x10^8.
 * Space: O(1). Only scalar accumulators.
 * Example: n=12 -> roughly 144 comparisons.
 *
 * ------------------------------------------------------------
 * Approach 2: DP (Prefix/Suffix Max)
 * ------------------------------------------------------------
 * Time:  O(n). Three linear passes = 3n.
 * Space: O(n). Two auxiliary arrays.
 * Example: n=12 -> ~36 iterations + 24 array slots.
 *
 * ------------------------------------------------------------
 * Approach 3: Two Pointers ✅
 * ------------------------------------------------------------
 * Time:  O(n). Each index visited once as pointers converge.
 * Space: O(1). Four scalars.
 * Example: n=12 -> exactly 11 iterations, no extra memory.
 *
 * ------------------------------------------------------------
 * Approach 4: Monotonic Stack
 * ------------------------------------------------------------
 * Time:  O(n). Each index pushed once, popped at most once (amortized).
 * Space: O(n). Worst case (decreasing bars) stack holds all n indices.
 * Example: n=12 -> up to 24 stack operations.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1 — Brute Force on [0,1,0,2,1,0,1,3,2,1,2,1]
 * ------------------------------------------------------------
 * | i  | height | leftMax | rightMax | min-height | water |
 * |----|--------|---------|----------|------------|-------|
 * | 0  | 0      | 0       | 3        | 0          | 0     |
 * | 1  | 1      | 1       | 3        | 0          | 0     |
 * | 2  | 0      | 1       | 3        | 1          | 1     |
 * | 3  | 2      | 2       | 3        | 0          | 0     |
 * | 4  | 1      | 2       | 3        | 1          | 1     |
 * | 5  | 0      | 2       | 3        | 2          | 2     |
 * | 6  | 1      | 2       | 3        | 1          | 1     |
 * | 7  | 3      | 3       | 3        | 0          | 0     |
 * | 8  | 2      | 3       | 2        | 0          | 0     |
 * | 9  | 1      | 3       | 2        | 1          | 1     |
 * | 10 | 2      | 3       | 2        | 0          | 0     |
 * | 11 | 1      | 3       | 1        | 0          | 0     |
 * Total = 1+1+2+1+1 = 6 ✅
 *
 * ------------------------------------------------------------
 * Approach 2 — DP on [4,2,0,3,2,5]
 * ------------------------------------------------------------
 *    leftMax  = [4, 4, 4, 4, 4, 5]
 *    rightMax = [5, 5, 5, 5, 5, 5]
 *    min      = [4, 4, 4, 4, 4, 5]
 *    water    = [0, 2, 4, 1, 2, 0]  ->  sum = 9 ✅
 *
 * ------------------------------------------------------------
 * Approach 3 — Two Pointers on [0,1,0,2,1,0,1,3,2,1,2,1]
 * ------------------------------------------------------------
 *    L=0  R=11 lm=0 rm=0  h[L]=0<h[R]=1 -> lm=0, L->1         water=0
 *    L=1  R=11 lm=1 rm=0  equal -> right: rm=1, R->10          water=0
 *    L=1  R=10 lm=1 rm=1  h[L]=1<h[R]=2 -> lm=1, L->2          water=0
 *    L=2  R=10 lm=1 rm=1  h[L]=0<h[R]=2 -> +1 (lm-0), L->3     water=1
 *    L=3  R=10 lm=1 rm=1  equal -> right: rm=2, R->9           water=1
 *    L=3  R=9  lm=1 rm=2  h[L]=2>h[R]=1 -> right:+1 (rm-1),R->8 water=2
 *    L=3  R=8  lm=1 rm=2  equal -> right: rm=2, R->7           water=2
 *    L=3  R=7  lm=1 rm=2  h[L]=2<h[R]=3 -> lm=2, L->4          water=2
 *    L=4  R=7  lm=2 rm=2  h[L]=1<h[R]=3 -> +1 (lm-1), L->5     water=3
 *    L=5  R=7  lm=2 rm=2  h[L]=0<h[R]=3 -> +2 (lm-0), L->6     water=5
 *    L=6  R=7  lm=2 rm=2  h[L]=1<h[R]=3 -> +1 (lm-1), L->7     water=6
 *    L=7  R=7  -> loop ends
 * Total = 6 ✅
 *
 * ------------------------------------------------------------
 * Approach 4 — Monotonic Stack on [0,1,0,2,1,0,1,3,2,1,2,1]
 * ------------------------------------------------------------
 *    i=1 (h=1): pop 0 (h=0), stack empty -> no water. push 1.
 *    i=3 (h=2): pop 2 (h=0), top=1 -> width=1, bh=min(2,1)-0=1 -> +1
 *               pop 1 (h=1), stack empty -> stop. push 3.        water=1
 *    i=6 (h=1): pop 5 (h=0), top=4 -> width=1, bh=min(1,1)-0=1 -> +1
 *               push 6.                                          water=2
 *    i=7 (h=3): pop 6 (h=1), top=4 -> width=2, bh=min(3,1)-1=0 -> +0
 *               pop 4 (h=1), top=3 -> width=3, bh=min(3,2)-1=1 -> +3
 *               pop 3 (h=2), stack empty -> stop. push 7.        water=5
 *    i=10(h=2): pop 9 (h=1), top=8 -> width=1, bh=min(2,2)-1=1 -> +1
 *               push 10.                                         water=6
 *    i=11(h=1): 1 < 2, no pop. push 11.
 * Total = 6 ✅
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case                  | Input          | Expected | How Handled                         |
 * |----------------------------|----------------|----------|-------------------------------------|
 * | Single bar                 | [5]            | 0        | No left+right walls -> 0.           |
 * | Two bars                   | [3,4]          | 0        | No interior valley.                 |
 * | Monotonically increasing   | [1,2,3,4]      | 0        | Each bar is its own leftMax.        |
 * | Monotonically decreasing   | [4,3,2,1]      | 0        | No left wall taller than right.     |
 * | Flat terrain               | [2,2,2,2]      | 0        | min(lm,rm) == height everywhere.    |
 * | All zeros                  | [0,0,0]        | 0        | Water bound is 0 everywhere.        |
 * | Classic valley             | [3,0,3]        | 3        | Middle traps min(3,3)-0 = 3.        |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 — Single global max instead of two-sided max:
 *    // WRONG
 *    int max = 0;
 *    for (int i = 0; i < n; i++) { max = Math.max(max, height[i]); water += max - height[i]; }
 *    // CORRECT
 *    water += Math.min(leftMax, rightMax) - height[i];
 *
 * Pitfall 2 — Stack: forgetting empty-stack check after popping:
 *    // WRONG
 *    int floor = stack.pop();
 *    int leftIndex = stack.peek(); // may be empty!
 *    // CORRECT
 *    int floor = stack.pop();
 *    if (stack.isEmpty()) break;
 *    int leftIndex = stack.peek();
 *
 * Pitfall 3 — Two pointers: advancing the TALLER side breaks the invariant.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: Empty arrays (n==0) and single/two-element arrays. The two-pointer loop
 *    returns 0 naturally; the DP version guards n==0 to avoid indexing height[0].
 *
 * Q: Are there any type mismatches?
 * A: With n <= 2x10^4 and height[i] <= 10^5, theoretical max water ~2x10^9 sits
 *    near Integer.MAX_VALUE but never occurs in practice (can't be max-wide and
 *    max-deep at once). LeetCode fits in int; for defensive code use long.
 *
 * Q: How can I verify this works right now?
 *    public static void verify() {
 *        TrappingRainWaterTwoPointers s = new TrappingRainWaterTwoPointers();
 *        assert s.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}) == 6;
 *        assert s.trap(new int[]{4,2,0,3,2,5}) == 9;
 *        assert s.trap(new int[]{3,0,3}) == 3;
 *        assert s.trap(new int[]{1,2,3,4}) == 0;
 *        assert s.trap(new int[]{5}) == 0;
 *        assert s.trap(new int[]{}) == 0;
 *        System.out.println("All tests passed!");
 *    }
 *    // Run with: java -ea (assertions enabled)
 *
 * | Approach     | Risk                          | Mitigation                       |
 * |--------------|-------------------------------|----------------------------------|
 * | Brute Force  | O(n^2) TLE on large inputs     | Use only for validation.         |
 * | DP           | O(n) extra memory             | Acceptable; or two pointers.     |
 * | Two Pointers | Off-by-one / wrong side moved | Unit-test asymmetric inputs.     |
 * | Stack        | Empty-stack access after pop  | Always if (stack.isEmpty()) break.|
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * Problem: LeetCode #42 · Difficulty: Hard · thousands of interview appearances.
 *
 * | Company       | Frequency | Notes                                    |
 * |---------------|-----------|------------------------------------------|
 * | Amazon        | ⭐⭐⭐⭐⭐    | Extremely common phone + onsite.         |
 * | Google        | ⭐⭐⭐⭐⭐    | Often with O(1)-space follow-ups.        |
 * | Microsoft     | ⭐⭐⭐⭐     | Expects two-pointer optimization.        |
 * | Meta          | ⭐⭐⭐⭐     | Asked in the "container" family.         |
 * | Apple         | ⭐⭐⭐      | Array-heavy rounds.                      |
 * | Bloomberg     | ⭐⭐⭐⭐     | Popular for stack follow-up.             |
 * | Goldman Sachs | ⭐⭐⭐      | Tests optimization progression.          |
 * | Adobe         | ⭐⭐⭐      | Mid-frequency array question.            |
 * | Uber          | ⭐⭐⭐      | Sometimes with the 2D variant.           |
 * | Nvidia        | ⭐⭐       | Less frequent but reported.              |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach            | Time   | Space | Code Complexity | Recommended?               |
 * |---------------------|--------|-------|-----------------|----------------------------|
 * | Brute Force         | O(n^2) | O(1)  | Very simple     | ❌ TLE; teaching only       |
 * | DP (Prefix/Suffix)  | O(n)   | O(n)  | Simple/readable | ✅ great for clarity        |
 * | Two Pointers        | O(n)   | O(1)  | Moderate        | ✅✅ best overall            |
 * | Monotonic Stack     | O(n)   | O(n)  | Moderate-hard   | ✅ if stack is being tested |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use Two Pointers — it uniquely achieves O(n) time AND O(1) space at once, so
 * there is no time-vs-space trade-off. Fall back to DP for readability, and the
 * stack only when an interviewer explicitly wants basin-filling logic.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * The whole problem collapses to one formula: water above bar i =
 * min(leftMax, rightMax) - height[i]. The two-pointer optimization works because
 * the pointer on the SHORTER wall is always safe to resolve — the taller opposite
 * side guarantees the bound. Memorize "advance the shorter side" as the trigger
 * for the O(1)-space solution.
 */
// @formatter:on
