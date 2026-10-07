package Array;

import java.util.Arrays;

public class ProductOfArrayExceptSelf {
    public static void main(String[] args) {
        ProductOfArrayExceptSelf productOfArrayExceptSelf = new ProductOfArrayExceptSelf();
        System.out.println("ProductOfArrayExceptSelf : "
                + Arrays.toString(productOfArrayExceptSelf.productExceptSelfBruteForce(new int[] { 1, 2, 3, 4 })));
        System.out.println("ProductOfArrayExceptSelf : "
                + Arrays.toString(productOfArrayExceptSelf.productExceptSelfBruteForce(new int[] { -1, 1, 0, -3, 3 })));
        System.out.println("----------------------------------------");
        System.out.println("ProductOfArrayExceptSelf : "
                + Arrays.toString(productOfArrayExceptSelf.productExceptSelfDivision(new int[] { 1, 2, 3, 4 })));
        System.out.println("ProductOfArrayExceptSelf : "
                + Arrays.toString(productOfArrayExceptSelf.productExceptSelfDivision(new int[] { -1, 1, 0, -3, 3 })));
        System.out.println("----------------------------------------");
        System.out.println("ProductOfArrayExceptSelf : "
                + Arrays.toString(
                        productOfArrayExceptSelf.productExceptSelfPrefixSuffixArrays(new int[] { 1, 2, 3, 4 })));
        System.out.println("ProductOfArrayExceptSelf : "
                + Arrays.toString(
                        productOfArrayExceptSelf.productExceptSelfPrefixSuffixArrays(new int[] { -1, 1, 0, -3, 3 })));
        System.out.println("----------------------------------------");
        System.out.println("ProductOfArrayExceptSelf : "
                + Arrays.toString(
                        productOfArrayExceptSelf.productExceptSelfPrefixSuffixScalar(new int[] { 1, 2, 3, 4 })));
        System.out.println("ProductOfArrayExceptSelf : "
                + Arrays.toString(
                        productOfArrayExceptSelf.productExceptSelfPrefixSuffixScalar(new int[] { -1, 1, 0, -3, 3 })));
    }

    // @formatter:off
    /**
     * 
     * https://leetcode.com/problems/product-of-array-except-self/
     * 
     * Given an integer array nums, return an array answer such that answer[i] is
     * equal to the product of all the elements of nums except nums[i].
     * 
     * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit
     * integer.
     * 
     * You must write an algorithm that runs in O(n) time and without using the
     * division operation.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: nums = [1,2,3,4]
     * Output: [24,12,8,6]
     * Example 2:
     * 
     * Input: nums = [-1,1,0,-3,3]
     * Output: [0,0,9,0,0]
     * 
     * 
     * Constraints:
     * 
     * 2 <= nums.length <= 105
     * -30 <= nums[i] <= 30
     * The input is generated such that answer[i] is guaranteed to fit in a 32-bit
     * integer.
     * 
     * 
     * Follow up: Can you solve the problem in O(1) extra space complexity? (The
     * output array does not count as extra space for space complexity analysis.)
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach                 | Time   | Space | Code Complexity        | Recommended?                          |
     * |--------------------------|--------|-------|------------------------|---------------------------------------|
     * | Brute Force              | O(n^2) | O(1)  | Very simple            | NO — too slow for large n             |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int[] productExceptSelfBruteForce(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            int product = 1;
            for (int j = 0; j < n; j++) {
                if (j != i)
                    product *= nums[j];
            }
            result[i] = product;
        }
        return result;
    }

    // @formatter:off
    /**
     * 
     * | Approach                 | Time   | Space | Code Complexity        | Recommended?                          |
     * |--------------------------|--------|-------|------------------------|---------------------------------------|
     * | Division                 | O(n)   | O(1)  | Medium (zero cases)    | NO — violates no-division; zeros fail |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int[] productExceptSelfDivision(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];
        int product = 1, zeroCount = 0, zeroIndex = -1;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                zeroCount++;
                zeroCount = i;
            } else {
                product *= nums[i];
            }
        }
        if (zeroCount > 1)
            return answer;
        if (zeroCount == 1) {
            answer[zeroIndex] = product;
            return answer;
        }

        for (int i = 0; i < n; i++) {
            answer[i] = product / nums[i];
        }
        return answer;
    }

    // @formatter:off
    /**
     * 
     * | Approach                 | Time   | Space | Code Complexity        | Recommended?                          |
     * |--------------------------|--------|-------|------------------------|---------------------------------------|
     * | Prefix + Suffix Arrays   | O(n)   | O(n)  | Simple, very readable  | YES — good when readability > space   |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int[] productExceptSelfPrefixSuffixArrays(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n];
        int[] suffix = new int[n];
        int[] answer = new int[n];

        prefix[0] = 1;
        for (int i = 1; i < n; i++)
            prefix[i] = prefix[i - 1] * nums[i - 1];
        suffix[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--)
            suffix[i] = suffix[i + 1] * nums[i + 1];
        for (int i = 0; i < n; i++)
            answer[i] = prefix[i] * suffix[i];
        return answer;
    }

    // @formatter:off
    /**
     * 
     * | Approach                 | Time   | Space | Code Complexity        | Recommended?                          |
     * |--------------------------|--------|-------|------------------------|---------------------------------------|
     * | Prefix + Suffix Scalar   | O(n)   | O(1)  | Simple                 | BEST — recommended overall            |
     * 
     * @param nums
     * @return
     */
    // @formatter:on
    public int[] productExceptSelfPrefixSuffixScalar(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];
        answer[0] = 1;
        for (int i = 1; i < n; i++) {
            answer[i] = answer[i - 1] * nums[i - 1];
        }
        int suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            answer[i] *= suffix;
            suffix *= nums[i];
        }
        return answer;
    }
}
// @formatter:off
/*
 * ============================================================
 * PRODUCT OF ARRAY EXCEPT SELF — DEEP DIVE EXPLANATION
 * ============================================================
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * Given an integer array nums, build a new array answer where each answer[i]
 * holds the product of every element in nums EXCEPT nums[i] itself. The twist:
 * you must do it WITHOUT the division operator, ideally in O(n) time.
 * This is LeetCode #238 (Medium).
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 * int[] nums — an array of integers of length n.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 * int[] answer — length n, where answer[i] = product of all nums[j] for j != i.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 *   - 2 <= n <= 10^5
 *   - -30 <= nums[i] <= 30
 *   - Every prefix/suffix product is guaranteed to fit in a 32-bit int.
 *   - Must run in O(n) time and WITHOUT division.
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * For each index i: (product of all elements LEFT of i) x (product RIGHT of i).
 * That split into a left part and a right part is the entire key.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    Input:  nums = [1, 2, 3, 4]
 *    Output: [24, 12, 8, 6]
 *    answer[0] = 2*3*4 = 24
 *    answer[1] = 1*3*4 = 12
 *    answer[2] = 1*2*4 = 8
 *    answer[3] = 1*2*3 = 6
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * The "obvious" solution multiplies everything, then divides out nums[i]. But
 * division is banned (and breaks on zeros). So instead of REMOVING an element
 * from a total, we NEVER include it in the first place:
 *    answer[i] = (everything LEFT of i) x (everything RIGHT of i)
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 *   1. "product except self" = "left product" x "right product".
 *   2. Sweep left->right, keeping a running product of everything seen so far
 *      but NOT the current element. Store it in answer[i].
 *   3. Sweep right->left, keeping a running product from the right, multiply
 *      it into answer[i].
 *   4. answer[i] now holds both halves fused together — no division needed.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                    | Why it's tricky                                   |
 * |------------------------------|---------------------------------------------------|
 * | Division is forbidden        | total / nums[i] is off the table.                 |
 * | Zeros in the array           | Dividing by a zero element is undefined.          |
 * | Achieving O(1) extra space   | Collapsing prefix+suffix into output+scalar.      |
 * | Off-by-one in running product| Must multiply nums[i-1], not nums[i].             |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 *
 * | # | Approach                  | Key Idea                                   | Best Used When                       | Time    | Space               |
 * |---|---------------------------|--------------------------------------------|--------------------------------------|---------|---------------------|
 * | 1 | Brute Force               | For each i, loop all other j and multiply  | Tiny arrays; clarity over speed      | O(n^2)  | O(1) (space-optimal)|
 * | 2 | Division (total / self)   | Multiply all, divide out each element      | Only if division allowed & no zeros  | O(n)    | O(1)                |
 * | 3 | Prefix + Suffix Arrays    | Two precomputed left/right product arrays  | Cleanest, most readable O(n)         | O(n)    | O(n)                |
 * | 4 | Prefix Pass + Suffix Scalar| Left products in output, fold right in     | Intended optimal: O(n) time,O(1) extra| O(n) OK | O(1) extra OK       |
 *
 * Brute force is O(n^2) and unacceptable at n = 10^5 (10 billion ops). Division
 * hits O(n) but is disqualified by the no-division rule and fails on zeros.
 * Approaches 3 and 4 share the same O(n) time and same prefix/suffix
 * architecture — the only difference is space: approach 3 uses two auxiliary
 * arrays (O(n)); approach 4 reuses the output array plus one scalar to reach
 * O(1) EXTRA space (output doesn't count). Approach 4 ties on time and wins on
 * space, so it is the recommended optimal. Use approach 3 only when readability
 * matters more than the last auxiliary array.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * 1. Create answer of length n.
 * 2. For each i, initialize product = 1.
 * 3. Loop j over all indices; when j != i, multiply nums[j] into product.
 * 4. answer[i] = product.
 *
 *    import java.util.Arrays;
 *
 *    public class BruteForce {
 *        public static int[] productExceptSelf(int[] nums) {
 *            int n = nums.length;
 *            int[] answer = new int[n];
 *            for (int i = 0; i < n; i++) {
 *                int product = 1;
 *                for (int j = 0; j < n; j++) {
 *                    if (j != i) product *= nums[j];   // skip the element itself
 *                }
 *                answer[i] = product;
 *            }
 *            return answer;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {1, 2, 3, 4};
 *            System.out.println(Arrays.toString(productExceptSelf(nums))); // [24, 12, 8, 6]
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 2: Division (for contrast — not a valid submission)
 * ------------------------------------------------------------
 * 1. Compute total = product of all, and count zeros.
 * 2. More than one zero -> every answer is 0.
 * 3. Exactly one zero -> only the zero's position gets the non-zero product.
 * 4. No zero -> answer[i] = total / nums[i].
 *
 *    import java.util.Arrays;
 *
 *    public class DivisionApproach {
 *        public static int[] productExceptSelf(int[] nums) {
 *            int n = nums.length;
 *            int[] answer = new int[n];
 *            int product = 1, zeroCount = 0, zeroIndex = -1;
 *
 *            for (int i = 0; i < n; i++) {
 *                if (nums[i] == 0) { zeroCount++; zeroIndex = i; }
 *                else product *= nums[i];
 *            }
 *
 *            if (zeroCount > 1) return answer;              // all zeros
 *            if (zeroCount == 1) { answer[zeroIndex] = product; return answer; }
 *
 *            for (int i = 0; i < n; i++) answer[i] = product / nums[i];
 *            return answer;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {1, 2, 3, 4};
 *            System.out.println(Arrays.toString(productExceptSelf(nums))); // [24, 12, 8, 6]
 *        }
 *    }
 *
 * The zero-handling shows why division is fragile: it needs special cases the
 * prefix/suffix method never has to think about.
 *
 * ------------------------------------------------------------
 * Approach 3: Prefix + Suffix Arrays
 * ------------------------------------------------------------
 * 1. Build prefix[] where prefix[i] = product of all elements before i (prefix[0]=1).
 * 2. Build suffix[] where suffix[i] = product of all elements after i (suffix[n-1]=1).
 * 3. For each i, answer[i] = prefix[i] * suffix[i].
 *
 *    import java.util.Arrays;
 *
 *    public class PrefixSuffixArrays {
 *        public static int[] productExceptSelf(int[] nums) {
 *            int n = nums.length;
 *            int[] prefix = new int[n];
 *            int[] suffix = new int[n];
 *            int[] answer = new int[n];
 *
 *            prefix[0] = 1;
 *            for (int i = 1; i < n; i++) prefix[i] = prefix[i - 1] * nums[i - 1];
 *
 *            suffix[n - 1] = 1;
 *            for (int i = n - 2; i >= 0; i--) suffix[i] = suffix[i + 1] * nums[i + 1];
 *
 *            for (int i = 0; i < n; i++) answer[i] = prefix[i] * suffix[i];
 *            return answer;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {1, 2, 3, 4};
 *            System.out.println(Arrays.toString(productExceptSelf(nums))); // [24, 12, 8, 6]
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 4: Prefix Pass + Suffix Scalar (Optimal, marked best)
 * ------------------------------------------------------------
 * 1. Pass 1 (left->right): fill answer[i] with running prefix product. answer[0]=1.
 * 2. Pass 2 (right->left): keep one suffix scalar starting at 1. At each i,
 *    multiply suffix into answer[i], then update suffix *= nums[i].
 * 3. Output now holds prefix x suffix at every index, no auxiliary arrays.
 *
 *    import java.util.Arrays;
 *
 *    public class ProductExceptSelf {
 *        public static int[] productExceptSelf(int[] nums) {
 *            int n = nums.length;
 *            int[] answer = new int[n];
 *
 *            // Pass 1: answer[i] = product of everything to the LEFT of i
 *            answer[0] = 1;
 *            for (int i = 1; i < n; i++) {
 *                answer[i] = answer[i - 1] * nums[i - 1];
 *            }
 *
 *            // Pass 2: multiply in the product of everything to the RIGHT of i
 *            int suffix = 1;
 *            for (int i = n - 1; i >= 0; i--) {
 *                answer[i] *= suffix;   // fuse left product with right product
 *                suffix *= nums[i];     // extend the running right product
 *            }
 *            return answer;
 *        }
 *
 *        public static void main(String[] args) {
 *            int[] nums = {1, 2, 3, 4};
 *            System.out.println(Arrays.toString(productExceptSelf(nums))); // [24, 12, 8, 6]
 *        }
 *    }
 *
 * The subtle part is indexing: in pass 1 we multiply nums[i-1] (never nums[i]),
 * so the current element is excluded from its own left product. In pass 2 we
 * multiply suffix BEFORE updating it with nums[i], excluding nums[i] from its
 * own right product.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Approach 1 — Brute Force
 *   Time:  O(n^2). Outer loop n times, inner loop n times -> n*n. For n=10^5,
 *          ~10^10 operations — far too slow.
 *   Space: O(1) extra (output array + scalar product).
 *
 * Approach 2 — Division
 *   Time:  O(n). One pass for total, one to divide. n=10^5 -> ~2*10^5 ops.
 *   Space: O(1) extra.
 *
 * Approach 3 — Prefix + Suffix Arrays
 *   Time:  O(n). Three linear passes -> 3n ~ 3*10^5 ops for n=10^5.
 *   Space: O(n) extra — two auxiliary arrays of length n (2n extra ints).
 *
 * Approach 4 — Prefix Pass + Suffix Scalar (Optimal)
 *   Time:  O(n). Two linear passes -> 2n ~ 2*10^5 ops for n=10^5.
 *   Space: O(1) extra. Only output (mandatory) + one suffix scalar.
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * Using nums = [1, 2, 3, 4] throughout.
 *
 * ------------------------------------------------------------
 * Approach 1 — Brute Force
 * ------------------------------------------------------------
 *    i=0: product = 2*3*4 = 24  -> answer[0]=24
 *    i=1: product = 1*3*4 = 12  -> answer[1]=12
 *    i=2: product = 1*2*4 = 8   -> answer[2]=8
 *    i=3: product = 1*2*3 = 6   -> answer[3]=6
 *    Output: [24, 12, 8, 6]
 *
 * ------------------------------------------------------------
 * Approach 2 — Division
 * ------------------------------------------------------------
 *    total = 1*2*3*4 = 24, zeroCount = 0
 *    answer[0] = 24/1 = 24
 *    answer[1] = 24/2 = 12
 *    answer[2] = 24/3 = 8
 *    answer[3] = 24/4 = 6
 *    Output: [24, 12, 8, 6]
 *
 * ------------------------------------------------------------
 * Approach 3 — Prefix + Suffix Arrays
 * ------------------------------------------------------------
 *    prefix[0]=1
 *    prefix[1]=1*nums[0]=1
 *    prefix[2]=1*nums[1]=2
 *    prefix[3]=2*nums[2]=6   -> prefix = [1, 1, 2, 6]
 *
 *    suffix[3]=1
 *    suffix[2]=1*nums[3]=4
 *    suffix[1]=4*nums[2]=12
 *    suffix[0]=12*nums[1]=24 -> suffix = [24, 12, 4, 1]
 *
 *    answer = [1*24, 1*12, 2*4, 6*1] = [24, 12, 8, 6]
 *
 * ------------------------------------------------------------
 * Approach 4 — Prefix Pass + Suffix Scalar (Optimal)
 * ------------------------------------------------------------
 *    Pass 1 (left products into answer):
 *      answer[0]=1
 *      answer[1]=answer[0]*nums[0]=1
 *      answer[2]=answer[1]*nums[1]=2
 *      answer[3]=answer[2]*nums[2]=6   -> answer = [1, 1, 2, 6]
 *
 *    Pass 2 (suffix scalar, right->left), suffix starts at 1:
 *      i=3: answer[3]*=1  -> 6 ;  suffix*=nums[3]=4
 *      i=2: answer[2]*=4  -> 8 ;  suffix*=nums[2]=12
 *      i=1: answer[1]*=12 -> 12;  suffix*=nums[1]=24
 *      i=0: answer[0]*=24 -> 24;  suffix*=nums[0]=24
 *      answer = [24, 12, 8, 6]
 *    Output: [24, 12, 8, 6]
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 *
 * | Edge Case            | Input                 | Expected Output   | How Handled                                        |
 * |----------------------|-----------------------|-------------------|----------------------------------------------------|
 * | Minimum length (n=2) | [2, 3]                | [3, 2]            | Both passes run; each answer is the other element. |
 * | Exactly one zero     | [-1, 1, 0, -3, 3]     | [0, 0, 9, 0, 0]   | Only the zero's index gets a non-zero product.     |
 * | Multiple zeros       | [0, 0]                | [0, 0]            | Every position has a zero on one side -> all zeros.|
 * | Zeros around values  | [0, 4, 0]             | [0, 0, 0]         | Two zeros make every product zero.                 |
 * | Contains negatives   | [-1, 2, -3]           | [-6, 3, -2]       | Sign carried through multiplication automatically. |
 * | All ones             | [1, 1, 1]             | [1, 1, 1]         | Products stay 1 everywhere.                        |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * WRONG — multiplying nums[i] into its own left product (off-by-one):
 *    answer[i] = answer[i - 1] * nums[i];
 * CORRECT — use the previous element:
 *    answer[i] = answer[i - 1] * nums[i - 1];
 *
 * WRONG — updating the suffix scalar BEFORE using it:
 *    suffix *= nums[i];
 *    answer[i] *= suffix;
 * CORRECT — use it first, then extend:
 *    answer[i] *= suffix;
 *    suffix *= nums[i];
 *
 * Reaching for division "just this once" is another trap — it fails on any
 * array containing a zero and violates the problem constraint.
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 *
 * Q: What edge cases might this miss?
 * A: The prefix/suffix method is robust to zeros, negatives, and minimum length
 *    because it never divides. Overflow is not a concern here — the problem
 *    guarantees every prefix/suffix product fits in 32-bit int. Without that
 *    guarantee you'd switch to long.
 *
 * Q: Are there any type mismatches?
 * A: No. Inputs and outputs are int[]; the running suffix is an int. All
 *    multiplications stay in int per the constraints.
 *
 * Q: How can I verify this works right now?
 *    import java.util.Arrays;
 *
 *    public class VerifyProductExceptSelf {
 *        static int[] solve(int[] nums) {
 *            int n = nums.length;
 *            int[] answer = new int[n];
 *            answer[0] = 1;
 *            for (int i = 1; i < n; i++) answer[i] = answer[i - 1] * nums[i - 1];
 *            int suffix = 1;
 *            for (int i = n - 1; i >= 0; i--) { answer[i] *= suffix; suffix *= nums[i]; }
 *            return answer;
 *        }
 *
 *        public static void main(String[] args) {
 *            assert Arrays.equals(solve(new int[]{1, 2, 3, 4}), new int[]{24, 12, 8, 6});
 *            assert Arrays.equals(solve(new int[]{-1, 1, 0, -3, 3}), new int[]{0, 0, 9, 0, 0});
 *            assert Arrays.equals(solve(new int[]{2, 3}), new int[]{3, 2});
 *            assert Arrays.equals(solve(new int[]{0, 0}), new int[]{0, 0});
 *            System.out.println("All assertions passed.");
 *        }
 *    }
 * Run with: java -ea VerifyProductExceptSelf
 *
 * | Approach                | Risk                                  | Mitigation                                       |
 * |-------------------------|---------------------------------------|--------------------------------------------------|
 * | Brute Force             | O(n^2) times out at large n           | Use only for tiny inputs or as an oracle.        |
 * | Division                | Breaks on zeros; violates no-division | Avoid entirely for this problem.                 |
 * | Prefix + Suffix Arrays  | Extra O(n) memory                     | Fine if readability matters more than space.     |
 * | Prefix + Suffix Scalar  | Off-by-one / update-order bugs        | nums[i-1] in pass 1; use suffix before updating. |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #238 · Difficulty: Medium · One of the most-asked array problems.
 *
 * | Company           | Frequency (stars) | Notes                                             |
 * |-------------------|-------------------|---------------------------------------------------|
 * | Amazon            | * * * * *         | Perennial favorite; no-division always emphasized.|
 * | Facebook / Meta   | * * * * *         | Often paired with an O(1) space follow-up.        |
 * | Microsoft         | * * * *           | Common phone-screen question.                     |
 * | Google            | * * * *           | Sometimes extended to 2D or streaming variants.   |
 * | Apple             | * * *             | Appears in array/prefix-sum rounds.               |
 * | Bloomberg         | * * * *           | Popular on-site array question.                   |
 * | Adobe             | * * *             | Appears in mid-level SDE loops.                   |
 * | Uber              | * * *             | Asked with the space-optimization follow-up.      |
 * | LinkedIn          | * * *             | Standard array-manipulation screen.               |
 * | Oracle            | * *               | Occasionally in coding rounds.                    |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 *
 * | Approach                 | Time   | Space | Code Complexity        | Recommended?                          |
 * |--------------------------|--------|-------|------------------------|---------------------------------------|
 * | Brute Force              | O(n^2) | O(1)  | Very simple            | NO — too slow for large n             |
 * | Division                 | O(n)   | O(1)  | Medium (zero cases)    | NO — violates no-division; zeros fail |
 * | Prefix + Suffix Arrays   | O(n)   | O(n)  | Simple, very readable  | YES — good when readability > space   |
 * | Prefix + Suffix Scalar   | O(n)   | O(1)  | Simple                 | BEST — recommended overall            |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use Approach 4 (Prefix Pass + Suffix Scalar): best O(n) time AND O(1) extra
 * space, so it dominates the array-based version with no downside. No valid
 * approach beats it on either axis — a single clear winner here.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * Pattern: "product except self = left product x right product". Precompute
 * prefixes going forward, then fold in suffixes going backward with one scalar.
 * The two gotchas: indexing (nums[i-1] in the left pass) and update order (use
 * the suffix scalar BEFORE multiplying nums[i] into it). Never reach for
 * division — the problem forbids it and it shatters on zeros.
 */
// @formatter:on
