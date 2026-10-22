package Array;

public class GasStation {
    public static void main(String[] args) {
        GasStation gasStation = new GasStation();
        System.out.println("GasStation : "
                + gasStation.canCompleteCircuitBruteForce(new int[] { 1, 2, 3, 4, 5 }, new int[] { 3, 4, 5, 1, 2 }));
        System.out.println("GasStation : "
                + gasStation.canCompleteCircuitBruteForce(new int[] { 2, 3, 4 }, new int[] { 3, 4, 3 }));
        System.out.println("-----------------------------");
        System.out.println("GasStation : "
                + gasStation.canCompleteCircuitMinPrefixSum(new int[] { 1, 2, 3, 4, 5 }, new int[] { 3, 4, 5, 1, 2 }));
        System.out.println("GasStation : "
                + gasStation.canCompleteCircuitMinPrefixSum(new int[] { 2, 3, 4 }, new int[] { 3, 4, 3 }));
        System.out.println("-----------------------------");
        System.out.println("GasStation : "
                + gasStation.canCompleteCircuitGreedyOnePass(new int[] { 1, 2, 3, 4, 5 }, new int[] { 3, 4, 5, 1, 2 }));
        System.out.println("GasStation : "
                + gasStation.canCompleteCircuitGreedyOnePass(new int[] { 2, 3, 4 }, new int[] { 3, 4, 3 }));

    }

    // @formatter:off
    /**
     * 
     * https://leetcode.com/problems/gas-station/
     * 
     * There are n gas stations along a circular route, where the amount of gas at
     * the ith station is gas[i].
     * 
     * You have a car with an unlimited gas tank and it costs cost[i] of gas to
     * travel from the ith station to its next (i + 1)th station. You begin the
     * journey with an empty tank at one of the gas stations.
     * 
     * Given two integer arrays gas and cost, return the starting gas station's
     * index if you can travel around the circuit once in the clockwise direction,
     * otherwise return -1. If there exists a solution, it is guaranteed to be
     * unique.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
     * Output: 3
     * Explanation:
     * Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 +
     * 4 = 4
     * Travel to station 4. Your tank = 4 - 1 + 5 = 8
     * Travel to station 0. Your tank = 8 - 2 + 1 = 7
     * Travel to station 1. Your tank = 7 - 3 + 2 = 6
     * Travel to station 2. Your tank = 6 - 4 + 3 = 5
     * Travel to station 3. The cost is 5. Your gas is just enough to travel back to
     * station 3.
     * Therefore, return 3 as the starting index.
     * Example 2:
     * 
     * Input: gas = [2,3,4], cost = [3,4,3]
     * Output: -1
     * Explanation:
     * You can't start at station 0 or 1, as there is not enough gas to travel to
     * the next station.
     * Let's start at station 2 and fill up with 4 unit of gas. Your tank = 0 + 4 =
     * 4
     * Travel to station 0. Your tank = 4 - 3 + 2 = 3
     * Travel to station 1. Your tank = 3 - 3 + 3 = 3
     * You cannot travel back to station 2, as it requires 4 unit of gas but you
     * only have 3.
     * Therefore, you can't travel around the circuit once no matter where you
     * start.
     * 
     * 
     * Constraints:
     * 
     * n == gas.length == cost.length
     * 1 <= n <= 105
     * 0 <= gas[i], cost[i] <= 104
     * The input is generated such that the answer is unique.
     * 
     * 
     */
    // @formatter:on

    // @formatter:off
    /**
     * 
     * | Approach        | Time   | Space | Code Complexity | Recommended?                                   |
     * |-----------------|--------|-------|-----------------|------------------------------------------------|
     * | Brute Force     | O(n^2) | O(1)  | Low             | ❌ Times out for large n; correctness oracle   |
     * 
     * @param gas
     * @param cost
     * @return
     */
    // @formatter:on
    public int canCompleteCircuitBruteForce(int[] gas, int[] cost) {
        int n = gas.length;
        for (int start = 0; start < n; start++) {
            int tank = 0;
            boolean completed = true;
            for (int step = 0; step < n; step++) {
                int station = (step + start) % n;
                tank += gas[station] - cost[station];
                if (tank < 0) {
                    completed = false;
                    break;
                }
            }
            if (completed)
                return start;
        }
        return -1;
    }

    // @formatter:off
    /**
     * 
     * | Approach        | Time   | Space | Code Complexity | Recommended?                                   |
     * |-----------------|--------|-------|-----------------|------------------------------------------------|
     * | Min Prefix Sum  | O(n)   | O(1)  | Medium          | ✅ Excellent alternative framing               |
     * 
     * @param gas
     * @param cost
     * @return
     */
    // @formatter:on
    public int canCompleteCircuitMinPrefixSum(int[] gas, int[] cost) {
        int n = gas.length;
        int total = 0, running = 0;
        int minRunning = Integer.MAX_VALUE, minIndex = 0;
        for (int i = 0; i < n; i++) {
            int diff = gas[i] - cost[i];
            total += diff;
            running += diff;
            if (running < minRunning) {
                minRunning = running;
                minIndex = i;
            }
        }
        if (total < 0)
            return -1;
        return (minIndex + 1) % n;
    }

    // @formatter:off
    /**
     * 
     * | Approach        | Time   | Space | Code Complexity | Recommended?                                   |
     * |-----------------|--------|-------|-----------------|------------------------------------------------|
     * | Greedy One-Pass | O(n)   | O(1)  | Low             | ✅✅ Best overall — optimal on both axes        |
     * 
     * @param gas
     * @param cost
     * @return
     */
    // @formatter:on
    public int canCompleteCircuitGreedyOnePass(int[] gas, int[] cost) {
        int total = 0;
        int current = 0;
        int start = 0;

        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            total += diff;
            current += diff;
            if (current < 0) {
                start = i + 1;
                current = 0;
            }
        }
        return total >= 0 ? start : -1;
    }
}

// @formatter:off
/*
 * ============================================================
 * GAS STATION — DEEP DIVE EXPLANATION
 * ============================================================
 * LeetCode #134 · Difficulty: Medium
 *
 * ============================================================
 * 1. PROBLEM STATEMENT
 * ============================================================
 *
 * ------------------------------------------------------------
 * What is the Problem?
 * ------------------------------------------------------------
 * You are on a CIRCULAR route with n gas stations. At station i you can pick up
 * gas[i] units of fuel, and it costs cost[i] units to drive from station i to the
 * next station (i+1) % n. You begin with an EMPTY tank and may choose ANY station
 * as your starting point. Return the index of the starting station from which you
 * can complete one full loop (clockwise). If no such start exists, return -1.
 * If an answer exists, it is guaranteed to be UNIQUE.
 *
 * ------------------------------------------------------------
 * Input Format
 * ------------------------------------------------------------
 *  - int[] gas  — gas[i] is fuel gained at station i
 *  - int[] cost — cost[i] is fuel needed to reach station i+1
 *  - Both arrays have the same length n.
 *
 * ------------------------------------------------------------
 * Output Format
 * ------------------------------------------------------------
 *  - int — the starting index (0-based), or -1 if impossible.
 *
 * ------------------------------------------------------------
 * Constraints
 * ------------------------------------------------------------
 *  - n == gas.length == cost.length
 *  - 1 <= n <= 10^5
 *  - 0 <= gas[i], cost[i] <= 10^4
 *
 * ------------------------------------------------------------
 * What Exactly Needs to Be Computed?
 * ------------------------------------------------------------
 * A single starting index such that, driving forward and never letting the tank
 * drop below zero at any leg, you return to the same station.
 *
 * ------------------------------------------------------------
 * Quick Example
 * ------------------------------------------------------------
 *    gas  = [1, 2, 3, 4, 5]
 *    cost = [3, 4, 5, 1, 2]
 *    Answer: 3
 * Starting at station 3: 0 -> +4-1=3 -> +5-2=6 -> +1-3=4 -> +2-4=2 -> +3-5=0.
 * Never negative, full loop completed.
 *
 * ============================================================
 * 2. INTUITION
 * ============================================================
 *
 * ------------------------------------------------------------
 * Core Idea in Simple Terms
 * ------------------------------------------------------------
 * For each leg, what matters is the NET gain diff[i] = gas[i] - cost[i]. If the sum
 * of all net gains is negative, you can never close the loop -> -1. If the total is
 * non-negative, a valid start is GUARANTEED to exist, and the trick is finding where
 * to begin so your tank never dips below empty.
 *
 * ------------------------------------------------------------
 * How a Human Reasons About It
 * ------------------------------------------------------------
 *  1. Convert the two arrays into one array of net gains diff[i] = gas[i] - cost[i].
 *  2. Add them all up. If the total is negative, give up (-1).
 *  3. Otherwise start driving from station 0, tracking a running tank.
 *  4. The moment the tank goes negative at station i, everything from the current
 *     start through i is a dead end — jump the candidate start to i+1, reset tank 0.
 *  5. Since a solution is guaranteed when the total is non-negative, whatever
 *     candidate start survives to the end is the answer.
 *
 * ------------------------------------------------------------
 * Why "reset to i+1" is safe (the key lemma)
 * ------------------------------------------------------------
 * If you start at s and the tank stays >= 0 up to station i-1 but goes negative at i,
 * then NO station in [s, i] can be a valid start. Proof: for any k in [s, i], the
 * fuel accumulated from s up to just before k was >= 0. Since the total from s to i
 * is negative, the portion from k to i must ALSO be negative. So starting at k fails
 * at i too. Hence the earliest possible start is i+1.
 *
 * ------------------------------------------------------------
 * What Makes This Tricky?
 * ------------------------------------------------------------
 * | Challenge                | Why it's tricky                                            |
 * |--------------------------|------------------------------------------------------------|
 * | Circular route           | Trip wraps; naive simulation needs modular indexing.       |
 * | Choosing the right start | n candidate starts; brute force checks each -> quadratic.  |
 * | Proving the greedy jump  | Skipping all stations between failed start and failure     |
 * |                          | point needs the prefix-sum lemma above.                    |
 * | Two independent facts    | "Solution exists" (total >= 0) and "this start works" are  |
 * |                          | separate and must be combined.                             |
 * | Overflow-looking but safe| n*max(gas) up to 10^9 fits in int; people reach for long.  |
 *
 * ============================================================
 * 3. APPROACH OVERVIEW
 * ============================================================
 * | # | Approach                 | Key Idea                                   | Best Used When            | Time   | Space |
 * |---|--------------------------|--------------------------------------------|---------------------------|--------|-------|
 * | 1 | Brute Force (simulate)   | Try each station, drive full loop, check   | n tiny / correctness oracle| O(n^2)| O(1)  |
 * | 2 | Min Prefix Sum           | Best start is right after lowest cumulative| "find the minimum" model  | O(n)   | O(1)  |
 * | 3 | Greedy One-Pass  ✅       | Reset candidate start when running tank < 0| General case, cleanest    | O(n)   | O(1)  |
 *
 * All three use O(1) auxiliary space, so SPACE is not the differentiator — TIME is.
 * Brute force is O(n^2) and blows past the limit at n = 10^5 (~10^10 ops). Both O(n)
 * approaches are optimal on time and space; they are genuinely distinct IDEAS
 * (reset-on-failure vs. locate-the-global-minimum) that converge on the same
 * complexity. Greedy One-Pass (Approach 3) is marked optimal as the cleanest,
 * most idiomatic solution; Min Prefix Sum is equally efficient and worth knowing.
 *
 * ============================================================
 * 4. DETAILED SOLUTIONS IN JAVA
 * ============================================================
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * Algorithm:
 *  1. For each candidate start in 0..n-1:
 *  2. Initialize tank = 0.
 *  3. Drive n steps forward using modular index (start + step) % n.
 *  4. If tank ever drops below 0, abandon this start.
 *  5. If all n legs succeed, return start.
 *  6. If no start works, return -1.
 *
 *    public class GasStationBrute {
 *        public int canCompleteCircuit(int[] gas, int[] cost) {
 *            int n = gas.length;
 *            for (int start = 0; start < n; start++) {
 *                int tank = 0;
 *                boolean completed = true;
 *                for (int step = 0; step < n; step++) {
 *                    int station = (start + step) % n;
 *                    tank += gas[station] - cost[station];
 *                    if (tank < 0) {          // ran dry on this leg
 *                        completed = false;
 *                        break;
 *                    }
 *                }
 *                if (completed) return start;
 *            }
 *            return -1;
 *        }
 *
 *        public static void main(String[] args) {
 *            GasStationBrute solver = new GasStationBrute();
 *            int[] gas  = {1, 2, 3, 4, 5};
 *            int[] cost = {3, 4, 5, 1, 2};
 *            System.out.println(solver.canCompleteCircuit(gas, cost)); // 3
 *        }
 *    }
 *
 * ------------------------------------------------------------
 * Approach 2: Min Prefix Sum
 * ------------------------------------------------------------
 * Algorithm:
 *  1. Walk once, keeping running = cumulative (gas[i]-cost[i]) and total = same sum.
 *  2. Track the index minIndex where running reaches its LOWEST value.
 *  3. After the pass, if total < 0 -> return -1.
 *  4. Otherwise ideal start is right after the lowest point: (minIndex + 1) % n.
 *
 *    public class GasStationMinPrefix {
 *        public int canCompleteCircuit(int[] gas, int[] cost) {
 *            int n = gas.length;
 *            int total = 0, running = 0;
 *            int minRunning = Integer.MAX_VALUE, minIndex = 0;
 *
 *            for (int i = 0; i < n; i++) {
 *                int diff = gas[i] - cost[i];
 *                total += diff;
 *                running += diff;
 *                if (running < minRunning) {   // new lowest cumulative point
 *                    minRunning = running;
 *                    minIndex = i;
 *                }
 *            }
 *            if (total < 0) return -1;
 *            return (minIndex + 1) % n;
 *        }
 *
 *        public static void main(String[] args) {
 *            GasStationMinPrefix solver = new GasStationMinPrefix();
 *            int[] gas  = {1, 2, 3, 4, 5};
 *            int[] cost = {3, 4, 5, 1, 2};
 *            System.out.println(solver.canCompleteCircuit(gas, cost)); // 3
 *        }
 *    }
 *
 * Why minIndex + 1? The cumulative curve hits its deepest dip at minIndex. Starting
 * just after it means the first station measured from is the point of maximum
 * accumulated deficit, so from there onward every relative prefix sum stays >= 0.
 *
 * ------------------------------------------------------------
 * Approach 3: Greedy One-Pass  ✅ (Optimal)
 * ------------------------------------------------------------
 * Algorithm:
 *  1. Keep total (net over whole array) and current (net since candidate start).
 *  2. For each station i, add gas[i]-cost[i] to both.
 *  3. If current drops below 0, set start = i+1 and reset current = 0.
 *  4. After the pass, if total >= 0 return start, else return -1.
 *
 *    public class GasStation {
 *        public int canCompleteCircuit(int[] gas, int[] cost) {
 *            int total = 0;      // net fuel across the entire circuit
 *            int current = 0;    // net fuel since the current candidate start
 *            int start = 0;      // current candidate starting station
 *
 *            for (int i = 0; i < gas.length; i++) {
 *                int diff = gas[i] - cost[i];
 *                total += diff;
 *                current += diff;
 *                if (current < 0) {   // cannot reach station i+1 from start
 *                    start = i + 1;   // earliest viable next start
 *                    current = 0;     // fresh tank from the new start
 *                }
 *            }
 *            return total >= 0 ? start : -1;
 *        }
 *
 *        public static void main(String[] args) {
 *            GasStation solver = new GasStation();
 *            int[] gas  = {1, 2, 3, 4, 5};
 *            int[] cost = {3, 4, 5, 1, 2};
 *            System.out.println(solver.canCompleteCircuit(gas, cost)); // 3
 *
 *            int[] gas2  = {2, 3, 4};
 *            int[] cost2 = {3, 4, 3};
 *            System.out.println(solver.canCompleteCircuit(gas2, cost2)); // -1
 *        }
 *    }
 *
 * Non-obvious point: start can momentarily become n (if the last station forces a
 * reset). That's fine — when it happens, total is necessarily negative, so -1 is
 * returned and the out-of-range start is never used as an index.
 *
 * ============================================================
 * 5. TIME & SPACE COMPLEXITY
 * ============================================================
 *
 * Approach 1: Brute Force
 *  - Time O(n^2): outer loop n times, inner loop up to n legs -> n*n. For n=10^5
 *    that's ~10^10 operations — far too slow.
 *  - Space O(1): only scalar counters.
 *  - Example: n=1000 -> up to 10^6 ops (instant). n=100000 -> ~10^10 (times out).
 *
 * Approach 2: Min Prefix Sum
 *  - Time O(n): one pass computing cumulative sums and tracking a minimum.
 *  - Space O(1): four scalars (total, running, minRunning, minIndex).
 *  - Example: n=100000 -> ~10^5 iterations, sub-millisecond.
 *
 * Approach 3: Greedy One-Pass
 *  - Time O(n): single pass; each station touched once.
 *  - Space O(1): three scalars (total, current, start).
 *  - Example: n=100000 -> ~10^5 iterations vs brute force's 10^10 (~100000x less).
 *
 * ============================================================
 * 6. COMPLETE WORKED EXAMPLES
 * ============================================================
 * gas = [1,2,3,4,5], cost = [3,4,5,1,2] -> diff = [-2,-2,-2,3,3], total = 0.
 *
 * ------------------------------------------------------------
 * Approach 1: Brute Force
 * ------------------------------------------------------------
 * start = 0:
 *  ├─ station 0: tank = 0 + (-2) = -2  -> negative, abandon
 * start = 1:
 *  ├─ station 1: tank = -2 -> abandon
 * start = 2:
 *  ├─ station 2: tank = -2 -> abandon
 * start = 3:
 *  ├─ station 3: tank = 0 + 3 = 3
 *  ├─ station 4: tank = 3 + 3 = 6
 *  ├─ station 0: tank = 6 + (-2) = 4
 *  ├─ station 1: tank = 4 + (-2) = 2
 *  └─ station 2: tank = 2 + (-2) = 0   -> survived all 5 legs
 * Return 3 ✅
 *
 * ------------------------------------------------------------
 * Approach 2: Min Prefix Sum
 * ------------------------------------------------------------
 * | i | diff | running | minRunning | minIndex |
 * |---|------|---------|------------|----------|
 * | 0 | -2   | -2      | -2         | 0        |
 * | 1 | -2   | -4      | -4         | 1        |
 * | 2 | -2   | -6      | -6         | 2        |
 * | 3 | +3   | -3      | -6         | 2        |
 * | 4 | +3   |  0      | -6         | 2        |
 * total = 0 >= 0, lowest at minIndex = 2 -> start = (2+1) % 5 = 3 ✅
 *
 * ------------------------------------------------------------
 * Approach 3: Greedy One-Pass
 * ------------------------------------------------------------
 * | i | diff | total | current | action | start |
 * |---|------|-------|---------|--------|-------|
 * | 0 | -2   | -2    | -2      | reset  | 1     |
 * | 1 | -2   | -4    | -2      | reset  | 2     |
 * | 2 | -2   | -6    | -2      | reset  | 3     |
 * | 3 | +3   | -3    |  3      | keep   | 3     |
 * | 4 | +3   |  0    |  6      | keep   | 3     |
 * total = 0 >= 0 -> return start = 3 ✅
 *
 * ============================================================
 * 7. EDGE CASES
 * ============================================================
 * | Edge Case                     | Input                       | Expected | How Handled                                  |
 * |-------------------------------|-----------------------------|----------|----------------------------------------------|
 * | Single station, enough fuel   | gas=[5], cost=[4]           | 0        | total=1>=0, no reset, start stays 0.         |
 * | Single station, not enough    | gas=[5], cost=[6]           | -1       | total=-1<0 -> -1.                            |
 * | No valid start (total < 0)    | gas=[2,3,4], cost=[3,4,3]   | -1       | total=-1<0 short-circuits to -1.            |
 * | Answer is station 0           | gas=[3,1,1], cost=[1,2,2]   | 0        | current never dips below 0, start never reset|
 * | Failure on the last station   | last leg forces reset       | -1       | start becomes n, but total<0 so -1 returned. |
 * | All stations break even       | gas=[2,2], cost=[2,2]       | 0        | total=0>=0, current never negative -> 0.     |
 *
 * ------------------------------------------------------------
 * Potential Pitfalls
 * ------------------------------------------------------------
 * Pitfall 1 — Returning the failure index instead of index+1:
 *    // WRONG
 *    if (current < 0) { start = i; current = 0; }
 *    // CORRECT — the failed station itself can't be the start
 *    if (current < 0) { start = i + 1; current = 0; }
 *
 * Pitfall 2 — Deciding feasibility from current instead of total:
 *    // WRONG — current is reset repeatedly; can't judge global feasibility
 *    return current >= 0 ? start : -1;
 *    // CORRECT — total reflects the whole circuit
 *    return total >= 0 ? start : -1;
 *
 * Pitfall 3 — Forgetting the circular wrap in brute force:
 *    // WRONG — walks off the end of the array
 *    tank += gas[start + step] - cost[start + step];
 *    // CORRECT
 *    int station = (start + step) % n;
 *    tank += gas[station] - cost[station];
 *
 * ============================================================
 * 8. SELF-CORRECTION & TESTING
 * ============================================================
 * Q: What edge cases might this miss?
 * A: The start = i+1 reset can push start to n on the final iteration. This is only
 *    reachable when the last leg fails, which forces total < 0, so -1 is returned and
 *    the invalid index is never dereferenced. Single-station and all-break-even
 *    inputs are handled because current < 0 simply never triggers when fuel suffices.
 *
 * Q: Are there any type mismatches?
 * A: With n <= 10^5 and values <= 10^4, the max magnitude of total is
 *    10^5 * 10^4 = 10^9, which fits in a signed 32-bit int (max ~2.14 * 10^9).
 *    No long required, though using long would be harmless and defensive.
 *
 * Q: How can I verify this works right now?
 *    public class GasStationVerify {
 *        static int solve(int[] gas, int[] cost) {
 *            int total = 0, current = 0, start = 0;
 *            for (int i = 0; i < gas.length; i++) {
 *                int diff = gas[i] - cost[i];
 *                total += diff; current += diff;
 *                if (current < 0) { start = i + 1; current = 0; }
 *            }
 *            return total >= 0 ? start : -1;
 *        }
 *        public static void main(String[] args) {
 *            assert solve(new int[]{1,2,3,4,5}, new int[]{3,4,5,1,2}) == 3;
 *            assert solve(new int[]{2,3,4},     new int[]{3,4,3})     == -1;
 *            assert solve(new int[]{5},         new int[]{4})         == 0;
 *            assert solve(new int[]{5},         new int[]{6})         == -1;
 *            assert solve(new int[]{3,1,1},     new int[]{1,2,2})     == 0;
 *            System.out.println("All assertions passed.");
 *        }
 *    }
 *    // Run with: java -ea GasStationVerify.java
 *
 * | Approach       | Risk                                   | Mitigation                                        |
 * |----------------|----------------------------------------|---------------------------------------------------|
 * | Brute Force    | Times out at n=10^5 (O(n^2))           | Use only as correctness oracle for small n.       |
 * | Min Prefix Sum | Off-by-one in (minIndex + 1) % n       | Unit-test the wrap case where minIndex == n-1.    |
 * | Greedy One-Pass| Using current instead of total to decide| Always gate the return on total >= 0.            |
 *
 * ============================================================
 * 9. COMPANIES & FREQUENCY
 * ============================================================
 * LeetCode #134 · Difficulty: Medium · ~1M+ cumulative interview appearances reported.
 *
 * | Company          | Frequency | Notes                                            |
 * |------------------|-----------|--------------------------------------------------|
 * | Amazon           | ⭐⭐⭐⭐⭐ | Very common; classic greedy screener.            |
 * | Google           | ⭐⭐⭐⭐   | Tests the correctness proof, not just the code.  |
 * | Microsoft        | ⭐⭐⭐⭐   | Frequent onsite / phone-screen appearance.       |
 * | Bloomberg        | ⭐⭐⭐⭐   | Popular for its clean single-pass insight.       |
 * | Apple            | ⭐⭐⭐     | Shows up in array/greedy rounds.                 |
 * | Adobe            | ⭐⭐⭐     | Often the follow-up to a simpler array problem.  |
 * | Uber             | ⭐⭐⭐     | Fits their routing/logistics theme.              |
 * | Meta (Facebook)  | ⭐⭐⭐     | Asked as a greedy-reasoning warm-up.             |
 * | Goldman Sachs    | ⭐⭐       | Appears in mixed DS&A rounds.                     |
 * | Oracle           | ⭐⭐       | Less frequent but documented.                     |
 *
 * ============================================================
 * 10. FINAL SUMMARY
 * ============================================================
 * | Approach        | Time   | Space | Code Complexity | Recommended?                                   |
 * |-----------------|--------|-------|-----------------|------------------------------------------------|
 * | Brute Force     | O(n^2) | O(1)  | Low             | ❌ Times out for large n; correctness oracle   |
 * | Min Prefix Sum  | O(n)   | O(1)  | Medium          | ✅ Excellent alternative framing               |
 * | Greedy One-Pass | O(n)   | O(1)  | Low             | ✅✅ Best overall — optimal on both axes        |
 *
 * ------------------------------------------------------------
 * Recommended Approach
 * ------------------------------------------------------------
 * Use the Greedy One-Pass: O(n) time, O(1) space, shortest to write. Time and space
 * optima coincide, so there's no trade-off — greedy wins outright, with Min Prefix
 * Sum as an equally efficient backup if the interviewer wants a different justification.
 *
 * ------------------------------------------------------------
 * What to Remember
 * ------------------------------------------------------------
 * Collapse gas and cost into a single NET-DIFFERENCE array and lean on two facts:
 * (1) a solution exists iff the total net fuel is non-negative, and (2) whenever your
 * running tank goes negative at station i, the next viable start is i+1 — never
 * anything in between. That reset-on-failure trick is the reusable pattern; the key
 * gotcha is judging feasibility from the GLOBAL total, not the repeatedly-reset tank.
 */
// @formatter:on
