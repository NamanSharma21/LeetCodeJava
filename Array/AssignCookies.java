package Array;

import java.util.Arrays;

public class AssignCookies {
    public static void main(String[] args) {
        AssignCookies assignCookies = new AssignCookies();

        System.out.println(
                "Assign Cookies : " + assignCookies.findContentChildren(new int[] { 1, 2, 3
                }, new int[] { 1, 1 }));

        System.out.println(
                "Assign Cookies : " + assignCookies.findContentChildren(new int[] { 1, 2 }, new int[] { 1, 2, 3 }));

        System.out.println(
                "Assign Cookies : " + assignCookies.findContentChildren(new int[] { 1, 2, 3
                }, new int[] { 3 }));

        System.out.println(
                "Assign Cookies : "
                        + assignCookies.findContentChildren(new int[] { 10, 9, 8, 7 }, new int[] { 5,
                                6, 7, 8 }));
    }

    /**
     * 
     * https://leetcode.com/problems/assign-cookies/description/?envType=problem-list-v2&envId=array
     * 
     * 
     * Assume you are an awesome parent and want to give your children some cookies.
     * But, you should give each child at most one cookie.
     * 
     * Each child i has a greed factor g[i], which is the minimum size of a cookie
     * that the child will be content with; and each cookie j has a size s[j]. If
     * s[j] >= g[i], we can assign the cookie j to the child i, and the child i will
     * be content. Your goal is to maximize the number of your content children and
     * output the maximum number.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input: g = [1,2,3], s = [1,1]
     * Output: 1
     * Explanation: You have 3 children and 2 cookies. The greed factors of 3
     * children are 1, 2, 3.
     * And even though you have 2 cookies, since their size is both 1, you could
     * only make the child whose greed factor is 1 content.
     * You need to output 1.
     * Example 2:
     * 
     * Input: g = [1,2], s = [1,2,3]
     * Output: 2
     * Explanation: You have 2 children and 3 cookies. The greed factors of 2
     * children are 1, 2.
     * You have 3 cookies and their sizes are big enough to gratify all of the
     * children,
     * You need to output 2.
     * 
     * 
     * Constraints:
     * 
     * 1 <= g.length <= 3 * 104
     * 0 <= s.length <= 3 * 104
     * 1 <= g[i], s[j] <= 231 - 1
     * 
     * 
     * Note: This question is the same as 2410: Maximum Matching of Players With
     * Trainers.
     * 
     */

    public int findContentChildren(int[] g, int[] s) {
        // int contentCount = 0;
        // int greedCounter = 0;
        // int cookieCounter = 0;
        // while (cookieCounter < s.length) {
        // if (contentCount == s.length) {
        // return contentCount;
        // }
        // while (greedCounter < g.length) {
        // System.out.println("G : " + g[greedCounter] + " S : " + s[cookieCounter]);
        // if (s[cookieCounter] >= g[greedCounter]) {
        // contentCount++;
        // break;
        // }
        // greedCounter++;
        // }
        // greedCounter = 0;
        // cookieCounter++;
        // }
        // return contentCount;

        Arrays.sort(g);
        Arrays.sort(s);

        int child = 0, cookie = 0, count = 0, n = g.length, m = s.length;

        while (child < n && cookie < m) {
            if (s[cookie] >= g[child]) {
                child++;
                cookie++;
                count++;
            } else {
                cookie++;
            }
        }
        return count;
    }
}
