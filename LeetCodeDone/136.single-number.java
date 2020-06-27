/*
 * @lc app=leetcode id=136 lang=java
 *
 * [136] Single Number
 */

// @lc code=start
class Solution {
    public int singleNumber(int[] nums) {
        int ret = 0;
        for (int i: nums) {
            ret = ret^i;
        }
        return ret;
    }
}
// @lc code=end

