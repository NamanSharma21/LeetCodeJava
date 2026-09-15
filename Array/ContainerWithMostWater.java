package Array;

public class ContainerWithMostWater {
    public static void main(String[] args) {
        ContainerWithMostWater containerWithMostWater = new ContainerWithMostWater();
        System.out.println(
                "ContainerWithMostWater : "
                        + containerWithMostWater.maxAreaTwoPointers(new int[] { 1, 8, 6, 2, 5, 4, 8, 3, 7 }));
        System.out.println("-------------------------------------");
        System.out.println(
                "ContainerWithMostWater : "
                        + containerWithMostWater.maxAreaBruteForce(new int[] { 1, 8, 6, 2, 5, 4, 8, 3, 7 }));
    }

    // @formatter:off
    /**
     * 
     * 
     * https://leetcode.com/problems/container-with-most-water/description/?envType=problem-list-v2&envId=array
     * 
     * 
     * You are given an integer array height of length n. There are n vertical lines
     * drawn such that the two endpoints of the ith line are (i, 0) and (i,
     * height[i]).
     * 
     * Find two lines that together with the x-axis form a container, such that the
     * container contains the most water.
     * 
     * Return the maximum amount of water a container can store.
     * 
     * Notice that you may not slant the container.
     * 
     * 
     * 
     * Example 1:
     * 
     * 
     * Input: height = [1,8,6,2,5,4,8,3,7]
     * Output: 49
     * Explanation: The above vertical lines are represented by array
     * [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the
     * container can contain is 49.
     * Example 2:
     * 
     * Input: height = [1,1]
     * Output: 1
     * 
     * 
     * Constraints:
     * 
     * n == height.length
     * 2 <= n <= 105
     * 0 <= height[i] <= 104
     */
    // @formatter:on

    // @formatter:off
    /**
     * | Approach                | Time     | Space     | Code Complexity   | Recommended?                                                          |
     * |-------------------------|----------|-----------|-------------------|-----------------------------------------------------------------------|
     * | Two Pointers (converge) | O(n)     | O(1)      | Simple            | [OK][OK] Best on both axes - use this                                 |
     * 
     * @param height
     * @return
     */
    // @formatter:on
    public int maxAreaTwoPointers(int[] height) {
        int area = 0, start = 0, end = height.length - 1;
        while (start < end) {
            area = Math.max(area, (end - start) * Math.min(height[start], height[end]));
            if (height[start] < height[end]) {
                start++;
            } else {
                end--;
            }
        }
        return area;
    }

    // @formatter:off
    /**
     * | Approach                | Time     | Space     | Code Complexity   | Recommended?                                                          |
     * |-------------------------|----------|-----------|-------------------|-----------------------------------------------------------------------|
     * | Brute Force (all pairs) | O(n^2)   | O(1)      | Very simple       | [X] Not for production - times out; keep as a correctness oracle only |
     * 
     * @param height
     * @return
     */
    // @formatter:on
    public int maxAreaBruteForce(int[] height) {
        int n = height.length, maxArea = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int area = Math.min(height[i], height[j]) * (j - 1);
                maxArea = Math.max(maxArea, area);
            }
        }
        return maxArea;
    }
}
// @formatter:off
/*
 * ============================================================
 * CONTAINER WITH MOST WATER - DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are given a row of vertical lines standing on a number line. Each line has
 * some height. If you pick any two of these lines, they form the two sides of a
 * container, and water can be held between them. The amount of water is limited
 * by the SHORTER of the two lines (water would spill over the shorter side) and by
 * the HORIZONTAL DISTANCE between them. Find the pair of lines that traps the MOST
 * water.
 *
 * This is LeetCode #11 - "Container With Most Water" (Medium).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * - A single integer array int[] height, where height[i] is the height of the line
 *   at position i.
 * - The x-coordinate of each line is simply its index i.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * - A single int: the maximum water area any pair of lines can contain.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 * - 2 <= height.length <= 10^5
 * - 0 <= height[i] <= 10^4
 * - There are always at least two lines, so a container always exists.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * For two indices i < j, the water area is:
 *    area(i, j) = min(height[i], height[j]) * (j - i)
 * Return max of area(i, j) over ALL pairs (i, j).
 * Lines have no thickness, and we do NOT sum multiple sub-containers - it is
 * exactly one pair forming one rectangular container.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
 * Best pair is index 1 (height 8) and index 8 (height 7):
 *    area = min(8, 7) * (8 - 1) = 7 * 7 = 49
 * Output: 49.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * The area is a rectangle: width x height. Width is how far apart the two lines
 * are; height is capped by the shorter line. The widest container uses the two
 * outermost lines, but those might be short. Moving inward gives taller candidates
 * but shrinks width. The trick is knowing WHICH direction to move to trade width
 * for height profitably.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 * 1. Start with a pointer at the far left and far right - maximum width.
 * 2. Compute the area; height is limited by whichever line is shorter.
 * 3. To give up width, move one pointer inward. Move the SHORTER one - it is the
 *    bottleneck, so keeping it while shrinking width can only make things worse or
 *    equal. The taller line still has potential.
 * 4. Repeat, tracking the best area, until the pointers meet.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge | Why it's tricky |
 * |-----------|-----------------|
 * | Two competing variables (width and height) | Maximizing one hurts the other, so "pick the tallest lines" fails. |
 * | Knowing which pointer to move | Feels arbitrary, but moving the taller pointer is provably useless. |
 * | Proving the greedy move never skips the answer | Not obvious that discarding the shorter line loses no better container. |
 * | Height is min of the two, not average or sum | Beginners add or average heights, computing the wrong area. |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach | Key Idea | Best Used When | Time | Space |
 * |---|----------|----------|----------------|------|-------|
 * | 1 | Brute Force (all pairs) | Test every pair (i, j), track max area | n is tiny, or want an obvious baseline | O(n^2) | O(1) [space-optimal, tied] |
 * | 2 | Two Pointers (converge inward) | Start at both ends; always move the shorter line inward | General case - intended solution | O(n) [time-optimal] | O(1) [space-optimal, tied] |
 *
 * Both approaches use O(1) auxiliary space, so space is not a differentiator - the
 * only meaningful axis is time. Brute force checks ~n^2/2 pairs; the two-pointer
 * method visits each index at most once by exploiting the greedy insight that the
 * shorter line can be safely discarded. The two-pointer approach dominates: it wins
 * decisively on time (O(n) vs O(n^2)) while tying on space. There is no trade-off -
 * prefer the two-pointer method in essentially all cases. Brute force is worth
 * understanding only as a correctness reference for small inputs.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (All Pairs)
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Initialize maxArea = 0.
 * 2. For each left index i from 0 to n-1:
 *      For each right index j from i+1 to n-1:
 *        width = j - i
 *        containerHeight = min(height[i], height[j])
 *        area = width * containerHeight
 *        maxArea = max(maxArea, area)
 * 3. Return maxArea.
 *
 *    public class ContainerBruteForce {
 *        public int maxArea(int[] height) {
 *            int maxArea = 0;
 *            int n = height.length;
 *            for (int left = 0; left < n; left++) {
 *                for (int right = left + 1; right < n; right++) {
 *                    int width = right - left;
 *                    int containerHeight = Math.min(height[left], height[right]);
 *                    int area = width * containerHeight;
 *                    maxArea = Math.max(maxArea, area);
 *                }
 *            }
 *            return maxArea;
 *        }
 *        public static void main(String[] args) {
 *            ContainerBruteForce solver = new ContainerBruteForce();
 *            int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
 *            System.out.println(solver.maxArea(height)); // Expected: 49
 *        }
 *    }
 *
 * No non-obvious formulas - it directly enumerates area(i, j) for every pair.
 *
 * ------------------------------------------------------------
 * Approach 2: Two Pointers (Converge Inward)  [OPTIMAL]
 * ------------------------------------------------------------
 * Algorithm:
 * 1. Set left = 0 and right = n - 1 (the two ends - maximum width).
 * 2. Initialize maxArea = 0.
 * 3. While left < right:
 *      width = right - left
 *      containerHeight = min(height[left], height[right])
 *      maxArea = max(maxArea, width * containerHeight)
 *      Move the pointer at the SHORTER line inward:
 *        if height[left] < height[right] -> left++
 *        else -> right--
 * 4. Return maxArea.
 *
 *    public class ContainerTwoPointers {
 *        public int maxArea(int[] height) {
 *            int left = 0;
 *            int right = height.length - 1;
 *            int maxArea = 0;
 *            while (left < right) {
 *                int width = right - left;
 *                int containerHeight = Math.min(height[left], height[right]);
 *                maxArea = Math.max(maxArea, width * containerHeight);
 *                // Discard the shorter line; it can never pair better than now.
 *                if (height[left] < height[right]) {
 *                    left++;
 *                } else {
 *                    right--;
 *                }
 *            }
 *            return maxArea;
 *        }
 *        public static void main(String[] args) {
 *            ContainerTwoPointers solver = new ContainerTwoPointers();
 *            int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
 *            System.out.println(solver.maxArea(height)); // Expected: 49
 *        }
 *    }
 *
 * Why moving the shorter pointer is correct (the key bound):
 * Suppose height[left] <= height[right]. Current area is
 * height[left] * (right - left). Any other container still using left must have a
 * SMALLER width (every remaining partner j has j < right, so j - left < right -
 * left), and its height is still capped at min(height[left], height[j]) <=
 * height[left]. So no container using left can beat the one we just computed - left
 * is exhausted and safe to discard. Symmetric when the right line is shorter. This
 * guarantees we never skip the true maximum despite examining only n positions.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * - Time: O(n^2). Outer loop n times; inner loop up to n-1-i times. Sum =
 *   n(n-1)/2 pair evaluations = O(n^2). Each evaluation O(1).
 * - Space: O(1). A few int variables, no auxiliary structures.
 * - Concrete: n = 1000 -> ~499,500 ops. n = 100,000 -> ~5 x 10^9 ops - too slow,
 *   would time out.
 *
 * ------------------------------------------------------------
 * Approach 2: Two Pointers
 * ------------------------------------------------------------
 * - Time: O(n). Each iteration moves one pointer inward; pointers only travel
 *   toward each other, covering n-1 steps before meeting. Loop runs <= n-1 times,
 *   each O(1).
 * - Space: O(1). Only left, right, maxArea, and temporaries.
 * - Concrete: n = 1000 -> ~999 iterations. n = 100,000 -> ~99,999 iterations -
 *   roughly 50,000x fewer ops than brute force at the constraint maximum.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * Using height = [1, 8, 6, 2, 5, 4, 8, 3, 7] (indices 0-8).
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force (record-setting pairs)
 * ------------------------------------------------------------
 * | Pair (i, j) | width | min height | area | maxArea after |
 * |-------------|-------|-----------|------|---------------|
 * | (0, 1) | 1 | min(1,8)=1 | 1  | 1  |
 * | (0, 8) | 8 | min(1,7)=1 | 8  | 8  |
 * | (1, 2) | 1 | min(8,6)=6 | 6  | 8  |
 * | (1, 6) | 5 | min(8,8)=8 | 40 | 40 |
 * | (1, 8) | 7 | min(8,7)=7 | 49 | 49 |
 * | (2, 6) | 4 | min(6,8)=6 | 24 | 49 |
 * | (4, 6) | 2 | min(5,8)=5 | 10 | 49 |
 * No later pair exceeds 49. Final output: 49.
 *
 * ------------------------------------------------------------
 * Approach 2: Two Pointers (full trace)
 * ------------------------------------------------------------
 * Start: left=0 (h=1), right=8 (h=7), maxArea=0
 *
 * Step 1: width=8, min(1,7)=1, area=8   -> maxArea=8
 *         h[left]=1 < h[right]=7 -> move left -> left=1
 *         |- [left=1(h=8), right=8(h=7)]
 * Step 2: width=7, min(8,7)=7, area=49  -> maxArea=49
 *         h[left]=8 >= h[right]=7 -> move right -> right=7
 *         |- [left=1(h=8), right=7(h=3)]
 * Step 3: width=6, min(8,3)=3, area=18  -> maxArea=49
 *         move right -> right=6
 *         |- [left=1(h=8), right=6(h=8)]
 * Step 4: width=5, min(8,8)=8, area=40  -> maxArea=49
 *         move right -> right=5
 *         |- [left=1(h=8), right=5(h=4)]
 * Step 5: width=4, min(8,4)=4, area=16  -> maxArea=49
 *         move right -> right=4
 *         |- [left=1(h=8), right=4(h=5)]
 * Step 6: width=3, min(8,5)=5, area=15  -> maxArea=49
 *         move right -> right=3
 *         |- [left=1(h=8), right=3(h=2)]
 * Step 7: width=2, min(8,2)=2, area=4   -> maxArea=49
 *         move right -> right=2
 *         |- [left=1(h=8), right=2(h=6)]
 * Step 8: width=1, min(8,6)=6, area=6   -> maxArea=49
 *         move right -> right=1
 *         |_ left == right -> STOP
 * Final output: 49 - matching the brute force result.
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 * | Edge Case | Input | Expected Output | How Handled |
 * |-----------|-------|-----------------|-------------|
 * | Minimum size (two lines) | [3, 7] | min(3,7)*1 = 3 | Loop runs once; single pair evaluated. |
 * | All equal heights | [5, 5, 5, 5] | 5 * 3 = 15 | Widest pair wins; equal-height tie moves right. |
 * | Contains zero-height lines | [0, 2, 0] | 0 | Zero caps container height to 0; still correct. |
 * | Strictly increasing | [1, 2, 3, 4, 5] | 6 (pair (2,4): min(3,5)*2) | Left pointer advances through short rising side. |
 * | Strictly decreasing | [5, 4, 3, 2, 1] | 6 | Right pointer advances; symmetric. |
 * | Tall spike in the middle | [1, 1, 100, 1, 1] | min(1,1)*4 = 4 | Spike can't help; both partners short, width wins. |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 - Using the wrong height (sum/average instead of min):
 *    // WRONG: water spills over the shorter wall
 *    int area = (height[left] + height[right]) * width;
 *    // CORRECT
 *    int area = Math.min(height[left], height[right]) * width;
 *
 * Pitfall 2 - Moving the taller pointer (or always moving left):
 *    // WRONG: discarding the taller line can skip the real maximum
 *    if (height[left] > height[right]) left++; else right--;
 *    // CORRECT: always discard the shorter line
 *    if (height[left] < height[right]) left++; else right--;
 *
 * Pitfall 3 - Off-by-one width:
 *    // WRONG: lines have no thickness
 *    int width = right - left + 1;
 *    // CORRECT
 *    int width = right - left;
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 * Q: What edge cases might this miss?
 * A: The core algorithm handles all valid inputs, but watch inputs with zeros
 *    (they legitimately produce 0-height containers, not bugs), the two-element
 *    minimum (don't assume more than two lines), and the equal-height tie (the else
 *    branch must cover ==; using < for the left-move and else for everything else
 *    handles ties safely). The area never overflows int here: max is
 *    10^4 * (10^5 - 1) ~= 10^9, which fits in 32-bit int (limit ~2.1 x 10^9), so no
 *    long is required - but it's close enough to note.
 *
 * Q: Are there any type mismatches?
 * A: All values are int. Width <= 10^5, height <= 10^4, product <= ~10^9 <
 *    Integer.MAX_VALUE, so int is safe. If constraints were larger, promote the
 *    area computation to long.
 *
 * Q: How can I verify this works right now?
 *    public class Verify {
 *        public int maxArea(int[] height) {
 *            int left = 0, right = height.length - 1, maxArea = 0;
 *            while (left < right) {
 *                int width = right - left;
 *                int h = Math.min(height[left], height[right]);
 *                maxArea = Math.max(maxArea, width * h);
 *                if (height[left] < height[right]) left++; else right--;
 *            }
 *            return maxArea;
 *        }
 *        public void verify() {
 *            assert maxArea(new int[]{1,8,6,2,5,4,8,3,7}) == 49 : "main example";
 *            assert maxArea(new int[]{1,1}) == 1 : "two equal";
 *            assert maxArea(new int[]{4,3,2,1,4}) == 16 : "ends win";
 *            assert maxArea(new int[]{1,2,1}) == 2 : "small";
 *            assert maxArea(new int[]{2,3,4,5,18,17,6}) == 17 : "tall pair";
 *            assert maxArea(new int[]{0,2,0}) == 0 : "zeros";
 *            System.out.println("All assertions passed.");
 *        }
 *        public static void main(String[] args) {
 *            new Verify().verify(); // run with: java -ea Verify
 *        }
 *    }
 *
 * | Approach | Risk | Mitigation |
 * |----------|------|------------|
 * | Brute Force | Times out for large n (O(n^2)) | Use only for n <= ~2000 or as a correctness oracle. |
 * | Two Pointers | Wrong pointer-move logic silently returns too-small answer | Unit-test against brute force on random arrays; assert equality. |
 * | Both | Integer overflow if constraints grow | Use long for the area product when magnitudes grow. |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #11 - "Container With Most Water" - Difficulty: Medium.
 * One of the most frequently asked two-pointer problems (thousands of reported
 * appearances across major companies).
 *
 * | Company | Frequency (stars) | Notes |
 * |---------|-------------------|-------|
 * | Amazon | ***** | Extremely common phone-screen/onsite two-pointer warm-up. |
 * | Google | **** | Tests greedy-proof reasoning, not just coding. |
 * | Meta (Facebook) | ***** | High-frequency; often with a "why is the greedy move valid" follow-up. |
 * | Microsoft | **** | Regular in coding rounds. |
 * | Apple | *** | Appears in array/two-pointer sets. |
 * | Bloomberg | **** | Popular for its clean optimization narrative. |
 * | Adobe | *** | Shows up in medium-tier screens. |
 * | Uber | *** | Used as a two-pointer discriminator. |
 * | Goldman Sachs | ** | Occasional, usually early-round. |
 * | Oracle | ** | Appears in warm-up sets. |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 * | Approach | Time | Space | Code Complexity | Recommended? |
 * |----------|------|-------|-----------------|--------------|
 * | Brute Force (all pairs) | O(n^2) | O(1) | Very simple | [X] Not for production - times out; keep as a correctness oracle only |
 * | Two Pointers (converge) | O(n) | O(1) | Simple | [OK][OK] Best on both axes - use this |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use the two-pointer method. It is optimal on BOTH time (O(n)) and space (O(1)) -
 * no trade-off to weigh, since brute force offers no compensating advantage. Reach
 * for brute force only to cross-check on small random arrays during testing.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * Start pointers at both ends and ALWAYS move the pointer at the shorter line
 * inward - the shorter line is the bottleneck and can never do better than its
 * current widest pairing, so discarding it loses nothing. The area is
 * min(height[left], height[right]) * (right - left) - height is the MINIMUM of the
 * two walls, never the sum, and width is a plain index difference with NO +1. This
 * "shrink from the shorter side" pattern is the canonical two-pointer greedy trick.
 */
// @formatter:on
